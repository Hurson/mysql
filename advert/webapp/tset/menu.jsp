<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma","No-cache");     
    response.setHeader("Cache-Control","no-cache");      
    response.setDateHeader("Expires",   -10);     
%>

<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link id="maincss" href="<%=request.getContextPath()%>/css/new/main.css" rel="stylesheet" type="text/css" media="all"/>
    <script language="javascript" type="text/javascript" src="<%=path %>/js/jquery-1.4.4.js"></script>
    <script type='text/javascript' src='<%=path%>/js/jquery.min.js'></script>
    <script type='text/javascript' src='<%=path%>/js/avit.js'></script>
    <script type="text/javascript">
$(window).resize(function() {
	resizeMe();
});
function resizeMe(){
	var iframe = document.getElementById("I5");
	var iframe2 = document.getElementById("frame_content");
	try{
	var dHeight = document.documentElement.clientHeight-70;
	var height = Math.max(526, dHeight);
	iframe.height =  height;
	iframe2.height =  height;
	}catch (ex){}
}

</script>
    <script type="text/javascript" defer="defer">
    
    
    /** 
 * 栏目显示的js 
 */
function  showColumn(){
	
	var menu_first = ['ggzcgl','ggstfgl','bpgl','xtgl','ggwgl','ggsgl','htgl','tfclgl','yxgzgl','ddgl','flsjtbgl','xtcx','rzgl']; 
	

var menu_second = ['qbb','zybwh','mrscwh','xtcspz','hylxsjpz ','yhjbwh','ggwbgl','mrscpz','dggwbgl','ggwglwh','zcgl','scsc','tcwjgl','yysscsh','yyszcgl','tfqyxxgl','pdxxgl','yhqygl','yhjbgl','yhhylbcx','zcsxxgl','tpggwh','spggwh','wzggwh','dcwjggwh','yxgzglwh','ggsxz','ggswh','ggssh','htwh','htsh','ddwh','ddsh','fbgl','yhgl','jsgl','lmgl','ggmrpz','tfptpz','sjjkpz','ggxswzpz','tbcps','cpxxcx','ypflcx','ypxxcx','dtfclwh','tfclwh','tfclsh','tfclzdy','jzppgl','htsh','tbcpyp','czrz','wjmbwh','pdzgl','uimsf','scsjb','zxsj','rbb','zbb','ybb','hkpdzgl','zmggdd'];
	var menu_second = ['qbb','zybwh','mrscwh','xtcspz','hylxsjpz ','yhjbwh','ggwbgl','mrscpz','dggwbgl','ggwglwh','zcgl','scsc','tcwjgl','yysscsh','yyszcgl','tfqyxxgl','pdxxgl','yhqygl','yhjbgl','yhhylbcx','zcsxxgl','tpggwh','spggwh','wzggwh','dcwjggwh','yxgzglwh','ggsxz','ggswh','ggssh','htwh','htsh','ddwh','wxddwh','ddsh','wxddsh','fbgl','yhgl','jsgl','lmgl','ggmrpz','tfptpz','sjjkpz','ggxswzpz','tbcps','cpxxcx','ypflcx','ypxxcx','dtfclwh','tfclwh','wxtfclsh','tfclsh','tfclzdy','jzppgl','htsh','tbcpyp','czrz','wjmbwh','pdzgl','uimsf','scsjb','zxsj','rbb','zbb','ybb','hkpdzgl','zmggdd'];

	
	var columns = $('#columns').val();//
	var userId = $.trim($('#userId').val());  //用户的ID,    0表示是超级管理员

	
		if('' != columns){
			var arr = eval(columns);
			var showMenuRight = true;
			for(var i = 0 ; i < arr.length; i++){
				$('#'+arr[i].columnCode).show();//菜单展示
			}
			//收起左侧菜单
		    $('.dropdown').slideUp('slow');	
			return;
		}else{
			alert('没有权限');
			return;
		}
	
	
}
        $(document).ready(function () {
            $('li.button a').click(function (e) {
                $('li.button a').attr('class', '');
                var dropDown = $(this).parent().next();
                $('.dropdown').not(dropDown).slideUp('slow');
                dropDown.slideToggle('slow');
                this.className = 'focus';
                e.preventDefault();
            })
            $('.dropdown').slideUp('slow');
        });

        function openPage(url, tabId, fname, name) {
            window.parent.frames["mainFrame"].path = '首页 >> ' + fname + ' >> ' + name;
            window.parent.frames["mainFrame"].openTab(url.substring(1), tabId, name);
        }
        function changePassword(userId) {
        	var user = '${USER_LOGIN_INFO.userName}';
        	if (user==null || user=='')
        	{
        		return ;	
        	}
            var url = "/page/sysconfig/changePassword.do?userId=" + userId;
            //parent.frames['mainFrame'].location = url;
            window.parent.frames["mainFrame"].openTab(url.substring(1), '1', '修改密码');
        }
        function getDisplay(objname)
        {
        	return document.getElementById(objname).style.display;
        }
    </script>
