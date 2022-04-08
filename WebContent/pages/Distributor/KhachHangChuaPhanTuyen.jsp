
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.khachhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IKhachhangList obj = (IKhachhangList)session.getAttribute("obj"); %>
<%-- <% List<IKhachhang> khlist = (List<IKhachhang>)obj.getKhList(); %> --%>
<% ResultSet khlist = (ResultSet)obj.getKhList(); %>
<% ResultSet hch = (ResultSet)obj.getHangcuahang(); %>
<% ResultSet kbh = (ResultSet)obj.getKenhbanhang();  %>
<% ResultSet vtch = (ResultSet)obj.getVitricuahang();  %>
<% ResultSet lch = (ResultSet)obj.getLoaicuahang(); 
	
	int[] quyen = new  int[6];
	quyen = util.Getquyen("KhachhangChuaPhanTuyenSvl","", userId);
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 
 } 
String url = util.getChuyenNguUrl("KhachhangChuaPhanTuyenSvl","",nnId); 
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>

<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
	document.khForm.khTen.value = "";
	document.khForm.hchTen.selectedIndex = 0;
	document.khForm.kbhTen.selectedIndex = 0;
	document.khForm.vtchTen.selectedIndex = 0;
	document.khForm.lchTen.selectedIndex = 0;
	submitform();
}

function xuatExcel()
{
	document.forms['khForm'].action.value= 'excel';
	document.forms['khForm'].submit();
	
}

function submitform()
{
	document.forms['khForm'].action.value= 'search';
	document.forms['khForm'].submit();
}

