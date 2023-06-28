package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcLeagueTableDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * 자문 형태 지정 저장
 * ====================================================================================
 * </pre>
 */
public class DcLtSetupConsultTypeSaveServiceWorker extends AbstractServiceWorker {

	private static final String PARAM_ITEMS = "items";
	private static final String SPLIT_ITEMS = "|";
	private static final String SPLIT_DATAS = "^";
	private static final String CONFIRM_NO = "100802";

	public DcLtSetupConsultTypeSaveServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// 파라미터 items
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_ITEMS);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		DcLeagueTableDAO leagueDAO = new DcLeagueTableDAO(connection);
		DcLogDAO logDAO = new DcLogDAO(connection);

		// 파라미터
		String items = ServiceHelper.getParameter(request, PARAM_ITEMS);
		String empno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();
		String logcd = Constants.LogType.SETUP_CONSULT_TYPE;
		String insert = Constants.LogMethod.INSERT;
		
		// 업데이트 및 등록
		String[] arrItems = StringUtils.split(items, SPLIT_ITEMS);
		for (String datas : arrItems) {
			String[] arrDatas = StringUtils.split(datas, SPLIT_DATAS);
			String prjtcd = arrDatas[0];
			String conscd = arrDatas[1];
			
			// 로그 기록
			logDAO.sqlLogConsultTypeSave(prjtcd, conscd, empno);
			
			// 자문 분류 업데이트
			int result = leagueDAO.sqlUpdateSetupConsultType(prjtcd, conscd);
			if (result != 1) {
				return new ServiceError(Constants.ErrorCode.FAIL_SQL_UPDATE, Constants.ErrorMessage.FAIL_SQL_UPDATE);
			}
			// 담당자 확인 철회
			leagueDAO.sqlUpdateConfirmConsultNo(prjtcd, CONFIRM_NO);
			
			// 기존 분류 데이터 삭제 <- 미분류 상태에서 지정하는 것이므로 기존 데이터 없음
			
			// 자문 분류 등록
			result = 0;
			if (Constants.Consult.BUY.equals(conscd)) {
				result = leagueDAO.sqlInsertSetupConsultBuy(prjtcd);
				String bigo = "WEB_DCLTBUY 인수/매각 자문 테이블 등록";
				logDAO.sqlLogCommon(logcd, insert, null, null, null, null, prjtcd, bigo, empno);
				
				// 타겟 등록
				leagueDAO.sqlInsertSetupConsultBuyTarget(prjtcd);
				bigo = "WEB_DCLTBIZ 테이블 인수/매각 대상 등록";
				String actcd = Constants.Actor.BUY_TARGET;
				String bizcd = Constants.LtBiz.MAIN;
				logDAO.sqlLogLeagueBiz(prjtcd, bigo, empno, actcd, bizcd);
				
			} else if (Constants.Consult.MNA.equals(conscd)) {
				result = leagueDAO.sqlInsertSetupConsultMna(prjtcd);
				String bigo = "WEB_DCLTMNA 합병 자문 테이블 등록";
				logDAO.sqlLogCommon(logcd, insert, null, null, null, null, prjtcd, bigo, empno);
				
			} else if (Constants.Consult.REAL.equals(conscd)) {
				result = leagueDAO.sqlInsertSetupConsultReal(prjtcd);
				String bigo = "WEB_DCLTREAL 부동산 자문 테이블 등록";
				logDAO.sqlLogCommon(logcd, insert, null, null, null, null, prjtcd, bigo, empno);
				
				// 타겟 등록
				leagueDAO.sqlInsertSetupConsultRealTarget(prjtcd);
				bigo = "WEB_DCLTBIZ 테이블 부동산 대상 등록";
				String actcd = Constants.Actor.BUY_TARGET;
				String bizcd = Constants.LtBiz.MAIN;
				logDAO.sqlLogLeagueBiz(prjtcd, bigo, empno, actcd, bizcd);
			}
			if (result != 1) {
				return new ServiceError(Constants.ErrorCode.FAIL_SQL_INSERT, Constants.ErrorMessage.FAIL_SQL_INSERT);
			}
			
			// PDT관리에서 삼일회계법인 관련 정보 동기화
			leagueDAO.sqlInsertSetupConsultSamil(prjtcd);
		}

		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
}
