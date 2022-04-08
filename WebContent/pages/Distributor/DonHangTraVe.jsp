<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.donhangtrave.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<% IDonhangtraveList obj = (IDonhangtraveList)session.getAttribute("obj"); %>
<% ResultSet dhtvlist = (ResultSet)obj.getDhtvList(); %>
<% ResultSet ddkd = (ResultSet)obj.getDaidienkd(); 
	ResultSet npp = (ResultSet)obj.getNpp();
  int[] quyen = new  int[6];
  quyen = util.Getquyen("DonhangtraveSvl","", userId);
  System.out.println("msg-----------"+obj.getMsg());
%>



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
    if(confirm("Ban co muon dang xuat?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }

  function clearform()
 {
	 
	 //document.forms["dhForm"].action1.value="guilai";
	 document.forms["dhForm"].reset();
	 document.forms["dhForm"].submit();
	 //document.forms["dhForm"].ddkdTen.selected.value = "";
	 //document.forms["dhForm"].trangthai[4].selected = true;
	 document.getElementById("empDDKD").selected = true;
	 document.getElementById("emp").selected = true;
	
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
 
 function thongbao()
 {   
	 var bgstId = document.getElementById("msg").value;
	 if(bgstId!='')
		 alert(bgstId);
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

<form name="dhForm" method="post" action="../../DonhangtraveSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="action1" value="1" >
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" id="msg" value="<%= obj.getMsg() %>" >

<%-- <input type="hidden" name="nppId" value="<%= obj.getNppId() %>" > --%>
<script language="javascript" type="text/javascript">
    thongbao();
</script>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD align="left" class="tbnavigation">
					   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation"> &nbsp;Quản lý bán hàng > Bán hàng cho khách hàng > Trả hàng lẻ
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= obj.getNppTen() %>&nbsp;</tr>
						</TABLE>
					</TD>
		  </TR>
			<TR>
				<TD >
				<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm</LEGEND>
							<TABLE width="100%" cellpadding="6" cellspacing="0">
								
								 <TR >
												<TD width="22%" class="plainlabel">Nhà phân phối  </TD>
												<TD colspan="3" class="plainlabel"> 
												<SELECT name="nppId" id="nppId" onChange = "submitform();">
										 			 <option value=""></option>
													  <% if(npp != null){
														  try{ while(npp.next()){ 		
															  System.out.println("343423 npp" + npp.getString("nppId"));
															  System.out.println("343423 get id" + obj.getNppId());
											    			if(npp.getString("nppId").equals(obj.getNppId())){ %>
											      				<option value='<%=npp.getString("nppId")%>' selected><%= npp.getString("nppTen") %></option>
											      			<%}else{ %>
											     				<option value='<%=npp.getString("nppId")%>'><%= npp.getString("nppTen") %></option>
											     			<%}} npp.close(); }catch(java.sql.SQLException e){}} %>	 
                                    			</SELECT></TD>
								</TR>	
								
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
											    <% if (obj.getTrangthai().equals("1")){%>
											  	<option id="emp" value=""></option>
											  	<option value="0">Chưa chốt</option>
											  	<option value="1" selected>Đang chờ duyệt</option>
											  	
											  	<option value="2">Đã hủy</option>
											  	<option value="3">Đã duyệt</option>
											  	
											  
											<%}else if(obj.getTrangthai().equals("0")) {%>
											 	<option id="emp" value=""></option>
											 	 <option value="0" selected>Chưa chốt</option>
											  	<option value="1" >Đang chờ duyệt</option>
											 	 <option value="2" >Đã hủy</option>
											 	 <option value="3">Đã duyệt</option>
											  	
											  	
											<%}else if(obj.getTrangthai().equals("2")){%>	
											<option id="emp" value=""></option>										
											 	<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đang chờ duyệt</option>
											 	 <option value="2" selected>Đã hủy</option>										  	
											  	
											  	<option value="3">Đã duyệt</option>
											  	
											  	
											<%}else if(obj.getTrangthai().equals("3")) { %>
												<option id="emp" value=""></option>
												<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đang chờ duyệt</option>											
											  	<option value="2" >Đã hủy</option>
											  	<option value="3" selected>Đã duyệt</option>
											  	
											  	
											<%} else{ %>
												<option id="emp" value="" selected></option>
												<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đang chờ duyệt</option>											
											  	<option value="2" >Đã hủy</option>
											  	<option value="3">Đã duyệt</option>
											<% } %>
									          </select>
									</TD>
							    </TR>
								
								<TR>
									<TD class="plainlabel" >Từ ngày</TD>
									<TD class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
											    <input class="days" type="text" name="tungay" size="11">
											</TD></TR>
										</TABLE>	
								</TR>
								<TR>
								  <TD class="plainlabel" >Đến ngày</TD>
							      <TD width="100%" class="plainlabel">
								  		<TABLE cellpadding="0" cellspacing="0">
										  	<TR><TD>
											  <input class="days" type="text" name="denngay" size="11">
										  	</TD></TR>
										</TABLE>
							  	</TR>
								<TR>
									<TD class="plainlabel" colspan="3">
									<a class="button2" href="javascript:submitform()"> 
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
					<LEGEND class="legendtitle">&nbsp;Đơn hàng trả về &nbsp;&nbsp;&nbsp;
					<%if(quyen[0]!= 0){ %>
					<a class="button3" onclick="newform()">
    				<img style="top: -4px;" src="../images/New.png" >Tạo mới </a>  
    				<%} %>                          
					<!-- <INPUT name="action" type="submit" value="Tao moi"> -->
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="3">
								<TR class="tbheader">
									<TH width="10%" align="center">Mã đơn hàng </TH>
									<TH width="10%" align="center">Mã khách hàng </TH>
									<TH width="10%" align="center">Tên khách hàng </TH>
									<TH width="10%" align="center">Ghi chú </TH>
									<TH width="10%" align="center">Ngày nhập </TH>
									<TH width="10%" align="center">Trạng thái </TH>
									<TH width="10%" align="center">Ngày tạo </TH>
									<TH width="10%" align="center">Người tạo </TH>
									<TH width="10%" align="center">Ngày sửa </TH>
									<TH width="10%" align="center">Người sửa </TH>
									<TH width="10%" align="center">Tác vụ</TH>
								</TR>
								
								<% 
									IDonhangtrave donhangtrave = null;
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(dhtvlist != null){
									try{
									while (dhtvlist.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												
										<TD align="center"><%= dhtvlist.getString("dhtvId") %></TD>                                   
										<TD align="center"><%= dhtvlist.getString("smartid") %></TD>
										<TD align="left"><%= dhtvlist.getString("khTen") %></TD>
										<TD align="left"><%= dhtvlist.getString("lydo") %></TD>
										<TD align="center"><%= dhtvlist.getString("ngaynhap") %></TD>
	
										<%
										String trangthai = dhtvlist.getString("trangthai");
										if (trangthai.equals("0")){ %>
											<TD align="left">Chưa chốt</TD>
										<%}else{ if(trangthai.equals("1")){ %>
											<TD align="left"><i>Đang chờ duyệt</i></TD>
										<%}else{ if(trangthai.equals("2")){ %>
											<TD align="left"><b>Đã hủy</b></TD>
										<%}else{ %>
											<TD align="left"><span style="color: red;">Đã duyệt</span></TD>
										<% }}} %>
										
										<TD align="center"><%= dhtvlist.getString("ngaytao") %></TD>
										<TD align="left"><%= dhtvlist.getString("nguoitao")%></TD>
										<TD align="center"><%= dhtvlist.getString("ngaysua") %></TD>
										<TD align="left"><%= dhtvlist.getString("nguoisua") %></TD>
										<TD align="center">
										
										<%if(quyen[3]!= 0){ %>
										<A  href="../../DonhangtravePdfSvl?userId=<%=userId%>&pdf=<%=dhtvlist.getString("dhtvId")%>"> <img src="../images/pdf.jpg" alt="In file Pdf" title="In file Pdf" width="20" height="20" longdesc="In file Pdf" border=0></A>
										<%} %>
										
										<% if(dhtvlist.getString("trangthai").equals("0")){ %>
										
										<%if(quyen[2]!= 0){ %>
										<A href = "../../DonhangtraveUpdateSvl?userId=<%=userId%>&update=<%= dhtvlist.getString("dhtvId") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
										<%} %>
										
										<%if(quyen[1]!= 0){ %>
										<A href = "../../DonhangtraveSvl?userId=<%=userId%>&delete=<%= dhtvlist.getString("dhtvId") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa đơn hàng này?')) return false;"></A>&nbsp;
										<%} %>
										
										<%if(quyen[4]!= 0){ %>
										<A href = "../../DonhangtraveSvl?userId=<%=userId%>&chotdh=<%= dhtvlist.getString("dhtvId") %>"><img src="../images/Chot.png" alt="Chot" title="Chốt" width="20" height="20" longdesc="Chot" border = 0 onclick="if(!confirm('Bạn muốn chốt đơn trả  hàng này?')) return false;"></A>
										<%} %>
										
										<%}else{ %>
										
										<%if(quyen[3]!= 0){ %>
										<A href = "../../DonhangtraveUpdateSvl?userId=<%=userId%>&display=<%= dhtvlist.getString("dhtvId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
										<%} %>
										
										<%} %>
								
										</TD>
									</TR>
								<% m++;} dhtvlist.close(); } catch(java.sql.SQLException e){} } %>
								
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
</TABLE>
</form>
</BODY>
</HTML>

<% 	

	try{
		if(ddkd != null){ ddkd.close(); ddkd = null ; }
		if(dhtvlist != null){ dhtvlist.close();  dhtvlist = null ; }
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj",null);
	}
	catch (SQLException e) {}

%>
<%}%>

