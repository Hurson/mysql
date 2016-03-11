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
function query() {

       if(validateSpecialCharacterAfter(document.getElementById("channelGroupQuery.name").value)){
			alert("频道组名称不能包括特殊字符！");
			return ;
		}
        document.forms[0].submit();
    }
 function goPage(pageNo) {
       document.getElementById("pageno").value=pageNo;
       document.forms[0].submit();
    }
 function addData() {
      window.location.href="<%=path%>/dchannelGroup/intoAddChannelGroup.do";
    }
 function deleteDatas() {
	 if (getCheckCount("dataIds") <= 0) {
                alert("请勾选需删除的记录！");
                return;
     }
     if (confirm("您确定删除所选的频道组吗？")) {
	         var dataIds = getCheckValue("dataIds");	
             document.forms[0].action="<%=path %>/dchannelGroup/deleteChannelGroup.do";
             document.forms[0].submit();
	    }
      
     
    }

function modifyChannelGroupRef(channelGroupId,channelGroupType) {
    window.location.href="<%=path %>/dchannelGroup/queryChannelGroupRefList.do?channelGroupId="+channelGroupId+"&channelGroupType="+channelGroupType; 
    }
    
    function modifyData(channelGroupId,channelGroupType) {
      window.location.href="<%=path %>/dchannelGroup/initChannelGroup.do?channelGroupId="+channelGroupId+"&channelGroupType="+channelGroupType;
    }
</script>
<style>
	.easyDialog_wrapper{ width:600px;height:580px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
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
 <form action="<%=path %>/page/dchannelGroup/queryChannelGroupList.do" method="post" id="queryForm">
 <!-- 
  <input type="hidden" id="pageno" name="contractPage.pageNo" value="${contractPage.pageNo}"/>
 <input type="hidden" id="pagesize" name="contractPage.pageSize" value="${contractPage.pageSize}"/>
  -->


         <s:set name="page" value="%{channelGroupPage}" />
		 <input type="hidden" id="pageNo" name="channelGroupPage.pageNo" value="${page.pageNo}"/>
		 <input type="hidden" id="pageSize" name="channelGroupPage.pageSize" value="${page.pageSize}"/>
		 <input type="hidden" id="channelGroupInfo.id"  value="${channelGroupInfo.id}"/>


<div class="search">
<div class="path">首页 >> 系统管理 >> 频道组维护</div>
<div class="searchContent" >
<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>频道组名称：</span>
                  <input type="text" name="channelGroupQuery.name" id="channelGroupQuery.name" value="${channelGroupQuery.name}"/>
                  
        <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td width="10%" height="28" class="dot"><input type="checkbox" name="selectAllBox" onclick="selectAll(this, 'dataIds');"/></td>
        <td width="20%" align="center">频道组名称</td>
        <td width="20%" align="center">频道组编号</td>
        <td width="15" align="center">频道组类型</td>
        <td width="20%" align="center">描述</td>
		<td width="15%" align="center">操作</td>
    </tr>
   				 <c:if test="${channelGroupPage.dataList != null && fn:length(channelGroupPage.dataList) > 0}">
                    <c:forEach items="${channelGroupPage.dataList}" var="channelGroupInfo" varStatus="pl">
                        <tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
                            <td>
                                <input type="checkbox" value="<c:out value='${channelGroupInfo.id}'/>" name="dataIds"/>
                             </td>
                            <td>
                                <a href="javascript:modifyData('${channelGroupInfo.id}','${channelGroupInfo.type}');">${channelGroupInfo.name}</a>
                            </td>
                            <td>
                                ${channelGroupInfo.code}
                            </td>
                            <td>
                            	${channelGroupInfo.type}
                            </td>
                            <td>
                                ${channelGroupInfo.channelDesc}
                            </td>
                            <td>
                               <a href="javascript:modifyChannelGroupRef('${channelGroupInfo.id}','${channelGroupInfo.type}');">关联频道</a> 
                            </td>
                           
							
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="6">
		<input type="button" value="删除" class="btn" onclick="javascript:deleteDatas();"/>
        <input type="button" value="添加" class="btn" onclick="javascript:addData();"/>
        <jsp:include page="../../common/page.jsp" flush="true" />

        
    </td>
  </tr>
</table>
</div>
</div>
</form>
</body>

</html>