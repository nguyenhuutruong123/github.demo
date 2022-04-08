<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.report.IBcSuDungKhuyenMai"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page  import = "geso.dms.center.util.*" %>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>

<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	IBcSuDungKhuyenMai obj = (IBcSuDungKhuyenMai) session.getAttribute("obj");
	ResultSet ddkd = obj.getDdkdRs();
	ResultSet khRs = obj.getKhRs();
	ResultSet spRs = obj.getSpRs();
	ResultSet hdRs = obj.getHoadonRs();
	ResultSet kbhRs = obj.getKbhRs();
	ResultSet totalRs = obj.getTotalRs();
	
	Utility util = (Utility) session.getAttribute("util");
	String url="";
	/* url = util.getUrl("BcDoanhThuBanHangTTSvl",""); */

	ResultSet vungRs = obj.getVungRs();
	ResultSet ttRs = obj.getTtRs();
	ResultSet nppRs = obj.getNppRs();
	ResultSet nhomRs = obj.getNhomRs();
	ResultSet ctkmRs = obj.getCtkmRs();
	
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
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
</script>
<script language="javascript" type="text/javascript">

function clearform()
{   
	document.forms["ckParkForm"].Sdays.value="";
	document.forms["ckParkForm"].Edays.value="";
	document.forms["ckParkForm"].khId.value="";
	document.forms["ckParkForm"].spId.value="";
	document.forms["ckParkForm"].ddkdId.value="";
	document.forms["ckParkForm"].kbhId.value="";
   document.forms["ckParkForm"].submit();
}

function submitform()
{   
	document.forms['ckParkForm'].action.value='';
   document.forms["ckParkForm"].submit();
}

function xuatExcel()
{
	document.forms['ckParkForm'].action.value= 'excel';
	document.forms['ckParkForm'].submit();	
}


function search()
{
	
	document.forms['ckParkForm'].action.value= 'search';
	document.forms['ckParkForm'].submit();
	
}

jQuery(document).ready(function()
		 {
		 	$("select:not(.notuseselect2)").chosen();     
		 	
		 }); 


</script>

<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>




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

	<form name="ckParkForm" method="post" action="../../BcSuDungKhuyenMaiSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value=''>
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden"name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">
		<% obj.setNextSplittings(); %>
		<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
		
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý khuyến mãi > Báo cáo > Báo cáo tổng kết CTKM</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1"  style="width: 100% ; color:#F00 ; font-weight:bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Doanh thu bán hàng </legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >
							<TR >
									<TD class="plainlabel" width="200px">Từ ngày</TD>
									<TD class="plainlabel" width="250px" >
								 	<input type="text" name="Sdays"	class="days" value='<%=obj.getTuNgay()%>'  /></TD> 
									<TD class="plainlabel" width="200px" > Đến ngày</TD>
									<TD class="plainlabel" colspan="3">
										<input type="text" name="Edays" class="days" value='<%=obj.getDenNgay()%>'   /></td> 
						   </TR>
						   
						   
						   <TR>
									<TD class="plainlabel">Miền  </TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" style="width:250px"   onchange="submitform();" >
											<option value="" >All</option>
											<%if (vungRs != null)
													while (vungRs.next()) {
														if (vungRs.getString("pk_seq").equals(obj.getVungId()  )) {%>
											   <option value="<%=vungRs.getString("pk_seq")%>" selected><%=vungRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=vungRs.getString("pk_seq")%>"><%=vungRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									
									<TD class="plainlabel">  Kênh bán hàng </TD>
									<TD class="plainlabel" colspan="3">
										<select name="kbhId" id="kbhId" style="width:250px"  onchange="submitform();"  >
											<option value="" >All</option>
											<%if (kbhRs != null)
													while (kbhRs.next()) {
														if (kbhRs.getString("pk_Seq").equals(obj.getKbhId())) {%>
											   <option value="<%=kbhRs.getString("pk_Seq")%>" selected><%=kbhRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=kbhRs.getString("pk_Seq")%>"><%=kbhRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Địa bàn  </TD>
									<TD class="plainlabel">
										<select name="ttId" id="ttId" style="width:250px"   onchange="submitform();" >
											<option value="" >All</option>
											<%if (ttRs != null)
													while (ttRs.next()) {
														if (ttRs.getString("pk_seq").equals(obj.getTtId()  )) {%>
											   <option value="<%=ttRs.getString("pk_seq")%>" selected><%=ttRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=ttRs.getString("pk_seq")%>"><%=ttRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									
									<TD class="plainlabel"> Nhà phân phối </TD>
									<TD class="plainlabel" colspan="3">
										<select name="nppId" id="nppId" style="width:250px"  onchange="submitform();" >
											<option value="" >All</option>
											<%if (nppRs != null)
													while (nppRs.next()) {
														if (nppRs.getString("pk_seq").equals(obj.getNppId()   )) {%>
											   <option value="<%=nppRs.getString("pk_seq")%>" selected><%=nppRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=nppRs.getString("pk_seq")%>"><%=nppRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
								</TR>
						
						<TR>
							<% String[] trangthai = new  String[] {"Tổng hợp","Chi tiết"  } ;
										String[] idTrangThai = new  String[] {"0","1"} ;
									%> 
										<TD class="plainlabel">Mức lấy</TD>
									<TD class="plainlabel" >
									<select name="muclay" id="muclay">
							<% for( int j=0;j<trangthai.length;j++) { 
   												if(idTrangThai[j].equals(obj.getMuclay()) ){ %>
     											<option value='<%=idTrangThai[j]%>' selected><%=trangthai[j] %></option>
     		 								<%}else{ %>
    												<option value='<%=idTrangThai[j]%>'><%=trangthai[j] %></option>
    										<%}} %>
									</select>
									</TD>
						
						
									<TD class="plainlabel">Chương trình khuyến mại  </TD>
									<TD class="plainlabel" colspan="3">
										<select name="ctkmId" id="ctkmId" class="select2" style="width:400px;" multiple="multiple"  >
											<option value="" >All</option>
											<%if (ctkmRs != null)
													while (ctkmRs.next()) {
														if (ctkmRs.getString("pk_seq").equals(obj.getCtkmId()  )) {%>
											   <option value="<%=ctkmRs.getString("pk_seq")%>" selected>
											   <%=ctkmRs.getString("SCHEME")%>  <%=ctkmRs.getString("ten")%>
											   </option>
											   <%} else {%>
											   <option value="<%=ctkmRs.getString("pk_seq")%>"  >
											   	<%=ctkmRs.getString("SCHEME")%>  <%=ctkmRs.getString("ten")%>
											   </option>
											<%}}%>
										</select>
									</TD>
									
								
								</TR>
								
								
								
				
								
						   
					<TR>
						<TD class="plainlabel" colspan="6">
							<a class="button2" href="javascript:xuatExcel();"> 
								<img style="top: -4px;" src="../images/button.png" alt="">Tạo báo cáo</a>&nbsp;&nbsp;&nbsp;&nbsp;
					</TR> 
					
						</TABLE>
						</div>
			</div>
			</fieldset>
		</div>
	</div>
	</form>
	<%
		if(obj!=null){obj.closeDB();
		obj = null;}
		session.setAttribute("obj", null);
	%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>