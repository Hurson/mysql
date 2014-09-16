package com.avit.ads.pushads.task.bean;

import java.util.HashMap;
import java.util.Map;

public class Uidesc {
	public Uidesc()
	{
		
	}
//	List<String> typeList= new ArrayList<String>();
//	List<String> nameList =new ArrayList<String>();
	Map<String,String> areaUIBodyMap = new HashMap<String,String>();
//	public List<String> getTypeList() {
//		return typeList;
//	}
//	public void setTypeList(List<String> typeList) {
//		this.typeList = typeList;
//	}
//	public List<String> getNameList() {
//		return nameList;
//	}
//	public void setNameList(List<String> nameList) {
//		this.nameList = nameList;
//	}
	public Map<String, String> getAreaUIBodyMap() {
		return areaUIBodyMap;
	}
	public void setAreaUIBodyMap(Map<String, String> areaUIBodyMap) {
		this.areaUIBodyMap = areaUIBodyMap;
	}
	/**
	 * 新增区域中指定的值
	 * @param type
	 * @param name
	 * @param areaCode
	 */
	public void addDesc(String type,String name,String areaCode)
	{
		if(areaUIBodyMap.containsKey(areaCode)){
			String bodyContent = areaUIBodyMap.get(areaCode);
			bodyContent = bodyContent+";"+type+":"+name;
			areaUIBodyMap.put(areaCode, bodyContent);
		}else{
			areaUIBodyMap.put(areaCode, type+":"+name);
		}
//		if (!typeList.contains(type))
//		{
//			typeList.add(type);
//		}
//		if (!nameList.contains(name))
//		{
//			nameList.add(name);
//		}
	}
	
	/**
	 * 删除区域中指定的值
	 * @param type
	 * @param name
	 * @param areaCode
	 */
	public void delete(String type,String name,String areaCode)
	{
		if(areaUIBodyMap.containsKey(areaCode)){
			String removeBodyContent = type+":"+name;
			String bodyContent = areaUIBodyMap.get(areaCode);
			int index = bodyContent.indexOf(removeBodyContent+";");
			int removeLength = removeBodyContent.length();
			if(index >= 0){
				bodyContent = bodyContent.substring(0,index)+bodyContent.substring(index+removeLength+1,bodyContent.length());
			}else if(bodyContent.indexOf(";"+removeBodyContent)>=0){
				index = bodyContent.indexOf(";"+removeBodyContent);
				bodyContent = bodyContent.substring(0,index)+bodyContent.substring(index+removeLength+1,bodyContent.length());
			}else if(bodyContent.indexOf(removeBodyContent)>=0){
				bodyContent = "";
			}
			areaUIBodyMap.put(areaCode, bodyContent);
		}
//		for (int i=0;i<typeList.size();i++)
//		{
//			if (type.equals(typeList.get(i)))
//			{
//				typeList.remove(i);
//				break;
//			}
//		}
//		for (int i=0;i<nameList.size();i++)
//		{
//			if (name.equals(nameList.get(i)))
//			{
//				nameList.remove(i);
//				break;
//			}
//		}
	}
}
