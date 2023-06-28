<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.samil.dc.domain.CommonCodeListBean"%>
<%@ page import="com.samil.dc.service.ApplicationGlobal"%>
<%@ page import="com.samil.dc.service.worker.ServiceHelper"%>
<%
String prjtcd 		= ServiceHelper.getParameter(request, "prjtcd");
String condition 	= ServiceHelper.getParameter(request, "condition");
String type 		= ServiceHelper.getParameter(request, "type");

//버튼 권한
boolean authChargerConfirm = ServiceHelper.isAuthLeagueListSave(request);
request.setAttribute("authChargerConfirm", authChargerConfirm);

boolean authETConfirm = ServiceHelper.isAuthLeagueETConfirm(request);
request.setAttribute("authETConfirm", authETConfirm);


//자문형태 
String consType = "103";
List<CommonCodeListBean> consTypeList = ApplicationGlobal.getCommonCodeList(consType);
request.setAttribute("consTypeList", consTypeList);

//Status 
String status = "106";
List<CommonCodeListBean> statusList = ApplicationGlobal.getCommonCodeList(status);
request.setAttribute("statusList", statusList);

//Confidential 여부 
String confType = "107";
List<CommonCodeListBean> confTypeList = ApplicationGlobal.getCommonCodeList(confType);
request.setAttribute("confTypeList", confTypeList);

//담당자확인여부
String confirmType = "108";
List<CommonCodeListBean> confirmCodeList = ApplicationGlobal.getCommonCodeList(confirmType);
request.setAttribute("confirmCodeList", confirmCodeList);

//Buyout 분류
String buyoutType = "109";
List<CommonCodeListBean> buyoutTypeList = ApplicationGlobal.getCommonCodeList(buyoutType);
request.setAttribute("buyoutTypeList", buyoutTypeList);

//국경간거래
String bordType = "111";
List<CommonCodeListBean> bordTypeList = ApplicationGlobal.getCommonCodeList(bordType);
request.setAttribute("bordTypeList", bordTypeList);

//인수매각 거래형태1
String buyDealType1 = "112";
List<CommonCodeListBean> buyDealType1List = ApplicationGlobal.getCommonCodeList(buyDealType1);
request.setAttribute("buyDealType1List", buyDealType1List);

//인수매각 거래형태2
String buyDealType2 = "113";
List<CommonCodeListBean> buyDealType2List = ApplicationGlobal.getCommonCodeList(buyDealType2);
request.setAttribute("buyDealType2List", buyDealType2List);

//인수매각 거래형태3
String buyDealType3 = "114";
List<CommonCodeListBean> buyDealType3List = ApplicationGlobal.getCommonCodeList(buyDealType3);
request.setAttribute("buyDealType3List", buyDealType3List);

//인수매각 거래형태4
String buyDealType4 = "115";
List<CommonCodeListBean> buyDealType4List = ApplicationGlobal.getCommonCodeList(buyDealType4);
request.setAttribute("buyDealType4List", buyDealType4List);

//합병 거래형태1
String mnaDealType1 = "116";
List<CommonCodeListBean> mnaDealType1List = ApplicationGlobal.getCommonCodeList(mnaDealType1);
request.setAttribute("mnaDealType1List", mnaDealType1List);

//합병 거래형태2
String mnaDealType2 = "117";
List<CommonCodeListBean> mnaDealType2List = ApplicationGlobal.getCommonCodeList(mnaDealType2);
request.setAttribute("mnaDealType2List", mnaDealType2List);

//합병 거래형태3
String mnaDealType3 = "118";
List<CommonCodeListBean> mnaDealType3List = ApplicationGlobal.getCommonCodeList(mnaDealType3);
request.setAttribute("mnaDealType3List", mnaDealType3List);
%>
<script>
var __cons_type_list = [];
<c:forEach var="code" items="${consTypeList}" varStatus="status">
__cons_type_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __status_list = [];
<c:forEach var="code" items="${statusList}" varStatus="status">
__status_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __conf_type_list = [];
<c:forEach var="code" items="${confTypeList}" varStatus="status">
__conf_type_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __confirm_code_list = [];
<c:forEach var="code" items="${confirmCodeList}" varStatus="status">
__confirm_code_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __buyout_type_list = [];
<c:forEach var="code" items="${buyoutTypeList}" varStatus="status">
__buyout_type_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __bord_type_list = [];
<c:forEach var="code" items="${bordTypeList}" varStatus="status">
__bord_type_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __buy_deal_type1_list = [];
<c:forEach var="code" items="${buyDealType1List}" varStatus="status">
__buy_deal_type1_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __buy_deal_type2_list = [];
<c:forEach var="code" items="${buyDealType2List}" varStatus="status">
__buy_deal_type2_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __buy_deal_type3_list = [];
<c:forEach var="code" items="${buyDealType3List}" varStatus="status">
__buy_deal_type3_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __buy_deal_type4_list = [];
<c:forEach var="code" items="${buyDealType4List}" varStatus="status">
__buy_deal_type4_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __mna_deal_type1_list = [];
<c:forEach var="code" items="${mnaDealType1List}" varStatus="status">
__mna_deal_type1_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __mna_deal_type2_list = [];
<c:forEach var="code" items="${mnaDealType2List}" varStatus="status">
__mna_deal_type2_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __mna_deal_type3_list = [];
<c:forEach var="code" items="${mnaDealType3List}" varStatus="status">
__mna_deal_type3_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __curr_list = [];
Ajax.post("CurrencySearch", {}, function(json) {
	if(json.result) {
		var data = json.data.list;
		for(var i=0; i<data.length; i++){
			__curr_list.push({code:data[i].etcocd, name:data[i].etcocd + '/' + data[i].etcdnm});
		}
	}
});
</script>
<script src="js/contents/client/ltDetailLeagueTable.js" charset='utf-8'></script>
<script>
$(document).ready(function() {
	DetailLT.init("<%= prjtcd %>", "<%= condition %>", "<%= type %>");
});
</script>
<body>
	<div class="dc_view_area">
		<div class="dc_view_top">
			<div class="dc_btn_area">
				<div class="dc_btn_area_lft">
					<button type="button" class="btn btn_red ico_title"
						id="btn_detail_title" style="display: none">목록</button>
				</div>
				<div class="dc_btn_area_rgt">
					<c:if test="${authChargerConfirm}">
						<button type="button" class="btn btn_red ico_check"
							id="btn_detail_chrgconf" style="display: none">본부 담당자 확인</button>
					</c:if>
					<c:if test="${authETConfirm}">
						<button type="button" class="btn btn_red ico_check"
							id="btn_detail_etconf" style="display: none">Engagement
							Team 확인</button>
					</c:if>
					<button type="button" class="btn btn_red ico_edit"
						id="btn_detail_modify" style="display: none">수정</button>
					<button type="button" class="btn btn_red ico_save"
						id="btn_detail_save" style="display: none">저장</button>
					<button type="button" class="btn btn_red ico_reset"
						id="btn_detail_reset" style="display: none">취소</button>
				</div>
			</div>
		</div>
		<div class="dc_view_body"></div>
	</div>
</body>