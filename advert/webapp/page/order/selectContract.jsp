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
<title>选择订单合同</title>
</head>
<script type="text/javascript">
	function query(){
		if(validateSpecialCharacterAfter($("#contractName").val())){
			alert("合同名称不能包括特殊字符！");
			$("#contractName").focus();
			return ;
		}
		if(validateSpecialCharacterAfter($("#contractCode").val())){
			alert("合同号不能包括特殊字符！");
			$("#contractCode").focus();
			return ;
		}
		if(validateSpecialCharacterAfter($("#contractNumber").val())){
			alert("合同代码不能包括特殊字符！");
			$("#contractNumber").focus();
			return ;
		}
		$("#queryForm").submit();
	}

	function save() {
		if (getCheckCount('contractId') <= 0) {
			alert("请选择合同！");
			return;
    	} else {
			var contractValue = getCheckValue('contractId');
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
<form action="showContractList.do" method="post" id="queryForm">
	<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
	<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<div class="searchContent">
        <table cellspacing="1" class="searchList">
            <tr class="title">
                <td>查询条件</td>
            </tr>
            <tr>
                <td class="searchCriteria">
                	<span>合同名称：</span><input type="text" id="contractName" name="contract.contractName" value="${contract.contractName}" style="width: 14%"/>
                    <span>合&nbsp;同&nbsp;号：</span><input type="text" id="contractCode" name="contract.contractCode" value="${contract.contractCode}" style="width: 14%"/>
                    <span>合同代码：</span><input type="text" id="contractNumber" name="contract.contractNumber" value="${contract.contractNumber}" style="width: 14%"/>
                    <input type="button" value="查询" onclick="javascript:query();" class="btn"/>
   				</td>
            </tr>
        </table>
        <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
              <td class="dot"></td>
                <td>合同名称</td>
                <td>合同号</td>
                <td>合同代码</td>
				<td>开始时间</td>
				<td>结束时间</td>
            </tr>
            <c:forEach items="${page.dataList}" var="contract" varStatus="pl">
				<tr <c:if test="${pl.index%2==1}">class="sec"</c:if> 
					onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
					<td align="center">
						<input type="radio"	name="contractId" value="${contract.id}_${contract.contractName}" 
						<c:if test="${order.contractId == contract.id}">checked</c:if> />
					</td>
					<td><c:out value="${contract.contractName}" /></td>
					<td><c:out value="${contract.contractCode }" /></td>
					<td><c:out value="${contract.contractNumber }" /></td>
					<td><fmt:formatDate value="${contract.effectiveStartDate}" dateStyle="medium"/></td>
					<td><fmt:formatDate value="${contract.effectiveEndDate}" dateStyle="medium"/></td>
				</tr>
			</c:forEach>
            <tr>
                <td colspan="6">
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