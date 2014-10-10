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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>
<title></title>
<script type="text/javascript">
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
var saveOrUpdateFlag = parent.saveOrUpdateFlag;
var positionIndexFlag = getParamValue('positionIndexFlag');   	

var alreadyChooseMarketRuleList = '';

var showMarketRule={'marketRuleList':[{'ruleCheckBoxId':'#ruleCheckBoxId0','orderId':'#orderId0','ruleNameId':'#ruleNameId0','startTime':'#startTime0','endTime':'#endTime0','positionName':'#positionName0','areaName':'#areaName0','channelName':'#channelName0','state':'#state0'}
											,{'ruleCheckBoxId':'#ruleCheckBoxId1','orderId':'#orderId1','ruleNameId':'#ruleNameId1','startTime':'#startTime1','endTime':'#endTime1','positionName':'#positionName1','areaName':'#areaName1','channelName':'#channelName1','state':'#state1'}
										    ,{'ruleCheckBoxId':'#ruleCheckBoxId2','orderId':'#orderId2','ruleNameId':'#ruleNameId2','startTime':'#startTime2','endTime':'#endTime2','positionName':'#positionName2','areaName':'#areaName2','channelName':'#channelName2','state':'#state2'}
										    ,{'ruleCheckBoxId':'#ruleCheckBoxId3','orderId':'#orderId3','ruleNameId':'#ruleNameId3','startTime':'#startTime3','endTime':'#endTime3','positionName':'#positionName3','areaName':'#areaName3','channelName':'#channelName3','state':'#state3'}
										    ,{'ruleCheckBoxId':'#ruleCheckBoxId4','orderId':'#orderId4','ruleNameId':'#ruleNameId4','startTime':'#startTime4','endTime':'#endTime4','positionName':'#positionName4','areaName':'#areaName4','channelName':'#channelName4','state':'#state4'}
									 ]								 
};

var showPositionElement = {'positionName':'#positionName','positionTypeName':'#positionTypeName','deliveryMode':'#deliveryMode','isHd':'#isHd','validStartDate':'#validStartDate','validEndDate':'#validEndDate'};

function show(pno, psize) {

   	clear(showMarketRule.marketRuleList);
   
   	
   	
   	var currentDateIndexInit = 'init';
   	//初始化频道信息
   	$(parent.alreadyFillInForm.bindingPosition).each(function(index, item){
		if(positionIndexFlag==item.positionIndexFlag){
			currentDateIndexInit=index;
		}	
	});
	
	if(currentDateIndexInit!='init'){
		var position = parent.alreadyFillInForm.bindingPosition[currentDateIndexInit];
		$(showPositionElement.positionName).html(position.positionName);
    	$(showPositionElement.positionTypeName).html(position.positionTypeName);
		$(showPositionElement.deliveryMode).html(showInfoTransform(position.deliveryMode,'deliveryMode'));
		$(showPositionElement.isHd).html(showInfoTransform(position.isHd,'isHd'));
    	$(showPositionElement.validStartDate).html(position.validStartDateShow);
		$(showPositionElement.validEndDate).html(position.validEndDateShow);
	}
   	
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
}

