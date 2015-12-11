<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>子广告位详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/jquery/uploadify.css" />
<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
</head>
<script type="text/javascript">
    function viewSpecification(id){
    	var url = "querySpecification.do?id="+id;
        window.showModalDialog(url, "", "dialogHeight=480px;dialogWidth=1000pxpx;center=1;resizable=0;status=0;");
    }

    $(function(){   
        $("#file_id").uploadify({
    		'uploader':'<%=path%>/js/jquery/upload/image/uploadify.swf',
    		'script':'uploadBackGroundImage.do',
    		'cancelImg':'<%=path%>/js/jquery/upload/image/cancel.png',
    		'folder':'/uploadFiles',
    		'queueID':'fileQueue',
    		'buttonImg':'<%=path%>/js/jquery/upload/image/uploadify.jpg',
    		'fileDataName': 'backgroundImage',
    		'auto':true,
    		'multi':false,
    		'fileExt':'*.png;*.jpg',
    		'fileDesc':'*.png;*.jpg',
    		'displayData':'speed',
    		'width':'76',
    		'height':'23',
    		'onSelect': function (event, queueID, fileObj){ 
    			$("#file_id").uploadifySettings('script','uploadBackGroundImage.do'); 
    		 } ,
    		'onComplete':function(event,queueID,fileObj,response,data){
    			var json = eval('(' + response + ')');
    			if(json!=null){
    				if(json.result=='true'){
    					$("#positionViewDivImg").attr("src","<%=path%>/"+json.filepath);
			            $("#positionViewDivImg").show();
			            $("#backgroundPath").val(json.filepath);	   				   						   						
					}else{
						alert('背景图片上传失败');
					}
    			}	
    		}
    	});
    });

	function save() {
		if (!validateBeforeSubmit()) {
	        return;
	    }
		$("#editForm").submit();
	}
	
	//表单验证
	function validateBeforeSubmit(){

		if(isEmpty($$("coordinate").value)){
			alert("广告位坐标不能为空！");
			$$("coordinate").focus();
    		return false;
		}else{
			var coordinates = $$("coordinate").value.split("*");
			if(coordinates.length != 2 || !isNumber(coordinates[0]) || !isNumber(coordinates[1])){
				alert("广告位坐标格式不正确！");
				$$("coordinate").focus();
	    		return false;
			}
		}

		if($$("coordinate").value.length>20){
			alert("广告位坐标必须小于20个字节！");
			$$("coordinate").focus();
    		return false;
		}

		if(!isEmpty($$("widthHeight").value)){
			var size = $$("widthHeight").value.split("*");
			if(size.length != 2 || !isNumber(size[0]) || !isNumber(size[1])){
				alert("广告位宽高格式不正确！");
				$$("widthHeight").focus();
	    		return false;
			}
			
			if($$("widthHeight").value.length>20){
				alert("广告位宽高必须小于20个字节！");
				$$("widthHeight").focus();
	    		return false;
		    }
		}

		var description = $.trim($("#description").val());
		if(description!=''){
			if(description.length>120){
				alert("广告位描述长度必须在0-120字之间！");
				$("#description").focus();
				return false;
			}
		}

		if(isEmpty($$("backgroundPath").value)){
			alert("广告位背景图片不能为空！");
    		return false;
		}
		
		return true;
	}
