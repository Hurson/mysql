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

<style type="text/css">
        a{text-decoration:underline;}
</style>

<script type="text/javascript">
    function query(){
    	$("pageNo").value=1;
		$("queryForm").submit();
    }

</script>
</head>

<body class="mainBody">
<form action="listOrderForCheck.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
<div class="search">
<div class="path">首页 >> 订单管理 >> 订单审核</div>
<div class="searchContent" >

<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span style="width: 60px;text-align:right;">广告商：${orderBean.customerId}</span>
      <select name="orderBean.customerId" style="width: 115px">
      	<option value="">请选择</option>
   		<c:if test="${customerList != null }">
     		<c:forEach items="${customerList}" var="customer">
				<option value="${customer.id}" <c:if test="${customer.id == orderBean.customerId}">selected</c:if>>${customer.advertisersName}</option>
			</c:forEach>
		</c:if>
     </select>
      <span style="width: 73px;text-align:right">订单类型：</span>
      <select name="orderBean.orderType" style="width:  115px">
          <option value="">请选择</option>
          <option value="0" <c:if test="${orderBean.orderType=='0' }">selected</c:if>>投放式</option>
		  <option value="1" <c:if test="${orderBean.orderType=='1' }">selected</c:if>>请求式</option>
      </select>
      <span style="width: 60px;text-align:right">合同名称：</span><input type="text" name="orderBean.contractName" value="${orderBean.contractName}" id="contractName" />
      <span style="width: 80px;text-align:right">广告位名称：</span><input type="text" name="orderBean.positionName" value="${orderBean.positionName}" id="positionName" />
     </td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span style="width: 60px;text-align:right;">开始日期：</span>
	   	<input id="startDateStr" name="orderBean.startDateStr" value="${orderBean.startDateStr}" type="text" readonly="readonly" style="width:  100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('startDateStr')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="left"/>
	  <span style="width: 60px;text-align:right">结束日期：</span>
	    <input id="endDateStr" name="orderBean.endDateStr" value="${orderBean.endDateStr}" type="text" readonly="readonly" style="width:  100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('endDateStr')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
      &nbsp;&nbsp;<input type="submit" value="查询" onclick="javascript:query();" class="btn"/>
     </td>
  </tr>
</table>

<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td>序号</td>
        <td>订单号</td>
        <td>广告商</td>
        <td>排期</td>
        <td>合同名称</td>
        <td>广告位名称</td>
        <td>策略名称</td>
        <td>状态</td>
        <td>订单类型</td>
    </tr>
    <c:set var="index" value="0" />
	<c:forEach items="${orderList}" var="order">
		<tr>
			<c:set var="index" value="${index+1 }" />
			<td align="center" height="26"><c:out value="${index }" /></td>
			<td><a href="getOrderAuditDetail.do?orderDetail.id=${order.id }"><c:out value="${order.orderNo}" /></a></td>
			<td><c:out value="${order.advertisersName}" /></td>
			<td><c:out value="${order.startTime}" />~<c:out value="${order.endTime}" /></td>
			<td><c:out value="${order.contractName}" /></td>
			<td><c:out value="${order.positionName}" /></td>
			<td><c:out value="${order.ployName}" /></td>
			<td>
				<c:choose>
					<c:when test="${order.state=='0'}">【未发布订单】待审核</c:when>
					<c:when test="${order.state=='1'}">【修改订单】待审核</c:when>
					<c:when test="${order.state=='2'}">【删除订单】待审核</c:when>
				</c:choose>
			</td>
			<td>
				<c:choose>
					<c:when test="${order.orderType=='0'}">投放式</c:when>
					<c:when test="${order.orderType=='1'}">请求式</c:when>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
  <tr>
    <td colspan="9">
        <jsp:include page="../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</div>
</div>
</form>
</body>
</html>