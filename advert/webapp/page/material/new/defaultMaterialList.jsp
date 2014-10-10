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




<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>


<title>素材管理</title>
<script>
var submitType = "";
function query() {
        document.forms[0].submit();
    }
 function goPage(pageNo) {
       document.getElementById("pageno").value=pageNo;
       document.forms[0].submit();
    }
    
   
  function addData() {
      window.location.href="<%=path%>/page/meterial/intoAddDefaultMaterial.do";
    }
 function deleteData() {
	 if (getCheckCount("dataIds") <= 0) {
                alert("请勾选需删除的记录！");
                return;
     }
     var ids = "";
     var  resourceArr = document.getElementsByName("dataIds");
		for(var i=0; i < resourceArr.length;i++ ){
			if(resourceArr[i].checked){
				ids+= resourceArr[i].value+",";
				
				
				var tmp = resourceArr[i].value;
					
				var state = $("#"+tmp+"_state").val();
				if(state > 0){
					alert("不能删除已经使用的默认素材！");
					return;
				}
				
			}
	    }
	 var ret = window.confirm("您确定要删除吗？");
	 if (ret==1)
     {
		 window.location.href="<%=path %>/page/meterial/nimeide.do?ids="+ids;
     }
    }
  function modifyData(materialId) {
      window.location.href="<%=path %>/page/meterial/initDefaultMaterial.do?materialId="+materialId;
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
 <form action="<%=path %>/page/meterial/queryDefaultMeterialList.do" method="post" id="queryForm">
 <input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
 <input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>
 <input id="positionPackIds" name="positionPackIds" type="hidden" value="${positionPackIds}"/>

<div class="search">
<div class="path">首页 >> 素材管理 >> 默认素材维护</div>
<div class="searchContent" >
<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
                  <span>素材名称：</span>
                  <input type="text" name="meterialQuery.resourceName" id="meterialQuery.resourceName" value="${meterialQuery.resourceName}"/>
                  
                  <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="selectAllBox" onclick="selectAll(this, 'dataIds');"/></td>
        <td width="25%" align="center">素材名称</td>
        <td width="15%" align="center">素材类型</td>
        <td width="15%" align="center">素材分类</td>
        <td width="20%" align="center">广告位</td>
		<td width="10%" align="center">状态</td>
		<td width="15%" align="center">创建日期</td>
    </tr>
   				 <c:if test="${page.dataList != null && fn:length(page.dataList) > 0}">
                    <c:forEach items="${page.dataList}" var="meterialInfo" varStatus="pl">
                        <tr <c:if test="${pl.index%2==1}">class="sec"</c:if> align="center">
                            <td>
                                <input type="checkbox" value="<c:out value='${meterialInfo.id}'/>" name="dataIds" />
                             </td>
                            <td>
                                <a href="javascript:modifyData('${meterialInfo.id}');">${meterialInfo.resourceName}</a>
                            </td>
                            <td align="center">
                                <c:choose>
											<c:when test="${meterialInfo.resourceType==0}">
												图片
											</c:when>
											<c:when test="${meterialInfo.resourceType==1}">
												视频
											</c:when>
											<c:when test="${meterialInfo.resourceType==2}">
												文字
											</c:when>
											<c:when test="${meterialInfo.resourceType==3}">
												调查问卷
											</c:when>
											<c:otherwise>
												ZIP
											</c:otherwise>
								</c:choose>
                            </td >
                            
                            <td align="center">								
								<c:forEach items="${materialCategoryList}" var="cp" >
                            	    <c:if test="${cp.id==meterialInfo.categoryId}">
                            	          ${cp.categoryName}
                            	    </c:if>
                                </c:forEach>
                            </td>
                            <td >
                                ${meterialInfo.advertPositionName}
                            </td>
                           
                            <td align="center">
                                <c:choose>
											<c:when test="${meterialInfo.used > 0}">
												使用
											</c:when>
											<c:otherwise>
												未使用
											</c:otherwise>
										</c:choose>
										<input type="hidden" value="${meterialInfo.used}"  id="${meterialInfo.id}_state"/>
                            </td>
                            <td align="center">
                                <fmt:formatDate value="${meterialInfo.createTime}" dateStyle="medium"/>
                            </td>
                           
							
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="7">
   		<input type="button" value="添加" class="btn" onclick="javascript:addData();"/>
        <input type="button" value="删除" class="btn" onclick="javascript:deleteData();"/>
       <jsp:include page="../../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</div>
</div>

</form>
</body>

</html>