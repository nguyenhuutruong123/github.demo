<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.ttpp.ITrungtamphanphoi" %>
<%@ page  import = "geso.dms.center.beans.ttpp.ITrungtamphanphoiList" %>
<%@ page  import = "geso.dms.center.beans.nhacungcap.INhacungcap" %>
<%@ page  import = "geso.dms.center.beans.nhacungcap.INhacungcapList" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ 
		int[] quyen = new  int[6];
		quyen = util.Getquyen("TrungtamphanphoiSvl", "", userId);
	%>

<% ITrungtamphanphoiList obj = (ITrungtamphanphoiList)session.getAttribute("obj"); %>
<% ResultSet dvkdlist = obj.getDvkdList(); %>
<% ResultSet ncclist = obj.getNccList(false) ; %>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("TrungtamphanphoiSvl","",nnId);	

String view = obj.getView();
if (view != null && view.equals("TT")) {
	quyen = util.Getquyen("TrungtamphanphoiSvl","&view=TT",userId);
}
else {
	quyen = util.Getquyen("TrungtamphanphoiSvl","",userId);
}
 %>

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

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

<SCRIPT language="JavaScript" type="text/javascript">
function clearform()
{
	document.dvkdForm.dvkd.value = "";
	document.dvkdForm.nccId.selectedIndex = 0;
	document.dvkdForm.tungay.value = "";
	document.dvkdForm.denngay.value = "";
    document.dvkdForm.trangthai.selectedIndex = 2;
    submitform();
}

function submitform()
{
	document.forms['dvkdForm'].action.value= 'search';
	document.forms['dvkdForm'].submit();
}

