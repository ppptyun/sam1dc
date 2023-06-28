function SHView(initObj){
	
	this.id = initObj.id; 		// id
	this.Dep = {target : null}; // 현재 종속(종속성 추적을 위함)
	this.data = {};				// 데이터
	this.domObj = {}; 			// sh-name 객체
	this.condDomObj = {}; 		// sh-name 객체
	this.event = initObj.event; // event 정의
	this.validation = initObj.validation;
	let thisData = this.data;
	
	
	let dateCtrl = new Helper.DateCtrl();
	this.validMethod = {
		required: function(value, obj){
			let isValid = true;
			let isNull = (value === null || value === undefined || value === '');
			
			if(typeof obj == 'function'){
				if(obj(value) && isNull){
					isValid = false;
				}
			}else if(typeof obj == 'boolean'){
				if(obj && isNull) isValid = false;
			}else{
				if(isNull) isValid = false;
			}
			return isValid;
		},
		min: function(value, obj){
			if(typeof obj == 'function'){
				return obj(value);
			}else{
				return Number(value) >= Number(obj);	
			}
		},
		max: function(value, obj){
			if(typeof obj == 'function'){
				return obj(value);
			}else{
				return Number(value) <= Number(obj);	
			}
		},
		minDate: function(value, obj){
			if(typeof obj == 'function'){
				return obj(value);
			}else{
				return dateCtrl.toTimeObject(value) >= dateCtrl.toTimeObject(obj);	
			}
		},
		maxDate: function(value, obj){
			if(typeof obj == 'function'){
				return obj(value);
			}else{
				return dateCtrl.toTimeObject(value) <= dateCtrl.toTimeObject(obj);	
			}
		},
	},
	
	this.props = [];
	for(let prop in initObj.computed) if(this.props.indexOf(prop) == -1) this.props.push(prop);
	for(let prop in initObj.data) if(this.props.indexOf(prop) == -1) this.props.push(prop);
	
	
	for(let i=0; i< this.props.length; i++){
		let key = this.props[i];
		if(initObj.computed[key]){
			this.__defineComputed(this.data, key, $.proxy(initObj.computed[key], this.data));	
		}else{
			this.__defineReactive(this.data, key, initObj.data[key]);
		}
	}
}

