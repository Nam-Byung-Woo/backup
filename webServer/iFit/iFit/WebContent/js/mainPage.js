$(document).on("click",".mainpage_productDiv",function(e){
	saveScroll();
	var prID = 0; // $(e.target).class() == "mainpage_productDiv"    --> prID = $(e.target).id(); e.stopPropagation(); 
				// id에 고유 상품 번호를 넣어서...
	showLoading();
	window.open("moreInfoPage.html?prId="+prID, '_self');
});