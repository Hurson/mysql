<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link id="maincss"  rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/new/main.css" media="all"/>
<title></title>
 <script type='text/javascript' src='<%=request.getContextPath()%>/js/jquery.min.js'></script>
    <script type='text/javascript' src='<%=request.getContextPath()%>/js/avit.js'></script>
<script type="text/javascript" src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

    function query() {
        if(validateSpecialCharacterAfter(document.getElementById("customerName").value)){
			alert("广告商名称不能包括特殊字符！");
			return ;
		}
		if(validateSpecialCharacterAfter(document.getElementById("contractName").value)){
			alert("合同名称不能包括特殊字符！");
			return ;
		}
		
        document.forms[0].submit();
    }

	function selectContract(contractId,contractName){
	    parent.document.getElementById("material.contractId").value=contractId;
    	parent.document.getElementById("material.contractName").value=contractName;
    	//选择合同后，清空广告位信息
    	parent.document.getElementById("material.advertPositionId").value="";
    	parent.document.getElementById("material.advertPositionName").value="";
    	
        parent.easyDialog.close();
    }
    

    
    function cancle(){
    	
        parent.easyDialog.close();
    }

</script>
</head>

<body class="mainBody" >
<form action="<%=path %>/page/meterial/selectContract.do" method="post" id="queryForm">
         <s:set name="page" value="%{contractPage}" />
		 <input type="hidden" id="pageNo" name="contractPage.pageNo" value="${page.pageNo}"/>
		 <input type="hidden" id="pageSize" name="contractPage.pageSize" value="${page.pageSize}"/>
<div class="search" >
<div class="searchContent" >
<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>广告商名称：</span>
                  <input type="text" name="contractQuery.customerName" id="customerName" value="${contractQuery.customerName}"/>
                  <span>合同名称：</span>
                  <input type="text" name="contractQuery.contractName" id="contractName" value="${contractQuery.contractName}"/>
                  <span>开始日期：</span>
                  		<input readonly="readonly" id="effectiveStartDate" name="contractQuery.effectiveStartDate"  value="${contractQuery.effectiveStartDateStr}"class="input_style2" type="text" style="width:80px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                  <span>截止日期：</span>
                  		<input readonly="readonly" id="effectiveEndDate"  name="contractQuery.effectiveEndDate" value="${contractQuery.effectiveStartDateStr}" class="input_style2" type="text" style="width:80px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" class="dot">选项</td>
        <td width="13%" align="center">合同名称</td>
        <td width="10%" align="center">合同编号</td>
        <td width="10%" align="center">合同号</td>
        <td width="10%" align="center">广告商</td>
        <td width="10%" align="center">送审单位</td>
        <td width="10%" align="center">合同金额</td>
		<td width="10%" align="center">广告审核文号</td>
		<td width="10%" align="center">合同开始日期</td>
		<td width="10%" align="center">合同截止日期</td>
		<td width="10%" align="center">状态</td>
    </tr>
   				 <c:if test="${contractPage.dataList != null && fn:length(contractPage.dataList) > 0}">
                    <c:forEach items="${contractPage.dataList}" var="contractInfo" varStatus="pl">
                        <tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
                            <td>                      
                                <input type="radio" value="${contractInfo.id}" name="contractInfo.locationId" id="contractInfo.locationId" onclick="selectContract('${contractInfo.id}','${contractInfo.contractName}')"/>
                             </td>
                            <td>
                                ${contractInfo.contractName}
                            </td>
                            <td>
                                ${contractInfo.contractNumber}
                            </td>
                            <td>
                                ${contractInfo.contractCode}
                            </td>
                            <td>
                             <c:forEach items="${customerPage}" var="cp" >
                            	<c:if test="${cp.id==contractInfo.customerId}">
                            	${cp.advertisersName}
                            	</c:if>
                            </c:forEach>
                            </td>
                            <td>
                                ${contractInfo.submitUnits}
                            </td>
                            <td>
                                ${contractInfo.financialInformation}
                            </td>
                            <td>
                                ${contractInfo.approvalCode}
                            </td>
                            <td>
                                <fmt:formatDate value="${contractInfo.effectiveStartDate}" dateStyle="medium"/>
					        </td>
                            <td>
                               <fmt:formatDate value="${contractInfo.effectiveEndDate}" dateStyle="medium"/>
					        </td>
                            <td>
                                <c:choose>
											<c:when test="${contractInfo.state==0}">
												待审核
											</c:when>
											<c:when test="${contractInfo.state==1}">
												已审核
											</c:when>
											<c:when test="${contractInfo.state==2}">
												审核未通过
											</c:when>
											<c:otherwise>
												下线
											</c:otherwise>
										</c:choose>
                            </td>

                           
							
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="11">
        <input type="button" class="btn" value="返 回" onclick="cancle();" />
        <jsp:include page="../../common/page.jsp" flush="true" />
        
    </td>
  </tr>
</table>
   
   </div>
</div>
</form>
</body>
</html>