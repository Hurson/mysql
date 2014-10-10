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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />

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
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.messages_cn.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-addon.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker-zh-CN.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-zh-CN.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/updateContract.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/tools.js"></script>
<title>广告系统</title>
<style>
	.easyDialog_wrapper{ width:1000px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
<script>
var contractAdObject = '${contractAd}';
		
$(function(){
	alreadyFillInForm.id='${contract.id}';
	alreadyFillInFormCopy.id='${contract.id}';
	
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
		
		var examinationOpinions = '${contract.examinationOpinions}';
		var status = '${contract.status}';
		
		if(!$.isEmptyObject(examinationOpinions)&&((status==2)||(status==1))){
			// 1、显示对应区域内容
			//$('#examinationOpinionsLab').show();
			//$('#examinationOpinionsInp').show();
			$('#examinationOpinions').val(examinationOpinions);
		}else if(!$.isEmptyObject(examinationOpinions)&&((status==0))){
			
			//$('#examinationOpinionsLab').hide();
			//$('#examinationOpinionsInp').hide();
			$('#examinationOpinions').val('待审核');
		}else if(!$.isEmptyObject(examinationOpinions)&&((status==3))){
			
			//$('#examinationOpinionsLab').hide();
			//$('#examinationOpinionsInp').hide();
			$('#examinationOpinions').val('已下线');
		}
		
		var updateFlag = '${updateFlag}';
		
		if(!$.isEmptyObject(updateFlag)){
			$('#addContractButton').remove();
		}else{
			$('#modifyContractButton').remove();
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
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6;}
</style>
</head>
<input id="projetPath" type="hidden" value="<%=path%>"/>
<body onload="">
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" border="0" width="100%"
					class="tab_right">
					<tr>
						<td>工作台</td>
						<td>客户</td>
						<td>日程行动</td>
						<td>销售机会</td>
						<td>订单管理</td>
						<td>客服中心</td>
						<td>财务中心</td>
						<td>营销中心</td>
						<td>人力资源中心</td>
						<td>数据统计</td>
						<td>信息门户管理</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
					<form action="addPosition.do?method=save"  id="addPositionform" name="addPositionform" method="get">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							
								<tr>
									<td style="padding:1px;">
										<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
											<tr>
												<td class="formTitle" colspan="99">
													<span>·添加合同</span>
												</td>
											</tr>
											<tr>
												<td class="td_label">合同编号：</td>
												<td class="td_input">
													<input id="contractNumber" name="contract.contractNumber" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${contract.contractNumber}" />
													<input id="id" name="contract.id" type="hidden" value="${contract.id}"/>
												</td>
												<td class="td_label">合同代码：</td>
												<td class="td_input">
													<input id="contractCode" name="contract.contractCode" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${contract.contractCode}"/>	
												</td>
											</tr>
											<tr>
												<td class="td_label">广告商ID：</td>
												<td class="td_input">
													<input id="customerId" name="contract.customerId" value="${contract.customerId}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" readonly="readonly"/>
													<input id="customerName" name="contract.customerName" value="${contract.customerName}" type="hidden" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" readonly="readonly"/>
												</td>
												<td class="td_label">合同名称：</td>
												<td class="td_input">
													<input id="contractName" name="contract.contractName" value="${contract.contractName}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>
												</td>
											</tr>			
											<tr>
												<td class="td_label">送审单位：</td>
												<td class="td_input">
													<input id="submitUnits" name="contract.submitUnits" value="${contract.submitUnits}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>
												</td>
												<td class="td_label">合同金额：</td>
												<td class="td_input">
													<input id="financialInformation" name="contract.financialInformation" value="${contract.financialInformation}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/>	
												</td>
											</tr>
											<tr>
												<td class="td_label">广告审批文号：</td>
												<td class="td_input">
													<input id="approvalCode" name="contract.approvalCode" value="${contract.approvalCode}" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
												</td>
												<td class="td_label">其他内容：</td>
												<td class="td_input">
													<input id="otherContent" name="contract.otherContent" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${contract.otherContent}"  />
												</td>
											</tr>
											<tr>
												<td class="td_label">合同有效期的开始时间：</td>
												<td class="td_input">
													<input id="effectiveStartDate" name="contract.effectiveStartDate" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${contract.effectiveStartDate}" />
												</td>
												<td class="td_label">合同有效期的结束时间：</td>
												<td class="td_input">
													<input id="effectiveEndDate" name="contract.effectiveEndDate" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${contract.effectiveEndDate}" />
												</td>
											</tr>
											<tr>
												<td class="td_label">审批文号有效期的开始日期：</td>
												<td class="td_input">
													<input id="approvalStartDate" name="contract.approvalStartDate" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${contract.approvalStartDate}"  />
												</td>
												<td class="td_label">审批文号有效期的截止日期：</td>
												<td class="td_input">
													<input id="approvalEndDate" name="contract.approvalEndDate" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="${contract.approvalEndDate}" />
												</td>
											</tr>
											<tr id="bindingTd">
												<td class="td_label">绑定广告位：</td>
												<td class="td_input">
													<input id="bindingPosition" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="点击绑定广告位"/>
												</td>
												<td class="td_label">绑定广告商:</td>
												<td class="td_input">
													<input id="bindingCustomer" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="点击绑定广告商"/>
												</td>
											</tr>
											<tr>
												<td class="td_label">备注描述：</td>
												<td class="td_input">	   
													<textarea id="desc" name="contract.contractDesc" value="${contract.contractDesc}" style="overflow:hidden;padding:0;width:323px;height:125px;border:1px solid gray;">		
													</textarea>
												</td>
												<td class="td_label" id="examinationOpinionsLab">审核意见:</td>
												<td class="td_input" id="examinationOpinionsInp">	   
													<textarea id="examinationOpinions" name="contract.examinationOpinions" value="${contract.examinationOpinions}" style="overflow:hidden;padding:0;width:323px;height:125px;border:1px solid gray;" readonly="readonly">		
													</textarea>
												</td>
											</tr>
										</table>
									</td>
								</tr>
						</table>
					</form>
				</td>
		</tr>
		<tr>
			<td style="padding:1px  0px  0px 1px;vertical-align:top;WIDTH:100%; HEIGHT:290px">
				<div style="OVERFLOW-Y:auto; OVERFLOW-X:auto; WIDTH:100%; HEIGHT:60%">  
					<table cellpadding="0" cellspacing="1" width="100%" class="binding_taba_right_list" id="bm">
						<tr>
							<td colspan="23" style="padding-left:8px; background: url(<%=path %>/images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>·已绑定广告位列表</span></td>
						</tr>
						<tr>
							<td height="26px" align="center">序号</td>
							<td>广告位类型编码</td>
							<td>广告位特征值</td>
							<td>广告位名称</td>
							<td>投放开始日期</td>
							<td>投放结束日期</td>
							<td>删除</td>
							<td>绑定</td>
							<td>编辑已绑策略</td>
							<td>已绑策略主键展示</td>
						</tr>
						<tr id="position0T">
							<td height="26px" align="center" id="position0Order" name="position0Order">1</td>
							<td id="position0TypeId" name="position0TypeId">1</td>
							<td id="eigen0Value" name="eigen0Value">1</td>
							<td id="position0Name" name="position0Name">1</td>
							<td id="validStart0" name="validStart0"><input id="validStart0I" name="validStart0I" type="text" title="投放开始日期" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="" onfocus=blur()/></td>
							<td id="validEnd0" name="validEnd0"><input id="validEnd0I" name="validEnd0I" type="text" title="投放结束日期" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="" onfocus=blur()/></td>
							<td>
								<input id="removeButtom0" name="removeButtom0" type="button" title="删除" class="button_delete" onfocus=blur()/>
							</td>
							<td>
								<input id="position0B" name="position0B" type="button" title="绑定策略" class="b_bangding" onfocus=blur()/>
							</td>
							<td>
								<input id="editorMarketRuleButtom0" name="editorMarketRuleButtom0" type="button" title="编辑已绑策略" class="b_bangding" onfocus=blur()/>
							</td>
							<td id="alreadyChooseRule0" name="alreadyChooseRule0">
								123
							</td>
						</tr>
						<tr id="position1T">
							<td height="26px" align="center" id="position1Order" name="position1Order">2</td>
							<td id="position1TypeId" name="position1TypeId">2</td>
							<td id="eigen1Value" name="eigen1Value">2</td>
							<td id="position1Name" name="position1Name">2</td>
							<td id="validStart1" name="validStart1"><input id="validStart1I" name="validStart1I" type="text" title="投放开始日期" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" onfocus=blur()/></td>
							<td id="validEnd1" name="validEnd1"><input id="validEnd1I" name="validEnd1I" type="text" title="投放结束日期" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" onfocus=blur()/></td>
							<td>
								<input id="removeButtom1" name="removeButtom1" type="button" title="删除" class="button_delete" onfocus=blur()/>
							</td>
							<td>
								<input id="position1B" name="position1B" type="button" title="绑定策略" class="b_bangding" onfocus=blur()/>
							</td>
							<td>
								<input id="editorMarketRuleButtom1" name="editorMarketRuleButtom1" type="button" title="编辑已绑策略" class="b_bangding" onfocus=blur()/>
							</td>
							<td id="alreadyChooseRule1" name="alreadyChooseRule1">
								123
							</td>
						</tr>
						<tr id="position2T">
							<td height="26px" align="center" id="position2Order" name="position2Order">3</td>
							<td id="position2TypeId" name="position2TypeId">3</td>
							<td id="eigen2Value" name="eigen2Value">3</td>
							<td id="position2Name" name="position2Name">3</td>
							<td id="validStart2" name="validStart2"><input id="validStart2I" name="validStart2I" type="text" title="投放开始日期" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="" onfocus=blur() onclick=""/></td>
							<td id="validEnd2" name="validEnd2"><input id="validEnd2I" name="validEnd2I" type="text" title="投放结束日期" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="" onfocus=blur() onclick=""/></td>
							<td>
								<input id="removeButtom2" name="removeButtom2" type="button" title="删除" class="button_delete" value="" onfocus=blur() onclick=""/>
							</td>
							<td>
								<input id="position2B" name="position2B" type="button" title="绑定策略" class="b_bangding" value="" onfocus=blur() onclick=""/>
							</td>
							
							<td>
								<input id="editorMarketRuleButtom2" name="editorMarketRuleButtom2" type="button" title="编辑已绑策略" class="b_bangding" onfocus=blur()/>
							</td>
							<td id="alreadyChooseRule2" name="alreadyChooseRule2">
								123
							</td>
						</tr>
						<tr id="position3T">
							<td height="26px" align="center" id="position3Order" name="position3Order">4</td>
							<td id="position3TypeId" name="position3TypeId">4</td>
							<td id="eigen3Value" name="eigen3Value">4</td>
							<td id="position3Name" name="position3Name">4</td>
							<td id="validStart3" name="validStart3"><input id="validStart3I" name="validStart3I" type="text" title="投放开始日期" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="" onfocus=blur() onclick=""/></td>
							<td id="validEnd3" name="validEnd3"><input id="validEnd3I" name="validEnd3I" type="text" title="投放结束日期" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="" onfocus=blur() onclick=""/></td>
							<td>
								<input id="removeButtom3" name="removeButtom3" type="button" title="删除" class="button_delete" value="" onfocus=blur() onclick=""/>
							</td>
							<td>
								<input id="position3B" name="position3B" type="button" title="绑定策略" class="b_bangding" value="" onfocus=blur() onclick=""/>
							</td>
							
							<td>
								<input id="editorMarketRuleButtom3" name="editorMarketRuleButtom3" type="button" title="编辑已绑策略" class="b_bangding" onfocus=blur()/>
							</td>
							<td id="alreadyChooseRule3" name="alreadyChooseRule3">
								123
							</td>
						</tr>
						<tr id="position4T">
							<td height="26px" align="center" id="position4Order" name="position4Order">5</td>
							<td id="position4TypeId" name="position4TypeId">5</td>
							<td id="eigen4Value" name="eigen4Value">5</td>
							<td id="position4Name" name="position4Name">5</td>
							<td id="validStart4" name="validStart4"><input id="validStart4I" name="validStart4I" type="text" title="投放开始日期" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="" onfocus=blur() onclick=""/></td>
							<td id="validEnd4" name="validEnd4"><input id="validEnd4I" name="validEnd4I" type="text" title="投放结束日期" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="" onfocus=blur() onclick=""/></td>
							<td>
								<input id="removeButtom4" name="removeButtom4" type="button" title="删除" class="button_delete" value="" onfocus=blur() onclick=""/>
							</td>
							<td>
								<input id="position4B" name="position4B" type="button" title="绑定策略" class="b_bangding" value="" onfocus=blur() onclick=""/>
							</td>
							
							<td>
								<input id="editorMarketRuleButtom4" name="editorMarketRuleButtom4" type="button" title="编辑已绑策略" class="b_bangding" onfocus=blur()/>
							</td>
							<td id="alreadyChooseRule4" name="alreadyChooseRule4">
								123
							</td>
						</tr>
						<tr id="position5T">
							<td height="26px" align="center" id="position5Order" name="position5Order">6</td>
							<td id="position5TypeId" name="position5TypeId">6</td>
							<td id="eigen5Value" name="eigen5Value">6</td>
							<td id="position5Name" name="position5Name">6</td>
							<td id="validStart5" name="validStart5"><input id="validStart5I" name="validStart5I" type="text" title="投放开始日期" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="" onfocus=blur() onclick=""/></td>
							<td id="validEnd5" name="validEnd5"><input id="validEnd5I" name="validEnd5I" type="text" title="投放结束日期" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="" onfocus=blur() onclick=""/></td>
							<td>
								<input id="removeButtom5" name="removeButtom5" type="button" title="删除" class="button_delete" value="" onfocus=blur() onclick=""/>
							</td>
							<td>
								<input id="position5B" name="position5B" type="button" title="绑定策略" class="b_bangding" value="" onfocus=blur() onclick=""/>
							</td>
							
							<td>
								<input id="editorMarketRuleButtom5" name="editorMarketRuleButtom5" type="button" title="编辑已绑策略" class="b_bangding" onfocus=blur()/>
							</td>
							<td id="alreadyChooseRule5" name="alreadyChooseRule5">
								123
							</td>
						</tr>
						<tr>
							<td height="26px" colspan="23" style="text-align: right;">
								<div id="pageOperationDiv" name="pageOperationDiv">
									
								</div>
							</td>
						</tr>
						<tr>
							<td class="formBottom" colspan="99" style="text-align: right;">
							   <input id="addContractButton" name="addContractButton" type="button" title="添加合同" class="b_add" value="" onfocus=blur()/>
							   <input id="modifyContractButton" name="modifyContractButton" type="button" title="修改合同" class="b_edit" value="" onfocus=blur()/>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
</table>
<div id="system-dialog" title="友情提示">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
    <span id="content"></span>
  </p>
</div>
</body>
</html>