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
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/new/main.css" />
		<link href="<%=path%>/css/new/common/dhtmlx/tree/css/dhtmlxtree.css"
			rel="stylesheet" type="text/css" media="all" />
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxcommon.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxtree.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/test.js"
			type="text/javascript">
</script>

		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/jquery-1.9.0.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/contract/listContract.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js">
</script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker-zh-CN.js">
</script>

		<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js">
</script>
		<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js">
</script>
		<script src="<%=path%>/js/new/avit.js">
</script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/position/speciCommon.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/util.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/util/tools.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script>
var id='';
var movieDesc='';
var resolution='';
var duration='';
var fileSize='';
var type='';
$(function(){  
	$("#system-dialog").hide(); 
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
    $('#queryButton').click(function(){
    	submitForm('#queryForm','queryVideoManager.do?');
    });
    $('#addButton').click(function(){
    
    	id='';
		movieDesc='';
		resolution='';
		duration='';
		fileSize='';
		type='';
    
    	easyDialog.open({
			container : {
				header : '新建',
				content : generateStruct('video','add')
			},
			overlay : false
		});
    });
});

function generateAccess(currentPage,videoDescQuery){
	var path = 'queryVideoManager.do?currentPage='+currentPage;
	if((!$.isEmptyObject(videoDescQuery))){
		$('#videoDescQuery').val(videoDescQuery);
	}
	submitForm('#queryForm',path);
}

function deleteVideo(idParam){
	if (confirm("确认要删除所选素材吗？"))
	{
	accessPath('removeVideo.do?object.id='+idParam);
		}
	else
		{
			return;
		}
}

function modify(idParam,movieDescParam,resolutionParam,durationParam,fileSizeParam,typeParam){
		//全局变量数据初始化
		id=idParam;
		movieDesc=movieDescParam;
		resolution=resolutionParam;
		duration=durationParam;
		fileSize=fileSizeParam;
		type=typeParam;
	window.location.href="addVideo.do?id="+idParam+"&movieDesc="+movieDesc+"&resolution="+resolution+"&duration="+duration+"&fileSize="+fileSize+"&type="+type;
	
	//	easyDialog.open({
	//		container : {
	//			header : '新建',
	//			content : generateStruct('video','add')
	//		},
	//		overlay : false
	//	});
}
function addPage(){
	window.location.href="addVideo.do";
}
</script>
<body class="mainBody" onload="">
		<input id="projetPath" type="hidden" value="<%=path%>" />

		<form action="queryVideoManager.do?"  id="queryForm" name="queryForm" method="post">
			<div class="search">
				<div class="path">
					<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
					首页 >> 广告位管理 >> 视频规格维护
				</div>
					<div class="searchContent">
					<table cellspacing="1" class="searchList">
						<tr class="title">
							<td colspan="9">
								查询条件
							</td>
						</tr>
						<tr>
							<td>
								视频描述：
									<input id="videoDescQuery" name="videoDescQuery" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" maxlength="80" value="${videoDescQuery}"/>		
									<input type="button" value="查询" class="btn"
									id="queryButton" />
									</td>
						</tr>
					</table>
					<div id="messageDiv"
						style="margin-top: 15px; color: red; font-size: 14px; font-weight: bold;"></div>
						<table cellspacing="1" class="searchList" id="bm">
						<tr class="title">
							<td height="26px" align="center">序号</td>
							<td>视频规格描述</td>
							<td>分辨率</td>
							<td>时长</td>
							<td>文件大小</td>
							<td>类型</td>
							<td>操作</td>
							</tr>
							<c:choose>
							<c:when test="${!empty objectList}">
								<c:forEach var="object" items="${objectList}" varStatus="status">
									<tr>
										<td align="center" height="26">${status.count}</td>
										<td>${object.movieDesc}</td>
										<td>${object.resolution}</td>
										<td>${object.duration}</td>
										<td>${object.fileSize}</td>
										<td>${object.type}</td>
										<td>
											<input name="#" id="#" type="button" class="btn" value="删除" title="删除" onClick="deleteVideo('${object.id}')" />
											<input name="#" id="#"  type="button" class="btn" value="修改" title="修改" onClick="modify('${object.id}','${object.movieDesc}','${object.resolution}','${object.duration}','${object.fileSize}','${object.type}')" />
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
							<td height="34" colspan="23">
								<input id="#" name="#"
									type="button" value="添加" class="btn" onclick="javascript:addPage();" />
								
								<c:if test="${page.totalPage>0}">
									<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
									<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
									<c:choose>
										<c:when test="${page.currentPage==1&&page.totalPage!=1}">
											【<a href="#" onclick="generateAccess('${page.currentPage+1}','${videoDescQuery}')">下一页</a>】
											【<a href="#" onclick="generateAccess('${page.totalPage}','${videoDescQuery}')">末页</a>】
										</c:when>
										<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
											【<a href="#" onclick="generateAccess('1','${videoDescQuery}')">首页</a>】 
											【<a href="#" onclick="generateAccess('${page.currentPage-1 }','${videoDescQuery}')">上一页</a>】
										</c:when>
										<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
											【<a href="#" onclick="generateAccess('1','${videoDescQuery}')">首页</a>】 
											【<a href="#" onclick="generateAccess('${page.currentPage-1}','${videoDescQuery}')">上一页</a>】
											【<a href="#" onclick="generateAccess('${page.currentPage+1}','${videoDescQuery}')">下一页</a>】
											【<a href="#" onclick="generateAccess('${page.totalPage}','${videoDescQuery}')">末页</a>】
										</c:when>
									</c:choose>
								</c:if>
							</td>
						</tr>
					</table>
									</div>
			</div>
		</form>
	</body>
</html>
					