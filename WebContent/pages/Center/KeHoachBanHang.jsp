<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.beans.kehoachbanhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen"); 
	String trangthai="";
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IKehoachbanhangList obj = (IKehoachbanhangList)session.getAttribute("obj"); %>
<% List<IKehoachbanhang> tbhlist = (List<IKehoachbanhang>)obj.getTbhList(); %>
<% ResultSet ddkd = (ResultSet)obj.getDdkd(); 

	int[] quyen = new  int[6];
	quyen = util.Getquyen("KehoachbanhangSvl","", userId);

%>

<% obj.setNextSplittings(); %>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("KehoachbanhangSvl","",nnId);	
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
 	   		
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
    document.tbhForm.tbhTen.value = "";      
    document.tbhForm.ddkdTen.selectedIndex = 0;
    submitform();
}

function submitform()
{
	document.tbhForm.action.value= 'search';
	document.forms['tbhForm'].submit();
}

function Excel()
{
	document.tbhForm.action.value= 'excel';
	document.forms['tbhForm'].submit();
}

function newform()
{
	document.tbhForm.action.value= 'new';
	document.forms['tbhForm'].submit();
}
function thongbao()
{
	if(document.getElementById("msg").value != '')
		alert(document.getElementById("msg").value);
	document.getElementById("msg").value = '';
}
</SCRIPT>

	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
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

<form name="tbhForm" method="post" action="../../KehoachbanhangSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>

<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
		<script type="text/javascript">
	thongbao();
