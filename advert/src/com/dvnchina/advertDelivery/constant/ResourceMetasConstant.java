package com.dvnchina.advertDelivery.constant;

public class ResourceMetasConstant {
	
	
	/**
	 * 上线素材不符合要求
	 */
	public static final int UP_ERROR = 3;
	/**
	 * 上线素材符合要求
	 */
	public static final int UP = 4;
	/**
	 * 上线素材不符合要求
	 */
	public static final int DOWN_ERROR = 5;
	/**
	 * 下线素材还在订单运行期
	 */
	public static final int DOWN_ORDER = 6;
	/**
	 * 下线素材符合要求
	 */
	public static final int DOWN = 7;
	
	/**
	 * 批量删除素材时不符合要求
	 */
	public static final int DELETE_ERROR =8;
	
	
	/**
	 * 素材用于公益
	 */ 
	public  static final int CATEGORY_CHARITY = 0;

	/**
	 * 素材用于用以商用 
	 */
	public  static final int CATEGORY_BUSINESS = 1;
	
	
	/**图片类型**/
	public static final int IMAGE_TYPE=0;
	/**视频类型**/
	public static final int VIDEO_TYPE=1;
	/**文字类型**/
	public static final int MESSAGE_TYPE=2;
	
	/**图片类型**/
	public static final String IMAGE_TYPE_STR="0";
	/**视频类型**/
	public static final String VIDEO_TYPE_STR="1";
	/**文字类型**/
	public static final String MESSAGE_TYPE_STR="2";
	
	/**图片类型**/
	public static final Integer IMAGE_TYPE_IN = 0;
	/**视频类型**/
	public static final Integer IVIDEO_TYPE_IN = 1;
	/**文字类型**/
	public static final Integer MESSAGE_TYPE_IN = 2;
	
	public static final String FREE_POSITION_REMIND = "freePositionRemind";
	
	
	
	
	//0待审核，1审核不通过，2上线，3下线
	//已审核删除状态 ：删除运行期表中该素材记录，并将维护表的记录设置为已审核删除状态。
	//未审核删除状态 : 未审核的素材
	/**
	 * 待审核 
	 */
	public static final char AUDIT_NOT_PASS='0';
	
	/**
	 *审核不通过 
	 */
	public static final char AUDIT_NO_PASS='1';
	
	/**
	 *上线 
	 */
	public static final char  ONLINE='2';
	
	/**
	 * 下线 
	 */
	public static final char DOWNLINE = '3';
	
	/**
	 *  已删除状态
	 */
	public static final char DOWN_LINE_DELETE = '4';
	
	
	/**
	 * 下线 状态的字符串类型 
	 */
	
	public static final String DOWNLINE_STRING = "3";
	
	

}
