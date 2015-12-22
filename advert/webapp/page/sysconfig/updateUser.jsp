<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"	href="<%=path%>/css/new/main.css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<style>
	.e_input_add {
		background:url(<%=path%>/images/add.png) right no-repeat #ffffff;
	}
</style>
<script>
	var roleType = ${user.roleType};
	//返回
	function goBack() {
		window.location.href="queryUserList.do";
	}

	/** 判断选择的角色类型 */
	function judgeRoleType(){
		var roleId = $('#roleId').val();
		if( roleId != '' || roleId == null){
			$.ajax({   
		       url:'getRoleType.do',       
		       type: 'POST',    
		       dataType: 'text',   
		       data: {
		    	   roleId:roleId
				},                   
		       timeout: 1000000000,                              
		       error: function(){                      
		    		alert("系统错误，请联系管理员！");
		       },    
		       success: function(result){ 
		       	   roleType = result;
		    	   if(result=='2'){
						$('#customer_div_id').show();
						$('#user_dtmb_position_div').show();
						$('#customer_div_id_single').hide();
						//清空广告位和区域码
						$('#user_position_ids').val("");
						$('#dtmb_position_ids').val("");
						$('#user_area_codes').val("");
					}else if(result=='1'){
						$('#customer_div_id').hide();
						$('#user_dtmb_position_div').hide();
						$('#customer_div_id_single').show();
						//清空广告商
						$('#user_customer_ids').val("");
					}else{
						$('#customer_div_id').hide();
						$('#user_dtmb_position_div').hide();
						$('#customer_div_id_single').hide();
						//清空广告位和区域码
						$('#user_position_ids').val("");
						$('#user_area_codes').val("");
						$('#dtmb_position_ids').val("");
					}
			   }  
		   }); 
		}
	}

	//选择广告商
	function showCustomer(roleType){
		var user_customer_ids = $('#user_customer_ids').val();
		var url = "showCustomerList.do?roleType="+roleType+"&user_customer_ids="+user_customer_ids;
		var custValue = window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
		if(custValue){
			var user_customer_ids = '';
			var user_customer_names = '';
			var cust = custValue.split(',');
			for(var i=0;i<cust.length;i++){
				if(i == cust.length-1){
					user_customer_ids += cust[i].split('_')[0];
					user_customer_names += cust[i].split('_')[1];
				}else{
					user_customer_ids += cust[i].split('_')[0]+",";
					user_customer_names += cust[i].split('_')[1]+",";
				}
			}
			$('#user_customer_ids').val(user_customer_ids);
			if(roleType == 1){
				$('#user_customer_names1').val(user_customer_names);
			}
        }
	}

	function showAreas(){
		
		var url = "<%=path %>/page/sysconfig/getUserAreas.do";
		var areaValue = window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;resizable=no;status=no;scroll=no;center=yes;");
		
		if(areaValue){
			var areas = areaValue.split(",");
			var areaCodes = "";
			var areaNames = "";
			for ( var i = 0; i < areas.length; i++) {
				var tt = areas[i].split("_");
				if(i == areas.length -1){
					areaCodes += tt[0];
					areaNames += tt[1];
				}else{
					areaCodes += tt[0]+",";
					areaNames += tt[1]+",";
				}
			}
			$("#user_area_codes").val(areaCodes);
			$("#user_area_names").val(areaNames);
		}
	}
	function updateUser() {
		if(checkFrom()){
			checkLoginName();
		}
	}

	//表单验证
	function checkFrom(){
		if($.trim($("#userName").val())==''){
			alert("请输入用户名！");
			$("#userName").focus();
			return false;
		}
		if(validateSpecialCharacterAfter($("#userName").val())){
			alert("用户名称不能包括特殊字符！");
			$("#userName").focus();
			return false;
		}
		
		if($.trim($("#loginName").val()) == ''){
			alert("请输入登陆名！");
			$("#loginName").focus();
			return false;
		}
		if(validateSpecialCharacterAfter($("#loginName").val())){
			alert("登陆名称不能包括特殊字符！");
			$("#loginName").focus();
			return false;
		}
		if($.trim($("#password").val()) ==''){
			alert("请输入密码！");
			$("#password").focus();
			return false;
		}
		if($("#roleId").val() == ''){
			alert("请选择角色！");
			$("#roleId").focus();
			return false;
		}
		if(roleType == 1 && $("#user_customer_ids").val()==''){
			alert("请选择广告商！");
			$("#user_customer_ids").focus();
			return false;
		}
		if(roleType == 2 && $("#user_positions").val() == '' ){
			alert("请选择广告位！");
			$("#user_positions").focus();
			return false;
		}
		
		if(roleType == 2 && $("#user_area_names").val() == '' ){
			alert("请选择区域！");
			$("#user_area_names").focus();
			return false;
		}
		if('' != $.trim($("#email").val())){
			 var mail_reg = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*$/;
			 if(!mail_reg.test($("#email").val())){
				 alert("请正确输入邮箱地址 ！");
				 $("#email").focus();
				 return false;
			 }
		}
		return true;
	}

	/**
	* 检查登录名是否存在
	* return  true存在   false不存在
	*/
	function checkLoginName(){
		var userId = $("#userId").val();
		var loginName = $("#loginName").val();
		$.ajax({   
		       url:'checkLoginName.do',       
		       type: 'POST',    
		       dataType: 'text',   
		       data: {
		    	   loginName:loginName,
		    	   userId:userId
				},                   
		       timeout: 1000000000,                              
		       error: function(){                      
		    		alert("系统错误，请联系管理员！");
		       },    
		       success: function(result){ 
		    	   if(result == 'false'){
		    		   $('#saveForm').submit();
			       }else{
			    	   alert("登陆名已存在，请重新输入！");
					   $("#loginName").focus();
				   }

		    	   
			   }  
		   }); 
	}

	function init(roleType,customerNames,positionNames){
		if($("#loginName").val() == "admin"){
			document.getElementById("loginName").disabled = "disabled";
			document.getElementById("userName").disabled = "disabled";
			document.getElementById("roleId").disabled = "disabled";
		}
		if(roleType == 1){
			$("#user_customer_names1").val(customerNames);
			$("#customer_div_id_single").show();
		}else if(roleType == 2){
			$("#user_positions").val(positionNames);
			$("#customer_div_id").show();
			$("#user_dtmb_position_div").show();
		}
	}
	
	function showPostions(){
		var user_positions = $('#user_position_ids').val();
		var url = "getUserAdvertPackage.do?user_positions="+user_positions;
		var custValue = window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
		if(custValue){
			var positions = custValue.split(",");
			var postionIds = "";
			var postionNames = "";
			for ( var i = 0; i < positions.length; i++) {
				var tt = positions[i].split("_");
				if(i == positions.length -1){
					postionIds += tt[0];
					postionNames += tt[1];
				}else{
					postionIds += tt[0]+",";
					postionNames += tt[1]+",";
				}
			}
			$("#user_position_ids").val(postionIds);
			$("#user_positions").val(postionNames);
		}
	}
	function showDtmbPostions(){
		var dtmb_positions = $('#dtmb_position_ids').val();
		var url = "getDtmbAdPosition.do?dtmb_positions="+dtmb_positions;
		var custValue = window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
		if(custValue){
			var positions = custValue.split(",");
			var postionIds = "";
			var postionNames = "";
			for ( var i = 0; i < positions.length; i++) {
				var tt = positions[i].split("_");
				if(i == positions.length -1){
					postionIds += tt[0];
					postionNames += tt[1];
				}else{
					postionIds += tt[0]+",";
					postionNames += tt[1]+",";
				}
			}
			$("#dtmb_position_ids").val(postionIds);
			$("#dtmb_positions").val(postionNames);
		}
	}
