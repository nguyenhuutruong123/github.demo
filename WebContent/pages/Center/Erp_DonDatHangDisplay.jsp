<%@page import="geso.dms.center.beans.donmuahang.IERP_DonDatHang_SP"%>
<%@page import="geso.dms.center.beans.donmuahang.IERP_DonDatHang"%>
<%@page import="java.util.Formatter"%>
<%@page import="java.util.Formattable"%>
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
<% IERP_DonDatHang hdBean = (IERP_DonDatHang)session.getAttribute("obj"); %>
<% List<IERP_DonDatHang_SP> splist = (List<IERP_DonDatHang_SP>)hdBean.getListSanPham(); %>
<% String userId = (String)session.getAttribute("userId");
String userTen=(String)session.getAttribute("userTen");
%>
<% Hashtable<String, Integer> spThieuList = hdBean.getSpThieuList(); %>
<%	ResultSet rs_nhacc=hdBean.GetRsnhacc();
	
	ResultSet rs_khott=(ResultSet)hdBean.GetRskhott();
	
	ResultSet rs_nhapp=hdBean.GetRsnhapp();
	
	ResultSet rs_kenhbanhang=hdBean.GetRsKbh();
	
	ResultSet rs_dvkd=hdBean.getrsdvkd();
	NumberFormat formatter=new DecimalFormat("#,###,###"); 
	NumberFormat formatter2=new DecimalFormat("#,###,###.##"); 
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
#tabc tbody tr input:HOVER{
	background: #C5E8CD;
}
#tabc tbody tr:hover{
	background: #C5E8CD;
}
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
<!-- <script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script> -->
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_erp_bgmuanpp.js"></script>
<script type="text/javascript" src="../scripts/DocTienTiengViet.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/formattien.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script language="javascript" type="text/javascript">

function replaces()
{
	var manpp=document.getElementById("nhappid");
	if(manpp.value.indexOf(" -- ") >0){
		var str = manpp.value;
		manpp.value = str.substring(0, manpp.value.indexOf(" -- "));
		document.getElementById("tennpp").value =str.substring(str.indexOf(" -- ") + 4);
		loadcontent();
	}
	
	var masp = document.getElementsByName("masp");
	var tensp = document.getElementsByName("tensp");
	var donvitinh = document.getElementsByName("donvitinh");
	var dongia = document.getElementsByName("dongia");
	var chietkhau = document.getElementsByName("chietkhau");
	var soluong = document.getElementsByName("soluongduyet");
	var thanhtien = document.getElementsByName("thanhtien");
	var tonkho = document.getElementsByName("tonkho");

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
				var valuegia= sp.substring(0, parseInt(sp.indexOf("] [")));
				dongia.item(i).value =formattien(valuegia);
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
			    tonkho.item(i).value= sp.substring(0, parseInt(sp.indexOf("]")));
			}
			if(parseInt(soluong.item(i).value) > 0)
			{				
				var don_gia = dongia.item(i).value;
				while(don_gia.match(",")){
					don_gia=don_gia.replace(",","");
					}
				
			    
			
				//var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia) - parseFloat(chietkhau.item(i).value);
				var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia);
				thanhtien.item(i).value = formattien(tt);
			}
			else			
			{
				
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
		
			soluong.item(i).value = "";
			thanhtien.item(i).value = "";
			tonkho.item(i).value = "";
			
		}
		//TinhTien();
	}	
	
	}


	
function TinhTien()
{
	var thanhtien = document.getElementsByName("thanhtien");
	var  chietkhau=document.getElementsByName("chietkhau");
	var tongtien = 0;
	var tongtienck=0;
	for(var i=0; i < thanhtien.length; i++)
	{
		if(thanhtien.item(i).value != "")
		{
			var thanh_tien = thanhtien.item(i).value.replace(",", "");
			
			;
			
			//var thanh_tien = thanhtien.item(i).value;
			tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
			
		}
	}
	
	
	//document.forms['dhForm'].SoTienChuaVAT.value=tongtien;
	document.getElementById("SoTienChuaVat").value=formattien(tongtien);
	
	var chietkhau=document.getElementById("chietkhau").value;
	
	//var loaick= document.getElementById("loaick").checked;
	
	
	
	tienck= document.getElementById("chietkhau").value;
	
	if(tienck==""){
		tienck=0;
	}
	
	while(tienck.match(",")){
		tienck=tienck.replace(",","");
		}
	var sotienck=tienck;
	
	/* if(loaick != false){
			
		sotienck= parseFloat(tongtien)* parseFloat(tienck)/100;
	
		}
	 */
		
	tongtien=	parseFloat(tongtien)-parseFloat(sotienck);
	
	var vat = document.getElementById("VAT").value;
	if(vat == "")
		vat = "10";
	var tiencothue=(parseFloat(vat) * tongtien) / 100 + tongtien;
	document.getElementById("SoTienCoVAT").value =formattien( Math.round(tiencothue));
	
	
	
  	
   		
   		//KiemTraGioiHangCongNo();
}

		function getinfoddh(){
			var sotienavat = document.getElementById("SoTienCoVAT").value;
			while(sotienavat.match(",")){
				sotienavat=sotienavat.replace(",","");
				}
			document.getElementById("doctien").value=DocTienBangChu(Math.round(sotienavat));
		}
