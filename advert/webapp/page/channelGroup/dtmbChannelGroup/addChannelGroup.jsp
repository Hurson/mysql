<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
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

<link id="maincss"  rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" media="all"/>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
	
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/tools.js"></script>
<title>广告系统</title>
<script type="text/javascript">
    var resourcePath=$('#projetPath').val();
	

	
    function submitForm(){
            document.getElementById("channelGroup.name").disabled="";
            document.getElementById("channelGroup.code").disabled="";
	        document.getElementById("channelGroup.channelDesc").disabled="";
	        document.getElementById("channelGroup.type").disabled="";
			if(checkChannelGroup()){
    		return ;
		    }		
		    document.getElementById("saveForm").submit();		
    }
    
    function checkChannelGroup(){		     
        
    	if(isEmpty($$("channelGroup.name").value)){
			alert("请输入频道组名称！");
			$$("channelGroup.name").focus();
    		return true;
		}
    	if(isEmpty($$("channelGroup.code").value)){
			alert("请输入频道组编号！");
			$$("channelGroup.code").focus();
    		return true;
		}

		if($$("channelGroup.code").value.length>20){
			alert("频道组编号必须小于20个字节！");
			$$("channelGroup.code").focus();
    		return true;
		}
		
		if($$("channelGroup.name").value.length>20){
			alert("频道组名称必须小于20个字节！");
			$$("channelGroup.name").focus();
    		return true;
		}
		
		if(isEmpty($$("channelGroup.channelDesc").value)){

		}else{
		   if($$("channelGroup.channelDesc").value.length>254){
			alert("描述必须小于254个字节！");
			$$("channelGroup.channelDesc").focus();
    		return true;
		   }
		}
		

		return false;
    }
    
    
    function gotoList(){

        window.location.href="<%=path %>/dchannelGroup/queryChanelGroupList.do";
    }



	 
	   

	   
</script>

<style>
	.easyDialog_wrapper{ width:1000px;height:400px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
</head>

<body class="mainBody">
<form action="<%=path %>/dchannelGroup/saveChannelGroup.do" method="post" id="saveForm">

<input id="projetPath" type="hidden" value="<%=path%>"/>
<div class="detail">
    <table cellspacing="1" class="content" align="left">
        <tr class="title">
            <td colspan="8">频道组基本信息 </td>
        </tr>
        <tr >
            <td width="15%" align="right"><span class="required">*</span>频道组名称：</td>
            <td width="35%">
                <input id="channelGroup.name" name="channelGroup.name" type="text"  value="${channelGroup.name}"/>
                <input id="channelGroup.id" name="channelGroup.id" type="hidden" value="${channelGroup.id}"/>
                <input id="channelGroup.operatorId" name="channelGroup.operatorId" type="hidden" value="${channelGroup.operatorId}"/>
                <span id="channelGroupName_error" ></span>
            </td>
			<td width="15%" align="right" ><span class="required">*</span>频道组编号：</td>
            <td width="35%">
                <input id="channelGroup.code" name="channelGroup.code" type="text"  value="${channelGroup.code}"/>
                <span id="channelGroupCode_error" ></span>
            </td>
            
        </tr>		 
        <tr>
        		<td width="10%" align="right" ><span class="required">*</span>频道组类型：</td>
        		<td width="25%">
            	<select name="channelGroup.type" id="channelGroup.type" style="width:120px" <c:if test="${!empty channelGroup.type}">disabled</c:if>>
            			<option value="视频直播类业务" <c:if test="${channelGroup.type=='视频直播类业务'}">selected="selected" </c:if>>视频直播类业务</option>
            			<option value="音频直播类业务" <c:if test="${channelGroup.type=='音频直播类业务'}">selected="selected" </c:if>>音频直播类业务</option>
            			<option value="NVOD业务" <c:if test="${channelGroup.type=='NVOD业务'}">selected="selected" </c:if>>NVOD业务</option>
				</select>
				</td>
                <td width="12%" align="right">描 述：</td>
                <td width="88%" colspan="7">
                    <textarea style="" id="channelGroup.channelDesc" name="channelGroup.channelDesc" rows="5">${channelGroup.channelDesc}</textarea>
                    <span id="channelGroupDesc_error"></span>
                </td>
        </tr>      
		
		
    </table>

    

    <div align="center" class="action">
         <input type="button" class="btn" value="保 存" onclick="submitForm();"/>&nbsp;&nbsp;&nbsp;&nbsp;   
        <input type="button" class="btn" value="返 回" onclick="gotoList();;"/>
    </div>
</div>



</form>
</body>
</html>