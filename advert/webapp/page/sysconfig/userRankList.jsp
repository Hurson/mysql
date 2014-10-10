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
		if(validateSpecialCharacterAfter($("#userRankName").val())){
			alert("用户级别名称不能包括特殊字符！");
			$("#userRankName").focus();
			return ;
		}
		if(validateSpecialCharacterAfter($("#userRankCode").val())){
			alert("用户级别编码不能包括特殊字符！");
			$("#userRankCode").focus();
			return ;
		}
		$("#queryForm").submit();
	}

	function delUserRank() {
		if (getCheckCount('ids') <= 0) {
	         alert("请选择需要删除的用户级别记录！");
	         return;
	    }

	    if (confirm("您确定需要删除所选的用户级别记录吗？")) {
	         document.getElementById("queryForm").action = "delUserRank.do";
	         document.getElementById("queryForm").submit();
	    }
	}
</script>
</head>

<body class="mainBody">
<div class="search">
<div class="path">首页 >> 系统管理 >> 用户级别维护</div>
<div class="searchContent" >
<form action="queryUserRankList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<table cellspacing="1" class="searchList">
	  <tr class="title">
	    <td>查询条件</td>
	  </tr>
	  <tr>
	    <td class="searchCriteria">
	      <span>用户级别名称：</span><input type="text" id="userRankName" name="userRank.userRankName" value="${userRank.userRankName}" />
	      <span>用户级别编码：</span><input type="text" id="userRankCode" name="userRank.userRankCode" value="${userRank.userRankCode}" />
	      <input type="button" value="查询" onclick="javascript:query();" class="btn"/>
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
    	<td height="28" class="dot"><input type="checkbox" name="chkAll" onclick="selectAll(this, 'ids');" id="chkAll"/></td>
        <td>用户级别名称</td>
        <td>用户级别编码</td>
        <td>描述</td>
    </tr>
    <c:forEach items="${page.dataList}" var="userRank" varStatus="pl">
		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
			<td><input type="checkbox" name="ids" value="${userRank.id}_${userRank.userRankName}"  /></td>
			<td><a href="getUserRankById.do?userRank.id=${userRank.id}"><c:out value="${userRank.userRankName}" /></a></td>
			<td><c:out value="${userRank.userRankCode}" /></td>
			<td><c:out value="${userRank.description}" /></td>
		</tr>
	</c:forEach>
  <tr>
    <td colspan="4">
    	<input type="button" value="删除" class="btn" onclick="javascript:delUserRank();"/>&nbsp;&nbsp;
        <input type="button" value="添加" class="btn" onclick="window.location.href='addUserRank.do'"/>
        <jsp:include page="../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</form>
</div>
</div>
</body>
</html>