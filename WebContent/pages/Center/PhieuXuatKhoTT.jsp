<%@page import="geso.dms.center.beans.phieuxuatkhott.IPhieuXuatKhoTT"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.phieuxuatkho.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% IPhieuXuatKhoTT obj = (IPhieuXuatKhoTT)session.getAttribute("obj"); %>
<% List<IPhieuXuatKhoTT> pxkList = (List<IPhieuXuatKhoTT>)obj.getListPhieuXuatKho(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<%String userTen=(String)session.getAttribute("userTen"); %>
<%String trangthai=(String)session.getAttribute("trangthai"); %>
<%String tungay=(String)session.getAttribute("tungay"); %>
<%String denngay=(String)session.getAttribute("denngay"); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="javascript" type="text/javascript">
	function confirmLogout()
	{
	   if(confirm("Bạn có muốn đăng xuất?"))
	   {
			top.location.href = "home.jsp";
	   }
	   return
	 }
	function searchphieu()
	{   
		document.forms["pxkForm"].action.value="search";
	   document.forms["pxkForm"].submit();
	}
	function clearform()
	{
		 document.forms["pxkForm"].trangthai.value="";
		 document.forms["pxkForm"].tungay.value="";
		 document.forms["pxkForm"].denngay.value="";
		 document.forms["pxkForm"].action.value="search";
		 document.forms["pxkForm"].submit();
	}
	function newform(){
		
		document.forms["pxkForm"].action.value="new";
		 document.forms["pxkForm"].submit();
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

<form name="pxkForm" method="post" action="../../PhieuXuatKhoTTSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="new" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
			<TR>
				<TD align="left" class="tbnavigation">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
   							<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý bán hàng> In phiếu xuất kho</TD>
   							
   							<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
						</tr>
					</table>
				</TD>
		  	</TR>
		</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
			<TR>
				<TD >
					<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</LEGEND>
					<TABLE  width="100%" cellspacing="0" cellpadding="3">
						
						<TR>
							<TD width="19%" class="plainlabel">Trạng thái </TD>
							<TD width="81%" colspan="3" class="plainlabel">
								<SELECT name ="trangthai" onChange = "searchphieu();">                                  
                                   <% if (obj.getTrangthai().equals("1")){%>
                                         <option value="1" selected>Đã chốt</option>
                                         <option value="0">Chưa chốt</option>
                                         <option value="2">Đã hủy</option>
                                         <option value=""> </option>                                        
                                   <%}else if(obj.getTrangthai().equals("0")) {%>
                                         <option value="0" selected>Chưa chốt</option>
                                         <option value="1" >Đã chốt</option>
                                         <option value="2">đã hủy</option>
                                         <option value="" > </option>  
                                    <%}else if(obj.getTrangthai().equals("2")){ %>
                                          <option value="0" >Chưa chốt</option>
                                         <option value="1" >Đã chốt</option>
                                         <option value="2" selected >đã hủy</option>
                                         <option value="" > </option>                                       
                                   <%}else{%>                                          
                                         <option value="" selected> </option>
                                         <option value="1" >Đã chốt</option>
                                         <option value="0" >Chưa chốt</option>
                                          <option value="2">đã hủy</option>
                                   <%}%>
                                </SELECT>
							</TD>
						</TR>
						<TR>
							<TD class="plainlabel" >Từ ngày</TD>
							<TD class="plainlabel">
								<TABLE cellpadding="0" cellspacing="0">
									<TR><TD>
									    <input type="text" name="tungay" value="<%=tungay  %>" size="11">
									</TD><TD>
										 <a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'pxkForm.tungay',false, 'yyyy-mm-dd'); return false;">
		  											&nbsp;<img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>
									</TD></TR>
								</TABLE>
						</TR>
						<TR>
							<TD class="plainlabel" >Đến ngày</TD>
							<TD class="plainlabel">
								<TABLE cellpadding="0" cellspacing="0">
									<TR><TD>
									    <input type="text" name="denngay" value="<%= denngay %>" size="11">
									</TD><TD>
										 <a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'pxkForm.denngay',false, 'yyyy-mm-dd'); return false;">
		  											&nbsp;<img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>
									</TD></TR>
								</TABLE>
						</TR>
						<TR>
							<TD class="plainlabel" colspan="3">
							<a class="button2" href="javascript:searchphieu()">
	<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;
	
							<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
	
							
							<!-- 
							  <INPUT name="action" type="submit" value="Tim kiem">&nbsp;
                              <INPUT name="reinitialiser" type="reset" value="Nhap lai"></TD>
                               -->
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
					<LEGEND class="legendtitle"> &nbsp;&nbsp;&nbsp;&nbsp;Phiếu xuất kho &nbsp;&nbsp;&nbsp;&nbsp;
						<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
					
					 <!--  <INPUT name="action" type="submit" value="Tao moi">-->	
					</LEGEND>
					<TABLE class="" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="2">
									<TR class="tbheader">
											<TH align="center">Mã phiếu</TH>	
											<TH align="center">Ngày lập</TH>
											<TH align="center">Trạng thái</TH>
											<TH align="center">Ngày tạo</TH>
											<TH align="center">Người tạo </TH>
											<TH align="center">Ngày sửa</TH>
											<TH align="center"> Người sửa </TH>
											<TH align="center">Tác vụ</TH>
									</TR>
									<% 
									IPhieuXuatKhoTT pxk = null;
									int size = pxkList.size();
									int m = 0;
									while (m < size){
										pxk = pxkList.get(m);
										if (m % 2 != 0) {%>						
											<TR class= "tblightrow">
										<%} else {%>
											<TR class= "tbdarkrow">
										<%}%>
												<TD align="center"><%= pxk.getID() %></TD>                                   
												<TD align="center"><%= pxk.getNgaylap() %></TD> 
												 <% if (pxk.getTrangthai().equals("1")){ %>
                                                    <TD align="center">Đã chốt</TD>
                                                    <%}else if(pxk.getTrangthai().equals("2")) { %>
                                                    <TD align="center">Đã hủy</TD>
                                                <%}else{%>
                                                    <TD align="center">Chưa chốt</TD>
                                                <%}%>                 
												<TD align="center"><%= pxk.getNgaytao()%></TD>
												<TD align="left"><%= pxk.getNguoitao()%></TD>
												<TD align="center"><%= pxk.getNgaysua()%></TD>
												<TD align="left"><%= pxk.getNguoisua()%></TD>
												<TD align="center" valign="middle">
													<% if(pxk.getTrangthai().equals("0")) {%>
														<A href = "../../PhieuXuatKhoTTNewSvl?userId=<%=userId%>&update=<%= pxk.getID() %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Sửa Phiếu Xuất" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
														<A href = "../../PhieuXuatKhoTTNewSvl?userId=<%=userId%>&delete=<%=pxk.getID()%>"><img src="../images/Delete20.png" title="Xóa" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa phiễu xuất kho này?')) return false;"> </A>
														<A href = "../../PhieuXuatKhoTTNewSvl?userId=<%=userId%>&chot=<%= pxk.getID() %>"><img src="../images/Chot.png" alt="Chot phieu xuat" title="Chốt Phiếu Xuất" width="20" height="20" longdesc="Chot phieu xuat" border = 0></A>
													<%}else{ %>
														<A href = "../../PhieuXuatKhoTTNewSvl?userId=<%=userId%>&display=<%= pxk.getID() %>"><img src="../images/Display20.png" title="Hiển Thị Phiếu Xuất" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
													<%} %>
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
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>