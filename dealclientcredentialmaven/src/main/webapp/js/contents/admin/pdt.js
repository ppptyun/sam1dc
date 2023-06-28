(function(){
	// 변수 선언
	var thisObj = this;
	var ATTR_NEW_ROW = "is_new_added";
	var m_gridPdtDefinition = {
		parent:"div-grid-area",
		image_path:"./js/dhtmlx/codebase/imgs/",
		skin:"dhx_skyblue",
		columns:[
		         	{id:"refyearly", label:["참조년도", "#rspan"], type:"ro", align:"left", width:"0", sort: "str"},
		         	{id:"ctgcd", label:["Product", "카테고리"], type:"coro", align:"left", width:"150", sort: "str"},
		         	{id:"pdtcd", label:["#cspan", "코드"], type:"ro", align:"center", width:"50", sort: "str"},
		         	{id:"pdtnm", label:["#cspan", "이름"], type:"ed", align:"left", width:"200", sort: "str"},
		         	{id:"comnm", label:["#cspan", "Comment"], type:"ed", align:"left", width:"200", sort: "str"},
		         	{id:"credcd", label:["#cspan", "Credential 분류"], type:"coro", align:"center", width:"100", sort: "str"},
		         	{id:"conscd1", label:["League Table 자문 분류", "Select 1"], type:"coro", align:"center", width:"100", sort: "str"},
		         	{id:"conscd2", label:["#cspan", "Select 2"], type:"coro", align:"center", width:"100", sort: "str"},
		         	{id:"conscd3", label:["#cspan", "Select 3"], type:"coro", align:"center", width:"100", sort: "str"},
		         	{id:"samactcd", label:["#cspan", "Samil Client"], type:"coro", align:"left", width:"180", sort: "str"},
		         	{id:"sambizcd", label:["#cspan", "Samil"], type:"coro", align:"left", width:"120", sort: "str"},
		         	{id:"useyn", label:["사용여부", "#rspan"], type:"coro", align:"center", width:"70", sort: "str"},
		         	{id:"empty", label:[""]}
				]
	};
	var m_gridPdtObj = null;
	
	// init 함수 등록
	this.init = function(){
		// PDT Grid
		if(!m_gridPdtObj){
			m_gridPdtObj = new dhtmlXGridObject(m_gridPdtDefinition);
			m_gridPdtObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px;font-size:12px", null, null, null);
		}
		
		var credCombo = m_gridPdtObj.getCombo(m_gridPdtObj.getColIndexById("credcd"));
		credCombo.put("", "");
		for (var i=0; i<__cred_code_list.length; i++) {
			credCombo.put(__cred_code_list[i].code, __cred_code_list[i].name);
		}
		
		var consultCombo1 = m_gridPdtObj.getCombo(m_gridPdtObj.getColIndexById("conscd1"));
		var consultCombo2 = m_gridPdtObj.getCombo(m_gridPdtObj.getColIndexById("conscd2"));
		var consultCombo3 = m_gridPdtObj.getCombo(m_gridPdtObj.getColIndexById("conscd3"));
		consultCombo1.put("", "");
		consultCombo2.put("", "");
		consultCombo3.put("", "");
		for (var i=0; i<__consult_code_list.length; i++) {
			consultCombo1.put(__consult_code_list[i].code, __consult_code_list[i].name);
			consultCombo2.put(__consult_code_list[i].code, __consult_code_list[i].name);
			consultCombo3.put(__consult_code_list[i].code, __consult_code_list[i].name);
		}
		
		var bizactorCombo = m_gridPdtObj.getCombo(m_gridPdtObj.getColIndexById("samactcd"));
		bizactorCombo.put("", "");
		for (var i=0; i<__bizactor_code_list.length; i++) {
			bizactorCombo.put(__bizactor_code_list[i].code, __bizactor_code_list[i].name);
		}
		
		var bizconsultCombo = m_gridPdtObj.getCombo(m_gridPdtObj.getColIndexById("sambizcd"));
		bizconsultCombo.put("", "");
		for (var i=0; i<__bizconsult_code_list.length; i++) {
			bizconsultCombo.put(__bizconsult_code_list[i].code, __bizconsult_code_list[i].name);
		}
		
		var useynCombo = m_gridPdtObj.getCombo(m_gridPdtObj.getColIndexById("useyn"));
		useynCombo.put("Y", "Yes");
		useynCombo.put("N", "No");
		
		bindEvent();
		doLoadPdtRefyearlyList();
		
		resize();
		$(window).resize(resize);
	}
	
	// Bind Event
	function bindEvent(){
		// 참조년도 변경 이벤트
		$("select[id='sel_refyearly']").off("change").on("change", function() {
			doLoadPdtList();
		});
		
		// 카테고리관리
		$("#btn_pdt_category").off("click").on("click", function() {
			var param = {};
			param.refyearly = $("select[id='sel_refyearly'] option:selected").val();
			POPUP.ViewPopup("pdtCategory", param, function(selectedInfo) {
				doLoadPdtList();
			});
		});
		
		// PDT복사
		$("#btn_pdt_copy").off("click").on("click", function() {
			var refyearly = $("select[id='sel_refyearly'] option:selected").val();
			if (confirm("["+ refyearly +"] 참조년도를 복사하시겠습니까?")) {
				Ajax.post("AdminPdtCopy", {refyearly:refyearly}, function(serverData){
					if (serverData.result) {
						var nrefyearly = serverData.data.nrefyearly
						alert("["+ refyearly +" → "+ nrefyearly +"] 복사되었습니다.");
						// PDT 참조년도 목록 조회
						m_gridPdtObj.clearAll();
						doLoadPdtRefyearlyList();
					} else {
						alert(serverData.error.errorMessage);
						return false;
					}
				});
			}
		});
		
		// PDT추가
		$("#btn_pdt_add").off("click").on("click", function() {
			var param = {};
			param.refyearly = $("select[id='sel_refyearly'] option:selected").val();
			POPUP.ViewPopup("pdtAdd", param, function(product) {
				if (product && product.refyearly) {
					// 선택값
					var newId = (new Date()).valueOf();
					var refyearly = product.refyearly;
					var ctgcd = product.ctgcd;
					var pdtcd = product.pdtcd;
					var pdtnm = product.pdtnm;
					var comnm = product.comnm;
					
					// 중복 체크
					var rowIds = m_gridPdtObj.getAllRowIds();
					if (rowIds) {
						var arrRowIds = rowIds.split(",");
						for (var i=0; i<arrRowIds.length; i++) {
							var rid = arrRowIds[i];
							var exist_pdtcd = m_gridPdtObj.cells(rid, m_gridPdtObj.getColIndexById("pdtcd")).getValue();
							if (pdtcd == exist_pdtcd) {
								alert("이미 추가된 PDT 입니다.");
								return false;
							}
						}
					}
					
					// row 추가시키기
					m_gridPdtObj.addRow(newId, refyearly+','+ctgcd+','+pdtcd+','+pdtnm+','+comnm+',,,,,,,');
				}
			});
		});
		
		// PDT삭제
		$("#btn_pdt_del").off("click").on("click", function() {
			var d = new Date();
			var currYear = d.getFullYear();
			var currMonth = d.getMonth() + 1;
			var selYear = $("#sel_refyearly").val();
			
			if(currMonth < 7) {
				currYear = currYear - 1;
			}
			
			if(currYear < selYear) {
				if (confirm("정말 삭제하시겠습니까?")) {
					Ajax.post("AdminPdtDel", {year:selYear}, function(serverData){
						if (serverData.result) {
							alert("삭제되었습니다.");
							doLoadPdtRefyearlyList();
						} else {
							alert(serverData.error.errorMessage);
							return false;
						}
					});
				}
			} else {
				alert("삭제할 수 없는 참조년도입니다.");
			}
		});
		
		// 저장
		$("#btn_pdt_save").off("click").on("click", function(){			
			var rowIds = m_gridPdtObj.getChangedRows(true);
			if (rowIds) {
				var items = [];
				var arrRowIds = rowIds.split(",");
				for (var i=0; i<arrRowIds.length; i++) {
					var item = {};
					var rowId = arrRowIds[i];
					item.refyearly	= m_gridPdtObj.cells(rowId, m_gridPdtObj.getColIndexById("refyearly")).getValue();
					item.ctgcd 		= m_gridPdtObj.cells(rowId, m_gridPdtObj.getColIndexById("ctgcd")).getValue();
					item.pdtcd 		= m_gridPdtObj.cells(rowId, m_gridPdtObj.getColIndexById("pdtcd")).getValue();
					item.pdtnm 		= m_gridPdtObj.cells(rowId, m_gridPdtObj.getColIndexById("pdtnm")).getValue();
					item.comnm 		= m_gridPdtObj.cells(rowId, m_gridPdtObj.getColIndexById("comnm")).getValue();
					item.credcd 	= m_gridPdtObj.cells(rowId, m_gridPdtObj.getColIndexById("credcd")).getValue();
					item.conscd1 	= m_gridPdtObj.cells(rowId, m_gridPdtObj.getColIndexById("conscd1")).getValue();
					item.conscd2 	= m_gridPdtObj.cells(rowId, m_gridPdtObj.getColIndexById("conscd2")).getValue();
					item.conscd3 	= m_gridPdtObj.cells(rowId, m_gridPdtObj.getColIndexById("conscd3")).getValue();
					item.samactcd 	= m_gridPdtObj.cells(rowId, m_gridPdtObj.getColIndexById("samactcd")).getValue();
					item.sambizcd 	= m_gridPdtObj.cells(rowId, m_gridPdtObj.getColIndexById("sambizcd")).getValue();
					item.useyn 		= m_gridPdtObj.cells(rowId, m_gridPdtObj.getColIndexById("useyn")).getValue();
					items.push(item);
				}
				// 서버 반영
				if (confirm("저장하시겠습니까?")) {
					Ajax.post("AdminPdtSave", {items:items}, function(serverData){
						if (serverData.result) {
							alert("저장되었습니다.");
							doLoadPdtList();
						} else {
							alert(serverData.error.errorMessage);
							return false;
						}
					});
				}
			} else {
				alert("변경 혹은 추가된 항목이 없습니다.");
				return false;
			}
		});
	}
	
	// PDT 참조년도 목록 조회
	function doLoadPdtRefyearlyList(){
		$("select[id='sel_refyearly']").empty();
		Ajax.post("AdminPdtRefyearList", {}, function(serverData){
			if (serverData.result) {
				var refyearlylist = serverData.data.list;
				for (var i=0; i<refyearlylist.length; i++){
					$("<option></option>").text(refyearlylist[i]).attr("value", refyearlylist[i]).appendTo("select[id='sel_refyearly']");					
				}
				// PDT 목록 조회
				doLoadPdtList();
			} else {
				return false;
			}
		});
	}
	
	// PDT 목록 조회
	function doLoadPdtList(){
		m_gridPdtObj.clearAll();
		SAMILCOMMON.progressOn();
		var refyearly = $("select[id='sel_refyearly'] option:selected").val();
		Ajax.post("AdminPdtList", {refyearly:refyearly}, function(serverData){
			if (serverData.result) {
				var categoryCombo = m_gridPdtObj.getCombo(m_gridPdtObj.getColIndexById("ctgcd"));
				categoryCombo.clear();
				var categorylist = serverData.data.category;
				for (var i=0; i<categorylist.length; i++) {
					categoryCombo.put(categorylist[i].ctgcd, categorylist[i].ctgnm);
				}
				
				var pdtlist = serverData.data.list;
				m_gridPdtObj.parse(pdtlist, "js");
				m_gridPdtObj.setColumnHidden(m_gridPdtObj.getColIndexById("refyearly"), true);
				SAMILCOMMON.progressOff();
			} else {
				alert(serverData.error.errorMessage);
				SAMILCOMMON.progressOff();
				return false;
			}
		});
	}
	
	function resize(){
		var height	= $("#dc_con_article").height() - 70;	// button + padding
		var width	= $("#dc_con_article").width() - 40;	// padding
		var $grid	= $("#div-grid-area");
		
		$grid.height(height);
		$grid.width(width);
		$grid.find(".xhdr").width(width);
		$grid.find(".objbox").width(width);
		$grid.find(".objbox").height(height - $grid.find(".xhdr").height());
		
	}
	
	$(document).ready(function(){
		thisObj.init();
	});
})();