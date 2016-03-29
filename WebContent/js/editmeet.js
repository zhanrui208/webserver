/**
 * 
 */

$("document").ready(function(){
	
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
	
});




/**
 * 保存meet
 */
function savemeet() {
	var username = $("#user").val();
	var oldpassword = $("#oldpassword").val();
	var newpassword = $("#newpassword").val();
	var newpassword_again = $("#newpassword_again").val();
	

	if (username==null ||username==""){
		alert("账号不能为空");
		return false;
	}
	
	if (oldpassword==null ||oldpassword==""){
		alert("旧密码不能为空");
		return false;
	}
	
	if (newpassword==null ||newpassword==""){
		alert("新密码不能为空");
		return false;
	}
	
	if (newpassword_again!=newpassword){
		alert("2次输入的新密码不相等");
		return false;
	}

	var token=$("#token").val();
		
		
	
	$.ajax({
		url : 'rest/save',
		type : 'POST',
		datatype : 'JSON',
		data : {
			'username' : username,
			'oldpassword' : oldpassword,
			'newpassword' : newpassword,
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
		  alert("更新密码失败!");
	  }
	}).fail(function(err) {
		alert("服务器响应失败!");
	});	
};