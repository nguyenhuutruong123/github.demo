<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
  	ResultSet thuong = obj.getthuong();
   	ResultSet vung = obj.getvung();
   	ResultSet khuvuc = obj.getkhuvuc();
   	ResultSet npp = obj.getnpp();
   	ResultSet dvkd = obj.getdvkd();
   	ResultSet nhanhang = obj.getnhanhang();
   	ResultSet chungloai = obj.getchungloai();
   	ResultSet dvdl = obj.getdvdl();
   	ResultSet sanpham = obj.getsanpham();
  	ResultSet gsbh = obj.getgsbh();
  	
	int[] quyen = new  int[6];
	quyen = util.Getquyen("BCThuongDauThungSvl","", userId);
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("BCThuongDauThungSvl","",nnId);	
 %>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});		
	});	
</script>


<SCRIPT language="javascript" type="text/javascript">

function submitform()
{
	document.forms['rpForm'].action.value="taomoi";
	document.forms['rpForm'].dataerror.value="";
	document.forms['rpForm'].submit();
}
function seach(){
	document.forms['rpForm'].action.value="search";
	document.forms['rpForm'].submit();
}
</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>	



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

<form name="rpForm" method="post" action="../../BCThuongDauThungSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="action" value='1'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&#160; <%=" "+url %> </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%= userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>	
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle">Thưởng Đầu Thùng</LEGEND>
							<TABLE  width="100%" cellpadding="6px" cellspacing="0px">
								<%-- <TR>
								  	<TD class="plainlabel" width="100px">Từ ngày</TD>
									<TD class="plainlabel" width="230px">
										<input type="text" name="Sdays"	class="days" value='<%=obj.gettungay()%>' />
									</TD>
									<TD class="plainlabel" width="130px">Đến ngày</TD>
									<td class="plainlabel">
										<input type="text" name="Edays" class="days" value='<%=obj.getdenngay()%>' />
									</td>
								</TR> --%>
								
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Chương trình thưởng",nnId) %></TD>
									
									<TD class="plainlabel" colspan="5">
										<select name="thuongId" id="thuongId" onchange="seach();">
												<option value="" selected>All</option>
												<% if(thuong != null)
												   while(thuong.next()){
												   if(thuong.getString("pk_seq").equals(obj.getthuongId()))
												   { %>
												   <option value="<%= thuong.getString("pk_seq") %>" selected><%=thuong.getString("diengiai") %></option>
												   <%}else { %>
												   <option value="<%= thuong.getString("pk_seq") %>"><%=thuong.getString("diengiai") %></option>
												<%} }%>
										</select>
									
									
								</TR> 
								<TR>
								<TD class="plainlabel"><%=ChuyenNgu.get("Vùng/Miền",nnId) %></TD>
									<TD class="plainlabel" colspan="5">
										<select name="vungId" id="vungId" onchange="seach();">
											<option value="" selected>All</option>
											<% if(vung != null)
											   while(vung.next()){
											   if(vung.getString("pk_seq").equals(obj.getvungId()))
											   { %>
											   <option value="<%= vung.getString("pk_seq") %>" selected><%=vung.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= vung.getString("pk_seq") %>"><%=vung.getString("ten") %></option>
											<%} }%>
										</select>
										</TD>
										<%-- <TD class="plainlabel">Nhãn hàng</TD>
									<TD class="plainlabel">
										<select name="nhanhangId" id="nhanhangId" onchange="seach();">
												<option value="" selected>All</option>
													<% if(nhanhang != null)
												   while(nhanhang.next()){
												   if(nhanhang.getString("pk_seq").equals(obj.getnhanhangId()))
												   { %>
												   <option value="<%= nhanhang.getString("pk_seq") %>" selected><%=nhanhang.getString("ten") %></option>
												   <%}else { %>
												   <option value="<%= nhanhang.getString("pk_seq") %>"><%=nhanhang.getString("ten") %></option>
												<%} }%>
										</select>
									</TD> --%>
									</TR>
								<TR>									
									<TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %></TD>
									<TD class="plainlabel" colspan="5">
									<select name="khuvucId" id="khuvucId" onchange="seach();">
											<option value="" selected>All</option>
											<% if(khuvuc != null)
											   while(khuvuc.next()){
											   if(khuvuc.getString("pk_seq").equals(obj.getkhuvucId()))
											   { %>
											   <option value="<%= khuvuc.getString("pk_seq") %>" selected><%=khuvuc.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= khuvuc.getString("pk_seq") %>"><%=khuvuc.getString("ten") %></option>
											<%} }%>
									</select>
									</TD>
									<%-- <TD class="plainlabel">Chủng Loại</TD>
									<TD class="plainlabel">
									<select name="chungloaiId" id="chungloaiId" onchange="seach();">
											<option value="" selected>All</option>
											<% if(chungloai != null)
											   while(chungloai.next()){
											   if(chungloai.getString("pk_seq").equals(obj.getchungloaiId()))
											   { %>
											   <option value="<%= chungloai.getString("pk_seq") %>" selected><%=chungloai.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= chungloai.getString("pk_seq") %>"><%=chungloai.getString("ten") %></option>
											<%} }%>
									</select>
									</TD> --%>
									</TR>
								<TR>									
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
									<TD class="plainlabel" colspan="5"><select name="nppId" id="nppId" >
											<option value="" selected >All</option>
											<% if(npp != null)
											   while(npp.next()){
											   if(npp.getString("pk_seq").equals(obj.getnppId()))
											   { %>
											   <option value="<%= npp.getString("pk_seq") %>" selected><%=npp.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= npp.getString("pk_seq") %>"><%=npp.getString("ten") %></option>
											<%} }%>
									</select></TD>	
								</TR>

							    <TR>
									<TD colspan="4" class="plainlabel">
									<a class="button2" href="javascript:submitform()" >
										<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Tạo báo cáo",nnId) %> </a>
											&nbsp;&nbsp;&nbsp;&nbsp;
									</TD>
								</TR>
							</TABLE>
							</FIELDSET>	</TD>
						</TR>
					</TABLE>					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
</body>  
</HTML>
<% 
	if(chungloai != null){ chungloai.close(); chungloai= null;}
	if (dvkd != null){ dvkd.close(); dvkd= null;}
	if (nhanhang != null){ nhanhang.close(); nhanhang= null;}
	if (dvdl != null){ dvdl.close();  dvdl= null;}
	if(gsbh != null){ gsbh.close(); gsbh= null;}
	
	if(khuvuc != null){ khuvuc.close(); khuvuc= null;}
	if(npp != null){ npp.close(); npp= null;}
	if(vung != null){ vung.close(); vung= null;}
	if(sanpham != null){ sanpham.close(); sanpham= null;}
	
	if(obj != null){
		obj.DBclose();
		obj = null;
	}
	
	session.setAttribute("obj", null);
}
%>