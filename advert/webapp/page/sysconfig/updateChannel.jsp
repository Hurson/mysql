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

<title>修改频道</title>
</head>

<body class="mainBody">
<form action="updateChannel.do" method="post" id="updateForm">
<input type="hidden" id="id" name="channel.channelId" value="${channel.channelId}"  />
<div class="detail">
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">频道信息</td>
	</tr>
	<tr>
		<td width="12%" align="right">频道名称：</td>
		<td>${channel.channelName}</td>
	</tr>
	<tr>
		<td width="12%" align="right"><span class="required">*</span>频道回放：</td>
		<td>
			<select id="isPlayBack" name="channel.isPlayBack" >
				<option value="1"							
				<c:if test='${channel.isPlayBack ==1}'>
				selected
				</c:if>
				 >是</option>
				<option value="0"
				<c:if test='${channel.isPlayBack ==0}'>
				selected
				</c:if>
				 >否</option>
			</select>
		</td>
	</tr>
	<tr>
		<td width="12%" align="right">简称：</td>
		<td><textarea id="summaryShort" name="channel.summaryShort" cols="50" rows="3">${channel.summaryShort}</textarea>
		<span class="required">(长度在0-120字之间)</span></td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<input type="submit" class="btn" value="保存" />
			<input type="button" class="btn" value="返回" onclick="window.location.href='queryChannelList.do'" />
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>