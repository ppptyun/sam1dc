const EVENT_BUS = new Vue({
	data: {
		selectBoxList:[],
		paramIds:[],
	},
});	// Global Event 관리

const popEmp = Helper.Popup("employee", "임직원 선택", "/popup/org");
const popMainProject = Helper.Popup("main-project", "Project Select", "/popup/project-v2", {size:'M', isMain:"Yes"});
const popProject = Helper.Popup("project", "Project Select", "/popup/project-v2", {size:'M'});
const popAdtr = Helper.Popup("adtr", "감사인 선택", "/popup/auditorList", {height:'600px', width:'700px'});
const popSimpleWkmnsp = Helper.Popup("wkmnsp", "숙련도 간단 계산", "/popup/simplewkmnsp", {height:'480px', width:'700px'});
const popSatCalc = Helper.Popup("satcalc", "표준감사시간 선택", "/popup/satcalc", {size:'L'});



// Component - 인원 정보
Vue.component('emp', {
	props: {
		id: {
			type: String,
			required: true
		},
		value: Object,
		isRead: {type: Boolean,default: false}
	},
	template: '<div v-if="!isRead" class="frmNowrap">'
			+ '<input v-model="empInfo" type="text" class="frmW01" readonly>'
			+ '<button @click.stop="onDeleteEmp" type="button" class="inpDel">삭제</button>'
			+ '<button @click.stop="onPopupEmp" type="button" class="btIco btnSearch02 gapL">임직원 검색</button>'
			+ '</div>'
			+ '<span v-else>{{empInfo}}</span>',
	computed: {
		empInfo: function(){
			return this.value.emplno?this.value.kornm + "(" + this.value.emplno + ") " + this.value.gradnm:"";
		},
	},
	methods: {
		onDeleteEmp: function(){
			this.$parent[this.id] = {}
			
		},
		onPopupEmp: function(){
			let thisObj = this;
			popEmp.openPopup("emp-" + this.id, function(data){
				thisObj.$parent[thisObj.id] = data
			});
		},
	}
})

Vue.component('calendar', {
	model: {
		prop: 'value',
		event: 'change'
	},
	props: {
		id: {
			type: String,
		},
		maxDate: [String, Object],
		minDate: [String, Object],
		value: String,
		format: {
			type: String,
			default: "yyyy-mm-dd"
		},
		hasDelBtn: {
			type: Boolean,
			default: false
		},
		zIndex: [String, Number],
		isRead: {type: Boolean,default: false},
		placeholder: String
	},
	data: function(){return {picker: null,}},
	template: '<div v-if="!isRead" class="calenWrapV2 frmNowrap">'
				+'<input :id="id" class="ui-datepicker-input frmW01" v-model="value" type="text" @click="onShow" :placeholder="placeholder" readonly>'
				+'<button v-if="hasDelBtn" type="button" class="inpDel" @click.stop="onDelete">삭제</button>'
				+'<button class="ui-datepicker-trigger" @click.stop="onShow"></button>'
			  +'</div>'
			  +'<span v-else>{{value}}</span>',
    computed: {
    	_minDate: function(){
    		if(typeof this.minDate === 'string' && this.REGEXP.date.test(this.minDate)){
    			return this.minDate
			}else if(typeof this.minDate === 'object' && this.minDate !== null){
				if(this.minDate.date === null || this.minDate.date===''){					
					return null
				}else{
					return this.shiftDate(this.minDate.date, this.minDate.shift, this.format)
				}
			}else{				
				return null;
			}
    	},
    	_maxDate: function(){
    		if(typeof this.maxDate === 'string' && this.REGEXP.date.test(this.maxDate)){
				return this.maxDate
			}else if(typeof this.maxDate === 'object' && this.maxDate !== null){
				if(this.maxDate.date === null || this.maxDate.date===''){
					return null
				}else{					
					return this.shiftDate(this.maxDate.date, this.maxDate.shift, this.format)
				}
			}else{
				return null;
			}
    	}
    },
	destroyed: function(){
		this.onClose();
	},
	methods: {
		onClose: function(){
			if(this.picker){
				this.picker.datepicker('destroy');	
				this.picker = null;
			}
		},
		onDelete: function(){
			this.$parent[this.id] = null;
		},
		onShow: function(e){
			if(!this.isRead){
				this.onClose();
				this._create();
				this.picker.datepicker('show');
			}
		},
		_create: function(){
			let $input = $(this.$el).find('input.ui-datepicker-input');
			let thisObj = this;
			this.picker = $input.datepicker({
				language: 'ko-KR',
				format: this.format,
				offset: 5,
				autoHide: true,
				zIndex: Number(this.zIndex)||10,
				startDate: this._minDate,
				endDate: this._maxDate,
				pick: function(e){
					thisObj.$emit('change', $input.datepicker('getDate', true));
				},
			});
		},
		onChange: function(e){
			console.log(e.target.value)
		}
	}
})

