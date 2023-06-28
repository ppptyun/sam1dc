package com.samil.dc.access;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.samil.dc.core.ContextLoader;

public class MSSQLConnection extends DBConnection {
	private static final Logger logger = Logger.getRootLogger();

	public MSSQLConnection() {
		try {

			String postFix;

			String connectInfo;
			String username;
			String password;

			if (ContextLoader.getContext("ConnectToOper").equals("Y")) {
				postFix = "Oper";
			} else {
				postFix = "Dev";
			}

			connectInfo = ContextLoader.getContext("HostSID", "SQLDataSource" + postFix);
			username = ContextLoader.getContext("UserName", "SQLDataSource" + postFix);
			password = ContextLoader.getContext("Password", "SQLDataSource" + postFix);

			logger.trace("JNDI: " + username + " HostSID: " + connectInfo);

			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			super.conn = DriverManager.getConnection("jdbc:sqlserver://" + connectInfo, username, password);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
