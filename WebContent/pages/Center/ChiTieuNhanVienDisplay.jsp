<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="geso.dms.center.beans.chitieu.imp.*"%>
<%@page import="geso.dms.center.beans.chitieu.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page import="java.util.Hashtable"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum))
	{
		response.sendRedirect("/IMV/index.jsp");
	} else
	{
%>

<%
	IChiTieuNhanVien obj = (IChiTieuNhanVien) session.getAttribute("tctskuBean");
		ResultSet sanphamRs = obj.getSanphamRs();
		ResultSet vungRs = obj.getVungRs();
		ResultSet kvRs = obj.getKhuvucRs();
		ResultSet nppRs = obj.getNppRs();
		ResultSet tdvRs = obj.getTdvRs();
		ResultSet nppctRs = obj.getNppCtRs();
		ResultSet SsRs = obj.getSsRs();
		ResultSet AsmRs = obj.getAsmRs();
		ResultSet RsmRs = obj.getRsmRs();
		ResultSet KyRs = obj.getKyRs();
		ResultSet quyRs = obj.getQuyRs();

		ResultSet nhomRs = obj.getNhomRs();
		
		String[] nhomsp = obj.getNhomTen();
		String[] nhomspid = obj.getNhomId();

		String[] tumuc = (String[]) obj.getTumuc();
		String[] denmuc = (String[]) obj.getDenmuc();
		String[] thuong = (String[]) obj.getThuong();
		String[] donvi_tinh_ds = (String[]) obj.getDonvi_tinh_ds();
		String[] donvi_tinh_thuong = (String[]) obj.getDonvi_tinh_thuong();
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
%>
<%
	Hashtable<String, String> npp_chitieu = (Hashtable<String, String>) obj.getNpp_chitieu();
%>
<%
	Hashtable<String, String> npp_donvi_chitieu = (Hashtable<String, String>) obj.getNpp_donvi_chitieu();
%>

<%
	Hashtable<String, String> tdv_chitieu = (Hashtable<String, String>) obj.getTdv_chitieu();
%>
<%
	Hashtable<String, String> asm_chitieu = (Hashtable<String, String>) obj.getAsm_chitieu();
%>
<%
	Hashtable<String, String> rsm_chitieu = (Hashtable<String, String>) obj.getRsm_chitieu();
%>
<%
	Hashtable<String, String> ss_chitieu = (Hashtable<String, String>) obj.getSs_chitieu();
%>


<%
	Hashtable<String, String> tdv_donvi_chitieu = (Hashtable<String, String>) obj.getTdv_donvi_chitieu();

	String url="Qu???n l?? ch??? ti??u > Ch??? ti??u nh??n vi??n > Ch??? ti??u cho B??n Ra > Hi???n th???";
	if(obj.getLoaichitieu().equals("1")){
		 url="Qu???n l?? ch??? ti??u > Ch??? ti??u nh??n vi??n > Ch??? ti??u cho Mua V??o > Hi???n th???";
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>DDT - Project</TITLE>
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
<!-- <link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script> -->


<script type="text/javascript">
		$(document).ready(function() {
			$(".days").datepicker({
				changeMonth : true,
				changeYear : true
			});
		});
	</script>


<script>
	$(function() {
		$( "#tabs" ).tabs();
	});
	</script>

<script>
	jQuery(document).ready(function()
			{
				$("select:not(.notuseselect2)").chosen();     
				
			}); 
	</script>


<SCRIPT language="JavaScript" type="text/javascript">
		function replaces()
		{
			var masp = document.getElementsByName("maspTra");
			var tensp = document.getElementsByName("tenspTra");
			var type = document.getElementById("hinhthuctra");
	
			var i;
			for(i=0; i < masp.length; i++)
			{
				if(masp.item(i).value != "")
				{
					var sp = masp.item(i).value;
					var pos = parseInt(sp.indexOf(" - "));
					if(pos > 0)
					{
						masp.item(i).value = sp.substring(0, pos);
						tensp.item(i).value = sp.substr(parseInt(sp.indexOf(" - ")) + 3);					
					}
				}
				else
				{
					tensp.item(i).value = "";
					if(type.value == "1")
					{
						var soluong = document.getElementsByName("soluong");
						soluong.item(i).value = "";
					}
				}			
			}
			setTimeout(replaces, 300);
		}	
		
	
		
		function keypress(e) //H??m d??ng d? ngan ngu?i d??ng nh?p c??c k?? t? kh??c k?? t? s? v??o TextBox
		{    
			var keypressed = null;
			if (window.event)
				keypressed = window.event.keyCode; //IE
			else
				keypressed = e.which; //NON-IE, Standard
			
			if (keypressed < 48 || keypressed > 57)
			{ 
				if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
				{//Ph??m Delete v?? Ph??m Back
					return;
				}
				return false;
			}
		}
		
		function save()
		{
		  document.forms["tctsku"].action.value = "save";
		  document.forms["tctsku"].submit(); 
	  	}
	
		function submitform()
		{
			document.forms["tctsku"].action.value = "submit";
			document.forms["tctsku"].submit(); 
		}
		
	function FormatNumber(e)
	{
		e.value = DinhDangTien(e.value.replace(/,/g,""));
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
	
	function SelectALL()
	{
		var checkAll = document.getElementById("checkAll");
		var spIds = document.getElementsByName("spIds");
		
		if(checkAll.checked == true)
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = false;
		}
		
	}
	
	function SelectALL2()
	{
		var checkAll = document.getElementById("checkAll2");
		var spIds = document.getElementsByName("nppIds");
		
		if(checkAll.checked == true)
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = false;
		}
		
	}
	function SelectALL3()
	{
		var checkAll = document.getElementById("checkAll3");
		var spIds = document.getElementsByName("tdvIds");
		
		if(checkAll.checked == true)
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = false;
		}
		
	}
	
	function upload()
	{
		   if(document.forms["tctsku"].filename.value=="")
		   {   
			   document.forms["tctsku"].dataerror.value="Ch??a t??m file upload l??n. Vui l??ng ch???n file c???n upload.";
		   }else
		   {
			 document.forms["tctsku"].setAttribute('enctype', "multipart/form-data", 0);
			 document.forms["tctsku"].action.value = "save";
			 document.forms["tctsku"].submit();	
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

	<form name="tctsku" method="post"
		action="../../ChiTieuNhanVienUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="action" value="0"> <input type="hidden"
			name="loaichitieu" value='<%=obj.getLoaichitieu()%>'> <input
			type="hidden" name="view" value='<%=obj.getView()%>'> 
			<input type="hidden" name="tructhuocId" value='<%=obj.getTructhuocId()%>'>
			<input type="hidden" name="id" value='<%=obj.getId()%>'>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp; <%= url %></TD>
										<TD colspan="2" align="right" class="tbnavigation">Ch??o
											m???ng b???n <%=userTen%></TD>
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
											href="../../ChiTieuSvl?userId=<%=userId%>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left">
											<div id="btnSave">
												
											</div>
										</TD>
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
									<LEGEND class="legendtitle">B??o l???i nh???p li???u </LEGEND>

									<textarea name="dataerror"
										style="width: 100%; color: #F00; font-weight: bold"
										style="width: 100% ; color:#F00 ; font-weight:bold"
										readonly="readonly" rows="1"><%=obj.getMsg()%></textarea>
								</FIELDSET>
							</TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Ch???
										ti??u </LEGEND>
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
								<TD width="150px" class="plainlabel" valign="top"> N??m </TD>
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

										<TR>
											<TD class="plainlabel">Scheme <FONT class="erroralert">
													*</FONT></TD>
											<TD class="plainlabel" colspan="3"><input type="text"
												name="scheme" id="scheme" value="<%=obj.getScheme()%>">
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel">Di???n gi???i</TD>
											<TD class="plainlabel" colspan="3"><input type="text"
												name="diengiai" id="diengiai"
												value="<%=obj.getDiengiai()%>">

												&nbsp;&nbsp;&nbsp;&nbsp;</TD>
										</TR>

										<%-- <TR>
											<TD class="plainlabel" colspan="6"><a href="#"
												class="btn btn-lg btn-primary" data-toggle="modal"
												data-target="#divNhanHang">Nh??m ch??? ti??u</a>
												<div class="modal fade" id="divNhanHang" tabindex="-1"
													role="dialog" aria-labelledby="myModalLabel"
													aria-hidden="true">
													<div class="modal-dialog modal-lg">
														<div class="modal-content">
															<div class="modal-header">
																<button type="button" class="close" data-dismiss="modal"
																	aria-hidden="true">&times;</button>
																<h4 class="modal-title" id="myModalLabel">Nh??m ch???
																	ti??u</h4>
															</div>
															<div class="modal-body">
																<table width="95%" align="center">
																	<TR class="tbheader">
																		<th width="250px"
																			style="font-size: 12px; text-align: center;">T??n</th>
																		<th width="80px"
																			style="font-size: 12px; text-align: center;">Ch???n
																		</th>
																	</tr>
																	<%
																		int sodong = 0;
																			while (nhomRs != null && nhomRs.next())
																			{
																				/* String ck = nhPtCk.get(nhRs.getString("nhId")) != null ? nhPtCk.get(nhRs.getString("nhId")) : ""; */
																	%>
																	<TR>
																		<TD><input type="hidden" style="width: 100%"
																			name="nhId" value="<%=nhomRs.getString("pk_seq")%>">
																			<input type="text" style="width: 100%" name="nhTen"
																			value="<%=nhomRs.getString("ten")%>"
																			readonly="readonly"></TD>
																		<TD><input type="checkbox" style="width: 100%"
																			name="nhPtCk"></TD>
																	</TR>
																	<%
																		}
																	%>
																</table>
															</div>
															<div class="modal-footer">
																<button type="button" class="btn btn-default"
																	data-dismiss="modal">Close</button>
															</div>
														</div>
													</div>
												</div></TD>
										</TR> --%>

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
										<!-- <li><a href="#">Ti??u ch?? th?????ng</a></li> -->
										 <!-- <li><a href="#">S???n ph???m </a></li> -->
										<li><a href="#">SR</a></li>
										<li><a href="#">Nh?? ph??n ph???i</a></li>
										 <li><a href="#">SS</a></li>
										 <li><a href="#">ASM</a></li>
										<li><a href="#">RSM</a></li> 
									</ul>
									<div class="panes">
										<div>
											<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
												<TR class="tbheader">
													<td align="center" width="10%">M??</td>
													<td align="center" width="20%">T??n</td>
													<td align="center" width="10%">Ch??? ti??u Sales Out</td>
													<td align="center" width="10%">S??? ????n h??ng</td>
													<td align="center" width="10%">G??a tr??? TB/??H</td>
													<td align="center" width="10%">S??? kh??ch h??ng m???i</td>
													<td align="center" width="10%">S??? kh??ch h??ng ph??t sinh doanh s???</td>
													
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
												String[]	trangthai = new String[]
														{ "Doanh s???", "S???n ph???m", "S??? c???a hi???u" };
	String[]														idTrangThai = new String[]
														{ "1", "2", "3" };
												%>


												<%
													if (tdvRs != null)
														{
															while (tdvRs.next())
															{
												%>
												<tr>
													<td><input type="text" value="<%=tdvRs.getString("nvId")%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%=tdvRs.getString("nvTen")%>" style="width: 100%;"readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( tdvRs.getDouble("ChiTieu"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( tdvRs.getDouble("SoDonHang"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( tdvRs.getDouble("GiaTriTBDonHang"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( tdvRs.getDouble("SoKhachHangMoi"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( tdvRs.getDouble("SoKhachHangMuaHang"))%>" style="width: 100%;" readonly="readonly"></td>
													
														<%
														if (nhomspid != null) {
																	for (int i = 0; i < nhomspid.length; i++) {
																		if (nhomspid[i] != null) {
													%>
													<td><input type="text" value="<%= formatter.format( tdvRs.getDouble(nhomspid[i]))%>" style="width: 100%;" readonly="readonly"></td>
													<%
														}
																	}

																}
													%>
														
												</tr>

												<%
													}
															tdvRs.close();
														}
												%>

												<TR>
													<TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
												</TR>
											</TABLE>
										</div>
										
										<div>
											<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
												<TR class="tbheader">
													<td align="center" width="10%">M??</td>
													<td align="center" width="20%">T??n</td>
													<td align="center" width="10%">Ch??? ti??u Sales Out</td>
													<td align="center" width="10%">S??? ????n h??ng</td>
													<td align="center" width="10%">G??a tr??? TB/??H</td>
													<td align="center" width="10%">S??? kh??ch h??ng m???i</td>
													<td align="center" width="10%">S??? kh??ch h??ng ph??t sinh doanh s???</td>
													
													<%
														col = 5;
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
													trangthai = new String[]
														{ "Doanh s???", "S???n ph???m", "S??? c???a hi???u" };
														idTrangThai = new String[]
														{ "1", "2", "3" };
												%>


												<%
													if (nppctRs != null)
														{
															while (nppctRs.next())
															{
												%>
												<tr>
													<td><input type="text" value="<%=nppctRs.getString("nvId")%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%=nppctRs.getString("nvTen")%>" style="width: 100%;"readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( nppctRs.getDouble("ChiTieu"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( nppctRs.getDouble("SoDonHang"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( nppctRs.getDouble("GiaTriTBDonHang"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( nppctRs.getDouble("SoKhachHangMoi"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( nppctRs.getDouble("SoKhachHangMuaHang"))%>" style="width: 100%;" readonly="readonly"></td>
													
														<%
														if (nhomspid != null) {
																	for (int i = 0; i < nhomspid.length; i++) {
																		if (nhomspid[i] != null) {
													%>
													<td><input type="text" value="<%= formatter.format( nppctRs.getDouble(nhomspid[i]))%>" style="width: 100%;" readonly="readonly"></td>
													<%
														}
																	}

																}
													%>
														
												</tr>

												<%
													}
															nppctRs.close();
														}
												%>

												<TR>
													<TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
												</TR>
											</TABLE>
										</div>
										
										<div>
											<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
												<TR class="tbheader">
													<td align="center" width="10%">M??</td>
													<td align="center" width="20%">T??n</td>
													<td align="center" width="10%">Ch??? ti??u Sales Out</td>
													<td align="center" width="10%">S??? ????n h??ng</td>
													<td align="center" width="10%">G??a tr??? TB/??H</td>
													<td align="center" width="10%">S??? kh??ch h??ng m???i</td>
													<td align="center" width="10%">S??? kh??ch h??ng ph??t sinh doanh s???</td>
													
													<%
														col = 5;
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
													trangthai = new String[]
														{ "Doanh s???", "S???n ph???m", "S??? c???a hi???u" };
														idTrangThai = new String[]
														{ "1", "2", "3" };
												%>


												<%
													if (SsRs != null)
														{
															while (SsRs.next())
															{
												%>
												<tr>
													<td><input type="text" value="<%=SsRs.getString("nvId")%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%=SsRs.getString("nvTen")%>" style="width: 100%;"readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( SsRs.getDouble("ChiTieu"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( SsRs.getDouble("SoDonHang"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( SsRs.getDouble("GiaTriTBDonHang"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( SsRs.getDouble("SoKhachHangMoi"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( SsRs.getDouble("SoKhachHangMuaHang"))%>" style="width: 100%;" readonly="readonly"></td>
													
														<%
														if (nhomspid != null) {
																	for (int i = 0; i < nhomspid.length; i++) {
																		if (nhomspid[i] != null) {
													%>
													<td><input type="text" value="<%= formatter.format( SsRs.getDouble(nhomspid[i]))%>" style="width: 100%;" readonly="readonly"></td>
													<%
														}
																	}

																}
													%>
														
												</tr>

												<%
													}
															SsRs.close();
														}
												%>

												<TR>
													<TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
												</TR>
											</TABLE>
										</div>
										
										
										
										<div>
											<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
												<TR class="tbheader">
													<td align="center" width="10%">M??</td>
													<td align="center" width="20%">T??n</td>
													<td align="center" width="10%">Ch??? ti??u Sales Out</td>
													<td align="center" width="10%">S??? ????n h??ng</td>
													<td align="center" width="10%">G??a tr??? TB/??H</td>
													<td align="center" width="10%">S??? kh??ch h??ng m???i</td>
													<td align="center" width="10%">S??? kh??ch h??ng ph??t sinh doanh s???</td>
													
													<%
														col = 5;
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
													trangthai = new String[]
														{ "Doanh s???", "S???n ph???m", "S??? c???a hi???u" };
														idTrangThai = new String[]
														{ "1", "2", "3" };
												%>


												<%
													if (AsmRs != null)
														{
															while (AsmRs.next())
															{
												%>
												<tr>
													<td><input type="text" value="<%=AsmRs.getString("nvId")%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%=AsmRs.getString("nvTen")%>" style="width: 100%;"readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( AsmRs.getDouble("ChiTieu"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( AsmRs.getDouble("SoDonHang"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( AsmRs.getDouble("GiaTriTBDonHang"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( AsmRs.getDouble("SoKhachHangMoi"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( AsmRs.getDouble("SoKhachHangMuaHang"))%>" style="width: 100%;" readonly="readonly"></td>
													
														<%
														if (nhomspid != null) {
																	for (int i = 0; i < nhomspid.length; i++) {
																		if (nhomspid[i] != null) {
													%>
													<td><input type="text" value="<%= formatter.format( AsmRs.getDouble(nhomspid[i]))%>" style="width: 100%;" readonly="readonly"></td>
													<%
														}
																	}

																}
													%>
														
												</tr>

												<%
													}
															AsmRs.close();
														}
												%>

												<TR>
													<TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
												</TR>
											</TABLE>
										</div>
										
										<div>
											<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
												<TR class="tbheader">
													<td align="center" width="10%">M??</td>
													<td align="center" width="20%">T??n</td>
													<td align="center" width="10%">Ch??? ti??u Sales Out</td>
													<td align="center" width="10%">S??? ????n h??ng</td>
													<td align="center" width="10%">G??a tr??? TB/??H</td>
													<td align="center" width="10%">S??? kh??ch h??ng m???i</td>
													<td align="center" width="10%">S??? kh??ch h??ng ph??t sinh doanh s???</td>
													
													<%
														col = 5;
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
													trangthai = new String[]
														{ "Doanh s???", "S???n ph???m", "S??? c???a hi???u" };
														idTrangThai = new String[]
														{ "1", "2", "3" };
												%>


												<%
													if (RsmRs != null)
														{
															while (RsmRs.next())
															{
												%>
												<tr>
													<td><input type="text" value="<%=RsmRs.getString("nvId")%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%=RsmRs.getString("nvTen")%>" style="width: 100%;"readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( RsmRs.getDouble("ChiTieu"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( RsmRs.getDouble("SoDonHang"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( RsmRs.getDouble("GiaTriTBDonHang"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( RsmRs.getDouble("SoKhachHangMoi"))%>" style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" value="<%= formatter.format( RsmRs.getDouble("SoKhachHangMuaHang"))%>" style="width: 100%;" readonly="readonly"></td>
													
														<%
														if (nhomspid != null) {
																	for (int i = 0; i < nhomspid.length; i++) {
																		if (nhomspid[i] != null) {
													%>
													<td><input type="text" value="<%= formatter.format( RsmRs.getDouble(nhomspid[i]))%>" style="width: 100%;" readonly="readonly"></td>
													<%
														}
																	}

																}
													%>
														
												</tr>

												<%
													}
															AsmRs.close();
														}
												%>

												<TR>
													<TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
												</TR>
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
	<script type="text/javascript">
	replaces();
</script>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
	}
%>