</script>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0"  ">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<input type="hidden" id="chietkhau" type="text" value="0">
<form name="dhForm" method="post" action="../../ERP_DonDatHangUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type="hidden" name="action" value='1'>
<INPUT type="hidden" name="ischot" value=''>   
<input type="hidden" name='tenform' value="Updateform">
<input type="hidden" name='id' value='<%=hdBean.getId() %>'>
   
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
			 								   <TD align="left"  class="tbnavigation">&nbsp;Quản lý bán hàng > Duyệt đơn hàng > Hiển thị </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng bạn&nbsp;&nbsp;  <%= userTen%> &nbsp; </TD>
					    				 </TR>
									  </TABLE>
								  </TD>
							  </TR>	
						  	</TABLE>
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
								<TR ><TD >
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class = "tbdarkrow">
											<TD width="30" align="left"><A href = "javascript:history.back()" ><img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
										    										    <TD width="2" align="left" >&nbsp;</TD>
												<TD width="30" align="left" ><A href="../../ERP_DonDatHangUpdateSvl?userId=<%=userId%>&print=<%=hdBean.getId()%> "  ><img src="../images/Printer30.png" alt="In" title="In chung tu" width="30" height="30" longdesc="In chung tu" border=1 style="border-style:outset"></A></TD>
								    		<TD align="left" >&nbsp;</TD>
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="1" width="100%" cellpadding = "1" cellspacing = "0" style="border-color:gray;" >
								<tr>
								
								 <TD align="left" class="legendtitle">
								
							  	 </TD>
								</tr>
								
								<TR class="plainlabel">
									<TD align="left" >						
										
										<TABLE  border="0" bordercolor="white" width="100%" cellpadding = "3" cellspacing = "0" style="padding-left:2px ;" >
											<tr class="plainlabel">
											<th width='15%' ></th>
											<th width='35%' ></th>
											<th width='15%' ></th>
											<th width='35%' ></th>
											</tr>
											<tr class="plainlabel" >
											<td >Nhà cung cấp  </td>
											<td >
												<select name='nhaccid' style="width: 100%">
								
													  <% if(rs_nhacc != null){
														  try{ while(rs_nhacc.next()){ 													 
											    			if(rs_nhacc.getString("pk_seq").trim().equals(hdBean.getIdNhaCungCap())){ %>
											      				<option value='<%=rs_nhacc.getString("pk_seq")%>' selected><%=rs_nhacc.getString("ten") %></option>
											      			<%}else{ %>
											     				<%-- <option value='<%=rs_nhacc.getString("pk_seq")%>'><%=rs_nhacc.getString("ten") %></option> --%>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select>	
											</td>
											<td > Chọn ĐVKD </td>
											<td>
											 <select name='dvkdid' style="width: 100%">
								
													  <% if(rs_dvkd != null){
														  try{ while(rs_dvkd.next()){ 													 
											    			if(rs_dvkd.getString("pk_seq").trim().equals(hdBean.getdvkdid())){ %>
											      				<option value='<%=rs_dvkd.getString("pk_seq")%>' selected><%=rs_dvkd.getString("ten") %></option>
											      			<%}else{ %>
											     				<%-- <option value='<%=rs_dvkd.getString("pk_seq")%>'><%=rs_dvkd.getString("ten") %></option> --%>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select>	
											</td>
											</tr>
											<TR class="plainlabel">
											  <TD class="plainlabel">Ngày giao dịch </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" readonly size="10" 
                                        id="ngaygiaodich" name="ngaygiaodich" value="<%=hdBean.getNgaygiaodich()%>" maxlength="10" /> 
											    </TD>
                                          <!-- chen vao kenh ban hang -->
                                          <td>Kênh bán hàng</td>
									          <td>
									          <select name='kenhbanhang' style="width: 100%">
												 
													  <% if(rs_kenhbanhang != null){
														  try{ while(rs_kenhbanhang.next()){ 				
											    			if(rs_kenhbanhang.getString("pk_seq").trim().equals(hdBean.getIDKenhBanHang())){ %>
											      				<option value='<%=rs_kenhbanhang.getString("pk_seq")%>' selected><%=rs_kenhbanhang.getString("ten") %></option>
											      			<%}else{ %>
											     				<%-- <option value='<%=rs_kenhbanhang.getString("pk_seq")%>'><%=rs_kenhbanhang.getString("ten") %></option> --%>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select> 
									          </td>
                                          
                                          	</TR>
											<TR class="plainlabel" >
												<TD >Nhà phân phối </TD>
												<td >
													 <input type="text" readonly id="nhappid" name="nhappid" value="<%= hdBean.getNppId() %>">
												</td>
												<td>
													Tên nhà phân phối
												</td>
												<td>
												<input type="text" readonly id="tennpp" name="tennpp"  style="width: 100%" value="<%=hdBean.getNppTen() %>">
												</td>
											</TR>
											
											
											  <TR class="plainlabel" >
											  <TD  >Tổng số tiền(Chưa VAT) </TD>
											  <TD  ><input name="SoTienChuaVat" id="SoTienChuaVat"  type="text" readonly="readonly" 
											  	  value="<%=formatter.format(hdBean.getTongtientruocVAT())%>" >
											  	  	<% System.out.println("So tien truoc Vat"+formatter.format(hdBean.getTongtiensauVAT()));%>
											  	VND </TD>
											  	
                                          	 <TD  class="plainlabel">VAT (%) </TD>
											  <TD  class="plainlabel"><input name="VAT" readonly="readonly" id="VAT" type="text" value="<%= Math.round(hdBean.getVAT()) %>" onkeypress="return keypress(event);">%
											  </TD>
											  </TR>
											  <TR class="plainlabel">
											    <TD  class="plainlabel">Tổng tiền(sau VAT) </TD>	     
										        <TD   class="plainlabel"><input readonly name="SoTienCoVAT" id="SoTienCoVAT" type="text" readonly="readonly" 
										        	value="<%=formatter.format(hdBean.getTongtiensauVAT())%>"> 
										          VND </TD>
										        <TD  class="plainlabel">Ngày đề nghị giao hàng</TD>
											  <TD  class="plainlabel"><input name="ngaydenghigh" style="width: 100%" id="ngaydenghigh" type="text" value="<%=hdBean.getNgaydenghigh() %>" >
										       
										          
										        
									       </TR>
											
												  <TR class="tblightrow">
                                                      <TD  class="plainlabel"> </TD>
                                                      <TD  class="plainlabel">
                                                      <% if(hdBean.getDoihang().equals("1")) {%>
                                                      <input type="checkbox" checked="checked" name="doihang" value="1" > Đơn đổi hàng 
                                                      <%}else{ %>
                                                        <input type="checkbox" value="1" name="doihang"  > Đơn đổi hàng 
                                                      <%} %>
                                                       </TD>
                                                       
                                                 
                                                      <TD  class="plainlabel"> </TD>
                                                      <TD  class="plainlabel">
                                                      <% if(hdBean.GetLoaidonhang().equals("1")) {%>
                                                      <input type="checkbox" checked="checked" value="1" name="loaidonhang"  > Đơn hàng khuyến mãi 
                                                      <%}else{ %>
                                                        <input type="checkbox" value="1" name="loaidonhang" >  Đơn hàng khuyến mãi 
                                                      <%} %>
                                                       </TD>
                                                       
                                                   </TR>
										 <tr class="plainlabel">
										  <td>
										  Lý do hủy đơn hàng
										  </td>
										  <td colspan="3"> 
										  <textarea rows="1" style="width:100% ;font-size:11pt "  readonly="readonly"  > <%=hdBean.getLyDohuy() %>  </textarea>
										  </td>
										  </tr>
										  <tr class="plainlabel">
										  <td>
										  Số tiền bằng chữ
										  </td>
										  <td colspan="3"> 
										  <textarea rows="1" style="width:100% ;font-size:11pt "  readonly="readonly" id="doctien">  </textarea>
										  </td>
										  </tr>

										</TABLE>
								
								  </TD>

							   </TR>	
							  
							   <TR>
							   		<TD>
										<TABLE id="tabc" class="tabledetail" width = "100%" border="0" cellpadding="0" cellspacing="1">
										<tbody id="san_pham">
												<thead  class="tbheader">
													<TH width="8%" height="20">Mã sản phẩm </TH>
													<TH width="24%">Tên sản phẩm</TH>
													<!-- <TH width="8%">Tồn kho</TH> -->
													<TH width="8%">Số lượng</TH>
													<TH width="12%">Số lượng duyệt</TH>
													<TH width="5%">DVT</TH>
													<TH width="10%">Đơn giá </TH>
													<TH width="9%">Thành tiền </TH>		
													<TH width="9%">Scheme </TH>	
													<TH width="9%">Kho </TH>	
												</thead>
									<% 
							if(splist != null){
							IERP_DonDatHang_SP sanpham;
							int size = splist.size();
							int m = 0;
							while (m < size){
								sanpham = splist.get(m); 
								if (m % 2 != 0) {%>						
								<TR class= <%="tblightrow"%> >
							<%} else {%>
								<TR class= <%= "tbdarkrow"%> >
							<%}%>
									<TD align="left" >
										<input name='idsp' readonly type='hidden' value=<%=sanpham.getIdSanPham() %>>
										<input name="masp" readonly type="text" value="<%=sanpham.getMaSanPham()%>" autocomplete='off'  onkeyup="" style="width:100%;background-color: transparent;border: none;">
									</TD>
									<TD align="left" >
										<input name="tensp" readonly type="text" readonly value="<%=sanpham.getTenSanPham()%>" style="width:100%;background-color: transparent;border: none;" ></TD>
											<%-- <TD align="left" >
										<input name="tonkho" readonly type="text" readonly value="<%=formatter.format(sanpham.getsoluongton()) %>" style="width:100%;background-color: transparent;border: none;" >
										</TD> --%>						
						        	<% if (spThieuList.containsKey(sanpham.getMaSanPham())){ %>
									    <TD align = "left"  class="addspeech">
								        <input name="soluong" readonly type="text" value="<%= formatter.format(sanpham.getSoLuong()) %>"  onkeypress="return keypress(event);" style="cursor:pointer; background-color:99CCCC;text-align:left ;width:100%;background-color: transparent;border: none;" title="San pham nay con toi da <%= spThieuList.get(sanpham.getMaSanPham()) %> san pham, vui long chon lai so luong">
								        </TD>
								    <%}else{ %>
							        	<TD align = "left" >
								        <input name="soluong" readonly type="text" value="<%= formatter.format(sanpham.getSoLuong()) %>" onkeypress="return keypress(event);" style="text-align:left;width:100%;background-color: transparent;border: none;">
								        </TD>
								    <%} %>
								    <TD align = "left" >
								        <input name="soluongduyet" readonly type="text" value="<%= formatter.format(sanpham.getsoluongduyet()) %>" onkeypress="return keypress(event);" style="text-align:left;width:100%;background-color: transparent;border: none;">
								        </TD>
								    <TD align = "center" ><input readonly name="donvitinh" type="text" value="<%= sanpham.getDonViTinh() %>" readonly style="width:100%;background-color: transparent;border: none;"></TD>
								    <TD align = "center" >
								    	<input type="text" readonly name="dongia" value="<%= formatter.format(sanpham.getDonGia()) %>" readonly style="width:100% ;text-align:right;background-color: transparent;border: none;">
								    </TD>
								    
								    
								    
								    <TD align = "center" ><input readonly name="thanhtien" type="text" value="<%=formatter.format(sanpham.getThanhTien()) %>" readonly  style="width:100% ;text-align:right;background-color: transparent;border: none;"></TD>
								     <TD align = "center" ><input readonly name="thanhtien" type="text" value="<%=sanpham.getSHEME() %>" readonly  style="width:100% ;text-align:right;background-color: transparent;border: none;"></TD>
								     
								     <TD align = "center" ><input readonly name="khodat" type="text" value="<%=sanpham.getTenKhoTT() %>" readonly  style="width:100% ;text-align:right;background-color: transparent;border: none;"></TD>
								</TR>
								<% m++; }}%>		

								</tbody>
								</TABLE>
															  
							</TD>
							  </TR>	
							  	<tr>
							  	<td>
							  		<!-- &nbsp;&nbsp;&nbsp;&nbsp;	<a class="button2" href="javascript:ThemSanPham()">
	                         			<img style="top: -4px;" src="../images/add.png" alt="">Thêm sản phẩm</a>&nbsp;&nbsp;&nbsp;&nbsp; -->
							  	
							  	</td>
							  	</tr>	  							  					   
						  </TABLE>
											</TD>
					</TR>	
					
				</TBODY>
			</TABLE>
	</td>
  </tr>

</TABLE>
<script type="text/javascript">
	getinfoddh();
	
</script>
<script type="text/javascript">
		
</script>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
try{
 if(rs_nhacc!=null){
	 rs_nhacc.close();
 }
 if(rs_khott!=null){
	 rs_khott.close();
 }
 if(rs_nhapp!=null){
	 rs_nhapp.close();
 }
 if(rs_kenhbanhang!=null){
	 rs_kenhbanhang.close();
 }
 if(rs_dvkd!=null){
	 rs_dvkd.close();
 }
	hdBean.DBClose();
	
}catch(Exception er){
	
}
%>

