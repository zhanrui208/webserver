<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Home - 千鸿通信</title>
   <link href="css/home.css" rel="stylesheet" type="text/css"/>
   <link href="css/head.css" rel="stylesheet" type="text/css"/>
   <link href="css/foot.css" rel="stylesheet" type="text/css"/>
   <script type="text/javascript" src="/webtest/js/home.js"  ></script>
   <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.1.min.js" ></script>
</head>
<body>
	<div id = "main">
		<!-- 头部分 -->
		<%@ include file="head.jsp"%> 
		<!-- 头部分 -->
		
		<!-- 主体部分 -->
		<div id = main_body>
			<!-- 第一部分 -->
			<div id ="part10">
				<div id ="part11" >
					<!-- 图片部分 -->
					<div id ="part11_picture" class="picture">
						<div class="ms-picture" style="height: 100%; opacity: 1;">
							<img src="https://d16b68sffmpvhr.cloudfront.net/wp-content/uploads/2015/04/abstract-sound-waves.jpg" alt="" title="" >
						</div>
						<div id="ms-context">
							<h1 style="text-align: center;"><span style="color: #ffffff;">沟通无界限</span></h1>
						</div>
					</div>
					<!-- 图片部分 -->
					
					<!-- 呢绒介绍 -->
					<div id ="part12" class="vc_col-sm-12">
						<div id ="part121" class="vc_row wpb_row ">
							<div class="part121-col-3">
								<div class="part12-col-detail ">
									<img src="http://cn.agora.io/wp-content/uploads/2015/07/qoenew1.png">
									<h2>用户体验较差</h2>
									<p>当前的软件无法在全球范围内提供优良的端到端用户体验和稳定的视频质量。 </p>
									<a href="#"> <i class="fa fa-angle-right"></i></a>
								</div>
							</div>
							<div class="part121-col-3">
								<div class="part12-col-detail">
									<img src="http://cn.agora.io/wp-content/uploads/2015/07/qoenew1.png">
									<h2>费用高昂</h2>
									<p>就当前市场而言，既能够轻松集成到既有平台和应用，又能保证稳定的用户体验质量的技术解决方案价格极高，令人望而却步。 </p>
								</div>
							</div>
							<div class="part121-col-3">
								<div class="part12-col-detail">
									<img src="http://cn.agora.io/wp-content/uploads/2015/07/qoenew1.png">
									<h2>开发难度大</h2>
									<p>实时通讯软件的无缝集成技术极其复杂，而且很耗时间。 </p>
								</div>
							</div>
						</div>
					</div>	
				</div>
			</div>
			<!-- 第一部分 -->	
		</div>	
		<!-- 主题部分 -->	
			
		<!-- 最下面基本介绍部分 -->
		<%@ include file="foot.jsp"%> 
		<!-- 最下面基本介绍部分 -->
	</div>		
</body>
</html>