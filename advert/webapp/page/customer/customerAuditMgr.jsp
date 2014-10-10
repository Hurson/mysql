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
			<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/new/main.css" />
		<link href="<%=path%>/css/new/common/dhtmlx/tree/css/dhtmlxtree.css"
			rel="stylesheet" type="text/css" media="all" />

		<script type="text/javascript" src="<%=basePath%>/js/customerJS.js">
</script>

		<script language="javascript" type="text/javascript"
			src="js/jquery-1.4.4.js">
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
		<link rel="stylesheet" href="<%=path%>/css/jquery/jquery.ui.all.css">
</script>


			<script type="text/javascript">

function goBack() {
	window.location.href = "adCustomerMgr_listAudit.do";
}
var examinationOpintions = "";

	   //提交审核不通过意见
	   function noSubmitOpintions(){
	    	//	var examinationOpintions = document.getElementById("examinationOpinions").value;
	        var id = document.getElementById("customerBackUp.id").value;
	          if (examinationOpintions=='' || examinationOpintions==null)
	        	{
	        	alert("请输入审核意见！");
	        	return ;
	        	}
	      //  alert("广告商信息没有通过审核，确认请按确定按钮！");
			window.location.href="adCustomerMgr_insertNoAuditCustomer.do?cId= "+id+" "+"&examinationOpinions= "+examinationOpintions+" ";
	   		return false;
	   }
		
	   //提交审核通过意见
	   function submitOpintions(){
	   //		var examinationOpintions = document.getElementById("examinationOpinions").value;
	        var id = document.getElementById("customerBackUp.id").value;
	        if (examinationOpintions=='' || examinationOpintions==null)
	        	{
	        //	alert("请输入审核意见！");
	        //	return ;
	        	}
	      //  alert("广告商信息通过审核，确认请按确定按钮！");
			window.location.href="adCustomerMgr_insertGoAuditCustomer.do?cId= "+id+" "+"&examinationOpinions= "+examinationOpintions+" ";
	   		return false;
	   }
	   
	   function changeexaminationOpintions(examinationOpintionsValue)
	   {
	   examinationOpintions = examinationOpintionsValue;
	   }
	function preview(obj)
	{
		if (document.getElementById(obj).value=="#")
		{
			return false;
		}
		if (document.getElementById(obj).value.indexOf(".pdf")<=0)
			{
			return false;
			}
		window.open(document.getElementById(obj).value);
		
	}
