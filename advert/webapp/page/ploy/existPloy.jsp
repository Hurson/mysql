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

<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>


<title>策略维护</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    $("#queryMarketingRuleButton").click(function(){
     	$("#queryMarketingRuleForm").submit();
     });
});
function query() {
        document.forms[0].submit();
        
       /// $("queryForm").submit();
    }
 function goPage(pageNo) {
        $("#pageno").value = pageNo;
       document.forms[0].submit();
        
       /// $("queryForm").submit();
          alert($("#pageno").value);
    }
 function addData() {
      window.location.href="<%=path %>/page/ploy/insertPloy.do";
      
    }
 function deleteData() {
	 if (getCheckCount("dataIds") <= 0) {
                alert("请勾选需删除的记录！");
                return;
     }
	 var ret = window.confirm("您确定要删除吗？");
	 if (ret==1)
     {
		 document.forms[0].action="<%=path %>/page/ploy/deletePloy.do";
         document.forms[0].submit();
     }
    }
  function modifyData(ployid) {
      window.location.href="<%=path %>/page/ploy/initPloy.do?method=initPloy&ployId="+ployid;
      //initPloy.do?method=initPloy&ployId=1
    }
function selectContract() {
          	var structInfo = '';
			structInfo+='					<div style="margin:0px;padding:0px;">';
			structInfo+='							<iframe id="selectContraceFrame" name="selectContraceFrame" src="<%=request.getContextPath()%>'+'/page/ploy/queryContractList.do'+'" frameBorder="0" width="600px" height="600px"  scrolling="yes"></iframe>';
			structInfo+='					</div>';
			easyDialog.open({
			container : {
				header : '选择合同',
				content : structInfo
			},			
			overlay : false
		});
	 } 
 	function selectAdPosition() {
          	var structInfo = '';
			var contractname = document.getElementById("contract.contractName").value;
			var contractid= document.getElementById("ploy.contractId").value;
			
          	//$("#content").html('请单击【绑定广告商】文本框进行广告商绑定');
			//$( "#system-dialog" ).dialog({
		   //   	modal: true
		    //});
          	structInfo+='					<div style="margin:0px;padding:0px;width:600px">';
			structInfo+='							<iframe id="selectAdPositionFrame" name="selectAdPositionFrame" src="<%=request.getContextPath()%>'+'/page/ploy/queryAdPostionList.do?contract.id='+contractid+'" frameBorder="0" width="600px" height="600px"  scrolling="yes"></iframe>';
			structInfo+='					</div>';
			easyDialog.open({
			container : {
				header : '选择广告位',
				content : structInfo
			},
			overlay : false
		});
	 } function selectRule() {
          	var structInfo = '';
		//	var adname = document.getElementById("ploy.adname").value;
			var contractid= document.getElementById("ploy.contractId").value;
			
			var adName = document.getElementById("adPosition.positionName").value;
			
          	structInfo+='					<div style="margin:0px;padding:0px;">';
			structInfo+='							<iframe id="selectRuleFrame" name="selectRuleFrame" src="<%=request.getContextPath()%>'+'/page/ploy/queryRuleList.do?contract.id='+contractid+'&adPosition.positionName='+adName+'" frameBorder="0" width="600px" height="600px"  scrolling="yes" align="top"></iframe>';
			structInfo+='					</div>';
			easyDialog.open({
			container : {
				header : '选择营销规则',
				content : structInfo
			},
			overlay : true
		});
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
 <form action="<%=path %>/page/ploy/queryExistPloyList.do" method="post" id="queryForm">
 <input type="hidden" id="pageno" name="pagePloy.pageNo" value="${pagePloy.pageNo}"/>
            <input type="hidden" id="pagesize" name="pagePloy.pageSize" value="${pagePloy.pageSize}"/>

<div class="search">
<div class="path">首页 >> 投放策略管理 >> 策略维护</div>
<div class="searchContent" >
  <input type="hidden" id="ploy.contractId" name="ploy.contractId" value="${ploy.contractId}"/>
					 <input type="hidden" id="adPosition.positionName" name="adPosition.positionName" value="${adPosition.positionName}"/>
					 <input type="hidden" id="rule.marketingRuleName" name="rule.marketingRuleName" value="${rule.marketingRuleName}"/>
	
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="selectAllBox" onclick="selectAll(this, 'dataIds');"/></td>
        <td width="15%" align="center">策略名称</td>
        <td width="10%" align="center">合同</td>
        <td width="10%" align="center">广告位</td>
        <td width="25%" align="center">营销规则</td>
        <td width="15%" align="center">开始时间段</td>
        <td width="15%" align="center">结束时间段</td>
		
    </tr>
   				 <c:if test="${pagePloy.dataList != null && fn:length(pagePloy.dataList) > 0}">
                    <c:forEach items="${pagePloy.dataList}" var="ployInfo" varStatus="pl">
                        <tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
                            <td>
                                <input type="checkbox" value="<c:out value='${ployInfo.ployId}'/>" name="dataIds"/>
                             </td>
                            <td>
                                <a href="javascript:modifyData('${ployInfo.ployId}');">${ployInfo.ployName}</a>
                            </td>
                            <td width="10%" align="center">
                             <c:forEach items="${pageContract.dataList}" var="pcontract" >
                            	<c:if test="${pcontract.id==ployInfo.contractId}">
                            	${pcontract.contractName}
                            	</c:if>
                            </c:forEach>
							</td>
					        <td width="10%" align="center">
					         <c:forEach items="${pageAdPosition.dataList}" var="padposition" >
                            	<c:if test="${padposition.id==ployInfo.positionId}">
                            	${padposition.positionName}
                            	</c:if>
                            </c:forEach>
							</td>
					        <td width="25%" align="center">
					         <c:forEach items="${pageRule.dataList}" var="prule" >
                            	<c:if test="${prule.marketingRuleId==ployInfo.ruleId}">
                            	${prule.marketingRuleName}
                            	</c:if>
                            </c:forEach>
							</td>
					        <td width="15%" align="center">
					        ${ployInfo.startTime}
							</td>
					        <td width="15%" align="center">
					        ${ployInfo.endTime}
							</td>
						
                        </tr>
                    </c:forEach>
                </c:if>


  
</table>
</div>
</div>
</form>
</body>

</html>