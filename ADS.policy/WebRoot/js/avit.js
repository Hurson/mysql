
function loadJsFile(jsFile){
	var script = document.createElement("script");

	script.src = jsFile;  

	script.setAttribute("type", "text/javascript");

	var _complete = function(){
	
		 if(!script.readyState || script.readyState == "loaded" || script.readyState == "complete"){
		
		   //alert("script load complete");
			jQuery.noConflict();
		 }
	
	}

	if(/msie/i.test(window.navigator.userAgent)){
	
	  script.onreadystatechange = _complete;
	
	} else {
	
	  script.onload = _complete;
	
	}
	var s = document.getElementsByTagName('script')[0];    
	s.parentNode.insertBefore(script, s);
}

//loadJsFile("/iscg/js/jquery-1.6.2.min.js");
/**
 * 选择页码跳转到指定页
 * @param id
 * @param length
 * @param index
 */
function addOptionToSelect(id, length, index) {
    var select = document.getElementById(id);
    for (var i = 0; i < length; i++) {
        select.options.add(new Option(i + 1, i + 1));
        if (index - 1 == i) {
            select[index - 1].selected = true;
        }
    }
}

/**
 * 选择页码跳转到指定页
 * @param id
 * @param length
 * @param index
 */
function addOptionToSelect2(id, length, index) {
	if(!$(id)) return;
    var k = [];
    k.push("<select id='pageNumber2' onchange='gotoSelectedPage(this.value);' style='width:55px'>");
    for (var i = 1; i < length + 1; i++) {
        if (index == i) {
            k.push("<option selected='selected' value='" + i + "'>" + i + "</option>");
        } else {
            k.push("<option value='" + i + "'>" + i + "</option>");
        }
    }
    k.push("</select>/" + length);
    $(id).innerHTML = k.join("");
}

/**
 * 获取字符串的字节数
 * 用法：  var str = "获取字符串的字节数";  var number = str.getBytesLength(); 这里得到number 为 18
 */
String.prototype.getBytesLength = function() {
    return this.replace(/[^\x00-\xff]/gi, "--").length;
}

/**
 * td 的内容过长的处理
 * @param name
 */
function toDoTDcontentLengthOver(name) {
    var objs = document.getElementsByTagName(name);
    for (var i = 0; i < objs.length; i++) {
        var str = objs[i].innerHTML;
        var length = str.length;
        if (length > 25) {
            objs[i].innerHTML = str.substring(0, 15) + "......";
        }
    }
}

/**
 * 控制文本框中特殊字符的输入(屏蔽不必要的键)。用法：onkeypress="return validateSpecialCharacter();"
 */
function validateSpecialCharacter() {
    var code;
    if (document.all) { //判断是否是IE浏览器
        code = window.event.keyCode;
    } else {
        code = arguments.callee.caller.arguments[0].which;
    }
    var character = String.fromCharCode(code);
    var txt = new RegExp("[ ,\\`,\\~,\\!,\\#,\\$,\\%,\\^,\\+,\\*,\\&,\\\\,\\/,\\?,\\|,\\:,\\<,\\>,\\{,\\},\\(,\\),\\'',\\;,\\=,\"]");
    //特殊字符正则表达式
    if (txt.test(character)) {
        if (document.all) {
            window.event.returnValue = false;
        } else {
            arguments.callee.caller.arguments[0].preventDefault();
        }
    }
}

/**
 * 提交的时候对value进行验证,value中存在特殊字符就返回true,没有特殊字符返回false
 * @param value
 */
function validateSpecialCharacterAfter(value) {
    for (var i = 0; i < value.length; i++) {
        var character = value.charAt(i);
        var txt = new RegExp("[ ,\\`,\\~,\\!,\\#,\\$,\\%,\\^,\\+,\\*,\\&,\\\\,\\/,\\?,\\|,\\:,\\<,\\>,\\{,\\},\\(,\\),\\'',\\;,\\=,\"]");
        //特殊字符正则表达式
        if (txt.test(character)) {
            return true;
        }
    }
    return false;
}

function validateSpecialCharacterUrl(value) {
    for (var i = 0; i < value.length; i++) {
        var character = value.charAt(i);
        var txt = new RegExp("[ ,\\`,\\~,\\!,\\#,\\$,\\%,\\^,\\+,\\*,\\&,\\\\,\\?,\\|,\\<,\\>,\\{,\\},\\(,\\),\\'',\\;,\\=,\"]");
        //特殊字符正则表达式
        if (txt.test(character)) {
            return true;
        }
    }
    return false;
}