</head>

<body class="leftBody" onload="resizeMe();showColumn();">
<!-- left_menu start -->
<div class="title">
<a href="#">${USER_LOGIN_INFO.userName}</a>
    <a href="javascript:changePassword('${USER_LOGIN_INFO.userId}');">修改密码</a>

</div>
<div class="menuList">
    <div id="main">
        <ul class="">
	        <li class="menu">
	            <ul>
	                <li id="xtgl" style="display: none;" class="button" style="list-style: none"><a href="#"><img src="<%=request.getContextPath()%>/images/filder.gif" width="15" height="13"/>系统管理</a></li>
	                <li class="dropdown">
	                    <ul>                                                                  
	                    	
	                    	<li id="yhgl" style="display: none;" ><a href="javascript:openPage('/page/sysconfig/queryUserList.do', '11', '用户维护', '用户维护')">&gt;&gt;&nbsp;用户维护</a></li>
	                        <li id="jsgl" style="display: none;"><a href="#" onclick="openPage('/page/sysconfig/queryRoleList.do', '12', '角色维护', '角色维护')">&gt;&gt;&nbsp;角色维护</a></li>
	                        <!-- <li id="pdwh" style="display: none;"><a href="#" onclick="openPage('/page/sysconfig/queryChannelList.do', '13', '频道维护', '频道维护')">&gt;&gt;&nbsp;频道维护</a></li> -->
	                        <li id="yhjbwh" style="display: none;"><a href="#" onclick="openPage('/page/sysconfig/queryUserRankList.do', '14', '用户级别维护', '用户级别维护')">&gt;&gt;&nbsp;用户级别维护</a></li>
							<li id="hylxsjpz" style="display: none;"><a href="#" onclick="openPage('/page/sysconfig/queryUserTradeList.do', '15', '行业类型数据配置', '行业类型数据配置')">&gt;&gt;&nbsp;行业类型数据配置</a></li>
	                    	<li id="xtcspz" style="display: none;"><a href="#" onclick="openPage('/page/sysconfig/allConfigList.do', '16', '系统参数配置', '系统参数配置')">&gt;&gt;&nbsp;系统参数配置</a></li>
	                    	<li id="pdzgl" style="display: none;"><a href="#" onclick="openPage('/page/channelGroup/queryChannelGroupList.do', '17', '频道组管理', '频道组管理')">&gt;&gt;&nbsp;频道组管理</a></li>
	                    	<li id="hkpdzgl" style="display: none;"><a href="#" onclick="openPage('/page/npvrChannelGroup/queryChannelGroupListNpvr.do', '19', '回放频道组管理', '回放频道组管理')">&gt;&gt;&nbsp;回放频道组管理</a></li>
	                    	<li id="uimsf" style="display: none;"><a href="#" onclick="openPage('/page/sysconfig/intoUpdateUI.do', '18', '更新UI描述符', '更新UI描述符')">&gt;&gt;&nbsp;更新UI描述符</a></li>
	                    	<li id="scsjb" style="display: none;"><a href="#" onclick="openPage('/page/sysconfig/initPage.do', '20', '上传升级包', '上传升级包')">&gt;&gt;&nbsp;上传升级包</a></li>
	                    	<li id="zxsj" style="display: none;"><a href="#" onclick="openPage('/page/sysconfig/startUpgrade.do', '21', 'OCG在线升级', 'OCG在线升级')">&gt;&gt;&nbsp;OCG在线升级</a></li>
	                    </ul>
	                </li>
	            </ul>
	        </li>
	        <!-- 
	        <li class="menu">
	            <ul>
	                <li id="xtcx" style="display: none;" class="button" style="list-style: none"><a href="#"><img src="<%=request.getContextPath()%>/images/filder.gif" width="15" height="13"/>系统查询</a></li>
	                <li class="dropdown">
	                    <ul>
	                    	<li id="tfqyxxgl" style="display: none;"><a href="#"  onclick="openPage('/page/ReleaseArea/ReleaseArea_list.do', '21', '投放区域信息管理', '投放区域信息管理')">&gt;&gt;&nbsp;投放区域信息管理</a></li>
	                        <li id="cpxxcx" style="display: none;" ><a href="#"  onclick="openPage('/vod/listProduct.do?selId=&selName=&pageNo=1', '22', '产品信息查询', '产品信息查询')">&gt;&gt;&nbsp;产品信息查询</a></li>
	                        <li id="ypxxcx" style="display: none;"><a href="#"  onclick="openPage('/vod/listAsset.do?selId=&selName=&pageNo=1', '23', '影片信息查询', '影片信息查询')">&gt;&gt;&nbsp;影片信息查询</a></li>
	                        <li id="yhqygl" style="display: none;"><a href="#"  onclick="openPage('/page/userArea/userArea_list.do', '24', '用户区域查询', '用户区域查询')">&gt;&gt;&nbsp;用户区域查询</a></li>
	                    </ul>
	                </li>
	            </ul>
	        </li>
	         -->
           	<li class="menu">
	            <ul>
	                <li id="ggwgl" style="display: none;" class="button" style="list-style: none"><a href="#"><img src="<%=request.getContextPath()%>/images/filder.gif" width="15" height="13"/>广告位管理</a></li>
	                <li class="dropdown">
	                    <ul>
	                    	<li id="ggwbgl" style="display: none;"><a href="#"  onclick="openPage('/page/position/queryPositionPackageList.do', '36', '广告位查询', '广告位查询')">&gt;&gt;&nbsp;广告位查询</a></li>
	                        <li id="mrscpz" style="display: none;"><a href="#"  onclick="openPage('/page/position/queryResourceList.do', '37', '默认素材配置', '默认素材配置')">&gt;&gt;&nbsp;默认素材配置</a></li>
	                    	<li id="dggwgl" style="display: none;"><a href="#"  onclick="openPage('/<%=path%>/dposition/queryDPositionPackageList.action', '38', '无线广告位查询', '无线广告位查询')">&gt;&gt;&nbsp;无线广告位查询</a></li>
	                    </ul>
	                    <!-- 
	                    <ul>
	                    	<li id="ggwglwh" style="display: none;"><a href="#"  onclick="openPage('/page/position/queryPositionPage.do?method=queryPage', '31', '广告位管理', '广告位管理')">&gt;&gt;&nbsp;广告位管理</a></li>
	                        <li id="tpggwh" style="display: none;"><a href="#"  onclick="openPage('/page/imageSpecification/queryImageManager.do', '32', '图片规格维护', '图片规格维护')">&gt;&gt;&nbsp;图片规格维护</a></li>
	                        <li id="spggwh" style="display: none;"><a href="#"  onclick="openPage('/page/videoSpecification/queryVideoManager.do', '33', '视频规格维护', '视频规格维护')">&gt;&gt;&nbsp;视频规格维护</a></li>
	                        <li id="wzggwh" style="display: none;"><a href="#"  onclick="openPage('/page/textSpecification/queryTextManager.do', '34', '文字规格维护', '文字规格维护')">&gt;&gt;&nbsp;文字规格维护</a></li>
	                    	<li id="dcwjggwh" style="display: none;"><a href="#"  onclick="openPage('/page/questionnaireSpecification/queryQuestionManager.do', '35', '调查问卷规格维护', '调查问卷规格维护')">&gt;&gt;&nbsp;调查问卷规格维护</a></li>
	                    </ul>
	                      -->
	                </li>
	            </ul>
	        </li>
			<li class="menu">
	            <ul>
	                <li id="ggsgl" style="display: none;" class="button" style="list-style: none"><a href="#"><img src="<%=request.getContextPath()%>/images/filder.gif" width="15" height="13"/>广告商管理</a></li>
	                <li class="dropdown">
	                    <ul>
	                    	
	                    	
	                    	<li id="ggsxz" style="display:none;"><a href="#"  onclick="openPage('/page/customer/adCustomerMgr_saveCustomerBackUpRedirect.do', '43', '新增广告商', '新增广告商')">&gt;&gt;&nbsp;新增广告商</a></li>
	                    	<li id="ggswh" style="display: none;"><a href="#"  onclick="openPage('/page/customer/adCustomerMgr_list.do', '41', '广告商维护', '广告商维护')">&gt;&gt;&nbsp;广告商维护</a></li>
	                        <li id="ggssh" style="display: none;"><a href="#"  onclick="openPage('/page/customer/adCustomerMgr_listAudit.do', '42', '广告商审核', '广告商审核')">&gt;&gt;&nbsp;广告商审核</a></li>
	                    </ul>
	                </li>
	            </ul>
	        </li>
	        <!-- 
			<li class="menu">
	            <ul>
	                <li id="yxgzgl" style="display: none;" class="button" style="list-style: none"><a href="#"><img src="<%=request.getContextPath()%>/images/filder.gif" width="15" height="13"/>营销规则管理</a></li>
	                <li class="dropdown">
	                    <ul>
	                    	<li id="yxgzglwh" style="display: none;"><a href="#"  onclick="openPage('/page/marketingrule/listMarketingRule.do?method=listMarketingRule', '51', '营销规则管理', '营销规则管理')">&gt;&gt;&nbsp;营销规则管理</a></li>
	                    </ul>
	                </li>
	            </ul>
	        </li>
	         -->
			<li class="menu">
	            <ul>
	                <li id="htgl" class="button" style="display: none;"><a href="#"><img src="<%=request.getContextPath()%>/images/filder.gif" width="15" height="13"/>合同管理</a></li>
	                <li class="dropdown">
	                    <ul>
	                    	<li id="htwh" style="display: none;" ><a href="#"  onclick="openPage('/page/contract/queryContractList8.do?method=queryContractList', '61', '合同维护', '合同维护')">&gt;&gt;&nbsp;合同维护</a></li>
							<li id="htsh" style="display: none;" ><a href="#"  onclick="openPage('/page/contract/auditContractList.do?method=queryContractList', '62', '合同审核', '合同审核')">&gt;&gt;&nbsp;合同审核</a></li>
	                    	
	                    </ul>
	                </li>
	            </ul>
	        </li>
			<li class="menu">
	            <ul>
	                <li id="tfclgl" style="display: none;"  class="button" style="list-style: none"><a href="#"><img src="<%=request.getContextPath()%>/images/filder.gif" width="15" height="13"/>投放策略管理</a></li>
	                <li class="dropdown">
	                    <ul>
	                    <!-- 
	                    	<li id="jzppgl" style="display: none;"><a href="#"  onclick="openPage('/page/precise/listPrecise.do?method=getAllPreciseList&ployId=2', '71', '精准匹配管理', '精准匹配管理')">&gt;&gt;&nbsp;精准匹配管理</a></li>
						
							<li id="tfclwh" style="display: none;"><a href="#"  onclick="openPage('/page/ploy/initPloy.do?method=initPloy&ployId=1', '72', '投放策略维护', '投放策略维护')">&gt;&gt;&nbsp;投放策略维护</a></li>
					 		--><li id="tfclwh" style="display: none;" ><a href="#"  onclick="openPage('/page/ploy/queryPloyList.do', '72', '策略维护', '策略维护')">&gt;&gt;&nbsp;策略维护</a></li>
					 		<li id="tfclsh" style="display: none;" ><a href="#"  onclick="openPage('/page/ploy/queryCheckPloyList.do', '73', '策略审核', '策略审核')">&gt;&gt;&nbsp;策略审核</a></li>
						 	<li id="dtfclwh" style="display: none;" ><a href="#"  onclick="openPage('/<%=path%>/dploy/queryDPloyList.action', '75', '无线策略维护', '无线策略维护')">&gt;&gt;&nbsp;无线策略维护</a></li> 	
					 		<li id="wxtfclsh" style="display: none;" ><a href="#"  onclick="openPage('/<%=path%>/dploy/queryDPloyList.action?ploy.status=1', '76', '无线策略审核', '无线策略审核')">&gt;&gt;&nbsp;无线策略审核</a></li>
					 		<li id="tfclzdy" style="display: none;" ><a href="#"  onclick="openPage('/page/ploy/queryNoAdPloyList.do', '74', '禁播策略维护', '禁播策略维护')">&gt;&gt;&nbsp;禁播策略维护</a></li>
					 		
			      	
	                    </ul>
	                </li>
	            </ul>
	        </li>
			<li class="menu">
	            <ul>
	                <li id="ggzcgl" style="display: none;" class="button" style="list-style: none"><a href="#"><img src="<%=request.getContextPath()%>/images/filder.gif" width="15" height="13"/>素材管理</a></li>
	                <li class="dropdown">
	                    <ul>
	                    <!-- 
	                    <li id="zcgl" style="display: none;"><a href="#"  onclick="openPage('/page/AdContent/adContentMgr_list.do', '81', '查询资产', '查询资产')">&gt;&gt;&nbsp;素材维护</a></li>
	                    <li id="yysscsh" style="display: none;"><a href="#"  onclick="openPage('/page/AdContent/adContentMgr_listAuditMetas.do', '84', '运营商素材审核', '运营商素材审核')">&gt;&gt;&nbsp;资产审核</a></li>
	                     -->
	                       
	                        <li id="zcgl" style="display: none;"><a href="#"  onclick="openPage('/page/meterial/queryMeterialList.do', '81', '素材维护', '素材维护')">&gt;&gt;&nbsp;素材维护</a></li>		
	                        <li id="mrscwh" style="display: none;"><a href="#"  onclick="openPage('/page/meterial/queryDefaultMeterialList.do', '82', '默认素材维护', '默认素材维护')">&gt;&gt;&nbsp;默认素材维护</a></li>
							<li id="yysscsh" style="display: none;"><a href="#"  onclick="openPage('/page/meterial/auditMaterialList.do', '84', '素材审核', '素材审核')">&gt;&gt;&nbsp;素材审核</a></li>
							<li id="wxscwh" style="display: none;"><a href="#"  onclick="openPage('/<%=path%>/dmaterial/queryMaterialList.do', '85', '无线素材维护', '无线素材维护')">&gt;&gt;&nbsp;无线素材维护</a></li>
						<!-- 
							<li id="zcsxxgl" style="display: none;" ><a href="#"  onclick="openPage('/page/meterial/queryUponLineList.do','86','资产上下线管理','资产上下线管理')">&gt;&gt;&nbsp;资产上下线</a></li>
						 -->
						 	<li id="wjmbwh" style="display: none;" ><a href="#"  onclick="openPage('/page/meterial/queryQuestionTemplateList.do','87','问卷模板维护','问卷模板维护')">&gt;&gt;&nbsp;问卷模板维护</a></li>
							<li id="zybwh" style="display: none;" ><a href="#"  onclick="openPage('/page/meterial/intoUploadUiMaterial.do','88','资源包维护','资源包维护')">&gt;&gt;&nbsp;资源包维护</a></li>
							 
							<!--<li id="scsc" style="display: none;"><a href="#"  onclick="openPage('/page/material/toUploaldFile.do', '82', '上传素材', '上传素材')">&gt;&gt;&nbsp;上传素材</a></li>
							<li id="tcwjgl" style="display: none;"><a href="#"  onclick="openPage('/page/AdContent/adContentMgr_listAuditMetas.do', '83', '调查问卷管理', '调查问卷管理')">&gt;&gt;&nbsp;调查问卷管理</a></li>
							 -->
	                    </ul>
	                </li>
	            </ul>
	        </li>
			<li class="menu">
	            <ul>
	                <li id="ddgl" style="display: none;" class="button"><a href="#"><img src="<%=request.getContextPath()%>/images/filder.gif" width="15" height="13"/>订单管理</a></li>
	                <li class="dropdown">
	                    <ul>
	                    	<li id="ddwh" style="display: none;"><a href="#"  onclick="openPage('/page/order/queryOrderList.do', '91', '订单维护', '订单维护')">&gt;&gt;&nbsp;订单维护</a></li>
							<li id="ddsh" style="display: none;"><a href="#"  onclick="openPage('/page/order/queryOrderAuditList.do', '92', '订单审核', '订单审核')">&gt;&gt;&nbsp;订单审核</a></li>
							<li id="wxddwh" style="display: none;"><a href="#"  onclick="openPage('/<%=path%>/dorder/queryDOrderList.action', '94', '无线订单维护', '无线订单维护')">&gt;&gt;&nbsp;无线订单维护</a></li>
							<li id="wxddsh" style="display: none;"><a href="#"  onclick="openPage('/<%=path%>/dorder/queryAuditDOrderList.action', '95', '无线订单审核', '无线订单审核')">&gt;&gt;&nbsp;无线订单审核</a></li>
							<li id="zmggdd" style="display: none;"><a href="#"  onclick="openPage('/page/order/findSubtitleList.do', '93', '字幕广告订单', '字幕广告订单')">&gt;&gt;&nbsp;字幕广告订单</a></li>
	                    </ul>
	                </li>
	            </ul>
	        </li>
			<li class="menu">
	            <ul>
	                <li id="bpgl" style="display:none;" class="button" ><a href="#"><img src="<%=request.getContextPath()%>/images/filder.gif" width="15" height="13"/>报表管理</a></li>
	                <li class="dropdown">
	                    <ul>
							<li id="rbb" style="display: none;"><a href="#"  onclick="openPage('/page/report/queryDayReportList.do?report.reportType=0', '1091', '分时报表', '分时报表')">&gt;&gt;&nbsp;分时报表</a></li>
							<li id="zbb" style="display: none;"><a href="#"  onclick="openPage('/page/report/queryWeekReportList.do?report.reportType=1', '1092', '日报表', '日报表')">&gt;&gt;&nbsp;日报表</a></li>
							<li id="ybb" style="display: none;"><a href="#"  onclick="openPage('/page/report/queryMonthReportList.do?report.reportType=2', '1093', '月报表', '月报表')">&gt;&gt;&nbsp;月报表</a></li>
	                        <li id="qbb" style="display: none;"><a href="#"  onclick="openPage('/page/report/queryQuestionReportList.do?report.reportType=2', '1094', '问卷报表', '问卷报表')">&gt;&gt;&nbsp;问卷报表</a></li>
	                   
	                    </ul>
	                </li>
	            </ul>
	        </li>
			
			
	        <li class="menu">
	            <ul>
	                <li id="rzgl" style="display:none;" class="button" style="display: none;"><a href="#"><img src="<%=request.getContextPath()%>/images/filder.gif" width="15" height="13"/>日志管理</a></li>
	                <li class="dropdown">
	                    <ul>
	                    	<li id="czrz" style="display: none;"><a href="#"  onclick="openPage('/page/log/queryOperateLogList.do', '121', '操作日志', '操作日志')">&gt;&gt;&nbsp;操作日志</a></li>
	                    </ul>
	                </li>
	            </ul>
	        </li>
        </ul>
        <div style="clear: both">
        </div>
    </div>
	<input id="columns" type="hidden" value='<%=request.getSession().getAttribute("coulumns")%>'/>
	<input id="userId" type="hidden" value="<%=request.getSession().getAttribute("userId")%>"/>
</div>

</body>
</html>