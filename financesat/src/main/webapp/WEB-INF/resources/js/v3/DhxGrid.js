
Vue.component('dhx-grid', {
	template: '<div :id="id" :class="applyClass"></div>',
	props: {
		id: {
			type: String,
			required: true,
		},
		className: {
			type: [String, Array],
		},
		options: Object,
		gridData: Array
	},
	mounted: function() {
		this.createDhxGrid();
		const thisObj = this;
		$(document).on('click.gridResize', 'nav > button, .sumArea > .btnTog', function(){
			thisObj.gridObj.setSizes();
		});
	},
	data: function() {
		return {
			gridObj: null,
		}
	},
	computed: {
		applyClass: function() {
			if(this.className) {
				if(typeof this.className === 'string'){
					return 'gridbox ' + this.className; 
				} else {
					return 'gridbox ' + this.className.join(' ');
				}
			}else{
				return 'gridbox';
			}
		}
	},
	watch: {
		options: function() {
			this.createDhxGrid();
		}
	},
	methods: {
		createDhxGrid: function() {
			if(this.gridObj) {
				this.gridObj.destructor();
				this.gridObj = null;
			}
			this.gridObj = new dhtmlXGridObject(this.options);
			this.$emit('createDhxGrid', this.gridObj);
		},
	},
	beforeDestroy: function() {
		if(this.gridObj) {
			this.gridObj.destructor();
			this.gridObj = null;
		}
		$(document).off('click.gridResize', 'nav > button, .sumArea > .btnTog')
	}
});

