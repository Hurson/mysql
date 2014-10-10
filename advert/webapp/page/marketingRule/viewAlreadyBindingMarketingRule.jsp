<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>
<title>广告系统</title>
<script>
//每页显示总记录数
var pageSize = 5;
// 当前页
var currentPage = 0;
// 开始显示的行
var startRow = 0;
// 结束显示的行
var endRow = 0;
//隐藏
var inputHidden = 0;
//显示
var inputShow = 1;
//显示已绑定的策略id
var newMarketRuleIds='';
var saveOrUpdateFlag = getParamValue('saveOrUpdateFlag');
var chooseMarketRulesElement = getParamValue('chooseMarketRulesElement');
var positionIndexFlag = getParamValue('positionIndexFlag');
var currentIndexParam =  getParamValue('currentIndex');  	
var startRowParam =  getParamValue('startRow');
var currentPageParam =  getParamValue('currentPage');     	
var endRowParam =  getParamValue('endRow');

var alreadyChooseMarketRuleList = '';

var showMarketRule={'marketRuleList':[{'ruleCheckBoxId':'#ruleCheckBoxId0','orderId':'#orderId0','ruleNameId':'#ruleNameId0','startTime':'#startTime0','endTime':'#endTime0','positionName':'#positionName0','areaName':'#areaName0','channelName':'#channelName0','state':'#state0'}
											,{'ruleCheckBoxId':'#ruleCheckBoxId1','orderId':'#orderId1','ruleNameId':'#ruleNameId1','startTime':'#startTime1','endTime':'#endTime1','positionName':'#positionName1','areaName':'#areaName1','channelName':'#channelName1','state':'#state1'}
										    ,{'ruleCheckBoxId':'#ruleCheckBoxId2','orderId':'#orderId2','ruleNameId':'#ruleNameId2','startTime':'#startTime2','endTime':'#endTime2','positionName':'#positionName2','areaName':'#areaName2','channelName':'#channelName2','state':'#state2'}
										    ,{'ruleCheckBoxId':'#ruleCheckBoxId3','orderId':'#orderId3','ruleNameId':'#ruleNameId3','startTime':'#startTime3','endTime':'#endTime3','positionName':'#positionName3','areaName':'#areaName3','channelName':'#channelName3','state':'#state3'}
										    ,{'ruleCheckBoxId':'#ruleCheckBoxId4','orderId':'#orderId4','ruleNameId':'#ruleNameId4','startTime':'#startTime4','endTime':'#endTime4','positionName':'#positionName4','areaName':'#areaName4','channelName':'#channelName4','state':'#state4'}
										]
};

function show(pno, psize) {

   	clear(showMarketRule.marketRuleList);
   	
	var num = alreadyChooseMarketRuleList.length;

	var totalPage = 0;// 总页数

	pageSize = psize;// 每页显示行数

	if ((num - 1) / pageSize >= parseInt((num - 1) / pageSize)) {
		totalPage = parseInt((num - 1) / pageSize) + 1;
	} else {
		totalPage = parseInt((num - 1) / pageSize);
	}

	currentPage = pno;// 当前页数
	startRow = (currentPage - 1) * pageSize;

	endRow = currentPage * pageSize;
	endRow = (endRow > num) ? num : endRow;
	
	
	$(alreadyChooseMarketRuleList).each(function(index,itemInner){
	   
	  if(index >= startRow && index < endRow){
		 
		  	var buttunIndex = 0;
		
			if(index>=pageSize){
				buttunIndex=index%pageSize;	
			}else{
				buttunIndex=index;
			}
		 
		 	init(buttunIndex,itemInner);
	 }
	 
	});

	
	var tempStr = " 共【" + num + "】条记录 分【" + totalPage + "】页 当前第【" + currentPage
			+ "】页 ";

	if (currentPage > 1) {
		tempStr += " <a href=\"#\" onClick=\"show(" + (currentPage - 1) + ","
				+ psize + ")\">上一页</a> "
	} else {
		tempStr += " 上一页 ";
	}

	if (currentPage < totalPage) {
		tempStr += " <a href=\"#\" onClick=\"show(" + (currentPage + 1) + ","
				+ psize + ")\">下一页</a> ";
	} else {
		tempStr += " 下一页 ";
	}

	if (currentPage > 1) {
		tempStr += " <a href=\"#\" onClick=\"show(" + (1) + "," + psize
				+ ")\">首页</a> ";
	} else {
		tempStr += " 首页 ";
	}
	if (currentPage < totalPage) {
		tempStr += " <a href=\"#\" onClick=\"show(" + (totalPage) + ","
				+ psize + ")\">尾页</a> ";
	} else {
		tempStr += " 尾页 ";
	}
	document.getElementById("pageOperationDiv").innerHTML = tempStr;
}

