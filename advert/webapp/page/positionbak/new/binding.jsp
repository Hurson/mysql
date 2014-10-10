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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <input id="projetPath" type="hidden" value="<%=path%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
	<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
	<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>
	<script language="javascript" type="text/javascript" src="<%=path %>/js/util/tools.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=path%>/css/new/main.css">
    <title></title>
	<script type='text/javascript' src='<%=path%>/js/new/avit.js'></script>
	<script type="text/javascript" src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
   <script>
	//保存已选广告位[因父页面要引用此变量，故此处定义为全局变量]
	var alreadyChoosePosition = [];
	var saveOrUpdateFlag = parent.saveOrUpdateFlag;



function generateAccess(currentPage,positionName,positionTypeId,isHd,deliveryMode){
	
	var path = 'queryPositionPage.do?method=queryPage&contractBindingFlag=1&currentPage='+currentPage;
	
	if((!$.isEmptyObject(positionName))&&(''!=positionName)){
		$('#positionName').val(positionName);
	}
	
	if((!$.isEmptyObject(positionTypeId))&&(''!=positionTypeId)){
		$('#positionTypeId').val(positionTypeId);
	}
	
	if((!$.isEmptyObject(isHd))&&(''!=isHd)){
		$('#isHd').val(isHd);
	}
	
	if((!$.isEmptyObject(deliveryMode))&&(''!=deliveryMode)){
		$('#deliveryMode').val(deliveryMode);
	}
	submitForm('#queryForm',path);
}

