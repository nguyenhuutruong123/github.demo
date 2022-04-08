<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="geso.dms.center.beans.chitieu.imp.ChiTieuNPP"%>
<%@page import="java.util.Calendar"%>
<%@page import="geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTKhuVuc"%>
<%@page import="geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTChoVung"%>
<%@page import="java.util.Date"%>
<%@page import="javax.xml.crypto.Data"%>
<%@page import="geso.dms.center.beans.chitieu.imp.ChiTieu"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhomkhuyenmai.INhomkhuyenmai" %>
<%@ page  import = "geso.dms.center.beans.nhomkhuyenmai.imp.Nhomkhuyenmai" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");   	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}%>
<%
		
	/* //Lay doi tuong objbean
 	IStockintransit obj=(IStockintransit)session.getAttribute("obj"); 

	int[] quyen = new  int[6];
	quyen = util.Getquyen("UploadChuyenNguSvl","", userId); */
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("UploadChuyenNguSvl","",nnId);	
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

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
<SCRIPT language="JavaScript" type="text/javascript"> 
function upload()
{ 
	if(document.forms["ChuyenNguForm"].filename.value==""){
		alert("Vui lòng chọn file upload !");
		return;
	}
	else
	{ 
		// Kiểm tra định dạng file có đúng là xls hay không !
		 var res_field = document.forms["ChuyenNguForm"].filename.value;   
		  var extension = res_field.substr(res_field.lastIndexOf('.') + 1).toLowerCase();
		  var allowedExtensions = ['xls'];
		  if (res_field.length > 0)
		     {
		          if (allowedExtensions.indexOf(extension) === -1) 
		             {
		               alert('Sai Format. Chỉ được phép Upload định dạng file excel: ' + allowedExtensions.join(', ') + '');
		               return ;
		             }
		    }	
	}  
	document.forms["ChuyenNguForm"].setAttribute('enctype', "multipart/form-data", 0);
	document.forms['ChuyenNguForm'].action.value="upload";
    document.forms["ChuyenNguForm"].submit();
  
}   

function exportToMCP()
{	 
	document.forms['ChuyenNguForm'].action.value="export";
	document.forms['ChuyenNguForm'].submit();
}

 

</SCRIPT>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2(); 
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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden"  name="userId" value='<%=userId%>'>
<input type="hidden" name="userTen" value='<%=userTen%>'>
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
							 <TD align="left" colspan="2" class="tbnavigation"><%=" "+url %></TD> 
							 <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng bạn",nnId) %> <%=userTen%>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	 

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black"><%=ChuyenNgu.get("Upload chuyển ngữ",nnId) %></LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">  
                     
						  	<tr class="plainlabel">
						  
						  	  <td colspan="2">
						  	  <INPUT type="file" name="filename" size="40" value=''> 
						  	  </td> 
						  	</tr>
						  	</TABLE>
						  	<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  	<tr class="plainlabel">
						  	<td colspan="0" style="width:100px">
						  		&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:upload()">
								<img style="top: -4px;" src="../images/button.png" alt="">Upload</a>							
						  	</td>
						  	
						  	<td >
						  		&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:export()">
								<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất kết quả",nnId) %></a>							
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


	<script type="text/javascript">
		$("select:not(.notuseselect2)").css({
			"width": "200px", 
			//"height": "200px"
		});
	</script>

<script type="text/javascript"> 
</script>
</body>  
 
</HTML>