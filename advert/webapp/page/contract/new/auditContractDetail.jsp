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
	    areaList=areaList+  "<td colspan='8'>已绑定广告位</td>";
	    areaList=areaList+"</tr>";
	    areaList=areaList+"<tr class='title'>";
	    areaList=areaList+"    <td >序号</td>";
	    areaList=areaList+"    <td>广告位名称</td>";
	    areaList=areaList+"    <td>广告位类型</td>";
		areaList=areaList+"	<td>投放方式</td>";
		areaList=areaList+"	<td>高标清标识</td>";
		areaList=areaList+"	<td>投放开始日期</td>";
		areaList=areaList+"	<td>投放结束日期</td>";
	    areaList=areaList+"  </tr>";

	    for (var i=0;i<areaChannels.length;i++)
	    {	
		     areaList=areaList+"  <tr>";
		     areaList=areaList+"    <td>"+(i+1)+"</td>";
		     areaList=areaList+"    <td>"+areaChannels[i].positionName+"</td>";
		     if(areaChannels[i].packageType == 0)
		     {
		     	   areaList=areaList+" <td>双向实时广告</td>";
		     }else if(areaChannels[i].packageType == 1){
		           areaList=areaList+" <td>双向实时请求广告</td>";		     
		     }else if(areaChannels[i].packageType == 2){
		           areaList=areaList+" <td>单向实时广告</td>";
		     }else if(areaChannels[i].packageType == 3){
		           areaList=areaList+" <td>单向非实时广告</td>";
		     }else{
		           areaList=areaList+" <td>未知类型</td>";
		     }
		     
		     if(areaChannels[i].deliveryMode==0){
		           areaList=areaList+" <td>投放式</td>";
		     }else{
		           areaList=areaList+" <td>请求式</td>";
		     }
		     
		     if(areaChannels[i].videoType==0){
		           areaList=areaList+" <td>只支持标清</td>";	     
		     }else if(areaChannels[i].videoType==1){
		           areaList=areaList+" <td>只支持高清</td>";
		     }else{
		           areaList=areaList+" <td>高清标清都支持</td>";
		     }

		     areaList=areaList+"    <td><input id='positionStartDate_"+areaChannels[i].positionId+"'  name='positionStartDate_"+areaChannels[i].positionId+"' class='input_style2' type='hidden' style='width:80px;' onclick='WdatePicker(positionStartDate_"+areaChannels[i].positionId+")' value='"+areaChannels[i].positionStartDate.substring(0,10)+"'/>"+areaChannels[i].positionStartDate.substring(0,10)+"</td>";
		     areaList=areaList+"    <td><input id='positionEndDate_"+areaChannels[i].positionId+"' name='positionEndDate_"+areaChannels[i].positionId+"' class='input_style2' type='hidden' style='width:80px;' onclick='WdatePicker(positionEndDate_"+areaChannels[i].positionId+")' value='"+areaChannels[i].positionEndDate.substring(0,10)+"'/>"+areaChannels[i].positionEndDate.substring(0,10);		     
		     areaList=areaList+" <input id='positionIds' name='positionIds' type='hidden' value='"+areaChannels[i].positionId +"'/></td>";
		    
		     areaList=areaList+"  </tr>";
	    
	     }
	     areaList=areaList+"</table>";
	   	 document.getElementById("areaList").innerHTML=areaList;
	   	 }
	}
	/*
	 * 删除区域
	 * */
	function deletePositon(positionId)
	{
		
		for (var i=0;i<areaChannels.length;i++)
	    {	
		    if (positionId==areaChannels[i].positionId)
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
	
    function aduitSuccess(){

        //document.getElementById("saveForm").submit();
        var reason = document.getElementById("contract.examinationOpinions").value;
        var contractId = document.getElementById("contract.id").value;
        if(reason!=null||reason!=""){
           if(reason.length>255){
			alert("审核意见必须小于255个字节！");
			$$("contract.examinationOpinions").focus();
    		return true;
		}
        }
        window.location.href="<%=path %>/page/contract/auditConstract.do?auditFlag=1&reason="+reason+"&contractId="+contractId;
    }
    
    function aduitFlase(){
        var reason = document.getElementById("contract.examinationOpinions").value;
        var contractId = document.getElementById("contract.id").value;
        if(reason==null||reason==""){
            alert("审核被拒时,审核意见不能为空!");
        }else{
        
        if(reason.length>255){
			alert("审核意见必须小于255个字节！");
			$$("contract.examinationOpinions").focus();
    		return true;
		}
            window.location.href="<%=path %>/page/contract/auditConstract.do?auditFlag=0&reason="+reason+"&contractId="+contractId;
        }
        
    }
    
    function gotoList(){

        window.location.href="<%=path %>/page/contract/auditContractList.do";
    }

    function validate(){
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
        
        return;
    }


	 
	/* $(document).ready(function(){
	 
 		$("#selectAreas").click(function(){
	  	selectArea();
 		});
});*/
	  
	 function selectAdPositionPacks() {
	 	 //var contractId= document.getElementById("ploy.contractId").value;
		 var structInfo ="";
			structInfo+="			<div style='margin:0px;padding:0px;width:600px'>";
			structInfo+="						<iframe id='selectAdPositionPacksFrame' name='selectAdPositionPacksFrame'  frameBorder='0' width='1000px' height='400px'  scrolling='yes'  align='top' src=<%=request.getContextPath()%>/page/contract/selectAdPositionPackage.do? ";
			//structInfo+="contractId="+contractId+"&postionId="+postionId+"&ruleId="+ruleId+"&startTime="+startTime+"&endTime="+endTime;
			structInfo+="'></iframe>";
			structInfo+="</div>";
			easyDialog.open({
			container : {
				header : "添加广告位",
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
		
		
function selectCustomer() {
          	var structInfo = '';
			structInfo+='<div style="margin:0px;padding:0px;">';
		    structInfo+='<iframe id="bindingCustomerFrame" name="bindingCustomerFrame" src="<%=request.getContextPath()%>'+'/page/customer/adCustomerMgr_list.do?contractBindingFlag=1'+'" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		    structInfo+='</div>';
			
			easyDialog.open({
			container : {
				header : '选择广告商',
				content : structInfo
			},			
			overlay : false
		});
	 } 
	 
	 

		function addselectChannel(areaid) {
          	var structInfo = '';
          	var contractId= document.getElementById("ploy.contractId").value;
          	var ruleId= document.getElementById("ploy.ruleId").value;
          	var starttime=document.getElementById("ploy.startTime").value;
          	var endtime =document.getElementById("ploy.endTime").value;
			structInfo+='					<div style="margin:0px;padding:0px;">';
			structInfo+='							<iframe id="selectContraceFrame" name="selectContraceFrame" src="<%=request.getContextPath()%>'+'/page/ploy/getChannelListByArea.do?ploy.areaId='+areaid+'&ploy.contractId='+contractId+'&ploy.ruleId='+ruleId+'&ploy.startTime='+starttime+'&ploy.endTime='+endtime+'" frameBorder="0" width="600px" height="570px"  scrolling="yes"></iframe>';
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
			structInfo+='							<iframe id="selectContraceFrame" name="selectContraceFrame" src="<%=request.getContextPath()%>'+'/page/ploy/modifychannel.jsp?areaid='+areaid+'" frameBorder="0" width="600px" height="570px"  scrolling="yes"></iframe>';
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
	.easyDialog_wrapper{ width:1000px;height:400px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
</head>

<body class="mainBody">
<form action="<%=path %>/page/constract/auditConstract.do" method="post" id="saveForm">

<input id="projetPath" type="hidden" value="<%=path%>"/>
<div class="detail">
    <table cellspacing="1" class="content" align="left">
        <tr class="title">
            <td colspan="4">合同基本信息 </td>
        </tr>
        <tr >
            <td width="10%" align="right"><span class="required">*</span>合同名称：</td>
            <td width="25%">
                <input id="contract.contractName" name="contract.contractName" type="hidden"  value="${contract.contractName}"/>
                ${contract.contractName}
                <input id="contract.id" name="contract.id" type="hidden" value="${contract.id}"/>
                <span id="contractName_error" ></span>
            </td>
			<td width="10%" align="right" ><span class="required">*</span>合同编号：</td>
            <td width="25%">
                <input id="contract.contractNumber" name="contract.contractNumber" type="hidden"  value="${contract.contractNumber}"/>
                ${contract.contractNumber}
                <span id="contractNumber_error" ></span>
            </td>
        </tr>
		 <tr >
            <td width="10%" align="right"><span class="required">*</span>合同号：</td>
            <td width="25%">           
                <input id="contract.contractCode" name="contract.contractCode" type="hidden"  value="${contract.contractCode}"/>
                ${contract.contractNumber}
                <span id="contractCode_error" ></span>
            </td>
			<td width="10%" align="right" ><span class="required">*</span>广告商：</td>
            <td width="25%">               
                <input id="contract.customerId" name="contract.customerId" value="${contract.customerId}" type="hidden"  readonly="readonly"/>
				<input id="contract.customerName" name="contract.customerName" value="${contract.customerName}" type="hidden" class="e_input_add" readonly="readonly" onclick="selectCustomer();"/>							
                ${contract.customerName}
                <span id="customer_error"></span>
            </td>
        </tr>   
        <tr >
            <td width="10%" align="right"><span class="required">*</span>送审单位：</td>
            <td width="25%">           
                <input id="contract.submitUnits" name="contract.submitUnits" type="hidden"  value="${contract.submitUnits}"/>
                ${contract.submitUnits}
                <span id="submitUnits_error" ></span>
            </td>
			<td width="10%" align="right" ><span class="required">*</span>广告位：</td>
            <td width="25%">                              
                <input id="adPositionPacks" name="adPositionPacks" type="hidden" value="${adPositionPacks}"/>
                <input id="adPositionPacks" onclick="selectAdPositionPacks();" type="button" class="btn" value="绑定广告位" disabled="disabled"/>
                
            </td>
        </tr> 
        <tr >
            <td width="10%" align="right"><span class="required">*</span>合同金额：</td>
            <td width="25%">
                <input id="contract.financialInformation" name="contract.financialInformation" type="hidden"  value="${contract.financialInformation}"/>
                ${contract.financialInformation}
                <span id="financialInformation_error" ></span>
            </td>
			<td width="10%" align="right" ><span class="required">*</span>广告审批文号：</td>
            <td width="25%">
                <input id="contract.approvalCode" name="contract.approvalCode" type="hidden"  value="${contract.approvalCode}"/>
                ${contract.approvalCode}
                <span id="approvalCode_error" ></span>
            </td>
        </tr>
        <tr>
            <td width="10%" align="right"><span class="required">*</span>合同开始日期：</td>
            <td width="25%">
                <input id="contract.effectiveStartDate" name="contract.effectiveStartDate" type="hidden"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'contract.effectiveStartDate\')}'})"   value='<fmt:formatDate value="${contract.effectiveStartDate}" dateStyle="medium"/>'/>             
                <fmt:formatDate value="${contract.effectiveStartDate}" dateStyle="medium"/>
                <span id="effectiveStartDate_error" ></span>
            </td>
      
            <td width="10%" align="right"><span class="required">*</span>合同截止日期：</td>
            <td width="25%">
                <input id="contract.effectiveEndDate" name="contract.effectiveEndDate"  type="hidden"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'contract.effectiveEndDate\')}'})"  value='<fmt:formatDate value="${contract.effectiveEndDate}" dateStyle="medium"/>'/>
                <fmt:formatDate value="${contract.effectiveEndDate}" dateStyle="medium"/>
                <span id="effectiveEndDate_error" ></span>
            </td>
        </tr>
        <tr>
            <td width="10%" align="right"><span class="required">*</span>合同执行开始日期：</td>
            <td width="25%">
                <input id="contract.approvalStartDate" name="contract.approvalStartDate" type="hidden"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'contract.approvalStartDate\')}'})"  value='<fmt:formatDate value="${contract.approvalStartDate}" dateStyle="medium"/>'/>
                <fmt:formatDate value="${contract.approvalStartDate}" dateStyle="medium"/>
                <span id="approvalStartDate_error" ></span>
            </td>
      
            <td width="10%" align="right"><span class="required">*</span>合同执行结束日期：</td>
            <td width="25%">
                <input id="contract.approvalEndDate" name="contract.approvalEndDate"  type="hidden"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'contract.approvalEndDate\')}'})"  value='<fmt:formatDate value="${contract.approvalEndDate}" dateStyle="medium"/>'/>
                <fmt:formatDate value="${contract.approvalEndDate}" dateStyle="medium"/>
                <span id="approvalEndDate_error" ></span>
            </td>
        </tr>
        <tr>
        	<td align="right"><span class="required">*</span>选择区域信息：</td>
			<td colspan="3">
				<input id="contract_area_codes" name="contract.areaCodes" type="hidden" value="${contract.areaCodes}" />
				<textarea rows="3" cols="70" id="contract_area_names" readonly="readonly" disabled>${contract.areaNames}</textarea>
			</td>
        </tr>
        <tr>
                <td width="12%" align="right">描 述：</td>
                <td width="33%">
                    <textarea disabled="disabled" id="contract.contractDesc" name="contract.contractDesc" rows="5">${contract.contractDesc}</textarea>
                    <span id="contractDesc_error"></span>
                </td>
                <td width="12%" align="right">审核意见：</td>
                <td width="33%" >
                    <textarea id="contract.examinationOpinions" name="contract.examinationOpinions" rows="5">${contract.examinationOpinions}</textarea>
                    <span id="reason_error"></span>
                </td>
        </tr> 
     
		
		
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
    <c:if test="${contract.status ==0 }">
         <input type="button" class="btn" value="通 过" onclick="aduitSuccess();"/>&nbsp;
        <input type="button" class="btn" value="驳 回" onclick="aduitFlase();"/>&nbsp; 
    </c:if>
        
        <input type="button" class="btn" value="返 回" onclick="gotoList();"/>
    </div>
</div>


<div id="system-dialog" title="友情提示">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
    <span id="content">99</span>
  </p>
</div>
</form>
</body>
</html>