<%@page import="geso.dms.center.beans.phieuxuatkhott.IPhieuXuatKhoTT_SP"%>
<%@page import="geso.dms.center.beans.phieuxuatkhott.IPhieuXuatKhoTT"%>
<%@page import="geso.dms.center.beans.nhaphanphoi.INhaphanphoiList"%>
<%@page import="geso.dms.center.beans.hoadon.IHoaDon_SanPham"%>
<%@page import="geso.dms.center.beans.hoadon.IHoaDon"%>
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
<% IPhieuXuatKhoTT phieuxuat=(IPhieuXuatKhoTT)session.getAttribute("phieuxuat"); %>
<% IHoaDon hdBean = (IHoaDon)session.getAttribute("obj"); %>
<% List<IPhieuXuatKhoTT_SP> splist = (List<IPhieuXuatKhoTT_SP>)phieuxuat.getListSanPham(); %>
<% String userId = (String)session.getAttribute("userId"); %>

<%	String userTen = (String)session.getAttribute("userTen"); %>
<%	ResultSet rs_nhacc=(ResultSet)session.getAttribute("rs_nhacc");
	ResultSet rs_dondathang=(ResultSet)session.getAttribute("rs_dondathang");
	ResultSet rs_khott=(ResultSet)session.getAttribute("rs_khott");
	ResultSet rs_nhapp=(ResultSet)session.getAttribute("rs_nhapp");
	ResultSet rs_kenhbanhang=(ResultSet)session.getAttribute("rs_kenhbanhang");
	ResultSet rs_dvkd=(ResultSet)session.getAttribute("rs_dvkd");
	String diachinhanhang=(String)session.getAttribute("diachinhanhang");
	INhaphanphoiList infonpp=hdBean.getInfoNhaPhoiPhoi();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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
		 $('.addspeech').speechbubble();})
</script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_bangianpp-mua.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>

<script language="javascript" type="text/javascript">

	function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }

	
	
	function saveform(){
				document.forms['dhForm'].action.value='chot';
		 document.forms['dhForm'].submit();
	}
		
		
</script>


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

