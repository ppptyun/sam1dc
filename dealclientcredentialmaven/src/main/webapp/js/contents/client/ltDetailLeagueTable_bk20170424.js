var DetailLT = (function(){
	var CONST = {
		  chrgconfcd	: {YES : "100801", NO : "100802"}
		, statuscd 		: {COMPLETE : "100601", PROCESS : "100602", DROP : "100603"}
		, conscd 		: {UNCLF : "100301", BUY : "100302", MNA : "100303", REAL : "100304"}
		, TYPE 			: {BUY : "Buy", MNA : "Mna", REAL : "Real"}
		, CONDITION		: {READ : "Read", MODIFY : "Modify"}
		, COUNT 		: 3
		, EDITROLE 		: ["R03", "R98", "R99"]
	};
	var lv_data = {
		  prjtcd : ""
		, condition : ""	// READ
		, type : ""
		, list : {}
		, remove : []
		, auth : {}
	};
	var cal1 = "";
	var cal2 = "";
	var cal3 = "";
	var cal4 = "";
	var modifycheck = false;
	
	function init(prjtcd, condition, type) {
		lv_data.prjtcd = prjtcd;
		lv_data.condition = condition;
		lv_data.type = type;
		doInit();
		bindEvent();
	};
	
	// 초기화
	function doInit(){
		lv_data.remove = [];
		lv_data.list = {};
		doLoadDetailLeagueTable();
	};
	
	// 목록 조회
	function doLoadDetailLeagueTable(){
		var url = "LtConsult" + lv_data.type + "Detail";
		Ajax.post(url, {prjtcd:lv_data.prjtcd}, function(serverData){
			if(serverData.result) {
				var list = serverData.data.project;
				$.extend(lv_data.list, list);
				lv_data.auth = serverData.data.auth;
				drawDetailLeagueTable();
				if(lv_data.condition == CONST.CONDITION.READ) {
					readPageSetting();
				} else if(lv_data.condition == CONST.CONDITION.MODIFY) {
					editPageSetting();
				}
			} else {
				alert(serverData.error.errorMessage);
				return false;
			}
		});
	};

	function drawDetailLeagueTable(){
		var _h = [];
		var _data = $.extend({}, lv_data.list);
		
		$(".dc_view_body").empty().append(drawSubstructureHtml().join(""));
		
		//기본정보
		$("#parent_basic").empty().append(drawBasicHtml().join(""));
		//일자
		$("#parent_date").empty().append(drawDateHtml().join(""));
		//금액
		$("#parent_curr").empty().append(drawCurrHtml().join(""));
		
		$("#prjtcd").val(_data.prjtcd + "/" + _data.prjtnm);
		$("#ptremp").val(_data.ptrempnm + "/" + _data.mgrempnm);
		$("#ctgcd").val(_data.ctgnm);
		$("#pdtcd").val(_data.pdtcd + "/" + _data.pdtnm);
		$("#chrgconfcd").val(_data.chrgconfnm);
		$("#hqcd").val(_data.hqcd + "/" + _data.hqnm);
		$("#comment").val(_data.comnm);
		$("#rate").val(GF.numberWithCommas(_data.rate));
		$("#amt").val(GF.numberWithCommas(_data.amt));
		$("#rateamt").val(GF.numberWithCommas(_data.rateamt));
		$("#upddt").text(_data.upddt);
		
		if(lv_data.condition == CONST.CONDITION.READ) {
			destroyCalendar();
			
			$("#consType").val(_data.consnm);
			$("#statusType").val(_data.statusnm);
			$("#confType").val(_data.confnm);
			if(_data.currcd != "") {
				$("#rateType").val(_data.currcd + "/" + _data.currnm);
			}
			$("#borddealType").val(_data.borddealnm);
			if(lv_data.type == CONST.TYPE.BUY) {
				$("#buyoutType").val(_data.buyoutnm);
				var deal1nm = "";
				for(var i=0; i<_data.deal1.length; i++){
					deal1nm += _data.deal1[i].dealitemnm;
					if(i != _data.deal1.length-1) {
						deal1nm += " / ";
					}
				}
				$("#deal1Type").val(deal1nm);
				$("#deal2Type").val(_data.dealnm2);
				$("#deal3Type").val(_data.dealnm3);
//				$("#deal4Type").val(_data.dealnm4);
			} else if(lv_data.type == CONST.TYPE.MNA) {
				$("#deal1Type").val(_data.dealnm1);
				$("#deal2Type").val(_data.dealnm2);
				$("#deal3Type").val(_data.dealnm3);
			}
		} else if(lv_data.condition == CONST.CONDITION.MODIFY) {
			createCalendar();
			
			_h = [];
			_h.push('<select id="consType" class="inp_change"><option value="">선택해주세요.</option>');
			for(var i=0; i<__cons_type_list.length; i++){
				if(__cons_type_list[i].code == CONST.conscd.UNCLF) {
					continue;
				}
				_h.push('<option value="' + __cons_type_list[i].code + '">' + __cons_type_list[i].name + '</option>');
			}
			_h.push('</select>');
			$("#conscd").empty().append(_h.join(""));
			$("#consType").val(_data.conscd);
			
			_h = [];
			_h.push('<select id="statusType" class="inp_change"><option value="">선택해주세요.</option>');
			for(var i=0; i<__status_list.length; i++){
				_h.push('<option value="' + __status_list[i].code + '">' + __status_list[i].name + '</option>');
			}
			_h.push('</select>');
			$("#statuscd").empty().append(_h.join(""));
			$("#statusType").val(_data.statuscd);
			
			_h = [];
			_h.push('<select id="confType" class="inp_change"><option value="">선택해주세요.</option>');
			for(var i=0; i<__conf_type_list.length; i++){
				_h.push('<option value="' + __conf_type_list[i].code + '">' + __conf_type_list[i].name + '</option>');
			}
			_h.push('</select>');
			$("#confcd").empty().append(_h.join(""));
			$("#confType").val(_data.confcd);
			
			_h = [];
			_h.push('<select id="rateType" class="inp_change"><option value="">선택해주세요.</option>');
			for(var i=0; i<__curr_list.length; i++){
				_h.push('<option value="' + __curr_list[i].code + '">' + __curr_list[i].name + '</option>');
			}
			_h.push('</select>');
			$("#ratecd").empty().append(_h.join(""));
			$("#rateType").val(_data.currcd);
			
			_h = [];
			_h.push('<select id="borddealType" class="inp_change"><option value="">선택해주세요.</option>');
			for(var i=0; i<__bord_type_list.length; i++){
				_h.push('<option value="' + __bord_type_list[i].code + '">' + __bord_type_list[i].name + '</option>');
			}
			_h.push('</select>');
			$("#borddeal").empty().append(_h.join(""));
			$("#borddealType").val(_data.borddealcd);
			
			if(lv_data.type == CONST.TYPE.BUY) {
				_h = [];
				_h.push('<select id="buyoutType" class="inp_change"><option value="">선택해주세요.</option>');
				for(var i=0; i<__buyout_type_list.length; i++){
					_h.push('<option value="' + __buyout_type_list[i].code + '">' + __buyout_type_list[i].name + '</option>');
				}
				_h.push('</select>');
				$("#buyoutcd").empty().append(_h.join(""));
				$("#buyoutType").val(_data.buyoutcd);
				
				_h = [];
				_h.push('<div class="data_cell edit_mode"><div class="inp_check_list">');
				for(var i=0; i<__buy_deal_type1_list.length; i++){
					_h.push('<span style="display:inline-block;margin-right:10px"><input type="checkbox" name="deal1Type" id="deal1Type_' + i + '" value="' + __buy_deal_type1_list[i].code + '"><label for="deal1Type_' + i + '">' + __buy_deal_type1_list[i].name + "</label></span>");
				}
				_h.push('</div></div>');
				$("#deal1").empty().append(_h.join(""));
				for(var i=0; i<_data.deal1.length; i++){
					$('input:checkbox[name="deal1Type"]').each(function() {
						if(this.value == _data.deal1[i].dealitemcd){
							this.checked = true;
						}
					});
				}
				
				_h = [];
				_h.push('<select id="deal2Type" class="inp_change"><option value="">선택해주세요.</option>');
				for(var i=0; i<__buy_deal_type2_list.length; i++){
					_h.push('<option value="' + __buy_deal_type2_list[i].code + '">' + __buy_deal_type2_list[i].name + '</option>');
				}
				_h.push('</select>');
				$("#deal2").empty().append(_h.join(""));
				$("#deal2Type").val(_data.dealcd2);
				
				_h = [];
				_h.push('<select id="deal3Type" class="inp_change"><option value="">선택해주세요.</option>');
				for(var i=0; i<__buy_deal_type3_list.length; i++){
					_h.push('<option value="' + __buy_deal_type3_list[i].code + '">' + __buy_deal_type3_list[i].name + '</option>');
				}
				_h.push('</select>');
				$("#deal3").empty().append(_h.join(""));
				$("#deal3Type").val(_data.dealcd3);
				
				/*_h = [];
				_h.push('<select id="deal4Type" class="inp_change"><option value="">선택해주세요.</option>');
				for(var i=0; i<__buy_deal_type4_list.length; i++){
					_h.push('<option value="' + __buy_deal_type4_list[i].code + '">' + __buy_deal_type4_list[i].name + '</option>');
				}
				_h.push('</select>');
				$("#deal4").empty().append(_h.join(""));
				$("#deal4Type").val(_data.dealcd4);*/
			} else if(lv_data.type == CONST.TYPE.MNA) {
				_h = [];
				_h.push('<select id="deal1Type" class="inp_change"><option value="">선택해주세요.</option>');
				for(var i=0; i<__mna_deal_type1_list.length; i++){
					_h.push('<option value="' + __mna_deal_type1_list[i].code + '">' + __mna_deal_type1_list[i].name + '</option>');
				}
				_h.push('</select>');
				$("#deal1").empty().append(_h.join(""));
				$("#deal1Type").val(_data.dealcd1);
				
				_h = [];
				_h.push('<select id="deal2Type" class="inp_change"><option value="">선택해주세요.</option>');
				for(var i=0; i<__mna_deal_type2_list.length; i++){
					_h.push('<option value="' + __mna_deal_type2_list[i].code + '">' + __mna_deal_type2_list[i].name + '</option>');
				}
				_h.push('</select>');
				$("#deal2").empty().append(_h.join(""));
				$("#deal2Type").val(_data.dealcd2);
				
				_h = [];
				_h.push('<select id="deal3Type" class="inp_change"><option value="">선택해주세요.</option>');
				for(var i=0; i<__mna_deal_type3_list.length; i++){
					_h.push('<option value="' + __mna_deal_type3_list[i].code + '">' + __mna_deal_type3_list[i].name + '</option>');
				}
				_h.push('</select>');
				$("#deal3").empty().append(_h.join(""));
				$("#deal3Type").val(_data.dealcd3);
			}
		}
		
		// 대상(Target)
		if(lv_data.type == CONST.TYPE.BUY) {
			$("#parent_target").empty().append(drawTargetHtml(_data.target).join(""));
			$("#parent_target_etc").empty().append(drawTargetEtcHtml(_data.trgtetc).join(""));
		} else if(lv_data.type == CONST.TYPE.REAL) {
			$("#parent_target").empty().append(drawRealTargetHtml(_data.target).join(""));
		}
		
		// 매각자(Seller), 인수자(Buyer) & 합병(존속/신설)법인, 피합병(소멸)법인 & 일자
		if(lv_data.type == CONST.TYPE.MNA) {
			_data.seller = _data.merging;
			_data.sellfinance = _data.mergingfinance;
			_data.sellaudit = _data.mergingaudit;
			_data.selllaw = _data.merginglaw;
			
			_data.buyer = _data.merged;
			_data.buyfinance = _data.mergedfinance;
			_data.buyaudit = _data.mergedaudit;
			_data.buylaw = _data.mergedlaw;
			
			_data.spadt = _data.dirtdt
			_data.moudt = _data.stkhdt
			_data.closdt = _data.mnadt
		}
		
		$("#parent_seller").empty().append(drawCompanyHtml("seller", _data.seller).join(""));
		
		$("#parent_sell_finance").empty().append(drawConsCompanyHtml("sell", "finance", _data.sellfinance).join(""));
		
		$("#parent_sell_audit").empty().append(drawConsCompanyHtml("sell", "audit", _data.sellaudit).join(""));
		
		$("#parent_sell_law").empty().append(drawConsCompanyHtml("sell", "law", _data.selllaw).join(""));
		
		$("#parent_buyer").empty().append(drawCompanyHtml("buyer", _data.buyer).join(""));
		
		$("#parent_buy_finance").empty().append(drawConsCompanyHtml("buy", "finance", _data.buyfinance).join(""));
		
		$("#parent_buy_audit").empty().append(drawConsCompanyHtml("buy", "audit", _data.buyaudit).join(""));
		
		$("#parent_buy_law").empty().append(drawConsCompanyHtml("buy", "law", _data.buylaw).join(""));
		
		$("#cal1").val(_data.consdt);
		$("#cal2").val(_data.spadt);
		$("#cal3").val(_data.moudt);
		$("#cal4").val(_data.closdt);
	};

	function createCalendar() {
		cal1 = new dhtmlXCalendarObject({input: "cal1", button: "btn_cal1"});
		cal2 = new dhtmlXCalendarObject({input: "cal2", button: "btn_cal2"});
		cal3 = new dhtmlXCalendarObject({input: "cal3", button: "btn_cal3"});
		cal4 = new dhtmlXCalendarObject({input: "cal4", button: "btn_cal4"});
	};
	
	function destroyCalendar() {
		if(cal1 != "") {
			cal1.unload();
			cal1 = "";
		}
		if(cal2 != "") {
			cal2.unload();
			cal2 = "";
		}
		if(cal3 != "") {
			cal3.unload();
			cal3 = "";
		}
		if(cal4 != "") {
			cal4.unload();
			cal4 = "";
		}
	};

	function readPageSetting(){
		$("#btn_detail_title").show();
		$("#btn_detail_chrgconf").show();
		$("#btn_detail_etconf").show();
		$("#btn_detail_modify").show();
		$("#btn_detail_save").hide();
		$("#btn_detail_reset").hide();
		$(".modify_btn").hide();
		$(".modify_inp").attr("readonly", true);
		$(".data_cell").removeClass("edit_mode rgt_btn");
	};

	function editPageSetting(){
		$("#btn_detail_title").hide();
		$("#btn_detail_chrgconf").hide();
		$("#btn_detail_etconf").hide();
		$("#btn_detail_modify").hide();
		$("#btn_detail_save").show();
		$("#btn_detail_reset").show();
		$(".modify_btn").show();
		$(".modify_inp").attr("readonly", false);
		$(".data_cell").addClass("edit_mode rgt_btn");
		editEventSetting();
	};
	
	function editEventSetting() {
		$("#consType").change(function(){
			var conscd = $(this).val();
			if(conscd == CONST.conscd.UNCLF || conscd == "") {
				alert("선택할 수 없는 항목입니다.");
				$("#consType").val(lv_data.list.conscd);
				return false;
			}
			
			dhtmlx.confirm({
				title:"Important!",
				type:"confirm-error",
				text:"입력한 데이터는 삭제되며 복원할 수 없습니다.",
				callback:function(result){
					if(result) {
						param = {};
						param.prjtcd = lv_data.prjtcd;
						param.condition = CONST.CONDITION.MODIFY;
						param.befconscd = lv_data.list.conscd;
						param.altconscd = conscd;
						if(param.altconscd == CONST.conscd.BUY) {
							param.type = CONST.TYPE.BUY;
						} else if(param.altconscd == CONST.conscd.MNA) {
							param.type = CONST.TYPE.MNA;
						} else if(param.altconscd == CONST.conscd.REAL) {
							param.type = CONST.TYPE.REAL;
						}
						
						var url = "LtConsultTypeChange";
						Ajax.post(url, param, function(serverData){
							if(serverData.result) {
								Ajax.jsp("client/ltDetailLeagueTable", param, function(html) {
									$("#dc_con_list_detail").empty().append(html);
									$("#dc_con_list").hide();
									$(".menu_list_li").removeClass("on");
									$(".menu_list_li").each(function() {
										var _url = $(this).data("menuurl");
										if(_url.indexOf(param.type) != -1) {
											$(this).addClass("on");
										}
									});
									modifycheck = true;
								});
							} else {
								alert(serverData.error.errorMessage);
								return false;
							}
						});
					} else {
						$("#consType").val(lv_data.list.conscd);
						return false;
					}
				}
			});
		});
		
		$(".btn_add_target").off("click").on("click", function() {
			var _code = $(this).data("code");
			if($("." + _code).length < CONST.COUNT){
				if(lv_data.type == CONST.TYPE.REAL) {
					$(".rowspan_" + _code).attr("rowspan", ($("." + _code).length+1));
					$("#tbody_" + _code).append(drawPlusRealTargetHtml("").join(""));
				} else {
					$("#tbody_" + _code).append(drawPlusTargetHtml("").join(""));
				}
			}
			$(".data_cell").addClass("edit_mode rgt_btn");
			$(".btn_delete_sel").off("click").on("click", inputDataDelete);
		});
		
		$(".btn_add_company").off("click").on("click", function() {
			var _code = $(this).data("code");
			if($("." + _code).length < CONST.COUNT){
				$(".rowspan_" + _code).attr("rowspan", ($("." + _code).length+1)*2);
				$("#tbody_" + _code).append(drawPlusCompanyHtml(_code).join(""));
			}
			$(".data_cell").addClass("edit_mode rgt_btn");
			$(".btn_delete_sel").off("click").on("click", inputDataDelete);
		});
		
		$(".btn_add_conscom").off("click").on("click", function() {
			var _code = $(this).data("code");
			var _split = _code.split("_");
			if($("." + _code).length < CONST.COUNT){
				$(".rowspan_" + _code).attr("rowspan", $("." + _code).length+1);
				$("#tbody_" + _code).append(drawPlusConsCompanyHtml(_split[0], _split[1]).join(""));
			}
			$(".data_cell").addClass("edit_mode rgt_btn");
			$(".btn_delete_sel").off("click").on("click", inputDataDelete);
		});

		$(".btn_remove_table").off("click").on("click", function() {
			var _code = $(this).data("code");
			var _target = $("."+_code);
			
			if(_target.length > 1) {
				var row = _target.data("row");
				if(!(lv_data.type == CONST.TYPE.BUY && _code == "target")) {
					$(".rowspan_" + _code).attr("rowspan", ($("." + _code).length-1)*row);
				}
				
				for(var i=0; i<row; i++) {
					var _rmtarget = $("#tbody_"+_code + " tr").last().find("input.essenValue");
					if(i+1 == row){
						var seq = _rmtarget.data("seq");
						if(seq != "" && seq != null ) {
							var data = {};
							data.seq = seq;
							data.prjtcd = lv_data.prjtcd;
							data.actcd = _rmtarget.data("actcd");
							data.bizcd = _rmtarget.data("bizcd");
							lv_data.remove.push(data);
						}
					}
					$("#tbody_"+_code + " tr").last().remove();
				}
			}
		});
		
		$(".btn_delete_sel").off("click").on("click", inputDataDelete);
		
		$(".btn_delete_date_sel").off("click").on("click", function() {
			$(this).parent().find("input").val("");
		});
		
		$("#rate, #amt").off("keyup").on("keyup", function() {
			$('#rate').val(GF.numberWithCommas( GF.numberCutCommas($('#rate').val()) ));
			$('#amt').val(GF.numberWithCommas( GF.numberCutCommas($('#amt').val()) ));
			$('#rateamt').val(GF.numberWithCommas( GF.numberCutCommas($('#rate').val()) * GF.numberCutCommas($('#amt').val()) ));
		});
		
		function inputDataDelete() {
			var _s = $(this).parent().find("input");
			var seq = _s.data("seq");
			if(seq != "" && seq != null) {
				var data = {};
				data.seq = seq;
				data.prjtcd = lv_data.prjtcd;
				data.actcd = _s.data("actcd");
				data.bizcd = _s.data("bizcd");
				lv_data.remove.push(data);
			}
			_s.val("");
			_s.data("code", "");
			_s.data("seq", "");
			_s.data("actcd", "");
			_s.data("bizcd", "");
			if(_s.attr('class').indexOf('consCompanyKorNm') == -1) {
				_s.closest("tr").next().find("input.companyEngNm").val("");
			}
		};
	};
	
	// Bind Event
	function bindEvent(){
		// 목록 버튼
		$("#btn_detail_title").off("click").on("click", function(){
			if(modifycheck) {
				var url = "client/lt" + lv_data.type + "LeagueTable"
				Ajax.jsp(url, {}, function doLoadedContent(html) {
					$(".dc_con_article").empty().html(html);
				});
			} else {
				$("#dc_con_list").show();
				$("#dc_con_list_detail").empty();
			}
		});
		
		// 본부 담당자 확인 버튼
		$("#btn_detail_chrgconf").off("click").on("click", function(){
			var typecd = "";
			if(lv_data.list.chrgconfcd == CONST.chrgconfcd.YES) {
				if(!confirm("해제됩니다.")) {
					return false;
				}
				typecd = CONST.chrgconfcd.NO;
			} else if(lv_data.list.chrgconfcd == CONST.chrgconfcd.NO) {
				alert("확인됩니다.");
				typecd = CONST.chrgconfcd.YES;
			}
			var prjtcd = lv_data.prjtcd;
			var items = [];
			items.push(prjtcd+"^"+typecd);

			// 서버 반영
			Ajax.post("LtConsultConfirm", {items:items.join("|")}, function(serverData){
				if (serverData.result) {
					alert("수정하신 내용이 저장되었습니다.");
					modifycheck = true;
					doInit();
				} else {
					alert(serverData.error.errorMessage);
					return false;
				}
			});
		});
		
		// Engagement Team 확인 버튼
		$("#btn_detail_etconf").off("click").on("click", function(){
			if (!confirm("내용을 확인하셨습니까?")) {
				return false;
			}
			var prjtcd = lv_data.prjtcd;
			var items = [];
			items.push(prjtcd);

			// 서버 반영
			Ajax.post("LtConsultETConfirm", {items:items.join("|")}, function(serverData){
				if (serverData.result) {
					alert("저장되었습니다.");
					modifycheck = true;
					doInit();
				} else {
					alert(serverData.error.errorMessage);
					return false;
				}
			});
		});
		
		// 수정 버튼
		$("#btn_detail_modify").off("click").on("click", function(){
			
			var editAuth = false;
			
			for(var i=0; i<USERROLE.length; i++) {
				if($.inArray(USERROLE[i].rolecd, CONST.EDITROLE) >= 0) {
					editAuth = true;
					break;
				}
			}
			
			if(!editAuth){
				if(!editAuth && (lv_data.list.statuscd == CONST.statuscd.COMPLETE || lv_data.list.statuscd == CONST.statuscd.DROP)) {
					alert("Status가 \"성사\", \"Drop\" 인 경우 \"수정\"이 필요시 리그테이블 관리자에게 문의하시기 바랍니다.");
					return false;
				}
				
				if (!lv_data.auth.btnEdit) {
					alert("지금은 \"수정\" 기간이 아닙니다.");
					return false;
				}	
			}
			lv_data.condition = CONST.CONDITION.MODIFY;
			drawDetailLeagueTable();
			editPageSetting();
			
			
		});
		
		// 저장 버튼
		$("#btn_detail_save").off("click").on("click", function(){
			$(".invalid").removeClass("invalid");
			var valid = validationCheck();
			
			if(valid.isValid){
				var param = getParam();
				// 저장
				Ajax.post("LtConsult" + lv_data.type + "Save", param, function(serverData) {
					if(serverData.result) {
						lv_data.condition = CONST.CONDITION.READ;
						modifycheck = true;
						doInit();
					}
				});
				
			}else{
				alert(valid.msg.join("\n"));
				valid.focus.focus();
			}
		}); 
		
		
		// 취소 버튼
		$("#btn_detail_reset").off("click").on("click", function(){
			lv_data.condition = CONST.CONDITION.READ;
			drawDetailLeagueTable();
			readPageSetting();
		});
		
		// 기업 선택
		$(document).off('click', '.btn_company_sel').on('click', '.btn_company_sel', function() {
			var _s = $(this);
			_s.parent().find("input").removeAttr( 'style' );
			POPUP.ViewPopup("companySearch", null, function(selectedCompanyInfo) {
				if(selectedCompanyInfo.hangnm != "" && selectedCompanyInfo.hangnm != null) {
					_s.parent().find("input").val(selectedCompanyInfo.hangnm);
					_s.parent().find("input").data("code", selectedCompanyInfo.upchecd);
					_s.closest("tr").next().find("input.companyEngNm").val(selectedCompanyInfo.englnm);
					if(selectedCompanyInfo.inducd != "") {
						_s.closest("tr").next().find("input.industryNm").val(selectedCompanyInfo.glindunmk + "/" + selectedCompanyInfo.indunm);
						_s.closest("tr").next().find("input.industryNm").data("code", selectedCompanyInfo.inducd);
					} else {
						_s.closest("tr").next().find("input.industryNm").val("");
						_s.closest("tr").next().find("input.industryNm").data("code", "");
					}
				}
			});
		});
		
		// 자문사 선택 버튼
		$(document).off('click', '.btn_conscompany_sel').on('click', '.btn_conscompany_sel', function() {
			var _s = $(this);
			_s.parent().find("input").removeAttr( 'style' );
			
			var param = {};
			param.type = "";
			
			if(_s.closest("tr").attr('class').indexOf("finance") != -1) {
				param.type = "finance";
			} else if(_s.closest("tr").attr('class').indexOf("audit") != -1) {
				param.type = "audit";
			} else if(_s.closest("tr").attr('class').indexOf("law") != -1) {
				param.type = "law";
			}
			
			POPUP.ViewPopup("consCompanySearch", param, function(selectedConsCompanyInfo) {
				if(selectedConsCompanyInfo.hangnm != "" && selectedConsCompanyInfo.hangnm != null) {
					_s.parent().find("input").val(selectedConsCompanyInfo.hangnm);
					_s.parent().find("input").data("code", selectedConsCompanyInfo.compcd);
					
					var $charger = _s.closest("tr").find(".charger");
					console.log($charger);
					if(selectedConsCompanyInfo.compcd == "1"){
						$charger.attr("readonly", true);
						$charger.next().show();
						$charger.next().next().show();
					}else{
						$charger.attr("readonly", false);
						$charger.next().hide();
						$charger.next().next().hide();
					}
				}
			});
		});
		
		// Industry 선택 버튼
		$(document).off('click', '.btn_industry_sel').on('click', '.btn_industry_sel', function() {
			var _s = $(this);
			POPUP.ViewPopup("industrySearch", null, function(selectedIndustryInfo) {
				_s.parent().find("input").val(selectedIndustryInfo.indugnm + "/" + selectedIndustryInfo.indunm);
				_s.parent().find("input").data("code", selectedIndustryInfo.inducd);
			});
		});
		
		// 국적 선택 버튼
		$(document).off('click', '.btn_nation_sel').on('click', '.btn_nation_sel', function() {
			var _s = $(this);
			POPUP.ViewPopup("nationSearch", null, function(selectedNationInfo) {
				if(selectedNationInfo.etcdnm != "" && selectedNationInfo.etcdnm != null) {
					_s.parent().find("input").val(selectedNationInfo.etcdnm);
					_s.parent().find("input").data("code", selectedNationInfo.etcocd);
				}
			});
		});
	};

	function getParam(){
		var param = {};
		
		param.prjtcd 	= lv_data.prjtcd;
		param.statuscd 	= $("#statusType").val();
		param.confcd 	= $("#confType").val();
		param.comnm 	= $("#comment").val();
		
		// Target
		if(lv_data.type == CONST.TYPE.BUY) {
			param.buyoutcd 	= $("#buyoutType").val();
			param.trgtetc 	= $("#targetEtc").val();
			param.target 	= [];
			allTypeData("target", param.target);
		} else if(lv_data.type == CONST.TYPE.REAL) {
			param.target = [];
			$('.target').each(function(idx){
				var _d 		= {};
				_d.dealnm 	= $(this).find('.companyKorNm').val();
				_d.natcd 	= $(this).find('.countryNm').data("code");
				_d.natnm 	= $(this).find('.countryNm').val();
				_d.seq 		= $(this).find('.companyKorNm').data("seq");
				param.target.push(_d);
			});
		}

		// 자문사
		if(lv_data.type == CONST.TYPE.BUY || lv_data.type == CONST.TYPE.REAL) {
			// 인수/매각자문, 부동산 자문
			param.seller = [];		allTypeData("seller", param.seller);
			param.sellfinance = [];	partTypeData("sell_finance", param.sellfinance);
			param.sellaudit = [];	partTypeData("sell_audit", param.sellaudit);
			param.selllaw =[]; 		partTypeData("sell_law", param.selllaw);
			param.buyer = [];		allTypeData("buyer", param.buyer);
			param.buyfinance = [];	partTypeData("buy_finance", param.buyfinance);
			param.buyaudit = [];	partTypeData("buy_audit", param.buyaudit);
			param.buylaw = [];		partTypeData("buy_law", param.buylaw);
			param.consdt = $('#cal1').val();
			param.spadt  = $('#cal2').val();
			param.moudt  = $('#cal3').val();
			param.closdt = $('#cal4').val();
			
		} else if(lv_data.type == CONST.TYPE.MNA) {
			// 합병/분할 자문
			param.merging = [];			allTypeData("seller", param.merging);
			param.mergingfinance = [];	partTypeData("sell_finance", param.mergingfinance);
			param.mergingaudit = [];	partTypeData("sell_audit", param.mergingaudit);
			param.merginglaw =[];		partTypeData("sell_law", param.merginglaw);
			param.merged = [];			allTypeData("buyer", param.merged);
			param.mergedfinance = [];	partTypeData("buy_finance", param.mergedfinance);
			param.mergedaudit = [];		partTypeData("buy_audit", param.mergedaudit);
			param.mergedlaw = [];		partTypeData("buy_law", param.mergedlaw);
			param.consdt = $('#cal1').val();
			param.dirtdt = $('#cal2').val();
			param.stkhdt = $('#cal3').val();
			param.mnadt  = $('#cal4').val();
		}
		
		param.currcd 	 = $('#rateType').val();
		param.rate 	 	 = GF.numberCutCommas( $('#rate').val() );
		param.amt 		 = GF.numberCutCommas( $('#amt').val() );
		param.rateamt 	 = GF.numberCutCommas( $('#rateamt').val() );
		param.borddealcd = $('#borddealType').val();
		param.borddealnm = (param.borddealcd == "") ? "" : $('#borddealType option:selected').text();
		
		// 거래형태
		if(lv_data.type != CONST.TYPE.REAL) {
			var focus = "";
			
			param.dealcd2 = $('#deal2Type').val();
			param.dealnm2 = (param.dealcd2 == "") ? "" : $('#deal2Type option:selected').text();
			param.dealcd3 = $('#deal3Type').val();
			param.dealnm3 = (param.dealcd3 == "") ? "" : $('#deal3Type option:selected').text();
			
			if(lv_data.type == CONST.TYPE.BUY) {
				param.deal1 = [];
				$("input[name='deal1Type']:checked").each(function() {
					param.deal1.push($(this).val());
				});
				/*param.dealcd4 = $('#deal4Type').val();
				param.dealnm4 = (param.dealcd4 == "") ? "" : $('#deal4Type option:selected').text();*/
			} else if(lv_data.type == CONST.TYPE.MNA) {
				param.dealcd1 = $('#deal1Type').val();
				param.dealnm1 = (param.dealcd1 == "") ? "" : $('#deal1Type option:selected').text();
			}
		}
		
		// 삭제 데이터
		param.remove = lv_data.remove;
		
		
		// 기업정보
		function allTypeData(kind, param) {
			$('.'+kind).each(function(idx){
				var _that = $(this);
				var _d = {};
				_d.compcd 		= _that.find('.companyKorNm').data("code");
				_d.comphannm 	= _that.find('.companyKorNm').val();
				_d.natcd 		= _that.find('.countryNm').data("code");
				_d.natnm 		= _that.find('.countryNm').val();
				_d.compengnm 	= _that.next().find('.companyEngNm').val();
				_d.inducd 		= _that.next().find('.industryNm').data("code");
				_d.indunm 		= _that.next().find('.industryNm').val();
				_d.seq 			= _that.find('.companyKorNm').data("seq");
				
				if(_that.hasClass('target')) {
					_d.dealnm = _that.next().next().find('.dealNm').val();
				}
				
				param.push(_d);
			})
		}
		
		// 자문사 
		function partTypeData(kind, param) {
			$('.'+kind).each(function(idx){
				var _that = $(this);
				var _d = {};
				_d.compcd 		= _that.find('.consCompanyKorNm').data("code");
				_d.comphannm 	= _that.find('.consCompanyKorNm').val();
				_d.chrgempnm1 	= _that.find('.charger').val();
				_d.chrgempno1 	= _that.find('.charger').data("code") == "" || $.trim(_that.find('.charger').val()) == "" ? "-" : _that.find('.charger').data("code");
				_d.seq 			= _that.find('.consCompanyKorNm').data("seq");
				
				param.push(_d);
			});
		}
		
		return param;
	}

	function validationCheck(){
		var valid = {isValid:true, msg:[]}
		
		// Status가 성사일 경우 필수 값 체크
		/*if($("#statusType").val() == CONST.statuscd.COMPLETE){
			if(lv_data.type == CONST.TYPE.BUY){
				if($("#buyoutType").val() == ""){addInvalid($("#buyoutType"), "Status가 \"성사\"일 경우, 반드시 [Buyout/Non-Buyout]이 입력되어야 합니다.");}
				$(".target").each(function(idx, obj){
					var $obj = $(obj)
					if($.trim($obj.find(".companyKorNm ").val()) != "" && $.trim($obj.next().next().find(".dealNm ").val()) == ""){
						addInvalid($obj.next().next().find(".dealNm "), "Status가 \"성사\"일 경우, 반드시 [거래물]이 입력되어야 합니다.");
					}
				})
				if($('#cal2').val() == ""){addInvalid($("#cal2"), "Status가 \"성사\"일 경우, 반드시 [매매계약(SPA)]이 입력되어야 합니다.");}
				if($('#cal4').val() == ""){addInvalid($("#cal4"), "Status가 \"성사\"일 경우, 반드시 [잔금납입일(Closing)]이 입력되어야 합니다.");}
				if($("input[name='deal1Type']:checked").length == 0){addInvalid($("#deal1 .inp_check_list").attr("tabindex", -1), "Status가 \"성사\"일 경우, 반드시 [거래형태1]이 입력되어야 합니다.");}
				if($('#deal2Type').val()==""){addInvalid($("#deal2 .inp_change"), "Status가 \"성사\"일 경우, 반드시 [거래형태2]가 입력되어야 합니다.");}
				if($('#deal3Type').val()==""){addInvalid($("#deal3 .inp_change"), "Status가 \"성사\"일 경우, 반드시 [거래형태3]이 입력되어야 합니다.");}
			}else if(lv_data.type == CONST.TYPE.MNA){
				if($('#cal4').val() == ""){addInvalid($("#cal4"), "Status가 \"성사\"일 경우, 반드시 [합병기일]이 입력되어야 합니다.");}
				if($('#deal1Type').val()==""){addInvalid($("#deal1 .inp_change"), "Status가 \"성사\"일 경우, 반드시 [거래형태1]이 입력되어야 합니다.");}
				if($('#deal2Type').val()==""){addInvalid($("#deal2 .inp_change"), "Status가 \"성사\"일 경우, 반드시 [거래형태2]가 입력되어야 합니다.");}
				if($('#deal3Type').val()==""){addInvalid($("#deal3 .inp_change"), "Status가 \"성사\"일 경우, 반드시 [거래형태3]이 입력되어야 합니다.");}
			}else if(lv_data.type == CONST.TYPE.REAL){
				if($('#cal4').val() == ""){addInvalid($("#cal4"), "Status가 \"성사\"일 경우, 반드시 [잔금납입일(Closing)]이 입력되어야 합니다.");}
			}
		}*/
		
		if($("#statusType").val() == CONST.statuscd.COMPLETE){
			if(lv_data.type == CONST.TYPE.BUY || lv_data.type == CONST.TYPE.REAL){
				if($('#cal4').val() == ""){addInvalid($("#cal4"), "Status가 \"성사\"일 경우, 반드시 [잔금납입일(Closing)]이 입력되어야 합니다.");}
			}else if(lv_data.type == CONST.TYPE.MNA){
				if($('#cal4').val() == ""){addInvalid($("#cal4"), "Status가 \"성사\"일 경우, 반드시 [합병기일]이 입력되어야 합니다.");}
			}
		}
		if(lv_data.type == CONST.TYPE.BUY){
			if($("#buyoutType").val() == ""){addInvalid($("#buyoutType"), "[Buyout/Non-Buyout]을 선택하세요.");}
		}
		// 내부 Comment 길이 체크
		validLengthCheck($("#comment"), $("#comment").val(), 400, "내부 Comment");
		// Target, Seller, Buyer 
		if(lv_data.type == CONST.TYPE.BUY){
			validCompany("target", "대상(Target) > 기업명");
			$(".target").each(function(idx, obj){
				var $obj = $(obj)
				if($.trim($obj.find(".companyKorNm ").val()) != ""){
					if($.trim($obj.next().next().find(".dealNm ").val()) == ""){
						addInvalid($obj.next().next().find(".dealNm "), "[거래물]을 입력하세요.");	
					}else{
						validLengthCheck($obj.next().next().find('.dealNm'), $obj.next().next().find('.dealNm').val(), 650, "대상(Target) > 거래물");		
					}
				}
			})
			validLengthCheck($("#targetEtc"), $("#targetEtc").val(), 200, "대상(Target) > 비고(Target 기타)");
			validCompany("seller", "매각자(Seller) > 기업명");
			validConsCompany("sell_finance", "매각자(Seller) > 재무/전략");
			validConsCompany("sell_audit", "매각자(Seller) > 회계");
			validConsCompany("sell_law", "매각자(Seller) > 법률");
			validCompany("buyer", "인수자(Buyer) > 기업명");
			validConsCompany("buy_finance", "인수자(Buyer) > 재무/전략");
			validConsCompany("buy_audit", "인수자(Buyer) > 회계");
			validConsCompany("buy_law", "인수자(Buyer) > 법률");
		}else if(lv_data.type == CONST.TYPE.MNA){
			validCompany("seller", "합병(존속/신설)법인 > 기업명");
			validConsCompany("sell_finance", "합병(존속/신설)법인 > 재무/전략");
			validConsCompany("sell_audit", "합병(존속/신설)법인 > 회계");
			validConsCompany("sell_law", "합병(존속/신설)법인 > 법률");
			validCompany("buyer", "피합병(소별)법인 > 기업명");
			validConsCompany("buy_finance", "피합병(소별)법인 > 재무/전략");
			validConsCompany("buy_audit", "피합병(소별)법인 > 회계");
			validConsCompany("buy_law", "피합병(소별)법인 > 법률");
		}else if(lv_data.type == CONST.TYPE.REAL){
			// Target
			$('.target').each(function(idx, obj){
				var $obj = $(obj);
				if($.trim($obj.find('.essenValue').val()) != "" && $obj.find('.essenValue').val() != null) {
					validLengthCheck($obj.find('.companyKorNm'), $obj.find('.companyKorNm').val(), 650, "대상(Target) > 매각대상");
					validLengthCheck($obj.find('.countryNm'), $obj.find('.countryNm').val(), 250, "대상(Target) > 국적");
				}else{
					if($obj.find('.countryNm').data("code") != ""){
						addInvalid($obj.find('.essenValue'), "[대상(Target) > 매각대상]을 입력하세요.")
					}
				}
			})
			
			validCompany("seller", "매각자(Seller) > 기업명");
			validConsCompany("sell_finance", "매각자(Seller) > 재무/전략");
			validConsCompany("sell_audit", "매각자(Seller) > 회계");
			validConsCompany("sell_law", "매각자(Seller) > 법률");
			validCompany("buyer", "인수자(Buyer) > 기업명");
			validConsCompany("buy_finance", "인수자(Buyer) > 재무/전략");
			validConsCompany("buy_audit", "인수자(Buyer) > 회계");
			validConsCompany("buy_law", "인수자(Buyer) > 재무/전략");
		}
		validLengthCheck($('#rate'), GF.numberCutCommas( $('#rate').val()), 14, "환율(1통화당 원화)");
		validLengthCheck($('#amt'), GF.numberCutCommas( $('#amt').val()), 14, "금액");
		// 거래형태
		if(lv_data.type == CONST.TYPE.BUY){
			if($("input[name='deal1Type']:checked").length == 0){addInvalid($("#deal1 .inp_check_list").attr("tabindex", -1), "[거래형태1]을 선택하세요.");}
			if($('#deal2Type').val()==""){addInvalid($("#deal2 .inp_change"), "[거래형태2]을 선택하세요.");}
			if($('#deal3Type').val()==""){addInvalid($("#deal3 .inp_change"), "[거래형태3]을 선택하세요.");}
		}else if(lv_data.type == CONST.TYPE.MNA){
			if($('#deal1Type').val()==""){addInvalid($("#deal1 .inp_change"), "[거래형태1]을 선택하세요.");}
			if($('#deal2Type').val()==""){addInvalid($("#deal2 .inp_change"), "[거래형태2]을 선택하세요.");}
			if($('#deal3Type').val()==""){addInvalid($("#deal3 .inp_change"), "[거래형태3]을 선택하세요.");}
		}
		
		function addInvalid($dom, msg){
			valid.isValid = false;	// 유효값 여부
			if($.inArray(msg, valid.msg) === -1) valid.msg.push(msg);	// 유효하지 않을 경우, 메시지 추가 (중복 메시지 제거)
			if(!valid.focus) valid.focus = $dom;	// focus를 이동시기기 위한 것
			$dom.addClass("invalid");
		}
		
		// 값의 최대 길이 유효성 검사 함수
		function validLengthCheck($dom, data, length, tagStr) {
			var strLength = GF.byteLength(data);
			if(strLength > length){
				addInvalid($dom,  (tagStr?"["+tagStr+"] ":"")+ "최대길이를 초과했습니다. (현재길이 : " + strLength + ", 최대길이 : " + length + ")");
			}
		}
		
		// 기업 유효성 검사 함수
		function validCompany(kind, dspName){
			$('.'+kind).each(function(idx, obj){
				var $obj = $(obj);
				if($obj.find('.essenValue').val() != "" && $obj.find('.essenValue').val() != null) {
					validLengthCheck($obj.find('.companyKorNm'), $obj.find('.companyKorNm').val(), 650, dspName + " 한글명");
					validLengthCheck($obj.find('.countryNm'), $obj.find('.countryNm').val(), 250, dspName + "의 국적");
					validLengthCheck($obj.next().find('.companyEngNm'), $obj.next().find('.companyEngNm').val(), 250, dspName + " 영문명");
				}else{
					if($obj.hasClass("target")){
						if($obj.find('.countryNm').val() != "" || $obj.next().find('.industryNm').val() != "" || $obj.next().next().find('.dealNm').val() != ""){
							addInvalid($obj.find('.essenValue'), "[" + dspName + "] " + " 기업을 선택하세요.")
						}
					}else{
						if($obj.find('.countryNm').val() != "" || $obj.next().find('.industryNm').val() != "" ){
							addInvalid($obj.find('.essenValue'), "[" + dspName + "] " + " 기업을 선택하세요.")
						}
					}
				}
			})
		}
		
		// 자문사 유효성 검사 함수
		function validConsCompany(kind, dspName){
			$('.'+kind).each(function(idx, obj){
				var $obj = $(obj);
				if($obj.find('.essenValue').val() != "" && $obj.find('.essenValue').val() != null) {
					validLengthCheck($obj.find('.consCompanyKorNm'), $obj.find('.consCompanyKorNm').val(), 650, dspName + " 자문사");
					validLengthCheck($obj.find('.charger'), $obj.find('.charger').val(), 30, dspName + " 담당자");
				} else {
					if($.trim($obj.find('.charger').val()) != "") {
						addInvalid($obj.find('.essenValue'), "[" + dspName + "] " + " 자문사를 선택하세요.");
					}
				}
			});
		}
		
		return valid;
	}
	
	function drawSubstructureHtml() {
		var _h = [];
		_h.push('<div class="dc_con_section">');
		_h.push('<h3 class="dc_con_title">기본정보<div class="dc_con_title_date">최종수정일 : <span id="upddt"></span></div></h3>');
		_h.push('<div id="parent_basic"></div>');
		_h.push('</div>');
		if(lv_data.type != CONST.TYPE.MNA) {
			_h.push('<div class="dc_con_section">');
			_h.push('<h3 class="dc_con_title">대상(<span class="font_eng">Target</span>)</h3>');
			/*_h.push('<div class="tb_btn_rgt_area modify_btn">');
			_h.push('<dl>');
			_h.push('<dt class="font_eng">Target</dt>');
			_h.push('<dd>');
			_h.push('<button type="button" class="btn btn_red ico_plus2 btn_add_target" data-code="target" title="추가">추가</button>');
			_h.push('<button type="button" class="btn btn_red ico_minus2 btn_remove_table" data-code="target" title="삭제">삭제</button>');
			_h.push('</dd>');
			_h.push('</dl>');
			_h.push('</div>')*/;
		}
		_h.push('<div id="parent_target"></div>');
		_h.push('</div>');
		if(lv_data.type == CONST.TYPE.BUY) {
			_h.push('<div class="dc_con_section">');
			_h.push('<div id="parent_target_etc"></div>');
			_h.push('</div>');
		}
		/*_h.push('<div class="dc_con_section">');
		if(lv_data.type == CONST.TYPE.MNA) {
			_h.push('<h3 class="dc_con_title">합병(존속/신설)법인</h3>');
			_h.push('<div class="tb_btn_rgt_area modify_btn"><dl>');
			_h.push('<dt class="font_eng">Merging</dt>');
		} else {
			_h.push('<h3 class="dc_con_title">매각자(<span class="font_eng">Seller)</h3>');
			_h.push('<div class="tb_btn_rgt_area modify_btn"><dl>');
			_h.push('<dt class="font_eng">Seller</dt>');
		}
		_h.push('<dd>');
		_h.push('<button type="button" class="btn btn_red ico_plus2 btn_add_company" data-code="seller">추가</button>');
		_h.push('<button type="button" class="btn btn_red ico_minus2 btn_remove_table" data-code="seller">삭제</button>');
		_h.push('</dd>');
		_h.push('</dl>');
		_h.push('<dl>');
		_h.push('<dt>재무/전략 자문사</dt>');
		_h.push('<dd>');
		_h.push('<button type="button" class="btn btn_red ico_plus2 btn_add_conscom" data-code="seller_finance" title="추가">추가</button>');
		_h.push('<button type="button" class="btn btn_red ico_minus2 btn_remove_table" data-code="seller_finance" title="삭제">삭제</button>');
		_h.push('</dd>');
		_h.push('</dl>');
		_h.push('<dl>');
		_h.push('<dt>회계 자문사</dt>');
		_h.push('<dd>');
		_h.push('<button type="button" class="btn btn_red ico_plus2 btn_add_conscom" data-code="seller_audit" title="추가">추가</button>');
		_h.push('<button type="button" class="btn btn_red ico_minus2 btn_remove_table" data-code="seller_audit" title="삭제">삭제</button>');
		_h.push('</dd>');
		_h.push('</dl>');
		_h.push('</div>');*/
		//------------
		_h.push('<div class="dc_con_section">');
		if(lv_data.type == CONST.TYPE.MNA) {
			_h.push('<h3 class="dc_con_title">합병(존속/신설)법인</h3>');
		} else {
			_h.push('<h3 class="dc_con_title">매각자(<span class="font_eng">Seller)</h3>');
		}
		//------------
		_h.push('<div id="parent_seller"></div>');
		_h.push('</div>');
		_h.push('<div class="dc_con_section">');
		_h.push('<div id="parent_sell_finance"></div>');
		_h.push('</div>');
		_h.push('<div class="dc_con_section">');
		_h.push('<div id="parent_sell_audit"></div>');
		_h.push('</div>');
		_h.push('<div class="dc_con_section">');
		_h.push('<div id="parent_sell_law"></div>');
		_h.push('</div>');
		/*_h.push('<div class="dc_con_section">');
		if(lv_data.type == CONST.TYPE.MNA) {
			_h.push('<h3 class="dc_con_title">피합병(소멸)법인</h3>');
			_h.push('<div class="tb_btn_rgt_area modify_btn"><dl>');
			_h.push('<dt class="font_eng">Merged</dt>');
		} else {
			_h.push('<h3 class="dc_con_title">인수자(<span class="font_eng">Buyer)</h3>');
			_h.push('<div class="tb_btn_rgt_area modify_btn"><dl>');
			_h.push('<dt class="font_eng">Buyer</dt>');
		}
		_h.push('<dd>');
		_h.push('<button type="button" class="btn btn_red ico_plus2 btn_add_company" data-code="buyer">추가</button>');
		_h.push('<button type="button" class="btn btn_red ico_minus2 btn_remove_table" data-code="buyer">삭제</button>');
		_h.push('</dd>');
		_h.push('</dl>');
		_h.push('<dl>');
		_h.push('<dt>재무/전략 자문사</dt>');
		_h.push('<dd>');
		_h.push('<button type="button" class="btn btn_red ico_plus2 btn_add_conscom" data-code="buyer_finance" title="추가">추가</button>');
		_h.push('<button type="button" class="btn btn_red ico_minus2 btn_remove_table" data-code="buyer_finance" title="삭제">삭제</button>');
		_h.push('</dd>');
		_h.push('</dl>');
		_h.push('<dl>');
		_h.push('<dt>회계 자문사</dt>');
		_h.push('<dd>');
		_h.push('<button type="button" class="btn btn_red ico_plus2 btn_add_conscom" data-code="buyer_audit" title="추가">추가</button>');
		_h.push('<button type="button" class="btn btn_red ico_minus2 btn_remove_table" data-code="buyer_audit" title="삭제">삭제</button>');
		_h.push('</dd>');
		_h.push('</dl>');
		_h.push('</div>');*/
		
		//------
		_h.push('<div class="dc_con_section">');
		if(lv_data.type == CONST.TYPE.MNA) {
			_h.push('<h3 class="dc_con_title">피합병(소멸)법인</h3>');
		} else {
			_h.push('<h3 class="dc_con_title">인수자(<span class="font_eng">Buyer)</h3>');
		}
		//------
		
		_h.push('<div id="parent_buyer"></div>');
		_h.push('</div>');
		_h.push('<div class="dc_con_section">');
		_h.push('<div id="parent_buy_finance"></div>');
		_h.push('</div>');
		_h.push('<div class="dc_con_section">');
		_h.push('<div id="parent_buy_audit"></div>');
		_h.push('</div>');
		_h.push('<div class="dc_con_section">');
		_h.push('<div id="parent_buy_law"></div>');
		_h.push('</div>');
		_h.push('<div class="dc_con_section">');
		_h.push('<h3 class="dc_con_title">일자(<span class="font_eng">Date</span>)</h3>');
		_h.push('<div id="parent_date"></div>');
		_h.push('</div>');
		_h.push('<div class="dc_con_section">');
		_h.push('<h3 class="dc_con_title">금액 및 성격</h3>');
		_h.push('<div id="parent_curr"></div>');
		_h.push('</div>');
		return _h;
	};
	
	function drawBasicHtml() {
		var _h = [];
		_h.push('<table class="dc_view_table">');
		_h.push('<colgroup>');
		_h.push('<col width="12%">');
		_h.push('<col width="12%">');
		_h.push('<col width="26%">');
		_h.push('<col width="24%">');
		_h.push('<col width="26%">');
		_h.push('</colgroup>');
		_h.push('<tbody>');
		_h.push('<tr>');
		_h.push('<th colspan="2">자문형태</th>');
		_h.push('<td id="conscd"><input type="text" readonly="readonly" class="inp_change" id="consType"></td>');
		_h.push('<th>Status</th>');
		_h.push('<td id="statuscd"><input type="text" readonly="readonly" class="inp_change" id="statusType"></td>');
		_h.push('</tr>');
		_h.push('<tr>');
		_h.push('<th rowspan="2">Project</th>');
		_h.push('<th>Code/Client/용역명</th>');
		_h.push('<td><input type="text" readonly="readonly" class="inp_change" id="prjtcd"></td>');
		_h.push('<th>Confidential 여부</th>');
		_h.push('<td id="confcd"><input type="text" readonly="readonly" class="inp_change" id="confType"></td>');
		_h.push('</tr>');
		_h.push('<tr>');
		_h.push('<th>PTR/MGR</th>');
		_h.push('<td><input type="text" readonly="readonly" class="inp_change" id="ptremp"></td>');
		_h.push('<th>본부 담당자 확인 여부</th>');
		_h.push('<td><input type="text" readonly="readonly" class="inp_change" id="chrgconfcd"></td>');
		_h.push('</tr>');
		_h.push('<tr>');
		_h.push('<th rowspan="2">Product</th>');
		_h.push('<th>Category</th>');
		_h.push('<td><input type="text" readonly="readonly" class="inp_change" id="ctgcd"></td>');
		_h.push('<th>담당본부</th>');
		_h.push('<td><input type="text" readonly="readonly" class="inp_change" id="hqcd"></td>');
		_h.push('</tr>');
		_h.push('<tr>');
		_h.push('<th>Code/명칭</th>');
		_h.push('<td><input type="text" readonly="readonly" class="inp_change" id="pdtcd"></td>');
		if(lv_data.type == CONST.TYPE.BUY) {
			_h.push('<th>Buyout / Non-Buyout</th>');
			_h.push('<td id="buyoutcd"><input type="text" class="inp_change modify_inp" id="buyoutType"></td>');
		} else {
			_h.push('<td colspan="2"></td>');
		}
		_h.push('</tr>');
		_h.push('<tr>');
		_h.push('<th colspan="2">내부 Comment</th>');
		_h.push('<td colspan="3">');
		_h.push('<input type="text" class="inp_change modify_inp" id="comment" maxlength="400">');
		/*_h.push('<textarea class="inp_change modify_inp" id="comment" value="수기 입력"></textarea>');*/
		_h.push('</td>');
		_h.push('</tr>');
		_h.push('</tbody>');
		_h.push('</table>');
		_h.push('</div>');
		return _h;
	};
	
	function drawTargetHtml(data) {
		var _h = [];
		_h.push('<table class="dc_view_table">');
		_h.push('<colgroup>');
		_h.push('<col width="12%">');
		_h.push('<col width="12%">');
		_h.push('<col width="26%">');
		_h.push('<col width="12%">');
		_h.push('<col width="12%">');
		_h.push('<col width="26%">');
		_h.push('</colgroup>');
		_h.push('<tbody id="tbody_target">');
		if(data.length == 0) {
			_h.push(drawPlusTargetHtml("", 0).join(""));
		} else {
			for(var i=0; i<data.length; i++) {
				_h.push(drawPlusTargetHtml(data[i], i).join(""));
			}
		}
		_h.push('</tbody>');
		_h.push('</table>');
		return _h;
	};
	
	function drawPlusTargetHtml(data, idx) {
		var _h = [];
		_h.push('<tr class="target" data-row="3">');
		
		
		//---------
		/*_h.push('<th rowspan="2">기업명</th>');*/
		_h.push('<th rowspan="2">기업명');
		if(idx==0){
			_h.push('<div class="tb_btn_rgt_area modify_btn"><dl>');
			_h.push('<dd>');
			_h.push('<button type="button" class="btn btn_red ico_plus2 btn_add_target" data-code="target" title="추가">추가</button>');
			_h.push('<button type="button" class="btn btn_red ico_minus2 btn_remove_table" data-code="target" title="삭제">삭제</button>');
			_h.push('</dd></dl></div>');	
		}
		_h.push('</th>');
		//---------
		_h.push('<th>한글</th>');
		_h.push('<td>');
		_h.push('<div class="data_cell">');
		_h.push('<input type="text" maxlength="650" readonly="readonly" class="essenValue companyKorNm inp_change" value="' + (data.comphannm ? data.comphannm : "") + '" data-code="' + (data.compcd ? data.compcd : "") + '" data-seq="' + (data.seq ? data.seq : "") + '" data-actcd="' + (data.actcd ? data.actcd : "") + '" data-bizcd="' + (data.bizcd ? data.bizcd : "") + '" />');
		_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
		_h.push('<button type="button" class="btn_company_sel btn btn_red modify_btn">선택</button>');
		_h.push('</div>');
		_h.push('</td>');
		_h.push('<th rowspan="2">기업정보</th>');
		_h.push('<th>국적</th>');
		_h.push('<td>');
		_h.push('<div class="data_cell">');
		_h.push('<input type="text" maxlength="250" readonly="readonly" class="countryNm inp_change" value="' + (data.natnm ? data.natnm : "") + '" data-code="' + (data.natcd ? data.natcd : "") + '" />');
		_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
		_h.push('<button type="button" class="btn_nation_sel btn btn_red modify_btn">선택</button>');
		_h.push('</div>');
		_h.push('</td>');
		_h.push('</tr>');
		_h.push('<tr>');
		_h.push('<th>영문</th>');
		_h.push('<td><input type="text" maxlength="250" readonly="readonly" class="companyEngNm inp_change" value="' + (data.compengnm ? data.compengnm : "") + '" /></td>');
		_h.push('<th>Industry</th>');
		_h.push('<td>');
		_h.push('<div class="data_cell">');
		_h.push('<input type="text" readonly="readonly" class="industryNm inp_change" value="' + (data.indunm ? data.indunm : "") + '" data-code="' + (data.inducd ? data.inducd : "") + '" />');
		_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
		_h.push('<button type="button" class="btn_industry_sel btn btn_red modify_btn">선택</button>');
		_h.push('</div>');
		_h.push('</td>');
		_h.push('</tr>');
		_h.push('<tr>');
		_h.push('<th colspan="2">거래물</th>');
		_h.push('<td colspan="4"><input type="text" maxlength="650" class="inp_change dealNm modify_inp" value="' + (data.dealnm ? data.dealnm : "") + '" /></td>');
		_h.push('</tr>');
		return _h;
	}
	
	function drawTargetEtcHtml(data) {
		var _h = [];
		_h.push('<table class="dc_view_table">');
		_h.push('<colgroup>');
		_h.push('<col width="24%">');
		_h.push('<col width="76%">');
		_h.push('</colgroup>');
		_h.push('<tbody>');
		_h.push('<tr>');
		_h.push('<th>비고 (Target 기타)</th>');
		_h.push('<td>');
		_h.push('<input type="text" class="inp_change modify_inp" id="targetEtc" maxlength="200" value="' + data + '">');
		_h.push('</td>');
		_h.push('</tr>');
		_h.push('</tbody>');
		_h.push('</table>');
		return _h;
	};
	
	function drawRealTargetHtml(data) {
		var _h = [];
		_h.push('<table class="dc_view_table">');
		_h.push('<colgroup>');
		_h.push('<col width="24%">');
		_h.push('<col width="26%">');
		_h.push('<col width="24%">');
		_h.push('<col width="26%">');
		_h.push('</colgroup>');
		_h.push('<tbody id="tbody_target">');
		
		var i=0;
		do {
			if(data.length == 0) {
				data[0] = "";
			}
			_h.push('<tr class="target" data-row="1">');
			if(i == 0){
				_h.push('<th rowspan="' + data.length + '" class="rowspan_target">매각대상</th>');
			}
			_h.push('<td>');
			_h.push('<input type="text" maxlength="650" class="essenValue companyKorNm inp_change modify_inp" value="' + (data[i].dealnm ? data[i].dealnm : "") + '" data-code="' + (data[i].compcd ? data[i].compcd : "") + '" data-seq="' + (data[i].seq ? data[i].seq : "") + '" data-actcd="' + (data[i].actcd ? data[i].actcd : "") + '" data-bizcd="' + (data[i].bizcd ? data[i].bizcd : "") + '" />');
			_h.push('</td>');
			if(i == 0){
				_h.push('<th rowspan="' + data.length + '" class="rowspan_target">국적</th>');
			}
			_h.push('<td>');
			_h.push('<div class="data_cell">');
			_h.push('<input type="text" maxlength="250" readonly="readonly" class="countryNm inp_change" value="' + (data[i].natnm ? data[i].natnm : "") + '" data-code="' + (data[i].natcd ? data[i].natcd : "") + '" />');
			_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
			_h.push('<button type="button" class="btn_nation_sel btn btn_red modify_btn">선택</button>');
			_h.push('</div>');
			_h.push('</td>');
			_h.push('</tr>');
			
			_h.push('<tr class="" data-row="1">');
			if(i == 0){
				_h.push('<th rowspan="' + data.length + '" class="rowspan_target">대분류</th>');
			}
			_h.push('<td><input type="text" readonly="readonly" class="inp_change" value="부동산"/></td>');
			_h.push('<td colspan="2"></td>');
			_h.push('</tr>');
			
			i++;
		} while(i<data.length);
		_h.push('</tbody>');
		_h.push('</table>');
		return _h;
	};
	
	function drawPlusRealTargetHtml() {
		var _h = [];
		_h.push('<tr class="target">');
		_h.push('<td>');
		_h.push('<input type="text" maxlength="650" class="essenValue companyKorNm inp_change modify_inp" value="" data-code="" data-seq="" />');
		_h.push('</td>');
		_h.push('<td>');
		_h.push('<div class="data_cell">');
		_h.push('<input type="text" maxlength="250" readonly="readonly" class="countryNm inp_change" value="" data-code="" />');
		_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
		_h.push('<button type="button" class="btn_nation_sel btn btn_red modify_btn">선택</button>');
		_h.push('</div>');
		_h.push('</td>');
		_h.push('</tr>');
		return _h;
	}
	
	function drawCompanyHtml(classid, data) {
		var _h = [];
		_h.push('<table class="dc_view_table">');
		_h.push('<colgroup>');
		_h.push('<col width="12%">');
		_h.push('<col width="12%">');
		_h.push('<col width="26%">');
		_h.push('<col width="12%">');
		_h.push('<col width="12%">');
		_h.push('<col width="26%">');
		_h.push('</colgroup>');
		_h.push('<tbody id="tbody_' + classid + '">');

		var i=0;
		do {
			if(data.length == 0) {
				data[0] = "";
			}
			_h.push('<tr class="' + classid + '" data-row="2">');
			if(i == 0){
				/*_h.push('<th rowspan="' + data.length*2 + '" class="rowspan_' + classid + '">기업명</th>');*/
				//-----------
				_h.push('<th rowspan="' + data.length*2 + '" class="rowspan_' + classid + '">기업명');
				_h.push('<div class="tb_btn_rgt_area modify_btn"><dl>');
				_h.push('<dd>');
				_h.push('<button type="button" class="btn btn_red ico_plus2 btn_add_company" data-code="' + classid + '">추가</button>');
				_h.push('<button type="button" class="btn btn_red ico_minus2 btn_remove_table" data-code="' + classid + '">삭제</button>');
				_h.push('</dd>');
				_h.push('</dl>');
				_h.push('</th>');
				//-----------
				
			}
			_h.push('<th>한글</th>');
			_h.push('<td>');
			_h.push('<div class="data_cell">');
			_h.push('<input type="text" maxlength="650" readonly="readonly" class="essenValue companyKorNm inp_change" value="' + (data[i].comphannm ? data[i].comphannm : "") + '" data-code="' + (data[i].compcd ? data[i].compcd : "") + '" data-seq="' + (data[i].seq ? data[i].seq : "") + '" data-actcd="' + (data[i].actcd ? data[i].actcd : "") + '" data-bizcd="' + (data[i].bizcd ? data[i].bizcd : "") + '" />');
			_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
			_h.push('<button type="button" class="btn_company_sel btn btn_red modify_btn">선택</button>');
			_h.push('</div>');
			_h.push('</td>');
			if(i == 0){
				_h.push('<th rowspan="' + data.length*2 + '" class="rowspan_' + classid + '">기업정보</th>');
			}
			_h.push('<th>국적</th>');
			_h.push('<td>');
			_h.push('<div class="data_cell">');
			_h.push('<input type="text" maxlength="250" readonly="readonly" class="countryNm inp_change" value="' + (data[i].natnm ? data[i].natnm : "") + '" data-code="' + (data[i].natcd ? data[i].natcd : "") + '" />');
			_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
			_h.push('<button type="button" class="btn_nation_sel btn btn_red modify_btn">선택</button>');
			_h.push('</div>');
			_h.push('</td>');
			_h.push('</tr>');
			_h.push('<tr>');
			_h.push('<th>영문</th>');
			_h.push('<td><input type="text" maxlength="250" readonly="readonly" class="companyEngNm inp_change" value="' + (data[i].compengnm ? data[i].compengnm : "") + '" /></td>');
			_h.push('<th>Industry</th>');
			_h.push('<td>');
			_h.push('<div class="data_cell">');
			_h.push('<input type="text" readonly="readonly" class="industryNm inp_change" value="' + (data[i].indunm ? data[i].indunm : "") + '" data-code="' + (data[i].inducd ? data[i].inducd : "") + '" />');
			_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
			_h.push('<button type="button" class="btn_industry_sel btn btn_red modify_btn">선택</button>');
			_h.push('</div>');
			_h.push('</td>');
			_h.push('</tr>');
			i++;
		} while(i<data.length);
		_h.push('</tbody>');
		_h.push('</table>');
		return _h;
	};
	
	function drawPlusCompanyHtml(classid) {
		var _h = [];
		_h.push('<tr class="' + classid + '">');
		_h.push('<th>한글</th>');
		_h.push('<td>');
		_h.push('<div class="data_cell">');
		_h.push('<input type="text" maxlength="650" readonly="readonly" class="essenValue companyKorNm inp_change" value="" data-code="" data-seq="" />');
		_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
		_h.push('<button type="button" class="btn_company_sel btn btn_red modify_btn">선택</button>');
		_h.push('</div>');
		_h.push('</td>');
		_h.push('<th>국적</th>');
		_h.push('<td>');
		_h.push('<div class="data_cell">');
		_h.push('<input type="text" maxlength="250" readonly="readonly" class="countryNm inp_change" value="" data-code="" />');
		_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
		_h.push('<button type="button" class="btn_nation_sel btn btn_red modify_btn">선택</button>');
		_h.push('</div>');
		_h.push('</td>');
		_h.push('</tr>');
		_h.push('<tr>');
		_h.push('<th>영문</th>');
		_h.push('<td><input type="text" maxlength="250" readonly="readonly" class="companyEngNm inp_change" value="" /></td>');
		_h.push('<th>Industry</th>');
		_h.push('<td>');
		_h.push('<div class="data_cell">');
		_h.push('<input type="text" readonly="readonly" class="industryNm inp_change" value="" data-code="" />');
		_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
		_h.push('<button type="button" class="btn_industry_sel btn btn_red modify_btn">선택</button>');
		_h.push('</div>');
		_h.push('</td>');
		_h.push('</tr>');
		return _h;
	}
	
	function drawConsCompanyHtml(classid, kind, data) {
		var name = "";
		var _h = [];
		_h.push('<table class="dc_view_table">');
		_h.push('<colgroup>');
		_h.push('<col width="12%">');
		_h.push('<col width="12%">');
		_h.push('<col width="26%">');
		_h.push('<col width="24%">');
		_h.push('<col width="26%">');
		_h.push('</colgroup>');
		_h.push('<tbody id="tbody_' + classid + '_' + kind + '">');
		
		if(kind == "finance") {
			name = "재무/전략";
		} else if(kind == "audit") {
			name = "회계";
		} else if(kind == "law") {
			name = "법률";
		}
		var i=0;
		do {
			if(data.length == 0) {
				data[0] = "";
			}
			_h.push('<tr class="' + classid + '_' + kind + '" data-row="1">');
			if(i == 0){
				/*_h.push('<th rowspan="' + data.length + '" class="rowspan_' + classid + '_' + kind + '">' + name + '</th>');*/
				//-----------
				_h.push('<th rowspan="' + data.length + '" class="rowspan_' + classid + '_' + kind + '">' + name);
				if(kind != "law") {
					_h.push('<div class="tb_btn_rgt_area modify_btn"><dl>');
					_h.push('<dd>');
					_h.push('<button type="button" class="btn btn_red ico_plus2 btn_add_conscom" data-code="' + classid + "_" +  kind + '">추가</button>');
					_h.push('<button type="button" class="btn btn_red ico_minus2 btn_remove_table" data-code="' + classid + "_" +  kind + '">삭제</button>');
					_h.push('</dd>');
					_h.push('</dl>');
					_h.push('</th>');
				}
				//-----------
			}
			_h.push('<th>자문사</th>');
			_h.push('<td>');
			_h.push('<div class="data_cell">');
			_h.push('<input type="text" maxlength="650" readonly="readonly" class="essenValue consCompanyKorNm inp_change" value="' + (data[i].comphannm ? data[i].comphannm : "") + '" data-code="' + (data[i].compcd ? data[i].compcd : "") + '" data-seq="' + (data[i].seq ? data[i].seq : "") + '" data-actcd="' + (data[i].actcd ? data[i].actcd : "") + '" data-bizcd="' + (data[i].bizcd ? data[i].bizcd : "") + '"/>');
			_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
			_h.push('<button type="button" class="btn_conscompany_sel btn btn_red modify_btn">선택</button>');
			_h.push('</div>');
			_h.push('</td>');
			/*if(i == 0){
				_h.push('<th rowspan="' + data.length + '" class="rowspan_' + classid + '_' + kind + '">담당자</th>');
			}*/
			_h.push('<th>담당자</th>');
			_h.push('<td><input type="text" maxlength="30" class="inp_change charger modify_inp" value="' + (data[i].chrgempnm1 ? data[i].chrgempnm1 : "") + '" data-code="' + (data[i].chrgempno1 ? data[i].chrgempno1 : "") + '" /></td>');
			_h.push('</tr>');
			i++;
		} while(i<data.length);
		
		_h.push('</tbody>');
		_h.push('</table>');
		return _h;
	};
	
	function drawPlusConsCompanyHtml(classid, kind) {
		var _h = [];
		_h.push('<tr class="' + classid + '_' + kind + '">');
		_h.push('<th>자문사</th>');
		_h.push('<td>');
		_h.push('<div class="data_cell">');
		_h.push('<input type="text" maxlength="650" readonly="readonly" class="essenValue consCompanyKorNm inp_change" value="" data-code="" data-seq="" />');
		_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
		_h.push('<button type="button" class="btn_conscompany_sel btn btn_red modify_btn">선택</button>');
		_h.push('</div>');
		_h.push('</td>');
		_h.push('<th>담당자</th>');
		
		_h.push('<td><div class="data_cell edit_mode rgt_btn edit_mode_txt">');
		_h.push('<input type="text" maxlength="30" class="inp_change charger modify_inp" value="" data-code="" />');
		_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
		_h.push('<button type="button" class="btn_charge_sel btn btn_red modify_btn">선택</button>');
		_h.push('</div></td>');
		_h.push('</tr>');
		return _h;
	}
	
	/*
	function drawPlusConsCompanyHtml(classid, kind) {
		var _h = [];
		_h.push('<tr class="' + classid + '_' + kind + '">');
		_h.push('<th>자문사</th>');
		_h.push('<td>');
		_h.push('<div class="data_cell">');
		_h.push('<input type="text" maxlength="650" readonly="readonly" class="essenValue consCompanyKorNm inp_change" value="" data-code="" data-seq="" />');
		_h.push('<button type="button" class="btn ico_delete modify_btn btn_delete_sel" title="삭제">삭제</button>');
		_h.push('<button type="button" class="btn_conscompany_sel btn btn_red modify_btn">선택</button>');
		_h.push('</div>');
		_h.push('</td>');
		_h.push('<th>담당자</th>');
		_h.push('<td><input type="text" maxlength="30" class="inp_change charger modify_inp" value="" data-code="" /></td>');
		_h.push('</tr>');
		return _h;
	}
	*/
	function drawDateHtml(data) {
		var _h = [];
		_h.push('<table class="dc_view_table">');
		_h.push('<colgroup>');
		_h.push('<col width="24%">');
		_h.push('<col width="26%">');
		_h.push('<col width="24%">');
		_h.push('<col width="26%">');
		_h.push('</colgroup>');
		_h.push('<tbody>');
		_h.push('<tr>');
		_h.push('<th>자문계약</th>');
		_h.push('<td>');
		_h.push('<div class="data_cell dc_frm_period">');
		_h.push('<input type="text" id="cal1" class="inp_change" readonly="readonly">');
		_h.push('<button type="button" class="btn ico_delete_date modify_btn btn_delete_date_sel" title="삭제">삭제</button>');
		_h.push('<button type="button" class="btn btn_cal modify_btn" title="날짜선택" id="btn_cal1">날짜선택</button>');
		_h.push('</div>');
		_h.push('</td>');
		if(lv_data.type == CONST.TYPE.MNA) {
			_h.push('<th>이사회 의결일</th>');
		} else {
			_h.push('<th>매매계약(<span class="font_eng">SPA</span>)</th>');
		}
		_h.push('<td>');
		_h.push('<div class="data_cell dc_frm_period">');
		_h.push('<input type="text" id="cal2" class="inp_change" readonly="readonly">');
		_h.push('<button type="button" class="btn ico_delete_date modify_btn btn_delete_date_sel" title="삭제">삭제</button>');
		_h.push('<button type="button" class="btn btn_cal modify_btn" title="날짜선택" id="btn_cal2">날짜선택</button>');
		_h.push('</div>');
		_h.push('</td>');
		_h.push('</tr>');
		_h.push('<tr>');
		if(lv_data.type == CONST.TYPE.MNA) {
			_h.push('<th>주주총회 의결일</th>');
		} else {
			_h.push('<th>MOU 체결</th>');
		}
		_h.push('<td>');
		_h.push('<div class="data_cell dc_frm_period">');
		_h.push('<input type="text" id="cal3" class="inp_change" readonly="readonly">');
		_h.push('<button type="button" class="btn ico_delete_date modify_btn btn_delete_date_sel" title="삭제">삭제</button>');
		_h.push('<button type="button" class="btn btn_cal modify_btn" title="날짜선택" id="btn_cal3">날짜선택</button>');
		_h.push('</div>');
		_h.push('</td>');
		if(lv_data.type == CONST.TYPE.MNA) {
			_h.push('<th>합병기일</th>');
		} else {
			_h.push('<th>잔금납입일(<span class="font_eng">Closing</span>)</th>');
		}
		_h.push('<td>');
		_h.push('<div class="data_cell dc_frm_period">');
		_h.push('<input type="text" id="cal4" class="inp_change" readonly="readonly">');
		_h.push('<button type="button" class="btn ico_delete_date modify_btn btn_delete_date_sel" title="삭제">삭제</button>');
		_h.push('<button type="button" class="btn btn_cal modify_btn" title="날짜선택" id="btn_cal4">날짜선택</button>');
		_h.push('</div>');
		_h.push('</td>');
		_h.push('</tr>');
		_h.push('</tbody>');
		_h.push('</table>');
		_h.push('</div>');
		return _h;
	};

	function drawCurrHtml(data) {
		var _h = [];
		_h.push('<table class="dc_view_table">');
		_h.push('<colgroup>');
		_h.push('<col width="24%">');
		_h.push('<col width="26%">');
		_h.push('<col width="24%">');
		_h.push('<col width="26%">');
		_h.push('</colgroup>');
		_h.push('<tbody>');
		_h.push('<tr>');
		_h.push('<th>통화</th>');
		_h.push('<td id="ratecd"><input type="text" id="rateType" class="inp_change modify_inp" /></td>');
		_h.push('<th>국경간거래</th>');
		_h.push('<td id="borddeal"><input type="text" id="borddealType" class="inp_change modify_inp" /></td>');
		_h.push('</tr>');
		_h.push('<tr>');
		_h.push('<th>환율(1통화당 원화)</th>');
		_h.push('<td><input type="text" id="rate" class="inp_change modify_inp" style="text-align:right;" /></td>');
		if(lv_data.type == CONST.TYPE.REAL) {
			_h.push('<td colspan="2"></td>');
		} else {
			_h.push('<th>거래형태 1</th>');
			_h.push('<td id="deal1"><input type="text" id="deal1Type" class="inp_change modify_inp" /></td>');
		}
		_h.push('</tr>');
		_h.push('<tr>');
		_h.push('<th>금액</th>');
		_h.push('<td><input type="text" id="amt" class="inp_change modify_inp" style="text-align:right;" /></td>');
		if(lv_data.type == CONST.TYPE.REAL) {
			_h.push('<td colspan="2"></td>');
		} else {
			_h.push('<th>거래형태 2</th>');
			_h.push('<td id="deal2"><input type="text" id="deal2Type" class="inp_change modify_inp" /></td>');
		}
		_h.push('</tr>');
		_h.push('<tr>');
		_h.push('<th>원화 환산금액</th>');
		_h.push('<td><input type="text" readonly="readonly" id="rateamt" class="inp_change" style="text-align:right;" /></td>');
		if(lv_data.type == CONST.TYPE.REAL) {
			_h.push('<td colspan="2"></td>');
		} else {
			_h.push('<th>거래형태 3</th>');
			_h.push('<td id="deal3"><input type="text" id="deal3Type" class="inp_change modify_inp" /></td>');
		}
		_h.push('</tr>');
		/*_h.push('<tr>');
		_h.push('<td colspan="2"></td>');
		if(lv_data.type == CONST.TYPE.BUY) {
			_h.push('<th>거래형태 4</th>');
			_h.push('<td id="deal4"><input type="text" id="deal4Type" class="inp_change modify_inp" /></td>');
		} else {
			_h.push('<td colspan="2"></td>');
		}
		_h.push('</tr>');*/
		_h.push('</tbody>');
		_h.push('</table>');
		_h.push('</div>');
		return _h;
	};
	
	return {
		init:init
	};
}());