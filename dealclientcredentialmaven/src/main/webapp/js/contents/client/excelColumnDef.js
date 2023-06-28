var DOWNLOAD_COLUMN_DEF = {};
DOWNLOAD_COLUMN_DEF.CREDENTIAL = [
	 {name:"CTGNM_ORIG",	dspname:["Product(최초)", "Category"],	align:"left", width:"150", type:"str"}
    ,{name:"PDTCDNM_ORIG",	dspname:["#cspan", "Code/Name"],	align:"left", width:"230", type:"str"}
    ,{name:"CTGNM",			dspname:["Product(변경)", "Category"],	align:"left", width:"150", type:"str"}
    ,{name:"PDTCDNM",		dspname:["#cspan", "Code/Name"],	align:"left", width:"230", type:"str"}
	,{name:"PRJTCD", 		dspname:["Project", "Code"], 		align:"left", width:"85", type:"str"}
	,{name:"PRJTNM", 		dspname:["#cspan", "Name"], 		align:"left", width:"230", type:"str"}
	,{name:"PRJTDIVNM", 	dspname:["#cspan", "주관/지원"],		align:"left", width:"70", type:"str"}
	,{name:"HQCDNM",		dspname:["담당", "본부"], 			align:"left", width:"180", type:"str"}
	,{name:"PTREMPNO", 		dspname:["#cspan", "PTR 사번"], 		align:"left", width:"70", type:"str"}
	,{name:"PTREMPNM", 		dspname:["#cspan", "PTR 이름"], 		align:"left", width:"70", type:"str"}
	,{name:"MGREMPNO", 		dspname:["#cspan", "MGR 사번"], 		align:"left", width:"70", type:"str"}
	,{name:"MGREMPNM", 		dspname:["#cspan", "MGR 이름"], 		align:"left", width:"70", type:"str"}
	,{name:"CLIENTNM", 		dspname:["Client", "Name"], 		align:"left", width:"180", type:"str"}
	,{name:"CLIENTINDUNM", 	dspname:["#cspan", "Industry"], 	align:"left", width:"230", type:"str"}
	,{name:"CLIENTNATNM", 	dspname:["#cspan", "Country"], 		align:"left", width:"70", type:"str"}
	,{name:"CISNO", 		dspname:["계약번호", "#rspan"], 		align:"left", width:"100", type:"str"}
	,{name:"CHAMTW", 		dspname:["계약금액", "#rspan"], 		align:"right", width:"100", type:"number", format:"#,##0"}
	,{name:"BILLAMTW", 		dspname:["청구금액", "#rspan"], 		align:"right", width:"100", type:"number", format:"#,##0"}
	,{name:"CONTDT", 		dspname:["용역기간", "용역개시일"], 	align:"left", width:"100"}
	,{name:"TERMIDT", 		dspname:["#cspan", "용역종료일"], 	align:"left", width:"100"}
	,{name:"TCOMPHANNM1", 	dspname:["Target", "Name"], 		align:"left", width:"180", type:"str"}
	,{name:"TINDUNM1", 		dspname:["#cspan", "Industry"], 	align:"left", width:"230", type:"str"}
	,{name:"TNATNM1", 		dspname:["#cspan", "Country"], 		align:"left", width:"70", type:"str"}
	,{name:"TCOMPHANNM2", 	dspname:["#cspan", "Name"], 		align:"left", width:"180", type:"str"}
	,{name:"TINDUNM2", 		dspname:["#cspan", "Industry"], 	align:"left", width:"230", type:"str"}
	,{name:"TNATNM2", 		dspname:["#cspan", "Country"], 		align:"left", width:"70", type:"str"}
	,{name:"TCOMPHANNM3", 	dspname:["#cspan", "Name"], 		align:"left", width:"180", type:"str"}
	,{name:"TINDUNM3", 		dspname:["#cspan", "Industry"], 	align:"left", width:"230", type:"str"}
	,{name:"TNATNM3", 		dspname:["#cspan", "Country"], 		align:"left", width:"70", type:"str"}
	,{name:"TRGTETC", 		dspname:["#cspan", "비고"], 			align:"left", width:"230", type:"str"}
	,{name:"INTRCOMPHANNM", dspname:["이해관계자", "Name"], 		align:"left", width:"180", type:"str"}
	,{name:"INTRINDUNM", 	dspname:["#cspan", "Industry"], 	align:"left", width:"230", type:"str"}
	,{name:"INTRNATNM", 	dspname:["#cspan", "Country"], 		align:"left", width:"70", type:"str"}
	,{name:"PRJTDESC", 		dspname:["상세업무내용", "#rspan"], 	align:"left", width:"230", type:"str"}
	,{name:"CONFNM", 		dspname:["Transaction (Source: League Table)", "Confidential"], align:"left", width:"70", type:"str"}
	,{name:"CONSNM", 		dspname:["#cspan", "자문형태"], 		align:"left", width:"100", type:"str"}
	,{name:"DEALNM1", 		dspname:["#cspan", "거래형태"], 		align:"left", width:"70", type:"str"}
	,{name:"DEALNM2", 		dspname:["#cspan", "#cspan"], 		align:"left", width:"70", type:"str"}
	,{name:"DEALNM3", 		dspname:["#cspan", "#cspan"], 		align:"left", width:"70", type:"str"}
//	,{name:"DEALNM4", 		dspname:["#cspan", "#cspan"], 		align:"left", width:"70", type:"str"}
	,{name:"DEALNM", 		dspname:["#cspan", "지분율"], 		align:"left", width:"230", type:"str"}
	,{name:"AMT", 			dspname:["#cspan", "거래규모"], 		align:"right", width:"100", type:"number", format:"#,##0"}
	,{name:"ACTOR1", 		dspname:["#cspan", "매도인"], 		align:"left", width:"100", type:"str"}
	,{name:"ACTOR2", 		dspname:["#cspan", "매수인"], 		align:"left", width:"100", type:"str"}
	,{name:"STATUSNM", 		dspname:["#cspan", "STATUS"], 		align:"left", width:"100", type:"str"}
	,{name:"SECURE", 		dspname:["BRS", "담보채권"], 			align:"left", width:"100", type:"str"}
	,{name:"UNSECURE", 		dspname:["#cspan", "무담보채권"], 	align:"left", width:"100", type:"str"}
	,{name:"BRSSALENM", 	dspname:["#cspan", "매각방식"], 		align:"left", width:"100", type:"str"}
	,{name:"BRSOPB", 		dspname:["#cspan", "OPB(단위: 백만원)"], 			align:"right", width:"120", type:"number", format:"#,##0"}
	,{name:"BRSBUYERNM", 	dspname:["#cspan", "매수처"], 		align:"left", width:"100", type:"str"}
	,{name:"RCFADDR", 		dspname:["RE", "위치"], 			align:"left", width:"100", type:"str"}
	,{name:"RCFTYPENM", 	dspname:["#cspan", "구분"], 			align:"left", width:"100", type:"str"}
	,{name:"RCFTYPEDTNM", 	dspname:["#cspan", "구분 상세"], 		align:"left", width:"100", type:"str"}
	,{name:"RCFTYPEETC", 	dspname:["#cspan", "구분 기타"], 		align:"left", width:"100", type:"str"}
	,{name:"RCFLAND", 		dspname:["#cspan", "토지면적"], 		align:"right", width:"100", type:"number", format:"#,##0"}
	,{name:"RCFAREA", 		dspname:["#cspan", "연면적"], 		align:"right", width:"100", type:"number", format:"#,##0"}
];

