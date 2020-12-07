package com.epopkon.mapper;

import javax.annotation.Resource;

import com.epopkon.model.EventInfo;

public interface EventMapper {
	
	int createKey(EventInfo param);
	
	void insertLog(EventInfo param);
	
	EventInfo getUserInfo(EventInfo param);
	
	int getLeftCnt();
	
	EventInfo getRandGoods();
	int updateWonCnt(EventInfo param);
	int updateUserResult(EventInfo param);
}
