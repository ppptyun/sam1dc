package com.samil.dc.util;

public class Constants {

	public final static String YES = "Y";
	public final static String NO = "N";
	
	/** 롤(역할) */
	public final static class Role {
		/** Deal Client Credential 관리자 */
		public final static String R01 = "R01";
		/** Deal Client Credential 조회권한자 */
		public final static String R02 = "R02";
		/** League Table 관리자 */
		public final static String R03 = "R03";
		/** Engagement Team EL, TM */
		public final static String R04 = "R04";
		/** 본부 담당자 */
		public final static String R05 = "R05";
		/** 본부장/Leader 등 상위 직급 */
		public final static String R06 = "R06";
		/** IT */
		public final static String IT = "R98";
		/** System */
		public final static String SYSTEM = "R99";
	}
	
	/** 자문 형태 분류 */
	public final static class Consult {
		/** 미분류 */
		public final static String NONE = "100301";
		/** 인수/매각 자문 */
		public final static String BUY = "100302";
		/** 합병 자문 */
		public final static String MNA = "100303";
		/** 부동산 자문 */
		public final static String REAL = "100304";
	}
	
	/** League Table 주체 분류 */
	public final static class Actor {
		/** 인수/매각 자문-대상 (Target) */
		public final static String BUY_TARGET = "100401";
		/** 인수/매각 자문-매각자 (Seller) */
		public final static String BUY_SELLER = "100402";
		/** 인수/매각 자문-인수자 (Buyer) */
		public final static String BUY_BUYER = "100403";
		/** 합병 자문-합병(존속/신설) 법인 */
		public final static String MNA_MERGING = "100404";
		/** 합병 자문-피합병(소멸) 법인 */
		public final static String MNA_MERGED = "100405";
		/** 부동산 자문-대상 (Target) */
		public final static String REAL_TARGET = "100408";
		/** 부동산 자문-매각자 (Seller) */
		public final static String REAL_SELLER = "100406";
		/** 부동산 자문-인수자 (Buyer) */
		public final static String REAL_BUYER = "100407";
	}
	
	/** League Table 기업 분류 */
	public final static class LtBiz {
		/** 주 기업 */
		public final static String MAIN = "100501";
		/** 재무/전략자문 기업 */
		public final static String FINANCE = "100502";
		/** 회계자문 기업 */
		public final static String AUDIT = "100503";
		/** 법률자문 기업 */
		public final static String LAW = "100504";
	}
	
	/** League Table 기업 대상 분류 */
	public final static class BizSrc {
		/** 삼일(Samil) */
		public final static String SAMIL = "102101";
		/** 기업정보 Master(Kisline) */
		public final static String KISLINE = "102102";
		/** 수기입력(Handwriting) */
		public final static String MANUAL = "102103";
		/** 자문기업 */
		public final static String CONSULT = "102104";
	}
	
	/** 삼일회계법인 정보 */
	public final static class Samil {
		/** 삼일회계법인 회사 코드(Kisline코드) */
		public final static String COMP_CD = "206652";
		/** 삼일회계법인 한글 이름 */
		public final static String HAN_NM = "삼일회계법인";
		/** 삼일회계법인 영문 이름 */
		public final static String ENG_NM = "Samil PricewaterhouseCoopers";
		/** 삼일회계법인 국가 코드 */
		public final static String NAT_CD = "KR";
		/** 삼일회계법인 산업 분류 코드 */
		public final static String INDU_CD = "74120";
	}
	
	/** Credential 기업 분류 */
	public final static class CredBiz {
		/** 클라이언트 */
		public final static String CLIENT = "102001";
		/** 대상 */
		public final static String TARGET = "102002";
		/** BRS 매수처 */
		public final static String BRSBUYER = "102003";
		/** RCF */
		public final static String RCF = "102004";
		/** 이해관계자 */
		public final static String INTEREST = "102005";
	}
	
	/** BRS 매각 채권 구분 */
	public final static class BondType {
		/** 담보 */
		public final static String SECURE = "102701";
		/** 무담보 */
		public final static String NONE_SECURE = "102702";
	}
	
	/** 담당자 확인 여부 */
	public final static class ChargeConfirm {
		public final static String YES = "100801";
		public final static String NO = "100802";
	}
	
	/** Credential 대상 여부*/
	public final static class CredentialTargetYN {
		public final static String YES 	= "103201";
		public final static String NO	= "103202";
	}
	
