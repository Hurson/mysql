package cn.com.avit.ads.syndata;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.avit.common.util.StringUtil;

public class SynMMSPData {
	public final Log logger = LogFactory.getLog(getClass());


	/**
	 * 通过TVN号取用户信息
	 * 
	 * @param tvn
	 * @return
	 */
	public HashMap getUserInfoByTvn(String tvn) {
		Connection bsmpConn = null;
		HashMap map = new HashMap(1);
		ResultSet rs = null;
		try {
			bsmpConn = SynDataUtil.getBsmpConnection();
			rs = bsmpConn.prepareStatement(
					"select * from t_user where usersn=" + tvn).executeQuery();
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();

				int count = rsmd.getColumnCount();
				String[] name = new String[count];

				for (int i = 0; i < count; i++) {

					name[i] = rsmd.getColumnName(i + 1);
					map.put(name[i], rs.getObject(name[i]));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			if (logger.isErrorEnabled()) {
				logger.debug("获取CPS数据库链接失败", e);
			}
		} finally {
			try {

				rs.close();
				bsmpConn.close();
			} catch (SQLException e) {

			}

		}

		return map;
	}

	
	public List getBsmpLocationCode(Connection cpsConn){
		List<HashMap> results = new ArrayList<HashMap>();
		ResultSet rs = null;
		try {
			rs = cpsConn.prepareStatement(
			"select * from t_location_detail").executeQuery();
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
	
	public void saveLocationCode(HashMap hm,List<HashMap> insertList,List<HashMap> updateList,Connection adsConn){
		PreparedStatement countps = null;
		ResultSet rs = null;
		int count = 0;
		if (adsConn != null) {
		try {
			countps  = adsConn.prepareStatement(
			"select count(1) from t_productinfo where  id =?");
			countps.setObject(1, hm.get("ID"));
			rs = countps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			if (count == 0) {
				insertList.add(hm);
			} else {
				updateList.add(hm);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			if (logger.isErrorEnabled()) {
				logger.debug("获取CPS数据库链接失败", e);
			}
		}finally {
			try {
				countps.close();
				rs.close();
			} catch (SQLException e) {

			}

		}
		}else {
			logger.error("get ads connection error");
		}
	}	
	/**
	 * 修改分类信息
	 * @param updateList
	 * @param adsConn
	 */
	private void updateLocationCode(List<HashMap> updateList,Connection adsConn) {
		
		if(updateList == null || updateList.size()<=0){
			return;
		}
		PreparedStatement ps = null;
		String 				sql = "update  t_location_code set LOCATIONCODE=?,LOCATIONNAME=?,PARENTLOCATION=?,LOCATIONTYPE=? where LOCATIONCODE = ?";
		try {	
            ps = adsConn.prepareStatement(sql);
            for(HashMap hm : updateList){
            	ps.setObject(1,
						SynDataUtil.toNotNullObject(hm.get("LOCATIONCODE")));
				ps.setObject(2,
						SynDataUtil.toNotNullObject(hm.get("LOCATIONNAME")));
				ps.setObject(3, SynDataUtil.toNotNullObject(hm
						.get("PARENTLOCATION")));
				ps.setObject(4,
						SynDataUtil.toNotNullObject(hm.get("LOCATIONTYPE")));

				ps.setObject(5,
						SynDataUtil.toNotNullObject(hm.get("LOCATIONCODE")));
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
	 * 新增分类信息
	 * @param insertList
	 * @param adsConn
	 */
	private void insertLocationCode(List<HashMap> insertList,Connection adsConn){
		if(insertList == null || insertList.size()<=0){
			return;
		}
		PreparedStatement ps = null;
		String 	sql = "insert into t_location_code(LOCATIONCODE,LOCATIONNAME,PARENTLOCATION,LOCATIONTYPE) VALUES(?,?,?,?)";
		try {	
            ps = adsConn.prepareStatement(sql);
            for(HashMap hm : insertList){
            	ps.setObject(1,
						SynDataUtil.toNotNullObject(hm.get("LOCATION_CODE")));
				ps.setObject(2,
						SynDataUtil.toNotNullObject(hm.get("LOCATION_NAME")));
				ps.setObject(3, SynDataUtil.toNotNullObject(hm
						.get("PARENT_LOCATION")));
				ps.setObject(4,
						SynDataUtil.toNotNullObject(hm.get("LOCATION_TYPE")));

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
	 * 同步区域码信息
	 */
	


	public void synLocationCode() {
		System.out.println("synLocationCode query start "+new Date());
		Connection bsmpConn = SynDataUtil.getBMSConnection();
		if (bsmpConn==null)
		{
			logger.error("获取BSMP数据库链接失败");
			return ;
		}
		List<HashMap> list = this.getBsmpLocationCode(bsmpConn);
		Connection adsConn = SynDataUtil.getADSConnection();
//		if(list != null && list.size()>0){
//			List<HashMap> insertList = new ArrayList<HashMap>();
//			List<HashMap> updateList = new ArrayList<HashMap>();
//			
//			for(HashMap map : list){
//				this.saveLocationCode(map,insertList,updateList,adsConn);
//			}
		    this.delteLocationCode(adsConn);
			this.insertLocationCode(list, adsConn);
		
//		}
		
		if(bsmpConn != null){
			try {
				bsmpConn.close();
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
		System.out.println("synLocationCode save end "+new Date());
		
		
	}
private void delteLocationCode(Connection adsConn) {
		
		
		PreparedStatement ps = null;
		String 				sql = "delete from   t_location_code ";
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
	public List getBsmpUser(Connection cpsConn,int pagesize,int pageNumber){
		List<HashMap> results = new ArrayList<HashMap>();
		ResultSet rs = null;
		try {
			int startindex,endindex;
			startindex=pagesize*pageNumber;
			endindex  =(pageNumber+1)*pagesize+1;
			String sql ="select t2.* "+
				" from (select rownum r,t1.USERID,t1.USERSN,t1.LOCATIONCODEVALUE,t1.USERLEVEL,t1.INDUSTRYCATEGORY from t_user t1 where rownum<"+endindex+" order by t1.USERID) t2"+
				" where t2.r>" +startindex;
			//sql ="select t1.USERID,t1.USERSN,t1.LOCATIONCODEVALUE,t1.USERLEVEL,t1.INDUSTRYCATEGORY from t_user t1  order by t1.USERID ";
			rs = cpsConn.prepareStatement(sql).executeQuery();
	  
			while (rs.next()) {
				HashMap map = new HashMap();
				map.put("USERID", rs.getObject("USERID"));
				map.put("USERSN", rs.getObject("USERSN"));
				map.put("LOCATIONCODEVALUE", rs.getObject("LOCATIONCODEVALUE"));
				map.put("USERLEVEL", rs.getObject("USERLEVEL"));
				map.put("INDUSTRYCATEGORY", rs.getObject("INDUSTRYCATEGORY"));				
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
	/**
	 * 新增分类信息
	 * @param insertList
	 * @param adsConn
	 */
	private void insertUser(List<HashMap> insertList,Connection adsConn){
		if(insertList == null || insertList.size()<=0){
			return;
		}
		PreparedStatement ps = null;
		int addbatchSize=0;
		String 	sql = "insert into t_bsmp_user(userid,usersn,LOCATIONCODEVALUE,USERLEVEL,INDUSTRYCATEGORY) VALUES(?,?,?,?,?)";
		try {	
			adsConn.setAutoCommit(false);
			ps = adsConn.prepareStatement(sql);
	        for(HashMap hm : insertList){
	        	ps.setObject(1,
						SynDataUtil.toNotNullObject(hm.get("USERID")));
				ps.setObject(2,
						SynDataUtil.toNotNullObject(hm.get("USERSN")));
				ps.setObject(3, SynDataUtil.toNotNullObject(hm
						.get("LOCATIONCODEVALUE")));
				ps.setObject(4,
						SynDataUtil.toNotNullObject(hm.get("USERLEVEL")));
				ps.setObject(5,
						SynDataUtil.toNotNullObject(hm.get("INDUSTRYCATEGORY")));
	        	ps.addBatch();
	        	addbatchSize++;
	        	
	        	if (addbatchSize%100000==0)
	        	{
	        		 ps.executeBatch();
	        		 ps.clearBatch();
	        	}
	        	if (addbatchSize%100000==0)
	        	{
	        		adsConn.commit();
	        	}
	        }
	        ps.executeBatch();
	        adsConn.commit();
	        adsConn.setAutoCommit(true);
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
	private void delteUser(Connection adsConn) {
		
		
		PreparedStatement ps = null;
		String 				sql = "delete from   t_bsmp_user ";
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
    public void synUser()
    {
    	logger.info("synUser query start "+new Date());
		Connection bsmpConn = SynDataUtil.getBMSConnection();
		if (bsmpConn==null)
		{
			logger.error("获取BSMP数据库链接失败");
			return ;
		}
		int pageSize=200000;
		int pageNumber=0;
	//	List<HashMap> list = this.getBsmpUser(bsmpConn,pageSize,pageNumber);
		Connection adsConn = SynDataUtil.getADSConnection();
		 this.delteUser(adsConn);
//		while (list!=null && list.size()>0)
//		{
//		//		if(list != null && list.size()>0){
////			List<HashMap> insertList = new ArrayList<HashMap>();
////			List<HashMap> updateList = new ArrayList<HashMap>();
////			
////			for(HashMap map : list){
////				this.saveLocationCode(map,insertList,updateList,adsConn);
////			}
//			this.insertUser(list, adsConn);
//			
//			pageNumber++;
//			list = this.getBsmpUser(bsmpConn,pageSize,pageNumber);
//			System.out.println("pageNumber:"+pageNumber);
//		}
		 ResultSet rs = null;
		 PreparedStatement ps = null;
			int addbatchSize=0;
			try {
				int startindex,endindex;
				int rowcount=0;
				String sqlcount ="select count(*) count from t_user ";
				String sql ;
				rs = bsmpConn.prepareStatement(sqlcount).executeQuery();
				if (rs.next())
				{
					rowcount = StringUtil.toInt(rs.getObject("COUNT"));
				}
				adsConn.setAutoCommit(false);
				startindex=pageSize*pageNumber;
				endindex  =(pageSize)*(pageNumber+1)+1;
				
				
				String 	insertsql = "insert into t_bsmp_user(userid,usersn,LOCATIONCODEVALUE,USERLEVEL,INDUSTRYCATEGORY,LOCATIONCODE) VALUES(?,?,?,?,?,?)";
				ps = adsConn.prepareStatement(insertsql);
			//	while (startindex<=rowcount)
				{
				    sql ="select t2.* "+
					" from (select rownum r,t1.USERID,t1.USERSN,t1.LOCATIONCODEVALUE,t1.USERLEVEL,t1.INDUSTRYCATEGORY,t1.LOCATIONCODE from t_user t1 where rownum<"+endindex+" order by t1.USERID) t2"+
					" where t2.r>" +startindex;
				    sql = "select rownum r,t1.ID,t1.USER_SN,t1.LOCATION_DETAIL_CODE,nvl(t1.USER_LEVEL,'0') USER_LEVEL,nvl(t1.INDUSTRY_CATEGORY,'0') INDUSTRY_CATEGORY,t1.LOCATION_CODE from t_user t1";
					 pageNumber++;
				    startindex=pageSize*pageNumber;
				    endindex  =(pageSize)*(pageNumber+1)+1;
					//logger.info("pageNumber:"+pageNumber);
				    rs = bsmpConn.prepareStatement(sql).executeQuery();
				    while (rs.next()) {
				    	ps.setObject(1,
								SynDataUtil.toNotNullObject(rs.getObject("ID")));
						ps.setObject(2, SynDataUtil.toNotNullObject(rs
								.getObject("USER_SN")));
						ps.setObject(3, SynDataUtil.toNotNullObject(rs
								.getObject("LOCATION_DETAIL_CODE")));
						ps.setObject(4, SynDataUtil.toNotNullObject(rs
								.getObject("USER_LEVEL")));
						ps.setObject(5, SynDataUtil.toNotNullObject(rs
								.getObject("INDUSTRY_CATEGORY")));
						ps.setObject(6, SynDataUtil.toNotNullObject(rs
								.getObject("LOCATION_CODE")));
				        	ps.addBatch();
				        	addbatchSize++;
				        	
				        	if (addbatchSize%100000==0)
				        	{
				        		 ps.executeBatch();
				        		 ps.clearBatch();
				        	}
				        	if (addbatchSize%100000==0)
				        	{
				        		adsConn.commit();
				        	}
				        }
				   }
				    ps.executeBatch();
			        adsConn.commit();
			        adsConn.setAutoCommit(true);
			        ps.close();
			        ps = null;
			} catch (Exception e) {
				e.printStackTrace();
				if (logger.isErrorEnabled()) {
					logger.debug("synUser失败", e);
				}
			}finally {
				try {
					if (rs!=null){
						rs.close();
					}
				} catch (SQLException e) {
		
				}
				if(ps != null){
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					ps = null;
				}
			}
		if(bsmpConn != null){
			try {
				bsmpConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(adsConn != null){
			try {
				adsConn.setAutoCommit(true);
				adsConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("synUser save end "+new Date());
		
    }
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
            int id=0;
            for(HashMap hm : insertList){
            	ps.setObject(1, SynDataUtil.toNotNullObject(id++));
				ps.setObject(2, SynDataUtil.toNotNullObject(hm.get("ASSET_ID")));
				ps.setObject(3, SynDataUtil.toNotNullObject(hm.get("ASSETNAME")));
				ps.setObject(4, SynDataUtil.toNotNullObject(hm.get("ASSETNAME")));
				ps.setObject(5, SynDataUtil.toNotNullObject(hm.get("TITLE")));
				ps.setObject(6, SynDataUtil.toNotNullObject(hm.get("YEAR")));
				ps.setObject(7, SynDataUtil.toNotNullObject(hm.get("KEYWORD")));
				ps.setObject(8, SynDataUtil.toNotNullObject(hm.get("RATING")));
				ps.setObject(9, SynDataUtil.toNotNullObject(hm.get("RUNTIME")));
				ps.setObject(10, SynDataUtil.toNotNullObject("0"));//ISPACKAGE
				ps.setObject(11, SynDataUtil.toDateObject(hm.get("CREATETIME")));
				ps.setObject(12, SynDataUtil.toNotNullObject(hm.get("DISPLAYRUNTIME")));
				ps.setObject(13, SynDataUtil.toNotNullObject(hm.get("ASSETDESC")));
				ps.setObject(14, SynDataUtil.toNotNullObject(hm.get("POSTERURL")));
				
				ps.setObject(15, SynDataUtil.toNotNullObject(""));
				ps.setObject(16,SynDataUtil.toNotNullObject(""));
				ps.setObject(17, SynDataUtil.toNotNullObject(""));
				ps.setObject(18, SynDataUtil.toNotNullObject(""));
				ps.setObject(19,SynDataUtil.toNotNullObject(""));
				
				ps.setObject(20, SynDataUtil.toNotNullObject(hm.get("DIRECTOR")));
				ps.setObject(21, SynDataUtil.toNotNullObject(hm.get("ACTOR")));
				ps.setObject(22, SynDataUtil.toDateObject(hm.get("CREATETIME")));
				ps.setObject(23, SynDataUtil.toDateObject(hm.get("MODIFYTIME")));
				ps.setObject(24, SynDataUtil.toNotNullObject(hm.get("STATE")));
				ps.setObject(25, SynDataUtil.toDateObject(hm.get("CATEGORY")));
				ps.setObject(26, SynDataUtil.toBigDecimalObject(new BigDecimal(5.0),new BigDecimal(5.0)));
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
	public List getCpsAssetInfo(Connection cpsConn){
		List<HashMap> results = new ArrayList<HashMap>();
		ResultSet rs = null;
		try {
			rs = cpsConn.prepareStatement(
			"select * from T_ASSET_TRANSITION").executeQuery();
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
	
    public void synAssetInfo() {
		System.out.println("synAssetInfo query start "+new Date());
		Connection cpsConn = SynDataUtil.getMMSPConnection();
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

	public List getCpsCategoryInfo(Connection cpsConn){
		List<HashMap> results = new ArrayList<HashMap>();
		ResultSet rs = null;
		String treeids="5246578,5246538";
		try {
			InputStream in = null;
			in = new FileInputStream(new File(SynDataUtil.class
					.getClassLoader().getResource("system.properties").getFile()));
			Properties p = new Properties();
			p.load(in);
			treeids=p.getProperty("mmsp.category_treeids");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			rs = cpsConn.prepareStatement(
			"select * from T_CATEGORY t where t.tree_id in ("+treeids+")").executeQuery();
			//InitConfig.getConfigMap().get("TREE_IDS");
			// select * from T_CATEGORY t where t.tree_id in (5246538,5246578)
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
    public void synAllCPSCategoryInfo() {
		System.out.println("synAllCPSCategoryInfo query start "+new Date());
		Connection cpsConn = SynDataUtil.getMMSPConnection();
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
	public List getNpvrChannel(Connection cpsConn){
		List<HashMap> results = new ArrayList<HashMap>();
		ResultSet rs = null;
		try {
			rs = cpsConn.prepareStatement(
			"select * from t_channel t where t.servicecode  like '%VOD%' ").executeQuery();
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
				logger.debug("获取EPG数据库链接失败", e);
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
	/**
	 * 
	 * @param insertList
	 * @param adsConn
	 */
	private void insertNpvrChannel(List<HashMap> insertList,Connection adsConn){
		if(insertList == null || insertList.size()<=0){
			return;
		}
		PreparedStatement ps = null;
		String 				sql = "insert into t_channelinfo_npvr(CHANNEL_ID,CHANNEL_CODE,CHANNEL_NAME,SERVICE_ID,LOCATION_CODE,SUMMARYSHORT) VALUES(?,?,?,?,?,?)";
		try {	
            ps = adsConn.prepareStatement(sql);
            for(HashMap hm : insertList){
            	ps.setObject(1,
						SynDataUtil.toNotNullObject(hm.get("ID")));
				ps.setObject(2,
						SynDataUtil.toNotNullObject(hm.get("CHANNELCODE")));
				ps.setObject(3,
						SynDataUtil.toNotNullObject(hm.get("CAPTION")));
				ps.setObject(4,
						SynDataUtil.toNotNullObject(hm.get("SERVICEID")));
				ps.setObject(5,
						SynDataUtil.toNotNullObject(hm.get("LOCATIONCODE")));
				ps.setObject(6,
						SynDataUtil.toNotNullObject(hm.get("CHANNELCODE")));

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
	public void synNpvrChannel() {
		System.out.println("synNpvrChannel query start "+new Date());
		Connection nPvrConn = SynDataUtil.getMMSPConnection();
		List<HashMap> list = this.getNpvrChannel(nPvrConn);
		if (nPvrConn==null)
		{
			return ;
		}
		Connection adsConn = SynDataUtil.getADSConnection();
//		if(list != null && list.size()>0){
//			List<HashMap> insertList = new ArrayList<HashMap>();
//			List<HashMap> updateList = new ArrayList<HashMap>();
//			
//			for(HashMap map : list){
//				this.saveNpvrChannel(map,insertList,updateList,adsConn);
//			}
	    	this.deleteNpvrChannel(adsConn);
			this.insertNpvrChannel(list, adsConn);
			
//		}
		
		if(nPvrConn != null){
			try {
				nPvrConn.close();
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
		System.out.println("synNpvrChannel save end "+new Date());
		
		
	}
private void deleteNpvrChannel(Connection adsConn){
		
		PreparedStatement ps = null;
		String 	sql = "delete from t_channelinfo_npvr";
		

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
 * 获取bms的产品信息
 * 
 * @param cpsConn
 * @return
 */
public List getBMSProductInfo(Connection bmsConn) {
	List<HashMap> results = new ArrayList<HashMap>();
	ResultSet rs = null;
	try {
		String sqlStr ="select b.id               ID,"
	       +" b.product_code     PRODUCT_CODE,"
	       +" b.product_name     PRODUCT_NAME,"
	       +" b.provider_id      OP_CODE,"
	       +" b.business_id      BIZ_ID,"
	       +" b.status           STATE"
	  +" from  t_product b"
	 +" where b.product_code in ("
				+ SynDataUtil.getBmsproductoffering() + ")";

		rs = bmsConn.prepareStatement(sqlStr).executeQuery();
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
			logger.debug("获取bms数据库链接失败", e);
		}
	} finally {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {

		}

	}

	return results;
}

/**
 * 新增产品信息
 * 
 * @param insertList
 * @param adsConn
 */
private void insertProductInfo(List<HashMap> insertList, Connection adsConn) {
	if (insertList == null || insertList.size() <= 0) {
		return;
	}
	PreparedStatement ps = null;
	String sql = "insert into t_productinfo(" + "ID," + "PRODUCT_ID,"
			+ "PRODUCT_NAME," + "SP_ID," + "TYPE," + "BIZ_ID," + "STATE) "
			+ " VALUES(?,?,?,?,?,?,?)";
	try {
		ps = adsConn.prepareStatement(sql);
		for (HashMap hm : insertList) {
			ps.setObject(1, SynDataUtil.toNotNullObject(hm.get("ID")));
			ps.setObject(2,
					SynDataUtil.toNotNullObject(hm.get("PRODUCT_CODE")));
			ps.setObject(3,
					SynDataUtil.toNotNullObject(hm.get("PRODUCT_NAME")));
			ps.setObject(4, SynDataUtil.toNotNullObject(hm.get("OP_CODE")));
			ps.setObject(5, SynDataUtil.toNotNullObject(hm.get("TYPE")));			
		//	ps.setObject(6,
			//		SynDataUtil.toNotNullObject(hm.get("BUSINESS_ID")));
			ps.setObject(6,
					SynDataUtil.toNotNullObject(hm.get("PRODUCT_CODE")));
			ps.setObject(7, SynDataUtil.toNotNullObject(hm.get("STATE")));
			ps.addBatch();
		}
		ps.executeBatch();
		ps.close();
		ps = null;
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		if (ps != null) {
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
 * 删除产品信息
 * 
 * @param adsConn
 */
private void deleteProductfo(Connection adsConn) {

	PreparedStatement ps = null;
	String sql = "delete from t_productinfo";

	try {
		ps = adsConn.prepareStatement(sql);

		ps.execute();
		ps.close();
		ps = null;
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ps = null;
		}
	}
}

public void synProductInfo() {
	System.out.println("synProductInfo query start " + new Date());
	Connection bmsConn = SynDataUtil.getBMSConnection();
	if (bmsConn == null) {
		return;
	}
	List<HashMap> list = this.getBMSProductInfo(bmsConn);
	Connection adsConn = SynDataUtil.getADSConnection();
	// if(list != null && list.size()>0){
	// List<HashMap> insertList = new ArrayList<HashMap>();
	// List<HashMap> updateList = new ArrayList<HashMap>();
	//
	// for(HashMap map : list){
	// this.saveProductInfo(map,insertList,updateList,adsConn);
	// }
	this.deleteProductfo(adsConn);
	this.insertProductInfo(list, adsConn);

	// }
	this.updateProductType(adsConn);
	if (bmsConn != null) {
		try {
			bmsConn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	if (adsConn != null) {
		try {
			adsConn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	System.out.println("synProductInfo save end " + new Date());

}

public void updateProductType(Connection adsConn) {
	String sql = "update t_productinfo t set t.TYPE='lookback'  where t.PRODUCT_NAME='节目回看'";
	String npvrsql = "update t_productinfo t set t.TYPE='npvr'  where t.PRODUCT_NAME='频道回看'";
	String vodsql = "update t_productinfo t set t.TYPE='vod'  where  t.PRODUCT_NAME!='节目回看' and t.PRODUCT_NAME!='频道回看'";

	PreparedStatement ps = null;
	try {
		ps = adsConn.prepareStatement(sql);

		ps.execute();
		ps = adsConn.prepareStatement(npvrsql);

		ps.execute();
		ps = adsConn.prepareStatement(vodsql);

		ps.execute();
		ps.close();
		ps = null;
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ps = null;
		}
	}

}
	
	public void synData() {
		//从 BMS同步
		this.synUser();
		//同步区域信息 Portal无区域信息  可否从BMS同步
		this.synLocationCode();
		//同步产品信息
		//this.synProductInfo();
		
		//以下从Portal同步
		//同步影片信息
		this.synAssetInfo();	
		//同步栏目上架数据
		this.synAllCPSCategoryInfo();
		//执行栏目存储过程
		this.synAssetCategory();
		//执行回看栏目存储过程
		this.synLookBackCategory();
		//同步回放频道
		this.synNpvrChannel();
	}
	public static void main(String args[]) {
		SynMMSPData syn = new SynMMSPData();
		syn.getUserInfoByTvn("22960786");
		syn.synLocationCode();
	}
}
