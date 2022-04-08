<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.khuvuc.IKhuvuc" %>
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

<% IKhuvuc kvBean = (IKhuvuc)session.getAttribute("kvBean"); %>
<% ResultSet vungmien = (ResultSet)kvBean.getVungMienlist(); %>
<% ResultSet asms = (ResultSet)kvBean.getAsms(); %>
<% ResultSet TTRs = (ResultSet)kvBean.getTtRs(); %>
<% ResultSet QHRs = (ResultSet)kvBean.getQHRs(); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("KhuvucSvl","",nnId);	
	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>	
<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>
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
 jQuery(document).ready(function()
		 {
		 	$("select:not(.notuseselect2)").chosen();     
		 	
		 }); 
</SCRIPT>
<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>

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

<form name='kvForm' method="post" action="../../KhuvucUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="action" value='1'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
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
							   		<%= " " + url %> > Tạo mới </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD> 
						    </tr>
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
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
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu</LEGEND>
				
		    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= kvBean.getMessage() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>
				
				  	<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin khu vực
								</LEGEND>
								<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
								<TR>
									  <TD width="15%" class="plainlabel" >Mã<FONT class="erroralert"></FONT></TD>
									  <TD  class="plainlabel" ><INPUT name="Ma" 
									  	type="text" value='<%= kvBean.getMa() %>' style="width:300px"></TD>
								  </TR>
									<TR>
									  <TD width="15%" class="plainlabel required" ><%=ChuyenNgu.get("Khu vực",nnId) %> <FONT class="erroralert"></FONT></TD>
									  <TD  class="plainlabel" ><INPUT name="khuvuc"
										type="text" value='<%= kvBean.getTen() %>' style="width:300px"></TD>
								  </TR>
									<TR>
									  <TD class="plainlabel required" ><%=ChuyenNgu.get("Diễn giải",nnId) %><FONT class="erroralert"></FONT></TD>
									  <TD class="plainlabel" ><INPUT name="diengiai"
										type="text" value='<%= kvBean.getDiengiai() %>' style="width:300px"></TD>
								  </TR>
								  <TR>
									  <TD class="plainlabel required" ><%=ChuyenNgu.get("Vùng miền",nnId) %><FONT class="erroralert"></FONT></TD>
									  <TD class="plainlabel" ><SELECT name="vungmien">
								      	<option value=''></option>
								        <% try{ while(vungmien.next()){ 
								    	if(vungmien.getString("vmId").equals(kvBean.getVmId())){ %>
								      		<option value='<%= vungmien.getString("vmId") %>' selected> <%= vungmien.getString("vmTen") %></option>
								      	<%}else{ %>
								     		<option value='<%= vungmien.getString("vmId") %>'> <%= vungmien.getString("vmTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>
								     	
								      </SELECT></TD>
								  </TR>
								   <TR>
								  <TD class="plainlabel"><%=ChuyenNgu.get("Quản lý khu vực",nnId) %></TD>
								  <TD class="plainlabel"><SELECT name="asm">
									<%  try{
								  		while(asms.next()){								  			
								  			if (asms.getString("pk_seq").equals(kvBean.getAsm())){ %>								  			
								  				<option value="<%= asms.getString("pk_seq")%>" selected><%= asms.getString("ten")%></option>
								  		  <%}else{ %>		
								  		  		<option value="<%= asms.getString("pk_seq")%>" ><%= asms.getString("ten")%></option>
								  	<%		}
								  		}
								  	}catch (java.sql.SQLException e){} %>
                                  </SELECT></TD>
							  	</TR>
									<TR>
									  <TD width="15%" class="plainlabel" ><label>
										<%  if (kvBean.getTrangthai().equals("1")){%>
										  	<input name="trangthai" type="checkbox" value ="1" checked>
										<%} else {%>
											<input name="trangthai" type="checkbox" value ="0">
										<%} %>
									    <%=ChuyenNgu.get("Hoạt động",nnId) %></label></TD>
										<TD  class="plainlabel" >&nbsp;</TD>
								  </TR>
								  <TR>
								<TD class="plainlabel required"> <%=ChuyenNgu.get("Tỉnh thành",nnId) %></TD>
								<TD class="plainlabel" colspan = 5>
								<select name="ttId"
									id="ttId" class="select2" multiple="multiple"
									style="width: 200px;" onchange=  "submitform();">
										<option value=""></option>
										<% if(TTRs != null) { 
								while(TTRs.next())
								{
									if (kvBean.getTtId() .indexOf(TTRs.getString("pk_seq")) >=0) {%>
										<option value="<%= TTRs.getString("pk_seq") %>"
											selected="selected"><%= TTRs.getString("TEN") %></option>
										<% }
									else { %>
										<option value="<%= TTRs.getString("pk_seq") %>"><%= TTRs.getString("TEN") %></option>
										<% }
								}
								TTRs.close();
							} %>
								</select>
								
							</TR>
							<%if(kvBean.getTtId().indexOf("100049") >= 0 || kvBean.getTtId().indexOf("100000") >= 0) {%>
							<TR>
								
						  
						  	<TD class="plainlabel"><%=ChuyenNgu.get("Quận huyện",nnId) %></TD>
						  	<Td class="plainlabel">
						  	<select name="QhId"
									id="QhId" class="select2" multiple="multiple"
									style="width: 200px;" >
										<option value=""></option>
						  	<% if(QHRs != null) { 
						  		
								while(QHRs.next())
								{
									if (kvBean.getQhId() .indexOf(QHRs.getString("pk_seq")) >=0) {%>
										<option value="<%= QHRs.getString("pk_seq") %>"
											selected="selected"><%= QHRs.getString("TEN") %></option>
										<% }
									else { %>
										<option value="<%= QHRs.getString("pk_seq") %>"><%= QHRs.getString("TEN") %></option>
										<% }
								}
								QHRs.close();
							} %>
							</select>
						  </Td>
						  </TR>
						  <%} %>
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
<%}%>