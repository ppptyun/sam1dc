package com.samil.dc.sql;

public final class DcLeagueTableSQL {

	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 형태 지정 목록 조회
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_SETUP_CONSULT_TYPE_LIST = 
			  "     SELECT LT.PRJTCD "
			+ "          , LT.YEARLY "
			+ "          , LT.CONSCD "
			+ "          , PT.CTGCD "
			+ "          , CT.CTGNM "
			+ "          , LT.PDTCD "
			+ "          , REPLACE(PT.PDTNM, chr(38)||'amp;', '&') PDTNM"
			+ "          , LT.PRJTNM "
			+ "          , LT.HQCD "
			+ "          , TM.TEAMNM AS HQNM "
			+ "          , LT.PTREMPNO "
			+ "          , SF_EMP_NAME(LT.PTREMPNO) AS PTREMPNM "
			+ "          , LT.MGREMPNO "
			+ "          , SF_EMP_NAME(LT.MGREMPNO) AS MGREMPNM "
			+ "          , HQCHR.CHREMPNM "
			+ "          , TO_CHAR(LT.UPDDT, 'YYYY-MM-DD') AS UPDDT "
			+ "       FROM WEB_DCLTBASE LT "
			+ "          , CMTEAMCD TM "
			+ "          , WEB_DCPDT PT "
			+ "          , WEB_DCPDTCTG CT "
			+ "          , ( "
			+ "              SELECT LTB.PRJTCD "
			+ "                   , LTB.PTRHQCD "
			+ "                   , SUBSTR(MAX(SYS_CONNECT_BY_PATH(LTB.CHREMPNM, ', ')), 3) AS CHREMPNM "
			+ "                FROM ( "
			+ "                        SELECT LTA.PRJTCD "
			+ "                             , LTA.PTRHQCD "
			+ "                             , SF_EMP_NAME(EP.EMPNO) AS CHREMPNM "
			+ "                             , RANK() OVER (PARTITION BY LTA.PRJTCD ORDER BY EP.EMPNO ASC) RANK "
			+ "                          FROM ( "
			// 쿼리 수정 Begin
			/*
			+ "                                  SELECT LT.PRJTCD "
			+ "                                       , DECODE( HQ.HQCD, NULL, '999', HQ.HQCD ) AS PTRHQCD "
			+ "                                    FROM WEB_DCLTBASE LT "
			+ "                                       , HREMP HR "
			+ "                                       , WEB_DCHQ HQ "
			+ "                                   WHERE HR.EMPLNO = LT.PTREMPNO "
			+ "                                     AND HQ.HQCD(+) = HR.TEAMCD "
			*/
			+ "                                  SELECT PRJTCD "
			+ "                                       , TMPHQCD "
			+ "                                       , DECODE( COUNT(EMPNO), 0, '999', TMPHQCD ) AS PTRHQCD "
			+ "                                    FROM ( "
			+ "                                            SELECT LT.PRJTCD "
			+ "                                                 , DECODE( HQ.HQCD, NULL, '999', HQ.HQCD ) AS TMPHQCD "
			+ "                                                 , HQEMP.EMPNO "
			+ "                                              FROM WEB_DCLTBASE LT "
			+ "                                                 , HREMP HR "
			+ "                                                 , WEB_DCHQ HQ "
			+ "                                                 , WEB_DCHQEMP HQEMP "
			+ "                                             WHERE HR.EMPLNO = LT.PTREMPNO "
			+ "                                               AND HQ.HQCD(+) = HR.TEAMCD "
			+ "                                               AND HQEMP.HQCD(+) = HQ.HQCD "
			+ "                                         ) "
			+ "                                   GROUP BY PRJTCD, TMPHQCD "
			// 쿼리 수정 End
			+ "                                  ) LTA "
			+ "                                , WEB_DCHQEMP EP "
			+ "                            WHERE EP.HQCD(+) = LTA.PTRHQCD "
			+ "                     ) LTB "
			+ "                 START WITH LTB.RANK = 1 "
			+ "               CONNECT BY PRIOR LTB.RANK = LTB.RANK - 1 "
			+ "                   AND PRIOR LTB.PRJTCD = LTB.PRJTCD "
			+ "                 GROUP BY LTB.PRJTCD, LTB.PTRHQCD "
			+ "            ) HQCHR "
			+ "      WHERE LT.LTTGTCD = '101001' "
			+ "        AND LT.CONSCD = '100301' "
			+ "        AND TM.TEAMCD(+) = LT.HQCD "
			+ "        AND PT.REFYEARLY = LT.REFYEARLY "
			+ "        AND PT.PDTCD = LT.PDTCD "
			+ "        AND CT.REFYEARLY = LT.REFYEARLY "
			+ "        AND CT.CTGCD = LT.CTGCD "
			+ "        AND HQCHR.PRJTCD = LT.PRJTCD "
			+ "        <#SQL_CONDITION#> "
			+ "      ORDER BY LT.UPDDT DESC, LT.PRJTCD ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 자문 목록 조회
	 * 
	 * <#SQL_CONDITION#> 검색 조건 쿼리
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_CONSULT_BUY_LIST = 
			  "    SELECT BU.PRJTCD "
			+ "         , LT.YEARLY "
			+ "         , LT.CHRGCONFCD "
			+ "         , TO_CHAR(LT.CHRGCONFDT, 'YYYY-MM-DD') AS CHRGCONFDT "
			+ "         , TO_CHAR(LT.ETCONFDT, 'YYYY-MM-DD') AS ETCONFDT "
			+ "         , SF_EMP_NAME(LT.ETCONFEMPNO) AS ETCONFEMPNM "
			+ "         , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.CONFCD ) AS CONFNM "
			+ "         , LT.PRJTNM "
			+ "         , LT.HQCD "
			+ "         , TM.TEAMNM AS HQNM "
			+ "         , LT.PTREMPNO "
			+ "         , SF_EMP_NAME(LT.PTREMPNO) AS PTREMPNM "
			+ "         , LT.MGREMPNO "
			+ "         , SF_EMP_NAME(LT.MGREMPNO) AS MGREMPNM "
			+ "         , HQCHR.CHREMPNM "
			+ "         , LT.STATUSCD "
			+ "         , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.STATUSCD ) AS STATUSNM "
			+ "         , TO_CHAR(GREATEST(LT.UPDDT, BU.UPDDT), 'YYYY-MM-DD') AS UPDDT "
			+ "         , SB.T_COMPHANNM AS TCOMPHANNM "
			+ "         , SB.T_INDUNM AS TINDUNM "
			+ "         , SB.T_DEALNM AS TDEALNM "
			+ "         , SB.S_COMPHANNM AS SCOMPHANNM "
			+ "         , SB.S_INDUNM AS SINDUNM "
			+ "         , SB.B_COMPHANNM AS BCOMPHANNM "
			+ "         , SB.B_INDUNM AS BINDUNM "
			+ "         , BU.AMT * DECODE( BU.RATE, NULL, 1, BU.RATE) AS AMT "
			+ "      FROM WEB_DCLTBASE LT "
			+ "         , WEB_DCLTBUY BU "
			+ "         , CMTEAMCD TM "
			+ "         , ( "
			+ "              SELECT PRJTCD "
			+ "                   , MAX(T_COMPHANNM) AS T_COMPHANNM "
			+ "                   , MAX(T_INDUNM) AS T_INDUNM "
			+ "                   , MAX(T_DEALNM) AS T_DEALNM "
			+ "                   , MAX(S_COMPHANNM) AS S_COMPHANNM "
			+ "                   , MAX(S_INDUNM) AS S_INDUNM "
			+ "                   , MAX(B_COMPHANNM) AS B_COMPHANNM "
			+ "                   , MAX(B_INDUNM) AS B_INDUNM "
			+ "                FROM ( "
			+ "                        SELECT PRJTCD "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(COMPHANNM, '; ')), 3) AS T_COMPHANNM "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(INDUNM, '; ')), 3) AS T_INDUNM "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(DEALNM, '; ')), 3) AS T_DEALNM "
			+ "                             , '' AS S_COMPHANNM "
			+ "                             , '' AS S_INDUNM "
			+ "                             , '' AS B_COMPHANNM "
			+ "                             , '' AS B_INDUNM "
			+ "                          FROM ( "
			+ "                                  SELECT A.PRJTCD "
			+ "                                       , A.COMPHANNM "
			+ "                                       , ( SELECT IND.INDUNM FROM PMINDUCD IND WHERE IND.INDUCD = A.INDUCD) AS INDUNM "
			+ "                                       , A.DEALNM "
			+ "                                       , RANK() OVER (PARTITION BY A.PRJTCD ORDER BY A.SEQ ASC) RANK "
			+ "                                    FROM WEB_DCLTBIZ A "
			+ "                                   WHERE A.ACTCD = '100401' "
			+ "                                     AND A.BIZCD = '100501' "
			+ "                               ) "
			+ "                         START WITH RANK = 1 "
			+ "                         CONNECT BY PRIOR RANK = RANK - 1 "
			+ "                         AND PRIOR PRJTCD = PRJTCD "
			+ "                         GROUP BY PRJTCD "
			+ "                        UNION ALL "
			+ "                        SELECT PRJTCD "
			+ "                             , '' AS T_COMPHANNM "
			+ "                             , '' AS T_INDUNM "
			+ "                             , '' AS T_DEALNM "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(COMPHANNM, '; ')), 3) AS S_COMPHANNM "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(INDUNM, '; ')), 3) AS S_INDUNM "
			+ "                             , '' AS B_COMPHANNM "
			+ "                             , '' AS B_INDUNM "
			+ "                          FROM ( "
			+ "                                  SELECT A.PRJTCD "
			+ "                                       , A.COMPHANNM "
			+ "                                       , ( SELECT IND.INDUNM FROM PMINDUCD IND WHERE IND.INDUCD = A.INDUCD) AS INDUNM "
			+ "                                       , RANK() OVER (PARTITION BY A.PRJTCD ORDER BY A.SEQ ASC) RANK "
			+ "                                    FROM WEB_DCLTBIZ A "
			+ "                                   WHERE A.ACTCD = '100402' "
			+ "                                     AND A.BIZCD = '100501' "
			+ "                               ) "
			+ "                         START WITH RANK = 1 "
			+ "                         CONNECT BY PRIOR RANK = RANK - 1 "
			+ "                         AND PRIOR PRJTCD = PRJTCD "
			+ "                         GROUP BY PRJTCD "
			+ "                        UNION ALL "
			+ "                        SELECT PRJTCD "
			+ "                             , '' AS T_COMPHANNM "
			+ "                             , '' AS T_INDUNM "
			+ "                             , '' AS T_DEALNM "
			+ "                             , '' AS S_COMPHANNM "
			+ "                             , '' AS S_INDUNM "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(COMPHANNM, '; ')), 3) AS B_COMPHANNM "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(INDUNM, '; ')), 3) AS B_INDUNM "
			+ "                          FROM ( "
			+ "                                  SELECT A.PRJTCD "
			+ "                                       , A.COMPHANNM "
			+ "                                       , ( SELECT IND.INDUNM FROM PMINDUCD IND WHERE IND.INDUCD = A.INDUCD) AS INDUNM "
			+ "                                       , RANK() OVER (PARTITION BY A.PRJTCD ORDER BY A.SEQ ASC) RANK "
			+ "                                    FROM WEB_DCLTBIZ A "
			+ "                                   WHERE A.ACTCD = '100403' "
			+ "                                     AND A.BIZCD = '100501' "
			+ "                               ) "
			+ "                         START WITH RANK = 1 "
			+ "                         CONNECT BY PRIOR RANK = RANK - 1 "
			+ "                         AND PRIOR PRJTCD = PRJTCD "
			+ "                         GROUP BY PRJTCD "
			+ "                     ) "
			+ "               GROUP BY PRJTCD "
			+ "           ) SB "
			+ "         , ( "
			+ "              SELECT LTB.PRJTCD "
			+ "                   , LTB.PTRHQCD "
			+ "                   , SUBSTR(MAX(SYS_CONNECT_BY_PATH(LTB.CHREMPNM, ', ')), 3) AS CHREMPNM "
			+ "                FROM ( "
			+ "                        SELECT LTA.PRJTCD "
			+ "                             , LTA.PTRHQCD "
			+ "                             , SF_EMP_NAME(EP.EMPNO) AS CHREMPNM "
			+ "                             , RANK() OVER (PARTITION BY LTA.PRJTCD ORDER BY EP.EMPNO ASC) RANK "
			+ "                          FROM ( "
			// 쿼리 수정 Begin
			/*
			+ "                                  SELECT LT.PRJTCD "
			+ "                                       , DECODE( HQ.HQCD, NULL, '999', HQ.HQCD ) AS PTRHQCD "
			+ "                                    FROM WEB_DCLTBASE LT "
			+ "                                       , HREMP HR "
			+ "                                       , WEB_DCHQ HQ "
			+ "                                   WHERE HR.EMPLNO = LT.PTREMPNO "
			+ "                                     AND HQ.HQCD(+) = HR.TEAMCD "
			*/
			+ "                                  SELECT PRJTCD "
			+ "                                       , TMPHQCD "
			+ "                                       , DECODE( COUNT(EMPNO), 0, '999', TMPHQCD ) AS PTRHQCD "
			+ "                                    FROM ( "
			+ "                                            SELECT LT.PRJTCD "
			+ "                                                 , DECODE( HQ.HQCD, NULL, '999', HQ.HQCD ) AS TMPHQCD "
			+ "                                                 , HQEMP.EMPNO "
			+ "                                              FROM WEB_DCLTBASE LT "
			+ "                                                 , HREMP HR "
			+ "                                                 , WEB_DCHQ HQ "
			+ "                                                 , WEB_DCHQEMP HQEMP "
			+ "                                             WHERE HR.EMPLNO = LT.PTREMPNO "
			+ "                                               AND HQ.HQCD(+) = HR.TEAMCD "
			+ "                                               AND HQEMP.HQCD(+) = HQ.HQCD "
			+ "                                         ) "
			+ "                                   GROUP BY PRJTCD, TMPHQCD "
			// 쿼리 수정 End
			+ "                                  ) LTA "
			+ "                                , WEB_DCHQEMP EP "
			+ "                            WHERE EP.HQCD(+) = LTA.PTRHQCD "
			+ "                     ) LTB "
			+ "                 START WITH LTB.RANK = 1 "
			+ "               CONNECT BY PRIOR LTB.RANK = LTB.RANK - 1 "
			+ "                   AND PRIOR LTB.PRJTCD = LTB.PRJTCD "
			+ "                 GROUP BY LTB.PRJTCD, LTB.PTRHQCD "
			+ "           ) HQCHR "
			+ "     WHERE LT.PRJTCD = BU.PRJTCD "
			+ "       AND LT.LTTGTCD = '101001' "
			+ "       AND HQCHR.PRJTCD = LT.PRJTCD "
			+ "       <#SQL_CONDITION#> "
			+ "       AND TM.TEAMCD(+) = LT.HQCD "
			+ "       AND SB.PRJTCD(+) = LT.PRJTCD "
			+ "     ORDER BY GREATEST(LT.UPDDT, BU.UPDDT) DESC, LT.YEARLY DESC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 자문 목록 조회
	 * 
	 * <#SQL_CONDITION#> 검색 조건 쿼리
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_CONSULT_MNA_LIST = 
			  "    SELECT MA.PRJTCD "
			+ "         , LT.YEARLY "
			+ "         , LT.CHRGCONFCD "
			+ "         , TO_CHAR(LT.CHRGCONFDT, 'YYYY-MM-DD') AS CHRGCONFDT "
			+ "         , TO_CHAR(LT.ETCONFDT, 'YYYY-MM-DD') AS ETCONFDT "
			+ "         , SF_EMP_NAME(LT.ETCONFEMPNO) AS ETCONFEMPNM "
			+ "         , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.CONFCD ) AS CONFNM "
			+ "         , LT.PRJTNM "
			+ "         , LT.HQCD "
			+ "         , TM.TEAMNM AS HQNM "
			+ "         , LT.PTREMPNO "
			+ "         , SF_EMP_NAME(LT.PTREMPNO) AS PTREMPNM "
			+ "         , LT.MGREMPNO "
			+ "         , SF_EMP_NAME(LT.MGREMPNO) AS MGREMPNM "
			+ "         , HQCHR.CHREMPNM "
			+ "         , LT.STATUSCD "
			+ "         , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.STATUSCD ) AS STATUSNM "
			+ "         , TO_CHAR(GREATEST(LT.UPDDT, MA.UPDDT), 'YYYY-MM-DD') AS UPDDT "
			+ "         , SB.S_COMPHANNM AS SCOMPHANNM "
			+ "         , SB.S_INDUNM AS SINDUNM "
			+ "         , SB.B_COMPHANNM AS BCOMPHANNM "
			+ "         , SB.B_INDUNM AS BINDUNM "
			+ "         , MA.AMT * DECODE( MA.RATE, NULL, 1, MA.RATE) AS AMT "
			+ "      FROM WEB_DCLTBASE LT "
			+ "         , WEB_DCLTMNA MA "
			+ "         , CMTEAMCD TM "
			+ "         , ( "
			+ "              SELECT PRJTCD "
			+ "                   , MAX(S_COMPHANNM) AS S_COMPHANNM "
			+ "                   , MAX(S_INDUNM) AS S_INDUNM "
			+ "                   , MAX(B_COMPHANNM) AS B_COMPHANNM "
			+ "                   , MAX(B_INDUNM) AS B_INDUNM "
			+ "                FROM ( "
			+ "                        SELECT PRJTCD "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(COMPHANNM, '; ')), 3) AS S_COMPHANNM "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(INDUNM, '; ')), 3) AS S_INDUNM "
			+ "                             , '' AS B_COMPHANNM "
			+ "                             , '' AS B_INDUNM "
			+ "                          FROM ( "
			+ "                                  SELECT A.PRJTCD "
			+ "                                       , A.COMPHANNM "
			+ "                                       , ( SELECT IND.INDUNM FROM PMINDUCD IND WHERE IND.INDUCD = A.INDUCD) AS INDUNM "
			+ "                                       , RANK() OVER (PARTITION BY A.PRJTCD ORDER BY A.SEQ ASC) RANK "
			+ "                                    FROM WEB_DCLTBIZ A "
			+ "                                   WHERE A.ACTCD = '100404' "
			+ "                                     AND A.BIZCD = '100501' "
			+ "                               ) "
			+ "                         START WITH RANK = 1 "
			+ "                         CONNECT BY PRIOR RANK = RANK - 1 "
			+ "                         AND PRIOR PRJTCD = PRJTCD "
			+ "                         GROUP BY PRJTCD "
			+ "                        UNION ALL "
			+ "                        SELECT PRJTCD "
			+ "                             , '' AS S_COMPHANNM "
			+ "                             , '' AS S_INDUNM "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(COMPHANNM, '; ')), 3) AS B_COMPHANNM "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(INDUNM, '; ')), 3) AS B_INDUNM "
			+ "                          FROM ( "
			+ "                                  SELECT A.PRJTCD "
			+ "                                       , A.COMPHANNM "
			+ "                                       , ( SELECT IND.INDUNM FROM PMINDUCD IND WHERE IND.INDUCD = A.INDUCD) AS INDUNM "
			+ "                                       , RANK() OVER (PARTITION BY A.PRJTCD ORDER BY A.SEQ ASC) RANK "
			+ "                                    FROM WEB_DCLTBIZ A "
			+ "                                   WHERE A.ACTCD = '100405' "
			+ "                                     AND A.BIZCD = '100501' "
			+ "                               ) "
			+ "                         START WITH RANK = 1 "
			+ "                         CONNECT BY PRIOR RANK = RANK - 1 "
			+ "                         AND PRIOR PRJTCD = PRJTCD "
			+ "                         GROUP BY PRJTCD "
			+ "                     ) "
			+ "               GROUP BY PRJTCD "
			+ "           ) SB "
			+ "         , ( "
			+ "              SELECT LTB.PRJTCD "
			+ "                   , LTB.PTRHQCD "
			+ "                   , SUBSTR(MAX(SYS_CONNECT_BY_PATH(LTB.CHREMPNM, ', ')), 3) AS CHREMPNM "
			+ "                FROM ( "
			+ "                        SELECT LTA.PRJTCD "
			+ "                             , LTA.PTRHQCD "
			+ "                             , SF_EMP_NAME(EP.EMPNO) AS CHREMPNM "
			+ "                             , RANK() OVER (PARTITION BY LTA.PRJTCD ORDER BY EP.EMPNO ASC) RANK "
			+ "                          FROM ( "
			// 쿼리 수정 Begin
			/*
			+ "                                  SELECT LT.PRJTCD "
			+ "                                       , DECODE( HQ.HQCD, NULL, '999', HQ.HQCD ) AS PTRHQCD "
			+ "                                    FROM WEB_DCLTBASE LT "
			+ "                                       , HREMP HR "
			+ "                                       , WEB_DCHQ HQ "
			+ "                                   WHERE HR.EMPLNO = LT.PTREMPNO "
			+ "                                     AND HQ.HQCD(+) = HR.TEAMCD "
			*/
			+ "                                  SELECT PRJTCD "
			+ "                                       , TMPHQCD "
			+ "                                       , DECODE( COUNT(EMPNO), 0, '999', TMPHQCD ) AS PTRHQCD "
			+ "                                    FROM ( "
			+ "                                            SELECT LT.PRJTCD "
			+ "                                                 , DECODE( HQ.HQCD, NULL, '999', HQ.HQCD ) AS TMPHQCD "
			+ "                                                 , HQEMP.EMPNO "
			+ "                                              FROM WEB_DCLTBASE LT "
			+ "                                                 , HREMP HR "
			+ "                                                 , WEB_DCHQ HQ "
			+ "                                                 , WEB_DCHQEMP HQEMP "
			+ "                                             WHERE HR.EMPLNO = LT.PTREMPNO "
			+ "                                               AND HQ.HQCD(+) = HR.TEAMCD "
			+ "                                               AND HQEMP.HQCD(+) = HQ.HQCD "
			+ "                                         ) "
			+ "                                   GROUP BY PRJTCD, TMPHQCD "
			// 쿼리 수정 End
			+ "                                  ) LTA "
			+ "                                , WEB_DCHQEMP EP "
			+ "                            WHERE EP.HQCD(+) = LTA.PTRHQCD "
			+ "                     ) LTB "
			+ "                 START WITH LTB.RANK = 1 "
			+ "               CONNECT BY PRIOR LTB.RANK = LTB.RANK - 1 "
			+ "                   AND PRIOR LTB.PRJTCD = LTB.PRJTCD "
			+ "                 GROUP BY LTB.PRJTCD, LTB.PTRHQCD "
			+ "           ) HQCHR "
			+ "     WHERE LT.PRJTCD = MA.PRJTCD "
			+ "       AND LT.LTTGTCD = '101001' "
			+ "       AND HQCHR.PRJTCD = LT.PRJTCD "
			+ "       <#SQL_CONDITION#> "
			+ "       AND TM.TEAMCD(+) = LT.HQCD "
			+ "       AND SB.PRJTCD(+) = LT.PRJTCD "
			+ "     ORDER BY GREATEST(LT.UPDDT, MA.UPDDT) DESC, LT.YEARLY DESC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 자문 목록 조회
	 * 
	 * <#SQL_CONDITION#> 검색 조건 쿼리
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_CONSULT_REAL_LIST = 
			  "    SELECT RL.PRJTCD "
			+ "         , LT.YEARLY "
			+ "         , LT.CHRGCONFCD "
			+ "         , TO_CHAR(LT.CHRGCONFDT, 'YYYY-MM-DD') AS CHRGCONFDT "
			+ "         , TO_CHAR(LT.ETCONFDT, 'YYYY-MM-DD') AS ETCONFDT "
			+ "         , SF_EMP_NAME(LT.ETCONFEMPNO) AS ETCONFEMPNM "
			+ "		    , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.CONFCD ) AS CONFNM "
			+ "         , LT.PRJTNM "
			+ "         , LT.HQCD "
			+ "         , TM.TEAMNM AS HQNM "
			+ "         , LT.PTREMPNO "
			+ "         , SF_EMP_NAME(LT.PTREMPNO) AS PTREMPNM "
			+ "         , LT.MGREMPNO "
			+ "         , SF_EMP_NAME(LT.MGREMPNO) AS MGREMPNM "
			+ "         , HQCHR.CHREMPNM "
			+ "         , LT.STATUSCD "
			+ "         , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.STATUSCD ) AS STATUSNM "
			+ "         , TO_CHAR(GREATEST(LT.UPDDT, RL.UPDDT), 'YYYY-MM-DD') AS UPDDT "
			+ "         , SB.T_DEALNM AS TDEALNM "
			+ "         , SB.S_COMPHANNM AS SCOMPHANNM "
			+ "         , SB.S_INDUNM AS SINDUNM "
			+ "         , SB.B_COMPHANNM AS BCOMPHANNM "
			+ "         , SB.B_INDUNM AS BINDUNM "
			+ "         , RL.AMT * DECODE( RL.RATE, NULL, 1, RL.RATE) AS AMT "
			+ "      FROM WEB_DCLTBASE LT "
			+ "         , WEB_DCLTREAL RL "
			+ "         , CMTEAMCD TM "
			+ "         , ( "
			+ "              SELECT PRJTCD "
			+ "                   , MAX(T_DEALNM) AS T_DEALNM "
			+ "                   , MAX(S_COMPHANNM) AS S_COMPHANNM "
			+ "                   , MAX(S_INDUNM) AS S_INDUNM "
			+ "                   , MAX(B_COMPHANNM) AS B_COMPHANNM "
			+ "                   , MAX(B_INDUNM) AS B_INDUNM "
			+ "                FROM ( "
			+ "                        SELECT PRJTCD "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(DEALNM, '; ')), 3) AS T_DEALNM "
			+ "                             , '' AS S_COMPHANNM "
			+ "                             , '' AS S_INDUNM "
			+ "                             , '' AS B_COMPHANNM "
			+ "                             , '' AS B_INDUNM "
			+ "                          FROM ( "
			+ "                                  SELECT A.PRJTCD "
			+ "                                       , A.DEALNM "
			+ "                                       , RANK() OVER (PARTITION BY A.PRJTCD ORDER BY A.SEQ ASC) RANK "
			+ "                                    FROM WEB_DCLTBIZ A "
			+ "                                   WHERE A.ACTCD = '100408' "
			+ "                                     AND A.BIZCD = '100501' "
			+ "                               ) "
			+ "                         START WITH RANK = 1 "
			+ "                         CONNECT BY PRIOR RANK = RANK - 1 "
			+ "                         AND PRIOR PRJTCD = PRJTCD "
			+ "                         GROUP BY PRJTCD "
			+ "                        UNION ALL "
			+ "                        SELECT PRJTCD "
			+ "                             , '' AS T_DEALNM "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(COMPHANNM, '; ')), 3) AS S_COMPHANNM "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(INDUNM, '; ')), 3) AS S_INDUNM "
			+ "                             , '' AS B_COMPHANNM "
			+ "                             , '' AS B_INDUNM "
			+ "                          FROM ( "
			+ "                                  SELECT A.PRJTCD "
			+ "                                       , A.COMPHANNM "
			+ "                                       , ( SELECT IND.INDUNM FROM PMINDUCD IND WHERE IND.INDUCD = A.INDUCD) AS INDUNM "
			+ "                                       , RANK() OVER (PARTITION BY A.PRJTCD ORDER BY A.SEQ ASC) RANK "
			+ "                                    FROM WEB_DCLTBIZ A "
			+ "                                   WHERE A.ACTCD = '100406' "
			+ "                                     AND A.BIZCD = '100501' "
			+ "                               ) "
			+ "                         START WITH RANK = 1 "
			+ "                         CONNECT BY PRIOR RANK = RANK - 1 "
			+ "                         AND PRIOR PRJTCD = PRJTCD "
			+ "                         GROUP BY PRJTCD "
			+ "                        UNION ALL "
			+ "                        SELECT PRJTCD "
			+ "                             , '' AS T_DEALNM "
			+ "                             , '' AS S_COMPHANNM "
			+ "                             , '' AS S_INDUNM "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(COMPHANNM, '; ')), 3) AS B_COMPHANNM "
			+ "                             , SUBSTR(MAX(SYS_CONNECT_BY_PATH(INDUNM, '; ')), 3) AS B_INDUNM "
			+ "                          FROM ( "
			+ "                                  SELECT A.PRJTCD "
			+ "                                       , A.COMPHANNM "
			+ "                                       , ( SELECT IND.INDUNM FROM PMINDUCD IND WHERE IND.INDUCD = A.INDUCD) AS INDUNM "
			+ "                                       , RANK() OVER (PARTITION BY A.PRJTCD ORDER BY A.SEQ ASC) RANK "
			+ "                                    FROM WEB_DCLTBIZ A "
			+ "                                   WHERE A.ACTCD = '100407' "
			+ "                                     AND A.BIZCD = '100501' "
			+ "                               ) "
			+ "                         START WITH RANK = 1 "
			+ "                         CONNECT BY PRIOR RANK = RANK - 1 "
			+ "                         AND PRIOR PRJTCD = PRJTCD "
			+ "                         GROUP BY PRJTCD "
			+ "                     ) "
			+ "               GROUP BY PRJTCD "
			+ "           ) SB "
			+ "         , ( "
			+ "              SELECT LTB.PRJTCD "
			+ "                   , LTB.PTRHQCD "
			+ "                   , SUBSTR(MAX(SYS_CONNECT_BY_PATH(LTB.CHREMPNM, ', ')), 3) AS CHREMPNM "
			+ "                FROM ( "
			+ "                        SELECT LTA.PRJTCD "
			+ "                             , LTA.PTRHQCD "
			+ "                             , SF_EMP_NAME(EP.EMPNO) AS CHREMPNM "
			+ "                             , RANK() OVER (PARTITION BY LTA.PRJTCD ORDER BY EP.EMPNO ASC) RANK "
			+ "                          FROM ( "
			// 쿼리 수정 Begin
			/*
			+ "                                  SELECT LT.PRJTCD "
			+ "                                       , DECODE( HQ.HQCD, NULL, '999', HQ.HQCD ) AS PTRHQCD "
			+ "                                    FROM WEB_DCLTBASE LT "
			+ "                                       , HREMP HR "
			+ "                                       , WEB_DCHQ HQ "
			+ "                                   WHERE HR.EMPLNO = LT.PTREMPNO "
			+ "                                     AND HQ.HQCD(+) = HR.TEAMCD "
			*/
			+ "                                  SELECT PRJTCD "
			+ "                                       , TMPHQCD "
			+ "                                       , DECODE( COUNT(EMPNO), 0, '999', TMPHQCD ) AS PTRHQCD "
			+ "                                    FROM ( "
			+ "                                            SELECT LT.PRJTCD "
			+ "                                                 , DECODE( HQ.HQCD, NULL, '999', HQ.HQCD ) AS TMPHQCD "
			+ "                                                 , HQEMP.EMPNO "
			+ "                                              FROM WEB_DCLTBASE LT "
			+ "                                                 , HREMP HR "
			+ "                                                 , WEB_DCHQ HQ "
			+ "                                                 , WEB_DCHQEMP HQEMP "
			+ "                                             WHERE HR.EMPLNO = LT.PTREMPNO "
			+ "                                               AND HQ.HQCD(+) = HR.TEAMCD "
			+ "                                               AND HQEMP.HQCD(+) = HQ.HQCD "
			+ "                                         ) "
			+ "                                   GROUP BY PRJTCD, TMPHQCD "
			// 쿼리 수정 End
			+ "                                  ) LTA "
			+ "                                , WEB_DCHQEMP EP "
			+ "                            WHERE EP.HQCD(+) = LTA.PTRHQCD "
			+ "                     ) LTB "
			+ "                 START WITH LTB.RANK = 1 "
			+ "               CONNECT BY PRIOR LTB.RANK = LTB.RANK - 1 "
			+ "                   AND PRIOR LTB.PRJTCD = LTB.PRJTCD "
			+ "                 GROUP BY LTB.PRJTCD, LTB.PTRHQCD "
			+ "           ) HQCHR "
			+ "     WHERE LT.PRJTCD = RL.PRJTCD "
			+ "       AND LT.LTTGTCD = '101001' "
			+ "       AND HQCHR.PRJTCD = LT.PRJTCD "
			+ "       <#SQL_CONDITION#> "
			+ "       AND TM.TEAMCD(+) = LT.HQCD "
			+ "       AND SB.PRJTCD(+) = LT.PRJTCD "
			+ "     ORDER BY GREATEST(LT.UPDDT, RL.UPDDT) DESC, LT.YEARLY DESC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 대상 프로젝트 관리 목록 조회
	 * 
	 * <#SQL_CONDITION#> 검색 조건 쿼리
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_LEAGUE_PROJECT_LIST =
			  " SELECT LT.PRJTCD ,"
			+ "        LT.YEARLY ,"
			+ "        LT.LTTGTCD ,"
			+ "        (SELECT CM.ITEMNM"
			+ "          FROM WEB_DCCOMCD CM"
			+ "         WHERE CM.ITEMCD = LT.LTTGTCD"
			+ "        ) AS LTTGTNM ,"
			+ "        LT.CONSCD ,"
			+ "        (SELECT CM.ITEMNM"
			+ "          FROM WEB_DCCOMCD CM"
			+ "         WHERE CM.ITEMCD = LT.CONSCD"
			+ "        ) AS CONSNM ,"
			+ "        PT.CTGCD ,"
			+ "        CT.CTGNM ,"
			+ "        LT.PDTCD ,"
			+ "        REPLACE(PT.PDTNM, chr(38)||'amp;', '&') PDTNM ,"
			+ "        LT.PRJTNM ,"
			+ "        LT.HQCD ,"
			+ "        TM.TEAMNM AS HQNM ,"
			+ "        LT.PTREMPNO ,"
			+ "        SF_EMP_NAME(LT.PTREMPNO) AS PTREMPNM ,"
			+ "        LT.MGREMPNO ,"
			+ "        SF_EMP_NAME(LT.MGREMPNO) AS MGREMPNM ,"
			+ "        HQCHR.CHREMPNM ,"
			+ "        TO_CHAR(LT.UPDDT, 'YYYY-MM-DD') AS UPDDT ,"
			+ "        SB.STATRSDT AS CISDT1 ,"
			+ "        SB.DEPOSITDT AS CISDT2 ,"
			+ "        SB.MIDDLEDT AS CISDT3 ,"
			+ "        SB.BALANCEDT AS CISDT4 ,"
			+ "        SB.CLOSDT1 AS CLOSDT1 ,"
			+ "        SB.CLOSDT AS CLOSDT2 ,"
			+ "        SB.TERMIDT AS CLOSDT3"
			+ "   FROM WEB_DCLTBASE LT ,"
			+ "        CMTEAMCD TM ,"
			+ "        WEB_DCPDT PT ,"
			+ "        WEB_DCPDTCTG CT ,"
			+ "        (SELECT DISTINCT A.PRJTCD1 ,"
			+ "               A.PRJTCD2 ,"
			+ "               A.PRJTCD3 ,"
			+ "               A.CLOSDT1 ,"
			+ "               A.CLOSDT ,"
			+ "               A.TERMIDT ,"
			+ "               D.STATRSDT ,"
			+ "               F.DEPOSITDT DEPOSITDT,"
			+ "               F.MIDDLEDT MIDDLEDT,"
			+ "               F.BALANCEDT BALANCEDT"
			+ "          FROM PMPRJTBS A ,"
			+ "               PMPRJTDT B ,"
			+ "               (SELECT B.COMPCD ,"
			+ "                      B.YYMM ,"
			+ "                      B.ATTSEQ ,"
			+ "                      SUM(DECODE( A.SUCCDV, 'N', 0, 1 )) AS SUCCDV"
			+ "                 FROM PMPRJTBS A ,"
			+ "                      PMPRJTDT B"
			+ "                WHERE A.STARTDT >= '20110701'"
			+ "                      AND"
			+ "                      ("
			+ "                          A.INADJUST = 'P'"
			+ "                          OR A.INADJUST = 'N'"
			+ "                      )"
			+ "                      AND B.PRJTCD1 = A.PRJTCD1"
			+ "                      AND B.PRJTCD2 = A.PRJTCD2"
			+ "                      AND B.PRJTCD3 = A.PRJTCD3"
			+ "                      AND B.CREATEDV = 1"
			+ "                GROUP BY B.COMPCD ,"
			+ "                      B.YYMM ,"
			+ "                      B.ATTSEQ"
			+ "               ) C ,"
			+ "               PMCONTHD D ,"
			+ "               WEB_DCLTBASE E,"
			+ "               (SELECT PRJTCD1,PRJTCD2,PRJTCD3,DEPOSITDT,MIDDLEDT,BALANCEDT"
			+ "                 FROM"
			+ "                      (SELECT PRJTCD1,"
			+ "                             PRJTCD2,"
			+ "                             PRJTCD3,"
			+ "                             DECODE(SETTDV, '1', 'DEPOSITDT', '2', 'MIDDLEDT', '3', 'BALANCEDT') DT,"
			+ "                             BILLDT"
			+ "                        FROM"
			+ "                             (SELECT BP.PRJTCD1,"
			+ "                                    BP.PRJTCD2,"
			+ "                                    BP.PRJTCD3,"
			+ "                                    SUBSTR(BP.SETTDV, 1, 1) SETTDV,"
			+ "                                    MIN(BL.BILLDT) BILLDT"
			+ "                               FROM PMBLPLAN BP,"
			+ "                                    PMBILLING BL"
			+ "                              WHERE BP.COMPCD=BL.COMPCD(+)"
			+ "                                    AND BP.YYMM=BL.CONTYM(+)"
			+ "                                    AND BP.ATTSEQ=BL.CONTSEQ(+)"
			+ "                                    AND BP.PRJTCD1=BL.PRJTCD1(+)"
			+ "                                    AND BP.PRJTCD2=BL.PRJTCD2(+)"
			+ "                                    AND BP.PRJTCD3=BL.PRJTCD3(+)"
			+ "                                    AND BP.CLIENT=BL.CLIENT(+)"
			+ "                                    AND BP.CLIENTFIRM=BL.CLIENTFIRM(+)"
			+ "                                    AND BP.PLANSEQ=BL.PLANSEQ(+)"
			+ "                                    AND BP.SETTDV BETWEEN '10' AND '39'"
			+ "                              GROUP BY BP.PRJTCD1,"
			+ "                                    BP.PRJTCD2,"
			+ "                                    BP.PRJTCD3,"
			+ "                                    SUBSTR(BP.SETTDV, 1, 1)"
			+ "                             )"
			+ "                      ) PIVOT (MIN(BILLDT) FOR DT IN ('DEPOSITDT' AS DEPOSITDT, 'MIDDLEDT' AS MIDDLEDT, 'BALANCEDT' AS BALANCEDT))"
			+ "               ORDER BY PRJTCD1, PRJTCD2, PRJTCD3) F"
			+ "         WHERE A.STARTDT >= '20110701'"
			+ "               AND"
			+ "               ("
			+ "                   A.INADJUST = 'P'"
			+ "                   OR A.INADJUST = 'N'"
			+ "               )"
			+ "               AND B.PRJTCD1 = A.PRJTCD1"
			+ "               AND B.PRJTCD2 = A.PRJTCD2"
			+ "               AND B.PRJTCD3 = A.PRJTCD3"
			+ "               AND B.CREATEDV = 1"
			+ "               AND C.COMPCD = B.COMPCD"
			+ "               AND C.YYMM = B.YYMM"
			+ "               AND C.ATTSEQ = B.ATTSEQ"
			+ "               AND D.COMPCD = C.COMPCD"
			+ "               AND D.YYMM = C.YYMM"
			+ "               AND D.ATTSEQ = C.ATTSEQ"
			+ "               AND A.PRJTCD1 = E.PRJTCD1"
			+ "               AND A.PRJTCD2 = E.PRJTCD2"
			+ "               AND A.PRJTCD3 = E.PRJTCD3"
			+ "               AND F.PRJTCD1(+) = A.PRJTCD1"
			+ "               AND F.PRJTCD2(+) = A.PRJTCD2"
			+ "               AND F.PRJTCD3(+) = A.PRJTCD3"
			+ "        ) SB ,"
			+ "        (SELECT LTB.PRJTCD ,"
			+ "               LTB.PTRHQCD ,"
			+ "               SUBSTR(MAX(SYS_CONNECT_BY_PATH(LTB.CHREMPNM, ', ')), 3) AS CHREMPNM"
			+ "          FROM"
			+ "               (SELECT LTA.PRJTCD ,"
			+ "                      LTA.PTRHQCD ,"
			+ "                      SF_EMP_NAME(EP.EMPNO) AS CHREMPNM ,"
			+ "                      RANK() OVER (PARTITION BY LTA.PRJTCD ORDER BY EP.EMPNO ASC) RANK"
			+ "                 FROM"
			+ "                      (SELECT PRJTCD ,"
			+ "                             TMPHQCD ,"
			+ "                             DECODE( COUNT(EMPNO), 0, '999', TMPHQCD ) AS PTRHQCD"
			+ "                        FROM"
			+ "                             (SELECT LT.PRJTCD ,"
			+ "                                    DECODE( HQ.HQCD, NULL, '999', HQ.HQCD ) AS TMPHQCD ,"
			+ "                                    HQEMP.EMPNO"
			+ "                               FROM WEB_DCLTBASE LT ,"
			+ "                                    HREMP HR ,"
			+ "                                    WEB_DCHQ HQ ,"
			+ "                                    WEB_DCHQEMP HQEMP"
			+ "                              WHERE HR.EMPLNO(+) = LT.PTREMPNO"
			+ "                                    AND HQ.HQCD(+) = HR.TEAMCD"
			+ "                                    AND HQEMP.HQCD(+) = HQ.HQCD"
			+ "                             )"
			+ "                       GROUP BY PRJTCD,"
			+ "                             TMPHQCD"
			+ "                      ) LTA ,"
			+ "                      WEB_DCHQEMP EP"
			+ "                WHERE EP.HQCD(+) = LTA.PTRHQCD"
			+ "               ) LTB START"
			+ "        WITH LTB.RANK = 1 CONNECT BY PRIOR LTB.RANK = LTB.RANK - 1"
			+ "               AND PRIOR LTB.PRJTCD = LTB.PRJTCD"
			+ "         GROUP BY LTB.PRJTCD,"
			+ "               LTB.PTRHQCD"
			+ "           )"
			+ "           HQCHR"
			+ "     WHERE TM.TEAMCD(+) = LT.HQCD"
			+ "           AND PT.REFYEARLY = LT.REFYEARLY"
			+ "           AND PT.PDTCD = LT.PDTCD"
			+ "           AND CT.REFYEARLY = LT.REFYEARLY"
			+ "           AND CT.CTGCD = LT.CTGCD"
			+ "           AND SB.PRJTCD1(+) = LT.PRJTCD1"
			+ "           AND SB.PRJTCD2(+) = LT.PRJTCD2"
			+ "           AND SB.PRJTCD3(+) = LT.PRJTCD3"
			+ "           AND HQCHR.PRJTCD = LT.PRJTCD"
			+ "          <#SQL_CONDITION#> "
			+ "  ORDER BY LT.PRJTCD ASC";

