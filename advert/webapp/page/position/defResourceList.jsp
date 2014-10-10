<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<title>默认素材查询</title>
</head>
<script type="text/javascript">
	function save(loopCount,positionPackageId) {
		if (getCheckCount('ids') <= 0) {
	         alert("请选择需要设置的默认素材！");
	         return;
	    }
	    if(loopCount && loopCount != 0){
	    	if (getCheckCount('ids') != loopCount) {
		         alert("只能选择"+loopCount+"个默认素材！");
		         return;
		    }
		}
		var ids  = getCheckValue('ids');

	    $.ajax({   
		       url:'checkResource.do',       
		       type: 'POST',    
		       dataType: 'text',   
		       data: {
		    	   ids:ids,
		    	   positionPackageId:positionPackageId
				},                   
		       timeout: 1000000000,                              
		       error: function(){                      
		    		alert("系统错误，请联系管理员！");
		       },    
		       success: function(result){ 
		    	   if(result!='-1'){
			    	   if(result=='0'){
			    		   document.getElementById("queryForm").action = "saveDefResource.do";
			    	       document.getElementById("queryForm").submit();
			    	   }else{
			    		   alert(result);
			    	   }
		    	   }else{
			    		alert("系统错误，请联系管理员！");
		    	   }
			   }  
		   }); 
		
        
	}
    
</script>
<body class="mainBody">
<form action="queryDefResourceList.do" method="post" id="queryForm">
	<input type="hidden" id="id" name="id" value="${advertPosition.id}"/>
	<input type="hidden" id="positionPackageId" name="positionPackageId" value="${advertPosition.positionPackageId}"/>
	<div class="search">
		<div class="path">首页 >> 默认素材配置 >> 默认素材查询</div>
		<div class="searchContent">
		<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
		<table cellspacing="1" class="searchList">
    		<tr class="title">
    			
    			<td height="28" class="dot">
    				<c:if test="${advertPosition.isLoop==1||advertPosition.isAdd==1}">
    					<input type="checkbox" name="chkAll" onclick="selectAll(this, 'ids');" id="chkAll"/>
    				</c:if>
    			</td>
    			<td>素材名称</td>
    			<td>素材类型</td>
    			<td>素材描述</td>
    		</tr>
    		<c:forEach items="${defResourceList}" var="resource" varStatus="pl">
    		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>
    			onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
    			<td><input 
    				<c:choose>
						<c:when test="${advertPosition.isLoop==1||advertPosition.isAdd==1}">type="checkbox" </c:when>
						<c:when test="${advertPosition.isLoop!=1&&advertPosition.isAdd!=1}">type="radio" </c:when>
					</c:choose>
    				name="ids" value="${resource.id}_${resource.resourceName}" 
    				<c:forEach items="${defResourceADList}" var="def">
						<c:if test="${def.resourceId == resource.id}">checked</c:if>
					</c:forEach>
    			 /></td>
    			<td><c:out value="${resource.resourceName}" /></td>
    			<td>
    				<c:choose>
						<c:when test="${resource.resourceType==0}">图片</c:when>
						<c:when test="${resource.resourceType==1}">视频</c:when>
						<c:when test="${resource.resourceType==2}">文字</c:when>
						<c:when test="${resource.resourceType==3}">问卷</c:when>
					</c:choose>
    			</td>
    			<td><c:out value="${resource.resourceDesc}" /></td>
    		</tr>
    		</c:forEach>
			<tr>
			    <td colspan="4">
			    	<input type="button" value="确定" class="btn" onclick="javascript:save('${advertPosition.loopCount}','${advertPosition.positionPackageId}');"/>&nbsp;&nbsp;
        			<input type="button" value="取消" class="btn" onclick="javascript :window.close();"/>
			    </td>
			</tr>
		</table>		
		</div>
	</div>
</form>
</body>
</html>