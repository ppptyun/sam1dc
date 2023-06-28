var GRID_OBJ = (function(){
	// 변수 선언
	var EMPTY_COMBO_OPTION = "_empty_";
	var LT_INCLUDE = "101001";
	var LT_EXCLUDE = "101002";
	var LT_NONE = "101003";
	var m_gridObj = null;
	var m_gridDefinition = {
			parent:"div-grid-area",
			image_path:"./js/dhtmlx/codebase/imgs/",
			skin:"dhx_skyblue",
			columns:[  
						{id:"ltcheck", label:["선택", "#rspan", "#master_checkbox"], type:"ch", align:"center", valign:"middle", width:"40", sort: "str"},
						{id:"lttgtnm", label:["League Table 대상여부", "#rspan", "#select_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"consnm", label:["자문형태", "#rspan", "#select_filter"], type:"ro", align:"left", valign:"middle", width:"100", sort: "str"},
			         	{id:"pdtinfo", label:["Product", "#rspan", "#select_filter"], type:"rotxt", align:"left", valign:"middle", width:"200", sort: "str"},
			         	{id:"prjtcd", label:["Project", "Code", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"100", sort: "str"},
			         	{id:"prjtnm", label:["#cspan", "Name", "#text_filter"], type:"rotxt", align:"left", valign:"middle", width:"200", sort: "str"},
			         	{id:"hqnm", label:["#cspan", "본부", "#text_filter"], type:"rotxt", align:"left", valign:"middle", width:"150", sort: "str"},
			         	{id:"ptrempnm", label:["#cspan", "EL", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"mgrempnm", label:["#cspan", "TM", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"chrempnm", label:["#cspan", "본부담당자"], type:"ro", align:"center", valign:"middle", width:"100", sort: "str"},
			         	{id:"upddt", label:["최종 수정일", "#rspan", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"cisdt1", label:["CIS", "(중간)종료", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"cisdt2", label:["#cspan", "착수금", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"cisdt3", label:["#cspan", "중도금", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"cisdt4", label:["#cspan", "잔금", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"closdt1", label:["프로젝트", "1차종료일", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"closdt2", label:["#cspan", "2차종료일", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"closdt3", label:["#cspan", "3차종료일", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"}
					]
		}

	// 초기화
	function doInit(){
		// Grid
		if(!m_gridObj){
			m_gridObj = new dhtmlXGridObject(m_gridDefinition);
			/*m_gridObj.setInitWidthsP("4,8,8,10,6,12,8,18,12,8,8,12,10,10,10,10,10,10,10");
			m_gridObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px;font-size:12px", null, null, null);*/
			m_gridObj.enableSmartRendering(true, 200);
			m_gridObj.splitAt(m_gridObj.getColIndexById("pdtinfo"));
			//m_gridObj.setNumberFormat("0,000",m_gridObj.getColIndexById("amt"));
		}
		
		bindEvent();
		doLoadLeagueTable();
		
		resize();
		$(window).resize(resize);
	};
	
	// Bind Event
	function bindEvent(){
		$("#btn_excel_download").off("click").on("click", function(){
			//--------- [2020.03.23  남웅주] ----------------------------------------------------
			var param = {};
			var excelParam = {};
			excelParam.filename = "대상프로젝트_"+ SAMILCOMMON.toTimeString(new Date);
			excelParam.column 	= DOWNLOAD_COLUMN_DEF.PROJECTLIST;
			param.SystemCode 	= "DcExcelDownloadDAO";
			param.Method 		= "sqlSelectProjectList";
			param.excelJSON 	= excelParam;
			//--------- [2020.03.23  남웅주] ----------------------------------------------------
			ExportObj.ExcelDownload(param);
		});
		$("#btn_league_include").off("click").on("click", function(){
			doSaveLeagueProject(LT_INCLUDE);
		});
		$("#btn_league_exclude").off("click").on("click", function(){
			doSaveLeagueProject(LT_EXCLUDE);
		});
		$("#btn_guidance").off("click").on("click", function(){
			window.open(SERVICE_CONFIG.leaguetable.guidanceuri, "leaguetableguide", "width=1000, height=600, scrollbars=yes, toolbar=no, menubar=no, location=no, resizable=yes");
		})
	}
	
	// League Table 대상 프로젝트 업데이트
	function doSaveLeagueProject(lttgtcd) {
		var targetText = GF.getItemText(__object_code_list, lttgtcd);
		var chkRowIds = m_gridObj.getCheckedRows(m_gridObj.getColIndexById("ltcheck")).split(",");
		if(chkRowIds.length == 0){
			alert("선택된 항목이 없습니다.");
			return false;
		}
		
		var items = [];
		var chkrs = [];
		var valid = false;
		for(var i=0; i<chkRowIds.length; i++){
			var prjtcd = m_gridObj.cells(chkRowIds[i], m_gridObj.getColIndexById("prjtcd")).getValue();
			items.push(prjtcd);
			chkrs.push(chkRowIds[i]);
			
			var lttgtnm = m_gridObj.cells(chkRowIds[i], m_gridObj.getColIndexById("lttgtnm")).getValue();
			if(((lttgtnm == "Yes") ? LT_INCLUDE : LT_EXCLUDE) != lttgtcd){
				valid = true;
			}
		}
		if(!valid){
			if(lttgtcd == LT_INCLUDE) {
				alert("선택된 항목은 대상으로 지정되어 있습니다.");
			} else {
				alert("선택된 항목은 대상에서 제외되어 있습니다.");
			}
			return false;
		}
		// 서버 반영
		Ajax.post("LtProjectSave", {lttgtcd:lttgtcd, items:items.join("|")}, function(serverData){
			if (serverData.result) {
				alert("저장되었습니다.");
				/*for (var i=0; i<chkrs.length; i++) {
					m_gridObj.cells(chkrs[i], m_gridObj.getColIndexById("ltcheck")).setValue(0);
					m_gridObj.cells(chkrs[i], m_gridObj.getColIndexById("lttgtnm")).setValue(targetText);
				}*/
				doLoadLeagueTable();
			} else {
				alert(serverData.error.errorMessage);
				return false;
			}
		});
	}
	
	// 목록 조회
	function doLoadLeagueTable(){
		m_gridObj.clearAll();
		SAMILCOMMON.transparencyMsgOff();
		SAMILCOMMON.progressOn();
		Ajax.post("LtProjectList", {}, function(serverData){
			if (serverData.result) {
				var list = serverData.data.list;
				if(list.length == 0){
					SAMILCOMMON.transparencyMsgOn("div-grid-area", "대상 프로젝트가 존재하지 않습니다.");	
				}else{
					for(var i=0; i<serverData.data.list.length; i++){
						serverData.data.list[i].pdtinfo = serverData.data.list[i].pdtcd + "/" + serverData.data.list[i].pdtnm
						if(serverData.data.list[i].pdtcd  == "F655") console.log(serverData.data.list[i].pdtinfo)
					}
					
					m_gridObj.parse(list, "js");	
				}
				SAMILCOMMON.progressOff();
			} else {
				alert(serverData.error.errorMessage);
				SAMILCOMMON.progressOff();
				return false;
			}
		});
	};
	
	function resize(){
		var height	= $("#dc_con_article").height() - 70;	// Search button + padding
		var width	= $("#dc_con_article").width() - 40;	// padding
		var $split1 = $($("#div-grid-area > div")[0]);
		var $split2 = $($("#div-grid-area > div")[1]);
		
		$("#div-grid-area").height(height);
		$("#div-grid-area").width("100%");
	
		$split1.height(height);
		$split1.find(".objbox").height(height - $split1.find(".xhdr").height());
		
		$split2.height(height);
		$split2.find(".objbox").height(height - $split2.find(".xhdr").height());
		$split2.width(width - $split1.width());
		$split2.find(".xhdr").width(width - $split1.width());
		$split2.find(".objbox").width(width - $split1.width());
	}
	
	$(document).ready(function(){
		doInit();
	});
}());