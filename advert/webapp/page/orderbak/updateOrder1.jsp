<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.dvnchina.advertDelivery.utils.ConfigureProperties"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/updateOrder.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/addOrUpdate.js"></script>
<script type='text/javascript'
	src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=path %>/css/new/main.css" />

<link rel="stylesheet" href="<%=path%>/css/popUpDiv.css" type="text/css" />
<title>修改订单</title>
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

.e_input_add {
	background: url(<%=path%>/images/add.png) right no-repeat #ffffff;
}

.e_input_time {
	background: url(<%=path%>/js/new/My97DatePicker/skin/datePicker.gif)
		right no-repeat #ffffff;
}
</style>
</head>

<body class="mainBody"
	onload='init(${orderDetail.contractId},${contractJson },${positionJson },${ployJson },${plMJson },${prMJson},"<%=ConfigureProperties.getInstance().get("orderOpAheadTime")%>");'>
<form action="updateOrder.do" method="post" id="updateForm">
<input type="hidden" id="id" name="orderDetail.id" value="${orderDetail.id}"  />
<input type="hidden" id="orderType" name="orderDetail.orderType" value="${orderDetail.orderType}" />
<input type="hidden" id="isHD" name="orderDetail.isHD" value="${orderDetail.isHD}" />
<input type="hidden" id="state" name="orderDetail.state" value="${orderDetail.state }" />
<input type="hidden" id="materialJson" name="materialJson" value="${materialJson}" />
<div class="detail">
<div id="messageDiv" style="margin-top: 15px;color: red;font-size: 14px;font-weight: bold;">
	 <c:if test="${message != null}">
            <span style="color:red;"><s:property value="%{getText(#request.message)}" /></span>
    </c:if>
</div> 
<table cellspacing="1" class="searchList" align="left">
	<tr class="title">
		<td colspan="4">订单编辑</td>
	</tr>
	<tr>
		<td width="12%" align="right">订单号：</td>
		<td width="33%"> <span id="orderNo">${orderDetail.orderNo }</span></td>
		<td width="12%" align="right"><span class="required">*</span>合同名称：</td>
		<td width="33%">
			<input id="contract" class="e_input_add" name="orderDetail.contractName" value="${orderDetail.contractName}"
				readonly="readonly" onclick="showContract();" type="text"/>
			<input type="hidden" id="contractId" name="orderDetail.contractId" value="${orderDetail.contractId}"  />
		</td>
	</tr>
	<tr>
		<td align="right"><span class="required">*</span>广告位名称：</td>
		<td>
			<input id="position" name="orderDetail.positionName" value="${orderDetail.positionName}" class="e_input_add" readonly="readonly"
				onclick="showPosition();" type="text"/>
			<input type="hidden" id="positionId" name="orderDetail.positionId" value="${orderDetail.positionId}"  />
		</td>
		<td align="right"><span class="required">*</span>策略名称：</td>
		<td>
			<input id="ployName" name="orderDetail.ployName" value="${orderDetail.ployName}" class="e_input_add" readonly="readonly"
				onclick="showPloy();" type="text" />
			<input type="hidden"  id="ployId" name="orderDetail.ployId" value="${orderDetail.ployId}"  />
		</td>
	</tr>
	<tr>
		<td align="right"><span class="required">*</span>生效日期：</td>
		<td><input type="text" name="orderDetail.startDateStr" value="${orderDetail.startTime}" readonly="readonly" class="e_input_time"
			id="startDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /></td>
		<td align="right"><span class="required">*</span>失效日期：</td>
		<td><input type="text" name="orderDetail.endDateStr" value="${orderDetail.endTime}" readonly="readonly" class="e_input_time"
			id="endDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /></td>

	</tr>
	<tr>
		<td align="right">订单描述：</td>
		<td colspan="3"><textarea id="desc" name="orderDetail.description" cols="50" rows="3">${orderDetail.description}</textarea>
		<span class="required">(长度在0-120字之间)</span></td>

	</tr>
