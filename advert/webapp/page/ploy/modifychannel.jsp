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
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<title></title>
 <script type='text/javascript' src='<%=request.getContextPath()%>/js/jquery.min.js'></script>
    <script type='text/javascript' src='<%=request.getContextPath()%>/js/avit.js'></script>
<script type="text/javascript">
var childAreas=[];
var channels =  new Array();
var areaid  = location.search.substr(8,location.search.length-8);
window.onload = function() {
	channels = parent.areaChannels[areaid].channels;
	
	 refreshChannelList();
	}
    var $ = function(id){
        return document.getElementById(id);
    };
    
    
    /*
	 * 刷新区域列表
	 * */
    function refreshChannelList()
	{
	   
		if( parent.areaChannels[areaid].channels!=null &&  parent.areaChannels[areaid].channels.length > 0)
		{
		var channelList=         "<table cellspacing='1' class='searchList'>";
		channelList=channelList+"<tr class=title>";
	    channelList=channelList+  "<td colspan='5'>已选频道</td>";
	    channelList=channelList+"</tr>";
	    channelList=channelList+"<tr class='title'>";
	    channelList=channelList+"    <td ><input type='checkbox' id='channelAll'  name='channelAll' onclick='selectAll(this,'channelids');'/></td>";
	    channelList=channelList+"    <td >频道名称</td>";
	    channelList=channelList+"    <td>频道编码</td>";
	    channelList=channelList+"    <td>serviceid</td>";
		channelList=channelList+"	<td>操作</td>";
	    channelList=channelList+"  </tr>";
	    
	  // alert(areaChannels.length);
	    for (var i=0;i<parent.areaChannels[areaid].channels.length;i++)
	    {	
	   // alert(areaChannels[i].areaCode);
		     channelList=channelList+"  <tr>";
		      channelList=channelList+"    <td><input type='checkbox' id='channelids' name='channelids' value='"+parent.areaChannels[areaid].channels[i].channel_id+"'/></td>";
		     channelList=channelList+"    <td>"+parent.areaChannels[areaid].channels[i].channel_name+"</td>";
		     channelList=channelList+"    <td>"+parent.areaChannels[areaid].channels[i].channel_code+"</td>";
		     channelList=channelList+"    <td>"+parent.areaChannels[areaid].channels[i].service_id+"</td>";
		     channelList=channelList+"   <td>";
		   	 channelList=channelList+"	<a href='#' onclick='deleteChannel("+parent.areaChannels[areaid].channels[i].channel_id+");'>删除频道</a>";
			 channelList=channelList+"	&nbsp;&nbsp;";
			
			 channelList=channelList+"	 </td>";
		     channelList=channelList+"  </tr>";
	    
	     }
		 channelList=channelList+"<td colspan='5'><input type='button' value='选择' class='btn' onclick='selectChannel();'/>";
      channelList=channelList+"    &nbsp;&nbsp;";
      channelList=channelList+"   <input type='button' class='btn' value='返 回' onclick='cancle();' />";
	 channelList=channelList+"  </tr>";
	     channelList=channelList+"</table>";
	    // alert(areaList);
	   	 document.getElementById("channelList").innerHTML=channelList;
	   	 }
	}
	/*
	 * 删除区域
	 * */
	function deleteChannel(channelid)
	{
		
		for (var i=0;i<parent.areaChannels[areaid].channels.length;i++)
	    {	
		    if (channelid==parent.areaChannels[areaid].channels[i].channel_id)
		    {
		    	parent.areaChannels[areaid].channels.splice(i,1);
		    	break;
		    }
	     }
		refreshChannelList();
		  parent.refreshAreaList();
	}
