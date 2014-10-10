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
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="css/platform.css" type="text/css" />
<script language="javascript" type="text/javascript" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/customerJS.js"></script>


<script type="text/javascript">


	function goBack(){
		window.location.href="adCustomerMgr_listAudit.do";
	}
	
	function go(){
		var clientCode = document.getElementById("clientCode").value;
		var advertisersName = document.getElementById("advertisersName").value;
		var contacts = document.getElementById("contacts").value;
		
		if(clientCode == ""){
			document.getElementById("clientCodeF").innerHTML ="客户代码不能空！";
			return false;
		}else{
			document.getElementById("clientCodeF").innerHTML ="";
			if(advertisersName == ""){
				document.getElementById("advertisersNameF").innerHTML ="广告商名称不能空！";
				return false;
			}else{
				document.getElementById("advertisersNameF").innerHTML ="";
				if(contacts == ""){
					document.getElementById("contactsF").innerHTML ="联系方式不能空！";
					return false;
				}else{
				   document.getElementById("contactsF").innerHTML ="";
				   		 $.ajax( {
							type : 'post',
							url : 'adCustomerMgr_saveValidateCustomer.do',
							data : 'clientCode=' + clientCode + '&date=' + new Date(),
							success : function(msg) {
								if(msg =="1"){
									document.getElementById("clientCodeF").innerHTML ="客户代码已存在，请重新输入！";
								}else{
									$("#form").submit();
								}
							}	
					    });
					}
				}
			}
	}
	
	
	

</script>



<title>客户管理</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
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

<body>
<form id="form" action="adCustomerMgr_updateCustomer.do"  method="post" name="adCustomerMgr_updateCustomer"> 

<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td>
		<table cellpadding="0" cellspacing="0" border="0" width="100%"
			class="tab_right">
			<tr>
				<td>工作台</td>
				<td>客户</td>
				<td>日程行动</td>
				<td>销售机会</td>
				<td>订单管理</td>
				<td>客服中心</td>
				<td>财务中心</td>
				<td>营销中心</td>
				<td>人力资源中心</td>
				<td>数据统计</td>
				<td>信息门户管理</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding: 4px;">
		<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
			<tr>
				<td class="formTitle" colspan="99"><span>广告商信息</span></td>
				
			</tr>
			<tr>
			
				<input type=hidden value="${customerBackUp.id}" name="customerBackUp.id"/>
            	<input type=hidden value="${customerBackUp.createTime}" name="customerBackUp.createTime"/>
            	<input type=hidden value="${customerBackUp.operator}" name="customerBackUp.operator"/>
            	<input type=hidden value="${customerBackUp.creditRating}" name="customerBackUp.creditRating"/>
				<input type=hidden value="${customerBackUp.auditTaff}" name="customerBackUp.auditTaff"/>
			
				<input type=hidden value="${customerBackUp.status}" name="customerBackUp.status" id="statusHidden"/>
				<input type=hidden value="${customerBackUp.auditDate}" name="customerBackUp.auditDate" id="auditDateHidden"/>
				
				<td class="td_label">客户状态：</td>
				<td class="td_input">&nbsp;
				      <c:if test='${customerBackUp.status == "0"}'><font style="color:red;">待审核</font></c:if>
					  <c:if test='${customerBackUp.status == "1"}'><font style="color:red;">审核通过</font></c:if>
					  <c:if test='${customerBackUp.status == "2"}'><font style="color:red;">审核不通过</font></c:if>				
			    </td>
				<td class="td_label">审核时间：</td>
				<td class="td_input">&nbsp;
				  <c:if test='${customerBackUp.status == "0"}'><font style="color:red;">&nbsp;待审核状态无审核时间</font></c:if>
				  <c:if test='${customerBackUp.status == "1"}'><fmt:formatDate value="${customerBackUp.auditDate}" pattern="yyyy-MM-dd"/></c:if>
				  <c:if test='${customerBackUp.status == "2"}'><fmt:formatDate value="${customerBackUp.auditDate}" pattern="yyyy-MM-dd"/></c:if>
				</td>
			</tr>
			<tr>
				<td class="td_label">合同扫描件路径：</td>
				<td class="td_input">&nbsp;${customerBackUp.contract}</td>
				<td class="td_label">营业执照存储路径：</td>
				<td class="td_input">&nbsp;${customerBackUp.businessLicence}</td>
			</tr>	
			
			
			<tr>
				<td class="td_label">客户代码：</td>
				<td class="td_input">&nbsp;${customerBackUp.clientCode}</td>
				<td class="td_label">广告商名称：</td>
				<td class="td_input">&nbsp;${customerBackUp.advertisersName}</td>
			</tr>	
			<tr>
				<td class="td_label">联系人：</td>
				<td class="td_input">&nbsp;${customerBackUp.communicator}</td>
				<td class="td_label">电话：</td>
				<td class="td_input">&nbsp;${customerBackUp.tel}</td>
			</tr>
			<tr>
				<td class="td_label">手机：</td>
				<td class="td_input">&nbsp;${customerBackUp.mobileTel}</td>
				<td class="td_label">传真：</td>
				<td class="td_input">&nbsp;${customerBackUp.fax}</td>
			</tr>
			<tr>
				<td class="td_label">通讯地址：</td>
				<td class="td_input">&nbsp;${customerBackUp.contacts}</td>
				<td class="td_label">合作期限：</td>
				<td class="td_input">&nbsp;${customerBackUp.cooperationTime}</td>
			</tr>				
			<tr>
			   <td class="td_label">公司地址：</td>
			   <td class="td_input">&nbsp;${customerBackUp.conpanyAddress}</td>
			   <td class="td_label">公司网址：</td>
			   <td class="td_input">&nbsp;${customerBackUp.conpanySheet}</td>
		    </tr>
			
			<tr>
					<td class="td_label">描述：</td>
					<td class="td_input"><textarea id="" rows="4" cols="30" readOnly="true" name="customerBackUp.remark">${customerBackUp.remark}</textarea></td>
					<td class="td_label">审核意见：</td>
				    <td class="td_input"><textarea id="" rows="4" cols="30" readOnly="true" name="customerBackUp.examinationOpinions">${customerBackUp.examinationOpinions}</textarea></td>
			</tr>
			
			<tr>
				<td class="formBottom" colspan="99" align="center">
				    <input type="button" title="返回" onfocus=blur() value="返回" onclick="goBack()" />
				</td>
			</tr> 
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
