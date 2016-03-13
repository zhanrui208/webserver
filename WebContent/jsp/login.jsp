<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>千鸿通信</title>
  <link type="image/x-icon" href="image/qianhong.ico" rel="shortcut icon">
   <link href="css/login.css" rel="stylesheet" type="text/css"/>
   <link href="css/head.css" rel="stylesheet" type="text/css"/>
   <link href="css/foot.css" rel="stylesheet" type="text/css"/>
  <script type="text/javascript" src="js/core/jquery-1.10.2.min.js" ></script>
   <script type="text/javascript" src="js/home.js" ></script>
   <script type="text/javascript" src="js/login.js" ></script>

</head>
<body>
	<div id = "main">
		<!-- 头部分 -->
		<%@ include file="head.jsp"%> 
		<!-- 头部分 -->
		
		<!-- 主体部分 -->
		<div id = main_body>
			<div id = btnlogin>
				<div id ="login_image_div">
					<a id ="login_image_a">
						<img src="image/LOGO.png" alt="加载图片失败">
					</a>
					
				</div>
				<div class = "centerClass">
					<a class="desName">账号  :</a> <input id = "username"  type ="text" class="input-control" >
				</div>
				<div class = "centerClass">
						<a class="desName">密码  :</a>
						<input id = "password" type ="password" class="input-control">
						<a class="forgetpass" href="forgetpwd" style="color: blue;font-size: 10px"> 忘记密码？</a>
				</div>
				<div id="login_msg_div">
					<a style="font-size: 10px;"></a>
				</div>
				
				<div id= "btnOKdiv" style="height: 50px;">
					<button id ="btnOK" value="" onclick="login()">登陆</button>	
				</div>
			</div>
		</div>	
		<!-- 主题部分 -->	
			
		<!-- 最下面基本介绍部分 -->
		<%@ include file="foot.jsp"%> 
		<!-- 最下面基本介绍部分 -->
	</div>		
</body>
</html>