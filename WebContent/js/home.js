/**
 * 
 */

$("document").ready(function(){
	//窗体的宽度
	var WD=window.innerWidth
	|| document.documentElement.clientWidth
	|| document.body.clientWidth;
	
	WD=WD-18;
	
	$(".mainpic").css("width",WD);
	$("#main_body").css("width",WD);
	
	
});	