function init(index,param){
	var buttunIndex = 0;
	
	if(index>=pageSize){
		buttunIndex=index%pageSize;	
	}else{
		buttunIndex=index;
	}
	modifyElementsDisplay(showMarketRule.marketRuleList[buttunIndex].ruleCheckBoxId,inputShow);
	$(showMarketRule.marketRuleList[buttunIndex].ruleCheckBoxId).val(param.id);
	$(showMarketRule.marketRuleList[buttunIndex].orderId).html(pageSize*(currentPage-1)+index+1);
	$(showMarketRule.marketRuleList[buttunIndex].ruleNameId).html(param.ruleName);
	$(showMarketRule.marketRuleList[buttunIndex].startTime).html(param.startTime);
	$(showMarketRule.marketRuleList[buttunIndex].endTime).html(param.endTime);
	$(showMarketRule.marketRuleList[buttunIndex].positionName).html(param.positionName);
	$(showMarketRule.marketRuleList[buttunIndex].areaName).html(param.areaName);
	$(showMarketRule.marketRuleList[buttunIndex].channelName).html(param.channelName);
	$(showMarketRule.marketRuleList[buttunIndex].state).html(param.state);
}

function clear(elementList){
	$(elementList).each(function(index, item){
			modifyElementsDisplay(item.ruleCheckBoxId,inputHidden);
			$(item.orderId).html('');
			$(item.ruleNameId).html('');
			$(item.startTime).html('');
			$(item.endTime).html('');
			$(item.positionName).html('');
			$(item.areaName).html('');
			$(item.channelName).html('');
			$(item.state).html('');
			
	});
}


function showAlreadyChooseMarketRules(){
		//从parent中的全局变量中获取信息
		alreadyChooseMarketRuleList=parent.alreadyChooseMarketRuleList4View;
		if(!$.isEmptyObject(alreadyChooseMarketRuleList)&&alreadyChooseMarketRuleList.length>0){
			show(1,pageSize);
		}else{
			clear(showMarketRule.marketRuleList);
		}
}		 

function modifyElementsDisplay(elements,isDisplay){
	switch(isDisplay){
		case 0:
			$(elements).hide();
			break;
		case 1:
			$(elements).show();
			break;
		default:
			break;
	}
}

