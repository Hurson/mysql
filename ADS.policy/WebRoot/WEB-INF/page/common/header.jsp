<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<script type="text/javascript">

	//返回若系统列表为空，则隐藏切换系统的下拉框
	function init(){
		var applicationList = "${applicationList}"; //若applicationList的size为0，则此处applicationList = "[]"
		if(applicationList == null || applicationList == "[]"){
			document.getElementById("td_applicationList").style.display = "none"; //此处若设置display="none",这样下拉框的位置会被占用
		}
	}
	
	function changeApplication(url){
		if(url == ""){
			return;
		}
		url = url+"/login.action?username=${userInfo.userName}&password=${userInfo.password}";
		parent.location=url;
	}
	
	function changePassword(userId){
		var url = "changePassword.action?userId="+userId;
		parent.frames['mainFrame'].location = url;
	}
	
	function logout(userId){
		var url = "logout.action?userId="+userId;
		parent.location = url;
	}
	
</script>
<link id="maincss" href="<%=request.getContextPath()%>/css/header.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body onload="init();" style="width:1024px">

<table width="100%" height="88px" border="0" cellspacing="0" cellpadding="0">
  <tr>
     <td width="295" valign="top"><img src="<%=request.getContextPath()%>/images/top_bg_01.jpg" width="295" height="88" border="0" /></td>
     <td valign="bottom">
         <table width="100%" height="22px" border="0" cellspacing="0" cellpadding="0">
           <tr>
               <td width="160">MMSP-<s:property value="%{getText('system.application')}"/>  V${version}</td>
               <td class="user_btn">
                       <a href="javascript:logout('${userInfo.id}')"><s:property value="%{getText('user.logout')}"/></a>
                       <img src="<%=request.getContextPath()%>/images/top_1.png" width="15" height="23" border="0" />
                       <a href="javascript:changePassword('${userInfo.id}');"><s:property value="%{getText('user.edit.password')}"/></a>
                       <img src="<%=request.getContextPath()%>/images/top_3.png" width="15" height="23" border="0" />
                       <a href="#"><s:property value="%{getText('menu.welcome')}"/> ${userInfo.userName}</a>
                       <img src="<%=request.getContextPath()%>/images/top_2.png" width="15" height="23" border="0" />
              </td>
              <td width="116"  align="right" id="td_applicationList">
  					    <select name="application"  id="application"  onchange="javascript:changeApplication(this.value);" style="width:85px;">
                    		 <option value=""><s:text name="common.please.select"/></option>
                    		 <c:if test="${applicationList != null }">
               					 <c:forEach items="${applicationList}" var="application">
		                    		<option value="${application.url}">${application.name}</option>
		                    	</c:forEach>
		                    </c:if>
		                </select>
              </td>
              <td width="31"></td>
           </tr>
           <tr>
              <td height="15" colspan="5"></td>
           </tr>
        </table>
    </td>
  </tr>
</table>
</body>
</html>
