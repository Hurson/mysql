package cn.com.avit.ads.syndata;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SynCpsData {

	public final Log logger = LogFactory.getLog(getClass());

	public SynCpsData() {

	}
    /*
     * 整体同步数据接口
     */
	public void synCPSData() {
		//同步产品数据
//		this.synProductInfo();
		//同步资源服务器
		this.synAssetInfo();
		//同步栏目信息，回看栏目信息及节目分类
		this.synCategoryInfo();
		
		synAssetCategory();
		

	}
	
	
	
	public List getCpsCategoryInfo(Connection cpsConn){
		List<HashMap> results = new ArrayList<HashMap>();
		ResultSet rs = null;
		try {
			rs = cpsConn.prepareStatement(
			"select * from T_CATEGORY t").executeQuery();
	    while (rs.next()) {
		ResultSetMetaData rsmd = rs.getMetaData();
		HashMap map = new HashMap();
		int count = rsmd.getColumnCount();
		String[] name = new String[count];

		for (int i = 0; i < count; i++) {

			name[i] = rsmd.getColumnName(i + 1);
			map.put(name[i], rs.getObject(name[i]));
		}
		results.add(map);
	   }
		} catch (Exception e) {
			e.printStackTrace();
			if (logger.isErrorEnabled()) {
				logger.debug("获取CPS数据库链接失败", e);
			}
		}finally {
			try {
				if (rs!=null){
					rs.close();
				}
			} catch (SQLException e) {

			}

		}
		
		return results;
	}
	public List getCpsAssetInfo(Connection cpsConn){
		List<HashMap> results = new ArrayList<HashMap>();
		ResultSet rs = null;
		try {
			rs = cpsConn.prepareStatement(
			"select * from t_content t where t.service_code like '%VOD%'").executeQuery();
	    while (rs.next()) {
		ResultSetMetaData rsmd = rs.getMetaData();
		HashMap map = new HashMap();
		int count = rsmd.getColumnCount();
		String[] name = new String[count];

		for (int i = 0; i < count; i++) {

			name[i] = rsmd.getColumnName(i + 1);
			map.put(name[i], rs.getObject(name[i]));
		}
		results.add(map);
	   }
		} catch (Exception e) {
			e.printStackTrace();
			if (logger.isErrorEnabled()) {
				logger.debug("获取CPS数据库链接失败", e);
			}
		}finally {
			try {
				if (rs!=null){
					rs.close();
				}
			} catch (SQLException e) {

			}

		}
		
		return results;
	}
	
	

	
	
	
	
	
	
	
	
	private void insertCategory(List<HashMap> insertList,Connection adsConn){
		if(insertList == null || insertList.size()<=0){
			return;
		}
		PreparedStatement ps = null;
		String sql = "insert into T_Syn_CPS_CATEGORY(ID,RESOURCE_TYPE,RESOURCE_ID,RESOURCE_NAME,PARENT_ID,RESOURCE_ORDER,RESOURCE_PATH,RESOURCE_SHOW_PATH,RESOURCE_STENCIL,STATE,TEMPLATE_ID,PRODUCT_ID,IS_ADV,BUSINESS_TYPE,IS_FOCUS,IS_COMMEND,CREATE_TIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {	
            ps = adsConn.prepareStatement(sql);
            for(HashMap hm : insertList){
            	ps.setObject(1,SynDataUtil.toNotNullObject(hm.get("ID")));
				ps.setObject(2,SynDataUtil.toNotNullObject(hm.get("RESOURCE_TYPE")));
				ps.setObject(3,SynDataUtil.toNotNullObject(hm.get("RESOURCE_ID")));
				ps.setObject(4,SynDataUtil.toNotNullObject(hm.get("RESOURCE_NAME")));
				ps.setObject(5,SynDataUtil.toNotNullObject(hm.get("PARENT_ID")));
				ps.setObject(6,SynDataUtil.toNotNullObject(hm.get("RESOURCE_ORDER")));
				ps.setObject(7,SynDataUtil.toNotNullObject(hm.get("RESOURCE_PATH")));
				ps.setObject(8,SynDataUtil.toNotNullObject(hm.get("RESOURCE_SHOW_PATH")));
				ps.setObject(9,SynDataUtil.toBigDecimalObject(hm.get("RESOURCE_STENCIL"), new BigDecimal(1)));
				ps.setObject(10,SynDataUtil.toNotNullObject(hm.get("STATE")));
				ps.setObject(11,SynDataUtil.toNotNullObject(hm.get("TEMPLATE_ID")));
				ps.setObject(12,SynDataUtil.toNotNullObject(hm.get("PRODUCT_ID")));
				ps.setObject(13,SynDataUtil.toNotNullObject(hm.get("IS_ADV")));
				ps.setObject(14,SynDataUtil.toNotNullObject(hm.get("BUSINESS_TYPE")));
				ps.setObject(15,SynDataUtil.toNotNullObject(hm.get("IS_FOCUS")));
				ps.setObject(16,SynDataUtil.toNotNullObject(hm.get("IS_COMMEND")));
				ps.setObject(17,SynDataUtil.toDateObject(hm.get("CREATE_TIME")));
            	ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
            ps = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ps = null;
			}
		}
	}
	
	
	
	
	/**
	 * 新增资产信息
	 * @param insertList
	 * @param adsConn
	 */
	private void insertAssetinfo(List<HashMap> insertList,Connection adsConn){
		if(insertList == null || insertList.size()<=0){
			return;
		}
		PreparedStatement ps = null;
		String 	sql = "insert into T_ASSETINFO("
			+ "ID,"
			+ "ASSET_ID,"
			+ "ASSET_NAME,"
			+ "SHORT_NAME,"
			+ "TITLE,"
			+ "YEAR,"
			+ "KEYWORD,"
			+ "RATING,"
			+ "RUNTIME,"
			+ "IS_PACKAGE,"
			+ "ASSET_CREATE_TIME,"
			+ "DISPLAY_RUNTIME,"
			+ "ASSET_DESC,"
			+ "POSTER_URL,"
			+ "PREVIEW_ASSET_ID,"
			+ "PREVIEW_ASSET_NAME,"
			+ "PREVIEW_RUNTIME,"
			+ "VIDEO_CODE,"
			+ "VIDEO_RESOLUTION,"
			+ "DIRECTOR,"
			+ "ACTOR,"
			+ "CREATE_TIME,"
			+ "MODIFY_TIME,"
			+ "STATE,"
			+ "CATEGORY,"
			+ "SCORE) "
			+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {	
            ps = adsConn.prepareStatement(sql);
            for(HashMap hm : insertList){
            	ps.setObject(1, SynDataUtil.toNotNullObject(hm.get("ID")));
				ps.setObject(2, SynDataUtil.toNotNullObject(hm.get("CONTENT_CODE")));
				ps.setObject(3, SynDataUtil.toNotNullObject(hm.get("TITLE")));
				ps.setObject(4, SynDataUtil.toNotNullObject(hm.get("SUMMARY_SHORT")));
				ps.setObject(5, SynDataUtil.toNotNullObject(hm.get("TITLE")));
				ps.setObject(6, SynDataUtil.toNotNullObject(hm.get("YEAR")));
				ps.setObject(7, "");
				ps.setObject(8, SynDataUtil.toNotNullObject(hm.get("RATING")));
				ps.setObject(9, SynDataUtil.toNotNullObject(hm.get("RUN_TIME")));
				ps.setObject(10, SynDataUtil.toNotNullObject(hm.get("ISPACKAGE")));
				ps.setObject(11, SynDataUtil.toDateObject(hm.get("ASSETCREATETIME")));
				ps.setObject(12, SynDataUtil.toNotNullObject(hm.get("DISPLAY_RUN_TIME")));
				ps.setObject(13, SynDataUtil.toNotNullObject(hm.get("ASSETDESC")));
				ps.setObject(14, SynDataUtil.toNotNullObject(hm.get("POSTER_NAME")));
				ps.setObject(15, SynDataUtil.toNotNullObject(hm.get("PREVIEWASSETID")));
				ps.setObject(16,
						SynDataUtil.toNotNullObject(hm.get("PREVIEWASSETNAME")));
				ps.setObject(17, SynDataUtil.toNotNullObject(hm.get("PREVIEWRUNTIME")));
				ps.setObject(18, SynDataUtil.toNotNullObject(hm.get("VIDEO_CODE")));
				ps.setObject(19,
						SynDataUtil.toNotNullObject(hm.get("VIDEO_RESOLUTION")));
				ps.setObject(20, SynDataUtil.toNotNullObject(hm.get("DIRECTOR")));
				ps.setObject(21, SynDataUtil.toNotNullObject(hm.get("ACTORS")));
				ps.setObject(22, SynDataUtil.toDateObject(hm.get("CREATETIME")));
				ps.setObject(23, SynDataUtil.toDateObject(hm.get("MODIFYTIME")));
				ps.setObject(24, SynDataUtil.toNotNullObject(hm.get("STATUS")));
				ps.setObject(25, SynDataUtil.toDateObject(hm.get("CATEGORY")));
				ps.setObject(26, SynDataUtil.toBigDecimalObject(hm.get("SCORE"),new BigDecimal(5.0)));
            	ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
            ps = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ps = null;
			}
		}
	}
	/**
	 * 删除资产信息
	 * @param adsConn
	 */
	private void deleteAssetinfo(Connection adsConn){
		
		PreparedStatement ps = null;
		String 	sql = "delete from T_ASSETINFO";
		

		try {	
            ps = adsConn.prepareStatement(sql);
          
            ps.execute();
            ps.close();
            ps = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ps = null;
			}
		}
	}

	/**
	 * 删除栏目信息
	 * @param adsConn
	 */
