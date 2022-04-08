<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript">

function warning() {
	window.top.location="/AHF/index.jsp";
}
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="sorryForm">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
	<h2>500 Internal Server Error</h2>
	<p>Sorry, something went wrong.</p>
	<p>This using function is broken or your session has been lost. You may try:</p>
	<p>Click <a href="javascript:warning();">here</a> to re-login and continue. 
		If the function is still in error, please contact to center immediately.
	</p>
	<img src="pages/images/Sorry.jpg" alt="Sorry" height="80%" width="80%">
	</form>
</BODY>
</HTML>
