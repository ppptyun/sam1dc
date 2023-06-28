package com.samil.dc.sql;

public final class DcAdminEditAuthSQL {
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 관리 목록 조회
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_EDIT_AUTH_LIST = 
			  "    SELECT EA.ROLECD "
			+ "         , RL.ROLENM "
			+ "         , EA.EDITYN "
			+ "         , TO_CHAR(EA.UPDDT, 'YYYY-MM-DD') AS UPDDT "
			+ "      FROM WEB_DCEDITAUTH EA "
			+ "         , WEB_DCROLE RL "
			+ "     WHERE RL.ROLECD = EA.ROLECD "
			+ "    ORDER BY EA.ROLECD ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 관리 조회
	 * 
	 * <#ROLECD#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_EDIT_AUTH_DETAIL = 
			  "    SELECT EA.ROLECD "
			+ "         , RL.ROLENM "
			+ "         , EA.EDITYN "
			+ "         , TO_CHAR(EA.UPDDT, 'YYYY-MM-DD') AS UPDDT "
			+ "      FROM WEB_DCEDITAUTH EA "
			+ "         , WEB_DCROLE RL "
			+ "     WHERE RL.ROLECD = EA.ROLECD "
			+ "       AND EA.ROLECD = <#ROLECD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 관리 등록
	 * 
	 * <#ROLECD#>
	 * <#EDITYN#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_EDIT_AUTH = 
			  "    INSERT INTO WEB_DCEDITAUTH ( "
			+ "           ROLECD "
			+ "         , EDITYN "
			+ "         , UPDDT "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#ROLECD#> "
			+ "         , <#EDITYN#> "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 관리 수정
	 * 
	 * <#ORIGINROLECD#>
	 * <#ROLECD#>
	 * <#EDITYN#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_EDIT_AUTH = 
			  "    UPDATE WEB_DCEDITAUTH "
			+ "       SET ROLECD = <#ROLECD#> "
			+ "         , EDITYN = <#EDITYN#> "
			+ "     WHERE ROLECD = <#ORIGINROLECD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 관리 삭제
	 * 
	 * <#ROLECD#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_EDIT_AUTH = 
			  "    DELETE FROM WEB_DCEDITAUTH "
			+ "     WHERE ROLECD = <#ROLECD#> ";
}
