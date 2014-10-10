<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<title>广告位运营商指定</title>
<script type="text/javascript">
   function save() {
		if (getCheckCount('packageid') <= 0) {
			alert("请给运营商指定广告位！");
			return;
    	} else {
			var positionValue = getCheckValue('packageid');
			window.returnValue=positionValue;
			window.close();
		}
		
	}
	
	function cancle() {
		window.returnValue="";
        window.close();
    }
</script>
</head>

<body class="mainBody">
<div class="search">
<div class="path">首页 >>用户管理 >> 广告位运营商指定</div>
<div class="searchContent" >
<form action="getUserAdvertPackage.do" method="post" id="queryForm">

<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div> 
<table cellspacing="1" class="searchList">
    <tr class="title">
    	<td height="28" class="dot">
			<c:if test="${roleType==2}">
				<input type="checkbox" name="chkAll" onclick="selectAll(this, 'customer');" id="chkAll"/>
			</c:if>
		</td>
        <td >广告位编码</td>
	    <td >广告位名称</td>
	    <td >广告位类型</td>
	    <td >子广告位个数</td>
	    <td >投放策略</td>
    </tr>
    <c:forEach items="${page.dataList}" var="package" varStatus="pl">
		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
			<td><input type="checkbox"  id="packageid" name="packageid" value="${package.id}_${package.positionPackageName}"
			<c:forEach items="${positionsIdList}" var="pid">
						<c:if test="${pid == package.id}">checked="checked" </c:if>
					</c:forEach>
    			 /></td>
			</td>
			<td><c:out value="${package.positionPackageCode}" /></td>
			<td><c:out value="${package.positionPackageName}" /></td>
			<td>
				<c:choose>
					<c:when test="${package.positionPackageType==0}">双向实时广告</c:when>
					<c:when test="${package.positionPackageType==1}">双向实时请求广告</c:when>
					<c:when test="${package.positionPackageType==2}">单向实时广告</c:when>
					<c:when test="${package.positionPackageType==3}">单向非实时广告</c:when>
				</c:choose>
			</td>
			<td><c:out value="${package.positionCount}" /></td>
			<td><c:out value="${package.ployDescription}" /></td>
			
		</tr>
	</c:forEach>
  <tr>
    <td colspan="6">
    	<input type="button" value="确定" class="btn" onclick="javascript:save();"/>&nbsp;&nbsp;
        <input type="button" value="取消" class="btn" onclick="javascript :window.close();"/>
        
    </td>
  </tr>
</table>
</form>
</div>
</div>
</body>
</html>