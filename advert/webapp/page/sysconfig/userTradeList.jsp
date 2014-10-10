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
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>

<script type="text/javascript">
	function query() {
		if(validateSpecialCharacterAfter($("#userIndustryCategoryCode").val())){
			alert("行业编码不能包括特殊字符！");
			$("#userIndustryCategoryCode").focus();
			return ;
		}
		if(validateSpecialCharacterAfter($("#userIndustryCategoryValue").val())){
			alert("行业名称不能包括特殊字符！");
			$("#userIndustryCategoryValue").focus();
			return ;
		}
		$("#queryForm").submit();
	}
	
	function addData(){
		window.location.href="<%=path %>/page/sysconfig/addInitUserTrade.do";
	}
	
	function deleteData(){
		if (getCheckCount("ids") <= 0) {
                alert("请勾选需删除的记录！");
                return;
	     }
		 var ret = window.confirm("您确定要删除吗？");
		 if (ret==1)
	     {
			 document.getElementById("queryForm").action="deleteUserTrade.do";
	         document.getElementById("queryForm").submit();
	     }
	}
</script>
</head>

<body class="mainBody">
<div class="search">
<div class="path">首页 >> 系统管理 >> 行业类型数据配置</div>
<div class="searchContent" >
<form action="queryUserTradeList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<table cellspacing="1" class="searchList">
	  <tr class="title">
	    <td>查询条件</td>
	  </tr>
	  <tr>
	    <td class="searchCriteria">
	      <span>行业编码：</span>
	      <input type="text" id="userIndustryCategoryCode" name="industry.userIndustryCategoryCode" value="${industry.userIndustryCategoryCode}" />
	      <span>行业名称：</span>
	      <input type="text" id="userIndustryCategoryValue" name="industry.userIndustryCategoryValue" value="${industry.userIndustryCategoryValue}"/>
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
    	<td height="28" class="dot">
			<input type="checkbox" name="checkbox" value="checkbox" />
		</td>
        <td width="20%">行业编码</td>
        <td width="80%">行业名称</td>
    </tr>
    <c:forEach items="${page.dataList}" var="industry" varStatus="pl">
		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
			<td align="center" height="26"><input type="checkbox" name="ids" value="${industry.id}" /></td>
			<td><a href="getUserTradeById.do?id=${industry.id}"><c:out value="${industry.userIndustryCategoryCode}" /></a></td>
			<td><c:out value="${industry.userIndustryCategoryValue}" /></td>
		</tr>
	</c:forEach>
  <tr>
  	<td colspan = "3">
  		<input type="button" value="删除" class="btn" onclick="deleteData();"/>&nbsp;&nbsp;
        <input type="button" value="添加" class="btn" onclick="addData();"/>
  		<jsp:include page="../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</form>
</div>
</div>
</body>
</html>