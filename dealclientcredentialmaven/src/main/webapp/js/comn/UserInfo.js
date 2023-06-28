var USERINFO = (function(USERINFO, undefined){
	var mUserObj={};
	
	USERINFO.init = function(_userObj){mUserObj = _userObj;};
	USERINFO.getAuth = function(){
		if (! mUserObj.authclass) {
			// 권한 테이블에 별도의 권한이 없을 경우 기본 권한 리턴
			return mUserObj.authapp;
		} else {
			if (mUserObj.authclass == '9') {
				// 권한테이블에 '9'로 설정있으면 권한테이블 권한 리턴
				return auth_class;
			} else {
				// 더 높은 권한 리턴
				return mUserObj.authapp < mUserObj.authclass? mUserObj.authapp:mUserObj.authclass;
			}
		}
	};
	USERINFO.getAuthType	= function(){return mUserObj.authtype;};
	USERINFO.getAuthClass 	= function(){return mUserObj.authclass;};
	USERINFO.getAuthApp 	= function(){return mUserObj.authapp;};
	USERINFO.getKorName 	= function(){return mUserObj.korname;};
	USERINFO.getEngName 	= function(){return mUserObj.engname;};
	USERINFO.getInteId 		= function(){return mUserObj.inteid;};
	USERINFO.getEmail 		= function(){return mUserObj.email;};
	USERINFO.getEmplNo 		= function(){return mUserObj.emplno;};
	USERINFO.getGradCd 		= function(){return mUserObj.gradcd;};
	USERINFO.getGradNm 		= function(){return mUserObj.gradnm;};
	USERINFO.getTeamCd 		= function(){return mUserObj.teamcd;};
	USERINFO.getTeamNm 		= function(){return mUserObj.teamnm;};
	USERINFO.getTel 		= function(){return mUserObj.tel;};
	
	return USERINFO;
	
}(USERINFO||{}));