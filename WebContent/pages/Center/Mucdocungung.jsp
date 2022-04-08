<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ page  import = "geso.dms.center.beans.baocao.IMucdocungunghang" %>
<%@ page  import = "geso.dms.center.beans.baocao.imp.Mucdocungunghang" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IMucdocungunghang obj = (IMucdocungunghang)session.getAttribute("obj"); %>
<% ResultSet mdcu = (ResultSet)obj.getMucdocungung(); %>
<% ResultSet khoIds = (ResultSet)obj.getKhoIds(); %>
<% ResultSet vungIds = (ResultSet)obj.getVungIds(); %>
<% ResultSet kvIds = (ResultSet)obj.getKvIds(); %>
<% ResultSet nppIds = (ResultSet)obj.getNppIds(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>BEST</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">

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
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">

function clearform()
{
	document.mdcuForm.masp.value = "";
    document.mdcuForm.tensp.value = "";
    document.mdcuForm.tungay.value = "";
    document.mdcuForm.denngay.value = "";
    document.mdcuForm.khoId.selectedIndex = 0;
    document.mdcuForm.vungId.selectedIndex = 0;
    document.mdcuForm.kvId.selectedIndex = 0;
    document.mdcuForm.nppId.selectedIndex = 0;
    submitform();
}

function submitform()
{
	document.forms['mdcuForm'].action.value= 'search';
	document.forms['mdcuForm'].submit();
}

function toExcel()
{
	document.forms['mdcuForm'].action.value= 'excel';
	document.forms['mdcuForm'].submit();
}

function toPDF()
{
	document.forms['mdcuForm'].action.value= 'pdf';
	document.forms['mdcuForm'].submit();
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

<form name="mdcuForm" method="post" action="../../BaocaocungungSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
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
							   		&nbsp;Quản lý bán hàng &gt; Mức độ cung ứng
							   </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td> 
						    </tr>
						</table>
					</TD>
				</TR>
				<TR>
					<TD>
						<TABLE border="0" width="100%" >
							<TR>							
							<TD colspan=4>
							<A href="javascript:toExcel();"  ><img src="../images/excel.gif" alt="Excel"  title="Excel" border="1" longdesc="Excel" style="border-style:outset" width='30' length='30'></A>
							
							<A href="javascript:toPDF();"  ><img src="../images/pdf.jpg" alt="PDF"  title="PDF" border="1" longdesc="PDF" style="border-style:outset" width='30' length='30'></A>
							</TD>
							</TR>
							<TR>
								<TD width="100%" align="left"><FIELDSET>
			
								
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm </LEGEND>

									<TABLE class="tblight"  width="100%" cellpadding="6" cellspacing = "0">
										<TR>
											<TD width="15%" class="plainlabel" >Mã sản phẩm</TD>
										  <TD  class="plainlabel" ><INPUT name="masp" type="text" size="40" value="<%= obj.getMasp() %>" onChange="submitform();" ></TD>

											<TD width="15%" class="plainlabel" >Tên sản phẩm</TD>
										  <TD class="plainlabel" ><INPUT name="tensp" type="text" size="40" value="<%= obj.getTensp() %>" onChange="submitform();" ></TD>
										</TR>
										
										<TR>							
											<TD class="plainlabel" >Từ ngày </TD>
											<TD class="plainlabel" >
												<TABLE cellpadding="0" cellspacing="0">
													<TR><TD>
										<input  class="days" type="text" name="tungay" value='<%=obj.getTuNgay() %>'  size="20" onchange=submitform(); >
												</TD>
												
		   										</TR>
												</TABLE>
																						
		  									</TD>
                                          <TD class="plainlabel" >Đến ngày </TD>
										  <TD class="plainlabel" >
										  		<TABLE cellpadding="0" cellspacing="0"><TR><TD>
										 <input  class="days" type="text" name="denngay" value='<%=obj.getDenNgay() %>' size="20" onchange=submitform(); >
										  		</TD>
										

											  </TR>
											 </TABLE>
									  	</TR>
										<TR>
											<TD class="plainlabel">Vùng</TD>
											
											<TD class="plainlabel"><select name="vungId" onChange="submitform();">
											<option value="0">&nbsp;</option>
										<%	try{
												while(vungIds.next()){
											 		if (obj.getVungId().equals(vungIds.getString("vungId"))){%>											  
											 		 	<option value="<%= vungIds.getString("vungId") %>" selected ><%= vungIds.getString("vungTen") %></option>											  
													<%}else{%>
														<option value="<%= vungIds.getString("vungId") %>" ><%= vungIds.getString("vungTen") %></option>
													<%}
												}
											}catch(java.sql.SQLException e){System.out.print("vungId");}%>
										    </select></TD>

											<TD class="plainlabel">Khu vực</TD>
											
											<TD class="plainlabel"><select name="kvId" onChange="submitform();">
											<option value="0">&nbsp;</option>
										<%	try{
												while(kvIds.next()){
											 		if (obj.getKvId().equals(kvIds.getString("kvId"))){%>											  
											 		 	<option value="<%= kvIds.getString("kvId") %>" selected ><%= kvIds.getString("kvTen") %></option>											  
													<%}else{%>
														<option value="<%= kvIds.getString("kvId") %>" ><%= kvIds.getString("kvTen") %></option>
													<%}
												}
											}catch(java.sql.SQLException e){System.out.print("kvId");}%>
										    </select></TD>
										</TR>										
										<TR>
											<TD class="plainlabel">Kho</TD>
											
											<TD class="plainlabel"><select name="khoId" onChange="submitform();">
											<option value="">&nbsp;</option>
										<%	try{
												while(khoIds.next()){
											 		if (obj.getKhoId().equals(khoIds.getString("khoId"))){%>											  
											 		 	<option value="<%= khoIds.getString("khoId") %>" selected ><%= khoIds.getString("khoId") %></option>											  
													<%}else{%>
														<option value="<%= khoIds.getString("khoId") %>" ><%= khoIds.getString("khoId") %></option>
													<%}
												}
											}catch(java.sql.SQLException e){System.out.print("khoId");}%>
										    </select></TD>

											<TD class="plainlabel">Nhà phân phối</TD>
											
											<TD class="plainlabel"><select name="nppId" onChange="submitform();">
											<option value="0">&nbsp;</option>
										<%	try{
												while(nppIds.next()){
											 		if (obj.getNppId().equals(nppIds.getString("nppId"))){%>											  
											 		 	<option value="<%= nppIds.getString("nppId") %>" selected ><%= nppIds.getString("nppTen") %></option>											  
													<%}else{%>
														<option value="<%= nppIds.getString("nppId") %>" ><%= nppIds.getString("nppTen") %></option>
													<%}
												}
											}catch(java.sql.SQLException e){System.out.print("NppId");}%>
										    </select></TD>
										    
										    
										</TR>										

										<TR>
										    <TD class="plainlabel" colspan=4>
										    	<!-- 
                                             	 <INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();"></TD>
                                              	-- -->
                                               <a class="button2" href="javascript:clearform()">
												<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
											</TD>
										</TR>
									</TABLE>
									</FIELDSET>
								</TD>	
							</TR>
						</TABLE>
					</TD>
				</TR>	
				
			    <TR>
					<TD align="left" >
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Mức độ cung ứng
							
						</LEGEND>
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="10%">Mã sản phẩm</TH>
												<TH width="20%">Tên sản phẩm</TH>
												<TH width="5%">Ngày</TH>
												<TH width="8%">Đặt (Thùng)</TH>
												<TH width="8%">Giao (Thùng)</TH>
												<TH width="8%">Chênh lệch (Thùng)</TH>
												<TH width="8%">Kho</TH>
												<TH width="8%">Vùng</TH>
												<TH width="8%">Khu vực</TH>
												<TH width="12%">Nhà phân phối</TH>
												<TH width="12%">Loại</TH>
											</TR>
								<% 
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									
									if( mdcu!= null){
										while (mdcu.next()){
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
											<%} else {%>
												<TR class= <%= darkrow%> >
											<%}%>
													<TD align="left"><div align="left"><%=mdcu.getString("masp")%></div></TD>                                   
													<TD><div align="left"><%=mdcu.getString("tensp")%></div></TD>
													<TD align="center"><%=mdcu.getString("ngaydat")%></TD>
													<TD align="center"><%=mdcu.getString("dadat")%></TD>
													<TD align="center"><%=Long.valueOf(mdcu.getString("dagiao")).longValue() %></TD>
													<TD align="center"><%=Long.valueOf(mdcu.getString("dadat")).longValue() - Long.valueOf(mdcu.getString("dagiao")).longValue() %></TD>
													<TD align="center"><%=mdcu.getString("kho")%></TD>
													<TD align="center"><%=mdcu.getString("vung")%></TD>
													<TD align="center"><%=mdcu.getString("khuvuc")%></TD>
													<TD align="center"><%=mdcu.getString("nppTen")%></TD>
													
													<% if (mdcu.getString("loai").equals("1")){ %>
													
													<TD align="center">OS</TD>
													
													<%}else{ %>
													
													<TD align="center">ERR</TD>
													
													<%} %>
													
												</TR>
											<%m++; 
											}
										}%>
										<tr class="tbfooter"><td colspan="11" >&nbsp; </td></tr>
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

<%
try{
if(mdcu!=null){
	mdcu.close();
}
if(khoIds!=null){
	khoIds.close();
}
if(vungIds!=null){
	vungIds.close();
}
if(kvIds!=null){
	kvIds.close();
}
if(nppIds!=null){
	nppIds.close();
}
obj.DBClose();
}catch(Exception er){
	
}
} %>
