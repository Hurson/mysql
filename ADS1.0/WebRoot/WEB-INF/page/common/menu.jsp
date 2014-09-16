<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>menu</title>
<link href="<%=request.getContextPath()%>/css/menu.css" rel="stylesheet" type="text/css" media="all">
<script language="javascript" defer="defer">
var myMenu; //在载入页面时建立菜单object并初始化.
xbDetectBrowser();
window.onload = function()
{
    myMenu = new SDMenu("my_menu");
    myMenu.collapseAll();
    myMenu.init();
}


function SDMenu(id) {
    if (!document.getElementById || !document.getElementsByTagName)
        return false;
    this.menu = document.getElementById(id);
    this.submenus = getElementsByClassName(this.menu,"div", "sub_menu" );
    this.remember = false;
    this.speed = 50;
    this.markCurrent = true;
    this.oneSmOnly = true;
    this.showNum = 1;				// 每次最多展开菜单数
    this.showList = new Array();
}
SDMenu.prototype.containShowMenu = function(submenu) {
    for (var i = 0; i < this.showList.length; i++) {
        if (this.showList[i] == submenu)return true;
    }
    return false;
}
SDMenu.prototype.addShowMenu = function(submenu) {
    for (var i = 0; i < this.showNum && i < this.showList.length; i++) {
        if (this.showList.length < this.showNum) {
            this.showList[this.showList.length - i] = this.showList[this.showList.length - i - 1];
        } else {
            this.showList[this.showNum - i - 1] = this.showList[this.showNum - i - 2];
        }
    }
    this.showList[0] = submenu;
}
SDMenu.prototype.init = function() {
    var mainInstance = this;
    for (var i = 0; i < this.submenus.length; i++) {
        this.submenus[i].getElementsByTagName("span")[0].onclick = function() {
            mainInstance.toggleMenu(this.parentNode.parentNode);
        };
    }
    this.initTarget();
};

SDMenu.prototype.initTarget = function() {
    var links = this.menu.getElementsByTagName("li");
    var path;
    for (var i = 0; i < links.length; i++) {
        links[i].onclick = new Function("goToURL(\'" + links[i].getAttribute("href") + "\', this)");
    }
};
SDMenu.prototype.toggleMenu = function(submenu) {
    if (submenu.getElementsByTagName("span")[0].className == "") {
        this.expandMenu(submenu);
    }
    else if (submenu.getElementsByTagName("span")[0].className == "currentclose") {
        this.expandMenu(submenu);
    }
    else if (submenu.getElementsByTagName("span")[0].className == "currentopen") {
        this.collapseCloseMenu(submenu);
    }
    else {
        this.collapseMenu(submenu);
    }   
};
SDMenu.prototype.expandMenu = function(submenu) {
    submenu.getElementsByTagName("ul")[0].style.display = "";
    submenu.getElementsByTagName("span")[0].className = "currentopen";
    var lis = submenu.getElementsByTagName("li");
    if (lis.length != 0) {
        for(var i = 0 ; i < lis.length ; i ++) {
        	 lis[i].className = "x";
        }
    }
    if (!this.containShowMenu(submenu))this.addShowMenu(submenu);
    this.collapseOthers(submenu);
};
SDMenu.prototype.collapseMenu = function(submenu) {
    var minHeight = submenu.getElementsByTagName("span")[0].className="";
    var lis = submenu.getElementsByTagName("li");
    if (lis.length != 0) {
        for(var i = 0 ; i < lis.length ; i ++) {
        	 lis[i].className = "";
        }
    }
    submenu.getElementsByTagName("ul")[0].style.display = "none";
};
SDMenu.prototype.collapseCloseMenu = function(submenu) {
    submenu.getElementsByTagName("span")[0].className="currentclose";
    var lis = submenu.getElementsByTagName("li");
    if (lis.length != 0) {
        for(var i = 0 ; i < lis.length ; i ++) {
        	 lis[i].className = "";
        }
       
    }
    submenu.getElementsByTagName("ul")[0].style.display = "none";
};
SDMenu.prototype.collapseOthers = function(submenu) {
    if (this.oneSmOnly) {
        for (var i = 0; i < this.submenus.length; i++) {
            if (!this.containShowMenu(this.submenus[i])) {
                this.collapseMenu(this.submenus[i]);
            }
        }
    }
};
SDMenu.prototype.expandAll = function() {
    var oldOneSmOnly = this.oneSmOnly;
    this.oneSmOnly = false;
    for (var i = 0; i < this.submenus.length; i++) {
            this.expandMenu(this.submenus[i]);
    }
    this.oneSmOnly = oldOneSmOnly;
};
SDMenu.prototype.collapseAll = function() {
    for (var i = 0; i < this.submenus.length; i++){
        if (this.submenus[i].getElementsByTagName("span").className != "currentopen"){
            this.collapseMenu(this.submenus[i]);
    	}
	}
    //进入的时候，展开一个菜单
	//this.toggleMenu(this.submenus[0]);
};
function goToURL(url, domm) { //点击子菜单项载入相应链接的fuction
    var lis = getElementsByClassName(document,"li","current");
    for(var i = 0 ; i < lis.length ; i ++ ) {
    	lis[i].className = "";
    }
    domm.className = "current";
    var middlepath = domm.parentNode.parentNode.getElementsByTagName("span")[0].innerHTML;
    var lastpath = domm.innerHTML;
    var path = '首页 &gt;&gt; ' + middlepath.trim() + ' &gt;&gt; ' 
               + lastpath.replaceAll("&nbsp;","").trim();
    parent.window.frames["navigateFrame"].document.getElementById("navigatediv").style.display="";
    parent.window.frames["navigateFrame"].document.getElementById("navigate").innerHTML = path; 
    parent.frames['mainFrame'].location="<%=request.getContextPath()%>" + url;
}


