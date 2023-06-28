(function(){
	var syscd			= CURR_INFO.systemCode;
	var thisObj = this;
	var gridMailObj = null;
	var gridRcptObj = null;
	var dpGridRcpt = null;
	var mailDetailObj = null;
	var editorObj	= [];
	var adminRole = ["R98", "R99"];
	var adminField = ["mailid"];
	var isAdmin	= false;
	var left_display = true; // 왼쪽 그리드 display 여부
	
	var gridMailDefinition = {
			parent:"div-grid-mail",
			image_path:"./js/dhtmlx/codebase/imgs/",
			skin:"dhx_skyblue",
			columns:[
			         	{id:"mailid", label:["메일ID"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"subject", label:["제목"], type:"ro", align:"left", valign:"middle", sort: "str"},
			         	{id:"useyn", label:["사용여부"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"}
					]
		};
	var gridRcptDefinition = {
			parent:"div-grid-rcpt",
			image_path:"./js/dhtmlx/codebase/imgs/",
			skin:"dhx_skyblue",
			columns:[
			         	{id:"rname", label:["이름"], type:"ro", align:"left", valign:"middle", width:"100", sort: "str"},
			         	{id:"rid", label:["사번"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
			         	{id:"rmail", label:["메일주소"], type:"ro", align:"left", valign:"middle",  sort: "str"},
					]
		};
	
	// 메일프로파일 초기화
	function initMailDetailObj(){
		mailDetailObj = {
			action:"",
			mailid:"",
			subject:"",
			contents:"",
			des:"",
			useyn:"",
			rcpt:""
		}
	}
	
	
	// 메일 프로파일 Role 권한 체크 (System, IT 만 추가 및 삭제 가능하게 함)
	function isExistRole(rolelist){
		for(var i=0; i<rolelist.length; i++){
			for(var j=0; j<USERROLE.length; j++){
				if(rolelist[i] == USERROLE[j].rolecd){
					return true;
				}
			}
		}
		return false;
	}
	
	// 초기화 함수
	function init(){
		isAdmin = isExistRole(adminRole);
		
		if(	isAdmin ){
			$("#mailprofile-btn-area").append('<button type="button" class="btn_gray_wide" id="btn_mailprofile_add">추가</button>');
			$("#mailprofile-btn-area").append('<button type="button" class="btn_red_wide" id="btn_mailprofile_del">삭제</button>');
		}else{
			
			for(var i=0; i<adminField.length; i++){
				$("input#" + adminField[i]).hide();
				$("input#" + adminField[i]).after("<div id='dsp-" + adminField[i] + "'></div>");
			}
		}
		
		// 메일 프로파일 객체 초기화
		initMailDetailObj()
		
		// 메일 프로파일 리스트 Grid 정의
		if(!gridMailObj){
			gridMailObj = new dhtmlXGridObject(gridMailDefinition);
			gridMailObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px; font-size:12px", null, null, null);
			gridMailObj.attachEvent("onRowSelect", function(rid, ind){
				initMailDetailObj();
				mailDetailObj.action 	= "update";
				mailDetailObj.mailid 	= gridMailObj.getUserData(rid, "mailid");
				mailDetailObj.useyn 	= gridMailObj.getUserData(rid, "useyn");
				mailDetailObj.des 		= gridMailObj.getUserData(rid, "des");
				mailDetailObj.subject 	= gridMailObj.getUserData(rid, "subject");
				mailDetailObj.contents 	= gridMailObj.getUserData(rid, "contents");
				loadMailProfile();
			})
		}
		
		// 메일 프로파일 상세의 수신자 리스트  Grid 정의
		if(!gridRcptObj){
			gridRcptObj = new dhtmlXGridObject(gridRcptDefinition);
			gridRcptObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px; font-size:12px", null, null, null);	
		}
		
		
		// 메일 프로파일 상세의 본문내용 Editor
		if(editorObj.length==0){
			nhn.husky.EZCreator.createInIFrame({
				oAppRef: editorObj,
				elPlaceHolder: "contents",
				sSkinURI: "external-library/editor/SmartEditor/SmartEditor2Skin.html",	
				htParams : {
					bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseModeChanger : false,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
					//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
					fOnBeforeUnload : function(){
						//alert("완료!");
					}
				}, //boolean
				fOnAppLoad : function(){
					// 메일 프로파일 리스트 가져오기
					loadMailProfileList();				
				},
				fCreator: "createSEditor2"
			});
		}
		
		resize();
		$(window).resize(resize);
		
		
		// 버튼 이벤트 바인딩
		if(isAdmin){
			$("#btn_mailprofile_add").off("click").on("click", addMailProfile);		// 메일 프로파일 추가 버튼
			$("#btn_mailprofile_del").off("click").on("click", delMailProfile);		// 메일 프로파일 삭제 버튼	
		}
		$("#btn_mailprofile_save").off("click").on("click", saveMailProfile);	// 메일 프로파일 저장 버튼
		$("#btn_mailprofile_send").off("click").on("click", function(){ 		// 메일 발송 버튼
			POPUP.ViewPopup("sendMail", {mailid:mailDetailObj.mailid}, sendMail);
		});
		$("#btn_mailprofile_history").off("click").on("click", function(){		// 메일 발송 이력 보기 버튼
			POPUP.ViewPopup("mailHistory", {mailid:mailDetailObj.mailid});	
		});
		$("#btn_member_add").off("click").on("click", addRcpt); 				// 수신자 추가 버튼
		$("#btn_member_remove").off("click").on("click", delRcpt); 				// 수신자 제거 버튼
		$("#btn_mailprofile_toggle").off("click").on("click", function(){
			
			if(left_display){
				$(".mail-area-left").hide();
				$(".mail-area-right").width("90%");
				$("#btn_mailprofile_toggle").text("작게보기");
				left_display = false;
			}else{
				$(".mail-area-left").show();
				$(".mail-area-left").width("40%");
				$(".mail-area-right").width("50%");
				$("#btn_mailprofile_toggle").text("크게보기");
				left_display = true;
			}
		})	
	};
	
	// 메일 프로파일 리스트 가져오기
	function loadMailProfileList(sRowId){
		SAMILCOMMON.progressOn();
		Ajax.post("AdminMailProfileList", null, function(serverData){
			if (serverData.result) {
				var mailList = serverData.data.list;
				for(var i=0; i<mailList.length; i++){
					mailList[i].userdata = {
						action:"update",
						mailid:mailList[i].mailid,
						useyn:mailList[i].useyn,
						des:mailList[i].des,
						subject:mailList[i].subject,
						contents:mailList[i].contents
					};
					
					mailList[i].id = mailList[i].mailid;
					mailList[i].useyn = mailList[i].useyn=="1"?"사용":"미사용"
				}
				
				gridMailObj.clearAll();
				gridMailObj.parse(mailList, "js");
				
				if(mailList.length==0){
					addMailProfile();
				}else{
					var idx = gridMailObj.getRowIndex(sRowId);
					if(idx==-1){
						gridMailObj.selectRow(0, true);
					}else{
						gridMailObj.selectRow(idx, true);
					}
				}
				SAMILCOMMON.progressOff();
			} else {
				alert(serverData.error.errorMessage);
				SAMILCOMMON.progressOff();
				return false;
			}
		});
	};
	
	// 메일 프로파일을 선택했을 때 상세 정보 가져오기
	function loadMailProfile(){
		for(var prpt in mailDetailObj){
			var $inputObj = $(".mail-contents [name='" + prpt + "']");
			var type = $inputObj.attr("type");
			if(!isAdmin && $.inArray(prpt, adminField)>=0){
				$("#" + prpt).val(mailDetailObj[prpt]);
				$("#dsp-" + prpt).text(mailDetailObj[prpt]);
			}else if(prpt == "contents"){
				editorObj.getById["contents"].setIR(mailDetailObj[prpt]);
			}else if(prpt == "action"){
				if(mailDetailObj.action == "update"){
					$("#mail-profile-title").text("메일 프로파일 수정");
					loadRcptList(mailDetailObj.mailid);
				}else if(mailDetailObj.action == "insert"){
					$("#mail-profile-title").text("새 메일 프로파일 작성");
					gridRcptObj.clearAll();
				}
			}else if(type == "text" || $inputObj.is("textarea")){
				$inputObj.val(mailDetailObj[prpt]);
			}else if(type == "radio"){
				$inputObj.prop("checked", false);
				$inputObj.each(function(){
					if($(this).val() == mailDetailObj[prpt]) $(this).prop("checked", true)
				});
			}
		}
	}
	
	// 메일 프로파일 추가하기
	function addMailProfile(){
		initMailDetailObj();
		gridMailObj.clearSelection();
		mailDetailObj.action = "insert";
		loadMailProfile();
	}
	
	// 메일 프로파일 삭제하기
	function delMailProfile(){
		if(confirm("삭제 하시겠습니까?")){
			Ajax.post("AdminMailProfileDelete", {mailid:gridMailObj.getSelectedRowId()}, function(serverData){
				if (serverData.result) {
					// refresh
					loadMailProfileList();
				} else {
					alert(serverData.error.errorMessage);
					return false;
				}
			});
		}
	}
	
	// 메일 프로파일 저장하기
	function saveMailProfile(){
		syncData();
		var mailid = mailDetailObj.mailid;
		if(validate()){
			Ajax.post("AdminMailProfileSave", mailDetailObj, function(serverData){
				if (serverData.result) {
					// refresh
					loadMailProfileList(mailid);
				} else {
					alert(serverData.error.errorMessage);
					return false;
				}
			});	
		}
	}
	
	// 메일 프로파일 상세에서 수신자 리스트 가져오기
	function loadRcptList(_mailid, callback){
		Ajax.post("AdminMailProfileRcptList", {mailid:_mailid}, function(serverData){
			if (serverData.result) {
				var rcptList = serverData.data.list;
				for(var i=0; i<rcptList.length; i++){
					rcptList[i].id = rcptList[i].rmail;
					rcptList[i].userdata = {status:"load"}
				}
				gridRcptObj.clearAll();
				gridRcptObj.parse(rcptList, "js");
			} else {
				alert(serverData.error.errorMessage);
				return false;
			}
		});
	}
	
	// 메일 수신자 추가 및 삭제 했을 경우 변경사항 가져오기
	function getUpdatedRcpt(){
		var retVal = {};
		var list = [];
		
		if(gridRcptObj.getRowsNum()>0){
			var ids = gridRcptObj.getAllRowIds(",").split(",");
			for(var i=0; i<ids.length; i++){
				var status = gridRcptObj.getUserData(ids[i], "status");
				
				var rname = gridRcptObj.cells(ids[i], gridRcptObj.getColIndexById("rname")).getValue();
				var rid = gridRcptObj.cells(ids[i], gridRcptObj.getColIndexById("rid")).getValue();
				var rmail = gridRcptObj.cells(ids[i], gridRcptObj.getColIndexById("rmail")).getValue();
				if(status == "new" || status == "delete"){
					list.push({
						"status":status,
						"rid":rid,
						"rname":rname,
						"rmail":rmail,
					});	
				}
			}
		}
		retVal.list = list;
		return retVal;
	}
	
	// 메일 수신자 추가하기
	function addRcpt(){
		POPUP.ViewPopup("userSearch", null, function(info) {
			if (info) {
				// var mail = info.inteid+"@samil.com"
				var mail = info.email;
				var ids = gridRcptObj.getAllRowIds();
				var user = [info.kornm, info.emplno, mail];
				
				if(ids.indexOf(mail)<0){
					var user = [info.kornm, info.emplno, mail];
					gridRcptObj.addRow(mail, user.join(","));
					gridRcptObj.setUserData(mail, "status", "new");
				}else{
					var status = gridRcptObj.getUserData(mail, "status");
					if(status == "delete"){
						gridRcptObj.setUserData(mail, "status", "load");
					}
				}
				refreshRcpt()
			}
		});
	}
	
	// 메일 수신자 삭제하기
	function delRcpt(){
		var rowId = gridRcptObj.getSelectedRowId();
		if(rowId){
			var status = gridRcptObj.getUserData(rowId, "status");
			if(status == "load"){
				gridRcptObj.setUserData(rowId, "status", "delete");
			}else if(status == "new"){
				gridRcptObj.deleteRow(rowId);
			}
			refreshRcpt()
		}else {
			alert("삭제할 대상를 선택해주세요.");
			return false;
		}
	}
	
	function sendMail(_mailid, _targetList){
		var param = {
			mailid:_mailid,
			rcpt:encodeURIComponent(JSON.stringify(_targetList))
		}
		
		Ajax.post("AdminSendMail", param, function(serverData){
			if (serverData.result) {
				alert(serverData.data.msg);
			} else {
				alert(serverData.error.errorMessage);
				return false;
			}
		});
		//alert(_mailid + ":" + JSON.stringify(_targetList));
	}
	
	
	
	// 메일 수신자 변경 시 화면 Refresh
	function refreshRcpt(){
		var ids = gridRcptObj.getAllRowIds().split(",");
		
		for(var i=0; i<ids.length; i++){
			var status = gridRcptObj.getUserData(ids[i], "status");
			var style = "";
			if(status == "load"){
				style = "";
			}else if(status == "new"){
				style = "color:#404041"
			}else if(status == "delete"){
				style = "color:#968c6d; text-decoration: line-through;"
			}
			
			for(var j=0; j< gridRcptObj.getColumnsNum(); j++){
				gridRcptObj.setCellTextStyle(ids[i], j, style)	
			}
		}
	}
	
	function validate(){
		if(!(mailDetailObj.action == "insert" || mailDetailObj.action == "update") ){
			alert("추가/변경 오류");
			return false;
		}
		if(mailDetailObj.mailid == ""){
			alert("메일ID를 입력하시기 바랍니다.");
			return false;
		}
		if(mailDetailObj.useyn == ""){
			alert("사용여부를 선택하시기 바랍니다.");
			return false;
		}
		if(mailDetailObj.subject == ""){
			alert("메일 제목을 입력하시기 바랍니다.");
			return false;
		}
		
		return true;
	}
	
	// 변경된 내역을 메일프로파일 상세객체에 담기.
	function syncData(){
		mailDetailObj.mailid 	= $.trim($("input[name='mailid']").val());
		mailDetailObj.subject 	= $.trim($("input[name='subject']").val());
		mailDetailObj.contents 	= encodeURIComponent(editorObj.getById["contents"].getIR());
		mailDetailObj.des 		= encodeURIComponent($.trim($("textarea[name='des']").val()));
		mailDetailObj.useyn 	= $.trim($("input[name='useyn']:checked").val());
		mailDetailObj.rcpt 		= encodeURIComponent(JSON.stringify(getUpdatedRcpt()));
	}
	
	function resize(){
		var $mail 	= $("#div-grid-mail");
		var $rcpt	= $("#div-grid-rcpt");
		var height	= 0;
		var width	= 0;
		
		height	= $mail.parent().height();
		width	= $mail.parent().width();
		$mail.height(height);
		$mail.width(width);
		$mail.find(".xhdr").width(width);
		$mail.find(".objbox").width(width);
		$mail.find(".objbox").height(height - $mail.find(".xhdr").height());
		
		
		height	= $rcpt.parent().height();
		width	= $rcpt.parent().width();
		$rcpt.height(height);
		$rcpt.width(width);
		$rcpt.find(".xhdr").width(width);
		$rcpt.find(".objbox").width(width);
		$rcpt.find(".objbox").height(height - $rcpt.find(".xhdr").height());
		
		/*
		$(".div-grid").width("100%");
		$(".div-grid").height("100%");
		if(gridMailObj) gridMailObj.setSizes();
		if(gridRcptObj) gridRcptObj.setSizes();*/
	}
	
	init();
})();