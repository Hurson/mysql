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
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<title>订单审核日志</title>
</head>
<script type="text/javascript">
	function query(){
		$("pageNo").value=1;
		$("queryForm").submit();
	}
	
	function cancle() {
        window.close();
    }
    
</script>
<body class="mainBody">
<form action="queryOrderAuditLog.do" method="post" id="queryForm">
	<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
	<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<input type="hidden" name="auditLog.relationType" value="${auditLog.relationType}"/>
	<input type="hidden" name="auditLog.relationId" value="${auditLog.relationId}"/>
	<div class="searchContent">
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
                <td>订单编号</td>
                <td>审核结果</td>
                <td>审核人</td>
                <td>审核时间</td>
				<td>审核意见</td>
            </tr>
            <c:forEach items="${page.dataList}" var="log" varStatus="pl">
				<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>
					onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
					<td><c:out value="${log.relationName}" /></td>
					<td>
						<c:choose>
							<c:when test="${log.state==0}">审核通过</c:when>
							<c:when test="${log.state==1}">审核不通过</c:when>
							<c:when test="${log.state==2}">修改审核通过</c:when>
							<c:when test="${log.state==3}">修改审核不通过</c:when>
							<c:when test="${log.state==4}">删除审核通过</c:when>
							<c:when test="${log.state==5}">删除审核不通过</c:when>
						</c:choose>
					</td>
					<td><c:out value="${log.operatorName}" /></td>
					<td><c:out value="${log.auditTime}" /></td>
					<td><c:out value="${log.auditOpinion}" /></td>
				</tr>
			</c:forEach>
            <tr>
                <td colspan="5">
					<input type="button" value="关闭" class="btn" onclick="cancle();"/>&nbsp;&nbsp;
					<jsp:include page="../common/page.jsp" flush="true" />
                </td>
            </tr>
        </table>
    </div>
</form>
</body>
</html>