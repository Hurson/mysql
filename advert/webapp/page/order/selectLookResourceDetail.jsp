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

	function delResource(){
		$("#queryForm").attr("action", "delOrderMateRelTmp.do");
		$("#queryForm").submit();
	}
	
	function cancle() {
		window.returnValue="";
        window.close();
    }

	function showResource(){
		var idss = document.getElementsByName("ids");
		var url = "queryAreaResourceList.do?resource.advertPositionId="+$("#positionId").val()+"&ployId="+$("#ployId").val();
		var resourceArray = window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
		if (resourceArray && resourceArray != null) {
			var resourceId ;
			var materialName = "";
			var mLoopsValue ;//轮询序号
        	if(resourceArray.length>0){
				for(var i=0;i<resourceArray.length;i++){
					mLoopsValue = resourceArray[i].mLoopsValue;
					if(mLoopsValue && mLoopsValue != '-1'){
						resourceId += resourceArray[i].resourceId + ",";
						materialName += resourceArray[i].resourceName + "["+mLoopsValue+"],";
					}else{
						resourceId = resourceArray[i].resourceId ;
						materialName = resourceArray[i].resourceName ;
						break;
					}
				}
        	}
        	if(resourceId.charAt(resourceId.length-1)==","){
        		resourceId = resourceId.substring(0,resourceId.length-1);
	        }
        	if(materialName.charAt(materialName.length-1)==","){
        		materialName = materialName.substring(0,materialName.length-1);
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
        <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
              <!--<td height="28" class="dot"><input type="checkbox" name="chkAll" onclick="selectAll(this, 'ids');" id="chkAll"/></td>
                --><td>开始时段</td>
				<td>结束时段</td>
				<td>栏目</td>
				<td>素材</td>
            </tr>
            <c:forEach items="${page.dataList}" var="omRelTmp" varStatus="pl">
				<tr <c:if test="${pl.index%2==1}">class="sec"</c:if> 
					onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
					<!--<td>
						<input type="checkbox" name="ids" value="${omRelTmp.id}" />
					</td>
					--><td><c:out value="${omRelTmp.startTime}" /></td>
					<td><c:out value="${omRelTmp.endTime}" /></td>
					<td><c:out value="${omRelTmp.channelGroupName}" /></td>
					<td><div id="${omRelTmp.id}">${omRelTmp.mateName}</div></td>
				</tr>
			</c:forEach>
            <tr>
            	<td colspan="5"><!--<span class="required"></span>选择素材：
            	<input id="resource" name="resource" class="e_input_add" value="" readonly="readonly" type="text" onclick="showResource();"/>
            	<input type="button" onclick="delResource();" class="btn" value="删除素材" /> &nbsp;&nbsp; 
            	--><jsp:include page="../common/page.jsp" flush="true" />
            	</td>
            </tr>
        </table>
    </div>
</form>
</body>
</html>