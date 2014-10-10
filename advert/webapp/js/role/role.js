var columns = new Array();//所有的栏目数据
var selColumnIDs = "";
var sub = 0;

//表单验证
function checkFrom(){
	if($.trim($("#name").val())==''){
		alert("请输入角色名称！");
		$("#name").focus();
		return false;
	}
	if(validateSpecialCharacterAfter($("#name").val())){
		alert("角色名称不能包括特殊字符！");
		$("#name").focus();
		return false;
	}
	if($.trim($("#type").val())==''){
		alert("请选择角色类型！");
		$("#type").focus();
		return false;
	}
	if($.trim($("#sel_columns").val())==''){
		alert("请选择栏目！");
		$("#role_columns").focus();
		return false;
	}
	if($("#description").val().length>120){
		alert("角色描述长度必须在0-120字之间！");
		$("#description").focus();
		return false;
	}
	if(validateSpecialCharacterAfter($("#description").val())){
		alert("角色描述不能包括特殊字符！");
		$("#description").focus();
		return false;
	}
	return true;
}


/**
 * 弹出策略选择层
 * */
function showSelectDiv(divId){
	$('#'+divId).show();
	$('#bg').show();
	$('#popIframe').show();
}
/**
 * 关闭策略选择层
 * */
function closeSelectDiv(divId){
	$('#'+divId).hide("slow");
	$('#bg').hide();
	$('#popIframe').hide();
	$('#TreePanel_id').empty();
	tree_show = true;
}

/** 关闭 div */
function closeDiv(divId){
	$('#'+divId).hide("slow");
	$('#bg').hide();
	$('#popIframe').hide();
	$("div").remove("#TreePanel_id");//删除div
	tree_show = true;
}

//加载所有栏目信息
function fillColumnInfo(){
	
	$.ajax({
		type:"post",
		url: 'getColumnList.do',
		success:function(responseText){
			columns = eval(responseText);
			var str = "<table style='width: 100%;height:270px; background: #fff;'>";
			for(var i =0;i<columns.length;i++){
				str +="<tr><td class='list_td'><input style='margin-left:20px;' type='checkbox' ";
				str += " name='column' value='";
				str += columns[i].id;
				str +="'/>"
				str +=columns[i].name;
				str +="</td></tr>"			
			}
			for(i;i<10;i++){
				str +="<tr><td style='height: 100%'></td></tr>";
			}
			str +="</table>"
			$("#columnInfo").html(str);
			
			showSelectDiv("columnDiv");
		},
		dataType:'json',
		error:function(){
			alert("服务器异常，策略加载失败，请稍后重试！");
		}
	});
}


//加载Role绑定的Column信息
function fillColumnInfoByRoleID(roleId){
	$.ajax({
		type:"post",
		url: 'getColumnListByRole.do',
		data:{id:roleId},
		success:function(responseText){
		columns = eval(responseText);
		var column_ids  = '';
		for(var i =0;i<columns.length;i++){
			column_ids += columns[i].id+',';
		}
		fillColumnInfo_2(column_ids);
	},
	dataType:'json',
	error:function(){
		alert("服务器异常，策略加载失败，请稍后重试！");
	}
	});
	
}

var tree_show = true;

function fillColumnInfo_2(column_ids){
	var column_IDs  = column_ids;
	$.ajax({
		type:"post",
		url: 'getColumnList.do',
		data:{column_IDs:column_IDs},
		success:function(responseText){
		//alert(responseText);
			var roo_t = eval(responseText);
			if(tree_show){
				renderTree(roo_t);
				showSelectDiv("columnDiv");
			}
		},
		dataType:'json',
		error:function(){
			alert("服务器异常，栏目加载失败，请稍后重试！");
		}
	});

}

/** 渲染树 */
function renderTree(roo_t){
	tree_show=false;
	tree = new TreePanel({
		'root' : roo_t[0]    //自己定义符合的树结构数据
	});
	tree.render();
	
	tree.expandAll();//展开
}



//保存绑定的栏目
function saveBandingColumn(){
	selColumnIDs = "";//选着的栏目的集合
	var  columnArr = $("input[name='column']");
	var  count = 0;
	var selColumnNames='';
	for(var i=0; i < columnArr.length;i++ ){
		if(columnArr[i].checked){
			count++;
			if(count < 3){
				selColumnNames += columns[i].name+"、";
			}
			selColumnIDs += columns[i].id+";";
		}
		
	}
	$("#role_columns").val(selColumnNames);
	$("#sel_columns").val(selColumnIDs);
	
	closeSelectDiv('columnDiv');
	
}
