<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>示列</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">

<!-- 可选的Bootstrap主题文件（一般不使用） -->
<!-- <script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap-theme.min.css"></script>  -->
    <link href="css/about.css" rel="stylesheet" type="text/css"/>
   	<link href="css/head.css" rel="stylesheet" type="text/css"/>
   	<link href="css/foot.css" rel="stylesheet" type="text/css"/>
   	<link href="css/public.css" rel="stylesheet" type="text/css"/>
	<link href="css/editmeet.css" rel="stylesheet" type="text/css"/>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/public.js"></script>
	<script type="text/javascript" src="js/editmeet.js"></script>
	
</head>
<body>
<div id = "main">
	<!-- 头部分 -->
	<div id = "main_head">
		<!-- logo -->
		<div class ="heg80" id = "main_logo " >
			<a  id="logo" href="home" title="Agora" rel="home">
				<img src="image/LOGO.png" alt="加载图片失败">
			</a>
		</div>
		<!-- logo -->
	</div>	
	<!-- 头部分 -->

	<div id ="main_div">
		<input type="hidden" name="roomid" id = "roomid" value = "${roomid}" >
		<div class="container"> 
			<button type="button" class="btn btn-default" id ="creRoom">创建  </button>
			<button type="button" class="btn btn-default" id ="reRoomList">返回列表   </button>
			
			 <HR style="FILTER: progid:DXImageTransform.Microsoft.Glow(color=#987cb9,strength=10)" width="100%" color=#987cb9 SIZE=1>

			<div class="maincontext">
	
				<form class="form-horizontal col-sm-9" id ="form1" role="form">
					<div class="form-group1  marall10" id ="bg1">
						<div class="form-group">
						</div>
					   <div class="form-group">
					      <label class="col-sm-3 control-label">实例名称:</label>
					      <div class="col-sm-6">
					         <input class="form-control" id="meetname"  type="text" 
					            placeholder="请输入会议室名称">
					      </div>
					      <label class="col-sm-2 control-label"></label>
					   </div>
	
					   <div class="form-group">
					      <label class="col-sm-3 control-label">最大用户数:</label>
					      <div class="col-sm-4">
					         <input class="form-control" id="usercounts" type="text" 
					            placeholder="请输入最大用户数">
					      </div>
					      <label class="col-sm-1 control-label ">人</label>
					   </div>			   
					   
					   <div class="form-group">
					      <label class="col-sm-3 control-label">购买时长:</label>
					      <div class="col-sm-3">
					         <input class="form-control" id="time" type="text" 
					            placeholder="请输入购买时长">
					      </div>
					      <label class="col-sm-1 control-label">月</label>
					   </div>
				   
					   <div class="form-group">
					      <label class="col-sm-3 control-label"  >产品类型:</label>
					      <div class="col-sm-4">
								<select class="form-control" id="type" >
							         <option value=1 >标清(最大分辨率640*480)</option>
							         <option value=2 >高清(最大分辨率800*600)</option>
							         <option value=3 >高清(最大分辨率1024*800)</option>
								</select>
					      </div>
					      <label class="col-sm-2 control-label"></label>
					   </div>
					   <div class="form-group">
					   </div>
					   
					   
					</div>						

				   <!-- 2 -->
				  <div class="form-group marall10 padall10 bg-c" id ="bg1" style="height: 80px;">
				  	<p>会议直播（无需安装开户端，支持浏览器，手机观看） 该服务为按流量收费，用多少算多少，1TB/元</p>
					<div class="radio1">
					   <label class="wid80 marleft40">
					      <input type="radio" name="optRadios1" id="opt1" 
					         value="option1" checked> 是
					   </label>
					   <label class="wid80">
					      <input type="radio" name="optRadios1" id="opt2" 
					         value="option2">否
					   </label>
					</div>  
				  </div>


				   <!-- 4 -->
				  <div class="form-group heg80  marall10 padall10 bg-c" id ="bg2" style="height: 80px;">
				  	<p>实时在线录制（后台实时在线录制，实时点播观看，再也不用担心繁琐的转码，上传操作了）</p>
					<div class="radio2">
					   <label class="wid80 marleft40 ">
					      <input type="radio" name="optRadios2" id="opt3" 
					         value="option1" checked> 是
					   </label>
					   <label class="wid80">
					      <input type="radio" name="optRadios2" id="opt4" 
					         value="option2" checked>否
					   </label>
					</div>  
				  </div>
			   
				</form>	   
				<div class="col-sm-3 ">
		   			<div class="row bg-c" id ="part21">
	   					<div class="heg40">
	   						<label class="marall10">当前配置</label>
	   						<hr>
	   					</div>
   						<div class ="col-sm-12 martop30" id="baseinfo">
   							<p class="heg50">
   								<a class=" col-sm-6 text-left">实例名称:</a>
   								<a class="col-sm-6 text-left" id ="p-meetname"></a>
   							</p>

    							<p class="heg50">
   								<a class=" col-sm-6 text-left">最大用户数:</a>
   								<a class="col-sm-6 text-left" id ="p-usercounts"></a>
   							</p>   

   							<p class="heg50">
   								<a class=" col-sm-6 text-left">购买时长:</a>
   								<a class="col-sm-6 text-left" id ="p-time"></a><a>月</a>
   							</p>

    							<p class="heg50">
   								<a class=" col-sm-6 text-left">产品类型:</a>
   								<a class="col-sm-6 text-left"></a>
   							</p>   
     							<p class="heg50">
   								<a class=" col-sm-6 text-left">会议直播:</a>
   								<a class="col-sm-6 text-left" id ="p-zhibo"></a>
   							</p>

    							<p class="heg50">
   								<a class=" col-sm-6 text-left">实时在线录制:</a>
   								<a class="col-sm-6 text-left" id ="p-luzhi"></a>
   							</p>  
   							
   							<p class="heg50">
   								<a class=" col-sm-6 text-left">已支付费用:</a>
   								<a class="col-sm-6 text-left" id ="p-luzhi"></a>
   							</p>
   							  
   							<p class="heg50">
   								<a class=" col-sm-6 text-left">总共费用:</a>
   								<a class="col-sm-6 text-left" id ="p-luzhi"></a>
   							</p>  
   							
   							<p class="heg50">
   								<a class=" col-sm-6 text-left">需支付费用:</a>
   								<a class="col-sm-6 text-left" id ="p-luzhi"></a>
   							</p> 
   							   							   							 							
   						</div>
   						
   						<div class ="col-sm-12 heg60 martop20">
   							<div class ="col-sm-6">
   								<button type="button" class="btn btn-default wid80" id ="save"> 保存   </button>
   							</div>
   							<div class ="col-sm-6">
   								<button type="button" class="btn btn-default wid80" id ="pay"> 确认支付   </button>
							</div>
						</div>
					</div>		
				</div>
			</div>
		</div>
	</div>	
</div>	
</body>
</html>