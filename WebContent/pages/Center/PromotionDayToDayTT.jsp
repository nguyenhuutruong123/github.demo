<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page  import = "geso.dms.center.util.*" %>
<%@ page import="java.sql.Types"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");		
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet kenh = obj.getkenh();
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet npp = obj.getnpp();	
	ResultSet programs = obj.getRsPrograms();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("PromotionDayToDayTTSvl","",userId);
		
		System.out.println(quyen[0]);
		System.out.println(quyen[1]);
		System.out.println(quyen[2]);
		System.out.println(quyen[3]);	
		System.out.println(quyen[4]);
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
		document.forms['frm'].action.value = "search";
		document.forms["frm"].submit();
	}
	function submitform() {
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}

		document.forms['frm'].action.value = "Taomoi";
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

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
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

	<form name="frm" method="post"
		action="../../PromotionDayToDayTTSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> <input
			type="hidden" name="userId" value='<%=obj.getuserId()%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý khuyến mãi &#62; Báo cáo &#62; Trả khuyến mãi ngày </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=obj.getuserTen()%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle">Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="2"  style="width: 100%;text-align: left; color:#F00 ; font-weight:bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left;font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Báo cáo xuất khuyến mãi</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR id="TheoNgay">
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel">
										<input type="text" name="tungay" class="days" value='<%=obj.gettungay()%>' /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<td>
										<input type="text" name="denngay" class="days" value='<%=obj.getdenngay()%>' /></td>
								</TR>
								<TR>
									<TD class="plainlabel">Chương trình khuyến mãi</TD>
									<TD class="plainlabel">
										<select name="programs">
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
									<TD class="plainlabel">Kênh bán hàng</TD>
									<TD class="plainlabel">
										<select name="kenhId" id="kenhId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (kenh != null)
													while (kenh.next()) {
														if (kenh.getString("pk_seq").equals(obj.getkenhId())) {%>
														<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("diengiai")%></option>
											<%} else { %>
												<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("diengiai")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Nhà phân phối</TD>
									<TD class="plainlabel"><select name="nppId" id="nppId" >
											<option value="" selected>All</option>
											<%
												if (npp != null)
													while (npp.next()) {
														if (npp.getString("pk_seq").equals(obj.getnppId())) {
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
									<TD class="plainlabel">Vùng/Miền</TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();">
											<option value="" selected>All</option>
											<%
												if (vung != null)
													while (vung.next()) {
														if (vung.getString("pk_seq").equals(obj.getvungId())) {
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
										</select>
										</TD>
									<TD class="plainlabel">Khu vực</TD>
									<TD class="plainlabel">
									<select name="khuvucId" id="khuvucId" onchange="seach();">
											<option value="" selected>All</option>
											<%
												if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {
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
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo </a></td>
								</TR>
							</TABLE>
						</div>
						<hr>
						<div style="width: 100%; float: none; display: none">
							<div style="width: 30%; float: left">
								<fieldset style="min-height: 200px">
									<legend class="legendtitle"> Dữ liệu hiển thị </legend>
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
													type="checkbox" value="KenhBanHang"></td>
											</tr>
											<tr class="tblightrow">
												<td>Chi nhánh</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="ChiNhanh"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Khu vực</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="KhuVuc"></td>
											</tr>
											<tr class="tblightrow">
												<td>Nhà phân phối</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="NhaPhanPhoi"></td>
											</tr>	
											<tr class="tbdarkrow">
												<td>Mã chương trình</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="MaChuongTrinh"></td>
											</tr>

											<tr class="tblightrow">
												<td>Diễn giải</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="ChuongTrinhKhuyenMai"></td>
											</tr>

											<tr class="tbdarkrow">
												<td>Mã sản phẩm</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="MaSanPham"></td>
											</tr>
											
											<tr class="tblightrow">
												<td>Tên sản phẩm </td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="TenSanPham"></td>
											</tr>

											<tr class="tbdarkrow">
												<td>Số lượng lẻ</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="SoLuong(Le)"></td>
											</tr>
											<tr class="tblightrow">
												<td>Số tiền</td>
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
									<legend class="legendtitle"> Dữ liệu ẩn </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbAllFields">
										<tbody id="AllFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chọn hiển thị</th>
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
												<td>Mã Nhà Phân Phối</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="MaNhaPhanPhoi"></td>
											</tr>
											<tr class="tblightrow">
												<td>Nhân viên bán hàng</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="DaiDienKinhDoanh"></td>
											</tr>

											<tr class="tbdarkrow">
												<td>Tỉnh/Thành</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Tinh/Thanh"></td>
											</tr>
											<tr class="tblightrow">
												<td>Quận/Huyện</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Quan/Huyen"></td>
											</tr>

											<tr class="tbdarkrow">
												<td>Mã khách hàng</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="MaKhachHang"></td>
											</tr>
											
											
											<tr class="tblightrow">
												<td>Tên khách hàng</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="TenKhachHang"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Ngày</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Ngay"></td>
											</tr>		

											<tr class="tblightrow">
												<td>Loại chương trình</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="LoaiChuongTrinh"></td>
											</tr>
																						
											<tr class="tbdarkrow">
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
	if(kenh !=null){ kenh.close(); kenh = null; }
	if(vung !=null){ vung.close(); vung = null; }	
	if(khuvuc !=null){ khuvuc.close(); khuvuc = null; }
	if(npp !=null){ npp.close(); npp = null; }
	if(programs!=null){ programs.close(); programs = null; }
	if(obj!=null){	
		obj.DBclose();
		obj = null;
	}
	session.setAttribute("obj", null);
%>

</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
<%} %>
</html>