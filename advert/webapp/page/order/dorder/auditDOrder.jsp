<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type='text/javascript' src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/preview.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/common.js"></script>
<title>审核订单</title>
<style>
	.ggw {
		width: 100%;
		height: 68px;
	}
	.ggw li {
		background: #efefef;
		font-weight: bold;
		width: 100%;
		height: 25px;
	}
	.e_input_add {
		background:url(<%=path%>/images/add.png) right no-repeat #ffffff;
	}
	
	.e_input_time{
		background:url(<%=path%>/js/new/My97DatePicker/skin/datePicker.gif) right no-repeat #ffffff;
	}
</style>
<script type="text/javascript">
	var op = 0;// 提交表单计数器，防止重复提交表单
	function init(){
		showOrderResource('${order.dposition.positionCode}','${order.orderCode}');
	}
	
	function showOrderResource(positionCode,orderCode){
    	$.ajax({   
   		       url:'getOrderResourceJson.action',       
   		       type: 'POST',    
   		       dataType: 'text',   
   		       data: {
	   				'omrTmp.orderCode':orderCode
   			   },                   
   		       timeout: 1000000000,                              
   		       error: function(){                      
   		    		alert("系统错误，请联系管理员！");
   		       },    
   		       success: function(result){ 
   		    	   if(result!=""){
   			    	   var json=eval("("+result+")");
					    var str="<tr><td>策略详情</td><td>素材</td><td>操作</td></tr>";
					    if(result==null||json==null){
							   return;
						}
					   for(var i = 0;i<json.length;i++){
					        var obj = json[i];
						    str+="<tr><td><a style='diplay:block;float:left' href=javascript:showOrderResourceDetail("+orderCode+","+obj.id+")>详情</a></td>"+
							 	 "<td>"+obj.resourceName+"</td>"+
						   		 "<td><a style='diplay:block;float:left' href=javascript:showDtmbSource('"+positionCode+"',"+obj.id+")>预览</a></td></tr>";
						   		 
					   }
					   $("#sucai").html(str);
   		    	   }else{
   			    		alert("系统错误，请联系管理员！");
   		    	   }
   		    	   
   			 }  
  		}); 
   	}
    function showOrderResourceDetail(orderCode, resourceId){
    
    	var url = "queryDOrderMateRelTmp.action?omrTmp.orderCode="+orderCode+"&omrTmp.resource.id="+resourceId;
    	window.showModalDialog(url, window, "dialogHeight=580px;dialogWidth=820px;center=1;resizable=0;status=0;");
    }
    function showDtmbSource(positionCode,resourceId){
    	var url = "previewResource.action?resource.positionCode="+positionCode+"&resource.id="+resourceId;
    	window.showModalDialog(url, window, "dialogHeight=245px;dialogWidth=428px;center=1;resizable=0;status=0;");
    }
    function save(flag){
    	if(op==0){
			
			if(flag==-1&&$("#checkOpinion").val()==''){
				alert("此订单审核不通过，请输入审核意见！");
				$("#checkOpinion").focus();
				return ;
			}
			if($("#checkOpinion").val()!=''&&$("#checkOpinion").val().length>120){
				alert("审核意见字数在0-120字之间！");
				$("#checkOpinion").focus();
				return;
			}
			if(validateSpecialCharacterAfter($("#checkOpinion").val())){
				alert("审核意见不能包括特殊字符！");
				$("#checkOpinion").focus();
				return ;
			}
			op = 1;
			var orderId = '${order.id}';
			var endDate = '${order.endDate}';
			$.ajax( {
				type : "post",
				url : 'auditDOrder.action',
				dataType : 'text',
				data : {
					'order.id':orderId,
					'order.endDate':endDate,
					'flag': flag,
					'order.auditAdvice':$("#checkOpinion").val()
				},
				success : function(result) {
					if (result == '0') {
						alert('提交审核结果成功！');
						window.location.href="queryAuditDOrderList.action"
					}else if(result == '-1'){
						alert("服务器异常，检查失败，请稍后重试！");
					}else{
						alert(result);
					}
					op=0;
				},
				error : function() {
					alert("服务器异常，检查失败，请稍后重试！");
					op=0;
				}
			});
		}else{
			alert('请不要重复提交表单！');
		}
	}
	//显示订单审核日志
	function showAuditLog(relationId){
    	var url = "queryOrderAuditLog.do?auditLog.relationType=1&auditLog.relationId="+relationId;
    	window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
	}
</script>
</head>

