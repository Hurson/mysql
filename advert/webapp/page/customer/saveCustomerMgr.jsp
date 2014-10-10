<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

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
<script type="text/javascript" src="<%=path%>/js/customerJS.js"></script>

<script type="text/javascript"><!--
	function goBack(){
		window.location.href="adCustomerMgr_list.do";
	}
	
		//表单验证
		function go(){
			var clientCode = document.getElementById("clientCode").value;
			var advertisersName = document.getElementById("advertisersName").value;
			var communicator = document.getElementById("communicator").value;
			var tel = document.getElementById("tel").value;
			var cooperationTime = document.getElementById("cooperationTime").value;
			var contacts = document.getElementById("contacts").value;
			var localContractFilePath = document.getElementById("file_contract_id").value;
			var localBusinessFilePath = document.getElementById("file_business_id").value;
			
			//验证上传合同扫描件不能为空
			if(localContractFilePath == "" || localContractFilePath == null || localContractFilePath == undefined){
				alert("上传的文件不能为空!");
				return;
			}
			//验证营业执照不能为空			
			if(localBusinessFilePath == "" || localBusinessFilePath == null || localBusinessFilePath == undefined){
				alert("上传的文件不能为空!");
				return;
			}
			
			if(clientCode == ""){
				document.getElementById("clientCodeF").innerHTML ="客户代码不能为空！";
				return false;
			}else{
				document.getElementById("clientCodeF").innerHTML ="";
				if(advertisersName == ""){
			       document.getElementById("advertisersNameF").innerHTML ="广告商名称不能为空！";
					return false;
				}else{
					document.getElementById("advertisersNameF").innerHTML ="";
					if(communicator == ""){
						document.getElementById("communicatorF").innerHTML ="联系人不能为空！";
						return false;
					}else{
						document.getElementById("communicatorF").innerHTML ="";
						if(tel == "" ){
							document.getElementById("telF").innerHTML ="电话不能为空！";
							return false;
						}else{
							document.getElementById("telF").innerHTML ="";
							if(cooperationTime == ""){
							   document.getElementById("cooperationTimeF").innerHTML ="合作期限不能为空！";
							   return false;
							}else{
								document.getElementById("cooperationTimeF").innerHTML ="";
								if(contacts == ""){
									document.getElementById("contactsF").innerHTML ="通讯地址不能为空！";
									return false;
								}else{
									document.getElementById("contactsF").innerHTML ="";
									
									 $.ajax( {
									 type : 'post',  
									 url : 'adCustomerMgr_saveValidateCustomer.do',
									 data : 'clientCode=' + clientCode + '&localContractFilePath=' + localContractFilePath +'&localBusinessFilePath=' + localBusinessFilePath + '&date=' + new Date(),
							         success : function(msg) {
										 if(msg =="1"){
											document.getElementById("clientCodeF").innerHTML ="客户代码已存在，请重新输入！";
										 }else{
										 	//提交
											$("#form").submit();
									     }
							         }	    
					              });
								}
							}
						}
					}
				}
			}
		}
		
		function playButton(){
			
			var imagePage = document.getElementById("file_contract_id").value;
			
			alert("imagePage="+imagePage);
		
			var pic = document.getElementById("picture");
		//   pic.src = "<%=path%>/uploadFiles/contractScanFiles/aaa123456/1.jpg";
		  	pic.src = imagePage;
			}
		
		 function  show(){   
		//var   p=document.getElementById("file1").value;  
		//document.getElementById("s").innerHTML="<input id=pic type=image height=96 width=128 /> ";   
		//document.getElementById("pic").src=p;
			  alert(this.value);    
		  }   
		
		
		function getImage(){
		//	alert("getImage");
			var imagePage = document.getElementById("file_contract_id").value;
			alert("imagePage="+imagePage);
			document.getElementById("imge").src = imagePage;
			
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
<form id="form" action="adCustomerMgr_insertCustomerBackUp.do"  method="post" name="form1" enctype="multipart/form-data"> 
<table cellpadding="0" cellspacing="0" border="0" width="100%" >
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
				<td class="formTitle" colspan="99"><span>添加客户信息</span></td>
			</tr>
			
			<tr>
				<td class="td_label">客户状态：</td>
				<td class="td_input">&nbsp;&nbsp;待审核</td>
				<td class="td_label"></td>
				<td class="td_input"></td>
			</tr>
			
			<tr>
				<td class="td_label">客户代码：</td>
				<td class="td_input"><input type="text" id="clientCode" name="customerBackUp.clientCode" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /><font style="color:red;"><span id = "clientCodeF"></span>*</font></td>
				<td class="td_label">广告商名称：</td>
				<td class="td_input"><input type="text" id="advertisersName" name="customerBackUp.advertisersName" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /><font style="color:red;"><span id = "advertisersNameF"></span>*</font></td>
			</tr>	
			<tr>
				<td class="td_label">联系人：</td>
				<td class="td_input"><input type="text" id="communicator" name="customerBackUp.communicator" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /><font style="color:red;"><span id ="communicatorF"></span>*</font></td>
				<td class="td_label">电话：</td>
				<td class="td_input"><input type="text" id="tel" name="customerBackUp.tel" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /><font style="color:red;"><span id ="telF"></span>*</font></td>
			</tr>
			<tr>
			<td class="td_label">合作期限：</td>
				<td class="td_input"><input type="text" id="cooperationTime" name="customerBackUp.cooperationTime" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /><font style="color:red;"><span id ="cooperationTimeF"></span>*</font></td>
				<td class="td_label">通讯地址：</td>
				<td class="td_input"><input type="text" id="contacts" name="customerBackUp.contacts" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /><font style="color:red;"><span id ="contactsF"></span>*</font></td>
			</tr>
			<tr>
			<td class="td_label">传真：</td>
				<td class="td_input"><input type="text"  name="customerBackUp.fax" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /></td>
			<td class="td_label">手机：</td>
				<td class="td_input"><input type="text" id="mobileTel" name="customerBackUp.mobileTel" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /></td>
			</tr>				
			<tr>
			   <td class="td_label">公司地址：</td>
			   <td class="td_input"><input type="text" name="customerBackUp.conpanyAddress" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /></td>
			   <td class="td_label">公司网址：</td>
			   <td class="td_input"><input type="text" name="customerBackUp.conpanySheet" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /></td>
		   </tr>
			
			<tr>
					<td class="td_label">描述：</td>
					<td class="td_input"><textarea id="" rows="4" cols="30" name="customerBackUp.remark"></textarea></td>
					<td class="td_label"></td>
				    <td class="td_input"></td>
			</tr>
			
			<tr>
					<td class="td_label">上传合同扫描件：</td>
					<td class="td_input">
					    <input title="浏览..." value="" onpropertychange="show();" id="file_contract_id" name="upload" class="e_inputfile" onfocus="this.className='e_inputFocusfile'" onblur="this.className='e_inputfile'" type="file"/>
					</td>
					<td class="td_label">上传营业执照：</td>
					<td class="td_input">  
                   <input title="浏览..."  value="" id="file_business_id" name="upload" class="e_inputfile" onfocus="this.className='e_inputFocusfile'" onblur="this.className='e_inputfile'" type="file"/></td>
			</tr>
			<tr>
					<td class="td_label" rowspan="1">预览合同扫描件：</td>
					<td class="td_input" rowspan="1"><img  id="picture" src="<%=path%>/uploadFiles/contractScanFiles/1.jpg" height="255px" width="260px"/></td>
					<td class="td_label" rowspan="1">预览营业执照：</td>
					<td class="td_input" rowspan="1"><img  src="<%=path%>/uploadFiles/contractScanFiles/1.jpg" height="255px" width="260px"/></td>
			</tr>
			
				<tr>
					<td class="formBottom" colspan="99" align="center">
					<!-- <input name="Submit" type="submit" title="确定"  value="确&nbsp;&nbsp;定" onfocus=blur() /> -->
					<input name="Submit" type="button" title="确定"  value="确&nbsp;&nbsp;定" onfocus=blur() onclick="go()" />
					    <input type="button" title="返回" onfocus=blur() value="返&nbsp;&nbsp;回" onclick="goBack()" />
					</td>
				</tr> 
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
