package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.sorter.DcCredentialListSorter;
import com.samil.dc.domain.DcCredentialAddBean;
import com.samil.dc.domain.DcCredentialBaseDetailBean;
import com.samil.dc.domain.DcCredentialBean;
import com.samil.dc.domain.DcCredentialBizBean;
import com.samil.dc.domain.DcCredentialListBean;
import com.samil.dc.domain.DcCredentialProductBean;
import com.samil.dc.domain.DcCredentialProductCategoryBean;
import com.samil.dc.domain.DcCredentialProductWithCategoryBean;
import com.samil.dc.domain.DcCredentialSearchConditionBean;
import com.samil.dc.domain.NationSearchListBean;
import com.samil.dc.service.worker.ServiceHelper;
import com.samil.dc.sql.DcCredentialSQL;
import com.samil.dc.sql.SQLManagement;

public class DcCredentialDAO {

	private static final Logger logger = Logger.getRootLogger();
	private DBConnection m_dbcon = null;

	public DcCredentialDAO(DBConnection _con) {
		m_dbcon = _con;
	}

	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<DcCredentialListBean> sqlSelectSetupConsultTypeList(DcCredentialSearchConditionBean searchCondition) throws SQLException {
		List<DcCredentialListBean> list = new ArrayList<DcCredentialListBean>();
		SQLManagement sqlmng = ServiceHelper.getCredentialListSQL(DcCredentialSQL.SELECT_CREDENTIAL_LIST, searchCondition);
		
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		// 시간 측정
		long startTime = System.currentTimeMillis();
		
		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcCredentialListBean item = new DcCredentialListBean();

				item.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
				item.setCisno(StringUtils.defaultIfBlank(rs.getString("CISNO"), ""));
				item.setPdtcd(StringUtils.defaultIfBlank(rs.getString("PDTCD"), ""));
				item.setPdtnm(StringUtils.defaultIfBlank(rs.getString("PDTNM"), ""));
				item.setPdtcd_orig(StringUtils.defaultIfBlank(rs.getString("PDTCD_ORIG"), ""));
				item.setPdtnm_orig(StringUtils.defaultIfBlank(rs.getString("PDTNM_ORIG"), ""));
				item.setPrjtnm(StringUtils.defaultIfBlank(rs.getString("PRJTNM"), ""));
				String hqcd = StringUtils.defaultIfBlank(rs.getString("HQCD"), "");
				item.setHqcd(hqcd);
				item.setHqnm(hqcd + "/" + StringUtils.defaultIfBlank(rs.getString("HQNM"), ""));
				item.setPtrempno(StringUtils.defaultIfBlank(rs.getString("PTREMPNO"), ""));
				item.setPtrempnm(StringUtils.defaultIfBlank(rs.getString("PTREMPNM"), ""));
				item.setMgrempno(StringUtils.defaultIfBlank(rs.getString("MGREMPNO"), ""));
				item.setMgrempnm(StringUtils.defaultIfBlank(rs.getString("MGREMPNM"), ""));
				item.setClientcd(StringUtils.defaultIfBlank(rs.getString("CLIENTCD"), ""));
				item.setClientnm(StringUtils.defaultIfBlank(rs.getString("CLIENTNM"), ""));
				String clientinducd =  StringUtils.defaultIfBlank(rs.getString("CLIENTINDUCD"), "");
				item.setClientinducd(clientinducd);
				if(!StringUtils.isBlank(clientinducd)){
					item.setClientindunm(StringUtils.defaultIfBlank(rs.getString("CLIENTGINDUNM"), "") + "/"+ StringUtils.defaultIfBlank(rs.getString("CLIENTINDUNM"), ""));	
				}
				item.setClientnatcd(StringUtils.defaultIfBlank(rs.getString("CLIENTNATCD"), ""));
				item.setClientnatnm(StringUtils.defaultIfBlank(rs.getString("CLIENTNATNM"), ""));
				item.setTcomphannm1(StringUtils.defaultIfBlank(rs.getString("TCOMPHANNM1"), ""));
				item.setTnatcd1(StringUtils.defaultIfBlank(rs.getString("TNATCD1"), ""));
				item.setTnatnm1(StringUtils.defaultIfBlank(rs.getString("TNATNM1"), ""));
				String tinducd1 = StringUtils.defaultIfBlank(rs.getString("TINDUCD1"), "");
				item.setTinducd1(tinducd1);
				if (!StringUtils.isBlank(tinducd1)) {
					item.setTindunm1(StringUtils.defaultIfBlank(rs.getString("TGINDUNM1"), "") + "/" + StringUtils.defaultIfBlank(rs.getString("TINDUNM1"), ""));
				}
				item.setTcomphannm2(StringUtils.defaultIfBlank(rs.getString("TCOMPHANNM2"), ""));
				item.setTnatcd2(StringUtils.defaultIfBlank(rs.getString("TNATCD2"), ""));
				item.setTnatnm2(StringUtils.defaultIfBlank(rs.getString("TNATNM2"), ""));
				String tinducd2 = StringUtils.defaultIfBlank(rs.getString("TINDUCD2"), "");
				item.setTinducd2(tinducd2);
				if (!StringUtils.isBlank(tinducd2)) {
					item.setTindunm2(StringUtils.defaultIfBlank(rs.getString("TGINDUNM2"), "") + "/" + StringUtils.defaultIfBlank(rs.getString("TINDUNM2"), ""));
				}
				item.setTcomphannm3(StringUtils.defaultIfBlank(rs.getString("TCOMPHANNM3"), ""));
				item.setTnatcd3(StringUtils.defaultIfBlank(rs.getString("TNATCD3"), ""));
				item.setTnatnm3(StringUtils.defaultIfBlank(rs.getString("TNATNM3"), ""));
				String tinducd3 = StringUtils.defaultIfBlank(rs.getString("TINDUCD3"), "");
				item.setTinducd3(tinducd3);
				if (!StringUtils.isBlank(tinducd3)) {
					item.setTindunm3(StringUtils.defaultIfBlank(rs.getString("TGINDUNM3"), "") + "/" + StringUtils.defaultIfBlank(rs.getString("TINDUNM3"), ""));
				}
				item.setTrgtetc(StringUtils.defaultIfBlank(rs.getString("TRGTETC"), ""));
				item.setIntrcomphannm(StringUtils.defaultIfBlank(rs.getString("INTRCOMPHANNM"), ""));
				item.setIntrnatcd(StringUtils.defaultIfBlank(rs.getString("INTRNATCD"), ""));
				item.setIntrnatnm(StringUtils.defaultIfBlank(rs.getString("INTRNATNM"), ""));
				String intrinducd = StringUtils.defaultIfBlank(rs.getString("INTRINDUCD"), "");
				item.setIntrinducd(intrinducd);
				if (!StringUtils.isBlank(intrinducd)) {
					item.setIntrindunm(StringUtils.defaultIfBlank(rs.getString("INTRGINDUNM"), "") + "/" + StringUtils.defaultIfBlank(rs.getString("INTRINDUNM"), ""));
				}
				item.setPrjtdesc(StringUtils.defaultIfBlank(rs.getString("PRJTDESC"), ""));
				item.setContdt(StringUtils.defaultIfBlank(rs.getString("CONTDT"), ""));
				item.setTermidt(StringUtils.defaultIfBlank(rs.getString("TERMIDT"), ""));
				item.setChamtw(StringUtils.defaultIfBlank(rs.getString("CHAMTW"), ""));
				item.setBillamtw(StringUtils.defaultIfBlank(rs.getString("BILLAMTW"), ""));
				item.setDealnm1(StringUtils.defaultIfBlank(rs.getString("DEALNM1"), ""));
				item.setDealnm2(StringUtils.defaultIfBlank(rs.getString("DEALNM2"), ""));
				item.setDealnm3(StringUtils.defaultIfBlank(rs.getString("DEALNM3"), ""));
				item.setDealnm4(StringUtils.defaultIfBlank(rs.getString("DEALNM4"), ""));
				item.setConsnm(StringUtils.defaultIfBlank(rs.getString("CONSNM"), ""));
				item.setDealnm(StringUtils.defaultIfBlank(rs.getString("DEALNM"), ""));
				item.setAmt(StringUtils.defaultIfBlank(rs.getString("AMT"), ""));
				item.setConfcd(StringUtils.defaultIfBlank(rs.getString("CONFCD"), ""));
				item.setConfnm(StringUtils.defaultIfBlank(rs.getString("CONFNM"), ""));
				item.setActor1(StringUtils.defaultIfBlank(rs.getString("ACTOR1"), ""));
				item.setActor2(StringUtils.defaultIfBlank(rs.getString("ACTOR2"), ""));
				item.setStatuscd(StringUtils.defaultIfBlank(rs.getString("STATUSCD"), ""));
				item.setStatusnm(StringUtils.defaultIfBlank(rs.getString("STATUSNM"), ""));
				item.setBondnm(StringUtils.defaultIfBlank(rs.getString("BONDNM"), ""));
				item.setSecure(StringUtils.defaultIfBlank(rs.getString("SECURE"), ""));
				item.setUnsecure(StringUtils.defaultIfBlank(rs.getString("UNSECURE"), ""));
				item.setBrssalenm(StringUtils.defaultIfBlank(rs.getString("BRSSALENM"), ""));
				item.setBrsopb(StringUtils.defaultIfBlank(rs.getString("BRSOPB"), ""));
				item.setBrsbuyernm(StringUtils.defaultIfBlank(rs.getString("BRSBUYERNM"), ""));
				item.setRcfaddr(StringUtils.defaultIfBlank(rs.getString("RCFADDR"), ""));
				item.setRcftypenm(StringUtils.defaultIfBlank(rs.getString("RCFTYPENM"), ""));
				item.setRcftypedtnm(StringUtils.defaultIfBlank(rs.getString("RCFTYPEDTNM"), ""));
				item.setRcftypeetc(StringUtils.defaultIfBlank(rs.getString("RCFTYPEETC"), ""));
				item.setRcfarea(StringUtils.defaultIfBlank(rs.getString("RCFAREA"), ""));
				item.setRcfland(StringUtils.defaultIfBlank(rs.getString("RCFLAND"), ""));
				//item.setComnm(StringUtils.defaultIfBlank(rs.getString("COMNM"), ""));
				item.setPrjtdivcd(StringUtils.defaultIfBlank(rs.getString("PRJTDIVCD"), ""));
				item.setPrjtdivnm(StringUtils.defaultIfBlank(rs.getString("PRJTDIVNM"), ""));
				item.setCredcd(StringUtils.defaultIfBlank(rs.getString("CREDCD"), ""));
				item.setCrednm(StringUtils.defaultIfBlank(rs.getString("CREDNM"), ""));
				item.setLeagueyn(StringUtils.defaultIfBlank(rs.getString("LEAGUEYN"), ""));

				list.add(item);
			}
		}
		
		long endTime = System.currentTimeMillis();
		logger.debug("QueryTime [" + (endTime - startTime) + "]");
		
		
		// 속도 개선을 위해 쿼리로 소팅하지 않고 WAS에서 소팅
		startTime = System.currentTimeMillis();
		Collections.sort(list, new DcCredentialListSorter());
		endTime = System.currentTimeMillis();
		logger.debug("SortingTime [" + (endTime - startTime) + "]");
		
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential Product Category 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<DcCredentialProductCategoryBean> sqlSelectProductCategoryList() throws SQLException {
		List<DcCredentialProductCategoryBean> list = new ArrayList<DcCredentialProductCategoryBean>();

		SQLManagement sqlmng = new SQLManagement(DcCredentialSQL.SELECT_CREDENTIAL_PRODUCT_CATEGORY_LIST);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcCredentialProductCategoryBean item = new DcCredentialProductCategoryBean();

				item.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				item.setCtgnm(StringUtils.defaultIfBlank(rs.getString("CTGNM"), ""));
				
				list.add(item);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential Product 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<DcCredentialProductBean> sqlSelectProductList(String ctgcd) throws SQLException {
		List<DcCredentialProductBean> list = new ArrayList<DcCredentialProductBean>();
		SQLManagement sqlmng = new SQLManagement(DcCredentialSQL.SELECT_CREDENTIAL_PRODUCT_LIST);
		if (!StringUtils.isBlank(ctgcd) && !StringUtils.isBlank(ctgcd.trim())) {
			sqlmng.addSubSQLReplacement("SEARCH_CONDITION", " AND CTGCD = <#CTGCD#>", "CTGCD", ctgcd.trim());
		}
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcCredentialProductBean item = new DcCredentialProductBean();

				String pdtcd = StringUtils.defaultIfBlank(rs.getString("PDTCD"), "");
				item.setPdtcd(pdtcd);
				item.setPdtnm(pdtcd + "/" + StringUtils.defaultIfBlank(rs.getString("PDTNM"), ""));
				
				list.add(item);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential Product 목록 조회 팝업(카테고리 포함)
	 * ====================================================================================
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<DcCredentialProductWithCategoryBean> sqlSelectProductListWithCategory(String refyearly) throws SQLException {
		List<DcCredentialProductWithCategoryBean> list = new ArrayList<DcCredentialProductWithCategoryBean>();
		SQLManagement sqlmng = new SQLManagement(DcCredentialSQL.SELECT_CREDENTIAL_PRODUCT_LIST_WITH_CATEGORY);
		if (!StringUtils.isBlank(refyearly) && !StringUtils.isBlank(refyearly.trim())) {
			sqlmng.addValueReplacement("REFYEARLY", refyearly);
		}
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcCredentialProductWithCategoryBean item = new DcCredentialProductWithCategoryBean();
				item.setRefyearly(StringUtils.defaultIfBlank(rs.getString("REFYEARLY"), ""));
				item.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				item.setCtgnm(StringUtils.defaultIfBlank(rs.getString("CTGNM"), ""));
				item.setPdtcd(StringUtils.defaultIfBlank(rs.getString("PDTCD"), ""));
				item.setPdtnm(StringUtils.defaultIfBlank(rs.getString("PDTNM"), ""));
				item.setComnm(StringUtils.defaultIfBlank(rs.getString("COMNM"), ""));
				list.add(item);
			}
		}
		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 상세 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public DcCredentialBaseDetailBean sqlSelectCredentialBaseDetail(String prjtcd) throws SQLException {
		DcCredentialBaseDetailBean info = null;
		
		SQLManagement sqlmng = new SQLManagement(DcCredentialSQL.SELECT_CREDENTIAL_DETAIL);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		logger.debug(rs == null);
		if(rs.next()){
			info = new DcCredentialBaseDetailBean();
			info.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
			info.setPrjtnm(StringUtils.defaultIfBlank(rs.getString("PRJTNM"), ""));
			info.setRefyearly(StringUtils.defaultIfBlank(rs.getString("REFYEARLY"), ""));
			info.setPdtcd(StringUtils.defaultIfBlank(rs.getString("PDTCD"), ""));
			info.setPdtnm(StringUtils.defaultIfBlank(rs.getString("PDTNM"), ""));
			info.setRefyearly_orig(StringUtils.defaultIfBlank(rs.getString("REFYEARLY_ORIG"), ""));
			info.setPdtcd_orig(StringUtils.defaultIfBlank(rs.getString("PDTCD_ORIG"), ""));
			info.setPdtnm_orig(StringUtils.defaultIfBlank(rs.getString("PDTNM_ORIG"), ""));
			info.setPrjtdesc(StringUtils.defaultIfBlank(rs.getString("PRJTDESC"), ""));
			info.setTrgtetc(StringUtils.defaultIfBlank(rs.getString("TRGTETC"), ""));
			info.setBrssalecd(StringUtils.defaultIfBlank(rs.getString("BRSSALECD"), ""));
			info.setBrsopb(StringUtils.defaultIfBlank(rs.getString("BRSOPB"), ""));
			info.setRcftypecd(StringUtils.defaultIfBlank(rs.getString("RCFTYPECD"), ""));
			info.setRcftypea(StringUtils.defaultIfBlank(rs.getString("RCFTYPEA"), ""));
			info.setRcftypeb(StringUtils.defaultIfBlank(rs.getString("RCFTYPEB"), ""));
			info.setRcftypeetc(StringUtils.defaultIfBlank(rs.getString("RCFTYPEETC"), ""));
			info.setRcfarea(StringUtils.defaultIfBlank(rs.getString("RCFAREA"), ""));
			info.setRcfland(StringUtils.defaultIfBlank(rs.getString("RCFLAND"), ""));
			info.setCredtgtcd(StringUtils.defaultIfBlank(rs.getString("CREDTGTCD"), ""));
			info.setCredtgtnm(StringUtils.defaultIfBlank(rs.getString("CREDTGTNM"), ""));
		}

		return info;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 기업 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param bizcd
	 * @return
	 * @throws SQLException
	 */
	public List<DcCredentialBizBean> sqlSelectCredentialBizList(String prjtcd, String bizcd) throws SQLException {
		List<DcCredentialBizBean> list = new ArrayList<DcCredentialBizBean>();

		SQLManagement sqlmng = new SQLManagement(DcCredentialSQL.SELECT_CREDENTIAL_BIZ_LIST);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.addValueReplacement("BIZCD", bizcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcCredentialBizBean item = new DcCredentialBizBean();

				item.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
				item.setBizcd(StringUtils.defaultIfBlank(rs.getString("BIZCD"), ""));
				item.setSeq(StringUtils.defaultIfBlank(rs.getString("SEQ"), ""));
				item.setBizdivcd(StringUtils.defaultIfBlank(rs.getString("BIZDIVCD"), ""));
				item.setCompcd(StringUtils.defaultIfBlank(rs.getString("COMPCD"), ""));
				item.setComphannm(StringUtils.defaultIfBlank(rs.getString("COMPHANNM"), ""));
				item.setCompengnm(StringUtils.defaultIfBlank(rs.getString("COMPENGNM"), ""));
				item.setInducd(StringUtils.defaultIfBlank(rs.getString("INDUCD"), ""));
				item.setIndunm(StringUtils.defaultIfBlank(rs.getString("INDUNM"), ""));
				item.setNatcd(StringUtils.defaultIfBlank(rs.getString("NATCD"), ""));
				item.setNatnm(StringUtils.defaultIfBlank(rs.getString("NATNM"), ""));
				item.setCity(StringUtils.defaultIfBlank(rs.getString("CITY"), ""));
				item.setAddr(StringUtils.defaultIfBlank(rs.getString("ADDR"), ""));
				
				list.add(item);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 추가항목 목록 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @param ctgcd
	 * @return
	 * @throws SQLException
	 */
	public List<DcCredentialAddBean> sqlSelectCredentialAddList(String prjtcd, String ctgcd) throws SQLException {
		List<DcCredentialAddBean> list = new ArrayList<DcCredentialAddBean>();

		SQLManagement sqlmng = new SQLManagement(DcCredentialSQL.SELECT_CREDENTIAL_ADD_LIST);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.addValueReplacement("CTGCD", ctgcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		
		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				DcCredentialAddBean item = new DcCredentialAddBean();

				item.setPrjtcd(StringUtils.defaultIfBlank(rs.getString("PRJTCD"), ""));
				item.setItemcd(StringUtils.defaultIfBlank(rs.getString("ITEMCD"), ""));
				item.setItemnm(StringUtils.defaultIfBlank(rs.getString("ITEMNM"), ""));
				item.setCtgcd(StringUtils.defaultIfBlank(rs.getString("CTGCD"), ""));
				item.setBigo(StringUtils.defaultIfBlank(rs.getString("BIGO"), ""));
				
				list.add(item);
			}
		}

		return list;
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential Base 업데이트
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public int sqlUpdateCredentialBase(DcCredentialBean project) throws SQLException {
		DcCredentialBaseDetailBean info = project.getInfo();
		
		SQLManagement sqlmng = new SQLManagement(DcCredentialSQL.UPDATE_CREDENTIAL_BASE_INFO);
		sqlmng.addValueReplacement("PRJTCD", info.getPrjtcd());
		sqlmng.addValueReplacement("REFYEARLY", info.getRefyearly());
		sqlmng.addValueReplacement("CTGCD", info.getCtgcd());
		sqlmng.addValueReplacement("PDTCD", info.getPdtcd());
		sqlmng.addValueReplacement("PRJTDESC", info.getPrjtdesc());
		sqlmng.addValueReplacement("TRGTETC", info.getTrgtetc());
		sqlmng.addValueReplacement("BRSSALECD", info.getBrssalecd());
		sqlmng.addValueReplacement("BRSOPB", info.getBrsopb());
		sqlmng.addValueReplacement("RCFTYPECD", info.getRcftypecd());
		sqlmng.addValueReplacement("RCFTYPEA", info.getRcftypea());
		sqlmng.addValueReplacement("RCFTYPEB", info.getRcftypeb());
		sqlmng.addValueReplacement("RCFTYPEETC", info.getRcftypeetc());
		sqlmng.addValueReplacement("RCFAREA", info.getRcfarea());
		sqlmng.addValueReplacement("RCFLAND", info.getRcfland());
		sqlmng.addValueReplacement("CREDTGTCD", info.getCredtgtcd());
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 클라이언트 국가 정보 업데이트
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public int sqlUpdateCredentialClientNat(DcCredentialBean project) throws SQLException {
		DcCredentialBizBean client = project.getClient();
		
		SQLManagement sqlmng = new SQLManagement(DcCredentialSQL.UPDATE_CREDENTIAL_CLIENT_NAT);
		sqlmng.addValueReplacement("PRJTCD", client.getPrjtcd());
		sqlmng.addValueReplacement("NATCD", client.getNatcd());
		sqlmng.addValueReplacement("NATNM", client.getNatnm());
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 기업 정보 업데이트
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param biz
	 * @return
	 * @throws SQLException
	 */
	public int sqlUpdateCredentialBiz(DcCredentialBizBean biz) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcCredentialSQL.UPDATE_CREDENTIAL_BIZ);
		sqlmng.addValueReplacement("PRJTCD", biz.getPrjtcd());
		sqlmng.addValueReplacement("BIZCD", biz.getBizcd());
		sqlmng.addValueReplacement("SEQ", biz.getSeq());
		sqlmng.addValueReplacement("BIZDIVCD", biz.getBizdivcd());
		sqlmng.addValueReplacement("COMPCD", biz.getCompcd());
		sqlmng.addValueReplacement("COMPHANNM", biz.getComphannm());
		sqlmng.addValueReplacement("COMPENGNM", biz.getCompengnm());
		sqlmng.addValueReplacement("INDUCD", biz.getInducd());
		sqlmng.addValueReplacement("NATCD", biz.getNatcd());
		sqlmng.addValueReplacement("NATNM", biz.getNatnm());
		sqlmng.addValueReplacement("CITY", biz.getCity());
		sqlmng.addValueReplacement("ADDR", biz.getAddr());
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 기업 정보 신규 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param biz
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertCredentialBiz(DcCredentialBizBean biz) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcCredentialSQL.INSERT_CREDENTIAL_BIZ);
		sqlmng.addValueReplacement("PRJTCD", biz.getPrjtcd());
		sqlmng.addValueReplacement("BIZCD", biz.getBizcd());
		sqlmng.addValueReplacement("BIZDIVCD", biz.getBizdivcd());
		sqlmng.addValueReplacement("COMPCD", biz.getCompcd());
		sqlmng.addValueReplacement("COMPHANNM", biz.getComphannm());
		sqlmng.addValueReplacement("COMPENGNM", biz.getCompengnm());
		sqlmng.addValueReplacement("INDUCD", biz.getInducd());
		sqlmng.addValueReplacement("NATCD", biz.getNatcd());
		sqlmng.addValueReplacement("NATNM", biz.getNatnm());
		sqlmng.addValueReplacement("CITY", biz.getCity());
		sqlmng.addValueReplacement("ADDR", biz.getAddr());
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 추가 정보 등록
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param item
	 * @return
	 * @throws SQLException
	 */
	public int sqlInsertCredentialAdd(DcCredentialAddBean item) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcCredentialSQL.INSERT_CREDENTIAL_ADD);
		sqlmng.addValueReplacement("PRJTCD", item.getPrjtcd());
		sqlmng.addValueReplacement("ITEMCD", item.getItemcd());
		sqlmng.addValueReplacement("CTGCD", item.getCtgcd());
		sqlmng.addValueReplacement("BIGO", item.getBigo());
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 추가 정보 삭제(프로젝트 단위로 전체 삭제)
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public int sqlDeleteCredentialAddAll(String prjtcd) throws SQLException {
		SQLManagement sqlmng = new SQLManagement(DcCredentialSQL.DELETE_CREDENTIAL_ADD_ALL);
		sqlmng.addValueReplacement("PRJTCD", prjtcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		return m_dbcon.executeUpdate(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 타입별 국가 조회
	 * ====================================================================================
	 * </pre>
	 * 
	 * @param prjtcd
	 * @return
	 * @throws SQLException
	 */
	public List<NationSearchListBean> sqlSelectCompanyTypeNationList(String bizcd) throws SQLException {
		List<NationSearchListBean> list = new ArrayList<NationSearchListBean>();
		
		SQLManagement sqlmng = new SQLManagement(DcCredentialSQL.SELECT_CREDENTIAL_COMPANY_TYPE_NATION);
		sqlmng.addValueReplacement("BIZCD", bizcd);
		sqlmng.generate();
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = m_dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				NationSearchListBean item = new NationSearchListBean();

				item.setEtcocd(StringUtils.defaultIfBlank(rs.getString("NATCD"), ""));
				item.setEtcdnm(StringUtils.defaultIfBlank(rs.getString("NATNM"), ""));
				
				list.add(item);
			}
		}
		
		return list;
	}
	
}
