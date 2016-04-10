
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
	
	//鼠标移动到区域的事件
	$("#part11 li").on("mouseover",function(){
		$(this).addClass("hover_active");
	});
	//鼠标离开区域事件
	$("#part11 li").on("mouseout",function(){
		$(this).removeClass("hover_active");
 	});
	//点击事件
	$("#part11 li").on("click",function(){
		var num = $(this).index();
		showback(num);
	});

	//注销
	$("#userlogout_btn").on("click",function(){
		logout();
	});
  
	//刷新按钮
	$("#refreshmeet").on("click",function(){
		refreshmeet();
	});
	
	//创建会议室
	$("#createmeet").on("click",function(){
		createmeet();
	});
	
	//会议室编辑
	$("#editmeet").on("click",function(){
		window.location="editmeet";
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
  

  //获取所有会议室基本信息
  function getMeetbases(offset,limit){
	var token ="";  
	$.ajax({
		url : 'rest/getmeetbaselist',
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
			  var meetlistdiv= "";
			  $("#part213").html('');
			  if (datalist !=null){
				  for (var i = 0;i<datalist.length;i++){
					  var roomID = datalist[i].roomID;
					  var roomName =  datalist[i].roomName;
					  var MaxUserCount = datalist[i].maxUserCount;
					  var businessinfoID = datalist[i].businessinfoID;
					  var hopeEndTime =datalist[i].hopeEndTime;
					  var recordOnline = datalist[i].recordOnline;
					  var uploadFile = datalist[i].uploadFile;
					  meetlistdiv= meetlistdiv 
						+' <div id ="part213'+ i + '" roomID="' + roomID +  '" class="meetlist rowvalue"> ' 
						  	+ ' <div id ="meetname_div" class="col col1" > '
						  	+ '		<a class="meetname" >' + roomName + '</a> '
						  	+ ' </div>	'
							+ ' <div id="userMaxnum_div" class="col col2"> '
							+ '		<a class="userMaxnum">' + MaxUserCount + '(含50个浏览器)</a> '
							+ ' </div> '
							+ ' <div id="protype_div" class="col col3"> '
							+ '		<a class="protype">标清产品</a> '
							+ ' </div> '
							+ ' <div id="savetime_div" class="col col4"> '
							+ '		<a class="savetime">7天</a> '
							+ ' </div> '
							+ ' <div id="paytype_div" class="col col5"> '
							+ '		<a class="paytype">包年包月</a> '
							+ ' </div> '
							+ ' <div id="manage_div" class="col col6"> '
							+ ' 	<button class ="editmeet"' + '" roomID="' + roomID + '" style = "margin-top: 10px;">编辑</button> '
							+ ' </div>	'																
						+ ' </div> ';
				  	}		
				  	$("#part213").append(meetlistdiv); 
					//会议室编辑
					$(".editmeet").on("click",function(){
						var id = this.getAttribute("roomid");
						window.location="editmeet?roomid=" + id;
					});
			  }else{
				  $("#part213").append('<div class="meetlist rowvalue" ><a>无数据</a></div>');
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
  
  //编辑会议室
  function editroom(){
	  //.$("#newTest").attr("myAttr");

	  
  };
  
  //注销登陆
  function logout(){
		$.ajax({
			url : 'rest/dologout',
			type : 'POST',
			datatype : 'JSON',
			data : {
			}
		}).done(function(data) {
		  if(data['success']){
			  if (data['errorCode']==100){
				  window.location="login";
			  }else{
				  alert(data['error']);
			  }
		  }else{
			  alert("注销失败!");
		  }
		}).fail(function(err) {
			alert("服务器响应失败!");
		});		  
  };
  
  //刷新会议室
  function refreshmeet(){
	  getMeetbases(0,10);
  };
  
  //创建会议室
  function createmeet(){
	  window.location="createmeet";
  }