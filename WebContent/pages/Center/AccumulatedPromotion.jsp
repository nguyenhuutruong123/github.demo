<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId =  (String)session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	String baoloi = (String) session.getAttribute("baoloi");
	
	ResultSet ctkmIds = (ResultSet)session.getAttribute("ctkmIds");
	String ctkmId = (String)session.getAttribute("ctkmTL"); 
		
	Utility util = (Utility) session.getAttribute("util"); 
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
	
<%
	int[] quyen = new  int[6];
	quyen = util.Getquyen("AccumulatedPromotion","", userId);
%>	
<% String nnId = (String)session.getAttribute("nnId"); %>
<% if(nnId == null) {
 nnId = "vi";  
 }
 String url = util.getChuyenNguUrl("AccumulatedPromotion","" ,nnId);
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
<SCRIPT language="javascript" type="text/javascript">

function submitform()
{
	if(document.getElementById("ctkmId").value == '' )
	{
		alert('Vui lòng chọn chương trình khuyến mại.');
		return;
	}
	document.forms['rpForm'].dataerror.value="";
	document.forms['rpForm'].submit();
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

<form name="rpForm" method="post" action="../../AccumulatedPromotion">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation"><%=url %></TD>
   
						   <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId) %> <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle"><%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %> </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=baoloi%></textarea>
						</FIELDSET>
				   </TD>
				</tr>	
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle"><%=ChuyenNgu.get("Chọn chương trình",nnId) %></LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
				             	<TR>
                					<TD class="plainlabel"  width="23%"><%=ChuyenNgu.get("Chương trình",nnId) %> </TD>
                					<TD class="plainlabel" >
                    					<select name="ctkmId" id="ctkmId" >
                       						<option value = ''> </option>
                            				<% if(ctkmIds != null){
					  								try{ 
					  									while(ctkmIds.next()){ 
		    												if(ctkmIds.getString("pk_seq").equals(ctkmId)){ %>
		      													<option value='<%=ctkmIds.getString("pk_seq")%>' selected><%=ctkmIds.getString("scheme") %></option>
		      												<%}else{ %>
		      													<option value='<%=ctkmIds.getString("pk_seq")%>'><%=ctkmIds.getString("scheme") %></option>
		      												<%}
		    												} 
					  									ctkmIds.close(); 
		    										}catch(java.sql.SQLException e){} 
		    									}%>
                     					</select>
                					</TD>
           					 	</TR>	

							    <TR>
									<TD colspan="2" class="plainlabel">
									<a class="button2" href="javascript:submitform()" >
										<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Tạo báo cáo",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;                                    
									</TD>
								</TR>
							</TABLE>
							</FIELDSET>						</TD>
						</TR>
					</TABLE>					</TD>
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
	if(ctkmIds!=null){
		ctkmIds.close();
		ctkmIds = null;
	}
	
}catch(Exception er){
	
}
}	%>