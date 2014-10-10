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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <input id="projetPath" type="hidden" value="<%=path%>" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/css/new/main.css">
    <title></title>
	<script type="text/javascript" src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
	<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/util/tools.js"></script>
	<script>
	
$(function(){
	var marketingRuleName = '${marketingRuleName}';
	
	if(!$.isEmptyObject(marketingRuleName)){
		$('#marketingRuleName').val(marketingRuleName);
	}
	
	var positionId = '${positionId}';
	if(!$.isEmptyObject(positionId)){
		$('#positionId').val(positionId);
	}
	
    $("#bm").find("tr:even").addClass("sec");  //奇数行的样式
    //$("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    $("#system-dialog").hide();
    
    var saveOrUpdateFlag = parent.saveOrUpdateFlag;
    var alreadyChooseMarketRule = '';

    //此值可从父页面记录中获取到
    //var chooseMarketRulesElement = '';
    var positionIndexFlag = '${positionIndexFlag}';
    
     var marketingRuleList="{'list':[";
     <c:choose>
		<c:when test="${!empty rules}">
			<c:forEach var="rule" items="${rules}" varStatus="status">
				marketingRuleList+="{'id':'${rule.ruleId}','ruleName':'${rule.ruleName}','startTime':'${rule.startTime}','endTime':'${rule.endTime}','positionName':'${rule.positionName}','areaName':'${rule.areaName}','channelName':'${rule.channelName}','state':${rule.state},'chooseState':'false','modify':'false'}";
				<c:choose>
					<c:when test="${status.last}">
					</c:when>
					<c:otherwise>
						marketingRuleList+=',';
					</c:otherwise>
				</c:choose>						
			</c:forEach>
		</c:when>			
	</c:choose>
	marketingRuleList+="]}";
	
    var marketingRuleListObject=eval("("+marketingRuleList+")"); 
    
    
    		parent.getAlreadyChoosePosition();
	    	
	    	var positionIndex='init';
	    	$(parent.alreadyFillInForm.bindingPosition).each(function(index,item){
	    			if(positionIndexFlag==item.positionIndexFlag){
	    				positionIndex = index;
	    			}
	    	});
	    	
	    	
	    	if(positionIndex!='init'){
	    		var existChooseMarketRules = parent.alreadyFillInForm.bindingPosition[positionIndex].chooseMarketRules;
	    		if(!$.isEmptyObject(existChooseMarketRules)){
     				alreadyChooseMarketRule=existChooseMarketRules.split(',');
			     	$("input[type='checkbox']").each(function(index,elements){
				        if(!$.isEmptyObject(alreadyChooseMarketRule)){
				        	
				        	//已存在的记录不允许添加
				        	$(alreadyChooseMarketRule).each(function(indexInner,elementsInner){
				        		if(elementsInner==elements.value){
				        			elements.disabled="disabled";
				        		}
				        	});
				        }
				        
				       
				    });
				}
				
				$("input[type='checkbox']").each(function(index,elements){
					 //数据库中已存在的记录不允许被再次添加
					 if((!$.isEmptyObject(parent.comparedForm))&&(!$.isEmptyObject(parent.comparedForm.bindingPosition))&&(!$.isEmptyObject(parent.comparedForm.bindingPosition[positionIndex]))&&(!$.isEmptyObject(parent.comparedForm.bindingPosition[positionIndex].marketRules))){
					        		$(parent.comparedForm.bindingPosition[positionIndex].marketRules).each(function(indexM,itemM){
					        			//itemM.dbFlag==0为数据库存在数据 itemM.flag==2 为已删除数据
						        		if((itemM.id==elements.value)&&(itemM.dbFlag==0)&&(itemM.flag==2)){
						        			elements.disabled="disabled";
						        		}
					        		});
					 }
				 });
	 		}
	 		
     $("#queryButton").click(function(){
     	
     	$("#queryForm").submit();
     });
     
      $("#chooseMarketingButton").click(function(){
     
     	var alreadyChoose = "";
     	//父级节点id，供回写参数时使用
     	
     	var alreadyChooseMarketRule = '';
     	
     	
     	var chooseMarketRulesElementValue = '';
     	
	    $("input[name='ruleCheckBox']").each(function(){
	        if($(this).is(':checked')){
	            alreadyChoose += $(this).val() + ",";
	        }
	    });
	    
	    if($.isEmptyObject(alreadyChoose)){
	    	var message = '请先选择已有【规则】后再进行添加';
			$("#content").html(message);
			$( "#system-dialog" ).dialog({
		      	modal: true
		    });
	    }else{
	    	//根据所获取的ID值，定位到对应的对象数组中
	    	var alreadyChooseMarketRulesInner = [];
	    	
	    	var alreadyChooseArray = alreadyChoose.split(',');
	    	
	    	//处理待传入后台的对象数组
	    	$(alreadyChooseArray).each(function(indexInner,item){
	    		$(marketingRuleListObject.list).each(function(indexListMarketingIndex,itemInner){
	    			if(item==itemInner.id){
	    				var jsonStr = deepCopy(itemInner);
	       				alreadyChooseMarketRulesInner.push(jsonStr);
	    			}
	    		});
	    	});
	    	
	    	parent.getAlreadyChoosePosition();
	    	
	    	var positionIndex='init';
	    	$(parent.alreadyFillInForm.bindingPosition).each(function(index,item){
	    			if(positionIndexFlag==item.positionIndexFlag){
	    				positionIndex = index;
	    			}
	    	});
	    	
	    	if(positionIndex!='init'){
	    	
	    			
	    			var existChooseMarketRules = parent.alreadyFillInForm.bindingPosition[positionIndex].chooseMarketRules;
	    			var existChooseMarketRulesArray = existChooseMarketRules.split(',');
					var existArray = Array.existsSameValues(existChooseMarketRulesArray,alreadyChooseArray);
					if(existArray.length<=0){
						
						var mergeArray = existChooseMarketRules+alreadyChooseArray;
						parent.alreadyFillInForm.bindingPosition[positionIndex].chooseMarketRules=mergeArray;
						
						//遍历当前已选集合
						$(alreadyChooseMarketRulesInner).each(function(indexMarketingRules,itemMarketingRules){
							
							var existFlag = 'init';
							//新增
							if((!$.isEmptyObject(saveOrUpdateFlag))&&(saveOrUpdateFlag=='save')){
								//判断记录在原始集合中是否已经存在
								$(parent.alreadyFillInForm.bindingPosition[positionIndex].marketRules).each(function(parentIndexMarketingRules,parentItemMarketingRules){
									if(parentItemMarketingRules.id==itemMarketingRules.id){
										existFlag=true;
									}
								});
							
								if(existFlag=='init'){
									parent.alreadyFillInForm.bindingPosition[positionIndex].marketRules.push(alreadyChooseMarketRulesInner[indexMarketingRules]);
								}
								
							}
							//更新
							else if((!$.isEmptyObject(saveOrUpdateFlag))&&(saveOrUpdateFlag='update')){
								//判断此规则在原始集合中是否已经存在
								$(parent.comparedForm.bindingPosition).each(function(parentIndexMarketingRules,parentItemMarketingRules){
										if(parentItemMarketingRules.positionIndexFlag==positionIndexFlag){
											existFlag=parentIndexMarketingRules;
										}
								});
							
							if(existFlag!='init'){
								
									var flag = false;
									//代表该记录已经存在，将状态置为一
									$(parent.comparedForm.bindingPosition[existFlag].marketRules).each(function(index,item){
										if(item.id==itemMarketingRules.id){
											flag = true;
											//设置flag的状态值为新增
											parent.comparedForm.bindingPosition[existFlag].marketRules[index].flag=1;
											//自变后，父级也会跟着发生变化
											parent.comparedForm.bindingPosition[existFlag].flag=3;
										}
									});
									
									if(!flag){
											parent.alreadyFillInForm.bindingPosition[positionIndex].marketRules.push(alreadyChooseMarketRulesInner[indexMarketingRules]);
											//将新新添加记录加入comparedForm中 1 新增 修改父一级节点状态
						 					var bindingMarketRuleStr = "{'id':'"+itemMarketingRules.id+"','dbFlag':1,'flag':1}";
						 					bindingMarketRuleStr=eval("("+bindingMarketRuleStr+")");
						 					//将其修改为更新状态
						 					parent.comparedForm.bindingPosition[existFlag].flag=3;
						 					parent.comparedForm.bindingPosition[existFlag].marketRules.push(bindingMarketRuleStr);
										
									}
								}
								//parent.easyDialog.close();
							}
							else{
								//代表该记录不存在
								parent.alreadyFillInForm.bindingPosition[index].marketRules.push(alreadyChooseMarketRulesInner[indexMarketingRules]);
								//将新新添加记录加入comparedForm中 1 新增 修改父一级节点状态
			 					var bindingMarketRuleStr = "{'id':'"+itemMarketingRules.id+"','dbFlag':1,'flag':1}";
			 					bindingMarketRuleStr=eval("("+bindingMarketRuleStr+")");
			 					
			 					//定位 区分 如果flag=0为默认数据，则为更新，否则如果flag=1为新增数据
			 					var bindingpositonCompareForm = parent.comparedForm.bindingPosition;
			 					$(bindingpositonCompareForm).each(function(bindingpositonCompareFormIndex,bindingpositonCompareFormElement){
									if(bindingpositonCompareFormElement.positionIndexFlag==positionIndexFlag){
										
										var matchFlag = 'init';
										$(bindingpositonCompareFormElement.marketRules).each(function(bindingMarketRuleCompareFormIndex,bindingMarketRuleCompareFormElement){
											//判断已选集合中数据在原有数据中是否存在
											if(bindingMarketRuleCompareFormElement.id==itemMarketingRules.id){
												matchFlag=bindingMarketRuleCompareFormIndex;
											}	
										});
										
										if(matchFlag!='init'){
											parent.comparedForm.bindingPosition[matchFlag].push(bindingMarketRuleStr);
										}
										bindingpositonCompareFormElement.marketRules.push(bindingMarketRuleStr);
									}
								});	
							}
						});
						parent.easyDialog.close();
					}else{
						var message = '以下记录已存在'+existArray;
						$("#content").html(message);
						$( "#system-dialog" ).dialog({
					      	modal: true
					    });
					}
	    		}
			
	    }
     });
     
     $("#cleanButton").click(function(){
    		$('#marketingRuleName').val('');
    		generateAccess(1,'','${positionId}');
     });	
});


