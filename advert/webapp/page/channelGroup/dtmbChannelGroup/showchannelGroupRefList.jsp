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
 -->

<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="<%=path%>/js/material/uploadMaterial.js"></script>
<script type="text/javascript" src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>


<script type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/addOrUpdate.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>

<title>频道组管理</title>
<script>	
alert(123);
function query() {

        if(validateSpecialCharacterAfter(document.getElementById("channelQuery.channelName").value)){
			alert("频道名称不能包括特殊字符！");
			return ;
		}
        document.forms[0].submit();
    }
 function goPage(pageNo) {
       document.getElementById("pageno").value=pageNo;
       document.forms[0].submit();
    }
 function addData() {
    var channelGroupId=document.getElementById("channelGroupId").value;

		var structInfo = "";
			structInfo+="<div style='margin:0px;padding:0px;width:600px'>";
		    structInfo+="<iframe id='selectChannelFrame' name='selectChannelFrame'  frameBorder='0' width='1000px' height='500px'  scrolling='yes'  align='top' src='<%=request.getContextPath()%>/page/channelGroup/selectChannel.do?channelGroupId="+channelGroupId+"'></iframe>";
		    structInfo+="</div>";
			
			easyDialog.open({
			container : {
				header : "关联频道",
				content : structInfo
			},			
			overlay : false
		});
    }
 function deleteDatas() {
	 if (getCheckCount("dataIds") <= 0) {
                alert("请勾选需删除的记录！");
                return;
     }
     if (confirm("您确定删除所选吗？")){
          var dataIds = getCheckValue("dataIds");	
          document.forms[0].action="<%=path %>/page/channelGroup/deleteChannelGroupRef.do";
          document.forms[0].submit();
     }
        
    }

    
    function goback() {
    window.location.href="<%=path%>/page/channelGroup/queryChannelGroupList.do"; 
    }
    
    function refreshChannelList(){
    var channelGroupId=document.getElementById("channelGroupId").value;
       window.location.href="<%=path%>/page/channelGroup/queryChannelGroupRefList.do?channelGroupId="+channelGroupId;
    }
</script>
<style>
	.easyDialog_wrapper{ width:1000px;height:400px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
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
 <form action="<%=path %>/page/channelGroup/dtmbChannelGroup/showChannelGroupRefList.do" method="post" id="queryForm">


         <s:set name="page" value="%{channelListPage}" />
		 <input type="hidden" id="pageNo" name="channelListPage.pageNo" value="${page.pageNo}"/>
		 <input type="hidden" id="pageSize" name="channelListPage.pageSize" value="${page.pageSize}"/>

         <input type="hidden" id="channelGroupId" name="channelGroupId" value="${channelGroupId}"/>
<div class="search">
<div class="path">首页 >> 系统管理 >> 频道组维护>> 关联频道</div>
<div class="searchContent" >
<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>频道名称：</span>
                  <input type="text" name="channelQuery.channelName" id="channelQuery.channelName" value="${channelQuery.channelName}"/>
                  
        <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td width="10%" height="28" class="dot"><input type="checkbox" name="selectAllBox" onclick="selectAll(this, 'dataIds');"/></td>
        <td width="15%" align="center">频道名称</td>
        <td width="15%" align="center">频道编码</td>
        <td width="15%" align="center">类型</td>
        <td width="10%" align="center">SERVICE_ID</td>
        <td width="10%" align="center">TS_ID</td>
        <td width="10%" align="center">NETWORK_ID</td>
        <td width="15%" align="center">是否回放频道</td>
    </tr>
    
   				 <c:if test="${channelListPage.dataList != null && fn:length(channelListPage.dataList) > 0}">
                    <c:forEach items="${channelListPage.dataList}" var="channelInfo" varStatus="pl">
                        <tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
                            <td>
                                <input type="checkbox" value="<c:out value='${channelInfo.channelId}'/>" name="dataIds"/>
                             </td>
                            <td>
                                ${channelInfo.channelName}
                            </td>
                            <td>
                                ${channelInfo.channelCode}
                            </td>
                            <td>
                                ${channelInfo.channelType}
                            </td>
                            <td>
                               ${channelInfo.serviceId} 
                            </td>
                            <td>
                               ${channelInfo.tsId} 
                            </td>
                            <td>
                               ${channelInfo.networkId} 
                            </td>
							<td>
                               <c:choose>
											<c:when test="${channelInfo.isPlayBack==0}">
												否
											</c:when>
											<c:otherwise>
												是
											</c:otherwise>
							   </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="8">
	  <jsp:include page="../../common/page.jsp" flush="true" />

        
    </td>
  </tr>
</table>
</div>
</div>
</form>
</body>

</html>