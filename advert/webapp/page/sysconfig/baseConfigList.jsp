<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<div class="path">首页 >> 系统管理 >> 系统参数配置</div>
<div class="searchContent" >
<form action="allConfigList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<table cellspacing="1" class="searchList">
	  <tr class="title">
	    <td>查询条件</td>
	  </tr>
	  <tr>
	    <td class="searchCriteria">
	      <span>配置项名称：</span><input type="text" name="queryKey" value="${queryKey}" />
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
    	
        <td>配置项名称</td>
        <td>配置项编码</td>
        <td>配置项值</td>
        <td>配置项描述</td>
    </tr>
    
    <c:forEach items="${page.dataList}" var="map" varStatus="pl">
		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
			
			<td><a href="getConfig.do?id=${map.id}"><c:out value="${map.remindName}" /></a></td>
			<td><c:out value="${map.remindKey}" /></td>
			<td><c:out value="${map.remindValue}" /></td>
			<td><c:out value="${map.remindDesc}" /></td>
		</tr>
	</c:forEach>
		
        
  <tr>
  	<td colspan = "5">
  		<jsp:include page="../common/page.jsp" flush="true" />
  	
        
    </td>
  </tr>
</table>
</form>
</div>
</div>
</body>
</html>