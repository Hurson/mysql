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

   function selectad(padname,padid,padisText,padisAllTime,padisChannel){
    
    	window.dialogArguments.document.getElementById("ploy.positionName").value=padname;
    	window.dialogArguments.document.getElementById("ploy.positionId").value=padid;
    //	parent.document.getElementById("padposition.isText").value=padisText;
    	window.dialogArguments.document.getElementById("padposition.isAllTime").value=padisAllTime;
    	window.dialogArguments.document.getElementById("padposition.isChannel").value=padisChannel;
    	if (padisAllTime!=null && padisAllTime==1)
    	{
    		window.dialogArguments.document.getElementById("ploy.startTime").value="00:00:00";
    		window.dialogArguments.document.getElementById("ploy.endTime").value="23:59:59";
    		window.dialogArguments.document.getElementById("ploy.startTime").disabled=true;
    		window.dialogArguments.document.getElementById("ploy.endTime").disabled=true;
    	}
    	else
    	{
    		window.dialogArguments.document.getElementById("ploy.startTime").disabled="";
    		window.dialogArguments.document.getElementById("ploy.endTime").disabled="";
    	}
  //  	parent.document.getElementById("ploy.ruleName").value="";
   // 	parent.document.getElementById("ploy.ruleId").value="";
    	
    	window.close();
    }
	function cancle(){
    	
        window.close();
        //parent.easyDialog.close();
    }

</script>
</head>

<body class="mainBody">

<form action="<%=path %>/page/ploy/addqueryAdPostionList.do" method="post" id="queryForm">
 <s:set name="page" value="%{pageAdPosition}" />
		 <input type="hidden" id="pageNo" name="pageAdPosition.pageNo" value="${page.pageNo}"/>
		 <input type="hidden" id="pageSize" name="pageAdPosition.pageSize" value="${page.pageSize}"/>

 <input type="hidden" id="contract.id" name="contract.id" value="${contract.id}"/>
<div class="search">
<div class="path">首页 >> 投放策略管理 >> 策略维护>>选择广告位</div>
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
        <td width="28%" align="center">广告位名称</td>
        <td width="10%" align="center">是否影片</td>
        <td width="10%" align="center">是否频道</td>
       <td width="16%" align="center">是否回看频道</td>
        <td width="16%" align="center">是否回看栏目</td>
         <td width="16%" align="center">是否回看产品</td>
    </tr>
   				 <c:if test="${pageAdPosition.dataList != null && fn:length(pageAdPosition.dataList) > 0}">
                    <c:forEach items="${pageAdPosition.dataList}" var="padpostion" varStatus="pc">
                        <tr <c:if test="${pc.index%2==1}">class="sec"</c:if>>
                            <td>
                                <input type="radio" name="contractid" value="合同2" onclick="selectad('${padpostion.positionName}','${padpostion.id}','${padpostion.isText}','${padpostion.isAllTime}','${padpostion.isChannel}');">
                             </td>
                            <td>
                                ${padpostion.positionName}
                            </td>
                             <td  align="center">
                                <c:choose>    
							   <c:when test="${padpostion.isAsset==1}">是 
							   </c:when>  							     
							   <c:otherwise> 否  
							   </c:otherwise>  
							</c:choose>  
                            </td>
					        <td  align="center">
					        
					        
					        <c:choose>    
							   <c:when test="${padpostion.isChannel==1}">是 
							   </c:when>  							     
							   <c:otherwise> 否  
							   </c:otherwise>  
							</c:choose>  
                                                       
                            </td>				     
					        <td width="16%" align="center">
					          <c:choose>    
							   <c:when test="${padpostion.isPlayback==1}">是 
							   </c:when>  							     
							   <c:otherwise> 否  
							   </c:otherwise>  
							</c:choose>  
							</td>
					        <td width="16%" align="center">
					            <c:choose>    
							   <c:when test="${padpostion.isColumn==1}">是 
							   </c:when>  							     
							   <c:otherwise> 否  
							   </c:otherwise>  
							</c:choose>  
							</td>
							 <td width="16%" align="center">
					            <c:choose>    
							   <c:when test="${padpostion.isLookbackProduct==1}">是 
							   </c:when>  							     
							   <c:otherwise> 否  
							   </c:otherwise>  
							</c:choose>  
							</td>
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="7">
		<input type="button" class="btn" value="返 回" onclick="cancle();" />
				&nbsp;&nbsp;
					        <jsp:include page="../common/page.jsp" flush="true" />
    <!--     <div class="page"
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
         -->
    </td>
  </tr>
</table>
</div>
</div>
</form>
</body>
</html>