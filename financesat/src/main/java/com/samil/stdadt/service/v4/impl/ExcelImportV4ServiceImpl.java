package com.samil.stdadt.service.v4.impl;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.comm.service.CommCodeService;
import com.samil.stdadt.comm.vo.ApplicationConfigVO;
import com.samil.stdadt.comm.vo.CommCodeVO;
import com.samil.stdadt.mapper.ExcelImportMapper;
import com.samil.stdadt.mapper.ProjectBudgetV4Mapper;
import com.samil.stdadt.mapper.ProjectInfoMapper;
import com.samil.stdadt.service.v4.ExcelImportV4Service;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.util.excelparser.AbstractExcelParser;
import com.samil.stdadt.util.excelparser.v4.BudgetHourExcelParser;
import com.samil.stdadt.vo.ResultVO;
import com.samil.stdadt.vo.WeekVO;

@Service
public class ExcelImportV4ServiceImpl implements ExcelImportV4Service {
	static final Logger logger = LoggerFactory.getLogger(ExcelImportV4ServiceImpl.class);
	
	@Autowired
	ExcelImportMapper excelMapper;
	
	@Autowired
	ProjectBudgetV4Mapper bdgtMapper;
	
	@Autowired
	ProjectInfoMapper prjtMapper;
	
	@Autowired
	ApplicationConfigVO appConfig;
	
