<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/new/main.css" />
		<link href="<%=path%>/css/new/common/dhtmlx/tree/css/dhtmlxtree.css"
			rel="stylesheet" type="text/css" media="all" />
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxcommon.js"
			type="text/javascript">

<script src="<%=path%>/js/new/avit.js">
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/ui/jquery.ui.core.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/ui/jquery-ui-1.10.0.custom.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/ui/jquery.ui.widget.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/ui/jquery.ui.tabs.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/ui/jquery.ui.accordion.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/validate/jquery.validate.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/validate/jquery.validate.messages_cn.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-addon.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker-zh-CN.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-zh-CN.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/util/util.js">
</script>
		<script type="text/javascript"
			src="<%=path%>/js/jquery/upload/js/swfobject.js">
</script>
		<script type="text/javascript"
			src="<%=path%>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js">
</script>
		<script type="text/javascript"
			src="<%=path%>/js/easydialog/easydialog.min.js">
</script>
		<script type="text/javascript"
			src="<%=path%>/js/contract/approvalContract.js">
</script>
		<script type="text/javascript" src="<%=path%>/js/util/util.js">
</script>
		<script type="text/javascript" src="<%=path%>/js/util/tools.js">
</script>
		<script>
var contractAdObject = '${contractAd}';

function goBack() {
	window.location.href = "approvalListContract.do";
}

function confirmPass() {
		if (confirm("确认该合同审核通过吗？"))
	{
	accessPath('');
		}
	else
		{
			return;
		}
}

function confirmNoPass() {
		if (confirm("确认该合同审核不通过吗？"))
	{
	accessPath('');
		}
	else
		{
			return;
		}
}

$(function(){
	
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
		
		//根据传入的参数判断表单中的值是否可以进行修改
		var formEnable = '${formEnable}';
		if($.isEmptyObject(formEnable)){
						
		}else{
			if('false'==formEnable){
				enableInputLable('contract');
			}
		}
		
		var bindingPosition = '${contract.bindingPosition}';
		if(!$.isEmptyObject(bindingPosition)){
			bindingPosition="[";
		     <c:choose>
				<c:when test="${!empty contract.bindingPosition}">
					<c:forEach var="position" items="${contract.bindingPosition}" varStatus="positionIndex">
						bindingPosition+="{'id':'${position.id}','validStartDate':'"+dateStringTranformDateObject('${position.validStartDate}')+"','validEndDate':'"+dateStringTranformDateObject('${position.validEndDate}')+"','dbFlag':0,'flag':0,'tabIdList':${position.tabIdList},'marketRules':[";
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
			bindingPosition=eval("("+bindingPosition+")");
			comparedForm.bindingPosition=bindingPosition;
		}
		
		var positionList = '${positionList}';
		if(!$.isEmptyObject(positionList)){
			positionList=eval("("+positionList+")");
			//1、根据从库中查询出合同相关信息初始化至alreadyFillInForm中
			alreadyFillInForm=deepCopy(positionList);
			if((!$.isEmptyObject(alreadyFillInForm))){
				showPositionList4Page(1,6);
				
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
								if((itemInner.id==item.id)&&(itemInner.validStartDate==item.validStartDate)&&(itemInner.validEndDate==item.validEndDate)){
									itemInner.positionIndexFlag=positionTimestampFlag;
								}
							});
						}
					});
				}
			}
		}
		
		var descValue = '${contract.contractDesc}';
		if(!$.isEmptyObject(descValue)){
			$('#desc').val(descValue);
		}
});
</script>
		<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}
