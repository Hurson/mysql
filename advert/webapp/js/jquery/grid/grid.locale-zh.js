;(function($){
/**
 * jqGrid English Translation
 * Tony Tomov tony@trirand.com
 * http://trirand.com/blog/ 
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
**/
$.jgrid = {};

$.jgrid.defaults = {
    allre: "共",
    pageStr: "页",
	recordtext: "条记录",
	loadtext: "加载数据...",
	pgtext : "/"
};
$.jgrid.search = {
    caption: "查询...",
    Find: "查找",
    Reset: "重置",
    odata : ['equal', 'not equal', 'less', 'less or equal','greater','greater or equal', 'begins with','ends with','contains' ]
};
$.jgrid.edit = {
    addCaption: "添加数据",
    editCaption: "修改数据",
    bSubmit: "提交",
    bCancel: "取消",
	bClose: "关闭",
    processData: "处理中...",
    msg: {
        required:"Field is required",
        number:"Please, enter valid number",
        minValue:"value must be greater than or equal to ",
        maxValue:"value must be less than or equal to",
        email: "is not a valid e-mail",
        integer: "Please, enter valid integer value",
		date: "Please, enter valid date value"
    }
};
$.jgrid.del = {
    caption: "删除",
    msg: "要删除该数据吗?",
    bSubmit: "删除",
    bCancel: "取消",
    processData: "处理中..."
};
$.jgrid.nav = {
	edittext: " ",
    edittitle: "编辑选中数据",
	addtext:" ",
    addtitle: "添加新数据",
    deltext: " ",
    deltitle: "删除选中数据",
    searchtext: " ",
    searchtitle: "查询数据",
    refreshtext: "",
    refreshtitle: "刷新数据",
    alertcap: "警告",
    alerttext: "未选中数据，请选择数据"
};
// setcolumns module
$.jgrid.col ={
    caption: "显示/隐藏列",
    bSubmit: "提交",
    bCancel: "取消"	
};
$.jgrid.errors = {
	errcap : "错误",
	nourl : "No url is set",
	norecords: "No records to process",
    model : "Length of colNames <> colModel!"
};
$.jgrid.formatter = {
	integer : {thousandsSeparator: " ", defaulValue: 0},
	number : {decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, defaulValue: 0},
	currency : {decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, prefix: "", suffix:"", defaulValue: 0},
	date : {
		dayNames:   [
			"Sun", "Mon", "Tue", "Wed", "Thr", "Fri", "Sat",
			"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
		],
		monthNames: [
			"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
			"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
		],
		AmPm : ["am","pm","AM","PM"],
		S: function (j) {return j < 11 || j > 13 ? ['st', 'nd', 'rd', 'th'][Math.min((j - 1) % 10, 3)] : 'th'},
		srcformat: 'Y-m-d',
		newformat: 'd/m/Y',
		masks : {
            ISO8601Long:"Y-m-d H:i:s",
            ISO8601Short:"Y-m-d",
            ShortDate: "n/j/Y",
            LongDate: "l, F d, Y",
            FullDateTime: "l, F d, Y g:i:s A",
            MonthDay: "F d",
            ShortTime: "g:i A",
            LongTime: "g:i:s A",
            SortableDateTime: "Y-m-d\\TH:i:s",
            UniversalSortableDateTime: "Y-m-d H:i:sO",
            YearMonth: "F, Y"
        },
        reformatAfterEdit : false
	},
	baseLinkUrl: '',
	showAction: 'show',
	addParam : ''
};
// US
// GB
// CA
// AU
})(jQuery);
