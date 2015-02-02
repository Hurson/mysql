
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target=_self >
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<title>选择区域信息</title>
<script type="text/javascript">
    
	function init(){
		var areaCodes = parent.document.getElementById("contract_area_codes").value;
		
		var selecteds = areaCodes.split(",");
		
		var obj = document.getElementsByName("area_code");
		for(var i =0; i < obj.length; i ++){
			var val= obj[i].value.split("_")[0];
			for(var j =0; j < selecteds.length; j ++){
				if(val == selecteds[j]){
					obj[i].checked="checked";
					break;
				}
			}
		}
	}
   function save() {
		if (getCheckCount('area_code') <= 0) {
			alert("请给运营商指定区域！");
			return;
    	} else {
			var areaValue = getCheckValue('area_code');
			
			var areas = areaValue.split(",");
			var areaCodes = "";
			var areaNames = "";
			for ( var i = 0; i < areas.length; i++) {
				var tt = areas[i].split("_");
				if(i == areas.length -1){
					areaCodes += tt[0];
					areaNames += tt[1];
				}else{
					areaCodes += tt[0]+",";
					areaNames += tt[1]+",";
				}
			}
			parent.document.getElementById("contract_area_codes").value=areaCodes;
			parent.document.getElementById("contract_area_names").value=areaNames;
			parent.easyDialog.close();
		}
		
	}
	
</script>
</head>

<body class="mainBody" onload="init()">
<div class="search">
<div class="searchContent" >
<form action="<%=path %>/page/contract/selectAreas.do" method="post" id="queryForm">

	<c:set var="page" value="${areaPage}" scope="request"/>
    <input type="hidden" id="pageNo" name="areaPage.pageNo" value="${page.pageNo}"/>
    <input type="hidden" id="pageSize" name="areaPage.pageSize" value="${page.pageSize}"/>
    	
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div> 
<table cellspacing="1" class="searchList">
    <tr class="title">
    	<td height="28" class="dot">
			<input type="checkbox" name="chkAll" onclick="selectAll(this, 'area_code');" id="chkAll"/>
		</td>
        <td >区域编码</td>
	    <td >区域名称</td>
	    <td >区域类型</td>
	   
    </tr>
    
    <c:forEach items="${page.dataList}" var="area" varStatus="pl">
		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
			<td><input type="checkbox"  id="area_code" name="area_code" value="${area.areaCode}_${area.areaName}"
			
    		/></td>
			</td>
			<td><c:out value="${area.areaCode}" /></td>
			<td><c:out value="${area.areaName}" /></td>
			<td><c:out value="${area.locationType}"/></td>
			
			
		</tr>
	</c:forEach>
  <tr>
    <td colspan="6">
    	<input type="button" value="确定" class="btn" onclick="javascript:save();"/>&nbsp;&nbsp;
        <input type="button" value="取消" class="btn" onclick="parent.easyDialog.close();"/>
        <jsp:include page="../../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</form>
</div>
</div>
</body>
</html>
