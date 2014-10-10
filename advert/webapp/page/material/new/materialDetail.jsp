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
				showVideo($$('videoPath').value);
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
			    //alert("调查问卷："+materialType);
			    //document.getElementById('tt').style.display = "";
				//document.getElementById('div_text').style.display = "none";      
				document.getElementById('div_video').style.display = "none";      
				//document.getElementById('div_image').style.display = "none";
				//document.getElementById('div_question').style.display = "";
				//document.getElementById('div_question2').style.display = "none";
				//document.getElementById('sel_template_type').disabled=true;
				//document.getElementById('sel_question_type').disabled=true;
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
		$("#pImage").attr("width",396).attr("height",288);
		$("#pImage").attr("src",getContextPath()+"/"+selPosition.backgroundPath);
		$("#mImage").attr("width",width).attr("height",height);
		$("#mImage,#video").css({
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
		$("#textContent").html(content);
		$("#text").show();
	}
	
    function aduitSuccess(){
        var reason = document.getElementById("material.examinationOpintions").value;
        var materialId = document.getElementById("material.id").value;
        window.location.href="<%=path %>/page/meterial/auditMaterial.do?auditFlag=1&reason="+reason+"&materialId="+materialId;
    }
    
    function aduitFlase(){
        var reason = document.getElementById("material.examinationOpintions").value;
        var materialId = document.getElementById("material.id").value;
        if(reason==null||reason==""){
            alert("审核被拒时,审核意见不能为空!");
        }else{
            window.location.href="<%=path %>/page/meterial/auditMaterial.do?auditFlag=0$reason="+reason+"&materialId="+materialId;
        }
        
    }
	
	function gotoList(){
        window.location.href="<%=path %>/page/meterial/queryUponLineList.do";
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
		                     <td width="15%" align="right"><span class="required">*</span>选择合同：</td>
		                     <td width="35%">	
		                         <input id="material.id" name="material.id" type="hidden" value="${material.id}"/>
		                         <input id="material.createTime" name="material.createTime" type="hidden" value="${material.createTime}"/>
		                         <input id="materialType" name="materialType" type="hidden" value="${material.resourceType}"/>
		                         <input id="material.resourceTypeTemp" name="material.resourceTypeTemp" type="hidden" />
		                         
		                         <input id="material.contractId" name="material.contractId" type="hidden" value="${material.contractId}"/>	                		                	
							     ${material.contractName}	
						     </td>
		                     <td width="15%" align="right"><span class="required">*</span>选择广告位：</td>
		                     <td width="35%">	                
		                         <input id="material.advertPositionId" name="material.advertPositionId" value="${material.advertPositionId}" type="hidden"  readonly="readonly"/>			                 	
				                 ${material.advertPositionName}	                
		                     </td>
		                 </tr>		           
		                 <tr>
		                     <td align="right"><span class="required">*</span>素材名称：</td>
		                     <td>
		                	     ${material.resourceName}
		                     </td>
		                     <td width="15%" align="right"><span class="required">*</span>素材关键字：</td>
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
		                  <tr>
		                     <td width="15%" align="right"><span class="required"></span>素材描述：</td>
		                     <td colspan="3"  width="85%" >
		                	     <textarea disabled="disabled" id="material.resourceDesc" name="material.resourceDesc" rows="5"  cols="80" >${material.resourceDesc}</textarea>		              	
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
		                          <td  align="right">文本显示动作：</td>
		                          <td>
			            		      ${textMeta.action}
		                          </td>
		                          <td  align="right">文本显示持续时间：</td>
		                          <td>
			            		      ${textMeta.durationTime}
		                         </td>
		                      </tr>
		                      <tr>
		                          <td  align="right">文本字体大小：</td>
		                          <td><input id="textMeta.fontSize" type="hidden" value="${textMeta.fontSize}"/>
			            		      ${textMeta.fontSize}
		                          </td>
		                          <td  align="right">文本字体颜色：</td>
		                          <td><input id="textMeta.fontColor" type="hidden" value="${textMeta.fontColor}"/>
			            		      ${textMeta.fontColor}
		                         </td>
		                      </tr>
		                      <tr>
		                          <td  align="right"><span class="required"></span>文本显示背景色：</td>
		                          <td>
			            		      ${textMeta.bkgColor}
		                          </td>
		                          <td  align="right">文本显示滚动速度：</td>
		                          <td><input id="textMeta.rollSpeed" type="hidden" value="${textMeta.rollSpeed}"/>
			            		      ${textMeta.rollSpeed}
		                         </td>
		                      </tr>
		                      <tr>
		                          <td  align="right">文本显示坐标：</td>
		                          <td>
			            		      ${textMeta.positionVertexCoordinates}
		                          </td>
		                          <td  align="right">文本显示区域：</td>
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
									  <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:396px;height:288px;
									     position: relative;">
											<img id="pImage" src="<%=path%>/images/position/position.jpg" width="396px" height="288px" /> 
											<div id="text"><marquee scrollamount="10" id="textContent"></marquee></div>
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
								    <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:396px;height:288px;
								     position: relative;">
										<img id="pImage" src="<%=path%>/images/position/position.jpg" width="396px" height="288px" />
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
								    <div style="margin-left:0px;margin-right:0px;background-repeat:no-repeat; width:396px;height:288px;
								     position: relative;">
										<img id="pImage" src="<%=path%>/images/position/position.jpg" width="396px" height="288px" /> 
										<img id="mImage" src="${viewPath}/${imageMeta.name}" />
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
		                	              <textarea id="questionSubject.summary" name="questionSubject.summary" cols="30" rows="3" maxlength="100"></textarea>
		                              </td>
		                          </tr>
		                          <tr>
		            	              <td width="15%" align="right"><span class="required"></span>新增问题：</td>
		                              <td width="35%">
		                	              <input type="button"  value="新增问题" onclick="addQuestion()"/>
		                              </td>
		                          </tr>
					        </table>
					     </td>
					</tr>
					
		            
		          </table>
		          
		        </td>
		      </tr>			  
			<tr>
				<td align="center">
					<input type="button" class="btn" value="返 回" onclick="gotoList();"/>
				</td>
			</tr>
</table>
<div align="center" class="action">
        
</div>
</div>
</div>
</div>



</form>
</body>