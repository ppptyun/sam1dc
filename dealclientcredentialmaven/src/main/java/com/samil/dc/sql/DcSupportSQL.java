package com.samil.dc.sql;

public final class DcSupportSQL {

	/**
	 * <pre>
	 * ====================================================================================
	 * 공통 코드 조회
	 * 
	 * <#PARCD#> 분류 코드
	 * ====================================================================================
	 */
	public static final String SELECT_COMMON_CODE_LIST = 
			  " SELECT PARCD AS CTGCD "
			+ "      , ITEMCD "
			+ "      , ITEMNM "
			+ "   FROM WEB_DCCOMCD "
			+ "  WHERE PARCD = <#PARCD#> "
			+ "    AND USEYN = 'Y' "
			+ "  ORDER BY SORT ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 롤 코드 조회
	 * ====================================================================================
	 */
	public static final String SELECT_ROLE_CODE_LIST = 
			  " SELECT ROLECD AS ITEMCD "
			+ "      , ROLENM AS ITEMNM "
			+ "   FROM WEB_DCROLE "
			+ "  ORDER BY SORT ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 임직원 검색
	 * 
	 * <#SEARCH_CONDITION#> 기타 검색 조건 SQL
	 * ====================================================================================
	 */
	public static final String SELECT_USER_SEARCH_LIST = " SELECT H.PRTDV AS PTRDV, "
			+ "        H.INTEID, "
			+ "        H.EMAIL, "
			+ "        H.EMPLNO, "
			+ "        H.KORNM, "
			+ "        H.ENGNM, "
			+ "        H.GRADCD, "
			+ "        H.LOSCD, "
			+ "        H.TEAMCD, "
			+ "        H.APPCD, "
			+ "        T.TEAMNM, "
			+ "        G.GRADNM, "
			+ "        W.APP AS APPNM, "
			+ "        C.ETCDNM AS LOSNM, "
			+ "        H.PHOEXT "
			+ "   FROM CMTEAMCD T, "
			+ "        HRGRAD G, "
			+ "        CMETCOCD C, "
			+ "        HRTRANWRK W, "
			+ "        HREMP H "
			+ "  WHERE H.OFFIST = '001' "
			+ "        <#SEARCH_CONDITION#> "
			+ "        AND H.GRADCD = G.GRADCD "
			+ "        AND H.TEAMCD = T.TEAMCD "
			+ "        AND H.LOSCD = C.ETCOCD "
			+ "        AND C.SYTMDV = 'CM' "
			+ "        AND H.OFFIST = '001' "
			+ "        AND C.CLASCD = '06' "
			+ "        AND H.EMPLNO = W.EMSABUCD";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 회사 검색
	 * 
	 * <#SEARCH_CONDITION#> 기타 검색 조건 SQL
	 * ====================================================================================
	 */
	public static final String SELECT_COMPANY_SEARCH_LIST = " SELECT AAA.SOURCE_TYPE, "
			+ "       AAA.CLIENT,  "
			+ "       AAA.UPCHECD,  "
			+ "       AAA.HANGNM,  "
			+ "       AAA.ENGLNM,  "
			+ "       AAA.BUBINNO,  "
			+ "       AAA.KORREPRNM,  "
			+ "       AAA.ADDR,  "
			+ "       BBB.INDUCD,  "
			+ "       BBB.INDUNM,  "
			+ "       BBB.GLINDUCD,  "
			+ "       BBB.GLINDUNMK  "
			+ "  FROM  "
			+ "       (  "
			+ "              (SELECT 'PMCLIENTBS' SOURCE_TYPE,  "
			+ "                     A.CLIENT,  "
			+ "                     B.UPCHECD,  "
			+ "                     A.HANGNM,  "
			+ "                     A.ENGLNM,  "
			+ "                     CASE  "
			+ "                         WHEN B.UPCHECD = '-'  "
			+ "                         THEN A.BUBINNO  "
			+ "                         ELSE C.CRPNO  "
			+ "                     END BUBINNO,  "
			+ "                     CASE  "
			+ "                         WHEN B.UPCHECD = '-'  "
			+ "                         THEN ''  "
			+ "                         ELSE C.KORREPRNM  "
			+ "                     END KORREPRNM,  "
			+ "                     CASE  "
			+ "                         WHEN B.UPCHECD = '-'  "
			+ "                         THEN ''  "
			+ "                         ELSE E.KORADDR  "
			+ "                     END ADDR,  "
			+ "                     A.INDUCD  "
			+ "                FROM NMSP.PMCLNTBS A,  "
			+ "                     NMSP.INFO_MAPPING_TBL B,  "
			+ "                     NMSP.INFO_EM001 C,  "
			+ "                     (SELECT UPCHECD,  "
			+ "                            MAX(BIZLO_SEQ) BIZLO_SEQ  "
			+ "                       FROM INFO_EM002  "
			+ "                      WHERE BIZLODIVCD='01'  "
			+ "                      GROUP BY UPCHECD  "
			+ "                     ) D,  "
			+ "                     NMSP.INFO_EM002 E  "
			+ "               WHERE A.CLIENT = B.CLIENT  "
			+ "                     AND B.USEYN = '1'  "
			+ "                     AND C.UPCHECD(+) = B.UPCHECD  "
			+ "                     AND D.UPCHECD(+) = C.UPCHECD  "
			+ "                     AND E.UPCHECD(+) = D.UPCHECD  "
			+ "                     AND E.BIZLO_SEQ(+) = D.BIZLO_SEQ  "
			+ "                     <#SEARCH_CONDITION#> " //AND HANGNM LIKE '%<#SEARCH_CONDITION#>%'  "
			+ "              )  "
			+ "           UNION ALL  "
			+ "              (SELECT 'INFO_EM001' SOURCE_TYPE,  "
			+ "                     '' CLIENT,  "
			+ "                     A.UPCHECD,  "
			+ "                     A.KORENTRNM HANGNM,  "
			+ "                     A.ENGENTRNM ENGLNM,  "
			+ "                     A.CRPNO,  "
			+ "                     A.KORREPRNM,  "
			+ "                     C.KORADDR,  "
			+ "                     '' AS INDUCD  "
			+ "                FROM  "
			+ "                     (SELECT A.UPCHECD,  "
			+ "                            A.KORENTRNM,  "
			+ "                            A.CRPNO,  "
			+ "                            A.KORREPRNM,  "
			+ "                            A.ENGENTRNM  "
			+ "                       FROM NMSP.INFO_EM001 A  "
			+ "                      WHERE NOT EXISTS  "
			+ "                            (SELECT *  "
			+ "                              FROM NMSP.INFO_MAPPING_TBL B  "
			+ "                             WHERE B.UPCHECD = A.UPCHECD  "
			+ "                            )  "
			+ "                            AND A.EPR_CNU_YN='Y' "
			+ "                     ) A,  "
			+ "                     (SELECT UPCHECD,  "
			+ "                            MAX(BIZLO_SEQ) BIZLO_SEQ  "
			+ "                       FROM INFO_EM002  "
			+ "                      WHERE BIZLODIVCD='01'  "
			+ "                      GROUP BY UPCHECD  "
			+ "                     ) B,  "
			+ "                     NMSP.INFO_EM002 C  "
			+ "               WHERE B.UPCHECD(+) = A.UPCHECD  "
			+ "                     AND C.UPCHECD = B.UPCHECD  "
			+ "                     AND C.BIZLO_SEQ = B.BIZLO_SEQ  "
			+ "                     <#SEARCH_CONDITION1#> " //AND A.KORENTRNM LIKE '%<#SEARCH_CONDITION#>%'  "
			+ "              )  "
			+ "       ) AAA, "
			+ "       ("
			+ "				SELECT B.GLINDUCD "
			+ "     	          , B.GLINDUNMK  "
			+ "     	          , A.INDUCD  "
			+ "     	          , A.INDUNM  "
			+ "     	    FROM PMINDUCD A  "
			+ "    	         	 , PMINDGLCD B  "
			+ "      	    WHERE B.GLINDUCD = A.GLINDUCD  "
			+ "      	          AND A.STATUS = '1'  "
			+ "       		      AND B.STATUS = '1'"
			+ "		   ) BBB  "
			+ "        WHERE AAA.INDUCD = BBB.INDUCD(+)  "
			+ "     ORDER BY SOURCE_TYPE DESC, HANGNM ASC  ";
			//+ "     WHERE HANGNM LIKE '%결과내검색추가%' ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문사 검색
	 * 
	 * <#SEARCH_CONDITION#> 기타 검색 조건 SQL
	 * ====================================================================================
	 */
	public static final String SELECT_CONSCOMPANY_SEARCH_LIST = " SELECT COMPCD, "
			+ "       COMPHANNM,  "
			+ "       COMPENGNM  "
			+ "  FROM WEB_DCCONSULT "
			+ "  WHERE  USEYN='Y' "
			+ "			<#SEARCH_CONDITION#> "
			+ "			<#SEARCH_CONDITION2#> "
			+ "  ORDER BY SORT ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 산업 검색
	 * 
	 * <#SEARCH_CONDITION#> 기타 검색 조건 SQL
	 * ====================================================================================
	 */
	public static final String SELECT_INDUSTRY_SEARCH_LIST = ""
			+ " SELECT A.INDUCD, "
			+ "        A.INDUNM, "
			+ "        A.TAG3 INDUGCD, "
			+ "        T.ETCDNM INDUGNM "
			+ "   FROM PMINDUCD A, "
			+ "        CMETCOCD T "
			+ "  WHERE T.SYTMDV='PM' "
			+ "        AND T.CLASCD='67' "
			+ "        AND T.ETCOCD=A.TAG3 "
			+ " ORDER BY A.TAG3, "
			+ "        A.INDUCD";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 국가 검색
	 * 
	 * <#SEARCH_CONDITION#> 기타 검색 조건 SQL
	 * ====================================================================================
	 */
	public static final String SELECT_NATION_SEARCH_LIST = " SELECT ETCOCD, "
			+ "        ETCDNM "
			+ "   FROM CMETCOCD "
			+ "  WHERE SYTMDV='AC' "
			+ "        AND CLASCD='92' "
			+ "        AND MGATT4='Y' "
			+ "        <#SEARCH_CONDITION#> "
			+ "  ORDER BY ETCDNM ASC ";
	 