DOWNLOAD_COLUMN_DEF.BUYLEAGUE = [
     {name:"CONFNM", 			dspname:["Confidential", "#rspan", "#rspan"], align:"left", width:"85", type:"str"}
  	,{name:"YEARLY", 			dspname:["대상년도", "#rspan", "#rspan"], align:"left", width:"70", type:"str"}
  	,{name:"UPDDT", 			dspname:["수정일", "#rspan", "#rspan"], align:"left", width:"130"}
  	,{name:"PDTCDNM",			dspname:["Product", "Code/Name", "#rspan"],	align:"left", width:"230"}
  	,{name:"PRJTCD", 			dspname:["Project 정보", "Project", "Code"], align:"left", width:"85"}
  	,{name:"PRJTNM", 			dspname:["#cspan", "#cspan", "Name"], align:"left", width:"230"}
  	,{name:"HQNM", 				dspname:["#cspan", "담당", "본부"], align:"left", width:"180"}
  	,{name:"PTREMPNM", 			dspname:["#cspan", "#cspan", "PTR"], align:"left", width:"70"}
  	,{name:"MGREMPNM", 			dspname:["#cspan", "#cspan", "MGR"], align:"left", width:"70"}
  	,{name:"CLIENTNM", 			dspname:["#cspan", "Client", "Name"], 		align:"left", width:"180"}
  	,{name:"CLIENTINDUNM", 		dspname:["#cspan", "#cspan", "Industry"], 	align:"left", width:"230"}
  	,{name:"STATUSNM",			dspname:["Status", "#rspan", "#rspan"], align:"left", width:"70"}
  	,{name:"BUYOUTTYPE", 		dspname:["Buyout/Non-Buyout", "#rspan", "#rspan"], 	align:"left", width:"130"}
  	,{name:"TCOMPHANNM1",		dspname:["Target", "대상기업", "기업명"], align:"left", width:"180"}
 	,{name:"TINDUNM1", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"TNATNM1", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"TDEALNM1", 			dspname:["#cspan", "#cspan", "거래물"], align:"left", width:"180"}
 	,{name:"TCOMPHANNM2", 		dspname:["#cspan", "#cspan", "기업명"], align:"left", width:"180"}
 	,{name:"TINDUNM2", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"TNATNM2", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"TDEALNM2", 			dspname:["#cspan", "#cspan", "거래물"], align:"left", width:"180"}
 	,{name:"TCOMPHANNM3", 		dspname:["#cspan", "#cspan", "기업명"], align:"left", width:"180"}
 	,{name:"TINDUNM3", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"TNATNM3", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"TDEALNM3", 			dspname:["#cspan", "#cspan", "거래물"], align:"left", width:"180"}
 	,{name:"TRGTETC", 			dspname:["#cspan", "#cspan", "비고"], align:"left", width:"230"}
 	,{name:"SCOMPHANNM1", 		dspname:["Seller", "대상기업", "Name"], align:"left", width:"180"}
 	,{name:"SINDUNM1", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"SNATNM1", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"SCOMPHANNM2", 		dspname:["#cspan", "#cspan", "Name"], align:"left", width:"180"}
 	,{name:"SINDUNM2", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"SNATNM2", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"SCOMPHANNM3", 		dspname:["#cspan", "#cspan", "Name"], align:"left", width:"180"}
 	,{name:"SINDUNM3", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"SNATNM3", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"SFINCOMPHANNM1",	dspname:["#cspan", "재무/전략자문", "자문사"], align:"left", width:"180"}
 	,{name:"SFININCHARGE1", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"SFINCOMPHANNM2",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"SFININCHARGE2", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"SFINCOMPHANNM3",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"SFININCHARGE3", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"SAUDCOMPHANNM1",	dspname:["#cspan", "회계자문", "자문사"], align:"left", width:"180"}
 	,{name:"SAUDINCHARGE1", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"SAUDCOMPHANNM2",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"SAUDINCHARGE2", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"SAUDCOMPHANNM3",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"SAUDINCHARGE3", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"SLAWCOMPHANNM",		dspname:["#cspan", "법률자문", "자문사"], align:"left", width:"180"}
 	,{name:"SLAWINCHARGE", 		dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"BCOMPHANNM1", 		dspname:["Buyer", "대상기업", "Name"], align:"left", width:"180"}
 	,{name:"BINDUNM1", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"BNATNM1", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"BCOMPHANNM2", 		dspname:["#cspan", "#cspan", "Name"], align:"left", width:"180"}
 	,{name:"BINDUNM2", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"BNATNM2", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"BCOMPHANNM3", 		dspname:["#cspan", "#cspan", "Name"], align:"left", width:"180"}
 	,{name:"BINDUNM3", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"BNATNM3", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"BFINCOMPHANNM1",	dspname:["#cspan", "재무/전략자문", "자문사"], align:"left", width:"180"}
 	,{name:"BFININCHARGE1", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"BFINCOMPHANNM2",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"BFININCHARGE2", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"BFINCOMPHANNM3",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"BFININCHARGE3", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"BAUDCOMPHANNM1",	dspname:["#cspan", "회계자문", "자문사"], align:"left", width:"180"}
 	,{name:"BAUDINCHARGE1", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"BAUDCOMPHANNM2",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"BAUDINCHARGE2", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"BAUDCOMPHANNM3",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"BAUDINCHARGE3", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"BLAWCOMPHANNM",		dspname:["#cspan", "법률자문", "자문사"], align:"left", width:"180"}
 	,{name:"BLAWINCHARGE", 		dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"CONSDT",			dspname:["일자", "자문계약", "#rspan"], align:"left", width:"100"}
 	,{name:"MOUDT",				dspname:["#cspan", "MOU체결", "#rspan"], align:"left", width:"100"}
 	,{name:"SPADT",				dspname:["#cspan", "매매계약(SPA)", "#rspan"], align:"left", width:"100"}
 	,{name:"CLOSDT",			dspname:["#cspan", "잔금납입일(Closing)", "#rspan"],align:"left", width:"100"}
 	,{name:"CURRCD",			dspname:["금액 및 성격", "통화", "#rspan"], align:"left", width:"100", type:"str"}
 	,{name:"AMT",				dspname:["#cspan", "금액", "#rspan"], align:"right", width:"100", type:"number", format:"#,##0"}
 	,{name:"RATE",				dspname:["#cspan", "환율(1통화당 원화)", "#rspan"], align:"right", width:"100", type:"number", format:"#,##0"}
 	,{name:"RATEAMT",			dspname:["#cspan", "원화 환산금액", "#rspan"], align:"right", width:"100", type:"number", format:"#,##0"}
 	,{name:"DEALNM1",			dspname:["#cspan", "거래형태 및 대상", "1"], align:"left", width:"100"}
 	,{name:"DEALNM2",			dspname:["#cspan", "#cspan", "2"], align:"left", width:"100"}
 	,{name:"DEALNM3",			dspname:["#cspan", "#cspan", "3"], align:"left", width:"100"}
// 	,{name:"DEALNM4",			dspname:["#cspan", "#cspan", "4"], align:"left", width:"100"}
 	,{name:"BORDDEALNM",		dspname:["#cspan", "Cross-Border", "#rspan"], align:"left", width:"100"}
];


