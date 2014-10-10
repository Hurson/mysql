<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"	href="<%=path %>/css/new/main.css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>

<title>编辑用户级别</title>
</head>
<script type="text/javascript">
	function save() {
		if (!validateBeforeSubmit()) {
	        return;
	    }
		$("#editForm").submit();
	}

	function validateBeforeSubmit() {
	    if (isEmpty($("#userRankName").val())) {
	        alert('用户级别名称不能为空！');
	        $("#userRankName").focus();
	        return false;
	    } 
	    if(validateSpecialCharacterAfter($("#userRankName").val())){
			alert("用户级别名称不能包括特殊字符！");
			$("#userRankName").focus();
			return false;
		}
	    
	    if (isEmpty($("#userRankCode").val())) {
	        alert('用户级别编码不能为空！');
	        $("#userRankCode").focus();
	        return false;
	    }
	    if(validateSpecialCharacterAfter($("#userRankCode").val())){
			alert("用户级别编码不能包括特殊字符！");
			$("#userRankCode").focus();
			return false;
		}
		if(!chineseVaildate($("#userRankCode").val())){
			alert("用户级别编码不能包括中文！");
			$("#userRankCode").focus();
			return false;
		}
	    if(!isEmpty($("#description").val()) && $("#description").val().length>120){
    		alert("用户级别描述长度必须在0-120字之间！");
    		return false;
    	}
    	if(validateSpecialCharacterAfter($("#description").val())){
			alert("用户级别描述不能包括特殊字符！");
			$("#description").focus();
			return false;
		}
	    return true;
	}
</script>
<body class="mainBody">
<form action="saveUserRank.do" method="post" id="editForm">
<input type="hidden" id="id" name="userRank.id" value="${userRank.id}"  />
<div class="detail">
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
	 <c:if test="${message != null}">
            <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
    </c:if>
</div> 
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="2">用户级别信息</td>
	</tr>
	<tr>
		<td width="12%" align="right"><span class="required">*</span>用户级别名称：</td>
		<td><input id="userRankName" name="userRank.userRankName" value="${userRank.userRankName}" type="text" maxlength="20" /></td>
	</tr>
	<tr class="sec">
		<td width="12%" align="right"><span class="required">*</span>用户级别编码：</td>
		<td><input id="userRankCode" name="userRank.userRankCode" value="${userRank.userRankCode}" type="text" maxlength="20" /></td>
	</tr>
	<tr>
		<td width="12%" align="right">描述：</td>
		<td><textarea id="description" name="userRank.description" cols="50" rows="3">${userRank.description}</textarea>
		<span class="required">(长度在0-120字之间)</span></td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<input type="button" class="btn" value="确定"  onclick="javascript:save();" />
			<input type="button" class="btn" value="返回" onclick="window.location.href='queryUserRankList.do'" />
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>