let cisOnSelectEventId;
let cisOnEditEventId;
Vue.component('cis-dhx-grid', {
	template: '<dhx-grid :id="id" :options="gridOptions" :gridData="gridData" @createDhxGrid="createDhxGrid" style="width:100%"/>',
	props: {
		gridData: Array,
		mode: {
			type: String,
			validator: function(value) {return ['read', 'edit'].indexOf(value) !== -1},
			default: function() { return 'read'; }
		},
		options: Object,
	},
	created: function() {
		EVENT_BUS.$on('getDataFromCisGrid', this.getDataFromGrid);
	},
	computed: {
		editableStyle: function(){
			return "background-color: #f6dbcb !important; border-top: 1px solid #7d7d7d !important; border-left: 1px solid #7d7d7d !important; border-right: 1px solid #7d7d7d !important;";
		} 
	},
	data: function() {
		const dhxGridId = "cis-grid";
		return {
			id: dhxGridId,
			gridOptions: {
				parent: dhxGridId,
				skin: THEME_INFO.THEME,
				image_path: THEME_INFO.THEME_IMG_PATH,
				columns: [
					{id:"bdgtTrgtYn", label:[Helper.getGridTitleAndInfo("Budget<br>대상", "Budgeting이 필요한 경우 Check", 'left')], align:"center", width:"90"},
					{id:"prjtcd", label:[Helper.getGridTitleAndInfo("프로젝트 코드", "지원코드는 자동으로 보여지며, 주관코드 추가가 필요한 경우에는 R&Q 담당자에게 요청(F-link 상 추가)", 'left')], align:"center", width:"120"},
					{id:"prjtnm", label:["프로젝트 명"], align:"left"},
					{id:"inadjustnm", label:["협업<br>구분"], align:"center", width:"70", resize:false},
					{id:"createdvnm", label:["생성<br>구분"], align:"center", width:"70"},
					{id:"auditgbnm", label:[Helper.getGridTitleAndInfo("외감<br>구분", 'F-link 에서 변경 가능하며, "01"코드인 경우 최초 외감으로 인식', 'left')], align:"center", width:"70"},
					{id:"amtw", label:["계약 금액"], align:"right", width:"150"},
					{id:"exbudgettime", label:[Helper.getGridTitleAndInfo("시간예산<br>(F-Link)", 'F-link 조회값, 생성 후 5개월내 변경가능', 'right')], align:"right", width:"100"},
					{id:"cntrtAdtTm", label:[Helper.getGridTitleAndInfo("감사계약시<br>합의시간", '보수에 따라 회사와 합의한 시간', 'right')], align:"right", width:"100"},
					{id:"bdgt", label:[Helper.getGridTitleAndInfo("Budgeting<br>Hours", '코드별 Budgeting 총 합계', 'right')], align:"right", width:"100"},
					{id:"div"}
				]
			},
			gridObj: null,
			data: [],
		}
	},
	methods: {
		createDhxGrid: function(_gridObj) {
			const thisObj = this;
			this.gridObj = _gridObj;
			// Mode 별 설정
			this.refreshGridByMode();
			
			// 공통 설정
			this.gridObj.setColumnHidden(this.gridObj.getColIndexById("div"), true);
			this.gridObj.setNumberFormat("0,000",this.gridObj.getColIndexById("amtw"),".",",");
			this.gridObj.setNumberFormat("0,000",this.gridObj.getColIndexById("bdgt"),".",",");
			this.gridObj.setNumberFormat("0,000",this.gridObj.getColIndexById("cntrtAdtTm"),".",",");
			this.gridObj.setNumberFormat("0,000",this.gridObj.getColIndexById("exbudgettime"),".",",");
			this.gridObj.setNumberFormat("0,000.000",this.gridObj.getColIndexById("expWkmnsp"),".",",");
			this.gridObj.attachFooter( 
					"Summary,#cspan,#cspan,#cspan,#cspan,#cspan,<span></span>,<span></span>,<span></span>,<span></span>",
					[
						"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#F7F7F7;height: 40px;padding: 1px 5px 0;border-left:0;border-right:0;"
						,"border-right:0; border-left:0"
						,"border-right:0; border-left:0"
						,"border-right:0; border-left:0"
						,"border-right:0; border-left:0"
						,"border-right:0; border-left:0"
						,"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#FFFFFF;border-left:1px solid #ddd !important;border-right:0"
						,"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#FFFFFF;border-left:1px solid #ddd !important;border-right:0"
						,"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#FFFFFF;border-left:1px solid #ddd !important;border-right:0"
						,"text-align:right;font-size:14px;font-family:arial,notokr;font-style:inherit;background-color:#FFFFFF;border-left:1px solid #ddd !important;border-right:0"
					]
			);	
			this.gridObj.enableResizing("false");	
			this.gridObj.enableAutoHeight(true);
		},
		loadData: function() {
			const thisObj = this;
			if(this.gridObj && this.gridData && this.gridData.length > 0) {
				this.gridObj.clearAll();
				this.gridObj.parse(this.gridData, 'js');
				if(this.mode === 'edit') {
					this.gridObj.forEachRow(function(rid){
						thisObj.gridObj.setCellTextStyle(rid, thisObj.gridObj.getColIndexById("cntrtAdtTm"), thisObj.editableStyle);	
					});
				}
				
				this.data = this.getDataFromGrid();
				this.setFooterLabel('amtw', this.getAmtw());
				this.setFooterLabel('exbudgettime', this.getExbudgettime());
				this.setFooterLabel('cntrtAdtTm', this.getCntrtAdtTm());
				this.setFooterLabel('bdgt', this.getBdgt());
			}
		},
		onRowSelect: function(rid, cInd) {
			if(cInd == this.gridObj.getColIndexById("bdgtTrgtYn")){
				this.setCellValue(rid, 'bdgtTrgtYn', this.getCellValue(rid, 'bdgtTrgtYn') === 'Y'?'N':'Y');
			}
		},
		onEditCell: function(stage,rId,cInd,nValue,oValue) {
			const thisObj = this;
			if(stage == 2){
				//감사계약시합의시간 이 변경
				if(cInd == this.gridObj.getColIndexById("cntrtAdtTm")){
					let regExp = /^[0-9]+(\.[0-9]+)?$/g;
					if(nValue !== ""){
						if(regExp.test(nValue)){
							this.$emit("change-cntrt-adt-tm"); // 표준감사시간 대상(그룹)에 있는 감사계약합의시간 업데이트 하기 위함 (변경되었다는 것을 알림)
							let sumCntrtAdtTm = 0;
							this.gridObj.forEachRow(function(rid){
								let rdata = thisObj.gridObj.getRowData(rid);
								sumCntrtAdtTm += Number(rdata.cntrtAdtTm);
							})
							this.setFooterLabel('cntrtAdtTm', sumCntrtAdtTm);
							return true;
						}else{
							Helper.MessageBox("숫자만 입력 가능합니다.");
							return false;
						}
					}
				}
			}
			return true;
		},
		setFooterLabel: function(cid, value) {
			this.gridObj.setFooterLabel(this.gridObj.getColIndexById(cid)-5, this.toFormat(value));
		},
		getAmtw: function() {
			let tmp = this.data.filter(function(data){ return ['정상', '이관'].includes(data.createdvnm)});
			return tmp.map(function(data){return Number(data.amtw);}).reduce(function(acc, curr) {return acc + curr}, 0);
		},
		getExbudgettime: function(isSatOnly) {
			let tmp = [];
			if(isSatOnly) {
				tmp = this.data.filter(function(data){ return data.auditgbnm === '외감'});
			}else {
				tmp = this.data;				
			}
			return tmp.map(function(data){return Number(data.exbudgettime);}).reduce(function(acc, curr) {return acc + curr}, 0);
		},
		getCntrtAdtTm: function(isSatOnly) {
			let tmp = [];
			if(isSatOnly) {
				tmp = this.data.filter(function(data){ return data.auditgbnm === '외감'});
			}else {
				tmp = this.data;				
			}
			return tmp.map(function(data){return Number(data.cntrtAdtTm);}).reduce(function(acc, curr) {return acc + curr}, 0);
		},
		getBdgt: function(isSatOnly) {
			let tmp = [];
			if(isSatOnly) {
				tmp = this.data.filter(function(data){ return data.auditgbnm === '외감'});
			}else {
				tmp = this.data;				
			}
			return tmp.map(function(data){return Number(data.bdgt);}).reduce(function(acc, curr) {return acc + curr}, 0);
		},
		toFormat: function(value, pos) {
			let _val = Number(value);
			let _pos = Number(pos || 0);
			
			if(isNaN(_val)) {
				return null;
			}else {
				return _val.toLocaleString(undefined, {minimumFractionDigits: _pos, maximumFractionDigits: _pos})
			}
		},
		getDataFromGrid: function(callback) {
			const thisObj = this;
			const data = [];
			this.gridObj.forEachRow(function(rid){
				data.push(Object.assign({}, thisObj.gridObj.getRowData(rid), {id: rid, rowIndex: thisObj.gridObj.getRowIndex(rid)}));
			});
			// Row Index로 정렬하기
			data.sort(function(a, b){return a.rowIndex - b.rowIndex;});
			if(callback) callback(data);
			return data;
		},
		getCellValue: function(rid, cid) {
			return this.gridObj.cells(rid, this.gridObj.getColIndexById(cid)).getValue();
		},
		setCellValue: function(rid, cid, value) {
			this.gridObj.cellById(rid, this.gridObj.getColIndexById(cid)).setValue(value);
		},
		refreshGridByMode: function(){
			const thisObj = this;
			if(this.mode === 'read'){
				this.gridObj.detachEvent(cisOnSelectEventId);
				this.gridObj.detachEvent(cisOnEditEventId);
				this.gridObj.enableEditEvents(false, false, false);
				this.gridObj.setColTypes("toggle,ro,ro,ro,ro,ro,ron,ron,ron,ron,ro");
				this.gridObj.forEachRow(function(rid){
					thisObj.gridObj.setCellTextStyle(rid, thisObj.gridObj.getColIndexById("cntrtAdtTm"),"");	
				});
			} else {				
				cisOnSelectEventId = this.gridObj.attachEvent("onRowSelect", this.onRowSelect);
				cisOnEditEventId = this.gridObj.attachEvent("onEditCell", this.onEditCell);
				this.gridObj.enableEditEvents(true,false,true);
				this.gridObj.setColTypes("toggle,ro,ro,ro,ro,ro,ron,ron,edn,ron,ro");
				this.gridObj.forEachRow(function(rid){
					thisObj.gridObj.setCellTextStyle(rid, thisObj.gridObj.getColIndexById("cntrtAdtTm"), thisObj.editableStyle);	
				});
								
				this.gridObj.enableEditEvents(true, false, false);
			}
		},
	},
	watch: {
		gridData: function(data) {
			this.loadData();
		},
		mode: function(){
			this.refreshGridByMode();
		}
	}
});

