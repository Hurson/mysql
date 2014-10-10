<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.dvnchina.advertDelivery.utils.ConfigureProperties" %>
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
<script type="text/javascript" src="<%=path%>/js/order/addOrder.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/addOrUpdate.js"></script>
<script language="javascript" type="text/javascript" 
	src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
<script language="javascript" type="text/javascript" 
	src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker-zh-CN.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>

<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css"/>
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

<body onload='init(${contractJson },"<%=ConfigureProperties.getInstance().get("orderOpAheadTime")%>");'>
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
				<td class="formTitle" colspan="99"><span>·新建订单</span></td>
			</tr>
			<tr>
				<td class="td_labelz">订单号：</td>
				<td class="td_inputz"><input type="hidden" id="orderNo"
					value="${orderNo }" /> <span class="e_input_text">${orderNo
				}</span></td>
				<td class="td_labelz">合同选择：</td>
				<td class="td_inputz"><input id="contract" class="e_input_add" readonly="readonly"
					onfocus="this.className='e_inputFocus'" onclick="showContract();"
					onblur="this.className='e_input_add'" />
					<span style="color:red">*</span>	
				</td>
			</tr>
			<tr>
				<td class="td_labelz">广告位选择：</td>
				<td class="td_inputz"><input id="position" class="e_input_add" readonly="readonly"
					onfocus="this.className='e_inputFocus'" onclick="showPosition();"
					onblur="this.className='e_input_add'" />
					<span style="color:red">*</span>	
				</td>
				<td class="td_labelz">策略选择：</td>
				<td class="td_inputz"><input id="ployName" class="e_input_add" readonly="readonly"
					onfocus="this.className='e_inputFocus'" onclick="showPloy();"
					onblur="this.className='e_input_add'" />
					<span style="color:red">*</span>	
				</td>
			</tr>
			<tr>
				<td class="td_labelz">开始日期选择：</td>
				<td class="td_inputz"><input type="text" readonly="readonly" class="e_input"
					onfocus="this.className='e_inputFocus'" id="startDate" 
					onblur="this.className='e_input'"/>
					<span style="color:red">*</span>	
				</td>
				<td class="td_labelz">结束日期选择：</td>
				<td class="td_inputz"><input type="text" readonly="readonly" class="e_input"
					onfocus="this.className='e_inputFocus'" id="endDate"  
					onblur="this.className='e_input'" />
					<span style="color:red">*</span>	
				</td>

			</tr>
			<tr>
				<td class="td_labelz">订单描述：</td>
				<td class="td_inputz" colspan="3"><textarea id="desc"
					class="e_textarea" onfocus="this.className='e_textareaFocus'"
					onblur="this.className='e_textarea'"></textarea>
					<span style="color:red">(长度在0-120字之间)</span>
				</td>

			</tr>
			<tr>
				<td colspan="99">
				<table cellpadding="0" cellspacing="1" width="100%" class="tablea">
					<!--<tr>
						<td class="formTitle" colspan="99">·订单参数配置</td>
					</tr>-->
					<tr>
						<td colspan="6" class="yulan">
						<span style="display:block;width:500px;padding:5px;margin-left:15px;">·订单参数配置</span>
						<div class="ggw">
						<ul>						
							<li>已选择策略</li>
							<div id="selPloy"><span id="selPloyName"></span> <span
								id="ploybd" style="display: none"> 
								<a href="javascript:fillMaterialInfo(0,0);"
								style="diplay:block; float:right;margin-left:5px;">编辑绑定</a>
								<a href="javascript:fillMaterialInfo(0,1);"
								style="diplay: block; float: right;">绑定素材</a>
								 </span> <br />
							&nbsp;&nbsp;&nbsp;&nbsp; <span id="mp0"></span></div>
							<li id="preciseli" style="display:none">已选择精准</li>
							<div id="selPrecise"></div>
						</ul>
						</div>
						
						<div style="width: 6%; height: 65px; margin-left: 15px; margin-top: 70px; float: left; border: 1px dashed #CCCCCC; padding-left: 5px; padding-top: 35px">
						<img src="<%=path%>/images/jiantou.png" /></div>
						<div style="width: 38%; height: 288px; float: left; border: 1px dashed #CCCCCC; margin-left: 15px; color: #CCCCCC;margin-top:-20px;">
							<img id="pImage" src="<%=path%>/images/position/position.jpg" width="426px" height="288px" />
							<img id="mImage" src="" style="display:none"/>
							<div id="video"></div>
							<div id="text" style="display:none;"><marquee scrollamount="10"  id="textContent" ></marquee></div>							
						</div>
						</td>
					</tr>
					<tr>
						<td class="formBottom" colspan="99"><!--<input type="button"
							title="大图预览" onclick="" class="b_preview" value=""
							onfocus="blur()" /> --><input type="button" title="保存"
							onclick="saveOrder();" class="b_baoc" value="" onfocus="blur()" />
						<input type="button" title="重置" onclick="reset();"
							class="b_refresh" value="" onfocus="blur()" /></td>
					</tr>
				</table>
				</td>
			</tr>

		</table>
		</td>
	</tr>
