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

   function selectrule(prulename,pruleid){
    	
    	parent.document.getElementById("ploy.ruleName").value=prulename;
    	parent.document.getElementById("ploy.ruleId").value=pruleid;
        parent.easyDialog.close();
    }
	function cancle(){
    	
        parent.easyDialog.close();
    }

</script>
</head>

<body class="mainBody">

<form action="<%=path %>/page/ploy/addqueryRuleList.do" method="post" id="queryForm">
 <input type="hidden" id="pageno" name="pageRule.pageNo" value="${pageRule.pageNo}"/>
            <input type="hidden" id="pagesize" name="pageRule.pageSize" value="${pageRule.pageSize}"/>

<div class="search">

<div class="searchContent" >
<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>规则名称：</span><input type="text" name="rule.marketingRuleName" id="rule.marketingRuleName" value="${rule.marketingRuleName}"/>
	  <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" width="5%" class="dot"></td>
        <td width="30%" align="center">规则名称</td>
        <td width="30%" align="center">开始时间</td>
        <td width="30%" align="center">结束时间</td>
    </tr>
   				 <c:if test="${pageRule.dataList != null && fn:length(pageRule.dataList) > 0}">
                    <c:forEach items="${pageRule.dataList}" var="prule" varStatus="pc">
                        <tr <c:if test="${pc.index%2==1}">class="sec"</c:if>>
                            <td>
                                <input type="radio" name="contractid" value="合同2" onclick="selectrule('${prule.marketingRuleName}','${prule.marketingRuleId}');">
                             </td>
                            <td>
                                ${prule.marketingRuleName}
                            </td>
                             <td>
                                ${prule.startTime}
                                
                            </td>
					        <td>
                                ${prule.endTime}
                            </td>		
							
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="4">
		<input type="button" class="btn" value="返 回" onclick="cancle();" />
		
        <div class="page"
					style="background: url(<%=path %>/images/bottom.jpg) repeat-x; text-align: right;">
					<a href="#">当前第${pageRule.pageNo }/${pageRule.totalPage }页</a>&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${pageRule.pageNo==1&&pageRule.totalPage!=1}">
							<a href="#" onclick="javascript:goPage('${pageRule.pageNo+1}')">下一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageRule.totalPage}')">末页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${pageRule.pageNo==pageRule.totalPage&&pageRule.totalPage!=1 }">
							<a href="#" onclick="javascript:goPage('${1}')">首页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageRule.pageNo-1}')">上一页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${pageRule.pageNo>1&&pageRule.pageNo<pageRuletotalPage }">
							<a href="#" onclick="javascript:goPage('${1}')">首页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageRule.pageNo-1}')">上一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageRule.pageNo+1}')">下一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageRule.totalPage}')">末页</a>&nbsp;&nbsp;
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