// Component - Format Number Input
Vue.component('number', {
	props:{
		id: {
			type: String,
			required: true
		},
		decimal: {
			type: [String, Number],
			default: 0
		},
		value: [String, Number],
		min:[String, Number],
		max:[String, Number],
		placeholder: String,
		isRead: {type: Boolean,default: false}
	},
	template: '<input v-if="!isRead" :value="initValue" @blur="onBulr($event)" type="text" :placeholder="placeholder" class="tright" style="font-weight:inherit;color:inherit" />'
		 + '<span v-else>{{value | formatNumberFilter(decimal)}}</span>',
	computed:{
		initValue: function(){
			if(this.value === null) return null;
			
			let value = Number(this.value);
			if (isNaN(value)) {
				return null;
			}else{
				return $.number(value, Number(this.decimal));
			}
		}
	},
	created: function(){
		EVENT_BUS.$on('set-number-value-' + this.id, this.updateValue);
	},
	beforeDestroy: function(){
		EVENT_BUS.$off('set-number-value-' + this.id, this.updateValue);
	},
	methods: {
		updateValue: function(value){
			if(value === null){
				this.$emit('input', null)
				this.$el.value = ""
				return;
			}
			
			value = Number(value);
			
			if(this.min && Number(this.min) > value){
				Helper.MessageBox(this.min + " 보다 커야합니다.")
				this.$emit('input', this.min)
				this.$el.value = $.number(this.min, Number(this.decimal)||0)
				return;
			}
			
			if(this.max && Number(this.max) < value){
				Helper.MessageBox(this.max + " 보다 작아야합니다.")
				this.$emit('input', this.max)
				this.$el.value = $.number(this.max, Number(this.decimal)||0)
				return;
			}
			
			this.$emit('input', Helper.round(value, Number(this.decimal)||0))
			this.$el.value = $.number(value, Number(this.decimal)||0)
		},
		onBulr: function(e){
			let value = $.trim(e.target.value).replace(/[^0-9\.]/g, "");
			if (value === "" || isNaN(Number(value))) {
				this.$emit('input', null)
				e.target.value = null
			}else{
				value = Number(value);
				this.updateValue(value)
			}			
		},
	}
});

Vue.component('selectbox', {
	props:{
		id: {type: String, required: true},
		value: Object,
		isRead: {type: Boolean,default: false},
		options: Array,
		maxCnt:{type: Number, default: 10},
		itemHeight: {type: Number, default: 28},
		title: String,
		disabled: [String, Boolean],
		defaultText: {
			type: [String, Object],
			default: "선택하세요."
		},
		css: [String, Object]
	},
	data: function(){return {on: false,ps: null}},
	template:  
		'<div v-if="!isRead" :class="{selectbox:true, frmW01:true, on:on}" :style="css" :title="title">'
			+ '	<a href="#" :class="{tit:true, disabled:disabled}" :title="_nm" role="button"   @click.prevent="onClick">{{_nm}}</a>'
			+ '	<div :id="_id" class="optionbox" :style="{height: _maxHeight + \'px\'}">'
			+ '		<div class="overcon fadein" style="display: block;">'
			+ '			<ul class="con">'
			+ '				<li :style="{height:itemHeight+\'px\'}"><a href="javascript:void(0)" :class="{on:_cd==\'\'}" @click="onSelect(-1)">{{typeof defaultText == \'string\'?defaultText:defaultText.text}}</a></li>'
			+ '				<li v-for="(option, index) in _options" :key="option.cd" :style="{height:itemHeight+\'px\'}"><a href="javascript:void(0)" :class="{on:_cd==option.cd}" @click="onSelect(index)">{{option.nm}}</a></li>'
			+ '			</ul>'
			+ '		</div>'
			+ '	</div>'
			+ '<div class="optionbox-outside" @click="onClose"></div>'
		+ '</div>'
		+ '<span v-else>{{typeof this.defaultText == \'string\' || this.defaultText.isDisplay?_nm:null}}</span>',
	computed: {
		_id: function(){return 'selectbox_' + this.id},
		_cd: function(){return this.value.cd},
		_nm: function(){
			if(this.value.cd===''||this.value.cd===null){
				if(typeof this.defaultText == 'string'){
					return this.defaultText;
				}else{
					return this.defaultText.text;
				}
			}else{
				return this.value.nm;
			}
		},
		_maxHeight: function() {return (this._options.length + 1 > this.maxCnt ? this.maxCnt : this._options.length + 1) * this.itemHeight + 2},
		_options: function(){return this.options?this.options:this.slotToOption();}
	},
	created: function(){
		EVENT_BUS.$on('set-select-value-' + this.id, this.setValue);
	},
	beforeDestroy: function(){
		EVENT_BUS.$off('set-select-value-' + this.id, this.setValue);
	},
	methods: {
		slotToOption: function(){
			let $slots = this.$slots.default;
			$slots = $slots?$slots.filter(function(slot){return slot.tag === "option"}): null;
			let tmp = [];
			if($slots){			
				for(let i=0; i<$slots.length; i++){
					tmp.push({cd: $slots[i].data.attrs.value,nm: $slots[i].children[0].text})
				}
			}
			return tmp;
		},
		onClick: function(){
			if(!this.disabled){				
				if(this.on){
					this.onClose()
				}else{
					this.onOpen();
				}
			}
		},
		onOpen: function(){
			this.on = true;
			
			if(this.ps){this.ps.destroy();}
			this.ps = new PerfectScrollbar('#'+this._id, {
				wheelSpeed: 0.2,
				wheelPropagation: false,
				swipeEasing : false,
				minScrollbarLength: 28,
				suppressScrollX: true
			});
		},
		onClose: function(){
			this.on = false;
			if(this.ps){this.ps.destroy();}
		},
		onSelect: function(index){
			this.onClose();
			if(index < 0){
				this.$parent[this.id] = {cd:null, nm:null};
			}else{
				if(this.options){
					this.$parent[this.id] = this.options[index];
				}else{
					this.$parent[this.id] = this._options[index];
				}
			}
		},
		setValue: function(cd){
			
			if(cd === undefined || cd === null || cd === ''){
				this.$parent[this.id] = {cd:'', nm:''}
			}else{				
				for(let i=0; i<this._options.length; i++){
					if(this._options[i].cd === cd){
						this.$parent[this.id] = this._options[i];
						return;
					}
				}
			}
		}
	}
});

