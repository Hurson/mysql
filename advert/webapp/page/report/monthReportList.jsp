<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" defer="defer">
    function query() {
    	if(validateSpecialCharacterAfter($("#positionName").val())){
			alert("广告位名称不能包括特殊字符！");
			$("#positionName").focus();
			return ;
		}
		if(validateSpecialCharacterAfter($("#positionCode").val())){
			alert("广告位编码不能包括特殊字符！");
			$("#positionCode").focus();
			return ;
		}
		$("#queryForm").submit(); 
    }
    
	function exportData() {
		var ret = window.confirm("您确定要导出本次查询的记录吗？");
		if (ret==1) {
			window.location = "<%=request.getContextPath()%>/page/report/exportReport.do"
										 + "?report.reportType=${report.reportType}"
			                             + "&report.positionName=${report.positionName}"
			                             + "&report.positionCode=${report.positionCode}"
			                             + "&report.pushDateStart=${report.pushDateStart}"
			                             + "&report.pushDateEnd=${report.pushDateEnd}";
		}      
    }
</script>
</head>
<body class="mainBody">
<form action="queryMonthReportList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
<input type="hidden" id="report.reportType" name="report.reportType" value="${report.reportType}"/>
<div class="search">
<div class="path">首页 >> 报表管理 >> 月报表</div>
<div class="searchContent" >

<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>广告位名称：</span><input type="text" id="positionName" name="report.positionName" value="${report.positionName}" />
      <span>广告位编码：</span><input type="text" id="positionCode" name="report.positionCode" value="${report.positionCode}" />
      <span>开始时间：</span>
	   	<input id="beginTime" name="report.pushDateStart" value="${report.pushDateStart}" type="text" readonly="readonly" style="width:  120px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('beginTime')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="left"/>
	  <span>结束时间：</span>
	    <input id="endTime" name="report.pushDateEnd" value="${report.pushDateEnd}" type="text" readonly="readonly" style="width:  120px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('endTime')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
      &nbsp;&nbsp;<input type="button" value="查询" onclick="javascript:query();" class="btn"/>
     </td>
  </tr>
</table>

<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title" align="center">
        <td>广告位名称</td>
        <td>广告位编码</td>
        <td>投放时间</td>
        <td>素材名称</td>
        <!-- <td>投放次数</td>-->
        <td>曝光次数</td>
        <td>素材分类</td>
        <td>广告商名称</td>
    </tr>
	 <s:iterator value="page.dataList" status="status" var="report">
	 	<tr <s:if test="#status.index%2==1">class="sec"</s:if> align="center">
			<td><s:property value="#report.positionName" /></td>
			<td><s:property value="#report.positionCode" /></td>
			<td><s:property value="#report.pushDate" /></td>
			<td><s:property value="#report.resourceName" /></td>
		 <!--	<td><s:property value="#report.pushCount" /></td>-->
			<td><s:property value="#report.receiveCount" /></td>
			<td><s:property value="#report.categoryName" /></td>
			<td><s:property value="#report.advertisersName" /></td>
		</tr>
	 </s:iterator>
  <tr>
    <td colspan="7">
        	<c:if test="${page.dataList != null && fn:length(page.dataList) > 0}">
    	<input type="button" value="导出" class="btn" onclick="javascript:exportData();"/>
    	</c:if>
        <jsp:include page="../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</div>
</div>
</form>
</body>
</html>