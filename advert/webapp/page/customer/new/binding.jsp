<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<input id="projetPath" type="hidden" value="<%=path%>" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
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
	
	function generateAccess(pageNo,advertisersName,status){
	
		var path = 'adCustomerMgr_list.do?contractBindingFlag=1&pageNo='+pageNo;
		
		if((!$.isEmptyObject(advertisersName))&&(''!=advertisersName)){
				$('#advertisersName').val(advertisersName);
		}
		
		if((!$.isEmptyObject(status))&&(''!=status)){
				$('#status').val(status);
		}
		
		submitForm('#bindingCustomerForm',path);
		
	}
    </script>
</head>

<body class="mainBody">

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
            <c:choose>
						<c:when test="${!empty listCustomers}">
							<c:forEach var="customer" items="${listCustomers}" varStatus="status">
								<tr>
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
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="7">
									暂无记录
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
           
           <tr>
				<td height="34" colspan="7"
							style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
							<input type="button" value="返回" class="btn" onclick="closeDialog();"/>&nbsp;&nbsp;
							<c:if test="${page.totalPage > 0 }">
								<a href="#">共计${page.totalPage }页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp;
								<c:choose>
									<c:when test="${page.pageNo==1&&page.totalPage!=1}">
										<a href="#" onclick="generateAccess('${page.pageNo+1 }','${advertisersName}','1')">下一页</a>&nbsp;&nbsp;
										<a href="#" onclick="generateAccess('${page.totalPage}','${advertisersName}','1')">末页</a>&nbsp;&nbsp;
									</c:when>
									<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1}">
										<a href="#" onclick="generateAccess('1','${advertisersName}','1')">首页</a>&nbsp;&nbsp;
										<a href="#" onclick="generateAccess('${page.pageNo-1}','${advertisersName}','1')">上一页</a>&nbsp;&nbsp;
									</c:when>
									<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
										<a href="#" onclick="generateAccess('1','${advertisersName}','1')">首页</a>&nbsp;&nbsp;
										<a href="#" onclick="generateAccess('${page.pageNo-1}','${advertisersName}','1')">上一页</a>&nbsp;&nbsp;
										<a href="#" onclick="generateAccess('${page.pageNo+1}','${advertisersName}','1')">下一页</a>&nbsp;&nbsp;
										<a href="#" onclick="generateAccess('${page.totalPage}','${advertisersName}','1')">末页</a>&nbsp;&nbsp;
									</c:when>
								</c:choose>
							</c:if>
						</td>
					</tr>
        </table>
    </div>
</div>
</body>
</html>