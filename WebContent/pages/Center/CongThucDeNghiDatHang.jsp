<%@page import="geso.dms.center.beans.congthucdndh.imp.Congthucdndh"%>
<%@page import="geso.dms.center.beans.congthucdndh.ICongthucdndh"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.mokhaibaochitieu.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Bibica/index.jsp");
	}else{ %>

<% ICongthucdndh obj = (Congthucdndh)session.getAttribute("obj"); %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
	function submit()
	{
		
    	document.forms['mkbctBean'].submit();
	}	


</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type = "hidden" name="action" value = "1">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="1" height="100%">
	<TR>
		<TD align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>				
					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<TR height="22">
				   				<TD align="left" class="tbnavigation">
				   					&nbsp;Quản lý bán hàng &gt; Công thức đề nghị đặt hàng</TD>
				   				<TD  align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp;  </TD> 
		     				</TR>
   
						</TABLE>
					</TD>								
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="1"  cellspacing="1" >
			  <tr>
				<TD align="left" colspan="4" class="legendtitle">
				<FIELDSET>
				<LEGEND class="legendtitle">Thông báo</LEGEND>
			
   				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= obj.getMsg() %></textarea>
				</FIELDSET>
				</TD>
			 </tr> 
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thiết lập công thức đặt hàng (Chỉ nhập số)</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
							  	<TD class="plainlabel">Số ngày tồn kho tối thiếu </TD>
						  	  	<TD class="plainlabel">
									<input type="text" name="ngaytktoithieu" id="ngaytktoithieu"  value="<%=obj.getNgayTonKhoToithieu() %>"  style="text-align: right">
						  	  	</TD>
						  </TR>

						  <TR>
							  	<TD class="plainlabel">Phần trăm tăng trưởng </TD>
						  	  	<TD class="plainlabel">
									<input type="text" name="muctangtruong" id="muctangtruong"  value="<%=obj.getMucTangTruong() %>"  style="text-align: right"> % 
						  	  	</TD>
						  </TR>
 							 <TR>
							  	<TD class="plainlabel">Mức thuế đơn đặt hàng </TD>
						  	  	<TD class="plainlabel">
									<input type="text" name="mucthue" id="mucthue"  value="<%=obj.getmucthue()%>"  style="text-align: right"> % 
						  	  	</TD>
						  </TR>
 						  <TR>							  	
 						   <TD><a class="button2" href="javascript:submit();">
							<img style="top: -4px;" src="../images/button.png" alt="">Lưu</a>&nbsp;&nbsp;&nbsp;&nbsp;
 						  </TD>
						 </TR>

						</TABLE>
						</FIELDSET>
					</TD>
				</TR>
		</TABLE>
	</TD>
</TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
}%>