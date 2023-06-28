package com.samil.dc.sql;

public final class DcAdminHQEmpSQL {
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Deal 본부 목록 동기화(From View)
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String MERGE_DEAL_HQ_SYNC_FROM_VIEW = 
			  "    MERGE INTO WEB_DCHQ S "
			+ "    USING ( "
			+ "               SELECT LV "
			+ "                    , SUPORGID "
			+ "                    , ORGID "
			+ "                    , HQCD "
			+ "                    , HQNM "
			+ "                    , ROWNUM AS RNUM "
			+ "                FROM ( "
			+ "                         SELECT LV AS LV "
			+ "                              , SUPER_ORG_ID AS SUPORGID "
			+ "                              , ORG_ID AS ORGID "
			+ "                              , SEARCH_CD AS HQCD "
			+ "                              , ORG_CHART_NM AS HQNM "
			+ "                           FROM SP_ORG_V "
			+ "                          WHERE ( SEARCH_CD LIKE 'F%' OR SEARCH_CD LIKE 'AN%' ) "
			+ "                      ) "
			+ "          ) A "
			+ "       ON ( S.HQCD = A.HQCD ) "
			+ "     WHEN MATCHED THEN "
			+ "          UPDATE SET HQNM = A.HQNM "
			+ "                   , LV = A.LV "
			+ "                   , SUPORGID = A.SUPORGID "
			+ "                   , ORGID = A.ORGID "
			+ "                   , ORGSORT = A.RNUM "
			+ "     WHEN NOT MATCHED THEN "
			+ "          INSERT ( "
			+ "                     HQCD "
			+ "                   , HQNM "
			+ "                   , LV "
			+ "                   , SUPORGID "
			+ "                   , ORGID "
			+ "                   , ORGSORT "
			+ "                 ) "
			+ "          VALUES ( "
			+ "                     A.HQCD "
			+ "                   , A.HQNM "
			+ "                   , A.LV "
			+ "                   , A.SUPORGID "
			+ "                   , A.ORGID "
			+ "                   , A.RNUM "
			+ "                 ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 본부 담당자 목록 조회
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_HQ_EMP_LIST = 
			  "    SELECT HQ.HQCD "
			+ "         , HQ.HQNM "
			+ "         , ( SELECT SB.HQNM FROM WEB_DCHQ SB WHERE SB.HQCD = SUBSTR(HQ.HQCD, 1, 2)||'0' ) AS GRPNM "
			+ "         , EP.EMPNO "
			+ "         , SF_EMP_NAME(EP.EMPNO) AS EMPNM "
			+ "         , SF_EMP_GRADNM(EP.EMPNO) AS GRADNM "
			+ "      FROM WEB_DCHQ HQ "
			+ "         , WEB_DCHQEMP EP "
			+ "    WHERE EP.HQCD(+) = HQ.HQCD "
			+ "    ORDER BY HQ.ORGSORT ASC, HQ.HQCD ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 본부 담당자 등록
	 * 
	 * <#HQCD#>
	 * <#EMPNO#>
	 * <#CREEMPNO#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_HQ_EMP = 
			  "    INSERT INTO WEB_DCHQEMP ( "
			+ "           HQCD "
			+ "         , EMPNO "
			+ "         , CREEMPNO "
			+ "         , CREDT "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#HQCD#> "
			+ "         , <#EMPNO#> "
			+ "         , <#CREEMPNO#> "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 본부 담당자 삭제
	 * 
	 * <#HQCD#>
	 * <#EMPNO#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_HQ_EMP = 
			  "    DELETE FROM WEB_DCHQEMP "
			+ "     WHERE HQCD = <#HQCD#> "
			+ "       AND EMPNO = <#EMPNO#> ";
}
