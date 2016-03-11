<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>千鸿通信</title>
  <link type="image/x-icon" href="image/qianhong.ico" rel="shortcut icon">
  <link href="css/resetpwd.css" rel="stylesheet" type="text/css"/>
  <link href="css/head.css" rel="stylesheet" type="text/css"/>
  <link href="css/foot.css" rel="stylesheet" type="text/css"/>
  <script type="text/javascript" src="js/core/jquery-1.10.2.min.js" ></script>
  <script type="text/javascript" src="js/forgetpwd.js"  ></script>
  <script type="text/javascript" src="js/public.js"  ></script>
</head>
<body>
	<div id = "main">
		<!-- 头部分 -->
		<%@ include file="head.jsp"%> 
		<!-- 头部分 -->
		
		<!-- 主体部分 -->
		<div id = main_body>
			<div id = btnregedit>
				<div id="haomaTitle">
					<div class="haoma-inner" id="haoma-inner">邮箱重置密码</div>
				</div>
				<form id="info_form"  >
					<input type ="hidden" id="token" value = ${token} >
					<div id ="user_div" class="info_div">
						<label class="infolable forgetpwd" id="accout_lable">账号</label>
						<div class="ipt_box">
							<input type="text" class="new_info" id="user"  tabindex="1" style="z-index:0" maxlength="24" >
						</div>
						<div class="errinfo">
							<a>账号不能为空</a>
						</div>	
					</div>
		
					<div class ="email forgetpwd">
						<label class="infolable" id="email_lable">邮箱</label>
						<div class="ipt_box">
							<input type="text" class="new_info" id="email"  tabindex="2" style="z-index:0" maxlength="40" >
						</div>
						<div class="errinfo">
							<a>邮箱不能为空</a>
						</div>	
					</div>		


					<div class ="submit_div resetpwd"  onclick="forgetpwd()">
						<div class="submit_box">
							<input class="submit_btn" type="button"  value="确认重置密码"  tabindex="3" id="submit">
						</div>
					</div>						
				</form>	
			</div>
		</div>								
		<!-- 主题部分 -->	
		
		<!-- 最下面基本介绍部分 -->
		<%@ include file="foot.jsp"%> 
		<!-- 最下面基本介绍部分 -->
	</div>		
</body>
</html>