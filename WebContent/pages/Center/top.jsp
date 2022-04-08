<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.servlets.count.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %> 
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	} else{ %>
<% 
	String nnId = (String)session.getAttribute("nnId"); 
	if(nnId == null) 
	{
		nnId = "vi"; 
		String url = util.getChuyenNguUrl("KhoSvl","",nnId);
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../bootstrap/css/bootstrap.min.css" type="text/css">
<!-- BEGIN RENDER AUTO -->
<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>
<!-- END RENDER AUTO -->
<style>
	body { font-size: .875rem; }
	.navbar.navbar-default .navbar-text 
	{  color: #292929; }
	a:hover { color: #72afd2; }
</style>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0"  onbeforeunload="confirmMe()">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="logoutForm" method="post" action="../../LogoutSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>

<nav class="navbar navbar-expand-md navbar-light ">
  <img src="../images/salesup.jpg" width="100" height="40" class="mr-3" alt="...">
   <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
       <ul class="navbar-nav ml-auto">
	       <li class="nav-item">
			<A class="nav-link" href="../../ChangeLanguageTTSvl?Id=vi&userId=<%= userId %>&userTen=<%= userTen %>" target="_parent" style="font-weight: bold;">
				<IMG src = "../images/vietnamflag.png" heigth="14" width="19"> </A>			
			</li>
			<li class="nav-item">					
			<A class="nav-link" href="../../ChangeLanguageTTSvl?Id=en&userId=<%= userId %>&userTen=<%= userTen %>" target="_parent" style="font-weight: bold;">
				<IMG src = "../images/en_flag.png" heigth="14" width="19"> </A>
			</li>
			<li class="nav-item"> 
			<A class="nav-link" href="../../ChangeLanguageTTSvl?Id=zh&userId=<%= userId %>&userTen=<%= userTen %>" target="_parent" style="font-weight: bold;">
				<IMG src = "../images/chinaflag.png" heigth="14" width="19"> </A>
			</li>
			
			<li class="nav-item">
       		   <a class="nav-link" href="../hdsd/!SSL!/Responsive_HTML5/index.htm"  target="_blank"><i class="far fa-lightbulb"></i> Trợ giúp</a>
       	   </li>

           <li class="nav-item">
               <a class="nav-link" href="../../LoginSvl" target="_parent" onclick = "abc();">Đổi mật khẩu</a>
           </li>
           
           <li class="nav-item">
               <a class="btn btn-outline-primary btn-sm" href="../../LogoutSvl" target="_parent">Đăng xuất</a>
           </li>
       </ul>
    </div>
</nav>
</form>
<script src="https://kit.fontawesome.com/ff58e95563.js"></script>
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
</body>  
</HTML>
<%} %>
