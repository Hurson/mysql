function ajaxGet(url, callback){
	var xmlHttp = createXMLHttpRequest();
	xmlHttp.open("GET",url, true);
    xmlHttp.onreadystatechange = function(){
    	if(xmlHttp.readyState == 4) {
          if(xmlHttp.status == 200) {
            callback(xmlHttp.responseText);
          }
        }
    };
    xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
    xmlHttp.send(null);
}


function ajaxPost(url, params, callback){
	var xmlHttp = createXMLHttpRequest();
	xmlHttp.open("POST",url, true);
    xmlHttp.onreadystatechange = function(){
    	if(xmlHttp.readyState == 4) {
          if(xmlHttp.status == 200) {
            callback(xmlHttp.responseText);
          }
        }
    };
    xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
    xmlHttp.send(params);
}

function createXMLHttpRequest() {
   var xmlHttp = null;
   if (window.ActiveXObject) {
       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
   }
   else if (window.XMLHttpRequest) {
       xmlHttp = new XMLHttpRequest();
   }
   return xmlHttp;
}
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
    var k = [];
    k.push("<select id='pageNumber2' onchange='gotoSelectedPage(this.value);' style='width:50px'>");
    for (var i = 1; i < length + 1; i++) {
        if (index == i) {
            k.push("<option selected='selected' value='" + i + "'>" + i + "</option>");
        } else {
            k.push("<option value='" + i + "'>" + i + "</option>");
        }
    }
    k.push("</select>");
    //  k.push("</select>/" + length);
    document.getElementById(id).innerHTML = k.join("");
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
    var txt = new RegExp("[ ,\\`,\\~,\\!,\\#,\\$$,\\%,\\^,\\+,\\*,\\&,\\\\,\\/,\\?,\\|,\\:,\\<,\\>,\\{,\\},\\(,\\),\\'',\\;,\\=,\"]");
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
        var txt = new RegExp("[\\`,\\~,\\!,\\#,\\$$,\\%,\\^,\\+,\\*,\\&,\\\\,\\/,\\?,\\|,\\:,\\<,\\>,\\{,\\},\\(,\\),\\'',\\;,\\=,\"]");
        //特殊字符正则表达式
        if (txt.test(character)) {
            return true;
        }
    }
    return false;
}

/**
 * 提交的时候对value进行验证,value中存在特殊字符就返回true,没有特殊字符返回false
 * @param value
 */
function validateSpecCharaNotStrict(value) {
    for (var i = 0; i < value.length; i++) {
        var character = value.charAt(i);
        var txt = new RegExp("[\\`,\\~,\\!,\\#,\\$$,\\%,\\^,\\+,\\*,\\&,\\\\,\\?,\\|,\\<,\\>,\\{,\\},\\(,\\),\\'',\\;,\\=,\"]");
        //特殊字符正则表达式
        if (txt.test(character)) {
            return true;
        }
    }
    return false;
}


//提交时验证是不是地址格式
function validateIpAddress(value) {
    for (var i = 0; i < value.length; i++) {
        var character = value.charAt(i);
        var txt = new RegExp("[\\`,\\~,\\!,\\#,\\$$,\\%,\\^,\\+,\\*,\\&,\\\\,\\?,\\|,\\<,\\>,\\{,\\},\\(,\\),\\'',\\;,\\=,\"]");
        //特殊字符正则表达式
        if (txt.test(character)) {
            return true;
        }
    }
    return false;
}


//验证URL