</script>
	</head>

	<body class="mainBody">
		<form id="form" action="adCustomerMgr_customerAuditInfo.do"
			method="post" name="adCustomerMgr_customerAuditInfo">
			<div class="path">
				<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
				首页 >> 广告商管理 >> 广告商审核
			</div>
			<div class="searchContent">
				<table cellspacing="1" class="searchList">
					<tr class="title">
						<td colspan="4">
							广告商审核
							<input type=hidden value="${customerBackUp.id}"
								name="customerBackUp.id" id="customerBackUp.id"/>

						</td>
					</tr>
					<tr>
						<td align="right">
							客户状态：
						</td>
						<td>
							<c:if test='${customerBackUp.status == "0"}'>
								<font style="color: red;">待审核</font>
							</c:if>
							<c:if test='${customerBackUp.status == "1"}'>
								<font style="color: red;">审核通过</font>
							</c:if>
							<c:if test='${customerBackUp.status == "2"}'>
								<font style="color: red;">审核不通过</font>
							</c:if>
						</td>
						<td align="right">
							审核时间：
						</td>
						<td>
							<c:if test='${customerBackUp.status == "0"}'>
								<font style="color: red;">&nbsp;待审核状态无审核时间</font>
							</c:if>
							<c:if test='${customerBackUp.status == "1"}'>
								<fmt:formatDate value="${customerBackUp.auditDate}"
									pattern="yyyy-MM-dd" />
							</c:if>
							<c:if test='${customerBackUp.status == "2"}'>
								<fmt:formatDate value="${customerBackUp.auditDate}"
									pattern="yyyy-MM-dd" />
							</c:if>

						</td>
					</tr>
					<tr>
						<td align="right">
							合同扫描件路径：
						</td>
						<td>
							${customerBackUp.contract}
						</td>
						<td align="right">
							营业执照存储路径：
						</td>
						<td>
							${customerBackUp.businessLicence}
						</td>
					</tr>
					<tr>
						<td align="right">合同扫描件缩略图：</td>
						<td>
						  <!-- <img id="materialViewDivImg" src="" style="background-repeat:no-repeat; width:345px;height:250px;"/> -->
							<img style="display:none" id="materialViewDivImg" src="${viewPath}/${customerBackUp.contract}" width="250px" height="150px"/>
							<input id="materialViewDivImgpdf" name="materialViewDivImgpdf" value="${viewPath}/${customerBackUp.contract}" type="hidden"/>
							<input type="button" class="btn" value="预览" onclick="preview('materialViewDivImgpdf');" />
						
						</td>
						<td align="right">营业执照缩略图：</td>
						<td>
						   <img style="display:none" id="lookContractPic" src ="${viewPath}/${customerBackUp.businessLicence}" width="250px" height="150px"/>
						 <input id="lookContractPicpdf" name="lookContractPicpdf" value="${viewPath}/${customerBackUp.businessLicence}" type="hidden"/>
							<input type="button" class="btn" value="预览" onclick="preview('lookContractPicpdf');" />
						
						</td>
					</tr>
					<tr>
						<td align="right">
							客户代码：
						</td>
						<td>
							${customerBackUp.clientCode}
						</td>
						<td align="right">
							广告商名称：
						</td>
						<td>
							${customerBackUp.advertisersName}
						</td>
					</tr>
					<tr>
						<td align="right">
							广告商级别：
						</td>
						<td>
								<c:if test='${customerBackUp.customerLevel == "1"}'>国级</c:if>
							<c:if test='${customerBackUp.customerLevel == "2"}'>省级</c:if>
							<c:if test='${customerBackUp.customerLevel == "3"}'>市级</c:if>
							<c:if test='${customerBackUp.customerLevel == "4"}'>其它</c:if>
						</td>
						<td align="right">
							注册资金：
						</td>
						<td>
							${customerBackUp.registerFinancing}
						</td>
					</tr>
					<tr>
						<td align="right">
							注册地：
						</td>
						<td>
							${customerBackUp.registerAddress}
						</td>
						<td align="right">
							营业面积：
						</td>
						<td>
							${customerBackUp.businessArea}
						</td>
					</tr>
					
					
					
					
					
					
					<tr>
						<td align="right">
							联系人：
						</td>
						<td>
						${customerBackUp.communicator}
						</td>
						<td align="right">
							电话：
						</td>
						<td>
							${customerBackUp.tel}
						</td>
					</tr>
					<tr>
						<td align="right">
							手机：
						</td>
						<td>
							${customerBackUp.mobileTel}
						</td>
						<td align="right">
							传真：
						</td>
						<td>
							${customerBackUp.fax}
						</td>
					</tr>

					<tr>
						<td align="right">
							通讯地址：
						</td>
						<td>
						${customerBackUp.contacts}
						</td>
						<td align="right">
							合作期限：
						</td>
						<td>
							${customerBackUp.cooperationTime}
						</td>
					</tr>
					<tr>
						<td align="right">
							公司地址：
						</td>
						<td>
							${customerBackUp.conpanyAddress}
						</td>
						<td align="right">
							公司网址：
						</td>
						<td>
						${customerBackUp.conpanySheet}
						</td>
					</tr>
					<tr>
						<td align="right">
							描述：
						</td>
						<td>
							<textarea rows="6" name="customerBackUp.remark" disabled="disabled">${customerBackUp.remark}</textarea>
						</td>
						<td align="right">
							审核意见：
						</td>
						<td>
							<textarea rows="6" name="customerBackUp.examinationOpinions" onchange="changeexaminationOpintions(this.value);"
								>${customerBackUp.examinationOpinions}</textarea>
						</td>
					</tr>

					<tr>
						<td align="center" colspan="4">
							<input type="button" class="btn" value="通过" onclick="submitOpintions()" />
							<input type="button" class="btn" value="驳回" onclick="noSubmitOpintions();" />
							<input type="button" class="btn" value="返回" onclick="goBack();" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
