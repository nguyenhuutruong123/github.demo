<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.diaban.IDiaban" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IDiaban kvBean = (IDiaban)session.getAttribute("kvBean"); %>
<% ResultSet khuvucRs = (ResultSet)kvBean.getKhuvucRs(); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 	nnId = "vi"; } 
String url = util.getChuyenNguUrl("DiabanSvl","",nnId); 
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>OPV - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
  function submitform()
 {
     document.forms['kvForm'].submit();
 }
 function saveform()
 {
 	 document.forms['kvForm'].action.value= 'save';
     document.forms['kvForm'].submit();
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

<form name='kvForm' method="post" action="../../DiabanUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="action" value='1'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<INPUT name="id" type="hidden" value='<%= kvBean.getId() %>' size="30">
<input type="hidden" name="ngonnguId" value='<%=nnId%>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   	<%if(kvBean.getId() != null && kvBean.getId().trim().length() > 0){ %>
							   		<%=url %> > <%=ChuyenNgu.get("Cập nhật",nnId) %> </TD>
							   		<%} else { %>
							   		<%=url %> > <%=ChuyenNgu.get("Tạo mới",nnId) %> </TD>
							   		<%} %>
							   <TD colspan="2" align="right" class="tbnavigation"> <%=ChuyenNgu.get("Chào mừng",nnId) %> <%=userTen %>&nbsp;  </TD> 
						    </tr>
   
						   	<tr>
						   		<TD align="left" height="5" colspan="4" class=""></td>
   
  							</tr>
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../DiabanSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle"><%= ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %> </LEGEND>
				
		    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= kvBean.getMessage() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>
				
				  	<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle"><%=ChuyenNgu.get("Thông tin Địa bàn",nnId)%>
								</LEGEND>
								<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
									<TR>
									  <TD width="15%" class="plainlabel" ><%=ChuyenNgu.get("Địa bàn",nnId)%><FONT class="erroralert">*</FONT></TD>
									  <TD  class="plainlabel" ><INPUT name="diaban"
										type="text" value='<%= kvBean.getTen() %>' style="width:300px"></TD>
								  </TR>
									<TR>
									  <TD class="plainlabel" ><%=ChuyenNgu.get("Diễn giải",nnId)%><FONT class="erroralert">*</FONT></TD>
									  <TD class="plainlabel" ><INPUT name="diengiai"
										type="text" value='<%= kvBean.getDiengiai() %>' style="width:300px"></TD>
								  </TR>
								  <TR>
									  <TD class="plainlabel" ><%=ChuyenNgu.get("Khu vực",nnId)%><FONT class="erroralert">*</FONT></TD>
									  <TD class="plainlabel" ><SELECT name="khuvuc">
								      	<option value=''></option>
								        <% try{ while(khuvucRs.next()){ 
								    	if(khuvucRs.getString("pk_seq").equals(kvBean.getKhuvucId())){ %>
								      		<option value='<%= khuvucRs.getString("pk_seq") %>' selected> <%= khuvucRs.getString("Ten") %></option>
								      	<%}else{ %>
								     		<option value='<%= khuvucRs.getString("pk_seq") %>'> <%= khuvucRs.getString("Ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>
								     	
								      </SELECT></TD>
								  </TR>
								    
									<TR>
									  <TD width="24%" class="plainlabel" ><label>
										<%  if (kvBean.getTrangthai().equals("Hoat dong")){%>
										  	<input name="trangthai" type="checkbox" value ="1" checked>
										<%} else {%>
											<input name="trangthai" type="checkbox" value ="0">
										<%} %>
									    <%=ChuyenNgu.get("Hoạt động",nnId)%></label></TD>
										<TD width="70%" class="plainlabel" >&nbsp;</TD>
								  </TR>
								</TABLE>
								</FIELDSET>
							</TD>	
						</TR>
					</TABLE>
			</TD>
	</TR>
 
</TABLE>
</form>
</BODY>
</HTML>
<%
	kvBean.closeDB();
}%>