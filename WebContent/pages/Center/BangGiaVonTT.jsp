<%@page import="geso.dms.center.beans.banggiavontt.IBangGiaVonTT"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.center.beans.banggiamuanpp.IBanggiamuanpp" %>
<%@ page  import = "geso.dms.center.beans.banggiamuanpp.IBanggiamuanppList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IBangGiaVonTT obj = (IBangGiaVonTT)session.getAttribute("obj"); %>
<% List<IBangGiaVonTT> bglist = (List<IBangGiaVonTT>)obj.getListBangGia(); %>
<% ResultSet rs_dvkd = (ResultSet)session.getAttribute("rs_dvkd")  ;%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">
 function submitform()
{   
   document.forms['bgmuanppForm'].action.value='search';
   document.forms["bgmuanppForm"].submit();
}

 function newform()
{   
	document.forms['bgmuanppForm'].action.value='new';
   	document.forms['bgmuanppForm'].submit();
}
 
 function clearform()
 {
     document.bgmuanppForm.bgTen.value = "";
     document.bgmuanppForm.dvkdId.selectedIndex = 0;
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

<form name="bgmuanppForm" method="post" action="../../BangGiaVonTTSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type="hidden" name="action" value='1'>


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR>
				<TD align="left" class="tbnavigation">
				  <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR height="22">
					  	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; Dữ liệu nền sản phẩm &gt; Bảng giá vốn hàng bán </TD>
   						<TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>  &nbsp;</TD>
					</TR>
				  </TABLE></TD>
			</TR>
		</TABLE>
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR>
					<TD>
					<TABLE border="0" width="100%" >
						<TR>
							<TD width="100%" align="left"><FIELDSET>
							<LEGEND class="legendtitle">Tiêu chí tìm kiếm &nbsp;</LEGEND>

							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<TR>
									<TD width="21%" class="plainlabel">Tên bảng giá</TD>
									<TD width="79%" class="plainlabel">
									<INPUT name="bgTen" type="text" size="40" value='<%=obj.getTen() %>' onChange = "submitform();"/></TD>
								</TR>
								<TR>
								    <TD class="plainlabel">Đơn vị kinh doanh </TD>
								      <TD class="plainlabel">
								      	<SELECT name="dvkdId" onChange = "submitform();">								      
								  	 		<option value =""></option>
								  	 <% try{ while(rs_dvkd.next()){ 
								    	if(rs_dvkd.getString("pk_seq").equals(obj.getDvkdId())){ %>
								      		<option value='<%=rs_dvkd.getString("pk_seq") %>' selected><%=rs_dvkd.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=rs_dvkd.getString("pk_seq") %>'><%=rs_dvkd.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
									</SELECT></TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">
									<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="" onClick="clearform()">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                  </TD>								
									<TD class="plainlabel">&nbsp;</TD>										
								</TR>							  
							</TABLE>

							</FIELDSET>
							</TD>
						</TR>
					</TABLE>
					</TD>
				</TR>

				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Bảng giá bán cho NPP &nbsp;&nbsp;&nbsp;
					<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="" onClick="newform();">Tạo mới </a>    
							</LEGEND>
	
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="18%">Tên bảng giá </TH>
									<TH width="14%">Đơn vị kinh doanh </TH>									
									<TH width="8%">Ngày tạo</TH>
									<TH width="12%">Người tạo </TH>
									<TH width="8%">Ngày sửa </TH>
									<TH width="12%">Người sửa </TH>
									<TH width="10%" align="center">&nbsp;Tác vụ</TH>
								</TR>
						<% 
							int m = 0;
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							
							while(m<bglist.size()){
								IBangGiaVonTT banggia=bglist.get(m);
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
							  <%} else {%>
									<TR class= <%= darkrow%> >
							  <%}%>
										<TD align="left"><%= banggia.getTen() %></TD>
										<TD align="center"><%=banggia.getDvkdTen() %></TD>
										<TD align="center"><%=banggia.getNgaytao() %></TD>
										<TD align="center"><%= banggia.getNguoitao()%></TD>	
										<TD align="center"><%= banggia.getNgaysua()%></TD>
										<TD align="center"><%= banggia.getNguoisua() %></TD>			
										<TD align="center">
											<TABLE border = 0 cellpadding="0" cellspacing="0">
												<TR><TD>
												<A href = "../../BangGiaVonTTNewSvl?userId=<%=userId%>&update=<%=banggia.getId() %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
											</TD>
											<TD>&nbsp;</TD>
											<TD>
												<A href = "../../BangGiaVonTTNewSvl?userId=<%=userId%>&display=<%=banggia.getId() %>"><img src="../images/Display20.png" alt="Chon Nha Phan Phoi" title="Chọn nhà phân phối" width="20" height="20" longdesc="Chon Nha Phan Phoi" border = 0></A>
											</TD>
											<TD>&nbsp;</TD>
																						
											<TD>
												<A href = "../../BanggiamuanppSvl?userId=<%=userId%>&delete=<%= banggia.getId() %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa bảng giá này ?')) return false;"></A></TD>
											</TR>
											</TABLE>
										</TD>
									</TR>
								<%m++; }%>
								<TR>
									<TD align="center" colspan="11" class="tbfooter">&nbsp;	</TD>
								</TR>
							</TABLE>
							</TD>
						</TR>
					</TABLE>

					</FIELDSET>
					</TD>
				</TR>			
		</TABLE>
	</TD>
	</TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<% }%>