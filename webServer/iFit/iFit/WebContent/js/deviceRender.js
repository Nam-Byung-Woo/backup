$(document).ready(function(){
	renderStart(".render");
});

function renderStart(target){
	var design_width = 375;
	var design_height = 667;
	
	var device_width = $(window).width();
	var device_height = $(window).height();
	
	var width_ratio = device_width / design_width;
	var height_ratio = device_height / design_height;
	var renderData="";
	var ratio=0;
	
	$(target).each(function(){
		renderData = $(this).attr("data-render").split(",");
		if($(this).attr("data-render-ratio")=="height"){
			ratio = height_ratio;
		}else{
			ratio = width_ratio;
		}
		for(var i=0; i<renderData.length; i++){
			switch(renderData[i]){			
			// width 기준으로만 조절(header,footer는 height) 
				case "width": 
					$(this).css("width", (($(this).css("width").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "height":
					$(this).css("height", (($(this).css("height").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "padding-top":
					if($(this).hasClass("eachPage") === true){
						$(this).css("padding-top", (($(this).css("padding-top").replace(/[^-\d\.]/g, ''))*height_ratio)+"px");
					}else{
						$(this).css("padding-top", (($(this).css("padding-top").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					}
					break;
				case "padding-right":
					$(this).css("padding-right", (($(this).css("padding-right").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "padding-bottom":
					$(this).css("padding-bottom", (($(this).css("padding-bottom").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "padding-left":
					$(this).css("padding-left", (($(this).css("padding-left").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "margin-top":
					$(this).css("margin-top", (($(this).css("margin-top").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "margin-right":
					$(this).css("margin-right", (($(this).css("margin-right").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "margin-bottom":
					$(this).css("margin-bottom", (($(this).css("margin-bottom").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "margin-left":
					$(this).css("margin-left", (($(this).css("margin-left").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "line-height":
					$(this).css("line-height", (($(this).css("line-height").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "background-size":
					$(this).css("background-size", (($(this).css("background-size").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "font-size":
					$(this).css("font-size", (($(this).css("font-size").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "top":
					$(this).css("top", (($(this).css("top").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "right":
					$(this).css("right", (($(this).css("right").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "bottom":
					$(this).css("bottom", (($(this).css("bottom").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "left":
					$(this).css("left", (($(this).css("left").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "border-top-width":
//					alert("before_ratio:"+ratio);
					$(this).css("border-top-width", (($(this).css("border-top-width").replace(/[^-\d\.]/g, ''))*ratio)+"px");
//					$(this).css("border-top-width", "15.5px");
//					alert($(this).css("border-top-width"));
					break;
				case "border-bottom-width":
					$(this).css("border-bottom-width", (($(this).css("border-bottom-width").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
				case "border-width":
					$(this).css("border-width", (($(this).css("border-width").replace(/[^-\d\.]/g, ''))*ratio)+"px");
					break;
			}
		}
	});
}