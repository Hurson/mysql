<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	response.setHeader("Pragma","No-cache");     
    response.setHeader("Cache-Control","no-cache");      
    response.setDateHeader("Expires",   -10);     
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<script type="text/javascript" >
<!--
function user_logout(){
	
	var flag= window.confirm("你确定要退出吗？");
	if(flag){
		
		top.location = "logout.do";
	}
}


function close(evt) //author: sunlei
{
    var isIE=document.all?true:false;
    evt = evt ? evt :(window.event ? window.event : null);
    if(isIE){//IE浏览器
        var n = evt.screenX - window.screenLeft;
        var b = n > document.documentElement.scrollWidth-20;
        if(b && evt.clientY<0 || evt.altKey){
           // alert("是关闭而非刷新");
            user_logout();
        }
        else{
           // alert("是刷新而非关闭");
        }
    }
    
}

//-->  
</script>


<head>
    <script type="text/javascript">

        //返回若系统列表为空，则隐藏切换系统的下拉框
        function init() {
            var applicationList = "${applicationList}"; //若applicationList的size为0，则此处applicationList = "[]"
            if (applicationList == null || applicationList == "[]") {
                document.getElementById("td_applicationList").style.display = "none"; //此处若设置display="none",这样下拉框的位置会被占用
            }
        }

        function changeApplication(url) {
            if (url == "") {
                return;
            }
            url = url + "/login.action?username=${userInfo.userName}&password=${userInfo.password}";
            parent.location = url;
        }

        function changePassword(userId) {
            var url = "changePassword.action?userId=" + userId;
            parent.frames['mainFrame'].location = url;
        }

        function logout(userId) {
        var flag= window.confirm("你确定要退出吗？");
	if(flag){
            var url = "logout.action?userId=" + userId;
            parent.location = url;
            }
        }

    </script>
    <link id="maincss" href="<%=request.getContextPath()%>/css/new/main.css" rel="stylesheet" type="text/css"
          media="all"/>
</head>
<body onload="init();" class="headBody">
<div class="headTable">
    <div class="logo">广告投放系统</div>
    <div class="menu">
        <c:if test="${applicationList != null }">
            <c:forEach items="${applicationList}" var="application">
                <a href="#"
                   <c:if test="${application.code ==30}">class="focus"</c:if> /><img
                    onclick="javascript:changeApplication('${application.url}');"
                    src="<%=request.getContextPath()%>/images/menu_icon_3.png" width="32"
                    height="31"/><br/>${application.name}</a>
            </c:forEach>
        </c:if>
        <a href="#" onclick="user_logout();"><img src="<%=request.getContextPath()%>/images/menu_icon_4.png" width="32" height="31"/><br/>退出</a>
    </div>
</div>
</body>
</html>
