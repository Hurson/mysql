<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"	href="<%=path %>/css/new/main.css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript">
    window.onload = function() {
    	setNewPeriodStart($("#contractId").val());
    };
    
    
    var json = new Array();
    var submitFlag = true;

	 
	 function setNewPeriodStart(contractId){
	 	$.ajax({
          		
                type:"post",
                url:"<%=path%>/page/accounts/getContractsDeadLine.do?",//从哪获取Json
                data:{"contractId":contractId},//Ajax传递的参数
                dataType:"json",
                success:callbackJson
            });
	 }
	 
	 function callbackJson(mess){
	 	json = eval(mess);
     	$("#payVallidityPeriodBegin").val(json.deadline);
     }
     
     function setPeriodEnd(){
     	var num = $("option:selected", "#paySort").index();
     	if(num == 1){
     		$("#payVallidityPeriodEnd").val(json.nextPeriod);
     	} else if (num == 2){
     		$("#payVallidityPeriodEnd").val(json.nextThreeMonth);
     	}
     	
     }
     
     function checkDate(){
     	var start = $("#payVallidityPeriodBegin").val();
     	var end = $("#payVallidityPeriodEnd").val();
     	if(end < start && start != "" && end != ""){
     		alert("结束时间应该大于开始时间！");
     		submitFlag = false;
     	} else {
     		submitFlag = true;
     	}
     }
     
     function saveData(){
     	if(!checkForm()){
     		return;
     	}
     	$("#updateForm").submit();
     	parent.rewriteMoney($("#moneyAmount").val());
     	parent.easyDialog.close();
     }
     
     function checkForm(){
     	var accountsCode = $("#accountsCode").val();
     	if(isEmpty(accountsCode)){
     		alert("请填写合同台账单号");
     		return false;
     	};
     	var paySort = $("#paySort").val();
     	if(isEmpty(paySort)){
     		alert("请选择付款方式");
     		return false;
     	};
     	var payDay = $("#payDay").val();
     	if(isEmpty(payDay)){
     		alert("请填写付款日期");
     		return false;
     	};
     	var moneyAmount = $("#moneyAmount").val();
     	if(isEmpty(moneyAmount)){
     		alert("请填写付款金额");
     		return false;
     	}
     	var payVallidityPeriodBegin = $("#payVallidityPeriodBegin").val();
     	if(isEmpty(payVallidityPeriodBegin)){
     		alert("请填写付款有效期开始时间");
     		return false;
     	};
     	var payVallidityPeriodEnd = $("#payVallidityPeriodEnd").val();
     	if(isEmpty(payVallidityPeriodEnd)){
     		alert("请填写付款有效期结束时间");
     		return false;
     	};
     	return true;
     }
     
     function checkAmountMoney(){
     	 var obj = document.getElementById("moneyAmount");
     	 obj.value = obj.value.replace(/[^\d.]/g,"");
     	 obj.value = obj.value.replace(/^\./g,"");
     	 obj.value = obj.value.replace(/\.{2,}/g,".");
     	 obj.value = obj.value.replace(/\.\d{5,}/g,".0");
     	 obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
     }
     
     function checkFloat(num){
     	if ( !/^\d+\.{0,1}\d{0,4}$/g.test(num) ) 
     	{  
     		alert("最多保留4位小数");
     		document.getElementById("moneyAmount").value = "";
     	}
     	
     }
    
     
</script>
<style>
	.easyDialog_wrapper{ width:900px;height:600px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
<title>合同付款</title>
</head>

<body class="mainBody">
<form action="updateAccounts.do" method="post" id="updateForm">
<input type="hidden" id="accountsId" name="contractAccounts.accountsId" value="${contractAccounts.accountsId}"  />
<input type="hidden"  id="contractId" name="contractAccounts.contractId" value="${contractId}"/>
<div class="detail" >
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">合同台账信息</td>
	</tr>
	<tr>
		<td width="15%" align="right"><span class="required">*</span>台账单号：</td>
		<td width="35%" >
			<input type="text" maxlength="20" id="accountsCode" name="contractAccounts.accountsCode" value="${contractAccounts.accountsCode}" />
		</td>
		<td width="15%" align="right"><span class="required">*</span>付款方式：</td>
		<td width="35%" >
			<select id="paySort"  name="contractAccounts.paySort"   class="e_select" onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'"  onchange="setPeriodEnd();">
				<option  value="-1">请选择...</option>
				<option  value="1">按月付款</option>
				<option  value="2">按季度付款</option>
			</select>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"><span class="required">*</span>付款日期：</td>
		<td width="35%" >
			<input id="payDay" name="contractAccounts.payDay" readonly="readonly" type="text" style="width: 70%"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${contractAccounts.payDay}"/>
		</td>
		<td width="15%" align="right"><span class="required">*</span>付款金额：（万元）</td>
		<td width="35%" >
			<input type="text" maxlength="10" id="moneyAmount" name="contractAccounts.moneyAmount"  value="${contractAccounts.moneyAmount}"  style='width:70%' onkeyup="checkAmountMoney();" onblur="checkFloat(this.value);"/>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"><span class="required">*</span>付款有效期开始时间：</td>
		<td width="35%" >
			<input id="payVallidityPeriodBegin" readonly="readonly" name="contractAccounts.payVallidityPeriodBegin"  type="text" style="width: 70%"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${contractAccounts.payVallidityPeriodBegin}" onchange="checkDate();"/>
		</td>
		<td width="15%" align="right"><span class="required">*</span>付款有效期结束时间：</td>
		<td width="35%" >
			<input id="payVallidityPeriodEnd" readonly="readonly" name="contractAccounts.payVallidityPeriodEnd"  type="text" style="width: 70%"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${contractAccounts.payVallidityPeriodEnd}"  onchange="checkDate();"/>
		</td>
	</tr>
	
	
	<tr>
		<td align="center" colspan="4">
			<input type="button" class="btn" value="保存" onclick="saveData();"/>
		</td>
	</tr>
	
</table>
</div>
</form>
</body>
</html>