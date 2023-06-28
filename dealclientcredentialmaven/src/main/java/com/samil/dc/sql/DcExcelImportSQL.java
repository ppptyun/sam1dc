package com.samil.dc.sql;

public final class DcExcelImportSQL {
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 프로젝트 정보 조회
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_PROJECT_INFO = 
			  "    SELECT A.PRJTCD "
			+ "         , A.PRJTCD1 "
			+ "         , A.PRJTCD2 "
			+ "         , A.PRJTCD3 "
			+ "         , A.PRJTNM "
			+ "         , A.YEARLY "
			+ "         , A.REFYEARLY "
			+ "         , ( SELECT PM.TYP12 FROM PMPRDUHD PM WHERE PM.YEARLY = A.REFYEARLY AND PM.PRDTCD = A.PDTCD AND PM.STATUS = '1' ) AS CTGCD "
			+ "         , A.PDTCD "
			+ "         , A.HQCD "
			+ "         , A.PTREMPNO "
			+ "         , A.MGREMPNO "
			+ "         , A.PAYCD "
			+ "         , DECODE( A.TERMIYN, 'Y', '종료', '진행' ) AS PROCESSNM "
			+ "         , TO_CHAR(A.CONTDT, 'YYYYMMDD') AS CONTDT "
			+ "         , TO_CHAR(A.TERMIDT, 'YYYYMMDD') AS TERMIDT "
			+ "         , DECODE( A.CHAMTW, NULL, 0, A.CHAMTW ) AS CHAMTW "
			+ "         , A.CLIENTCOMPCD AS COMPCD "
			+ "         , A.CLIENTINDUCD AS INDUCD "
			+ "         , A.CISNO1||'-'||A.CISNO2||'-'||A.CISNO3 AS CISNO "
			+ "         , A.CISNO1 "
			+ "         , A.CISNO2 "
			+ "         , A.CISNO3 "
			+ "      FROM ( "
			+ "            SELECT A.PRJTCD1||'-'||A.PRJTCD2||'-'||A.PRJTCD3 AS PRJTCD "
			+ "                 , A.PRJTCD1 AS PRJTCD1 "
			+ "                 , A.PRJTCD2 AS PRJTCD2 "
			+ "                 , A.PRJTCD3 AS PRJTCD3 "
			+ "                 , A.PRJTNM AS PRJTNM "
			+ "                 , A.YEARLY AS YEARLY "
			+ "                 , CASE TO_CHAR(DECODE( SIGN(TO_CHAR(SYSDATE, 'YYYY')||'07' - TO_CHAR(SYSDATE, 'YYYYMM')), 1, ADD_MONTHS(SYSDATE, -12), SYSDATE ), 'YYYY') "
			+ "                       WHEN '2016' THEN '2016' "
			+ "                       ELSE TO_CHAR(DECODE( SIGN('07' - SUBSTR(A.CALCCLOSDT, 5, 2)), 1, SUBSTR(A.CALCCLOSDT, 1, 4) - '1', SUBSTR(A.CALCCLOSDT, 1, 4))) "
			+ "                   END REFYEARLY "
			+ "                 , A.PRDTCD AS PDTCD "
			+ "                 , A.CHARGBON AS HQCD "
			+ "                 , A.CHARGPTR AS PTREMPNO "
			+ "                 , A.CHARGMGR AS MGREMPNO "
			+ "                 , A.CALCSUCCDV AS PAYCD "
			+ "                 , A.CALCCLOSDV AS TERMIYN "
			+ "                 , TO_DATE(A.CONTDT, 'YYYYMMDD') AS CONTDT "
			+ "                 , TO_DATE(A.CALCCLOSDT, 'YYYYMMDD') AS TERMIDT "
			+ "                 , A.CHAMTW AS CHAMTW "
			+ "                 , A.PRJTCD1 AS CLIENTCOMPCD "
			+ "                 , A.INDUCD AS CLIENTINDUCD "
			+ "                 , A.COMPCD AS CISNO1 "
			+ "                 , A.YYMM AS CISNO2 "
			+ "                 , A.ATTSEQ AS CISNO3 "
			+ "              FROM ( "
			+ "                      SELECT DISTINCT A.PRDTCD "
			+ "                           , A.PRJTCD1 "
			+ "                           , A.PRJTCD2 "
			+ "                           , A.PRJTCD3 "
			+ "                           , A.PRJTNM "
			+ "                           , A.YEARLY "
			+ "                           , A.CLOSDT1 "
			+ "                           , A.CHARGBON "
			+ "                           , A.CHARGPTR "
			+ "                           , A.CHARGMGR "
			+ "                           , A.INDUCD "
			+ "                           , D.COMPCD "
			+ "                           , D.YYMM "
			+ "                           , D.ATTSEQ "
			+ "                           , D.CHAMTW "
			+ "                           , D.CONTDT "
			+ "                           , D.STATRSDT "
			+ "                           , DECODE( C.SUCCDV, 0, '100201', '100202' ) AS CALCSUCCDV "
			+ "                           , DECODE( C.SUCCDV, 0, DECODE(A.CLOSDV1, 'Y', 'Y', 'N'), DECODE(D.STATUS, '1', 'N', '9', 'Y', '')) AS CALCCLOSDV "
			+ "                           , DECODE( C.SUCCDV, 0, DECODE(A.CLOSDV1, 'Y', A.CLOSDT1, ''), DECODE(D.STATUS, '1', '', '9', D.STATRSDT, '')) AS CALCCLOSDT "
			+ "                        FROM (SELECT   p.PRDTCD "
			+ "                                     , p.PRJTCD1 "
			+ "                                     , p.PRJTCD2 "
			+ "                                     , p.PRJTCD3 "
			+ "                                     , p.PRJTNM "
			+ "                                     , p.YEARLY "
			+ "                                     , p.CLOSDT1 "
			+ "                                     , p.CLOSDV1 "
			+ "                                     , p.CHARGBON "
			+ "                                     , p.CHARGPTR "
			+ "                                     , p.CHARGMGR "
			+ "                                     , p.INDUCD "
			+ "                                     , p.INADJUST "
			+ "                                     , p.STARTDT "
			+ "                                     , p.PRJTCD1_B "
			+ "                                     , p.PRJTCD2_B "
			+ "                                     , p.PRJTCD3_B "
			+ "                               FROM nmsp.PMPRJTBS p "
			+ "                              WHERE EXISTS "
			+ "                                     (SELECT 1 "
			+ "                                       FROM NMSP.PMPRJTH ph "
			+ "                                      WHERE (ph.CHARGBON LIKE 'F%' OR ph.CHARGBON IN (SELECT HQCD FROM WEB_DCHQ WHERE HQCD NOT LIKE 'F%' AND HQCD<>'999')) "
			+ "                                      AND ph.PRJTCD1 = p.PRJTCD1 "
			+ "                                      AND ph.PRJTCD2 = p.PRJTCD2 "
			+ "                                      AND ph.PRJTCD3 = p.PRJTCD3 "
			+ "                                     ) "
			+ "                             ) A "
			+ "                           , NMSP.PMPRJTDT B "
			+ "                           , ( "
			+ "                               SELECT B.COMPCD "
			+ "                                    , B.YYMM "
			+ "                                    , B.ATTSEQ "
			+ "                                    , SUM(DECODE( A.SUCCDV, 'N', 0, 1 )) AS SUCCDV "
			+ "                                 FROM NMSP.PMPRJTBS A "
			+ "                                    , NMSP.PMPRJTDT B "
			+ "                               WHERE B.PRJTCD1 = A.PRJTCD1 "
			+ "                                 AND B.PRJTCD2 = A.PRJTCD2 "
			+ "                                 AND B.PRJTCD3 = A.PRJTCD3 "
			+ "                               GROUP BY B.COMPCD "
			+ "                                   , B.YYMM "
			+ "                                   , B.ATTSEQ "
			+ "                            ) C "
			+ "                          , NMSP.PMCONTHD D "
			+ "                      WHERE A.PRJTCD1 = <#PRJTCD1#> "
			+ "                        AND A.PRJTCD2 = <#PRJTCD2#> "
			+ "                        AND A.PRJTCD3 = <#PRJTCD3#> "
			//+ "                        AND A.STARTDT >= '20110701' "
			//+ "                        AND ( A.INADJUST = 'P' OR A.INADJUST = 'N' ) "
			+ "                        AND B.PRJTCD1 = A.PRJTCD1 "
			+ "                        AND B.PRJTCD2 = A.PRJTCD2 "
			+ "                        AND B.PRJTCD3 = A.PRJTCD3 "
			+ "                        AND B.CREATEDV = 1 "
			+ "                        AND C.COMPCD = B.COMPCD "
			+ "                        AND C.YYMM = B.YYMM "
			+ "                        AND C.ATTSEQ = B.ATTSEQ "
			+ "                        AND D.COMPCD = C.COMPCD "
			+ "                        AND D.YYMM = C.YYMM "
			+ "                        AND D.ATTSEQ = C.ATTSEQ "
			+ "                   ) A "
			+ "             WHERE REGEXP_INSTR(DECODE( A.CONTDT, NULL, '0', A.CONTDT),'[^0-9]') = 0 "
			+ "               AND REGEXP_INSTR(DECODE( A.CALCCLOSDT, NULL, '0', A.CALCCLOSDT),'[^0-9]') = 0 "
			+ "           ) A ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 프로젝트 갯수 조회
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_CREDENTIAL_COUNT = 
			  "    SELECT COUNT( PRJTCD ) AS CNT "
			+ "      FROM WEB_DCCREDENTIAL "
			+ "     WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 프로젝트 갯수 조회
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_LEAGUE_COUNT = 
			  "    SELECT COUNT( PRJTCD ) AS CNT "
			+ "      FROM WEB_DCLTBASE "
			+ "     WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 등록
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_CREDENTIAL = 
			  "    INSERT INTO WEB_DCCREDENTIAL ( "
			+ "           PRJTCD "
			+ "         , PRJTCD1 "
			+ "         , PRJTCD2 "
			+ "         , PRJTCD3 "
			+ "         , CISNO "
			+ "         , CISNO1 "
			+ "         , CISNO2 "
			+ "         , CISNO3 "
			+ "         , PRJTDIVCD "
			+ "         , PRJTNM "
			+ "         , YEARLY "
			+ "         , REFYEARLY "
			+ "         , CTGCD "
			+ "         , PDTCD "
			+ "         , HQCD "
			+ "         , PTREMPNO "
			+ "         , MGREMPNO "
			+ "         , PAYCD "
			+ "         , CONTDT "
			+ "         , TERMIDT "
			+ "         , CHAMTW "
			+ "         , TRGTETC "
			+ "         , CREDTGTCD "
			+ "         , CREDT "
			+ "         , UPDDT "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#PRJTCD#> "
			+ "         , <#PRJTCD1#> "
			+ "         , <#PRJTCD2#> "
			+ "         , <#PRJTCD3#> "
			+ "         , <#CISNO#> "
			+ "         , <#CISNO1#> "
			+ "         , <#CISNO2#> "
			+ "         , <#CISNO3#> "
			+ "         , <#PRJTDIVCD#> "
			+ "         , <#PRJTNM#> "
			+ "         , <#YEARLY#> "
			+ "         , <#REFYEARLY#> "
			+ "         , ( SELECT PT.CTGCD FROM WEB_DCPDT PT WHERE PT.REFYEARLY = <#REFYEARLY#> AND PT.PDTCD = <#PDTCD#> ) "
			+ "         , <#PDTCD#> "
			+ "         , <#HQCD#> "
			+ "         , <#PTREMPNO#> "
			+ "         , <#MGREMPNO#> "
			+ "         , <#PAYCD#> "
			+ "         , TO_DATE(<#CONTDT#>, 'YYYYMMDD') "
			+ "         , TO_DATE(<#TERMIDT#>, 'YYYYMMDD') "
			+ "         , <#CHAMTW#> "
			+ "         , <#TRGTETC#> "
			+ "         , <#CREDTGTCD#> "
			+ "         , SYSDATE "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential BIZ 등록
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_CREDENTIAL_BIZ =
			  "    INSERT INTO WEB_DCCREDBIZ ( "
			+ "           PRJTCD "
			+ "         , BIZCD "
			+ "         , SEQ "
			+ "         , BIZDIVCD "
			+ "         , COMPCD "
			+ "         , COMPHANNM "
			+ "         , COMPENGNM "
			+ "         , INDUCD "
			+ "         , NATCD "
			+ "         , NATNM "
			+ "         , CREDT "
			+ "         , UPDDT "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#PRJTCD#> "
			+ "         , <#BIZCD#> "
			+ "         , <#SEQ#> "
			+ "         , <#BIZDIVCD#> "
			+ "         , <#COMPCD#> "
			+ "         , <#COMPHANNM#> "
			+ "         , <#COMPENGNM#> "
			+ "         , <#INDUCD#> "
			+ "         , <#NATCD#> "
			+ "         , <#NATNM#> "
			+ "         , SYSDATE "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Base 등록
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_LEAGUE_BASE =
			  "    INSERT INTO WEB_DCLTBASE ( "
			+ "           PRJTCD "
			+ "         , PRJTCD1 "
			+ "         , PRJTCD2 "
			+ "         , PRJTCD3 "
			+ "         , CISNO "
			+ "         , CISNO1 "
			+ "         , CISNO2 "
			+ "         , CISNO3 "
			+ "         , PRJTNM "
			+ "         , YEARLY "
			+ "         , REFYEARLY "
			+ "         , CTGCD "
			+ "         , PDTCD "
			+ "         , HQCD "
			+ "         , PTREMPNO "
			+ "         , MGREMPNO "
			+ "         , CONSCD "
			+ "         , STATUSCD "
			+ "         , COMNM "
			+ "			, CHRGCONFCD "
			+ "         , LTTGTCD "
			+ "         , CREDT "
			+ "         , UPDDT "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#PRJTCD#> "
			+ "         , <#PRJTCD1#> "
			+ "         , <#PRJTCD2#> "
			+ "         , <#PRJTCD3#> "
			+ "         , <#CISNO#> "
			+ "         , <#CISNO1#> "
			+ "         , <#CISNO2#> "
			+ "         , <#CISNO3#> "
			+ "         , <#PRJTNM#> "
			+ "         , <#YEARLY#> "
			+ "         , <#REFYEARLY#> "
			+ "         , ( SELECT PT.CTGCD FROM WEB_DCPDT PT WHERE PT.REFYEARLY = <#REFYEARLY#> AND PT.PDTCD = <#PDTCD#> ) "
			+ "         , <#PDTCD#> "
			+ "         , <#HQCD#> "
			+ "         , <#PTREMPNO#> "
			+ "         , <#MGREMPNO#> "
			+ "         , <#CONSCD#> "
			+ "         , <#STATUSCD#> "
			+ "         , <#COMNM#> "
			+ "         , '100802' "
			+ "         , '101003' "
			+ "         , SYSDATE "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Buy 등록
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_LEAGUE_BUY =
			  "    INSERT INTO WEB_DCLTBUY ( "
			+ "           PRJTCD "
			+ "         , TRGTETC "
			+ "         , BUYOUTCD "
			+ "         , CONSDT "
			+ "         , SPADT "
			+ "         , MOUDT "
			+ "         , CLOSDT "
			+ "         , CURRCD "
			+ "         , AMT "
			+ "         , RATE "
			+ "         , BORDDEALCD "
			+ "         , DEALCD2 "
			+ "         , DEALCD3 "
			+ "         , CREDT "
			+ "         , UPDDT "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#PRJTCD#> "
			+ "         , <#TRGTETC#> "
			+ "         , <#BUYOUTCD#> "
			+ "         , TO_DATE(<#CONSDT#>, 'YYYYMMDD') "
			+ "         , TO_DATE(<#SPADT#>, 'YYYYMMDD') "
			+ "         , TO_DATE(<#MOUDT#>, 'YYYYMMDD') "
			+ "         , TO_DATE(<#CLOSDT#>, 'YYYYMMDD') "
			+ "         , <#CURRCD#> "
			+ "         , <#AMT#> "
			+ "         , <#RATE#> "
			+ "         , <#BORDDEALCD#> "
			+ "         , <#DEALCD2#> "
			+ "         , <#DEALCD3#> "
			+ "         , SYSDATE "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table M&A 등록
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_LEAGUE_MNA =
			  "    INSERT INTO WEB_DCLTMNA ( "
			+ "           PRJTCD "
			+ "         , CONSDT "
			+ "         , DIRTDT "
			+ "         , STKHDT "
			+ "         , MNADT "
			+ "         , CURRCD "
			+ "         , AMT "
			+ "         , RATE "
			+ "         , BORDDEALCD "
			+ "         , DEALCD1 "
			+ "         , DEALCD2 "
			+ "         , DEALCD3 "
			+ "         , CREDT "
			+ "         , UPDDT "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#PRJTCD#> "
			+ "         , TO_DATE(<#CONSDT#>, 'YYYYMMDD') "
			+ "         , TO_DATE(<#DIRTDT#>, 'YYYYMMDD') "
			+ "         , TO_DATE(<#STKHDT#>, 'YYYYMMDD') "
			+ "         , TO_DATE(<#MNADT#>, 'YYYYMMDD') "
			+ "         , <#CURRCD#> "
			+ "         , <#AMT#> "
			+ "         , <#RATE#> "
			+ "         , <#BORDDEALCD#> "
			+ "         , <#DEALCD1#> "
			+ "         , <#DEALCD2#> "
			+ "         , <#DEALCD3#> "
			+ "         , SYSDATE "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Real 등록
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_LEAGUE_REAL =
			  "    INSERT INTO WEB_DCLTREAL ( "
			+ "           PRJTCD "
			+ "         , CONSDT "
			+ "         , SPADT "
			+ "         , MOUDT "
			+ "         , CLOSDT "
			+ "         , CURRCD "
			+ "         , AMT "
			+ "         , RATE "
			+ "         , BORDDEALCD "
			+ "         , CREDT "
			+ "         , UPDDT "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#PRJTCD#> "
			+ "         , TO_DATE(<#CONSDT#>, 'YYYYMMDD') "
			+ "         , TO_DATE(<#SPADT#>, 'YYYYMMDD') "
			+ "         , TO_DATE(<#MOUDT#>, 'YYYYMMDD') "
			+ "         , TO_DATE(<#CLOSDT#>, 'YYYYMMDD') "
			+ "         , <#CURRCD#> "
			+ "         , <#AMT#> "
			+ "         , <#RATE#> "
			+ "         , <#BORDDEALCD#> "
			+ "         , SYSDATE "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table BIZ 등록(비자문회사)
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_LEAGUE_BIZ_COMMON =
			  "    INSERT INTO WEB_DCLTBIZ ( "
			+ "           PRJTCD "
			+ "         , ACTCD "
			+ "         , BIZCD "
			+ "         , SEQ "
			+ "         , BIZDIVCD "
			+ "         , COMPCD "
			+ "         , COMPHANNM "
			+ "         , COMPENGNM "
			+ "         , INDUCD "
			+ "         , NATCD "
			+ "         , NATNM "
			+ "         , CHRGEMPNO1 "
			+ "         , CHRGEMPNM1 "
			+ "         , DEALNM "
			+ "         , CREDT "
			+ "         , UPDDT "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#PRJTCD#> "
			+ "         , <#ACTCD#> "
			+ "         , <#BIZCD#> "
			+ "         , <#SEQ#> "
			+ "         , <#BIZDIVCD#> "
			+ "         , <#COMPCD#> "
			+ "         , <#COMPHANNM#> "
			+ "         , <#COMPENGNM#> "
			+ "         , <#INDUCD#> "
			+ "         , <#NATCD#> "
			+ "         , <#NATNM#> "
			+ "         , <#CHRGEMPNO1#> "
			+ "         , <#CHRGEMPNM1#> "
			+ "         , <#DEALNM#> "
			+ "         , SYSDATE "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table BIZ 등록(자문회사)
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_LEAGUE_BIZ_CONSULT =
			  "    INSERT INTO WEB_DCLTBIZ ( "
			+ "           PRJTCD "
			+ "         , ACTCD "
			+ "         , BIZCD "
			+ "         , SEQ "
			+ "         , BIZDIVCD "
			+ "         , COMPCD "
			+ "         , COMPHANNM "
			+ "         , COMPENGNM "
			+ "         , INDUCD "
			+ "         , NATCD "
			+ "         , NATNM "
			+ "         , CHRGEMPNO1 "
			+ "         , CHRGEMPNM1 "
			+ "         , DEALNM "
			+ "         , CREDT "
			+ "         , UPDDT "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#PRJTCD#> "
			+ "         , <#ACTCD#> "
			+ "         , <#BIZCD#> "
			+ "         , <#SEQ#> "
			+ "         , <#BIZDIVCD#> "
			+ "         , <#COMPCD#> "
//			+ "         , <#COMPHANNM#> "
//			+ "         , <#COMPENGNM#> "
			+ "         , ( "
			+ "               SELECT CASE <#COMPCD#> "
			+ "                          WHEN NULL THEN '' "
			+ "                          WHEN '-' THEN <#COMPHANNM#> "
			+ "                          ELSE ( SELECT CO.COMPHANNM FROM WEB_DCCONSULT CO WHERE CO.COMPCD = <#COMPCD#> ) "
			+ "                      END AS COMPHANNM "
			+ "                 FROM DUAL "
			+ "           ) "
			+ "         , ( "
			+ "               SELECT CASE <#COMPCD#> "
			+ "                          WHEN NULL THEN '' "
			+ "                          WHEN '-' THEN <#COMPENGNM#> "
			+ "                          ELSE ( SELECT CO.COMPENGNM FROM WEB_DCCONSULT CO WHERE CO.COMPCD = <#COMPCD#> ) "
			+ "                      END AS COMPENGNM "
			+ "                 FROM DUAL "
			+ "           ) "
			
