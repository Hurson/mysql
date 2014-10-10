var array = new Array();				 //异步请求的数据（角色或地区或广告商）
var role_location_customer_ids = "";	 //绑定的角色或地区或广告商的ID集合
var user_roles 	='';						//用户绑定的角色ID集合
var user_locations	='';					//用户绑定的地区ID集合
var user_customers	='';					//用户绑定的客户ID集合
var sub = 0;							//提交次数

var roleArray = new Array();
var roleType = 0;						//用户选择的角色类型：1运营商管理员，2广告商管理员
var tmpcustomer = new Array();
var tempids = '';
var temptype = 0;
var tempname = '';
var mutname = '';
var signame ='';
/**********************************************/

function loadRole(){
var num = $('option:selected', '#user_roles_id').index();
	
	
	$.ajax({
		type:"post",
		url: 'getAllRole.do',
		dataType:'json',
		success:function(responseText){
			//var json = eval('(' + responseText + ')');
		     var json = eval(responseText);
			roleArray = json;
			//initRoleInfo(roleArray);
			if(num){
				var type = roleArray[num-1].type;
				if(type == 2){
					var bane = $('#user_customers_id').val();
			    	 mutname = bane;
				} else if(type == 1){
					var name = $('#user_customers_id_single').val();
			    	signame = name;
				}
			}
		},
		error:function(){
			alert("服务器异常，请稍后重试！");
		}
	});
	
}
/** 初始化地区 
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
*/
var roleType=-1;//单选时，标识角色类型

/** 判断选择的角色类型 */
function judgeRoleType(){
	var num = $('option:selected', '#user_roles_id').index();
	
	if(num){
		
		
		var type = roleArray[num-1].type;
		var id = roleArray[num-1].id;
		var customers_id = $('#user_customers_id').val(''); 
		
		
		var customers_id_single = $('#user_customers_id_single').val('');
		var customers = "";
		
		$('#role_id').val(id);
		roleType=type;
		if(type=='2'){
			
			$('#customer_div_id').show();
			$('#user_customers_id').val(mutname);
			$('#customer_div_id_single').hide();
		}else if((type=='1')){
			$('#customer_div_id').hide();
			$('#user_customers_id_single').val(signame);
			$('#customer_div_id_single').show();
		} else {
			$('#customer_div_id').hide();
			$('#customer_div_id_single').hide();
		}
	}else{
		$('#customer_div_id_single').hide();
		$('#customer_div_id').hide();
	}
}
/**********************************************/
$(document).ready(function(){
	$('#saveForm').submit(function() {
		var options = { 
				url:'updateU.do?method=updateUser&flag=update',
				type:'post',
				data:{user_roles:user_roles,user_locations:user_locations,user_customers:user_customers},
		        success:function(responseText){
					var meg = eval(responseText);
					if(meg){
						sub=0;
						alert("修改成功！");
						window.location.href="userList.do";
					}else{
						alert("修改失败！");
					}
				}, 
				error:function(){
					sub=0;
					alert("服务器异常，请稍后重试！");
				},
		        dataType:'json' 
		    };
		
	    $(this).ajaxSubmit(options); 
	    return false; 
	});
});

