<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />

<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/demos.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/position/speciCommon.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/tools.js"></script>

<title>广告系统</title>

<script>
$(function(){
	init();
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    bindSubmit('#addForm');
    
    $("#system-dialog").hide();
    
    $("#addButton").click(function(){
    	if(validate()){
     		$("#addForm").submit();
     		}
    });
    
    $("#updateButton").click(function(){
    	if(validate()){
     		$("#addForm").submit();
     		}
    });
    
});

function init(){

	if(!$.isEmptyObject(parent.id)){
		$('#id').val(parent.id+'');
		$('#addButton').remove();
	}
	
	if(!$.isEmptyObject(parent.type)){
		$('#type').val(parent.type+'');
		$('#addButton').remove();
	}
	
	if(!$.isEmptyObject(parent.fileSize)){
		$('#fileSize').val(parent.fileSize+'');
		$('#addButton').remove();
	}
	
	if(!$.isEmptyObject(parent.optionNumber)){
		$('#optionNumber').val(parent.optionNumber+'');
		$('#addButton').remove();
	}
	
	if(!$.isEmptyObject(parent.maxLength)){
		$('#maxLength').val(parent.maxLength+'');
		$('#addButton').remove();
	}
	
	if(!$.isEmptyObject(parent.excludeContent)){
		$('#excludeContent').val(parent.excludeContent+'');
		$('#addButton').remove();
	}

}

function bindSubmit(formName) {
    	var options = {
            success: showResponse,
			error: showError
        };

        $(formName).submit(function () {
        	$(this).ajaxSubmit(options);
            return false;
        });
}

function showResponse(responseText, statusText, xhr, $form) {
	messagebox(responseText);
    bindSubmit();
}

function showError(xhr, ajaxOptions, thrownError) {
	messagebox("出错了!" + thrownError);
    bindSubmit();
}

function messagebox(msg) {
	
	var responseMsg = eval('(' + msg + ')');
	if(responseMsg!=null){
		if(responseMsg.method=='save'){
			if(responseMsg.flag=='success'){
				createDialog('保存成功');
				removeAddButton(responseMsg.dataType);
				fillPrimaryId(responseMsg);
			}else{
				createDialog('保存失败');
			}	
		}else if(responseMsg.method='update'){
			if(responseMsg.flag=='success'){
				createDialog('更新成功');
				fillPrimaryId(responseMsg);
			}else{
				createDialog('更新失败');
			}
		}	
	}
}

function validate(){
	var flag = true;
	if($.isEmptyObject($('#type').val())){
		flag = false;
		createDialog('类型不能为空');
		return flag;
	}
	if($.isEmptyObject($('#fileSize').val())){
		flag = false;
		createDialog('问题个数不能为空');
		return flag;
	}else{
		flag = validateNumber($('#fileSize').val());
		if(!flag){
			createDialog('问题个数必须为数字');
			return flag;
		}
	}
	if($.isEmptyObject($('#optionNumber').val())){
		flag = false;
		createDialog('每个问题选项个数不能为空');
		return flag;
	}else{
		flag = validateNumber($('#optionNumber').val());
		if(!flag){
			createDialog('每个问题选项个数必须为数字');
			return flag;
		}
	}
	if($.isEmptyObject($('#maxLength').val())){
		flag = false;
		createDialog('问题最大字数不能为空');
		return flag;
	}else{
		flag = validateNumber($('#maxLength').val());
		if(!flag){
			createDialog('问题最大字数数必须为数字');
			return flag;
		}
	}
	if($.isEmptyObject($('#excludeContent').val())){
		flag = false;
		createDialog('过滤内容不能为空');
		return flag;
	}
	return flag;
}

</script>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6;}
#loading{background-image:url(<%=path %>/images/jqueryui/loading.gif);background-position:0px 0px;background-repeat:no-repeat; position:absolute;width:50px;height:50px;top:60%;left:50%;margin-left:-25px;text-align:center;}

</style>
</head>

<body onload="">

	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		
		<tr>
			<td style="padding:1px;">
				<form action="saveQuestion.do"  id="addForm" name="addForm" method="post">
					<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
						<tr>
							<td class="td_label" style="width:270px">调查问卷类型：</td>
							<td class="td_input">
								<input id="id" name="object.id" type="hidden"/>		
								<input id="type" name="object.type" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>		
							</td>
							<td class="td_label" style="width:270px">问题个数：</td>
							<td class="td_input">
								<input id="fileSize" name="object.fileSize" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>		
							</td>
						</tr>
						<tr>
							<td class="td_label" style="width:270px">每个问题选项个数：</td>
							<td class="td_input">
								<input id="optionNumber" name="object.optionNumber" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="5"/>		
							</td>
							<td class="td_label" style="width:270px">问题最大字数：</td>
							<td class="td_input">
								<input id="maxLength" name="object.maxLength" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="5"/>	
							</td>
						</tr>
						<tr>
							<td class="td_label" style="width:270px">过滤内容：</td>
							<td class="td_input" colspan="3">
								<input id="excludeContent" name="object.excludeContent" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>	
							</td>
						</tr>
						<tr>
							<td class="formBottom" colspan="99">
								<input id="addButton" 
								type="button" 
								class="b_add" 
								onfocus="blur()"/>
								<input id="updateButton" 
								type="button" 
								class="b_edit" 
								onfocus="blur()"/>
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>
	<div id="system-dialog" title="友情提示">
	  <p>
	    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
	    <span id="content"></span>
	  </p>
	</div>
</body>
</html>