let bdgtOnSelectEventId;
let bdgtOnEditEventId;
Vue.component('budget-dhx-grid', {
	template: '<dhx-grid :id="id" className="gridHp07" :options="gridOptions" :gridData="gridData" @createDhxGrid="createDhxGrid" style="width:100%;height:100%"></dhx-grid>',
	props: {
		gridData: Array,
		mode: {
			type: String,
			validator: function(value) {return ['read', 'edit'].indexOf(value) !== -1},
			default: function() { return 'read'; }
		},
		weekInfo: {
			type: Array,
		},
		options: {
			type: Object,
			required: true,
		},
	},
	created: function() {
		const thisObj = this;
		this.weekInfo.forEach(function(data){
			thisObj.gridReadOptions.columns.push({
				  id: data.id
				, label: [data.startDate, data.workDay + ' days']
				, align:"right"
				, width:"85"
				, type: 'ron'
			});
			thisObj.gridEditOptions.columns.push({
				  id: data.id
				, label: [data.startDate, data.workDay + ' days']
				, align:"right"
				, width:"85"
				, type: 'edn'
			});
		});
		
		EVENT_BUS.$on('addRowToBudgetGrid', this.addRow);
		EVENT_BUS.$on('getDataFromBudgetGrid', this.getDataFromGrid);
		EVENT_BUS.$on('excelExportBudgetGrid', this.excelExport);
	},
	data: function(){
		const dhxGridId = "budget-grid";
		
		return {
			id: dhxGridId,
			gridReadOptions: {
				parent: dhxGridId,
				skin: THEME_INFO.THEME,
				image_path: THEME_INFO.THEME_IMG_PATH,
				columns: [
					{id:"prjtcd", type:"ro", label:["Project Code", "#select_filter_strict"], align:"center", sort:"str", width:"100"},
					{id:"prjtnm", type:"ro", label:["Project Name", "#select_filter_strict"], align:"center", sort:"str", width:"100"},
					{id:"tbd", type:"toggle", label:[Helper.getGridTitleAndInfo("TBD", "미정인 경우(New staff 등) Check(주 80시간 초과하여 일괄로 입력 가능)", 'left'), "#select_filter_strict"], align:"center", sort:"str", width:"65"},
					{id:"actvNm", type:"ro", label:[Helper.getGridTitleAndInfo("Activity", "SPA, Fulcrum, New STaff(TBD), QC(QRP 포함) 예산 배정시 반드시 해당 Activity로 기입 필요 ", 'left'), "#select_filter_strict"], align:"left", sort:"str", width:"180"},
					{id:"locaNm", type:"ro", label:[Helper.getGridTitleAndInfo("Location", "Retain 반영시 팀별로 field 만 선택하여 반영 가능", 'left'), "#select_filter_strict"], align:"left", sort:"str", width:"90"},
					{id:"korNm", type:"ro", label:["Name", "#text_filter"], align:"left", sort:"str", width:"70"},
					{id:"emplNo", type:"ro", label:["사번", "#text_filter"], align:"center", sort:"str", width:"70"},
					{id:"gradNm", type:"ro", label:["Grade", "#select_filter_strict"], align:"left", sort:"str", width:"70"},
					{id:"wkmnsp", type:"ron", label:[Helper.getGridTitleAndInfo("숙련도", "예상감사보고서일과 수습종료일을 기준으로 자동계산되며, 수정 가능합니다.", 'right'), "#numeric_filter"], align:"right", sort:"int", width:"80"},
					{id:"totWkmnspAsgnTm", type:"ron", label:["숙련도 고려<br>투입시간", "#numeric_filter"], align:"right", sort:"int", width:"70"},
					{id:"totAsgnTm", type:"ron", label:["Total<br>Time", "#numeric_filter"], align:"right", sort:"int", width:"70"},
				]
			},
			gridEditOptions: {
				parent: dhxGridId,
				skin: THEME_INFO.THEME,
				image_path: THEME_INFO.THEME_IMG_PATH,
				columns: [
					{id:"btnDel", type:"btnDel", label:["", ""],  align:"center", width:"40"},
					{id:"prjtcd", type:"ro", label:["Project Code", "#select_filter_strict"], align:"center", sort:"str", width:"100"},
					{id:"prjtnm", type:"ro", label:["Project Name", "#select_filter_strict"], align:"center", sort:"str", width:"100"},
					{id:"tbd", type:"toggle", label:[Helper.getGridTitleAndInfo("TBD", "미정인 경우(New staff 등) Check(주 80시간 초과하여 일괄로 입력 가능)", 'left'), "#select_filter_strict"], align:"center", sort:"str", width:"65"},
					{id:"actvCd", type:"coro", label:[Helper.getGridTitleAndInfo("Activity", "SPA, Fulcrum, New STaff(TBD), QC(QRP 포함) 예산 배정시 반드시 해당 Activity로 기입 필요 ", 'left'), "#select_filter_strict"], align:"left", sort:"str", width:"180"},
					{id:"locaCd", type:"coro", label:[Helper.getGridTitleAndInfo("Location", "Retain 반영시 팀별로 field 만 선택하여 반영 가능", 'left'), "#select_filter_strict"], align:"left", sort:"str", width:"90"},
					{id:"btnOrg", type:"btnOrg", label:["", ""], align:"center", width:"40"},
					{id:"korNm", type:"ro", label:["Name", "#text_filter"], align:"left", sort:"str", width:"70"},
					{id:"emplNo", type:"ro", label:["사번", "#text_filter"], align:"center", sort:"str", width:"70"},
					{id:"gradNm", type:"ro", label:["Grade", "#select_filter_strict"], align:"left", sort:"str", width:"70"},
					{id:"gradCd", type:"ro"},
					{id:"wkmnsp", type:"edn", label:[Helper.getGridTitleAndInfo("숙련도", "예상감사보고서일과 수습종료일을 기준으로 자동계산되며, 수정 가능합니다.", 'right'), "#numeric_filter"], align:"right", sort:"int", width:"80"},
					{id:"totWkmnspAsgnTm", type:"ron", label:["숙련도 고려<br>투입시간", "#numeric_filter"], align:"right", sort:"int", width:"70"},
					{id:"totAsgnTm", type:"ron", label:["Total<br>Time", "#numeric_filter"], align:"right", sort:"int", width:"70"},
				]
			},
			gridObj: null,
		};
	},
	computed: {
		gridOptions: function() {
			return this.mode === 'read' ? this.gridReadOptions : this.gridEditOptions 
		}
	},
	methods: {
		createDhxGrid: function(_gridObj) {
			const thisObj = this;
			this.gridObj = _gridObj;
			
			this.gridObj.splitAt(this.gridObj.getColIndexById("week1"));
			
			// 공통 설정
			this.gridObj.enableResizing("false");
			let actvCombo = this.gridObj.getCombo(this.gridObj.getColIndexById("actvCd"));
			let locaCombo = this.gridObj.getCombo(this.gridObj.getColIndexById("locaCd"));
			this.options.actvList.forEach(function(item){actvCombo.put(item.cd, item.nm);});
			this.options.locaList.forEach(function(item){locaCombo.put(item.cd, item.nm);});
			this.gridObj.setNumberFormat("0,000.000",this.gridObj.getColIndexById("wkmnsp"),".",",");
			this.gridObj.setNumberFormat("0,000.00",this.gridObj.getColIndexById("totAsgnTm"),".",",");
			this.gridObj.setNumberFormat("0,000.00",this.gridObj.getColIndexById("totWkmnspAsgnTm"),".",",");
			for(let i=1; i<=this.weekInfo.length; i++){
				this.gridObj.setNumberFormat("0,000.00",this.gridObj.getColIndexById('week'+i),".",",");
			}
			// 모드별 Column Type 지정 및 hidden 처리
			this.refreshGridByMode();
			
			this.loadData();
		},
		onRowSelect: function(rid, cInd) {
			const thisObj = this;
			let actvCd = this.getCellValue(rid, 'actvCd');
			
			if(cInd === this.gridObj.getColIndexById('btnDel')){
				// 삭제 버튼
				this.gridObj.deleteRow(rid);
			} else if(cInd === this.gridObj.getColIndexById('tbd')) {
				// TBD 체크 버튼
				//Activity, Name, 사번, 직급, 숙련도 초기화				
				this.setCellValue(rid, 'gradNm', '');
				this.setCellValue(rid, 'gradCd', '');
				this.setCellValue(rid, 'wkmnsp', '');
				this.setCellValue(rid, 'korNm', '');
				this.setCellValue(rid, 'emplNo', '');
				this.setCellValue(rid, 'tbd', this.getCellValue(rid, 'tbd') === 'Y'?'N':'Y' );
				this.calculateByRow(rid);
				
//				this.gridObj.refreshFilters();
			} else if(cInd === this.gridObj.getColIndexById('btnOrg')) {
				// 조직도 버튼
				let tbdYn = this.getCellValue(rid, 'tbd');
				
				if(tbdYn === 'Y' && ['09','12','13','14','15','16'].includes(actvCd)) {
					let roleConfig = this.options.bdgtRoleConfig.filter(function(data){return data.cd === actvCd});
					if(roleConfig.length > 0){
						roleConfig = roleConfig[0];
						this.setCellValue(rid, 'gradNm', roleConfig.gradnm);
						this.setCellValue(rid, 'gradCd', roleConfig.gradcd);
						this.setCellValue(rid, 'wkmnsp', roleConfig.wkmnsp);
						this.setCellValue(rid, 'korNm', this.gridObj.cells(rid, this.gridObj.getColIndexById('actvCd')).getTitle());
						this.setCellValue(rid, 'emplNo', '');
						this.calculateByRow(rid);
					} else {
						this.setCellValue(rid, 'gradNm', '');
						this.setCellValue(rid, 'gradCd', '');
						this.setCellValue(rid, 'wkmnsp', '');
						this.setCellValue(rid, 'korNm', '');
						this.setCellValue(rid, 'emplNo', '');
					}
				} else {
					this.options.empPopup().openPopup(rid, function(data){
						thisObj.setCellValue(rid, 'korNm', data.kornm);
						thisObj.setCellValue(rid, 'emplNo', data.emplno);
						thisObj.setCellValue(rid, 'gradNm', data.gradnm);
						thisObj.setCellValue(rid, 'gradCd', data.gradcd);
						thisObj.setCellValue(rid, 'wkmnsp', data.wkmnsp);
						
						thisObj.calculateByRow(rid);
					});
				}
			}
		},
		onEditCell: function(stage,rId,cInd,nValue,oValue) {
			if(stage === 2){
				const wkmnspRegExp = /^[0-9]+(\.[0-9]{0,3})?$/g;
				const tmRegExp = /^[0-9]+(\.(00|25|50?|75))?$/g;
				const rowData = this.gridObj.getRowData(rId);
				const workDays = this.weekInfo.map(function(data){return data.workDay});
				const week1Ind = this.gridObj.getColIndexById("week1");
				
				if(cInd === this.gridObj.getColIndexById("wkmnsp")){
					if(wkmnspRegExp.test(nValue)){
						if(nValue > 0){ return true;}
						
						Helper.MessageBox("0 이상의 숫자를 입력해야 합니다.");
						return false
					}else{
						Helper.MessageBox("필수 값이고, 숫자만 입력 가능합니다.");	
						return false;
					}
				}else if(cInd >= week1Ind){
					if(nValue === null || nValue === undefined || $.trim(nValue) === '') {
						this.calculateByRow(rId);
						return true;
					}else if(tmRegExp.test(nValue)) {
						// TBD는 초과 시간 체크 하지않음.
						if(rowData.tbd !== 'Y') {							
							const maxWorkTm = (Number(workDays[cInd - week1Ind]) * 16); // 최대 근무시간 (하루16시간)
							if(Number(nValue) > maxWorkTm){
								Helper.MessageBox("해당 주에는 최대 " + maxWorkTm + "시간까지만 입력 가능합니다.");
								return false;	
							}
						}
						this.calculateByRow(rId);
						return true;
					}else{
						Helper.MessageBox("숫자만 입력가능 하며, 소수점 이하는 0.25, 0.5, 0.75(15분 단위)로 입력할 수 있습니다.");
						return false;
					}
				}
			}
			return true;
		}, 
		getCellValue: function(rid, cid) {
			return this.gridObj.cells(rid, this.gridObj.getColIndexById(cid)).getValue();
		},
		setCellValue: function(rid, cid, value) {
			this.gridObj.cellById(rid, this.gridObj.getColIndexById(cid)).setValue(value);
		},
		calculateByRow: function(rid) {
			const data = this.gridObj.getRowData(rid);
			let totTime = 0;
			
			for(let i=1; i<=weekInfo.length; i++){
				totTime += Number(data['week' + i]);
			}
			
			this.setCellValue(rid, 'totAsgnTm', totTime);
			this.setCellValue(rid, 'totWkmnspAsgnTm', totTime*data.wkmnsp);
		},
		loadData: function() {
			if(this.gridObj && this.gridData && this.gridData.length > 0) {
				this.gridObj.clearAll();
				this.gridObj.parse(this.gridData, 'js');
			}
		},
		addRow: function(list) {
			const thisObj = this;
			const prjtPopup = Helper.Popup("project", "Project Select", "/popup/project-v3", {size:'M', prjtCd:this.options.prjtCd, list: JSON.stringify(list)});
			prjtPopup.openPopup("subPrjt" + thisObj.gridObj.uid(), function(data){
				thisObj.gridObj.addRow(thisObj.gridObj.uid(), [,data.prjtCd,data.prjtNm,'N',,,,], 0);
			});
		},
		getDataFromGrid: function(callback) {
			const thisObj = this;
			const data = [];
			this.gridObj.forEachRow(function(rid){
				data.push(Object.assign({}, thisObj.gridObj.getRowData(rid), {id: rid, rowIndex: thisObj.gridObj.getRowIndex(rid)}));
			});
			
			// Row Index로 정렬하기
			// data.sort(function(a, b){return a.rowIndex - b.rowIndex;});
			if(callback) callback(data)
			return data;
		},
		excelExport: function(param) {
			this.gridObj.toExcel(EXCEL_WRITER_URL_V3 + "?" + $.param(param));
		},
		refreshGridByMode: function(){
			if(this.mode === 'read'){
				this.gridObj.enableSmartRendering(true);
				this.gridObj.enableEditEvents(false, false, false);
				this.gridObj.detachEvent(bdgtOnSelectEventId);
				this.gridObj.detachEvent(bdgtOnEditEventId);
			}else {
				this.gridObj.setColumnHidden(this.gridObj.getColIndexById("gradCd"), true);
				this.gridObj.enableSmartRendering(false);
				this.gridObj.enableEditEvents(true, false, false);
				bdgtOnSelectEventId = this.gridObj.attachEvent("onRowSelect", this.onRowSelect);
				bdgtOnEditEventId = this.gridObj.attachEvent("onEditCell", this.onEditCell);
			}
		}
	},
	watch: {
		gridData: function(data) {
			this.loadData();
		},
		mode: function(mode) {
			this.refreshGridByMode();
		}
	}
})