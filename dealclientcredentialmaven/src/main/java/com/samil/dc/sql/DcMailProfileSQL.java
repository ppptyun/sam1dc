package com.samil.dc.sql;

public final class DcMailProfileSQL {
	/**
	 * 메일 프로파일 전체 검색 쿼리
	 */
	private static final String _SELECT_MAIL_PROFILE_BASE	= "SELECT SYSCD,MAILID,SUBJECT,CONTENTS,DES,USEYN,TO_CHAR(CREATED,'YYYY-MM-DD HH24:Mi:SS') AS CREATED,CREATEDBY,TO_CHAR(MODIFIED,'YYYY-MM-DD HH24:Mi:SS') AS MODIFIED,MODIFIEDBY FROM NMSP.WEB_APPMAILPROF ";
	
	/**
	 * 어플리케이션 별 메일 프로파일 리스트 검색 쿼리
	 */
	public static final String SELECT_MAIL_PROFILE_LIST 	= _SELECT_MAIL_PROFILE_BASE + "WHERE SYSCD = <#SYSCD#>";
	
	/**
	 * 특정 메일 프로파일 검색 쿼리
	 */
	public static final String SELECT_MAIL_PROFILE 			= _SELECT_MAIL_PROFILE_BASE + "WHERE SYSCD=<#SYSCD#> AND MAILID=<#MAILID#>";
	
	
	/**
	 * 메일 프로파일 추가 및 업데이트
	 */
	public static final String MERGE_INTO_MAIL_PROFILE		= "{CALL NMSP.SP_WEBMAILPROF(<#SYSCD#>,<#MAILID#>,<#SUBJECT#>,<#CONTENTS#>,<#DES#>,<#USEYN#>,<#EMPLNO#>)}";
	

	/**
	 * 메일 프로파일 삭제
	 */
	public static final String DELETE_MAIL_PROFILE			= "DELETE FROM NMSP.WEB_APPMAILPROF WHERE SYSCD=<#SYSCD#> AND MAILID=<#MAILID#>";
	
	
	
	/**
	 * 메일 수신자 검색 기본 쿼리
	 */
	private static final String _SELECT_MAIL_RECIPIENT_BASE	= "SELECT SYSCD,MAILID,RID,RNAME,RMAIL,TO_CHAR(CREATED,'YYYY-MM-DD HH24:Mi:SS') AS CREATED,CREATEDBY FROM NMSP.WEB_APPMAILRCPT ";
	
	/**
	 * 메일 수신자 리스트 검색
	 */
	public static final String SELECT_MAIL_RECIPIENT_LIST	= _SELECT_MAIL_RECIPIENT_BASE + "WHERE SYSCD=<#SYSCD#> AND MAILID=<#MAILID#>";
	
	/**
	 * 메일 수신자 검색
	 */
	public static final String SELECT_MAIL_RECIPIENT		= _SELECT_MAIL_RECIPIENT_BASE + "WHERE SYSCD=<#SYSCD#> AND MAILID=<#MAILID#> AND RMAIL=<#RMAIL#>";
	
	
	
	/*
	 * 메일 수신자 추가
	 */
	public static final String INSERT_MAIL_RECIPIENT		= ""
			+ " INSERT INTO NMSP.WEB_APPMAILRCPT(SYSCD,MAILID,RID,RNAME,RMAIL,CREATEDBY)"
			+ " VALUES(<#SYSCD#>,<#MAILID#>,<#RID#>,<#RNAME#>,<#RMAIL#>,<#EMPLNO#>)";
	
	/*
	 * 메일 수신자 삭제
	 */
	public static final String DELETE_MAIL_RECIPIENT		= "DELETE FROM NMSP.WEB_APPMAILRCPT WHERE SYSCD=<#SYSCD#> AND MAILID=<#MAILID#> AND RMAIL=<#RMAIL#>";
	
	/*
	 * 메일 수신자 전체 삭제
	 */
	public static final String DELETE_ALL_MAIL_RECIPIENT		= "DELETE FROM NMSP.WEB_APPMAILRCPT WHERE SYSCD=<#SYSCD#> AND MAILID=<#MAILID#>";
	
	
	/*
	 * 메일 히스토리 
	 */
	public static final String SELECT_MAIL_HISTORY		= ""
			+ " SELECT B.MID, "
			+ "        A.SNDEMPLNO, "
			+ "        C.KORNM, "
			+ "        B.SUBJECT, "
			+ "        B.CONTENTS, "
			+ "        A.SNDDATE "
			+ "   FROM NMSP.WEB_APPMAILHIST A, "
			+ "        SAC_NEWS.RTMS_MAILQUEUE B, "
			+ "        NMSP.HREMP C "
			+ "  WHERE A.SYSCD=<#SYSCD#> "
			+ "		   AND A.MAILID=<#MAILID#> "
			+ "        AND B.MID = A.SNDMAILID "
			+ "        AND A.SNDEMPLNO = C.EMPLNO "
			+ " ORDER BY A.SNDDATE DESC ";
	
