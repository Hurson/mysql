<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
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
		var resourceId = ids.split("_")[0];
	   var resourceName = ids.split("_")[1];
	   var inStreamArray = new Array();
	   var returnArray = new Array();
	   returnArray[0] = {"resourceId":resourceId,"resourceName":resourceName,"inStreamArray":inStreamArray,"mLoopsValue":"-1"};
	   
	   window.returnValue=returnArray;
	   window.close();
	}


	/**
	* 取消绑定的素材
	*/
	function cancelBinding(){
		var returnArray = new Array();
		window.returnValue=returnArray;
		window.close();
	}

	function init(selectResource){
		if(selectResource){
			selectResource = selectResource.substring(0,selectResource.length-1);
			
			var relArray = selectResource.split("@");
			if(relArray && relArray.length>0){
				for(var i=0;i<relArray.length;i++){
					var resArray = relArray[i].split(",");
					if(resArray && resArray.length>2){
						var resourceId = resArray[0];
						$("#"+resourceId+"_resource").attr("checked","checked") ;
					}
				}
			}
		}
	}
	
</script>
<body class="mainBody" onload="init('${selectResource}');">
<form action="showResourceList.do" method="post" id="queryForm">
	<input type="hidden" name="resource.contractId" value="${resource.contractId}"/>
	<input type="hidden" name="resource.advertPositionId" value="${resource.advertPositionId}"/>
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
    			<td>文件大小（字节）</td>
    			<td width="50%">描述</td>
    		</tr>
    		<c:forEach items="${resourceList}" var="resource" varStatus="pl">
    		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>
    			onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
    			<td><input id="${resource.id}_resource" type="radio" name="ids" value="${resource.id}_${resource.resourceName}" /></td>
    			<td><c:out value="${resource.resourceName}" /></td>
    			<td>
    				<c:choose>
						<c:when test="${resource.resourceType==0}">图片</c:when>
						<c:when test="${resource.resourceType==1}">视频</c:when>
						<c:when test="${resource.resourceType==2}">文字</c:when>
						<c:when test="${resource.resourceType==3}">问卷</c:when>
					</c:choose>
    			</td>
    			<td><c:out value="${resource.fileSize}" /></td>
    			<td><c:out value="${resource.resourceDesc}" /></td>
    		</tr>
    		</c:forEach>
			<tr>
				<td colspan="5">
			    	<input type="button" value="确定" class="btn" onclick="javascript:save();"/>&nbsp;&nbsp;
        			<input type="button" value="关闭" class="btn" onclick="javascript :window.close();"/>
        			<input type="button" value="取消绑定" class="btn" onclick="javascript :cancelBinding();"/>
			    </td>
			</tr>
		</table>		
	</div>
</form>
</body>
</html>