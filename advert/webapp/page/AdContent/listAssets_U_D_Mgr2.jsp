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
	var queryPositions = new Array();//按条件查询广告位数组
	var selPosition = null;//绑定广告位对象

	
	//全选
	/** 复选框全选 */
	function checkAll(form) {
		for ( var i = 0; i < form.elements.length; i++) {
			var e = form.elements[i];
			if (e.Name != "chkAll")
				e.checked = form.chkAll.checked;
		}
		
		return;
	}
	
	//下线操作
	function goDownInfo(){
		var ids = "";
		var  resourceArr = document.getElementsByName("ids");
		for(var i=0; i < resourceArr.length;i++ ){
			if(resourceArr[i].checked){
				ids+= resourceArr[i].value +",";
			}
	    }
	    
	    if(ids==""){
	    	alert("您还没有选择要下线的素材，请确认后再操作！");
	    	return;
	    }
	    getDownResourceInfo(ids);
	    return;	
	}
	//得到信息
	function getResourceInfo(ids){
		$.ajax({
				type:'post',
				url: 'adContentMgr_getAuditResourceInfoUp.do',
				data : 'ids=' + ids + '&date=' + new Date(),
				success:function(msg){
					var ss = eval(msg);
					if(ss.result == 3){
						alert("您要上线的素材中，已经上线的素材不能再做上线处理，请确认后在操作！");
						return;
					}else if(ss.result == 4){
						var str = "<table class='content' cellpadding='0' cellspacing='1' width='100%' height='200px'>";
						    str += "<tr class='title' ><td align='center' colspan='4'>资产上线操作</td></tr>";
						    str += "<tr><td class='td_labelXdiv'>变更意见：</td>";
							str +="<td class='td_inputXdiv'><textarea id='Opintions'  rows='6' cols='25' value='${resource.examinationOpintions}' name='resource.examinationOpintions'></textarea>";
							str +="</td></tr>";				
							str +="<tr><td align='center' colspan='4'>";
						    str +="<input type='button' value='确认' class='btn' onclick='javascript:SubmitUpOpintions();'/>";
						    str +="<input type='button' value='返回' class='btn' onclick='closeUpResource();'/>";
						    str +="</td></tr>";
						    str +="</table>"
						$("#upMaterialInfo").html(str);
						showUpResource();
				   }
				},
				dataType:'json',
				error:function(){
					alert("服务器异常，策略加载失败，请稍后重试！");
				},
			});
	   }
	
	//下线操作
	function  getDownResourceInfo(ids){
		$.ajax({
				type:'post',
				url: 'adContentMgr_getAuditResourceInfoDown.do',
				data : 'ids=' + ids + '&date=' + new Date(),
				success:function(msg){
					var ss = eval(msg);
					if(ss.result == 5){
						alert("您要下线的素材中，已经下线的素材不能再做下线处理，请确认后在操作！");
						return;
					}else if(ss.result == 6){
						alert("您选择要下线的素材中，有的素材的订单还处于执行阶段，不能被下线，请确认后再操作！");
						return;
					}else if(ss.result == 4){
						var str = "<table class='content' cellpadding='0' cellspacing='1' width='100%' height='200px'>";
						    str += "<tr class='title' ><td align='center' colspan='4'>资产下线操作</td></tr>";
						    str += "<tr><td class='td_labelXdiv'>变更意见：</td>";
							str +="<td class='td_inputXdiv'><textarea id='Opintions'  rows='6' cols='25' value='${resource.examinationOpintions}' name='resource.examinationOpintions'></textarea>";
							str +="</td></tr>";
							str +="<tr><td align='center' colspan='4'>";
						    str +="<input type='button' value='确认' class='btn' onclick='javascript:SubmitDownOpintions();'/>";
						    str +="<input type='button' value='返回' class='btn' onclick='closeDownResource();'/>";
						    str +="</td></tr>";
						    str +="</table>"
						$("#downMaterialInfo").html(str);
						showDownResource();
				   }
				},
				dataType:'json',
				error:function(){
					alert("服务器异常，策略加载失败，请稍后重试！");
				},
			});
	
	}
	
	
	//填写上线素材的审核意见
	function SubmitUpOpintions(){
	
	    var UpOpintions = document.getElementById("Opintions").value;
	    
		window.location.href="adContentMgr_submitUpMetas.do?Opintions= "+UpOpintions;
	    alert("素材上线，确认请按确定按钮！");
		return;
	}
	
	
		//填写下线素材的审核意见
	function SubmitDownOpintions(){
	
	    var UpOpintions = document.getElementById("Opintions").value;
	    
		window.location.href="adContentMgr_submitDownMetas.do?Opintions= "+UpOpintions;
	 //  alert("素材上线，确认请按确定按钮！");
		return;
	}
	
	//上线操作
	function goUpInfo(){
		var ids = "";
		var  resourceArr = document.getElementsByName("ids");
		for(var i=0; i < resourceArr.length;i++ ){
			if(resourceArr[i].checked){
				ids+= resourceArr[i].value +",";
			}
	    }
	    
	    if(ids==""){
	    	alert("您还没有选择要上线的素材，请确认后再操作！");
	    	return;
	    }
	     getResourceInfo(ids);
	}
	
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
		var str = "<table class='content' cellpadding='0' cellspacing='1' width='100%' id='pDiv'>";
		    str +="<tr class='title'><td height='26px' align='center' width='5%'></td>";
		    str +="<td>广告位名称</td><td>广告位类型</td><td>高清/标清</td><td>是否叠加</td><td>是否轮询</td><td>轮询个数</td><td>投放方式</td><td>描述</td></tr>";

		for(var i =0;i<queryPositions.length;i++){
			str +="<tr";
			//if(i%2==0){
			//	str += " class='treven' "
			//}else{
			//	str += " class='trodd' "
			//}
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
		str +="<tr><td align='center' colspan='9'>";
		str +="<input type='button' value='确认' class='btn' onclick='javascript:selectPosition();'/>";
		str +="<input type='button' value='返回' class='btn' onclick='closeSelectDiv();'/>";
	    str +="</td></tr>";
		str +="</table>"
		
		$("#positionInfo").html(str);
	}
	
	
	//显示上线素材弹出层
	function showUpResource(){
		$('#upMeterialInfo').show("slow");
		$('#bg').show();
		$('#popIframe').show();
	}
	
	//显示下线素材弹出层
	function showDownResource(){
		$('#downMeterialInfo').show("slow");
		$('#bg').show();
		$('#popIframe').show();
	}
	
	
	//显示弹出层
	function showDiv(){
		    $('#position').show("slow");
			$('#bg').show();
			$('#popIframe').show();
	}
	
	//关闭上线素材弹出层
	function closeUpResource(){
		    $('#upMeterialInfo').hide();
		  	$('#bg').hide();
		  	$('#popIframe').hide();
		  	return;
	}
	//关闭下线弹出层
	function closeDownResource(){
		    $('#downMeterialInfo').hide();
		  	$('#bg').hide();
		  	$('#popIframe').hide();
		  	return;
	}
	
	
	
	//关闭弹出层
	function closeSelectDiv(){
		    $('#position').hide();
		  	$('#bg').hide();
		  	$('#popIframe').hide();
		  	return;
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
	
   
</script>



</head>

<body class="mainBody">
  
<div id="upMeterialInfo" class="showDiv_2" style="display:none">
	<table cellpadding="0" cellspacing="0" style="margin-top: 0; width: 600px; height: 200px; border: 1px solid #cccccc; font-size:12px; background-color:#cccccc;">

		<tr>
			<td colspan='2' valign="top">
				<div id="upMaterialInfo">
				
				</div>
			</td>
		</tr>
		
	</table>
</div>


  
<div id="downMeterialInfo" class="showDiv_2" style="display:none">
	<table cellpadding="0" cellspacing="0" style="margin-top: 0; width: 600px; height: 200px; border: 1px solid #cccccc; font-size:12px; background-color:#cccccc;">

		<tr>
			<td colspan='2' valign="top">
				<div id="downMaterialInfo">
				
				</div>
			</td>
		</tr>
		
	</table>
</div>

<div id="position" class="showDiv_2" style="display:none">
<table cellpadding="0" cellspacing="0" style="margin-top: 0; width: 800px; height: 900px; border: 1px solid #cccccc; font-size:12px; background-color:#cccccc;">

		<tr>
			<td colspan='9' valign="top">
				<div id="positionInfo">
				
				</div>
			</td>
		</tr>
		
	</table>

</div> 

<div id="bg" class="bg" style="display:none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>

<form  id="form" action="adContentMgr_listUD.do" method="post" id="adContentMgr_list" name="adContentMgr_list">

	<div class="search">
		<div class="path">首页 >> 广告资产管理 >> 资产上下线</div>
		<div class="searchContent" >
			<table cellspacing="1" class="searchList">
				<tr class="title">
  			        <td>查询条件</td>
				</tr>
				<tr>
			    <td class="searchCriteria">
			      <span>资产名称：</span>
			      <input type="text" name="resource.resourceName" value="${resourceName}" onblur="this.className='e_input'" onfocus="this.className='e_inputFocus'"/>			    
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
				  <span>广告位：</span>
				  <input type="text"  id="advertPositionName" name="resource.advertPositionName"  value="${advertPositionName}" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />
				  <input type="hidden" name="resource.advertPositionId" id="advertPositionId"/>
				  <span>合同号：</span>
				  <input type="text" name="resource.contractNumberStr" value="${contractNumber}" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />			  
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
      <span>状态：</span>
        <select name="resource.stateStr">
					<option value="10">全部</option>
					<option value="2">上线</option>
					<option value="3">下线</option>
		</select>  
      <span style="width: 60px;text-align:right;">创建日期：</span>
	   	<input id="startTime"name="resourceReal.createTimeA" value="${resourceReal.createTimeA}" type="text" readonly="readonly" style="width:  100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	   	<img onclick="showDate('startTime')" src="<%=path %>/js/new/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="left"/>
	  <span style="width: 30px;text-align:right">至：</span>
	    <input id="endTime" name="resourceReal.createTimeB" value="${resourceReal.createTimeB}" type="text" readonly="readonly" style="width:  100px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
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
				<td bgcolor="#FFFFFF" align="center" colspan="11"
					style="text-align: center">无记录</td>
			</tr>
			</s:if>
			<s:else>
			    <c:set var="index" value="0"/>
				<s:iterator value="listAdAssetResource" status="rowstatus" var="item">
				<tr>
				    <td>
				    <input type="checkbox" name="ids" value="${item.id}" />
				    </td>
					<td align="center">${rowstatus.count}</td>
					<td align="center">
					<s:property value="resourceName" />
					</td>
					<td align="center">
					<s:if test="resourceType==0">图片</s:if>
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
					<input  type="button" value="上线" class="btn" onclick="goUpInfo()"/>
					<input  type="button" value="下线" class="btn" onclick="goDownInfo()"/>
					<div class="page">												
					<a href="#">共计${page.totalPage }页</a>&nbsp;&nbsp;
					<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp;
						<c:choose>
							<c:when test="${page.pageNo==1&&page.totalPage!=1}">
								【<a href="adContentMgr_listUD.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_listUD.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
							</c:when>
							<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
								【<a href="adContentMgr_listUD.do?pageNo=1">首页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_listUD.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
							</c:when>
							<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
								【<a href="adContentMgr_listUD.do?pageNo=1">首页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_listUD.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_listUD.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
								【<a href="adContentMgr_listUD.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
							</c:when>
						</c:choose>

					</div>	
				</td>
			</tr>
			</table>
		</div>
	</div>
	</form>
</body>