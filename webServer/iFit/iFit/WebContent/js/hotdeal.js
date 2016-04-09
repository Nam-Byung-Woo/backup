$(document).on("click",".hotDeal_item",function(e){
	var prID = 0; // $(e.target).class() == "hotDeal_Item"    --> prID = $(e.target).id(); e.stopPropagation(); 
	// id에 고유 상품 번호를 넣어서... 
	
	saveScroll();
	showLoading();
	window.open("moreInfoPage.html?prId="+prID, '_self');
});