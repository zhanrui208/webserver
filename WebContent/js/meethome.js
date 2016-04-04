
$("document").ready(function(){
	//窗体的宽度
	var WD=window.innerWidth
	|| document.documentElement.clientWidth
	|| document.body.clientWidth;
	WD=WD-18;
	
		
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
	
	
	  $("#part11 li").on("mouseover",function(){
		  $(this).addClass("hover_active");
	  });
	  $("#part11 li").on("mouseout",function(){
		  $(this).removeClass("hover_active");
	  });
	  $("#part11 li").on("click",function(){
		  var num = $(this).index();
		  showback(num);
	  });
	
	getMeetbases(0,10);//获取会议室列表
	
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
  $("#editmeet").on("click",function(){
	  window.location="editmeet";
  });
  
  //会议室创建
  $("#createmeet").on("click",function(){
	  window.location="createmeet";
  });
  
  //获取所有会议室基本信息
  function getMeetbases(offset,limit){
	var token ="";  
	$.ajax({
		url : 'rest/getmeetbase',
		type : 'POST',
		datatype : 'JSON',
		data : {
			'offset' : offset ,
			'limit' : limit ,
			'token' : token
		}
	}).done(function(data) {
	  if(data['success']){
		  if (data['errorCode']==100){
			  var datalist = data["data"];
			  for (var i = 0;i<datalist.length;i++){
				  var roomID = datalist[i].roomID;
				  var roomName =  datalist[i].roomName;
				  var MaxUserCount = datalist[i].maxUserCount;
				  var businessinfoID = datalist[i].businessinfoID;
				  var hopeEndTime =datalist[i].hopeEndTime;
				  var recordOnline = datalist[i].recordOnline;
				  var uploadFile = datalist[i].uploadFile;
				  
			  }
			  
		  }else{
			  alert(data['error']);
		  }
	  }else{
		  alert("获取会议室信息失败!");
	  }
	}).fail(function(err) {
		alert("服务器响应失败!");
	});	
  };