<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");	
	ResultSet npps = obj.getnpp();
	ResultSet vungs = obj.getvung();
	ResultSet khuvucs = obj.getkhuvuc();
	ResultSet kenhs = obj.getkenh();
	ResultSet giamsats = obj.getgsbh();
	ResultSet dvkds = obj.getdvkd();
	ResultSet ddkds = obj.getRsddkd();
	ResultSet nhomRs = obj.GetRsNhomSP();
 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
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
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
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
	function seach() {
		document.forms['frm'].action.value = 'seach';
		document.forms["frm"].submit();
	}
	function submitform() {
		
		var bien=document.forms['frm'].typeid.value;
		if(bien=="0"){
		if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return;
		}
		}
		document.forms['frm'].action.value = 'Taomoi';
		document.forms["frm"].submit();
	}
	
	function LayTheoNgay()
	{
		document.forms['frm'].typeid.value=0;
		document.getElementById("TheoThang").style.display = "none";
		document.getElementById("TheoNgay").style.display = "";
	}
	
	function LayTheoThang()
	{
		document.forms['frm'].typeid.value=1;
		document.getElementById("TheoNgay").style.display = "none";
		document.getElementById("TheoThang").style.display = "";
	}
	
	function LayTheoTuan()
	{
		document.forms['frm'].typeid.value=2;
		document.getElementById("TheoThang").style.display = "none";
		document.getElementById("TheoNgay").style.display = "";
	}
	
	function setErrors(errorMsg) 
	{
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}

