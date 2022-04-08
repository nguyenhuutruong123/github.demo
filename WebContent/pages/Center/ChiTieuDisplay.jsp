<%@page import="geso.dms.center.beans.chitieu.imp.ChiTieuNPP"%>
<%@page import="java.util.Calendar"%>
<%@page
	import="geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTKhuVuc"%>
<%@page
	import="geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTChoVung"%>
<%@page import="java.util.Date"%>
<%@page import="javax.xml.crypto.Data"%>
<%@page import="geso.dms.center.beans.chitieu.imp.ChiTieu"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.beans.nhomkhuyenmai.INhomkhuyenmai"%>
<%@ page import="geso.dms.center.beans.nhomkhuyenmai.imp.Nhomkhuyenmai"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.center.util.*"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	//Lay doi tuong obj
	ChiTieu obj = (ChiTieu) session.getAttribute("obj");
	//truyen qua doi tuong list vung

	String check1 = (String) session.getAttribute("check");
	//lay resultset vung de do vao combobox

	String loaichitieu = (String) session.getAttribute("loaichitieu");
	ResultSet rs_dvkd = obj.getRsDvdk();
	ResultSet rs_kenh = obj.getRsKenh();

	ResultSet listnhapp = obj.getRsNppNhomSp();

	NumberFormat formatter = new DecimalFormat("#,###,###.##");

	ResultSet rschitieunv = obj.getRsChitieunhanvien();

	String[] nhomsp = obj.getNhomSp();
	String[] nhomspid = obj.getNhomSpId();
	ResultSet kyRs = obj.getKyRs();
	ResultSet quyRs = obj.getQuyRs();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs-panes.css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../scripts/Numberformat.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>
