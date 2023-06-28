(function(){
	// 변수 선언
	var _list = {};
	var thisObj = this;
	var ATTR_NEW_ROW = "new";
	var EMPTY_COMBO_OPTION = "_empty_";
	var compidx = 0;
	var cousidx = 6;
	var m_gridDefinition = {
		parent:"div-grid-area",
		image_path:"./js/dhtmlx/codebase/imgs/",
		skin:"dhx_skyblue",
		columns:[
		         	{id:"compcd", label:["기업코드"], type:"ro", align:"center", valign:"middle", width:"60", sort: "str"},
		         	{id:"comphannm", label:["기업한글명"], type:"ed", align:"left", valign:"middle", width:"300", sort: "str"},
		         	{id:"compengnm", label:["기업영문명"], type:"ed", align:"left", valign:"middle", width:"300", sort: "str"},
		         	{id:"financeyn", label:["재무전략자문"], type:"ch", align:"center", valign:"middle", width:"90", sort: "str"},
		         	{id:"audityn", label:["회계자문"], type:"ch", align:"center", valign:"middle", width:"90", sort: "str"},
		         	{id:"lawyn", label:["법률자문"], type:"ch", align:"center", valign:"middle", width:"90", sort: "str"},
		         	{id:"useyn", label:["사용여부"], type:"coro", align:"center", valign:"middle", width:"90", sort: "str"}
				]
	};
	var m_gridObj = null;
	var useCombo = null;
	
	// init 함수 등록
	this.init = function(){
		// Grid
		if(!m_gridObj){
			m_gridObj = new dhtmlXGridObject(m_gridDefinition);
			m_gridObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px;font-size:12px", null, null, null);
		}
		
		useCombo = m_gridObj.getCombo(cousidx);
		useCombo.put("1", "Yes");
		useCombo.put("0", "No");
		
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
			m_gridObj.addRow(newId, ATTR_NEW_ROW+",,,1,1,1,1");
		})
		
		// 삭제
		$("#btn_del").off("click").on("click", function(){
			var rowId = m_gridObj.getSelectedRowId();
			if (rowId) {
				var codeCell = m_gridObj.cells(rowId, compidx);
				var compcd = codeCell.getValue();
				if (ATTR_NEW_ROW == compcd) {
					m_gridObj.deleteRow(rowId);
					return false;
				}
				// 서버 반영
				if (confirm("사용하지 않으시겠습니까?")) {
					Ajax.post("AdminConsultDel", {compcd:compcd}, function(serverData){
						if (serverData.result) {
							alert("처리되었습니다.");
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
//			console.log(rowIds);
			var items = [];
			if(rowIds) {
				var arrRowIds = rowIds.split(",");
				for (var i=0; i<arrRowIds.length; i++) {
					var rowId = arrRowIds[i];
					var param = {};
					param.compcd = m_gridObj.cells(rowId, m_gridObj.getColIndexById("compcd")).getValue();
					param.comphannm = m_gridObj.cells(rowId, m_gridObj.getColIndexById("comphannm")).getValue();
					param.compengnm = m_gridObj.cells(rowId, m_gridObj.getColIndexById("compengnm")).getValue();
					param.financeyn = m_gridObj.cells(rowId, m_gridObj.getColIndexById("financeyn")).getValue();
					param.audityn = m_gridObj.cells(rowId, m_gridObj.getColIndexById("audityn")).getValue();
					param.lawyn = m_gridObj.cells(rowId, m_gridObj.getColIndexById("lawyn")).getValue();
					param.useyn = m_gridObj.cells(rowId, m_gridObj.getColIndexById("useyn")).getValue();
					
					if (param.comphannm == "") {
						alert("기업명을 입력해주세요.");
						m_gridObj.selectRow(rowId);
						return false;
					}
					
					items.push(param);
				}
			}
			
			// 서버 반영
			if(items.length > 0) {
				Ajax.post("AdminConsultSave", {items:items}, function(serverData){
					if (serverData.result) {
						alert("저장되었습니다.");
						doLoadList();
					} else {
						alert(serverData.error.errorMessage);
						return false;
					}
				});
			}
		});
	}
	
	// 목록 조회
	function doLoadList(){
		m_gridObj.clearAll();
		Ajax.post("AdminConsultList", {}, function(serverData){
			if (serverData.result) {
				_list = serverData.data.list;
				m_gridObj.parse(_list, "js");
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
		$grid.find(".xhdr").height("22px");
		$grid.find(".objbox").width("100%");
		$grid.find(".objbox").height(height - $grid.find(".xhdr").height());
	}
	
	$(document).ready(function(){
		thisObj.init();
	});
})();