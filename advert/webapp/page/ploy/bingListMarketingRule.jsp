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
<title>广告系统</title>
<script>
/**
 * 判断数组中的元素是否在另一个数组中已存在
 **/
Array.existsSameValues = function(arr1, arr2) {
    var existsSaveValueReturn=[];
    if(arr1 instanceof Array && arr2 instanceof Array)
    {
        for(var s in arr1){
		    for(var x in arr2){
		        if(arr1[s]!=''&&arr1[s]==arr2[x]){
		            existsSaveValueReturn.push(arr1[s]);
		        }
		    }
		}
    }
    return existsSaveValueReturn;
};

$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
     $("#system-dialog").hide();
     
     var marketingRuleList="{'list':[";
     <c:choose>
		<c:when test="${!empty rules}">
			<c:forEach var="rule" items="${rules}" varStatus="status">
				//marketingRuleList+="{'ruleOrder':'${status.count}','id':'${rule.id}','ruleName':'${rule.ruleName}','startTime':"+parent.dateStringTranformDateObject('${rule.startTime}')+",'endTime':"+parent.dateStringTranformDateObject('${rule.endTime}')+",'positionName':'${rule.positionName}','areaName':'${rule.areaName}','channelName':'${rule.channelName}','state':'${rule.state}'}";
				//marketingRuleList+="{'ruleOrder':'${status.count}','id':'${rule.id}','ruleName':'${rule.ruleName}','areaName':'${rule.areaName}','channelName':'${rule.channelName}','state':'${rule.state}'}";
				marketingRuleList+="{'ruleOrder':'${status.count}','id':${rule.id},'ruleName':'${rule.ruleName}','startTime':"+parent.dateStringTranformDateObject('${rule.startTime}')+",'endTime':"+parent.dateStringTranformDateObject('${rule.endTime}')+",'positionName':'${rule.positionName}','areaName':'${rule.areaName}','channelName':'${rule.channelName}','state':${rule.state}}";
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
    
    $("#queryMarketingRuleButton").click(function(){
     	$("#bindingMarketingRuleForm").submit();
     });
     
      $("#chooseMarketingButton").click(function(){
     
     	var alreadyChoose = "";
     	//父级节点id，供回写参数时使用
     	var chooseMarketRulesElement = '${chooseMarketRulesElement}';
     	//父级节点id，供回写参数时使用
     	var currentIndex = '${currentIndex}';
     	
     	var startRow = '${startRow}';
     	
     	var endRow = '${endRow}';
     	
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
	    	
	    	
	    	chooseMarketRulesElement=unescape(chooseMarketRulesElement);
	    	
	    	//chooseMarketRulesElementValue = $(chooseMarketRulesElement,parent.document).val();
	    	
	    	//$(chooseMarketRulesElement,parent.document).html(alreadyChoose);
	    	
	    	parent.getAlreadyChoosePosition();
	    	
	    	//将数据写入全局变量中
	    	for (var index = startRow; index < endRow; index++) {
	    		if(index==currentIndex){
	    			
					var existChooseMarketRules = parent.alreadyChoosePositionP[index].chooseMarketRules;
					var existChooseMarketRulesArray = existChooseMarketRules.split(',');
					var existArray = Array.existsSameValues(existChooseMarketRulesArray,alreadyChooseArray);
					if(existArray.length<=0){
						
						//var mergeArray = existChooseMarketRulesArray.concat(alreadyChooseArray);
						var mergeArray = existChooseMarketRules+alreadyChooseArray;
						parent.alreadyChoosePositionP[index].chooseMarketRules=mergeArray;
						
						$(chooseMarketRulesElement,parent.document).html(mergeArray);
					
						//遍历当前已选集合
						$(alreadyChooseMarketRulesInner).each(function(indexMarketingRules,itemMarketingRules){
							
							var existFlag = false;
							
							//判断记录在原始集合中是否已经存在
							$(parent.alreadyChoosePositionP[index].alreadyChooseMarketRules).each(function(parentIndexMarketingRules,parentItemMarketingRules){
									if(parentItemMarketingRules.id==itemMarketingRules.id){
										existFlag=true;
									}
							});
							
							if(existFlag==false){
								parent.alreadyChoosePositionP[index].alreadyChooseMarketRules.push(alreadyChooseMarketRulesInner[indexMarketingRules]);
							}
						});
					}else{
						var message = '以下记录已存在'+existArray;
						$("#content").html(message);
						$( "#system-dialog" ).dialog({
					      	modal: true
					    });
					}
					
	    		}
			}
	    }
     });
});

/**
 * 对象属性拷贝
 *
 */