DOWNLOAD_COLUMN_DEF.MNALEAGUE = [
	 {name:"CONFNM", 			dspname:["Confidential", "#rspan", "#rspan"], align:"left", width:"85"}
	,{name:"YEARLY", 			dspname:["대상년도", "#rspan", "#rspan"], align:"left", width:"70"}
	,{name:"UPDDT", 			dspname:["수정일", "#rspan", "#rspan"], align:"left", width:"130"}
	,{name:"PDTCDNM",			dspname:["Product", "Code/Name", "#rspan"],	align:"left", width:"230"}
	,{name:"PRJTCD", 			dspname:["Project 정보", "Project", "Code"], align:"left", width:"85"}
	,{name:"PRJTNM", 			dspname:["#cspan", "#cspan", "Name"], align:"left", width:"230"}
	,{name:"HQNM", 				dspname:["#cspan", "담당", "본부"], align:"left", width:"180"}
	,{name:"PTREMPNM", 			dspname:["#cspan", "#cspan", "PTR"], align:"left", width:"70"}
	,{name:"MGREMPNM", 			dspname:["#cspan", "#cspan", "MGR"], align:"left", width:"70"}
	,{name:"CLIENTNM", 			dspname:["#cspan", "Client", "Name"], 		align:"left", width:"180"}
	,{name:"CLIENTINDUNM", 		dspname:["#cspan", "#cspan", "Industry"], 	align:"left", width:"230"}
	,{name:"STATUSNM",			dspname:["Status", "#rspan", "#rspan"], align:"left", width:"70"}
	,{name:"SCOMPHANNM1",		dspname:["합병(존속/신설) 법인", "대상기업", "기업명"], align:"left", width:"180"}
	,{name:"SINDUNM1", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
	,{name:"SNATNM1", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
	,{name:"SCOMPHANNM2", 		dspname:["#cspan", "#cspan", "기업명"], align:"left", width:"180"}
	,{name:"SINDUNM2", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
	,{name:"SNATNM2", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
	,{name:"SCOMPHANNM3", 		dspname:["#cspan", "#cspan", "기업명"], align:"left", width:"180"}
	,{name:"SINDUNM3", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
	,{name:"SNATNM3", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
	,{name:"SFINCOMPHANNM1",	dspname:["#cspan", "재무/전략자문", "자문사"], align:"left", width:"180"}
	,{name:"SFININCHARGE1", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
	,{name:"SFINCOMPHANNM2",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
	,{name:"SFININCHARGE2", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
	,{name:"SFINCOMPHANNM3",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
	,{name:"SFININCHARGE3", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
	,{name:"SAUDCOMPHANNM1",	dspname:["#cspan", "회계자문", "자문사"], align:"left", width:"180"}
	,{name:"SAUDINCHARGE1", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
	,{name:"SAUDCOMPHANNM2",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
	,{name:"SAUDINCHARGE2", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
	,{name:"SAUDCOMPHANNM3",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
	,{name:"SAUDINCHARGE3", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
	,{name:"SLAWCOMPHANNM",		dspname:["#cspan", "법률자문", "자문사"], align:"left", width:"180"}
	,{name:"SLAWINCHARGE", 		dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
	,{name:"BCOMPHANNM1", 		dspname:["피합병(소멸) 법인", "대상기업", "Name"], align:"left", width:"180"}
	,{name:"BINDUNM1", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
	,{name:"BNATNM1", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
	,{name:"BCOMPHANNM2", 		dspname:["#cspan", "#cspan", "Name"], align:"left", width:"180"}
	,{name:"BINDUNM2", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
	,{name:"BNATNM2", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
	,{name:"BCOMPHANNM3", 		dspname:["#cspan", "#cspan", "Name"], align:"left", width:"180"}
	,{name:"BINDUNM3", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
	,{name:"BNATNM3", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
	,{name:"BFINCOMPHANNM1",	dspname:["#cspan", "재무/전략자문", "자문사"], align:"left", width:"180"}
	,{name:"BFININCHARGE1", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
	,{name:"BFINCOMPHANNM2",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
	,{name:"BFININCHARGE2", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
	,{name:"BFINCOMPHANNM3",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
	,{name:"BFININCHARGE3", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
	,{name:"BAUDCOMPHANNM1",	dspname:["#cspan", "회계자문", "자문사"], align:"left", width:"180"}
	,{name:"BAUDINCHARGE1", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
	,{name:"BAUDCOMPHANNM2",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
	,{name:"BAUDINCHARGE2", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
	,{name:"BAUDCOMPHANNM3",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
	,{name:"BAUDINCHARGE3", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
	,{name:"BLAWCOMPHANNM",		dspname:["#cspan", "법률자문", "자문사"], align:"left", width:"180"}
	,{name:"BLAWINCHARGE", 		dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
	,{name:"CONSDT",			dspname:["일자", "자문계약", "#rspan"], align:"left", width:"100"}
	,{name:"DIRTDT",			dspname:["#cspan", "이사회 의결일", "#rspan"], align:"left", width:"100"}
	,{name:"STKHDT",			dspname:["#cspan", "주주총회 의결일", "#rspan"], align:"left", width:"100"}
	,{name:"MNADT",				dspname:["#cspan", "합병기일", "#rspan"],align:"left", width:"100"}
	,{name:"CURRCD",			dspname:["금액 및 성격", "통화", "#rspan"], align:"left", width:"100"}
	,{name:"AMT",				dspname:["#cspan", "금액", "#rspan"], align:"right", width:"100", type:"number", format:"#,##0"}
	,{name:"RATE",				dspname:["#cspan", "환율(1통화당 원화)", "#rspan"], align:"right", width:"100", type:"number", format:"#,##0"}
	,{name:"RATEAMT",			dspname:["#cspan", "원화 환산금액", "#rspan"], align:"right", width:"100", type:"number", format:"#,##0"}
	,{name:"DEALNM1",			dspname:["#cspan", "거래형태", "1"], align:"left", width:"100"}
	,{name:"DEALNM2",			dspname:["#cspan", "#cspan", "2"], align:"left", width:"100"}
	,{name:"DEALNM3",			dspname:["#cspan", "#cspan", "3"], align:"left", width:"100"}
	,{name:"BORDDEALNM",		dspname:["#cspan", "Cross-Border", "#rspan"], align:"left", width:"100"}
];

DOWNLOAD_COLUMN_DEF.REALLEAGUE = [
     {name:"CONFNM", 			dspname:["Confidential", "#rspan", "#rspan"], align:"left", width:"85"}
  	,{name:"YEARLY", 			dspname:["대상년도", "#rspan", "#rspan"], align:"left", width:"70"}
  	,{name:"UPDDT", 			dspname:["수정일", "#rspan", "#rspan"], align:"left", width:"130"}
  	,{name:"PDTCDNM",			dspname:["Product", "Code/Name", "#rspan"],	align:"left", width:"230"}
  	,{name:"PRJTCD", 			dspname:["Project 정보", "Project", "Code"], align:"left", width:"85"}
  	,{name:"PRJTNM", 			dspname:["#cspan", "#cspan", "Name"], align:"left", width:"230"}
  	,{name:"HQNM", 				dspname:["#cspan", "담당", "본부"], align:"left", width:"180"}
  	,{name:"PTREMPNM", 			dspname:["#cspan", "#cspan", "PTR"], align:"left", width:"70"}
  	,{name:"MGREMPNM", 			dspname:["#cspan", "#cspan", "MGR"], align:"left", width:"70"}
  	,{name:"CLIENTNM", 			dspname:["#cspan", "Client", "Name"], 		align:"left", width:"180"}
  	,{name:"CLIENTINDUNM", 		dspname:["#cspan", "#cspan", "Industry"], 	align:"left", width:"230"}
  	,{name:"STATUSNM",			dspname:["Status", "#rspan", "#rspan"], align:"left", width:"70"}
  	,{name:"TNM",				dspname:["Target", "대상기업", "매각대상"], align:"left", width:"230"}
 	,{name:"TNATNM", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"TCATEGORY", 		dspname:["#cspan", "#cspan", "대분류"], align:"left", width:"70"}
 	,{name:"SCOMPHANNM1", 		dspname:["Seller", "대상기업", "Name"], align:"left", width:"180"}
 	,{name:"SINDUNM1", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"SNATNM1", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"SCOMPHANNM2", 		dspname:["#cspan", "#cspan", "Name"], align:"left", width:"180"}
 	,{name:"SINDUNM2", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"SNATNM2", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"SCOMPHANNM3", 		dspname:["#cspan", "#cspan", "Name"], align:"left", width:"180"}
 	,{name:"SINDUNM3", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"SNATNM3", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"SFINCOMPHANNM1",	dspname:["#cspan", "재무/전략자문", "자문사"], align:"left", width:"180"}
 	,{name:"SFININCHARGE1", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"SFINCOMPHANNM2",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"SFININCHARGE2", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"SFINCOMPHANNM3",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"SFININCHARGE3", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"SAUDCOMPHANNM1",	dspname:["#cspan", "회계자문", "자문사"], align:"left", width:"180"}
 	,{name:"SAUDINCHARGE1", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"SAUDCOMPHANNM2",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"SAUDINCHARGE2", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"SAUDCOMPHANNM3",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"SAUDINCHARGE3", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"SLAWCOMPHANNM",		dspname:["#cspan", "법률자문", "자문사"], align:"left", width:"180"}
 	,{name:"SLAWINCHARGE", 		dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"BCOMPHANNM1", 		dspname:["Buyer", "대상기업", "Name"], align:"left", width:"180"}
 	,{name:"BINDUNM1", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"BNATNM1", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"BCOMPHANNM2", 		dspname:["#cspan", "#cspan", "Name"], align:"left", width:"180"}
 	,{name:"BINDUNM2", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"BNATNM2", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"BCOMPHANNM3", 		dspname:["#cspan", "#cspan", "Name"], align:"left", width:"180"}
 	,{name:"BINDUNM3", 			dspname:["#cspan", "#cspan", "Industry"], align:"left", width:"230"}
 	,{name:"BNATNM3", 			dspname:["#cspan", "#cspan", "Country"], align:"left", width:"70"}
 	,{name:"BFINCOMPHANNM1",	dspname:["#cspan", "재무/전략자문", "자문사"], align:"left", width:"180"}
 	,{name:"BFININCHARGE1", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"BFINCOMPHANNM2",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"BFININCHARGE2", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"BFINCOMPHANNM3",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"BFININCHARGE3", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"BAUDCOMPHANNM1",	dspname:["#cspan", "회계자문", "자문사"], align:"left", width:"180"}
 	,{name:"BAUDINCHARGE1", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"BAUDCOMPHANNM2",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"BAUDINCHARGE2", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"BAUDCOMPHANNM3",	dspname:["#cspan", "#cspan", "자문사"], align:"left", width:"180"}
 	,{name:"BAUDINCHARGE3", 	dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"BLAWCOMPHANNM",		dspname:["#cspan", "법률자문", "자문사"], align:"left", width:"180"}
 	,{name:"BLAWINCHARGE", 		dspname:["#cspan", "#cspan", "담당자"], align:"left", width:"70"}
 	,{name:"CONSDT",			dspname:["일자", "자문계약", "#rspan"], align:"left", width:"100"}
 	,{name:"MOUDT",				dspname:["#cspan", "MOU체결", "#rspan"], align:"left", width:"100"}
 	,{name:"SPADT",				dspname:["#cspan", "매매계약(SPA)", "#rspan"], align:"left", width:"100"}
 	,{name:"CLOSDT",			dspname:["#cspan", "잔금납입일(Closing)", "#rspan"],align:"left", width:"100"}
 	,{name:"CURRCD",			dspname:["금액 및 성격", "통화", "#rspan"], align:"left", width:"100"}
 	,{name:"AMT",				dspname:["#cspan", "금액", "#rspan"], align:"right", width:"100", type:"number", format:"#,##0"}
 	,{name:"RATE",				dspname:["#cspan", "환율(1통화당 원화)", "#rspan"], align:"right", width:"100", type:"number", format:"#,##0"}
 	,{name:"RATEAMT",			dspname:["#cspan", "원화 환산금액", "#rspan"], align:"right", width:"100", type:"number", format:"#,##0"}
 	,{name:"BORDDEALNM",		dspname:["#cspan", "Cross-Border", "#rspan"], align:"left", width:"100"}
  ];

// 20200323 남웅주 대상 프로젝트 목록 ------ 엑셀다운로드 
DOWNLOAD_COLUMN_DEF.PROJECTLIST = [
	{name:"LTTGTNM", 	dspname:["League Table 대상여부", "#rspan"], align:"center", width:"110", type: "str"},
 	{name:"CONSNM", 	dspname:["자문형태", 	"#rspan"], 	align:"left", 	width:"100", type: "str"},
 	{name:"PDTINFO", 	dspname:["Product", "#rspan"], 	align:"left", 	width:"200", type: "str"},
 	{name:"PRJTCD", 	dspname:["Project", "Code"], 	align:"center", width:"100", type: "str"},
 	{name:"PRJTNM", 	dspname:["#cspan", 	"Name"], 	align:"left", 	width:"200", type: "str"},
 	{name:"HQNM", 		dspname:["#cspan", 	"본부"], 		align:"left", 	width:"150", type: "str"},
 	{name:"PTREMPNM", 	dspname:["#cspan", 	"EL"], 		align:"center", width:"80", type: "str"},
 	{name:"MGREMPNM", 	dspname:["#cspan", 	"TM"], 		align:"center", width:"80", type: "str"},
 	{name:"CHREMPNM", 	dspname:["#cspan", 	"본부담당자"], 	align:"center", width:"100", type: "str"},
 	{name:"UPDDT", 		dspname:["최종 수정일", "#rspan"], 	align:"center", width:"80", type: "str"},
 	{name:"CISDT1", 	dspname:["CIS", 	"(중간)종료"], align:"center", width:"80", type: "str"},
 	{name:"CISDT2", 	dspname:["#cspan", 	"착수금"], 	 align:"center",width:"80", type: "str"},
 	{name:"CISDT3", 	dspname:["#cspan", 	"중도금"], 	align:"center", width:"80", type: "str"},
 	{name:"CISDT4", 	dspname:["#cspan", 	"잔금"], 		align:"center", width:"80", type: "str"},
 	{name:"CLOSDT1", 	dspname:["프로젝트", 	"1차종료일"], 	align:"center", width:"80", type: "str"},
 	{name:"CLOSDT2", 	dspname:["#cspan", 	"2차종료일"], 	align:"center", width:"80", type: "str"},
 	{name:"CLOSDT3", 	dspname:["#cspan", 	"3차종료일"], 	align:"center", width:"80", type: "str"}
];

