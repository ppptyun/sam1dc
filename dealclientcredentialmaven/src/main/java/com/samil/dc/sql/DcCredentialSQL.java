package com.samil.dc.sql;

public final class DcCredentialSQL {

	public static final String SELECT_CREDENTIAL_LIST = ""
			+ " SELECT A.PRJTCD , "
			+ "        A.CISNO , "
			+ "        A.PRJTDIVCD , "
			+ "        DECODE( A.PRJTDIVCD, '102501', '주관', '102502', '지원', '' ) AS PRJTDIVNM , "
			+ "        B.CTGCD , "
			+ "        (SELECT CTGNM "
			+ "          FROM WEB_DCPDTCTG "
			+ "         WHERE REFYEARLY = B.REFYEARLY "
			+ "               AND CTGCD=B.CTGCD"
			+ "        ) CTGNM, "
			+ "        A.PDTCD , "
			+ "        REPLACE(B.PDTNM, chr(38)||'amp;', '&') PDTNM , "
			+ "        B_ORIG.CTGCD CTGCD_ORIG , "
			+ "        (SELECT CTGNM "
			+ "          FROM WEB_DCPDTCTG "
			+ "         WHERE REFYEARLY = B_ORIG.REFYEARLY "
			+ "               AND CTGCD=B_ORIG.CTGCD"
			+ "        ) CTGNM_ORIG, "
			+ "        A.PDTCD_ORIG, "
			+ "        REPLACE(B_ORIG.PDTNM, chr(38)||'amp;', '&') PDTNM_ORIG, "
			+ "        A.PRJTNM , "
			+ "        A.YEARLY , "
			+ "        A.HQCD , "
			+ "        (SELECT T.TEAMNM "
			+ "          FROM CMTEAMCD T "
			+ "         WHERE T.TEAMCD = A.HQCD"
			+ "        ) AS HQNM, "
			+ "        A.PTREMPNO , "
			+ "        SF_EMP_NAME(A.PTREMPNO) AS PTREMPNM , "
			+ "        A.MGREMPNO , "
			+ "        SF_EMP_NAME(A.MGREMPNO) AS MGREMPNM , "
			+ "        C.CLIENTCD , "
			+ "        C.CLIENTNM , "
			+ "        C.CLIENTNATCD, "
			+ "        CASE C.CLIENTNATCD "
			+ "            WHEN '-' "
			+ "            THEN C.CLIENTNATNM "
			+ "            WHEN 'ZZ' "
			+ "            THEN '해외' "
			+ "            ELSE "
			+ "                (SELECT CD.ETCDNM "
			+ "                  FROM CMETCOCD CD "
			+ "                 WHERE CD.SYTMDV='AC' "
			+ "                       AND CD.CLASCD='92' "
			+ "                       AND CD.MGATT4='Y' "
			+ "                       AND CD.ETCOCD = C.CLIENTNATCD "
			+ "                ) "
			+ "        END AS CLIENTNATNM, "
			+ "        C.CLIENTINDUCD, "
			+ "        (SELECT INDUNM "
			+ "          FROM NMSP.PMINDUCD "
			+ "         WHERE INDUCD=C.CLIENTINDUCD"
			+ "        ) AS CLIENTINDUNM, "
			+ "        CI.ETCOCD AS CLIENTGINDUCD, "
			+ "        CI.ETCDNM AS CLIENTGINDUNM,"
			+ "        C.TGT1_COMPHANNM AS TCOMPHANNM1 , "
			+ "        C.TGT1_NATCD AS TNATCD1 , "
			+ "        CASE C.TGT1_NATCD "
			+ "            WHEN '-' "
			+ "            THEN C.TGT1_NATNM "
			+ "            WHEN 'ZZ' "
			+ "            THEN '해외' "
			+ "            ELSE "
			+ "                (SELECT CD.ETCDNM "
			+ "                  FROM CMETCOCD CD "
			+ "                 WHERE CD.SYTMDV='AC' "
			+ "                       AND CD.CLASCD='92' "
			+ "                       AND CD.MGATT4='Y' "
			+ "                       AND CD.ETCOCD = C.TGT1_NATCD "
			+ "                ) "
			+ "        END AS TNATNM1, "
			+ "        C.TGT1_INDUCD AS TINDUCD1 , "
			+ "        (SELECT INDUNM "
			+ "          FROM NMSP.PMINDUCD "
			+ "         WHERE INDUCD=C.TGT1_INDUCD"
			+ "        ) AS TINDUNM1, "
			+ "        TI1.ETCOCD AS TGINDUCD1, "
			+ "        TI1.ETCDNM AS TGINDUNM1,"
			+ "        C.TGT2_COMPHANNM AS TCOMPHANNM2 , "
			+ "        C.TGT2_NATCD AS TNATCD2 , "
			+ "        CASE C.TGT2_NATCD "
			+ "            WHEN '-' "
			+ "            THEN C.TGT2_NATNM "
			+ "            WHEN 'ZZ' "
			+ "            THEN '해외' "
			+ "            ELSE "
			+ "                (SELECT CD.ETCDNM "
			+ "                  FROM CMETCOCD CD "
			+ "                 WHERE CD.SYTMDV='AC' "
			+ "                       AND CD.CLASCD='92' "
			+ "                       AND CD.MGATT4='Y' "
			+ "                       AND CD.ETCOCD = C.TGT2_NATCD "
			+ "                ) "
			+ "        END AS TNATNM2, "
			+ "        C.TGT2_INDUCD AS TINDUCD2 , "
			+ "        (SELECT INDUNM "
			+ "          FROM NMSP.PMINDUCD "
			+ "         WHERE INDUCD=C.TGT2_INDUCD"
			+ "        ) AS TINDUNM2, "
			+ "        TI2.ETCOCD AS TGINDUCD2, "
			+ "        TI2.ETCDNM AS TGINDUNM2,"
			+ "        C.TGT3_COMPHANNM AS TCOMPHANNM3 , "
			+ "        C.TGT3_NATCD AS TNATCD3 , "
			+ "        CASE C.TGT3_NATCD "
			+ "            WHEN '-' "
			+ "            THEN C.TGT3_NATNM "
			+ "            WHEN 'ZZ' "
			+ "            THEN '해외' "
			+ "            ELSE "
			+ "                (SELECT CD.ETCDNM "
			+ "                  FROM CMETCOCD CD "
			+ "                 WHERE CD.SYTMDV='AC' "
			+ "                       AND CD.CLASCD='92' "
			+ "                       AND CD.MGATT4='Y' "
			+ "                       AND CD.ETCOCD = C.TGT3_NATCD "
			+ "                ) "
			+ "        END AS TNATNM3, "
			+ "        C.TGT3_INDUCD AS TINDUCD3 , "
			+ "        (SELECT INDUNM "
			+ "          FROM NMSP.PMINDUCD "
			+ "         WHERE INDUCD=C.TGT3_INDUCD"
			+ "        ) AS TINDUNM3, "
			+ "        TI2.ETCOCD AS TGINDUCD3, "
			+ "        TI2.ETCDNM AS TGINDUNM3,"
			+ "        A.TRGTETC , "
			+ "        C.INTRCOMPHANNM AS INTRCOMPHANNM , "
			+ "        C.INTRNATCD AS INTRNATCD , "
			+ "        CASE C.INTRNATCD "
			+ "            WHEN '-' "
			+ "            THEN C.INTRNATNM "
			+ "            WHEN 'ZZ' "
			+ "            THEN '해외' "
			+ "            ELSE "
			+ "                (SELECT CD.ETCDNM "
			+ "                  FROM CMETCOCD CD "
			+ "                 WHERE CD.SYTMDV='AC' "
			+ "                       AND CD.CLASCD='92' "
			+ "                       AND CD.MGATT4='Y' "
			+ "                       AND CD.ETCOCD = C.INTRNATCD "
			+ "                ) "
			+ "        END AS INTRNATNM , "
			+ "        C.INTRINDUCD AS INTRINDUCD , "
			+ "        (SELECT INDUNM "
			+ "          FROM NMSP.PMINDUCD "
			+ "         WHERE INDUCD=C.INTRINDUCD"
			+ "        ) AS INTRINDUNM, "
			+ "        II.ETCOCD AS INTRGINDUCD, "
			+ "        II.ETCDNM AS INTRGINDUNM, "
			+ "        A.PRJTDESC , "
			+ "        TO_CHAR(A.CONTDT, 'YYYY-MM-DD') AS CONTDT, "
			+ "        TO_CHAR(A.TERMIDT, 'YYYY-MM-DD') AS TERMIDT , "
			+ "        A.CHAMTW , "
			+ "        A.BILLAMTW , "
			+ "        A.CREDCD , "
			+ "        (SELECT CM.ITEMNM "
			+ "          FROM WEB_DCCOMCD CM "
			+ "         WHERE CM.ITEMCD = A.CREDCD "
			+ "        ) AS CREDNM , "
			+ "        DECODE( D.PRJTCD, NULL, 'N', 'Y' ) AS LEAGUEYN , "
			+ "        D.DEALNM1 , "
			+ "        D.DEALNM2 , "
			+ "        D.DEALNM3 , "
			+ "        D.DEALNM4 , "
			+ "        D.CONSNM , "
			+ "        D.DEALNM , "
			+ "        D.EXCHANGE_AMT AMT, "
			+ "        D.CONFCD , "
			+ "        (SELECT CM.ITEMNM "
			+ "          FROM WEB_DCCOMCD CM "
			+ "         WHERE CM.ITEMCD = D.CONFCD "
			+ "        ) AS CONFNM , "
			+ "        D.ACTOR1 , "
			+ "        D.ACTOR2 , "
			+ "        D.STATUSCD , "
			+ "        D.STATUSNM , "
			+ "        C.BONDNM , "
			+ "        C.SECURE , "
			+ "        C.UNSECURE , "
			+ "        A.BRSSALECD , "
			+ "        (SELECT CM.ITEMNM "
			+ "          FROM WEB_DCCOMCD CM "
			+ "         WHERE CM.ITEMCD = A.BRSSALECD "
			+ "        ) AS BRSSALENM , "
			+ "        A.BRSOPB , "
			+ "        C.BRSBUYERNM , "
			+ "        C.ADDR RCFADDR, "
			+ "        (SELECT CM.ITEMNM "
			+ "          FROM WEB_DCCOMCD CM "
			+ "         WHERE CM.ITEMCD = A.RCFTYPECD "
			+ "        ) AS RCFTYPENM , "
			+ "        (SELECT CM.ITEMNM "
			+ "          FROM WEB_DCCOMCD CM "
			+ "         WHERE CM.ITEMCD = DECODE(A.RCFTYPEA, NULL, DECODE(RCFTYPEB, NULL, NULL, RCFTYPEB), A.RCFTYPEA) "
			+ "        ) AS RCFTYPEDTNM , "
			+ "        A.RCFTYPECD , "
			+ "        A.RCFTYPEETC , "
			+ "        A.RCFAREA , "
			+ "        A.RCFLAND , "
			+ "        A.CREDTGTCD "
			+ "   FROM WEB_DCCREDENTIAL A , "
			+ "        WEB_DCPDT B , "
			+ "        WEB_DCPDT B_ORIG , "
			+ "        WEB_DCCREDFORLIST_V C , "
			+ "        WEB_DCLTFORCRED_V D,"
			+ "        (SELECT A.INDUCD, T.ETCOCD, T.ETCDNM FROM PMINDUCD A, CMETCOCD T WHERE T.SYTMDV='PM' AND T.CLASCD='67' AND T.ETCOCD=A.TAG3) CI,"
			+ "        (SELECT A.INDUCD, T.ETCOCD, T.ETCDNM FROM PMINDUCD A, CMETCOCD T WHERE T.SYTMDV='PM' AND T.CLASCD='67' AND T.ETCOCD=A.TAG3) TI1,"
			+ "        (SELECT A.INDUCD, T.ETCOCD, T.ETCDNM FROM PMINDUCD A, CMETCOCD T WHERE T.SYTMDV='PM' AND T.CLASCD='67' AND T.ETCOCD=A.TAG3) TI2,"
			+ "        (SELECT A.INDUCD, T.ETCOCD, T.ETCDNM FROM PMINDUCD A, CMETCOCD T WHERE T.SYTMDV='PM' AND T.CLASCD='67' AND T.ETCOCD=A.TAG3) TI3,"
			+ "        (SELECT A.INDUCD, T.ETCOCD, T.ETCDNM FROM PMINDUCD A, CMETCOCD T WHERE T.SYTMDV='PM' AND T.CLASCD='67' AND T.ETCOCD=A.TAG3) II"
			+ "  WHERE A.CREDTGTCD = <#CREDTGTCD#> "
			+ "        AND B.REFYEARLY = A.REFYEARLY "
			+ "        AND B.PDTCD = A.PDTCD "
			+ "        AND B_ORIG.REFYEARLY = A.REFYEARLY_ORIG "
			+ "        AND B_ORIG.PDTCD = A.PDTCD_ORIG "
			+ "        AND A.PRJTCD = C.PRJTCD(+)  "
			+ "        AND A.PRJTCD = D.PRJTCD(+)"
			+ "        AND C.CLIENTINDUCD = CI.INDUCD(+)"
			+ "        AND C.TGT1_INDUCD = TI1.INDUCD(+)"
			+ "        AND C.TGT2_INDUCD = TI2.INDUCD(+)"
			+ "        AND C.TGT3_INDUCD = TI3.INDUCD(+)"
			+ "        AND C.INTRINDUCD = II.INDUCD(+)"
			+ "        <#SQL_CONDITION#> ";
	
			
			
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 목록 조회
	 * 
	 * <#SQL_CONDITION#> 검색 조건 1
	 * <#SQL_CONDITION_WRAP#> 검색 조건 2
	 * ====================================================================================
	 * </pre>
	 */
	/*public static final String SELECT_CREDENTIAL_LIST =
			  "    SELECT A.PRJTCD "
			+ "         , A.CISNO "
			+ "         , A.PRJTDIVCD "
			+ "         , DECODE( A.PRJTDIVCD, '102501', '주관', '102502', '지원', '' ) AS PRJTDIVNM "
			+ "         , A.CTGCD "
			+ "         , A.PDTCD "
			+ "         , A.PDTNM "
			+ "         , A.PRJTNM "
			+ "         , A.HQCD "
			+ "         , ( SELECT T.TEAMNM FROM CMTEAMCD T WHERE T.TEAMCD = A.HQCD ) AS HQNM "
			+ "         , A.PTREMPNO "
			+ "         , SF_EMP_NAME(A.PTREMPNO) AS PTREMPNM "
			+ "         , A.MGREMPNO "
			+ "         , SF_EMP_NAME(A.MGREMPNO) AS MGREMPNM "
			+ "         , A.CLIENTCD "
			+ "         , A.CLIENTNM "
			+ "         , A.CLIENTINDUCD "
			+ "         , A.CLIENTINDUNM "
			+ "         , A.CLIENTINDUGLNM "
			+ "         , A.CLIENTNATCD "
			+ "         , A.CLIENTNATNM "
			+ "         , A.TCOMPHANNM1 "
			+ "         , A.TNATCD1 "
			+ "         , A.TNATNM1 "
			+ "         , A.TINDUCD1 "
			+ "         , A.TINDUNM1 "
			+ "         , A.TINDUGLNM1 "
			+ "         , A.TCOMPHANNM2 "
			+ "         , A.TNATCD2 "
			+ "         , A.TNATNM2 "
			+ "         , A.TINDUCD2 "
			+ "         , A.TINDUNM2 "
			+ "         , A.TINDUGLNM2 "
			+ "         , A.TCOMPHANNM3 "
			+ "         , A.TNATCD3 "
			+ "         , A.TNATNM3 "
			+ "         , A.TINDUCD3 "
			+ "         , A.TINDUNM3 "
			+ "         , A.TINDUGLNM3 "
			+ "         , A.TRGTETC "
			+ "         , A.INTRCOMPHANNM "
			+ "         , A.INTRNATCD "
			+ "         , A.INTRNATNM "
			+ "         , A.INTRINDUCD "
			+ "         , A.INTRINDUNM "
			+ "         , A.INTRINDUGLNM "
			+ "         , A.PRJTDESC "
			+ "         , TO_CHAR(A.CONTDT, 'YYYY-MM-DD') AS CONTDT "
			+ "         , TO_CHAR(A.TERMIDT, 'YYYY-MM-DD') AS TERMIDT "
			+ "         , A.CHAMTW "
			+ "         , A.CREDCD "
			+ "         , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = A.CREDCD ) AS CREDNM "
			+ "         , A.LEAGUEYN "
			+ "         , A.DEALNM1 "
			+ "         , A.DEALNM2 "
			+ "         , A.DEALNM3 "
			+ "         , A.DEALNM4 "
			+ "         , A.CONSNM "
			+ "         , A.DEALNM "
			+ "         , A.AMT "
			+ "         , A.CONFCD "
			+ "         , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = A.CONFCD ) AS CONFNM "
			+ "         , A.ACTOR1 "
			+ "         , A.ACTOR2 "
			+ "         , A.STATUSCD "
			+ "         , A.STATUSNM "
			+ "         , A.BONDNM "
			+ "         , A.SECURE "
			+ "         , A.UNSECURE "
			+ "         , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = A.BRSSALECD ) AS BRSSALENM "
			+ "         , A.BRSOPB "
			+ "         , A.BRSBUYERNM "
			+ "         , A.RCFADDR "
			+ "         , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = A.RCFTYPECD ) AS RCFTYPENM "
			+ "         , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = A.RCFTYPEDT ) AS RCFTYPEDTNM "
			+ "         , A.RCFTYPEETC "
			+ "         , A.RCFAREA "
			+ "         , A.RCFLAND "
			+ "         , A.CREDTGTCD "
			+ "      FROM ( "
			+ "                SELECT A.PRJTCD "
			+ "                     , A.CISNO "
			+ "                     , A.PRJTDIVCD "
			+ "                     , DECODE( A.PRJTDIVCD, '102501', '주관', '102502', '지원', '' ) AS PRJTDIVNM "
			+ "                     , A.CTGCD "
			+ "                     , A.PDTCD "
			+ "                     , A.PDTNM "
			+ "                     , A.PRJTNM "
			+ "                     , A.HQCD "
			+ "                     , A.PTREMPNO "
			+ "                     , A.MGREMPNO "
			+ "                     , A.CLIENTCD "
			+ "                     , A.CLIENTNM "
			+ "                     , A.CLIENTINDUCD "
			+ "                     , ( SELECT IND.INDUNM FROM PMINDUCD IND WHERE IND.INDUCD = A.CLIENTINDUCD ) AS CLIENTINDUNM "
			+ "                     , ( SELECT GLD.GLINDUNMK FROM PMINDGLCD GLD, PMINDUCD IND WHERE IND.INDUCD = A.CLIENTINDUCD AND GLD.GLINDUCD = IND.GLINDUCD ) AS CLIENTINDUGLNM "
			+ "                     , A.CLIENTNATCD "
			+ "                     , A.CLIENTNATNM "
			+ "                     , A.TCOMPHANNM1 "
			+ "                     , A.TNATCD1 "
			+ "                     , A.TNATNM1 "
			+ "                     , A.TINDUCD1 "
			+ "                     , ( SELECT IND.INDUNM FROM PMINDUCD IND WHERE IND.INDUCD = A.TINDUCD1 ) AS TINDUNM1 "
			+ "                     , ( SELECT GLD.GLINDUNMK FROM PMINDGLCD GLD, PMINDUCD IND WHERE IND.INDUCD = A.TINDUCD1 AND GLD.GLINDUCD = IND.GLINDUCD ) AS TINDUGLNM1 "
			+ "                     , A.TCOMPHANNM2 "
			+ "                     , A.TNATCD2 "
			+ "                     , A.TNATNM2 "
			+ "                     , A.TINDUCD2 "
			+ "                     , ( SELECT IND.INDUNM FROM PMINDUCD IND WHERE IND.INDUCD = A.TINDUCD2 ) AS TINDUNM2 "
			+ "                     , ( SELECT GLD.GLINDUNMK FROM PMINDGLCD GLD, PMINDUCD IND WHERE IND.INDUCD = A.TINDUCD2 AND GLD.GLINDUCD = IND.GLINDUCD ) AS TINDUGLNM2 "
			+ "                     , A.TCOMPHANNM3 "
			+ "                     , A.TNATCD3 "
			+ "                     , A.TNATNM3 "
			+ "                     , A.TINDUCD3 "
			+ "                     , ( SELECT IND.INDUNM FROM PMINDUCD IND WHERE IND.INDUCD = A.TINDUCD3 ) AS TINDUNM3 "
			+ "                     , ( SELECT GLD.GLINDUNMK FROM PMINDGLCD GLD, PMINDUCD IND WHERE IND.INDUCD = A.TINDUCD3 AND GLD.GLINDUCD = IND.GLINDUCD ) AS TINDUGLNM3 "
			+ "                     , A.TRGTETC "
			+ "                     , A.INTRCOMPHANNM "
			+ "                     , A.INTRNATCD "
			+ "                     , A.INTRNATNM "
			+ "                     , A.INTRINDUCD "
			+ "                     , ( SELECT IND.INDUNM FROM PMINDUCD IND WHERE IND.INDUCD = A.INTRINDUCD ) AS INTRINDUNM "
			+ "                     , ( SELECT GLD.GLINDUNMK FROM PMINDGLCD GLD, PMINDUCD IND WHERE IND.INDUCD = A.INTRINDUCD AND GLD.GLINDUCD = IND.GLINDUCD ) AS INTRINDUGLNM "
			+ "                     , A.PRJTDESC "
			+ "                     , A.CONTDT "
			+ "                     , A.TERMIDT "
			+ "                     , A.CHAMTW "
			+ "                     , A.CREDCD "
			+ "                     , A.LEAGUEYN "
			+ "                     , A.DEALNM1 "
			+ "                     , A.DEALNM2 "
			+ "                     , A.DEALNM3 "
			+ "                     , A.DEALNM4 "
			+ "                     , A.CONSNM "
			+ "                     , A.DEALNM "
			+ "                     , A.AMT "
			+ "                     , A.CONFCD "
			+ "                     , A.ACTOR1 "
			+ "                     , A.ACTOR2 "
			+ "                     , A.STATUSCD "
			+ "                     , A.STATUSNM "
			+ "                     , A.BONDNM "
			+ "                     , A.SECURE "
			+ "                     , A.UNSECURE "
			+ "                     , A.BRSSALECD "
			+ "                     , A.BRSOPB "
			+ "                     , A.BRSBUYERNM "
			+ "                     , A.ADDR AS RCFADDR "
			+ "                     , A.RCFTYPECD "
			+ "                     , A.RCFTYPEDT "
			+ "                     , A.RCFTYPEETC "
			+ "                     , A.RCFAREA "
			+ "                     , A.RCFLAND "
			+ "                     , A.CREDTGTCD "
			+ "                  FROM ( "
			+ "                            SELECT A.PRJTCD "
			+ "                                 , A.CISNO "
			+ "                                 , A.PRJTDIVCD "
			+ "                                 , B.CTGCD "
			+ "                                 , A.PDTCD "
			+ "                                 , B.PDTNM "
			+ "                                 , A.PRJTNM "
			+ "                                 , A.YEARLY "
			+ "                                 , A.HQCD "
			+ "                                 , A.PTREMPNO "
			+ "                                 , A.MGREMPNO "
			+ "                                 , C.CLIENTCD "
			+ "                                 , C.CLIENTNM "
			+ "                                 , C.CLIENTINDUCD "
			+ "                                 , C.CLIENTNATCD "
			+ "                                 , CASE C.CLIENTNATCD "
			+ "                                       WHEN '-' THEN C.CLIENTNATNM "
			+ "                                       WHEN 'ZZ' THEN '해외' "
			+ "                                       ELSE ( SELECT CD.ETCDNM FROM CMETCOCD CD WHERE CD.SYTMDV='AC' AND CD.CLASCD='92' AND CD.MGATT4='Y' AND CD.ETCOCD = C.CLIENTNATCD ) "
			+ "                                   END AS CLIENTNATNM "
			+ "                                 , C.TGT1_COMPHANNM AS TCOMPHANNM1 "
			+ "                                 , C.TGT1_NATCD AS TNATCD1 "
			+ "                                 , CASE C.TGT1_NATCD "
			+ "                                       WHEN '-' THEN C.TGT1_NATNM "
			+ "                                       WHEN 'ZZ' THEN '해외' "
			+ "                                       ELSE ( SELECT CD.ETCDNM FROM CMETCOCD CD WHERE CD.SYTMDV='AC' AND CD.CLASCD='92' AND CD.MGATT4='Y' AND CD.ETCOCD = C.TGT1_NATCD ) "
			+ "                                   END AS TNATNM1 "
			+ "                                 , C.TGT1_INDUCD AS TINDUCD1 "
			+ "                                 , C.TGT2_COMPHANNM  AS TCOMPHANNM2 "
			+ "                                 , C.TGT2_NATCD AS TNATCD2 "
			+ "                                 , CASE C.TGT2_NATCD "
			+ "                                       WHEN '-' THEN C.TGT2_NATNM "
			+ "                                       WHEN 'ZZ' THEN '해외' "
			+ "                                       ELSE ( SELECT CD.ETCDNM FROM CMETCOCD CD WHERE CD.SYTMDV='AC' AND CD.CLASCD='92' AND CD.MGATT4='Y' AND CD.ETCOCD = C.TGT2_NATCD ) "
			+ "                                   END AS TNATNM2 "
			+ "                                 , C.TGT2_INDUCD AS TINDUCD2 "
			+ "                                 , C.TGT3_COMPHANNM AS TCOMPHANNM3 "
			+ "                                 , C.TGT3_NATCD AS TNATCD3 "
			+ "                                 , CASE C.TGT3_NATCD "
			+ "                                       WHEN '-' THEN C.TGT3_NATNM "
			+ "                                       WHEN 'ZZ' THEN '해외' "
			+ "                                       ELSE ( SELECT CD.ETCDNM FROM CMETCOCD CD WHERE CD.SYTMDV='AC' AND CD.CLASCD='92' AND CD.MGATT4='Y' AND CD.ETCOCD = C.TGT3_NATCD ) "
			+ "                                   END AS TNATNM3 "
			+ "                                 , C.TGT3_INDUCD AS TINDUCD3 "
			+ "                                 , A.TRGTETC "
			+ "                                 , C.INTRCOMPHANNM AS INTRCOMPHANNM "
			+ "                                 , C.INTRNATCD AS INTRNATCD "
			+ "                                 , CASE C.INTRNATCD "
			+ "                                       WHEN '-' THEN C.INTRNATNM "
			+ "                                       WHEN 'ZZ' THEN '해외' "
			+ "                                       ELSE ( SELECT CD.ETCDNM FROM CMETCOCD CD WHERE CD.SYTMDV='AC' AND CD.CLASCD='92' AND CD.MGATT4='Y' AND CD.ETCOCD = C.INTRNATCD ) "
			+ "                                   END AS INTRNATNM "
			+ "                                 , C.INTRINDUCD AS INTRINDUCD "
			+ "                                 , A.PRJTDESC "
			+ "                                 , A.CONTDT "
			+ "                                 , A.TERMIDT "
			+ "                                 , A.CHAMTW "
			+ "                                 , A.CREDCD "
			+ "                                 , DECODE( D.PRJTCD, NULL, 'N', 'Y' ) AS LEAGUEYN "
			+ "                                 , D.DEALNM1 "
			+ "                                 , D.DEALNM2 "
			+ "                                 , D.DEALNM3 "
			+ "                                 , D.DEALNM4 "
			+ "                                 , D.CONSNM "
			+ "                                 , D.DEALNM "
			+ "                                 , D.AMT "
			+ "                                 , D.CONFCD "
			+ "                                 , D.ACTOR1 "
			+ "                                 , D.ACTOR2 "
			+ "                                 , D.STATUSCD "
			+ "                                 , D.STATUSNM "
			+ "                                 , C.BONDNM "
			+ "                                 , C.SECURE "
			+ "                                 , C.UNSECURE "
			+ "                                 , A.BRSSALECD "
			+ "                                 , A.BRSOPB "
			+ "                                 , C.BRSBUYERNM "
			+ "                                 , C.ADDR "
			+ "                                 , A.RCFTYPECD "
			+ "                                 , CASE "
			+ "                                       WHEN A.RCFTYPEA IS NOT NULL THEN A.RCFTYPEA "
			+ "                                       WHEN A.RCFTYPEB IS NOT NULL THEN A.RCFTYPEB "
			+ "                                       ELSE NULL "
			+ "                                   END RCFTYPEDT "
			+ "                                 , A.RCFTYPEETC "
			+ "                                 , A.RCFAREA "
			+ "                                 , A.RCFLAND "
			+ "                                 , A.CREDTGTCD "
			+ "                              FROM WEB_DCCREDENTIAL A "
			+ "                                 , WEB_DCPDT B "
			+ "                                 , WEB_DCCREDFORLIST_V C "
			+ "                                 , WEB_DCLTFORCRED_V D "
			+ "                             WHERE A.CREDTGTCD = <#CREDTGTCD#> "
			+ "                               AND B.REFYEARLY = A.REFYEARLY "
			+ "                               AND B.PDTCD = A.PDTCD "
			+ "                               AND C.PRJTCD(+) = A.PRJTCD "
			+ "                               AND D.PRJTCD(+) = A.PRJTCD "
			+ "                       ) A "
			+ "                 WHERE 1 = 1 "
			+ "				   <#SQL_CONDITION#> "
			+ "           ) A "
			+ "     WHERE 1 = 1 "
			+ "       <#SQL_CONDITION_WRAP#> ";*/

