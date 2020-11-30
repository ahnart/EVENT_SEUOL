package com.epopkon.model;

import lombok.Data;

@Data
public class EventInfo {

	private int idx;
	private String userKey;
	private String userResult;
	private String resultDate;
	
	private int goodsIdx;
	private String goodsName;
	private String goodsImg;
	private int maxCount;
	private int currentCount;
	
	private String userIp;
	private String userAction;
	private String requestReferrer;
	private String requestUrl;
	private String requestParam;
	private String requestDate;
}
