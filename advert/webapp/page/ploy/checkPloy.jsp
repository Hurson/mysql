<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=path %>/css/platform.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/jquery/uploadify.css" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />
<link href="<%=path%>/css/Pager.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=path %>/css/menu_right_new1.css" type="text/css" />

<link id="maincss"  rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" media="all"/>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
	
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/tools.js"></script>

 <script src="<%=path %>/js/jquery/jquery-1.3.1.min.js" type="text/javascript"></script>
  <script src="<%=path %>/js/jquery/cluetip/jquery.bgiframe.min.js" type="text/javascript"></script>
  <script src="<%=path %>/js/jquery/cluetip/jquery.hoverIntent.js" type="text/javascript"></script>
  <script src="<%=path %>/js/jquery/cluetip/jquery.cluetip.js" type="text/javascript"></script>
 
  <link rel="stylesheet" href="<%=path %>/css/cluetip/jquery.cluetip.css" type="text/css" />



<title>广告系统</title>
<script type="text/javascript">
var resourcePath=$('#projetPath').val();
var postions=eval(${postionJson});
var areaChannels =  new Array();
var ploys = new Array();
var checkflag =false;
var firstflag=true;
window.onload = function() {
	//refreshPloyList();
	var starttimes = document.getElementsByName("starttime"); 
	if (starttimes!=null && starttimes.length>0)
	{
		document.getElementById("selectedtable0").style.display="";
		document.getElementById("selectedOption0").checked=true;
	}
	//直播、npvr 添加框
	var channelgroup = document.getElementsByName("channelgroup"); 
	
	if (channelgroup!=null && channelgroup.length>0)
	{
		var channelGroupType = document.getElementsByName("channelGroupType")[0].value;

		if("1" == channelGroupType){  //直播频道组
			document.getElementById("selectedtable10").style.display="";
			document.getElementById("selectedOption10").checked=true;
		}else if("2" == channelGroupType){  //回看频道组
			document.getElementById("selectedtable5").style.display="";
			document.getElementById("selectedOption5").checked=true;
		}		
	}
	
	// 回放频道 
	var playback = document.getElementsByName("preciseUiBean.playbackChannelId"); 
	if (playback!=null && playback.length>0)
	{
		document.getElementById("selectedtable16").style.display="";
		document.getElementById("selectedOption16").checked=true;
	}

	
	var product = document.getElementsByName("preciseUiBean.productId"); 
	if (product!=null && product.length>0)
	{
		document.getElementById("selectedtable1").style.display="";
		document.getElementById("selectedOption1").checked=true;
	}
	var assetkey = document.getElementsByName("preciseUiBean.assetKey"); 
	if (assetkey!=null && assetkey.length>0)
	{
		document.getElementById("selectedtable2").style.display="";
		document.getElementById("selectedOption2").checked=true;
	}
	var assetname = document.getElementsByName("preciseUiBean.assetName"); 
	if (assetname!=null && assetname.length>0)
	{
		document.getElementById("selectedtable8").style.display="";
		document.getElementById("selectedOption8").checked=true;
	}
	 var nvodType = document.getElementsByName("preciseUiBean.typeCode");
	if(nvodType!=null && nvodType.length>0)
	{
		document.getElementById("selectedtable18").style.display="";
		//document.getElementById("selectedOption18").checked=true;
	} 
	var assetCategory = document.getElementsByName("preciseUiBean.assetSortId"); 
	if (assetCategory!=null && assetCategory.length>0)
	{
		document.getElementById("selectedtable4").style.display="";
		document.getElementById("selectedOption4").checked=true;
	}
	
	var coiumn = document.getElementsByName("preciseUiBean.lookbackCategoryId"); 
	if (coiumn!=null && coiumn.length>0)
	{
		document.getElementById("selectedtable6").style.display="";
		document.getElementById("selectedOption6").checked=true;
	}
	var area = document.getElementsByName("preciseUiBean.userAreaName"); 
	if (area!=null && area.length>0)
	{
		document.getElementById("selectedtable11").style.display="";
		document.getElementById("selectedOption11").checked=true;
	}
	var ind = document.getElementsByName("preciseUiBean.userindustrys"); 
	if (ind!=null && ind.length>0)
	{
		document.getElementById("selectedtable12").style.display="";
		document.getElementById("selectedOption12").checked=true;
	}
	
	var level = document.getElementsByName("preciseUiBean.userlevels"); 
	if (level!=null && level.length>0)
	{
		document.getElementById("selectedtable13").style.display="";
		document.getElementById("selectedOption13").checked=true;
	}
	
	//投放区域
	var distributeArea = document.getElementsByName("areaChannelUiBean.userArea"); 
	if (distributeArea!=null && distributeArea.length>0)
	{
		document.getElementById("selectedtable14").style.display="";
		document.getElementById("selectedOption14").checked=true;
	}
	
	//背景广播
	var radio = document.getElementsByName("bchannelgroup"); 
	if (radio!=null && radio.length>0)
	{
		document.getElementById("selectedtable15").style.display="";
		document.getElementById("selectedOption15").checked=true;
	}
	
	//TVN号
	var radio = document.getElementsByName("preciseUiBean.tvnNumber"); 
	if (radio!=null && radio.length>0)
	{
		document.getElementById("selectedtable17").style.display="";
		document.getElementById("selectedOption17").checked=true;
	}  
	 
	changePosition();
	$("#system-dialog").hide();
	
	}
	//刷新已有策略列表
    function refreshPloyList()
	{
	    var alltime = document.getElementById("padposition.isAllTime");
	    if (alltime!=null && alltime.value==1)
		{
			document.getElementById("ploy.startTime").disabled=true;
    		document.getElementById("ploy.endTime").disabled=true;
		}
    	if( ploys!=null)// &&  ploys.length > 0
		{
		var ployList=         "<table cellspacing='1' class='searchList'>";
		ployList=ployList+"<tr class=title>";
	    ployList=ployList+  "<td colspan='4'>已有策略</td>";
	    ployList=ployList+"</tr>";
	    ployList=ployList+"<tr class='title'>";
	    ployList=ployList+"    <td>序号</td>";
	    ployList=ployList+"    <td>策略名称</td>";
	    ployList=ployList+"    <td>开始时间</td>";
	    ployList=ployList+"    <td>结束时间</td>";
	    ployList=ployList+"  </tr>";
	    
	  // alert(areaChannels.length);
	    for (var i=0;i<ploys.length;i++)
	    {	
	   // alert(areaChannels[i].areaCode);
		     ployList=ployList+"  <tr>";
		     ployList=ployList+"    <td>"+i+"</td>";
		     ployList=ployList+"    <td>"+ploys[i].ployName+"</td>";
		     ployList=ployList+"    <td>"+ploys[i].startTime+"</td>";
		     ployList=ployList+"    <td>"+ploys[i].endTime+"</td>";
		     ployList=ployList+"  </tr>";
	      }
	     ployList=ployList+"</table>";
	    // alert(areaList);
	   	 document.getElementById("ployList").innerHTML=ployList;
	   	 }
	}
	/*
	 * 刷新区域列表
	 * */
    function refreshAreaList()
	{
	   
		if( areaChannels!=null &&  areaChannels.length > 0)
		{
		var areaList=         "<table cellspacing='1' class='searchList'>";
		areaList=areaList+"<tr class=title>";
	    areaList=areaList+  "<td colspan='5'>已选区域</td>";
	    areaList=areaList+"</tr>";
	    areaList=areaList+"<tr class='title'>";
	    areaList=areaList+"    <td >序号</td>";
	    areaList=areaList+"    <td>区域名称</td>";
	    areaList=areaList+"    <td>区域编码</td>";
		areaList=areaList+"	<td>操作</td>";
	    areaList=areaList+"  </tr>";
	    
	  // alert(areaChannels.length);
	    for (var i=0;i<areaChannels.length;i++)
	    {	
	   // alert(areaChannels[i].areaCode);
		     areaList=areaList+"  <tr>";
		     areaList=areaList+"    <td>"+i+"</td>";
		     areaList=areaList+"    <td>"+areaChannels[i].areaName+"</td>";
		     areaList=areaList+"    <td>"+areaChannels[i].areaCode+"</td>";
		     areaList=areaList+"   <td>";
		     areaList=areaList+" <input id='areaids' name='areaids' type='hidden' value='"+areaChannels[i].areaId +"'/>";
		     areaList=areaList+" <input id='channel"+i+"' name='channel"+i+"' type='hidden' value='";
		     if(areaChannels[i].channels != null)
		     {
		     	for (var j=0;j<areaChannels[i].channels.length;j++)
		    	 {
		    	  areaList=areaList+areaChannels[i].channels[j].channel_id;
		    	//  alert(areaChannels[i].channels[j].channel_id);
		    	  if (j<areaChannels[i].channels.length-1)
		    	  {
		    		  areaList=areaList+",";
		    	 }
		    	  
		     	}
		     }
		     
		     areaList=areaList+"'/>";
			 areaList=areaList+"	<a href='#' onclick='deleteArea("+areaChannels[i].areaId+");'>删除区域</a>";
			 areaList=areaList+"	&nbsp;&nbsp;";
			 if (document.getElementById('padposition.isChannel').value==1)
			 {
				 areaList=areaList+"	<span id='selectc' ><a href='#' onclick='addselectChannel("+areaChannels[i].areaId+");'>绑定频道</a></span>";
				 areaList=areaList+"	&nbsp;&nbsp;";
				 areaList=areaList+"	<span id='selectm' ><a href='#' onclick='modifyChannel("+i+");'>编辑频道</a></span>";
			}
			 areaList=areaList+"	 </td>";
		     areaList=areaList+"  </tr>";
	    
	     }
	     areaList=areaList+"</table>";
	    // alert(areaList);
	   	 document.getElementById("areaList").innerHTML=areaList;
	   	 }
	}
	/*
	 * 删除区域
	 * */
	function deleteArea(areaCode)
	{
		
		for (var i=0;i<areaChannels.length;i++)
	    {	
		    if (areaCode==areaChannels[i].areaCode)
		    {
		    	areaChannels.splice(i,1);
		    	break;
		    }
	     }
		refreshAreaList();
	}
	 function submitForm(state){
        //validate();
      //  checkPloy();
       // return ;
        //window.returnValue = "";
       // alert(document.getElementById("saveForm"));
      //  alert(document.getElementById("saveForm").action);
      document.getElementById("ploy.state").value=state;
        document.getElementById("saveForm").submit();
        
    }


    function validate(){
        //输入框的边框变为红色
        //alert($("ploy.ployName").value);
        
        if(document.getElementById("ploy.ployName").value==null || ''==document.getElementById("ploy.ployName").value )
			{
				alert('请输入策略名称');
				document.getElementById("ploy.ployName").focus();
				return false ;
			}
         if(document.getElementById("ploy.positionId").value==null || ''==document.getElementById("ploy.positionId").value || '0'==document.getElementById("ploy.positionId").value)
			{
				alert('请选择广告位');
				return false ;
			}
        
        if (document.getElementById("selectedOption0").checked=true)
        {
        	 var starttimes = document.getElementsByName("starttime");
        	 var endtimes = document.getElementsByName("endtime");
       		 if (starttimes!=null)
       		 {
       			 for (var i=0;i<starttimes.length;i++)
       			 {
       			     if (starttimes[i].value==null || starttimes[i].value=="")
       			     {
       			         alert('请输入开始时间');
       			         starttimes[i].focus();
						return false ;
       			     }
       			      if (endtimes[i].value==null || endtimes[i].value=="")
       			     {
       			         alert('请输入结束时间');
       			         endtimes[i].focus();
						return false ;
       			     }
       			       if (starttimes[i].value>endtimes[i].value)
       			     {
       			         alert('开始时间大于结束时间');
       			         endtimes[i].focus();
						return false ;
       			     }
       			     for (var j=i+1;j<starttimes.length;j++)
       			     {
       			    	 if (i==j)
       			    	 {
       			    	     continue;	 
       			    	 }
       			    	 else
       			    	 {
       			    		 if (starttimes[j].value>=starttimes[i].value && starttimes[j].value<=endtimes[i].value)
       			    		 {
       			    			 alert('开始时间冲突');
			       			       starttimes[j].focus();
								   return false ;
       			    		 }
       			    		 if (endtimes[j].value>=starttimes[i].value && endtimes[j].value<=endtimes[i].value)
       			    		 {
       			    			   alert('结束时间冲突');
			       			       endtimes[j].focus();
								   return false ;
       			    		 }
       			    		 if (starttimes[j].value<starttimes[i].value && endtimes[j].value>starttimes[i].value)
       			    		 {
       			    			   alert('开始时间冲突');
			       			       starttimes[j].focus();
								   return false ;
       			    		 }
       			    	 }
       			     }
       			      
       			 }
       		 }
        }
        
        
        //return false ;
       
        return true;
       /* $("name").style.borderColor="red";
        $("contentType").style.borderColor="red";
        $("select0").style.borderColor="red";
        $("time1").style.borderColor="red";
        $("time2").style.borderColor="red";
        $("remark").style.borderColor="red";
        //输入框后加错误提示信息
        $("name_error").className="error";
        $("name_error").innerHTML = "内容包名不能为空";
        $("contentType_error").className="error";
        $("contentType_error").innerHTML = "请选择内容类型";
        $("select0_error").className="error";
        $("select0_error").innerHTML = "请选择是否为电视剧";
        $("time1_error").className="error";
        $("time1_error").innerHTML = "生效日期不能为空";
        $("time2_error").className="error";
        $("time2_error").innerHTML = "失效日期不能为空";
        $("remark_error").className="error";
        $("remark_error").innerHTML = "备注中含有特殊字符";
        */
        return;
    }
    function checkPloy() {
 			var ployName = document.getElementById("ploy.ployName").value;
 			var ployId = document.getElementById("ploy.ployId").value;
 			var contractId = "";
 			var positionId = "";
 			var startTime = "";
 			var endTime = "";
 			//从哪获取Json
 		
          	$.ajax({
                type:"post",
                async : false,
                url:"<%=request.getContextPath()%>/page/ploy/checkPloy.do?",
                data:{"ploy.ployName":ployName,"ploy.ployId":ployId,"ploy.contractId":contractId,"ploy.positionId":positionId,"ploy.startTime":startTime,"ploy.endTime":endTime},//Ajax传递的参数
                success:function(mess)
                {
                	 if(mess=="0")// 如果获取的数据不为空
                    {
						document.getElementById("checkflag").value="0";
                    	//return false;
                    }
                    if(mess=="1")// 如果获取的数据不为空
                    {
						alert("策略名称已存在");
						document.getElementById("checkflag").value="1";
                    	//return false;
                    }
                     if(mess=="2")// 如果获取的数据不为空
                    {
						alert("该时间段已有策略");
						document.getElementById("checkflag").value="2";
                    	//return false;
                    }
                   
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
			//return true;
	 } 
 	function selectContract() {
 	
 			var contractId = document.getElementById("ploy.contractId").value;
 			alert(contractId);
          	$.ajax({
                type:"post",
                url:"/page/ploy/getContract.do?",//从哪获取Json
                data:{"contractId":contractId},//Ajax传递的参数
                success:function(mess)
                {
                	
                    if(mess!="]")// 如果获取的数据不为空
                    {
						 var type=eval(mess);
                         
                         for(var i=0;i<type.length;i++)
                         {
                            //操作type 获取东西
                           
                         }
                          easyDialog.open({
							container : {
								header : "选择合同",
								content : $("#divImage")
							},			
							overlay : false
						});
                         $("#divLinkMan").html(str);//把str嵌入div divLinkMan 中
                     }
                     else
                     {
                        var notfind="<h2>未找到数据<a href='../Main.htm' target='_parent'>返回</a></h2>"
                        $("#divLinkMan").html(notfind);
                     }
                     
                }
            });
			
	 } 
 	function selectAdPosition() {
 		   	var structInfo = '';
			var contractname = document.getElementById("ploy.contractname").value;
			
			if(contractname==null || ''==contractname )
			{
				alert('请单击【合同】文本框进行选择000');
				return ;
			}
          	//$("#content").html('请单击【绑定广告商】文本框进行广告商绑定');
			//$( "#system-dialog" ).dialog({
		   //   	modal: true
		    //});
          	structInfo+='					<div style="margin:0px;padding:0px;width:600px">';
			structInfo+='							<iframe id="selectAdPositionFrame" name="selectAdPositionFrame" src="<%=request.getContextPath()%>'+'/page/ploy/selectAdPosition.jsp'+'" frameBorder="0" width="800px" height="400px"  scrolling="yes"></iframe>';
			structInfo+='					</div>';
			easyDialog.open({
			container : {
				header : '选择广告位',
				content : structInfo
			},
			overlay : false
		});
		
			
	 } $(document).ready(function(){
	 
 		$("#selectAreas").click(function(){
	  	selectArea();
 		});
});
	  
	 function selectArea() {
	 
	 	 var contractId= document.getElementById("ploy.contractId").value;
		 var postionId= document.getElementById("ploy.positionId").value;
		 var ruleId= document.getElementById("ploy.ruleId").value;
		 var startTime= document.getElementById("ploy.startTime").value;
		 var endTime= document.getElementById("ploy.endTime").value;
		 var structInfo ="";
			structInfo+="			<div style='margin:0px;padding:0px;width:600px'>";
			structInfo+="						<iframe id='selectAreaFrame' name='selectAreaFrame'  frameBorder='0' width='600px' height='400px'  scrolling='yes'  align='top' src=<%=request.getContextPath()%>/page/ploy/getArea.do? ";
			structInfo+="contractId="+contractId+"&postionId="+postionId+"&ruleId="+ruleId+"&startTime="+startTime+"&endTime="+endTime;
			structInfo+="'></iframe>";
			structInfo+="</div>";
			easyDialog.open({
			container : {
				header : "添加区域",
				content : structInfo
			},
			overlay : true
			
		});
	 }
	 function alreadyrule() {
            var url = "positionmarketrulelist.htm";
			// window.location=url;
            window.showModalDialog(url, "", "dialogHeight=550px;dialogWidth=650px;center=1;resizable=0;status=0;");
        }
		function selectChannel()
		{
		   var url = "selectChannelList.html";
			 window.location=url;
            //window.showModalDialog(url, "", "dialogHeight=550px;dialogWidth=650px;center=1;resizable=0;status=0;");
		}
		function onchangeAD()
		{
		if (document.getElementById("adType").value == "1")
		{
		var array = document.getElementsByName("selectc");
		if(array.length){
	    for(var i = 0 ;i<array.length;i++){
		 		array[i].style.display="none";
			}
		}
		document.getElementById("starttime").value="00:00:00";
		document.getElementById("endtime").value="23:59:59";
		}
		else
		{
		var array = document.getElementsByName("selectc");
		if(array.length){
	    for(var i = 0 ;i<array.length;i++){
		 		array[i].style.display="";
			}
		}
		
		}
		}
		
		
function addselectContract() {
          	
	//var structInfo = '';
	//		structInfo+='					<div style="margin:0px;padding:0px;">';
	//		structInfo+='							<iframe id="selectContraceFrame" name="selectContraceFrame" src="<%=request.getContextPath()%>'+'/page/ploy/addqueryContractList.do'+'" frameBorder="0" width="800px" height="570px"  scrolling="yes"></iframe>';
	//		structInfo+='					</div>';
	//		easyDialog.open({
	//		container : {
	//			header : '选择合同',
	//			content : structInfo
	//		},			
	//		overlay : false
	//	});
	
		var	height = 500;
		var width=750;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/ploy/addqueryContractList.do';
		 var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 } 
 	function addselectAdPosition() {
          	var structInfo = '';
			var contractid= document.getElementById("ploy.contractId").value;
			var contractname = document.getElementById("ploy.contractName").value;
			
			if(contractname==null || ''==contractname )
			{
				message = '请单击【合同】文本框进行选择';
				$("#content").html(message);
				$( "#system-dialog" ).dialog({
			      	modal: true
			    });
			//alert('请单击【合同】文本框进行选择');
				return ;
			}
			
          	//$("#content").html('请单击【绑定广告商】文本框进行广告商绑定');
			//$( "#system-dialog" ).dialog({
		   //   	modal: true
		    //});
         // 	structInfo+='					<div style="margin:0px;padding:0px;width:600px">';
		//	structInfo+='							<iframe id="selectAdPositionFrame" name="selectAdPositionFrame" src="<%=request.getContextPath()%>'+'/page/ploy/addqueryAdPostionList.do?contract.id='+contractid+'" frameBorder="0" width="800px" height="570px"  scrolling="yes"></iframe>';
		//	structInfo+='					</div>';
		//	easyDialog.open({
		//	container : {
		//		header : '选择广告位',
		//		content : structInfo
		//	},
		//	overlay : false
		//    });
			
			var	height = 500;
		var width=750;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/ploy/addqueryAdPostionList.do?contract.id='+contractid;
		var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		
		var ployId = document.getElementById("ploy.ployId").value;
 		var positionId = document.getElementById("ploy.positionId").value;
		$.ajax({
                type:"post",
                async : false,
                url:"<%=request.getContextPath()%>/page/ploy/queryExistPloyList.do?",
                data:{"ploy.ployId":ployId,"ploy.contractId":contractid,"ploy.positionId":positionId},//Ajax传递的参数
                success:function(mess)
                {
                	if(mess!=null)// 如果获取的数据不为空
                    {
						ploys =eval(mess);
	    				refreshPloyList();
					}
                	
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
		
		
	 } 
 	
 	function showNpvrChannelGroupRef(channelGroupId) {
 	    var	height = 550;
 		var width=750;
 		var actinUrl = "<%=path %>/page/npvrChannelGroup/showChannelGroupRefListNpvr.do?channelGroupId="+channelGroupId;
 		var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
 	}
 	
 	function addselectRule() {
          	var structInfo = '';
		//	var adname = document.getElementById("ploy.adname").value;
			var contractid= document.getElementById("ploy.contractId").value;
			
			var adId = document.getElementById("ploy.positionId").value;
			if(adId==null || ''==adId )
			{
				alert('请单击【广告位】文本框进行选择');
				return ;
			}
			
          	structInfo+='					<div style="margin:0px;padding:0px;">';
			structInfo+='							<iframe id="selectRuleFrame" name="selectRuleFrame" src="<%=request.getContextPath()%>'+'/page/ploy/addqueryRuleList.do?contract.id='+contractid+'&adPosition.id='+adId+'" frameBorder="0" width="800px" height="570px"  scrolling="yes" align="top"></iframe>';
			structInfo+='					</div>';
			easyDialog.open({
			container : {
				header : '选择营销规则',
				content : structInfo
			},
			overlay : true
		});
	 }
		function addselectChannel(areaid) {
          	var structInfo = '';
          	var contractId= document.getElementById("ploy.contractId").value;
          	var ruleId= document.getElementById("ploy.ruleId").value;
          	var starttime=document.getElementById("ploy.startTime").value;
          	var endtime =document.getElementById("ploy.endTime").value;
			structInfo+='					<div style="margin:0px;padding:0px;">';
			structInfo+='							<iframe id="selectContraceFrame" name="selectContraceFrame" src="<%=request.getContextPath()%>'+'/page/ploy/getChannelListByArea.do?ploy.areaId='+areaid+'&ploy.contractId='+contractId+'&ploy.ruleId='+ruleId+'&ploy.startTime='+starttime+'&ploy.endTime='+endtime+'" frameBorder="0" width="800px" height="570px"  scrolling="yes"></iframe>';
			structInfo+='					</div>';
			easyDialog.open({
			container : {
				header : '绑定频道',
				content : structInfo
			},			
			overlay : false
		});
	 } 
		function modifyChannel(areaid) {
          	var structInfo = '';
          	structInfo+='					<div style="margin:0px;padding:0px;">';
			structInfo+='							<iframe id="selectContraceFrame" name="selectContraceFrame" src="<%=request.getContextPath()%>'+'/page/ploy/modifychannel.jsp?areaid='+areaid+'" frameBorder="0" width="800px" height="570px"  scrolling="yes"></iframe>';
			structInfo+='					</div>';
			easyDialog.open({
			container : {
				header : '绑定频道',
				content : structInfo
			},			
			overlay : false
		});
	 } 
		
	 function selectOptionVal(){
	 }
	 //更改广告位，显示精准控制复选框
	 function changePosition(){
		 
		 var positionid = document.getElementById("ploy.positionId").value;
		 
		 if (firstflag==false)
		 {
		    for (var k=0;k<18;k++)
		    {
		    	var ck = document.getElementsByName("selectedcheckbox"+k); 
				var tab = document.getElementById("contenttable"+k);
				var tabselect = document.getElementById("selectedtable"+k);
				var selectedOption = document.getElementById("selectedOption"+k);
				if (tabselect!=null)
				{
					tabselect.style.display="none";
				}
				if (selectedOption!=null)
				{
					selectedOption.checked=false;
				}
				
				var rowIndex; 
				for(var i=ck.length-1;i>-1;i--){ 
				    if (ck[i].parentNode.parentNode.id==null || ck[i].parentNode.parentNode.id=="")
				    {
				    	continue;
				    }
					if (ck[i].parentNode.parentNode.id=="contentrow")
					{
						rowIndex=  ck[i].parentNode.parentNode.rowIndex;
						tab.deleteRow(rowIndex);				
					}
					else
					{
						var tr  = document.getElementById(ck[i].parentNode.parentNode.id); 
					    tab.removeChild(tr);
					}
			   } 
		    }
		 }
		
		 if( postions!=null &&  postions.length > 0)
		 {
			 for (var i=0;i<postions.length;i++)
			 {
				 if (positionid==postions[i].id)
				{
				     document.getElementById("positionCode").value= postions[i].positionCode;
					 
				     document.getElementById("selectedspan0").style.display="none";
					 document.getElementById("selectedspan1").style.display="none";
					 document.getElementById("selectedspan2").style.display="none";
					 document.getElementById("selectedspan4").style.display="none";
					 document.getElementById("selectedspan5").style.display="none";
					 document.getElementById("selectedspan16").style.display="none";
					 document.getElementById("selectedspan6").style.display="none";
					 document.getElementById("selectedspan7").style.display="none";
					 document.getElementById("selectedspan8").style.display="none";
					 document.getElementById("selectedspan9").style.display="none";
					 document.getElementById("selectedspan10").style.display="none";
					 document.getElementById("selectedspan11").style.display="none";
					 document.getElementById("selectedspan12").style.display="none";
					 document.getElementById("selectedspan13").style.display="none";
					 document.getElementById("selectedspan14").style.display="none";
					 document.getElementById("selectedspan15").style.display="none";
					 document.getElementById("selectedspan17").style.display="none";
					 document.getElementById("selectedspan18").style.display="none";

					 
					 document.getElementById("assetployNumber").style.display="none";
					 document.getElementById("ploy.ployNumber").style.display="none";					 
					
					  document.getElementById("defaultflag").style.display="none";
					 document.getElementById("ploy.defaultstart").style.display="none";
					 
					  if (firstflag==false)
					 {
						 document.getElementById("ploy.ployNumber").value=0;
					 }
					 if (postions[i].isAllTime==0)
					 {
						 document.getElementById("selectedspan0").style.display="";
					 }
					 
					 //开机广告，显示 "投放区域"
					  if (postions[i].positionCode=='01001' || postions[i].positionCode=='01002' || postions[i].positionCode=='01003'|| postions[i].positionCode=='01004')
					{
						document.getElementById("selectedspan14").style.display="";
						
					}
					//推荐广告位，显示"投放区域"
					  if(postions[i].positionCode=='02083'){
						//document.getElementById("defaultflag").style.display="";
						//document.getElementById("ploy.defaultstart").style.display="";
						document.getElementById("selectedspan14").style.display="";
					  }
					//直播频道组
	                 if (postions[i].isChannel==1 )
					 {
						 document.getElementById("selectedspan10").style.display="";
					 }
	                 //如果广告位是如下编号，不显示“频道组”复选框  ， 有部分 isChannel=1， 但不能显示
	                  if (postions[i].positionCode=='02011' || postions[i].positionCode=='02012' || postions[i].positionCode=='02101' || postions[i].positionCode=='02102')
					{
						document.getElementById("selectedspan10").style.display="none";
					}
					
					
	                //广播频道组
	                if (postions[i].isFreq==1)
					 {
						 document.getElementById("selectedspan15").style.display="";
						 document.getElementById("selectedspan14").style.display="";
					 }
					
					 if (postions[i].isLookbackProduct==1)
					 {
						 document.getElementById("selectedspan1").style.display="";
						  document.getElementById("ploy.ployNumber").disabled=false ;
						 //需记录产品类型为回看产品  isLookbackProduct
					 }
					//回看菜单广告
					 if (postions[i].isColumn==1)
					{
						 document.getElementById("selectedspan6").style.display="";
						 document.getElementById("lookbackdiv").style.display="none";  //不显示优先级
					 }
					 //回放菜单广告位
					 if (postions[i].positionCode=='02074')  
					 {
						 document.getElementById("selectedspan5").style.display="";
						 document.getElementById("ploy.ployNumber").disabled=false ;
					 }
		             
					 //回放暂停广告、回放插播广告位
					 if (postions[i].positionCode=='02084' || postions[i].positionCode=='02094')  
					 {
						 document.getElementById("selectedspan16").style.display="";
						 document.getElementById("ploy.ployNumber").disabled=false;
					 }
					//点播随片图片广告
					 if (postions[i].isFollowAsset==1)
					 {
						 document.getElementById("selectedspan8").style.display="";
						 document.getElementById("assetBar").innerHTML = "影片（电视剧）名称";
						 document.getElementById("selectedspan4").style.display="";		
						 //document.getElementById("assetdiv").style.display="none"; //影片 不显示优先级
					 } 
					 //else{
					 //	  document.getElementById("assetdiv").style.display="";
					 //}
					 //NVOD主界面广告
					 if(postions[i].positionCode=='02402'){
							document.getElementById("selectedspan14").style.display=""; 
							document.getElementById("selectedspan18").style.display=""; 
						}
					//NVOD挂角广告
						if(postions[i].positionCode=='20432'){
							document.getElementById("selectedspan14").style.display="";
						}
						//NVOD字幕广告
						if(postions[i].positionCode=='02422'){
							document.getElementById("selectedspan10").style.display="";
							document.getElementById("selectedspan14").style.display="";
							document.getElementById("selectedspan11").style.display="";
							document.getElementById("selectedspan13").style.display="";
							document.getElementById("selectedspan17").style.display="";
						}
					//影片精准，  针对双向实时请求类广告
					 if (postions[i].isAsset==1)
					  {
						 document.getElementById("selectedspan1").style.display="";
						 document.getElementById("selectedspan2").style.display="";
						 document.getElementById("selectedspan4").style.display="";
						 document.getElementById("selectedspan8").style.display="";
						 document.getElementById("selectedspan11").style.display="";
						 document.getElementById("selectedspan12").style.display="";
						 document.getElementById("selectedspan13").style.display="";
						
					     document.getElementById("assetployNumber").style.display="";
					     document.getElementById("ploy.ployNumber").style.display="";
						 
					 }
					 if(postions[i].positionCode=='02302'){
					     
						 document.getElementById("selectedspan12").style.display="";
						 document.getElementById("selectedspan13").style.display="";
						 document.getElementById("selectedspan14").style.display="";
						 document.getElementById("selectedspan17").style.display="";
					 }
					 /*
					 //音频广播
					  if (postions[i].positionCode=='02061' || postions[i].positionCode=='02062')
					{
						document.getElementById("selectedtable14").style.display="";
						document.getElementById("selectedtable15").style.display="";
						document.getElementById("selectedspan10").style.display="none";
					}
					 
					   if (postions[i].positionCode=='02091' || postions[i].positionCode=='02092'  || postions[i].positionCode=='02093'  || postions[i].positionCode=='02094' || postions[i].positionCode=='02071' || postions[i].positionCode=='02072'  || postions[i].positionCode=='02073'  || postions[i].positionCode=='02074')
					{
						document.getElementById("selectedtable14").style.display="";
						document.getElementById("lookbackdiv").style.display="none";
						document.getElementById("npvrdiv").style.display="none";
					}
					   else
					 {
						 document.getElementById("lookbackdiv").style.display="";
						document.getElementById("npvrdiv").style.display="";
					 }*/
				}
			 }
		 }
		  firstflag=false;
		
		 
		 //value="0"/>时间</span>
       //value="1"/>产品</span>
        //value="2"/>影片关键字</span>
        //value="3"/>受众</span>
        //value="4"/>影片分类栏目</span>
        //value="5"/>回放频道</span>
        //value="6"/>回看栏目</span>
        //value="7"/>频道</span>
       // value="8"/>影片名称</span>
        //value="9"/>DTV投放区域</span>
        //value="10"/>频道组</span>
        //value="11"/>用户区域</span>
       //value="12"/>行业</span>
       //value="13"/>级别</span>
       //根据positionJson控制显示复选框
	 }
</script>
<script language="javascript">
//根据精准复选框，显示精准输入界面
 function selecttable(object,selecttable)
	 {
		 if (object.checked==true)
		 {
			document.getElementById("selectedtable"+selecttable).style.display="" ;
			
			//selectedtable0
		 }
		 else
		 {
			document.getElementById("selectedtable"+selecttable).style.display="none" ;
			var ck = document.getElementsByName("selectedcheckbox"+selecttable); 
			var tab = document.getElementById("contenttable"+selecttable);
			var rowIndex; 
			for(var i=ck.length-1;i>-1;i--){ 
				    if (ck[i].parentNode.parentNode.id==null || ck[i].parentNode.parentNode.id=="")
				    {
				    	continue;
				    }
					if (ck[i].parentNode.parentNode.id=="contentrow")
					{
						rowIndex=  ck[i].parentNode.parentNode.rowIndex;
						tab.deleteRow(rowIndex);				
					}
					else
					{
						var tr  = document.getElementById(ck[i].parentNode.parentNode.id); 
					    tab.removeChild(tr);
					}
			} 
			//此处需清除已编缉缓存内容 清空对应 json
		 }
	 }
 //删除行元素  修改行数据ID=contentrow  新增行数据 ID= contentrow+num 
function del_tbl(tblN,ckN){ 
	var ck = document.getElementsByName(ckN); 
	var tab = document.getElementById(tblN);//$(tblN);
	var rowIndex; 
	for(var i=ck.length-1;i>-1;i--){ 
		if(ck[i].checked){ 
			if (ck[i].parentNode.parentNode.id=="contentrow")
			{
				rowIndex=  ck[i].parentNode.parentNode.rowIndex;
				tab.deleteRow(rowIndex);				
			}
			else
			{
				var tr  = document.getElementById(ck[i].parentNode.parentNode.id); 
			    tab.removeChild(tr);
			}
		} 
	} 
}
//行数据ID，新增使用
var rowcount=0;
//添加投放时间行  
function add_time(tblN){
	var tab = document.getElementById(tblN);//$(tblN); 
	var row = document.createElement("tr"); 
	var td1 = document.createElement("td");
	td1.setAttribute("class","dot");
	td1.innerHTML = '<input type="checkbox" name="selectedcheckbox0" />';
	var td2 = document.createElement("td")
	td2.innerHTML = '<input name="starttime" type="text" readonly onclick="WdatePicker({dateFmt:\'HH:mm:ss\'})" value="${starttime}"/>';
	var td3 = document.createElement("td")
	td3.innerHTML = '<input name="endtime" type="text" readonly onclick="WdatePicker({dateFmt:\'HH:mm:ss\'})" value="${starttime}"/>';
    row.setAttribute("id", "contentrow"+(rowcount++));
    row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    tab.appendChild(row);       
}
//添加投放频道组行  增加参数  ID，NAME
//
function add_channelgroup(tblN,groupid,groupname){
	var tab = document.getElementById(tblN);//$(tblN); 
	//遍历数据，判断是否已添加 可由弹出式页面处理
	/*var channelgroup = document.getElementsByName("channelgroup"); 
	if (channelgroup!=null)
	{
		for (var i=0;i<channelgroup.length;i++)
		{
			if (channelgroup[i].value==groupid)
			{
				  alert(groupname +"已存在");
			}
		}
	}
	*/
	var row = document.createElement("tr"); 
	var td1 = document.createElement("td");
	td1.setAttribute("class","dot");
	td1.innerHTML = '<input type="checkbox" name="selectedcheckbox10" />';	
	var td2 = document.createElement("td")
	//td2.innerHTML = '<input name="channelgroup" type="text" />';
	
	td2.innerHTML = '<input type="hidden" id="channelgroup" name="channelgroup"  value="'+groupid+'"/><a href="#" onclick="javascript:showChannelGroupRef('+groupid+');">'+groupname;
	var td3 = document.createElement("td")
	td3.setAttribute("align","left");
	td3.setAttribute("width='45%'");
	td3.innerHTML = '<select id="priority" name="priority" style="width:40px">'+
	                '<option value=1>1</option>'+
	                '<option value=2>2</option>'+
	                '<option value=3>3</option>'+
	                '<option value=4>4</option>'+
	                '<option value=5>5</option>'+
	                '<option value=6>6</option>'+
	                '<option value=7>7</option>'+
	                '<option value=8>8</option>'+
	                '<option value=9>9</option>'+
	                '</select>';
	var td4 = document.createElement("td")
	td4.setAttribute("width='5%'");
	
	row.setAttribute("id", "contentrow"+(rowcount++));
    row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    row.appendChild(td4);
    tab.appendChild(row);  
}

function selectChannelGroup()
	 {
		var	height = 550;
		var width=750;
		var ployId = document.getElementById("ploy.ployId").value;
		var actinUrl = '<%=request.getContextPath()%>'+'/page/precise/queryChannelGroup.do?ployId='+ployId;
		var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
	 }
function showChannelGroupRef(channelGroupId) {
   // window.location.href="<%=path %>/page/channelGroup/showChannelGroupRefList.do?channelGroupId="+channelGroupId;
    var	height = 550;
		var width=750;
		var actinUrl = "<%=path %>/page/channelGroup/showChannelGroupRefList.do?channelGroupId="+channelGroupId;
		var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){
			
		} 
}
</script>
<body class="mainBody">
<form action="<%=path %>/page/ploy/checkPloyState.do" method="post" id="saveForm">
<div class="path">首页 >> 投放策略管理 >> 策略审核</div>
<div class="searchContent">
  <table cellspacing="1" class="searchList">
    <tr class="title">
      <td>策略编辑</td>
    </tr>
  <tr>
    <td class="searchCriteria">
      <span>*投放策略名称：</span>
      			<input id="checkflag" name="checkflag" type="hidden" value="0"/> 
                <input id="ploy.ployId" name="ploy.ployId" type="hidden" value="${ploy.ployId}"/>
                <input id="ploy.state" name="ploy.state" type="hidden" value="${ploy.state}"/>
               <input id='areaids' name='areaids' type='hidden' value='0'/>
                <input id="ploy.operationId" name="ploy.operationId" type="hidden" value="${ploy.operationId}"/>
                <input id="ploy.createTime" name="ploy.createTime" type="hidden" value="${ploy.createTime}"/>
                <input id="ploy.modifyTime" name="ploy.modifyTime" type="hidden" value="${ploy.modifyTime}"/>
                <input id="ploy.description" name="ploy.description" type="hidden" value="${ploy.description}"/>
                <input   disabled onkeypress="return validateSpecialCharacter();" maxlength="30" id="ploy.ployName" name="ploy.ployName" type="text" value="${ploy.ployName}"/>
      <span>&nbsp;&nbsp;&nbsp;&nbsp;广告位名称：</span> <select  disabled id="ploy.positionId" name="ploy.positionId" onchange="changePosition();">
               <c:forEach items="${pageAdPosition.dataList}" var="positionVar" >
                        <option value=${positionVar.id} <c:if test="${positionVar.id==ploy.positionId}">
                            	 selected
                            	</c:if>>
                           
                            	
                            ${positionVar.positionName}
                </option>
               </c:forEach>
                </select>
                 <input  disabled maxlength="30" id="positionCode" name="positionCode" type="text" style="width: 40px" value="${ploy.ployName}"/>
				<span id="assetployNumber" >&nbsp;&nbsp;&nbsp;&nbsp;投放次数：</span>
			      	<input disabled onkeypress="return validateSpecialCharacter();"  maxlength="5" id="ploy.ployNumber" name="ploy.ployNumber" type="text" style="width: 50px" value="${ploy.ployNumber}"/>
			      
			      <span id="defaultflag" >&nbsp;&nbsp;&nbsp;&nbsp;是否默认：</span>	
			         			 <select disabled id="ploy.defaultstart" name="ploy.defaultstart" style="width:40px">
              						<option value=0 <c:if test="${0==ploy.defaultstart}">
                            	 selected
                            	</c:if>>否</option>
              						<option value=1 <c:if test="${1==ploy.defaultstart}">
                            	 selected
                            	</c:if>>是</option>                            	
                				</select>
                	                
     

       </td>
      </tr>
  <tr>
    <td class="searchSec">
        <span id="selectedspan0"><input type="checkbox" disabled name="selectedOption0"  id="selectedOption0" value="0" onclick="selecttable(this,'0');"/>时间</span>
        <span id="selectedspan1"><input type="checkbox"  disabled onclick="showContent('tfqy')" name="selectedOption1"  id="selectedOption1" value="1"/>产品</span>
        <span id="selectedspan2"><input type="checkbox" disabled onclick="showContent('tfyp')" name="selectedOption2"  id="selectedOption2" value="2"/>影片关键字</span>
        <span id="selectedspan3" style="display:none" ><input type="checkbox" disabled  onclick="showContent('ypgjz')" name="selectedOption3"  id="selectedOption3" value="3"/>受众</span>
        <span id="selectedspan4"><input type="checkbox" disabled onclick="showContent('yhjb')" name="selectedOption4"  id="selectedOption4" value="4"/>影片分类栏目</span>
        <span id="selectedspan5"><input type="checkbox" disabled onclick="showContent('hylb')" name="selectedOption5"  id="selectedOption5" value="5"/>回放频道组</span>
        
        <span id="selectedspan16"><input type="checkbox" name="selectedOption16"  id="selectedOption16" value="5" />回放频道</span>
        
        <span id="selectedspan6"><input type="checkbox" disabled name="selectedOption6"  id="selectedOption6" value="6"/>回看栏目</span>
        <span id="selectedspan7"><input type="checkbox" disabled name="selectedOption7"  id="selectedOption7" value="7"/>频道</span>
        <span id="selectedspan8"><input type="checkbox" disabled name="selectedOption8"  id="selectedOption8" value="8"/> <span id="assetBar">影片名称</span></span>
        <span id="selectedspan9"><input type="checkbox" disabled name="selectedOption9"  id="selectedOption9" value="9"/>DTV投放区域</span>
        <span id="selectedspan10"><input type="checkbox" disabled name="selectedOption10"  id="selectedOption10" value="10" />频道组</span>
        <span id="selectedspan11"><input type="checkbox" disabled name="selectedOption11"  id="selectedOption11" value="11"/>用户区域</span>
         <span id="selectedspan12"><input type="checkbox" disabled name="selectedOption12"  id="selectedOption12" value="12"/>行业</span>
        <span id="selectedspan13"><input type="checkbox" disabled name="selectedOption13"  id="selectedOption13" value="13"/>级别</span>
      	
      	<span id="selectedspan14"><input type="checkbox" disabled name="selectedOption14"  id="selectedOption14" value="14" />投放区域</span>
        <span id="selectedspan15"><input type="checkbox" disabled name="selectedOption15"  id="selectedOption15" value="15" />广播频道组</span>
        <span id="selectedspan17"><input type="checkbox" disabled name="selectedOption17"  id="selectedOption17" value="17" />TVN号</span>
       </td>
        </tr>
  </table>
  <div class="boxList">
   
   <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable0" name="selectedtable0">
      <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="checkbox3" value="checkbox" onclick="selectAll(this, 'selectedcheckbox0');"/></td>
	        <td><b>投放时间</b>
          </p>
             
          </td>
		
      </tr>
      <tr>
        <td colspan="2" class="conditionList">
          <div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable0" >
               <tr >
                <td class="dot"></td>
                <td >开始时间</td>
                <td >结束时间</td>
              </tr>
           
            <c:forEach items="${ployTimeCGroup.timeList}" var="timeVar" >
              <tr id="contentrow">
                <td class="dot"><input type="checkbox" disabled name="selectedcheckbox0" value="checkbox" /></td>
                <td ><input name="starttime" type="text" readonly  value="${timeVar.startTime}"/></td>
                <td ><input name="endtime" type="text" readonly  value="${timeVar.endTime}"/></td>
              </tr>
                </option>
               </c:forEach>
           
            
              
            </table>
         </div>
        </td>
      </tr>
     
    </table>
     <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable10">
      <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="checkbox3" value="checkbox"  onclick="selectAll(this, 'selectedcheckbox10');"/></td>
	       <td><b>投放频道</b>
          </p>
             
          </td>
		</td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList">
        <div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable10">
              <tr >
                <td class="dot"></td>
                <td >频道组</td>
                <td >优先级</td>
              </tr>
                <c:if test="${fn:length(ployTimeCGroup.channelgroupList) > 0 && channelGroupType == '1'}">
	            	<c:forEach items="${ployTimeCGroup.channelgroupList}" var="groupVar">
			              <tr id="contentrow">
			                <td class="dot"><input type="checkbox" disabled name="selectedcheckbox10" value="checkbox"/></td>
			                <td width="45%"><input type="hidden" id="channelgroup" name="channelgroup"  value="${groupVar.groupId}"/><a href="#" onclick="javascript:showChannelGroupRef(${groupVar.groupId});">${groupVar.groupName}</a></td>
			                <td align="left" width="45%">
			                	<select disabled id="priority" name="priority" style="width:40px">
	             					<option value="1" <c:if test="${1==groupVar.priority}">selected</c:if> > 1 </option>
	             					<option value="2" <c:if test="${2==groupVar.priority}">selected</c:if> > 2 </option>
	             					<option value="3" <c:if test="${3==groupVar.priority}">selected</c:if> > 3 </option>
	             					<option value="4" <c:if test="${4==groupVar.priority}">selected</c:if> > 4 </option>
	             					<option value="5" <c:if test="${5==groupVar.priority}">selected</c:if> > 5 </option>
	             					<option value="6" <c:if test="${6==groupVar.priority}">selected</c:if> > 6 </option>
	             					<option value="7" <c:if test="${7==groupVar.priority}">selected</c:if> > 7 </option>
	             					<option value="8" <c:if test="${8==groupVar.priority}">selected</c:if> > 8 </option>
	             					<option value="9" <c:if test="${9==groupVar.priority}">selected</c:if> > 9 </option>
			                	</select>
			                </td>
			                <td width="5%"><input type="hidden" name="channelGroupType" value="1"/></td>
			              </tr>
		               </c:forEach>
		          </c:if>                   
            </table>
        </div>
        </td>
      </tr>
     
    </table>
   
     <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable1">
      <tr class="title">
        <td height="28" class="dot"><input  disabled type="checkbox" name="checkbox3" value="checkbox" onclick="selectAll(this, 'selectedcheckbox1');"/></td>
        <td><b>产品</b>
            <p><span>优先级别：</span>
                <select  disabled id="preciseUiBean.priority1" name="preciseUiBean.priority1" style="width:40px">
              						<option value=1 <c:if test="${1==preciseUiBean.priority1}">
                            	 selected
                            	</c:if>>1</option>
              						<option value=2 <c:if test="${2==preciseUiBean.priority1}">
                            	 selected
                            	</c:if>>2</option>
              						<option value=3 <c:if test="${3==preciseUiBean.priority1}">
                            	 selected
                            	</c:if>>3</option>
              						<option value=4 <c:if test="${4==preciseUiBean.priority1}">
                            	 selected
                            	</c:if>>4</option>
              						<option value=5 <c:if test="${5==preciseUiBean.priority1}">
                            	 selected
                            	</c:if>>5</option>
              						<option value=6 <c:if test="${6==preciseUiBean.priority1}">
                            	 selected
                            	</c:if>>6</option>
              						<option value=7 <c:if test="${7==preciseUiBean.priority1}">
                            	 selected
                            	</c:if>>7</option>
              						<option value=8 <c:if test="${8==preciseUiBean.priority1}">
                            	 selected
                            	</c:if>>8</option>
              						<option value=9 <c:if test="${9==preciseUiBean.priority1}">
                            	 selected
                            	</c:if>>9</option>
                				</select>
                
                <span>预制条件</span>
                <select  disabled name="preciseUiBean.tvnExpression1">
                  <option value=0 <c:if test="${0==preciseUiBean.tvnExpression1}">
                            	 selected
                            	</c:if>>不等于</option>
                            	 <option value=1 <c:if test="${1==preciseUiBean.tvnExpression1}">
                            	 selected
                            	</c:if>>等于</option>
                 
                </select>
                <input type="hidden" id="preciseUiBean.tvnNumber1" name="preciseUiBean.tvnNumber1"  value="${preciseUiBean.tvnNumber1}"/>
          </p></td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList"><div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable1">
              <c:forEach items="${preciseUiBean.productIdList}" var="productVar" >
              <tr id="contentrow">
                 <td class="dot"><input type="checkbox" name="selectedcheckbox1" value="checkbox"/></td>
                 <td >
                 <input type="hidden" id="preciseUiBean.productId" name="preciseUiBean.productId"  value="${productVar.datavalue}"/>
                 <input type="hidden" id="preciseUiBean.productName" name="preciseUiBean.productName"  value="${productVar.dataname}"/>
                 ${productVar.dataname}
                 </td>
              </tr>
             </c:forEach>
            </table>
        </div></td>
      </tr>
     
    </table>
    
    <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable2">
      <tr class="title">
        <td height="28" class="dot"><input  disabled type="checkbox" name="checkbox3" value="checkbox" onclick="selectAll(this, 'selectedcheckbox2');"/></td>
        <td><b>影片关键字</b>
            <p><span>优先级别：</span>
                <select  disabled id="preciseUiBean.priority2" name="preciseUiBean.priority2" style="width:40px">
              						<option value=1 <c:if test="${1==preciseUiBean.priority2}">
                            	 selected
                            	</c:if>>1</option>
              						<option value=2 <c:if test="${2==preciseUiBean.priority2}">
                            	 selected
                            	</c:if>>2</option>
              						<option value=3 <c:if test="${3==preciseUiBean.priority2}">
                            	 selected
                            	</c:if>>3</option>
              						<option value=4 <c:if test="${4==preciseUiBean.priority2}">
                            	 selected
                            	</c:if>>4</option>
              						<option value=5 <c:if test="${5==preciseUiBean.priority2}">
                            	 selected
                            	</c:if>>5</option>
              						<option value=6 <c:if test="${6==preciseUiBean.priority2}">
                            	 selected
                            	</c:if>>6</option>
              						<option value=7 <c:if test="${7==preciseUiBean.priority2}">
                            	 selected
                            	</c:if>>7</option>
              						<option value=8 <c:if test="${8==preciseUiBean.priority2}">
                            	 selected
                            	</c:if>>8</option>
              						<option value=9 <c:if test="${9==preciseUiBean.priority2}">
                            	 selected
                            	</c:if>>9</option>
                				</select>
                
                <span>预制条件</span>
                <select  disabled name="preciseUiBean.tvnExpression2">
                
                  <option value=0 <c:if test="${0==preciseUiBean.tvnExpression2}">
                            	 selected
                            	</c:if>>不等于</option>
                            	  <option value=1 <c:if test="${1==preciseUiBean.tvnExpression2}">
                            	 selected
                            	</c:if>>等于</option>
                </select>
                <input type="hidden" id="preciseUiBean.tvnNumber2" name="preciseUiBean.tvnNumber2"  value="${preciseUiBean.tvnNumber2}"/>
          </p></td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList"><div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable2">
              <c:forEach items="${preciseUiBean.assetKeyList}" var="keyVar" >
              <tr id="contentrow">
                 <td class="dot"><input type="checkbox" name="selectedcheckbox2" value="checkbox"/></td>
                 <td >
                 <input  disabled type="text" id="preciseUiBean.assetKey" name="preciseUiBean.assetKey"  value="${keyVar.datavalue}"/>
                </td>
              </tr>
             </c:forEach>
            </table>
        </div></td>
      </tr>
    
    </table>
    <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable4">
      <tr class="title">
        <td height="28" class="dot"><input  disabled type="checkbox" name="checkbox3" value="checkbox" onclick="selectAll(this, 'selectedcheckbox4');"/></td>
        <td><b>影片栏目</b>
            <p><span>优先级别：</span>
                <select  disabled id="preciseUiBean.priority4" name="preciseUiBean.priority4" style="width:40px">
              						<option value=1 <c:if test="${1==preciseUiBean.priority4}">
                            	 selected
                            	</c:if>>1</option>
              						<option value=2 <c:if test="${2==preciseUiBean.priority4}">
                            	 selected
                            	</c:if>>2</option>
              						<option value=3 <c:if test="${3==preciseUiBean.priority4}">
                            	 selected
                            	</c:if>>3</option>
              						<option value=4 <c:if test="${4==preciseUiBean.priority4}">
                            	 selected
                            	</c:if>>4</option>
              						<option value=5 <c:if test="${5==preciseUiBean.priority4}">
                            	 selected
                            	</c:if>>5</option>
              						<option value=6 <c:if test="${6==preciseUiBean.priority4}">
                            	 selected
                            	</c:if>>6</option>
              						<option value=7 <c:if test="${7==preciseUiBean.priority4}">
                            	 selected
                            	</c:if>>7</option>
              						<option value=8 <c:if test="${8==preciseUiBean.priority4}">
                            	 selected
                            	</c:if>>8</option>
              						<option value=9 <c:if test="${9==preciseUiBean.priority4}">
                            	 selected
                            	</c:if>>9</option>
                				</select>
                
                <span>预制条件</span>
                <select  disabled name="preciseUiBean.tvnExpression4">
                  
                 				 <option value=0 <c:if test="${0==preciseUiBean.tvnExpression4}">
                            	 selected
                            	</c:if>>不等于</option>
                            	<option value=1 <c:if test="${1==preciseUiBean.tvnExpression4}">
                            	 selected
                            	</c:if>>等于</option>
                </select>
                <input type="hidden" id="preciseUiBean.tvnNumber4" name="preciseUiBean.tvnNumber4"  value="${preciseUiBean.tvnNumber4}"/>
          </p></td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList"><div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable4">
              <c:forEach items="${preciseUiBean.assetSortIdList}" var="categoryVar" >
              <tr id="contentrow">
                 <td class="dot"><input type="checkbox" name="selectedcheckbox4" value="checkbox"/></td>
                 <td >
                 <input type="hidden" id="preciseUiBean.assetSortId" name="preciseUiBean.assetSortId"  value="${categoryVar.datavalue}"/>
                 <input type="hidden" id="preciseUiBean.assetSortName" name="preciseUiBean.assetSortName"  value="${categoryVar.dataname}"/>
                 ${categoryVar.dataname}
                 </td>
              </tr>
             </c:forEach>
            </table>
        </div></td>
      </tr>
   
    </table>
    
    <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable5">
      <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="checkbox3" value="checkbox" onclick="selectAll(this, 'selectedcheckbox5');"/></td>
        <td><b>回放频道组</b><div id="npvrdiv" style="display:"/></td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList">
	        <div>
	            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable5">
	            	<c:if test="${fn:length(ployTimeCGroup.channelgroupList) > 0 && channelGroupType == '2'}">
	            		<c:forEach items="${ployTimeCGroup.channelgroupList}" var="npvrGroup" >
				            <tr id="contentrow">
				                <td class="dot"><input type="checkbox" disabled name="selectedcheckbox5" value="checkbox"/></td>		                 
				                <td width="45%"><input type="hidden" id="channelgroup" name="channelgroup"  value="${npvrGroup.groupId}"/><a href="#" onclick="javascript:showNpvrChannelGroupRef(${npvrGroup.groupId});">${npvrGroup.groupName}</a></td>
				                <td align="left" width="45%">
				                	<select disabled id="priority" name="priority" style="width:40px">
		             					<option value="1" <c:if test="${1==npvrGroup.priority}">selected</c:if> > 1 </option>
		             					<option value="2" <c:if test="${2==npvrGroup.priority}">selected</c:if> > 2 </option>
		             					<option value="3" <c:if test="${3==npvrGroup.priority}">selected</c:if> > 3 </option>
		             					<option value="4" <c:if test="${4==npvrGroup.priority}">selected</c:if> > 4 </option>
		             					<option value="5" <c:if test="${5==npvrGroup.priority}">selected</c:if> > 5 </option>
		             					<option value="6" <c:if test="${6==npvrGroup.priority}">selected</c:if> > 6 </option>
		             					<option value="7" <c:if test="${7==npvrGroup.priority}">selected</c:if> > 7 </option>
		             					<option value="8" <c:if test="${8==npvrGroup.priority}">selected</c:if> > 8 </option>
		             					<option value="9" <c:if test="${9==npvrGroup.priority}">selected</c:if> > 9 </option>
				                	</select>
				                </td>
				                <td width="5%"><input type="hidden" name="channelGroupType" value="2"/></td>
				             </tr>
			             </c:forEach>
	            	</c:if>
	              
	            </table>
	        </div>
        </td>
      </tr>
      
    </table>
    
    
 <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable16">
      <tr class="title">
        <td height="28" class="dot"><input  disabled type="checkbox" name="checkbox3" value="checkbox" onclick="selectAll(this, 'selectedcheckbox16');"/></td>
        <td><b>回放频道</b>
           <div id="npvrdiv" style="display:"> <p><span>优先级别：</span>
                <select disabled  id="preciseUiBean.priority1" name="preciseUiBean.priority5" style="width:40px">
              						<option value=1 <c:if test="${1==preciseUiBean.priority5}">
                            	 selected
                            	</c:if>>1</option>
              						<option value=2 <c:if test="${2==preciseUiBean.priority5}">
                            	 selected
                            	</c:if>>2</option>
              						<option value=3 <c:if test="${3==preciseUiBean.priority5}">
                            	 selected
                            	</c:if>>3</option>
              						<option value=4 <c:if test="${4==preciseUiBean.priority5}">
                            	 selected
                            	</c:if>>4</option>
              						<option value=5 <c:if test="${5==preciseUiBean.priority5}">
                            	 selected
                            	</c:if>>5</option>
              						<option value=6 <c:if test="${6==preciseUiBean.priority5}">
                            	 selected
                            	</c:if>>6</option>
              						<option value=7 <c:if test="${7==preciseUiBean.priority5}">
                            	 selected
                            	</c:if>>7</option>
              						<option value=8 <c:if test="${8==preciseUiBean.priority5}">
                            	 selected
                            	</c:if>>8</option>
              						<option value=9 <c:if test="${9==preciseUiBean.priority5}">
                            	 selected
                            	</c:if>>9</option>
                				</select>
                
                <span>预制条件</span>
                <select disabled  name="preciseUiBean.tvnExpression5">
                  
                 				 <option value=0 <c:if test="${0==preciseUiBean.tvnExpression5}">
                            	 selected
                            	</c:if>>不等于</option>
                            	<option value=1 <c:if test="${1==preciseUiBean.tvnExpression5}">
                            	 selected
                            	</c:if>>等于</option>
                </select>
                <input type="hidden" id="preciseUiBean.tvnNumber5" name="preciseUiBean.tvnNumber5"  value="${preciseUiBean.tvnNumber5}"/>
           </p>
           </div></td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList"><div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable16">
              <c:forEach items="${preciseUiBean.playbackChannelIdList}" var="npvrVar" >
              <tr id="contentrow">
                 <td class="dot"><input type="checkbox" name="selectedcheckbox16" value="checkbox"/></td>
                 <td >
                 <input type="hidden" id="preciseUiBean.playbackChannelId" name="preciseUiBean.playbackChannelId"  value="${npvrVar.datavalue}"/>
                 <input type="hidden" id="preciseUiBean.playbackChannelName" name="preciseUiBean.playbackChannelName"  value="${npvrVar.dataname}"/>
                 ${npvrVar.dataname}
                 </td>
              </tr>
             </c:forEach>
            </table>
        </div></td>
      </tr>
     
    </table>
    
    
    
    
    
    
    <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable6">
      <tr class="title">
        <td height="28" class="dot"><input  disabled type="checkbox" name="checkbox3" value="checkbox" onclick="selectAll(this, 'selectedcheckbox6');"/></td>
        <td><b>回看栏目</b>
           <div id="lookbackdiv" style="display:"> <p><span>优先级别：</span>
                <select  disabled id="preciseUiBean.priority1" name="preciseUiBean.priority6" style="width:40px">
              						<option value=1 <c:if test="${1==preciseUiBean.priority6}">
                            	 selected
                            	</c:if>>1</option>
              						<option value=2 <c:if test="${2==preciseUiBean.priority6}">
                            	 selected
                            	</c:if>>2</option>
              						<option value=3 <c:if test="${3==preciseUiBean.priority6}">
                            	 selected
                            	</c:if>>3</option>
              						<option value=4 <c:if test="${4==preciseUiBean.priority6}">
                            	 selected
                            	</c:if>>4</option>
              						<option value=5 <c:if test="${5==preciseUiBean.priority6}">
                            	 selected
                            	</c:if>>5</option>
              						<option value=6 <c:if test="${6==preciseUiBean.priority6}">
                            	 selected
                            	</c:if>>6</option>
              						<option value=7 <c:if test="${7==preciseUiBean.priority6}">
                            	 selected
                            	</c:if>>7</option>
              						<option value=8 <c:if test="${8==preciseUiBean.priority6}">
                            	 selected
                            	</c:if>>8</option>
              						<option value=9 <c:if test="${9==preciseUiBean.priority6}">
                            	 selected
                            	</c:if>>9</option>
                				</select>
                
                <span>预制条件</span>
                <select  disabled name="preciseUiBean.tvnExpression6">
              				  <option value=0 <c:if test="${0==preciseUiBean.tvnExpression6}">
                            	 selected
                            	</c:if>>不等于</option>
                              <option value=1 <c:if test="${1==preciseUiBean.tvnExpression6}">
                            	 selected
                            	</c:if>>等于</option>
                  
                </select>
                <input type="hidden" id="preciseUiBean.tvnNumber6" name="preciseUiBean.tvnNumber6"  value="${preciseUiBean.tvnNumber6}"/>
           </p>
           </div></td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList"><div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable6">
              <c:forEach items="${preciseUiBean.lookbackCategoryIdList}" var="columnVar" >
              <tr id="contentrow">
                 <td class="dot"><input type="checkbox" name="selectedcheckbox6" value="checkbox"/></td>
                 <td >
                 <input type="hidden" id="preciseUiBean.lookbackCategoryId" name="preciseUiBean.lookbackCategoryId"  value="${columnVar.datavalue}"/>
                 <input type="hidden" id="preciseUiBean.lookbackCategoryName" name="preciseUiBean.lookbackCategoryName"  value="${columnVar.dataname}"/>
                 ${columnVar.dataname}
                 </td>
              </tr>
             </c:forEach>
            </table>
        </div></td>
      </tr>
   
    </table>
    
    <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable8">
      <tr class="title">
        <td height="28" class="dot"><input disabled  type="checkbox" name="checkbox3" value="checkbox" onclick="selectAll(this, 'selectedcheckbox8');"/></td>
        <td><b>影片</b>
            <div id="assetdiv"><p><span>优先级别：</span>
                <select disabled  id="preciseUiBean.priority8" name="preciseUiBean.priority8" style="width:40px">
              						<option value=1 <c:if test="${1==preciseUiBean.priority8}">
                            	 selected
                            	</c:if>>1</option>
              						<option value=2 <c:if test="${2==preciseUiBean.priority8}">
                            	 selected
                            	</c:if>>2</option>
              						<option value=3 <c:if test="${3==preciseUiBean.priority8}">
                            	 selected
                            	</c:if>>3</option>
              						<option value=4 <c:if test="${4==preciseUiBean.priority8}">
                            	 selected
                            	</c:if>>4</option>
              						<option value=5 <c:if test="${5==preciseUiBean.priority8}">
                            	 selected
                            	</c:if>>5</option>
              						<option value=6 <c:if test="${6==preciseUiBean.priority8}">
                            	 selected
                            	</c:if>>6</option>
              						<option value=7 <c:if test="${7==preciseUiBean.priority8}">
                            	 selected
                            	</c:if>>7</option>
              						<option value=8 <c:if test="${8==preciseUiBean.priority8}">
                            	 selected
                            	</c:if>>8</option>
              						<option value=9 <c:if test="${9==preciseUiBean.priority8}">
                            	 selected
                            	</c:if>>9</option>
                				</select>
                
                <span>预制条件</span>
                <select disabled name="preciseUiBean.tvnExpression8">
                   <option value=0 <c:if test="${0==preciseUiBean.tvnExpression8}">
                            	 selected
                            	</c:if>>不等于</option>
                            	 <option value=1 <c:if test="${1==preciseUiBean.tvnExpression8}">
                            	 selected
                            	</c:if>>等于</option>
                
                </select>
                 <input type="hidden" id="preciseUiBean.tvnNumber8" name="preciseUiBean.tvnNumber8"  value="${preciseUiBean.tvnNumber8}"/>
            </p>
            </div></td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList"><div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable8">
              <c:forEach items="${preciseUiBean.assetNameList}" var="assetnameVar" >
              <tr id="contentrow">
                 <td class="dot"><input type="checkbox" name="selectedcheckbox8" value="checkbox"/></td>
                 <td >
                 <input  disabled type="text" id="preciseUiBean.assetName" name="preciseUiBean.assetName"  value="${assetnameVar.datavalue}"/>
                </td>
              </tr>
             </c:forEach>
            </table>
        </div></td>
      </tr>
    
    </table>
    <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable11">
      <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="checkbox3" value="checkbox"  onclick="selectAll(this, 'selectedcheckbox11');"/></td>
	       <td><b>区域</b>
            <p><span>优先级别：</span>
                <select  disabled id="preciseUiBean.priority11" name="preciseUiBean.priority11" style="width:40px">
              						<option value=1 <c:if test="${1==preciseUiBean.priority11}">
                            	 selected
                            	</c:if>>1</option>
              						<option value=2 <c:if test="${2==preciseUiBean.priority11}">
                            	 selected
                            	</c:if>>2</option>
              						<option value=3 <c:if test="${3==preciseUiBean.priority11}">
                            	 selected
                            	</c:if>>3</option>
              						<option value=4 <c:if test="${4==preciseUiBean.priority11}">
                            	 selected
                            	</c:if>>4</option>
              						<option value=5 <c:if test="${5==preciseUiBean.priority11}">
                            	 selected
                            	</c:if>>5</option>
              						<option value=6 <c:if test="${6==preciseUiBean.priority11}">
                            	 selected
                            	</c:if>>6</option>
              						<option value=7 <c:if test="${7==preciseUiBean.priority11}">
                            	 selected
                            	</c:if>>7</option>
              						<option value=8 <c:if test="${8==preciseUiBean.priority11}">
                            	 selected
                            	</c:if>>8</option>
              						<option value=9 <c:if test="${9==preciseUiBean.priority11}">
                            	 selected
                            	</c:if>>9</option>
                				</select>
                
                <span>预制条件</span>
                <select  disabled name="preciseUiBean.tvnExpression11">
              				  <option value=0 <c:if test="${0==preciseUiBean.tvnExpression11}">
                            	 selected
                            	</c:if>>不等于</option>
                              <option value=1 <c:if test="${1==preciseUiBean.tvnExpression11}">
                            	 selected
                            	</c:if>>等于</option>
                  
                </select>
             
          </p>
          </td>
		</td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList">
        <div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable11">
              <tr >
                <td class="dot"></td>
                <td >一级区域</td>
                <td >二级区域</td>
                <td >三级区域</td>
              </tr>
                <c:forEach items="${preciseUiBean.userAreaList}" var="preciseareaVar" varStatus="status">  
              <tr id="contentrow">
                <td class="dot"><input type="checkbox" name="selectedcheckbox11" value="checkbox"/></td>
                <td name="arearow0" id="areaone" >
                             <input type='hidden' id='preciseUiBean.userAreaName${status.index}' name='preciseUiBean.userAreaName'  value='${preciseareaVar.dataname}'/>
                             <select  disabled onchange="changeArea(this,${status.index})" id="preciseUiBean.userArea" name="preciseUiBean.userArea" style="width:80px">
                				<c:forEach items="${pageLocation.dataList}" var="areaVar" >
                				<c:if test="${'02'==areaVar.locationtype}">
                            	              						<option value=${areaVar.locationcode} 
                            	              						<c:if test="${preciseareaVar.datavalue==areaVar.locationcode}">
                            	              						selected
                            	              						 </c:if>>
                            	${areaVar.locationname}</option>   
                            </c:if>
                            	</c:forEach>           					
                				</select></td>
               <td name="arearow0" id="areatwo${status.index}" > <select  disabled onchange="changeArea2(this,${status.index})" id="preciseUiBean.userArea2" name="preciseUiBean.userArea2" style="width:80px">
              					<option value=0 >全部</option><c:forEach items="${pageLocation.dataList}" var="area2Var" >
                				<c:if test="${preciseareaVar.datavalue==area2Var.parentlocation}">
                            	              						<option value=${area2Var.locationcode}  
																	<c:if test="${preciseareaVar.data2value==area2Var.locationcode}">
                            	              						selected
                            	              						 </c:if>>
                            	${area2Var.locationname}</option>   
                            </c:if>
                            	</c:forEach>           					
                				</select></td>
               <td name="arearow0" id="areathree${status.index}" ><select disabled  onchange="changeArea3(this,${status.index})" id="preciseUiBean.userArea3" name="preciseUiBean.userArea3" style="width:120px">
              					<option value=0>全部</option><c:forEach items="${pageLocation.dataList}" var="area3Var" >
                				<c:if test="${preciseareaVar.data2value==area3Var.parentlocation}">
                            	              						<option value=${area3Var.locationcode}  
																	<c:if test="${preciseareaVar.data3value==area3Var.locationcode}">
                            	              						selected
                            	              						 </c:if>>
                            	${area3Var.locationname}</option>   
                            </c:if>
                            	</c:forEach>           					
                				</select></td>
              </tr>

              </c:forEach> 
              
            </table>
        </div>
        </td>
      </tr>
    
    </table>
     <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable12">
      <tr class="title">
        <td height="28" class="dot"><input disabled type="checkbox" disabled="disabled" name="checkbox3" value="checkbox" onclick="selectAll(this, 'selectedcheckbox12');"/></td>
        <td><b>行业</b>
            <p><span>优先级别：</span>
                <select disabled id="preciseUiBean.priority12" name="preciseUiBean.priority12" style="width:40px">
              						<option value=1 <c:if test="${1==preciseUiBean.priority12}">
                            	 selected
                            	</c:if>>1</option>
              						<option value=2 <c:if test="${2==preciseUiBean.priority12}">
                            	 selected
                            	</c:if>>2</option>
              						<option value=3 <c:if test="${3==preciseUiBean.priority12}">
                            	 selected
                            	</c:if>>3</option>
              						<option value=4 <c:if test="${4==preciseUiBean.priority12}">
                            	 selected
                            	</c:if>>4</option>
              						<option value=5 <c:if test="${5==preciseUiBean.priority12}">
                            	 selected
                            	</c:if>>5</option>
              						<option value=6 <c:if test="${6==preciseUiBean.priority12}">
                            	 selected
                            	</c:if>>6</option>
              						<option value=7 <c:if test="${7==preciseUiBean.priority12}">
                            	 selected
                            	</c:if>>7</option>
              						<option value=8 <c:if test="${8==preciseUiBean.priority12}">
                            	 selected
                            	</c:if>>8</option>
              						<option value=9 <c:if test="${9==preciseUiBean.priority12}">
                            	 selected
                            	</c:if>>9</option>
                				</select>
                
                <span>预制条件</span>
                <select disabled name="preciseUiBean.tvnExpression12">
                  <option value=0 <c:if test="${0==preciseUiBean.tvnExpression12}">
                            	 selected
                            	</c:if>>不等于</option>
                            	 <option value=1 <c:if test="${1==preciseUiBean.tvnExpression12}">
                            	 selected
                            	</c:if>>等于</option>
                 
                </select>
                 <input type="hidden" id="preciseUiBean.tvnNumber12" name="preciseUiBean.tvnNumber12"  value="${preciseUiBean.tvnNumber12}"/>
              
          </p></td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList"><div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable12">
              <c:forEach items="${preciseUiBean.userindustrysList}" var="industryVar" >
              <tr id="contentrow">
                 <td class="dot"><input type="checkbox" disabled="disabled" name="selectedcheckbox12" value="checkbox"/></td>
                 <td >
                 <input type="hidden" id="preciseUiBean.userindustrys" name="preciseUiBean.userindustrys"  value="${industryVar.datavalue}"/>
                 <input type="hidden" id="preciseUiBean.userindustrysName" name="preciseUiBean.userindustrysName"  value="${industryVar.dataname}"/>
                 ${industryVar.dataname}
                 </td>
              </tr>
             </c:forEach>
            </table>
        </div></td>
      </tr>
    
    </table>
    <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable13">
      <tr class="title">
        <td height="28" class="dot"><input disabled type="checkbox" name="checkbox3" value="checkbox" onclick="selectAll(this, 'selectedcheckbox13');"/></td>
        <td><b>级别</b>
            <p><span>优先级别：</span>
                <select  disabled id="preciseUiBean.priority13" name="preciseUiBean.priority13" style="width:40px">
              						<option value=1 <c:if test="${1==preciseUiBean.priority13}">
                            	 selected
                            	</c:if>>1</option>
              						<option value=2 <c:if test="${2==preciseUiBean.priority13}">
                            	 selected
                            	</c:if>>2</option>
              						<option value=3 <c:if test="${3==preciseUiBean.priority13}">
                            	 selected
                            	</c:if>>3</option>
              						<option value=4 <c:if test="${4==preciseUiBean.priority13}">
                            	 selected
                            	</c:if>>4</option>
              						<option value=5 <c:if test="${5==preciseUiBean.priority13}">
                            	 selected
                            	</c:if>>5</option>
              						<option value=6 <c:if test="${6==preciseUiBean.priority13}">
                            	 selected
                            	</c:if>>6</option>
              						<option value=7 <c:if test="${7==preciseUiBean.priority13}">
                            	 selected
                            	</c:if>>7</option>
              						<option value=8 <c:if test="${8==preciseUiBean.priority13}">
                            	 selected
                            	</c:if>>8</option>
              						<option value=9 <c:if test="${9==preciseUiBean.priority13}">
                            	 selected
                            	</c:if>>9</option>
                				</select>
                
                <span>预制条件</span>
                <select name="preciseUiBean.tvnExpression13" disabled>
                  <option value=0 <c:if test="${0==preciseUiBean.tvnExpression13}">
                            	 selected
                            	</c:if>>不等于</option>
                            	 <option value=1 <c:if test="${1==preciseUiBean.tvnExpression13}">
                            	 selected
                            	</c:if>>等于</option>
                 
                </select>
                 <input type="hidden" id="preciseUiBean.tvnNumber13" name="preciseUiBean.tvnNumber13"  value="${preciseUiBean.tvnNumber13}"/>
              
          </p></td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList"><div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable13">
              <c:forEach items="${preciseUiBean.userlevelsList}" var="levelVar" >
              <tr id="contentrow">
                 <td class="dot"><input type="checkbox" disabled="disabled" name="selectedcheckbox13" value="checkbox"/></td>
                 <td >
                 <input type="hidden" id="preciseUiBean.userlevels" name="preciseUiBean.userlevels"  value="${levelVar.datavalue}"/>
                 <input type="hidden" id="preciseUiBean.userlevelName" name="preciseUiBean.userlevelName"  value="${levelVar.dataname}"/>
                 ${levelVar.dataname}
                 </td>
              </tr>
             </c:forEach>
            </table>
        </div></td>
      </tr>
    </table>
    
      <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable14">
      <tr class="title">
        <td height="28" class="dot"><input disabled="disabled" type="checkbox" name="checkbox3" value="checkbox"  onclick="selectAll(this, 'selectedcheckbox14');"/></td>
	       <td><b>投放区域</b>            
          </td>
		</td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList">
        <div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable14">
              <tr >
                <td class="dot"></td>
                <td >投放区域</td>
              </tr>
                <c:forEach items="${areaChannelUiBean.userAreaList}" var="preciseareaVar" varStatus="status">  
              <tr id="contentrow">
                <td class="dot"><input disabled="disabled" type="checkbox" name="selectedcheckbox14" value="checkbox"/></td>
                <td name="arearow0" id="areaone" >
                              <select disabled="disabled" id="areaChannelUiBean.userArea" name="areaChannelUiBean.userArea" style="width:80px">
                				<c:forEach items="${pageReleaseLocation.dataList}" var="areaVar" >
                				              						<option value="${areaVar.areaCode}" 
                            	              						<c:if test="${preciseareaVar==areaVar.areaCode}">
                            	              						selected
                            	              						 </c:if>>
                            	${areaVar.areaName}  </option>   
                           
                            	</c:forEach>           					
                				</select></td>             
              </tr>
              </c:forEach> 
              
            </table>
        </div>
        </td>
      </tr>
     
    </table>
    
    
     <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable15">
      <tr class="title">
        <td height="28" class="dot"><input disabled="disabled" type="checkbox" name="checkbox3" value="checkbox"  onclick="selectAll(this, 'selectedcheckbox15');"/></td>
	       <td><b>广播投放频道</b>
          </p>
             
          </td>
		</td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList">
        <div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable15">
              <tr >
                <td class="dot"></td>
                <td >广播频道组</td>
              <td >优先级</td>
              </tr>
              <c:if test="${channelGroupType == '3'}">
                <c:forEach items="${ployTimeCGroup.channelgroupList}" var="groupVar" >
              <tr id="contentrow">
                <td class="dot"><input disabled="disabled" type="checkbox" name="selectedcheckbox15" value="checkbox"/></td>
                <td width="45%"><input type="hidden" id="bchannelgroup" name="bchannelgroup"  value="${groupVar.groupId}"/><a href="#" onclick="javascript:showChannelGroupRef(${groupVar.groupId});">
                ${groupVar.groupName}</a></td>
                <td align="left" width="45%"><select disabled="disabled" id="priority" name="priority" style="width:40px">
              						<option value="1" <c:if test="${1==groupVar.priority}">
                            	 selected
                            	</c:if>>1</option>
              						<option value="2" <c:if test="${2==groupVar.priority}">
                            	 selected
                            	</c:if>>2</option>
              						<option value="3" <c:if test="${3==groupVar.priority}">
                            	 selected
                            	</c:if>>3</option>
              						<option value="4" <c:if test="${4==groupVar.priority}">
                            	 selected
                            	</c:if>>4</option>
              						<option value="5" <c:if test="${5==groupVar.priority}">
                            	 selected
                            	</c:if>>5</option>
              						<option value="6" <c:if test="${6==groupVar.priority}">
                            	 selected
                            	</c:if>>6</option>
              						<option value="7" <c:if test="${7==groupVar.priority}">
                            	 selected
                            	</c:if>>7</option>
              						<option value="8" <c:if test="${8==groupVar.priority}">
                            	 selected
                            	</c:if>>8</option>
              						<option value="9" <c:if test="${9==groupVar.priority}">
                            	 selected
                            	</c:if>>9</option>
                				</select></td>
              </tr>
			 </c:forEach></c:if>
              
            </table>
        </div>
        </td>
      </tr>
    </table>
    
    <table cellspacing="1" class="searchList"  style="display: none;" id="selectedtable17">
      <tr class="title">
        <td height="28" class="dot"><input type="checkbox" disabled="disabled" name="checkbox3" value="checkbox" onclick="selectAll(this, 'selectedcheckbox17');"/></td>
        <td><b>TVN号</b>
            <div id="assetdiv"><p><span>优先级别：</span>
                <select id="preciseUiBean.priority17" disabled="disabled" name="preciseUiBean.priority17" style="width:40px">
              					<option value="1" <c:if test="${1==preciseUiBean.priority17}">
                            	 selected
                            	</c:if>>1</option>
              						<option value="2" <c:if test="${2==preciseUiBean.priority17}">
                            	 selected
                            	</c:if>>2</option>
              						<option value="3" <c:if test="${3==preciseUiBean.priority17}">
                            	 selected
                            	</c:if>>3</option>
              						<option value="4" <c:if test="${4==preciseUiBean.priority17}">
                            	 selected
                            	</c:if>>4</option>
              						<option value="5" <c:if test="${5==preciseUiBean.priority17}">
                            	 selected
                            	</c:if>>5</option>
              						<option value="6" <c:if test="${6==preciseUiBean.priority17}">
                            	 selected
                            	</c:if>>6</option>
              						<option value="7" <c:if test="${7==preciseUiBean.priority17}">
                            	 selected
                            	</c:if>>7</option>
              						<option value="8" <c:if test="${8==preciseUiBean.priority17}">
                            	 selected
                            	</c:if>>8</option>
              						<option value="9" <c:if test="${9==preciseUiBean.priority17}">
                            	 selected
                            	</c:if>>9</option>
                				</select>
                <span>预制条件</span>
                <select name="preciseUiBean.tvnExpression17" disabled="disabled">
                  <option value="0" <c:if test="${0==preciseUiBean.tvnExpression17}">
               	 selected
               	</c:if>>不等于</option>
               	 <option value="1" <c:if test="${1==preciseUiBean.tvnExpression17}">
               	 selected
               	</c:if>>等于</option>
                 
                </select>
          </p>
          </div></td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList"><div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable17">
              <c:forEach items="${preciseUiBean.tvnNumberList}" var="tvnNumber" >
              <tr id="contentrow">
                 <td class="dot"><input type="checkbox"  disabled="disabled" name="selectedcheckbox17" value="checkbox"/></td>
                 <td >
                 <input type="text" id="preciseUiBean.tvnNumber" disabled="disabled" name="preciseUiBean.tvnNumber"  value="${tvnNumber.datavalue}"/>
                </td>
              </tr>
             </c:forEach>
            </table>
        </div></td>
      </tr>
     
    </table>
    
    <table cellspacing="1"  class="searchList"  style="display: none;" id="selectedtable18">
      <tr class="title">
        <td height="28" class="dot"><input type="checkbox" name="checkbox18" value="checkbox"  onclick="selectAll(this, 'selectedcheckbox18');"/></td>
	       <td><b>类型选择</b>
          </td>
      </tr>
      <tr>
        <td colspan="2" class="conditionList">
        <div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contenttable18">
              <tr >
                <td class="dot"></td>
                <td >投放类型</td>
	            	<c:forEach items="${preciseUiBean.typeMenuList}" var="NVODtype">
			              <tr id="contentrow">
			                <td class="dot"><input type="checkbox" name="selectedcheckbox18" value="checkbox"/></td>
			                <td width="45%">
			                <input type="hidden" id="preciseUiBean.typeCode" name="preciseUiBean.typeCode"  value="${NVODtype.datavalue}" readonly/>
			                <input  id="preciseUiBean.typeName" name="preciseUiBean.typeName"  value="${NVODtype.dataname}" readonly/>
			                </td>
			                </td>
			                <td>
			                <input type="hidden" name="channelNVODType" value="1"/>
			                </td>
			              </tr>
	               </c:forEach>
            </table>
        </div>
        </td>
      </tr>
      <tr>
      
        <td colspan="2"><input name="button" type="button" class="bottonTwo" value="添加" onclick="selectType()"/>
            <input name="button" type="button" class="bottonTwo" value="删除" onclick="del_tbl('contenttable18','selectedcheckbox18')" />
        </td>
      </tr>
    </table>
    
    
  </div>
  <table cellspacing="1" class="searchList">
    <tr >
      <td align="right" style="width: 10%" >审核意见：</td>
						<td>
							<textarea  style="width: 40%"  rows="6" name="ploy.auditOption" onchange=""
								>${ploy.auditOption}</textarea>
						</td>
      
    </tr>
  <tr>
    <td colspan="2" class="searchSec" align=left>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" class="btn" value="通过" onclick="submitForm(1);"/>&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" class="btn" value="驳回" onclick="submitForm(2);"/>&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" class="btn" value="取 消" onclick="history.back(-1);"/>
       </td>
    </tr>
  </table>
</div>
</form>
</body>
</html>