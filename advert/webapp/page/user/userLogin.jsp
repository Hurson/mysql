<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*,com.opensymphony.xwork2.util.*" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	ValueStack vs = (ValueStack)request.getAttribute("struts.valueStack");
	if(vs!=null){
		String username = (String)vs.findValue("username");
		System.out.println("username="+username);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>互动平台系统登陆页222</title>
<script>
	function go(location){
		//alert("location:"+location);
		window.location.href = location;
	}
</script>
</head>
<body>
	<table style="width:720px;height:576px;">
		<tr>
			<td>
				<form action="login.do?method=userLogin" method="post">
					<table>
						<tr>
							
							<td style="width:200px">
								
							</td>
							<td colspan="2" style="text-align:center">
								用户登陆
							</td>
							<td>
								
							</td>
						</tr>
						<tr>
							<td style="width:200px">
								
							</td>
							<td>
								用户名：
							</td>
							<td>
								<input type="text" name="username"/>
							</td>
							<td id="usernameText">
								${errors.username[0]}
							</td>
						</tr>
						<tr>
							<td style="width:200px">
								
							</td>
							<td>
							密        码：
							</td>
							<td>
								<input type="password" name="passwd"/>
							</td>
							<td id="passwdText">
								${errors.passwd[0]}
							</td>
						</tr>
						<tr>
							<td style="width:200px">
								
							</td>
							<td>
								<input type="submit" value="<s:text name='login'/>"   onclick="checkFrom()"/>
							</td>
							<td>
								<input type="reset" value="<s:text name='reset'/>"/>
							</td>
							<!-- 
							<td>
								<input type="button" value="<s:text name='register'/>" onclick="go('http://localhost:8686/ad/page/user/userRegist.html')"/>
							</td>
							 -->
							<td>
								
							</td>
						</tr>
					</table>
				</form>	
			</td>
		</tr>
	</table>
</body>