<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <title>千鸿通信</title>
  <script type="text/javascript" src="js/core/jquery-1.10.2.min.js" ></script>
  </script>
</head>
<body>
	<div id = "main">
		<p id="info">${error}</p>
	</div>	
	<script type="text/javascript">
		var height=window.innerHeight ;
		$("#info").css("margin-top",height/2+"px");
		$("#info").css("text-align","center");
	</script>	
</body>
</html>