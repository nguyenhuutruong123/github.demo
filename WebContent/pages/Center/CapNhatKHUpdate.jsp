<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="geso.dms.center.beans.Router.imp.Router"%>
<%@page import="javax.xml.crypto.Data"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  		
 	Router cnkhBean = (Router)session.getAttribute("cnkhBean");
 	ResultSet cnkhList = cnkhBean.getCnkhList();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">


<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">


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

function submitform()
{
    document.forms["cnkhForm"].submit();
}

function upload(){// nhan nut cap nhat

	   if(document.forms["cnkhForm"].filename.value==""){		   
		   document.forms["cnkhForm"].dataerror.value="Ch??a ch???n file upload l??n. Vui l??ng ch???n file c???n upload.";
	   }
	   
	   if(document.forms["cnkhForm"].diengiai.value==""){		   
		   document.forms["cnkhForm"].dataerror.value="Vui l??ng nh???p di???n gi???i.";
	   }
	   else
	   {
		 document.forms["cnkhForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["cnkhForm"].action.value="save";
		 document.forms["cnkhForm"].submit();	
	   }

}
function save(){	  	
	document.forms["cnkhForm"].action.value="save";
	document.forms["cnkhForm"].submit();
}

</SCRIPT>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>

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
<input type="hidden"  name="userId" value='<%=userId%>'>
<input type="hidden" name="userTen" value='<%=userTen%>'>
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value='<%=cnkhBean.getId()%>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <td align="left" colspan="2" class="tbnavigation">&nbsp;B??o c??o qu???n tri &gt; B??o c??o kh??c &gt; C???p nh???t kh??ch h??ng &gt; ?????c file Excel</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%=userTen%>&nbsp;  </TD></tr>
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
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">B??o l???i nh???p li???u </LEGEND>
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1"><%=cnkhBean.getMessage()%></textarea>						
	    				</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET>
						<LEGEND class="legendtitle" style="color:black">C???p nh???t kh??ch h??ng</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">																		            		        				
						 
						 <tr class="plainlabel">
							<TD class="plainlabel">Di???n gi???i </TD>						 
							<TD class="plainlabel">
						  		<INPUT type="text" name="diengiai" id="diengiai" value="<%= cnkhBean.getDiengiai() %>"> 
						  	</TD> 
						</tr>
						  	
						<tr class="plainlabel">
							<TD width="15%" class="plainlabel">Upload file </TD>						 
							<td colspan="2">
						  		<INPUT type="file" name="filename" size="40" value=''> 
						  	</td> 
						</tr>
						
					  	<tr class="plainlabel">
					  	<td colspan="4">
					  		&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:upload()">
							<img style="top: -4px;" src="../images/button.png" alt=""> C???p nh???t</a>							
					  	</td>
					  	</tr>						  							  	
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