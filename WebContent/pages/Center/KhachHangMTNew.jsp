<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.beans.khachhangmt.IKhachhangMT"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page  import = "java.util.Hashtable"%>
<%@ page  import ="java.util.Set" %>
<%@ page import ="java.util.Map" %>
<%@ page import ="java.util.Iterator" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IKhachhangMT nppBean = (IKhachhangMT)session.getAttribute("nppBean"); %>
<% ResultSet tp = (ResultSet)nppBean.getTp() ;  %>
<% ResultSet qh = (ResultSet)nppBean.getQh() ;  %>
<% ResultSet kv = (ResultSet)nppBean.getKhuvuc();  %>
<% ResultSet npptn = (ResultSet)nppBean.getNhappTiennhiem();  %>
<% ResultSet dvkd_ncc = (ResultSet)nppBean.getDvkd_NccList(); %>
<% ResultSet dvkd_nccSelected = (ResultSet)nppBean.getDvkd_NccSelected(); %>
<% ResultSet gsbh_kbh = (ResultSet)nppBean.getGsbh_KbhList();  %>
<% String gsbh_kbhSelected = (String)nppBean.getGsbh_KbhIdSelected();%>
<% ResultSet rs_khott=(ResultSet)nppBean.getrs_khott(); %>
<% ResultSet ngaydh = (ResultSet)nppBean.getNgaydhList();%>
<% String ngaydhSelected = (String)nppBean.getNgayDh_IdSelected();%>

<% String dvkd_ncc_Selected = (String)nppBean.getDvkd_NccIdSelected(); %>

<% ResultSet loainppRs = (ResultSet)nppBean.getLoaiNppRs();%>
<% ResultSet tructhuocRs = (ResultSet)nppBean.getTructhuocRs();%>
<% ResultSet ttppRs = (ResultSet)nppBean.getTtppRs();%>
<% ResultSet muahangtuRs = (ResultSet)nppBean.getMuahangtuRs();%>

<!DOCTYPE HTML>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	
	
	
	
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>

<SCRIPT language="javascript" type="text/javascript">



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

