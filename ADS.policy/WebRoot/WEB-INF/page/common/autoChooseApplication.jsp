<%@ page contentType="text/html; charset=utf-8" %>


<script language="javascript">

	//调用统一的后续处理
   var url = "${chosenApplicationUrl}";
   //跳转到对应的系统
   window.location = url+"/login.action?username=${user.userName}&password=${user.password}"
</script>
