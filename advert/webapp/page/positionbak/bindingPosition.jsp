<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<input id="projetPath" type="hidden" value="<%=path%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>
<title>无标题文档</title>
<script>
//保存已选广告位[因父页面要引用此变量，故此处定义为全局变量]
var alreadyChoosePosition = [];
var saveOrUpdateFlag= '${saveOrUpdateFlag}';

function accessUrl(url){
		window.location.href=url;
}
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    $("#system-dialog").hide();
    
    
    var positionList="{'list':[";
     <c:choose>
		<c:when test="${!empty positionList}">
			<c:forEach var="advertPosition" items="${positionList}" varStatus="status">
				positionList+="{'id':'${advertPosition.id}','characteristicIdentification':'${advertPosition.characteristicIdentification}','positionName':'${advertPosition.positionName}','categoryId':'${advertPosition.categoryId}','positionTypeId':${advertPosition.positionTypeId},'positionTypeName':'${advertPosition.positionTypeName}','deliveryPlatform':'${advertPosition.deliveryPlatform}','textRuleId':'${advertPosition.textRuleId}','videoRuleId':'${advertPosition.videoRuleId}','imageRuleId':'${advertPosition.imageRuleId}','questionRuleId':'${advertPosition.questionRuleId}','isLoop':'${advertPosition.isLoop}','deliveryMode':'${advertPosition.deliveryMode}','isHd':'${advertPosition.isHd}','isAdd':'${advertPosition.isAdd}','isChannel':'${advertPosition.isChannel}','isAllTime':'${advertPosition.isAllTime}','materialNumber':'${advertPosition.materialNumber}','coordinate':'${advertPosition.coordinate}','price':'${advertPosition.price}','discount':'${advertPosition.discount}','backgroundPath':'${advertPosition.backgroundPath}','startTime':'${advertPosition.startTime}','endTime':'${advertPosition.endTime}','validStartDateShow':'','validEndDateShow':'','validStartDate':'','validEndDate':'','chooseMarketRules':'','marketRules':[],'chooseMarketRulesElement':'','currentIndex':'','chooseState':'false','modify':'false'}";
				<c:choose>
					<c:when test="${status.last}">
					</c:when>
					<c:otherwise>
						positionList+=',';
					</c:otherwise>
				</c:choose>						
			</c:forEach>
		</c:when>			
	</c:choose>
	positionList+="]}";
	var positionListObject=eval("("+positionList+")");
	
    $("#searchPositionSubmit").click(function(){
     	var positionTypeId = $("#positionTypeId").val();
     	positionTypeId = encodeURI(positionTypeId);   
		positionTypeId = encodeURI(positionTypeId);
     	var positionName = $("#positionNameS").val();
     	positionName = encodeURI(positionName);   
		positionName = encodeURI(positionName);
     	url='queryPositionPage.do?method=queryPage&contractBindingFlag=1&positionTypeId='+positionTypeId+'&positionName='+positionName;
     	accessUrl(url);
     	return;
     });
     $("#choosePositionButton").click(function(){
     
     	var alreadyChoose = "";
	    $("input[name='positionCheckbox']").each(function(){
	        if($(this).is(':checked')){
	            alreadyChoose += $(this).val() + "#";
	        }
	    });
	    
	    if($.isEmptyObject(alreadyChoose)){
	    	var message = '请先选择已有【广告位】后再进行添加';
			$("#content").html(message);
			$( "#system-dialog" ).dialog({
		      	modal: true
		    });
	    }else{
	    	var alreadyChooseColumn = alreadyChoose.split('#');
	    	$(alreadyChooseColumn).each(function(index,itemOut){
	    		if(!$.isEmptyObject(itemOut)){
	       			$(positionListObject.list).each(function(index,itemInner){
	       				if((!$.isEmptyObject(itemOut))&&(itemOut==itemInner.id)){
	       					var jsonStr = deepCopy(itemInner);
	       					
	       					if((!$.isEmptyObject(saveOrUpdateFlag))&&(saveOrUpdateFlag=='save')){
	       						alreadyChoosePosition.push(jsonStr);
	       					}else if((!$.isEmptyObject(saveOrUpdateFlag))&&(saveOrUpdateFlag=='update')){
	       						alreadyChoosePosition.push(jsonStr);
	       						var timePositionFlag = new Date().getTime()+"_"+jsonStr.id;
	       						jsonStr.positionIndexFlag=timePositionFlag;
	       						//将新新添加记录加入comparedForm中 1 新增
			 					var bindingPosition = "{'id':"+itemInner.id+",'validStartDate':"+"''"+",'validEndDate':"+"''"+",'dbFlag':1,'flag':1,'positionIndexFlag':'"+timePositionFlag+"','marketRules':[]"+"}"; 
			 					bindingPosition=eval("("+bindingPosition+")");
			 					parent.comparedForm.bindingPosition.push(bindingPosition);
	       					}
	       				}
					});
	       		}
			});
	 		
	 		//拷贝已有数据至当前记录中
	 		alreadyChoosePosition=alreadyChoosePosition.concat(parent.alreadyFillInForm.bindingPosition);

	 		//排序
	 		alreadyChoosePosition=alreadyChoosePosition.sort(
	 			function(position1,position2)  
                {  
                	var result = 0;
                    if(position1.characteristicIdentification < position2.characteristicIdentification){
                    	result = -1;
                    }  
                    if(position1.characteristicIdentification > position2.characteristicIdentification){  
                    	result = 1;
                    }  
                    return result;  
                }  
	 		);
	 		parent.showPositionList4Page(1,6);
	    }
	    parent.easyDialog.close();
     });
	defaultChoose('checkbox',parent.alreadyFillInForm.bindingPosition);
});

