<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="layCont">
	<div class="titArea">
		<p class="txtInfo">표준감사시간관리 시스템상 Conflict 내역이며, Retain 및 T-link와 연동되지 않습니다.</p>
		<div class="layBtn side">
			<button id="popBtnExcelDownload" type="button" class="btnPwc btnM action">Excel Export</button>
		</div>
	</div>
	<div id="pop-conflict-grid" class="gridbox gridHp04"></div>
</div>

<script type="text/javascript">
(function($){
	// grid
	const conflictGrid = {
		parent: "pop-conflict-grid",
		skin: THEME_INFO.THEME,
		image_path: THEME_INFO.THEME_IMG_PATH,
		columns: [
			{id:"membNm", label:["Name", "#text_filter"], type:"ro", align:"center", width:"120"},
			{id:"workSdt", label:["업무시작일", "#text_filter"], type:"ro", align:"center", sort:"str", width:"80"},
			{id:"workEdt", label:["업무종료일", "#text_filter"], type:"ro", align:"center", sort:"str", width:"80"},
			{id:"workDays", label:["업무일", "#number_filter"], type:"ron", align:"center", sort:"str", width:"80"},
			{id:"prjtCd", label:["Project Code", "#text_filter"], type:"ro", align:"center", sort:"str", width:"100"},
			{id:"prjtNm", label:["프로젝트 명", "#text_filter"], type:"ro", align:"left", sort:"str"},
			{id:"ptrNm", label:["EL", "#text_filter"], type:"ro", align:"left", sort:"str", width:"80"},
			{id:"mgrNm", label:["PM", "#text_filter"], type:"ro", align:"left", sort:"str", width:"80"},
			{id:"actv", label:["Activity", "#select_filter"], type:"ro", align:"left", sort:"str", width:"150"},
			{id:"loca", label:["Location", "#select_filter"], type:"ro", align:"left", sort:"str", width:"100"},
			{id:"bdgt", label:["Budget Hour(hr)", "#number_filter"], type:"ron", align:"right", sort:"int", width:"80"},
			
		]
	};
	
	let conflictGridObj = new dhtmlXGridObject(conflictGrid);
	conflictGridObj.setNumberFormat("0,000.00",conflictGridObj.getColIndexById("bdgt"),".",",");
	conflictGridObj.enableAlterCss('', '');
	conflictGridObj.enableColSpan(true);
	
	Helper.post("/popup/conflict", {prjtCd:'<c:out value="${popupInfo.prjtCd}" />', sDt:'<c:out value="${popupInfo.sDt}" />', eDt:'<c:out value="${popupInfo.eDt}" />'})
	.done(function(data){
		if(data){
			for(let i=0; i<data.length; i++){
				if(data[i].prjtCd === '-'){
					data[i].membNm 	= '합계'
					data[i].workSdt = '';
					data[i].workEdt = '';
					data[i].prjtCd 	= '';
				}
			}
		} 
		
		grid_noData("pop-conflict-grid", false);
		if(data.length > 0){			
			conflictGridObj.parse(data, 'js');
		}else{
			grid_noData("pop-conflict-grid", true, "조회 결과가 없습니다.");
		}
		conflictGridObj.setSizes();	
	});
	
	// 이벤트 바인딩
	$("#layPopup-${popupInfo.id} #popBtnExcelDownload").off('click').on('click', function(){
		conflictGridObj.toExcel(EXCEL_WRITER_URL + "?filename=Conflict(${popupInfo.prjtCd})");
	});
	
})(jQuery)
</script>
