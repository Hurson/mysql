<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/new/main.css" />
		<link href="<%=path%>/css/new/common/dhtmlx/tree/css/dhtmlxtree.css"
			rel="stylesheet" type="text/css" media="all" />
		<script src="<%=path%>/js/new/avit.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/jquery-1.9.0.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker-zh-CN.js">
</script>
		<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js">
</script>
		<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js">
</script>

		<script type="text/javascript"
			src="<%=path%>/js/easydialog/easydialog.min.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/contract/approvalListContract.js">
</script>
		<script>
$(function() {
	$("#bm").find("tr:even").addClass("treven"); //奇数行的样式
	$("#bm").find("tr:odd").addClass("trodd"); //偶数行的样式
});
function generateAccess(currentPage, customerName, positionName,
		effectiveStartDate, effectiveEndDate, positionTypeName, status) {

	var path = 'queryContractPage.do?currentPage=' + currentPage;
	if ((!$.isEmptyObject(customerName))) {
		$('#customerName').val(customerName);
	}
	if ((!$.isEmptyObject(positionName))) {
		$('#positionName').val(positionName);
	}
	if ((!$.isEmptyObject(effectiveStartDate))) {
		$('#effectiveStartDate').val(effectiveStartDate);
	}
	if ((!$.isEmptyObject(effectiveEndDate))) {
		$('#effectiveEndDate').val(effectiveEndDate);
	}
	if ((!$.isEmptyObject(positionTypeName))) {
		$('#positionTypeName').val(positionTypeName);
	}
	if ((!$.isEmptyObject(status))) {
		$('#status').val(status);
	}

	submitForm('#query', path);
}
</script>
		<style>
.easyDialog_wrapper {
	width: 800px;
	color: #444;
	border: 3px solid rgba(0, 0, 0, 0);
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 0 10px rgba(0, 0, 0, 0.4);
	-moz-box-shadow: 0 0 10px rgba(0, 0, 0, 0.4);
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.4);
	display: none;
	font-family: "Microsoft yahei", Arial;
}

.easyDialog_text {
	
}
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

	<body class="mainBody" onload="">
		
			<input id="projetPath" type="hidden" value="<%=path%>" />
			<div class="search">
				<div class="path">
					<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
					首页 >> 合同管理 >> 合同审核
				</div>
				<div class="searchContent">
					<table cellspacing="1" class="searchList">
						<form action="" method="post" id="query" name="query">
						<tr class="title">
							<td colspan="9">
								查询条件
							</td>
						</tr>
						<tr>
							<td>
								合同名称:	
								<input id="" name=""
										type="text" class="e_input"
										onfocus=""
										onblur="" maxlength="80" />
							 合同编号:
									 <input id="" name=""
										type="text" class="e_input"
										onfocus=""
										onblur="" maxlength="80" />
								<input type="button" value="查询" class="btn" id="queryButton" />
							</td>
						</tr>
						</form>
					</table>
					<div id="messageDiv"
						style="margin-top: 15px; color: red; font-size: 14px; font-weight: bold;"></div>
					<table cellspacing="1" class="searchList" id="bm">
						<tr class="title">
							<td height="26px" align="center">
								序号
							</td>
							<td>
								合同编号
							</td>
							<td>
								合同代码
							</td>
							<td>
								广告商ID
							</td>
							<td>
								合同名称
							</td>
							<td>
								合同金额
							</td>
							<td>
								素材存放路径
							</td>
							<td>
								状态
							</td>
							<td>
								操作人员
							</td>
							<td>
								合同开始时间
							</td>
							<td>
								合同结束时间
							</td>
							<td>
								操作
							</td>
						</tr>
						<c:choose>
							<c:when test="${!empty contractBackupList}">
								<c:forEach var="contract" items="${contractBackupList}"
									varStatus="status">
									<tr>
										<td align="center" height="26">
											${status.count}
										</td>
										<td>
											${contract.contractNumber}
										</td>
										<td>
											${contract.contractCode}
										</td>
										<td>
											${contract.customerId}
										</td>
										<td>
											${contract.contractName}
										</td>
										<td>
											${contract.financialInformation}
										</td>
										<td>
											${contract.metarialPath}
										</td>
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
										<td>
											${contract.operatorId}
										</td>
										<td>
											<fmt:formatDate value="${contract.effectiveStartDate}"
												type="both" />
										</td>
										<td>
											<fmt:formatDate value="${contract.effectiveEndDate}"
												type="both" />
										</td>
										<td>
											<form action="#" method="post" id="listApprovalContractOperationForm${status.count}" name="listApprovalContractOperationForm${status.count}">
											<input id="id" name="contract.id" type="hidden" value="${contract.id}"/>
											<input id="status" name="contract.status" type="hidden"  value="${contract.status}"/>
											<input name="approvalContract${status.count}" id="approvalContract${status.count}" type="button" class="btn" value="审核合同" title="审核合同" onClick="operation('approvalContract','#listApprovalContractOperationForm${status.count}','${contract.id}')" />
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
							<td height="34" colspan="23"
								style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
								<c:if test="${page.totalPage > 0 }">
									<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
								<c:choose>
										<c:when test="${page.currentPage==1&&page.totalPage!=1}">
										【<a href="#"
												onclick="generateAccess('${page.currentPage+1}','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">下一页</a>】
										【<a href="#"
												onclick="generateAccess('${page.currentPage+1}','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">末页</a>】
									</c:when>
										<c:when
											test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
										【<a href="#"
												onclick="generateAccess('1','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">首页</a>】 
										【<a href="#"
												onclick="generateAccess('${page.currentPage-1}','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">上一页</a>】
									</c:when>
										<c:when
											test="${page.currentPage>1&&page.currentPage<page.totalPage}">
										【<a href="#"
												onclick="generateAccess('1','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">首页</a>】 
										【<a href="#"
												onclick="generateAccess('${page.currentPage-1}','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">上一页</a>】
										【<a href="#"
												onclick="generateAccess('${page.currentPage+1}','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">下一页</a>】
										【<a href="#"
												onclick="generateAccess('${page.totalPage}','${object.customerName}','${object.positionName}','${object.effectiveStartDate}','${object.effectiveEndDate}','${object.positionTypeName}','${object.status}')">末页</a>】
									</c:when>
									</c:choose>
								</c:if>
							</td>
						</tr>
					</table>
				</div>
			</div>
	</body>
</html>