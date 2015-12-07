<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<title>素材查询</title>
</head>
<script type="text/javascript">
	function query(){
		if(validateSpecialCharacterAfter($("#resourceName").val())){
			alert("素材名称不能包括特殊字符！");
			$("#resourceName").focus();
			return ;
		}
		$("#queryForm").submit();
	}
	
	function save() {
		if (getCheckCount('ids') <= 0) {
	         alert("请选择需要绑定的素材！");
	         return;
	    }
		var ids  = getCheckValue('ids');

		var returnArray = new Array();
	   var idsArray = ids.split(",");
	   
	   for(var i = 0; i<idsArray.length; i++){  
		   var resourceId = idsArray[i].split("_")[0];
		   
		   var iArray=idsArray[i];
		   var m=iArray.indexOf("_")
		   var resourceName =iArray.substr(m+1);

		   returnArray[i] = {"resourceId":resourceId,"resourceName":resourceName};
	   }
	   
	   window.returnValue=returnArray;
	   window.close();
	}

	
</script>
<body class="mainBody">
<form action="queryDResourceList.action" method="post" id="queryForm">
	<input type="hidden" name="resource.positionCode" value="${resource.positionCode}"/>
	<div class="searchContent">
		<table cellspacing="1" class="searchList">
            <tr class="title">
                <td>查询条件</td>
            </tr>
            <tr>
                <td class="searchCriteria">
                	<span>素材名称：</span><input type="text" id="resourceName" name="resource.resourceName" value="${resource.resourceName}" />
                	<input type="button" value="查询" onclick="javascript:query();" class="btn"/>
   				</td>
            </tr>
        </table>
		<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
		<table cellspacing="1" class="searchList">
    		<tr class="title">
    			
    			<td height="28" class="dot"></td>
    			<td width="30%">素材名称</td>
    			<td width="15%">素材类型</td>
    			<td width="50%">描述</td>
    		</tr>
    		<c:forEach items="${page.dataList}" var="resource" varStatus="pl">
    		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>
    			onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
    			<td><input id="${resource.id}_resource" type="radio"
    				name="ids" value="${resource.id}_${resource.resourceName}" /></td>
    			<td><c:out value="${resource.resourceName}" /></td>
    			
    			<td>
    				<c:choose>
						<c:when test="${resource.resourceType==0}">图片</c:when>
						<c:when test="${resource.resourceType==1}">视频</c:when>
						<c:when test="${resource.resourceType==2}">文字</c:when>
						<c:when test="${resource.resourceType==3}">问卷</c:when>
						<c:when test="${resource.resourceType==4}">ZIP</c:when>
					</c:choose>
    			</td>
    			<td><c:out value="${resource.description}" /></td>
    		</tr>
    		</c:forEach>
			<tr>
				<td colspan="4" >
			    	<input type="button" value="确定" class="btn" onclick="javascript:save();"/>&nbsp;&nbsp;
        			<input type="button" value="关闭" class="btn" onclick="javascript :window.close();"/>
        			<jsp:include page="../../common/page.jsp" flush="true" />
			    </td>
			</tr>
		</table>		
	</div>
</form>
</body>
</html>