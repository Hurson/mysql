<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css">

<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker-zh-CN.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>

<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/contract/approvalListContract.js"></script>



<title>审核合同列表页面</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});
function generateAccess(currentPage,customerName,positionName,effectiveStartDate,effectiveEndDate,positionTypeName,status){
	
	var path = 'queryContractPage.do?currentPage='+currentPage;
	if((!$.isEmptyObject(customerName))){
		$('#customerName').val(customerName);
	}
	if((!$.isEmptyObject(positionName))){
		$('#positionName').val(positionName);
	}
	if((!$.isEmptyObject(effectiveStartDate))){
		$('#effectiveStartDate').val(effectiveStartDate);
	}
	if((!$.isEmptyObject(effectiveEndDate))){
		$('#effectiveEndDate').val(effectiveEndDate);
	}
	if((!$.isEmptyObject(positionTypeName))){
		$('#positionTypeName').val(positionTypeName);
	}
	if((!$.isEmptyObject(status))){
		$('#status').val(status);
	}
	
	submitForm('#query',path);
}
</script>
<style>
	.easyDialog_wrapper{ width:800px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}
</style>
<input id="projetPath" type="hidden" value="<%=path%>"/>
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
		<td style="padding:2px;">
			<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
				<form action="" method="post" id="query" name="query">
					<tr>
						<td class="formTitle" colspan="99"><span>·合同查询</span></td>
					</tr>
					<tr>
						<td class="td_label">广告商名称：</td>
						<td class="td_input">
							<input id="customerName" name="object.customerName" class="e_input" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>
						</td>
						<td class="td_label">广告位名称：</td>
						<td class="td_input">
							<input id="positionName" name="object.positionName" class="e_input" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>
						</td>
					</tr>
					<tr>
						<td class="td_label">开始时间：</td>
						<td class="td_input">
							<input id="effectiveStartDate" name="object.effectiveStartDate" class="e_input" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>
						</td>
						<td class="td_label">结束时间：</td>
						<td class="td_input">
							<input id="effectiveEndDate" name="object.effectiveEndDate" class="e_input" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>
						</td>
					</tr>
					<tr>
						<td class="td_label">广告位类型：</td>
						<td class="td_input">
							<input id="positionTypeId" name="object.positionTypeId" class="e_input" type="hidden" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80" value=""/>
							<input id="positionTypeName" name="object.positionTypeName" class="e_input" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80" value=""/>
						</td>
						<td class="td_label">状态：</td>
						<td class="td_input" colspan="3">
							<select id="status" name="object.status" class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'">
									<option value="0">待审核</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="formBottom" colspan="99">
							<input name="searchContractButton" id="searchContractButton" type="button" title="查看" class="b_search"/>
						</td>
					</tr>
				</form>
			</table>
			
		</td>
	</tr>
	<tr>
		<td style="padding:2px;">
			<div>  
				<table cellpadding="0" cellspacing="1" width="100%"
			class="taba_right_list" id="bm">
					<tr>
						<td colspan="23" style="padding-left: 8px; background: url(<%=path %>/images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>·广告位列表</span></td>
					</tr>
					<tr>
						<td height="26px" align="center">序号</td>
						<td>合同编号</td>
						<td>合同代码</td>
						<td>广告商ID</td>
						<td>合同名称</td>
						<td>合同金额</td>
						<td>素材存放路径</td>
						<td>状态</td>
						<td>操作人员</td>
						<td>合同开始时间</td>
						<td>合同结束时间</td>
						<td>操作</td>
					</tr>
					<c:choose>
						<c:when test="${!empty contractBackupList}">
							<c:forEach var="contract" items="${contractBackupList}" varStatus="status">
								<tr>
									<td align="center" height="26">${status.count}</td>
									<td>${contract.contractNumber}</td>
									<td>${contract.contractCode}</td>
									<td>${contract.customerId}</td>
									<td>${contract.contractName}</td>
									<td>${contract.financialInformation}</td>
									<td>${contract.metarialPath}</td>
									<td>
										<c:choose>
											<c:when test="${contract.status==0}">
												待审核
											</c:when>
											<c:when test="${contract.status==1}">
												审核
											</c:when>
											<c:otherwise>
												下线状态
											</c:otherwise>
										</c:choose>
									</td>
									<td>${contract.operatorId}</td>
									<td><fmt:formatDate value="${contract.effectiveStartDate}" type="both"/></td>
									<td><fmt:formatDate value="${contract.effectiveEndDate}" type="both"/></td>
									<td>
										<form action="#" method="post" id="listApprovalContractOperationForm${status.count}" name="listApprovalContractOperationForm${status.count}">
											<input id="id" name="contract.id" type="hidden" value="${contract.id}"/>
											<input id="status" name="contract.status" type="hidden"  value="${contract.status}"/>
											<input name="approvalContract${status.count}" id="approvalContract${status.count}" type="button" class="button_start" value="" title="审核合同" onClick="operation('approvalContract','#listApprovalContractOperationForm${status.count}','${contract.id}')" />
										</form>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="12">
									暂无记录
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td colspan="12">
						
						</td>
					</tr>
					<tr>
						<td height="34" colspan="23"
							style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
							<c:if test="${page.totalPage > 0 }">
								<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
								<c:choose>
									<c:when test="${page.currentPage==1&&page.totalPage!=1}">
										【<a href="#" onclick="generateAccess('${page.currentPage+1}','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">下一页</a>】
										【<a href="#" onclick="generateAccess('${page.currentPage+1}','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">末页</a>】
									</c:when>
									<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
										【<a href="#" onclick="generateAccess('1','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">首页</a>】 
										【<a href="#" onclick="generateAccess('${page.currentPage-1}','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">上一页</a>】
									</c:when>
									<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
										【<a href="#" onclick="generateAccess('1','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">首页</a>】 
										【<a href="#" onclick="generateAccess('${page.currentPage-1}','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">上一页</a>】
										【<a href="#" onclick="generateAccess('${page.currentPage+1}','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">下一页</a>】
										【<a href="#" onclick="generateAccess('${page.totalPage}','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">末页</a>】
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