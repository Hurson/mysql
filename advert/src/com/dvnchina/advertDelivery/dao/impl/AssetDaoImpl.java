package com.dvnchina.advertDelivery.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.constant.VodConstant;
import com.dvnchina.advertDelivery.dao.AssetDao;
import com.dvnchina.advertDelivery.model.AssetInfo;

/**
 * 资源实体表数据库操作
 * */
public class AssetDaoImpl extends JdbcDaoSupport implements AssetDao{

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, AssetInfo> getAssets() {
		String sql = "select id,asset_id,modify_time from t_assetinfo where is_package=" + VodConstant.IS_NOT_PACKAGE;
		final Map<Integer, AssetInfo> assetMap = new HashMap<Integer, AssetInfo>();
		
		getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int num) throws SQLException {
				AssetInfo asset = new AssetInfo();
				asset.setId(rs.getInt("id"));
				asset.setAssetId(rs.getString("asset_id"));
				asset.setModifyTime(new Date(rs.getTimestamp("modify_time").getTime()));
				assetMap.put(asset.hashCode(), asset);
				return null;
			}
		});
		
		return assetMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, AssetInfo> getAssetsByAId(List<String> assetIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,asset_id,modify_time from t_assetinfo where is_package=");
		sql.append(VodConstant.IS_NOT_PACKAGE);
		sql.append(" and (");
		int size = assetIds.size();
		int num = 0;
		while(size>0){
			sql.append(this.getSqlStr(assetIds, num));
			num=num+1000;
			size = size-1000;
		}
		sql.append(")");
		final Map<Integer, AssetInfo> assetMap = new HashMap<Integer, AssetInfo>();
		
		getJdbcTemplate().query(sql.toString(), new RowMapper() {
			public Object mapRow(ResultSet rs, int num) throws SQLException {
				AssetInfo asset = new AssetInfo();
				asset.setId(rs.getInt("id"));
				asset.setAssetId(rs.getString("asset_id"));
				asset.setModifyTime(new Date(rs.getTimestamp("modify_time").getTime()));
				assetMap.put(asset.hashCode(), asset);
				return null;
			}
		});
		
		return assetMap;
	}
	private StringBuffer getSqlStr(List<String> assetIds,int num){
		StringBuffer sql = new StringBuffer();
		for(int i = num;i<assetIds.size()&&i-num<1000;i++){
			if(i==0&&num==0){
				sql.append("asset_id in(");
			}else if(i==num){
				sql.append(" or asset_id in(");
			}
			if(i!=num){
				sql.append(",");
			}
			sql.append("'").append(assetIds.get(i)).append("'");
		}
		sql.append(")");
		return sql;
	}
	@Override
	public void insertAsset(final List<AssetInfo> assets) {
		String sql = "insert into t_assetinfo(id,asset_id,asset_name,short_name,title,year,keyword,rating,runtime,is_package,asset_create_time,"
			+ "display_runtime,asset_desc,poster_url,preview_asset_id,preview_asset_name,preview_runtime,director,actor,create_time,modify_time,state,"
			+ "category,score,video_code,video_resolution) values(T_ASSETINFO_SEQ.nextval,?,?,?,?,?,?,?,?,?,"
			+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		getJdbcTemplate().batchUpdate(sql,new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement pstmt, int i)throws SQLException {
				
				pstmt.setString(1, assets.get(i).getAssetId());
				pstmt.setString(2, assets.get(i).getAssetName());
				pstmt.setString(3, assets.get(i).getShortName());
				pstmt.setString(4, assets.get(i).getTitle());
				pstmt.setString(5, assets.get(i).getYear());

				pstmt.setString(6, assets.get(i).getKeyword());
				pstmt.setString(7, assets.get(i).getRating());
				pstmt.setString(8, assets.get(i).getRuntime());
				pstmt.setString(9, String.valueOf(assets.get(i).getIsPackage()));
				pstmt.setTimestamp(10, new Timestamp(assets.get(i).getAssetCreatetime().getTime()));

				pstmt.setString(11, assets.get(i).getDisplayRuntime());
				pstmt.setString(12, assets.get(i).getAssetDesc());
				pstmt.setString(13, assets.get(i).getPosterUrl());
				pstmt.setString(14, assets.get(i).getPreviewAssetId());
				pstmt.setString(15, assets.get(i).getPreviewAssetName());

				pstmt.setString(16, assets.get(i).getPreviewRuntime());
				pstmt.setString(17, assets.get(i).getDirector());
				pstmt.setString(18, assets.get(i).getActor());
				pstmt.setTimestamp(19, new Timestamp(assets.get(i).getCreateTime().getTime()));
				pstmt.setTimestamp(20, new Timestamp(assets.get(i).getModifyTime().getTime()));

				pstmt.setString(21, String.valueOf(assets.get(i).getState()));
				pstmt.setString(22, assets.get(i).getCategory());
				if (assets.get(i).getScore() != null) {
					pstmt.setDouble(23, assets.get(i).getScore());
				} else {
					pstmt.setDouble(23, VodConstant.DE_SCORE);
				}
				pstmt.setString(24, assets.get(i).getVideoCode());
				pstmt.setString(25, assets.get(i).getVideoResolution());

			}

			public int getBatchSize() {
				return assets.size();
			}
		});
		
	}

	@Override
	public void deleteAsset(final List<Integer> ids) {
		String sql = "delete from t_assetinfo where id=?";
		
		getJdbcTemplate().batchUpdate(sql,new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement pstmt, int i)
					throws SQLException {
				pstmt.setInt(1, ids.get(i));
			}

			public int getBatchSize() {
				return ids.size();
			}
		});
		
	}

	@Override
	public void updateAsset(final List<AssetInfo> assets) {
		String sql = "update t_assetinfo set asset_id=?,asset_name=?,short_name=?,title=?,year=?,keyword=?,rating=?,runtime=?,is_package=?,"
				+ "asset_create_time=str_to_date(?,'%Y-%m-%d %H:%i:%S'),display_runtime=?,asset_desc=?,poster_url=?,preview_asset_id=?,preview_asset_name=?,"
				+ "preview_runtime=?,director=?,actor=?,create_time=str_to_date(?,'%Y-%m-%d %H:%i:%S'),modify_time=str_to_date(?,'%Y-%m-%d %H:%i:%S'),"
				+ "state=?,category=?,score=?,video_code=?,video_resolution=? where id=?";
		
		getJdbcTemplate().batchUpdate(sql,new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement pstmt, int i)throws SQLException {
				
				pstmt.setString(1, assets.get(i).getAssetId());
				pstmt.setString(2, assets.get(i).getAssetName());
				pstmt.setString(3, assets.get(i).getShortName());
				pstmt.setString(4, assets.get(i).getTitle());
				pstmt.setString(5, assets.get(i).getYear());

				pstmt.setString(6, assets.get(i).getKeyword());
				pstmt.setString(7, assets.get(i).getRating());
				pstmt.setString(8, assets.get(i).getRuntime());
				pstmt.setString(9, String.valueOf(assets.get(i).getIsPackage()));
				pstmt.setString(10, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(assets.get(i).getAssetCreatetime()));

				pstmt.setString(11, assets.get(i).getDisplayRuntime());
				pstmt.setString(12, assets.get(i).getAssetDesc());
				pstmt.setString(13, assets.get(i).getPosterUrl());
				pstmt.setString(14, assets.get(i).getPreviewAssetId());
				pstmt.setString(15, assets.get(i).getPreviewAssetName());

				pstmt.setString(16, assets.get(i).getPreviewRuntime());
				pstmt.setString(17, assets.get(i).getDirector());
				pstmt.setString(18, assets.get(i).getActor());
				pstmt.setString(19, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(assets.get(i).getCreateTime()));
				pstmt.setString(20, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(assets.get(i).getModifyTime()));

				pstmt.setString(21, String.valueOf(assets.get(i).getState()));
				pstmt.setString(22, assets.get(i).getCategory());
				if (assets.get(i).getScore() != null) {
					pstmt.setDouble(23, assets.get(i).getScore());
				} else {
					pstmt.setDouble(23, VodConstant.DE_SCORE);
				}
				pstmt.setString(24, assets.get(i).getVideoCode());
				pstmt.setString(25, assets.get(i).getVideoResolution());

				pstmt.setInt(26, assets.get(i).getId());

			}

			public int getBatchSize() {
				return assets.size();
			}
		});
	}

	@Override
	public int getAssetCount(String id, String name) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(id) from t_assetinfo where 1=1");
		if(StringUtils.isNotBlank(id)){
			sql.append(" and upper(asset_id) like '%");
			sql.append(id.toUpperCase());
			sql.append("%'");
		}
		if(StringUtils.isNotBlank(name)){
			sql.append(" and asset_name like '%");
			sql.append(name);
			sql.append("%'");
		}
		return getJdbcTemplate().queryForInt(sql.toString());
	}

	@Override
	public List<AssetInfo> getAssets(int begin, int end, String id, String name) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,asset_id,asset_name,year,rating,runtime,is_package,score,state from t_assetinfo where 1=1");
		if(StringUtils.isNotBlank(id)){
			sql.append(" and upper(asset_id) like '%");
			sql.append(id.toUpperCase());
			sql.append("%'");
		}
		if(StringUtils.isNotBlank(name)){
			sql.append(" and asset_name like '%");
			sql.append(name);
			sql.append("%'");
		}
		List<AssetInfo> assets = getJdbcTemplate().query(this.getPageSql(sql.toString(), begin, end), new RowMapper<AssetInfo>(){
			@Override
			public AssetInfo mapRow(ResultSet rs, int num)
					throws SQLException {
				AssetInfo asset = new AssetInfo();
				asset.setId(rs.getInt("id"));
				asset.setAssetId(rs.getString("asset_id"));
				asset.setAssetName(rs.getString("asset_name"));
				asset.setYear(rs.getString("year"));
				asset.setRating(rs.getString("rating"));
				asset.setRuntime(rs.getString("runtime"));
				String isPackage = rs.getString("is_package");
				if(StringUtils.isNotBlank(isPackage)){
					asset.setIsPackage(isPackage.charAt(0));
				}
				asset.setScore(rs.getDouble("score"));
				if(StringUtils.isNotBlank(rs.getString("state"))){
					asset.setState(rs.getString("state").charAt(0));
				}
				return asset;
			}
		});
		return assets;
	}
	
	private String getPageSql(String sql, int begin, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
				+ sql + ") row_ where rownum <= " + end + ") where rownum_ >="
				+ begin;
		return pageSql;
	}
}