function clear(elementList){
	//清空广告位相关信息
	$(showPositionElement.positionName).html('');
    $(showPositionElement.positionTypeName).html('');
	$(showPositionElement.deliveryMode).html('');
	$(showPositionElement.isHd).html('');
    $(showPositionElement.validStartDate).html('');
	$(showPositionElement.validEndDate).html('');
	//清空规则展示列表信息
	$(elementList).each(function(index, item){
			modifyElementsDisplay(item.ruleCheckBoxId,inputHidden);
			$(item.orderId).html('');
			$(item.ruleNameId).html('');
			$(item.startTime).html('');
			$(item.endTime).html('');
			$(item.positionName).html('');
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
	//$("#bm").find("tr:even").addClass("sec");  //奇数行的样式
    //$("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
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
	    	
	    	parent.getAlreadyChoosePosition();
	    	
	    	$(alreadyChooseColumn).each(function(index,itemOut){
	    		if(!$.isEmptyObject(itemOut)){
	    			//定位到所选时间的索引位置
					var currentDateIndex = 'init';
					
					$(parent.alreadyFillInForm.bindingPosition).each(function(index, item){
						if(positionIndexFlag==item.positionIndexFlag){
							currentDateIndex=index;
						}	
					});
						
					if(currentDateIndex!='init'){
								
						if((!$.isEmptyObject(saveOrUpdateFlag))&&(saveOrUpdateFlag=='save')){
									
							//操作保存实体对象json
							var marketRule = parent.alreadyFillInForm.bindingPosition[currentDateIndex].marketRules;
							var removeMarketRuleIndex='init';
								
								if((!$.isEmptyObject(marketRule))&&marketRule.length>0){
										$(marketRule).each(function(mkruleIndex, mkItem){
												if(itemOut==mkItem.id){
													removeMarketRuleIndex=mkruleIndex;
												}	
										});
										if(removeMarketRuleIndex!='init'){
												//将数据直接删除
												parent.alreadyFillInForm.bindingPosition[currentDateIndex].chooseMarketRules=parent.alreadyFillInForm.bindingPosition[currentDateIndex].chooseMarketRules.replace(itemOut+',','');
												parent.alreadyFillInForm.bindingPosition[currentDateIndex].marketRules.splice(removeMarketRuleIndex,1);
										}
								}
									
						}else if((!$.isEmptyObject(saveOrUpdateFlag))&&(saveOrUpdateFlag=='update')){
							//标记
							var positionIndexFlagParam = parent.alreadyFillInForm.bindingPosition[currentDateIndex].positionIndexFlag;
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
													parent.alreadyFillInForm.bindingPosition[currentDateIndex].chooseMarketRules=parent.alreadyFillInForm.bindingPosition[currentDateIndex].chooseMarketRules.replace(itemOut+',','');
												}else if((itemOut==mkItem.id)&&(mkItem.dbFlag!=0)){
													//将父级节点置为修改状态
													parent.comparedForm.bindingPosition[comparedFormIndex].flag=3;
													mkItem.flag=2;
													marketRuleIndex1=mkruleIndex;
												}
												
												if(marketRuleIndex1!='init'){
													parent.comparedForm.bindingPosition[comparedFormIndex].marketRules.splice(marketRuleIndex1,1);
													parent.alreadyFillInForm.bindingPosition[currentDateIndex].chooseMarketRules=parent.alreadyFillInForm.bindingPosition[currentDateIndex].chooseMarketRules.replace(itemOut+',','');												
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
												parent.alreadyFillInForm.bindingPosition[currentDateIndex].chooseMarketRules=parent.alreadyFillInForm.bindingPosition[currentDateIndex].chooseMarketRules.replace(itemOut+',','');												
											}
										}
									}
								}
								
								//操作保存实体对象json
								var marketRule = parent.alreadyFillInForm.bindingPosition[currentDateIndex].marketRules;
								var removeMarketRuleIndex='init';
								
								if((!$.isEmptyObject(marketRule))&&marketRule.length>0){
									$(marketRule).each(function(mkruleIndex, mkItem){
										if(itemOut==mkItem.id){
											removeMarketRuleIndex=mkruleIndex;
										}	
									});
										if(removeMarketRuleIndex!='init'){
											//将数据直接删除
											parent.alreadyFillInForm.bindingPosition[currentDateIndex].marketRules.splice(removeMarketRuleIndex,1);
										}
									}
								
								}
					}	
	       		}
			});			 
			 parent.easyDialog.close();
	    	 parent.show();
	    	 alreadyChooseMarketRuleList=[];
			 parent.alreadyChooseMarketRuleList4View=[];
	    }
	   
     });
});
</script>
</head>

