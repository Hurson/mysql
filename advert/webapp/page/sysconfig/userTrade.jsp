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
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<link rel="stylesheet" type="text/css"	href="<%=path %>/css/new/main.css" />
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<title>修改用户行业</title>
</head>
<script type="text/javascript">
	function save() {
		if (!validateBeforeSubmit()) {
	        return;
	    }
		$("#updateForm").submit();
	}

	function validateBeforeSubmit() {
	    if (isEmpty($("#industryCode").val())) {
	        alert('用户行业编码不能为空！');
	        $("#industryCode").focus();
	        return false;
	    }
	    if(validateSpecialCharacterAfter($("#industryCode").val())){
			alert("用户行业编码不能包括特殊字符！");
			$("#industryCode").focus();
			return false;
		}
		if(!chineseVaildate($("#industryCode").val())){
			alert("用户行业编码不能包括中文！");
			$("#industryCode").focus();
			return false;
		}
	    
	    if (isEmpty($("#industryValue").val())) {
	        alert('用户行业名称不能为空！');
	        $("#industryValue").focus();
	        return false;
	    }
	    if(validateSpecialCharacterAfter($("#industryValue").val())){
			alert("用户行业名称不能包括特殊字符！");
			$("#industryValue").focus();
			return false;
		}
	    return true;
	}
</script>
<body class="mainBody">
<form action="updateUserTrade.do" method="post" id="updateForm">
<input type="hidden" id="id" name="industry.id" value="${industry.id}"  />
<div class="detail">
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
	 <c:if test="${message != null}">
            <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
    </c:if>
</div> 
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="2">用户行业信息</td>
	</tr>
	<tr>
		<td width="15%" align="right"><span class="required">*</span>用户行业编码：</td>
		<td width="85%" >
		<input id="industryCode" name="industry.userIndustryCategoryCode" maxlength="5" type="text"  style='width:20%' value="${industry.userIndustryCategoryCode}"/>
		</td>
	</tr>
	
	<tr class="sec">
		<td width="15%" align="right"><span class="required">*</span>用户行业名称：</td>
		<td width="85%" >
		<input id="industryValue" name="industry.userIndustryCategoryValue" maxlength="20" type="text"  style='width:20%' value="${industry.userIndustryCategoryValue}"/>
		</td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<input type="button" class="btn" value="确定"  onclick="javascript:save();" />
			<input type="button" class="btn" value="返回" onclick="window.location.href='queryUserTradeList.do'" />
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>