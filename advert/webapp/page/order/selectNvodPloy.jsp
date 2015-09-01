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
<form action="showPloyList.do" method="post" id="queryForm">
	<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
	<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<input type="hidden" name="ploy.positionId" value="${ploy.positionId}"/>
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
        <c:if test="${adPackageType==0 || adPackageType==1}">
        <!-- 双向广告位策略 -->
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
              <td class="dot"></td>
                <td>策略名称</td>
                <td>开始时段</td>
                <td>结束时段</td>
                <td>投放次数</td>
            </tr>
            <c:forEach items="${page.dataList}" var="ploy" varStatus="pl">
				<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>
					onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
					<td align="center">
						<input type="radio"	name="ployId" value="${ploy.ployId}_${ploy.ployName}_${adPackageType}_${ploy.ployNumber}" 
						<c:if test="${order.ployId == ploy.ployId}">checked</c:if> />
					</td>
					<td><c:out value="${ploy.ployName}" /></td>
					<td><c:out value="${ploy.startTime }" /></td>
					<td><c:out value="${ploy.endTime }" /></td>
					<td><c:out value="${ploy.ployNumber }" /></td>
				</tr>
			</c:forEach>
            <tr>
                <td colspan="5">
                	<input type="button" value="确定" class="btn" onclick="save();"/>&nbsp;&nbsp;
					<input type="button" value="取消" class="btn" onclick="cancle();"/>&nbsp;&nbsp;
					<jsp:include page="../common/page.jsp" flush="true" />
                </td>
            </tr>
        </table>
        </c:if>
        <c:if test="${adPackageType==2 || adPackageType==3 || adPackageType==4}">
        <!-- 单向广告位策略 -->
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
              <td class="dot"></td>
                <td>策略名称</td>
                <td>投放时段</td>
                <td>投放区域</td>
                <td>投放类型</td>
            	</tr>
            <c:forEach items="${page.dataList}" var="ploy" varStatus="pl">
				<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>
					onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
					<td align="center">
						<input type="radio"	name="ployId" value="${ploy.ployId}_${ploy.ployName}_${adPackageType}_0" 
						<c:if test="${order.ployId == ploy.ployId}">checked</c:if> />
					</td>
					<td><c:out value="${ploy.ployName}" /></td>
					<td>
						<table>
							<c:forEach items="${ploy.subPloyList}" var="sub" >
							<c:forEach items="${sub.matches}" var="subSub" >
								<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
									<td><c:out value="${sub.startTime}" />-<c:out value="${sub.endTime}" /></td>
								</tr>
							</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td>
						<table>
							<c:forEach items="${ploy.subPloyList}" var="sub" >
							<c:forEach items="${sub.matches}" var="subSub" >
								<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
									<td><c:out value="${sub.description}" /></td>
								</tr>
							</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td>
						<table>
						<c:forEach items="${ploy.subPloyList}" var="sub" >
							<c:forEach items="${sub.matches}" var="subSub" >
								<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
									<td><c:out value="${subSub.menuTypeName}" /></td>
								</tr>
							</c:forEach>
							</c:forEach>
						</table>
					</td>
				</tr>
			</c:forEach>
            <tr>
                <td colspan="5">
                	<input type="button" value="确定" class="btn" onclick="save();"/>&nbsp;&nbsp;
					<input type="button" value="取消" class="btn" onclick="cancle();"/>&nbsp;&nbsp;
					<jsp:include page="../common/page.jsp" flush="true" />
                </td>
            </tr>
        </table>
        </c:if>
    </div>
</form>
</body>
</html>