/**
 * 验证中文字符和特殊字符
 * @param value  值
 */
function chineseVaildate(value) {
    if (value == null || value == "")
        return true;
    if ((/[\u4E00-\u9FA5]+/.test(value))) {
        return false;
    }
    return true;
}

/**
 * 验证中文字符和特殊字符  用法：onblur="validate(this)"
 * @param obj
 */
function validate(obj) {
    if (!chineseVaildate(obj.value)) {
        alert("有特殊字符和中文字符");
    }
}

/**
 * 清空表单除"查询","重置"的所有表单元素 的值  add by hemeijin 20110829
 * @param formId  重置按钮所在的表单的ID
 */
function resetAll2(formId) {
    var form = $(formId);
    for (var i = 0; i < form.elements.length; i++) {
        // if(form.elements[i].type != "button" && form.elements[i].type != "submit" && form.elements[i].type != "reset"){
        if (form.elements[i].type == "text" || form.elements[i].type == "radio" || form.elements[i].type == "select-one" || form.elements[i].type == "hidden") {
            if (form.elements[i].type == "radio") {
                form.elements[i].checked = false;
            } else {
                form.elements[i].value = "";
            }
        }
    }
}
function resetAll(formId) {
	if( $(formId)){
		jQuery("table").eq(0).find("input:text,input:radio,select").each(function(i, obj){
			jQuery(obj).val("");
		});
		// $(formId).reset();
	}
}
/**
 *  效验ip地址是否合法 正确的IP地址是   1-223.0-255.0-255.0-255
 * @param str
 */
function isIP(value)
{
	var reg = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
	return regValidate(reg, value);
}
function isIP(str) {
    var ip = /^([1-9]|[1-9]\d|1\d{2}|2[0-1]\d|22[0-3])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}$/;
    return   ip.test(str);
}


//编码格式：只能输入数字和英文，下划线“_"
function isCode(value)
{
	var reg = /^[0-9A-Za-z_]+$/;
	return regValidate(reg, value);
}

//使用正则表达式验证字符串
//reg: 正则表达式
//value: 要验证的字符串
function regValidate(reg, value)
{
	if (reg.test(value))
	{
		return true;
	}
	else {
		return false;
	}
}


/**
 * 验证只能输入整数数字
 * @param value
 */
function isIntegerNumber(value) {
    var patrn = /^[0-9]+$/;
    if (patrn.test(value)) {
        return true;
    } else {
        return false;
    }
}

/**
 * 效验text是否为数字
 * @param text
 */
function isNumber(text) {
    var reNumber = /\d*/;
    if (reNumber.test(text)) {
        return true;
    } else {
        return false;
    }
}

//可以为小数
function isRealNumber(text) {
    !isEmpty(text);
    this.number = /^[0-9]+(.[0-9]{0,3})?$/;
    return this.number.test(text);
}

/**
 * 获取页面元素
 * @param id
 */
function $(id) {
    return document.getElementById(id);
}

/**
 * 隐藏页面元素
 * @param id  元素的id
 */
function HideDataField(id) {
    if (document.getElementById(id) != null) {
        document.getElementById(id).style.display = 'none';
    }
}

/**
 * 显示页面元素
 * @param id  元素的id
 */
function ShowDataField(id) {
    if (document.getElementById(id) != null) {
        document.getElementById(id).style.display = '';
    }
}
/**
 * 页面跳转
 * @param url
 */
function gotoPage(url) {
    window.location.href = url;
}

/**
 * 初始化radio
 * @param name
 */
function initRadio(name) {
    var opt = document.getElementsByName(name);
    for (var i = 0; i < opt.length; i++) {
        opt[i].checked = '';
    }
}

/**
 * 是否是中文
 * @param string
 */
