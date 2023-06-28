package com.samil.dc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.samil.dc.access.DBConnection;
import com.samil.dc.core.ContextLoader;
import com.samil.dc.domain.CommCodeDetailBean;
import com.samil.dc.domain.CommCodeTreeBean;
import com.samil.dc.sql.DcAdminCommCodeSQL;
import com.samil.dc.sql.SQLManagement;

public class DcAdminCommCodeDAO {
	String PRGCD = "";
	DBConnection dbcon = null;
	
	
	public DcAdminCommCodeDAO(DBConnection _dbcon){
		dbcon = _dbcon;
		PRGCD = ContextLoader.getContext("ProgramCode");
	}
	
	public List<CommCodeTreeBean> sqlSelectCommCodeTree() throws SQLException{
		List<CommCodeTreeBean> list = new ArrayList<CommCodeTreeBean>();
		
		String sql = DcAdminCommCodeSQL.SELECT_COMM_CODE_TREE;
		SQLManagement sqlmng = new SQLManagement(sql);
		ResultSet rs = null;
		
		sqlmng.generate();
				
		rs = dbcon.executeQuery(sqlmng.getSql());
		
		if (rs != null) {
			while(rs.next()){
				CommCodeTreeBean cctVo = new CommCodeTreeBean();
				cctVo.setParcd(rs.getString("PARCD"));
				cctVo.setItemcd(rs.getString("ITEMCD"));
				cctVo.setItemnm(rs.getString("ITEMNM"));
				list.add(cctVo);
			}
		}
		
		return list;
	}
	
	public CommCodeDetailBean sqlSelectCommCodeDetail(String parcd, String itemcd) throws SQLException{
		CommCodeDetailBean cctVo = new CommCodeDetailBean();
		
		SQLManagement sqlmng = new SQLManagement(DcAdminCommCodeSQL.SELECT_COMM_CODE_DETAIL);
		ResultSet rs = null;
		
		sqlmng.addValueReplacement("PARCD", parcd);
		sqlmng.addValueReplacement("ITEMCD", itemcd);
		sqlmng.generate();
				
		rs = dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		
		if (rs.next()){
			cctVo.setParcd(rs.getString("PARCD"));
			cctVo.setItemcd(rs.getString("ITEMCD"));
			cctVo.setItemnm(rs.getString("ITEMNM"));
			cctVo.setDescp(rs.getString("DESCP"));
			cctVo.setUseyn(rs.getString("USEYN"));
			cctVo.setSort(rs.getString("SORT"));
			cctVo.setCreempno(rs.getString("CREEMPNO"));
			cctVo.setCredt(rs.getString("CREDT"));
			cctVo.setUpdempno(rs.getString("UPDEMPNM")+"("+rs.getString("UPDEMPNO")+") / "+rs.getString("UPDGRADNM"));
			cctVo.setUpddt(rs.getString("UPDDT"));
		}
		
		return cctVo;
	}
	
	public void sqlSaveCommCode(Map<String, Object> map, String saveState) throws SQLException{
		String sql = DcAdminCommCodeSQL.INSERT_COMM_CODE;
		if("U".equals(saveState)){
			sql = DcAdminCommCodeSQL.UPDATE_COMM_CODE;
		}
		
		SQLManagement sqlmng = new SQLManagement(sql);
		for(String key : map.keySet()){
			sqlmng.addValueReplacement(key, String.valueOf(map.get(key)));
		}
		sqlmng.generate();
		dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
		
	}
	
	
	public void sqlDeleteCommCode(String parcd, String itemcd) throws SQLException{
			
			String sql = DcAdminCommCodeSQL.DELETE_COMM_CODE;
			SQLManagement sqlmng = new SQLManagement(sql);
			sqlmng.addValueReplacement("PARCD", parcd);
			sqlmng.addValueReplacement("ITEMCD", itemcd);
			sqlmng.generate();

			dbcon.executeQuery(sqlmng.getSql(), sqlmng.getParameter());
			
	}
	
}
