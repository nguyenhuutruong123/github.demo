<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.donhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<% IDonhang dhBean = (IDonhang)session.getAttribute("dhBean"); %>
<% List<ISanpham> splist = (List<ISanpham>)dhBean.getSpList(); %>
<% ResultSet ddkd = (ResultSet)dhBean.getDdkdList(); %>
<% ResultSet tbh = (ResultSet)dhBean.getTbhList(); %>
<% ResultSet kh = (ResultSet)dhBean.getKhList(); %>


<% Hashtable<String, Integer> spThieuList = dhBean.getSpThieuList(); %>

<% Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); %>
<% Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); %>
<% List<ISanpham> scheme_sanpham = (List<ISanpham>)dhBean.getScheme_SpList(); %>
<% ResultSet kho = (ResultSet)dhBean.getKhoList(); %>
<% ResultSet gsbhs = (ResultSet)dhBean.getgsbhs(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
	#dhtmltooltip
	{
		position: absolute;
		left: -300px;
		width: 150px;
		border: 1px solid black;
		padding: 2px;
		background-color: lightyellow;
		visibility: hidden;
		z-index: 100;
		/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
		filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
	}	
	#dhtmlpointer
	{
		position:absolute;
		left: -300px;
		z-index: 101;
		visibility: hidden;
	}	
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();
	})
</script>

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax-dynamic-list.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>

<script language="javascript" type="text/javascript">
function replaces()
{
	var masp = document.getElementsByName("masp");
	var tensp = document.getElementsByName("tensp");
	var donvitinh = document.getElementsByName("donvitinh");
	var dongia = document.getElementsByName("dongia");
	var chietkhau = document.getElementsByName("spchietkhau");
	var soluong = document.getElementsByName("soluong");
	var soluongOld = document.getElementsByName("soluongOld");
	var thanhtien = document.getElementsByName("thanhtien");
	var ckId = document.getElementById("ChietKhau");
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
				sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
				tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
				sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
				donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				dongia.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
			}
			if(soluong.item(i) != null)
			{
				if(soluong.item(i).value != "")					
				{
					var don_gia = dongia.item(i).value;
					//don_gia = don_gia.replace(".", "");
					//tinh chiet khau theo san pham
					var tck = (parseFloat(ckId.value) * parseFloat(soluong.item(i).value) * parseFloat(don_gia)) / 100;
					chietkhau.item(i).value = tck; 
					//var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia) - parseFloat(chietkhau.item(i).value);
					var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia);
					thanhtien.item(i).value = tt;
					TinhTien();
				}
				else			
					thanhtien.item(i).value = "";
			}
	
			if(checkMasp(masp.item(i).value) == true)
			{
				masp.item(i).parentNode.parentNode.bgColor = "#9FC";
			}
		}
		else
		{
			tensp.item(i).value = "";
			donvitinh.item(i).value = "";
			dongia.item(i).value = "";
			chietkhau.item(i).value = "";
			soluong.item(i).value = "";
			soluongOld.item(i).value = "0";
			thanhtien.item(i).value = "";

			TinhTien();
		}
	}
	setTimeout(replaces, 20);
	}
	
	replaces();
	
	function TinhTien()
	{
		var thanhtien = document.getElementsByName("thanhtien");
		//var chietkhau = document.getElementsByName("ChietKhau");
		var tongtien = 0;
		for(var i=0; i < thanhtien.length; i++)
		{
			if(thanhtien.item(i).value != "")
			{
				//var thanh_tien = thanhtien.item(i).value.replace(".", "");
				var thanh_tien = thanhtien.item(i).value;
				tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
			}
		}
		tongtien = parseFloat(tongtien) + parseFloat(Tongtienkhuyenmai());
//		document.getElementById("SoTienChuaCK").value = parseFloat(tongtien);
		var ck = document.getElementById("ChietKhau").value;
		if(ck == "")
			ck = "0";
		var tienchietkhau = ((tongtien * parseFloat(ck)) / 100) + parseFloat(TongchietkhauKM());
		document.getElementById("TienChietKhau").value =Math.round(tienchietkhau);
		var tienchuaVAT = tongtien - tienchietkhau;
		document.getElementById("SoTienChuaVAT").value =Math.round(tienchuaVAT);
		var vat = document.getElementById("VAT").value;
		if(vat == "")
			vat = "10";
		var sotiencovat=(parseFloat(vat) * tienchuaVAT) / 100 + tienchuaVAT;
		document.getElementById("SoTienCoVAT").value = Math.round(sotiencovat);
		
	}
	
	function Tongtienkhuyenmai()
	{
		var ttTrakm = document.getElementsByName("ttTrakm");
		var sum = 0;
		if(ttTrakm.length > 0)
		{
			for(h =0; h < ttTrakm.length; h++)
			{
				if(ttTrakm.item(h).value != "")
					sum = parseFloat(sum) + parseFloat(ttTrakm.item(h).value);
			}
		}
		return sum;
	}
	
	function TongchietkhauKM()
	{
		var ckTrakm = document.getElementsByName("ckTrakm");
		var sum = 0;
		if(ckTrakm.length > 0)
		{
			for(h =0; h < ckTrakm.length; h++)
			{
				if(ckTrakm.item(h).value != "")
					sum = parseFloat(sum) + parseFloat(ckTrakm.item(h).value);
			}
		}
		return sum;
	}
	
	function checkMasp(masanpham)
	{
		var masp = document.getElementsByName("masp");
		for(i = 0; i < masp.length ; i++)
		{
			if(masp.item(i).value == masanpham)
				return true;
		}
		return false;
	}
	
	function CheckSoluongOld()
	{
		 var trangthaidh = document.getElementById("trangthaiDh").value;
		 if(trangthaidh == 3)
		 {
			 var soluong = document.getElementsByName("soluong");
			 var soluongOld = document.getElementsByName("soluongOld");
			 
			 for(i = 0; i < soluong.length ; i++)
			 {
				if(parseInt(soluong.item(i).value) > parseInt(soluongOld.item(i).value))
				{
				    alert("Đơn hàng đã xuất kho, Bạn không thể nhập số lượng lớn hơn, Vui lòng nhập lại...");
				    soluong.item(i).value = soluongOld.item(i).value;
					return;
				}
			 }			
		 }
	}
	
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	
	 function submitform()
	 { 
		congDonSPCungMa();
		document.forms['dhForm'].action.value='submit';
	    document.forms["dhForm"].submit();
	 }
	 
