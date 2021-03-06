<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>千鸿通信</title>
  <link type="image/x-icon" href="image/qianhong.ico" rel="shortcut icon">
  <link href="css/public.css" rel="stylesheet" type="text/css"/>
  <link href="css/updatepwd.css" rel="stylesheet" type="text/css"/>
  <link href="css/head.css" rel="stylesheet" type="text/css"/>
  <link href="css/foot.css" rel="stylesheet" type="text/css"/>
  <script type="text/javascript" src="js/core/jquery-1.10.2.min.js" ></script>
  <script type="text/javascript" src="js/updatepwd.js"  ></script>
  <script type="text/javascript" src="js/public.js"  ></script>
</head>
<body>
	<div id = "main">
		<!-- 头部分 -->
		<%@ include file="head.jsp"%> 
		<!-- 头部分 -->
		
		<div id ="speheight"></div>
		
		<!-- 主体部分 -->
		<div id = main_body>
			<div id = btnregedit>
				<div id="haomaTitle">
					<div class="haoma-inner" id="haoma-inner">重置密码</div>
				</div>
				<div id="info_div"  >
					<input type ="hidden" id="token" value = ${token} >
					<input type ="hidden" id="username" value = ${username} >
					<div id ="newpassword_div" class="info_div updatepwd ">
						<label class="infolable" id="password_again_lable">账号</label>
						<div  class="ipt_box">
							<a class="new_info">${username}</a>
						</div>
					</div>
					
					<div id ="newpassword_div" class="info_div updatepwd ">
						<label class="infolable" id="password_again_lable">新密码</label>
						<div class="ipt_box">
							<input type="newpassword" class="new_info" id="newpassword" name="newpassword" tabindex="3" style="z-index:0" maxlength="16" >
						</div>
						<div class="errinfo">
							<a></a>
						</div>	
					</div>	
								
					<div id ="newpassword_again_div" class="info_div updatepwd ">
						<label class="infolable" id="email_lable">确认新密码</label>
						<div class="ipt_box">
							<input type="text" class="new_info" id="newpassword_again" name="newpassword_again" tabindex="4" style="z-index:0" maxlength="40" >
						</div>
						<div class="errinfo">
							<a></a>
						</div>	
					</div>		


					<div class ="submit_div resetpwd"  onclick="updatepwd()">
						<div class="submit_box">
							<input class="submit_btn" type="button"  value="确认修改"  tabindex="5" id="submit">
						</div>
					</div>						
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