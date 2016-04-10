$("document").ready(function(){
	//窗体的宽度
	var WD=window.innerWidth
	|| document.documentElement.clientWidth
	|| document.body.clientWidth;
	
	WD=WD-23;
	
	$(".mainpic").css("width",WD);
	$("#main_body").css("width",WD);
	
	$("body").width(WD);
	$("#main").width(WD);
});	
/**
 * 
 */
function sendcode(){
	var username= $("#ipt_email").val();
	var token= $("#token").val();
	$.ajax({
		url : 'rest/sendcode',
		type : 'POST',
		datatype : 'JSON',
		data : {
			'username' : username,
			'token' : token
		}
	}).done(function(data) {
	  if(data['success']){
		  if (data['errorCode']==100){
			  alert("验证码已发送到你的邮箱!");
		  }else {
			  alert(data['error']);
		  }
	  }
	  else{
		  alert("发送验证码失败!");
	  }
	}).fail(function(err) {
		alert("发送验证码失败!");
	});	
	
};




/**
 * 重新设置密码
 */
function forgetpwd() {

	var email= $("#ipt_email").val();
	var code= $("#ipt_code").val();
	var token= $("#token").val();
	
	if (!IsEmail(email)){
		alert("邮箱格式不正确");
		return false;
	}

	if (code ==null ||code.length == 0) {
		alert("验证码不能为空式");
		return false;
	}

	$.ajax({
		url : 'rest/doforgetpwd',
		type : 'POST',
		datatype : 'JSON',
		data : {
			'username' : email,
			'token' : token,
			'code'  : code
		}
	}).done(function(data) {
	  if(data['success']){
		  if (data['errorCode']==100){
			  location.href = data.data;
		  }else {
			  alert(data['error']);
		  }
	  }
	  else{
		  alert("更新密码失败!");
	  }
	}).fail(function(err) {
		alert("更新密码失败!");
	});	
};