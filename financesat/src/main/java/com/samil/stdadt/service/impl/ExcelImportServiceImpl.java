package com.samil.stdadt.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.comm.vo.CommCodeVO;
import com.samil.stdadt.mapper.ExcelImportMapper;
import com.samil.stdadt.mapper.ProjectBudgetMapper;
import com.samil.stdadt.mapper.ProjectInfoMapper;
import com.samil.stdadt.service.ExcelImportService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.util.excelparser.AbstractExcelParser;
import com.samil.stdadt.util.excelparser.BudgetHourExcelParser;
import com.samil.stdadt.vo.ResultVO;
import com.samil.stdadt.vo.WeekVO;

@Service
public class ExcelImportServiceImpl implements ExcelImportService {
	static final Logger logger = LoggerFactory.getLogger(ExcelImportServiceImpl.class);
	
	@Autowired
	ExcelImportMapper excelMapper;
	
	@Autowired
	ProjectBudgetMapper bdgtMapper;
	
	@Autowired
	ProjectInfoMapper prjtMapper;
	
	
	@Override
	public ResultVO importBudgetHour(Map<String, Object> param, File excel, Map<String, List<CommCodeVO>> codes) throws Exception {
		ResultVO result = new ResultVO();
		
		Map<String, Object> prjtInfo = prjtMapper.getPrjtInfoSimple(param);
		Map<String, Object> weekParam = new HashMap<String, Object>();
		weekParam.put("prjtFrdt", prjtInfo.get("prjtFrdt"));
		weekParam.put("prjtTodt", prjtInfo.get("prjtTodt"));
		weekParam.put("maxWeekNum", Constant.maxWeekNum);
		
		List<WeekVO> weeks = bdgtMapper.getWeekInfo(weekParam);
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
		
		parser = new BudgetHourExcelParser(param, sheet, codes, weeks);
		parser.ready().parse();
		
		if(parser.isError()) {
			result.setStatus(Constant.RESULT.FAIL);
			result.setMsg(parser.getErrLog());
		}else {
			Map<String, Object> data = parser.getData();
			
			// 존재하지 않는 사번이 있는지 체크
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> members = (List<Map<String, Object>>) data.get("members");
			if(members.size() == 0) {
				result.setStatus(Constant.RESULT.FAIL);
				result.setMsg("Import 할 대상을 입력하세요.");
				return result;
			}
			
			List<String> inValidEmp = new ArrayList<String>();
			List<Map<String, Object>> empList = excelMapper.selectEmp(data);
			
			for(int i=0; i<members.size(); i++) {
				Map<String, Object> member = members.get(i);
				Stream<Map<String, Object>> existsEmp = empList.stream().filter(emplno -> member.get("membEmplNo").equals(emplno.get("emplNo")));
				if(existsEmp.count() == 0) {
					inValidEmp.add((String) members.get(i).get("membEmplNo"));
				}
			}
			
			// 키 중복여부 확인
			Map<String, Integer> checkDup = new HashMap<String, Integer>();
			for(int i=0; i<members.size(); i++) {
				Map<String, Object> member = members.get(i);
				String key = "" + member.get("actvCd") + member.get("locaCd") + "_" + member.get("membEmplNo");
				if(checkDup.get(key) == null) {checkDup.put(key, 0);}
				
				checkDup.put(key, checkDup.get(key) + 1);
			}
			List<String> dupKeyMsg = new ArrayList<String>();
			Iterator<String> itr = checkDup.keySet().iterator();
			while(itr.hasNext()) {
				String key = itr.next();
				if(checkDup.get(key) > 1) {
					dupKeyMsg.add(key.split("_")[1]);
				}
			}
			
			// 유효한 사번이고 키 중복이 없을 경우에만 데이터 입력
			if(inValidEmp.size() > 0) {
				result.setStatus(Constant.RESULT.FAIL);
				inValidEmp.add(0, "존재하지 않는 사번이 입력되었습니다.");
				result.setMsg(inValidEmp);
			}else if(dupKeyMsg.size()> 0){
				result.setStatus(Constant.RESULT.FAIL);
				dupKeyMsg.add(0, "Activity, Location, Name 중복건이 존재합니다.");
				result.setMsg(dupKeyMsg);
			}else {
				bdgtMapper.deleteAllMemberBudget(param);
				bdgtMapper.deleteAllMember(param);
				
				List<Map<String, Object>> members2 = (List<Map<String, Object>>) data.get("members");
				if(members2.size() > 0) {
					List<Map<String, Object>> members2Copy = new ArrayList<Map<String, Object>>(); 
					members2Copy.addAll((List<Map<String, Object>>) data.get("members"));
					// Budget 정보가 많을 경우, 50개씩 Insert 하기 위함.
					for(int i=0; i<members2Copy.size(); i=i+50) {
						int lastIdx = (i+50)>members2Copy.size()?members2Copy.size():i+50;
						data.put("members", members2Copy.subList(i, lastIdx));
						excelMapper.inertMemberByExcel(data);
					}
				}
				
				
				List<Map<String, Object>> bdgts = (List<Map<String, Object>>) data.get("bdgts");
				if(bdgts.size() > 0) {
					List<Map<String, Object>> bdgtsCopy = new ArrayList<Map<String, Object>>(); 
					bdgtsCopy.addAll((List<Map<String, Object>>) data.get("bdgts"));
					// Budget 정보가 많을 경우, 1000개씩 Insert 하기 위함.
					for(int i=0; i<bdgtsCopy.size(); i=i+1000) {
						int lastIdx = (i+1000)>bdgtsCopy.size()?bdgtsCopy.size():i+1000;
						data.put("bdgts", bdgtsCopy.subList(i, lastIdx));
						excelMapper.inertMemberBudgetByExcel(data);	
					}
				}
				
				// 결재 완료 상태일 경우에 Excel Import를 하면 재결재를 받을 수 있도록 등록(RG)상태로 되돌린다.
				if(prjtInfo.get("stat") == "CO") {
					param.put("stat", "RG");
					prjtMapper.updateStatus(param);
				}
				result.setStatus(Constant.RESULT.SUCCESS);
			}
			
		}
		
		return result;
	}


	@Override
	public Map<String, Object> getWkmnspMinMax() throws Exception {
		return excelMapper.getWkmnspMinMax();
	}
}