function goPage(pageNo) {
       document.getElementById("pageno").value=pageNo;
       document.forms[0].submit();
        
       /// $("queryForm").submit();
    }
    function submitForm(){
        validate();
        window.returnValue = "";
    }

    function query(){
        doument.form[0].submit();
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
	function selectChannel(){
    	//parent.document.request.getAttribute("areas","10,12");
    	if (getCheckCount("channelids")==0)
    	{
    		alert("请选择区域");
    		return ;
    	}
    	var selectData=getCheckValue("channelids");
    	var strchannels = selectData.split(",");
    	var areaId= document.getElementById("ploy.areaId").value;
    	for (var i=0;i<strchannels.length;i++)
    	{
    		var channelPropertys= strchannels[i].split("_");
    		
    		var obj = { channel_id: channelPropertys[0],channel_name: channelPropertys[0],channel_code: channelPropertys[2],service_id: channelPropertys[3]}; 
    		if (parent.areaChannels!=null)
    		{
    			
    			//遍历现有区域
    			for (var j=0;j<parent.areaChannels.length;j++)
	    		{
	    			if (parent.areaChannels[j].areaId==areaId)
	    			{
	    				//判断频道是否为空
	    				if (parent.areaChannels[j].channels!=null)
	    				{
	    					var flag=false;
	    					//遍历现有频道
	    					for (var k=0;k<parent.areaChannels[j].channels.length;k++)
				    		{
				    			if (parent.areaChannels[j].channels[k].channel_id==channelPropertys[0])
				    			{
				    				flag=true;
				    				alert(channelPropertys[1]+"频道已选择");
				    				return ;
				    			}	  
				    		}
	    					//end遍历现有频道
	    					if (flag==false)
				    		{
				    		  parent.areaChannels[j].channels.push(obj);
				    		}
	    				}	
	    				else
	    				{
	    					var newobj = [{ channel_id: channelPropertys[0],channel_name: channelPropertys[0],channel_code: channelPropertys[2],service_id: channelPropertys[3]}]; 
	    					parent.areaChannels[j]['channels']=newobj;
	    					
	    				}
	    				
	    				
	    			}	  
	    		}
	    		//end遍历现有区域
    		}
    		
        	
    	}
    	// var channels = {channel_id:"22",channel_name:"m",channel_code:"26"};
    	// var areas = {areaCode:"22",areaName:"m",channels:""};
    	// parent.areaChannels.push(areas);
    	// alert(parent.areaChannels[0].areaCode);
    	// childAreas = parent.areaChannels;
		// childAreas[0].channels.push(channels);
		// alert("11");
		   parent.refreshAreaList();
		 //   alert("22");
		// alert(areaChannels[0].channels[0].channel_id);
    	//alert(parent.document.request.getAttribute("areas"));
    //	parent.document.getElementById("ploy.contractname").value=pradio.value;
    //	parent.document.getElementById("ploy.contractid").value="1";
        parent.easyDialog.close();
    }
    function cancle(){
    	
        parent.easyDialog.close();
    }

</script>
</head>

<body class="mainBody" >


<form action="<%=path %>/page/ploy/getChannelListByArea.do" method="post" id="queryForm">
 <input type="hidden" id="pageno" name="pageChannel.pageNo" value="${pageChannel.pageNo}"/>
            <input type="hidden" id="pagesize" name="pageChannel.pageSize" value="${pageChannel.pageSize}"/>
  <input type="hidden" id="ploy.areaId" name="ploy.areaId" value="${ploy.areaId}"/>

<div class="search">

<div class="searchContent" >
<table cellspacing="1" class="searchList">
  <tr class="title">
    <td>查询条件</td>
  </tr>
  <tr>
    <td class="searchCriteria">
      <span>频道名称：</span><input type="text" name="channel.channelName" id="channel.channelName" value="${channel.channelName}"/>
      <span>频道编码：</span><input type="text" name="channel.channelCode" id="channel.channelName" value="${channel.channelName}"/>
      
	  <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
  </tr>
</table>
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
<div id="channelList">
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" width="5%" class="dot"><input type="checkbox" id="areaAll"  name="areaAll" onclick="selectAll(this,'channelids');"/></td>
        <td width="30%" align="center">频道名称</td>
        <td width="30%" align="center">频道编码</td>
        <td width="30%" align="center">serviceid</td>
    </tr>
   				 <c:if test="${pageChannel.dataList != null && fn:length(pageChannel.dataList) > 0}">
                    <c:forEach items="${pageChannel.dataList}" var="pchannel" varStatus="pc">
                        <tr <c:if test="${pc.index%2==1}">class="sec"</c:if>>
                            <td>
                               <input type="checkbox" id="channelids" name="channelids" value="${pchannel.channelId}_${pchannel.channelName}_${pchannel.channelCode}_${pchannel.serviceId}"/>
                             </td>
                            <td>
                                ${pchannel.channelName}
                            </td>
                             <td>
                                ${pchannel.channelCode}
                                
                            </td>
					        <td>
                                ${pchannel.serviceId}
                            </td>		
							
                        </tr>
                    </c:forEach>
                </c:if>


  <tr>
   	 <td colspan="4"><input type="button" value="选择" class="btn" onclick="selectChannel();"/>
          &nbsp;&nbsp;
          <input type="button" class="btn" value="返 回" onclick="cancle();" />
		
        <div class="page"
					style="background: url(<%=path %>/images/bottom.jpg) repeat-x; text-align: right;">
					<a href="#">当前第${pageChannel.pageNo }/${pageChannel.totalPage }页</a>&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${pageChannel.pageNo==1&&pageChannel.totalPage!=1}">
							<a href="#" onclick="javascript:goPage('${pageChannel.pageNo+1}')">下一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageChannel.totalPage}')">末页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${pageChannel.pageNo==pageChannel.totalPage&&pageChannel.totalPage!=1 }">
							<a href="#" onclick="javascript:goPage('${1}')">首页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageChannel.pageNo-1}')">上一页</a>&nbsp;&nbsp;
						</c:when>
						<c:when test="${pageChannel.pageNo>1&&pageChannel.pageNo<pageChanneltotalPage }">
							<a href="#" onclick="javascript:goPage('${1}')">首页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageChannel.pageNo-1}')">上一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageChannel.pageNo+1}')">下一页</a>&nbsp;&nbsp;
							<a href="#" onclick="javascript:goPage('${pageChannel.totalPage}')">末页</a>&nbsp;&nbsp;
						</c:when>
					</c:choose>
				
        </div>
    </td>
  </tr>
</table>
</div>
</div>
</div>
</form>
</body>
</html>