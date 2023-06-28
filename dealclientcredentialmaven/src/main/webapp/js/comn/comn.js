var SAMILCOMMON = (function(){
	function getPostXMLBySync(url, param){
		var retVal = "";
		$.ajax({      
	        type:"POST",  
	        url:url,
	        dataType:"xml",
	        async:false, // 중요
	        data:param,      
	        success:function(args){retVal = args;},   
	        error:function(xhr, status, error){
	        	//xhr.responseText;
	        	//status;
	        	//error;
	        	//alert("--------- error -----------\n" + "*status: " + status + "\n" + "*error: "+ error);  
	        },
	        complete:function(){
	        	
	        }
	   });
		return retVal;
	}

	// 현재 날짜 기준으로 FY 구하기
	function getCurFY(){
		var curDate = new Date();
		var tmp = {};
		if((curDate.getMonth()+1) >= 7){
			tmp.sYear = "" + curDate.getFullYear();
			tmp.eYear = "" + (curDate.getFullYear()+1);

		}else{
			tmp.sYear = "" + (curDate.getFullYear()-1);
			tmp.eYear = "" + curDate.getFullYear();
		}
		
		tmp.sMonth = "07";
		tmp.sDay = "01";
		tmp.eMonth = "06";
		tmp.eDay = "30";
		return tmp;
	}

	// 월(月) 리스트
	function getMonthList_desc(_type){
		var curDate = new Date();
		var month;
		
		if(_type){
			if(_type=="sFY"){
				month = "07";
			}else if(_type=="eFY"){
				month = "06";
			}else{
				month = _type;
			}		
		}else{
			month = curDate.getMonth() + 1;
		}
					
		var ret  = new Array();	
		for(var j = 12 ; j >= 1; j--){
			var obj = new Object();
			obj.text = obj.value = j<10?"0"+j.toString():j.toString();
			
			if(obj.value == month){
				obj.selected = true;
			}else{
				obj.selected = false;
			}
			ret[12-j] = obj;
		}
		return ret;
	}

	//월(月) 리스트
	function getMonthList_asc(_type){
		var curDate = new Date();
		var month;
		if(_type){
			if(_type=="sFY"){
				month = "07";
			}else if(_type=="eFY"){
				month = "06";
			}else{
				month = _type;
			}		
		}else{
			month = curDate.getMonth() + 1;
		}
		
					
		var ret  = new Array();	
		for(var j = 1 ; j <= 12; j++){
			var obj = new Object();
			obj.text = obj.value = j<10?"0"+j.toString():j.toString();
			
			if(obj.value == month){
				obj.selected = true;
			}else{
				obj.selected = false;
			}
			ret[j-1] = obj;
		}
		return ret;
	}

	function getMonthInterval(time1,time2) { //measureMonthInterval(time1,time2)
	    var date1 = toTimeObject(time1);
	    var date2 = toTimeObject(time2);

	    var years  = date2.getFullYear() - date1.getFullYear();
	    var months = date2.getMonth() - date1.getMonth();
	    var days   = date2.getDate() - date1.getDate();

	    return (years * 12 + months + (days >= 0 ? 0 : -1) );
	}
	
	function getDayInterval(time1,time2) {
		var date1 = toTimeObject(time1);
	    var date2 = toTimeObject(time2);
	    var day   = 1000 * 3600 * 24; //24시간

	    return parseInt((date2 - date1) / day, 10);
	}
	function isFutureTime(time) {
	    return (toTimeObject(time) > new Date());
	}
	function toTimeObject(time) { //parseTime(time)
	    var year  = time.substr(0,4);
	    var month = time.substr(4,2) - 1; // 1월=0,12월=11
	    var day   = time.substr(6,2);
	    var hour  = time.substr(8,2);
	    var min   = time.substr(10,2);
	    
	    return new Date(year,month,day,hour,min);
	}

	function toTimeString(date) { //formatTime(date)
	    var year  = date.getFullYear();
	    var month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
	    var day   = date.getDate();
	    var hour  = date.getHours();
	    var min   = date.getMinutes();
	    var sec	  = date.getSeconds();

	    if (("" + month).length == 1) { month = "0" + month; }
	    if (("" + day).length   == 1) { day   = "0" + day;   }
	    if (("" + hour).length  == 1) { hour  = "0" + hour;  }
	    if (("" + min).length   == 1) { min   = "0" + min;   }
	    if (("" + sec).length   == 1) { sec   = "0" + sec;   }

	    return ("" + year + month + day + hour + min + sec);
	}
	
	function toFormatedTimeString(date) { //formatTime(date)
	    var year  = date.getFullYear();
	    var month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
	    var day   = date.getDate();
	    var hour  = date.getHours();
	    var min   = date.getMinutes();
	    var sec	  = date.getSeconds();

	    if (("" + month).length == 1) { month = "0" + month; }
	    if (("" + day).length   == 1) { day   = "0" + day;   }
	    if (("" + hour).length  == 1) { hour  = "0" + hour;  }
	    if (("" + min).length   == 1) { min   = "0" + min;   }
	    if (("" + sec).length   == 1) { sec   = "0" + sec;   }

	    return ("" + year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec);
	}
	
	function shiftTime(time,y,m,d,h) { //moveTime(time,y,m,d,h)
		var date = toTimeObject(time);

	    date.setFullYear(date.getFullYear() + y); //y년을 더함
	    date.setMonth(date.getMonth() + m);       //m월을 더함
	    date.setDate(date.getDate() + d);         //d일을 더함
	    date.setHours(date.getHours() + h);       //h시를 더함

	    return toTimeString(date);
	}
	

	function replaceAll(text, fromSubStr, toSubStr){
		if(text){
			return text.split(fromSubStr).join(toSubStr)	
		} else{
			return text;
		}
		
	}
	
	function checkEmplNo(emplno){
		// 6자리 숫자 체크
		var re = /^[0-9]{6,6}$/;
		if(re.test(emplno)){
			return true;
		}else{
			return false;
		}
	}
	
	function checkNumber(number){
		var re = /^(\-|[0-9])[0-9]*$/;
		if(!re.test(number)){
			return false;
		}
		return true;
	}
	
	function numberWithCommas(x) {
		return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
	
	function progressOn(){
		$("body").append('<div id="div-progress" class="progress"><i class="fa fa-spinner fa-pulse fa-5x"></i><br/><br/><span>데이터 로딩 중입니다...</span></div>');
	}
	
	function progressOff(){
		$("#div-progress").remove();
	}
	
	function transparencyMsgOn(id, msg){
		$("#" + id).append("" +
				"<div id='transparency-msg-" + id + "' class='transparency-msg-area'>" +
						"<div class='transparency-msg'>" +
							"<i class='fa fa-info-circle' aria-hidden='true'></i>" + msg + "" +
						"</div>" +
				"</div>")
	} 
	
	function transparencyMsgOff(id){
		$("#transparency-msg-" + id).remove();
	}
	
	
	function intersection(arr1, arr2){
		var retVal = [];
		if((typeof arr1 == "array" || typeof arr1 == "object" ) && (typeof arr2 == "array" || typeof arr2 == "object")){
			for(var i=0; i<arr1.length; i++){
				for(var j=0; j<arr2.length; j++){
					if(arr1[i] == arr2[j]) retVal.push(arr1[i]);
				}
			}
		}
		return retVal;
	}
	
	function union(arr1, arr2){
		var retVal = arr1;
		for(var i=0; i<arr2.length; i++){
			if($.inArray(arr[i], retVal) < 0){
				retVal.push(arr[i]);
			}
		}
		return retVal;
	}
	
	function getArrayByColumn(arr, columns){
		var retVal = []
		for(var i=0; i<arr.length; i++){
			var tmp = [];
			for(var j=0; j<columns.length; j++){
				tmp.push(arr[i][columns[j]]);
			}
			retVal.push(tmp);
		}
		return retVal;
	}
	
	
	return {
		getPostXMLBySync:getPostXMLBySync,
		getCurFY:getCurFY,
		getMonthList_desc:getMonthList_desc,
		getMonthList_asc:getMonthList_asc,
		getMonthInterval:getMonthInterval,
		toTimeObject:toTimeObject,
		toTimeString:toTimeString,
		shiftTime:shiftTime,
		getDayInterval:getDayInterval,
		isFutureTime:isFutureTime,
		replaceAll:replaceAll,
		checkEmplNo:checkEmplNo,
		toFormatedTimeString:toFormatedTimeString,
		checkNumber:checkNumber,
		numberWithCommas:numberWithCommas,
		progressOn:progressOn,
		progressOff:progressOff,
		transparencyMsgOn:transparencyMsgOn,
		transparencyMsgOff:transparencyMsgOff,
		intersection:intersection,
		getArrayByColumn:getArrayByColumn
	};
}());