	/** League Table Status */
	public final static class LtStatus {
		public final static String SUCCESS = "100601";
		public final static String PROCESSING = "100602";
		public final static String DROP = "100603";
	}
	
	
	/** 변경 로그 유형 */
	public final static class LogType {
		/** Credential */
		public final static String CREDENTIAL = "102801";
		/** League Table */
		public final static String LEAGUE_TABLE = "102802";
		/** 자문 형태 지정 */
		public final static String SETUP_CONSULT_TYPE = "102803";
		/** 대상 프로젝트 관리 */
		public final static String SETUP_PROJECT_TARGET = "102804";
		/** PDT 관리 */
		public final static String PRODUCT_MANAGE = "102805";
		/** 본부 담당자 관리 */
		public final static String HQ_EMP_MANAGE = "102806";
		/** League Table 편집 관리 */
		public final static String LT_EDIT_MANAGE = "102807";
		/** 메일링 관리 */
		public final static String MAIL_MANAGE = "102808";
		/** PE 관리 */
		public final static String PE_MANAGE = "102809";
		/** 엑셀 */
		public final static String IMPORT_EXCEL = "102810";
		/** CONSULT 관리 */
		public final static String CONSULT_MANAGE = "102811";
	}
	
	/** 변경 로그 방식 */
	public final static class LogMethod {
		/** 신규 */
		public final static String INSERT = "102901";
		/** 수정 */
		public final static String UPDATE = "102902";
		/** 삭제 */
		public final static String DELETE = "102903";
	}
	
	/** League Table 권한 레벨 */
	public final static class LeagueRoleLevel {
		public final static int ROLE_MANAGER = 1;
		public final static int ROLE_HQ_ET = 2;
		public final static int ROLE_HQ = 3;
		public final static int ROLE_ET = 4;
		public final static int ROLE_ETC = 9;
	}
	
	/** 에러 코드 */
	public final static class ErrorCode {
		/** 파라미터를 찾을 수 없습니다. */
		public final static String NOT_FOUND_PARAMETER = "10000";
		/** 파라미터의 값이 비어 있습니다. */
		public final static String EMPTY_PARAMETER = "10001";
		/** 요청한 서비스를 찾을 수 없습니다. */
		public final static String NOT_FOUND_SERVICE = "10002";
		/** 서비스를 초기화하지 못했습니다. */
		public final static String NOT_INIT_SERVICE = "10003";
		/** 프로젝트를 찾을 수 없습니다. */
		public final static String NOT_FOUND_PROJECT = "10004";
		/** 필드항목을 찾을 수 없습니다. */
		public final static String NOT_FOUND_FIELD = "10005";
		/** 파라미터 타입이 유효하지 않습니다. */
		public final static String NOT_VALID_PARAMETER_TYPE = "10006";
		/** 파라미터 디코딩을 실패 하였습니다. */
		public final static String FAIL_DECODE_PARAMETER = "10007";
		/** 파라미터 값이 유효하지 않습니다.*/
		public final static String NOT_VALID_PARAMETER_VALUE = "10008";
		/** 해당 메일의 수신자 리스트는 약 10분 후에 확인 가능합니다. */
		public final static String FAIL_MAIL_RCPT_DECRYPTION = "20001";
		/** 파라미터 값이 동일합니다. */
		public final static String EQUAL_PARAMETER = "10008";
		/** 등록 쿼리 수행 실패하였습니다. */
		public final static String FAIL_SQL_INSERT = "50001";
		/** 수정 쿼리 수행 실패하였습니다. */
		public final static String FAIL_SQL_UPDATE = "50002";
		/** 삭제 쿼리 수행 실패하였습니다. */
		public final static String FAIL_SQL_DELETE = "50003";
		/** 사용자 롤(역할)이 필요합니다. */
		public final static String NOT_FOUND_ROLE = "99997";	
		/** 사용자를 찾을 수 없습니다. */
		public final static String NOT_FOUND_USER = "99998";
		/** 서버 내부 오류입니다. */
		public final static String INTERNAL = "99999";
		
		/** 엑셀 임포트 처리 중 오류가 발생했습니다. */
		public final static String EXCEL_PROCESS = "88888";
	}
	
