package com.samil.stdadt.service.v4.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.comm.service.CommCodeService;
import com.samil.stdadt.mapper.ProjectBudgetV4Mapper;
import com.samil.stdadt.mapper.ProjectInfoMapper;
import com.samil.stdadt.service.v4.ProjectBudgetV4Service;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ProjectBudgetCisV4VO;
import com.samil.stdadt.vo.ProjectBudgetMemberV4VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryV4VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.ResultVO;
import com.samil.stdadt.vo.WeekVO;

@Service
public class ProjectBudgetV4ServiceImpl implements ProjectBudgetV4Service {
	
	Logger logger = LoggerFactory.getLogger(ProjectBudgetV4ServiceImpl.class);

	@Autowired
	ProjectInfoMapper prjtMapper;
	
	@Autowired
	ProjectBudgetV4Mapper bdgtMapper;
	
	@Autowired
	CommCodeService commService;
	
	@Override
	public ProjectBudgetSummaryV4VO getBudgetSummary(Map<String, Object> param) throws Exception {
		// 프로젝트 Summary 정보 가져오기
		ProjectBudgetSummaryV4VO summary = bdgtMapper.getProjectBudgetV4Summary(param);
		if(summary == null) return null;
		param.put("prjtFrdt", summary.getPrjtFrdt().replaceAll("-", ""));
		param.put("prjtTodt", summary.getPrjtTodt().replaceAll("-", ""));
		
		List<WeekVO> vo = null;
		//20230207 Budget 입력주기
		String budgetInputCycle = (param.get("budgetInputCycle")==null)?"Weekly":param.get("budgetInputCycle").toString();
		// 프로젝트 기간별 주 시작일 가져오기(Grid의 컬럼 정보 생성을 위함)
		if("Weekly".equals(budgetInputCycle)) {
			vo = this.getBudgetWeek(param);
		}else if("Monthly".equals(budgetInputCycle)) {
			vo = this.getBudgetMonthly(param);
		}else if("Quarterly".equals(budgetInputCycle)) {
			vo = this.getBudgetQuarterly(param);	
		}else if("Annually".equals(budgetInputCycle)) {
			vo = this.getBudgetAnnually(param);				
		}
		
		if(vo != null) summary.setsDtOfWeek(vo);
		return summary;
	}
	
	@Override
	public List<WeekVO> getBudgetWeek(Map<String, Object> param) throws Exception{
		Map<String, Object> weekParam = new HashMap<String, Object>();
		if(param.get("prjtFrdt") == null || param.get("prjtTodt") == null) {
			Map<String, Object> prjtInfo = prjtMapper.getPrjtInfoSimple(param);
			weekParam.put("prjtFrdt", prjtInfo.get("prjtFrdt"));
			weekParam.put("prjtTodt", prjtInfo.get("prjtTodt"));	
		}else {
			weekParam = param;
		}
		weekParam.put("maxWeekNum", Constant.maxWeekNum);
		return bdgtMapper.getWeekInfo(weekParam);
	}

	@Override
	public List<ProjectBudgetMemberV4VO> getBudgetList(Map<String, Object> param) throws Exception {
		List<WeekVO> weeks = this.getBudgetWeek(param);
		param.put("weeks", weeks);
		
		//20230213 Budget 입력주기
		List<ProjectBudgetMemberV4VO> vo = null;
		String budgetInputCycle = (param.get("budgetInputCycle")==null)?"Weekly":param.get("budgetInputCycle").toString();
		// 프로젝트 기간별 주 시작일 가져오기(Grid의 컬럼 정보 생성을 위함)
		if("Weekly".equals(budgetInputCycle)) {
			vo = bdgtMapper.getProjectBudgetMember(param);
		}else if("Monthly".equals(budgetInputCycle)) {
			vo = bdgtMapper.getProjectBudgetMemberMonthly(param);
		}else if("Quarterly".equals(budgetInputCycle)) {
			vo = bdgtMapper.getProjectBudgetMemberQuarterly(param);	
		}else if("Annually".equals(budgetInputCycle)) {
			vo = bdgtMapper.getProjectBudgetMemberAnnually(param);				
		}

		return vo;
	}

