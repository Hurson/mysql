<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tags/fmt.tld" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/new/main.css" />
		<link href="<%=path%>/css/new/common/dhtmlx/tree/css/dhtmlxtree.css"
			rel="stylesheet" type="text/css" media="all" />

		<script type="text/javascript" src="<%=basePath%>/js/customerJS.js">
</script>



	<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/jquery-1.9.0.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/contract/listContract.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js">
</script>
		<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js">
</script>
		<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js">
</script>

<script src="<%=path%>/js/new/avit.js">
</script>

		<link rel="stylesheet" href="<%=path%>/css/jquery/jquery.ui.all.css" />
		<script>

function go() {
		document.getElementById("queryForm").submit();
}

$(function() {
	$("#bm").find("tr:even").addClass("treven"); //奇数行的样式
	$("#bm").find("tr:odd").addClass("trodd"); //偶数行的样式
});
</script>
		<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}
</style>
	</head>
	
		<body class="mainBody" onload="">
		<form id="queryForm" action="adCustomerMgr_listAudit.do" method="post"
		name="adCustomerMgr_listAudit">
		 <s:set name="page" value="%{pageCustomer}" />
		 <input type="hidden" id="pageNo" name="pageCustomer.pageNo" value="${page.pageNo}"/>
		 <input type="hidden" id="pageSize" name="pageCustomer.pageSize" value="${page.pageSize}"/>
		 
		<div class="path">
					<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
					首页 >> 广告商管理 >> 广告商维护
				</div>
			<div class="search">
				
				<div class="searchContent">
					<table cellspacing="1" class="searchList">
						<tr class="title">
							<td colspan="1">
								查询条件
							</td>
						</tr>
						<tr>
							<td>
								<span>广告商名称</span>
								<input onkeypress="return validateSpecialCharacter();" type="text" name="customerBackUp.advertisersName"
									value="${advertisersName}" class="e_input"
									onfocus="this.className='e_inputFocus'"
									onblur="this.className='e_input'" />
								<span>客户代码</span>
								<input onkeypress="return validateSpecialCharacter();" type="text" name="customerBackUp.clientCode"
									value="${clientCode}" class="e_input"
									onfocus="this.className='e_inputFocus'"
									onblur="this.className='e_input'" />
								<span>联系人：</span>
								<input onkeypress="return validateSpecialCharacter();" type="text" name="customerBackUp.communicator"
									value="${communicator}" class="e_input"
									onfocus="this.className='e_inputFocus'"
									onblur="this.className='e_input'" />
								<input type="button" value="查询" onclick="javascript:go();"
									class="btn" />
							</td>
						</tr>
					</table>
					<div id="messageDiv"
						style="margin-top: 15px; color: red; font-size: 14px; font-weight: bold;"></div>
					<table cellspacing="1" class="searchList">
						<tr class="title">
							<td>
								广告商名称
							</td>
							<td>
								客户代码
							</td>
							<td>
								公司地址
							</td>
							<td>
								联系人
							</td>
							<td>
								联系方式
							</td>
							<td>
								级别
							</td>
							<td>
								状态
							</td>
							<td>
								创建时间
							</td>
						</tr>
						<s:if test="listCustomers.size==0">
							<tr>
								<td bgcolor="#FFFFFF" align="center" colspan="12"
									style="text-align: center">
									无记录
								</td>
							</tr>
						</s:if>
						<s:else>
							<c:set var="index" value="0" />
							<s:iterator value="listCustomers" status="rowstatus" var="item">
								<tr <c:if test="${index%2==1}">class="sec"</c:if>>
									<td align="center">
										
										<a href="adCustomerMgr_getCustomerAuditInfo.do?cId=${id}"><s:property
												value="advertisersName" /> </a>

									</td>
									<td align="center">
										<s:property value="clientCode" />
									</td>
									<td align="center">
										<s:property value="conpanyAddress" />
									</td>
									<td align="center">
										<s:property value="communicator" />
									</td>
									<td align="center">
										<s:property value="contacts" />
									</td>
									<td align="center">
										<s:if test='customerLevel == "1"'>国级</s:if>
										<s:if test='customerLevel == "2"'>省级</s:if>
										<s:if test='customerLevel == "3"'>市级</s:if>
										<s:if test='customerLevel == "4"'>其它</s:if></td>
									<td align="center">
										<s:if test='status == "0"'>待审核</s:if>
										<s:if test='status == "1"'>审核通过</s:if>
										<s:if test='status == "2"'>审核未通过</s:if>
									</td>
									<td align="center">
									<fmt:formatDate value="${item.createTime}"
											pattern="yyyy-MM-dd" />
											</td>
								</tr>
								<c:set var="index" value="${index+1}" />
							</s:iterator>
						</s:else>
						 <tr>
					    <td colspan="8">
					      
					        <jsp:include page="../common/page.jsp" flush="true" />
					    </td>
					  </tr>
						<!--
						<tr>
							<td height="34" colspan="12"
								style="background: url(images/bottom.jpg) repeat-x; text-align: right;">
								<a href="#">共计${page.totalPage }页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp;
								<c:choose>
									<c:when test="${page.pageNo==1&&page.totalPage!=1}">
								【<a href="adCustomerMgr_listAudit.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAudit.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
							</c:when>
									<c:when
										test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
								【<a href="adCustomerMgr_listAudit.do?pageNo=1">首页</a>】&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAudit.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
							</c:when>
									<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
								【<a href="adCustomerMgr_listAudit.do?pageNo=1">首页</a>&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAudit.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAudit.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAudit.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
							</c:when>
								</c:choose>
							</td>
						</tr>
						-->
					</table>
				</div>
			</div>
			</form>
		</body>
	
</html>