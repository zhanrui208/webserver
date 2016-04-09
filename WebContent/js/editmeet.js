/**
 * 页面加载完成加载
 */

$("document").ready(function(){
	
	//初始化数据
	init();
	
	
	//绑定单选按钮点击事件
	$("input[name='optRadios1']").bind("click",function(){
		var selval = $("input[name='optRadios1']:checked").val();
		if (selval == "option1"){
			$("input[name='optRadios2']").attr('disabled',false);
			$("#bg2").removeClass("hided");
		}else{
			$("input[name='optRadios2']").attr('disabled',true);
			$("#bg2").addClass("hided");
		}
	});
	
	//输入框必须为数字
	$("#usercounts").bind("keypress",function(event){
		return isnum(event);
	});
	
	$("#time").bind("keypress",function(event){
		return isnum(event);
	});
	
	
	//失去焦点事件
	$("#usercounts").bind("blur",function(){
		var value = $("#usercounts").val();
		if (!chkusercounts(value)){
			$("#usercounts").focus();
		}
	});
	
	$("#time").bind("blur",function(){
		var value = $("#time").val();
		if (!chktime(value)){
			$("#time").focus();
		}
	});

	//保存会议室
	$("#save").bind("click",function(){
		savemeet();
	});
	
	//创建会议室
	$("#creRoom").bind("click",function(){
		createmeet();
	});
	
	//返回会议列表
	$("#reRoomList").bind("click",function(){
		roomList();
	});
		
});

//页面加载话初始化数据
function init(){
	var roomid = $("#roomid").val().trim();
	if (roomid !=null && roomid.length>0){
		getMeetBaseByRoomId(roomid);
	}else{
		clearMeetBase();
	}
}

function getMeetBaseByRoomId(roomid){
	$.ajax({
		url : 'rest/getmeetbasebyroomid',
		type : 'POST',
		datatype : 'JSON',
		data : {
			'token' :token,
			'roomid' : roomid
		}
	}).done(function(data) {
	  if(data['success']){
		  if (data['errorCode']==100){
			  loadMeetBase(data['data']);
		  }else {
			  alert(data['error']);
		  }
	  }
	  else{
		  alert("保存会议室失败!");
	  }
	}).fail(function(err) {
		alert("服务器响应失败!");
	});	
}

//清空所哟的数据
function clearMeetBase(){
	$("#meetname").val();
	$("#usercounts").val();
	$("#time").val();
	$("#type").val();
}
//加载数据
function loadMeetBase(data){
	$("#meetname").val(data.roomName);
	$("#usercounts").val(data.maxUserCount);
	$("#time").val(5);
	
	if (data.businessinfoID == "1"){
		$("#type").get(0).selectedIndex=1 ;
	}else if (data.businessinfoID == "2"){
		$("#type").get(0).selectedIndex=2 ;
	}else if (data.businessinfoID == "5"){
		$("#type").get(0).selectedIndex=3 ;
	}
	
	if (data.isSupportLive == "1"){
		$("input[name='optRadios1']").attr('disabled',true);
	}else{
		$("input[name='optRadios1']").attr('disabled',false);
	}
	if (data.isSupportMediaUpload == "1"){
		$("input[name='optRadios2']").attr('disabled',true);
	}else{
		$("input[name='optRadios2']").attr('disabled',false);
	}
}






//人数限制
function chkusercounts(usercounts){
	if (usercounts==0 || usercounts>9999){
		alert("最大用户数必须在1至9999之间");
		return false;
	}
	return true;
}
//时长限制
function chktime(time){
	if (time==0 || time>24){
		alert("购买时长必须在1至24之间");
		return false;
	}
	return true;
}
/**
 * 保存meet
 */
function savemeet() {
	var roomid = $("#roomid").val().trim();
	var meetname = $("#meetname").val().trim();
	var usercounts = $("#usercounts").val().trim();
	var time = $("#time").val().trim();
	var type = $("#type").val().trim();
	
	var zhibo = "0";
	var luzhi = "0";
	var selval = $("input[name='optRadios1']:checked").val();
	if (selval == "option1"){
		zhibo="1";
		selval = $("input[name='optRadios2']:checked").val();
		if (selval == "option1"){
			luzhi = "1";
		}
	}
	
	if (meetname==null ||meetname==""){
		alert("实例不能为空");
		return false;
	}
	
	if (!isNum(usercounts)){
		alert("最大用户数必须为数字");
		return false;
	}
	
	if (!chkusercounts(usercounts)){
		return false;
	};
	

	if (!isNum(time)){
		alert("购买时长必须为数字");
		return false;
	}
	
	if (!chktime(time)){
		return false;
	};
	
	if (type==null ||type==""){
		alert("类型不能为空");
		return false;
	}
	
	var token=$("#token").val();
	
	//是否是新room
	var newroom = "0";
	if (roomid != null){
		newroom = "1";
	}
		
	$.ajax({
		url : 'rest/savemeet',
		type : 'POST',
		datatype : 'JSON',
		data : {
			'token' :token,
			'meetname' : meetname,
			'usercounts' : usercounts,
			'roomid' :roomid,
			'newroom' :newroom,
			'time' : time,
			'type' : type,
			'zhibo': zhibo,
			'luzhi': luzhi
		}
	}).done(function(data) {
	  if(data['success']){
		  if (data['errorCode']==100){
			  alert("保存会议室成功!");
		  }else {
			  alert(data['error']);
		  }
	  }
	  else{
		  alert("保存会议室失败!");
	  }
	}).fail(function(err) {
		alert("服务器响应失败!");
	});	
};

//计算费用
function compute(){
	var usercounts = $("#usercounts").val();
	var time = $("#time").val();
	var type = $("#type").val();
	var price =0;
	if (type == 1){
		price = 29 ;
	}else if (type = 2){
		price =59 ;
	}else if (type = 3){
		price = 99;
	}	
	var amount = price*time*usercounts;
};


//创建会议室
function createmeet(){
	  window.location="createmeet";
}
//返回会议列表
function roomList(){
	window.location="meethome";
}

//判断是否是数字
function isnum(event){
    var kc=0;
    if(window.event){
        kc=event.keyCode;
    }else if(event.which){
        kc=event.which;
    }
    //48-57 96-105
    if((kc >= 48 && kc <= 57)){
    	return true;
    }else{
        alert("必须输入数字");
        return false;       
    }  
}
