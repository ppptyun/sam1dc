<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.samil.dc.domain.CommonCodeListBean"%>
<%@ page import="com.samil.dc.service.ApplicationGlobal"%>
<%@ page import="com.samil.dc.service.worker.ServiceHelper"%>
<%
String credComType = "120";
List<CommonCodeListBean> credComTypeList = ApplicationGlobal.getCommonCodeList(credComType);
request.setAttribute("credComTypeList", credComTypeList);

String securityType = "122";
List<CommonCodeListBean> securityTypeList = ApplicationGlobal.getCommonCodeList(securityType);
request.setAttribute("securityTypeList", securityTypeList);

String unsecuredType = "126";
List<CommonCodeListBean> unsecuredTypeList = ApplicationGlobal.getCommonCodeList(unsecuredType);
request.setAttribute("unsecuredTypeList", unsecuredTypeList);

String rcfType = "124";
List<CommonCodeListBean> rcfTypeList = ApplicationGlobal.getCommonCodeList(rcfType);
request.setAttribute("rcfTypeList", rcfTypeList);

String rcfLeisure = "130";
List<CommonCodeListBean> rcfLeisureList = ApplicationGlobal.getCommonCodeList(rcfLeisure);
request.setAttribute("rcfLeisureList", rcfLeisureList);

String rcfInfra = "131";
List<CommonCodeListBean> rcfInfraList = ApplicationGlobal.getCommonCodeList(rcfInfra);
request.setAttribute("rcfInfraList", rcfInfraList);
%>
<!-- JS Library Loading -------------------------------------------------------------------------------------------------------------------- -->
<script>
var __cred_com_type_list = [];
<c:forEach var="code" items="${credComTypeList}" varStatus="status">
__cred_com_type_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __security_type_list = [];
<c:forEach var="code" items="${securityTypeList}" varStatus="status">
__security_type_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __unsecured_type_list = [];
<c:forEach var="code" items="${unsecuredTypeList}" varStatus="status">
__unsecured_type_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __rcf_type_list = [];
<c:forEach var="code" items="${rcfTypeList}" varStatus="status">
__rcf_type_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __rcf_leisure_list = [];
<c:forEach var="code" items="${rcfLeisureList}" varStatus="status">
__rcf_leisure_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>

var __rcf_infra_list = [];
<c:forEach var="code" items="${rcfInfraList}" varStatus="status">
__rcf_infra_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
</c:forEach>
</script>

<style>
/* #btn_detail_search_option{width:23px;padding:0;} */
.valign-bottom * {
	display: inline-block;
	margin-top: 10px
}

.dc_search_detail_1 {
	padding: 15px 10px 10px;
	border: 1px solid #afafaf;
	margin-top: 10px
}

.dc_search_detail_1 .search_type_comp_area {
	position: absolute;
	display: inline-block;
	margin-top: -30px;
	margin-right: 20px;
	background-color: #fff;
	padding: 5px 10px;
	border: 1px solid #afafaf
}

.dc_search_detail_1 .dc_search_table {
	margin-top: 10px
}

