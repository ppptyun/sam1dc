<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/manage.css">
<!-- CSS Loading --------------------------------------------------------------------------------------------------------------------------- -->
<style>
</style>
<!-- JS Library Loading -------------------------------------------------------------------------------------------------------------------- -->

<script>
	$(document).ready(function() {
		var _h = [];
		var _mInd = 0;
		for(var i in user_Menus){
			if(user_Menus[i].upmenuid == selected_menu.menuid){
				_h.push('<li data-menuid="'+user_Menus[i].menuid +'" data-menuurl="'+user_Menus[i].url+'" ');
				if(_mInd == 0) {
					_h.push('data-menusel="Y" class="on');
				} else {
					_h.push('data-menusel="N" class="');
				}		
				_h.push(' nav-item"><span class="nav-link">'+user_Menus[i].menunm+'</span></li>');
				_mInd++;
			}
		}
		$("#nav-list").html(_h.join(""));
		$("#nav-top").html('<h2>' + selected_menu.menunm + '</h2>');
		
		var select_menu_ele = $(".nav-list").find("[data-menusel='Y']")[0];
		var select_menu = {menuid:$(select_menu_ele).attr("data-menuid"), menuurl:$(select_menu_ele).attr("data-menuurl")};		
		doLoadSubMenuContent(select_menu);
		$(".nav-list > li").off("click").on("click", onSelectSubMenu);		
		
	});
	
	function onSelectSubMenu(){
		$(".nav-list > li").removeClass("on");
		$(this).addClass("on");
		doLoadSubMenuContent({menuid:$(this).attr("data-menuid"), menuurl:$(this).attr("data-menuurl")});
	}
	
	function doLoadSubMenuContent(menu) {
		if (!menu.menuurl) {
			alert("선택된 메뉴가 없습니다.");
			return false;
		}
		Ajax.jsp(menu.menuurl, {}, doLoadedSubContent);
	}
	
	function doLoadedSubContent(html) {
		$(".ad_con_article").empty().append(html);
	}
	
	</script>
</head>
<body>
	<div class="ad_con_body">
		<div class="ad_con_nav" id="ad_con_nav">
			<div class="nav-top" id="nav-top"></div>
			<ul class="nav-list" id="nav-list">
			</ul>
		</div>
		<div class="ad_con_article" id="ad_con_article"></div>
	</div>
</body>
</html>