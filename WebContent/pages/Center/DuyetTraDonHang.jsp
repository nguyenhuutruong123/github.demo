<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.donhangtrave.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%	String userId = (String) session.getAttribute("userId");  
     String userTen = (String) session.getAttribute("userTen");  	

IDonhangtraveList obj = (IDonhangtraveList)session.getAttribute("obj"); %>
<% List<IDonhangtrave> dhtvlist = (List<IDonhangtrave>)obj.getDhtvList(); %>
<% ResultSet ddkd = (ResultSet)obj.getDaidienkd(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
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
 function clearform()
 {
	 
	 document.forms["dhForm"].action1.value="guilai";
	 document.forms["dhForm"].submit();
 }
 function newform()
 {
	 document.forms["dhForm"].action1.value="taomoi";
	 document.forms["dhForm"].submit();
 }
 function submitform()
 {   
    document.forms["dhForm"].submit();
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

<form name="dhForm" method="post" action="../../DuyetdontrahangnppSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="action1" value="1" >
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD align="left" class="tbnavigation">
					   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation"> &nbsp;Quản lý bán hàng> Đơn hàng trả về
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%=userTen %>&nbsp;</tr>
						</TABLE>
					</TD>
		  </TR>
			<TR>
				<TD >
				<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm</LEGEND>
							<TABLE width="100%" cellpadding="6" cellspacing="0">
								
								<TR>
									<TD width="21%" class="plainlabel">Nhân viên bán hàng </TD>
									<TD class="plainlabel">
									<SELECT name="ddkdTen" onChange = "submitform();">
										  <option value=""> </option>
										  <% try{ while(ddkd.next()){ 
								    			if(ddkd.getString("ddkdId").equals(obj.getDdkdId())){ %>
								      				<option value='<%=ddkd.getString("ddkdId")%>' selected><%=ddkd.getString("ddkdTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=ddkd.getString("ddkdId")%>'><%=ddkd.getString("ddkdTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>	 
                                    </SELECT></TD>
							    </TR>
								
								<TR>
									<TD class="plainlabel">Trạng thái </TD>
									<TD class="plainlabel">
										<select name="trangthai" onChange="submitform();">
										 <% if(obj.getTrangthai().equals("1")){%>
										        <option value="1" selected>Chưa chốt</option>
											  	<option value="2" >Đã chốt</option>
											  	<option value=""></option>
												<%}else if(obj.getTrangthai().equals("2")){%>											
											 	<option value="2" selected>Đã chốt</option>
											 	<option value="1" >Chưa chốt</option>
											  	<option value="" ></option>
											  	
											<%}else{%>
												 <option value="" selected></option>
												 <option value="1" >Chưa chốt</option>
											  	<option value="2" >Đã chốt</option>											
											  	
											<%} %>
									          </select>
									</TD>
							    </TR>
								
								<TR>
									<TD class="plainlabel" >Từ ngày</TD>
									<TD class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
											    <input type="text" name="tungay" size="11">
											</TD><TD>
												 <a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'dhForm.tungay',false, 'yyyy-mm-dd'); return false;">
		  											&nbsp;<img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>
											</TD></TR>
										</TABLE>	
								</TR>
								<TR>
								  <TD class="plainlabel" >Đến ngày</TD>
							      <TD width="100%" class="plainlabel">
								  		<TABLE cellpadding="0" cellspacing="0">
										  	<TR><TD>
											  <input type="text" name="denngay" size="11">
										  	</TD><TD>
			                                    <a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'dhForm.denngay',false, 'yyyy-mm-dd'); return false;">
		  											&nbsp;<img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>
											</TD></TR>
										</TABLE>
							  	</TR>
								<TR>
									<TD class="plainlabel" colspan="3">
									<a class="button2" >
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

				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Đơn hàng bán &nbsp;&nbsp;&nbsp;
				
    	                                  
					<!-- <INPUT name="action" type="submit" value="Tao moi"> -->
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="3">
								<TR class="tbheader">
									<TH width="12%" align="center">Mã đơn hàng </TH>
									<TH width="12%" align="center">Mã khách hàng </TH>
									<TH width="17%" align="center">Tên khách hàng </TH>
									<TH width="10%" align="center">Trạng thái </TH>
									<TH width="9%" align="center">Ngày tạo </TH>
									<TH width="11%" align="center">Người tạo </TH>
									<TH width="10%" align="center">Ngày duyệt </TH>
									<TH width="11%" align="center">Người duyệt </TH>
									<TH width="8%" align="center">Tác vụ</TH>
								</TR>
								
								<% 
									IDonhangtrave donhangtrave = null;
									int size = dhtvlist.size();
									System.out.println("so" + size);
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										donhangtrave = dhtvlist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><%=donhangtrave.getId() %></TD>                                   
												<TD><div align="center"><%= donhangtrave.getKhId() %></div></TD>
												<TD align="left"><%=donhangtrave.getKhTen() %></TD>
												<% if (donhangtrave.getTrangthai().equals("1")){ %>
													<TD align="left">Chưa chốt</TD>
												<%}else{ %>
													<TD align="left">Đã chốt</TD>
												<%} %>
												<TD align="center"><%=donhangtrave.getNgaytao() %></TD>
												<TD align="left"><%=donhangtrave.getNguoitao()%></TD>
												<TD align="center"><%=donhangtrave.getNgaysua()%></TD>
												<TD align="left"><%=donhangtrave.getNguoisua()%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<%if(donhangtrave.getTrangthai().equals("1")){ %>
													<TD>
														<A href = "../../DonhangtraveSvl?userId=<%=userId%>&chot=<%=donhangtrave.getId() %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa đơn hàng này?')) return false;"></A></TD>
													</TR>
													<%}else if(donhangtrave.getTrangthai().equals("2")){ %>
													<TR><TD>
														<A href = "../../DuyetdontrahangnppDislaySvl?userId=<%=userId%>&display=<%=donhangtrave.getId() %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
													</TD></TR>
													<%} %>
												</TABLE>
												</TD>
											</TR>
								<% m++; }%>
								
								<TR>
									<TD align="center" colspan="10" class="tbfooter">
									|<   <   1 to 20 of 100   >   >| </TD>
								</TR>
							</TABLE>
							</TD>
						</TR>
					</TABLE>
					</FIELDSET>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<!--end body Dossier--></TD>
	</TR>
	<TR align="center" height="30" id="findoc">
		<TD bgcolor="#FFFFFF" class="footer"></TD>
		<TD bgcolor="#FFFFFF" class="footer" align="center" colspan="4"><A
			href="#"></A></TD>

	</TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
	if(!(ddkd == null))
		ddkd.close();
	obj.DBclose();
%>