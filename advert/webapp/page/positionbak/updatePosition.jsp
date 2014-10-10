<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery-1.4.4.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<title>广告系统</title>
<script>
$(function(){   
    $("#bm").find("tr:even").addClass("treven");  //奇数行的样式
    $("#bm").find("tr:odd").addClass("trodd");  //偶数行的样式
});

//公共方法--获得dom对象
	function getObj(id){
		var obj = document.getElementById(id);
		return obj;	
	}
	
	//公共方法-选择下拉框的值
	function selectOptionVal(id){
		var optionVal="-1";
		var selectPobj = getObj(id); 
		var options   = selectPobj.options; 
		for(var i = 0; i < options.length; i++){
			var optionResult = options[i].selected ;
			if(optionResult){
				optionVal = options[i].value;
			}
		}
		return optionVal;
	}

--></script>
<style>
.treven{ background:#FFFFFF;}
.trodd{ background:#f6f6f6;}
</style>
</head>

<body>
<form action=""  id="form1" name="form1"  >
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td style=" padding:4px;">
				<table cellpadding="0" cellspacing="1" width="100%" class="tablea" >
					<tr>
						<td class="formTitle" colspan="99">
							<span>添加广告位</span>
						</td>
					</tr>
					
					<tr>
						<td class="td_label">广告位类型编码：</td>
						<td class="td_input">
							<input id="typeCode" name="typeCode" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
						<td class="td_label">广告位特征值：</td>
						<td class="td_input">
							<input id="eigenValue" name="eigenValue" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
					</tr>
					
					<tr>
						<td class="td_label">广告位中文名称：</td>
						<td class="td_input">
							<input id="chiName" name="chiName" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
						<td class="td_label">广告位英文名称：</td>
						<td class="td_input">
							<input id="engName" name="engName" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
					</tr>
					
					<tr>
						<td class="td_label">区域-左：</td>
						<td class="td_input">
							<input id="left" name="left" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
						<td class="td_label">区域-右：</td>
						<td class="td_input">
							<input id="right" name="right" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
					</tr>
					
					<tr>
						<td class="td_label">区域-高：</td>
						<td class="td_input">
							<input id="high" name="high" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
						<td class="td_label">区域-宽：</td>
						<td class="td_input">
							<input id="width" name="width" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
					</tr>
					
					<tr>
						<td class="td_label">区域-颜色：</td>
						<td class="td_input">
							<input id="color" name="color" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
						<td class="td_label">栏目：</td>
						<td class="td_input" colspan="3">
							<input id="positionColume" name="positionColume" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
					</tr>
					<tr>
						<td class="td_label">素材类型：</td>
						<td class="td_input">
							<select id="materialType" name="materialType" class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'">
									<option value="-1">请选择</option>
									<option value="1">视频</option>
									<option value="2">图片</option>
									<option value="3">文字</option>
									<option value="4">问卷</option>
							</select>
						</td>
						<td class="td_label">广告位类型：</td>
						<td class="td_input">
							<select id="positionCategory" name="positionCategory" class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'">
									<option value="-1">请选择</option>
									<option value="0">开机画面广告</option>
									<option value="1">操作界面广告</option>
									<option value="2">DTV导视节目广告</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="td_label">是否高清：</td>
						<td class="td_input">
							<select id="isHD" name="isHD" class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'">
									<option value="-1">请选择</option>
									<option value="0">否</option>
									<option value="1">是</option>
									<option value="1">都支持</option>
							</select>		
						</td>
						<td class="td_label">是否轮询：</td>
						<td class="td_input">
							<select id="isPolling" name="isPolling" class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'">
									<option value="-1">请选择</option>
									<option value="0">否</option>
									<option value="1">是</option>
							</select>		
						</td>
					</tr>
					
					<tr>
						<td class="td_label">是否叠加：</td>
						<td class="td_input">
							<select id="isOverlying" name="isOverlying" class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'">
									<option value="-1">请选择</option>
									<option value="0">否</option>
									<option value="1">是</option>
							</select>		
						</td>
						<td class="td_label">投放方式：</td>
						<td class="td_input">
							<select id="advertiseWay" name="advertiseWay" class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'">
									<option value="-1">请选择</option>
									<option value="1">单向</option>
									<option value="2">双向</option>
							</select>
						</td>
						
					</tr>
					
					<tr>
						<td class="td_label">停留时间：</td>
						<td class="td_input">
							<input id="dwellTime" name="dwellTime" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
						<td class="td_label">广告位背景图：</td>
						<td class="td_input">
							<input id="backupPicture" name="backupPicture" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
					</tr>
					<tr>
						<td class="td_label">轮询个数：</td>
						<td class="td_input">
							<input id="pollingCount" name="pollingCount" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
						<td class="td_label">价格：</td>
						<td class="td_input">
							<input id="price" name="price" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
					</tr>
					<tr>
						<td class="td_label">是否赠送：</td>
						<td class="td_input">
							<input id="isPresented" name="isPresented" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
						<td class="td_label">折扣：</td>
						<td class="td_input">
							<input id="isDiscount" name="isDiscount" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
					</tr>
					<tr>
						<td class="td_label">所属频道：</td>
						<td class="td_input">
							<input id="isPresented" name="isPresented" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
						<td class="td_label">所属区域：</td>
						<td class="td_input">
							<input id="isDiscount" name="isDiscount" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
					</tr>
					<tr>
						<td class="td_label">描述：</td>
						<td class="td_input" colspan="3">
							<input id="describe" name="describe" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" />		
						</td>
					</tr>
					<tr>
						<td class="formBottom" colspan="99">
							<input name="Submit" 
							type="button" 
							title="添加" 
							class="b_next" 
							value="添加" 
							onfocus="blur()"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