</style>
	</head>
	<input id="projetPath" type="hidden" value="<%=path%>" />
	<body class="mainBody">
		<div class="detail">
			<div style="position: relative">
				<form action="addPosition.do?method=save" id="addPositionform"
					name="addPositionform" method="post">
					<table cellspacing="1" class="content" align="left">
						<tr class="title">
							<td colspan="4">
								合同基本信息
							</td>
						</tr>
						<tr>
							<td width="12%" align="right">
								<span class="required">*</span> 合同名称：
							</td>
							<td width="33%">
								<input id="id" name="contract.id" value="${contract.id}"
									type="hidden" />
									${contract.contractName}								
								<span id="operatorFullName_error"></span>
							</td>
							<td width="12%" align="right">
								<span class="required">*</span> 合同编号：
							</td>
							<td width="33%">
							${contract.contractNumber}							
								<span id="operatorName_error"></span>
							</td>
						</tr>
						<tr>
							<td width="12%" align="right">
								<span class="required">*</span> 合同号：
							</td>
							<td width="33%">
							${contract.contractCode}								
								<span id="operatorName_error"></span>
							</td>
							<td width="12%" align="right">
								<span class="required"></span> 广告商：
							</td>
							<td width="33%">
								<input id="customerId" name="contract.customerId"
									value="${contract.customerId}" type="hidden"
									readonly="readonly" />
									${contract.customerName}							
									<span id="operatorCode_error"></span>
							</td>
						</tr>
						<tr>
							<td width="12%" align="right">
								<span class="required">*</span> 送审单位：
							</td>
							<td width="33%">
							${contract.submitUnits}								
								<span id="licenseNum_error"></span>
							</td>
							<td width="12%" align="right">
								<span class="required">*</span> 广告位：
							</td>
							<td width="33%">							
							</td>
						</tr>
						<tr>
							<td width="12%" align="right">
								<span class="required">*</span> 合同金额：
							</td>
							<td width="33%">
							${contract.financialInformation}								
								<span id="legalPerson_error"></span>
							</td>
							<td width="12%" align="right">
								<span class="required">*</span> 广告审批文号：
							</td>
							<td width="33%">
							${contract.approvalCode}							
							</td>
						</tr>
						<tr>
							<td width="12%" align="right">
								<span class="required"></span> 合同开始日期：
							</td>
							<td width="33%">
							<fmt:formatDate value="${contract.effectiveStartDate}" dateStyle="medium"/>						
							
								<span id="effDate_error"> </span>
							</td>
							<td width="12%" align="right">
								<span class="required"></span> 合同截止日期：
							</td>
							<td width="33%">
							<fmt:formatDate value="${contract.effectiveEndDate}" dateStyle="medium"/>
						</tr>
						<tr>
							<td width="13%" align="right">
								<span class="required"></span> 审批文号生效日期：
							</td>
							<td width="33%">
								<fmt:formatDate value="${contract.approvalStartDate}" dateStyle="medium"/>								
								<span id="auditEffDate_error"></span>
							</td>
							<td width="13%" align="right">
								<span class="required"></span> 审批文号失效日期：
							</td>
							<td width="33%">
								<fmt:formatDate value="${contract.approvalStartDate}" dateStyle="medium"/>							
								<span id="auditExpDate_error"></span>
						</tr>
						<tr>
							<td width="12%" align="right">
								描 述：
							</td>
							<td>
								<textarea style="" id="desc" name="contract.contractDesc"
									value="${contract.contractDesc}" rows="5" readonly></textarea>
								<span id="remark_error"></span>
							</td>
							<td width="12%" align="right">
								审核意见：
							</td>
							<td>
								<textarea style="" id="examinationOpinions" name="contract.examinationOpinions"
									value="${contract.examinationOpinions}" rows="5"></textarea>
								<span id="remark_error"></span>
							</td>
						</tr>
						<tr>
												<td class="td_label">审核操作：</td>
												<td class="td_input" colspan="3">	   
													<input type="button" class="btn" value="审核通过" onclick="confirmPass();" />
													<input type="button" class="btn" value="审核不通过" onclick="confirmNoPass();" />
													<input type="button" class="btn" value="返回" onclick="goBack();" />
												</td>
												
											</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>