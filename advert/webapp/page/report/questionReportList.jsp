<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
    	if(validateSpecialCharacterAfter($("#advertisersName").val())){
			alert("广告商名称不能包括特殊字符！");
			$("#advertisersName").focus();
			return ;
		}
		if(validateSpecialCharacterAfter($("#resourceName").val())){
			alert("问卷名称不能包括特殊字符！");
			$("#resourceName").focus();
			return ;
		}
		$("#queryForm").submit(); 
    }
    
	function exportData() {
		var ret = window.confirm("您确定要导出本次查询的记录吗？");
		if (ret==1) {
			window.location = "<%=request.getContextPath()%>/page/report/exportQuestionReport.do"
										 + "?qreport.reportType=${report.reportType}"
			                             + "&qreport.advertisersName=${report.advertisersName}"
			                             + "&qreport.resourceName=${report.resourceName}"
			                             + "&qreport.pushDateStart=${report.pushDateStart}"
			                             + "&qreport.pushDateEnd=${report.pushDateEnd}";
		}      
    }
</script>
</head>
<body class="mainBody">
<form action="queryQuestionReportList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
<input type="hidden" id="report.reportType" name="report.reportType" value="${report.reportType}"/>
<div class="search">
<div class="path">首页 >> 报表管理 >> 日报表</div>
<div class="searchContent" >

<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>广告商名称：</span><input type="text" id="advertisersName" name="qreport.advertisersName" value="${qreport.advertisersName}" />
      <span>问卷名称：</span><input type="text" id="resourceName" name="qreport.resourceName" value="${qreport.resourceName}" />
      <span>开始时间：</span>
	   	<input id="beginTime" name="qreport.pushDateStart" value="${qreport.pushDateStart}" type="text" readonly="readonly" style="width:  120px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('beginTime')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="left"/>
	  <span>结束时间：</span>
	    <input id="endTime" name="qreport.pushDateEnd" value="${qreport.pushDateEnd}" type="text" readonly="readonly" style="width:  120px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('endTime')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
      &nbsp;&nbsp;<input type="button" value="查询" onclick="javascript:query();" class="btn"/>
     </td>
  </tr>
</table>

<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title" align="center">
         <td>广告商名称</td>       
        <td>素材名称</td>
         <td>投放开始时间</td>
         <td>投放结束时间</td>
        <td>投放次数</td>        
       
    </tr>
	 <s:iterator value="page.dataList" status="status" var="qreport">
	 	<tr <s:if test="#status.index%2==1">class="sec"</s:if> align="center">
	 	<td><s:property value="#qreport.advertisersName" /></td>
	 	<td><s:property value="#qreport.resourceName" /></td>	 	
		<td>
		<s:property value="#qreport.pushDateStart" />		
		</td>
		<td>
			<s:property value="#qreport.pushDateEnd" />
		</td>
		<td><s:property value="#qreport.receiveCount" /></td>
			
		</tr>
	 </s:iterator>
  <tr>
    <td colspan="5">
    	<input type="button" value="导出" class="btn" onclick="javascript:exportData();"/>
        <jsp:include page="../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</div>
</div>
</form>
</body>
</html>