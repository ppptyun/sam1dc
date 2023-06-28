package com.samil.dc.sql;

public final class DcAdminMenuSQL {
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 공통코드 트리 조회
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_MENU_TREE = 
			 						  "SELECT UPMENUID"
			+ "								, MENUID"
			+ "								, MENUNM"
			+ "						     FROM WEB_DCMENU"
			+ "						 ORDER BY DECODE( NVL(UPMENUID, 'ROOT'), 'ROOT', 0, UPMENUID) ASC, SORT ASC";
	
	public static final String SELECT_MENU_DETAIL = 
									  "SELECT UPMENUID"
			+ "								, MENUID"
			+ "								, MENUNM"
			+ "								, URL"
			+ "								, SORT"
			+ "						     FROM WEB_DCMENU"
			+ "                         WHERE (UPMENUID = <#UPMENUID#> OR <#UPMENUID#> IS NULL)"
			+ "                           AND MENUID = <#MENUID#> "
			;
	
	public static final String UPDATE_MENU = 
						  				  "UPDATE WEB_DCMENU"
			+ "								  SET MENUNM = <#menunm#>"
			+ "									, URL = <#url#>"
			+ "									, SORT = <#sort#>"
			+ "								WHERE MENUID = <#menuid#>"
			;
			
	
	public static final String INSERT_MENU = 
						"INSERT INTO WEB_DCMENU ( UPMENUID"
			+ "									, MENUID"
			+ "									, MENUNM"
			+ "									, URL"
			+ "									, SORT"
			+ "									)"
			+ "								 VALUES ("
			+ "									  <#upmenuid#>"
			+ "									, <#menuid#>"
			+ "									, <#menunm#>"
			+ "									, <#url#>"
			+ "									, <#sort#>"
			+ "									)"
			;

	public static final String DELETE_MENU =
						  			  "DELETE"
			+ "						     FROM WEB_DCMENU"
			+ "                         WHERE MENUID = <#MENUID#> "
			;
	
	public static final String DELETE_MENU_MAP_BY_MENU = 
			  "    DELETE"
			+ " 	 FROM WEB_DCMENUMAP"
			+ "     WHERE MENUID  = <#MENUID#> "
			;
	
	public static final String SELECT_MENU_MAPPED_ROLE_LIST = 
						  				"SELECT ROLECD"
			+ "						     FROM WEB_DCMENUMAP"
			+ "                         WHERE MENUID = <#MENUID#> "
			;
	
	
	
	
	public static final String INSERT_MENU_MAP = 
			  "    INSERT INTO WEB_DCMENUMAP ( "
			+ "           MENUID "
			+ "         , ROLECD "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#MENUID#> "
			+ "         , <#ROLECD#> "
			+ "         ) "
			;

	public static final String UPDATE_MENU_MAP = 
			  "    UPDATE WEB_DCMENUMAP "
			+ "       SET ROLECD  = <#ROLECD#>    "
			+ "     WHERE MENUID  = <#MENUID#>    "
			+ "       AND ROLECD  = <#ORIGINROLECD#> "
			;	

	public static final String DELETE_MENU_MAP = 
			  "    DELETE"
			+ " 	 FROM WEB_DCMENUMAP"
			+ "     WHERE MENUID  = <#MENUID#> "
			+ "       AND ROLECD  = <#ROLECD#> "
			;
	
}
