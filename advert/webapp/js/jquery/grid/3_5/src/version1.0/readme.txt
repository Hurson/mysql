�汾��1.0 final  (���߰汾��jqGrid 3.5.3)

�Ѿ��޸Ĳ������£�

���ܵ�json���������check��Ϊһ�����飬��setSelarrrow��ʹ��
������ setSelarrrow������ѡ�в�����check�а�������
setColWidth������colModel���ݸ���tempColModel ����ֹ��ͣ�仯���ڴ�С�����һ��Խ��Խ������⣩
setGridWidth������colModelʹ��tempColModel�����ж�$t.grid.cols[lvc]�Ƿ�δ���壨���gridû�����ݻ��쳣��
��� pageInfo�������Ƿ���ʾҳ��Ϣ��Ĭ��Ϊtrue����ʾ��
���� ts.p.width = pw > 0?  pw: 'nw' Ϊ ts.p.width = pw > 0?  pw: ts.p.width  ��pw=0ʱ�ᵼ��grid����޷����ƣ�������Ҫ����Ĭ�ϵ�width
��� beforeComplete��function(request){} ���� ��ajax������յ����������֮������ִ�����������������session��ʱҳ����ת�����û��Զ��壩
��� clickRow��function(selection,status){} ���� �������һ��ʱִ��ĳ���¼�,selectionΪ��ǰ�����ݵ�id������һ�е�ֵ��,statusΪѡ��״̬��trueΪѡ�У�falseδѡ�У����û��Զ���
������ ÿ�� colModel �� resizable Ĭ��ֵΪ false������ͨ�������ק���ı��еĿ�ȣ�
�����˱�����Ĭ��ֵ��
    sortorder: "desc",
	sortname: "id",
	datatype: "json",
	mtype: "POST",
	jsonReader: {repeatitems : false,id: "0"},
��setGridWidth����������һ���㷨����grid�ⲿ���ȱ��ڲ���table��ȶ�1px��ʱ�����������ڲ���table���
title��footer �߶ȸ���Ϊ28px
��� ���ʻ�����֧�� ����ҳ��Ϣ��loading��Ϣ�ͼ�¼��Ϣ���֣�


���⽨�飺
   1����iframe��ʹ������Ӧ��ȵ�ʱ��iframe�߶ȳ����Ժ�grid�Ŀ�Ȼ���������ֿ�
      ����취���ڴ������grid�Ժ�Ȼ���ӳټ���һ�� setGridWidth ��������������grid�Ŀ��
   

2.0�汾�����޸ģ�
   �ڲ�table��ȿ��Ա�grid�ⲿ�߿��ͨ�����򻬶������鿴�������ݣ����Խ������̫�࣬�������޷���ʾ�����⣨���룩
   ���ڲ�table��ȵ����ⲿgrid��ȵ�ʱ�����һ�е��ұ߿�Ӧ��ȥ�����Ǳ��룩
   �ڽ���xml���ݸ�ʽ��ʱ�����check���ݣ��Ǳ��룩