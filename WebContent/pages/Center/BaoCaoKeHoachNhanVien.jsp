<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.util.Date" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<%	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		$(".button").hover(function() {
			$(".button img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
	$(document).ready(function() {
		$(".button1").hover(function() {
			$(".button1 img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
</script>


<script language="javascript" type="text/javascript">

	function seach() 
	{
		document.forms['frm'].action.value = 'seach';
		document.forms["frm"].submit();
	}
	function submitform() 
	{
		document.forms['frm'].action.value = 'Taomoi';
		document.forms["frm"].submit();
	}

</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="frm" method="post" action="../../BaoCaoKeHoachNhanVien">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=obj.getuserId()%>'>
			
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo quản trị > Đánh giá > Kế hoạch nhân viên </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= obj.getuserTen() %></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea id="errors" name="errors" rows="2"  style="width: 100%;text-align: left; color:#F00 ; font-weight:bold">
						<%= obj.getMsg()%></textarea>
					<% obj.setMsg(""); %>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left;font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Tiêu Chí</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Từ</TD>
									<TD class="plainlabel">
										Tháng <select name="tuthang" style="width: 150px"  onchange="submitform()">
											<%
												
											for (int n = 1; n <=12; n++) {
												if (n == Integer.parseInt(obj.getFromMonth())) {
											%>
													<option value=<%=n%> selected="selected"><%=n%></option>
											<%
												} else {
											%>
													<option value=<%=n%>><%=n%></option>
											<%
												}
											}
											%>
										</select>
									</TD>
									<TD class="plainlabel">
										Năm <select name="tunam" style="width: 200px;" onchange="submit()">
									  	<%  
										  	DateFormat dateFormat = new SimpleDateFormat("yyyy");
									        Date date = new Date();
									        int year = Integer.parseInt(dateFormat.format(date));
									  		for(int i = year - 2; i < year + 2; i++) {
									  			if (obj.getFromYear().equals(String.valueOf(i))) { %>
										  			<option value ="<%=i %>" selected><%=i %></option>
										<%} 	else { %>
													<option value ="<%=i %>"><%=i %></option>
										<%		}
											}%>
										</select>
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel">Đến</TD>
									<TD class="plainlabel">
										Tháng <select name="denthang" style="width: 150px"  onchange="submitform()">
											<%
												
											for (int n = 1; n <=12; n++) {
												if (n == Integer.parseInt(obj.getToMonth())) {
											%>
													<option value=<%=n%> selected="selected"><%=n%></option>
											<%
												} else {
											%>
													<option value=<%=n%>><%=n%></option>
											<%
												}
											}
											%>
										</select>
									</TD>
									<TD class="plainlabel">
										Năm <select name="dennam" style="width: 200px;" onchange="submit()">
									  	<%  
									  		for(int i = year - 2; i < year + 2; i++) {
									  			if (obj.getToYear().equals(String.valueOf(i))) { %>
										  			<option value ="<%=i %>" selected><%=i %></option>
										<%} 	else { %>
													<option value ="<%=i %>"><%=i %></option>
										<%		}
											}%>
										</select>	
									</TD>
								</TR>
								
								<TR>
									<TD colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo </a>
									</TD>
								</TR>
							</TABLE>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
	</form>
<%
	if(obj!=null){obj.DBclose();
	obj = null;}
	session.setAttribute("obj", null);
%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>