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
<title>选择订单策略</title>
</head>
<script type="text/javascript">
	function query(){
		if(validateSpecialCharacterAfter($("#ployName").val())){
			alert("策略名称不能包括特殊字符！");
			$("#ployName").focus();
			return ;
		}
		$("#queryForm").submit();
	}

	function save() {
		if (getCheckCount('ployId') <= 0) {
			alert("请选择策略！");
			return;
    	} else {
			var ployValue = getCheckValue('ployId');
			window.returnValue=ployValue;
			window.close();
		}
		
	}
	
	function cancle() {
        window.close();
    }
    
</script>
<body class="mainBody">
<form action="queryDPloyList.action" method="post" id="queryForm">
	<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
	<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<input type="hidden" name="ploy.dposition.positionCode" value="${ploy.dposition.positionCode}"/>
	<div class="searchContent">
        <table cellspacing="1" class="searchList">
            <tr class="title">
                <td>查询条件</td>
            </tr>
            <tr>
                <td class="searchCriteria">
                	<span>策略名称：</span><input type="text" id="ployName" name="ploy.ployName" value="${ploy.ployName}" />
                	<input type="button" value="查询" onclick="javascript:query();" class="btn"/>
   				</td>
            </tr>
        </table>
        <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
       
        <!-- 单向广告位策略 -->
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
              <td class="dot"></td>
                <td>策略名称</td>
                <td>区域</td>
                <td>时段</td>
                <c:forEach items="${page.dataList[0].dposition.mainPloyMap }" var="map">
                	<td>${map.value }</td>
                </c:forEach>
            </tr>
            <c:forEach items="${page.dataList}" var="ploy" varStatus="pl">
				<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>
					onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
					<td align="center">
						<input type="radio"	name="ployId" value="${ploy.id}|${ploy.ployName}|${ploy.dposition.resourceCount}|${ploy.dposition.mainPloy}" 
						
					</td>
					<td><c:out value="${ploy.ployName }"/></td>
					<td>
						<table>
							<c:forEach items="${ploy.ployDetailList}" var="sub" >
							<c:if test="${'1' eq sub.ployType }">
								
								<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
									<td><c:out value="${sub.typeValue}" /></td>
								</tr>
							</c:if>
							</c:forEach>
						</table>
					</td>
					<td>
						<table>
							<c:forEach items="${ploy.ployDetailList}" var="sub" >
							<c:if test="${'2' eq sub.ployType }">
								<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
									<td><c:out value="${sub.typeValue}" /></td>
								</tr>
							</c:if>
							</c:forEach>
						</table>
					</td>
					<c:forTokens items="${ploy.dposition.mainPloy}" delims="|" var="type">
					<td>
						<table>
							<c:set var="flag" value="0"></c:set>
							<c:forEach items="${ploy.ployDetailList}" var="sub" >
							<c:if test="${type eq sub.ployType }">
								<c:set var="flag" value="1"></c:set>
								<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
									<td><c:out value="${sub.typeValue}" /></td>
								</tr>
							</c:if>
							</c:forEach>
							<c:if test="${'0' eq flag}">
								<tr>
									<td>全频道</td>
								</tr>
							</c:if>
						</table>
					</td>
					</c:forTokens>
					
				</tr>
			</c:forEach>
            <tr>
                <td colspan="5">
                	<input type="button" value="确定" class="btn" onclick="save();"/>&nbsp;&nbsp;
					<input type="button" value="取消" class="btn" onclick="cancle();"/>&nbsp;&nbsp;
					<jsp:include page="../../common/page.jsp" flush="true" />
                </td>
            </tr>
        </table>
    </div>
</form>
</body>
</html>