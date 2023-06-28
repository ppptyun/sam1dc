(function(){
	// 변수 선언
	var thisObj = this;
	var paging = null;
	var PAGE_COUNT = 10;
	var DEFAULT_PAGING_ROWS = 40;
	var PAGING_ROWS = [20, 25, 30, 35, 40, 45, 50, 60, 70, 80, 90, 100, 150, 200];
	var m_gridDefinition = {
		parent:"div-grid",
		image_path:"./js/dhtmlx/codebase/imgs/",
		skin:"dhx_skyblue",
		columns:[
		         	{id:"seq", label:["순번"], type:"ro", align:"right", valign:"middle", width:"50", sort: "int"},
		         	{id:"prjtcd", label:["프로젝트코드"], type:"ro", align:"center", valign:"middle", width:"90", sort: "str"},
		         	{id:"lognm", label:["로그유형"], type:"ro", align:"center", valign:"middle", width:"100", sort: "left"},
		         	{id:"altnm", label:["변경유형"], type:"ro", align:"center", valign:"middle", width:"60", sort: "str"},
		         	{id:"fildnm", label:["필드명"], type:"ro", align:"left", valign:"middle", width:"100", sort: "str"},
		         	{id:"befval", label:["이전필드값"], type:"ro", align:"left", valign:"middle", width:"150", sort: "str"},
		         	{id:"altval", label:["변경필드값"], type:"ro", align:"left", valign:"middle", width:"150", sort: "str"},
		         	{id:"altemp", label:["변경자"], type:"ro", align:"center", valign:"middle", width:"90", sort: "str"},
		         	{id:"altdt", label:["변경일시"], type:"ro", align:"center", valign:"middle", width:"120", sort: "str"},
		         	{id:"bigo", label:["비고"], type:"ro", align:"left", valign:"middle", width:"*", sort: "str"},
				]
	};
	var m_gridObj = null;
	
	// init 함수 등록
	this.init = function(){
		if(!m_gridObj){
			m_gridObj = new dhtmlXGridObject(m_gridDefinition);
			/*m_gridObj.setInitWidthsP("6,8,8,7,8,12,12,12,11,20");
			m_gridObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px;font-size:12px", null, null, null);*/
		}
		if(!paging){
			paging = new PageNavigation("page_box", PAGE_COUNT, onClickPage);
		}
		
		// 페이지 당 노출 로우 선택항목 생성
		$.each(PAGING_ROWS, function(index, value){
			$("<option></option>").text(value).attr("value", value).appendTo("select[name='search_rowcount']");
		});
		$("select[name='search_rowcount']").val(DEFAULT_PAGING_ROWS).prop("selected", true);
		
		bindEvent();
		
		// 초기 조회
		setTimeout(function(){
			doSearchUserList(1);
		}, 300);
		
		resize();
		$(window).resize(resize);
	}
	
	// Bind Event
	function bindEvent(){
		// 페이지 당 노출 로우/로그유형/변경유형 선택항목 변경 이벤트
		$("#search_rowcount, #search_logcd, #search_altcd").change(function(){
			doSearchUserList(1);
		});
		
		// 프로젝트코드/변경자사번 엔터키 이벤트
		$("#search_prjtcd, #search_empno").off("keypress").on("keypress", function(e){
			if (e.which === 13) {
				doSearchUserList(1);
			}
		});
		
		// 검색
		$("#btnSearch").off("click").on("click", function(){
			doSearchUserList(1);
		});
	}
	
	function onClickPage(page) {
    	doSearchUserList(page);
    }
    
    function doSearchUserList(page, rows, logcd, altcd, prjtcd, empno) {
    	var params = {};
    	if (page == null) {
    		params.page = 1;
    	} else {
    		params.page = page;	
    	}
    	if (rows == null) {
    		params.rows = $("#search_rowcount option:selected").val();
    	} else {
    		params.rows = rows;
    	}
    	if (prjtcd == null) {
    		var _prjtcd = $.trim($("#search_prjtcd").val());
    		params.prjtcd = _prjtcd;
    	} else {
    		params.prjtcd = prjtcd;
    	}
    	if (logcd == null) {
    		params.logcd = $("#search_logcd").val();
    	} else {
    		params.logcd = logcd;
    	}
    	if (altcd == null) {
    		params.altcd = $("#search_altcd").val();
    	} else {
    		params.altcd = altcd;
    	}
    	if (empno == null) {
    		var _empno = $.trim($("#search_empno").val());
    		params.empno = _empno;
    	} else {
    		params.empno = empno;
    	}
    	
    	m_gridObj.clearAll();
    	
    	Ajax.post("AdminFieldLogSearch", params, function(serverData){
			if (serverData.result) {
				var loglist = serverData.data.list;
				m_gridObj.parse(loglist, "js");
				var total = serverData.data.total;
				paging.refresh(total, params.page, params.rows);
			} else {
				alert(serverData.error.errorMessage);
				return false;
			}
		});
    }
    
    function resize(){
		var height	= $(".dc_con_area").height() - 100;	// button + padding
		var width	= $(".dc_con_area").width() - 40;	// padding
		var $grid	= $("#div-grid");
		
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