<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>

<script type="text/javascript">
	function query() {
		$("pageNo").value=1;
		$("queryForm").submit();
	}
</script>
</head>

<body class="mainBody">
<div class="search">
<div class="path">首页 >> 系统管理 >> 频道维护</div>
<div class="searchContent" >
<form action="queryChannelList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<table cellspacing="1" class="searchList">
	  <tr class="title">
	    <td>查询条件</td>
	  </tr>
	  <tr>
	    <td class="searchCriteria">
	      <span>频道名称：</span><input type="text" name="channel.channelName" value="${channel.channelName}" />
	      <span>频道编码：</span><input type="text" name="channel.channelCode" value="${channel.channelCode}" />
	      <input type="submit" value="查询" onclick="javascript:query();" class="btn"/>
	     </td>
	  </tr>
	</table>

<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
	 <c:if test="${message != null}">
            <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
    </c:if>
</div> 
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td>频道名称</td>
        <td>是否频道回放</td>
        <td>简称</td>
        <td>频道编码</td>
        <td>区域码</td>
        <td>区域名</td>
        <td>状态</td>
        <td>创建时间</td>
    </tr>
    <c:forEach items="${page.dataList}" var="channel" varStatus="pl">
		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
			<td><a href="getChannelById.do?channel.channelId=${channel.channelId}"><c:out value="${channel.channelName}" /></a></td>
			<td>
				<c:choose>
					<c:when test="${channel.isPlayBack==0}">否</c:when>
					<c:when test="${channel.isPlayBack==1}">是</c:when>
				</c:choose>
			</td>
			<td><c:out value="${channel.summaryShort}" /></td>
			<td><c:out value="${channel.channelCode}" /></td>
			<td><c:out value="${channel.locationCode}" /></td>
			<td><c:out value="${channel.locationName}" /></td>
			<td>
				<c:choose>
					<c:when test="${channel.state==0}">不可用</c:when>
					<c:when test="${channel.state==1}">可用</c:when>
				</c:choose>
			</td>
			<td><fmt:formatDate value="${channel.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	</c:forEach>
  <tr>
    <td colspan="8">
        <jsp:include page="../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</form>
</div>
</div>
</body>
</html>