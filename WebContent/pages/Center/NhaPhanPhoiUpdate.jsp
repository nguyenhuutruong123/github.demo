<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhaphanphoi.INhaphanphoi" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
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

<% INhaphanphoi nppBean = (INhaphanphoi)session.getAttribute("nppBean"); %>
<% ResultSet tp = (ResultSet)nppBean.getTp() ;  %>
<% ResultSet qh = (ResultSet)nppBean.getQh() ;  %>
<% ResultSet kv = (ResultSet)nppBean.getKhuvuc();  %>
<% ResultSet npptn = (ResultSet)nppBean.getNhappTiennhiem();  %>
<% ResultSet dvkd_ncc = (ResultSet)nppBean.getDvkd_NccList(); %>
<% ResultSet dvkd_nccSelected = (ResultSet)nppBean.getDvkd_NccSelected(); %>
<% String dvkd_nccIdSelected = (String)nppBean.getDvkd_NccIdSelected(); %>
<% Hashtable has_dvkd_ncc_Selected = (Hashtable)nppBean.getGsbh_KbhIds(); %>
<% ResultSet gsbh_kbh = (ResultSet)nppBean.getGsbh_KbhList();  %>
<% ResultSet ngaydh = (ResultSet)nppBean.getNgaydhList();%>
<% ResultSet ngaydhSelected = (ResultSet)nppBean.getNgaydhSelected();%>
<% String Dvkd_NccIdSelected = (String)nppBean.getDvkd_NccIdSelected(); %> <!-- Đơn vị kinh doanh được chọn -->
<% String GsbhIdSelected = (String)nppBean.getGsbh_KbhIdSelected(); %> <!--  Giám sát bán hàng được chọn -->
<% String NgayDhIdSelected = (String)nppBean.getNgayDh_IdSelected(); %> <!-- Ngày đặt hàng được chọn được chọn -->
<%
ResultSet rs_khott = nppBean.getrs_khott();
ResultSet gsQlRs = nppBean.getGsQlRs();
ResultSet gsbh_kbhSelected = (ResultSet)nppBean.getGsbh_KbhSelected(); %>

<% ResultSet loainppRs = (ResultSet)nppBean.getLoaiNppRs();%>
<% ResultSet tructhuocRs = (ResultSet)nppBean.getTructhuocRs();%>
<% ResultSet ttppRs = (ResultSet)nppBean.getTtppRs();%>
<% ResultSet kenhrs = (ResultSet)nppBean.getKenhRs();%>
<% ResultSet muahangtuRs = (ResultSet)nppBean.getMuahangtuRs(); %>
<% ResultSet nppc1Rs = (ResultSet)nppBean.getNppC1Rs();%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
 } 
String url = util.getChuyenNguUrl("NhacungcapSvl","",nnId); 
ResultSet diaban = (ResultSet)nppBean.getDiabanRs();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>


<script type="text/javascript">
		$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>

