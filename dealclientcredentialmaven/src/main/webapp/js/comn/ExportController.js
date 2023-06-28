var ExportObj = (function(){
	var m_serviceBroker  = CURR_INFO.serviceBroker;
	var m_excelBroker	 = "comn/ExportBroker.jsp";
	
	var m_excelFormObj 	= null;
	var m_maxRow 		= null;
	var m_totalCount 	= null;
	
	function init(param){
		m_maxRow 		= param.excelJSON.maxRow;
		m_totalCount 	= param.excelJSON.totalCount;
	}
	
	function parseColumn(_column){
		var header = [];
		var cspan_base_idx = [];	// colspan 적용 Index
		var cspan = [];				// colspan 값
		for(var i=0; i< _column[0].dspname.length; i++){
			cspan_base_idx[i] = 0;
			cspan[i] = 1;
		}
		
		var rspan_base_idx = 0;		// rowspan 적용 Index
		var rspan = 1;				// rowspan 값
		
		for(var i=0; i<_column.length; i++){
			header[i] = [];
			
			// row span 관련 초기화
			rspan = 1;
			rspan_base_idx = 0;
			
			for(var j=0; j<_column[i].dspname.length; j++){
				
				var tmpVal = _column[i].dspname[j];
				var column = {};
				
				if(j==0){
					column.name		= _column[i].name;
					column.width 	= _column[i].width||"100";
					column.align 	= _column[i].align||"left";
					column.type		= _column[i].type||"str";
					column.format	= _column[i].format||"";
				}
				
				if(tmpVal == "#cspan"){
					column.dspname = "";
					cspan[j] = cspan[j] + 1;
					if(i==(_column.length-1)){
						header[cspan_base_idx[j]][j].colspan = cspan[j];
					}
				}else if(tmpVal == "#rspan"){
					column.dspname = "";
					rspan = rspan + 1;
					if(j == (_column[i].dspname.length-1)){
						header[i][rspan_base_idx].rowspan = rspan;
					}
				}else{
					column.dspname = tmpVal;
					rspan_base_idx = j;
					
					if(rspan > 1){
						header[i][rspan_base_idx].rowspan = rspan;
					}
					if(cspan[j] > 1){
						header[cspan_base_idx[j]][j].colspan = cspan[j];
					}
					
					// col span 관련 초기화
					cspan_base_idx[j] = i;
					cspan[j] = 1;
				}
				header[i][j] = column;
			}
		}
		//console.log(header);
		return header;
	}
	
	function submit(param){
		var inputs = "";
		param.excelJSON = JSON.stringify(param.excelJSON)
		
		/*
		var sParam = {};
		var _h = $.param(param).split("&");
		for(var i=0; i<_h.length; i++){
			var tmp = _h[i].split("=");
			sParam[decodeURIComponent(tmp[0])] = decodeURIComponent(tmp[1]);
			console.log(decodeURIComponent(tmp[0]) + " : " + decodeURIComponent(tmp[1]));
		}
		inputs +="<input type=\'hidden\' name=\'filetype\' value=\'excel\' />";
		for(var obj in sParam){
			inputs += "<input type=\'hidden\' name=\'"+ obj +"\' value=\'"+ sParam[obj] +"\' />";
		}
    	$('<form action="' + m_excelBroker + '" method="post">'+inputs+'</form>')
        .appendTo('body').submit().remove();*/
		
		
		inputs +="<input type=\'hidden\' name=\'filetype\' value=\'excel\' />";
		for(var obj in param){
			inputs += "<input type=\'hidden\' name=\'"+ obj +"\' value=\'"+ (typeof(param[obj])=="object" || typeof(param[obj])=="arrary" ? JSON.stringify(param[obj]) : param[obj]) +"\' />";
		}
    	$('<form action="' + m_excelBroker + '" method="post">'+inputs+'</form>')
        .appendTo('body').submit().remove();
	}
	
	function parseParam(param, curKey, curObj){
		if(typeof(curObj) == "object"){
			for(var key in curObj){
				key + "[" +  + "]=" + parseParam(param, curObj[key]);				
			}
		}else if(typeof(curObj) == "array"){
			for(var i=0; i<curObj.length; i++){
				parseParam(param, curObj[i]);
			}
		}else{
			return  
		}
	}
	
	// Excel Export
	function ExcelDownload(param, start, end){
		var btnObj	= null;
		var max		= null;
		var total	= null;
		
		if(param.excelJSON.btnObj){
			btnObj	= param.excelJSON.btnObj;
			delete param.excelJSON.btnObj;
		}
		if(param.excelJSON.maxRow){
			max	= param.excelJSON.maxRow;
		}
		if(param.excelJSON.totalCount){
			total = param.excelJSON.totalCount;
		}
		
		if(!(max==null || total==null)){
			if(total > max){
				if(!param.disablePopup){
					showPopup(btnObj, param); 
					return;	
				}else{
					delete param.disablePopup;
				}
			}else{
				param.posStart	= 0;
				param.count		= total;	
			}
		}else{
			param.posStart	= 0;
			param.count		= total; 
		}

		if(!(typeof(start) === "undefined" || typeof(end) === "undefined")){
			start	= Number(start-1);
			end 	= Number(end-1);
			param.posStart	= start;
			param.count		= end-start+1 
		}
		
		param.excelJSON.column = parseColumn(param.excelJSON.column);
		submit(param);
	}
	

	function toggleExcelForm(){
		
		if(m_excelFormObj){
			if(m_excelFormObj.isVisible()){
				m_excelFormObj.hide();
				return false;
			}else{
				m_excelFormObj.show();
				return true;
			}
		}
		return false;
	}

	function showPopup(inp, param){
		var html = "" +
		"<div style='width:300px'>" +
		"<div>" +
		"	<table class='tbl'>" +
		"	<col width='150px'><col width='*'>" +
		"	<tr><td><label>- 총 Row 수</label></td><td><span id='excel-total-count'></span></td></tr>" +
		"	<tr><td><label>- Export 가능한<br/>&nbsp;&nbsp;최대 Row 수</label></td><td><span id='excel-max-row'></span></td></tr>" +
		"	<tr>" +
		"		<td><label>- Row 범위</label></td>" +
		"		<td><input type='text' id='excel-start-row' style='width:50px'></input> ~ <input type='text' id='excel-end-row' style='width:50px'></input></td>" +
		"	</tr>" +
		"	</table>" +
		"</div>" +
		"<div class='blank-height-5'></div>" + 
		"<div class='align'>" +
		"	<div class='align-right'>" +
		"		<span id='btnExcelExportAction' class='btn'>Export</span>" +
		"	</div>" +
		"</div>" +
		"</div>";

		if (!m_excelFormObj) {
			m_excelFormObj = new dhtmlXPopup({mode : "bottom"});
			m_excelFormObj.attachHTML(html);
		}
		
		if(!toggleExcelForm()) return;
		
		$("#excel-total-count").text(param.excelJSON.totalCount);
		$("#excel-max-row").text(param.excelJSON.maxRow);
		$("#excel-start-row").val("");
		$("#excel-end-row").val("");
		var x = $(inp).offset().left;
		var y = $(inp).offset().top;
		var w = inp.offsetWidth;
		var h = inp.offsetHeight;
		m_excelFormObj.show(x, y, w, h);
		
		$("#btnExcelExportAction").off("click");
		$("#btnExcelExportAction").on("click", function(){
			var total	= param.totalCount;
			var start 	= $("#excel-start-row").val();
			var end 	= $("#excel-end-row").val();
			var max 	= param.maxRow;
			delete param.totalCount;
			delete param.maxRow;
			if(validationCheckExcel(total, start, end, max)){
				param.disablePopup = true;
				ExcelDownload(param, start, end)
				m_excelFormObj.hide();
			}
		});
	}
	
	// Excel Export 시 조건 Validation Check
	function validationCheckExcel(total, start, end, max){
		if(!(SAMILCOMMON.checkNumber(start) && SAMILCOMMON.checkNumber(end))){
			alert("Row 범위에는 숫자만 입력 가능 합니다.");
			return false;
		}else if($.trim(start) == "" || $.trim(end) == "" ){
			alert("Row 범위를 입력하세요.");
			return false;
		}else{
			total	= Number(total);
			start 	= Number(start);
			end 	= Number(end);
			max 	= Number(max);
			
			if(start > end){
				alert("Row 범위에서 시작이 끝보다 작아야 합니다.");
				return false;
			}else if(end > total){
				alert("Row 범위의 끝은 총 Row 수 보다 작아야 합니다.");
				return false;
			}else if(end - start >= max){
				alert("Row 범위는 " + max + " 보다 작아야 합니다.");
				return false;
			}else if(start <= 0 || end <= 0){
				alert("Row 범위에 0보다 큰 수를 입력해야 합니다.");
				return false;
			}else{
				return true;
			}
		}
		
	}
	
	// Excel Export 하기 전 데이터의 총 Row 수 확인 용
	function getRowCount(param, callback){
		var tmpExcelJSON = param.excelJSON;
		delete param.excelJSON;
		
		$.ajax({
			url		: m_serviceBroker,
			data	: $.param(param),
			type	:"post", 
			success	: function(text){
				param.excelJSON = tmpExcelJSON;
				if(callback) callback(text);
			}
		})
	}
	
	return {
		ExcelDownload:ExcelDownload,
		getRowCount:getRowCount,
		parseColumn:parseColumn
	}
}());