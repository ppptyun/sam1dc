package com.samil.dc.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

//import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.samil.dc.access.DBConnection;
import com.samil.dc.sql.AppLogMngSQL;
import com.samil.dc.sql.SQLManagement;


public class AppLogMngDAO {
//	private static final Logger logger = Logger.getRootLogger();
	
	private DBConnection dbcon = null;
	public AppLogMngDAO(DBConnection _dbcon){
		dbcon = _dbcon;
	}
	
	public void insertLogToLogMaster(Map<String, String> param) throws SQLException {
		JSONParser jsonParser = new JSONParser();
		String sql = AppLogMngSQL.INSERT_LOG;
		
		SQLManagement sqlmng = new SQLManagement(sql);
		try {
			JSONObject dataObj = (JSONObject) jsonParser.parse((String) param.get("data"));
			sqlmng.addValueReplacement("PRGCD", (String) dataObj.get("systemcode"));
			sqlmng.addValueReplacement("KIND", (String) dataObj.get("kind"));
			sqlmng.addValueReplacement("CURDB", (String) dataObj.get("appurl"));
			sqlmng.addValueReplacement("CURDOC", (String) dataObj.get("pagepath"));
			sqlmng.addValueReplacement("EMPLNO", (String) dataObj.get("emplno"));
			sqlmng.generate();
			
			dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void outputResponse(String outStr, String type, HttpServletResponse res) throws IOException, SQLException{
		dbcon.outputResponse(outStr, type, res);
	}
}
