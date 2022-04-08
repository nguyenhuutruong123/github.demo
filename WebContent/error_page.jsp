<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage = "true"%>
<%@ page import = "java.io.*" %>

<HTML>
    <HEAD>
        <TITLE>Session timeed out</TITLE>
        <style>
body, html {
  height: 100%;
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
}

* {
  box-sizing: border-box;
}

.bg-image {
  /* The image used */
  background-image: url("Center/salesup.jpg");
  
  /* Add the blur effect */
  filter: blur(5px);
  -webkit-filter: blur(5px);
  
  /* Full height */
  height: 100%; 
  
  /* Center and scale the image nicely */
  background-position: center;
  /* background-repeat: no-repeat;
  background-size: cover; */
}

/* Position text in the middle of the page/image */
.bg-text {
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0, 0.4); /* Black w/opacity/see-through */
  color: white;
  font-weight: bold;
  border: 3px solid #f1f1f1;
  position: absolute;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 2;
  width: 80%;
  padding: 20px;
  text-align: center;
}
</style>
    </head>
<body style="margin : 10px;">
	
	<%-- <font color="red"><%= exception.toString() %></font> --%>
	<div class="bg-image" style="width: 100%; margin-top: -5" align="center"></div>
	<div class="bg-text" style="width: 100%; margin-top: -5" align="center">
	<img src="pages/images/logosgp.png" style="width:15%; ">
	<h2>Session timed out</h2>
	<h3>Vui lòng Refesh lại ứng dụng từ trình duyệt, nếu vẫn gặp lỗi vui lòng liên hệ trung tâm để xử lý.</h3>
	<b><font color="red">Exception : Session đã hết hiệu lực. Vui lòng đăng nhập lại.</font> </b><br> 
    </div>
	
</body>
</html>