</table>



<div id="contractDiv" class="showDiv" style="display: none;">
<table cellpadding="0" cellspacing="1" width="100%" class="tableDiv">
	<tr>
		<td class="formTitle" colspan="99"><span>·合同选择</span></td>
	</tr>
	<tr>
		<td class="td_label">合同名称：</td>
		<td class="td_input"><input type="text" id="cName"
			class="e_inputDiv" onfocus="this.className='e_inputFocusDiv'"
			onblur="this.className='e_inputDiv'" /></td>
		<td class="td_label">合同号：</td>
		<td class="td_input"><input type="text" id="cCode"
			class="e_inputDiv" onfocus="this.className='e_inputFocusDiv'"
			onblur="this.className='e_inputDiv'" /></td>
		<td class="td_label">合同代码：</td>
		<td class="td_input"><input type="text" id="cNumber"
			class="e_inputDiv" onfocus="this.className='e_inputFocusDiv'"
			onblur="this.className='e_inputDiv'" /></td>
	</tr>
	<tr>
		<td class="td_label">开始时间：</td>
		<td class="td_input">
			<input type="text" readonly="readonly" class="time_inputDiv" id="cStart"/>
		</td>
		<td class="td_label">结束时间：</td>
		<td class="td_input">
			<input type="text" readonly="readonly" id="cEnd" class="time_inputDiv" />
		</td>
		<td class="td_label">&nbsp;</td>
		<td class="td_input">&nbsp;</td>
	</tr>
	<tr>
		<td class="formBottom" colspan="99"><input name="Submit"
			type="button" title="查看" class="b_search" value=""
			onclick="queryContract()" onfocus="blur()" /></td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" width="100%"
	class="taba_right_list" id="cDiv">

	<tr>
		<td colspan="12"
			style="padding-left: 8px; background: url(<%=path%>/images/menu_righttop.png) repeat-x; text-align: left; height: 22px;"><span>·合同列表</span></td>
	</tr>
	<tr>
		<td height="26px" align="center" width="5%"></td>
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
				<td align="center" height="26"><input type="radio"
					name="contractId" value="${contract.id}"
					<c:if test="${cIndex==0 }"> checked="checked"</c:if> /> <input
					type="hidden" name="contractCode" value="${contract.contractCode }" /></td>
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
		<td height="34" colspan="12"
			style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: center;">
		<div class="buttons"><a href="#" onclick="selectContract();">确认</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" onclick="closeSelectDiv('contractDiv');">返回</a></div>
		</td>
	</tr>
</table>
</div>

