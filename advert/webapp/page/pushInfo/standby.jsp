<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/standby.css" />
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>

<script type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>
<script src="<%=path%>/js/new/avit.js"></script>
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<title>待机指令</title>

<script type="text/javascript">
$(document).ready(function(){
	
		var areaValue='${maintainList.areaCodes}';
		if(areaValue.length!=0){
			$("input[name='maintain.areaCodes']").each(function(){
				if(areaValue.indexOf($(this).val())!=-1){
					 $(this).prop("checked", true);   
				}else {
					$(this).prop("checked", false); 
				}
		});}
});
function validaSubmit(){
	var flag = true;
	var activeHour=$("input[name='maintain.activeHour']").val();
	var sendTime=$("#effectiveStartDate2").val();
	var areaSize=$("input[name='maintain.areaCodes']:checked").size();
	if(activeHour.length==0){
		alert("持续开机时长不能为空!");
		flag = false;
	}else if(!validateNumber(activeHour)){
		alert("持续开机时长只能是整数!");
		flag = false;
	}else if(sendTime.length==0){
		alert("发送时间不能为空!");
		flag=false;
	}else if(areaSize == 0){
		alert("发送区域不能为空！");
		flag=false;
	}
		
	return flag;
}
//数字验证
function validateNumber(password){
	var pattern = /^\d+$/;
	var numberChkFlag = false;
	if(pattern.test(password)){
	numberChkFlag  = true;
	}
	return numberChkFlag;
}

function dosave() {
	if (!validaSubmit()) {
		return;
	}
	
	$("#saveMainForm").submit();
}
</script>
</head>
<body class="mainBody">
<form action="<%=path %>/page/systemMaintain/saveMaintain.do" method="post" id="saveMainForm">
<div class="path">首页 >>UNT业务管理 >>待机指令管理</div>
<div style="border:1px solid #bfd8e0;" id="djId">
<table cellspacing="1" class="searchList" id="djTab">
<c:if test="${message != null}">
	<tr>
		<td colspan="4">
		 <span style="color:red;"><s:text name='%{message}'/></span>
		</td>
	</tr>
</c:if>
	<tr class="title">
		<td height="28" class="dot" width="5%" style="text-align:center;">持续开机时长:</td>
		<td width="10%" style="text-align:center;">
		<c:choose>
		<c:when test="${maintainList==null}">
		<input type="text" name="maintain.activeHour" value=""/>小时
		</c:when>
		<c:otherwise>
		<input type="text" name="maintain.activeHour" value="${maintainList.activeHour}"/>小时
		</c:otherwise>
		</c:choose>
		</td>
		<td width="15%" align="center" style="text-align:center;">发送时间:</td>
		<td width="23%" align="center" style="text-align:center;">
		<c:choose>
		<c:when test="${maintainList==null}">
		<input readonly='readonly' id='effectiveStartDate2' class='input_style2' type='text' style='width:150px;' 
		onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="maintain.sendTime" value=''/>
		<img onclick="showDate('effectiveStartDate2')"src='<%=path %>/js/new/My97DatePicker/skin/datePicker.gif'width='16' height='22' align='absmiddle'/>
		</c:when>
		<c:otherwise>
		<input readonly='readonly' id='effectiveStartDate2' class='input_style2' type='text' style='width:150px;' 
		onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="maintain.sendTime" value='<fmt:formatDate type="both" value="${maintainList.sendTime}"/>'/>
		<img onclick="showDate('effectiveStartDate2')"src='<%=path %>/js/new/My97DatePicker/skin/datePicker.gif'width='16' height='22' align='absmiddle'/>
		</c:otherwise>
		</c:choose>
		</td>   
	</tr>
	<tr class="title">
		<td style="text-align:center;">发送持续时间:</td>
		<td style="text-align:left;">
		<c:choose>
		<c:when test="${maintainList==null}">
		<input type="text"  name="maintain.duration" value='60'/>秒
		</c:when>
		<c:otherwise>
		<input type="text"  name="maintain.duration" value='${maintainList.duration}'/>秒
		</c:otherwise>
		</c:choose>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	
	<tr class="title">
	    <td style="text-align:center;"><span>区域:(全选<input type="checkbox" onclick="selectAll(this,'maintain.areaCodes')"/>)</span></td>
	    <td colspan="3" style="text-align:left;">
	    <c:forEach items="${areaList}" var="area" varStatus="status">
	    	<input type="checkbox" name="maintain.areaCodes" value="${area.areaCode }"/>${area.areaName }
	        <c:if test="${status.count%8 == 0}"></td></tr><tr class="title"><td/><td colspan="3" style="text-align:left;"></c:if>
	    </c:forEach>
	    
	    </td>
	    
	</tr>
	<tr>
		<td colspan="4">
			<center>
			<input type="button" value="提交" class="btn" id="saveBtn" onclick="dosave();"/>&nbsp;&nbsp;
        	<input type="button" value="取消" class="btn" id="cancelBtn"/>
        	</center>
		</td>
	</tr>
</table>
<input type="hidden" name="maintain.actionCode" value="2"/>
<c:if test="${maintainList!=null}">
<input type="hidden" name="${maintainList.id}"/>
</c:if>
</form>

</body>
</html>