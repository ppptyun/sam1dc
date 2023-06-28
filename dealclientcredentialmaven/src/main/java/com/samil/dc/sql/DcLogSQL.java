package com.samil.dc.sql;

public final class DcLogSQL {
	
	/**
	 * <pre>
	 * ====================================================================================
	 * 일반 로그
	 * 
	 * <#LOGCD#> 로그 유형
	 * <#ALTCD#> 변경 방식
	 * <#FILDNM#> 필드 이름
	 * <#FILDCD#> 필드 코드
	 * <#BEFVAL#> 이전 필드 값
	 * <#ALTVAL#> 변경 필드 값
	 * <#PRJTCD#> 프로젝트 코드
	 * <#BIGO#> 비고
	 * <#ALTEMPNO#> 변경자 사번
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_PUBLIC_LOG = 
			  "	   INSERT INTO WEB_DCFILDLOG ( "
			+ "				   SEQ "
			+ "				 , LOGCD "
			+ "				 , ALTCD "
			+ "				 , FILDNM "
			+ "				 , FILDCD "
			+ "				 , BEFVAL "
			+ "				 , ALTVAL "
			+ "				 , PRJTCD "
			+ "				 , BIGO "
			+ "				 , ALTEMPNO "
			+ "				 , ALTDT "
			+ "				) "
			+ "			VALUES ( "
			+ "				   SEQ_DCFILDLOG.NEXTVAL "
			+ "				 , <#LOGCD#> "
			+ "				 , <#ALTCD#> "
			+ "				 , <#FILDNM#> "
			+ "				 , <#FILDCD#> "
			+ "				 , <#BEFVAL#> "
			+ "				 , <#ALTVAL#> "
			+ "				 , <#PRJTCD#> "
			+ "				 , <#BIGO#> "
			+ "				 , <#ALTEMPNO#> "
			+ "				 , SYSDATE "
			+ "			    ) ";

	/**
	 * <pre>
	 * ====================================================================================
	 * 자문 형태 지정 로그
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * <#LOGCD#> 로그 유형
	 * <#ALTCD#> 변경 방식
	 * <#ALTVAL#> 변경 필드 값
	 * <#ALTEMPNO#> 변경자 사번
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_LEAGUE_CONSULT_TYPE_CHANGE_LOG = 
			  "	   INSERT INTO WEB_DCFILDLOG ( "
			+ "				   SEQ "
			+ "				 , LOGCD "
			+ "				 , ALTCD "
			+ "				 , FILDNM "
			+ "				 , FILDCD "
			+ "				 , BEFVAL "
			+ "				 , ALTVAL "
			+ "				 , PRJTCD "
			+ "				 , BIGO "
			+ "				 , ALTEMPNO "
			+ "				 , ALTDT "
			+ "				) "
			+ "			SELECT SEQ_DCFILDLOG.NEXTVAL "
			+ "				 , <#LOGCD#> "
			+ "				 , <#ALTCD#> "
			+ "				 , '자문형태' "
			+ "				 , 'conscd' "
			+ "				 , LT.CONSCD "
			+ "				 , <#ALTVAL#> "
			+ "				 , <#PRJTCD#> "
			+ "				 , NULL "
			+ "				 , <#ALTEMPNO#> "
			+ "				 , SYSDATE "
			+ "			  FROM WEB_DCLTBASE LT "
			+ "			 WHERE LT.PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League 기업 정보 등록 로그
	 * 
	 * <#LOGCD#> 로그 유형
	 * <#ALTCD#> 변경 방식
	 * <#PRJTCD#> 프로젝트 코드
	 * <#BIGO#> 비고
	 * <#ALTEMPNO#> 변경자 사번
	 * <#ACTCD#> 주체분류코드
	 * <#BIZCD#> 기업분류코드
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_LEAGUE_BIZ_LOG = 
			  "	   INSERT INTO WEB_DCFILDLOG ( "
			+ "				   SEQ "
			+ "				 , LOGCD "
			+ "				 , ALTCD "
			+ "				 , FILDNM "
			+ "				 , FILDCD "
			+ "				 , BEFVAL "
			+ "				 , ALTVAL "
			+ "				 , PRJTCD "
			+ "				 , BIGO "
			+ "				 , ALTEMPNO "
			+ "				 , ALTDT "
			+ "				) "
			+ "			SELECT SEQ_DCFILDLOG.NEXTVAL "
			+ "				 , <#LOGCD#> "
			+ "				 , <#ALTCD#> "
			+ "				 , '' "
			+ "				 , '' "
			+ "				 , '' "
			+ "				 , DECODE( BZ.DEALNM, NULL, BZ.COMPHANNM, BZ.DEALNM ) "
			+ "				 , <#PRJTCD#> "
			+ "				 , <#BIGO#> "
			+ "				 , <#ALTEMPNO#> "
			+ "				 , SYSDATE "
			+ "			  FROM WEB_DCLTBIZ BZ "
			+ "			 WHERE BZ.PRJTCD = <#PRJTCD#> "
			+ "			   AND BZ.ACTCD = <#ACTCD#> "
			+ "			   AND BZ.BIZCD = <#BIZCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League 담당자 확인 로그
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * <#LOGCD#> 로그 유형
	 * <#ALTCD#> 변경 방식
	 * <#ALTVAL#> 변경 필드 값
	 * <#BIGO#> 비고
	 * <#ALTEMPNO#> 변경자 사번
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_LEAGUE_CONSULT_CONFIRM_LOG = 
			  "	   INSERT INTO WEB_DCFILDLOG ( "
			+ "				   SEQ "
			+ "				 , LOGCD "
			+ "				 , ALTCD "
			+ "				 , FILDNM "
			+ "				 , FILDCD "
			+ "				 , BEFVAL "
			+ "				 , ALTVAL "
			+ "				 , PRJTCD "
			+ "				 , BIGO "
			+ "				 , ALTEMPNO "
			+ "				 , ALTDT "
			+ "				) "
			+ "			SELECT SEQ_DCFILDLOG.NEXTVAL "
			+ "				 , <#LOGCD#> "
			+ "				 , <#ALTCD#> "
			+ "				 , '담당자확인여부' "
			+ "				 , 'chrgconfcd' "
			+ "				 , LT.CHRGCONFCD "
			+ "				 , <#ALTVAL#> "
			+ "				 , <#PRJTCD#> "
			+ "				 , <#BIGO#> "
			+ "				 , <#ALTEMPNO#> "
			+ "				 , SYSDATE "
			+ "			  FROM WEB_DCLTBASE LT "
			+ "			 WHERE LT.PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League ET 확인 로그
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * <#LOGCD#> 로그 유형
	 * <#ALTCD#> 변경 방식
	 * <#BIGO#> 비고
	 * <#ALTEMPNO#> 변경자 사번
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_LEAGUE_CONSULT_ET_CONFIRM_LOG = 
			  "	   INSERT INTO WEB_DCFILDLOG ( "
			+ "				   SEQ "
			+ "				 , LOGCD "
			+ "				 , ALTCD "
			+ "				 , FILDNM "
			+ "				 , FILDCD "
			+ "				 , BEFVAL "
			+ "				 , ALTVAL "
			+ "				 , PRJTCD "
			+ "				 , BIGO "
			+ "				 , ALTEMPNO "
			+ "				 , ALTDT "
			+ "				) "
			+ "			SELECT SEQ_DCFILDLOG.NEXTVAL "
			+ "				 , <#LOGCD#> "
			+ "				 , <#ALTCD#> "
			+ "				 , 'ET확인' "
			+ "				 , 'etconfdt' "
			+ "				 , TO_CHAR(LT.ETCONFDT, 'YYYY-MM-DD') "
			+ "				 , TO_CHAR(SYSDATE, 'YYYY-MM-DD') "
			+ "				 , <#PRJTCD#> "
			+ "				 , <#BIGO#> "
			+ "				 , <#ALTEMPNO#> "
			+ "				 , SYSDATE "
			+ "			  FROM WEB_DCLTBASE LT "
			+ "			 WHERE LT.PRJTCD = <#PRJTCD#> ";
	
	/**
	 * <pre>
	 * ====================================================================================
	 * League 프로젝트 대상 변경 로그
	 * 
	 * <#PRJTCD#> 프로젝트 코드
	 * <#LOGCD#> 로그 유형
	 * <#ALTCD#> 변경 방식
	 * <#ALTVAL#> 변경 필드 값
	 * <#ALTEMPNO#> 변경자 사번
	 * ====================================================================================
	 * </pre>
	 */
	public static final String INSERT_LEAGUE_PROJECT_TARGET_LOG = 
			  "	   INSERT INTO WEB_DCFILDLOG ( "
			+ "				   SEQ "
			+ "				 , LOGCD "
			+ "				 , ALTCD "
			+ "				 , FILDNM "
			+ "				 , FILDCD "
			+ "				 , BEFVAL "
			+ "				 , ALTVAL "
			+ "				 , PRJTCD "
			+ "				 , BIGO "
			+ "				 , ALTEMPNO "
			+ "				 , ALTDT "
			+ "				) "
			+ "			SELECT SEQ_DCFILDLOG.NEXTVAL "
			+ "				 , <#LOGCD#> "
			+ "				 , <#ALTCD#> "
			+ "				 , 'LeagueTable대상여부' "
			+ "				 , 'lttgtcd' "
			+ "				 , LT.LTTGTCD "
			+ "				 , <#ALTVAL#> "
			+ "				 , <#PRJTCD#> "
			+ "				 , NULL "
			+ "				 , <#ALTEMPNO#> "
			+ "				 , SYSDATE "
			+ "			  FROM WEB_DCLTBASE LT "
			+ "			 WHERE LT.PRJTCD = <#PRJTCD#> ";
}
