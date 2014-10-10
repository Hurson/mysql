package cn.com.avit.ads.syndata;

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

import com.avit.common.util.StringUtil;

public class SynBsmpData {
	public final Log logger = LogFactory.getLog(getClass());

	/**
	 * 通过TVN号取用户信息
	 * 
	 * @param tvn
	 * @return
	 */

	public List getBsmpLocationCode(Connection cpsConn) {
		List<HashMap> results = new ArrayList<HashMap>();
		ResultSet rs = null;
		try {
			rs = cpsConn.prepareStatement("select * from t_location_detail")
					.executeQuery();
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
				ps.setObject(6,
						SynDataUtil.toNotNullObject(hm.get("BUSINESS_ID")));
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
		Connection bmsConn = SynDataUtil.getBsmpConnection();
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

	/**
	 * 新增地区信息
	 * 
	 * @param insertList
	 * @param adsConn
	 */
	private void insertLocationCode(List<HashMap> insertList, Connection adsConn) {
		if (insertList == null || insertList.size() <= 0) {
			return;
		}
		PreparedStatement ps = null;
		String sql = "insert into t_location_code(LOCATIONCODE,LOCATIONNAME,PARENTLOCATION,LOCATIONTYPE) VALUES(?,?,?,?)";
		try {
			ps = adsConn.prepareStatement(sql);
			for (HashMap hm : insertList) {
				ps.setObject(1,
						SynDataUtil.toNotNullObject(hm.get("LOCATION_CODE")));
				ps.setObject(2,
						SynDataUtil.toNotNullObject(hm.get("LOCATION_NAME")));
				ps.setObject(3,
						SynDataUtil.toNotNullObject(hm.get("PARENT_LOCATION")));
				ps.setObject(4,
						SynDataUtil.toNotNullObject(hm.get("LOCATION_TYPE")));

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
	 * 同步区域码信息
	 */

	public void synLocationCode() {
		System.out.println("synLocationCode query start " + new Date());
		Connection bsmpConn = SynDataUtil.getBsmpConnection();
		if (bsmpConn == null) {
			logger.error("获取BSMP数据库链接失败");
			return;
		}
		List<HashMap> list = this.getBsmpLocationCode(bsmpConn);
		Connection adsConn = SynDataUtil.getADSConnection();
		// if(list != null && list.size()>0){
		// List<HashMap> insertList = new ArrayList<HashMap>();
		// List<HashMap> updateList = new ArrayList<HashMap>();
		//
		// for(HashMap map : list){
		// this.saveLocationCode(map,insertList,updateList,adsConn);
		// }
		this.delteLocationCode(adsConn);
		this.insertLocationCode(list, adsConn);

		// }

		if (bsmpConn != null) {
			try {
				bsmpConn.close();
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
		System.out.println("synLocationCode save end " + new Date());

	}

	private void delteLocationCode(Connection adsConn) {

		PreparedStatement ps = null;
		String sql = "delete from   t_location_code ";
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

	public List getBsmpUser(Connection cpsConn, int pagesize, int pageNumber) {
		List<HashMap> results = new ArrayList<HashMap>();
		ResultSet rs = null;
		try {
			int startindex, endindex;
			startindex = pagesize * pageNumber;
			endindex = (pageNumber + 1) * pagesize + 1;
			String sql = "select t2.* "
					+ " from (select rownum r,t1.ID,t1.USER_SN,t1.LOCATION_DETAIL_CODE,t1.USER_LEVEL,t1.INDUSTRYCATEGORY from t_user t1 where rownum<"
					+ endindex + " order by t1.ID) t2" + " where t2.r>"
					+ startindex;
			// sql
			// ="select t1.USERID,t1.USERSN,t1.LOCATIONCODEVALUE,t1.USERLEVEL,t1.INDUSTRYCATEGORY from t_user t1  order by t1.USERID ";
			rs = cpsConn.prepareStatement(sql).executeQuery();

			while (rs.next()) {
				HashMap map = new HashMap();
				map.put("USERID", rs.getObject("ID"));
				map.put("USERSN", rs.getObject("USER_SN"));
				map.put("LOCATIONCODEVALUE",
						rs.getObject("LOCATION_DETAIL_CODE"));
				map.put("USERLEVEL", rs.getObject("USER_LEVEL"));
				map.put("INDUSTRYCATEGORY", rs.getObject("INDUSTRY_CATEGORY"));
				results.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (logger.isErrorEnabled()) {
				logger.debug("获取CPS数据库链接失败", e);
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
	 * 新增分类信息
	 * 
	 * @param insertList
	 * @param adsConn
	 */
	private void insertUser(List<HashMap> insertList, Connection adsConn) {
		if (insertList == null || insertList.size() <= 0) {
			return;
		}
		PreparedStatement ps = null;
		int addbatchSize = 0;
		String sql = "insert into t_bsmp_user(userid,usersn,LOCATIONCODEVALUE,USERLEVEL,INDUSTRYCATEGORY) VALUES(?,?,?,?,?)";
		try {
			adsConn.setAutoCommit(false);
			ps = adsConn.prepareStatement(sql);
			for (HashMap hm : insertList) {
				ps.setObject(1, SynDataUtil.toNotNullObject(hm.get("USERID")));
				ps.setObject(2, SynDataUtil.toNotNullObject(hm.get("USERSN")));
				ps.setObject(3, SynDataUtil.toNotNullObject(hm
						.get("LOCATIONCODEVALUE")));
				ps.setObject(4,
						SynDataUtil.toNotNullObject(hm.get("USERLEVEL")));
				ps.setObject(5,
						SynDataUtil.toNotNullObject(hm.get("INDUSTRYCATEGORY")));
				ps.addBatch();
				addbatchSize++;

				if (addbatchSize % 100000 == 0) {
					ps.executeBatch();
					ps.clearBatch();
				}
				if (addbatchSize % 100000 == 0) {
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

	private void delteUser(Connection adsConn) {

		PreparedStatement ps = null;
		String sql = "delete from   t_bsmp_user ";
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

	public void synUser() {
		logger.info("synUser query start " + new Date());
		Connection bsmpConn = SynDataUtil.getBsmpConnection();
		if (bsmpConn == null) {
			logger.error("获取BSMP数据库链接失败");
			return;
		}
		int pageSize = 200000;
		int pageNumber = 0;
		// List<HashMap> list = this.getBsmpUser(bsmpConn,pageSize,pageNumber);
		Connection adsConn = SynDataUtil.getADSConnection();
		this.delteUser(adsConn);
		// while (list!=null && list.size()>0)
		// {
		// // if(list != null && list.size()>0){
		// // List<HashMap> insertList = new ArrayList<HashMap>();
		// // List<HashMap> updateList = new ArrayList<HashMap>();
		// //
		// // for(HashMap map : list){
		// // this.saveLocationCode(map,insertList,updateList,adsConn);
		// // }
		// this.insertUser(list, adsConn);
		//
		// pageNumber++;
		// list = this.getBsmpUser(bsmpConn,pageSize,pageNumber);
		// System.out.println("pageNumber:"+pageNumber);
		// }
		ResultSet rs = null;
		PreparedStatement ps = null;
		int addbatchSize = 0;
		try {
			int startindex, endindex;
			int rowcount = 0;
			String sqlcount = "select count(*) count from t_user ";
			String sql;
			rs = bsmpConn.prepareStatement(sqlcount).executeQuery();
			if (rs.next()) {
				rowcount = StringUtil.toInt(rs.getObject("COUNT"));
			}
			adsConn.setAutoCommit(false);
			startindex = pageSize * pageNumber;
			endindex = (pageSize) * (pageNumber + 1) + 1;

			String insertsql = "insert into t_bsmp_user(userid,usersn,LOCATIONCODEVALUE,USERLEVEL,INDUSTRYCATEGORY,LOCATIONCODE) VALUES(?,?,?,?,?,?)";
			ps = adsConn.prepareStatement(insertsql);
			// while (startindex<=rowcount)
			{
				sql = "select t2.* "
						+ " from (select rownum r,t1.ID,t1.USER_SN,t1.LOCATION_DETAIL_CODE,t1.USER_LEVEL,t1.INDUSTRY_CATEGORY,t1.LOCATION_CODE from t_user t1 where rownum<"
						+ endindex + " order by t1.ID) t2" + " where t2.r>"
						+ startindex;
				sql = "select rownum r,t1.ID,t1.USER_SN,t1.LOCATION_DETAIL_CODE,nvl(t1.USER_LEVEL,'0') USER_LEVEL,nvl(t1.INDUSTRY_CATEGORY,'0') INDUSTRY_CATEGORY,t1.LOCATION_CODE from t_user t1";
				pageNumber++;
				startindex = pageSize * pageNumber;
				endindex = (pageSize) * (pageNumber + 1) + 1;
				// logger.info("pageNumber:"+pageNumber);
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

					if (addbatchSize % 100000 == 0) {
						ps.executeBatch();
						ps.clearBatch();
					}
					if (addbatchSize % 100000 == 0) {
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
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {

			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ps = null;
			}
		}
		if (bsmpConn != null) {
			try {
				bsmpConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (adsConn != null) {
			try {
				adsConn.setAutoCommit(true);
				adsConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("synUser save end " + new Date());

	}

	public void synData() {
		this.synUser();
		this.synLocationCode();
		this.synProductInfo();

	}

	public static void main(String args[]) {
		SynBsmpData syn = new SynBsmpData();
		// syn.getUserInfoByTvn("22960786");
		// syn.synUser();
//		SynDataUtil.getBmsproductoffering();
		syn.synProductInfo();
	}
}
