package com.samil.dc.dao;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.core.ContextLoader;
import com.samil.dc.domain.AdminMailHistoryBean;
import com.samil.dc.domain.AdminMailProfileBean;
import com.samil.dc.domain.AdminMailRcptHistoryBean;
import com.samil.dc.domain.AdminMailRecipientBean;
import com.samil.dc.domain.UserSimpleBean;
import com.samil.dc.sql.DcMailProfileSQL;
import com.samil.dc.sql.SQLManagement;

import dev.bizisolution.lib.util.Decrypt;

public class DcAdminMailProfileDAO {
	
	private static final Logger logger = Logger.getRootLogger();
	private DBConnection dbcon = null;
	private String syscd = ContextLoader.getContext("ProgramCode");
	
	public DcAdminMailProfileDAO(DBConnection _dbcon){
		this.dbcon = _dbcon;
	}
	
	public List<AdminMailProfileBean> sqlSelectMailProfileList() throws SQLException {
		
		List<AdminMailProfileBean> list = new ArrayList<AdminMailProfileBean>(); 

		SQLManagement sqlmng = new SQLManagement(DcMailProfileSQL.SELECT_MAIL_PROFILE_LIST);
		sqlmng.addValueReplacement("SYSCD", syscd);
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				AdminMailProfileBean mp = new AdminMailProfileBean();
				mp.setSyscd(StringUtils.defaultIfBlank(rs.getString("SYSCD"), ""));
				mp.setMailid(StringUtils.defaultIfBlank(rs.getString("MAILID"), ""));
				mp.setSubject(StringUtils.defaultIfBlank(rs.getString("SUBJECT"), ""));
				mp.setContents(StringUtils.defaultIfBlank(rs.getString("CONTENTS"), ""));
				mp.setDes(StringUtils.defaultIfBlank(rs.getString("DES"), ""));
				mp.setUseyn(StringUtils.defaultIfBlank(rs.getString("USEYN"), ""));
				mp.setCreated(StringUtils.defaultIfBlank(rs.getString("CREATED"), ""));
				mp.setCreatedby(StringUtils.defaultIfBlank(rs.getString("CREATEDBY"), ""));
				mp.setModified(StringUtils.defaultIfBlank(rs.getString("MODIFIED"), ""));
				mp.setModefiedby(StringUtils.defaultIfBlank(rs.getString("MODIFIEDBY"), ""));	
				list.add(mp);
			}
		}
		
		return list;
	}
	
	public List<AdminMailRecipientBean> sqlSelectRcptList(String mailid) throws SQLException {
		List<AdminMailRecipientBean> retVal = new ArrayList<AdminMailRecipientBean>(); 

		SQLManagement sqlmng = new SQLManagement(DcMailProfileSQL.SELECT_MAIL_RECIPIENT_LIST);
		sqlmng.addValueReplacement("SYSCD", syscd);
		sqlmng.addValueReplacement("MAILID", mailid);
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}

		ResultSet rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				AdminMailRecipientBean mr = new AdminMailRecipientBean();
				mr.setSyscd(StringUtils.defaultIfBlank(rs.getString("SYSCD"), ""));
				mr.setMailid(StringUtils.defaultIfBlank(rs.getString("MAILID"), ""));
				mr.setRid(StringUtils.defaultIfBlank(rs.getString("RID"), ""));
				mr.setRname(StringUtils.defaultIfBlank(rs.getString("RNAME"), ""));
				mr.setRmail(StringUtils.defaultIfBlank(rs.getString("RMAIL"), ""));
				mr.setCreated(StringUtils.defaultIfBlank(rs.getString("CREATED"), ""));
				mr.setCreatedby(StringUtils.defaultIfBlank(rs.getString("CREATEDBY"), ""));
				retVal.add(mr);
			}
		}
		
		return retVal;
	}
	
	public void sqlMergeMailProfile(String mailid, String subject, String contents, String des, String useyn, String emplno) throws SQLException{
		SQLManagement sqlmng = new SQLManagement(DcMailProfileSQL.MERGE_INTO_MAIL_PROFILE);
		sqlmng.addValueReplacement("SYSCD", syscd);
		sqlmng.addValueReplacement("MAILID", mailid);
		sqlmng.addValueReplacement("SUBJECT", subject);
		sqlmng.addValueReplacement("CONTENTS", contents);
		sqlmng.addValueReplacement("DES", des);
		sqlmng.addValueReplacement("USEYN", useyn);
		sqlmng.addValueReplacement("EMPLNO", emplno);
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		dbcon.executeProcedure(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	public void sqlDeleteMailProfile(String mailid) throws SQLException{
		SQLManagement sqlmng = new SQLManagement(DcMailProfileSQL.DELETE_MAIL_PROFILE);
		sqlmng.addValueReplacement("SYSCD", syscd);
		sqlmng.addValueReplacement("MAILID", mailid);
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	public void sqlDeleteAllRcpt(String mailid) throws SQLException{
		SQLManagement sqlmng = new SQLManagement(DcMailProfileSQL.DELETE_ALL_MAIL_RECIPIENT);
		sqlmng.addValueReplacement("SYSCD", syscd);
		sqlmng.addValueReplacement("MAILID", mailid);
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	public void sqlInsertRcpt(String mailid, String rid, String rname, String rmail, String emplno) throws SQLException{
		SQLManagement sqlmng = new SQLManagement(DcMailProfileSQL.INSERT_MAIL_RECIPIENT);
		sqlmng.addValueReplacement("SYSCD", syscd);
		sqlmng.addValueReplacement("MAILID", mailid);
		sqlmng.addValueReplacement("RID", rid);
		sqlmng.addValueReplacement("RNAME", rname);
		sqlmng.addValueReplacement("RMAIL", rmail);
		sqlmng.addValueReplacement("EMPLNO", emplno);
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	public void sqlDeleteRcpt(String mailid, String rmail) throws SQLException{
		SQLManagement sqlmng = new SQLManagement(DcMailProfileSQL.DELETE_MAIL_RECIPIENT);
		sqlmng.addValueReplacement("SYSCD", syscd);
		sqlmng.addValueReplacement("MAILID", mailid);
		sqlmng.addValueReplacement("RMAIL", rmail);
		sqlmng.generate(true);
		if (logger.isDebugEnabled()) {
			logger.debug(sqlmng.getSql());
		}
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	public List<AdminMailHistoryBean> sqlSelectMailHistory(String mailid) throws SQLException{
		List<AdminMailHistoryBean> retVal = new ArrayList<AdminMailHistoryBean>(); 
		
		SQLManagement sqlmng = new SQLManagement(DcMailProfileSQL.SELECT_MAIL_HISTORY);
		sqlmng.addValueReplacement("SYSCD", syscd);
		sqlmng.addValueReplacement("MAILID", mailid);
		sqlmng.generate(true);
		
		ResultSet rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				AdminMailHistoryBean mh = new AdminMailHistoryBean();
				mh.setMid(StringUtils.defaultIfBlank(rs.getString("MID"), ""));
				mh.setSndemplno(StringUtils.defaultIfBlank(rs.getString("SNDEMPLNO"), ""));
				mh.setKornm(StringUtils.defaultIfBlank(rs.getString("KORNM"), ""));
				//mh.setSubject(StringUtils.defaultIfBlank(rs.getString("SUBJECT"), ""));
				//mh.setContents(StringUtils.defaultIfBlank(rs.getString("CONTENTS"), ""));
				mh.setSnddate(StringUtils.defaultIfBlank(rs.getString("SNDDATE"), ""));
				retVal.add(mh);
			}
		}
		return retVal;
	}
	
	public List<AdminMailHistoryBean> sqlSelectMailHistory(String mailid, String mid) throws SQLException{
		List<AdminMailHistoryBean> retVal = new ArrayList<AdminMailHistoryBean>(); 
		
		SQLManagement sqlmng = new SQLManagement(DcMailProfileSQL.SELECT_MAIL_HISTORY_BY_MID);
		sqlmng.addValueReplacement("SYSCD", syscd);
		sqlmng.addValueReplacement("MAILID", mailid);
		sqlmng.addValueReplacement("MID", mid);
		sqlmng.generate(true);
		
		ResultSet rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				AdminMailHistoryBean mh = new AdminMailHistoryBean();
				//mh.setMid(StringUtils.defaultIfBlank(rs.getString("MID"), ""));
				//mh.setSndemplno(StringUtils.defaultIfBlank(rs.getString("SNDEMPLNO"), ""));
				//mh.setKornm(StringUtils.defaultIfBlank(rs.getString("KORNM"), ""));
				mh.setSubject(StringUtils.defaultIfBlank(rs.getString("SUBJECT"), ""));
				mh.setContents(StringUtils.defaultIfBlank(rs.getString("CONTENTS"), ""));
				//mh.setSnddate(StringUtils.defaultIfBlank(rs.getString("SNDDATE"), ""));
				retVal.add(mh);
			}
		}
		return retVal;
	}
	
	public List<AdminMailRcptHistoryBean> sqlSelectMailRcptHistory(String mid) throws SQLException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException{
		List<AdminMailRcptHistoryBean> retVal = new ArrayList<AdminMailRcptHistoryBean>();
		List<String> emplno_list = new ArrayList<String>();
		boolean isDecript = "Y".equals(ContextLoader.getContext("ConnectToOper"));
		
		// RTMS에서 수신자 리스트에서 사번 정보 가져오기
		SQLManagement sqlmng = new SQLManagement(DcMailProfileSQL.SELECT_MAIL_RCPT_HISTORY);
		sqlmng.addValueReplacement("MID", mid);
		sqlmng.generate(true);
		ResultSet rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		if (rs != null) {
			while (rs.next()) {
				emplno_list.add(StringUtils.defaultIfBlank(rs.getString("RID"), ""));
			}
		}
		
		// 수신자 사번 정보를 통해 인사정보 가져오기
		if(emplno_list.size()>0){
			String tmp 			= "";
			String tmpEmplno 	= "";
			sqlmng = new SQLManagement(DcMailProfileSQL.SELECT_USER_INFO_BY_EMPLNO);
			
			for(int i=0; i<emplno_list.size(); i++){
				if(i==0){
					tmp += "<#EMPLNO" + i + "#>";
				}else{
					tmp += ",<#EMPLNO" + i + "#>";
				}
				
				// 운영서버의 경우 Encrypt되어 저장되어 있으므로 복호화 해야 함.
				if (isDecript) {
					tmpEmplno = Decrypt.decrypt(emplno_list.get(i)) ;
					//tmpEmplno = emplno_list.get(i);
				}else{
					tmpEmplno = emplno_list.get(i);
				}
				
				sqlmng.addValueReplacement("EMPLNO" + i, tmpEmplno);
			}
			sqlmng.addSQLReplacement("EMPLNO_LIST", tmp);
			sqlmng.generate(true);
			
			rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
			
			if (rs != null) {
				while (rs.next()) {
					AdminMailRcptHistoryBean mrh = new AdminMailRcptHistoryBean();
//					mrh.setRmail(StringUtils.defaultIfBlank(rs.getString("INTEID") + "@samil.com", ""));
//					2022-02-13 이지민 samil.com 도메인 미사용으로 pwc.com 메일주소 사용
					mrh.setRmail(StringUtils.defaultIfBlank(rs.getString("EMAIL"), ""));
					mrh.setEmplno(StringUtils.defaultIfBlank(rs.getString("EMPLNO"), ""));
					mrh.setKornm(StringUtils.defaultIfBlank(rs.getString("KORNM"), ""));
					mrh.setGradnm(StringUtils.defaultIfBlank(rs.getString("GRADNM"), ""));
					mrh.setTeam(StringUtils.defaultIfBlank(rs.getString("TEAMCD"), "") + "/" + StringUtils.defaultIfBlank(rs.getString("TEAMNM"), ""));
					retVal.add(mrh);
				}
			}
			
		}
		return retVal;
	}
	
	
	public List<UserSimpleBean> sqlSelectMailRcptList(String mailid) throws SQLException{
		List<UserSimpleBean> retVal = new ArrayList<UserSimpleBean>();
		SQLManagement sqlmng = null;
		if("M001".equals(mailid)){
			sqlmng = new SQLManagement(DcMailProfileSQL.SELECT_M001_RCPT_LIST);
		}
		
		if(sqlmng != null){
			sqlmng.generate(true);
			ResultSet rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
			if (rs != null) {
				while (rs.next()) {
					UserSimpleBean user = new UserSimpleBean();
					user.setEMPLNO(StringUtils.defaultIfBlank(rs.getString("EMPLNO"), ""));
					user.setINTEID(StringUtils.defaultIfBlank(rs.getString("INTEID"), ""));
					user.setEMAIL(StringUtils.defaultIfBlank(rs.getString("EMAIL"), ""));
					user.setKORNM(StringUtils.defaultIfBlank(rs.getString("KORNM"), ""));
					user.setENGNM(StringUtils.defaultIfBlank(rs.getString("ENGNM"), ""));
					user.setGRADCD(StringUtils.defaultIfBlank(rs.getString("GRADCD"), ""));
					user.setGRADNM(StringUtils.defaultIfBlank(rs.getString("GRADNM"), ""));
					user.setTEAMCD(StringUtils.defaultIfBlank(rs.getString("TEAMCD"), ""));
					user.setTEAMNM(StringUtils.defaultIfBlank(rs.getString("TEAMNM"), ""));
					user.setLOSCD(StringUtils.defaultIfBlank(rs.getString("LOSCD"), ""));
					user.setLOSNM(StringUtils.defaultIfBlank(rs.getString("LOSNM"), ""));
					retVal.add(user);
				}
			}
		}
		return retVal;
	}
}