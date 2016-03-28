
$("document").ready(function(){
	//窗体的宽度
	var WD=window.innerWidth
	|| document.documentElement.clientWidth
	|| document.body.clientWidth;
	//窗体的高度
	var WH=window.innerHeight
	|| document.documentElement.clientHeight
	|| document.body.clientHeight;
	
	
	$("body").width(WD);
	$("#main").width(WD);
	
	var headH= $("#main_head").height()+1;//1底线高度
	
	$("#main").height(WH-headH);
	
	var MINH = 600 - headH;
	
	$("#part1").css("minHeight",MINH+"px");
	$("#part2").css("minHeight",MINH+"px");
	
	
});



$("#part11 li").live("mouseover",function(){
	  $(this).addClass("hover_active");
  });
  $("#part11 li").live("mouseout",function(){
	  $(this).removeClass("hover_active");
  });
  $("#part11 li").live("click",function(){
	  var num = $(this).index();
	  showback(num);
  });
  
  function showback(num){
      $("#part11  li").each(function(index) {
    	  var i = index+1;
    	  if (index==num){
    		  $(this).addClass("CR_DBCC4E");
    		  showmeet(i);
    	  }else{
    		  $(this).removeClass("CR_DBCC4E");
    		  hidemeet(i);
    	  }
      });
  };
  
  function showmeet(num){
	  $('#part2'+num).removeClass("part_hide");
	  $('#part2'+num).addClass("part_show");
  };
  
  function hidemeet(num){
	  $('#part2'+num).removeClass("part_show");
	  $('#part2'+num).addClass("part_hide");
  };
  
  //会议室编辑
  $("#editmeet").live("click",function(){
	  window.location="editmeet";
  });
  
  //会议室创建
  $("#createmeet").live("click",function(){
	  window.location="createmeet";
  });
  