.dc_search_detail_2 {
	padding: 0px;
	margin-top: 10px;
	margin-top: 10px;
	border: 1px solid #afafaf;
}
</style>
<script>
function test(){
	var amtfrom = $.trim($("#search_amt_from").val());
	var amtto = $.trim($("#search_amt_to").val());
	
	if(amtfrom.length>0 && amtto.length>0){
		if(amtfrom < 0 || amtto < 0){
			alert("거래규모의 값은 0보다 커야합니다.")
			return false;
		}
		if(amtfrom > amtto){
			alert("거래규모 값의 범위가 유효하지 않습니다.")
		}
	}else if( (amtfrom.length>0 && amtfrom < 0) || (amtto.length>0 && amtto < 0)){
		alert("거래규모의 값은 0보다 커야합니다.")
		return false;
	}
	
	return true;
}
</script>
<script src="external-library/bootstrap/js/bootstrap-tooltip.js"></script>
<script src="js/contents/client/credential.js" charset='utf-8'></script>
<body>
	<div class="dc_con_area">
		<div class="dc_search_area">
			<div class="search_head">
				<dl>
					<dt class="font_eng">Product Code</dt>
					<!-- <dd>
						<div id="pdt_ctg_list"></div>
						<div id="pdt_list"></div>
					</dd> -->

					<dd>
						<div class="dc_frm_multi_select">
							<span id="pdt_ctg_list"></span> <span id="pdt_list"></span>
						</div>
					</dd>
				</dl>
				<dl>
					<dt>용역종료일</dt>
					<dd>
						<div class="dc_frm_period">
							<span> <input type="text" readonly="readonly"
								class="inp_text" id="inp_cal1" />
								<button type="button" class="btn ico_delete btn_remove_date"
									title="삭제">삭제</button>
								<button type="button" class="btn btn_cal" title="날짜선택"
									id="btn_cal1">날짜선택</button>
							</span> <span class="dash"> <input type="text"
								readonly="readonly" class="inp_text" id="inp_cal2" />
								<button type="button" class="btn ico_delete btn_remove_date"
									title="삭제">삭제</button>
								<button type="button" class="btn btn_cal" title="날짜선택"
									id="btn_cal2">날짜선택</button>
							</span>
						</div>
					</dd>
				</dl>
				<dl>
					<dt>용역명</dt>
					<dd>
						<div>
							<input type="text" class="inp_text" id="prjtnm" size="15" />
						</div>
					</dd>
				</dl>
				<dl>
					<dd class="font_eng">
						<input type="checkbox" id="check_pe" class="font_eng" /> <label
							for="check_pe">PE/자산운용사</label> <span class="ico_tooltip"
							data-tooltip-id="pe"></span>
					</dd>
					<dd class="font_eng">
						<input type="checkbox" id="check_cross" class="font_eng" /> <label
							for="check_cross">Cross-Border</label> <span class="ico_tooltip"
							data-tooltip-id="cross"></span>
					</dd>
				</dl>
				<!-- <button type="button" class="btn btn_red btn_search" id="btn_detail_search_option"><span class="detail_expand">▼</span><span class="detail_collapse" style="display:none">▲</span></button> -->
				<button type="button" class="btn btn_red btn_search"
					id="btn_detail_search_option">상세검색</button>
				<button type="button" class="btn btn_red btn_search" id="btn_search">검색</button>
			</div>
			<div class="search_detail" id="search_detail">
				<div>
					<div class="dc_search_detail_1">
						<div class="search_type_comp_area">
							<input type="radio" id="search_type_comp1"
								name="search_type_comp" value="or" checked /><label
								for="search_type_comp1">OR</label> <input type="radio"
								id="search_type_comp2" name="search_type_comp" value="and" /><label
								for="search_type_comp2">AND</label>
						</div>
						<table class="dc_search_table">
							<colgroup>
								<col width="8%" />
								<col width="6%" />
								<col width="25%" />
								<col width="6%" />
								<col width="31%" />
								<col width="6%" />
								<col width="*" />
							</colgroup>
							<tr>
								<td class="bg_red font_eng">Client</td>
								<td class="bg_gray">회사명</td>
								<td><input type="text" class="inp_text inp_change_cre"
									id="client_company" data-code="" /></td>
								<td class="bg_gray font_eng">Industry</td>
								<td>
									<!-- <div class="dc_frm_rgt_btn"> -->
									<div class="data_cell rgt_btn">
										<span class="inp_radio_list"> <input type="radio"
											name="client_ind_chk" id="client_ind_chk_1" value="glinducd"><label
											for="client_ind_chk_1">중분류</label> <input type="radio"
											name="client_ind_chk" id="client_ind_chk_2" value="inducd"
											checked><label for="client_ind_chk_2">소분류</label>
										</span> <input type="text" readonly="readonly"
											class="inp_text inp_change" id="client_industry" data-code=""
											style="width: 66%; padding-right: 0px;" />
										<button type="button" class="btn ico_delete btn_remove_data"
											title="삭제">삭제</button>
										<button type="button" class="btn btn_red btn_industry_sel">선택</button>
									</div>
								</td>
								<td class="bg_gray">국가</td>
								<td>
									<div class="" id="client_nation_list">
										<!-- <input type="text" readonly="readonly" class="inp_text" id="client_nation" data-code="" />
									<button type="button" class="btn btn_red btn_nation_sel">선택</button> -->
									</div>
								</td>
							</tr>
							<tr>
								<td class="bg_red font_eng">Target</td>
								<td class="bg_gray">회사명</td>
								<td><input type="text" class="inp_text inp_change_cre"
									id="target_company" data-code="" /></td>
								<td class="bg_gray font_eng">Industry</td>
								<td>
									<!-- <div class="dc_frm_rgt_btn"> -->
									<div class="data_cell rgt_btn">
										<span class="inp_radio_list"> <input type="radio"
											name="target_ind_chk" id="target_ind_chk_1" value="glinducd"><label
											for="target_ind_chk_1">중분류</label> <input type="radio"
											name="target_ind_chk" id="target_ind_chk_2" value="inducd"
											checked><label for="target_ind_chk_2">소분류</label>
										</span> <input type="text" readonly="readonly"
											class="inp_text inp_change" id="target_industry" data-code=""
											style="width: 66%; padding-right: 0px;" />
										<button type="button" class="btn ico_delete btn_remove_data"
											title="삭제">삭제</button>
										<button type="button" class="btn btn_red btn_industry_sel">선택</button>
									</div>
								</td>
								<td class="bg_gray">국가</td>
								<td>
									<div class="" id="target_nation_list">
										<!-- <input type="text" readonly="readonly" class="inp_text" id="target_nation" data-code="" />
									<button type="button" class="btn btn_red btn_nation_sel">선택</button> -->
									</div>
								</td>
							</tr>
							<tr>
								<td class="bg_red">이해관계자</td>
								<td class="bg_gray">회사명</td>
								<td><input type="text" class="inp_text inp_change_cre"
									id="concerned_company" data-code="" /></td>
								<td class="bg_gray font_eng">Industry</td>
								<td>
									<!-- <div class="dc_frm_rgt_btn"> -->
									<div class="data_cell rgt_btn">
										<span class="inp_radio_list"> <input type="radio"
											name="concerned_ind_chk" id="concerned_ind_chk_1"
											value="glinducd"><label for="concerned_ind_chk_1">중분류</label>
											<input type="radio" name="concerned_ind_chk"
											id="concerned_ind_chk_2" value="inducd" checked><label
											for="concerned_ind_chk_2">소분류</label>
										</span> <input type="text" readonly="readonly"
											class="inp_text inp_change" id="concerned_industry"
											data-code="" style="width: 66%; padding-right: 0px;" />
										<button type="button" class="btn ico_delete btn_remove_data"
											title="삭제">삭제</button>
										<button type="button" class="btn btn_red btn_industry_sel">선택</button>
									</div>
								</td>
								<td class="bg_gray">국가</td>
								<td>
									<div class="" id="concerned_nation_list">
										<!-- <input type="text" readonly="readonly" class="inp_text" id="concerned_nation" data-code="" />
									<button type="button" class="btn btn_red btn_nation_sel">선택</button> -->
									</div>
								</td>
							</tr>
						</table>
					</div>

					<div class="dc_search_detail_2">
						<table class="dc_search_table">
							<colgroup>
								<col width="8%" />
								<col width="6%" />
								<col width="25%" />
								<col width="6%" />
								<col width="31%" />
								<col width="6%" />
								<col width="*" />
							</colgroup>
							<tr authRole="EXCLUDEROLE">
								<td class="bg_red">Transaction</td>
								<td class="bg_gray">거래규모</td>
								<td colspan="5"><span id="search_amt"></span> <!-- <input type="number" min="0" max="99999999999999" maxlength="14" size="14" class="inp_text" id="search_amt_from" style="width:150px"/>
									<span> ~ </span>
									<input type="number" min="0" max="99999999999999" maxlength="14" size="14" class="inp_text" id="search_amt_to" style="width:150px" />
									<button class="btn btn_red" onclick="test()">테스트</button> --></td>
							</tr>
							<tr>
								<td class="bg_red">BRS</td>
								<td class="bg_gray">담보</td>
								<td id="search_secu_list"></td>
								<td class="bg_gray">무담보</td>
								<td id="search_unse_list" colspan="3"></td>
							</tr>
							<tr>
								<td class="bg_red">RE</td>
								<td class="bg_gray">위치</td>
								<td><input type="text" class="inp_text inp_change_cre"
									id="search_rcf_location" /></td>
								<td class="bg_gray">구분</td>
								<td>
									<div class="" id="search_rcf_list"></div>
								</td>
								<td class="bg_gray">구분상세</td>
								<td>
									<div class="" id="search_rcf_detail_list"></div>
								</td>
							</tr>
							<tr authRole="EXCLUDEROLE" style="display: none">
								<td class="bg_red" colspan="2">제외된 Credential 검색</td>
								<td colspan="5"><div class="inp_check_list">
										<input type="checkbox" id="check_credtgtcd" /><label
											for="check_credtgtcd">Yes</label>
									</div></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="dc_btn_area">
			<div class="dc_btn_area_lft valign-bottom">
				<span>&lt;검색 결과&nbsp;:&nbsp;</span><span id="totalcount">0</span><span>&nbsp;건&gt;</span>
			</div>
			<div class="dc_btn_area_rgt">
				<button type="button" class="btn btn_red" id="btn_guidance">Guidance</button>
				<button type="button" class="btn btn_red ico_down"
					id="ppt_download" authRole="DOWNLOADROLE" style="display: none">PPT 변환</button>
				<button type="button" class="btn btn_red ico_down"
					id="exel_download" authRole="DOWNLOADROLE" style="display: none">다운로드</button>
				<button type="button" class="btn btn_red ico_up"
					id="exel_prjt_exception" authRole="EXCLUDEROLE"
					style="display: none">대상 제외</button>
				<!-- <button type="button" class="btn btn_red ico_up" id="exel_upload" authRole="IMPORTROLE" style="display:none">업로드</button> -->
			</div>
		</div>
		<div class="dc_scroll_table_area" id="div-grid-area"></div>
	</div>
</body>
