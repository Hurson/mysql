<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"	href="<%=path %>/css/new/main.css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript">
	function search() {
		$("#queryForm").submit();
	}
	function upgrade(){
		var ips = getCheckValue("dataIds");
		if(ips==""){
			alert("请选择升级区域！");
			return false;
		}else{
			$("#butt").attr("disabled",true);
			
			window.location.href="<%=path%>/upgrade.do?upgradePath=${upgradePath}&newVersion=${newVersion}&ips="+ips;
		}
	}
</script>

<title>在线升级</title>
</head>
<body class="mainBody">

<form action="startUpgrade.do" id="queryForm" method ="POST">
	<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
    <input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
    <div class="path">首页 >> 系统管理 >> OCG在线升级</div>
	<div class="searchContent" >
	<table cellspacing="1" class="searchList">
    <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
		 <c:if test="${message != null}">
	            <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
	    </c:if>
	</div>     
	<table cellspacing="1" class="searchList" style="table-layout:fixed">
		<tr>
			<td align="right" colspan="3">最新版本：<span style="font-size:18px;font-weight:bold;">${newVersion }</span></td>
		</tr>
		<tr class="title"><td colspan="3">查询条件</td></tr>
		<tr>
			<td colspan="3">区域：<input type="text" name="areaOCG.areaName" value="${areaOCG.areaName}"/><input type="button" value="查询"/ onclick="search()"></td>
			
		</tr>
	</table>
	<table cellspacing="1" class="searchList">
        <tr class="title">
        <td height="10" class="dot"><input  type="checkbox" name="selectAllBox" disabled="disabled"/></td>
        <td width="30%">区域</td>
        <td width="30%">ip</td> 
        <td width="30%">当前版本号</td>
        </tr>
                    <c:if test="${page.dataList == null || fn:length(page.dataList) <= 0}">
                        <tr>
                          <td colspan="4">
                                <span style="color:red;">没有相关的数据记录！</span>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${page.dataList != null && fn:length(page.dataList) > 0}">
                        <c:forEach items="${page.dataList}" var="area" varStatus="status">
                            <tr <c:if test="${status.index%2 == 1}">class="sec"</c:if>>
                            	<td style = "width:10%;" >
                                    <input type="checkbox" value="<c:out value='${area.ip}'/>" name="dataIds" id="dataIds" <c:if test="${area.version >=newVersion}">disabled</c:if>/>
                                </td>
                               <td style = "width:30%;">
                                  <c:out value="${area.areaName }"/>
                                </td>
                                <td style = "width:30%;">
                                  <c:out value="${area.ip }"/>
                                </td>
                                <td style = "width:20%;" >
                                	<c:out value="${area.version }"/>
                                </td>
                               
                            </tr>
                        </c:forEach>
                    </c:if>
                    </table>
                    <div class="btnPage">
                        <div style="display:inline;float: left;">
                         <input type="button" id="butt" value="升级" onclick="upgrade()"/>
                       </div>
                        <jsp:include page="../common/page.jsp" flush="true"/>
                             
                    </div>

</form>
</body>
</html>