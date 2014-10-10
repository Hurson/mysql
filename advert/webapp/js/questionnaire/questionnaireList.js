function deleteQuestionnaire(qId){
	var con = confirm("确定要删除吗？");
	if(con){
		$.ajax({
			type:"post",
			url: 'deleteQuestionnaire.do',
			success:function(responseText){
				alert(responseText);
				window.location.href="listQuestionnaire.do";
			},
			dataType:'text',
			data:{id:qId},
			error:function(){
				alert("服务器异常，请稍后重试！");
			}
		});
	}
}
function preview(path,qId){
	var path = path +qId+"/"+qId+".html";
	window.open(path);
}