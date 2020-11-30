package com.epopkon.service.impl;

import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epopkon.mapper.EventMapper;
import com.epopkon.model.EventInfo;
import com.epopkon.service.EventService;

import common.util.CommonUtils;
import common.util.HttpUtils;

@Service(value="EventService")
public class EventServiceImpl implements EventService{

	@Resource EventMapper mapper;
	public EventMapper getMapper() {
		return this.mapper;
	}
	
	


	@Value("#{config['lose.percent']}")
	private String losePercent;
	
	@Override
	public int createKey(String userKey) {
		EventInfo param = new EventInfo();
		param.setUserKey(userKey);
		return mapper.createKey(param);
	}
	@Override
	public void insertLog(String userKey, String userAction, HttpServletRequest request) {
		EventInfo param = new EventInfo();
		param.setUserKey(userKey);
		param.setUserAction(userAction);
		param.setUserIp(HttpUtils.getRemoteAddr(request));
		param.setRequestReferrer(request.getHeader("referer"));
		param.setRequestUrl(request.getRequestURI());
		param.setRequestParam(getQueryString(request));
		
		mapper.insertLog(param);
	}
	
	@Override
	public EventInfo getUserInfo(String userKey) {
		EventInfo param = new EventInfo();
		param.setUserKey(userKey);
		return mapper.getUserInfo(param);
	}
	
	@Override
	public int getLeftCnt() {
		return mapper.getLeftCnt();
	}
	@Override
	public EventInfo getRandGoods() {
		return mapper.getRandGoods();
	}
	@Override
	public int updateUserResult(EventInfo param) {
		return mapper.updateUserResult(param);
	}
	@Override
	public int updateWonCnt(EventInfo param) {
		return mapper.updateWonCnt(param);
	}

	@Override
	@Transactional
	public EventInfo eventStart(String userKey) {
		EventInfo resultData = this.getUserInfo(userKey);
		int leftCnt = this.getLeftCnt();		// 당첨가능 상품 수량
		
		String result = "";
    	int value = (int)(Math.random()*10);   // 0 ~ 9
    	if(CommonUtils.isNullOrEmpty(resultData.getUserResult())) {
        	if(value < Integer.parseInt(losePercent) || leftCnt < 1) {
        		// 당첨가능 수량이없거나
        		result = "L";
        		resultData.setUserResult(result);
        		this.updateUserResult(resultData);
        	}else {
        		result = "W";
        		EventInfo goodsData = this.getRandGoods();
        		if(this.updateWonCnt(goodsData) == 1) {
        			// 상품 카운트 증가 성공
            		resultData.setUserResult(result);
            		resultData.setGoodsIdx(goodsData.getGoodsIdx());
            		resultData.setGoodsName(goodsData.getGoodsName());
            		resultData.setGoodsImg(goodsData.getGoodsImg());
            		if(this.updateUserResult(resultData) != 1) {
            			//업데이트 실패(해당키로 선입처리됨)
            			resultData = null;
            		}
        		}
        	}
    	}else {
    		resultData = null;
    	}
		return resultData;
	}
	
	
	
    @SuppressWarnings("unchecked")
    public String getQueryString(HttpServletRequest request){
    	String queryString = "";
    	if(request.getMethod().equals(RequestMethod.GET.toString())){
    		if(!CommonUtils.isNullOrEmpty(request.getQueryString())){
    			queryString = "?"+ request.getQueryString();	
    		}
    	}else{
    		StringBuilder sb = new StringBuilder("?");
    		for(Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();){
    			String param = (String) e.nextElement();
    			sb.append(param).append("=").append(request.getParameter(param)).append("&");
    		}
    		queryString = sb.toString().substring(0, sb.length()-1);
    	}
    	return queryString;
    }
}