	/** 에러 메시지 */
	public final static class ErrorMessage {
		/** 파라미터를 찾을 수 없습니다. */
		public final static String NOT_FOUND_PARAMETER = "파라미터를 찾을 수 없습니다.";
		/** 파라미터의 값이 비어 있습니다. */
		public final static String EMPTY_PARAMETER = "파라미터의 값이 비어 있습니다.";
		/** 요청한 서비스를 찾을 수 없습니다. */
		public final static String NOT_FOUND_SERVICE = "요청한 서비스를 찾을 수 없습니다.";
		/** 서비스를 초기화하지 못했습니다. */
		public final static String NOT_INIT_SERVICE = "서비스를 초기화하지 못했습니다.";
		/** 프로젝트를 찾을 수 없습니다. */
		public final static String NOT_FOUND_PROJECT = "프로젝트를 찾을 수 없습니다.";
		/** 필드항목을 찾을 수 없습니다. */
		public final static String NOT_FOUND_FIELD = "필드항목을 찾을 수 없습니다.";
		/** 파라미터 타입이 유효하지 않습니다. */
		public final static String NOT_VALID_PARAMETER_TYPE = "파라미터 타입이 유효하지 않습니다.";
		/** 파라미터 디코딩을 실패 하였습니다. */
		public final static String FAIL_DECODE_PARAMETER = "파라미터 디코딩을 실패 하였습니다.";
		/** 파라미터 값이 유효하지 않습니다.*/
		public final static String NOT_VALID_PARAMETER_VALUE = "파라미터 값이 유효하지 않습니다.";
		/** 파라미터 값이 동일합니다. */
		public final static String EQUAL_PARAMETER = "파라미터 값이 동일합니다.";
		/** 해당 메일의 수신자 리스트는 약 10분 후에 확인 가능합니다. */
		public final static String FAIL_MAIL_RCPT_DECRYPTION = "해당 메일의 수신자 리스트는 약 10분 후에 확인 가능합니다.";
		/** 등록 쿼리 수행 실패하였습니다. */
		public final static String FAIL_SQL_INSERT = "등록 쿼리 수행 실패하였습니다.";
		/** 수정 쿼리 수행 실패하였습니다. */
		public final static String FAIL_SQL_UPDATE = "수정 쿼리 수행 실패하였습니다.";
		/** 삭제 쿼리 수행 실패하였습니다. */
		public final static String FAIL_SQL_DELETE = "삭제 쿼리 수행 실패하였습니다.";
		/** 사용자 롤(역할)이 필요합니다. */
		public final static String NOT_FOUND_ROLE = "사용자 롤(역할)이 필요합니다.";
		/** 사용자를 찾을 수 없습니다. */
		public final static String NOT_FOUND_USER = "사용자를 찾을 수 없습니다.";
		/** 서버 내부 오류입니다. */
		public final static String INTERNAL = "서버 내부 오류입니다.";
		
		/** 엑셀 임포트 처리 중 오류가 발생했습니다. */
		public final static String EXCEL_PROCESS = "엑셀 임포트 처리 중 오류가 발생했습니다.";
	}
	
	/** Credential 검색조건 파라미터 */
	public final static class CredentialParam{
		public final static String PARAM_CTGCD = "ctgcd";
		public final static String PARAM_PDTCD = "pdtcd";
		public final static String PARAM_FRDT = "frdt";
		public final static String PARAM_TODT = "todt";
		public final static String PARAM_PRJTNM = "prjtnm";
		public final static String PARAM_CHKPE = "chkpe";
		public final static String PARAM_CHKCROSS = "chkcross";
		
		public final static String PARAM_SRCHTYPCOMP = "srchtypcomp";
		public final static String PARAM_CLICOMNM = "clicomnm";
		public final static String PARAM_CLIINDCD = "cliindcd";
		public final static String PARAM_CLIGINDCD = "cligindcd";
		public final static String PARAM_CLIGINDNM = "cligindnm";
		public final static String PARAM_CLINATCD = "clinatcd";
		public final static String PARAM_CLINATNM = "clinatnm";
		
		public final static String PARAM_TARCOMNM = "tarcomnm";
		public final static String PARAM_TARINDCD = "tarindcd";
		public final static String PARAM_TARNATCD = "tarnatcd";
		public final static String PARAM_TARGINDCD = "targindcd";
		public final static String PARAM_TARGINDNM = "targindnm";
		public final static String PARAM_TARNATNM = "tarnatnm";
		
		public final static String PARAM_CONCOMNM = "concomnm";
		public final static String PARAM_CONINDCD = "conindcd";
		public final static String PARAM_CONGINDCD = "congindcd";
		public final static String PARAM_CONGINDNM = "congindnm";
		public final static String PARAM_CONNATCD = "connatcd";
		public final static String PARAM_CONNATNM = "connatnm";
		
		public final static String PARAM_AMTRANGE = "amtrange";
		
		public final static String PARAM_BRSSECURE = "brssecurity";
		public final static String PARAM_BRSUNSECURE = "brsunsecured";
		public final static String PARAM_RCFLOACTION = "rcflocation";
		public final static String PARAM_RCFTYPE = "rcftype";
		public final static String PARAM_RCFDETAILTYPE = "rcfdetailtype";
		
		public final static String PARAM_CREDTGTCD = "credtgtcd";
	}
}