Vue.component('sub-prjt', {
	props:{
		id: {type: String, required: true},
		value: Array,
		isRead: {
			type:Boolean,
			Boolean: false
		}
	},
	components:{
		tip: {
			template: '<div class="tipArea" style="margin-top:10px;margin-left:10px">'
				+ '<button type="button" class="btnTip">도움말</button>'
				+ '<div class="tipCont">Service code(가운데 두자리)가 <span style="color:red">01</span> 인 경우에만 모니터링 대상에 포함됩니다</div>'
			+ '</div>'
		}
	},
	template: '<div>'
		+ '<div v-if="!isRead" class="titArea">'
			+ '<h3 class="titDep3">합산 대상 코드 추가</h3>'
			+ '<button  type="button" class="btIco btnAdd" @click="onAddRow">추가</button>'
			+ '<tip></tip>'
			+ '<p class="txtInfo side">합산 대상 코드는 최대 6개까지 추가 가능합니다.</p>'
		+ '</div>'
		+ '<div v-else-if="isRead && _cnt>0" class="titArea"><h3 class="titDep3">합산 대상 코드</h3><tip></tip></div>'
		+ '<table  class="tblForm tblAdd">'
		+'<colgroup><col style="width:50px"><col style="width:19%"><col style="width:auto"><col style="width:24%"><col style="width:27%"></colgroup>'
		+'<tbody v-for="(prjt, index) in value" :key="index">'
		+ '<tr>'
			+ '<th scope="row" rowspan="3" class="lineR thDel">{{index+1}}<button v-if="!isRead" type="button" class="btIco btnDel" @click.prevent="onDelRow(index)">삭제</button></th>'
			+ '<th scope="row">Projedt Name</th>'
			+ '<td>'
				+ '<div class="frmNowrap" v-if="!isRead" >'
					+ '<input type="text" class="frmW01" v-model="prjt.prjtNm" readonly><button  type="button" class="inpDel" @click.prevent="onDelPrjt(index)">삭제</button>'
					+ '<button type="button" class="btIco btnSearch gapL" @click.prevent="onAddPrjt(index)">검색</button>'
				+ '</div>'
				+ '<div v-else>{{prjt.prjtNm}}</div>'
			+ '</td>'
			+ '<th scope="row">Project Code</th>'
			+ '<td><span>{{prjt.prjtCd}}</span></td>'
		+ '</tr>'	
		+ '<tr>'
			+ '<th scope="row">Engagement Leader(합산코드)</th>'
			+ '<td><span v-if="prjt.chargPtr.emplno != null">{{prjt.chargPtr.kornm}}({{prjt.chargPtr.emplno}}) {{prjt.chargPtr.gradnm}}</span></td>'
			+ '<th scope="row">Project Manager</th>'
			+ '<td><span v-if="prjt.chargPtr.emplno != null">{{prjt.chargPtr.kornm}}({{prjt.chargPtr.emplno}}) {{prjt.chargPtr.gradnm}}</span></td>'
		+ '</tr>'
		+ '<tr>'
			+ '<th scope="row">보수</th>'
			+ '<td colspan="3"	class="tright">'
				+ '<div class="frmNowrap etc">'
					+ '<number v-if="!isRead" :id="\'cntrtFee\'+index" v-model="prjt.cntrtFee" decimal="0" class="frmW01 tright" ></number> <span class="unit">원</span>'
					+ '<span v-if="isRead">{{prjt.cntrtFee | formatNumberFilter(0)}}</span>'
				+ '</div>'
			+ '</td>'
		+ '</tr>'
	+ '</tbody>'
	+ '<tfoot class="lineF">'
		+ '<tr>'
			+ '<th scope="row" colspan="2">총 보수</th>'
			+ '<td colspan="3"><span>{{totCntrtFee | formatNumberFilter}}</span> 원</td>'
		+ '</tr>'
	+ '</tfoot>'
	+ '</table>'
	+ '</div>',
	computed: {
		_cnt: function(){return this.value.length},
		totCntrtFee: function(){return this.$parent.totCntrtFee}
	},
	methods:{
		onAddRow: function(){
			if(this.value.length >= 6){
				Helper.MessageBox("합산 대상 코드는 최대 6개까지 추가 가능합니다.");
			}else{
				Vue.set(this.value, this.value.length,{
					prjtCd: null,
					prjtNm: null,
					chargPtr: {emplno: null, kornm: null, gradnm: null},
					chargMgr: {emplno: null, kornm: null, gradnm: null},
					cntrtFee: null
				});
			}
		},
		onDelRow: function(index){
			this.value.splice(index, 1)
		},
		onAddPrjt: function(index){
			let subPrjtList = this.value;
			let target = this.value[index];
			
			popProject.openPopup("subPrjt" + index, function(data){
				Vue.set(subPrjtList, index, Object.assign({}, target, {
					prjtCd: data.prjtCd,
					prjtNm: data.prjtNm,
					chargPtr: data.chargPtr,
					chargMgr: data.chargMgr,
				}));
			});	
		},
		onDelPrjt: function(index){
			Vue.set(this.value, index, {
				prjtCd: null,
				prjtNm: null,
				chargPtr: {emplno: null, kornm: null, gradnm: null},
				chargMgr: {emplno: null, kornm: null, gradnm: null},
				cntrtFee: null
			});
		}
	}
});

