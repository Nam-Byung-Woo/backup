$(document).on("click",".cart_photo",function(e){
	var prID = 0; // $(e.target).class() == "hotDeal_Item"    --> prID = $(e.target).id(); e.stopPropagation(); 
	// id에 고유 상품 번호를 넣어서... 
	
	saveScroll();
	window.open("moreInfoPage.html?prId="+prID, '_self');
});

$(document).on("click",".cart_btnDiv>img",function(e){
	var prID = 0; // $(e.target).class() == "hotDeal_Item"    --> prID = $(e.target).id(); e.stopPropagation(); 
	// id에 고유 상품 번호를 넣어서... 
	alert("바로구매 & 장바구니");
	
});

function reCal(){
	var totalPrice=0;
	var num=0;
	$(".realPrice").each(function() {
		totalPrice+=parseInt($(this).text());
		num++;
	});
	var deliveryPrice =0;
	if(num>0){
		deliveryPrice = 15000;
	}
	$("#deliveryPrice").text(deliveryPrice);
	$("#sumPrice").text(totalPrice);
	totalPrice += deliveryPrice;
	$("#totalPrice").text(totalPrice);
}

function removeCart(productId){
	//ajax요청후
	$("#cartPage").find("#"+productId).remove();
	reCal();
}

function itemPriceSum(){
	var sum = 0;
	$(".item_price_list").each(function(){
		sum += removeCurrencyMark($(this).children("p:last-child").html());
	});
	$("#item_price_sum").html(addCurrencyMark(sum));
	
}
function calculateAll(){
	var itemName;
	var itemCnt;
	var itemPrice;
	var calculateHtml="";
	$(".basicItem .itemOn").each(function(){
		itemName = $(this).children(".itemOptionArea").children("div:first-child").children("p").html();
		itemCnt = $(this).children(".itemOptionArea").children("div:last-child").children("p:first").html();
		itemPrice = $(this).children(".itemOptionArea").children("div:last-child").children("p:last").html();
		$(".dynamic_price").remove();
		calculateHtml += '<div id="item_price_1" class="dynamic_price item_price_list font-size-13 mb10 render" data-render="width,height,margin-bottom">';
		calculateHtml += '<p class="font-size-13 render dib" data-render="font-size">' + itemName;
		if(itemCnt>1){
			calculateHtml += '<span> x</span><span>' + itemCnt + '</span></p>';
			itemPrice = addCurrencyMark(itemCnt * removeCurrencyMark(itemPrice));
		}
		calculateHtml += '<p class="font-size-13 render dib fr" data-render="font-size">' + itemPrice + '</p>';
		calculateHtml += '</div>';
		
		$(".cart_item_all .itemOptionArea #delivery_price").before(calculateHtml);
		
		itemPriceSum();
	});
	renderStart(".dynamic_price");
	renderStart(".dynamic_price p");
}

function addCurrencyMark(price){
	return "￦"+price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
function removeCurrencyMark(price){
	return parseInt(price.replace(/[^0-9]/g,""));
}


$(document).ready(function() {
//	reCal();
	
	$(".numUp").click(function(){
		if($(this).parents(".cart_item").hasClass("itemOn") === true) {
			var amount = $(this).parents().children(".cartAmount");
			var count = $(amount).text();
			$(amount).text(parseInt(count)+1);
			calculateAll();
		}

	})
	
	$(".numDown").click(function(){
		if($(this).parents(".cart_item").hasClass("itemOn") === true) {
			var amount = $(this).parents().children(".cartAmount");
			var count = $(amount).text();
			if(parseInt(count)>1)	$(amount).text(parseInt(count)-1);
			calculateAll();
//			}
		}
	})
	
	
	// 웨딩쪽 시연때문에 임시로
	$(".numUp_w").click(function(){
		if($(this).parents(".cart_item").hasClass("itemOn") === true) {
			var amount = $(this).parents().children(".cartAmount");
			var count = $(amount).text();
			$(amount).text(parseInt(count)+1);
		}
	})
	
	$(".numDown_w").click(function(){
		if($(this).parents(".cart_item").hasClass("itemOn") === true) {
			var amount = $(this).parents().children(".cartAmount");
			var count = $(amount).text();
			if(count!=0)	$(amount).text(parseInt(count)-1);
		}
	})

});


//16.03.22추가 작업

$(function(){
	$("#selectSize li").click(function(){
		if($(this).parents(".cart_item").hasClass("itemOn") === true) {
			$("#selectSize li").each(function(){
				$(this).removeClass("btnOn");
			});
			$(this).addClass("btnOn");
		}
	});
	$(".itemOptionCheckBtn").click(function(){
		if($(this).parents(".cart_item").hasClass("itemOn") === true) {
			$(this).parents(".cart_item").removeClass("itemOn");
			$(this).parents(".cart_item").addClass("itemOff");
			$("#item_price_"+$(this).parents(".cart_item").attr("data-item-idx")).hide();
			calculateAll();
		}else if($(this).parents(".cart_item").hasClass("itemOff") === true) {
			$(this).parents(".cart_item").removeClass("itemOff");
			$(this).parents(".cart_item").addClass("itemOn");
			$("#item_price_"+$(this).parents(".cart_item").attr("data-item-idx")).show();
			calculateAll();
		}
	});
	$(".itemDeleteBtn").click(function(){
		if($(this).parents("#cartPage").length>0){
			//	장바구니
			if(confirm("장바구니 상품을 삭제하시겠습니까?")){
				$(this).parents(".cart_item").remove();
				calculateAll();
			}
		}else if($(this).parents("#zzimPage").length>0){
			if(confirm("찜상품을 삭제하시겠습니까?")){
				$(this).parents(".cart_item").remove();
			}
		}
	});
})