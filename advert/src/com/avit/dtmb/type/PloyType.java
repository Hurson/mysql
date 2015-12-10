package com.avit.dtmb.type;

public enum PloyType {
	Area("1","区域"),
	Time("2","时段"),
	ChanGroup("3","频道组"),
	AudGronp("4","广播频道组"),
	UserIndustry("5","行业"),
	UserLevel("6","级别"),
	UserTVNNO("7","TVN号");
	
	private String key;
	private String value;
	private PloyType(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public static String getValue(String key){
		for(PloyType type : PloyType.values()){
			if(type.key.equals(key)){
				return type.value;
			}
		}
		return null;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	

}