function FormatNumber(e)
{
	e.value = DinhDangTien(e.value);
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


function Kiemtra(object)
{

	if(object.checked==true)
		{
			var gsbh=document.getElementById("gsbh");
			var n =gsbh.value.indexOf(object.value); 
			if(n !="-1")
				{
					var r=confirm("Gi??m s??t n??y ???? c?? nh?? ph??n ph???i, b???n mu???n ti???p t???c?");
					if (r==true)
					  {
						object.checked=true;
					  }
					else
					  {
						object.checked=false;
					  }
				}
			}
		
}
 function submitform()
 {   
    document.forms["nppForm"].submit();
 }

 function saveform()
 {
	 /*var ten = document.getElementById('nppTen');
	 var diachi = document.getElementById('DiaChi');
	 var tp = document.getElementById('TP');
	 var qh = document.getElementById('QH');
	 var dienthoai = document.getElementById('DienThoai');
	 var khuvuc = document.getElementById('KhuVuc');
	 var dvkd_ncc = document.getElementsByName("dvkd_nccList");
	 var gsbh_kbh = document.getElementsByName("gsbh_kbhList");
	 var maSAP = document.getElementById('maSAP');
	 var pass = document.getElementById('pass');
	 
	 if(ten.value == "")
	 {
		 alert('Vui l??ng nh???p t??n Nh?? ph??n ph???i');
		 return;
	 }
	 if(diachi.value == "")
	 {
		 alert('Vui l??ng nh???p ?????a ch???');
		 return;
	 }	
	 if(dienthoai.value == "")
	 {
		 alert('Vui l??ng nh???p s??? ??i???n tho???i');
		 return;
	 }	
	 
	 if(tp.value == "")
	 {
		 alert('Vui l??ng ch???n th??nh ph???');
		 return;
	 }	
	 
	 if(qh.value == "")
	 {
		 alert('Vui l??ng ch???n qu???n huy???n');
		 return;
	 }	

	 if(khuvuc.value == "")
	 {
		 alert('Vui l??ng ch???n khu v???c');
		 return;
	 }	 
	 
	 if(maSAP.value == "")
	 {
		 alert('Vui l??ng nh???p m?? truy c???p');
		 return;
	 }	 
	
	 var flag = '';
	 for(var i in dvkd_ncc)
	 {
		 if(dvkd_ncc[i].checked)
			 flag = flag + dvkd_ncc[i].value;
	 }
	 if(flag == '')
	 {
		 alert('Vui l??ng ch???n ????n v??? kinh doanh v?? nh?? cung c???p');
		 return;
	 }
	 	 
	 
	 var flag2 = '';
	 for(var i in gsbh_kbh)
	 {
		 if(gsbh_kbh[i].checked)
			 flag2 = flag2 + gsbh_kbh[i].value;
	 }
	 if(flag2 == '')
	 {
		 alert('Vui l??ng ch???n gi??m s??t b??n h??ng v?? k??nh b??n h??ng');
		 return;
	 }  */
	 
 	document.forms['nppForm'].action.value='save';
	 document.forms['nppForm'].submit();
 }
 
 jQuery(document).ready(function()
		 {
		 	$("select:not(.notuseselect2)").chosen();     
		 	
		 }); 
 
</SCRIPT>


<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>




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

	<form name="nppForm" method="post" action="../../KhachhangMTUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" id="gsbh" value="<%= nppBean.getGsbhnpp() %>">
		<input type="hidden" name="userId" value='<%= userId %>'> <input
			type="hidden" name="action" id="action" value='1'>
		<%-- <input type="hidden" name="checkgsbh" id="checkgsbh" value='<%= nppBean.CheckGsbh()%>'> --%>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;D???
											li???u n???n &gt; Kinh doanh &gt; Kh??ch h??ng MT &gt; T???o m???i</TD>
										<TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%=userTen %>&nbsp;
										</TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR class="tbdarkrow">
							<TD width="30" align="center"><A
								href="javascript:history.back()"><img
									src="../images/Back30.png" alt="Quay ve" title="Quay ve"
									width="30" height="30" border="1" longdesc="Quay ve"
									style="border-style: outset"></A></TD>
							<TD width="2" align="left"></TD>
							<TD width="30" align="left"><A href="javascript:saveform()"><IMG
									src="../images/Save30.png" title="Luu lai" alt="Luu lai"
									border="1" style="border-style: outset"></A></TD>
							<TD align="left">&nbsp;</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">B??o l???i nh???p li???u </LEGEND>
									<textarea name="dataerror"
										style="width: 100%; color: #F00; font-weight: bold"
										style="width:100%" rows="1"><%= /* nppBean.getMessage() */ nppBean.getError() +"\r\n" + " "+ nppBean.getMessage() %></textarea>
									<%nppBean.setMessage(""); %>
								</FIELDSET>
							</TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Th??ng
										tin nh?? ph??n ph???i </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										
										
										
										<%-- <TR>
											
											 <TD class="plainlabel" width="130px">Ch???n lo???i:<FONT
												class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel">
													<SELECT name="loai" id="loai"
												onChange="submitform();">
													<option value=""></option>
													<%
								      if(loainppRs!=null)
								      try{while(loainppRs.next()){ 
								    	if(loainppRs.getString("pk_seq").equals(nppBean.getLoaiNpp()  )){ %>
													<option value='<%=loainppRs.getString("pk_seq")%>' selected><%=loainppRs.getString("ten") %></option>
													<%}else{ %>
													<option value='<%=loainppRs.getString("pk_seq")%>'><%=loainppRs.getString("ten") %></option>
													<%}}}catch(java.sql.SQLException e){} %>
											</SELECT>
											</TD>
											<TD class="plainlabel"></TD>
											<TD class="plainlabel"></TD>
											<TD class="plainlabel"></TD>
											<TD class="plainlabel"></TD>
										</TR> --%>
										
										
										
										<TR>
											<TD class="plainlabel" width="130px">T??n<FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel" width="220px"><INPUT
												name="nppTen" id="nppTen" type="text"
												value="<%= nppBean.getTen() %>" required></TD>
											<%-- <TD class="plainlabel" width="140px">M?? kh??ch h??ng<FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><INPUT name="maddt"
												id="maddt" type="text" value="<%= nppBean.getMaDDT()%>">
											</TD> --%>
											<TD class="plainlabel">M?? kh??ch h??ng<FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><INPUT name="maSAP" id="maSAP"
												type="text" value="<%= nppBean.getMaSAP() %>" size="10" onchange="submitform();"></TD>
											<TD class="plainlabel" width="140px"> Ng?????i ?????i di???n <FONT class="erroralert"></FONT>
											</TD>
											<TD class="plainlabel">
												<INPUT name="ndd" id="ndd"
												type="text" value="<%= nppBean.getNguoidaidien() %>"
												size="10">
											
											</TD>
										</TR>

										<TR>
											<TD class="plainlabel">?????a ch??? giao h??ng <FONT
												class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel"><INPUT name="DiaChi" id="DiaChi"
												type="text" value="<%= nppBean.getDiachi() %>"></TD>

											<TD class="plainlabel">T???nh/Th??nh ph??? <FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel" width="220px"><SELECT
												name="tpId" id="TP" onChange="submitform();" class="select2"
												style="width: 200px;">
													<option value=""></option>
													<% if(tp!=null)
								      	try{while(tp.next()){ 
								    	if(tp.getString("tpId").equals(nppBean.getTpId())){ %>
													<option value='<%=tp.getString("tpId")%>' selected><%=tp.getString("tpTen") %></option>
													<%}else{ %>
													<option value='<%=tp.getString("tpId")%>'><%=tp.getString("tpTen") %></option>
													<%}}}catch(java.sql.SQLException e){} %>
											</SELECT></TD>

											<TD class="plainlabel">Qu???n/Huy???n <FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel">
												<SELECT name="qhId" id="QH"
												class="select2" style="width: 200px;">
													<option value=""></option>
													<%if(qh != null){ 
									      		try{while(qh.next()){ 
									    			if(qh.getString("qhId").equals(nppBean.getQhId())){ %>
													<option value='<%=qh.getString("qhId")%>' selected><%=qh.getString("qhTen") %></option>
													<%}else{ %>
													<option value='<%=qh.getString("qhId")%>'><%=qh.getString("qhTen") %></option>
													<%}}}catch(java.sql.SQLException e){} 
									     		
									      }	%>
											</SELECT>
											</TD>
										</TR>

										<TR>
											<TD class="plainlabel">?????a ch??? giao h??ng 2<FONT class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><INPUT name="diachi2" id="diachi2" type="text" value="<%= nppBean.getDiachi2() %>"></TD>
											<TD class="plainlabel">??i???n tho???i<FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><INPUT name="DienThoai"
												id="DienThoai" type="text"
												value="<%= nppBean.getSodienthoai() %>"></TD>
											<%-- <TD class="plainlabel">
												<!-- ?????a b??n ho???t ?????ng --> L???ch ?????t h??ng <FONT
												class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel">

												<TABLE cellpadding="0" cellspacing="0">
													<TR>
														<TD><INPUT name="LichDatHang" class="days"
															id="LichDatHang" type="text" size="20"
															value="<%= nppBean.getLichDatHang()%>"></TD>
														<input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="20" onchange="submitform();">
														</TD>

													</TR>
												</TABLE>
											</TD> --%> <%-- <INPUT name="DiaBanHd" id="DiaBanHd" type="text" value="<%= nppBean.getDiaBanHd() %>"></TD> --%>
											<TD class="plainlabel">?????a ch??? xu???t H??<FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><INPUT name="diachixhd"
												id="diachixhd" type="text"
												value="<%= nppBean.getDiaChiXuatHoaDon() %>"></TD>

										</TR>


										<TR>
											<TD class="plainlabel">M?? s??? thu??? <FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><INPUT name="masothue"
												id="masothue" type="text"
												value="<%= nppBean.getmaSoThue() %>"></TD>

											<TD class="plainlabel">Kho H??ng B??n <FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><SELECT name="khottid"
												id="khottid">
													<option value=""></option>
													<% if(rs_khott!=null) 
								      try{while(rs_khott.next()){ 
								    	if(rs_khott.getString("pk_seq").trim().equals(nppBean.getKhoTT().trim())){ %>
													<option value='<%=rs_khott.getString("pk_seq")%>' selected><%=rs_khott.getString("ten") %></option>
													<%}else{ %>
													<option value='<%=rs_khott.getString("pk_seq")%>'><%=rs_khott.getString("ten") %></option>
													<%}}}catch(java.sql.SQLException e){} %>
											</SELECT></TD>							
											<%-- <TD class="plainlabel">T???n an to??n<FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel">
												<INPUT  onkeyup="Dinhdang(this)"  name="tonantoan" id="tonantoan" type="text" value="<%= nppBean.getTonAnToan() %>"></TD> --%>
											<TD class="plainlabel">T???n su???t ?????t h??ng <FONT class="erroralert"> *</FONT></TD>
											<TD class="plainlabel">
												<input type="text" name="tsdathang" value="<%= nppBean.getTSdathang()%>" onkeyup="Dinhdang(this)" >
											</TD>
										</TR>


										<TR>
											<TD class="plainlabel">Khu v???c <FONT class="erroralert">
													*</FONT></TD>
											<TD class="plainlabel"><SELECT name="kvId" id="KhuVuc"
												onChange="submitform();">
													<option value=""></option>
													<%
								      if(kv!=null)
								      try{while(kv.next()){ 
								    	if(kv.getString("kvId").equals(nppBean.getKvId())){ %>
													<option value='<%=kv.getString("kvId")%>' selected><%=kv.getString("kvTen") %></option>
													<%}else{ %>
													<option value='<%=kv.getString("kvId")%>'><%=kv.getString("kvTen") %></option>
													<%}}}catch(java.sql.SQLException e){} %>
											</SELECT></TD>

											<%-- <TD class="plainlabel"> <!-- Ng??n h??ng<FONT class="erroralert"> *</FONT> --></TD>
								 <TD  class="plainlabel">
								 <input type="text" name="NganHangId" value="<%=nppBean.getNganHangId() %>" />
								 </TD> --%>
											<%-- <TD class="plainlabel">Mua h??ng t??? <!-- ?????a ch??? kho -->
												<FONT class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel">
											<SELECT name="noimuahang" id="noimuahang">
												
												<% 
													try{
														while(muahangtuRs.next()){
															if(muahangtuRs.getString("pk_seq").equals(nppBean.getMuaHangTu().trim())){
																%>
																	<option selected value="<%=muahangtuRs.getString("pk_seq")%>"><%=muahangtuRs.getString("ten")%><option>
																<% 
															} else{
																%>
																	<option value="<%=muahangtuRs.getString("pk_seq")%>"><%=muahangtuRs.getString("ten")%><option>
																<%
															}
															}
													}catch(java.sql.SQLException e){}
												%>
											</SELECT> 

											</TD> --%>


											<TD class="plainlabel">Chi nh??nh<FONT class="erroralert">
													*</FONT></TD>
											<TD class="plainlabel" ><input type="text"
												name="ChiNhanhId" value="<%=nppBean.getChiNhanhId() %>" /></TD>
												
											<TD class="plainlabel">
												<!-- Gi???y ph??p kinh doanh <FONT--> Fax: <FONT class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel" colspan=5>
												<INPUT name="fax" id="fax"
												type="text" value="<%= nppBean.getFax() %>" size="10">
												<%-- <INPUT name="gpkd" id="gpkd" type="text" value="<%= nppBean.getGiayphepKD() %>" size="10"> --%>
											</TD>
										</TR>
										<%-- <TR>
											<TD class="plainlabel">Ch??? nh?? ph??n ph???i <!-- Ch??? t??i kho???n-->
												<FONT class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel">
												<INPUT name="ChuTaiKhoan" id="ChuTaiKhoan" type="text" value="<%= nppBean.getChuTaiKhoan() %>" size="10">
												<INPUT name="ChuNhaPhanPhoi" id="ChuNhaPhanPhoi" type="text"
												value="<%= nppBean.getChuNhaPhanPhoi() %>" size="10">
											</TD>

											<TD class="plainlabel">M?? truy c???p <FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><INPUT name="maSAP" id="maSAP"
												type="text" value="<%= nppBean.getMaSAP() %>" size="10" onchange="submitform();"></TD>

											<TD class="plainlabel">
												<!-- Gi???y ph??p kinh doanh <FONT--> Fax: <FONT class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel" colspan=5>
												<INPUT name="fax" id="fax"
												type="text" value="<%= nppBean.getFax() %>" size="10">
												<INPUT name="gpkd" id="gpkd" type="text" value="<%= nppBean.getGiayphepKD() %>" size="10">
											</TD>
										</TR> --%>

										<TR>
											<TD class="plainlabel">Ng??y b???t ?????u <FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><input class="days" type="text"
												name="tungay" size="15" value="<%= nppBean.getTungay()%>">
											</TD>
											<TD class="plainlabel">Ng??y k???t th??c <FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><input class="days" type="text"
												name="denngay" size="15" value="<%= nppBean.getDenngay()%>">
											</TD>
											<TD class="plainlabel"><!-- Fax--> Trung t??m ph??n ph???i: <FONT class="erroralert">
													*</FONT></TD>
											<TD class="plainlabel">
											<select name="ttppId"
												id="ttppId" class="select2" multiple="multiple"
												style="width: 200px;">
													<option value=""></option>
													<% if(ttppRs != null) { 
											while(ttppRs.next())
											{
												if (nppBean.getTtppId() .indexOf(ttppRs.getString("id")) >=0) {%>
													<option value="<%= ttppRs.getString("Id") %>"
														selected="selected"><%= ttppRs.getString("TEN") %></option>
													<% }
												else { %>
													<option value="<%= ttppRs.getString("Id") %>"><%= ttppRs.getString("TEN") %></option>
													<% }
											}
											ttppRs.close();
										} %>
											</select>
											
											<%-- <INPUT name="fax" id="fax"
												type="text" value="<%= nppBean.getFax() %>" size="10"> --%></TD>
										</TR>


										<TR>
											<TD class="plainlabel">S??? ng??y n??? <FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><input type="text"
												name="songayno" value="<%= nppBean.getSongayno()%>" onkeyup="Dinhdang(this)" >
											</TD>
											<TD class="plainlabel">S??? ti???n n???<FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><input type="text"
												name="sotienno"  onkeyup="Dinhdang(this)" value="<%= nppBean.getSotienno()%>">
											</TD>
											<TD class="plainlabel"> Quy tr??nh b??n h??ng: <font class="erroralert">*</font>
											
											</TD>
											<TD class="plainlabel">
												<SELECT name="quytrinhbanhang"
												id="quytrinhbanhang">

													<% if (nppBean.getQuyTrinhBanHang().equals("0")){%>
													<option selected="true" value="0">Quy tr??nh chu???n
													</option>
													<option value="1">Quy tr??nh r??t g???n</option>
													<option value=""></option>
													<%}else if(nppBean.getQuyTrinhBanHang().equals("1")) {%>
													<option value=""></option>
													<option value="0">Quy tr??nh chu???n</option>
													<option selected="true" value="1">Quy tr??nh r??t
														g???n</option>
													<%} else{ %>
													<option selected="true" value=""></option>
													<option value="0">Quy tr??nh chu???n</option>
													<option value="1">Quy tr??nh r??t g???n</option>
													<%} %>

											</SELECT>
											</TD>
										</TR>

										<TR>
											<TD class="plainlabel"> <!-- Tr???c thu???c: <FONT class="erroralert">
													*</FONT> --></TD>
											<TD class="plainlabel">
											<%-- <select name="tructhuocId"
												id="tructhuocId" class="select2" style="width: 200px;">
													<option value=""></option>
													<% if(tructhuocRs != null) { 
											while(tructhuocRs.next())
											{
												if(tructhuocRs.getString("Id").equals(nppBean.getTructhuocId())) { %>
													<option value="<%= tructhuocRs.getString("Id") %>"
														selected="selected"><%= tructhuocRs.getString("TEN") %></option>
													<% }
												else { %>
													<option value="<%= tructhuocRs.getString("Id") %>"><%= tructhuocRs.getString("TEN") %></option>
													<% }
											}
											tructhuocRs.close();
										} %>
											</select> --%>
											<%-- <SELECT name="loai" id="loai"
												onChange="submitform();">
													<option value=""></option>
													<%
								      if(loainppRs!=null)
								      try{while(loainppRs.next()){ 
								    	if(loainppRs.getString("pk_seq").equals(nppBean.getLoaiNpp()  )){ %>
													<option value='<%=loainppRs.getString("pk_seq")%>' selected><%=loainppRs.getString("ten") %></option>
													<%}else{ %>
													<option value='<%=loainppRs.getString("pk_seq")%>'><%=loainppRs.getString("ten") %></option>
													<%}}}catch(java.sql.SQLException e){} %>
											</SELECT> --%></TD>
											<TD class="plainlabel"><!-- Tr???c thu???c<FONT
												class="erroralert"> *</FONT> --></TD>
											<TD class="plainlabel"><%-- <select name="tructhuocId"
												id="tructhuocId" class="select2" style="width: 200px;">
													<option value=""></option>
													<% if(tructhuocRs != null) { 
											while(tructhuocRs.next())
											{
												if(tructhuocRs.getString("Id").equals(nppBean.getTructhuocId())) { %>
													<option value="<%= tructhuocRs.getString("Id") %>"
														selected="selected"><%= tructhuocRs.getString("TEN") %></option>
													<% }
												else { %>
													<option value="<%= tructhuocRs.getString("Id") %>"><%= tructhuocRs.getString("TEN") %></option>
													<% }
											}
											tructhuocRs.close();
										} %>
											</select> --%></TD>

											<TD class="plainlabel"><!-- Trung t??m ph??n ph???i<FONT
												class="erroralert"> *</FONT> --></TD>
											<TD class="plainlabel"><%-- <select name="ttppId"
												id="ttppId" class="select2" multiple="multiple"
												style="width: 200px;">
													<option value=""></option>
													<% if(ttppRs != null) { 
											while(ttppRs.next())
											{
												if (nppBean.getTtppId() .indexOf(ttppRs.getString("id")) >=0) {%>
													<option value="<%= ttppRs.getString("Id") %>"
														selected="selected"><%= ttppRs.getString("TEN") %></option>
													<% }
												else { %>
													<option value="<%= ttppRs.getString("Id") %>"><%= ttppRs.getString("TEN") %></option>
													<% }
											}
											ttppRs.close();
										} %>
											</select> --%></TD>
										</TR>


										<TR>
											<TD class="plainlabel"><!-- S??? t??i kho???n <FONT
												class="erroralert"> *</FONT> --></TD>
											<TD class="plainlabel"><%-- <INPUT name="sotk" id="sotk"
												type="text" value="<%= nppBean.getSotk() %>" size="10"> --%></TD>

											<TD class="plainlabel"><!-- Ng?????i ?????i di???n <FONT
												class="erroralert"> *</FONT> --></TD>
											<TD class="plainlabel"><%-- <INPUT name="ndd" id="ndd"
												type="text" value="<%= nppBean.getNguoidaidien() %>"
												size="10"> --%></TD>
											<TD class="plainlabel"><!-- Quy tr??nh b??n h??ng <FONT
												class="erroralert"> *</FONT> --></TD>
											<TD class="plainlabel"><%-- <SELECT name="quytrinhbanhang"
												id="quytrinhbanhang">

													<% if (nppBean.getQuyTrinhBanHang().equals("0")){%>
													<option selected="true" value="0">Quy tr??nh chu???n
													</option>
													<option value="1">Quy tr??nh r??t g???n</option>
													<option value=""></option>
													<%}else if(nppBean.getQuyTrinhBanHang().equals("1")) {%>
													<option value=""></option>
													<option value="0">Quy tr??nh chu???n</option>
													<option selected="true" value="1">Quy tr??nh r??t
														g???n</option>
													<%} else{ %>
													<option selected="true" value=""></option>
													<option value="0">Quy tr??nh chu???n</option>
													<option value="1">Quy tr??nh r??t g???n</option>
													<%} %>

											</SELECT> --%>
											<TD>
										</TR>

										<TR>
											<%-- <TD class="plainlabel"><div align="right">Pri. =
													Sec.</div></TD>
											<TD class="plainlabel">
												<%  if (nppBean.getPriSec().equals("1")){%> <input
												name="prisec" type="checkbox" value="1" checked> <%} else {%>
												<input name="prisec" type="checkbox" value="0"> <%} %>

											</TD> --%>


											<TD class="plainlabel"><div align="right">Ho???t
													?????ng</div></TD>
											<TD class="plainlabel" colspan="5">
												<%  if (nppBean.getTrangthai().equals("1")){%> <input
												name="TrangThai" type="checkbox" value="1" checked>
												<%} else {%> <input name="TrangThai" type="checkbox" value="0">
												<%} %>

											</TD>



										</TR>
									</TABLE>
								</FIELDSET>

								
								<FIELDSET>
								
						<LEGEND class="legendtitle" style="color:black">Ch???n ????n v??? kinh doanh</LEGEND>
                        <TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
                            <TR class="tbheader">
                                <TH width="13%">????n v??? kinh doanh </TH>
                                <TH width="13%">Nh?? cung c???p </TH>
                                <TH width="13%">Ch???n </TH>
                            </TR>              		
                     		<%
								int i = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								
								if (!(dvkd_ncc == null)){
							    	try{
							    		while(dvkd_ncc.next()){							    			
										if (i % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
										
										<TD align="center"><div align="left"><%= dvkd_ncc.getString("dvkdTen")%> </div></TD>
										<TD align="center"><div align="left"><%= dvkd_ncc.getString("nccTen") %> </div></TD>
										<TD align="center">
										<% if(dvkd_ncc_Selected.indexOf(dvkd_ncc.getString("dvkd_nccId"))>=0) { %>
										<input type="checkbox" checked name="dvkd_nccList" value="<%=dvkd_ncc.getString("dvkd_nccId") %>" id="dvkd_nccList"/>
										<%}else{%>
											<input type="checkbox" name="dvkd_nccList" value="<%=dvkd_ncc.getString("dvkd_nccId") %>" id="dvkd_nccList"/>
											<%}%>
											
										</TR>
							     		<% i++;
										}
							    	
							    	}catch(java.sql.SQLException e){} 
								}
								%>
								  	  		
                        </TABLE> 							
						</FIELDSET>

								<%-- <FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Ch???n
										gi??m s??t b??n h??ng</LEGEND>
									<TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
										<TR class="tbheader">
											<TH width="13%">Gi??m s??t b??n h??ng</TH>
											<TH width="13%">K??nh b??n h??ng</TH>
											<TH width="13%">S??? ??i???n tho???i</TH>
											<TH width="13%">Ch???n</TH>
										</TR>
										<%
								int j = 0;
								String light = "tblightrow";
								String dark = "tbdarkrow";
								if(!(gsbh_kbh == null)){
									try{
								while(gsbh_kbh.next())
									{ 
										if (j % 2 != 0) {%>
										<TR class=<%=light%>>
											<%} else {%>
										
										<TR class=<%= dark%>>
											<%}%>
											<TD align="center"><div align="left"><%= gsbh_kbh.getString("gsbhTen") %>
												</div></TD>
											<TD align="center"><div align="left"><%= gsbh_kbh.getString("kbhTen") %>
												</div></TD>
											<TD align="center"><div align="left"><%= gsbh_kbh.getString("sodienthoai") %></div></TD>
											<TD align="center">
											<% if(gsbh_kbhSelected.indexOf(gsbh_kbh.getString("gsbh_kbhId"))>=0){%>
												<input name="gsbh_kbhList" onclick="Kiemtra(this)" id="gsbh_kbhList" type="checkbox"
												value="<%= gsbh_kbh.getString("gsbh_kbhId")%>"
												checked></TD>
											<%}else{ %>
												
												<input name="gsbh_kbhList" onclick="Kiemtra(this)" id="gsbh_kbhList" type="checkbox" value="<%= gsbh_kbh.getString("gsbh_kbhId")%>"/>
												</TD>
											<%} %>
											
										</TR>
										<%j++;}
									}
									catch(java.sql.SQLException e){}
								}
								
							  %>
									</TABLE>
								</FIELDSET> --%>


								<%-- <FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Ch???n
										ng??y cho ph??p ?????t h??ng</LEGEND>
									<TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
										<TR class="tbheader">
											<TH width="13%">Ng??y</TH>
											<TH width="13%">Ch???n</TH>
										</TR>
										<%
								int k = 0;
								String light1 = "tblightrow";
								String dark1 = "tbdarkrow";
								if(!(ngaydh == null)){
									try{while(ngaydh.next()){ 
										if (k % 2 != 0) {%>
										<TR class=<%=light1%>>
											<%} else {%>
										<!-- ngaydhSelected -->
										<TR class=<%= dark1%>>
											<%}%>
											<TD align="center"><div align="left"><%= ngaydh.getString("ngay") %>
												</div></TD>
											<TD align="center">
												<% if(ngaydhSelected.indexOf(ngaydh.getString("pk_seq"))>=0){ %>
												<input name="ngaydhList"
												id="ngaydhList" type="checkbox"
												value="<%= ngaydh.getString("pk_seq")%>" checked>
												<% } else{ %>
													<input name="ngaydhList"
												id="ngaydhList" type="checkbox"
												value="<%= ngaydh.getString("pk_seq")%>">
												<%} %>
												</TD>
										</TR>
										<%k++;}}catch(java.sql.SQLException e){}
								}
								  %>
									</TABLE>
								</FIELDSET> --%>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
<%nppBean.DBclose() ;%>
</HTML>
<%}%>