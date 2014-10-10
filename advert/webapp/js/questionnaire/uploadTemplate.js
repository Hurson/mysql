var sub=0;
$(document).ready(function(){
	$('#uploadForm').submit(function() {
		var options = { 
				url:'uploadTemplate.do',
				type:'post',
		        success:function(responseText){
					sub=0;
					var flag = responseText.flag;
					var msg = responseText.msg;
					if(flag==0||flag==1||flag==3){
						alert(msg);
					}else if(flag==2){
						var con = confirm(msg);
						if(con){
							repeatUpload();
						}else{
							deleteZip();
						}
					}				
				}, 
				error:function(){
					sub=0;
					alert("服务器异常，请稍后重试！");
				},
		        dataType:'json' 
		    };
		
	    $(this).ajaxSubmit(options); 
	    return false; 
	});
});
function repeatUpload(){
	$.ajax({
		type:"post",
		url: 'saveTemplate.do',
		success:function(responseText){
			alert(responseText);
		},
		dataType:'text',
		data:{saveFlag:'1'},
		error:function(){
			alert("服务器异常，请稍后重试！");
		}
	});
}

function deleteZip(){
	$.ajax({
		type:"post",
		url: 'deleteZip.do',
	});
}
/** 模板上传提交 */
function firstSubmit(){
	if(sub==0){
		var templateName = $("#templateName").val();
		var zipName = $("#templateFile").val();
		if ($.trim(templateName).length < 1){
			alert('模板名称不能为空!');
			return;
		}
		if(getZip(zipName) && validatorZipName(zipName)){
			$('#uploadForm').submit();
			sub=1;	
		}else{ // 判断模板是不是.zip
			if(!getZip(zipName)){
		  		alert("上传的不是zip文件!请重新选择上传文件！");
			}else{
				alert("上传的zip文件名包含特殊字符！请重新选择上传文件！");
			}
		}
	}else{
		alert('请不要重复提交');
	}
}
	
/** 判断模板格式 */
function getZip(zip_url){
	var zipname = zip_url.substring(zip_url.lastIndexOf()+1,zip_url.length);
	var zip_f = zipname.substring(zipname.lastIndexOf(".")+1,zipname.length);
	if(zip_f == "zip"){
	 	return true;
	}
	return false;
}
/** 判断模板名称 */
function validatorZipName(zip_url) {
	var zipname = zip_url.substring(zip_url.lastIndexOf('\\')+1,zip_url.length-4);
	var reg = /^[a-zA-Z0-9_-]*$/;   // 只允许数字、英文、下划线、中划线
	var result;
	result = reg.test(zipname);
	return result;
}