	<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tags/fmt.tld" prefix="fmt"%>
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

<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/new/main.css" />
		<link href="<%=path%>/css/new/common/dhtmlx/tree/css/dhtmlxtree.css"
			rel="stylesheet" type="text/css" media="all" />
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxcommon.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxtree.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/test.js"
			type="text/javascript">
</script>

<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>

<script language="javascript" type="text/javascript" src="<%=path %>/js/precise/listPrecise.js"></script>

<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    $("#queryPreciseButton").click(function(){
     	$("#queryPreciseForm").submit();
     });
});


</script>

</head>
<body class="mainBody" onload="">
		<input id="projetPath" type="hidden" value="<%=path%>" />
		<form id="queryPreciseForm" action="listPrecise.do?method=getAllPreciseList" method="post"
			name="adCustomerMgr_list">
			<div class="search">
				<div class="path">
					<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
					首页 >> 投放策略管理 >> 精准匹配管理
				</div>
				<div class="searchContent">
					<table cellspacing="1" class="searchList">
						<tr class="title">
							<td colspan="9">
								查询条件
							</td>
						</tr>
						<tr>
							<td>
								精准匹配名称：
								<input type="text" id="matchName" name="matchName"
									value="${matchName}" class="e_input"
									onfocus="this.className='e_inputFocus'"
									onblur="this.className='e_input'" />
								<input type="button" id="queryPreciseButton" name="queryPreciseButton"  class="btn" value="查看" class="btn" />
						</tr>
					</table>
					<div id="messageDiv"
						style="margin-top: 15px; color: red; font-size: 14px; font-weight: bold;"></div>
					<table cellspacing="1" class="searchList" id="bm">
						<tr class="title">
							<td height="28" class="dot">
								<input type="checkbox" name="checkbox" value="checkbox" />
							</td>
							<td align="center">
								序号
							</td>
							<td align="center">
								精准名称
							</td>
							<td align="center">
								类型
							</td>
							<td align="center">
								产品编码
							</td>
							<td align="center">
								回看频道
							</td>
							<td align="center">
								关键字
							</td>
							<td align="center">
								用户区域
							</td>
							<td align="center">
								用户行业
							</td>
							<td align="center">
								用户级别
							</td>
							<td align="center">
								TVN号段
							</td>
							
							<td width="center">
								优先级
							</td>
							<td width="center">
								策略
							</td>
							<!--  
							<td align="center">
								结点信息
							</td>
							-->
						</tr>
						<c:choose>
						<c:when test="${!empty precises}">
							<c:set var="index" value="0" />
							<c:forEach var="item" items="${precises}" varStatus="status">
								<c:set var="index"  value="${index+1}" />
								<tr>
									<td align="center" height="26"><input type="checkbox" name="checkBoxElement" value="${item.id}" /></td>
									<td align="center" height="26">${status.count}</td>
									<td>
										<a href="#" onclick="updatePrecise('${item.id}')">${item.matchName}</a>
									</td>
									<td>
										<c:if test="${item.type==1}">按产品</c:if>
										<c:if test="${item.type==2}">按影片元数据关键字</c:if>
										<c:if test="${item.type==3}">按受众</c:if>
										<c:if test="${item.type==4}">按回看频道</c:if>
									</td>
									<td>${item.productName}</td>
									<td>${item.btvChannelName}</td>
									<!-- <td><c:out value="${precise.assetSortName}" /></td> -->
									<td>${item.key}</td>
									<td>${item.userAreaName}</td>
									<td>${item.userIndustrysName}</td>
									<td>${item.userLevelsName}</td>
									<td>${item.tvnNumber}</td>
									<td>${item.priority}</td>
									<td>${item.ployName}</td>
									<!--  
									<td>${item.categoryName}</td>
									-->
						</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="23">
									暂无记录
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
						<tr>
				<td height="34" colspan="15"
					style="background: url(<%=path %>/images/bottom.jpg) repeat-x; text-align: left;">
					<input id="deletePreciseButton" name="deletePreciseButton" type="button" value="删除" class="btn"/>
								&nbsp;&nbsp;
					<input type="button"  name="Submit" value="添加" onclick="addPrecise()"  class="btn" />
					<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${page.pageNo==1&&page.totalPage!=1}">
							<a href="listPrecise.do?pageNo=${page.pageNo+1 }&matchName=${matchName}">下一页</a>&nbsp;&nbsp;
							<a href="listPrecise.do?pageNo=${page.totalPage}&matchName=${matchName}">末页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
							<a href="listPrecise.do?pageNo=1&matchName=${matchName}">首页</a>&nbsp;&nbsp;
							<a href="listPrecise.do?pageNo=${page.pageNo-1 }&matchName=${matchName}">上一页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
							<a href="listPrecise.do?pageNo=1&matchName=${matchName}">首页</a>&nbsp;&nbsp;
							<a href="listPrecise.do?pageNo=${page.pageNo-1 }&matchName=${matchName}">上一页</a>&nbsp;&nbsp;
							<a href="listPrecise.do?pageNo=${page.pageNo+1 }&matchName=${matchName}">下一页</a>&nbsp;&nbsp;
							<a href="listPrecise.do?pageNo=${page.totalPage}&matchName=${matchName}">末页</a>&nbsp;&nbsp;
						</c:when>
					</c:choose>
				</td>
			</tr>
					</table>
				</div>
			</div>
		</form>
	</body>
</html>