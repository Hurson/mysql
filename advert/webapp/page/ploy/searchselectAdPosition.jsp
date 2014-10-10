﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

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
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
<title></title>

<script type="text/javascript">
    var $ = function(id){
        return document.getElementById(id);
    };
	function goPage(pageNo) {
     document.getElementById("pageno").value=pageNo;
       document.forms[0].submit();
    }
    function submitForm(){
        validate();
        window.returnValue = "";
    }

    function query(){
        window.location.reload();
    }

    function deleteData(){
        document.getElementById("messageDiv").innerHTML = "删除成功";
    }
    
    function addData(){
    	var url = "queryContent4Package.htm";
        window.showModalDialog(url, "", "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
    }

    function validate(){
        //输入框的边框变为红色
        $("name").style.borderColor="red";
        $("type").style.borderColor="red";
        $("remark").style.borderColor="red";
        //输入框后加错误提示信息
        $("name_error").className="error";
        $("name_error").innerHTML = "角色名称不能为空";
        $("type_error").className="error";
        $("type_error").innerHTML = "角色类型不能为空";
        $("remark_error").className="error";
        $("remark_error").innerHTML = "备注中含有特殊字符";
        return;
    }

   function selectad(padname,padid){
    	
    	parent.document.getElementById("adPosition.positionName").value=padname;
    	parent.document.getElementById("adPosition.id").value=padid;
    	
    	//parent.document.getElementById("ploy.adid").value="1";
        parent.easyDialog.close();
    }
	function cancle(){
    	
        parent.easyDialog.close();
    }

</script>
</head>

<body class="mainBody">

<form action="<%=path %>/page/ploy/queryAdPostionList.do" method="post" id="queryForm">
 <input type="hidden" id="pageno" name="pageAdPosition.pageNo" value="${pageAdPosition.pageNo}"/>
            <input type="hidden" id="pagesize" name="pageAdPosition.pageSize" value="${pageAdPosition.pageSize}"/>

<div class="search">

<div class="searchContent" >
<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>广告位名称：</span><input type="text" name="adPosition.positionName" id="adPosition.positionName" value="${adPosition.positionName}"/>
	  <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" width="5%" class="dot"></td>
        <td width="20%" align="center">广告位名称</td>
        <td width="20%" align="center">广告位类型</td>
        <td width="20%" align="center">是否高清</td>
       <td width="20%" align="center">是否叠加</td>
        <td width="20%" align="center">是否轮询</td>
    </tr>
   				 <c:if test="${pageAdPosition.dataList != null && fn:length(pageAdPosition.dataList) > 0}">
                    <c:forEach items="${pageAdPosition.dataList}" var="padpostion" varStatus="pc">
                        <tr <c:if test="${pc.index%2==1}">class="sec"</c:if>>
                            <td>
                                <input type="radio" name="contractid" value="合同2" onclick="selectad('${padpostion.positionName}','${padpostion.id}');">
                             </td>
                            <td>
                                ${padpostion.positionName}
                            </td>
                             <td>
                                ${padpostion.positionName}
                            </td>
					        <td>
                               <c:if test="${padpostion.isHd==1}">是
                               </c:if>
                                <c:if test="${padpostion.isHd!=1}">否
                               </c:if>                            
                            </td>				     
					        <td width="15%" align="center">
					         <c:if test="${padpostion.isAdd==1}">是
                               
                               </c:if>
                               <c:if test="${padpostion.isAdd!=1}">否
                               
                               </c:if>
							</td>
					        <td width="15%" align="center">
					         <c:if test="${padpostion.isLoop==1}">是
                              
                               </c:if>
                                 <c:if test="${padpostion.isLoop!=1}">否
                              
                               </c:if>
							</td>
							
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="6">
		<input type="button" class="btn" value="返 回" onclick="cancle();" />
		
        <div class="page"
					style="background: url(<%=path %>/images/bottom.jpg) repeat-x; text-align: right;">
					<a href="#">当前第${pageAdPosition.pageNo }/${pageAdPosition.totalPage }页</a>&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${pageAdPosition.pageNo==1&&pageAdPosition.totalPage!=1}">
							<a href="#" onclick="javascript:goPage('${pageAdPosition.pageNo+1}')">下一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageAdPosition.totalPage}')">末页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${pageAdPosition.pageNo==pageAdPosition.totalPage&&pageAdPosition.totalPage!=1 }">
							<a href="#" onclick="javascript:goPage('${1}')">首页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageAdPosition.pageNo-1}')">上一页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${pageAdPosition.pageNo>1&&pageAdPosition.pageNo<pageAdPositiontotalPage }">
							<a href="#" onclick="javascript:goPage('${1}')">首页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageAdPosition.pageNo-1}')">上一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageAdPosition.pageNo+1}')">下一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageAdPosition.totalPage}')">末页</a>&nbsp;&nbsp;
						</c:when>
					</c:choose>
				
        </div>
    </td>
  </tr>
</table>
</div>
</div>
</form>
</body>
</html>