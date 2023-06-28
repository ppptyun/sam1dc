package com.samil.dc.access;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.samil.dc.core.ContextLoader;

public class OracleConnection extends DBConnection {
	private static final Logger logger = Logger.getRootLogger();

	public OracleConnection() {
		try {
			Context initContext = new InitialContext();

			String postFix;

			String connectInfo;
			String username;
			String password;

			Context envContext;
			DataSource ds;

			if (ContextLoader.getContext("ConnectToOper").equals("Y")) {
				postFix = "Oper";
			} else {
				postFix = "Dev";
			}

			if (ContextLoader.getContext("UseJndi").equals("Y")) {
				String Initial_context = ContextLoader.getContext("InitialContext", "JNDI" + postFix);
				String jndi_name = ContextLoader.getContext("JNDIName", "JNDI" + postFix);

				logger.trace("JNDI: " + jndi_name);

				envContext = (Context) initContext.lookup(Initial_context);
				ds = (DataSource) envContext.lookup(jndi_name);

				conn = ds.getConnection();

			} else {
				connectInfo = ContextLoader.getContext("HostSID", "DataSource" + postFix);
				username = ContextLoader.getContext("UserName", "DataSource" + postFix);
				password = ContextLoader.getContext("Password", "DataSource" + postFix);

				logger.trace("JNDI: " + username + " HostSID: " + connectInfo);

				try {
					Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				conn = DriverManager.getConnection("jdbc:oracle:thin:@" + connectInfo, username, password);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
