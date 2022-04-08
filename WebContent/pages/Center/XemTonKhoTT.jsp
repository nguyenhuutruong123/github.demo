<%@page import="geso.dms.center.beans.khott.imp.TonKhoTT"%>
<%@page import="geso.dms.center.beans.khott.ITonKhoTT"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT"%>
<%@page import="geso.dms.center.beans.khott.IKhoTT"%>
<%@page import="geso.dms.center.beans.khott.imp.KhoTT"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.kho.IKho" %>
<%@ page  import = "geso.dms.center.beans.kho.IKhoList" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	  String khottid=(String)session.getAttribute("khottid");
	  String  sanphamid=(String)session.getAttribute("sanphamid");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
  ResultSet rs_khott=(ResultSet)session.getAttribute("rs_khott");
ResultSet rs_sanpham=(ResultSet)session.getAttribute("rs_sanpham");
 ITonKhoTT obj = (ITonKhoTT)session.getAttribute("obj"); %>
<% List<ITonKhoTT> kholist = obj.getListTonKhoTT(); %>
<% ResultSet dvkd = (ResultSet)obj.getdvkd(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
    document.khoForm.khott.value = "0";    
    document.khoForm.sanphamid.value = "0";	
    submitform();
}

function submitform()
{
	document.forms['khoForm'].action.value= 'search';
	document.forms['khoForm'].submit();
}

function newform()
{
	document.forms['khoForm'].action.value= 'new';
	document.forms['khoForm'].submit();
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

<form name="khoForm" method="post" action="../../TonKhoTTSvl" charset="UTF-8">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId%>' size="30">
<input type="hidden" name="action" value='1'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		&nbsp;Quản lý tồn kho> Kho trung tam > Xem tồn kho</TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td> 
						    </tr>
   
						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="100%" align="left"><FIELDSET>
						<LEGEND class="legendtitle">Tiêu chí tìm kiếm </LEGEND>

							<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
							<TR>
									  	<TD class="plainlabel" width="19%">Đơn vị kinh doanh</TD>
									  	<TD class="plainlabel" >
									  	<select name="dvkdId"  onchange="javascript:submitform()">
									  	<option value="0" > </option>
									  	<% if(dvkd!=null){
									  		try{
									  			while(dvkd.next()){
									  				if(dvkd.getString("pk_seq").equals(obj.getdvkdId())){
									  					%>
									  					<option value="<%= dvkd.getString("pk_seq")%>" selected> <%= dvkd.getString("donvikinhdoanh") %> </option>
									  					<%
									  				}else{
									  					%>
									  					<option value="<%= dvkd.getString("pk_seq")%>"> <%= dvkd.getString("donvikinhdoanh") %> </option>
									  					<%
									  				}
									  			
									  			}
									  		}catch(Exception er){
									  		}
									  	}
									  		%>	  
									  	</select>
									  
									  	</TD>
								</TR>
								<TR>
									  	<TD class="plainlabel" width="19%">Chọn kho trung tâm</TD>
									  	<TD class="plainlabel" >
									  	<select name="khott" onchange="javascript:submitform()">
									  	<option value="0" > </option>
									  	<% if(rs_khott!=null){
									  		try{
									  			while(rs_khott.next()){
									  				if(rs_khott.getString("pk_seq").equals(khottid)){
									  					%>
									  					<option value="<%= rs_khott.getString("pk_seq")%>" selected="selected"> <%= rs_khott.getString("ten") %> </option>
									  					<%
									  				}else{
									  					%>
									  					<option value="<%= rs_khott.getString("pk_seq")%>"> <%= rs_khott.getString("ten") %> </option>
									  					<%
									  				}
									  			
									  			}
									  		}catch(Exception er){
									  		}
									  	}
									  		%>	  
									  	</select>
									  
									  	</TD>
								</TR>
								
							
								<TR >
								<TD class="plainlabel" >
										Chọn sản phẩm 
									</TD>
									<TD class="plainlabel">
										<input type ="text" name ="sanphamid" value ="<%=sanphamid %>" onchange="submitform()">
									</TD>
								</TR>	
								<TR>
									<TD class="plainlabel" colspan="2">
                           		<a class="button2" href="javascript:clearform()">
							<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                                
									</TD>
								</TR>
							</TABLE>
							</FIELDSET>
						</TD>	
					</TR>
				</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">		
			    <TR>
					<TD align="left" >
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Danh sách kho &nbsp;&nbsp;		
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="6%">Số TT</TH>
												<TH width="10%">Mã SP</TH>
												<TH width="33%"> Tên sản phẩm </TH>
												<TH width="7%">Mã kho </TH>	
												<TH width="10%">Số lượng</TH>
												<TH width="10%">Đã đặt hàng</TH>
												<TH width="15%">SL Cho Phép ĐH</TH>
												
											</TR>
								<% 
								ITonKhoTT pnkho = null;
									int size = kholist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										pnkho = (ITonKhoTT)kholist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
										    <TD><%=(m+1) %></TD>
											<TD align="left"><%=pnkho.getMaSanPham()%></TD> 
											<TD><%=pnkho.getTenSanPham() %></TD>                                  
											<TD><%=pnkho.getKhoTTId() %></TD>
											<TD align="center"><%=pnkho.getSoluong() %></TD>
											<TD align="center"><%=pnkho.getBooked() %></TD>												
											<TD align="center"><%=pnkho.getAvailable() %></TD>
												</TR>
										<% 	m++; }%>
								
										
								<TR>
									<TD align="center" colspan="11" class="tbfooter">&nbsp;</TD>
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
<%}%>