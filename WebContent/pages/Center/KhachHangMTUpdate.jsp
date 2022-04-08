<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.khachhangmt.IKhachhangMT" %>
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

<% IKhachhangMT nppBean = (IKhachhangMT)session.getAttribute("nppBean"); %>
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
ResultSet rs_khott=nppBean.getrs_khott();
ResultSet gsQlRs=nppBean.getGsQlRs();
ResultSet gsbh_kbhSelected = (ResultSet)nppBean.getGsbh_KbhSelected(); %>

<% ResultSet loainppRs = (ResultSet)nppBean.getLoaiNppRs();%>
<% ResultSet tructhuocRs = (ResultSet)nppBean.getTructhuocRs();%>
<% ResultSet ttppRs = (ResultSet)nppBean.getTtppRs();%>
<% ResultSet muahangtuRs = (ResultSet)nppBean.getMuahangtuRs(); %>

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

 function submitform()
 {   
    document.forms["nppForm"].submit();
 }

 function saveform()
 {
	 /* var ten = document.getElementById('nppTen');
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
		 alert('Vui lòng nhập tên Nhà phân phối');
		 return;
	 }
	 if(diachi.value == "")
	 {
		 alert('Vui lòng nhập địa chỉ');
		 return;
	 }	

	 if(tp.value == "")
	 {
		 alert('Vui lòng chọn thành phố');
		 return;
	 }	
	 
	 if(qh.value == "")
	 {
		 alert('Vui lòng chọn quận huyện');
		 return;
	 }	
	 
	 if(dienthoai.value == "")
	 {
		 alert('Vui lòng nhập số DT');
		 return;
	 }	
	 if(khuvuc.value == "")
	 {
		 alert('Vui lòng chọn khu vực');
		 return;
	 }	 
	 
	 if(maSAP.value == "")
	 {
		 alert('Vui Long Nhap Ma Truy Cap');
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
	 if(flag2 == '')
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kinh doanh &gt; Khách hàng MT &gt; Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD> 
						  </tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
					<TR class = "tbdarkrow">
						<TD width="30" align="center"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <TD width="30" align="left" ><A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
				    	<TD align="left" >&nbsp;</TD>
					</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="0">
				  	<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
			    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" rows="1" ><%= nppBean.getMessage() %></textarea>
                                        <%nppBean.setMessage(""); %>
								</FIELDSET>
						   </TD>
					</tr>

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET>
						<LEGEND class="legendtitle" style="color:black">Thông tin nhà phân phối </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<%-- <tr>
							  <td class="plainlabel" width="130px"> Chọn loại NPP: <font class="erroralert"></font></td>
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
							  <td class="plainlabel"></td>
							  <td class="plainlabel"></td>
							  <td class="plainlabel"></td>
							  <td class="plainlabel"></td>
							</tr> --%>
							<TR>
								<TD class="plainlabel" width="130px"  > Tên<FONT class="erroralert"> *</FONT></TD>
                                	<TD class="plainlabel" width="220px" >
                                	<INPUT name="nppTen" id="nppTen" type="text" value="<%= nppBean.getTen() %>" >
                                </TD>
                              	<TD class="plainlabel">Mã khách hàng <FONT class="erroralert"> *</FONT></TD>
								 <TD class="plainlabel"><INPUT name="maSAP" id="maSAP" type="text" value="<%= nppBean.getMaSAP() %>" size="10"></TD>
                              	<TD class="plainlabel">Người đại diện <FONT class="erroralert"> *</FONT></TD>
							 	<TD  class="plainlabel" ><INPUT name="ndd" id="ndd" type="text" value="<%= nppBean.getNguoidaidien() %>" size="10"></TD>
                             
							</TR>
                              
                              <TR>
							  	<TD class="plainlabel">Địa chỉ giao hàng <FONT class="erroralert"> *</FONT> </TD>
                                <TD class="plainlabel" >
                                	<INPUT name="DiaChi" id="DiaChi" type="text" value="<%= nppBean.getDiachi() %>"  >
                                </TD>
                                
							   	 <TD class="plainlabel">Tỉnh/Thành phố <FONT class="erroralert"> *</FONT></TD>
								 <TD class="plainlabel" width="220px" >
								 	<SELECT name="tpId" id="TP" onChange="submitform();" class="select2" style="width: 200px;" >
								    	<option value=""></option>
								      	<% if(tp!=null)
								      	try{while(tp.next()){ 
								    	if(tp.getString("tpId").equals(nppBean.getTpId())){ %>
								      		<option value='<%=tp.getString("tpId")%>' selected><%=tp.getString("tpTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=tp.getString("tpId")%>'><%=tp.getString("tpTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
	                       			</SELECT>	
	                       		</TD>
	
							   	 <TD class="plainlabel">Quận/Huyện <FONT class="erroralert"> *</FONT></TD>
								 <TD class="plainlabel" >
								 	<SELECT name="qhId" id="QH" class="select2" style="width: 200px;">
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
						  		<TD class="plainlabel">Địa chỉ giao hàng 2<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"><INPUT name="diachi2" id="diachi2" type="text" value="<%= nppBean.getDiachi2() %>"></TD>
							  	<TD class="plainlabel">Điện thoại<FONT class="erroralert"> *</FONT></TD>
                                <TD  class="plainlabel">
                                	<INPUT name="DienThoai" id="DienThoai" type="text" value="<%= nppBean.getSodienthoai() %>" ></TD>
                                <%-- <TD class="plainlabel"><!-- Địa bàn hoạt động -->Lịch đặt hàng<FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel">
                                	<INPUT name="DiaBanHd" id="DiaBanHd" type="text" value="<%= nppBean.getDiaBanHd() %>">
                                													<TABLE cellpadding="0" cellspacing="0">
													<TR>
														<TD><INPUT name="LichDatHang" class="days"
															id="LichDatHang" type="text" size="20"
															value="<%= nppBean.getLichDatHang()%>"></TD>
														<input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="20" onchange="submitform();">
														</TD>

													</TR>
												</TABLE>
                                	</TD> --%>
                                <TD class="plainlabel">Địa chỉ xuất HĐ<FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel">
                                <INPUT name="diachixhd" id="diachixhd" type="text" value="<%= nppBean.getDiaChiXuatHoaDon()%>"  ></TD>
						  </TR>
						  
						 
						    <TR>
							  	<TD class="plainlabel">Mã số thuế <FONT class="erroralert"> *</FONT></TD>
                                <TD  class="plainlabel"><INPUT name="masothue" id="masothue" type="text" value="<%= nppBean.getmaSoThue() %>" ></TD>
                                
                                <TD class="plainlabel">Kho Hàng Bán  <FONT class="erroralert"> *</FONT></TD>
								 <TD  class="plainlabel"><SELECT name="khottid" id="khottid" >
								    <option value=""></option>
								      <%if(rs_khott!=null) 
								      try{while(rs_khott.next()){ 
								    	if(rs_khott.getString("pk_seq").trim().equals(nppBean.getKhoTT().trim())){ %>
								      		<option value='<%=rs_khott.getString("pk_seq")%>' selected><%=rs_khott.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=rs_khott.getString("pk_seq")%>'><%=rs_khott.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>	</TD>
                        		<%-- <TD class="plainlabel"> Tồn an toàn <FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel">
                                <INPUT name="tonantoan" onkeyup="Dinhdang(this)"	id="tonantoan" type="text" value="<%= nppBean.getTonAnToan() %>">
                                </TD> --%> 
                                <TD class="plainlabel">Tần suất đặt hàng <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<input type="text" name="tsdathang" value="<%= nppBean.getTSdathang()%>" onkeyup="Dinhdang(this)" >
								</TD>      
						  </TR>	
						
						 
							<TR>
							   	 <TD class="plainlabel">Khu vực: <FONT class="erroralert"> *</FONT></TD>
								 <TD  class="plainlabel">
									
								<SELECT name="kvId" id="KhuVuc" onChange="submitform();">
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
								
								 <%-- <TD class="plainlabel">Mua hàng từ: <!-- Ngân hàng --><FONT class="erroralert"> *</FONT></TD>
								 <TD  class="plainlabel">
								 <input type="text" name="NganHangId" value="<%=nppBean.getNganHangId() %>" />
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
								 
								 <TD class="plainlabel">Chi nhánh<FONT class="erroralert"> *</FONT></TD>
								 <TD  class="plainlabel">
								 	<input type="text" name="ChiNhanhId" value="<%=nppBean.getChiNhanhId() %>"/>
								 </TD>
								 <TD class="plainlabel"><!-- Giấy phép kinh doanh --> Fax: <FONT class="erroralert"> *</FONT> </TD>
								 <TD  class="plainlabel" colspan=5>
								 <INPUT name="fax" id="fax" type="text" value="<%= nppBean.getFax() %>" size="10">
								 <%-- <INPUT name="gpkd" id="gpkd" type="text" value="<%= nppBean.getGiayphepKD() %>" size="10"> --%></TD>
							</TR>
							<%-- <TR>
							   	 <TD class="plainlabel">Chủ nhà phân phối<!-- Chủ tài khoản --><FONT class="erroralert"> *</FONT></TD>
								 <TD class="plainlabel"><INPUT name="ChuTaiKhoan" id="ChuTaiKhoan" type="text" value="<%= nppBean.getChuTaiKhoan() %>" size="10">
								 						<INPUT name="ChuNhaPhanPhoi" id="ChuNhaPhanPhoi" type="text"
												value="<%= nppBean.getChuNhaPhanPhoi() %>" size="10">
								 </TD>
								 
								 <TD class="plainlabel">Mã khách hàng <FONT class="erroralert"> *</FONT></TD>
								 <TD class="plainlabel"><INPUT name="maSAP" id="maSAP" type="text" value="<%= nppBean.getMaSAP() %>" size="10"></TD>
								 
								 <TD class="plainlabel"><!-- Giấy phép kinh doanh --> Fax: <FONT class="erroralert"> *</FONT> </TD>
								 <TD  class="plainlabel" colspan=5>
								 <INPUT name="fax" id="fax" type="text" value="<%= nppBean.getFax() %>" size="10">
								 <INPUT name="gpkd" id="gpkd" type="text" value="<%= nppBean.getGiayphepKD() %>" size="10"></TD>
							</TR> --%>
							
							<TR>
							   	 <TD class="plainlabel">Ngày bắt đầu <FONT class="erroralert"> *</FONT></TD>
								 <TD  class="plainlabel">								 		
								 <input class="days" type="text" name="tungay" size="15" value="<%= nppBean.getTungay()%>">						 
          						 </TD>
          						 <TD class="plainlabel">Ngày kết thúc <FONT class="erroralert"> *</FONT></TD>
          						 <TD class="plainlabel">								 								 
                                 	<input class="days" type="text" name="denngay" size="15" value="<%= nppBean.getDenngay()%>">
          						 </TD>
          						 <TD class="plainlabel"><!-- Fax--> Trung tâm phân phối: <FONT class="erroralert"> *</FONT></TD>
								 <TD class="plainlabel">
								 
								 <select name="ttppId" id="ttppId" class="select2" multiple="multiple" style="width: 200px;" >
										<option value="" ></option>
										<% if(ttppRs != null) { 
											while(ttppRs.next())
											{
												if (nppBean.getTtppId() .indexOf(ttppRs.getString("id")) >=0) {%>
													<option value="<%= ttppRs.getString("Id") %>" selected="selected" ><%= ttppRs.getString("TEN") %></option>
												<% }
												else { %>
													<option value="<%= ttppRs.getString("Id") %>" ><%= ttppRs.getString("TEN") %></option>
												<% }
											}
											ttppRs.close();
										} %>
									</select>
								 <%-- <INPUT name="fax" id="fax" type="text" value="<%= nppBean.getFax() %>" size="10"> --%></TD>
							</TR>
							
							
							<TR>
							   	 <TD class="plainlabel">Số ngày nợ <FONT class="erroralert"> *</FONT></TD>
								 <TD  class="plainlabel">								 		
								 <input type="text" name="songayno"  value="<%= nppBean.getSongayno()%>">						 
          						 </TD>
          						 <TD class="plainlabel">Số tiền nợ<FONT class="erroralert"> *</FONT></TD>
          						 <TD class="plainlabel">								 								 
                                 	<input type="text" name="sotienno" value="<%= nppBean.getSotienno()%>">
          						 </TD>
          						 <TD class="plainlabel">Quy trình bán hàng: <font class="erroralert">*</font></TD>
								 <TD class="plainlabel">
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
								 </TD>
							</TR>
							
							<TR>
							<TD class="plainlabel"><!-- Loại<FONT class="erroralert"> *</FONT> --></TD>
							 <TD  class="plainlabel">
								 <%-- <SELECT name="loai" id="loai" onChange="submitform();">
								    <option value=""></option>
								      <%
								      if(loainppRs!=null)
								      try{while(loainppRs.next()){ 
								    	if(loainppRs.getString("pk_seq").equals(nppBean.getLoaiNpp()  )){ %>
								      		<option value='<%=loainppRs.getString("pk_seq")%>' selected><%=loainppRs.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=loainppRs.getString("pk_seq")%>'><%=loainppRs.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>	 --%>
								</TD>
								<TD class="plainlabel" ><!-- Trực thuộc<FONT class="erroralert"> *</FONT> --></TD>
								<TD class="plainlabel"  >
								<%-- 	<select name="tructhuocId" id="tructhuocId" class="select2" style="width: 200px;" >
										<option value="" ></option>
										<% if(tructhuocRs != null) { 
											while(tructhuocRs.next())
											{
												if(tructhuocRs.getString("Id").equals(nppBean.getTructhuocId())) { %>
													<option value="<%= tructhuocRs.getString("Id") %>" selected="selected" ><%= tructhuocRs.getString("TEN") %></option>
												<% }
												else { %>
													<option value="<%= tructhuocRs.getString("Id") %>" ><%= tructhuocRs.getString("TEN") %></option>
												<% }
											}
											tructhuocRs.close();
										} %>
									</select> --%>
								</TD>
								
								<TD class="plainlabel" ><!-- Trung tâm phân phối<FONT class="erroralert"> *</FONT> --></TD>
								<TD class="plainlabel"  >
									<%-- <select name="ttppId" id="ttppId" class="select2" multiple="multiple" style="width: 200px;" >
										<option value="" ></option>
										<% if(ttppRs != null) { 
											while(ttppRs.next())
											{
												if (nppBean.getTtppId() .indexOf(ttppRs.getString("id")) >=0) {%>
													<option value="<%= ttppRs.getString("Id") %>" selected="selected" ><%= ttppRs.getString("TEN") %></option>
												<% }
												else { %>
													<option value="<%= ttppRs.getString("Id") %>" ><%= ttppRs.getString("TEN") %></option>
												<% }
											}
											ttppRs.close();
										} %>
									</select> --%>
								</TD>
							</TR>
							
							
							<TR>
          						 <TD class="plainlabel"><!--  Số tài khoản Trực thuộc: <FONT class="erroralert"> *</FONT> --></TD>
								 <TD  class="plainlabel">
								 <%-- <select name="tructhuocId" id="tructhuocId" class="select2" style="width: 200px;" >
										<option value="" ></option>
										<% if(tructhuocRs != null) { 
											while(tructhuocRs.next())
											{
												if(tructhuocRs.getString("Id").equals(nppBean.getTructhuocId())) { %>
													<option value="<%= tructhuocRs.getString("Id") %>" selected="selected" ><%= tructhuocRs.getString("TEN") %></option>
												<% }
												else { %>
													<option value="<%= tructhuocRs.getString("Id") %>" ><%= tructhuocRs.getString("TEN") %></option>
												<% }
											}
											tructhuocRs.close();
										} %>
									</select> --%>
								 <%-- <INPUT name="sotk" id="sotk" type="text" value="<%= nppBean.getSotk() %>" size="10"> --%>
								 
								 </TD>
								  <TD class="plainlabel" width="140px" > <!-- Trực thuộc <font class="erroralert">*</font>  --> </TD>
                                <TD class="plainlabel">
                                 	<%-- <select name="tructhuocId" id="tructhuocId" class="select2" style="width: 200px;" >
										<option value="" ></option>
										<% if(tructhuocRs != null) { 
											while(tructhuocRs.next())
											{
												if(tructhuocRs.getString("Id").equals(nppBean.getTructhuocId())) { %>
													<option value="<%= tructhuocRs.getString("Id") %>" selected="selected" ><%= tructhuocRs.getString("TEN") %></option>
												<% }
												else { %>
													<option value="<%= tructhuocRs.getString("Id") %>" ><%= tructhuocRs.getString("TEN") %></option>
												<% }
											}
											tructhuocRs.close();
										} %>
									</select> --%>
                                
                                </TD>
                                <td class="plainlabel"></td>
                                    <td class="plainlabel"></td>
								 
							</TR>
							
							<TR>	 
								 
								 <%-- <TD class="plainlabel"><div align="right">Pri. = Sec. </div></TD>
                                <TD class="plainlabel">
                               	<%  if (nppBean.getPriSec().equals("1")){%>
                                      <input name="prisec" type="checkbox" value ="1" checked>
                                <%} else {%>
                                       <input name="prisec" type="checkbox" value ="0">
                                <%} %>
                                                                     
                              </TD> --%>
								 
								 
								<TD class="plainlabel"><div align="right">Hoạt động </div></TD>
                              	<TD  class="plainlabel" colspan="5">
                               	<%  if (nppBean.getTrangthai().equals("1")){%>
                                      <input name="TrangThai" type="checkbox" value ="1" checked>
                                <%} else {%>
                                       <input name="TrangThai" type="checkbox" value ="0">
                                <%} %>
                                                                     
                              </TD>
                              
                              
                              
							</TR> 
						</TABLE>
						</FIELDSET>

						<FIELDSET>
						<LEGEND class="legendtitle" style="color:black">Chọn đơn vị kinh doanh</LEGEND>
                        <TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
                            <TR class="tbheader">
                                <TH width="13%">Đơn vị kinh doanh </TH>
                                <TH width="13%">Nhà cung cấp </TH>
                                <TH width="13%">Chọn </TH>
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
							
						<%-- <FIELDSET>
                        <LEGEND class="legendtitle" style="color:black">Chọn giám sát bán hàng</LEGEND>
                        <TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
                            <TR class="tbheader">
                                <TH width="13%">Giám sát bán hàng</TH>
                                <TH width="13%">Kênh bán hàng </TH>
                                <TH width="13%">Số điện thoại </TH>
                                  <TH width="20%">Ngày bắt đầu </TH>
                                <TH width="20%">Ngày kết thúc </TH>   
                                <TH width="13%">Chọn </TH>
                            </TR>             		
                     		<%
								int j = 0;
								String light = "tblightrow";
								String dark = "tbdarkrow";
								
								try{if(gsbh_kbhSelected !=null)
									while(gsbh_kbhSelected.next()){ 
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
							     	<%j++;}}catch(java.sql.SQLException e){}
							     	
								try{
									if(gsbh_kbh!=null)
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
										<input name="gsbh_kbhList"  onclick="Kiemtra(this)" id="gsbh_kbhList" type="checkbox" value ="<%=gsbh_kbh.getString("gsbh_kbhId")%>"></TD>
									<%} %>
									</TR>
							     	<%j++;}
									}catch(java.sql.SQLException e){}
							  %>          		
                        </TABLE>                        
                        </FIELDSET> --%>
                        
                      
                        <%-- <FIELDSET>
                        <LEGEND class="legendtitle" style="color:black">Chọn ngày cho phép đặt hàng</LEGEND>
                        <TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
                            <TR class="tbheader">
                                <TH width="13%">Ngày</TH>                                                                
                                <TH width="13%">Chọn </TH>
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
										<TD align="center"><div align="left"><%= ngaydhSelected.getString("ngay") %> </div></TD>										
										<TD align="center">
											<input name="ngaydhList" id="ngaydhList" type="checkbox" value ="<%= ngaydhSelected.getString("pk_seq")%>" checked></TD>
										</TR>
							     		<%k++;}}catch(java.sql.SQLException e){}
								}

								
								if(!(ngaydh == null)){
								try{while(ngaydh.next()){ 
									if (k % 2 != 0) {%>						
										<TR class= <%=light1%> >
									<%} else {%>
										<TR class= <%= dark1%> >
								<%}%>
									<TD align="center"><div align="left"><%= ngaydh.getString("ngay") %> </div></TD>									
									<TD align="center">
										<% if(NgayDhIdSelected.indexOf(ngaydh.getString("pk_seq"))>=0){%>
										<input name="ngaydhList" checked id="ngaydhList" type="checkbox" value ="<%= ngaydh.getString("pk_seq")%>"></TD>
									<%}else{ %>
										<input name="ngaydhList" id="ngaydhList" type="checkbox" value ="<%= ngaydh.getString("pk_seq")%>"></TD>
									<%} %>
									</TR>
							     	<%k++;}}catch(java.sql.SQLException e){}
							 }
							  %>          		
                        </TABLE>                        
                        </FIELDSET>		 --%>											
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