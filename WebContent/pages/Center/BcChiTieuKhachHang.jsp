<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.dms.center.util.Utility"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
	<%
		IStockintransit obj = (IStockintransit) session.getAttribute("obj");
		ResultSet kenh = obj.getkenh();
		ResultSet vung = obj.getvung();
		ResultSet khuvuc = obj.getkhuvuc();
		ResultSet npp = obj.getnpp();
		ResultSet dvkd = obj.getdvkd();
		ResultSet dvdl = obj.getdvdl();
		ResultSet gsbh = obj.getgsbh();
		ResultSet ddkd = obj.getRsddkd();
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>OPV - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
		$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
	$("#vungId").select2();
	$("#khuvucId").select2();
	$("#ddkdId").select2();
});
</script>

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
	function submitform() {
		var fieldShow = document.getElementsByName("fieldsHien");		
		for (var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}	
		document.forms['frm'].action.value= 'Taomoi';
		document.forms["frm"].submit();
		reset();
	}
	
	function ChiTiet() {
		var fieldShow = document.getElementsByName("fieldsHien");		
		for (var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}	
		document.forms['frm'].action.value= 'ChiTiet';
		document.forms["frm"].submit();
		reset();
	}
	
	function seach()
	{
		document.forms['frm'].action.value= 'seach';
		document.forms["frm"].submit();
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");
		var fieldHidden = document.getElementsByName("fieldsAn");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}
		for ( var i = 0; i < fieldHidden.length; ++i) {
			fieldHidden.item(i).checked = false;
		}
	};
	
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

	<form name="frm" method="post" action="../../BcChiTieuKhachHangSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="view" value='TT'>
	<input type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý chỉ tiêu > Báo cáo > Chỉ tiêu Khách Hàng</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen%></div>
			</div>
			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="#"> <img
					src="../images/Back30.png" alt="Quay ve" title="Quay ve"
					border="1px" longdesc="Quay ve" style="border-style: outset"></A>
				<A href="javascript:saveform()"> <IMG src="../images/Save30.png"
					title="Luu lai" alt="Luu lai" border="1px"
					style="border-style: outset"></A>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
					<%= obj.getMsg() %></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"> Chỉ tiêu của Khách Hàng </legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Từ ngày</TD>
									
									<TD class="plainlabel">
									<input type="text" name="Sdays"	class="days" value='<%=obj.gettungay()%>' /></TD>							
									
									
									<TD class="plainlabel">Đến ngày</TD>
									<TD>
										<input type="text" name="Edays" class="days" value='<%=obj.getdenngay()%>' /></td>
									
									
								</TR>
								<TR>
									<TD class="plainlabel">Kênh bán hàng</TD>									
									<TD class="plainlabel">
										<select name="kenhId" id="kenhId" onchange="seach();">
												<option value="" selected>All</option>
												<%if (kenh != null)
														while (kenh.next()) {
															if (kenh.getString("pk_seq").equals(obj.getkenhId())) {%>
												   		<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("diengiai")%></option>
												   <%} else {%>
												   	<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("diengiai")%></option>
													<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Chi nhánh / Nhà phân phối</TD>
									<TD class="plainlabel">
										<select name="nppId" onchange="seach();" id="nppId" style="width:250px">
											<option value="" selected>All</option>
											<%if (npp != null)
													while (npp.next()) {
														if (npp.getString("pk_seq").equals(obj.getnppId())) {
												%>
											   	<option value="<%=npp.getString("pk_seq")%>" selected><%=npp.getString("ten")%></option>
											   <%} else {%>
											   	<option value="<%=npp.getString("pk_seq")%>"><%=npp.getString("ten")%></option>
												<%}}%>
										</select>
									</TD>
								</TR>
								<TR>
								<TD class="plainlabel">Miền</TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();" style="width:250px">
											<option value="" selected>All</option>
											<%if (vung != null)
													while (vung.next()) {
														if (vung.getString("pk_seq").equals(obj.getvungId())) {%>
											   				<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
											   			<%} else {%>
											   				<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
														<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Đơn vị kinh doanh</TD>
									<TD class="plainlabel">
										<select name="dvkdId" id="dvkdId" onchange="seach();" >
											<option value="" selected>All</option>
											<%if (dvkd != null)
													while (dvkd.next()) {
														if (dvkd.getString("pk_seq").equals(obj.getdvkdId())) {%>
										   				<option value="<%=dvkd.getString("pk_seq")%>" selected><%=dvkd.getString("diengiai")%></option>
										   		  <%} else {%>
										   				<option value="<%=dvkd.getString("pk_seq")%>"><%=dvkd.getString("diengiai")%></option>
													<%}}%>
											</select>
										</TD>
									</TR>
								<TR>									
									<TD class="plainlabel">Khu Vực</TD>
									<TD class="plainlabel">
									<select name="khuvucId" id="khuvucId" onchange="seach();" style="width:250px">
											<option value="" selected>All</option>
											<%if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
											   <option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
											<%}}%>
									</select>
									</TD>	
									<TD class="plainlabel">Nhân viên bán hàng</TD>
									<TD class="plainlabel">
										<select name="ddkdId" id="ddkdId" style="width:250px" onchange="seach();">
											<option value="" >All</option>
											<%if (ddkd != null)
													while (ddkd.next()) {
														if (ddkd.getString("pk_seq").equals(obj.getDdkd())) {%>
											   <option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=ddkd.getString("pk_seq")%>"><%=ddkd.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>								
								</TR>	
														
								<TR>
									<td colspan="2"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> BC Chỉ tiêu Khách Hàng
									</a></td>
									
									<td colspan="2"><a class="button"
										href="javascript:ChiTiet();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> BC Doanh Số Khách Hàng
									</a></td>
									
								</TR>
							</TABLE>
						</div>
						<hr>
						<div style="width: 100%; float: none;">
							
							
						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<br /> <br /> <br /> <br />		
	</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%
	try
	{
		if(!(ddkd == null))
			ddkd.close();
		if(dvdl != null)
			dvdl.close();
		if(dvkd != null)
			dvkd.close();
		if(gsbh != null)
			gsbh.close();
		if(kenh != null)
			kenh.close();
		if(khuvuc != null)
			khuvuc.close();
		if(npp != null)
			npp.close();

		if(obj != null){
			obj.DBclose();
			obj = null;
		}
	}catch(java.sql.SQLException e){}
	}
%>