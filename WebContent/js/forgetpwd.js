/**
 * 
 */
/**
 * 重新设置密码
 */
function forgetpwd() {

	var username = $("#email").val();
	var email= $("#email").val();
	
	if (!IsEmail(email)){
		alert("邮箱格式不正确");
		return false;
	}

	var token=$("#token").val();
		
	$.ajax({
		url : 'rest/doforgetpwd',
		type : 'POST',
		datatype : 'JSON',
		data : {
			'username' : username,
			'email' : email,
			'token' : token
		}
	}).done(function(data) {
	  if(data['success']){
		  if (data['errorCode']==100){
			  alert("更新密码的链接已发送到你的邮箱!");
			  window.location="login";
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