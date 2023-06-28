(function(){
	// 변수 선언
	var thisObj = this;
	var addObjId = "$_new";
	var ATTR_NEW_ROW = "is_new_added";
	var EMPTY_COMBO_OPTION = "_empty_";
	var saveState = "U";//I-신규/U-수정
	var selectMenuId = "";
	var myTreeView = null;
	var m_gridRoleObj = null;
	var m_gridRoleCombo = [];
	var highestId;
	var m_gridRoleDefinition = {
		parent:"edit_role_grid",
		image_path:"./js/dhtmlx/codebase/imgs/",
		skin:"dhx_skyblue",
		columns:[
		         	{id:"originrolecd", label:["롤 코드(origin)"], type:"ro", align:"center", valign:"middle", width:"100", sort: "str"},
		         	{id:"rolecd", label:["롤 이름"], type:"coro", align:"left", valign:"middle", width:"300", sort: "str"}
				]
	};
	var formObj = [
			{
				id : "upmenuid"
				, label : "상위메뉴 ID"
				, type : "text"
				, minlength : 1
				, maxlength : 7
				, require : false
				, readonly : true
				, descript : "해당 메뉴가 소속된 메뉴의 ID"
			}
			,{	id : "menuid"
				, label : "메뉴 ID"
				, type : "text"
				, minlength : 1
				, maxlength : 7
				, require : true
				, readonly : false
				, descript : "현재 선택한 메뉴의 ID"
			}
			,{  id : "menunm"
				, label : "메뉴명"
				, type : "text"
				, minlength : 1
				, maxlength : 200
				, require : false
				, readonly : false
				, descript : "메뉴가 표현되는 이름"
			}
			,{  id : "url"
				, label : "URL"
				, type : "text"
				, minlength : 1
				, maxlength : 200
				, require : false
				, readonly : false
				, descript : "메뉴의 페이지 URL"
			}
			,{  id : "sort"
				, label : "정렬순서"
				, type : "number"
				, minlength : 1
				, maxlength : 7
				, require : false
				, readonly : false
				, descript : "정렬되는 순서"
			}
	];

	// init 함수 등록
	this.init = function(){
		if(!myTreeView){
			myTreeView = new dhtmlXTreeView("menuTree");
			myTreeView.setIconset("font_awesome");
		}

		if(!m_gridRoleObj){
			m_gridRoleObj = new dhtmlXGridObject(m_gridRoleDefinition);
			m_gridRoleObj.setInitWidthsP("0,100");
			m_gridRoleObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px; font-size:12px", null, null, null);
		}
		
		var roleCombo = m_gridRoleObj.getCombo(1);
		roleCombo.put(EMPTY_COMBO_OPTION, "선택해주세요.");
		for (var i=0; i<__role_code_list.length; i++) {
			roleCombo.put(__role_code_list[i].code, __role_code_list[i].name);
		}
		
		m_gridRoleObj.attachEvent("onEditCell", function(st, rId, cInd, nV){
			var oV = m_gridRoleObj.cells(rId, m_gridRoleObj.getColIndexById("originrolecd")).getValue();
			if(oV != EMPTY_COMBO_OPTION){
				if(st==2 && oV != nV ){
					m_gridRoleObj.setRowTextBold(rId);
				}else if (st==2){
					m_gridRoleObj.setRowTextNormal(rId);
					m_gridRoleObj.cells(rId, cInd).cell.wasChanged=false;
				}
			}
			return true;
		});
		
		m_gridRoleObj.setColumnHidden(m_gridRoleObj.getColIndexById("originrolecd"), true);
		
		doLoadRoleList();
		doLoadMenuTree();
		bindEvent();
		
		resize();
		$(window).resize(resize);
	}


//	// Bind Event
	function bindEvent(){
		$("#addMenu").off("click").on("click", function(){
			var selectedId = myTreeView.getSelectedId();
			if(!selectedId) selectedId = "ROOT";
			myTreeView.deleteItem(addObjId);
			myTreeView.addItem(addObjId, "", selectedId);
		});

		$("#deleteMenu").off("click").on("click", function(){
			var selectedId = myTreeView.getSelectedId();
			var selectedNm = myTreeView.getItemText(selectedId);
			var parentId = myTreeView.getParentId(selectedId);
			if(!selectedId) {alert("삭제할 항목을 선택해주십시오."); return;}
			if(addObjId != selectedId){
				if (confirm("해당메뉴(" + selectedNm + ")를 삭제 하시겠습니까?")) {
					var data = {};
					data.upmenuid = parentId;
					data.menuid = selectedId;
					Ajax.post("AdminMenuDelete", data, doReflash);
					myTreeView.deleteItem(selectedId);
				}
			}else{
				clearForm();
				myTreeView.deleteItem(selectedId);
			}
		});

		$("#saveMenu").off("click").on("click", function(){
			validationMsg = doValidation();
			if(validationMsg) { alert(validationMsg); return; }
			var data = {};
			$("#edit_menu_frm").find("input").each(function(){
				data[$(this).attr("id")] = $(this).val();
			});
			data.saveState = thisObj.saveState;
			//저장//
			Ajax.post("AdminMenuSave", data, doReflash);
		});
		
		$("#addRole").off("click").on("click", function(){
			var newId = (new Date()).valueOf();
			m_gridRoleObj.addRow(newId, EMPTY_COMBO_OPTION+","+EMPTY_COMBO_OPTION);
			m_gridRoleObj.cells(newId, m_gridRoleObj.getColIndexById("originrolecd")).setAttribute(ATTR_NEW_ROW, true);
			m_gridRoleObj.setRowTextStyle(newId, "color: green; font-weight:bold;");
		});
		
		// 메뉴 롤 저장
		$("#saveRole").off("click").on("click", function(){
			var rowIds = m_gridRoleObj.getChangedRows(true);
			if (rowIds) {
				var items = [];
				var arrRowIds = rowIds.split(",");
				for (var i=0; i<arrRowIds.length; i++) {
					var data = [];
					var rowId = arrRowIds[i];
					var codeCell = m_gridRoleObj.cells(rowId, m_gridRoleObj.getColIndexById("originrolecd"));
					if (codeCell.getAttribute(ATTR_NEW_ROW)) {
						data.push("I");//신규
					} else {
						data.push("U");//수정
					}
					var menuid = thisObj.selectMenuId;
					var originrolecd = codeCell.getValue();
					var roleCd = m_gridRoleObj.cells(rowId, m_gridRoleObj.getColIndexById("rolecd")).getValue();
					if (roleCd == EMPTY_COMBO_OPTION) {
						alert("롤을 선택해주세요.");
						return false;
					}
					data.push(menuid);
					data.push(roleCd);
					data.push(originrolecd);
					items.push(data.join("^"));
				}
				// 서버 반영
				Ajax.post("AdminMenuMappedRolesSave", {items:items.join("|")}, function(serverData){
					if (serverData.result) {
						alert("저장되었습니다.");
						doLoadMenuMappedRolesList(thisObj.selectMenuId);
					} else {
						alert(serverData.error.errorMessage);
						return false;
					}
				});
			} else {
				alert("변경 혹은 추가된 항목이 없습니다.");
				return false;
			}
		});
		
		// 메뉴 롤 삭제
		$("#deleteRole").off("click").on("click", function(){
			var rowId = m_gridRoleObj.getSelectedRowId();
			if (rowId) {
				var codeCell = m_gridRoleObj.cells(rowId, m_gridRoleObj.getColIndexById("originrolecd"));
				if (codeCell.getAttribute(ATTR_NEW_ROW)) {
					m_gridRoleObj.deleteRow(rowId);
				} else {
					if (confirm("정말로 삭제 하시겠습니까?")) {
						var items = [];
						var menuid = thisObj.selectMenuId;
						var rolecd = m_gridRoleObj.cells(rowId, m_gridRoleObj.getColIndexById("rolecd")).getValue();
						items.push(menuid+"^"+rolecd);
						// 서버 반영
						Ajax.post("AdminMenuMappedRoleDelete", {items:items.join("|")}, function(serverData){
							if (serverData.result) {
								alert("삭제되었습니다.");
								m_gridRoleObj.deleteRow(rowId);
							} else {
								alert(serverData.error.errorMessage);
								return false;
							}
						});
					}
				}
			} else {
				alert("삭제할 항목을 선택해주세요.");
				return false;
			}
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
			myTreeView = new dhtmlXTreeView("menuTree");
			myTreeView.setIconset("font_awesome");
			doLoadMenuTree(data);
		} else {
			alert(data.error.errorMessage);
			return false;
		}
	}

	function doLoadMenuTree(prcdData){
		Ajax.post("AdminMenuTree", null, function (data) {
			if(data.result){
				var list = data.data.list;
				myTreeView.addItem("ROOT", "메뉴관리");
				highestId = 0;
				for (var i in list){
					myTreeView.addItem(list[i].menuid, list[i].menunm, GF.replaceNullValue(list[i].upmenuid,""));
					if(Number(highestId) < list[i].menuid) highestId = list[i].menuid;
				}
				myTreeView.openItem("ROOT", true);
			} else {
				alert(data.error.errorMessage);
				return false;
			}

			myTreeView.attachEvent("onSelect", function(id, mode){
				if(mode && id != "ROOT" && id != addObjId){
		 			var param = {};
		 			param.upmenuid = myTreeView.getParentId(id);
		 			param.menuid = id;
		 			thisObj.selectMenuId = id;
		 			makeForm();
		 			doLoadMenuForm(param);
		 			thisObj.saveState = "U";
				} else if (mode && id == "ROOT") {
					clearForm();
				} else if (mode && id == addObjId){
					var pid = myTreeView.getParentId(id);
					makeForm();
					setAddItemToForm(pid);
					m_gridRoleObj.clearAll();
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
				var upmenuids = []
				var menuid = prcdData.data.menuid;
				var upmenuid = prcdData.data.upmenuid;
				while(upmenuid){
					upmenuids.unshift(upmenuid);
					upmenuid = myTreeView.getParentId(upmenuid);
				}
				for(var i in upmenuids) myTreeView.openItem(upmenuids[i], true);
				try{
					myTreeView.selectItem(menuid);
				}catch(err){ console.log("item is deleted");}
			}else if(data.data.list.length > 0) myTreeView.selectItem(myTreeView.getSubItems("")[0], true);
		});
	}

	function doLoadMenuForm(param){
		Ajax.post("AdminMenuDetail", param,	function (data) {
			var vo = data.data.vo;
			var result = "";
			for (var i in vo){
				$("#"+i).val(vo[i]);
			}
		});

		doLoadMenuMappedRolesList();
	}

	function doLoadMenuMappedRolesList(){
		m_gridRoleObj.clearAll();
		var selectedId = myTreeView.getSelectedId();
		Ajax.post("AdminMenuMappedRolesList", {menuid:selectedId}, function(serverData){
			if (serverData.result) {
				var rolelist = serverData.data.list;
				m_gridRoleObj.parse(rolelist, "js");
			} else {
				alert(serverData.error.errorMessage);
				return false;
			}
		});
	}



	function doLoadRoleList(){
		Ajax.post("AdminRoleList", {}, function(serverData){
			if (serverData.result) {
				m_gridRoleCombo = serverData.data.list;
			} else {
				alert(serverData.error.errorMessage);
				return false;
			}
		});
	}

	function makeForm(){
		var _h = [];
		var _target = $("#edit_menu_frm");
		_h.push('<div class="">');

		for(var i in formObj){
			_h.push('<dl class="dc_frm">');
			_h.push('<dt>'+ formObj[i].label +'</dt>');
			_h.push('<dd class="input_frm">');
			_h.push('<div class="frm_atr">');

			_h.push('<span class="data_input');
			if(formObj[i].readonly) _h.push(' non_border');
			_h.push('">');

			_h.push('<input type="text" id="' + formObj[i].id +'"');
			if(formObj[i].readonly) _h.push(' readonly="' + formObj[i].readonly  + '"');
			if(formObj[i].maxlength) _h.push(' maxLength="' + formObj[i].maxlength  + '"');
			_h.push('/>');

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
		$("#edit_menu_frm").empty();
	}

	function setAddItemToForm(pid){
		$("#upmenuid").val(pid);
		//$("#menuid").val(Number(highestId)+1);
	}
	
	function resize(){
		
		// Grid Resize
		var $grid	= $("#edit_role_grid");
		var height	= $grid.parent().height() - 44;
		var width	= $grid.parent().width() - 2;
		$grid.height(height);
		$grid.width(width);
		$grid.find(".xhdr").width(width);
		$grid.find(".objbox").width(width);
		$grid.find(".objbox").height(height - $grid.find(".xhdr").height());
		
		// TreeGrid Resize 
		myTreeView.setSizes();
		
		
		//edit_role_grid
	}

	$(document).ready(function(){
		
		thisObj.init();
	});
})();
