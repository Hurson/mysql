var array = new Array();				 //异步请求的数据（角色或地区或广告商）
var array_2 = new Array();				 //异步请求的数据（角色或地区或广告商）
var role_location_customer_ids = "";	 //绑定的角色或地区或广告商的ID集合
var user_roles 	='';						//用户绑定的角色ID集合
var user_locations	='';					//用户绑定的地区ID集合
var p_user_locations ='';					//用户绑定的地区上级ID集合
var user_customers	='';					//用户绑定的客户ID集合
var sub = 0;							//提交次数

var roleArray = new Array();
var roleType = 0;						//用户选择的角色类型：1运营商管理员，2广告商管理员
/**********************************************/

function loadRole(){
	$.ajax({
		type:"post",
		url: 'getAllRole.do',
		dataType:'json',
		success:function(responseText){
			//var json = eval('(' + responseText + ')');
		     var json = eval(responseText);
			roleArray = json;
			initRoleInfo(roleArray);
		},
		error:function(){
			alert("服务器异常，请稍后重试！");
		}
	});
}

/** 初始化地区 */
var flag = 0;  //禁止加载2次
function initRoleInfo(roles){
	if(flag==0){
		for(var i = 0; i < roles.length; i++){
			var option_str ='<option title="'+roles[i].type+'" value="'+roles[i].id+'">'+roles[i].name+'</option>';
			$("#user_roles_id").append(option_str);
		}
		flag=1;
	}
}

var roleType=-1;//单选时，标识角色类型

