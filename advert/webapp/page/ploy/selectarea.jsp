<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link id="maincss"  rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/new/main.css" media="all"/>
<title></title>
 <script type='text/javascript' src='<%=request.getContextPath()%>/js/jquery.min.js'></script>
    <script type='text/javascript' src='<%=request.getContextPath()%>/js/avit.js'></script>

<script type="text/javascript">
var childAreas=[];
    var $ = function(id){
        return document.getElementById(id);
    };
	function goPage(pageNo) {
      document.getElementById("pageno").value=pageNo;
       document.forms[0].submit();
    }

    function submitForm(){
        validate();
        window.returnValue = "";
    }

    function query(){
        window.location.reload();
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
	function selectArea(){
    	//parent.document.request.getAttribute("areas","10,12");
    	
    	if (getCheckCount("area.locationId")==0)
    	{
    		alert("请选择区域");
    		return ;
    	}

    	var selectData=getCheckValue("area.locationId");

    	var strareas = selectData.split(",");
    	
    	for (var i=0;i<strareas.length;i++)
    	{

    		var obj = { areaId: strareas[i], areaName: document.getElementById("name_"+strareas[i]).value, areaCode: document.getElementById("areaCode_"+strareas[i]).value};  

    		//var areaPropertys= strareas[i].split("_");
    		//var obj = { areaCode: areaPropertys[0],areaName: areaPropertys[1], parentCode:areaPropertys[2]};    
    		if (parent.areaChannels!=null && parent.areaChannels.length>0)
    		{
	    		var flag=false;
	    		for (var j=0;j<parent.areaChannels.length;j++)
	    		{
		    		
		    		if (parent.areaChannels[j].areaId==strareas[i])
	    			{
	    				  flag=true;
	    				  alert(document.getElementById("name_"+strareas[i]).value+"区域已选择");
	    				  return ;
	    			}	  
	    		}
	    		if (flag==false)
	    		{
	    		 	parent.areaChannels.push(obj);
	    		}
        	}
    		else
    		{
    			 obj = [{ areaId: strareas[i], areaName: document.getElementById("name_"+strareas[i]).value, areaCode: document.getElementById("areaCode_"+strareas[i]).value}];  
	    		 parent.areaChannels=obj;
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
<div class="search" >
<div class="searchContent" >
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="selectAllBox" onclick="selectAll(this, 'area.locationId');"/></td>
        <td width="30%" align="center">区域名称</td>
        <td width="30%" align="center">区域编码</td>
        <td width="30%" align="center">父区域编码</td>
    </tr>
        <c:if test="${lstReleaseArea != null && fn:length(lstReleaseArea) > 0}">
           <c:forEach items="${lstReleaseArea}"  var="area" varStatus="pc">
               <tr <c:if test="${pc.index%2==1}">class="sec"</c:if>>
                   <td>
                       <input type="checkbox" value="${area.id}" name="area.locationId" id="area.locationId"/>
                   </td>
                   <td>
                       <input id="name_${area.id}" name="area.id" type="hidden" value="${area.areaName}"/>
                       ${area.areaName}
                     </td>
                    <td>
                        <input id="areaCode_${area.id}" name="areaCode" type="hidden" value="${area.areaCode}"/>
                        ${area.areaCode}
                    </td>
                    <td>
                	    <input id="parentCode_${area.id}" name="parentCode" type="hidden" value="${area.parentCode}"/>
                	    ${area.parentCode}
                   </td>
               </tr>
           </c:forEach>
       </c:if>
        <tr>
   
		 <td colspan="4"><input type="button" value="选择" class="btn" onclick="selectArea();"/>
          &nbsp;&nbsp;
          <input type="button" class="btn" value="返 回" onclick="cancle();" />
	</td>
	</tr>
   </table>
   
   </div>
</div>

</body>
</html>