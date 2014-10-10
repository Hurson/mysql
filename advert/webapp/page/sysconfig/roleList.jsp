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
		if(validateSpecialCharacterAfter($("#name").val())){
			alert("角色名称不能包括特殊字符！");
			$("#name").focus();
			return ;
		}
		$("#queryForm").submit();
	}
	
	function delRole() {
		
		if (getCheckCount('ids') <= 0) {
	         alert("请选择需要删除的角色记录！");
	         return;
	    }

		var ids = getCheckValue('ids');
		$.ajax({   
		       url:'checkRoleUserBinging.do',       
		       type: 'POST',    
		       dataType: 'text',   
		       data: {
		    	   ids:ids
				},                   
		       timeout: 1000000000,                              
		       error: function(){                      
		    		alert("系统错误，请联系管理员！");
		       },    
		       success: function(result){ 
		    	   if(result == 'false'){
		    		   if (confirm("您确定需要删除所选的角色记录吗？")) {
		    		         document.getElementById("queryForm").action = "delRole.do";
		    		         document.getElementById("queryForm").submit();
		    		    }
		    	   }else{
			    		alert("您选择的角色中存在已经绑定用户，无法删除！");
		    	   }
			   }  
		   }); 
	}
</script>
</head>
<body class="mainBody">
<div class="search">
<div class="path">首页 >> 系统管理 >> 角色维护</div>
<div class="searchContent" >
<form action="queryRoleList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<table cellspacing="1" class="searchList">
	  <tr class="title">
	    <td>查询条件</td>
	  </tr>
	  <tr>
	    <td class="searchCriteria">
	        <span>角色名称：</span><input type="text" id="name" name="role.name" value="${role.name}" />
	        <span>角色类型：</span>
	        <select name="role.type">
				<option value="">全部</option>
				<option value="2" <c:if test="${role.type=='2'}">selected</c:if>>运营商</option>
				<option value="1" <c:if test="${role.type=='1'}">selected</c:if>>广告商</option>
			</select>
	        <input type=button value="查询" onclick="javascript:query();" class="btn"/>
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
        <td>角色名称</td>
		<td>类型</td>
		<td>创建时间</td>
		<td>修改时间</td>
		<td>描述</td>
    </tr>
	<s:iterator value="page.dataList" status="status" var="role">
		<tr <s:if test="#status.index%2==1">class="sec"</s:if>>
			<td><input type="checkbox" name="ids" value="<s:property value='#role.roleId' />_<s:property value="#role.name" />"  /></td>
			<td><a href="getRoleForUpdate.do?role.roleId=<s:property value='#role.roleId' />"><s:property value="#role.name" /></a></td>
			<td>
				<s:if test="#role.type==1">广告商</s:if>
				<s:else>运营商</s:else>
			</td>
			<td><s:date name="#role.createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td><s:date name="#role.modifyTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td><s:property value="#role.description" /></td>
		</tr>
	</s:iterator>
  <tr>
    <td colspan="8">
       	<input type="button" value="删除" class="btn" onclick="javascript:delRole();"/>&nbsp;&nbsp;
        <input type="button" value="添加" class="btn" onclick="window.location.href='initRoleAdd.do'"/>
        <jsp:include page="../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</form>
</div>
</div>
</body>
</html>