//			  "    SELECT LT.PRJTCD "
//			+ "         , LT.YEARLY "
//			+ "         , LT.LTTGTCD "
//			+ "         , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.LTTGTCD ) AS LTTGTNM "
//			+ "         , LT.CONSCD "
//			+ "         , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.CONSCD ) AS CONSNM "
//			+ "         , PT.CTGCD "
//			+ "         , CT.CTGNM "
//			+ "         , LT.PDTCD "
//			+ "         , PT.PDTNM "
//			+ "         , LT.PRJTNM "
//			+ "         , LT.HQCD "
//			+ "         , TM.TEAMNM AS HQNM "
//			+ "         , LT.PTREMPNO "
//			+ "         , SF_EMP_NAME(LT.PTREMPNO) AS PTREMPNM "
//			+ "         , LT.MGREMPNO "
//			+ "         , SF_EMP_NAME(LT.MGREMPNO) AS MGREMPNM "
//			+ "         , HQCHR.CHREMPNM "
//			+ "         , TO_CHAR(LT.UPDDT, 'YYYY-MM-DD') AS UPDDT "
//			+ "         , SB.STATRSDT AS CISDT1 "
//			+ "         , SB.DEPOSITDT AS CISDT2 "
//			+ "         , SB.MIDDLEDT AS CISDT3 "
//			+ "         , SB.BALANCEDT AS CISDT4 "
//			+ "         , SB.CLOSDT1 AS CLOSDT1 "
//			+ "         , SB.CLOSDT AS CLOSDT2 "
//			+ "         , SB.TERMIDT AS CLOSDT3 "
//			+ "      FROM WEB_DCLTBASE LT "
//			+ "         , CMTEAMCD TM "
//			+ "         , WEB_DCPDT PT "
//			+ "         , WEB_DCPDTCTG CT "
//			+ "         , ( "
//			+ "                SELECT DISTINCT A.PRJTCD1 "
//			+ "                     , A.PRJTCD2 "
//			+ "                     , A.PRJTCD3 "
//			+ "                     , A.CLOSDT1 "
//			+ "                     , A.CLOSDT "
//			+ "                     , A.TERMIDT "
//			+ "                     , D.STATRSDT "
//			+ "                     , (SELECT MIN(BP.FIXDT) FROM PMBLPLAN BP WHERE BP.COMPCD = D.COMPCD AND BP.YYMM = D.YYMM AND BP.ATTSEQ = D.ATTSEQ AND SUBSTR(BP.SETTDV, 1, 1) = '1' AND BP.CLAIMDV = 'Y') AS DEPOSITDT "
//			+ "                     , (SELECT MIN(BP.FIXDT) FROM PMBLPLAN BP WHERE BP.COMPCD = D.COMPCD AND BP.YYMM = D.YYMM AND BP.ATTSEQ = D.ATTSEQ AND SUBSTR(BP.SETTDV, 1, 1) = '2' AND BP.CLAIMDV = 'Y') AS MIDDLEDT "
//			+ "                     , (SELECT MIN(BP.FIXDT) FROM PMBLPLAN BP WHERE BP.COMPCD = D.COMPCD AND BP.YYMM = D.YYMM AND BP.ATTSEQ = D.ATTSEQ AND SUBSTR(BP.SETTDV, 1, 1) = '3' AND BP.CLAIMDV = 'Y') AS BALANCEDT "
//			+ "                  FROM PMPRJTBS A "
//			+ "                     , PMPRJTDT B "
//			+ "                     , (  "
//			+ "                           SELECT B.COMPCD "
//			+ "                                , B.YYMM "
//			+ "                                , B.ATTSEQ "
//			+ "                                , SUM(DECODE( A.SUCCDV, 'N', 0, 1 )) AS SUCCDV "
//			+ "                             FROM PMPRJTBS A "
//			+ "                                , PMPRJTDT B "
//			+ "                           WHERE A.STARTDT >= '20110701' "
//			+ "                             AND ( A.INADJUST = 'P' OR A.INADJUST = 'N' ) "
//			// + "                             AND A.CHARGBON LIKE 'F%' "
//			+ "                             AND B.PRJTCD1 = A.PRJTCD1 "
//			+ "                             AND B.PRJTCD2 = A.PRJTCD2 "
//			+ "                             AND B.PRJTCD3 = A.PRJTCD3 "
//			+ "                             AND B.CREATEDV = 1 "
//			+ "                           GROUP BY B.COMPCD "
//			+ "                               , B.YYMM "
//			+ "                               , B.ATTSEQ "
//			+ "                      ) C "
//			+ "                    , PMCONTHD D "
//			+ "                    , WEB_DCLTBASE E "
//			+ "                WHERE A.STARTDT >= '20110701' "
//			+ "                  AND ( A.INADJUST = 'P' OR A.INADJUST = 'N' ) "
//			// + "                  AND A.CHARGBON LIKE 'F%' "
//			+ "                  AND B.PRJTCD1 = A.PRJTCD1 "
//			+ "                  AND B.PRJTCD2 = A.PRJTCD2 "
//			+ "                  AND B.PRJTCD3 = A.PRJTCD3 "
//			+ "                  AND B.CREATEDV = 1 "
//			+ "                  AND C.COMPCD = B.COMPCD "
//			+ "                  AND C.YYMM = B.YYMM "
//			+ "                  AND C.ATTSEQ = B.ATTSEQ "
//			+ "                  AND D.COMPCD = C.COMPCD "
//			+ "                  AND D.YYMM = C.YYMM "
//			+ "                  AND D.ATTSEQ = C.ATTSEQ "
//			+ "                  AND A.PRJTCD1 = E.PRJTCD1 "
//			+ "                  AND A.PRJTCD2 = E.PRJTCD2 "
//			+ "                  AND A.PRJTCD3 = E.PRJTCD3 "
//			+ "           ) SB "
//			+ "         , ( "
//			+ "              SELECT LTB.PRJTCD "
//			+ "                   , LTB.PTRHQCD "
//			+ "                   , SUBSTR(MAX(SYS_CONNECT_BY_PATH(LTB.CHREMPNM, ', ')), 3) AS CHREMPNM "
//			+ "                FROM ( "
//			+ "                        SELECT LTA.PRJTCD "
//			+ "                             , LTA.PTRHQCD "
//			+ "                             , SF_EMP_NAME(EP.EMPNO) AS CHREMPNM "
//			+ "                             , RANK() OVER (PARTITION BY LTA.PRJTCD ORDER BY EP.EMPNO ASC) RANK "
//			+ "                          FROM ( "
//			// 쿼리 수정 Begin
//			/*
//			+ "                                  SELECT LT.PRJTCD "
//			+ "                                       , DECODE( HQ.HQCD, NULL, '999', HQ.HQCD ) AS PTRHQCD "
//			+ "                                    FROM WEB_DCLTBASE LT "
//			+ "                                       , HREMP HR "
//			+ "                                       , WEB_DCHQ HQ "
//			+ "                                   WHERE HR.EMPLNO = LT.PTREMPNO "
//			+ "                                     AND HQ.HQCD(+) = HR.TEAMCD "
//			*/
//			+ "                                  SELECT PRJTCD "
//			+ "                                       , TMPHQCD "
//			+ "                                       , DECODE( COUNT(EMPNO), 0, '999', TMPHQCD ) AS PTRHQCD "
//			+ "                                    FROM ( "
//			+ "                                            SELECT LT.PRJTCD "
//			+ "                                                 , DECODE( HQ.HQCD, NULL, '999', HQ.HQCD ) AS TMPHQCD "
//			+ "                                                 , HQEMP.EMPNO "
//			+ "                                              FROM WEB_DCLTBASE LT "
//			+ "                                                 , HREMP HR "
//			+ "                                                 , WEB_DCHQ HQ "
//			+ "                                                 , WEB_DCHQEMP HQEMP "
//			+ "                                             WHERE HR.EMPLNO = LT.PTREMPNO "
//			+ "                                               AND HQ.HQCD(+) = HR.TEAMCD "
//			+ "                                               AND HQEMP.HQCD(+) = HQ.HQCD "
//			+ "                                         ) "
//			+ "                                   GROUP BY PRJTCD, TMPHQCD "
//			// 쿼리 수정 End
//			+ "                                  ) LTA "
//			+ "                                , WEB_DCHQEMP EP "
//			+ "                            WHERE EP.HQCD(+) = LTA.PTRHQCD "
//			+ "                     ) LTB "
//			+ "                 START WITH LTB.RANK = 1 "
//			+ "               CONNECT BY PRIOR LTB.RANK = LTB.RANK - 1 "
//			+ "                   AND PRIOR LTB.PRJTCD = LTB.PRJTCD "
//			+ "                 GROUP BY LTB.PRJTCD, LTB.PTRHQCD "
//			+ "           ) HQCHR "
//			+ "     WHERE TM.TEAMCD(+) = LT.HQCD "
//			+ "       AND PT.REFYEARLY = LT.REFYEARLY "
//			+ "       AND PT.PDTCD = LT.PDTCD "
//			+ "       AND CT.REFYEARLY = LT.REFYEARLY "
//			+ "       AND CT.CTGCD = LT.CTGCD "
//			+ "       AND SB.PRJTCD1(+) = LT.PRJTCD1 "
//			+ "       AND SB.PRJTCD2(+) = LT.PRJTCD2 "
//			+ "       AND SB.PRJTCD3(+) = LT.PRJTCD3 "
//			+ "       AND HQCHR.PRJTCD = LT.PRJTCD "
//			+ "       <#SQL_CONDITION#> "
//			+ "     ORDER BY LT.PRJTCD ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 상세 정보 조회
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_LEAGUE_BUY = 
			  "     SELECT LT.PRJTCD "
			+ "          , LT.CONSCD "
			+ "          , (SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.CONSCD ) AS CONSNM "
			+ "          , PT.CTGCD "
			+ "          , CT.CTGNM "
			+ "          , LT.PDTCD "
			+ "          , REPLACE(PT.PDTNM, chr(38)||'amp;', '&') PDTNM "
			+ "          , LT.PRJTNM "
			+ "          , LT.HQCD "
			+ "          , TM.TEAMNM AS HQNM "
			+ "          , LT.PTREMPNO "
			+ "          , SF_EMP_NAME(LT.PTREMPNO) AS PTREMPNM "
			+ "          , ( SELECT HR.TEAMCD FROM HREMP HR WHERE HR.EMPLNO = LT.PTREMPNO ) AS PTRHQCD "
			+ "          , LT.MGREMPNO "
			+ "          , SF_EMP_NAME(LT.MGREMPNO) AS MGREMPNM "
			+ "          , LT.STATUSCD "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.STATUSCD ) AS STATUSNM "
			+ "          , LT.CONFCD "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.CONFCD ) AS CONFNM "
			+ "          , LT.CHRGCONFCD "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.CHRGCONFCD ) AS CHRGCONFNM "
			+ "          , LT.CHRGCONFEMPNO "
			+ "          , SF_EMP_NAME(LT.CHRGCONFEMPNO) AS CHRGCONFEMPNM "
			+ "          , TO_CHAR(LT.CHRGCONFDT, 'YYYY-MM-DD') AS CHRGCONFDT "
			+ "          , LT.COMNM "
			+ "          , BU.TRGTETC "
			+ "          , BU.BUYOUTCD "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = BU.BUYOUTCD ) AS BUYOUTNM "
			+ "          , TO_CHAR(BU.CONSDT, 'YYYY-MM-DD') AS CONSDT "
			+ "          , TO_CHAR(BU.SPADT, 'YYYY-MM-DD') AS SPADT "
			+ "          , TO_CHAR(BU.MOUDT, 'YYYY-MM-DD') AS MOUDT "
			+ "          , TO_CHAR(BU.CLOSDT, 'YYYY-MM-DD') AS CLOSDT "
			+ "          , BU.CURRCD "
			+ "          , ( SELECT TRIM(CD.ETCDNM) FROM CMETCOCD CD WHERE CD.SYTMDV = 'AC' AND CD.CLASCD = '11' AND CD.ETCOCD = BU.CURRCD AND CD.ETCDNM NOT LIKE '%사용%' ) AS CURRNM "
			+ "          , BU.RATE "
			+ "          , BU.AMT "
			+ "          , BU.AMT * DECODE( BU.RATE, NULL, 1, BU.RATE) AS RATEAMT "
			+ "          , BU.BORDDEALCD "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = BU.BORDDEALCD ) AS BORDDEALNM "
			+ "          , BU.DEALCD2 "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = BU.DEALCD2 ) AS DEALNM2 "
			+ "          , BU.DEALCD3 "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = BU.DEALCD3 ) AS DEALNM3 "
			+ "          , BU.DEALCD4 "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = BU.DEALCD4 ) AS DEALNM4 "
			+ "          , TO_CHAR(GREATEST(LT.UPDDT, BU.UPDDT), 'YYYY-MM-DD') AS UPDDT "
			+ "       FROM WEB_DCLTBASE LT "
			+ "          , WEB_DCLTBUY BU "
			+ "          , CMTEAMCD TM "
			+ "          , WEB_DCPDT PT "
			+ "          , WEB_DCPDTCTG CT "
			+ "      WHERE LT.PRJTCD = <#PRJTCD#> "
			+ "        AND BU.PRJTCD = LT.PRJTCD "
			+ "        AND TM.TEAMCD(+) = LT.HQCD "
			+ "        AND PT.REFYEARLY = LT.REFYEARLY "
			+ "        AND PT.PDTCD = LT.PDTCD "
			+ "        AND CT.REFYEARLY = LT.REFYEARLY "
			+ "        AND CT.CTGCD = LT.CTGCD ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 합병 상세 정보 조회
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_LEAGUE_MNA = 
			  "     SELECT LT.PRJTCD "
			+ "          , LT.CONSCD "
			+ "          , (SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.CONSCD ) AS CONSNM "
			+ "          , PT.CTGCD "
			+ "          , CT.CTGNM "
			+ "          , LT.PDTCD "
			+ "          , REPLACE(PT.PDTNM, chr(38)||'amp;', '&') PDTNM "
			+ "          , LT.PRJTNM "
			+ "          , LT.HQCD "
			+ "          , TM.TEAMNM AS HQNM "
			+ "          , LT.PTREMPNO "
			+ "          , SF_EMP_NAME(LT.PTREMPNO) AS PTREMPNM "
			+ "          , ( SELECT HR.TEAMCD FROM HREMP HR WHERE HR.EMPLNO = LT.PTREMPNO ) AS PTRHQCD "
			+ "          , LT.MGREMPNO "
			+ "          , SF_EMP_NAME(LT.MGREMPNO) AS MGREMPNM "
			+ "          , LT.STATUSCD "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.STATUSCD ) AS STATUSNM "
			+ "          , LT.CONFCD "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.CONFCD ) AS CONFNM "
			+ "          , LT.CHRGCONFCD "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.CHRGCONFCD ) AS CHRGCONFNM "
			+ "          , LT.CHRGCONFEMPNO "
			+ "          , SF_EMP_NAME(LT.CHRGCONFEMPNO) AS CHRGCONFEMPNM "
			+ "          , TO_CHAR(LT.CHRGCONFDT, 'YYYY-MM-DD') AS CHRGCONFDT "
			+ "          , LT.COMNM "
			+ "          , TO_CHAR(MA.CONSDT, 'YYYY-MM-DD') AS CONSDT "
			+ "          , TO_CHAR(MA.DIRTDT, 'YYYY-MM-DD') AS DIRTDT "
			+ "          , TO_CHAR(MA.STKHDT, 'YYYY-MM-DD') AS STKHDT "
			+ "          , TO_CHAR(MA.MNADT, 'YYYY-MM-DD') AS MNADT "
			+ "          , MA.CURRCD "
			+ "          , ( SELECT TRIM(CD.ETCDNM) FROM CMETCOCD CD WHERE CD.SYTMDV = 'AC' AND CD.CLASCD = '11' AND CD.ETCOCD = MA.CURRCD AND CD.ETCDNM NOT LIKE '%사용%' ) AS CURRNM "
			+ "          , MA.RATE "
			+ "          , MA.AMT "
			+ "          , MA.AMT * DECODE( MA.RATE, NULL, 1, MA.RATE) AS RATEAMT "
			+ "          , MA.BORDDEALCD "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = MA.BORDDEALCD ) AS BORDDEALNM "
			+ "          , MA.DEALCD1 "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = MA.DEALCD1 ) AS DEALNM1 "
			+ "          , MA.DEALCD2 "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = MA.DEALCD2 ) AS DEALNM2 "
			+ "          , MA.DEALCD3 "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = MA.DEALCD3 ) AS DEALNM3 "
			+ "          , TO_CHAR(GREATEST(LT.UPDDT, MA.UPDDT), 'YYYY-MM-DD') AS UPDDT "
			+ "       FROM WEB_DCLTBASE LT "
			+ "          , WEB_DCLTMNA MA "
			+ "          , CMTEAMCD TM "
			+ "          , WEB_DCPDT PT "
			+ "          , WEB_DCPDTCTG CT "
			+ "      WHERE LT.PRJTCD = <#PRJTCD#> "
			+ "        AND MA.PRJTCD = LT.PRJTCD "
			+ "        AND TM.TEAMCD(+) = LT.HQCD "
			+ "        AND PT.REFYEARLY = LT.REFYEARLY "
			+ "        AND PT.PDTCD = LT.PDTCD "
			+ "        AND CT.REFYEARLY = LT.REFYEARLY "
			+ "        AND CT.CTGCD = LT.CTGCD ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 부동산 상세 정보 조회
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_LEAGUE_REAL = 
			  "     SELECT LT.PRJTCD "
			+ "          , LT.CONSCD "
			+ "          , (SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.CONSCD ) AS CONSNM "
			+ "          , PT.CTGCD "
			+ "          , CT.CTGNM "
			+ "          , LT.PDTCD "
			+ "          , REPLACE(PT.PDTNM, chr(38)||'amp;', '&') PDTNM "
			+ "          , LT.PRJTNM "
			+ "          , LT.HQCD "
			+ "          , TM.TEAMNM AS HQNM "
			+ "          , LT.PTREMPNO "
			+ "          , SF_EMP_NAME(LT.PTREMPNO) AS PTREMPNM "
			+ "          , ( SELECT HR.TEAMCD FROM HREMP HR WHERE HR.EMPLNO = LT.PTREMPNO ) AS PTRHQCD "
			+ "          , LT.MGREMPNO "
			+ "          , SF_EMP_NAME(LT.MGREMPNO) AS MGREMPNM "
			+ "          , LT.STATUSCD "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.STATUSCD ) AS STATUSNM "
			+ "          , LT.CONFCD "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.CONFCD ) AS CONFNM "
			+ "          , LT.CHRGCONFCD "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = LT.CHRGCONFCD ) AS CHRGCONFNM "
			+ "          , LT.CHRGCONFEMPNO "
			+ "          , SF_EMP_NAME(LT.CHRGCONFEMPNO) AS CHRGCONFEMPNM "
			+ "          , TO_CHAR(LT.CHRGCONFDT, 'YYYY-MM-DD') AS CHRGCONFDT "
			+ "          , LT.COMNM "
			+ "          , TO_CHAR(RL.CONSDT, 'YYYY-MM-DD') AS CONSDT "
			+ "          , TO_CHAR(RL.SPADT, 'YYYY-MM-DD') AS SPADT "
			+ "          , TO_CHAR(RL.MOUDT, 'YYYY-MM-DD') AS MOUDT "
			+ "          , TO_CHAR(RL.CLOSDT, 'YYYY-MM-DD') AS CLOSDT "
			+ "          , RL.CURRCD "
			+ "          , ( SELECT TRIM(CD.ETCDNM) FROM CMETCOCD CD WHERE CD.SYTMDV = 'AC' AND CD.CLASCD = '11' AND CD.ETCOCD = RL.CURRCD AND CD.ETCDNM NOT LIKE '%사용%' ) AS CURRNM "
			+ "          , RL.RATE "
			+ "          , RL.AMT "
			+ "          , RL.AMT * DECODE( RL.RATE, NULL, 1, RL.RATE) AS RATEAMT "
			+ "          , RL.BORDDEALCD "
			+ "          , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = RL.BORDDEALCD ) AS BORDDEALNM "
			+ "          , TO_CHAR(GREATEST(LT.UPDDT, RL.UPDDT), 'YYYY-MM-DD') AS UPDDT "
			+ "       FROM WEB_DCLTBASE LT "
			+ "          , WEB_DCLTREAL RL "
			+ "          , CMTEAMCD TM "
			+ "          , WEB_DCPDT PT "
			+ "          , WEB_DCPDTCTG CT "
			+ "      WHERE LT.PRJTCD = <#PRJTCD#> "
			+ "        AND RL.PRJTCD = LT.PRJTCD "
			+ "        AND TM.TEAMCD(+) = LT.HQCD "
			+ "        AND PT.REFYEARLY = LT.REFYEARLY "
			+ "        AND PT.PDTCD = LT.PDTCD "
			+ "        AND CT.REFYEARLY = LT.REFYEARLY "
			+ "        AND CT.CTGCD = LT.CTGCD ";	
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기업정보 목록 조회
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_LEAGUE_BIZ_LIST = 
			  "    SELECT BZ.PRJTCD "
			+ "         , BZ.ACTCD "
			+ "         , BZ.BIZCD "
			+ "         , BZ.SEQ "
			+ "         , BZ.BIZDIVCD "
			+ "         , BZ.COMPCD "
			+ "         , BZ.COMPHANNM "
			+ "         , BZ.COMPENGNM "
			+ "         , BZ.INDUCD "
			+ "         , ( SELECT TRIM(IND.INDUNM) FROM PMINDUCD IND WHERE IND.INDUCD = BZ.INDUCD) AS INDUNM "
			+ "         , BZ.NATCD "
			+ "         , CASE BZ.NATCD "
			+ "               WHEN '-' THEN BZ.NATNM "
			+ "               ELSE ( SELECT CD.ETCDNM FROM CMETCOCD CD WHERE CD.SYTMDV='AC' AND CD.CLASCD='92' AND CD.MGATT4='Y' AND CD.ETCOCD = BZ.NATCD ) "
			+ "           END AS NATNM "
			+ "         , BZ.CITY "
			+ "         , BZ.ADDR "
			+ "         , BZ.CHRGEMPNO1 "
			+ "         , CASE BZ.CHRGEMPNO1 "
			+ "               WHEN NULL THEN BZ.CHRGEMPNM1 "
			+ "               WHEN '-' THEN BZ.CHRGEMPNM1 "
			+ "               ELSE SF_EMP_NAME(BZ.CHRGEMPNO1) "
			+ "           END AS CHRGEMPNM1 "
			+ "         , BZ.CHRGEMPNO2 "
			+ "         , CASE BZ.CHRGEMPNO2 "
			+ "               WHEN NULL THEN BZ.CHRGEMPNM2 "
			+ "               WHEN '-' THEN BZ.CHRGEMPNM2 "
			+ "               ELSE SF_EMP_NAME(BZ.CHRGEMPNO2) "
			+ "           END AS CHRGEMPNM2 "
			+ "         , BZ.DEALNM "
			+ "      FROM WEB_DCLTBIZ BZ "
			+ "     WHERE BZ.PRJTCD = <#PRJTCD#> "
			+ "     ORDER BY BZ.ACTCD ASC, BZ.BIZCD ASC, BZ.SEQ ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 인수/매각 거래형태 1 목록 조회
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_LEAGUE_BUY_DEAL1_LIST = 
			  "     SELECT DE.PRJTCD "
			+ "          , DE.DEALITEMCD "
			+ "          , CM.ITEMNM AS DEALITEMNM "
			+ "       FROM WEB_DCLTDEAL DE "
			+ "          , WEB_DCCOMCD CM "
			+ "      WHERE DE.PRJTCD = <#PRJTCD#> "
			+ "        AND CM.ITEMCD = DE.DEALITEMCD "
			+ "      ORDER BY DE.DEALITEMCD ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 편집 가능 롤 목록 조회
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_LEAGUE_EDITALBE_ROLE_LIST = 
			  "     SELECT ROLECD "
			+ "       FROM WEB_DCEDITAUTH "
			+ "      WHERE EDITYN = 'Y' ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 형태 지정 업데이트
	 * 
	 * <#CONSCD#> 자문 형태 코드
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_SETUP_CONSULT_TYPE = 
			  "     UPDATE WEB_DCLTBASE "
			+ "        SET CONSCD = <#CONSCD#> "
			+ "          , UPDDT = SYSDATE "
			+ "      WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 형태 지정 - 인수/매각 자문 등록
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_SETUP_CONSULT_BUY = 
			  "    INSERT INTO WEB_DCLTBUY ( "
			+ "           PRJTCD "
			+ "         , CREDT "
			+ "         , UPDDT "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#PRJTCD#> "
			+ "         , SYSDATE "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 형태 지정 - 합병 자문 등록
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_SETUP_CONSULT_MNA = 
			  "    INSERT INTO WEB_DCLTMNA ( "
			+ "           PRJTCD "
			+ "         , CREDT "
			+ "         , UPDDT "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#PRJTCD#> "
			+ "         , SYSDATE "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 형태 지정 - 부동산 자문 등록
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_SETUP_CONSULT_REAL = 
			  "    INSERT INTO WEB_DCLTREAL ( "
			+ "           PRJTCD "
			+ "         , CREDT "
			+ "         , UPDDT "
			+ "         ) "
			+ "     VALUES ( "
			+ "           <#PRJTCD#> "
			+ "         , SYSDATE "
			+ "         , SYSDATE "
			+ "         ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 형태 지정 - 인수/매각 타겟 등록
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_SETUP_CONSULT_BUY_TARGET = 
			  "	INSERT INTO WEB_DCLTBIZ ( "
			+ "		    PRJTCD "
			+ "		  , ACTCD "
			+ "		  , BIZCD "
			+ "		  , SEQ "
			+ "		  , BIZDIVCD "
			+ "		  , COMPCD "
			+ "		  , COMPHANNM "
			+ "		  , COMPENGNM "
			+ "		  , INDUCD "
			+ "		  , NATCD "
			+ "		  , CITY "
			+ "		  , ADDR "
			+ "		  , CREDT "
			+ "		  , UPDDT "
			+ "		  ) "
			+ "	 SELECT <#PRJTCD#> "
			+ "		  , '100401' "
			+ "		  , '100501' "
			+ "		  , ENT.SEQ "
			+ "		  , ENT.ENTDV "
			+ "		  , ENT.CODE "
			+ "		  , TRIM(ENT.KRNM) "
			+ "		  , TRIM(ENT.ENGNM) "
			+ "		  , ENT.INDUCD "
			+ "		  , ENT.NATION "
			+ "		  , ENT.CITY "
			+ "		  , ENT.ADDR "
			+ "		  , SYSDATE "
			+ "		  , SYSDATE "
			+ "	   FROM WEB_DCDBAENT ENT "
			+ "	  WHERE ENT.PRJCD = <#PRJTCD#> "
			+ "		AND ENT.ENTTYPE = '102002' ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 형태 지정 - 부동산 타겟 등록
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_SETUP_CONSULT_REAL_TARGET = 
			  "	INSERT INTO WEB_DCLTBIZ ( "
			+ "			PRJTCD "
			+ "		  , ACTCD "
			+ "		  , BIZCD "
			+ "		  , SEQ "
			+ "		  , NATCD "
			+ "		  , CITY "
			+ "		  , ADDR "
			+ "		  , DEALNM "
			+ "		  , CREDT "
			+ "		  , UPDDT "
			+ "		 ) "
			+ "	 SELECT <#PRJTCD#> "
			+ "		  , '100408' "
			+ "		  , '100501' "
			+ "		  , ENT.SEQ "
			+ "		  , ENT.NATION "
			+ "		  , ENT.CITY "
			+ "		  , ENT.ADDR "
			+ "		  , TRIM(ENT.KRNM) "
			+ "		  , SYSDATE "
			+ "		  , SYSDATE "
			+ "	   FROM WEB_DCDBAENT ENT "
			+ "	  WHERE ENT.PRJCD = <#PRJTCD#> "
			+ "		AND ENT.ENTTYPE = '102002' ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 형태 지정 - 삼일 클라이언트 등록
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * <#COMPCD#> 회사 코드
	 * <#COMPHANNM#> 회사 한글 이름
	 * <#COMPENGNM#> 회사 영문 이름
	 * <#NATCD#> 국가 코드
	 * <#INDUCD#> 산업 분류 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_SETUP_CONSULT_SAMIL = 
			  "	   INSERT INTO WEB_DCLTBIZ ( "
			+ "				   PRJTCD "
			+ "				 , ACTCD "
			+ "				 , BIZCD "
			+ "				 , SEQ "
			+ "				 , COMPCD "
			+ "				 , COMPHANNM "
			+ "				 , COMPENGNM "
			+ "				 , NATCD "
			+ "				 , INDUCD "
			+ "				 , CHRGEMPNO1 "
			+ "				 , CHRGEMPNM1 "
			+ "				 , CHRGEMPNO2 "
			+ "				 , CHRGEMPNM2 "
			+ "				 , CREDT "
			+ "				 , UPDDT "
			+ "				) "
			+ "			SELECT LEAG.PRJTCD "
			+ "				 , PT.SAMACTCD "
			+ "				 , PT.SAMBIZCD "
			+ "				 , 1 "
			+ "				 , <#COMPCD#> "
			+ "				 , <#COMPHANNM#> "
			+ "				 , <#COMPENGNM#> "
			+ "				 , <#NATCD#> "
			+ "				 , <#INDUCD#> "
			+ "				 , LEAG.PTREMPNO "
			+ "				 , ( SELECT HR.KORNM FROM HREMP HR WHERE HR.EMPLNO = LEAG.PTREMPNO ) "
			+ "				 , LEAG.MGREMPNO "
			+ "				 , ( SELECT HR.KORNM FROM HREMP HR WHERE HR.EMPLNO = LEAG.MGREMPNO ) "
			+ "				 , SYSDATE "
			+ "				 , SYSDATE "
			+ "			  FROM WEB_DCPDT PT "
			+ "				 , WEB_DCLTBASE LEAG "
			+ "			 WHERE LEAG.PRJTCD = <#PRJTCD#> "
			+ "			   AND PT.REFYEARLY = LEAG.REFYEARLY "
			+ "			   AND PT.PDTCD = LEAG.PDTCD "
			+ "			   AND PT.USEYN = 'Y' "
			+ "			   AND PT.SAMACTCD IS NOT NULL "
			+ "			   AND PT.SAMBIZCD IS NOT NULL ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 담당자 확인 YES
	 * 
	 * <#CHRGCONFCD#> 담당자 확인 코드
	 * <#EMPNO#> 담당자 사번
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_CONFIRM_CONSULT_YES = 
			  "     UPDATE WEB_DCLTBASE "
			+ "        SET CHRGCONFCD = <#CHRGCONFCD#> "
			+ "          , CHRGCONFEMPNO = <#EMPNO#> "
			+ "          , CHRGCONFDT = SYSDATE "
			+ "          , UPDDT = SYSDATE "
			+ "      WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 담당자 확인 NO
	 * 
	 * <#CHRGCONFCD#> 담당자 확인 코드
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_CONFIRM_CONSULT_NO = 
			  "     UPDATE WEB_DCLTBASE "
			+ "        SET CHRGCONFCD = <#CHRGCONFCD#> "
			+ "          , CHRGCONFEMPNO = NULL "
			+ "          , CHRGCONFDT = NULL "
			+ "          , UPDDT = SYSDATE "
			+ "      WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 ET 확인
	 * 
	 * <#EMPNO#> 담당자 사번
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_ET_CONFIRM_CONSULT = 
			  "     UPDATE WEB_DCLTBASE "
			+ "        SET ETCONFEMPNO = <#EMPNO#> "
			+ "          , ETCONFDT = SYSDATE "
			+ "          , UPDDT = SYSDATE "
			+ "      WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 대상 프로젝트 업데이트
	 * 
	 * <#LTTGTCD#> LeagueTable대상여부 코드
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_PROJECT_TARGET = 
			  "     UPDATE WEB_DCLTBASE "
			+ "        SET LTTGTCD = <#LTTGTCD#> "
			+ "          , UPDDT = SYSDATE "
			+ "      WHERE PRJTCD = <#PRJTCD#> ";
	
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
			+ "       SET STATUSCD = <#STATUSCD#> "
			+ "         , CONFCD = <#CONFCD#> "
			+ "         , COMNM = <#COMNM#> "
			+ "         , UPDDT = SYSDATE "
			+ "     WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 인수/매각 업데이트
	 * 
	 * <#TRGTETC#>
	 * <#BUYOUTCD#>
	 * <#CONSDT#>
	 * <#SPADT#>
	 * <#MOUDT#>
	 * <#CLOSDT#>
	 * <#CURRCD#>
	 * <#AMT#>
	 * <#RATE#>
	 * <#BORDDEALCD#>
	 * <#DEALCD2#>
	 * <#DEALCD3#>
	 * <#DEALCD4#>
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_LEAGUE_BUY = 
			  "    UPDATE WEB_DCLTBUY "
			+ "       SET TRGTETC = <#TRGTETC#> "
			+ "         , BUYOUTCD = <#BUYOUTCD#> "
			+ "         , CONSDT = TO_DATE(<#CONSDT#>, 'YYYYMMDD') "
			+ "         , SPADT = TO_DATE(<#SPADT#>, 'YYYYMMDD') "
			+ "         , MOUDT = TO_DATE(<#MOUDT#>, 'YYYYMMDD') "
			+ "         , CLOSDT = TO_DATE(<#CLOSDT#>, 'YYYYMMDD') "
			+ "         , CURRCD = <#CURRCD#> "
			+ "         , AMT = <#AMT#> "
			+ "         , RATE = <#RATE#> "
			+ "         , BORDDEALCD = <#BORDDEALCD#> "
			+ "         , DEALCD2 = <#DEALCD2#> "
			+ "         , DEALCD3 = <#DEALCD3#> "
			+ "         , DEALCD4 = <#DEALCD4#> "
			+ "         , UPDDT = SYSDATE "
			+ "     WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 합병 업데이트
	 * 
	 * <#CONSDT#>
	 * <#DIRTDT#>
	 * <#STKHDT#>
	 * <#MNADT#>
	 * <#CURRCD#>
	 * <#AMT#>
	 * <#RATE#>
	 * <#BORDDEALCD#>
	 * <#DEALCD1#>
	 * <#DEALCD2#>
	 * <#DEALCD3#>
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_LEAGUE_MNA = 
			  "    UPDATE WEB_DCLTMNA "
			+ "       SET CONSDT = TO_DATE(<#CONSDT#>, 'YYYYMMDD') "
			+ "         , DIRTDT = TO_DATE(<#DIRTDT#>, 'YYYYMMDD') "
			+ "         , STKHDT = TO_DATE(<#STKHDT#>, 'YYYYMMDD') "
			+ "         , MNADT = TO_DATE(<#MNADT#>, 'YYYYMMDD') "
			+ "         , CURRCD = <#CURRCD#> "
			+ "         , AMT = <#AMT#> "
			+ "         , RATE = <#RATE#> "
			+ "         , BORDDEALCD = <#BORDDEALCD#> "
			+ "         , DEALCD1 = <#DEALCD1#> "
			+ "         , DEALCD2 = <#DEALCD2#> "
			+ "         , DEALCD3 = <#DEALCD3#> "
			+ "         , UPDDT = SYSDATE "
			+ "     WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 부동산 업데이트
	 * 
	 * <#CONSDT#>
	 * <#SPADT#>
	 * <#MOUDT#>
	 * <#CLOSDT#>
	 * <#CURRCD#>
	 * <#AMT#>
	 * <#RATE#>
	 * <#BORDDEALCD#>
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_LEAGUE_REAL = 
			  "    UPDATE WEB_DCLTREAL "
			+ "       SET CONSDT = TO_DATE(<#CONSDT#>, 'YYYYMMDD') "
			+ "         , SPADT = TO_DATE(<#SPADT#>, 'YYYYMMDD') "
			+ "         , MOUDT = TO_DATE(<#MOUDT#>, 'YYYYMMDD') "
			+ "         , CLOSDT = TO_DATE(<#CLOSDT#>, 'YYYYMMDD') "
			+ "         , CURRCD = <#CURRCD#> "
			+ "         , AMT = <#AMT#> "
			+ "         , RATE = <#RATE#> "
			+ "         , BORDDEALCD = <#BORDDEALCD#> "
			+ "         , UPDDT = SYSDATE "
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
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table Buy 거래형태1 항목 등록
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * <#DEALITEMCD#> 거래형태 항목 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_LEAGUE_BUY_DEAL1 = 
			  "	INSERT INTO WEB_DCLTDEAL ( "
			+ "			PRJTCD "
			+ "		  , DEALITEMCD "
			+ "		 ) "
			+ "	 VALUES ( "
			+ "         <#PRJTCD#> "
			+ "		  , <#DEALITEMCD#> "
			+ "		 ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기업정보 등록
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_LEAGUE_BIZ = 
			  "	INSERT INTO WEB_DCLTBIZ ( "
			+ "		    PRJTCD "
			+ "		  , ACTCD "
			+ "		  , BIZCD "
			+ "		  , SEQ "
			+ "		  , BIZDIVCD "
			+ "		  , COMPCD "
			+ "		  , COMPHANNM "
			+ "		  , COMPENGNM "
			+ "		  , INDUCD "
			+ "		  , NATCD "
			+ "		  , NATNM "
			+ "		  , CHRGEMPNO1 "
			+ "		  , CHRGEMPNM1 "
			+ "		  , DEALNM "
			+ "		  , CREDT "
			+ "		  , UPDDT "
			+ "		  ) "
			+ "	 VALUES ( "
			+ "         <#PRJTCD#> "
			+ "		  , <#ACTCD#> "
			+ "		  , <#BIZCD#> "
			+ "		  , ( SELECT NVL(MAX(BZ.SEQ), 0) + 1 FROM WEB_DCLTBIZ BZ WHERE BZ.PRJTCD = <#PRJTCD#> AND BZ.ACTCD = <#ACTCD#> AND BZ.BIZCD = <#BIZCD#> ) "
			+ "		  , <#BIZDIVCD#> "
			+ "		  , <#COMPCD#> "
			+ "		  , TRIM(<#COMPHANNM#>) "
			+ "		  , TRIM(<#COMPENGNM#>) "
			+ "		  , <#INDUCD#> "
			+ "		  , <#NATCD#> "
			+ "		  , <#NATNM#> "
			+ "		  , <#CHRGEMPNO1#> "
			+ "		  , <#CHRGEMPNM1#> "
			+ "		  , <#DEALNM#> "
			+ "		  , SYSDATE "
			+ "		  , SYSDATE "
			+ "	      ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기업정보 변경
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_LEAGUE_BIZ = 
			  "    UPDATE WEB_DCLTBIZ "
			+ "       SET BIZDIVCD = <#BIZDIVCD#> "
			+ "         , COMPCD = <#COMPCD#> "
			+ "         , COMPHANNM = TRIM(<#COMPHANNM#>) "
			+ "         , COMPENGNM = TRIM(<#COMPENGNM#>) "
			+ "         , INDUCD = <#INDUCD#> "
			+ "         , NATCD = <#NATCD#> "
			+ "         , NATNM = <#NATNM#> "
			+ "         , CHRGEMPNO1 = <#CHRGEMPNO1#> "
			+ "         , CHRGEMPNM1 = <#CHRGEMPNM1#> "
			+ "         , DEALNM = <#DEALNM#> "
			+ "         , UPDDT = SYSDATE "
			+ "     WHERE PRJTCD = <#PRJTCD#> "
			+ "       AND ACTCD = <#ACTCD#> "
			+ "       AND BIZCD = <#BIZCD#> "
			+ "       AND SEQ = <#SEQ#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League Table 기업정보 삭제
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_LEAGUE_BIZ = 
			  "    DELETE FROM WEB_DCLTBIZ "
			+ "     WHERE PRJTCD = <#PRJTCD#> "
			+ "       AND ACTCD = <#ACTCD#> "
			+ "       AND BIZCD = <#BIZCD#> "
			+ "       AND SEQ = <#SEQ#> ";
	
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
	 * 파트너 관리 본부 코드 조회
	 * 
	 * <#PRTEMPNO#> 파트너 사번
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_PTREMP_HQCD = 
			  "    SELECT TMPHQCD "
			+ "         , DECODE( COUNT(EMPNO), 0, '999', TMPHQCD ) AS PTRHQCD "
			+ "      FROM ( "
			+ "              SELECT DECODE( HQ.HQCD, NULL, '999', HQ.HQCD ) AS TMPHQCD "
			+ "                   , HQEMP.EMPNO "
			+ "                FROM HREMP HR "
			+ "                   , WEB_DCHQ HQ "
			+ "                   , WEB_DCHQEMP HQEMP "
			+ "               WHERE HR.EMPLNO = <#PRTEMPNO#> "
			+ "                 AND HQ.HQCD(+) = HR.TEAMCD "
			+ "                 AND HQEMP.HQCD(+) = HQ.HQCD "
			+ "           ) "
			+ "     GROUP BY TMPHQCD ";
}
