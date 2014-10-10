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
 

<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
	
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>

<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/tools.js"></script>
 -->
<link id="maincss"  rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" media="all"/>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>

<title>策略维护</title>
<script>

function query() {
        document.forms[0].submit();
        
       /// $("queryForm").submit();
    }

 function addData() {
      window.location.href="<%=path %>/page/ploy/getNoAdPloyById.do?noAdPloy.id=0";
    
    }
 function deleteData() {
	 if (getCheckCount("dataIds") <= 0) {
                alert("请勾选需删除的记录！");
                return;
     }
	 var ret = window.confirm("您确定要删除吗？");
	 if (ret==1)
     {
		 document.forms[0].action="<%=path %>/page/ploy/deleteNoAdPloy.do";
         document.forms[0].submit();
     }
    }
  function modifyData(ployid) {
      window.location.href="<%=path %>/page/ploy/getNoAdPloyById.do?noAdPloy.id="+ployid;
     
    }
    function modifyDataPrecise(ployid,preciseType) {
    window.location.href="<%=path %>/page/precise/queryPreciseList.do?ployId="+ployid+"&preciseType="+preciseType;
  
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
 <form action="<%=path %>/page/ploy/queryNoAdPloyList.do" method="post" id="queryForm">
 <s:set name="page" value="%{pageNoAdPloy}" />
		 <input type="hidden" id="pageNo" name="pageNoAdPloy.pageNo" value="${page.pageNo}"/>
		 <input type="hidden" id="pageSize" name="pageNoAdPloy.pageSize" value="${page.pageSize}"/>

<div class="search">
<div class="path">首页 >> 投放策略管理 >> 禁播策略维护</div>
<div class="searchContent" >
<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>策略名称：</span><input onkeypress="return validateSpecialCharacter();" type="text" name="noAdPloy.ployname" id="noAdPloy.ployname" value="${noAdPloy.ployname}"/>
	 				
	<!--  <input type="text" name="contract.contractName" id="contract.contractName" value="${contract.contractName}" class="e_input_add" readonly="readonly"
					onfocus="this.className='e_inputFocus'" onclick="selectContract();"	onblur="this.className='e_input_add'"  />
					 <input type="hidden" id="ploy.contractId" name="ploy.contractId" value="${ploy.contractId}"/>
					  --> 
	  <span>广告位：</span>
	  <select id="noAdPloy.positionid" name="noAdPloy.positionid">
	   <option value='' > 选择广告位</option>
	   <c:forEach items="${pageAdPosition.dataList}" var="position" >
	    
                            
                           <option value='${position.id}' <c:if test="${noAdPloy.positionid==position.id}">
                            	selected
                            	</c:if>> 
                            	${position.positionName} 
                           </option>
                            
                            </c:forEach>
	  </select>
	  <span>TVN号：</span>
	  <input onkeypress="return validateSpecialCharacter();" type="text" name="noAdPloy.tvn" id="noAdPloy.tvn" value="${noAdPloy.tvn}"/>
			
	 				
	 <!--  <span>营销规则：</span><input type="text" name="rule.marketingRuleName" id="rule.marketingRuleName" value="${rule.marketingRuleName}" class="e_input_add" readonly="readonly"
					onfocus="this.className='e_inputFocus'" onclick="selectRule();"	onblur="this.className='e_input_add'"  />
 -->
        <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
<c:if test="${message != null}">
			 <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
</c:if>
</div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="selectAllBox" onclick="selectAll(this, 'dataIds');"/></td>
        <td width="28%" align="center">策略名称</td>
        <td width="28%" align="center">广告位</td>
        <td width="20%" align="center">禁播TVN号</td>
       <!--  <td width="25%" align="center">营销规则</td> -->
        <td width="10%" align="center">生效时间</td>
        <td width="10%" align="center">失效时间</td>
    </tr>
   				 <c:if test="${page.dataList != null && fn:length(page.dataList) > 0}">
                    <c:forEach items="${page.dataList}" var="ployInfo" varStatus="pl">
                        <tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
                            <td>
                                <input type="checkbox" value="<c:out value='${ployInfo.id}'/>" name="dataIds"/>
                             </td>
                            <td>
                                <a href="javascript:modifyData('${ployInfo.id}');">${ployInfo.ployname}</a>
                            </td>
                            <td width="20%" align="center">
                              <c:forEach items="${pageAdPosition.dataList}" var="position" >
                         		    <c:if test="${ployInfo.positionid==position.id}">
                            		${position.positionName}
                            		</c:if>
                            
                               </c:forEach>
							</td>
					        <td width="20%" align="center">
					       ${ployInfo.tvn}
							</td>
					     <!--    <td width="25%" align="center">
					         <c:forEach items="${pageRule.dataList}" var="prule" >
                            	<c:if test="${prule.marketingRuleId==ployInfo.ruleId}">
                            	${prule.marketingRuleName}
                            	</c:if>
                            </c:forEach>
							</td>
							 -->
					        <td width="15%" align="center">
					        <fmt:formatDate value="${ployInfo.startDate}"
											pattern="yyyy-MM-dd" />
							</td>
					        <td width="15%" align="center">
					        <fmt:formatDate value="${ployInfo.endDate}"
											pattern="yyyy-MM-dd" />
					    
							</td>
							
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="6">
		
        <input type="button" value="添加" class="btn" onclick="javascript:addData();"/>&nbsp;&nbsp;
        <input type="button" value="删除" class="btn" onclick="javascript:deleteData();"/>
		
								&nbsp;&nbsp;
					        <jsp:include page="../common/page.jsp" flush="true" />
	<!--   <div class="page"
					style="background: url(<%=path %>/images/bottom.jpg) repeat-x; text-align: right;">
					<a href="#">当前第${pagePloy.pageNo }/${pagePloy.totalPage }页</a>&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${pagePloy.pageNo==1&&pagePloy.totalPage!=1}">
							<a href="#" onclick="javascript:goPage('${pagePloy.pageNo+1}')">下一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pagePloy.totalPage}')">末页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${pagePloy.pageNo==pagePloy.totalPage&&pagePloy.totalPage!=1 }">
							<a href="#" onclick="javascript:goPage('${1}')">首页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pagePloy.pageNo-1}')">上一页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${pagePloy.pageNo>1&&pagePloy.pageNo<pagePloy.totalPage }">
							<a href="#" onclick="javascript:goPage('${1}')">首页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pagePloy.pageNo-1}')">上一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pagePloy.pageNo+1}')">下一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pagePloy.totalPage}')">末页</a>&nbsp;&nbsp;
						</c:when>
					</c:choose>
				
        </div>
         -->  
    </td>
  </tr>
</table>
</div>
</div>
</form>
</body>

</html>