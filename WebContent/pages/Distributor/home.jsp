<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<title>Best - Project</title>

<META http-equiv="Content-Style-Type" content="text/css">
<script src="../js/md5.js" type="text/javascript" language="javascript">
</script>
<script src="../js/scripts.js" type="text/javascript"
	language="javascript">
</script>
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" type="text/css" media="print" href="print.css">
<style>
body {
	font-family:verdana,arial,sans-serif;
	font-size:10pt;
	margin:2px;
	background-color:#FFFFFF;
	}
#chuadoc {min-width:300px;max-width: 700px }
#chuadoc ul { list-style: none;text-align: left; }
#chuadoc ul li { }
#chuadoc ul li a { display:block; text-decoration:none; color:#000000; background-color:#FFFFFF; line-height:30px;
	border-bottom-style:solid; border-bottom-width:1px; border-bottom-color:#CCCCCC; padding-left:10px; cursor:pointer; }
#chuadoc ul li a:hover { color:#FFFFFF; background-color: #80CB9B}
#chuadoc ul li a strong { margin-right:10px;font-size: 10px }
#chuadoc  { font-style:italic;  padding:10px 20px; margin:30px 0; color:#eee; }
</style>
<script type="text/javascript">
var ma,chuoi;
function goiham(id,st)
{
	ma = id;
	chuoi =st;
	goi();
}
function goi()
{
	ajaxOption(ma, chuoi);
	setTimeout(goi, 300000);
}
	function ajaxOption(id, str)
		{
			var xmlhttp;
			if (window.XMLHttpRequest)
			{// code for IE7+, Firefox, Chrome, Opera, Safari
			   xmlhttp = new XMLHttpRequest();
			}
			else
			{// code for IE6, IE5
			   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange=function()
			{
			   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			   {
				   var dl=xmlhttp.responseText;
		
				   dl=dl.replace('id="thongbao" style="display:none"', 'id="thongbao"');
				   dl=dl.replace('style=""', 'style="display:none"');
			       document.getElementById('chuadoc').innerHTML = dl;
			   }
			}
			xmlhttp.open("POST","../../ThongbaoAjax?q=" + str + "&id=" + id,true);
			xmlhttp.send();
		}
</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY onload="goiham('<%=userId%>', 'xxx')" leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0"  >
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
 	<TR height="22">
   		<TD height="15" align="left" class="tbnavigation">
      	<TD  align="right" class="tbnavigation">Chào mừng <%=userTen%> &nbsp;  
	</TR>

	<TR>

		<TD align='left' valign='top' bgcolor="#FFFFFF" colspan="2">
		<TABLE style="margin-top: -80px" width='100%' height="50%" border="0" cellspacing="0" cellpadding="0">
			<TR>
				<TD valign='middle' align='center' class="plaintitle">&nbsp;<div id="chuadoc"></div></TD>
			</TR>
		</TABLE>
		
	</TR>
</table>
</BODY>
</HTML>
<%}%>