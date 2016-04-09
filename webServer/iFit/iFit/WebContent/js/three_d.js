$(document).ready(function(){
	$(".threeD_productDiv").click(function() {
		var prID = 0;
		window.open("moreInfoPage.html?prId="+prID, '_self');
	})
});

$(document).on("click",".threeD_imgDiv",function(e){
	var prID = 0; // $(e.target).class() == "threeD_productDiv"    --> prID = $(e.target).id(); e.stopPropagation(); 
	// id에 고유 상품 번호를 넣어서...
	saveScroll();
	showLoading();
	window.open("moreInfoPage.html?prId="+prID, '_self');
});