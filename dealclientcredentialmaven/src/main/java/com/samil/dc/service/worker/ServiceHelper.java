package com.samil.dc.service.worker;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.samil.dc.dao.DcCredentialDAO;
import com.samil.dc.dao.DcLeagueTableDAO;
import com.samil.dc.domain.AdminEditAuthListBean;
import com.samil.dc.domain.CommonCodeListBean;
import com.samil.dc.domain.DcCredentialAddBean;
import com.samil.dc.domain.DcCredentialBaseDetailBean;
import com.samil.dc.domain.DcCredentialBean;
import com.samil.dc.domain.DcCredentialBizBean;
import com.samil.dc.domain.DcCredentialSearchConditionBean;
import com.samil.dc.domain.DcLtBizBean;
import com.samil.dc.domain.DcLtBuyBean;
import com.samil.dc.domain.DcLtDealListBean;
import com.samil.dc.domain.DcLtMnaBean;
import com.samil.dc.domain.DcLtRealBean;
import com.samil.dc.domain.UserRoleBeans;
import com.samil.dc.domain.UserSessionBeans;
import com.samil.dc.sql.SQLManagement;
import com.samil.dc.util.Constants;
import com.samil.dc.util.SessionUtil;
import com.samil.dc.util.Constants.CredentialParam;
import com.samil.dc.util.Constants.CredentialTargetYN;
import com.samil.dc.util.CustomRuntimeException;

public final class ServiceHelper {
	
