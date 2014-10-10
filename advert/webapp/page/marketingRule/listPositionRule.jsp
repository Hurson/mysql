<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/marketingRule/addMarketingRule.js"></script>

<title>营销规则列表</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});

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

</head>
<body>
<div class="mainBody">
	<div class="search">
		<div class="path">首页 >> 营销规则管理 >> 查看已有规则</div>
		<div class="searchContent" >
			<table cellspacing="1" class="searchList" id="bm">
    		<tr class="title">    		
    			<td>序号</td>
    			<td>规则名称</td>
				<td>开始时间</td>
				<td>结束时间</td>
				<td>广告位</td>
				<td>地区</td>
				<td>频道</td>
				<td>创建时间</td>
				<td>状态</td>
    		</tr>
    		<s:if test="ruleList.size==0">
			<tr>
				<td bgcolor="#FFFFFF" align="center" colspan="9"
					style="text-align: center">无记录</td>
			</tr>
			</s:if>
			<s:else>
			    <c:set var="index" value="0"/>
				<c:forEach items="${ruleList}" var="rule">
				<tr>					
					<c:set var="index" value="${index+1 }" />
					<td align="center" height="26"><c:out value="${index }" /></td>
					<td align="center"><c:out value="${rule.ruleName}" /></td>
					<td align="center"><c:out value="${rule.startTime}" /></td>
					<td align="center"><c:out value="${rule.endTime}" /></td>
					<td align="center"><c:out value="${rule.positionName}" /></td>
					<td align="center"><c:out value="${rule.areaName}" /></td>
					<td align="center"><c:out value="${rule.channelName}" /></td>
					<td align="center"><c:out value="${rule.createTime}" /></td>
					<td align="center"><c:out value="${rule.state}" /></td>
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
					</tr>
				</c:forEach>
			</c:if>

			
			
		</s:else>
    		

			
			<tr>
				<td height="34" colspan="11" style="background: url(images/bottom.jpg) repeat-x; text-align: left;">


					
					
					<div class="page">
					<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${page.pageNo==1&&page.totalPage!=1}">
							<a href="queryRule.do?pageNo=${page.pageNo+1 }&positionId=${positionId}">下一页</a>&nbsp;&nbsp;
							<a href="queryRule.do?pageNo=${page.totalPage}&positionId=${positionId}">末页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
							<a href="queryRule.do?pageNo=1&positionId=${positionId}">首页</a>&nbsp;&nbsp;
							<a href="queryRule.do?pageNo=${page.pageNo-1 }&positionId=${positionId}">上一页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
							<a href="queryRule.do?pageNo=1&positionId=${positionId}">首页</a>&nbsp;&nbsp;
							<a href="queryRule.do?pageNo=${page.pageNo-1 }&positionId=${positionId}">上一页</a>&nbsp;&nbsp;
							<a href="queryRule.do?pageNo=${page.pageNo+1 }&positionId=${positionId}">下一页</a>&nbsp;&nbsp;
							<a href="queryRule.do?pageNo=${page.totalPage}&positionId=${positionId}">末页</a>&nbsp;&nbsp;
						</c:when>
					</c:choose>
					</div>
				</td>
			</tr>
    		
			</table>
		</div>
	</div>

</div>
<div id="columnDiv" class="showDiv" style="display: none;">
<table cellpadding="0" cellspacing="0"
	style="margin-top: 0; width: 777px; height: 336px; border: 1px solid #cccccc; font-size: 12px;">
	<tr class="list_title">
		<td style="border: 0;">绑定的栏目</td>
		<td style="border: 0;" align="right"><a href="#" onclick="closeSelectDiv('columnDiv');">关闭</a></td>
	</tr>
	<tr>
		<td colspan='2'>
		<div id="columnInfo"></div>
		</td>
	</tr>
	<tr>
		<td colspan="2" class="td_bottom">
		<div class="buttons">
		<a href="#" onclick="closeSelectDiv('columnDiv');">返回</a></div>
		</td>
	</tr>
</table>
</div>
<div id="bg" class="bg" style="display: none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>
</body>
</html>