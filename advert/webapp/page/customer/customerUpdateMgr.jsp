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
			<link rel="stylesheet" type="text/css"
				href="<%=path%>/css/new/main.css" />
			<link href="<%=path%>/css/new/common/dhtmlx/tree/css/dhtmlxtree.css"
				rel="stylesheet" type="text/css" media="all" />
			<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxcommon.js"
				type="text/javascript">

<script language="javascript" type="text/javascript" src="js/jquery-1.4.4.js"></script>
			<script type="text/javascript" src="<%=basePath%>/js/customerJS.js">
</script>


<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>

			<script type="text/javascript" src="<%=path%>/js/new/avit.js"></script>



			<script type="text/javascript">

function goBack() {
	window.location.href = "adCustomerMgr_list.do";
}

function go() {
	var advertisersName = document.getElementById("advertisersName").value;
	var communicator = document.getElementById("communicator").value;
	var tel = document.getElementById("tel").value;
	var mobileTel = document.getElementById("mobileTel").value;
	var contacts = document.getElementById("contacts").value;
	var registerFinancing = document.getElementById("customerBackUp.registerFinancing").value;
	var businessArea = document.getElementById("customerBackUp.businessArea").value;
	
	var cooperationTime = document.getElementById("cooperationTime").value;
	
	
	if (advertisersName == "") {
		document.getElementById("advertisersNameF").innerHTML = "广告商名称不能空！";
		return false;
	} else {
		
		
			if (registerFinancing=="")
			{
				document.getElementById("registerFinancing").innerHTML = "注册资金不能为空！";
				return ;
			}
			else
			{
				document.getElementById("registerFinancing").innerHTML = "";
			}
			
			if (numberCheck(registerFinancing)==false)
			{
				document.getElementById("registerFinancing").innerHTML = "注册资金需为数字！";
				return ;
			}
			else
			{
				document.getElementById("registerFinancing").innerHTML = "";
			}
			
			if (numberCheck(businessArea)==false)
			{
				document.getElementById("businessArea").innerHTML = "营业面积需为数字！";
				return ;
			}
			else
			{
				document.getElementById("businessArea").innerHTML = "";
			}
		
		
		document.getElementById("advertisersNameF").innerHTML = "";
		if (communicator == "") {
			document.getElementById("communicatorF").innerHTML = "联系人不能空！";
			return false;
		} else {
			document.getElementById("communicatorF").innerHTML = "";
			if (tel == ""  || isIntegerNumber(tel)==false) {
					document.getElementById("telF").innerHTML = "电话不能为空,且为数字！";
				return false;
			} else {
				document.getElementById("telF").innerHTML = "";
				if (cooperationTime == "") {
					document.getElementById("cooperationTimeF").innerHTML = "合作期限不能为空！";
						return false;
				} else
				{
					document.getElementById("cooperationTimeF").innerHTML = "";
					if (contacts == "") {
						document.getElementById("contactsF").innerHTML = "通讯地址不能为空！";
						return false;
					}  else {
						
						document.getElementById("form").submit();
						window.close();
					}

				}
			}
		}
	}
}

	$(function(){
	     //上传营业执照
		 $("#file_business_id").uploadify({
		'uploader':'<%=path%>/js/jquery/upload/image/uploadify.swf',
		'script':'adCustomerMgr_uploadBusiness.do',
		'cancelImg':'<%=path%>/js/jquery/upload/image/cancel.png',
		'folder':'/uploadFiles',
		'queueID':'fileQueue',
		'buttonImg':'<%=path%>/js/jquery/upload/image/uploadify.jpg',
		'fileDataName': 'backgroundImage',
		'auto':true,
		'multi':false,
		'fileDesc':'*.pdf',
		'fileExt':'*.pdf',
		 'displayData':'speed',
		  'width':'76',
    	'height':'23',
		'onComplete':function(event,queueID,fileObj,response,data){
			var json = eval('(' + response + ')');
			if(json!=null){
					if(json.position=='local'){
						if(json.result=='true'){
							$("#lookContractPic").attr("src","<%=path%>/uploadFiles/businessLicence/"+json.viewpath);
							$("#lookContractPicpdf").attr("value","<%=path%>/uploadFiles/businessLicence/"+json.viewpath);
							
							$("#backgroundBusinessImage").val(json.filepath);
						}else{
							alert('营业执照图片上传失败');
						}
					}
				}	
		   }
	  });
	  //上传合同号
		 $("#file_contract_id").uploadify({
		'uploader':'<%=path%>/js/jquery/upload/image/uploadify.swf',
		'script':'adCustomerMgr_uploadContract.do',
		'cancelImg':'<%=path%>/js/jquery/upload/image/cancel.png',
		'folder':'/uploadFiles',
		'queueID':'fileQueue',
		'buttonImg':'<%=path%>/js/jquery/upload/image/uploadify.jpg',
		'fileDataName': 'backgroundImage',
		'auto':true,
		'multi':false,
		'fileDesc':'*.pdf',
		'fileExt':'*.pdf',
		 'displayData':'speed',
		 'width':'76',
    	'height':'23',
		'onComplete':function(event,queueID,fileObj,response,data){
			var json = eval('(' + response + ')');
			if(json!=null){
					if(json.position=='local'){
						if(json.result=='true'){
							$("#materialViewDivImg").attr("src","<%=path%>/uploadFiles/contractScanFiles/"+json.viewpath);
							$("#materialViewDivImgpdf").attr("value","<%=path%>/uploadFiles/contractScanFiles/"+json.viewpath);
							
							$("#backgroundImage").val(json.filepath);
						}else{
							alert('合同图片上传失败');
						}
					}
				}	
		}
	  });
	
	});
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
		<form id="form" action="adCustomerMgr_updateCustomerBackUp.do"
			method="post" name="adCustomerMgr_updateCustomer">
			<div class="path">
				<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
				首页 >> 广告商管理 >> 广告商修改
			</div>
			<div class="searchContent">
				<table cellspacing="1" class="searchList">
					<tr class="title">
						<td colspan="4">
							广告商信息修改
							<input type=hidden value="${customerBackUp.id}"
								name="customerBackUp.id" />
							<input type=hidden value="${customerBackUp.createTime}"
								name="customerBackUp.createTime" />
							<input type=hidden value="${customerBackUp.operator}"
								name="customerBackUp.operator" />
							<input type=hidden value="${customerBackUp.creditRating}"
								name="customerBackUp.creditRating" />
							<input type=hidden value="${customerBackUp.auditTaff}"
								name="customerBackUp.auditTaff" />
							<input type=hidden value="${customerBackUp.clientCode}"
								name="customerBackUp.clientCode" />

							<input type=hidden value="${customerBackUp.status}"
								name="customerBackUp.status" id="statusHidden" />
							<input type=hidden value="${customerBackUp.auditDate}"
								name="customerBackUp.auditDate" id="auditDateHidden" />
							<input type=hidden value="${customerBackUp.examinationOpinions}"
								name="customerBackUp.examinationOpinions" />
								
								
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
						<input id="file_contract_id" name="file_contract_id" type="file" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>
						    <input id="backgroundImage" name="customerBackUp.contract" value="${customerBackUp.contract}" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
						</td>
						<td align="right">
							营业执照存储路径：
						</td>
						<td>
							<input id="file_business_id" name="file_contract_id" type="file" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>
						    <input id="backgroundBusinessImage" name="customerBackUp.businessLicence" value="${customerBackUp.businessLicence}" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
						
						</td>
					</tr>
					<tr>
						<td align="right">合同扫描件缩略图：</td>
						<td>
						  <!-- <img id="materialViewDivImg" src="<%=path%>/" style="background-repeat:no-repeat; width:345px;height:250px;"/> -->
							<img  style="display:none" id="materialViewDivImg" src="${viewPath}/${customerBackUp.contract}" width="250px" height="150px"/>
							<input id="materialViewDivImgpdf" name="materialViewDivImgpdf" value="${viewPath}/${customerBackUp.contract}" type="hidden"/>
							<input type="button" class="btn" value="预览" onclick="preview('materialViewDivImgpdf');" />
						
						</td>
						<td align="right">营业执照缩略图：</td>
						<td>
						   <img  style="display:none" id="lookContractPic" src ="${viewPath}/${customerBackUp.businessLicence}" width="250px" height="150px"/>
						  <input id="lookContractPicpdf" name="lookContractPicpdf" value="${viewPath}/${customerBackUp.businessLicence}" type="hidden"/>
							<input type="button" class="btn" value="预览" onclick="preview('lookContractPicpdf');" />
						
						</td>
					</tr>
					<tr>
						<td align="right">
							<font style="color: red;">*</font>客户代码：
						</td>
						<td>
							<input  style="width:250px" type="text" value="${customerBackUp.clientCode}" readonly/>
							<font style="color: red;"><span id="clientCodeF"></span></font>
						</td>
						<td align="right">
							<font style="color: red;">*</font>广告商名称：
						</td>
						<td>
							<input  style="width:250px" onkeypress="return validateSpecialCharacter();" maxlength="30" type="text" id="advertisersName"
								value="${customerBackUp.advertisersName}"
								name="customerBackUp.advertisersName" class="e_input"
								onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" />
							<font style="color: red;"><span id="advertisersNameF"></span></font>
						</td>
					</tr>
					
					
					<tr>
						<td align="right">
							广告商级别：
						</td>
						<td>
						<select id="customerBackUp.customerLevel" name="customerBackUp.customerLevel" >
							<option value="1"							
							<c:if test='${customerBackUp.customerLevel ==1}'>
							selected
							</c:if>
							 >国级</option>
							<option value="2"
							<c:if test='${customerBackUp.customerLevel ==2}'>
							selected
							</c:if>
							 >省级</option>
							<option value="3"
							<c:if test='${customerBackUp.customerLevel ==3}'>
							selected
							</c:if>
							 >市级</option>
							<option value="4"
							<c:if test='${customerBackUp.customerLevel ==4}'>
							selected
							</c:if>
							 >其他</option>
						</select>
						</td>
						<td align="right">
							<font style="color: red;">*</font>注册资金（万元）：
						</td>
						<td>
							<input type="text" name="customerBackUp.registerFinancing" id="customerBackUp.registerFinancing" value="${customerBackUp.registerFinancing}" />
							<font style="color: red;"><span id="registerFinancing"></span></font>
						</td>
					</tr>
					
					<tr>
						<td align="right">
							注册地：
						</td>
						<td>
							<input  style="width:250px" onkeypress="return validateSpecialCharacter();" maxlength="30" type="text" name="customerBackUp.registerAddress" id="customerBackUp.registerAddress" value="${customerBackUp.registerAddress}" />
						</td>
						<td align="right">
							<font style="color: red;">*</font>营业面积（平米）：
						</td>
						<td>
							<input  style="width:250px" onkeypress="return validateSpecialCharacter();" maxlength="10" type="text" name="customerBackUp.businessArea" id="customerBackUp.businessArea" value="${customerBackUp.businessArea}"/>
							<font style="color: red;"><span id="businessArea"></span></font>
						</td>
					</tr>
					
					
					
					
					
					
					<tr>
						<td align="right">
							<font style="color: red;">*</font>联系人：
						</td>
						<td>
							<input  style="width:250px" onkeypress="return validateSpecialCharacter();" maxlength="10" type="text" id="communicator"
								value="${customerBackUp.communicator}"
								name="customerBackUp.communicator" class="e_input"
								onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" />
							<font style="color: red;"><span id="communicatorF"></span></font>
						</td>
						<td align="right">
							<font style="color: red;">*</font>电话：
						</td>
						<td>
							<input  style="width:250px" onkeypress="return validateSpecialCharacter();" maxlength="20" type="text" id="tel" value="${customerBackUp.tel}"
								name="customerBackUp.tel" class="e_input"
								onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" />
							<font style="color: red;"><span id="telF"></span></font>
						</td>
					</tr>
					<tr>
						<td align="right">
							手机：
						</td>
						<td>
							<input  style="width:250px" onkeypress="return validateSpecialCharacter();" maxlength="20" type="text" id="mobileTel"
								value="${customerBackUp.mobileTel}"
								name="customerBackUp.mobileTel" class="e_input"
								onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" />
						
						</td>
						<td align="right">
							传真：
						</td>
						<td>
							<input  style="width:250px" onkeypress="return validateSpecialCharacter();" maxlength="20" type="text" value="${customerBackUp.fax}"
								name="customerBackUp.fax" class="e_input"
								onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" />
							
						</td>
					</tr>

					<tr>
						<td align="right">
							<font style="color: red;">*</font>通讯地址：
						</td>
						<td>
							<input  style="width:250px" onkeypress="return validateSpecialCharacter();" maxlength="30" type="text" id="contacts"
								value="${customerBackUp.contacts}"
								name="customerBackUp.contacts" class="e_input"
								onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" />
									<font style="color: red;"><span id="contactsF"></span></font>
						</td>
						<td align="right">
							<font style="color: red;">*</font>合作期限：
						</td>
						<td>
							<input  style="width:250px" onkeypress="return validateSpecialCharacter();" maxlength="20" type="text" value="${customerBackUp.cooperationTime}"
								id="cooperationTime" name="customerBackUp.cooperationTime" class="e_input"
								onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" />
								<font style="color: red;"><span id="cooperationTimeF"></span></font>
						</td>
					</tr>
					<tr>
						<td align="right">
							公司地址：
						</td>
						<td>
							<input  style="width:250px" onkeypress="return validateSpecialCharacter();" maxlength="30" type="text" value="${customerBackUp.conpanyAddress}"
								name="customerBackUp.conpanyAddress" class="e_input"
								onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" />
						</td>
						<td align="right">
							公司网址：
						</td>
						<td>
							<input  style="width:250px" maxlength="30" type="text" value="${customerBackUp.conpanySheet}"
								name="customerBackUp.conpanySheet" class="e_input"
								onfocus="this.className='e_inputFocus'"
								onblur="this.className='e_input'" />
						</td>
					</tr>
					<tr>
						<td align="right">
							描述：
						</td>
						<td>
							<textarea  style="width:250px" onkeypress="return validateSpecialCharacter();" rows="6" name="customerBackUp.remark">${customerBackUp.remark}</textarea>
						</td>
						<td align="right">
							审核意见：
						</td>
						<td>
							<textarea  style="width:250px" rows="6" 
								disabled>${customerBackUp.examinationOpinions}</textarea>
						</td>
					</tr>

					<tr>
						<td align="center" colspan="4">
							<input type="button" class="btn" value="保存" onclick="go();" />
							<input type="button" class="btn" value="返回" onclick="goBack();" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