	@Autowired
	CommCodeService commService;
	
	
	@Override
	public ResultVO importBudgetHour(Map<String, Object> param, File excel, Map<String, List<CommCodeVO>> codes) throws Exception {
		ResultVO result = new ResultVO();
		
		Map<String, Object> prjtInfo = prjtMapper.getPrjtInfoSimple(param);
		Map<String, Object> weekParam = new HashMap<String, Object>();
		weekParam.put("prjtFrdt", prjtInfo.get("prjtFrdt"));
		weekParam.put("prjtTodt", prjtInfo.get("prjtTodt"));
		weekParam.put("maxWeekNum", Constant.maxWeekNum);
		
		if("CO".equals(prjtInfo.get("stat"))) {
			Map<String, Object> statusParam = new HashMap<String, Object>();
			statusParam.put("prjtCd", param.get("prjtCd"));
			statusParam.put("stat", "RG");
			statusParam.put("session", param.get("session"));
			prjtMapper.updateStatus(statusParam);
		}

		List<WeekVO> weeks = bdgtMapper.getWeekInfo(weekParam);	// 기간 DATE 정보
		
		//----------------------------------------------------------------------
		//[시작] 20230207 Budget 입력주기에 따라서 실제 유효한 엑셀 week정보를 가져온다. 
		//----------------------------------------------------------------------
		List<WeekVO> orgWeeks = weeks;
		
		String budgetInputCycle = (param.get("budgetInputCycle")==null)?"Weekly":param.get("budgetInputCycle").toString();
		
		//두 날짜간의 월 차이를 계산한다.
		String prjtFrdt = weekParam.get("prjtFrdt").toString();
		String prjtTodt = weekParam.get("prjtTodt").toString();
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate prjtFrdtDt = LocalDate.parse(prjtFrdt, format);
		LocalDate prjtTodtDt = LocalDate.parse(prjtTodt, format);
		
	    int diff = (int) ChronoUnit.MONTHS.between(prjtFrdtDt, prjtTodtDt);
	    weekParam.put("diffMonth", diff+1);
		
		if("Monthly".equals(budgetInputCycle)) {
			orgWeeks = bdgtMapper.getMonthlyInfo(weekParam);
		}else if("Quarterly".equals(budgetInputCycle)) {
			orgWeeks = bdgtMapper.getQuarterlyInfo(weekParam);	
		}else if("Annually".equals(budgetInputCycle)) {
			orgWeeks = bdgtMapper.getAnnuallyInfo(weekParam);		
		}		
		//----------------------------------------------------------------------
		//[종료] 20230207 Budget 입력주기에 따라서 실제 유효한 엑셀 week정보를 가져온다. 
		//----------------------------------------------------------------------
		
		List<Map<String, Object>> gradList = bdgtMapper.getGradList();	// 직급리스트	
		List<Map<String, Object>> dftBdgtRoleConfig = bdgtMapper.selectDftBdgtRoleConfig(param); // role정보)
		
		param.putAll(prjtInfo);
		String filePath = excel.getAbsolutePath();
		FileInputStream inputStream = new FileInputStream(filePath);
		
		Sheet sheet;
		AbstractExcelParser parser;
		if(excel.getName().toLowerCase().endsWith(".xlsx")) {
			sheet = (new XSSFWorkbook(inputStream)).getSheetAt(0);
		}else if(excel.getName().toLowerCase().endsWith(".xls")) {
			sheet = (new HSSFWorkbook(inputStream)).getSheetAt(0);
		}else {
			inputStream.close();
			throw new Exception("허용되지 않는 파일 타입입니다.");
		}
		
		// Excel을 객체화 하기 
		parser = new BudgetHourExcelParser(param, sheet, codes, weeks, budgetInputCycle, orgWeeks);
		parser.ready().parse();
		
		if(parser.isError()) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(parser.getErrLog());
		}else {
			// 파싱된 데이터 결과 가져오기
			Map<String, Object> data = parser.getData();
			
			//----------------------------------------------------------------------------------
			// 파싱된 데이터 찍어보기
			//----------------------------------------------------------------------------------
//			System.out.println("####################################################################################################");
//			System.out.println("파싱 이후 : " + data.toString());	
//			System.out.println("####################################################################################################");

			//==========================================================
			//[시작] 20230207 남웅주  2023년도 표준감사시간 개정 : Budget 입력 주기 저장
			//==========================================================		
			Map<String, Object> inParam = new HashMap<String, Object>();
			inParam.put("prjtCd", param.get("prjtCd"));
			inParam.put("budgetInputCycle", budgetInputCycle);
			prjtMapper.updateBudgetInputCycle(inParam);
			
			// 업로드하는 엑셀의 Budget 대상 프로젝트 목록 
			List<String> bdgtTargtList = excelMapper.getBudgetTargetPrjtCd((String) param.get("prjtCd"));
						
			// Import할 멤버정보 가져오기
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> members = (List<Map<String, Object>>) data.get("members");
			if(members.size() == 0) {
				result.setStatus(Constant.RESULT.FAIL);
				result.setMsg("Import 할 대상을 입력하세요.");
				return result;
			}
			
			// Import 대상 중 Budget 대상으로 지정되지 않은 프로젝트가 있는지 확인
			List<String> noBudgetTargetList = members.stream()
													 .filter(member -> !bdgtTargtList.contains(member.get("prjtcd")))
													 .map(member -> (String) member.get("prjtcd"))
													 .collect(Collectors.toList());
			if(noBudgetTargetList.size() > 0) {
				result.setStatus(Constant.RESULT.FAIL);
				result.setMsg("[CIS 예산 및 Code별 예산]에서 Budget 대상으로 지정 안된 프로젝트 코드가 존재합니다. (" + String.join(",", noBudgetTargetList) + ")");
				return result;
			}
			
			List<String> inValidEmp = new ArrayList<String>();
			Map<String, Integer> keyCount = new HashMap<String, Integer>();
			List<Map<String, Object>> empList = excelMapper.selectEmp(data);
			for(int i=0; i<members.size(); i++) {
				Map<String, Object> member = members.get(i);
				String tbd = member.get("tbd").toString();
				String actvCd = member.get("actvCd").toString();
				String key = "" + member.get("prjtcd") + member.get("actvCd") + member.get("locaCd") + "_" + member.get("membEmplNo");
				
				// 키 count 입력
				keyCount.put(key, (keyCount.get(key) == null ? 0 : keyCount.get(key)) + 1);
				
				if("Y".equals(tbd) && Constant.BUDGET_ROLE_CD.contains(actvCd)) {
					// tbd이고 actvCd에 해당하는 default값이 있을 경우, default값으로 설정한다.
					List<Map<String, Object>> tmp = dftBdgtRoleConfig.stream().filter(item -> item.get("cd").equals(actvCd)).collect(Collectors.toList());
					if(tmp.size() > 0) {
						member.put("gradcd", tmp.get(0).get("gradcd"));
						if(((Double) member.get("wkmnsp")).compareTo(0.0) == 0)
							member.put("wkmnsp", Double.parseDouble(String.valueOf(tmp.get(0).get("wkmnsp"))));
					}
				}else{
					// 직급명으로 직급 코드를 찾아 설정
					if(!"".equals(member.get("gradnm"))) {						
						List<Map<String, Object>> tmp = gradList.stream().filter(item -> item.get("gradnm").equals(member.get("gradnm"))).collect(Collectors.toList());
						if(tmp.size() > 0) member.put("gradcd", tmp.get(0).get("gradcd"));
					} 
					
					// 유효한 사번인지 확인
					List<Map<String, Object>> existsEmp = empList.stream().filter(emplno -> member.get("membEmplNo").equals(emplno.get("emplNo"))).collect(Collectors.toList());
					if(existsEmp.size() == 0) {
						inValidEmp.add((String) members.get(i).get("membEmplNo"));
					}
				}	
			}
			
			// 키 중복여부
			List<String> dupKeyMsg = new ArrayList<String>();
			for (String key : keyCount.keySet())
				if(keyCount.get(key) > 1) dupKeyMsg.add(key.split("_")[1]); // 사번
			
			if(dupKeyMsg.size()> 0){
				result.setStatus(Constant.RESULT.FAIL);
				dupKeyMsg.add(0, "아래 사번의 경우 Project 코드, Activity, Location의 중복이 존재합니다.");
				result.setMsg(dupKeyMsg);
				return result;
			}
						
			// 유효하지 않는 사번이 입력되었는지 확인.
			if(inValidEmp.size() > 0) {
				result.setStatus(Constant.RESULT.FAIL);
				inValidEmp.add(0, "존재하지 않는 사번이 입력되었습니다.");
				result.setMsg(inValidEmp);
				return result;
			}
						
			// 유효한 사번이고 키 중복이 없을 경우에만 데이터 입력
			bdgtMapper.deleteAllMemberBudget((String) param.get("prjtCd"));
			bdgtMapper.deleteAllMember((String) param.get("prjtCd"));
			data.put(Constant.PARAM_NAME.APP_CD, appConfig.getAppCd());
			
			List<Map<String, Object>> members2 = (List<Map<String, Object>>) data.get("members");
			if(members2.size() > 0) {
				final int batchSize = 500;
				List<Map<String, Object>> members2Copy = new ArrayList<Map<String, Object>>(); 
				members2Copy.addAll((List<Map<String, Object>>) data.get("members"));
				// Budget 정보가 많을 경우, 50개씩 Insert 하기 위함.
				for(int i=0; i<members2Copy.size(); i=i+batchSize) {
					int lastIdx = (i+batchSize)>members2Copy.size()?members2Copy.size():i+batchSize;
					data.put("members", members2Copy.subList(i, lastIdx));
					excelMapper.inertMemberByExcelV3(data);
				}
			}
			
			List<Map<String, Object>> bdgts = (List<Map<String, Object>>) data.get("bdgts");
			if(bdgts.size() > 0) {
				final int batchSize = 1000;
				List<Map<String, Object>> bdgtsCopy = new ArrayList<Map<String, Object>>(); 
				bdgtsCopy.addAll((List<Map<String, Object>>) data.get("bdgts"));
				// Budget 정보가 많을 경우, 1000개씩 Insert 하기 위함.
				for(int i=0; i<bdgtsCopy.size(); i=i+batchSize) {
					int lastIdx = (i+batchSize)>bdgtsCopy.size()?bdgtsCopy.size():i+batchSize;
					data.put("bdgts", bdgtsCopy.subList(i, lastIdx));
					excelMapper.inertMemberBudgetByExcelV3(data);	
				}
			}
			result.setStatus(Constant.RESULT.SUCCESS);
				
		}
	
		
		return result;
	}


	@Override
	public Map<String, Object> getWkmnspMinMax() throws Exception {
		return excelMapper.getWkmnspMinMax();
	}
}
