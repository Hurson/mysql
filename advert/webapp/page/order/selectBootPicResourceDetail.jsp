<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
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
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<title>详情</title>
<style>
	.e_input_add {
		background:url(<%=path%>/images/add.png) right no-repeat #ffffff;
	}
</style>
</head>
<script type="text/javascript">

	function query(){
		$("#queryForm").submit();
	}
	function delResource(){
		$("#queryForm").attr("action", "delOrderMateRelTmp.do");
		$("#queryForm").submit();
	}
	
	function cancle() {
		window.returnValue="";
        window.close();
    }

	function showResource(){
		if (getCheckCount('ids') <= 0) {
	         alert("请勾选需要添加素材的记录！");
	         return;
	    }
		var idss = document.getElementsByName("ids");
		var url = "queryAreaResourceList.do?resource.advertPositionId="+$("#positionId").val()+"&ployId="+$("#ployId").val();
		var resourceArray = window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
		if (resourceArray && resourceArray != null) {
			var resourceId ;
			var materialName = "";
        	if(resourceArray.length>0){
				for(var i=0;i<resourceArray.length;i++){
					resourceId = resourceArray[i].resourceId;
					materialName = resourceArray[i].resourceName;
					break;
				}
        	}
        	for (var i = 0; i < idss.length; i++) {
	        	if (idss[i].checked) {
	        		document.getElementById(idss[i].value).innerHTML = materialName;
	            }
        	}
        	var ids  = getCheckValue('ids');
        	$.ajax({   
	 		       url:'saveOrderMateRelTmp.do',       
	 		       type: 'POST',    
	 		       dataType: 'text',   
	 		       data: {
	 		    	  ids:ids,
	 		    	  resourceId:resourceId
	 				},                   
	 		       timeout: 1000000000,                              
	 		       error: function(){                      
	 		    		alert("系统错误，请联系管理员！");
	 		       },    
	 		       success: function(result){ 
	 		    	   if(result == '-1'){
	 		    		  alert("系统错误，请联系管理员！");
	 		    	   }
	 			   }  
	 		   });
        	$("[name='ids']").removeAttr("checked");//取消全选  
    	}
	}
    
</script>
<body class="mainBody">
<form action="getAreaResourceDetail.do" method="post" id="queryForm">
	<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
	<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<input type="hidden" id="positionId" name="order.positionId" value="${order.positionId}"/>
	<input type="hidden" id="ployId" name="order.ployId" value="${order.ployId}"/>
	<input type="hidden" id="orderCode" name="omRelTmp.orderCode" value="${omRelTmp.orderCode}"/>
	<input type="hidden" id="mateId" name="omRelTmp.mateId" value="${omRelTmp.mateId}"/>
	<div class="searchContent">
		<table cellspacing="1" class="searchList">
            <tr class="title">
                <td>查询条件</td>
            </tr>
             <tr>
                <td class="searchCriteria">
                	<span>区域：</span>
                	<select id="omRelTmp.areaCode" name="omRelTmp.areaCode" style="width:80px">
                		<option value="">所有</option>
           				<c:forEach items="${pageReleaseLocation.dataList}" var="areaVar" >
       						<option value="${areaVar.areaCode}"	<c:if test="${omRelTmp.areaCode==areaVar.areaCode}">selected</c:if>>
                       			${areaVar.areaName}  
                       		</option>   
                       	</c:forEach>           					
                	</select>
                    <input type="button" value="查询" onclick="javascript:query();" class="btn"/>
   				</td>
            </tr>
        </table>
        <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
            	<td>开始结束时间</td>
                <td>区域</td>
                <td>播放时段</td>
				<td>素材</td>
            </tr>
            <c:forEach items="${page.dataList}" var="omRelTmp" varStatus="pl">
				<tr <c:if test="${pl.index%2==1}">class="sec"</c:if> 
					onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
					<td><c:out value="${omRelTmp.startTime}" /> - <c:out value="${omRelTmp.endTime}" /></td>
					<td><c:out value="${omRelTmp.areaName}" /></td>
					<!--  <td><c:out value="${omRelTmp.playLocation}" /></td>-->
					<td>
					<c:choose>
						<c:when test="${omRelTmp.playLocation!='null'}"><c:out value="${omRelTmp.playLocation}" /></c:when>
						<c:when test="${omRelTmp.playLocation=='null'}">全时段</c:when>
					</c:choose>
					</td>
					<td><div id="${omRelTmp.id}">${omRelTmp.mateName}</div></td>
				</tr>
			</c:forEach>
            
        </table>
    </div>
</form>
</body>
</html>