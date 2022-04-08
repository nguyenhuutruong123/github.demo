<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.phieuxuatkho.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% IPhieuxuatkhoList obj = (IPhieuxuatkhoList)session.getAttribute("obj"); %>
<% List<IPhieuxuatkho> pxkList = (List<IPhieuxuatkho>)obj.getPxkList(); %>
<% ResultSet nvgn = (ResultSet)obj.getNhanvienGN(); %>

<% String userId = (String) session.getAttribute("userId");  %>

<% obj.setNextSplittings(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<SCRIPT language="javascript" type="text/javascript">
	function confirmLogout()
	{
	   if(confirm("Ban co muon dang xuat?"))
	   {
			top.location.href = "home.jsp";
	   }
	   return;
	 }
	function searchphieu()
	{   
		document.forms["pxkForm"].action.value="search";
	    document.forms["pxkForm"].submit();
	}
	function clearform()
	{
		 document.forms["pxkForm"].trangthai.value="";
		 document.forms["pxkForm"].nvgnTen.selectedIndex = -1;
		 document.forms["pxkForm"].trangthai.selectedIndex = -1;
		 document.forms["pxkForm"].tungay.value = "";
		 document.forms["pxkForm"].action.value = "clear";
		 //document.forms["pxkForm"].submit();
	}
	function newform(){
		
		 document.forms["pxkForm"].action.value="Tao moi";
		 document.forms["pxkForm"].submit();
	}
	function thongbao()
	{
		if(document.getElementById("msg").value != '')
			alert(document.getElementById("msg").value);
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

<form name="pxkForm" method="post" action="../../PhieuxuatkhoSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >

<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
<script type="text/javascript">
	thongbao();
</script>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
			<TR>
				<TD align="left" class="tbnavigation">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
   							<TD align="left" colspan="2" class="tbnavigation">&nbsp;Xử lý đơn hàng > Phiếu xuất kho</TD>
   							
   							<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= obj.getNppTen() %></TD>
						</tr>
					</table>
				</TD>
		  	</TR>
		</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
			<TR>
				<TD >
					<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</LEGEND>
					<TABLE  width="100%" cellspacing="0" cellpadding="3">
						<TR class="tblightrow">
							<TD  class="plainlabel">Nhân viên giao nhận </TD>
							<TD colspan="3" class="plainlabel"> 
				 			<SELECT name="nvgnTen" onChange = "searchphieu();">
					 			 <option value="">&nbsp;&nbsp;</option>
								  <% if(nvgn != null){
									  try{ while(nvgn.next()){ 
						    			if(nvgn.getString("nvgnId").equals(obj.getNvgnId())){ %>
						      				<option value='<%=nvgn.getString("nvgnId")%>' selected><%=nvgn.getString("nvgnTen") %></option>
						      			<%}else{ %>
						     				<option value='<%=nvgn.getString("nvgnId")%>'><%=nvgn.getString("nvgnTen") %></option>
						     			<%}}}catch(java.sql.SQLException e){} }%>	 
                               			</SELECT></TD>
						</TR>
						<TR>
							<TD width="19%" class="plainlabel">Trạng thái </TD>
							<TD width="81%" colspan="3" class="plainlabel">
								<SELECT name ="trangthai" onChange = "searchphieu();">                                  
                                   <% if (obj.getTrangthai().equals("1")){%>
                                         <option value="1" selected>Đã chốt</option>
                                         <option value="0">Chưa chốt</option>
                                         <option value=""> </option>                                        
                                   <%}else if(obj.getTrangthai().equals("0")) {%>
                                         <option value="0" selected>Chưa chốt</option>
                                         <option value="1" >Đã chốt</option>
                                         <option value="" > </option>                                         
                                   <%}else{%>                                          
                                         <option value="" selected> </option>
                                         <option value="1" >Đã chốt</option>
                                         <option value="0" >Chưa chốt</option>
                                   <%}%>
                                </SELECT>
							</TD>
						</TR>
						<TR>
							<TD class="plainlabel" >Từ ngày</TD>
							<TD class="plainlabel">
								<TABLE cellpadding="0" cellspacing="0">
									<TR><TD>
									    <input type="text" name="tungay" value="<%= obj.getTungay() %>" size="11">
									</TD><TD>
										 <a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'pxkForm.tungay',false, 'yyyy-mm-dd'); return false;">
		  											&nbsp;<img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>
									</TD></TR>
								</TABLE>
						</TR>
						<TR>
							<TD class="plainlabel" colspan="3">
							<a class="button2" href="javascript:searchphieu()">
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
		</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			<TR>
					<TD width="100%"> 
					<FIELDSET>
					<LEGEND class="legendtitle"> Phiếu xuất kho &nbsp;&nbsp;&nbsp;&nbsp;
						<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
					
					 <!--  <INPUT name="action" type="submit" value="Tao moi">-->	
					</LEGEND>
					<TABLE class="" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="2">
									<TR class="tbheader">
											<TH align="center">Mã phiếu</TH>
											<TH align="center"> NV giao nhận</TH>
											<TH align="center">Ngày lập</TH>
											<TH align="center">Trạng thái</TH>
											<TH align="center">Ngày tạo</TH>
											<TH align="center">Người tạo </TH>
											<TH align="center">Ngày sửa</TH>
											<TH align="center"> Người sửa </TH>
											<TH align="center">Tác vụ</TH>
									</TR>
									<% 
									IPhieuxuatkho pxk = null;
									int size = pxkList.size();
									int m = 0;
									while (m < size){
										pxk = pxkList.get(m);
										if (m % 2 != 0) {%>						
											<TR class= "tblightrow">
										<%} else {%>
											<TR class= "tbdarkrow">
										<%}%>
												<TD align="center"><%= pxk.getId() %></TD>                                   
												<TD align="left"><%= pxk.getNvgnId() %></TD>  
												<TD align="center"><%= pxk.getNgaylap() %></TD> 
												 <% if (pxk.getTrangthai().equals("1")){ %>
                                                    <TD align="center">Đã chốt</TD>
                                                <%}else{%>
                                                    <TD align="center">Chưa chốt</TD>
                                                <%}%>                 
												<TD align="center"><%= pxk.getNgaytao()%></TD>
												<TD align="left"><%= pxk.getNguoitao()%></TD>
												<TD align="center"><%= pxk.getNgaysua()%></TD>
												<TD align="left"><%= pxk.getNguoisua()%></TD>
												<TD align="center" valign="middle">
													<% if(pxk.getTrangthai().equals("0")) {%>
														<A href = "../../PhieuxuatkhoUpdateSvl?userId=<%=userId%>&update=<%= pxk.getId() %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
														<A href = "../../PhieuxuatkhoSvl?userId=<%=userId%>&chotphieu=<%= pxk.getId() %>"><img src="../images/Chot.png" alt="Chot phieu xuat" title="Chốt phiếu xuất" width="20" height="20" longdesc="Chot phieu xuat" border = 0></A>
													<%}else{ %>
														<A href = "../../PhieuxuatkhoUpdateSvl?userId=<%=userId%>&display=<%= pxk.getId() %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
													<%} %>
												</TD>
											</TR>
								<%m++; }%>
								
<tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('pxkForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('pxkForm', 'prev')"> &nbsp;
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
													<a href="javascript:View('pxkForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('pxkForm', 'next')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('pxkForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr>  	
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			</FIELDSET>
		</TD>
	</TR>
</TABLE>
		<!--end body Dossier--></TD>
</TR>
</TABLE>
</form>
</BODY>
</html>
<%
	try
	{
		if(!(nvgn == null))
			nvgn.close();
		obj.DBclose();
	}catch(java.sql.SQLException e){}
%>