<form name="dhForm" method="post" action="../../PhieuXuatKhoTTNewSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type="hidden" name="id" value='<%=phieuxuat.getID()%>'>
<input type="hidden" name="action" value='diplay'>
<INPUT type="hidden" name="tenform" value='diplayformform'>   
   
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
				<TABLE border =0 width = "100%" cellpadding="2" cellspacing="0">
				<TBODY>
					<TR height="22">
						<TD align="left" >
							<TABLE width="100%" cellpadding="0" cellspacing="0" >
								<TR>
									<TD align="left">
									   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										   <TR height="22">
			 								   <TD align="left"  class="tbnavigation">&nbsp;Quản lý bán hàng> In phiếu xuất kho > Chốt phiếu xuất kho </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp; </TD>
					    				 </TR>
									  </TABLE>
								  </TD>
							  </TR>	
						  	</TABLE>
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
								<TR ><TD >
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class = "tbdarkrow">
											<TD width="30" align="left"><A href = "javascript:history.back()" ><img src="../images/Back30.png" alt="Quay về"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
										    <TD width="30" align="left" ><A href="javascript:saveform()" ><img src="../images/Chot.png" alt="Chốt Đơn hàng"  title="Chốt Đơn Hàng" border="1" longdesc="Luu lai" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
							    			<TD width="30" align="left"><A href="Print.jsp" ><img src="../images/Printer30.png" alt="In" title="In chung tu" longdesc="In Chứng từ" border=1 style="border-style:outset"></A></TD>
								    		<TD align="left" >&nbsp;</TD>
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="1" width="100%" cellpadding = "1" cellspacing = "0" style="border-color:gray;" >
								<tr>
								
								 <TD align="left" class="legendtitle">
									<fieldset >
									<legend> Báo lỗi nhập liệu</legend>
				    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%;margin:0px " readonly="readonly" rows="1"><%=phieuxuat.getMessage() %></textarea>
							  	</fieldset>
							  	 </TD>
								</tr>
								
								<TR class="plainlabel">
									<TD align="left" class="khoatest">						
										
										<TABLE class="khoatest" border="0" bordercolor="white" width="100%" cellpadding = "1" cellspacing = "2" style="padding-left:2px ;" >
											<tr class="plainlabel">
											<th width='15%' ></th>
											<th width='35%' ></th>
											<th width='15%' ></th>
											<th width='35%' ></th>
											</tr>
											<tr class="plainlabel">
											<td>
											Chọn đơn đặt hàng
											</td>
											<td>
											 <input type="text" name="ddhid" style="width:100%" readonly="readonly" value="<%=phieuxuat.getDonDatHangID()  %>" >
											</td>
											<td>
											Ngày xuất kho 
											</td>
											<td>
											<table border=0 cellpadding="0" cellspacing="0">
                                                <TR>
                                                <TD>
											    <input type="text" readonly="readonly" name="ngayxuatkho" style="width:100%" value="<%= phieuxuat.getNgaylap()%>" >
											    </TD>
											     <TD>
												<a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'dhForm.ngayxuatkho',false, 'yyyy-mm-dd'); return false;">
		                                          &nbsp;<img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>
											    </TD>
										     	</TR>
                                          	</table>
											</td>
											</tr>
											<tr class="plainlabel">
											<td>
											Lý do xuất 
											</td>
											<td >
											 <input type="text" readonly="readonly" name="lydoxuat" style="width:100%" value="<%= phieuxuat.getLyDoXuat() %>" >
											</td>
										
											<td>
											Ghi chú 
											</td>
											<td >
											 <input type="text" readonly="readonly"  name="ghichu" style="width:100%" value="<%= phieuxuat.getGhiChu() %>" >
											</td>
											</tr>
											<tr class="plainlabel" >
											<td >Nhà cung cấp  </td>
											<td >
												<select name='nhaccid' style="width: 100%" disabled="disabled">
												 <option value="" ></option>
													  <% if(rs_nhacc != null){
														  try{ while(rs_nhacc.next()){ 													 
											    			if(rs_nhacc.getString("pk_seq").trim().equals(hdBean.getIdNhaCungCap())){ %>
											      				<option value='<%=rs_nhacc.getString("pk_seq")%>' selected><%=rs_nhacc.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=rs_nhacc.getString("pk_seq")%>'><%=rs_nhacc.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select>	
											</td>
											<td > Chọn DVKD </td>
											<td>
											 <select name='dvkdid' style="width: 100%" disabled="disabled">
												 <option value="" ></option>
													  <% if(rs_dvkd != null){
														  try{ while(rs_dvkd.next()){ 													 
											    			if(rs_dvkd.getString("pk_seq").trim().equals(hdBean.getIdDVKD())){ %>
											      				<option value='<%=rs_dvkd.getString("pk_seq")%>' selected><%=rs_dvkd.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=rs_dvkd.getString("pk_seq")%>'><%=rs_dvkd.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select>	
											</td>
											</tr>
											<TR class="plainlabel">
											  <TD class="plainlabel">Ngày giao dịch </TD>
											  <TD class="plainlabel">
											   <input type="text" name="ngaygiaodich" style="width:100%" readonly="readonly" value="<%= hdBean.getNgaygiaodich() %>" >
                                          	</TD>
                                          	<td>
                                          	Kho xuất hàng 
                                          	</td>
                                          	<td>
                                              	<select  name='khottid' style="width: 100%" disabled="disabled" >
												 <option value=""></option>
													  <% if(rs_khott != null){
														  try{ while(rs_khott.next()){ 													 
											    			if(rs_khott.getString("pk_seq").trim().equals(hdBean.getKhottId())){ %>
											      				<option value='<%=rs_khott.getString("pk_seq")%>' selected><%=rs_khott.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=rs_khott.getString("pk_seq")%>'><%=rs_khott.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select>
                                          	</td>
                                          	</TR>
											<TR class="plainlabel" >
												<TD >Nhà phân phối </TD>
												<td >
													<select name='nhappid' style="width: 100%" disabled="disabled">
												 <option value=""> </option>
													  <% if(rs_nhapp != null){						  
														  try{ while(rs_nhapp.next()){ 
											    			if(rs_nhapp.getString("pk_seq").trim().equals(hdBean.getNppId())){ %>
											      				<option value='<%=rs_nhapp.getString("pk_seq")%>' selected><%=rs_nhapp.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=rs_nhapp.getString("pk_seq")%>'><%=rs_nhapp.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select> 
												</td>
												<td>
												Số SO
												</td>
												<td>
												<input type='text' readonly="readonly"   name='soso' style="width:100%" value="<%=hdBean.getSoSO()%> ">
												</td>
											</TR>
											<tr class="plainlabel">
											<td>Địa chỉ nhận hàng </td>
											<td >
											<input type='text'  readonly="readonly" name="diachinhanhang" style="width:100%" value="<%=infonpp.getDiaChi() %>">
											 
											</td>
											<td>Kênh bán hàng</td>
									          <td>
									          <select name='kenhbanhang' style="width: 100%" disabled="disabled">
												 <option value=""></option>
													  <% if(rs_kenhbanhang != null){
														  try{ while(rs_kenhbanhang.next()){ 				
											    			if(rs_kenhbanhang.getString("pk_seq").trim().equals(hdBean.getIDKenhBanHang())){ %>
											      				<option value='<%=rs_kenhbanhang.getString("pk_seq")%>' selected><%=rs_kenhbanhang.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=rs_kenhbanhang.getString("pk_seq")%>'><%=rs_kenhbanhang.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select> 
									          </td>
											
											</tr>
											<tr class="plainlabel">
											<td>Điện thoại</td>
											<td><input type='text' readonly="readonly"  name="dienthoai" style="width:100%" value="<%=infonpp.getSodienthoai()%>" ></td>
											<td></td>
											<td></td>
											</tr>

										</TABLE>
								
								  </TD>

							   </TR>	
							   
							   <TR>
							   		<TD>
										<TABLE width = "100%" border="0" cellpadding="0" cellspacing="1">
										<tbody id="san_pham">
												<TR class="tbheader" >
													<TH width="15%" height="20">Mã sản phẩm </TH>
													<TH width="28%">Tên sản phẩm</TH>
													<TH width="5%">DVT</TH>
												    <TH width="5%">Quy cách</TH>
													<TH width="8%">Số lượng</TH>
													<TH width="9%">Thùng </TH>	
													<TH width="9%">Lẻ </TH>		
												</TR>
									<% 
										if(splist != null){
									IPhieuXuatKhoTT_SP sanpham;
								int size = splist.size();
								int m = 0;
								while (m < size){
								sanpham = splist.get(m); 
								%>
									<TR class= 'tblightrow' >
									<TD align="left" >
										<input name="masp" readonly="readonly" type="text" value="<%=sanpham.getMaSanPham()%>" style="width:100%">
									</TD>
									<TD align="left" >
										<input name="tensp" type="text" readonly value="<%=sanpham.getTenSanPham()%>" style="width:100%" ></TD>
								    <TD align = "center" ><input name="donvitinh" type="text" value="<%= sanpham.getDonViTinh() %>" readonly style="width:100%"></TD>
								   	
								    <TD align = "left" >
								        <input name="quycach" readonly="readonly" type="text" value="<%= sanpham.getQuyCach() %>"  style="text-align:left;width:100%;">
								    </TD>
								    	<td> <input type="text" name="soluong" value="<%= sanpham.getSoLuong() %>" readonly style="width:100% ;text-align:right">		
								    </TD>
								    <TD align = "center" ><input name="soluongqydoi" type="text" value=" <%=sanpham.getSoLuongQuyDoi() %>" readonly="readonly"   style="width:100% ;text-align:right"></TD>
								    <TD align = "center" ><input name="le" type="text" value="<%=Math.round(sanpham.getLe()) %>" readonly  style="width:100% ;text-align:right"></TD>
								</TR>
								<% m++; }}%>		
								</tbody>
								</TABLE>
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
</script>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

