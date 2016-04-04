<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   	<title>千鸿通信</title>
   	<link type="image/x-icon" href="image/qianhong.ico" rel="shortcut icon">
   
    <link href="css/meethome.css" rel="stylesheet" type="text/css"/>
   	<link href="css/public.css" rel="stylesheet" type="text/css"/>
   
  	<script type="text/javascript" src="js/core/jquery-1.10.2.min.js" ></script>
  	<script type="text/javascript" src="js/meethome.js"  ></script>
  	<% 
  		String username = session.getAttribute("username").toString();
  	
  	%>
  	
</head>
<body>
	<div id = "main">
		<!-- 头部分 -->
		<div id = "main_head">
			<!-- logo -->
			<div id = "main_logo" >
				<a id="logo" href="home" title="Agora" rel="home">
					<img src="image\LOGO.png" alt="加载图片失败">
				</a>
			</div>

			<!-- 退出按钮 -->
			<div id ="userlogout_div">
				<a id ="userlogout_btn">退出</a>
			</div>
						
			<!-- 显示名字 -->
			<div id ="username_div">
				<a id ="username_val"><%=username%></a>
				<a id ="username_other">,晚上好</a>
			</div>
		</div>
		
		<!-- 主体部分 -->
		<div id = main_body>
		<!-- 主题部分 -->	

			<!-- 第一部分 :左边侧边菜单栏-->
			<div id ="part1">
				<ul  id ="part11">
					<li href="" class="hover_active">
						<div  href="11" class ="padleft20" ><a>会议室     ></a></div>
					</li>
					<li href="" >
						<div  href="11" class ="padleft20"><a>会议室 2    ></a></div>
					</li>
				</ul>
			</div>	
			<!-- 第一部分 -->
				
			<!-- 第二部分-->
			<div id ="part2" class="part2_main_public">
				<div id =part21 class="part_show">
					<!-- 标题 -->
					<div id="part211_head_div">
						<div id ="refreshmeet_div">
							 <button id="refreshmeet">刷新</button>
						</div>
						<div id ="createmeet_div">
							<button id ="createmeet">创建会议室</button>
						</div> 
					</div>
					
					<!-- 标题 -->
					<div id ="part212" class="rowhead">
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
							<a class="manage">管理</a>
						</div>																	
					</div>
					<!-- 标题 -->
					<!-- 值部分 -->
					<div id ="part213" class="rowvalue">
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
							<button id ="editmeet">编辑</button>
						</div>																	
					</div>
					<!-- 值部分 -->
				</div>
				<div id ="part22"  class="part_hide">
					<p>第二个页签</p>
				</div>
			</div>
			<!-- 第二部分-->
		</div>	
		<!-- 主体部分 -->
	</div>
</body>
</html>