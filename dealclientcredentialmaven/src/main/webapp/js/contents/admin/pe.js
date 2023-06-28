(function(){
	// 변수 선언
	var thisObj = this;
	var ATTR_NEW_ROW = "new";
	var EMPTY_COMBO_OPTION = "_empty_";
	/*var codeidx = 0;
	var chckidx = 5;*/
	var m_gridDefinition = {
		parent:"div-grid-area",
		image_path:"./js/dhtmlx/codebase/imgs/",
		skin:"dhx_skyblue",
		columns:[
		         	{id:"inducd", label:["산업코드"], type:"ro", align:"center", valign:"middle", width:"0", sort: "str"},
		         	{id:"glinducd", label:["그룹코드"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
		         	{id:"glindunmk", label:["그룹명"], type:"ro", align:"left", valign:"middle", width:"300", sort: "str"},
		         	{id:"inducd", label:["산업코드"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
		         	{id:"indunm", label:["산업명"], type:"ro", align:"left", valign:"middle", width:"300", sort: "str"},
		         	{id:"chkval", label:["선택"], type:"ch", align:"center", valign:"middle", width:"40", sort: "str"},
		         	{id:"empty", label:[""]}
				]
	};
	var m_gridObj = null;
	
	// init 함수 등록
	this.init = function(){
		// Grid
		if(!m_gridObj){
			m_gridObj = new dhtmlXGridObject(m_gridDefinition);
			/*m_gridObj.setInitWidthsP("0,6,25,6,25,4");*/
			m_gridObj.setColumnHidden(m_gridObj.getColIndexById("inducd"), true);
			m_gridObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px;font-size:12px", null, null, null);
		}
		
		bindEvent();
		doLoadList();
		
		resize();
		$(window).resize(resize);
	}
	
	// Bind Event
	function bindEvent(){
		// 저장
		$("#btn_save").off("click").on("click", function(){
			var items = [];
			var rowIds = m_gridObj.getCheckedRows(m_gridObj.getColIndexById("chkval"));
			if (rowIds) {
				var arrRowIds = rowIds.split(",");
				for (var i=0; i<arrRowIds.length; i++) {
					var rowId = arrRowIds[i];
					var inducd = m_gridObj.cells(rowId, m_gridObj.getColIndexById("inducd")).getValue();
					items.push(inducd);
				}
			}
			// 서버 반영
			Ajax.post("AdminPESave", {items:items.join("|")}, function(serverData){
				if (serverData.result) {
					alert("저장되었습니다.");
					doLoadList();
				} else {
					alert(serverData.error.errorMessage);
					return false;
				}
			});
		});
	}
	
	// 목록 조회
	function doLoadList(){
		m_gridObj.clearAll();
		Ajax.post("AdminPEList", {}, function(serverData){
			if (serverData.result) {
				var _list = serverData.data.list;
				m_gridObj.parse(_list, "js");
				m_gridObj.setColumnHidden(m_gridObj.getColIndexById("inducd"), true);
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