	/**
	 * <pre>
	 * ====================================================================================
	 * Credential Product Category 목록 조회
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_CREDENTIAL_PRODUCT_CATEGORY_LIST = 
			  "    SELECT DISTINCT CTGCD "
			+ "         , REPLACE(CTGNM, chr(38)||'amp;', '&') CTGNM "
			+ "      FROM WEB_DCPDTCTG "
			+ "     WHERE USEYN='Y' "
			+ "     ORDER BY CTGNM ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential Product 목록 조회
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_CREDENTIAL_PRODUCT_LIST = 
			  "    SELECT DISTINCT PDTCD "
			+ "         , REPLACE(PDTNM, chr(38)||'amp;', '&') PDTNM "
			+ "      FROM WEB_DCPDT "
			+ "     WHERE USEYN='Y' "
			+ "       <#SEARCH_CONDITION#> "
			+ "     ORDER BY PDTCD ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential Product 목록 조회 - 팝업용
	 * 
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_CREDENTIAL_PRODUCT_LIST_WITH_CATEGORY = 
			" SELECT A.REFYEARLY,"
		  + "        B.CTGCD, "
		  + "        B.CTGNM, "
		  + "        A.PDTCD, "
		  + "        REPLACE(A.PDTNM, chr(38)||'amp;', '&') PDTNM, "
		  + "        A.COMNM "
		  + "   FROM WEB_DCPDT A, "
		  + "        WEB_DCPDTCTG B "
		  + "  WHERE A.REFYEARLY=B.REFYEARLY "
		  + "        AND A.CTGCD = B.CTGCD "
		  + "        AND A.USEYN = 'Y' "
		  + "        AND B.USEYN = 'Y' "
		  + "        AND A.REFYEARLY = <#REFYEARLY#> "
		  + " ORDER BY B.CTGCD, "
		  + "        A.PDTCD" ;
	
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 수정을 위한 상세 조회
	 * 
	 * <#PRJTCD#>
	 * ====================================================================================
	 * </pre>  
	 */
	public static final String SELECT_CREDENTIAL_DETAIL = 
			  "    SELECT A.PRJTCD "
			+ "         , A.PRJTNM "
			+ "         , A.REFYEARLY "
			+ "         , A.PDTCD "
			+ "         , (SELECT REPLACE(PDTNM, chr(38)||'amp;', '&') FROM WEB_DCPDT WHERE REFYEARLY=A.REFYEARLY AND PDTCD=A.PDTCD) PDTNM "
			+ "         , A.REFYEARLY_ORIG "
			+ "         , A.PDTCD_ORIG "
			+ "         , (SELECT REPLACE(PDTNM, chr(38)||'amp;', '&') FROM WEB_DCPDT WHERE REFYEARLY=A.REFYEARLY AND PDTCD=A.PDTCD_ORIG) PDTNM_ORIG "
			+ "         , A.PRJTDESC "
			+ "         , A.TRGTETC "
			+ "         , A.BRSSALECD "
			+ "         , A.BRSOPB "
			+ "         , A.RCFTYPECD "
			+ "         , A.RCFTYPEA "
			+ "         , A.RCFTYPEB "
			+ "         , A.RCFTYPEETC "
			+ "         , A.RCFAREA "
			+ "         , A.RCFLAND "
			+ "         , A.CREDTGTCD "
			+ "         , ( SELECT CM.ITEMNM FROM WEB_DCCOMCD CM WHERE CM.ITEMCD = A.CREDTGTCD ) AS CREDTGTNM "
			+ "      FROM WEB_DCCREDENTIAL A "
			+ "     WHERE A.PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 기업 목록 조회
	 * 
	 * <#PRJTCD#>
	 * <#BIZCD#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_CREDENTIAL_BIZ_LIST = 
			  "    SELECT A.PRJTCD "
			+ "         , A.BIZCD "
			+ "         , A.SEQ "
			+ "         , A.BIZDIVCD "
			+ "         , A.COMPCD "
			+ "         , A.COMPHANNM "
			+ "         , A.COMPENGNM "
			+ "         , A.INDUCD "
			+ "         , ( SELECT IND.INDUNM FROM PMINDUCD IND WHERE IND.INDUCD = A.INDUCD ) AS INDUNM "
			+ "         , A.NATCD "
			+ "         , CASE A.NATCD "
			+ "               WHEN '-' THEN A.NATNM "
			+ "               WHEN 'ZZ' THEN '해외' "
			+ "               ELSE ( SELECT CD.ETCDNM FROM CMETCOCD CD WHERE CD.SYTMDV='AC' AND CD.CLASCD='92' AND CD.MGATT4='Y' AND CD.ETCOCD = A.NATCD ) "
			+ "           END AS NATNM "
			+ "         , A.CITY "
			+ "         , A.ADDR "
			+ "      FROM WEB_DCCREDBIZ A "
			+ "     WHERE A.PRJTCD = <#PRJTCD#> "
			+ "       AND A.BIZCD = <#BIZCD#> "
			+ "     ORDER BY A.SEQ ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 추가항목 목록 조회
	 * 
	 * <#PRJTCD#>
	 * <#CTGCD#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_CREDENTIAL_ADD_LIST = 
			  "    SELECT A.PRJTCD "
			+ "         , A.ITEMCD "
			+ "         , B.ITEMNM "
			+ "         , A.CTGCD "
			+ "         , A.BIGO "
			+ "      FROM WEB_DCCREDADD A "
			+ "         , WEB_DCCOMCD B "
			+ "     WHERE A.PRJTCD = <#PRJTCD#> "
			+ "       AND A.CTGCD = <#CTGCD#> "
			+ "       AND B.ITEMCD = A.ITEMCD "
			+ "     ORDER BY A.ITEMCD ASC ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 기본 정보 업데이트
	 * 
	 * <#PRJTDESC#>
	 * <#TRGTETC#>
	 * <#BRSSALECD#>
	 * <#BRSOPB#>
	 * <#RCFTYPECD#>
	 * <#RCFTYPEA#>
	 * <#RCFTYPEB#>
	 * <#RCFTYPEETC#>
	 * <#RCFAREA#>
	 * <#RCFLAND#>
	 * <#CREDTGTCD#>
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_CREDENTIAL_BASE_INFO = 
			  "     UPDATE WEB_DCCREDENTIAL "
			+ "        SET PRJTDESC = <#PRJTDESC#> "
			+ "          , REFYEARLY = <#REFYEARLY#> "
			+ "          , CTGCD = <#CTGCD#> "
			+ "          , PDTCD = <#PDTCD#> "
			+ "          , TRGTETC = <#TRGTETC#> "
			+ "          , BRSSALECD = <#BRSSALECD#> "
			+ "          , BRSOPB = <#BRSOPB#> "
			+ "          , RCFTYPECD = <#RCFTYPECD#> "
			+ "          , RCFTYPEA = <#RCFTYPEA#> "
			+ "          , RCFTYPEB = <#RCFTYPEB#> "
			+ "          , RCFTYPEETC = <#RCFTYPEETC#> "
			+ "          , RCFAREA = <#RCFAREA#> "
			+ "          , RCFLAND = <#RCFLAND#> "
			+ "          , CREDTGTCD = <#CREDTGTCD#> "
			+ "          , UPDDT = SYSDATE "
			+ "      WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 클라이언트 국가 정보 업데이트
	 * 
	 * <#NATCD#>
	 * <#NATNM#>
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_CREDENTIAL_CLIENT_NAT = 
			  "     UPDATE WEB_DCCREDBIZ "
			+ "        SET NATCD = <#NATCD#> "
			+ "          , NATNM = <#NATNM#> "
			+ "          , UPDDT = SYSDATE "
			+ "      WHERE PRJTCD = <#PRJTCD#> "
			+ "        AND BIZCD = '102001' "
			+ "        AND SEQ = 1 ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 기업 정보 업데이트
	 * 
	 * <#NATCD#>
	 * <#NATNM#>
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String UPDATE_CREDENTIAL_BIZ = 
			  "     UPDATE WEB_DCCREDBIZ "
			+ "        SET BIZDIVCD = <#BIZDIVCD#> "
			+ "          , COMPCD = <#COMPCD#> "
			+ "          , COMPHANNM = <#COMPHANNM#> "
			+ "          , COMPENGNM = <#COMPENGNM#> "
			+ "          , INDUCD = <#INDUCD#> "
			+ "          , NATCD = <#NATCD#> "
			+ "          , NATNM = <#NATNM#> "
			+ "          , CITY = <#CITY#> "
			+ "          , ADDR = <#ADDR#> "
			+ "          , UPDDT = SYSDATE "
			+ "      WHERE PRJTCD = <#PRJTCD#> "
			+ "        AND BIZCD = <#BIZCD#> "
			+ "        AND SEQ = <#SEQ#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 기업 정보 등록
	 * 
	 * <#NATCD#>
	 * <#NATNM#>
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_CREDENTIAL_BIZ =
			  "	INSERT INTO WEB_DCCREDBIZ ( "
			+ "		    PRJTCD "
			+ "		  , BIZCD "
			+ "		  , SEQ "
			+ "		  , BIZDIVCD "
			+ "		  , COMPCD "
			+ "		  , COMPHANNM "
			+ "		  , COMPENGNM "
			+ "		  , INDUCD "
			+ "		  , NATCD "
			+ "		  , NATNM "
			+ "		  , CITY "
			+ "		  , ADDR "
			+ "		  , CREDT "
			+ "		  , UPDDT "
			+ "		  ) "
			+ "	 VALUES ( "
			+ "         <#PRJTCD#> "
			+ "		  , <#BIZCD#> "
			+ "		  , ( SELECT NVL(MAX(SB.SEQ), 0) + 1 FROM WEB_DCCREDBIZ SB WHERE SB.PRJTCD = <#PRJTCD#> AND  SB.BIZCD = <#BIZCD#> ) "
			+ "		  , <#BIZDIVCD#> "
			+ "		  , <#COMPCD#> "
			+ "		  , <#COMPHANNM#> "
			+ "		  , <#COMPENGNM#> "
			+ "		  , <#INDUCD#> "
			+ "		  , <#NATCD#> "
			+ "		  , <#NATNM#> "
			+ "		  , <#CITY#> "
			+ "		  , <#ADDR#> "
			+ "		  , SYSDATE "
			+ "		  , SYSDATE "
			+ "	      ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 추가 정보 삭제(프로젝트 단위로 전체 삭제)
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String DELETE_CREDENTIAL_ADD_ALL = 
			  "     DELETE FROM WEB_DCCREDADD "
			+ "      WHERE PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 추가 정보 등록
	 * 
	 * <#NATCD#>
	 * <#NATNM#>
	 * <#PRJTCD#> 프로젝트 코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_CREDENTIAL_ADD =
			  "	INSERT INTO WEB_DCCREDADD ( "
			+ "		    PRJTCD "
			+ "		  , ITEMCD "
			+ "		  , CTGCD "
			+ "		  , BIGO "
			+ "		  ) "
			+ "	 VALUES ( "
			+ "         <#PRJTCD#> "
			+ "		  , <#ITEMCD#> "
			+ "		  , <#CTGCD#> "
			+ "		  , <#BIGO#> "
			+ "	      ) ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * Credential 타입별 국가 조회
	 * 
	 * <#BIZCD#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_CREDENTIAL_COMPANY_TYPE_NATION =
			  "	SELECT DISTINCT "
			+ "		    CASE WHEN NATCD='KR' THEN 1 ELSE 99 END AS orderno "
			+ "		  , NATCD "
			+ "		  , CASE WHEN NATCD='ZZ' THEN '해외' ELSE (SELECT ETCDNM FROM CMETCOCD WHERE SYTMDV='AC' AND CLASCD='92' AND MGATT4='Y' AND ETCOCD = NATCD) END AS NATNM  "
			+ "	 FROM WEB_DCCREDBIZ "
			+ "  WHERE BIZCD = <#BIZCD#> "
			+ "	 AND NATCD IS NOT NULL "
			+ "	 ORDER BY ORDERNO, NATNM ";
	
}
