<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tags/fmt.tld" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>

<script type="text/javascript">
	function query() {
		$("pageNo").value=1;
		$("queryForm").submit();
	}
	
	
</script>
</head>

<body class="mainBody">
<div class="search">
<div class="path">首页 >> 合同管理 >> 合同台账维护</div>
<div class="searchContent" >
<form action="queryAccountsList.do" method="post" id="queryForm">
<input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
<input type="hidden" id="contractId" name="contractId" value="${contractId}"/>
	<table cellspacing="1" class="searchList">
	  <tr class="title">
	    <td>查询条件</td>
	  </tr>
	  <tr>
	    <td class="searchCriteria">
	      <span>台账单号：</span><input type="text" name="queryKey" value="${queryKey}" />
	      <input type="submit" value="查询" onclick="javascript:query();" class="btn"/>
	     </td>
	  </tr>
	</table>

<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
	 <c:if test="${message != null}">
            <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
    </c:if>
</div> 
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td width="20%" align="center">台账单号</td>
        <td width="10%" align="center">付款方式</td>
        <td width="20%" align="center">付款金额（万元）</td>
        <td width="20%" align="center">付款时间</td>
        <td width="30%" align="center">到期时间</td>
    </tr>
    
    <c:forEach items="${page.dataList}" var="accounts" varStatus="pl">
		<tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
			
			<td align="center"><!-- <a href="getAccounts.do?accountsId=${accounts.accountsId}"> --><c:out value="${accounts.accountsCode}" /></a>
				<input type="hidden" id="accountsId" name="accounts.accountsId"  value="${accounts.accountsId}"/>
			</td>
			<td align="center">
				<c:choose>
					<c:when test="${accounts.paySort==1}">按月付款</c:when>
					<c:when test="${accounts.paySort==2}">按季度付款</c:when>
				</c:choose>
			</td>
			
			<td align="right"><c:out value="${accounts.moneyAmount}" /></td>
			<td align="center"><fmt:formatDate value="${accounts.payDay}"pattern="yyyy-MM-dd" /></td>
			<td align="center"><fmt:formatDate value="${accounts.payVallidityPeriodBegin}"pattern="yyyy-MM-dd" />&nbsp;至&nbsp;<fmt:formatDate value="${accounts.payVallidityPeriodEnd}"pattern="yyyy-MM-dd" /></td>
		</tr>
	</c:forEach>
		
        
  <tr>
  	<td colspan = "6">
  		<jsp:include page="../common/page.jsp" flush="true" />
        
    </td>
  </tr>
</table>
</form>
</div>
</div>
</body>
</html>