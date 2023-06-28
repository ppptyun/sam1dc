package com.samil.dc.sql;

public final class DcAdminConsultSQL {
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문사 관리 목록 조회
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_CONSULT_LIST = 
			  "    SELECT COMPCD "
			+ "         , COMPHANNM "
			+ "         , COMPENGNM "
			+ "         , CASE FINANCEYN WHEN 'Y' then 1 WHEN 'N' THEN 0 END AS FINANCEYN "
			+ "         , CASE AUDITYN WHEN 'Y' then 1 WHEN 'N' THEN 0 END AS AUDITYN "
			+ "         , CASE LAWYN WHEN 'Y' then 1 WHEN 'N' THEN 0 END AS LAWYN "
			+ "         , CASE USEYN WHEN 'Y' then 1 WHEN 'N' THEN 0 END AS USEYN "
			+ "      FROM WEB_DCCONSULT "
			+ "     ORDER BY SORT ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문사 관리 목록 저장
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_CONSULT = 
			  "    UPDATE WEB_DCCONSULT "
		  	+ "       SET FINANCEYN = <#FINANCEYN#> "
		  	+ "         , COMPHANNM = <#COMPHANNM#> "
		  	+ "         , COMPENGNM = <#COMPENGNM#> "
			+ "         , AUDITYN = <#AUDITYN#> "
			+ "         , LAWYN = <#LAWYN#> "
			+ "         , USEYN = <#USEYN#> "
			+ "     WHERE COMPCD = <#COMPCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문사 관리 목록 저장
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_CONSULT = 
			  "    INSERT INTO WEB_DCCONSULT ( "
		  	+ "       	COMPCD "
		  	+ "       	, COMPHANNM "
		  	+ "       	, COMPENGNM "
		  	+ "       	, FINANCEYN "
		  	+ "       	, AUDITYN "
		  	+ "       	, LAWYN "
		  	+ "       	, USEYN "
		  	+ "       	, SORT "
		  	+ "       	, CREEMPNO "
		  	+ "       	, CREDT "
		  	+ "         ) "
		  	+ "     VALUES ( "
		  	+ "           (SELECT MAX(ROWNUM) + 1 FROM WEB_DCCONSULT) "
			+ "         , <#COMPHANNM#> "
			+ "         , <#COMPENGNM#> "
			+ "         , <#FINANCEYN#> "
			+ "         , <#AUDITYN#> "
			+ "         , <#LAWYN#> "
			+ "         , <#USEYN#> "
			+ "         , (SELECT MAX(ROWNUM) + 1 FROM WEB_DCCONSULT) "
			+ "         , <#CREEMPNO#> "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문사 관리 목록 삭제 (사용안함처리)
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_CONSULT = 
			  "    UPDATE WEB_DCCONSULT "
		  	+ "       SET USEYN = 'N' "
			+ "     WHERE COMPCD = <#COMPCD#> ";
	
}