var op = 0;// 提交删除请求计数器，防止重复发送删除请求

/** 复选框全选 */
function checkAll(form) {
	for ( var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];
		if (e.Name != "chkAll")
			e.checked = form.chkAll.checked;
	}
}

/** 验证要删除的订单集合是否符合要求 
function validateDelOrders() {
	if(op==0){
		op=1;
		var num = 0;
		var pId = '';// 已发布订单id
		var id = '';// 未发布订单id
		var ids = $("input[name='ids']");
		var states = $("input[name='states']");
	
		for ( var i = 0; i < ids.length; i++) {
			if (ids[i].checked) {
				switch (states[i].value) {
				case "7":
					alert('选择的订单包含执行完毕的订单，不能进行删除操作！');
					op=0;
					return;
				case "2":
				case "5":
					alert('选择的订单包含已删除的订单！');
					op=0;
					return;
				case "0":
				case "3":
					if (id != '') {
						id += ',';
					}
					id += ids[i].value;
					break;
				case "1":
				case "4":
				case "6":
					if (pId != '') {
						pId += ',';
					}
					pId += ids[i].value;
					break;
				}
				num = num + 1;
			}
		}
		if (num == 0) {
			alert("请选择要删除的订单！");
			op=0;
			return;
		}
		if (pId == '') {
			deleteOrders(id, pId);
		} else {
			$.ajax( {
				type : "post",
				url : 'checkOrderTime.do',
				success : function(result) {
					if(result != '-1'){
						if (result == '0') {
							deleteOrders(id, pId);
						} else if(result == '1'){
							alert("选择的订单包括正在执行的订单，不能进行删除操作！");
							op=0;
						} 
					}else{
						alert("系统错误，请联系管理员");
						op=0;
					}
				},
				dataType : 'text',
				data : {
					ids : pId
				},
				error : function() {
					alert("系统错误，请联系管理员");
					op=0;
				}
			});
		}
	}else{
		alert("请不要重复提交删除请求！");
	}
}
*/

/** 验证要删除的订单集合是否符合要求 */
function validateDelOrders() {
	if(op==0){
		op=1;
		var num = 0;
		var pId = '';// 已发布订单id
		var id = '';// 未发布订单id
		var ids = $("input[name='ids']");
		var states = $("input[name='states']");
		for ( var i = 0; i < ids.length; i++) {
			if (ids[i].checked) {
				switch (states[i].value) {
				case "0":
				case "3":
					if (id != '') {
						id += ',';
					}
					id += ids[i].value;
					break;
				case "1":
				case "4":
				case "6":
					if (pId != '') {
						pId += ',';
					}
					pId += ids[i].value;
					break;
				}
				num = num + 1;
			}
		}
		if (num == 0) {
			alert("请选择要删除的订单！");
			op=0;
			return;
		}
		if (pId == '') {
			deleteOrders(id, pId);
		} else {
			$.ajax( {
				type : "post",
				url : 'checkOrderTime.do',
				success : function(result) {
					if(result != '-1'){
						if (result == '0') {
							deleteOrders(id, pId);
						} else if(result == '1'){
							alert("选择的订单包括正在执行的订单，不能进行删除操作！");
							op=0;
						} 
					}else{
						alert("系统错误，请联系管理员");
						op=0;
					}
				},
				dataType : 'text',
				data : {
					ids : pId
				},
				error : function() {
					alert("系统错误，请联系管理员");
					op=0;
				}
			});
		}
	}else{
		alert("请不要重复提交删除请求！");
	}
}

/** 批量删除订单 */
function deleteOrders(id, pId) {
	var con = confirm("确定要删除选择的订单吗？");
	if (con) {
		$.ajax( {
			type : "post",
			url : 'deleteOrders.do',
			success : function(result) {
				if (result == '0') {
					alert("删除成功");
					window.location.href = "listOrder.do";
				}else{
					alert("系统错误，请联系管理员");
				}
				op=0;
			},
			dataType : 'text',
			data : {
				ids : id,
				pIds : pId
			},
			error : function() {
				alert("系统错误，请联系管理员");
				op=0;
			}
		});
	}else{
		op=0;
	}
}

/**
 * 验证要删除的订单是否符合要求
 */
//function validateDelOrder(id, orderState) {
//	if(op==0){
//		op=1;
//		switch (orderState) {
//		case "7":
//			alert('此订单执行完毕，不允许进行删除操作！');
//			op=0;
//			return;
//		case "2":
//		case "5":
//			alert('此订单已被删除！');
//			op=0;
//			return;
//		case "0":
//		case "3":
//			deleteOrder(id);
//			break;
//		case "1":
//		case "4":
//		case "6":
//			$.ajax( {
//				type : "post",
//				url : 'checkOrderTime.do',
//				success : function(result) {
//					if(result!='-1'){
//						if (result == '0') {
//							setOrderDelState(id);
//						} else {
//							alert("此订单正在执行，不能进行删除操作！");
//							op=0;
//							return;
//						}
//					}else{
//						alert("系统错误，请联系管理员");
//						op=0;
//					}
//				},
//				dataType : 'text',
//				data : {
//					ids : id
//				},
//				error : function() {
//					alert("系统错误，请联系管理员");
//					op=0;
//				}
//			});
//			break;
//		}
//	}else{
//		alert("请不要重复提交删除请求！");
//	}
//
//}
/** 单个删除订单 */
//function deleteOrder(id) {
//	var con = confirm("确定要删除该订单吗？");
//	if (con) {
//		$.ajax( {
//			type : "post",
//			url : 'deleteOrder.do',
//			success : function(result) {
//				if (result == '0') {
//					alert("删除成功");
//					window.location.href = "listOrder.do"
//				}else{
//					alert("系统错误，请联系管理员");
//				}
//				op=0;
//			},
//			dataType : 'text',
//			data : {
//				id : id,
//			},
//			error : function() {
//				alert("系统错误，请联系管理员");
//				op=0;
//			}
//		});
//	}else{
//		op=0;
//	}
//}

/**
 * 设置订单为删除状态
 * */
//function setOrderDelState(id){
//	var con = confirm("确定要删除该订单吗？");
//	if (con) {
//		$.ajax( {
//			type : "post",
//			url : 'setOrderDelState.do',
//			success : function(result) {
//				if (result == '0') {
//					alert("删除成功");
//					window.location.href = "listOrder.do"
//				}else{
//					alert("系统错误，请联系管理员");
//				}
//				op=0;
//			},
//			dataType : 'text',
//			data : {
//				id : id,
//			},
//			error : function() {
//				alert("系统错误，请联系管理员");
//				op=0;
//			}
//		});
//	}else{
//		op=0;
//	}
//}
/**
 * 编辑订单
 */
function editOrder(id, orderState) {
	if (orderState =='7') {
		alert('此订单已经执行完毕，不允许进行编辑操作！');
	} else {
		window.location.href = "getOrderForUpdate.do?id=" + id + "&state="
		+ orderState;
	}
}