function validateURL(value) {
	
    var strRegex = "^((https|http|ftp|rtsp|mms)?://)"       
        + "?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?" //ftp的user@      
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184      
        + "|" // 允许IP和DOMAIN（域名）      
        + "([0-9a-zA-Z_!~*'()-]+\.)*" // 域名- www.      
        + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\." // 二级域名      
        + "[a-zA-Z]{2,6})" // first level domain- .com or .museum      
        + "(:[0-9]{1,4})?" // 端口- :80      
        + "((/?)|"       
        + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";      
	var reg = new RegExp(strRegex);
	if (reg.test(value)) {
        return true;
    } else {
    	return false;
    }
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
function resetAll(formId) {
    var form = $$(formId);
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

/**
 *  效验ip地址是否合法 正确的IP地址是   1-223.0-255.0-255.0-255
 * @param str
 */
function isIP(str) {
    var ip = /^([1-9]|[1-9]\d|1\d{2}|2[0-1]\d|22[0-3])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}$$/;
    return   ip.test(str);
}

/**
 * 验证只能输入整数数字
 * @param value
 */
function isIntegerNumber(value) {
    var patrn = /^[0-9]+$$/;
    if (patrn.test(value)) {
        return true;
    } else {
        return false;
    }
}

/**
 * 验证只能输入大于等于-1的整数
 * @param value
 */
function numberCheck(value) {
	if(value=='-1'){
		return true;
	}else{
	  return isIntegerNumber(value);
	}
}


/**
 * 验证只能输入10到99的整数
 * @param value
 */
function isNumDiscount(value){
    var patrn = /^[0-9]+$$/;
    if (patrn.test(value)) {
       if(value >=1 && value <= 100){
          return true;
       }else{
          return false;
       }
        
    } else {
        return false;
    }

}



function isDiscount(value) {
    var patrn = /^0.\d{1,2}$/;
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
    var reNumber = /^\d*$/;
    if (reNumber.test(text)) {
        return true;
    } else {
        return false;
    }
}

//验证是否为电话格式，该电话格式为座机如0755-88888888
function isTelNumFormat(text){
	var regTelNum = /^\d{3,4}-\d{7,8}(-\d{3,4})?$/;
	if(regTelNum.test(text)){
		return true;
	}else{
		return false;
	}
	                                   
}

//验证是否为邮箱
function isMailFormat(text){
	var regMail =/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
	if(reMail.test(text)){
		return true;
	}else{
		return false;
	}
}


//可以为小数
function number(text) {
    if (!required(text))
        return true;

    this.number = /^[0-9]+(.[0-9]{2})?$$/;
    return this.number.test(text);
}  

//可以为小数或者整数
function isFloat(text) {
	var number = /^[0-9]+(.[0-9]{2})?$$/;
	var reNumber = /\d*/;
	var returnFlag = number.test(text) || reNumber.test(text);
	return  returnFlag;
} 

// 是不是为空
function required(text) {
    this.required = /.+/;
    return this.required.test(text);
}

/**
 * 获取页面元素
 * @param id
 */
function $$(id) {
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
    // var reString = /^[^a-zA-Z0-9]/;/^[0-9\u4e00-\u9faf]+$$/ /[^x00-xff]/g
    var reString = /^[\u4E00-\u9FA5]+$$/;
    return reString.test(string);
}
function isUnicodeFor(string) {
    var reString = /^[^a-zA-Z0-9_@;:'"=+-`!#$$%^&*(,<.>?)]/;
    return reString.test(string);
}
function trim(sInString) {
    if (sInString != null)
        sInString = sInString.replace(/^\s+/g, "").replace(/\s+$$/g, "");// strip
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
        $$(infoId).style.display = '';
    } else {
        $$(infoId).style.display = 'none';
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
 *  获取checkbox 选中的value值,以","分隔
 * @param name
 */
function getCheckValue(name) {
    var cbg = document.getElementsByName(name);
    var value = '';
    var len = cbg.length;
    for (var k = 0; k < len; k++) {
        if (cbg[k] && (cbg[k].type) && ((cbg[k].type == 'checkbox') || cbg[k].type == 'radio')
                && cbg[k].checked) {
            value += cbg[k].value + ',';
        }
    }
    if (value.length > 0) {
    	value = value.substring(0,value.length-1);
    }
    
    return value;
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
        if (cbg[k] && (cbg[k].type) && (cbg[k].type == 'checkbox' || cbg[k].type == 'radio')
                && cbg[k].checked) {
            retCnt++;
        }
    }
    return retCnt;
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
    if ((dateCompare($$(begindateId).value, $$(enddateId).value)) >= 0){
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
    var month1 = date1.substr(5, 2);
    var month2 = date2.substr(5, 2);

    var day1 = date1.substr(8, 2);
    var day2 = date2.substr(8, 2);

    var temp1 = year1 + "/" + month1 + "/" + day1;
    var temp2 = year2 + "/" + month2 + "/" + day2;

    var dateaa = new Date(temp1);
    var datebb = new Date(temp2);
    var date = datebb.getTime() - dateaa.getTime();
    time = Math.floor(date / (1000 * 60 * 60 * 24));
    return time;
}

window.preObjectId='';

function show(id,isHidenPre) {
	var object = document.getElementById(id);
	
	if(isHidenPre){
		var obj=document.getElementById(window.preObjectId);
		if(obj){
		  obj.style.display = 'none';
		}
		window.preObjectId=id;
	}

	if (object) {
		if (object.style.display != '') {
			object.style.display = '';
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
    var modelWin = window.showModalDialog(actinUrl,window,"resizable=no;status=no;scroll=no;center=yes;dialogHeight="+height+"px;dialogWidth="+width+"px");
	if(modelWin){
		if($("searchForm").action.indexOf("?")<0) {
		 $("searchForm").action+="?message="+modelWin;
		} else {
			 $("searchForm").action+="&message="+modelWin;
		}
		$("searchForm").submit();
	} 
    
 //alert(window.location.href);
    //if(modelWin == "success") {
		//window.location.reload();
		//window.location = window.location.href+="?message="+modelWin;
	//}
}


//列表全选
function selectAll(checkBoxObject, elementName){
    var idss = document.getElementsByName(elementName);
    var ifChecked = checkBoxObject.checked;
    for(i = 0; i < idss.length; i++){
        idss[i].checked = ifChecked;
    }
}

//比较时间 格式 yyyy-mm-dd hh:mi:ss  
function comptime(time1, time2){  
	//var beginTime = "2009-09-21";// "+time1;  
	//var endTime = "2009-09-20";//+time2; 
	var dateaa = new Date(2009,1,1,time1.substr(0, 2),time1.substr(3, 2),time1.substr(6, 2));
    var datebb = new Date(2009,1,1,time2.substr(0, 2),time2.substr(3, 2),time2.substr(6, 2));
    var date = datebb.getTime() - dateaa.getTime();
    var time = Math.floor(date / (1000 * 60 * 60 * 24))
    return time = Math.floor(date / (1000 * 60 * 60 * 24))
	
}
function showDate(id){
	$$(id).click();
}

/**
 * 初始化列表中各行的颜色
 * @param tableId 需要初始化的表格id
 * @param startTrIndex 从第几行开始初始化，若第一行为标题，则从第二行开始初始化
 */
function initTrBgByTable(tableId, startTrIndex){
    var listTable = document.getElementById(tableId);
    if(listTable){
        var trArr = document.getElementsByTagName("tr");
        var childTrArr = new Array();
        for(var i=0; i < trArr.length; i++){
            if(trArr[i].parentNode == listTable || trArr[i].parentNode.parentNode == listTable){
                childTrArr[childTrArr.length] = trArr[i];
            }
        }
        var targetTrArr = childTrArr.slice(startTrIndex-1, childTrArr.length);
        for(var j=0; j < targetTrArr.length; j++){
            targetTrArr[j].style.backgroundColor = j%2==0 ? "" : "#E3EFFF";
        }
    }
}

//扩展Date的format方法
    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),
            "S": this.getMilliseconds()
        }
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    }
    /**
    *转换日期对象为日期字符串
    * @param date 日期对象
    * @param isFull 是否为完整的日期数据,
    *               为true时, 格式如"2000-03-05 01:05:04"
    *               为false时, 格式如 "2000-03-05"
    * @return 符合要求的日期字符串
    */
    function getSmpFormatDate(date, isFull) {
        var pattern = "";
        if (isFull == true || isFull == undefined) {
            pattern = "yyyy-MM-dd hh:mm:ss";
        } else {
            pattern = "yyyy-MM-dd";
        }
        return getFormatDate(date, pattern);
    }
    /**
    *转换当前日期对象为日期字符串
    * @param date 日期对象
    * @param isFull 是否为完整的日期数据,
    *               为true时, 格式如"2000-03-05 01:05:04"
    *               为false时, 格式如 "2000-03-05"
    * @return 符合要求的日期字符串
    */

    function getSmpFormatNowDate(isFull) {
        return getSmpFormatDate(new Date(), isFull);
    }
    /**
    *转换long值为日期字符串
    * @param l long值
    * @param isFull 是否为完整的日期数据,
    *               为true时, 格式如"2000-03-05 01:05:04"
    *               为false时, 格式如 "2000-03-05"
    * @return 符合要求的日期字符串
    */

    function getSmpFormatDateByLong(l, isFull) {
        return getSmpFormatDate(new Date(l), isFull);
    }
    /**
    *转换long值为日期字符串
    * @param l long值
    * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
    * @return 符合要求的日期字符串
    */

    function getFormatDateByLong(l, pattern) {
        return getFormatDate(new Date(l), pattern);
    }
    /**
    *转换日期对象为日期字符串
    * @param l long值
    * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
    * @return 符合要求的日期字符串
    */
    function getFormatDate(date, pattern) {
        if (date == undefined) {
            date = new Date();
        }
        if (pattern == undefined) {
            pattern = "yyyy-MM-dd hh:mm:ss";
        }
        return date.format(pattern);
    }  
    
    
