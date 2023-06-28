<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
#EXCEL_DOWNLOAD_BY_AUTH .calenWrap input{width: 260px;}
#EXCEL_DOWNLOAD_BY_AUTH input::placeholder { /* Chrome, Firefox, Opera, Safari 10.1+ */
  color: #7D7D7D;
  opacity: 1; /* Firefox */
}
#EXCEL_DOWNLOAD_BY_AUTH input:-ms-input-placeholder { /* Internet Explorer 10-11 */
  color: #7D7D7D;
}
#EXCEL_DOWNLOAD_BY_AUTH input::-ms-input-placeholder { /* Microsoft Edge */
  color: #7D7D7D;
}
#EXCEL_DOWNLOAD_BY_AUTH .tblH tbody tr td:first-child{text-align:left}
</style>
<div class="layCont" id="EXCEL_DOWNLOAD_BY_AUTH">
	<table class="tblH">
		<colgroup><col style="width:200px"><col style="width:auto"><col style="width:150px" /></colgroup>
		<thead>		
			<tr>
				<th scope="col">종류</th>
				<th scope="col">조건</th>
				<th scope="col">버튼</th>
				<!-- 그룹 목록 -->
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>인원별 전체 Budget</td>
				<td id="bdgtByEmp" style="text-align:left">
					<div style="text-align:left"><div class="calenWrap"><input name="sdt" type="text" readonly></div><span>&nbsp;&nbsp;~&nbsp;&nbsp;</span><div class="calenWrap"><input name="edt" type="text" readonly></div></div>
					<div style="text-align:left">입력된 기간에 포함된 인원별 Budget 정보를 다운로드 합니다.</div>
				</td>
				<td>
					<button type="button" id="btnDownloadBdgtByEmp" class="btnPwc btnM action">Download</button>
				</td>
			</tr>
			<tr>
				<td>표준감사시간 Summary</td>
				<td  id="satBdgtInfo">
					<div style="text-align:left"><span style="display:inline-block;">프로젝트 시작일&nbsp;>=&nbsp;</span><div class="calenWrap"><input name="baseDt" type="text" readonly placeholder="기준일"></div></div>
				</td>
				<td>
					<button type="button" id="btnDownloadSatBdgtInfo" class="btnPwc btnM action">Download</button>
				</td>
			</tr>
			<tr>
				<td>프로젝트 상세 정보</td>
				<td  id="satPrjtInfo">
					<div style="text-align:left"><span style="display:inline-block;">프로젝트 시작일&nbsp;>=&nbsp;</span><div class="calenWrap"><input name="baseDt" type="text" readonly placeholder="기준일"></div></div>
				</td>
				<td>
					<button type="button" id="btnDownloadSatPrjtInfo" class="btnPwc btnM action">Download</button>
				</td>
			</tr>
		</tbody>
	</table>
	
	
	<ul style="min-height:110px;padding-top:2rem;color:#e0301e">
		<li>접근 가능한 프로젝트만 다운로드 됩니다.</li>
	</ul>
	
	<div class="btnArea">
		<button id="popBtnCancle" type="button" class="btnPwc btnL">닫기</button>
	</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ExcelExport.js" ></script>
