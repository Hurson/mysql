<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<title>告警信息</title>
<style>
	.e_input_add {
		background:url(<%=path%>/images/add.png) right no-repeat #ffffff;
	}
</style>
</head>
<script type="text/javascript">

	function del(){
	if (getCheckCount('ids') <= 0) {
	         alert("请勾选需要标记处理的记录！");
	         return;
	    }
	    var ids  = getCheckValue('ids');
        	$.ajax({   
	 		       url:'delWarn.do',       
	 		       type: 'POST',    
	 		       dataType: 'text',   
	 		       data: {
	 		    	  ids:ids,
	 				},                   
	 		       timeout: 1000000000,                              
	 		       error: function(){                      
	 		    		alert("系统错误，请联系管理员！");
	 		       },    
	 		       success: function(result){ 
	 		    	   if(result == '-1'){
	 		    		  alert("系统错误，请联系管理员！");
	 		    	   }
	 		    	   $("#queryForm").submit();
	 			   }  
	 		   });
	 		
        	$("[name='ids']").removeAttr("checked");
        	
	}


	
    
</script>
<body class="mainBody">
<form action="showWarn.do" method="post" id="queryForm">
 <input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
	<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
	<div class="searchContent">
        <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
             <td height="28" class="dot"><input type="checkbox" name="chkAll" onclick="selectAll(this, 'ids');" id="chkAll"/></td>
                <td>告警时间</td>
				<td>告警信息</td>
            </tr>
            <c:forEach items="${page.dataList}" var="warn" varStatus="pl">
   
				<tr <c:if test="${pl.index%2==1}">class="sec"</c:if> 
					onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#fffed9'">
					<td>
						<input type="checkbox" name="ids" value="${warn.id}" />
					</td>
					<td>
					<!--<c:out value="${warn.time}" />-->
					<fmt:formatDate value="${warn.time}" pattern="yyyy-MM-dd  HH:mm:ss" />
					</td>
					<td><c:out value="${warn.content}" /></td>

				</tr>
			
			</c:forEach>
            <tr>
            	<td colspan="5">
            	<input type="button" value="标记处理" class="btn" onclick="del();"/>&nbsp;&nbsp;
            	<jsp:include page="../page/common/page.jsp" flush="true" />
            	</td>
            </tr>-
        </table>
    </div>
</form>
</body>
</html>