<body class="mainBody" onload="init()">
<form action="saveDOrder.action" method="post" id="saveForm">
<input type="hidden" id="selResource" name="order.selResource"  />
<input type="hidden" id="startTime" name="order.startDateStr"  />
<div class="detail">
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">订单添加</td>
	</tr>
	<c:if test="${roleType == 1}">
	<!-- 广告商 -->
	<tr>
		<td width="12%" align="right">订单号：</td>
		<td width="33%"><input type="hidden" id="orderCode" name="order.orderCode" value="${order.orderCode}" /> ${order.orderCode}</td>
		<td width="12%" align="right"><span class="required">*</span>合同名称：</td>
		<td width="33%">
			<input id="contractName" name="order.contractName" class="e_input_add" readonly="readonly" type="text" onclick="showContent('contract',null);"/>
			<input type="hidden" id="contractId" name="order.contract.id" />
			<span id="contract_error"></span>
		</td>
	</tr>
	</c:if>
	<c:if test="${roleType == 2}">
	<!-- 运营商 -->
	<tr>
		<td width="12%" align="right">订单号：</td>
		<td colspan="3"><input type="hidden" id="orderCode" name="order.orderCode" value="${order.orderCode}" /> ${order.orderCode}</td>
		<input type="hidden" id="contractId" name="order.contract.id" value="0"  />
	</tr>
	</c:if>
	<tr>
		<td align="right"><span class="required">*</span>广告位名称：</td>
		<td>
			
			<c:if test="${empty order.id }">
				<select id="positionCode" name="order.dposition.positionCode" onchange="showContent('position',null);">
					<option value="">--请选择--</option>
					<c:forEach items="${dpositionList }" var="position">
						<option value="${position.positionCode }">${position.positionName }</option>
					</c:forEach>
				</select>
			</c:if>
			<c:if test="${not empty order.id }">
				<input type="hidden" id="positionCode" name="order.dposition.positionCode" value="${order.dposition.positionCode }"/>
				<c:out value="${order.dposition.positionName }"/>
			</c:if>
			<span id="position_error"></span>
		</td>
		<td align="right"><span class="required">*</span>策略名称：</td>
		<td>
			<c:out value="${order.dploy.ployName }"/>
		</td>
	</tr>
	
	<tr id="periodDate">
		<td align="right"><span class="required">*</span>开始日期：</td>
		<td><fmt:formatDate type="date" value="${order.startDate }"/></td>
		<td align="right"><span class="required">*</span>结束日期：</td>
		<td><fmt:formatDate type="date" value="${order.endDate }"/></td>
	</tr>
	<tr>
		 <td align="right">订单状态：</td>
         <td colspan="3">
         	<c:choose>
				<c:when test="${order.state=='0'}">【未发布订单】待审核</c:when>
				<c:when test="${order.state=='1'}">【修改订单】待审核</c:when>
				<c:when test="${order.state=='2'}">【删除订单】待审核</c:when>
			</c:choose>
         </td>
	</tr>
	<tr>
		<td align="right">订单描述：</td>
		<td colspan="3"><textarea id="desc" disabled="disabled" name="order.description" cols="50" rows="3"></textarea>${order.description }</td>
	</tr>
	 <tr>
            <td align="right">审核意见：</td>
            <td colspan="3">
                <textarea id="checkOpinion" name="order.auditAdvice" cols="50" rows="3"></textarea>
                <span id="opinions_error" class="required">
                <c:if test="${not empty order.auditAdvice}">
                	错误提示：${order.opinion}
                </c:if>
                </span>
            </td>
     </tr>
	<tr>
	    <td colspan="3" class="yulan"><span
			style="display: block; width: 500px;">策略绑定素材信息:</span>
		</td>
		<td>
			
		</td>
   </tr>
        <table name="sucai" id="sucai"  width="100%" cellspacing="1" class="searchList">
        	<tr><td>策略详情</td><td>素材</td><td>操作</td></tr>
        </table>
</table>
<div style="margin-left:50px;">
	<c:if test="${empty order.auditAdvice}">
        <input type="button" class="btn" value="通过" onclick="javascript:save(1);" />&nbsp;&nbsp;&nbsp;&nbsp;
        </c:if>
        <input type="button" class="btn" value="驳回" onclick="javascript:save(-1);" />&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" class="btn" value="返回" onclick="javascript :history.back(-1)" />&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:showAuditLog('${order.id}');" >审核日志</a>
</div>
</div>

</form>
</body>
</html>