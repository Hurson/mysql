<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>无线广告位详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/jquery/uploadify.css" />
<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
</head>

<body class="mainBody">
<div class="path">首页 >> 广告位管理 >> 广告位详情</div>
<form action="updateAdvertPosition.do" method="post" id="editForm">
<input id="id" name="adposition.id" type="hidden" value="${adposition.id}"/>
<div class="searchContent" >
	<table  id="bm" cellspacing="1" class="searchList" cellpadding="3">
	    <tr class="title">
	        <td colspan="6">无线广告位详情</td>
	    </tr>
	    <tr>
	    	<td align="right" width="12%">广告位名称：</td>
	        <td colspan="2">${adposition.positionName}</td>
	        <td align="right" width="12%">广告位编码：</td>
	        <td colspan="2">${adposition.positionCode}</td>
	     </tr>
		<tr class="sec">
	        <td align="right" width="12%">高清/标清：</td>
			<td colspan="2">
				<s:if test="adposition.isHD==1">高清</s:if>
	            <s:else>标清</s:else>
			</td>
			<td align="right" width="12%">素材个数：</td>
			<td colspan="2">${adposition.resourceCount}</td>
	    </tr>
		<tr class="sec">
			<td align="right" width="12%">是否视频：</td>
			<td>
				<s:if test="adposition.resourceType==2">是</s:if>
	            <s:else>否</s:else>
			</td>
			<td align="right" width="12%">是否图片：</td>
			<td>
				<s:if test="adposition.resourceType==1">是</s:if>
	            <s:else>否</s:else>
			</td>
			<td align="right" width="12%">是否字幕：</td>
			<td>
				<s:if test="adposition.resourceType==3">是</s:if>
	            <s:else>否</s:else>
			</td>
	    </tr>
	    <tr class="sec">
			<td align="right" >广告位坐标：</td>
			<td>
				<input id="coordinate" name="adposition.coordinate" value="${adposition.coordinate}" type="text" />
				<span class="required">格式：x1*y1,x2*y2,...</span>
			</td>
			<td align="right" width="12%"> 宽*高：</td>
			<td>
				<input id="widthHeight" name="adposition.domain" value="${adposition.domain}" type="text" />
				<span class="required">格式：80*80(宽高w*h)</span>
			</td>
			<td colspan="2">
				<input type="button" onclick="window.location.href='specificationAdpter.do?id=${adposition.specificationId}&type=${adposition.resourceType}'" 
				value="查看已绑规格" class="btn"/>
			</td>
	    </tr>
	    <tr>
	    	<td colspan="6" class="searchSec">
	    		<span style="margin-left:40px;" >投放策略</span>
	    		<span style="margin-left:40px;" id="selectedspan0"><input type="checkbox" name="selectedOption0"  id="selectedOption0" value="0" DISABLED ;"/>区域</span>
	    		<span style="margin-left:40px;" id="selectedspan1"><input type="checkbox" name="selectedOption1"  id="selectedOption1" value="1" DISABLED ;"/>时段</span>
	    		<span style="margin-left:40px;" id="selectedspan2"><input type="checkbox" name="selectedOption2"  id="selectedOption2" value="2" DISABLED ;"/>频道</span>
	    		<span style="margin-left:40px;" id="selectedspan3"><input type="checkbox" name="selectedOption3"  id="selectedOption3" value="3" DISABLED ;"/>文字</span>
	    	</td>
	    </tr>
	    <tr>
            <td width="12%" align="right">广告位描述：</td>
            <td colspan="5">
                <textarea id="description" name="adposition.description" cols="50" rows="3">${adposition.description }</textarea>
                <span class="required">(长度在0-120字之间)</span>
            </td> 
        </tr>
		<tr class="sec">
	        <td align="right" >
				<span>背景预览效果图：</span> 
			</td>
			<td colspan="5">
				<img id="positionViewDivImg" src="<%=path %>/${adposition.backgroundPath}" border="2" width="426px" height="240px"/>
				<input id="file_id" name="upload" type="file" class="e_input" />
				<input id="backgroundPath" name="adposition.backgroundPath" value="${adposition.backgroundPath}" type="hidden"  />
			</td>		
	    </tr>
		<tr>
			<td colspan="6" align="center">
				<input type="button" class="btn" value="确定" onclick="javascript:save();" />
				<input type="button" class="btn" value="返回" 
					onclick="window.location.href='queryDPositionPackageList.do'" />
			</td>
		</tr>
	  </table>
</div>
</form>
</body>
<script type="text/javascript">
	var positionPloy = '${adposition.ployTypes}';
    var re = new RegExp();
    re=/0/;
    if(re.test(positionPloy)){
    	document.getElementById("selectedOption0").checked=true;
    }
    re=/1/;
    if(re.test(positionPloy)){
    	document.getElementById("selectedOption1").checked=true;
    }
    re=/2/;
    if(re.test(positionPloy)){
    	document.getElementById("selectedOption2").checked=true;
    }
    re=/3/;
    if(re.test(positionPloy)){
    	document.getElementById("selectedOption3").checked=true;
    }
    
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
			var mutilCoordinates = $$("coordinate").value.split(",");
			var mutilCoordinateSize = mutilCoordinates.length;
			for(var i = 0; i < mutilCoordinateSize; i++){
				var coordinates = mutilCoordinates[i].split("*");
				if(coordinates.length != 2 || !isNumber(parseInt(coordinates[0])) || !isNumber(parseInt(coordinates[1]))){
					alert("广告位坐标格式不正确！");
					$$("coordinate").focus();
		    		return false;
				}
			 }
		}
			
/*
		if($$("coordinate").value.length>20){
			alert("广告位坐标必须小于20个字节！");
			$$("coordinate").focus();
    		return false;
		}
 */
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
</html>