</script>
<body class="mainBody">
<div class="path">首页 >> 广告位管理 >> 子广告位详情</div>
<form action="updateAdvertPosition.do" method="post" id="editForm">
<input id="id" name="advertPosition.id" type="hidden" value="${advertPosition.id}"/>
<div class="searchContent" >
	<table  id="bm" cellspacing="1" class="searchList" cellpadding="3">
	    <tr class="title">
	        <td colspan="6">子广告位详情</td>
	    </tr>
	    <tr>
	    	<td align="right" width="12%">广告位名称：</td>
	        <td>${advertPosition.positionPackageName}</td>
	        <td align="right" width="12%">子广告位名称：</td>
	        <td>${advertPosition.positionName}</td>
	        <td align="right" width="12%">子广告位编码：</td>
	        <td>${advertPosition.positionCode}</td>
	     </tr>
		<tr class="sec">
	        <td align="right" width="12%">高清/标清：</td>
			<td>
				<s:if test="advertPosition.isHD==1">高清</s:if>
	            <s:else>标清</s:else>
			</td>
			<td align="right" width="12%">投放方式：</td>
			<td>
				<s:if test="advertPosition.deliveryMode==1">请求式</s:if>
	            <s:else>投放式</s:else>
			</td>
			<td align="right" width="12%">是否叠加：</td>
			<td>
				<s:if test="advertPosition.isAdd==1">是</s:if>
	            <s:else>否</s:else>
			</td>
	    </tr>
		<tr>
	        <td align="right" width="12%">是否轮询：</td>
			<td>
				<s:if test="advertPosition.isLoop==1">是</s:if>
	            <s:else>否</s:else>
			</td>
			<td align="right" width="12%">轮询个数：</td>
			<td>${advertPosition.loopCount}</td>
			<td align="right" width="12%">是否全时段：</td>
			<td>
				<s:if test="advertPosition.isAllTime==1">是</s:if>
	            <s:else>否</s:else>
			</td>
	    </tr>
		<tr class="sec">
			<td align="right" width="12%">是否视频：</td>
			<td>
				<s:if test="advertPosition.isVideo==1">是</s:if>
	            <s:else>否</s:else>
			</td>
			<td align="right" width="12%">是否图片：</td>
			<td>
				<s:if test="advertPosition.isImage==1">是</s:if>
	            <s:else>否</s:else>
			</td>
			<td align="right" width="12%">是否字幕：</td>
			<td>
				<s:if test="advertPosition.isText==1">是</s:if>
	            <s:else>否</s:else>
			</td>
	    </tr>
	    <tr>
			<td align="right" width="12%">是否问卷：</td>
			<td>
				<s:if test="advertPosition.isQuestion==1">是</s:if>
	            <s:else>否</s:else>
			</td>
			<td align="right" width="12%">是否区域：</td>
			<td>
				<s:if test="advertPosition.isArea==1">是</s:if>
	            <s:else>否</s:else>
			</td>
			<td align="right" width="12%">是否频道：</td>
			<td>
				<s:if test="advertPosition.isChannel==1">是</s:if>
	            <s:else>否</s:else>
			</td>
	    </tr>
	    <tr class="sec">
			<td align="right" width="12%">是否频率：</td>
			<td>
				<s:if test="advertPosition.isFreq==1">是</s:if>
	            <s:else>否</s:else>
			</td>
			<td align="right" width="12%">是否回看频道：</td>
			<td>
				<s:if test="advertPosition.isLookback==1">是</s:if>
	            <s:else>否</s:else>
			</td>
			<td align="right" width="12%">是否回放频道：</td>
			<td>
				<s:if test="advertPosition.isPlayback==1">是</s:if>
	            <s:else>否</s:else>
			</td>
	    </tr>
	    <tr>
			<td align="right" width="12%">是否CPS特征值：</td>
			<td>
				<s:if test="advertPosition.isCharacteristic==1">是（CPS广告位）</s:if>
	            <s:else>否（非CPS广告位）</s:else>
			</td>
			<td align="right" width="12%"> 价    格：</td>
			<td>${advertPosition.price}</td>
			<td align="right" width="12%">折扣：</td>
			<td>${advertPosition.discount}</td>
	    </tr>
	    <tr class="sec">
			<td align="right" >广告位坐标：</td>
			<td>
				<input id="coordinate" name="advertPosition.coordinate" value="${advertPosition.coordinate}" type="text" />
				<span class="required">格式：80*80(坐标x*y)</span>
			</td>
			<td align="right" width="12%"> 宽*高：</td>
			<td>
				<input id="widthHeight" name="advertPosition.widthHeight" value="${advertPosition.widthHeight}" type="text" />
				<span class="required">格式：80*80(宽高w*h)</span>
			</td>
			<td colspan="2">
				<input type="button" onclick="window.location.href='querySpecification.do?id=${advertPosition.id}'" 
				value="查看已绑规格" class="btn"/>
			</td>
	    </tr>
	    <tr>
            <td width="12%" align="right">子广告位描述：</td>
            <td colspan="5">
                <textarea id="description" name="advertPosition.description" cols="50" rows="3">${advertPosition.description }</textarea>
                <span class="required">(长度在0-120字之间)</span>
            </td> 
        </tr>
		<tr class="sec">
	        <td align="right" >
				<span>背景预览效果图：</span> 
			</td>
			<td colspan="5">
				<img id="positionViewDivImg" src="<%=path %>/${advertPosition.backgroundPath}" border="2" width="426px" height="240px"/>
				<input id="file_id" name="upload" type="file" class="e_input" />
				<input id="backgroundPath" name="advertPosition.backgroundPath" value="${advertPosition.backgroundPath}" type="hidden"  />
			</td>		
	    </tr>
		<tr>
			<td colspan="6" align="center">
				<input type="button" class="btn" value="确定" onclick="javascript:save();" />
				<input type="button" class="btn" value="返回" 
					onclick="window.location.href='getPositionPackage.do?id=${advertPosition.positionPackageId}'" />
			</td>
		</tr>
	  </table>
</div>
</form>
</body>
</html>