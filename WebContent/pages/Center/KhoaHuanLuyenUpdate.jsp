<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.beans.khoahuanluyen.imp.*" %>
<%@ page import="geso.dms.center.beans.khoahuanluyen.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%
 	IKhoahuanluyen obj =(IKhoahuanluyen)session.getAttribute("khlBean");
	ResultSet nppRs = obj.getNppRs();
	ResultSet gsbhRs = obj.getGsbhRs();
	ResultSet ddkdRs = obj.getNvbhRs();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

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

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["tctsku"].submit();
	}
	
	function save()
	{
	  var thang = document.getElementById("tieude").value;

	  if( tieude == '' )
	  {
		  alert("Vui l??ng nh???p ti??u ????? kh??a hu???n luy???n");
		  return;
	  }
	  
	  document.forms["tctsku"].action.value = "save";
	  document.forms["tctsku"].submit(); 
  }
	
	function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var nppIds = document.getElementsByName("nppIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < nppIds.length; i++)
			 {
				 nppIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < nppIds.length; i++)
			 {
				 nppIds.item(i).checked = false;
			 }
		 }
	 }
	
	function sellectAll_GSBH()
	 {
		 var chkAll = document.getElementById("chkAll_GSBH");
		 var gsbhIds = document.getElementsByName("gsbhIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < gsbhIds.length; i++)
			 {
				 gsbhIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < gsbhIds.length; i++)
			 {
				 gsbhIds.item(i).checked = false;
			 }
		 }
	 }
	
	function sellectAll_DDKD()
	 {
		 var chkAll = document.getElementById("chkAll_DDKD");
		 var ddkdIds = document.getElementsByName("ddkdIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < ddkdIds.length; i++)
			 {
				 ddkdIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < ddkdIds.length; i++)
			 {
				 ddkdIds.item(i).checked = false;
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
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="id" value="<%= obj.getId() %>">
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Qu???n l?? nh??n s??? > Khai b??o > Kh??a hu???n luy???n / K??? n??ng > C???p nh???t</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%= userTen %></TD></tr>
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
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Th??ng b??o </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Th??ng tin ti??u kh??a hu???n luy???n </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
						  	  	<TD class="plainlabel" width="100px">Ti??u ????? <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name=tieude id="tieude" value="<%= obj.getTenkhoa() %>"> 
						  	  	</TD>
						  </TR>
						  <TR valign="top">
						  	  	<TD class="plainlabel">N???i dung</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<textarea rows="2" cols="10" name="diengiai"><%= obj.getDiengiai() %></textarea> 
						  	  	</TD>
						  </TR>
						  <TR>
                             	<TD class="plainlabel" >B???t ?????u t??? </TD>
								<TD class="plainlabel">
									<input class="days" type="text" name="tungay" value="<%= obj.getTungay() %>" size="20" >
								</TD>
                             </TR>
                          <TR>
                             	<TD class="plainlabel" >K???t th??c </TD>
								<TD class="plainlabel">
									<input class="days" type="text" name="denngay" value="<%=obj.getDenngay() %>" size="20" >
								</TD>
                          </TR >
                          <TR>
                             	<TD class="plainlabel" >Nh?? ph??n ph???i </TD>
								<TD class="plainlabel">
									<a href="" id="nppId" rel="subcontentNpp">
	           	 							&nbsp; <img alt="Ch???n nh?? ph??n ph???i" src="../images/vitriluu.png"></a>
	           	 		
		                          <DIV id="subcontentNpp" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
						                             background-color: white; width: 590px; max-height:300px; overflow:auto; padding: 4px;">
						                    <table width="90%" align="center">
						                        <tr>
						                            <th width="100px" style="font-size: 12px">M?? NPP</th>
						                            <th width="350px" style="font-size: 12px">T??n NPP</th>
						                            <th width="100px" align="center" style="font-size: 12px">Ch???n h???t <input type="checkbox" onchange="sellectAll()" id="chkAll" ></th>
						                        </tr>
				                            	<%
					                        		if(nppRs != null)
					                        		{
					                        			while(nppRs.next())
					                        			{  %>
					                        			
					                        			<tr>
					                        				<td><input style="width: 100%" value="<%= nppRs.getString("ma") %>"></td>
					                        				<td><input style="width: 100%" value="<%= nppRs.getString("ten") %>"></td>
					                        				<td align="center">
					                        				<% if(obj.getNppIds().indexOf(nppRs.getString("pk_seq")) >= 0 ){ %>
					                        					<input type="checkbox" name="nppIds" checked="checked" value="<%= nppRs.getString("pk_seq") %>">
					                        				<%}else{ %>
					                        					<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>">
					                        				<%} %>
					                        				</td>
					                        			</tr>
					                        			
					                        		 <% } nppRs.close(); } %>
						                    </table>
						                     <div align="right">
						                     	<label style="color: red" ></label>
						                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                     	<a href="javascript:dropdowncontent.hidediv('subcontentNpp')" onclick="submitform();" >Ho??n t???t</a>
						                     </div>
						            </DIV>         
								</TD>
                          </TR >
						</TABLE>
					<hr />
						
					<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
                    <TR class="tbheader">
                        <TH align="center" width="10%"> M?? GSBH</TH>
                        <TH align="left" width="25%"> T??n GSBH </TH>
                        <TH align="center" width="10%"> ??i???n tho???i</TH>
                        <TH align="left" width="30%"> Email</TH>
                        <TH align="center" width="15%"> ???? ho??n th??nh</TH>
                        <TH align="center" width="10%"> Ch???n h???t <input type="checkbox" onchange="sellectAll_GSBH()" id="chkAll_GSBH" > </TH>
                    </TR>
					
					<%
						if(gsbhRs != null)
						{
							while(gsbhRs.next())
							{
								%>
								<tr>
									<td>
										<input type="text" style="width: 100%" value="<%= gsbhRs.getString("pk_seq") %>"></td>
									<td>
										<input type="text" style="width: 100%" value="<%= gsbhRs.getString("ten") %>"></td>
									<td>
										<input type="text" style="width: 100%" value="<%= gsbhRs.getString("dienthoai") %>"></td>
									<td>
										<input type="text" style="width: 100%" value="<%= gsbhRs.getString("diachi") %>"></td>
									<td align="center">
										<% if(gsbhRs.getString("trangthai").equals("1")){ %>
											<input type="checkbox" name="gsbh_hoanthanhIds" value="<%= gsbhRs.getString("pk_seq") %>" checked="checked">
										<%} else { %>
											<input type="checkbox" name="gsbh_hoanthanhIds" value="<%= gsbhRs.getString("pk_seq") %>" >
										<%} %>
									</td>
									<td align="center">
										<% if(obj.getGsbhIds().indexOf(gsbhRs.getString("pk_seq")) >= 0 ){ %>
											<input type="checkbox" name="gsbhIds" value="<%= gsbhRs.getString("pk_seq") %>" checked="checked">
										<%} else { %>
											<input type="checkbox" name="gsbhIds" value="<%= gsbhRs.getString("pk_seq") %>" >
										<%} %>
									</td>
								</tr>
							<% }
						}
					%>
					
                    <TR>
                        <TD align="center" colspan="10" class="tbfooter">&nbsp;</TD>
                    </TR>
					</TABLE>
					
					<hr />
					<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
                    <TR class="tbheader">
                        <TH align="center" width="10%"> M?? DDKD</TH>
                        <TH align="left" width="25%"> T??n DDKD </TH>
                        <TH align="center" width="10%"> ??i???n tho???i</TH>
                        <TH align="left" width="30%" > Nh?? ph??n ph???i</TH>
                        <TH align="center" width="15%"> ???? ho??n th??nh</TH>
                        <TH align="center" width="10%"> Ch???n h???t <input type="checkbox" onchange="sellectAll_DDKD()" id="chkAll_DDKD" ></TH>
                    </TR>
					
					<%
						if(ddkdRs != null)
						{
							while(ddkdRs.next())
							{
								%>
								<tr>
									<td>
										<input type="text" style="width: 100%" value="<%= ddkdRs.getString("pk_seq") %>"></td>
									<td>
										<input type="text" style="width: 100%" value="<%= ddkdRs.getString("ten") %>"></td>
									<td>
										<input type="text" style="width: 100%" value="<%= ddkdRs.getString("dienthoai") %>"></td>
									<td>
										<input type="text" style="width: 100%" value="<%= ddkdRs.getString("nppTen") %>"></td>
									<td align="center">
										<% if(ddkdRs.getString("trangthai").equals("1")){ %>
											<input type="checkbox" name="ddkd_hoanthanhIds" value="<%= ddkdRs.getString("pk_seq") %>" checked="checked">
										<%} else { %>
											<input type="checkbox" name="ddkd_hoanthanhIds" value="<%= ddkdRs.getString("pk_seq") %>" >
										<%} %>
									</td>
									<td align="center">
										<% if(obj.getNvbhIds().indexOf(ddkdRs.getString("pk_seq")) >= 0 ){ %>
											<input type="checkbox" name="ddkdIds" value="<%= ddkdRs.getString("pk_seq") %>" checked="checked">
										<%} else { %>
											<input type="checkbox" name="ddkdIds" value="<%= ddkdRs.getString("pk_seq") %>" >
										<%} %>
									</td>
								</tr>
							<% }
						}
					%>
					
                    <TR>
                        <TD align="center" colspan="10" class="tbfooter">&nbsp;</TD>
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
<script type="text/javascript">
	dropdowncontent.init('nppId', "right-bottom", 300, "click");
</script>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%}%>