</script>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>	


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="frm" method="post" action="../../ThucDatVaChiTieuSKUIn">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="typeid" value=<%=obj.gettype()%>> 
		
		<input type="hidden" name="action" value='1'> <input
			type="hidden" name="userId" value='<%=obj.getuserId()%>'>
			
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"> Quản lý chỉ tiêu &#62; Báo cáo &#62; Thực đạt & chỉ tiêu SKU  </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= obj.getuserTen() %></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea id="errors" name="errors" rows="2"  style="width: 100%;text-align: left; color:#F00 ; font-weight:bold">
						<%= obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left;font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Thực đạt và chỉ tiêu SKU In</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Xem theo</TD>
									<TD class="plainlabel" colspan="3">
									<% if(obj.gettype().equals("1")){ %>
										<input type="radio" name="xemtheo" onchange="LayTheoNgay();" value="0" />Ngày &nbsp;&nbsp;&nbsp;
										<input type="radio" name="xemtheo" onchange="LayTheoTuan();" value="2" />Tuần &nbsp;&nbsp;&nbsp;
										<input type="radio" name="xemtheo" onchange="LayTheoThang();" value="1" checked="checked"/>Tháng
									<%}else{ if(obj.equals("2")) {  %>
										<input type="radio" name="xemtheo" onchange="LayTheoNgay();" value="0" />Ngày &nbsp;&nbsp;&nbsp;
										<input type="radio" name="xemtheo" onchange="LayTheoTuan();" value="2" checked="checked" />Tuần &nbsp;&nbsp;&nbsp;
										<input type="radio" name="xemtheo" onchange="LayTheoThang();" value="1" />Tháng
									<%} else { %> 
										<input type="radio" name="xemtheo" onchange="LayTheoNgay();" value="0"  />Ngày &nbsp;&nbsp;&nbsp;
										<input type="radio" name="xemtheo" onchange="LayTheoTuan();" value="2" />Tuần &nbsp;&nbsp;&nbsp;
										<input type="radio" name="xemtheo" onchange="LayTheoThang();" value="1" checked="checked" />Tháng
									<%} } %>
									</TD>
								</TR>
								<TR id="TheoThang">
									<TD class="plainlabel">Từ tháng</TD>
									<TD class="plainlabel">
									 <select name="tuthang" class="notuseselect2"  style="width :50px" onchange="loctien()">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										String chuoi=k<10?"0"+k:""+k;
  									  if(obj.getFromMonth().equals(chuoi)){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=k%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									<select name="tunam"  class="notuseselect2"  style="width :50px" onchange="loctien()">
									<option value=0> </option>  
									<%
									  
  										int n;
  										for(n=2008;n<2025;n++){
  										if(obj.getFromYear().equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
									</TD>
								
									<TD class="plainlabel">Tới tháng</TD>
									<TD class="plainlabel">
									 <select name="denthang"  class="notuseselect2" style="width :50px" onchange="loctien()">
									<option value=0> </option>  
									<%
  									
  									for(k=1;k<=12;k++){
  										String chuoi=k<10?"0"+k:""+k;
  									  if(obj.getToMonth().equals(chuoi)){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=k%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									<select name="dennam"  class="notuseselect2" style="width :50px" onchange="loctien()">
									<option value=0> </option>  
									<%
									  
  									
  										for(n=2008;n<2025;n++){
  										if(obj.getToYear().equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
									</TD>
								</TR>
								<TR id="TheoNgay" style="display: none">
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel">
										<input type="text" name="Sdays"	class="days" value='<%= obj.gettungay() %>' /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<td>
										<input type="text" name="Edays" class="days" value='<%= obj.getdenngay() %>' /></td>
								</TR>
								<TR>
									<TD class="plainlabel">Kênh bán hàng</TD>
									<TD class="plainlabel">
										<select name="kenhId" id="kenhId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (kenhs != null)
													while (kenhs.next()) {
														if (kenhs.getString("pk_seq").equals(obj.getkenhId())) {%>
														<option value="<%=kenhs.getString("pk_seq")%>" selected><%=kenhs.getString("diengiai")%></option>
											<%} else { %>
												<option value="<%=kenhs.getString("pk_seq")%>"><%=kenhs.getString("diengiai")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Giám sát bán hàng</TD>
									<TD class="plainlabel">
										<select name="gsbhs" id="gsbhsId"
											onchange="seach();">
												<option value="" selected>All</option>
												<%if (giamsats != null)
														while (giamsats.next()) {
															if (giamsats.getString("pk_seq").equals(obj.getgsbhId())) {%>
														<option value="<%=giamsats.getString("pk_seq")%>" selected>
															<%=giamsats.getString("ten")%></option>
												<%} else {%>
														<option value="<%=giamsats.getString("pk_seq")%>"><%=giamsats.getString("ten")%></option>
												<%}}%>
										</select>
									</td>
								</TR>
								<TR>
									<TD class="plainlabel">Chi nhánh</TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (vungs != null)
													while (vungs.next()) {
														if (vungs.getString("pk_seq").equals(obj.getvungId())) {%>
													<option value="<%=vungs.getString("pk_seq")%>" selected><%=vungs.getString("ten")%></option>
												<%} else {%>
													<option value="<%=vungs.getString("pk_seq")%>"><%=vungs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Nhà Phân Phối </TD>
									<TD class="plainlabel">
										<select name="nppId" onchange="seach();" id="nppId">
											<option value="" selected>All</option>
											<%if (npps != null)
													while (npps.next()) {
														if (npps.getString("pk_seq").equals(obj.getnppId())) {%>
															<option value="<%=npps.getString("pk_seq")%>" selected><%=npps.getString("ten")%></option>
													<%} else {%>
														<option value="<%=npps.getString("pk_seq")%>"><%=npps.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Khu vực</TD>
									<TD class="plainlabel">
										<select name="khuvucId" id="khuvucId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (khuvucs != null)
													while (khuvucs.next()) {
														if (khuvucs.getString("pk_seq").equals(obj.getkhuvucId())) {%>
															<option value="<%=khuvucs.getString("pk_seq")%>" selected><%=khuvucs.getString("ten")%></option>
													<%} else {%>
														<option value="<%=khuvucs.getString("pk_seq")%>"><%=khuvucs.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Đơn vị kinh doanh</TD>
									<TD class="plainlabel">
										<select name="dvkdId" id="dvkdId"	onchange="seach();">
											<option value="" selected>All</option>
											<%if (dvkds != null)
													while (dvkds.next()) {
														if (dvkds.getString("pk_seq").equals(obj.getdvkdId())) {%>
														<option value="<%=dvkds.getString("pk_seq")%>" selected><%=dvkds.getString("diengiai")%></option>
													<%} else {%>
														<option value="<%=dvkds.getString("pk_seq")%>"><%=dvkds.getString("diengiai")%></option>
													<%}}%>
										</select>
									</TD>
								</TR>
								<tr>
									<TD class="plainlabel">Nhân viên bán hàng</TD>
									<TD class="plainlabel">
										<select name="ddkdId" 
											onchange="seach();">
												<option value="" selected>All</option>
												<%if (ddkds != null)
														while (ddkds.next()) {
															if (ddkds.getString("pk_seq").equals(obj.getDdkd())) {%>
														<option value="<%=ddkds.getString("pk_seq")%>" selected>
															<%=ddkds.getString("ten")%></option>
												<%} else {%>
														<option value="<%=ddkds.getString("pk_seq")%>"><%=ddkds.getString("ten")%></option>
												<%}}%>
										</select>
									</td>
									<TD class="plainlabel">Nhóm sản phẩm</TD>
							  	  	<TD class="plainlabel">
									<select name="nspId" >
										<option value="" selected>All</option>
										<%if (nhomRs != null){
												while (nhomRs.next()) {
													if (nhomRs.getString("pk_seq").equals(obj.getNspId())) {%>
														<option value="<%= nhomRs.getString("pk_seq")%>" selected><%=nhomRs.getString("diengiai")%></option>
												<%} else {%>
													<option value="<%= nhomRs.getString("pk_seq")%>"><%=nhomRs.getString("diengiai")%></option>
												<%} } } %>
									</select>
								</tr>
								
								
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo </a></td>
								</TR>
							</TABLE>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
	</form>
<%
	if(kenhs !=null) kenhs.close();
	if(vungs !=null) vungs.close();
	if(khuvucs !=null) khuvucs.close();
	if(npps !=null) npps.close();
	if(dvkds !=null) dvkds.close();
	if(giamsats !=null) giamsats.close();
	if(ddkds !=null) ddkds.close();		
	if(obj!=null){obj.DBclose();
	obj = null;}
	session.setAttribute("obj", null);
%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>