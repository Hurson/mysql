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

public class SynBsmpData {
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
		map.put("name", tvn);
		if (0==0)
		{
			return map;
		}
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
			"select * from t_location_code").executeQuery();
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
//				logger.debug("获取CPS数据库链接失败", e);
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
						SynDataUtil.toNotNullObject(hm.get("LOCATIONCODE")));
				ps.setObject(2,
						SynDataUtil.toNotNullObject(hm.get("LOCATIONNAME")));
				ps.setObject(3, SynDataUtil.toNotNullObject(hm
						.get("PARENTLOCATION")));
				ps.setObject(4,
						SynDataUtil.toNotNullObject(hm.get("LOCATIONTYPE")));

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
		Connection bsmpConn = SynDataUtil.getBsmpConnection();
		List<HashMap> list = this.getBsmpLocationCode(bsmpConn);
		Connection adsConn = SynDataUtil.getADSConnection();
		if(list != null && list.size()>0){
			List<HashMap> insertList = new ArrayList<HashMap>();
			List<HashMap> updateList = new ArrayList<HashMap>();
			
			for(HashMap map : list){
				this.saveLocationCode(map,insertList,updateList,adsConn);
			}
			this.insertLocationCode(insertList, adsConn);
			this.updateLocationCode(updateList, adsConn);
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
				adsConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("synLocationCode save end "+new Date());
		
		
	}
	
	

	
	public void synData() {

		this.synLocationCode();

	}
	public static void main(String args[]) {
		SynBsmpData syn = new SynBsmpData();
		syn.getUserInfoByTvn("22960786");
		syn.synLocationCode();
	}
}