</script>
</head>
<body class="mainBody" onload="init(${user.roleType},'${user.customerNames}' , '${user.positionNames}') ">
<form action="updateUser.do" method="post" id="saveForm">
<input id="userId" name="user.userId" type="hidden" value="${user.userId}" />
<input id="createTime" name="user.createTime" type="hidden" value="${user.createTime}" />
<input id="delFlag" name="user.delFlag" type="hidden" value="${user.delFlag}" />
<c:if test="${user.loginName == 'admin'}">
<input name="user.userName" type="hidden" value="${user.userName}" />
<input name="user.loginName" type="hidden" value="${user.loginName}" />
<input name="user.roleId" type="hidden" value="${user.roleId}" />
</c:if>
<div class="detail">
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
	 <c:if test="${message != null}">
            <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
    </c:if>
</div> 
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">修改用户信息</td>
	</tr>
	<tr>
		<td align="right"><span class="required">*</span>用户名称：</td>
		<td>
			<input type="text" id="userName" name="user.userName" value="${user.userName}" maxlength="12" />
		</td>
		<td align="right"><span class="required">*</span>登陆名称：</td>
		<td>
			<input type="text" id="loginName" name="user.loginName" value="${user.loginName}" maxlength="12" />
		</td>
	</tr>
	<tr class="sec">
		<td align="right"><span class="required">*</span>登录密码：</td>
		<td>
			<input type="text" id="password" name="user.password" value="${user.password}" maxlength="25" />
		</td>
		<td align="right">电子邮箱：</td>
		<td>
			<input type="text" id="email" name="user.email" value="${user.email}" maxlength="50"  />
		</td>
	</tr>
	<tr>
		<td align="right"><span class="required">*</span>选择角色：</td>
		<td>
           <select id="roleId" name="user.roleId" onchange="judgeRoleType()">
				<option  value="">请选择...</option>
				<c:forEach items="${roleList}" var="role">
					<option value="${role.roleId}" <c:if test="${role.roleId == user.roleId}">selected='selected'</c:if> >${role.name}</option>
				</c:forEach>
	       </select>    
	    </td>
	    <td align="right" >用户状态：</td>
		<td> 
			 <select id="state" name="user.state">
				<option value="0"  <c:if test="${user.state=='0'}">selected='selected'  </c:if>>禁用</option>
				<option value="1"  <c:if test="${user.state=='1'}">selected='selected'  </c:if>>可用</option>
			</select>
		</td>
	</tr>
	<input id="user_customer_ids" name="user.customerIds" type="hidden" value="${user.customerIds}" /> 
	<input id="user_position_ids" name="user.positionIds" type="hidden" value="${user.positionIds}" />
	<input id="user_area_codes" name="user.areaCodes" type="hidden" value="${user.areaCodes}" />
	<input id="dtmb_position_ids" name="user.dtmbPositionIds" type="hidden" value="${user.dtmbPositionIds}" />
	
	<tr id="customer_div_id" style="display:none;" class="sec">
		
		<td align="right"><span class="required">*</span>指定广告位：</td>
		<td>
			<textarea rows="8" cols="40" id="user_positions" readonly="readonly" onclick="showPostions();">${user.positionNames}</textarea>
		</td>
		<td align="right"><span class="required">*</span>选择区域信息：</td>
		<td>
			<textarea rows="8" cols="40" id="user_area_names" readonly="readonly" onclick="showAreas();">${user.areaNames}</textarea>
		</td>
	</tr>
	<tr id="user_dtmb_position_div" style="display:none;">
		<td align="right"><span class="required">*</span>指定广告位：</td>
		<td colspan="3">
			<textarea rows="5" cols="50" id="dtmb_positions" readonly="readonly" onclick="showDtmbPostions();">${user.dtmbPositionNames}</textarea>
		</td>
	</tr>	
	<tr id="customer_div_id_single" style="display:none;" class="sec">
		<td align="right" >绑定广告商：</td>
		<td colspan="3">
			<input id="user_customer_names1" class="e_input_add" readonly="readonly" onclick="showCustomer(1);"/>
		</td>
	</tr>	
</table>
<div style="margin-left:50px;">
	<input type="button" onclick="updateUser();" class="btn" value="确认"  /> &nbsp;&nbsp; 
	<input type="button" onclick="goBack();" class="btn" value="取消" /><br>
</div>
</div>
</form>
</body>
</html>