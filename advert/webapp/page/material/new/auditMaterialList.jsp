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
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>


<title>素材管理</title>
<script>

function query() {

        if(validateSpecialCharacterAfter(document.getElementById("meterialQuery.resourceName").value)){
			alert("素材名称不能包括特殊字符！");
			return ;
		}
		if(validateSpecialCharacterAfter(document.getElementById("meterialQuery.advertPositionName").value)){
			alert("广告位名称不能包括特殊字符！");
			return ;
		}
		if(validateSpecialCharacterAfter(document.getElementById("meterialQuery.customerName").value)){
			alert("广告商名称不能包括特殊字符！");
			return ;
		}

        document.forms[0].submit();
    }
 function goPage(pageNo) {
       document.getElementById("pageno").value=pageNo;
       document.forms[0].submit();
    }
 function addData() {
      window.location.href="<%=path%>/page/meterial/intoAddMaterial.do";
    }
 function deleteData() {
	 if (getCheckCount("dataIds") <= 0) {
                alert("请勾选需删除的记录！");
                return;
     }
	 var ret = window.confirm("您确定要删除吗？");
	 if (ret==1)
     {
		 document.forms[0].action="<%=path %>/page/meterial/deleteMaterial.do";
         document.forms[0].submit();
     }
    }
  function modifyData(materialId) {
      window.location.href="<%=path %>/page/meterial/initAuditMaterial.do?materialId="+materialId;
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
 <form action="<%=path %>/page/meterial/auditMaterialList.do" method="post" id="queryForm">
 <input type="hidden" id="pageNo" name="page.pageNo" value="${page.pageNo}"/>
 <input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize}"/>

<div class="search">
<div class="path">首页 >> 素材管理 >> 素材审核</div>
<div class="searchContent" >
<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
                  <span>素材名称：</span>
                  <input type="text" name="meterialQuery.resourceName" id="meterialQuery.resourceName" value="${meterialQuery.resourceName}"/>
                  <span>广告位名称：</span>
                  <input type="text" name="meterialQuery.advertPositionName" id="meterialQuery.advertPositionName" value="${meterialQuery.advertPositionName}"/>
                  <span>广告商：</span>
                  <input type="text" name="meterialQuery.customerName" id="meterialQuery.customerName" value="${meterialQuery.customerName}"/>
                  <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<table cellspacing="1" class="searchList">
    <tr class="title">
		
		<td width="15%" align="center">素材名称</td>
        <td width="10%" align="center">素材类型</td>
        <td width="15%" align="center">广告商</td>
        <td width="8%" align="center">素材分类</td>
        <td width="25%" align="center">广告位</td>
		<td width="10%" align="center">状态</td>
		<td width="10%" align="center">创建日期</td>
    </tr>
   				 <c:if test="${page.dataList != null && fn:length(page.dataList) > 0}">
                    <c:forEach items="${page.dataList}" var="meterialInfo" varStatus="pl">
                        <tr <c:if test="${pl.index%2==1}">class="sec"</c:if> align="center">
                            <td>
                                <a href="javascript:modifyData('${meterialInfo.id}');">${meterialInfo.resourceName}</a>
                            </td>
                            <td>
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
                            </td>
                            <td>
                                ${meterialInfo.customerName}
                            </td>
                            <td>								
								<c:forEach items="${materialCategoryList}" var="cp" >
                            	    <c:if test="${cp.id==meterialInfo.categoryId}">
                            	          ${cp.categoryName}
                            	    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                ${meterialInfo.advertPositionName}
                            </td>
                                                      
                            <td>
                                <c:choose>
											<c:when test="${meterialInfo.state=='0'}">
												待审核
											</c:when>
											<c:when test="${meterialInfo.state=='1'}">
												审核不通过
											</c:when>
											<c:when test="${meterialInfo.state=='2'}">
												上线
											</c:when>
											<c:otherwise>
												下线
											</c:otherwise>
										</c:choose>
                            </td>
                            <td>
                                <fmt:formatDate value="${meterialInfo.createTime}" dateStyle="medium"/>
                            </td>
                           
							
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
    <td colspan="7">

        
       <jsp:include page="../../common/page.jsp" flush="true" />
    </td>
  </tr>
</table>
</div>
</div>
</form>
</body>

</html>