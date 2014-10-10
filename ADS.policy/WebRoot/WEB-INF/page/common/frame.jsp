<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<title> <s:text name="system.application"/> </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="bookmark" href="favicon.ico" type="image/x-icon" /> 

</head>
<frameset cols="*,1024,*" frameborder="no" border="0" framespacing="0">
<frame src="about:blank"></frame>

<frameset id="frameset1" rows="87,*" frameborder="no" scrolling="auto"  border="0" framespacing="0">
		<frame src="head.action" name="topFrame"  scrolling="no" noresize="noresize" id="topFrame" title="topFrame" />
		<frameset id="frameset2"  cols="201,*" frameborder="no" border="0" framespacing="0">
			<frame src="menu.action" name="leftFrame" scrolling="no" noresize="noresize"  id="leftFrame" title="leftFrame" />
			<frameset id="frameset3" rows="20,*"  border="0" framespacing="0">
				<frame src="foot.action" name="navigateFrame"  scrolling="no" noresize="noresize id="navigateFrame" title="navigateFrame" />
				<frame src="body.action" name="mainFrame"  id="mainFrame" title="mainFrame" />
	        </frameset>
	        
		</frameset>
</frameset>
<frame src="about:blank"></frame> 
</frameset>
<noframes>
<body>
</body>
</noframes></html>