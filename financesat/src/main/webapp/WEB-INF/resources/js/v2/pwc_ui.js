//2018-12-10
var COMMON = (function(ns, $) {
    ns.init = function() {

        //layout
        ns.layout.init();
        ns.lnb.init();
        ns.domEvent.init();

        //form
        //ns.formEvent.init();
        ns.alertpopup.init();

        // [수정] 2019-09-23 by shyunchoi 
        //ns.calendar.init();

        ns.layerpopup.init();
    };

    ns.layout = {
        init: function() {
            this._ieClass();
			//this._navHeight();
        },

        _ieClass: function() {
            var agent = navigator.userAgent.toLowerCase();
            if ((navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || (agent.indexOf("msie") != -1)) {
                $('body').addClass('ieBody');
            } else {
                return false
            }
        },
		
		_navHeight: function() {
            var navH = $(window).height() - 61;
			
			$('nav').css('height',navH);
        }

    };

    ns.lnb = {
        init: function() {
            this._lnbAnition();
            this._snbListAni();
        },

        _lnbAnition: function() {
            var $lnbWrap;
            $(document).off('click.lnbToggle');
            $(document).on('click.lnbToggle', 'nav > button', function(e) {
                e.preventDefault();
                $snbBtn = $(this);
                $toggleWrap = $('.wrapper');
                $toggleWrap.toggleClass('navClose');
            });
        },
        _snbListAni: function() {
            var oSelf = this;
            $(document).off('click.snbList').on('click.snbList', '.snbList > li > a', function(e) {
                var $li = $(this).closest('li');
                var $subList = $li.find('>.snbDep2');
                if ($subList.length === 0) {
                    return;
                }

                if ($('.wrapper').hasClass('navClose')) {
                    return;
                }
                e.preventDefault();

                if ($li.hasClass('snbON')) {
                    $subList.stop().slideUp('easeInCubic', function() {
                        $li.removeClass('snbON');
                        oSelf._setScrollNav();
                    });
                } else {
                    $li.addClass('snbON');
                    $subList.hide();
                    $subList.stop().slideDown('easeOutCubic', function() {
                        oSelf._setScrollNav();
                    });

                    if ($li.siblings('li.snbON').length > 0) {
                        $li.siblings('li.snbON').find('>a').trigger('click');
                    }
                }
            });

            $(document).off('mouseenter.snbList').on('mouseenter.snbList', '.snbList > li', function(e) {
                var $li = $(this);
                var $subList = $(this).find('>.snbDep2');
                if ($subList.length === 0) {
                    return;
                }

                if (!$('.wrapper').hasClass('navClose')) {
                    return;
                }
                e.preventDefault();
                if ($('.navArea').data('jsp')) {
                    $('.navArea').data('jsp').destroy();
                }
                $li.addClass('snbON');
            });

            $(document).off('mouseleave.snbList').on('mouseleave.snbList', '.snbList > li', function(e) {
                if (!$('.wrapper').hasClass('navClose')) {
                    return;
                }
                if ($('.navArea').data('jsp')) {
                    $('.navArea').data('jsp').destroy();
                }
                var $li = $(this);
                $li.removeClass('snbON');
            });

            $(window).off('resize.scrollNav');
            $(window).on('resize.scrollNav', $.proxy(this._setScrollNav, this)).trigger('resize.scrollNav');

        },
        _setScrollNav: function() {
            var $nav = $('.navArea');
            if ($nav.data('jsp') !== undefined) {
                $nav.data('jsp').reinitialise();
                // if ($nav.data('jsp').getIsScrollableV()) {
                //     $nav.find('.jspPane').css('top', 0);
                //     $nav.find('.jspDrag').css('top', 0);
                // }
            } else {
                var scrollBarH = $nav.outerHeight();
                $nav.on(
                        'jsp-initialised',
                        function(event, isScrollable) {

                            //스크롤 조절
                            $(this).find('.jspTrack').removeAttr('style');
                            $(this).find('.jspDrag').css('height', $(this).find('.jspDrag').height() - 21);

                            //불필요한 태그 삭제
                            $(this).find('.jspArrow').remove();
                            $(this).find('.jspCap').remove();
                            $(this).find('.jspHorizontalBar').remove();
                            $(this).css('max-height', '');


                            //불필요한 태그 삭제
                            $(this).css('max-height', '');
                            $(this).css('width', '');
                        }
                    )
                    .jScrollPane({
                        showArrows: false,
                        verticalDragMinHeight: 30,
                        mouseWheelSpeed: scrollBarH
                    });
            }
        }


    };

    ns.domEvent = {
        init: function() {
            this._toggleCont();
            this._toggleTipBox.init();
			this._accodianCont();
			this._toolTip();
			this._setAttachFile();
        },
		
		 _toolTip: function() {
            
			$(document).off('mouseenter.tipArea').on('mouseenter.snbList', '.tipArea > .btnTip', function(e) {
                var $tipWrap = $(this).parents('.gridbox');
                if ($tipWrap.length === 0) {
                    return;
                }
                
                e.preventDefault();
                $tipWrap.addClass('tipVisible');
            });

            $(document).off('mouseleave.tipArea').on('mouseleave.tipArea', '.tipArea > .btnTip', function(e) {
				var $tipWrap = $(this).parents('.gridbox');
				
				if ($tipWrap.length === 0) {
                    return;
                }
				
                e.preventDefault();
				
                $tipWrap.removeClass('tipVisible');
            });
		 },
		 
		 _setAttachFile: function() {
            var uploadFile = $('#file_attach'),
                fileName;
            $('body').on('change', 'input[type=file]', function() {
                if (window.FileReader) {
                    if($(this)[0].files[0] !== undefined){
                        fileName = $(this)[0].files[0].name;
                    }else{
                        fileName = '';
                    }
                } else {
                    if($(this).val() !== undefined){
                        fileName = $(this).val().split('/').pop().split('\\').pop();
                    }else{
                        fileName = '';
                    }
                }

                $(this).siblings('.file').text(fileName);
            });
        },

        _toggleCont: function() {
            // $(.contents .sumArea .btnTog) 버튼 클릭 시 ->  $('.sumArea') toggleOpen 클래스 토글

            //토글 컨텐츠 높이 계산
            var oSelf = this;
            

            var $toggleWrap;
            var $toggleBtn;
			$(document).off('click.toggleCont');
            $(document).on('click.toggleCont', '.contents .sumArea .btnTog', function(e) {
                e.preventDefault();
                $toggleBtn = $(this);
                $toggleWrap = $(this).closest('.sumArea');
                $toggleWrap.toggleClass('toggleOpen');


                if ($toggleWrap.hasClass('toggleOpen')) {
                    //열기
                    $toggleBtn.text('닫기');
                } else {
                    //닫기
                    $toggleBtn.text('열기');
                }
            });
        },
		
		_accodianCont: function () {
            // $(.contents .sumArea .btnTog) 버튼 클릭 시 ->  $('.sumArea') toggleOpen 클래스 토글

            //토글 컨텐츠 높이 계산
            var oSelf = this;
            var schedulerB = $('.treeGroup');
            

            var $toggleWrap;
            var $toggleBtn;
			var $dep2;
            $(document).off('click.accodianCont');
            $(document).on('click.accodianCont', '.treeGroup .dep1', function (e) {
                e.preventDefault();
				$dep2 = $(this).next();
				$toggleBtn = $(this);
                $toggleWrap = $(this).closest('li');
                $dep2.slideToggle(300, 'easeOutCubic', function() {
                	$toggleWrap.toggleClass('treeON');
	            });

            });
        },

    };

    ns.domEvent._toggleTipBox = {
        init: function() {
            //tooltip 토글
            this._bindEvent();
        },
        _bindEvent: function() {
            var tipCont;
            var isVisibleCont;
            //tip 아이콘 클릭시 -> tipCont 내용 토글
            $(document).on('click.toggleTipBox');
            $(document).on('click.toggleTipBox', '.tipClick > a, .tipClick > button', function(e) {
				e.preventDefault();
                tipCont = $(this).closest('.tipClick').find('.tipCont');
                if (tipCont.length === 0) {
                    return;
                }
                isVisibleCont = tipCont.is(':visible');
                //열려 있는 tipCont 모두 닫기
                $('.tipClick .tipCont').hide();
                if (!isVisibleCont) {
                    //기존 tipCont 닫혀 있을 경우 열기
                    tipCont.show();
                }
            });
            //tipCont 닫기 아이콘 클릭시 -> tipCont 내용 숨김
            $(document).on('click.hideTipBox');
            $(document).on('click.hideTipBox', '.tipCont .close', function() {
                tipCont = $(this).closest('.tipCont');
                tipCont.hide();
            });
        }
    };



    /********************************************************
     * 달력 스크립트
     * // 특정 날짜 이전 선택 불가능 (data-min 속성 적용)
     * <input data-min="yy-mm-dd">
     *
     * // 특정 날짜 이후 선택 불가능 (data-max 속성 적용)
     * <input data-max="yy-mm-dd">
     *********************************************************/
    ns.calendar = {
        init: function() {
            if ($('.calenWrap input').length === 0) {
                return;
            }
            this._setElment();
            this._bindEvent();
        },
        _setElment: function() {
            var $jsDatepicker = $('.calenWrap input');
            var oSelf = this,
                $lastFocus,
                $dateWrap;

            $jsDatepicker.each(function() {
                var getDataMin = $(this).data('min') || '-2Y',
                    getDataMax = $(this).data('max') || '+4Y',
                    format   = $(this).data('format') || 'yy-mm-dd',
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
                    dateFormat: format,
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
            $(document).on('click.datepicker', '.calenWrap .ui-datepicker-trigger, .calenWrap input', function(e) {
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

    ns.layerpopup = {
        init: function() {
            this._bindEvent();
        },
        _bindEvent: function() {
            $(window).off('resize.layerPop');
            $(window).on('resize.layerPop', $.proxy(this._centerPos, this)).trigger('resize.layerPop');

            $(document).off('click.openPop').on('click.openPop', '[data-path]', $.proxy(this._loadPop, this));
            $(document).off('click.closePop').on('click.closePop', '#popInner .closeL', $.proxy(this.closePop, this));
        },
        _loadPop: function(e) {
            var fileName = $(e.currentTarget).attr('data-path');
            var filePath = '../popup/' + fileName;
            var popCont;
            var oSelf = this;
            //  //팝업 로드
            $.get(filePath, function(data) {
                popCont = $(data).filter('#layPopup');
                popCont.appendTo('#popInner');
                oSelf.openPop('#popInner');
            });
        },
        openPop: function(_targetId) {
            var $pop = $(_targetId);
            $pop.show();
            $('.dimmed').show();
            this._centerPos();
            this._preventScroll();
        },
        closePop: function(_targetId) {
            var $pop;
            if (_targetId === undefined || _targetId === '') {
                $pop = $(_targetId);
            } else {
                $pop = $('#layPopup');
            }
            $pop.hide();
            $('.dimmed').hide();
        },
        _centerPos: function() {
            //레이어 팝업 가운데 정렬
            var popH;
            $('.layPopup:visible').each(function() {
                popH = $(this).outerHeight();
                $(this).css('margin-top', -parseInt(popH / 2));
            });
        },
        _preventScroll: function() {
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
    };

    ns.alertpopup = {
        init: function() {
            this._bindEvent();
        },
        _bindEvent: function() {
            $(window).off('resize.alertBox');
            $(window).on('resize.alertBox', $.proxy(this._centerPos, this)).trigger('resize.alertBox');
        },
        _centerPos: function() {
            //레이어 팝업 가운데 정렬
            var popH;
            $('.alertBox:visible').each(function() {
                popH = $(this).outerHeight();
                $(this).css('margin-top', -parseInt(popH / 2));
            });
        }
    };

    ns.formEvent = {
        init: function() {
            //selectBox 디자인 태그 추가
            this._createSelBox.init();

            //scrollBar 디자인 태그 추가
            //this._createScrollBar.init();
        },
        resetSelBox: function(_$el, _callBack) {
            //selectBox option 내용 동적으로 변경시 호출
            var $target = _$el;
            var instance = $target.siblings('a').getInstance();
            if (instance) {
                instance.resetSB();
            }
        },
		setDisabledSelBox: function(_$target, isBoolean) {
            //_target 은 셀렉트박스 객체
            var instance = _$target.closest('div').find('a.tit').getInstance();

            if (instance) {
                instance.disabledSB(isBoolean);
            } else {
                _$target.prop('disabled', isBoolean);
            }
        },
        resetScrollBar: function() {
            //scrollbarJs 내용 동적으로 변경시 호출

            $('.scrollbarJs:visible').each(function() {
                if ($(this).data('jsp') !== undefined) {
                    $(this).data('jsp').reinitialise();
                    if (!$(this).data('jsp').getIsScrollableV()) {
                        $(this).find('.jspPane').css('top', 0);
                    }
                }
            });
        }
    };

    ns.formEvent._createSelBox = {
        init: function() {
            this._selElement();
        },
        _selElement: function() {

            if ($.SelectBoxSet !== undefined) {
                var target;
                var direction;
                $('div.selectbox select').each(function() {

                    if ($(this).closest('.selectbox').hasClass('selectTop')) {
                        direction = 'up';
                    } else {
                        direction = 'down';
                    }
                    target = $(this).get(0);
                    $.SelectBoxSet(target, {
                        height: 140,
                        multiText: '|',
                        direction: direction
                    });
                });
            }
        }
    };

    ns.formEvent._createScrollBar = {
        init: function() {
            // .scrollbarJs 클래스에 스크롤 디자인 추가
            var oSelf = this;
            setTimeout(function() {
                oSelf._setElment();
            }, 100);

            this._bindEvent();
            //this.resetScrollBar();
        },
        _setElment: function() {
            var $scrollBar = $('.scrollbarJs:visible');
            var scrollBarH;

            $scrollBar.each(function() {
                scrollBarH = $(this).outerHeight();
                $(this)
                    .on(
                        'jsp-initialised',
                        function(event, isScrollable) {
                            //스크롤 조절
                            if ($(this).find('.jspDrag:visible')) {
                                $(this).find('.jspVerticalBar .jspDrag:visible').each(function() {
                                    if ($(this).closest('.jspVerticalBar').siblings('.jspHorizontalBar').length > 0) {
                                        $(this).css('height', $(this).height() - 5);

                                    } else {
                                        $(this).css('height', $(this).height() - 10);
                                    }

                                });
                            }

                            //테이블 스크롤
                            $('tbody .jspVerticalBar').css('right', 0);

                            //불필요한 태그 삭제
                            $(this).css('max-height', '');
                        }
                    )
                    .jScrollPane({
                        showArrows: false,
                        verticalDragMinHeight: 30,
                        mouseWheelSpeed: scrollBarH
                    });
            });
        },

    };

    ns.showLoading = function(){
    	$(".loadingA").show();
    }
    
    ns.hideLoading = function(){
    	$(".loadingA").hide();
    }

    ns.init();
    return ns;
}(window.COMMON || {}, jQuery));
