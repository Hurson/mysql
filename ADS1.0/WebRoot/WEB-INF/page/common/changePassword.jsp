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
    <link id="maincss" href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css" media="all"/>
<script type='text/javascript' src='<%=request.getContextPath()%>/js/avit.js'></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
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
				alert('<s:text name="enter.old.password" />');
				$("oldPassword").focus();
				$("oldPassword").style.borderColor = "red";
				return ;
			} else {
			    $("oldPassword").style.borderColor = "";
			}
			if($("newPassword").value==null || $("newPassword").value==""){
				alert('<s:text name="enter.new.password" />');
				$("newPassword").focus();
				$("newPassword").style.borderColor = "red";
				return ;
			} else {
			    $("newPassword").style.borderColor = "";
			}
			if($("newPasswordCheck").value==null || $("newPasswordCheck").value==""){
				alert('<s:text name="enter.new.password.check" />');
				$("newPasswordCheck").focus();
				$("newPasswordCheck").style.borderColor = "red";
				return ;
			} else {
			    $("newPasswordCheck").style.borderColor = "";
			}
			if($("newPasswordCheck").value != $("newPassword").value){
				alert('<s:text name="new.password.check.not.accord" />');
				$("newPassword").focus();
				$("newPasswordCheck").focus();
				$("newPassword").style.borderColor = "red";
				$("newPasswordCheck").style.borderColor = "red";
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
  
<body>
  
  <br/>
    <c:if test="${message != null}">
        <div id="message_in_phase" style="padding-left:3%;font-weight:bold;">
            <span style="color:red;">${message}</span>
        </div>
    </c:if>
    
<form action="changePasswordCheck.action" method="post" id="queryForm" >
	<s:token></s:token>
	<input type="hidden" name="userId" value='${userInfo.id}'/>
    <table border="0" cellpadding="0" cellspacing="0" class="box w90">
      <tr>
         <td class="title">
              <div id="modAddMsg"><s:text name="user.change.password"/></div>
         </td>
      </tr>
      <tr>
         <td bgcolor="#ecf0f1" class="content">
           <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="list">
            <tr>
               <td width="10" align="center">&nbsp;</td>
               <td width="100" align="left">
                    <s:text name="user.username" /><s:text name="common.colon"/>
               </td>
               <td width="288">
                    <c:out value="${userInfo.userName}"/>
               </td>
               <td width="222">
               </td>
            </tr>
            <tr>
              <td align="center">&nbsp;</td>
              <td align="left">
                    <s:text name="user.old.password" /><s:text name="common.colon"/>
              </td>
              <td>
                    <input type="password" id="oldPassword" name="oldPassword" class="input_style padding_left"/>
               </td>
               <td>
                     <span class="hint"><span><s:text name="common.must"/></span></span>
               </td>
             </tr>
             <tr>
                <td align="center">&nbsp;</td>
                <td align="left">
                     <s:text name="user.new.password" /><s:text name="common.colon"/>
                </td>
                <td>
                     <input type="password" id="newPassword" name="newPassword" class="input_style padding_left" />
                </td>
                <td>
                     <span class="hint"><span><s:text name="common.must"/></span></span>
                 </td>
             </tr>
             <tr>
                 <td align="center">&nbsp;</td>
                 <td align="left"> 
                      <s:text name="user.new.password.check" /><s:text name="common.colon"/>
                 </td>
                 <td>
                      <input type="password" id="newPasswordCheck" name="newPasswordCheck" class="input_style padding_left"/>
                 </td>
                 <td>
                      <span class="hint"><span><s:text name="common.must"/></span></span>
                 </td>
              </tr>
              <tr>
              	  <td align="center">&nbsp;</td>
                  <td align="right">
                      <input class="btn_big" type="button" value='<s:property value="%{getText('user.submit')}"/>'
                                                onclick="javascript:checkPassword();"/>
                  </td>
                  <td align="left">
                      &nbsp;
                      <input class="btn_big" type="button"  onclick="resetAllPassword('queryForm');" value='<s:property value="%{getText('user.reset')}"/>'/>
                  </td>
                  <td></td>
                </tr>
             </table>
           </td>
        </tr>
      </table>
  </form>      
</body>
</html>
