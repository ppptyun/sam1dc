(function(){
	// 변수 선언
	var thisObj = this;
	var ATTR_NEW_ROW = "is_new_added";
	var m_gridRoleDefinition = {
		parent:"div-grid-role",
		image_path:"./js/dhtmlx/codebase/imgs/",
		skin:"dhx_skyblue",
		columns:[
		         	{id:"rolecd", label:["롤 코드"], type:"ed", align:"center", valign:"middle", width:"80", sort: "str"},
		         	{id:"rolenm", label:["롤 이름"], type:"ed", align:"left", valign:"middle", width:"*", sort: "str"},
		         	{id:"sort", label:["순번"], type:"ed", align:"center", valign:"middle", width:"60", sort: "str"},
		         	{id:"orirolecd", label:["원래롤"], type:"ro", align:"center", valign:"middle", width:"0", sort: "str"}
				]
	};
	var m_gridMemberDefinition = {
		parent:"div-grid-member",
		image_path:"./js/dhtmlx/codebase/imgs/",
		skin:"dhx_skyblue",
		columns:[
		         	{id:"empno", label:["사번"], type:"ro", align:"center", valign:"middle", width:"100", sort: "str"},
		         	{id:"empnm", label:["성명"], type:"ro", align:"center", valign:"middle", width:"100", sort: "str"},
		         	{id:"gradnm", label:["직급"], type:"ro", align:"left", valign:"middle", width:"150", sort: "str"},
		         	{id:"teamnm", label:["부서"], type:"ro", align:"left", valign:"middle", width:"250", sort: "str"}
				]
	};
	var m_gridRoleObj = null;
	var m_gridMemberObj = null;
	
	// init 함수 등록
	this.init = function(){
		// Role Grid
		if(!m_gridRoleObj){
			m_gridRoleObj = new dhtmlXGridObject(m_gridRoleDefinition);
			m_gridRoleObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px;font-size:12px", null, null, null);
			m_gridRoleObj.setColumnHidden(m_gridRoleObj.getColIndexById("orirolecd"), true);
		}
		// Member Grid
		if(!m_gridMemberObj){
			m_gridMemberObj = new dhtmlXGridObject(m_gridMemberDefinition);
			m_gridMemberObj.setInitWidthsP("15,15,25,*");
			m_gridMemberObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px;font-size:12px", null, null, null);
		}
		
		bindEvent();
		doLoadRoleList();
		
		resize();
		$(window).resize(resize);
	}
	
	// Bind Event
	function bindEvent(){
		// 롤 추가
		$("#btn_role_add").off("click").on("click", function(){
			var newId = (new Date()).valueOf();
			m_gridRoleObj.addRow(newId, "R,,");
			m_gridRoleObj.cells(newId, m_gridRoleObj.getColIndexById("rolecd")).setAttribute(ATTR_NEW_ROW, true);
			
		});
		
		// 롤 삭제
		$("#btn_role_remove").off("click").on("click", function(){
			var rowId = m_gridRoleObj.getSelectedRowId();
			if (rowId) {
				var codeCell = m_gridRoleObj.cells(rowId, m_gridRoleObj.getColIndexById("rolecd"));
				if (codeCell.getAttribute(ATTR_NEW_ROW)) {
					m_gridRoleObj.deleteRow(rowId);
				} else {
					if (confirm("정말로 삭제 하시겠습니까?")) {
						var items = [];
						items.push(codeCell.getValue());
						// 서버 반영
						Ajax.post("AdminRoleDelete", {items:items.join("|")}, function(serverData){
							if (serverData.result) {
								alert("삭제되었습니다.");
								doLoadRoleList();
							} else {
								alert(serverData.error.errorMessage);
								return false;
							}
						});
					}
				}
			} else {
				alert("삭제할 항목을 선택해주세요.");
				return false;
			}
		});
		
		// 롤 저장
		$("#btn_role_save").off("click").on("click", function(){
			var rowIds = m_gridRoleObj.getChangedRows(true);
			if (rowIds) {
				var items = [];
				var arrRowIds = rowIds.split(",");
				for (var i=0; i<arrRowIds.length; i++) {
					var data = [];
					var rowId = arrRowIds[i];
					var codeCell = m_gridRoleObj.cells(rowId, m_gridRoleObj.getColIndexById("rolecd"));
					if (codeCell.getAttribute(ATTR_NEW_ROW)) {
						data.push("I");//신규
					} else {
						data.push("U");//수정
					}
					var rolecd		= codeCell.getValue();
					var rolenm		= m_gridRoleObj.cells(rowId, m_gridRoleObj.getColIndexById("rolenm")).getValue();
					var sort 		= m_gridRoleObj.cells(rowId, m_gridRoleObj.getColIndexById("sort")).getValue();
					var orirolecd 	= m_gridRoleObj.cells(rowId, m_gridRoleObj.getColIndexById("orirolecd")).getValue();
					if (rolecd == "") {
						alert("롤 코드를 입력해주세요.");
						m_gridRoleObj.selectRow(rowId);
						return false;
					}
					if (rolecd.length != 3) {
						alert("롤 코드는 3자리로 입력해주세요.");
						m_gridRoleObj.selectRow(rowId);
						return false;
					}
					if (rolecd.charAt(0) != 'R') {
						alert("롤 코드는 'R'문자로 시작해야합니다.");
						m_gridRoleObj.selectRow(rowId);
						return false;
					}
					if (rolenm == "") {
						alert("롤 이름을 입력해주세요.");
						m_gridRoleObj.selectRow(rowId);
						return false;
					}
					if (sort == "") {
						alert("순번을 입력해주세요.");
						m_gridRoleObj.selectRow(rowId);
						return false;
					}
					// TODO 유효성 체크 더 적극적으로....
					data.push(rolecd);
					data.push(rolenm);
					data.push(sort);
					data.push(orirolecd);
					items.push(data.join("^"));
				}
				// 서버 반영
				Ajax.post("AdminRoleSave", {items:items.join("|")}, function(serverData){
					if (serverData.result) {
						alert("저장되었습니다.");
						doLoadRoleList();
					} else {
						alert(serverData.error.errorMessage);
						return false;
					}
				});
			} else {
				alert("변경 혹은 추가된 항목이 없습니다.");
				return false;
			}
		});
		
		// 롤 멤버 추가
		$("#btn_member_add").off("click").on("click", function(){
			POPUP.ViewPopup("userSearch", null, function(info) {
				if (info) {
					var newId = (new Date()).valueOf();
					var user = [info.emplno, info.kornm, info.gradnm, info.teamnm];
					m_gridMemberObj.addRow(newId, user.join(","));
					m_gridMemberObj.cells(newId, m_gridMemberObj.getColIndexById("empno")).setAttribute(ATTR_NEW_ROW, true);
				}
			});
		});
		
		// 롤 멤버 삭제
		$("#btn_member_remove").off("click").on("click", function(){
			var rowId = m_gridMemberObj.getSelectedRowId();
			if (rowId) {
				var codeCell = m_gridMemberObj.cells(rowId, m_gridMemberObj.getColIndexById("empno"));
				if (codeCell.getAttribute(ATTR_NEW_ROW)) {
					m_gridMemberObj.deleteRow(rowId);
				} else {
					if (confirm("정말로 삭제 하시겠습니까?")) {
						var items = [];
						items.push(codeCell.getValue());
						var rolecd = $("#select_role_name").attr("rolecd");
						// 서버 반영
						Ajax.post("AdminRoleMemberDelete", {rolecd:rolecd, items:items.join("|")}, function(serverData){
							if (serverData.result) {
								alert("삭제되었습니다.");
								m_gridMemberObj.deleteRow(rowId);
							} else {
								alert(serverData.error.errorMessage);
								return false;
							}
						});
					}
				}
			} else {
				alert("삭제할 멤버를 선택해주세요.");
				return false;
			}
		});
		
		// 롤 멤버 저장
		$("#btn_member_save").off("click").on("click", function(){
			var rowIds = m_gridMemberObj.getChangedRows(true);
			if (rowIds) {
				var items = [];
				var arrRowIds = rowIds.split(",");
				for (var i=0; i<arrRowIds.length; i++) {
					var rowId = arrRowIds[i];
					var empno = m_gridMemberObj.cells(rowId, m_gridMemberObj.getColIndexById("empno")).getValue();
					items.push(empno);
				}
				var rolecd = $("#select_role_name").attr("rolecd");
				// 서버 반영
				Ajax.post("AdminRoleMemberSave", {rolecd:rolecd, items:items.join("|")}, function(serverData){
					if (serverData.result) {
						alert("저장되었습니다.");
						doLoadRoleMemberList($("#select_role_name").attr("rolecd"));
					} else {
						alert(serverData.error.errorMessage);
						return false;
					}
				});
			} else {
				alert("추가된 멤버가 없습니다.");
				return false;
			}
		});
		
		m_gridRoleObj.attachEvent("onRowSelect", function(id, ind){
			var codeCell = m_gridRoleObj.cells(id, m_gridRoleObj.getColIndexById("rolecd"));
			if (codeCell.getAttribute(ATTR_NEW_ROW)) {
				$("#select_role_name").attr("rolecd", "").html("");
				m_gridMemberObj.clearAll();
			} else {
				var rolecd = codeCell.getValue();
				var rolenm = m_gridRoleObj.cells(id, m_gridRoleObj.getColIndexById("rolenm")).getValue();
				$("#select_role_name").attr("rolecd", rolecd).html(rolenm);
				doLoadRoleMemberList(rolecd);				
			}
		});
	}
	
	// 롤 목록 조회
	function doLoadRoleList(){
		m_gridRoleObj.clearAll();
		m_gridMemberObj.clearAll();
		Ajax.post("AdminRoleList", {}, function(serverData){
			if (serverData.result) {
				var rolelist = serverData.data.list;
				m_gridRoleObj.parse(rolelist, "js");
				
				if (rolelist && rolelist.length > 0) {
					m_gridRoleObj.selectRow(0);
					$("#select_role_name").attr("rolecd", rolelist[0].rolecd).html(rolelist[0].rolenm);
					doLoadRoleMemberList(rolelist[0].rolecd);
				}
			} else {
				alert(serverData.error.errorMessage);
				return false;
			}
		});
	}
	
	// 롤 멤버 목록 조회
	function doLoadRoleMemberList(rolecd){
		m_gridMemberObj.clearAll();
		Ajax.post("AdminRoleMemberList", {rolecd:rolecd}, function(serverData){
			if (serverData.result) {
				var memberlist = serverData.data.list;
				m_gridMemberObj.parse(memberlist, "js");
			} else {
				alert(serverData.error.errorMessage);
				return false;
			}
		});
	}
	
	function resize(){
		// 왼쪽 그리드
		var lHeight = $(".dc_divs_left .ds_scroll_box").height() - 10;
		var lWdith	= $(".dc_divs_left .ds_scroll_box").width() - 2;
		var $role	= $("#div-grid-role");
		$role.height(lHeight);
		$role.width(lWdith);
		$role.find(".xhdr").width("100%");
		$role.find(".objbox").width("100%");
		$role.find(".objbox").height(lHeight - $role.find(".xhdr").height());
		
		// 오른쪽 그리드		
		var rHeight = $(".dc_divs_right .ds_scroll_box").height() - 10;
		var rWdith	= $(".dc_divs_right .ds_scroll_box").width() - 2;
		var $member	= $("#div-grid-member");
		$member.height(rHeight);
		$member.width(rWdith);
		$member.find(".xhdr").width("100%");
		$member.find(".objbox").width("100%");
		$member.find(".objbox").height(rHeight - $member.find(".xhdr").height());
	}
	
	$(document).ready(function(){
		thisObj.init();
	});
})();