<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <input id="projetPath" type="hidden" value="<%=path%>"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
    <title></title>
	<script type='text/javascript' src="<%=path %>/js/new/avit.js"></script>
	<script type='text/javascript' src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
	
    <link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
	<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
	<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
	<link rel="stylesheet" href="<%=path %>/css/jquery/jquery-ui-timepicker-addon.css" type="text/css" />
	
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.widget.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.tabs.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.accordion.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-addon.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker-zh-CN.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-zh-CN.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
	<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
	<script type="text/javascript" src="<%=path%>/js/contract/new/add.js"></script>
	<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>
	<script type="text/javascript" src="<%=path%>/js/util/tools.js"></script>
<script>
var saveOrUpdateFlag = '${saveOrUpdateFlag}';
var contractAdObject = '${contractAd}';
		
if(''==saveOrUpdateFlag){
	saveOrUpdateFlag='save';
}
$(function(){
		
		$('#returnButton').click(function(){
			history.go(-1);
		});
		
		if('save'==saveOrUpdateFlag){
			$('#modifyContractButton').remove();
		}else{
			$('#addContractButton').remove();
		}
		alreadyFillInForm.id='${contract.id}';
		alreadyFillInFormCopy.id='${contract.id}';
		
		var status = '${contract.status}';		
		
		var bindingPosition = '${contract.bindingPosition}';
		//alert("绑定广告位:"bindingPosition);
		if((!$.isEmptyObject(bindingPosition))&&(''!=bindingPosition)){
			bindingPosition="[";
		     <c:choose>
				<c:when test="${!empty contract.bindingPosition}">
					<c:forEach var="position" items="${contract.bindingPosition}" varStatus="positionIndex">
						bindingPosition+="{'id':'${position.id}','validStartDate':'"+dateStringTranformDateObject('${position.validStartDate}')+"','validStartDateShow':'"+'<fmt:formatDate value="${position.validStartDate}" dateStyle="medium"/>'+"','validEndDateShow':'"+"<fmt:formatDate value='${position.validStartDate}' dateStyle='medium'/>"+"','validEndDate':'"+dateStringTranformDateObject('${position.validEndDate}')+"','dbFlag':0,'flag':0,'tabIdList':${position.tabIdList},'marketRules':[";
							<c:choose>
								<c:when test="${!empty position.marketRules}">
									<c:forEach var="marketRule" items="${position.marketRules}" varStatus="marketRuleIndex">
										//dbFlag:0 代表记录在数据库中存在
										bindingPosition+="{'id':'${marketRule.id}','dbFlag':0,'flag':0,'tabId':'${marketRule.tabId}'}";
										<c:choose>
											<c:when test="${marketRuleIndex.last}">
											   
											</c:when>
											<c:otherwise>
												bindingPosition+=',';
											</c:otherwise>
										</c:choose>			
									</c:forEach>
								</c:when>	
							</c:choose>
							<c:choose>
								<c:when test="${positionIndex.last}">
									bindingPosition+="]}";					
								</c:when>
								<c:otherwise>
									bindingPosition+="]},";		
								</c:otherwise>
							</c:choose>					
					</c:forEach>
				</c:when>			
			</c:choose>
			bindingPosition+="]";
			//alert("jason1:"+bindingPosition);
			bindingPosition=eval("("+bindingPosition+")");
			//alert("jason2:"+bindingPosition);
			
			comparedForm.bindingPosition=bindingPosition;
		}
		
		var positionList = '${positionList}';
		//alert("广告位列表:"+positionList);
		if((!$.isEmptyObject(positionList))&&(''!=positionList)){
			positionList=eval("("+positionList+")");
			//1、根据从库中查询出合同相关信息初始化至alreadyFillInForm中
			alreadyFillInForm=deepCopy(positionList);
			if((!$.isEmptyObject(alreadyFillInForm))){
				
				alreadyFillInFormCopy=deepCopy(alreadyFillInForm);
				
				//1-1、生成标记值，并将标记值初始化入各自的属性值中
				var positionList = alreadyFillInForm.bindingPosition;
				var positionTimestampFlag = '';
				if(!$.isEmptyObject(positionList)){
					$(positionList).each(function(index, item){
						positionTimestampFlag=new Date().getTime()+'_'+item.id;
						item.positionIndexFlag=positionTimestampFlag;
						if(!$.isEmptyObject(comparedForm.bindingPosition)){
							$(comparedForm.bindingPosition).each(function(indexInner, itemInner){
								//一个合同中同一个广告位的开始和和结束时间一定不相同
								if((itemInner.id==item.id)&&(itemInner.validStartDate==item.validStartDate)&&(itemInner.validEndDate==item.validEndDate)){
									itemInner.positionIndexFlag=positionTimestampFlag;
									//item.positionIndexFlag=positionTimestampFlag;
								}
							});
						}
					});
				}
			}
			show();
		}
		
		var descValue = '${contract.contractDesc}';
		if((!$.isEmptyObject(descValue))&&(''!=descValue)){
			$('#desc').val(descValue);
		}
});
</script>
<input id="projetPath" type="hidden" value="<%=path%>"/>
<style>
	.easyDialog_wrapper{ width:1000px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
</head>

<body class="mainBody">

<div class="detail">
    <div style="position: relative">
    	<form action="addPosition.do?method=save"  id="addPositionform" name="addPositionform" method="post">
        <table cellspacing="1" class="content" align="left">
            <tr class="title">
                <td colspan="4">合同基本信息</td>
            </tr>
            <tr>
                <td width="12%" align="right">
                	<span class="required">*</span>
                	合同名称：
                </td>
                <td width="33%">
                	 <input id="id" name="contract.id" value="${contract.id}"  type="hidden" />
                     <input id="contractName" name="contract.contractName" value="${contract.contractName}"  type="text" />
                     <span id="operatorFullName_error"></span>
                </td>
                <td width="12%" align="right">
                	<span class="required">*</span>
                	合同编号：
                </td>
                <td width="33%">
                    <input id="contractNumber" name="contract.contractNumber" value="${contract.contractNumber}" type="text"/>
                    <span id="operatorName_error"></span>
                </td>
            </tr>
            <tr>
                <td width="12%" align="right">
                	<span class="required">*</span>
                	合同号：
                </td>
                <td width="33%">
                   <input id="contractCode" name="contract.contractCode" value="${contract.contractCode}" type="text"/>
                   <span id="operatorName_error"></span>
                </td>
                <td width="12%" align="right">
                	<span class="required">*</span>
                	广告商：
                </td>
                <td width="33%">
                	<input id="customerId" name="contract.customerId" value="${contract.customerId}" type="hidden"  readonly="readonly"/>
					<input id="customerName" name="contract.customerName" value="${contract.customerName}" type="text" readonly="readonly"/>							
                    <input id="bindingCustomer" name="bindingCustomer" type="button" class="btn" value="选择">
                    <span id="operatorCode_error"></span>
                </td>
            </tr>
            <tr>
                <td width="12%" align="right">
                	<span class="required">*</span>
                	送审单位：
                </td>
                <td width="33%">
                    <input id="submitUnits" name="contract.submitUnits" value="${contract.submitUnits}" type="text"/>
                    <span id="licenseNum_error"></span>
                </td>
                <td width="12%" align="right">
                	<span class="required">*</span>
                	广告位：
                </td>
                <td width="33%">
                    <input id="bindingPosition" name="bindingPosition" type="button" class="btn" value="绑定广告位"/>
                </td>
            </tr>
            <tr>
                <td width="12%" align="right">
                	<span class="required">*</span>
                	合同金额：
                </td>
                <td width="33%">
                    <input id="financialInformation" name="financialInformation" value="${contract.financialInformation}" type="text"/>
                    <span id="legalPerson_error"></span>
                </td>
                <td width="12%" align="right">
                	<span class="required">*</span>
                		广告审批文号：
                </td>
                <td width="33%">
                    <input id="approvalCode" name="contract.approvalCode" value="${contract.approvalCode}" type="text"/>
                </td>
            </tr>
            <tr>
                <td width="12%" align="right">
                	<span class="required">*</span>
                	合同开始日期：
                </td>
                <td width="33%">
                    <input id="effectiveStartDate"  name="contract.effectiveStartDate" value='<fmt:formatDate value="${contract.effectiveStartDate}" dateStyle="medium"/>'  class="input_style2" type="text" style="width:125px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'effectiveEndDate\')}',onpicked:function(dp){alreadyFillInForm.effectiveStartDateShow=dp.cal.getDateStr();alreadyFillInForm.effectiveEndDateShow=dp.cal.getDateStr();alreadyFillInForm.effectiveStartDate=dateStringTranformDateObject(dp.cal.getDateStr());alreadyFillInForm.effectiveEndDate=dateStringTranformDateObject(dp.cal.getDateStr());}})"/>
                 		<img onclick="showDate('effectiveStartDate')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
                    	<span id="effDate_error">
                    </span>
                </td>
                <td width="12%" align="right">
                	<span class="required">*</span>
                	合同截止日期：
                </td>
                <td width="33%">
                    <input id="effectiveEndDate" name="contract.effectiveEndDate" value='<fmt:formatDate value="${contract.effectiveEndDate}" dateStyle="medium"/>' class="input_style2" type="text" style="width:125px;"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'effectiveStartDate\')}',onpicked:function(dp){alreadyFillInForm.effectiveEndDateShow=dp.cal.getDateStr();alreadyFillInForm.effectiveEndDate=dateStringTranformDateObject(dp.cal.getDateStr());}})"/>
                    <img onclick="showDate('effectiveEndDate')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
                    <span id="expDate_error"></span>
           	</tr>
			<tr>
                <td width="13%" align="right">
                	<span class="required">*</span>
                	合同执行开始日期：
                </td>
                <td width="33%">
                    <input id="approvalStartDate" name="contract.approvalStartDate" value='<fmt:formatDate value="${contract.approvalStartDate}" dateStyle="medium"/>' class="input_style2" type="text" style="width:125px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'approvalEndDate\')}',onpicked:function(dp){alreadyFillInForm.approvalStartDateShow=dp.cal.getDateStr();alreadyFillInForm.approvalEndDateShow=dp.cal.getDateStr();alreadyFillInForm.approvalStartDate=dateStringTranformDateObject(dp.cal.getDateStr());alreadyFillInForm.approvalEndDate=dateStringTranformDateObject(dp.cal.getDateStr());}})"/>
                    <img onclick="showDate('approvalStartDate')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
                    <span id="auditEffDate_error"></span>
                </td>
                <td width="13%" align="right">
                	<span class="required">*</span>
                	合同执行结束日期：
                </td>
                <td width="33%">
                    <input id="approvalEndDate" name="contract.approvalEndDate" value='<fmt:formatDate value="${contract.approvalEndDate}" dateStyle="medium"/>' class="input_style2" type="text" style="width:125px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'approvalStartDate\')}',onpicked:function(dp){alreadyFillInForm.approvalEndDateShow=dp.cal.getDateStr();alreadyFillInForm.approvalEndDate=dateStringTranformDateObject(dp.cal.getDateStr());}})"/>
                    <img onclick="showDate('approvalEndDate')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
                    <span id="auditExpDate_error"></span>
             </tr>
            
            <tr>
                <td width="12%" align="right">描 述：</td>
                <td width="88%" colspan="3">
                    <textarea style="" id="desc" name="contract.contractDesc" value="${contract.contractDesc}" rows="5"></textarea>
                    <span id="remark_error"></span>
                </td>
            </tr>
        </table>
		<table cellspacing="1" class="searchList">
			<tr class="title">
                <td colspan="8">已绑定广告位</td>
            </tr>
		      <tr id="bindingPositionArea" name="bindingPositionArea" class="title">
		        <td>序号</td>
		        <td>广告位名称</td>
		        <td>广告位类型</td>
		        <td>投放方式</td>
		        <td>是否高清</td>
		        <td>投放开始日期</td>
		        <td>投放结束日期</td>
				<td>操作</td>
		      </tr>
		      <tr>
		         <td id="positionOrder" name="positionOrder" >1</td>
		         <td id="positionName" name="positionName">1</td>
		         <td id="positionTypeName" name="positionTypeName">再续意难忘第一集</td>
		         <td id="deliveryMode" name="deliveryMode">R_AVIT_TEST1</td>
		         <td id="isHd" name="isHd">AVIT深圳CP</td>
		         <td id="validStartTd" name="validStartTd">
		        	<input id="validStart" name="validStart" class="input_style2" type="text" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		         </td>
		         <td id="validEndTd" name="validEndTd">
		        	<input id="validEnd" name="validEnd" class="input_style2" type="text" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		         </td>
				 <td>
					<a id="remove" name="remove" href="#">删除</a>
					&nbsp;&nbsp;
					<a id="binding" name="binding" href="#">绑定</a>
					&nbsp;&nbsp;
					<a id="editBinding" name="editBinding" href="#">编辑绑定</a>
				  </td>
		       </tr>
    </table>
    </form>
    </div>
    <div align="center" class="action">
        <input id="addContractButton" name="addContractButton" type="button" class="btn" value="确 定"/>&nbsp;&nbsp;&nbsp;&nbsp;
        <input id="modifyContractButton" name="modifyContractButton" type="button" class="btn"  value="确 定"/>&nbsp;&nbsp;&nbsp;&nbsp;
        <input id="returnButton" name="returnButton" type="button" class="btn"  value="返  回"/>
    </div>
</div>
<div id="system-dialog" title="友情提示">
	  <p>
	    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
	    <span id="content"></span>
	  </p>
</div>
</body>
</html>