<div id="positionDiv" class="showDiv" style="display: none;">
<table cellpadding="0" cellspacing="1" width="100%" class="tableDiv">
	<tr>
		<td class="formTitle" colspan="99"><span>·广告位选择</span></td>
	</tr>
	<tr>
		<td class="td_label">广告位名称：</td>
		<td class="td_input"><input type="text" id="pName"
			class="e_inputDiv" onfocus="this.className='e_inputFocusDiv'"
			onblur="this.className='e_inputDiv'" /></td>
		<td class="td_label">广告位类型：</td>
		<td class="td_input"><select id="pType">
			<option value="-1">--选择广告位类型--</option>
			<c:forEach items="${positionTypes}" var="type">
				<option value="${type.id }">${type.positionTypeName }</option>
			</c:forEach>
		</select></td>
		<td class="td_label">投放方式：</td>
		<td class="td_input"><select id="pMode">
			<option value="-1">--选择投放方式--</option>
			<c:forEach items="${positionModes}" var="mode">
				<option value="${mode.id }">${mode.name}</option>
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td class="formBottom" colspan="99"><input name="Submit"
			type="button" title="查看" class="b_search" value=""
			onclick="queryPosition()" onfocus="blur()" /></td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" width="100%"
	class="taba_right_list" id="pDiv">

	<tr>
		<td colspan="12"
			style="padding-left: 8px; background: url(<%=path%>/images/menu_righttop.png) repeat-x; text-align: left; height: 22px;"><span>·广告位列表</span></td>
	</tr>
	<tr>
		<td height="26px" align="center" width="5%"></td>
		<td>广告位名称</td>
		<td>广告位类型</td>
		<td>高清/标清</td>
		<td>是否叠加</td>
		<td>是否轮询</td>
		<td>轮询个数</td>
		<td>投放方式</td>
		<td>描述</td>
	</tr>
	<tbody id="positionInfo"></tbody>
	<tr>
		<td height="34" colspan="12"
			style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: center;">
		<div class="buttons"><a href="#" onclick="selectPosition();">确认</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" onclick="closeSelectDiv('positionDiv');">返回</a></div>
		</td>
	</tr>
</table>
</div>
<div id="ployDiv" class="showDiv" style="display: none;">
<table cellpadding="0" cellspacing="1" width="100%" class="tableDiv">
	<tr>
		<td class="formTitle" colspan="99"><span>·合同选择</span></td>
	</tr>
	<tr>
		<td class="td_label">策略名称：</td>
		<td class="td_input" colspan="5"><input type="text" id="plName"
			class="e_inputDiv" onfocus="this.className='e_inputFocusDiv'"
			onblur="this.className='e_inputDiv'" /></td>
	</tr>
	<tr>
		<td class="formBottom" colspan="99"><input name="Submit"
			type="button" title="查看" class="b_search" value=""
			onclick="queryPloy()" onfocus="blur()" /></td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" width="100%"
	class="taba_right_list" id="plDiv">

	<tr>
		<td colspan="12"
			style="padding-left: 8px; background: url(<%=path%>/images/menu_righttop.png) repeat-x; text-align: left; height: 22px;"><span>·策略列表</span></td>
	</tr>
	<tr>
		<td height="26px" align="center" width="5%"></td>
		<td>策略名称</td>
		<td>开始时段</td>
		<td>结束时段</td>
		<td>关联区域</td>
		<td>关联频道</td>
	</tr>
	<tbody id="ployInfo"></tbody>

	<tr>
		<td height="34" colspan="12"
			style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: center;">
		<div class="buttons"><a href="#" onclick="selectPloy();">确认</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" onclick="closeSelectDiv('ployDiv');">返回</a></div>
		</td>
	</tr>
</table>
</div>


<div id="materialDiv" class="showDiv" style="display: none;">
<table cellpadding="0" cellspacing="1" width="100%" class="tableDiv" id="queryMaterial">
	<tr>
		<td class="formTitle" colspan="99"><span>·素材选择</span></td>
	</tr>
	<tr>
		<td class="td_label">素材名称：</td>
		<td class="td_input" colspan="5"><input type="text" id="mName"
			class="e_inputDiv" onfocus="this.className='e_inputFocusDiv'"
			onblur="this.className='e_inputDiv'" /></td>
	</tr>
	<tr>
		<td class="formBottom" colspan="99"><input name="Submit"
			type="button" title="查看" class="b_search" value=""
			onclick="queryMaterial()" onfocus="blur()" /></td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" width="100%"
	class="taba_right_list" id="mDiv">

	<tr>
		<td colspan="12"
			style="padding-left: 8px; background: url(<%=path%>/images/menu_righttop.png) repeat-x; text-align: left; height: 22px;"><span>·资产列表</span></td>
	</tr>
	<tr id="materialTitle">

	</tr>
	<tbody id="materialInfo"></tbody>

	<tr>
		<td height="34" colspan="12"
			style="background: url(<%=path%>/images/bottom.jpg) repeat-x; text-align: center;">
		<div class="buttons"><a href="#" onclick="selectMaterial();">确认</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" onclick="closeSelectDiv('materialDiv');">返回</a></div>
		</td>
	</tr>
</table>
</div>

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


<div id="bg" class="bg" style="display: none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>
</body>
</html>