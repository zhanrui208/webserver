/**
 * 登陆
 */
function login(){
	var username = $("#username").val();
	var password = $("#password").val();
	var token = $("#password").val();
	$.ajax({
		url : 'rest/dologin',
		type : 'POST',
		datatype : 'JSON',
		data : {
			'username' : username,
			'password' : password,
			'token' : token
		}
	}).done(function(data) {
	  if(data['success']){
		  if (data['errorCode']==100){
			  window.location="meethome";
		  }else{
			  alert(data['error']);
		  }
	  }
	  else{
		  alert("登录失败!");
	  }
	}).fail(function(err) {
		alert("服务器响应失败!");
	});	
}