	// XSS(Cross Site Scripting) 방지를 위한 패턴 배열
	private static final Pattern[] xssPatterns = new Pattern[] {
		Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
		Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
		Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
		Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
		Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
	};
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 파라미터 조회(XSS 제거 포함)
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String parameterName) {
		if (StringUtils.isBlank(parameterName)) {
			return null;
		}
		String parameterValue;
		try {
			parameterValue = URLDecoder.decode(request.getParameter(parameterName), "utf-8");
		} catch (Exception e){
			parameterValue = request.getParameter(parameterName);
		}
		return stripXSS(parameterValue);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Bean 객체 프로퍼티 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param bean
	 * @param property
	 * @return
	 */
	public static String getBeanProperty(Object bean, String property) {
		Map<String, String> beanMap = null;
		try {
			beanMap = BeanUtils.describe(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (beanMap != null) {
			return StringUtils.defaultIfBlank(beanMap.get(property), "");
		}
		return "";
	}
	
	public static Map<String, String> getBeanMap(Object bean) {
		Map<String, String> beanMap = null;
		try {
			beanMap = BeanUtils.describe(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beanMap;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Bean 객체 프로퍼티 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param beanMap
	 * @param property
	 * @return
	 */
	public static String getBeanMapProperty(Map<String, String> beanMap, String property) {
		if (beanMap != null) {
			return StringUtils.defaultIfBlank(beanMap.get(property), "");
		}
		return "";
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 사용자 세션 검증
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static ValidationResult validateUser(HttpServletRequest request) {
		UserSessionBeans userSessionBeans = SessionUtil.getUserSession(request);
		
		// 세션 정보가 없을 경우 갱신
		if (userSessionBeans == null) {
			userSessionBeans = SessionUtil.refreshUserSession(request);
		}

		if (userSessionBeans == null) {
			ValidationResult validationResult = new ValidationResult(Constants.ErrorCode.NOT_FOUND_USER, Constants.ErrorMessage.NOT_FOUND_USER);
			return validationResult;
		}
		
		if (userSessionBeans.getUserBeans() == null) {
			ValidationResult validationResult = new ValidationResult(Constants.ErrorCode.NOT_FOUND_USER, Constants.ErrorMessage.NOT_FOUND_USER);
			return validationResult;
		}

		try {
			if (userSessionBeans.getUserRoleList().size() == 0) {
				ValidationResult validationResult = new ValidationResult(Constants.ErrorCode.NOT_FOUND_ROLE, Constants.ErrorMessage.NOT_FOUND_ROLE);
				return validationResult;
			}
		} catch (Exception e) {
		}

		return new ValidationResult(true);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 프로젝트 코드 검증
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 */
	public static boolean isValidProjectCode(String prjtcd) {
		if (StringUtils.isBlank(prjtcd)) {
			return false;
		}
		if (StringUtils.length(prjtcd) != 12) {
			return false;
		}
		// 가상 프로젝트 코드가 포함되어 있을 수 있음
		String _temp = StringUtils.replace(String.valueOf(prjtcd), "-", "");
		_temp = _temp.substring(1);
		if (!StringUtils.isNumeric(_temp)) {
			return false;
		}
		return true;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 사번 검증
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param empNo
	 * @return
	 */
	public static boolean isValidEmpno(String empNo) {
		// 6자리
		if (StringUtils.length(empNo) != 6) {
			return false;
		}
		// only 숫자
		if (!StringUtils.isNumeric(empNo)) {
			return false;
		}
		return true;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 사원번호 좌측 채우기 ['12345' -> '012345']
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param empNo
	 * @return
	 */
	public static String doLPadEmpno(String empNo) {
		if (StringUtils.isBlank(empNo)) {
			return "";
		}
		return StringUtils.leftPad(empNo, 6, "0");
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 날짜 변환[8자리만 변환]
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param date
	 * @return
	 */
	public static String doConvertStrictDateFormat(String date, String separator) {
		if (StringUtils.isBlank(date)) {
			return "";
		}
		if (date.length() != 8) {
			return date;
		}
		
		String copyDate = String.valueOf(date);
		StringBuffer sb = new StringBuffer();
		sb.append(StringUtils.substring(copyDate, 0, 4)).append(separator);
		sb.append(StringUtils.substring(copyDate, 4, 6)).append(separator);
		sb.append(StringUtils.substring(copyDate, 6, 8));
		return sb.toString();
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 파라미터 유효성 체크(XSS 제거 포함)
	 * 
	 * 1.파라미터 존재 유무 체크
	 * 2.파라미터 값이 있는지 체크
	 * 3.파라미터 값이 목록에 포함되어 있는지 체크
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public static ValidationResult validateParameterInList(HttpServletRequest request, String parameterName, List<String> list) {
		String parameterValue = getParameter(request, parameterName);

		if (parameterValue == null) {
			ValidationResult validationResult = new ValidationResult(Constants.ErrorCode.NOT_FOUND_PARAMETER, Constants.ErrorMessage.NOT_FOUND_PARAMETER);
			validationResult.setErrorParameterAndValue(parameterName, "");
			return validationResult;
		}

		if ("".equals(parameterValue)) {
			ValidationResult validationResult = new ValidationResult(Constants.ErrorCode.EMPTY_PARAMETER, Constants.ErrorMessage.EMPTY_PARAMETER);
			validationResult.setErrorParameterAndValue(parameterName, parameterValue);
			return validationResult;
		}
		
		if (!list.contains(parameterValue)){
			ValidationResult validationResult = new ValidationResult(Constants.ErrorCode.NOT_VALID_PARAMETER_VALUE, Constants.ErrorMessage.NOT_VALID_PARAMETER_VALUE);
			validationResult.setErrorParameterAndValue(parameterName, parameterValue);
			return validationResult;
		}

		return new ValidationResult(true);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 파라미터 유효성 체크(XSS 제거 포함)
	 * 
	 * 1.파라미터 존재 유무 체크
	 * 2.파라미터 값이 있는지 체크
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public static ValidationResult validateParameterBlank(HttpServletRequest request, String parameterName) {
		String parameterValue = getParameter(request, parameterName);

		if (parameterValue == null) {
			ValidationResult validationResult = new ValidationResult(Constants.ErrorCode.NOT_FOUND_PARAMETER, Constants.ErrorMessage.NOT_FOUND_PARAMETER);
			validationResult.setErrorParameterAndValue(parameterName, "");
			return validationResult;
		}

		if ("".equals(parameterValue)) {
			ValidationResult validationResult = new ValidationResult(Constants.ErrorCode.EMPTY_PARAMETER, Constants.ErrorMessage.EMPTY_PARAMETER);
			validationResult.setErrorParameterAndValue(parameterName, parameterValue);
			return validationResult;
		}

		return new ValidationResult(true);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 파라미터 유효성 체크(XSS 제거 포함)
	 * 
	 * 1.파라미터 존재 유무 체크
	 * 2.파라미터 값이 있는지 체크
	 * 3.파라미터 디코딩 체크
	 * 4.파라미터 JSON 파싱 체크
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public static ValidationResult validateParameterJSON(HttpServletRequest request, String parameterName){
		String parameterValue = getParameter(request, parameterName);

		if (parameterValue == null) {
			ValidationResult validationResult = new ValidationResult(Constants.ErrorCode.NOT_FOUND_PARAMETER, Constants.ErrorMessage.NOT_FOUND_PARAMETER);
			validationResult.setErrorParameterAndValue(parameterName, "");
			return validationResult;
		}

		if ("".equals(parameterValue)) {
			ValidationResult validationResult = new ValidationResult(Constants.ErrorCode.EMPTY_PARAMETER, Constants.ErrorMessage.EMPTY_PARAMETER);
			validationResult.setErrorParameterAndValue(parameterName, parameterValue);
			return validationResult;
		}
		
		String decodeValue = "";
		try {
			decodeValue = URLDecoder.decode(parameterValue, "utf-8");
			JSONParser jsonParser = new JSONParser();
			jsonParser.parse(decodeValue);
		} catch (UnsupportedEncodingException e) {
			ValidationResult validationResult = new ValidationResult(Constants.ErrorCode.FAIL_DECODE_PARAMETER, Constants.ErrorMessage.FAIL_DECODE_PARAMETER);
			validationResult.setErrorParameterAndValue(parameterName, parameterValue);
			return validationResult;
		} catch (ParseException e) {
			ValidationResult validationResult = new ValidationResult(Constants.ErrorCode.NOT_VALID_PARAMETER_TYPE, Constants.ErrorMessage.NOT_VALID_PARAMETER_TYPE);
			validationResult.setErrorParameterAndValue(parameterName, parameterValue);
			return validationResult;
		}
		
		return new ValidationResult(true);
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 파라미터 유효성 체크(XSS 제거 포함)
	 * 
	 * 1.파라미터 존재 유무 체크
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public static ValidationResult validateParameterExist(HttpServletRequest request, String parameterName) {
		String parameterValue = getParameter(request, parameterName);

		if (parameterValue == null) {
			ValidationResult validationResult = new ValidationResult(Constants.ErrorCode.NOT_FOUND_PARAMETER, Constants.ErrorMessage.NOT_FOUND_PARAMETER);
			validationResult.setErrorParameterAndValue(parameterName, "");
			return validationResult;
		}

		return new ValidationResult(true);
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * 서비스 로직 수행 결과값 얻기
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param success
	 * @param data
	 * @return
	 */
	public static Map<String, Object> generateServiceResult(boolean success, Object data) {
		Map<String, Object> workResult = new HashMap<String, Object>();

		workResult.put("result", Boolean.valueOf(success));
		if (success) {
			workResult.put("data", data);
		} else {
			workResult.put("error", data);
		}

		return workResult;
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * XSS(Cross Site Scripting) 취약점 제거
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param value
	 * @return
	 */
	public static String stripXSS(String value) {
		if (value == null) {
			return null;
		}

		String stripValue = String.valueOf(value);

		// null 문자 제거
		stripValue = stripValue.replaceAll("\0", "");

		// xss 패턴을 포함하는 입력에 대해 '<', '>'을 인코딩한다.
		for (Pattern xssPattern : xssPatterns) {
			if (xssPattern.matcher(stripValue).find()) {
				stripValue = stripValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			}
		}

		return stripValue;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * '2016-11-15' -> '20161115' 변환
	 * '2016.11.15' -> '20161115' 변환
	 * '2016/11/15' -> '20161115' 변환
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param strDate
	 * @return
	 */
	public static String stripForDateFormat(String strDate) {
		String newStrDate = String.valueOf(strDate);
		newStrDate = StringUtils.replace(newStrDate, "-", "");
		newStrDate = StringUtils.replace(newStrDate, ".", "");
		newStrDate = StringUtils.replace(newStrDate, "/", "");
		return newStrDate;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 리스트 변환
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param src
	 * @param separatorChars
	 * @return
	 */
	public static List<String> convertStringToList(String src, String separatorChars) {
		List<String> list = new ArrayList<String>();
		if (!StringUtils.isBlank(src) && !StringUtils.isBlank(separatorChars)) {
			String[] arrays = StringUtils.split(src, separatorChars);
			if (arrays != null && arrays.length > 0) {
				for (String item : arrays) {
					list.add(item);
				}
			}
		}
		return list;
	}
	
	// 사용자 롤 중복 제거
	public static List<UserRoleBeans> getUniqueRoleList(List<UserRoleBeans> pool) {
		List<UserRoleBeans> uniqueList = new ArrayList<UserRoleBeans>();
		for (UserRoleBeans role : pool) {
			if (!existRole(role.getRolecd(), uniqueList)) {
				uniqueList.add(role);
			}
		}
		return uniqueList;
	}
	
	// 사용자 관리 본부 중복 제거
	public static List<String> getUniqueHQList(List<String> pool) {
		List<String> uniqueList = new ArrayList<String>();
		for (String item : pool) {
			if (!existHQ(item, uniqueList)) {
				uniqueList.add(item);
			}
		}
		return uniqueList;
	}
	
	private static boolean existRole(String rolecd, List<UserRoleBeans> pool) {
		for (UserRoleBeans role : pool) {
			if (rolecd.equals(role.getRolecd())) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean existHQ(String hqcd, List<String> pool) {
		for (String item : pool) {
			if (hqcd.equals(item)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isExistIndexArray(int index, String[] array) {
		if (array == null) {
			return false;
		}
		return (array.length - 1) >= index;
	}
	
	public static String getBizDivCdNonConsult(String compcd) {
		String _compcd = StringUtils.defaultString(compcd);
		String bizdivcd = "";
		switch (_compcd.length()) {
		case 0:
			bizdivcd = "";
			break;
		case 1:
			bizdivcd = Constants.BizSrc.MANUAL;
			break;
		case 5:
			bizdivcd = Constants.BizSrc.SAMIL;
			break;
		case 6:
			bizdivcd = Constants.BizSrc.KISLINE;
			break;
		}
		return bizdivcd;
	}
	
	public static String getBizDivCdConsult(String compcd) {
		String _compcd = StringUtils.defaultString(compcd);
		String bizdivcd = "";
		switch (_compcd.length()) {
		case 0:
			bizdivcd = "";
			break;
		case 1:
			bizdivcd = Constants.BizSrc.MANUAL;
			break;
		default:
			bizdivcd = Constants.BizSrc.CONSULT;
		}
		return bizdivcd;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 신규/수정 기업정보 분류하기
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @param newBizList
	 * @param updBizList
	 */
	public static void doParseNewUpdBizList(DcLtBuyBean project, List<DcLtBizBean> newBizList, List<DcLtBizBean> updBizList) {
		List<DcLtBizBean> bizList = new ArrayList<DcLtBizBean>();
		bizList.addAll(project.getTarget());
		bizList.addAll(project.getSeller());
		bizList.addAll(project.getSellfinance());
		bizList.addAll(project.getSellaudit());
		bizList.addAll(project.getSelllaw());
		bizList.addAll(project.getBuyer());
		bizList.addAll(project.getBuyfinance());
		bizList.addAll(project.getBuyaudit());
		bizList.addAll(project.getBuylaw());
		for (DcLtBizBean biz : bizList) {
			if (StringUtils.isBlank(biz.getSeq())) {
				newBizList.add(biz);
			} else {
				updBizList.add(biz);
			}
		}
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 신규/수정 기업정보 분류하기
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @param newBizList
	 * @param updBizList
	 */
	public static void doParseNewUpdBizList(DcLtMnaBean project, List<DcLtBizBean> newBizList, List<DcLtBizBean> updBizList) {
		List<DcLtBizBean> bizList = new ArrayList<DcLtBizBean>();
		bizList.addAll(project.getMerging());
		bizList.addAll(project.getMergingfinance());
		bizList.addAll(project.getMergingaudit());
		bizList.addAll(project.getMerginglaw());
		bizList.addAll(project.getMerged());
		bizList.addAll(project.getMergedfinance());
		bizList.addAll(project.getMergedaudit());
		bizList.addAll(project.getMergedlaw());
		for (DcLtBizBean biz : bizList) {
			if (StringUtils.isBlank(biz.getSeq())) {
				newBizList.add(biz);
			} else {
				updBizList.add(biz);
			}
		}
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 신규/수정 기업정보 분류하기
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @param newBizList
	 * @param updBizList
	 */
	public static void doParseNewUpdBizList(DcLtRealBean project, List<DcLtBizBean> newBizList, List<DcLtBizBean> updBizList) {
		List<DcLtBizBean> bizList = new ArrayList<DcLtBizBean>();
		bizList.addAll(project.getTarget());
		bizList.addAll(project.getSeller());
		bizList.addAll(project.getSellfinance());
		bizList.addAll(project.getSellaudit());
		bizList.addAll(project.getSelllaw());
		bizList.addAll(project.getBuyer());
		bizList.addAll(project.getBuyfinance());
		bizList.addAll(project.getBuyaudit());
		bizList.addAll(project.getBuylaw());
		for (DcLtBizBean biz : bizList) {
			if (StringUtils.isBlank(biz.getSeq())) {
				newBizList.add(biz);
			} else {
				updBizList.add(biz);
			}
		}
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 자문 프로젝트 정보 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param leagueDAO
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public static Object getLeagueTableBuyProject(DcLeagueTableDAO leagueDAO, String prjtcd) throws SQLException {
		DcLtBuyBean project = null;
		
		try {
			project = leagueDAO.sqlSelectConsultBuy(prjtcd);
			if (project == null) {
				return new ServiceError(Constants.ErrorCode.NOT_FOUND_PROJECT, Constants.ErrorMessage.NOT_FOUND_PROJECT);
			}
			
			// 거래유형1
			List<DcLtDealListBean> dealList = leagueDAO.sqlSelectLeagueBuyDeal1List(prjtcd);
			if (dealList == null) {
				dealList = new ArrayList<DcLtDealListBean>();
			}
			project.setDeal1(dealList);
			
			// 기업정보 분류
			List<DcLtBizBean> bizList = leagueDAO.sqlSelectLeagueBizList(prjtcd);
			if (bizList == null) {
				bizList = new ArrayList<DcLtBizBean>();
			}
			for (DcLtBizBean biz : bizList) {
				if (Constants.Actor.BUY_TARGET.equals(biz.getActcd())) {
					if (Constants.LtBiz.MAIN.equals(biz.getBizcd())) {
						project.getTarget().add(biz);
					}
				} else if (Constants.Actor.BUY_SELLER.equals(biz.getActcd())) {
					if (Constants.LtBiz.MAIN.equals(biz.getBizcd())) {
						project.getSeller().add(biz);
					} else if (Constants.LtBiz.FINANCE.equals(biz.getBizcd())) {
						project.getSellfinance().add(biz);
					} else if (Constants.LtBiz.AUDIT.equals(biz.getBizcd())) {
						project.getSellaudit().add(biz);
					} else if (Constants.LtBiz.LAW.equals(biz.getBizcd())) {
						project.getSelllaw().add(biz);
					}
				} else if (Constants.Actor.BUY_BUYER.equals(biz.getActcd())) {
					if (Constants.LtBiz.MAIN.equals(biz.getBizcd())) {
						project.getBuyer().add(biz);
					} else if (Constants.LtBiz.FINANCE.equals(biz.getBizcd())) {
						project.getBuyfinance().add(biz);
					} else if (Constants.LtBiz.AUDIT.equals(biz.getBizcd())) {
						project.getBuyaudit().add(biz);
					} else if (Constants.LtBiz.LAW.equals(biz.getBizcd())) {
						project.getBuylaw().add(biz);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return project;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 자문 프로젝트 정보 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param leagueDAO
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public static Object getLeagueTableMnaProject(DcLeagueTableDAO leagueDAO, String prjtcd) throws SQLException {
		DcLtMnaBean project = null;
		
		try {
			project = leagueDAO.sqlSelectConsultMna(prjtcd);
			if (project == null) {
				return new ServiceError(Constants.ErrorCode.NOT_FOUND_PROJECT, Constants.ErrorMessage.NOT_FOUND_PROJECT);
			}

			// 기업정보 분류
			List<DcLtBizBean> bizList = leagueDAO.sqlSelectLeagueBizList(prjtcd);
			if (bizList == null) {
				bizList = new ArrayList<DcLtBizBean>();
			}
			for (DcLtBizBean biz : bizList) {
				if (Constants.Actor.MNA_MERGING.equals(biz.getActcd())) {
					if (Constants.LtBiz.MAIN.equals(biz.getBizcd())) {
						project.getMerging().add(biz);
					} else if (Constants.LtBiz.FINANCE.equals(biz.getBizcd())) {
						project.getMergingfinance().add(biz);
					} else if (Constants.LtBiz.AUDIT.equals(biz.getBizcd())) {
						project.getMergingaudit().add(biz);
					} else if (Constants.LtBiz.LAW.equals(biz.getBizcd())) {
						project.getMerginglaw().add(biz);
					}
				} else if (Constants.Actor.MNA_MERGED.equals(biz.getActcd())) {
					if (Constants.LtBiz.MAIN.equals(biz.getBizcd())) {
						project.getMerged().add(biz);
					} else if (Constants.LtBiz.FINANCE.equals(biz.getBizcd())) {
						project.getMergedfinance().add(biz);
					} else if (Constants.LtBiz.AUDIT.equals(biz.getBizcd())) {
						project.getMergedaudit().add(biz);
					} else if (Constants.LtBiz.LAW.equals(biz.getBizcd())) {
						project.getMergedlaw().add(biz);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return project;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 자문 프로젝트 정보 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param leagueDAO
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public static Object getLeagueTableRealProject(DcLeagueTableDAO leagueDAO, String prjtcd) throws SQLException {
		DcLtRealBean project = null;
		
		try {
			project = leagueDAO.sqlSelectConsultReal(prjtcd);
			if (project == null) {
				return new ServiceError(Constants.ErrorCode.NOT_FOUND_PROJECT, Constants.ErrorMessage.NOT_FOUND_PROJECT);
			}
			
			// 기업정보 분류
			List<DcLtBizBean> bizList = leagueDAO.sqlSelectLeagueBizList(prjtcd);
			if (bizList == null) {
				bizList = new ArrayList<DcLtBizBean>();
			}
			for (DcLtBizBean biz : bizList) {
				if (Constants.Actor.REAL_TARGET.equals(biz.getActcd())) {
					if (Constants.LtBiz.MAIN.equals(biz.getBizcd())) {
						project.getTarget().add(biz);
					}
				} else if (Constants.Actor.REAL_SELLER.equals(biz.getActcd())) {
					if (Constants.LtBiz.MAIN.equals(biz.getBizcd())) {
						project.getSeller().add(biz);
					} else if (Constants.LtBiz.FINANCE.equals(biz.getBizcd())) {
						project.getSellfinance().add(biz);
					} else if (Constants.LtBiz.AUDIT.equals(biz.getBizcd())) {
						project.getSellaudit().add(biz);
					} else if (Constants.LtBiz.LAW.equals(biz.getBizcd())) {
						project.getSelllaw().add(biz);
					}
				} else if (Constants.Actor.REAL_BUYER.equals(biz.getActcd())) {
					if (Constants.LtBiz.MAIN.equals(biz.getBizcd())) {
						project.getBuyer().add(biz);
					} else if (Constants.LtBiz.FINANCE.equals(biz.getBizcd())) {
						project.getBuyfinance().add(biz);
					} else if (Constants.LtBiz.AUDIT.equals(biz.getBizcd())) {
						project.getBuyaudit().add(biz);
					} else if (Constants.LtBiz.LAW.equals(biz.getBizcd())) {
						project.getBuylaw().add(biz);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return project;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 프로젝트 정보 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param dao
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public static Object getCredentialProject(DcCredentialDAO dao, String prjtcd) throws SQLException {
		DcCredentialBean project = null;
		
		try {
			// 기본 정보
			DcCredentialBaseDetailBean info = dao.sqlSelectCredentialBaseDetail(prjtcd);
			if (info == null) {
				return new ServiceError(Constants.ErrorCode.NOT_FOUND_PROJECT, Constants.ErrorMessage.NOT_FOUND_PROJECT);
			}
			project = new DcCredentialBean();
			project.setInfo(info);
			
			// 클라이언트
			List<DcCredentialBizBean> clients = dao.sqlSelectCredentialBizList(prjtcd, Constants.CredBiz.CLIENT);
			if (clients == null || clients.size() == 0) {
				return new ServiceError(Constants.ErrorCode.NOT_FOUND_PROJECT, Constants.ErrorMessage.NOT_FOUND_PROJECT);
			}
			project.setClient(clients.get(0));
			
			// 타겟
			List<DcCredentialBizBean> target = dao.sqlSelectCredentialBizList(prjtcd, Constants.CredBiz.TARGET);
			project.setTarget(target);
			
			// 이해관계자
			List<DcCredentialBizBean> interest = dao.sqlSelectCredentialBizList(prjtcd, Constants.CredBiz.INTEREST);
			if (interest != null && interest.size() > 0) {
				project.setInterest(interest.get(0));
			}
			
			// 담보 채권
			List<DcCredentialAddBean> bondsecure = dao.sqlSelectCredentialAddList(prjtcd, Constants.BondType.SECURE);
			project.setBondsecure(bondsecure);
			// 무담보 채권
			List<DcCredentialAddBean> bondsecurenone = dao.sqlSelectCredentialAddList(prjtcd, Constants.BondType.NONE_SECURE);
			project.setBondsecurenone(bondsecurenone);
			
			// BRS 매수처
			List<DcCredentialBizBean> brsbuyer = dao.sqlSelectCredentialBizList(prjtcd, Constants.CredBiz.BRSBUYER);
			if (brsbuyer != null && brsbuyer.size() > 0) {
				project.setBrsbuyer(brsbuyer.get(0));
			}
			
			// RCF
			List<DcCredentialBizBean> rcf = dao.sqlSelectCredentialBizList(prjtcd, Constants.CredBiz.RCF);
			if (rcf != null && rcf.size() > 0) {
				project.setRcf(rcf.get(0));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return project;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 목록 페이지 'Download' 버튼 권한 여부
	 * 
	 * 권한 롤
	 * LeagueTable 관리자/IT/System
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAuthLeagueListDownload(HttpServletRequest request) {
		ValidationResult validationResult = validateUser(request);
		if (validationResult != null && validationResult.isValid()) {
			UserSessionBeans userSessionBeans = SessionUtil.getUserSession(request);
			for (UserRoleBeans role : userSessionBeans.getUserRoleList()) {
				if (Constants.Role.R03.equals(role.getRolecd()) || Constants.Role.IT.equals(role.getRolecd()) || Constants.Role.SYSTEM.equals(role.getRolecd())) {
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 목록 페이지 '저장' 버튼 권한 여부
	 * 
	 * 권한 롤
	 * LeagueTable 관리자/본부담당자/IT/System
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAuthLeagueListSave(HttpServletRequest request) {
		ValidationResult validationResult = validateUser(request);
		if (validationResult != null && validationResult.isValid()) {
			UserSessionBeans userSessionBeans = SessionUtil.getUserSession(request);
			for (UserRoleBeans role : userSessionBeans.getUserRoleList()) {
				if (Constants.Role.R03.equals(role.getRolecd()) || Constants.Role.R05.equals(role.getRolecd()) || Constants.Role.IT.equals(role.getRolecd()) || Constants.Role.SYSTEM.equals(role.getRolecd())) {
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 상세 페이지 'ET확인' 버튼 권한 여부
	 * 
	 * 권한 롤
	 * EngagementTeam/본부담당자/LeagueTable관리자/IT/System
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAuthLeagueETConfirm(HttpServletRequest request) {
		ValidationResult validationResult = validateUser(request);
		if (validationResult != null && validationResult.isValid()) {
			UserSessionBeans userSessionBeans = SessionUtil.getUserSession(request);
			for (UserRoleBeans role : userSessionBeans.getUserRoleList()) {
				if (Constants.Role.R03.equals(role.getRolecd()) || Constants.Role.R04.equals(role.getRolecd()) || Constants.Role.R05.equals(role.getRolecd()) || Constants.Role.IT.equals(role.getRolecd()) || Constants.Role.SYSTEM.equals(role.getRolecd())) {
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 상세 페이지 '수정' 버튼 권한 여부
	 * 
	 * 관리 > League Table 편집 관리 테이블 참조함
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param request
	 * @param leagueDAO
	 * @param project
	 * @return
	 */
	public static boolean isAuthLeagueDetailEdit(HttpServletRequest request, DcLeagueTableDAO leagueDAO, Object project) {
		boolean auth = false;
		
		try {
			// 사용자 정보 조회
			UserSessionBeans userSessionBean = SessionUtil.getUserSession(request);
			String userempno = userSessionBean.getUserBeans().getEMPLNO();
			List<UserRoleBeans> userRoleList = userSessionBean.getUserRoleList();
			int roleLevel = getUserLeagueRoleLevel(userRoleList);
			
			// League Table 편집 관리 목록 조회
			List<AdminEditAuthListBean> list = new ArrayList<AdminEditAuthListBean>();
			list = leagueDAO.sqlSelectEditAuthList();
			if (list == null) {
				list = new ArrayList<AdminEditAuthListBean>();
			}
			
			// 프로젝트 정보 조회
			String hqcd = "";
			String ptrempno = "";
			String mgrempno = "";
			if (project != null) {
				if (project instanceof DcLtBuyBean) {
					DcLtBuyBean buy = (DcLtBuyBean) project;
					//hqcd = buy.getHqcd();
					hqcd = buy.getPtrhqcd();
					ptrempno = buy.getPtrempno();
					mgrempno = buy.getMgrempno();
				} else if (project instanceof DcLtMnaBean) {
					DcLtMnaBean mna = (DcLtMnaBean) project;
					//hqcd = mna.getHqcd();
					hqcd = mna.getPtrhqcd();
					ptrempno = mna.getPtrempno();
					mgrempno = mna.getMgrempno();
				} else if (project instanceof DcLtRealBean) {
					DcLtRealBean real = (DcLtRealBean) project;
					//hqcd = real.getHqcd();
					hqcd = real.getPtrhqcd();
					ptrempno = real.getPtrempno();
					mgrempno = real.getMgrempno();
				}
			}
			
			switch (roleLevel) {
			case Constants.LeagueRoleLevel.ROLE_MANAGER:
				auth = true;
				break;
				
			case Constants.LeagueRoleLevel.ROLE_HQ_ET:
				// 수정 권한 설정
				boolean authHQ = false;
				boolean authET = false;
				for (AdminEditAuthListBean item : list) {
					if (Constants.Role.R05.equals(item.getRolecd()) && Constants.YES.equals(item.getEdityn())) {
						authHQ = true;
					}
					if (Constants.Role.R04.equals(item.getRolecd()) && Constants.YES.equals(item.getEdityn())) {
						authET = true;
					}
				}
				
				// 프로젝트 조회 롤
				boolean srcHQ = false;
				boolean srcET = false;
				for (String hq : userSessionBean.getUserHQList()) {
					if (hq.equals(hqcd)) {
						srcHQ = true;
						break;
					}
				}
				if (userempno.equals(ptrempno) || userempno.equals(mgrempno)) {
					srcET = true;
				}
				
				boolean editableHQ = srcHQ && authHQ;
				boolean editableET = srcET && authET;
				auth = editableHQ || editableET;
				break;
				
			case Constants.LeagueRoleLevel.ROLE_HQ:
				for (AdminEditAuthListBean item : list) {
					if (Constants.Role.R05.equals(item.getRolecd()) && Constants.YES.equals(item.getEdityn())) {
						auth = true;
					}
				}
				break;
				
			case Constants.LeagueRoleLevel.ROLE_ET:
				for (AdminEditAuthListBean item : list) {
					if (Constants.Role.R04.equals(item.getRolecd()) && Constants.YES.equals(item.getEdityn())) {
						auth = true;
					}
				}
				break;
				
			case Constants.LeagueRoleLevel.ROLE_ETC:
				auth = false;
				break;
			}
			
		} catch (SQLException e) {
			auth = false;
			e.printStackTrace();
		}
		
		return auth;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 목록 조회를 위한 사용자 롤 결정
	 * 
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param roleList
	 * @return
	 */
	public static int getUserLeagueRoleLevel(List<UserRoleBeans> roleList) {
		// 관리자 여부[IT/System 포함]
		for (UserRoleBeans role : roleList) {
			String rolecd = role.getRolecd();
			if (Constants.Role.R03.equals(rolecd) || Constants.Role.IT.equals(rolecd) || Constants.Role.SYSTEM.equals(rolecd)) {
				return Constants.LeagueRoleLevel.ROLE_MANAGER;
			}
		}

		// 본부 담당자 && Engagement Team
		boolean r04 = false;
		boolean r05 = false;
		for (UserRoleBeans role : roleList) {
			String rolecd = role.getRolecd();
			if (Constants.Role.R04.equals(rolecd)) {
				r04 = true;
			}
			if (Constants.Role.R05.equals(rolecd)) {
				r05 = true;
			}
		}
		if (r04 && r05) {
			return Constants.LeagueRoleLevel.ROLE_HQ_ET;
		}

		// 본부 담당자
		if (!r04 && r05) {
			return Constants.LeagueRoleLevel.ROLE_HQ;
		}

		// Engagement Team
		if (r04 && !r05) {
			return Constants.LeagueRoleLevel.ROLE_ET;
		}

		// 기타
		return Constants.LeagueRoleLevel.ROLE_ETC;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * ET 확인일 유효성 검증
	 * 
	 * 확인한 일자가 현재일과 동일한 YYYYMM이면 유효함
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param etconfdt
	 * @return
	 */
	public static String isValidETConfdt(String statuscd, String etconfdt) {
		final String yes = "Yes";
		final String no = "No";
		
		if (Constants.LtStatus.SUCCESS.equals(statuscd) || Constants.LtStatus.DROP.equals(statuscd)) {
			return yes;
		}
		
		if (!StringUtils.isBlank(etconfdt)) {
			String[] arrDate = etconfdt.split("-");
			String etYear = arrDate[0];
			String etMonth = arrDate[1];
			
			Calendar now = Calendar.getInstance();
			String nowYear = String.valueOf(now.get(Calendar.YEAR));
			String nowMonth = StringUtils.leftPad(String.valueOf(now.get(Calendar.MONTH) + 1), 2, "0");
			
			if (nowYear.equals(etYear) && nowMonth.equals(etMonth)) {
				return yes;
			}
		}
		
		return no;
	}
	
	// 검색 조건 파싱
	public static DcCredentialSearchConditionBean parseSearchCondition(HttpServletRequest request) {
		String ctgcd = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CTGCD), ""));
		String pdtcd = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_PDTCD), ""));
		String frdt = StringUtils.trim(ServiceHelper.stripForDateFormat(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_FRDT), "")));
		String todt = StringUtils.trim(ServiceHelper.stripForDateFormat(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_TODT), "")));
		String prjtnm = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_PRJTNM), ""));
		String chkpe = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CHKPE), ""));
		String chkcross = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CHKCROSS), ""));
		// Client
		String srchtypcomp = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_SRCHTYPCOMP), ""));
		String clicomnm = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CLICOMNM), ""));
		String cliindcd = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CLIINDCD), ""));
		String cligindcd = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CLIGINDCD), ""));
		String cligindnm = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CLIGINDNM), ""));
		String clinatcd = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CLINATCD), ""));
		String clinatnm = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CLINATNM), ""));
		// Target
		String tarcomnm = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_TARCOMNM), ""));
		String tarindcd = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_TARINDCD), ""));
		String targindcd = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_TARGINDCD), ""));
		String targindnm = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_TARGINDNM), ""));
		String tarnatcd = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_TARNATCD), ""));
		String tarnatnm = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_TARNATNM), ""));
		// 이해관계자
		String concomnm = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CONCOMNM), ""));
		String conindcd = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CONINDCD), ""));
		String congindcd = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CONGINDCD), ""));
		String congindnm = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CONGINDNM), ""));
		String connatcd = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CONNATCD), ""));
		String connatnm = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CONNATNM), ""));
		
		// Transaction
		String amtrange = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_AMTRANGE), ""));
		
		// BRS
		Object tmp = ServiceHelper.getParameter(request, "excelJSON");	// BRS : Excel Download와 검색용 파라미터가 달라서, 분기하여 처리하기 위함.
		List<CommonCodeListBean> brssecure = new ArrayList<CommonCodeListBean>();
		List<CommonCodeListBean> brsunsecure = new ArrayList<CommonCodeListBean>();
		if(tmp == null){
			int count = ParameterHelper.getCategoryCount(request, CredentialParam.PARAM_BRSSECURE);
			for (int i = 0; i < count; i++) {
				CommonCodeListBean item = new CommonCodeListBean();
				item.setItemcd(StringUtils.defaultString(ParameterHelper.getParameter(request, CredentialParam.PARAM_BRSSECURE, i, "code"), ""));
				item.setItemnm(StringUtils.defaultString(ParameterHelper.getParameter(request, CredentialParam.PARAM_BRSSECURE, i, "name"), ""));
				brssecure.add(item);
			}
			count = ParameterHelper.getCategoryCount(request, CredentialParam.PARAM_BRSUNSECURE);
			for (int i = 0; i < count; i++) {
				CommonCodeListBean item = new CommonCodeListBean();
				item.setItemcd(StringUtils.defaultString(ParameterHelper.getParameter(request, CredentialParam.PARAM_BRSUNSECURE, i, "code"), ""));
				item.setItemnm(StringUtils.defaultString(ParameterHelper.getParameter(request, CredentialParam.PARAM_BRSUNSECURE, i, "name"), ""));
				brsunsecure.add(item);
			}
		}else{
			JSONParser jp = new JSONParser();
			if(request.getParameter(CredentialParam.PARAM_BRSSECURE) != null){
				try {
					JSONArray brssecureArr = (JSONArray) jp.parse(request.getParameter(CredentialParam.PARAM_BRSSECURE));
					for(Object obj : brssecureArr){
						JSONObject json = (JSONObject) obj;
						CommonCodeListBean item = new CommonCodeListBean();
						item.setItemcd((String) json.get("code"));
						item.setItemnm((String) json.get("name"));
						brssecure.add(item);
					}
				} catch (ParseException e) {
					throw CustomRuntimeException.INVALID_PARAM_JSON;
				}
			}
			
			if(request.getParameter(CredentialParam.PARAM_BRSUNSECURE) != null){
				try {
					JSONArray brssecureArr = (JSONArray) jp.parse(request.getParameter(CredentialParam.PARAM_BRSUNSECURE));
					for(Object obj : brssecureArr){
						JSONObject json = (JSONObject) obj;
						CommonCodeListBean item = new CommonCodeListBean();
						item.setItemcd((String) json.get("code"));
						item.setItemnm((String) json.get("name"));
						brsunsecure.add(item);
					}
				} catch (ParseException e) {
					throw CustomRuntimeException.INVALID_PARAM_JSON;
				}
			}
		}
		
		
		
		// RCF
		String rcflocation = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_RCFLOACTION), ""));
		String rcftype = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_RCFTYPE), ""));
		String rcfdetailtype = StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_RCFDETAILTYPE), ""));
		
		// Credential 대상 여부
		boolean credtgtcd = Boolean.parseBoolean(StringUtils.trim(StringUtils.defaultIfBlank(ServiceHelper.getParameter(request, CredentialParam.PARAM_CREDTGTCD), "false")));
		
		DcCredentialSearchConditionBean condition = new DcCredentialSearchConditionBean();
		condition.setCtgcd(ctgcd);
		condition.setPdtcd(pdtcd);
		condition.setFrdt(frdt);
		condition.setTodt(todt);
		condition.setPrjtnm(prjtnm);
		condition.setChkpe(chkpe);
		condition.setChkcross(chkcross);
		condition.setSrchtypcomp(srchtypcomp);
		condition.setClicomnm(clicomnm);
		condition.setCliindcd(cliindcd);
		condition.setCligindcd(cligindcd);
		condition.setCligindnm(cligindnm);
		condition.setClinatcd(clinatcd);
		condition.setClinatnm(clinatnm);
		condition.setTarcomnm(tarcomnm);
		condition.setTarindcd(tarindcd);
		condition.setTargindcd(targindcd);
		condition.setTargindnm(targindnm);
		condition.setTarnatcd(tarnatcd);
		condition.setTarnatnm(tarnatnm);
		condition.setConcomnm(concomnm);
		condition.setConindcd(conindcd);
		condition.setCongindcd(congindcd);
		condition.setCongindnm(congindnm);
		condition.setConnatcd(connatcd);
		condition.setConnatnm(connatnm);
		condition.setAmtrange(amtrange);
		condition.setBrssecure(brssecure);
		condition.setBrsunsecure(brsunsecure);
		condition.setRcflocation(rcflocation);
		condition.setRcftype(rcftype);
		condition.setRcfdetailtype(rcfdetailtype);
		condition.setCredtgtcd(credtgtcd?CredentialTargetYN.NO:CredentialTargetYN.YES);
		
		return condition;
	}
	
	public static SQLManagement getCredentialListSQL(String initSql, DcCredentialSearchConditionBean searchCondition){
		SQLManagement sqlmng = new SQLManagement(initSql);
		// 검색 조건 쿼리 만들기
		
		// Product
		if (!StringUtils.isBlank(searchCondition.getCtgcd()) && StringUtils.isBlank(searchCondition.getPdtcd())) {
			sqlmng.addSubSQLReplacement("SQL_CONDITION", " AND A.CTGCD = <#CTGCD#>", "CTGCD",  searchCondition.getCtgcd());
		}
		if (!StringUtils.isBlank(searchCondition.getPdtcd())) {
			String[] pdt = StringUtils.split(searchCondition.getPdtcd(), ",");
			if(pdt.length==1){
				sqlmng.addSubSQLReplacement("SQL_CONDITION", " AND A.PDTCD = <#PDTCD#>", "PDTCD",  pdt[0]);
			}else{
				for(int i=0; i<pdt.length; i++){
					if(i==0){
						sqlmng.addSubSQLReplacement("SQL_CONDITION", " AND ( A.PDTCD = <#PDTCD" + i + "#>", "PDTCD" + i,  pdt[i]);
					}else if(i==pdt.length-1){
						sqlmng.addSubSQLReplacement("SQL_CONDITION", " OR A.PDTCD = <#PDTCD" + i + "#> )", "PDTCD" + i,  pdt[i]);
					}else{
						sqlmng.addSubSQLReplacement("SQL_CONDITION", " OR A.PDTCD = <#PDTCD" + i + "#>", "PDTCD" + i,  pdt[i]);	
					}
				}	
			}
		}
		
		// 종료일자
		if (!StringUtils.isBlank(searchCondition.getFrdt())) {
			sqlmng.addSubSQLReplacement("SQL_CONDITION", " AND A.TERMIDT >= TO_DATE(<#FROMDT#>,'YYYYMMDD')", "FROMDT",  searchCondition.getFrdt());
		}
		if (!StringUtils.isBlank(searchCondition.getTodt())) {
			sqlmng.addSubSQLReplacement("SQL_CONDITION", " AND A.TERMIDT <= TO_DATE(<#TODT#>,'YYYYMMDD')", "TODT",  searchCondition.getTodt());
		}
		// --------------------- 20200323 남웅주 : 검색어 대문자로 변환 -----------------------
		// 용역명 검색
		if (!StringUtils.isBlank(searchCondition.getPrjtnm())) {
			sqlmng.addSubSQLReplacement("SQL_CONDITION", " AND UPPER(A.PRJTNM) LIKE <#PRJTNM#>", "PRJTNM", "%" + searchCondition.getPrjtnm().toUpperCase() + "%");
		}
		// PE
		if (Constants.YES.equals(searchCondition.getChkpe())) {
			sqlmng.addSubSQLReplacement("SQL_CONDITION", " AND C.CLIENTINDUCD IN ( SELECT PE.INDUCD FROM WEB_DCCREDPE PE ) ");
		}
		// Cross-Border
		if (Constants.YES.equals(searchCondition.getChkcross())) {
			final String KOR_NAT_CD = "KR";
			StringBuffer tmpSql = new StringBuffer();
			tmpSql.append("       AND ( ");
			tmpSql.append("               DECODE( C.CLIENTNATCD, NULL, '").append(KOR_NAT_CD).append("', C.CLIENTNATCD ) != '").append(KOR_NAT_CD).append("' ");
			tmpSql.append("               OR ");
			tmpSql.append("               DECODE( C.TGT1_NATCD, NULL, '").append(KOR_NAT_CD).append("', C.TGT1_NATCD ) != '").append(KOR_NAT_CD).append("' ");
			tmpSql.append("               OR ");
			tmpSql.append("               DECODE( C.TGT2_NATCD, NULL, '").append(KOR_NAT_CD).append("', C.TGT2_NATCD ) != '").append(KOR_NAT_CD).append("' ");
			tmpSql.append("               OR ");
			tmpSql.append("               DECODE( C.TGT3_NATCD, NULL, '").append(KOR_NAT_CD).append("', C.TGT3_NATCD ) != '").append(KOR_NAT_CD).append("' ");
			tmpSql.append("               OR ");
			tmpSql.append("               DECODE( C.INTRNATCD, NULL, '").append(KOR_NAT_CD).append("', C.INTRNATCD ) != '").append(KOR_NAT_CD).append("' ");
			tmpSql.append("           ) ");
			sqlmng.addSubSQLReplacement("SQL_CONDITION", tmpSql.toString());
		}
		
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 기업검색 - Client, Target, 이해관계자
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		ArrayList<String> clientSqlList = new ArrayList<String>(); 	// [SubKey, SQL, Value]
		ArrayList<String> targetSqlList = new ArrayList<String>(); 	// [SubKey, SQL, Value]
		ArrayList<String> intrSqlList = new ArrayList<String>(); 	// [SubKey, SQL, Value]
		Map<String, Object> compVal = new HashMap<String, Object>();
		
		// --------------------- 20200323 남웅주 : 검색어 대문자로 변환 -----------------------
		// Client - 이름검색
		if (!StringUtils.isBlank(searchCondition.getClicomnm())) {
			clientSqlList.add(" UPPER(C.CLIENTNM) LIKE <#CLIENTNM#> ");
			compVal.put("CLIENTNM", "%" + searchCondition.getClicomnm().toUpperCase() + "%");
		}
		// Client - Industry 검색
		if (!StringUtils.isBlank(searchCondition.getCliindcd())) {
			clientSqlList.add(" C.CLIENTINDUCD = <#CLIENTINDUCD#> ");
			compVal.put("CLIENTINDUCD", searchCondition.getCliindcd());
		}else if (!StringUtils.isBlank(searchCondition.getCligindcd())) {
			clientSqlList.add(" CI.ETCOCD = <#CLIENTGINDUCD#> ");
			compVal.put("CLIENTGINDUCD", searchCondition.getCligindcd());
		}
		// Client - 국가검색
		if (!StringUtils.isBlank(searchCondition.getClinatcd()) && !"-".equals(searchCondition.getClinatcd())) {
			clientSqlList.add(" C.CLIENTNATCD = <#CLIENTNATCD#> ");
			compVal.put("CLIENTNATCD", searchCondition.getClinatcd());
		}else if (!StringUtils.isBlank(searchCondition.getClinatcd()) && !StringUtils.isBlank(searchCondition.getClinatnm()) && "-".equals(searchCondition.getClinatcd())) {
			clientSqlList.add(" C.CLIENTNATNM = <#CLIENTNATNM#> ");
			compVal.put("CLIENTNATNM", searchCondition.getClinatnm());
		}
		
		// --------------------- 20200323 남웅주 : 검색어 대문자로 변환 -----------------------
		// Target - 이름검색
		if (!StringUtils.isBlank(searchCondition.getTarcomnm())) {
			targetSqlList.add(" REGEXP_LIKE(UPPER(C.TGT1_COMPHANNM||'^'||C.TGT2_COMPHANNM||'^'||C.TGT3_COMPHANNM), <#TCOMPHANNM#>) ");
			compVal.put("TCOMPHANNM", searchCondition.getTarcomnm().toUpperCase());
		}
		// Target - Industry 검색
		if (!StringUtils.isBlank(searchCondition.getTarindcd())) {
			StringBuffer tmpSql = new StringBuffer();
			tmpSql.append("        ( ");
			tmpSql.append("               C.TGT1_INDUCD = <#TINDUCD#> ");
			tmpSql.append("               OR ");
			tmpSql.append("               C.TGT2_INDUCD = <#TINDUCD#> ");
			tmpSql.append("               OR ");
			tmpSql.append("               C.TGT3_INDUCD = <#TINDUCD#> ");
			tmpSql.append("           ) ");
			targetSqlList.add(tmpSql.toString());
			compVal.put("TINDUCD", searchCondition.getTarindcd());
		}else if (!StringUtils.isBlank(searchCondition.getTargindcd())) {
			StringBuffer tmpSql = new StringBuffer();
			tmpSql.append("        ( ");
			tmpSql.append("               TI1.ETCOCD = <#TGINDUCD#> ");
			tmpSql.append("               OR ");
			tmpSql.append("               TI2.ETCOCD LIKE <#TGINDUCD#> ");
			tmpSql.append("               OR ");
			tmpSql.append("               TI3.ETCOCD LIKE <#TGINDUCD#> ");
			tmpSql.append("           ) ");
			targetSqlList.add(tmpSql.toString());
			compVal.put("TGINDUCD", searchCondition.getTargindcd());
		}
		// Target - 국가검색
		if (!StringUtils.isBlank(searchCondition.getTarnatcd()) && !"-".equals(searchCondition.getTarnatcd())) {
			StringBuffer tmpSql = new StringBuffer();
			tmpSql.append("        ( ");
			tmpSql.append("               C.TGT1_NATCD = <#TNATCD#> ");
			tmpSql.append("               OR ");
			tmpSql.append("               C.TGT2_NATCD = <#TNATCD#> ");
			tmpSql.append("               OR ");
			tmpSql.append("               C.TGT3_NATCD = <#TNATCD#> ");
			tmpSql.append("           ) ");
			targetSqlList.add(tmpSql.toString());
			compVal.put("TNATCD", searchCondition.getTarnatcd());
		}
		if (!StringUtils.isBlank(searchCondition.getTarnatcd()) && !StringUtils.isBlank(searchCondition.getTarnatnm()) && "-".equals(searchCondition.getTarnatcd())) {
			StringBuffer tmpSql = new StringBuffer();
			tmpSql.append("        ( ");
			tmpSql.append("               C.TGT1_NATNM = <#TNATNM#> ");
			tmpSql.append("               OR ");
			tmpSql.append("               C.TGT2_NATNM = <#TNATNM#> ");
			tmpSql.append("               OR ");
			tmpSql.append("               C.TGT3_NATNM = <#TNATNM#> ");
			tmpSql.append("           ) ");
			targetSqlList.add(tmpSql.toString());
			compVal.put("TNATNM", searchCondition.getTarnatnm());
		}

		// --------------------- 20200323 남웅주 : 검색어 대문자로 변환 -----------------------
		// 이해관계자 - 이름검색
		if (!StringUtils.isBlank(searchCondition.getConcomnm())) {
			intrSqlList.add(" UPPER(C.INTRCOMPHANNM) LIKE <#INTRCOMPHANNM#> ");
			compVal.put("INTRCOMPHANNM", "%" + searchCondition.getConcomnm().toUpperCase() + "%");
		}
		// 이해관계자 - Industry 검색
		if (!StringUtils.isBlank(searchCondition.getConindcd())) {
			intrSqlList.add(" C.INTRINDUCD = <#INTRINDUCD#> ");
			compVal.put("INTRINDUCD", searchCondition.getConindcd());
		}else if(!StringUtils.isBlank(searchCondition.getCongindcd())){
			intrSqlList.add(" II.ETCOCD = <#INTRGINDUCD#> ");
			compVal.put("INTRGINDUCD", searchCondition.getCongindcd());
		}
		// 이해관계자 - 국가검색
		if (!StringUtils.isBlank(searchCondition.getConnatcd()) && !"-".equals(searchCondition.getConnatcd())) {
			intrSqlList.add(" C.INTRNATCD = <#INTRNATCD#> ");
			compVal.put("INTRNATCD", searchCondition.getConnatcd());
		}else if (!StringUtils.isBlank(searchCondition.getConnatcd()) && !StringUtils.isBlank(searchCondition.getConnatnm()) && "-".equals(searchCondition.getConnatcd())) {
			intrSqlList.add(" C.INTRNATNM = <#INTRNATNM#> ");
			compVal.put("INTRNATNM", searchCondition.getConnatnm());
		}
		
		// 기업검색 쿼리 생성
		ArrayList<String> compSql = new ArrayList<String>();
		if(clientSqlList.size()>0){compSql.add(StringUtils.join(clientSqlList, " AND "));}
		if(targetSqlList.size()>0){compSql.add(StringUtils.join(targetSqlList, " AND "));}
		if(intrSqlList.size()>0){compSql.add(StringUtils.join(intrSqlList, " AND "));}
		if(compSql.size()>0){
			if("and".equals(searchCondition.getSrchtypcomp())){		// and조건일 경우
				sqlmng.addSubSQLReplacement("SQL_CONDITION", " AND ( (" + StringUtils.join(compSql, ") AND (") + ") )", compVal);
			}else{		// or조건일 경우
				sqlmng.addSubSQLReplacement("SQL_CONDITION", " AND ( (" + StringUtils.join(compSql, ") OR (") + ") )", compVal);	
			}
		}
		
		// Transaction - 거래규모
		if (!StringUtils.isBlank(searchCondition.getAmtrange())) {
			String[] amtrange = searchCondition.getAmtrange().split("~");
			Map<String, Object> amtmap = new HashMap<String, Object>();
			ArrayList<String> tmpCondition = new ArrayList<String>();
			if(!"infinity".equals(amtrange[0].trim())){
				tmpCondition.add(" AND D.AMT >= <#FROM_AMT#>");
				amtmap.put("FROM_AMT", Long.parseLong(amtrange[0].trim()));
			}
			if(!"infinity".equals(amtrange[1].trim())){
				tmpCondition.add(" AND D.AMT < <#TO_AMT#>");
				amtmap.put("TO_AMT", Long.parseLong(amtrange[1].trim()));	
			}
			sqlmng.addSubSQLReplacement("SQL_CONDITION", StringUtils.join(tmpCondition, ""), amtmap);
		}
		
		// BRS - 담보
		if (searchCondition.getBrssecure() != null && searchCondition.getBrssecure().size() > 0) {
			ArrayList<String> tmp = new ArrayList<String>();
			for(int i=0; i<searchCondition.getBrssecure().size(); i++){
				tmp.add(searchCondition.getBrssecure().get(i).getItemcd());
			}
			sqlmng.addSubSQLReplacement("SQL_CONDITION", " AND REGEXP_LIKE(C.SECURECD, <#SECURECD#>)", "SECURECD", StringUtils.join(tmp,"|"));
		}
		// BRS - 무담보
		if (searchCondition.getBrsunsecure() != null && searchCondition.getBrsunsecure().size() > 0) {
			ArrayList<String> tmp = new ArrayList<String>();
			for(int i=0; i<searchCondition.getBrsunsecure().size(); i++){
				tmp.add(searchCondition.getBrsunsecure().get(i).getItemcd());
			}
			sqlmng.addSubSQLReplacement("SQL_CONDITION", " AND REGEXP_LIKE(C.UNSECURECD, <#UNSECURECD#>)", "UNSECURECD", StringUtils.join(tmp,"|"));
		}
		
		// --------------------- 20200323 남웅주 : 검색어 대문자로 변환 -----------------------
		// RCF - 위치
		if (!StringUtils.isBlank(searchCondition.getRcflocation())) {
			sqlmng.addSubSQLReplacement("SQL_CONDITION", " AND UPPER(C.ADDR) LIKE <#ADDR#>", "ADDR", "%" + searchCondition.getRcflocation().toUpperCase() + "%");
		}
		// RCF - 구분
		if (!StringUtils.isBlank(searchCondition.getRcftype())) {
			sqlmng.addSubSQLReplacement("SQL_CONDITION", " AND A.RCFTYPECD = <#RCFTYPECD#>", "RCFTYPECD", searchCondition.getRcftype());
		}
		// RCF - 구분상세
		if (!StringUtils.isBlank(searchCondition.getRcfdetailtype())) {
			sqlmng.addSubSQLReplacement("SQL_CONDITION", " AND (A.RCFTYPEA = <#RCFTYPEDTL#> OR A.RCFTYPEB = <#RCFTYPEDTL#>) ", "RCFTYPEDTL", searchCondition.getRcfdetailtype());
		}
		
		// 대상여부
		sqlmng.addValueReplacement("CREDTGTCD", searchCondition.getCredtgtcd());
		sqlmng.generate(true);
				
		return sqlmng;	
	}
	
	
}
