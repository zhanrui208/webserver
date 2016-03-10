/**
 * 验证输入是否符合要求
 * @returns {Boolean}
 */
function toVaild(){
	var username = $("#user").val();
	var password = $("#password").val();
	var password_again = $("#password_again").val();
	var email = $("#email").val();
	var phone = $("#phone").val();
	
	if (username==null ||username==""){
		alert("账号不能为空");
		return false;
	}
	
	if (password==null ||password==""){
		alert("密码不能为空");
		return false;
	}
	
	if (password_again!=password){
		alert("2次输入的密码不相等");
		return false;
	}
	
	if (email==null ||email==""){
		alert("邮箱不能为空");
		return false;
	}
	
	if (!IsEmail(email)){
		alert("邮箱格式不正确");
		return false;
	}
	
	
	if (phone==null ||phone==""){
		alert("手机号不能为空");
		return false;
	}
	
	return true;
}