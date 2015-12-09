<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<title>订单审核列表</title>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>

<script type="text/javascript">
    function query(){
    	if(validateSpecialCharacterAfter($("#contractName").val())){
			alert("合同名称不能包括特殊字符！");
			$("#contractName").focus();
			return ;
		}
		if(validateSpecialCharacterAfter($("#positionName").val())){
			alert("广告位名称不能包括特殊字符！");
			$("#positionName").focus();
			return ;
		}
		var start = $("#startDateStr").val();
    	var end = $("#endDateStr").val();
    	if(!isEmpty(start) && !isEmpty(end)){
	    	var startTime=Date.parse(start.replace(/-/g, "/"));
	        var endTime=Date.parse(end.replace(/-/g, "/"));
	        if(endTime<startTime){
	            alert("结束日期不能小于开始日期！");
	            return;
	        }
    	}
		$("#queryForm").submit();
    }

</script>
</head>

<body class="mainBody">
<form action="queryOrderAuditList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
<div class="search">
<div class="path">首页 >> 订单管理 >> 订单审核</div>
<div class="searchContent" >

<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span style="width: 60px;text-align:right;">广告商：</span>
      <select name="order.customerId" style="width: 115px">
      	<option value="">请选择</option>
   		<c:if test="${customerList != null }">
     		<c:forEach items="${customerList}" var="customer">
				<option value="${customer.id}" <c:if test="${customer.id == order.customerId}">selected</c:if>>${customer.advertisersName}</option>
			</c:forEach>
		</c:if>
     </select>
      <span style="width: 73px;text-align:right">订单类型：</span>
      <select name="order.orderType" style="width:  115px">
          <option value="">请选择</option>
          <option value="0" <c:if test="${order.orderType==0}">selected</c:if>>投放式</option>
		  <option value="1" <c:if test="${order.orderType==1}">selected</c:if>>请求式</option>
      </select>
      <span style="width: 60px;text-align:right">合同名称：</span><input type="text" id="contractName" name="order.contractName" value="${order.contractName}" id="contractName" />
      <span style="width: 80px;text-align:right">广告位名称：</span><input type="text" id="positionName" name="order.positionName" value="${order.positionName}" id="positionName" />
     </td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span style="width: 60px;text-align:right;">开始日期：</span>
	   	<input id="startDateStr" name="order.startDate" value="${order.startDate}" type="text" readonly="readonly" style="width:  100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('startDateStr')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="left"/>
	  <span style="width: 60px;text-align:right">结束日期：</span>
	    <input id="endDateStr" name="order.endDateStr" value="${order.endDateStr}" type="text" readonly="readonly" style="width:  100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('endDateStr')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
      &nbsp;&nbsp;<input type="button" value="查询" onclick="javascript:query();" class="btn"/>
     </td>
  </tr>
</table>

<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList" >
    <tr class="title" align="center">
        <td>订单号</td>
        <td>广告商</td>
        <td>合同名称</td>
        <td>广告位名称</td>
        <td>策略名称</td>
        <td>排期</td>
        <td>状态</td>
    </tr>
	<s:iterator value="page.dataList" status="status" var="order">
		<tr <s:if test="#status.index%2==1">class="sec"</s:if>
			onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'" align="center">
			<td><a href="getDOrder.action?operType=audit&order.id=<s:property value='#order.id' />"><s:property value="#order.orderCode" /></a></td>
			<td><s:property value="#order.customer.advertisersName" /></td>
			<td><s:property value="#order.contract.contractName" /></td>
			<td><s:property value="#order.dposition.positionName" /></td>
			<td><s:property value="#order.dploy.ployName" /></td>
			<td><s:date name="#order.startDate" format="yyyy-MM-dd" />~<s:date name="#order.endDate" format="yyyy-MM-dd" /></td>
			<td>
				<s:if test="#order.state==0">【未发布订单】待审核</s:if>
				<s:elseif test="#order.state==1">【修改订单】待审核</s:elseif>
				<s:elseif test="#order.state==2">【删除订单】待审核</s:elseif>
			</td>
			
		</tr>
	</s:iterator>
  <tr>
    <td colspan="7">
        <jsp:include page="../../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</div>
</div>
</form>
</body>
</html>