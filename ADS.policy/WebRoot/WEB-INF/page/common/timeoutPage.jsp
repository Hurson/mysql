<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<%@   page   isELIgnored="false"%>
<link id="maincss" href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css" media="all"/> 
<p>
<br><br><br><br><br>
<center>
<s:text name='user.session.timeout' />
<span id="time">5</span><s:text name='user.portal.auto.flash' />
<p>
<s:text name='user.not.flash' />：
<a href="javascript:relogin();"><s:text name='user.relogin' /></a>
</center>
<script>       
    //return to log page after 5 second
    var t = 5;   
    setInterval(function(){
        t -= 1;
        if(t <= 0 ) {
        	t = 0;
        }
    	document.getElementById("time").innerHTML = t;
    	if(t <= 0) {
    		relogin();
    	}
    }, 1000);
    
    function relogin(){
    	if(parent.parent!= null){
    		parent.parent.location='<%=request.getContextPath()%>';
    	}else{
    		parent.location='<%=request.getContextPath()%>';
    	}
    }
</script>
