<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<title>广告位列表查询</title>
<script type="text/javascript">
    function viewPositionOccupy(id){
    	$.ajax({   
 	       url:'checkSession.do',       
 	       type: 'POST',    
 	       dataType: 'text',   
 	       data: {
 		   },                   
 	       timeout: 1000000000,                              
 	       error: function(){                      
 	    		alert("系统错误，请联系管理员！");
 	       },    
 	       success: function(result){
 	    	   if(result=='0'){
 	    		  var url = "viewPositionOccupy.do?contractAD.positionId="+id;
 	    	      window.showModalDialog(url, "", "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
 	    	   }else{
 	 	    	   //会话已经过期
 	    		  window.location.href=getContextPath()+'/tset/timeoutPage.jsp'
 	 	    	}	    	   
 		   }  
 		}); 
    }
</script>
</head>

<body class="mainBody">
<div class="search">
<div class="path">首页 >> 广告位管理 >> 广告位查询</div>
<div class="searchContent" >
<form action="queryPositionPackageList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>

<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div> 
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td >广告位编码</td>
	    <td >广告位名称</td>
	    <td >广告位类型</td>
	    <td >子广告位个数</td>
	    <td >投放策略</td>
		<td>操作</td>
    </tr>
    <c:forEach items="${page.dataList}" var="detail" varStatus="pl">
		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>
			onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
			<td><a href="getPositionPackage.do?id=${detail.Id }"><c:out value="${detail.positionCode}" /></a></td>
			<td><c:out value="${detail.positionName}" /></td>
			<td>
				<c:choose>
					<c:when test="${detail.positionType==1}">单向实时</c:when>
					<c:when test="${detail.positionType==2}">单向非实时</c:when>
					<c:when test="${detail.positionType==3}">字幕</c:when>
				</c:choose>
			</td>
			<td><c:out value="${detail.ployTypes}" /></td>
			<td><a href="#" onclick="viewPositionOccupy('${detail.Id }')">查看占用情况</a></td>
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