	@Override
	public ResultVO saveMemberBudget(Map<String, Object> param) throws Exception {
		ResultVO result = new ResultVO();
		
		//==========================================================
		//[시작] 20230207 남웅주  2023년도 표준감사시간 개정 : Budget 입력 주기
		//==========================================================		
		Map<String, Object> inParam = new HashMap<String, Object>();
		inParam.put("prjtCd", param.get("prjtCd"));
		inParam.put("budgetInputCycle", param.get("budgetInputCycle"));
		prjtMapper.updateBudgetInputCycle(inParam);
		
		//----------------------------------------------------------------
		// 20230207 Budget 입력주기에 따라서 실제 유효한 엑셀 week정보를 가져온다. 
		//----------------------------------------------------------------
		Map<String, Object> prjtInfo = prjtMapper.getPrjtInfoSimple(param);
		Map<String, Object> weekParam = new HashMap<String, Object>();
		weekParam.put("prjtFrdt", prjtInfo.get("prjtFrdt"));
		weekParam.put("prjtTodt", prjtInfo.get("prjtTodt"));
		weekParam.put("maxWeekNum", Constant.maxWeekNum);		
		
		List<WeekVO> weeks = bdgtMapper.getWeekInfo(weekParam);	// 기간 DATE 정보
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
		
		//==========================================================
		//[종료] 20230207 남웅주  2023년도 표준감사시간 개정 : Budget 입력 주기
		//==========================================================		
		
		// 결재 완료된 것을 수정하면 등록상태로 변경한다.
		if("CO".equals(param.get("stat"))) {
			Map<String, Object> statusParam = new HashMap<String, Object>();
			statusParam.put("prjtCd", param.get("prjtCd"));
			statusParam.put("stat", "RG");
			statusParam.put("session", param.get("session"));
			prjtMapper.updateStatus(statusParam);
		}
		
		List<Map<String, Object>> dftBdgtRoleConfig = bdgtMapper.selectDftBdgtRoleConfig(param); // role정보
		Map<String, String> roleGradCd = new HashMap<String, String>();
		for(int i=0; i<dftBdgtRoleConfig.size(); i++) {
			roleGradCd.put( (String) dftBdgtRoleConfig.get(i).get("cd"), (String) dftBdgtRoleConfig.get(i).get("gradcd"));
		}

		// Cis 그리드 정보 업데이트
		List<Map<String, Object>> cisList = (List<Map<String, Object>>) param.get("cislist");
		if (cisList.size() > 0) {
			for (Map<String, Object> cisData : cisList) {
				cisData.put(Constant.PARAM_NAME.SESSION, param.get(Constant.PARAM_NAME.SESSION));

				String div = (String) cisData.get("div");
				if ("main".equals(div)) {
					bdgtMapper.updateCisMainBudget(cisData);
				} else {
					cisData.put("p_prjtCd", param.get("prjtCd"));
					String prjtCd = cisData.get("prjtcd").toString();
					String[] prjtCds = prjtCd.split("-");
					cisData.put("prjtCd1", prjtCds[0]);
					cisData.put("prjtCd2", prjtCds[1]);
					cisData.put("prjtCd3", prjtCds[2]);
					bdgtMapper.mergeCisSubBudget(cisData);
				}
			}
		}
		
		// 멤버, 멤버별 Budget 정보 초기화
		bdgtMapper.deleteAllMemberBudget((String) param.get("prjtCd"));
		bdgtMapper.deleteAllMember((String) param.get("prjtCd"));
		
		// Member 정보 저장
		List<Map<String, Object>> members = (List<Map<String, Object>>) param.get("list");
		
		if(members.size() > 0) {
			bdgtMapper.inertMember(param);	// Member 저장
			
			List<Map<String, Object>> bdgtList = new ArrayList<Map<String, Object>>(); // Member Budget 저장
			
			for(Map<String, Object> member : members) {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> bdgts = (List<Map<String, Object>>)  member.get("list");
				
				//Budget 입력주기 가 Weekly 가 아닐때만 사용된다.
				int orgWeeksPos = 0;
				for(Map<String, Object> bdgt : bdgts) {
					//----------------------------------------------------------------------
					//[시작] 20230214 Budget 입력주기에 따라서 실제 저장 
					//----------------------------------------------------------------------
					if("Monthly".equals(budgetInputCycle)) {
						Double value = (Double) bdgt.get("asgnTm");
						//---------------------------------------------
						// value 가 월인데, 주단위로 나누어서 저장해야 한다.
						//---------------------------------------------
						
						//1. 월정보를 읽어내자.
						WeekVO vo = orgWeeks.get(orgWeeksPos);
						int weekCnt = 0;
						for(int w=0; w < weeks.size() ;w++){							
							//주 정보에서 월이 같은것이 몇개인지 찾는다. 2023/05 == 2023/05/01
							if(vo.getStartDate().toString().equals(weeks.get(w).getStartDate().substring(0, 7))) {
								weekCnt++;
							}
						}
						//2. 주 개수만큼 나누어서 저장한다.
						Double dWeekValue[] = new Double[weekCnt];
						//2.1 소숫점 2자리만 저장한다.(버림)
						Double dpValue = Math.floor((value / weekCnt)*100)/100.0;
												
						for(int x=0; x < dWeekValue.length;x++) {
							dWeekValue[x] = dpValue;
						}
						//2.2 소숫점 2자리로 딱 떨어지지 않은 값이 있다면 첫번째 배열에 넣는다.
						String tmpValue = String.format("%.2f", (value - (dpValue * weekCnt)));
						dWeekValue[0] = Double.valueOf(String.format("%.2f", (dWeekValue[0] + Double.valueOf(tmpValue))));					
						
						//아래 처럼 바로 연산하면 소숫점 이하 .999999 처럼 생긴다.
						//소숫점 이하 2자리로 변환한뒤 사용한다.
						//dWeekValue[0] += (value - (Double.valueOf(dpValue) * weekCnt));
						
						//3. 배열 값을 해당 weeks 에 적용해서 저장하자.
						int weekIdx = 0;
						for(int w=0; w < weeks.size() ;w++){							
							//주 정보에서 월이 같은것을 찾는다. 2023/05 == 2023/05/01
							if(vo.getStartDate().toString().equals(weeks.get(w).getStartDate().substring(0, 7))) {
								//maxWorkTm = this.weeks.get(w).getWorkDay() * 16; 
								
								//-----------------------------------------------------------------------
								//TBD = 'Y' 이면 숫자범위제한 금지 체크를 해야 하는데 자동으로 나뉘어서 들어가기 때문에 어렵다.
								//-----------------------------------------------------------------------
								if(dWeekValue[weekIdx].compareTo(0.0) > 0) {
									String weekFrdt = weeks.get(w).getStartDate().replaceAll("/", "-");
									Double asgnTm = dWeekValue[weekIdx];
									
									bdgtList.add(new HashMap<String, Object>(){
										private static final long serialVersionUID = -2082442897453073455L;
									{
										// 2020-05-17 추가
										put("prjtcd", member.get("prjtcd"));
										put("membEmplNo", member.get("membEmplNo"));
										put("actvCd", member.get("actvCd"));
										put("locaCd", member.get("locaCd"));
										put("weekFrdt", weekFrdt);
										put("asgnTm", asgnTm);							
									}});
								}					
								weekIdx++;
							}
						}
												
					}else if("Quarterly".equals(budgetInputCycle)) {
						//Double value = (Double) bdgt.get("asgnTm");
						Double value = Double.valueOf(bdgt.get("asgnTm").toString());
						//---------------------------------------------
						// value 가 분기인데, 주단위로 나누어서 저장해야 한다.
						//---------------------------------------------
						
						//1. 분기정보를 읽어내자.
						WeekVO vo = orgWeeks.get(orgWeeksPos);
						int weekCnt = 0;
						for(int w=0; w < weeks.size() ;w++){							
							//주 정보에서 분기범위 같은것이 몇개인지 찾는다. 2023/01~2023/03 == 2023/02/01
							String frDt = vo.getStartDate().toString().substring(0, 7).replaceAll("/", "");
							String edDt = vo.getStartDate().toString().substring(8).replaceAll("/", "");							
							String orgDt = weeks.get(w).getStartDate().substring(0, 7).replaceAll("/", "");
									
							if((Integer.valueOf(frDt) <= Integer.valueOf(orgDt)) && (Integer.valueOf(edDt) >= Integer.valueOf(orgDt))) {
								weekCnt++;
							}
						}
						
						//2. 주 개수만큼 나누어서 저장한다.
						Double dWeekValue[] = new Double[weekCnt];
						//2.1 소숫점 2자리만 저장한다.(버림)
						Double dpValue = Math.floor((value / weekCnt)*100)/100.0;
												
						for(int x=0; x < dWeekValue.length;x++) {
							dWeekValue[x] = dpValue;
						}
						//2.2 소숫점 2자리로 딱 떨어지지 않은 값이 있다면 첫번째 배열에 넣는다.
						String tmpValue = String.format("%.2f", (value - (dpValue * weekCnt)));
						dWeekValue[0] = Double.valueOf(String.format("%.2f", (dWeekValue[0] + Double.valueOf(tmpValue))));					
						
						//아래 처럼 바로 연산하면 소숫점 이하 .999999 처럼 생긴다.
						//소숫점 이하 2자리로 변환한뒤 사용한다.
						//dWeekValue[0] += (value - (Double.valueOf(dpValue) * weekCnt));
						
						//3. 배열 값을 해당 weeks 에 적용해서 저장하자.
						int weekIdx = 0;
						for(int w=0; w < weeks.size() ;w++){	
							//주 정보에서 분기범위 같은것이 몇개인지 찾는다. 2023/01~2023/03 == 2023/02/01
							String frDt = vo.getStartDate().toString().substring(0, 7).replaceAll("/", "");
							String edDt = vo.getStartDate().toString().substring(8).replaceAll("/", "");							
							String orgDt = weeks.get(w).getStartDate().substring(0, 7).replaceAll("/", "");
									
							if((Integer.valueOf(frDt) <= Integer.valueOf(orgDt)) && (Integer.valueOf(edDt) >= Integer.valueOf(orgDt))) {
								//-----------------------------------------------------------------------
								//TBD = 'Y' 이면 숫자범위제한 금지 체크를 해야 하는데 자동으로 나뉘어서 들어가기 때문에 어렵다.
								//-----------------------------------------------------------------------
								
								if(dWeekValue[weekIdx].compareTo(0.0) > 0) {
									String weekFrdt = weeks.get(w).getStartDate().replaceAll("/", "-");
									Double asgnTm = dWeekValue[weekIdx];
									
									bdgtList.add(new HashMap<String, Object>(){
										private static final long serialVersionUID = -2082442897453073455L;
									{
										put("prjtcd", member.get("prjtcd"));
										put("membEmplNo", member.get("membEmplNo"));
										put("actvCd", member.get("actvCd"));
										put("locaCd", member.get("locaCd"));
										put("weekFrdt", weekFrdt);
										put("asgnTm", asgnTm);							
									}});
								}					
								weekIdx++;
								
							}							
						}
						
					}else if("Annually".equals(budgetInputCycle)) {
						//Double value = (Double) bdgt.get("asgnTm");
						Double value = Double.valueOf(bdgt.get("asgnTm").toString());
						
						//---------------------------------------------
						// value 가 분기인데, 주단위로 나누어서 저장해야 한다.
						//---------------------------------------------
						
						//1. 분기정보를 읽어내자.
						WeekVO vo = orgWeeks.get(orgWeeksPos);
						int weekCnt = 0;
						for(int w=0; w < weeks.size() ;w++){							
							//주 정보에서 분기범위 같은것이 몇개인지 찾는다. 2023/01~2023/03 == 2023/02/01
							String frDt = vo.getStartDate().toString().substring(0, 7).replaceAll("/", "");
							String edDt = vo.getStartDate().toString().substring(8).replaceAll("/", "");							
							String orgDt = weeks.get(w).getStartDate().substring(0, 7).replaceAll("/", "");
									
							if((Integer.valueOf(frDt) <= Integer.valueOf(orgDt)) && (Integer.valueOf(edDt) >= Integer.valueOf(orgDt))) {
								weekCnt++;
							}
						}
						
						//2. 주 개수만큼 나누어서 저장한다.
						Double dWeekValue[] = new Double[weekCnt];
						//2.1 소숫점 2자리만 저장한다.(버림)
						Double dpValue = Math.floor((value / weekCnt)*100)/100.0;
												
						for(int x=0; x < dWeekValue.length;x++) {
							dWeekValue[x] = dpValue;
						}
						//2.2 소숫점 2자리로 딱 떨어지지 않은 값이 있다면 첫번째 배열에 넣는다.
						String tmpValue = String.format("%.2f", (value - (dpValue * weekCnt)));
						dWeekValue[0] = Double.valueOf(String.format("%.2f", (dWeekValue[0] + Double.valueOf(tmpValue))));					
						
						//아래 처럼 바로 연산하면 소숫점 이하 .999999 처럼 생긴다.
						//소숫점 이하 2자리로 변환한뒤 사용한다.
						//dWeekValue[0] += (value - (Double.valueOf(dpValue) * weekCnt));
						
						//3. 배열 값을 해당 weeks 에 적용해서 저장하자.
						int weekIdx = 0;
						for(int w=0; w < weeks.size() ;w++){	
							//주 정보에서 분기범위 같은것이 몇개인지 찾는다. 2023/01~2023/03 == 2023/02/01
							String frDt = vo.getStartDate().toString().substring(0, 7).replaceAll("/", "");
							String edDt = vo.getStartDate().toString().substring(8).replaceAll("/", "");						
							String orgDt = weeks.get(w).getStartDate().substring(0, 7).replaceAll("/", "");
										
							if((Integer.valueOf(frDt) <= Integer.valueOf(orgDt)) && (Integer.valueOf(edDt) >= Integer.valueOf(orgDt))) {
								//-----------------------------------------------------------------------
								//TBD = 'Y' 이면 숫자범위제한 금지 체크를 해야 하는데 자동으로 나뉘어서 들어가기 때문에 어렵다.
								//-----------------------------------------------------------------------
								if(dWeekValue[weekIdx].compareTo(0.0) > 0) {
									String weekFrdt = weeks.get(w).getStartDate().replaceAll("/", "-");
									Double asgnTm = dWeekValue[weekIdx];
									
									bdgtList.add(new HashMap<String, Object>(){
										private static final long serialVersionUID = -2082442897453073455L;
									{
										put("prjtcd", member.get("prjtcd"));
										put("membEmplNo", member.get("membEmplNo"));
										put("actvCd", member.get("actvCd"));
										put("locaCd", member.get("locaCd"));
										put("weekFrdt", weekFrdt);
										put("asgnTm", asgnTm);							
									}});
								}					
								weekIdx++;
								
							}							
						}

					}else {
						// 기존 Weekly 
						bdgtList.add(new HashMap<String, Object>(){
							private static final long serialVersionUID = -2082442897453073455L;
						{
							// 2020-05-17 추가
							put("prjtcd", member.get("prjtcd"));
							put("membEmplNo", member.get("membEmplNo"));
							put("actvCd", member.get("actvCd"));
							put("locaCd", member.get("locaCd"));
							put("weekFrdt", bdgt.get("weekFrdt"));
							put("asgnTm", bdgt.get("asgnTm"));							
						}});						
					}					

					orgWeeksPos++;
				}
			}
			
			if(bdgtList.size() > 0) {					
				Map<String, Object> param2 = new HashMap<String, Object>();
				param2.put(Constant.PARAM_NAME.SESSION, param.get(Constant.PARAM_NAME.SESSION));
				param2.put("prjtCd", param.get("prjtCd"));
				param2.put("list", bdgtList);
				bdgtMapper.inertMemberBudget(param2);
			}
		}
		
		result.setStatus(Constant.RESULT.SUCCESS);
		return result;
	}