function generateAccess(currentPage,marketingRuleName,positionId){
		
		var path = 'listMarketingRule.do?pageNo='+currentPage+'&contractBindingFlag=1&positionIndexFlag=${positionIndexFlag}&saveOrUpdateFlag=${saveOrUpdateFlag}&'+'positionId='+positionId;
		if((!$.isEmptyObject(marketingRuleName))){
			$('#marketingRuleName').val(marketingRuleName);
		}
		if((!$.isEmptyObject(positionId))){
			$('#positionId').val(positionId);
		}
		submitForm('#queryForm',path);
}
</script>
</head>

<body class="mainBody">

<div class="search">
        <div class="path">
        	<img src="<%=path%>/images/new/filder.gif" width="15" height="13"/>首页 >> 合同管理 >>合同维护&gt;&gt;选择营销规则</div>
        <div class="searchContent">
	        <form action="listMarketingRule.do?method=listMarketingRule&contractBindingFlag=1&positionIndexFlag=${positionIndexFlag}&saveOrUpdateFlag=${saveOrUpdateFlag}&positionId=${positionId}"  id="queryForm" name="queryForm" method="post">
		        <table cellspacing="1" class="searchList">
		            <tr class="title">
		                <td>查询条件</td>
		            </tr>
		            <tr>
		                <td class="searchCriteria">
		                	<span>规则名称：</span>
		                  		<input name="marketingRule.marketingRuleName" id="marketingRuleName" type="text"/>
		                  		<input name="marketingRule.positionId" id="positionId" type="hidden"/>
		                    <!--<span>区域：</span>
		                    <select name="marketingRule.locationId" id="locationId">
		                    	<option value="-1">请选择</option>
		                    	<c:choose>
		                    		<c:when test="${!empty areaList}">
		                    			<c:forEach var="object" items="${areaList}" varStatus="">
		                    				<c:choose>
		                    					<c:when test="${object.id==marketingRule.locationId}">
		                    						<option value="${object.id}" selected="selected">${object.areaName}</option>
		                    					</c:when>
		                    					<c:otherwise>
		                    						<option value="${object.id}">${object.areaName}</option>
		                    					</c:otherwise>
		                    				</c:choose>
		                    				
		                    			</c:forEach>
		                    		</c:when>
		                    	</c:choose>
		                    </select>-->	
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>				
		                    <input id="queryButton" name="queryButton" type="button" value="查询" class="btn"/>
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
		                    <input name="cleanButton" id="cleanButton" type="button" value="查全部" class="btn"/>
		                </td>
		            </tr>
		        </table>
	        </form>
        <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
        <table width="100%" cellspacing="1" class="searchList" id="bm">
            <tr class="title">
                <td width="38" height="28" class="dot">选项</td>
                <td width="136">规则名称</td>
                <td width="122">开始时段</td>             
                <td width="150">结束时段</td>
				
            </tr>
            <c:choose>
						<c:when test="${!empty rules}">
							<c:forEach var="rule" items="${rules}" varStatus="status">
								<tr>
									<td align="center">
										<input type="checkbox" id="ruleCheckBox${rule.ruleId}" name="ruleCheckBox" value="${rule.ruleId}"/>
									</td>
									<td>${rule.ruleName}</td>
									<td>${rule.startTime}</td>
									<td>${rule.endTime}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="4">
									暂无记录
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					
					<tr>
						<td height="34" colspan="7"
							style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
							 <input  id="chooseMarketingButton" name="chooseMarketingButton" type="button" value="确定" class="btn"/>&nbsp;&nbsp;
							<c:if test="${page.totalPage>0}">
								<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.pageNo }/${page.totalPage}页</a>&nbsp;&nbsp;
								<c:choose>
								<c:when test="${page.pageNo==1&&page.totalPage!=1}">
									<a href="#" onclick="generateAccess('${page.pageNo+1}','${ruleName}','${positionId}')">下一页</a>&nbsp;&nbsp;
									<a href="#" onclick="generateAccess('${page.totalPage}','${ruleName}','${positionId}')">末页</a>&nbsp;&nbsp;
								</c:when>
								<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
									<a href="#" onclick="generateAccess('1','${ruleName}','${positionId}')">首页</a>&nbsp;&nbsp;
									<a href="#" onclick="generateAccess('${page.pageNo-1 }','${ruleName}','${positionId}')">上一页</a>&nbsp;&nbsp;
								</c:when>
								<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
									<a href="#" onclick="generateAccess('1','${ruleName}','${positionId}')">首页</a>&nbsp;&nbsp;
									<a href="#" onclick="generateAccess('${page.pageNo-1 }','${ruleName}','${positionId}')">上一页</a>&nbsp;&nbsp;
									<a href="#" onclick="generateAccess('${page.pageNo+1 }','${ruleName}','${positionId}')">下一页</a>&nbsp;&nbsp;
									<a href="#" onclick="generateAccess('${page.totalPage}','${ruleName}','${positionId}')">末页</a>&nbsp;&nbsp;
								</c:when>
								</c:choose>
							</c:if>
						</td>
					</tr>
        </table>
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