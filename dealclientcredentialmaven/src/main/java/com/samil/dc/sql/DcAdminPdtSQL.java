package com.samil.dc.sql;

public final class DcAdminPdtSQL {
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 참조년도 목록 조회
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_PDT_REFYEARLY_LIST = 
			  "   SELECT DISTINCT REFYEARLY "
			+ "     FROM WEB_DCPDT "
			+ "    ORDER BY REFYEARLY DESC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 카테고리 목록 조회
	 * 
	 * <#REFYEARLY#> 참조년도
	 * <#USEYN_A#> 사용여부 옵션 1
	 * <#USEYN_B#> 사용여부 옵션 2
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_CATEGORY_LIST = 
			  "   SELECT B.REFYEARLY "
			+ "        , B.CTGCD "
			+ "        , REPLACE(B.CTGNM, chr(38)||'amp;', '&') CTGNM  "
			+ "        , B.USEYN "
			+ "     FROM WEB_DCPDTCTG B "
			+ "    WHERE B.REFYEARLY = <#REFYEARLY#> "
			+ "      AND ( B.USEYN = <#USEYN_A#> OR B.USEYN = <#USEYN_B#> ) "
			+ "    ORDER BY B.CTGCD ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 목록 조회
	 * 
	 * <#REFYEARLY#> 참조년도
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_PDT_LIST = 
			  "   SELECT A.REFYEARLY "
			+ "        , A.CTGCD "
			+ "        , B.CTGNM "
			+ "        , A.PDTCD "
			+ "        , REPLACE(A.PDTNM, chr(38)||'amp;', '&') PDTNM "	
			+ "        , A.COMNM "
			+ "        , A.CREDCD "
			+ "        , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = A.CREDCD AND CM.USEYN = 'Y' ) AS CREDNM "
			+ "        , A.CONSCD1 "
			+ "        , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = A.CONSCD1 AND CM.USEYN = 'Y' ) AS CONSNM1 "
			+ "        , A.CONSCD2 "
			+ "        , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = A.CONSCD2 AND CM.USEYN = 'Y' ) AS CONSNM2 "
			+ "        , A.CONSCD3 "
			+ "        , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = A.CONSCD3 AND CM.USEYN = 'Y' ) AS CONSNM3 "
			+ "        , A.SAMACTCD "
			+ "        , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = A.SAMACTCD AND CM.USEYN = 'Y' ) AS SAMACTNM "
			+ "        , A.SAMBIZCD "
			+ "        , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = A.SAMBIZCD AND CM.USEYN = 'Y' ) AS SAMBIZNM "
			+ "        , A.USEYN "
			+ "     FROM WEB_DCPDT A "
			+ "        , WEB_DCPDTCTG B "
			+ "    WHERE A.REFYEARLY = <#REFYEARLY#> "
			+ "      AND B.REFYEARLY = A.REFYEARLY "
			+ "      AND B.CTGCD = A.CTGCD "
			+ "    ORDER BY A.CTGCD ASC, A.PDTCD ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT SYSTEM 카테고리 목록 조회
	 * 
	 * <#REFYEARLY#> 참조년도
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_SYSTEM_CATEGORY_LIST = 
			   "  SELECT <#REFYEARLY#> AS REFYEARLY "
			 + "       , CTGCD "
			 + "       , SF_ETC_NAME('PM', '64', CTGCD) AS CTGNM "
			 + "    FROM ( "
			 + "            SELECT DISTINCT TYP12 AS CTGCD "
			 + "              FROM PMPRDUHD "
			 + "             WHERE STATUS = '1' "
			 + "               AND YEARLY = <#REFYEARLY#> "
			 + "            MINUS  "
			 + "            SELECT CTGCD "
			 + "              FROM WEB_DCPDTCTG "
			 + "             WHERE REFYEARLY = <#REFYEARLY#> "
			 + "         ) "
			 + "  ORDER BY CTGCD ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT SYSTEM 추가 가능 목록 조회
	 * 
	 * <#REFYEARLY#> 참조년도
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_SYSTEM_PDT_ADDABLE_LIST = 
			  "    SELECT C.YEARLY AS REFYEARLY "
			+ "         , C.TYP12 AS CTGCD "
			+ "         , SF_ETC_NAME('PM', '64', C.TYP12) AS CTGNM "
			+ "         , C.PRDTCD AS PDTCD "
			+ "         , REPLACE(C.PTDTNM, chr(38)||'amp;', '&') PDTNM "
			+ "         , C.COMNM AS COMNM "
			+ "      FROM ( "
			+ "              SELECT DISTINCT PRDTCD "
			+ "                FROM PMPRDUHD "
			+ "               WHERE STATUS = '1' "
			+ "                 AND YEARLY = <#REFYEARLY#> "
			+ "              MINUS "
			+ "              SELECT DISTINCT PDTCD AS PRDTCD "
			+ "                FROM WEB_DCPDT "
			+ "               WHERE REFYEARLY = <#REFYEARLY#> "
			+ "           ) A "
			+ "         , ( "
			+ "              SELECT DISTINCT CTGCD AS TYP12 "
			+ "                FROM WEB_DCPDTCTG "
			+ "               WHERE REFYEARLY = <#REFYEARLY#> "
			+ "           ) B "
			+ "         , PMPRDUHD C "
			+ "     WHERE C.STATUS = '1' "
			+ "       AND C.YEARLY = <#REFYEARLY#> "
			+ "       AND C.PRDTCD = A.PRDTCD "
			+ "       AND C.TYP12 = B.TYP12 "
			+ "     ORDER BY C.TYP12 ASC, C.PRDTCD ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 복사해서 만들 참조년도 조회
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_NEXT_REFYEARLY = " SELECT MAX(REFYEARLY) + 1 AS REFYEARLY FROM WEB_DCPDT ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 카테고리 저장
	 * 
	 * <#REFYEARLY#>
	 * <#CTGCD#>
	 * <#CTGNM#>
	 * <#EMPNO#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String MERGE_CAGEGORY = 
			  "    MERGE INTO WEB_DCPDTCTG "
			+ "    USING DUAL "
			+ "       ON ( REFYEARLY = <#REFYEARLY#> AND CTGCD = <#CTGCD#> ) "
			+ "     WHEN MATCHED THEN "
			+ "          UPDATE SET CTGNM = <#CTGNM#> "
			+ "                   , UPDEMPNO = <#EMPNO#> "
			+ "                   , UPDDT = SYSDATE "
			+ "     WHEN NOT MATCHED THEN "
			+ "          INSERT ( "
			+ "                     REFYEARLY "
			+ "                   , CTGCD "
			+ "                   , CTGNM "
			+ "                   , USEYN "
			+ "                   , CREEMPNO "
			+ "                   , CREDT "
			+ "                   , UPDEMPNO "
			+ "                   , UPDDT "
			+ "                 ) "
			+ "          VALUES ( "
			+ "                     <#REFYEARLY#> "
			+ "                   , <#CTGCD#> "
			+ "                   , <#CTGNM#> "
			+ "                   , 'Y' "
			+ "                   , <#EMPNO#> "
			+ "                   , SYSDATE "
			+ "                   , <#EMPNO#> "
			+ "                   , SYSDATE "
			+ "                 ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 저장
	 * 
	 * <#REFYEARLY#>
	 * <#PDTCD#>
	 * <#CTGCD#>
	 * <#PDTNM#>
	 * <#COMNM#>
	 * <#CREDCD#>
	 * <#CONSCD1#>
	 * <#CONSCD2#>
	 * <#CONSCD3#>
	 * <#SAMACTCD#>
	 * <#SAMBIZCD#>
	 * <#USEYN#>
	 * <#EMPNO#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String MERGE_PDT = 
			  "    MERGE INTO WEB_DCPDT "
			+ "    USING DUAL "
			+ "       ON ( REFYEARLY = <#REFYEARLY#> AND PDTCD = <#PDTCD#> ) "
			+ "     WHEN MATCHED THEN "
			+ "          UPDATE SET CTGCD = <#CTGCD#> "
			+ "                   , PDTNM = <#PDTNM#> "
			+ "                   , COMNM = <#COMNM#> "
			+ "                   , CREDCD = <#CREDCD#> "
			+ "                   , CONSCD1 = <#CONSCD1#> "
			+ "                   , CONSCD2 = <#CONSCD2#> "
			+ "                   , CONSCD3 = <#CONSCD3#> "
			+ "                   , SAMACTCD = <#SAMACTCD#> "
			+ "                   , SAMBIZCD = <#SAMBIZCD#> "
			+ "                   , USEYN = <#USEYN#> "
			+ "                   , UPDEMPNO = <#EMPNO#> "
			+ "                   , UPDDT = SYSDATE "
			+ "     WHEN NOT MATCHED THEN "
			+ "          INSERT ( "
			+ "                     REFYEARLY "
			+ "                   , PDTCD "
			+ "                   , CTGCD "
			+ "                   , PDTNM "
			+ "                   , COMNM "
			+ "                   , CREDCD "
			+ "                   , CONSCD1 "
			+ "                   , CONSCD2 "
			+ "                   , CONSCD3 "
			+ "                   , SAMACTCD "
			+ "                   , SAMBIZCD "
			+ "                   , USEYN "
			+ "                   , CREEMPNO "
			+ "                   , CREDT "
			+ "                   , UPDEMPNO "
			+ "                   , UPDDT "
			+ "                 ) "
			+ "          VALUES ( "
			+ "                     <#REFYEARLY#> "
			+ "                   , <#PDTCD#> "
			+ "                   , <#CTGCD#> "
			+ "                   , <#PDTNM#> "
			+ "                   , <#COMNM#> "
			+ "                   , <#CREDCD#> "
			+ "                   , <#CONSCD1#> "
			+ "                   , <#CONSCD2#> "
			+ "                   , <#CONSCD3#> "
			+ "                   , <#SAMACTCD#> "
			+ "                   , <#SAMBIZCD#> "
			+ "                   , <#USEYN#> "
			+ "                   , <#EMPNO#> "
			+ "                   , SYSDATE "
			+ "                   , <#EMPNO#> "
			+ "                   , SYSDATE "
			+ "                 ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 카테고리 테이블 복사하기
	 * 
	 * <#REFYEARLY#> 참조년도
	 * <#NREFYEARLY#>
	 * <#CREEMPNO#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String COPY_CATEGORY_LIST = 
			  "    INSERT INTO WEB_DCPDTCTG ( "
			+ "           REFYEARLY "
			+ "         , CTGCD "
			+ "         , CTGNM "
			+ "         , USEYN "
			+ "         , CREEMPNO "
			+ "         , CREDT "
			+ "         , UPDEMPNO "
			+ "         , UPDDT "
			+ "         ) "
			+ "    SELECT <#NREFYEARLY#> "
			+ "         , CT.CTGCD "
			+ "         , CT.CTGNM "
			+ "         , CT.USEYN "
			+ "         , <#CREEMPNO#> "
			+ "         , SYSDATE "
			+ "         , <#CREEMPNO#> "
			+ "         , SYSDATE "
			+ "      FROM WEB_DCPDTCTG CT "
			+ "     WHERE CT.REFYEARLY = <#REFYEARLY#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 테이블 복사하기
	 * 
	 * <#REFYEARLY#> 참조년도
	 * <#NREFYEARLY#>
	 * <#CREEMPNO#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String COPY_PDT_LIST = 
			  "    INSERT INTO WEB_DCPDT ( "
			+ "           REFYEARLY "
			+ "         , PDTCD "
			+ "         , PDTNM "
			+ "         , CTGCD "
			+ "         , COMNM "
			+ "         , CREDCD "
			+ "         , CONSCD1 "
			+ "         , CONSCD2 "
			+ "         , CONSCD3 "
			+ "         , SAMACTCD "
			+ "         , SAMBIZCD "
			+ "         , USEYN "
			+ "         , CREEMPNO "
			+ "         , CREDT "
			+ "         , UPDEMPNO "
			+ "         , UPDDT "
			+ "         ) "
			+ "    SELECT <#NREFYEARLY#> "
			+ "         , PT.PDTCD "
			+ "         , REPLACE(PT.PDTNM, chr(38)||'amp;', '&') PDTNM "
			+ "         , PT.CTGCD "
			+ "         , PT.COMNM "
			+ "         , PT.CREDCD "
			+ "         , PT.CONSCD1 "
			+ "         , PT.CONSCD2 "
			+ "         , PT.CONSCD3 "
			+ "         , PT.SAMACTCD "
			+ "         , PT.SAMBIZCD "
			+ "         , PT.USEYN "
			+ "         , <#CREEMPNO#> "
			+ "         , SYSDATE "
			+ "         , <#CREEMPNO#> "
			+ "         , SYSDATE "
			+ "      FROM WEB_DCPDT PT "
			+ "     WHERE PT.REFYEARLY = <#REFYEARLY#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 테이블 복사하기
	 * 
	 * <#YEAR#> 참조년도
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_PDT = 
			  "    DELETE FROM WEB_DCPDT "
			+ "     WHERE REFYEARLY = <#YEAR#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * PDT 테이블 복사하기
	 * 
	 * <#YEAR#> 참조년도
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_PDTCTG = 
			  "    DELETE FROM WEB_DCPDTCTG "
			+ "     WHERE REFYEARLY = <#YEAR#> ";
	
}
