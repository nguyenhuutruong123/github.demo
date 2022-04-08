
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="geso.dms.distributor.beans.khachhang.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/AHF/index.jsp");
	} else {
	IKhachhangList obj = (IKhachhangList) session.getAttribute("obj");
		String view = obj.getView();
 //List<IKhachhang> khlist = (List<IKhachhang>)obj.getKhList(); 
	ResultSet khlist = (ResultSet) obj.getKhList();
	ResultSet hch = (ResultSet) obj.getHangcuahang();
	ResultSet kbh = (ResultSet) obj.getKenhbanhang();
	ResultSet vtch = (ResultSet) obj.getVitricuahang();
	ResultSet lch = (ResultSet) obj.getLoaicuahang();
	ResultSet npp = (ResultSet) obj.getNhapp();
	ResultSet vung = obj.getVung();
	ResultSet kenh = obj.getKenhbanhang();
	ResultSet khuvuc = obj.getKhuvuc();
	ResultSet gsbh = obj.getGsbh();
	ResultSet ddkd = obj.getDdkd();
	ResultSet tinhthanh = obj.getTinhthanh();
	ResultSet codeRouteRs = obj.getCoderouteRs();
	codeRouteRs = null;
	String nnId = (String) session.getAttribute("nnId");
	if (nnId == null) {
		nnId = "vi";
	}
	int[] quyen = new  int[6];	
	String url = "";
	
	if(obj.getView().trim().length() > 0 )
	{
		quyen = util.Getquyen("KhachhangSvl","&view="+obj.getView(),userId);
		 url = util.getChuyenNguUrl("KhachhangSvl", "&view="+obj.getView(), nnId);	
	}
	else
	{
		quyen = util.Getquyen("KhachhangSvl","",userId);
		 url = util.getChuyenNguUrl("KhachhangSvl", "", nnId);	
	}
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function() { 
		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
	});
