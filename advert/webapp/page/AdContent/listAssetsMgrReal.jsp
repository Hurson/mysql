<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tags/fmt.tld" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="css/platform.css" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/js.js"></script>


<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/addOrUpdate.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/contract/listContract.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>

<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css">




<title>运营商广告资产管理</title>
<script>

		var queryPositions = new Array();//按条件查询广告位数组
	var selPosition = null;//绑定广告位对象
	
	function showPosition(){
	    //从数据库中拿到广告位信息
		selectPositonInfo();
		showDiv();
	}
	
	function selectPositonInfo(){
		$.ajax({
		type:"post",
		url: 'adContentMgr_getPosition.do',
		success:function(responseText){
			if(responseText!='-1'){
		     //	positions = eval(responseText);
				queryPositions = eval(responseText);
				//将从数据库中查询出来的数据，展现在页面上
				fillPositionInfo();
			}else{
				alert("服务器异常，广告位加载失败，请稍后重试！");
			}
		},
		dataType:'text',
		error:function(){
			alert("服务器异常，广告位加载失败，请稍后重试！");
		}
	});
	
	}

		//确定 选择
	//确认 广告位
	function selectPosition(){ 
		var positionIds = $("input[name='positionId']");
		for(var i = 0;i<positionIds.length;i++){
			if(positionIds[i].checked){
				selPosition = queryPositions[i];
				$("#advertPositionName").val(selPosition.positionName);
				$("#advertPositionId").val(selPosition.id);
			}else{
				$("#advertPositionName").val();
				$("#advertPositionId").val();
				
			}
		}
		closeSelectDiv();
		return;
	}
	
	//填充广告位
	function fillPositionInfo(){
		var str = "";
		for(var i =0;i<queryPositions.length;i++){
			str +="<tr";
			if(i%2==0){
				str += " class='treven' "
			}else{
				str += " class='trodd' "
			}
			str +="><td><input type='radio' name='positionId' value='";
			str +=queryPositions[i].id;
			if(i==0){
				str +="' checked='checked";
			}
			str +="'/></td><td>";
			str +=queryPositions[i].positionName;
			str +="</td><td>";
			str += queryPositions[i].positionTypeName;
			str +="</td><td>";
			str += getIsHDDesc(queryPositions[i].isHd);
			str +="</td><td>";
			str += getAddDesc(queryPositions[i].isAdd);
			str +="</td><td>";
			str += getLoopDesc(queryPositions[i].isLoop);
			
			str +="</td><td>";
			if(queryPositions[i].materialNumber==0){
				str +="-";
			}else{
				str += queryPositions[i].materialNumber;
			}
			str +="</td><td>";
			str += getModeDesc(queryPositions[i].deliveryMode);
			str +="</td><td>";
			str += queryPositions[i].description;
			str +="</td></tr>";	
		}
		$("#positionInfo").html(str);
	}
	
	
	
	
	
	//显示弹出层
	function showDiv(){
		    $('#position').show("slow");
			$('#bg').show();
			$('#popIframe').show();
	}
	//关闭弹出层
	function closeSelectDiv(){
		    $('#position').hide();
		  	$('#bg').hide();
		  	$('#popIframe').hide();
		  	return;
	}



	function go(){
		//alert("nihao");	
		var startTime = document.getElementById("startTime").value;
		var endTime =  document.getElementById("endTime").value;
		
		var sTime = new Date(startTime);
		var eTime = new Date(endTime);
		
		if(startTime !="" && endTime == ""){
			alert("创建结束时间不能为空！");
			document.getElementById("startTime").value="";
			return false;
		}else if(startTime == "" && endTime != ""){
		    alert("创建开始时间不能为空！");
		    document.getElementById("endTime").value="";
		    return false;
		}else if(startTime > endTime){
			alert("您输入的创建开始时间大于结束时间，请确认后再输入！");
			document.getElementById("startTime").value="";
			document.getElementById("endTime").value="";
			return false;
		}else{
		  document.getElementById("form").submit();
		}
	}




$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
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

