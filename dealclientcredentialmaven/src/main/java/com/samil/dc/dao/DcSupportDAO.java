package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.domain.CommonCodeListBean;
import com.samil.dc.domain.CompanySearchListBean;
import com.samil.dc.domain.ConsCompanySearchListBean;
import com.samil.dc.domain.CurrencySearchListBean;
import com.samil.dc.domain.IndustrySearchListBean;
import com.samil.dc.domain.NationSearchListBean;
import com.samil.dc.domain.UserSearchListBean;
import com.samil.dc.sql.DcSupportSQL;
import com.samil.dc.sql.SQLManagement;

public class DcSupportDAO {

	private static final Logger logger = Logger.getRootLogger();
	private DBConnection m_dbcon = null;

	public DcSupportDAO(DBConnection _con) {
		m_dbcon = _con;
	}
	
	public List<CommonCodeListBean> sqlSelectCommonCodeList(String parCd) throws SQLException {
		List<CommonCodeListBean> list = new ArrayList<CommonCodeListBean>();

		SQLManagement sqlmng = new SQLManagement(DcSupportSQL.SELECT_COMMON_CODE_LIST);
		sqlmng.addValueReplacement("PARCD", parCd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				CommonCodeListBean code = new CommonCodeListBean();
				code.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				code.setItemcd(StringUtils.defaultIfBlank(rs.getString("ITEMCD"), ""));
				code.setItemnm(StringUtils.defaultIfBlank(rs.getString("ITEMNM"), ""));

				list.add(code);
			}
		}

