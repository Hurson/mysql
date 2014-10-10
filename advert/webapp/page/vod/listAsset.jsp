<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<title>系统查询</title>
<style type="text/css">
	a{text-decoration:underline;}
</style>
</head>

<body class="mainBody">
<div class="search">
<div class="path">首页 >> 系统查询 >> 影片信息查询</div>
<div class="searchContent">
<form action="listAsset.do" method="post"><input type="hidden"
	name="pageNo" value="1" />
<table cellspacing="1" class="searchList">
	<tr class="title">
		<td>查询条件</td>
	</tr>
	<tr>
		<td class="searchCriteria"><span>影片名称：</span> <input type="text"
			name="selAName" value="${selAName}" id="textfield" />
		<span>影片编码：</span> <input type="text"
			name="selAId" value="${selAId}" id="textfield" />
		<input type="submit" value="查询" class="btn" /></td>
	</tr>

</table>
</form>
<table cellspacing="1" class="searchList">

	<tr class="title">
		<td height="28" class="dot">序号</td>
		<td>影片编号</td>
		<td>影片名称</td>
		<td>年份</td>
		<td>影片等级</td>
		<td>时长</td>
		<td>是否为资源包</td>
		<td>得分</td>
		<td>状态</td>
	</tr>
	<c:choose>
		<c:when test="${!empty assets}">
			<c:set var="index" value="0" />
			<c:forEach items="${assets}" var="asset">
				<tr <c:if test="${index%2==1 }">class='sec'</c:if>>
					<c:set var="index" value="${index+1 }" />
					<td align="center"><c:out value="${index }" /></td>
					<td><c:out value="${asset.assetId}" /></td>
					<td><c:out value="${asset.assetName}" /></td>
					<td><c:out value="${asset.year}" /></td>
					<td><c:out value="${asset.rating}" /></td>
					<td><c:out value="${asset.runtime}" /></td>
					<td><c:choose>
						<c:when test="${asset.isPackage=='0'}">否</c:when>
						<c:when test="${asset.isPackage=='1'}">是</c:when>
					</c:choose></td>
					<td><c:out value="${asset.score}" /></td>
					<td><c:choose>
						<c:when test="${asset.state=='0'}">不可用</c:when>
						<c:when test="${asset.state=='1'}">可用</c:when>
					</c:choose></td>
				</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="9">
					暂无记录
				</td>
			</tr>
		</c:otherwise>
	</c:choose>
	<tr>
		<td colspan="9" style="text-align: right;">
		<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
		<a href="#">当前第${page.pageNo}/${page.totalPage }页</a>&nbsp;&nbsp;
	<c:choose>
			<c:when test="${page.pageNo==1&&page.totalPage>1}">
				【<a href="listAsset.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
		【<a href="listAsset.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
	</c:when>
			<c:when test="${page.pageNo==page.totalPage&&page.totalPage>1 }">
				【<a href="listAsset.do?pageNo=1">首页</a>】&nbsp;&nbsp;
		【<a href="listAsset.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
	</c:when>
			<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
				【<a href="listAsset.do?pageNo=1">首页</a>】&nbsp;&nbsp;
		【<a href="listAsset.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
		【<a href="listAsset.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
		【<a href="listAsset.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
	</c:when>
		</c:choose></td>
	</tr>
</table>
</div>
</div>
</body>
</html>
