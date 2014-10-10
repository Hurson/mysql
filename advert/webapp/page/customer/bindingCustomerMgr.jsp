<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>
<title>广告系统</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    $("#queryCustomerButton").click(function(){
     	$("#bindingCustomerForm").submit();
     });
     
     var parentCustomerId=parent.alreadyFillInForm.customerId;
     $("input[type='radio']").each(function(index,elements){
	        if(!$.isEmptyObject(parentCustomerId)){
	        	if(elements.id==parentCustomerId){
	        		elements.checked=true;
	        	}
	        }
	 });
});

/**
 * 选择广告商
 */
function chooseCustomer(customerId,customerName){
	$("#customerId",window.parent.document).val(customerId);
	parent.alreadyFillInForm.customerId=customerId;
	parent.alreadyFillInForm.customerName=customerName;
	parent.easyDialog.close();
}
</script>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6; }
</style>
</head>

<body onload="">
	<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
	<tr>
			<td style=" padding:1px;">
				<form action="adCustomerMgr_list.do?contractBindingFlag=1"  id="bindingCustomerForm" name="bindingCustomerForm" method="post">
					<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
						<tr>
							<td class="formTitle" colspan="99">
								<span>查询 客户 条件</span>
							</td>
						</tr>
						<tr>
							<td class="td_label">广告商名称：</td>
							<td class="td_input">
								<input type="text" name="customerBackUp.advertisersName" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
							</td>
						</tr>
						<tr>
							<td class="td_label">客户代码</td>
							<td class="td_input">
								<input type="text" name="customerBackUp.clientCode" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
							</td>
						</tr>
						<tr>
							<td class="td_label">状态：</td>
							<td class="td_input">
								<select id="selectId" name="customerBackUp.status">
									<option value="1" selected="selected">审核通过</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="formBottom" colspan="99">
							   <input id="queryCustomerButton" name="queryCustomerButton" type="button" title="查看" class="b_search" value="" onfocus=blur()/>
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
		<tr>
		<td style="padding:1px;height: 100%;">
			<div>  
				<table cellpadding="0" cellspacing="1" width="100%" class="taba_right_list" id="bm">
					<tr>
						<td colspan="23" style="padding-left: 8px; background: url(<%=path %>/images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>展示·广告商列表</span></td>
					</tr>
					<tr>
						<td height="26px" align="center">选项</td>
						<td height="26px" align="center">序号</td>
						<td align="center">广告商名称</td>
						<td align="center">客户代码</td>
						<td align="center">联系人</td>
						<td align="center">联系方式</td>
						<td align="center">状态</td>
					</tr>
					<c:choose>
						<c:when test="${!empty listCustomers}">
							<c:forEach var="customer" items="${listCustomers}" varStatus="status">
								<tr>
									<td align="center"><input type="radio" id="customerRadio${customer.id}" name="customer" value="${customer.id}" onclick="chooseCustomer('${customer.id}','${customer.advertisersName}')"/></td>
									<td align="center" height="26">${status.count}</td>
									<td>${customer.advertisersName}</td>
									<td>${customer.clientCode}</td>
									<td>${customer.communicator}</td>
									<td>${customer.contacts}</td>
									<td>
										<c:choose>
											<c:when test="${customer.status==0}">
												待审核
											</c:when>
											<c:when test="${customer.status==1}">
												审核通过
											</c:when>
											<c:otherwise>
												审核不通过
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="8">
									暂无记录
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td colspan="8"></td>
					</tr>
					<tr>
						<td height="34" colspan="23"
							style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
							<c:if test="${page.totalPage > 0 }">
								<a href="#">共计${page.totalPage }页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp;
								<c:choose>
									<c:when test="${page.pageNo==1&&page.totalPage!=1}">
										<a href="adCustomerMgr_list.do?pageNo=${page.pageNo+1 }&contractBindingFlag=1">下一页</a>&nbsp;&nbsp;
										<a href="adCustomerMgr_list.do?pageNo=${page.totalPage}&contractBindingFlag=1">末页</a>&nbsp;&nbsp;
									</c:when>
									<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
										<a href="adCustomerMgr_list.do?pageNo=1&contractBindingFlag=1">首页</a>&nbsp;&nbsp;
										<a href="adCustomerMgr_list.do?pageNo=${page.pageNo-1 }&contractBindingFlag=1">上一页</a>&nbsp;&nbsp;
									</c:when>
									<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
										<a href="adCustomerMgr_list.do?pageNo=1&contractBindingFlag=1">首页</a>&nbsp;&nbsp;
										<a href="adCustomerMgr_list.do?pageNo=${page.pageNo-1 }&contractBindingFlag=1">上一页</a>&nbsp;&nbsp;
										<a href="adCustomerMgr_list.do?pageNo=${page.pageNo+1 }&contractBindingFlag=1">下一页</a>&nbsp;&nbsp;
										<a href="adCustomerMgr_list.do?pageNo=${page.totalPage}&contractBindingFlag=1">末页</a>&nbsp;&nbsp;
									</c:when>
								</c:choose>
							</c:if>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	</table>
</body>
</html>