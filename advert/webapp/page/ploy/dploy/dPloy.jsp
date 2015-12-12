<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link id="maincss"  rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css" media="all"/>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery/jquery-1.9.0.js"></script>
<link rel="stylesheet" href="<%=path %>/css/jquery/jquery.ui.all.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=path %>/js/jquery/ui/jquery-ui-1.10.0.custom.js"></script>
	
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>
<script type="text/javascript" src="<%=path %>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/util.js"></script>
<title>无标题文档</title>
</head>
<script type="text/javascript">

	var j =${fn:length(ploy.ployDetailList)};

	window.onload = function() {
		
		changePosition('${ploy.dposition.positionCode}');
		initItem(); 
		var status = $("#ploy_status").val();
		if(status == '4'){
			$(".bottonTwo").attr('disabled',"true");
			$("#saveBtn").attr('disabled',"true");
		}
	}
		
	/**
	 * 绑定广告位
	 */
    function submitForm(){
        if (!validate()){
			return;
		}
		
       if(!checkPloy()){
           return false;
       }
      
       document.getElementById("ploy.positionCode").disabled=false;
       document.getElementById("saveForm").action="<%=path %>/dploy/saveDPloy.action";
       document.getElementById("saveForm").submit();
     
    }

    function validate(){
        
        if(document.getElementById("ploy.ployName").value==null || ''==document.getElementById("ploy.ployName").value ){
			alert('请输入策略名称');
			document.getElementById("ploy.ployName").focus();
			return false ;
		}
        if(document.getElementById("ploy.ployName").value.indexOf("_") != -1){
        	alert('策略名称不能含有下划线  ');
			document.getElementById("ploy.ployName").focus();
			return false ;
        }
        if(validateSpecialCharacterAfter(document.getElementById("ploy.ployName").value)){
			alert('不能输入特殊字符');
			document.getElementById("ploy.ployName").value="";
		    document.getElementById("ploy.ployName").focus();
			return false ;
     	}
        
        if(document.getElementById("ploy.positionCode").value==null || ''==document.getElementById("ploy.positionCode").value){
			alert('请选择广告位');
			return false ;
		}
		var result = true;
        var times = $("#table2 input[type=text]");
		var start='0';
        times.each(function(m, out){
        	if(out.value==''){
        		alert("开始时间或结束时间不能为空！");
        		out.focus();
        		result = false;
        		return result;
        	}
        	
        	if(m%2==0){
        		start = out.value;
        	}else{
        		if(start >= out.value){
        			alert("结束时间要大于开始时间！");
        			out.focus();
        			result = false;
        			return result;
        		}
        		
        		times.each(function(n, int){
        			if(n== m){
        				return true;
        			}
        			if(int.value >start && int.value < out.value){
        				alert((n%2==0?"开始":"结束")+"时间选择有冲突,请重新选择！");
        				int.focus();
        				result = false;
        				return result;
        			}
        		});
        		return result;
        	}
        
        });
        if(!result){
        	return result;
        }
        
       var areas = $("#table1 select");
       var hash = {}; 
       areas.each(function(x,area){
           if(hash[area.value]){
       	   	  alert("不能选择重复的区域！");
       		  area.focus();
        	  result = false;
        	  return result;
       	   }
       	   hash[area.value] = true; 
	 	   if(areas.size() > 1){
	 	       if(area.value=='' || area.value=='0' || area.value=='152000000000'){
		 		   alert("选择多个区域时不能选择河南！");
		 	       area.focus();
	        	   result = false;
	        	   return result;
 	    	   }
	 	   }
       
       });
       return result;	 	
       		 
    }
    function checkPloy() {
 			var ployName=document.getElementById("ploy.ployName").value;
 			var ployId = document.getElementById("ploy.id").value;
 			var result = false;
          	$.ajax({
                type:"get",
                async:false,
                url:"<%=path%>/dploy/checkDPloy.action",
                data:{"ploy.ployName":ployName,"ploy.id":ployId},//Ajax传递的参数
                success:function(mess){
                	 if(mess=="1"){
						alert("策略名已存在，请重新输入！");
						result = false;
                     }else{
                     	result = true;
                     }
                     
                },
                error:function(mess)
                {
                	alert("未知错误");
                }
            });
            return result;
	 } 
 	
	 //更改广告位
	 function changePosition(positionCode){
		 var ployType ;
		  $.ajax({
                type:"get",
                async:false,
                url:"<%=path%>/dploy/getPositionPloy.action",
                data:{'position.positionCode':positionCode},
                success:function(mess){
                    ployType = eval(mess);
                },
                error:function(mess){
                	alert("未知错误");
                }
           });
           $("#positionCode").val(positionCode);
           if(ployType != ''){
	           	$("#typeList span").remove();
	           	$(".boxList table").remove();
	           	$.each(ployType, function(key,value) {     
	   				 $("#typeList").append('<span><input type="checkbox" id="type'+key+'" value="'+key+'" checked disabled/>'+value+'</span>');
	   				 createTable(key,value);        
				});
       		}
	 }

	 function createTable(index, title){
	 	var tab = '<table cellspacing="1" class="searchList">'
	     +' <tr class="title">'
	     +'   <td height="28" class="dot"><input type="checkbox" onclick="selectAll(this,\'checkbox'+index+'\');"/></td>'
		 +'      <td><b>'+title+'</b>'
		 +'	  </td>'
	     +' </tr>'
	     +' <tr>'
	     +'   <td colspan="2" class="conditionList">'
	     +'   <div>'
	     +'       <table width="100%" border="0" cellspacing="0" cellpadding="0" id="table'+index+'">'
	     +'         <tr >'
	     +'           <td class="dot"></td>'
	     +'           <td >'+(index==2?'开始时间':title)+'</td>'
	     +'           <td >'+(index==2?'结束时间':'')+'</td>'
	     +'         </tr>  '
	     +'       </table>'
	     +'   </div>'
	     +'   </td>'
	     +' </tr>'
	     +' <tr>'
	     +'   <td colspan="2"><input name="button" type="button" class="bottonTwo" value="添加" onclick="addItem('+index+')"/>'
	     +'   <input name="button" type="button" class="bottonTwo" value="删除" onclick="delItems('+index+')" />'
	     +'   </td>'
	     +' </tr>'
		 +'</table>';
	 	$(".boxList").append(tab);
	 
	 }
	 function initItem(){
	    var detailJson = eval(${ploy.ployDetailJson});
	   	$.each(detailJson, function(i, detail){
	   		var type= detail.ployType;
	   		var item = '<tr><td class="dot"><input type="checkbox" name="checkbox'+type+'" value="checkbox" />'
	   				+'<input type="hidden" name="ploy.ployDetailList['+i+'].id" value="'+detail.id+'" /><input type="hidden" name="ploy.ployDetailList['+i+'].ployType" value="'+detail.ployType+'" />'
	   				+'</td>';
	    	switch(type){
	    		case '1':
	    			item+='<td><select name="ploy.ployDetailList['+i+'].typeValue" style="width:80px">'
	          			+'<c:forEach items="${areaList}" var="areaVar" >'
				        +'<option value="${areaVar.areaCode}" '+(${areaVar.areaCode}==detail.typeValue?'selected':'')+'>${areaVar.areaName}</option>'   
				        +'</c:forEach>' 				         					
				        +'</select></td>';
				   
				    break;
				case '2':
					item+='<td ><input name="ploy.ployDetailList['+i+'].startTime" type="text" readonly="readonly" onclick="WdatePicker({dateFmt:\'HH:mm:ss\'})" value="'+detail.startTime+'"/></td>'
              			+'<td ><input name="ploy.ployDetailList['+i+'].endTime" type="text" readonly="readonly" onclick="WdatePicker({dateFmt:\'HH:mm:ss\'})" value="'+detail.endTime+'"/></td>';
	    			break;
	    		default:
	    			item+='<td ><input name="ploy.ployDetailList['+i+'].typeValue" type="text"  value="${ploy.ployDetailList[i].typeValue}"/></td>'
              			+'<td ><input name="ploy.ployDetailList['+i+'].priority" type="text"  value="${ploy.ployDetailList[i].priority}"/></td>';
	    	
	    	}
	    	item+='</tr>';
	    	$("#table"+type+" tbody").append(item);
	   
	   	});
			 	
	 }
	 function addItem(index){
		var tab = $("#table"+index);
		var item ='<tr><td class="dot"><input type="checkbox" name="checkbox'+index+'"/><input type="hidden" name="ploy.ployDetailList['+j+'].ployType" value="'+index+'" /></td>';
		switch(index){
		case 1:
			item+='<td><select id="" name="ploy.ployDetailList['+j+'].typeValue" style="width:80px">'
	        	+'       <c:forEach items="${areaList}" var="areaVar" >'
	            +'    		<option value="${areaVar.areaCode}">${areaVar.areaName}</option>'   
	            +'       </c:forEach>'; 				         					
	        item+='</select></td>';
	        break;
		case 2:
	        item+='<td ><input name="ploy.ployDetailList['+j+'].startTime" type="text" readonly="readonly" onclick="WdatePicker({dateFmt:\'HH:mm:ss\'})"/></td>';
	        item+='<td ><input name="ploy.ployDetailList['+j+'].endTime" type="text" readonly="readonly" onclick="WdatePicker({dateFmt:\'HH:mm:ss\'})"/></td>';
	        break;
	    case 3:
	    case 4:
	    	selectChannelGroup();
	    default:
	    	break;
		}
		item +='</tr>';
		tab.append(item);
		j++;
	}
	
 	/**
 	*删除行元素  
 	*/
	function delItems(index){ 
		var checks=$("input[name=checkbox"+index+"]:checked");
		if(checks.size()==0){
        	alert("请选中要删除的行！");
        	return;
     	}
        checks.each(function(){
           $(this).parent().parent().remove();
        });
	}

	function selectChannelGroup()
	{
		var	height = 550;
		var width=750;
		
		var actinUrl = '<%=path%>/page/precise/queryChannelGroup.do';
		var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
		if(modelWin){	
		} 
	}