<SCRIPT language="javascript" type="text/javascript">
function Kiemtra(object)
{

	if(object.checked==true)
		{
			var gsbh=document.getElementById("gsbh");
			var n =gsbh.value.indexOf(object.value); 
			if(n !="-1")
				{
					var r=confirm("Giám sát này đã có nhà phân phối, bạn muốn tiếp tục?");
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

 function submitform(){
	 document.forms['nppForm'].submit();
 }
 function saveform()
 {
	 var ten = document.getElementById('nppTen');
	 var manpp = document.getElementById('manpp');
	 var diachi = document.getElementById('DiaChi');
	 var tp = document.getElementById('TP');
	 var qh = document.getElementById('QH');
	 var dienthoai = document.getElementById('DienThoai');
	 var mst = document.getElementById('masothue');
	 var khuvuc = document.getElementById('KhuVuc');
	 var ttpp = document.getElementById('ttppId');
	 var diachixhd = document.getElementById('diachixhd');
	 var dvkd_ncc = document.getElementsByName("dvkd_nccList");
	 var gsbh_kbh = document.getElementsByName("gsbh_kbhList");
	 var maSAP = document.getElementById('maSAP');
	 var pass = document.getElementById('pass');
	 var khodat = document.getElementById('khottid');
	// var kenhbh = document.getElementById('kenhid');
	 var tonantoan = document.getElementById('tonantoan');
	 var fax = document.getElementById('fax');
	 
	 if(ten.value == "")
	 {
		 alert('Vui lòng nhập tên Nhà phân phối');
		 return;
	 }
	 if(fax.value == "")
	 {
		 alert('Vui lòng nhập Fax');
		 return;
	 }
	 if(khuvuc.value == "")
	 {
		 alert('Vui lòng chọn Khu vực');
		 return;
	 }
	 if(tonantoan.value == "")
	 {
		 alert('Vui lòng nhập Tồn an toàn');
		 return;
	 }
	/*  if(kenhbh.value == "")
	 {
		 alert('Vui lòng nhập Kênh bán hàng');
		 return;
	 } */
	 if(khodat.value == "")
	 {
		 alert('Vui lòng chọn Kho đặt');
		 return;
	 }
	 if(ttpp.value == "")
	 {
		 alert('Vui lòng nhập Trung tâm phân phối');
		 return;
	 }
	 if(manpp.value == "")
	 {
		 alert('Vui lòng nhập mã Nhà phân phối');
		 return;
	 }
	 if(diachi.value == "")
	 {
		 alert('Vui lòng nhập Địa chỉ giao hàng');
		 return;
	 }	
	 if(diachixhd.value == "")
	 {	
		 alert('Vui lòng nhập địa chỉ xuất hoá đơn');
		 return;
	 }	

	/*  if(tp.value == "")
	 {
		 alert('Vui lòng chọn thành phố');
		 return;
	 }	
	 
	 if(qh.value == "")
	 {
		 alert('Vui lòng chọn quận huyện');
		 return;
	 }	 */
	 
	 if(dienthoai.value == "")
	 {
		 alert('Vui lòng nhập số DT');
		 return;
	 }
	 
	 if(mst.value == "")
	 {
		 alert('Vui lòng nhập số Mã số thuế');
		 return;
	 }
	 
/* 	 if(document.getElementById('diabanid').value == "")
	 {
		 alert('Vui lòng chọn địa bàn');
		 return;
	 }	 */ 
	 
	 if(maSAP.value == "")
	 {
		 alert('Vui lòng nhập Mã Truy Cập');
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
		 alert('Vui lòng chọn đơn vị kinh doanh và nhà cung cấp');
		 return;
	 }
	 var flag2 = '';
	 for(var i in gsbh_kbh)
	 {
		 if(gsbh_kbh[i].checked)
			 flag2 = flag2 + gsbh_kbh[i].value;
	 }
	 /* if(flag2 == '')
	 {
		 alert('Vui lòng chọn giám sát bán hàng và kênh bán hàng');
		 return;
	 } */
	 
 	 document.forms['nppForm'].action.value='save';
     document.forms['nppForm'].submit();
 }
 
 jQuery(document).ready(function()
		 {
		 	$("select:not(.notuseselect2)").chosen();   
		 	$("#qtbh").hide();
		 	$("#prisec").hide();
		 	$("#TrangThaidms").hide();
		 	
		 }); 
</SCRIPT>
<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>

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

<form name="nppForm" method="post" action="../../NhaphanphoiUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="ngonnguId" value='<%=nnId%>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" id="gsbh" value="<%= nppBean.getGsbhnpp() %>">
<%-- <input type="hidden" name="checkgsbh" id="checkgsbh" value='<%= nppBean.CheckGsbh()%>'> --%>
<INPUT name="id" type="hidden" value='<%= nppBean.getId() %>' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <%-- <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%=url %> &gt; <%=ChuyenNgu.get("Cập nhật",nnId)%></TD> --%>
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kinh doanh &gt; Nhà phân phối &gt; Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng ",nnId)%> <%=userTen %>&nbsp;  </TD> 
						  </tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
					<TR class = "tbdarkrow">
						<TD width="30" align="center"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Back"  title="Back" width="30" height="30" border="1" longdesc="Back" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <TD width="30" align="left" ><A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Save" alt="Save" border = "1"  style="border-style:outset"></A></TD>
				    	<TD align="left" >&nbsp;</TD>
					</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="0">
				  	<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle"><%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId)%></LEGEND>
			    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" rows="1" ><%= nppBean.getMessage() %></textarea>
                                        <%nppBean.setMessage(""); %>
								</FIELDSET>
						   </TD>
					</tr>

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET>
						<LEGEND class="legendtitle" style="color:black"><%=ChuyenNgu.get("Thông tin",nnId)%> </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<tr>
							  	<td class="plainlabel" width="130px"><%=ChuyenNgu.get("Chọn loại: ",nnId)%><font class="erroralert"></font></td>
							  	<td class="plainlabel">
							  		<SELECT name="loai" id="loai" onChange="submitform();">
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
							  	</td>
							  	<TD class="plainlabel" width="130px"><%=ChuyenNgu.get("Tên",nnId)%><FONT
									class="erroralert"> *</FONT></TD>
							  	<TD class="plainlabel" width="220px">
							  	<!-- readonly="readonly" --><INPUT
									name="nppTen" id="nppTen" type="text" 
									value="<%= nppBean.getTen() %>" required></TD>
							  	<% if(nppBean.getLoaiNpp().equals("6")){ %>
							 	<TD class="plainlabel" width="130px"><%=ChuyenNgu.get("Chọn nhà phân phối cấp 1",nnId)%><FONT class="erroralert"> *</FONT>
						      	</TD>
							  	<TD class="plainlabel">
									<SELECT name="nppc1" id="nppc1" >
									<option value=""></option>
									<%
					      			if(nppc1Rs!=null)
					      			try{
					      				while(nppc1Rs.next()){ 
					    					if(nppc1Rs.getString("pk_seq").equals(nppBean.getNppcap1()  )){ %>
										<option value='<%=nppc1Rs.getString("pk_seq")%>' selected><%=nppc1Rs.getString("ten") %></option>
										<%}else{ %>
										<option value='<%=nppc1Rs.getString("pk_seq")%>'><%=nppc1Rs.getString("ten") %></option>
										<%}}}catch(java.sql.SQLException e){} %>
									</SELECT>
							  	</TD>
								<% } else { %>	
							  	<TD class="plainlabel"></TD>
							  	<TD class="plainlabel"></TD>
								<% } %>
							</tr>
							<TR>
								<%-- <TD class="plainlabel" width="140px">Tên viết tắt<FONT
									class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"><INPUT name="tentat"
									id="tentat" type="text" value="<%= nppBean.getTentat()%>">
								</TD> --%>
								
								<TD class="plainlabel"><%=ChuyenNgu.get("Mã",nnId)%><FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"><INPUT name="manpp" id="manpp" 
									type="text" value="<%= nppBean.getMaNpp() %>"></TD>
									
								<TD class="plainlabel" width="140px"><%=ChuyenNgu.get("Người đại diện",nnId)%>
								</TD>
								<TD class="plainlabel">
									<INPUT name="ndd" id="ndd"
									type="text" value="<%= nppBean.getNguoidaidien() %>"
									size="10">
								
								</TD>
								<TD class="plainlabel"><%=ChuyenNgu.get("Địa chỉ giao hàng",nnId)%><FONT
									class="erroralert"> *</FONT>
								</TD>
								<TD class="plainlabel"><INPUT name="DiaChi" id="DiaChi" 
									type="text" value="<%= nppBean.getDiachi() %>"></TD>
							</TR>

							<TR>
									
								<TD class="plainlabel"><%=ChuyenNgu.get("Địa chỉ giao hàng 2",nnId)%>
								</TD>
								<TD class="plainlabel"><INPUT name="DiaChi2" id="DiaChi2"
									type="text" value="<%= nppBean.getDiachi2() %>"></TD>

								<TD class="plainlabel"><%=ChuyenNgu.get("Tỉnh/thành phố",nnId)%> <FONT class="erroralert"> *</FONT> </TD>
								<TD class="plainlabel" width="220px"><SELECT
									name="tpId" id="TP" onChange="submitform();" class="select2"
									style="width: 200px;">
										<option value=""></option>
										<% if(tp!=null)
					      				try{while(tp.next()){ 
					    				if(tp.getString("tpId").equals(nppBean.getTpId())){ %>
										<option value='<%=tp.getString("tpId")%>' selected><%=tp.getString("tpTen") %></option>
										<% }else{ %>
										<option value='<%=tp.getString("tpId")%>'><%=tp.getString("tpTen") %></option>
										<% }}}catch(java.sql.SQLException e){} %>
								</SELECT></TD>

								<TD class="plainlabel"><%=ChuyenNgu.get("Quận/huyện",nnId)%></TD>
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
								<TD class="plainlabel"><%=ChuyenNgu.get("Điện thoại",nnId)%><FONT
									class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"><INPUT name="DienThoai"
									id="DienThoai" type="text"
									value="<%= nppBean.getSodienthoai() %>"></TD>
								<%-- <TD class="plainlabel">
									<!-- Địa bàn hoạt động --> Lịch đặt hàng <FONT
									class="erroralert"> *</FONT>
								</TD>
								<TD class="plainlabel">

									<TABLE cellpadding="0" cellspacing="0">
										<TR>
											<TD><INPUT name="LichDatHang" class="days"
												id="LichDatHang" type="text" size="20"
												value="<%= nppBean.getLichDatHang()%>">
											<input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="20" onchange="submitform();">
											</TD>

										</TR>
									</TABLE> <INPUT name="DiaBanHd" id="DiaBanHd" type="text" value="<%= nppBean.getDiaBanHd() %>">
								</TD>  --%>
								<TD class="plainlabel"><%=ChuyenNgu.get("Địa chỉ xuất hoá đơn",nnId)%><FONT
									class="erroralert"> *</FONT></TD>
								<TD class="plainlabel" colspan = "3"><INPUT name="diachixhd" 
									id="diachixhd" type="text"
									value="<%= nppBean.getDiaChiXuatHoaDon() %>"></TD>

							</TR>


							<TR>
								<TD class="plainlabel"><%=ChuyenNgu.get("Mã số thuế",nnId)%><FONT
									class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"><INPUT name="masothue"
									id="masothue" type="text"
									value="<%= nppBean.getmaSoThue() %>"></TD>

								<TD class="plainlabel"><%=ChuyenNgu.get("Kho đặt",nnId)%><FONT
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
								<TD class="plainlabel">Tồn an toàn<FONT
									class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<INPUT  onkeyup="Dinhdang(this)"  name="tonantoan" id="tonantoan" type="text" value="<%= nppBean.getTonAnToan() %>"></TD>
							</TR>


							<TR>
							
								<TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %> 
									<FONT class="erroralert"> *</FONT></TD>
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
										</SELECT>
									</TD>
							
						<%-- 	<TD class="plainlabel"><%=ChuyenNgu.get("Địa bàn",nnId)%><FONT class="erroralert">
													*</FONT></TD>
											<TD class="plainlabel"><SELECT name="diabanid" id="diabanid"
												onChange="submitform();">
													<option value=""></option>
													<%
								      if(diaban!=null)
								      try{while(diaban.next()){ 
								    	if(diaban.getString("pk_seq").equals(nppBean.getDiabanId())){ %>
													<option value='<%=diaban.getString("pk_seq")%>' selected><%=diaban.getString("ten") %></option>
													<%}else{ %>
													<option value='<%=diaban.getString("pk_seq")%>'><%=diaban.getString("ten") %></option>
													<%}}}catch(java.sql.SQLException e){} %>
											</SELECT></TD> --%>
							
								<%-- <TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId)%><FONT class="erroralert">
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
								</SELECT></TD>--%>
								
								<TD class="plainlabel"><%=ChuyenNgu.get("Phần trăm tăng trưởng",nnId)%> </TD>
								<TD class="plainlabel" colspan = 3 >
									<INPUT name="pttangtruong" id="pttangtruong" type="text" value="<%= nppBean.getPtTangTruong() %>" size="10">
								</TD> 
								<%-- <TD class="plainlabel"> <!-- Ngân hàng<FONT class="erroralert"> *</FONT> --></TD>
								 <TD  class="plainlabel">
								 <input type="text" name="NganHangId" value="<%=nppBean.getNganHangId() %>" />
								 </TD> --%>
								<%-- <TD class="plainlabel">Mua hàng từ <!-- Địa chỉ kho -->
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


								<%-- <TD class="plainlabel">Chi nhánh<FONT class="erroralert">
										*</FONT></TD>
								<TD class="plainlabel"><input type="text"
									name="ChiNhanhId" value="<%=nppBean.getChiNhanhId() %>" /></TD> --%>
							</TR>
							<TR>
								<TD class="plainlabel"><%=ChuyenNgu.get("Chủ nhà phân phối",nnId)%><!-- Chủ tài khoản-->
											
											</TD>
								<TD class="plainlabel">
									<%-- <INPUT name="ChuTaiKhoan" id="ChuTaiKhoan" type="text" value="<%= nppBean.getChuTaiKhoan() %>" size="10"> --%>
									<INPUT name="ChuNhaPhanPhoi" id="ChuNhaPhanPhoi" type="text"
									value="<%= nppBean.getChuNhaPhanPhoi() %>" size="10">
								</TD>

								<TD class="plainlabel"><%=ChuyenNgu.get("Mã truy cập",nnId)%><FONT
												class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"><INPUT name="maSAP" id="maSAP" 
									type="text" value="<%= nppBean.getMaSAP() %>" size="10" onchange="submitform();"></TD>

								<TD class="plainlabel">
									<!-- Giấy phép kinh doanh <FONT--> Fax <FONT class="erroralert"> *</FONT> 
								</TD>
								<TD class="plainlabel">
									<INPUT name="fax" id="fax"
									type="text" value="<%= nppBean.getFax() %>" size="10">
									<%-- <INPUT name="gpkd" id="gpkd" type="text" value="<%= nppBean.getGiayphepKD() %>" size="10"> --%>
								</TD>
							</TR>

							<TR>
							
								<TD class="plainlabel"><!-- Fax--> <%=ChuyenNgu.get("Trung tâm phân phối:",nnId)%> <FONT class="erroralert">
													*</FONT></TD>
								<TD class="plainlabel" colspan = 5>
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
								</TD>
									
									
							</TR>


							<TR>
								<%-- <TD class="plainlabel">Số ngày nợ <FONT
									class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"><input type="text"
									name="songayno" value="<%= nppBean.getSongayno()%>" onkeyup="Dinhdang(this)" >
								</TD>
								<TD class="plainlabel">Số tiền nợ<FONT
									class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"><input type="text"
									name="sotienno"  onkeyup="Dinhdang(this)" value="<%= nppBean.getSotienno()%>">
								</TD> --%>
								<TD class="plainlabel"><%=ChuyenNgu.get("Tần suất đặt hàng",nnId)%></TD>
											<TD class="plainlabel" colspan="5"><input type="text" maxlength="9"
												name="tansuat"   value="<%= nppBean.getTansuatdh()%>">
											</TD>
											
											
											
											
											
								<!-- <TD class="plainlabel"> Quy trình bán hàng: <font class="erroralert">*</font>
								
								</TD> -->
								<%-- <TD class="plainlabel">
									<SELECT name="quytrinhbanhang"
									id="quytrinhbanhang">

										<% if (nppBean.getQuyTrinhBanHang().equals("0")){%>
										<option selected="true" value="0">Quy trình chuẩn
										</option>
										<option value="1">Quy trình rút gọn</option>
										<option value=""></option>
										<%}else if(nppBean.getQuyTrinhBanHang().equals("1")) {%>
										<option value=""></option>
										<option value="0">Quy trình chuẩn</option>
										<option selected="true" value="1">Quy trình rút
											gọn</option>
										<%} else{ %>
										<option selected="true" value=""></option>
										<option value="0">Quy trình chuẩn</option>
										<option value="1">Quy trình rút gọn</option>
										<%} %>

								</SELECT>
								</TD> --%>
							</TR>
							<TR>
							
							
								<TD class="plainlabel"><div ><%=ChuyenNgu.get("Hoạt động",nnId)%></div></TD>

								<TD class="plainlabel" colspan="1">
									<%  if (nppBean.getTrangthai().equals("1")){%> 
										<input readonly="readonly" name="TrangThai" type="checkbox" value="1" checked>
									<%} else 
									{%> 
										<input name="TrangThai" type="checkbox" value="0">
									<%} %>

								</TD>
								<TD class="plainlabel"><div align="right"></div></TD>
								<TD class="plainlabel">
									<%  if (nppBean.getPriSec().equals("1")){%> <input
									name="prisec" id="prisec" type="checkbox" value="1" checked> <%} else {%>
									<input name="prisec" id="prisec" type="checkbox" value="0"> <%} %>

								</TD>



									<TD class="plainlabel"><div align="right"></div></TD>
											<TD class="plainlabel" colspan="1">
												<%  if (nppBean.getQuyTrinhBanHang().equals("1")){%> <input
												name="qtbh"  id="qtbh" type="checkbox" value="1" checked>
												<%} else {%> <input name="qtbh" id="qtbh" type="checkbox" value="0">
												<%} %>

											</TD>

							</TR>
							
							<TR>
										<TD class="plainlabel"><div><%=ChuyenNgu.get("Chiết khấu",nnId) %></div></TD>
											<TD class="plainlabel" ><input type="text" maxlength="5"
												name="ChietKhau"   value="<%= nppBean.getChietKhau() %>">
											</TD>
							
							
											<TD class="plainlabel"><div align="right"></div></TD>
											<TD class="plainlabel" colspan="3">
												<%  if (nppBean.getTrangthaidms().equals("1.0")){%> <input
												name="TrangThaidms" id="TrangThaidms" type="checkbox" value="1" checked>
												<%} else {%> <input name="TrangThaidms"  id="TrangThaidms"type="checkbox" value="0">
												<%} %>
											</TD>
											
										</TR>



							<TR>
								<TD class="plainlabel"> <!-- Trực thuộc: <FONT class="erroralert">
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
								<TD class="plainlabel"><!-- Trực thuộc<FONT
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

								<TD class="plainlabel"><!-- Trung tâm phân phối<FONT
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
								<TD class="plainlabel"><!-- Số tài khoản <FONT
									class="erroralert"> *</FONT> --></TD>
								<TD class="plainlabel"><%-- <INPUT name="sotk" id="sotk"
									type="text" value="<%= nppBean.getSotk() %>" size="10"> --%></TD>

								<TD class="plainlabel"><!-- Người đại diện <FONT
									class="erroralert"> *</FONT> --></TD>
								<TD class="plainlabel"><%-- <INPUT name="ndd" id="ndd"
									type="text" value="<%= nppBean.getNguoidaidien() %>"
									size="10"> --%></TD>
								<TD class="plainlabel"><!-- Quy trình bán hàng <FONT
									class="erroralert"> *</FONT> --></TD>
								<TD class="plainlabel"><%-- <SELECT name="quytrinhbanhang"
									id="quytrinhbanhang">

										<% if (nppBean.getQuyTrinhBanHang().equals("0")){%>
										<option selected="true" value="0">Quy trình chuẩn
										</option>
										<option value="1">Quy trình rút gọn</option>
										<option value=""></option>
										<%}else if(nppBean.getQuyTrinhBanHang().equals("1")) {%>
										<option value=""></option>
										<option value="0">Quy trình chuẩn</option>
										<option selected="true" value="1">Quy trình rút
											gọn</option>
										<%} else{ %>
										<option selected="true" value=""></option>
										<option value="0">Quy trình chuẩn</option>
										<option value="1">Quy trình rút gọn</option>
										<%} %>

								</SELECT> --%>
								<TD>
							</TR>

							
							
						</TABLE>
						</FIELDSET>

						<FIELDSET>
					<LEGEND class="legendtitle" style="color:black"><%=ChuyenNgu.get("Chọn đơn vị kinh doanh",nnId)%></LEGEND>
                        <TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
                            <TR class="tbheader">
                                 <TH width="13%"><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId)%> </TH>
                                <TH width="13%"><%=ChuyenNgu.get("Nhà cung cấp",nnId)%> </TH>
                                <TH width="13%"><%=ChuyenNgu.get("Chọn",nnId)%> </TH>
                            </TR>
           		
                     		<%
								int i = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								if (!(dvkd_nccSelected == null)){
							    	try{while(dvkd_nccSelected.next()){ 
										if (i % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
										<TD align="center"><div align="left"><%= dvkd_nccSelected.getString("dvkdTen") %> </div></TD>
										<TD align="center"><div align="left"><%= dvkd_nccSelected.getString("nccTen") %> </div></TD>
										<TD align="center">
											<input name="dvkd_nccList" id="dvkd_nccList" type="checkbox" value ="<%= dvkd_nccSelected.getString("dvkd_nccId")%>" checked></TD>
										</TR>
							     		<% i++;
										}
							    	
							    	}catch(java.sql.SQLException e){} 
								}
								
								if (!(dvkd_ncc == null)){
							     	try{while(dvkd_ncc.next()){ 
										if (i % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
										<TD align="center"><div align="left"><%= dvkd_ncc.getString("dvkdTen") %></div></TD>
										<TD align="center"><div align="left"><%= dvkd_ncc.getString("nccTen") %> </div></TD>
										<TD align="center">
											<%if(dvkd_nccIdSelected.indexOf(dvkd_ncc.getString("dvkd_nccId"))>=0){ %>
												<input name="dvkd_nccList" checked id="dvkd_nccList" type="checkbox" value ='<%= dvkd_ncc.getString("dvkd_nccId") %>' ></TD>
											<%}else{ %>
												<input name="dvkd_nccList" id="dvkd_nccList" type="checkbox" value ='<%= dvkd_ncc.getString("dvkd_nccId") %>' ></TD>
											<%} %>
											
										</TR>
							     		<% i++;}}catch(java.sql.SQLException e){} 
								}
							     %>  	  		
                        </TABLE> 							
						</FIELDSET>
							
						<FIELDSET>
                        <LEGEND class="legendtitle" style="color:black">Chọn giám sát bán hàng</LEGEND>
                        <TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
                            <TR class="tbheader">
                               <TH width="13%"><%=ChuyenNgu.get("Giám sát bán hàng",nnId)%></TH>
								<TH width="13%"><%=ChuyenNgu.get("Kênh bán hàng",nnId)%></TH>
								<TH width="13%"><%=ChuyenNgu.get("Số điện thoại",nnId)%></TH>
								<TH width="20%"><%=ChuyenNgu.get("Ngày bắt đầu",nnId)%></TH>
                             	<TH width="20%"><%=ChuyenNgu.get("Ngày kết thúc",nnId)%></TH> 
								<TH width="13%"><%=ChuyenNgu.get("Chọn",nnId)%></TH>
                            </TR>             		
                     		<%
								int j = 0;
								String light = "tblightrow";
								String dark = "tbdarkrow";
								boolean coGiamSat = false;
								
								try{ if (gsbh_kbhSelected != null)
									while (gsbh_kbhSelected.next()){ 
										coGiamSat = true;
									if (j % 2 != 0) {%>						
										<TR class= <%=light%> >
									<%} else {%>
										<TR class= <%= dark%> >
								<%}%>
									<TD align="center"><div align="left"><%= gsbh_kbhSelected.getString("gsbhTen") %> </div></TD>
									<TD align="center"><div align="left"><%= gsbh_kbhSelected.getString("kbhTen") %> </div></TD>
									<TD align="center"><div align="left"><%= gsbh_kbhSelected.getString("sodienthoai") %></div></TD>
									<TD align="center"><input class="days" name="ngaybatdau<%=gsbh_kbhSelected.getString("gsbh_kbhId")%>" value='<%=gsbh_kbhSelected.getString("ngaybatdau")%>'>  </TD>
									<TD align="center"><input class="days"  name="ngayketthuc<%=gsbh_kbhSelected.getString("gsbh_kbhId")%>" value='<%=gsbh_kbhSelected.getString("ngayketthuc")%>'> </TD>
									
									<TD align="center">
										<input name="gsbh_kbhList"  onclick="Kiemtra(this)" id="gsbh_kbhList" type="checkbox" value ="<%= gsbh_kbhSelected.getString("gsbh_kbhId")%>" checked></TD>
									</TR>
							     	<%j++;}}catch(java.sql.SQLException e){e.printStackTrace();}%>	
							     	
								<% if (!coGiamSat) {
										try { if (gsbh_kbh != null) {
											while(gsbh_kbh.next()){ 
												if (j % 2 != 0) {%>						
												<TR class= <%=light%> >
												<%} else {%>
												<TR class= <%= dark%> >
												<%}%>
									<TD align="center"><div  align="left"><%= gsbh_kbh.getString("gsbhTen") %> </div></TD>
									<TD align="center"><div  align="left"><%= gsbh_kbh.getString("kbhTen") %> </div></TD>
									<TD align="center"><div align="left"><%= gsbh_kbh.getString("sodienthoai") %></div></TD>
									<TD align="center"><input class="days" name="ngaybatdau<%=gsbh_kbh.getString("gsbh_kbhId")%>" value='<%=gsbh_kbh.getString("ngaybatdau")%>'>  </TD>
									<TD align="center"><input class="days"  name="ngayketthuc<%=gsbh_kbh.getString("gsbh_kbhId")%>" value='<%=gsbh_kbh.getString("ngayketthuc")%>'> </TD>
									
									<TD align="center">
										<%if(GsbhIdSelected.indexOf(gsbh_kbh.getString("gsbh_kbhId"))>=0) { %>
										<input name="gsbh_kbhList" checked  onclick="Kiemtra(this)" id="gsbh_kbhList" type="checkbox" value ="<%=gsbh_kbh.getString("gsbh_kbhId")%>"></TD>
										<%}else{ %>
										<input name="gsbh_kbhList" onclick="Kiemtra(this)" id="gsbh_kbhList" type="checkbox" value ="<%=gsbh_kbh.getString("gsbh_kbhId")%>"></TD>
										<%} %>
									</TR>
							     	<%j++;}}
										}
										catch(java.sql.SQLException e) {e.printStackTrace();} 
									}
							  %>     		
                        </TABLE>                        
                        </FIELDSET>
                        
                      
                        <FIELDSET>
                        <LEGEND class="legendtitle" style="color:black">Nhóm nhà phân phối</LEGEND>
                        <TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
                            <TR class="tbheader">
                                <TH width="20%"><%=ChuyenNgu.get("Mã nhóm",nnId)%></TH>                                                                
                                <TH width="80%"><%=ChuyenNgu.get("Tên nhóm",nnId)%></TH>
                            </TR>             		
                     		<%
								int k = 0;
								String light1 = "tblightrow";
								String dark1 = "tbdarkrow";
								
								if(!(ngaydhSelected == null)){
									try{while(ngaydhSelected.next()){ 
										if (k % 2 != 0) {%>						
											<TR class= <%=light1%> >
										<%} else {%>
											<TR class= <%= dark1%> >
										<%}%>
										<TD align="center"><div align="center"><%= ngaydhSelected.getString("ma") %> </div></TD>										
										<TD align="center"><div align="center"><%= ngaydhSelected.getString("ten") %> </div></TD>		
							     		<%k++;}}catch(java.sql.SQLException e){
							     			e.printStackTrace();
							     		}
								}

								
							
							  %>          		
                        </TABLE>                        
                        </FIELDSET>													
					</TD>
			      </TR>
		  	</TABLE>	
	</TD>
	</TR>
</TABLE>
</form>
</body>  <!-- <script type='text/javascript' src='../scripts/loadingv2.js'></script> -->
<%nppBean.DBclose() ;%>
</HTML>
<%}%>