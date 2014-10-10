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
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/addOrUpdate.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/updateEndTime.js"></script>
<script type='text/javascript'
	src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=path %>/css/new/main.css" />
<link rel="stylesheet" href="<%=path%>/css/popUpDiv.css" type="text/css" />

<title>订单管理</title>
<style>
.ggw {
	width: 48%;
	height: 268px;
	color: #000000;
	float: left;
	border: 1px dashed #CCCCCC;
}

.ggw li {
	background: #efefef;
	font-weight: bold;
	width: 100%;
	height: 25px;
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

.e_input_time{
	background:url(<%=path%>/js/new/My97DatePicker/skin/datePicker.gif) right no-repeat #ffffff;
}
</style>
</head>

<body class="mainBody"
	onload='init("${orderDetail.coordinate }",${orderDetail.isHD },${orderDetail.isLoop},${orderDetail.isBoot },"${orderDetail.positionSize}",${ployJson },${ployMaterialJson },${materialJson },"${orderDetail.fontSize }","${orderDetail.rollSpeed }","${orderDetail.fontColor }","${orderDetail.ployStartTime }","${aheadTime }",${orderDetail.state});'>
<div class="detail">
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">订单编辑</td>
	</tr>
	<tr>
		<td width="12%" align="right">订单号：</td>
		<td width="33%">${orderDetail.orderNo}</td>
		<td width="12%" align="right">合同名称：</td>
		<td width="33%">${orderDetail.contractCode }</td>
	</tr>
	<tr>
		<td align="right">广告位名称：</td>
		<td>${orderDetail.positionName }</td>
		<td align="right">策略名称：</td>
		<td>${orderDetail.ployName }</td>
	</tr>
	<tr>
		<td align="right">生效日期：</td>
		<td>${orderDetail.startTime}</td>
		<td align="right">
			<c:if test="${orderDetail.state!='7'}">
				<span class="required">*</span>
			</c:if>
			失效日期：</td>
		<td>
			<c:if test="${orderDetail.state!='7'}">
				<input type="text" readonly="readonly" class="e_input_time"
				id="endDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
				value="${orderDetail.endTime }" />
			</c:if>
			<c:if test="${orderDetail.state=='7'}">
				${orderDetail.endTime }
			</c:if>
		</td>

	</tr>
	<tr>
		<td align="right">订单描述：</td>
		<td colspan="3"><textarea id="desc" cols="50" rows="3"
			disabled="disabled">${orderDetail.description }</textarea></td>

	</tr>
</table>
<table>
	<tr>
		<td colspan="6" class="yulan"><span
			style="display: block; width: 500px; padding: 5px;">·订单参数配置</span>
		<div class="ggw">
		<ul>
			<li>已选择策略</li>
			<div id="selPloy"><a href="javascript:showPloyInfo();">${orderDetail.ployName
			}</a> <br />
			&nbsp;&nbsp;&nbsp;&nbsp; <span id="mp0"> <c:set var="i"
				value="0" /> <c:if
				test="${(orderDetail.isAdd==0||orderDetail.isLoop==1)&&orderDetail.isBoot==0&&orderDetail.isInstream==0 }">
				<a href="javascript:showPloyMaterial();">
			</c:if> <c:forEach items="${orderDetail.ployMaterials }" var="pm">
				<c:if test="${i>0}">,</c:if>
				<c:if
					test="${(orderDetail.isAdd==1&&orderDetail.isLoop==0)||orderDetail.isBoot==1||orderDetail.isInstream!=0 }">
					<a
						href="javascript:showMaterialPre('${pm.path}','${pm.content}',${pm.type})">${pm.name
					}</a>
				</c:if>
				<c:if
					test="${(orderDetail.isAdd==0||orderDetail.isLoop==1)&&orderDetail.isBoot==0&&orderDetail.isInstream==0 }">
							 			${pm.name }
							 		</c:if>
				<c:if test="${orderDetail.isInstream!=0}">
							 			[插播位置：${pm.instream }]
							 		</c:if>
				<c:if test="${orderDetail.isInstream==0&&orderDetail.isLoop==1}">
							 			[轮询序号：${pm.loopNo }]
							 		</c:if>
				<c:set var="i" value="${i+1}" />
			</c:forEach> <c:if
				test="${(orderDetail.isAdd==0||orderDetail.isLoop==1)&&orderDetail.isBoot==0&&orderDetail.isInstream==0 }">
				</a>
			</c:if> </span></div>
			<li id="preciseli" style="display: none">已选择精准</li>
			<div id="selPrecise"><c:set var="j" value="0" /> <c:forEach
				items="${orderDetail.materials }" var="ms">
				<c:if test="${j>0}">
					<br />
					<br />
				</c:if>
				<a href="javascript:showPreciseInfo(${j });">${ms.preciseName} </a>
				<br />&nbsp;&nbsp;&nbsp;&nbsp;
								<c:set var="k" value="0" />
				<c:if
					test="${(orderDetail.isAdd==0||orderDetail.isLoop==1)&&orderDetail.isInstream==0}">
					<a href="javascript:showPreciseMaterial(${ms.pId });">
				</c:if>
				<c:forEach items="${ms.material }" var="prm">
					<c:if test="${k>0}">,</c:if>
					<c:if
						test="${(orderDetail.isAdd==1&&orderDetail.isLoop==0)||orderDetail.isInstream!=0 }">
						<a
							href="javascript:showMaterialPre('${prm.path}','${prm.content}',${prm.type})">${prm.name
						}</a>
					</c:if>
					<c:if
						test="${(orderDetail.isAdd==0||orderDetail.isLoop==1)&&orderDetail.isInstream==0}">
									 	${prm.name }
									</c:if>
					<c:if test="${orderDetail.isInstream!=0}">
							 			[插播位置：${prm.instream }]
							 		</c:if>
					<c:if test="${orderDetail.isInstream==0&&orderDetail.isLoop==1}">
							 			[轮询序号：${prm.loopNo }]
							 		</c:if>
					<c:set var="k" value="${k+1}" />
				</c:forEach>
				<c:if
					test="${(orderDetail.isAdd==0||orderDetail.isLoop==1)&&orderDetail.isInstream==0}">
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
			style="width: 38%; height: 288px; float: left; border: 1px dashed #CCCCCC; margin-left: 15px; color: #CCCCCC; margin-top: -20px;">
		<img id="pImage" src="<%=path%>/${orderDetail.positionBGPath}" /> <img
			id="mImage" src="" style="display: none" />
		<div id="video"></div>
		<div id="text" style="display: none;"><marquee scrollamount="10"
			id="textContent"></marquee></div>
		</div>
		</td>
	</tr>
	<tr>
		<td>
		<div style="margin-left:50px;">
		<c:if test="${orderDetail.state!='7'}">
			<input type="button" onclick="saveOrder(${orderDetail.id},${orderDetail.ployId },'${orderDetail.endTime }','${orderDetail.positionEndDate }');"
			class="btn" value="确认" />&nbsp;&nbsp;
			<input
			type="button" onclick="javascript :history.back(-1);" class="btn"
			value="取消" />
		</c:if> 
		<c:if test="${orderDetail.state=='7'}">
			<input
			type="button" onclick="javascript :history.back(-1);" class="btn"
			value="返回" />
		</c:if> 
		
		<span style="margin-left: 50px; font-size: 12px;">
		订单状态： <c:choose>
			<c:when test="${orderDetail.state=='0'}">
											【未发布订单】待审核
										</c:when>
			<c:when test="${orderDetail.state=='1'}">
											【修改订单】待审核
										</c:when>
			<c:when test="${orderDetail.state=='2'}">
											【删除订单】待审核
										</c:when>
			<c:when test="${orderDetail.state=='3'}">
											【未发布订单】审核不通过&nbsp;&nbsp;
											<a href='javascript:showSelectDiv("opinion");'>查看审核意见</a>
			</c:when>
			<c:when test="${orderDetail.state=='4'}">
											【修改订单】审核不通过&nbsp;&nbsp;
											<a href='javascript:showSelectDiv("opinion");'>查看审核意见</a>
			</c:when>
			<c:when test="${orderDetail.state=='5'}">
											【删除订单】审核不通过&nbsp;&nbsp;
											<a href='javascript:showSelectDiv("opinion");'>查看审核意见</a>
			</c:when>
			<c:when test="${orderDetail.state=='6'}">
												已发布&nbsp;&nbsp;
											<a href='javascript:showSelectDiv("opinion");'>查看审核意见</a>
			</c:when>
			<c:when test="${orderDetail.state=='7'}">
												执行完毕
			</c:when>
		</c:choose> </span></div>
		</td>
	</tr>

</table>
</div>
<div id="ployInfoDiv" class="showDiv" style="display: none">
<div class="searchContent">
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="5"><span id="ployInfoName">策略</span>详情</td>
	</tr>
</table>
<table width="100%" cellspacing="1" class="searchList">
	<tr class="title" id="ployInfoTitle">

	</tr>
	<tbody id="ployInfoContent">
	</tbody>
	<tr>
		<td id="plBtn" colspan="5"><input type="button" value="关闭"
			class="btn" onclick="closeSelectDiv('ployInfoDiv');" />&nbsp;&nbsp;</td>
		<td id="preBtn" colspan="11" style="display: none"><input
			type="button" value="关闭" class="btn"
			onclick="closeSelectDiv('ployInfoDiv');" />&nbsp;&nbsp;</td>
	</tr>
</table>
</div>
</div>

<div id="opinion" class="showDiv" style="display:none;height: 300px;">
	<div class="searchContent">
		<table cellspacing="1" class="searchList" align="left">
		<tr class="title">
			<td colspan="5">审核信息</td>
		</tr>
	</table>
	<table width="100%" cellspacing="1" class="searchList">
		<tr>
			<td width="12%" align="right">审核人：</td>
			<td width="33%"><span class="e_input_text">${orderDetail.checker}</span></td>
			<td width="12%" align="right">审核时间：</td>
			<td width="33%"><span class="e_input_text">${orderDetail.checkDate }</span></td>
		</tr>
		<tr>
			<td align="right">审核意见：</td>
			<td colspan="3">
				${orderDetail.opinion }
			</td>	
		</tr>
		<tr>
			<td colspan="4" >
				<input type="button" value="关闭" class="btn" onclick="closeSelectDiv('opinion');"/>
			</td>
		</tr>
	</table>
	</div>
</div>
<div id="bg" class="bg" style="display: none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>
</body>
</html>