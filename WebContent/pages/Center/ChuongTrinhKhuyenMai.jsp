<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="geso.dms.center.beans.ctkhuyenmai.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page import="geso.dms.center.util.ChuyenNgu"%>
<%
	ICtkhuyenmaiList obj = (ICtkhuyenmaiList) session
			.getAttribute("obj");
%>
<%
	ResultSet ctkmList = (ResultSet) obj.getCtkmList();
%>
<%
	ResultSet vung = (ResultSet) obj.getvungRs();
%>
<%
	ResultSet khuvuc = (ResultSet) obj.getkhuvucRs();
%>
<%
	ResultSet npp = (ResultSet) obj.getnppRs();
%>

<%
	String userId = (String) session.getAttribute("userId");
%>
<%
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/AHF/index.jsp");
	} else {
		int[] quyen = new int[5];
		quyen = util.Getquyen("CtkhuyenmaiSvl", "", userId);
%>

<%
	obj.setNextSplittings();
%>
<%
	String nnId = (String) session.getAttribute("nnId");
%>
<%
	if (nnId == null) {
			nnId = "vi";
		}
		String url = util.getChuyenNguUrl("CtkhuyenmaiSvl", "", nnId);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>DDT - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/table.css" type="text/css">
<LINK rel="stylesheet" href="../css/pagination.css" type="text/css">
<LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>



<style type="text/css">
#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}

th {
	cursor: pointer;
}
</style>



<script language="javascript" src="../scripts/datepicker.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript"
	src="../scripts/Timepicker/jquery-ui.min.js"></script>

<script type="text/javascript" src="..scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="../scripts/jquery-latest.js"></script>
<script type="text/javascript" src="../scripts/jquery.tablesorter.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#ctKMList").tablesorter();
	});
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
		$(".button2").hover(function() {
			$(".button2 img").animate({
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
		$(".button3").hover(function() {
			$(".button3 img").animate({
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
<SCRIPT language="javascript" type="text/javascript">
	function submitform() {
		document.forms["ctkmForm"].action.value = "";
		document.forms["ctkmForm"].submit();

	}
	function newform() {
		document.forms["ctkmForm"].action.value = "Tao moi";
		document.forms["ctkmForm"].submit();
	}
	function clearform() {
		document.forms["ctkmForm"].diengiai.value = "";
		document.forms["ctkmForm"].tungay.value = "";
		document.forms["ctkmForm"].denngay.value = "";
		document.forms["ctkmForm"].trangthai.value = "";
		document.forms["ctkmForm"].khuvuc.value = "";
		document.forms["ctkmForm"].vung.value = "";
		document.forms["ctkmForm"].npp.value = "";
		submitform();
	}
	function thongbao() {
		var tt = document.forms["ctkmForm"].msg.value;
		if (tt.length > 0)
			alert(document.forms["ctkmForm"].msg.value);
		document.forms["ctkmForm"].msg.value = "";
	}

	function upload() {

		if (document.forms["ctkmForm"].filename.value == "") {
			alert("Vui l??ng ch???n file upload !");
			return;
		} else {
			// Ki???m tra ?????nh d???ng file c?? ????ng l?? xls hay kh??ng !
			var res_field = document.forms["ctkmForm"].filename.value;
			var extension = res_field.substr(res_field.lastIndexOf('.') + 1)
					.toLowerCase();
			var allowedExtensions = [ 'xls' ];
			if (res_field.length > 0) {
				if (allowedExtensions.indexOf(extension) === -1) {
					alert('Sai Format. Ch??? ???????c ph??p Upload ?????nh d???ng file excel: '
							+ allowedExtensions.join(', ') + '');
					return;
				}
			}
		}
		document.forms["ctkmForm"].setAttribute('enctype',
				"multipart/form-data", 0);
		document.forms["ctkmForm"].submit();
	}
	function xuatexcel() {
		document.forms["ctkmForm"].action.value = "excel";
		document.forms["ctkmForm"].submit();
	}
</SCRIPT>

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

	<form name="ctkmForm" method="post" action="../../CtkhuyenmaiSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value="<%=userId%>"> <input
			type="hidden" name="userTen" value="<%=userTen%>"> <input
			type="hidden" name="msg" value='<%=obj.getMessage()%>'>
		<script language="javascript" type="text/javascript">
			thongbao();
		</script>

		<input type="hidden" name="action" value="1"> <input
			type="hidden" name="crrApprSplitting"
			value="<%=obj.getCrrApprSplitting()%>"> <input
			type="hidden" name="nxtApprSplitting"
			value="<%=obj.getNxtApprSplitting()%>">

		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">
					<%=url%>
				</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Ch??o m???ng", nnId)%>
					<%=userTen%>
					&nbsp;
				</div>
			</div>

			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<%-- <fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Ti???u ch?? t??m ki???m", nnId)%></legend> --%>
					
					<FIELDSET style="padding: 5px 10px; border: 1px solid rgb(128, 203, 155); background-color:#C5E8CD">
					<p id="button" style="font-size:12px; cursor:pointer; font-family:arial; font-weight:bold; text-decoration: underline;"><%=ChuyenNgu.get("Ti???u ch?? t??m ki???m", nnId)%></p>
					
					<div style="width: 100%; float: none" align="left">
						<TABLE width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="10%" class="plainlabel"><%=ChuyenNgu.get("M??/T??n ch????ng tr??nh khuy???n m??i", nnId)%>
								</TD>
								<TD class="plainlabel"><input type="text"
									value="<%=obj.getDiengiai()%>"
									onchange="javascript:submitform()" name="diengiai" size="40"></TD>
								<TD class="plainlabel"><%=ChuyenNgu.get("V??ng", nnId)%></TD>
								<TD class="plainlabel"><SELECT name="vung"
									onChange="submitform();">
										<option value=""></option>
										<%
											try {
													if (vung != null)
														while (vung.next()) {
															if (vung.getString("pk_seq").equals(obj.getvung())) {
										%>
										<option value='<%=vung.getString("pk_seq")%>' selected><%=vung.getString("ten")%></option>
										<%
											} else {
										%>
										<option value='<%=vung.getString("pk_seq")%>'><%=vung.getString("ten")%></option>
										<%
											}
														}
												} catch (java.sql.SQLException e) {
												}
										%>

								</SELECT></TD>
							</TR>
							<TR>
								<TD class="plainlabel"><%=ChuyenNgu.get("T??? ng??y", nnId)%>
								</TD>
								</TD>
								<TD class="plainlabel"><input type="text" size="10"
									class="days" id="tungay" name="tungay"
									value="<%=obj.getTungay()%>" maxlength="10" /></TD>
								<TD class="plainlabel"><%=ChuyenNgu.get("Khu v???c", nnId)%>
								</TD>
								<TD class="plainlabel"><SELECT name="khuvuc"
									onChange="submitform();">
										<option value=""></option>
										<%
											try {
													if (khuvuc != null)
														while (khuvuc.next()) {
															if (khuvuc.getString("pk_seq").equals(
																	obj.getkhuvuc())) {
										%>
										<option value='<%=khuvuc.getString("pk_seq")%>' selected><%=khuvuc.getString("ten")%></option>
										<%
											} else {
										%>
										<option value='<%=khuvuc.getString("pk_seq")%>'><%=khuvuc.getString("ten")%></option>
										<%
											}
														}
												} catch (java.sql.SQLException e) {
												}
										%>

								</SELECT></TD>
							</TR>
							<TR>
								<TD class="plainlabel"><%=ChuyenNgu.get("?????n ng??y", nnId)%>
								</TD>
								<TD class="plainlabel"><input type="text" size="10"
									class="days" id="denngay" name="denngay"
									value="<%=obj.getDenngay()%>" maxlength="10" /></TD>

								<TD class="plainlabel"><%=ChuyenNgu.get("Nh?? ph??n ph???i", nnId)%></TD>
								<TD class="plainlabel"><SELECT name="npp"
									onChange="submitform();">
										<option value=""></option>
										<%
											try {
													if (npp != null)
														while (npp.next()) {
															if (npp.getString("pk_seq").equals(obj.getnpp())) {
										%>
										<option value='<%=npp.getString("pk_seq")%>' selected><%=npp.getString("ten")%></option>
										<%
											} else {
										%>
										<option value='<%=npp.getString("pk_seq")%>'><%=npp.getString("ten")%></option>
										<%
											}
														}
												} catch (java.sql.SQLException e) {
												}
										%>

								</SELECT></TD>
							</TR>

							<TR>
								<TD class="plainlabel">Tr???ng th??i</TD>
								<TD colspan="5" class="plainlabel"><SELECT name="trangthai"
									id="trangthai" class="legendtitle">
										<option value=""></option>
										<%
											if (obj.getTrangthai().equals("1")) {
										%>
										<option value='1' selected><%=ChuyenNgu.get("C??n hi???u l???c", nnId)%></option>
										<%
											} else {
										%>
										<option value='1'><%=ChuyenNgu.get("C??n hi???u l???c", nnId)%></option>
										<%
											}
										%>

										<%
											if (obj.getTrangthai().equals("2")) {
										%>
										<option value='2' selected><%=ChuyenNgu.get("H???t hi???u l???c", nnId)%></option>
										<%
											} else {
										%>
										<option value='2'><%=ChuyenNgu.get("H???t hi???u l???c", nnId)%></option>
										<%
											}
										%>

								</SELECT></TD>
							</TR>

							<tr>
								<td class="plainlabel" colspan="5">&nbsp;&nbsp;
								<a class="button" href="javascript:submitform()"> <img
										style="top: -4px;" src="../images/Search30.png" alt=""><%=ChuyenNgu.get("T??m ki???m", nnId)%>
								</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="button2" href="javascript:clearform()"> <img
										style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nh???p l???i", nnId)%></a>
								</td>
							</tr>

						</TABLE>
						
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<tr class="plainlabel">

								<td colspan="2"><INPUT type="file" name="filename"
									size="40" value=''></td>
							</tr>
							<tr class="plainlabel">
							<% if (quyen[0] != 0) { %>
						  		<td colspan="1" style="width:280px">&nbsp;&nbsp;
							  		<a class="button2" href="javascript:upload()"> <img style="top: -4px;"
											src="../images/button.png" alt=""><%=ChuyenNgu.get("Upload ch????ng tr??nh khuy???n m??i", nnId)%>
									</a>
								</td>
								<%} %>
								<td colspan="1">&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2"
									href="javascript:xuatexcel()"> <img style="top: -20px;"
										src="../images/button.png" alt=""><%=ChuyenNgu.get("Xu???t Excel ch????ng tr??nh khuy???n m??i",nnId)%></a>
								</td>

							</tr>

						</TABLE>
					</div>
				<!-- </fieldset> -->
			<!-- </div>
			<div align="left" style="width: 100%; float: none; clear: left"> -->
				<%-- <fieldset>
					<legend>
						<span class="legendtitle"> <%=ChuyenNgu.get("Ch????ng tr??nh khuy???n m??i", nnId)%>
						</span>&nbsp;&nbsp;&nbsp; --%>
						<% if (quyen[0] != 0) { %>
						<a class="button3" href="javascript:newform()"> <img
							style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("T???o m???i", nnId)%></a>
						<% } %>
					<!-- </legend> -->
					<div style="width: 100%; float: none" align="left">
						
						<table class="chitieutable">
						<!-- <TABLE width="100%" id="table" class="tabledetail sortable"
							border="0" cellspacing="1" cellpadding="1"> -->
							<!-- <thead> -->
								<TR class="">
									<TH class="nosort" width="12%" align="center"><%=ChuyenNgu.get("M?? ch????ng tr??nh khuy???n m??i", nnId)%></TH>
									<TH width="25%" align="center"><%=ChuyenNgu.get("Di???n gi???i", nnId)%></TH>
									<TH width="7%" align="center"><%=ChuyenNgu.get("Ng??y b???t ?????u", nnId)%></TH>
									<TH width="7%" align="center"><%=ChuyenNgu.get("Ng??y k???t th??c", nnId)%></TH>
									<!-- <TH width="7%" align="center">Ng??n s??ch</TH>
                    <TH width="9%" align="center">Lo???i CT</TH> -->
									<TH width="9%" align="center"><%=ChuyenNgu.get("V??ng", nnId)%></TH>
									<TH width="9%" align="center"><%=ChuyenNgu.get("Lo???i CT", nnId)%></TH>
									<!-- <TH width="7%" align="center">Ng??y t???o</TH> -->
									<!-- <TH width="7%" align="center">Ng?????i t???o</TH> -->
									<TH width="7%" align="center"><%=ChuyenNgu.get("Ng??y s???a", nnId)%></TH>
									<TH width="7%" align="center"><%=ChuyenNgu.get("Ng?????i s???a", nnId)%></TH>
									<TH width="9%" align="center"><%=ChuyenNgu.get("T??c v???", nnId)%></TH>
								</TR>
							<!-- </thead>
							<tbody> -->
								<%
									NumberFormat formatter = new DecimalFormat("#,###,###");
										int m = 0;
										try {
											if (ctkmList != null)

												while (ctkmList.next()) {
													if (m % 2 == 0) {
								%>
								<TR class='tbdarkrow'>
									<%
										} else {
									%>
								
								<TR class='tblightrow'>
									<%
										}

														String loaict = "";

														if (ctkmList.getString("type").equals("1")) {
															loaict = "B??nh th?????ng";
														} else if (ctkmList.getString("type").equals("2")) {
															loaict = "On top";
														} else {
															loaict = "T??ch l??y";
														}
														String diengiai = "";
														double ngansach = 0;
														ngansach = Double.parseDouble(ctkmList
																.getString("ngansach"));

														if (ctkmList.getString("diengiai") != null)
															diengiai = ctkmList.getString("scheme")
																	+ " -- "
																	+ ctkmList.getString("diengiai");
														else
															diengiai = ctkmList.getString("scheme");
									%>
									<TD align="center" onMouseover="ddrivetip('<%=diengiai%>', 150)" onMouseout="hideddrivetip()"><%=ctkmList.getString("scheme")%></TD>
									<TD align="left"><%=ctkmList.getString("diengiai")%></TD>
									<TD align="center"><%=ctkmList.getString("tungay")%></TD>
									<TD align="center"><%=ctkmList.getString("denngay")%></TD>
									<%-- <TD align="right"><%= formatter.format(ngansach)%></TD> 
                  <TD align="center"><%=loaict %></TD> --%>
									<TD align="center"><%=ctkmList.getString("vTen") == null ? ""
									: ctkmList.getString("vTen")%></TD>
									
									<TD align="center"><%=loaict%></TD>

									<%-- <TD align="center"><%= ctkmList.getString("ngaytao") %></TD> --%>
									<%-- <TD align="center"><%= ctkmList.getString("nguoitao") %></TD> --%>
									<TD align="center"><%=ctkmList.getString("ngaysua")%></TD>
									<TD align="center"><%=ctkmList.getString("nguoisua")%></TD>
									
									<TD align="center">
										<!-- COPY -->
										<% if (quyen[0] != 0){ %> 
											<A href="../../CtkhuyenmaiUpdateSvl?userId=<%=userId%>&copy=<%=ctkmList.getString("ctkmId")%>">
											<IMG src="../images/copy20.png" alt="Copy" title="Copy" border="0"></A>
										<%}%> 
										
										<!-- DELETE -->
										<% if (quyen[1] != 0 && ctkmList.getInt("soDH") <= 0 && !ctkmList.getString("ISDONGBO").equals("1") && !ctkmList.getString("ISDUYET").equals("1") && !ctkmList.getString("ISDUYET").equals("2")) { %> 
											<A href="../../CtkhuyenmaiSvl?userId=<%=userId%>&delete=<%=ctkmList.getString("ctkmId")%>">
											<img src="../images/Delete20.png" alt="X??a" title="X??a" width="20" height="20" longdesc="X??a" border=0 onclick="if(!confirm('B???n C?? Mu???n X??a Ch????ng Tr??nh Khuy???n M??i N??y?')) return false;"></A>
										<%}%> 
										
										<!-- UPDATE -->
										<% if (quyen[2] != 0 && !ctkmList.getString("ISDONGBO").equals("1") && !ctkmList.getString("ISDUYET").equals("1") && !ctkmList.getString("ISDUYET").equals("2")) { %> 
											<A href="../../CtkhuyenmaiUpdateSvl?userId=<%=userId%>&update=<%=ctkmList.getString("ctkmId")%>">
											<IMG src="../images/Edit20.png" alt="C???p nh???t" title="C???p nh???t" border="0"></A> 
										<%}%> 
										
										<!-- APPROVE -->
										<% if (quyen[4] != 0 && ctkmList.getInt("isDuyet") <= 0) { %>
											<A href="../../CtkhuyenmaiSvl?userId=<%=userId%>&duyet=<%=ctkmList.getString("ctkmId")%>">
											<IMG src="../images/Chot.png" alt="Duy???t" title="Duy???t" width="20" height="20" border="0"></A> 
										<%}%>
										 
										 <!-- DISAPPROVE -->
							 			<%-- <% if (quyen[5] != 0 && ctkmList.getInt("isDuyet") >= 1 && ctkmList.getInt("isPB") <= 0) { %>
											<A href="../../CtkhuyenmaiSvl?userId=<%=userId%>&boDuyet=<%=ctkmList.getString("ctkmId")%>">
											<IMG src="../images/unChot.png" alt="B??? duy???t" title="B??? duy???t" width="20" height="20" border="0"></A> 
										<%}%>  --%>
 
 										 <!-- VIEW -->
							 			<% if (quyen[3] != 0){%>
											<A href="../../CtkhuyenmaiUpdateSvl?userId=<%=userId%>&display=<%=ctkmList.getString("ctkmId")%>">
											<IMG src="../images/Display20.png" alt="Xem" title="Xem" border="0"></A> 
										<%}%>
										
										<!-- MAIL -->
										<% if (quyen[4] != 0 && ctkmList.getInt("isDuyet") == 1) { %>
										<%if(ctkmList.getInt("ismail") == 1) { %>
											<A style="  cursor: not-allowed; opacity: 0.5; " href="../../CtkhuyenmaiSvl?userId=<%=userId%>&mail=<%=ctkmList.getString("ctkmId")%>"> 
											<IMG src="../images/mail.png" alt="DaGuiMail" title="???? g???i mail" width="20" height="20" border="0">
											</A>
										<%} else {%>
											<A href="../../CtkhuyenmaiSvl?userId=<%=userId%>&mail=<%=ctkmList.getString("ctkmId")%>"> 
											<IMG src="../images/mail.png" alt="GuiMail" title="G???i mail" width="20" height="20" border="0">
											</A>
											<%} %>
											 
										<%}%>
									</TD>
								</TR>
								<%
									m++;
												}
										} catch (java.sql.SQLException e) {
										}
								%>
							<!-- </tbody> -->
							<%-- <tfoot>
								<tr class="tbfooter">
									<TD align="center" valign="middle" colspan="13"
										class="tbfooter">
										<%
											if (obj.getNxtApprSplitting() > 1) {
										%> <img alt="Trang Dau"
										src="../images/first.gif" style="cursor: pointer;"
										onclick="View('ctkmForm', 1, 'view')"> &nbsp; <%
 	} else {
 %>
										<img alt="Trang Dau" src="../images/first.gif"> &nbsp;
										<%
											}
										%> <%
 	if (obj.getNxtApprSplitting() > 1) {
 %> <img
										alt="Trang Truoc" src="../images/prev.gif"
										style="cursor: pointer;"
										onclick="View('ctkmForm', eval(ctkmForm.nxtApprSplitting.value) -1, 'view')">
										&nbsp; <%
 	} else {
 %> <img alt="Trang Truoc"
										src="../images/prev_d.gif"> &nbsp; <%
 	}
 %> <%
 	int[] listPage = obj.getNextSplittings();
 		for (int i = 0; i < listPage.length; i++) {
 %> <%
 	System.out.println("Current page:"
 					+ obj.getNxtApprSplitting());
 			System.out.println("List page:" + listPage[i]);

 			if (listPage[i] == obj.getNxtApprSplitting()) {
 %> <a
										style="color: white;"><%=listPage[i]%>/ <%=obj.getTheLastSplitting()%></a>
										<%
											} else {
										%> <a
										href="javascript:View('ctkmForm', <%=listPage[i]%>, 'view')"><%=listPage[i]%></a>
										<%
											}
										%> <input type="hidden" name="list"
										value="<%=listPage[i]%>" /> &nbsp; <%
 	}
 %> <%
 	if (obj.getNxtApprSplitting() < obj.getTheLastSplitting()) {
 %>
										&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
										style="cursor: pointer;"
										onclick="View('ctkmForm', eval(ctkmForm.nxtApprSplitting.value) +1, 'view')">
										&nbsp; <%
 	} else {
 %> &nbsp; <img alt="Trang Tiep"
										src="../images/next_d.gif"> &nbsp; <%
 	}
 %> <%
 	if (obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {
 %>
										<img alt="Trang Cuoi" src="../images/last.gif"> &nbsp;
										<%
											} else {
										%> <img alt="Trang Cuoi" src="../images/last.gif"
										style="cursor: pointer;"
										onclick="View('ctkmForm', -1, 'view')"> &nbsp; <%
 	}
 %>
									</TD>
								</tr>
							</tfoot> --%>
							
							<TR class="tbfooter" style="font-weight:normal;">
							 <TD align="center" valign="middle" colspan="11" >
							 <ul class="pagination">
							 <li>
							 	<%if(obj.getNxtApprSplitting() >1) {%>
									<a href="javascript:View('ctkmForm', 1, 'view')">&lt;&lt;</a>
								<%}else {%> <a href="#">&lt;&lt;</a> <%} %>
							</li>
							<li>
								<% if(obj.getNxtApprSplitting() > 1){ %>
									<a href="javascript:View('ctkmForm', eval(ctkmForm.nxtApprSplitting.value) -1, 'view')">&lt;</a>
								<%}else{ %> <a href="#">&lt;</a> <%} %>
							</li>	
								
								<% int[] listPage = obj.getNextSplittings();
								for(int j = 0; j < listPage.length; j++){  								
								//System.out.println("Current page:" + obj.getNxtApprSplitting());
								//System.out.println("List page:" + listPage[j]); %>
								
								<li>
								<% if(listPage[j] == obj.getNxtApprSplitting()){ %>
									<a style="color:red; font-weight: bold;" href="#"><%= listPage[j] %>/<%=obj.getTheLastSplitting() %></a>
								<%}else{ %>
									<a href="javascript:View('ctkmForm', <%= listPage[j] %>, 'view')"><%= listPage[j] %>
								<%} %>
									<input type="hidden" name="list" value="<%= listPage[j] %>" />
									</a>
								<%} %>
								</li>
								
								<li>
								<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
									<a href="javascript:View('ctkmForm', eval(ctkmForm.nxtApprSplitting.value) +1, 'view')">&gt;</a>
								<%}else{ %> <a href="#">&gt;</a> <%} %>
								</li>
								<li>
								<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%> <a href="#">&gt;&gt;</a>
						   		<%}else{ %> <a href="javascript:View('ctkmForm', -1, 'view')">&gt;&gt;</a> <%} %>
						   		</li>
						   		</ul>
							</TD>
						    </TR>
						</TABLE>
					</div>
				</fieldset>
			</div>
		</div>

	</form>
	<!-- <script type="text/javascript" src="../scripts/script-table-sorter.js"></script>
	<script type="text/javascript">
		var sorter = new TINY.table.sorter("sorter");
		sorter.head = "head";
		sorter.asc = "asc";
		//sorter.desc = "desc";
		sorter.even = "tblightrow";
		sorter.odd = "tbdarkrow";
		sorter.evensel = "evenselected";
		sorter.oddsel = "oddselected";
		sorter.paginate = true;
		sorter.currentid = "currentpage";
		sorter.limitid = "pagelimit";
		sorter.init("table",3);
	</script>  -->
</body>
<!-- <script type='text/javascript' src='../scripts/loadingv2.js'></script> -->
</html>


<%
	if (ctkmList != null) {
			ctkmList.close();
			ctkmList = null;
		}
		obj.DBclose();
		obj = null;
		session.setAttribute("obj", null);

	}
%>