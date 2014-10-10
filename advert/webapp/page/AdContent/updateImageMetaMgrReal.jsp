<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="css/platform.css" type="text/css" />
<script language="javascript" type="text/javascript" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/js.js"></script>

<title>修改审核图片素材</title>
<script type="text/javascript">
		
		//页面加载时调用访法 
    	    window.onload=function(){
			  getStartTime();
			  getEndTime();
			  getFilePath();
    		}
    		
    		//得到有效结束时间
    		function getEndTime(){
    			 var end =  document.getElementById("endTimeHidden").value;
    			 var endTime = end.substr(0,10);
    			 document.getElementById("endTime").value=endTime;
    		}
    		
    		
    		//得到生效开始时间
    		function getStartTime(){
    			 var start =  document.getElementById("startTimeHidden").value;
    			 var startTime = start.substr(0,10);
    			 document.getElementById("startTime").value=startTime;
    			 
    		}
    		
    		//得到临时存储的有效路径
         	function getFilePath(){ 
 				var temporaryFilePathHidden = document.getElementById("temporaryFilePathHidden").value;  
 				var temporaryFilePathStr = temporaryFilePathHidden.substr(0,temporaryFilePathHidden.indexOf("/")+1);
 				document.getElementById("temporaryFilePath").value=temporaryFilePathStr;	
    		}
    		



    		//取消素材		
    		function goBack(){
    			window.location.href="adContentMgr_listReal.do";
    		}
    		
    		function goUpdate(){
    		
    			var resourceName = document.getElementById("resourceName").value;
    			var contractNumber =  document.getElementById("contractNumber").value;  
    			var name =  document.getElementById("name").value;
    			var fileFormat =  document.getElementById("fileFormat").value;
    			
    			if(resourceName == ""){
    				document.getElementById("resourceNameF").innerHTML ="资产名称不能为空！";
    				return false;
    			}else{
    				document.getElementById("resourceNameF").innerHTML ="";
    				if(contractNumber == ""){
    					document.getElementById("contractNumberF").innerHTML ="所属合同号不能为空！";
    					return false;
    				}else{
    					document.getElementById("contractNumberF").innerHTML ="";
    					if(name == ""){
    						document.getElementById("nameF").innerHTML ="图片名称不能为空！";
    						return false;
    					}else{
    						document.getElementById("nameF").innerHTML ="";
    						if(fileFormat == ""){
    							document.getElementById("fileFormatF").innerHTML ="图片格式不能为空！";
    							return false;
    						}else{
    							document.getElementById("fileFormatF").innerHTML ="";
    							return true;
    						}
    					}
    				}
    			}
    		}
    		
    		
    </script>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});
</script>
<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}
</style>
</head>

