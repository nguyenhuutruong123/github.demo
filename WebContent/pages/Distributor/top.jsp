<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Bibica/index.jsp");
	}else{ %>
	
<% 
String nnId = (String)session.getAttribute("nnId"); 
if(nnId == null) {
	nnId = "vi"; 
String url = util.getChuyenNguUrl("KhoSvl","",nnId);
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>&nbsp;</TITLE>

<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script src="../scripts/jquery-1.8.1.js" type="text/javascript"></script>
<SCRIPT type="text/javascript">
 function submitform()
{
    document.forms["logoutForm"].submit();
}
</SCRIPT>
<script type="text/javascript">
var flag = true;
function abc()
{
	flag = false;
}

function Khoaso()
{
	var flag = document.getElementById('khoaso');
	flag.src = flag.src;

}

document.khoaso = function()
{	
	var frame_id = 'khoaso';
    if(window.document.getElementById(frame_id).location ) {  
        window.document.getElementById(frame_id).location.reload(true);
    } else if (window.document.getElementById(frame_id).contentWindow.location ) {
        window.document.getElementById(frame_id).contentWindow.location.reload(true);
    } else if (window.document.getElementById(frame_id).src){
        window.document.getElementById(frame_id).src = window.document.getElementById(frame_id).src;
    } else {
        // fail condition, respond as appropriate, or do nothing
        alert("Sorry, unable to reload that frame!");
    }

};

function confirmMe()
{
	if(flag)
	{
		$(window).bind('unload', function(){	
			$.ajax({
		    	cache: false,
		    	async: false,
		    	dataType: "script",
		        url: "../../ThongbaoAjax"
		    });
		});
	}

}
</script>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->

<style>

a:hover {
  color: #72afd2;
}
</style>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0"  onbeforeunload="confirmMe()">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<div id="dataDisplay"></div>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">

	<TR bgcolor="#FFFFFF">
		<TD style="width:80%;" align="right" valign="middle">
		<table width="100%" height="100%">
		<TR>
		<TD>
			<!-- <img src="../images/logosgp.png" style="top:-25%;position:absolute;display:flex;height:160%;"> -->
		</TD>
		<TD>
			<!-- <img src="../images/logo1.png" style="top:0;left:15%;position:absolute;display:flex;height:100%;"> -->
		</TD>
		<TD>
			<img src="../images/salesup.jpg" style="top:8px;right:86%;position:absolute;display:flex;height:82%;">
		</TD>
		</table>
		</TD>
		<TD style="width:7%;" align="center" valign="middle" class="blanc" >
		
		<A href="../../ChangeLanguageTTSvl?Id=vi&userId=<%= userId %>&userTen=<%= userTen %>" target="_parent" style="font-weight: bold;">
			<IMG src = "../images/vietnamflag.png" heigth="14" width="19"> </A>								
		<A href="../../ChangeLanguageTTSvl?Id=en&userId=<%= userId %>&userTen=<%= userTen %>" target="_parent" style="font-weight: bold;">
			<IMG src = "../images/en_flag.png" heigth="14" width="19"> </A> 
		<A href="../../ChangeLanguageTTSvl?Id=zh&userId=<%= userId %>&userTen=<%= userTen %>" target="_parent" style="font-weight: bold;">
			<IMG src = "../images/chinaflag.png" heigth="14" width="19"> </A>
			</TD>
			<TD> 
		<A href="../../LogoutSvl"  style="font-weight: bold" target="_parent" ><%=ChuyenNgu.get("Đăng xuất",nnId) %> &nbsp;&nbsp;</A>
		</TD>
		<TD>
		<A href="../../LoginSvl" style="font-weight: bold" onclick = "abc();" target="_parent" ><%=ChuyenNgu.get("Đổi mật khẩu",nnId) %>  &nbsp;&nbsp;</A>
		</TD>
	</tr>
<!-- 	<tr>
		<td align="left">
			<span style="text-align: left; font-size: 0.7em">
				<iframe  style="width: 300px ;height: 20px" id = "khoaso" src = "counter.jsp" frameborder="0" scrolling="no"> </iframe>
			</span></td>
		<td>&nbsp</td>
	</tr> -->
</table>
</form>
</BODY>
</HTML>

<%}%>