<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tags/fmt.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="<%=path %>/css/new/main.css"/>
<link rel="stylesheet" href="<%=path %>/css/popUpDiv.css" type="text/css" />


<script type="text/javascript" src="<%=path %>/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="<%=path%>/js/material/uploadMaterial.js"></script>
<script type="text/javascript" src="<%=path%>/js/new/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path %>/js/easydialog/easydialog.min.js"></script>


<script type="text/javascript" src="<%=path %>/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=path%>/js/order/addOrUpdate.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery/upload/js/swfobject.js"></script>
<link rel="stylesheet" href="<%=path %>/css/easydialog/easydialog.css" type="text/css" />
<script type='text/javascript' src='<%=path %>/js/new/avit.js'></script>

<title>广告系统</title>

<script type="text/javascript">

	function init(positionJson){
		var materialType =document.getElementById('materialType').value;
	
		if(materialType!=null && materialType!=""){
			//预览素材
			preview(positionJson);
			if (materialType == 1 ){//视频
				document.getElementById("sel_material_type").options.add(new Option("视频","1"));
				setTimeout("showVideo($$('videoPath').value)", 1000);
			}
			else if (materialType == 0 ){//图片
				document.getElementById('div_video').style.display = "none";      
				document.getElementById('div_image').style.display = "";
				document.getElementById("sel_material_type").options.add(new Option("图片","0"));
			}
			else if (materialType == 2 ){//文字
				document.getElementById('div_text').style.display = "";      
				document.getElementById('div_video').style.display = "none";      
				document.getElementById("sel_material_type").options.add(new Option("文字","2"));
				showText();
			}
			else if (materialType == 3 )
			{//调查问卷			
				document.getElementById('div_video').style.display = "none";      
			    document.getElementById('div_question').style.display = "";
			    document.getElementById('div_question2').style.display = "";

				document.getElementById('sel_template_type').disabled=true;
				document.getElementById('sel_question_type').disabled=true;

			document.getElementById("sel_material_type").options.add(new Option("问卷","3"));
			}
			else if (materialType == 4 ){//ZIP
				document.getElementById('div_video').style.display = "none";      
				document.getElementById('div_image').style.display = "none";
				document.getElementById('div_zip').style.display = "";
				document.getElementById("sel_material_type").options.add(new Option("ZIP","4"));
			}
		}
	}

	/**
	 * 预览
	 */
	function preview(positionJson){
		var selPosition = eval(positionJson);
		/**为页面预览区域赋值*/
		var size = selPosition.widthHeight.split('*');
		width = size[0];
		height = size[1];
		var coordinate = selPosition.coordinate.split('*');
		//$("#pImage").attr("width",426).attr("height",240);
		$("#pImage1").attr("width",426).attr("height",240);
	    $("#pImage2").attr("width",426).attr("height",240);
	    $("#pImage3").attr("width",426).attr("height",240);
	    $("#pImage4").attr("width",426).attr("height",240);
		//$("#pImage").attr("src",getContextPath()+"/"+selPosition.backgroundPath);
		$("#mImage").attr("width",width).attr("height",height);
		$("#mImage4").attr("width",width).attr("height",height);
		$("#mImage,#video,#mImage4").css({
			width:width+"px",
			height:height+"px",
			position:'absolute',
			left: coordinate[0]+"px", 
			top: coordinate[1]+"px" 
		});
		
		$("#text").css({
			position:'absolute',
			width:width+"px",
			height:height+"px",
			left:coordinate[0]+"px",
			top:coordinate[1]+"px",
			'z-index':1
		});
		$("#text2").css({
		position:'absolute',
		width:width+"px",
		height:height+"px",
		left:coordinate[0]+"px",
		top:coordinate[1]+"px",
		'z-index':1
	});
	}

	/**
	 * 获取上下文路径
	 */ 
	function getContextPath() {
		var contextPath = document.location.pathname;
		var index = contextPath.substr(1).indexOf("/");
		contextPath = contextPath.substr(0, index + 1);
		delete index;
		return contextPath;
	}

	/**预览视频*/
	function showVideo(path){
		$("#vlc").css({
			width:width+"px",
			height:height+"px"
		});
		var vlc=document.getElementById("vlc");
		vlc.playlist.stop();
		vlc.playlist.clear();
		 // 添加播放地址
		 vlc.playlist.add(path);
		 // 播放
		 vlc.playlist.play();
	}

	/**预览文字*/
	function showText(){
		
		if($$("sel_textMeta_action").value!='0'){
		//滚动
		   $("#textContent").css({
			   'color':$$("textMeta.fontColor").value,
			   'font-size':$$("textMeta.fontSize").value+"px"
		   });
		   if($$("textMeta.rollSpeed").value!=''){
			$("#textContent").attr("scrollamount",$$("textMeta.rollSpeed").value);
		   }
		   
		   var content = $$("textMeta.contentMsg").value;
		   if($$("textMeta.URL").value!=''){
			 content = "<a href='"+$$("textMeta.URL").value+"'>"+content+"</a>";
		   }
		      if(isEmpty($$("textMeta.positionVertexCoordinates").value)){
		   
		   }else{
					var coordinates = $$("textMeta.positionVertexCoordinates").value.split("*");
					if(coordinates.length != 2 || !isNumber(coordinates[0]) || !isNumber(coordinates[1]) || coordinates[0]>1280 || coordinates[1]>720){
						
					}
					else
					{
					   var left = coordinates[0]/1280*426+"px";
					   var bottom = coordinates[1]/720*240+"px";					 				  
					   $('#text').css('left',left);
					   $('#text').css('top',bottom);
					}				
	  	}
    	if(!isEmpty($$("textMeta.positionWidthHeight").value)){
					var size = $$("textMeta.positionWidthHeight").value.split("*");
					if(size.length != 2 || !isNumber(size[0]) || !isNumber(size[1])){
					
					}
			    else
			    {
			    	   var width = size[0]/1280*426+"px";
					   var height = size[1]/720*240+"px";
					   $('#text').css('width',width);
					   $('#text').css('height',height);
					}
		 }
		   
		   $("#textContent").html(content);
		   $("#text").show();
		}else{
		//静止
		   $("#textContent2").css({
			   'color':$$("textMeta.fontColor").value,
			   'font-size':$$("textMeta.fontSize").value+"px"
		   });
		   document.getElementById("textMeta.rollSpeed").value="0";
		   document.getElementById("textMeta.rollSpeed").disabled="true";
		   
		   var content = $$("textMeta.contentMsg").value;
		   if($$("textMeta.URL").value!=''){
			content = "<a href='"+$$("textMeta.URL").value+"'>"+content+"</a>";
		   }
		      if(isEmpty($$("textMeta.positionVertexCoordinates").value)){
		   
		   }else{
					var coordinates = $$("textMeta.positionVertexCoordinates").value.split("*");
					if(coordinates.length != 2 || !isNumber(coordinates[0]) || !isNumber(coordinates[1]) || coordinates[0]>1280 || coordinates[1]>720){
						
					}
					else
					{
					   var left = coordinates[0]/1280*426+"px";
					   var bottom = coordinates[1]/720*240+"px";					 				  
					   $('#text2').css('left',left);
					   $('#text2').css('top',bottom);
					}				
	  	}
    	if(!isEmpty($$("textMeta.positionWidthHeight").value)){
					var size = $$("textMeta.positionWidthHeight").value.split("*");
					if(size.length != 2 || !isNumber(size[0]) || !isNumber(size[1])){
					
					}
			    else
			    {
			    	   var width = size[0]/1280*426+"px";
					   var height = size[1]/720*240+"px";
					   $('#text2').css('width',width);
					   $('#text2').css('height',height);
					}
		 }
		   $("#textContent2").html(content);
		   $("#text2").show();
		}
		
		
	}
	
    function aduitSuccess(){
        var reason = document.getElementById("material.examinationOpintions").value;
        var materialId = document.getElementById("material.id").value;
        
        if(reason!=null||reason!=""){
           if(reason.length>255){
			alert("审核意见必须小于255个字节！");
			$$("material.examinationOpintions").focus();
    		return true;
		   }
        }
        window.location.href="<%=path %>/page/meterial/auditMaterial.do?auditFlag=1&reason="+reason+"&materialId="+materialId;
    }
    
    function aduitFlase(){
        var reason = document.getElementById("material.examinationOpintions").value;
        var materialId = document.getElementById("material.id").value;
        if(reason==null||reason==""){
            alert("审核被拒时,审核意见不能为空!");
        }else{
            if(reason.length>255){
			alert("审核意见必须小于255个字节！");
			$$("material.examinationOpintions").focus();
    		return true;
		   }
            window.location.href="<%=path %>/page/meterial/auditMaterial.do?auditFlag=0$reason="+reason+"&materialId="+materialId;
        }
        
    }
	
	function gotoList(){
        window.location.href="<%=path %>/page/meterial/auditMaterialList.do";
    }
