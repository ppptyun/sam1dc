const SessionStorageCtrl = (function(){
	const key = "__STANDARD_AUDIT_TIME__";	// 어플리케이션 구분 코드

	function getSessionData(){
		if(sessionStorage.getItem(key)) return JSON.parse(atob(sessionStorage.getItem(key)));
		return null;
	}
	
	function setSessionData(data){
		sessionStorage.setItem(key, btoa(JSON.stringify(data)));
	}
	
	
	return {
		clear: function(){
			sessionStorage.removeItem(key);
		},
		getData: function(property){
			const sessionData = getSessionData();
			if(sessionData){
				return sessionData[property];
			}
			return null;
		},
		setData: function(property, data){
			
			let sessionData = getSessionData();
			if(!sessionData) sessionData = {};
			sessionData[property] = data;
			
			setSessionData(sessionData);
		}
	}
})();