function xbDetectBrowser()
{
    var oldOnError = window.onerror;
    var element = null;

    window.onerror = null;

    // work around bug in xpcdom Mozilla 0.9.1
    window.saveNavigator = window.navigator;

    navigator.OS = '';
    navigator.version = parseFloat(navigator.appVersion);
    navigator.org = '';
    navigator.family = '';

    var platform;
    if (typeof(window.navigator.platform) != 'undefined')
    {
        platform = window.navigator.platform.toLowerCase();
        if (platform.indexOf('win') != -1)
            navigator.OS = 'win';
        else if (platform.indexOf('mac') != -1)
            navigator.OS = 'mac';
        else if (platform.indexOf('unix') != -1 || platform.indexOf('linux') != -1 || platform.indexOf('sun') != -1)
                navigator.OS = 'nix';
    }

    var i = 0;
    var ua = window.navigator.userAgent.toLowerCase();


    if (ua.indexOf('opera') != -1)
    {
        i = ua.indexOf('opera');
        navigator.family = 'opera';
        navigator.org = 'opera';
        navigator.version = parseFloat('0' + ua.substr(i + 6), 10);
    }
    else if ((i = ua.indexOf('msie')) != -1)
    {
        navigator.org = 'microsoft';
        navigator.version = parseFloat('0' + ua.substr(i + 5), 10);

        if (navigator.version < 4)
            navigator.family = 'ie3';
        else
            navigator.family = 'ie4'
    }
    else if (ua.indexOf('gecko') != -1)
        {
            navigator.family = 'gecko';
            var rvStart = ua.indexOf('rv:');
            var rvEnd = ua.indexOf(')', rvStart);
            var rv = ua.substring(rvStart + 3, rvEnd);
            var rvParts = rv.split('.');
            var rvValue = 0;
            var exp = 1;

            for (var i = 0; i < rvParts.length; i++)
            {
                var val = parseInt(rvParts[i]);
                rvValue += val / exp;
                exp *= 100;
            }
            navigator.version = rvValue;

            if (ua.indexOf('netscape') != -1)
                navigator.org = 'netscape';
            else if (ua.indexOf('compuserve') != -1)
                navigator.org = 'compuserve';
            else
                navigator.org = 'mozilla';
        }
        else if ((ua.indexOf('mozilla') != -1) && (ua.indexOf('spoofer') == -1) && (ua.indexOf('compatible') == -1) && (ua.indexOf('opera') == -1) && (ua.indexOf('webtv') == -1) && (ua.indexOf('hotjava') == -1))
            {
                var is_major = parseFloat(navigator.appVersion);

                if (is_major < 4)
                    navigator.version = is_major;
                else
                {
                    i = ua.lastIndexOf('/')
                    navigator.version = parseFloat('0' + ua.substr(i + 1), 10);
                }
                navigator.org = 'netscape';
                navigator.family = 'nn' + parseInt(navigator.appVersion);
            }
            else if ((i = ua.indexOf('aol')) != -1)
                {
                    // aol
                    navigator.family = 'aol';
                    navigator.org = 'aol';
                    navigator.version = parseFloat('0' + ua.substr(i + 4), 10);
                }
                else if ((i = ua.indexOf('hotjava')) != -1)
                    {
                        // hotjava
                        navigator.family = 'hotjava';
                        navigator.org = 'sun';
                        navigator.version = parseFloat(navigator.appVersion);
                    }

    window.onerror = oldOnError;

}

