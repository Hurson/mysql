package com.dvnchina.advertDelivery.accounts;

public class QueryConditionName {
	
	/**
	 * 私有构造函数 防止被实例化
	 */
	private QueryConditionName(){
		
	}

	/** 查询条件 台账流水订单号*/
	public static final String ACCOUNT_CODE = "accountCode";
	
	/** 查询条件 台账流水金额 查询范围开始*/
	public static final String ACCOUNT_MONEY_MIN= "accountMoneyMin";
	
	/** 查询条件 台账流水金额 查询范围结束*/
	public static final String ACCOUNT_MONEY_MAX = "accountMoneyMax";
	
	/** 查询条件 到期时间  查询范围开始*/
	public static final String ACCOUNT_EXPIRE_START = "accountExpireStart";
	
	/** 查询条件 到期时间  查询范围结束*/
	public static final String ACCOUNT_EXPIRE_OVER = "accountExpireOver";
}
