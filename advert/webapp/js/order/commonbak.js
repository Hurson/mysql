/** 高清屏幕宽度 */
var hdWidth = 1024;

/** 高清屏幕高度 */
var hdHeight = 742;

/** 标清屏幕宽度 */
var bdWidth = 950;

/** 标清屏幕高度 */
var bdHeight = 700;

/**预览图x坐标*/
var preX=644;

/**新增修改页面预览图Y坐标*/
var preY=213;

/**修改结束时间页面预览图Y坐标*/
var preY1=199;

/**执行完毕订单页面预览图Y坐标*/
var preY2=194;

/** 预览比例 */
var scale = 0.4;

var aheadTime=0;


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

/**
 * 根据高标清编码返回屏幕预览宽度
 * */
function getHDWidth(isHD) {
	switch (isHD) {
	case 0:
		return bdWidth * scale;
	case 1:
		return hdWidth * scale;
	}
}

/**
 * 根据高标清编码返回屏幕预览高度
 * */
function getHDHeight(isHD) {
	switch (isHD) {
	case 0:
		return bdHeight * scale;
	case 1:
		return hdHeight * scale;
	}
}

function getPreciseType(type){
	switch(type){
	case 1:
		return "按产品";
	case 2:
		return "按影片元数据关键字";
	case 3:
		return "按受众";
	case 4:
		return "按影片分类";
	case 5:
		return "按回看频道";
	}
}
function showMaterialPre(path,content,type){
	if(type!=null){
		switch(type){
		case 0:showImage(path);break;
		case 1:showVideo(path);break;
		case 2:showText(content);break;
		}
	}
}
/**显示图片*/
function showImage(path){
	$("#mImage").attr("src",getContextPath()+"/"+path);
	$("#mImage").show();
	$("#video").hide();
	videoFlag=0;
}
/**显示视频*/
function showVideo(path){
	$("#temp").hide();
	$("#temp").remove();
	$("#video").html('<embed id="temp" width="'+width+'" height="'+height+'" version="VideoLAN.VLCPlugin.2" pluginspage="http://www.videolan.org" type="application/x-vlc-plugin" src="'+getContextPath()+"/"+path+'"/>');
	$("#video").show();
	videoFlag=1;
	$("#mImage").hide();
}
/**显示文字*/
function showText(content){
	$("#text").show();
	$("#textContent").html(content);
}

/**
 * 弹出策略选择层
 */
function showSelectDiv(divId){
	$('#'+divId).show();
	$('#bg').show();
	$('#popIframe').show();
	$("#video").hide();
}
/**
 * 关闭策略选择层
 */
function closeSelectDiv(divId){
	$('#'+divId).hide();
	$('#bg').hide();
	$('#popIframe').hide();
	if(videoFlag==1){
		$("#video").show();
	}	
}
/**
 * 查看绑定策略详情
 * */
function showPloyInfo(){
	var title = "<td>策略名称</td><td>开始时段</td><td>结束时段</td><td>关联区域</td><td>关联频道</td>";
	$("#ployInfoTitle").html(title);
	var content = "<tr><td>";
	content +=selPloy.name;
	content +="</td><td>";
	content += selPloy.startTime;
	content +="</td><td>";
	content += selPloy.endTime;
	content +="</td>";
	content +=fillAreaChannelInfo(selPloy.areas);
	content +="</tr>";		
	$("#ployInfoContent").html(content);
	$("#ployInfoName").html("策略");
	$("#plBtn").show();
	$("#preBtn").hide();
	showSelectDiv("ployInfoDiv");
}

/**
 * 查看绑定精准详情
 * */
function showPreciseInfo(i){	
	var title = "<td>精准名称</td><td>类型</td><td>产品</td><td>频道</td><td>影片关键字</td><td>用户区域</td>" +
			"<td>行业类别</td><td>用户级别</td><td>TVN号段</td><td>优先级</td><td>节点</td>";
	$("#ployInfoTitle").html(title);
	var content="<tr><td>";
	content +=precises[i].name;
	content +="</td><td>";
	content += getPreciseType(precises[i].type);
	content +="</td><td>";
	content += precises[i].products;
	content +="</td><td>";
	content +=precises[i].channels;
	content +="</td><td>";
	content += precises[i].key;
	content +="</td><td>";
	content += precises[i].userArea;
	content +="</td><td>";
	content +=precises[i].userIndustrys;
	content +="</td><td>";
	content += precises[i].userLevels;
	content +="</td><td>";
	content += precises[i].tvnNumber;
	content +="</td><td>";
	content +=precises[i].priority;
	content +="</td><td>";
	content += precises[i].categorys;
	content +="</td></tr>";	
	$("#ployInfoContent").html(content);
	$("#ployInfoName").html("精准");
	$("#plBtn").hide();
	$("#preBtn").show();
	showSelectDiv("ployInfoDiv");
}
/**
 * 填充策略区域频道内容
 * */
function fillAreaChannelInfo(areas){
	str = "<td><table>";
	for(var i = 0;i<areas.length;i++){
		str +="<tr><td>";
		str +=areas[i].name;
		str +="</td></tr>";
	}
	str += "</table></td><td><table>";
	for(var j = 0;j<areas.length;j++){
		str +="<tr><td>";
		for(var k= 0;k<areas[j].child.length;k++){
			if(k>0){
				str += ",";
			}
			str += areas[j].child[k].name;
		}
		str +="</td></tr>";
	}
	str +="</table></td>";
	return str;
}