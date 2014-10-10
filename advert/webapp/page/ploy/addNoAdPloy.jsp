<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
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
var checkflag  =true;
var resourcePath=$('#projetPath').val();
var areaChannels =  new Array();
window.onload = function() {
	  areaChannels=eval(${areasJson});
	$("#system-dialog").hide();
	 refreshAreaList();
	}

	/*
	 * 刷新区域列表
	 * */
    function refreshAreaList()
	{
	   
		if( areaChannels!=null &&  areaChannels.length > 0)
		{
		var areaList=         "<table cellspacing='1' class='searchList'>";
		areaList=areaList+"<tr class=title>";
	    areaList=areaList+  "<td colspan='5'>已选区域</td>";
	    areaList=areaList+"</tr>";
	    areaList=areaList+"<tr class='title'>";
	    areaList=areaList+"    <td >序号</td>";
	    areaList=areaList+"    <td>区域名称</td>";
	    areaList=areaList+"    <td>区域编码</td>";
		areaList=areaList+"	<td>操作</td>";
	    areaList=areaList+"  </tr>";
	    
	  // alert(areaChannels.length);
	    for (var i=0;i<areaChannels.length;i++)
	    {	
	   // alert(areaChannels[i].areaCode);
		     areaList=areaList+"  <tr>";
		     areaList=areaList+"    <td>"+i+"</td>";
		     areaList=areaList+"    <td>"+areaChannels[i].areaName+"</td>";
		     areaList=areaList+"    <td>"+areaChannels[i].areaCode+"</td>";
		     areaList=areaList+"   <td>";
		     areaList=areaList+" <input id='areaids' name='areaids' type='hidden' value='"+areaChannels[i].areaId +"'/>";
		     areaList=areaList+" <input id='channel"+i+"' name='channel"+i+"' type='hidden' value='";
		     if(areaChannels[i].channels != null)
		     {
		     	for (var j=0;j<areaChannels[i].channels.length;j++)
		    	 {
		    	  areaList=areaList+areaChannels[i].channels[j].channel_id;
		    	//  alert(areaChannels[i].channels[j].channel_id);
		    	  if (j<areaChannels[i].channels.length-1)
		    	  {
		    		  areaList=areaList+",";
		    	 }
		    	  
		     	}
		     }
		     
		     areaList=areaList+"'/>";
			 areaList=areaList+"	<a href='#' onclick='deleteArea("+areaChannels[i].areaId+");'>删除区域</a>";
			 areaList=areaList+"	&nbsp;&nbsp;";
			 if (document.getElementById('padposition.isChannel').value==1)
			 {
				 areaList=areaList+"	<span id='selectc' ><a href='#' onclick='addselectChannel("+areaChannels[i].areaId+");'>绑定频道</a></span>";
				 areaList=areaList+"	&nbsp;&nbsp;";
				 areaList=areaList+"	<span id='selectm' ><a href='#' onclick='modifyChannel("+i+");'>编辑频道</a></span>";
			}
			 areaList=areaList+"	 </td>";
		     areaList=areaList+"  </tr>";
	    
	     }
	     areaList=areaList+"</table>";
	    // alert(areaList);
	   	 document.getElementById("areaList").innerHTML=areaList;
	   	 }
	}
	/*
	 * 删除区域
	 * */
	function deleteArea(areaCode)
	{
		
		for (var i=0;i<areaChannels.length;i++)
	    {	
		    if (areaCode==areaChannels[i].areaCode)
		    {
		    	areaChannels.splice(i,1);
		    	break;
		    }
	     }
		refreshAreaList();
	}
	/**
	 * 绑定广告位
	 */
	
    function submitForm(){
       if (validate()==false)
    	   {
    	   return ;
    	   }
      //  checkPloy();
       // return ;
        //window.returnValue = "";
       // alert(document.getElementById("saveForm"));
      //  alert(document.getElementById("saveForm").action);
        document.getElementById("saveForm").submit();
        //window.location.href="<%=path %>/page/ploy/save.do?method=saveUpdatePloy";
    }

    function validate(){
    	if (isEmpty(document.getElementById("noAdPloy.ployname").value))
    	{
    		alert("禁播名称不能为空");
    		return false;
    	}
    	if (isEmpty(document.getElementById("noAdPloy.tvn").value))
    	{
    		alert("禁播TVN不能为空");
    		return false;
    	}
    	if (isEmpty(document.getElementById("noAdPloy.startDate").value))
    	{
    		alert("禁播开始时间不能为空");
    		return false;
    	}
    	if (isEmpty(document.getElementById("noAdPloy.endDate").value))
    	{
    		alert("禁播结束时间不能为空");
    		return false;
    	}
    	if (dateCompare(document.getElementById("noAdPloy.startDate").value,document.getElementById("noAdPloy.endDate").value)<0)
    	{
    		alert("开始时间不能大于结束时间");
    		return false;
    	}
    	checkPloy();
        if (checkflag==false)
        {
        	alert("禁播名称已存在");
    		return false;
       	}
		
		
		
		
    	/*
    	//输入框的边框变为红色
       
        $("name").style.borderColor="red";
        $("contentType").style.borderColor="red";
        $("select0").style.borderColor="red";
        $("time1").style.borderColor="red";
        $("time2").style.borderColor="red";
        $("remark").style.borderColor="red";
        //输入框后加错误提示信息
        $("name_error").className="error";
        $("name_error").innerHTML = "内容包名不能为空";
        $("contentType_error").className="error";
        $("contentType_error").innerHTML = "请选择内容类型";
        $("select0_error").className="error";
        $("select0_error").innerHTML = "请选择是否为电视剧";
        $("time1_error").className="error";
        $("time1_error").innerHTML = "生效日期不能为空";
        $("time2_error").className="error";
        $("time2_error").innerHTML = "失效日期不能为空";
        $("remark_error").className="error";
        $("remark_error").innerHTML = "备注中含有特殊字符";
        */
        return;
    }
    function checkPloy() {
 			var ployName = document.getElementById("noAdPloy.ployname").value;
 			var ployId = document.getElementById("noAdPloy.id").value;
 			var ret = true;
 		   	$.ajax({
                type:"post",
                async : false,
                url:"<%=request.getContextPath()%>/page/ploy/checkNoAdPloy.do?",//从哪获取Json
                data:{"noAdPloy.ployname":ployName,"noAdPloy.id":ployId},//Ajax传递的参数
                success:function(mess)
                {
                	
                    if(mess=="1")// 如果获取的数据不为空
                    {
						checkflag=false;
						//return false;
                    }
                    else
                    	{
                    	checkflag=true;
                    	}
                   
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
			return true;
	 } 
 	function selectContract() {
 	
 			var contractId = document.getElementById("ploy.contractId").value;
 			alert(contractId);
          	$.ajax({
                type:"post",
                url:"/page/ploy/getContract.do?",//从哪获取Json
                data:{"contractId":contractId},//Ajax传递的参数
                success:function(mess)
                {
                	
                    if(mess!="]")// 如果获取的数据不为空
                    {
						 var type=eval(mess);
                         
                         for(var i=0;i<type.length;i++)
                         {
                            //操作type 获取东西
                           
                         }
                          easyDialog.open({
							container : {
								header : "选择合同",
								content : $("#divImage")
							},			
							overlay : false
						});
                         $("#divLinkMan").html(str);//把str嵌入div divLinkMan 中
                     }
                     else
                     {
                        var notfind="<h2>未找到数据<a href='../Main.htm' target='_parent'>返回</a></h2>"
                        $("#divLinkMan").html(notfind);
                     }
                     
                }
            });
			
	 } 
 	function selectAdPosition() {
 		   	var structInfo = '';
			var contractname = document.getElementById("ploy.contractname").value;
			
			if(contractname==null || ''==contractname )
			{
				alert('请单击【合同】文本框进行选择000');
				return ;
			}
          	//$("#content").html('请单击【绑定广告商】文本框进行广告商绑定');
			//$( "#system-dialog" ).dialog({
		   //   	modal: true
		    //});
          	structInfo+='					<div style="margin:0px;padding:0px;width:600px">';
			structInfo+='							<iframe id="selectAdPositionFrame" name="selectAdPositionFrame" src="<%=request.getContextPath()%>'+'/page/ploy/selectAdPosition.jsp'+'" frameBorder="0" width="800px" height="400px"  scrolling="yes"></iframe>';
			structInfo+='					</div>';
			easyDialog.open({
			container : {
				header : '选择广告位',
				content : structInfo
			},
			overlay : false
		});
	 } $(document).ready(function(){
	 
 		$("#selectAreas").click(function(){
	  	selectArea();
 		});
});
	  
	 function selectArea() {
	 
	 	 var contractId= document.getElementById("ploy.contractId").value;
		 var postionId= document.getElementById("ploy.positionId").value;
		 var ruleId= document.getElementById("ploy.ruleId").value;
		 var startTime= document.getElementById("ploy.startTime").value;
		 var endTime= document.getElementById("ploy.endTime").value;
		 var structInfo ="";
			structInfo+="			<div style='margin:0px;padding:0px;width:600px'>";
			structInfo+="						<iframe id='selectAreaFrame' name='selectAreaFrame'  frameBorder='0' width='600px' height='400px'  scrolling='yes'  align='top' src=<%=request.getContextPath()%>/page/ploy/getArea.do? ";
			structInfo+="contractId="+contractId+"&postionId="+postionId+"&ruleId="+ruleId+"&startTime="+startTime+"&endTime="+endTime;
			structInfo+="'></iframe>";
			structInfo+="</div>";
			easyDialog.open({
			container : {
				header : "添加区域",
				content : structInfo
			},
			overlay : true
			
		});
	 }
	 function alreadyrule() {
            var url = "positionmarketrulelist.htm";
			// window.location=url;
            window.showModalDialog(url, "", "dialogHeight=550px;dialogWidth=650px;center=1;resizable=0;status=0;");
        }
		function selectChannel()
		{
		   var url = "selectChannelList.html";
			 window.location=url;
            //window.showModalDialog(url, "", "dialogHeight=550px;dialogWidth=650px;center=1;resizable=0;status=0;");
		}
		function onchangeAD()
		{
		if (document.getElementById("adType").value == "1")
		{
		var array = document.getElementsByName("selectc");
		if(array.length){
	    for(var i = 0 ;i<array.length;i++){
		 		array[i].style.display="none";
			}
		}
		document.getElementById("starttime").value="00:00:00";
		document.getElementById("endtime").value="23:59:59";
		}
		else
		{
		var array = document.getElementsByName("selectc");
		if(array.length){
	    for(var i = 0 ;i<array.length;i++){
		 		array[i].style.display="";
			}
		}
		
		}
		}
		
		
function addselectContract() {
          	
	//var structInfo = '';
	//		structInfo+='					<div style="margin:0px;padding:0px;">';
	//		structInfo+='							<iframe id="selectContraceFrame" name="selectContraceFrame" src="<%=request.getContextPath()%>'+'/page/ploy/addqueryContractList.do'+'" frameBorder="0" width="800px" height="570px"  scrolling="yes"></iframe>';
	//		structInfo+='					</div>';
	//		easyDialog.open({
	//		container : {
	//			header : '选择合同',
	//			content : structInfo
	//		},			
	//		overlay : false
	//	});
	
		var	height = 500;
		var width=750;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/ploy/addqueryContractList.do';
		 var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 } 
 	function addselectAdPosition() {
          	var structInfo = '';
			var contractid= document.getElementById("ploy.contractId").value;
			var contractname = document.getElementById("ploy.contractName").value;
			
			if(contractname==null || ''==contractname )
			{
				message = '请单击【合同】文本框进行选择';
				$("#content").html(message);
				$( "#system-dialog" ).dialog({
			      	modal: true
			    });
			//alert('请单击【合同】文本框进行选择');
				return ;
			}
			
          	//$("#content").html('请单击【绑定广告商】文本框进行广告商绑定');
			//$( "#system-dialog" ).dialog({
		   //   	modal: true
		    //});
         // 	structInfo+='					<div style="margin:0px;padding:0px;width:600px">';
		//	structInfo+='							<iframe id="selectAdPositionFrame" name="selectAdPositionFrame" src="<%=request.getContextPath()%>'+'/page/ploy/addqueryAdPostionList.do?contract.id='+contractid+'" frameBorder="0" width="800px" height="570px"  scrolling="yes"></iframe>';
		//	structInfo+='					</div>';
		//	easyDialog.open({
		//	container : {
		//		header : '选择广告位',
		//		content : structInfo
		//	},
		//	overlay : false
		//    });
			
			var	height = 500;
		var width=750;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/ploy/addqueryAdPostionList.do?contract.id='+contractid;
		 var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 } 
 	function addselectRule() {
          	var structInfo = '';
		//	var adname = document.getElementById("ploy.adname").value;
			var contractid= document.getElementById("ploy.contractId").value;
			
			var adId = document.getElementById("ploy.positionId").value;
			if(adId==null || ''==adId )
			{
				alert('请单击【广告位】文本框进行选择');
				return ;
			}
			
          	structInfo+='					<div style="margin:0px;padding:0px;">';
			structInfo+='							<iframe id="selectRuleFrame" name="selectRuleFrame" src="<%=request.getContextPath()%>'+'/page/ploy/addqueryRuleList.do?contract.id='+contractid+'&adPosition.id='+adId+'" frameBorder="0" width="800px" height="570px"  scrolling="yes" align="top"></iframe>';
			structInfo+='					</div>';
			easyDialog.open({
			container : {
				header : '选择营销规则',
				content : structInfo
			},
			overlay : true
		});
	 }
		function addselectChannel(areaid) {
          	var structInfo = '';
          	var contractId= document.getElementById("ploy.contractId").value;
          	var ruleId= document.getElementById("ploy.ruleId").value;
          	var starttime=document.getElementById("ploy.startTime").value;
          	var endtime =document.getElementById("ploy.endTime").value;
			structInfo+='					<div style="margin:0px;padding:0px;">';
			structInfo+='							<iframe id="selectContraceFrame" name="selectContraceFrame" src="<%=request.getContextPath()%>'+'/page/ploy/getChannelListByArea.do?ploy.areaId='+areaid+'&ploy.contractId='+contractId+'&ploy.ruleId='+ruleId+'&ploy.startTime='+starttime+'&ploy.endTime='+endtime+'" frameBorder="0" width="800px" height="570px"  scrolling="yes"></iframe>';
			structInfo+='					</div>';
			easyDialog.open({
			container : {
				header : '绑定频道',
				content : structInfo
			},			
			overlay : false
		});
	 } 
		function modifyChannel(areaid) {
          	var structInfo = '';
          	structInfo+='					<div style="margin:0px;padding:0px;">';
			structInfo+='							<iframe id="selectContraceFrame" name="selectContraceFrame" src="<%=request.getContextPath()%>'+'/page/ploy/modifychannel.jsp?areaid='+areaid+'" frameBorder="0" width="800px" height="570px"  scrolling="yes"></iframe>';
			structInfo+='					</div>';
			easyDialog.open({
			container : {
				header : '绑定频道',
				content : structInfo
			},			
			overlay : false
		});
	 } 
		
	 function selectOptionVal(){
	 }
</script>

<style>
	.easyDialog_wrapper{ width:800px;height:580px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
</head>

<body class="mainBody">
<form action="<%=path %>/page/ploy/saveUpdateNoAdPloy.do" method="post" id="saveForm">

<input id="projetPath" type="hidden" value="<%=path%>"/>
<div class="detail">
    <table cellspacing="1" class="content" align="left">
        <tr class="title">
            <td colspan="4">策略编辑 </td>
        </tr>
		 <tr >
            <td width="10%" align="right"><span class="required">*</span>策略名称：</td>
            <td width="25%">
                <input id="noAdPloy.id" name="noAdPloy.id" type="hidden" value="${noAdPloy.id}"/>
                <input onkeypress="return validateSpecialCharacter();" maxlength="20" id="noAdPloy.ployname" name="noAdPloy.ployname" type="text" style="width: 70%" value="${noAdPloy.ployname}"/>
                <span id="name_error" ></span>
            </td>
			<td width="10%" align="right" ><span class="required">*</span>广告位：</td>
            <td width="25%">
               <select id="noAdPloy.positionid" name="noAdPloy.positionid">
			   <c:forEach items="${pageAdPosition.dataList}" var="position" >
		                          <option value='${position.id}' <c:if test="${noAdPloy.positionid==position.id}">
                            	selected
                            	</c:if>> 
                            	${position.positionName} 
                           </option>
		                            
		                            </c:forEach>
			  </select>
		               
            </td>
        </tr>    
        	 <tr >
            <td width="10%" align="right"><span class="required">*</span>TVN号：</td>
            <td width="25%">
             <input onkeypress="return validateSpecialCharacter();" maxlength="20" id="noAdPloy.tvn" name="noAdPloy.tvn" type="text" style="width: 70%" value="${noAdPloy.tvn}"/>
                <span id="name_error" ></span>
            </td>
            
			<td width="10%" align="right" ><!-- <span class="required">*</span>营销规则：--></td>
            <td width="25%">
                 <!-- <input id="ploy.ruleName" name="ploy.ruleName" type="text" class="e_input_add" readonly="readonly"
					onfocus="this.className='e_inputFocus'" onclick="addselectRule();"
					onblur="this.className='e_input_add'"  style="width: 70%" value="${ploy.ruleName}"/>
                <input id="ploy.ruleId" name="ploy.ruleId" type="hidden" value="${ploy.ruleId}"/>
                <span id="contentType_error" ></span>
                -->
            </td>
             
        </tr>    
		 <tr>
            <td width="10%" align="right"><span class="required">*</span>开始时间：</td>
            <td width="25%">
                <input id="noAdPloy.startDate" name="noAdPloy.startDate" type="text" style="width: 70%" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
                                 value="<fmt:formatDate value='${noAdPloy.startDate}' pattern='yyyy-MM-dd' />"/>
                <span id="name_error" ></span>
            </td>
      
            <td width="10%" align="right"><span class="required">*</span>结束时间：</td>
            <td width="25%">
                <input id="noAdPloy.endDate" name="noAdPloy.endDate"  type="text" style="width: 70%"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${noAdPloy.endDate}' pattern='yyyy-MM-dd' />"/>
                <span id="name_error" ></span>
            </td>
        </tr>
         <input id='areaids' name='areaids' type='hidden' value='0'/>
       <!--  <tr >
            <td align="right"><span class="required">*</span>区域：</td>
            <td colspan="3">
             <input id="areas" name="areas" type="hidden" value="${areas}"/>
                <input id="selectAreas"type="button" class="btn" value="选择区域" />
            </td>
        </tr>       
		 -->
		
    </table>
   
    
    <div id="areaList" >
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="list">
        <c:if test="${lstPloyAreaChannel != null && fn:length(lstPloyAreaChannel) > 0}">
           <c:forEach items="${lstPloyAreaChannel}" var="AreaChannel">
               <tr>
                   <td width="30">
                       <input type="radio" value="${AreaChannel.areaId}" name="areaIds" />
                   </td>
                   <td width="10%">
                       <div id="name_${AreaChannel.areaId}">${AreaChannel.areaName}</div>
                   </td>
                   <td width="10%">
                       <div id="name_${AreaChannel.strChannelIds}">${AreaChannel.strChannels}</div>
                   </td>
               </tr>
           </c:forEach>
       </c:if>
   </table>
</div>
    <div align="center" class="action">
        <input type="button" class="btn" value="保 存" onclick="submitForm();"/>&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" class="btn" value="取 消" onclick="history.back(-1);"/>
    </div>
</div>


<div id="contractDiv" class="showDiv" style="display:none">
    <div class="searchContent">
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
              <td class="dot"></td>
                <td>合同名称</td>
                <td>合同号</td>
                <td>合同代码</td>
            </tr>
            <c:set var="cIndex" value="0" />
			<c:forEach items="${contracts}" var="contract">
				<tr>
					<td align="center"><input type="radio"
						name="contractId" value="${contract.id}"
						<c:if test="${cIndex==0 }"> checked="checked"</c:if> /> <input
						type="hidden" name="contractCode" value="${contract.contractCode }" />
						<input
						type="hidden" name="contractName" value="${contract.contractName }" /></td>
					<td>${contract.contractName }</td>
					<td>${contract.contractCode }</td>
				</tr>
				<c:set var="cIndex" value="${cIndex+1 }" />
			</c:forEach>
        </table>
    </div>
</div>

<div id="adSiteDiv" class="showDiv" style="display:none">
    <div class="searchContent">
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
              <td class="dot"></td>
                <td>广告位名称</td>
                <td>广告位</td>
            </tr>
            <c:set var="cIndex" value="0" />
			<c:forEach items="${contracts}" var="contract">
				<tr>
					<td align="center"><input type="radio"
						name="contractId" value="${contract.id}"
						<c:if test="${cIndex==0 }"> checked="checked"</c:if> /> <input
						type="hidden" name="contractCode" value="${contract.contractCode }" />
						<input
						type="hidden" name="contractName" value="${contract.contractName }" /></td>
					<td>${contract.contractName }</td>
				</tr>
				<c:set var="cIndex" value="${cIndex+1 }" />
			</c:forEach>
        </table>
    </div>
</div>

<div id="marketRuleDiv" class="showDiv" style="display:none">
    <div class="searchContent">
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
              <td class="dot"></td>
                <td>规则名称</td>
                <td>合同号</td>
                <td>合同代码</td>
            </tr>
            <c:set var="cIndex" value="0" />
			<c:forEach items="${contracts}" var="contract">
				<tr>
					<td align="center"><input type="radio"
						name="contractId" value="${contract.id}"
						<c:if test="${cIndex==0 }"> checked="checked"</c:if> /> <input
						type="hidden" name="contractCode" value="${contract.contractCode }" />
						<input
						type="hidden" name="contractName" value="${contract.contractName }" /></td>
					<td>${contract.contractName }</td>
					<td>${contract.contractCode }</td>
				</tr>
				<c:set var="cIndex" value="${cIndex+1 }" />
			</c:forEach>
        </table>
    </div>
</div>
<div id="system-dialog" title="友情提示">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
    <span id="content"></span>
  </p>
</div>
</form>
</body>
</html>