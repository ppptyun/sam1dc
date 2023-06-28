const Helper = {
	getContextPath:function(){
		const hostIndex = location.href.indexOf( location.host ) + location.host.length;
		return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
	},
	goPage: function(url, param, _target){
		const urlRegExp = /^https?:\/\/[^\s]/gi;	// 외부 링크 여부
		let target = _target||'_self';
		
		
		if(urlRegExp.test(url) && target === "_blank"){
			window.open(url);
		}else if(urlRegExp.test(url) &&  target === "_self"){
			location.href = url;
		}else{
			// 내부 링크
			let $form = $("<form method='post' style='display:none'></form>");
			$form.attr("action", this.getContextPath() + url);
			
			for(let p in param){
				let $input = $("<input />");
				$input.attr("name", p);
				$input.val(param[p]);
				$input.attr("type", 'hidden');
				$form.append($input);
			}
			
			if($("#wrapper.navClose").length > 0){
				$form.append($("<input type='hidden' name='isNavClose' value='1' />"))
			}else{
				$form.append($("<input type='hidden' name='isNavClose' value='0' />"))
			}
			
			if($("li.snb01.snbON > a").length > 0){
				$form.append($("<input type='hidden' name='menuCd1' value='" + $("li.snb01.snbON > a").attr("id").replace("menu-", "") + "' />"));
			}
			
			$form.attr('target', target);
			$("body").append($form);
			$form.submit();	
		}
	},
	ajax: function(param){
		const thisObj = this;
		$.ajax({
			type: param.type||"post",
			contentType: 'application/json',
			url: thisObj.getContextPath() + "/json" + param.url,
			data: JSON.stringify(param.data),
			dataType: "json",
			success: function(data, textStatus, xhr){
				if(data.result){					
					if(data.result.status == "success"){
						if(data.result.msg) Helper.MessageBox(data.result.msg);
						if(param.success) param.success(data.result.data);	
					}else if(data.result.status == "fail"){
						if(data.result.msg) alert(data.result.msg);
						if(param.error) param.error();
					}else{
						console.log(data.result)
						alert("허용되지 않은 응답 Status를 리턴하였습니다.")
					}
				}else{
					console.log("응답이 없습니다.")
				}
				
			},
			error: function(xhr, textStatus, error){
				console.log(xhr)
				console.log("status", textStatus)
				console.log("error", error)
				alert("데이터 처리 도중 오류가 발생하였습니다.");
				COMMON.hideLoading();
			},
			complete: function(xhr, textStatus ){
				if(param.complete) param.complete(xhr, textStatus);
			}
		})
	},
	post:function(url, param, noHideLoading){
		let dfd = $.Deferred();
		const thisObj = this;
		thisObj.showLoading();
		$.ajax({
			type: "post",
			contentType: 'application/json',
			url: thisObj.getContextPath() + "/json" + url,
			data: JSON.stringify(param),
			dataType: "json",
			success: function(data, textStatus, xhr){
				if(data.result){
					if(data.result.msg) {
						thisObj.MessageBox([].concat(data.result.msg), function(){
							if(data.result.status == "success"){
								dfd.resolve(data.result.data);
							}else{
								dfd.reject(data.result.msg);
							}	
						})
					}else{						
						if(data.result.status == "success"){
							dfd.resolve(data.result.data);
						}else{
							dfd.reject("데이터 처리 도중 오류가 발생하였습니다.");
						}
					}
					
				}else{
					thisObj.MessageBox("응답 값이 없습니다.");
				}
			},
			error: function(xhr, textStatus, error){
				console.log(xhr)
				console.log("status", textStatus)
				console.log("error", error)
				thisObj.MessageBox("데이터 처리 도중 오류가 발생하였습니다.", function(){
					dfd.reject("");	
				});
			},
			complete: function(){
				if( noHideLoading=== undefined || noHideLoading === null ){
					thisObj.hideLoading();
				}else if(!noHideLoading){
					thisObj.hideLoading();
				}
			}
		});
		return dfd.promise();
	},
	postSync:function(url, param){
		const thisObj = this;
		thisObj.showLoading();
		
		let returnData = null;
		$.ajax({
			type: "post",
			contentType: 'application/json',
			url: thisObj.getContextPath() + "/json" + url,
			data: JSON.stringify(param),
			async: false,
			dataType: "json",
			success: function(data, textStatus, xhr){
				if(data.result){
					returnData = data.result;	
				}
			},
			error: function(xhr, textStatus, error){
				console.log(xhr)
				console.log("status", textStatus)
				console.log("error", error)
				returnData = {status: 'error'}
			},
			complete: function(){
				thisObj.hideLoading();
			}
		});
		return returnData;
	},
	uploadFile: function(url, formData){
		let dfd = $.Deferred();
		const thisObj = this;
		thisObj.showLoading();
		
		$.ajax({
			url: thisObj.getContextPath() + url,
			data: formData,
			type: 'post',
			processData: false, 
			contentType: false,
			dataType: "json",
			success:function(data){
				if(data.result){
					if(data.result.msg) {
						thisObj.MessageBox(data.result.msg, function(){
							if(data.result.status == "success"){
								dfd.resolve();
							}else{
								dfd.reject();
							}	
						})
					}else{						
						if(data.result.status == "success"){
							dfd.resolve();
						}else{
							dfd.reject();
						}
					}
				}
			},
			error: function(){
				console.log(xhr)
				console.log("status", textStatus)
				console.log("error", error)
				thisObj.MessageBox(["데이터 처리 도중 오류가 발생하였습니다."], function(){
					dfd.reject();	
				});	
			},
			complete: function(){
				thisObj.hideLoading();
			}
		})
		
		return dfd.promise();
	},
	downloadFile: function(url, formData){
		let dfd = $.Deferred();
		const thisObj = this;
		thisObj.showLoading();
		
		$.ajax({
			url: thisObj.getContextPath() + url,
			data: formData,
			type: 'post',
			processData: false, 
			contentType: false,
			dataType: "json",
			success:function(data){
				if(data.result){
					if(data.result.msg) {
						thisObj.MessageBox(data.result.msg, function(){
							if(data.result.status == "success"){
								dfd.resolve();
							}else{
								dfd.reject();
							}	
						})
					}else{						
						if(data.result.status == "success"){
							dfd.resolve();
						}else{
							dfd.reject();
						}
					}
				}
			},
			error: function(){
				console.log(xhr)
				console.log("status", textStatus)
				console.log("error", error)
				thisObj.MessageBox(["데이터 처리 도중 오류가 발생하였습니다."], function(){
					dfd.reject();	
				});	
			},
			complete: function(){
				thisObj.hideLoading();
			}
		})
		
		return dfd.promise();
	},
	Popup:function(id, name, url, _param){
		const thisObj = this;
		const popSelector = "#popInner";
		const popContSelector = "#layPopup-" + id;
		
		// 팝업 Open (팝업 ID와 리턴 값 받을 Callback 함수)
		function openPopup(targetId, param2, callback){
			const callbackId = "callback-" + targetId;
			
			if(typeof param2 === 'function'){
				callback = param2;
				param2 = {}
			}
			
			$(document).data(callbackId, callback);
			const param = $.extend({
				id: 		id, 
				callbackId:	callbackId, 
				name: 		name,
				size: 		'M'
			}, _param, param2);
			
			// 팝업 페이지 로딩
			$.ajax({
				type: "post",
				contentType: 'application/json',
				url: thisObj.getContextPath() + url,
				data: JSON.stringify(param),
				dataType: "html",
				success: function(data, textStatus, xhr){
					
					// content 영역만 가져오기
					let $popCont = $(data).filter(popContSelector);
					$(popSelector).append($popCont);
					
					// 팝업의 기본 상단 닫기버튼(X)에 이벤트 바인딩 
					$popCont.off('click').on('click', '.closeL', closePopup);
					
					// 페이지 보이기
					showPopup();
				},
				error: function(xhr, textStatus, error){
					console.log(xhr)
					console.log("status", textStatus)
					console.log("error", error)
					thisObj.MessageBox(["데이터 처리 도중 오류가 발생하였습니다."]);
				}
			})
		}
		
		function showPopup(){
			// open
			$(popSelector).show(); 
            $('.dimmed').show();
            centerPos();
            preventScroll();
		}
		
		function closePopup(){
			$(popContSelector).remove();
			if($(popSelector).find(".layPopup").length == 0){			
				$(popSelector).hide();
				$('.dimmed').hide();
			}
		}
		
		
		function centerPos(){
			//레이어 팝업 가운데 정렬
            var popH;
            $('.layPopup:visible').each(function() {
                popH = $(this).outerHeight();
                $(this).css('margin-top', -parseInt(popH / 2));
            });
		}
		
		function preventScroll(){
			// 마우스가 팝업 위에 있을 경우 - 마우스 휠 방향에 따른 현재 객체  스크롤 값 계산하여 지정
            var scrollElem;
            $(document).off('mousewheel.layPopup').on('mousewheel.layPopup', function(e) {
                if (scrollElem && scrollElem.is(':visible')) { // layer popup scroll cont
                    e.preventDefault();
                    var wheelEvent = e.originalEvent;
                    var dY = wheelEvent.detail ? wheelEvent.detail : -(wheelEvent.wheelDelta);
                    var unknownVal = 0;
                    scrollElem.parent().hasClass('popCont') || scrollElem.hasClass('popLayer') ? unknownVal = 0 : unknownVal = -2;
                    scrollElem.each(function(m) {
                        var $scElem = $(this),
                            otherScroll = $scElem.scrollTop();
                        if ($scElem[0].scrollHeight - $scElem.scrollTop() === ($scElem.outerHeight() + unknownVal)) {
                            dY > 0 ? deltaSave = 1 : deltaSave = -1;

                        } else {
                            dY > 0 ? deltaSave = 1 : deltaSave = -1;
                            if ($scElem.scrollTop() === 0) {
                                dY < 0 ? deltaSave = 0 : deltaSave;
                            }
                        }
                        $scElem.scrollTop(otherScroll + deltaSave * 100);
                    });
                } else {
                    //레이어 팝업 열려 있을 경우 body 스크롤 막기
                    if ($('#popInner .layPopup:visible').length > 0) {
                        e.preventDefault();
                    }
                }
            });

            $(document).off('mouseenter.pl');
            $(document).on('mouseenter.pl', '#popInner .layPopup', function(e) {
                var findElem = $(this).find('*').filter(function() {
                    if ($(this).css('overflow-y') == 'auto' || $(this).css('overflow-y') == 'scroll') return $(this);
                });
                if ($(this).css('overflow-y') == 'auto' || $(this).css('overflow-y') == 'scroll') {
                    var pElem = $(this);
                    scrollElem = $(this);
                    if (findElem.length > 0) {
                        findElem.off('mouseenter').on('mouseenter', function(e) {
                            scrollElem = $(this);
                        }).off('mouseleave').on('mouseleave', function(e) {
                            scrollElem = pElem;
                        });
                    }
                } else {
                    scrollElem = $(this).find('*').filter(function() {
                        if ($(this).css('overflow-y') == 'auto' || $(this).css('overflow-y') == 'scroll') return $(this);
                    });
                    scrollElem.off('mouseenter').on('mouseenter', function(e) {
                        scrollElem = $(this);
                    }).off('mouseleave').on('mouseleave', function(e) {
                        if ($(this).closest('.layCont').length > 0) {
                            scrollElem = $(this).closest('.layCont');
                        }
                    });
                }
                if (scrollElem.length < 1) scrollElem = undefined;

                //디자인 스크롤
                if ($(e.target).closest('.jspScrollable').length > 0) {
                    scrollElem = $(e.target).closest('.jspScrollable');
                }
            });

            $(document).off('mouseleave.pl');
            $(document).on('mouseleave.pl', '#popInner .layPopup', function() {
                scrollElem = undefined;
            });
		}
		
		return {
			closePopup:closePopup,
			openPopup: openPopup
		}
	},
	closePopup:function(id){
		const popSelector = "#popInner";
		const popContSelector = "#layPopup-" + id;
		
		$(popContSelector).remove();
		if($(popSelector).find(".layPopup").length == 0){			
			$(popSelector).hide();
			$('.dimmed').hide();
		}
	},
	ConfirmBox: function(msg, option, callback){
		let thisObj = this;
		if(typeof option == 'function') callback = option;
		
		function closeConfirmBox($messageBox){
			$messageBox.remove();
		}
		$.ajax({
			url: thisObj.getContextPath() + "/resources/html/ConfirmBox.html",
			type:"get",
			dataType: "html",
			success: function(html){
				let $html = $(html);
				if(option){
					if(option.width) $html.find(".alertBox").css('width', option.width)
				}
				if(msg){					
					if(typeof msg == 'string'){
						$html.find('.alertMsg').append('<li>' + msg + '</li>')
					}else{					
						$html.find('.alertMsg').append('<li>' + msg.join("</li><li>") + '</li>')
					}
				}
				
				$html.find('.btCancel').off('click.alertBtn').on('click.alertBtn', function(){closeConfirmBox($html); if(callback) callback(false);});
				$html.find('.btConfirm').off('click.alertBtn').on('click.alertBtn', function(){closeConfirmBox($html); if(callback) callback(true);});
				
				$html.appendTo("body");
			}
		})
	},
	MessageBox: function(msg, option, callback){
		let thisObj = this;
		if(typeof option == 'function') callback = option;
		function closeConfirmBox($messageBox){
			$messageBox.remove();
		}
		
		$.ajax({
			url: thisObj.getContextPath() + "/resources/html/MessageBox.html",
			type:"get",
			dataType: "html",
			success: function(html){
				let $html = $(html);
				
				if(option){
					if(option.width) $html.find(".alertBox").css('width', option.width)
				}
				
				if(msg){					
					if(typeof msg == 'string'){
						$html.find('.alertMsg').append('<li>' + msg + '</li>')
					}else{					
						$html.find('.alertMsg').append('<li>' + msg.join("</li><li>") + '</li>')
					}
				}
				
				$html.find('.btConfirm').off('click.alertBtn').on('click.alertBtn', function(){closeConfirmBox($html);if(callback) callback();});
				$html.appendTo("body");
			}
		})
	},
	isIE: function(){
		var agent = navigator.userAgent.toLowerCase();
		if ( (navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || (agent.indexOf("msie") != -1)) {
		     return true;
		}else{
		     return false;
		}
	},
	ObjectProxy: function(obj, handler){
		let thisObj = this;
		let data = $.extend({}, obj);
		let retVal = {};
		
		for(let _key in data){
			let key = _key;
			Object.defineProperty(retVal, key, {
				get: function(){
					return handler.get(data, key);
				},
				set: function(value){
					handler.set(data, key, value);
				},
				enumerable: true,
			})
		} 
		
		return retVal;
	},
	DateCtrl: function(){
		this.toTimeObject = function(timeStr){	// yyyymmddhhmiss
			let year  = timeStr.substr(0,4);
		    let month = timeStr.substr(4,2) - 1; // 1월=0,12월=11
		    let day   = timeStr.substr(6,2);
		    let hour  = timeStr.substr(8,2);
		    let min   = timeStr.substr(10,2);
		    
		    return new Date(year,month,day,hour,min);
		},
		this.toTimeString = function(date){
			let year  = date.getFullYear();
		    let month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
		    let day   = date.getDate();
		    let hour  = date.getHours();
		    let min   = date.getMinutes();
		    let sec	  = date.getSeconds();
		    
		    if (("" + month).length == 1) { month = "0" + month; }
		    if (("" + day).length   == 1) { day   = "0" + day;   }
		    if (("" + hour).length  == 1) { hour  = "0" + hour;  }
		    if (("" + min).length   == 1) { min   = "0" + min;   }
		    if (("" + sec).length   == 1) { sec   = "0" + sec;   }

		    return ("" + year + month + day + hour + min + sec);
		},
		this.toFormatedTimeString = function(date){
			let year  = date.getFullYear();
		    let month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
		    let day   = date.getDate();
		    let hour  = date.getHours();
		    let min   = date.getMinutes();
		    let sec	  = date.getSeconds();
		    if (("" + month).length == 1) { month = "0" + month; }
		    if (("" + day).length   == 1) { day   = "0" + day;   }
		    if (("" + hour).length  == 1) { hour  = "0" + hour;  }
		    if (("" + min).length   == 1) { min   = "0" + min;   }
		    if (("" + sec).length   == 1) { sec   = "0" + sec;   }
		    return ("" + year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec);
		},
		this.shiftTime = function(time, y, m, d, h){
			var date = this.toTimeObject(time);

		    date.setFullYear(date.getFullYear() + (y||0)); //y년을 더함
		    date.setMonth(date.getMonth() + (m||0));       //m월을 더함
		    date.setDate(date.getDate() + (d||0));         //d일을 더함
		    date.setHours(date.getHours() + (h||0));       //h시를 더함

		    return this.toTimeString(date);
		},
		this.getMonthInterval = function(time1,time2) { //measureMonthInterval(time1,time2)
		    var date1 = this.toTimeObject(time1);
		    var date2 = this.toTimeObject(time2);

		    var years  = date2.getFullYear() - date1.getFullYear();
		    var months = date2.getMonth() - date1.getMonth();
		    var days   = date2.getDate() - date1.getDate();

		    return (years * 12 + months + (days >= 0 ? 0 : -1) );
		},
		this.getDayInterval = function(time1,time2) {
			var date1 = this.toTimeObject(time1);
		    var date2 = this.toTimeObject(time2);
		    var day   = 1000 * 3600 * 24; //24시간

		    return parseInt((date2 - date1) / day, 10);
		}
		this.toFormatDate = function(time) {
		    return this.toFormatedTimeString(time).substr(0,10);
		}
	},
	replaceAll: function(str, targetStr, replacementStr){
		let regExp = new RegExp(targetStr, "g");
		return str.replace(regExp, replacementStr);
	},
	getExtension: function(fullname){
		let regExp = /\.[a-z0-9]+$/gi;
		let tmp = fullname.match(regExp);
		if(tmp){
			return tmp[0].substr(1);
		}else{
			return null;
		}
	},
	getGridTitleAndInfo:function(title, info, direction){
		const GRID_INFO = '[INFO]';
		const GRID_TITLE = '[TITLE]';
		const GRID_INFO_BOX_LEFT = '<span style="display:inline-block">[TITLE]</span><div class="tipArea"><button type="button" class="btnTip">도움말</button><div class="tipCont">[INFO]</div></div>';
		const GRID_INFO_BOX_RIGHT = '<span style="display:inline-block">[TITLE]</span><div class="tipArea tipLeft"><button type="button" class="btnTip">도움말</button><div class="tipCont">[INFO]</div></div>';
		
		if(direction == 'right'){
			return GRID_INFO_BOX_RIGHT.replace(GRID_TITLE, title).replace(GRID_INFO, info);
		}else{
			return GRID_INFO_BOX_LEFT.replace(GRID_TITLE, title).replace(GRID_INFO, info);
		}
	} ,
	isNull: function(val){
		if(val === undefined || val === null || val === '') return true;
		return false;
	},
	showLoading: function(){
    	$(".loadingA").show();
    },
    hideLoading: function(){
    	$(".loadingA").hide();
    },
    getBytesLength: function(s){
    	let b, i, c;
		for (b = i = 0; c = s.charCodeAt(i++); b += (c >> 7 ? 2 : 1));
		return b;
    },
    round: function(num, point){
    	let tmp = Math.pow(10, point||0);
    	return Math.round(num * tmp)/tmp;
    }
}

function MessageManager() {
	const replaceTextMark = "%VALUE%";
	const delimeter = '%DELIMETER%'
	const messages = {
			LOCKBIZTODT: ['정책에 의해 사업연도 종료월이 '+ replaceTextMark + ' 이전 프로젝트는 수정할 수 없습니다.'],
			NEARBIZTODT: ['사업연도 종료까지 '+ replaceTextMark +'개월 남은 프로젝트는 수정할 수 없습니다.', '계약 변경 등으로 수정이 필요한 경우에는 본부별 Budget 담당자에게 문의 바랍니다.']
	}
	
	this.getMessage = function(id, replacement) {
		
		const msg = messages[id];
		let replacementList = [];
		if(replacement) {
			replacementList = replacementList.concat(replacement);
		}
		
		if(msg) {
			let tmpMsg = '';
			if(replacementList.length > 0) {
				if(Array.isArray(msg)) {
					tmpMsg = repaceText(msg.join(delimeter), replacementList).split(delimeter);
				}else{
					tmpMsg = repaceText(msg, replacementList).split(delimeter);
				}
			}
			return tmpMsg;
		}
		
		return '';
	}
	
	function repaceText(text, replacementList) {
		for(let i=0; i<replacementList.length; i++){
			text = text.replace(replaceTextMark, replacementList[i]);
		}
		
		return text;
	}
}

// ----------------------------------------------------------------------------------------------------------------------------------------------

function grid_noData(gridId, isExist, contents){
	if(isExist==true){
		if($("#grid-info").length == 0){
			$("#" + gridId + " .objbox").append("<span id='grid-info' class='grid-info-container'>" +
					"<div class='grid-info-content'>" +
					contents +
					"</div>" +
					"</span>")	
		}
	}else{
		$("#grid-info").remove();
	}
}

const EXCEL_WRITER_URL = Helper.getContextPath() + "/excel/writer";
//2020.05.11 추가
const EXCEL_WRITER_URL_V3 = Helper.getContextPath() + "/excel/writerV3";

//=====================================================
//	[시작] 20220210 남웅주  2022년도 표준감사시간 개정
//=====================================================
//const EXCEL_WRITER_URL_V4 = Helper.getContextPath() + "/excel/writerV4";

// 엑셀 버전 up을 위한 임시 작업
const EXCEL_WRITER_URL_V4 = Helper.getContextPath() + "/excel/writerPoiV4";

//=====================================================
//	[종료] 20220210 남웅주  2022년도 표준감사시간 개정
//=====================================================


const GRID_INFO_STA_ET_DFN_SAT = '한공회산식에 따라 산출된 감사시간에 산식외 요소를 고려하여 결정한 최종 표준감사시간';
const GRID_INFO_STA_BASE_WKMNSP = '감사대상 회사가 소속된 그룹별 기준숙련도';
const GRID_INFO_STA_WKMNSP_TEAM = 'From F-link(외감 참여 인원수 및 시간 집계)';
const GRID_INFO_STA_WKMNSP_SAT_TEAM = '한공회 표준감사시간 * 기준숙련도/팀의 숙련도	모니터링 시점 현재 팀의숙련도가 유지되었을 경우, 예상되는 표준감사시간';
const GRID_INFO_STA_ACTUAL_TIME = 'From F-link(외감 참여 인원수 및 시간 집계)';
// const GRID_INFO_STA_PROGRESS = 'Actual Time / 예상 표준감사시간';
const GRID_INFO_STA_PROGRESS = 'Actual Time / 합의시간(외감)';

const GRID_INFO_WKMNSP = '' 
	+ '<div class="tipArea tipLeft">'
	+ '	<button type="button" class="btnTip">도움말</button>'
	+ '	<div class="tipCont tipW02">'
	+ '		 <table class="tblH">'
	+ '			<caption>경력기간별 숙련도 테이블입니다.</caption>'
	+ '			<colgroup><col style="width:35%"><col style="width:25%"><col style="width:auto"></colgroup>'
	+ '			<thead>'
	+ '				<tr>'
	+ '					<th scope="col">경력기간</th>'
	+ '					<th scope="col">숙련도</th>'
	+ '					<th scope="col">예상 Grade</th>'
	+ '				</tr>'
	+ '			</thead>'
	+ '			<tbody>'
	+ '				<tr>'
	+ '					<td class="tleft">실무수습 1년 2년</td>'
	+ '					<td class="tright">0.4</td>'
	+ '					<td class="tleft">Associate</td>'
	+ '				</tr>'
	+ '				<tr>'
	+ '					<td class="tleft">경력 2년미만</td>'
	+ '					<td class="tright">0.8</td>'
	+ '					<td class="tleft">SA 1,2년차</td>'
	+ '				</tr>'
	+ '				<tr>'
	+ '					<td class="tleft">경력 6년미만</td>'
	+ '					<td class="tright">1</td>'
	+ '					<td class="tleft">SA 3년차 ~ M 3년차</td>'
	+ '				</tr>'
	+ '				<tr>'
	+ '					<td class="tleft">경력 10년미만</td>'
	+ '					<td class="tright">1.1</td>'
	+ '					<td class="tleft">SM ~ Director 1년차</td>'
	+ '				</tr>'
	+ '				<tr>'
	+ '					<td class="tleft">경력 15년미만</td>'
	+ '					<td class="tright">1.15</td>'
	+ '					<td class="tleft" rowspan="2">Director 2년차 이상</td>'
	+ '				</tr>'
	+ '				<tr>'
	+ '					<td class="tleft">경력 15년이상</td>'
	+ '					<td class="tright">1.2</td>'
	+ '				</tr>'
	+ '				<tr>'
	+ '					<td class="tleft" colspan="3">KICPA 기준이며, 기타 전문가는 기준숙련도 기준으로 적용됩니다.<br>※ 자동계산되는 개인의 숙련도는 휴업기간은 고려되어있지 않습니다. (숙련도 수정 가능함)</td>'
	+ '				</tr>'
	+ '			</tbody>'
	+ '		 </table>'
	+ '	</div>'
	+ '</div>';

const GRID_INFO_PLAN_SAT 		= '한공회 표준감사시간과 Budgeting 단계의 팀숙련도를 고려하여 계산한 예상 표준감사시간<br>(한공회 표준감사시간 * 기준숙련도/팀숙련도)';
const GRID_INFO_TOT_BUDGET 		= 'Budgeting 완료 후 인별로 배부된 총 시간';
const GRID_INFO_ET_TRGT_ADT_TM	= '한공회산식에 따라 산출된 감사시간(숙련도 반영 전)을 기초로 ET가 Client와 보수 협의 단계에서 합의한 시간.<br>기준숙련도 투입을 가정하여 산출된 시간';