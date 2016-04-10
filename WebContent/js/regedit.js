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

function doregedit() {  
	
	var username = $("#email").val();
	var password = $("#password").val();
	var password_again = $("#password_again").val();
	var email = $("#email").val();
	var phone = $("#phone").val();
	
	if (username==null ||username==""){
		alert("账号不能为空");
		return false;
	}
	
	if (!isPwd(password)){
		return;
	}

	if (password_again!=password){
		alert("2次输入的密码不相等");
		return false;
	}
	
	
	if (!IsEmail(email)){
		alert("邮箱格式不正确");
		return false;
	}
	
	if (!isMobil(phone)){
		alert("手机号格式错误");
		return false;
	}
	var token=$("#token").val();
		
		
	
	$.ajax({
		url : 'rest/doregedit',
		type : 'POST',
		datatype : 'JSON',
		data : {
			'UserName' : username,
			'Password' : password,
			'EMail' : email,
			'Mobile' : phone,
			'token' : token
		}
	}).done(function(data) {
	  if(data['success']){
		  if (data['errorCode']==100){
			  alert("注册成功!");
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
}  



/**
 * 验证输入是否符合要求
 * @returns {Boolean}
 */
function getToken(){
	var input=$("input");  
    $.each(input,function(index,a){  
        alert(a.value);  
        return a.value;
    });  
}