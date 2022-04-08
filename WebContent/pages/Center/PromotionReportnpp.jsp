<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet programs = obj.getRsPrograms();
	
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

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">
	function seach() {
		document.forms['frm'].action.value = 'seach';
		document.forms["frm"].submit();
	}
	function submitform() {
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}

		document.forms['frm'].action.value = 'create';
		document.forms["frm"].submit();
		reset();
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");		
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}		
	};
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

	<form name="frm" method="post"
		action="../../Ppromotionreportnpp"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> <input
			type="hidden" name="userId" value='<%=obj.getuserId()%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo khuyến mãi &#62; Báo cáo  &#62; Báo cáo xuất khuyến mãi</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn  &nbsp;
					<%=obj.getuserTen()%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="2"  style="width: 100% ; color:#F00 ; font-weight:bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Báo cáo xuất khuyến mãi</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Chọn năm</TD>
									
									<TD class="plainlabel">
										<SELECT name = "year">
										<%if(obj.getYear().equals("2012")){ %>
											<option value= "2012" selected>2012</option>
										<%}else{ %>
											<option value= "2012" >2012</option>
										<%} %>
																						
										<%if(obj.getYear().equals("2011")){ %>
											<option value= "2011" selected>2011</option>
										<%}else{ %>
											<option value= "2011" >2011</option>
										<%} %>

										</SELECT>									
									</TD>
									
									<TD class="plainlabel">Chọn tháng</TD>
									<TD>
										<SELECT name = "month">
										<%if(obj.getMonth().equals("01")){ %>
											<option value = "01" selected>T1</option>
										<%}else{ %>
											<option value = "01">T1</option>
										<%} %>

										<%if(obj.getMonth().equals("02")){ %>
											<option value = "02" selected>T2</option>
										<%}else{ %>
											<option value = "02">T2</option>
										<%} %>

										<%if(obj.getMonth().equals("03")){ %>
											<option value = "03" selected>T3</option>
										<%}else{ %>
											<option value = "03">T3</option>
										<%} %>

										<%if(obj.getMonth().equals("04")){ %>
											<option value = "04" selected>T4</option>
										<%}else{ %>
											<option value = "04">T4</option>
										<%} %>

										<%if(obj.getMonth().equals("05")){ %>
											<option value = "05" selected>T5</option>
										<%}else{ %>
											<option value = "05">T5</option>
										<%} %>

										<%if(obj.getMonth().equals("06")){ %>
											<option value = "06" selected>T6</option>
										<%}else{ %>
											<option value = "06">T6</option>
										<%} %>

										<%if(obj.getMonth().equals("07")){ %>
											<option value = "07" selected>T7</option>
										<%}else{ %>
											<option value = "07">T7</option>
										<%} %>

										<%if(obj.getMonth().equals("08")){ %>
											<option value = "08" selected>T8</option>
										<%}else{ %>
											<option value = "08">T8</option>
										<%} %>
										
										<%if(obj.getMonth().equals("09")){ %>
											<option value = "09" selected>T9</option>
										<%}else{ %>
											<option value = "09">T9</option>
										<%} %>
										
										<%if(obj.getMonth().equals("10")){ %>
											<option value = "10" selected>T10</option>
										<%}else{ %>
											<option value = "10">T10</option>
										<%} %>
										
										<%if(obj.getMonth().equals("11")){ %>
											<option value = "11" selected>T11</option>
										<%}else{ %>
											<option value = "11">T11</option>
										<%} %>
										
										<%if(obj.getMonth().equals("12")){ %>
											<option value = "12" selected>T12</option>
										<%}else{ %>
											<option value = "12">T12</option>
										<%} %>
										
										

										</SELECT>
									
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel" colspan="2">Chương trình khuyến mãi</TD>
									<TD class="plainlabel" colspan="2">
										<select name="programs" style="width: 300px;">
											<option value="">All</option>
											<%
												if(programs!=null){
													while(programs.next()){
														if(obj.getPrograms().equals(programs.getString("SCHEME"))){%>
															<option value="<%=programs.getString("SCHEME")%>" selected="selected" >
																<%=programs.getString("DIENGIAI") %></option>															
														<%}else{%>
															<option value="<%=programs.getString("SCHEME")%>" >
																<%=programs.getString("DIENGIAI") %></option>	
														<%}
													}
												}
											%>
										</select>
									</TD>									
								</TR>
								<TR>
									<TD  class="plainlabel" colspan = "4" ><label>
										<%  if (obj.getUnghang().equals("1")){%>
										  	<input name="unghang" type="checkbox" value ="1" checked>
										<%} else {%>
											<input name="unghang" type="checkbox" value ="0">
										<%} %>
									    Bao gồm chương trình ứng hàng trước</label></TD>
										<TD  class="plainlabel" >&nbsp;</TD>

								
								</TR>
								<TR>
									<TD colspan="4"><A class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo </A></TD>
								</TR>
							</TABLE>
						</div>
						<hr>
						<div style="width: 100%; float: none;">
							<div style="width: 30%; float: left">
								<fieldset style="min-height: 200px">
									<legend class="legendtitle"> Hiển thị </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbShowFields">
										<tbody id="ShowFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chọn ẩn</th>
											</tr>
											<tr class="tbdarkrow">
												<td>Kênh bán hàng</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Kenh"></td>
											</tr>
											<tr class="tblightrow">
												<td>Mã chương trình</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="MaChuongTrinh"></td>
											</tr>
											<tr class="tblightrow">
												<td>Tên chương trình</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="TenChuongTrinh"></td>
											</tr>

											<tr class="tblightrow">
												<td>Mã sản phẩm</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="MaSanPham"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Tên sản phẩm</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="TenSanPham"></td>
											</tr>
											
											<tr class="tblightrow">
												<td>Số lượng lẻ</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="SoLuong(Le)"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Tổng tiền</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="SoTien"></td>
											</tr>											
										</tbody>
									</TABLE>
								</fieldset>
							</div>
							<div
								style="width: 35px; float: left; min-height: 200px; vertical-align: middle"
								align="center">
								<br> <br> <br> <img src="../images/Back30.png"
									border="0" class="imageClick" onClick="ChuyenSangPhai();"><br />
								<br> <br> <img src="../images/Next30.png" border="0"
									class="imageClick" onClick="ChuyenSangTrai();">
							</div>
							<div style="width: 30%; float: left;">
								<fieldset style="min-height: 200px;">
									<legend class="legendtitle"> Không hiển thị </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbAllFields">
										<tbody id="AllFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chọn hiện</th>
											</tr>
											<tr class="tbdarkrow">
												<td>Nhãn hàng</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="NhanHang"></td>
											</tr>
											<tr class="tblightrow">
												<td>Chủng loại</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="ChungLoai"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Tỉnh thành</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Tinh/Thanh"></td>
											</tr>
											<tr class="tblightrow">
												<td>Thị trấn</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Quan/Huyen"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Mã nhà phân phối</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="MaNhaPhanPhoi"></td>
											</tr>
											
											<tr class="tbdarkrow">
												<td>Loại chương trình</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="LoaiChuongTrinh"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Mã khách hàng</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="MaKhachHang"></td>
											</tr>
											<tr class="tblightrow">
												<td>Tên khách hàng</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="KhachHang"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Ngày</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Ngay"></td>
											</tr>
											<tr class="tblightrow">
												<td>Số lượng thùng</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="SoLuong(Thung)"></td>
											</tr>
											
										</tbody>
									</TABLE>
								</fieldset>
							</div>


						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<br /> <br /> <br /> <br />
	</form>
	<%
		if(programs!=null){
			programs.close();
		}
		if(programs!=null)
			programs.close();
		obj.DBclose();
	%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>