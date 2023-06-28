package com.samil.dc.sql;

public final class DcAdminFieldLogSQL {
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 로그 목록 조회
	 * 
	 * <#SEARCH_CONDITION#>
	 * <#PAGE_START#>
	 * <#PAGE_END#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_FIELD_LOG_LIST_COUNT = 
			  " 				SELECT COUNT(A.SEQ) AS CNT "
			+ " 				  FROM WEB_DCFILDLOG A "
			+ " 				 WHERE 1 = 1 "
			+ "	                   <#SEARCH_CONDITION#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 로그 목록 조회
	 * 
	 * <#SEARCH_CONDITION#>
	 * <#PAGE_START#>
	 * <#PAGE_END#>
	 * ====================================================================================
	 * </pre>
	 */
	public static final String SELECT_FIELD_LOG_LIST = 
			  " SELECT TEMP_RNUM "
			+ "      , SEQ "
			+ "      , PRJTCD "
			+ "      , FILDCD "
			+ "      , FILDNM "
			+ "      , LOGCD "
			+ "      , ( SELECT CD.ITEMNM FROM WEB_DCCOMCD CD WHERE CD.ITEMCD = LOGCD ) AS LOGNM "
			+ "      , ALTCD "
			+ "      , ( SELECT CD.ITEMNM FROM WEB_DCCOMCD CD WHERE CD.ITEMCD = ALTCD ) AS ALTNM "
			+ "      , BEFVAL "
			+ "      , ALTVAL "
			+ "      , ALTEMPNO "
			+ "      , SF_EMP_NAME(ALTEMPNO) AS ALTEMPNM "
			+ "      , SF_EMP_GRADNM(ALTEMPNO) AS ALTGRADNM "
			+ "      , TO_CHAR(ALTDT, 'YYYY-MM-DD HH24:MI:SS') AS ALTDT "
			+ " 	 , BIGO "
			+ "   FROM ( "
			+ " 		SELECT ROWNUM AS TEMP_RNUM "
			+ " 			 , TMP.SEQ "
			+ " 			 , TMP.PRJTCD "
			+ " 			 , TMP.FILDCD "
			+ " 			 , TMP.FILDNM "
			+ " 			 , TMP.LOGCD "
			+ " 			 , TMP.ALTCD "
			+ " 			 , TMP.BEFVAL "
			+ " 			 , TMP.ALTVAL "
			+ " 			 , TMP.ALTEMPNO "
			+ " 			 , TMP.ALTDT "
			+ " 			 , TMP.BIGO "
			+ " 		  FROM ( "
			+ " 				SELECT A.SEQ "
			+ " 					 , A.PRJTCD "
			+ " 					 , A.FILDCD "
			+ " 					 , A.FILDNM "
			+ " 					 , A.LOGCD "
			+ " 					 , A.ALTCD "
			+ " 					 , A.BEFVAL "
			+ " 					 , A.ALTVAL "
			+ " 					 , A.ALTEMPNO "
			+ " 					 , A.ALTDT "
			+ " 					 , A.BIGO "
			+ " 				  FROM WEB_DCFILDLOG A "
			+ " 				 WHERE 1 = 1 "
			+ "	                   <#SEARCH_CONDITION#> "
			+ " 				 ORDER BY A.SEQ DESC "
			+ " 			   ) TMP "
			+ " 		 WHERE ROWNUM <= <#PAGE_END#> "
			+ "        )  "
			+ "  WHERE TEMP_RNUM > <#PAGE_START#> ";
	
}
