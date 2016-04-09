var select_star;
var select_item;
function starEventhandlerRegist() {
	$(document).on("click",".starItem",
			function(e) {

				switch ($(e.target).attr("id")) {
				case "star_1":
					select_star = 1;
					break;
				case "star_2":
					select_star = 2;
					break;
				case "star_3":
					select_star = 3;
					break;
				case "star_4":
					select_star = 4;
					break;
				case "star_5":
					select_star = 5;
					break;
				}
				$(".starItem").attr("src", "img/mypage/emptyStar.png");
				$(".starItem").each(
						function(index) {
							if (index < select_star) {
								$($(".starItem")[index]).attr("src",
										"img/mypage/fullStar.png");
							}

						});
			});
}
function registPopupHandlerRegist(){
	$(document).on("click",".regist",function(e){
		$("#registPopup").popup("open");
		//select_item = $(e.target).attr("id")  ---> 나중에 선택된 제품의 고유번호를 id로 주는것도 나을듯.. 
	});
}

$(document).on("click","#cancelBtn",function(e){
	$("#registPopup").popup("close");
});

$(document).on("click","#writeBtn",function(e){
	$("#registPopup").popup("close");
});

starEventhandlerRegist();
registPopupHandlerRegist();