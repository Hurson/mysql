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
<link rel="stylesheet" href="<%=path%>/css/menu_right.css"
	type="text/css" />
<link rel="stylesheet" href="<%=path%>/css/platform.css" type="text/css" />
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<title>系统查询</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});
</script>
<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}
</style>
</head>

<body>
<table cellpadding="0" cellspacing="0" basset="0" width="100%">
	<tr>
		<td>
		<table cellpadding="0" cellspacing="0" basset="0" width="100%"
			class="tab_right">
			<tr>
				<td>工作台</td>
				<td>客户</td>
				<td>日程行动</td>
				<td>销售机会</td>
				<td>订单管理</td>
				<td>客服中心</td>
				<td>财务中心</td>
				<td>营销中心</td>
				<td>人力资源中心</td>
				<td>数据统计</td>
				<td>信息门户管理</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding: 4px;">
		<form action="listAsset.do" method="post"><input type="hidden"
			name="pageNo" value="1" />
		<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
			<tr>
				<td class="formTitle" colspan="99"><span>·影片信息</span></td>
			</tr>
			<tr>
				<td class="td_label">影片编号：</td>
				<td class="td_input"><input name="selId" class="e_input"
					onfocus="this.className='e_inputFocus'"
					onblur="this.className='e_input'" value="${selId }" /></td>
				<td class="td_label">影片名称：</td>
				<td class="td_input"><input name="selName" class="e_input"
					onfocus="this.className='e_inputFocus'"
					onblur="this.className='e_input'" value="${selName}" /></td>
			</tr>

			<tr>
				<td class="formBottom" colspan="99">
					<input name="Submit" type="submit" title="查看" class="b_search" value="" onfocus=blur() />
				</td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
	<tr>
		<td style="padding: 4px;">
		<form>
		<table cellpadding="0" cellspacing="1" width="100%"
			class="taba_right_list" id="bm">

			<tr>
				<td colspan="12"
					style="padding-left:8px; background:url(<%=path%>/images/menu_righttop.png) repeat-x; text-align:left; height:26px;"><span>·订单列表</span></td>
			</tr>

			<tr>
				<td height="26px" align="center">
					序号</td>
				<td>影片编号</td>
				<td>影片名称</td>
				<td>年份</td>
				<td>影片等级</td>
				<td>时长</td>
				<td>是否为资源包</td>
				<td>得分</td>
				<td>状态</td>
			</tr>
			<c:set var="index" value="0" />
			<c:forEach items="${assets}" var="asset">
				<tr>
					<c:set var="index" value="${index+1 }" />
					<td align="center" height="26"><c:out value="${index }" /></td>
					<td><c:out value="${asset.assetId}" /></td>
					<td><c:out value="${asset.assetName}" /></td>
					<td><c:out value="${asset.year}" /></td>
					<td><c:out value="${asset.rating}" /></td>
					<td><c:out value="${asset.runtime}" /></td>
					<td>
						<c:choose>
							<c:when test="${asset.isPackage=='0'}">否</c:when>
							<c:when test="${asset.isPackage=='1'}">是</c:when>
						</c:choose>
					</td>
					<td><c:out value="${asset.score}" /></td>
					<td>
						<c:choose>
							<c:when test="${asset.state=='0'}">不可用</c:when>
							<c:when test="${asset.state=='1'}">可用</c:when>
						</c:choose>
					</td>				
				</tr>
			</c:forEach>
			<c:if test="${index<page.pageSize}">
				<c:forEach begin="${index}" end="${page.pageSize-1}" step="1">
					<tr>
						<td align="center" height="26">&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</c:forEach>
			</c:if>
			<tr>
				<td height="34" colspan="12"
					style="background: url(images/bottom.jpg) repeat-x; text-align: right;">
				<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp; <c:choose>
					<c:when test="${page.pageNo==1&&page.totalPage!=1}">
						<a href="listAsset.do?pageNo=${page.pageNo+1 }">下一页</a>&nbsp;&nbsp;
		<a href="listAsset.do?pageNo=${page.totalPage}">末页</a>&nbsp;&nbsp;
	</c:when>
					<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
						<a href="listAsset.do?pageNo=1">首页</a>&nbsp;&nbsp;
		<a href="listAsset.do?pageNo=${page.pageNo-1 }">上一页</a>&nbsp;&nbsp;
	</c:when>
					<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
						<a href="listAsset.do?pageNo=1">首页</a>&nbsp;&nbsp;
		<a href="listAsset.do?pageNo=${page.pageNo-1 }">上一页</a>&nbsp;&nbsp;
		<a href="listAsset.do?pageNo=${page.pageNo+1 }">下一页</a>&nbsp;&nbsp;
		<a href="listAsset.do?pageNo=${page.totalPage}">末页</a>&nbsp;&nbsp;
	</c:when>
				</c:choose></td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>
<div style="position: absolute; width: 100%; left: 0px; bottom: 0px;">
</div>
</body>
</html>
