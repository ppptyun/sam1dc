/**
 * Paging based on jQuery
 */
function PageNavigation(container_id, display_page_count, on_click_page_function) {
	this.current_page = 1;
	this.container_id = container_id;
	this.display_page_count = display_page_count;
	this.on_click_page_function = on_click_page_function;
}

PageNavigation.prototype.get_current_page = function() {
	return this.current_page;
};

/**
 * 페이지 네비게이션 새로 고침
 * 
 * @param total_item_count 전체 항목 건수
 * @param current_page 현재 페이지
 * @param display_item_count 보여지는 페이지 당 항목 갯수
 * @param display_page_count 보여지는 페이지 갯수
 */
PageNavigation.prototype.refresh = function(total_item_count, current_page, display_item_count, display_page_count) {
	this.current_page = current_page;
	var total_page_count = 0;//전체 페이지 수
	var display_page_begin = 1;//보여지는 시작 페이지
	var display_page_end = 1;//보여지는 마지막 페이지
	var page_prev = false;//이전 페이지 부분 있는지 여부
	var page_next = false;//다음 페이지 부분 있는지 여부
	if (display_page_count == null || display_page_count == defined) {
		display_page_count = this.display_page_count;
	}
	// 페이징 계산
	var total_item_remain = parseInt(total_item_count % display_item_count, 10);
	total_page_count = parseInt(total_item_count / display_item_count, 10);
	if (total_item_remain > 0) {
		total_page_count++;
	}
	var display_page_remain = parseInt(current_page % display_page_count, 10);
	display_page_begin = parseInt(current_page / display_page_count, 10);
	if (display_page_begin > 0 && display_page_remain == 0) {
		display_page_begin--;
	}
	display_page_begin = display_page_begin * display_page_count + 1;
	if (display_page_begin <= 0) {
		display_page_begin = 1;
	}
	display_page_end = display_page_begin + display_page_count - 1;
	if (display_page_end > total_page_count) {
		display_page_end = total_page_count;
	}
	
	page_prev = display_page_begin > display_page_count;
	page_next = total_page_count > display_page_end;
	
	var html = [];
	if (total_item_count <= 0) {
		html.push('<button id="prev" type="button" class="btn_prev" title="이전페이지로">이전</button>');
		html.push('<button id="next" type="button" class="btn_next" title="다음페이지로">다음</button>');
		
	} else {
		html.push('<button id="first" type="button" class="btn_first" title="첫페이지로" clickpage="1">1페이지</button>');
		
		if (page_prev) {
			var previous_page = display_page_begin - display_page_count;
			html.push('<button id="prev" type="button" class="btn_prev" title="이전페이지로" clickpage="' + previous_page + '">이전</button>');
		} else {
			html.push('<button id="prev" type="button" class="btn_prev" title="이전페이지로">이전</button>');
		}
		
		html.push('<div class="btn_paging">');
		for (var i=display_page_begin; i<=display_page_end; i++) {
			if (i == current_page) {
				html.push('<button id="' + i + '" type="button" class="on">' + i + '</button>');
			} else {
				html.push('<button id="' + i + '" type="button" class="" clickpage="' + i + '">' + i + '</button>');
			}
		}
		html.push('</div>');
		
		if (page_next) {
			var next_page = display_page_end + 1;
			html.push('<button id="next" type="button" class="btn_next" title="다음페이지로" clickpage="' + next_page + '">다음</button>');
		} else {
			html.push('<button id="next" type="button" class="btn_next" title="다음페이지로">다음</button>');
		}
		
		html.push('<button id="last" type="button" class="btn_last" title="마지막 페이지로" clickpage="' + total_page_count + '">' + total_page_count + '페이지</button>');
	}
	
	// render html
	jQuery('#'+this.container_id).html(html.join(''));
	
	// trigger click event
	var paging_object = this;
	jQuery('#'+this.container_id + ' button[clickpage]').off('click').on('click', function() {
		var _page = jQuery(this).attr('clickpage');
		paging_object.on_click_page_function(_page);
	});
};