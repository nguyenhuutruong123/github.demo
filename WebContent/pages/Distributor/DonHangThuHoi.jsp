<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.donhangthuhoi.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IDonhangthuhoiList obj = (IDonhangthuhoiList)session.getAttribute("obj"); %>
<% List<IDonhangthuhoi> dhthList = (List<IDonhangthuhoi>)obj.getDhthList(); %>
<% ResultSet nvgn = (ResultSet)obj.getNhanvienGN(); %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
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

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="javascript" type="text/javascript">
	function confirmLogout()
	{
	   if(confirm("Ban co muon dang xuat?"))
	   {
			top.location.href = "home.jsp";
	   }
	   return
	 }
	function submitform()
	{   
	   document.forms["dhthForm"].submit();
	}
	function clearform()
	{
		document.forms["dhthForm"].nvgnTen.value="";
		document.forms["dhthForm"].trangthai.value="";
		document.forms["dhthForm"].tungay.value="";
		submitform();
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

<form name="dhthForm" method="post" action="../../DonhangthuhoiSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
<input type="hidden" name="nppTen" value='<%= obj.getNppTen() %>'>
<input type="hidden" name="nvgn_Ten" value=''>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
			<TR>
				<TD align="left" class="tbnavigation">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
   							<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý bán hàng > Đơn hàng thu hồi</TD>
   							<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= obj.getNppTen() %> </TD>
						</tr>
					</table>
				</TD>
		  	</TR>
		</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
			<TR>
				<TD >
					<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>
					<TABLE  width="100%" cellspacing="0" cellpadding="3">
						<TR class="tblightrow">
							<TD  class="plainlabel">Nhân viên giao nhận </TD>
							<TD colspan="3" class="plainlabel"> 
				 			<SELECT name="nvgnTen" id="nvgnList" onChange = "submitform();">
					 			 <option value="">&nbsp;</option>
								  <% if(nvgn != null){
									  try{ while(nvgn.next()){ 
						    			if(nvgn.getString("nvgnId").equals(obj.getNvgnId())){ %>
						      				<option value='<%=nvgn.getString("nvgnId")%>' selected><%=nvgn.getString("nvgnTen") %></option>
						      			<%}else{ %>
						     				<option value='<%=nvgn.getString("nvgnId")%>'><%=nvgn.getString("nvgnTen") %></option>
						     			<%}}}catch(java.sql.SQLException e){} }%>	 
                               			</SELECT></TD>
						</TR>
						<TR>
							<TD width="19%" class="plainlabel">Trạng thái </TD>
							<TD width="81%" colspan="3" class="plainlabel">
								<SELECT name ="trangthai" onChange = "submitform();">                                  
                                   <% if (obj.getTrangthai().equals("1")){%>
                                         <option value="1" selected>Đã chốt</option>
                                         <option value="0">Chưa chốt</option>
                                         <option value=""> </option>                                        
                                   <%}else if(obj.getTrangthai().equals("0")) {%>
                                         <option value="0" selected>Chưa chốt</option>
                                         <option value="1" >Đã chốt</option>
                                         <option value="" > </option>                                         
                                   <%}else{%>                                          
                                         <option value="" selected> </option>
                                         <option value="1" >Đã chốt</option>
                                         <option value="0" >Chưa chốt</option>
                                   <%}%>
                                </SELECT>
							</TD>
						</TR>
						<TR>
							<TD class="plainlabel" >Từ ngày</TD>
							<TD class="plainlabel">
								<TABLE cellpadding="0" cellspacing="0">
									<TR><TD>
									    <input class="days" type="text" name="tungay" value="<%= obj.getTungay() %>" size="11">
									</TD></TR>
								</TABLE>	
						</TR>
						<TR>
							<TD class="plainlabel" colspan="3">
							<a class="button2"  >
	<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm</a>&nbsp;&nbsp;&nbsp;&nbsp;	
							<a class="button2"  onclick="clearform()">
	<img style="top: -4px;" src="../images/button.png" >Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
							<!--  
							  <INPUT name="action" type="submit" value="Tim kiem">&nbsp;
							  
                              <INPUT name="reinitialiser" type="reset" value="Nhap lai"></TD>-->
						</TR>
					</TABLE>

				  </FIELDSET>
				</TD>	
			</TR>
		</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle"> Đơn hàng thu hồi </LEGEND>
					<TABLE class="" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="2">
									<TR class="tbheader">
											<TH >Mã DH thu hồi</TH>
											<TH >Mã phiếu xuất</TH>
											<TH >Đơn hàng</TH>
											<TH >Trạng thái</TH>
											<TH >Ngày tạo</TH>
											<TH align="left"> Người tạo </TH>
											<TH >Ngày xửa</TH>
											<TH align="left"> Người sửa </TH>
											<TH >Tác vụ</TH>
									</TR>
									<% 
									IDonhangthuhoi dhth = null;
									int size = dhthList.size();
									int m = 0;
									while (m < size){
										dhth = dhthList.get(m);
										if (m % 2 != 0) {%>						
											<TR class= "tblightrow">
										<%} else {%>
											<TR class= "tbdarkrow">
										<%}%>
												<TD align="center"><%= dhth.getId() %></TD>                                   
												<TD align="center"><%= dhth.getPhieuxuatkho() %></TD>
												<TD align="center"><%= dhth.getDonhang() %></TD>  
												 <% if (dhth.getTrangthai().equals("1")){ %>
                                                    <TD align="center">Đã chốt</TD>
                                                <%}else{%>
                                                    <TD align="center">Chưa chốt</TD>
                                                <%}%>                 
												<TD align="center"><%= dhth.getNgaytao()%></TD>
												<TD align="left"><%= dhth.getNguoitao()%></TD>
												<TD align="center"><%= dhth.getNgaysua()%></TD>
												<TD align="left"><%= dhth.getNguoisua()%></TD>
												<TD align="center" valign="middle">
													<A href = "../../DonhangthuhoiPdfSvl?userId=<%=userId%>&display=<%= dhth.getId() %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
												</TD>
											</TR>
								<%m++; }%>
								
								<TR>
									<TD align="center" colspan="10" class="tbfooter">
									|<   <   1 to 20 of 100   >   >|
									</TD>
								</TR>
							
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			</FIELDSET>
		</TD>
	</TR>
</TABLE>
		<!--end body Dossier--></TD>
</TR>
</TABLE>
</form>
</BODY>
</html>
<%
	try
	{
		if(!(nvgn == null))
			nvgn.close();
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
	}catch(java.sql.SQLException e){}
	}
%>