<%@page import="geso.dms.center.beans.congnonpp.ICongnonpp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page import="java.util.Hashtable"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Best/index.jsp");
	}else{ %>

<% 
	ICongnonpp voucherBean = (ICongnonpp)session.getAttribute("ctBean");
%>

<% 
	ResultSet dvkdList = voucherBean.getDvkdList();
	ResultSet kbhList = voucherBean.getKbhList();
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/tablezebra.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>

<script type="text/javascript">
	
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
		$(".button").hover(function() {
			$(".button img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
	$(document).ready(function() {
		$(".button1").hover(function() {
			$(".button1 img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
</script>

<link href="../css/select2.css" rel="stylesheet" />
 <script src="../scripts/select2.js"></script>
 <script>
     $(document).ready(function() { 
      $("select:not(.notuseselect2)").select2({ width: 'resolve' });     
     });
    </script>

<SCRIPT language="JavaScript" type="text/javascript">

function upload() 
{			
   if(document.forms["KHForm"].filename.value=="")
   { document.forms["KHForm"].errors.value="Chưa tìm file upload lên. Vui lòng chọn file cần upload."; }   
   else if(document.forms["KHForm"].thoigian.value=="" || document.forms["KHForm"].diengiai.value=="" ||
		   document.forms["KHForm"].httt.value=="" || document.forms["KHForm"].dvkdId.value=="" || document.forms["KHForm"].kbhId.value=="")
   {
	 document.forms["KHForm"].errors.value="Vui lòng nhập đầy đủ thông tin !";
   }
   else
   {
	 document.forms["KHForm"].setAttribute('enctype', "multipart/form-data", 0);
	 document.KHForm.action.value = 'upload';	 
	 document.forms["KHForm"].submit();	
   }
}

function submitform()
{
    document.forms["KHForm"].submit();
}

function saveform() {
	if(document.forms["KHForm"].thoigian.value=="" || document.forms["KHForm"].diengiai.value=="" ||
	document.forms["KHForm"].httt.value=="" || document.forms["KHForm"].dvkdId.value=="" || document.forms["KHForm"].kbhId.value=="")
   {
	 document.forms["KHForm"].errors.value="Vui lòng nhập đầy đủ thông tin !";
   }
   else
   {
	 document.KHForm.action.value = 'save';
	 document.forms["KHForm"].submit();
   }	
}

</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>

<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="KHForm" method="post" action="../../UploadCongnoNPPSvl" charset="utf-8">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input name="userId" type="hidden" value='<%=userId %>' size="30">
<input name="action" type="hidden" value='1' size="30">
<input name="id" type="hidden" value='<%= voucherBean.getId() %>' size="30">
<div id="main" style="width: 99%; padding-left: 2px">
	<div align="left" id="header" style="width: 100%; float: none">
		<div style="width: 70%; padding: 5px; float: left" class="tbnavigation">Quản lý bán hàng &gt; Công nợ nhà phân phối &gt; Tạo mới</div>					
		<div align="right" style="padding: 5px" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;</div>
	</div>
	<div>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR class = "tbdarkrow">
					<TD width="30" align="left"><A href="../../CongnoNPPSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
				    <TD width="2" align="left" ></TD>
				    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
					<TD >&nbsp; </TD>						
				</TR>
		</TABLE>
	</div>
	<div align="left" style="width: 100%; float: none; clear: left">
		<fieldset>
			<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
			<textarea style="border : none; width: 100% ;" id="errors" name="errors" rows="1">
				<%=voucherBean.getMsg() %></textarea>
		</fieldset>
	</div>
	
	<div align="left" 
		style="width: 100%; float: none; clear: left; font-size: 0.7em">
		<fieldset style="padding : 0px 5px 5px 5px;">
			<legend class="legendtitle">Upload công nợ NPP</legend>
			<div style="width: 100%; float: none" align="left">
				<div style="width: 100%; float: none" align="left" class="plainlabel">													
				<TABLE width="70%" cellpadding="6" cellspacing="0">													
				<TR>
					<TD width="20%" class="plainlabel">Diễn giải <FONT class="erroralert"> *</FONT></TD>									
					<TD width="81%" class="plainlabel">					
					<INPUT name="diengiai" maxlength="50" id="diengiai" type="text" value="<%= voucherBean.getDiengiai() %>" >
					</TD>																						   
				</TR>
																				
				<TR>
					<TD width="10%" class="plainlabel">Thời gian <FONT class="erroralert"> *</FONT></TD>									
					<TD width="81%" class="plainlabel">
					 <input type="text"  class="days" size="11"
               		 id="thoigian" name="thoigian" value="<%= voucherBean.getThoigian()%>" maxlength="10" readonly />
					</TD>																	   				
				</TR>
				
				<TR>
                   <TD width="13%" class="plainlabel">Đơn vị kinh doanh <FONT class="erroralert"> *</FONT></TD>
				   <TD width="18%" colspan='3' class="plainlabel">
				   <SELECT style=" width : 175px;" name="dvkdId" id="dvkdId">
					<option value=""> </option>
					<%if(dvkdList != null) {
					try
					{
						while(dvkdList.next())
						{
							if(voucherBean.getDvkdId().equals(dvkdList.getString("pk_seq")))
							{%>
								<option value="<%= dvkdList.getString("pk_seq") %>" selected="selected" ><%= dvkdList.getString("donvikinhdoanh") %></option>
							<%}else{%>
								<option value="<%= dvkdList.getString("pk_seq") %>"><%= dvkdList.getString("donvikinhdoanh") %></option>
							<%}
						}
					}catch(java.sql.SQLException e){} }%>								
				   </SELECT>																													
				   </TD>													  
				</TR>
				
				 <TR>
                   <TD width="13%" class="plainlabel">Kênh bán hàng <FONT class="erroralert"> *</FONT></TD>
				   <TD width="18%" colspan='3' class="plainlabel">
				   <SELECT style=" width : 175px;" name="kbhId" id="kbhId">
					<option value=""> </option>
					<%if(kbhList != null) {
					try
					{
						while(kbhList.next())
						{
							if(voucherBean.getKbhId().equals(kbhList.getString("pk_seq")))
							{%>
								<option value="<%= kbhList.getString("pk_seq") %>" selected="selected" ><%= kbhList.getString("diengiai") %></option>
							<%}else{%>
								<option value="<%= kbhList.getString("pk_seq") %>"><%= kbhList.getString("diengiai") %></option>
							<%}
						}
					}catch(java.sql.SQLException e){} }%>								
				   </SELECT>																													
				   </TD>													  
				</TR>	
				
				<TR>
					<TD width="10%" class="plainlabel">Hình thức thanh toán <FONT class="erroralert"> *</FONT></TD>									
					<TD width="81%" class="plainlabel">					
					<INPUT name="httt" maxlength="50" id="httt" type="text" value="<%= voucherBean.getHinhthuctt() %>" >
					</TD>																						   
				</TR>					
				</TABLE>
				
				<TABLE width="70%" cellpadding="6" cellspacing="0">											
				<TR>													
				    <TD width="25%" class="plainlabel">									
						<INPUT type="file" name="filename" size="40" value=''> &nbsp;&nbsp;&nbsp;&nbsp;
						<a class="button2" href="javascript:upload()"> <img style="top: -4px;" src="../images/button.png" alt="">Upload</a>							
					</TD>								
				</TR>																	 																					
				</TABLE>																                    		             	                        																																					
				</div> 
			</div>
		</fieldset>
	</div> 
	<br /> <br /> <br /> <br />
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
<%
	if(voucherBean!=null)
	{
		voucherBean.DBclose();
		voucherBean=null;
	}
	session.setAttribute("voucherBean", null);
}%>