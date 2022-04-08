<%@page import="geso.dms.center.util.Utility"%>
<%@page import="geso.dms.center.util.Csrf"%>
<%@page import="java.awt.Dimension"%>
<%@page import="java.awt.Toolkit"%>
<%@ page language="java" contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>


<% String msg = (String) session.getAttribute("msg");  
	String logoutMsg = (String) session.getAttribute("logoutMsg");  
	if(logoutMsg ==null) logoutMsg= "";
	Csrf csrf = new Csrf(request, response, true, true, true);	
   session.setAttribute("msg", "");
   session.setAttribute("logoutMsg", "");
   Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int screenWidth = screenSize.width;
	int screenHeight = screenSize.height;
   if(screenWidth>=1280)
   {
   	 session.putValue("chuoi","../css/style1280.css");
    }
   else if(screenWidth>=1024)
   {
   	 session.putValue("chuoi","../css/style1024.css");
   }
   else if(screenWidth>=800)
   {
   	 session.putValue("chuoi","../css/style800.css");
   }
%>
<html>
<head>
	<title>Đăng nhập</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<link rel="shortcut icon" href="pages/images/salesup.jpg">
	<LINK rel="stylesheet" href="pages/css/style_v2_optimized.css" type="text/css">
	<!-- <LINK rel="stylesheet" href="pages/css/main.css" type="text/css">  -->
	<style type="text/css">
		.blanc 
		{
		  color : #999999;
		  font-family: Arial, Helvetica, sans-serif;
		  letter-spacing : 0pt;
		  text-decoration: none ; 
		  font-weight : 200;
		  font-size: 1.0em;
		}
		
		.blanc1 
		{
		  color : #999999;
		  font-family: Arial, Helvetica, sans-serif;
		  letter-spacing : 0pt;
		  text-decoration: none ; 
		  font-weight : 200;
		  font-size: 0.8em;
		}
		
 		 body {
			background-image: url(pages/images/background.jpg);
		} 
	</style>
<script>

function thongbao()
{   var tt = document.forms['loginForm'].logoutMsg.value;
	if(tt.length>0)
    	alert(document.forms['loginForm'].logoutMsg.value);
	
	document.forms['khForm'].logoutMsg.value= '';
}
</script>
	
	
</head>
<body style = "background-image: url(pages/images/background.jpg); background-size:85%;">
<form name="loginForm" method="post">  
<input type="hidden" name="login" value = "1"></input>
<INPUT name="<%=csrf.get_tokenName() %>" type="hidden" value="<%=csrf.get_tokenValue() %>" >
<input type="hidden" name="logoutMsg" value = "<%=logoutMsg%>"></input>

<script language="javascript" type="text/javascript">
    thongbao();
</script>