function firstSubmit(questionNum){
	user_roles = $('#role_id').val();
	user_locations = $('#sel_location_ids').val();  //隐藏域内的地区的ID
	user_customers = $('#sel_customer_ids').val();  //隐藏域内的客户的ID
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
		 
		 user_roles = $('#user_roles_id').val()+";";
		 
//		  var url = "getAllRole.do"; //请求数据
//		  loadID = 'user_roles_id';   
//		  loadName = 'user_role_name';
//		  $('#title_id').html("请选择角色");
		 
//		  fillDivInfo(url,loadName);
		
		 return;
    });
	 $('#user_loginname').change(function(){
		 var loginName =$('#user_loginname').val();
		 checkLoginName(loginName);
		 return;
	 });
	 
	 $("#user_email").change(function(){//
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
    	 $('#title_id').html("·请选择区域");
    	 loadID = 'user_locations_id';  
    	 
    	 initLocationTree(url);
    	 
    	
    	
//    	 var url = "getAllLocation.do"; //请求数据
//    	 loadID = 'user_locations_id';   
//		 loadName = 'user_location_name';
//		 $('#title_id').html("请选择区域");
//		 
//		 var userOwnLocation = $('#sel_location_ids').val();
//		 var user_Location_ago= userOwnLocation.split(';')
//		 fillLocationDivInfo(url,loadName ,user_Location_ago);
		 
    	return;
    });
    
    $("#user_customers_id").click(function(){
    	
    	 var url = "getAllCustomer.do"; //请求数据
    	 loadID = 'user_customers_id';   
    	 loadName = 'user_customer_name';
    	 $('#title_id').html("请选择客户");
    	 
    	 var userOwnCustomer = $('#sel_customer_ids').val();
    	 
    	 var user_customer_ago = userOwnCustomer.split(';');
    	 
    	 tmpcustomer = user_customer_ago;
    	 fillDivInfo(url,loadName,user_customer_ago);
    	 
    	return;
    	
    	
    });
    
    $("#user_customers_id_single").click(function(){
    	var url = "getAllCustomer.do"; //请求数据
    	$('#title_id').html("请选择客户");
    	loadID = 'user_customers_id_single';   
    	loadName = 'radioName';
    	var customer = $('#sel_customer_ids').val();
    	
    	tempids = customer;
    	
    	fillDivInfo_single(url,loadName, customer);
    	
    	return;
    });
    
});
/**
 * 加载数据，公共方法
 * 
 */
function  fillDivInfo_single(url,radioName, customer){

	$.ajax({
		type:"post",
		url: url,
		success:function(responseText){
			array_2 = eval(responseText);
			var str = "<table style='width: 100%;height:270px; background: #fff;'>";
			for(var i =0;i<array_2.length;i++){
				str +="<tr><td class='list_td'><input style='margin-left:20px;' type='radio' ";
				if(customer == array_2[i].id || tempids == array_2[i].id){
					str +=" checked='checked'";	
				}
				str += " name='"+radioName+"' value='";
				str += array_2[i].id;
				
				str +="'/>";
				str +=array_2[i].name;
				str +="</td></tr>";			
			}
			for(i;i<10;i++){
				str +="<tr><td style='height: 100%'></td></tr>";
			}
			str +="</table>";
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
		$("#sel_customer_ids").val(role_location_customer_ids);
	}
	closeSelectDiv('customerDiv');
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
	$('#sel_location_ids').val(user_locations);
	
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
function  fillLocationDivInfo(url,checkboxName,userLocations){

	$.ajax({
		type:"post",
		url: url,
		success:function(responseText){
			array = eval(responseText);
			var str = "<table style='width: 100%;height:270px; background: #fff;'>";
			for(var i =0;i<array.length;i++){
				str +="<tr><td class='list_td'><input style='margin-left:20px;' type='checkbox' ";
				
				for(var j=0; j < userLocations.length-1; j++){
					if(array[i].id==userLocations[j]){
						str +=" checked='checked'";	
						break;
					}
				}
				
				str += " name='"+checkboxName+"' value='";
				str += array[i].id;
				str +="'/>";
				str +=array[i].name;
				str +="</td></tr>";	
			}
			for(i;i<10;i++){
				str +="<tr><td style='height: 100%'></td></tr>";
			}
			str +="</table>";
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
/**
 * 加载数据，公共方法
 * 
 */
function  fillDivInfo(url,checkboxName,customers){
	
	
	
	
	$.ajax({
		type:"post",
		url: url,
		success:function(responseText){
		array = eval(responseText);
		var str = "<table style='width: 100%;height:270px; background: #fff;overflow:auto;'>";
		
		for(var i =0;i<array.length;i++){
			
			str +="<tr><td class='list_td'><input style='margin-left:20px;' type='checkbox' ";
			
			for(var j = 0; j < customers.length;j++){
				if(array[i].id==customers[j] || array[i].id==tmpcustomer[j]){
					str +=" checked='checked'";	
					break;
				}
			}
			
			str += " name='"+checkboxName+"' value='";
			str += array[i].id;
			str +="'/>";
			str +=array[i].name;
			str +="</td></tr>";			
		}
		for(i;i<10;i++){
			str +="<tr><td style='height: 100%'></td></tr>";
		}
		str +="</table>";
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
//	alert(loadID);
	role_location_customer_ids = "";		//绑定角色或地区或广告商的ID集合
	var  count =  0;
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
		$("#sel_customer_ids").val(role_location_customer_ids);
	}
	closeSelectDiv('roleDiv');
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
	 $('#tick').checked="";//控制全选按钮
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