SHView.prototype = {
	init:function(_callback){
		let callback = _callback;
		let thisObj = this;
		$.when(this.__initDomObj(), this.__initCondDom(), this.__bindEvent())
		.done(function(){
			$("#" + thisObj.id + " [sh-test]").removeAttr('sh-test sh-dotype');
			$("#" + thisObj.id + " [sh-click]").removeAttr('sh-click');
			$("#" + thisObj.id + " [sh-select]").removeAttr('sh-select');
			$("#" + thisObj.id + " [sh-change]").removeAttr('sh-change');
			$("#" + thisObj.id + " [sh-name]").removeAttr('sh-name sh-format sh-type');
			thisObj.__refreshAll();
			if(callback) {
				callback();
			}
		})
	},
	__defineReactive: function(obj, key, val){
		let thisObj = this;
		let deps = [];
		Object.defineProperty(obj, key, {
			get : function() {
				if (thisObj.Dep.target && deps.indexOf(thisObj.Dep.target) == -1) {
					deps.push(thisObj.Dep.target);
				}
				return val;
			},
			set : function(newValue) {
				val = newValue;
				thisObj.__refreshView(key, val);
				thisObj.__refreshCondView(key); 
				for (let i = 0; i < deps.length; i++) deps[i]();
			},
			enumerable: true,
		});
	},
	__defineComputed: function(obj, key, computeFunc) {
		let thisObj = this;
		let onDependencyUpdated = function() {
			let value = computeFunc();
			thisObj.__refreshCondView(key);
			thisObj.__refreshView(key, value);
		}
		Object.defineProperty(obj, key, {
			get : function() {
				thisObj.Dep.target = onDependencyUpdated;
				let value = computeFunc();
				thisObj.Dep.target = null;
				return value;
			},
			set : function() {},
			enumerable: true,
		});
	},
	__refreshCondView: function(name){
		let thisObj = this;
		let condDomList = thisObj.__getCondDomObj(name);
		
		document.__prjtInfo = thisObj.data;
		
		let regExp_AND = /\sand\s/gi;
		let regExp_OR = /\sor\s/gi;
		let regExp_VAR = /(\$[a-z0-9\-\_]+[a-z0-9])/gi;
		if(condDomList){
			for(let i=0; i<condDomList.length; i++){
				let $obj = condDomList[i].$obj;
				let type = condDomList[i].type;
				let test = condDomList[i].test;
				let testStr = test.replace(regExp_VAR, 'document\.\_\_prjtInfo.$1').replace(/document\.\_\_prjtInfo\.\$/g, 'document\.\_\_prjtInfo\.').replace(regExp_AND, ' && ').replace(regExp_OR, ' || ');
				
				let isTrue = new Function("return " + testStr + ";")();
				
				if(type=='hide'){
					thisObj.setHidden($obj, isTrue);
				}else if(type=='disable'){
					thisObj.setDisabled($obj, isTrue);
				}
			}
		}
		delete document.__prjtInfo;
	},
	__refreshView: function(name, value){ // data => dom
		let thisObj = this;
		let list = thisObj.__getDomObj(name);
		if(list){
			for(let i=0; i<list.length; i++){
				let $obj = list[i].$obj;
				let format = list[i].format;
				let type = list[i].type;
				let test = list[i].test;
				
				if($obj.is('select')){
					let instance = $obj.closest('.selectbox').find('a').getInstance();
					if(instance && instance.getValueSB() != value){
						instance.setValueSB(value);	
					}
				}else if($obj.is('input:text, input[type="number"], textarea')){
					if(type == 'number'){
						if(format){
							if(value == null){
								$obj.val(null);
							}else{
								let num = value.toString().match(/\-?\d+(?=\.)?/g);		// 정수영역
								let num2 = value.toString().match(/\.\d+/g);			// 소수점 영역
								$obj.val($.number(num[0]) + (num2?num2[0]:''));									
							}
						}else{
							if(!isNaN(Number(value))){
								$obj.val(value);	
							}else{
								$obj.val(null);	
							}
						}
					}else{
						$obj.val(value);	
					}
				}else{
					if(value === null){
						$obj.text('');
					}else{						
						if(format == "#,##0"){
							$obj.text($.number(Number(value), 0));
						}else if(format == "#,##0.00"){
							$obj.text($.number(Number(value), 2));
						}else if(format == "#,##0.000"){
							$obj.text($.number(Number(value), 3));
						}else{						
							$obj.text(value);
						}
					}
				}
			}
		}
	},
	__bindEvent: function(eventObj){
		let thisObj = this;
		let dfd = $.Deferred();
		
		$.when(
			$("#" + thisObj.id + " [sh-click]").each(function(idx, obj){
				let eventName = $(obj).attr('sh-click');
				if(thisObj.event && thisObj.event.click && thisObj.event.click[eventName]){
					$(obj).off('click.sh-click').on('click.sh-click', $.proxy(thisObj.event.click[eventName], thisObj.data));	
				}
			}),
			$("#" + thisObj.id + " [sh-change]").each(function(idx, obj){
				let name = $(obj).attr('sh-name');
				let eventName = $(obj).attr('sh-change');
				
				if(thisObj.event && thisObj.event.change && thisObj.event.change[eventName]){
					$(obj).off('change.sh-change').on('change.sh-change', function(e){
						thisObj.data[name] = e.target.value;
						$.proxy(thisObj.event.change[eventName], thisObj.data, e.target.value)();
					});
				}
			}),
			$("#" + thisObj.id + " [sh-select]").each(function(idx, obj){
				let name = $(obj).attr('sh-name');
				let selectName = $(obj).attr('sh-select');
				if(thisObj.event && thisObj.event.select && thisObj.event.select[selectName]){
					$(obj).closest('.selectbox').off('change.selectbox').on('change.selectbox', function(e, param){
						if (param === undefined) return;
						if(thisObj.data[name] != param.value){
							thisObj.data[name] = param.value;
							$.proxy(thisObj.event.select[selectName], thisObj.data, param.value)();
						}
					}) 
				}
			})
		).done(function(){
			dfd.resolve();
		})
		
		return dfd.promise();
	},
	__initDomObj: function(){
		let thisObj = this;
		let dfd = $.Deferred();
		
		$("#" + thisObj.id + " [sh-name]").each(function(idx, obj){
			let $obj = $(obj);
			let name = $obj.attr("sh-name");
			
			if($obj.is('select')){
				$obj.closest('.selectbox').on('change.selectbox', function(e, param){
					if (param === undefined) return;
					if(thisObj.data[name] != param.value) {
						thisObj.data[name] = param.value;
					}
				});
			}else if($obj.is("input, textarea")){
				let type = $obj.attr("sh-type");
				let format = $obj.attr('sh-format');
				
				$obj.off('change.sh-change').on('change.sh-change', function(e){
					e.preventDefault();
					
					let valStr = $.trim($(this).val());
					let value = valStr == ''?null:Number(valStr.toString().replace(/,|\s/g, '')); 
					
					if(type == 'number'){
						if(isNaN(value)){
							Helper.MessageBox(['숫자만 입력 가능합니다.']);
							thisObj.data[name] = null;
						}else{
							thisObj.data[name] = value;
						}	
					}else{
						thisObj.data[name] = $(this).val();
					}
				});
			}
			
			thisObj.__setDomObj(name, $obj);
			
		}).promise().done(function(){
			dfd.resolve();
		});
		
		return dfd.promise();
	},
	__initCondDom: function(){
		let thisObj = this;
		let dfd = $.Deferred();
		let regExp_AND = /\sand\s/gi;
		let regExp_OR = /\sor\s/gi;
		let regExp_VAR = /\$[a-z0-9\-\_]+[a-z0-9]/gi;
				
		
		$("#" + thisObj.id + " [sh-test]").each(function(idx, obj){
			let names = $(obj).attr('sh-test').match(regExp_VAR);
			if(names){
				for(let i=0; i<names.length; i++){
					let name = names[i].replace('$', '');
					thisObj.__setCondDomObj(name, $(obj));
				}	
			}
		}).promise().done(function(){
			dfd.resolve();
		});
		
		
		
		return dfd.promise();
	},
	__setDomObj:function(name, $obj){
		let domObj = this.domObj;
		if(!domObj[name]) domObj[name] = [];
		domObj[name].push({
			$obj: 	$obj,
			format: $obj.attr("sh-format"),
			type: 	$obj.attr("sh-type")||$obj.attr('type'),
			test: 	$obj.attr("sh-test")
		});
	},
	__getDomObj:function(name){
		let domObj = this.domObj;
		return domObj[name];
	},
	__setCondDomObj:function(name, $obj){
		let condDomObj = this.condDomObj;
		if(!condDomObj[name]) condDomObj[name] = [];
		let type = $obj.attr('sh-dotype')
		condDomObj[name].push({
			type: type,   
			$obj: $obj,
			test: $obj.attr("sh-test")
		});
	},
	__getCondDomObj:function(name){
		return this.condDomObj[name];
	},
	__refreshAll: function(){
		let thisObj = this;
		for(let key in thisObj.condDomObj){
			thisObj.__refreshCondView(key)
		}
		for(let key in thisObj.domObj){
			thisObj.__refreshView(key,  this.data[key])
		}
	},
	setHidden: function($obj, flag){
		let thisObj = this;
		if($obj.is('select')){
			if(flag){
				$obj.closest(".selectbox").hide();	
			}else{
				$obj.closest(".selectbox").show();
			}
		}else{
			if(flag){
				$obj.hide();	
			}else{
				$obj.show();
			}
		}
	},
	setDisabled: function($obj, flag){
		let thisObj = this;
		if($obj.is('select')){
			COMMON.formEvent.setDisabledSelBox($obj.closest('.selectbox'), flag);
		}else{
			$obj.prop('disabled', flag);
		}
	},
	addValidMethod: function(name, func){
		this.validMethod[name] = func;
	},
	validCheck: function(){
		let validMethod = this.validMethod;
		let data = this.data;
		let validation = this.validation;
		let rules = validation.rules;
		let msg = validation.messages;
		let isValid = true;
		let msgQueue = [];
		
		for(let field in rules){
			if(typeof rules[field] == 'string'){
				if(!validMethod[rules[field]](data[field])){
					isValid = false;
					if(msg[field]){
						msgQueue.push(msg[field][rules[field]]);
					}
				}
			}else{
				for(let rule in rules[field]){
					if(rule == 'required'){
						let isRequired;
						
						let obj = rules[field][rule];
						if(typeof obj == 'function'){
							isRequired = obj();
						}else{
							isRequired = obj
						}
						
						if(isRequired){
							if(!validMethod[rule](data[field], rules[field][rule])){
								isValid = false;
								if(msg[field] && msg[field][rule]){
									msgQueue.push(msg[field][rule])	
								}
							}	
						}else{
							break;
						}
						
					}else{						
						if(!validMethod[rule](data[field], rules[field][rule])){
							isValid = false;
							if(msg[field] && msg[field][rule]){
								msgQueue.push(msg[field][rule])	
							}
						}
					}
				}
			}
		}
		
		if(!isValid && msgQueue.length > 0){			
			Helper.MessageBox(msgQueue, {width: "400px"});
		}
		
		return isValid;
	},
}