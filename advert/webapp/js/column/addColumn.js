var sub = 0;
$(document).ready(function(){
	$('#saveForm').submit(function() {
		var options = { 
				url:'addColumn.do?method=addColumn',
				type:'post',
		        success:function(responseText){
					sub=0;
					alert("添加成功!");
					window.location.href="columnList.do";
				}, 
				error:function(){
					sub=0;
					alert("服务器异常，请稍后重试！");
				},
		        dataType:'html' 
		    };
		
	    $(this).ajaxSubmit(options); 
	    return false; 
	});
});

function firstSubmit(){
	if(sub==0){
		/*
		var pwd1 = $("#pwd1").val();
		var pwd2 = $("#pwd2").val();
		if(pwd1 != pwd2){
			alert("密码和确认密码不一致！");
			return;
		}*/
		$('#saveForm').submit();
		sub=1;
	}else{
		alert('请不要重复提交');
	}
	
}
	
	