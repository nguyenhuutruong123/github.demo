<%@page import="com.cete.dynamicpdf.ob"%>
<%@page import="geso.dms.center.beans.baocaokehoach.IBCKeHoach"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	/* String sum = (String) session.getAttribute("sum"); */

	/* String groupCustomer = (String)session.getAttribute("groupCustomer");
	String gorupSKU = (String)session.getAttribute("gorupSKU");
	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet kenh = obj.getkenh();
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet npp = obj.getnpp();
	ResultSet dvkd = obj.getdvkd();
	ResultSet nhanhang = obj.getnhanhang();
	ResultSet chungloai = obj.getchungloai();
	ResultSet dvdl = obj.getdvdl();
	ResultSet sanpham = obj.getsanpham();
	ResultSet gsbh = obj.getgsbh(); */

	IBCKeHoach obj = (IBCKeHoach) session.getAttribute("obj");
	ResultSet npp = obj.getNpp();
	ResultSet vung = obj.getVung();
	ResultSet khuvuc = obj.getKhuvuc();
	ResultSet gsbh = obj.getGiamsatbanhang();
	Utility util = (Utility) session.getAttribute("util");
	/* int[] quyen = new  int[6];
	quyen = util.Getquyen("DailyPrimarySales","", userId); */
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("BcKeHoachSvl","",nnId);	
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

