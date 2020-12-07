package com.epopkon.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epopkon.model.EventInfo;
import com.epopkon.service.EventService;

import common.util.CommonUtils;
import common.util.HttpUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class EventController {
	
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	@Resource EventService eventService;
	
	
	/* 임시용 페이지 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) {
		
		logger.info("REQUEST IP :: " + HttpUtils.getRemoteAddr(request));
		return "index";
	}
	
	
	
	
	/* 이벤트 키 생성 */
	@RequestMapping(value="/createKey", method=RequestMethod.POST)
	public String createKey(HttpServletRequest request, Model model) throws Exception{
		int cnt = CommonUtils.replaceNull(request.getParameter("cnt"), 100);
		
		int count = 0;
		while(count < cnt) {
			count += eventService.createKey(RandomCode(12));
		}
		model.addAttribute("count", count);
		return "jsonView";
	}
	

    private String RandomCode(int length) {
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        String randomChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for(int i = 0; i < length; i++)
            sb.append(randomChars.charAt(r.nextInt(randomChars.length())));

        return sb.toString();
    }
    
    
    /* 이벤트 페이지 */
    @RequestMapping(value="/event/{userKey}", method=RequestMethod.GET)
    public String event(HttpServletRequest request, HttpSession session, Model model, @PathVariable("userKey") String userKey) throws Exception{
    	String view = "event";
    	EventInfo data = eventService.getUserInfo(userKey);
    	if(CommonUtils.isNullOrEmpty(data)) {
    		// 키정보 안맞음
    		view = "redirect:/";
    	}else {
    		session.setAttribute("USER_KEY", userKey);
    		model.addAttribute("data", data);
    		
    		eventService.insertLog(userKey, "페이지호출", request);
    	}
    	return view;
    }
    
    
    @RequestMapping(value="/eventStart", method=RequestMethod.POST)
    public String eventStart(HttpServletRequest request, HttpSession session, Model model) throws Exception{
    	String userKey = CommonUtils.replaceNull(request.getParameter("userKey"), "");
    	String sessionUserKey = CommonUtils.replaceNull(session.getAttribute("USER_KEY"), "");
    	String winResult = "";
    	int result = 0;
    	if(CommonUtils.isNullOrEmpty(userKey)) {
    		// 키값없음
    		result = -1;
    	}else if(!userKey.equals(sessionUserKey)) {
    		// 세션키값 미일치
    		result = -2;
    	}else {
    		EventInfo resultData = eventService.eventStart(userKey);
    		if(!CommonUtils.isNullOrEmpty(resultData)) {
    			result = 1;
    			model.addAttribute("resultData", resultData);
    			winResult = resultData.getUserResult();
    		}else {
    			result = -3;
    		}
    	}

		eventService.insertLog(userKey, "이벤트참여:"+result+":"+winResult, request);
		model.addAttribute("result", result);
    	return "jsonView";    	
    }
}
