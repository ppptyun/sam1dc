/**
 * @author : Jo Yun Ki (ddoeng@naver.com)
 * @version : 1.5.1
 * @since : 2013.11.12
 *
 * history
 *
 * 1.4 (2013.12.08) : -
 * 1.4.1 (2013.12.16) : $hiddenSelect.val(selectValue) 로 변경을 알리기 때문에 option 태그에 value 값이 동일하면 정상적으로 변경되지 않는 부분 수정
 * 1.4.2 (2014.01.22) : 리스트 a 태그 title 속성 추가
 * 1.4.3 (2014.07.16) :
 *                      multiText 옵션 생성하여 리스트의 텍스트의 구분자를 지정하면 span 태그로 분할 래핑
 *                      changeText() 의 html 인자 추가
 *                      disabledClass 옵션 추가 컨테이너 DIV에 class 적용됩니다.
 *                      zindex 옵션 존재 시 z-index 리스트 열고 닫을 때 변경
 *                      option 태그에 class 속성이 존재 할 경우 위에서 multiText에 의해 나누어진 span 에 차례대로 클래스가 이전된다. 단, span 당 하나의 클래스
 * 1.4.4 (2014.08.14) : 리스트 전체 가로 크기보다 텍스트가 긴 리스트를 선택시 btn 에 영역을 넘어가 버린다 그래서
 *                      css 에서 btnA>span 에 overflow:hidden 을 처리하면 오른쪽이 여유가 없어 btnA>span width 에 5px 를 빼주었다.
 *                      버튼 클릭 시 selectList() 함수 비 실행으로 수정하여 select 태그 change event 발생하지 않게 수정
 * 1.4.5 (2014.08.18) : opts.width 가 btnA에 width 였는데 따로 옵션으로 설정해줄 필요가 없어보여 container의 width 로 변경 하였다.
 * 1.4.6 (2014.09.01) : 완료 시점에 hidden select tag 를 통하여 complete.selectbox 이벤트 발생
 * 1.4.7 (2014.09.11) : 외부에서 메소드로 리스트 변경 시 maskDIV의 display:none으로 인하여 position().top 을 0으로 반환 직접 index로 계산으로 인한 itemHeight 변수 추가
 * 1.4.8 (2014.09.12) : getBorderWidth() 메소드 추가하여 ul 뿐만 아니라 tweenDIV 의 좌우 border로 list의 width 값에 영향을 주게 변경
 *                      btnA의 글자라인이 늘어나면 btnHeight 재정의하여 maskDIV top 위치 조정
 *                      1.4.4 에 삭제된 버튼클릭 시 selectList() 호출 삭제로 인하여 버튼 클릭시 리스트 활성화 안되던것 복구
 * 1.4.9 (2014.09.19) : getBorderHeight() 메소드 추가하여 tweenDIV, maskDIV 에 대한 높이 재정리
 * 1.5 (2014.10.14)   : ul 에 paddingTop, paddingBottom 을 적용하여 상하 여백을 만드는 상황을 대비하여 $maskDIV 의 height 값에 더해주었다.
 * 1.5.1 (2015.04.23) : opts.complete 와 $.SelectBoxSet() 의 3번째 인자 전체 complete 추가
 *
 * Jo Yun Ki에 의해 작성된 SelectBox은(는) 크리에이티브 커먼즈 저작자표시-비영리-동일조건변경허락 4.0 국제 라이선스에 따라 이용할 수 있습니다.
 * 이 라이선스의 범위 이외의 이용허락을 얻기 위해서는 ddoeng@naver.com을 참조하십시오.
 *
 */


/********************************************************************************************/
/***************************************** selectbox ****************************************/
/********************************************************************************************/

