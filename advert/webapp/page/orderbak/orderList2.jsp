<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/order/listOrder.js"></script>

<style type="text/css">
        a{text-decoration:underline;}
</style>

<script type="text/javascript">
	function query() {
		$("pageNo").value=1;
		$("queryForm").submit();
	}

	function delOrder() {
		if (getCheckCount('ids') <= 0) {
	         alert("请选择需要删除的订单记录！");
	         return;
	    }

	    if (confirm("您确定需要删除所选的订单记录吗？")) {
	         document.getElementById("queryForm").action = "delOrder.do";
	         document.getElementById("queryForm").submit();
	    }
	}
    
</script>
</head>

<body class="mainBody">
<div class="search">
<div class="path">首页 >> 订单管理 >> 订单维护</div>
<div class="searchContent" >
<form action="listOrder.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
	<table cellspacing="1" class="searchList">
	  <tr class="title">
	    <td>查询条件</td>
	  </tr>
	  <tr>
	    <td class="searchCriteria">
	      <span>合同名称：</span><input type="text" name="orderBean.contractName" value="${orderBean.contractName }" />
	      <span>广告位名称：</span><input type="text" name="orderBean.positionName" value="${orderBean.positionName}" />
	      <span>策略名称：</span><input type="text" name="orderBean.ployName" value="${orderBean.ployName }" />
	      <span>状态：</span>
	        <select name="orderBean.state">
	            <option value="">请选择...</option>
				<option value="0,1,2" <c:if test="${orderBean.state=='0,1,2'}">selected</c:if>>待审核</option>
				<option value="3,4,5" <c:if test="${orderBean.state=='3,4,5' }">selected</c:if>>审核未通过</option>
				<option value="6" <c:if test="${orderBean.state=='6'}">selected</c:if>>已发布</option>
				<option value="7" <c:if test="${orderBean.state=='7' }">selected</c:if>>执行完毕</option>
	        </select>
	
	        <input type="submit" value="查询" onclick="javascript:query();" class="btn"/>
	     </td>
	  </tr>
	</table>

<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
	 <c:if test="${message != null}">
            <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
    </c:if>
</div> 
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="chkAll" onclick="selectAll(this, 'ids');" id="chkAll"/></td>
        <td>序号</td>
        <td>订单号</td>
        <td>排期</td>
        <td>合同名称</td>
        <td>广告位名称</td>
        <td>策略名称</td>
        <td>状态</td>
    </tr>
    <c:set var="index" value="0" />
	<s:iterator value="orderList" status="rowstatus" var="order">
		<tr>
			<c:set var="index" value="${index+1 }" />
			<td>
				<s:if test="state==0">
					<input type="checkbox" name="ids" value="${order.id}"  />
				</s:if>
				<s:elseif test="state==1">
					<input type="checkbox" name="ids" value="${order.id}"  />
				</s:elseif>
				<s:elseif test="state==2">
					<input type="checkbox" name="ids" value="${order.id}" disabled="disabled" />
				</s:elseif>
				<s:elseif test="state==3">
					<input type="checkbox" name="ids" value="${order.id}"  />
				</s:elseif>
				<s:elseif test="state==4">
					<input type="checkbox" name="ids" value="${order.id}"  />
				</s:elseif>
				<s:elseif test="state==5">
					<input type="checkbox" name="ids" value="${order.id}" disabled="disabled"  />
				</s:elseif>
				<s:elseif test="state==6">
					<input type="checkbox" name="ids" value="${order.id}"  />
				</s:elseif>
				<s:elseif test="state==7">
					<input type="checkbox" name="ids" value="${order.id}" disabled="disabled" />
				</s:elseif>
				<!-- <input type="hidden" name="states" value="${order.state }"/> -->
			</td>
			<td align="center" height="26"><c:out value="${index }" /></td>
			<td><a href="getOrderForUpdate.do?orderDetail.id=${order.id }&orderDetail.state=${order.state}"><c:out value="${order.orderNo}" /></a></td>
			<td><c:out value="${order.startTime}" />~<c:out value="${order.endTime}" /></td>
			<td><c:out value="${order.contractName}" /></td>
			<td><c:out value="${order.positionName}" /></td>
			<td><c:out value="${order.ployName}" /></td>
			<td>
				<c:choose>
					<c:when test="${order.state=='0'}">【未发布订单】待审核</c:when>
					<c:when test="${order.state=='1'}">【修改订单】待审核</c:when>
					<c:when test="${order.state=='2'}">【删除订单】待审核</c:when>
					<c:when test="${order.state=='3'}">【未发布订单】审核不通过</c:when>
					<c:when test="${order.state=='4'}">【修改订单】审核不通过</c:when>
					<c:when test="${order.state=='5'}">【删除订单】审核不通过</c:when>
					<c:when test="${order.state=='6'}">已发布</c:when>
					<c:when test="${order.state=='7'}">执行完毕</c:when>
				</c:choose>
			</td>
		</tr>
	</s:iterator>
  <tr>
    <td colspan="8">
       	<input type="button" value="删除" class="btn" onclick="javascript:delOrder();"/>&nbsp;&nbsp;
        <input type="button" value="添加" class="btn" onclick="window.location.href='createOrder.do'"/>
        <jsp:include page="../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</form>
</div>
</div>
</body>
</html>