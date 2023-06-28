package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.samil.dc.access.DBConnection;
import com.samil.dc.core.ContextLoader;
import com.samil.dc.domain.UserBeans;
import com.samil.dc.sql.ApplicationGlobalSQL;
import com.samil.dc.sql.SQLManagement;

public class UserBeanDAO {
	String PRGCD = "";
	DBConnection dbcon = null;
	
	
	public UserBeanDAO(DBConnection _dbcon){
		dbcon = _dbcon;
		PRGCD = ContextLoader.getContext("ProgramCode");
	}
	
	public UserBeans getUserByEmplno(String emplno) throws SQLException{
		UserBeans uVo = null;
		
		String sql = ApplicationGlobalSQL.SELECT_USERINFO_BY_EMPLNO;
		SQLManagement sqlmng = new SQLManagement(sql);
		ResultSet rs = null;
		
		sqlmng.addValueReplacement("EMPLNO", emplno);
		sqlmng.generate();
		
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		
		if(rs.next()){
			uVo = new UserBeans();
			uVo.setPTRDV(rs.getString("PTRDV"));
			uVo.setINTEID(rs.getString("INTEID"));
			uVo.setEMAIL(rs.getString("EMAIL"));
			uVo.setEMPLNO(rs.getString("EMPLNO"));
			uVo.setKORNM(rs.getString("KORNM"));
			uVo.setENGNM(rs.getString("ENGNM"));
			uVo.setGRADCD(rs.getString("GRADCD"));
			uVo.setGRADNM(rs.getString("GRADNM"));
			uVo.setLOSCD(rs.getString("LOSCD"));
			uVo.setLOSNM(rs.getString("LOSNM"));
			uVo.setTEAMCD(rs.getString("TEAMCD"));
			uVo.setTEAMNM(rs.getString("TEAMNM"));
			uVo.setAPPCD(rs.getString("APPCD"));
			uVo.setAPPNM(rs.getString("APPNM"));
			uVo.setTEL(rs.getString("PHOEXT"));
		}
		
		return uVo;
	}
	
	public UserBeans getUserByUserId(String userid) throws SQLException{
		UserBeans uVo = null;
		
		String sql = ApplicationGlobalSQL.SELECT_USERINFO_BY_USERID;
		SQLManagement sqlmng = new SQLManagement(sql);
		ResultSet rs = null;
		
		sqlmng.addValueReplacement("USERID", userid);
		sqlmng.generate();
		
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		
		if(rs.next()){
			uVo = new UserBeans();
			uVo.setPTRDV(rs.getString("PTRDV"));
			uVo.setINTEID(rs.getString("INTEID"));
			uVo.setEMAIL(rs.getString("EMAIL"));
			uVo.setEMPLNO(rs.getString("EMPLNO"));
			uVo.setKORNM(rs.getString("KORNM"));
			uVo.setENGNM(rs.getString("ENGNM"));
			uVo.setGRADCD(rs.getString("GRADCD"));
			uVo.setGRADNM(rs.getString("GRADNM"));
			uVo.setLOSCD(rs.getString("LOSCD"));
			uVo.setLOSNM(rs.getString("LOSNM"));
			uVo.setTEAMCD(rs.getString("TEAMCD"));
			uVo.setTEAMNM(rs.getString("TEAMNM"));
			uVo.setAPPCD(rs.getString("APPCD"));
			uVo.setAPPNM(rs.getString("APPNM"));
			uVo.setTEL(rs.getString("PHOEXT"));
		}
		
		return uVo;
	}
	
	public UserBeans getUserAuthByUserId(String userid, String menu_path) throws SQLException{
		UserBeans uVo = null;
		ResultSet rs = null;
		String sql = ApplicationGlobalSQL.SELECT_USERINFO_WITH_AUTHORITY_BY_USERID;
		
		SQLManagement sqlmng = new SQLManagement(sql);
		sqlmng.addValueReplacement("SYSTEM_CODE", ContextLoader.getContext("ProgramCode"));
		sqlmng.addValueReplacement("MENU_PATH", menu_path);
		sqlmng.addValueReplacement("USERID", userid);
		sqlmng.generate();
		
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		
		if(rs.next()){
			uVo = new UserBeans();
			uVo.setAUTH_TYPE(rs.getString("AUTH_TYPE"));
			uVo.setAUTH_CLASS(rs.getString("AUTH_CLASS"));
			uVo.setAUTH_APP(rs.getString("AUTH_APP"));
			uVo.setPTRDV(rs.getString("PTRDV"));
			uVo.setINTEID(rs.getString("INTEID"));
			uVo.setEMAIL(rs.getString("EMAIL"));
			uVo.setEMPLNO(rs.getString("EMPLNO"));
			uVo.setKORNM(rs.getString("KORNM"));
			uVo.setENGNM(rs.getString("ENGNM"));
			uVo.setGRADCD(rs.getString("GRADCD"));
			uVo.setGRADNM(rs.getString("GRADNM"));
			uVo.setLOSCD(rs.getString("LOSCD"));
			uVo.setLOSNM(rs.getString("LOSNM"));
			uVo.setTEAMCD(rs.getString("TEAMCD"));
			uVo.setTEAMNM(rs.getString("TEAMNM"));
			uVo.setAPPCD(rs.getString("APPCD"));
			uVo.setAPPNM(rs.getString("APPNM"));
			uVo.setTEL(rs.getString("PHOEXT"));
		}
		
		return uVo;
	}
}