<body>

<div id="position" class="showDiv" style="display:none">
	 <table cellpadding="0" cellspacing="1" width="100%" class="taba_right_list" id="pDiv">
	<tr>
		<td height="26px" align="center" width="5%"></td>
		<td>广告位名称</td>
		<td>广告位类型</td>
		<td>高清/标清</td>
		<td>是否叠加</td>
		<td>是否轮询</td>
		<td>轮询个数</td>
		<td>投放方式</td>
		<td>描述</td>
	</tr>
	<tbody id="positionInfo"></tbody>
	
	<tr>
		<td height="34" colspan="12" style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: center;">
		<div class="buttons"><a href="javascript:selectPosition();">确认</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:closeSelectDiv();">返回</a>
		</div>
		</td>
	</tr>
</table>
</div> 




<div id="bg" class="bg" style="display:none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>

<form id="form" action="adContentMgr_listReal.do" method="post" id="adContentMgr_listReal" name="adContentMgr_listReal"> 
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
		<td style="padding: 4px;">
		<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
			<tr>
				<td class="formTitle" colspan="99"><span>运营商广告资产管理</span></td>
			</tr>
			<tr>
				<td class="td_label">资产名称：</td>
				<td class="td_input"><input type="text" class="e_input" name="resource.resourceName" value="${resourceName}" onblur="this.className='e_input'" onfocus="this.className='e_inputFocus'"/></td>
				<td class="td_label">资产类型：</td>
				<td class="td_input"><select name="resource.resourceType">
					<option value="10">全部</option>
					<option value="0">图片</option>
					<option value="1">视频</option>
					<option value="2">文字</option>
				</select></td>
			</tr>
			
			<tr>
				<td class="td_label">所属内容分类：</td>
				<td class="td_input"><select name="resource.category">
					<option value="10">全部</option>
					<option value="0">公益</option>
					<option value="1">商用</option>
				</select></td>
				<td class="td_label">广告位：</td>
				<td class="td_input">                                                                                                  
				  <input type="text"  id="advertPositionName" name="resource.advertPositionName" onclick="showPosition();" value="${advertPositionName}" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
				  <input type="hidden" name="resource.advertPositionId" id="advertPositionId"/>
				</td>	
			</tr>
			
			<tr>
				<td class="td_label">所属合同号：</td>
				<td class="td_input"><input type="text" name="resource.contractNumberStr" value="${contractNumber}" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /></td>
				<td class="td_label">审核状态：</td>
					<td class="td_input"><select name="resource.stateStr">
					<option value="10">全部</option>
					<option value="2">上线</option>
					<option value="3">下线</option>
					</select>
				</td>
			</tr>
				<tr>
				<td class="td_label">创建时间前：</td>
				<td class="td_input"><input type="text" name="resourceReal.createTimeA" id="startTime" onclick="WdatePicker()" readOnly="true" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /></td>
				<td class="td_label">创建时间后：</td>
				<td class="td_input"><input type="text" id="endTime" name="resourceReal.createTimeB" onclick="WdatePicker()" readOnly="true" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"/></td>
			</tr>
			
			
			
			<tr>
				<td class="formBottom" colspan="99">
				     <!--<input name="Submit" type="submit" title="查看" value="" class="b_search" onfocus=blur() />-->
				     <input name="Submit" type="button" title="查看" value="" class="b_search" onfocus=blur() onclick="go()" />
				     
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding: 4px;">
		<table cellpadding="0" cellspacing="1" width="100%" class="taba_right_list" id="bm">

			<tr>
				<td colspan="12" class="formTitle"
					style="padding-left: 8px; background: url(images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>广告资源分类列表</span></td>
			</tr>

			<tr>
				<td  height="26px" align="center">序号</td>
				<td  height="26px" align="center">资产名称</td>
				<td  height="26px" align="center">资产类型</td>
				<td  height="26px" align="center">所属合同号</td>
				<td  height="26px" align="center">所属内容分类</td>
				<td  height="26px" align="center">广告位名称</td>
				<td  height="26px" align="center">有效期开始时间</td>
				<td  height="26px" align="center">有效期结束时间</td>
				<td  height="26px" align="center">状态</td>
				<td  height="26px" align="center">创建时间</td>
				<td  height="26px" align="center">操作</td>
			</tr>
			<s:if test="listAdAssetResourceReal.size==0">
			<tr>
				<td bgcolor="#FFFFFF" align="center" colspan="12"
					style="text-align: center">无记录</td>
			</tr>
		</s:if>
		<s:else>
		    <c:set var="index" value="0"/>
			<s:iterator value="listAdAssetResourceReal" status="rowstatus" var="item">
				<tr>
					<td align="center">${rowstatus.count}</td>
					<td align="center"><s:property value="resourceName" /></td>
					
					<td align="center"><s:if test="resourceType==0">图片</s:if>
					<s:if test="resourceType==1">视频</s:if>
					<s:if test="resourceType==2">文字</s:if>
					</td>
					<td align="center"><s:property value="contractNumber" /></td>
					<td align="center">
					    <s:if test="category == 0">公益</s:if>
						<s:if test="category == 1">商用</s:if>	
					</td>
					
					<td align="center">
				      <s:if test="mapAdvertPosition[id] eq '0100'">开机画面广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0201'">菜单图片广告向单</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0202'">导航条广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0203'">快捷切换列表广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0204'">音量条广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0205'">预告提示广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0206'">广播收听背景广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0207'">回看回放菜单广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0208'">回看回放插播广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0209'">回看回放暂停广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0210'">菜单视频外框广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0211'">点播菜单广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0212'">点播随片图片广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0213'">点播问卷广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0214'">点播片头插播广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0215'">点播暂停广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0216'">点播挂角广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0217'">点播游动字幕广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0300'">节目视频广告</s:if>
				      <s:if test="mapAdvertPosition[id] eq '0500'">回看回放暂停广告</s:if>
					</td>
					
					<td align="center"><fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd"/></td>
					<td align="center"><fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd"/></td>
					<td align="center">
						<s:if test="state=='0'">待审核</s:if>
						<s:if test="state=='1'">审核未通过</s:if>
						<s:if test="state=='2'">上线</s:if>
						<s:if test="state=='3'">下线</s:if>
					</td>
					<td align="center"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/></td>
					<td align="center">
					   <input type="button" title="修改" class="button_halt" onclick="goUpdateMetaRedirectReal(<s:property value='id'/>,<s:property value='resourceType'/>,<s:property value='resourceId'/>,<s:property value='state'/>)" />
						<input type="button" title ="删除"  class="button_delete"  onclick="goDeleteInfoReal(<s:property value='id'/>,<s:property value='resourceType'/>,<s:property value='resourceId'/>,<s:property value='state'/>)" />
						<input type="button" title="下线" class="button_start" onclick="goDownLine(<s:property value ='id'/>,<s:property value='resourceType'/>,<s:property value='resourceId'/>,<s:property value='state'/>)" />
					</td>
				</tr>
				<c:set var="index" value="${index+1}"/>
			</s:iterator>
			
			<c:if test="${index<page.pageSize}">
	                <c:forEach begin="${index}" end="${page.pageSize-1}" step="1">
						<tr>
							<td align="center" height="26">&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</c:forEach>
				</c:if>
		</s:else>
			<tr>
				<td height="34" colspan="12" style="background: url(images/bottom.jpg) repeat-x; text-align: right;">
					<a href="#">共计${page.totalPage }页</a>&nbsp;&nbsp;
					<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp;
						<c:choose>
							<c:when test="${page.pageNo==1&&page.totalPage!=1}">
								【<a href="adContentMgr_listReal.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_listReal.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
							</c:when>
							<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
								【<a href="adContentMgr_listReal.do?pageNo=1">首页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_listReal.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
							</c:when>
							<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
								【<a href="adContentMgr_listReal.do?pageNo=1">首页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_listReal.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_listReal.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_listReal.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
							</c:when>
						</c:choose>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
