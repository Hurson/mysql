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
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
<title></title>
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript">
    var $ = function(id){
        return document.getElementById(id);
    };
	
    function submitForm(){
        validate();
        window.returnValue = "";
    }

    function query(){
       document.forms[0].submit();
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
       
        return;
    }

    function selectcontract(contractName,contractId){
    	
    	parent.document.getElementById("contract.contractName").value=contractName;
    	parent.document.getElementById("ploy.contractId").value=contractId;
        parent.easyDialog.close();
    }
	function cancle(){
    	
        parent.easyDialog.close();
    }
</script>
</head>

<body class="mainBody">

<form action="<%=path %>/page/ploy/queryContractList.do" method="post" id="queryForm">
 <s:set name="page" value="%{pageContract}" />
		 <input type="hidden" id="pageNo" name="pageContract.pageNo" value="${page.pageNo}"/>
		 <input type="hidden" id="pageSize" name="pageContract.pageSize" value="${page.pageSize}"/>


<div class="searchContent" >
<table cellspacing="1" class="searchList" style="align:top">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>合同名称：</span><input type="text" name="contract.contractName" id="contract.contractName" value="${contract.contractName}"/>
	  <span>合同编码：</span><input type="text" name="contract.contractCode" id="contract.contractCode" value="${contract.contractCode}"  />
	
        <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>

<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" width="5%" class="dot"></td>
        <td width="20%" align="center">合同名称</td>
        <td width="20%" align="center">合同编号</td>
        <td width="20%" align="center">广告商</td>
       <td width="20%" align="center">生效时间</td>
        <td width="20%" align="center">失效时间</td>
    </tr>
   				 <c:if test="${pageContract.dataList != null && fn:length(pageContract.dataList) > 0}">
                    <c:forEach items="${pageContract.dataList}" var="pcontract" varStatus="pc">
                        <tr <c:if test="${pc.index%2==1}">class="sec"</c:if>>
                            <td>
                                <input type="radio" name="contractid" value="合同2" onclick="selectcontract('${pcontract.contractName}','${pcontract.id}');">
                             </td>
                            <td>
                                ${pcontract.contractName}</a>
                            </td>
                             <td>
                                ${pcontract.contractCode}</a>
                            </td>
					        <td>
                                ${pcontract.customerId}</a>
                            </td>				     
					        <td width="15%" align="center">
					      <fmt:formatDate value="${pcontract.effectiveStartDate}" dateStyle="medium"/>
							</td>
					        <td width="15%" align="center">
					      <fmt:formatDate value="${pcontract.effectiveStartDate}" dateStyle="medium"/> 
							</td>
							
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="6">
		<input type="button" class="btn" value="返 回" onclick="cancle();" />
		&nbsp;&nbsp;
					        <jsp:include page="../common/page.jsp" flush="true" />
					        <!-- 
        <div class="page"
					style="background: url(<%=path %>/images/bottom.jpg) repeat-x; text-align: right;">
					<a href="#">当前第${pageContract.pageNo }/${pageContract.totalPage }页</a>&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${pageContract.pageNo==1&&pageContract.totalPage!=1}">
							<a href="#" onclick="javascript:goPage('${pageContract.pageNo+1}')">下一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageContract.totalPage}')">末页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${pageContract.pageNo==pageContract.totalPage&&pageContract.totalPage!=1 }">
							<a href="#" onclick="javascript:goPage('${1}')">首页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageContract.pageNo-1}')">上一页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${pageContract.pageNo>1&&pageContract.pageNo<pageContracttotalPage }">
							<a href="#" onclick="javascript:goPage('${1}')">首页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageContract.pageNo-1}')">上一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageContract.pageNo+1}')">下一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageContract.totalPage}')">末页</a>&nbsp;&nbsp;
						</c:when>
					</c:choose>
				
        </div>
         -->
    </td>
  </tr>
</table>
</div>

</form>
</body>
</html>