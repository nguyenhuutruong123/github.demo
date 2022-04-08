<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.tuyenbanhang.ITuyenbanhang" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% ITuyenbanhang tbhBean = (ITuyenbanhang)session.getAttribute("tbhBean"); %>
<% ResultSet ddkd = (ResultSet)tbhBean.getDaidienkd();  %>

<% ResultSet nlvNew = (ResultSet)tbhBean.getNlvList();  %>
<% ResultSet diengiaiNew = (ResultSet)tbhBean.getDiengiaiList();  %>

<% ResultSet kh_tbh_dpt = (ResultSet)tbhBean.getKh_Tbh_DptList(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

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
		document.forms["tbhForm"].submit();	
	}
	function saveform()
	{
		var ddkdTen = document.getElementById("ddkdNewTen");
		var nlv = document.getElementById("nlvNewTen");
		var tbhTen = document.getElementById("tbhNewTen");
		
		if(ddkdTen.value == "")
		{
			alert("Vui lòng chọn đại  diện kinh doanh của tuyến bán hàng mới...");
			return;
		}
		if(nlv.value == "")
		{
			alert("Vui lòng chọn ngày làm việc của tuyến bán hàng mới...");
			return;
		}
		if(tbhTen.value == "")
		{
			alert("Vui lòng chọn tên của tuyến bán hàng mới...");
			return;
		}
		
		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

		
		document.forms['tbhForm'].action.value='save';
	    document.forms['tbhForm'].submit();
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

<form name="tbhForm" method="post" action="../../TuyenbanhangMoveSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="nppId" value='<%= tbhBean.getNppId() %>'>
<input type="hidden" name="id" value='<%= tbhBean.getId() %>'>
<input type="hidden" name="action" value='1'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
	  <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <TR height="22">
						  	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền > Dữ liệu nền kinh doanh > Tuyến bán hàng &gt; Chuyển đổi 
						  	<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= tbhBean.getNppTen() %></TD>
						 </TR>
					  </table>
					</TD>
			  </TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="0">
				<TR >
					<TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR class = "tbdarkrow">
								<TD width="30" align="left"><A href="javascript:history.back()" >			 		
									<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								<TD width="2" align="left" ></TD>
				    			<TD width="30" align="left" >
				    			<div id="btnSave">
				    			<A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
				    			</div>
				    			</TD>
								<TD align="left" >&nbsp;</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
					
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="0">
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>			
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  rows="1" ><%= tbhBean.getMessage() %></textarea>				
						</FIELDSET>
				   </TD>
				</tr>
				<TR>
				  <TD height="100%" width="100%">
				<FIELDSET>
				<LEGEND class="legendtitle">&nbsp;Thông tin tuyến bán hàng cũ  &nbsp;</LEGEND>
							<TABLE width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
								  <TD width="15%"  class="plainlabel">Nhân viên bán hàng </TD>
								  <TD class="plainlabel"><INPUT name="ddkdTen"
										type="text" value="<%= tbhBean.getDdkd() %>" size="20" height="15" readonly="readonly">				   	
								  </TD>
							  </TR>
								<TR>
									<TD  class="plainlabel">Diễn giải tuyến bán hàng  </TD>
									<TD  class="plainlabel"><INPUT name="diengiai"
										type="text" value="<%= tbhBean.getDiengiai() %>" size="60" height="15" readonly="readonly"></TD>
								</TR>
								
								<TR>
								  <TD class="plainlabel">Ngày làm việc </TD>
							      <TD class="plainlabel" valign="middle"> <INPUT name="ngaylamviec" 
							      		type="text" value="<%= tbhBean.getNgaylamviec() %>" size="15" height="15" readonly="readonly">
							      </TD>
							  </TR>
							</TABLE>
	
				    </FIELDSET>
				  </TD>	
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" >
				<TR>
				  <TD height="100%" width="100%">
				<FIELDSET>
				<LEGEND class="legendtitle">&nbsp;Thông tin tuyến bán hàng mới &nbsp;</LEGEND>
						<TABLE width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
								  <TD style="width:15%" class="plainlabel">Nhân viên bán hàng </TD>
								  <TD class="plainlabel">
								     <SELECT name="ddkdNewTen" id="ddkdNewTen" onChange = "submitform();" >
								  	<option value=""></option>
								 	<% 
									  try{ while(ddkd.next()){ 
									    	if(ddkd.getString("ddkdId").equals(tbhBean.getDdkdNewId())){ %>
									      		<option value='<%=ddkd.getString("ddkdId") %>' selected><%=ddkd.getString("ddkdTen") %></option>
									      	<%}else{ %>
									     		<option value='<%=ddkd.getString("ddkdId") %>'><%=ddkd.getString("ddkdTen") %></option>
									     	<%}}}catch(java.sql.SQLException e){}
									   %>
							    	</SELECT>
								  </TD>
							  </TR>
							  <TR>
								  <TD class="plainlabel">Ngày làm việc </TD>
							      <TD class="plainlabel" valign="middle">
							        <select name="nlvNewTen" id="nlvNewTen" onChange = "submitform();">
							        <option value=""></option>
							        <% 
							        if(nlvNew != null)
									  try{ while(nlvNew.next()){ 
									    	if(nlvNew.getString("nlvNewTen").equals(tbhBean.getNlvNew())){ %>
									      		<option value='<%=nlvNew.getString("nlvNewTen") %>' selected><%=nlvNew.getString("nlvNewTen") %></option>
									      	<%}else{ %>
									     		<option value='<%=nlvNew.getString("nlvNewTen") %>' ><%=nlvNew.getString("nlvNewTen") %></option>
									     	<%}}}catch(java.sql.SQLException e){}
									   %>					          
						             </select>
							      </TD>
							  </TR>
								<TR>
									<TD class="plainlabel">Diễn giải tuyến bán hàng </TD>
									<TD  class="plainlabel">
									<select name="tbhNewTen" id="tbhNewTen">
							        <option value=""></option>
							        <% if(diengiaiNew!=null)
									  try{ while(diengiaiNew.next()){ 
									    	if(diengiaiNew.getString("tbhNewTen").equals(tbhBean.getDiengiaiNew())){ %>
									      		<option value='<%=diengiaiNew.getString("tbhNewTen") %>' selected><%=diengiaiNew.getString("tbhNewTen") %></option>
									      	<%}else{ %>
									     		<option value='<%=diengiaiNew.getString("tbhNewTen") %>'><%=diengiaiNew.getString("tbhNewTen") %></option>
									     	<%}}}catch(java.sql.SQLException e){}
									   %>					          
						             </select>
									</TD>
								</TR>								
							</TABLE>			
				</FIELDSET>
				  </TD>	
				</TR>
			</TABLE>

			<TABLE width = 100% border="0" cellpadding="0" cellspacing ="0">
			<TR>
			 	<TD width = "100%" valign="top">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%">
								<FIELDSET>
								<LEGEND class="legendtitle">Khách hàng được phân vào tuyến cũ này</LEGEND>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4" id="tb_kh_tbh_dpt">
							<tbody id="kh_tbh_dpt">
								<TR class="tbheader">
								  <TH width="5%">Thứ tự </TH>
								  <TH width="13%">Mã KH </TH>
									<TH width="30%">Tên KH</TH>
									<TH width="40%">Địa chỉ </TH>
									<TH width="12%">Tần số </TH>
								</TR>
								<%
								int i = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								try{while(kh_tbh_dpt.next()){ 
									if (i % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
								<%}%>
								<TD align="center">
							    	<input name='thutu' type="text" value='<%= kh_tbh_dpt.getString("sott") %>' size="5"></TD>
								<TD align="left" ><input type="hidden" name="khIds" value='<%=kh_tbh_dpt.getString("khId")%>' > <%= kh_tbh_dpt.getString("smartid") %> </TD>
								<TD align="left" > <%= kh_tbh_dpt.getString("ten") %> </TD>
								<TD align="center"><p align="left"> <%= kh_tbh_dpt.getString("diachi") %> </TD>
								<TD align="center"><select name="tansoList" >
                                    <% if (kh_tbh_dpt.getString("tanso").equals("F2-1")){%>
										  	<option value="F2-1" selected>F2 Lẻ</option>
										  	<option value="F2-2">F2 Chẵn</option>
										  	<option value="F4">F4</option>
										  	<option value="F6">F6 </option>									  
									<%}else if(kh_tbh_dpt.getString("tanso").equals("F2-2")) {%>
										 	<option value="F2-1">F2 Lẻ</option>
										 	<option value="F2-2" selected>F2 Chẵn</option>
										  	<option value="F4">F4</option>
										  	<option value="F6">F6 </option>									  									  
									<%}else if(kh_tbh_dpt.getString("tanso").equals("F4")) {%>
										 	<option value="F2-1">F2 Lẻ</option>
										  	<option value="F2-2">F2 Chẵn</option>
										  	<option value="F4" selected>F4</option>
										  	<option value="F6">F6 </option>									  									  
									<%}else{%>
											<option value="F2-1">F2 Lẻ</option>
										  	<option value="F2-2">F2 Chẵn</option>
										  	<option value="F4">F4</option>
										  	<option value="F6" selected>F6 </option>									  
									<%}%>
                                   </select></TD>
                               </TR>
						     	<% i++;}}catch(java.sql.SQLException e){}  %>
							<tr class="tbfooter" > <td colspan="6" >&nbsp;</td> </tr> 
							</tbody>
							</TABLE>

								</FIELDSET>								
							</TD>
						</TR>
				    </TABLE>
				</TD>
				
			</TR>
		</TABLE>

	    <!--end body Dossier--></TD>
	</TR>
	
</TABLE>
</form>
</BODY>
</HTML>
<% 	

	try{
		if(ddkd != null)
			ddkd.close();
		if(kh_tbh_dpt != null)
			kh_tbh_dpt.close();
		if(diengiaiNew != null)
			diengiaiNew.close();
		if(nlvNew != null)
			nlvNew.close();
		if(tbhBean != null){
			tbhBean.DBclose();
			tbhBean = null;}		
	
	}
	catch (SQLException e) {}

%>
<%}%>