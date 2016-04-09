function registPopupHandlerRegist(){
	$(document).on("click",".regist",function(e){
		$("#registPopup").popup("open");
	});
}

$(document).on("click","#cancelBtn",function(e){
	$("#registPopup").popup("close");
});

$(document).on("click","#writeBtn",function(e){
	$("#registPopup").popup("close");
});

registPopupHandlerRegist();