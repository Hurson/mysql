<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
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
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<title>选择广告位</title>
</head>
<script type="text/javascript">
	function query(){
		if(validateSpecialCharacterAfter($("#positionName").val())){
			alert("广告位名称不能包括特殊字符！");
			$("#positionName").focus();
			return ;
		}
		$("#queryForm").submit();
	}

	function save() {
		if (getCheckCount('positionId') <= 0) {
			alert("请选择广告位！");
			return;
    	} else {
			var contractValue = getCheckValue('positionId');
			window.returnValue=contractValue;
			window.close();
		}
		
	}
	
	function cancle() {
		window.returnValue="";
        window.close();
    }
    
</script>
<body class="mainBody">
<form action="showPositionList.do" method="post" id="queryForm">
	<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
	<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<input type="hidden" name="advertPosition.contractId" value="${advertPosition.contractId}"/>
	<div class="searchContent">
        <table cellspacing="1" class="searchList">
            <tr class="title">
                <td>查询条件</td>
            </tr>
            <tr>
                <td class="searchCriteria">
                	<span>广告位名称：</span><input type="text" id="positionName" name="advertPosition.positionName" value="${advertPosition.positionName}" style="width: 14%"/>
                    <span>投放方式：</span>
			        <select name="advertPosition.deliveryMode">
			            <option value="">请选择...</option>
						<option value="0" <c:if test="${advertPosition.deliveryMode==0}">selected</c:if>>投放式</option>
						<option value="1" <c:if test="${advertPosition.deliveryMode==1}">selected</c:if>>请求式</option>
			        </select>
                    <input type="button" value="查询" onclick="javascript:query();" class="btn"/>
   				</td>
            </tr>
        </table>
        <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
              <td class="dot"></td>
				<td>广告位名称</td>
				<td>广告位编码</td>
				<td>高清/标清</td>
				<td>是否叠加</td>
				<td>是否轮询</td>
				<td>轮询个数</td>
				<td>投放方式</td>
				<td>投放开始日期</td>
				<td>投放结束日期</td>
			</tr>
            <c:forEach items="${page.dataList}" var="position" varStatus="pl">
				<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>
					onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
					<td align="center">
						<input type="radio"	name="positionId" value="${position.id}_${position.positionName}_${position.validStart}_${position.validEnd}_${position.deliveryMode}_${position.backgroundPath}_${position.coordinate}_${position.widthHeight}" 
						<c:if test="${order.positionId == position.id}">checked</c:if> />
					</td>
					<td><c:out value="${position.positionName}" /></td>
					<td><c:out value="${position.positionCode }" /></td>
					<td>
						<c:choose>
							<c:when test="${position.isHD==1}">是</c:when>
							<c:when test="${position.isHD==0}">否</c:when>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${position.isAdd==1}">是</c:when>
							<c:when test="${position.isAdd==0}">否</c:when>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${position.isLoop==1}">是</c:when>
							<c:when test="${position.isLoop==0}">否</c:when>
						</c:choose>
					</td>
					<td><c:out value="${position.loopCount}" /></td>
					<td>
						<c:choose>
							<c:when test="${position.deliveryMode==1}">请求式</c:when>
							<c:when test="${position.deliveryMode==0}">投放式</c:when>
						</c:choose>
					</td>
					<td><fmt:formatDate value="${position.validStart}" dateStyle="medium"/></td>
					<td><fmt:formatDate value="${position.validEnd}" dateStyle="medium"/></td>
				</tr>
			</c:forEach>
            <tr>
                <td colspan="10">
                	<input type="button" value="确定" class="btn" onclick="save();"/>&nbsp;&nbsp;
					<input type="button" value="取消" class="btn" onclick="cancle();"/>&nbsp;&nbsp;
					<jsp:include page="../common/page.jsp" flush="true" />
                </td>
            </tr>
        </table>
    </div>
</form>
</body>
</html>