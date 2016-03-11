<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<input id="projetPath" type="hidden" value="<%=path%>" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
    <title></title>
    <link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
    <script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
	<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
	<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>
	<script type="text/javascript" src="<%=path%>/js/util/tools.js"></script>
<script type="text/javascript">
var selectData="";
	function selectChannel(){
    	
    	if (getCheckCount("serviceIds")==0)
    	{
    		alert("请选择频道");
    		return ;
    	}
    	if(selectData==""){
    	    selectData=getCheckValue("serviceIds");
    	}
    	document.forms[0].action="<%=path %>/dchannelGroup/saveChannelGroupRef.do";
    	document.forms[0].submit(); 
    	parent.refreshChannelList();
        parent.easyDialog.close();
    }
    
    function closeDialog(){
    	
        parent.easyDialog.close();
    }
	 function query() {
	 
	    if(validateSpecialCharacterAfter(document.getElementById("selectChannelQuery.channelName").value)){
			alert("频道名称不能包括特殊字符！");
			return ;
		}
        document.forms[0].submit();
    }

function addIds(id){
    	selectData = document.getElementById("selServiceIds").value
    	if(id.checked){
    	//选中
    	if(selectData==""){
           selectData=id.value;
        }else{
           selectData=selectData+","+id.value;
        }
        document.getElementById("selServiceIds").value=selectData;
        
    	}else{
    	//撤销
    	   if(selectData!=""){
    	      var newdate= "";
    	      var ss= new Array();
              ss = selectData.split(",");
              for(var i=0;i<ss.length;i++){
                 if(ss[i]==id.value){
                     
                 }else{
                   if(newdate==""){
                      newdate=ss[i];
                   }else{
                      newdate=newdate+","+ss[i];
                   }                 
                 }
              }
              selectData=newdate;
              document.getElementById("selServiceIds").value=selectData;

           }
    	}
        

    }
    

//列表全选
function selectAll2(checkBoxObject, elementName){
selectData = document.getElementById("selServiceIds").value
    var idss = document.getElementsByName(elementName);
    var idss2 = document.getElementsByName(elementName).value;
    var ifChecked = checkBoxObject.checked;

    for(i = 0; i < idss.length; i++){
    	//alert("idss[i]:"+idss[i].value);
        	if (idss[i].disabled)
        	{
        	//撤销
    	   if(selectData!=""){
    	      var newdate= "";
    	      var ss= new Array();
              ss = selectData.split(",");
              for(var i=0;i<ss.length;i++){
                 if(ss[i]==idss[i].value){
                     
                 }else{
                   if(newdate==""){
                      newdate=ss[i];
                   }else{
                      newdate=newdate+","+ss[i];
                   }                 
                 }
              }
              selectData=newdate;
              document.getElementById("selServiceIds").value=selectData;

           }
        	continue ;
        	}
        idss[i].checked = ifChecked;
        //选中
    	if(selectData==""){
           selectData=idss[i].value;
        }else{
           selectData=selectData+","+idss[i].value;
        }
        document.getElementById("selServiceIds").value=selectData;
    }
}
    </script>
</head>

<body class="mainBody">
<form action="<%=path %>/dchannelGroup/selectChannel.do" method="post" id="queryForm">
<s:set name="page" value="selectchannelPage" />
 <input type="hidden" id="pageNo" name="selectchannelPage.pageNo" value="${selectchannelPage.pageNo}"/>
 <input type="hidden" id="pageSize" name="selectchannelPage.pageSize" value="${selectchannelPage.pageSize}"/>
 <input type="hidden" id="channelGroupType" name="channelGroupType" value="${channelGroupType}"/>
 <input type="hidden" id="channelGroupId"  name="channelGroupId" value="${channelGroupId}"/>
 <input type="hidden" id="channelGroupIdTemp" name="channelGroupIdTemp"  value="${channelGroupIdTemp}"/>

<div class="search">
        <div class="path">首页 >> 系统管理 >>频道组管理>>关联频道&gt;&gt;选择频道</div>
        <div class="searchContent">

<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
        <span>频道名称：</span>
        <input type="text" name="selectChannelQuery.channelName" id="selectChannelQuery.channelName" value="${selectChannelQuery.channelName}"/>
                  
        <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>

        <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
        <table width="100%" cellspacing="1" class="searchList" id="bm">
            <tr class="title">            	
              <td height="28" class="dot"><input type="checkbox" name="selectAllBox" onclick="selectAll2(this, 'serviceIds');"/></td>		
				<td width="20%" align="center">频道名称</td>
                <td width="20%" align="center">类型</td>
                <td width="15%" align="center">SERVICE_ID</td>
                <td width="15%" align="center">TS_ID</td>
                <td width="15%" align="center">NETWORK_ID</td>
                <td width="15%" align="center">是否回放频道</td>
                <input id="selServiceIds" name="selServiceIds" type="hidden"  value="${selServiceIds}"/>
            </tr>

						<c:if test="${selectchannelPage.dataList != null && fn:length(selectchannelPage.dataList) > 0}">
							<c:forEach var="channelInfo" items="${selectchannelPage.dataList}" varStatus="status">
								<tr <c:if test="${status.index%2==1}">class="sec"</c:if>>
									<td><input onclick="addIds(this);" <c:forEach items="${selChannelIdList}" var="ws2" >
                   <c:if test="${ws2==channelInfo.channelId}">
                    checked
                   </c:if>
                   </c:forEach> type="checkbox"  name="serviceIds"  value="${channelInfo.serviceId}" /></td>									 
									<td>${channelInfo.channelName}</td>
									<td>
									
									<c:choose>
											<c:when test="${channelInfo.channelType == '1'}">
												视频直播类业务
											</c:when>
											<c:when test="${channelInfo.channelType == '2'}">
												音频直播类业务
											</c:when>
											<c:otherwise>
												无
											</c:otherwise>
							  	 </c:choose>
									
									</td>
									<td>${channelInfo.serviceId}</td>
									<td>${channelInfo.tsId}</td>
									<td>${channelInfo.networkId}</td>
									<td>
										<c:choose>
											<c:when test="${channelInfo.isPlayBack==0}">
												否
											</c:when>
											<c:otherwise>
												是
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						
			
           
           <tr>
				<td colspan="8">
				            <input type="button" value="选择" class="btn" onclick="selectChannel();"/>
          &nbsp;&nbsp;
							<input type="button" value="返回" class="btn" onclick="closeDialog();"/>&nbsp;&nbsp;							
							<jsp:include page="../../common/page.jsp" flush="true" />
                           
						</td>
					</tr>
        </table>
    </div>
</div>
</form>
</body>
</html>