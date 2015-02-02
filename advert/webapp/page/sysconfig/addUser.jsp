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
	var roleType ;
	//返回
	function goBack() {
		window.location.href="queryUserList.do";
	}

	/** 判断选择的角色类型 */
	function judgeRoleType(){
		var roleId = $('#roleId').val();
		if( roleId != ''){
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
		    	   if(result=='2'){
		    	   		roleType = '2';
						$('#customer_div_id').show();
						$('#customer_div_id_single').hide();
						//清空广告位和区域码
						$('#user_position_ids').val("");
						$('#user_area_codes').val("");
						
					}else if(result=='1'){
						roleType = '1';
						$('#customer_div_id').hide();
						$('#customer_div_id_single').show();
						//清空广告商
						$('#user_customer_ids').val("");
					}else{
						$('#customer_div_id').hide();
						$('#customer_div_id_single').hide();
						//清空广告位和区域码
						$('#user_position_ids').val("");
						$('#user_area_codes').val("");
					}
			   }  
		   }); 
		}
	}

	//选择广告商
	function showCustomer(){
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
			if(roleType == '1'){
				$('#user_customer_names1').val(user_customer_names);
			}
        }
	}

	function addUser() {
		if(checkFrom()){
			checkLoginName();
		}
	}

	//表单验证
	function checkFrom(){
		if($.trim($("#userName").val())==''){
			alert("请输入用户名称！");
			$("#userName").focus();
			return false;
		}
		
		if(validateSpecialCharacterAfter($("#userName").val())){
			alert("用户名称不能包括特殊字符！");
			$("#userName").focus();
			return false;
		}
		
		if($.trim($("#loginName").val()) == ''){
			alert("请输入登陆名称！");
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
		if($("#user_customer_ids").val()==''){
			if(roleType == '1'){
				alert("请选择广告商！");
				return false;
			}
		}
		
		if('' != $.trim($("#email").val())){
			 var mail_reg = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*$/;
			 if(!mail_reg.test($("#email").val())){
				 alert("请正确输入邮箱地址 ！");
				 $("#email").focus();
				 return false;
			 }
		}
		
		if($("#roleId").val() == 2 && $("#user_positions").val() == '' ){
			alert("请选择广告位！");
			$("#user_positions").focus();
			return false;
		}
		
		if($("#roleId").val() == 2 && $("#user_area_names").val() == '' ){
			alert("请选择区域！");
			$("#user_area_names").focus();
			return false;
		}
		return true;
	}

	/**
	* 检查登录名是否存在
	* return  true存在   false不存在
	*/
	function checkLoginName(){
		var loginName = $("#loginName").val();
		$.ajax({   
		       url:'checkLoginName.do',       
		       type: 'POST',    
		       dataType: 'text',   
		       data: {
		    	   loginName:loginName
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
	function showAreas(){
		var user_area_codes = $('#user_area_codes').val();
		var url = "<%=path %>/page/sysconfig/getUserAreas.do?user_area_codes="+user_area_codes;
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
</script>
</head>
<body class="mainBody">
<form action="addUser.do" method="post" id="saveForm">
<div class="detail">
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
	 <c:if test="${message != null}">
            <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
    </c:if>
</div> 
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">用户添加</td>
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
		<td colspan="3">
           <select id="roleId" name="user.roleId" onchange="judgeRoleType()">
				<option  value="">请选择...</option>
				<c:forEach items="${roleList}" var="role">
					<option value="${role.roleId}" >${role.name}</option>
				</c:forEach>
	       </select>    
	    </td>
	</tr>
	<input id="user_customer_ids" name="user.customerIds" type="hidden" value="${user.customerIds}" /> 
	<input id="user_position_ids" name="user.positionIds" type="hidden" value="${user.positionIds}" /> 
	<input id="user_area_codes" name="user.areaCodes" type="hidden" value="${user.areaCodes}" />
	
	<tr id="customer_div_id_single" style="display:none;" class="sec">
			<td align="right" ><span class="required">*</span>绑定广告商：</td>
			<td colspan="3">
				<input id="user_customer_names1" class="e_input_add" readonly="readonly" onclick="showCustomer();"/>
			</td>
	</tr>
	<tr id="customer_div_id" style=display:none;" class="sec">
		<td align="right"><span class="required">*</span>指定广告位：</td>
		<td>
			<textarea rows="14" cols="24" id="user_positions" readonly="readonly" onclick="showPostions();"></textarea>
		</td>
		<td align="right"><span class="required">*</span>选择区域信息：</td>
		<td>
			<textarea rows="14" cols="24" id="user_area_names" readonly="readonly" onclick="showAreas();">${user.areaNames}</textarea>
		</td>
	</tr>
	<tr >
		
	</tr>	
</table>
<div style="margin-left:50px;">
	<input type="button" onclick="addUser();" class="btn" value="确认" /> &nbsp;&nbsp; 
	<input type="button" onclick="goBack();" class="btn" value="取消" />
</div>
</div>
</form>
</body>
</html>