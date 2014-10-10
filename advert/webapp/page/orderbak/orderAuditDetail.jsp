<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<link rel="stylesheet" href="<%=path%>/css/menu_right_new1.css"
	type="text/css" />
<link rel="stylesheet" href="<%=path%>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path%>/css/popUpDiv.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/auditOrder.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/checkOrder2.js"></script>
<title>订单明细审核</title>
</head>
<script type="text/javascript">
    
</script>
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
	.yulan{ height:310px;
	      background: #ffffff;
	}
	.tablea {
		border: 0px solid #dfdfdf;
		width: 100%;
		background:999999;
	}
</style>

<body class="mainBody"
	onload='init(${positionJson },${ployJson },${plMJson },${prMJson});'>
<div class="detail">
    <table cellspacing="1" class="searchList" align="left">
        <tr class="title">
            <td colspan="4">订单明细审核</td>
        </tr>
        <tr>
            <td width="12%" align="right">订单编号：</td>
            <td width="33%">${orderDetail.orderNo}</td>
            <td width="12%" align="right">合同名称：</td>
            <td width="33%">${orderDetail.contractName }</td>
        </tr>
        <tr>
            <td width="12%" align="right">广告位名称：</td>
            <td width="33%">${orderDetail.positionName }</td>
            <td width="12%" align="right">策略名称：</td>
            <td width="33%">${orderDetail.ployName }</td>
        </tr>
        <tr>
            <td width="12%" align="right">开始日期：</td>
            <td width="33%">${orderDetail.startTime }</td>
            <td width="12%" align="right">结束日期：</td>
            <td width="33%">${orderDetail.endTime }</td>
        </tr>
        <tr>
            <td width="12%" align="right">描述：</td>
            <td colspan="3">
                <textarea disabled="disabled" cols="40" rows="3">${orderDetail.description }</textarea>
            </td> 
        </tr>
        <tr>
            <td align="right">审核意见：</td>
            <td colspan="3">
                <textarea id="checkOpinion" name="checkOpinion" cols="40" rows="3"></textarea>
                <span id="opinions_error" class="required">
                <c:if test="${orderDetail.opinion!=null && orderDetail.opinion!=''}">
                	错误提示：${orderDetail.opinion}
                </c:if>
                </span>
            </td>
        </tr>
        
        <tr>
			<td colspan="4" class="yulan"><span
				style="display: block; width: 500px; padding: 5px;">·订单参数配置</span>
			<div class="ggw">
			<ul>
				<li>已选择策略</li>
				<div id="selPloy"><span id="selPloyName"><a href="javascript:showPloyInfo();"> ${orderDetail.ployName } </a></span>  <br />
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
    </table>
    <div class="action">
    	<c:if test="${orderDetail.opinion == null || orderDetail.opinion == ''}">
        <input type="button" class="btn" value="通过" onclick="javascript:save(${orderDetail.id },${orderDetail.state },${orderDetail.orderType },'${orderDetail.updateFlag }',0);" />&nbsp;&nbsp;&nbsp;&nbsp;
       	</c:if>
        <input type="button" class="btn" value="驳回" onclick="javascript:save(${orderDetail.id },${orderDetail.state },${orderDetail.orderType },'${orderDetail.updateFlag }',1);" />&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" class="btn" value="返回" onclick="javascript :history.back(-1)" />
        <c:choose>
			<c:when test="${orderDetail.state=='0'}">【未发布订单】待审核</c:when>
			<c:when test="${orderDetail.state=='1'}">【修改订单】待审核</c:when>
			<c:when test="${orderDetail.state=='2'}">【删除订单】待审核</c:when>
		</c:choose>
    </div>
</div>

<!-- 显示策略（精准） 信息 -->
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

<div id="bg" class="bg" style="display: none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>
</body>
</html>