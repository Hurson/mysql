var resourcePath='';
$(function(){
	resourcePath=$('#projetPath').val();
});
function addRule(){
	window.location.href=resourcePath+"/page/marketingRule/addMarketingRule.do";
}
function deleteRule(id){
	var con = window.confirm("确定要删除吗？");
	if(con){
		$.ajax({
			type:"post",
			url: 'deleteMarketingRule.do?method=deleteMarketingRule',
			dataType:'json',
			data:{id:id},
			success:function(responseText){
				alert("删除成功!");
				window.location.href=resourcePath+"/page/marketingrule/listMarketingRule.do";
			},
			error:function(){
				alert("服务器异常，请稍后重试！");
			}
		});
	}
}

function upLine(id){
	var con = window.confirm("确定要上线吗？");
	if(con){
		$.ajax({
			type:"post",
			url: 'upLine.do?method=upLineMarketingRule',
			dataType:'json',
			data:{id:id},
			success:function(responseText){
				alert("上线成功!");
				window.location.href=resourcePath+"/page/marketingrule/listMarketingRule.do";
			},
			error:function(){
				alert("服务器异常，请稍后重试！");
			}
		});
	}
}

function downLine(id){
	var con = window.confirm("确定要下线吗？");
	if(con){
		$.ajax({
			type:"post",
			url: 'downLine.do?method=downLineMarketingRule',
			dataType:'json',
			data:{id:id},
			success:function(responseText){
				alert("下线成功!");
				window.location.href=resourcePath+"/page/marketingrule/listMarketingRule.do";
			},
			error:function(){
				alert("服务器异常，请稍后重试！");
			}
		});
	}
}

function updateRule(id){
	window.location.href=resourcePath+"/page/marketingRule/updateMarketingRule.do?ruleId="+id;
}