// Filter
Vue.filter('formatNumberFilter', function (value, decimal) {
	return $.number(Number(value), Number(decimal)||0);
})

// Mixin
Vue.mixin({
	computed: {
		REGEXP: function() {
    		return {
    			date: /^20[0-9]{2,2}(\-(0[1-9]|1[0-2])(\-(0[1-9]|[1-2][0-9]|3[0-1]))?)?$/,
    			shift: /^[\+\-]\d+[yYmMdD]$/
    		}
    	},
	},
	methods: {
		setSelect: function(id, value){		// 스크립트로 Select 하기
			EVENT_BUS.$emit('set-select-value-' + id, value||'');
		},
		setNumber: function(id, value){
			EVENT_BUS.$emit('set-number-value-' + id, value);
		},
		shiftDate: function(date, shift, format){
			let retDate = new Date(date);
			
			if(this.REGEXP.shift.test(shift)){
				let sign = shift.substr(0, 1);
				let num = Number(shift.match(/\d+/g)[0]);
				let type = shift.substr(-1);
				
				if(type == 'y' || type == 'Y'){
					if(sign=='-'){
						retDate.setFullYear(retDate.getFullYear() - Number(num))	
					}else{
						retDate.setFullYear(retDate.getFullYear() + Number(num))
					}
				}else if(type == 'm' || type == 'M'){
					if(sign=='-'){
						retDate.setMonth(retDate.getMonth() - Number(num))	
					}else{
						retDate.setMonth(retDate.getMonth() + Number(num))
					}
				}else{
					if(sign=='-'){
						retDate.setDate(retDate.getDate() - Number(num))	
					}else{
						retDate.setDate(retDate.getDate() + Number(num))
					}
				}
				return (new Date(retDate)).toISOString().substr(0, format?format.length:10);
			}else{
				return null;
			}
		}
	}
});