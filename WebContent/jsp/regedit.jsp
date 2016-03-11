<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>千鸿通信</title>
  <link type="image/x-icon" href="image/qianhong.ico" rel="shortcut icon">
   <link href="css/regedit.css" rel="stylesheet" type="text/css"/>
   <link href="css/head.css" rel="stylesheet" type="text/css"/>
   <link href="css/foot.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="js/core/jquery-1.10.2.min.js" ></script>
    <script type="text/javascript" src="js/regedit.js"  ></script>
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
				<div class="regeditTitle"><div class="regedit-inner" id="regedit-inner">注册帐号</div></div>
				<form id ="saveDataForm" >
					<input type ="hidden" id="token" value = ${token} >
				
					<div class ="user regedit">
						<label class="infolable" id="accout_lable">账号</label>
						<div class="ipt_box">
							<input type="text" class="new_info" id="user" name="UserName" tabindex="1" style="z-index:0" maxlength="24" >
						</div>
						<div class="errinfo">
							<a>账号不能为空</a>
						</div>	
					</div>
		
					<div class ="password regedit">
						<label class="infolable" id="password_lable">密码</label>
						<div class="ipt_box">
							<input type="password" class="new_info" id="password" name="Password" tabindex="2" style="z-index:0" maxlength="16" >
						</div>
						<div class="errinfo">
							<a>密码不能为空</a>
						</div>	
					</div>
					
					<div class ="password_again regedit">
						<label class="infolable" id="password_again_lable">确认密码</label>
						<div class="ipt_box">
							<input type="password" class="new_info" id="password_again" name="password_again" tabindex="3" style="z-index:0" maxlength="16" >
						</div>
						<div class="errinfo">
							<a>密码不能为空</a>
						</div>	
					</div>	
								
					<div class ="email regedit">
						<label class="infolable" id="email_lable">邮箱</label>
						<div class="ipt_box">
							<input type="text" class="new_info" id="email" name="EMail" tabindex="4" style="z-index:0" maxlength="40" >
						</div>
						<div class="errinfo">
							<a>邮箱不能为空</a>
						</div>	
					</div>		
	
	
					<div class ="phone regedit">
						<label class="infolable" id="phone_lable">电话</label>
						<div class="ipt_box">
							<input id="phone" type="text" class="new_info" name="Mobile" tabindex="5"  maxlength="11">
						</div>
						<div class="errinfo">
							<a>电话不能为空</a>
						</div>	
					</div>						


					<div class ="submit_div regedit"  onclick="doregedit()">
						<div class="submit_box">
							<input class="submit_btn" type="button"  value="提交注册"  tabindex="6" id="submit">
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