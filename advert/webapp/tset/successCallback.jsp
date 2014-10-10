<%@ page contentType="text/html; charset=utf-8" %>


<script language="javascript">

   
   //调用统一的后续处理
   try{
   		window.returnValue = "${message}";
   		//window.opener.successCallBack();
   }catch(e){
	   alert("An exception occured in the script.Error name: " + e.name
            + ".Error message: " + e.message); 
   }
   
   window.close();
</script>
