<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nganhang.IErpNganHang" %>
<%@ page  import = "geso.dms.center.beans.nganhang.imp.ErpNganHang" %>
<%@ page  import = "java.sql.ResultSet"%>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%
	IErpNganHang nhBean =(ErpNganHang)session.getAttribute("nhBean");
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript">
function msg()
{
alert("Lưu dữ liệu thành công!");
}

function submitform(){
	var manh = document.forms['nhForm'].Ma.value;
	if(manh == "")                        
	{
		document.forms['nhForm'].dataerror.value = "Vui lòng nhập mã ngân hàng.";
		return;              
	}
	
	var tennh = document.forms['nhForm'].Ten.value;
	if(tennh == "")
	{
		document.forms['nhForm'].dataerror.value = "Vui lòng nhập tên ngân hàng.";
		return;
	}
	
	document.forms["nhForm"].submit();
	
}
</script>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="nhForm" method="post" action="../../ErpNganHangUpdateSvl" charset="UTF-8">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<INPUT name="action" type="hidden" value="save">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; &nbsp;Kinh doanh &gt; Ngân hàng
							   &gt; Tạo mới </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp; </td>
					      </tr>
   
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"> <A href="/AHF/ErpNganHangSvl" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD>&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
								<TABLE width="100%" cellspacing="0" cellpadding="6">
										<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông báo </LEGEND>
			    				<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1" ><%=nhBean.getMsg()%></textarea>
								
								</FIELDSET>
						   </TD>
							</tr>	
							<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin ngân hàng
								</LEGEND>
								<TABLE  width="100%" cellspacing="0" cellpadding="6">		
									<TR>
									  <TD width="15%" class="plainlabel">Mã <FONT class="erroralert"></FONT></TD>
									  <TD  class="plainlabel" ><INPUT type = "text" name="Ma" id = "ma" size="40"
										type="text" value="<%=nhBean.getMA() %>" >  
										
										</TD>
										
										
								    </TR>
	
									<tr>
									<td width = "15%" class = "plainlabel">Tên <font class = "erroralert"></font> </td> 
									<td class = "plainlabel"> <input type = "text" name = "Ten" id = "ten" value="<%=nhBean.getTEN() %>"> </td>
									</tr>

									<TR>
									<TD class = "plainlabel">
									Hoạt động
									</TD>
									<TD class = "plainlabel">
									<%
									   if(nhBean.gettrangthai().equals("1")){ %>
										<input name="hoatdong" checked="checked" type="checkbox" value="1">
									<%}else{ %>
										<input name="hoatdong" type="checkbox" value="1" >
									<%} %>
									</TD>
									
									</TR>
								</TABLE>
								</FIELDSET>			
								</TD> </tr></TABLE>
				 </TD>
			</TR>
		</TABLE>				
</form>

</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
