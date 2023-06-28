<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<!-- CSS Loading --------------------------------------------------------------------------------------------------------------------------- -->
<style>
</style>
<!-- JS Library Loading -------------------------------------------------------------------------------------------------------------------- -->
<script src="js/contents/admin/role.js" charset='utf-8'></script>
<script>

	</script>
</head>
<body>
	<div class="dc_divs_area">
		<div class="dc_divs_left">
			<div class="divs_btn_area">
				<button type="button" class="btn_gray_wide" id="btn_role_add">추가</button>
				<button type="button" class="btn_red_wide" id="btn_role_remove">삭제</button>
				<button type="button" class="btn_red_wide" id="btn_role_save">저장</button>
			</div>
			<div class="divs_con_area">
				<div class="ds_scroll_box">
					<!-- 컨텐츠 또는 트리구조  -->
					<div class="dc_con_div dc_list" id="div-grid-role"></div>
				</div>
			</div>
		</div>
		<div class="dc_divs_right">
			<span style="display: none;"><b id="select_role_name"
				rolecd=""></b></span>
			<div class="divs_btn_area">
				<button type="button" class="btn_gray_wide" id="btn_member_add">추가</button>
				<button type="button" class="btn_red_wide" id="btn_member_remove">삭제</button>
				<button type="button" class="btn_red_wide" id="btn_member_save">저장</button>
			</div>
			<div class="divs_con_area">
				<div class="ds_scroll_box">
					<div class="dc_con_div dc_detail_sub" id="div-grid-member"></div>
					<!-- 컨텐츠 또는 트리구조  -->
					<!-- <div class="dc_con_div dc_detail">
							<div class="dc_con_div dc_detail_sub" id="div-grid-member"></div>
						</div> -->
				</div>
			</div>
		</div>
	</div>
	<!-- <div class="ad_top_area non_border" style="width:100%">
			<div class="ad_list_btn" style="width:450px; float:left;">
				<span style="float:right;">
					<button type="button" class="btn_gray_wide" id="btn_role_add">추가</button>
					<button type="button" class="btn_red_wide" id="btn_role_remove">삭제</button>
					<button type="button" class="btn_red_wide" id="btn_role_save">저장</button>
				</span>
			</div>
			<div class="ad_detail_list_btn" style="width:500px; float:left;">
				<div class="ad_top_lft">
				<span><b id="select_role_name" rolecd=""></b></span>
				</div>
				<span style="float:right;">
					<button type="button" class="btn_gray_wide" id="btn_member_add">추가</button>
					<button type="button" class="btn_red_wide" id="btn_member_remove">삭제</button>
					<button type="button" class="btn_red_wide" id="btn_member_save">저장</button>
				</span>
			</div>
		</div>
		<div class="ad_con_area" id="div-grid-area">
			<div class="ad_con_div ad_list" id=div-grid-role style="width:450px; height:400px; float:left;"></div>
			<div class="ad_con_div ad_detail_sub" id="div-grid-member" style="width:500px; height:450px; float:left;"></div>
		</div> -->
</body>
</html>