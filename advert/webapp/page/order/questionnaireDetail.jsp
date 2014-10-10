<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<title>问卷订单明细</title>
</head>
<body class="mainBody" >
<div class="detail">
    <table cellspacing="1" class="searchList" align="left">
        <tr class="title">
            <td colspan="4">问卷订单明细</td>
        </tr>
        <tr>
            <td width="12%" align="right">订单编号：</td>
            <td width="33%">${order.orderCode}</td>
            <td width="12%" align="right">合同名称：</td>
            <td width="33%">${order.contractName }</td>
        </tr>
        <tr>
            <td width="12%" align="right">广告位名称：</td>
            <td width="33%">${order.positionName }</td>
            <td width="12%" align="right">策略名称：</td>
            <td width="33%">${order.ployName }</td>
        </tr>
        <tr>
			<td width="12%" align="right">用户总次数：</td>
            <td width="33%">${order.userNumber}</td>
            <td width="12%" align="right">问卷总次数：</td>
            <td width="33%">${order.questionnaireNumber}</td>
		</tr>
		
		<tr>
			<td width="12%" align="right">通知门限值：</td>
            <td width="33%">${order.thresholdNumber}</td>
            <td width="12%" align="right">已请求问卷次数：</td>
            <td width="33%">${order.questionnaireCount}</td>
		</tr>
		<tr>
            <td width="12%" align="right">积分兑换人民币比率：</td>
            <td colspan="3">${order.integralRatio}</td>
		</tr>
        <tr>
            <td width="12%" align="right">开始日期：</td>
            <td width="33%"><fmt:formatDate value="${order.startTime}" dateStyle="medium"/></td>
            <td width="12%" align="right">结束日期：</td>
            <td width="33%"><fmt:formatDate value="${order.endTime}" dateStyle="medium"/></td>
        </tr>
        <tr>
            <td width="12%" align="right">订单类型：</td>
            <td width="33%">
            	<c:choose>
					<c:when test="${order.orderType==0}">投放式</c:when>
					<c:when test="${order.orderType==1}">请求式</c:when>
				</c:choose>
            </td>
            <td width="12%" align="right">订单状态：</td>
            <td width="33%">
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
        <tr>
            <td width="12%" align="right">描述：</td>
            <td colspan="3">
                <textarea disabled="disabled" cols="40" rows="3">${order.description }</textarea>
            </td> 
        </tr>
    </table>
    <div class="action">
        <input type="button" value="已阅" class="btn" onclick="window.location.href='saveRealQuestionnaire.do?order.id=${order.id}'"/>
         &nbsp;&nbsp; 
        <input type="button" onclick="javascript :history.back(-1);" class="btn" value="返回" />
    </div>
</div>

</body>
</html>