$(function(){
	showAlreadyChooseMarketRules();
	$("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    $("#system-dialog").hide();
    $("#removeMarketingButton4View").click(function(){
     
     	var alreadyChoose = "";
	    $("input[name='ruleCheckBox']").each(function(){
	        if($(this).is(':checked')){
	            alreadyChoose += $(this).val() + ",";
	        }
	    });
	    
	    if($.isEmptyObject(alreadyChoose)){
	    	var message = '请先选择待删除【策略】';
	    	
	    	if((!$.isEmptyObject(alreadyChooseMarketRuleList))&&alreadyChooseMarketRuleList.length>0){
	    		message = '请先选择待删除【策略】';
	    	}else{
	    		message = '暂无记录将不进行删除操作！';
	    	}
	    	
			$("#content").html(message);
			$( "#system-dialog" ).dialog({
		      	modal: true
		    });
	    }else{
	    	var alreadyChooseColumn = alreadyChoose.split(',');
	    	
	    	chooseMarketRulesElement=unescape(chooseMarketRulesElement);
	    	
	    	parent.getAlreadyChoosePosition();
	    	
	    	
	    	$(alreadyChooseColumn).each(function(index,itemOut){
	    		if(!$.isEmptyObject(itemOut)){
	    				
	    			
	    			//定位到所选时间的索引位置
					var currentDateIndex = 'init';
					
					$(parent.showPositionCount.positionButtonList).each(function(index, item){
						if(chooseMarketRulesElement==item.editorMarketRuleButtom){
							currentDateIndex=index;
						}	
					});
						
					if(currentDateIndex!='init'){
						for (var choosePositionindex = startRowParam; choosePositionindex < endRowParam; choosePositionindex++) {
							if(choosePositionindex==(currentDateIndex+((currentPageParam - 1) * pageSize))){
								if((!$.isEmptyObject(saveOrUpdateFlag))&&(saveOrUpdateFlag=='save')){
									
									//操作保存实体对象json
									var marketRule = parent.alreadyFillInForm.bindingPosition[choosePositionindex].marketRules;
									var removeMarketRuleIndex='init';
								
									if((!$.isEmptyObject(marketRule))&&marketRule.length>0){
										$(marketRule).each(function(mkruleIndex, mkItem){
											if(itemOut==mkItem.id){
												removeMarketRuleIndex=mkruleIndex;
											}	
										});
										if(removeMarketRuleIndex!='init'){
											//将数据直接删除
											parent.alreadyFillInForm.bindingPosition[choosePositionindex].chooseMarketRules=parent.alreadyFillInForm.bindingPosition[choosePositionindex].chooseMarketRules.replace(itemOut+',','');
											parent.alreadyFillInForm.bindingPosition[choosePositionindex].marketRules.splice(removeMarketRuleIndex,1);
										}
									}
									
								}else if((!$.isEmptyObject(saveOrUpdateFlag))&&(saveOrUpdateFlag=='update')){
									//标记
									var positionIndexFlagParam = parent.alreadyFillInForm.bindingPosition[choosePositionindex].positionIndexFlag;
									if(!$.isEmptyObject(parent.comparedForm.bindingPosition)&&parent.comparedForm.bindingPosition.length>0){
									var comparedFormIndex='init';
									//找到对应记录
									for (var bindPositionindex = 0; bindPositionindex < parent.comparedForm.bindingPosition.length; bindPositionindex++) {
										if(parent.comparedForm.bindingPosition[bindPositionindex].positionIndexFlag==positionIndexFlagParam){
											comparedFormIndex=bindPositionindex;
										}
									}
									//操作操作情况记录json
									if(comparedFormIndex!='init'){
										//分两种情况，一种数据库存在，页面中要进行删除操作(修改状态为2)
										if(parent.comparedForm.bindingPosition[comparedFormIndex].dbFlag==0){
											
											$(parent.comparedForm.bindingPosition[comparedFormIndex].marketRules).each(function(mkruleIndex, mkItem){
												
												var marketRuleIndex1='init';
												
												if((itemOut==mkItem.id)&&(mkItem.dbFlag==0)){
													//将父级节点置为修改状态
													parent.comparedForm.bindingPosition[comparedFormIndex].flag=3;
													mkItem.flag=2;
													parent.alreadyFillInForm.bindingPosition[choosePositionindex].chooseMarketRules=parent.alreadyFillInForm.bindingPosition[choosePositionindex].chooseMarketRules.replace(itemOut+',','');
												}else if((itemOut==mkItem.id)&&(mkItem.dbFlag!=0)){
													//将父级节点置为修改状态
													parent.comparedForm.bindingPosition[comparedFormIndex].flag=3;
													mkItem.flag=2;
													marketRuleIndex1=mkruleIndex;
												}
												
												if(marketRuleIndex1!='init'){
													parent.comparedForm.bindingPosition[comparedFormIndex].marketRules.splice(marketRuleIndex1,1);
													parent.alreadyFillInForm.bindingPosition[choosePositionindex].chooseMarketRules=parent.alreadyFillInForm.bindingPosition[choosePositionindex].chooseMarketRules.replace(itemOut+',','');												
												}
											});
											//从下标为choosePositionindex的元素开始,删除一个元素
											//parent.alreadyChoosePositionP.splice(choosePositionindex,1);
										}else{
											//一种数据库中不存在，页面中进行删除操作(可直接删除)
											var marketRuleIndex='init';
											parent.comparedForm.bindingPosition[comparedFormIndex].flag=3;
											$(parent.comparedForm.bindingPosition[comparedFormIndex].marketRules).each(function(mkruleIndex, mkItem){
												if(itemOut==mkItem.id){
													mkItem.flag=2;
													marketRuleIndex=mkruleIndex;
												}	
											});
											
											if(marketRuleIndex!='init'){
												parent.comparedForm.bindingPosition[comparedFormIndex].marketRules.splice(marketRuleIndex,1);
												parent.alreadyFillInForm.bindingPosition[choosePositionindex].chooseMarketRules=parent.alreadyFillInForm.bindingPosition[choosePositionindex].chooseMarketRules.replace(itemOut+',','');												
											}
										}
									}
								}
								
								//操作保存实体对象json
								var marketRule = parent.alreadyFillInForm.bindingPosition[choosePositionindex].marketRules;
								var removeMarketRuleIndex='init';
								
								if((!$.isEmptyObject(marketRule))&&marketRule.length>0){
									$(marketRule).each(function(mkruleIndex, mkItem){
										if(itemOut==mkItem.id){
											removeMarketRuleIndex=mkruleIndex;
										}	
									});
										if(removeMarketRuleIndex!='init'){
											//将数据直接删除
											parent.alreadyFillInForm.bindingPosition[choosePositionindex].marketRules.splice(removeMarketRuleIndex,1);
										}
									}
								
								}
							}
						}
					}
	    			
	    			
	       			
	       		}
			});			 
			 parent.easyDialog.close();
	    	 parent.showPositionList4Page(1,6);
	    	 alreadyChooseMarketRuleList=[];
			 parent.alreadyChooseMarketRuleList4View=[];
	    }
	   
     });
});
</script>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6; }
</style>
</head>