	public static final String SELECT_MAIL_HISTORY_BY_MID 		= "SELECT SUBJECT, CONTENTS FROM ( " + SELECT_MAIL_HISTORY + " ) WHERE MID = <#MID#>";
	
	/*
	 * 메일 수신 히스토리 
	 */
	public static final String SELECT_MAIL_RCPT_HISTORY		= ""
			+ " SELECT A.RMAIL, "
			+ "        A.RNAME, "
			+ "        A.RID "
			+ "   FROM SAC_NEWS.RTMS_RECIPIENTINFO A "
			+ "  WHERE MID=<#MID#> ";
			
	public static final String SELECT_USER_INFO_BY_EMPLNO = ""
			+ " SELECT A.INTEID,"
			/*
			 * 2022-02-13 이지민 samil.com 도메인 미사용으로 INTEID > EMAIL 변경
			 * */
			+ "        A.EMAIL,"
			+ "        A.EMPLNO,"
			+ " 	   A.KORNM,"
			+ "        A.ENGNM,"
			+ "        A.GRADCD,"
			+ "        (SELECT GRADNM FROM NMSP.HRGRAD WHERE GRADCD = A.GRADCD) GRADNM, "
			+ "		   A.TEAMCD, "
			+ "        (SELECT TEAMNM FROM NMSP.CMTEAMCD WHERE TEAMCD = A.TEAMCD) TEAMNM, "
			+ "		   A.LOSCD, "
			+ "        (SELECT SHRTNM FROM NMSP.CMETCOCD WHERE SYTMDV = 'CM' AND CLASCD = '06' AND ETCOCD=A.LOSCD) LOSNM "
			+ "   FROM NMSP.HREMP A"
			+ "  WHERE A.EMPLNO IN (<#EMPLNO_LIST#>)";
	
	
	
	/*
	 * Mail Profile의 M001 메일 수신자 리스트 가져오기
	 * */
	public static final String SELECT_M001_RCPT_LIST 	= ""
			+ " SELECT B.INTEID,"
			+ "        A.EMPLNO,"
			+ "        B.EMAIL,"
			+ " 	   B.KORNM,"
			+ "        B.ENGNM,"
			+ "        B.GRADCD,"
			+ "        (SELECT GRADNM FROM NMSP.HRGRAD WHERE GRADCD = B.GRADCD) GRADNM, "
			+ "		   B.TEAMCD, "
			+ "        (SELECT TEAMNM FROM NMSP.CMTEAMCD WHERE TEAMCD = B.TEAMCD) TEAMNM, "
			+ "		   B.LOSCD, "
			+ "        (SELECT SHRTNM FROM NMSP.CMETCOCD WHERE SYTMDV = 'CM' AND CLASCD = '06' AND ETCOCD=B.LOSCD) LOSNM "
			+ "   FROM "
			+ "        ("
			+ "          SELECT '1' AS DIV, PTREMPNO AS EMPLNO FROM NMSP.WEB_DCLTBASE WHERE LTTGTCD = '101001' AND (STATUSCD IS NULL OR  STATUSCD = '100602' ) " // EL
			+ "              UNION "
			+ "          SELECT '1' AS DIV, MGREMPNO AS EMPLNO FROM NMSP.WEB_DCLTBASE WHERE LTTGTCD = '101001' AND (STATUSCD IS NULL OR  STATUSCD = '100602' ) " // TM
			+ "              UNION "
			+ "          SELECT '2' AS DIV, EMPNO FROM WEB_DCHQEMP "	// 본부 담당자
			+ "        ) A, "
			+ "        NMSP.HREMP B "
			+ "  WHERE A.EMPLNO = B.EMPLNO AND B.OFFIST='001' AND ((A.DIV = '1' AND B.TEAMCD LIKE 'F%') OR A.DIV = '2')"
			+ " ORDER BY  B.KORNM ASC";
}
