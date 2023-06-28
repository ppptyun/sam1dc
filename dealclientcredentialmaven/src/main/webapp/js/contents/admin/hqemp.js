(function(){
	// 변수 선언
	var thisObj = this;
	var ATTR_NEW_ROW = "is_new_added";
	var m_gridHqempDefinition = {
		parent:"div-grid-area",
		image_path:"./js/dhtmlx/codebase/imgs/",
		skin:"dhx_skyblue",
		columns:[
		         	{id:"grpnm", label:["grouping", "#rspan"], type:"ro", align:"left", valign:"middle", width:"0", sort: "str"},
		         	{id:"hqnm", label:["소속본부", "#rspan"], type:"ro", align:"left", valign:"middle", width:"300", sort: "str"},
		         	{id:"hqcd", label:["본부코드", "#rspan"], type:"ro", align:"left", valign:"middle", width:"0", sort: "str"},
		         	{id:"empno", label:["본부담당자", "사번"], type:"ro", align:"center", valign:"middle", width:"100", sort: "str"},
		         	{id:"empnm", label:["#cspan", "성명"], type:"ro", align:"center", valign:"middle", width:"100", sort: "str"},
		         	{id:"gradnm", label:["#cspan", "직급"], type:"ro", align:"left", valign:"middle", width:"150", sort: "str"},
		         	{id:"empcheck", label:["선택", "#rspan"], type:"ch", align:"center", valign:"middle", width:"40", sort: "str"},
		         	{id:"empty", label:["", "#rspan"], type:"ro"}
				]
	};
	var m_gridHqempObj = null;
	
	// init 함수 등록
	this.init = function(){
		// HqEmp Grid
		if(!m_gridHqempObj){
			m_gridHqempObj = new dhtmlXGridObject(m_gridHqempDefinition);
			/*m_gridHqempObj.setInitWidthsP("0,35,0,5,6,10,4");*/
			m_gridHqempObj.setColumnHidden(2, true);
			m_gridHqempObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px;font-size:12px", null, null, null);
		}
		
		bindEvent();
		doLoadHqempList();
		
		resize();
		$(window).resize(resize);
	}
	
	// Bind Event
	function bindEvent(){
		// 본부 동기화
		$("#btn_hq_sync").off("click").on("click", function(){
			if (confirm("동기화 하시겠습니까?")) {
				Ajax.post("AdminHQEmpSync", {}, function(serverData){
					if (serverData.result) {
						alert("동기화 완료되었습니다.");
						doLoadHqempList();
					} else {
						alert(serverData.error.errorMessage);
						return false;
					}
				});
			}
		});
		
		// 담당자 추가
		$("#btn_emp_add").off("click").on("click", function(){
			var rowId = m_gridHqempObj.getSelectedRowId();
			if (rowId) {
				var codeCell = m_gridHqempObj.cells(rowId, m_gridHqempObj.getColIndexById("hqcd"));
				var selhqcd = codeCell.getValue();
				POPUP.ViewPopup("userSearch", null, function(info) {
					if (info) {
						var empno = info.emplno;
						// 서버 반영
						Ajax.post("AdminHQEmpAdd", {hqcd:selhqcd, empno:empno}, function(serverData){
							if (serverData.result) {
								alert("저장되었습니다.");
								doLoadHqempList();
							} else {
								alert(serverData.error.errorMessage);
								return false;
							}
						});
					}
				});
			} else {
				alert("담당자를 추가할 본부를 선택해주세요.");
				return false;
			}
		});
		
		// 담당자 삭제
		$("#btn_emp_del").off("click").on("click", function(){
			var rowIds = m_gridHqempObj.getChangedRows(true);
			if (rowIds) {
				var arrRowIds = rowIds.split(",");
				var items = [];
				for (var i=0; i<arrRowIds.length; i++) {
					var rowId = arrRowIds[i];
					var chkbox = m_gridHqempObj.cells(rowId, m_gridHqempObj.getColIndexById("empcheck")).getValue();
					if (chkbox == 1) {
						var hqcd = m_gridHqempObj.cells(rowId, m_gridHqempObj.getColIndexById("hqcd")).getValue();
						var empno = m_gridHqempObj.cells(rowId, m_gridHqempObj.getColIndexById("empno")).getValue();
						items.push(hqcd +"^"+ empno);
					}
				}
				if (items.length == 0) {
					alert("선택된 항목이 없습니다.");
					return false;
				}
				// 서버 반영
				if (confirm("정말 삭제하시겠습니까?")) {
					Ajax.post("AdminHQEmpDel", {items:items.join("|")}, function(serverData){
						if (serverData.result) {
							alert("삭제되었습니다.");
							doLoadHqempList();
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
	}
	
	// HqEmp 목록 조회
	function doLoadHqempList(){
		m_gridHqempObj.clearAll();
		Ajax.post("AdminHQEmpList", {}, function(serverData){
			if (serverData.result) {
				var hqemplist = serverData.data.list;
				m_gridHqempObj.parse(hqemplist, "js");
				m_gridHqempObj.setColumnHidden(2, true);
				m_gridHqempObj.customGroupFormat = function(name, count) {
					// return name +" ("+ count +")";
					return name;
				};
				m_gridHqempObj.groupBy(0);
				m_gridHqempObj.forEachRow(function(id){
					var empnoVal = m_gridHqempObj.cells(id, m_gridHqempObj.getColIndexById("empno")).getValue();
					if (empnoVal == "") {
						m_gridHqempObj.cells(id, m_gridHqempObj.getColIndexById("empcheck")).setDisabled(true);
					}
				});
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