</table>
<table>
	<tr>
		<td colspan="6" class="yulan"><span
			style="display: block; width: 500px; padding: 5px;">·订单参数配置</span>
		<div class="ggw">
		<ul>
			<li>已选择策略</li>
			<div id="selPloy"><span id="selPloyName"><a href='javascript:showPloyInfo();'>${orderDetail.ployName}</a></span> <span
				id="ploybd"> <a href="javascript:fillMaterialInfo(0,0);"
				style="diplay: block; float: right; margin-left: 5px;">编辑绑定</a> <a
				href="javascript:fillMaterialInfo(0,1);"
				style="diplay: block; float: right;">绑定素材</a> </span> <br />
			&nbsp;&nbsp;&nbsp;&nbsp; <span id="mp0"> </span></div>
			<li id="preciseli" style="display: none">已选择精准</li>
			<div id="selPrecise"></div>
		</ul>
		</div>
		<div
			style="width: 6%; height: 65px; margin-left: 15px; margin-top: 70px; float: left; border: 1px dashed #CCCCCC; padding-left: 5px; padding-top: 35px">
		<img src="<%=path%>/images/jiantou.png" /></div>
		<div
			style="width: 38%; height: 288px; float: left; border: 1px dashed #CCCCCC; margin-left: 15px; color: #CCCCCC; margin-top: -20px;">
		<img id="pImage" src="<%=path%>/images/position/position.jpg"
			width="426px" height="288px" /> <img id="mImage" src=""
			style="display: none" />
		<div id="video"></div>
		<div id="text" style="display: none;"><marquee scrollamount="10"
			id="textContent"></marquee></div>
		</div>
		</td>
	</tr>
	<tr>
		<td>
			<div style="margin-left:50px;">
				 <input type="button" onclick="saveOrder();" class="btn" value="确认" />&nbsp;&nbsp;
				 <input type="button" onclick="javascript :history.back(-1);" class="btn" value="取消" />
				 <span style="margin-left: 50px; font-size: 12px;">
				 订单状态：
					<c:choose>
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
					</c:choose>
				</span>
			</div>
		</td>
	</tr>

</table>
</div>
</form>

<div id="contractDiv" class="showDiv" style="display: none">
<div class="searchContent">
<table cellspacing="1" class="searchList">
	<tr class="title">
		<td>查询条件</td>
	</tr>
	<tr>
		<td class="searchCriteria"><span>合同名称：</span><input type="text"
			name="textfield" id="cName" style="width: 14%" /> <span>合&nbsp;同&nbsp;号：</span><input
			type="text" name="textfield" id="cCode" style="width: 14%" /> <span>合同代码：</span><input
			type="text" name="textfield" id="cNumber" style="width: 14%" /> <input
			type="button" value="查询" onclick="queryContract();" class="btn" /></td>
	</tr>
</table>
<div id="messageDiv"
	style="margin-top: 15px; color: red; font-size: 14px; font-weight: bold;"></div>
<table width="100%" cellspacing="1" class="searchList">
	<tr class="title">
		<td class="dot"></td>
		<td>合同名称</td>
		<td>合同号</td>
		<td>合同代码</td>
		<td>广告商名称</td>
		<td>开始时间</td>
		<td>结束时间</td>
	</tr>
	<tbody id="contractInfo">
	<c:set var="cIndex" value="0" />
	<c:forEach items="${contracts}" var="contract">
		<tr>
			<td align="center"><input type="radio"
				name="contractId" value="${contract.id}"
				<c:if test="${cIndex==0 }"> checked="checked"</c:if> /> <input
				type="hidden" name="contractCode" value="${contract.contractCode }" />
			<input type="hidden" name="contractName"
				value="${contract.contractName }" /></td>
			<td>${contract.contractName }</td>
			<td>${contract.contractCode }</td>
			<td>${contract.contractNumber }</td>
			<td>${contract.customer.advertisersName }</td>
			<td>${contract.effectiveStartDate }</td>
			<td>${contract.effectiveEndDate }</td>
		</tr>
		<c:set var="cIndex" value="${cIndex+1 }" />
	</c:forEach>
	</tbody>
	<tr>
		<td colspan="7"><input type="button" value="确定" class="btn"
			onclick="selectContract();" />&nbsp;&nbsp; <input type="button"
			value="取消" class="btn" onclick="closeContract();" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
