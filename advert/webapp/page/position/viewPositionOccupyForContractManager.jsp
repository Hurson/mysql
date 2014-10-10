<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<title>广告位包占用情况查询</title>
</head>
<script type="text/javascript">
    function query(){
    	var start = $("#startDateStr").val();
    	var end = $("#endDateStr").val();
    	if(!isEmpty(start) && !isEmpty(end)){
	    	var startTime=Date.parse(start.replace(/-/g, "/"));
	        var endTime=Date.parse(end.replace(/-/g, "/"));
	        if(endTime<=startTime){
	            alert("结束日期不能小于开始日期！");
	            return;
	        }
    	}
		$("#queryForm").submit();
    }

</script>
<body class="mainBody">
<form action="viewPositionOccupyForContractManager.do" method="post" id="queryForm">
	<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
	<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<input type="hidden" id="id" name="contractADBackup.positionId" value="${contractADBackup.positionId}"/>
	<div class="search">
		<div class="path">首页 >> 广告位包查询 >> 占用情况</div>
		<div class="searchContent">
		<table cellspacing="1" class="searchList">
		  <tr class="title">
		    <td>查询条件</td>
		  </tr>
		  <tr>
		    <td class="searchCriteria">
		      <span style="width: 60px;text-align:right;">开始日期：</span>
			   	<input id="startDateStr" name="contractADBackup.startDateStr" value="${contractADBackup.startDateStr}" type="text" readonly="readonly" style="width:  100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
			   	<img onclick="showDate('startDateStr')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="left"/>
			  <span style="width: 60px;text-align:right">结束日期：</span>
			    <input id="endDateStr" name="contractADBackup.endDateStr" value="${contractADBackup.endDateStr}" type="text" readonly="readonly" style="width:  100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
			   	<img onclick="showDate('endDateStr')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
		      &nbsp;&nbsp;<input type="button" value="查询" onclick="javascript:query();" class="btn"/>
		     </td>
		  </tr>
		</table>
		<table cellspacing="1" class="searchList">
				<tr class="title">
  			 		<td colspan="4">${pPackage.positionPackageName}</td>
				</tr>
		</table>
		<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
		<table cellspacing="1" class="searchList">
    		<tr class="title">
    			<td>广告商名称</td>
    			<td>合同名称</td>
    			<td>合同编码</td>
    			<td>有效期</td>
    		</tr>
    		<c:forEach items="${page.dataList}" var="view" varStatus="pl">
    		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
    			<td><c:out value="${view.advertisersName}" /></td>
    			<td><c:out value="${view.contractName}" /></td>
    			<td><c:out value="${view.contractCode}" /></td>
    			<td><c:out value="${view.startTime}" />~<c:out value="${view.endTime}" /></td>
    		</tr>
    		</c:forEach>
			<tr>
			    <td colspan="4">
			        <jsp:include page="../common/page.jsp" flush="true" />
			    </td>
			</tr>
		</table>		
		</div>
	</div>
</form>
</body>
</html>