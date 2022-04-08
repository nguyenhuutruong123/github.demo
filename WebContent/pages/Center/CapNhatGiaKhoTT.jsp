<%@page import="geso.dms.center.beans.khott.ICapNhatGiaKhoTT"%>
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

	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
  ResultSet rs_khott=(ResultSet)session.getAttribute("rs_khott");

 ICapNhatGiaKhoTT obj = (ICapNhatGiaKhoTT)session.getAttribute("obj"); %>
<% List<ICapNhatGiaKhoTT> kholist = obj.getListTonKhoTT(); %>
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
function loadbanggia()
{
	document.forms['khoForm'].action.value= 'loadbanggia';
	document.forms['khoForm'].submit();
}

function save()
{
	document.forms['khoForm'].action.value= 'save';
	document.forms['khoForm'].submit();
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

<form name="khoForm" method="post" action="../../CapNhatGiaKhoTTSvl" charset="UTF-8">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
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
							   		&nbsp;Quản lý kho> Quản lý kho trung tam >Cập nhật bảng giá kho trung tâm </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td> 
						    </tr>
   
						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>			
				    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width:100%"  rows="2"   ><%=obj.getMessage() %> </textarea>
									</FIELDSET>
							   </TD>
								</tr>
				<TR>
					<TD width="100%" align="left"><FIELDSET>
						<LEGEND class="legendtitle">Tiêu chí tìm kiếm </LEGEND>

							<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
								<TR>
									  	<TD class="plainlabel" width="19%">Chọn kho trung tâm:</TD>
									  	<TD class="plainlabel" >
									  	<select name="khott" onchange="loadbanggia();">
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
								<TR>
									<TD class="plainlabel" colspan="2">
                           		<a class="button2" href="javascript:save()">
							<img style="top: -4px;" src="../images/button.png" alt="">Cập nhật lại giá</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                                
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
							<LEGEND class="legendtitle">&nbsp;Danh sách bảng giá kho &nbsp;&nbsp;
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="3%">Số TT</TH>
												<TH width="10%">Mã SP</TH>
												<TH width="33%"> Tên sản phẩm </TH>
												<TH width="5%">Giá cũ</TH>
												<TH width="7%">Giá mới</TH>
											</TR>
								<% 
								ICapNhatGiaKhoTT pnkho = null;
									int size = kholist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										pnkho = (ICapNhatGiaKhoTT)kholist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
										    <TD><%=(m+1) %> <input  name="idsp" style="width:100%" type="hidden" value="<%=pnkho.getSanPhamId() %>" ></TD>
											<TD align="left"> <input readonly="readonly" name="masp" style="width:100%" type="text" value="<%=pnkho.getMaSanPham() %>" ></TD> 
											<TD><input name="tensp" readonly="readonly" style="width:100%" type="text" value="<%=pnkho.getTenSanPham() %>" > </TD>                                  
											<TD align="center"> <input name="giagoc" readonly="readonly" style="width:100%" type="text" value=" <%=Math.round(pnkho.getGiaGoc())%>" > </TD>
											<TD> <input name="giamoi" style="width:100%" type="text" value="<%= Math.round(pnkho.getGiaMoi())%>" >  </TD>
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