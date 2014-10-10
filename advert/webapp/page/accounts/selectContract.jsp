<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<title></title>
<script type="text/javascript">
    var $ = function(id){
        return document.getElementById(id);
    };
	

    function query(){
       document.forms[0].submit();
    }

   

    function selectcontract(contractName,contractId){
    	
    	parent.document.getElementById("contractName").value=contractName;
    	parent.document.getElementById("contractId").value=contractId;
    	parent.setNewPeriodStart(contractId);
        parent.easyDialog.close();
    }
	function cancle(){
    	
        parent.easyDialog.close();
    }
</script>
</head>

<body class="mainBody">

<form action="<%=path %>/page/accounts/getContracts.do" method="post" id="queryForm">
 <input type="hidden" id="pageno" name="page.pageNo" value="${page.pageNo}"/>
            <input type="hidden" id="pagesize" name="page.pageSize" value="${page.pageSize}"/>

<div class="search">

<div class="searchContent" >
<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>合同名称：</span><input type="text" name="contract.contractName" id="contract.contractName" value="${contract.contractName}"/>
	
        <span><input type="button" value="查询" onclick="javascript:query();" class="btn"/></span>
    </td>
  </tr>
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" width="5%" class="dot"></td>
        <td width="20%" align="center">合同名称</td>
        <td width="20%" align="center">合同编号</td>
        <td width="20%" align="center">广告商</td>
       <td width="20%" align="center">生效时间</td>
        <td width="20%" align="center">失效时间</td>
    </tr>
   				 <c:if test="${page.dataList != null && fn:length(page.dataList) > 0}">
                    <c:forEach items="${page.dataList}" var="pcontract" varStatus="pc">
                        <tr <c:if test="${pc.index%2==1}">class="sec"</c:if>>
                            <td>
                                <input type="radio" name="contractid" value="" onclick="selectcontract('${pcontract.contractName}','${pcontract.id}');">
                             </td>
                            <td>
                                ${pcontract.contractName}
                            </td>
                             <td>
                                ${pcontract.contractCode}
                            </td>
					        <td>
                                ${pcontract.customerId}
                            </td>				     
					        <td width="15%" align="center">
					        ${pcontract.effectiveStartDate}
							</td>
					        <td width="15%" align="center">
					        ${pcontract.effectiveStartDate}
							</td>
							
                        </tr>
                    </c:forEach>
                </c:if>
<tr>
<td colspan="6">
<jsp:include page="../common/page.jsp" flush="false" />
</td>
</tr>
</table>
</div>
</div>
</form>
</body>
</html>