package com.samil.dc.sql;

public class RTMSMailSQL {
	
	// 발송된 메일
	public static final String SELECT_RTMS_MAILQUEUE = "SELECT SUBJECT, CONTENTS FROM SAC_NEWS.RTMS_MAILQUEUE WHERE MID = <#MID#>";
	
	// 발송된 메일의 수신자 리스트
	public static final String SELECT_RTMS_RECIPIENTINFO = ""
			+ " SELECT RMAIL,"
			+ "        B.EMPLNO,"
			+ "        B.KORNM,"
			+ "        (SELECT GRADNM "
			+ "          FROM NMSP.HRGRAD "
			+ "         WHERE GRADCD = B.GRADCD "
			+ "        ) GRADNM, "
			+ "        TEAMCD, "
			+ "        (SELECT TEAMNM "
			+ "          FROM NMSP.CMTEAMCD "
			+ "         WHERE TEAMCD = B.TEAMCD "
			+ "        ) TEAMNM "
			+ "   FROM SAC_NEWS.RTMS_RECIPIENTINFO A, "
			+ "        NMSP.HREMP B "
			+ "  WHERE MID = <#MID#> "
			+ "        AND B.EMPLNO(+) = A.RID";
	
	
	
	/**
	 * 메일 프로파일에서 데이터 가져오기
	 */
	public static final String INSERT_MAIL_FROM_PROF = ""
	+ " INSERT INTO SAC_NEWS.RTMS_MAILQUEUE(MID, SUBID, TID, SID, SNAME, SMAIL, RPOS, CTNPOS, SUBJECT, CONTENTS, ISSECURE, SECURETEMPLATE)"
	+ " SELECT <#MID#>, 0, 0, <#SID#>, <#SNAME#>, <#SMAIL#>, 0, 0, SUBJECT, CONTENTS||<#ADDCONTENTS#>, 0, NULL FROM NMSP.WEB_APPMAILPROF WHERE SYSCD=<#SYSCD#> AND MAILID=<#MAILID#>";
	
	
	/**
	 * 메일 정보 입력
	 */
	public static final String INSERT_MAIL = ""
			+ " INSERT INTO SAC_NEWS.RTMS_MAILQUEUE(MID, SUBID, TID, SID, SNAME, SMAIL, RPOS, CTNPOS, SUBJECT, CONTENTS, ISSECURE, SECURETEMPLATE)"
			+ " VALUES(<#MID#>, 0, 0, <#SID#>, <#SNAME#>, <#SMAIL#>, 0, 0, <#SUBJECT#>, <#CONTENTS#>, 0, NULL)" ;
	
	/**
	 * 메일 수신인 정보 입력
	 */
	public static final String INSERT_RCPT = ""
			+ "INSERT INTO SAC_NEWS.RTMS_RECIPIENTINFO(MID, SUBID, TID, RID, RNAME, RMAIL) "
			+ "values(<#MID#>, 0, 0,<#RID#>,<#RNAME#>,<#RMAIL#>)";
	
	
	/**
	 * 메일 프로파일 가져오기
	 */
	private static final String _SELECT_MAIL_PROFILE_BASE	= "SELECT SYSCD,MAILID,SUBJECT,CONTENTS,DES,USEYN,TO_CHAR(CREATED,'YYYY-MM-DD HH24:Mi:SS') AS CREATED,CREATEDBY,TO_CHAR(MODIFIED,'YYYY-MM-DD HH24:Mi:SS') AS MODIFIED,MODIFIEDBY FROM NMSP.WEB_APPMAILPROF ";
	public static final String SELECT_MAIL_PROFILE 			= _SELECT_MAIL_PROFILE_BASE + "WHERE SYSCD=<#SYSCD#> AND MAILID=<#MAILID#> AND USEYN=<#USEYN#>";
	
	/**
	 * 메일 프로파일에 등록된 수신자 가져오기
	 */
	private static final String _SELECT_MAIL_RECIPIENT_BASE	= "SELECT SYSCD,MAILID,RID,RNAME,RMAIL,TO_CHAR(CREATED,'YYYY-MM-DD HH24:Mi:SS') AS CREATED,CREATEDBY FROM NMSP.WEB_APPMAILRCPT ";
	public static final String SELECT_MAIL_RECIPIENT_LIST	= _SELECT_MAIL_RECIPIENT_BASE + "WHERE SYSCD=<#SYSCD#> AND MAILID=<#MAILID#>";
	
	
	/*
	 * 메일 발송 히스토리 입력
	 */
	public static final String INSERT_MAIL_HISTORY = ""
			+ "INSERT INTO NMSP.WEB_APPMAILHIST(SYSCD, MAILID, SNDMAILID, SNDEMPLNO, STATUS) VALUES(<#SYSCD#>, <#MAILID#>, <#SNDMAILID#>, <#SNDEMPLNO#>, <#STATUS#>)";
	
	/*
	 * 메일 발송 히스토리 가져오기*/
	public static final String SELECT_MAIL_HISTORY = ""
			+ " SELECT A.SNDDATE"
			+ "		   A.MAILID, "
			+ "        A.SNDMAILID, "
			+ "        A.SNDEMPLNO, "
			+ "		   B.INTEID, "
			+ "        B.KORNM, "
			+ "        (SELECT GRADNM "
			+ "          FROM NMSP.HRGRAD "
			+ "         WHERE GRADCD = B.GRADCD"
			+ "        ) GRADNM, "
			+ "        TEAMCD, "
			+ "        (SELECT TEAMNM "
			+ "          FROM NMSP.CMTEAMCD "
			+ "         WHERE TEAMCD = B.TEAMCD"
			+ "        ) TEAMNM "
			+ "   FROM NMSP.WEB_APPMAILHIST A, "
			+ "        NMSP.HREMP B "
			+ "  WHERE A.SNDEMPLNO = B.EMPLNO";
	
	
	
}
