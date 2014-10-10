package com.avit.common.type;

import java.util.HashMap;
import java.util.Map;

public final class AreaType {
	
	private static Map<String, String> areaMap;
	
	static{
		if(null == areaMap){
			areaMap = new HashMap<String, String>();
		}
		areaMap.put("any", "152050000000"); //安阳
		areaMap.put("heb", "152060000000"); //鹤壁
		areaMap.put("jiaoz", "152080000000"); //焦作
		areaMap.put("jiy", "152180000000"); //济源
		areaMap.put("kaif", "152020000000"); //开封
		areaMap.put("luoh", "152110000000"); //漯河
		areaMap.put("luoy", "152030000000"); //洛阳
		areaMap.put("nany", "152130000000"); //南阳
		areaMap.put("pds", "152040000000"); //平顶山
		areaMap.put("puy", "152090000000"); //濮阳
		areaMap.put("shangq", "152140000000"); //商丘
		areaMap.put("smx", "152120000000"); //三门峡
		areaMap.put("xinx", "152070000000"); //新乡
		areaMap.put("xiny", "152150000000"); //信阳
		areaMap.put("xuc", "152100000000"); //许昌
		areaMap.put("zhengzh", "152010000000"); //郑州
		areaMap.put("zhouk", "152160000000"); //周口
		areaMap.put("zmd", "152170000000"); //驻马店
		
		
	}
	
	private AreaType(){};
	
	public static Map<String, String> getAreaMap(){
		return areaMap;
	}
}
