<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type='text/javascript' src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/preview.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/common.js"></script>
<title>新增订单</title>
<style>
	.ggw {
		width: 100%;
		height: 68px;
	}
	.ggw li {
		background: #efefef;
		font-weight: bold;
		width: 100%;
		height: 25px;
	}
	.e_input_add {
		background:url(<%=path%>/images/add.png) right no-repeat #ffffff;
	}
	
	.e_input_time{
		background:url(<%=path%>/js/new/My97DatePicker/skin/datePicker.gif) right no-repeat #ffffff;
	}
</style>
<script type="text/javascript">
	var contractId = -1;
	var positionId = -1;
	var ployId = -1;
	var validMinDate = '2010-01-01';//广告位投放开始日期
	var validMaxDate = '2099-01-01';//广告位投放结束日期
	var ployStartTime = '';//策略开始时间
	var ployEndTime = '';//策略结束时间
	var deliveryMode = 1;//投放方式
	var selResource = '';//选择的素材
	var aheadTime=0; //提前量
	var playNumber = 0;//按次播放次数
	var ployNumber = 1;//分策略或基本策略加精准总数
	var orderId=-1;
	var previewValue;
	function init(time){
		aheadTime = time;
	}

	/**
	*  进入弹出页面前，先检查会话是否过期
	*/
	function showContent(content,pId){
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
 	    		  if(content == 'contract'){
 	    			 showContract();
 	 	    	  }else if(content == 'position'){
 	 	    		showPosition();
 	 	    	  }else if(content == 'ploy'){
 	 	    		showPloy();
 	 	    	  }else if(content == 'resource'){
 	 	    		showResource(pId);
 	 	    	  }
 	    	   }else{
 	 	    	   //会话已经过期
 	    		  window.location.href=getContextPath()+'/tset/timeoutPage.jsp'
 	 	    	}	    	   
 		   }  
 		}); 
    }
    
	//选择合同
	function showContract(){
		var url = "showContractList.do?order.contractId="+contractId;
		var contractValue = window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
		if(contractValue){
			if(contractId != contractValue.split("_")[0]){
				contractId = contractValue.split("_")[0];
				$("#contractId").val(contractId);
				$("#contractName").val(contractValue.split("_")[1]);
				document.getElementById("contractName").style.borderColor="";
	        	document.getElementById("contract_error").innerHTML = "";
	        	$("#positionId").val("");
				$("#positionName").val("");
				$("#ployId").val("");
				$("#ployName").val("");
				$("#startDate").val("");
				$("#startDate2").val("");
				$("#endDate").val("");
				$("#selResource").val("");
				positionId = -1;
				ployId = -1;
			}
        }
	}

	//选择广告位
    function showPosition(){
    	contractId = document.getElementById("contractId").value;
    	if(!contractId || contractId==-1){
        	document.getElementById("contractName").style.borderColor="red";
        	document.getElementById("contract_error").innerHTML = "合同不能为空";
    		return;
    	}
    	var url = "showPositionList.do?advertPosition.contractId="+contractId+"&order.positionId="+positionId;
    	var value = window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
        previewValue=value;
        if(value){
            if(positionId != value.split("_")[0]){
	        	positionId = value.split("_")[0];
	        	$("#positionId").val(positionId);
				$("#positionName").val(value.split("_")[1]);
				validMinDate = value.split("_")[2];
				validMaxDate = value.split("_")[3];
				deliveryMode = value.split("_")[4];
				document.getElementById("positionName").style.borderColor="";
	        	document.getElementById("position_error").innerHTML = "";
	        	$("#ployId").val("");
				$("#ployName").val("");
				$("#startDate").val("");
				$("#startDate2").val("");
				$("#endDate").val("");
				$("#selResource").val("");
				ployId = -1;
				//预览
				preview(value.split("_")[5],value.split("_")[6],value.split("_")[7]);
				//问卷广告位，订单添加用户次数、问卷次数、通知门限值、积分兑换人民币比率
				if(positionId=='27'||positionId=='28'){
					$("#questionnaire1").show();
					$("#questionnaire2").show();
				}else{
					$("#questionnaire1").hide();
					$("#questionnaire2").hide();
				}
            }
        }
    }

    //选择策略
    function showPloy(){
    	//var positionId = document.getElementById("positionId").value;
    	if(!positionId || positionId == -1){
        	document.getElementById("positionName").style.borderColor="red";
        	document.getElementById("position_error").innerHTML = "广告位不能为空";
    		return;
    	}
    	var ployId = document.getElementById("ployId").value;
    	var url = "showPloyList.do?ploy.positionId="+positionId+"&order.ployId="+ployId;
    	var value = window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
    	var orderId=${order.orderCode};
        if(value){
            if(ployId != value.split("_")[0]){
            	ployId = value.split("_")[0];
            	ployName = value.split("_")[1];
	        	$("#ployId").val(ployId);
				$("#ployName").val(ployName);
				playNumber = value.split("_")[3];//播放次数
				if(playNumber && playNumber>0){
					$("#playNumber").val(playNumber);
					$("#viewPlayNumber").val(playNumber);
					$("#periodDate").hide();
					$("#onceDate").show();
				}else{
					$("#periodDate").show();
					$("#onceDate").hide();
				}
				/*
				var adPackageType = value.split("_")[2];
				if(adPackageType == 0 || adPackageType == 1){
					//双向广告位策略
					$.ajax({   
			 		       url:'showPrecises4Json.do',       
			 		       type: 'POST',    
			 		       dataType: 'text',   
			 		       data: {
			 		    	   id:ployId
			 				},                   
			 		       timeout: 1000000000,                              
			 		       error: function(){                      
			 		    		alert("系统错误，请联系管理员！");
			 		       },    
			 		       success: function(result){ 
			 		    	   if(result == '-1'){
			 		    		  alert("系统错误，请联系管理员！");
			 		    	   }else{
			 		    		  ployNumber = viewTwoPloy(result,0);
			 		    	   }
			 			   }  
			 		   });
				}else{
					//单向广告位策略
					$.ajax({   
			 		       url:'getAreaResource.do',       
			 		       type: 'POST',    
			 		       dataType: 'text',   
			 		       data: {
			 		    	   orderId:orderId
			 				},                   
			 		       timeout: 1000000000,                              
			 		       error: function(){                      
			 		    		alert("系统错误，请联系管理员！");
			 		       },    
			 		       success: function(result){ 
			 		    	   if(result == '-1'){
			 		    		  alert("系统错误，请联系管理员！");
			 		    	   }else{
			 		    	     ployNumber = viewOneAreaPloy(result,0);
			 		    		 // ployNumber = viewOnePloy(result,0);
			 		    	   }
			 			   }  
			 		   });
				}
				$("#selResource").val("");*/
				//选择策略，保存订单和素材临时关系数据
				var orderCode = document.getElementById("orderCode").value;
				$.ajax({   
		 		       url:'insertOrderMateRelTmp.do',       
		 		       type: 'POST',    
		 		       dataType: 'text',   
		 		       data: {
		 		    	   orderCode:orderCode,
		 		    	   ployId:ployId,
		 		    	   positionId:positionId
		 				},                   
		 		       timeout: 1000000000,                              
		 		       error: function(){                      
		 		    		alert("系统错误，请联系管理员！");
		 		       },    
		 		       success: function(result){ 
		 		    	   if(result == '-1'){
		 		    		  alert("系统错误，请联系管理员！");
		 		    	   }
		 			   }  
		 		   });
            }
        }
        hidDetails();
    }
     
    function showResource(pId){
    	var index = selResource.indexOf(pId+"#");
    	var selectResource = '';
    	var resStr = '';
        if(index >= 0){
        	var index2 = selResource.indexOf("~",index);
        	var selectResource = selResource.substring(index,index2);
        	var relsArray = selectResource.split("#");
        	resStr = relsArray[1];
				
        }
		var url = "showResourceList.do?resource.advertPositionId="+positionId+"&order.selResource="+resStr+"&ployId="+$("#ployId").val();
		var resourceArray = window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
		var materialJson= "";
        if (resourceArray && resourceArray != null) {
        	if(resourceArray.length>0){
	        	var materialName = "";
				for(var i=0;i<resourceArray.length;i++){
				    //视频插播位置
					var inStreamArray = resourceArray[i].inStreamArray;
					if(inStreamArray && inStreamArray.length>0){
						for(var j=0;j<inStreamArray.length;j++){
							materialJson += resourceArray[i].resourceId+","+resourceArray[i].mLoopsValue+","+inStreamArray[j]+"@";
							materialName += "<a href='javascript:viewMaterial("+resourceArray[i].resourceId+")'>"+resourceArray[i].resourceName+"</a>";
							if(inStreamArray[j] != '-1'){
								materialName +="[播放位置："+inStreamArray[j]+"]";
							}
							if(resourceArray[i].mLoopsValue != '-1'){
								materialName += "[轮询序号："+resourceArray[i].mLoopsValue+"]"
							}
							materialName += ",";
						}
					}else{
						materialJson += resourceArray[i].resourceId+","+resourceArray[i].mLoopsValue+",-1@";
						materialName += "<a href='javascript:viewMaterial("+resourceArray[i].resourceId+")'>"+resourceArray[i].resourceName+"</a>";
						//materialvalue=resourceArray[i].resourceId;
						if(resourceArray[i].mLoopsValue != '-1'){
							materialName += "[轮询序号："+resourceArray[i].mLoopsValue+"]"
						}
						materialName += ",";
					}
				}
				index = selResource.indexOf(pId+"#");
	            if(index >= 0){
	            	index2 = selResource.indexOf("~",index);
	            	selResource = selResource.substring(0,index)+selResource.substring(index2+1);
	            }
	            selResource = selResource+pId+"#"+materialJson+"~";
	            //$("#selResource").val(selResource);
	            $("#mp"+pId).html(materialName.substring(0,materialName.length-1));
        	}else{//取消绑定素材
            	index = selResource.indexOf(pId+"#");
                if(index >= 0){
                	index2 = selResource.indexOf("~",index);
                	selResource = selResource.substring(0,index)+selResource.substring(index2+1);
                }
        		$("#mp"+pId).html('');
            }
    	}
        $("#selResource").val(selResource);
	}

    /**
    * 选择订单日期
    */
    function selectDate(){
    	var ahead = 0;
    	if(deliveryMode==0){
    		ahead =  parseInt(aheadTime)*1000;
    	}
    	var now = Date.parse(new Date())+ahead;
    	var vStartDate = Date.parse(validMinDate.replace(/-/g,"/"));
    	//当前时间大于投放开始时间，则订单开始时间只能选择大于当前时间，否则订单开始时间从投放开始时间选择
    	var tmp = '#';
    	if(now > vStartDate){
    		WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-'+tmp+'{%d}',maxDate:validMaxDate});
    	}else{
    		WdatePicker({dateFmt:'yyyy-MM-dd',minDate:validMinDate,maxDate:validMaxDate});
    	}
    }

    /**
     * 保存订单
     */
    function saveOrder(){
    	if(checkFormNotNull()&&checkOrderDate()){
    		if(!playNumber || playNumber<=0){
    			$("#startTime").val($("#startDate").val())
        	}else{
        		$("#startTime").val($("#startDate2").val())
            }
    		var startDate = $("#startTime").val();
    		var endDate = $("#endDate").val();
    		var ployId = $("#ployId").val();
    		var positionId = $("#positionId").val();
    		//var selResource = $("#selResource").val();
    		var orderCode = $("#orderCode").val();
    		$.ajax({   
   		       url:'checkOrderRule2.do',       
   		       type: 'POST',    
   		       dataType: 'text',   
   		       data: {
	   				startDate:startDate,
	   				endDate:endDate,
	   				ployId:ployId,
	   				positionId:positionId,
	   				orderCode:orderCode
   			   },                   
   		       timeout: 1000000000,                              
   		       error: function(){                      
   		    		alert("系统错误，请联系管理员！");
   		       },    
   		       success: function(result){ 
   		    	   if(result!='-1'){
   			    	   if(result=='0'){
   			    			$("#saveForm").submit();
   			    			/*
   	   			    	   var resourceArray = selResource.split("~");
   	   			    	   if(resourceArray.length != (ployNumber+1)){
	   	   			    		if (confirm("有部分分策略未选择素材，是否提交订单？")) {
	   	   			    			$("#saveForm").submit();
	   	   	   			    	}
   	   	   			       }else{
   	   	   			  			$("#saveForm").submit();
   	   	   	   			   }*/
   			    	   }else{
   			    		   alert(result);
   			    	   }
   		    	   }else{
   			    		alert("系统错误，请联系管理员！");
   		    	   }
   		    	   
   			   }  
   		   }); 
    		
    	}
	}

    /**
     * 检查输入项是否符合要求
     * */
    function checkFormNotNull(){
    	if(isEmpty($("#contractId").val())){
    		alert("请选择合同！");
    		$("#contractId").focus();
    		return false;
    	}
    	if(isEmpty($("#positionId").val())){
    		alert("请选择广告位！");
    		$("#positionId").focus();
    		return false;
    	}
    	if(isEmpty($("#ployId").val())){
    		alert("请选择策略！");
    		$("#ployId").focus();
    		return false;
    	}

    	if($("#positionId").val() == '27' || $("#positionId").val()=='28'){
        	//问卷广告位，用户次数、问卷次数、积分兑换人民币比率必填
        	var userNumber = $("#userNumber").val();
    		if(isEmpty(userNumber)){
        		alert("请输入用户总次数！");
        		$("#userNumber").focus();
        		return false;
        	}
    		if(!isIntegerNumber(userNumber)){
        		alert("用户总次数只能是整数数字！");
        		$("#userNumber").focus();
        		return false;
        	}
    		if(userNumber.length>10){
        		alert("用户总次数长度不能大于10位！");
        		$("#userNumber").focus();
        		return false;
        	}
        	var questionnaireNumber = $("#questionnaireNumber").val();
    		if(isEmpty(questionnaireNumber)){
        		alert("请输入问卷次数！");
        		$("#questionnaireNumber").focus();
        		return false;
        	}
    		if(!isIntegerNumber(questionnaireNumber)){
        		alert("问卷次数只能是整数数字！");
        		$("#questionnaireNumber").focus();
        		return false;
        	}
    		if(questionnaireNumber.length>10){
        		alert("问卷次数长度不能大于10位！");
        		$("#questionnaireNumber").focus();
        		return false;
        	}
        	var thresholdNumber = $("#thresholdNumber").val();
        	if(!isEmpty(thresholdNumber)){
        		if(!isIntegerNumber(thresholdNumber)){
            		alert("通知门限值只能是整数数字！");
            		$("#thresholdNumber").focus();
            		return false;
            	}
        		if(thresholdNumber.length>10){
            		alert("通知门限值长度不能大于10位！");
            		$("#thresholdNumber").focus();
            		return false;
            	}
        		if(Number(thresholdNumber) > Number(questionnaireNumber)){
            		alert("通知门限值不能大于问卷次数！");
            		$("#thresholdNumber").focus();
            		return false;
            	}
        	}
        	var integralRatio = $("#integralRatio").val();
    		if(isEmpty(integralRatio)){
        		alert("请输入积分兑换人民币比率！");
        		$("#integralRatio").focus();
        		return false;
        	}else{
        		if(integralRatio.length>20){
            		alert("积分兑换人民币比率长度不能大于20位！");
            		$("#integralRatio").focus();
            		return false;
            	}
    			var integralRatios = integralRatio.split(":");
    			if(integralRatios.length != 2 || !isFloat2(integralRatios[0]) || !isFloat2(integralRatios[1])){
    				alert("积分兑换人民币比率格式不正确！");
    				$("#integralRatio").focus();
    	    		return false;
    			}
    		}
    	}

    	if(!playNumber || playNumber<=0){
    		if(isEmpty($("#startDate").val())){
        		alert("请选择开始日期！");
        		$("#startDate").focus();
        		return false;
        	}
	    	if(isEmpty($("#endDate").val())){
	    		alert("请选择结束日期！");
	    		$("#endDate").focus();
	    		return false;
	    	}
    	}else{
    		if(isEmpty($("#startDate2").val())){
        		alert("请选择开始日期！");
        		$("#startDate2").focus();
        		return false;
        	}
        }
    	
    	if(!isEmpty($("#desc").val()) && $("#desc").val().length>120){
    		alert("订单描述长度必须在0-120字之间！");
    		$("#desc").focus();
    		return false;
    	}
    	if(validateSpecialCharacterAfter($("#desc").val())){
			alert("订单描述不能包括特殊字符！");
			$("#desc").focus();
			return false;
		}
		
    	if($("#sucai").find("tr").length==0){
    		alert("请为策略绑定素材！");
    		return false;
    	}
    	return true;
    }
    
     //选择素材
    function addResouce(){
    	positionId = document.getElementById("positionId").value;
    	ployId = document.getElementById("ployId").value;
    	var orderCode = document.getElementById("orderCode").value;
    	if(!ployId || ployId==-1){
        	document.getElementById("ployName").style.borderColor="red";
        	document.getElementById("ploy_error").innerHTML = "策略不能为空";
    		return;
    	}
    	var url = "initAreaResource.do?order.ployId="+ployId+"&order.positionId="+positionId+"&omRelTmp.orderCode="+orderCode;
    	var value = window.showModalDialog(url, window, "dialogHeight=580px;dialogWidth=820px;center=1;resizable=0;status=0;");
    	showAreaResource(ployId,'${order.orderCode}',positionId);  
    }
    /**
     * 检查开始时间和结束时间是否符合要求
     * */
    function checkOrderDate(){
    	var start = $("#startDate").val();
    	var end = $("#endDate").val();
    	var ahead = 0;
    	if(deliveryMode==0){
    		ahead =  parseInt(aheadTime)*1000;
    	}
    	
    	var startTime=Date.parse(start.replace(/-/g, "/")+" "+ployStartTime);
        var endTime=Date.parse(end.replace(/-/g, "/")+" "+ployEndTime);
        var now = Date.parse(new Date())+ahead; 
        
        var startDate = Date.parse(start.replace(/-/g,"/"));
        var endDate = Date.parse(end.replace(/-/g,"/"));
        var vStartDate = Date.parse(validMinDate.replace(/-/g,"/"));
        var vEndDate = Date.parse(validMaxDate.replace(/-/g,"/"));
        
       if(endTime<startTime)
       {
           alert("订单结束时间不能小于开始时间！");
           return false;
       }
       /*
       if(startTime<now)
       {
          alert("订单开始时间与当前时间间隔小于"+aheadTime+"秒！");
          return false;
       } */
       if(startDate<vStartDate||endDate>vEndDate){
    	   alert("订单日期超出合同范围，合同日期范围是"+vStart.replace(" 00:00:00","")+"~"+vEnd.replace(" 00:00:00",""));
    	   return false;
       }
       return true;
    }