function isChinse(string) {
    // var reString = /^[^a-zA-Z0-9]/;/^[0-9\u4e00-\u9faf]+$/ /[^x00-xff]/g
    var reString = /^[\u4E00-\u9FA5]+$/;
    return reString.test(string);
}
function isUnicodeFor(string) {
    var reString = /^[^a-zA-Z0-9_@;:'"=+-`!#$%^&*(,<.>?)]/;
    return reString.test(string);
}
function trim(sInString) {
    if (sInString != null)
        sInString = sInString.replace(/^\s+/g, "").replace(/\s+$/g, "");// strip
    // leading
    return sInString;
}

/**
 * 是否为空
 * @param elementValue
 */
function isEmpty(elementValue) {
    if (elementValue == null || trim(elementValue) == "") {
        return true;
    } else {
        return false;
    }
}

/**
 * 时间格式化
 * @param str
 */
function timeFormatForKfit(str) {
    var array = new Array();
    for (var i = 0; i < 6; i += 2) {
        array.push(str.substring(i, i + 2));
    }
    return array.join(":");
}

/**
 * 根据标识判断 是否显示或隐藏元素
 * @param infoId
 * @param flag
 */
function showInfo(infoId, flag) {
    if (flag) {
        $(infoId).style.display = '';
    } else {
        $(infoId).style.display = 'none';
    }
}

/**
 * checkbox的全选操作
 * @param id
 */
function selectAll(name)
{
    var idss = document.getElementsByName(name);
    var haveNoSelected = false;
    if (idss.length == 1) {
        if (!idss[0].checked)
            idss[0].checked = true;
        else
            idss[0].checked = false;
    } else if (idss.length > 1)
    {
        for (var i = 0; i < idss.length; i++) {
            if (!idss[i].checked) {
                haveNoSelected = true;
                break;
            }
        }
        for (i = 0; i < idss.length; i++)
            idss[i].checked = haveNoSelected;
    }
}

/**
 *  获取checkbox 选中的个数
 * @param name
 */
function getCheckCount(name) {
    var cbg = document.getElementsByName(name);
    var retCnt = 0;
    var len = cbg.length;
    for (var k = 0; k < len; k++) {
        if (cbg[k] && (cbg[k].type) && (cbg[k].type == 'checkbox')
                && cbg[k].checked) {
            retCnt++;
        }
    }
    return retCnt;
}

/**
 *  获取checkbox 选中的value
 * @param name
 */
function getCheckedValue(name) {
    var cbg = document.getElementsByName(name);
    var retCnt = 0;
    var len = cbg.length;
    for (var k = 0; k < len; k++) {
        if (cbg[k] && (cbg[k].type) && (cbg[k].type == 'checkbox')
                && cbg[k].checked) {
            return cbg[k].value;
        }
    }
    return "";
}

/**
 * 清空select框的值
 * @param select
 */
function clearSelect(select) {
    select.innerHTML = '';
    select.options.add(new Option('**请选择**', ''))
}

/**
 * 日历界面Calendar.htm中调用的方法，显示日历
 * @param id
 * @param dateFormat
 * @param isLeft
 */
function showCalendar(id, dateFormat, isLeft) {
    var calendarFrame = document.getElementById('calendarFrame');
    if (isLeft) {
        calendarFrame.style.left = (document.getElementById(id).parentNode
                .getElementsByTagName('div')[0].offsetLeft + 23) + 'px';
        calendarFrame.style.top = (document.getElementById(id).parentNode
                .getElementsByTagName('div')[0].offsetTop - 166) + 'px';
    } else {
        calendarFrame.style.left = (getLeft(document.getElementById(id))) + 'px';
        calendarFrame.style.top = (getTop(document.getElementById(id)) + 23) + 'px';
    }
    calendarFrame.style.visibility = 'visible';
    function getTop(e) {
        var offset = e.offsetTop;
        if (e.offsetParent != null)
            offset += getTop(e.offsetParent);
        return offset;
    }
    function getLeft(e) {
        var offset = e.offsetLeft;
        if (e.offsetParent != null)
            offset += getLeft(e.offsetParent);
        return offset;
    }
}

/**
 * 获取今天的日期 yyyymmdd
 */
function getToday() {
    var today = new Date();
    return format('yyyymmdd', today.getFullYear(), today.getMonth() + 1, today
            .getDate());

    function format(f, y, m, d) {
        var ds = replace(f, "yyyy", y);
        ds = replace(ds, "mm", (100 + m).toString().substr(1));
        ds = replace(ds, "dd", (100 + d).toString().substr(1));
        return ds + '';
    }
    function replace(str, o, n) {
        var pos = str.indexOf(o);
        if (pos == -1)
            return str;
        return str.substr(0, pos) + n + str.substr(pos + o.length);
    }
}

/**
 * 获取昨天的日期 yyyymmdd
 */
function getBeforeDay() {
    var today = new Date();
    return format('yyyymmdd', today.getFullYear(), today.getMonth() + 1, today
            .getDate() + 1);

    function format(f, y, m, d) {
        var ds = replace(f, "yyyy", y);
        ds = replace(ds, "mm", (100 + m).toString().substr(1));
        ds = replace(ds, "dd", (100 + d).toString().substr(1));
        return ds + '';
    }
    function replace(str, o, n) {
        var pos = str.indexOf(o);
        if (pos == -1)
            return str;
        return str.substr(0, pos) + n + str.substr(pos + o.length);
    }
}

/**
 * 获取今天前7天得日期 yyyymmdd
 */
function getPrev7Days() {
    var today = new Date();

    var y = today.getFullYear();
    var m = today.getMonth() + 1;

    var d = today.getDate();
    if (d > 7) {
        d = d - 7;
    } else {
        var prevMonth = getRelativeMonth(new Month(y, m), -1);
        if (m <= 1) {
            y = prevMonth.year;
            m = prevMonth.month;
            d = prevMonth.length - (7 - today.getDate());
        } else {
            m = prevMonth.month;
            d = prevMonth.length - (7 - today.getDate());
        }
    }

    return format('yyyymmdd', y, m, d);

    function getRelativeMonth(mth, n) { // n must be -12 to +12
        var m = mth.month + n;
        var y = mth.year;

        if (m <= 0) {
            m += 12;
            y--;
        } else if (m > 12) {
            m -= 12;
            y++;
        }
        return new Month(y, m);
    }

    function format(f, y, m, d) {
        var ds = replace(f, "yyyy", y);
        ds = replace(ds, "mm", (100 + m).toString().substr(1));
        ds = replace(ds, "dd", (100 + d).toString().substr(1));
        return ds + '';
    }
    function replace(str, o, n) {
        var pos = str.indexOf(o);
        if (pos == -1)
            return str;
        return str.substr(0, pos) + n + str.substr(pos + o.length);
    }

    function Month(y, m) {
        var months = new Array("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月",
                "9月", "10月", "11月", "12月");
        var numDays = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
        // properties
        this.year = y - 0;
        // minus 0 to convert it to number
        this.month = m - 0;
        this.name = months[m - 1];
        this.length = numDays[m - 1];
        if ((m == 2) && ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0))
            this.length = 29;

        var d = new Date(this.year, this.month - 1, 1);
        this.firstDay = d.getDay();
    }
}
/**
 *  验证起始日期不能比截止日期大    如果大 返回false
 * @param begindateId 起始日期的ID
 * @param enddateId  截止日期的ID
 */
