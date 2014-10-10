<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<base target="_self" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
 -->
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>


<title>策略维护</title>
<script>
window.onload = function() {
	
	 refreshSelectList();
}
function  refreshSelectList()
{
	var optionSelelct =window.dialogArguments.document.getElementById("precise.dtvChannelId");
    var flag=false;
    		
	for (var j=0;j<optionSelelct.length;j++)
	{
		 var data_index = "data_"+optionSelelct.options[j].value;
	     var obj= document.getElementById(data_index);
	     if (obj!=null)
	     {
	    	 obj.checked=true;
	     }
	}
}
function query() {
        document.forms[0].submit();
        
       /// $("queryForm").submit();
    }
function selectData(){
    	//parent.document.request.getAttribute("areas","10,12");
    
    	
    	if (getCheckCount("dataIds")==0)
    	{
    		alert("请选择频道");
    		return ;
    	}
    	var selectData=getCheckValue("dataIds");
    	var strdatas = selectData.split(",");
    	for (var i=0;i<strdatas.length;i++)
    	{
    		var dataPropertys= strdatas[i].split("_");
    		var optionSelelct =window.dialogArguments.document.getElementById("precise.dtvChannelId");
    		var flag=false;
    		
	        for (var j=0;j<optionSelelct.length;j++)
	        {
	        	if (optionSelelct.options[j].value==dataPropertys[0])
	        	{
	        		flag =true;
	        	}
	        	//alert(optionSelelct.length);
	        }
	       if (flag==false)
	        {
	        	window.dialogArguments.addselectData("precise.dtvChannelId",dataPropertys[0],dataPropertys[1]);
	        }
	      
    	}
    	//window.dialogArguments.document.getElementById("precise.assetId").options.add(new Option("文本","111"));
    
		//parent.easyDialog.close();
    }
</script>
<style>
	.easyDialog_wrapper{ width:800px;height:580px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}
</style>

</head>
<body class="mainBody">
 <form action="<%=path %>/page/precise/queryDTVChannel.do" method="post" id="queryForm">
 <s:set name="page" value="%{pageChannel}" />
		 <input type="hidden" id="pageNo" name="pageChannel.pageNo" value="${page.pageNo}"/>
		 <input type="hidden" id="pageSize" name="pageChannel.pageSize" value="${page.pageSize}"/>
 <input type="hidden" id="ployId" name="ployId" value="${ployId}"/>
  <input type="hidden" id="positionId" name="positionId" value="${positionId}"/>
	 <input type="hidden" id="channel.channelType" name="channel.channelType" value="${channel.channelType}"/>
	
<div class="search">
<div class="path">首页 >> 投放策略管理 >> 精准维护>>选择频道</div>
<div class="searchContent" >
<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>频道名称：</span><input onkeypress="return validateSpecialCharacter();" type="text" name="channel.channelName" id="channel.channelName" value="${channel.channelName}"/>
	 
        <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
<c:if test="${message != null}">
				<span style="color:red;">${message}</span>
</c:if></div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="selectAllBox" onclick="selectAll(this, 'dataIds');"/></td>
        <td width="30%" align="center">频道名称</td>
        <td width="30%" align="center">频道编码</td>
        <td width="30%" align="center">ServiceID</td>
     
    </tr>
   				 <c:if test="${pageChannel.dataList != null && fn:length(pageChannel.dataList) > 0}">
                    <c:forEach items="${pageChannel.dataList}" var="channelInfo" varStatus="pl">
                        <tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
                            <td>
                                <input id="data_${channelInfo.serviceId}" type="checkbox" value="<c:out value='${channelInfo.serviceId}_${channelInfo.channelName}'/>" name="dataIds"/>
                             </td>
                            <td width="30%" >
                               ${channelInfo.channelName}
                            </td>
                            <td width="30%" align="center">
                         
                              ${channelInfo.channelCode}
							</td>
					        <td width="20%" align="center">
					          ${channelInfo.serviceId}
                            
							</td>
					   
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="4">
		
        <input type="button" value="添加" class="btn" onclick="javascript:selectData();"/>&nbsp;&nbsp;
        <input type="button" value="返回" class="btn" onclick="window.close();"/>
		
								&nbsp;&nbsp;
					        <jsp:include page="../../common/page.jsp" flush="true" />

    </td>
  </tr>
</table>
</div>
</div>
</form>
</body>

</html>