<body>
<form action="adContentMgr_updateImageMetaReal.do" method="post" name="adContentMgr_updateImageMetaReal" onsubmit="return goUpdate();"> 
<table cellpadding="0" cellspacing="0" border="0" width="100%">

	<tr>
		<td>
		<table cellpadding="0" cellspacing="0" border="0" width="100%"
			class="tab_right">
			<tr>
				<td>工作台</td>
				<td>客户</td>
				<td>日程行动</td>
				<td>销售机会</td>
				<td>订单管理</td>
				<td>客服中心</td>
				<td>财务中心</td>
				<td>营销中心</td>
				<td>人力资源中心</td>
				<td>数据统计</td>
				<td>信息门户管理</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td style="padding: 4px;">
		<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
			<tr>
				<td class="formTitle" colspan="99"><span>图片素材修改</span></td>
			</tr>
			<tr>
				<input type=hidden value="${resourceReal.id}" name="resourceReal" />
				<input type=hidden value="${resourceReal.createTime}" name="resourceReal.createTime"/>
				<input type=hidden value="${resourceReal.operationId}" name="resourceReal.operationId"/>
				<input type=hidden value="${resourceReal.resourceType}" name="resourceReal.resourceType"/>
				<input type=hidden value="${resourceReal.resourceId}" name="resourceReal.resourceId"/>
				<input type=hidden value="${resourceReal.category}" name="resourceReal.category"/>
				<input type=hidden value="${resourceReal.state}" name="resourceReal.state"/>
				<input type=hidden value="${resourceReal.customerId}" name="resourceReal.customerId"/>
				<input type=hidden value="${resourceReal.contractNumber}" name="resourceReal.contractNumber"/>
				<input type=hidden value="${resourceReal.startTime}" name="resourceReal.startTime" id= "startTimeHidden"/>
				<input type=hidden value="${resourceReal.endTime}" name="resourceReal.endTime" id="endTimeHidden"/>
				
				<input type=hidden value="${imageReal.id}" name="imageReal.id"/> 
				<input type=hidden value="${imageReal.fileWidth}" name="imageReal.fileWidth"/>
				<input type=hidden value="${imageReal.fileHeigth}" name="imageReal.fileHeigth"/>
				<input type=hidden value="${imageReal.fileSize}" name="imageReal.fileSize"/>
				<input type=hidden value="${imageReal.formalFilePath}" name="imageReal.formalFilePath" id="formalFilePath"/>
			
				<td class="td_label">内容分类：</td> 
			    <td class="td_input">&nbsp;<c:if test="${resourceReal.category == 1}">公益</c:if><c:if test="${resourceReal.category == 0}">商用</c:if></td>
				<td class="td_label">素材状态：</td>
				<td class="td_input">&nbsp;<c:if test="${resourceReal.state == '0'}">未审核</c:if><c:if test="${resourceReal.state == '1'}">上线</c:if><c:if test="${resource.state == '2'}">下线</c:if><c:if test="${resource.state == '3'}">审核不通过</c:if><c:if test="${resource.state == '4'}">审核待删除</c:if><c:if test="${resource.state == '5'}">审核已删除</c:if></td>
			</tr>
			
			<tr>
				<td class="td_label">所属广告位：</td>
				<td class="td_input">&nbsp;${positionName}</td>
				<td class="td_label">所属合同号：</td>
				<td class="td_input">&nbsp;${resourceReal.contractNumber}</td>
			</tr>
			
			<tr>
				<td class="td_label">图片大小：</td>
				<td class="td_input">&nbsp;${imageReal.fileSize}</td>
				<td class="td_label">文件宽度：</td>
				<td class="td_input">&nbsp;${imageReal.fileWidth}&nbsp;&nbsp;&nbsp;<font style="color:red;">像素(PX)</font></td>
			</tr>
			<tr>
				<td class="td_label">临时库存储路径：</td>
				<td class="td_input"><input type="text" class="e_input" id="formalFilePath" value="${imageReal.formalFilePath}" name="imageReal.formalFilePath" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/><font style="color:red;"><span id = "temporaryFilePathF"></span>*</font></td>
				<td class="td_label">资产名称：</td>
				<td class="td_input">
				<input type="text" class="e_input" value="${resourceReal.resourceName}" name="resourceReal.resourceName" id="resourceName" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/><font style="color:red;"><span id = "resourceNameF"></span>*</font></td>
			</tr>
			<tr>
				<td class="td_label">图片名称：</td>
				<td class="td_input">
				    <input type="text" class="e_input" value="${imageReal.name}"  name="imageMeta.name" id="name" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/><font style="color:red;"><span id = "nameF"></span>*</font></td>
				<td class="td_label">图片格式：</td>
				<td class="td_input"><input type="text" class="e_input" value="${imageReal.fileFormat}" id="fileFormat" name="imageReal.fileFormat" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/><font style="color:red;"><span id = "fileFormatF"></span>*</font></td>
			</tr>
			<tr>
				<td class="td_label">有效开始时间：</td>
				<td class="td_input"><input type="text" class="e_input" name="resourceReal.startTime" id="startTime" onclick="WdatePicker()" readOnly="true" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/><font style="color:red;">*</font></td>
				<td class="td_label">有效结束时间：</td>
				<td class="td_input"><input type="text" class="e_input" name="resourceReal.endTime" id="endTime" class="e_input" onclick="WdatePicker()" readOnly="true" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/><font style="color:red;">*</font></td>
			</tr>
			
			<tr>
				<td class="td_label">资产描述：</td>
				<td class="td_input"><textarea id="" rows="8" cols="35" value="${resourceReal.resourceDesc}" name="resourceReal.resourceDesc">${resourceReal.resourceDesc}</textarea></td>
				<td class="td_label" rowspan="2">图片素材缩略图：</td>
				<td class="td_input" rowspan="2"><img  src="${resourcePath}" height="255px" width="260px"/></td>
			</tr>
			
			<tr>
				<td class="td_label">审核意见：</td>
				<td class="td_input"></td>
				
			</tr>
			 <tr>
				<td class="formBottom" colspan="99" align="center">
					<input name="Submit"  type="submit" title="确定" value="确定" onfocus=blur()/>
					<input type="button" title="取消"    value="返回" onfocus=blur() id="bt1" onclick="goBack();" />
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
