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
var tempObj = "";
var accountAmountMoney = 0;
window.onload = function() {
	accountAmountMoney = $("#amountMoney").val();
	areaChannels=eval(${areasJson});
	$("#system-dialog").hide();
	 refreshAreaList();
	}

	function getAreaChannels(v){
		var tt = v.split(",");
		var obj = { 
    		positionId: tt[0], 
    		positionName: tt[1], 
    		deliveryMode:  tt[2],
    		videoType:  tt[3],
    		packageType:  tt[4],
    		positionStartDate:" ",
    		positionEndDate:""
    		};  
			areaChannels.push(obj);
		
	}
	
	function getFirstChannels(v){
		var tt = v.split(",");
		obj = [{ 
    		positionId: tt[0], 
    		positionName: tt[1], 
    		deliveryMode:  tt[2],
    		videoType:  tt[3],
    		packageType:  tt[4],
    		positionStartDate:" ",
    		positionEndDate:""
    		}];  
	    	areaChannels=obj;
	}
	/*
	 * 刷新区域列表
	 * */
    function refreshAreaList()
	{
		var audited=document.getElementById("audited").value;
		var contractId=document.getElementById("contract.id").value;
		if (audited!=null && audited==1)
		{
			document.getElementById("contract.contractName").disabled="true";
			document.getElementById("contract.contractNumber").disabled="true";
			document.getElementById("contract.contractCode").disabled="true";
			document.getElementById("contract.customerName").disabled="true";
			document.getElementById("contract.submitUnits").disabled="true";
			document.getElementById("contract.financialInformation").disabled="true";
			document.getElementById("contract.approvalCode").disabled="true";
			document.getElementById("contract.effectiveStartDate").disabled="true";
			document.getElementById("contract.effectiveEndDate").disabled="true";
			document.getElementById("contract.approvalStartDate").disabled="true";
			document.getElementById("adPositionPacksbutton").disabled="true";
			document.getElementById("contract_area_names").disabled="true";

		}
		document.getElementById("contract.approvalStartDate").disabled="true";
		
		if( areaChannels!=null )
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
		areaList=areaList+"	<td>操作</td>";
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
		     areaList=areaList+"    <td><input readonly=\"readonly\" onchange=\"positionStartDate('positionStartDate_"+areaChannels[i].positionId+"')\" id='positionStartDate_"+areaChannels[i].positionId+"'  name='positionStartDate_"+areaChannels[i].positionId+"' class='input_style2' type='text' style='width:80px;' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\" value='"+areaChannels[i].positionStartDate.substring(0,10)+"' /></td>";
		     areaList=areaList+"    <td><input readonly=\"readonly\" onchange=\"positionEndDate('positionEndDate_"+areaChannels[i].positionId+"')\" id='positionEndDate_"+areaChannels[i].positionId+"' name='positionEndDate_"+areaChannels[i].positionId+"' class='input_style2' type='text' style='width:80px;' onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"  value='"+areaChannels[i].positionEndDate.substring(0,10)+"'/></td>";
		  
		    
		     areaList=areaList+"   <td>";
		     areaList=areaList+" <input id='positionIds' name='positionIds' type='hidden' value='"+areaChannels[i].positionId +"'/>";

			 areaList=areaList+"	<a href='#' ";
			 if(contractId==""){			 
			    contractId=0;//新增合同查询广告位占用情况时使用
			 }
			 if (audited!=null && audited==1){
			       areaList=areaList+" disabled='disabled' onclick='deletePositon("+areaChannels[i].positionId+");'>删除</a>&nbsp;&nbsp;<a href='#' onclick='viewPositionOccupy("+areaChannels[i].positionId+","+contractId+")'>查看占用情况</a>";
			 }else{
			       areaList=areaList+" onclick='deletePositon("+areaChannels[i].positionId+");'>删除</a>&nbsp;&nbsp;<a href='#' onclick='viewPositionOccupy("+areaChannels[i].positionId+","+contractId+")'>查看占用情况</a>";
			 }
			 areaList=areaList+"	 </td>";
		     areaList=areaList+"  </tr>";
	    
	     }
	     areaList=areaList+"</table>";
	   	 document.getElementById("areaList").innerHTML=areaList;
	   	 }
	   	 tempObj = areaChannels;
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

	
    function submitForm(){
            document.getElementById("contract.approvalStartDate").disabled="";
            document.getElementById("contract.contractName").disabled="";
	        document.getElementById("contract.contractNumber").disabled="";
			document.getElementById("contract.contractCode").disabled="";
			document.getElementById("contract.customerName").disabled="";
			document.getElementById("contract.submitUnits").disabled="";
			document.getElementById("contract.financialInformation").disabled="";
			document.getElementById("contract.approvalCode").disabled="";
			document.getElementById("contract.effectiveStartDate").disabled="";
			document.getElementById("contract.effectiveEndDate").disabled="";
			document.getElementById("contract.approvalStartDate").disabled="";
			document.getElementById("adPositionPacksbutton").disabled="";
			document.getElementById("contract_area_names").disabled="";
			
			if(checkContract()){
    		return ;
		}
		
		var contractNumber = $$("contract.contractNumber").value;	 
		var contractId = $$("contract.id").value;
	 $.ajax({
                type:"post",
                async : false,
                url:"<%=request.getContextPath()%>/page/contract/checkContractExist.do?",
                data:{"contractCode":contractNumber,"contractId":contractId},//Ajax传递的参数
                success:function(mess)
                {
                	if(mess=="0")// 如果获取的数据不为空
                    {
                		document.getElementById("saveForm").submit();
                		//alert("ok");
                    }
                    else
                    {
						alert("合同编码已存在，请重新输入！");
						return;
                    }
                   
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
			
            //document.getElementById("saveForm").submit();
            //alert("ok");
    }
    
    function checkContract(){		     
        
    	if(isEmpty($$("contract.contractName").value)){
			alert("请输入合同名称！");
			$$("contract.contractName").focus();
    		return true;
		}
		if($$("contract.contractName").value.length>255){
			alert("合同名称必须小于255个字节！");
			$$("contract.contractName").focus();
    		return true;
		}
    	if(isEmpty($$("contract.contractNumber").value)){
			alert("请输入合同编号！");
			$$("contract.contractNumber").focus();
    		return true;
		}
		if($$("contract.contractNumber").value.length>255){
			alert("合同编号必须小于255个字节！");
			$$("contract.contractNumber").focus();
    		return true;
		}
    	if(!isEmpty($$("contract.contractCode").value)){
			if($$("contract.contractCode").value.length>255){
			alert("合同号必须小于255个字节！");
			$$("contract.contractCode").focus();
    		return true;
		}
		}
		
    	if(isEmpty($$("contract.customerName").value)){
			alert("请选择广告商！");
			$$("contract.customerName").focus();
    		return true;
		}
		if(isEmpty($$("contract.submitUnits").value)){
			alert("请输入送审单位！");
			$$("contract.submitUnits").focus();
    		return true;
		}
		if($$("contract.submitUnits").value.length>255){
			alert("送审单位必须小于255个字节！");
			$$("contract.submitUnits").focus();
    		return true;
		}
		if( areaChannels==null ||  areaChannels.length <= 0){
		    alert("请绑定广告位！");
    		return true;
		}
		if(isEmpty($$("contract.financialInformation").value)){
			alert("请输入合同金额！");
			$$("contract.financialInformation").focus();
    		return true;
		}else{
		    var coordinates = $$("contract.financialInformation").value.split(".");
			if(coordinates.length == 1 || coordinates.length == 2 ){
				if(coordinates.length == 1){
				      //整数
				      if(isNaN($$("contract.financialInformation").value)){
			              alert("合同金额必须为数字！");
			              $$("contract.financialInformation").focus();
    		              return true;
		                 }
				}else{
				      //小数
				      if(!isNumber(coordinates[0]) || !isNumber(coordinates[1])){
			              alert("合同金额格式不正确！");
			              $$("contract.financialInformation").focus();
    		              return true;
		                 }
				}
			}else{
			    alert("合同金额格式不正确！");
				$$("contract.financialInformation").focus();
	    		return true;
			}
		
		}
		
		
		//if(isNaN($$("contract.financialInformation").value)){
			//alert("合同金额必须为数字！");
			//$$("contract.financialInformation").focus();
    		//return true;
		//}
		if($$("contract.financialInformation").value.length>255){
			alert("合同金额必须小于255个字节！");
			$$("contract.financialInformation").focus();
    		return true;
		}
		if(!isEmpty($$("contract.approvalCode").value)){
			if($$("contract.approvalCode").value.length>255){
			alert("审批文号必须小于255个字节！");
			$$("contract.approvalCode").focus();
    		return true;
		}
		}
		
    	if(isEmpty($$("contract.effectiveStartDate").value)){
			alert("请选择合同开始日期！");
			$$("contract.effectiveStartDate").focus();
    		return true;
		}
    	if(isEmpty($$("contract.effectiveEndDate").value)){
			alert("请选择合同截止日期！");
			$$("contract.effectiveEndDate").focus();
    		return true;
		}
		
		if(!dateCompare($$("contract.effectiveStartDate").value,$$("contract.effectiveEndDate").value)){
		    alert("合同截止日期必须大于合同开始日期！");
			$$("contract.effectiveEndDate").focus();
			return true;
		}
		
		if(isEmpty($$("contract.approvalStartDate").value)){
			alert("请选择合同执行开始日期！");
			$$("contract.approvalStartDate").focus();
    		return true;
		}
    	if(isEmpty($$("contract.approvalEndDate").value)){
			alert("请选择合同执行结束日期！");
			$$("contract.approvalEndDate").focus();
    		return true;
		}
		
		if(!dateCompare($$("contract.approvalStartDate").value,$$("contract.approvalEndDate").value)){
		    alert("合同执行结束日期必须大于合同执行开始日期！");
			$$("contract.approvalEndDate").focus();
			return true;
		}
		if( isEmpty($$("contract_area_names").value)){
		    alert("请绑定区域！");
			$$("contract_area_names").focus();
			return true;
		}
		for (var i=0;i<areaChannels.length;i++){
		   var sd = "positionStartDate_"+areaChannels[i].positionId;
		   var ed = "positionEndDate_"+areaChannels[i].positionId;
		   if(isEmpty($$(sd).value)){
			alert("请选择投放开始日期！");
			$$(sd).focus();
    		return true;
		   }
		   if(isEmpty($$(ed).value)){
			alert("请选择投放结束日期！");
			$$(ed).focus();
    		return true;
		   }
		   if(!dateCompare($$(sd).value,$$(ed).value)){
		    alert("投放结束日期必须大于投放开始日期！");
			$$(ed).focus();
			return true;
		}
		   
		}
        
        if(isEmpty($$("contract.contractDesc").value)){

		}else{
		   if($$("contract.contractDesc").value.length>255){
			alert("描述必须小于255个字节！");
			$$("contract.contractDesc").focus();
    		return true;
		   }
		}

		return false;
    }
    
    function dateCompare(startdate,enddate){   
        var arr=startdate.split("-");    
        var starttime=new Date(arr[0],arr[1],arr[2]);    
        var starttimes=starttime.getTime();   
  
        var arrs=enddate.split("-");    
        var lktime=new Date(arrs[0],arrs[1],arrs[2]);    
        var lktimes=lktime.getTime();   
  
        if(starttimes>=lktimes){   
           return false;   
        }   
        else  
        return true;    
     }
    
    function gotoList(){

        window.location.href="<%=path %>/page/contract/queryContractList8.do";
    }



	 
	/* $(document).ready(function(){
	 
 		$("#selectAreas").click(function(){
	  	selectArea();
 		});
});*/
	  
	 function selectAdPositionPacks() {
		 var structInfo ="";
			structInfo+="<div style='margin:0px;padding:0px;width:600px'>";
			structInfo+="<iframe id='selectAdPositionPacksFrame' name='selectAdPositionPacksFrame'  frameBorder='0' width='1000px' height='400px'  scrolling='yes'  align='top' src='<%=request.getContextPath()%>/page/contract/selectAdPositionPackage.do'> ";
			structInfo+="</iframe>";
			structInfo+="</div>";
			easyDialog.open({
			container : {
				header : "添加广告位",
				content : structInfo
			},
			overlay : true
			
		});
	}
	function selectAreas() {
		 var structInfo ="";
			structInfo+="<div style='margin:0px;padding:0px;width:600px'>";
			structInfo+="<iframe id='selectAreasFrame' name='selectAreasFrame'  frameBorder='0' width='1000px' height='420px'  scrolling='yes'  align='top' src='<%=request.getContextPath()%>/page/contract/selectAreas.do'> ";
			structInfo+="</iframe>";
			structInfo+="</div>";
			easyDialog.open({
			container : {
				header : "添加区域",
				content : structInfo
			},
			overlay : true
			
		});
	 }
	 
	 function selectCustomer() {
          	var structInfo = "";
			structInfo+="<div style='margin:0px;padding:0px;width:600px'>";
		    structInfo+="<iframe id='bindingCustomerFrame' name='bindingCustomerFrame'  frameBorder='0' width='1000px' height='500px'  scrolling='yes'  align='top' src='<%=request.getContextPath()%>/page/contract/selectCustomer.do'></iframe>";
		    structInfo+="</div>";
			
			easyDialog.open({
			container : {
				header : "选择广告商",
				content : structInfo
			},			
			overlay : true
		});
	 }
	 
	 function selectCustomerff() {
          	var structInfo = '';
			structInfo+='<div style="margin:0px;padding:0px;">';
		    structInfo+='<iframe id="bindingCustomerFrame" name="bindingCustomerFrame" src="<%=request.getContextPath()%>'+'/page/contract/selectCustomer.do?'+'" frameBorder="0" width="1000px" height="400px"  scrolling="yes"></iframe>';
		    structInfo+='</div>';
			
			easyDialog.open({
			container : {
				header : '选择广告商',
				content : structInfo
			},			
			overlay : false
		});
	 }
	 
	 function onchangeEffDate()
	 {
		 document.getElementById("contract.approvalStartDate").value=document.getElementById("contract.effectiveStartDate").value;
		 document.getElementById("contract.approvalStartDate").disabled="true";
		 
	 }
	 function onchangeappEndDate()
	 {
		 var endDate = document.getElementById("contract.approvalEndDate").value;
		 var effendDate=document.getElementById("contract.effectiveEndDate").value;
		if (endDate<effendDate)
		{
			//alert("执行日期必须大于合同结束日期");	
			document.getElementById("contract.approvalEndDate").value=effendDate;
		}
		// document.getElementById("contract.approvalEndDate").value;
	 }
	 function onchangeEffEndDate()
	 {
		var endDate = document.getElementById("contract.approvalEndDate").value;
		var effendDate=document.getElementById("contract.effectiveEndDate").value;
		if (endDate<effendDate)
		{
			document.getElementById("contract.approvalEndDate").value=effendDate;
		}
	 }
	  function positionStartDate(objectid)
	 {
		//return;
		var startDate = document.getElementById("contract.approvalStartDate").value;
		 var endDate = document.getElementById("contract.approvalEndDate").value;
		var positionStartDate=document.getElementById(objectid).value;
		//alert("广告位开始时间："+document.getElementById(objectid).value);
		if (startDate==null || startDate=='')
		{
		    alert("请输入合同执行开始日期！");
		    document.getElementById(objectid).value="";
			return ;
		}
		if (endDate==null || endDate=='')
		{
		    alert("请输入合同执行结束日期！");
		    document.getElementById(objectid).value="";
			return ;
		}
		if (startDate>positionStartDate)
		{
		   if(positionStartDate!=''){
		        document.getElementById(objectid).value=startDate;
		   }
			
		}
		//alert("广告位开始时间2："+document.getElementById(objectid).value);
		if (endDate<positionStartDate)
		{
			document.getElementById(objectid).value=endDate;
		}
			var temp=objectid.split("_")
			//alert("temp:"+temp);
		var positionEndDate = document.getElementById("positionEndDate_"+temp[1]).value;
		//alert("positionEndDate:"+positionEndDate);
		if (positionStartDate==null || positionStartDate=='')
		{
			//return false;
		}
		if (positionEndDate==null || positionEndDate=='')
		{
			//return false;
		}
		var contractId = document.getElementById("contract.id").value;
		var positionId = temp[1];
		//alert("positionId:"+positionId);
		var paramtemp = '{"startDate":"'+document.getElementById(objectid).value+'","positionId":"'+positionId+'"';
		if (positionEndDate!=null && positionEndDate!='')
		{
			paramtemp +=',"endDate":"'+document.getElementById("positionEndDate_"+temp[1]).value+'"';
		}
		if (contractId!=null && contractId!='')
		{
			paramtemp +=',"contractId":"'+contractId+'"';
		}
		paramtemp +='}';
		//alert("paramtemp:"+paramtemp);
		var params =JSON.parse(paramtemp);
 			$.ajax({
                type:"post",
                url:"<%=request.getContextPath()%>/page/contract/checkPostitionUsed.do?",//从哪获取Json
                data:params,//Ajax传递的参数
                success:function(mess)
                {
                    if(mess=="1")// 如果获取的数据不为空
                    {
						alert("广告位已销售");
						document.getElementById(objectid).value="";
                    	return false;
                    }
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
			return true;
	 }
	   function positionEndDate(objectid)
	 {
		//return;
		//alert(objectid);
			var startDate = document.getElementById("contract.approvalStartDate").value;
			   var endDate = document.getElementById("contract.approvalEndDate").value;
		var positionEndDate=document.getElementById(objectid).value;
		if (startDate==null || startDate=='')
		{
		    alert("请输入合同执行开始日期！");
		    document.getElementById(objectid).value="";
			return ;
		}
		if (endDate==null || endDate=='')
		{
		    alert("请输入合同执行结束日期！");
		    document.getElementById(objectid).value="";
			return ;
		}
		if (endDate<positionEndDate)
		{
		   if(positionEndDate!=''){
		        document.getElementById(objectid).value=endDate;
		   }
			
		}
		if (startDate>positionEndDate)
		{
			document.getElementById(objectid).value=startDate;
		}
		var temp=objectid.split("_")
		var positionStartDate = document.getElementById("positionStartDate_"+temp[1]).value;
		
		if (positionStartDate==null || positionStartDate=='')
		{
		//	return ;
		}
		if (positionEndDate==null || positionEndDate=='')
		{
		//	return ;
		}
		
		var contractId = document.getElementById("contract.id").value;
		var positionId = temp[1];
		var paramtemp = '{"endDate":"'+document.getElementById(objectid).value+'","positionId":"'+positionId+'"';
		if (positionStartDate!=null && positionStartDate!='')
		{
			paramtemp +=',"startDate":"'+document.getElementById("positionStartDate_"+temp[1]).value+'"';
		}
		if (contractId!=null && contractId!='')
		{
			paramtemp +=',"contractId":"'+contractId+'"';
		}
		paramtemp +='}';
		var params =JSON.parse(paramtemp);
		
 		 //data:{"contractId":contractId,"positionId":positionId,"endDate":positionEndDate,"startDate":positionStartDate},//Ajax传递的参数
              
 			$.ajax({
                type:"post",
                url:"<%=request.getContextPath()%>/page/contract/checkPostitionUsed.do?",//从哪获取Json
                data:params,//Ajax传递的参数
                success:function(mess)
                {
                    if(mess=="1")// 如果获取的数据不为空
                    {
						alert("广告位已销售");
						document.getElementById(objectid).value="";
                    	return false;
                    }
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
			return true;
	 }
	   
	function showAccounts(contractId){
		var structInfo = "";
			structInfo+="<div style='margin:0px;padding:0px;width:600px'>";
		    structInfo+="<iframe id='showAccounts' name='showAccounts'  frameBorder='0' width='1000px' height='500px'  scrolling='yes'  align='top' src='<%=request.getContextPath()%>/page/accounts/queryAccountsList.do?contractId="
		    structInfo+=contractId;
		    structInfo+="'></iframe>";
		    structInfo+="</div>";
			
			easyDialog.open({
			container : {
				header : "查看台账流水",
				content : structInfo
			},			
			overlay : true
		});
	}
	
	function payforContract(contractId){
		var structInfo = "";
			structInfo+="<div style='margin:0px;padding:0px;width:600px'>";
		    structInfo+="<iframe id='showAccounts' name='showAccounts'  frameBorder='0' width='1000px' height='500px'  scrolling='yes'  align='top' src='<%=request.getContextPath()%>/page/accounts/addAccounts.do?contractId="
		    structInfo+=contractId;
		    structInfo+="'></iframe>";
		    structInfo+="</div>";
			
			easyDialog.open({
			container : {
				header : "新增台账",
				content : structInfo
			},			
			overlay : true
		});
	}
	
	function rewriteMoney(addmoney){
		var newMoney = parseFloat(accountAmountMoney)+ parseFloat(addmoney);
		document.getElementById("amount").innerHTML=newMoney;
		accountAmountMoney = newMoney;
	}
	
	   
	   function viewPositionOccupy(positionId,contractId){
    	$.ajax({   
 	       url:'checkSession.do',       
 	       type: 'POST',    
 	       dataType: 'text',   
 	       data: {
 		   },                   
 	       timeout: 1000000000,                              
 	       error: function(){                      
 	    		alert("系统错误，请联系管理员！");
 	       },    
 	       success: function(result){
 	    	   if(result=='0'){
 	    		  var url = "viewPositionOccupyForContractManager.do?contractADBackup.positionId="+positionId+"&contractADBackup.id="+contractId;
 	    	      window.showModalDialog(url, "", "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
 	    	   }else{
 	 	    	   //会话已经过期
 	    		  window.location.href=getContextPath()+'/tset/timeoutPage.jsp'
 	 	    	}	    	   
 		   }  
 		}); 
    }
	   
function  exportContract(id){
	 
	 window.location = "<%=request.getContextPath()%>/page/contract/exportContract.do?contractId="+id ;
  }
	   
</script>

<style>
	.easyDialog_wrapper{ width:1000px;height:400px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
</head>

<body class="mainBody">
<form action="<%=path %>/page/constract/saveConstractBackup.do" method="post" id="saveForm">
<input id="amountMoney" type="hidden" value="${accountsAmount}"/>
<input id="projetPath" type="hidden" value="<%=path%>"/>
<div class="detail">
    <table cellspacing="1" class="content" align="left">
        <tr class="title">
            <td colspan="4">合同基本信息 </td>
        </tr>
        <tr >
            <td width="10%" align="right"><span class="required">*</span>合同名称：</td>
            <td width="25%">
                <input id="contract.contractName" name="contract.contractName" type="text"  value="${contract.contractName}"/>
                <input id="contract.id" name="contract.id" type="hidden" value="${contract.id}"/>
                <input id="audited" name="audited" type="hidden" value="${audited}"/>
                <input id="contract.auditTaff" name="contract.auditTaff" type="hidden" value="${contract.auditTaff}"/>
                <input id="contract.auditDate" name="contract.auditDate" type="hidden" value="${contract.auditDate}"/>
                <input id="contract.examinationOpinions" name="contract.examinationOpinions" type="hidden" value="${contract.examinationOpinions}"/>
                <input id="contract.createTime" name="contract.createTime" type="hidden" value="${contract.createTime}"/>
                <span id="contractName_error" ></span>
            </td>
			<td width="10%" align="right" ><span class="required">*</span>合同编号：</td>
            <td width="25%">
                <input id="contract.contractNumber" name="contract.contractNumber" type="text"  value="${contract.contractNumber}"/>
                <span id="contractNumber_error" ></span>
            </td>
        </tr>
		 <tr >
            <td width="10%" align="right">合同号：</td>
            <td width="25%">           
                <input id="contract.contractCode" name="contract.contractCode" type="text"  value="${contract.contractCode}"/>
                <span id="contractCode_error" ></span>
            </td>
			<td width="10%" align="right" ><span class="required">*</span>广告商：</td>
            <td width="25%">               
                <input id="contract.customerId" name="contract.customerId" value="${contract.customerId}" type="hidden"  readonly="readonly"/>
				<input id="contract.customerName" name="contract.customerName" value="${contract.customerName}" type="text" class="e_input_add" readonly="readonly" onclick="selectCustomer();"/>							
                <span id="customer_error"></span>
            </td>
        </tr>   
        <tr >
            <td width="10%" align="right"><span class="required">*</span>送审单位：</td>
            <td width="25%">           
                <input id="contract.submitUnits" name="contract.submitUnits" type="text"  value="${contract.submitUnits}"/>
                <span id="submitUnits_error" ></span>
            </td>
			<td width="10%" align="right" ><span class="required">*</span>广告位：</td>
            <td width="25%">                              
                <input id="adPositionPacks" name="adPositionPacks" type="hidden" value="${adPositionPacks}"/>
                <input id="adPositionPacksbutton" onclick="selectAdPositionPacks();" type="button" class="btn" value="绑定广告位" />
                
            </td>
        </tr> 
        <tr >
            <td width="10%" align="right"><span class="required">*</span>合同金额：</td>
            <td width="25%">
                <input id="contract.financialInformation" name="contract.financialInformation" type="text"  value="${contract.financialInformation}"/>
                <span id="financialInformation_error" ></span><span class="required">万元</span>
            </td>
			<td width="10%" align="right" >广告审批文号：</td>
            <td width="25%">
                <input id="contract.approvalCode" name="contract.approvalCode" type="text"  value="${contract.approvalCode}"/>
                <span id="approvalCode_error" ></span>
            </td>
        </tr>
        <tr>
            <td width="10%" align="right"><span class="required">*</span>合同开始日期：</td>
            <td width="25%">
                <input readonly="readonly" id="contract.effectiveStartDate" name="contract.effectiveStartDate" type="text" onchange="onchangeEffDate()" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"   value='<fmt:formatDate value="${contract.effectiveStartDate}" type="date" pattern="yyyy-MM-dd"/>'/>             
                <span id="effectiveStartDate_error" ></span>
                
            </td>
      
            <td width="10%" align="right"><span class="required">*</span>合同截止日期：</td>
            <td width="25%">
                <input readonly="readonly" id="contract.effectiveEndDate" name="contract.effectiveEndDate"  type="text" onchange="onchangeEffEndDate()" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  value='<fmt:formatDate value="${contract.effectiveEndDate}" type="date" pattern="yyyy-MM-dd"/>'/>
                <span id="effectiveEndDate_error" ></span>
            </td>
        </tr>
        <tr>
            <td width="10%" align="right"><span class="required">*</span>合同执行开始日期：</td><!--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'contract.approvalStartDate\')}'})"  value='<fmt:formatDate value="${contract.approvalStartDate}" dateStyle="medium"/>'  -->
            <td width="25%">
                <input disabled="disabled" readonly="readonly" id="contract.approvalStartDate" name="contract.approvalStartDate" type="text" value='<fmt:formatDate value="${contract.approvalStartDate}" type="date" pattern="yyyy-MM-dd"/>'  />
                <span id="approvalStartDate_error" ></span>
            </td>
      
            <td width="10%" align="right"><span class="required">*</span>合同执行结束日期：</td>
            <td width="25%">
                <input readonly="readonly" id="contract.approvalEndDate" name="contract.approvalEndDate"  type="text" onchange="onchangeappEndDate()"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  value='<fmt:formatDate value="${contract.approvalEndDate}" type="date" pattern="yyyy-MM-dd"/>'/>
               
                <span id="approvalEndDate_error" ></span>
            </td>
        </tr>
        <tr >
			<td align="right"><span class="required">*</span>选择区域信息：</td>
			<td colspan="3">
				<input id="contract_area_codes" name="contract.areaCodes" type="hidden" value="${contract.areaCodes}" />
				<textarea rows="3" cols="70" id="contract_area_names" readonly="readonly" onclick="selectAreas();">${contract.areaNames}</textarea>
			</td>
		</tr>
        <tr>
                <td width="12%" align="right">描 述：</td>
                <td width="38%" >
                    <textarea style="" id="contract.contractDesc" name="contract.contractDesc" rows="5">${contract.contractDesc}</textarea>
                    <span id="contractDesc_error"></span>
                </td>
                <td width="10%" align="right" >合同台账信息：</td>
           		<td width="25%">                              
                <a id="showAccountsList" href="javascript:showAccounts('${contract.id}')";>总计付款：<u id="amount">${accountsAmount} </u>万元</a><br />
                <input  style="margin-top:5px" id="payfor" onclick="payforContract( '${contract.id}');" type="button" class="btn" value="付款"  
                <c:if test="${contract.id == null}">
                	disabled="disabled"
                </c:if>
                />
            	</td>
        </tr>      
		
		
    </table>

    
    <div id="areaList" >
		
</div>
    <div align="center" class="action">
         <input type="button" class="btn" value="保 存" onclick="submitForm();"/>&nbsp;&nbsp;&nbsp;&nbsp;  
         <c:if test="${contract.id!=null}">
             <input type="button" class="btn" value="导 出" onclick="exportContract(${contract.id});"/>&nbsp;&nbsp;&nbsp;&nbsp;
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