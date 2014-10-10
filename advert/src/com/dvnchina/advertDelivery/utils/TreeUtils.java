package com.dvnchina.advertDelivery.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import com.dvnchina.advertDelivery.bean.ColumnTree;
import com.dvnchina.advertDelivery.bean.TreeBaseBean;

/**
 * 树操作工具类
 * 
 * @author chennaidong
 *
 */
public class TreeUtils {

	/**
	 * 树状结构的数据
	 * @param list 数据
	 * @param pid 父节点的Id
	 * @return
	 */
	public static List<TreeBaseBean> getTreeColumnTrees(List<TreeBaseBean> list , String pid){
		List<TreeBaseBean> nodeList = new ArrayList<TreeBaseBean>();
		for(Iterator<TreeBaseBean> iterator = list.iterator(); iterator.hasNext();) {
			TreeBaseBean dto = (TreeBaseBean) iterator.next();
	            if (dto.getPid().equals(pid)) {
	            	TreeBaseBean tp = new TreeBaseBean();
	            	tp.setId(dto.getId());
	                tp.setChecked("0");
	                tp.setText(dto.getText());
	                tp.setPid(dto.getPid());
	                List<TreeBaseBean> child = new ArrayList<TreeBaseBean>();
	                child = getTreeColumnTrees(list, dto.getId());
	                tp.setChildren(child);
	                nodeList.add(tp);
	            }
		}
		return nodeList;
	}
	
	public static String getJsonTree(List<TreeBaseBean> list , String pid){
		List<TreeBaseBean> nodeList = getTreeColumnTrees( list ,  pid);
		String json = JSONArray.fromObject(nodeList).toString()+"";
		return json;
	}
	
}
