package com.dvnchina.advertDelivery.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.constant.VodConstant;
import com.dvnchina.advertDelivery.dao.AssetDao;
import com.dvnchina.advertDelivery.dao.AssetPackageDao;
import com.dvnchina.advertDelivery.model.AssetInfo;
import com.dvnchina.advertDelivery.model.PackageAsset;

/**
 * 资源包实体表数据库操作
 * */
public class AssetPackageDaoImpl extends JdbcDaoSupport implements
		AssetPackageDao {

	private AssetDao assetDao;

	public void setAssetDao(AssetDao assetDao) {
		this.assetDao = assetDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, Map<String, Object>> getAssetPackages() {

		String sql = "select id,asset_id,modify_time from t_assetinfo where is_package=" + VodConstant.IS_PACKAGE;

		final Map<Integer, Map<String, Object>> assetPackageMap = new HashMap<Integer, Map<String, Object>>();

		getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int num) throws SQLException {
				AssetInfo asset = new AssetInfo();
				asset.setId(rs.getInt("id"));
				asset.setAssetId(rs.getString("asset_id"));
				asset.setModifyTime(new Date(rs.getTimestamp("modify_time").getTime()));

				final Map<String, Object> packageMap = new HashMap<String, Object>();
				Integer id = rs.getInt("id");
				asset.setId(id);
				packageMap.put(VodConstant.ASSETPACKAGE, asset);
				String queryAsset = "select t.id,t.package_id,t.asset_id,a.asset_id assetid,p.asset_id packageid "
						+ "from t_package_asset t,t_assetinfo a,t_assetinfo p where t.package_id=p.id and t.asset_id=a.id and package_id="
						+ id;
				final Map<Integer, PackageAsset> assetMap = new HashMap<Integer, PackageAsset>();
				getJdbcTemplate().query(queryAsset, new RowMapper() {

					public Object mapRow(ResultSet rs, int num)
							throws SQLException {
						PackageAsset packageAsset = new PackageAsset();
						packageAsset.setId(rs.getInt("id"));
						packageAsset.setAsset_id(rs.getInt("asset_id"));
						packageAsset.setPackage_id(rs.getInt("package_id"));

						packageAsset.setPackageId(rs.getString("packageid"));

						packageAsset.setAssetId(rs.getString("assetid"));

						assetMap.put(packageAsset.hashCode(), packageAsset);

						return null;
					}
				});
				packageMap.put(VodConstant.ASSETMAP, assetMap);

				packageMap.put(VodConstant.ASSETMAP, assetMap);

				assetPackageMap.put(asset.hashCode(), packageMap);
				return null;
			}
		});

		return assetPackageMap;
	}

	private StringBuffer getSqlStr(List<String> assetIds, int num) {
		StringBuffer sql = new StringBuffer();
		for (int i = num; i < assetIds.size() && i - num < 1000; i++) {
			if (i == 0 && num == 0) {
				sql.append("asset_id in(");
			} else if (i == num) {
				sql.append(" or asset_id in(");
			}
			if (i != num) {
				sql.append(",");
			}
			sql.append("'").append(assetIds.get(i)).append("'");
		}
		sql.append(")");
		return sql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, Map<String, Object>> getAssetPackagesByPId(
			List<String> packageIds) {
		StringBuffer sql = new StringBuffer(
				"select id,asset_id,date_format(modify_time,'%Y-%m-%d %H:%i:%S') mTime from t_assetinfo where is_package=");
		sql.append(VodConstant.IS_PACKAGE);
		sql.append(" and (");
		int size = packageIds.size();
		int num = 0;
		while (size > 0) {
			sql.append(this.getSqlStr(packageIds, num));
			num = num + 1000;
			size = size - 1000;
		}
		sql.append(")");
		final Map<Integer, Map<String, Object>> assetPackageMap = new HashMap<Integer, Map<String, Object>>();

		getJdbcTemplate().query(sql.toString(), new RowMapper() {

			public Object mapRow(ResultSet rs, int num) throws SQLException {
				AssetInfo asset = new AssetInfo();
				asset.setId(rs.getInt("id"));
				asset.setAssetId(rs.getString("asset_id"));
				Date date = new Date();
				try {
					date = DateUtils.parseDate(rs.getString("mTime"),
							new String[] { "yyyy-MM-dd HH:mm:ss" });
				} catch (ParseException e) {
					logger.info("", e);
				}
				asset.setModifyTime(date);

				final Map<String, Object> packageMap = new HashMap<String, Object>();
				Integer id = rs.getInt("id");
				asset.setId(id);
				packageMap.put(VodConstant.ASSETPACKAGE, asset);
				String queryAsset = "select t.id,t.package_id,t.asset_id,a.asset_id assetid,p.asset_id packageid "
						+ "from t_package_asset t,t_assetinfo a,t_assetinfo p where t.package_id=p.id and t.asset_id=a.id and package_id="
						+ id;
				final Map<Integer, PackageAsset> assetMap = new HashMap<Integer, PackageAsset>();
				getJdbcTemplate().query(queryAsset, new RowMapper() {

					public Object mapRow(ResultSet rs, int num)
							throws SQLException {
						PackageAsset packageAsset = new PackageAsset();
						packageAsset.setId(rs.getInt("id"));
						packageAsset.setAsset_id(rs.getInt("asset_id"));
						packageAsset.setPackage_id(rs.getInt("package_id"));
						packageAsset.setPackageId(rs.getString("packageid"));
						packageAsset.setAssetId(rs.getString("assetid"));
						assetMap.put(packageAsset.hashCode(), packageAsset);

						return null;
					}
				});
				packageMap.put(VodConstant.ASSETMAP, assetMap);

				assetPackageMap.put(asset.hashCode(), packageMap);
				return null;
			}
		});

		return assetPackageMap;
	}

	@Override
	public void insertAssetPackage(List<AssetInfo> assetPackagetList) {
		assetDao.insertAsset(assetPackagetList);
	}

	@Override
	public void updateAssetPackage(List<AssetInfo> assetPackagetList) {
		assetDao.updateAsset(assetPackagetList);
	}

	@Override
	public void deleteAssetPackage(List<Integer> assetPackagets) {
		assetDao.deleteAsset(assetPackagets);
	}

	@Override
	public void insertPackageAsset(final Object[] packageAssets) {
		String sql = "insert into t_package_asset(id,package_id,asset_id) values(T_PACKAGE_ASSET_SEQ.nextval,(select id from t_assetinfo where asset_id=?),(select id from t_assetinfo where asset_id=?))";

		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement pstmt, int i)
					throws SQLException {
				PackageAsset pa = (PackageAsset) packageAssets[i];
				try {
					pstmt.setString(1, pa.getPackageId());
					pstmt.setString(2, pa.getAssetId());
				} catch (Exception e) {
					logger.info("通过资源或资源包id查询不到记录，添加资源包与资源关系失败！");
				}
			}

			public int getBatchSize() {
				return packageAssets.length;
			}
		});

	}

	@Override
	public void deletePackageAsset(final List<Integer> ids) {
		String sql = "delete from t_package_asset where id=?";

		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement pstmt, int i)
					throws SQLException {
				pstmt.setInt(1, ids.get(i));
			}

			public int getBatchSize() {
				return ids.size();
			}
		});

	}

}