</script>
<SCRIPT language="javascript" type="text/javascript">
	function fn_checkAll() {
		var checkAll = document.getElementById("checkAll");
		var checkValue = document.getElementsByName("checkValue");
		if (checkValue != null) {
			for (var i = 0; i < checkValue.length; i++) {
				if (checkAll.checked == true)
					checkValue[i].checked = true;
				else
					checkValue[i].checked = false;
			}
		}
	}
	
	function showMap(lat,lng){
	   var url = "https://maps.google.com/?q=" + lat + "," + lng;
	   window.open(url);
	}
	
	function getMaximumWidth() {
		var x = document.getElementsByClassName("fixedWidth");
		for (var i = 0; i < x.length; i++) {
			x[i].style.width = window.screen.availWidth*1.2;
		}
	}
	
	function clearform() {
		document.khForm.khTen.value = "";
		document.khForm.sodt.value = "";
		document.khForm.mathamchieu.value = "";

		document.khForm.vungId.selectedIndex = 0;
		document.khForm.kvId.selectedIndex = 0;
		document.khForm.isDuyet.value = "";
		document.khForm.nppSearch.value = "";
		document.khForm.isToado.value = "";
		document.khForm.gsbhId.selectedIndex = 0;
		document.khForm.tinhthanhId.selectedIndex = 0;
		document.khForm.ddkdId.selectedIndex = 0;
		document.khForm.trangthai.value = "";
		
		//document.khForm.hchTen.selectedIndex = 0;
		//document.khForm.kbhTen.selectedIndex = 0;
		//document.khForm.vtchTen.selectedIndex = 0;
		//document.khForm.lchTen.selectedIndex = 0;
		submitform();
	}

	function xuatExcel() {
		document.forms['khForm'].action.value = 'excel';
		document.forms['khForm'].submit();

	}
	
	function duyetmulti() {
		document.forms['khForm'].action.value = 'duyetmulti';
		document.forms['khForm'].submit();

	}
	
	function xoatoadomulti() {
		document.forms['khForm'].action.value = 'xoatoadomulti';
		document.forms['khForm'].submit();

	}
	function ngungmulti() {
		document.forms['khForm'].action.value = 'ngungmulti';
		document.forms['khForm'].submit();

	}
	
	function submitform() {
		document.forms['khForm'].action.value = 'search';
		document.forms['khForm'].submit();
	}

	function newform() {
		document.forms['khForm'].action.value = 'new';
		document.forms['khForm'].submit();
	}

	function thongbao() {
		var tt = document.forms['khForm'].msg.value;
		if (tt.length > 0)
			alert(document.forms['khForm'].msg.value);

		document.forms['khForm'].msg.value = '';
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

	<form name="khForm" method="post" action="../../KhachhangSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value="<%=userId%>"> 
		<input type="hidden" name="nppId" value="<%=obj.getNppId()==null||obj.getNppId().length()<=0?"":obj.getNppId()%>"> 
		<input type="hidden" name="msg" value='<%=obj.getMsg()%>'> 
		<input type="hidden" name="action" value="1"> 
		<input type="hidden" name="currentPage" value="<%=obj.getCurrentPage()%>"> 
		<input type="hidden" name="view" value="<%=obj.getView()%>">
		<script language="javascript" type="text/javascript">
			 thongbao();
		</script>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;<%=" " + url%></TD>
										<TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng", nnId)%>
											<%=obj.getNppTen() == null
						|| obj.getNppTen().length() <= 0 ? userTen : ""%> &nbsp;</TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD>
								<FIELDSET>
									<LEGEND class="legendtitle">
										&nbsp;<%=ChuyenNgu.get("Tiêu chí tìm kiếm", nnId)%>&nbsp;
									</LEGEND>
									<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
										<TR>
										<TD class="plainlabel" style="width:9%"><%=ChuyenNgu.get("Miền", nnId)%></TD>
											<TD class="plainlabel" style="width:9%">
												<SELECT name="vungId" onChange="submitform();">
													<option value=""></option>
													<%
														try {
																while (vung.next()) {
																	if (vung.getString("pk_seq").equals(obj.getVungId())) {
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
																e.printStackTrace();
															}
													%>
												</SELECT>
											</TD>
											
											<TD class="plainlabel" style="width:9%"><%=ChuyenNgu.get("Kênh bán hàng", nnId)%></TD>
											<TD class="plainlabel" style="width:9%">
												<SELECT name="kbhId" id="kbhId" onChange="submitform();">
													<option value=""></option>
													<% try { System.out.println("kbhId "+ obj.getKbhId()); while (kenh.next()) { if (kenh.getString("kbhId").equals(obj.getKbhId())) { %>
													<option value='<%=kenh.getString("kbhId")%>' selected><%=kenh.getString("kbhTen")%></option>
													<% } else { %>
													<option value='<%=kenh.getString("kbhId")%>'><%=kenh.getString("kbhTen")%></option>
													<% } } } catch (java.sql.SQLException e) { e.printStackTrace(); }
													%>
												</SELECT>
											</TD>
											<TD class="plainlabel" style="width:64%"></TD>
										</TR>
										
										<TR>
										<TD class="plainlabel" style="width:9%"><%=ChuyenNgu.get("Khu vực", nnId)%></TD>
											<TD class="plainlabel" style="width:9%">
												<SELECT name="kvId" onChange="submitform();">
													<option value=""></option>
													<%
														try {
																while (khuvuc.next()) {
																	if (khuvuc.getString("pk_seq").equals(obj.getKvId())) {
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
																e.printStackTrace();
															}
													%>
												</SELECT>
											</TD>
											
											<TD class="plainlabel" style="width:9%"><%=ChuyenNgu.get("Mã/tên khách hàng", nnId)%></TD>
											<TD class="plainlabel" style="width:9%">
												<INPUT name="khTen" type="text" value="<%=obj.getTen()%>" size="40" onChange="submitform();">
											</TD>
											<TD class="plainlabel" style="width:64%"></TD>
										</TR>
										<TR>
										<TD class="plainlabel" style="width:9%"><%=ChuyenNgu.get("Mã KH tham chiếu", nnId)%></TD>
											<TD class="plainlabel" style="width:9%"  colspan="3">
												<INPUT name="mathamchieu" id="mathamchieu" type="text" value="<%=obj.getMathamchieu()%>" size="40" onChange="submitform();">
											</TD>
											<TD class="plainlabel" style="width:64%"></TD>
										</TR>
										<TR>
										<TD class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối", nnId)%></TD>
											<TD class="plainlabel" colspan="1">
											<SELECT name="nppSearch" id="nppSearch" onChange="submitform();">
												<option value=""></option>
													<%
														try {
																while (npp.next()) {
																	if (obj.getNpp_search() != null
																			&& npp.getString("npp1Id").equals(
																					obj.getNpp_search())) {
													%>
													<option value='<%=npp.getString("npp1Id")%>' selected><%=npp.getString("npp1Ten")%></option>
													<%
														} else {
													%>
													<option value='<%=npp.getString("npp1Id")%>'><%=npp.getString("npp1Ten")%></option>
													<%
														}
																}
															} catch (java.sql.SQLException e) {
																e.printStackTrace();
															}
													%>
												</SELECT>
											</TD>
											
											<TD class="plainlabel" style="width:9%"><%=ChuyenNgu.get("Duyệt", nnId)%></TD>
											<TD class="plainlabel" style="width:9%">
												<SELECT name="isDuyet" onChange="submitform();">
												<% String isDuyet = obj.getIsDuyet()==null?"":obj.getIsDuyet(); %>
													<option value="">All</option>
													<% if (isDuyet.equals("1")) {%>
													<option value = "1" selected>Duyệt</option>
													<option value = "0" >Chưa</option>
													<% } else if (isDuyet.equals("0")) {%>
													<option value = "1" >Duyệt</option>
													<option value = "0" selected>Chưa</option>
													<% } else { %>
													<option value = "1" >Duyệt</option>
													<option value = "0" >Chưa</option>
													<% } %>													
												</SELECT>
											</TD>
											<TD class="plainlabel" style="width:64%"></TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("SS", nnId)%></TD>
											<TD class="plainlabel" colspan="1"><SELECT
												name="gsbhId" id="gsbhId" onChange="submitform();">
													<option value=""></option>
													<%
														try {
																while (gsbh.next()) {
																	if (obj.getGsbhId() != null 
																			&& gsbh.getString("pk_seq").equals(obj.getGsbhId())) {
													%>
													<option value='<%=gsbh.getString("pk_seq")%>' selected><%=gsbh.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=gsbh.getString("pk_seq")%>'><%=gsbh.getString("ten")%></option>
													<%
														}
																}
															} catch (java.sql.SQLException e) {
																e.printStackTrace();
															}
													%>
												</SELECT>
											</TD>	
											
											<TD class="plainlabel" style="width:9%"><%=ChuyenNgu.get("Có toạ độ", nnId)%></TD>
											<TD class="plainlabel" style="width:9%">
												<SELECT name="isToado" onChange="submitform();">
												<% String isToado = obj.getIsToado()==null?"":obj.getIsToado(); %>
													<option value="">All</option>
													<% if (isToado.equals("1")) {%>
													<option value = "1" selected>Có</option>
													<option value = "0" >Không</option>
													<% } else if (isToado.equals("0")) {%>
													<option value = "1" >Có</option>
													<option value = "0" selected>Không</option>
													<% } else { %>
													<option value = "1" >Có</option>
													<option value = "0" >Không</option>
													<% } %>													
												</SELECT>
											</TD>
											<TD class="plainlabel"></TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("NVBH", nnId)%></TD>
											<TD class="plainlabel" colspan="1"><SELECT
												name="ddkdId" id="ddkdId" onChange="submitform();">
													<option value=""></option>
													<%
														try {
																while (ddkd.next()) {
																	if (obj.getDdkdId() != null 
																			&& ddkd.getString("pk_seq").equals(obj.getDdkdId())) {
													%>
													<option value='<%=ddkd.getString("pk_seq")%>' selected><%=ddkd.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=ddkd.getString("pk_seq")%>'><%=ddkd.getString("ten")%></option>
													<%
														}
																}
															} catch (java.sql.SQLException e) {
																e.printStackTrace();
															}
													%>
												</SELECT>
											</TD>
											
											<TD class="plainlabel"><%=ChuyenNgu.get("Tỉnh thành", nnId)%></TD>
											<TD class="plainlabel" colspan="1"><SELECT
												name="tinhthanhId" id="tinhthanhId" onChange="submitform();">
													<option value=""></option>
													<%
														try {
																while (tinhthanh.next()) {
																	if (obj.getTtId() != null 
																			&& tinhthanh.getString("pk_seq").equals(obj.getTinhthanhId())) {
													%>
													<option value='<%=tinhthanh.getString("pk_seq")%>' selected><%=tinhthanh.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=tinhthanh.getString("pk_seq")%>'><%=tinhthanh.getString("ten")%></option>
													<%
														}
																}
															} catch (java.sql.SQLException e) {
																e.printStackTrace();
															}
													%>
												</SELECT>
											</TD>											
											<TD class="plainlabel"></TD>
										</TR>
										
										<TR>
											<TD class="plainlabel" style="width:9%"><%=ChuyenNgu.get("Số điện thoại", nnId)%></TD>
											<TD class="plainlabel" style="width:9%">
												<INPUT name="sodt" type="text" value="<%=obj.getSodienthoai()%>" size="40" onChange="submitform();">
											</TD>
											
											<TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái", nnId)%></TD>										
											<TD class="plainlabel" style="width:9%">
												<SELECT name="trangthai" onChange="submitform();">
												<% String trangthai = obj.getTrangthai()==null?"":obj.getTrangthai(); %>
													<option value="">All</option>
													<% if (trangthai.equals("1")) {%>
													<option value = "1" selected>Hoạt động</option>
													<option value = "0" >Ngưng hoạt động</option>
													<% } else if (trangthai.equals("0")) {%>
													<option value = "1" >Hoạt động</option>
													<option value = "0" selected>Ngưng hoạt động</option>
													<% } else { %>
													<option value = "1" >Hoạt động</option>
													<option value = "0" >Ngưng hoạt động</option>
													<% } %>													
												</SELECT>
											</TD>				
											<TD class="plainlabel"></TD>
										</TR>
										
										
										<TR style="display:none;">
											<TD class="plainlabel"><%=ChuyenNgu.get("Loại khách hàng", nnId)%></TD>
											<TD class="plainlabel" colspan=1><SELECT name="lchTen"
												onChange="submitform();">
													<option value=""></option>
													<%
														try {
																while (lch.next()) {
																	if (lch.getString("lchId").equals(obj.getLchId())) {
													%>
													<option value='<%=lch.getString("lchId")%>' selected><%=lch.getString("lchTen")%></option>
													<%
														} else {
													%>
													<option value='<%=lch.getString("lchId")%>'><%=lch.getString("lchTen")%></option>
													<%
														}
																}
															} catch (java.sql.SQLException e) {
																e.printStackTrace();
															}
													%>
											</SELECT></TD>
											
											<TD class="plainlabel"></TD>
										</TR>
										<TR style="display:none;">
											<TD class="plainlabel"><%=ChuyenNgu.get("CodeRoute", nnId)%></TD>
											<TD class="plainlabel" colspan=3>
												<SELECT name="coderoute" onChange="submitform();">
													<option value=""></option>
													<%
														try {
															if (codeRouteRs != null) {
																while (codeRouteRs.next()) {
																	if (obj.getCoderoute() != null && 
																			codeRouteRs.getString("pk_seq").equals(obj.getCoderoute())) {
													%>
													<option value='<%=codeRouteRs.getString("pk_seq")%>' selected><%=codeRouteRs.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=codeRouteRs.getString("pk_seq")%>'><%=codeRouteRs.getString("ten")%></option>
													<%
														}
																}
															}
															} catch (java.sql.SQLException e) {
																e.printStackTrace();
															}
													%>
												</SELECT>
											</TD>
											<TD class="plainlabel"></TD>
										</TR>
										<TR>
											<TD class="plainlabel" colspan="4">
												<a class="button2" href="javascript:clearform()"> 
													<img style="top: -4px;" src="../images/button.png" alt=""> <%=ChuyenNgu.get("Nhập lại", nnId)%>
												</a>&nbsp;&nbsp;&nbsp;&nbsp; 
												<a class="button2" href="javascript:xuatExcel()"> 
													<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất Excel", nnId)%>
												</a> &nbsp;&nbsp;&nbsp;&nbsp; 
													<%if( quyen[Utility.CHOT] != 0  ){ %>
												<a class="button2" href="javascript:duyetmulti()"> 
													<img style="top: -4px;" src="../images/button.png" alt=""> <%=ChuyenNgu.get("Duyệt khách hàng", nnId)%>
												</a>&nbsp;&nbsp;&nbsp;&nbsp; 
												<a class="button2" href="javascript:ngungmulti()"> 
													<img style="top: -4px;" src="../images/button.png" alt=""> <%=ChuyenNgu.get("Ngưng khách hàng", nnId)%>
												</a>&nbsp;&nbsp;&nbsp;&nbsp; 
													<%} %>
												<%if( quyen[Utility.CHOT] != 0  ){ %>
												<a class="button2" href="javascript:xoatoadomulti()"> 
													<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xoá toạ độ khách hàng", nnId)%>
												</a> &nbsp;&nbsp;&nbsp;&nbsp; 
												<%} %>
												<!-- <INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();"> --></TD>
                                    	<TD class="plainlabel"></TD>
										</TR>
									</TABLE>
								</FIELDSET>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellspacing="0" cellpadding="2">
						<TR>
							<TD width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle">
										&nbsp;<%=ChuyenNgu.get("Khách hàng", nnId)%>
										&nbsp;&nbsp;&nbsp;
										<%
											if ( quyen[Utility.THEM] != 0 && view != null && !view.equals("TT")) {
										%>
										<a class="button3" href="javascript:newform()"> <img
											style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới", nnId)%>
										</a>
										<%
											}
										%>

										<!-- <INPUT name="new" type="button" value="Tao moi" onClick="newform();"> -->
									</LEGEND>
									<TABLE class="fixedWidth">
										<TR>
											<TD width="98%">
												<TABLE width="100%" border="0" cellspacing="1"
													cellpadding="4">
													<TR class="tbheader">
													<TH><%=ChuyenNgu.get("Mã nhà phân phối", nnId)%></TH>
													<TH><%=ChuyenNgu.get("Tên nhà phân phối", nnId)%></TH>
													<TH><%=ChuyenNgu.get("Tên khách hàng", nnId)%></TH>
													<TH><%=ChuyenNgu.get("Mã khách hàng", nnId)%></TH>
													<TH><%=ChuyenNgu.get("Mã KH tham chiếu", nnId)%></TH>
													<TH><%=ChuyenNgu.get("Địa chỉ", nnId)%></TH>
													<%-- <TH><%=ChuyenNgu.get("Route", nnId)%></TH>
													<TH><%=ChuyenNgu.get("CodeRoute", nnId)%></TH> --%>
													<TH><%=ChuyenNgu.get("Tên NVBH", nnId)%></TH>
													<TH><%=ChuyenNgu.get("Mã NVBH", nnId)%></TH>	
													<TH><%=ChuyenNgu.get("Mã SS", nnId)%></TH>	
													<TH><%=ChuyenNgu.get("Tên SS", nnId)%></TH>
													<TH><%=ChuyenNgu.get("Ngày tạo", nnId)%></TH>													
													<TH><%=ChuyenNgu.get("Trạng thái duyệt", nnId)%></TH>
													<TH><%=ChuyenNgu.get("Có toạ độ", nnId)%></TH>													
													<TH><%=ChuyenNgu.get("Tác vụ", nnId)%></TH>
													<TH><%=ChuyenNgu.get("Chọn hết", nnId)%>
														<input type="checkbox" name="checkAll" id="checkAll" onclick="fn_checkAll();">
													</TH>
													</TR>

													<%
														int m = 0;
														String lightrow = "tblightrow";
														String darkrow = "tbdarkrow";
														String pattern = "0000000";
														DecimalFormat df2 = new DecimalFormat(pattern);
														if (khlist != null) {
															try {
																while (khlist.next()) {
																	if (m % 2 != 0) {
													%>
													<TR class=<%=lightrow%>>
													<% 				
																	} else { 
													%>													
													<TR class=<%=darkrow%>>
													<%				
																	} 
													%>
														<TD align="center"><%=khlist.getString("nppId")%></TD>
														<TD align="center"><%=khlist.getString("nppTen")%></TD>
														<TD align="left"><%=khlist.getString("khTen")%></TD>
														
														<%
														String daduyet = khlist.getString("daduyet")==null?"":khlist.getString("daduyet");
														if(daduyet.equals("0")) { %>
															<TD style="color: red; font-weight: bold;" align="left"><%=khlist.getString("smartid")%></TD>
														<%} else { %>
															<TD align="left"><%=khlist.getString("smartid")%></TD>
														<%} %>
														<TD align="left"><%=khlist.getString("mathamchieu")%></TD>
														<TD align="left"><%=khlist.getString("diachi")%></TD>
														<%-- <TD align="center"><%=khlist.getString("RouteName")%></TD>
														<TD align="center"><%=khlist.getString("CodeRoute")%></TD> --%>
														<TD align="center"><%=khlist.getString("ddkdten")%></TD>
														<TD align="center"><%=khlist.getString("ddkdma")%></TD>
														<TD align="center"><%=khlist.getString("gsbhma")%></TD>
														<TD align="center"><%=khlist.getString("gsbhten")%></TD>
														<TD align="center"><%=khlist.getString("ngaytao")%></TD>														
														<% 
															if (daduyet.length() > 0 && !daduyet.equals("0"))
																daduyet = "Duyệt";
															else
																daduyet = "Chưa";
															
															String lat = khlist.getString("lat");
															String lng = khlist.getString("long");
														%>
														<TD align="center"><%=daduyet%></TD>
														<TD align="center">
															<% if (!lat.equals("0") && !lng.equals("0")) { %>
															<A href="#">
																<img src="../images/Display20.png" onClick="showMap('<%=lat%>','<%=lng%>');"
																	alt="Show map" title="Show map" width="20" height="20" longdesc="Show map" border=0>
															</A>
															<% } %>
														</TD>
														<TD align="center">
															<TABLE border=0 cellpadding="0" cellspacing="0">
																<TR>
																	<Td>
																	<%if( quyen[Utility.SUA] != 0  ){ %>
																		<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "KhachhangUpdateSvl?userId=" + userId+ "&update="+ khlist.getString("khId") +"&view="+view)%>">
																			<img src="../images/Edit20.png" alt="Update" title="Update" width="20" height="20" longdesc="Update" border=0>
																		</A>
																	<%} %>
																	</TD>
																	<TD>&nbsp;</TD> 
																	<TD>
																	<%if( quyen[Utility.XOA] != 0  ){ %>
																		<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "KhachhangSvl?userId=" + userId+ "&delete="+ khlist.getString("khId") +"&view="+view)%>">
																			<img src="../images/Delete20.png" alt="Delete" title="Delete" width="20" height="20" longdesc="Delete" border=0
																			onclick="if(!confirm('Bạn có muốn xoá khách hàng này?')) return false;">
																		</A>
																		<%} %>
																	</TD>	
																	<TD>&nbsp;</TD> 
																	<Td>
																	<%if( quyen[Utility.XEM] != 0  ){ %>
																		<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "KhachhangUpdateSvl?userId=" + userId+ "&display="+ khlist.getString("khId") +"&view="+view)%>">
																			<img src="../images/Display20.png" alt="Display" title="Display" width="20" height="20" longdesc="Display" border=0>
																		</A>
																		<%} %>
																	</TD>	
																	<%
																		if ( quyen[Utility.CHOT] != 0  &&  view != null && view.equals("TT") && (khlist.getString("trangthai") != null && khlist.getString("trangthai").equals("1"))) {
																	%>
																	<TD>&nbsp;</TD>
																	<TD><A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "KhachhangSvl?userId=" + userId+ "&deactivate="+ khlist.getString("khId")+"&view="+view )%>">
																			<img src="../images/warning30.gif" alt="Deactivate"
																			title="Deactivate" width="20" height="20"
																			longdesc="Deactivate" border=0
																			onclick="if(!confirm('Bạn có muốn ngưng hoạt động khách hàng này?')) return false;">
																	</A></TD>
																	<%
																		}
																	%>

																	<%
																		if (quyen[Utility.CHOT] != 0  &&  view != null && view.equals("TT")
																								&& khlist.getString("daduyet").equals("0")) {
																	%>
																	<TD>&nbsp;</TD>
																	<TD><A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "KhachhangSvl?userId=" + userId+ "&duyet="+ khlist.getString("khId")+"&view="+view )%>">
																			<img src="../images/Chot.png" alt="Duyệt" width="20"
																			height="20" longdesc="Duyệt" border=0
																			onclick="if(!confirm('Bạn muốn duyệt khách hàng này?')) return false;">
																	</A></TD>
																	<%
																		}
																	%>

																</TR>
															</TABLE>
														</TD>
													<TD align="center">
														<input type="checkbox" name="checkValue" value="<%=khlist.getString("khId")%>">
													</TD>
													</TR>
													<%
														m++;
																	}
																} catch (java.sql.SQLException e) {
																	e.printStackTrace();
																}
															}
													%>

													<tr class="tbfooter">
														<TD align="center" valign="middle" colspan="15"
															class="tbfooter">
															<%
																obj.setNextSplittings();
															%> <script type="text/javascript"
																src="../scripts/phanTrang.js"></script> <input
															type="hidden" name="crrApprSplitting"
															value="<%=obj.getCrrApprSplitting()%>"> <input
															type="hidden" name="nxtApprSplitting"
															value="<%=obj.getNxtApprSplitting()%>"> <%
 															if (obj.getNxtApprSplitting() > 1) {
 															%> <img alt="Trang Dau" src="../images/first.gif"
															style="cursor: pointer;"
															onclick="View(document.forms[0].name, 1, 'view')">
																														&nbsp; <%
															 	} else {
															 %> <img alt="Trang Dau" src="../images/first.gif"> &nbsp; <%
															 	}
															 %> <%
															 	if (obj.getNxtApprSplitting() > 1) {
															 %> <img alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')">
															&nbsp; <%
															 	} else {
															 %> <img alt="Trang Truoc" src="../images/prev_d.gif"> &nbsp; <%
															 	}
															 %> <%
															 	int[] listPage = obj.getNextSplittings();
															 		for (int i = 0; i < listPage.length; i++) {
															 %> <%
															 	//System.out.println("Current page:" + obj.getNxtApprSplitting());
															 			//System.out.println("List page:" + listPage[i]);
															 			if (listPage[i] == obj.getNxtApprSplitting()) {
															 %> <a style="color: white;"><%=listPage[i]%>/ <%=obj.getTheLastSplitting()%></a>
															<%
																} else {
															%> <a
															href="javascript:View(document.forms[0].name, <%=listPage[i]%>, 'view')"><%=listPage[i]%></a>
															<%
																}
															%> <input type="hidden" name="list"
															value="<%=listPage[i]%>" /> &nbsp; <%
															 	}
															 %> <%
															 	if (obj.getNxtApprSplitting() < obj.getTheLastSplitting()) {
															 %> &nbsp; <img alt="Trang Tiep" src="../images/next.gif"
																														style="cursor: pointer;"
																														onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')">
																														&nbsp; <%
															 	} else {
															 %> &nbsp; <img alt="Trang Tiep" src="../images/next_d.gif">
																														&nbsp; <%
															 	}
															 %> <%
															 	if (obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {
															 %> <img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; <%
															 	} else {
															 %> <img alt="Trang Cuoi" src="../images/last.gif"
															style="cursor: pointer;"
															onclick="View(document.forms[0].name, -1, 'view')">
															&nbsp; <%
 	}
 %>
														</TD>
													</tr>
												</TABLE>
									</TABLE>
							</TD>
						</TR>
					</TABLE>
					</FIELDSET>
				</TD>
			</TR>
			</TBODY>
		</TABLE>

		</TR>
		</TABLE>
		<script language="javascript" type="text/javascript">
		getMaximumWidth();
		</script>
	</form>
</BODY>
</HTML>

<%
	try {

			if (hch != null)
				hch.close();
			if (kbh != null)
				kbh.close();
			if (lch != null)
				lch.close();
			if (vtch != null) {
				vtch.close();
			}
			if (obj != null) {
				obj.DBclose();
				obj = null;
			}

			session.setAttribute("obj", null);

		} catch (SQLException e) {
		}
%>
<%
	}
%>