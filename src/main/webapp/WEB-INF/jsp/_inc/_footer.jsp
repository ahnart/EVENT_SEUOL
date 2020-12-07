<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/_inc/_taglib.jsp" %>




		<!-- footer -->
		<footer class="clear footer">

			<div class="f_logo_copyright">
				<p><img src="/resources/images/footer/footer_logo.png" alt="Globione"></p>

				<p class="copyright">© COPYRIGHTS 2020 Globione, Inc. ALL RIGHTS RESERVED. </p>
			</div>

			<div class="clear f_r_btn">
				<div class="language_select_area">		
					<button type="button" data-code="kr"><spring:message code="lang.kor" /></button>			
					<button type="button" data-code="en"><spring:message code="lang.eng" /></button>
				</div>

				<a href="#p_top" class="btn_top"><spring:message code="btn.top" /></a>
			</div>
			
		</footer>
		<!-- //footer -->
		
		<script type="text/javascript">

			$(function(){
				$.each($('.language_select_area button'), function(i, target){
					if($(this).attr('data-code') == location.pathname.substring(1,3)){
						$(this).hide();
					}
				});

				
				$('.language_select_area button').click(function(){
		    		location.href = "/"+$(this).attr('data-code')+location.pathname.substring(3,location.pathname.lenght)+location.search;
					
				});
			});

		</script>