<script>
//perform JavaScript after the document is scriptable.
$(function() {
 // setup ul.tabs to work as tabs for each div directly under div.panes
 $("ul.tabs").tabs("div.panes > div");
});
</script>
<SCRIPT language="JavaScript" type="text/javascript"> 
function submitform()
{
    document.forms["ChiTieuTTForm"].submit();
}
function keypress(e) 
{    
	var keypressed = null;
	if (window.event)
		keypressed = window.event.keyCode; //IE
	else
		keypressed = e.which; //NON-IE, Standard
	
	if (keypressed < 48 || keypressed > 57)
	{ 
		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
		{//Phím Delete và Phím Back
			return;
		}
		return false;
	}
}
function upload()
{// nhan nut cap nhat
	   if(document.forms["ChiTieuTTForm"].filename.value=="")
	   {		   
		   document.forms["ChiTieuTTForm"].dataerror.value="Chưa tìm file upload lên. Vui lòng chọn file cần upload.";
	   }else
	   {
		 document.forms["ChiTieuTTForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["ChiTieuTTForm"].submit();	
	   }
}
function save()
{
	  document.forms["ChiTieuTTForm"].dataerror.value=" ";
	 var thang=document.forms["ChiTieuTTForm"].thang.value;
	 var nam=document.forms["ChiTieuTTForm"].nam.value;
	 var tongchitieu=document.forms["ChiTieuTTForm"].tongchitieu.value;
	 var loaichitieu=document.forms["ChiTieuTTForm"].loaichitieu.value;
	  if(nam==0)
	  {
		  document.forms["ChiTieuTTForm"].dataerror.value="Chọn năm cần đạt chỉ tiêu ";
		  return;
	  }
	  if(thang==0){
		  document.forms["ChiTieuTTForm"].dataerror.value="Chọn tháng cần đạt chỉ tiêu ";
		  return;
		  }
		document.forms["ChiTieuTTForm"].action.value="capnhatForm";
		document.forms["ChiTieuTTForm"].submit();

}

</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="ChiTieuTTForm" method="post" action="../../ChiTieuNewSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="userTen" value='<%=userTen%>'> <input
			type="hidden" name="nkmId" value=""> <input type="hidden"
			name="action" value="0"> <input type="hidden" name="id"
			value='<%=obj.getID()%>'> <input type="hidden"
			name="tenform" value="0"> <input type="hidden" name="luutam"
			value="<%=check1%>"> <input type="hidden" name="loaichitieu"
			value="<%=loaichitieu%>">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản
											lý chỉ tiêu &gt; Chỉ tiêu cho nhân viên &gt; Hiển thị</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%=userTen%>&nbsp;
										</TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="javascript:history.back()"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay về"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left"><A
											href="../../ChiTieuNewSvl?userId=<%=userId%>&excel=<%=obj.getID()%>"><img
												src="../images/excel.gif" alt="Xuất File Excel NPP"
												width="20" height="20" title="Xuất File Excel NPP" border=0></A></TD>
										<TD width="30" align="left"><A
											href="../../ChiTieuNewSvl?userId=<%=userId%>&excelSR=<%=obj.getID()%>"><img
												src="../images/excel.gif" alt="Xuất File Excel NVBH"
												width="20" height="20" title="Xuất File Excel NVBH" border=0></A></TD>

										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
									<textarea name="dataerror" style="width: 100%"
										readonly="readonly" rows="1"><%=obj.getMessage()%></textarea>
								</FIELDSET>
							</TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông
										tin chỉ tiêu</LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

										<TR>
								<TD width="130px" class="plainlabel" valign="top">Tháng <FONT class="erroralert"> </FONT></TD>
								<TD width="300px" class="plainlabel" valign="top">
									<select name="thang" style="width :50px">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  									  if(obj.getThang().equals(k+"")){
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
									
								</TD>
								<TD width="120px" class="plainlabel" valign="top"> Năm </TD>
								<TD class="plainlabel" valign="top">
									<select name="nam"  style="width :80px">
									<option value=0> </option>  
									<%
									  Calendar c2 = Calendar.getInstance();
  										int t=c2.get(Calendar.YEAR) +6;
  										int n;
  										for(n=2015;n<t;n++){
  										if(obj.getNam().equals(n+"")){
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
									</select>
								</TD>
							</TR>
										
										<tr class="plainlabel">
											<td>Đơn vị kinh doanh</td>
											<td><select name=dvkdid>
													<%
														if (rs_dvkd != null) {
															while (rs_dvkd.next()) {
																if (rs_dvkd.getString("pk_seq").equals(obj.getDVKDId())) {
													%>
													<option value="<%=rs_dvkd.getString("pk_seq")%>"
														selected="selected">
														<%=rs_dvkd.getString("donvikinhdoanh")%></option>
													<%
														} else {
													%>
													<option value="<%=rs_dvkd.getString("pk_seq")%>">
														<%=rs_dvkd.getString("donvikinhdoanh")%></option>
													<%
														}
															}
														}
													%>
											</select></td>

											<td>Kênh bán hàng</td>
											<td><select name=kbhid>
													<%
														if (rs_kenh != null) {
															while (rs_kenh.next()) {
													%>
													<%
														if (rs_kenh.getString("pk_seq").equals(obj.getKenhId())) {
													%>
													<option value="<%=rs_kenh.getString("pk_seq")%>"
														selected="selected">
														<%=rs_kenh.getString("ten")%></option>
													<%
														} else {
													%>
													<option value="<%=rs_kenh.getString("pk_seq")%>">
														<%=rs_kenh.getString("ten")%></option>
													<%
														}
													%>
													<%
														}
														}
													%>
											</select></td>

										</tr>
										<TR>
											<TD class="plainlabel">Số ngày làm việc</TD>
											<TD class="plainlabel"><input
												onkeypress="return keypress(event);" type="text"
												name="songaylamviec" value="<%=obj.getSoNgayLamViec()%>">
											</TD>
											<TD class="plainlabel"></TD>
											<TD class="plainlabel"></TD>
										</TR>
										<TR>
											<TD class="plainlabel">Diễn giải</TD>
											<TD class="plainlabel"><textarea rows="" cols=""
													name="diengiai"> <%=obj.getDienGiai()%></textarea></TD>
											<TD class="plainlabel">Ngày kết thúc</TD>
											<TD class="plainlabel"><input type="text" class="days"
												name="ngayketthuc" value="<%=obj.getNgayKetThuc()%>">
											</TD>
										</TR>
										<tr class="plainlabel">
											<TD class="plainlabel">File Upload</td>
											<TD class="plainlabel"><INPUT type="file"
												name="filename" size="40" value=''></TD>
											<TD class="plainlabel" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
												<a class="button2" href="javascript:upload()"> <img
													style="top: -4px;" src="../images/button.png" alt="">
													Cập nhật
											</a>
											</td>
										</tr>
									</TABLE>

									<ul class="tabs">
									<li><a href="#">Nhân viên</a></li>
										<li><a href="#">Nhà phân phối</a></li>
										
									</ul>
									<div class="panes">
									<div>
											<TABLE width="1400px" border="1" cellspacing="1px"
												cellpadding="3px">
												<TR class="tbheader">
													<TH width=7%>Mã nhân viên</TH>
													<TH width=20%>Tên nhân viên</TH>
													<TH width="10%">chỉ tiêu Sellsout</TH>
													<TH width="7%">Số đơn hàng</TH>
													<TH width="7%">Sản lượng đơn hàng</TH>
													<TH width="10%">% Khách Hàng Mua Hàng</TH>
													<TH width="10%">Số Khách Hàng Mới</TH>
													<%
													int	col = 5;
														if (nhomsp != null) {
															for (int i = 0; i < nhomsp.length; i++) {
																col++;
																if (nhomsp[i] != null) {
													%>
													<TH width="10%"><%=nhomsp[i]%></TH>
													<%
														}
															}

														}
													%>
												</TR>
												<%
													int m = 1;
													if (rschitieunv != null) {

														String nppIdPrev = "";
														String kvIdPrev = "";
														while (rschitieunv.next()) {
															if (!kvIdPrev.trim().equals(
																	rschitieunv.getString("kvTen").trim())) {

																kvIdPrev = rschitieunv.getString("kvTen").trim();
												%>
												<tr style="color: blue; font-weight: bold; font-size: 14">

													<TD colspan="<%=col%>" style="text-align: center;">
														<%="Khu vực  :  "
								+ rschitieunv.getString("kvTen")%>
													</TD>
												</tr>
												<%
													}
												%>

												<%
													if (!nppIdPrev.trim().equals(
																	rschitieunv.getString("NPPID").trim())) {
																nppIdPrev = rschitieunv.getString("NPPID");
												%>
												<tr style="color: black; font-weight: bold; font-size: 12">

													<TD colspan="<%=col%>"><%=" " + rschitieunv.getString("nppTen")%>
													</TD>
												</tr>
												<%
													}
												%>

												<tr>

													<TD><input name="ddkdId"
														style="text-align: right; width: 100%; border: 0"
														type="text" value="<%=rschitieunv.getString("ddkdId")%>" />
														<input name="ddkd_NppId" type="hidden"
														value="<%=rschitieunv.getString("nppId")%>" /></TD>
													<TD><input name="ddkdTen"
														style="text-align: left; width: 100%; border: 0"
														type="text" value="<%=rschitieunv.getString("ddkdTen")%>" />
													</TD>
													<TD><input onkeyup="Dinhdang(this)"
														name="ctSalesOut_Ddkd"
														style="text-align: right; width: 100%; border: 0"
														type="text"
														value="<%=formatter.format(rschitieunv
							.getDouble("ctSalesOut"))%>" />
													</TD>
													<TD><input onkeyup="Dinhdang(this)"
														name="ctSoDonHang_Ddkd"
														style="text-align: right; width: 100%; border: 0"
														type="text"
														value="<%=formatter.format(rschitieunv
							.getDouble("ctSodonhang"))%>" />
													</TD>
													<TD><input onkeyup="Dinhdang(this)"
														name="ctSanLuong_Ddkd"
														style="text-align: right; width: 100%; border: 0"
														type="text"
														value="<%=formatter.format(rschitieunv
							.getFloat("ctSanLuong"))%>" />
													</TD>
													<TD><input onkeyup="Dinhdang(this)"
														name="ctSoKhachHang_MuaHang_Ddkd"
														style="text-align: right; width: 100%; border: 0"
														type="text"
														value="<%=formatter.format(rschitieunv
							.getDouble("ctSoKhachHang_MuaHang"))%>" />
													</TD>
													<TD><input onkeyup="Dinhdang(this)"
														name="ctSoKhachHang_PhatSinh_Ddkd"
														style="text-align: right; width: 100%; border: 0"
														type="text"
														value="<%=formatter.format(rschitieunv
							.getDouble("ctSoKhachHang_PhatSinh"))%>" />
													</TD>

													<%
														if (nhomspid != null) {
																	for (int i = 0; i < nhomspid.length; i++) {
																		if (nhomspid[i] != null) {
													%>
													<TD width="10%"><input
														name="ctDdkdNspId_<%=nhomspid[i]%>"
														onkeyup="Dinhdang(this)"
														style="text-align: right; width: 100%; border: 0"
														type="text"
														value=" <%=formatter.format(rschitieunv.getDouble("CT"+nhomspid[i]))%>">

													</TD>
													<%
										}
									 }
										
									}
									%>

												</tr>
												<%
									m++;
								}
							}
							%>
											</TABLE>
										</div>
										<div>
											<TABLE width="1400px" border="1" cellspacing="1px"
												cellpadding="3px">
												<TR class="tbheader">
													<TH width="7%">Mã nhà phân phối</TH>
													<TH width="35%">Tên nhà phân phối</TH>
													<TH width="7%">Số đơn hàng</TH>
													<TH width="7%">Sản lượng / đơn hàng</TH>
													<TH width="10%">SellsOut</TH>
													<TH width="10%">% Khách Hàng Mua Hàng</TH>
													<TH width="10%">Số Khách Hàng Mới</TH>
													<%
														col = 5;
														if (nhomsp != null) {
															for (int i = 0; i < nhomsp.length; i++) {
																col++;
																if (nhomsp[i] != null) {
													%>
													<TH width="10%"><%=nhomsp[i]%></TH>
													<%
														}
															}

														}
													%>
												</TR>
												<%
													m = 0;

													if (listnhapp != null) {
														String nppIdPrev = "";
														String kvIdPrev = "";
														while (listnhapp.next()) {
															if (!kvIdPrev.trim().equals(
																	listnhapp.getString("vungTen").trim())) {

																kvIdPrev = listnhapp.getString("vungTen").trim();
												%>
												<tr style="color: blue; font-weight: bold; font-size: 14">

													<TD colspan="<%=col%>" style="text-align: center;">
														<%="Vùng  :  " + listnhapp.getString("vungTen")%>
													</TD>
												</tr>
												<%
													}
												%>

												<%
													if (!nppIdPrev.trim().equals(
																	listnhapp.getString("kvTen").trim())) {
																nppIdPrev = listnhapp.getString("kvTen");
												%>
												<tr style="color: black; font-weight: bold; font-size: 12">

													<TD colspan="<%=col%>"><%="Khu vực : " + listnhapp.getString("kvTen")%>
													</TD>
												</tr>
												<%
													}
												%>

												<tr>
													<TD><input name="nppId"
														style="text-align: right; width: 100%; border: 0"
														type="text" value="<%=listnhapp.getString("nppId")%>" />

													</TD>
													<TD><input name="nppTen"
														style="text-align: left; width: 100%; border: 0"
														type="text" value="<%=listnhapp.getString("nppTen")%>" />
													</TD>
													<TD><input onkeyup="Dinhdang(this)" name="ctSoDonHang"
														style="text-align: right; width: 100%; border: 0"
														type="text"
														value="<%=formatter.format(listnhapp
							.getDouble("ctSoDonHang"))%>" />
													</TD>
													<TD><input onkeyup="Dinhdang(this)" name="ctSanLuong"
														style="text-align: right; width: 100%; border: 0"
														type="text"
														value="<%=formatter.format(listnhapp
							.getDouble("ctSanLuong"))%>" />
													</TD>
													<TD><input onkeyup="Dinhdang(this)" name="ctSalesOut"
														style="text-align: right; width: 100%; border: 0"
														type="text"
														value="<%=formatter.format(listnhapp
							.getDouble("ctSalesOut"))%>" />
													</TD>
													<TD><input onkeyup="Dinhdang(this)"
														name="ctSoKhachHang_MuaHang"
														style="text-align: right; width: 100%; border: 0"
														type="text"
														value="<%=formatter.format(listnhapp
							.getDouble("ctSoKhachHang_MuaHang"))%>" />
													</TD>
													<TD><input onkeyup="Dinhdang(this)"
														name="ctSoKhachHang_PhatSinh"
														style="text-align: right; width: 100%; border: 0"
														type="text"
														value="<%=formatter.format(listnhapp
							.getDouble("ctSoKhachHang_PhatSinh"))%>" />
													</TD>

													<%
														if (nhomspid != null) {
																	for (int i = 0; i < nhomspid.length; i++) {
																		if (nhomspid[i] != null) {
													%>
													<TD width="10%"><input onkeyup="Dinhdang(this)"
														name="ctNppNspId_<%=nhomspid[i]%>"
														style="text-align: right; width: 100%; border: 0"
														type="text"
														value=" <%=formatter.format(listnhapp
										.getDouble("CT" + nhomspid[i]))%>">
													</TD>
													<%
														}
																	}

																}
													%>
												</tr>
												<%
													m++;
														}
													}
												%>
											</TABLE>
										</div>
									</div>
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