var GRID_OBJ = (function(){
	// 변수 선언
	var EMPTY_COMBO_OPTION = "_empty_";
	var m_RowDblClick = false;
	var m_gridObj = null;
	var m_gridDefinition = {
			parent:"div-grid-buy",
			image_path:"./js/dhtmlx/codebase/imgs/",
			skin:"dhx_skyblue",
			columns:[  
						{id:"chrgconfcd", label:["기본정보", "담당자 확인", "확인 여부"], type:"coro", align:"center", valign:"middle", widthP:"8", sort: "str"},
			         	{id:"chrgconfdt", label:["#cspan", "#cspan", "확인일자"], type:"ro", align:"center", valign:"middle", widthP:"8", sort: "str"},
			         	{id:"confnm", label:["#cspan", "Confidential 여부", "#rspan"], type:"ro", align:"center", valign:"middle", widthP:"6", sort: "str"},
			         	{id:"prjtcd", label:["Project", "Code", "#rspan"], type:"ro", align:"center", valign:"middle", widthP:"10", sort: "str"},
			         	{id:"prjtnm", label:["#cspan", "Name", "#rspan"], type:"ro", align:"left", valign:"middle", widthP:"15", sort: "str"},
			         	{id:"hqnm", label:["#cspan", "본부", "#rspan"], type:"ro", align:"left", valign:"middle", widthP:"10", sort: "str"},
			         	{id:"ptrempnm", label:["#cspan", "EL", "#rspan"], type:"ro", align:"center", valign:"middle", widthP:"5", sort: "str"},
			         	{id:"mgrempnm", label:["#cspan", "TM", "#rspan"], type:"ro", align:"center", valign:"middle", widthP:"5", sort: "str"},
			         	{id:"statusnm", label:["상태", "Status", "#rspan"], type:"ro", align:"center", valign:"middle", widthP:"5", sort: "str"},
			         	{id:"upddt", label:["최종 수정일", "#rspan", "#rspan"], type:"ro", align:"center", valign:"middle", widthP:"8", sort: "str"},
			         	{id:"tcomphannm", label:["TARGET", "기업", "Company"], type:"ro", align:"left", valign:"middle", widthP:"10", sort: "str"},
			         	{id:"tindunm", label:["#cspan", "업종", "Biz.Category"], type:"ro", align:"left", valign:"middle", widthP:"10", sort: "str"},
			         	{id:"tdealnm", label:["#cspan", "거래물", "Object"], type:"ro", align:"left", valign:"middle", widthP:"10", sort: "str"},
			         	{id:"scomphannm", label:["SELLER", "기업", "Company"], type:"ro", align:"left", valign:"middle", widthP:"10", sort: "str"},
			         	{id:"sindunm", label:["#cspan", "업종", "Biz.Category"], type:"ro", align:"left", valign:"middle", widthP:"10", sort: "str"},
			         	{id:"bcomphannm", label:["인수후보 WOULD-BE BUYER", "기업", "Company"], type:"ro", align:"left", valign:"middle", widthP:"10", sort: "str"},
			         	{id:"bindunm", label:["#cspan", "업종", "Biz.Category"], type:"ro", align:"left", valign:"middle", widthP:"10", sort: "str"},
			         	{id:"amt", label:["금액 및 성격 VALUE & TYPE", "원화 환산금액", "KRW Amount"], type:"ro", align:"right", valign:"middle", widthP:"10", sort: "str"}
					]
		}

	// 초기화
	function doInit(){
		// Grid
		if(!m_gridObj){
			m_gridObj = new dhtmlXGridObject(m_gridDefinition);
			m_gridObj.setInitWidthsP("8,8,6,10,15,10,5,5,5,8,10,10,10,10,10,10,10,10");
			m_gridObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px;font-size:12px", null, null, null);
			m_gridObj.enableSmartRendering(true, 200);
			//m_gridObj.enableEditEvents(true,true);
			m_gridObj.attachEvent("onRowDblClicked", function(rid,cid){
				m_RowDblClick = true;
				if(cid == 0) {
//					console.log(m_gridObj.cells(rid, cid));
					var xCellCombo = m_gridObj.cells(rid, cid);
					xCellCombo.edit();
					$(".dhx_combo_select").off("change").on("change", function(){
						var val = $(this).val();
						$(this).remove();
						m_gridObj.cells(rid, 0).setValue(val);
					});
					$("body").off("click").on("click", function(){
						$(".dhx_combo_select").remove();
					});
				} else {
					
				}
			});
			m_gridObj.attachEvent("onRowSelect", function(id,ind){
				if (!m_RowDblClick) {
					$(".dhx_combo_select").remove();
				}
				m_RowDblClick = false;
			});
		}
		
		var confirmCombo = m_gridObj.getCombo(0);
		confirmCombo.put(EMPTY_COMBO_OPTION, "선택해주세요.");
		for (var i=0; i<__confirm_code_list.length; i++) {
			confirmCombo.put(__confirm_code_list[i].code, __confirm_code_list[i].name);
		}
		
		bindEvent();
		doLoadLeagueTable();
	};
	
	// Bind Event
	function bindEvent(){
		$("#btn_consult_save").off("click").on("click", function(){
			var rowIds = m_gridObj.getChangedRows(true);
			if (rowIds) {
				var items = [];
				var arrRowIds = rowIds.split(",");
				for (var i=0; i<arrRowIds.length; i++) {
					var data = [];
					var rowId = arrRowIds[i];
					var prjtcd = m_gridObj.cells(rowId, 4).getValue();
					var typecd = m_gridObj.cells(rowId, 0).getValue();
					if (typecd == "") {
						continue;
					}
					data.push(prjtcd);
					data.push(typecd);
					items.push(data.join("^"));
				}
				// 서버 반영
				/*
				Ajax.post("LtSetupConsultTypeSave", {items:items.join("|")}, function(serverData){
					if (serverData.result) {
						alert("저장되었습니다.");
						doLoadLeagueTable();
					} else {
						alert(serverData.error.errorMessage);
						return false;
					}
				});
				*/
			} else {
				alert("변경된 항목이 없습니다.");
				return false;
			}
		});
	}
	
	// 목록 조회
	function doLoadLeagueTable(){
		m_gridObj.clearAll();
		Ajax.post("LtConsultBuyList", {}, function(serverData){
			if (serverData.result) {
				var list = serverData.data.list;
				m_gridObj.parse(list, "js");
			} else {
				alert(serverData.error.errorMessage);
				return false;
			}
		});
	};
	
	$(document).ready(function(){
		doInit();
	});
}());