package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcLeagueTableDAO;
import com.samil.dc.dao.DcLogDAO;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * League Table 자문 형태 변경
 * ====================================================================================
 * </pre>
 */
public class DcLtConsultTypeChangeServiceWorker extends AbstractServiceWorker {

	private static final String PARAM_PRJTCD = "prjtcd";
	private static final String PARAM_BEF_CONSCD = "befconscd";
	private static final String PARAM_ALT_CONSCD = "altconscd";
	private static final String CONFIRM_NO = "100802";

	public DcLtConsultTypeChangeServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// 파라미터 prjtcd
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_PRJTCD);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// 파라미터 befconscd 기존 자문형태 코드
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_BEF_CONSCD);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		// 파라미터 altconscd 변경 자문형태 코드
		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_ALT_CONSCD);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		String befconscd = ServiceHelper.getParameter(request, PARAM_BEF_CONSCD);
		String altconscd = ServiceHelper.getParameter(request, PARAM_ALT_CONSCD);
		// 변경하고자 하는 자문형태가 '미분류'인지 체크
		if (Constants.Consult.NONE.equals(altconscd)) {
			validationResult = new ValidationResult(Constants.ErrorCode.NOT_VALID_PARAMETER_TYPE, "'미분류'로 변경할 수 없습니다.");
			validationResult.setErrorParameterAndValue(PARAM_ALT_CONSCD, altconscd);
			return validationResult;
		}
		// 기존 자문형태와 변경하고자 하는 자문형태가 동일한지 체크
		if (befconscd.equals(altconscd)) {
			validationResult = new ValidationResult(Constants.ErrorCode.EQUAL_PARAMETER, "동일한 자문형태로 변경할 수 없습니다.");
			validationResult.setErrorParameterAndValue(PARAM_ALT_CONSCD, altconscd);
			return validationResult;
		}

		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		DcLeagueTableDAO leagueDAO = new DcLeagueTableDAO(connection);
		DcLogDAO logDAO = new DcLogDAO(connection);

		// 파라미터
		String prjtcd = ServiceHelper.getParameter(request, PARAM_PRJTCD);
		String befconscd = ServiceHelper.getParameter(request, PARAM_BEF_CONSCD);
		String altconscd = ServiceHelper.getParameter(request, PARAM_ALT_CONSCD);
		String empno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();
		String logcd = Constants.LogType.SETUP_CONSULT_TYPE;
		String insert = Constants.LogMethod.INSERT;

		// 업데이트 및 등록
		// 로그 기록
		logDAO.sqlLogConsultTypeSave(prjtcd, altconscd, empno);

		// 자문 분류 업데이트
		int result = leagueDAO.sqlUpdateSetupConsultType(prjtcd, altconscd);
		if (result != 1) {
			return new ServiceError(Constants.ErrorCode.FAIL_SQL_UPDATE, Constants.ErrorMessage.FAIL_SQL_UPDATE);
		}
		// 담당자 확인 철회
		leagueDAO.sqlUpdateConfirmConsultNo(prjtcd, CONFIRM_NO);

		// 기존 분류 데이터 삭제
		if (Constants.Consult.BUY.equals(befconscd)) {
			leagueDAO.sqlDeleteLeagueTableBuy(prjtcd);
			leagueDAO.sqlDeleteLeagueTableBuyDeal1(prjtcd);
		} else if (Constants.Consult.MNA.equals(befconscd)) {
			leagueDAO.sqlDeleteLeagueTableMna(prjtcd);
		} else if (Constants.Consult.REAL.equals(befconscd)) {
			leagueDAO.sqlDeleteLeagueTableReal(prjtcd);
		}
		leagueDAO.sqlDeleteLeagueTableBizAll(prjtcd);

		// 자문 분류 등록
		result = 0;
		if (Constants.Consult.BUY.equals(altconscd)) {
			result = leagueDAO.sqlInsertSetupConsultBuy(prjtcd);
			String bigo = "WEB_DCLTBUY 인수/매각 자문 테이블 등록";
			logDAO.sqlLogCommon(logcd, insert, null, null, null, null, prjtcd, bigo, empno);

			// 타겟 등록
			leagueDAO.sqlInsertSetupConsultBuyTarget(prjtcd);
			bigo = "WEB_DCLTBIZ 테이블 인수/매각 대상 등록";
			String actcd = Constants.Actor.BUY_TARGET;
			String bizcd = Constants.LtBiz.MAIN;
			logDAO.sqlLogLeagueBiz(prjtcd, bigo, empno, actcd, bizcd);

		} else if (Constants.Consult.MNA.equals(altconscd)) {
			result = leagueDAO.sqlInsertSetupConsultMna(prjtcd);
			String bigo = "WEB_DCLTMNA 합병 자문 테이블 등록";
			logDAO.sqlLogCommon(logcd, insert, null, null, null, null, prjtcd, bigo, empno);

		} else if (Constants.Consult.REAL.equals(altconscd)) {
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

		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
}
