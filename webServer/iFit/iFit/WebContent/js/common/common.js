function init_header() {
	$(".headerIcon").each(function(index) {
		switch (index) {
		case 0:
			$(this).attr("src", " img/header/likeItem.png");
			break;
		case 1:
			$(this).attr("src", " img/header/shoppingCart.png");
			break;
		default:
			break;
		}
	});
	$("#searchBar").attr("value", "");
}
function init_footer() {
	$(".footerIcon").each(function(index) {
		switch (index) {
		case 0:
			$(this).attr("src", " img/footer/home.png");
			break;
		case 1:
			$(this).attr("src", " img/footer/category.png");
			break;
		case 2:
			$(this).attr("src", " img/footer/threeD.png");
			break;
		case 3:
			$(this).attr("src", " img/footer/hotDeal.png");
			break;
		case 4:
			$(this).attr("src", " img/footer/myPage.png");
			break;
		default:
			break;
		}
	});
	$("#searchBar").attr("value", "");
}
function homeBtn_Handler(e) {
	showAd();
	init_header();
	init_footer();
	$("#homeBtn").attr("src", " img/footer/homeClick.png");
	$.mobile.changePage("#main_home");
}
function categoryBtn_Handler(e) {
	showAd();
	init_header();
	init_footer();
//	localStorage.setItem("isCategory", "true");
	$("#categoryBtn").attr("src", " img/footer/categoryClick.png");
	$.mobile.changePage("#categoryPage");
}
function threeDBtn_Handler(e) {
	init_header();
	init_footer();
	$("#threeDBtn").attr("src", " img/footer/threeDClick.png");
	$.mobile.changePage("#threeDPage");
	hideAd();
}

function hotDealBtn_Handler(e) {
	init_header();
	init_footer();
	$("#hotDealBtn").attr("src", " img/footer/hotDealClick.png");
	$.mobile.changePage("#hotDealPage");
	hideAd();
}
function myPageBtn_Handler(e) {
	init_header();
	init_footer();
	$("#myPageBtn").attr("src", " img/footer/myPageClick.png");
	$.mobile.changePage("#myPage");
	hideAd();
}

function headBar_handler(select){
	hideAd();
	init_header();
	init_footer();
	stopSearchBar();
	if(select=="closet"){
		saveScroll();
		location = "codiPage.html";
//		location = "closetGallerySample.html";
		//$.mobile.changePage("#codiPage");
	}else if(select=="zzim"){
		//$("#menuBar").hide(); 일단보류. 백헤더관련 확정되면 적용
		$("#likeItem").attr("src", " img/header/likeItemClick.png");
		$.mobile.changePage("#zzimPage");
	}else if(select=="cart"){
		$("#shoppingCart").attr("src", " img/header/shoppingCartClick.png");
		$.mobile.changePage("#cartPage");
	}
}

function hideAd(){
	$("#adWrap").hide();
}
function showAd(){
	$("#adWrap").show();
}

function searchInputBtn_Handler(e) {
	$("#searchBarClickView").show();
	$("#tagDiv").show();
	$("#footBalckDiv").show();
}
function searchBarBtn_Handler(e) {
	hideAd();
//	var queryValue = $("#searchBar").attr("value");
	init_footer();
	stopSearchBar();
//	console.log("검색 질의어 : " + queryValue);
//	localStorage.setItem("isCategory", "false");
	$.mobile.changePage("#productList");
}
function stopSearchBar() {
	$("#tagDiv").hide();
	$("#footBalckDiv").hide();
	$("#searchBarClickView").hide();
}


$(function() {
	$(".middleEach").click(function() {
		$.mobile.changePage("#productList");
	});
	
	$(".hotContents").click(function() {
		var prID=0;
		window.open("moreInfoPage.html?prId="+prID, '_self');
	});
	
	$("[data-role='header'], [data-role='footer']").toolbar();
	$("#searchBar").click(searchInputBtn_Handler);

	$("#hashRecommandWrapDiv").click(function(e) {
		e.stopPropagation();
	});
	$("#menuBar").click(function(e) {
		e.stopPropagation();
	});
	$(".recommandItems").click(function(e) {
		$("#searchBar").attr("value", $(this).text());
		e.stopPropagation();
	});
	$(".carousel-inner>.item").on("swipeleft", function(e) {
		$("#myCarousel").carousel("next");
	});
	$(".carousel-inner>.item").on("swiperight", function(e) {
		$("#myCarousel").carousel("prev");
	});
	$(".eachPage").on("pagebeforehide", function(event) {
		$("#adWrap").hide();
	});
	
	$(".haveADContent").on("pagebeforeshow", function(event) {
		$("#adWrap").fadeIn();
	});

	if (!($(".ui-page-active").hasClass("haveADContent"))) {
		$("#adWrap").hide();
	}
	$(document).on("pageshow",function(e){
		var pageName = $($(window.location.href.split("/")).last()[0].split(".html")).last()[0].replace("&","");
		console.log(pageName);
		
		switch(pageName){
			case "":
			init_header();
			init_footer();
			$(homeBtn).attr("src", " img/footer/homeClick.png");
			break;
			case "#categoryPage":
				init_header();
				init_footer();
				$("#categoryBtn").attr("src", " img/footer/categoryClick.png");
				break;
			case "#threeDPage":
				init_header();
				init_footer();
				$("#threeDBtn").attr("src", " img/footer/threeDClick.png");
				break;
			case "#hotDealPage":
				init_header();
				init_footer();
				$("#hotDealBtn").attr("src", " img/footer/hotDealClick.png");
				break;
			case "#myPage":
				init_header();
				init_footer();
				$("#myPageBtn").attr("src", " img/footer/myPageClick.png");
				break;
			case "#cartPage":
				init_header();
				init_footer();
				$("#shoppingCart").attr("src", " img/header/shoppingCartClick.png");
				break;
			case "#zzimPage":
				init_header();
				init_footer();
				$("#likeItem").attr("src", " img/header/likeItemClick.png");
				break;
			case "#productList":
				init_header();
				init_footer();
				$("#categoryBtn").attr("src", " img/footer/categoryClick.png");
				
//				var prePageName = localStorage.getItem("isCategory");
//				if(prePageName == "true"){
//					init_footer();
//					$("#categoryBtn").attr("src", " img/footer/categoryClick.png");
//				}
				
				break;
			case "#codiPage":
				break;
			default :
				init_footer();
				break;
		}
	});
});
function saveScroll(){
//	console.log("saveScroll");
//	console.log($($(window.location.href.split("/")).last()[0].split(".html")).last()[0].replace("&",""));
//	if($($(window.location.href.split("/")).last()[0].split(".html")).last()[0].replace("&","") =="#categoryPage")
//		localStorage.setItem("prepageName", "category");
}

$.urlParam = function(name){
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	return results[1] || 0;
};

function showLoading(){
	$("#loadingDiv").show();
	setTimeout(function(){
		$("#loadingDiv").hide();
	},1500);
}