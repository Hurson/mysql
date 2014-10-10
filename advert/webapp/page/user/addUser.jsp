<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/new/main.css" />
		<link href="<%=path%>/css/new/common/dhtmlx/tree/css/dhtmlxtree.css"
			rel="stylesheet" type="text/css" media="all" />
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxcommon.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxtree.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/test.js"
			type="text/javascript">
</script>

<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery-1.4.4.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.messages_cn.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>

<script language="javascript" type="text/javascript" src="<%=path %>/js/user/addUser.js"></script>

<!-- 树的结构   -->
<link href="TreePanel.css" rel="<%=path %>/common/tree/stylesheet" type="text/css"/>
<script type=text/javascript src="<%=path %>/common/tree/common-min.js"></script>
<script type=text/javascript src="<%=path %>/common/tree/TreePanel.js"></script>
<script type=text/javascript src="<%=path %>/common/tree/china_2.js"></script>
<!-- 树的结构 end -->

	<script>
			//返回
function goBack() {
		window.location.href="userList.do?method=getAllUserList";
}
</script>


</head>
<body class="mainBody" onload="loadRole()">
  <input id="projetPath" type="hidden" value="<%=path%>" />
		<form id="saveForm" action="" method="post" name="saveForm">
				<div class="path">
					<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
					首页 >> 系统管理 >> 用户添加
				</div>
		<div class="searchContent" >
			<div class="listDetail" style="position: relative">
		    	<table>
		    		<tr>
		    			<td>
		        		<table cellspacing="1" class="content" align="left" style="margin-bottom: 30px">
			            <tr class="title">
			                <td colspan="4">用户信息</td>
			            </tr>
			            <tr>
			                <td width="15%" align="right"><span class="required">*</span>用户名称：</td>
			                <td width="35%">
			                	<input type="text" id="user_name" name="user.username" maxlength="100"/>
			                </td>
			                <td width="15%" align="right"><span class="required">*</span><div  id="confirm_div_id" style="display:none;float: right; width:80px;  height:22px; line-height:22px; padding-right:47px;"><span style="color: red;size: 2px;">登陆名已存在</span></div>登陆名称：</td>
			                <td width="35%">
				              	<input id="user_loginname" name="user.loginname" type="text" maxlength="100"/>
			                </td>
			            </tr>		     
			             <tr>
			                <td width="15%" align="right"><span class="required">*</span>用户密码：</td>
			                <td width="35%">
			                	<input id="user_password" name="user.password" type="text"  maxlength="80"/>
			                </td>
			                <td width="15%" align="right">电子邮箱：</td>
			                <td width="35%">
				              	<input id="user_email" name="user.email" type="text"  maxlength="80"/>
			                </td>
			            </tr>	
			             <tr>
			                <td width="15%" align="right"><span class="required">*</span>选择角色：</td>
			                <td colspan="3">
			                   <select id="user_roles_id" name="user_roles_name" onchange="judgeRoleType()">
									<option  title="-1" value="-1">请选择...</option>
						      </select>    
						    </td>
			            </tr>  
			            
							<tr id="customer_div_id"
												<c:choose>
													   <c:when test="${role.type == 2 }">style="display:;"</c:when>
													   <c:otherwise>style="display:none;"</c:otherwise></c:choose>>
				
										<td width="15%" align="right"><span class="required">*</span>选择广告商：</td>
										<td width="35%"><input id="sel_customer_ids"
											type="hidden" value="${userBandCusids }" /> 
											
											<textarea rows="14" cols="24" id="user_customers_id">
												${userBandCusNames }
											</textarea>
										</td>
										<td width="15%" align="right">选择区域：</td>
										<td width="35%" ><input id="sel_location_ids"
											type="hidden" value="${userBandLocCode }" /> <!-- 
												<input id="user_locations_id"  value="${userBandLocName }" class="e_input_add"
													onfocus="this.className='e_inputFocus'"
													onblur="this.className='e_input_add'" />	
													
													-->
											 <textarea rows="14" cols="24" id="user_locations_id">
												${userBandLocName }
											</textarea>
										</td>
									</tr>
								
							
								
									 <tr id="customer_div_id_single"  
								<c:choose>
									<c:when test="${role.type == 1 }">style="display:;"</c:when>
									<c:otherwise>style="display:none;"</c:otherwise>
								</c:choose>>
				
										<td width="15%" align="right" ><span class="required">*</span>绑定广告商：</td>
										<td width="85%" colspan="3">
											<input id="sel_customer_ids" type="hidden" value="${userBandCusids }" /> 
											<input  id="user_customers_id_single" value="${userBandCusNames }"   class="e_input_add" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input_add'"/>
										</td>
									</tr>					
					<tr>
						<td width="20%" align="right" colspan="4">
						     <input id="addPositionButton" type="button" value="添加" onclick="firstSubmit();" class="btn" />
							 <input id="" type="button" value="返回" onclick="goBack();" class="btn" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</div>
</div>
</form>
<div id="locationDiv1" class="showDiv_2"  style="display: none;">
<table cellpadding="0" cellspacing="0"
	style="margin-top: 0; width: 750px; height: 350px; border: 1px solid #cccccc; font-size: 12px; background-color: #e7f2fc;">
	<tr class="list_title">
		<th id="title_id" style="border: 0;" align="left">&nbsp;&nbsp;·请选择区域</th>
		<td style="border: 0;" align="right"><div >
			<a href="#" onclick="saveCheckLocation();">确认</a>&nbsp;
			<a href="#" onclick="closeSelectDiv('locationDiv1');">返回</a></div>
		</td>
	</tr>
	<tr>
		<td colspan='2'  id="treeList">
		</td>
	</tr>
</table>
</div>
<div id="roleDiv" class="showDiv_2" style="display: none;">
<table cellpadding="0" cellspacing="0"
	style="margin-top: 0; width: 750px; height: 350px; border: 1px solid #cccccc; font-size: 12px; background-color: #e7f2fc;">
	<tr class="list_title">
		<td align="left" style="padding-left: 21px;" ><input id="tick" type="checkbox" onclick="ticked()"/>全选 </td>
		<td id="title_id" style="border: 0;" align="left">绑定广告商</td>
		<td style="border: 0;" align="right">
		<a href="#" onclick="saveBanding();">确认</a>&nbsp;
		<a href="#" onclick="closeSelectDiv('roleDiv');">返回</a>
		</td>
	</tr>
	<tr>
		<td colspan='3' id="roleInfo">
		</td>
	</tr>
	
</table>
</div>
<div id="customerDiv" class="showDiv_2" style="display: none;">
<table cellpadding="0" cellspacing="0"
	style="margin-top: 0; width: 750px; height: 350px; border: 1px solid #cccccc; font-size: 12px; background-color: #e7f2fc;">
	<tr class="list_title">
		
		<td id="title_id" style="border: 0;" align="left">绑定广告商</td>
		<td style="border: 0;" align="right">
		<a href="#" onclick="saveSingleCustomer();">确认</a>&nbsp;
		<a href="#" onclick="closeSelectDiv('customerDiv');">返回</a>
		</td>
	</tr>
	<tr>
		<td colspan='2' id="customerInfo">
		</td>
	</tr>
	
</table>
</div>
<div id="bg" class="bg" style="display: none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>
</body>
</html>