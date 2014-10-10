<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tags/fmt.tld" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<title>用户列表</title>
<script>
	function query() {
		if(validateSpecialCharacterAfter($("#userName").val())){
			alert("用户名称不能包括特殊字符！");
			$("#userName").focus();
			return ;
		}
		if(validateSpecialCharacterAfter($("#loginName").val())){
			alert("登陆名称不能包括特殊字符！");
			$("#loginName").focus();
			return ;
		}
		if(validateSpecialCharacterAfter($("#email").val())){
			alert("邮箱不能包括特殊字符！");
			$("#email").focus();
			return ;
		}
		$("#queryForm").submit();
	}
	
	function delUser() {
		if (getCheckCount('ids') <= 0) {
	         alert("请选择需要删除的用户记录！");
	         return;
	    }
	
	    if (confirm("您确定需要删除所选的用户记录吗？")) {
	         document.getElementById("queryForm").action = "delUser.do";
	         document.getElementById("queryForm").submit();
	    }
	}
</script>
</head>
<body class="mainBody">
<div class="search">
<div class="path">首页 >> 系统管理 >> 用户维护</div>
<div class="searchContent" >
<form action="queryUserList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<table cellspacing="1" class="searchList">
	  <tr class="title">
	    <td>查询条件</td>
	  </tr>
	  <tr>
	    <td class="searchCriteria">
	        <span>用户名称：</span><input type="text" id="userName" name="user.userName" value="${user.userName}" />
	        <span>登录名称：</span><input type="text" id="loginName" name="user.loginName" value="${user.loginName}" />
	        <span>邮箱：</span><input type="text" id="email" name="user.email" value="${user.email}" />
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
        <td>用户名称</td>
		<td>登陆名称</td>
		<td>用户状态</td>
		<td>电子邮箱</td>
		<td>角色名称</td>
		<td>创建时间</td>
		<td>修改时间</td>
    </tr>
	<s:iterator value="page.dataList" status="status" var="user">
		<tr <s:if test="#status.index%2==1">class="sec"</s:if>>
			<td><input type="checkbox" name="ids" value="<s:property value='#user.userId' />_<s:property value='#user.userName' />"  /></td>
			<td><a href="getUserForUpdate.do?user.userId=<s:property value='#user.userId' />"><s:property value="#user.userName" /></a></td>
			<td><s:property value="#user.loginName" /></td>
			<td>
				<s:if test="#user.state==1">可用</s:if>
				<s:else>禁用</s:else>
			</td>
			<td><s:property value="#user.email" /></td>
			<td><s:property value="#user.roleName" /></td>
			<td><s:date name="#user.createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td><s:date name="#user.modifyTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
	</s:iterator>
  <tr>
    <td colspan="8">
       	<input type="button" value="删除" class="btn" onclick="javascript:delUser();"/>&nbsp;&nbsp;
        <input type="button" value="添加" class="btn" onclick="window.location.href='initUserAdd.do'"/>
        <jsp:include page="../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</form>
</div>
</div>
</body>
</html>