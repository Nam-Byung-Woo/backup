$(function(){
	$("#backBtn").click(function(){
		if($("#menuBar").length>0){
			$("#menuBar").show();
		}
		history.back();
	});
});