/**
* 디자인 셀렉트 박스 (div >> a, div >> ul 구조), (select 태그 기반)
*
* 1.  jQuery 라이브러리 로드후 실행
* 2.  모든 메소드는 document ready 후 실행 하여야 한다.
* 3.  $.SelectBoxSet 를 실행하면 내부적으로 document ready 후 실행한다.
* 4.  select 태그에 onchange 이벤트가 발생하면 디자인 셀렉트 박스에 전달된다. select 의 속성 변화(disabled, selected)는 select 태그에 적용후 .resetSB() 를 실행
* 5.  select 태그가 없는 디자인 셀렉트 박스는 $('target').on('change.selectbox', function (e, param) {} 로 반환값을 전달 받는다.
* 6.  select 태그 기반으로 작성시 반드시 option 태그에 value 속성 존재하여야 한다. 디자인 셀렉트 에선 li 에 data-value 로 저장된다.
* 7.  지정 높이가 리스트보다 작으면 자동 스크롤바 생성(http://jscrollpane.kelvinluck.com/)
* 8.  1.4 버전에서 IE6 지원하지 않음
* 9.  리스트의 가로 사이즈를 변경하고자 한다면 css .maskDiv_wddo 의 padding-right 나 margin-right 로 조절한다.
* 10. 스크롤의 여백 조절은 좌.우 는 css .jspVerticalBar 의 left:0;padding-left, right:0;padding-right 를 조절하며 상단 하단 여백 높이는 css .jspCapTop .jspCapBottom 의 height 로 조절
* 11. 리스트를 2줄이상으로 보이게 하려면 css white-space: nowrap; 삭제
* 12. 리스트의 상하 여백은 padding-top, padding-bottom 으로 조절한다.
*
* ex)
*
*   1. 한꺼 번에 적용
*
*       $.SelectBoxSet('select', {height: 200}); // select 태그 기반으로 할 경우
*
*       - target (jQuery Selectors)
*               경우1. select 태그
*               경우2. div >> a, div >> ul 구조의 상위 DIV (UL > LI 에 data-value 속성 없으면 자동 pulldown menu 형태)
*
*   2. 하나씩 적용
*
*       var options = {
*           height: 200
*       };
*
*       var selectBox = new SelectBox('div.select', options); // select 태그 기반이 아닌 경우엔 div.select 를 target으로 삼는다.
*
*       $(function () {
*           selectBox.initSB();
*       });
*
*   3. 옵션 (: 기본값)
*
*       var option = {
*           width: undeinfed,           //셀렉트 가로 크기
*           height: undefined,          //스크롤바 한계높이
*           direction: 'down',          //열리는 방향 'up'|'down'
*           speed: 250,                 //기본 속도. 닫힐땐 이보다 절반의 속도로 닫힘
*           aClass: 'on',               //LI의 A 태그 오버효과를 담당할 클래스 명
*           divClass: 'select',         //컨테이너 DIV 태그 클래스 명
*           btnClass: 'tit',            //클릭할 A 태그의 클래스명
*           conClass: 'overcon',        //UL 감싸고 있는 DIV 클래스명
*           ulClass: 'con',             //UL 클래스명
*           disabledClass: undefined    //비활성화 클래스
*           autoClose: false,           //pulldown 인 경우 리스트에서 포커스가 빠지면 자동으로 닫힐지 유무
*           multiText: undefined,       //텍스트에 구분자를 지정하여 <span 태그로 래핑
*           zindex: 840212,             //열릴 때 z-index 값
*           complete : undefined        //생성완료 콜백 함수
*       };
*
*   4. 메서드
*
*       var instance = $('div.select').getInstance();
*
*       .getTarget();               //jQueryObject 형태로 컨테이너 DIV 반환
*       .getInfoSB();               //정보반환, change 이벤트의 param 와 같음
*       .setIndexSB(idx);           //index로 선택
*       .getIndexSB();              //index 반환
*       .setValueSB(value);         //value로 선택
*       .getValueSB()               //value 반환
*       .setTextSB(value);          //text로 선택
*       .getTextSB();               //text 반환
*       .disabledSB(boolean);       //disabled
*       .removeSB();                //디자인 셀렉트 삭제
*       .resetSB();                 //select 태그를 토대로 재적용
*
*   5. 이벤트
*
*       //디자인 기반의 셀렉트 박스
*       $('.select').on('change.selectbox', function (e, param) {
*           console.log('change index: ' + param.index);
*           console.log('change value: ' + param.value);
*           console.log('change text: ' + param.text);
*       });
*
*       //기본 셀렉트박스
*       $('select').on('change.selectbox', function (e) {
*           console.log($(this).val());
*       })
*
*   6. HTML
*
*    <div class="select">
*       <a href="#" class="tit"><span>선택해주세요</span></a>
*       <div class="overcon">
*           <ul class="con">
*               <li><a href="#" data-value="list1_0">선택하세요</a></li>
*               <li><a href="#" data-value="list1_1">리스트1</a></li>
*               <li><a href="#" data-value="list1_2">리스트2</a></li>
*               <li><a href="#" data-value="list1_3">리스트3</a></li>
*           </ul>
*           <div class="selectL"></div>
*           <div class="selectR"></div>
*       </div>
*   </div>
*
*   7. CSS(기본 템플릿)
*
*       .select {position:relative;}
*       .select .tit {display:block;height:33px;background:url(img/common/bg_selectL.png) no-repeat left top;}
*       .select .tit > span {display:inline-block;overflow:hidden;margin-left:15px;padding:10px 40px 10px 0;height:13px;background:url(img/common/bg_selectR.png) no-repeat right top;white-space: nowrap;}
*       .select .tit.on {background:url(img/common/bg_selectLon.png) no-repeat left top;}
*       .select .tit.on > span {background:url(img/common/bg_selectRon.png) no-repeat right top;}
*       .select .overcon .con {padding:5px 0 15px;border-left:1px solid #ccc;border-right:1px solid #ccc;border-bottom:1px solid #ccc;background:#fff;}
*       .select .overcon .con a {display:block;padding:5px 15px 5px 15px;white-space: nowrap;}
*       .select .overcon .con a.on {background:#f1f1f1;}
*       .select .overcon .selectL {width:16px;height:16px;background:url(img/common/bg_selectconL.gif) no-repeat;}
*       .select .overcon .selectR {width:16px;height:16px;background:url(img/common/bg_selectconR.gif) no-repeat;}
*
*       .jspContainer{overflow:hidden;position:relative;}
*       .jspPane{position:absolute;}
*       .jspVerticalBar{position:absolute;top:0;right:0;width:8px;height:100%;background:red;}
*       .jspTrack{background:#ccc;position:relative;}
*       .jspDrag{background:#999;position:relative;top:0;left:0;cursor:pointer;}
*       .jspCapTop{height:0px;}
*       .jspCapBottom{height:0px;}
*
*/

