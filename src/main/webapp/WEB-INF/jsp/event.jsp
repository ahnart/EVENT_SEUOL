<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ include file="/WEB-INF/jsp/_inc/_taglib.jsp" %>
<%@ include file="/WEB-INF/jsp/_inc/_head.jsp" %>

<body>
    <div id="wrap">
        <div class="bg"><img src="/resources/img/main_bg.png" alt="메인배경"></div>
        <div class="com_logo"><img src="/resources/img/com_logo.png" alt="고객사로고"></div>
        <div class="logo"><img src="/resources/img/logo.png" alt=""></div>
        <div class="headcopy">
           <!--<strong>대교</strong>가 드리는 행운 이벤트<br>-->
            1차: 첫 번째 럭키박스<br>
            2차: 두 번째 럭키박스<br>
            3차: 세 번째 럭키박스
        </div>
<!--
        <div class="event_date">2020.12.07(수)~2020.12.30(수)</div>
-->
        <div class="scratch_bord">
            <div class="goods">
				<c:if test="${!empty data.userResult && data.userResult eq 'W' }">
					<img id="bgImg" src="${data.goodsImg}" alt="당첨상품이미지">
				</c:if>
				<c:if test="${!empty data.userResult && data.userResult eq 'L' }">
					<p style="font-size: 50px;text-align: center;margin-top: 30px;font-weight: bold;">꽝</p>
				</c:if>
            </div>
            <div class="scratch">
                <ul class="scr_top">
                    <li><img src="/resources/img/scratch_01_st.gif" alt="" data-animated="/resources/img/scratch_01.gif" data-static="/resources/img/scratch_01_st.gif" class="hov-anim"></li>
                    <li><img src="/resources/img/scratch_02_st.gif" alt="" data-animated="/resources/img/scratch_02.gif" data-static="/resources/img/scratch_02_st.gif" class="hov-anim"></li>
                    <li><img src="/resources/img/scratch_03_st.gif" alt="" data-animated="/resources/img/scratch_03.gif" data-static="/resources/img/scratch_03_st.gif" class="hov-anim"></li>
                    <li><img src="/resources/img/scratch_04_st.gif" alt="" data-animated="/resources/img/scratch_04.gif" data-static="/resources/img/scratch_04_st.gif" class="hov-anim"></li>
                </ul>
                <ul class="scr_down">
                    <li><img src="/resources/img/scratch_05_st.gif" alt="" data-animated="/resources/img/scratch_05.gif" data-static="/resources/img/scratch_05_st.gif" class="hov-anim"></li>
                    <li><img src="/resources/img/scratch_06_st.gif" alt="" data-animated="/resources/img/scratch_06.gif" data-static="/resources/img/scratch_06_st.gif" class="hov-anim"></li>
                    <li><img src="/resources/img/scratch_07_st.gif" alt="" data-animated="/resources/img/scratch_07.gif" data-static="/resources/img/scratch_07_st.gif" class="hov-anim"></li>
                    <li><img src="/resources/img/scratch_08_st.gif" alt="" data-animated="/resources/img/scratch_08.gif" data-static="/resources/img/scratch_08_st.gif" class="hov-anim"></li>
                </ul>
            </div>
        </div>
        
        
        
		<c:if test="${empty data.userResult}">
		    <div class="st_popup">
		        <div class="reg_popup">
		            <h3>이벤트 참가</h3>
		            <p>이벤트에 참가 하시겠습니까?</p>
		            <button onClick="eventStart();">확인</button>
		        </div>
		    </div>
		</c:if>
        
	    <div class="result_popup">
	        <div class="end_popup">
	            <h3>당첨 결과</h3>
	            <div class="result_area">
	            <!--  참여결과가없을때 -->
					<c:if test="${empty data.userResult}">   
						<c:if test="${!empty data.userResult && data.userResult eq 'W' }">
							<div class="won_goods"><img src="${data.goodsImg}" alt="당첨상품이미지"></div>
							<div class="won_goods_name">${data.goodsName }</div>
							<p>에 당첨 되셨습니다 <br>축하합니다~!</p>
						</c:if>
						<c:if test="${!empty data.userResult && data.userResult eq 'L' }">
							<div class="lose_goods">아쉬워요. <br>꽝 입니다. </div>
						</c:if>
					</c:if>
					
						<!-- >> 참여결과가있고 당첨일때 -->
						<c:if test="${!empty data.userResult && data.userResult eq 'W' }">
							<div class="lose_goods">이미 참여 하셨습니다. <br>감사합니다. </div>
						</c:if>
						<!-- >> 참여결과가있고 꽝일때 -->
						<c:if test="${!empty data.userResult && data.userResult eq 'L' }">  
							<div class="lose_goods">이미 참여 하셨습니다. <br>감사합니다. </div>
						</c:if>
	            </div>
	            <button onClick="hideResult();">확인</button>
	        </div>
	    </div>
    </div>

	<script type="text/javascript">

	<c:if test="${empty data.userResult}">
		function eventStart(){
	        $("#wrap .logo img, .event_date, .headcopy, .com_logo").toggleClass("on")
	        
			$.post(
				'/eventStart',
				{'userKey':'${data.userKey}'},
				function(data){
					if(parseInt(data.result) == 1){
						$('.st_popup').hide();
				        $("#wrap .logo img, .event_date, .headcopy, .com_logo").toggleClass("on")
				        if(data.resultData.userResult == 'W'){
		            		$('.goods').html('<img src="'+data.resultData.goodsImg+'" alt="당첨상품이미지">');
							$('#bgImg').attr('src', data.resultData.goodsImg);
							var tags = '<div class="won_goods"><img src="'+data.resultData.goodsImg+'" alt="당첨상품이미지"></div>';
							tags += '<div class="won_goods_name">'+data.resultData.goodsName+'</div>';
							tags += '<p>에 당첨 되셨습니다 <br>축하합니다~!</p>';
							$('.result_area').html(tags);
					    }else{
					    	$('#bgImg').remove();
					    	$('.goods').html('<p style="font-size: 50px;text-align: center;margin-top: 30px;font-weight: bold;">꽝</p>');
					    	$('.result_area').html('<div class="lose_goods">아쉬워요. <br>꽝 입니다. </div>');
					    }
				        scrachClick();
					}else{
						alert("잠시후 다시 시도해주세요.");
					}
				},
				"json"
			);
		}

		function scrachClick(){
			$(".hov-anim").click(function() {
				$(this).attr("src", $(this).data("animated"));
				var idx = $('.hov-anim').index($(this));
				if(idx == 1 || idx == 2 || idx == 5 || idx == 6){
					$(this).addClass('clicked');	
				}

				if($('.clicked').length == 4){
					$('.result_popup').show();
				}
			});
		}
	</c:if>

	 // 참여 정보가있을떄
	<c:if test="${!empty data.userResult}">
		$(function(){
			// 결과 팝업 호출
			$('.result_popup').show();
			//  로고 애니메이션??  올라오는 그거 활성화
			$("#wrap .logo img, .event_date, .headcopy, .com_logo").toggleClass("on")
			// 스크래치 전부 열어본상태로 변경
			$.each($(".hov-anim"), function(){
				$(this).attr('src', $(this).data("animated"));
			});
		});
	</c:if>
	function hideResult(){
		$('.result_popup').hide();
	}

	</script>


</body>

</html>
