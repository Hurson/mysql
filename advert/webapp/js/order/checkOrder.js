var op = 0;// 提交表单计数器，防止重复提交表单
var pass=0;//是否通过，0-通过，1-不通过
var updateFlag=0;//修改标识，0-全部修改，1-修改结束时间

/**审核通过校验*/
function passCheck(id,state, startDate, endDate, startTime, endTime,
		aheadTime,modifyTime, ployId, orderType,isPass) {
	switch (state) {
	case 0:
		if (checkStartTime(startDate, startTime, aheadTime)) {
			checkPlayList4Add(startDate, endDate, ployId, orderType,isPass);
		} else {
			showOpinions( '订单的开始时间小于当前日期，审核失败！');
		}
		break;
	case 1:
		if(checkStartTime(startDate, startTime, aheadTime)){
			checkPlayList4Upd(id,startDate,endDate,ployId,orderType,isPass);
		}else{
			checkPlayList4Upd1(id,startDate,endDate,endTime,modifyTime,ployId,orderType,isPass);
		}
		
		break;
	case 2:
		if (checkStartTime(startDate, startTime, aheadTime)) {
			showOpinions(isPass);
		} else {
			showOpinions( '订单的开始时间小于当前日期，审核失败！');
		}
		break;
	}
}

/**
 * 验证订单开始时间是否大于当前时
 *@return true-开始时间大于当前时间
 *			false- 开始时间小于当前时间
 * */
function checkStartTime(startDate, startTime, aheadTime) {
	var sTime = startDate.replace(/-/g, "/")+" " + startTime;
	var start = Date.parse(sTime) + aheadTime*1000;
	var now = Date.parse(new Date());
	if (start < now) {// 开始时间小于当前时间，审核不通过
		return false;
	}
	return true;
}
/**
 * 验证新增订单绑定的策略在排期范围内是否与播出单冲突
 * */
function checkPlayList4Add(startDate, endDate, ployId, orderType,isPass) {
	$.ajax( {
		type : "post",
		url : 'checkPlayList4Add.do',
		success : function(result) {
			if(result!='-1'){
				if (result == '0') {
					showOpinions(isPass);
				} else {
					showOpinions('与'+result+'订单冲突，审核失败！')
				}
			}else{
				alert("服务器异常，检查失败，请稍后重试！");
			}
		},
		dataType : 'text',
		data : {
			startDate : startDate,
			endDate : endDate,
			ployId : ployId,
			orderType : orderType
		},
		error : function() {
			alert("服务器异常，检查失败，请稍后重试！");
		}
	});
}

/**
 * 当订单开始时间大于当前时间时
 * 验证订单时间是否符合规则，策略在排期范围内是否与播出单冲突
 * */
function checkPlayList4Upd(id,startDate,endDate,ployId,orderType,isPass) {
	$.ajax( {
		type : "post",
		url : 'checkPlayList4Upd.do',
		success : function(result) {
			if(result!='-1'){
				if (result == '0') {
					updateFlag=0;
					showOpinions(isPass);
				} else if(result=='1'){
					showOpinions("订单正在执行，不能修改除结束日期以外的内容，审核失败！")
				}else{
					showOpinions("与订单"+result+"冲突，审核失败！");
				}
			}else{
				alert("服务器异常，检查失败，请稍后重试！");		
			}
		},
		dataType : 'text',
		data : {
			id:id,
			startDate:startDate,
			endDate:endDate,
			ployId:ployId,
			orderType : orderType
		},
		error : function() {
			alert("服务器异常，检查失败，请稍后重试！");
		}
	});
}

/**
 * 当订单开始时间小于当前时间时
 * 验证订单时间是否符合规则，策略在排期范围内是否与播出单冲突
 * 0-通过
 * 1-未执行的订单，修改后的开始时间小于当前时间，审核失败
 * 2-已执行的订单，结束时间小于当前时间，审核失败
 * orderNo-策略时间冲突
 * */
function checkPlayList4Upd1(id,startDate, endDate, endTime,modifyTime, ployId, orderType,isPass) {
	$.ajax( {
		type : "post",
		url : 'checkPlayList4Upd1.do',
		success : function(result) {
			if(result!='-1'){
				if (result == '0') {
					updateFlag=1;
					showOpinions(isPass);
				} else if(result == '1'){
					showOpinions("未执行的订单，修改后的开始时间小于当前时间，审核失败！");
				} else if(result == '2'){
					showOpinions("已执行的订单，结束时间小于当前时间，审核失败！");
				} else if(result == '3'){
					showOpinions("订单正在执行，不能修改除结束日期以外的内容，审核失败！");
				}else{
					showOpinions("与订单"+result+"冲突，审核失败！");
				}
			}else{
				alert("服务器异常，检查失败，请稍后重试！");
			}
		},
		dataType : 'text',
		data : {
			id:id,
			startDate:startDate,
			endDate:endDate,
			endTime:endTime,
			modifyTime:modifyTime,
			ployId:ployId,
			orderType : orderType
		},
		error : function() {
			alert("服务器异常，检查失败，请稍后重试！");
		}
	});
}