var SelectBox = (function ($) {
    var wddoObj = function (_target, _options) {
        var scope,                      //현 함수의 인스턴트
            $container,                 //디자인 selectBox와 히든 input 을 담고 있는 컨테이너
            $target,                    //div >> a, ul 형태를 가지고 있는 DIV
            $btnA,                      //열고 닫기를 할 버튼 a
            $listCon,                   //리스트 ul 감싸고 있는 DIV
            $listUL,                    //리스트 ul
            $listA,                     //li 속 a
            $roundDIV,                  //ul 하단 라운드 레이아웃 담당하는 DIV x2
            $maskDIV,                   //트윈 DIV 를 감싸고 영역밖으로 overflow:hidden; 인 마스크 DIV
            $tweenDIV,                  //트윈이 일어나는 DIV
            $hiddenSelect,              //숨겨진 <select> 태그
            itemWidth,                  //리스트안에 li 아이템 넓이
            itemHeight,                 //리스트안에 li 아이템 높이
            listWidth,                  //리스트 최종적 넓이(ul tweenDiv border 포함)
            listHeight,                 //리스트의 스크롤링 되지 않은 최종적 높이
            btnWidth,                   //열고 닫기를 할 버튼의 넓이
            btnHeight,                  //열고 닫기를 할 버튼의 높이
            isValue,                    //<select> 형태일지 pulldown 형태일지 결정할 변수
            isScroll = false,           //jScroll 적용시 높이보다 리스트가 작으면 스크롤이 생기지 않는것 감안하여 스크롤바가 있는지 없는지 유무
            isDisabled = false,         //<select> tag 에 disabled 되었는지 유무
            isEvent = false,            //이벤트가 적용되었는지 유무
            opts,
            defaults = defaultOptions(),//기본값
            selectIndex,                //selectbox 의 인덱스 넘버
            selectText,                 //selectbox 의 레이블명
            selectValue,                //selectbox 의 숨은 값 <li data-value:
            jscroll,                    //jScroll 인스턴트
            zindex;                     //$target 부모의 zindex 값

        function defaultOptions() {
            return {
                height: undefined,
                width: undefined,
                direction: 'down',
                speed: 250,
                aClass: 'on',
                divClass: 'select',
                btnClass: 'tit',
                conClass: 'overcon',
                ulClass: 'con',
                disabledClass: 'disabled',
                autoClose: false,
                multiText: undefined,
                zindex: 1000,
                complete: undefined
            };
        }

        //init
        function init() {
            opts = $.extend(defaults, _options);

            createLayout();

            $container = $target.parent();
            $btnA = $target;
            $listCon = $target.next('div:eq(0)');
            $listUL = $listCon.find('ul');
            $listA = $listUL.find('li > a');
            $roundDIV = $listCon.find('> div');

            isValue = ($listA.data('value') !== undefined) ? true : false;
        }

        //<select> tag choose
        function createLayout() {
            if ($target.is('select')) {
                $hiddenSelect = $target;
                $hiddenSelect.data('select', true);
                var con, ul, li, a;
                var title = ($target.attr("title") !== undefined) ? $target.attr("title") : '';
                var text, cls, htmlText, textArr, classArr;

                //$target : <select> tag -> <div> tag
                var addTag = "";
                var optsDivClass = "";
                var optsBtnClass = "";
                var $tempTarget = "";
                var oImg = "";
                optsDivClass = opts.divClass;
                optsBtnClass = opts.btnClass;
                addTag = '<a href="#" class="' + optsBtnClass + '" title="' + title + '" role="button">'+title+'</a>';

                //$target = $(addTag).insertBefore($hiddenSelect);
                //$target = $hiddenSelect.before(addTag).prev();

                //$hiddenSelect.before(addTag).prev();
                //$target = $hiddenSelect.prev();
                try {
                    $hiddenSelect.parent().prepend($(addTag));
                } catch(e) {
                    $hiddenSelect.parent().prepend(addTag);
                }

                $target = $hiddenSelect.prev();
                con = $target.after('<div class="' + opts.conClass + '"></div>').next('div');
                ul = con.append('<ul class="' + opts.ulClass + '"></ul>').find('ul');
                //con.append('<div class="selectL"></div><div class="selectR"></div>').find('> div'); //round layout

                $hiddenSelect.find('option').each(function () {

                    if($(this).attr('hidden') === 'hidden'){
                        li = ul.append('<li style="display:none"><a href="#none"></a></li>').find('li:last');
                    }else{
                        li = ul.append('<li><a href="#none"></a></li>').find('li:last');
                    }
                    a = li.find('> a');
                    text = $(this).text();
                    textArr = (opts.multiText === undefined) ? [text] : text.split(opts.multiText);
                    classArr = ($(this).attr('class') === undefined) ? [] : $(this).attr('class').split(' ');
                    htmlText = '';

                    //이미지 태그
                    if($(this).data('img') !== undefined){
                        oImg = $(this).data('img');

                        if(oImg.type !== ''){
                            li.addClass(oImg.type)
                        }
                    }
                    //multi text
                    //alt 삭제
                    $(textArr).each(function (idx){
                        //img add
                        if(idx === 0 && oImg !== ''){
                            htmlText += '<img src="'+oImg.src+'" alt="" type="'+oImg.type+'">';
                        }
                        //cjh 2018-03-22 :  구분자 다음 부터 span 태그로 wrap
                        if(htmlText !== ''){
                          textArr[idx] = '<span>'+textArr[idx]+'</span>';
                        }
                        cls = (classArr[idx] !== undefined) ? classArr[idx] : '';
                        htmlText += ( (classArr.length !== 0) ? ' class="' + cls + '"' : '' ) + textArr[idx] ;
                    });

                    a.html(htmlText).data('value', $(this).attr('value'));
                    //CJH - 2018-07-12 span 여러개 일 경우에 span 으로 wrap
                    a.find('span').wrapAll('<span></span');
                    //img error
                    var hostImg = '';
                    // a.find('>img').error(function(){
                    //     if(typeof(gvImgUrl)  !== "undefined" && gvImgUrl  !== ''){
                    //         hostImg = gvImgUrl;
                    //     }else{
                    //         hostImg = 'http://dimage.lottecard.co.kr';
                    //     }
                    //     $(this).attr('src', hostImg+'/webapp/pc/images/common/img_cardDefault.jpg');
                    // });

                });

                $hiddenSelect.css('display', 'none');
            }
        }

        //init layout
        function initLayout() {
            //width - width();
            //height - outerHeight();
            //roundDIV exception

            zindex = $container.css('z-index');
            $hiddenSelect.parents().filter(function () {
                return $(this).css('display') === 'none'
            }).show().addClass('unhide_wddo');
            $listCon.css('display', 'block');
            $tweenDIV = $listCon.wrap('<div class="maskDiv_wddo" style="*position: relative;"><div class="tweenDiv_wddo">').parent();
            $maskDIV = $tweenDIV.parent();

            //display:none 없는 첫법째 태그 반환
            var li = $listUL.find('> li').filter(function(){
                return $(this).css('display') !== 'none'
            }).first();

            var bwListUL = getBorderWidth($listUL);
            var bwTweenDIV = getBorderWidth($tweenDIV);
            var bhListUL = getBorderHeight($listUL);
            var bhTweenDIV = getBorderHeight($tweenDIV);

            //container //add 1.4.5
            if (opts.width === undefined) opts.width = $container.width();

            //cjh - container width remove
            // $container.css('width', opts.width); //fix selectbox width

            //btn
            btnWidth = $btnA.width();
            btnHeight = $btnA.outerHeight();

            //btn A > span blank // - 5 add 1.4.4
            var btnAMultiLine = $btnA.find('> span').css('white-space') !== 'nowrap';
            var btnABlank = (opts.width !== undefined) ? 5 : 0;
            //$btnA.find('> span').css(((!btnAMultiLine) ? 'width' : 'paddingRight'), ((!btnAMultiLine) ? (btnWidth - btnABlank) : btnABlank));

            //CJH 2018-03-22 : 값 구하기 위해 show
            $maskDIV.show();

            //item //add 1.4.7
            itemWidth = li.width();
            itemHeight = li.outerHeight();

            //list
            var listLength = $listUL.children().filter(function(){
                return $(this).css('display') !== 'none'
            }).length;
            listWidth = itemWidth + bwListUL + bwTweenDIV;
            listHeight = (listLength * itemHeight) + bhListUL; //$listUL.outerHeight(); crossbrowser error

            $roundDIV.css({
                'position' : 'relative',
                'marginTop' : -$roundDIV.outerHeight()
            }).filter(':eq(1)').css({'marginLeft' : listWidth - $roundDIV.outerWidth()});

            $tweenDIV.css({
                'height' : listHeight
            });
            $maskDIV.css({
                // 'position' : 'absolute',
                'overflow' : 'hidden',
                // 'left': '0',
                // 'top' : btnHeight,
                // 'width' : listWidth,
                'height' : listHeight + parseInt($listUL.css('paddingTop')) + parseInt($listUL.css('paddingBottom')) //list top&bottom space //add 1.5
            });

            //IE7-
            IE7Fix();

            //scroll apply
            addScroll();

            //add scroll : layout reset
            if (isScroll) {
                itemWidth = li.width();
                listWidth = itemWidth + bwListUL + bwTweenDIV;
                $roundDIV.filter(':eq(1)').css({'marginLeft' : listWidth - $roundDIV.outerWidth()});
                $maskDIV.css('height', opts.height);
                $tweenDIV.css('height', opts.height);
            }

            //direction
            modifyDirection();
        }

        //get border left, right //add 1.4.8
        function getBorderWidth(_target) {
            //ie8- css 'border-left-width' 'border-right-width' default value 'medium'
            var borderLeftWidth = parseInt(_target.css('border-left-width')) | 0;
            var borderRightWidth = parseInt(_target.css('border-right-width')) | 0;

            return borderLeftWidth + borderRightWidth;
        }

        //get border top, bottom //add 1.4.9
        function getBorderHeight(_target) {
            //ie8- css 'border-top-width' 'border-bottom-width' default value 'medium'
            var borderTopHeight = parseInt(_target.css('border-top-width')) | 0;
            var borderBottomHeight = parseInt(_target.css('border-bottom-width')) | 0;

            return borderTopHeight + borderBottomHeight;
        }

        //IE7 fixed
        function IE7Fix() {
            var agt= navigator.userAgent.toLowerCase();

            if (agt.indexOf("msie 7" ) != -1) {
                $btnA.css('cursor', 'pointer');
            }
        }

        //add scroll-pane
        function addScroll() {
            if (opts.height !== undefined && opts.height < listHeight) {
                var pane = $listCon.show().wrap('<div class="scroll-pane"></div>').parent();

                pane.css({
                    'overflow' : 'auto',
                    'height' : opts.height
                });
                $(pane).find('.'+opts.conClass).css('width', pane.css('width'));
                pane.on('jsp-initialised', function (e, _isScroll) {
                    isScroll = _isScroll;

                    if (isScroll) {
                        //non .jspPane scroll padding
                        var jspPane = pane.find('.jspPane');
                        var bwTweenDIV = getBorderWidth($tweenDIV);

                        jspPane.css('width', listWidth - bwTweenDIV);
                    }
                });

                var dragH;
                //create jscroll
                jscroll = pane
                .on(
                    'jsp-initialised',
                    function(event, isScrollable){
                        //스크롤 최소 높이 20px 지정
                        dragH  = $(this).find('.jspDrag').height() - 21;
                        if(dragH < 20){
                            dragH = 20;
                        }



                        //스크롤 조절
                        $(this).find('.jspTrack').removeAttr('style');
                        $(this).find('.jspDrag').css('height', dragH);

                        //불필요한 태그 삭제
                        $(this).find('.jspArrow').remove();
                        $(this).find('.jspCap').remove();
                        $(this).find('.jspHorizontalBar').remove();
                    }
                )
                .jScrollPane({
                    showArrows: false,
                    verticalDragMinHeight: 30,
                    mouseWheelSpeed: itemHeight
                });
                pane.attr('tabIndex', 0);
            }
        }

        //add direction
        function modifyDirection() {
            if (opts.direction !== 'down') {
               $maskDIV.css({
                    'marginTop': -$maskDIV.outerHeight() - btnHeight
               });
            }
        }

        function open(_isOpen, _speed) {
            var speed = (_speed !== undefined) ? _speed : ((_isOpen) ? opts.speed : opts.speed / 2);
            var direction = (opts.direction === 'down') ? -1 : 1;

            if (_isOpen) $container.css('z-index', opts.zindex);

            if ($tweenDIV.length !== 0) {
                var top = (_isOpen) ? 0 : $maskDIV.outerHeight() * direction; //open 0

                if (speed > 1) {
                    $maskDIV.add($listCon).show();
                }//else default close

                $tweenDIV.stop().animate({
                    'marginTop': top
                },{queue:false, duration:speed, complete:comp});
            }

            //complete
            function comp () {
                if (!_isOpen) {
                    $maskDIV.hide();
                    removeDocumentEvent();
                    $btnA.removeClass(opts.aClass);
                    // $container.css('z-index', zindex); CJH : z-index 지정 삭제
                    $container.css('z-index', '');
                } else {
                    addDocumentEvent();
                }
            }
        }

        //document event
        function addDocumentEvent() {
            $(document).on('click.selectbox', function (e) {
                var currentTarget = $(e.currentTarget);
                var target = $(e.target);
                var isOpen = ($target.data('state') === 'open');

                if (!target.hasClass('jspDrag') && isOpen) {
                    changeBtnState(false);
                }
            });
        }

        function removeDocumentEvent() {
            $(document).off('click.selectbox');
        }

        //event
        function initEvent() {
            isEvent = true;
            //btn a handler
            $btnA.on('click.selectbox', function (e) {
                var target = $(e.currentTarget);
                var toggle = ($target.data('state') !== 'open'); //true : open
                changeBtnState(toggle); //open & close
                //if (selectIndex !== undefined && toggle) selectList($listA.eq(selectIndex)); //list on, if open // delete 1.4.4
                if (selectIndex !== undefined && toggle) $listA.eq(selectIndex).triggerHandler('mouseover.selectbox'); // add 1.4.8
                if ((!isValue && jscroll !== undefined) && toggle) changeScroll(0); //if pulldown menu & jscroll

                e.preventDefault();
            });


            //<select> version
            if (isValue) {
                $btnA.add($listA).on('keydown.selectbox', function (e) {
                    var target = $(e.currentTarget);
                    var keyCode = e.keyCode;
                    var idx = scope.getIndexSB() || 0;
                    var altOpen = (e.altKey && (keyCode == 40 || keyCode == 38));
                    var isOpen = ($target.data('state') === 'open');

                    //alt open
                    if (altOpen && !isOpen) {
                        changeBtnState(true, 400);
                        selectList($listA.eq(idx));

                        e.preventDefault();

                        return false;
                    }

                    //접근성 추가 -  엔터키 셀렉트 박스 열기 / 닫기
                    if( (keyCode === 13) && target.is($btnA)){
                        e.preventDefault();
                        $(this).trigger('click');
                    }

                    //enter, btn focus
                    //접근성 추가 - 스페이스(셀렉트 박스 열기)
                    if (keyCode === 32 && target.is($btnA)) {
                        e.preventDefault();
                        if (!isOpen) changeBtnState(true);

                        e.preventDefault();
                    }

                    //tab, is open
                    if (keyCode === 9 && isOpen) {
                        e.preventDefault();
                        changeBtnState(false);

                        e.preventDefault();
                    }

                    //end, pagedown
                    if (keyCode === 35 || keyCode === 34) {
                        idx = $listA.length - 1;

                        selectList($listA.eq(idx));

                        e.preventDefault();
                    }

                    //down, right
                    if (keyCode === 40 || keyCode === 39) {
                        if (idx < $listA.length - 1 && scope.getIndexSB() !== undefined) idx += 1; //not label

                        selectList($listA.eq(idx));

                        e.preventDefault();
                    }

                    //home, pageup
                    if (keyCode === 36 || keyCode === 33) {
                        idx = 0;

                        selectList($listA.eq(idx));

                        e.preventDefault();
                    }

                    //up, left
                    if (keyCode === 38 || keyCode === 37) {
                        if (idx > 0) idx -= 1;

                        selectList($listA.eq(idx));

                        e.preventDefault();
                    }
                });

                if ($hiddenSelect !== undefined) {
                    //<select> tag change -> selecbox change
                    $hiddenSelect.bind('change.selectbox', function (e) {
                        //selectList() copy
                        var target = $listA.eq($hiddenSelect[0].selectedIndex);
                        var text = target.text();
                        var html = target.html();
                        var value = target.data('value');
                        var idx = target.closest('li').index();

                        if (isDisabled) return false;

                        selectIndex = idx;
                        
                        $listA.eq(selectIndex).triggerHandler('mouseover.selectbox');
                        
                        changeText(text, html);
                        changeValue(value);
                        changeScroll();
                    });
                }

            } else {
            //pulldown menu version
                if (opts.autoClose) {
                   $listA.on('focusin.selectbox', function (e) {
                        changeBtnState(true);
                   });

                   $listA.on('focusout.selectbox', function (e) {
                        changeBtnState(false);
                   });
                }
            }

            //list a handler
            $listA.on('click.selectbox', function (e) {
                var target = $(e.currentTarget);

                if (isValue) {
                    e.preventDefault();

                    selectList(target);
                    changeBtnState(false);
                    $btnA.focus();
                }
            });

            $listA.on('mouseover.selectbox focusin.selectbox', function (e) {
                var target = $(e.currentTarget);
                var idx = target.closest('li').index();

                //all remove class form list <A> & add class
                $listA.removeClass(opts.aClass).eq(idx).addClass(opts.aClass);
            });
        }

        function removeEvent() {
            isEvent = false;

            $listA.off('.selectbox');
            $btnA.off('.selectbox');
            $(document).off('.selectbox');
            if ($hiddenSelect !== undefined) $hiddenSelect.off('.selectbox');
        }

        //init attribute from <select>
        function initAttribute() {
            //select first
            if ($hiddenSelect !== undefined) {
                var selectedIdx = ($hiddenSelect.find('> option:selected').length > 0) ? $hiddenSelect.find('> option:selected').index() : 0;

                //scope.setIndexSB(selectedIdx);
                changeBtnState(true, 0);
                selectList($listA.eq(selectedIdx), 1);
                changeBtnState(false, 0);

                isDisabled = $hiddenSelect.prop('disabled');

                scope.disabledSB(isDisabled);

                $hiddenSelect.trigger('complete.selectbox'); // add 1.4.6
            } else {
                scope.disabledSB(false);
            }
        }

        //select list & close
        function selectList(_target, _first, _isChange) {
            var target = _target;
            var text = target.text();
            var html = target.html();
            var value = target.data('value');
            var idx = target.closest('li').index();
            var isFirst = _first || 0;
            var isOptChange = (isFirst === 1) || ((selectIndex !== undefined) && (selectIndex !== idx)); // CJH : option 값 변경  여부
            var _isTriggerChange;

            // CJH : 2018-07-24 값 건택 동시에 disable 처리 케이스 고려
            // if (isDisabled) return false;
            if (isValue) {
                //idx save
                selectIndex = idx;

                $listA.eq(selectIndex).triggerHandler('mouseover.selectbox');

                changeText(text, html);
                changeValue(value);
                changeScroll();

                if (!isFirst){
                    _isTriggerChange  = (_isChange !== false && isOptChange);
                    changeHiddenSelect(_isTriggerChange);
                }
            } else {
                changeScroll(0); //init scroll ypos
            }

            //event binding
            $target.trigger('change.selectbox', scope.getInfoSB());
        }

        //change btn state
        function changeBtnState(_value, _speed) {
            var speed = _speed;
            //add 2016-05-30
            btnHeight = $btnA.outerHeight();
            // if (opts.direction === 'down') $maskDIV.css('top', btnHeight);

            if (isDisabled) return false;

            $listA.removeClass(opts.aClass);

            if (_value) { //open
                $target.data('state', 'open');
                open(true, speed);
                $btnA.addClass(opts.aClass);
            } else { //close
                $target.removeData('state');
                open(false, speed);
                //$btnA.removeClass(opts.aClass); //code move: motion complete
            }

            if ($target.parent().find('.jspDrag').length > 0 &&
                $target.closest('.selectbox').attr('style') !== undefined &&
                $target.closest('.selectbox').attr('style').indexOf('width') === -1
            ) {
                //셀렉트 박스 width 고정 값 아닐 경우 동적으로 적용
                var changeW = $target.closest('.selectbox').find('a.tit').outerWidth();
                $target.closest('.selectbox').find('.jspScrollable').css('width', changeW);
                $target.closest('.selectbox').find('.jspContainer').css('width', changeW);
                $target.closest('.selectbox').find('.jspPane').css('width', changeW);
            }
        }

        //text change
        function changeText(_txt, _html) {
            // CJH - 2018-03-22 세로타입 케이스 경우 클래스 추가
            if(_html.match('typeH')){
                $btnA.addClass('typeH');
            }else{
                $btnA.removeClass('typeH');
            }
            var htmlText = $('<span>'+_html+'</span>').text();
            if($btnA.data('title') === undefined){
                $btnA.data('title', $btnA.attr('title'));
            }
            $btnA.attr('title', htmlText + ' ' + $btnA.data('title'));
            $btnA.html(_html);
            //btnA multiline //add 1.4.8
            btnHeight = $btnA.outerHeight();
            // if (opts.direction === 'down') $maskDIV.css('top', btnHeight);

            //img error
            var hostImg = '';
            // $btnA.find('>img').error(function(){
            //     if(typeof(gvImgUrl)  !== "undefined" && gvImgUrl  !== ''){
            //         hostImg = gvImgUrl;
            //     }else{
            //         hostImg = 'http://image.lottecard.co.kr';
            //     }
            //
            //     $(this).attr('src', hostImg+'/webapp/pc/images/common/img_cardDefault.jpg');
            //
            // });

            selectText = _txt;
        }

        //value change
        function changeValue(_value) {
            selectValue = _value;
        }

        //scroll move
        function changeScroll(_ypos) {
            if ($target.parent().find('.jspDrag').length > 0) {
                var ypos = (_ypos !== undefined) ? _ypos : itemHeight * selectIndex;

                jscroll.data('jsp').scrollToY(ypos);
            }
        }

        //<select> tag change
        //selecbox change -> <select>
        function changeHiddenSelect(_isTriggerChange) {
            if ($hiddenSelect !== undefined) {
                if(_isTriggerChange){
                    $hiddenSelect.find('option').removeAttr('selected').eq(selectIndex).prop('selected', true).end().end().trigger('change.selectbox');
                }else{
                    $hiddenSelect.find('option').removeAttr('selected').eq(selectIndex).prop('selected', true);
                }
            }
        }

        //tab
        var getTab = function (keydownEvent) {
            var e = keydownEvent,
                isDown;

            if (e.keyCode === 9) {
                isDown = (e.shiftKey) ? false : true;
            } else {
                isDown = undefined;
            }

            return isDown;
        };

        //public
        //init
        this.initSB = function () {
            var $this = this;
            setTimeout(function(){
                $target = (_target.jquery === undefined) ? $(_target) : _target;

                if ($target.length > 0) {

                    if ($target.is('select') && $target.data('select') !== undefined) return false;  //avoid duplication
                    //if ($target.is('select') && $target.prev('.select').length > 0 ) return false;  //avoid duplication
                    scope = $this;


                        init();
                        initLayout();

                        //scope
                        $target.data('scope', scope);

                        initAttribute();

                        //CJH - 2018-03-27 : display noe remove
                        $hiddenSelect.parents('.unhide_wddo').each(function(){
                            $(this).css('display', '');
                            if($(this).css('display') !== "none"){
                                $(this).css('display', 'none');
                            }
                        });
                        $hiddenSelect.parents('.unhide_wddo').removeClass('unhide_wddo');

                        if (opts.complete !== undefined && typeof opts.complete === 'function') opts.complete();

                }
            },1);
        };

        //reset
        this.resetSB = function () {
           setTimeout(function(){
                scope.removeSB();
                scope.initSB();
            },1);
        };

        //dispose
        this.removeSB = function () {
            setTimeout(function(){
                //remove animate infomation
                $listCon.parent('div').stop().css('marginTop', '');

                //remove scroll source
                if (jscroll !== undefined) {
                    jscroll.data('jsp').destroy();
                    $listCon.unwrap($('div'));
                }

                //remove mask source
                $listCon.unwrap($('div')).unwrap($('div'));

                //cjh reset add
                $listCon.siblings('a').remove();
                $listCon.remove();

                //remove event
                removeEvent();

                //init
                selectIndex = undefined;
                selectText = undefined;
                selectValue = undefined;
                jscroll = undefined;
                zindex = undefined;

                $target.removeData('scope');
                $target.removeData('state');
                $listA.removeClass(opts.aClass);
                $btnA.removeClass(opts.aClass);
                $listCon.hide();

                if ($hiddenSelect !== undefined) {
                    $hiddenSelect.css('display', '');
                    $hiddenSelect.siblings('div.' + opts.divClass).remove();
                    $hiddenSelect.removeData('select');
                }

                //scope = undefined;
                $container = undefined;
                $target = undefined;
                $btnA = undefined;
                $listCon = undefined;
                $listUL = undefined;
                $listA = undefined;
                $roundDIV = undefined;
                $maskDIV = undefined;
                $tweenDIV = undefined;
                $hiddenSelect = undefined;
                itemWidth = undefined;
                itemHeight = undefined;
                listWidth = undefined;
                listHeight = undefined;
                btnWidth = undefined;
                btnHeight = undefined;
                isValue = undefined;
                isScroll = false;
                isDisabled = false;
                isEvent = false;
                opts = undefined;
                defaults = defaultOptions();
            },1);
        };

        //return target
        this.getTarget = function () {
            return $target;
        };

        //return infomation
        this.getInfoSB = function () {
            return {
                index: selectIndex,
                value: selectValue,
                text: selectText
            };
        };

        //set get index
        this.setIndexSB = function (_idx) {
            setTimeout(function(){
                changeBtnState(true, 0);
                selectList($listA.eq(_idx), 0, false);
                changeBtnState(false, 0);
            },1);
        };

        this.getIndexSB = function () {
            return selectIndex;
        };

        //set get value
        this.setValueSB = function (_value) {
            var oSelf = this;
            setTimeout(function(){
                var idx = 0;
                $listA.each(function () {
                    if ($(this).data('value') === _value) {
                        idx = $(this).closest('li').index();
                        return false;
                    }
                });

                oSelf.setIndexSB(idx);
            },1);
        };

        this.getValueSB = function () {
            return selectValue;
        };

        //set get text
        this.setTextSB = function (_value) {
            setTimeout(function(){
                var idx;
                $listA.each(function () {
                    if ($(this).text() === _value) {
                        idx = $(this).closest('li').index();
                        return false;
                    }
                });
                this.setIndexSB(idx);
            },1);
        };

        this.getTextSB = function () {
            return selectText;
        };

        //set disabled
        this.disabledSB = function (_value) {
            var target = $target.children().eq(0); //btnA
            var txt = target.text();
            var cls = target.attr('class');

            //first
            changeBtnState(false, 0);    //default close
            if (_value) {
                (opts.disabledClass !== undefined) ? $target.addClass(opts.disabledClass) : $target.css('opacity', 0.5);
                //2018-04-24 CJH aria add
                $target.attr('aria-disabled', true);

                if ($hiddenSelect !== undefined) $hiddenSelect.prop('disabled', true);

                //CJH - 2018-06.28 : disabled span 다시 그리는 부분 생략
                //if (!isDisabled) target.css('display', 'none').after('<span class="' + opts.btnClass + '"><span style="width:' + target.find('> span').width() + 'px">'+target.text()+'</span></span>');
                // if (!isDisabled) target.css('display', 'none').after('<span class="' + opts.btnClass + '"><span>'+target.text()+'</span></span>');;

                if (isEvent) removeEvent();

                //disable btn - preventDefault
                $btnA.off('click.btn');
                $btnA.on('click.btn', function(e){
                    e.preventDefault();
                });
            } else {
                (opts.disabledClass !== undefined) ? $target.removeClass(opts.disabledClass) : $target.css('opacity', 1);

                if ($hiddenSelect !== undefined) $hiddenSelect.removeAttr('disabled');
                //CJH - 2018-06.28 : disabled span 다시 그리는 부분 생략
                // if (isDisabled) target.css('display', '').siblings('span').remove();

                if (!isEvent) initEvent();
            }

            isDisabled = _value;
        };

    };//end Obj

    return wddoObj;
}(jQuery));

//get instance
jQuery.fn.getInstance=function(){return this.data('scope');};

//multiple init
jQuery.SelectBoxSet = function (_target, _options, _complete) {
    var complete = _complete || function () {};
    $(_target).each(function (idx) {
        var selectBox = new SelectBox($(this), _options);
        selectBox.initSB();
    });

    complete();
    
    //document ready
    /*$(document).ready(function () {
        $(_target).each(function (idx) {
            var selectBox = new SelectBox($(this), _options);
            selectBox.initSB();
        });

        complete();
    });*/
};