function newform()
{
	document.forms['dvkdForm'].action.value= 'new';
	document.forms['dvkdForm'].submit();
}
function thongbao()
{var tt = document.forms['dvkdForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['dvkdForm'].msg.value);
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

<form name="dvkdForm" method="post" action="../../TrungtamphanphoiSvl" charset="UTF-8">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							  	<TD align="left" colspan="2" class="tbnavigation">
							  	<%=url %>
							  	</td>
   								<TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%= userTen %>&nbsp; </td> 
   								</tr>
   
						</table>
					</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0">
			  <tr>
				   <TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>				   
							<LEGEND class="legendtitle">Ti??u ch?? t??m ki???m &nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding = "6" >
								<TR>
									<TD width="19%" class="plainlabel" ><%=ChuyenNgu.get("Trung t??m ph??n ph???i",nnId) %></TD>
								  <TD width="81%" class="plainlabel" ><input name="dvkd" type="text" size="40" value="<%=obj.getDvkd() %>" onChange="submitform();"></TD>
								</TR>
								<TR>
									<TD class="plainlabel" ><%=ChuyenNgu.get("Nh?? cung c???p",nnId) %></TD>
									<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0" border="0" >
										<TR>
										  	<TD><SELECT  name="nccId" onChange="submitform();">
										<% 
			
											String nccId = (String) obj.getNccId(); %>
											
											<option value="0" ></option>
										<% 	
											while (ncclist.next()){
												
												if (ncclist.getString("pk_seq").equals(nccId)){%>
												
													<option value= '<%=ncclist.getString("pk_seq")%>' selected><%= ncclist.getString("ten") %></option>
												<%}else{ %>
													<option value= '<%=ncclist.getString("pk_seq")%>' ><%= ncclist.getString("ten") %></option>										
												<%
												  }
																	
											}%>
												                                           
                                              </SELECT>
                                         	</TD>
										</TR>
										</TABLE>									
									</TD>
								</TR>
								<TR>
								  	<TD class="plainlabel" >T??? ng??y </TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
										<TR><TD>
											<input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="20" onchange=submitform();>
										</TD>
                                   	</TR>
										</TABLE>
									</TD>
								<TR>
								<TR>
									<TD class="plainlabel" ><%=ChuyenNgu.get("?????n ng??y",nnId) %> </TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  			<TR>
							  			<TD>
											<input class="days" type="text" name="denngay" value="<%=obj.getDenngay() %>" size="20" onchange=submitform();>
										</TD>
										
                                     	</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Tr???ng th??i",nnId) %></TD>
									<TD class="plainlabel"><select name="trangthai" onChange="submitform();">
									<% if(obj.getTrangthai().equals("0")){ %>
										<option value="0" selected>Ng??ng ho???t ?????ng</option>
										<option value="1">Ho???t ?????ng</option>
										<option value="2"> </option>
									<%}else{
										if (obj.getTrangthai().equals("1")){%>										
										<option value="0" ><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
										<option value="1" selected><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>										
										<option value="2"> </option>
										<%}else{ %>
										<option value="0" ><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
										<option value="1" ><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>								
										<option value="2" selected> </option>
									<%	} 
										}%>
										
									    </select></TD>
								</TR>
								<TR>
									<TD class="plainlabel" colspan="2">
									<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nh???p l???i",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	
				 </TD>
								</TR>
							</TABLE>
					 </FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			
		   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			  <TR>
			  	   <TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>				   
							<LEGEND class="legendtitle">Trung t??m ph??n ph???i &nbsp;
							<%if(quyen[0]!=0){ %>
							<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("T???o m???i",nnId) %> </a>                            
							<%} %>
							
							</LEGEND>
							<TABLE width="100%" cellspacing="0" cellpadding="0">
								<TR>
								<TD width="98%">
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
										<TR class="tbheader">
											<TH width="15%"><%=ChuyenNgu.get("Trung t??m ph??n ph???i",nnId) %> </TH>
											<TH width="22%"><%=ChuyenNgu.get("Nh?? cung c???p",nnId) %></TH>
											<TH width="8%"><%=ChuyenNgu.get("Tr???ng th??i",nnId) %> </TH>
											<TH width="8%"><%=ChuyenNgu.get("Ng??y t???o",nnId) %></TH>
											<TH width="16%"><%=ChuyenNgu.get("Ng?????i t???o",nnId) %> </TH>
											<TH width="8%"><%=ChuyenNgu.get("Ng??y s???a",nnId) %> </TH>
											<TH width="15%"><%=ChuyenNgu.get("Ng?????i s???a",nnId) %></TH>
											<TH width="8%"><%=ChuyenNgu.get("T??c v???",nnId) %></TH>
										</TR>
									
								<% 
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (dvkdlist.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>

											<TD align="left"><%=dvkdlist.getString("ttpp") %></TD>                                   
											<TD><%=dvkdlist.getString("nhacungcap") %></TD>
											<% if (dvkdlist.getString("trangthai").equals("1")){ %>
												<TD align="left"><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></TD>
											<%}else{%>
												<TD align="left"><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></TD>
											<%}%>
											
											<TD align="center"><%=dvkdlist.getString("ngaytao")%></TD>
											<TD align="center"><%=dvkdlist.getString("nguoitao") %></TD>
											<TD align="center"><%=dvkdlist.getString("ngaysua") %></TD>
											<TD align="center"><%=dvkdlist.getString("nguoisua") %></TD>
									
											<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													<%if(quyen[2]!=0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "TrungtamphanphoiSvl?userId="+userId+"&update="+dvkdlist.getString("pk_seq")+"")%>"><img src="../images/Edit20.png" alt="Chinh sua" title="C???p nh???t" width="20" height="20" longdesc="Chinh sua" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;&nbsp;</TD>
													<TD>
													<%if(quyen[1]!=0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "TrungtamphanphoiSvl?userId="+userId+"&delete="+dvkdlist.getString("pk_seq")+"")%>"><img src="../images/Delete20.png" alt="Xoa" title="X??a" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('B???n c?? mu???n x??a trung t??m ph??n ph???i n??y?')) return false;"></A>
														<%} %></TD>
													</TR>
												</TABLE>											
											</TD>
										</TR>
									<% 	m++;
									}%>
										<TR>
											<TD align="center" colspan="11" class="tbfooter">&nbsp;	</TD>
										</TR>
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
</FORM>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<%
	if (ncclist != null){ ncclist.close(); ncclist = null ;}
	if (dvkdlist != null){ dvkdlist.close(); dvkdlist =  null; }
	obj.DBClose();
	obj = null;
	session.setAttribute("obj", null); 
}
%>