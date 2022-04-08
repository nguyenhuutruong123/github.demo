<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhomskus.INhomSkus" %>
<%@ page  import = "geso.dms.center.beans.nhomskus.imp.NhomSkus" %>
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

<% INhomSkus nskusBean = (INhomSkus)session.getAttribute("nskusBean"); %>
<% 	ResultSet nskusList = (ResultSet)nskusBean.getNSkusList();
	ResultSet spList = (ResultSet)nskusBean.getSpList(); 
	ResultSet spSelected = (ResultSet)nskusBean.getSpSelected();
	
	  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["nskusForm"].submit();
}

function checkedAll(chk) {
	for(i=0; i<chk.length; i++){
	 	if(document.nskusForm.chon.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="nskusId" value='<%= nskusBean.getId()  %>'>
<input type="hidden" name="action" value="0">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Sản phẩm  &gt; Nhóm sản phẩm &gt; Cập nhật</TD> 
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
						    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
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
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=nskusBean.getMessage()%></textarea>
						<% nskusBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin nhóm sản phẩm</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						<TR>
								<TD width="29%" class="plainlabel" >Mã<FONT class="erroralert"> </FONT></TD>
								<TD class="plainlabel"><INPUT type="text" name="ma" style="width:300px" value='<%= nskusBean.getMa()%>'></TD>
							</TR>
							<TR>
								<TD width="29%" class="plainlabel" >Tên nhóm<FONT class="erroralert"> </FONT></TD>
								<TD class="plainlabel"><INPUT type="text" name="ten" style="width:300px" value='<%= nskusBean.getTen()%>'></TD>
							</TR>
						
						  		<TR>
						  		
							  
							    	<td><input name="trangthai" type="hidden" value="1" ></td>
							    
						  </TR>
						  
						</TABLE>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">							
								<TR class="tbheader">
									<TH width="28%">Mã sản phẩm </TH>
									<TH width="60%">Tên sản phẩm </TH>
									<TH width="12%">Chọn
										<input type="checkbox" name="chon" onClick="checkedAll(document.nskusForm.sanpham)">								
									
									</TH>
								</TR>

								<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
							   
							   	       rs = spSelected;
									   
									   if (!(rs == null)){			
										    
									   		while (rs.next()){								   			
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
											<%  } else {%>
													<TR class= <%= darkrow%> >
											<%  } %>	
													<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
													<TD align="center"><div align="left"><%= rs.getString("ten")%></div></TD>
													<TD align="center">
														<input type="checkbox" name="sanpham" value='<%= rs.getString("pk_seq") %>' checked>									
													</TD>

													</TR>
													
								<% 			m++;}
										}	
			
							   	       rs = spList;
									   
									   if (!(rs == null)){			
										    
									   		while (rs.next()){								   			
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
											<%  } else {%>
													<TR class= <%= darkrow%> >
											<%  } %>	
													<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
													<TD align="center"><div align="left"><%= rs.getString("ten")%></div></TD>
													<TD align="center">
														<input type="checkbox" name="sanpham" value='<%= rs.getString("pk_seq") %>'>									
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
<% 	if(nskusList != null) nskusList.close(); 
	if(spList != null) spList.close(); 
	if(spSelected != null) spSelected.close();
%>
<%}%>