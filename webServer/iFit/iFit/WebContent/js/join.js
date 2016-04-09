$(document).on("click","#joinBtn",function(e){
	//saveScroll();
	alert("회원가입이 완료되었습니다.");
	window.open("index.html", '_self');
});

$(function(){
	$(".agreeCheckBtn").parent("div").click(function(){
		if($(".agreeCheckBtn").hasClass("btnOn") === true){
			$(".agreeCheckBtn").removeClass("btnOn");
		}else{
			$(".agreeCheckBtn").addClass("btnOn");
		}
	});
});