	/**
	 * <pre>
	 * ====================================================================================
	 * 통화 목록
	 * 
	 * ====================================================================================
	 */
	public static final String SELECT_CURRENCY_SEARCH_LIST = " SELECT ETCOCD, "
			+ "        ETCDNM "
			+ "   FROM ( "
			+ "   	SELECT ETCOCD "
			+ "   	, ETCDNM  "
			+ "   	, DECODE(ETCOCD, 'KRW', 1, 'USD', 2, 3) SORTORDER "
			+ "   	FROM CMETCOCD "
			+ "   	WHERE SYTMDV='AC' "
			+ "   	AND CLASCD='11' "
			+ "   	AND ETCDNM NOT LIKE '%사용%' "
			+ "   	) "
			+ "   ORDER BY SORTORDER ASC, ETCDNM ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 예외적인 코드 조회 [ INFO_CODES 테이블에서 조회 ]
	 * 
	 * <#EXCEPTIONAL_CODE#> 분류 코드 1
	 * <#DMNITEM_CODE#> 분류 코드 2
	 * ====================================================================================
	 */
	public static final String SELECT_EXCEPTIONAL_CODE_LIST = 
			  " SELECT <#EXCEPTIONAL_CODE#> AS CTGCD "
			+ "      , DMNCDVL AS ITEMCD "
			+ "      , DECODE(TRIM(DMN_CDVL_KOR_CFCNM), '대상아님', '비상장', 'K-OTC', '비상장', TRIM(DMN_CDVL_KOR_CFCNM)) AS ITEMNM "
			+ "   FROM INFO_CODES "
			+ "  WHERE DMNITEM = <#DMNITEM_CODE#> "
			+ " ORDER BY DMN_CDVL_SORTNO ASC";
	
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 기업정보Master - 상장시장구분 옵션값
	 * 프로젝트 상세 화면에서 선택값 가져오는 쿼리
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_LTMRKTDIV_INFO = ""
			+ " SELECT <#EXCEPTIONAL_CODE#>  AS CTGCD, "
			+ "	DMNCDVL AS ITEMCD ,"
			+ " DECODE(DMNCDVL, 9, '비상장', DMN_CDVL_KOR_CFCNM) AS ITEMNM "
			+ " FROM INFO_CODES "
			+ " WHERE DMNITEM='ltgmktdivcd' AND DMNCDVL IN (1,2,3,9)"
			+ " ORDER BY DMN_CDVL_SORTNO ASC";
}
