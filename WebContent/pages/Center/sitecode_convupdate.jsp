<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.sitecode_conv.Isitecode_conv"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhacungcap.INhacungcap" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% Isitecode_conv obj = (Isitecode_conv)session.getAttribute("obj"); 
	ResultSet rs_npptiennhiem=obj.getRsNppTienNhiem();
	ResultSet rs_getkhuvuc=obj.getRsloctheokhuvuc();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
		<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">

</SCRIPT>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();})
</script>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
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
<!--HPB_SCRIPT_CODE_40
function submitform()
{
	document.forms["nccForm"].action.value="save";
    document.forms["nccForm"].submit();
}
function chot(){
	document.forms["nccForm"].action.value="chot";
	document.forms["nccForm"].submit();
}
function taomoinpp(){
	
	if(document.forms["nccForm"].khuvucid.value==""){
		document.forms["nccForm"].dataerror.value="Vui Long Chon Vung Cho Nha Phan Phoi Moi";
		return;
	}
	if(document.forms["nccForm"].ngayks.value==""){
		document.forms["nccForm"].dataerror.value="Vui Long Chon Ngay Khoa So Nha Phan Phoi Moi";
		return;
	}
	document.forms["nccForm"].action.value="nppmoi";
	document.forms["nccForm"].submit();
}
function locnhapp(){
	document.forms["nccForm"].action.value="submit";
	document.forms["nccForm"].submit();
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

<form name="nccForm" method="post" action="../../sitecode_convUpdateSvl" charset="UTF-8">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="action" type="hidden" value='save' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation" >

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		&nbsp;Thiết lập dữ liệu nền &gt; &nbsp;Dữ liệu nền cơ bản &gt; Nhà phân phối ERP
							   &gt; Tạo mới </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp; </td> 
						    </tr>
   
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow" >
									<TD width="30" align="left"><A href="javascript:history.back()"  ><img src="../images/Back30.png" alt="Quay ve"  title="Quay về" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Lưu lại" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									 <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: chot()" ><IMG src="../images/Chot.png" title="Chốt" alt="Chot" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>

				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
				  	<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
			    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%=obj.getMsg()%></textarea>
								
								</FIELDSET>
						   </TD>
					</tr>
					<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin nhà phân phối ERP 
								</LEGEND>
								<TABLE  width="100%" cellspacing="0" cellpadding="6">
								<TR>
									  <TD class="plainlabel" >Sitecode ERP <FONT class="erroralert">*</FONT></TD>
									  <TD class="plainlabel" ><input readonly="readonly" type="text" style="width:300px"  name="sitecode" value ='<%=obj.getsitecode() %>'></TD>
								  </TR>
									<TR>
									  <TD class="plainlabel" >Tên nhà phân phối ERP<FONT class="erroralert"> *</FONT></TD>
									  <TD class="plainlabel" ><INPUT  name="TenNCC_long" style="width:300px" value ='<%=obj.getten()%>'    type="text"></TD>
								  </TR>
									<TR>
									  <TD class="plainlabel" >Chọn khu vực<FONT class="erroralert">*</FONT></TD>
									  <TD class="plainlabel" >
									     <SELECT name="khuvucid" onchange="locnhapp();" >
									   <option  value=""> </option>
									   <% if(rs_getkhuvuc!=null)
								    	  try{while(rs_getkhuvuc.next()){ 
								    		if(rs_getkhuvuc.getString("pk_Seq").equals(obj.getKhuVucId() )){ %>
								      		<option value='<%= rs_getkhuvuc.getString("pk_seq") %>' selected><%= rs_getkhuvuc.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%= rs_getkhuvuc.getString("pk_Seq") %>'><%= rs_getkhuvuc.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){
								     		System.out.println("Loi :"+ e.toString());
								     	} %>	  
                        				</SELECT>
									   
									  </TD>
								  </TR>
								  
									<TR>
									  <TD class="plainlabel" >Chọn ngày khóa sổ : <FONT class="erroralert"> </FONT></TD>
									  <TD class="plainlabel" ><INPUT class="days" id="ngayks"  name="ngayks" style="width:200px" value ='<%=obj.getNgaykhoaso()%>'    type="text"></TD>
								  </TR>
								  
									<TR>
									  <TD class="plainlabel" >Chọn nhà phân phối DMS<FONT class="erroralert">*</FONT></TD>
									  <TD class="plainlabel" >
									     <SELECT name="npptn"  >
								   <option value=""> </option>
								   <% if(rs_npptiennhiem!=null)
								      try{while(rs_npptiennhiem.next()){ 
								    	if(rs_npptiennhiem.getString("pk_Seq").equals(obj.getIdNppTienNhiem() )){ %>
								      		<option value='<%= rs_npptiennhiem.getString("pk_seq") %>' selected><%= rs_npptiennhiem.getString("sitecode")+"_"+ rs_npptiennhiem.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%= rs_npptiennhiem.getString("pk_Seq") %>'><%= rs_npptiennhiem.getString("sitecode")+"_"+ rs_npptiennhiem.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){
								     		System.out.println("Loi :"+ e.toString());
								     	} %>	  
                        				</SELECT>
									   
									  </TD>
								  </TR>
								  <tr class="plainlabel">
								  <td colspan="2">
								  	<a class="button2" href="javascript:taomoinpp()">
										<img style="top: -4px;" src="../images/button.png" alt="">Tạo nhà phân phối mới </a>&nbsp;&nbsp;&nbsp;&nbsp;	
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
<%
try{
	// TODO Auto-generated method stub
	if(rs_getkhuvuc!=null){
		rs_getkhuvuc.close();
	}
	if(rs_npptiennhiem!=null){
		rs_npptiennhiem.close();
	}
	

	obj.DBClose();
	}catch(Exception er){
		
	}
}%>