/** 判断选择的角色类型 */
function judgeRoleType(){
	var num = $('option:selected', '#user_roles_id').index();
	if(num){
		var type= roleArray[num-1].type;
		roleType=type;
		var customers_id = $('#user_customers_id').val(''); 
		var customers_id_single = $('#user_customers_id_single').val('');
		var customers = $('#sel_customer_ids').val('');
		if(type=='2'){
			$('#customer_div_id').show();
			$('#customer_div_id_single').hide();
			return;
		}else if(type=='1'){
			$('#customer_div_id').hide();
			$('#customer_div_id_single').show();
			return;
		}else{
			$('#customer_div_id').hide();
			$('#customer_div_id_single').hide();
			return;
		}
	}else{
		$('#customer_div_id').hide();
		$('#customer_div_id_single').hide();
	}
}
/**********************************************/
$(document).ready(function(){
	$('#saveForm').submit(function() {
		var options = { 
				url:'toadduser.do?method=addUser',
				type:'post',
				data:{user_roles:user_roles,user_locations:user_locations,p_user_locations:p_user_locations,user_customers:user_customers},
		        success:function(responseText){
					sub=0;
					alert("添加成功!");
					window.location.href="userList.do";
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
});

function firstSubmit(questionNum){
	if(sub==0){
		
		if(checkFrom()){
			$('#saveForm').submit();
			sub=1;
		}
	}else{
		alert('请不要重复提交');
	}
}

//表单验证
function checkFrom(){
	var user_name = $.trim($("#user_name").val());					//用户名
	var user_loginname = $.trim($("#user_loginname").val());		//登陆名
	var user_password = $.trim($("#user_password").val());			//密码
	var user_email = $.trim($("#user_email").val());				//邮箱
	var user_roles_id = $("#user_roles_id").val();			//用户绑定的角色
	var user_locations_id = $("#user_locations_id").val();	//用户绑定的地区
	var user_customers_id = $("#user_customers_id").val();	//用户绑定的客户
	var user_customers_id_single = $("#user_customers_id_single").val();
	if(user_name==''){
		alert("请输入用户名！");
		$("#user_name").val("");
		return false;
	}
	
	if(user_loginname == ''){
		alert("请输入登陆名！");
		$("#user_loginname").val("");
		return false;
	}
	if(user_password ==''){
		alert("请输入密码！");
		$("#user_password").val("");
		return false;
	}
//	if(user_email == ''){
//		alert("请填写邮箱！");
//		return false;
//	}
	if(user_locations_id == ''){
		alert("请选择区域！");
		return false;
	}
	if(user_roles_id == '-1'){
		alert("请选择角色！");
		return false;
	}else{
		if(roleType==2){
			if(user_customers_id==''){
				alert("请选择广告商！");
				return false;
			}
		} else if(roleType == 1){
			if(user_customers_id_single == ''){
				alert("请选择广告商！");
				return false;
			}
		}
	}
	return true;
}

/****************************************************************/
var loadID='';			/**<input/>标签id属性值；id="user_roles_id"*/
var loadName="";		/**<input type="checkbox"/>标签name属性值；name="user_role_name"*/
$(function(){
	 var options = {   
       contentType: "application/x-www-form-urlencoded;charset=utf-8"
    }; 
	 $("#user_roles_id").blur(function(){
		 
		 user_roles =$('#user_roles_id').val()+";";
		
		 return;
    });
	 $('#user_loginname').blur(function(){
		 var loginName =$('#user_loginname').val();
		 checkLoginName(loginName);
		 return;
	 });
	 
	 $("#user_email").blur(function(){//
		 var email_val = $("#user_email").val();
		 if('' != $.trim(email_val)){
			 var mail_reg = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*$/;
			 if(!mail_reg.test(email_val)){
				 alert("请正确输入邮箱地址 ！");
				 $("#user_email").val("");
				 $("#user_email").focus();
			 }
		 }
		 return;
	 });
	 
    $("#user_locations_id").click(function(){
    	 var url = "getTreeLocation.do"; //请求数据
    	 $('#title_id').html(" ·请选择区域");
    	 
    	 
    	 initLocationTree(url);
    	 
    	 loadID = 'user_locations_id';   
//		 loadName = 'user_location_name';
//		 fillDivInfo(url,loadName);
    	return;
    });
    $("#user_customers_id").click(function(){
    	 var url = "getAllCustomer.do"; //请求数据
    	 $('#title_id').html("请选择客户");
    	 loadID = 'user_customers_id';   
    	 loadName = 'user_customer_name';
    	 fillDivInfo(url,loadName);
    	return;
    });
    $("#user_customers_id_single").click(function(){
    	var url = "getAllCustomer.do"; //请求数据
    	$('#title_id').html("请选择客户");
    	loadID = 'user_customers_id_single';   
    	loadName = 'radioName';
    	fillDivInfo_single(url,loadName);
    	return;
    });
});

function initLocation(){
	
	 var url = "getTreeLocation.do"; //请求数据
	 initLocationTree(url);
	 
	 $('#title_id').html("请选择区域");
	 loadID = 'user_locations_id';   
}


/*
 * 加载树
 */
function initLocationTree(url){
	$.ajax({
		type:'post',
		url:url,
		dataType:'json',
		success:function(responseText){
			var roo_t = eval(responseText);
			if(tree_show){
				renderTree(roo_t);
				showSelectDiv("locationDiv1");
			}
		},
		error:function(){
			alert("服务器异常，数据加载失败，请稍后重试！");
		}
	});
}

/** 树的加载 */
var tree_show = true;
function renderTree(roo_t){
	tree_show = false;
	tree = new TreePanel({
		'root' : roo_t[0]
	});
	tree.render();
	//tree.expandAll();//展开
}
/** 保存地区信息 */
function saveCheckLocation(){
	
	user_locations = tree.getChecked('id')+"";
	
	//有子必有父，
	 p_user_locations = tree.getChecked('pid')+"";
	
	var  user_location_name = tree.getChecked('text')+"";
	$('#user_locations_id').val(user_location_name);
	
	closeSelectDiv('locationDiv1');
	
}


/**
 * 检查用户的登录名是否存在 
 * @param loginName
 * @return
 */
function checkLoginName(loginName){
	$.ajax({
		type:'post',
		url:'checkLoginName.do?method=checkLoginName',
		data:{loginName:loginName},
		dataType:'json',
		success:function(responseText){
			var meg = eval(responseText).result;
			if(meg){//有重复的名字，给出提示
				$('#confirm_div_id').show();	//提示内容
				$('#user_loginname').focus();   //重置焦点
			}else{
				$('#confirm_div_id').hide();
			}
		},
		error:function(){
			alert("服务器异常，数据加载失败，请稍后重试！");
		}
	});
}
/**
 * 加载数据，公共方法
 * 
 */
function  fillDivInfo_single(url,radioName){

	$.ajax({
		type:"post",
		url: url,
		success:function(responseText){
			array_2 = eval(responseText);
			var str = "<table style='width: 100%;height:270px; background: #fff;'>";
			for(var i =0;i<array_2.length;i++){
				str +="<tr><td class='list_td'><input style='margin-left:20px;' type='radio' ";
				str += " name='"+radioName+"' value='";
				str += array_2[i].id;
				str +="'/>"
				str +=array_2[i].name;
				str +="</td></tr>"			
			}
			for(i;i<10;i++){
				str +="<tr><td style='height: 100%'></td></tr>";
			}
			str +="</table>"
			$("#customerInfo").html(str);
			showSelectDiv("customerDiv");
		},
		dataType:'json',
		error:function(){
			alert("服务器异常，数据加载失败，请稍后重试！");
		}
	});
	
}	

/**
 * 加载数据，公共方法
 * 
 */
function  fillDivInfo(url,checkboxName){

	$.ajax({
		type:"post",
		url: url,
		success:function(responseText){
			array = eval(responseText);
			var str = "<table style='width: 100%;height:270px; background: #fff;'>";
			for(var i =0;i<array.length;i++){
				str +="<tr><td class='list_td'><input style='margin-left:20px;' type='checkbox' ";
				str += " name='"+checkboxName+"' value='";
				str += array[i].id;
				str +="'/>"
				str +=array[i].name;
				str +="</td></tr>"			
			}
			for(i;i<10;i++){
				str +="<tr><td style='height: 100%'></td></tr>";
			}
			str +="</table>"
			$("#roleInfo").html(str);
			tick_count=true;
			$('#tick').removeAttr("checked");//控制全选按钮
			showSelectDiv("roleDiv");
		},
		dataType:'json',
		error:function(){
			alert("服务器异常，数据加载失败，请稍后重试！");
		}
	});
	
}	


//保存绑定的关系
function saveBanding(){
	
	role_location_customer_ids = "";		//绑定角色或地区或广告商的ID集合
	var  role_location_customer_names = '';  //勾选的角色、地区、广告商的名字
	var  roleArr = $("input[name='"+loadName+"']");
	for(var i=0; i < roleArr.length;i++ ){
		if(roleArr[i].checked){
			role_location_customer_names += array[i].name+",";
			role_location_customer_ids += array[i].id+";";
		}
	}
	role_location_customer_names = role_location_customer_names.substring(0, role_location_customer_names.length-1);
	role_location_customer_ids = role_location_customer_ids.substring(0, role_location_customer_ids.length-1);
	$("#"+loadID).val(role_location_customer_names);
	
	if(loadID=='user_roles_id'){//将选择的绑定的角色ID
		user_roles = role_location_customer_ids;
	}
	if(loadID=='user_locations_id'){//将选择的绑定的区域ID
		user_locations = role_location_customer_ids;
		$("#sel_location_ids").val(role_location_customer_ids);
	}
	if(loadID=='user_customers_id'){//将选择的绑定的客户ID
		user_customers = role_location_customer_ids;
	}
	closeSelectDiv('roleDiv');
}

/**
 * 用户类型是广告商时，绑定一个客户
 * @return
 */
function saveSingleCustomer(){
	
	role_location_customer_ids = "";		//绑定角色或地区或广告商的ID集合
	var  role_location_customer_names = '';  //勾选的角色、地区、广告商的名字
	var  roleArr = $("input[name='"+loadName+"']");
	for(var i=0; i < roleArr.length;i++ ){
		if(roleArr[i].checked){
			
			role_location_customer_names += array_2[i].name;
			role_location_customer_ids += array_2[i].id;
		}
	}
	
	$("#"+loadID).val(role_location_customer_names);
	if(loadID=='user_customers_id_single'){//将选择的绑定的客户ID
		user_customers = role_location_customer_ids;
	}
	closeSelectDiv('customerDiv');
}




var tick_count=true;
/** 勾选 */
function ticked(){
	var  roleArr = $("input[name='"+loadName+"']");
	if(tick_count){
		for(var i=0; i < roleArr.length;i++ ){
			roleArr[i].checked="checked";
		}
		tick_count=false;
	}else{
		tick_count=true;
		for(var i=0; i < roleArr.length;i++ ){
			roleArr[i].checked="";
		}
	}
}

/**
 * 弹出策略选择层
 * */
function showSelectDiv(divId){
	// $('#tick').checked="";//控制全选按钮
	//$('#'+divId).show("slow");
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
	$("div").remove("#TreePanel_id");//删除div
	tree_show = true;
}