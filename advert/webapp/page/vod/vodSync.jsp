<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<title>数据同步管理</title>
<script>
$(function(){
	$.ajax({   
	       url:'<%=path%>'+'/vod/vodSync.do',       
	       type: 'POST',    
	       dataType: 'text',                      
	       timeout: 1000000000,                              
	       error: function(){                      
	    		alert("系统错误，请联系管理员！");
	    		$("#info").html('数据同步失败！');
	       },    
	       success: function(result){ 
	    	   if(result=='0'){
		    		$("#info").html('数据同步成功！');
	    	   }else{
		    		$("#info").html('数据同步失败！');
	    	   }
	    	   
		   }  
	   }); 
});
</script>
</head>

<body>
<div id="info">
	数据同步中，请稍后...
</div>
</body>
</html>
