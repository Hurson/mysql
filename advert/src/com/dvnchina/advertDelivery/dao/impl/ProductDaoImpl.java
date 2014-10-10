package com.dvnchina.advertDelivery.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.constant.VodConstant;
import com.dvnchina.advertDelivery.dao.ProductDao;
import com.dvnchina.advertDelivery.model.AssetProduct;
import com.dvnchina.advertDelivery.model.ProductInfo;

public class ProductDaoImpl extends JdbcDaoSupport implements ProductDao {

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, Map<String, Object>> getProducts() {
		String sql = "select id,product_id,modify_time from t_productinfo";
		final Map<Integer, Map<String, Object>> productMap = new HashMap<Integer, Map<String, Object>>();

		getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int num) throws SQLException {
				ProductInfo product = new ProductInfo();
				product.setProductId(rs.getString("product_id"));
				product.setModifyTime(new Date(rs.getTimestamp("modify_time").getTime()));

				final Map<String, Object> proMap = new HashMap<String, Object>();
				Integer id = rs.getInt("id");
				product.setId(id);
				proMap.put(VodConstant.PRODUCT, product);

				String queryAsset = "select t.id,t.product_id,t.asset_id,a.asset_id assetid,p.product_id productid "
						+ "from t_asset_product t,t_assetinfo a,t_productinfo p "
						+ "where a.id=t.asset_id and p.id=t.product_id and t.product_id="
						+ id;
				final Map<Integer, AssetProduct> assetMap = new HashMap<Integer, AssetProduct>();
				/** 查询产品资源关系 */
				getJdbcTemplate().query(queryAsset, new RowMapper() {
					public Object mapRow(ResultSet rs, int num)
							throws SQLException {
						AssetProduct assetProduct = new AssetProduct();
						assetProduct.setId(rs.getInt("id"));
						assetProduct.setProduct_id(rs.getInt("product_id"));
						assetProduct.setAsset_id(rs.getInt("asset_id"));

						assetProduct.setProductId(rs.getString("productid"));

						assetProduct.setAssetId(rs.getString("assetid"));

						assetMap.put(assetProduct.hashCode(), assetProduct);

						return null;
					}
				});
				proMap.put(VodConstant.ASSETMAP, assetMap);

				proMap.put(VodConstant.ASSETMAP, assetMap);

				productMap.put(product.hashCode(), proMap);
				return null;
			}
		});

		return productMap;
	}

	private StringBuffer getSqlStr(List<String> productIds, int num) {
		StringBuffer sql = new StringBuffer();
		for (int i = num; i < productIds.size() && i - num < 1000; i++) {
			if (i == 0 && num == 0) {
				sql.append(" where product_id in(");
			} else if (i == num) {
				sql.append(" or product_id in(");
			}
			if (i != num) {
				sql.append(",");
			}
			sql.append("'").append(productIds.get(i)).append("'");
		}
		sql.append(")");
		return sql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, Map<String, Object>> getProductsByPId(
			List<String> productIds) {
		StringBuffer sql = new StringBuffer();
		sql
				.append("select id,product_id,modify_time from t_productinfo");
		int size = productIds.size();
		int num = 0;
		while (size > 0) {
			sql.append(this.getSqlStr(productIds, num));
			num = num + 1000;
			size = size - 1000;
		}
		final Map<Integer, Map<String, Object>> productMap = new HashMap<Integer, Map<String, Object>>();

		getJdbcTemplate().query(sql.toString(), new RowMapper() {

			public Object mapRow(ResultSet rs, int num) throws SQLException {
				ProductInfo product = new ProductInfo();
				product.setProductId(rs.getString("product_id"));
				product.setModifyTime(new Date(rs.getTimestamp("modify_time").getTime()));

				final Map<String, Object> proMap = new HashMap<String, Object>();
				Integer id = rs.getInt("id");
				product.setId(id);
				proMap.put(VodConstant.PRODUCT, product);

				String queryAsset = "select t.id,t.product_id,t.asset_id,a.asset_id assetid,p.product_id productid "
						+ "from t_asset_product t,t_assetinfo a,t_productinfo p "
						+ "where a.id=t.asset_id and p.id=t.product_id and t.product_id="
						+ id;
				final Map<Integer, AssetProduct> assetMap = new HashMap<Integer, AssetProduct>();
				/** 查询产品资源关系 */
				getJdbcTemplate().query(queryAsset, new RowMapper() {
					public Object mapRow(ResultSet rs, int num)
							throws SQLException {
						AssetProduct assetProduct = new AssetProduct();
						assetProduct.setId(rs.getInt("id"));
						assetProduct.setProduct_id(rs.getInt("product_id"));
						assetProduct.setAsset_id(rs.getInt("asset_id"));
						assetProduct.setProductId(rs.getString("productid"));
						assetProduct.setAssetId(rs.getString("assetid"));
						assetMap.put(assetProduct.hashCode(), assetProduct);

						return null;
					}
				});
				proMap.put(VodConstant.ASSETMAP, assetMap);

				productMap.put(product.hashCode(), proMap);
				return null;
			}
		});

		return productMap;
	}

	@Override
	public void insertProduct(final List<ProductInfo> products) {
		String sql = "insert into t_productinfo(id,product_id,product_name,price,product_desc,billing_model_name,billing_model_id,"
				+ "billing_model_type,sp_id,is_package,poster_url,type,biz_id,biz_desc,create_time,modify_time,state) "
				+ "values(T_PRODUCTINFO_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement pstmt, int i)
					throws SQLException {
				pstmt.setString(1, products.get(i).getProductId());
				pstmt.setString(2, products.get(i).getProductName());
				pstmt.setString(3, products.get(i).getPrice());
				pstmt.setString(4, products.get(i).getProductDesc());
				pstmt.setString(5, products.get(i).getBillingModelName());

				pstmt.setString(6, products.get(i).getBillingModelId());
				pstmt.setString(7, products.get(i).getBillingModelType());
				pstmt.setString(8, products.get(i).getSpId());
				pstmt.setString(9, String.valueOf(products.get(i)
						.getIsPackage()));
				pstmt.setString(10, products.get(i).getPosterUrl());

				pstmt.setString(11, products.get(i).getType());
				pstmt.setString(12, products.get(i).getBizId());
				pstmt.setString(13, products.get(i).getBizDesc());
				pstmt.setTimestamp(14, new Timestamp(products.get(i).getCreateTime().getTime()));
				pstmt.setTimestamp(15, new Timestamp(products.get(i).getModifyTime().getTime()));

				pstmt.setString(16, String.valueOf(products.get(i).getState()));

			}

			public int getBatchSize() {
				return products.size();
			}
		});

	}

	@Override
	public void deleteProduct(final List<Integer> ids) {
		String sql = "delete from t_productinfo where id=?";

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

	@Override
	public void updateProduct(final List<ProductInfo> products) {
		String sql = "update t_productinfo set product_id=?,product_name=?,price=?,product_desc=?,billing_model_name=?,billing_model_id=?,"
				+ "billing_model_type=?,sp_id=?,is_package=?,poster_url=?,type=?,biz_id=?,biz_desc=?,create_time=?,"
				+ "modify_time=?,state=? where id=?";

		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement pstmt, int i)
					throws SQLException {
				pstmt.setString(1, products.get(i).getProductId());
				pstmt.setString(2, products.get(i).getProductName());
				pstmt.setString(3, products.get(i).getPrice());
				pstmt.setString(4, products.get(i).getProductDesc());
				pstmt.setString(5, products.get(i).getBillingModelName());

				pstmt.setString(6, products.get(i).getBillingModelId());
				pstmt.setString(7, products.get(i).getBillingModelType());
				pstmt.setString(8, products.get(i).getSpId());
				pstmt.setString(9, String.valueOf(products.get(i)
						.getIsPackage()));
				pstmt.setString(10, products.get(i).getPosterUrl());

				pstmt.setString(11, products.get(i).getType());
				pstmt.setString(12, products.get(i).getBizId());
				pstmt.setString(13, products.get(i).getBizDesc());
				pstmt.setTimestamp(14, new Timestamp(products.get(i).getCreateTime().getTime()));
				pstmt.setTimestamp(15, new Timestamp(products.get(i).getModifyTime().getTime()));

				pstmt.setString(16, String.valueOf(products.get(i).getState()));
				pstmt.setInt(17, products.get(i).getId());

			}

			public int getBatchSize() {
				return products.size();
			}
		});

	}

	@Override
	public void insertAssetProduct(final Object[] assetProducts) {
		String sql = "insert into t_asset_product(id,product_id,asset_id) values(T_ASSET_PRODUCT_SEQ.nextval,"
				+ "(select id from t_productinfo where product_id=?),(select id from t_assetinfo where asset_id=?))";

		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement pstmt, int i)
					throws SQLException {
				AssetProduct ap = (AssetProduct) assetProducts[i];
				try {
					pstmt.setString(1, ap.getProductId());
					pstmt.setString(2, ap.getAssetId());
				} catch (Exception e) {
					logger.info("通过资源或产品id查询不到记录，添加资源包与资源关系失败！");
				}
			}

			public int getBatchSize() {
				return assetProducts.length;
			}
		});

	}

	@Override
	public void deleteAssetProduct(final List<Integer> ids) {
		String sql = "delete from t_asset_product where id=?";

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

	@Override
	public List<ProductInfo> getProducts(int begin, int end, String id,
			String name) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,product_id,product_name,price,is_package,type,biz_id,state from t_productinfo where 1=1 ");
		if(StringUtils.isNotBlank(id)){
			sql.append(" and upper(product_id) like '%");
			sql.append(id.toUpperCase());
			sql.append("%'");
		}
		if(StringUtils.isNotBlank(name)){
			sql.append(" and product_name like '%");
			sql.append(name);
			sql.append("%'");
		}
		List<ProductInfo> ps = getJdbcTemplate().query(this.getPageSql(sql.toString(), begin, end),new RowMapper<ProductInfo>(){
			@Override
			public ProductInfo mapRow(ResultSet rs, int num)
					throws SQLException {
				ProductInfo p = new ProductInfo();
				p.setId(rs.getInt("id"));
				p.setProductId(rs.getString("product_id"));
				p.setProductName(rs.getString("product_name"));
				p.setPrice(rs.getString("price"));
				String isPackage = rs.getString("is_package");
				if(StringUtils.isNotBlank(isPackage)){
					p.setIsPackage(isPackage.charAt(0));
				}
				p.setType(rs.getString("type"));
				p.setBizId(rs.getString("biz_id"));
				String state = rs.getString("state");
				if(StringUtils.isNotBlank(state)){
					p.setState(state.charAt(0));
				}
				return p;
			}
		});
		return ps;
	}

	@Override
	public int getProductCount(String id, String name) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(id) from t_productinfo where 1=1 ");
		if(StringUtils.isNotBlank(id)){
			sql.append(" and upper(product_id) like '%");
			sql.append(id.toUpperCase());
			sql.append("%'");
		}
		if(StringUtils.isNotBlank(name)){
			sql.append(" and product_name like '%");
			sql.append(name);
			sql.append("%'");
		}
		return getJdbcTemplate().queryForInt(sql.toString());
	}

	private String getPageSql(String sql, int begin, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
				+ sql + ") row_ where rownum <= " + end + ") where rownum_ >="
				+ begin;
		return pageSql;
	}

}
