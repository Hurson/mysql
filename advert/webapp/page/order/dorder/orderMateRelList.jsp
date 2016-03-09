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
<title>选择区域素材</title>
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
		$("#queryForm").attr("action", "delDOrderMateRelTmp.action");
		$("#queryForm").submit();
	}
	
	function cancle() {
		window.returnValue="";
        window.close();
    }
	function isAll(checkBoxObject,locationCode){
		var idss = document.getElementsByName("ids");
		var flag=false;
		if(locationCode=="152000000000"&&checkBoxObject.checked){
			flag=true;
		}
        if(flag){
	        for (var i = 0; i < idss.length; i++) {
		        	idss[i].checked=true;
	        	}
        }
        var flag1=false;
        for (var i = 0; i < idss.length; i++) {
	        	if (idss[i].checked) {
		        		if(parseInt(idss[i].value)==parseInt(locationCode)){ 
		        		   flag=true;
	        		    }
	            }
        	}
        	
	}
	function showResource(){
		if (getCheckCount('ids') <= 0) {
	         alert("请勾选需要添加素材的记录！");
	         return;
	    }
		var idss = document.getElementsByName("ids");
		var url = "queryDResourceList.do?resource.positionCode="+$("#positionCode").val();
		var resourceArray = window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
		if (resourceArray && resourceArray != null) {
			var resourceId ="" ;
			var materialName = "";
        	if(resourceArray.length>0){
        		if(resourceArray.length == 1){
        			resourceId = resourceArray[0].resourceId;
					materialName = resourceArray[0].resourceName;
        			for (var i = 0; i < idss.length; i++) {
        	        	if (idss[i].checked) {
        	        		document.getElementById(idss[i].value).innerHTML = materialName;
        	            }
                	}
        		}else{
        			var j=0;
        			for (var i = 0; i < idss.length; i++) {
        	        	if (idss[i].checked) {
        	        		if(j<resourceArray.length){
        	        			resourceId +=(j==0?"":",")+resourceArray[j].resourceId;
        	        			document.getElementById(idss[i].value).innerHTML = resourceArray[j].resourceName;
        	        		}
        	        		j++;
        	            }
                	}
        		}
				/*for(var i=0;i<resourceArray.length;i++){
					resourceId = resourceArray[i].resourceId;
					materialName = resourceArray[i].resourceName;
					break;
				}*/
        	}
        	
        	var ids  = getCheckValue('ids');
        	$.ajax({   
	 		       url:'saveDOrderMateRelTmp.action',       
	 		       type: 'POST',    
	 		       dataType: 'text',   
	 		       data: {
	 		    	  ids:ids,
	 		    	  resourceIds:resourceId
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
        	$("input[name='ids']").removeAttr("checked");//取消全选  
    	}
	}
	
</script>
<body class="mainBody">
<form action="queryDOrderMateRelTmp.action" method="post" id="queryForm">
	<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
	<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<input type="hidden" id="positionCode" name="order.dposition.positionCode" value="${order.dposition.positionCode}"/>
	<input type="hidden" id="orderCode" name="omrTmp.orderCode" value="${omrTmp.orderCode}"/>
	<!-- <input type="hidden" id="orderCode" name="omrTmp.resource.id" value="${omrTmp.resource.id}"/> -->
	<div class="searchContent">
		<table cellspacing="1" class="searchList">
            <tr class="title">
                <td>查询条件</td>
            </tr>
            <tr>
                <td class="searchCriteria">
                	<span>开始时间段：</span><input name="omrTmp.startTime" type="text" readonly="readonly" onclick="WdatePicker({dateFmt:'HH:mm:ss'})" value="${omrTmp.startTime}" style="width: 14%"/>
                    <span>结束时间段：</span><input name="omrTmp.endTime" type="text" readonly="readonly" onclick="WdatePicker({dateFmt:'HH:mm:ss'})" value="${omrTmp.endTime}" style="width: 14%"/>
                    <c:if test="${empty omrTmp.resource.id }">
	                    <input type="checkbox" name="omrTmp.contain" value="1" 
	                    <c:if test="${omrTmp.contain=='1'}">
	                    	checked="checked"
	                    </c:if> 
	                    /><span>包含已配置</span>
                    </c:if>
   				</td>
            </tr>
             <tr>
                <td class="searchCriteria">
                	<span>区域：</span>
                	<select id="omrTmp.areaCode" name="omrTmp.releaseArea.areaCode" style="width:80px">
                		<option value="">所有</option>
           				<c:forEach items="${releaseAreaList}" var="areaVar" >
       						<option value="${areaVar.areaCode}"	<c:if test="${omrTmp.releaseArea.areaCode==areaVar.areaCode}">selected</c:if>>
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
              <td height="28" class="dot"><input type="checkbox" name="chkAll" onclick="selectAll(this, 'ids');" id="chkAll"/></td>
                <td>区域</td>
                <td>开始时段</td>
				<td>结束时段</td>
				<c:if test="${not empty page.dataList[0].ployType }">
				<td>${page.dataList[0].typeName }</td>
				</c:if>
				<td>位置</td>
				<td>素材</td>
            </tr>
            <c:forEach items="${page.dataList}" var="omr" varStatus="pl">
				<tr <c:if test="${pl.index%2==1}">class="sec"</c:if> 
					onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
					<td>
						<input type="checkbox" name="ids" value="${omr.id}"  onclick="isAll(this,${omr.releaseArea.areaCode})"/>
					</td>
					<td><c:out value="${omr.releaseArea.areaName}" /></td>
					<td><c:out value="${omr.startTime}" /></td>
					<td><c:out value="${omr.endTime}" /></td>
					<c:if test="${not empty omr.ployType}">
						<td><c:out value="${omr.valueName}" /></td>
					</c:if>
					<td><c:out value="${omr.indexNum}" /></td>
					<td><div id="${omr.id}">${omr.resource.resourceName}</div></td>
				</tr>
			</c:forEach>
            <tr>
            	
            	<td colspan="7">
            	<c:if test="${empty omrTmp.resource.id }">
	            	<span class="required"></span>选择素材：
	            	<input id="resource" name="resource" class="e_input_add" value="" readonly="readonly" type="text" onclick="showResource();"/>
	            	<input type="button" onclick="delResource();" class="btn" value="删除素材" /> &nbsp;&nbsp; 
            	</c:if>
            	<jsp:include page="../../common/page.jsp" flush="true" />
            	</td>
            </tr>
        </table>
        <c:if test="${empty omrTmp.resource.id }">
        	<input type="button" value="确定" class="btn" onclick="javascript:window.close();"/>
        </c:if>
    </div>
</form>
</body>
</html>