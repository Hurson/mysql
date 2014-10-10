<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"	href="<%=path %>/css/new/main.css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>

<title>编辑问卷广告位规格</title>
</head>
<script type="text/javascript">
	function save() {
		if (!validateBeforeSubmit()) {
	        return;
	    }
		$("#editForm").submit();
	}
	
	//表单验证
	function validateBeforeSubmit(){
		var optionNumber = $.trim($("#optionNumber").val());
		if(optionNumber!=''){
			if(!isIntegerNumber(optionNumber)){
				alert("问卷规格每个问题选项个数只能是数字！");
				$("#optionNumber").focus();
				return false;
			}
			if(optionNumber.length>5){
				alert("问卷规格每个问题选项个数长度最大只能是5位！");
				$("#optionNumber").focus();
				return false;
			}
		}
		var maxLength = $.trim($("#maxLength").val());
		if(maxLength!=''){
			if(!isIntegerNumber(maxLength)){
				alert("问卷规格问题最大字数只能是数字！");
				$("#maxLength").focus();
				return false;
			}
			if(maxLength.length>5){
				alert("问卷规格问题最大字数长度最大只能是10位！");
				$("#maxLength").focus();
				return false;
			}
		}
		var excludeContent = $.trim($("#excludeContent").val());
		if(excludeContent!=''){
			if(excludeContent.length>120){
				alert("问卷规格过滤内容长度必须在0-120字之间！");
				$("#excludeContent").focus();
				return false;
			}
		}
		return true;
	}

</script>
<body class="mainBody">
<form action="saveQuestionnaireSpecification.do" method="post" id="editForm">
<input type="hidden" name="id" value="${advertPosition.id}"  />
<input type="hidden" id="id" name="questionnaireSpe.id" value="${questionnaireSpe.id}"  />
<div class="detail">
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">问卷规格信息</td>
	</tr>
	<tr>
		<td width="12%" align="right">类型：</td>
		<td><input id="type" name="questionnaireSpe.type" value="${questionnaireSpe.type}" type="text" /></td>
		<td width="12%" align="right">问题个数：</td>
		<td><input id="fileSize" name="questionnaireSpe.fileSize" value="${questionnaireSpe.fileSize}" type="text" /></td>
	</tr>
	<tr class="sec">
		<td width="12%" align="right">每个问题选项个数：</td>
		<td><input id="optionNumber" name="questionnaireSpe.optionNumber" value="${questionnaireSpe.optionNumber}" type="text" /></td>
		<td width="12%" align="right">问题最大字数：</td>
		<td><input id="maxLength" name="questionnaireSpe.maxLength" value="${questionnaireSpe.maxLength}" type="text" />字节</td>
	</tr>
	<tr>
		<td width="12%" align="right">过滤内容：</td>
		<td colspan="3"><textarea id="excludeContent" name="questionnaireSpe.excludeContent" cols="50" rows="3">${questionnaireSpe.excludeContent}</textarea>
		<span class="required">(长度在0-120字之间)</span></td>
	</tr>
	<tr>
		<td align="center" colspan="4">
			<input type="button" class="btn" value="确定"  onclick="javascript:save();" />
			<input type="button" class="btn" value="返回" onclick="window.location.href='querySpecification.do?id=${advertPosition.id}'" />
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>