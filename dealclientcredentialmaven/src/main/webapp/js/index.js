var INDEX_OBJ = (function(){
	var m_serviceBroker = CURR_INFO.serviceBroker;
	var m_classNm 		= "global";
	var infoPop			= null;
	var m_sideBarObj 	= {};
	function init(){
		// 브라우저 백버튼 막기
		history.pushState(null, null, location.href);
		window.onpopstate = function(event) { 
			history.go(1);
		}

		// 최상단 탭 메뉴
		var _h = [];
		for(var i = 0; i < user_Menus.length; i++){
			if(user_Menus[i].level == '1'){
				_h.push('<li data-menuid="'+user_Menus[i].menuid +'" data-menuurl="'+user_Menus[i].url+'" data-menunm="'+ user_Menus[i].menunm +'" ');
				if(i == 0) {
					_h.push('data-menusel="Y" class="on">');
				} else {
					_h.push('data-menusel="N" class="">');
				}		
				_h.push('<span class="font_eng">'+user_Menus[i].menunm+'</span></li>');
			}
		}
		$("#dc_tab_list").html(_h.join(""));
		
		var select_menu_ele = $(".dc_tab_list").find("[data-menusel='Y']")[0];
		if ($(select_menu_ele).attr("data-menuurl") != "") {
			$("#dc_con_menu").addClass("hidden");
			doLoadMenuContent({menuid:$(select_menu_ele).attr("data-menuid"), menuurl:$(select_menu_ele).attr("data-menuurl")});
		} else {
			doMakeSubNav($(select_menu_ele).attr("data-menuid"), $(select_menu_ele).attr("data-menunm"));
		}
		$(".dc_tab_list > li").off("click").on("click", onSelectRootMenu);
		
		$("#user").html(USERINFO.getKorName() + " (" + USERINFO.getEngName() + ")").on("click", function(){showPopup(this);});
	}; 
	
	function showPopup(inp) {
		if (!infoPop) {
			infoPop = new dhtmlXPopup({
				mode : "bottom"
			});
			infoPop.attachHTML("<span style='font-weight: bold; font-size: 15px;'>"
					+ USERINFO.getEngName() + "</span>&nbsp&nbsp (" + USERINFO.getEmplNo()
					+ ")<br><br>" + USERINFO.getTeamNm() + " / " + USERINFO.getGradNm() + "<br>"
					+ USERINFO.getEmail());
		}
		
		if (infoPop.isVisible()) {
			infoPop.hide();
		} else {
			var x = $(inp).position().left;
			var y = $(inp).position().top+30;
			var w = inp.offsetWidth;
			var h = inp.offsetHeight;
			infoPop.show(x, y, w, h);
		}
	};
	
	function onSelectRootMenu(){
		selected_menu.menuid = $(this).attr("data-menuid");
		selected_menu.menunm = $(this).attr("data-menunm");
		$(".dc_tab_list > li").removeClass("on");
		$(this).addClass("on");
		
		if(selected_menu.menuid == 1){
			$("#dc_con_article").addClass("no-margin");
		}else{
			$("#dc_con_article").removeClass("no-margin");
		}
		
		if ($(this).attr("data-menuurl") != "") {
			$("#dc_con_menu").addClass("hidden");
			doLoadMenuContent({menuid:$(this).attr("data-menuid"), menuurl:$(this).attr("data-menuurl")});			
		} else {
			doMakeSubNav(selected_menu.menuid, selected_menu.menunm);
		}
	}
	
	function onSelectSubMenu(){
		$(".menu_list_li").removeClass("on");
		$(this).parent().addClass("on");
		doLoadMenuContent({menuid:$(this).parent().attr("data-menuid"), menuurl:$(this).parent().attr("data-menuurl")});
	}
	
	function doMakeSubNav(upmenuid, upmenunm) {
		$("#dc_con_menu").removeClass("hidden");
		
		var _h = [];
		for(var i = 0; i < user_Menus.length; i++){
			var _menu = user_Menus[i];
			if (_menu.upmenuid == upmenuid) {
				var _subCount = doCountSubMenuItem(_menu.menuid);
				_h.push('<li data-menuid="'+ _menu.menuid +'" data-menuurl="'+ _menu.url +'" data-menusel="N" ');
				if (_subCount == 0) {
					_h.push('class="menu_list_li">');
					_h.push('<a class="menu_link">'+ _menu.menunm +'</a>');
				} else {
					_h.push('>');
					_h.push('<span class="menu_mas_link">'+ _menu.menunm +'</span>');
					if (_subCount > 0) {
						_h.push('<ul class="dep02">');
						_h.push(doMakeSubMenuItem(_menu.menuid));
						_h.push('</ul>');
					}
				}
				_h.push('</li>');
			}
		}
		$("#menu_list").empty().append(_h.join(''));
		
		doLoadMenuContent({menuid:$($("li.menu_list_li")[0]).data("menuid"), menuurl:$($("li.menu_list_li")[0]).data("menuurl")});
		$($("li.menu_list_li")[0]).addClass("on");
		
		$(".menu_link").off("click").on("click", onSelectSubMenu);
	}
	
	function doCountSubMenuItem(upmenuid) {
		var _count = 0;
		for(var i = 0; i < user_Menus.length; i++){
			var _menu = user_Menus[i];
			if (_menu.upmenuid == upmenuid) {
				_count++;
			}
		}
		return _count;
	}
	
	function doMakeSubMenuItem(upmenuid) {
		var _h = [];
		for(var i = 0; i < user_Menus.length; i++){
			var _menu = user_Menus[i];
			if (_menu.upmenuid == upmenuid) {
				_h.push('<li data-menuid="'+ _menu.menuid +'" data-menuurl="'+ _menu.url +'" data-menusel="N" class="menu_list_li">');
				_h.push('<a class="menu_link">'+ _menu.menunm + '</a>');
				_h.push('</li>');
			}
		}
		return _h.join("");
	}
	
	function doLoadMenuContent(menu) {
		if (!menu.menuurl) {
			location.href = "comn/UserFollow.jsp";
			return false;
		}
		Ajax.jsp(menu.menuurl, {}, doLoadedContent);
	}
	
	function doLoadedContent(html) {
		$(".dc_con_article").empty().html(html);
	}
	
	
	return {
		init:init
	};
}());