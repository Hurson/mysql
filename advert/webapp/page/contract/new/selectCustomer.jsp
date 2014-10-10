<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<input id="projetPath" type="hidden" value="<%=path%>" />

<link id="maincss"  rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/new/main.css" media="all"/>
    <title></title>
	<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
	<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>
	<script type="text/javascript" src="<%=path%>/js/util/tools.js"></script>
<script type="text/javascript">
	
	function selectCustomer(customerId,customerName){
    	
    	parent.document.getElementById("contract.customerId").value=customerId;
    	parent.document.getElementById("contract.customerName").value=customerName;
    	
        parent.easyDialog.close();
    }
    
    function closeDialog(){
    	
        parent.easyDialog.close();
    }
	

</script>
</head>

<body class="mainBody">
<form action="<%=path %>/page/contract/selectCustomer.do" method="post" id="queryForm">
<s:set name="page" value="pageCustomer" />
 <input type="hidden" id="pageNo" name="pageCustomer.pageNo" value="${pageCustomer.pageNo}"/>
 <input type="hidden" id="pageSize" name="pageCustomer.pageSize" value="${pageCustomer.pageSize}"/>
<div class="search">
        <div class="path">首页 >> 合同管理 >>合同维护&gt;&gt;选择广告商</div>
        <div class="searchContent">

        <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
        <table width="100%" cellspacing="1" class="searchList" id="bm">
            <tr class="title">
              <td width="38" height="28" class="dot">选项</td>
                <td width="150">广告商名称</td>
                <td width="141">客户代码</td>
                <td width="900">公司地址</td>
                <td width="172">联系人</td>
				<td width="193">电话</td>
				<td width="134">状态</td>
            </tr>

						<c:if test="${pageCustomer.dataList != null && fn:length(pageCustomer.dataList) > 0}">
							<c:forEach var="customer" items="${pageCustomer.dataList}" varStatus="status">
								<tr <c:if test="${status.index%2==1}">class="sec"</c:if>>
									<td><input type="radio" id="customerRadio${customer.id}" name="customer" value="${customer.id}" onclick="selectCustomer('${customer.id}','${customer.advertisersName}')"/></td>
									<td>${customer.advertisersName}</td>
									<td>${customer.clientCode}</td>
									<td>${customer.conpanyAddress}</td>
									<td>${customer.communicator}</td>
									<td>${customer.tel}</td>
									<td>
										<c:choose>
											<c:when test="${customer.status==0}">
												待审核
											</c:when>
											<c:when test="${customer.status==1}">
												审核通过
											</c:when>
											<c:otherwise>
												审核不通过
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						
			
           
           <tr>
				<td colspan="7">
							<input type="button" value="返回" class="btn" onclick="closeDialog();"/>&nbsp;&nbsp;							
							<jsp:include page="../../common/page.jsp" flush="true" />
                           
						</td>
					</tr>
        </table>
    </div>
</div>
</form>
</body>
</html>