package com.samil.dc.service.worker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.DcCredentialDAO;
import com.samil.dc.domain.DcCredentialAddBean;
import com.samil.dc.domain.DcCredentialBean;
import com.samil.dc.domain.DcCredentialBizBean;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;

/**
 * <pre>
 * ====================================================================================
 * Credential 변경 정보 저장
 * ====================================================================================
 * </pre>
 */
public class DcCredentialSaveServiceWorker extends AbstractServiceWorker {

	private final static String PARAM_PRJTCD = "prjtcd";
	/** BRS 매각 채권 구분 */
	private final static String BOND_CTG_CD = "127";

	public DcCredentialSaveServiceWorker(HttpServletRequest request, HttpServletResponse response, DBConnection connection) {
		super(request, response, connection);
	}

	@Override
	public ValidationResult validate() throws Exception {
		// 사용자 검증
		ValidationResult validationResult = ServiceHelper.validateUser(request);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		validationResult = ServiceHelper.validateParameterBlank(request, PARAM_PRJTCD);
		if (!validationResult.isValid()) {
			return validationResult;
		}

		return new ValidationResult(true);
	}

	@Override
	public Object process() throws Exception {
		String prjtcd = ServiceHelper.getParameter(request, PARAM_PRJTCD);
		String empno = SessionUtil.getUserSession(request).getUserBeans().getEMPLNO();

		DcCredentialDAO credDAO = new DcCredentialDAO(connection);
		DcCredentialBean befProject = null;

		try {
			// 프로젝트 정보 조회
			Object rtnObj = ServiceHelper.getCredentialProject(credDAO, prjtcd);
			if (rtnObj instanceof ServiceError) {
				return (ServiceError) rtnObj;
			} else if (rtnObj instanceof DcCredentialBean) {
				befProject = (DcCredentialBean) rtnObj;
			} else {
				return new ServiceError(Constants.ErrorCode.INTERNAL, Constants.ErrorMessage.INTERNAL);
			}

			// 사용자 입력 파라미터 정보 조회
			DcCredentialBean altProject = ParameterHelper.getParameterCredentialBean(request);

			// 삭제기업정보 - Credential 편집에서는 없음

			// 기본 정보 변경 수행
			int result = 0;
			result = credDAO.sqlUpdateCredentialBase(altProject);
			if (result != 1) {
				return new ServiceError(Constants.ErrorCode.FAIL_SQL_UPDATE, Constants.ErrorMessage.FAIL_SQL_UPDATE);
			}

			// 클라이언트 국가 정보 변경 수행
			result = credDAO.sqlUpdateCredentialClientNat(altProject);
			if (result != 1) {
				return new ServiceError(Constants.ErrorCode.FAIL_SQL_UPDATE, Constants.ErrorMessage.FAIL_SQL_UPDATE);
			}
			
			// 대상(Target) 정보 변경 수행
			for (DcCredentialBizBean target : altProject.getTarget()) {
				String seq = target.getSeq();
				if (StringUtils.isBlank(seq)) {
					// 신규 등록
					if (!StringUtils.isBlank(target.getCompcd())) {
						credDAO.sqlInsertCredentialBiz(target);
					}
				} else {
					// 수정
					credDAO.sqlUpdateCredentialBiz(target);
				}
			}

			// 이해관계자 정보 변경 수행
			if (altProject.getInterest() != null) {
				DcCredentialBizBean interest = altProject.getInterest();
				String seq = interest.getSeq();
				if (StringUtils.isBlank(seq)) {
					// 신규 등록
					if (!StringUtils.isBlank(interest.getCompcd())) {
						credDAO.sqlInsertCredentialBiz(interest);
					}
				} else {
					// 수정
					credDAO.sqlUpdateCredentialBiz(interest);
				}
			}
			
			// 담보/무담보 채권 정보 변경 수행
			credDAO.sqlDeleteCredentialAddAll(prjtcd);
			if (altProject.getBondsecure() != null && altProject.getBondsecure().size() > 0) {
				DcCredentialAddBean ctgAddBean = new DcCredentialAddBean();
				ctgAddBean.setPrjtcd(prjtcd);
				ctgAddBean.setItemcd(Constants.BondType.SECURE);
				ctgAddBean.setCtgcd(BOND_CTG_CD);
				credDAO.sqlInsertCredentialAdd(ctgAddBean);

				for (DcCredentialAddBean item : altProject.getBondsecure()) {
					credDAO.sqlInsertCredentialAdd(item);
				}
			}
			if (altProject.getBondsecurenone() != null && altProject.getBondsecurenone().size() > 0) {
				DcCredentialAddBean ctgAddBean = new DcCredentialAddBean();
				ctgAddBean.setPrjtcd(prjtcd);
				ctgAddBean.setItemcd(Constants.BondType.NONE_SECURE);
				ctgAddBean.setCtgcd(BOND_CTG_CD);
				credDAO.sqlInsertCredentialAdd(ctgAddBean);

				for (DcCredentialAddBean item : altProject.getBondsecurenone()) {
					credDAO.sqlInsertCredentialAdd(item);
				}
			}
			
			// BRS 매수처 정보 변경 수행
			if (altProject.getBrsbuyer() != null) {
				DcCredentialBizBean brsbuyer = altProject.getBrsbuyer();
				String seq = brsbuyer.getSeq();
				if (StringUtils.isBlank(seq)) {
					// 신규 등록
					if (!StringUtils.isBlank(brsbuyer.getCompcd())) {
						credDAO.sqlInsertCredentialBiz(brsbuyer);
					}
				} else {
					// 수정
					credDAO.sqlUpdateCredentialBiz(brsbuyer);
				}
			}
			
			// RCF 정보 변경 수행
			if (altProject.getRcf() != null) {
				DcCredentialBizBean rcf = altProject.getRcf();
				String seq = rcf.getSeq();
				if (StringUtils.isBlank(seq)) {
					// 신규 등록
					if (!StringUtils.isBlank(rcf.getNatcd()) || !StringUtils.isBlank(rcf.getCity())) {
						credDAO.sqlInsertCredentialBiz(rcf);
					}
				} else {
					// 수정
					credDAO.sqlUpdateCredentialBiz(rcf);
				}
			}

			// 변경 로그
			LogHelper logHelper = new LogHelper(connection);
			logHelper.doLogCredential(befProject, altProject, empno);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}
}