function deepCopy(obj){
            if(obj instanceof Array){
                var arr = [],i = obj.length;
                while(i--){
                   arr[i] = arguments.callee.call(null,obj[i]);
                }
                return arr;
            }else if(obj instanceof Date || obj instanceof RegExp || obj instanceof Function){
                return obj;
            }else if(obj instanceof Object){
                var a = {};
                for(var i in obj){
                   a[i] = arguments.callee.call(null,obj[i]);
                }
                return a;
            }else{
                return obj;
            }
        }

</script>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6; }
</style>
</head>

<body onload="">
	<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
	<tr>
			<td style=" padding:1px;">
				<form action="listMarketingRule.do?method=listMarketingRule&contractBindingFlag=1&positionId=${positionId}&ruleId=${ruleId}&viewType=${viewType}"  id="bindingMarketingRuleForm" name="bindingMarketingRuleForm" method="post">
					<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
						<tr>
							<td class="formTitle" colspan="99">
								<span>查询 规则 条件</span>
							</td>
						</tr>
						<tr>
							<td class="td_label">广告位ID：</td>
							<td class="td_input">
								<input  id="positionId" type="text" name="positionId" class="e_input"  value="${positionId}" readonly="readonly"/>
							</td>
							<td class="td_label">查看类型：</td>
							<td class="td_input">
								<select id="viewType" name="viewType">
									<option value="1" selected="selected">按对应广告位查询</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="formBottom" colspan="99">
							   <input id="queryMarketingRuleButton" name="queryMarketingRuleButton" type="button" title="查看" class="b_search" value="" onfocus=blur()/>
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
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
					<c:choose>
						<c:when test="${!empty rules}">
							<c:forEach var="rule" items="${rules}" varStatus="status">
								<tr>
									<td align="center"><input type="checkbox" id="ruleCheckBox${rule.id}" name="ruleCheckBox" value="${rule.id}"/></td>
									<td align="center" height="26">${status.count}</td>
									<td>${rule.ruleName}</td>
									<td>${rule.startTime}</td>
									<td>${rule.endTime}</td>
									<td>${rule.positionName}</td>
									<td>
										${rule.areaName}
									</td>
									<td>
										${rule.channelName}
									</td>
									<td>
										${rule.state}
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="9">
									暂无记录
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td colspan="9"></td>
					</tr>
					<tr>
						<td height="34" colspan="23"
							style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
							<c:if test="${page.totalPage>0}">
								<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.pageNo }/${page.totalPage}页</a>&nbsp;&nbsp;
								<c:choose>
								<c:when test="${page.pageNo==1&&page.totalPage!=1}">
									<a href="listMarketingRule.do?pageNo=${page.pageNo+1}&positionId=${positionId}&ruleId=${ruleId}&viewType=${viewType}&contractBindingFlag=1">下一页</a>&nbsp;&nbsp;
									<a href="listMarketingRule.do?pageNo=${page.totalPage}&positionId=${positionId}&ruleId=${ruleId}&viewType=${viewType}&contractBindingFlag=1">末页</a>&nbsp;&nbsp;
								</c:when>
								<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
									<a href="listMarketingRule.do?pageNo=1&positionId=${positionId}&ruleId=${ruleId}&viewType=${viewType}&contractBindingFlag=1">首页</a>&nbsp;&nbsp;
									<a href="listMarketingRule.do?pageNo=${page.pageNo-1 }&positionId=${positionId}&ruleId=${ruleId}&viewType=${viewType}&contractBindingFlag=1">上一页</a>&nbsp;&nbsp;
								</c:when>
								<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
									<a href="listOrder.do?pageNo=1&positionId=${positionId}&ruleId=${ruleId}&viewType=${viewType}&contractBindingFlag=1&contractBindingFlag=1">首页</a>&nbsp;&nbsp;
									<a href="listMarketingRule.do?pageNo=${page.pageNo-1 }&positionId=${positionId}&ruleId=${ruleId}&viewType=${viewType}&contractBindingFlag=1">上一页</a>&nbsp;&nbsp;
									<a href="listMarketingRule.do?pageNo=${page.pageNo+1 }&positionId=${positionId}&ruleId=${ruleId}&viewType=${viewType}&contractBindingFlag=1">下一页</a>&nbsp;&nbsp;
									<a href="listMarketingRule.do?pageNo=${page.totalPage}&positionId=${positionId}&ruleId=${ruleId}&viewType=${viewType}&contractBindingFlag=1">末页</a>&nbsp;&nbsp;
								</c:when>
								</c:choose>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="formBottom" colspan="99" style="text-align: right;">
							 <input id="chooseMarketingButton" name="chooseMarketingButton" type="button" title="确定" class="b_add" value="" onfocus=blur()/>
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