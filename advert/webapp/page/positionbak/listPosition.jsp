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
<script language="javascript" type="text/javascript" src="<%=path%>/js/position/listPosition.js"></script>


<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>

<title>无标题文档</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
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
						<input name="searchPositionSubmit" id="searchPositionSubmit" type="submit" title="查看" class="b_search" value=""/>
						<input name="addPositionSubmit" id="addPositionSubmit" type="submit" title="添加" class="b_add" value=""/>
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
						<td>操作</td>
					</tr>
					<c:choose>
						<c:when test="${!empty positionList}">
							<c:forEach var="advertPosition" items="${positionList}" varStatus="status">
								<tr>
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
									<td>${advertPosition.price}</td>
									<td>
										<form action="" method="get" id="listPositionOperationForm${status.count}" name="listPositionOperationForm${status.count}">
											<input id="id" name="advertPosition.id" type="hidden" value="${advertPosition.id}"/>
											<!--<input id="characteristicIdentification" name="advertPosition.characteristicIdentification" type="hidden"  value="${advertPosition.characteristicIdentification}"/>	
											<input id="positionName" name="advertPosition.positionName" type="hidden"  value="${advertPosition.positionName}"/>	
											<input id="categoryId" name="advertPosition.categoryId" type="hidden"  value="${advertPosition.categoryId}"/>
											<input id="positionTypeId" name="advertPosition.positionTypeId" type="hidden"  value="${advertPosition.positionTypeId}"/>
											<input id="deliveryPlatform" name="advertPosition.deliveryPlatform" type="hidden"  value="${advertPosition.deliveryPlatform}"/>
											<input id="textRuleId" name="advertPosition.textRuleId" type="hidden"  value="${advertPosition.textRuleId}"/>
											<input id="questionRuleId" name="advertPosition.questionRuleId" type="hidden"  value="${advertPosition.questionRuleId}"/>
											<input id="imageRuleId" name="advertPosition.imageRuleId" type="hidden"  value="${advertPosition.imageRuleId}"/>
											<input id="questionRuleId" name="advertPosition.questionRuleId" type="hidden"  value="${advertPosition.questionRuleId}"/>
											<input id="isLoop" name="advertPosition.isLoop" type="hidden"  value="${advertPosition.isLoop}"/>
											<input id="deliveryMode" name="advertPosition.deliveryMode" type="hidden"  value="${advertPosition.deliveryMode}"/>
											<input id="isHd" name="advertPosition.isHd" type="hidden"  value="${advertPosition.isHd}"/>
											<input id="isAdd" name="advertPosition.isAdd" type="hidden"  value="${advertPosition.isAdd}"/>
											<input id="materialNumber" name="advertPosition.materialNumber" value="${advertPosition.materialNumber}" type="hidden" />		
											<input id="coordinate" name="advertPosition.coordinate" value="${advertPosition.coordinate}" type="hidden" />
											<input id="price" name="advertPosition.price" value="${advertPosition.price}" type="hidden" />	
											<input id="discount" name="advertPosition.discount" value="${advertPosition.discount}" type="hidden"  />		
											<input id="backgroundPath" name="advertPosition.backgroundPath" value="${advertPosition.backgroundPath}" type="hidden"/>				
											<input id="description" name="advertPosition.description" value="${advertPosition.description}" type="hidden"/>
											<input id="startTime" name="advertPosition.startTime" value="${advertPosition.startTime}" type="hidden"/>
											<input id="endTime" name="advertPosition.endTime" value="${advertPosition.endTime}" type="hidden"/>
											<input id="widthHeight" name="advertPosition.widthHeight" value="${advertPosition.widthHeight}" type="hidden"/>  -->
											<input name="deletePosition${status.count}" id="deletePosition${status.count}" type="button" class="button_delete" value="" title="删除" onClick="operation('deletePosition','#listPositionOperationForm${status.count}','${advertPosition.id}')" />
											<c:if test="${advertPosition.deliveryPlatform!=2}">
												<input name="modifyPosition${status.count}" id="modifyPosition${status.count}"  type="button" class="button_halt" value="" title="修改" onClick="operation('modifyPosition','#listPositionOperationForm${status.count}','${advertPosition.id}')" />
											</c:if>
											<input name="viewOccupyStatesPosition${status.count}" id="viewPosition${status.count}" type="button" class="button_start" value="" title="广告位占用情况"onClick="operation('viewOccupyStatesPosition','#listPositionOperationForm${status.count}','${advertPosition.id}')" />
											<input name="setPositionStates${status.count}" id="setPositionStates${status.count}" type="button" class="button_start" value="" title="广告位状态设置" onClick="operation('setPositionStates','#listPositionOperationForm${status.count}','${advertPosition.id}')" />
											<input name="viewPosition${status.count}" id="viewPosition${status.count}" type="button" class="button_start" value="" title="查看" onClick="operation('viewPosition','#listPositionOperationForm${status.count}','${advertPosition.id}')" />
										</form>
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
							style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: right;">
							<c:if test="${page.totalPage > 0 }">
								<a href="#">共计${page.totalPage}页</a>&nbsp;&nbsp;
								<a href="#">当前第${page.currentPage}/${page.totalPage}页</a>&nbsp;&nbsp;
								<c:choose>
									<c:when test="${page.currentPage==1&&page.totalPage!=1}">
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.currentPage+1}&positionTypeId=${positionTypeId}">下一页</a>】
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.totalPage}&positionTypeId=${positionTypeId}">末页</a>】
									</c:when>
									<c:when test="${page.currentPage==page.totalPage&&page.totalPage!=1 }">
										【<a href="queryPositionPage.do?method=queryPage&currentPage=1&positionTypeId=${positionTypeId}">首页</a>】 
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.currentPage-1 }&positionTypeId=${positionTypeId}">上一页</a>】
									</c:when>
									<c:when test="${page.currentPage>1&&page.currentPage<page.totalPage}">
										【<a href="queryPositionPage.do?method=queryPage&currentPage=1&positionTypeId=${positionTypeId}">首页</a>】 
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.currentPage-1}&positionTypeId=${positionTypeId}">上一页</a>】
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.currentPage+1}&positionTypeId=${positionTypeId}">下一页</a>】
										【<a href="queryPositionPage.do?method=queryPage&currentPage=${page.totalPage}&positionTypeId=${positionTypeId}">末页</a>】
									</c:when>
								</c:choose>
							</c:if>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<div id="dialog-conform" title="再次确认">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
        即将开始删除，是否继续
  </p>
</div>
</body>
</html>