package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.ContractRun;

/**
 * 合同运行期表
 * @author lester
 */
public interface ContractRunDao {
	/**
	 * 新增
	 * @param contractRun
	 * @return
	 */
	public int saveContractRun(final ContractRun contractRun);
	/**
	 * 删除
	 * @param contractRunId
	 * @return
	 */
	public int removeContractRun(final int contractRunId);
	/**
	 * 更新
	 * @param ContractRun
	 * @return
	 */
	public int updateContractRun(final ContractRun contractRun);
	/**
	 * 分页查询
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ContractRun> page(String sql, int start, int end);
	/**
	 * 统计总数
	 * @param sql
	 * @return
	 */
	public int queryTotalCount(String sql);
	/**
	 * 按条件查询
	 * @param sql
	 * @return
	 */
	public List<ContractRun> query(String sql);
	
	/**
	 * 按照资源合同号即合同主键查询
	 * @param id
	 * @return
	 */
	public List<ContractRun> getContractRunById(Integer id);
	
	/**
	 * 从合同主表中查询相关记录
	 * @param contractId
	 * @return
	 */
	public List<AdvertPosition> queryPositionByContractIdFromRun(Integer contractId);
	/**
	 * 审核通过后将备份表中记录加入运行期表
	 * @param contractBackupId
	 * @return
	 */
	public int copyContractBackup2ContractRun(final Integer contractBackupId);
	/**
	 * 审核通过后将关系备份表中记录加入运行期表
	 * @param contractBackupId
	 * @return
	 */
	public int copyContractBackupAD2ContractADRun(final Integer contractaADBackupId);
	/**
	 * 修改用户状态
	 * @param contractId
	 * @param status
	 * @return
	 */
	public int updateContractRunStatus(Integer contractId,Integer status);
}
