dhtmlXGridObject.prototype._in_header_select_filter_strict=function(t,i){
	//t.innerHTML="<select style='width:90%; font-size:8pt; font-family:Tahoma;'></select>";	//스타일 삭제함.
	t.innerHTML="<select style='width:90%;'></select>";
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