function validatQueryDate(begindateId, enddateId) {
    if ((dateCompare($(enddateId).value, $(begindateId).value)) > 0)
    {
        return false;
    }
    return true;
}

// 2个日期之间比较
function dateCompare(date1, date2) {
    var time;
    // 默认格式为"20030303",根据自己需要改格式和方法
    var year1 = date1.substr(0, 4);
    var year2 = date2.substr(0, 4);
    var month1 = date1.substr(4, 2);
    var month2 = date2.substr(4, 2);

    var day1 = date1.substr(6, 2);
    var day2 = date2.substr(6, 2);

    var temp1 = year1 + "/" + month1 + "/" + day1;
    var temp2 = year2 + "/" + month2 + "/" + day2;

    var dateaa = new Date(temp1);
    var datebb = new Date(temp2);
    var date = datebb.getTime() - dateaa.getTime();
    return time = Math.floor(date / (1000 * 60 * 60 * 24));
}

//设置焦点进入和离开元素时的背景色
var fieldBackColor;
 var elements = new Array();
function setFocusStyle(formId){
	/**
	var a = document.all;
	for(var i=0; i<a.length;i++){
		if(((a[i].tagName=="input"||a[i].tagName=="INPUT")&&(a[i].type=="text"||a[i].type=="checkbox"||a[i].type=="radio")&&(a[i].disabled!="disabled"))
				||(a[i].tagName=="select"||a[i].tagName=="SELECT")
				||(a[i].tagName=="textarea"||a[i].tagName=="TEXTAREA")
		) {
			elements.push(a[i]);
			a[i].onfocus= setFieldFocus;
			a[i].onblur= clearFieldFocus;
		}
	}
	*/
}

