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
<link id="maincss"  rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" media="all"/>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
	
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/tools.js"></script>



<title>策略维护</title>
<script>

function query() {
        document.forms[0].submit();
        
       // $("queryForm").submit();
    }

 function addData() {
      window.location.href="<%=path %>/page/ploy/insertPloy.do";
     //  window.location.href="<%=path %>/page/ploy/initPloy.do?ployId=0";
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
                url:"<%=request.getContextPath()%>/page/ploy/checkDelPloy.do?",
                data:{"dataIds":dataIds},//Ajax传递的参数
                success:function(mess)
                {
                	if(mess=="0")// 如果获取的数据不为空
                    {
                		var ret = window.confirm("您确定要删除吗？");
						 if (ret==1)
					     {
							//alert("可删");
							 document.forms[0].action="<%=path %>/page/ploy/deletePloy.do";
					         document.forms[0].submit();
					     }
                    }
                    else
                    {
						alert("您要删除的策略还有订单未执行完毕，请确认后，再操作！");
						return;
                    }
                   
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
	 
	 
    }
  function modifyData(ployid, channelGroupType, state) {
      window.location.href="<%=path %>/page/ploy/initPloy.do?ployId="+ployid+"&channelGroupType="+channelGroupType + "&state=" + state;
     
    }
  function modifyDataPrecise(ployid,positionId) {
   	 window.location.href="<%=path %>/page/precise/queryPreciseList.do?ployId="+ployid+"&positionId="+positionId;
  }
function selectContract() {
          	var structInfo = '';
			structInfo+='					<div style="margin:0px;padding:0px;">';
			structInfo+='							<iframe id="selectContraceFrame" name="selectContraceFrame" src="<%=request.getContextPath()%>'+'/page/ploy/queryContractList.do'+'" frameBorder="0" width="800px" height="540px"  scrolling="yes"></iframe>';
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
			structInfo+='							<iframe id="selectAdPositionFrame" name="selectAdPositionFrame" src="<%=request.getContextPath()%>'+'/page/ploy/queryAdPostionList.do?contract.id='+contractid+'" frameBorder="0" width="800px" height="540px"  scrolling="yes"></iframe>';
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
			structInfo+='							<iframe id="selectRuleFrame" name="selectRuleFrame" src="<%=request.getContextPath()%>'+'/page/ploy/queryRuleList.do?contract.id='+contractid+'&adPosition.positionName='+adName+'" frameBorder="0" width="800px" height="540px"  scrolling="yes" align="top"></iframe>';
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
 <form action="<%=path %>/page/ploy/queryPloyList.do" method="post" id="queryForm">
 <s:set name="page" value="%{pagePloy}" />
		 <input type="hidden" id="pageNo" name="pagePloy.pageNo" value="${page.pageNo}"/>
		 <input type="hidden" id="pageSize" name="pagePloy.pageSize" value="${page.pageSize}"/>

<div class="search">
<div class="path">首页 >> 投放策略管理 >> 策略维护</div>
<div class="searchContent" >
<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>策略名称：</span><input onkeypress="return validateSpecialCharacter();" type="text" name="ploy.ployName" id="ploy.ployName" value="${ploy.ployName}"/>
					
	<!--  <input type="text" name="contract.contractName" id="contract.contractName" value="${contract.contractName}" class="e_input_add" readonly="readonly"
					onfocus="this.className='e_inputFocus'" onclick="selectContract();"	onblur="this.className='e_input_add'"  />
					 <input type="hidden" id="ploy.contractId" name="ploy.contractId" value="${ploy.contractId}"/>
					  --> 
	  <span>广告位：</span><input onkeypress="return validateSpecialCharacter();" type="text" name="adPosition.positionName" id="adPosition.positionName" value="${adPosition.positionName}"/>
				
	 				
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
        <td width="30%" align="center">策略名称</td>
        <td width="30%" align="center">广告商</td>
        <td width="30%" align="center">广告位</td>
       <!--  <td width="25%" align="center">营销规则</td> -->
        <td width="5%" align="center">状态</td>
    </tr>
   				 <c:if test="${pagePloy.dataList != null && fn:length(pagePloy.dataList) > 0}">
                    <c:forEach items="${pagePloy.dataList}" var="ployInfo" varStatus="pl">
                        <tr <c:if test="${pl.index%2==1}">class="sec"</c:if>>
                            <td>
                                <input type="checkbox" value="<c:out value='${ployInfo.ployId}'/>" name="dataIds"/>
                             </td>
                            <td align="center">
                                <a href="javascript:modifyData('${ployInfo.ployId}','${ployInfo.channelGroupType}','${ployInfo.state}');">${ployInfo.ployName}</a>
                            </td>
                            <td width="20%" align="center">
                             <c:forEach items="${pageCustomer.dataList}" var="pcustomer" >
                            	<c:if test="${pcustomer.id==ployInfo.customerId}">
                            	     ${pcustomer.advertisersName}
                            	</c:if>
                            </c:forEach>
							</td>
					        <td width="15%" align="center">
					         <c:forEach items="${pageAdPosition.dataList}" var="padposition" >
                            	<c:if test="${padposition.id==ployInfo.positionId }">
                            	${padposition.positionName}
                            	</c:if>
                            </c:forEach>
							</td>
					     <!--    <td width="25%" align="center">
					         <c:forEach items="${pageRule.dataList}" var="prule" >
                            	<c:if test="${prule.marketingRuleId==ployInfo.ruleId}">
                            	${prule.marketingRuleName}
                            	</c:if>
                            </c:forEach>
							</td>
							 -->
					     	 <td width="10%" align="center">
					        <c:if test="${ployInfo.state==1 }">
	                            	  通过
	                            	</c:if>
	                            	<c:if test="${ployInfo.state==2 }">
	                            	  驳回
	                            	</c:if>
	                               	<c:if test="${ployInfo.state==0 }">
	                            	 待审
	                            	</c:if> 
	                            	<c:if test="${ployInfo.state==3 }">
	                            	 已关联
	                            	</c:if> 	
							</td>
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="5">
		
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