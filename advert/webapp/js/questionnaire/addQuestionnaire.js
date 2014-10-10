var sub = 0;
$(document).ready(function(){
	$('#saveForm').submit(function() {
		var options = { 
				url:'saveQuestionnaire.do',
				type:'post',
		        success:function(responseText){
					sub=0;
					alert(responseText);
					window.location.href="listQuestionnaire.do";
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

function firstSubmit(questionNum){
	if(sub==0){
		var name = $("#name").val();
		var contractCode = $("#contractCode").val();
		var description = $("#description").val();
		if($.trim(name).length<1){
			alert("请输入问卷名称");
			return;
		}
		if($.trim(name).length>50){
			alert("问卷名称长度超过50字");
			return;
		}
		if(contractCode=="-1"){
			alert("请选择合同号");
			return;
		}
		if($.trim(description).length<1){
			alert("请输入问卷描述");
			return;
		}
		if($.trim(description).length>120){
			alert("问卷描述长度超过120字");
			return;
		}
		var summary = $("#summary").val();
		var picturePath = $("#picturePath").val();
		var videoPath = $("#videoPath").val();
		
		if(summary!=undefined){
			if($.trim(summary).length<1){
				alert("请输入问卷摘要内容");
				return;
			}
			if($.trim(summary).length>300){
				alert("问卷摘要内容大于300字");
				return;
			}
		}
		if(picturePath!=undefined){
			if($.trim(picturePath).length<1){
				alert("请选择要上传的图片文件");
				return;
			}
		}
		if(videoPath!=undefined){
			if($.trim(videoPath).length<1){
				alert("请选择要上传的视频文件");
				return;
			}
		}
		var questions = new Array();
		for(var i = 0;i<questionNum;i++){
			var question = $("#question"+i);
			if($.trim(question.val()).length >= 1){
				questions[i] = question;
				var optionNum = $("#optionNum"+i).val();
				var options = "";
				for(var j=0;j<optionNum;j++){
					var option = $("#option"+i+j).val();
					if($.trim(options).length<1&&$.trim(option).length >= 1){
						options = $.trim(option);
					}else{
						if($.trim(option).length >= 1){
							options=options+","+option;
						}
					}
				}
				if($.trim(options).length<1){
					alert("请输入问题选项内容");
					return;
				}
				$("#option"+i).val(options);
			}else{
				var que=0;
				for(var q=i;q<questionNum;q++){
					if($.trim($("#question"+q).val()).length >= 1){
						que = 1;
						break;
					}
				}
				if(que==1){
					alert("请按顺序输入问题及选项内容");
					return;
				}
				break;
			}
		}
		if(questions.length < 1){
			alert("请输入问题内容");
			return;
		}
		
		summary= summary.replace(/\n/g,"<br>");
		summary = summary.replace(/\s/g,"&nbsp;")
		$("#summary").val(summary);
		$('#saveForm').submit();
		sub=1;
	}else{
		alert('请不要重复提交');
	}
	
}
	