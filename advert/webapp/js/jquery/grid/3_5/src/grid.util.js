function searchForm(formId) {
    var $search = jQuery(".mygrid_ui_to_num");
    if ($search.length == 1) {
        searchByFormAndGrid(formId, $search.attr('id'));
    }
}
function searchByIdList(formId, gridId, array) {
    if (array.constructor == Array)
        for (var i = 0; i < array.length; i++) {
            var value = $("#" + array[i] + "").fieldValue() ;
            jQuery("#" + gridId).setPostDataItem(array[i], value);
        }
    $("#" + gridId).trigger("reloadGrid");
}
function searchByIdValue(formId, gridId, values) {
    if (values.constructor == Array)
        for (var i = 0; i < values.length; i++) {
            jQuery("#" + gridId).setPostDataItem(values[i].name, values[i].value);
        }
    $("#" + gridId).trigger("reloadGrid");
}
function searchByFormAndGrid(formId, gridId) {
    var postData = $("#" + formId + "").formToArray() ;
    for (var i = 0; i < postData.length; i++) {
        jQuery("#" + gridId + "").setPostDataItem(postData[i].name, postData[i].value);
    }
    jQuery("#" + gridId + "").trigger("reloadGrid");
}
function reloadGridWidth(time) {
    if (time == undefined)time = 1000;
    if ($(".mygrid_ui_to_num").length >0) {
        $(".mygrid_ui_to_num").each(function() {
            var gridId = $(this).attr('id');
            if ($("#" + gridId).getGridParam('autowidth')) {
                $("#" + gridId).reloadGridWidthByRef(time);
            }
        });
    }
} 
function getSelectRowData(gridId) {
    var selects = new Array();
    var selarrrow = jQuery("#" + gridId).getGridParam('selarrrow');
    if (selarrrow.length > 0) {
        for (var i = 0; i < selarrrow.length; i++) {
            var select = jQuery("#" + gridId).getRowData(selarrrow[i]);
            selects.push(select);
        }
    }
    return selects;
}