function setFieldFocus(){
	fieldBackColor = this.style.backgroundColor;
	this.style.backgroundColor="#fffa94";
}

function clearFieldFocus(){
	this.style.backgroundColor=fieldBackColor;
}

//隐藏操作后的提示消息
function clearMessage(){
	if($("message_in_phase")){
		//$("message_in_phase").style.display="none";
	}
	
}

function startTimer(){
	if($("message_in_phase")) {
		setTimeout("clearMessage()", 3000);
	}
}

var count = 3;
var intInterval;
function countTime(){
	if(count==0){
		window.clearInterval(intInterval);
		$("message_in_phase").style.display="none";
	}else{
		count--;
		var h = $("message_in_phase").style.height;
		var intH = h.substr(0,h.indexOf("px"));
		alert(intH);
		if(intH <10){
			$("message_in_phase").style.height = h;
		} else
		$("message_in_phase").style.height=(intH-10)+"px";
	}
}

//列表全选
function selectAll(checkBoxObject, elementName){
    var idss = document.getElementsByName(elementName);
    var ifChecked = checkBoxObject.checked;
    for(i = 0; i < idss.length; i++){
    	
    	if(idss[i].disabled!=true){
    		idss[i].checked = ifChecked;
    	}
        
    }
}

//弹出窗口
function showDiag(actinUrl,height,width) {
	if(!height){
		height = 450;
	}
	if(!width){
		width=650;
	}
    var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px")
	if(modelWin){
		if($("searchForm").action.indexOf("?")<0) {
		 $("searchForm").action+="?message="+modelWin;
		} else {
			 $("searchForm").action+="&message="+modelWin;
		}
		$("searchForm").submit();
	} 

}

function showDate(id){
	$(id).click();
}

//获取窗口大小
function getSize() {
        var a, b;
        var browserSize = new Array;

        if (window.innerWidth)
        {
            a = window.innerWidth;
            b = window.innerHeight;
        }
        else if (document.compatMode == 'CSS1Compat')
        {
            a = document.documentElement.clientWidth;
            b = document.documentElement.clientHeight + document.documentElement.scrollTop;
        }
        else if (document.body)
            {
                a = document.body.clientWidth;
                b = document.body.clientHeight;
            }
        browserSize = {
            width:a,height:b
        };
        return browserSize;
}

//汉字
function isChinese(value)
{
	var reg = /^[\u0391-\uFFE5]+$/;
	return regValidate(reg, value);
}

//使用正则表达式验证字符串
//reg: 正则表达式
//value: 要验证的字符串
function regValidate(reg, value)
{
	if (reg.test(value))
	{
		return true;
	}
	else {
		return false;
	}
}

//检查字符串的长度
function isStrLengthLong(value, length)
{
	if (value != "" && value != null)
	{
		var len = 0;
		for(var i=0; i<value.length; i++)
		{
			if (isChinese(value.charAt(i)))
			{
				len += 2;
			} else {
				len += 1;
			}
		}
		if (len > length)
		{
			return true;
		}
	}
	return false;
}

//js日期比较(yyyy-mm-dd)

 function cmpDate(a, b) {
    var arr = a.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();

    var arrs = b.split("-");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();

    if (starttimes >= lktimes) {

        return false;
    }
    else
        return true;

}
 
 //js时间比较(yyyy-mm-dd hh:mi:ss)
function cmpDatetime(d1,d2) {
    var beginTime = d1;
    var endTime = d2;
    var beginTimes = beginTime.substring(0, 10).split('-');
    var endTimes = endTime.substring(0, 10).split('-');

    beginTime = beginTimes[1] + '-' + beginTimes[2] + '-' + beginTimes[0] + ' ' + beginTime.substring(10, 19);
    endTime = endTimes[1] + '-' + endTimes[2] + '-' + endTimes[0] + ' ' + endTime.substring(10, 19);

    var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
    
	return a;
}
function disabledSoureElement(){
	if(event){
		var src=event.srcElement;
	
		if(src){
			src.disabled="disabled";
			src.onclick=function(){return false;};
		}
	}
}
window.onload = function() {
	if($("message_in_phase")) {
		setTimeout("clearMessage()", 3000);
	}
	//setFocusStyle("searchForm");
}
