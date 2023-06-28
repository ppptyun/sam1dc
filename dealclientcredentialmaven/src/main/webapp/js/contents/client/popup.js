var POPUP = (function(){
		
	function ViewPopup(popupId, param, callback){
		switch (popupId) {
			case "userSearch":
				doMakePopup(popupId, 736);
				doMakePopupUserSearch(popupId, param, callback);
				break;
			case "importExcel":
				doMakePopup(popupId, 400);
				doMakePopupImportExcel(popupId, param, callback);
				break;
			case "exportExcelList":
				doMakePopup(popupId, 400);
				doMakePopupExportExcelList(popupId, param, callback);
				break;
			case "guideline":
				doMakePopup(popupId, 500);
				doMakePopupGuideline(param);
				break;
			case "companySearch":
				doMakePopup(popupId, 800);
				doMakePopupCompanySearch(popupId, param, callback);
				break;
			case "consCompanySearch":
				doMakePopup(popupId, 736);
				doMakePopupConsCompanySearch(popupId, param, callback);
				break;
			case "industrySearch":
				doMakePopup(popupId, 736);
				doMakePopupIndustrySearch(popupId, param, callback);
				break;
			case "nationSearch":
				doMakePopup(popupId, 736);
				doMakePopupNationSearch(popupId, param, callback);
				break;
			case "editCredential":
				doMakePopup(popupId, 1200);
				doMakePopupEditCredential(popupId, param, callback);
				break;
			case "mailHistory":
				doMakePopup(popupId, 1200);
				doMakePopupReadMailHistory(popupId, param, callback);
				break;
			case "pdtCategory":
				doMakePopup(popupId, 650, callback);
				doMakePopupPdtCategory(popupId, param, callback);
				break;
			case "pdtAdd":
				doMakePopup(popupId, 650);
				doMakePopupPdtAdd(popupId, param, callback);
				break;
			case "sendMail":
				doMakePopup(popupId, 600);
				doMakePopupSendToTarget(popupId, param, callback);
				break;
			case "excel_except_credential":
				doMakePopup(popupId, 400);
				doMakePopupExceptCredentialByExcel(popupId, param, callback);
				break;
			case "select_pdt":
				doMakePopup(popupId, 600);
				doMakePopupSelectProduct(popupId, param, callback);
				break;
			default: break;
		}
		$("#"+popupId).dialog("open");
	}
	
	function doMakePopup(id, wid, callback){
		var buttons = {}
		if(id == "readQRPHistory"){
			buttons = {
				"저장": {
						text : "저장",
						id : "updateQRPHistory",
						"class" : "btn_highlight"
						},
				"취소": function() {
		        		$( "#"+id ).dialog( "close" );
		        		}	
			}
		}else if(id == "userSearch"){
			buttons = {
					"확인": {
							text : "확인",
							id : "completeSearch",
							"class" : "btn_highlight"
							},
					"취소": function() {
			        		$( "#"+id ).dialog( "close" );
							}
			}
		}else if(id == "companySearch"){
			buttons = {
					"확인": {
							text : "확인",
							id : "completeCompanySearch",
							"class" : "btn_highlight"
							},
					"취소": function() {
			        		$( "#"+id ).dialog( "close" );
							}
			}
		}else if(id == "consCompanySearch"){
			buttons = {
					"확인": {
							text : "확인",
							id : "completeConsCompanySearch",
							"class" : "btn_highlight"
							},
					"취소": function() {
			        		$( "#"+id ).dialog( "close" );
							}
			}
		}else if(id == "industrySearch"){
			buttons = {
					"확인": {
							text : "확인",
							id : "completeIndustrySearch",
							"class" : "btn_highlight"
							},
					"취소": function() {
			        		$( "#"+id ).dialog( "close" );
							}
			}
		}else if(id == "nationSearch"){
			buttons = {
					"확인": {
							text : "확인",
							id : "completeNationSearch",
							"class" : "btn_highlight"
							},
					"취소": function() {
			        		$( "#"+id ).dialog( "close" );
							}
			}
		}else if(id == "importExcel"){
			buttons = {
					"Import": {
						text : "Import",
						id : "btnTemplateUpload",
						"class" : "btn_highlight"
						},
					"취소": function() {
			        		$( "#"+id ).dialog( "close" );
							}
			}
		}else if(id == "excel_except_credential"){
			buttons = {
					"Import": {
						text : "Import",
						id : "btnTemplateUpload",
						"class" : "btn_highlight"
						},
					"다운로드 Template":{
						text : "다운로드 Template",
						id : "btnTemplateDownload",
						"class" : "btn_highlight"
					},
					"취소": function() {
			        		$( "#"+id ).dialog( "close" );
							}
			}
		}else if(id=="exportExcelList"){
			buttons = {
					"Export":{
						text:"Export",
						id:"btnExportExcelList",
						"class":"btn_highlight"
					},
					"취소":function() {
		        		$( "#"+id ).dialog( "close" );
					}
			}
		}else if(id=="editCredential"){
			buttons = {
					"저장":{
						text:"저장",
						id:"completeEditCredential",
						"class":"btn_highlight"
					},
					"취소":function() {
		        		$( "#"+id ).dialog( "close" );
					}
			}
		}else if(id=="pdtCategory"){
			buttons = {
					"닫기":function() {
		        		$( "#"+id ).dialog( "close" );
					}
			}
		}else if(id=="pdtAdd"){
			buttons = {
					"확인":{
						text:"확인",
						id:"completePdtAdd",
						"class":"btn_highlight"
					},
					"취소":function() {
		        		$( "#"+id ).dialog( "close" );
					}
			}
		}else if(id == "sendMail"){
			buttons = {
					"메일발송":{
						text:"메일발송",
						id:"btnPopupSendMail",
						"class":"btn_highlight"
					},
					"취소":function() {
		        		$( "#"+id ).dialog( "close" );
					}
			}
		}else if(id == "select_pdt"){
			buttons = {
					"확인":{
						text:"확인",
						id:"btnPopupSelectPdt",
						"class":"btn_highlight"
					},
					"취소":function() {
						$(this).dialog("close");
					}
			}
		}else{
			buttons = {
		        "닫기": function() {
		        		$( "#"+id ).dialog( "close" );
		        		}	
			}
		}
		
		$( "#"+id ).dialog({
		      autoOpen: false,
		      width: wid,
		      modal: true,
		      buttons:buttons,
		      open:function(event, ui){
		    	  
		      },
		      close: function( event, ui ) {
		    	  $(this).dialog("destroy");
		    	  switch (id) {
					case "userSearch":
						$("#userSearch > .dialog_data_list > .dc_data_table_nobdr").empty();
						$("#inputSearchParam").val("");
						$("#userSearchConditionBox input[type=radio]").eq(0).prop("checked", true);
						doRadioTrsnfed();
						break;
					case "importExcel":
						$("#_importExcelFileContainer").empty();
						break;
					case "importExcel":
						$("#exportExcelFileContainer").empty();
						break;
					case "guideline":
						$("#guideline_content_area").empty();
						break;
					case "companySearch":
						$("#companySearch > .dialog_data_list > .dc_data_table_nobdr").empty();
						$("#inputCompanySearchParam").val("");
						$("#hiddenInputCompanySearchParam").val("");
						$("input:checkbox[id='companyUserCheck']").prop("checked", false);
						$("#firstSearchTerm").empty().hide();
						$("#companyUserKorNm").val("");
						$("#companyUserEngNm").val("");
						$("#companyUserKorNm").attr("disabled", true);
						$("#companyUserEngNm").attr("disabled", true);
						break;
					case "consCompanySearch":
						$("#consCompanySearch > .dialog_data_list > .dc_data_table_nobdr").empty();
						$("#inputConsCompanySearchParam").val("");
						$("input:checkbox[id='consCompanyUserCheck']").prop("checked", false);
						$("#consCompanyUserKorNm").val("");
						$("#consCompanyUserEngNm").val("");
						$("#consCompanyUserKorNm").attr("disabled", true);
						$("#consCompanyUserEngNm").attr("disabled", true);
						break;
					case "industrySearch":
						$("#industrySearch > .dialog_data_list > .dc_data_table_nobdr").empty();
						$("#inputIndustrySearchParam").val("");
						break;
					case "nationSearch":
						$("#nationSearch > .dialog_data_list > .dc_data_table_nobdr").empty();
						$("#inputNationSearchParam").val("");
						$("input:checkbox[id='nationUserCheck']").prop("checked", false);
						$("#nationUserNm").val("");
						$("#nationUserNm").attr("disabled", true);
						break;
					case "editCredential":
						$("#brs_section").hide();
						$("#rcf_section").hide();
						
						$("#cli_nat").val("");
						$("#cli_nat").attr("code", "");
						$("#cli_ind").val("code", "");
						$("#cli_ind").attr("");

						$("#tar0_comkr").val("");
						$("#tar0_comkr").attr("code", "");
						$("#tar0_comkr").attr("seq", "");
						$("#tar0_nat").val("");
						$("#tar0_nat").attr("code", "");
						$("#tar0_comen").val("");
						$("#tar0_ind").val("");
						$("#tar0_ind").attr("code", "");

						$("#tar1_comkr").val("");
						$("#tar1_comkr").attr("code", "");
						$("#tar1_comkr").attr("seq", "");
						$("#tar1_nat").val("");
						$("#tar1_nat").attr("code", "");
						$("#tar1_comen").val("");
						$("#tar1_ind").val("");
						$("#tar1_ind").attr("code", "");

						$("#tar2_comkr").val("");
						$("#tar2_comkr").attr("code", "");
						$("#tar2_comkr").attr("seq", "");
						$("#tar2_nat").val("");
						$("#tar2_nat").attr("code", "");
						$("#tar2_comen").val("");
						$("#tar2_ind").val("");
						$("#tar2_ind").attr("code", "");

						$("#iter_comkr").val("");
						$("#iter_comkr").attr("code", "");
						$("#iter_comkr").attr("seq", "");
						$("#iter_nat").val("");
						$("#iter_nat").attr("code", "");
						$("#iter_comen").val("");
						$("#iter_ind").val("");
						$("#iter_ind").attr("code", "");

						$("#tar_etc").val("");
						$("#detail_work").val("");

						$("#opb_data").val("");
						$("#brs_comkr").val("");
						$("#brs_comkr").attr("code", "");
						$("#brs_comkr").attr("seq", "");

						$("#land_area").val("");
						$("#total_area").val("");

						$("#rcf_comkr").val("");
						$("#rcf_comkr").attr("code", "");
						$("#rcf_comkr").attr("seq", "");
						$("#rcf_comen").val("");
						$("#rcf_ind").val("");
						$("#rcf_ind").attr("code", "");
						$("#rcf_nat").val("");
						$("#rcf_nat").attr("code", "");
						$("#rcf_city").val("");
						$("#rcf_addr").val("");

						break;
					case "mailHistory":
						// 초기화
						$("#popup-mail-subject").empty();
						$("#popup-mail-contents").empty();
						break;
					case "pdtCategory":
						callback({});
						break;
					case "select_pdt":
						$(this).data("combo").unload();
						$("#select_pdt_yearly").empty();
						$("#select_pdt_grid").empty();						
						break;
					default: break;
				}
		    	  
		      }
	    });
	}
	
	
	function doMakePopupGuideline(param){
		var target = $("#guideline_content_area");
		target.empty().append(param.message);
	}
	
	
	function doMakePopupImportExcel(id, param, callback){
		var allow_extension_array = ["xls"];
		var upload_config = {
			title:"Excel Import",
			allowExtentions:allow_extension_array,
			callback: function(uploadedFilePath) {
				if (!uploadedFilePath || uploadedFilePath == "undefined" || uploadedFilePath == "") {
					alert("파일 업로드 실패입니다.");
					return false;
				}
				uploadedFilePath = $.trim(uploadedFilePath);
				if (uploadedFilePath == "NOT_ALLOWED_EXT") {
					alert("허용된 확장자가 아닙니다.\n["+allow_extension_array.join(", ")+"] 확장자만 업로드 가능합니다.");
					return false;
				}
				
				var contentType = "json";
				var _o = {"SystemCode":"dealcredential", "Method":"service", "ContentType":contentType, "ServiceTask":"ExcelImport"};
				var _p = $.extend({}, _o, {uploadedFileName:uploadedFilePath});
				$.ajax({
					url: CURR_INFO.serviceBroker,
					type: "POST",
					dataType: contentType,
					timeout: 1000 * 60 * 20,
					async: true,
					cache: false,
					data: $.param(_p),
					beforeSend: function() {
						progressOn();
					},
					success: function(data, textStatus, jqXHR) {
						try {
							if (data) {
								if (false == data.result) {
									if ("99998" == data.error.errorCode) {
										alert("로그인이 필요한 서비스입니다.");
										location.href = CURR_INFO.portalUri;
										return false;
									}
									if ("99997" == data.error.errorCode) {
										alert("권한이 없는 사용자입니다.");
										location.href = CURR_INFO.portalUri;
										return false;
									}
									if ("10002" == data.error.errorCode || "10003" == data.error.errorCode) {
										alert("요청한 서비스를 찾을 수 없습니다.");
										return false;
									}
								}
							}
						} catch(e) {}
						if (data) {
							if (false == data.result) {
								$( "#"+id ).dialog( "close" );
								POPUP.ViewPopup("guideline", {message: data.error.errorMessage});
								return false;
							} else  {
								$( "#"+id ).dialog( "close" );
								POPUP.ViewPopup("guideline", {message: data.data.message});
								return false;
							}
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						try {
							if (jqXHR.readyState == 0 || jqXHR.readyState == 4) {
								alert("잠시 후 다시 접속하시기 바랍니다.");
								return false;
							}
						} catch(e) {}
					},
					complete: function(jqXHR, textStatus) {
						progressOff();
					}
				});
			},
			filetype:allow_extension_array
		};
		var FileUploadObj = FileUpload(upload_config);
		var html = FileUploadObj.getFileUploalModalHtml();
		$("#_importExcelFileContainer").empty().append(html);
		
		// 엑셀 업로드
		$("#btnTemplateUpload").off("click").on("click", function(){
			FileUploadObj.upload();
		});
	}
	
	function doMakePopupExportExcelList(id, param, callback){
		var _h = [];
		var _target = $("#exportExcelList > #exportExcelFileContainer");
		_h.push('<div><table>');
		_h.push('<col width="200px"><col width="100px">');
		_h.push('<tr><th style="text-align:left">전체 Rows</th><td style="text-align:right">' + SAMILCOMMON.numberWithCommas(param.tot_rows) + '</td></tr>');
		_h.push('<tr><th style="text-align:left">Export 가능한 최대 Rows</th><td style="text-align:right">' + SAMILCOMMON.numberWithCommas(param.max_rows) + '</td></tr>');
		_h.push('<tr><th style="text-align:left">시작 Row Number</th><td style="text-align:right"><input type="text" id="inputExcelExportListStart"></input></td></tr>');
		_h.push('<tr><th style="text-align:left">종료 Row Number</th><td style="text-align:right"><input type="text" id="inputExcelExportListEnd"></input></td></tr>');
		_h.push('</table></div>');
		_h.push('<div></div>');
		_target.empty().append(_h.join(""));
		
		function validationCheck(start, end){
			if($.trim(start)=="" || $.trim(end)==""){
				alert("시작 Row Number와 종료 Row Number는 필수 값입니다.");
				return false;
			}
			if( !(SAMILCOMMON.checkNumber(start) && SAMILCOMMON.checkNumber(end)) ){
				alert("숫자만 입력하시기 바랍니다.")
				return false;
			}
			start = Number(start);
			end = Number(end);
			
			if(start<1){
				alert("시작 Row Number는 1보다 크거나 같아야합니다.")
				return false;
			}
			if(start > param.tot_rows || end > param.tot_rows){
				alert("입력값은 " + param.tot_rows + " 보다 작거나 같아야 합니다.")
				return false;
			}
			if(start > end){
				alert("시작 Row Number는 종료 Row Number보다 작아야합니다.")
				return false;
			}
			if((end - start + 1) > param.max_rows){
				alert("Export 가능한 최대 Rows는 " + param.max_rows + "입니다."); 
				return false;
			}
			
			return true;
		}
		
		$("#btnExportExcelList").off("click").on("click", function(){
			var start	= $.trim($("#inputExcelExportListStart").val());
			var end 	= $.trim($("#inputExcelExportListEnd").val());
			if(validationCheck(start, end)){
				if(callback) callback(start, end);
			}
		});
		
	}
	
	function afterUserSearch(data){
		var _h = [];
		var _target = $("#userSearch > .dialog_data_list > .dc_data_table_nobdr");
		var list = data.data.list;
		
		_h.push('<colgroup>	<col width="20%" /><col width="20%" /><col width="30%" /><col width="30%" /></colgroup>');
		
		if(list.length == 0){
			_h.push('<tr><td colspan="4"><span class="data_text"> 데이터가 없습니다. </span></td></tr>');
		}else{
			for(var i in list){
				_h.push('<tr>');
				_h.push('<td name="userSearchEmplno" inteid="' + list[i].inteid + '" email="' + list[i].email +'"><span class="data_text">'+ list[i].emplno +'</span></td>');
				/*_h.push('<td name="userSearchEmplno"><span class="data_text">'+ list[i].emplno +'</span></td>');*/
				_h.push('<td name="userSearchKornm"><span class="data_text">'+ list[i].kornm +'</span></td>');
				_h.push('<td name="userSearchGradnm" gradcd="'+ list[i].gradcd + '"><span class="data_text">'+ list[i].gradnm +'</span></td>');
				_h.push('<td name="userSearchTeamnm" teamcd="'+ list[i].teamcd + '"><span class="data_text">'+ list[i].teamcd + '/'+ list[i].teamnm +'</span></td>');
				_h.push('</tr>');
			}
		}	
		_target.empty().append(_h.join(""));
		_target.find("tr").css({"cursor": "pointer"});
	}
	
	function doMakePopupUserSearch(id, param, callback){
		$("#inputSearchParam").focus();
		$("#inputSearchParam").off("keydown").on("keydown",function(e){
			if(e.keyCode == 13) $("#btnUserSearch").trigger("click");
		});
		$("#userSearchConditionBox input[type=radio]").off("change").on("change",function(){
			doRadioTrsnfed();
//			$("#inputSearchParam").off("keydown").on("keydown",function(e){
//				if($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
//						(e.keyCode == 65 && e.ctrlKey === true) ||
//						(e.keyCode == 67 && e.ctrlKey === true) ||
//						(e.keyCode == 88 && e.ctrlKey === true) ||
//						(e.keyCode >= 35 && e.keyCode <= 39)) {
//						return;
//					}
//				if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
//		            e.preventDefault();
//		        }
//			});
		});
		$("#btnUserSearch").off("click").on("click", function(){			
			userSearchParam = {};
			checkedId = $("#userSearchConditionBox input[type=radio]:checked").attr("id");
			var msg = searchValidation(checkedId);
			if(msg){
				alert(msg);
				return;
			}
			if(checkedId == "paramEmpnm"){
				userSearchParam.empnm = $("#inputSearchParam").val();
			} else {
				userSearchParam.empno = $("#inputSearchParam").val();
			}
			Ajax.post("UserSearch", userSearchParam, afterUserSearch);
			$("#userSearch > .dialog_data_list > .dc_data_table_nobdr").undelegate("tr", "click").delegate("tr", "click", function(){
				$("#userSearch > .dialog_data_list > .dc_data_table_nobdr tr").not($(this)).each(function(){
					$(this).removeClass("selectedRow");
				});
				$(this).toggleClass("selectedRow");
			});
		});
		$("#completeSearch").off("click").on("click", function(){
			var selectedRow = $("#userSearch > .dialog_data_list > .dc_data_table_nobdr tr.selectedRow");
			if(selectedRow.length == 0){
				alert("대상을 선택하세요.")
			}else{
				var rslt = {};
				rslt.emplno = selectedRow.find("td[name='userSearchEmplno']").text();
				rslt.inteid = selectedRow.find("td[name='userSearchEmplno']").attr("inteid");
				rslt.email = selectedRow.find("td[name='userSearchEmplno']").attr("email");
				rslt.kornm = selectedRow.find("td[name='userSearchKornm']").text();
				rslt.gradcd = selectedRow.find("td[name='userSearchGradnm']").attr("gradcd");
				rslt.gradnm = selectedRow.find("td[name='userSearchGradnm']").text();
				rslt.teamcd = selectedRow.find("td[name='userSearchTeamnm']").attr("teamcd");
				rslt.teamnm = selectedRow.find("td[name='userSearchTeamnm']").text();
				$("#userSearch").dialog("close");
				callback(rslt);	
			}
		});
	}
	
	function doMakePopupCompanySearch(id, param, callback){
		// 원 검색 목록
		var firstSearchList = [];
		
		$("#inputCompanySearchParam").focus();
		$("#firstSearchTerm").empty().hide();
		
		$("#inputCompanySearchParam").off("keydown").on("keydown",function(e){
			if(e.keyCode == 13) $("#btnCompanySearch").trigger("click");
		});
		
		$("#btnCompanySearch").off("click").on("click", function(){
			// 결과 내 검색인지 판별
			var reSearchChk = $("#reSearchCheck").prop("checked");
			if (reSearchChk) {
				var firstSearchTerm = $("#hiddenInputCompanySearchParam").val().trim();
				if(firstSearchTerm == "") {
					alert("1차 검색을 먼저 실행해주세요.");
					$("#reSearchCheck").prop("checked", false);
					$("#reSearchCheck").attr("checked", false);
					$("#firstSearchTerm").empty().hide();
					$("#inputCompanySearchParam").focus();
					return false;
				}
				if (firstSearchList.length == 0) {
					alert("1차 검색 데이터가 없습니다.\n다른 검색어를 사용해주세요.");
					return false;
				}
				doReSearch();
				
			} else {
				var firstSearchTerm = $("#inputCompanySearchParam").val().trim();
				$("#hiddenInputCompanySearchParam").val(firstSearchTerm);
				if(firstSearchTerm == "") {
					alert("검색어를 입력해주세요.");
					$("#inputCompanySearchParam").focus();
					return false;
				}
				
				var companySearchParam = {};
				companySearchParam.comnm = firstSearchTerm
				
				SAMILCOMMON.progressOn();
				
				Ajax.post("CompanySearch", companySearchParam, function(data) {
					// 서버 검색 결과 저장
					firstSearchList = data.data.list;
					doMakrTableResult(firstSearchList);
				});				
			}
			
			$("#companySearch > .dialog_data_list > .dc_data_table_nobdr").undelegate("tr", "click").delegate("tr", "click", function(){
				$("#companySearch > .dialog_data_list > .dc_data_table_nobdr tr").not($(this)).each(function(){
					$(this).removeClass("selectedRow");
				});
				$(this).toggleClass("selectedRow");
			});
		});
		
		$("#reSearchCheck").off("click").on("click", function(){
			var reSearchChk = $(this).prop("checked");
			var firstSearchTerm = $("#hiddenInputCompanySearchParam").val().trim();
			if (reSearchChk) {
				if(firstSearchTerm == "") {
					alert("1차 검색을 먼저 실행해주세요.");
					$("#reSearchCheck").prop("checked", false);
					$("#reSearchCheck").attr("checked", false);
					$("#firstSearchTerm").empty().hide();
					$("#inputCompanySearchParam").focus();
					return false;
				}
				if (firstSearchList.length == 0) {
					alert("1차 검색 데이터가 없습니다.\n다른 검색어를 사용해주세요.");
					return false;
				}
				$("#firstSearchTerm").empty().append("1차 검색어: " + firstSearchTerm).show();
				$("#inputCompanySearchParam").val("").focus();
				
			} else {
				$("#inputCompanySearchParam").val(firstSearchTerm);
				$("#firstSearchTerm").empty().hide();
				doRefreshFirstSearch();
			}
		});
		
		function doReSearch() {
			var reSearchTerm = $("#inputCompanySearchParam").val().trim();
			var regpattern = new RegExp(reSearchTerm, ['gi']);
			var list = []
			for(var i=0; i<firstSearchList.length; i++){
				var companyname = firstSearchList[i].hangnm.trim();
				if(companyname.match(regpattern)){
					list.push(firstSearchList[i]);
				}
			}
			doMakrTableResult(list);
		}
		
		function doRefreshFirstSearch() {
			doMakrTableResult(firstSearchList);
		}
		
		function doMakrTableResult(arrCompany) {
			var _h = [];
			var _target = $("#companySearch > .dialog_data_list > .dc_data_table_nobdr");
			_h.push('<colgroup>	<col width="10%" /><col width="25%" /><col width="15%" /><col width="10%" /><col width="40%" /></colgroup>');
			if(arrCompany.length == 0){
				_h.push('<tr><td colspan="5"><span class="data_text"> 데이터가 없습니다. </span></td></tr>');
				SAMILCOMMON.progressOff();
			}else{
				for(var i in arrCompany){
					_h.push(doMakeTableRow(arrCompany[i]));
				}
				SAMILCOMMON.progressOff();
			}	
			_target.empty().append(_h.join(""));
			_target.find("tr").css({"cursor": "pointer"});
		}
		
		function doMakeTableRow(company) {
			var _h = [];
			var client_cd = company.client.trim();
			var upche_cd = company.upchecd.trim();
			var clientid = client_cd == "" ? upche_cd : client_cd;
			_h.push('<tr>');
			_h.push('<td name="companySearchClient" data-upchecd="' + clientid + '" class="tac"><span class="data_text">'+ (client_cd == "" ? "" : client_cd) +'</span></td>');
			_h.push('<td name="companySearchHangnm" class="tal" data-englnm="'+ company.englnm.trim() +'" data-glindunmk="'+ company.glindunmk +'" data-inducd="'+ company.inducd +'" data-indunm="'+ company.indunm +'"><span class="data_text">'+ company.hangnm.trim() +'</span></td>');
			_h.push('<td name="companySearchBubinno" class="tac"><span class="data_text">'+ company.bubinno.trim() +'</span></td>');
			_h.push('<td name="companySearchKorreprnm" class="tac"><span class="data_text">'+ company.korreprnm.trim() +'</span></td>');
			_h.push('<td name="companySearchAddr" class="tal"><span class="data_text">'+ company.addr.trim() +'</span></td>');
			_h.push('</tr>');
			return _h.join("");
		}
		
		$("#companyUserCheck").off("click").on("click", function(){
			if($(this).is(":checked")){
				$("#companyUserKorNm").attr("disabled", false);
				$("#companyUserEngNm").attr("disabled", false);
				$("#companyUserKorNm").focus();
			} else {
				$("#companyUserKorNm").attr("disabled", true);
				$("#companyUserEngNm").attr("disabled", true);
			}
		});
		
		$("#completeCompanySearch").off("click").on("click", function(){
			var rslt = {};
			if($(companyUserCheck).is(":checked")){
				if($("#companyUserKorNm").val().trim() == "") {
					return false;
				}
				rslt.hangnm = $("#companyUserKorNm").val().trim();
				rslt.englnm = $("#companyUserEngNm").val().trim();
				rslt.upchecd = "-";
				rslt.inducd = "";
			} else {
				var selectedRow = $("#companySearch > .dialog_data_list > .dc_data_table_nobdr tr.selectedRow");
				if(selectedRow.find("td[name='companySearchHangnm']").text() == "") {
					return false;
				}
				rslt.hangnm = selectedRow.find("td[name='companySearchHangnm']").text();
				rslt.englnm = selectedRow.find("td[name='companySearchHangnm']").data("englnm");
				rslt.upchecd = selectedRow.find("td[name='companySearchClient']").data("upchecd");
				rslt.inducd = selectedRow.find("td[name='companySearchHangnm']").data("inducd");
				rslt.indunm = selectedRow.find("td[name='companySearchHangnm']").data("indunm");
				rslt.glindunmk = selectedRow.find("td[name='companySearchHangnm']").data("glindunmk");
			}
			
			$("#companySearch").dialog("close");
			callback(rslt);
		});
		
		/*$("#companyUserEngNm").off("keyup").on("keyup", function(event){
			if (!(event.keyCode >=37 && event.keyCode<=40)) {
                var inputVal = $(this).val();
                //$(this).val(inputVal.replace(/[^a-z0-9]/gi,''));
                $(this).val(inputVal.replace(/[\ㄱ-ㅎㅏ-ㅣ가-힣]/g,''));
            }
        });*/
	}
	
	function doMakePopupConsCompanySearch(id, param, callback){
		search();
		
		$("#inputConsCompanySearchParam").focus();
		$("#inputConsCompanySearchParam").off("keydown").on("keydown",function(e){
			if(e.keyCode == 13) $("#btnConsCompanySearch").trigger("click");
		});
		
		$("#btnConsCompanySearch").off("click").on("click", function(){
			search();
		});
		
		$("#consCompanyUserCheck").off("click").on("click", function(){
			if($(this).is(":checked")){
				$("#consCompanyUserKorNm").attr("disabled", false);
				$("#consCompanyUserEngNm").attr("disabled", false);
				$("#consCompanyUserKorNm").focus();
			} else {
				$("#consCompanyUserKorNm").attr("disabled", true);
				$("#consCompanyUserEngNm").attr("disabled", true);
			}
		});
		
		$("#completeConsCompanySearch").off("click").on("click", function(){
			var rslt = {};
			if($(consCompanyUserCheck).is(":checked")){
				if($("#consCompanyUserKorNm").val().trim() == "") {
					return false;
				}
				rslt.hangnm = $("#consCompanyUserKorNm").val().trim();
				rslt.englnm = $("#consCompanyUserEngNm").val().trim();
				rslt.compcd = "-";
			} else {
				var selectedRow = $("#consCompanySearch > .dialog_data_list > .dc_data_table_nobdr tr.selectedRow");
				if(selectedRow.find("td[name='consCompanySearchHangnm']").text() == "") {
					return false;
				}
				rslt.hangnm = selectedRow.find("td[name='consCompanySearchHangnm']").text();
				rslt.englnm = selectedRow.find("td[name='consCompanySearchEnglnm']").text();
				rslt.compcd = selectedRow.find("td[name='consCompanySearchCompcd']").text();
			}
			
			var consDiv = param.compcd=="1"?"samil":(param.compcd=="" || typeof param.compcd === "undefined" ? "init" : "others");
			var consDiv2 = rslt.compcd=="1"?"samil":(rslt.compcd=="" || typeof rslt.compcd === "undefined" ? "init" : "others");
			rslt.isChanged = !(consDiv == consDiv2);
			
			$("#consCompanySearch").dialog("close");
			callback(rslt);
		});
		
		function search() {
			consCompanySearchParam = {};
			consCompanySearchParam.comnm = $("#inputConsCompanySearchParam").val();
			consCompanySearchParam.type = param.type;
			
			Ajax.post("ConsCompanySearch", consCompanySearchParam, function(data) {
				var _h = [];
				var _target = $("#consCompanySearch > .dialog_data_list > .dc_data_table_nobdr");
				var list = data.data.list;
				
				_h.push('<colgroup><col width="20%" /><col width="40%" /><col width="40%" /></colgroup>');
				
				if(list.length == 0){
					_h.push('<tr><td colspan="3"><span class="data_text"> 데이터가 없습니다. </span></td></tr>');
				}else{
					for(var i in list){
						_h.push('<tr>');
						_h.push('<td name="consCompanySearchCompcd" class="tar"><span class="data_text">'+ list[i].compcd.trim() +'</span></td>');
						_h.push('<td name="consCompanySearchHangnm" class="tal"><span class="data_text">'+ list[i].hangnm.trim() +'</span></td>');
						_h.push('<td name="consCompanySearchEnglnm" class="tal"><span class="data_text">'+ list[i].englnm.trim() +'</span></td>');
						_h.push('</tr>');
					}
				}	
				_target.empty().append(_h.join(""));
				_target.find("tr").css({"cursor": "pointer"});
			});
			$("#consCompanySearch > .dialog_data_list > .dc_data_table_nobdr").undelegate("tr", "click").delegate("tr", "click", function(){
				$("#consCompanySearch > .dialog_data_list > .dc_data_table_nobdr tr").not($(this)).each(function(){
					$(this).removeClass("selectedRow");
				});
				$(this).toggleClass("selectedRow");
			});
		}
	}
	
	function doMakePopupIndustrySearch(id, param, callback){
		var gridDef = {
			parent:"industrySearchGrid",
			image_path:"./js/dhtmlx/codebase/imgs/",
			skin:"dhx_skyblue",
			columns:[
			         	{id:"indugcd", label:["중분류(삼일)", "코드", "#text_filter"], type:"ro", align:"center", valign:"middle", sort: "str"},
			         	{id:"indugnm", label:["#cspan", "이름", "#text_filter"], type:"ro", align:"left", valign:"middle", sort: "str"},
			         	{id:"inducd", label:["소분류", "코드", "#text_filter"], type:"ro", align:"center", valign:"middle", sort: "str"},
			         	{id:"indunm", label:["#cspan", "이름", "#text_filter"], type:"ro", align:"left", valign:"middle", sort: "str"}
					]
		};
		var induGridObj = new dhtmlXGridObject(gridDef);
		induGridObj.setInitWidthsP("10,40,10,40");
		induGridObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px;font-size:12px", null, null, null);
		
		Ajax.post("IndustrySearch", {}, function(serverData){
			var list = serverData.data.list;
			if (serverData.result) {
				var list = serverData.data.list;
				induGridObj.parse(list, "js");
			} else {
				alert("창을 닫고 다시 시도 해 보시기 바랍니다.");
			}
		});
		
		$("#completeIndustrySearch").off("click").on("click", function(){
			var rid = induGridObj.getSelectedRowId();
			if(rid){
				var result = {}
				result.inducd = induGridObj.cellById(rid, induGridObj.getColIndexById("inducd")).getValue();
				result.indunm = induGridObj.cellById(rid, induGridObj.getColIndexById("indunm")).getValue();
				result.indugcd = induGridObj.cellById(rid, induGridObj.getColIndexById("indugcd")).getValue();
				result.indugnm = induGridObj.cellById(rid, induGridObj.getColIndexById("indugnm")).getValue();
				$("#industrySearch").dialog("close");
				callback(result);
			}else{
				alert("Industry를 선택하시기 바랍니다.")
			}
		})
		
		/*search();
		
		$("#inputIndustrySearchParam").focus();
		
		$("#inputIndustrySearchParam").off("keydown").on("keydown",function(e){
			if(e.keyCode == 13) $("#btnIndustrySearch").trigger("click");
		});
		
		$("#btnIndustrySearch").off("click").on("click", function(){			
			search();
		});
		
		$("#completeIndustrySearch").off("click").on("click", function(){
			var selectedRow = $("#industrySearch > .dialog_data_list > .dc_data_table_nobdr tr.selectedRow");
			if(selectedRow.find("td[name='industrySearchInducd']").text() == "") {
				return false;
			}
			var rslt = {};
			rslt.indunm = selectedRow.find("td[name='industrySearchIndunm']").text();
			rslt.inducd = selectedRow.find("td[name='industrySearchInducd']").text();
			rslt.glindunmk = selectedRow.find("td[name='industrySearchGlindunmk']").text();
			rslt.glinducd = selectedRow.find("td[name='industrySearchGlinducd']").text();
			$("#industrySearch").dialog("close");
			callback(rslt);
		});
		
		function search() {
			industrySearchParam = {};
			industrySearchParam.indunm = $("#inputIndustrySearchParam").val();

			Ajax.post("IndustrySearch", industrySearchParam, function(data) {
				var _h = [];
				var _target = $("#industrySearch > .dialog_data_list > .dc_data_table_nobdr");
				var list = data.data.list;
				
				_h.push('<colgroup>	<col width="10%" /><col width="40%" /><col width="10%" /><col width="40%" /> </colgroup>');
				
				if(list.length == 0){
					_h.push('<tr><td colspan="4"><span class="data_text"> 데이터가 없습니다. </span></td></tr>');
				}else{
					for(var i in list){
						_h.push('<tr>');
						_h.push('<td name="industrySearchGlinducd" class="tar"><span class="data_text">'+ list[i].glinducd.trim() +'</span></td>');
						_h.push('<td name="industrySearchGlindunmk" class="tal"><span class="data_text">'+ list[i].glindunmk.trim() +'</span></td>');
						_h.push('<td name="industrySearchGlindunm"><span class="data_text">'+ list[i].glindunm.trim() +'</span></td>');
						_h.push('<td name="industrySearchInducd" class="tar"><span class="data_text">'+ list[i].inducd.trim() +'</span></td>');
						_h.push('<td name="industrySearchIndunm" class="tal"><span class="data_text">'+ list[i].indunm.trim() +'</span></td>');
						_h.push('<td name="industrySearchIndunme"><span class="data_text">'+ list[i].indunme.trim() +'</span></td>');
						_h.push('</tr>');
					}
				}	
				_target.empty().append(_h.join(""));
				_target.find("tr").css({"cursor": "pointer"});
			});
			
			$("#industrySearch > .dialog_data_list > .dc_data_table_nobdr").undelegate("tr", "click").delegate("tr", "click", function(){
				$("#industrySearch > .dialog_data_list > .dc_data_table_nobdr tr").not($(this)).each(function(){
					$(this).removeClass("selectedRow");
				});
				$(this).toggleClass("selectedRow");
			});
		}*/
	}
	
	function doMakePopupNationSearch(id, param, callback){
		search();
		
		$("#inputNationSearchParam").focus();
		
		$("#inputNationSearchParam").off("keydown").on("keydown",function(e){
			if(e.keyCode == 13) $("#btnNationSearch").trigger("click");
		});
		
		$("#btnNationSearch").off("click").on("click", function(){			
			search();
		});
		
		$("#nationUserCheck").off("click").on("click", function(){
			if($(this).is(":checked")){
				$("#nationUserNm").attr("disabled", false);
				$("#nationUserNm").focus();
			} else {
				$("#nationUserNm").attr("disabled", true);
			}
		});
		
		$("#completeNationSearch").off("click").on("click", function(){
			var rslt = {};
			if($("#nationUserCheck").is(":checked")){
				if($("#nationUserNm").val().trim() == "") {
					return false;
				}
				rslt.etcocd = "-";
				rslt.etcdnm = $("#nationUserNm").val().trim();
			} else {
				var selectedRow = $("#nationSearch > .dialog_data_list > .dc_data_table_nobdr tr.selectedRow");
				if(selectedRow.find("td[name='nationSearchEtcocd']").text() == "") {
					return false;
				}
				rslt.etcocd = selectedRow.find("td[name='nationSearchEtcocd']").text();
				rslt.etcdnm = selectedRow.find("td[name='nationSearchEtcdnm']").text();
			}
			$("#nationSearch").dialog("close");
			callback(rslt);
		});
		
		function search() {
			nationSearchParam = {};
			nationSearchParam.etcdnm = $("#inputNationSearchParam").val();
			
			Ajax.post("NationSearch", nationSearchParam, function(data) {
				var _h = [];
				var _target = $("#nationSearch > .dialog_data_list > .dc_data_table_nobdr");
				var list = data.data.list;
				
				_h.push('<colgroup><col width="50%" /><col width="50%" /></colgroup>');
				
				if(list.length == 0){
					_h.push('<tr><td colspan="2"><span class="data_text"> 데이터가 없습니다. </span></td></tr>');
				}else{
					for(var i in list){
						_h.push('<tr>');
						_h.push('<td name="nationSearchEtcocd" class="tal"><span class="data_text">'+ list[i].etcocd.trim() +'</span></td>');
						_h.push('<td name="nationSearchEtcdnm" class="tal"><span class="data_text">'+ list[i].etcdnm.trim() +'</span></td>');
						_h.push('</tr>');
					}
				}	
				_target.empty().append(_h.join(""));
				_target.find("tr").css({"cursor": "pointer"});
			});
			
			$("#nationSearch > .dialog_data_list > .dc_data_table_nobdr").undelegate("tr", "click").delegate("tr", "click", function(){
				$("#nationSearch > .dialog_data_list > .dc_data_table_nobdr tr").not($(this)).each(function(){
					$(this).removeClass("selectedRow");
				});
				$(this).toggleClass("selectedRow");
			});
		}
	}
	
	function doMakePopupEditCredential(id, param, callback){
		var brs = "100101";
		var rcf = "100102";
		var security = "122";
		var unsecured = "126";
		var disposal = "123";
		var rcftype = "124";
		var rcfleisure = "130";
		var rcfleisuredetail = "102404";
		var rcfinfra = "131";
		var rcfinfradetail = "102408";
		var rcfetcdetail = "102409";
		var credtgt = "132";
		var data = {};
		var codes = {};
		
		drawData();
		
		function drawData() {
			$("#"+id).find(".data_cell").addClass("edit_mode rgt_btn");
			Ajax.post("CredentialDetail", param, function(json) {
				if(json.result){
					data = json.data.project;
					codes = json.data.codes;
					var _h = [];

					_h = [];
					_h.push('<select id="credtgt_type" class="inp_change"><option value="">선택해주세요.</option>');
					for(var i=0; i<codes[credtgt].length; i++){
						_h.push('<option value="' + codes[credtgt][i].itemcd + '">' + codes[credtgt][i].itemnm + '</option>');
					}
					_h.push('</select>');
					$("#credtgt_list").empty().append(_h.join(""));
					$('#credtgt_type').val(data.info.credtgtcd);
					$("#pdt_orig").val(data.info.pdtcd_orig + "/" + data.info.pdtnm_orig)
					$("#pdt").val(data.info.pdtcd + "/" + data.info.pdtnm);
					$("#pdt").data("refyearly", data.info.refyearly);
					$("#pdt").data("ctgcd", data.info.ctgcd);
					$("#pdt").data("pdtcd", data.info.pdtcd);
					
					$("#cli_nat").val(data.client.natnm);
					$("#cli_nat").attr("code", data.client.natcd);
					$("#cli_ind").val(data.client.indunm);
					$("#cli_ind").attr("code", data.client.inducd);

					for(var i=0; i<data.target.length; i++) {
						$("#tar" + i + "_comkr").val(data.target[i].comphannm);
						$("#tar" + i + "_comkr").attr("code", data.target[i].compcd);
						$("#tar" + i + "_comkr").attr("seq", data.target[i].seq);
						$("#tar" + i + "_comen").val(data.target[i].compengnm);
						$("#tar" + i + "_ind").val(data.target[i].indunm);
						$("#tar" + i + "_ind").attr("code", data.target[i].idnucd);
						$("#tar" + i + "_nat").val(data.target[i].natnm);
						$("#tar" + i + "_nat").attr("code", data.target[i].natcd);
					}
					
					$("#tar_etc").val(data.info.trgtetc);
					$("#detail_work").val(data.info.prjtdesc);
					
					// 이해관계자
					$("#iter_comkr").val(data.interest.comphannm);
					$("#iter_comkr").attr("code", data.interest.compcd);
					$("#iter_comkr").attr("seq", data.interest.seq);
					$("#iter_comen").val(data.interest.compengnm);
					$("#iter_ind").val(data.interest.indunm);
					$("#iter_ind").attr("code", data.interest.idnucd);
					$("#iter_nat").val(data.interest.natnm);
					$("#iter_nat").attr("code", data.interest.natcd);
					
					_h = [];
					_h.push('<div class="data_cell edit_mode"><div class="inp_check_list">');
					for(var i=0; i<codes[security].length; i++){
						_h.push('<input type="checkbox" id="' + codes[security][i].itemcd + '" name="security_type" value="' + codes[security][i].itemcd + '"><label for="' + codes[security][i].itemcd + '">' + codes[security][i].itemnm + '</label>');
					}
					_h.push('</div></div>');
					$("#security_list").empty().append(_h.join(""));
					for(var i=0; i<data.bondsecure.length; i++){
						$('input:checkbox[name="security_type"]').each(function() {
							if(this.value == data.bondsecure[i].itemcd){
								this.checked = true;
							}
						});
					}

					_h = [];
					_h.push('<div class="data_cell edit_mode"><div class="inp_check_list">');
					for(var i=0; i<codes[unsecured].length; i++){
						_h.push('<input type="checkbox" id="' + codes[unsecured][i].itemcd + '" name="security_type" value="' + codes[unsecured][i].itemcd + '"><label for="' + codes[unsecured][i].itemcd + '">' + codes[unsecured][i].itemnm + '</label>');
					}
					_h.push('</div></div>');
					$("#unsecured_list").empty().append(_h.join(""));
					for(var i=0; i<data.bondsecurenone.length; i++){
						$('input:checkbox[name="unsecured_type"]').each(function() {
							if(this.value == data.bondsecurenone[i].itemcd){
								this.checked = true;
							}
						});
					}
					
					_h = [];
					_h.push('<select id="disposal_type" class="inp_change"><option value="">선택해주세요.</option>');
					for(var i=0; i<codes[disposal].length; i++){
					_h.push('<option value="' + codes[disposal][i].itemcd + '">' + codes[disposal][i].itemnm + '</option>');
					}
					_h.push('</select>');
					$("#disposal_list").empty().append(_h.join(""));
					$('#disposal_type').val(data.info.brssalecd);
					
					$("#opb_data").val(data.info.brsopb);

					$("#brs_comkr").val(data.brsbuyer.comphannm);
					$("#brs_comkr").attr("code", data.brsbuyer.compcd);
					$("#brs_comkr").attr("seq", data.brsbuyer.seq);
					
					// RCF 타입
					_h = [];
					_h.push('<option value="">선택해주세요.</option>');
					for(var i=0; i<codes[rcftype].length; i++){
					_h.push('<option value="' + codes[rcftype][i].itemcd + '">' + codes[rcftype][i].itemnm + '</option>');
					}
					$("#rcf_type").empty().append(_h.join(""));
					var rcftypecd = data.info.rcftypecd;
					$("#rcf_type").val(rcftypecd);
					if (rcftypecd == rcfleisuredetail || rcftypecd == rcfinfradetail) {
						doBuildComboRCFTypeDetail(codes, rcftypecd == rcfleisuredetail ? rcfleisure : rcfinfra, rcftypecd == rcfleisuredetail ? data.info.rcftypea : data.info.rcftypeb);
						
						$("#rcf_etc").val("");
					}
					if (rcftypecd == rcfetcdetail) {
						$("#rcf_detail_type").empty().append('<option value="" selected>선택해주세요.</option>');
						
						$("#rcf_etc").val(data.info.rcftypeetc);
					}
					if (rcftypecd != rcfleisuredetail && rcftypecd != rcfinfradetail && rcftypecd != rcfetcdetail) {
						$("#rcf_detail_type").empty().append('<option value="" selected>선택해주세요.</option>');
						
						$("#rcf_etc").val("");
					}

					$("#land_area").val(data.info.rcfland);
					$("#total_area").val(data.info.rcfarea);
					
					$("#rcf_comkr").val(data.rcf.comphannm);
					$("#rcf_comkr").attr("code", data.rcf.compcd);
					$("#rcf_comkr").attr("seq", data.rcf.seq);
					$("#rcf_comen").val(data.rcf.compengnm);
					$("#rcf_ind").val(data.rcf.indunm);
					$("#rcf_ind").attr("code", data.rcf.idnucd);
					$("#rcf_nat").val(data.rcf.natnm);
					$("#rcf_nat").attr("code", data.rcf.natcd);
					$("#rcf_city").val(data.rcf.city);
					$("#rcf_addr").val(data.rcf.addr);
					
					if(param.credcd == brs) {  // '100101' : BRS
						$("#brs_section").show();
					} else if(param.credcd == rcf) { // '100102' : RCF  
						$("#rcf_section").show();
					}
				}else{
					alert(json.error.errorMessage);
					return false;
				}
				
			});
		};
		
		function doBuildComboRCFTypeDetail(codes, detailcode, selectedvalue) {
			_h = [];
			_h.push('<option value="" selected>선택해주세요.</option>');
			for(var i=0; i<codes[detailcode].length; i++){
				_h.push('<option value="' + codes[detailcode][i].itemcd + '">' + codes[detailcode][i].itemnm + '</option>');
			}
			$("#rcf_detail_type").empty().append(_h.join(""));
			$("#rcf_detail_type").val(selectedvalue);
		}
		
		$("#rcf_type").off("change").on("change", function(){
			var rcftypecd = $(this).val();
			if (rcftypecd == rcfleisuredetail || rcftypecd == rcfinfradetail) {
				doBuildComboRCFTypeDetail(codes, rcftypecd == rcfleisuredetail ? rcfleisure : rcfinfra, "");
				
				$("#rcf_etc").val("");
			}
			if (rcftypecd == rcfetcdetail) {
				$("#rcf_detail_type").empty().append('<option value="">선택해주세요.</option>');
				$("#rcf_etc").val(data.info.rcftypeetc);
			}
			if (rcftypecd != rcfleisuredetail && rcftypecd != rcfinfradetail && rcftypecd != rcfetcdetail) {
				$("#rcf_detail_type").empty().append('<option value="" selected>선택해주세요.</option>');
				
				$("#rcf_etc").val("");
			}
		});
		
		$("#completeEditCredential").off("click").on("click", function(){
			param = {};
			
			param.prjtcd = data.info.prjtcd;
			
			param.credtgtcd = $('#credtgt_type').val();
			if(param.credtgtcd == "") {
				alert("대상여부를 지정해주세요.");
				return;
			}
			param.refyearly = $('#pdt').data("refyearly");
			param.ctgcd = $('#pdt').data("ctgcd");
			param.pdtcd = $('#pdt').data("pdtcd");
			param.clientnatnm = $("#cli_nat").val();
			param.clientnatcd = $("#cli_nat").attr("code");
			param.clientindnm = $("#cli_ind").val();
			param.clientindcd = $("#cli_ind").attr("code");
			
			param.target = [];
			for(var i=0; i<3; i++) {
				var _d = {};
				_d.comphannm = $("#tar" + i + "_comkr").val();
				_d.compcd = $("#tar" + i + "_comkr").attr("code");
				_d.seq = $("#tar" + i + "_comkr").attr("seq");
				_d.compengnm = $("#tar" + i + "_comen").val();
				_d.indunm = $("#tar" + i + "_ind").val();
				_d.inducd = $("#tar" + i + "_ind").attr("code");
				_d.natnm = $("#tar" + i + "_nat").val();
				_d.natcd = $("#tar" + i + "_nat").attr("code");
				_d.city = $("#tar" + i + "_city").val();
				_d.addr = $("#tar" + i + "_addr").val();
				param.target.push(_d);
			}
			
			param.trgtetc = $("#tar_etc").val();
			param.prjtdesc = $("#detail_work").val();
			
			param.interest = [];
			var _d = {};
			_d.comphannm = $("#iter_comkr").val();
			_d.compcd = $("#iter_comkr").attr("code");
			_d.seq = $("#iter_comkr").attr("seq");
			_d.compengnm = $("#iter_comen").val();
			_d.indunm = $("#iter_ind").val();
			_d.inducd = $("#iter_ind").attr("code");
			_d.natnm = $("#iter_nat").val();
			_d.natcd = $("#iter_nat").attr("code");
			_d.city = $("#iter_city").val();
			_d.addr = $("#iter_addr").val();
			param.interest.push(_d);
			
			param.bondsecure = [];
			$("input[name='security_type']:checked").each(function() {
				var itemobj = {};
				itemobj.itemcd = $(this).val();
				param.bondsecure.push(itemobj);
			});
			
			param.bondsecurenone = [];
			$("input[name='unsecured_type']:checked").each(function() {
				var itemobj = {};
				itemobj.itemcd = $(this).val();
				param.bondsecurenone.push(itemobj);
			});

			param.brssalecd = $('#disposal_type').val();
			param.brsopb = $("#opb_data").val();

			param.brsbuyer = [];
			_d = {};
			_d.comphannm = $("#brs_comkr").val();
			_d.compcd = $("#brs_comkr").attr("code");
			_d.seq = $("#brs_comkr").attr("seq");
			_d.compengnm = $("#brs_comen").val();
			_d.indunm = $("#brs_ind").val();
			_d.inducd = $("#brs_ind").attr("code");
			_d.natnm = $("#brs_nat").val();
			_d.natcd = $("#brs_nat").attr("code");
			_d.city = $("#brs_city").val();
			_d.addr = $("#brs_addr").val();
			param.brsbuyer.push(_d);

			var rcftypecd = $("#rcf_type option:selected").val();
			param.rcftypecd = rcftypecd;
			param.rcftypea = "";
			param.rcftypeb = "";
			param.rcftypeetc = "";
			if (rcftypecd == rcfleisuredetail) {
				param.rcftypea = $("#rcf_detail_type option:selected").val();
			}
			if (rcftypecd == rcfinfradetail) {
				param.rcftypeb = $("#rcf_detail_type option:selected").val();
			}
			if (rcftypecd == rcfetcdetail) {
				param.rcftypeetc = $("#rcf_etc").val();				
			}
			param.rcfland = $("#land_area").val();
			param.rcfarea = $("#total_area").val();
			
			param.rcf = [];
			_d = {};
			_d.comphannm = $("#rcf_comkr").val();
			_d.compcd = $("#rcf_comkr").attr("code");
			_d.seq = $("#rcf_comkr").attr("seq");
			_d.compengnm = $("#rcf_comen").val();
			_d.indunm = $("#rcf_ind").val();
			_d.inducd = $("#rcf_ind").attr("code");
			_d.natnm = $("#rcf_nat").val();
			_d.natcd = $("#rcf_nat").attr("code");
			_d.city = $("#rcf_city").val();
			_d.addr = $("#rcf_addr").val();
			param.rcf.push(_d);

			//console.log(param);
			
			Ajax.post("CredentialSave", param, function(json) {
				callback(json);
			});
			
			$("#editCredential").dialog("close");
			//callback("");
		});
		
		$(".btn_company_sel").off("click").on("click", function() {
			var _s = $(this);
			POPUP.ViewPopup("companySearch", null, function(selectedCompanyInfo) {
//				console.log(selectedCompanyInfo);
				if(selectedCompanyInfo.hangnm != "" && selectedCompanyInfo.hangnm != null) {
					_s.parent().find("input").val(selectedCompanyInfo.hangnm);
					_s.parent().find("input").attr("code", selectedCompanyInfo.upchecd);
					_s.closest("tr").next().find("input.companyEngNm").val(selectedCompanyInfo.englnm);
					if(selectedCompanyInfo.inducd != "") {
						_s.closest("tr").next().find("input.industryNm").val(selectedCompanyInfo.glindunmk + "/" + selectedCompanyInfo.indunm);
						_s.closest("tr").next().find("input.industryNm").attr("code", selectedCompanyInfo.inducd);
					} else {
						_s.closest("tr").next().find("input.industryNm").val("");
						_s.closest("tr").next().find("input.industryNm").attr("code", "");
					}
				}
			});
		});

		$(".btn_industry_sel").off("click").on("click", function() {
			var _s = $(this);
			POPUP.ViewPopup("industrySearch", null, function(selectedIndustryInfo) {
				_s.parent().find("input").val(selectedIndustryInfo.glindunmk + "/" + selectedIndustryInfo.indunm);
				_s.parent().find("input").attr("code", selectedIndustryInfo.inducd);
			});
		});
		
		$(".btn_pdt_sel").off("click").on("click", function() {
			var _s = $(this);
			POPUP.ViewPopup("select_pdt", null, function(pdt) {
				_s.parent().find("input").val(pdt.pdtcd + "/" + pdt.pdtnm);
				_s.parent().find("input").data("refyearly", pdt.refyearly);
				_s.parent().find("input").data("ctgcd", pdt.ctgcd);
				_s.parent().find("input").data("pdtcd", pdt.pdtcd);
			});
		});
		
		$(".btn_nation_sel").off("click").on("click", function() {
			var _s = $(this);
			POPUP.ViewPopup("nationSearch", null, function(selectedNationInfo) {
				if(selectedNationInfo.etcdnm != "" && selectedNationInfo.etcdnm != null) {
					_s.parent().find("input").val(selectedNationInfo.etcdnm);
					_s.parent().find("input").attr("code", selectedNationInfo.etcocd);
				}
			});
		});
		
		$(".btn_remove_sel").off("click").on("click", function() {
			var _s = $(this).prev();
			_s.val("");
			_s.attr("code", "");
			if(_s.attr('id').indexOf('comkr') != -1) {
				_s.closest("tr").next().find("input.companyEngNm").val("");
			}
		});
	}
	
	function doMakePopupPdtCategory(id, param, callback){
		var refyearlyidx = 0;
		var ctgcdidx = 1;
		var ctgnmidx = 2;
		var parent_refyearly = "";
		parent_refyearly = param.refyearly;
		var system_category_list = [];
		
		var m_gridDialogCategoryDefinition = {
			parent:"div-dialog-category-grid-area",
			image_path:"./js/dhtmlx/codebase/imgs/",
			skin:"dhx_skyblue",
			columns:[
			         	{id:"refyearly", label:["참조년도"], type:"ro", align:"left", valign:"middle", widthP:"0", sort: "str"},
			         	{id:"ctgcd", label:["코드"], type:"ro", align:"center", valign:"middle", widthP:"12", sort: "str"},
			         	{id:"ctgnm", label:["이름"], type:"ed", align:"left", valign:"middle", widthP:"40", sort: "str"}
					]
		};
		var m_gridDialogCategoryObj = null;
		if(!m_gridDialogCategoryObj){
			m_gridDialogCategoryObj = new dhtmlXGridObject(m_gridDialogCategoryDefinition);
			m_gridDialogCategoryObj.setInitWidthsP("0,12,40");
			m_gridDialogCategoryObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px;font-size:12px", null, null, null);
		}
		
		doLoadCategoryRefyearlyList();
		
		// 참조년도 변경 이벤트
		$("select[id='dialog_sel_refyearly']").off("change").on("change", function() {
			doLoadCategoryList();
		});
		
		// 저장
		$("#dialog_btn_category_save").off("click").on("click", function(){			
			var rowIds = m_gridDialogCategoryObj.getChangedRows(true);
			if (rowIds) {
				var items = [];
				var arrRowIds = rowIds.split(",");
				for (var i=0; i<arrRowIds.length; i++) {
					var item = {};
					var rowId = arrRowIds[i];
					item.refyearly = m_gridDialogCategoryObj.cells(rowId, refyearlyidx).getValue();
					item.ctgcd = m_gridDialogCategoryObj.cells(rowId, ctgcdidx).getValue();
					item.ctgnm = m_gridDialogCategoryObj.cells(rowId, ctgnmidx).getValue();
					items.push(item);
				}
				// 서버 반영
				if (confirm("저장하시겠습니까?")) {
					Ajax.post("AdminPdtCategorySave", {items:items}, function(serverData){
						if (serverData.result) {
							alert("저장되었습니다.");
							doLoadCategoryRefyearlyList();
						} else {
							alert(serverData.error.errorMessage);
							return false;
						}
					});
				}
			} else {
				alert("변경 혹은 추가된 항목이 없습니다.");
				return false;
			}
		});
		
		// 추가
		$("#dialog_btn_category_add").off("click").on("click", function(){			
			var sys_category = $("select[id='dialog_system_category'] option:selected").val();
			if (sys_category == "") {
				alert("추가할 카테고리를 선택해주세요.");
				return false;
			}
			
			var rowIds = m_gridDialogCategoryObj.getAllRowIds();
			if (rowIds) {
				var arrRowIds = rowIds.split(",");
				for (var i=0; i<arrRowIds.length; i++) {
					var rid = arrRowIds[i];
					var exist_ctgcd = m_gridDialogCategoryObj.cells(rid, ctgcdidx).getValue();
					if (sys_category == exist_ctgcd) {
						alert("이미 추가된 카테고리 입니다.");
						return false;
					}
				}
			}
			
			var sys_category_obj = {};
			for (var i=0; i<system_category_list.length; i++) {
				if (sys_category == system_category_list[i].ctgcd){
					sys_category_obj = system_category_list[i];
					break;
				}
			}
			// 추가시키기
			var newId = (new Date()).valueOf();
			m_gridDialogCategoryObj.addRow(newId, sys_category_obj.refyearly+","+sys_category_obj.ctgcd+","+sys_category_obj.ctgnm);
		});
		
		function doLoadCategoryRefyearlyList() {
			$("select[id='dialog_sel_refyearly']").empty();
			m_gridDialogCategoryObj.clearAll();
			Ajax.post("AdminPdtRefyearList", {}, function(serverData){
				if (serverData.result) {
					var refyearlylist = serverData.data.list;
					for (var i=0; i<refyearlylist.length; i++){
						$("<option></option>").text(refyearlylist[i]).attr("value", refyearlylist[i]).appendTo("select[id='dialog_sel_refyearly']");					
					}
					$("select[id='dialog_sel_refyearly']").val(parent_refyearly).prop("selected", true);
					// PDT 목록 조회
					doLoadCategoryList();
				} else {
					return false;
				}
			});
		}
		
		function doLoadCategoryList() {
			m_gridDialogCategoryObj.clearAll();
			var refyearly = $("select[id='dialog_sel_refyearly'] option:selected").val();
			Ajax.post("AdminPdtCategoryList", {refyearly:refyearly}, function(serverData){
				if (serverData.result) {
					var dctglist = serverData.data.list;
					m_gridDialogCategoryObj.parse(dctglist, "js");
					m_gridDialogCategoryObj.setColumnHidden(refyearlyidx, true);
					
					var systemctglist = serverData.data.system;
					system_category_list = systemctglist;
					$("select[id='dialog_system_category']").empty();
					if (systemctglist.length == 0) {
						$("<option></option>").text("추가할 수 있는 카테고리가 없습니다.").attr("value", "").appendTo("select[id='dialog_system_category']");
					} else {
						$("<option></option>").text("카테고리를 선택해서 추가할 수 있습니다.").attr("value", "").appendTo("select[id='dialog_system_category']");						
					}
					for (var i=0; i<systemctglist.length; i++){
						$("<option></option>").text(systemctglist[i].ctgcd +"/"+ systemctglist[i].ctgnm).attr("value", systemctglist[i].ctgcd).appendTo("select[id='dialog_system_category']");					
					}
					
				} else {
					alert(serverData.error.errorMessage);
					return false;
				}
			});
		}
	}
	
	function doMakePopupPdtAdd(id, param, callback){
		var refyearlyidx = 0;
		var ctgcdidx = 1;
		var pdtcdidx = 3;
		var parent_refyearly = "";
		parent_refyearly = param.refyearly;
		
		var m_gridDialogPdtDefinition = {
			parent:"div-dialog-pdt-grid-area",
			image_path:"./js/dhtmlx/codebase/imgs/",
			skin:"dhx_skyblue",
			columns:[
			         	{id:"refyearly", label:["참조년도"], type:"ro", align:"left", valign:"middle", widthP:"0", sort: "str"},
			         	{id:"ctgcd", label:["카테고리코드"], type:"ro", align:"center", valign:"middle", widthP:"0", sort: "str"},
			         	{id:"ctgnm", label:["카테고리"], type:"ed", align:"left", valign:"middle", widthP:"12", sort: "str"},
			         	{id:"pdtcd", label:["PDT 코드"], type:"ro", align:"center", valign:"middle", widthP:"6", sort: "str"},
			         	{id:"pdtnm", label:["PDT 이름"], type:"ro", align:"center", valign:"middle", widthP:"15", sort: "str"},
			         	{id:"comnm", label:["Comment"], type:"ro", align:"center", valign:"middle", widthP:"20", sort: "str"}
					]
		};
		var m_gridDialogPdtObj = null;
		if(!m_gridDialogPdtObj){
			m_gridDialogPdtObj = new dhtmlXGridObject(m_gridDialogPdtDefinition);
			m_gridDialogPdtObj.setInitWidthsP("0,0,12,6,15,20");
			m_gridDialogPdtObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px;font-size:12px", null, null, null);
		}
		
		doLoadPdtRefyearlyList();
		
		function doLoadPdtRefyearlyList() {
			m_gridDialogPdtObj.clearAll();
			Ajax.post("AdminPdtAddableList", {refyearly:parent_refyearly}, function(serverData){
				if (serverData.result) {
					var dpdtlist = serverData.data.list;
					m_gridDialogPdtObj.parse(dpdtlist, "js");
					m_gridDialogPdtObj.setColumnHidden(refyearlyidx, true);
					m_gridDialogPdtObj.setColumnHidden(ctgcdidx, true);
				} else {
					alert(serverData.error.errorMessage);
					return false;
				}
			});
		}
		
		$("#completePdtAdd").off("click").on("click", function(){
			var rowId = m_gridDialogPdtObj.getSelectedRowId();
			if (rowId) {
				var product = {};
				product.refyearly = m_gridDialogPdtObj.cells(rowId, refyearlyidx).getValue();
				product.ctgcd = m_gridDialogPdtObj.cells(rowId, ctgcdidx).getValue();
				product.pdtcd = m_gridDialogPdtObj.cells(rowId, pdtcdidx).getValue();
				product.pdtnm = m_gridDialogPdtObj.cells(rowId, 4).getValue();
				product.comnm = m_gridDialogPdtObj.cells(rowId, 5).getValue();
				
				$("#pdtAdd").dialog("close");
				callback(product);
				
			} else {
				alert("추가할 PDT를 선택해주세요.");
				return false;
			}
		});
	}
	
	function searchValidation(checkedId){
		if(checkedId == "paramEmpnm"){
			if($("#inputSearchParam").val().length < 2){
				return "이름은 2글자 이상 입력하십시오.";
			}
		} else {
			if($("#inputSearchParam").val().length < 6){
				return "사번은 6글자입니다.";
			}
		}
		return null;
	}
	
	function doRadioTrsnfed(){
		var checkedId = $("#userSearchConditionBox input[type=radio]:checked").attr("id");
		var inputBox = $("#inputSearchParam");
		inputBox.val("");
		if(checkedId == "paramEmpnm"){
			inputBox.attr("maxlength", "20");
			inputBox.off("keyup");
		} else {
			inputBox.attr("maxlength", "6");
			inputBox.off("keyup").on("keyup", function(e) {
				$(this).val( $(this).val().replace(/[^0-9]/gi,"") );
			});
		}
	}
	
	function addDeleteEvent(target){
		var deleteBtns = target.find(".btn_delete");
		deleteBtns.off("click").on("click", function(){
			var btnInd = target.find(".btn_delete").index(this);
			target.find(".frm_user").eq(btnInd).remove();
			if(target.find(".frm_user").size() == 0){
				target.empty();
			}else if(btnInd == 0){
				target.find(".frm_user").eq(btnInd).appendTo("#currentQRP");
			}
			if(target.find(".frm_user").size() == 1){
				target.find("#QRPhsty").remove();
			}
		});
	}

	function addSearchEvent(){
		$(".inp_user_btn").off("click").on("click", function(){
			var btnInd = $(".inp_user_btn").index(this);
			ViewPopup("userSearch", null, function(data){
				if(data.emplno&&data.kornm&&data.gradnm){
					$("#_added").attr("data-empno", data.emplno);
					$("#_added").attr("data-empnm", data.kornm);
					$("#_added").attr("data-gradnm", data.gradnm);
					$("#_added").val(data.kornm+'('+ data.emplno +')'+ ' / '+ data.gradnm);
				};
			});
		});
	}
	
	function doMakePopupReadMailHistory(popupId, param, callback){
		var gridMailHistoryObj = null;
		var gridMailRcptObj = null;
		
		var gridMailHistoryDef = {
				parent:"popup-grid-mailhistory",
				image_path:"./js/dhtmlx/codebase/imgs/",
				skin:"dhx_skyblue",
				columns:[
				         	{id:"snddate", label:["발송일"], type:"ro", align:"left", valign:"middle", sort: "str"},
				         	{id:"kornm", label:["발송인 이름"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
				         	{id:"sndemplno", label:["발송인 사번"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
						]
			};
		var gridMailRcptDef = {
				parent:"popup-grid-rcpt",
				image_path:"./js/dhtmlx/codebase/imgs/",
				skin:"dhx_skyblue",
				columns:[
				         	{id:"rmail", label:["메일주소"], type:"ro", align:"left", valign:"middle", width:"*", sort: "str"},
				         	{id:"kornm", label:["이름"], type:"ro", align:"left", valign:"middle", width:"100", sort: "str"},
				         	{id:"emplno", label:["사번"], type:"ro", align:"left", valign:"middle", width:"100", sort: "str"},
				         	{id:"gradnm", label:["직급"], type:"ro", align:"left", valign:"middle", width:"100", sort: "str"},
				         	{id:"team", label:["소속"], type:"ro", align:"left", valign:"middle", width:"150", sort: "str"},
						]
			};
		
		// Mail 발송 히스토리 Grid
		gridMailHistoryObj = new dhtmlXGridObject(gridMailHistoryDef);
		gridMailHistoryObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px; font-size:12px", null, null, null);
		gridMailHistoryObj.attachEvent("onRowSelect", function(rid, ind){
			// 수신자 리스트 Load
			var subject = gridMailHistoryObj.getRowAttribute(rid,"subject");
			var contents = gridMailHistoryObj.getRowAttribute(rid,"contents");
			_loadMailRcptHistory(rid, subject, contents);
			
			// 메일 제목, 본문내용 Load 
			var _param = {};
			_param.mailid 	= param.mailid;
			_param.mid		= rid;
			_loadMailHistory(_param);
		})
		// Mail 수신자 리스트 Grid
		gridMailRcptObj = new dhtmlXGridObject(gridMailRcptDef);
		gridMailRcptObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px; font-size:12px", null, null, null);
		gridMailRcptObj.enableSmartRendering(true, 200);
		
		// 발송된 메일 History 가져오기
		Ajax.post("AdminMailHistory", {mailid:param.mailid}, function(serverData){
			if (serverData.result) {
				var mailHistroyList = serverData.data.list;
				for(var i=0; i<mailHistroyList.length; i++){
					mailHistroyList[i].id = mailHistroyList[i].mid;
				}
				gridMailHistoryObj.clearAll();
				gridMailHistoryObj.parse(mailHistroyList, "js");
				gridMailHistoryObj.selectRow(0, true);
			} else {
				alert(serverData.error.errorMessage);
				return false;
			}
		});
		
		// 발송 된 메일을 수신한 인원의 History 가져오기
		function _loadMailRcptHistory(_mid, _subject, _contents){
			var _param = {};
			_param.mailid 	= param.mailid;
			_param.mid		= _mid;
			
			Ajax.post("AdminMailRcptHistory", {mid:_mid}, function(serverData){
				if (serverData.result) {
					var rcptList = serverData.data.list;
					for(var i=0; i<rcptList.length; i++){
						rcptList[i].id = rcptList[i].rmail;
					}
					// 초기화
					gridMailRcptObj.clearAll();
					
					// 데이터 출력
					gridMailRcptObj.parse(rcptList, "js");
				} else {
					alert(serverData.error.errorMessage);
					return false;
				}
			});
		}
		
		function _loadMailHistory(_param){
			$("#popup-mail-subject").empty();
			$("#popup-mail-contents").empty();
			
			Ajax.post("AdminMailHistory", _param, function(serverData){
				if (serverData.result) {
					var mailHistroyList = serverData.data.list;
					$("#popup-mail-subject").html(mailHistroyList[0].subject);
					$("#popup-mail-contents").html(mailHistroyList[0].contents);
				} else {
					alert(serverData.error.errorMessage);
					return false;
				}
			});
		}
	}
	
	
	function doMakePopupSendToTarget(popupId, param, callback){
		
		var gridTargetListDef = {
				parent:"popup-grid-mail-targetlist",
				image_path:"./js/dhtmlx/codebase/imgs/",
				skin:"dhx_skyblue",
				columns:[
				         	{id:"chk", 		label:["", "#master_checkbox"], type:"ch", align:"left", valign:"middle", width:"30"},
				         	{id:"kornm", 	label:["이름", "#text_filter"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
				         	{id:"emplno", 	label:["사번", "#text_filter"], type:"ro", align:"left", valign:"middle", width:"80", sort: "str"},
				         	{id:"gradnm", 	label:["직급", "#text_filter"], type:"ro", align:"left", valign:"middle", width:"120", sort: "str"},
				         	{id:"team", 	label:["소속", "#text_filter"], type:"ro", align:"left", valign:"middle", width:"*", sort: "str"},
						]
			};
		// Mail 수신자 리스트 Grid
		var gridTargetListObj = new dhtmlXGridObject(gridTargetListDef);
		gridTargetListObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px; font-size:12px", null, null, null);
		gridTargetListObj.enableSmartRendering(true, 200);
		
		
		Ajax.post("AdminMailRcptList", {mailid:param.mailid}, function(serverData){
			
			if (serverData.result) {
				var rcptList = serverData.data.list;
				for(var i=0; i<rcptList.length; i++){
					rcptList[i].id = rcptList[i].emplno;
					rcptList[i].chk	= 0;
					rcptList[i].team = rcptList[i].teamcd + "/" + rcptList[i].teamnm;
					rcptList[i].userdata = {
						// rmail:rcptList[i].inteid + "@samil.com",
						rmail:rcptList[i].email,
						rname:rcptList[i].kornm,
						rid:rcptList[i].emplno
					}
				}
				
				// 초기화
				gridTargetListObj.clearAll();
				
				// 데이터 출력
				gridTargetListObj.parse(rcptList, "js");
			} else {
				alert(serverData.error.errorMessage);
				return false;
			}
		});
		
		
		
		$("#btnPopupSendMail").off("click").on("click", function(){
			var retVal = [];
			var ids = gridTargetListObj.getCheckedRows(0);
			if(ids == ""){
				alert("선택된 대상이 없습니다.");
				return;
			}
			
			if(confirm("선택된 대상에게 메일을 발송 하시겠습니까?")){
				ids = ids.split(",");
				for(var i=0; i<ids.length; i++){
					retVal.push({
						"rid":gridTargetListObj.getUserData(ids[i],"rid"),
						"rmail":gridTargetListObj.getUserData(ids[i],"rmail"),
						"rname":gridTargetListObj.getUserData(ids[i],"rname")
					})
				}
				
				if(callback){
					callback(param.mailid, retVal);
					$( "#sendMail" ).dialog( "close" );
				}
			}
		})
	}
	
	function doMakePopupExceptCredentialByExcel(id, param, callback){
		var allow_extension_array = ["xls"];
		var allow_role = ["R01", "R98", "R99"];
		var checkRole = false;
		for(var i=0; i<USERROLE.length; i++){
			if($.inArray(USERROLE[i].rolecd, allow_role)>0){
				checkRole = true;
				break;
			}
		}
		
		var upload_config = {
			title:"Excel Import",
			allowExtentions:allow_extension_array,
			callback: function(uploadedFilePath) {
				if (!uploadedFilePath || uploadedFilePath == "undefined" || uploadedFilePath == "") {
					alert("파일 업로드 실패입니다.");
					return false;
				}
				uploadedFilePath = $.trim(uploadedFilePath);
				if (uploadedFilePath == "NOT_ALLOWED_EXT") {
					alert("허용된 확장자가 아닙니다.\n["+allow_extension_array.join(", ")+"] 확장자만 업로드 가능합니다.");
					return false;
				}
				
				var contentType = "json";
				var _o = {"SystemCode":"dealcredential", "Method":"service", "ContentType":contentType, "ServiceTask":"ExcelImportForExceptCredential"};
				var _p = $.extend({}, _o, {uploadedFileName:uploadedFilePath});
				$.ajax({
					url: CURR_INFO.serviceBroker,
					type: "POST",
					dataType: contentType,
					timeout: 1000 * 60 * 20,
					async: true,
					cache: false,
					data: $.param(_p),
					beforeSend: function() {
						progressOn();
					},
					success: function(data, textStatus, jqXHR) {
						try {
							if (data) {
								if (false == data.result) {
									if ("99998" == data.error.errorCode) {
										alert("로그인이 필요한 서비스입니다.");
										location.href = CURR_INFO.portalUri;
										return false;
									}
									if ("99997" == data.error.errorCode) {
										alert("권한이 없는 사용자입니다.");
										location.href = CURR_INFO.portalUri;
										return false;
									}
									if ("10002" == data.error.errorCode || "10003" == data.error.errorCode) {
										alert("요청한 서비스를 찾을 수 없습니다.");
										return false;
									}
								}
							}
						} catch(e) {}
						if (data) {
							if (false == data.result) {
								$( "#"+id ).dialog( "close" );
								POPUP.ViewPopup("guideline", {message: data.error.errorMessage});
								return false;
							} else  {
								//alert(data.data.message);
								POPUP.ViewPopup("guideline", {message: data.data.message});
								$( "#"+id ).dialog( "close" );
								
							}
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						try {
							if (jqXHR.readyState == 0 || jqXHR.readyState == 4) {
								alert("잠시 후 다시 접속하시기 바랍니다.");
								return false;
							}
						} catch(e) {}
					},
					complete: function(jqXHR, textStatus) {
						progressOff();
					}
				});
			}
		}
		
		var FileUploadObj = new FileUpload(upload_config);
		var html = FileUploadObj.getFileUploalModalHtml();
		$("#excel_except_credential_container").empty().append(html);
		
		// 엑셀 업로드
		$("#btnTemplateUpload").off("click").on("click", function(){
			FileUploadObj.upload();
		});
		
		// 템플릿 다운로드
		$("#btnTemplateDownload").off("click").on("click", function(){
			location.href = "files/Upload_Template_ExceptProject.xls";
		});
	}
	
	function doMakePopupSelectProduct(popupId, param, callback){
		var comboYearyDef = {
				parent:"select_pdt_yearly",
				width:70,
				name:"refyearly",
				skin:"dhx_skyblue",
				image_path:"./js/dhtmlx/codebase/imgs/",
		}
		var gridPdtDef = {
				parent:"select_pdt_grid",
				image_path:"./js/dhtmlx/codebase/imgs/",
				skin:"dhx_skyblue",
				columns:[
		         	{id:"ctgnm", 	label:["카테고리", 	"#text_filter"], type:"rotxt", align:"left", valign:"middle", width:"150", sort: "str"},
		         	{id:"pdtcd", 	label:["코드", 		"#text_filter"], type:"rotxt", align:"left", valign:"middle", width:"80", sort: "str"},
		         	{id:"pdtnm", 	label:["이름", 		"#text_filter"], type:"rotxt", align:"left", valign:"middle", width:"150", sort: "str"},
		         	{id:"comnm", 	label:["Comment", 	"#text_filter"], type:"rotxt", align:"left", valign:"middle", width:"*", sort: "str"},
				]
			};
		
		var comboRefYearlyObj	= new dhtmlXCombo(comboYearyDef);
		comboRefYearlyObj.readonly(true);
		comboRefYearlyObj.attachEvent("onChange", function(value, text){doLoadPdtList(value);})
		
		var gridPdtObj 			= new dhtmlXGridObject(gridPdtDef);
		gridPdtObj.setStyle("font-family:Georgia, Dotum, Helvetica, sans-serif; text-align:center; padding:2px; font-size:12px", null, null, null);
		
		$("#" + popupId).data('combo', comboRefYearlyObj);	// 팝업닫을때 Combo박스 Unload하기 위함.
		
		$("#btnPopupSelectPdt").off("click").on("click", function(){
			if(typeof callback == "function"){
				var rowid = gridPdtObj.getSelectedRowId();
				if(rowid){
					var param = {
							refyearly	:gridPdtObj.getUserData(rowid, "refyearly"),
							ctgcd		:gridPdtObj.getUserData(rowid, "ctgcd"),
							pdtcd		:gridPdtObj.cells(rowid, gridPdtObj.getColIndexById("pdtcd")).getValue(),
							pdtnm		:gridPdtObj.cells(rowid, gridPdtObj.getColIndexById("pdtnm")).getValue()
						}
						callback(param);
					$("#" + popupId).dialog("close");
				}else{
					alert("대상을 선택하세요");
				}
			}else{
				$("#" + popupId).dialog("close");
			}
		})
		
		// Refyearly 로드
		Ajax.post("AdminPdtRefyearList", {}, function(serverData){
			if (serverData.result) {
				var list = serverData.data.list;
				var option = []; 
				for(var i=0; i<list.length; i++){
					option.push([list[i], list[i]]);
				}
				comboRefYearlyObj.clearAll();
				comboRefYearlyObj.addOption(option);
				comboRefYearlyObj.selectOption(0, true, true);
			} else {
				return false;
			}
		});
		
		function doLoadPdtList(refyearly){
			Ajax.post("CredentialProductListWithCategory", {refyearly:refyearly}, function(serverData){
				if (serverData.result) {
					var list = serverData.data.list;
					for(var i=0; i<list.length; i++){
						list[i].userdata={refyearly:list[i].refyearly, ctgcd:list[i].ctgcd}
					}
					// 초기화
					gridPdtObj.clearAll();
					// 데이터 출력
					gridPdtObj.parse(list, "js");
				} else {
					alert(serverData.error.errorMessage);
					return false;
				}
			});
		}
	};
	
//	$(document).ready(function(){		
//		$("#btn_spt_prjt").off("click").on("click", function(){
//			popupId = $(this).attr("name");
//			//param = {prjtcd:"16113-01-000"};//지원프로젝트
//			param = {prjtcd:"16113-01-000", pdtNm:"(외감)비상장 외감법인 일반기업회계기준 감사"};//변경이력
//			ViewPopup("userSearch", param, function(data){alert(data.emplno + " : " + data.kornm)});
//			dialog.dialog("open");
////			togglePopupView(popupId);
////			dialog.dialog( "open" );
//		})
		
//		onPopup("userSearch", null, function(data){alert(data.emplno + " : " + data.kornm)});
//	});
	
	function progressOn(){
		$("body").append('<div id="div-progress" class="progress"><i class="fa fa-spinner fa-pulse fa-5x"></i><div>&nbsp;</div><div style="color:#fff">엑셀 일괄 임포트 중입니다...</div></div>');
	}
	
	function progressOff(){
		$("#div-progress").remove();
	}
	
	
	
	return {
		ViewPopup:ViewPopup
	};
	

	
})();
