package com.samil.dc.sql;

public final class DcAdminPESQL {
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PE 관리 목록 조회
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_PE_LIST = 
			  "    SELECT B.GLINDUCD "
			+ "         , B.GLINDUNMK "
			+ "         , B.GLINDUNM "
			+ "         , A.INDUCD "
			+ "         , A.INDUNM "
			+ "         , A.INDUNME "
			+ "         , DECODE( C.INDUCD, NULL, '0', '1') AS CHKVAL "
			+ "      FROM PMINDUCD A "
			+ "         , PMINDGLCD B "
			+ "         , WEB_DCCREDPE C "
			+ "     WHERE B.GLINDUCD = A.GLINDUCD "
			+ "       AND A.STATUS = '1' "
			+ "       AND B.STATUS = '1' "
			+ "       AND C.INDUCD(+) = A.INDUCD "
			+ "     ORDER BY B.GLINDUCD ASC, A.INDUCD ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PE 관리 목록 등록
	 * 
	 * <#INDUCD#>
	 * <#CREEMPNO#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_PE = 
			  "    INSERT INTO WEB_DCCREDPE ( "
			+ "           INDUCD "
			+ "         , CREEMPNO "
			+ "         , CREDT "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#INDUCD#> "
			+ "         , <#CREEMPNO#> "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PE 관리 목록 삭제
	 * 
	 * <#INDUCD#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_PE = 
			  "    DELETE FROM WEB_DCCREDPE "
			+ "     WHERE INDUCD = <#INDUCD#> ";
}
