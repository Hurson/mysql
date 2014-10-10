package com.avit.ads.syncreport.dao;

import java.util.Date;
import java.util.List;

import com.avit.ads.syncreport.bean.AdPlaylistGis;
import com.avit.ads.syncreport.bean.TReportData;
import com.avit.common.page.dao.BaseDao;

public interface ReportDao extends BaseDao {

	public List<AdPlaylistGis> queryAdPlaylistGisList(String positionCode,Date startTime ,Date endTime);
	public boolean insertReport(TReportData report);
	public boolean deleteReportByDate(Date startDate,Date endDate);
}