/**
 * 显示审核意见
 * 
 * @param ispass
 *            是否通过（0-技术审核通过，1-技术审核通过，人工审核不通过，非0非1-技术审核不通过原因描述）
 */
function showOpinions(isPass) {
	if(isPass==0){
		$("#checkInfo").html("通过");
		pass=0;
	}else if(isPass==1){
		$("#checkInfo").html("不通过");
		pass=1;
	}else{
		$("#checkInfo").html("不通过");
		$("#checkOpinion").val(isPass);
		pass=1;
	}
	showDiv();
}

function showDiv(){
	$("#bg,#popIframe,#checkDiv").show();
}

function hideDiv(){
	$("#bg,#popIframe,#checkDiv").hide();
}
/**
 * 查看绑定策略详情
 * */
function showPloyInfo(){
	var title = "<td>策略名称</td><td>开始时段</td><td>结束时段</td><td>关联区域</td><td>关联频道</td>";
	$("#ployInfoTitle").html(title);
	var content = "<tr class='treven'><td>";
	content +=selPloy.name;
	content +="</td><td>";
	content += selPloy.startTime;
	content +="</td><td>";
	content += selPloy.endTime;
	content +="</td>";
	content +=fillAreaChannelInfo(selPloy.areas);
	content +="</tr>";		
	$("#ployInfoContent").html(content);
	$("#ployInfoName").html("策略");
	showSelectDiv("ployInfoDiv");
}

/**
 * 填充策略区域频道内容
 * */
function fillAreaChannelInfo(areas){
	str = "<td><table cellpadding='0' cellspacing='1' width='100%' class='taba_right_list'>";
	for(var i = 0;i<areas.length;i++){
		str +="<tr";
		if(i%2==0){
			str += " class='treven' "
		}else{
			str += " class='trodd' "
		}
		str +="><td>";
		str +=areas[i].name;
		str +="</td></tr>";
	}
	str += "</table></td><td><table cellpadding='0' cellspacing='1' width='100%' class='taba_right_list'>";
	for(var j = 0;j<areas.length;j++){
		str +="<tr";
		if(j%2==0){
			str += " class='treven' "
		}else{
			str += " class='trodd' "
		}
		str +="><td>";
		for(var k= 0;k<areas[j].child.length;k++){
			if(k>0){
				str += ",";
			}
			str += areas[j].child[k].name;
		}
		str +="</td></tr>";
	}
	str +="</table></td>";
	return str;
}

/**
 * 查看绑定精准详情
 * */
function showPreciseInfo(i){	
	var title = "<td>精准名称</td><td>类型</td><td>产品</td><td>频道</td><td>影片关键字</td><td>用户区域</td>" +
			"<td>行业类别</td><td>用户级别</td><td>TVN号段</td><td>优先级</td><td>节点</td>";
	$("#ployInfoTitle").html(title);
	var content="<tr class='treven'><td>";
	content +=precises[i].name;
	content +="</td><td>";
	content += getPreciseType(precises[i].type);
	content +="</td><td>";
	content += precises[i].products;
	content +="</td><td>";
	content +=precises[i].channels;
	content +="</td><td>";
	content += precises[i].key;
	content +="</td><td>";
	content += precises[i].userArea;
	content +="</td><td>";
	content +=precises[i].userIndustrys;
	content +="</td><td>";
	content += precises[i].userLevels;
	content +="</td><td>";
	content += precises[i].tvnNumber;
	content +="</td><td>";
	content +=precises[i].priority;
	content +="</td><td>";
	content += precises[i].categorys;
	content +="</td></tr>";	
	$("#ployInfoContent").html(content);
	$("#ployInfoName").html("精准");
	showSelectDiv("ployInfoDiv");
}
/**
 * 弹出策略选择层
 */
function showSelectDiv(divId){
	$('#'+divId).show();
	$('#bg').show();
	$('#popIframe').show();
}
/**
 * 关闭策略选择层
 */
function closeSelectDiv(divId){
	$('#'+divId).hide();
	$('#bg').hide();
	$('#popIframe').hide();
	
}
/**
 * 提交审核结果
 * */
function save(id,orderType){
	if(op==0){
		op=1;
		if(pass==1&&$("#checkOpinion").val()==''){
			alert("此订单审核不通过，请输入审核意见！");
			op=0;
			return false;
		}
		if($("#checkOpinion").val()!=''&&$("#checkOpinion").val().length>120){
			alert("审核意见字数在0-120字之间！");
			op=0;
			return false;
		}
		$.ajax( {
			type : "post",
			url : 'checkOrder.do',
			success : function(result) {
				if (result == '0') {
					hideDiv();
					alert('提交审核结果成功！');
					window.location.href="listOrderForCheck.do"
				}  else{
					alert("服务器异常，检查失败，请稍后重试！");
				}
				op=0;
			},
			dataType : 'text',
			data : {
				id:id,
				orderType : orderType,
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