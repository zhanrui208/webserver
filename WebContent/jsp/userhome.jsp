<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   	<title>千鸿通信</title>
   	<link type="image/x-icon" href="image/qianhong.ico" rel="shortcut icon">
   
    <link href="css/userhome.css" rel="stylesheet" type="text/css"/>
   	<link href="css/public.css" rel="stylesheet" type="text/css"/>
   
  	<script type="text/javascript" src="js/core/jquery-1.10.2.min.js" ></script>
  	<script type="text/javascript" src="js/userhome.js"  ></script>
</head>
<body>
	<div id = "main">		
		<!-- 头部分 -->
		<div id = "main_head">
			<!-- logo -->
			<div id = "main_logo" >
				<a id="logo" href="home" title="Agora" rel="home">
					<img style="padding-top: 5px;" src="image\LOGO.png" alt="加载图片失败">
				</a>
			</div>

			<!-- 退出按钮 -->
			<div id ="userlogout_div">
				<a id ="userlogout_btn"  href="logout">退出</a>
			</div>
						
			<!-- 显示名字 -->
			<div id ="username_div">
				<a id ="username_val">hello</a>
				<a id ="username_other">,晚上好</a>
			</div>
		</div>
		<!-- 头部分 -->
		
		<!-- 主体部分 -->
		<div id = main_body>
		<!-- 主题部分 -->	

			<!-- 第一部分 :左边侧边菜单栏-->
			<div id ="part1">
				<div id ="part11" class ="part_type" href="mymeeting">
					<!-- 会议室部分-->
					<a class="C_menu_a">会议室      ></a>
					<!-- 图片部分 -->
				</div>
			</div>	
			<!-- 第一部分 -->
			
			
			<!-- 第二部分-->
			<div id ="part2" class="part2_main_public">
				<div id =part21>
					<!-- 标题 -->
					<div id="part21_head_div">
						<div id ="refreshmeet_div">
							 <button id="refreshmeet">刷新</button>
						</div>
						<div id ="createmeet_div">
							<button id ="createmeet">创建会议室</button>
						</div> 
					</div>
					
					<!-- 标题 -->
					<div id ="part22" class="rowhead">
						<div id ="meetname_div" class="col col1" >
							<a class="meetname" >会议室名</a>
						</div>	
						<div id="userMaxnum_div" class="col col2">
							<a class="userMaxnum">最大用户数</a>
						</div>
						<div id="protype_div" class="col col3">
							<a class="protype">产品类型</a>
						</div>
						<div id="savetime_div" class="col col4">
							<a class="savetime">文件保存天数</a>
						</div>
						<div id="paytype_div" class="col col5">
							<a class="paytype">付费方式</a>
						</div>
						<div id="manage_div" class="col col6">
							<a class="manage">操作</a>
						</div>																	
					</div>
					<!-- 标题 -->
					<!-- 值部分 -->
					<div id ="part23" class="rowvalue">
						<div id ="meetname_div" class="col col1" >
							<a class="meetname" >苹果发布会</a>
						</div>	
						<div id="userMaxnum_div" class="col col2">
							<a class="userMaxnum">300(含50个浏览器)</a>
						</div>
						<div id="protype_div" class="col col3">
							<a class="protype">标清产品</a>
						</div>
						<div id="savetime_div" class="col col4">
							<a class="savetime">7天</a>
						</div>
						<div id="paytype_div" class="col col5">
							<a class="paytype">包年包月</a>
						</div>
						<div id="manage_div" class="col col6">
							<a class="manage">管理</a>
						</div>																	
					</div>
					<!-- 值部分 -->
				</div>
			</div>
			<!-- 第二部分-->
		</div>	
		<!-- 主体部分 -->
	</div>		
</body>
</html>