function newform()
{
	document.forms['khForm'].action.value= 'new';
	document.forms['khForm'].submit();
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

<form name="khForm" method="post" action="../../KhachhangChuaPhanTuyenSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="action" value="1" >


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" 	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  	<tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %></TD>
							   <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId)%> <%= obj.getNppTen() %> &nbsp;</TD>
							</tr>
						</table>
					</TD>
		  		</TR>	
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId)%>&nbsp;</LEGEND>
							<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
									<TD width="17%" class="plainlabel"><%=ChuyenNgu.get("Tên khách hàng",nnId)%></TD>
								    <TD width="29%" class="plainlabel">
											<INPUT name="khTen" type="text" value="<%= obj.getTen() %>" size="40" onChange = "submitform();">
								  </TD>
									<TD width="16%" class="plainlabel"><%=ChuyenNgu.get("Hạng khách hàng",nnId)%></TD>
 
									<TD width="38%" class="plainlabel">
										<SELECT name="hchTen" onChange = "submitform();">
										  <option value=""> </option>
										  <% try{ while(hch.next()){ 
								    			if(hch.getString("hchId").equals(obj.getHchId())){ %>
								      				<option value='<%=hch.getString("hchId")%>' selected><%=hch.getString("hchTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=hch.getString("hchId")%>'><%=hch.getString("hchTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>	 
                                        </SELECT>
								  </TD>
								</TR>
								<TR>
								  	<TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId)%></TD>
								  	<TD  class="plainlabel"><SELECT name="kbhTen" onChange = "submitform();">
								    	<option value=""> </option>
									    <% try{ while(kbh.next()){ 
								    			if(kbh.getString("kbhId").equals(obj.getKbhId())){ %>
								      				<option value='<%=kbh.getString("kbhId")%>' selected><%=kbh.getString("kbhTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=kbh.getString("kbhId")%>'><%=kbh.getString("kbhTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
									  	</SELECT>
									</TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Vị trí",nnId)%></TD>
	                                <TD  class="plainlabel"><SELECT name="vtchTen" onChange = "submitform();">
    		                            <option value=""> </option>
            		                    <% try{ while(vtch.next()){ 
								    			if(vtch.getString("vtchId").equals(obj.getVtchId())){ %>
								      				<option value='<%=vtch.getString("vtchId")%>' selected><%=vtch.getString("vtchTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=vtch.getString("vtchId")%>'><%=vtch.getString("vtchTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
                                        </SELECT></TD>
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Loại khách hàng",nnId)%></TD>
								  	<TD  class="plainlabel" colspan=3><SELECT name="lchTen"  onChange = "submitform();">
								    	<option value=""> </option>
								    	<% try{ while(lch.next()){ 
								    			if(lch.getString("lchId").equals(obj.getLchId())){ %>
								      				<option value='<%=lch.getString("lchId")%>' selected><%=lch.getString("lchTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=lch.getString("lchId")%>'><%=lch.getString("lchTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
										</SELECT>
									</TD>
										
								</TR>
								<TR>
								    <TD class="plainlabel" colspan="4">
								    	<a class="button2" href="javascript:clearform()">
											<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId)%>
										</a>&nbsp;&nbsp;&nbsp;&nbsp;
										
										<a class="button2" href="javascript:xuatExcel()"> 
										<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất Excel",nnId)%>
										</a> &nbsp;&nbsp;&nbsp;&nbsp;
											    
								    <!--  
                                      <INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();"> 
                                     --> </TD>
								</TR>
							</TABLE>
					  </FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellspacing="0" cellpadding="2">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Khách hàng chưa phân tuyến",nnId)%> &nbsp;&nbsp;&nbsp;
					                          
					
					<!-- <INPUT name="new" type="button" value="Tao moi" onClick="newform();"> -->
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="13%"><%=ChuyenNgu.get("Mã khách hàng",nnId)%></TH>
									<TH width="18%"><%=ChuyenNgu.get("Tên khách hàng",nnId)%></TH>
									<TH width="10%"><%=ChuyenNgu.get("Trạng thái",nnId)%></TH>
									<TH width="9%"><%=ChuyenNgu.get("Ngày tạo",nnId)%></TH>
									<TH width="15%"><%=ChuyenNgu.get("Người tạo",nnId)%> </TH>
									<TH width="9%"><%=ChuyenNgu.get("Ngày sửa",nnId)%></TH>
									<TH width="15%"><%=ChuyenNgu.get("Người sửa",nnId)%></TH> 
									
								</TR>
								
						<%  int m = 0;
	                        String lightrow = "tblightrow";
	                        String darkrow = "tbdarkrow";
							if(khlist!=null)
							{%>				
								<% try{								
	                                while (khlist.next()){
	                                    	
	                                   	if (m % 2 != 0) {%>                     
	                                    	<TR class= <%=lightrow%> >
	                                    <%} else {%>
	                                       	<TR class= <%= darkrow%> >
	                                    	<%}%>
											<TD align="left"><div align="center"><%=khlist.getString("khId") %></div></TD>                                   
											<TD><div align="left"><%= khlist.getString("khTen")%></div></TD>
											<%
											/* if(khlist.getTrangthai() ==null ){
												khlist.setTrangthai("0");
								      		} */
											if (khlist.getString("trangthai").equals("1")){ %>
												<TD align="center"><%=ChuyenNgu.get("Hoạt động",nnId)%></TD>
											<%}else{%>
												<TD align="center"><%=ChuyenNgu.get("Ngưng hoạt động",nnId)%></TD>
											<%}%>
											
											<TD align="center"><%=khlist.getString("ngaytao")%></TD>
											<TD align="center"><%=khlist.getString("nguoitao")%></TD>
											<TD align="center"><%=khlist.getString("ngaysua")%></TD>
											<TD align="center"><%=khlist.getString("nguoisua")%></TD>
											
										</TR>
								<%m++; }}catch(java.sql.SQLException e){}								
								}%>
								

										<tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 <% obj.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
												<input type="hidden" name="items" value="30">
												<input type="hidden" name="splittings" value="20">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr>  							</TABLE>
							</TD>
						</TR>
					</TABLE>
					</FIELDSET>
					</TD>
				</TR>
			</TBODY>
		</TABLE>

	</TR>
</TABLE>
</form>
</BODY>
</HTML>

<% 	

	try{
		
		if(khlist != null){ khlist.close(); khlist= null; }
		if(hch != null){ hch.close(); hch= null; }
		if(kbh != null){ kbh.close(); kbh= null; }
		if(lch != null){ lch.close(); lch= null; }
		if(vtch!=null){ vtch.close(); vtch= null; }
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}	
		
		session.setAttribute("obj",null);
	
	}
	catch (SQLException e) {}
	

%>
<%}%>