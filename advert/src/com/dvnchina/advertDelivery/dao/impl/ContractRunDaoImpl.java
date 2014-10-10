package com.dvnchina.advertDelivery.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.dao.ContractRunDao;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.ContractRun;

public class ContractRunDaoImpl extends JdbcDaoSupport implements ContractRunDao {

	@Override
	public int removeContractRun(final int removeContractRunId) {
		String removeSql = "DELETE FROM T_CONTRACT WHERE ID=?";
		return getJdbcTemplate().update(removeSql,new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1,removeContractRunId);
			}
		});
	}

	@Override
	public List<ContractRun> page(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= ?) where rownum_ >?";
	RowMapper<ContractRun> rowMapper = BeanPropertyRowMapper.newInstance(ContractRun.class);
	return getJdbcTemplate().query(pageSql, new Object[] { end, start },rowMapper);
	}

	@Override
	public int queryTotalCount(String sql) {
		return getJdbcTemplate().queryForInt(sql);
	}

	@Override
	public int saveContractRun(final ContractRun contractRun) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("INSERT INTO T_CONTRACT(ID,CONTRACT_NUMBER,CONTRACT_CODE,CUSTOMER_ID,CONTRACT_NAME,EFFECTIVE_END_DATE,SUBMIT_UNITS,FINANCIAL_INFORMATION,APPROVAL_CODE,APPROVAL_START_DATE,APPROVAL_END_DATE,METARIAL_PATH,STATUS,CREATE_TIME,OPERATOR_ID,OTHER_CONTENT,CONTRACT_DESC) VALUES(T_CONTRACT_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
		return getJdbcTemplate().update(updateSql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, contractRun.getContractNumber());
						ps.setString(2, contractRun.getContractCode());
						ps.setInt(3, contractRun.getCustomerId());
						ps.setString(4, contractRun.getContractName());
						ps.setDate(5, new Date(contractRun.getEffectiveStartDate().getTime()));
						ps.setDate(6, new Date(contractRun.getEffectiveEndDate().getTime()));
						ps.setString(7, contractRun.getSubmitUnits());
						ps.setString(8, contractRun.getFinancialInformation());
						ps.setString(9, contractRun.getApprovalCode());
						ps.setDate(10, new Date(contractRun.getApprovalStartDate().getTime()));
						ps.setDate(11,new Date(contractRun.getApprovalEndDate().getTime()));
						ps.setString(12, contractRun.getMetarialPath());
						ps.setInt(13, contractRun.getStatus());
						ps.setDate(14, new Date(contractRun.getCreateTime().getTime()));
						ps.setInt(15, contractRun.getOperatorId());
						ps.setString(16, contractRun.getOtherContent());
						ps.setString(17, contractRun.getContractDesc());
					}
				});
	}

	@Override
	public int updateContractRun(final ContractRun contractRun) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE T_CONTRACT_BACKUP")
			     .append(" SET CONTRACT_NUMBER = ?,")
			     .append(" CONTRACT_CODE=?,")
			     .append(" CUSTOMER_ID=?,")
			     .append(" CUSTOMER_NAME=?,")
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
			     .append(" WHERE ID=?");
				
		return getJdbcTemplate().update(updateSql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, contractRun.getContractNumber());
						ps.setString(2, contractRun.getContractCode());
						ps.setInt(3, contractRun.getCustomerId());
						ps.setString(4, contractRun.getContractName());
						ps.setDate(5, new Date(contractRun.getEffectiveStartDate().getTime()));
						ps.setDate(6, new Date(contractRun.getEffectiveEndDate().getTime()));
						ps.setString(7, contractRun.getSubmitUnits());
						ps.setString(8, contractRun.getFinancialInformation());
						ps.setString(9, contractRun.getApprovalCode());
						ps.setDate(10, new Date(contractRun.getApprovalStartDate().getTime()));
						ps.setDate(11,new Date(contractRun.getApprovalEndDate().getTime()));
						ps.setString(12, contractRun.getMetarialPath());
						ps.setInt(13, contractRun.getStatus());
						ps.setInt(14, contractRun.getOperatorId());
						ps.setString(15, contractRun.getOtherContent());
						ps.setString(16, contractRun.getContractDesc());
						ps.setInt(17,contractRun.getId());
					}
				});
	}

	@Override
	public List<ContractRun> query(String sql) {
		RowMapper<ContractRun> rowMapper = BeanPropertyRowMapper.newInstance(ContractRun.class);
		return getJdbcTemplate().query(sql,rowMapper);
	}

	@Override
	public List<ContractRun> getContractRunById(Integer id) {
		
		String sql1 = "SELECT * FROM T_CONTRACT WHERE ID = ?";
		Object[] params = new Object[]{id};
		List<ContractRun> list = null;
		RowMapper<ContractRun> rowMapper = BeanPropertyRowMapper.newInstance(ContractRun.class);
		list = this.getJdbcTemplate().query(sql1,params,rowMapper);
		return list;
	}

	@Override
	public List<AdvertPosition> queryPositionByContractIdFromRun(
			Integer contractId) {
		String sql = "";
		//return this.getJdbcTemplate().query(sql,);
		return null;
	}

	@Override
	public int copyContractBackup2ContractRun(final Integer contractBackupId) {
		/*
		 * 此种方式插入时不会有问题，但更新时会出现问题，如不加判断会出现主键冲突
		 * 	String sql ="INSERT  INTO t_contract(id, contract_number, contract_code, custom_id, contract_name, effective_start_date, effective_end_date, submit_units, financial_information, approval_code, approval_start_date, approval_end_date, metarial_path, status, create_time, operator_id, other_content, contract_desc)\n" +
			"SELECT  id, contract_number, contract_code, customer_id, contract_name, effective_start_date, effective_end_date, submit_units, financial_information, approval_code, approval_start_date, approval_end_date, metarial_path, status, create_time, operator_id, other_content, contract_desc FROM t_contract_backup WHERE ID=?";*/
		
		/*
		 * 此种方式spring jdbc中目前还没有支持方式
		 * String sql = "MERGE INTO t_contract r  USING (SELECT * FROM t_contract_backup  WHERE ID=78) b" +
			         " ON (r.id=b.id) WHEN MATCHED THEN " + 
			         " UPDATE SET contract_number=b.contract_number, contract_code=b.contract_code, CUSTOM_ID=b.customer_id, contract_name=b.contract_name, effective_start_date=b.effective_start_date, effective_end_date=b.effective_end_date, submit_units=b.submit_units, financial_information=b.financial_information, approval_code=b.approval_code, approval_start_date=b.approval_start_date, approval_end_date=b.approval_end_date, metarial_path=b.metarial_path, status=b.status, create_time=b.create_time, operator_id=b.operator_id, other_content=b.other_content, contract_desc=b.contract_desc" + 
			         " WHEN NOT MATCHED THEN " + 
			         " INSERT VALUES (b.id, b.contract_number, b.contract_code, b.customer_id, b.contract_name, b.effective_start_date, b.effective_end_date, b.submit_units, b.financial_information, b.approval_code, b.approval_start_date, b.approval_end_date, b.metarial_path, b.status, b.create_time, b.operator_id, b.other_content, b.contract_desc);";*/
		//return this.getJdbcTemplate().update(sql,new Object[]{contractBackupId});
		
		//String sql ="{call p_advert_sync_contract(?,?)}";
		/*return getJdbcTemplate().execute(sql,new CallableStatementCallback(){
			@Override
			public Object doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				cs.setInt(1,contractBackupId);
				cs.execute();
				//因为无返回值，故此处暂未空
				return null;
			}
			
		});*/
		Integer result = (Integer)getJdbcTemplate().execute(
				new CallableStatementCreator(){
					String sql ="{call p_advert_sync_contract(?,?)}";
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

	@Override
	public int copyContractBackupAD2ContractADRun(final Integer contractaADBackupId) {
		//String sql ="INSERT INTO t_contract_ad SELECT * FROM t_contract_ad_backup t1 WHERE t1.contract_id=?";
		/*String sql = 
			"MERGE INTO t_contract_ad adr USING (SELECT * FROM t_Contract_Ad_Backup adb WHERE adb.contract_id=?) b\n" +
			"ON (adr.id=b.id) WHEN MATCHED THEN\n" + 
			"UPDATE SET adr.contract_id=b.contract_id,adr.ad_id=b.ad_id,adr.valid_start=b.valid_start,adr.valid_end=b.valid_end,adr.rule_id=b.rule_id,adr.contract_code=b.contract_code,adr.contract_name=b.contract_name,adr.ad_name=b.ad_name,adr.ad_type=b.ad_type,adr.rule_name=b.rule_name,adr.contract_starttime=b.contract_starttime,adr.contract_endtime=b.contract_endtime,adr.advertisers_id=b.advertisers_id,adr.ad_type_name=b.ad_type_name,adr.advertisers_name=b.advertisers_name\n" + 
			"WHEN NOT MATCHED THEN\n" + 
			"INSERT VALUES (b.id,b.contract_id,b.ad_id,b.valid_start,b.valid_end,b.rule_id,b.contract_code,b.contract_name,b.ad_name,b.ad_type,b.rule_name,b.contract_starttime,b.contract_endtime,b.advertisers_id,b.ad_type_name,b.advertisers_name);";*/
		//return this.getJdbcTemplate().update(sql,new Object[]{contractaADBackupId});
		
		
		/*return getJdbcTemplate().execute(sql,new CallableStatementCallback(){
			@Override
			public Object doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				cs.setInt(1,contractaADBackupId);
				cs.execute();
				//因为无返回值，故此处暂未空
				return null;
			}
			
		});*/
		
		Integer result = (Integer)getJdbcTemplate().execute(
				new CallableStatementCreator(){
					String sql ="{call p_advert_sync_contractAD(?,?)}";
					@Override
					public CallableStatement createCallableStatement(
							Connection con) throws SQLException {
						con.prepareCall(sql);
						CallableStatement cs = con.prepareCall(sql);
						cs.setInt(1,contractaADBackupId);
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

	@Override
	public int updateContractRunStatus(Integer contractId, Integer status) {
		String sql ="UPDATE t_contract_ad t SET t.status=? WHERE t.id=?";
		return this.getJdbcTemplate().update(sql,new Object[]{status,contractId});
	}
	
	 

}
