package com.samil.stdadt.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samil.stdadt.comm.vo.UserSessionVO;
import com.samil.stdadt.mapper.ApprovalListMapper;
import com.samil.stdadt.mapper.ProjectBudgetMapper;
import com.samil.stdadt.mapper.ProjectBudgetV4Mapper;
import com.samil.stdadt.mapper.ProjectInfoMapper;
import com.samil.stdadt.service.ApprovalService;
import com.samil.stdadt.service.HistoryService;
import com.samil.stdadt.util.Constant;
import com.samil.stdadt.vo.ApprovalListVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryV3VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryV4VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;
import com.samil.stdadt.vo.ProjectVersionVO;
import com.samil.stdadt.vo.WeekVO;


@Service
public class ApprovalServiceImpl implements ApprovalService {

	@Autowired
	ApprovalListMapper approvalList;
	
	@Autowired
	ProjectBudgetMapper approvalBudget;
	
	@Autowired
	HistoryService histService;
	
	@Autowired
	ProjectInfoMapper prjtMapper;
	
	@Autowired
	ProjectBudgetV4Mapper approvalBudgetV4;
	
	@Override
	public List<ApprovalListVO> getApprovalList(Map<String, Object> param) throws Exception {
		return approvalList.getProjectList(param);
	}

	@Override
	public ProjectBudgetSummaryVO getApprovalDetails(Map<String, Object> param) throws Exception {
				
		// 프로젝트 Summary 정보 가져오기
		ProjectBudgetSummaryVO summary = approvalList.getApprovalDetails(param);
		if(summary == null) return null;
		
		param.put("prjtFrdt", summary.getPrjtFrdt().replaceAll("-", ""));
		param.put("prjtTodt", summary.getPrjtTodt().replaceAll("-", ""));
		param.put("maxWeekNum", Constant.maxWeekNum);
				
		// 프로젝트 기간별 주 시작일 가져오기(Grid의 컬럼 정보 생성을 위함) 
		List<WeekVO> weeks = approvalBudget.getWeekInfo(param);
		if(weeks != null) summary.setsDtOfWeek(weeks);
				
		return summary;
	}
	
	@Override
	public ProjectBudgetSummaryV3VO getApprovalDetailsV3(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		
		// 프로젝트 Summary 정보 가져오기
		ProjectBudgetSummaryV3VO summary = approvalList.getApprovalDetailsV3(param);
		if(summary == null) return null;
		
		param.put("prjtFrdt", summary.getPrjtFrdt().replaceAll("-", ""));
		param.put("prjtTodt", summary.getPrjtTodt().replaceAll("-", ""));
		param.put("maxWeekNum", Constant.maxWeekNum);
				
		// 프로젝트 기간별 주 시작일 가져오기(Grid의 컬럼 정보 생성을 위함) 
		List<WeekVO> weeks = approvalBudget.getWeekInfo(param);
		if(weeks != null) summary.setsDtOfWeek(weeks);
				
		return summary;
	}
	
	@Override
	public int approveProjectInfo(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		int result = approvalList.approveProjectInfo(param);
		return result;
	}
	
	
	@Override
	public int approveProjectMailReceiverInsert(Map<String, Object> param) throws Exception{
		int result = approvalList.approveProjectMailReceiverInsert(param);
		return result;
	}
	
	@Override
	public int approveProjectMailContentInsert(Map<String, Object> param) throws Exception{
		int result = approvalList.approveProjectMailContentInsert(param);
		return result;
	}


	@Override
	public int approve(Map<String, Object> param) throws Exception {
		param.put("stat", "CO");
				
		int result1 = approvalList.aprvPrjtInfo(param);
		int result2 = approvalList.addAprvMailReceiver(param);
		int result3 = approvalList.addAprvMailContent(param);

		ProjectVersionVO prjtVersion = new ProjectVersionVO();
		prjtVersion.setAppCd((String) param.get("appCd"));
		prjtVersion.setPrjtCd((String) param.get("prjtCd"));
		prjtVersion.setVersionTy("CO");
		prjtVersion.setCreBy(((UserSessionVO)param.get("session")).getEmplNo());
		
		if(result1 == 0 || result2 == 0 || result3 == 0) {
			throw new Exception();
		}else {
			histService.addHistory(prjtVersion);
			return 1;
		}
	}