</div>
</div>

<div id="positionDiv" class="showDiv" style="display: none;">
<div class="searchContent">
<table cellspacing="1" class="searchList">
	<tr class="title">
		<td>查询条件</td>
	</tr>
	<tr>
		<td class="searchCriteria"><span>广告位名称：</span><input type="text"
			name="textfield" id="pName" /> <span>广告位类型：</span> <select id="pType">
			<option value="-1">--选择广告位类型--</option>
			<c:forEach items="${positionTypes}" var="type">
				<option value="${type.id }">${type.positionTypeName }</option>
			</c:forEach>
		</select> <span>投放方式：</span> <select id="pMode">
			<option value="-1">--选择投放方式--</option>
			<c:forEach items="${positionModes}" var="mode">
				<option value="${mode.id }">${mode.name}</option>
			</c:forEach>
		</select> <input type="button" value="查询" onclick="queryPosition();"
			class="btn" /></td>
	</tr>
</table>
<div id="messageDiv"
	style="margin-top: 15px; color: red; font-size: 14px; font-weight: bold;"></div>
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
		<td>投放开始日期</td>
		<td>投放结束日期</td>
	</tr>
	<tbody id="positionInfo"></tbody>
	<tr>
		<td colspan="10"><input type="button" value="确定" class="btn"
			onclick="selectPosition();" />&nbsp;&nbsp; <input type="button"
			value="取消" class="btn" onclick="closePosition();" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
</div>
</div>
<div id="ployDiv" class="showDiv" style="display: none;">
<div class="searchContent">
<table cellspacing="1" class="searchList">
	<tr class="title">
		<td>查询条件</td>
	</tr>
	<tr>
		<td class="searchCriteria"><span>策略名称：</span><input type="text"
			name="textfield" id="plName" /> <input type="button" value="查询"
			onclick="queryPloy();" class="btn" /></td>
	</tr>
</table>
<div id="messageDiv"
	style="margin-top: 15px; color: red; font-size: 14px; font-weight: bold;"></div>
<table width="100%" cellspacing="1" class="searchList">
	<tr class="title">
		<td class="dot"></td>
		<td>策略名称</td>
		<td>开始时段</td>
		<td>结束时段</td>
		<td>关联区域</td>
		<td>关联频道</td>
	</tr>
	<tbody id="ployInfo"></tbody>

	<tr>
		<td colspan="7"><input type="button" value="确定" class="btn"
			onclick="selectPloy();" />&nbsp;&nbsp; <input type="button"
			value="取消" class="btn" onclick="closePloy();" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
</div>
</div>


<div id="materialDiv" class="showDiv" style="display: none;">
<div class="searchContent">
<table cellspacing="1" class="searchList" id="queryMaterial">
	<tr class="title">
		<td>查询条件</td>
	</tr>
	<tr>
		<td class="searchCriteria"><span>资产名称：</span><input type="text"
			name="textfield" id="mName" /> <input type="button" value="查询"
			onclick="queryMaterial();" class="btn" /></td>
	</tr>
</table>
<div id="messageDiv"
	style="margin-top: 15px; color: red; font-size: 14px; font-weight: bold;"></div>
<table width="100%" cellspacing="1" class="searchList">
	<tr class="title" id="materialTitle">

	</tr>
	<tbody id="materialInfo"></tbody>
	<tr>
		<td colspan="9"><input type="button" value="确定" class="btn"
			onclick="selectMaterial();" />&nbsp;&nbsp; <input type="button"
			value="取消" class="btn" onclick="closeMaterial();" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
</div>
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