<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.donhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IDonhangList obj = (IDonhangList)session.getAttribute("obj"); %>
<% List<IDonhang> dhlist = (List<IDonhang>)obj.getDhList(); %>
<% ResultSet ddkd = (ResultSet)obj.getDaidienkd(); %>

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
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="javascript" type="text/javascript">

 function submitform()
 {
 	document.forms['dhForm'].action.value= 'search';
 	document.forms['dhForm'].submit();
 }
 function newform()
 {
 	document.forms['dhForm'].action.value= 'new';
 	document.forms['dhForm'].submit();
 }
 function clearform()
 {   
	 document.forms['dhForm'].sodonhang.value= '';
	document.forms['dhForm'].tungay.value= '';
	document.forms['dhForm'].denngay.value= '';
	document.forms['dhForm'].ddkdTen.selectedIndex = 0; 
	submitform();
 }
 
 function Next()
 {
 	document.forms['dhForm'].action.value= 'next';
 	document.forms['dhForm'].submit();
 }

 function Prev()
 {
 	document.forms['dhForm'].action.value= 'prev';
 	document.forms['dhForm'].submit();
 }

 function XemTrang(page)
 {
 	document.forms['dhForm'].action.value= 'view';
 	document.forms['dhForm'].currentPage.value = page;
 	document.forms['dhForm'].submit();
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

<form name="dhForm" method="post" action="../../InDonhangSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
<input type="hidden" name="action" value="1">
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD align="left" class="tbnavigation">
					   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation"> &nbsp;Quản lý bán hàng > Đơn hàng bán
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= obj.getNppTen() %>&nbsp;</tr>
						</TABLE>
					</TD>
		  </TR>
			<TR>
				<TD >
				<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm</LEGEND>
							<TABLE width="100%" cellpadding="6" cellspacing="0">
								<TR>
									<TD width="21%" class="plainlabel">Số đơn hàng </TD>
									<TD class="plainlabel">
									  <input type="text" value="" name="sodonhang" >
									</TD>
							    </TR>
								<TR>
									<TD width="21%" class="plainlabel">Nhân viên bán hàng </TD>
									<TD class="plainlabel">
									<SELECT name="ddkdTen" onChange = "submitform();">
										  <option value=""> </option>
										  <% if(ddkd != null){
										  	try{ while(ddkd.next()){ 
								    			if(ddkd.getString("ddkdId").equals(obj.getDdkdId())){ %>
								      				<option value='<%=ddkd.getString("ddkdId")%>' selected><%=ddkd.getString("ddkdTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=ddkd.getString("ddkdId")%>'><%=ddkd.getString("ddkdTen") %></option>
								     	<%}} ddkd.close(); }catch(java.sql.SQLException e){} }%>	 
                                    </SELECT></TD>
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
							      <TD width="79%" class="plainlabel">
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
                                   <a class="button2" href="javascript:submitform()" >
    	                           <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm  </a> &nbsp;&nbsp;&nbsp;                         
									<a class="button2" href="javascript:clearform()">
	                               <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                       </TD>
								</TR>
							</TABLE>
				  </FIELDSET>
							</TD>	
				</TR>
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Đơn hàng bán &nbsp;&nbsp;&nbsp; 
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1px" cellpadding="4px">
								<TR class="tbheader">
									<th width="6%" align="center">Mã ĐH</th>
									<th width="20%" align="center">Khách hàng</th>
									<th width="6%" align="left"> Trạng thái</th>
									<th width="7%" align="center">Ngày DH</th>
									<th width="7%" align="center">Ngày tạo</th>
									<th width="6%" align="left">Người tạo</th>
									<th width="6%" align="center">Ngày sửa</th>
									<th width="6%" align="left">Người sửa </th>
									<th width="10%" align="center">Tác vụ</th>
								</TR>
								
								<% 
								if(dhlist.size() > 0)
								{
									IDonhang donhang = null;
									int size = dhlist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										donhang = dhlist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><%=donhang.getId() %></TD>                                   
												<TD align="left"><%= donhang.getKhId() %></TD>
												<TD align="left">
												<% if (donhang.getTrangthai().equals("0")){ %>
													<span> Chua Chot</span>
												<%}else if(donhang.getTrangthai().equals("1")){ %>
													<span><b> Da Chot</b></span>
												<%}else if(donhang.getTrangthai().equals("2")){ %>
													<span><u> Da Huy</u></span>
												<%}else if(donhang.getTrangthai().equals("6")){ %>
													<span><u> Thieu hang khuyen mai</u></span>
												<%} else{ %>
													<span><i style="color:red"> Da Xuat Kho</i></span>
												<% } %>
												</TD>
												<TD align="center"><%=donhang.getNgaygiaodich()%></TD>
												<TD align="center"><%=donhang.getNgaytao() %></TD>
												<TD align="left"><%=donhang.getNguoitao()%></TD>
												<TD align="center"><%=donhang.getNgaysua()%></TD>
												<TD align="left"><%=donhang.getNguoisua()%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0" >
													<TR><TD>
														<A href = "../../InDonhangSvl?userId=<%=userId%>&display=<%=donhang.getId() %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
														<A href = "../../InDonHangpdfSvl?userId=<%=userId%>&display=<%=donhang.getId() %>"><img src="../images/Printer30.png" alt="In don hang" title="In đơn hàng" width="20" height="20" longdesc="Hien thi" border = 0></A>
													</TD>
													</TR>
												</TABLE>
												</TD>
											</TR>
								<%m++; }}%>	
								<tr class="tbfooter" > 
									 <TD align="center" valign="middle" colspan="10" class="tbfooter">
										<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="XemTrang(1)"> &nbsp;
										
										<% if(obj.getCurrentPage() > 1){ %>
											<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev()"> &nbsp;
										<%}else{ %>
											<img alt="Trang Truoc" src="../images/prev_d.gif" style="cursor: pointer;"> &nbsp;
										<%} %>
										
										<%
											int[] listPage = obj.getListPages();
											for(int i = 0; i < listPage.length; i++){
										%>
										
										<% if(listPage[i] == obj.getCurrentPage()){ %>
											<a href="javascript:XemTrang(<%= listPage[i] %>)" style="color:white;"><%= listPage[i] %></a>
										<%}else{ %>
											<a href="javascript:XemTrang(<%= listPage[i] %>)"><%= listPage[i] %></a>
										<%} %>
											<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
										<%} %>
										
										<% if(obj.getCurrentPage() < obj.getLastPage()){ %>
											&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next()"> &nbsp;
										<%}else{ %>
											&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" style="cursor: pointer;"> &nbsp;
										<%} %>
									   <img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="XemTrang(-1)"> &nbsp;
									</TD>
								 </tr>  			
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
</TABLE>
</form>
</BODY>
</HTML>
<%
	try
	{
		if(!(ddkd == null))
			ddkd.close();
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
	}catch(java.sql.SQLException e){}
	}
%>