<script type="text/javascript">
(function(){
	// pwc_ui.js에서 Calendar 부분 복사.
	let calendar = {
	        init: function() {
	            if ($('#EXCEL_DOWNLOAD_BY_AUTH .calenWrap input').length === 0) {
	                return;
	            }
	            this._setElment();
	            this._bindEvent();
	        },
	        _setElment: function() {
	            var $jsDatepicker = $('#EXCEL_DOWNLOAD_BY_AUTH .calenWrap input');
	            var oSelf = this,
	                $lastFocus,
	                $dateWrap;

	            $jsDatepicker.each(function() {
	                var getDataMin = $(this).data('min') || '-2Y',
	                    getDataMax = $(this).data('max') || '+4Y',
	                    $calBtn = $(this).siblings('button.cal');


	                // 접근성 관련 - showOn: 'button' 추가
	                $(this).siblings('.cal').remove();

	                $(this).datepicker({
	                    /*
	                     * dateFormat : 'y. mm. dd'로 수정할 경우 개발자분들에게 추가 요청 필요함
	                     * 변경할 때 data-min, data-max 값도 16. mm. dd식으로 수정 요청 필요함
	                     */
	                    showOn: 'button',
	                    buttonText: '날짜선택',
	                    dateFormat: 'yy-mm-dd',
	                    showMonthAfterYear: true,
	                    showOtherMonths: true,
	                    selectOtherMonths: true,
	                    monthNames: ['.01', '.02', '.03', '.04', '.05', '.06', '.07', '.08', '.09', '.10', '.11', '.12'],
	                    dayNamesMin: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
	                    monthNamesShort: ['01월', '02월', '03월', '04월', '05월', '06월', '07월', '08월', '09월', '10월', '11월', '12월'],
	                    showButtonPanel: true,
	                    minDate: getDataMin,
	                    maxDate: getDataMax,
	                    closeText: '취소',
	                    beforeShow: function(input, date) {

	                        //create layout
	                        oSelf._createLayoutSel(input, date);

	                        $lastFocus = $(document).find(':focus');

	                        setTimeout(function() {
	                            $datepicker = $('#ui-datepicker-div');
	                            $datepicker.css({
	                                position: '',
	                                top: '',
	                                left: '',
	                                'z-index': ''
	                            });

	                            //datepicker 탬플릿 특정 영역 안에 위치 하도록 수정
	                            $(input).closest('.calenWrap').find('.calendarWrap').append($('#ui-datepicker-div'));

	                            oSelf._renderDateHeader({
	                                target: $datepicker,
	                                year: date.selectedYear,
	                                month: date.selectedMonth + 1
	                            });

	                            $(input).closest('.calenWrap').find('.calendarWrap').attr('tabindex', 0).focus();
	                        }, 0);

	                        // input에 data-min이 있을 경우 data-min의 이전 날짜 체크 불가능
	                        if ($(input).attr('data-min')) {
	                            $(input).datepicker('option', 'minDate', $(input).attr('data-min'));
	                        }

	                        // input에 data-min이 있을 경우 data-max의 이후 날짜 체크 불가능
	                        if ($(input).attr('data-max')) {
	                            $(input).datepicker('option', 'maxDate', $(input).attr('data-max'));
	                        }
	                    },
	                    onChangeMonthYear: function(year, month, inst) {
	                        setTimeout(function() {
	                            $datepicker = $('#ui-datepicker-div');
	                            $datepicker.css({
	                                position: '',
	                                top: '',
	                                left: '',
	                                'z-index': ''
	                            });

	                            oSelf._renderDateHeader({
	                                target: $datepicker,
	                                year: year,
	                                month: month
	                            });
	                        }, 0);
	                    },
	                    onClose: function(selectedDate, ins) {
	                        //닫기
	                        ins.input.closest('.calenWrap').find('.calendarWrap').fadeOut();
	                        if ($('.alertBox:visible').length > 0) {
	                            return;
	                        }
	                    },
	                    onSelect: function(dateText, inst) {
	                        //console.log(dateText, inst, $(this));
	                        inst.input.trigger("change");
	                    }
	                });

	            });
	        },
	        _bindEvent: function() {
	            var oSelf = this;
	            //데이터피커 외부 영역 클릭 시 달력 닫히는 부분 방지
	            $(document).unbind('mousedown', $.datepicker._checkExternalClick);

	            //달력 활성화
	            $(document).on('click.datepicker', '.calenWrap .ui-datepicker-trigger, #EXCEL_DOWNLOAD_BY_AUTH .calenWrap input', function(e) {
	                e.preventDefault();
	                if ($(this).get(0).tagName === 'BUTTON') {
	                    $(this).siblings('input').datepicker("show");
	                } else {
	                    $(this).datepicker("show");
	                }
	            });

	            $(document).on('click', '.ui-datepicker-prev, .ui-datepicker-next', function(e) {
	                e.preventDefault();
	            });

	            //캘린더 다른 영역 선택 시 닫힘
	            $(document).off('click.closeCal');
	            $(document).on('click.closeCal', $.proxy(this._onClickCloseCal, this));

	        },
	        _onClickCloseCal: function(e) {
	            var $clickEl = $(e.target);

	            if ($('.calendarWrap:visible').length === 0) {
	                return;
	            }
	            //영역 삭제 되면 체크 안함
	            if (!$clickEl.is(':visible')) {
	                return;
	            }
	            var isCalendar = $clickEl.closest('.calenWrap').length > 0;

	            if (!isCalendar) {
	                $('.ui-datepicker-close').trigger('click');
	            }
	        },
	        _createLayoutSel: function(input, date) {
	            var $calWrap = $(input).closest('.calenWrap');

	            if ($calWrap.find('.calendarWrap').length === 0) {
	                $calWrap.append('<div class="calendarWrap"></div>');
	            }

	            $calWrap.find('.selectbox').eq(0).append('<select id="selYear" title="년도 선택" />');
	            $calWrap.find('.selectbox').eq(1).append('<select id="selMonth" title="월 선택" />');
	            $calWrap.find('.calendarWrap').fadeIn();
	        },
	        _renderDateHeader: function(opts) {
	            opts.target.find('.tblCalendar').prepend('<caption>달력 테이블에는 일,월,화,수,목,금,토 요일과 일 선택을 하실 수 있습니다.</caption>');
	            opts.target.find('.ui-datepicker-current').remove();

	            //disabled 된 영역 접근성 추가
	            opts.target.find('.ui-datepicker-unselectable').children().attr('aria-disabled', 'true');

	            //접근성 - 좌우 화살표에 a 링크에 href 속성 추가
	            opts.target.find('.ui-datepicker-prev').attr('href', '#');
	            opts.target.find('.ui-datepicker-next').attr('href', '#');

	            //접근성 - 달력 새로 선택 시 포커스 이동
	            if (this.lastFocusClass) {
	                opts.target.closest('.calendarWrap').find(this.lastFocusClass).focus();
	            }

	        },
	        _pad: function(n, width) {
	            n = n + '';
	            return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
	        }
	    };
	calendar.init();
	
	$("#layPopup-${popupInfo.id} #popBtnCancle").off('click').on('click', function(){Helper.closePopup("${popupInfo.id}");});
	
	$("#btnDownloadBdgtByEmp").off('click').on('click', function(){
		let sdt = $("#bdgtByEmp input[name='sdt']").val();
		let edt = $("#bdgtByEmp input[name='edt']").val();
		
		if(Helper.isNull(sdt) || Helper.isNull(edt)){
			Helper.MessageBox("시작일과 종료일을 입력하세요");
			return false;
		} 
		
		COMMON.showLoading();
		$.fileDownload("${pageContext.request.contextPath}/excel/export/bdgtByEmp", {
			httpMethod: "POST", 
		    data: $.param({
		    	sdt: sdt,
		    	edt: edt,
		    	filename: "[표준감사시간] 인원별 전체 Budget(" + sdt + "~" + edt + ")"
		    }),
		    successCallback: function (url) { 
		    	COMMON.hideLoading();
	       },
	       failCallback: function (responseHtml, url, error) {
	    	   COMMON.hideLoading();
	       } 
		});
		return false;
	});

	$("#btnDownloadSatBdgtInfo").off('click').on('click', function(){
		let baseDt = $("#satBdgtInfo input[name='baseDt']").val();
		if(Helper.isNull(baseDt)){
			Helper.MessageBox('기준일을 입력하세요.');
			return false;
		}
		
		COMMON.showLoading();
		$.fileDownload("${pageContext.request.contextPath}/excel/export/satBdgtInfo", {
			httpMethod: "POST", 
		    data: $.param({
		    	baseDt: baseDt,
		    	filename: "[표준감사시간] 표준감사시간 Summary(" + baseDt + " 이후)"
		    }),
		    successCallback: function (url) { 
		    	COMMON.hideLoading();
	       },
	       failCallback: function (responseHtml, url, error) {
	    	   COMMON.hideLoading();
	       } 
		});
		return false;
	});

	$("#btnDownloadSatPrjtInfo").off('click').on('click', function(){
		let baseDt = $("#satPrjtInfo input[name='baseDt']").val();
		if(Helper.isNull(baseDt)){
			Helper.MessageBox('기준일을 입력하세요.');
			return false;
		}
		
		COMMON.showLoading();
		$.fileDownload("${pageContext.request.contextPath}/excel/export/satPrjtInfo", {
			httpMethod: "POST", 
		    data: $.param({
		    	baseDt: baseDt,
		    	filename: "[표준감사시간] 프로젝트 상세 정보(" + baseDt + " 이후)"
		    }),
		    successCallback: function (url) { 
		    	COMMON.hideLoading();
	       },
	       failCallback: function (responseHtml, url, error) {
	    	   COMMON.hideLoading();
	       } 
		});
		return false;
	});
})();
</script>