<table align="left" style="width:26%; margin-left: 40px; " cellpadding="0px" cellspacing="0px" >
    	<tr height="25px">
        	<td style="background-image:url(pages/images/borderTopLeft.png); width:25px"></td>
            <td style="background-image:url(pages/images/borderTopCenter.png)"></td>
            <td style="background-image:url(pages/images/borderTopRight.png); width:25px"></td>
        </tr>
    <tr>
    <td style="background-image:url(pages/images/borderMiddleLeft.png); width:25px; background-repeat:repeat-y; background-position:left"></td>
    <td style="background-image:url(pages/images/point_make_area_transparent.png); background-color:white; ">
    
    <div style="width: 100%; margin-top: -5" align="center">
   		 <img src="pages/images/logosgp.png" style="width:50%; ">
    	<img src="pages/images/salesup.jpg" align="top" style="display:none; width:26%; height:40%; " >
    </div>
    <table cellpadding="1px" cellspacing="4px">
    	<tr>
    		<td class="blanc"><span>Tên đăng nhập</span></td>
    		<td>&nbsp;</td>
    	</tr>
    	<tr>
    		<td colspan="2">
    			<div class="input-field-login icon username-container">
		            <input name="username" id="username" autofocus="autofocus" placeholder="Vui lòng nhập tên đăng nhập." 
		                    class="std_textbox" tabindex="1" required="" type="text" AUTOCOMPLETE="off" value="admin" ><!-- /130046 -->
		        </div>
    		</td>
    	</tr>
    	<tr>
    		<td class="blanc"><span>Mật khẩu</span></td>
    		<td>&nbsp;</td>
    	</tr>
    	<tr>
    		<td colspan="2">
    			<div class="input-field-login icon password-container">
		            <input name="password" id="password" placeholder="Vui lòng nhập mật khẩu." class="std_textbox" tabindex="2" required="" type="password" AUTOCOMPLETE="off" value="admin1">
		        </div>
    		</td>
    	</tr>
    	<tr>
    		<td class="blanc"><span>Mã bảo vệ</span></td>
    		<td>&nbsp;</td>
    	</tr>
    	<tr>
    		<td colspan="2">
    			<div class="input-field-login icon username-container">
		            <input name="captchas" id="captchas" autofocus="autofocus" placeholder="Vui lòng nhập mã xác nhận." 
		                    class="std_textbox" tabindex="3" required="" type="text" AUTOCOMPLETE="off" value="geso123" >
		        </div>
    		</td>
    	</tr>
    	<tr>
    		<td colspan="2"><img src= "<%= csrf._create_link("/AHF/CaptchaSvl" ) %>"></td>
    	</tr>
    	<tr>
    		<td colspan="2"><input type="submit" value=" Đăng nhập "></td>
    	</tr>
    	<%if (msg != null){ if(msg != ""){ %>
	  		<tr>
    			<td colspan="2"><font color="red"><%= msg %></font></td>
    		</tr>
	  	<%}} %>
    	<tr></tr>
    <!-- 	<tr>
    		<td class="blanc1" align="right" >Hotline GESO: 0901 312 239 </td>
    	</tr>
    	<tr>
    		<td class="blanc1" align="right">DDT: 0976 475 461 </td>
    	</tr> -->
    	
	  	
    </table>

    </td>
    <td style="background-image:url(pages/images/borderMiddleRight.png); width:25px; background-repeat:repeat-y; background-position:right"></td>
</tr>
<tr height="25px">
	<td style="background-image:url(pages/images/borderBottomLeft.png); width:25px"></td>
    <td style="background-image:url(pages/images/borderBottomCenter.png)"></td>
    <td style="background-image:url(pages/images/borderBottomRight.png); width:25px"></td>
</tr>
</table>

</form>


<%
	String captcha = "geso123";
	//String captcha = (String) session.getAttribute("captcha");
	String username = (String) geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("username"));
	String password = (String) geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("password"));
	//String cap = "12345";
	String cap = (String) geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("captchas"));	
	
	if(session.getAttribute("solandn") != null)
	{
		//System.out.println("So lan dang nhap o tren: " + session.getAttribute("solandn").toString() + "\n");
		if(Integer.parseInt(session.getAttribute("solandn").toString()) >= 20)
		{
			out.print("<script>alert('Bạn đã dăng nhập vượt quá số lần qui định')</script>");
			return;
		}
	}
		
	if (cap != null && username != null && password != null)
	{
		if (captcha.toUpperCase().equals(cap.toUpperCase())) 
		{
			out.print("<script>document.forms['loginForm'].username.value='" + username + "'; document.forms['loginForm'].password.value='" + password + "'; document.forms['loginForm'].action = '/AHF/LoginSvl'; document.forms['loginForm'].submit(); </script>");
		} 
		else
		{
			if(session.getAttribute("solandn") == null)
				session.setAttribute("solandn", 0);
			else
			{
				session.setAttribute("solandn", Integer.parseInt(session.getAttribute("solandn").toString()) + 1);
				//System.out.println("So lan dang nhap: " + session.getAttribute("solandn").toString() + "\n");
			}
			out.print("<script>alert('Mã bảo vệ không đúng, vui lòng nhập lại')</script>");
		}
	}
%>
</body>
</html>