</script>
</head>

<body class="mainBody" onload='init(${orderOpAheadTime});'>
<form action="saveOrder.do" method="post" id="saveForm">
<input type="hidden" id="selResource" name="order.selResource"  />
<input type="hidden" id="startTime" name="order.startDateStr"  />
<div class="detail">
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">订单添加</td>
	</tr>
	<c:if test="${roleType == 1}">
	<!-- 广告商 -->
	<tr>
		<td width="12%" align="right">订单号：</td>
		<td width="33%"><input type="hidden" id="orderCode" name="order.orderCode" value="${order.orderCode}" /> ${order.orderCode}</td>
		<td width="12%" align="right"><span class="required">*</span>合同名称：</td>
		<td width="33%">
			<input id="contractName" name="order.contractName" class="e_input_add" readonly="readonly" type="text" onclick="showContent('contract',null);"/>
			<input type="hidden" id="contractId" name="order.contractId" />
			<span id="contract_error"></span>
		</td>
	</tr>
	</c:if>
	<c:if test="${roleType == 2}">
	<!-- 运营商 -->
	<tr>
		<td width="12%" align="right">订单号：</td>
		<td colspan="3"><input type="hidden" id="orderCode" name="order.orderCode" value="${order.orderCode}" /> ${order.orderCode}</td>
		<input type="hidden" id="contractId" name="order.contractId" value="0"  />
	</tr>
	</c:if>
	<tr>
		<td align="right"><span class="required">*</span>广告位名称：</td>
		<td>
			<input id="positionName" class="e_input_add" name="order.positionName" readonly="readonly" type="text" onclick="showContent('position',null);"/>
			<input type="hidden" id="positionId" name="order.positionId" />
			<span id="position_error"></span>
		</td>
		<td align="right"><span class="required">*</span>策略名称：</td>
		<td>
			<input id="ployName" name="order.ployName" class="e_input_add" readonly="readonly" type="text" onclick="showContent('ploy',null);" />
			<input type="hidden"  id="ployId" name="order.ployId" />
			<span id="ploy_error"></span>
		</td>
	</tr>
	
	<tr id="questionnaire1" style="display: none">
		<td align="right"><span class="required">*</span>用户总次数：</td>
		<td>
			<input id="userNumber" name="order.userNumber" type="text" />
		</td>
		<td align="right"><span class="required">*</span>问卷总次数：</td>
		<td>
			<input id="questionnaireNumber" name="order.questionnaireNumber" type="text" />
		</td>
	</tr>
	
	<tr id="questionnaire2" style="display: none">
		<td align="right">通知门限值：</td>
		<td>
			<input id="thresholdNumber" name="order.thresholdNumber" type="text" />
		</td>
		<td align="right"><span class="required">*</span>积分兑换人民币比率：</td>
		<td>
			<input id="integralRatio" name="order.integralRatio" type="text" /><span class="required">格式：1:1</span>
		</td>
	</tr>
	
	<tr id="periodDate" style="display: none">
		<td align="right"><span class="required">*</span>开始日期：</td>
		<td><input type="text" readonly="readonly"  class="e_input_time" id="startDate"	 onclick="selectDate();"/></td>
		<td align="right"><span class="required">*</span>结束日期：</td>
		<td><input type="text" name="order.endDateStr" readonly="readonly" class="e_input_time" id="endDate" 
			onclick="selectDate();"/></td>
	</tr>
	
	<tr id="onceDate" style="display: none">
		<td align="right"><span class="required">*</span>开始日期：</td>
		<td><input type="text" readonly="readonly"  class="e_input_time" id="startDate2" onclick="selectDate();"/></td>
		<td align="right">播放次数：</td>
		<td>
			<input type="text" id="viewPlayNumber" disabled="disabled"/>
			<input type="hidden" id="playNumber" name="order.playNumber" />
		</td>
	</tr>
	<tr>
		<td align="right">订单描述：</td>
		<td colspan="3"><textarea id="desc" name="order.description" cols="50" rows="3"></textarea> <span class="required">(长度在0-120字之间)</span></td>
	</tr>
		<tr>
		    <td colspan="3" class="yulan"><span
				style="display: block; width: 500px;">策略绑定素材信息:</span>
			</td>
			<td>
				<input type="button" onclick="addResouce();" class="btn" value="新增绑定" />
			</td>
	   </tr>
	        <table  name="sucai" id="sucai"  width="100%" cellspacing="1" class="searchList">
	        </table>
</table>
<div style="margin-left:50px;">
	<input type="button" onclick="saveOrder();" class="btn" value="确认" /> &nbsp;&nbsp; 
	<input type="button" onclick="javascript:history.back(-1);" class="btn" value="取消" />
</div>
</div>


</form>
</body>
</html>