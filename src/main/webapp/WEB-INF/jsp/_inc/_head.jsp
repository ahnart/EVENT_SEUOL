<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/_inc/_taglib.jsp" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <title>스크래치이벤트</title>
    <link rel="stylesheet" href="/resources/css/style.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/style.css" type="text/css">
    <script src="/resources/js/jquery.min.js"></script>
    <script src="/resources/js/main.js"></script>

	<sec:csrfMetaTags/>
	
	<script type="text/javascript">
		$(function(){
			var header=$("meta[name='_csrf_header']").attr('content');
			var token=$("meta[name='_csrf']").attr('content');
			if(header && header.length > 0){
		        $.ajaxSetup({
		            beforeSend: function (xhr, settings) {
		            	xhr.setRequestHeader(header, token);
		            },
		            statusCode : {
		            	403: function() {
		                    alert("Forbidden Error");
		                    location.reload();
		                },
		                500: function() {
		                    location.reload();
		                }
		            }
		        });
			}else{
		        $.ajaxSetup({
		            statusCode : {
		            	403: function() {
		                    alert("Forbidden Error");
		                    location.reload();
		                },
		                500: function() {
		                    location.reload();
		                }
		            }
		        });
			}
	
		});
	</script>
</head>
