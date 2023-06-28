(function(){
	// 변수 선언
	var thisObj = this;
	var ATTR_NEW_ROW = "new";
	var EMPTY_COMBO_OPTION = "_empty_";
	var roleidx = 0;
	var coroidx = 1;
	var coauidx = 2;
	var m_gridDefinition = {
		parent:"div-grid-area",
		image_path:"./js/dhtmlx/codebase/imgs/",
		skin:"dhx_skyblue",
		columns:[
		         	{id:"rolecd", label:["롤코드"], type:"ro", align:"left", valign:"middle", width:"100", sort: "str"},
		         	{id:"rolecd", label:["대상"], type:"coro", align:"left", valign:"middle", width:"300", sort: "str"},
		         	{id:"edityn", label:["편집여부"], type:"coro", align:"left", valign:"middle", width:"100", sort: "str"},
		         	{id:"upddt", label:["적용일자"], type:"ro", align:"center", valign:"middle", width:"100", sort: "str"},
		         	{id:"empty", label:[""]}
				]
	};
	var m_gridObj = null;
	var roleCombo = null;
	var authCombo = null;
	
	// init 함수 등록
	this.init = function(){
		// Grid
		if(!m_gridObj){
			m_gridObj = new dhtmlXGridObject(m_gridDefinition);
			/*m_gridObj.setInitWidthsP("0,20,6,10");*/
			m_gridObj.setColumnHidden(roleidx, true);
			m_gridObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px;font-size:12px", null, null, null);
		}
		
		roleCombo = m_gridObj.getCombo(coroidx);
		roleCombo.put(EMPTY_COMBO_OPTION, "선택해주세요.");
		for (var i=0; i<__role_code_list.length; i++) {
			roleCombo.put(__role_code_list[i].code, __role_code_list[i].name);
		}
		
		
		authCombo = m_gridObj.getCombo(coauidx);
		authCombo.put("Y", "Yes");
		authCombo.put("N", "No");
		
		bindEvent();
		doLoadList();
		
		resize();
		$(window).resize(resize);
	}
	
	// Bind Event
	function bindEvent(){
		// 추가
		$("#btn_add").off("click").on("click", function(){
			var newId = (new Date()).valueOf();
			m_gridObj.addRow(newId, ATTR_NEW_ROW+","+EMPTY_COMBO_OPTION+",Y,");
		});
		
		// 삭제
		$("#btn_del").off("click").on("click", function(){
			var rowId = m_gridObj.getSelectedRowId();
			if (rowId) {
				var codeCell = m_gridObj.cells(rowId, roleidx);
				var rolecd = codeCell.getValue();
				if (ATTR_NEW_ROW == rolecd) {
					m_gridObj.deleteRow(rowId);
					return false;
				}
				// 서버 반영
				if (confirm("정말로 삭제하시겠습니까?")) {
					Ajax.post("AdminEditAuthDel", {rolecd:rolecd}, function(serverData){
						if (serverData.result) {
							alert("삭제되었습니다.");
							doLoadList();
						} else {
							alert(serverData.error.errorMessage);
							return false;
						}
					});					
				}
			} else {
				alert("선택된 항목이 없습니다.");
				return false;
			}
		});
		
		// 저장
		$("#btn_save").off("click").on("click", function(){
			var rowIds = m_gridObj.getChangedRows(true);
			if (rowIds) {
				var items = [];
				var arrRowIds = rowIds.split(",");
				for (var i=0; i<arrRowIds.length; i++) {
					var rowId = arrRowIds[i];
					var codeCell = m_gridObj.cells(rowId, roleidx);
					var roleCell = m_gridObj.cells(rowId, coroidx);
					var authCell = m_gridObj.cells(rowId, coauidx);
					
					var originrolecd = codeCell.getValue();
					var rolecd = roleCell.getValue();
					if (rolecd == EMPTY_COMBO_OPTION) {
						alert("롤을 선택해주세요.");
						return false;
					}
					var edityn = authCell.getValue();
					items.push(originrolecd+"^"+rolecd+"^"+edityn);
				}
				// 서버 반영
				Ajax.post("AdminEditAuthSave", {items:items.join("|")}, function(serverData){
					if (serverData.result) {
						alert("저장되었습니다.");
						doLoadList();
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
	}
	
	// 목록 조회
	function doLoadList(){
		m_gridObj.clearAll();
		Ajax.post("AdminEditAuthList", {}, function(serverData){
			if (serverData.result) {
				var _list = serverData.data.list;
				m_gridObj.parse(_list, "js");
				m_gridObj.setColumnHidden(roleidx, true);
			} else {
				alert(serverData.error.errorMessage);
				return false;
			}
		});
	}
	
	function resize(){
		var height	= $("#dc_con_article").height() - 70;	// button + padding
		var width	= $("#dc_con_article").width() - 40;	// padding
		var $grid	= $("#div-grid-area");
		
		$grid.height(height);
		$grid.width("100%");
		$grid.find(".xhdr").width("100%");
		$grid.find(".objbox").width("100%");
		$grid.find(".objbox").height(height - $grid.find(".xhdr").height());
	}
	
	$(document).ready(function(){
		thisObj.init();
	});
})();