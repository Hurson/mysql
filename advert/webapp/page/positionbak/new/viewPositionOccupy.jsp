<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<input id="projetPath" type="hidden" value="<%=path%>" />
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/new/main.css" />
		<link rel="stylesheet" href="<%=path%>/css/easydialog/easydialog.css" type="text/css" />
		<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
		<script type="text/javascript" src="<%=path%>/js/easydialog/easydialog.min.js"></script>
		<link rel="stylesheet" href="<%=path%>/css/jquery/jquery.ui.all.css" type="text/css" />
		<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
		<script type="text/javascript" src="<%=path%>/js/util/tools.js"></script>
		<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
		<title>广告位展示</title>

		<style type="text/css">
a {
	text-decoration: underline;
}

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

<script>
	
	
	$(function(){   
    
	    //$("#bm").find("tr:even").addClass("sec");  //偶数行的样式
	    
	    $("#queryButton").click(function(){
	     	$("#queryForm").submit();
	     });
	    var positionOccupyStatesType = '${positionOccupyStatesInfo.positionOccupyStatesType}';
		if(!$.isEmptyObject(positionOccupyStatesType)){
			$("#positionOccupyStatesType").val(parseInt(positionOccupyStatesType));
		}
	
		var effectiveEndDate = '${positionOccupyStatesInfo.effectiveEndDate}';
		if(!$.isEmptyObject(effectiveEndDate)){
			$("#effectiveEndDate").val(effectiveEndDate);
		}
	
		var effectiveStartDate = '${positionOccupyStatesInfo.effectiveStartDate}';
		if(!$.isEmptyObject(effectiveStartDate)){
			$("#effectiveStartDate").val(effectiveStartDate);
		}
  
});

function generateAccess(currentPage,effectiveStartDate,effectiveEndDate,positionOccupyStatesType){
	
	var path = 'queryPositionOccupyStates.do?method=queryPositionOccupyStates&positionId=${positionId}'+currentPage;
	
	if((!$.isEmptyObject(effectiveStartDate))&&(''!=effectiveStartDate)){
		$('#effectiveStartDate').val(effectiveStartDate);
	}
	
	if((!$.isEmptyObject(effectiveEndDate))&&(''!=effectiveEndDate)){
		$('#effectiveEndDate').val(effectiveEndDate);
	}
	
	if((!$.isEmptyObject(positionOccupyStatesType))&&(''!=positionOccupyStatesType)){
		$('#positionOccupyStatesType').val(positionOccupyStatesType);
	}
	
	submitForm('#queryForm',path);
	
}

