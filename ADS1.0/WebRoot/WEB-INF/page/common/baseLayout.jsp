<%--
  Created by IntelliJ IDEA.
  User: Hemeijin
  Date: 2011-7-22
  Time: 20:48:05
  To change this template use File | Settings | File Templates.
--%>


<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<link id="maincss" href="../css/main.css" rel="stylesheet" type="text/css" media="all"/>
<html>
<head>
    <title><tiles:getAsString name="title"/></title>
    <style type="text/css">
    <!--
    .STYLE1 {
        font-size: 24px;
        font-family: "微软雅黑";
    }

    .STYLE2 {
        font-size: 14px;
        font-family: "微软雅黑";
    }

    .STYLE4 {
        font-size: 12px;
        font-family: "微软雅黑";
        color: #FFFFFF;
    }

    -->
</style>
</head>
<body style="text-align:center;" >
<table width="1024px" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td colspan="2">
            <tiles:insertAttribute name="header"/>
        </td>
    </tr>
    <tr>
        <td height="550px" width="178px" valign="top" align="left">
            <div style="overflow-y:scroll;overflow-x:scroll; height:100%;width:110%; display:block;" background="<%=request.getContextPath()%>/css/img/xt_07.jpg"><tiles:insertAttribute name="menu"/></div>
        </td>
        <td width="828px" valign="top" align="left">
            <div><tiles:insertAttribute name="body"/></div>
        </td>
    </tr>
    <tr height="28px" valign="top">
        <td colspan="2">
            <tiles:insertAttribute name="footer"/>
        </td>
    </tr>
</table>
</body>
</html>