</script>
<style>
	.easyDialog_wrapper{ width:800px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}
</style>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td style="padding:2px;">
			<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
				<tr>
					<td class="formTitle" colspan="99"><span>·广告位查询</span></td>
				</tr>
				<tr>
					<td class="td_label">广告位类型编码：</td>
					<td class="td_input">
						<input id="positionTypeId" name="positionTypeId" class="e_input" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>
					</td>
					<td class="td_label">广告位名称：</td>
					<td class="td_input">
						<input id="positionNameS" name="positionNameS" class="e_input" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80"/>
					</td>
				</tr>
				<tr>
					<td class="formBottom" colspan="99">
						<input name="searchPositionSubmit" id="searchPositionSubmit" type="button" title="查看" class="b_search" value=""/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="padding:2px;">
			<div>  
				<table cellpadding="0" cellspacing="1" width="100%"
			class="taba_right_list" id="bm">
					<tr>
						<td colspan="23" style="padding-left: 8px; background: url(<%=path %>/images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>·广告位列表</span></td>
					</tr>
					<tr>
						<td height="26px" align="center">选项</td>
						<td height="26px" align="center">序号</td>
						<td>广告位类型编码</td>
						<td>广告位特征值</td>
						<td>广告位名称</td>
						<td>是否高清</td>
						<td>是否轮询</td>
						<td>是否叠加</td>
						<td>投放方式</td>
						<td>轮询个数</td>
						<td>价格</td>
					</tr>
					<c:choose>
						<c:when test="${!empty positionList}">
							<c:forEach var="advertPosition" items="${positionList}" varStatus="status">
								<tr>
									<td align="center" height="26"><input type="checkbox" id="positionCheckbox${advertPosition.id}" name="positionCheckbox" value="${advertPosition.id}"/></td>
									<td align="center" height="26">${status.count}</td>
									<td>${advertPosition.positionTypeId}</td>
									<td>${advertPosition.characteristicIdentification}</td>
									<td>${advertPosition.positionName}</td>
									<td>
										<c:choose>
											<c:when test="${advertPosition.isHd==0}">
												标清
											</c:when>
											<c:when test="${advertPosition.isHd==1}">
												高清
											</c:when>
											<c:when test="${advertPosition.isHd==2}">
												都支持
											</c:when>
											<c:otherwise>
												未选择
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${advertPosition.isLoop==0}">
												否
											</c:when>
											<c:when test="${advertPosition.isLoop==1}">
												是
											</c:when>
											<c:otherwise>
												未选择
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${advertPosition.isAdd==0}">
												否
											</c:when>
											<c:when test="${advertPosition.isAdd==1}">
												是
											</c:when>
											<c:otherwise>
												未选择
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${advertPosition.deliveryMode==0}">
												投放式
											</c:when>
											<c:when test="${advertPosition.deliveryMode==1}">
												请求式
											</c:when>
											<c:otherwise>
												未选择
											</c:otherwise>
										</c:choose>
									</td>
									<td>${advertPosition.materialNumber}</td>
									<td>
										${advertPosition.price}
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="12">
									暂无记录
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td colspan="12">
						
						</td>
					</tr>
					<tr>
						<td height="34" colspan="23"
							style="text-align: right;">
							<c:if test="${page.totalPage > 0 }">
								<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
								<c:choose>
									<c:when test="${page.currentPage==1&&page.totalPage!=1}">
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.currentPage+1}&positionTypeId=${positionTypeId}&contractBindingFlag=1">下一页</a>】
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.totalPage}&positionTypeId=${positionTypeId}&contractBindingFlag=1">末页</a>】
									</c:when>
									<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
										【<a href="queryPositionPage.do?method=queryPage&currentPage=1&positionTypeId=${positionTypeId}&contractBindingFlag=1">首页</a>】 
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.currentPage-1 }&positionTypeId=${positionTypeId}&contractBindingFlag=1">上一页</a>】
									</c:when>
									<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
										【<a href="queryPositionPage.do?method=queryPage&currentPage=1&positionTypeId=${positionTypeId}&contractBindingFlag=1">首页</a>】 
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.currentPage-1}&positionTypeId=${positionTypeId}&contractBindingFlag=1">上一页</a>】
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.currentPage+1}&positionTypeId=${positionTypeId}&contractBindingFlag=1">下一页</a>】
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.totalPage}&positionTypeId=${positionTypeId}&contractBindingFlag=1">末页</a>】
									</c:when>
								</c:choose>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="formBottom" colspan="99" style="text-align: right;">
							 <input id="choosePositionButton" name="choosePositionButton" type="button" title="确定" class="b_add" value="" onfocus=blur()/>
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