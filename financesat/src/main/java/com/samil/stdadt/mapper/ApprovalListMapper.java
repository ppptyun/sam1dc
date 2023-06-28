package com.samil.stdadt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.samil.stdadt.vo.ApprovalListVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryV3VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryV4VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;

@Mapper
public interface ApprovalListMapper {
	
	// 프로젝트 양식 정보	
	public List<ApprovalListVO> getProjectList(Map<String, Object> param) throws Exception;
	
	// 프로젝트 승인
	public int approveProjectInfo(Map<String, Object> param) throws Exception;	
	
	// 프로젝트 승인 시 메일 전송(수신자)
	public int approveProjectMailReceiverInsert(Map<String, Object> param) throws Exception;
	
	// 프로젝트 승인 시 메일 전송(본문)
	public int approveProjectMailContentInsert(Map<String, Object> param) throws Exception;
	
	// Summary 정보 및 Table Column 정보
	public ProjectBudgetSummaryVO getApprovalDetails(Map<String, Object> param) throws Exception;
	public ProjectBudgetSummaryV3VO getApprovalDetailsV3(Map<String, Object> param) throws Exception;
	
	public int aprvPrjtInfo(Map<String, Object> param) throws Exception;
	public int addAprvMailReceiver(Map<String, Object> param) throws Exception;
	public int addAprvMailContent(Map<String, Object> param) throws Exception;
	
	//==================================================================
	// 20230216 남웅주  2023년도 표준감사시간 개정 : Budget 입력 주기 반영
	//==================================================================
	public ProjectBudgetSummaryV4VO getApprovalDetailsV4(Map<String, Object> param) throws Exception;

}
