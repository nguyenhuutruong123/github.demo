<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.banggiamuanpp.IBanggiamuanpp_npp" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IBanggiamuanpp_npp bgmuanppBean = (IBanggiamuanpp_npp)session.getAttribute("assign"); %>
<% ResultSet kv = (ResultSet)bgmuanppBean.getKhuvucIds();  %>
<% ResultSet dvkd = (ResultSet)bgmuanppBean.getDvkdIds(); %>
<% ResultSet kenh = (ResultSet)bgmuanppBean.getKenhIds(); %>
<% ResultSet nppSelected = (ResultSet)bgmuanppBean.getNppSelected(); %>
<% ResultSet nppList = (ResultSet)bgmuanppBean.getNppList(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
function submitform()
{   
   document.forms["bgmuanppForm"].submit();
}

 function saveform()
{
	document.forms['bgmuanppForm'].action.value='save';
    document.forms["bgmuanppForm"].submit();
}

</SCRIPT>
 <script type="text/javascript">
 
 function checkedAll() {
		var nppIds = new Array(<%= bgmuanppBean.getNppString() %>);	
		for (var i =0; i < nppIds.length; i++) 
	 	{
		 	var cb = "chbox" + nppIds[i];
		 	var objCheckBoxes = document.forms["bgmuanppForm"].elements[cb];
			if (document.forms["bgmuanppForm"].elements["chon"].checked == false){
				objCheckBoxes.checked = false;
			}else{
				objCheckBoxes.checked = true;
			}
	 	}
 }
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

<form name="bgmuanppForm" method="post" action="../../BanggiamuanppSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=bgmuanppBean.getUserId() %>'>
<input type="hidden" name="action" value='assign'>
<input type="hidden" name="id" value='<%= bgmuanppBean.getId() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; Dữ liệu nền sản phẩm &gt; Bảng giá bán &gt; Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
		<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
		  	<tr>
				<TD align="left" colspan="4" class="legendtitle">
					<FIELDSET>
					<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    			<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" rows="1"><%=bgmuanppBean.getMessage()%></textarea>
					<% bgmuanppBean.setMessage(""); %>
					</FIELDSET>
			   </TD>
			</tr>			

		 	<TR>
				<TD >
			        <FIELDSET>
			        <LEGEND class="legendtitle">&nbsp;Bảng giá bán cho NPP </LEGEND>
					<TABLE width="100%"cellpadding="0" cellspacing="1">
						<TR>
							<TD>				
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="center">
											<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
												<TR>
													<TD width="15%" class="plainlabel">Tên bảng giá <FONT class="erroralert">*</FONT></TD>
													<TD  class="plainlabel"><INPUT name="bgTen" value="<%= bgmuanppBean.getTen() %>"  type="text" style="width:300px" readonly="readonly"/></TD>
										
												</TR>
												<TR>
								    				<TD class="plainlabel">Đơn vị kinh doanh</TD>
								      				<TD class="plainlabel">
								      				<SELECT name="dvkdId" disabled="disabled" style="width:300px">								      
								  	 					<option value =""></option>
								  	 					<% try{ while(dvkd.next()){ 
								  	 							if(dvkd.getString("dvkdId").equals(bgmuanppBean.getDvkdId())){ %>
								      								<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkd") %></option>
								      						   <%}else{ %>
								     								<option value='<%=dvkd.getString("dvkdId")%>' ><%=dvkd.getString("dvkd")  %></option>
								     							<%}}}catch(java.sql.SQLException e){} %>	
								     	
													</SELECT></TD>
												</TR>
												<TR>
								  					<TD class="plainlabel">Kênh bán hàng </TD>
								  					<TD class="plainlabel">
								      					<SELECT name="kenhId" disabled="disabled" style="width:300px">								      
								  	 						<option value =""></option>
								  	 					<% try{ while(kenh.next()){ 
								    							if((kenh.getString("kenhId").trim()).equals(bgmuanppBean.getKenhId().trim())){ %>
								      								<option value='<%=kenh.getString("kenhId")%>' selected><%=kenh.getString("kenh") %></option>
								      						  <%}else{ %>
								     								<option value='<%=kenh.getString("kenhId")%>'><%=kenh.getString("kenh") %></option>
								     						<%}}}catch(java.sql.SQLException e){} %>	
								     	
                                  						</SELECT></TD>
									  			</TR>
												<TR>
							   	 					<TD class="plainlabel">Khu vực <FONT class="erroralert"> *</FONT></TD>
								 					<TD colspan="4" class="plainlabel">
								 						<SELECT name="kvId" id="KhuVuc" onChange = "submitform();" style="width:300px">
								    						<option value=""></option>
								      					<% try{while(kv.next()){ 
								    					if(kv.getString("kvId").equals(bgmuanppBean.getKvId())){ %>
								      						<option value='<%=kv.getString("kvId")%>' selected><%=kv.getString("kvTen") %></option>
												      	<%}else{ %>
												     		<option value='<%=kv.getString("kvId")%>'><%=kv.getString("kvTen") %></option>
								    				 	<%}}}catch(java.sql.SQLException e){} %>	  
                        								</SELECT>	
                        							</TD>
                        			
												</TR>

											</TABLE>								
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
				</TABLE>
				</FIELDSET>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">							
								<TR class="tbheader">
									<TH width="28%">Mã nhà phân phối</TH>
									<TH width="60%">Tên nhà phân phối </TH>
									<TH width="12%">Chọn						
										<input type="checkbox" name="chon" onClick="checkedAll()">																	
									</TH>
								</TR>

								<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
								   
								   rs = nppSelected;
									   
								   if (!(rs == null)){			
										    
									  	while (rs.next()){								   			
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
										<%  } else {%>
												<TR class= <%= darkrow%> >
										<%  } %>	
												<TD align="left" class="textfont" > <input type="hidden" name='<%="idnpp" +rs.getString("nppId")%>'> <%= rs.getString("nppId") %></TD>
												<TD align="center"><div align="left"><%= rs.getString("nppTen")%></div></TD>
												<TD align="center">
													<input type="checkbox" name='<%= "chbox" + rs.getString("nppId") %>' value='<%= rs.getString("nppId") %>' checked>									
												</TD>

												</TR>
													
								<% 		m++;}
									}	
			
							   	       rs = nppList;
									   
									   if (!(rs == null)){			
										    
									   		while (rs.next()){								   			
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
											<%  } else {%>
													<TR class= <%= darkrow%> >
											<%  } %>	
													<TD align="left" class="textfont"><%= rs.getString("nppId") %></TD>
													<TD align="center"><div align="left"><%= rs.getString("nppTen")%></div></TD>
													<TD align="center">
														<input type="checkbox" name='<%= "chbox" + rs.getString("nppId") %>' value='<%= rs.getString("nppId") %>'>									
													</TD>

													</TR>
													
								<% 			m++;}
										}	
									   
									%>	
								   
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
	if(kv!=null){
		kv.close();
	}
	if(dvkd!=null){
		dvkd.close();
	}
	if(kenh!=null){
		kenh.close();
	}
	if(nppSelected!=null){
		nppSelected.close();
	}
	if(nppList!=null){
		nppList.close();
	}
	bgmuanppBean.DbClose();
}catch(Exception er){
	
}
%>
<%}%>