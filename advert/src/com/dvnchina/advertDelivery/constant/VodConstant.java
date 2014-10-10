package com.dvnchina.advertDelivery.constant;

public class VodConstant {

	/** 产品 */
	public final static String PRODUCT = "product";

	/** 影片 */
	public final static String ASSET = "asset";

	/** 资源包 */
	public final static String ASSETPACKAGE = "assetPackage";

	/** 资源关系映射 */
	public final static String ASSETMAP = "assetMap";

	/** 默认状态信息,1为有效 */
	public final static char STATE = '1';

	/** 默认状态信息,0为无效 */
	public final static char FAILSTATE = '0';

	/** 是资源包 */
	public final static char IS_PACKAGE = '1';

	/** 是资源 */
	public final static char IS_NOT_PACKAGE = '0';

	/** 默认得分8.0 */
	public final static Double DE_SCORE = 8.0;

	/** 资源类型为1 */
	public final static Character DATA_TYPE_ASSET = new Character('1');

	/** 资源包类型为2 */
	public final static Character DATA_TYPE_ASSET_PACKAGE = new Character('2');

	/** 产品类型为3 */
	public final static Character DATA_TYPE_PRODUCT = new Character('3');

	/** 同步接口名称 */
	public final static String SYNC_NAME = "vod";

}
