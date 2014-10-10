<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
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
<script language="javascript" type="text/javascript" src="<%=path %>/js/marketingRule/addMarketingRule.js"></script>
<script language="javascript" type="text/javascript" type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/util/util.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/util/tools.js"></script>

<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.widget.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.tabs.js"></script>
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.accordion.js"></script>

<script type="text/javascript">
var editorFlag  = "save";

            //返回按钮
    		function goBack(){
    			window.location.href="page/marketingrule/listMarketingRule.do?method=listMarketingRule";
    		}
</script>
<input id="projetPath" type="hidden" value="<%=path%>"/>
<style>
	.easyDialog_wrapper{ width:1000px;color:#444; border:3px solid rgba(0,0,0,0); -webkit-border-radius:5px; -moz-border-radius:5px; border-radius:5px; -webkit-box-shadow:0 0 10px rgba(0,0,0,0.4); -moz-box-shadow:0 0 10px rgba(0,0,0,0.4); box-shadow:0 0 10px rgba(0,0,0,0.4); display:none; font-family:"Microsoft yahei", Arial; }
	.easyDialog_text{}
</style>
</head>

<body class="mainBody">

<div class="detail">
    <div style="position: relative">
    	<form action="addPosition.do?method=save"  id="addPositionform" name="addPositionform" method="post">
        <table cellspacing="1" class="content" align="left">
            <tr class="title">
                <td colspan="4">营销规则信息</td>
            </tr>
            <tr>
                <td width="12%" align="right">
                	<span class="required">*</span>
                	规则ID：
                </td>
                <td width="33%">
                     <input id="ruleId" name="rule.id" type="text" />
                     <span id="operatorFullName_error"></span>
                </td>
                <td width="12%" align="right">
                	<span class="required">*</span>
                	规则名称：
                </td>
                <td width="33%">       
                    <input id="ruleName" name="rule.name" type="text" />
                    <span id="operatorName_error"></span>
                </td>
            </tr>
            <tr>             
                <td width="12%" align="right">
                	<span class="required">*</span>
                	广告位：
                </td>
                <td width="33%">
                    

							<input id="position" type="text" class="e_input" onfocus="this.className='e_inputFocus'" onblur="this.className='e_input'"  maxlength="80" readonly="readonly"/>							
							<input id="positionId" type="hidden" />
							<input id="isChannel" type="hidden" />
							<input id="isAllTime" type="hidden" />
							<input id="choosePosition" type="button" value="选择 " class="btn"/>

                    <input id="findRule" type="button" value="查看已有规则" class="btn" onclick=''/>
                    <span id="operatorCode_error"></span>
                </td>
                <td width="12%" align="right">
                	<span class="required">*</span>
                	区域：
                </td>
                <td width="33%">
                   <input id="chooseArea" type="button" class="btn" value="查询地区" />
                   
                </td>
            </tr>
            <tr>
                <td width="12%" align="right">
                	<span class="required">*</span>
                	开始时间：
                </td>
                <td width="33%">
                    <select id="startTime"   onchange="selectOptionVal(this.id)"  class="e_select" onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'" >
								<option id="ad_id" value="-1">请选择...</option>
								<c:forEach items="${startTimeList}" var="startTime">
									<option  value="${startTime.areaName }">${startTime.ruleName }</option>
								</c:forEach>
					</select>
                    <span id="licenseNum_error"></span>
                </td>
                <td width="12%" align="right">
                	<span class="required">*</span>
                	结束时间：
                </td>
                <td width="33%">
                    <select id="endTime" onchange="selectOptionVal(this.id)"  class="e_select" onfocus="this.className='e_selectFocus'" onblur="this.className='e_select'" >
								<option id="ad_id" value="-1">请选择...</option>
								<c:forEach items="${endTimeList}" var="endTime">
									<option  value="${endTime.channelName }">${endTime.ruleName }</option>
								</c:forEach>
					</select>
                </td>
            </tr>
            
            
            
            
        </table>
		<table cellspacing="1" class="searchList">
			<tr class="title">
                <td colspan="8">已绑定区域</td>
            </tr>
		      <tr id="bindingPositionArea" >
		      <td>区域</td>
		        <td>频道</td>         
		       </tr>
    </table>
    </form>
    </div>
    <div align="center" class="action">
        <input id="addRuleButton" onclick="firstSubmit()" name="addContractButton" type="button" class="btn" value="确 定"/>&nbsp;&nbsp;&nbsp;&nbsp;
        <input id="modifyContractButton" onclick="goBack();" name="modifyContractButton" type="button" class="btn"  value="取消"/>
    </div>
</div>

<div id="system-dialog" title="友情提示">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>
    <span id="content"></span>
  </p>
</div>

</body>
</html>