var roleId = '';
var p_column_ids='' ;
/*
$(document).ready(function(){
	$('#updateForm').submit(function() {
		var options = { 
				url:'updaterole.do?method=updateRole&flag=update',
				type:'post',
				data:{id:roleId,columnIDs:selColumnIDs,p_column_ids:p_column_ids},
		        success:function(responseText){
					sub=0;
					alert("修改成功!");
					window.location.href="toRoleList.do";
				}, 
				error:function(){
					sub=0;
					alert("服务器异常，请稍后重试！");
				},
		        dataType:'html' 
		    };
		
	    $(this).ajaxSubmit(options); 
	    return false; 
	});
});*/

function firstSubmit(){
	if(checkFrom()){
		var roleId = $("#roleId").val();
		var name = $("#name").val();
		$.ajax({   
		       url:'checkRoleName.do',       
		       type: 'POST',    
		       dataType: 'text',   
		       data: {
				   roleId:roleId,
		    	   name:name
				},                   
		       timeout: 1000000000,                              
		       error: function(){                      
		    		alert("系统错误，请联系管理员！");
		       },    
		       success: function(result){ 
		    	   if(result == 'false'){
		    		   $('#updateForm').submit();
			       }else{
			    	   alert("角色名称已存在，请重新输入！");
					   $("#name").focus();
				   }

		    	   
			   }  
		   }); 
	}
}
var tree_show = true;

function fillColumnInfo_2(){
	var sel_columns  = $('#sel_columns').val();
	var sel_p_columns  = $('#sel_p_columns').val();
	$.ajax({
		type:"post",
		url: 'getTreeColumn.do',
		data:{sel_columns:sel_columns,sel_p_columns:sel_p_columns},
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

function saveBandingColumn_2(){
	selColumnIDs = '';//选着的栏目的集合
	
	var selColumnNames='';
	
	selColumnIDs = tree.getChecked('id')+"";
	
	//有子必有父，
	 p_column_ids = tree.getChecked('pid')+"";
	 
	selColumnNames = tree.getChecked('text');
	
	$("#role_columns").val(selColumnNames);
	$("#sel_columns").val(selColumnIDs);
	$("#sel_p_columns").val(p_column_ids);
	
	closeDiv('columnDiv');
}


/** 关闭 div */
function closeDiv(divId){
	$('#'+divId).hide("slow");
	$('#bg').hide();
	$('#popIframe').hide();
	$("div").remove("#TreePanel_id");//删除div
	tree_show = true;
}	