			+ "         , <#INDUCD#> "
			+ "         , <#NATCD#> "
			+ "         , <#NATNM#> "
			+ "         , <#CHRGEMPNO1#> "
			+ "         , <#CHRGEMPNM1#> "
			+ "         , <#DEALNM#> "
			+ "         , SYSDATE "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Buy 거래유형1 등록
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_LEAGUE_DEAL1 =
			  "    INSERT INTO WEB_DCLTDEAL ( "
			+ "           PRJTCD "
			+ "         , DEALITEMCD "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#PRJTCD#> "
			+ "         , <#DEALITEMCD#> "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Base 업데이트
	 * 
	 * <#STATUSCD#>
	 * <#CONFCD#>
	 * <#COMNM#>
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_LEAGUE_BASE = 
			  "    UPDATE WEB_DCLTBASE "
			+ "       SET PRJTNM = <#PRJTNM#> "
			+ "         , YEARLY = <#YEARLY#> "
			+ "         , REFYEARLY = <#REFYEARLY#> "
			+ "         , CTGCD = ( SELECT PT.CTGCD FROM WEB_DCPDT PT WHERE PT.REFYEARLY = <#REFYEARLY#> AND PT.PDTCD = <#PDTCD#> ) "
			+ "         , PDTCD = <#PDTCD#> "
			+ "         , HQCD = <#HQCD#> "
			+ "         , PTREMPNO = <#PTREMPNO#> "
			+ "         , MGREMPNO = <#MGREMPNO#> "
			+ "         , CONSCD = <#CONSCD#> "
			+ "         , STATUSCD = <#STATUSCD#> "
			+ "         , COMNM = <#COMNM#> "
			+ "         , CHRGCONFCD = '100802' "
			+ "         , CHRGCONFEMPNO = NULL "
			+ "         , CHRGCONFDT = NULL "
			+ "         , UPDDT = SYSDATE "
			+ "     WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Buy 프로젝트 삭제
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_LEAGUE_BUY = 
			  "    DELETE FROM WEB_DCLTBUY "
			+ "     WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table M&A 프로젝트 삭제
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_LEAGUE_MNA = 
			  "    DELETE FROM WEB_DCLTMNA "
			+ "     WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 부동산 프로젝트 삭제
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_LEAGUE_REAL = 
			  "    DELETE FROM WEB_DCLTREAL "
			+ "     WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기업정보 모두 삭제
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_LEAGUE_BIZ_ALL = 
			  "    DELETE FROM WEB_DCLTBIZ "
			+ "     WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Buy 거래형태1 목록 삭제
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_LEAGUE_BUY_DEAL1 = 
			  "    DELETE FROM WEB_DCLTDEAL "
			+ "     WHERE PRJTCD = <#PRJTCD#> ";
	
	public static final String SELECT_CREDENTIAL_CREDTGTCD = ""
			+ " SELECT A.PRJTCD, A.CREDTGTCD "
			+ "  FROM NMSP.WEB_DCCREDENTIAL A "
			+ "  WHERE A.PRJTCD=<#PRJTCD#>";
	
	public static final String UPDATE_CREDENTIAL_CREDTGTCD = ""
			+ " UPDATE NMSP.WEB_DCCREDENTIAL A "
			+ "        SET CREDTGTCD = <#CREDTGTCD#>, "
			+ "        UPDDT = SYSDATE"
			+ "  WHERE A.PRJTCD=<#PRJTCD#>";
}