</script>

	</head>

	<body class="mainBody" onload="">
		<input id="projetPath" type="hidden" value="<%=path%>" />
		<div class="search">
			<div class="path">
				<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
				首页 >> 广告位管理 >> 广告位维护
			</div>
			<div class="searchContent">
				<form action="queryPositionOccupyStates.do?method=queryPositionOccupyStates&positionId=${positionId}"  id="queryForm" name="queryForm" method="post">
					<table cellspacing="1" class="searchList">
						<tr class="title">
							<td colspan="2">
								查询条件
							</td>
						</tr>
						<tr>
							<td class="searchCriteria">
								<span>开始时间：</span>&nbsp;&nbsp;&nbsp;
								<input id="effectiveStartDate" name="positionOccupyStatesInfo.effectiveStartDate"
									value="" class="input_style2" type="text" style="width: 125px;"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
								<img onclick="showDate('effDate')"
									src="<%=path%>/js/new/My97DatePicker/skin/datePicker.gif" width="16"
									height="22" align="absmiddle" />
								<span>结束时间：</span>&nbsp;&nbsp;&nbsp;
								<input id="effectiveEndDate" name="positionOccupyStatesInfo.effectiveEndDate"
									value="" class="input_style2" type="text" style="width: 125px;"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
								<img onclick="showDate('effDate')"
									src="<%=path%>/js/new/My97DatePicker/skin/datePicker.gif" width="16"
									height="22" align="absmiddle" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<span>广告位状态：</span>
								<select id="positionOccupyStatesType" name="positionOccupyStatesInfo.positionOccupyStatesType">
									<option value="1">
										已销售
									</option>
									<option value="2">
										待销售
									</option>
								</select>
								<input id="queryButton" name="queryButton" type="button" value="查询" onclick="" class="btn" />
							</td>
						</tr>
					</table>
				</form>
				<div id="messageDiv"
					style="margin-top: 15px; color: red; font-size: 14px; font-weight: bold;"></div>
				<table cellspacing="1" class="searchList" id="bm">
					<tr class="title">
						<td>
							序号
						</td>
						<td>
							广告位ID
						</td>
						<td>
							广告位名称
						</td>
						<td>
							广告策略
						</td>
						<td>
							广告主
						</td>
						<td>
							投放区域
						</td>
						<td>
							有效期
						</td>
						<td>
							合同编号
						</td>
						<td>
							合同名称
						</td>
					</tr>
					<c:choose>
						<c:when test="${!empty objectList}">
							<c:forEach var="object" items="${objectList}" varStatus="status">
								<tr>
									<td align="center" height="26">
										${status.count}
									</td>
									<td>${object.positionId}</td>
									<td>${object.positionName}</td>
									<td>${object.ruleName}</td>
									<td>
										<c:choose>
											<c:when test="${positionOccupyStatesInfo.positionOccupyStatesType==1}">
												${object.advertisersName}
											</c:when>
											<c:otherwise>
												无
											</c:otherwise>
										</c:choose>
										
									</td>
									<td>${object.areaName}</td>
									<td>
										<c:choose>
											<c:when test="${positionOccupyStatesInfo.positionOccupyStatesType==1}">
												${object.effectiveStartDate}~${object.effectiveEndDate}
											</c:when>
											<c:otherwise>
												无
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${positionOccupyStatesInfo.positionOccupyStatesType==1}">
												${object.contractNumber}
											</c:when>
											<c:otherwise>
												无
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${positionOccupyStatesInfo.positionOccupyStatesType==1}">
												${object.contractName}
											</c:when>
											<c:otherwise>
												无
											</c:otherwise>
										</c:choose>
										
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="9">
									暂无记录
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td height="34" colspan="9"
							style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
							<c:if test="${page.totalPage > 0 }">
								<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
								<c:choose>
									<c:when test="${page.currentPage==1&&page.totalPage!=1}">
										【<a href="#" onclick="generateAccess('${page.currentPage+1}','${positionOccupyStatesInfo.effectiveStartDate}','${positionOccupyStatesInfo.effectiveEndDate}','${isHd}','${deliveryMode}')">下一页</a>】
										【<a href="#" onclick="generateAccess('${page.totalPage}','${positionOccupyStatesInfo.effectiveStartDate}','${positionOccupyStatesInfo.effectiveEndDate}','${isHd}','${deliveryMode}')">末页</a>】
									</c:when>
									<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
										【<a href="#" onclick="generateAccess('1','${positionOccupyStatesInfo.effectiveStartDate}','${positionOccupyStatesInfo.effectiveEndDate}','${isHd}','${deliveryMode}')">首页</a>】 
										【<a href="#" onclick="generateAccess('${page.currentPage-1 }','${positionOccupyStatesInfo.effectiveStartDate}','${positionOccupyStatesInfo.effectiveEndDate}','${isHd}','${deliveryMode}')">上一页</a>】
									</c:when>
									<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
										【<a href="#" onclick="generateAccess('1','${positionOccupyStatesInfo.effectiveStartDate}','${positionOccupyStatesInfo.effectiveEndDate}','${positionOccupyStatesInfo.positionOccupyStatesType}')">首页</a>】 
										【<a href="#" onclick="generateAccess('${page.currentPage-1}','${positionOccupyStatesInfo.effectiveStartDate}','${positionOccupyStatesInfo.effectiveEndDate}','${positionOccupyStatesInfo.positionOccupyStatesType}')">上一页</a>】
										【<a href="#" onclick="generateAccess('${page.currentPage+1}','${positionOccupyStatesInfo.effectiveStartDate}','${positionOccupyStatesInfo.effectiveEndDate}','${positionOccupyStatesInfo.positionOccupyStatesType}')">下一页</a>】
										【<a href="#"  onclick="generateAccess('${page.totalPage}','${positionOccupyStatesInfo.effectiveStartDate}','${positionOccupyStatesInfo.effectiveEndDate}','${positionOccupyStatesInfo.positionOccupyStatesType}')">末页</a>】
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