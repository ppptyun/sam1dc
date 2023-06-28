package com.samil.dc.access;

import java.io.IOException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.Executor;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DBConnection implements Connection{
	private static final Logger logger = Logger.getRootLogger();
	protected Connection conn = null;
	private java.util.List<Statement> statementList = new java.util.ArrayList<Statement>(); // statement를 저장
	private java.util.List<ResultSet> rsList = new java.util.ArrayList<ResultSet>(); // statement를 저장

	public DBConnection() {

	};

	public DBConnection(Connection _conn) {
		conn = _conn;
	};

	public Connection getConnection() {
		return conn;
	}

	public String getParameter(String param) {
		return param == null ? "" : param;
	}

	public boolean isNull(String param) {
		return param == null || "".equals(param) ? true : false;
	}

	public void closeAllStatement(){
		for (int i = 0 ; i < statementList.size() ; i++) {
			Statement stmt = (Statement)statementList.get(i);
			if (stmt != null)
				try {
					stmt.close();
					//					logger.debug("~~~~~~~~~~~~~~~~~~statment	종료(" + i + ")~~~~~~~~~~~~~~~~");
				} catch (SQLException ex) {
				}
		}
	}

	public void closeAllResultSet(){
		for (int i = 0 ; i < rsList.size() ; i++) {
			ResultSet rs = (ResultSet)rsList.get(i);
			if (rs != null)
				try {
					rs.close();
					//					logger.debug("~~~~~~~~~~~~~~~~~~resultset	종료(" + i + ")~~~~~~~~~~~~~~~~");
				} catch (SQLException ex) {
				}
		}
	}

	public void close(){
		this.closeAllResultSet();
		this.closeAllStatement();
		if (conn != null)
			try {
				conn.close();
				//				logger.debug("~~~~~~~~~~~~~~~~~~connection	종료   ~~~~~~~~~~~~~~~~");
			} catch (SQLException e) {
			}
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		PreparedStatement pstmt = this.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		logger.debug(getQueryString(sql, null));

		rsList.add(rs);
		return rs;
	}

	public ResultSet executeQuery(String sql, ArrayList<Object> param) throws SQLException {
		ArrayList<Object> parameterValues = new ArrayList<Object>();
		parameterValues.add(null);
		this.setAutoCommit(false);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = this.prepareStatement(sql);
			applyPreparedStatement(pstmt, param, parameterValues);
			logger.debug(getQueryString(sql, parameterValues));
			rs = pstmt.executeQuery();

			rsList.add(rs);
			this.commit();
		} catch (SQLException e) {
			this.rollback();
			e.printStackTrace();
		} 
		/*
		finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 */
		return rs;
	}

	public void executeProcedure(String sql, ArrayList<Object> param) throws SQLException {
		ArrayList<Object> parameterValues = new ArrayList<Object>();
		parameterValues.add(null);

		CallableStatement cs = null;
		cs = this.prepareCall(sql);

		for (int i = 0; i < param.size(); i++) {
			if (param.get(i) == null) {
				cs.setNull(i + 1, java.sql.Types.NULL);
			} else if (param.get(i) instanceof Integer) {
				cs.setInt(i + 1, (Integer) param.get(i));
			} else {
				cs.setString(i + 1, (String) param.get(i));
			}
			parameterValues.add(param.get(i));
		}
		logger.debug(getQueryString(sql, parameterValues));
		cs.execute();
	}

	public int executeUpdate(String sql, ArrayList<Object> param) throws SQLException {
		ArrayList<Object> parameterValues = new ArrayList<Object>();
		parameterValues.add(null);
		this.setAutoCommit(false);

		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = this.prepareStatement(sql);
			applyPreparedStatement(pstmt, param, parameterValues);
			logger.debug(getQueryString(sql, parameterValues));
			result = pstmt.executeUpdate();
			pstmt.close();
			this.commit();
		} catch (SQLException e) {
			this.rollback();
			e.printStackTrace();
		} finally {
			// important !!!
			// ORA-01000: 최대 열기 커서 수를 초과했습니다
			// 현재 상황에서 약 300개를 넘어서면 위의 오류 발생
			// 반드시 사용한 PreparedStatement 닫아야 한다.
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return result;
	}

	public void excuteBatchQuery(String sql, ArrayList<ArrayList<Object>> param) throws SQLException {
		this.excuteBatchQuery(sql, param, 100);
	}

	public void excuteBatchQuery(String sql, ArrayList<ArrayList<Object>> param, int size) throws SQLException {
		this.setAutoCommit(false);

		PreparedStatement pstmt = null;
		int batch_size = size;

		try {
			pstmt = this.prepareStatement(sql);
			int i=0;
			for (i = 0; i < param.size(); i++) {

				ArrayList<Object> parameterValues = new ArrayList<Object>();
				parameterValues.add(null);
				ArrayList<Object> paramObj = param.get(i);

				applyPreparedStatement(pstmt, paramObj, parameterValues);
				logger.debug(getQueryString(sql, parameterValues));
				pstmt.addBatch();

				if((i+1)%batch_size == 0){
					pstmt.executeBatch();
					this.commit();
				}
			}
			// 마지막으로 남은 것을 반영하기 위한 것
			if((i+1)%batch_size != 0){
				pstmt.executeBatch();
				this.commit();	
			}
		} catch (SQLException e) {
			this.rollback();
			logger.info("excuteBatchQuery : SQLException 발생");
			e.printStackTrace();
		}
	}

	public String getQueryString(String sql, ArrayList<Object> parameterValues) {
		StringBuffer buf = new StringBuffer();

		int qMarkCount = 0;
		if (parameterValues != null) {

			StringTokenizer tok = new StringTokenizer(sql + " ", "?");
			while (tok.hasMoreTokens()) {
				String oneChunk = tok.nextToken();
				buf.append(oneChunk);

				try {
					Object value;

					if (parameterValues.size() > 1 + qMarkCount) {
						value = parameterValues.get(1 + qMarkCount++);

						if (value instanceof Integer || value instanceof Long) {
							buf.append("" + value + "");
						} else {
							buf.append("'" + value + "'");
						}
					} else {
						if (tok.hasMoreTokens()) {
							value = null;
						} else {
							value = "";
						}
					}
				} catch (Throwable e) {
					buf.append("ERROR WHEN PRODUCING QUERY STRING FOR LOG." + e.toString());
					// catch this without whining, if this fails the only thing
					// wrong is probably this class
				}
			}
			return buf.toString().trim();
		} else {
			return sql;
		}

	}

	@SuppressWarnings("unchecked")
	public String getArrayString(ResultSet rs) {
		ResultSetMetaData rsmd = null;
		JSONArray tmpArray = new JSONArray();

		try {
			rsmd = rs.getMetaData();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			while (rs.next()) {
				JSONObject tmpObject = new JSONObject();
				if (rsmd != null) {
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						tmpObject.put(rsmd.getColumnName(i).toLowerCase(), (rs.getString(i) == null ? "" : rs.getString(i)));
					}
				}
				tmpArray.add(tmpObject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tmpArray.toString();
	}

	public void outputResponse(String outStr, String type, HttpServletResponse res) throws IOException {
		java.io.Writer out = null;
		res.setCharacterEncoding("utf-8");
		String contentType = "";
		if ("xml".equalsIgnoreCase(type)) {
			contentType = "text/xml;charset=utf-8";
		} else if ("json".equalsIgnoreCase(type)) {
			contentType = "text/json;charset=utf-8";
		} else {
			contentType = "text;charset=utf-8";
		}
		res.setContentType(contentType);
		out = res.getWriter();
		out.write(outStr);
		res.flushBuffer();
		out.close();
	}




	/*
	 * (non-Javadoc)
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */

	// 2023.04 cm
	// @override 삭제
	//@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return this.conn.unwrap(iface);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.conn.isWrapperFor(iface);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public Statement createStatement() throws SQLException {
		return this.conn.createStatement();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		PreparedStatement stmt = this.conn.prepareStatement(sql);
		statementList.add(stmt);
		return stmt;
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		CallableStatement cs = this.conn.prepareCall(sql);
		statementList.add(cs);
		return cs;
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public String nativeSQL(String sql) throws SQLException {
		return this.conn.nativeSQL(sql);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		this.conn.setAutoCommit(autoCommit);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public boolean getAutoCommit() throws SQLException {
		return this.conn.getAutoCommit();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void commit() throws SQLException {
		this.conn.commit();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void rollback() throws SQLException {
		this.conn.rollback();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public boolean isClosed() throws SQLException {
		return this.conn.isClosed();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return this.conn.getMetaData();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		this.conn.setReadOnly(readOnly);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public boolean isReadOnly() throws SQLException {
		return this.conn.isReadOnly();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void setCatalog(String catalog) throws SQLException {
		this.conn.setCatalog(catalog);

	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public String getCatalog() throws SQLException {
		return this.conn.getCatalog();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void setTransactionIsolation(int level) throws SQLException {
		this.conn.setTransactionIsolation(level);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public int getTransactionIsolation() throws SQLException {
		return this.conn.getTransactionIsolation();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public SQLWarning getWarnings() throws SQLException {
		return this.conn.getWarnings();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void clearWarnings() throws SQLException {
		this.conn.clearWarnings();

	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return this.conn.createStatement(resultSetType, resultSetConcurrency);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {

		PreparedStatement stmt = this.conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
		statementList.add(stmt);
		return stmt;
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		CallableStatement cs = this.conn.prepareCall(sql, resultSetType, resultSetConcurrency);
		statementList.add(cs);
		return cs;
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return this.conn.getTypeMap();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		this.conn.setTypeMap(map);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void setHoldability(int holdability) throws SQLException {
		this.conn.setHoldability(holdability);	
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public int getHoldability() throws SQLException {
		return this.conn.getHoldability();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public Savepoint setSavepoint() throws SQLException {
		return this.conn.setSavepoint();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		return this.conn.setSavepoint(name);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		this.conn.rollback(savepoint);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		this.conn.releaseSavepoint(savepoint);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		PreparedStatement stmt = this.conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
		statementList.add(stmt);
		return stmt;
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		CallableStatement cs = this.conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		statementList.add(cs);
		return cs;
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		PreparedStatement stmt = this.conn.prepareStatement(sql, autoGeneratedKeys);
		statementList.add(stmt);
		return stmt;
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		PreparedStatement stmt = this.conn.prepareStatement(sql, columnIndexes);
		statementList.add(stmt);
		return stmt;
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		PreparedStatement stmt = this.conn.prepareStatement(sql, columnNames);
		statementList.add(stmt);
		return stmt;
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public Clob createClob() throws SQLException {
		return this.conn.createClob();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public Blob createBlob() throws SQLException {
		return this.conn.createBlob();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public NClob createNClob() throws SQLException {
		return this.conn.createNClob();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public SQLXML createSQLXML() throws SQLException {
		return this.conn.createSQLXML();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public boolean isValid(int timeout) throws SQLException {
		return this.conn.isValid(timeout);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		this.conn.setClientInfo(name, value);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		this.conn.setClientInfo(properties);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public String getClientInfo(String name) throws SQLException {
		return this.conn.getClientInfo(name);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public Properties getClientInfo() throws SQLException {
		return this.conn.getClientInfo();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return this.conn.createArrayOf(typeName, elements);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		return this.conn.createStruct(typeName, attributes);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void setSchema(String schema) throws SQLException {
		this.conn.setSchema(schema);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public String getSchema() throws SQLException {
		return this.conn.getSchema();
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void abort(Executor executor) throws SQLException {
		this.conn.abort(executor);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		this.conn.setNetworkTimeout(executor, milliseconds);
	}

	// 2023.04 cm
	// @override 삭제
	//@Override
	public int getNetworkTimeout() throws SQLException {
		return this.conn.getNetworkTimeout();
	}

	private void applyPreparedStatement(PreparedStatement prst, ArrayList<Object> param, ArrayList<Object> parameterValues) throws SQLException{
		for (int i = 0; i < param.size(); i++) {
			if (param.get(i) == null) {
				prst.setNull(i + 1, java.sql.Types.NULL);
			} else if (param.get(i) instanceof Integer) {
				prst.setInt(i + 1, (Integer) param.get(i));
			} else if (param.get(i) instanceof Long) {
				prst.setLong(i + 1, (Long) param.get(i));
			} else {
				prst.setString(i + 1, (String) param.get(i));
			}
			parameterValues.add(param.get(i));
		}
	}

}
