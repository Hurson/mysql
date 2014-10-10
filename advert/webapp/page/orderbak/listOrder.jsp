<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path%>/css/menu_right.css"
	type="text/css" />
<link rel="stylesheet" href="<%=path%>/css/platform.css" type="text/css" />
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/order/listOrder.js"></script>
<title>订单管理</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});
</script>
<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}
</style>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td>
		<table cellpadding="0" cellspacing="0" border="0" width="100%"
			class="tab_right">
			<tr>
				<td>工作台</td>
				<td>客户</td>
				<td>日程行动</td>
				<td>销售机会</td>
				<td>订单管理</td>
				<td>客服中心</td>
				<td>财务中心</td>
				<td>营销中心</td>
				<td>人力资源中心</td>
				<td>数据统计</td>
				<td>信息门户管理</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding: 4px;">
		<form action="listOrder.do" method="post"><input type="hidden"
			name="pageNo" value="1" />
		<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
			<tr>
				<td class="formTitle" colspan="99"><span>·订单详细信息</span></td>
			</tr>
			<tr>
				<td class="td_label">合同号：</td>
				<td class="td_input"><input name="contractCode" class="e_input"
					onfocus="this.className='e_inputFocus'"
					onblur="this.className='e_input'" value="${contractCode }" /></td>
				<td class="td_label">广告位：</td>
				<td class="td_input"><input name="positionName" class="e_input"
					onfocus="this.className='e_inputFocus'"
					onblur="this.className='e_input'" value="${positionName}" /></td>
			</tr>

			<tr>
				<td class="td_label">策略名称：</td>
				<td class="td_input"><input name="ployName" class="e_input"
					onfocus="this.className='e_inputFocus'"
					onblur="this.className='e_input'" value="${ployName }" /></td>
				<td class="td_label">订单状态：</td>
				<td class="td_input"><select name="orderState" class="e_select">
					<option value="">请选择...</option>
					<option value="0,1,2" <c:if test="${orderState=='0,1,2'}">selected</c:if>>待审核</option>
					<option value="3,4,5" <c:if test="${orderState=='3,4,5' }">selected</c:if>>审核未通过</option>
					<option value="6" <c:if test="${orderState=='6'}">selected</c:if>>已发布</option>
					<option value="7" <c:if test="${orderState=='7' }">selected</c:if>>执行完毕</option>
				</select></td>
			</tr>

			<tr>
				<td class="formBottom" colspan="99"><input name="Submit"
					type="submit" title="查看" class="b_search" value="" onfocus=blur() />
				<input name="Submit" type="button" title="添加" class="b_add" value=""
					onclick="window.location.href='createOrder.do'" onfocus=blur() />
				</td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
	<tr>
		<td style="padding: 4px;">
		<form>
		<table cellpadding="0" cellspacing="1" width="100%"
			class="taba_right_list" id="bm">

			<tr>
				<td colspan="12"
					style="padding-left:8px; background:url(<%=path%>/images/menu_righttop.png) repeat-x; text-align:left; height:26px;"><span>·订单列表</span></td>
			</tr>

			<tr>
				<td height="26px" align="center">
					<input type="checkbox" name="chkAll" 
				onclick=checkAll(this.form) id="chkAll"/>
				</td>
				<td>序号</td>
				<td>订单号</td>
				<td>排期</td>
				<td>合同号</td>
				<td>广告位</td>
				<td>策略名称</td>
				<td>状态</td>
				<td>创建时间</td>
				<td>修改时间</td>
				<td>操作员</td>
				<td>操作</td>
			</tr>
			<c:set var="index" value="0" />
			<c:forEach items="${orders}" var="order">
				<tr>
					<c:set var="index" value="${index+1 }" />
					<td>
						<input type="checkbox" name="ids" value="${order.id}" />
						<input type="hidden" name="states" value="${order.state }"/>
					</td>
					<td align="center" height="26"><c:out value="${index }" /></td>
					<td><c:out value="${order.orderNo}" /></td>
					<td><c:out value="${order.startTime}" />~<c:out
						value="${order.endTime}" /></td>
					<td><c:out value="${order.contractCode}" /></td>
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
					<td><c:out value="${order.createTime}" /></td>
					<td><c:out value="${order.modifyTime}" /></td>
					<td><c:out value="${order.userName}" /></td>
					<td>
					<!-- <input type="button" class="button_start"
						value="" title="查看" onfocus="blur()"
						onclick="window.location.href='getOrder.do?id=${order.id}&op=view'" />
					 -->	
						<input type="button" class="button_halt"
						value="" title="编辑" onfocus="blur()"
						onclick="editOrder('${order.id}','${order.state }')" /> 
					
						<input type="button" class="button_delete" value=""
						title="删除" onfocus="blur()" 
						onclick="validateDelOrder('${order.id}','${order.state }')" />
						
					</td>
				</tr>
			</c:forEach>
			<c:if test="${index<page.pageSize}">
				<c:forEach begin="${index}" end="${page.pageSize-1}" step="1">
					<tr>
						<td align="center" height="26">&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</c:forEach>
			</c:if>
			<tr>
				<td height="34" colspan="12"
					style="background: url(images/bottom.jpg) repeat-x; text-align: right;">
					<a href="javascript:validateDelOrders();">全部删除</a>
				<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp; <c:choose>
					<c:when test="${page.pageNo==1&&page.totalPage>1}">
						<a href="listOrder.do?pageNo=${page.pageNo+1 }">下一页</a>&nbsp;&nbsp;
		<a href="listOrder.do?pageNo=${page.totalPage}">末页</a>&nbsp;&nbsp;
	</c:when>
					<c:when test="${page.pageNo==page.totalPage&&page.totalPage>1 }">
						<a href="listOrder.do?pageNo=1">首页</a>&nbsp;&nbsp;
		<a href="listOrder.do?pageNo=${page.pageNo-1 }">上一页</a>&nbsp;&nbsp;
	</c:when>
					<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
						<a href="listOrder.do?pageNo=1">首页</a>&nbsp;&nbsp;
		<a href="listOrder.do?pageNo=${page.pageNo-1 }">上一页</a>&nbsp;&nbsp;
		<a href="listOrder.do?pageNo=${page.pageNo+1 }">下一页</a>&nbsp;&nbsp;
		<a href="listOrder.do?pageNo=${page.totalPage}">末页</a>&nbsp;&nbsp;
	</c:when>
				</c:choose></td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>
<div style="position: absolute; width: 100%; left: 0px; bottom: 0px;">
</div>
</body>
</html>
