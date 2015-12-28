<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link id="maincss"  rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" media="all"/>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
	
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>



<title>策略维护</title>
<script>

function query() {
        document.forms[0].submit();
    }

 function addData() {
      window.location.href="<%=path %>/dploy/getDPloy.action";
    }
 	function deleteData() {
		 if (getCheckCount("dataIds") <= 0) {
	                alert("请勾选需删除的记录！");
	                return;
	     }
		 var dataIds = getCheckValue("dataIds");	 
		 
		 var ret = window.confirm("您确定要删除吗？");
		 if (ret==1){
			
			 document.forms[0].action="<%=path %>/dploy/deleteDPloy.action?ids="+dataIds;
	         document.forms[0].submit();
	     }
	   
    }
    function modifyData(ployid, status) {
	    window.location.href="<%=path %>/dploy/getDPloy.action?ploy.id="+ployid+"&ploy.status=" + status;
    }
	
</script>
<style>
	.easyDialog_wrapper{ width:800px;height:580px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}
</style>

</head>
<body class="mainBody">
 <form action="<%=path %>/dploy/queryDPloyList.action" method="post" id="queryForm">
 <s:set name="page" value="%{page}" />
		 <input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
		 <input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>

<div class="search">
<div class="path">首页 >> 投放策略管理 >> 策略维护</div>
<div class="searchContent" >
<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>策略名称：</span><input onkeypress="return validateSpecialCharacter();" type="text" name="ploy.ployName" id="ploy.ployName" value="${ploy.ployName}"/>
					
	  <span>广告位：</span><input onkeypress="return validateSpecialCharacter();" type="text" name="ploy.dposition.positionName" id="ploy.positionName" value="${ploy.dposition.positionName}"/>
      <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
<c:if test="${message != null}">
			 <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
</c:if>
</div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="selectAllBox" onclick="selectAll(this, 'dataIds');"/></td>
        <td width="30%" align="center">策略名称</td>
        <td width="30%" align="center">广告商</td>
        <td width="30%" align="center">广告位</td>
        <td width="5%" align="center">状态</td>
    </tr>
   				 <c:if test="${page.dataList != null && fn:length(page.dataList) > 0}">
                    <c:forEach items="${page.dataList}" var="ployInfo" varStatus="pl">
                        <tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
                            <td>
                                <input type="checkbox" value="<c:out value='${ployInfo.id}'/>" name="dataIds" <c:if test="${ployInfo.status==4 }">disabled</c:if> />
                             </td>
                            <td align="center">
                                <a href="javascript:modifyData('${ployInfo.id}','${ployInfo.status}');">${ployInfo.ployName}</a>
                            </td>
                            <td width="20%" align="center">
                            	<c:out value="${ployInfo.customer.advertisersName }"></c:out>
							</td>
					        <td width="15%" align="center">
					         	<c:out value="${ployInfo.dposition.positionName }"></c:out>
							</td>
					    
					     	 <td width="10%" align="center">
					        <c:if test="${ployInfo.status==2 }">
	                            	  通过
	                            	</c:if>
	                            	<c:if test="${ployInfo.status==3 }">
	                            	  驳回
	                            	</c:if>
	                               	<c:if test="${ployInfo.status==1 }">
	                            	 待审
	                            	</c:if> 
	                            	<c:if test="${ployInfo.status==4 }">
	                            	 已关联
	                            	</c:if> 	
							</td>
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="5">
		
        <input type="button" value="添加" class="btn" onclick="javascript:addData();"/>&nbsp;&nbsp;
        <input type="button" value="删除" class="btn" onclick="javascript:deleteData();"/>
		
								&nbsp;&nbsp;
		<jsp:include page='../../common/page.jsp' flush="true" />
	
    </td>
  </tr>
</table>
</div>
</div>
</form>
</body>

</html>