<!-- <script type="text/javascript" src="../scripts/report-js/action-report.js"></script> -->
<script language="javascript" type="text/javascript">
	var myVar;

	function submitform() {
		//document.getElementById("btnSave1").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		document.forms['frm'].action.value = 'taobaocao';

		//AJAX CHECK REPORT
		//myVar = setTimeout(CheckFinishReport, 100);
		document.getElementById("errors").value  = "Xuất thông báo thành công!";
		// setTimeout(CheckFinishReport, 100);
		document.forms["frm"].submit();
	}

	function CheckFinishReport() {
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		xmlhttp.onreadystatechange = function() {
			//alert('Text xxxx: ' + xmlhttp.responseText);

			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				if (xmlhttp.responseText == 'OK') {
					//alert('Text: ' + xmlhttp.responseText);
					//clearTimeout(myVar);

					document.getElementById("btnSave1").innerHTML = '<a class="button" href="javascript:submitform();"> <img style="top: -4px;" src="../images/button.png" alt="">Đơn đặt hàng</a>';
				}
			}
		}

		xmlhttp.open("GET", "../../DailyPrimarySales?action=checkReportStatus",
				true);
		xmlhttp.send();

	}

	function TheoDoiDDH() {
		//document.getElementById("btnSave2").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		document.forms['frm'].action.value = 'TheoDoiDDH';
		document.forms["frm"].submit();
	}

	function HangNoKho() {
		document.forms['frm'].action.value = 'HangNoKho';
		document.forms["frm"].submit();
	}

	function HoaDonErp() {
		//document.getElementById("btnSave3").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		document.forms['frm'].action.value = 'HoaDonErp';
		document.forms["frm"].submit();
	}

	function toPdf() {
		document.forms['frm'].action.value = 'toPdf';
		document.forms["frm"].submit();
	}

	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}

	function AnThang() {
		document.getElementById("XemNgay").style.display = "";
		document.getElementById("XemThang").style.display = "none";
	}
	function HienThang() {
		document.getElementById("XemThang").style.display = "";
		document.getElementById("XemNgay").style.display = "none";
	}
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function() {
		$("select:not(.notuseselect2)").select2({
			width : 'resolve'
		});
	});
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

	<form name="frm" method="post" action="../../BcKeHoachSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="alert" value=<%=obj.getMsg()%>> <input
			type="hidden" name="action" value='1'> <input type="hidden"
			name="view" value='TT'> <input type="hidden" name="userId"
			value='<%=obj.getUserId()%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=" "+url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng bạn",nnId) %>
					<%=obj.getUserTen()%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> <%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %></legend>
					<textarea id="errors" name="errors" rows="1"
						style="width: 100%; color: #F00; font-weight: bold; text-align: center"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Báo Cáo Kế Hoạch",nnId) %> </legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">

								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Chọn Tháng",nnId) %></TD>
									<TD class="plainlabel">
										<%-- <input type="text" name="Sdays"	class="days" value='<%=obj.getFromDay()%>' /> --%>
										<select name="month">
											<%
												for (int i = 1; i <= 12; i++) {
													if ((i + "").equals(obj.getMonth())) {
											%>
											<option value=<%=i%> selected="selected"><%="Tháng " + i%></option>
											<%
												} else {
											%>
											<option value=<%=i%>><%="Tháng " + i%></option>
											<%
												}
												}
											%>

									</select>
									</TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Chọn năm",nnId) %></TD>
									<td>
										<%-- <input type="text" name="Edays" class="days" value='<%=obj.getToDay()%>' /> --%>
										<select name="year">
											<%
												for (int i = 2000; i <= 2100; i++) {
													if ((i + "").equals(obj.getYear())) {
											%>
											<option value=<%=i%> selected="selected"><%=i%></option>
											<%
												} else {
											%>
											<option value=<%=i%>><%=i%></option>
											<%
												}
												}
											%>
									</select>
									</td>

								</TR>

								<TR>

									<TD class="plainlabel"><%=ChuyenNgu.get("Giám sát bán hàng",nnId) %></TD>
									<TD class="plainlabel"><select name="gsbhId" id="gsbhId"
										style="width: 250px">
											<option>All</option>
											<%
												if (gsbh != null)
													while (gsbh.next()) {
														if (gsbh.getString("pk_seq").equals(obj.getGsbanhangId())) {
											%>
											<option value="<%=gsbh.getString("pk_seq")%>" selected>
												<%=gsbh.getString("ten")%></option>
											<%
												} else {
											%>
											<option value="<%=gsbh.getString("pk_seq")%>"><%=gsbh.getString("ten")%></option>
											<%
												}
													}
											%>
									</select></td>
									<TD class="plainlabel"><%=ChuyenNgu.get("Khu Vực",nnId) %></TD>
									<TD class="plainlabel"><select name="khuvucId"
										id="khuvucId" style="width: 250px">
											<option>All</option>
											<%
												if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("pk_seq").equals(obj.getKhuvucId())) {
											%>
											<option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
											<%
												} else {
											%>
											<option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
											<%
												}
													}
											%>
									</select></TD>
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Vùng",nnId) %></TD>
									<TD class="plainlabel"><select name="vungId" id="vungId"
										style="width: 250px">
											<option>All</option>
											<%
												if (vung != null)
													while (vung.next()) {
														if (vung.getString("pk_seq").equals(obj.getVungId())) {
											%>
											<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
											<%
												} else {
											%>
											<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
											<%
												}
													}
											%>
									</select></TD>

								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhà Phân Phối",nnId) %></TD>
									<TD class="plainlabel"><select name="nppId" id="nppId"
										style="width: 250px">
											<option>All</option>
											<%
												if (npp != null)
													while (npp.next()) {
														if (npp.getString("pk_seq").equals(obj.getNppId())) {
											%>
											<option value="<%=npp.getString("pk_seq")%>" selected><%=npp.getString("ten")%></option>
											<%
												} else {
											%>
											<option value="<%=npp.getString("pk_seq")%>"><%=npp.getString("ten")%></option>
											<%
												}
													}
											%>
									</select></TD>


								</TR>

								<TR>
									<td class="plainlabel" colspan='3'>
										<div id="btnSave1">
											<a class="button" href="javascript:submitform();"> <img
												style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Tạo báo cáo",nnId) %>
											</a>
										</div>
									</TD>
								</TR>
							</TABLE>
						</div>

					</div>
			</div>
		</div>
		<br /> <br /> <br /> <br />
	</form>
	<%
		if (vung != null) {
			vung.close();
			vung = null;
		}
		if (khuvuc != null) {
			khuvuc.close();
			khuvuc = null;
		}
		if (npp != null) {
			npp.close();
			npp = null;
		}
		if (gsbh != null) {
			gsbh.close();
			gsbh = null;
		}

		if (obj != null) {
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj", null);
	%>
</body>
<!-- <script type='text/javascript' src='../scripts/loadingv2.js'></script> -->
</html>