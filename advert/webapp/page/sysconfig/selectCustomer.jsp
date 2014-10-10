<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>


<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<title>广告商查询</title>
</head>
<script type="text/javascript">
	function query(){
		if(validateSpecialCharacterAfter($("#advertisersName").val())){
			alert("广告商名称不能包括特殊字符！");
			$("#advertisersName").focus();
			return ;
		}
		if(validateSpecialCharacterAfter($("#clientCode").val())){
			alert("客户代码不能包括特殊字符！");
			$("#clientCode").focus();
			return ;
		}
		$("#queryForm").submit();
	}

	function save() {
		if (getCheckCount('customer') <= 0) {
			alert("请选择广告商！");
			return;
    	} else {
			var contractValue = getCheckValue('customer');
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
<form action="showCustomerList.do" method="post" id="queryForm">
	<input type="hidden" name="roleType" value="${roleType}"/>
	<div class="searchContent">
		<table cellspacing="1" class="searchList">
            <tr class="title">
                <td>查询条件</td>
            </tr>
            <tr>
                <td class="searchCriteria">
                	<span>广告商名称：</span><input type="text" id="advertisersName" name="cust.advertisersName" value="${cust.advertisersName}" />
                	<span>客户代码：</span><input type="text" id="clientCode" name="cust.clientCode" value="${cust.clientCode}" />
                	<input type="button" value="查询" onclick="javascript:query();" class="btn"/>
   				</td>
            </tr>
        </table>
		<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
		<table cellspacing="1" class="searchList">
    		<tr class="title">
    			
    			<td height="28" class="dot">
    				<c:if test="${roleType==2}">
    					<input type="checkbox" name="chkAll" onclick="selectAll(this, 'customer');" id="chkAll"/>
    				</c:if>
    			</td>
    			<td>广告商名称</td>
    			<td>客户代码</td>
    			<td>联系人</td>
    			<td>公司地址</td>
    		</tr>
    		<c:forEach items="${customerList}" var="cust" varStatus="pl">
    		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
    			<td><input id="customer"
    				<c:choose>
						<c:when test="${roleType==2}">type="checkbox" </c:when>
						<c:when test="${roleType==1}">type="radio" </c:when>
					</c:choose>
    				name="customer" value="${cust.id}_${cust.advertisersName}" 
    				<c:forEach items="${customerIdList}" var="customerId">
						<c:if test="${customerId == cust.id}">checked</c:if>
					</c:forEach>
    			 /></td>
    			<td><c:out value="${cust.advertisersName}" /></td>
    			<td><c:out value="${cust.clientCode}" /></td>
    			<td><c:out value="${cust.communicator}" /></td>
    			<td><c:out value="${cust.conpanyAddress}" /></td>
    		</tr>
    		</c:forEach>
			<tr>
				<td colspan="5">
			    	<input type="button" value="确定" class="btn" onclick="javascript:save();"/>&nbsp;&nbsp;
        			<input type="button" value="取消" class="btn" onclick="javascript :window.close();"/>
			    </td>
			</tr>
		</table>		
	</div>
</form>
</body>
</html>