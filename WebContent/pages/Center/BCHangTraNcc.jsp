<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.Router.imp.Router"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.beans.Router.IDRouter" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	String loi=(String)session.getAttribute("loi");
	String tungay=(String)session.getAttribute("tungay");
	String denngay=(String)session.getAttribute("denngay");
	Utility util = (Utility) session.getAttribute("util");
	IDRouter obj = (IDRouter)session.getAttribute("obj");
	ResultSet khuvuc = (ResultSet)obj.getkhuvuc();
	ResultSet vung = (ResultSet)obj.getvung();
	ResultSet npp = (ResultSet)obj.getnpp();
	ResultSet ddkd = (ResultSet)obj.getddkd();
	ResultSet kenh = (ResultSet)obj.getKenh();
	ResultSet danhsach = (ResultSet)obj.getdanhsach();
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
   
<%
	int[] quyen = new  int[6];
	quyen = util.Getquyen("Routereport","", userId);
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("Routereport","",nnId);	
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />

<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>
<SCRIPT language="javascript" type="text/javascript">

function khuvucChanging(){
	
	document.forms['rpForm'].action.value="khuvucChanged";
}

function submitform()
{
	document.forms['rpForm'].action.value="thuchien";
	document.forms['rpForm'].submit();
}
function exportToExcel()
{
	//alter('M???i b???n ch???n nh?? ph??n ph???i!');
	if(document.forms['rpForm'].nppId.value=="" && !document.forms['rpForm'].view.value=="NPP" ){
		document.forms['rpForm'].nppId.focus();
		alert('M???i b???n ch???n nh?? ph??n ph???i tr?????c ????!');
		
		return;
	}
	
	document.forms['rpForm'].action.value="export";
	document.forms['rpForm'].submit();
}
function submitCBO(){
	document.forms['rpForm'].action.value="";
	document.forms['rpForm'].submit();
}
</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2(); 
    	});
    </script>	


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

