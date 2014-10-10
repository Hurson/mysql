package com.dvnchina.advertDelivery.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.bean.contract.ContractContractADRelation;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.dao.ContractBackupDao;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.TempContract;

public class ContractBackupDaoImpl extends JdbcDaoSupport implements ContractBackupDao {

	@Override
	public int removeContractBackup(final int removeContractBackupId) {
		String removeSql = "DELETE FROM T_CONTRACT_BACKUP WHERE ID=?";
		return getJdbcTemplate().update(removeSql,new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1,removeContractBackupId);
			}
		});
	}

	@Override
	public List<ContractBackup> page(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= ?) where rownum_ >?";
	RowMapper<ContractBackup> rowMapper = BeanPropertyRowMapper.newInstance(ContractBackup.class);  
	return getJdbcTemplate().query(pageSql, new Object[] { end, start },
			rowMapper);
	}
	
	@Override
	public List<ContractBackup> page(String sql,List param, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= ?) where rownum_ >?";
	RowMapper<ContractBackup> rowMapper = BeanPropertyRowMapper.newInstance(ContractBackup.class);
	param.add(end);
	param.add(start);
	return getJdbcTemplate().query(pageSql,param.toArray(),rowMapper);
	}

	@Override
	public int queryTotalCount(String sql) {
		return getJdbcTemplate().queryForInt(sql);
	}
	
	public int queryTotalCount(String sql,List listParam) {
		return getJdbcTemplate().queryForInt(sql,listParam.toArray());
	}

	@Override
	public int saveContractBackup(final ContractBackup contractBackup) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("INSERT INTO T_CONTRACT_BACKUP(ID,CONTRACT_NUMBER,CONTRACT_CODE,CUSTOMER_ID,CONTRACT_NAME,EFFECTIVE_START_DATE,EFFECTIVE_END_DATE,SUBMIT_UNITS,FINANCIAL_INFORMATION,APPROVAL_CODE,APPROVAL_START_DATE,APPROVAL_END_DATE,METARIAL_PATH,STATUS,CREATE_TIME,OPERATOR_ID,OTHER_CONTENT,CONTRACT_DESC) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
		return getJdbcTemplate().update(updateSql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1,contractBackup.getId());
						ps.setString(2, contractBackup.getContractNumber());
						ps.setString(3, contractBackup.getContractCode());
						ps.setInt(4, contractBackup.getCustomerId());
						ps.setString(5, contractBackup.getContractName());
						ps.setTimestamp(6, new Timestamp(contractBackup.getEffectiveStartDate().getTime()));
						ps.setTimestamp(7, new Timestamp(contractBackup.getEffectiveEndDate().getTime()));
						ps.setString(8, contractBackup.getSubmitUnits());
						ps.setString(9, contractBackup.getFinancialInformation());
						ps.setString(10, contractBackup.getApprovalCode());
						ps.setTimestamp(11, new Timestamp(contractBackup.getApprovalStartDate().getTime()));
						ps.setTimestamp(12,new Timestamp(contractBackup.getApprovalEndDate().getTime()));
						ps.setString(13, contractBackup.getMetarialPath());
						ps.setInt(14, contractBackup.getStatus());
						ps.setTimestamp(15,new Timestamp(contractBackup.getCreateTime().getTime()));
						ps.setInt(16, contractBackup.getOperatorId());
						ps.setString(17, contractBackup.getOtherContent());
						ps.setString(18, contractBackup.getContractDesc());
					}
				});
	}

	@Override
	public int updateContractBackup(final ContractBackup contractBackup) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE T_CONTRACT_BACKUP")
			     .append(" SET CONTRACT_NUMBER = ?,")
			     .append(" CONTRACT_CODE=?,")
			     .append(" CUSTOMER_ID=?,")
			     .append(" CONTRACT_NAME=?,")
			     .append(" EFFECTIVE_START_DATE=?,")
			     .append(" EFFECTIVE_END_DATE=?,")
			     .append(" SUBMIT_UNITS=?,")
			     .append(" FINANCIAL_INFORMATION=?,")
			     .append(" APPROVAL_CODE=?,")
			     .append(" APPROVAL_START_DATE=?,")
			     .append(" APPROVAL_END_DATE=?,")
			     .append(" METARIAL_PATH=?,")
			     .append(" STATUS=?,")
			     .append(" OPERATOR_ID=?,")
			     .append(" OTHER_CONTENT=?,")
			     .append(" CONTRACT_DESC=?")
			     //.append(" AUDIT_TAFF=?,")
			     //.append(" EXAMINATION_OPINIONS=?,")
			     //.append(" AUDIT_DATE=?,")
			     .append(" WHERE ID=?");
				
		return getJdbcTemplate().update(updateSql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, contractBackup.getContractNumber());
						ps.setString(2, contractBackup.getContractCode());
						ps.setInt(3, contractBackup.getCustomerId());
						ps.setString(4, contractBackup.getContractName());
						ps.setTimestamp(5, new Timestamp(contractBackup.getEffectiveStartDate().getTime()));
						ps.setTimestamp(6,new Timestamp(contractBackup.getEffectiveEndDate().getTime()));
						ps.setString(7, contractBackup.getSubmitUnits());
						ps.setString(8, contractBackup.getFinancialInformation());
						ps.setString(9, contractBackup.getApprovalCode());
						ps.setTimestamp(10, new Timestamp(contractBackup.getApprovalStartDate().getTime()));
						ps.setTimestamp(11,new Timestamp(contractBackup.getApprovalEndDate().getTime()));
						ps.setString(12, contractBackup.getMetarialPath());
						ps.setInt(13, contractBackup.getStatus());
						ps.setInt(14, contractBackup.getOperatorId());
						ps.setString(15, contractBackup.getOtherContent());
						ps.setString(16, contractBackup.getContractDesc());
						//ps.setString(17, contractBackup.getAuditTaff());
						//ps.setString(18, contractBackup.getExaminationOpinions());
						//ps.setTimestamp(19,new Timestamp(contractBackup.getAuditDate().getTime()));
						ps.setInt(17,contractBackup.getId());
					}
				});
	}
	@Override
	public int updateContractADBackup4ModifyContractBackup(final ContractAD contractAD) {
		
		StringBuffer updateSql = new StringBuffer();
		updateSql.append(" UPDATE T_CONTRACT_AD_BACKUP SET ")
			     .append(" CONTRACT_CODE=?,")
			     .append(" CONTRACT_NAME=?,")
			     .append(" ADVERTISERS_ID=?,")
			     .append(" ADVERTISERS_NAME=?,")
			     .append(" CONTRACT_STARTTIME=?,")
			     .append(" CONTRACT_ENDTIME=?")
			     .append(" WHERE CONTRACT_ID=?");
		
		return getJdbcTemplate().update(updateSql.toString(),new PreparedStatementSetter(){

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, contractAD.getContractCode());
				ps.setString(2, contractAD.getContractName());
				ps.setInt(3, contractAD.getCustomerId());
				ps.setString(4, contractAD.getCustomerName());
				ps.setTimestamp(5, new Timestamp(contractAD.getEffectiveStartDate().getTime()));
				ps.setTimestamp(6, new Timestamp(contractAD.getEffectiveEndDate().getTime()));
				ps.setInt(7, Integer.valueOf(contractAD.getContractId()));
			}
			
		});
	}
	
	@Override
	public int[] updateContractADBackupBatch(final List<ContractAD> contractAdList) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE T_CONTRACT_AD_BACKUP SET ")
			     .append(" VALID_START=?,")
			     .append(" VALID_END=?")
			     .append(" WHERE ID=?");
		return getJdbcTemplate().batchUpdate(updateSql.toString(),new BatchPreparedStatementSetter(){

			@Override
			public int getBatchSize() {
				return contractAdList.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setTimestamp(1,new Timestamp(contractAdList.get(i).getStartDate().getTime()));
				ps.setTimestamp(2,new Timestamp(contractAdList.get(i).getEndDate().getTime()));
				ps.setInt(3,contractAdList.get(i).getId());
			}
		});
	}
	
	@Override
	public List<ContractBackup> query(String sql) {
		RowMapper<ContractBackup> rowMapper = BeanPropertyRowMapper.newInstance(ContractBackup.class);  
		return getJdbcTemplate().query(sql,rowMapper);
	}

	public List<ContractBackup> queryCustomerBackupById(Integer id) {
		String sql1 = "SELECT * from T_CONTRACT_BACKUP WHERE ID = ?";
		Object[] params = new Object[]{id};
		List<ContractBackup> list =null;
		RowMapper<ContractBackup> rowMapper = BeanPropertyRowMapper.newInstance(ContractBackup.class);  
		list = this.getJdbcTemplate().query(sql1,rowMapper);
		return list;
	}
	
	public Integer queryCurrentSequece(){
		String query = "SELECT T_CONTRACT_BACKUP_SEQ.NEXTVAL FROM DUAL ";
		return this.getJdbcTemplate().queryForInt(query);
	}

	@Override
	public int[] saveBatchContractAD(final List<ContractAD> contractAdList) {
		String insertSql = "INSERT INTO T_CONTRACT_AD_BACKUP(ID,CONTRACT_ID,AD_ID,VALID_START,VALID_END,RULE_ID,CONTRACT_CODE,CONTRACT_NAME,AD_NAME,AD_TYPE,RULE_NAME,CONTRACT_STARTTIME,CONTRACT_ENDTIME,ADVERTISERS_ID,ADVERTISERS_NAME,AD_TYPE_NAME) VALUES(T_CONTRACT_AD_BACKUP_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return this.getJdbcTemplate().batchUpdate(insertSql, new BatchPreparedStatementSetter(){

			@Override
			public int getBatchSize() {
				return contractAdList.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int index)
					throws SQLException {
				ContractAD contractAD = contractAdList.get(index);
				ps.setString(1,contractAD.getContractId()+"");
				ps.setInt(2,contractAD.getPositionId());
				setDate(contractAD.getStartDate());
				ps.setTimestamp(3,new Timestamp(contractAD.getStartDate().getTime()));
				setDate(contractAD.getEndDate());
				ps.setTimestamp(4,new Timestamp(contractAD.getEndDate().getTime()));
				
				ps.setInt(5,contractAD.getMarketingRuleId());
				
				ps.setString(6,contractAD.getContractCode());
				ps.setString(7,contractAD.getContractName());
				ps.setString(8,contractAD.getPositionName());
				ps.setInt(9,contractAD.getPositionTypeId());
				ps.setString(10,contractAD.getMarketingRuleName());
				setDate(contractAD.getEffectiveStartDate());
				ps.setTimestamp(11,new Timestamp(contractAD.getEffectiveStartDate().getTime()));
				setDate(contractAD.getEffectiveStartDate());
				ps.setTimestamp(12,new Timestamp(contractAD.getEffectiveStartDate().getTime()));
				
				ps.setInt(13,contractAD.getCustomerId());
				ps.setString(14,contractAD.getCustomerName());
				ps.setString(15,contractAD.getPositionTypeName());
			}
		});
	}

	@Override
	public List<ContractBackup> queryContractByContractNumber(String contractNumber) {
		String query ="SELECT * FROM T_CONTRACT_BACKUP WHERE CONTRACT_NUMBER='"+contractNumber+"'";
		return this.query(query);
	}

	@Override
	public List<TempContract> queryContractByContractIdFromBackup(
			Integer contractId) {

		

			/*"String query = SELECT TA.Characteristic_Identification,\n" +
			"       TA.DESCRIPTION,\n" + 
			"\t\t\t TA.Image_rule_Id,\n" + 
			"\t\t\t TA.video_rule_Id,\n" + 
			"\t\t\t TA.text_rule_id,\n" + 
			"\t\t\t TA.question_rule_id,\n" + 
			"\t\t\t TA.IS_HD,\n" + 
			"\t\t\t TA.IS_Add,\n" + 
			"\t\t\t TA.IS_LOOP,\n" + 
			"\t\t\t TA.Material_Number,\n" + 
			"\t\t\t TA.Delivery_MODE,\n" + 
			"\t\t\t TA.PRICE,\n" + 
			"\t\t\t TA.DISCOUNT,\n" + 
			"\t\t\t TA.State,\n" + 
			"\t\t\t TA.BACKGROUND_PATH,\n" + 
			"\t\t\t TA.Coordinate,\n" + 
			"\t\t\t TA.width_height,\n" + 
			"\t\t\t TA.Delivery_Platform,\n" + 
			"\t\t\t TA.start_time,\n" + 
			"\t\t\t TA.end_time,\n" + 
			"\t\t\t ta.create_time,\n" + 
			"\t\t\t ta.modify_time,\n" + 
			"\t\t\t TC.*\n" + 
			"  FROM (SELECT *\n" + 
			"          FROM (SELECT TAB.ID AS TABID,\n" + 
			"                       TAB.AD_ID AS POSITIONID,\n" + 
			"                       TAB.CONTRACT_ID,\n" + 
			"                       TAB.RULE_ID,\n" + 
			"                       TAB.ADVERTISERS_ID,\n" + 
			"                       TAB.ADVERTISERS_NAME,\n" + 
			"                       TAB.AD_TYPE,\n" + 
			"                       TAB.AD_TYPE_NAME AS adTypeName,\n" + 
			"                       TAB.VALID_START,\n" + 
			"                       TAB.VALID_END,\n" + 
			"                       TAB.RULE_NAME,\n" + 
			"                       TAB.VALID_START AS VALIDSTART,\n" + 
			"                       TAB.VALID_END AS VALIDEND,\n" + 
			"                       TAB.AD_NAME AS POSITIONNAME,\n" + 
			"\t\t\t\t\t\t\t\t\t\t\t TMR.ID AS mrId,\n" + 
			"                       TMR.RULE_NAME AS mrRuleName,\n" + 
			"                       TMR.START_TIME AS mrStartTime,\n" + 
			"                       TMR.END_TIME AS mrEndTime,\n" + 
			"                       TMR.RULE_ID AS MARKETINGRULEID,\n" + 
			"                       TMR.POSITION_ID AS MARKETINGRULEPOSITIONID,\n" + 
			"                       TMR.LOCATION_ID AS MARKETINGRULEAREAID,\n" + 
			"                       TMR.CHANNEL_ID AS MARKETINGRULECHANNELID,\n" + 
			"\t\t\t\t\t\t\t\t\t\t\t TMR.LOCATION_NAME AS mrAreaName,\n" + 
			"\t\t\t\t\t\t\t\t\t\t\t TMR.CHANNEL_NAME AS mrChannelName\n" + 
			"                  FROM T_CONTRACT_AD_BACKUP TAB\n" + 
			"                 INNER JOIN T_MARKETING_RULE TMR ON TAB.CONTRACT_ID =?\n" + 
			"                                                AND TAB.RULE_ID = TMR.ID) TABMR\n" + 
			"         INNER JOIN T_CONTRACT_BACKUP CB ON TABMR.CONTRACT_ID = CB.ID) TC\n" + 
			"  LEFT JOIN T_ADVERTPOSITION TA ON TC.POSITIONID = TA.ID";
*/
		String query = "SELECT * FROM VIEW_ADVERT_QUERY_CONTRACT WHERE p_view_param.set_param(?)=?";
		RowMapper<TempContract> rowMapper = BeanPropertyRowMapper.newInstance(TempContract.class);  
		return getJdbcTemplate().query(query,new Object[]{contractId,contractId},rowMapper);
	}

	@Override
	public List<ContractContractADRelation> getContractContractADRelation(
			Integer contractId) {
		String query = "select t.id AS contractId,ad.id adId,ad.rule_id AS ruleId from t_contract_backup t INNER JOIN t_contract_ad_backup ad ON ad.contract_id=? and ad.contract_id=t.id";
		RowMapper<ContractContractADRelation> rowMapper = BeanPropertyRowMapper.newInstance(ContractContractADRelation.class);  
		return getJdbcTemplate().query(query,new Object[]{contractId}, rowMapper);
	}
	
	@Override
	public int[] removeContractBackupAd(final List<Integer> ids){
		String query = "DELETE FROM t_contract_ad_backup WHERE ID=?";
		
		return getJdbcTemplate().batchUpdate(query, new BatchPreparedStatementSetter(){

			@Override
			public int getBatchSize() {
				return ids.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
					ps.setInt(1, ids.get(i));
			}
		});
	}

	@Override
	public int approvalContractBackup(ContractBackup contractBackup) {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE t_contract_backup SET ");
		sql.append(" METARIAL_PATH=?, ");
		sql.append(" Audit_Taff=?, ");
		sql.append(" Examination_Opinions=?, ");
		sql.append(" AUDIT_DATE=?, ");
		sql.append(" Operator_ID=? ");
		sql.append(" WHERE ID=? ");
		return getJdbcTemplate().update(sql.toString(),new Object[]{contractBackup.getMetarialPath(),contractBackup.getAuditTaff(),contractBackup.getExaminationOpinions(),contractBackup.getAuditDate(),contractBackup.getOperatorId(),contractBackup.getId()});
	}

	@Override
	public int updateCotractBackupStatus(Integer contractBackupId,
			Integer status) {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE t_contract_backup SET ");
		sql.append(" STATUS=? ");
		sql.append(" WHERE ID=? ");
		return getJdbcTemplate().update(sql.toString(),new Object[]{status,contractBackupId});
	}

	@Override
	public int removeContractAllInfoByContractId(final Integer contractBackupId) {
		
		
		/*return getJdbcTemplate().execute(sql,new CallableStatementCallback(){
			@Override
			public Object doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				cs.setInt(1,contractBackupId);
				cs.registerOutParameter(2, Types.INTEGER);
				cs.execute();
				return cs;
			}
			
		});*/
		Integer result = (Integer)getJdbcTemplate().execute(
				new CallableStatementCreator(){
					String sql ="{call p_advert_batch_remove_contract(?,?)}";
					@Override
					public CallableStatement createCallableStatement(
							Connection con) throws SQLException {
						con.prepareCall(sql);
						CallableStatement cs = con.prepareCall(sql);
						cs.setInt(1,contractBackupId);
						cs.registerOutParameter(2,OracleTypes.INTEGER);
						return cs;
					}
					
				},new CallableStatementCallback(){

					@Override
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException, DataAccessException {
						cs.execute();
						return cs.getInt(2);
					}
				}
		);
		
		if(result!=null){
			return result.intValue();
		}else{
			//处理失败
			return Constant.CALLABLE_EXECURE_ERROR;
		}
	}
		
	/**
	 * 暂时处理所选日期多8小时问题
	 * @param date
	 */
	private void setDate(Date date){
		if(date!=null){
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
		}
	}
}
