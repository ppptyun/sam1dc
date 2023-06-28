package com.samil.dc.sql;

public final class DcAdminCommCodeSQL {
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 공통코드 트리 조회
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_COMM_CODE_TREE = 
			 						  "SELECT LEVEL"
			+ "								, PARCD"
			+ "								, ITEMCD"
			+ "								, ITEMNM"
			+ "						     FROM WEB_DCCOMCD"
			+ "						    START WITH PARCD = 'ROOT'"
			+ "						  CONNECT BY PRIOR ITEMCD = PARCD"
			+ "						 ORDER BY LEVEL ASC, SORT ASC";
	
	public static final String SELECT_COMM_CODE_DETAIL = 
									  "SELECT PARCD"
			+ "								, ITEMCD"
			+ "								, ITEMNM"
			+ "								, DESCP"
			+ "								, USEYN"
			+ "								, SORT"
			+ "								, CREEMPNO"
			+ "								, CREDT"
			+ "								, UPDEMPNO"
			+ "								, SF_EMP_NAME(UPDEMPNO) AS UPDEMPNM"
			+ "								, SF_EMP_GRADNM(UPDEMPNO) AS UPDGRADNM" 
			+ "								, UPDDT"
			+ "						     FROM WEB_DCCOMCD"
			+ "                         WHERE PARCD = <#PARCD#> "
			+ "                           AND ITEMCD = <#ITEMCD#> "
			;
	
	public static final String UPDATE_COMM_CODE = 
						  			      "UPDATE WEB_DCCOMCD"
			+ "								  SET ITEMNM = <#itemnm#>"
			+ "									, USEYN = <#useyn#>"
			+ "									, SORT = <#sort#>"
			+ "									, DESCP = <#descp#>"
			+ "									, UPDEMPNO = <#updempno#>"
			+ "									, UPDDT = SYSDATE"
			+ "							    WHERE ITEMCD = <#itemcd#>"
			;


		public static final String INSERT_COMM_CODE = 
						"INSERT INTO WEB_DCCOMCD ("
			+ "									  PARCD"
			+ "									, ITEMCD"
			+ "									, ITEMNM"
			+ "									, USEYN"
			+ "									, SORT"
			+ "									, DESCP"
			+ "									, CREEMPNO"
			+ "									, CREDT"
			+ "									, UPDEMPNO"
			+ "									, UPDDT"	
			+ "									)"
			+ "								 VALUES ("
			+ "									  <#parcd#>"
			+ "									, <#itemcd#>"
			+ "									, <#itemnm#>"
			+ "									, <#useyn#>"
			+ "									, <#sort#>"
			+ "									, <#descp#>"			
			+ "									, <#updempno#>"
			+ "									, SYSDATE"
			+ "									, <#updempno#>"
			+ "									, SYSDATE"	
			+ "									)"
			;
	
	
	public static final String DELETE_COMM_CODE =
						  			  "DELETE"
			+ "						     FROM WEB_DCCOMCD"
			+ "                         WHERE PARCD = <#PARCD#> "
			+ "                           AND ITEMCD = <#ITEMCD#> "
			;
	
	
}
