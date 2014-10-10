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

<title>合同管理</title>
<script>

function query() {
        var effectiveStartDate = document.getElementById("effectiveStartDate").value;
        var effectiveEndDate = document.getElementById("effectiveEndDate").value;
        if(effectiveStartDate!=""){
          if(effectiveEndDate==""){
             alert("开始日期和截止日期必须同时输入");
             return;
          }
        }
        if(effectiveEndDate!=""){
          if(effectiveStartDate==""){
             alert("开始日期和截止日期必须同时输入");
             return;
          }
        }
        
        if(validateSpecialCharacterAfter($("#customerName").val())){
			alert("广告商名称不能包括特殊字符！");
			$("#customerName").focus();
			return ;
		}
		if(validateSpecialCharacterAfter($("#contractName").val())){
			alert("合同名称不能包括特殊字符！");
			$("#contractName").focus();
			return ;
		}
        document.forms[0].submit();
    }
 function goPage(pageNo) {
       document.getElementById("pageno").value=pageNo;
       document.forms[0].submit();
    }
 function addData() {
      window.location.href="<%=path%>/page/contract/intoAddContract.do";
    }
 function deleteData() {
	 if (getCheckCount("dataIds") <= 0) {
                alert("请勾选需删除的记录！");
                return;
     }
     var dataIds = getCheckValue("dataIds");	 
	 $.ajax({
                type:"post",
                async : false,
                url:"<%=request.getContextPath()%>/page/contract/checkDelContract.do?",
                data:{"dataIds":dataIds},//Ajax传递的参数
                success:function(mess)
                {
                	if(mess=="0")// 如果获取的数据不为空
                    {
                		var ret = window.confirm("您确定要删除吗？");
						 if (ret==1)
					     {
							//alert("可删");
							 document.forms[0].action="<%=path %>/page/contract/deleteContract.do";
                             document.forms[0].submit();
					     }
                    }
                    else
                    {
						alert("您要删除的合同还有订单未执行完毕，请确认后，再操作！");
						return;
                    }
                   
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
     
     
    }
    
    function exportData() {
	 if (getCheckCount("dataIds") <= 0) {
                alert("请勾选需导出的记录！");
                return;
     }
     var dataIds = getCheckValue("dataIds");
     var ret = window.confirm("您确定要导出吗？");
						 if (ret==1)
					     {
							window.location = "<%=request.getContextPath()%>/page/contract/exportContractBatch.do?dataIds="+dataIds ;
					     }      
    }
    
  function  exportContract(){
     var url = "<%=path %>/page/contract/exportContract.action";
	 var modelWin = window
			.showModalDialog(
					url,window,
					"resizable=no;status=no;scroll=no;center=yes;dialogHeight=180px;dialogWidth=600px");

     if (modelWin) {
		window.location = "<%=path%>/page/contract/queryContractList.action?message="+modelWin;
	 }
  }
    
  function modifyData(contractId) {
      window.location.href="<%=path %>/page/contract/initContract.do?contractId="+contractId;
    }
    function modifyDataPrecise(ployid) {
      //window.location.href="<%=path %>/page/ploy/initPloy.do?ployId="+ployid;
      window.location.href="<%=path %>/page/precise/listPrecise.do?method=getAllPreciseList&ployId="+ployid;
      //initPloy.do?method=initPloy&ployId=1
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
			structInfo+='							<iframe id="selectAdPositionFrame" name="selectAdPositionFrame" src="<%=request.getContextPath()%>'+'/page/ploy/queryAdPostionList.do?contract.id='+contractid+'" frameBorder="0" width="600px" height="540px"  scrolling="yes"></iframe>';
			structInfo+='					</div>';
			easyDialog.open({
			container : {
				header : '选择广告位',
				content : structInfo
			},
			overlay : false
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
 <form action="<%=path %>/page/contract/queryContractList8.do" method="post" id="queryForm">
 <!-- 
  <input type="hidden" id="pageno" name="contractPage.pageNo" value="${contractPage.pageNo}"/>
 <input type="hidden" id="pagesize" name="contractPage.pageSize" value="${contractPage.pageSize}"/>
  -->


         <s:set name="page" value="%{contractPage}" />
		 <input type="hidden" id="pageNo" name="contractPage.pageNo" value="${page.pageNo}"/>
		 <input type="hidden" id="pageSize" name="contractPage.pageSize" value="${page.pageSize}"/>


<div class="search">
<div class="path">首页 >> 合同管理 >> 合同维护</div>
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
                  		<input readonly="readonly" value="${contractQuery.effectiveStartDateStr}" id="effectiveStartDate" name="contractQuery.effectiveStartDate" class="input_style2" type="text" style="width:80px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                 		<img onclick="showDate('effectiveStartDate')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
                  <span>截止日期：</span>
                  		<input readonly="readonly" value="${contractQuery.effectiveEndDateStr}" id="effectiveEndDate"  name="contractQuery.effectiveEndDate" class="input_style2" type="text" style="width:80px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                 		<img onclick="showDate('effectiveEndDate')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="selectAllBox" onclick="selectAll(this, 'dataIds');"/></td>
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
                                <input type="checkbox" value="<c:out value='${contractInfo.id}'/>" name="dataIds"/>
                             </td>
                            <td>
                                <a href="javascript:modifyData('${contractInfo.id}');">${contractInfo.contractName}</a>
                            </td>
                            <td>
                                ${contractInfo.contractNumber}
                            </td>
                            <td>
                                ${contractInfo.contractCode}
                            </td>
                            <td>
                             <c:forEach items="${customerPage.dataList}" var="cp" >
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
                                ${contractInfo.effectiveStartDateShow}
                            </td>
                            <td>
                                ${contractInfo.effectiveEndDateShow}
                            </td>
                            <td>
                                <c:choose>
											<c:when test="${contractInfo.status==0}">
												待审核
											</c:when>
											<c:when test="${contractInfo.status==1}">
												已审核
											</c:when>
											<c:when test="${contractInfo.status==2}">
												审核未通过
											</c:when>
											<c:otherwise>
												下线
											</c:otherwise>
										</c:choose>
                            </td>
                            <!-- 
                            <td width="10%" align="center">
                             <c:forEach items="${customerPage.dataList}" var="cp" >
                            	<c:if test="${cp.id==contractInfo.customerId}">
                            	${cp.advertisersName}
                            	</c:if>
                            </c:forEach>
							</td>
							<td width="10%" align="center">
							
							<c:forEach items="${pageAdPosition.dataList}" var="padposition" >
                            	<c:if test="${padposition.id==ployInfo.positionId}">
                            	
                                	<c:forEach items="${pageAdPositionType.dataList}" var="padpositionType" >
	                            	<c:if test="${padposition.positionTypeId==padpositionType.id}">
	                            		<c:if test="${padpositionType.deliveryType==1}">
	                            	     <a href="javascript:modifyDataPrecise('${ployInfo.ployId}');">精准</a>
	                            	     </c:if>
	                            	</c:if>
	                                </c:forEach>
                            	</c:if>
                            </c:forEach>
							
							
							</td>
                             -->
                           
							
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="11">
		<input type="button" value="删除" class="btn" onclick="javascript:deleteData();"/>
        <input type="button" value="添加" class="btn" onclick="javascript:addData();"/>
        <input type="button" value="导出" class="btn" onclick="javascript:exportData();"/>
        <jsp:include page="../../common/page.jsp" flush="true" />
		<!-- 
		<div class="page">
					<a >当前第${contractPage.pageNo }/${contractPage.totalPage }页</a>&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${contractPage.pageNo==1&&contractPage.totalPage!=1}">
							<a href="#" onclick="javascript:goPage('${contractPage.pageNo+1}')">下一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${contractPage.totalPage}')">末页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${contractPage.pageNo==contractPage.totalPage&&contractPage.totalPage!=1 }">
							<a href="#" onclick="javascript:goPage('${1}')">首页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${contractPage.pageNo-1}')">上一页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${contractPage.pageNo>1&&contractPage.pageNo<contractPage.totalPage }">
							<a href="#" onclick="javascript:goPage('${1}')">首页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${contractPage.pageNo-1}')">上一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${contractPage.pageNo+1}')">下一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${contractPage.totalPage}')">末页</a>&nbsp;&nbsp;
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