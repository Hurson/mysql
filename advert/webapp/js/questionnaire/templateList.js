function showImage(showImagePath,name){
	$("#showName").html(name);
	$("#showImage").attr("src",showImagePath);
	$("#showImageDiv").show("slow");
}
function closeShowImage(){
	$("#showImageDiv").hide("slow");
}

function deleteTemplate(templateId){
	var con = confirm("确定要删除吗？");
	if(con){
		$.ajax({
			type:"post",
			url: 'deleteTemplate.do',
			success:function(responseText){
				alert(responseText);
				window.location.href="listTemplate.do";
			},
			dataType:'text',
			data:{templateId:templateId},
			error:function(){
				alert("服务器异常，请稍后重试！");
			}
		});
	}
}
function createQuestionnaire(templateId){
	window.location.href="addQuestionnaire.do?templateId="+templateId;
}