</script>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							<TD align="left" colspan="2" class="tbnavigation"><%=" "+url %>
   							<TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng <%= userTen %>&nbsp;&nbsp;</TD>
						  </tr>
						</table>
		  		</TR>
			</TABLE>

			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Ti??u ch?? t??m ki???m&nbsp;</LEGEND>
						<TABLE  width="100%" cellspacing="0" cellpadding = "6">
							<TR>
								<TD width="19%" class="plainlabel"><%=ChuyenNgu.get("K??? ho???ch b??n h??ng",nnId) %> </TD>
								<TD width="81%" valign="middle" class="plainlabel">
									<TABLE cellpadding="0" cellspacing="0">
										<TR>
											<TD colspan="1">
												<input name="tbhTen" type="text" value="<%= obj.getTuyenbh() %>" size="40" onChange = "submitform();">
											</TD>											
										</TR>
									</TABLE>	  
							  </TD>
							</TR>
							<TR>
								<TD class="plainlabel"><%=ChuyenNgu.get("Nh??n vi??n b??n h??ng",nnId) %> </TD>
								<TD class="plainlabel">
									<SELECT name="ddkdTen" onChange = "submitform();" >
									  <option value=""></option>
										  <% 
										  try{ while(ddkd.next()){ 
										    	if(ddkd.getString("ddkdId").equals(obj.getDdkdId())){ %>
										      		<option value='<%=ddkd.getString("ddkdId") %>' selected><%=ddkd.getString("ddkdTen") %></option>
										      	<%}else{ %>
										     		<option value='<%=ddkd.getString("ddkdId") %>'><%=ddkd.getString("ddkdTen") %></option>
										     	<%}}}catch(java.sql.SQLException e){}
										   %>
								    </SELECT>
							    </TD>
							    
									
							</TR>
							
						
							<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Tr???ng th??i",nnId) %> </TD>
									<TD class="plainlabel">
									<% 
									String[]	tt = new String[]
														{"","Ch??a ch???t" ,"???? ch???t"};
											String[]			idTrangThai = new String[]
														{"","0", "1"};
												%>
										<select name="trangthai" onChange="submitform();">
												 <% for( int i=0;i<tt.length;i++) { 
								    			if(idTrangThai[i].equals(obj.getTrangthai()  )   ){ %>
								      				<option value='<%=idTrangThai[i]%>' selected><%=tt[i] %></option>
								      		 	<%}else{ %>
								     				<option value='<%=idTrangThai[i]%>'><%=tt[i] %></option>
								     			<%} 
								      		 }
								       	%>
											
									          </select>
									</TD>
									</TR>
							
							<TR>
							    <TD class="plainlabel"  >
							    
                               <a class="button2" href="javascript:clearform()">
							   	<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nh???p l???i",nnId) %></a>
                                </TD>
                                	<TD  class="plainlabel" colspan= "4">
									<a   class="button2" href="javascript:Excel()" >
										<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xu???t Excel",nnId) %> </a>
											&nbsp;&nbsp;&nbsp;&nbsp;
									</TD>
							</TR>
						</TABLE>
						</FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			<TABLE width = "100%" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="100%">
						<FIELDSET>
						<LEGEND class="legendtitle">&nbsp;K??? ho???ch b??n h??ng &nbsp;&nbsp;&nbsp;
							<%if(quyen[0]!= 0){ %>
							<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("T???o m???i",nnId) %> </a> 
    						<%} %>                           
						</LEGEND>
						<TABLE class="" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="98%">
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="2">
										<TR class="tbheader">
											<TH width=""><%=ChuyenNgu.get("M?? K??? Ho???ch",nnId) %> </TH>
											<TH width=""><%=ChuyenNgu.get("K??? ho???ch b??n h??ng",nnId) %> </TH>
											<TH width=""><%=ChuyenNgu.get("Nh??n vi??n b??n h??ng",nnId) %></TH>
											<TH width=""><%=ChuyenNgu.get("Ng??y t???o",nnId) %></TH>
											<TH width=""><%=ChuyenNgu.get("Ng?????i t???o",nnId) %></TH>
											<TH width=""><%=ChuyenNgu.get("Ng??y s???a",nnId) %> </TH>
											<TH width=""><%=ChuyenNgu.get("Ng?????i s???a",nnId) %> </TH>
											<TH width=""><%=ChuyenNgu.get("Tr???ng th??i",nnId) %> </TH>
											<TH width=""><%=ChuyenNgu.get("T??c v???",nnId) %></TH>
										</TR>
	
								<% 
									IKehoachbanhang tbh = null;
									int size = tbhlist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										tbh = tbhlist.get(m);
										
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
										
										<%
											if(tbh.getTrangthai().equals("1"))
											{
												trangthai="???? ch???t";
												
											}
											else
											{
												trangthai="Ch??a ch???t";
											}
										%>
										<TD align="left"><div align="left"><%= tbh.getId() %></div></TD> 
												<TD align="left"><div align="left"><%= tbh.getDiengiai() %></div></TD>                                   
												<TD><div align="center"><%= tbh.getDdkd() %></div></TD>
												<TD align="center"><%= tbh.getNgaytao() %></TD>
												<TD align="center"><%= tbh.getNguoitao() %></TD>
												<TD align="center"><%= tbh.getNgaysua() %></TD>
												<TD align="center"><%= tbh.getNguoisua() %></TD>
												<TD align="center"><%= trangthai %></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR>
													<%  if(!tbh.getTrangthai().trim().equals("0")){ %>
													<TD>
                                              		   <%if(quyen[3]!=0){ %>
                                                        <A href = "../../KehoachbanhangUpdateSvl?userId=<%=userId%>&display=<%= tbh.getId() %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" title="Hi???n th???" border = 0></A>
                                                    	<%} %>
                                                     </TD>
													<%} %>
             										 <%
             										System.out.println("111111"+tbh.getTrangthai());
                                                    if(tbh.getTrangthai().trim().equals("0")){ %>
													<td>
                                                    	<%if(quyen[2]!= 0){ %>
														<A href = "../../KehoachbanhangUpdateSvl?userId=<%=userId%>&update=<%= tbh.getId() %>"><img src="../images/Edit20.png" alt="Cap nhat" title="C???p nh???t" width="20" height="20" longdesc="Cap nhat" border = 0></A>
														<%} %>
                                                    	
                                                    </td>
														<TD>&nbsp;
														<%if(quyen[0]!= 0){ %>
															<A href="../../KehoachbanhangSvl?userId=<%=userId%>&chot=<%= tbh.getId() %>&ddkdId=<%=tbh.getDdkdId() %>" title="Ch???t"><img src="../images/Chot.png" alt="Ch???t" title="Ch???t" width="20" height="20" longdesc="Ch???t" border = 0></A>
														<%} %>
														</TD>
													<TD>&nbsp;
														<%if(quyen[1]!= 0){ %>
													    <A href = "../../KehoachbanhangSvl?userId=<%=userId%>&delete=<%= tbh.getId() %>"><img src="../images/Delete20.png" alt="Xoa" title="X??a" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('B???n mu???n x??a k??? ho???ch b??n h??ng n??y?')) return false;"></A>
														<%} %>
														
													
												   <%} else {%>
												     <TD>
                                                     	<%if(tbh.getTrangthai().trim().equals("1") && quyen[4]!=0) {%>
                                                        <A href = "../../KehoachbanhangSvl?userId=<%=userId%>&unchot=<%=tbh.getId()%>"><img src="../images/unChot.png" alt="Hien thi" width="20" height="20" title="Hi???n th???" border = 0 onclick="if(!confirm('B???n mu???n m??? ch???t k??? ho???ch b??n h??ng n??y?')) return false;"></A>
                                                    	<%} %>
                                                     </TD>
												   <%} %>
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }%>
							
<tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('tbhForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('tbhForm', eval(tbhForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
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
													<a href="javascript:View('tbhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('tbhForm', eval(tbhForm.nxtApprSplitting.value) + 1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('tbhForm', -1, 'view')"> &nbsp;
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
			</TBODY>
		</TABLE>

	</TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<% 

	try{
	
		if(ddkd != null){ ddkd.close(); ddkd = null;}
		if(tbhlist!=null){ tbhlist.clear(); tbhlist = null;}
		
		if(obj != null){
			obj.DBclose();
			obj = null;}	
		
		session.setAttribute("obj",null);
	}
	catch (Exception e) {}
	
%>
<%}%>