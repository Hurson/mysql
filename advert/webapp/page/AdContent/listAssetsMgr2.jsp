<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tags/fmt.tld" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css">
<script type="text/javascript" src="<%=path%>/js/order/addOrUpdate.js"></script>
<link rel="stylesheet" href="css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>/css/popUpDiv.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css">
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<title></title>

<style type="text/css">
        a{text-decoration:underline;}
</style>

<script language="Javascript1.1" src="../../js/avit.js"></script>
<script type="text/javascript">

var selPosition = null;//绑定广告位对象
var queryPositions = new Array();//按条件查询广告位数组
/**
 * 设置广告位
 */
 
 function selectPositonInfo(){
		$.ajax({
		type:"post",
		url: 'adContentMgr_getPosition.do',
		success:function(responseText){
			if(responseText!='-1'){
		     	//positions = eval(responseText);
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

/**
 * 填充广告位记录内容
 * */
function fillPositionInfo(){
	var str = "";
	for(var i =0;i<queryPositions.length;i++){
		str +="<tr><td><input type='radio' name='positionId' value='";
		str +=queryPositions[i].id;
		if(i==0){
			str +="' checked='checked";
		}
		str +="'/></td><td>";
		str +=queryPositions[i].positionName;
		str +="</td><td>";
		str += queryPositions[i].positionTypeCode;
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
		str +="</td></tr>";	
	}
	$("#positionInfo").html(str);
}


/**
 * 显示广告位选择层
 */
function showPosition(){
    selectPositonInfo();
	var position = $("input[name='sel_postion_id']");
	if(selPosition!=null){
		for(var i = 0;i<position.length;i++){
			if(position[i].value==selPosition.id){
				position[i].checked="checked";
			}else{
				position[i].checked="";
			}
		}
	}	
	showSelectDiv('positionDiv');
}

/**
 * 选择广告位
 * */
function selectPosition(){
	var positionIds = $("input[name='positionId']");
	for(var i = 0;i<positionIds.length;i++){
		if(positionIds[i].checked){
			if(selPosition==null||selPosition.id!=queryPositions[i].id){
				selPosition = queryPositions[i];
				$("#sel_postion_id_str").val(selPosition.positionName);
				$("#sel_postion_id").val(positionIds[i].value);
				//emptyExceptPosition();
			}
			break;
		}
	}
	closeSelectDiv('positionDiv');
}

/**
 * 弹出策略选择层
 */
function showSelectDiv(divId){
	$('#'+divId).show();
	$('#bg').show();
	$('#popIframe').show();
}
/**
 * 关闭策略选择层
 */
function closeSelectDiv(divId){
	$('#'+divId).hide();
	$('#bg').hide();
	$('#popIframe').hide();
}
   
	//批量删除
	function goDeleteAbatch(){
		var ids = "";
		var  resourceArr = document.getElementsByName("ids");
		for(var i=0; i < resourceArr.length;i++ ){
			if(resourceArr[i].checked){
				ids+= resourceArr[i].value +",";
			}
	    }
	    if(ids==""){
	    	alert("您没有选择要删除素材，请确认后再操作！");
	    	return;
	    }
	    
	    if(ids != ""){
	       deleteMeteraInfo(ids);
	    }else{
	      alert("你还没有选中！");
	    }
	}

	function deleteMeteraInfo(ids){
		$.ajax( {
			type : 'post',
			url : 'adContentMgr_deleteResourceInfo.do',
			data : 'ids=' + ids +'&date=' + new Date(),
			success : function(msg) {
				var ss = eval(msg);
				 if(ss.result == 8){
				 	alert("已经上线和已删除状态的素材不能执行删除操作，请确认之后再操作！");
				 	return;
				 }else{
				 	var a = window.confirm("您确定要删除此条素材吗？");
				 	if(a==1){
				 		deleteResource(ids);
				 	}
				 }
			}	
		});
	}


	function deleteResource(ids){
		$.ajax( {
			type : 'post',
			url : 'adContentMgr_deleteResource.do',
			data : 'ids=' + ids +'&date=' + new Date(),
			success : function(msg) {
				var ss = eval(msg);
				 if(ss != null){
				    window.location.href="adContentMgr_list.do";
				 }
			}	
		});
	}
	


	
	/** 复选框全选 */
	function checkAll(form) {
		for ( var i = 0; i < form.elements.length; i++) {
			var e = form.elements[i];
			if (e.Name != "chkAll")
				e.checked = form.chkAll.checked;
		}
	}
	
	
	

	function test(){
		 window.location.href="adCustomerMgr_getAllCustomer.do";
	}

	function go(){
	
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
function addContent(){
			window.location.href="<%=path %>/material/toUploaldFile.do";
			//window.showModalDialog("adMetaOpenShow.jsp",window,"status:no;dialogHeight:210px;dialogWidth:360px;help:no");
	}

$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});
</script>



</head>

<body class="mainBody">
<form id="form" action="adContentMgr_list.do" method="post" id="adContentMgr_list" name="adContentMgr_list">

	<div class="search">
		<div class="path">首页 sssss>> 广告资产管理 >> 资产维护</div>
		<div class="searchContent" >
			<table cellspacing="1" class="searchList">
				<tr class="title">
  			        <td>查询条件</td>
				</tr>
				<tr>
			    <td class="searchCriteria">
			      <span>资产名称：</span>
			      <input style="width:100px" type="text"  name="resource.resourceName" value="${resourceName}"/>			    
			      <span>资产类型：</span>
				    <select style="width:100px" name="resource.resourceType">
					   <option value="10">全部</option>
					   <option value="0">图片</option>
					   <option value="1">视频</option>
					   <option value="2">文字</option>
				    </select>
			      <span>资产分类：</span>
			        <select style="width:100px" name="resource.category">
					   <option value="10">全部</option>
					   <option value="0">公益</option>
					   <option value="1">商用</option>
				    </select>
				  <span>合同号：</span>
				  <input type="text" style="width:100px" name="resource.contractNumberStr" value="${contractNumber}" />			  
			  	</td>
			  	</tr>
			  	<!-- 
			  	<tr>
			  	<td class="searchCriteria">
			  	<span>创建时间：</span>
				  <input id="startTime" readOnly="true" name="resource.createTimeA"  style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/> 
				<span>至</span>
				  <input id="endTime"  readOnly="true" name="resource.createTimeB" style="width:100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			  	<input type="button" value="查询" onclick="javascript:go();" class="btn"/>
			  	</td>
 				</tr>
			  	 -->
			  	 <tr>
    <td class="searchCriteria">
      <span>广告位：</span>
      <!-- 
      <input type="hidden" name="resource.userId" value="${userId}" id="customerId"/>
		<input style="width:100px" type="text"  id="advertPositionName" name="resource.advertPositionName"  value="${advertPositionName}"/> 
		<input type="hidden" name="resource.advertPositionId" id="advertPositionId"/>
       -->
        
		<!-- 
		<input type="hidden" name="resource.userId" value="${userId}" id="customerId"/>
		<input type="text"  id="advertPositionName" name="resource.advertPositionName" onclick="showPosition();" value="${advertPositionName}" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
		<input type="hidden" name="resource.advertPositionId" id="advertPositionId"/>
		 -->	 		
		<input type="hidden" id="sel_postion_id"  value="" name="resource.advertPositionId"/>
		<input id="sel_postion_id_str" name="resource.advertPositionName" type="text" class="new_input_add" readonly="readonly" onclick="javascript:showPosition();"/>
     
      <span style="width: 60px;text-align:right;">创建日期：</span>
	   	<input id=startTime name="resource.createTimeA" value="${resource.createTimeA}" type="text" readonly="readonly" style="width:  100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('startTime')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="left"/>
	  <span style="width: 30px;text-align:right">至：</span>
	    <input id="endTime" name="resource.createTimeB" value="${resource.createTimeB}" type="text" readonly="readonly" style="width:  100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('endTime')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"/>
      &nbsp;&nbsp;<input type="button" value="查询" onclick="javascript:go();" class="btn"/>
     </td>
  </tr>
			  	
			</table>
			<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
			<table cellspacing="1" class="searchList" id="bm">
    		<tr class="title">    		
    			<td height="28" class="dot">
    			<input type="checkbox" name="chkAll"  onclick=checkAll(this.form) id="chkAll"/>
    			</td>
    			<td>序号</td>
    			<td>资产名称</td>
    			<td>资产类型</td>
    			<td>合同号</td>
    			<td>资产分类</td>
    			<td>广告位类型</td>
    			<td>开始时间</td>
    			<td>结束时间</td>
    			<td>状态</td>
    			<td>创建时间</td>
    		</tr>
    		<s:if test="listAdAssetResource.size==0">
			<tr>
				<td bgcolor="#FFFFFF" align="center" colspan="10"
					style="text-align: center">无记录</td>
			</tr>
			</s:if>
			<s:else>
			    <c:set var="index" value="0"/>
				<s:iterator value="listAdAssetResource" status="rowstatus" var="item">
				<tr>

				    <td align="center" height="26">
				    
				    <s:if test="state=='2'">
					<input type="checkbox" name="ids" value="${item.id}" disabled="disabled" />
				    </s:if>
				    <s:else>
				       <input type="checkbox" name="ids" value="${item.id}" onclick="checkAllBox()"/>
				    </s:else>
				    </td>
					<td align="center">${rowstatus.count}</td>
					<td align="center">
					
					<s:if test="state==2">
                          <s:property value="resourceName" />
			        </s:if>
			        <s:if test="state!=2">
			           <s:if test="resourceType==0">
			              <a href="adContentMgr_updateImageMetaRedirect.do?id= ${id} &resourceId= ${resourceId}">
			              <s:property value="resourceName" />
			              </a>
			           </s:if>
			           <s:if test="resourceType==1">
			              <a href="adContentMgr_updateVideoMetaRedirect.do?id= ${id} &resourceId= ${resourceId}">
			              <s:property value="resourceName" />
			              </a>
			           </s:if>
			           <s:if test="resourceType==2">
			              <a href="adContentMgr_updateMessageMetaRedirect.do?id= ${id} &resourceId= ${resourceId}">
			              <s:property value="resourceName" />
			              </a>
			           </s:if>
			        </s:if>

					</td>
					<td align="center"><s:if test="resourceType==0">图片</s:if>
					<s:if test="resourceType==1">视频</s:if>
					<s:if test="resourceType==2">文字</s:if>
					<s:if test="resourceType==3">问卷</s:if>
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
						<s:if test="state=='4'">已删除状态</s:if>
					</td>
					<td align="center"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/></td>
				</tr>
			<c:set var="index" value="${index+1}"/>
			</s:iterator>
			
			

			<c:if test="${index<page.pageSize}">
                <c:forEach begin="${index}" end="${page.pageSize-1}" step="1">
					<tr>
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
						<td>&nbsp;</td>
					</tr>
			    </c:forEach>
			</c:if>

			
			
		</s:else>
    		
    		<tr>
				<td height="34" colspan="11" style="background: url(images/bottom.jpg) repeat-x; text-align: left;">
					<input  type="button" value="删除" class="btn" onclick="goDeleteAbatch()"/>
					<input  type="button" value="新增" class="btn" onclick="javascript:addContent();"/>
					<div class="page">
					<a href="#">共计${page.totalPage }页</a>&nbsp;&nbsp;
					<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp;
						<c:choose>
							<c:when test="${page.pageNo==1&&page.totalPage!=1}">
								【<a href="adContentMgr_list.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_list.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
							</c:when>
							<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
								【<a href="adContentMgr_list.do?pageNo=1">首页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_list.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
							</c:when>
							<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
								【<a href="adContentMgr_list.do?pageNo=1">首页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_list.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_list.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_list.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
							</c:when>
						</c:choose>
					</div>	
				</td>
			</tr>
    		
			</table>
		</div>
	</div>
	</form>
	<!-- 
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
	 -->
 

<div id="positionDiv" class="showDiv" style="display: none;">
    <div class="searchContent">
		 <table cellspacing="1" class="searchList">
            <tr class="title">
                <td>查询条件</td>
            </tr>
            <tr>
                <td class="searchCriteria">
                <span>广告位名称：</span><input type="text" name="textfield" id="pName"/>
                    <span>广告位类型：</span>
                    <select id="pType">
			            <option value="-1">--选择广告位类型--</option>
						<c:forEach items="${positionTypes}" var="type">
							<option value="${type.id }">${type.positionTypeName }</option>
						</c:forEach>
			        </select>
                    <span>投放方式：</span>
                    <select id="pMode">
			           <option value="-1">--选择投放方式--</option>
						<c:forEach items="${positionModes}" var="mode">
							<option value="${mode.id }">${mode.name}</option>
						</c:forEach>
			        </select>
                    <input type="button" value="查询" onclick="queryPosition();" class="btn"/>
   				</td>
   			</tr>
		</table>
		<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;"></div>
        <table width="100%" cellspacing="1" class="searchList">
            <tr class="title">
              <td class="dot"></td>
				<td>广告位名称</td>
				<td>广告位类型</td>
				<td>高清/标清</td>
				<td>是否叠加</td>
				<td>是否轮询</td>
				<td>轮询个数</td>
				<td>投放方式</td>
			</tr>
			<tbody id="positionInfo"></tbody>
			<tr>
				<td colspan="8">
                    <input type="button" value="确定" class="btn" onclick="selectPosition();"/>&nbsp;&nbsp;
					<input type="button" value="取消" class="btn" onclick="closeSelectDiv('positionDiv');"/>&nbsp;&nbsp;
                </td>
			</tr>
		</table>
	</div>
</div>

<div id="bg" class="bg" style="display:none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>
</body>