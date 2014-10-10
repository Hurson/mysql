package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.ImageSpecification;
import com.dvnchina.advertDelivery.model.TextSpecification;
import com.dvnchina.advertDelivery.model.VideoSpecification;

public interface GetPositionDao {
	public List<ImageSpecification> getImageFileSpeci(Integer positionId);
	public List<VideoSpecification> getVideoFileSpeci(Integer positionId);
	public List<TextSpecification> getMessageSpeci(Integer positionId);
	public List<AdvertPosition> getAllPosition();
}