</script>
<body class="mainBody">
<form action="<%=path %>/dploy/saveDPloy.action" method="post" id="saveForm">
<div class="path">首页 &gt;&gt; 投放策略管理 &gt;&gt; 策略维护</div>
<div class="searchContent">
  <table cellspacing="1" class="searchList">
    <tr class="title">
      <td>策略编辑</td>
    </tr>
  <tr>
    <td class="searchCriteria">
      <span>*投放策略名称：</span>
      			             
               <input onkeypress="return validateSpecialCharacter();" maxlength="30" id="ploy.ployName" name="ploy.ployName" type="text" value="${ploy.ployName}"/>
               <span>&nbsp;&nbsp;&nbsp;&nbsp;广告位名称：</span> 
               
               <select id="ploy.positionCode" name="ploy.dposition.positionCode" onchange="changePosition(this.value);"  <c:if test="${ploy.id > 0}"> disabled="disabled" </c:if> >
	               <option value ="0">--请选择--</option>
	               <c:forEach items="${listPosition}" var="positionVar" >
	                     <option value="${positionVar.positionCode}" <c:if test="${positionVar.positionCode==ploy.dposition.positionCode}"> selected </c:if>  >
	                            ${positionVar.positionName}
	                	</option>
	               </c:forEach>
               </select>
               <input type="hidden" name="ploy.id" id="ploy.id" type="text" value="${ploy.id}"/>
               <input type="hidden" name="ploy.customer.id" type="text" value="${ploy.customer.id}"/>
               <input type="hidden" name="ploy.operatorId" type="text" value="${ploy.operatorId}"/>
               <input type="hidden" id="ploy_status" name="ploy.status" type="text" value="${ploy.status}"/>  
               <input  disabled="disabled" maxlength="30" id="positionCode" name="positionCode" type="text" style="width: 40px" value="${ploy.dposition.positionCode}"/>
               
       </td>
      </tr>
  <tr>
    <td class="searchSec">
    	<span id="typeList"></span>
         <input id="saveBtn" type="button" class="btn" value="保 存" onclick="submitForm();"/>&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="button" class="btn" value="取消" onclick="history.back(-1);"/>
    </td>
        </tr>
  </table>
  <div class="boxList">
    
  </div>
</div>
</form>
</body>
</html>
