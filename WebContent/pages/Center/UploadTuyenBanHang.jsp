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
	}else{ %>
<%
		
	//Lay doi tuong objbean
 	IStockintransit obj=(IStockintransit)session.getAttribute("obj");
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet npp = obj.getnpp();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("UploadTuyenBanHangSvl","", userId);
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("UploadTuyenBanHangSvl","",nnId);	
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
<!-- <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script> -->

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

<script src="../scripts/jquery.fileDownload.js" type="text/javascript"></script>
<script>
$(document).ready(function() {
   /*  $(".button2").click(function(){
        $.fileDownload('/UploadTuyenBanHangSvl')
            .done(function () { alert('File download a success!'); })
            .fail(function () { alert('File download failed!'); });
    });  */
    $("#mcp_ds").hide();
    
    
});
</script>

<SCRIPT language="JavaScript" type="text/javascript"> 
function upload()
{
	var npp = document.getElementById("nppId");
	if(npp.value == "")
	{
		alert("Vui lòng chọn nhà phân phối !");
		return;
	}	
	
	if(document.forms["ChiTieuTTForm"].filename.value==""){
		alert("Vui lòng chọn file upload !");
		return;
	}
	else
	{ 
		// Kiểm tra định dạng file có đúng là xls hay không !
		 var res_field = document.forms["ChiTieuTTForm"].filename.value;   
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
	
	var l = new Loading();
	l.render();
	l.show();
	l.hide_with_download('UploadTuyenBanHangSvl');
	document.forms["ChiTieuTTForm"].setAttribute('enctype', "multipart/form-data", 0);
    document.forms["ChiTieuTTForm"].submit();
  
}
function seach()
{
	
	document.forms['ChiTieuTTForm'].action.value= 'seach';
    document.forms["ChiTieuTTForm"].submit();
}

function exportToMCP()
{	
	var npp = document.getElementById("nppId");
	if(npp.value == "")
	{
		alert("Vui lòng chọn nhà phân phối !");
		return;
	}
	var l = new Loading();
	l.render();
	l.show();
	l.hide_with_download('UploadTuyenBanHangSvl'); 
	document.forms['ChiTieuTTForm'].action.value="exportmcp";
	document.forms['ChiTieuTTForm'].submit();
}

function exportToMCPDS()
{	
	var npp = document.getElementById("nppId");
	if(npp.value == "")
	{
		alert("Vui lòng chọn nhà phân phối !");
		return;
	}
	var l = new Loading();
	l.render();
	l.show();
	l.hide_with_download('UploadTuyenBanHangSvl');
	document.forms['ChiTieuTTForm'].action.value="exportmcpds";
	document.forms['ChiTieuTTForm'].submit();
}
function thongbao()
{   var tt = document.forms['ChiTieuTTForm'].msg.value;
	if(tt.length>0)
    	alert(document.forms['ChiTieuTTForm'].msg.value);
	
	document.forms['ChiTieuTTForm'].msg.value= '';
}

</SCRIPT>
<!-- <script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script> -->
<script type="text/javascript" src="../scripts/ajax.js"></script>
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
<form name ="ChiTieuTTForm" method="post" action="../../UploadTuyenBanHangSvl" >
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> 
<input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden"  name="userId" value='<%=userId%>'>
<input type="hidden" name="userTen" value='<%=userTen%>'>
<input type="hidden" name="action" value="0">
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script>
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
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle"><%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %> </LEGEND>
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1"><%=obj.getMsg()%></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black"><%=ChuyenNgu.get("Upload MCP",nnId) %></LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						
							
							
						  
				  		   <TR>
								<TD style="width:150px" class="plainlabel"><%=ChuyenNgu.get("Vùng/Miền",nnId) %></TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();">
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
									<TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %></TD>
									<TD class="plainlabel">
									<select name="khuvucId" id="khuvucId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
											   <option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
											<%}}%>
									</select>
									</TD>	
									</TR>
									<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
									<TD class="plainlabel">
										<select name="nppId" id="nppId">
											<option value="">All</option>
												<%if(npp !=null)
												{
													while(npp.next())
													{
														if(npp.getString("pk_seq").equals(obj.getnppId()))
														{	%>
													<option value="<%=npp.getString("pk_seq")%>" selected><%=npp.getString("ten") %></option>
														<%} 
														else
														{ %>
													<option value="<%=npp.getString("pk_seq")%>"><%=npp.getString("ten") %></option>
													<%}}} %>	
										</select>
									</TD>	
									</TR>
						  	<%-- <TR>
									<TD  class="plainlabel">Ngày upload mcp gần nhất</TD>
								    <TD class="plainlabel">
											<INPUT type="text" value="<%= obj.getNgaysuagannhat() %>" readonly="readonly">
								  </TD>
									</TR>
									<TR>
									<TD class="plainlabel">Người upload mcp gần nhất</TD>
								    <TD  class="plainlabel">
											<INPUT type="text" value="<%= obj.getNguoisuagannhat() %>" readonly="readonly">
								  </TD>
									</TR> --%>
						  	<%if(obj.getLoaiNv().equals("0")){ %>
						  	<tr class="plainlabel">
						  
						  	  <td colspan="2">
						  	  <INPUT type="file" name="filename" size="40" value=''> 
						  	  </td> 
						  	</tr>
						  	<%} %>
						  	</TABLE>
						  	
						  	<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  	<tr class="plainlabel">
						  		<%if(obj.getLoaiNv().equals("0")){ %>
								  	<td colspan="0" style="width:100px">
								  		&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:upload()">
										<img style="top: -4px;" src="../images/button.png" alt="">Upload</a>							
								  	</td>
						  		<%} %>
						  	<td >
						  		&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:exportToMCP()">
								<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất MCP",nnId) %></a>							
						  	</td>
						  			
						  	<td colspan="1">
						  		&nbsp;&nbsp;&nbsp;&nbsp; <a id = "mcp_ds" class="button2" href="javascript:exportToMCPDS()">
								<img  style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất MCP DS",nnId) %></a>							
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

</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>


<%
	try
	{
		if(vung!=null){ vung.close(); vung= null; }
		if(khuvuc != null){ khuvuc.close(); khuvuc= null; }
		if(npp != null){ npp.close(); npp= null; }
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj",null);
		
	}catch(java.sql.SQLException e){}
}
%>
</HTML>