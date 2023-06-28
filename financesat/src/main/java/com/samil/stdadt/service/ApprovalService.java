package com.samil.stdadt.service;

import java.util.List;
import java.util.Map;

import com.samil.stdadt.vo.ApprovalListVO;
import com.samil.stdadt.vo.ProjectBudgetSummaryV3VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryV4VO;
import com.samil.stdadt.vo.ProjectBudgetSummaryVO;

public interface ApprovalService {
	public List<ApprovalListVO> getApprovalList(Map<String, Object> param) throws Exception;
	
	public ProjectBudgetSummaryVO getApprovalDetails(Map<String, Object> param) throws Exception;
	public ProjectBudgetSummaryV3VO getApprovalDetailsV3(Map<String, Object> param) throws Exception;
	
	public int approveProjectInfo(Map<String, Object> param) throws Exception;
	public int approveProjectMailReceiverInsert(Map<String, Object> param) throws Exception;
	public int approveProjectMailContentInsert(Map<String, Object> param) throws Exception;
	
	public int approve(Map<String, Object> param) throws Exception;
	public int reject(Map<String, Object> param) throws Exception;

	//==============================================================
	// 20230216 남웅주  2023년도 표준감사시간 개정 : Budget 입력 주기 반영
	//==============================================================
	public ProjectBudgetSummaryV4VO getApprovalDetailsV4(Map<String, Object> param) throws Exception;

}
