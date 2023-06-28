var GRID_OBJ = (function(){
	// 변수 선언
	var cred_cd = {
			client : "102001"
			, target : "102002"
			, concerned : "102005"
	}
	var authRole = SERVICE_CONFIG.credential.authrole;
	var tooltip = SERVICE_CONFIG.credential.tooltip;
	
	var m_gridObj = null;
	var m_gridDefinition = {
			parent:"div-grid-area",
			image_path:"./js/dhtmlx/codebase/imgs/",
			skin:"dhx_skyblue",
			columns:[  
			         	/*{id:"pdtcd", label:["Product", "Code"], type:"ro", align:"center", valign:"middle", width:"50", sort: "str"},
			         	{id:"pdtnm", label:["#cspan", "Name"], type:"ro", align:"left", valign:"middle", width:"150", sort: "str"},*/
			         	{id:"pdtinfo", label:["Product", "#rspan"], type:"ro", align:"left", valign:"middle", width:"150", sort: "str"},
			         	{id:"prjtcd", label:["Project", "Code"], type:"ro", align:"center", valign:"middle", width:"90", sort: "str"},
			         	{id:"prjtnm", label:["#cspan", "Name"], type:"ro", align:"left", valign:"middle", width:"150", sort: "str"},
			         	{id:"prjtdivnm", label:["#cspan", "주관/지원"], type:"ro", align:"center", valign:"middle", width:"70", sort: "str"},
			         	{id:"hqnm", label:["담당", "본부"], type:"ro", align:"left", valign:"middle", width:"120", sort: "str"},
			         	{id:"ptrempnm", label:["#cspan", "PTR"], type:"ro", align:"left", valign:"middle", width:"60", sort: "str"},
			         	{id:"mgrempnm", label:["#cspan", "MGR"], type:"ro", align:"left", valign:"middle", width:"60", sort: "str"},
			         	{id:"clientnm", label:["Client", "Name"], type:"ro", align:"left", valign:"middle", width:"100", sort: "str"},
			         	{id:"clientnatnm", label:["#cspan", "Country"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"clientindunm", label:["#cspan", "Industry"], type:"ro", align:"left", valign:"middle", width:"120", sort: "str"},
			         	{id:"tcomphannm1", label:["Target (Source: DBA or League Table)", "Name"], type:"ro", align:"left", valign:"middle", width:"100", sort: "str"},
			         	{id:"tnatnm1", label:["#cspan", "Country"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"tindunm1", label:["#cspan", "Industry"], type:"ro", align:"left", valign:"middle", width:"120", sort: "str"},
			         	{id:"tcomphannm2", label:["#cspan", "Name"], type:"ro", align:"left", valign:"middle", width:"100", sort: "str"},
			         	{id:"tnatnm2", label:["#cspan", "Country"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"tindunm2", label:["#cspan", "Industry"], type:"ro", align:"left", valign:"middle", width:"120", sort: "str"},
			         	{id:"tcomphannm3", label:["#cspan", "Name"], type:"ro", align:"left", valign:"middle", width:"100", sort: "str"},
			         	{id:"tnatnm3", label:["#cspan", "Country"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"tindunm3", label:["#cspan", "Industry"], type:"ro", align:"left", valign:"middle", width:"120", sort: "str"},
			         	{id:"trgtetc", label:["#cspan", "비고"], type:"ro", align:"left", valign:"middle", width:"150", sort: "str"},
			         	{id:"intrcomphannm", label:["이해관계자 (Source: DBA)", "Name"], type:"ro", align:"left", valign:"middle", width:"100", sort: "str"},
			         	{id:"intrnatnm", label:["#cspan", "Country"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"intrindunm", label:["#cspan", "Industry"], type:"ro", align:"left", valign:"middle", width:"120", sort: "str"},
			         	{id:"prjtdesc", label:["상세업무내용", "#rspan"], type:"ro", align:"left", valign:"middle", width:"150", sort: "str"},
			         	{id:"contdt", label:["용역기간", "개시일"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"termidt", label:["#cspan", "종료일"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"cisno", label:["계약 번호", "#rspan"], type:"ro", align:"left", valign:"middle", width:"100", sort: "str"},
			         	{id:"chamtw", label:["계약 금액", "#rspan"], type:"ron", align:"right", valign:"middle", width:"80", sort: "int"},
			         	{id:"billamtw", label:["청구 금액", "#rspan"], type:"ron", align:"right", valign:"middle", width:"80", sort: "int"},
			         	{id:"confnm", label:["Transaction (Source: League Table)", "Confidential"], type:"ro", align:"center", valign:"middle", width:"80", sort: "str"},
			         	{id:"consnm", label:["#cspan", "자문형태"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"dealnm1", label:["#cspan", "거래형태 및 대상"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"dealnm2", label:["#cspan", "#cspan"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"dealnm3", label:["#cspan", "#cspan"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
//			         	{id:"dealnm4", label:["#cspan", "#cspan"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"dealnm", label:["#cspan", "지분율"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"amt", label:["#cspan", "거래규모"], type:"ron", align:"right", valign:"middle", width:"80", sort: "int"},
			         	{id:"actor1", label:["#cspan", "매도인"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"actor2", label:["#cspan", "매수인"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"statusnm", label:["#cspan", "STATUS"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"secure", label:["BRS (Source: DBA)", "담보채권"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"unsecure", label:["#cspan", "무담보채권"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"brssalenm", label:["#cspan", "매각방식"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"brsopb", label:["#cspan", "OPB(단위: 백만원)"], type:"ron", align:"right", valign:"middle", width:"120", sort: "int"},
			         	{id:"brsbuyernm", label:["#cspan", "매수처"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"rcfaddr", label:["RE (Source: DBA)", "위치"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"rcftypenm", label:["#cspan", "구분"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"rcftypedtnm", label:["#cspan", "구분 상세"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"rcftypeetc", label:["#cspan", "구분 기타"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"rcfarea", label:["#cspan", "연면적"], type:"ron", align:"right", valign:"middle", width:"80", sort: "int"},
			         	{id:"rcfland", label:["#cspan", "토지면적"], type:"ron", align:"right", valign:"middle", width:"80", sort: "int"},
			         	{id:"credcd", label:["Cred", "#rspan"], type:"ro", align:"right", valign:"middle", width:"0", sort: "str"}
			         	/*{id:"comnm", label:["#cspan", "업무성격"], type:"ro", align:"left", valign:"middle", width:"120", sort: "str"}*/
					]
		}
	var pdtCateDef = {
			parent:"pdt_ctg_list",
			width:100,
			name:"pdtctg",
			skin:"dhx_skyblue",
			image_path:"./js/dhtmlx/codebase/imgs/",
	}
	var pdtDef = {
			parent:"pdt_list",
			width:200,
			name:"pdt",
			mode:"checkbox",
			skin:"dhx_skyblue",
			image_path:"./js/dhtmlx/codebase/imgs/",
	}
	var amtRangeDef = {
			parent:"search_amt",
			width:200,
			name:"amtrange",
			skin:"dhx_skyblue",
			image_path:"./js/dhtmlx/codebase/imgs/",
			items:[{value:"", 								text:"선택해주세요.", selected:true},
			       {value:"infinity~1000000000", 			text:"10억 미만"},
			       {value:"1000000000~10000000000",			text:"10억(이상) ~ 100억(미만)"},
			       {value:"10000000000~50000000000", 		text:"100억(이상) ~ 500억(미만)"},
			       {value:"50000000000~100000000000", 		text:"500억(이상) ~ 1,000억(미만)"},
			       {value:"100000000000~500000000000", 		text:"1,000억(이상) ~ 5,000억(미만)"},
			       {value:"500000000000~1000000000000", 	text:"5,000억(이상) ~ 1조(미만)"},
			       {value:"1000000000000~infinity",			text:"1조 이상"}]
	}
	
	var calendar = "";
	var league_columns 	= ["confnm", "consnm", "dealnm1", "dealnm2", "dealnm3", "dealnm4", "dealnm", "amt", "actor1", "actor2", "statusnm"];
	var brs_columns 	= ["secure", "unsecure", "brssalenm", "brsopb", "brsbuyernm"];
	var rcf_columns 	= ["rcfaddr", "rcftypenm", "rcftypedtnm", "rcftypeetc", "rcfarea", "rcfland"];
	var naColumnStyle	= "background-color:#f5f4f0";
	var pdtCateObj		= null;
	var pdtObj			= null;
	var amtRangeObj		= null; 
	
	// 초기화
	function doInit(){
		drawAuthRole("IMPORTROLE");
		drawAuthRole("DOWNLOADROLE");
		drawAuthRole("EXCLUDEROLE", function(isAuth){
			if(isAuth){
				amtRangeObj = new dhtmlXCombo(amtRangeDef);
				amtRangeObj.readonly(true);
			}
		});
		
		// Grid
		if(!m_gridObj){
			m_gridObj = new dhtmlXGridObject(m_gridDefinition);
			m_gridObj.enableSmartRendering(true, 200);
			m_gridObj.enableAlterCss("","");
			m_gridObj.splitAt(m_gridObj.getColIndexById("clientnm"));
			m_gridObj.attachEvent("onRowDblClicked", function(rid,cid){
				for(var i=0; i<USERROLE.length; i++) {
					if($.inArray(USERROLE[i].rolecd, authRole.EDITROLE) != -1) {
						var prjtcd = m_gridObj.cells(rid, m_gridObj.getColIndexById("prjtcd")).getValue();
						var credcd = m_gridObj.cells(rid, m_gridObj.getColIndexById("credcd")).getValue();
						POPUP.ViewPopup("editCredential", {"prjtcd":prjtcd, "credcd":credcd}, function(data) {
							if(data.result) {
								doSearchProject();
							}
						});
						break;
					}
				}
			});
			m_gridObj.setNumberFormat("0,000",m_gridObj.getColIndexById("chamtw"));
			m_gridObj.setNumberFormat("0,000",m_gridObj.getColIndexById("billamtw"));
			m_gridObj.setNumberFormat("0,000",m_gridObj.getColIndexById("amt"));
			m_gridObj.setNumberFormat("0,000",m_gridObj.getColIndexById("brsopb"));
			m_gridObj.setNumberFormat("0,000",m_gridObj.getColIndexById("rcfarea"));
			m_gridObj.setNumberFormat("0,000",m_gridObj.getColIndexById("rcfland"));
			m_gridObj.setColumnHidden(m_gridObj.getColIndexById("credcd"),true);
		}
		
		SAMILCOMMON.transparencyMsgOn("div-grid-area", "조회 조건 입력 후 검색 버튼을 클릭하여 조회 하시기 바랍니다.");
		calendar 	= new dhtmlXCalendarObject([{input: "inp_cal1", button: "btn_cal1"}, {input: "inp_cal2", button: "btn_cal2"}]);
		pdtCateObj 	= new dhtmlXCombo(pdtCateDef);
		pdtObj		= new dhtmlXCombo(pdtDef);
		
		pdtCateObj.readonly(true);
		pdtObj.readonly(true);
		
		pdtObj.attachEvent("onCheck", setPdtComboText);
		pdtObj.attachEvent("onChange", setPdtComboText);
		
		pdtCateObj.setOptionWidth(200);
		pdtObj.setOptionWidth(370);
		
		doLoadPdtCtgList();
		doLoadPdtList();
		
		pdtCateObj.attachEvent("onChange", function(value, text){doLoadPdtList(value);})
		
//		doPdtCtgList();			// Product Category
//		doPdtList("");			// Product
		doCredComTypeList();	// Client, Target, 이해관계자
		doBrsList();			// BRS
		doRcfList();			// RCF
		
		drawTooltip();
		bindEvent();
		resize();
		$(window).resize(resize);
	};
	
	function checkRole(role){
		for(var i=0; i<USERROLE.length; i++) {
			if($.inArray(USERROLE[i].rolecd, role) != -1) {
				return true;
			}
		}
		return false;
	}
	
	function drawAuthRole(role, callback){
		if(checkRole(authRole[role])){
			$("[authRole='"+role+"']").show()
			if(callback) callback(true)
		}else{
			$("[authRole='"+role+"']").remove()
			if(callback) callback(false)
		}
	}
	function drawTooltip(){
		$(".ico_tooltip").each(function(){
			$(this).tooltip({
				placement:"top",
				container:"body",
				html:true,
				title:tooltip[$(this).data("tooltip-id")].description
			})
		})
	}
	
	function bindEvent(){
		$("#btn_guidance").off("click").on("click", function(){
			window.open(SERVICE_CONFIG.credential.guidanceuri, "credentialguide", "width=1000, height=600, scrollbars=yes, toolbar=no, menubar=no, location=no, resizable=yes");
		})
		
		$("#btn_cal1").off("click").on("click", function() {
			if($("#inp_cal2").val() != "") {
				calendar.setSensitiveRange(null, $("#inp_cal2").val());
			}
		});
		
		$("#btn_cal2").off("click").on("click", function() {
			if($("#inp_cal1").val() != "") {
				calendar.setSensitiveRange($("#inp_cal1").val(), null);
			}
		});
		
		$("#btn_search").off("click").on("click", function() {
			doSearchProject();
		});
		
		$(".btn_industry_sel").off("click").on("click", function() {
			var _s = $(this).parent();
			POPUP.ViewPopup("industrySearch", null, function(selectedIndustryInfo) {
				_s.find("input:text").val(selectedIndustryInfo.indugnm + "/" + selectedIndustryInfo.indunm);
				_s.find("input:text").data("code", selectedIndustryInfo.inducd);
				_s.find("input:text").data("name", selectedIndustryInfo.indunm);
				_s.find("input:text").data("gcode", selectedIndustryInfo.indugcd);
				_s.find("input:text").data("gname", selectedIndustryInfo.indugnm);
			});
		});
		
		$(".btn_remove_data").off("click").on("click", function() {
			var _s = $(this).parent().find("input:text");
			_s.val("");
			_s.data("code", "");
			_s.data("gcode", "");
			_s.data("gname", "");
		});
		
		$(".btn_remove_date").off("click").on("click", function() {
			$(this).parent().find("input").val("");
		});
		
		$("#ppt_download").off("click").on("click", function() {
			var param = getParam();
			if(validate(param)){
				if(param.ctgcd == "F1" || param.ctgcd == "F3" || param.ctgcd == "F7" || param.ctgcd == "F5" || param.ctgcd == "FZ"){
					Ajax.downloadPPT("PptExport", param);
				}else if(param.ctgcd=="" || param.ctgcd == undefined || param.ctgcd == null){
					alert("Product 그룹을 반드시 선택하시기 바랍니다.")	
				}else{
					alert("선택한 Product 그룹에 대한 PPT 템플릿이 존재하지 않습니다.")
				}
			}
		});
		
		$("#exel_download").off("click").on("click", function() {
			var param = getParam();
			param.SystemCode 	= "DcExcelDownloadDAO";
			param.Method 		= "sqlSelectCredential";
			var brssecurity = [];
			var brsunsecured = [];
			for(var i=0; i<param.brssecurity.length; i++){
				brssecurity[i] = param.brssecurity[i].name;
			}
			for(var i=0; i<param.brsunsecured.length; i++){
				brsunsecured[i] = param.brsunsecured[i].name;
			}
			
			var excelParam = {};
			excelParam.filename		= "Credential_"+ SAMILCOMMON.toTimeString(new Date);
			excelParam.column		= DOWNLOAD_COLUMN_DEF.CREDENTIAL;
			excelParam.condition 	= [
			    {label:"Product 카테고리",	value:pdtCateObj.getSelectedText()},
			    {label:"Product",			value:pdtObj.getChecked()==""?"선택해주세요":pdtObj.getChecked()},
   				{label:"용역 종료일", 		value:!param.frdt && !param.todt?"":param.frdt + " ~ " + param.todt},
   				{label:"용역명", 			value:param.prjtnm},
   				{label:"PE 여부", 			value:param.chkpe},
   				{label:"Cross-Border 여부", 	value:param.chkcross},
   				{label:"Client 회사명", 		value:param.clicomnm},
   				{label:"Client Industry", 	value:param.cliindnm==""?param.cligindnm:param.cliindnm},
   				{label:"Client 국가", 		value:$('#client_nation option:selected').text()},
   				{label:"Target 회사명", 		value:param.tarcomnm},
   				{label:"Target Industry", 	value:param.tarindnm==""?param.targindnm:""},
   				{label:"Target 국가", 		value:$('#target_nation option:selected').text()},
   				{label:"이해관계자 회사명", 	value:param.concomnm},
   				{label:"이해관계자 Industry",	value:param.conindnm==""?param.congindnm:param.conindnm},
   				{label:"이해관계자 국가", 		value:$('#concerned_nation option:selected').text()},
   				{label:"Transaction-거래규모", value:amtRangeObj.getSelectedText()},
   				{label:"BRS 담보", 			value:brssecurity.join(", ")},
   				{label:"BRS 무담보", 		value:brsunsecured.join(", ")},
   				{label:"RE 위치", 			value:param.rcflocation},
   				{label:"RE 구분", 			value:$('#search_ref_type option:selected').text()},
   				{label:"RE 구분상세", 		value:$('#search_ref_detail_type option:selected').text()}
   			];
			if(checkRole(authRole.EXCLUDEROLE)) excelParam.condition.push({label:"제외된 Credential 검색", 	value:param.credtgtcd=="true"?"Y":"N"});
			param.excelJSON 	= excelParam;
			ExportObj.ExcelDownload(param);
		});
		
		$("#exel_upload").off("click").on("click", function() {
			POPUP.ViewPopup("importExcel", null, function(callback) {});
		});
		
		$("#exel_prjt_exception").off("click").on("click", function(){
			POPUP.ViewPopup("excel_except_credential", null);
		});
		
		
		$("#btn_detail_search_option").off("click").on("click", doOpenDetailOption);
	}
	
	
	function doAfterSearch(data) {
		m_gridObj.clearAll();
		SAMILCOMMON.transparencyMsgOff("div-grid-area");
		$(".detail_expand").show();
		$(".detail_collapse").hide();
		$("#search_detail").hide();
		if(!data.result){
			alert("에러가발생했습니다.["+data.error.errorCode+"]");
			SAMILCOMMON.progressOff();
			return;
		}else if (data.data.list.length == 0){
			SAMILCOMMON.transparencyMsgOn("div-grid-area", "검색 결과가 존재하지 않습니다.");
		}else{
			$("#totalcount").text(GF.numberWithCommas(data.data.list.length))
			var tmpRole = []
			for(var i=0; i<USERROLE.length; i++) {
				tmpRole.push(USERROLE[i].rolecd);
			}
			if(SAMILCOMMON.intersection(tmpRole, ["R01", "R98", "R99"]).length == 0){
				m_gridObj.setColumnId(m_gridObj.getColIndexById("chamtw"), "nochamtw");
				m_gridObj.setColumnId(m_gridObj.getColIndexById("billamtw"), "nobillamtw");
			}
			for(var i=0; i<data.data.list.length; i++){
				data.data.list[i].pdtinfo = data.data.list[i].pdtcd + "/" + data.data.list[i].pdtnm;
				
				if(data.data.list[i].credcd == '100101'){  // '100101' : BRS
					// BRS 일 경우 - RCF 관련 컬럼 음영처리
					setCellStyle(data.data.list[i], rcf_columns, naColumnStyle)
				}else if(data.data.list[i].credcd == '100102'){ // '100102' : RCF  
					// RCF 일 경우 - BRS 관련 컬럼 음영처리
					setCellStyle(data.data.list[i], brs_columns, naColumnStyle)
				}else{
					// RCF, BRS 관련 컬럼 음영처리
					setCellStyle(data.data.list[i], rcf_columns, naColumnStyle)
					setCellStyle(data.data.list[i], brs_columns, naColumnStyle)
				}
				if(data.data.list[i].leagueyn == 'N'){
					// League Table 대상이 아닐 경우 League Table 관련 컬럼 음영처리
					setCellStyle(data.data.list[i], league_columns, naColumnStyle)
				}
			}
			
			m_gridObj.parse(data.data.list, "js");
			
		}
		SAMILCOMMON.progressOff();
	}
	
	function setCellStyle(obj, colArr, style){
		for(var i=0; i<colArr.length; i++){
			obj[colArr[i]] = {
					value:obj[colArr[i]],
					style:style
			}
		}	
	}
	
	function doSearchProject(){
		var param = getParam();
		if(validate(param)){	
			SAMILCOMMON.progressOn();
			$("#totalcount").text("0");
			Ajax.post("CredentialList", param, doAfterSearch);	
		}
	};
	
	function setPdtComboText(){
		if(pdtObj.getChecked() == ""){
			pdtObj.setComboText("선택해주세요");
		}else{
			pdtObj.setComboText(pdtObj.getChecked());
			pdtObj.openSelect();
		}
	}
	
	
	function doLoadPdtCtgList(){
		Ajax.post("CredentialProductCategoryList", {}, function(json) {
			if(json.result) {
				var data = json.data.list;
				var option = [["", "선택해주세요"]];
				for(var i=0; i<data.length; i++){
					option.push([data[i].ctgcd, data[i].ctgnm]);
				}
				pdtCateObj.clearAll();
				pdtCateObj.addOption(option);
				pdtCateObj.selectOption(0, true, true);
			}
		});
	}
	
	function doLoadPdtList(ctgcd){
		var param = {};
		param.ctgcd = ctgcd;
		
		Ajax.post("CredentialProductList", param, function(json) {
			if(json.result) {
				var data = json.data.list;
				var option = [];
				for(var i=0; i<data.length; i++){
					option.push([data[i].pdtcd, data[i].pdtnm]);
				}
				pdtObj.clearAll();
				pdtObj.setComboText("선택해주세요");
				pdtObj.addOption(option);
			}
		});
	}
	
	function doCredComTypeList() {
		for(var i=0; i<__cred_com_type_list.length; i++) {
			if(cred_cd.client == __cred_com_type_list[i].code) {
				Ajax.post("CredentialCompanyTypeNationList", {"bizcd":cred_cd.client}, function(json) {
					if(json.result) {
						var data = json.data.list;
						_h = [];
						_h.push('<select id="client_nation" class="inp_select"><option value="">선택해주세요.</option>');
						for(var i=0; i<data.length; i++){
							_h.push('<option value="' + data[i].etcocd + '">' + data[i].etcdnm + '</option>');
						}
						_h.push('</select>');
						$("#client_nation_list").empty().append(_h.join(""));
					}
				});
			} else if(cred_cd.target == __cred_com_type_list[i].code) {
				Ajax.post("CredentialCompanyTypeNationList", {"bizcd":cred_cd.target}, function(json) {
					if(json.result) {
						var data = json.data.list;
						_h = [];
						_h.push('<select id="target_nation" class="inp_select"><option value="">선택해주세요.</option>');
						for(var i=0; i<data.length; i++){
							_h.push('<option value="' + data[i].etcocd + '">' + data[i].etcdnm + '</option>');
						}
						_h.push('</select>');
						$("#target_nation_list").empty().append(_h.join(""));
					}
				});
			} else if(cred_cd.concerned == __cred_com_type_list[i].code) {
				Ajax.post("CredentialCompanyTypeNationList", {"bizcd":cred_cd.concerned}, function(json) {
					if(json.result) {
						var data = json.data.list;
						_h = [];
						_h.push('<select id="concerned_nation" class="inp_select"><option value="">선택해주세요.</option>');
						for(var i=0; i<data.length; i++){
							_h.push('<option value="' + data[i].etcocd + '">' + data[i].etcdnm + '</option>');
						}
						_h.push('</select>');
						$("#concerned_nation_list").empty().append(_h.join(""));
					}
				});
			}
		}
	};
	
	function doBrsList() {
		_h = [];
		_h.push('<div class="inp_check_list">');
		for(var i=0; i<__security_type_list.length; i++){
			_h.push('<input type="checkbox" id="' + __security_type_list[i].name + '" name="search_secu_type" value="' + __security_type_list[i].code + '" data-name="' + __security_type_list[i].name + '"><label for="' + __security_type_list[i].name + '">' + __security_type_list[i].name + "</label>");
			//_h.push('<label for="' + __security_type_list[i].name + '">' + __security_type_list[i].name + '</label>');
		}
		_h.push('</div>');
		$("#search_secu_list").empty().append(_h.join(""));
		
		_h = [];
		_h.push('<div class="inp_check_list">');
		for(var i=0; i<__unsecured_type_list.length; i++){
			_h.push('<input type="checkbox" id="' + __unsecured_type_list[i].name + '" name="search_unse_type" value="' + __unsecured_type_list[i].code + '" data-name="' + __unsecured_type_list[i].name + '"><label for="' + __unsecured_type_list[i].name + '">' + __unsecured_type_list[i].name + "</label>");
			//_h.push('<label for="' + __unsecured_type_list[i].name + '">' + __unsecured_type_list[i].name + '</label>');
		}
		_h.push('</div>');
		$("#search_unse_list").empty().append(_h.join(""));
	}
	
	function getParam(){
		var param = {};
		param.ctgcd = pdtCateObj.getSelectedValue();
		param.pdtcd = encodeURIComponent(pdtObj.getChecked());
		param.frdt = $("#inp_cal1").val();
		param.todt = $("#inp_cal2").val();
		param.prjtnm = $("#prjtnm").val();
		param.chkpe = $("#check_pe").prop("checked")?"Y":"N";
		param.chkcross = $("#check_cross").prop("checked")?"Y":"N";
		param.srchtypcomp= $("input[name='search_type_comp']:checked").val();
		param.clicomnm = $("#client_company").val();
		if($("input:radio[name=client_ind_chk]:checked").val() == "inducd") {
			param.cliindcd = $("#client_industry").data("code");
			param.cliindnm = $("#client_industry").val();
			param.cligindcd = "";
			param.cligindnm = "";
		} else {
			param.cliindcd = "";
			param.cliindnm = "";
			param.cligindcd = $("#client_industry").data("gcode");
			param.cligindnm = $("#client_industry").data("gname");
		}
		param.clinatcd = $("#client_nation").val();
		param.clinatnm = "";
		if(param.clinatcd != "") {
			param.clinatnm = $('#client_nation option:selected').text();
		}
		param.tarcomnm = $("#target_company").val();
		if($("input:radio[name=target_ind_chk]:checked").val() == "inducd") {
			param.tarindcd = $("#target_industry").data("code");
			param.tarindnm = $("#target_industry").val();
			param.targindcd = "";
			param.targindnm = "";
		} else {
			param.tarindcd = "";
			param.tarindnm = "";
			param.targindcd = $("#target_industry").data("gcode");
			param.targindnm = $("#target_industry").data("gname");
		}
		param.tarnatcd = $("#target_nation").val();
		param.tarnatnm = "";
		if(param.tarnatcd != "") {
			param.tarnatnm = $('#target_nation option:selected').text();
		}
		param.concomnm = $("#concerned_company").val();
		if($("input:radio[name=concerned_ind_chk]:checked").val() == "inducd") {
			param.conindcd = $("#concerned_industry").data("code");
			param.conindnm = $("#concerned_industry").val();
			param.congindcd = "";
			param.congindnm = "";
		} else {
			param.conindcd = "";
			param.conindnm = "";
			param.congindcd = $("#concerned_industry").data("gcode");
			param.congindnm = $("#concerned_industry").data("gname");
		}
		param.connatcd = $("#concerned_nation").val();
		param.connatnm = "";
		if(param.connatcd != "") {
			param.connatnm = $('#concerned_nation option:selected').text();
		}
		
		// 거래규모
		if(amtRangeObj){
			param.amtrange = amtRangeObj.getSelectedValue();
		}
		
		param.brssecurity = [];
		$("input[name='search_secu_type']:checked").each(function() {
			var data = {};
			data.code = $(this).val();
			data.name = $(this).data("name");
			param.brssecurity.push(data);
		});
		param.brsunsecured = [];
		$("input[name='search_unse_type']:checked").each(function() {
			var data = {};
			data.code = $(this).val();
			data.name = $(this).data("name");
			param.brsunsecured.push(data);
		});
		
		param.rcflocation = $("#search_rcf_location").val();
		param.rcftype = $("#search_ref_type option:selected").val();
		param.rcfdetailtype = $("#search_ref_detail_type option:selected").val();
		
		// Credential 대상여부
		if($("#check_credtgtcd").length>0){
			param.credtgtcd = "" + $("#check_credtgtcd").prop("checked");
		}else{
			param.credtgtcd = "false";
		}
		
		// 불필요 파라미터 삭제
		for(key in param){
  			if(param[key] == ""){
  				delete param[key];
  			}
  		}
		
		return $.extend({"credtgtcd":"false","srchtypcomp":"or","chkpe":"N","chkcross":"N", brssecurity:[], brsunsecured:[]}, param);
	}
	
	function validate(param){
		
		var chkParam = $.extend({}, param)
		delete chkParam.srchtypcomp;
		
		// 조회조건 있는지 확인
		if(JSON.stringify(chkParam)=='{"credtgtcd":"false","chkpe":"N","chkcross":"N","brssecurity":[],"brsunsecured":[]}'){
			alert("조회 조건을 입력 후 조회하시기 바랍니다.");
			return false;
		}
		
		
		// 용역 종료일 확인
		if(param.frdt && param.todt){
			if((SAMILCOMMON.getMonthInterval(param.frdt.replace(/\-/gi, ""), param.todt.replace(/\-/gi, ""))+1)>36){
				alert("[용역종료일] 검색 기간은 최대 3년입니다.");
				return false
			};
		}else if(!param.frdt && param.todt){
			alert("[용역종료일] 시작일을 입력하시기 바랍니다.")
			return false;
		}else if(param.frdt && !param.todt){
			alert("[용역종료일] 종료일을 입력하시기 바랍니다.")
			return false;
		}
		
		if(param.prjtnm){
			if($.trim(param.prjtnm).length >=3 ||  $.trim(param.prjtnm).length == 0){
				return true;
			}else{
				alert("[용역명] 3글자 이상 입력하시기 바랍니다.")
				return false;
			}
		}
		
		return true;
	}
	
	
	
	function doRcfList() {
		var rcfleisuredetail = "102404";
		var rcfinfradetail = "102408";
		
		var _h = [];
		_h.push('<select id="search_ref_type" class="inp_select"><option value="">선택해주세요.</option>');
		for(var i=0; i<__rcf_type_list.length; i++){
		_h.push('<option value="' + __rcf_type_list[i].code + '">' + __rcf_type_list[i].name + '</option>');
		}
		_h.push('</select>');
		$("#search_rcf_list").empty().append(_h.join(""));
		
		_h = [];
		_h.push('<select id="search_ref_detail_type" class="inp_select"><option value="">선택해주세요.</option></select>');
		$("#search_rcf_detail_list").empty().append(_h.join(""));
		
		$("#search_ref_type").off("change").on("change", function(){
			var rcftypecd = $(this).val();
			_h = [];
			_h.push('<select id="search_ref_detail_type" class="inp_select"><option value="">선택해주세요.</option>');
			if(rcftypecd == rcfleisuredetail) {
				for(var i=0; i<__rcf_leisure_list.length; i++){
					_h.push('<option value="' + __rcf_leisure_list[i].code + '">' + __rcf_leisure_list[i].name + '</option>');
				}
			} else if(rcftypecd == rcfinfradetail) {
				for(var i=0; i<__rcf_infra_list.length; i++){
					_h.push('<option value="' + __rcf_infra_list[i].code + '">' + __rcf_infra_list[i].name + '</option>');
				}
			}
			_h.push('</select>');
			$("#search_rcf_detail_list").empty().append(_h.join(""));
		});
	}
	
	function doOpenDetailOption(){
		$(".detail_expand, .detail_collapse").toggle();
		$("#search_detail").slideToggle("fast");
	}
	
	function resize(){
		var height	= $("#dc_con_article").height() - 135;	// Search button + padding
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