$(document).ready(function() { 
	$("#preView").click(function(){
		$("#leftDoor").animate({ "right": "40%", "opacity": "0.25"}, "slow",function(){
			$("#preView").hide();
			alert("옷장 중비중");
		} );
		$("#rightDoor").animate({ "left": "40%", "opacity": "0.25" }, "slow" );
	});
});