<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script language="javascript">

   
   //调用统一的后续处理
   try{
	   window.returnValue = "${message}";
   }catch(e){
	   alert("An exception occured in the script.Error name: " + e.name
            + ".Error message: " + e.message); 
   }

   window.parent.close();
</script>