	@Override
	public List<Map<String, Object>> getCMByGradCd(Map<String, Object> param) throws Exception {
		return bdgtMapper.getCMByGradCd(param);
	}


	@Override
	public boolean reqELAprv(Map<String, Object> param) throws Exception {
		param.put("stat", "RQ");
		int result = prjtMapper.updateStatus(param);
		
		if(result == 1) return true;
		else return false;
	}

	@Override
	public int saveRetainSchedule(Map<String, Object> param) throws Exception {
		return bdgtMapper.saveRetainSchedule(param);
	}
	
	//--------------------------------------------------
	// 20200428 추가
	//--------------------------------------------------
	@Override
	public List<ProjectBudgetCisV4VO> getBudgetCisList(Map<String, Object> param) throws Exception {
		return bdgtMapper.getProjectBudgetCis(param);
	}
	
	@Override
	public ProjectBudgetSummaryVO getProjectFormInfo(Map<String, Object> param) throws Exception {
		// 프로젝트 Form 버전 정보 가져오기
		return bdgtMapper.getProjectFormInfo(param);
	}

	@Override
	public List<Map<String, Object>> checkDuplPrjt(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return bdgtMapper.checkDuplPrjt(param);
	}

	@Override
	public List<Map<String, Object>> selectDftBdgtRoleConfig(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return bdgtMapper.selectDftBdgtRoleConfig(param);
	}
	
