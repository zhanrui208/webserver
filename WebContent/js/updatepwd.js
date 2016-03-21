/**
 * 
 */
/**
 * 重新设置密码
 */
function updatepwd() {
	var username = $("#username").val();
	var newpassword = $("#newpassword").val();
	var newpassword_again = $("#newpassword_again").val();
	var token=$("#token").val();
	
	if (!isPwd(newpassword)){
		return;
	}
	
	if (newpassword_again!=newpassword){
		alert("2次输入的新密码不相等");
		return false;
	}

	$.ajax({
		url : 'rest/doupdatepwd',
		type : 'POST',
		datatype : 'JSON',
		data : {
			'username' : username,
			'password' : newpassword,
			'token' : token
		}
	}).done(function(data) {
	  if(data['success']){
		  if (data['errorCode']==100){
			  alert("更新密码成功!");
			  window.location="login";
		  }else {
			  alert(data['error']);
		  }
	  }
	  else{
		  alert("注册失败!");
	  }
	}).fail(function(err) {
		alert("服务器响应失败!");
	});	
};