private void deleteCategoryInfo(Connection adsConn){
	
	PreparedStatement ps = null;
	String 	sql = "delete from T_Syn_CPS_CATEGORY";
	

	try {	
        ps = adsConn.prepareStatement(sql);
      
        ps.execute();
        ps.close();
        ps = null;
	} catch (SQLException e) {
		e.printStackTrace();
	}finally{
		if(ps != null){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ps = null;
		}
	}
}


	
	public void synAssetInfo() {
		System.out.println("synAssetInfo query start "+new Date());
		Connection cpsConn = SynDataUtil.getCPSConnection();
		if (cpsConn==null)
		{
			return ;
		}
		List<HashMap> list = this.getCpsAssetInfo(cpsConn);
		Connection adsConn = SynDataUtil.getADSConnection();
//		if(list != null && list.size()>0){
//			List<HashMap> insertList = new ArrayList<HashMap>();
//			List<HashMap> updateList = new ArrayList<HashMap>();
//			
//			for(HashMap map : list){
//				this.saveAssetInfo(map,insertList,updateList,adsConn);
//			}
		 this.deleteAssetinfo(adsConn);
	     this.insertAssetinfo(list, adsConn);
			
//		}
		
		if(cpsConn != null){
			try {
				cpsConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(adsConn != null){
			try {
				adsConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("synAssetInfo save end "+new Date());
		
	}	

	/**
	 * 调用存储过程完成将回看栏目放回给回看栏目表
	 */
	public void synLookBackCategory() {
		System.out.println("synLookBackCategory query start "+new Date());
		Connection adsConn = null;
		CallableStatement  cs = null;
		try {
			adsConn = SynDataUtil.getADSConnection();
			cs = adsConn.prepareCall("call synLookBackCategoryProcedure(?)");
			cs.setString(1, "节目回看");
			cs.execute(); 	
	
		} catch (Exception e) {
			
			if (logger.isErrorEnabled()) {
				logger.error("执行存储过程失败", e);
			}
		}finally {
			try {
			
				cs.close();
				adsConn.close();
			} catch (SQLException e) {

			}

		}
		System.out.println("synLookBackCategory end "+new Date());
	}
	/**
	 * 通过栏目定义节目分类
	 */
	public void synAssetCategory() {
		System.out.println("synAssetCategory query start "+new Date());
		Connection adsConn = null;
		CallableStatement  cs = null;
		try {
			adsConn = SynDataUtil.getADSConnection();
			cs = adsConn.prepareCall("call synCategory()");
			cs.execute(); 	
	
		} catch (Exception e) {
			
			e.printStackTrace();
			if (logger.isErrorEnabled()) {
				logger.debug("获取CPS数据库链接失败", e);
			}
		}finally {
			try {
			
				cs.close();
				adsConn.close();
			} catch (SQLException e) {

			}

		}
		System.out.println("synAssetCategory end "+new Date());
	}
	public void synAllCPSCategoryInfo() {
		System.out.println("synAllCPSCategoryInfo query start "+new Date());
		Connection cpsConn = SynDataUtil.getCPSConnection();
		if (cpsConn==null)
		{
			return ;
		}
		List<HashMap> list = this.getCpsCategoryInfo(cpsConn);
		Connection adsConn = SynDataUtil.getADSConnection();
//		if(list != null && list.size()>0){
//			List<HashMap> insertList = new ArrayList<HashMap>();
//			List<HashMap> updateList = new ArrayList<HashMap>();
//			
//			for(HashMap map : list){
//				this.saveCategoryInfo(map,insertList,updateList,adsConn);
//			}
			this.deleteCategoryInfo( adsConn);
			this.insertCategory(list, adsConn);
//		}
		
		if(cpsConn != null){
			try {
				cpsConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(adsConn != null){
			try {
				adsConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("synAllCPSCategoryInfo save end "+new Date());
		
	}
    public void synCategoryInfo() {
		
		this.synAllCPSCategoryInfo();
		synLookBackCategory();	

	}

	
	
	public static void main(String args[]) {
		SynCpsData syn = new SynCpsData();
		//syn.synCategoryInfo();
		//syn.synLookBackCategory();
//		syn.synProductInfo();
		//同步资源服务器
//		syn.synAssetInfo();
		//同步栏目信息，回看栏目信息及节目分类
		syn.synCategoryInfo();
		
		
	}
}
