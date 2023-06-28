const initPrjtBdgt = {
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
	flcmBdgtTmWkmnsp: 0,
	
	isExpand: false
}

const ProjectBudget = function(config){SHView.call(this, config);}
ProjectBudget.prototype = Object.create(SHView.prototype);
ProjectBudget.constructor = ProjectBudget;

ProjectBudget.prototype.initPrjtBdgt = function(callback){
	this.init(callback);
}
ProjectBudget.prototype.setValue = function(name, value){
	if(this.data[name] !== undefined){
		this.data[name] = value;	
	}
}