<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.samil.dc.domain.CommonCodeListBean"%>
<%@ page import="com.samil.dc.service.ApplicationGlobal"%>
<%
//롤
List<CommonCodeListBean> roleCodeList = ApplicationGlobal.getRoleCodeList();
request.setAttribute("roleCodeList", roleCodeList);
%>
<!doctype html>
<html>
<head>
<script>
		var __role_code_list = [];
		<c:forEach var="code" items="${roleCodeList}" varStatus="status">
		__role_code_list.push({code:'${code.itemcd}', name:'${code.itemnm}'});
 		</c:forEach>
	</script>

<!-- CSS Loading --------------------------------------------------------------------------------------------------------------------------- -->
<style>
</style>
<!-- JS Library Loading -------------------------------------------------------------------------------------------------------------------- -->
<script src="js/contents/admin/menu.js" charset='utf-8'></script>
<script>
	
	</script>
</head>
<body>
	<div class="dc_divs_area">
		<div class="dc_divs_left">
			<div class="divs_btn_area">
				<button type="button" class="btn_gray_wide" id="addMenu">추가</button>
				<button type="button" class="btn_red_wide" id="deleteMenu">삭제</button>
			</div>
			<div class="divs_con_area">
				<div class="ds_scroll_box">
					<div class="dc_con_div dc_tree" id="menuTree" style="height: 100%"></div>
				</div>
			</div>
		</div>
		<div class="dc_divs_right">
			<div class="divs_btn_area">
				<button type="button" class="btn_red_wide" id="saveMenu">저장</button>
			</div>
			<div class="divs_con_area">
				<div class="ds_scroll_box" style="height: 46%;">
					<!-- 높이를 % 또는 px 값으로 지정을 해주세요 -->
					<div class="dc_con_div dc_detail_half" style="">
						<div class="edit_menu_frm" id="edit_menu_frm"></div>
					</div>
				</div>

				<div class="ds_scroll_box"
					style="height: 54%; padding-top: 20px; width: 100%; overflow: hidden">
					<div class="dc_btn_area">
						<div class="dc_btn_area_rgt">
							<button type="button" class="btn_gray_wide" id="addRole">추가</button>
							<button type="button" class="btn_red_wide" id="deleteRole">삭제</button>
							<button type="button" class="btn_red_wide" id="saveRole">저장</button>
						</div>
					</div>
					<div class="dc_sub_grid" id="edit_role_grid"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- <div class="ad_top_area non_border" style="width:100%">
			<div class="ad_tree_btn" style="width:450px; float:left;">
				<span style="float:right;">
					<button type="button" class="btn_gray_wide" id="addMenu">추가</button>
					<button type="button" class="btn_red_wide" id="deleteMenu">삭제</button>
				</span>
			</div>
			<div class="ad_detail_btn" style="width:500px; float:left;">
				<span style="float:right;">
					<button type="button" class="btn_red_wide" id="saveMenu">저장</button>
				</span>
			</div>					
		</div>														
		<div class="ad_con_area" id="div-grid-area">
			<div class="ad_con_div ad_tree" id="menuTree" style="width:450px; height:470px; float:left;"></div>
			<div class="ad_con_div ad_detail_half" style="width:500px; height:150px; float:left;">
				<div class="edit_menu_frm" id="edit_menu_frm"></div>
			</div>
			<div class="ad_con_div ad_sub_grid_area" style="width:500px; height:120px; float:right;">
				<div class="ad_sub_grid_btn" id="edit_role_grid_btn">
					<span style="float:right;">
						<button type="button" class="btn_gray_wide" id="addRole">추가</button>
						<button type="button" class="btn_red_wide" id="deleteRole">삭제</button>
						<button type="button" class="btn_red_wide" id="saveRole">저장</button>
					</span>						
				</div>
				<div class="ad_sub_grid" id="edit_role_grid" style="width:300px; height:100px;"></div>
			</div>
		</div> -->
</body>
</html>