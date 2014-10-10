<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />


<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="<%=path%>/js/material/uploadMaterial.js"></script>
<script type="text/javascript" src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>


<script type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/addOrUpdate.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />


<title>广告系统</title>

<script type="text/javascript">


$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    $("#file_id").uploadify({
		'uploader':'<%=path%>/js/jquery/upload/image/uploadify.swf',
		'script':'uploadQuestionTemplate.do?method=uploadQuestionTemplate',
		'cancelImg':'<%=path%>/js/jquery/upload/image/cancel.png',
		'folder':'/uploadFiles',
		'queueID':'fileQueue',
		'buttonImg':'<%=path%>/js/jquery/upload/image/uploadify.jpg',
		'fileDataName': 'backgroundImage',
		'auto':true,
		'multi':false,
		'fileExt':'*.zip',
		'fileDesc':'*.zip',
		 'displayData':'speed',
		 'width':'76',
    	'height':'23',
		'onComplete':function(event,queueID,fileObj,response,data){
			var json = eval('(' + response + ')');
			if(json!=null){
					if(json.position=='local'){
						if(json.result=='true'){
							//$("#materialViewDivImg").attr("src","<%=path%>/images/material/"+json.viewpath);
							$("#backgroundImage").val(json.filepath);
							writeMessage();
						}else{
							alert('上传问卷模板失败');
						}
					}
				}	
		}
	});
	

	
 
});



function closeSavePane() {
    window.location.href = "<%=path %>/page/material/queryQuestionTemplateList.do";
}

function submitForm(){   
        if(document.getElementById("questionTemplate.templatePackageName").value==""){
			alert("模板名称不能为空!");
			return ;
		}
   
        var localFilePath = getObj("backgroundImage").value; 
        $("#localFilePath").val(localFilePath);

		if($("#localFilePath").val()==""){
			alert("上传的文件不能为空!");
			return ;
		}
        
        //效验模板名称是否重复   
     var templateName = document.getElementById("questionTemplate.templatePackageName").value;	
     var templateId = document.getElementById("questionTemplate.id").value; 
	 $.ajax({
                type:"post",
                async : false,
                url:"<%=request.getContextPath()%>/page/material/checkQuestionTemplateExist.do?",
                data:{"templateName":templateName,"templateId":templateId},//Ajax传递的参数
                success:function(mess)
                {
                	if(mess=="0")// 如果获取的数据不为空
                    {
                		document.getElementById("saveForm").submit();
                		//alert("ok");
                    }
                    else
                    {
						alert("模板名称已存在，请重新输入！");
						return;
                    }
                   
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });

        //document.getElementById("saveForm").submit();
    }

</script>
<style>
	.easyDialog_wrapper{ width:1000px;height:400px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
</head>
<body class="mainBody">
<form action="<%=path %>/page/meterial/saveQuestionTemplate.do" method="post" id="saveForm">	
<div class="path">首页 >> 素材管理 >> 新增问卷模板</div>
<div class="searchContent" >
<div class="listDetail">
<div style="position: relative">	
<table>
		    	<tr>
		    	   <td>
		               <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px">
		                 <tr class="title"><td colspan="4">问卷模板信息</td></tr>		           
		                 <tr>
		                     <td align="right"><span class="required">*</span>模板名称：</td>
		                     <td>
		                         <input id="questionTemplate.id" name="questionTemplate.id"  type="hidden" value="${questionTemplate.id}"/>
		                         <input id="questionTemplate.createTime" name="questionTemplate.createTime"  type="hidden" value="${questionTemplate.createTime}"/>
		                	     <input id="questionTemplate.templatePackageName" name="questionTemplate.templatePackageName" value="${questionTemplate.templatePackageName}" type="text" maxlength="20" />
		                	     <input id="questionTemplate.templateName" name="questionTemplate.templateName"  type="hidden" value="${questionTemplate.templateName}"/>
		                     </td>
		                     <td width="15%" align="right"><span class="required"></span>选择模板：</td>
		                     <td>	            	          
							          <input id="backgroundImage" name="" value="${questionTemplate.htmlPath}" type="text"  /><input id="file_id" name="upload" type="file" />
							          <input id="localFilePath" name="localFilePath"  type="hidden" />
							          <input id="htmlPath" name="htmlPath"  type="hidden" value="${questionTemplate.htmlPath}"/>
							          <input id="questionTemplate.htmlPath" name="questionTemplate.htmlPath"  type="hidden" value="${questionTemplate.htmlPath}"/>
		                     </td>	                
		                 </tr>


		                  <tr>
		                     <td align="right"><span class="required"></span>模板描述：</td>
		                     <td colspan="3">
		                	     <textarea id="questionTemplate.remark" name="questionTemplate.remark" cols="40" rows="3" maxlength="100">${questionTemplate.remark}</textarea>		              	
		                     </td>
		                 </tr>
		                 <tr id="b1">
		            	     <td colspan="4">
		            		     <input type="button" value="确定" class="btn" onclick="submitForm();"/>
		            		     <input type="button" value="取消" class="btn" onclick="javascript:closeSavePane();"/>
		            	     </td>
		                 </tr>
		             </table>
		          </td>
		        </tr>
		        
		        


</table>
</div>
</div>
</div>
</form>
</body>