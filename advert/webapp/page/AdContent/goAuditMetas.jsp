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
<link rel="stylesheet" href="<%=basePath%>/css/popUpDiv.css" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/js.js"></script>


<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>

<script language="javascript" type="text/javascript" src="<%=path%>/js/contract/listContract.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>

<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css">



<title>审核素材</title>

<script language="javascript" type="text/javascript">

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
					var str = "<table cellpadding='0' cellspacing='1' width='100%' class='tablea' height='272px'>";
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
						str += "'id='resourceId'/><td class='td_labelXdiv' rowspan='5'>图片预览：</td>"
						str +="<td class='td_inputXdiv' rowspan='5'><img src='";
						str +=ss[i].TemporaryFilePath;
						str +="'height='200px' width='255px'/></td></tr>"; 
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
					var str = "<table cellpadding='0' cellspacing='1' width='100%' class='tablea' height='272px'>";
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
						str += "'id='resourceId'/><td class='td_labelXdiv' rowspan='5'>视频内容：</td>"
						str +="<td class='td_inputXdiv' rowspan='5'><font style='color:red;'>仅支持IE浏览器!如需播放，请单击开始按钮！<embed src='";
						str +=ss[i].TemporaryFilePath;
						str +="'height='200px' width='255px' autostart=false  loop=true type='application/x-vlc-plugin' version='VideoLAN.VLCPlugin.2' pluginspage='http://www.videolan.org'/></td></tr>"; 
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
					var str = "<table cellpadding='0' cellspacing='1' width='100%' class='tablea' height='272px'>";
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



	//审核通过方法
	function goAuditMetas(id,type,resourceId,state){
	
		
		if(state == '4'){
			//图片
    		if(type == 0){
    			var a = window.confirm("您确定要删除该条图片记录吗？");
    			if(a==1){
		    		$.ajax( {
						type : 'post',
						url : 'adContentMgr_deleteImageMetaReal.do',
						data : 'id=' + id + '&resourceId='+resourceId+'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_listAuditMetas.do";
							}
						}	
					});
				}	
    		//视频
    		}else if(type == 1){
    		  var a = window.confirm("您确定要删除该条视频记录吗？");
    		  	if(a==1){
		    		 $.ajax( {
						type : 'post',
						url : 'adContentMgr_deleteVideoMetaReal.do',
						data : 'id=' + id +'&resourceId='+resourceId+ '&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_listAuditMetas.do"
							}
						}	
					});
				}
    		 //消息
    		}else if(type == 2){
    		  var a = window.confirm("您确定要删除该条消息记录吗？");
    		  if(a==1){
		    	  $.ajax( {
						type : 'post',
						url : 'adContentMgr_deleteMessageMetaReal.do',
						data : 'id='+id +'&resourceId='+resourceId+'&date=' + new Date(),
						success : function(msg) {
							if(msg != null){
								window.location.href="adContentMgr_listAuditMetas.do"
							}
						}	
					});
				}
			}
		}else{
			//图片
			if(type == 0){
				$.ajax({
				type:"post",
				url: 'adContentMgr_insertImageRealMeta2.do',
				data : 'id=' + id +'&resourceId=' + resourceId +'&date=' + new Date(),
				success:function(responseText){
					var ss = eval(responseText);
					var str = "<table cellpadding='0' cellspacing='1' width='100%' class='tablea' height='272px'>";
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
						str += "'id='resourceId'/><td class='td_labelXdiv' rowspan='5'>图片预览：</td>"
						str +="<td class='td_inputXdiv' rowspan='5'><img src='";
						str +=ss[i].TemporaryFilePath;
						str +="'height='200px' width='255px'/></td></tr>"; 
						str +="<tr><td class='td_labelXdiv'>资产类型：</td>"
						str +="<td class='td_inputXdiv'>";
						str +=ss[i].resourceTypeName;
						str +="</td></tr>";
						str +="<tr><td class='td_labelXdiv'>内容分类：</td>"
						str +="<td class='td_inputXdiv'>";
						str +=ss[i].categoryName;
						str +="</td></tr>"
						str +="<tr><td class='td_labelXdiv'>状态：</td><td class='td_inputXdiv'><font style='color:red;'>审核通过</font></td></tr>"
						str += "<tr><td class='td_labelXdiv'>审核意见：</td>";
						str +="<td class='td_inputXdiv'><textarea id='examinationOpintions'  rows='6' cols='25' value='${resource.examinationOpintions}' name='resource.examinationOpintions'>${resource.examinationOpintions}</textarea>";
						str +="</td></tr>";
					}
					str +="</table>"
					$("#materialInfo").html(str);
					showDiv();
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
					var str = "<table cellpadding='0' cellspacing='1' width='100%' class='tablea' height='272px'>";
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
						str += "'id='resourceId'/><td class='td_labelXdiv' rowspan='5'>视频内容：</td>"
						str +="<td class='td_inputXdiv' rowspan='5'><font style='color:red;'>仅支持IE浏览器!如需播放，请单击开始按钮！<embed src='";
						str +=ss[i].TemporaryFilePath;
						str +="'height='200px' width='255px' autostart=false  loop=true type='application/x-vlc-plugin' version='VideoLAN.VLCPlugin.2' pluginspage='http://www.videolan.org'/></td></tr>"; 
						str +="<tr><td class='td_labelXdiv'>资产类型：</td>"
						str +="<td class='td_inputXdiv'>";
						str +=ss[i].resourceTypeName;
						str +="</td></tr>";
						str +="<tr><td class='td_labelXdiv'>内容分类：</td>"
						str +="<td class='td_inputXdiv'>";
						str +=ss[i].categoryName;
						str +="</td></tr>"
						str +="<tr><td class='td_labelXdiv'>状态：</td><td class='td_inputXdiv'><font style='color:red;'>审核通过</font></td></tr>"
						str += "<tr><td class='td_labelXdiv'>审核意见：</td>";
						str +="<td class='td_inputXdiv'><textarea id='examinationOpintions'  rows='6' cols='25' value='${resource.examinationOpintions}' name='resource.examinationOpintions'>${resource.examinationOpintions}</textarea>";
						str +="</td></tr>";
					}
					
					    str +="</table>"
					$("#materialInfo").html(str);
					showDiv();
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
					var str = "<table cellpadding='0' cellspacing='1' width='100%' class='tablea' height='272px'>";
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
						str +="<tr><td class='td_labelXdiv'>状态：</td><td class='td_inputXdiv'><font style='color:red;'>审核通过</font></td></tr>"
						str += "<tr><td class='td_labelXdiv'>审核意见：</td>";
						str +="<td class='td_inputXdiv'><textarea id='examinationOpintions'  rows='6' cols='25' value='${resource.examinationOpintions}' name='resource.examinationOpintions'>${resource.examinationOpintions}</textarea>";
						str +="</td></tr>";
					}
					str +="</table>"
					$("#materialInfo").html(str);
					showDiv();
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
		
	}

	//审核素材不通过方法
	function noAuditMetasPass(id,type,resourceId,state){
	
		if(state == '4'){
			alert("此素材删除待审核状态不通过！");
			return;
		}else{
		  
		//图片
		if(type == 0){
		       $.ajax({
				type:"post",
				url: 'adContentMgr_insertImageRealMeta2.do',
				data : 'id=' + id +'&resourceId=' + resourceId +'&date=' + new Date(),
				success:function(responseText){
					var ss = eval(responseText);
					var str = "<table cellpadding='0' cellspacing='1' width='100%' class='tablea' height='272px'>";
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
						//资源ID
						str +="<input type='hidden' value='";
						str += ss[i].resourceId;
						str += "'id='resourceId'/><td class='td_labelXdiv' rowspan='5'>图片预览：</td>"
						str +="<td class='td_inputXdiv' rowspan='5'><img src='";
						str +=ss[i].TemporaryFilePath;
						str +="'height='200px' width='255px'/></td></tr>"; 
						str +="<tr><td class='td_labelXdiv'>资产类型：</td>"
						str +="<td class='td_inputXdiv'>";
						str +=ss[i].resourceTypeName;
						str +="</td></tr>";
						str +="<tr><td class='td_labelXdiv'>内容分类：</td>"
						str +="<td class='td_inputXdiv'>";
						str +=ss[i].categoryName;
						str +="</td></tr>"
						str +="<tr><td class='td_labelXdiv'>状态：</td><td class='td_inputXdiv'><font style='color:red;'>审核不通过</font></td></tr>"
						str += "<tr><td class='td_labelXdiv'>审核意见：</td>";
						str +="<td class='td_inputXdiv'><textarea id='examinationOpintions'  rows='6' cols='25' value='${resource.examinationOpintions}' name='resource.examinationOpintions'>${resource.examinationOpintions}</textarea>";
						str +="</td></tr>";
					}
					str +="</table>"
					$("#noMaterialInfo").html(str);
					showDivNo();
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
					var str = "<table cellpadding='0' cellspacing='1' width='100%' class='tablea' height='272px'>";
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
						//资源ID
						str +="<input type='hidden' value='";
						str += ss[i].resourceId;  
						str += "'id='resourceId'/><td class='td_labelXdiv' rowspan='5'>视频内容：</td>"
						str +="<td class='td_inputXdiv' rowspan='5'><font style='color:red;'>仅支持IE浏览器！如需播放，请单击开始按钮</font><embed src='";
						str +=ss[i].TemporaryFilePath;
						str +="'height='200px' width='255px' autostart=false  loop=true type='application/x-vlc-plugin' version='VideoLAN.VLCPlugin.2' pluginspage='http://www.videolan.org'/></td></tr>"; 
						str +="<tr><td class='td_labelXdiv'>资产类型：</td>"
						str +="<td class='td_inputXdiv'>";
						str +=ss[i].resourceTypeName;
						str +="</td></tr>";
						str +="<tr><td class='td_labelXdiv'>内容分类：</td>"
						str +="<td class='td_inputXdiv'>";
						str +=ss[i].categoryName;
						str +="</td></tr>"
						str +="<tr><td class='td_labelXdiv'>状态：</td><td class='td_inputXdiv'><font style='color:red;'>审核不通过</font></td></tr>"
						str += "<tr><td class='td_labelXdiv'>审核意见：</td>";
						str +="<td class='td_inputXdiv'><textarea id='examinationOpintions'  rows='6' cols='25' value='${resource.examinationOpintions}' name='resource.examinationOpintions'>${resource.examinationOpintions}</textarea>";
						str +="</td></tr>";
					}
				str +="</table>"
				$("#noMaterialInfo").html(str);
				showDivNo();
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
					var str = "<table cellpadding='0' cellspacing='1' width='100%' class='tablea' height='272px'>";
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
						str +="<tr><td class='td_labelXdiv'>状态：</td><td class='td_inputXdiv'><font style='color:red;'>审核不通过</font></td></tr>"
						str += "<tr><td class='td_labelXdiv'>审核意见：</td>";
						str +="<td class='td_inputXdiv'><textarea id='examinationOpintions'  rows='6' cols='25'  name='resource.examinationOpintions'>${resource.examinationOpintions}</textarea>";
						str +="</td></tr>";
					}
					str +="</table>"
					$("#noMaterialInfo").html(str);
					showDivNo();
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
	
	}


	//显示审核不通过
	function showDivNo(){
			$('#noMaterialDiv').show("slow");
			$('#bg').show();
			$('#popIframe').show();
		}

 	     //显示审核通过
		function showAuditDiv(){
			$('#goMaterialDiv').show("slow");
			$('#bg').show();
			$('#popIframe').show();
		}


	    //显示审核通过
		function showDiv(){
			$('#materialDiv').show("slow");
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
		
		
		//关闭审核通过弹出层
		function closeSelectDiv(){
		    $('#materialDiv').hide();
		  	$('#bg').hide();
		  	$('#popIframe').hide();
		  	return;
		}

		//关闭审核未通过弹出层
		function noCloseSelectDiv(){
		    $('#noMaterialDiv').hide();
		  	$('#bg').hide();
		  	$('#popIframe').hide();
		  	return;
		}


	   //提交审核不通过意见
	   function noSubmitOpintions(){
	   
		    var resourceType = document.getElementById("resourceType").value;
		   	var examinationOpintions = document.getElementById("examinationOpintions").value;
		    var id = document.getElementById("id").value;
		    var resourceId = document.getElementById("resourceId").value;
		    
	   		//图片
	   		if(resourceType == 0){
	   			alert("该图片素材没有通过审核，确认请按确定按钮！");
				window.location.href="adContentMgr_noAuditImageMetasPass.do?id= "+id+" "+"&resourceId= "+resourceId+" "+"&examinationOpintions= "+examinationOpintions+" ";	   		
	   			noCloseSelectDiv();
	   		//视频
	   		}else if(resourceType == 1){
	   			alert("该视频素材没有通过审核，确认请按确定按钮！");
				window.location.href="adContentMgr_noAuditVideoMetasPass.do?id= "+id+" "+"&resourceId= "+resourceId+" "+"&examinationOpintions= "+examinationOpintions+" ";	   		
	   			noCloseSelectDiv();
	   		//文字
	   		}else if(resourceType == 2){
	   			alert("该文字素材没有通过审核，确认请按确定按钮！");
	   			window.location.href="adContentMgr_noAuditMessageMetasPass.do?id= "+id+" "+"&resourceId= "+resourceId+" "+"&examinationOpintions= "+examinationOpintions+" ";
	   			noCloseSelectDiv();
	   		}else{
				alert("没有找到你要审核的素材类型……");
				noCloseSelectDiv();	   		
	   		}
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

	   //提交审核通过意见
	   function submitOpintions(){
	   
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
				closeSelectDiv();
			//添加视频审核信息
			}else if(resourceType == 1){
				alert("视频素材通过审核，确认请按确定按钮！");
				window.location.href="adContentMgr_insertGoAuditVideoMetas.do?id= "+id+" "+"&resourceId= "+resourceId+" "+"&examinationOpintions= "+examinationOpintions+" "+"&clientCode= "+clientCode+" "+"&contractNumber= "+contractNumber;
				closeSelectDiv();
			//添加文字审核信息
			}else if(resourceType == 2){
		        alert("文字素材通过审核，确认请按确定按钮！");
				window.location.href="adContentMgr_insertGoAuditMessageMetas.do?id= "+id+" "+"&resourceId= "+resourceId+" "+"&examinationOpintions= "+examinationOpintions+" "+"&clientCode= "+clientCode+" "+"&contractNumber= "+contractNumber;;
				closeSelectDiv();
			}else{
				alert("抱歉，没有你要选择的类型");
				closeSelectDiv();
			}
		 
	   }

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


    	//取消审核素材		
    	function goBack(){
    		window.location.href="adContentMgr_list.do";
    	}
    </script>
<script>
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
	<div id="goMaterialDiv" class="showDiv" style="display:none">
	<table cellpadding="0" cellspacing="0" style="margin-top: 0; width: 760px; height: 336px; border: 1px solid #cccccc; font-size:12px; background-color:#cccccc;">
		<tr class="list_title_x">
			<td style="border: 0; padding-left:5px;" align="center">素材审核</td>
		    <td style="border: 0;" align="right"></td>
		</tr>
		<tr>
			<td colspan='2' valign="top">
				<div id="goMaterialInfo">
				
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="td_bottom">
				<div class="buttons">
				<!--<a href="javascript:submitOpintions()">确认</a> -->
					<a href="javascript:passAudit()">审核通过</a>
					<a href="javascript:noPassAudit()">审核不通过</a>
					<a href="javascript:closeAuditDiv()">返回</a>
				</div>		
			</td>
		</tr>
	</table>
</div>

<div id="materialDiv" class="showDiv" style="display:none">
	<table cellpadding="0" cellspacing="0" style="margin-top: 0; width: 760px; height: 336px; border: 1px solid #cccccc; font-size:12px; background-color:#cccccc;">
		<tr class="list_title_x">
			<td style="border: 0; padding-left:5px;" align="center">素材审核</td>
		    <td style="border: 0;" align="right"></td>
		</tr>
		<tr>
			<td colspan='2' valign="top">
				<div id="materialInfo">
				
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="td_bottom">
				<div class="buttons">
					<a href="javascript:submitOpintions()">确认</a>
					<a href="javascript:closeSelectDiv()">返回</a>
				</div>		
			</td>
		</tr>
	</table>
</div> 
  
  <div id="noMaterialDiv" class="showDiv" style="display:none">
	<table cellpadding="0" cellspacing="0" style="margin-top: 0; width: 760px; height: 336px; border: 1px solid #cccccc; font-size:12px;background-color:#cccccc; ">
		<tr class="list_title">
			<td style="border: 0; padding-left:5px;" align="center">素材审核</td>
		    <td style="border: 0;" align="right"></td>
		</tr>
		<tr>
			<td colspan='2' valign="top">
				<div id="noMaterialInfo">
				
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="td_bottom">
				<div class="buttons">
					<a href="javascript:noSubmitOpintions()">确认</a>
					<a href="javascript:noCloseSelectDiv()">返回</a>
				</div>		
			</td>
		</tr>
	</table>
</div> 
  
<div id="bg" class="bg" style="display:none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>

<form id="form" action="adContentMgr_listAuditMetas.do" method="post" name="adContentMgr_listAuditMetas"> 
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
				<td class="formTitle" colspan="99"><span>素材审核管理</span></td>
			</tr>
			<tr>
				<td class="td_label">资产名称：</td>
				<td class="td_input"><input type="text" class="e_input" name="resource.resourceName" value="${resourceName}"onblur="this.className='e_input'" onfocus="this.className='e_inputFocus'"/></td>
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
				<td class="td_label">所属合同号：</td>
				<td class="td_input"><input type="text" name="resource.contractNumberStr" value="${contractNumber}"class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" /></td>
			</tr>
			
			<tr>
				<td class="td_label">创建时间：</td>
				<td class="td_input">
				<input class="e_inputTime" onfocus="this.className='e_inputFocusTime'" id="startTime" onblur="this.className='e_inputTime'"  onclick="WdatePicker()" readOnly="true" name="resource.createTimeA"/>~<input class="e_inputTime"  id="endTime" onfocus="this.className='e_inputFocusTime'" onblur="this.className='e_inputTime'" onclick="WdatePicker()" readOnly="true" name="resource.createTimeB" /></td>
				<td class="td_label"/>
				<td class="td_input"/>
			</tr>	
			<tr>
			<td class="formBottom" colspan="99">
			      <input name="Submit" type="button" title="查看" onfocus=blur() class="b_search" onclick="go()"/>
			 </td>     
		</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding: 4px;">
		<table cellpadding="0" cellspacing="1" width="100%" class="taba_right_list" id="bm">

			<tr>
				<td colspan="12" class="formTitle"
					style="padding-left: 8px; background: url(images/menu_righttop.png) repeat-x; text-align: left; height: 26px;"><span>广告资源审核分类列表</span></td>
			</tr>
			<tr>
			    <td  height="26px" align="center">序号</td>
				<td  height="26px" align="center">资产名称</td>
				<td  height="26px" align="center">资产类型</td>
				<td  height="26px" align="center">所属合同号</td>
				<td  height="26px" align="center">所属内容分类</td>
				<td  height="26px" align="center">有效期开始时间</td>
				<td  height="26px" align="center">有效期结束时间</td>
				<td  height="26px" align="center">状态</td>
				<td  height="26px" align="center">创建时间</td>
				<td  height="26px" align="center">操作</td>
			</tr>
			<s:if test="listAdAssetResource.size==0">
			<tr>
				<td bgcolor="#FFFFFF" align="center" colspan="12"
					style="text-align: center">无记录</td>
			</tr>
			</s:if>
			<s:else>
			    <c:set var="index" value="0"/>
				<s:iterator value="listAdAssetResource" status="rowstatus" var="item">
				<tr>
					<td align="center">${rowstatus.count}</td>
					<td align="center"><s:property value="resourceName" /></td>
					
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
					<!--<input type="button" title="通过" style="background:url(<%=basePath%>/images/1.gif); border-style:none; background-repeat:no-repeat;  width:33px; height:17px;" onclick="goAuditMetas(<s:property value='id'/>,<s:property value='resourceType'/>,<s:property value='resourceId'/>,<s:property value='state'/>)"/> 
					<input type="button" title="不通过" style="background:url(<%=basePath%>/images/2.gif); border-style:none; background-repeat:no-repeat;  width:33px; height:17px;" onclick="noAuditMetasPass(<s:property value='id'/>,<s:property value='resourceType'/>,<s:property value='resourceId'/>,<s:property value='state'/>)" />
					 -->
					
					<input type="button" title="审核" style="background:url(<%=basePath%>/images/1.gif); border-style:none; background-repeat:no-repeat;  width:33px; height:17px;" onclick="auditMetas(<s:property value='id'/>,<s:property value='resourceType'/>,<s:property value='resourceId'/>,<s:property value='state'/>)" />
										 
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
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>


