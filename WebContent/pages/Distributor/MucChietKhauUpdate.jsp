<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.mucchietkhau.*" %>
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

<% IMucchietkhau mckBean = (IMucchietkhau)session.getAttribute("mckBean"); %>
<% ResultSet ddkd = (ResultSet)mckBean.getDdkdList(); %>
<% ResultSet khachhang = (ResultSet)mckBean.getKhachhangList();  %>
<% ResultSet khselected = (ResultSet)mckBean.getSelectedKhlist(); %>
<% Hashtable<Integer, String> kh_mckIds = (Hashtable<Integer, String>)mckBean.getKh_MckIds(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<SCRIPT language="javascript" type="text/javascript">
	function confirmLogout()
	{
	   if(confirm('Bạn có muốn đăng xuất?'))
	   {
			top.location.href = "home.jsp";
	   }
	   return
	 }
	function submitform()
	{   
	   document.forms["mckForm"].submit();
	}
	function saveform()
	{	  
		 var diengiai = document.getElementById("diengiai");
		 var chietkhau = document.getElementById("chietkhau");
		 
		 if(diengiai.value == "")
		 {
			 alert("Vui lòng nhập diễn giả mức chiết khấu...");
			return;
		 }
		 if(chietkhau.value == "")
		 {
			 alert("Vui lòng nhập mức chiết khấu...");
			return;
		 }
				 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

		 document.forms['mckForm'].action.value='save';
	    document.forms['mckForm'].submit();
	}
</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="mckForm" method="post" action="../../MucchietkhauUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<input name="id" type="hidden" value='<%= mckBean.getId() %>'>
<input type="hidden" name="nppId" value='<%= mckBean.getNppId() %>'>
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
	  <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <TR height="22">
						  	<TD align="left" colspan="2" class="tbnavigation"> &nbsp;Thiết lập dữ liệu nền  &gt; Dữ liệu nền kinh doanh &gt; Mức chiết khấu &gt; Cập nhật
						  	<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= mckBean.getNppTen() %>&nbsp; </TD>
						 </TR>
					  </table>
					</TD>
			  </TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="2">
				<TR >
					<TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR class = "tbdarkrow">
								<TD width="30" align="left"><A href= "javascript:history.back()">			 		
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
					
			<TABLE width="100%%" border="0" cellpadding="0" cellspacing="0" >
				<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
		                        	<LEGEND class="legendtitle">Báo lỗi nhập liệu</LEGEND>              
		                        	<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width:100%" rows="1" ><%= mckBean.getMessage() %></textarea>
		                        	<% mckBean.setMessage(""); %>
                        		</FIELDSET>
						   </TD>
					</tr>
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET>
						<LEGEND class="legendtitle">&nbsp;Thông tin mức chiết khấu &nbsp;</LEGEND>
						<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
							<TR>
								<TD width="24%" class="plainlabel">Diễn giải mức chiết khấu <FONT class="erroralert">*</FONT></TD>
								<TD width="76%" class="plainlabel"><INPUT name="diengiai" id="diengiai" size="30"
										type="text" value="<%= mckBean.getDiengiai() %>" ></TD>
							</TR>
							<TR>
								<TD class="plainlabel">Chiết khấu <FONT class="erroralert">*</FONT></TD>
								<TD class="plainlabel" valign="middle">
								    <INPUT name="chietkhau" id="chietkhau" size="10" type="text" value="<%= mckBean.getChietkhau() %>" >&nbsp;% 
								</TD>
							</TR>
						</TABLE>
	
						</FIELDSET>
				 	</TD>	
				</TR>
               
			</TABLE>
        <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD colspan = "6" width="100%">
						<FIELDSET><LEGEND class="legendtitle">Khách hàng hướng chiết khấu</LEGEND>
						  	<div class="title" align="left" >Chọn Nhân viên bán hàng
						  	<select name="ddkdTen" onChange = "submitform();">
								<option value="" selected></option>
									<%
									if(ddkd!=null)
									try{while(ddkd.next()){ 
								    	if(ddkd.getString("ddkdId").equals(mckBean.getDdkdId())){ %>
								      		<option value='<%= ddkd.getString("ddkdId") %>' selected><%= ddkd.getString("ddkdTen") %></option>
								      	<%}else{ %>
								     		<option value='<%= ddkd.getString("ddkdId") %>'><%= ddkd.getString("ddkdTen") %></option>
								     <%}}}catch(java.sql.SQLException e){} %>	   
								</select><br>
							</div>					
								
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="6">
								<TR class="tbheader">
								  <TH align="center" width="18%">Mã khách hàng</TH>
									<TH width="30%">Tên khách hàng </TH>
									<TH width="45%">Địa chỉ </TH>
									<TH align="center" width="7%">Chọn </TH>
								</TR>
								<%
									int j = 0; 
									try{
										if(khselected!=null)
										while(khselected.next()){ 
										if (j % 2 != 0) {%>						
											<TR class= "tblightrow">
										<%} else {%>
											<TR class= "tbdarkrow" >
										<%}%>
										<TD align="center"><div align="center"><%= khselected.getString("khId") %> </div></TD>
										<TD align="center"><div align="left"><%= khselected.getString("khTen") %></div></TD>
										<TD align="center"><div align="left"><%= khselected.getString("diachi") %> </div></TD>
										<% if(!kh_mckIds.isEmpty()){
										 		if(kh_mckIds.contains(khselected.getString("khId"))){ %>
											   		<TD align="center">
											   			<input name="khIds" type="checkbox" value ="<%= khselected.getString("khId") %>" checked></TD>
												<%}else{%>
											   		<TD align="center">
											   			<input name="khIds" type="checkbox" value ="<%= khselected.getString("khId") %>"></TD>
										<%}
										}else{%>
												<TD align="center">
														<input name="khIds" type="checkbox" value ="<%= khselected.getString("khId") %>" checked></TD>
								     	<%}
										j++;%> 
		                             	</TR>
								   		<%} %>
								   	<%
								   	if(khachhang!= null)
								   	while(khachhang.next()){ 
										if (j % 2 != 0) {%>						
											<TR class= "tblightrow">
										<%} else {%>
											<TR class= "tbdarkrow" >
										<%}%>
										<TD align="center"><div align="center"><%= khachhang.getString("khId") %> </div></TD>
										<TD align="center"><div align="left"><%= khachhang.getString("khTen") %></div></TD>
										<TD align="center"><div align="left"><%= khachhang.getString("diachi") %> </div></TD>								   		
								   		<% 
											if(kh_mckIds.contains(khachhang.getString("khId"))){ %>
									   		<TD align="center">
									   				<input name="khIds" type="checkbox" value ="<%= khachhang.getString("khId") %>" checked></TD>
											<%}else{%>
										   		<TD align="center"><input name="khIds" type="checkbox" value ="<%= khachhang.getString("khId") %>"></TD>
										<%}%>
							     		<%j++;%>
                                    		</TR> 
								   		<%}}catch(java.sql.SQLException e){
								   	} %>									
										
						</TABLE>
						</FIELDSET>								
					</TD>
				</TR>
		    </TABLE>
	    <!--end body Dossier--></TD>   
	    
	</TR>
</TABLE>
</form>
</body>
</html>
<%
	try
	{
		if(!(khachhang == null))
			ddkd.close();
		if(!(khachhang == null))
			khachhang.close();
		if(khselected==null){
			khselected.close();
		}
		if(kh_mckIds!=null){
			kh_mckIds.clear();
		}
		if(mckBean!=null){
			mckBean.DBclose();
			mckBean=null;
		}
		session.setAttribute("mckBean",null);
		
	}catch(java.sql.SQLException e){}
	}
%>
