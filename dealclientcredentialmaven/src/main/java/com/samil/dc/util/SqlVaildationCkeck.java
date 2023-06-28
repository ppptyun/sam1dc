package com.samil.dc.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.dhtmlx.connector.LogManager;


public class SqlVaildationCkeck {

	private String table;
	private Map<String,String> value; 
	private HttpServletResponse resp;
	private Connection conn;
	
	private boolean outputoption = true;
	
	/**
	 * @return
	 * output check;
	 */
	public boolean isOutputoption() {return outputoption;}
	public void setOutputoption(boolean outputoption) {this.outputoption = outputoption;}

	public SqlVaildationCkeck() {
		
	}
	
	public SqlVaildationCkeck(String table, HashMap<String, String> value, HttpServletResponse resp, Connection conn) {
		this.table = table;
		this.value = value;
		this.resp = resp;
		this.conn = conn;
	}
	
	public boolean vaildationckeck()
	{
		boolean check = false;
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/xml:charset=utf8");
		
		ResultSet result = null;
		java.io.Writer out = null;
		
		try 
		{
			try
			{
				conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				PreparedStatement cs = null;
				
				String sql = "select * FROM "+ table + " WHERE 1=1"; 
				Set<String> keyset = value.keySet();
				Iterator<String> iter = keyset.iterator();
				
				while(iter.hasNext())
				{
					String key = iter.next();
					String val = value.get(key);
					
					sql += " AND "+ key + " = '" + val + "'";
					
				}
				
				//System.out.println("SqlVaildationCkeck SQL : " + sql);
				LogManager.getInstance().log("SqlVaildationCkeck SQL : " + sql);
				cs = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				result = cs.executeQuery();
				result.last();
				
				//System.out.println("ResultSet result Row: " + result.getRow());
				LogManager.getInstance().log("ResultSet result Row: " + result.getRow());
				if (result.getRow() > 0)
				{
					if (outputoption)
					{
						out = resp.getWriter();
						out.write("<LOG><item>ERROR</item><item>ERR_001</item></LOG>");
						resp.flushBuffer();
						out.close();
						conn.close();
					}
				}
				else
					check = true;
			}
			catch (Exception e) {
				try {
					conn.rollback();
					conn.close();
					if (out != null)
						out.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					LogManager.getInstance().log(e1.getMessage());
				}
				e.printStackTrace();
				LogManager.getInstance().log(e.getMessage());
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			LogManager.getInstance().log("Error during data outputing");
			LogManager.getInstance().log(e.getMessage());
		}
		
		return check;
	}
	
}
