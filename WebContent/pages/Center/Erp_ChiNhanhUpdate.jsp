<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.erp_chinhanh.IErp_chinhanh" %>
<%@ page  import = "geso.dms.center.beans.erp_chinhanh.imp.Erp_chinhanh" %>
<%@ page  import = "java.sql.ResultSet"%>

<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ 

	IErp_chinhanh cnBean = (IErp_chinhanh)session.getAttribute("cnBean");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Best - Update Chi Nhánh</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript">
function submitform()
{
	
	var ma = document.forms['cnForm'].ma.value;
	if(ma == "")                        
	{
		document.forms['cnForm'].dataerror.value = "Vui lòng chọn mã chi nhánh";
		return;
	}
	
	var ten = document.forms['cnForm'].ten.value;
	if(ten == "")                        
	{
		document.forms['cnForm'].dataerror.value = "Vui lòng chọn tên chi nhánh";
		return;
	}
	document.forms["cnForm"].submit();	
}
</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="cnForm" method="post" action="/AHF/Erp_ChiNhanhUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" value="save" name="action">
<INPUT type="hidden" name="userId" value='<%=userId %>' size="30">
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type = "hidden" value = "<%=cnBean.getID()%>" name = "id">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<table width="100%" cellpadding="0" cellspacing="1">
			<tr>
					<td align="left" class="tbnavigation">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr height="22">
						<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kinh doanh &gt; Chi nhánh &gt; Cập nhật </TD>
						<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td> 
					</tr>
					</table>
					</td>
				</tr>
			</table>
				
			<table width="100%" border="0" cellpadding="3" cellspacing="0">
				<tr>
					<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class = "tbdarkrow">
						<td width="30" align="left"> <a href="/AHF/Erp_ChiNhanhSvl?userId=<%=userId %>" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </a></td>
					    <td width="2" align="left" ></td>
						<td width="30" align="left" ><a href="javascript: submitform()" ><img src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></a></td>
						<td>&nbsp;</td>						
					</tr>
					</table>
					</td>
				</tr>
			</table>
								  				
					<table width="100%" cellspacing="0" cellpadding="6">
					<tr>
						<td align="left" colspan="4" class="legendtitle">
						<fieldset>
							<legend class="legendtitle">Thông báo </legend>
			    			<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1" ><%=cnBean.getMsg()%></textarea>
						</fieldset>
						</td>
					</tr>	
					
					<tr>
				<td align="left" colspan="4" class="legendtitle">
				<fieldset>
					<legend class="legendtitle">Thông tin chi nhánh</legend>
					<TABLE  width="100%" cellspacing="0" cellpadding="6">
										
					
					<tr>
						<td width="15%" class="plainlabel">Mã<font class="erroralert"></font></td>
					 	<td  class="plainlabel" ><input type = "text" name="ma" size="40" value="<%=cnBean.getMA()%>" ></td>
					</tr>

						<tr>
					  		<td class="plainlabel">Tên</td> <td class="plainlabel"> <input type = "text" name = "ten" value="<%=cnBean.getTEN()%>"> </td>
						</tr>

					<tr>
						<td class="plainlabel">Hoạt động</td>
						<td class="plainlabel">
							<%if(cnBean.getTRANGTHAI().equals("1")){%>
								<input name = "hoatdong" checked = "checked" type = "checkbox" value = "1">
							<%}
							else { %>
								<input name = "hoatdong" type = "checkbox" value = "1">
								 <%}%>
						</td>
					</tr>
					</table>
				</fieldset>			
				</td>
			</tr>
</table>
</td></tr>
</table>				
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%
	cnBean.DBClose();
	} %>
