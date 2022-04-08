<%@ page language="java" contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<% String msg = (String) session.getAttribute("msg");  
   session.setAttribute("msg", "");
%>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<html>
<LINK rel="stylesheet" href="pages/css/main.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<head>
<title>Doi mat ma dang nhap</title>

<SCRIPT language="javascript" type="text/javascript">
function check(){
	var oldpass = document.passForm.oldpass.value;
	var newpass1 = document.passForm.newpass1.value;
	var newpass2 = document.passForm.newpass2.value;

	if (oldpass == newpass1){
		alert("Vui lòng đổi mật mã mới");
		document.passForm.newpass1.focus();			
	}else{
		if (newpass1 != newpass2){
			alert("Xác nhận lại mật khẩu");
			document.passForm.newpass2.focus();			
		}else{
			document.forms['passForm'].submit();
		}
	}
}

</SCRIPT>
</head>

<body OnLoad="document.passForm.oldpass.focus();">
<form name="passForm" method="post" action="/AHF/LoginSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="login" value = "2"></input>
<table width="100%"	height="35%" border="0" cellpadding="0" cellspacing="0">
  	<tr bgcolor="#FFFFFF">
    	<td width="14%" height="52"  align="right" valign="middle" colspan=2><img src="pages/images/logosgp.png" width="100" height="40" align="right" /></td>
    	<td width="32%"  align="left" valign="middle" class="title" >&nbsp;</td>
   	  <td width="52%" align="right" class="footer" style="padding-right: 15">&nbsp; </td>
  	</tr>
	<tr class="tblightrow" height="3">
		<td  colspan="3" align="center" class="plainlabel">Vì lý do an ninh, vui lòng thay đổi mật mã&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

	</tr>

	<tr class="tblightrow" height="3">
		<td  colspan="3" align="center" class="blance">&nbsp;</td>
	</tr>

  	<tr class="tblightrow" height="3">
	  	<%if (msg != null){ %>
	  		<td width="14%" colspan="3" align="center" class="blanc"><%= msg %></td>
	  	<%} else {%>
			<td  colspan="3" align="center" class="blanc">&nbsp;</td>
		<%} %>
	</tr>
	<tr class="tblightrow" height="3">
	  	<td  colspan="4" align="center" class="blanc">&nbsp;</td>

	</tr>
	<tr class="tblightrow" height="1">
	  	<td  colspan="2" align="right" class="blanc">Mật mã cũ&nbsp;		</td>
	  	<td class="blanc" align="left">
		<input type="password" name = "oldpass" value = "" size="20" >		</td>
  	</tr>
	
	<tr class="tblightrow" height="1">
	  	<td  colspan="2" align="right" class="blanc">Mật mã mới&nbsp;		</td>
	  	<td class="blanc" align="left">
		<input type="password" name = "newpass1" value = "" size="20" >		</td>
  	</tr>

  	<tr class="tblightrow" height="1">
  		<td  colspan="2" align="right" class="blanc">Nhập lại mật mã mới&nbsp;		</td>
	  	<td class="blanc" align="left">	
		<input type="password" name = "newpass2" value = "" size="20" >		</td>
	</tr>

 	<tr height="1">
  		<td height="5" class="blanc" colspan="2" align="right">&nbsp;		</td>
	  	<td class="blanc" align="left">	
			<input type="button" value="Luu lai" onClick= "check();">		</td>
	</tr>

</table>
</form>
</body>
</html>
<%}%>