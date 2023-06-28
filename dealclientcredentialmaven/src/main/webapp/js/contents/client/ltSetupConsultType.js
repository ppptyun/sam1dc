var GRID_OBJ = (function(){
	// 변수 선언
	var EMPTY_COMBO_OPTION = "_empty_";
	var m_gridObj = null;
	var m_gridDefinition = {
			parent:"div-grid-area",
			image_path:"./js/dhtmlx/codebase/imgs/",
			skin:"dhx_skyblue",
			columns:[  
						{id:"conscd", label:["Select", "#rspan"], type:"coro", align:"left", valign:"middle", width:"100", sort: "str", style:"background-color:#e7e7e8"},
						{id:"pdtinfo", label:["Product", "#rspan"], type:"ro", align:"left", valign:"middle", width:"250", sort: "str"},
			         	/*{id:"ctgnm", label:["Product", "Cagegory"], type:"ro", align:"left", valign:"middle", widthP:"12", sort: "str"},*/
			         	{id:"prjtcd", label:["Project", "Code"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"prjtnm", label:["#cspan", "Name"], type:"ro", align:"left", valign:"middle", width:"270", sort: "str"},
			         	{id:"hqnm", label:["#cspan", "본부"], type:"ro", align:"left", valign:"middle", width:"150", sort: "str"},
			         	{id:"ptrempnm", label:["#cspan", "EL"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"mgrempnm", label:["#cspan", "TM"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"chrempnm", label:["#cspan", "본부담당자"], type:"ro", align:"center", valign:"middle", width:"100", sort: "str"},
			         	{id:"upddt", label:["최종 수정일", "#rspan"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"empty", label:["", "#rspan"], type:"ro"}
					]
		} 

	// 초기화
	function doInit(){
		// Grid
		if(!m_gridObj){
			m_gridObj = new dhtmlXGridObject(m_gridDefinition);
			//m_gridObj.setInitWidthsP("8,12,6,19,9,18,10,5,5,8");
			//m_gridObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px;font-size:12px", null, null, null);
			m_gridObj.enableSmartRendering(true, 200);
			m_gridObj.enableAlterCss("","");
		}
		
		var consultCombo = m_gridObj.getCombo(0);
		consultCombo.put(EMPTY_COMBO_OPTION, "선택해주세요.");
		for (var i=0; i<__consult_code_list.length; i++) {
			if(__consult_code_list[i].code != "100301") {
				consultCombo.put(__consult_code_list[i].code, __consult_code_list[i].name);	
			}
		}
		
		bindEvent();
		doLoadLeagueTable();
		
		resize();
		
		$(window).resize(resize);
	};
	
	// Bind Event
	function bindEvent(){
		$("#btn_guidance").off("click").on("click", function(){
			window.open(SERVICE_CONFIG.leaguetable.guidanceuri, "leaguetableguide", "width=1000, height=600, scrollbars=yes, toolbar=no, menubar=no, location=no, resizable=yes");
		})
		$("#btn_consult_save").off("click").on("click", function(){
			var rowIds = m_gridObj.getChangedRows(true);
			if (rowIds) {
				var items = [];
				var arrRowIds = rowIds.split(",");
				var valid = true;
				for (var i=0; i<arrRowIds.length; i++) {
					var data = [];
					var rowId = arrRowIds[i];
					var prjtcd = m_gridObj.cells(rowId, m_gridObj.getColIndexById("prjtcd")).getValue();
					var typecd = m_gridObj.cells(rowId, m_gridObj.getColIndexById("conscd")).getValue();
					if (typecd == "") {
						continue;
					}
					if (typecd == EMPTY_COMBO_OPTION) {
						valid = false;
					}
					data.push(prjtcd);
					data.push(typecd);
					items.push(data.join("^"));
				}
				
				if(!valid) {
					alert("선택할 수 없는 항목이 포함되었습니다.");
					return false;
				}
				
				// 서버 반영
				Ajax.post("LtSetupConsultTypeSave", {items:items.join("|")}, function(serverData){
					if (serverData.result) {
						alert("저장되었습니다.");
						doLoadLeagueTable();
					} else {
						alert(serverData.error.errorMessage);
						return false;
					}
				});
			} else {
				alert("변경된 항목이 없습니다.");
				return false;
			}
		});
	}

	
	// 목록 조회
	function doLoadLeagueTable(){
		m_gridObj.clearAll();
		SAMILCOMMON.transparencyMsgOff();
		SAMILCOMMON.progressOn();
		Ajax.post("LtSetupConsultTypeList", {}, function(serverData){
			if (serverData.result) {
				var list = serverData.data.list;
				if(list.length == 0){
					SAMILCOMMON.transparencyMsgOn("div-grid-area", "대상 프로젝트가 존재하지 않습니다.");	
				}else{
					for(var i=0; i<serverData.data.list.length; i++){
						serverData.data.list[i].pdtinfo = serverData.data.list[i].pdtcd + "/" + serverData.data.list[i].pdtnm;
						serverData.data.list[i].conscd = EMPTY_COMBO_OPTION;
					}
					m_gridObj.parse(list, "js");
					
					// Style 설정
					for(var i=0; i<m_gridObj.getRowsNum(); i++){
						var rid = m_gridObj.getRowId(i);
						setCellStyle(rid, ["conscd"], "background-color:#f6dabf");
					}
				}
				
				SAMILCOMMON.progressOff();
			} else {
				alert(serverData.error.errorMessage);
				SAMILCOMMON.progressOff();
				return false;
			}
		});
	};
	
	function setCellStyle(rid, colArr, style){
		for(var i=0; i<colArr.length; i++){
			m_gridObj.setCellTextStyle(rid, m_gridObj.getColIndexById(colArr[i]), style);
		}	
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
		doInit();
	});
}());