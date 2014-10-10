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

<title>编辑文字广告位规格</title>
</head>
<script type="text/javascript">
	function save() {
		$("#editForm").submit();
	}

</script>
<body class="mainBody">
<form action="saveTextSpecification.do" method="post" id="editForm">
<input type="hidden" name="id" value="${advertPosition.id}"  />
<input type="hidden" id="id" name="textSpe.id" value="${textSpe.id}"  />
<div class="detail">
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">文字规格信息</td>
	</tr>
	<tr>
		<td width="12%" align="right">字段长度：</td>
		<td><input id="textLength" name="textSpe.textLength" value="${textSpe.textLength}" type="text" /></td>
		<td width="12%" align="right">是否连接：</td>
		<td>
			<select id="isLink" name="textSpe.isLink">
				<option value="0"  <c:if test="${textSpe.isLink==0}">selected='selected'  </c:if>>否</option>
				<option value="1"  <c:if test="${textSpe.isLink==1}">selected='selected'  </c:if>>是</option>
			</select>
		</td>
	</tr>
	<tr class="sec">
		<td width="12%" align="right">描述：</td>
		<td colspan="3"><textarea id="textDesc" name="textSpe.textDesc" cols="50" rows="3">${textSpe.textDesc}</textarea>
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