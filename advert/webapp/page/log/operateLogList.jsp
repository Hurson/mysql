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
    	if(validateSpecialCharacterAfter($("#userName").val())){
			alert("操作人不能包括特殊字符！");
			$("#userName").focus();
			return ;
		}
		if(validateSpecialCharacterAfter($("#moduleName").val())){
			alert("操作模块不能包括特殊字符！");
			$("#moduleName").focus();
			return ;
		}
		$("#queryForm").submit(); 
    }
</script>
</head>
<body class="mainBody">
<form action="queryOperateLogList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
<div class="search">
<div class="path">首页 >> 日志管理 >> 操作日志查询</div>
<div class="searchContent" >

<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>操作人：</span><input type="text" id="userName" name="operateLog.userName" value="${operateLog.userName}" />
      <span>操作模块：</span><input type="text" id="moduleName" name="operateLog.moduleName" value="${operateLog.moduleName}" />
      <span>开始时间：</span>
	   	<input id="beginTime" name="operateLog.beginTime" value="${operateLog.beginTime}" type="text" readonly="readonly" style="width:  120px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
	   	<img onclick="showDate('beginTime')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="left"/>
	  <span>结束时间：</span>
	    <input id="endTime" name="operateLog.endTime" value="${operateLog.endTime}" type="text" readonly="readonly" style="width:  120px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
	   	<img onclick="showDate('endTime')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
      &nbsp;&nbsp;<input type="button" value="查询" onclick="javascript:query();" class="btn"/>
     </td>
  </tr>
</table>

<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title" align="center">
        <td>操作模块</td>
        <td>动作名称</td>
        <td>操作人</td>
        <td>操作IP</td>
        <td>操作结果</td>
        <td>操作时间</td>
        <td>操作详情</td>
    </tr>
	 <s:iterator value="page.dataList" status="status" var="log">
	 	<tr <s:if test="#status.index%2==1">class="sec"</s:if> align="center">
			<td><s:property value="#log.moduleName" /></td>
			<td><s:property value="getText(#log.operateType)" /></td>
			<td><s:property value="#log.userName" /></td>
			<td><s:property value="#log.operateIP" /></td>
			<td>
				<s:if test="#log.operateResult==0">成功</s:if>
				<s:else>失败</s:else>
			</td>
			<td><s:property value="#log.operateTime" /></td>
			<td title="<s:property value="#log.operateInfo" />">
				<s:if test="#log.operateInfo.length()>20">
					<s:property value="#log.operateInfo.substring(0,20)" />...
				</s:if>
				<s:else>
					<s:property value="#log.operateInfo" />
				</s:else>
			</td>
		</tr>
	 </s:iterator>
  <tr>
    <td colspan="7">
        <jsp:include page="../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</div>
</div>
</form>
</body>
</html>