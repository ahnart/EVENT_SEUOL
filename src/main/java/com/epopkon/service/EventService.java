package com.epopkon.service;

import javax.servlet.http.HttpServletRequest;

import com.epopkon.model.EventInfo;

public interface EventService {

	int createKey(String userKey);
	
	void insertLog(String userKey, String userAction, HttpServletRequest request);
	
	EventInfo getUserInfo(String userKey);
	
	int getLeftCnt();
	

	EventInfo getRandGoods();
	int updateWonCnt(EventInfo param);
	int updateUserResult(EventInfo param);
	
	

	EventInfo eventStart(String userKey);
	
}
