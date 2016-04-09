$(document).on("click","#requestOrder",function(e){
	saveScroll();
	window.open("orderTracking.html", '_self'); 
});
$(document).on("click","#requestManyQnA",function(e){
	saveScroll();
	window.open("manyQuestion.html", '_self'); 
});
$(document).on("click","#requestOneByOne",function(e){
	saveScroll();
	window.open("oneByoneQnA.html", '_self'); 
});
$(document).on("click","#requestUsingApp",function(e){
	saveScroll();
	alert("이용안내 준비중"); 
});
$(document).on("click","#joinBtn",function(e){
	saveScroll();
	window.open("join.html", '_self');
});
$(document).on("click","#loginBtn",function(e){
	$(this).hide();
	$(this).siblings().hide();
	$(this).siblings("p#loginLabel2").show();
	$("#userImage").show();
});
$(document).on("click","#logoutBtn",function(e){
	$("#loginBtn").show();
	$("#loginBtn").siblings().show();
	$("#loginLabel2").hide();
	$("#userImage").hide();
});

function idSearchPopupHandlerRegist(){
	$(document).on("click","#idSearch",function(e){
		$("#idSearchPopup").popup("open");
		$("#idSearchPopup").bind({
			popupafterclose: function(event, ui) {
				$("#idSearchTitle").val("");
			}
		});
	});
}
function pwSearchPopupHandlerRegist(){
	$(document).on("click","#pwSearch",function(e){
		$("#pwSearchPopup").popup("open");
		$("#pwSearchPopup").bind({
			popupafterclose: function(event, ui) {
				$("#pwSearchTitle").val("");
			}
		});
	});
}
function updateUserInfoPopupHandlerRegist(){
	$(document).on("click","#updateUserInfo",function(e){
		$("#updateUserInfoPopup").popup("open");
		$("#updateUserInfoPopup").bind({
			popupafterclose: function(event, ui) {
				$("#updateUserInfoTitle").val("");
			}
		});
	});
}

$(document).on("click","#idSearchConfirm",function(e){
	$("#idSearchPopup").popup("close");
});

$(document).on("click","#pwSearchConfirm",function(e){
	$("#pwSearchPopup").popup("close");
});

$(document).on("click","#updateUserInfoConfirm",function(e){
	$("#updateUserInfoPopup").popup("close");
//	saveScroll();
	window.open("updateUserInfo.html", '_self'); 
});

idSearchPopupHandlerRegist();
pwSearchPopupHandlerRegist();
updateUserInfoPopupHandlerRegist();