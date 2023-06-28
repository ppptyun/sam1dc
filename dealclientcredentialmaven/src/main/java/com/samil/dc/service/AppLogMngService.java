package com.samil.dc.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.samil.dc.access.DBConnection;
import com.samil.dc.dao.AppLogMngDAO;
import com.samil.dc.util.SamilUtil;

public class AppLogMngService {
	private static final Logger logger = Logger.getRootLogger();
	
	public void insertLogToLogMaster(HttpServletRequest req, HttpServletResponse res, DBConnection dbcon) throws SQLException, IOException {
		Map param = SamilUtil.getParameterMap(req);
		AppLogMngDAO almdao = new AppLogMngDAO(dbcon);
		
		try {
			almdao.insertLogToLogMaster(param);
		} catch (SQLException e) {
			logger.debug("Logging Error : " + param.get("data"));
			almdao.outputResponse("Logging Error : " + param.get("data"), "string", res);
		}
	}
}
