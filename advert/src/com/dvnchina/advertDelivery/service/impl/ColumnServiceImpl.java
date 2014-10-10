package com.dvnchina.advertDelivery.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import org.hibernate.mapping.Array;

import com.dvnchina.advertDelivery.bean.ColumnTree;
import com.dvnchina.advertDelivery.constant.PurviewConstant;
import com.dvnchina.advertDelivery.dao.ColumnDao;
import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.service.ColumnService;

public class ColumnServiceImpl  implements ColumnService{
	
	private ColumnDao columnDao;
	
	@Override
	public boolean insertColumn(Column column) {
		try {
			columnDao.save(column);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean deleteColumn(String columnId) {
		columnDao.deleteObj("Column", Integer.valueOf(columnId));
		return true;
	}

	@Override
	public List<Column> getAllColumnList() {
		
		List<Column> columnList=columnDao.getAllColumnList();

		for(int i=0; columnList!=null && i < columnList.size();i++){
			if(columnList.get(i).getParentId()==1){
				columnList.get(i).setParentName("菜单导航");
			}else{
				int parentId = columnList.get(i).getParentId();
				for(int j=0; j < columnList.size();j++){
					int id = columnList.get(j).getId();
					if(parentId==id){
						columnList.get(i).setParentName(columnList.get(j).getName());
					}
				}
			}
			
		}
	for(int i =0;columnList !=null && i <columnList.size();i++){
		if(columnList.get(i).getParentId()==0){
			columnList.remove(i);
		}
	}
		return columnList;
	}
	
//	public String getTreeColumnList(){
//		
//		List<ColumnTree>  result = getTreeColumnTrees(getColumnTreeList() , "-1");
//		
//		return JSONArray.fromObject(result).toString()+"";
//	}
	
	
//	public String getTreeColumnList(String columnIds){
//		String[] columnIDs  = columnIds.split(PurviewConstant.SIGN_COMMA);
//		List<ColumnTree>  result = getTreeColumnTrees(getColumnTreeList(columnIDs) ,columnIDs, "-1");
//		String json = JSONArray.fromObject(result).toString()+"";
//		System.out.println(json);
//		return json;
//	}
	
	public String getTreeColumnList(List<Integer> columnIdList){
//		String[] columnIDs  = columnIds.split(PurviewConstant.SIGN_COMMA);
		List<ColumnTree>  result = getTreeColumnTrees(getColumnTreeList(columnIdList) , "-1");
		String json = JSONArray.fromObject(result).toString()+"";
		return json;
	}
	
	/**
	 * 获取栏目数据
	 * 
	 * @return
	 */
	private List<ColumnTree> getColumnTreeList(){
		
		List<Column> columns = columnDao.getColumnList(1, 3);
		List<ColumnTree> list = new ArrayList<ColumnTree>();
		for(int i = 0; i < columns.size(); i++){
			ColumnTree columnTree = new ColumnTree();
			columnTree.setId(columns.get(i).getId().toString());
			columnTree.setText(columns.get(i).getName());
			columnTree.setPid(columns.get(i).getParentId().toString());
			
			list.add(columnTree);
		}
		
		return list;
	}
	
	/**
	 * 获取栏目数据
	 * 
	 * @return
	 */
//	private List<ColumnTree> getColumnTreeList(String[] columnIDs){
//		
//		List<Column> columns = columnDao.getColumnList(1, 3);
//		List<ColumnTree> list = new ArrayList<ColumnTree>();
//		for(int i = 0; i < columns.size(); i++){
//			ColumnTree columnTree = new ColumnTree();
//			columnTree.setId(columns.get(i).getId().toString());
//			columnTree.setText(columns.get(i).getName());
//			columnTree.setPid(columns.get(i).getParentId().toString());
//			boolean checked = false;
//            for(String colId : columnIDs){
//            	if(colId.equals(columns.get(i).getId().toString())){
//            		checked = true;
//            		break;
//            	}
//            }
//            
//            if(checked){columnTree.setChecked("1");}else{ columnTree.setChecked("0");}
//			list.add(columnTree);
//		}
//		
//		return list;
//	}
	
	/**
	 * 获取栏目数据
	 * 
	 * @return
	 */
	private List<ColumnTree> getColumnTreeList(List<Integer> columnIdList){
		
		List<Column> columns = columnDao.getColumnList(1, 3);
		List<ColumnTree> list = new ArrayList<ColumnTree>();
		for(int i = 0; i < columns.size(); i++){
			ColumnTree columnTree = new ColumnTree();
			columnTree.setId(columns.get(i).getId().toString());
			columnTree.setText(columns.get(i).getName());
			columnTree.setPid(columns.get(i).getParentId().toString());
			boolean checked = false;
			if(columnIdList != null && columnIdList.size()>0){
	            for(Integer colId : columnIdList){
	            	if(colId.intValue() == columns.get(i).getId().intValue()){
	            		checked = true;
	            		break;
	            	}
	            }
			}
            
            if(checked){columnTree.setChecked("1");}else{ columnTree.setChecked("0");}
			list.add(columnTree);
		}
		
		return list;
	}
	
	/**
	 * 整理栏目集合为树结构的数据
	 * 
	 * @param list 所有的栏目集合
	 * @param pid  树根节点
	 * @return     树结构的栏目集合
	 */
//	private List<ColumnTree> getTreeColumnTrees(List<ColumnTree> list , String[] columnIDs ,   String pid){
//		
//		List<ColumnTree> nodeList = new ArrayList<ColumnTree>();
//		for(Iterator<ColumnTree> iterator = list.iterator(); iterator.hasNext();) {
//			
//			ColumnTree dto = (ColumnTree) iterator.next();
//            if (dto.getPid().equals(pid)) {
//            	ColumnTree tp = new ColumnTree();
//            	tp.setId(dto.getId());
//            	
//            	tp.setChecked(dto.getChecked());
//            	
//                tp.setText(dto.getText());
//                tp.setPid(dto.getPid());
//                List<ColumnTree> child = new ArrayList<ColumnTree>();
//                child = getTreeColumnTrees(list, columnIDs,dto.getId());
//                tp.setChildren(child);
//                nodeList.add(tp);
//            }
//		}
//		return nodeList;
//	}
	
	/**
	 * 整理栏目集合为树结构的数据
	 * 
	 * @param list 所有的栏目集合
	 * @param pid  树根节点
	 * @return     树结构的栏目集合
	 */
	private List<ColumnTree> getTreeColumnTrees(List<ColumnTree> list , String pid){
		
		List<ColumnTree> nodeList = new ArrayList<ColumnTree>();
		for(Iterator<ColumnTree> iterator = list.iterator(); iterator.hasNext();) {
			
			ColumnTree dto = (ColumnTree) iterator.next();
            if (dto.getPid().equals(pid)) {
            	ColumnTree tp = new ColumnTree();
            	tp.setId(dto.getId());
            	tp.setChecked(dto.getChecked());
                tp.setText(dto.getText());
                tp.setPid(dto.getPid());
                List<ColumnTree> child = new ArrayList<ColumnTree>();
                child = getTreeColumnTrees(list,dto.getId());
                tp.setChildren(child);
                nodeList.add(tp);
            }
		}
		return nodeList;
	}
	
	/**
	 * 整理栏目集合为树结构的数据
	 * 
	 * @param list 所有的栏目集合
	 * @param pid  树根节点
	 * @return     树结构的栏目集合
	 */
//	private List<ColumnTree> getTreeColumnTrees(List<ColumnTree> list , String pid){
//		
//		List<ColumnTree> nodeList = new ArrayList<ColumnTree>();
//		for(Iterator<ColumnTree> iterator = list.iterator(); iterator.hasNext();) {
//			
//			ColumnTree dto = (ColumnTree) iterator.next();
//			if (dto.getPid().equals(pid)) {
//				ColumnTree tp = new ColumnTree();
//				tp.setId(dto.getId());
//				tp.setChecked("0");
//				tp.setText(dto.getText());
//				tp.setPid(dto.getPid());
//				List<ColumnTree> child = new ArrayList<ColumnTree>();
//				child = getTreeColumnTrees(list, dto.getId());
//				tp.setChildren(child);
//				nodeList.add(tp);
//			}
//		}
//		return nodeList;
//	}
	
	
	
	@Override
	public List<Column> getAllColumnList(int first,int pageSize) {
		
		return columnDao.getAllColumnList(first, pageSize);
	}

	@Override
	public List<Column> getChildColumnList(String columnPId) {
		return columnDao.getChildColumnList(columnPId);
	}

	@Override
	public Column getSingleColumn(String columnId) {
		Column columnBean = (Column) columnDao.get(Column.class, Integer.valueOf(columnId));
		return columnBean;
	}

	@Override
	public boolean updateColumn(Column column) {
		columnDao.update(column);
		return true;
	}

	public ColumnDao getColumnDao() {
		return columnDao;
	}

	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	@Override
	public List<Column> getFirstColumnList() {
		List<Column> rl = columnDao.getColumnList(1, 2);
		return rl;
	}

	
	
}