//三个参数都是必需的，查找一网页中5007个类名为“cell”的元素，IE8历时1828 ~ 1844毫秒， 
//IE6为4610 ~ 6109毫秒，FF3.5为46 ~ 48毫秒，opera10为31 ~ 32毫秒，Chrome为23~ 26毫秒， 
//safari4为19 ~ 20毫秒 
function getElementsByClassName(oElm, strTagName, strClassName){ 
    var arrElements = (strTagName == "*" && oElm.all)? oElm.all : 
        oElm.getElementsByTagName(strTagName); 
    var arrReturnElements = new Array(); 
    strClassName = strClassName.replace(/\-/g, "\\-"); 
    var oRegExp = new RegExp("(^|\\s)" + strClassName + "(\\s|$)"); 
    var oElement; 
    for(var i=0; i < arrElements.length; i++){ 
        oElement = arrElements[i]; 
        if(oRegExp.test(oElement.className)){ 
            arrReturnElements.push(oElement); 
        } 
    } 
    return (arrReturnElements) 
}

String.prototype.trim = function() 
{ 
   return this.replace(/(^\s*)|(\s*$)/g, ""); 
}

String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}
</script>
</head>

<body>
<!-- left_menu start -->
<div id="my_menu" class="left_menu">
    <c:if test="${menuList!= null && fn:length(menuList) > 0}">
        <c:forEach items="${menuList}" var="menu">
           <c:if test="${menu.isPermissionFlag == 1}">
           	     <div class="sub_menu">
	            	<div id="div_${menu.id}" class="sub_menu_title pointer" ><span>${menu.name}</span> </div>
	            	<ul class="sub_menu_list" id="ul_${menu.id}" style="display:none;">
	                <c:if test="${menu.subMenu!= null && fn:length(menu.subMenu) > 0}">
	                    <c:forEach items="${menu.subMenu}" var="submenu">
	                    	<c:if test="${submenu.isPermissionFlag == 1}">
		                        <li href="${submenu.url}" id="li_${submenu.id}">
		                              &nbsp;&nbsp;&nbsp;${submenu.name}
		                        </li>
		                    </c:if>
	                    </c:forEach>
	                </c:if>
	           	    </ul>
	           	    <div class="sub_menu_bottom"></div>
	           	</div>
            </c:if>
        </c:forEach>
    </c:if>
</div>
<div  class="bottm" align="right">
<p>版权</p>
<p>Copyright by AVIT LTD</p>
<p>佳创视讯技术股份有限公司</p>
</div>
</body>
</html>