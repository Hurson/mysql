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
			if (getCheckCount('ids') <= 0) {
	         alert("请勾选需要删除素材的记录！"); 
	         return;
	    }
		var idss = document.getElementsByName("ids");		
        	
	    var orderCode = document.getElementById("orderCode").value;
        	var selectedAreas = getCheckValue('ids');
        	
 
        	$.ajax({   
	 		       url:'delBootOrderMateRelTmp.do',       
	 		       type: 'POST',    
	 		       dataType: 'text',   
	 		       data: {
	 		    	  orderCode: orderCode,
	 		    	  selectedAreas: selectedAreas
	 				},                   
	 		       timeout: 1000000000,                              
	 		       error: function(){                      
	 		    		alert("系统错误，请联系管理员！");
	 		       },    
	 		       success: function(result){ 
	 		    	   if(result == '-1'){
	 		    		  alert("系统错误，请联系管理员！");
	 		    	   }else{
	 		    	   for (var i = 0; i < idss.length; i++) {
	        				if (idss[i].checked) {
	        				document.getElementById(idss[i].value).innerHTML = '';
	           			 }
        					}
	 		    	   }
	 			   }  
	 		   });
	 		   
		//$("#queryForm").attr("action", "delBootOrderMateRelTmp.do");
		//$("#queryForm").submit();
		
	}
	
	function cancle() {
		window.returnValue="";
        window.close();
    }
	function isAll(checkBoxObject,locationCode){
		/*
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
        	}*/
        	
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
        	for (var i = 0; i < idss.length; i++) {
	        	if (idss[i].checked) {
	        		document.getElementById(idss[i].value).innerHTML = resourceArray[0].resourceName;
	            }
        	}
        	
        	var orderCode = document.getElementById("orderCode").value;
        	var omRelTmpId = getCheckValue('ids');
        	var mateIds = "";
        	for(var i = 0; i < resourceArray.length; i++){
        		var resourceRef = resourceArray[i];
        		mateIds=resourceRef.resourceId;
        	}
        	$.ajax({   
	 		       url:'saveNVODMenuOrderReTmp.do',       
	 		       type: 'POST',    
	 		       dataType: 'text',   
	 		       data: {
	 		    	// orderCode: orderCode,
	 		    	 omRelTmpId: omRelTmpId,
	 		    	 mateIds: mateIds,
	 		    	 orderCode:orderCode
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
	
	function arrayToStr(array){
	    var str = "";
	    if(array && array.length > 0){       
	        for(var i = 0; i < array.length; i++){
	            str += array[i] + ",";
	        }
	        var strlen = str.length;
	        if(strlen > 0 && str.charAt(strlen - 1) == ','){
	            str = str.substring(0, strlen -1);
	        }
	    }
	    return str;
	}
    
</script>
<body class="mainBody">
<form action="initAreaResource.do" method="post" id="queryForm">
	<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
	<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<input type="hidden" id="positionId" name="order.positionId" value="${order.positionId}"/>
	<input type="hidden" id="ployId" name="order.ployId" value="${order.ployId}"/>
	<input type="hidden" id="orderCode" name="omRelTmp.orderCode" value="${omRelTmp.orderCode}"/>
	<div class="searchContent">
		<table cellspacing="1" class="searchList">
            <tr class="title">
                <td>查询条件</td>
            </tr>
            <tr>
                <td class="searchCriteria">
                	<span>开始时间段：</span><input name="omRelTmp.startTime" type="text" readonly="readonly" onclick="WdatePicker({dateFmt:'HH:mm:ss'})" value="${omRelTmp.startTime}" style="width: 14%"/>
                    <span>结束时间段：</span><input name="omRelTmp.endTime" type="text" readonly="readonly" onclick="WdatePicker({dateFmt:'HH:mm:ss'})" value="${omRelTmp.endTime}" style="width: 14%"/>
                    <input type="checkbox" name="omRelTmp.contain" value="1" 
                    <c:if test="${omRelTmp.contain=='1'}">
                    	checked="checked"
                    </c:if> 
                    /><span>包含已配置</span>
   				</td>
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
              <td height="28" class="dot"><input type="checkbox" name="chkAll" onclick="selectAll(this, 'ids');" id="chkAll"/></td>
              	<td>开始时段段</td>
				<td>结束时段</td>
                <td>区域</td>
                <td>编号</td>
				<td>素材</td>
            </tr>
            
            
            <c:forEach items="${page.dataList}" var="omRelTmp" varStatus="pl">
				<tr <c:if test="${pl.index%2==1}">class="sec"</c:if> 
					onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
					<td>
						<input type="checkbox" name="ids" value="${omRelTmp.id}"  onclick="isAll(this,${omRelTmp.id})"/>
					</td>
					<td><c:out value="${omRelTmp.startTime}" /></td>
					<td><c:out value="${omRelTmp.endTime}" /></td>
					<td><c:out value="${omRelTmp.areaName}" /></td>
					<td><c:out value="${omRelTmp.pollIndex}"/></td>
					<!--<td><div id="${omRelTmp.areaCode}">${omRelTmp.mateId}</div></td>-->
					      <td><div id="${omRelTmp.id}">
					   <c:choose>						
						<c:when test="${omRelTmp.mateId==null}"></c:when>
						<c:otherwise>${omRelTmp.resourceName}</c:otherwise>
					</c:choose>

						</div></td>
				</tr>
			</c:forEach>
            <tr>
            	<td colspan="6"><span class="required"></span>选择素材：
            	<input id="resource" name="resource" class="e_input_add" value="" readonly="readonly" type="text" onclick="showResource();"/>
            	<input type="button" onclick="delResource();" class="btn" value="删除素材" /> &nbsp;&nbsp; 
            	<jsp:include page="../common/page.jsp" flush="true" />
            	</td>
            </tr>
        </table>
        <input type="button" value="确定" class="btn" onclick="javascript:window.close()"/>
        
    </div>
</form>
</body>
</html>