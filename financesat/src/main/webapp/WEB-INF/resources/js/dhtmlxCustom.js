function eXcell_btnDel(cell){ //the eXcell name is defined here
    if (cell){                // the default pattern, just copy it
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function(){}  //read-only cell doesn't have edit method
    // the cell is read-only, so it's always in the disabled state
    this.isDisabled = function(){ return true; }
    this.setValue=function(val){
    	const rowId = this.cell.parentNode.idd;
        this.setCValue("<button class='btIco btnDel' type='button' style='cursor:pointer'><button>");
    }
}
eXcell_btnDel.prototype = new eXcell;// nests all other methods from the base class

function eXcell_btnOrg(cell){ //the eXcell name is defined here
    if (cell){                // the default pattern, just copy it
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function(){}  //read-only cell doesn't have edit method
    // the cell is read-only, so it's always in the disabled state
    this.isDisabled = function(){ return true; }
    this.setValue=function(val){
    	const rowId = this.cell.parentNode.idd;
        this.setCValue("<button class='btIco btnSearch02' type='button' style='cursor:pointer'><button>");
    }
}
eXcell_btnOrg.prototype = new eXcell;// nests all other methods from the base class


function eXcell_btnOpen(cell){ //the eXcell name is defined here
    if (cell){                // the default pattern, just copy it
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function(){}  //read-only cell doesn't have edit method
    // the cell is read-only, so it's always in the disabled state
    this.isDisabled = function(){ return true; }
    this.setValue=function(val){
    	const rowId = this.cell.parentNode.idd;
        this.setCValue("<input type='button' class='btnPwc btnS' onclick='openPrjt(\""+val+"\")' value='Project' style='cursor:pointer'><input type='button' class='btnPwc btnS' value='Budget' onclick='openBdgt(\""+val+"\")' style='cursor:pointer'>");                                      
    }
}
eXcell_btnOpen.prototype = new eXcell;// nests all other methods from the base class


function eXcell_btnOpen2(cell){ //the eXcell name is defined here
	if (cell){                // the default pattern, just copy it
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function(){}  //read-only cell doesn't have edit method
    // the cell is read-only, so it's always in the disabled state
    this.isDisabled = function(){ return true; }
    this.setValue=function(val){
    	const rowId = this.cell.parentNode.idd;
    	this.setCValue("<input type='button' class='btnPwc btnS' onclick='openPrjt(\""+val+"\")' value='바로가기' style='cursor:pointer'>", val);
    }
}
eXcell_btnOpen2.prototype = new eXcell;// nests all other methods from the base class


function eXcell_btnAprv(cell){ //the eXcell name is defined here
    if (cell){                // the default pattern, just copy it
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function(){}  //read-only cell doesn't have edit method
    // the cell is read-only, so it's always in the disabled state
    this.isDisabled = function(){ return true; }
    this.setValue=function(val){
    	const rowId = this.cell.parentNode.idd;
    	
        this.setCValue("<input type='button' class='btnPwc btnS' onclick='openPrjt(\""+val+"\")' value='결재문서 보기'>");                                      
    }
}
eXcell_btnAprv.prototype = new eXcell;// nests all other methods from the base class


dhtmlXGridObject.prototype._in_header_select_filter_strict=function(t,i){
	t.innerHTML="<select style='width:100%'></select>";
	t.onclick=function(e){ (e||event).cancelBubble=true; return false; }
	this.makeFilter(t.firstChild,i);
	var combos = this.combos;
	t.firstChild._filter=function(){ 
		var value = t.firstChild.value;
		if (!value) return "";
		if (combos[i])
            value = combos[i].keys[combos[i].values._dhx_find(value)];
       	value = value.toLowerCase();
            
		return function(val){
			return (val.toString().toLowerCase()==value); 
		};
	};
	this._filters_ready();
}

dhtmlXGridObject.prototype.makeFilter=function(id,column,preserve){
	if (!this.filters) this.filters=[];
	if (typeof(id)!="object")
		id=document.getElementById(id);
	if(!id) return;
	var self=this;
	
	if (!id.style.width) id.style.width = "100%";
		
	if (id.tagName=='SELECT'){
		this.filters.push([id,column]);
		this._loadSelectOptins(id,column);
		id.onchange=function(){
			self.filterByAll();
		}
		if(_isIE)
			id.style.marginTop="1px";
			
		this.attachEvent("onEditCell",function(stage,a,ind){ 
			this._build_m_order();
			if (stage==2 && this.filters && ( this._m_order?(ind==this._m_order[column]):(ind==column) ))
				this._loadSelectOptins(id,column);
			return true;
		});
	} 
	else if (id.tagName=='INPUT'){
		this.filters.push([id,column]);
		id.old_value = id.value='';
		id.onkeydown=function(){
			if (this._timer) window.clearTimeout(this._timer);
			this._timer=window.setTimeout(function(){
				if (id.value != id.old_value){
					self.filterByAll();
					id.old_value=id.value;
				}
			},500);
		};
	}
	else if (id.tagName=='DIV'){
		this.filters.push([id,column]);
		id.style.padding="0px";id.style.margin="0px";
		if (!window.dhx_globalImgPath) window.dhx_globalImgPath=this.imgURL;
		var z=new dhtmlXCombo(id,"_filter","100%");
		z.filterSelfA=z.filterSelf;
		z.filterSelf=function(){
			if (this.getSelectedIndex()==0) this.setComboText("");
			this.filterSelfA.apply(this,arguments);
			this.optionsArr[0].hide(false);	
		}
         
         
		z.enableFilteringMode(true);
		id.combo=z;
		id.value="";
		
		this._loadComboOptins(id,column);
		z.attachEvent("onChange",function(){
			id.value=z.getSelectedValue();
			if (id.value === null) id.value = "";
			self.filterByAll();
		});
	}
	if (id.parentNode)
		id.parentNode.className+=" filter";
	
	this._filters_ready(); //set event handlers
}

dhtmlXGridObject.prototype.toExcel = function(url,mode,header,footer,rows) {
	if (!document.getElementById('ifr')) {
		var ifr = document.createElement('iframe');
		ifr.style.display = 'none';
		ifr.setAttribute('name', 'dhx_export_iframe');
		ifr.setAttribute('src', '');
		ifr.setAttribute('id', 'dhx_export_iframe');
		document.body.appendChild(ifr);
	}

	var target = " target=\"dhx_export_iframe\"";
	if(this.toPDF(url,mode,header,footer,rows,target)){
		return true;
	}
}

function eXcell_toggle(cell){ //the eXcell name is defined here
	if (cell){                // the default pattern, just copy it
        this.cell = cell;
        this.grid = this.cell.parentNode.grid;
    }
    this.edit = function(){}  //read-only cell doesn't have edit method
    // the cell is read-only, so it's always in the disabled state
    this.isDisabled = function(){ return true; }
    this.setValue=function(val){
    	let component; 
    	
    	if(val == "Y") component = "<div class='frmInp'><input type='radio' value='Y' checked><label><span style='display:none'>Y</span></label><div>"
    	else component = "<div class='frmInp'><input type='radio' value='N'><label></label><span style='display:none'>N</span></div>"
    			
    	this.setCValue(component, val);                                      
    }
    this.getValue=function(){
    	return $(this.cell.childNodes[0]).find('input').val();
    }
}
eXcell_toggle.prototype = new eXcell;// nests all other methods from the base class
