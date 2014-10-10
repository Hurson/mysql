var resourcePath='';
$(function(){
	resourcePath=$('#projetPath').val();
	$("#deletePreciseButton").click(function(){
		var alreadyChoose = "";
		var con = window.confirm("确定要删除吗？");
		$("input[name='checkBoxElement']").each(function(){
	        if($(this).is(':checked')){
	            alreadyChoose += $(this).val() + ",";
	        }
		});
		if(con){
			$.ajax({
				type:"post",
				url: 'deletePrecise.do?method=deletePrecise',
				dataType:'json',
				data:{alreadyChoose:alreadyChoose},
				success:function(responseText){
					alert("删除成功!");
					window.location.href=resourcePath+"/page/precise/listPrecise.do";
				},
				error:function(){
					alert("服务器异常，请稍后重试！");
				}
			});
		}
     });
});
function addPrecise(){
	window.location.href=resourcePath+"/page/precise/addPrecise.do";
}

function updatePrecise(id){
	window.location.href=resourcePath+"/page/precise/updatePrecise.do?id="+id;
}
