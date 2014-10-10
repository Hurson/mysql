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
<script type="text/javascript">
	function save() {
		$("#editForm").submit();
	}
</script>

<title>新增广告素材</title>
</head>
<body class="mainBody">
<form action="updateUI.do" id="editForm" theme="simple"  validate="false" enctype ="multipart/form-data" method ="POST">
	<div class="detail">
	<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
		 <c:if test="${message != null}">
	            <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
	    </c:if>
	</div> 
	<table cellspacing="1" class="searchList" align="left">
		<tr class="title">
			<td colspan="2">更新UI描述符信息</td>
		</tr>
		<tr>
			<td align="right">选择地市：</td>
			<td>
	           <select id="areaCode" name="areaCode" >
					<option  value="">请选择...</option>
					<c:forEach items="${pageReleaseLocation.dataList}" var="area">
						<option value="${area.areaCode}" >${area.areaName}</option>
					</c:forEach>
		       </select>    
		    </td>
		</tr>
		<tr>
			<td width="20%" align="right">dataDefine-c.dat文件：</td>
			<td><input type="file" name="dataDefine"/></td>
		</tr>
		<tr class="sec">
			<td width="20%" align="right">htmlData-c.dat文件：</td>
			<td><input type="file" name="htmlData"/></td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<input type="button" class="btn" value="保存"  onclick="javascript:save();" />
			</td>
		</tr>
	</table>
	</div>
</form>
</body>
</html>