<body onload="">
	<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
	
		<tr>
		<td style="padding:1px;height: 100%;">
			<div>  
				<table cellpadding="0" cellspacing="1" width="100%" class="taba_right_list" id="bm">
					<tr>
						<td colspan="23" style="padding-left: 8px; background: url(<%=path %>/images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>展示·规则列表</span></td>
					</tr>
					<tr>
						<td height="26px" align="center">选项</td>
						<td height="26px" align="center">序号</td>
						<td align="center">规则名称</td>
						<td align="center">开始时间</td>
						<td align="center">结束时间</td>
						<td align="center">广告位</td>
						<td align="center">地区</td>
						<td align="center">频道</td>
						<td align="center">状态</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" id="ruleCheckBoxId0" name="ruleCheckBox" value="0"/>
						</td>
						<td align="center" height="26" id="orderId0">0</td>
						<td id="ruleNameId0">0</td>
						<td id="startTime0">0</td>
						<td id="endTime0">0</td>
						<td id="positionName0">0</td>
						<td id="areaName0">
							0
						</td>
						<td id="channelName0">
							0
						</td>
						<td id="state0">
							0
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" id="ruleCheckBoxId1" name="ruleCheckBox" value="1"/>
						</td>
						<td align="center" height="26" id="orderId1">1</td>
						<td id="ruleNameId1">1</td>
						<td id="startTime1">1</td>
						<td id="endTime1">1</td>
						<td id="positionName1">1</td>
						<td id="areaName1">
							1
						</td>
						<td id="channelName1">
							1
						</td>
						<td id="state1">
							1
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" id="ruleCheckBoxId2" name="ruleCheckBox" value="2"/>
						</td>
						<td align="center" height="26" id="orderId2">2</td>
						<td id="ruleNameId2">2</td>
						<td id="startTime2">2</td>
						<td id="endTime2">2</td>
						<td id="positionName2">2</td>
						<td id="areaName2">
							2
						</td>
						<td id="channelName2">
							2
						</td>
						<td id="state2">
							2
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" id="ruleCheckBoxId3" name="ruleCheckBox" value="3"/>
						</td>
						<td align="center" height="26" id="orderId3">3</td>
						<td id="ruleNameId3">3</td>
						<td id="startTime3">3</td>
						<td id="endTime3">3</td>
						<td id="positionName3">3</td>
						<td id="areaName3">
							3
						</td>
						<td id="channelName3">
							3
						</td>
						<td id="state3">
							3
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="checkbox" id="ruleCheckBoxId4" name="ruleCheckBox" value="4"/>
						</td>
						<td align="center" height="26" id="orderId4">4</td>
						<td id="ruleNameId4">4</td>
						<td id="startTime4">4</td>
						<td id="endTime4">4</td>
						<td id="positionName4">4</td>
						<td id="areaName4">
							4
						</td>
						<td id="channelName4">
							4
						</td>
						<td id="state4">
							4
						</td>
					</tr>
					<tr>
						<td height="26px" colspan="23" style="text-align: right;">
							<div id="pageOperationDiv" name="pageOperationDiv">
									
							</div>
						</td>
					</tr>
					<tr>
						<td height="34" colspan="23"
							style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
							
						</td>
					</tr>
					<tr>
						<td class="formBottom" colspan="99" style="text-align: right;">
							 <input id="removeMarketingButton4View" name="removeMarketingButton4View" type="button" title="确定删除" class="b_delete" value="" onfocus=blur()/>
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