<body class="mainBody">
<div class="searchContent" >
<table cellspacing="1" class="searchList">
    <tr class="title">
        <td colspan="6">广告位信息</td>
    </tr>
    <tr>
        <td width="12%" align="right">广告位名称：</td>
        <td width="21%" id="positionName" name="positionName">
                 天天影院
        </td>
        <td width="12%" align="right">广告位类型：</td>
        <td width="21%" id="positionTypeName" name="positionTypeName">
            P_TESTTESTCODE
        </td>
        <td width="12%" align="right">投放方式：</td>
        <td width="22%" id="deliveryMode" name="deliveryMode">
            天天影院业务
        </td>
    </tr>
    <tr>
       	<td align="right">是否高清：</td>
        <td id="isHd" name="isHd">
            视频
        </td>
        <td align="right">投放开始日期：</td>
        <td id="validStartDate" name="validStartDate">
            2013-03-02
        </td>
        <td  align="right">投放结束日期：</td>
        <td id="validEndDate" name="validEndDate">
            2020-04-03
        </td>
    </tr>
  </table>


  <!--<table cellspacing="1" class="searchList">
        <tr class="title">
            <td>已绑定营销规则</td>
        </tr>
        <tr>
            <td class="searchCriteria">
             <span>规则名称：</span>
             <input type="text" name="textfield" id="textfield" />
             <span>区域：</span>
            <select>
	            <option value="">请选择</option>
	            <option value="">深圳</option>
	            <option value="">北京</option>
	            <option value="">郑州</option>
        	</select>
            <span>状态：</span>
            <select>
	            <option value="">请选择</option>
	            <option value="">正常</option>
	            <option value="">失效</option>
            </select>
            <input type="button" value="查询" onclick="javascript:query();" class="btn"/></td>
    </tr>
    </table>  -->
    <table cellspacing="1" class="searchList" id="bm">
      <tr class="title">
        <td width="38">
        	选项
        </td>
        <td>规则名称</td>
        <td>开始时段</td>
        <td>结束时段</td>
      </tr>
      <tr>
	        <td>
	        	<input type="checkbox" id="ruleCheckBoxId0" name="ruleCheckBox" value="0"/>
	        </td>
	        <td id="ruleNameId0">营销规则一</td>
	        <td id="startTime0">9:00</td>
	        <td id="endTime0">12:00</td>
      </tr>
      <tr>
	        <td>
	        	<input type="checkbox" id="ruleCheckBoxId1" name="ruleCheckBox" value="0"/>
	        </td>
	        <td id="ruleNameId1">营销规则一</td>
	        <td id="startTime1">9:00</td>
	        <td id="endTime1">12:00</td>
      </tr>
      <tr>
	        <td>
	        	<input type="checkbox" id="ruleCheckBoxId2" name="ruleCheckBox" value="0"/>
	        </td>
	        <td id="ruleNameId2">营销规则一</td>
	        <td id="startTime2">9:00</td>
	        <td id="endTime2">12:00</td>
      </tr>
      <tr>
	        <td>
	        	<input type="checkbox" id="ruleCheckBoxId3" name="ruleCheckBox" value="0"/>
	        </td>
	        <td id="ruleNameId3">营销规则一</td>
	        <td id="startTime3">9:00</td>
	        <td id="endTime3">12:00</td>
      </tr>
      <tr>
	        <td>
	        	<input type="checkbox" id="ruleCheckBoxId4" name="ruleCheckBox" value="0"/>
	        </td>
	        <td id="ruleNameId4">营销规则一</td>
	        <td id="startTime4">9:00</td>
	        <td id="endTime4">12:00</td>
      </tr>
      <tr>
        <td height="26px" colspan="23" style="text-align: right;">
			<div id="pageOperationDiv" name="pageOperationDiv">
									
			</div>
		</td>
      </tr>
      <tr>
			<td height="34" colspan="23" style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
							
			</td>
	  </tr>
	  <tr>
			<td class="formBottom" colspan="99" style="text-align: right;">
				<input id="removeMarketingButton4View" name="removeMarketingButton4View" type="button" title="确定删除" class="btn" value="确认删除"/>
			</td>
	  </tr>
    </table>
    
</div>
<div id="system-dialog" title="友情提示">
	  <p>
	    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
	    <span id="content"></span>
	  </p>
</div>
</body>
</html>