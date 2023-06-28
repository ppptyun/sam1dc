function ExcelExport(url, param, excelOption){
	this.url = url;
	if(!excelOption){
		excelOption = $.extend({}, param);
		param = {}
	};
	
	
	this.param = param;
	this.filename = excelOption.filename||"data";
	this.column = [];
	
	if(param.header){		
		this.__header = excelOption.header;
		this._parse();
	}else{
		alert("Header 정보가 없습니다.")
		return null;
	}
}

ExcelExport.prototype._parse = function(){
	let header = this.__header;
	
	for(let i=0; i<header.length; i++){
		let column = {
			id:header[i].id,
			type:header[i].type,
			format:header[i].format,
			width:header[i].width,
			cells:[]
		}
		
		if(typeof header[i].label === 'string'){
			this.header.push({text: header[i].label, rspan: 1, cspan: 1});
		}else{
			for(let j=0; j<header[i].label.length; j++){
				let text = header[i].label[j];
				let span = this.__getSpan(i, j);
				this.header.push({
					value: (text ==='#rspan' || text === '#rspan')?null:text, 
					rspan: span.rspan, 
					cspan: span.cspan
				});
			}
		}
		
		this.column.push(column);
	}
}

ExcelExport.prototype.test = function(){
	console.log(this.column, this.header);
}

ExcelExport.prototype.download = function(){
	let dfd = $.Deferred();
	const thisObj = this;
	thisObj.showLoading();
	
	$.ajax({
		url: thisObj.getContextPath() + this.url,
		data: {
			param: thisObj.param,
			excelOption: {
				filename: thisObj.filename,
				columns: thisObj.column,
			}
		},
		type: 'post',
		processData: false, 
		contentType: false,
		dataType: "json",
		success:function(data){
			if(data.result){
				if(data.result.msg) {
					thisObj.MessageBox(data.result.msg, function(){
						if(data.result.status == "success"){
							dfd.resolve();
						}else{
							dfd.reject();
						}	
					})
				}else{						
					if(data.result.status == "success"){
						dfd.resolve();
					}else{
						dfd.reject();
					}
				}
			}
		},
		error: function(){
			console.log(xhr)
			console.log("status", textStatus)
			console.log("error", error)
			thisObj.MessageBox(["데이터 처리 도중 오류가 발생하였습니다."], function(){
				dfd.reject();	
			});	
		},
		complete: function(){
			thisObj.hideLoading();
		}
	})
	
	return dfd.promise();
}
ExcelExport.prototype.__getSpan = function(colIdx, rowIdx){
	let header = this.__header;
	let labelText = this.__header[colIdx].label[rowIdx];
	let rspan = 1;
	let cspan = 1;
	
	if(labelText === '#cspan'){
		// rspan 있는지 파악
		for(j=(rowIdx+1); j<header[colIdx].label.length; j++){
			if(header[colIdx].label[j] === '#rspan'){
				rspan++;	
			}else{
				break;
			}
		}
	}else if(labelText === '#rspan'){
		// cspan 있는지 파악
		for(let i=(colIdx+1) ; i<header.length; i++){
			if(header[i].label[rowIdx] === "#cspan"){				
				cspan++;
			}else{
				break;
			}
		}
	}else{
		// rspan, cspan 모두 파악
		for(j=(rowIdx+1); j<header[colIdx].label.length; j++){
			if(header[colIdx].label[j] === '#rspan'){
				rspan++;	
			}else{
				break;
			}
		}
		for(let i=(colIdx+1) ; i<header.length; i++){
			if(header[i].label[rowIdx] === "#cspan"){				
				cspan++;
			}else{
				break;
			}
		}
	}
	return {
		rspan: rspan,
		cspan: cspan
	}
}