<form name="rpForm" method="post" action="../../BCTraHangNcc">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="action" value='1'>
<input type="hidden" name="view" value='<%= obj.getView() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">Qu???n l?? mua h??ng NPP > B??o c??o tr??? h??ng ncc</TD>
   
						   <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Ch??o m???ng b???n",nnId) %> <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle"><%=ChuyenNgu.get("B??o l???i nh???p li???u",nnId) %> </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=loi%></textarea>
						</FIELDSET>
				   </TD>
				</tr>	
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle"><%=ChuyenNgu.get("Ti??u ch?? xu???t b??o c??o",nnId) %></LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
							<TR>
							<TD class="plainlabel" ><%=ChuyenNgu.get("T??? ng??y",nnId) %> </TD>
                    <TD  class="plainlabel" >
                    	<input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>"/></TD>
                    	<TD class="plainlabel" ><%=ChuyenNgu.get("?????n ng??y",nnId) %> </TD>
                    <TD  class="plainlabel" >
                    	<input type="text" class="days"   name="denngay" value="<%= obj.getDenngay() %>"/></TD>
							</TR>
								<TR> 
									<TD class="plainlabel"><%=ChuyenNgu.get("V??ng/Mi???n",nnId) %></TD>
									<TD class="plainlabel"  colspan="3">
										<select name="vungId" id="vungId" onchange="submitCBO();" style ="width:200px">
											<option value="" selected>All</option>
											<%if (vung != null)
													while (vung.next()) {
														if (vung.getString("pk_seq").equals(obj.getvungId())) {%>
													<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
												<%} else {%>
													<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
								</TR>
								
								<TR>
								<tr>
								
								<TD width="19%" class="plainlabel" ><%=ChuyenNgu.get("Khu v???c",nnId) %> </TD>
								<TD class="plainlabel"  colspan="3">
								<TABLE cellpadding="0" cellspacing="0">
								<TR><TD>
								<select name="khuvucId" onchange="submitCBO();" >
                                 <option value ="" > </option>  
                               <%
                               
                               while(khuvuc.next())
                               {
                               if(khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {
                            	%><option value ="<%=khuvuc.getString("pk_seq") %>" selected> <%=khuvuc.getString("ten") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=khuvuc.getString("pk_seq") %>"> <%=khuvuc.getString("ten") %></option>
                              <%}} %>                             </select>		   										</TD>
                                    		</TR>
								</TABLE>
								</TD>
								
								</tr>
								
					<% if(!obj.getView().equals("")) { %>			
								<tr>
								  	<TD width="19%" class="plainlabel" ><%=ChuyenNgu.get("Nh?? ph??n ph???i",nnId) %></TD>
								  	<TD class="plainlabel"  colspan="3">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
								<select name="nppId" onchange="submitCBO();" >
                                 <option value =""> </option>  
                               <%
                               if(npp != null)
                               while(npp.next())
                               {
                               if(npp.getString("pk_seq").equals(obj.getnppId())) {
                            	%><option value ="<%=npp.getString("pk_seq") %>" selected> <%=npp.getString("ten") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=npp.getString("pk_seq") %>"> <%=npp.getString("ten") %></option>
                              <%}} %>                             </select>		   										</TD>
                                    		</TR>
								</TABLE>									
								</TD>
								
								</tr>
					<% } else { %>
						<input name="nppId" type="hidden" value=<%=obj.getnppId()%> />
					<% } %>

								<tr>
									<td width="19%" class="plainlabel" ><%=ChuyenNgu.get("Tr???ng th??i ????n",nnId) %></td>
									<td class="plainlabel" colspan="3">
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td>
													<select name="status"  >
															<option value=""></option>																												
														<%if(obj.getStatus().equals("1")) {%>
															<option selected="selected" value="1"><%=ChuyenNgu.get("Ch??? duy???t",nnId) %></option>
															<option value="0"><%=ChuyenNgu.get("Ch??a ch???t",nnId) %></option>
															<option value="2"><%=ChuyenNgu.get("Ho??n t???t",nnId) %></option>
															<option value="3"><%=ChuyenNgu.get("???? h???y",nnId) %></option>															
														<%}else if(obj.getStatus().equals("0")) {%>
															<option  value="1"><%=ChuyenNgu.get("Ch??? duy???t",nnId) %></option>
															<option selected="selected" value="0"><%=ChuyenNgu.get("Ch??a ch???t",nnId) %></option>
															<option value="2"><%=ChuyenNgu.get("Ho??n t???t",nnId) %></option>
															<option value="3"><%=ChuyenNgu.get("???? h???y",nnId) %></option>	
														<%}else if(obj.getStatus().equals("2")) {%>
														<option  value="1"><%=ChuyenNgu.get("Ch??? duy???t",nnId) %></option>
															<option value="0"><%=ChuyenNgu.get("Ch??a ch???t",nnId) %></option>
															<option  selected="selected" value="2"><%=ChuyenNgu.get("Ho??n t???t",nnId) %></option>
															<option value="3"><%=ChuyenNgu.get("???? h???y",nnId) %></option>	
														<%}else if(obj.getStatus().equals("3")) {%>
														<option  value="1"><%=ChuyenNgu.get("Ch??? duy???t",nnId) %></option>
															<option  value="0"><%=ChuyenNgu.get("Ch??a ch???t",nnId) %></option>
															<option value="2"><%=ChuyenNgu.get("Ho??n t???t",nnId) %></option>
															<option selected="selected" value="3"><%=ChuyenNgu.get("???? h???y",nnId) %></option>	
															<%}else{ %>
															<option value="1"><%=ChuyenNgu.get("Ch??? duy???t",nnId) %></option>
															<option value="0"><%=ChuyenNgu.get("Ch??a ch???t",nnId) %></option>
															<option value="2"><%=ChuyenNgu.get("Ho??n t???t",nnId) %></option>
															<option value="3"><%=ChuyenNgu.get("???? h???y",nnId) %></option>
																<%} %>
													</select>
												</td>
											</tr>
										</table>
									
									</td>
								</tr>
								
							  
							    <TR>
							    <TR>
										<TD width="19%" class="plainlabel" ><%=ChuyenNgu.get("K??nh b??n h??ng",nnId) %></TD>
								  	<TD class="plainlabel" colspan="3" >
										<TABLE cellpadding="0" cellspacing="0">
								<TR><TD>
								<select name="kbhId"  >
                                 <option value =""> </option>  
                               <%
                               while(kenh.next())
                               {
                               if(kenh.getString("pk_seq").equals(obj.getkenhId())) {
                            	%><option value ="<%=kenh.getString("pk_seq") %>" selected> <%=kenh.getString("ten") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=kenh.getString("pk_seq") %>"> <%=kenh.getString("ten") %></option>
                              <%}} %>             
                               </select>		   										
                               </TD>
                               </TR>
                               </TABLE>
                               </TD>
                               </TR>
                               <TR>                               
                               </TR>
                               <TR>
									<td class="plainlabel" colspan="4"><a class="button"
										href="javascript:exportToExcel()"> <img style="top: -4px;"
											src="../images/button.png" alt=""><%=ChuyenNgu.get("Xu???t ra excel",nnId) %> 
									</a></td>
								</TR>
                               </TABLE>
                               <!-- <a class="button3" href="javascript:exportToExcel()">Xu???t ra excel </a> -->
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
<script type="text/javascript">
		$("select:not(.notuseselect2)").css({
			"width": "250px", 
			//"height": "200px"
		});
	</script>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<%
	try
	{
		if(!(danhsach == null)){ danhsach.close(); danhsach = null ; }
		if(ddkd != null){ ddkd.close(); ddkd = null ; }
		if(npp != null){ npp.close(); npp = null ; }
		if(kenh != null){ kenh.close(); kenh = null ; }
		if(vung!=null){ vung.close(); vung = null ; }
		if(khuvuc!=null){ khuvuc.close(); khuvuc = null ; }
		
		if(obj != null){obj.DBclose();
		obj = null;}
		session.setAttribute("obj", null);
	}catch(java.sql.SQLException e){}
	}
%>