		return list;
	}
	
	public List<CommonCodeListBean> sqlSelectRoleCodeList() throws SQLException {
		List<CommonCodeListBean> list = new ArrayList<CommonCodeListBean>();

		SQLManagement sqlmng = new SQLManagement(DcSupportSQL.SELECT_ROLE_CODE_LIST);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				CommonCodeListBean code = new CommonCodeListBean();
				code.setCtgcd("");
				code.setItemcd(StringUtils.defaultIfBlank(rs.getString("ITEMCD"), ""));
				code.setItemnm(StringUtils.defaultIfBlank(rs.getString("ITEMNM"), ""));

				list.add(code);
			}
		}

		return list;
	}
	
	public List<UserSearchListBean> sqlSelectUserSearchList(String empNo, String empNm) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcSupportSQL.SELECT_USER_SEARCH_LIST);
		
		// empNo 사번 optional
		if (!StringUtils.isBlank(empNo) && !StringUtils.isBlank(empNo.trim())) {
			sqlmng.addSubSQLReplacement("SEARCH_CONDITION", " AND H.EMPLNO = <#EMPLNO#>", "EMPLNO", empNo.trim());
		}
		
		// empNm 이름 optional
		if (!StringUtils.isBlank(empNm) && !StringUtils.isBlank(empNm.trim())) {
			sqlmng.addSubSQLReplacement("SEARCH_CONDITION", " AND H.KORNM = <#KORNM#>", "KORNM", empNm.trim());
		}
		List<UserSearchListBean> list = new ArrayList<UserSearchListBean>();
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				UserSearchListBean user = new UserSearchListBean();
				user.setInteid(StringUtils.defaultIfBlank(rs.getString("INTEID"), ""));
				user.setEmail(StringUtils.defaultIfBlank(rs.getString("EMAIL"), ""));
				user.setEmplno(StringUtils.defaultIfBlank(rs.getString("EMPLNO"), ""));
				user.setKornm(StringUtils.defaultIfBlank(rs.getString("KORNM"), ""));
				user.setTeamcd(StringUtils.defaultIfBlank(rs.getString("TEAMCD"), ""));
				user.setTeamnm(StringUtils.defaultIfBlank(rs.getString("TEAMNM"), ""));
				user.setGradcd(StringUtils.defaultIfBlank(rs.getString("GRADCD"), ""));
				user.setGradnm(StringUtils.defaultIfBlank(rs.getString("GRADNM"), ""));

				list.add(user);
			}
		}

		return list;
	}
	
	public List<CompanySearchListBean> sqlSelectCompanySearchList(String comNm) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcSupportSQL.SELECT_COMPANY_SEARCH_LIST);
		// comNm 회사명 optional
		if (!StringUtils.isBlank(comNm) && !StringUtils.isBlank(comNm.trim())) {
			sqlmng.addSubSQLReplacement("SEARCH_CONDITION", " AND UPPER(HANGNM) LIKE <#HANGNM#>", "HANGNM", "%" + comNm.trim().toUpperCase() + "%");
			sqlmng.addSubSQLReplacement("SEARCH_CONDITION1", " AND UPPER(A.KORENTRNM) LIKE <#HANGNM#>");
		}
				
		List<CompanySearchListBean> list = new ArrayList<CompanySearchListBean>();
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				CompanySearchListBean company = new CompanySearchListBean();
				company.setSourcetype(StringUtils.defaultIfBlank(rs.getString("SOURCE_TYPE"), ""));
				company.setClient(StringUtils.defaultIfBlank(rs.getString("CLIENT"), ""));
				company.setUpchecd(StringUtils.defaultIfBlank(rs.getString("UPCHECD"), ""));
				company.setHangnm(StringUtils.defaultIfBlank(rs.getString("HANGNM"), ""));
				company.setEnglnm(StringUtils.defaultIfBlank(rs.getString("ENGLNM"), ""));
				company.setBubinno(StringUtils.defaultIfBlank(rs.getString("BUBINNO"), ""));
				company.setKorreprnm(StringUtils.defaultIfBlank(rs.getString("KORREPRNM"), ""));
				company.setAddr(StringUtils.defaultIfBlank(rs.getString("ADDR"), ""));
				company.setInducd(StringUtils.defaultIfBlank(rs.getString("INDUCD"), ""));
				company.setIndunm(StringUtils.defaultIfBlank(rs.getString("INDUNM"), ""));
				company.setGlinducd(StringUtils.defaultIfBlank(rs.getString("GLINDUCD"), ""));
				company.setGlindunmk(StringUtils.defaultIfBlank(rs.getString("GLINDUNMK"), ""));
				
				list.add(company);
			}
		}

		return list;
	}
	
	public List<ConsCompanySearchListBean> sqlSelectConsCompanySearchList(String comNm, String type) throws SQLException {
		StringBuffer searchConditionSQL = new StringBuffer();
		StringBuffer searchConditionSQL2 = new StringBuffer();
		SQLManagement sqlmng = new SQLManagement(DcSupportSQL.SELECT_CONSCOMPANY_SEARCH_LIST);
		
		// comNm 회사명 optional
		if (!StringUtils.isBlank(comNm) && !StringUtils.isBlank(comNm.trim())) {
			sqlmng.addSubSQLReplacement("SEARCH_CONDITION", " AND UPPER(COMPHANNM) LIKE <#COMPHANNM#>", "COMPHANNM", "%" + comNm.trim().toUpperCase() + "%");
		}
			
		// type 조건
		if (!StringUtils.isBlank(type) && !StringUtils.isBlank(type.trim())) {
			sqlmng.addSubSQLReplacement("SEARCH_CONDITION2", "    AND " + type + "YN = 'Y'");
		}
		
		List<ConsCompanySearchListBean> list = new ArrayList<ConsCompanySearchListBean>();
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				ConsCompanySearchListBean company = new ConsCompanySearchListBean();
				company.setCompcd(StringUtils.defaultIfBlank(rs.getString("COMPCD"), ""));
				company.setHangnm(StringUtils.defaultIfBlank(rs.getString("COMPHANNM"), ""));
				company.setEnglnm(StringUtils.defaultIfBlank(rs.getString("COMPENGNM"), ""));

				list.add(company);
			}
		}

		return list;
	}
	
	public List<IndustrySearchListBean> sqlSelectIndustrySearchList(String induNm) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcSupportSQL.SELECT_INDUSTRY_SEARCH_LIST);
		
		// induNm 산업명 optional
		if (!StringUtils.isBlank(induNm) && !StringUtils.isBlank(induNm.trim())) {
			sqlmng.addSubSQLReplacement("SEARCH_CONDITION", " AND A.INDUNM LIKE <#INDUNM#>", "INDUNM", "%" + induNm.trim() + "%");
		}
		List<IndustrySearchListBean> list = new ArrayList<IndustrySearchListBean>();
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				IndustrySearchListBean industry = new IndustrySearchListBean();
				industry.setInducd(StringUtils.defaultIfBlank(rs.getString("INDUCD"), ""));
				industry.setIndunm(StringUtils.defaultIfBlank(rs.getString("INDUNM"), ""));
				industry.setIndugcd(StringUtils.defaultIfBlank(rs.getString("INDUGCD"), ""));
				industry.setIndugnm(StringUtils.defaultIfBlank(rs.getString("INDUGNM"), ""));
				list.add(industry);
			}
		}

		return list;
	}
	
	public List<NationSearchListBean> sqlSelectNationSearchList(String etcdNm) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcSupportSQL.SELECT_NATION_SEARCH_LIST);
		
		// etcdNm 국가명 optional
		if (!StringUtils.isBlank(etcdNm) && !StringUtils.isBlank(etcdNm.trim())) {
			sqlmng.addSubSQLReplacement("SEARCH_CONDITION", " AND ETCDNM LIKE <#ETCDNM#>", "ETCDNM", "%" + etcdNm.trim() + "%");  
//			searchConditionSQL.append("    AND ETCDNM LIKE '%").append(etcdNm.trim()).append("%' ");
		}
		
		List<NationSearchListBean> list = new ArrayList<NationSearchListBean>();
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				NationSearchListBean nation = new NationSearchListBean();
				nation.setEtcocd(StringUtils.defaultIfBlank(rs.getString("ETCOCD"), ""));
				nation.setEtcdnm(StringUtils.defaultIfBlank(rs.getString("ETCDNM"), ""));

				list.add(nation);
			}
		}

		return list;
	}
	
	public List<CurrencySearchListBean> sqlSelectCurrencySearchList() throws SQLException {
		List<CurrencySearchListBean> list = new ArrayList<CurrencySearchListBean>();

		SQLManagement sqlmng = new SQLManagement(DcSupportSQL.SELECT_CURRENCY_SEARCH_LIST);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				CurrencySearchListBean currency = new CurrencySearchListBean();
				currency.setEtcocd(StringUtils.defaultIfBlank(rs.getString("ETCOCD"), ""));
				currency.setEtcdnm(StringUtils.defaultIfBlank(rs.getString("ETCDNM"), ""));

				list.add(currency);
			}
		}

		return list;
	}
	
	public List<CommonCodeListBean> sqlSelectExceptionalCodeList(String exceptionalCode, String dmnitemCode) throws SQLException {
		List<CommonCodeListBean> list = new ArrayList<CommonCodeListBean>();

		SQLManagement sqlmng = new SQLManagement(DcSupportSQL.SELECT_EXCEPTIONAL_CODE_LIST);
		sqlmng.addValueReplacement("EXCEPTIONAL_CODE", exceptionalCode);
		sqlmng.addValueReplacement("DMNITEM_CODE", dmnitemCode);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				CommonCodeListBean code = new CommonCodeListBean();
				code.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				code.setItemcd(StringUtils.defaultIfBlank(rs.getString("ITEMCD"), ""));
				code.setItemnm(StringUtils.defaultIfBlank(rs.getString("ITEMNM"), ""));

				list.add(code);
			}
		}

		return list;
	}
	
	public List<CommonCodeListBean> sqlSelectLtmrktdivCodeList(String exceptionalCode, String dmnitemCode) throws SQLException {
		List<CommonCodeListBean> list = new ArrayList<CommonCodeListBean>();

		SQLManagement sqlmng = new SQLManagement(DcSupportSQL.SELECT_LTMRKTDIV_INFO);
		sqlmng.addValueReplacement("EXCEPTIONAL_CODE", exceptionalCode);
		sqlmng.addValueReplacement("DMNITEM_CODE", dmnitemCode);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				CommonCodeListBean code = new CommonCodeListBean();
				code.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				code.setItemcd(StringUtils.defaultIfBlank(rs.getString("ITEMCD"), ""));
				code.setItemnm(StringUtils.defaultIfBlank(rs.getString("ITEMNM"), ""));

				list.add(code);
			}
		}

		return list;
	}
}
