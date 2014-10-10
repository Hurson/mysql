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
	
	function go(){
		
		  var startTime = document.getElementById("startTime").value;
		  var endTime =  document.getElementById("endTime").value;
		
		  var sTime = new Date(startTime);
		  var eTime = new Date(endTime);
		
			if(startTime !="" && endTime == ""){
				alert("创建结束时间不能为空，请确认后再输入！");
				document.getElementById("startTime").value="";
				return false;
			}else if(startTime == "" && endTime != ""){
			    alert("创建开始时间不能为空，请确认后再输入！");
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
	

   
   
	//审核素材
     function auditMetas(id,type,resourceId,state){
			//图片
			if(type == 0){
				$.ajax({
				type:"post",
				url: 'adContentMgr_insertImageRealMeta2.do',
				data : 'id=' + id +'&resourceId=' + resourceId +'&date=' + new Date(),
				success:function(responseText){
					var ss = eval(responseText);
					var str = "<table class='content' cellpadding='0' cellspacing='1' width='100%'  height='350px'>";
					    str += "<tr class='title' ><td align='center' colspan='4'>资产审核</td></tr>";
					for(var i =0;i<ss.length;i++){
					    str += "<tr><td >资产名称：</td>";
						str +="<td >";
						str += ss[i].resourceName;
						str +="</td><input type='hidden' value='";
						str +=ss[i].id;
						str +="' id='id'/>";
						//资源类型ID
						str +="<input type='hidden' value='";
						str += ss[i].resourceType;
						str += "'id='resourceType'/>";
						//客户代码 
						str +="<input type='hidden' value='";
						str += ss[i].clientCode;
						str += "'id='clientCode'/>";
						//所属合同号
						str +="<input type='hidden' value='";
						str += ss[i].contractNumber;
						str += "'id='contractNumber'/>";
						
						//资源ID
						str +="<input type='hidden' value='";
						str += ss[i].resourceId;
						str += "'id='resourceId'/><td  rowspan='5'>图片预览：</td>"
						str +="<td  rowspan='5'><img src='";
						str +=ss[i].TemporaryFilePath;
						str +="'height='200px' width='255px'/></td></tr>"; 
						str +="<tr><td >资产类型：</td>"
						str +="<td >";
						str +=ss[i].resourceTypeName;
						str +="</td></tr>";
						str +="<tr><td >资产分类：</td>"
						str +="<td >";
						str +=ss[i].categoryName;
						str +="</td></tr>"
						str +="<tr><td >状态：</td><td ><font style='color:red;'>待审核</font></td></tr>"
						str += "<tr><td >审核意见：</td>";
						str +="<td ><textarea id='examinationOpintions'  rows='6' cols='25' value='${resource.examinationOpintions}' name='resource.examinationOpintions'>${resource.examinationOpintions}</textarea>";
						str +="</td></tr>";
						str +="<tr><td colspan='4' align='center'>";
						str +="<input type='button' value='通过' class='btn' onclick='javascript:passAudit();'/>";
						str +="<input type='button' value='驳回' class='btn' onclick='javascript:noPassAudit();'/>";
						str +="<input type='button' value='返回' class='btn' onclick='closeAuditDiv();'/>";
						str +="</td></tr>";
					}
					str +="</table>"
					$("#goMaterialInfo").html(str);
					showAuditDiv();
				},
				dataType:'text',
				error:function(){
					alert("服务器异常，策略加载失败，请稍后重试！");
				}
			});
		//视频
		}else if(type == 1){
			$.ajax({
				type:"post",
				url: 'adContentMgr_insertVideoRealMeta2.do',
				data : 'id=' + id +'&resourceId=' + resourceId +'&date=' + new Date(),
				success:function(responseText){
					var ss = eval(responseText);
					var str = "<table class='content' cellpadding='0' cellspacing='1' width='100%' class='tablea' height='350px'>";
					    str += "<tr class='title' ><td align='center' colspan='4'>资产审核</td></tr>";
					for(var i =0;i<ss.length;i++){
					    str += "<tr><td>资产名称：</td>";
						str +="<td >";
						str += ss[i].resourceName;
						str +="</td><input type='hidden' value='";
						str +=ss[i].id;
						str +="' id='id'/>";
						
						//资源类型ID
						str +="<input type='hidden' value='";
						str += ss[i].resourceType;
						str += "'id='resourceType'/>";
						//客户代码 
						str +="<input type='hidden' value='";
						str += ss[i].clientCode;
						str += "'id='clientCode'/>";
						//所属合同号
						str +="<input type='hidden' value='";
						str += ss[i].contractNumber;
						str += "'id='contractNumber'/>";
						
						//资源ID
						str +="<input type='hidden' value='";
						str += ss[i].resourceId;
						str += "'id='resourceId'/><td rowspan='5'>视频内容：</td>"
						str +="<td rowspan='5'><font style='color:red;'>仅支持IE浏览器!如需播放,请单击开始按钮</font><embed src='";
						str +=ss[i].TemporaryFilePath;
						str +="' height='200px' width='255px' autostart=false  loop=true type='application/x-vlc-plugin' version='VideoLAN.VLCPlugin.2' pluginspage='http://www.videolan.org'/></td></tr>"; 
						str +="<tr><td>资产类型：</td>"
						str +="<td>";
						str +=ss[i].resourceTypeName;
						str +="</td></tr>";
						str +="<tr><td>资产分类：</td>"
						str +="<td>";
						str +=ss[i].categoryName;
						str +="</td></tr>"
						str +="<tr><td >状态：</td><td ><font style='color:red;'>待审核</font></td></tr>"
						str += "<tr><td >审核意见：</td>";
						str +="<td ><textarea id='examinationOpintions'  rows='6' cols='25' value='${resource.examinationOpintions}' name='resource.examinationOpintions'>${resource.examinationOpintions}</textarea>";
						str +="</td></tr>";
						str +="<tr><td align='center' colspan='4'>";
						str +="<input type='button' value='通过' class='btn' onclick='javascript:passAudit();'/>";
						str +="<input type='button' value='驳回' class='btn' onclick='javascript:noPassAudit();'/>";
						str +="<input type='button' value='返回' class='btn' onclick='closeAuditDiv();'/>";
						str +="</td></tr>";
					}
					
					    str +="</table>"
					$("#goMaterialInfo").html(str);
					showAuditDiv();
				},
				dataType:'text',
				error:function(){
					alert("服务器异常，策略加载失败，请稍后重试！");
				}
			});
		//文字
		}else if(type == 2){
			$.ajax({
				type:"post",
				url: 'adContentMgr_insertMessageRealMeta2.do',
				data : 'id=' + id +'&resourceId=' + resourceId +'&date=' + new Date(),
				success:function(responseText){
					var ss = eval(responseText);
					var str = "<table class='content' cellpadding='0' cellspacing='1' width='100%' class='tablea' height='350px'>";
					    str += "<tr class='title' ><td align='center' colspan='4'>资产审核</td></tr>";
					for(var i =0;i<ss.length;i++){
					    str += "<tr><td class='td_labelXdiv'>资产名称：</td>";
						str +="<td class='td_inputXdiv'>";
						str += ss[i].resourceName;
						str +="</td><input type='hidden' value='";
						str +=ss[i].id;
						str +="' id='id'/>";
						//资源类型ID
						str +="<input type='hidden' value='";
						str += ss[i].resourceType;
						str += "'id='resourceType'/>";
						//客户代码 
						str +="<input type='hidden' value='";
						str += ss[i].clientCode;
						str += "'id='clientCode'/>";
						//所属合同号
						str +="<input type='hidden' value='";
						str += ss[i].contractNumber;
						str += "'id='contractNumber'/>";
						
						//资源ID
						str +="<input type='hidden' value='";
						str += ss[i].resourceId;
						str += "'id='resourceId'/><td class='td_labelXdiv' rowspan='5'>文字内容：</td>"
						str +="<td class='td_inputXdiv' rowspan='5'><textarea rows='12' cols='30' readonly='true'>";
						str +=ss[i].content;
						str +="</textarea></td></tr>"; 
						str +="<tr><td class='td_labelXdiv'>资产类型：</td>"
						str +="<td class='td_inputXdiv'>";
						str +=ss[i].resourceTypeName;
						str +="</td></tr>";
						str +="<tr><td class='td_labelXdiv'>内容分类：</td>"
						str +="<td class='td_inputXdiv'>";
						str +=ss[i].categoryName;
						str +="</td></tr>"
						str +="<tr><td class='td_labelXdiv'>状态：</td><td class='td_inputXdiv'><font style='color:red;'>待审核</font></td></tr>"
						str += "<tr><td class='td_labelXdiv'>审核意见：</td>";
						str +="<td class='td_inputXdiv'><textarea id='examinationOpintions'  rows='6' cols='25' value='${resource.examinationOpintions}' name='resource.examinationOpintions'>${resource.examinationOpintions}</textarea>";
						str +="</td></tr>";
						str +="<tr><td align='center' colspan='4'>";
						str +="<input type='button' value='通过' class='btn' onclick='javascript:passAudit();'/>";
						str +="<input type='button' value='驳回' class='btn' onclick='javascript:noPassAudit();'/>";
						str +="<input type='button' value='返回' class='btn' onclick='closeAuditDiv();'/>";
						str +="</td></tr>";
					}
					str +="</table>"
					$("#goMaterialInfo").html(str);
					showAuditDiv();
				},
				dataType:'text',
				error:function(){
					alert("服务器异常，策略加载失败，请稍后重试！");
				}
			});
			
		}else{
			alert("没有你要选择的素材类型，请确认后再输入！");
		}
		
	}   
	
	 //显示审核通过
		function showAuditDiv(){
			$('#goMaterialDiv').show("slow");
			$('#bg').show();
			$('#popIframe').show();
		}
		
	//关闭审核素材弹出层
		function closeAuditDiv(){
			 $('#goMaterialDiv').hide();
		  	$('#bg').hide();
		  	$('#popIframe').hide();
		  	return;
		}
   
    //审核通过
	function passAudit(){
	
		var resourceType = document.getElementById("resourceType").value;
	   	var examinationOpintions = document.getElementById("examinationOpintions").value;
	    var id = document.getElementById("id").value;
	    var resourceId = document.getElementById("resourceId").value;
	    var clientCode = document.getElementById("clientCode").value;
	    var contractNumber = document.getElementById("contractNumber").value;
	    
	   //添加图片审核信息
			if(resourceType == 0){
				alert("图片素材通过审核，确认请按确定按钮！");
				window.location.href="adContentMgr_insertGoAuditImageMetas.do?id= "+id+" "+"&resourceId= "+resourceId+" "+"&examinationOpintions= "+examinationOpintions+" "+"&clientCode= "+clientCode+" "+"&contractNumber= "+contractNumber;
				closeAuditDiv();
			//添加视频审核信息
			}else if(resourceType == 1){
				alert("视频素材通过审核，确认请按确定按钮！");
				window.location.href="adContentMgr_insertGoAuditVideoMetas.do?id= "+id+" "+"&resourceId= "+resourceId+" "+"&examinationOpintions= "+examinationOpintions+" "+"&clientCode= "+clientCode+" "+"&contractNumber= "+contractNumber;
				closeAuditDiv();
			//添加文字审核信息
			}else if(resourceType == 2){
		        alert("文字素材通过审核，确认请按确定按钮！");
				window.location.href="adContentMgr_insertGoAuditMessageMetas.do?id= "+id+" "+"&resourceId= "+resourceId+" "+"&examinationOpintions= "+examinationOpintions+" "+"&clientCode= "+clientCode+" "+"&contractNumber= "+contractNumber;;
				closeAuditDiv();
			}else{
				alert("抱歉，没有你要选择的类型");
				closeSelectDiv();
			}
		return ;
	}
	
	
	//审核不通过
	function noPassAudit(){
	
	    var resourceType = document.getElementById("resourceType").value;
	   	var examinationOpintions = document.getElementById("examinationOpintions").value;
	    var id = document.getElementById("id").value;
	    var resourceId = document.getElementById("resourceId").value;
		    
   		//图片
   		if(resourceType == 0){
   			alert("该图片素材没有通过审核，确认请按确定按钮！");
			window.location.href="adContentMgr_noAuditImageMetasPass.do?id= "+id+" "+"&resourceId= "+resourceId+" "+"&examinationOpintions= "+examinationOpintions+" ";	   		
   			closeAuditDiv();
   		//视频
   		}else if(resourceType == 1){
   			alert("该视频素材没有通过审核，确认请按确定按钮！");
			window.location.href="adContentMgr_noAuditVideoMetasPass.do?id= "+id+" "+"&resourceId= "+resourceId+" "+"&examinationOpintions= "+examinationOpintions+" ";	   		
   			closeAuditDiv();
   		//文字
   		}else if(resourceType == 2){
   			alert("该文字素材没有通过审核，确认请按确定按钮！");
   			window.location.href="adContentMgr_noAuditMessageMetasPass.do?id= "+id+" "+"&resourceId= "+resourceId+" "+"&examinationOpintions= "+examinationOpintions+" ";
   			closeAuditDiv();
   		}else{
			alert("没有找到你要审核的素材类型……");
			closeAuditDiv();	   		
   		}
		return;
	}
</script>



</head>

<body class="mainBody">
<form id="form" action="adContentMgr_listAuditMetas.do" method="post" name="adContentMgr_listAuditMetas">
<div id="goMaterialDiv" class="showDiv_2" style="display:none">
	<table cellpadding="0" cellspacing="0" style="margin-top: 0; width: 760px; height: 336px; border: 1px solid #cccccc; font-size:12px; background-color:#cccccc;">

		<tr>
			<td colspan='2' valign="top">
				<div id="goMaterialInfo">
				
				</div>
			</td>
		</tr>
		
	</table>
</div>

<div id="bg" class="bg" style="display:none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>

	<div class="search">
		<div class="path">首页 >> 广告资产审核 >> 资产查询</div>
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
    			<!-- 
    			<td height="28" class="dot"><input type="checkbox" name="select" onclick="javascript:selectAll("content_id");" /></td>
    			 -->   			
    			<td>序号</td>
    			<td>资产名称</td>
    			<td>资产类型</td>
    			<td>合同号</td>
    			<td>资产分类</td>
    			<td>开始时间</td>
    			<td>结束时间</td>
    			<td>状态</td>
    			<td>创建时间</td>
				<td>操作</td>
    		</tr>
    		<s:if test="listAdAssetResource.size==0">
			<tr>
				<td bgcolor="#FFFFFF" align="center" colspan="10"style="text-align: center">无记录</td>
			</tr>
			</s:if>
			<s:else>
			    <c:set var="index" value="0"/>
				<s:iterator value="listAdAssetResource" status="rowstatus" var="item">
				<tr>
				<!--
				<td><input type="checkbox" name="content_id" /></td>
				  -->			    
					<td align="center">${rowstatus.count}</td>
					<td align="center"><s:property value="resourceName" /></td>
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
					<td align="center"><fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd"/></td>
					<td align="center"><fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd"/></td>
					<td align="center">
						<s:if test="state=='0'">待审核</s:if>
						<s:if test="state=='1'">审核未通过</s:if>
						<s:if test="state=='2'">上线</s:if>
						<s:if test="state=='3'">下线</s:if>
						<s:if test="state=='4'">删除待审核状态</s:if>
						<s:if test="state=='5'">删除已审核状态</s:if>
					</td>
					<td align="center"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/></td>
					<td align="center">					
					<input type="button" title="审核" style="background:url(<%=basePath%>/images/1.gif); border-style:none; background-repeat:no-repeat;  width:33px; height:17px;" onclick="auditMetas(<s:property value='id'/>,<s:property value='resourceType'/>,<s:property value='resourceId'/>,<s:property value='state'/>)" />										 
					</td>
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
					</tr>
			</c:forEach>
			</c:if>

			
			
		</s:else>
    		

			  
			  <tr>
				<td height="34" colspan="12" style="background: url(images/bottom.jpg) repeat-x; text-align: left;">
					<!-- 
					<input type="button" value="通过" class="btn" onclick="javascript:auditArr();"/>
					<input type="button" value="驳回" class="btn" onclick="javascript:auditArr();"/>
					 -->
					
					
					<div class="page">
					<a href="#">共计${page.totalPage }页</a>&nbsp;&nbsp;
					<a href="#">当前第${page.pageNo }/${page.totalPage }页</a>&nbsp;&nbsp;
						<c:choose>
							<c:when test="${page.pageNo==1&&page.totalPage!=1}">
								【<a href="adCustomerMgr_listAuditMetas.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAuditMetas.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
							</c:when>
							<c:when test="${page.pageNo==page.totalPage&&page.totalPage!=1 }">
								【<a href="adCustomerMgr_listAuditMetas.do?pageNo=1">首页</a>】&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAuditMetas.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
							</c:when>
							<c:when test="${page.pageNo>1&&page.pageNo<page.totalPage }">
								【<a href="adCustomerMgr_listAuditMetas.do?pageNo=1">首页</a>】&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAuditMetas.do?pageNo=${page.pageNo-1 }">上一页</a>】&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAuditMetas.do?pageNo=${page.pageNo+1 }">下一页</a>】&nbsp;&nbsp;
								【<a href="adCustomerMgr_listAuditMetas.do?pageNo=${page.totalPage}">末页</a>】&nbsp;&nbsp;
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