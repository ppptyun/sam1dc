var BRSConst = {
		mode:{NEW:"new", READ:"read",EDIT:"edit"}
}
var BRSObject = {};
BRSObject.prototype.init = function(){
	this.data = {};
	this.mode = BRSConst.mode.READ;
}
BRSObject.prototype.load = function(prjtcd, mode){
	this.init();
}
BRSObject.prototype.close = function(){
	
}
BRSObject.prototype.saveNew = function(){
	
}
BRSObject.prototype.saveEdit = function(){
	
}