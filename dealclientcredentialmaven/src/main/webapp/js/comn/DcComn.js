// Common Constant
var GC = {
	
};

// Common Function
var GF = {};

// 천단위 콤마 추가
GF.numberWithCommas = function(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};
//천단위 콤마 제거
GF.numberCutCommas = function(x) {
	return x.toString().replace(/[^0-9]/gi,"");
};

// 문자 Byte 계산
GF.byteLength = function(s) {
	var b = 0;
	var c = s;
	for (var i=0; c=s.charCodeAt(i++); b += c>>11?3:c>>7?2:1);
	return b;
};

GF.inputOnlyNumber = function(o) {
	o.off("keyup").on("keyup", function(e) {
		$(this).val( $(this).val().replace(/[^0-9]/gi,""));
	});
};

GF.replaceNullValue = function (str, defaultVal) {
    var defaultValue = "";
    if (typeof defaultVal != 'undefined') defaultValue = defaultVal;
    if (typeof str == "undefined" || str == null || str == '' || str == "undefined") {
        return defaultValue;
    }
    return str;
};

GF.getCurYear = function(){
	var curDate = new Date();
	var stdDate = new Date(curDate.getFullYear(), GC.stdMonthDay.month, GC.stdMonthDay.day);
	var curYear= "" + curDate.getFullYear();
	if(curDate < stdDate) curYear = "" + (curDate.getFullYear()-1);
	return curYear
};

GF.getItemText = function(itemArray, itemCode) {
	for (var i=0; i<itemArray.length; i++) {
		if (itemCode == itemArray[i].code) {
			return itemArray[i].name;
		}
	}
	return "";
};


// ================= Ajax Util =================
function Ajax() {};
Ajax.get = function(serviceWorker, param, successCallback, errorCallback, completeCallback) {
	Ajax.___ajax("GET", "json", serviceWorker, param, successCallback, errorCallback, completeCallback);
};
Ajax.post = function(serviceWorker, param, successCallback, errorCallback, completeCallback) {
	Ajax.___ajax("POST", "json", serviceWorker, param, successCallback, errorCallback, completeCallback);
};
Ajax.jsp = function(jspInnerPath, param, successCallback, errorCallback, completeCallback) {
	//Ajax.___ajax("POST", "html", jspInnerPath, param, successCallback, errorCallback, completeCallback);
	$.ajax({
		url: CURR_INFO.contextPath + "/contents/" + jspInnerPath + ".jsp",
		type: "GET",
		dataType: "html",
		timeout: 30000,
		cache: false,
		data: $.param($.extend({}, {"SystemCode":"dealcredential", "Method":"service", "ContentType":"html", "ServiceTask":jspInnerPath}, param)),
		success: function(data, textStatus, jqXHR) {
			if (successCallback) {
				successCallback(data);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			try {
				if (jqXHR.readyState == 0 || jqXHR.readyState == 4) {
					alert("잠시 후 다시 접속하시기 바랍니다.");
					return false;
				}
			} catch(e) {}
			if (errorCallback) {
				errorCallback(jqXHR, textStatus, errorThrown);
			}
		},
		complete: function(jqXHR, textStatus) {
			if (completeCallback) {
				completeCallback(jqXHR, textStatus);
			}
		}
	});
};
Ajax.downloadPPT = function(serviceWorker, param){
	if(confirm("PPT 변환을 하시겠습니까?\n\n(완료될 때까지 기다려 주시기 바랍니다.)")){
		var _o = {"SystemCode":"dealcredential", "Method":"service", "ContentType":"download_ppt", "ServiceTask":serviceWorker};
		var _p = $.extend({}, _o, param);
	    // 파라미터를 form의  input으로 만든다.
	    var inputs = [];
	    for(var key in _p){
	    	inputs.push('<input type="hidden" name="' + key + '" value="' + _p[key] +'" />');
	    }
	    // request를 보낸다.
	    jQuery('<form action="'+ CURR_INFO.serviceBroker +'" method="'+ ('post') +'">'+inputs.join("")+'</form>')
	    .appendTo('body').submit().remove();	
	}
}
Ajax.___ajax = function(method, contentType, serviceTask, param, successCallback, errorCallback, completeCallback) {
	var _o = {"SystemCode":"dealcredential", "Method":"service", "ContentType":contentType, "ServiceTask":serviceTask};
	var _p = $.extend({}, _o, param);
	$.ajax({
		url: CURR_INFO.serviceBroker,
		type: method,
		dataType: contentType,
		timeout: 50000,
		cache: false,
		data: $.param(_p),
		success: function(data, textStatus, jqXHR) {
			try {
				if (data) {
					if (false == data.result) {
						if ("99998" == data.error.errorCode) {
							alert("로그인이 필요한 서비스입니다.");
							location.href = CURR_INFO.portalUri;
							return false;
						}
						if ("99997" == data.error.errorCode) {
							alert("권한이 없는 사용자입니다.");
							location.href = CURR_INFO.portalUri;
							return false;
						}
						if ("10002" == data.error.errorCode || "10003" == data.error.errorCode) {
							alert("요청한 서비스를 찾을 수 없습니다.");
							return false;
						}
					}
				}
			} catch(e) {}
			if (successCallback) {
				successCallback(data);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			try {
				if (jqXHR.readyState == 0 || jqXHR.readyState == 4) {
					alert("잠시 후 다시 접속하시기 바랍니다.");
					return false;
				}
			} catch(e) {}
			if (errorCallback) {
				errorCallback(jqXHR, textStatus, errorThrown);
			}
		},
		complete: function(jqXHR, textStatus) {
			if (completeCallback) {
				completeCallback(jqXHR, textStatus);
			}
		}
	});
};
