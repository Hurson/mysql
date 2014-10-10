<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<body background="<%=request.getContextPath()%>/images/main_20.gif" style="width:823px;">
<center>
<table>
	<tr><td>&nbsp;</td></tr>
	<tr><td>&nbsp;</td></tr>
	<tr><td>&nbsp;</td></tr>
	<tr><td>&nbsp;</td></tr>
	<tr><td>&nbsp;</td></tr>
    <tr>
        <td >
        	<font size="20">
        		<s:property value="%{getText('menu.welcome')}"/><s:property value="%{getText('login.title')}"/>
        		<s:property value="%{getText('system.application')}"/>
        	</font>
        </td>
    </tr>
</table>
</center>
</body>
</html>

