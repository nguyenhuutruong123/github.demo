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
		{//Ph??m Delete v?? Ph??m Back
			return;
		}
		return false;
	}
}
function upload()
{// nhan nut cap nhat
	   if(document.forms["ChiTieuTTForm"].filename.value=="")
	   {		   
		   document.forms["ChiTieuTTForm"].dataerror.value="Ch??a t??m file upload l??n. Vui l??ng ch???n file c???n upload.";
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
	  if(nam==0)
	  {
		  document.forms["ChiTieuTTForm"].dataerror.value="Ch???n n??m c???n ?????t ch??? ti??u ";
		  return;
	  }
	  if(thang==0){
		  document.forms["ChiTieuTTForm"].dataerror.value="Ch???n th??ng c???n ?????t ch??? ti??u ";
		  return;
		  }
	  
	//kiem tra xem thang nam dat chi tieu co hop le hay khong
	  var d=new Date();
		 var year_= d.getFullYear();
		 var month_=d.getMonth()+1;
		
			 if(parseInt(nam)<parseInt(year_)){
				 
				  document.forms["ChiTieuTTForm"].dataerror.value="Th???i gian ?????t ch??? ti??u kh??ng h???p l??. Ph???i ?????t th???i gian ch??? ti??u l???n h??n th???i gian hi???n th???i ";
					return; 
			 }else if(parseInt(nam)==parseInt(year_) && parseInt(thang)<parseInt(month_)){
				  document.forms["ChiTieuTTForm"].dataerror.value="Th???i gian ?????t ch??? ti??u kh??ng h???p l??. Ph???i ?????t th???i gian ch??? ti??u l???n h??n th???i gian hi???n th???i";
					return; 
			 }
	  
		document.forms["ChiTieuTTForm"].action.value="capnhatForm";
		document.forms["ChiTieuTTForm"].submit();

}

function DinhDangTien(num) 
{
   num = num.toString().replace(/\$|\,/g,'');
   if(isNaN(num))
   num = "0";
   sign = (num == (num = Math.abs(num)));
   num = Math.floor(num*100+0.50000000001);
   num = Math.floor(num/100).toString();
   for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
   num = num.substring(0,num.length-(4*i+3))+','+
   num.substring(num.length-(4*i+3));
   return (((sign)?'':'-') + num);
}
function Dinhdang(element)
	{
		element.value=DinhDangTien(element.value);
		if(element.value== '' ||element.value=='0' )
		{
			element.value= '';
		}
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
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="userTen" value='<%=userTen%>'> 
		<input type="hidden" name="nkmId" value=""> 
		<input type="hidden" name="action" value="0"> 
		<input type="hidden" name="id" value='<%=obj.getID()%>'> 
		<input type="hidden" name="tenform" value="0"> 
		<input type="hidden" name="luutam" value="<%=check1%>"> 
		<input type="hidden" name="loaichitieu" value="<%=loaichitieu%>">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Qu???n
											l?? ch??? ti??u &gt; Ch??? ti??u cho NPP &gt; Ch??? ti??u cho b??n ra
											&gt; C???p nh???t</TD>
										<TD colspan="2" align="right" class="tbnavigation">Ch??o
											m???ng b???n <%=userTen%>&nbsp;
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
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"><A href="javascript:save()"><IMG
												src="../images/Save30.png" title="Luu lai" alt="Luu lai"
												border="1" style="border-style: outset"></A></TD>
										<TD width="30" align="left"><A
											href="../../ChiTieuNewSvl?userId=<%=userId%>&excel=<%=obj.getID()%>"><img
												src="../images/excel.gif" alt="Xu???t File Excel NPP"
												width="20" height="20" title="Xu???t File Excel NPP" border=0></A></TD>
										<TD><A
											href="../../ChiTieuNewSvl?userId=<%=userId%>&excelSR=<%=obj.getID()%>"><img
												src="../images/excel.gif" alt="Xu???t File Excel NVBH"
												width="20" height="20" title="Xu???t File Excel NVBH" border=0></A></TD>

									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">B??o l???i nh???p li???u </LEGEND>
									<textarea name="dataerror" style="width: 100%"
										readonly="readonly" rows="1"><%=obj.getMessage()%></textarea>
								</FIELDSET>
							</TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Th??ng
										tin ch??? ti??u</LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

										
											<TR>
								<TD width="130px" class="plainlabel" valign="top">Th??ng <FONT class="erroralert"> </FONT></TD>
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
								<TD width="120px" class="plainlabel" valign="top"> N??m </TD>
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
											<td>????n v??? kinh doanh</td>
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

											<td>K??nh b??n h??ng</td>
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
											<TD class="plainlabel">S??? ng??y l??m vi???c</TD>
											<TD class="plainlabel"><input
												onkeypress="return keypress(event);" type="text"
												name="songaylamviec" value="<%=obj.getSoNgayLamViec()%>">
											</TD>
											<TD class="plainlabel"></TD>
											<TD class="plainlabel"></TD>
										</TR>
										<TR>
											<TD class="plainlabel">Di???n gi???i</TD>
											<TD class="plainlabel"><textarea rows="" cols=""
													name="diengiai"> <%=obj.getDienGiai()%></textarea></TD>
											<TD class="plainlabel">Ng??y k???t th??c</TD>
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
													C???p nh???t
											</a>
											</td>
										</tr>
									</TABLE>

									<ul class="tabs">
										<li><a href="#">Nh?? ph??n ph???i</a></li>
										<li><a href="#">Nh??n vi??n</a></li>
									</ul>
									<div class="panes">
										<div>
											<TABLE width="1600px" border="1" cellspacing="1px"
												cellpadding="3px">
												<TR class="tbheader">
													<TH width="7%">M?? nh?? ph??n ph???i</TH>
													<TH width="19%">T??n nh?? ph??n ph???i</TH>
													<TH width="7%">S??? ????n h??ng</TH>
													<TH width="7%">S???n l?????ng / ????n h??ng</TH>
													<TH width="7%">SellsOut</TH>
													<TH width="7%">% Kh??ch H??ng Mua H??ng</TH>
													<TH width="7%">S??? Kh??ch H??ng M???i</TH>
													<TH width="7%">T??? L??? Giao H??ng</TH>
													<TH width="7%">????? Ch??nh X??c T???n Kho</TH>
													<%
														int col = 5;
														if (nhomsp != null) {
															for (int i = 0; i < nhomsp.length; i++) {
																col++;
																if (nhomsp[i] != null) {
													%>
													<TH width="5%"><%=nhomsp[i]%></TH>
													<%
														}
															}

														}
													%>
												</TR>
												<%
													int m = 0;

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
														<%="V??ng  :  " + listnhapp.getString("vungTen")%>
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

													<TD colspan="<%=col%>"><%="Khu v???c : " + listnhapp.getString("kvTen")%>
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

													<TD><input onkeyup="Dinhdang(this)" name="ctGiaoHang"
														style="text-align: right; width: 100%; border: 0"
														type="text"
														value="<%=formatter.format(listnhapp
							.getDouble("ctGiaoHang"))%>" />
													</TD>

													<TD><input onkeyup="Dinhdang(this)" name="ctTonKho"
														style="text-align: right; width: 100%; border: 0"
														type="text"
														value="<%=formatter.format(listnhapp.getDouble("ctTonKho"))%>" />
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
										<div>
											<TABLE width="1400px" border="1" cellspacing="1px"
												cellpadding="3px">
												<TR class="tbheader">
													<TH width=7%>M?? nh??n vi??n</TH>
													<TH width=20%>T??n nh??n vi??n</TH>
													<TH width="10%">ch??? ti??u Sellsout</TH>
													<TH width="7%">S??? ????n h??ng</TH>
													<TH width="7%">S???n l?????ng ????n h??ng</TH>
													<TH width="7%">% Kh??ch H??ng Mua H??ng</TH>
													<TH width="7%">S??? Kh??ch H??ng M???i</TH>
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
													m = 1;
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
														<%="Khu v???c  :  "
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


														<input onkeyup="Dinhdang(this)" name="ctGiaoHang_Ddkd"
														style="text-align: right; width: 100%; border: 0"
														type="hidden"
														value="<%=formatter.format(rschitieunv
							.getDouble("ctGiaoHang"))%>" />
														<input onkeyup="Dinhdang(this)" name="ctTonKho_Ddkd"
														style="text-align: right; width: 100%; border: 0"
														type="hidden"
														value="<%=formatter.format(rschitieunv
							.getDouble("ctTonKho"))%>" />
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
														value=" <%=formatter.format(rschitieunv
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
<% obj.DbClose();
	obj=null;
%>
</HTML>