	/* ===========================================================
		[시작] 20230207 Budget 입력 주기 : Budget v4에서 저장시에 최도 
		        BUDGET_INPUT_CYCLE 가 비어 있을때만 업데이트 한다.
			매주 Weekly
			월별 Monthly
			분기별 Quarterly
			매년 Annually
	   =========================================================== */	
	@Override
	public List<WeekVO> getBudgetMonthly(Map<String, Object> param) throws Exception{
		Map<String, Object> weekParam = new HashMap<String, Object>();
		if(param.get("prjtFrdt") == null || param.get("prjtTodt") == null) {
			Map<String, Object> prjtInfo = prjtMapper.getPrjtInfoSimple(param);
			weekParam.put("prjtFrdt", prjtInfo.get("prjtFrdt"));
			weekParam.put("prjtTodt", prjtInfo.get("prjtTodt"));	
		}else {
			weekParam = param;
		}
		weekParam.put("maxWeekNum", Constant.maxWeekNum);
		return bdgtMapper.getMonthlyInfo(weekParam);
	}	
	@Override
	public List<WeekVO> getBudgetQuarterly(Map<String, Object> param) throws Exception{
		Map<String, Object> weekParam = new HashMap<String, Object>();
		if(param.get("prjtFrdt") == null || param.get("prjtTodt") == null) {
			Map<String, Object> prjtInfo = prjtMapper.getPrjtInfoSimple(param);
			weekParam.put("prjtFrdt", prjtInfo.get("prjtFrdt"));
			weekParam.put("prjtTodt", prjtInfo.get("prjtTodt"));	
		}else {
			weekParam = param;
		}
		
		//두 날짜간의 월 차이를 계산한다.
		String prjtFrdt = weekParam.get("prjtFrdt").toString();
		String prjtTodt = weekParam.get("prjtTodt").toString();
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate prjtFrdtDt = LocalDate.parse(prjtFrdt, format);
		LocalDate prjtTodtDt = LocalDate.parse(prjtTodt, format);
		
	    int diff = (int) ChronoUnit.MONTHS.between(prjtFrdtDt, prjtTodtDt);
	    
	    //System.out.println("두 날짜간 월 차이 :" + diff+1);
		
	    weekParam.put("diffMonth", diff+1);
	    
		weekParam.put("maxWeekNum", Constant.maxWeekNum);
			
		List<WeekVO> list = bdgtMapper.getQuarterlyInfo(weekParam);
		//마지막 row 의 마지막일자를 prjtTodt 로 적용한다.
		WeekVO vo = list.get(list.size()-1);		
		String startDate = vo.getStartDate();
		startDate = startDate.substring(0, 8) + prjtTodt.substring(0, 4) + "/" + prjtTodt.substring(4, 6);		
		vo.setStartDate(startDate);		
		//기존 마지막 row 삭제
		list.remove(list.size()-1);
		//수정된 row 삽입
		list.add(vo);
		
		return list;
	}	
	@Override
	public List<WeekVO> getBudgetAnnually(Map<String, Object> param) throws Exception{
		Map<String, Object> weekParam = new HashMap<String, Object>();
		if(param.get("prjtFrdt") == null || param.get("prjtTodt") == null) {
			Map<String, Object> prjtInfo = prjtMapper.getPrjtInfoSimple(param);
			weekParam.put("prjtFrdt", prjtInfo.get("prjtFrdt"));
			weekParam.put("prjtTodt", prjtInfo.get("prjtTodt"));	
		}else {
			weekParam = param;
		}
		
		//두 날짜간의 월 차이를 계산한다.
		String prjtFrdt = weekParam.get("prjtFrdt").toString();
		String prjtTodt = weekParam.get("prjtTodt").toString();
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate prjtFrdtDt = LocalDate.parse(prjtFrdt, format);
		LocalDate prjtTodtDt = LocalDate.parse(prjtTodt, format);
		
	    int diff = (int) ChronoUnit.MONTHS.between(prjtFrdtDt, prjtTodtDt);
	    
	    //System.out.println("두 날짜간 월 차이 :" + diff+1);
		
	    weekParam.put("diffMonth", diff+1);
	    
		weekParam.put("maxWeekNum", Constant.maxWeekNum);
		
		List<WeekVO> list = bdgtMapper.getAnnuallyInfo(weekParam);
		//마지막 row 의 마지막일자를 prjtTodt 로 적용한다.
		WeekVO vo = list.get(list.size()-1);		
		String startDate = vo.getStartDate();
		startDate = startDate.substring(0, 8) + prjtTodt.substring(0, 4) + "/" + prjtTodt.substring(4, 6);		
		vo.setStartDate(startDate);		
		//기존 마지막 row 삭제
		list.remove(list.size()-1);
		//수정된 row 삽입
		list.add(vo);
		
		return list;
	}	
	/* ===========================================================
		[종료] 20230207 Budget 입력 주기 : Budget v4에서 저장시에 최도 
	        BUDGET_INPUT_CYCLE 가 비어 있을때만 업데이트 한다.
		매주 Weekly
		월별 Monthly
		분기별 Quarterly
		매년 Annually
	   =========================================================== */	
	
	
	
	
	
	
	
	
	
}
