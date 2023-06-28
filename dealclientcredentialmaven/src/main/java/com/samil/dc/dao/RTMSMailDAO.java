package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.core.ContextLoader;
import com.samil.dc.domain.RTMSMailBean;
import com.samil.dc.domain.RTMSUserBean;
import com.samil.dc.sql.RTMSMailSQL;
import com.samil.dc.sql.SQLManagement;

public class RTMSMailDAO {
//	private static final Logger logger = Logger.getRootLogger();
	private String syscd = ContextLoader.getContext("ProgramCode");
	private DBConnection dbcon = null;
	
	public RTMSMailDAO(DBConnection _dbcon){
		this.dbcon = _dbcon;
	}
	
	public RTMSMailBean sqlSelectMailProfile(String mailid) throws SQLException{
		RTMSMailBean data = null;
		
		SQLManagement sqlmng = new SQLManagement(RTMSMailSQL.SELECT_MAIL_PROFILE);
		sqlmng.addValueReplacement("SYSCD", syscd);
		sqlmng.addValueReplacement("MAILID", mailid);
		sqlmng.addValueReplacement("USEYN", "1");
		sqlmng.generate(true);
		
		ResultSet rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		
		if(rs.next()){
			data = new RTMSMailBean();
			data.setSubject(StringUtils.defaultIfBlank(rs.getString("SUBJECT"), ""));
			data.setContents(StringUtils.defaultIfBlank(rs.getString("CONTENTS"), ""));
		}
		
		return data;
	}
	
	public ArrayList<RTMSUserBean> sqlSelectRcptList(String mailid) throws SQLException{
		ArrayList<RTMSUserBean> list  = new ArrayList<RTMSUserBean>();
		
		SQLManagement sqlmng = new SQLManagement(RTMSMailSQL.SELECT_MAIL_RECIPIENT_LIST);
		sqlmng.addValueReplacement("SYSCD", syscd);
		sqlmng.addValueReplacement("MAILID", mailid);
		sqlmng.generate(true);
		
		ResultSet rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		
		while(rs.next()){
			RTMSUserBean user = new RTMSUserBean();
			user.setId(StringUtils.defaultIfBlank(rs.getString("RID"), ""));
			user.setName(StringUtils.defaultIfBlank(rs.getString("RNAME"), ""));
			user.setMail(StringUtils.defaultIfBlank(rs.getString("RMAIL"), ""));
			list.add(user);
		}
		
		return list;
	}
	
	
	public void sqlInsertMail(RTMSMailBean mail) throws SQLException{
		SQLManagement sqlmng = new SQLManagement(RTMSMailSQL.INSERT_MAIL);
		
		sqlmng.addValueReplacement("MID", mail.getMid());
		sqlmng.addValueReplacement("SID", mail.getSender().getId());
		sqlmng.addValueReplacement("SNAME", mail.getSender().getName());
		sqlmng.addValueReplacement("SMAIL", mail.getSender().getMail());
		sqlmng.addValueReplacement("SUBJECT", mail.getSubject());
		sqlmng.addValueReplacement("CONTENTS", mail.getContents());
		sqlmng.generate(true);
		
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	public void sqlInsertMailFromProf(String mid, RTMSUserBean sender, String prof_mailid) throws SQLException{
		SQLManagement sqlmng = new SQLManagement(RTMSMailSQL.INSERT_MAIL_FROM_PROF);
		
		sqlmng.addValueReplacement("MID", mid);
		sqlmng.addValueReplacement("SID", sender.getId());
		sqlmng.addValueReplacement("SNAME", sender.getName());
		sqlmng.addValueReplacement("SMAIL", sender.getMail());
		sqlmng.addValueReplacement("ADDCONTENTS", "");
		sqlmng.addValueReplacement("SYSCD", syscd);
		sqlmng.addValueReplacement("MAILID", prof_mailid);
		sqlmng.generate(true);
		
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
	
	
	public void sqlInsertRcptBatch(String mid, List<RTMSUserBean> rcptList ) throws SQLException{
		SQLManagement sqlmng = new SQLManagement(RTMSMailSQL.INSERT_RCPT);
		
		for(int i=0; i<rcptList.size(); i++){
			sqlmng.addValueReplacement("MID", mid);
			sqlmng.addValueReplacement("RID", rcptList.get(i).getId());
			sqlmng.addValueReplacement("RNAME", rcptList.get(i).getName());
			sqlmng.addValueReplacement("RMAIL", rcptList.get(i).getMail());
			sqlmng.addBatch();
		}
		sqlmng.generate(true); 
		dbcon.excuteBatchQuery(sqlmng.getSql(), sqlmng.getParameterBatch());
	}
	
	
	public void sqlInsertMailHistory(String mailid, String mid, String emplno, String status) throws SQLException{
		
		SQLManagement sqlmng = new SQLManagement(RTMSMailSQL.INSERT_MAIL_HISTORY);
		sqlmng.addValueReplacement("SYSCD", syscd);
		sqlmng.addValueReplacement("MAILID", mailid);
		sqlmng.addValueReplacement("SNDMAILID", mid);
		sqlmng.addValueReplacement("SNDEMPLNO", emplno);
		sqlmng.addValueReplacement("STATUS", status);
		
		sqlmng.generate(true);
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
	}
}
