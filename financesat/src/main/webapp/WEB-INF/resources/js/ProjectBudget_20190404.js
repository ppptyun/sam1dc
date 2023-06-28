const initData = {
	prjtNm: "",
	prjtCd: "",
	totCntrtFee: 0,
	totPrjtBdgt: 0,
	sumMembBdgt: 0,			// member only
	sumMembBdgtWkmnsp: 0,	// member only
	totMembBdgt: 0,			// new staff, other, ra, fulcrum, member 포함
	totMembBdgtWkmnsp: 0,	// new staff, other, ra, fulcrum, member 포함
	wkmnspSat: 0,
	expCm: 0,
	newStfBdgtTm: 0,
	newStfWkmnsp: 0,
	otherBdgtTm: 0,
	otherWkmnsp: 0,
	raBdgtTm: 0,
	flcmBdgtTm: 0,
	baseWkmnsp: 0,
	sDtOfWeek: [],
	
	etDfnSat: 0,
	etcBdgtTm: 0,
	
	planSat: 0,
	expTeamWkmnsp: 0,
	
	newStfBdgtTmWkmnsp: 0,
	otherBdgtTmWkmnsp: 0,
	raBdgtTmWkmnsp: 0,
	flcmBdgtTmWkmnsp: 0
}

const ProjectBudget = function(){
	this.summary;			// summary 정보
	this.listData;			// Member Budget list 정보
	this.gridObj;			// grid Obj;
	
	this.nameList = [];		// sh-name 목록
}


ProjectBudget.prototype = {
	init: function(summary){
		let thisObj = this; 
		if(typeof summary === 'string') summary = JSON.parse(summary);
		thisObj.summary = new Helper.ObjectProxy($.extend(initData, summary), {
			get:function(target, key){
				let obj = thisObj.summary;
				let retVal;
				switch(key){
					case "totMembBdgt":
						retVal = Number(target.newStfBdgtTm) + Number(target.otherBdgtTm) + Number(target.raBdgtTm) + Number(target.flcmBdgtTm) + Number(target.sumMembBdgt);
						break;
					case "newStfBdgtTmWkmnsp":
						retVal = Number(target.newStfBdgtTm) *  Number(target.newStfWkmnsp);
						break;
					case "otherBdgtTmWkmnsp":
						retVal = Number(target.otherBdgtTm) *  Number(target.baseWkmnsp);
						break;
					case "raBdgtTmWkmnsp":
						retVal = Number(target.raBdgtTm) *  Number(target.baseWkmnsp);
						break;
					case "flcmBdgtTmWkmnsp":
						retVal = Number(target.flcmBdgtTm) *  Number(target.baseWkmnsp);
						break;
					case "totMembBdgtWkmnsp":
						retVal = Number(obj.newStfBdgtTmWkmnsp) + Number(obj.otherBdgtTmWkmnsp) + Number(obj.raBdgtTmWkmnsp) + Number(obj.flcmBdgtTmWkmnsp) + Number(obj.sumMembBdgtWkmnsp);
						break;
					case "planSat":
						retVal = Number(target.wkmnspSat)/(Number(target.totMembBdgt)==0?1:Number(target.totMembBdgtWkmnsp) / Number(target.totMembBdgt))
						break;
					case "expTeamWkmnsp":
						retVal = Number(target.totMembBdgt)==0?0:Number(target.totMembBdgtWkmnsp) / Number(target.totMembBdgt)
						break;
					default:
						retVal = target[key];
				}
				return retVal;
			},
			set:function(target, key, value){
				target[key] = value;
			}
		});
		
		// sh-name tag 처리
		$("[sh-name]").each(function(idx, obj){
			let $obj = $(obj);
			let name = $obj.attr("sh-name");
			if($obj.is("input")){
				$obj.off('change').on('change', function(e){
					e.preventDefault();
					thisObj.setValue(name, $(this).val());
				});
			}
			thisObj.setUI(name, $obj);
			thisObj.nameList.push(name);
		}).promise().done(function(){
			$("[sh-name]").removeAttr('sh-name sh-format');
			thisObj._refreshAll();
		});
		
	},
	setUI:function(name, $obj){
		let domObj = $(document).data("domObj");
		if(!domObj){
			$(document).data("domObj", {});
			domObj = $(document).data("domObj");
		}
		if(!domObj[name]){
			domObj[name] = [];
		}
		domObj[name].push({
			$obj: $obj,
			format: $obj.attr("sh-format")
		});
	},
	getUI:function(name){
		return $(document).data("domObj")[name];
	},
	setValue: function(name, value){
		let thisObj = this;
		thisObj.summary[name] = value;
		thisObj._refreshByName(name);
	},
	_refreshAll: function(){
		let thisObj = this;
		for(let i=0; i<this.nameList.length; i++){
			thisObj._refreshByName(this.nameList[i]);
		}
		
	},
	_refreshByName: function(name){
		let thisObj = this;
		let list = thisObj.getUI(name);
		
		if(thisObj.nameList.includes(name)){			
			for(let i=0; i<list.length; i++){
				let $obj = list[i].$obj;
				let format = list[i].format;
				if($obj.is('input:text, input[type="number"], textarea')){
					$obj.val(thisObj.summary[name]);
				}else{
					if(format == "#,##0"){
						$obj.text($.number(Number(thisObj.summary[name]), 0));
					}else if(format == "#,##0.00"){
						$obj.text($.number(Number(thisObj.summary[name]), 2));
					}else if(format == "#,##0.000"){
						$obj.text($.number(Number(thisObj.summary[name]), 3));
					}else{						
						$obj.text(thisObj.summary[name]);
					}
				}
			}
		}
		
		switch(name){
		case "newStfBdgtTm":
			thisObj._refreshByName("newStfBdgtTmWkmnsp");
			thisObj._refreshByName("totMembBdgt");
			thisObj._refreshByName("expTeamWkmnsp");
			thisObj._refreshByName("planSat");
			break;
		case "otherBdgtTm":
			thisObj._refreshByName("otherStfBdgtTmWkmnsp");
			thisObj._refreshByName("totMembBdgt");
			thisObj._refreshByName("expTeamWkmnsp");
			thisObj._refreshByName("planSat");
			break;
		case "raBdgtTm":
			thisObj._refreshByName("raBdgtTmWkmnsp");
			thisObj._refreshByName("totMembBdgt");
			thisObj._refreshByName("expTeamWkmnsp");
			thisObj._refreshByName("planSat");
			break;
		case "flcmBdgtTm":
			thisObj._refreshByName("flcmBdgtTmWkmnsp");
			thisObj._refreshByName("totMembBdgt");
			thisObj._refreshByName("expTeamWkmnsp");
			thisObj._refreshByName("planSat");
			break;
		case "sumMembBdgt":
			thisObj._refreshByName("totMembBdgt");
			thisObj._refreshByName("expTeamWkmnsp");
			thisObj._refreshByName("planSat");
			break;
		case "sumMembBdgtWkmnsp":
			thisObj._refreshByName("totMembBdgtWkmnsp");
			thisObj._refreshByName("expTeamWkmnsp");
			thisObj._refreshByName("planSat");
			break;
		case "totMembBdgt":
			thisObj._refreshByName("totMembBdgtWkmnsp");
			thisObj._refreshByName("expTeamWkmnsp");
			thisObj._refreshByName("planSat");
			break;
		}
	},
}