<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>

<!-- CSS Loading --------------------------------------------------------------------------------------------------------------------------- -->
<style>
</style>
<!-- JS Library Loading -------------------------------------------------------------------------------------------------------------------- -->
<script src="js/contents/admin/commCode.js" charset='utf-8'></script>
<script>
	
	
// 	$(document).ready(function() {
		
		
		
		
		
		
// // 		myTreeView = new dhtmlXTreeView({
// // 		    parent:         "treeviewObj",  // id/object, container for treeview
// // 		    skin:           "dhx_terrace",  // string, optional, treeview's skin
// // 		    iconset:        "font_awesome", // string, optional, sets the font-awesome icons
// // 		    multiselect:    true,           // boolean, optional, enables multiselect
// // 		    checkboxes:     true,           // boolean, optional, enables checkboxes
// // 		    dnd:            true,           // boolean, optional, enables drag-and-drop
// // 		    context_menu:   true,           // boolean, optional, enables context menu
// // 		    json:           "filename.json",// string, optional, json file with struct
// // 		    xml:            "filename.xml", // string, optional, xml file with struct
// // 		    items:          [],             // array, optional, array with tree struct
// // 		    onload:         function(){}    // callable, optional, callback for load
// // 		});
// // 		myTreeView.init();
// 	});
	
	</script>
</head>
<body>
	<div class="dc_divs_area">
		<div class="dc_divs_left">
			<div class="divs_btn_area">
				<button type="button" class="btn_gray_wide" id="addCommcode">추가</button>
				<button type="button" class="btn_red_wide" id="deleteCommcode">삭제</button>
			</div>
			<div class="divs_con_area">
				<div class="ds_scroll_box">
					<!-- 컨텐츠 또는 트리구조  -->
					<!-- <div class="dc_con_div dc_tree" id="commCodeTree" style="height:445px;"></div> -->
					<div class="dc_con_div dc_tree" id="commCodeTree"
						style="height: 100%; width: 100%"></div>
				</div>
			</div>
		</div>
		<div class="dc_divs_right">
			<div class="divs_btn_area">
				<button type="button" class="btn_red_wide" id="saveCommcode">저장</button>
			</div>
			<div class="divs_con_area">
				<div class="ds_scroll_box">
					<!-- 컨텐츠 또는 트리구조  -->
					<div class="dc_con_div dc_detail">
						<div class="edit_code_frm" id="edit_code_frm"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- <div class="ad_top_area non_border" style="width:100%">
			<div class="ad_tree_btn" style="width:450px; float:left;">
				<span style="float:right;">
					<button type="button" class="btn_gray_wide" id="addCommcode">추가</button>
					<button type="button" class="btn_red_wide" id="deleteCommcode">삭제</button>
				</span>
			</div>
			<div class="ad_detail_btn" style="width:500px; float:left;">
				<span style="float:right;">
					<button type="button" class="btn_red_wide" id="saveCommcode">저장</button>
				</span>
			</div>					
		</div>														
		<div class="ad_con_area" id="div-grid-area">
			<div class="ad_con_div ad_tree" id="commCodeTree" style="width:450px; height:400px; float:left;"></div>
			<div class="ad_con_div ad_detail" style="width:500px; height:450px; float:left;">
				<div class="edit_code_frm" id="edit_code_frm"></div>
			</div>
		</div> -->
</body>
</html>