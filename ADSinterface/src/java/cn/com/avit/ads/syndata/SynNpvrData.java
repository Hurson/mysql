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

public class SynNpvrData {
	public final Log logger = LogFactory.getLog(getClass());

	
		
	public List getNpvrChannel(Connection cpsConn){
		List<HashMap> results = new ArrayList<HashMap>();
		ResultSet rs = null;
		try {
			rs = cpsConn.prepareStatement(
			"select * from t_channel t where t.servicecode like '%VOD%'").executeQuery();
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
		String 				sql = "insert into t_channelinfo_npvr(CHANNEL_ID,CHANNEL_CODE,CHANNEL_NAME,SERVICE_ID,SUMMARYSHORT) VALUES(?,?,?,?,?)";
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
	/**
	 * 同步区域码信息
	 */
	


	public void synNpvrChannel() {
		System.out.println("synNpvrChannel query start "+new Date());
		Connection nPvrConn = SynDataUtil.getCPSConnection();
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
		
	
	public static void main(String args[]) {
		SynNpvrData syn = new SynNpvrData();
		syn.synNpvrChannel();
	}
}
