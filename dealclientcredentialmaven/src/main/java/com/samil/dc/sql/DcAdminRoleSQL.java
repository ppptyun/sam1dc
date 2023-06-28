package com.samil.dc.sql;

public final class DcAdminRoleSQL {
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 목록 조회
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_ROLE_LIST = 
			 						  "SELECT ROLECD "
			+ "								, ROLENM "
			+ "								, SORT "
			+ "						     FROM WEB_DCROLE "
			+ "						 ORDER BY SORT ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 멤버 목록 조회
	 * 
	 * <#ROLECD#> 롤 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_ROLE_MEMBER_LIST = 
					  "    SELECT A.ROLECD "
					+ "         , A.EMPNO "
					+ "         , H.KORNM AS EMPNM "
					+ "         , H.TEAMCD "
					+ "         , T.TEAMNM "
					+ "         , H.GRADCD "
					+ "         , SF_EMP_GRADNM(A.EMPNO) AS GRADNM "
					+ "      FROM WEB_DCROLEMBR A "
					+ "         , HREMP H "
					+ "         , CMTEAMCD T "
					+ "     WHERE A.ROLECD = <#ROLECD#> "
					+ "       AND H.EMPLNO = A.EMPNO "
					+ "       AND T.TEAMCD = H.TEAMCD "
					+ "     ORDER BY H.KORNM ASC, H.GRADCD ASC, A.EMPNO ASC ";

	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 신규 등록
	 * 
	 * <#ROLECD#> 롤 코드
	 * <#ROLENM#> 롤 이름
	 * <#SORT#> 순번
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_ROLE = 
					  "    INSERT INTO WEB_DCROLE ( "
					+ "           ROLECD "
					+ "         , ROLENM "
					+ "         , SORT "
					+ "         ) "
					+ "     VALUES ( "
					+ "           <#ROLECD#> "
					+ "         , <#ROLENM#> "
					+ "         , <#SORT#> "
					+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 수정
	 * 
	 * <#ROLECD#> 롤 코드
	 * <#ROLENM#> 롤 이름
	 * <#SORT#> 순번
	 * <#ORIROLECD#> 원래 롤 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_ROLE = 
					  "    UPDATE WEB_DCROLE "
					+ "       SET ROLECD = <#ROLECD#> "
					+ "         , ROLENM = <#ROLENM#> "
					+ "         , SORT   = <#SORT#>   "
					+ "     WHERE ROLECD = <#ORIROLECD#> ";

	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 삭제
	 * 
	 * <#ROLECD#> 롤 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_ROLE = 
					  "    DELETE FROM WEB_DCROLE "
					+ "     WHERE ROLECD = <#ROLECD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 멤버 신규 등록
	 * 
	 * <#ROLECD#> 롤 코드
	 * <#EMPNO#> 사번
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_ROLE_MEMBER = 
					  "    INSERT INTO WEB_DCROLEMBR ( "
					+ "           ROLECD "
					+ "         , EMPNO "
					+ "         ) "
					+ "     VALUES ( "
					+ "           <#ROLECD#> "
					+ "         , <#EMPNO#> "
					+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 멤버 삭제
	 * 
	 * <#ROLECD#> 롤 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_ROLE_MEMBER_BY_ROLE = 
					  "    DELETE FROM WEB_DCROLEMBR "
					+ "     WHERE ROLECD = <#ROLECD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 메뉴 삭제
	 * 
	 * <#ROLECD#> 롤 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_ROLE_MENU_BY_ROLE = 
					  "    DELETE FROM WEB_DCMENUMAP "
					+ "     WHERE ROLECD = <#ROLECD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 필드 삭제
	 * 
	 * <#ROLECD#> 롤 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_ROLE_FIELD_BY_ROLE = 
					  "    DELETE FROM WEB_DCFILDMAP "
					+ "     WHERE ROLECD = <#ROLECD#> ";	
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 멤버 삭제
	 * 
	 * <#ROLECD#> 롤 코드
	 * <#EMPNO#> 사번
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_ROLE_MEMBER_BY_EMP = 
					  "    DELETE FROM WEB_DCROLEMBR  "
					+ "     WHERE ROLECD = <#ROLECD#> "
					+ "       AND EMPNO  = <#EMPNO#>  ";
}
