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
<% 
String nnId = "";

if (nnId == null) {
	nnId = "vi";
}

	ResultSet ddkd = (ResultSet)obj.getDaidienkd(); 
	ResultSet npp = (ResultSet)obj.getNpp();
	int[] quyen = new  int[6];
	String view = obj.getView();
	System.out.println("View: " + view);
	String url = "";
	if (view != null && view.equals("TT")) {
		quyen = Utility.Getquyen("DonhangtraveNguyenDonSvl","&view=TT", userId);
		url = util.getChuyenNguUrl("DonhangtraveNguyenDonSvl", "&view=TT", nnId);
	}
	else {
		quyen = Utility.Getquyen("DonhangtraveNguyenDonSvl","", userId);
		url = util.getChuyenNguUrl("DonhangtraveNguyenDonSvl", "", nnId);
	}
	ResultSet trangthaiNguyenDonRs = obj.getTrangthaiNguyenDonRs();
	
	
	
	
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

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function() { 
		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
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

<form name="dhForm" method="post" action="../../DonhangtraveNguyenDonSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="action1" value="1" >
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" id="msg" value="<%= obj.getMsg() %>" >
<input type="hidden" id="view" value="<%= obj.getView() %>" >


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
							   <TD align="left" colspan="2" class="tbnavigation"> &nbsp;<%= url %>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= obj.getNppTen() %>&nbsp;</tr>
						</TABLE>
					</TD>
		  </TR>
			<TR>
				<TD >
				<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm</LEGEND>
							<TABLE width="100%" cellpadding="6" cellspacing="0">
								
								<%
									if (view != null && view.equals("TT")) { %>
									 <TR >
											<TD width="22%" class="plainlabel">Nhà phân phối  </TD>
											<TD colspan="3" class="plainlabel"> 
												<SELECT name="nppId" id="nppId" onChange = "submitform();">
										 			 <option value=""></option>
													  <% if(npp != null){
														  try{ while(npp.next()){ 		
											    			if(npp.getString("nppId").equals(obj.getNppId())){ %>
											      				<option value='<%=npp.getString("nppId")%>' selected><%= npp.getString("nppTen") %></option>
											      			<%}else{ %>
											     				<option value='<%=npp.getString("nppId")%>'><%= npp.getString("nppTen") %></option>
											     			<%}} npp.close(); }catch(java.sql.SQLException e){}} %>	 
	                                   			</SELECT>
                                   			</TD>
									</TR>	
								<% } %>
								
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
									<SELECT name="trangthainguyendon" onChange = "submitform();">
										  <option value=""> </option>
										  <% try{ while(trangthaiNguyenDonRs.next()){ 
								    			if(trangthaiNguyenDonRs.getString("t").equals(obj.getTrangthaiNguyenDon())){ %>
								      				<option value='<%=trangthaiNguyenDonRs.getString("t")%>' selected><%=trangthaiNguyenDonRs.getString("tt") %></option>
								      			<%}else{ %>
								     				<option value='<%=trangthaiNguyenDonRs.getString("t")%>'><%=trangthaiNguyenDonRs.getString("tt") %></option>
								     			<%}}}catch(java.sql.SQLException e){e.printStackTrace();} %>	 
                                    </SELECT></TD> 
							    </TR>
								
								<TR>
									<TD class="plainlabel" >Từ ngày</TD>
									<TD class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
											    <input class="days" autocomplete="off" type="text" name="tungay" size="11">
											</TD></TR>
										</TABLE>	
								</TR>
								<TR>
								  <TD class="plainlabel" >Đến ngày</TD>
							      <TD width="100%" class="plainlabel">
								  		<TABLE cellpadding="0" cellspacing="0">
										  	<TR><TD>
											  <input class="days" autocomplete="off" type="text" name="denngay" size="11">
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
									<TH width="10%" align="center">Mã phiếu trả hàng </TH>
									<TH width="10%" align="center">Mã đơn hàng </TH>
									<TH width="10%" align="center">Mã khách hàng </TH>
									<TH width="10%" align="center">Lý do trả hàng </TH>
									<TH width="10%" align="center">Ngày trả </TH>
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
												
										<TD align="center"><%= dhtvlist.getString("pk_seq") %></TD>                                   
										<TD align="center"><%= dhtvlist.getString("donhang_fk") %></TD>
										<TD align="left"><%= dhtvlist.getString("makh") %></TD>
										<TD align="left"><%= dhtvlist.getString("lydo") %></TD>
										<TD align="center"><%= dhtvlist.getString("ngaynhap") %></TD>	
										<TD align="center"><%= dhtvlist.getString("trangthaihienthi") %></TD>										
										<TD align="center"><%= dhtvlist.getString("ngaytao") %></TD>
										<TD align="left"><%= dhtvlist.getString("nguoitao")%></TD>
										<TD align="center"><%= dhtvlist.getString("ngaysua") %></TD>
										<TD align="left"><%= dhtvlist.getString("nguoisua") %></TD>
										<TD align="center">										
										
										<% 	String trangthaidh = dhtvlist.getString("trangthai")== null?"":dhtvlist.getString("trangthai");
											if (view.equals("TT")) { 
												if (trangthaidh.equals("1")) { 
													if(quyen[Utility.XEM]!= 0){ %>	
													<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangtraveNguyenDonUpdateSvl?userId="+userId+"&display="+dhtvlist.getString("pk_seq")+"&view="+view) %>">
													<img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
													<%} %>		
													
													<% if (quyen[Utility.CHOT]!= 0) { %>
													<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangtraveNguyenDonSvl?userId="+userId+"&HOchot="+dhtvlist.getString("pk_seq")) %>">
													<img src="../images/Chot.png" alt="Chot" title="Chốt" width="20" height="20" longdesc="Chot" border = 0 
														onclick="if(!confirm('Bạn có muốn chốt phiếu này?')) return false;">
													</A>

													<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangtraveNguyenDonSvl?userId="+userId+"&HOtuchoi="+dhtvlist.getString("pk_seq")) %>">
													<img src="../images/warning30.gif" alt="Từ chối" title="Từ chối" width="20" height="20" longdesc="Từ chối" border = 0></A>
													<%} %>
												<% } 									
												else { 
													if(quyen[Utility.XEM]!= 0){ %>	
													<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangtraveNguyenDonUpdateSvl?userId="+userId+"&display="+dhtvlist.getString("pk_seq")+"&view="+view) %>">
													<img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
													<%} %>		
											<% } %>		
										<%} else { 
												if (trangthaidh.equals("0")) {
													if(quyen[Utility.SUA]!= 0) { %>
													<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangtraveNguyenDonUpdateSvl?userId="+userId+"&update="+dhtvlist.getString("pk_seq"))%>">
													<img src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0></A>&nbsp;
													<%} %>
											
													<%if(quyen[Utility.XOA]!= 0){ %>
													<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangtraveNguyenDonSvl?userId="+userId+"&delete="+dhtvlist.getString("pk_seq")) %>">
													<img src="../images/Delete20.png" alt="Xoá" title="Xoá" width="20" height="20" longdesc="Xoá" border=0 onclick="if(!confirm('Bạn muốn xóa phiếu hàng này?')) return false;"></A>&nbsp;
													<%} %>
											
													<%if(quyen[Utility.CHOT]!= 0 && !view.equals("TT")){ %>
													<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangtraveNguyenDonSvl?userId="+userId+"&chot="+dhtvlist.getString("pk_seq")) %>">
													<img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" longdesc="Chốt" border = 0 
														onclick="if(!confirm('Bạn có muốn chốt phiếu này?')) return false;">
													</A>
													<%} %>										
											
													<%if(quyen[Utility.XEM]!= 0){ %>
													<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangtraveNguyenDonUpdateSvl?userId="+userId+"&display="+dhtvlist.getString("pk_seq")) %>">
													<img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
													<%} %>
											<% } else {%>
													<%if(quyen[Utility.XEM]!= 0){ %>
													<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangtraveNguyenDonUpdateSvl?userId="+userId+"&display="+dhtvlist.getString("pk_seq")) %>">
													<img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
													<%} %>
											<% } %>
										<% } %>
									
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