</script>
</head>
<body class="mainBody" onload='init(${positionJson});'>
<form action="<%=path %>/page/meterial/saveMaterialBackup.do" method="post" id="saveForm">	
<div class="path">首页 >> 素材管理 >> 审核素材</div>
<div class="searchContent" >
<div class="listDetail">
<div style="position: relative">	
<table>
		    	<tr>
		    	   <td>
		               <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px">
		                 <tr class="title"><td colspan="4">素材信息</td></tr>
		                 <tr>
		                 <!-- 
		                 <td width="15%" align="right"><span class="required">*</span>选择合同：</td>
		                     <td width="35%">		                         	                		                	
							     ${material.contractName}	
						     </td>
		                  -->
		                     
		                     <td width="15%" align="right"><span class="required">*</span>选择广告位：</td>
		                     <td width="35%" colspan="3">	                
		                         <input id="material.advertPositionId" name="material.advertPositionId" value="${material.advertPositionId}" type="hidden"  readonly="readonly"/>			                 	
				                 ${material.advertPositionName}	      
				                 
				                 <input id="material.id" name="material.id" type="hidden" value="${material.id}"/>
		                         <input id="material.createTime" name="material.createTime" type="hidden" value="${material.createTime}"/>
		                         <input id="materialType" name="materialType" type="hidden" value="${material.resourceType}"/>
		                         <input id="material.resourceTypeTemp" name="material.resourceTypeTemp" type="hidden" />	                         
		                         <input id="material.contractId" name="material.contractId" type="hidden" value="${material.contractId}"/>          
		                     </td>
		                 </tr>		           
		                 <tr>
		                     <td align="right"><span class="required">*</span>素材名称：</td>
		                     <td>
		                	     ${material.resourceName}
		                     </td>
		                     <td width="15%" align="right">素材关键字：</td>
		                     <td width="35%">
		                         ${material.keyWords}					       
		                     </td>		                
		                 </tr>
		                 <tr>
		                     <td align="right"><span class="required">*</span>素材分类：</td>
		                     <td>
		                	    <select  id="contentSort"  name="material.categoryId" disabled="disabled">
								     <option id="ad_id" value="-1">请选择...</option>
							         <c:forEach items="${materialCategoryList}" var="typeBean">
								        <option  value="${typeBean.id }" <c:if test="${material.categoryId== typeBean.id}">selected="selected"</c:if> >
								        ${typeBean.categoryName }
								        </option>
							         </c:forEach>
							    </select>
		                     </td>
		                     <td align="right"><span class="required">*</span>素材类型：</td>
		                     <td>
			              	    <select id="sel_material_type" name="material.resourceType" disabled="disabled">
							    </select>		  
		                     </td>
		                  </tr>
		                  <!-- 
		                  <tr>
		                     <td align="right"><span class="required">*</span>开始时间：</td>
		                     <td>		               
		                         <fmt:formatDate value="${material.startTime}" dateStyle="medium"/>
		                     </td>
		                     <td align="right"><span class="required">*</span>结束时间：</td>
		                     <td>
			              	     <fmt:formatDate value="${material.endTime}" dateStyle="medium"/>
		                     </td>
		                  </tr>
		                   -->
		                  
		                  <tr>
		                  <!-- 
		                  <td align="right"><span class="required"></span>素材描述：</td>
		                     <td>
		                	     <textarea disabled="disabled" id="material.resourceDesc" name="material.resourceDesc" rows="5" maxlength="100">${material.resourceDesc}</textarea>		              	
		                     </td>
		                   -->  
		                     
		                     <td width="12%" align="right">审核意见：</td>
                             <td width="33%" colspan="3">
                                 <textarea id="material.examinationOpintions" name="material.examinationOpintions" rows="5">${material.examinationOpintions}</textarea>
                                 <span id="reason_error"></span>
                             </td>
		                 </tr>
		             </table>
		          </td>
		        </tr>
		        
		        
		        <tr>
		        	<td>
		                <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display: none" id ="div_text">
		                      <tr class="title" >
		                          <td colspan="4">文字素材</td>
		                      </tr>
		                      <tr>
		                          <td  align="right"><span class="required"></span>文字标题：</td>
		                          <td>
			            		      ${textMeta.name}
		                          </td>
		                          <td  align="right"><span class="required"></span>文件URL：</td>
		                          <td><input id="textMeta.URL" type="hidden" value="${textMeta.URL}"/>
			            		      ${textMeta.URL}
		                         </td>
		                      </tr>
		                      <tr>
		                          <td  align="right"><span class="required"></span>文本显示动作：</td>
		                          <td>
			            		      <select disabled="disabled"  id="sel_textMeta_action"  name="textMeta.action" onchange="changeTextAction()">
								                <option id="action_id" value="-1">请选择...</option>
										        <option  value="0" <c:if test="${textMeta.action==0}">selected="selected"</c:if> >
										                        静止
										        </option>
										        <option  value="1" <c:if test="${textMeta.action== 1}">selected="selected"</c:if> >
										                        滚动
										        </option>
							           </select>
		                          </td>
		                          <td  align="right"><span class="required"></span>文本显示持续时间：</td>
		                          <td>
			            		      ${textMeta.durationTime}
		                         </td>
		                      </tr>
		                      <tr>
		                          <td  align="right"><span class="required"></span>文本字体大小：</td>
		                          <td><input id="textMeta.fontSize" type="hidden" value="${textMeta.fontSize}"/>
			            		      ${textMeta.fontSize}
		                          </td>
		                          <td  align="right"><span class="required"></span>文本字体颜色：</td>
		                          <td><input id="textMeta.fontColor" type="hidden" value="${textMeta.fontColor}"/>
			            		      ${textMeta.fontColor}
		                         </td>
		                      </tr>
		                      <tr>
		                          <td  align="right"><span class="required"></span>文本显示背景色：</td>
		                          <td>
			            		      ${textMeta.bkgColor}
		                          </td>
		                          <td  align="right"><span class="required"></span>文本显示滚动速度：</td>
		                          <td>
		                        <!-- <input id="textMeta.rollSpeed" type="hidden" value="${textMeta.rollSpeed}"/>
			            		      ${textMeta.rollSpeed}
			            		    -->     
			            		       <select  disabled="disabled"  id="textMeta.rollSpeed"  name="textMeta.rollSpeed" >
								                 <option  value="1" <c:if test="${textMeta.rollSpeed==1}">selected="selected"</c:if> >
										                        低速
										        </option>
										        <option  value="2" <c:if test="${textMeta.rollSpeed== 2}">selected="selected"</c:if> >
										                        中速
										        </option>
										         <option  value="3" <c:if test="${textMeta.rollSpeed== 3}">selected="selected"</c:if> >
										                        高速
										        </option>
							  </select>  
		                         </td>
		                      </tr>
		                      <tr>
		                          <td  align="right"><span class="required"></span>文本显示坐标：</td>
		                          <td>   <input type ="hidden" id="textMeta.positionVertexCoordinates" name="textMeta.positionVertexCoordinates" value="${textMeta.positionVertexCoordinates}"/>
                       				     <input  type ="hidden" id="textMeta.positionWidthHeight" name="textMeta.positionWidthHeight" value="${textMeta.positionWidthHeight}"/>
                     
			            		      ${textMeta.positionVertexCoordinates}
		                          </td>
		                          <td  align="right"><span class="required"></span>文本显示区域：</td>
		                          <td>
			            		      ${textMeta.positionWidthHeight}
		                         </td>
		                      </tr>
		                      <tr>
		            	          <td width="15%" align="right"><span class="required"></span>内容：</td>
		                          <td colspan="3">
		                	      <textarea disabled="disabled" id="textMeta.contentMsg" name="textMeta.contentMsg" maxlength="4000" cols="80" rows="5">${textMeta.contentMsg}</textarea>
		                          </td>
		                      </tr>
		                      <tr>
							      <td align="right" >素材预览效果：</td>
							      <td colspan="3">
									  <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
									     position: relative;">
											<img id="pImage1" src="<%=path%>/${adPositionQuery.backgroundPath}" width="426px" height="240px" /> 
											<div id="text"><marquee scrollamount="10" id="textContent"></marquee></div>
											<div id="text2"><span id="textContent2"></span></div>
										</div>
							      </td>
					          </tr>   
		                </table>
					
					    <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px;" id ="div_video">
		                      <tr class="title" >
		                          <td colspan="4">视频素材</td>
		                      </tr>
		                      <tr>
		            	          <td width="15%" align="right"><span class="required"></span>时长：</td>
		                          <td width="35%" colspan="3">
		                	          ${videoMeta.runTime}
		                          </td>
		                          
		                      </tr>
		                      <tr>
							      <td align="right" >素材预览效果：</td>
							      <td colspan="3">
							      	<input id="videoPath" type="hidden" value="${sssspath}"/>
								    <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
								     position: relative;">
										<img id="pImage2" src="<%=path%>/${adPositionQuery.backgroundPath}" width="426px" height="240px" />
										<div id="video">
											<object type='application/x-vlc-plugin' id='vlc'  classid='clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921' width="150" height="150">
										           <param name='mrl'  value=''/>
													<param name='volume' value='50' />
													<param name='autoplay' value='false' />
													<param name='loop' value='false' />
													<param name='fullscreen' value='false' />
										    </object>
										</div>
									</div>
							      </td>
					          </tr>
                       </table>
	            
					   <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display: none" id ="div_image">
		                     <tr class="title" ><td colspan="4">图片素材</td></tr>	
		                     <tr>
							     <td align="right" >素材预览效果：</td>
							     <td colspan="3">
								    <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
								     position: relative;">
										<img id="pImage3" src="<%=path%>/${adPositionQuery.backgroundPath}" width="426px" height="240px" /> 
										<img id="mImage" src="${viewPath}/${imageMeta.name}" />
									</div>
							     </td>						
					         </tr>
		               </table>
		               
		               
		                <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display: none" id ="div_zip">
		                     <tr class="title" ><td colspan="4">ZIP素材</td></tr>	
		                     <tr>
							     <td align="right" >素材预览效果：</td>
							     <td colspan="3">
								    <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:426px;height:240px;
								     position: relative;">
										<img id="pImage4" src="<%=path%>/${adPositionQuery.backgroundPath}" width="426px" height="240px" /> 
										<img id="mImage4" src="${viewPath}/${zipMeta.fileHeigth}" />
									</div>
							     </td>						
					         </tr>
		               </table>
		          
		          <table cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display: none" id ="div_question">
		            <tr class="title" >
		                <td colspan="4">调查问卷</td>
		            </tr>		            
		            <tr>		            	
		                <td align="right"><span class="required"></span>问卷类型：</td>
		                <td>
			              	    <select id="sel_question_type"  name="questionSubject.questionnaireType">
								     <option id="ad_id" value="-1">请选择...</option>
								            <c:forEach items="${questionTypeList}" var="queBean">
									           <option  value="${queBean.id }" <c:if test="${questionSubject.questionnaireType== queBean.id}">selected="selected"</c:if> >${queBean.typeName }</option>
								            </c:forEach>
							    </select>		  
		                </td>		            	
		                <td align="right"><span class="required"></span>模板选择：</td>
		                <td>
			              	    <select id="sel_template_type" onclick="selectTemplate(this.value)" ">
								     <option id="ad_id" value="-1">请选择...</option>
								            <c:forEach items="${templateList}" var="templateBean">
									           <option  value="${templateBean.id }" <c:if test="${questionSubject.templateId== templateBean.id}">selected="selected"</c:if> >${templateBean.templateName }</option>
								            </c:forEach>
							    </select>		  
		                </td>
		            </tr>
		            
		            <tr>
		            	<td colspan="4">
					        <table  cellspacing="1" class="content" align="left" style="margin-bottom: 0px; display: none" id ="div_question2">
		                          <tr>
		            	              <td width="15%" align="right"><span class="required"></span>问卷主题：</td>
		                              <td width="35%">
		                	              <textarea id="questionSubject.summary" name="questionSubject.summary" cols="30" rows="3" maxlength="100" disabled="disabled">${questionSubject.summary}</textarea>
		                              </td>
		                          </tr>
		                          <tr>
		            	              <td width="15%" align="right"><span class="required"></span>新增问题：</td>
		                              <td width="35%">
		                	              <input type="button"  value="新增问题" onclick="addQuestion()" disabled="disabled"/>
		                              </td>
		                          </tr>
		                  <c:if test="${questionInfoList != null && fn:length(questionInfoList) > 0}">
                          <c:forEach items="${questionInfoList}" var="questionInfo" varStatus="pl">
                          <tr>
		                      <td width="15%" align="right"><span class="required"></span>问题内容：</td>
		                      <td width="35%">
		                	      <textarea  cols="30" rows="3" maxlength="100" disabled="disabled">${questionInfo.question}</textarea>
		                      </td>
		                  </tr>		                   
		                  <tr>
		                      <td width="15%" align="right"><span class="required"></span>问题答案：</td>
		                      <td width="35%">
		                      <c:if test="${questionInfo.answerList != null && fn:length(questionInfo.answerList) > 0}">
                              <c:forEach items="${questionInfo.answerList}" var="answerInfo" varStatus="al">
		                	              ${al.index+1} :<input disabled="disabled" type='text' id='answer1' name='answer1' value="${answerInfo.options}"/></p>
		                	              
		                	  </c:forEach>
                             </c:if>            
		                      </td>
		                  </tr>
		                     
		                  </c:forEach>
                          </c:if>
					        </table>
					     </td>
					</tr>
		            
		          </table>
		          
		        </td>
		      </tr>			  

</table>
<div align="center" class="action">
        <input type="button" class="btn" value="通 过" onclick="aduitSuccess();"/>&nbsp;
        <input type="button" class="btn" value="驳 回" onclick="aduitFlase();"/>&nbsp; 
        <input type="button" class="btn" value="返 回" onclick="gotoList();"/>
</div>
</div>
</div>
</div>



</form>
</body>