/**
 * 
 */
/**
 * 重新设置密码
 */
function resetpwd() {
	var param1 = $("#param1").val();
	var paramvalue1 = $("#paramvalue1").val();
	$.ajax({
		url : webpath+'resetpwd.do',
		type : 'POST',
		datatype : 'JSON',
		data : {
			'username' : username,
			'password' : password,
			
		}
	}).done(function(data) {
		document.write(data);
	}).fail(function(err) {
		alert("服务器响应失败!");
	});
};