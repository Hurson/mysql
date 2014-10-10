版本：1.0 final  (基线版本：jqGrid 3.5.3)

已经修改部分如下：

接受的json数据添加了check，为一个数组，在setSelarrrow中使用
加入了 setSelarrrow方法，选中并高亮check中包含的行
setColWidth方法中colModel备份给了tempColModel （防止不停变化窗口大小，最后一列越来越大的问题）
setGridWidth方法中colModel使用tempColModel，并判断$t.grid.cols[lvc]是否未定义（如果grid没有数据会异常）
添加 pageInfo变量，是否显示页信息，默认为true（显示）
更改 ts.p.width = pw > 0?  pw: 'nw' 为 ts.p.width = pw > 0?  pw: ts.p.width  当pw=0时会导致grid表格无法绘制，这里需要设置默认的width
添加 beforeComplete：function(request){} 变量 当ajax请求接收到服务端数据之后立刻执行这个方法（可以做session超时页面跳转处理，用户自定义）
添加 clickRow：function(selection,status){} 变量 当鼠标点击一行时执行某个事件,selection为当前行数据的id（即第一列的值）,status为选中状态（true为选中，false未选中），用户自定义
更改了 每个 colModel 的 resizable 默认值为 false（不能通过鼠标拖拽来改变列的宽度）
更改了变量的默认值：
    sortorder: "desc",
	sortname: "id",
	datatype: "json",
	mtype: "POST",
	jsonReader: {repeatitems : false,id: "0"},
在setGridWidth方法最后添加一个算法，当grid外部框宽度比内部的table宽度多1px的时候，重新设置内部的table宽度
title与footer 高度更改为28px
添加 国际化中文支持 （仅页信息、loading信息和记录信息部分）


问题建议：
   1、在iframe中使用自适应宽度的时候，iframe高度超出以后，grid的宽度会比其他部分宽
      解决办法：在创建完成grid以后，然后延迟加载一次 setGridWidth 方法，重新设置grid的宽度
   

2.0版本建议修改：
   内部table宽度可以比grid外部边框大，通过横向滑动条来查看所有数据，可以解决列数太多，而数据无法显示的问题（必须）
   当内部table宽度等于外部grid宽度的时候，最后一列的右边框应该去掉（非必须）
   在接受xml数据格式的时候加入check数据（非必须）