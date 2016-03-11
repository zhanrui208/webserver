/**
 * 
 */
/**
 * 重新设置密码
 */
function forgetpwd() {
	var username = $("#user").val();
	var email = $("#email").val();

	if (username==null ||username==""){
		alert("账号不能为空");
		return false;
	}
	if (email==null ||email==""){
		alert("邮箱不能为空");
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
			  alert("新密码已发送到你的邮箱!");
			  window.location="login";
		  }else {
			  alert(data['error']);
		  }
	  }
	  else{
		  alert("密码重置失败!");
	  }
	}).fail(function(err) {
		alert("密码重置失败!");
	});	
};