<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.chungloai.IChungloai" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IChungloai cloaiBean = (IChungloai)session.getAttribute("cloaiBean"); 
   ResultSet nhlist = (ResultSet)cloaiBean.getNhList();
   ResultSet nh_cllist = (ResultSet)cloaiBean.getNh_clList();
   Hashtable nhanhangSelected = (Hashtable)cloaiBean.getNhanhangSelected(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">

</SCRIPT>
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["chungloaiForm"].submit();
}

function checkedAll(chk) {

	for(i=0; i<chk.length; i++){
	 	if(document.chungloaiForm.chon.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
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

<form name="chungloaiForm" method="post" action="../../ChungloaiUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="Id" type="hidden" value='<%=cloaiBean.getId() %>' size="30">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		&nbsp;D??? li???u n???n &gt; S???n ph???m &gt; Ch???ng lo???i &gt; C???p nh???t</TD>
							   <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%=userTen %>&nbsp;</td> 
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
								    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD>&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
				  	<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">B??o l???i nh???p li???u </LEGEND>
				
			    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1" ><%=cloaiBean.getMessage()%></textarea>
								<%cloaiBean.setMessage(""); %>
								</FIELDSET>
						   </TD>
					</tr>			
				  	
				  	<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Th??ng tin ch???ng lo???i
								</LEGEND>
								<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
								<TR>
									  <TD width="15%" class="plainlabel" >M??  <FONT class="erroralert"></FONT></TD>
									  <TD  class="plainlabel" ><INPUT name="ma" readonly="readonly" value ='<%=cloaiBean.getMa()%>' size="40"
										type="text"></TD>
								  </TR>
									<TR>
									  <TD width="15%" class="plainlabel required" >Ch???ng lo???i  <FONT class="erroralert"></FONT></TD>
									  <TD  class="plainlabel" ><INPUT readonly="readonly" name="chungloai" value ='<%=cloaiBean.getChungloai()%>' size="40"
										type="text"></TD>
								  </TR>
									<TR>
									  <TD colspan="2" class="plainlabel" ><label>
									  <% if (cloaiBean.getTrangthai().equals("1")){ %>
									    	<input name="trangthai" type="checkbox" value="1" checked>
									  <%}else{ %>
									  		<input name="trangthai" type="checkbox" value="1" >
									  <%} %>
								      Ho???t ?????ng</label></TD>
								    </TR>
								</TABLE>
								</FIELDSET>
							    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD colspan = "6" width="100%"  >
											<FIELDSET>
											<LEGEND class="legendtitle">Ch???n nh??n h??ng
											</LEGEND>
                                            <TABLE width="100%" align="left" >
												<TR>
													<TD width="100%">
														<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
															<TR class="tbheader">
																<TH width="20%">Nh??n h??ng </TH>
																<TH width="10%">Ch???n t???t c???
																	<input type="checkbox" name="chon" onClick="checkedAll(document.chungloaiForm.nhId)">
																</TH>
															</TR>
											<%
												
												int m = 0;
												String ten ;
												String id;
												String lightrow = "tblightrow";
												String darkrow = "tbdarkrow";
												while (nh_cllist.next()){
													ten = nh_cllist.getString("ten");
													id = nh_cllist.getString("pk_seq");
													if (m % 2 != 0) {%>						
														<TR class= <%=lightrow%> >
													<%} else {%>
														<TR class= <%= darkrow%> >
													<%}%>
														<TD align="left" class="textfont"><%= ten %></TD>
  												   		<TD align="center" ><input name="nhId" type="checkbox" value="<%=id%>" checked></TD>
														</TR>
													<%
														m++;
												 }
																					 
												while (nhlist.next()){
													ten = nhlist.getString("ten");
													id = nhlist.getString("pk_seq");

													if (m % 2 != 0) {%>						
														<TR class= <%=lightrow%> >
													<%} else {%>
														<TR class= <%= darkrow%> >
													<%}%>
														<TD align="left" class="textfont"><%= ten %></TD>
  											    		<TD align="center" ><input name="nhId" type="checkbox" value="<%=id%>" ></TD>
														</TR>
													<%
														m++;
												}
											
												%>

											</TABLE>
										</TD>
									</TR>
								</TABLE>
							</FIELDSET>								
						</TD>
					</TR>
				</TABLE>
							
			</TD>	
		</TR>
	</TABLE>
</TD>
</TR>
 
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<% if(nhlist != null) nhlist.close(); %>
<% if(nh_cllist != null) nh_cllist.close(); %>

<%
	cloaiBean.DBClose();
	}%>