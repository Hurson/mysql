<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<link rel="stylesheet" href="<%=path%>/css/menu_right_new1.css"	type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/preview.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/common.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<title>订单明细审核</title>
</head>
<style>
	.ggw {
		width: 48%;
		/*height: 268px;*/
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
	.e_input_add {
		background:url(<%=path%>/images/add.png) right no-repeat #ffffff;
	}
	
	.e_input_time{
		background:url(<%=path%>/js/new/My97DatePicker/skin/datePicker.gif) right no-repeat #ffffff;
	}

</style>
<script type="text/javascript">
	var op = 0;// 提交表单计数器，防止重复提交表单

	function init(materialJson,adPackageType,ployJson,positionJson,playNumber,positionId){
		if(playNumber<=0){
			$("#periodDate").show();
			$("#onceDate").hide();
		}else{
			$("#onceDate").show();
			$("#periodDate").hide();
		}

		//问卷广告位，订单添加用户次数、问卷次数、通知门限值、积分兑换人民币比率
		if(positionId=='27'||positionId=='28'){
			$("#questionnaire1").show();
			$("#questionnaire2").show();
		}
		
		if(adPackageType == 0 || adPackageType == 1){
			//显示双向策略信息
			viewTwoPloy(ployJson,1);
		}else{
			//显示单向策略信息
			viewOnePloy(ployJson,1);
		}
		
		//显示已经选择的素材信息
		viewMaterials(materialJson);

		//预览素材
		var selPosition = eval(positionJson);
		preview(selPosition.backgroundPath,selPosition.coordinate,selPosition.widthHeight);
		
		
	}

	/**
	 * 审核通过保存
	 * */
	function save(id,updateFlag,pass){
		if(op==0){
			op=1;
			if(pass==1&&$("#checkOpinion").val()==''){
				alert("此订单审核不通过，请输入审核意见！");
				$("#checkOpinion").focus();
				op=0;
				return ;
			}
			if($("#checkOpinion").val()!=''&&$("#checkOpinion").val().length>120){
				alert("审核意见字数在0-120字之间！");
				$("#checkOpinion").focus();
				op=0;
				return;
			}
			if(validateSpecialCharacterAfter($("#checkOpinion").val())){
				alert("审核意见不能包括特殊字符！");
				$("#checkOpinion").focus();
				op=0;
				return ;
			}
			$.ajax( {
				type : "post",
				url : 'checkOrder.do',
				success : function(result) {
					if (result == '0') {
						alert('提交审核结果成功！');
						window.location.href="queryOrderAuditList.do"
					}else if(result == '-1'){
						alert("服务器异常，检查失败，请稍后重试！");
					}else{
						alert(result);
					}
					op=0;
				},
				dataType : 'text',
				data : {
					id:id,
					pass :pass,
					updateFlag:updateFlag,
					opinion:$("#checkOpinion").val()
				},
				error : function() {
					alert("服务器异常，检查失败，请稍后重试！");
					op=0;
				}
			});
		}else{
			alert('请不要重复提交表单！');
		}
	}
	//显示订单审核日志
	function showAuditLog(relationId){
    	var url = "queryOrderAuditLog.do?auditLog.relationType=1&auditLog.relationId="+relationId;
    	window.showModalDialog(url, window, "dialogHeight=480px;dialogWidth=820px;center=1;resizable=0;status=0;");
	}
</script>
<body class="mainBody" 
	onload='init(${materialJson},${adPackageType},${ployJson},${positionJson},${order.playNumber},${order.positionId});'>
<div class="detail">
    <table cellspacing="1" class="searchList" align="left">
        <tr class="title">
            <td colspan="4">订单明细审核</td>
        </tr>
        <tr>
            <td width="12%" align="right">订单编号：</td>
            <td width="33%">${order.orderCode}</td>
            <td width="12%" align="right">合同名称：</td>
            <td width="33%">${order.contractName }</td>
        </tr>
        <tr>
            <td width="12%" align="right">广告位名称：</td>
            <td width="33%">${order.positionName }</td>
            <td width="12%" align="right">策略名称：</td>
            <td width="33%">${order.ployName }</td>
        </tr>
        <tr id="questionnaire1" style="display: none">
			<td width="12%" align="right">用户总次数：</td>
            <td width="33%">${order.userNumber}</td>
            <td width="12%" align="right">问卷总次数：</td>
            <td width="33%">${order.questionnaireNumber}</td>
		</tr>
		
		<tr id="questionnaire2" style="display: none">
			<td width="12%" align="right">通知门限值：</td>
            <td width="33%">${order.thresholdNumber}</td>
            <td width="12%" align="right">积分兑换人民币比率：</td>
            <td width="33%">${order.integralRatio}</td>
		</tr>
        <tr id="periodDate" style="display: none">
            <td width="12%" align="right">开始日期：</td>
            <td width="33%"><fmt:formatDate value="${order.startTime}" dateStyle="medium"/></td>
            <td width="12%" align="right">结束日期：</td>
            <td width="33%"><fmt:formatDate value="${order.endTime}" dateStyle="medium"/></td>
        </tr>
        <tr id="onceDate" style="display: none">
			<td align="right">开始日期：</td>
			<td><fmt:formatDate value="${order.startTime}" dateStyle="medium"/></td>
			<td align="right">播放次数：</td>
			<td>${order.playNumber}</td>
		</tr>
        <tr>
            <td width="12%" align="right">订单类型：</td>
            <td width="33%">
            	<c:choose>
					<c:when test="${order.orderType==0}">投放式</c:when>
					<c:when test="${order.orderType==1}">请求式</c:when>
				</c:choose>
            </td>
            <td width="12%" align="right">订单状态：</td>
            <td width="33%">
            	<c:choose>
					<c:when test="${order.state=='0'}">【未发布订单】待审核</c:when>
					<c:when test="${order.state=='1'}">【修改订单】待审核</c:when>
					<c:when test="${order.state=='2'}">【删除订单】待审核</c:when>
				</c:choose>
            </td>
        </tr>
        <tr>
            <td width="12%" align="right">描述：</td>
            <td colspan="3">
                <textarea disabled="disabled" cols="40" rows="3">${order.description }</textarea>
            </td> 
        </tr>
        <tr>
            <td align="right">审核意见：</td>
            <td colspan="3">
                <textarea id="checkOpinion" name="checkOpinion" cols="40" rows="3"></textarea>
                <span id="opinions_error" class="required">
                <c:if test="${order.opinion!=null && order.opinion!=''}">
                	错误提示：${order.opinion}
                </c:if>
                </span>
            </td>
        </tr>
        <tr>
			<td colspan="4" class="yulan"><span
				style="display: block; width: 500px; padding: 5px;">·策略绑定素材信息</span>
			<div id="ggw" class="ggw">
			<ul>
				<!-- <li>已选择策略</li>
				<div id="selPloy"><span id="selPloyName">${order.ployName}</span>  <br />
				&nbsp;&nbsp;&nbsp;&nbsp; <span id="mp0"> </span></div>
				<li id="preciseli" style="display: none">已选择精准</li> -->
				<div id="selPrecise"></div>
			</ul>
			</div>
			<div
				style="width: 6%; height: 65px; margin-left: 15px; margin-top: 70px; float: left; border: 1px dashed #CCCCCC; padding-left: 5px; padding-top: 35px">
			<img src="<%=path%>/images/jiantou.png" /></div>
			<div
				style="width: 426px; height: 240px; float: left; border: 1px dashed #CCCCCC; margin-left: 15px; color: #CCCCCC; margin-top: -20px; position: relative;">
			<img id="pImage" src="<%=path%>/images/position/position.jpg" width="426px" height="240px" /> 
			<img id="mImage" src=""	style="display: none" />
			<div id="video">
				<object type='application/x-vlc-plugin' id='vlc'  classid='clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921' width="150" height="150">
			           <param name='mrl'  value=''/>
						<param name='volume' value='50' />
						<param name='autoplay' value='false' />
						<param name='loop' value='false' />
						<param name='fullscreen' value='false' />
			    </object>
			</div>
			<div id="text" style="display: none;"><marquee scrollamount="10"
				id="textContent"></marquee></div>
			</div>
			</td>
		</tr>
    </table>
    <div class="action">
    	<c:if test="${order.opinion == null || order.opinion == ''}">
        <input type="button" class="btn" value="通过" onclick="javascript:save(${order.id},'${order.updateFlag}',0);" />&nbsp;&nbsp;&nbsp;&nbsp;
        </c:if>
        <input type="button" class="btn" value="驳回" onclick="javascript:save(${order.id},'${order.updateFlag}',1);" />&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" class="btn" value="返回" onclick="javascript :history.back(-1)" />&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:showAuditLog('${order.id}');" >审核日志</a>
    </div>
</div>

</body>
</html>