<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhanhang.INhanhang" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.sql.SQLException" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi";
	} 
	String url = util.getChuyenNguUrl("NhanhangSvl","",nnId);
	%>

<% INhanhang nhBean = (INhanhang)session.getAttribute("nhanhangBean");%>
<% ResultSet dvkdlist = (ResultSet)nhBean.getDvkdList(); %>
<% ResultSet nganhhanglist = (ResultSet)nhBean.getNganhHangList();%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["nhanForm"].submit();
}

function loc()
{
	document.forms['nhanForm'].action.value = 'loc';
    document.forms["nhanForm"].submit();
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

<form name="nhanForm" method="post" action="../../NhanhangNewSvl" charset="utf-8">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
		<!--begin common dossier info---> <!--End common dossier info--->
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation"><%=url %> &gt; Tạo mới </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>
		</TABLE>		
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR ><TD >
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
				</TD></TR>
		</TABLE>
					
		<TABLE width = 100% cellpadding = "4" cellspacing = "0" border = "0">
		  	<tr>
				<TD align="left" colspan="4" class="legendtitle">
					<FIELDSET>
					<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1"><%=nhBean.getMessage()%></textarea>
						<% nhBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
			</tr>			
			<TR>
				<TD width="100%" align="left" >
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Nhãn hàng mới</LEGEND>

					<TABLE  width="100%" cellpadding="6" cellspacing="0">
						<TR>
							<TD width="20%" class="plainlabel required"><%=ChuyenNgu.get("Nhãn hàng",nnId) %><FONT class="erroralert"></FONT></TD>
							<TD width="80%" class="plainlabel"><INPUT name="nhanhang" type="text" size="40" value="<%=nhBean.getNhanhang()%>"/></TD>

						</TR>

						<TR>
							<TD width="19%" class="plainlabel required"><%=ChuyenNgu.get("Ngành hàng",nnId) %></TD>
							  	<TD class="plainlabel" >
								<TABLE cellpadding="0" cellspacing="0" border="0">
									<TR>
								  		<TD><SELECT  name="nganhhangId" onchange="loc()">
								  		<option value=""></option>
 										<% 				
											if(nganhhanglist!=null)
											{
											try{
												nganhhanglist.beforeFirst();
												while (nganhhanglist.next()){
													if(nganhhanglist.getString("PK_SEQ").equals(nhBean.getNganhHangId()))
													{
														%>			
														<option value= <%=nganhhanglist.getString("PK_SEQ")%> selected><%= nganhhanglist.getString("TEN") %></option>													
													<%
													}
													else{
														%>			
														<option value= <%=nganhhanglist.getString("PK_SEQ")%> ><%= nganhhanglist.getString("TEN") %></option>													
													<%
													}
												%>
												
												<%}															
												}catch(java.sql.SQLException e){}
												
											}
												%>			 										                                           
                                      			</SELECT>
                                   			</TD>
										</TR>
									</TABLE>									
								</TD>

						</TR>
						
				      	<TR>
							<TD width="19%" class="plainlabel required	"><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %></TD>
							  	<TD class="plainlabel" >
								<TABLE cellpadding="0" cellspacing="0" border="0">
									<TR>
								  		<TD><SELECT  name="dvkdId">
								  		<option value=""></option>
										<% 
											if(dvkdlist!=null)
											{
												try{											
													while (dvkdlist.next()){%>													
														<%	if(dvkdlist.getString("pk_seq").equals(nhBean.getDvkdId())){ %>											
																<option value= <%=dvkdlist.getString("pk_seq")%> selected><%= dvkdlist.getString("donvikinhdoanh") %></option>															
															<%}else{%>
																<option value= <%=dvkdlist.getString("pk_seq")%> ><%= dvkdlist.getString("donvikinhdoanh") %></option>																																		
															<%	}
															}
																
														}catch(java.sql.SQLException e){}												
											}
													
										%>														                                           
                                      			</SELECT>
                                   			</TD>
										</TR>
									</TABLE>									
								</TD>

						</TR>
						<%-- <TR>
							<TD width="20%" class="plainlabel">Diễn giải<FONT class="erroralert"></FONT></TD>
							<TD width="80%" class="plainlabel"><INPUT name="diengiai" value="" maxlength="200" type="text" style="width: 300px" value=<%=nhBean.getDiengiai() %>/></TD>
						</TR> --%>
						
						<TR>
						  	<TD class="plainlabel">
						  	
						  	<input name="trangthai" type="checkbox" value="1" checked >
							      <%=ChuyenNgu.get("Hoạt động",nnId) %></TD>
							<TD class="plainlabel">&nbsp;</TD>
						</TR>

								
				</TABLE>

				</FIELDSET>
			</TD>
		</TR>
	</TABLE>
	</TD></TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<% if(dvkdlist != null)
		dvkdlist.close(); 
	if(nganhhanglist != null)
		nganhhanglist.close();
	
	nhBean.DBClose();
}

%>

