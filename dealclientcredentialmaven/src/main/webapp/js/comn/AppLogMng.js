var AppLOGManager = (function(){
	var m_classNm = "AppLogMngService"
	var reserved_code = ["LOGIN", "VISIT"]
	loginLog();
	
	function loginLog(){
		if (!$.cookie(CURR_INFO.systemCode + "_login")) {
			var date = new Date();
			var minutes = 580;
			date.setTime(date.getTime() + (minutes * 60 * 1000));
			$.cookie(CURR_INFO.systemCode + "_login", "ok", {expires : date})
			insertActionLog("index.jsp", "LOGIN");
		}
	}
	
	function visitLog(pagePath){
		insertActionLog(pagePath, "VISIT")
	}
	
	function customLog(pagePath, logKind){
		if(reserved_code.indexOf(logKind.toUpperCase()) > 0){
			alert("'" + logKind + "' 코드는 예약코드 입니다.");
		}else{
			insertActionLog(pagePath,logKind);
		}
	}
	
	function insertActionLog(pagePath, logKind){
		$.ajax({
			url:CURR_INFO.serviceBroker,
			data:$.param({
				SystemCode	:m_classNm,
				Method		:"insertLogToLogMaster",
				data		: JSON.stringify({
					systemcode	: CURR_INFO.systemCode,
					kind	  	: logKind,
					appurl	 	: window.location.protocol + "//" + window.location.host + "/" + CURR_INFO.systemCode,
					pagepath	: pagePath,
					emplno		: USERINFO.getEmplNo()
				})
			})
		})
	}
	
	return {
		visitLog:visitLog,
		customLog:customLog
	}
})();