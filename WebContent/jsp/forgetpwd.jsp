<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>千鸿通信</title>
  <link type="image/x-icon" href="image/qianhong.ico" rel="shortcut icon">
  <link href="css/public.css" rel="stylesheet" type="text/css"/>
  <link href="css/forgetpwd.css" rel="stylesheet" type="text/css"/>
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
		<div id ="speheight"></div>
		<!-- 主体部分 -->
		<div id = main_body>
			<div id = btnregedit>
				<div id="haomaTitle">
					<div class="haoma-inner" id="haoma-inner">通过邮箱验证码找回</div>
				</div>
				<div id="info_div"  >
					<input type ="hidden" id="token" value = ${token} >
					<div id ="descript_div">点击发送验证码后，验证码会发送到你的邮箱账号，验证通过后进行密码重置</div>
					
					<div id = "forgetpwd_div" class ="email forgetpwd">
						<div id ="email_div">
							<label class="infolable" id="email_lable">邮箱</label>
							<input  id="ipt_email" type="text" class="new_info" name="email"  tabindex="1" style="z-index:0" maxlength="40" >
						</div>
						<div id ="code_div">
							<label class="infolable" id="email_lable">验证码</label>
							<input id="ipt_code" type="text" class="new_info" id="code"  name="code" tabindex="2" style="z-index:0" maxlength="40" >
							<input id ="sendcode_btn" type ="button" class ="sendocde"  tabindex="3" value="发送到验证码" onclick="sendcode()">
						</div>
					</div>		

					<div  onclick="forgetpwd()">
						<div class="submit_box">
							<input class="submit_btn" type="button"  value="下一步"  tabindex="4" id="submit">
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