<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path%>/css/menu_right_new1.css"
	type="text/css" />
<link rel="stylesheet" href="<%=path%>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path%>/css/popUpDiv.css" type="text/css" />
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/updateEndTime.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker-zh-CN.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>

<link rel="stylesheet" href="<%=path%>/css/jquery/jquery.ui.all.css" />
<title>订单管理</title>
<style>
.treven {
	background: #FFFFFF;
}

.trodd {
	background: #f6f6f6;
}

.ggw {
	width: 48%;
	height: 268px;
	color: #000000;
	float: left;
	border: 1px dashed #CCCCCC;
	margin-left: 20px;
}

.ggw ul {
	padding: 0px;
	margin: 0px;
}

.ggw li {
	background: #efefef;
	font-weight: bold;
	width: 99%;
	height: 30px;
}

#selPloy {
	width: 100%;
	overflow: auto
}

#selPrecise {
	width: 100%;
	overflow: auto
}

img {
	border: 0px;
}

.list_td {
	height: 27px;
	background: #fff;
	text-align: left;
	border-bottom: 1px dashed #eeeeee;
	color: #000066;
}
</style>
</head>

<body
	onload='init("${order.coordinate }",${order.isHD },${order.isLoop},"${order.positionSize}",${ployJson },${ployMaterialJson },${materialJson },"${order.fontSize }","${order.rollSpeed }","${order.fontColor }","${order.ployStartTime }","${aheadTime }");'>
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
				<td class="formTitle" colspan="99"><span>·更新订单</span></td>
			</tr>
			<tr>
				<td class="td_labelz">订单号：</td>
				<td class="td_inputz"><span class="e_input_text">${order.orderNo}</span></td>
				<td class="td_labelz">合同：</td>
				<td class="td_inputz">${order.contractCode }</td>
			</tr>
			<tr>
				<td class="td_labelz">广告位：</td>
				<td class="td_inputz">${order.positionName }</td>
				<td class="td_labelz">策略：</td>
				<td class="td_inputz">${order.ployName }</td>
			</tr>
			<tr>
				<td class="td_labelz">开始日期选择：</td>
				<td class="td_inputz">${order.startTime}</td>
				<td class="td_labelz">结束日期选择：</td>
				<td class="td_inputz"><input type="text" readonly="readonly"
					class="e_input" value="${order.endTime }"
					onfocus="this.className='e_inputFocus'" id="endDate"
					onblur="this.className='e_input'" /></td>

			</tr>
			<tr>
				<td class="td_labelz">订单描述：</td>
				<td class="td_inputz" colspan="3"><textarea
					class="e_textarea"
					disabled="disabled">${order.description }</textarea></td>

			</tr>
			<tr>
				<td colspan="99">
				<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
					<tr>
						<td colspan="6" class="yulan">
						<span style="display:block;width:500px;padding:5px;margin-left:15px;">·订单参数配置</span>
						<div class="ggw">
						<ul>
							<li>已选择策略</li>
							<div id="selPloy">
								<a href="javascript:showPloyInfo();">${order.ployName }</a> <br />
							&nbsp;&nbsp;&nbsp;&nbsp; <span id="mp0"> <c:set var="i"
								value="0" /> 
								<c:if test="${(order.isAdd==0||order.isLoop==1)&&order.isInstream==0 }">
									<a href="javascript:showPloyMaterial();">
								</c:if>
								<c:forEach items="${order.ployMaterials }" var="pm">
								<c:if test="${i>0}">,</c:if>
								<c:if
									test="${(order.isAdd==1&&order.isLoop==0)||order.isInstream!=0 }">
									<a
										href="javascript:showMaterial('${pm.path}','${pm.content}',${pm.type})">${pm.name
									}</a>
								</c:if>
								<c:if
									test="${(order.isAdd==0||order.isLoop==1)&&order.isInstream==0 }">
							 			${pm.name }
							 		</c:if>
							 		<c:if test="${order.isInstream!=0}">
							 			[插播位置：${pm.instream }]
							 		</c:if>
								<c:if test="${order.isInstream==0&&order.isLoop==1}">
							 			[轮询序号：${pm.loopNo }]
							 		</c:if>
								<c:set var="i" value="${i+1}" />
							</c:forEach> 
							<c:if test="${(order.isAdd==0||order.isLoop==1)&&order.isInstream==0 }">
								</a>
							</c:if>
							</span></div>
							<li id="preciseli" style="display:none">已选择精准</li>
							<div id="selPrecise"><c:set var="j" value="0" /> <c:forEach
								items="${order.materials }" var="ms">
								<c:if test="${j>0}">
									<br />
									<br />
								</c:if>
								<a href="javascript:showPreciseInfo(${j });">${ms.preciseName} </a>
								<br />&nbsp;&nbsp;&nbsp;&nbsp;
								<c:set var="k" value="0" />		
								<c:if test="${(order.isAdd==0||order.isLoop==1)&&order.isInstream==0}">
									<a href="javascript:showPreciseMaterial(${ms.pId });">
								</c:if>						
								<c:forEach items="${ms.material }" var="prm">
									<c:if test="${k>0}">,</c:if>
									<c:if test="${(order.isAdd==1&&order.isLoop==0)||order.isInstream!=0 }">
										<a href="javascript:showMaterial('${prm.path}','${prm.content}',${prm.type})">${prm.name }</a>
									</c:if>
									<c:if test="${(order.isAdd==0||order.isLoop==1)&&order.isInstream==0}">
									 	${prm.name }
									</c:if>
									<c:if test="${order.isInstream!=0}">
							 			[插播位置：${prm.instream }]
							 		</c:if>
									<c:if test="${order.isInstream==0&&order.isLoop==1}">
							 			[轮询序号：${prm.loopNo }]
							 		</c:if>
									<c:set var="k" value="${k+1}" />
								</c:forEach>
								<c:if test="${(order.isAdd==0||order.isLoop==1)&&order.isInstream==0}">
									</a>
								</c:if>
								
								<c:set var="j" value="${j+1}" />
							</c:forEach></div>
						</ul>
						</div>
						<div
							style="width: 6%; height: 65px; margin-left: 15px; margin-top: 70px; float: left; border: 1px dashed #CCCCCC; padding-left: 5px; padding-top: 35px">
						<img src="<%=path%>/images/jiantou.png" /></div>
						<div
							style="width: 38%; height: 288px; float: left; border: 1px dashed #CCCCCC; margin-left: 15px; color: #CCCCCC;margin-top:-20px;">
						<img id="pImage" src="<%=path%>/${order.positionBGPath}" /> <img
							id="mImage" src="" style="display: none" />
						<div id="video"></div>
						<div id="text" style="display: none;"><marquee
							scrollamount="10" id="textContent"></marquee></div>
						</div>
						</td>
					</tr>
					<tr>
						<td class="formBottom" colspan="99"><!--<input type="button" title="大图预览" onclick="" class="b_preview" value=""
							onfocus="blur()" /> 
							-->
							<span style="float: left; margin-left: 20px;font-size:12px;">订单状态： <c:choose>
							<c:when test="${state=='0'}">
								【未发布订单】待审核
							</c:when>
							<c:when test="${state=='1'}">
								【修改订单】待审核
							</c:when>
							<c:when test="${state=='2'}">
								【删除订单】待审核
							</c:when>
							<c:when test="${state=='3'}">
								【未发布订单】审核不通过&nbsp;&nbsp;&nbsp;&nbsp;
								<a href='javascript:showSelectDiv("opinion");'>查看审核意见</a>
							</c:when>
							<c:when test="${state=='4'}">
								【修改订单】审核不通过&nbsp;&nbsp;&nbsp;&nbsp;
								<a href='javascript:showSelectDiv("opinion");'>查看审核意见</a>
							</c:when>
							<c:when test="${state=='5'}">
								【删除订单】审核不通过&nbsp;&nbsp;&nbsp;&nbsp;
								<a href='javascript:showSelectDiv("opinion");'>查看审核意见</a>
							</c:when>
							<c:when test="${state=='6'}">
									已发布&nbsp;&nbsp;&nbsp;&nbsp;
								<a href='javascript:showSelectDiv("opinion");'>查看审核意见</a>
							</c:when>
						</c:choose> </span>
							<input type="button" title="保存"
							onclick="saveOrder(${order.id},${order.ployId },'${order.endTime }','${order.positionEndDate }');" class="b_baoc" value=""
							onfocus="blur()" /> <input type="button" title="重置"
							onclick="reset('${order.endTime }');" class="b_refresh" value=""
							onfocus="blur()" /></td>
					</tr>
				</table>
				</td>
			</tr>

		</table>
		</td>
	</tr>
