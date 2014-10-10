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
<title>广告系统</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    
    var statusValue = '${status}';
	if(!$.isEmptyObject(statusValue)){
		$("#status").val(statusValue);
	}
    
    $("#status").change(function(){
     	$("#queryPositionOccupyStatesTypeForm").submit();
     });
});
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
				<form action="queryPositionOccupyStates.do?method=queryPositionOccupyStates"  id="queryPositionOccupyStatesTypeForm" name="queryPositionOccupyStatesTypeForm" method="post">
					<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
						<tr>
							<td class="formTitle" colspan="99">
								<span>查询 广告位占用情况</span>
							</td>
						</tr>
						<tr>
							<td class="td_label">广告位状态:</td>
							<td class="td_input" colspan="3">
								<input id="positionId" name="positionId" type="hidden"  value="${positionId}"/>	
								<select id="status" name="status" class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'">
										<option value="-1">请选择</option>
										<option value="1">已销售</option>
										<option value="2">待销售</option>
										<option value="3">其他</option>
								</select>	
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
		<tr>
		<td style="padding:1px;height: 100%;">
			<div>  
				<table cellpadding="0" cellspacing="1" width="100%"
			class="taba_right_list" id="bm">
					<tr>
						<td colspan="23" style="padding-left: 8px; background: url(<%=path %>/images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>展示·广告位占用情况列表</span></td>
					</tr>
					<tr>
						<td height="26px" align="center">序号</td>
						<td>广告位ID</td>
						<td>广告位名称</td>
						<td>广告策略</td>
						<td>广告主</td>
						<td>投放位置</td>
						<td>有效期</td>
						<td>合同编号</td>
						<td>合同名称</td>
					</tr>
					<c:choose>
						<c:when test="${!empty positionOccupyStatesInfoList}">
							<c:forEach var="positionOccupyStatesInfo" items="${positionOccupyStatesInfoList}" varStatus="status">
								<tr>
									<td align="center" height="26">${status.count}</td>
									<td>${positionOccupyStatesInfo.positionId}</td>
									<td>${positionOccupyStatesInfo.positionName}</td>
									<td>${positionOccupyStatesInfo.ruleName}</td>
									<td>
										<c:choose>
											<c:when test="${empty positionOccupyStatesInfo.advertisersName}">
												未绑定广告商
											</c:when>
											<c:otherwise>
												${positionOccupyStatesInfo.advertisersName}
											</c:otherwise>
										</c:choose>
									</td>
									<td>${positionOccupyStatesInfo.areaName}</td>
									<td>${positionOccupyStatesInfo.validStartDate} ${positionOccupyStatesInfo.startTime}-${positionOccupyStatesInfo.validEndDate} ${positionOccupyStatesInfo.endTime}</td>
									<td>
										<c:choose>
											<c:when test="${empty positionOccupyStatesInfo.contractNumber}">
												未绑定合同
											</c:when>
											<c:otherwise>
												${positionOccupyStatesInfo.contractNumber}
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${empty positionOccupyStatesInfo.contractName}">
												未绑定合同
											</c:when>
											<c:otherwise>
												${positionOccupyStatesInfo.contractName}
											</c:otherwise>
										</c:choose>
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
						<td colspan="12"></td>
					</tr>
					<tr>
						<td height="34" colspan="23"
							style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
							<c:if test="${page.totalPage > 0 }">
								<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
								<c:choose>
									<c:when test="${page.currentPage==1&&page.totalPage!=1}">
										【<a href="queryPositionOccupyStates.do?method=queryPositionOccupyStates&positionId=${positionOccupyStatesInfo.positionId}&currentPage=${page.currentPage+1}&status=${status}">下一页</a>】
										【<a href="queryPositionOccupyStates.do?method=queryPositionOccupyStates&positionId=${positionOccupyStatesInfo.positionId}&currentPage=${page.totalPage}&status=${status}">末页</a>】
									</c:when>
									<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
										【<a href="queryPositionOccupyStates.do?method=queryPositionOccupyStates&positionId=${positionOccupyStatesInfo.positionId}&currentPage=1&status=${status}">首页</a>】 
										【<a href="queryPositionOccupyStates.do?method=queryPositionOccupyStates&positionId=${positionOccupyStatesInfo.positionId}&currentPage=${page.currentPage-1 }&status=${status}">上一页</a>】
									</c:when>
									<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
										【<a href="queryPositionOccupyStates.do?method=queryPositionOccupyStates&positionId=${positionOccupyStatesInfo.positionId}&currentPage=1&status=${status}">首页</a>】 
										【<a href="queryPositionOccupyStates.do?method=queryPositionOccupyStates&positionId=${positionOccupyStatesInfo.positionId}&currentPage=${page.currentPage-1}&status=${status}">上一页</a>】
										【<a href="queryPositionOccupyStates.do?method=queryPositionOccupyStates&positionId=${positionOccupyStatesInfo.positionId}&currentPage=${page.currentPage+1}&status=${status}">下一页</a>】
										【<a href="queryPositionOccupyStates.do?method=queryPositionOccupyStates&positionId=${positionOccupyStatesInfo.positionId}&currentPage=${page.totalPage}&status=${status}">末页</a>】
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