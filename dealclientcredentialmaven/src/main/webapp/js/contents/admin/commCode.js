(function(){
	// 변수 선언
	var thisObj = this;
	var addObjId = "$_new";	
	var myTreeView = null;
	var saveState = "U";//I-신규/U-수정
	var formObj = [
			{
				id : "parcd" 
				, label : "상위코드"
				, type : "text"	
				, minlength : 1
				, maxlength : 7
				, require : true
				, readonly : true	
				, descript : "해당 코드가 소속된 분류코드 (최상위코드 : ROOT)"
			}
			,{	id : "itemcd"
				, label : "항목코드"
				, type : "text"	
				, minlength : 1
				, maxlength : 7
				, require : true
				, readonly : false	
				, descript : "현재 항목의 코드"
			}
			,{  id : "itemnm"
				, label : "항목이름"
				, type : "text"
				, minlength : 1					
				, maxlength : 100
				, require : false
				, readonly : false	
				, descript : "항목이 표현되는 이름"
			}
			,{  id : "useyn"
				, label : "사용여부"
				, type : "select"					
				, minlength : 1
				, maxlength : 1
				, require : false
				, readonly : false	
				, descript : "사용 여부 ( Y : N )"
			}
			,{  id : "sort"
				, label : "정렬순서"
				, type : "number"	
				, minlength : 1
				, maxlength : 3
				, require : false
				, readonly : false	
				, descript : "정렬되는 순서"
			}
			,{  id : "descp"
				, label : "설명"
				, type : "textarea"					
				, minlength : 0					
				, maxlength : 400
				, require : false
				, readonly : false
				, descript : "해당 항목에 관한 설명"
			}
			,{  id : "updempno"
				, label : "수정자"
				, type : "text"					
				, require : false
				, readonly : true
				, descript : "마지막으로 수정한 사람"
			}
			,{  id : "upddt"
				, label : "수정시간"
				, type : "text"					
				, require : false
				, readonly : true
				, descript : "마지막으로 수정된 시간"
			}
	];
	
	// init 함수 등록
	this.init = function(){
		if(!myTreeView){
			myTreeView = new dhtmlXTreeView("commCodeTree");
			myTreeView.setIconset("font_awesome");
		}
		doLoadCommCodeTree();
		bindEvent();
		
		resize();
		$(window).resize(resize);
	}
	
	// Bind Event
	function bindEvent(){
		$("#addCommcode").off("click").on("click", function(){
			var selectedId = myTreeView.getSelectedId();
			if(!selectedId) selectedId = "ROOT";
			myTreeView.deleteItem(addObjId);
			myTreeView.addItem(addObjId, "", selectedId);
		});

		$("#deleteCommcode").off("click").on("click", function(){
			var selectedId = myTreeView.getSelectedId();
			var selectedNm = myTreeView.getItemText(selectedId);
			var parentId = myTreeView.getParentId(selectedId);
			if(!selectedId) {alert("삭제할 항목을 선택해주십시오."); return;}			
			if(addObjId != selectedId){
				if (confirm("해당코드(" + selectedNm + ")를 삭제 하시겠습니까?")) {				
					var data = {};
					data.prtcd = parentId;
					data.itemcd = selectedId;
					Ajax.post("AdminCommCodeDelete", data, doReflash);
					myTreeView.deleteItem(selectedId);
				}
			}else{
				clearForm();
				myTreeView.deleteItem(selectedId);
			}
		});
		
		$("#saveCommcode").off("click").on("click", function(){
			validationMsg = doValidation(); 
			if(validationMsg) { alert(validationMsg); return; }
			var data = {};
			$(".edit_code_frm").find("input, textarea, select").each(function(){
				data[$(this).attr("id")] = $(this).val();
			});
			data.saveState = thisObj.saveState;
			//저장//
			SAMILCOMMON.progressOn();
			Ajax.post("AdminCommCodeSave", data, doReflash);
		});
	}
	
	function doValidation(){
		msg = "";
		for(var i in formObj){
			if(formObj[i].require && !$("#"+formObj[i].id).val()){
				msg += "필수입력값입니다.(" + formObj[i].label +")\n";
			}else if(formObj[i].minlength && formObj[i].minlength > $("#"+formObj[i].id).val().length){
				msg += formObj[i].label +"의 길이는 최소" + formObj[i].minlength + "글자 이상입니다.\n";
			}
		}
		return msg;
	}
	
	function doReflash(data){
		if(data.result){
			clearForm();
			myTreeView.clearAll();
			myTreeView = null;
			myTreeView = new dhtmlXTreeView("commCodeTree");
			myTreeView.setIconset("font_awesome");
			doLoadCommCodeTree(data);
			SAMILCOMMON.progressOff();
		} else {
			alert(data.error.errorMessage);
			SAMILCOMMON.progressOff();
			return false;
		}
	}
	
	function doLoadCommCodeTree(prcdData){
		SAMILCOMMON.progressOn();
		Ajax.post("AdminCommCodeTree", null, function (data) {
			if(data.result){
				var list = data.data.list;
				myTreeView.addItem("ROOT", "공통코드관리");
				for (var i in list){
					myTreeView.addItem(list[i].itemcd, list[i].itemnm, list[i].parcd);	
				}
				myTreeView.openItem("ROOT", true);
				SAMILCOMMON.progressOff();
			} else {
				alert(data.error.errorMessage);
				SAMILCOMMON.progressOff();
				return false;
			}
			myTreeView.attachEvent("onSelect", function(id, mode){
				if(mode && id != "ROOT" && id != addObjId){
		 			var param = {};
		 			param.prtcd = myTreeView.getParentId(id);
		 			param.itemcd = id;
		 			formObj[1].readonly = true;
		 			makeForm();
		 			doLoadCommCodeForm(param);
		 			thisObj.saveState = "U";
				} else if (mode && id == "ROOT") {
					clearForm();
				} else if (mode && id == addObjId){
					var pid = myTreeView.getParentId(id);
					formObj[1].readonly = false;
					makeForm();
					setAddItemToForm(pid);
					thisObj.saveState = "I";
				}
			});
			
			myTreeView.attachEvent("onAddItem", function (id, text, pid, index){
				myTreeView.openItem(pid, true);
				myTreeView.selectItem(id);
				makeForm();
				setAddItemToForm(pid);
				thisObj.saveState = "I";
			});
			
			if(prcdData){
				var parcds = []
				var itemcd = prcdData.data.itemcd;
				var parcd = prcdData.data.parcd;
				while(parcd){
					parcds.unshift(parcd);
					parcd = myTreeView.getParentId(parcd);
				}
				for(var i in parcds) myTreeView.openItem(parcds[i], true);
				try{
					myTreeView.selectItem(itemcd, true);
				}catch(err){ console.log("item is deleted");}
			} else if(data.data.list.length > 0) myTreeView.selectItem(myTreeView.getSubItems("ROOT")[0], true);
		});
	}
	
	function doLoadCommCodeForm(param){
		Ajax.post("AdminCommCodeDetail", param,	function (data) {
			var vo = data.data.vo;
			var result = "";
			for (var i in vo){
				$("#"+i).val(vo[i]);
			}
//			$("#parcd, #updempno, #upddt").parent().addClass("non_border");
		});		
	}
	
	function makeForm(){
		var _h = [];
		var _target = $("#edit_code_frm");
		_h.push('<div class="">');
		
		for(var i in formObj){
			_h.push('<dl class="dc_frm">');
			_h.push('<dt>'+ formObj[i].label +'</dt>');
			_h.push('<dd class="input_frm">');
			_h.push('<div class="frm_atr">');
			
			if(formObj[i].type == "textarea"){
				_h.push('<span class="data_textarea">');
			}else{
				_h.push('<span class="data_input');
				if(formObj[i].readonly) _h.push(' non_border');
				_h.push('">');
			}	
			
			if(formObj[i].type == "textarea"){
				_h.push('<textarea id="descp"');
				if(formObj[i].maxlength) _h.push(' maxLength="' + formObj[i].maxlength  + '"');
				_h.push('/>');
			}else if(formObj[i].type == "select"){
				_h.push('<select id="'+ formObj[i].id + '">');
				_h.push('<option value="Y">Y</option><option value="N">N</option>');
				_h.push('</select>');
			}else{
				_h.push('<input type="text" id="' + formObj[i].id +'"');
				if(formObj[i].readonly) _h.push(' readonly="' + formObj[i].readonly  + '"');
				if(formObj[i].maxlength) _h.push(' maxLength="' + formObj[i].maxlength  + '"');
//				if(formObj[i].type == "number") _h.push(' pattern="([0-9]{' + formObj[i].maxlength  + '})" inputmode="numeric"');
				_h.push('/>');
			}
			_h.push('</span>');
			_h.push('<span class="data_discript">'+ formObj[i].descript +'</span>');
			_h.push('</div>');
			_h.push('</dd>');
			_h.push('</dl>');
		}
		_h.push('</div>');
		_target.empty().append(_h.join(""));
		
		for(var i in formObj){
			if(formObj[i].type == "number"){
				GF.inputOnlyNumber($("#"+formObj[i].id));
			}
		}
	}
	
	function clearForm(){
//		$(".edit_code_frm").find("input, textarea").each(function(){
//			$(this).val("");
//		});
		$("#edit_code_frm").empty();
	}
	
	function setAddItemToForm(pid){
		$("#parcd").val(pid);
	}
	
	function resize(){
		myTreeView.setSizes();
	}
	
	$(document).ready(function(){
		thisObj.init();
	});
})();