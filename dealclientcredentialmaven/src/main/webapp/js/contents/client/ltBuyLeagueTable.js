var GRID_OBJ = (function(){
	// 변수 선언
	var EMPTY_COMBO_OPTION = "_empty_";
	var m_gridObj = null;
	var m_gridDefinition = {
			parent:"div-grid-area",
			image_path:"./js/dhtmlx/codebase/imgs/",
			skin:"dhx_skyblue",
			columns:[  
						{id:"chrgconfcd", label:["본부 담당자 확인", "확인 여부"], type:"coro", align:"center", valign:"middle", width:"80", sort: "str", disabled:true},
			         	{id:"chrgconfdt", label:["#cspan", "확인일자"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"etconfyn", label:["Engagement Team 확인", "확인 여부"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str", disabled:true},
			         	{id:"etconfdt", label:["#cspan", "확인일자"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"etconfempnm", label:["#cspan", "확인자"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"confnm", label:["Confidential", "#rspan", "#select_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"prjtcd", label:["Project", "Code", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"100", sort: "str"},
			         	{id:"prjtnm", label:["#cspan", "Name", "#text_filter"], type:"ro", align:"left", valign:"middle", width:"200", sort: "str"},
			         	{id:"hqnm", label:["#cspan", "본부", "#text_filter"], type:"ro", align:"left", valign:"middle", width:"150", sort: "str"},
			         	{id:"ptrempnm", label:["#cspan", "EL", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"mgrempnm", label:["#cspan", "TM", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"chrempnm", label:["#cspan", "본부담당자", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"100", sort: "str"},
			         	{id:"yearly", label:["#cspan", "대상년도", "#text_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"statusnm", label:["Status", "#rspan", "#select_filter"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"upddt", label:["최종 수정일", "#rspan"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"tcomphannm", label:["Target", "기업명"], type:"ro", align:"left", valign:"middle", width:"200", sort: "str"},
			         	{id:"tindunm", label:["#cspan", "Industry"], type:"ro", align:"left", valign:"middle", width:"200", sort: "str"},
			         	{id:"tdealnm", label:["#cspan", "거래물"], type:"ro", align:"left", valign:"middle", width:"150", sort: "str"},
			         	{id:"scomphannm", label:["Seller", "기업명"], type:"ro", align:"left", valign:"middle", width:"200", sort: "str"},
			         	{id:"sindunm", label:["#cspan", "Industry"], type:"ro", align:"left", valign:"middle", width:"200", sort: "str"},
			         	{id:"bcomphannm", label:["Buyer", "기업명"], type:"ro", align:"left", valign:"middle", width:"200", sort: "str"},
			         	{id:"bindunm", label:["#cspan", "Industry"], type:"ro", align:"left", valign:"middle", width:"200", sort: "str"},
			         	{id:"amt", label:["금액 및 성격", "원화 환산금액(원)"], type:"ron", align:"right", valign:"middle", width:"120", sort: "int"}
					]
		}
	
	// 수정필드 정의 및 수정가능한 Role 정의
	var editableColumns = {
		"chrgconfcd":["R03", "R05", "R98", "R99"]
	};
	
	
	
	// 초기화
	function doInit(){
		// Grid
		if(!m_gridObj){
			m_gridObj = new dhtmlXGridObject(m_gridDefinition);
			m_gridObj.enableSmartRendering(true, 200);
			m_gridObj.enableEditEvents(true, true);
			m_gridObj.enableAlterCss("","");
			var bgColor = [];
			for(var i=0; i<m_gridDefinition.columns.length; i++){if(SAMILCOMMON.intersection(SAMILCOMMON.getArrayByColumn(USERROLE,["rolecd"]), editableColumns[m_gridDefinition.columns[i].id]).length > 0){
					bgColor[i] = "#f6dabf";
				}else{
					bgColor[i] = "";
				}
			}
			m_gridObj.setColumnColor(bgColor.join(","));			
			m_gridObj.attachEvent("onRowDblClicked", function(rid,cid){
				if(cid != 0) {
					SAMILCOMMON.progressOn();
					var prjtcd = m_gridObj.cells(rid, m_gridObj.getColIndexById("prjtcd")).getValue();
					prjtcdParam = {};
					prjtcdParam.prjtcd = prjtcd;
					prjtcdParam.condition = "Read";
					prjtcdParam.type = "Buy";
					Ajax.jsp("client/ltDetailLeagueTable", prjtcdParam, function(html) {
						$("#dc_con_list_detail").empty().append(html);
						$("#dc_con_list").hide();
						SAMILCOMMON.progressOff();
					});
				}
			});
			m_gridObj.splitAt(m_gridObj.getColIndexById("prjtcd"));
			m_gridObj.setNumberFormat("0,000",m_gridObj.getColIndexById("amt"));
			
			var confirmCombo = m_gridObj.getCombo(m_gridObj.getColIndexById("chrgconfcd"));
			confirmCombo.put(EMPTY_COMBO_OPTION, "선택해주세요.");
			for (var i=0; i<__confirm_code_list.length; i++) {
				confirmCombo.put(__confirm_code_list[i].code, __confirm_code_list[i].name);
			}
			
			// 리스트 - 담당자 확인 관련 컨트롤 
			if(SAMILCOMMON.intersection(SAMILCOMMON.getArrayByColumn(USERROLE,["rolecd"]), editableColumns["chrgconfcd"]).length <= 0){
				m_gridObj.attachEvent("onEditCell", function(stage,rId,cInd,nValue,oValue){
				     if(cInd == m_gridObj.getColIndexById("chrgconfcd")){
				    	 return false;
				     }
				     return true;
				});	
			}
		}
		
		bindEvent();
		doLoadLeagueTable();
		
		resize();
		$(window).resize(resize);
	};
	
	// Bind Event
	function bindEvent(){		
		//----- [2019.01.24  남웅주] ---------------------------------
		$("select[id='sel_actcdbizcd']").off("change").on("change", function() {
			doLoadLeagueTable();
		});
		//----- [2019.01.24  남웅주] ---------------------------------		
		$("#btn_guidance").off("click").on("click", function(){
			window.open(SERVICE_CONFIG.leaguetable.guidanceuri, "leaguetableguide", "width=1000, height=600, scrollbars=yes, toolbar=no, menubar=no, location=no, resizable=yes");
		});
		
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
					var typecd = m_gridObj.cells(rowId, m_gridObj.getColIndexById("chrgconfcd")).getValue();
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
				Ajax.post("LtConsultConfirm", {items:items.join("|")}, function(serverData){
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
		
		
		$("#btn_excel_download").off("click").on("click", function() {
			//--------- [2019.01.24  남웅주] ----------------------------------------------------
			var actcdbizcd = $("select[id='sel_actcdbizcd'] option:selected").val();
			//--------- [2019.01.24  남웅주] ----------------------------------------------------
			var param = {};
			var excelParam = {};
			excelParam.filename = "인수매각자문_"+ SAMILCOMMON.toTimeString(new Date);
			excelParam.column 	= DOWNLOAD_COLUMN_DEF.BUYLEAGUE;
			param.SystemCode 	= "DcExcelDownloadDAO";
			param.Method 		= "sqlSelectBuyLeagueTable";
			param.excelJSON 	= excelParam;
			//--------- [2019.01.24  남웅주] ----------------------------------------------------
			param.ActcdBizcd    = actcdbizcd;
			//--------- [2019.01.24  남웅주] ----------------------------------------------------
			ExportObj.ExcelDownload(param);
		});
		
		
	}
	
	// 목록 조회
	function doLoadLeagueTable(){
		m_gridObj.clearAll();
		SAMILCOMMON.transparencyMsgOff();
		SAMILCOMMON.progressOn();
		//--------- [2019.01.24  남웅주] ----------------------------------------------------
		var actcdbizcd = $("select[id='sel_actcdbizcd'] option:selected").val();
		//alert(actcdbizcd);
		//--------- [2019.01.24  남웅주] ----------------------------------------------------
		Ajax.post("LtConsultBuyList", {actcdbizcd:actcdbizcd}, function(serverData){
			if (serverData.result) {
				var list = serverData.data.list;
				
				if(list.length == 0){
					SAMILCOMMON.transparencyMsgOn("div-grid-area", "대상 프로젝트가 존재하지 않습니다.");	
				}else{
					for(var i=0; i<serverData.data.list.length; i++){
						// Product 코드/이름으로 변경
						serverData.data.list[i].pdtinfo = serverData.data.list[i].pdtcd + "/" + serverData.data.list[i].pdtnm;
						
						if(serverData.data.list[i].chrgconfcd == "100802"){
							serverData.data.list[i].chrgconfcd = {
									value:"100802",
									style:"color:red"
							}
						}
						if(serverData.data.list[i].etconfyn == "No"){
							serverData.data.list[i].etconfyn = {
									value:"No",
									style:"color:red"
							}
						}
					}
					m_gridObj.parse(list, "js");		
					
//					// Style 설정
//					for(var i=0; i<m_gridObj.getRowsNum(); i++){
//						var rid = m_gridObj.getRowId(i);
//						
//						// 담당자 확인
//						var tmpStyle = [];
//						if(serverData.data.list[i].chrgconfcd == "100802"){
//							tmpStyle.push("color:red"); // 확인여부가 No일경우 빨간색 표기
//						}
//						setCellStyle(rid, ["chrgconfcd"], tmpStyle.join(";"));
//						
//						// ET 확인
//						if(serverData.data.list[i].etconfyn == "No"){
//							setCellStyle(rid, ["etconfyn"], "color:red");
//						}
//					}
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