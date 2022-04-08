<%@page import="java.sql.SQLException"%>
<%@page import="geso.dms.center.beans.nhaphanphoi.imp.Nhaphanphoi"%>
<%@page import="geso.dms.distributor.beans.donhangnhapp.IDonhangnpp"%>
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
<%
IDonhangnpp obj = (IDonhangnpp)session.getAttribute("obj"); 
ResultSet listnpp=obj.GetRs_NhappBan();

String idnpp = (String) session.getAttribute("idnpp"); 
String tungay=obj.GetTungay();
	
	
String denngay=obj.GetDenNgay();

%>

<%


 ResultSet dhlist= obj.GetDonHangNPP();

 %>

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
<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout()
 {
    if(confirm("Bạn có muốn đăng xuất?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function submitform()
 {   
    document.forms["dhForm"].submit();
 }
 function newform()
 {
 	document.forms['dhForm'].action1.value= 'new';
 	document.forms['dhForm'].submit();
 }
 function clearform()
 {
	 document.forms['dhForm'].nhappmuatk.value="";
	 document.forms['dhForm'].trangthai.value="";
	 document.forms['dhForm'].tungay.value="";
	 document.forms['dhForm'].denngay.value="";
	 document.forms['dhForm'].action1.value="clear";
     document.forms["dhForm"].submit();
 }
 function timkiem()
 {
	 document.forms['dhForm'].action1.value="timkiem";
	    document.forms["dhForm"].submit();
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

<form name="dhForm" method="post" action="../../DonhangnppSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=idnpp%>" >
<input type="hidden" name="action1" value="1">


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD align="left" class="tbnavigation">
					   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation"> &nbsp;Quản lý bán hàng > Bán cho nhà NPP
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= obj.getTenNPPBan() %>&nbsp;</tr>
						</TABLE>
					</TD>
		  </TR>
			<TR>
				<TD >
				<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm</LEGEND>
							<TABLE width="100%" cellpadding="6" cellspacing="0">
									<TR>
									<TD class="plainlabel"> Nhà phân phối mua hàng</TD>
									<TD class="plainlabel">
										<SELECT name="nhappmuatk">
												<!-- Cach tao mot combobox -->
										 			 <option value=""> </option>
													  <% int i=0; 
													  if(listnpp!= null){
														  try{ while(listnpp.next()){ 
															
															  if(listnpp.getString("pk_Seq").trim().equals(idnpp))
															  {
																%>  <option  selected="selected" value='<%=listnpp.getString("pk_Seq").trim()%>'  ><%=listnpp.getString("ten").trim()%></option><%
															  }
															  else
															  {
											    			%>
											     				<option value='<%=listnpp.getString("pk_Seq").trim()%>'><%=listnpp.getString("ten").trim() %></option>
											     			<% 
															  }i++; } }catch(Exception e){
											     				System.out.println("Error : 194 DonHangNhaPPNew ; "+ e.toString());
											     			}} %>	 
                                    			</SELECT> 
									</TD>
									
								</TR>
								<TR>
									<TD class="plainlabel">Trạng thái </TD>
									<TD class="plainlabel">
										<select name="trangthai" >
											<% if (obj.getTrangthai().equals("1")){%>
											  	<option value="1" selected>Đã chốt</option>
											  	<option value="0">Chưa chốt</option>
											  	<option value="2">Đã hủy</option>
											  	<option value="3">Đã xuất kho</option>
											  	<option value=""></option>
											  
											<%}else if(obj.getTrangthai().equals("0")) {%>
											 	<option value="0" selected>Chưa chốt</option>
											  	<option value="1" >Đã chốt</option>
											 	<option value="2" >Đã hủy</option>
											 	<option value="3" >Đã xuất kho</option>
											  	<option value="" ></option>
											  	
											<%}else if(obj.getTrangthai().equals("2")){%>											
											 	<option value="2" selected>Đã hủy</option>										  	
											  	<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đã chốt</option>
											  	<option value="3" >Da Xuat Kho</option>
											  	<option value="" ></option>
											  	
											<%}else if(obj.getTrangthai().equals("3")){%>											
											 	<option value="3" selected>Đã xuất kho</option>										  	
											  	<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đã chốt</option>
											  	<option value="2" >Đã hủy</option>
											  	<option value="" ></option>
											  	  	
											<%}else{%>
												<option value="" selected> </option>
												<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đã chốt</option>											
											  	<option value="2" >Đã hủy</option>
											  	<option value="3" >Đã xuất kho</option>
											<%} %>
									          </select>
									</TD>
							    </TR>
								
								<TR>
									<TD class="plainlabel" >Từ ngày</TD>
									<TD class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
											    <input class="days" type="text" name="tungay" size="11" value="<%=tungay %>">
											</TD></TR>
										</TABLE>	
								</TR>
								<TR>
								  <TD class="plainlabel" >Đến ngày</TD>
							      <TD width="79%" class="plainlabel">
								  		<TABLE cellpadding="0" cellspacing="0">
										  	<TR><TD>
											  <input class="days" type="text" name="denngay" size="11" value="<%= denngay%>">
										  	</TD></TR>
										</TABLE>
							  	</TR>
								<TR>
									<TD class="plainlabel" colspan="3">
                                   		<a name="action" class="button2" href="javascript:timkiem()">
    	                          		 <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm  </a> &nbsp;&nbsp;&nbsp;                         
										<a class="button2" href="javascript:clearform()">
	                               			<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
									
									  <!--  <INPUT name="action" type="submit" value="Tim kiem">&nbsp;
                                       <INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();">
                                       -->
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
					<a class="button3"  onclick="newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
					
				<!--<INPUT name="action" type="submit" value="Tao moi"> -->	
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1px" cellpadding="4px">
								<TR class="tbheader">
									<TH align="center">Mã DH</TH>
									<TH align ="center"> Ngày nhập</TH>
									<TH align="center">Nhà phân phối mua</TH>
									<TH align="left"> Trạng thái</TH>
									<TH align="center">Ngày tạo</TH>
									<TH align="left">Người tạo</TH>
									<TH align="center">Ngày sửa</TH>
									<TH align="left">Người sửa</TH>
									<TH align="center" width="10%">Tác vụ</TH>
								</TR>
								
								<% 
									IDonhangnpp donhang = null;
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									
										while (dhlist.next()){
										
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><%=dhlist.getString("pk_seq") %></TD>       
												<TD align="center"><%=dhlist.getString("NGAYNHAP") %></TD>                            
												<TD align="left"><%=dhlist.getString("TEN") %></TD>
												<TD align="left">
												<% if (dhlist.getString("trangthai").equals("0")){ %>
													<span> Chưa Chốt</span>
												<%}else{ if(dhlist.getString("trangthai").equals("1")){ %>
													<span><b> Đã Chốt</b></span>
												<%}else{ if(dhlist.getString("trangthai").equals("2")){ %>
													<span><u> Đã Hủy</u></span>
												<%} else{ %>
													<span><i style="color:red"> Đã Xuất Kho</i></span>
												<% }}} %>
												</TD>
												<TD align="center"><%=dhlist.getString("ngaytao") %></TD>
												<TD align="left"><%=dhlist.getString("nguoitao")%></TD>
												<TD align="center"><%=dhlist.getString("ngaysua")%></TD>
												<TD align="left"><%=dhlist.getString("nguoisua")%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<%if(dhlist.getString("trangthai").equals("0") || dhlist.getString("trangthai").equals("3")){ %>
													<TR>
													<%if(dhlist.getInt("nppDaChot")==0 ) { %>
													<TD>
														<A href = "../../DonhangnppSvl?userId=<%=userId%>&update=<%=dhlist.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													</TD>
													
														 <TD> 
			                                            	<A href = "../../DonhangnppSvl?userId=<%=userId%>&chot=<%= dhlist.getString("pk_seq") %>"><img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn chốt đơn bán hàng này?')) return false;"></A>
			                                            </TD>
		                                            <%} %>
		                                            
		                                            <TD>
														<A href = "../../DonhangnppSvl?userId=<%=userId%>&display=<%=dhlist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
													</TD>
													
													<TD>
														<A href = "../../DonhangnppSvl?userId=<%=userId%>&delete=<%=dhlist.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa đơn hàng này?')) return false;"></A></TD>
													</TR>
													
													
													<%}else{ %>
													<TR>
													
													<TD>
														<A href = "../../DonhangnppSvl?userId=<%=userId%>&display=<%=dhlist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
													</TD>
													
													</TR>
													<%} %>
												</TABLE>
												</TD>
											</TR>
								<%m++; }%>								






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
try{
	if(obj != null){
		obj.DBclose();
		obj = null;
	}
	if(listnpp!=null){
		listnpp.close();
	}
	if(dhlist!=null){
		dhlist.close();
	}

	session.setAttribute("obj",null);
	}catch(Exception er){
		
	}
%>
<%}%>