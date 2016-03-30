/**
 * 页面加载完成加载
 */

$("document").ready(function(){
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
	
	
	
	

});

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
	var meetname = $("#meetname").val();
	var usercounts = $("#usercounts").val();
	var time = $("#time").val();
	var type = $("#type").val();
	
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
		
		
	$.ajax({
		url : 'rest/savemeet',
		type : 'POST',
		datatype : 'JSON',
		data : {
			'token' :token,
			'meetname' : meetname,
			'usercounts' : usercounts,
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