	@Override
	public int reject(Map<String, Object> param) throws Exception {
		param.put("stat", "RJ");
		
		int result1 = approvalList.aprvPrjtInfo(param);
		int result2 = approvalList.addAprvMailReceiver(param);
		int result3 = approvalList.addAprvMailContent(param);
		
		if(result1 == 0 || result2 == 0 || result3 == 0) {
			throw new Exception();
		}else {
			return 1;
		}
	}

	//==============================================================
	//[시작] 20230216 남웅주  2023년도 표준감사시간 개정 : Budget 입력 주기 반영
	//==============================================================	
	@Override
	public ProjectBudgetSummaryV4VO getApprovalDetailsV4(Map<String, Object> param) throws Exception {
		//-------------------------------------------------
		System.out.println(" getApprovalDetailsV4 서비스 파라미터 >> " + param.toString());
		//-------------------------------------------------
		
		// 프로젝트 Summary 정보 가져오기
		ProjectBudgetSummaryV4VO summary = approvalList.getApprovalDetailsV4(param);
		if(summary == null) return null;
		
		param.put("prjtFrdt", summary.getPrjtFrdt().replaceAll("-", ""));
		param.put("prjtTodt", summary.getPrjtTodt().replaceAll("-", ""));
		param.put("maxWeekNum", Constant.maxWeekNum);

		List<WeekVO> vo = null;
		//20230207 Budget 입력주기
		String budgetInputCycle = (param.get("budgetInputCycle")==null)?"Weekly":param.get("budgetInputCycle").toString();
		// 프로젝트 기간별 주 시작일 가져오기(Grid의 컬럼 정보 생성을 위함)
		if("Weekly".equals(budgetInputCycle)) {
			vo = approvalBudgetV4.getWeekInfo(param);
		}else if("Monthly".equals(budgetInputCycle)) {
			vo = approvalBudgetV4.getMonthlyInfo(param);
		}else if("Quarterly".equals(budgetInputCycle)) {
			//두 날짜간의 월 차이를 계산한다.
			String prjtFrdt = param.get("prjtFrdt").toString();
			String prjtTodt = param.get("prjtTodt").toString();
			
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDate prjtFrdtDt = LocalDate.parse(prjtFrdt, format);
			LocalDate prjtTodtDt = LocalDate.parse(prjtTodt, format);
			
		    int diff = (int) ChronoUnit.MONTHS.between(prjtFrdtDt, prjtTodtDt);
		    
		    System.out.println("두 날짜간 월 차이 :" + diff+1);
			
		    param.put("diffMonth", diff+1);
			List<WeekVO> list = approvalBudgetV4.getQuarterlyInfo(param);
			//마지막 row 의 마지막일자를 prjtTodt 로 적용한다.
			WeekVO vo2 = list.get(list.size()-1);		
			String startDate = vo2.getStartDate();
			startDate = startDate.substring(0, 8) + prjtTodt.substring(0, 4) + "/" + prjtTodt.substring(4, 6);		
			vo2.setStartDate(startDate);		
			//기존 마지막 row 삭제
			list.remove(list.size()-1);
			//수정된 row 삽입
			list.add(vo2);
			
			vo = list;			
		}else if("Annually".equals(budgetInputCycle)) {
			//두 날짜간의 월 차이를 계산한다.
			String prjtFrdt = param.get("prjtFrdt").toString();
			String prjtTodt = param.get("prjtTodt").toString();
			
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDate prjtFrdtDt = LocalDate.parse(prjtFrdt, format);
			LocalDate prjtTodtDt = LocalDate.parse(prjtTodt, format);
			
		    int diff = (int) ChronoUnit.MONTHS.between(prjtFrdtDt, prjtTodtDt);
		    
		    System.out.println("두 날짜간 월 차이 :" + diff+1);
			
		    param.put("diffMonth", diff+1);
			
			List<WeekVO> list = approvalBudgetV4.getAnnuallyInfo(param);
			//마지막 row 의 마지막일자를 prjtTodt 로 적용한다.
			WeekVO vo2 = list.get(list.size()-1);		
			String startDate = vo2.getStartDate();
			startDate = startDate.substring(0, 8) + prjtTodt.substring(0, 4) + "/" + prjtTodt.substring(4, 6);		
			vo2.setStartDate(startDate);		
			//기존 마지막 row 삭제
			list.remove(list.size()-1);
			//수정된 row 삽입
			list.add(vo2);			
			
			vo = list;	
		}
		
		if(vo != null) summary.setsDtOfWeek(vo);
				
		return summary;
	}	
	//==============================================================
	//[종료] 20230216 남웅주  2023년도 표준감사시간 개정 : Budget 입력 주기 반영
	//==============================================================	
	
}
	
		