</table>
<div id="ployInfoDiv" class="showDiv" style="display:none">
<table cellpadding="0" cellspacing="1" width="100%"
	class="taba_right_list" id="piDiv">

	<tr>
		<td colspan="12"
			style="padding-left: 8px; background: url(<%=path%>/images/menu_righttop.png) repeat-x; text-align: left; height: 22px;">·<span id="ployInfoName">策略</span>详情</td>
	</tr>
	<tr id="ployInfoTitle" class="trodd">
		
	</tr>
	<tbody id="ployInfoContent">
	</tbody>

	<tr>
		<td height="34" colspan="12"
			style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: center;">
		<a href="#" onclick="closeSelectDiv('ployInfoDiv');">返回</a>
		</td>
	</tr>
</table>
</div>

<div id="opinion" class="showDiv" style="display:none">
<table cellpadding="0" cellspacing="1" width="100%"
	class="taba_right_list">

	<tr>
		<td colspan="12"
			style="padding-left: 8px; background: url(<%=path%>/images/menu_righttop.png) repeat-x; text-align: left; height: 22px;"><span>·订单审核</span></td>
	</tr>
	<tr>
		<td class="td_labelz">审核人：</td>
		<td class="td_inputz"><span class="e_input_text">${order.checker}</span></td>
		<td class="td_labelz">审核时间：</td>
		<td class="td_inputz"><span class="e_input_text">${order.checkDate }</span></td>
	</tr>
	<tr>
		<td class="td_labelz">审核意见：</td>
		<td colspan="3" style="background: #ffffff;">
			<textarea style="width:460px;height:100px;font-size: 12px;" disabled="disabled">${order.opinion }</textarea>
		</td>	
	</tr>
	<tr>
		<td height="34" colspan="12"
			style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: center;">
		<div class="buttons">
		<a href="#" onclick="closeSelectDiv('opinion');">关闭</a></div>
		</td>
	</tr>
</table>
</div>
<div id="bg" class="bg" style="display: none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>
</body>
</html>