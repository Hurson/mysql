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

public class SynEpgData {
	public final Log logger = LogFactory.getLog(getClass());

	

	

	

	
	public List getEPGChanal(Connection cpsConn){
		List<HashMap> results = new ArrayList<HashMap>();
		ResultSet rs = null;
		try {
			rs = cpsConn.prepareStatement(
			"select a.channel_id as CHANNEL_ID,a.channel_code as CHANNEL_CODE,a.channel_type as CHANNEL_TYPE,a.channel_name as CHANNEL_NAME,a.service_id as SERVICE_ID,a.channel_language as CHANNEL_LANGUAGE,a.channel_logo as CHANNEL_LOGO,a.keyword as KEYWORD,a.state as STATE,a.channel_desc as CHANNEL_DESC from tbl_channel a ").executeQuery();
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
	
	public void saveEPGChannel(HashMap hm,List<HashMap> insertList,List<HashMap> updateList,Connection adsConn){
		PreparedStatement countps = null;
		ResultSet rs = null;
		int count = 0;
		if (adsConn != null) {
		try {
			countps  = adsConn.prepareStatement(
			"select count(1) from t_channelinfo where  CHANNEL_ID =?");
			countps.setObject(1, hm.get("CHANNEL_ID"));
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
				logger.debug("获取EPG数据库链接失败", e);
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
	 * 
	 * @param updateList
	 * @param adsConn
	 */
	private void updateEPGChannel(List<HashMap> updateList,Connection adsConn) {
		
		if(updateList == null || updateList.size()<=0){
			return;
		}
		PreparedStatement ps = null;
		String sql = "update  t_channelinfo set " + "CHANNEL_ID=?,"
		+ "CHANNEL_CODE=?," + "CHANNEL_TYPE=?,"
		+ "CHANNEL_NAME=?," + "SERVICE_ID=?,"
		+ "CHANNEL_LANGUAGE=?," + "CHANNEL_LOGO=?,"
		+ "KEYWORD=?," + "STATE=? " + " where CHANNEL_ID=?";
		try {	
            ps = adsConn.prepareStatement(sql);
            for(HashMap hm : updateList){
            	ps.setObject(1,
						SynDataUtil.toNotNullObject(hm.get("CHANNEL_ID")));
				ps.setObject(2,
						SynDataUtil.toNotNullObject(hm.get("CHANNEL_CODE")));
				ps.setObject(3,
						SynDataUtil.toNotNullObject(hm.get("CHANNEL_TYPE")));
				ps.setObject(4,
						SynDataUtil.toNotNullObject(hm.get("CHANNEL_NAME")));
				ps.setObject(5,
						SynDataUtil.toNotNullObject(hm.get("SERVICE_ID")));
				ps.setObject(6, SynDataUtil.toNotNullObject(hm
						.get("CHANNEL_LANGUAGE")));
				ps.setObject(7,
						SynDataUtil.toNotNullObject(hm.get("CHANNEL_LOGO")));
				ps.setObject(8,
						SynDataUtil.toNotNullObject(hm.get("KEYWORD")));
				ps.setObject(9,
						SynDataUtil.toNotNullObject(hm.get("STATE")));
				ps.setObject(10,
						SynDataUtil.toNotNullObject(hm.get("CHANNEL_ID")));
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
	 * 
	 * @param insertList
	 * @param adsConn
	 */
	private void insertEPGChannel(List<HashMap> insertList,Connection adsConn){
		if(insertList == null || insertList.size()<=0){
			return;
		}
		PreparedStatement ps = null;
		String 	sql = "insert into t_channelinfo(CHANNEL_ID,"
			+ "CHANNEL_CODE," + "CHANNEL_TYPE,"
			+ "CHANNEL_NAME," + "SERVICE_ID,"
			+ "CHANNEL_LANGUAGE," + "CHANNEL_LOGO,"
			+ "KEYWORD,STATE) VALUES(?,?,?,?,?,?,?,?,?)";

		try {	
            ps = adsConn.prepareStatement(sql);
            for(HashMap hm : insertList){
            	ps.setObject(1,
						SynDataUtil.toNotNullObject(hm.get("CHANNEL_ID")));
				ps.setObject(2,
						SynDataUtil.toNotNullObject(hm.get("CHANNEL_CODE")));
				ps.setObject(3,
						SynDataUtil.toNotNullObject(hm.get("CHANNEL_TYPE")));
				ps.setObject(4,
						SynDataUtil.toNotNullObject(hm.get("CHANNEL_NAME")));
				ps.setObject(5,
						SynDataUtil.toNotNullObject(hm.get("SERVICE_ID")));
				ps.setObject(6, SynDataUtil.toNotNullObject(hm
						.get("CHANNEL_LANGUAGE")));
				ps.setObject(7,
						SynDataUtil.toNotNullObject(hm.get("CHANNEL_LOGO")));
				ps.setObject(8,
						SynDataUtil.toNotNullObject(hm.get("KEYWORD")));
				ps.setObject(9,
						SynDataUtil.toNotNullObject(hm.get("STATE")));

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
	
	private void deleteEPGChannel(Connection adsConn){
		
		PreparedStatement ps = null;
		String 	sql = "delete from t_channelinfo";
		

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

	public void synEPGChannel() {
		System.out.println("synEPGChannel query start "+new Date());
		Connection epgConn = SynDataUtil.getEPGConnection();
		if (epgConn==null)
		{
			return ;
		}
		List<HashMap> list = this.getEPGChanal(epgConn);
		Connection adsConn = SynDataUtil.getADSConnection();
//		if(list != null && list.size()>0){
//			List<HashMap> insertList = new ArrayList<HashMap>();
//			List<HashMap> updateList = new ArrayList<HashMap>();
//			
//			for(HashMap map : list){
//				this.saveEPGChannel(map,insertList,updateList,adsConn);
//			}
		this.deleteEPGChannel(adsConn);
			this.insertEPGChannel(list, adsConn);
			
//		}
		
		if(epgConn != null){
			try {
				epgConn.close();
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
		System.out.println("synEPGChannel save end "+new Date());
		
		
	}
	
	
	public static void main(String args[]) {
		SynEpgData syn = new SynEpgData();
		syn.synEPGChannel();
	}
}
