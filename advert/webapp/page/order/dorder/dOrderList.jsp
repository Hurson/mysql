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
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>

<script type="text/javascript">
	function query() {
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
		if(validateSpecialCharacterAfter($("#ployName").val())){
			alert("策略名称不能包括特殊字符！");
			$("#ployName").focus();
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

	function delOrder() {
		if (getCheckCount('ids') <= 0) {
	         alert("请选择需要删除的订单记录！");
	         return;
	    }

	    if (confirm("您确定需要删除所选的订单记录吗？")) {
	         document.getElementById("queryForm").action = "deleteDOrder.action";
	         document.getElementById("queryForm").submit();
	    }
	}
    
</script>
</head>

<body class="mainBody">
<div class="search">
<div class="path">首页 >> 订单管理 >> 订单维护</div>
<div class="searchContent" >
<form action="queryOrderList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<table cellspacing="1" class="searchList">
	  <tr class="title">
	    <td>查询条件</td>
	  </tr>
	  <tr>
	    <td class="searchCriteria">
	      <span>合同名称：</span><input type="text" id="contractName" name="order.contractName" value="${order.contract.contractName }" />
	      <span>广告位名称：</span><input type="text" id="positionName" name="order.positionName" value="${order.dposition.positionName}" />
	      <span>策略名称：</span><input type="text" id="ployName" name="order.ployName" value="${order.dploy.ployName }" />
	      <span>状态：</span>
	        <select name="order.state">
	            <option value="">请选择...</option>
				<option value="0,1,2" <c:if test="${order.state=='0,1,2'}">selected</c:if>>待审核</option>
				<option value="3,4,5" <c:if test="${order.state=='3,4,5' }">selected</c:if>>审核未通过</option>
				<option value="6" <c:if test="${order.state=='6'}">selected</c:if>>已发布</option>
				<option value="7" <c:if test="${order.state=='7' }">selected</c:if>>执行完毕</option>
				<option value="9" <c:if test="${order.state=='9' }">selected</c:if>>投放失败</option>
	        </select>
	     </td>
	  </tr>
	  <tr>
    <td class="searchCriteria">
      <span style="width: 60px;text-align:right;">开始日期：</span>
	   	<input id="startDateStr" name="order.startDate" value="${order.startDate}" type="text" readonly="readonly" style="width:  100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('startDateStr')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="left"/>
	  <span style="width: 60px;text-align:right">结束日期：</span>
	    <input id="endDateStr" name="order.endDate" value="${order.endDate}" type="text" readonly="readonly" style="width:  100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('endDateStr')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
      &nbsp;&nbsp;<input type="button" value="查询" onclick="javascript:query();" class="btn"/>
     </td>
  </tr>
	</table>

<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
	 <c:if test="${message != null}">
            <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
    </c:if>
</div> 
<table cellspacing="1" class="searchList" >
    <tr class="title" align="center">
        <td height="28" class="dot"><input type="checkbox" name="chkAll" onclick="selectAll(this, 'ids');" id="chkAll"/></td>
        <td>订单号</td>
        <td>合同名称</td>
        <td>广告位名称</td>
        <td>策略名称</td>
        <td>排期</td>
        <td>状态</td>
    </tr>
	<s:iterator value="page.dataList" status="status" var="order">
		<tr <s:if test="#status.index%2==1">class="sec"</s:if>
			onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'" align="center">
			<td>
				<s:if test="#order.state==0 || #order.state==1 || #order.state==3 || #order.state==4 || #order.state==6">
					<input type="checkbox" name="ids" value="<s:property value='#order.id' />"  />
				</s:if>
				<s:else>
					<input type="checkbox" name="ids" value="<s:property value='#order.id' />" disabled="disabled" />
				</s:else>
			</td>
			<td><a href="getDOrder.action?order.id=<s:property value='#order.id' />"><s:property value="#order.orderCode" /></a></td>
			<td><c:if test="${empty order.contract }">无合同</c:if><s:property value="#order.contract.contractName" /></td>
			<td><s:property value="#order.dposition.positionName" /></td>
			<td><s:property value="#order.dploy.ployName" /></td>
			<td><s:date name="#order.startDate" format="yyyy-MM-dd" />~<s:date name="#order.endDate" format="yyyy-MM-dd" /></td>
			<td>
				<s:if test="#order.state==0">【未发布订单】待审核</s:if>
				<s:elseif test="#order.state==1">【修改订单】待审核</s:elseif>
				<s:elseif test="#order.state==2">【删除订单】待审核</s:elseif>
				<s:elseif test="#order.state==3">【未发布订单】审核不通过</s:elseif>
				<s:elseif test="#order.state==4">【修改订单】审核不通过</s:elseif>
				<s:elseif test="#order.state==5">【删除订单】审核不通过</s:elseif>
				<s:elseif test="#order.state==6">已发布</s:elseif>
				<s:elseif test="#order.state==7">执行完毕</s:elseif>
				<s:elseif test="#order.state==9">投放失败</s:elseif>
			</td>
		</tr>
	</s:iterator>
  <tr>
    <td colspan="7">
       	<input type="button" value="删除" class="btn" onclick="javascript:delOrder();"/>&nbsp;&nbsp;
        <input type="button" value="添加" class="btn" onclick="window.location.href='getDOrder.action'"/>
        <jsp:include page="../../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</form>
</div>
</div>
</body>
</html>