$(function(){ 
	
	$("#cleanButton").click(function(){
    		$('#positionName').val('');
    		$('#positionTypeId').val('');
    		$('#positionTypeName').val('');
    		$('#isHd').val('');
    		$('#deliveryMode').val('');
    		generateAccess(1,positionName,positionTypeId,isHd,deliveryMode)		
    });
	
	var positionName = '${positionName}'
	if(!$.isEmptyObject(positionName)){
		$('#positionName').val(positionName);
	}
	
	var isHd = '{isHd}'
	if(!$.isEmptyObject(isHd)){
		$('#isHd').val(isHd);
	}
	
	var deliveryMode = '{deliveryMode}'
	if(!$.isEmptyObject(deliveryMode)){
		$('#deliveryMode').val(deliveryMode);
	}
	
    $("#bm").find("tr:even").addClass("sec");  //奇数行的样式
    $("#system-dialog").hide();
    $('#queryButton').click(function(){
    	submitForm('#queryForm','queryPositionPage.do?method=queryPage&contractBindingFlag=1');
    });
    var positionList="{'list':[";
     <c:choose>
		<c:when test="${!empty positionList}">
			<c:forEach var="advertPosition" items="${positionList}" varStatus="status">
				positionList+="{'id':'${advertPosition.id}','characteristicIdentification':'${advertPosition.characteristicIdentification}','positionName':'${advertPosition.positionName}','positionTypeId':'${advertPosition.positionTypeId}','positionTypeName':'${advertPosition.positionTypeName}','positionTypeCode':'${advertPosition.positionTypeCode}','textRuleId':'${advertPosition.textRuleId}','videoRuleId':'${advertPosition.videoRuleId}','imageRuleId':'${advertPosition.imageRuleId}','questionRuleId':'${advertPosition.questionRuleId}','isLoop':'${advertPosition.isLoop}','deliveryMode':'${advertPosition.deliveryMode}','isHd':'${advertPosition.isHd}','isAdd':'${advertPosition.isAdd}','isChannel':'${advertPosition.isChannel}','isAllTime':'${advertPosition.isAllTime}','materialNumber':'${advertPosition.materialNumber}','coordinate':'${advertPosition.coordinate}','price':'${advertPosition.price}','backgroundPath':'${advertPosition.backgroundPath}','validStartDateShow':'','validEndDateShow':'','validStartDate':'','validEndDate':'','chooseMarketRules':'','marketRules':[],'chooseMarketRulesElement':'','currentIndex':'','chooseState':'false','modify':'false'}";
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
	
    /*$("#searchPositionSubmit").click(function(){
     	var positionTypeId = $("#positionTypeId").val();
     	positionTypeId = encodeURI(positionTypeId);   
		positionTypeId = encodeURI(positionTypeId);
     	var positionName = $("#positionNameS").val();
     	positionName = encodeURI(positionName);   
		positionName = encodeURI(positionName);
		var isHd = $("#isHd").val();
		var deliveryMode = $("#deliveryMode").val();
     	url='queryPositionPage.do?method=queryPage&contractBindingFlag=1&positionTypeId='+positionTypeId+'&positionName='+positionName+'&isHd='+isHd+'&deliveryMode='+deliveryMode;
     	accessUrl(url);
     	return;
     });*/
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
	       					var timePositionFlag = new Date().getTime()+"_"+jsonStr.id;
	       						jsonStr.positionIndexFlag=timePositionFlag;
	       						alreadyChoosePosition.push(jsonStr);
	       						
	       					if((!$.isEmptyObject(saveOrUpdateFlag))&&(saveOrUpdateFlag=='save')){
	       						//alreadyChoosePosition.push(jsonStr);
	       					}else if((!$.isEmptyObject(saveOrUpdateFlag))&&(saveOrUpdateFlag=='update')){
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
	 		//alreadyChoosePosition=alreadyChoosePosition.concat(parent.alreadyFillInForm.bindingPosition);
			
			if(!$.isEmptyObject(alreadyChoosePosition)){
				$(alreadyChoosePosition).each(function(index,item){
					var objectCopy=deepCopy(item);
	       			parent.alreadyFillInForm.bindingPosition.push(objectCopy);
				});
			}
			
	 		//排序
	 		parent.alreadyFillInForm.bindingPosition=parent.alreadyFillInForm.bindingPosition.sort(
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
	 		//数组对象拷贝
	 		parent.show();
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
</head>

<body class="mainBody">

<div class="search">
        <div class="path">
        	<img src="<%=path%>/images/new/filder.gif" width="15" height="13"/>首页 >> 合同管理 >>合同维护&gt;&gt;选择广告位</div>
        <div class="searchContent">
        <table cellspacing="1" class="searchList">
            <tr class="title">
                <td>查询条件</td>
            </tr>
            <tr>
            	<form action="" id="queryForm" name="queryForm" method="post">
	                <td class="searchCriteria">
		                  <span>广告位名称：</span>
		                  <input id="positionName" name="advertPosition.positionName" type="text" value="${advertPosition.positionName}" maxlength="80"/>
		                    <span>类型：</span>
		                    <select id="positionTypeId" name="advertPosition.positionTypeId">
		                        <option value="-1">请选择</option>
			                      	<c:if test="${list!=null}">
			                      		<c:forEach var="pt" items="${list}">
			                      			<c:choose>
				                      			<c:when test="${pt.id==advertPosition.positionTypeId}">
				                      				<option value="${pt.id}" selected="selected">${pt.positionTypeName}</option>
				                      			</c:when>
				                      			<c:otherwise>
				                      				<option value="${pt.id}">${pt.positionTypeName}</option>
				                      			</c:otherwise>
			                      			</c:choose>
			                      		</c:forEach>	
			                      	</c:if>
		                    </select>
							<span>是否高清：</span>
							<select id="isHd" name="advertPosition.isHd">
									
									<c:choose>
				                      	<c:when test="${advertPosition.isHd==0}">
				                      		<option value="-1">请选择</option>
				                      		<option value="0" selected="selected">标清</option>
				                      		<option value="1">高清</option>
				                      	</c:when>
				                      	<c:when test="${advertPosition.isHd==1}">
				                      		<option value="-1">请选择</option>
				                      		<option value="0">标清</option>
				                      		<option value="1" selected="selected">高清</option>
				                      	</c:when>
				                      	<c:otherwise>
				                      		<option value="-1">请选择</option>
				                      		<option value="0">标清</option>
				                      		<option value="1">高清</option>
				                      	</c:otherwise>
			                      	</c:choose>										
		                    </select>
							<span>投放方式：</span>
		                    <select id="deliveryMode" name="advertPosition.deliveryMode">
					            
								<c:choose>
				                      	<c:when test="${advertPosition.deliveryMode==0}">
				                      		<option value="-1">请选择</option>
				                      		<option value="0" selected="selected">投放式</option>
				                      		<option value="1">请求式</option>
				                      	</c:when>
				                      	<c:when test="${advertPosition.deliveryMode==1}">
				                      		<option value="-1">请选择</option>
				                      		<option value="0">投放式</option>
				                      		<option value="1" selected="selected">请求式</option>
				                      	</c:when>
				                      	<c:otherwise>
				                      		<option value="-1">请选择</option>
				                      		<option value="0">投放式</option>
				                      		<option value="1">请求式</option>
				                      	</c:otherwise>
			                     </c:choose>	
							</select>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
		                    <input name="queryButton" id="queryButton" type="button" value="查询" class="btn"/>
		                    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
		                    <input id="cleanButton" name="cleanButton" type="button" value="查全部" class="btn"/>
	                 </td>
                 </form>
            </tr>
        </table>
        <div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
        <table width="100%" cellspacing="1" class="searchList" id="bm">
            <tr class="title">
              <td width="38" height="28" class="dot">选项</td>
              	<td width="150">特征值</td>
                <td width="163">广告位名称</td>
                <td width="227">类型</td>             
                <td width="72">是否高清</td>
				<td width="141">投放方式</td>
				<td width="80">价格</td>
            </tr>
            <c:choose>
						<c:when test="${!empty positionList}">
							<c:forEach var="advertPosition" items="${positionList}" varStatus="status">
								<tr>
									<td align="center" height="26"><input type="checkbox" id="positionCheckbox${advertPosition.id}" name="positionCheckbox" value="${advertPosition.id}"/></td>
									<td>
										${advertPosition.characteristicIdentification}
									</td>
									<td>${advertPosition.positionName}</td>
									<td>${advertPosition.positionTypeName}</td>
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
									<td>
										${advertPosition.price}
									</td>
									
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="7">
									暂无记录
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
           <tr>
						<td height="34" colspan="7"
							style="text-align: right;">
							<input id="choosePositionButton" name="choosePositionButton" type="button" value="确定" class="btn" />&nbsp;&nbsp;
							<c:if test="${page.totalPage > 0 }">
								<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
								<c:choose>
									<c:when test="${page.currentPage==1&&page.totalPage!=1}">
										【<a href="#" onclick="generateAccess('${page.currentPage+1}','${positionName}','${positionTypeId}','${isHd}','${deliveryMode}')">下一页</a>】
										【<a href="#" onclick="generateAccess('${page.totalPage}','${positionName}','${positionTypeId}','${isHd}','${deliveryMode}')">末页</a>】
									</c:when>
									<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
										【<a href="#" onclick="generateAccess('1','${positionName}','${positionTypeId}','${isHd}','${deliveryMode}')">首页</a>】 
										【<a href="#" onclick="generateAccess('${page.currentPage-1}','${positionName}','${positionTypeId}','${isHd}','${deliveryMode}'">上一页</a>】
									</c:when>
									<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
										【<a href="#" onclick="generateAccess('1','${positionName}','${positionTypeId}','${isHd}','${deliveryMode}')">首页</a>】 
										【<a href="#" onclick="generateAccess('${page.currentPage-1}','${positionName}','${positionTypeId}','${isHd}','${deliveryMode}')">上一页</a>】
										【<a href="#" onclick="generateAccess('${page.currentPage+1}','${positionName}','${positionTypeId}','${isHd}','${deliveryMode}')">下一页</a>】
										【<a href="#" onclick="generateAccess('${page.totalPage}','${positionName}','${positionTypeId}','${isHd}','${deliveryMode}')">末页</a>】
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