</script>


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

<form name="dhForm" method="post" action="../../DonhangUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="nppId" value='<%= dhBean.getNppId() %>'>
<input type="hidden" name="action" value='1'>
<INPUT type="hidden" name="id" value='<%= dhBean.getId() %>'>
<INPUT type="hidden" name="trangthai" id="trangthaiDh" value='<%= dhBean.getTrangthai() %>'>
<INPUT type="hidden" name="khoTen" value='<%= dhBean.getKhoId() %>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
				<TABLE border =0 width = "100%" cellpadding="2" cellspacing="0">
				<TBODY>
					<TR height="22">
						<TD align="left" >
							<TABLE width="100%" cellpadding="0" cellspacing="0">
								<TR>
									<TD align="left">
									   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										   <TR height="22">
			 								   <TD align="left"  class="tbnavigation">&nbsp;Quản lý bán hàng > Đơn hàng bán > Hiển thị </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng bạn  <%= dhBean.getNppTen() %> &nbsp; </TD>
					    				 </TR>
									  </TABLE>
								  </TD>
							  </TR>	
						  	</TABLE>
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
								<TR ><TD >
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class = "tbdarkrow">
											<TD width="30" align="left"><A href = "javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
							    			<TD width="30" align="left"><A href = "../../InDonHangpdfSvl?userId=<%=userId %>&display=<%=dhBean.getId() %>" ><img src="../images/Printer30.png" alt="In" title="In chung tu" longdesc="In chung tu" border=1 style="border-style:outset"></A></TD>
								    		<TD align="left" >&nbsp;</TD>
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="0" width="100%" cellpadding = "0" cellspacing = "0">
								<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>			
				    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" rows="1" readonly="readonly"><%= dhBean.getMessage() %></textarea>
									</FIELDSET>
							   </TD>
								</tr>
								<TR>
									<TD  align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Đơn hàng bán </LEGEND>
										<TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>
											<TR >
											  <TD class="plainlabel">Ngày giao dịch </TD>
											  <TD colspan="3" class="plainlabel">
											  <table border=0 cellpadding="0" cellspacing="0">
                                                <TR><TD>
											    <input type="text" name="tungay" size="20" value="<%= dhBean.getNgaygiaodich() %>" readonly >
											</TD><TD>
											</TD></TR>
                                          </table></TR>
											<TR >
												<TD width="22%" class="plainlabel">Nhà phân phối </TD>
												<TD colspan="3" class="plainlabel"><%= dhBean.getNppTen() %> </TD></TR>		
												<TR class="tblightrow">
												<TD  class="plainlabel">Giám sát bán hàng </TD>
												<TD colspan="3" class="plainlabel"> 
									 			<SELECT name="gsbhId" id="gsbhId" onChange = "submitform();">
										 			 <option value=""></option>
													  <% if(gsbhs != null){
														  try{ while(gsbhs.next()){ 													 
											    			if(gsbhs.getString("pk_seq").equals(dhBean.getGsbhId())){ %>
											      				<option value='<%=gsbhs.getString("pk_seq")%>' selected><%=gsbhs.getString("ten") %></option>
											      			<%}else{ %>
											     				<%}}}catch(java.sql.SQLException e){}} %>	 
                                    			</SELECT></TD>
											</TR>					
											<TR class="tblightrow">
												<TD  class="plainlabel">Nhân viên bán hàng </TD>
												<TD colspan="3" class="plainlabel"> 
									 			<SELECT name="ddkdTen" id="ddkdTen">
										 			 <option value=""> </option>
													  <% if(ddkd != null){
														  try{ while(ddkd.next()){ 													 
											    			if(ddkd.getString("ddkdId").equals(dhBean.getDdkdId())){ %>
											      				<option value='<%=ddkd.getString("ddkdId")%>' selected><%=ddkd.getString("ddkdTen") %></option>
											      			<%}else{ %>
											     				<%}}}catch(java.sql.SQLException e){}} %>	 
                                    			</SELECT></TD>
											</TR>
											<TR class="tblightrow">
												<TD  class="plainlabel">Tuyến bán hàng </TD>
												<TD colspan="3"  class="plainlabel"> 
									 			<SELECT name="tbhTen">
										 			 <option value="">&nbsp;&nbsp;</option>
													  <% if(tbh != null){
														  try{ while(tbh.next()){ 
											    			if(tbh.getString("tbhId").equals(dhBean.getTbhId())){ %>
											      				<option value='<%=tbh.getString("tbhId")%>' selected><%=tbh.getString("tbhTen") %></option>
											      			<%}else{ %>
											     				<%}}}catch(java.sql.SQLException e){} }%>	 
                                    			</SELECT></TD>
											</TR>
											<TR class="tblightrow">
												<TD  class="plainlabel">Khách hàng </TD>
												<TD colspan="3"  class="plainlabel"> 
									 			<SELECT name="khTen" id="khTen">
										 			 <option value="">&nbsp;</option>
													  <% if(kh != null){
														  try{ while(kh.next()){ 
											    			if(kh.getString("khId").equals(dhBean.getKhId())){ %>
											      				<option value='<%= kh.getString("khId") + " - " + kh.getString("chietkhau") %>' selected onMouseover="ddrivetip('<%= "Dia chi: " + kh.getString("diachi") %>', 300)"; onMouseout="hideddrivetip()"><%= kh.getString("khTen") + " " %></option>
											      			<%}else{ %>
											     			<%}}}catch(java.sql.SQLException e){} }%>
                                    			</SELECT></TD>
											</TR>
											<TR class="tblightrow">
												<TD  class="plainlabel">Kho hàng </TD>
												<TD colspan="3" class="plainlabel"> 
									 			<SELECT name="khoTen" id="khoTen">
										 			 <option value="">&nbsp;</option>
													  <% if(kho != null){
														  try{ while(kho.next()){ 
											    			if(kho.getString("khoId").equals(dhBean.getKhoId())){ %>
											      				<option value='<%= kho.getString("khoId") %>' selected onMouseover="ddrivetip('<%= kho.getString("diengiai") %>', 300)"; onMouseout="hideddrivetip()"><%= kho.getString("Ten") + " " %></option>
											      			<%}else{ %>
											     				<%}}}catch(java.sql.SQLException e){} }%>
                                    			</SELECT></TD>
											</TR>
											
											</TR>
											  <TR class="tblightrow">
											    <TD  class="plainlabel">Tổng số tiền (trước VAT) </TD>	     
										        <TD colspan="3"  class="plainlabel"><input name="SoTienChuaVAT" id="SoTienChuaVAT" type="text" value="<%=dhBean.getTongtientruocVAT()%>" readonly > VND </TD>
									       	</TR>

											  <TR class="tblightrow">
											    <TD  class="plainlabel">% Chiết khấu (khách hàng) </TD>
											    <TD width="9%"  class="plainlabel"><input name="ChietKhau" id="ChietKhau" type="text" value="<%= dhBean.getChietkhau() %>" readonly></TD>
									            
									            <TD width="19%" class="plainlabel" align="right">Tổng chiết khấu</TD>
									            <TD width="72%" class="plainlabel"><input name="TienChietKhau" id="TienChietKhau" type="text" readonly value="<%= dhBean.getTongChietKhau() %>"> VND </TD>
										   	</TR>

											<TR class="tblightrow">
											  <TD  class="plainlabel">Tổng số tiền (Sau chiết khấu)</TD>
											  <TD colspan="3" class="plainlabel"><input name="SoTienSauCK" id="SoTienSauCK" type="text" readonly value="<%=dhBean.getTongtiensauCK()%>" > VND </TD>
										  	</TR>

										    <TR class="tblightrow">
											  <TD  class="plainlabel">VAT (%) </TD>
											  <TD colspan="3"  class="plainlabel"><input name="VAT" id="VAT" type="text" value="<%= dhBean.getVAT() %>" readonly>%</TD>
										  	</TR>

											<TR class="tblightrow">
											  <TD  class="plainlabel">Tổng số tiền (sau VAT)</TD>
											  <TD colspan="3"  class="plainlabel">
											  	<input name="SoTienCoVAT" id="SoTienCoVAT" type="text" readonly value="<%= dhBean.getTongtiensauVAT()%>">
									          VND </TD>
										  	</TR>

											<TR class="tblightrow">
											  <TD  class="plainlabel">Tổng số tiền chiết khấu KM</TD>
											  <TD colspan="3"  class="plainlabel">
											  	<input name="SoTienCKKM" id="SoTienCKKM" type="text" readonly value="<%= dhBean.getTongtienCKKM() %>">
									          VND </TD>
										  	</TR>

												
											<TR class="tblightrow">
											  <TD  class="plainlabel">Tổng số tiền (sau chiết khấu KM)</TD>
											  <TD colspan="3"  class="plainlabel">
											  	<input name="SoTienSauCKKM" id="SoTienSauCKKM" type="text" readonly value="<%= dhBean.getTongtiensauCKKM() %>">
									          VND </TD>
										  	</TR>
											
												
										</TABLE>
									</FIELDSET>
								  </TD>
							   </TR>	
							   <TR>
							   		<TD>
										<TABLE class="tabledetail" width = "100%" border="0" cellpadding="0" cellspacing="1">
										<tbody id="san_pham">
												<TR class="tbheader" >
													<TH width="15%">Mã sản phẩm </TH>
													<TH width="25%">Tên sản phẩm </TH>
													<TH width="10%">Số lượng </TH>
													<TH width="7%">DVT</TH>
													<TH width="10%">Đơn giá </TH>
													<TH width="10%">Chiết khấu </TH>
													<TH width="10%">Thành tiền </TH>
													<TH width="13%" >CTKM</TH>
												</TR>
									<% 
							if(splist != null){
							ISanpham sanpham = null;
							int size = splist.size();
							int m = 0;
							while (m < size){
								sanpham = splist.get(m); 
								%>
									<TR class= 'tblightrow' >
									<TD align="left" >
									<% if(!dhBean.getTrangthai().equals("3")){ %>
										<input name="masp" type="text" value="<%=sanpham.getMasanpham()%>" style="width:100%" readonly>
									<%} else{ %>
										<input name="masp" type="text" value="<%=sanpham.getMasanpham()%>" style="width:100%"  readonly>
									<%} %>
									</TD>
									<TD align="left" >
										<input name="tensp" type="text" readonly value="<%=sanpham.getTensanpham()%>" style="width :100%"></TD>
																		
						        	<% if (spThieuList.containsKey(sanpham.getMasanpham())){ %>
									    <TD align = "center" ><div class="addspeech" title="San pham nay con toi da <%= spThieuList.get(sanpham.getMasanpham()) %> san pham, vui long chon lai so luong">
									        <input name="soluong" type="text" value="<%= sanpham.getSoluong() %>"  onkeypress="return keypress(event);" onkeyup="CheckSoluongOld()" style="width:100%; color:#F00; cursor:pointer; background-color:#0FF; text-align:right">
									        <input name="soluongOld" type="hidden" value="<%= sanpham.getSoluong() %>" >
								        </div></TD>
								    <%}else{ %>
							        	<TD align = "center" >
								        	<input name="soluong" type="text" value="<%= sanpham.getSoluong() %>" readonly style="text-align:right;width:100%">
								        	<input name="soluongOld" type="hidden" value="<%= sanpham.getSoluong() %>" >
								        </TD>
								    <%}%>
								    									    
								    <TD align = "center" ><input name="donvitinh" type="text" value="<%= sanpham.getDonvitinh() %>" readonly  style="text-align:center;width:100%"></TD>
								    <TD align = "center" >
								    	<input type="text" name="dongia" value="<%= sanpham.getDongia() %>" readonly  style="text-align:right;width:100%">
								    </TD>
								    <TD align = "center" ><input name="spchietkhau" type="text" value="" readonly  style="text-align:right;width:100%"></TD>
								    <TD align = "center" ><input name="thanhtien" type="text" value="" readonly  style="text-align:right;width:100%"></TD>
								    <TD align = "center" ><input name="ctkm" type="text"  value="<%= sanpham.getCTKM() %>"  readonly style ="width:100%"></TD>
								</TR>
								<% m++; }}%>
								
								<%if(scheme_tongtien.size() > 0)
								{
									Enumeration<String> keys = scheme_tongtien.keys();
									while(keys.hasMoreElements())
									{
										String key = keys.nextElement(); %>
										<TR class= 'tblightrow'>
											<TD align="center" ><input type="text" style="width:100%"  readonly></TD>
											<TD align="left" ><input type="text" readonly style="width:100%" ></TD>
										    <TD align = "center" ><input type="text" value="" style="width:100%"  readonly></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input name="ttTrakm" type="text" value="<%= "-" + Float.toString(scheme_tongtien.get(key)) %>" readonly style="width:100%" style="text-align:right"></TD>
										    <TD align = "center" ><input name="trakmScheme" type="text" value="<%= key %>" style="width:100%" readonly></TD>
										</TR>
								<%}}%>						
								<%if(scheme_chietkhau.size() > 0)
								{
									Float tonggiatri = Float.parseFloat(dhBean.getTongtiensauVAT());
									Enumeration<String> keyss = scheme_chietkhau.keys();
									while(keyss.hasMoreElements())
									{
										String key = keyss.nextElement(); 
										//Float chietkhau = (scheme_chietkhau.get(key) / 100) * tonggiatri; 
										long chietkhau = Math.round(scheme_chietkhau.get(key)); %>
										<TR class= 'tblightrow'>
											<TD align="center" ><input type="text" style="width:100%" readonly></TD>
											<TD align="left" ><input type="text" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input name="ckTrakm"  type="text" value="<%= Float.toString(chietkhau) %>" readonly style="text-align:right; width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input name="trakmScheme" type="text" style="width:100%" value="<%= key %>"  readonly></TD>
										</TR>
								<%}}%>								
								<%if(scheme_sanpham.size() > 0)
								{
									ISanpham tkmSp = null;
									int count = 0;
									while(count < scheme_sanpham.size())
									{
										tkmSp = scheme_sanpham.get(count); %>
										<TR class= 'tblightrow'>
											<TD align="center" >
												<input name="maspTrakm" type="text" value="<%= tkmSp.getMasanpham() %>" style="width:100%" readonly>
											</TD>
											<TD align="left" >
												<input name="tenspTraKm" type="text" readonly value="<%= tkmSp.getTensanpham() %>" style="width:100%"></TD>
										    <TD align = "center" >
									        <input name="slgTraKm" type="text" value="<%= tkmSp.getSoluong() %>" style="text-align:right; width:100%" readonly></TD>
										    <TD align = "center" ><input name="dvtTrakm" type="text" value="" readonly style="text-align:right; width:100%"></TD>
										    <TD align = "center" ><input name="dgTrakm" type="text" value="0" readonly  style="text-align:right; width:100%"></TD>
										    <TD align = "center" ><input name="" type="text" value="0" readonly style="text-align:right ;width:100%" ></TD>
										    <TD align = "center" ><input name="" type="text" value="0" readonly style="text-align:right; width:100%" ></TD>
										    <TD align = "center" ><input name="trakmScheme" type="text" value="<%= tkmSp.getCTKM() %>" readonly style="width:100%" ></TD>
										</TR>
								<% count++; }}%>											
								<TR><TD colspan="8" class="tbfooter">&nbsp;</TD></TR>																																																																																																															
								</tbody></TABLE>							
								</TD>
							  </TR> 
						  </TABLE>
						</TD>
					</TR>				
				</TBODY>
			</TABLE>
	</td>
  </tr>

</TABLE>
<script type="text/javascript">
	//Call dropdowncontent.init("anchorID", "positionString", glideduration, "revealBehavior") at the end of the page:
	dropdowncontent.init("searchlink", "right-bottom", 500, "click");
</script>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
	if(!(ddkd == null))
		ddkd.close();
	if(!(tbh == null))
		tbh.close();
	if(dhBean != null){
		dhBean.DBclose();
		dhBean = null;
	} 
	
	}
%>

