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
			//console.log(_s);
			//console.log(seq);
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
			var valid = true;
			$(".invalid").removeClass("invalid");
			
			var param = {};
			param.prjtcd 	= lv_data.prjtcd;
			param.statuscd 	= $("#statusType").val();
			if(param.statuscd == CONST.statuscd.COMPLETE) {
				if($('#cal4').val() == "") {
					var str = "";
					if(lv_data.type == CONST.TYPE.MNA) {
						str = "Status를 \"성사\"로 변경할 경우는 반드시 합병기일이 입력되어야 합니다."
					} else {
						str = "Status를 \"성사\"로 변경할 경우는 반드시 잔금납입(Closing)이 입력되어야 합니다."
					}
					alert(str);
					$('#cal4').focus();
					return false;
				}
			}
			param.confcd = $("#confType").val();
			param.comnm = $("#comment").val();
			validLengthCheck(param.comnm, 400);
			if(!valid) {
				$("#comment").focus();
				return false;
			}
			
			if(lv_data.type == CONST.TYPE.BUY) {
				param.buyoutcd = $("#buyoutType").val();
				param.trgtetc = $("#targetEtc").val();
				validLengthCheck(param.trgtetc, 200);
				if(!valid) {
					$("#targetEtc").focus();
					return false;
				}
				
				param.target = [];
				allTypeData("target", param.target);
				if(!valid) return false;
			} else if(lv_data.type == CONST.TYPE.REAL) {
				param.target = [];
				$('.target').each(function(idx){
					if($(this).find('.essenValue').val() != "" && $(this).find('.essenValue').val() != null) {
						var _d = {};
						_d.dealnm = $(this).find('.companyKorNm').val();
						validLengthCheck(_d.dealnm, 650);
						if(!valid) {
							$(this).find('.companyKorNm').focus();
							return false;
						}
						_d.natcd = $(this).find('.countryNm').data("code");
						_d.natnm = $(this).find('.countryNm').val();
						validLengthCheck(_d.dealnm, 250);
						if(!valid) {
							$(this).find('.countryNm').focus();
							return false;
						}
						_d.seq = $(this).find('.companyKorNm').data("seq");
						param.target.push(_d);
					} else {
						if($(this).find('.countryNm').data("code") != "") {
							alert("매각대상을 선택하세요");
							$(this).find('.essenValue').focus();
							valid = false;
							return false;
						}
					}
				});
			}
			if(!valid) return false;
			
			if(lv_data.type == CONST.TYPE.BUY || lv_data.type == CONST.TYPE.REAL) {
				param.seller = [];
				allTypeData("seller", param.seller);
				if(!valid) return false;
				
				param.sellfinance = [];
				partTypeData("sell_finance", param.sellfinance);
				if(!valid) return false;
				
				param.sellaudit = [];
				partTypeData("sell_audit", param.sellaudit);
				if(!valid) return false;
				
				param.selllaw =[];
				partTypeData("sell_law", param.selllaw);
				if(!valid) return false;
				
				param.buyer = [];
				allTypeData("buyer", param.buyer);
				if(!valid) return false;
				
				param.buyfinance = [];
				partTypeData("buy_finance", param.buyfinance);
				if(!valid) return false;
				
				param.buyaudit = [];
				partTypeData("buy_audit", param.buyaudit);
				if(!valid) return false;
				
				param.buylaw = [];
				partTypeData("buy_law", param.buylaw);
				if(!valid) return false;
				
				param.consdt = $('#cal1').val();
				param.spadt = $('#cal2').val();
				param.moudt = $('#cal3').val();
				param.closdt = $('#cal4').val();
				
			} else if(lv_data.type == CONST.TYPE.MNA) {
				param.merging = [];
				allTypeData("seller", param.merging);
				if(!valid) return false;
				
				param.mergingfinance = [];
				partTypeData("sell_finance", param.mergingfinance);
				if(!valid) return false;
				
				param.mergingaudit = [];
				partTypeData("sell_audit", param.mergingaudit);
				if(!valid) return false;
				
				param.merginglaw =[];
				partTypeData("sell_law", param.merginglaw);
				if(!valid) return false;
				
				param.merged = [];
				allTypeData("buyer", param.merged);
				if(!valid) return false;
				
				param.mergedfinance = [];
				partTypeData("buy_finance", param.mergedfinance);
				if(!valid) return false;
				
				param.mergedaudit = [];
				partTypeData("buy_audit", param.mergedaudit);
				if(!valid) return false;
				
				param.mergedlaw = [];
				partTypeData("buy_law", param.mergedlaw);
				if(!valid) return false;
				
				param.consdt = $('#cal1').val();
				param.dirtdt = $('#cal2').val();
				param.stkhdt = $('#cal3').val();
				param.mnadt = $('#cal4').val();
			}
			
			param.currcd = $('#rateType').val();
			param.rate = GF.numberCutCommas( $('#rate').val() );
			var rate = param.rate.split(".");
			if(rate[0].length > 14) {
				alert("최대길이를 초과했습니다.");
				$('#rate').focus();
				valid = false;
			}
			if(!valid) return false;
			param.amt = GF.numberCutCommas( $('#amt').val() );
			var amt = param.amt.split(".");
			if(amt[0].length > 14) {
				alert("최대길이를 초과했습니다.");
				$('#amt').focus();
				valid = false;
			}
			if(!valid) return false;
			param.rateamt = GF.numberCutCommas( $('#rateamt').val() );
			
			param.borddealcd = $('#borddealType').val();
			param.borddealnm = (param.borddealcd == "") ? "" : $('#borddealType option:selected').text();
			
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
					
					
					
					
					// 매매계약(SPA) 날짜가 있을 경우, 거래형태 1,2,3,4는 반드시 선택되어야한다.
					if($.trim($("#cal2").val()) != ""){
						
						if(param.deal1.length == 0) {
							$("#deal1 .inp_check_list").addClass("invalid");
							focus = "#deal1 .inp_check_list"
							valid = false;
						}
						if(param.dealcd2 == ""){
							$("#deal2 .inp_change").addClass("invalid").focus();
							focus = "#deal2 .inp_change"
							valid = false;
						}
						if(param.dealcd3 == ""){
							$("#deal3 .inp_change").addClass("invalid").focus();
							focus = "#deal3 .inp_change"
							valid = false;
						}
						/*if(param.dealcd4 == ""){
							$("#deal4 .inp_change").addClass("invalid").focus();
							focus = "#deal4 .inp_change"
							valid = false;
						}*/
						if(!valid){
							alert("매매계약(SPA)을 입력한 경우, 거래형태 1,2,3는 반드시 입력해야 합니다.")
							$(focus).focus();
							return false;
						}
					} 
						
				} else if(lv_data.type == CONST.TYPE.MNA) {
					param.dealcd1 = $('#deal1Type').val();
					param.dealnm1 = (param.dealcd1 == "") ? "" : $('#deal1Type option:selected').text();
					
					// 합병기일에 날짜가 있을 경우, 거래형태 1,2,3은 반드시 선택되어야한다.
					if($.trim($("#cal4").val()) != ""){
						if(param.dealcd1 == "") {
							$("#deal1 .inp_change").addClass("invalid").focus();
							focus = "#deal1 .inp_change"
							valid = false;
						}
						if(param.dealcd2 == ""){
							$("#deal2 .inp_change").addClass("invalid").focus();
							focus = "#deal2 .inp_change"
							valid = false;
						}
						if(param.dealcd3 == ""){
							$("#deal3 .inp_change").addClass("invalid").focus();
							focus = "#deal3 .inp_change"
							valid = false;
						}
						
						if(!valid){
							alert("합병기일을 입력한 경우, 거래형태 1,2,3은 반드시 입력해야 합니다.")
							return false;
						}
					} 
				}
			}
			
			param.remove = lv_data.remove;
			
			
			Ajax.post("LtConsult" + lv_data.type + "Save", param, function(serverData) {
				if(serverData.result) {
					lv_data.condition = CONST.CONDITION.READ;
					modifycheck = true;
					doInit();
				}
			});
			
			function allTypeData(kind, param) {
				$('.'+kind).each(function(idx){
					var _that = $(this);
					if(_that.find('.essenValue').val() != "" && _that.find('.essenValue').val() != null) {
						var _d = {};
						_d.compcd = _that.find('.companyKorNm').data("code");
						_d.comphannm = _that.find('.companyKorNm').val();
						validLengthCheck(_d.comphannm, 650);
						if(!valid) {
							_that.find('.companyKorNm').focus();
							return false;
						}
						_d.natcd = _that.find('.countryNm').data("code");
						_d.natnm = _that.find('.countryNm').val();
						validLengthCheck(_d.natnm, 250);
						if(!valid) {
							_that.find('.countryNm').focus();
							return false;
						}
						_d.compengnm = _that.next().find('.companyEngNm').val();
						validLengthCheck(_d.compengnm, 250);
						if(!valid) {
							_that.next().find('.companyEngNm').focus();
							return false;
						}
						_d.inducd = _that.next().find('.industryNm').data("code");
						_d.indunm = _that.next().find('.industryNm').val();
						if(_that.attr('class').indexOf('target') != -1) {
							_d.dealnm = _that.next().next().find('.dealNm').val();
							validLengthCheck(_d.dealnm, 650);
							if(!valid) {
								_that.next().next().find('.dealNm').focus();
								return false;
							}
						}
						_d.seq = _that.find('.companyKorNm').data("seq");
						
						param.push(_d);
					} else {
						if(_that.attr('class').indexOf('target') != -1) {
							if(_that.find('.countryNm').val() != "" || _that.next().find('.industryNm').val() != "" || _that.next().next().find('.dealNm').val() != "") {
								_that.find('.essenValue').focus();
								_that.find('.essenValue').css("background-color","#faeef0");
								alert("기업을 선택하세요");
								valid = false;
							}
						} else {
							if(_that.find('.countryNm').val() != "" || _that.next().find('.industryNm').val() != "") {
								_that.find('.essenValue').focus();
								_that.find('.essenValue').css("background-color","#faeef0");
								alert("기업을 선택하세요");
								valid = false;
							}
						}
						return false;
					}
				});
			}
			
			function partTypeData(kind, param) {
				$('.'+kind).each(function(idx){
					var _that = $(this);
					if(_that.find('.essenValue').val() != "" && _that.find('.essenValue').val() != null) {
						var _d = {};
						_d.compcd = _that.find('.consCompanyKorNm').data("code");
						_d.comphannm = _that.find('.consCompanyKorNm').val();
						validLengthCheck(_d.comphannm, 650);
						if(!valid) {
							_that.find('.consCompanyKorNm').focus();
							return false;
						}
						_d.chrgempnm1 = _that.find('.charger').val();
						_d.chrgempno1 = _that.find('.charger').data("code") == "" ? "-" : _that.find('.charger').data("code");
						validLengthCheck(_d.chrgempnm1, 30);
						if(!valid) {
							_that.find('.charger').focus();
							return false;
						}
						_d.seq = _that.find('.consCompanyKorNm').data("seq");
						
						param.push(_d);
					} else {
						if(_that.find('.charger').val() != "") {
							_that.find('.essenValue').focus();
							_that.find('.essenValue').css("background-color","#faeef0");
							alert("자문사를 선택하세요");
							valid = false;
						}
						return false;
					}
				});
			}
			
			function validLengthCheck(data, length) {
				var strLength = 0;
				for(i = 0; i < data.length; i++) {
					var code = data.charCodeAt(i)
					var ch = data.substr(i,1).toUpperCase()
	
					code = parseInt(code)
	
					if ((ch < "0" || ch > "9") && (ch < "A" || ch > "Z") && ((code > 255) || (code < 0)))
						strLength = strLength + 2;
					else
						strLength = strLength + 1;
				}
				//console.log(strLength, length);
				if(strLength > length) {
					alert("최대길이를 초과했습니다. (현재길이 : " + strLength + ", 최대길이 : " + length + ")");
					valid = false;
				}
				/*if(GF.byteLength(data) > length) {
					alert("최대길이를 초과했습니다.");
					valid = false;
					return false;
				}*/
			}
		}); // #btn_detail_save 이벤트
		
		
		// 취소 버튼
		$("#btn_detail_reset").off("click").on("click", function(){
			lv_data.condition = CONST.CONDITION.READ;
			drawDetailLeagueTable();
			readPageSetting();
		});

		$(document).on('click', '.btn_company_sel', function() {
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
		
		// 기업 선택 버튼
		$(document).on('click', '.btn_conscompany_sel', function() {
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
				}
			});
		});
		
		// Industry 선택 버튼
		$(document).on('click', '.btn_industry_sel', function() {
			var _s = $(this);
			POPUP.ViewPopup("industrySearch", null, function(selectedIndustryInfo) {
				_s.parent().find("input").val(selectedIndustryInfo.indugnm + "/" + selectedIndustryInfo.indunm);
				_s.parent().find("input").data("code", selectedIndustryInfo.inducd);
			});
		});
		
		// 국적 선택 버튼
		$(document).on('click', '.btn_nation_sel', function() {
			var _s = $(this);
			POPUP.ViewPopup("nationSearch", null, function(selectedNationInfo) {
				if(selectedNationInfo.etcdnm != "" && selectedNationInfo.etcdnm != null) {
					_s.parent().find("input").val(selectedNationInfo.etcdnm);
					_s.parent().find("input").data("code", selectedNationInfo.etcocd);
				}
			});
		});
	};

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
		_h.push('<td><input type="text" maxlength="30" class="inp_change charger modify_inp" value="" data-code="" /></td>');
		_h.push('</tr>');
		return _h;
	}
	
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