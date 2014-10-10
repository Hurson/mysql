<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title><s:property value="%{getText('user.change.password')}"/></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link id="maincss"  rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" media="all"/>
	<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
	<script type="text/javascript" defer="defer">
		function resetAllPassword(formId) {
		    var form = $(formId);
		    for (var i = 0; i < form.elements.length; i++) {
		        // if(form.elements[i].type != "button" && form.elements[i].type != "submit" && form.elements[i].type != "reset"){
		        if (form.elements[i].type == "text" || form.elements[i].type == "radio" || form.elements[i].type == "select-one" ||form.elements[i].type == "password"|| form.elements[i].type == "hidden") {
		            if (form.elements[i].type == "radio") {
		                form.elements[i].checked = false;
		            } else {
		                form.elements[i].value = "";
		            }
		        }
		    }
		    $("oldPassword").style.borderColor = "";
		    $("newPassword").style.borderColor = "";
		    $("newPasswordCheck").style.borderColor = "";
		}
	
	
		function checkPassword(){
			if($("oldPassword").value==null || $("oldPassword").value==""){
				alert('请输入旧密码');
				$("oldPassword").focus();
				//$("oldPassword").style.borderColor = "red";
				return ;
			} else {
			    $("oldPassword").style.borderColor = "";
			}
			if($("newPassword").value==null || $("newPassword").value==""){
				alert('请输入新密码');
				$("newPassword").focus();
			//	$("newPassword").style.borderColor = "red";
				return ;
			} else {
			    $("newPassword").style.borderColor = "";
			}
			if($("newPasswordCheck").value==null || $("newPasswordCheck").value==""){
				alert('请输入校验新密码');
				$("newPasswordCheck").focus();
				//$("newPasswordCheck").style.borderColor = "red";
				return ;
			} else {
			    $("newPasswordCheck").style.borderColor = "";
			}
			if($("newPasswordCheck").value != $("newPassword").value){
				alert('新密码与校验密码相同，请重新输入');
				$("newPassword").focus();
				$("newPasswordCheck").focus();
				//$("newPassword").style.borderColor = "red";
				//$("newPasswordCheck").style.borderColor = "red";
				return;
			} else {
			    $("newPassword").style.borderColor = "";
			    $("newPasswordCheck").style.borderColor = "";
			}
			document.forms[0].submit();
		}
		
		function $(id){
			return document.getElementById(id);
		}
     	
     </script>
  </head>
  
<body class="mainBody">
  
 
    
<form action="<%=path %>/page/sysconfig/changePasswordCheck.do" method="post" id="queryForm" >
<input type="hidden" name="user.userId" value='${USER_LOGIN_INFO.userId}'/>
<div class="path">首页 >> 修改密码</div>

<div class="searchContent" >

	<div class="listDetail">
	  <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
<c:if test="${message != null}">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red;">${message}</span>
</c:if>
</div>
		<div style="position: relative">	
			<table>
		    	<tr>
		    	   <td>
		               <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px">
		                 <tr class="title"><td colspan="2">修改密码</td></tr>
		                 
		                 <tr> <td width="10%" align="right"> 用户名:</td>
				               <td width="90%">
				                    <c:out value="${USER_LOGIN_INFO.userName}"/>
				               </td>
				        </tr>
				        <tr>
				        <td align="right">
		                    <span class="required">*</span>旧密码:
		               </td>
		               <td>
		                    <input type="password" id="oldPassword" name="oldPassword" class="input_style padding_left"/>
		               </td>
		         
				        </tr>
				        <tr>
				         <td align="right">
			                     	<span class="required">*</span>新密码:
			                </td>
			                <td>
			                     <input type="password" id="newPassword" name="newPassword" class="input_style padding_left" />
			                </td>
				        </tr>
				        <tr>
				         <td align="right"> 
		                     <span class="required">*</span> 校验新密码:
		                 </td>
		                 <td>
		                      <input type="password" id="newPasswordCheck" name="newPasswordCheck" class="input_style padding_left"/>
		                 </td>
				        </tr>
				        <tr>
				         <td colspan="2">
				         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <input class="btn" type="button" value='<s:property value="%{getText('user.submit')}"/>'
                                                onclick="javascript:checkPassword();"/>
                      </td>
                  
				        </tr>
						</table>
				 </td>
				 </tr>
			</table>
		</div>
	</div>
</div>
				 



  </form>      
</body>
</html>
