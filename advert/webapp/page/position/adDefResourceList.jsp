<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<title></title>
<script type="text/javascript">
	function queryADList(positionPackageId){
		window.location.href="queryADList.do?id="+positionPackageId;
    }
</script>
</head>
<body class="mainBody">

<div class="search">
<div class="path">首页 >> 广告位管理 >> 默认素材配置</div>
<div class="searchContent" >
<form action="queryResourceList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>

<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div> 
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td>广告位名称</td>
	    <td>广告位类型</td>
	    <td>素材名称</td>
		<td>操作</td>
    </tr>
    <c:forEach items="${page.dataList}" var="package" varStatus="pl">
		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>
			onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
			<td><c:out value="${package.positionPackageName}" /></td>
			<td>
				<c:choose>
					<c:when test="${package.positionPackageType==0}">双向实时广告</c:when>
					<c:when test="${package.positionPackageType==1}">双向实时请求广告</c:when>
					<c:when test="${package.positionPackageType==2}">单向实时广告</c:when>
					<c:when test="${package.positionPackageType==3}">单向非实时广告</c:when>
					<c:when test="${package.positionPackageType==4}">单向数据广播</c:when>
				</c:choose>
			</td>
			<td><c:out value="${package.resourceName}" /></td>
			<td width="7%"><a href="#" onclick="queryADList('${package.positionPackageId}')">设定素材</a></td>
		</tr>
	</c:forEach>
  <tr>
    <td colspan="6">
        <jsp:include page="../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</form>
</div>
</div>
</body>
</html>