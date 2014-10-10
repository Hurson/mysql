<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<link rel="stylesheet" type="text/css"
			href="<%=path%>/css/new/main.css" />
		<link href="<%=path%>/css/new/common/dhtmlx/tree/css/dhtmlxtree.css"
			rel="stylesheet" type="text/css" media="all" />
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxcommon.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/dhtmlxtree.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/css/new/common/dhtmlx/tree/js/test.js"
			type="text/javascript">
</script>

<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/Pager.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery-ui-timepicker-addon.css" type="text/css" />

<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/validate/jquery.validate.messages_cn.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-addon.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery.ui.datepicker-zh-CN.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/datepicker/jquery-ui-timepicker-zh-CN.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/precise/addPrecise.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>

<style>
	.easyDialog_wrapper{width:1000px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>


</head>
<input id="projetPath" type="hidden" value="<%=path%>"/>
<body class="mainBody"  >
  <input id="projetPath" type="hidden" value="<%=path%>" />
		<form id="saveForm" action="" method="post" name="saveForm">
			<div class="search">
				<div class="path">
					<img src="<%=path%>/images/new/filder.gif" width="15" height="13" />
					首页 >> 营销规则管理 >> 添加精准匹配
				</div>
				<div class="searchContent" >
			<div class="listDetail">
		    <div style="position: relative">	
		    	<table>
		    		<tr>
		    			<td>
		        		<table cellspacing="1" class="content" align="left" style="margin-bottom: 30px">
			            <tr class="title">
			                <td colspan="4">添加精准匹配</td>
			            </tr>
			            <tr>
			                <td colspan="2" align="center" height="25px" width="35px"><span class="required">*</span>精准匹配名称：</td>
			                <td colspan="2" >
			                	<input id="role_name" name="role.name" type="text" maxlength="80"/>
			                </td>
			            </tr>		     
					
					<tr>
						<td colspan="2" align="center" height="25px" width="35px"><span class="required">*</span>优先级：</td>
						<td colspan="2">
							<input id="priority" name="precise.priority" type="text"  onblur="this.className='e_input'"  maxlength="80"/>
						</td>
					</tr> 
					
					
					<tr>
						<td  colspan="2"  align="center" height="25px" width="35px"><span class="required">*</span>选择匹配类型：</td>
						<td  colspan="2" class="td_input">
							<select  id="sel_type_id" onchange="selectOptionVal(this.id)"   class="e_select"  onfocus="this.className='e_selectFocus'" onblur="this.className='e_select';checkType()" >
								<option id="ad_id" value="-1">请选择...</option>
									<c:forEach items="${typeList}" var="typeBean">
										<option  value="${typeBean.key }">${typeBean.matchName }</option>
									</c:forEach>
							</select>	
						</td>
					</tr>
					
					<tr>
						<td  width="15%" align="right"><span class="required">*</span>选择策略：</td>
						<td width="35%">
							<input id="choosePloy" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="点击选择策略" />
						</td>
						<td  width="15%" align="right">已选择的策略：</td>
						<td width="35%">
							<input id="ployName" name="ployName" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"  maxlength="80" readonly="readonly"/>
							<input id="ployId" name="ployId" type="hidden"/>
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<div  colspan="4" id="product_div_id" style="display:none;">
								<table cellpadding="0" cellspacing="0" width="100%" >
									<tr>
										<td  width="15%" align="right">选择产品：</td>
										<td width="35%">
											<input id="chooseProduct" type="text" value="点击选择产品" />
										</td>
										<td width="15%" align="right">已选择的产品：</td>
										<td width="35%">
										<textarea id="productName"></textarea>
										<div id="product_div" style="display:none;" >
											<textarea id="product"></textarea>
										</div>
										</td>
									</tr>	
								</table>
							</div>	
						</td>
					</tr>	
					<tr>
						<td colspan="4">
							<div id="channel_div_id" style="display:none;">
								<table cellpadding="0" cellspacing="0" width="100%" >
									<tr>
										<td width="15%" align="right">选择回看频道：</td>
										<td width="35%">
											<input id="chooseChannel" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="点击选择回看频道" />
										</td>
										<td width="15%" align="right" >已选择的回看频道：</td>
										<td width="35%">
										<div id="channel_div" style="display:none;" >
											<textarea id="channel"
												class="e_textarea" onfocus="this.className='e_textareaFocus'"
												onblur="this.className='e_textarea'"></textarea>
										</div>
											<textarea id="channelName"
												class="e_textarea" onfocus="this.className='e_textareaFocus'"
												onblur="this.className='e_textarea'"></textarea>
										</td>
									</tr>	
								</table>
							</div>	
						</td>
					</tr>	
					
					<tr>
						<td colspan="4">
							<div id="key_div_id" style="display:none;">
								<table cellpadding="0" cellspacing="0" width="100%" >
									<tr>
										<td width="15%" align="right" >选择关键字：</td>
										<td width="35%">
											<input id="chooseKeyword" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="点击选择关键字" />
										</td>
										<td width="15%" align="right">已选择的关键字：</td>
										<td width="35%">
											<textarea id="keyword"
												class="e_textarea" onfocus="this.className='e_textareaFocus'"
												onblur="this.className='e_textarea'"></textarea>
										</td>
									</tr>	
								</table>
							</div>	
						</td>
					</tr>
					
					<tr>
						<td  colspan="4" >
							<div  id="sort_div_id" style="display:none;">
								<table cellpadding="0" cellspacing="0" width="100%" >
									<tr>
										<td width="15%" align="right" >选择影片分类：</td>
										<td width="35%">
											<input id="chooseAssetSort" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="点击选择影片分类" />
										</td>
										<td width="15%" align="right">已选择的影片分类：</td>
										<td width="35%">
											<div id="assetSort_div" style="display:none;" >
												<textarea id="assetSort"
													class="e_textarea" onfocus="this.className='e_textareaFocus'"
													onblur="this.className='e_textarea'"></textarea>
											</div>
											<textarea id="assetSortName"
												class="e_textarea" onfocus="this.className='e_textareaFocus'"
												onblur="this.className='e_textarea'"></textarea>
										</td>
									</tr>	
								</table>
							</div>	
						</td>
					</tr>
					
					<tr>
						<td  colspan="4">
							<div id="userarea_div_id" style="display:none;" >
								<table cellpadding="0" cellspacing="0" width="100%" >
									<tr>
										<td width="15%" align="right" >选择用户区域：</td>
										<td width="35%">
											<input id="chooseUserArea" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="点击选择用户区域" />
										</td>
										<td width="15%" align="right" >已选择的用户区域：</td>
										<td width="35%">
											<div id="userArea_div" style="display:none;" >
												<textarea id="userArea"
												class="e_textarea" onfocus="this.className='e_textareaFocus'"
												onblur="this.className='e_textarea'"></textarea>
											</div>
											<textarea id="userAreaName"
												class="e_textarea" onfocus="this.className='e_textareaFocus'"
												onblur="this.className='e_textarea'"></textarea>
										</td>
									</tr>	
								</table>
							</div>	
						</td>
					</tr>
					
					<tr>
						<td  colspan="4">
							<div id="userIndustrys_div_id" style="display:none;">
								<table cellpadding="0" cellspacing="0" width="100%" >
									<tr>
										<td width="15%" align="right">选择用户行业：</td>
										<td width="35%">
											<input id="chooseUserIndustrys" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="点击选择用户行业" />
										</td>
										<td width="15%" align="right" >已选择的用户行业：</td>
										<td width="35%">
											<div id="userIndustrys_div" style="display:none;" >
												<textarea id="userIndustrys"
													class="e_textarea" onfocus="this.className='e_textareaFocus'"
													onblur="this.className='e_textarea'"></textarea>
											</div>
											<textarea id="userIndustrysName"
												class="e_textarea" onfocus="this.className='e_textareaFocus'"
												onblur="this.className='e_textarea'"></textarea>
										</td>
									</tr>	
								</table>
							</div>	
						</td>
					</tr>
					
					<tr>
						<td  colspan="4">
							<div id="userLevels_div_id" style="display:none;">
								<table cellpadding="0" cellspacing="0" width="100%" >
									<tr>
										<td width="15%" align="right">选择用户级别：</td>
										<td width="35%">
											<input id="chooseUserLevels" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="点击选择用户级别" />
										</td>
										<td width="15%" align="right" >已选择的用户级别：</td>
										<td width="35%">
											<div id="userLevels_div" style="display:none;" >
												<textarea id="userLevels"
													class="e_textarea" onfocus="this.className='e_textareaFocus'"
													onblur="this.className='e_textarea'"></textarea>
											</div>
											<textarea id="userLevelsName"
												class="e_textarea" onfocus="this.className='e_textareaFocus'"
												onblur="this.className='e_textarea'"></textarea>
										</td>
									</tr>	
								</table>
							</div>	
						</td>
					</tr>
					
					<tr>
						<td  colspan="4">
							<div id="tvnNumber_div_id" style="display:none;">
								<table cellpadding="0" cellspacing="0" width="100%" >
									<tr>
										<td width="15%" align="right">选择TVN号段：</td>
										<td width="35%">
											<input id="chooseTvnNumber" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="点击选择TVN号段" />
										</td>
										<td width="15%" align="right" >已选择的TVN号段：</td>
										<td width="35%">
											<input id="tvnNumber" name="tvnNumber" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"  maxlength="80"/>
										</td>
									</tr>	
								</table>
							</div>	
						</td>
					</tr>
					
					<tr>
						<td colspan="4">
							<div  id="category_div_id" style="display:none;">
								<table cellpadding="0" cellspacing="0" width="100%" >
									<tr>
										<td width="15%" align="right" >选择结点：</td>
										<td width="35%">
											<input id="choosePlatCategory" type="text" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'" value="点击选择结点" />
										</td>
										<td width="15%" align="right" >已选择的结点：</td>
										<td width="35%">
											<div id="category_div" style="display:none;" >
												<textarea id="category"
													class="e_textarea" onfocus="this.className='e_textareaFocus'"
													onblur="this.className='e_textarea'"></textarea>
											</div>
											<textarea id="categoryName"
												class="e_textarea" onfocus="this.className='e_textareaFocus'"
												onblur="this.className='e_textarea'"></textarea>
										</td>
									</tr>	
								</table>
							</div>	
						</td>
					 </tr>
					<tr>
					<td width="20%" align="right" colspan="4">
						<input id="addPreciseButton" type="button" value="添加" onclick="firstSubmit();" class="btn" />
					</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
<div id="system-dialog" title="友情提示">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
    <span id="content"></span>
  </p>
</div>
</body>
</html>