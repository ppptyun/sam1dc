var FileUpload = function(config){
	
	var m_classNm 			= "excel_import";
	var m_fileUploadUrl 	= "comn/FileUpload.jsp";
	var m_accept = {
			xls:"application/vnd.ms-excel",
			xlsx:"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
			csv:".csv"
	}
	
	var m_filtype			= m_accept["xls"];
	var m_allow_extensions 	= "*";	// 허용되는 파일 확장자
	var f_callback			= null;	// callback 함수
	var m_fileObj 			= null;	// 업로드할 File 객체
	
	
	// 초기화
	if(config){
		if(config.allowExtentions)
			m_allow_extensions = config.allowExtentions;
		
		if(config.callback)
			f_callback = config.callback;
		
		if(config.download)
			f_download = config.download;
		
		if(config.filetype){
			var tmp = [];
			for(var i=0; i<config.filetype.length; i++){
				if(m_accept[config.filetype[i]]){
					tmp[tmp.length] = m_accept[config.filetype[i]];  	
				}
			}
			if(tmp.length > 0){
				m_filtype = tmp.join(", ");
			}
		}
	}
	
	
	function getFileUploalModalHtml(){
		$input_file = $("<input type=\"file\" name=\"file1\" accept=\""+ m_filtype +"\" size=\"20\" />");
		$input_file.change(function(){
			m_fileObj = this.files[0];
			if(m_fileObj.name != ""){
				if(!checkFile(m_fileObj.name)){
					alert("허용되지 않는 파일입니다.\n\n허용되는 파일 확장자 : " + m_allow_extensions.join(", "));
					$(this).val("");
					m_fileObj = null;
				}	
			}
		})
		
		return $input_file;
	}
	
	function checkFile(filepath){
		var fileExt =  filepath.substring(filepath.lastIndexOf(".")+1);
		if(m_allow_extensions != "*"){
			for(var i=0; i<m_allow_extensions.length; i++){
				if(m_allow_extensions[i] == fileExt) return true;
			}
		}
		return false;
	}
	
	function validationCheck(){
		if(!m_fileObj){
			if(m_allow_extensions != "*"){
				alert("파일을 선택하세요.\n\n확장자 : " + m_allow_extensions.join(", "));
			}else{
				alert("파일을 선택하세요.");	
			}
			
			return false;
		}else{
			return true;
		}
	}
	
	function upload(){
		if(validationCheck()){
			$("#div-progress").addClass("progress-active");
			
			var data = new FormData();
			data.append("file", m_fileObj);
			$.ajax({
				url:m_fileUploadUrl,
				data:data,
				type:"post",
				cache: false,
				processData: false,
                contentType: false,
                success:function(filepath){
                	if(f_callback) f_callback(filepath);
                },
                error:function(data){
                	alert("에러발생")
                }
			})
		}
	}
	
	return {
		getFileUploalModalHtml:getFileUploalModalHtml,
		upload:upload
	}
}