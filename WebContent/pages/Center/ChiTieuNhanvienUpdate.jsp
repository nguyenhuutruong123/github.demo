<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="javax.xml.crypto.Data"%>
<%@page import="geso.dms.center.beans.chitieunhanvien.imp.ChiTieuNhanvien"%>
<%@page import="geso.dms.center.beans.chitieunhanvien.IChiTieuNhanvien"%>
<%@page import="geso.dms.center.beans.chitieunhanvien.imp.CTNhanvien"%>
<%@page import="geso.dms.center.beans.chitieunhanvien.ICTNhanvien"%>
<%@page import="geso.dms.center.beans.chitieunhanvien.imp.CTNhanvien_NSP"%>
<%@page import="geso.dms.center.beans.chitieunhanvien.ICTNhanvien_NSP"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.sql.ResultSetMetaData" %>
<%@ page  import = "java.sql.Types" %>

<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%NumberFormat formatter = new DecimalFormat("#,###,##0.##");%>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
 	IChiTieuNhanvien objbean=(ChiTieuNhanvien)session.getAttribute("obj");
 	ResultSet chitieuRs = objbean.getChitieuRs();
 	ResultSet vung = objbean.getVungRS();
 	ResultSet khuvuc = objbean.getKhuVucRS();
 	Utility util = (Utility) session.getAttribute("util");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>HTP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

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
    document.forms["ChiTieuTTForm"].submit();
}

function xuatexcel()
{
	document.forms['ChiTieuTTForm'].action.value= 'excel';
	document.forms['ChiTieuTTForm'].submit();
}


function save(){
	
	document.forms["ChiTieuTTForm"].dataerror.value = "";
	var thang = document.forms["ChiTieuTTForm"].thang.value;
	var nam = document.forms["ChiTieuTTForm"].nam.value; 	 

	if (nam == 0){
		document.forms["ChiTieuTTForm"].dataerror.value = "Ch???n n??m c???n ?????t ch??? ti??u ";
		return;
	}
	if (thang == 0){
		document.forms["ChiTieuTTForm"].dataerror.value = "Ch???n th??ng c???n ?????t ch??? ti??u ";
		return;
	}   

	//kiem tra xem thang nam dat chi tieu co hop le hay khong
	var d = new Date();
	var year_= d.getFullYear();
	var month_=d.getMonth()+1;	 
	/*  if(nam<year_){
		 
		  document.forms["ChiTieuTTForm"].dataerror.value="Th???i gian ?????t ch??? ti??u kh??ng h???p l??. Ph???i ?????t th???i gian ch??? ti??u l???n h??n th???i gian hi???n th???i ";
			return; 
	 }else if(nam==year_ && thang<month_){
		  document.forms["ChiTieuTTForm"].dataerror.value="Th???i gian ?????t ch??? ti??u kh??ng h???p l??. Ph???i ?????t th???i gian ch??? ti??u l???n h??n th???i gian hi???n th???i";
			return; 
	}  */

	if (document.forms["ChiTieuTTForm"].filename.value == ""){
		document.forms["ChiTieuTTForm"].dataerror.value = "Ch??a t??m file upload l??n. Vui l??ng ch???n file c???n Upload.";
	}else{
		document.forms["ChiTieuTTForm"].setAttribute('enctype', "multipart/form-data", 0);
		document.forms["ChiTieuTTForm"].submit();	
	}
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
<form name="ChiTieuTTForm" method="post" action="../../ChiTieuNhanvienNewSvl"> 
<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden"  name="userId" value='<%=userId%>'>
<input type="hidden" name="userTen" value='<%=userTen%>'>
<input type="hidden" name="nkmId" value="">
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value='<%=objbean.getID()%>'>
<input type="hidden" name="tenform" value="0">
<input type="hidden" name="isDisplay" value='<%=objbean.getIsDisplay()%>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <%if(!objbean.getIsDisplay().equals("1") ){ %>
						  	 	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Qu???n l?? ch??? ti??u &gt; Khai b??o &gt; Ch??? ti??u nh??n vi??n &gt; C???p nh???t </TD>
						  	 <%} else { %>
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Qu???n l?? ch??? ti??u &gt; Khai b??o &gt; Ch??? ti??u nh??n vi??n &gt;Hi???n th??? </TD> 
							<%} %> 
							 <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%=userTen%>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
		
	
			<TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ChiTieuNhanvienSvl?userId="+userId )%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <%if(!objbean.getIsDisplay().equals("1") ){ %>
						    <TD width="30" align="left" ><A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<%} %>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">B??o l???i nh???p li???u </LEGEND>
	    				<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1"><%=objbean.getMessage()%></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Th??ng tin ch??? ti??u</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						
							<TR>
										  	 <TD class="plainlabel" colspan=2>
												<%if(objbean.getLoai().equals("1")){ %>									  	 
									  	 			<INPUT TYPE="radio" NAME="loai" value="1" checked onChange="submitform();" >NVBH
									  	 			<!-- <INPUT TYPE="radio" NAME="loai" value="2" onChange="submitform();">GSBH
									  	 			<INPUT TYPE="radio" NAME="loai" value="3" onChange="submitform();">ASM
									  	 			<INPUT TYPE="radio" NAME="loai" value="4" onChange="submitform();">RSM
									  	 			<INPUT TYPE="radio" NAME="loai" value="5" onChange="submitform();">NPP -->
									  	 			
									  	 			
									  	 		<%}else{
									  	 				if(objbean.getLoai().equals("2")){ %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="1" onChange="submitform();">NVBH
									  	 					<INPUT TYPE="radio" NAME="loai" value="2" checked onChange="submitform();">GSBH									  	 		
									  	 					<INPUT TYPE="radio" NAME="loai" value="3" onChange="submitform();">ASM
									  	 					<INPUT TYPE="radio" NAME="loai" value="4" onChange="submitform();">RSM
									  	 					<INPUT TYPE="radio" NAME="loai" value="5" onChange="submitform();">NPP
									  	 				
									  	 			  <%}else{ 
									  	 				 if(objbean.getLoai().equals("3")){ %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="1" onChange="submitform();">NVBH
									  	 					<INPUT TYPE="radio" NAME="loai" value="2" onChange="submitform();">GSBH
									  	 					<INPUT TYPE="radio" NAME="loai" value="3" checked onChange="submitform();">ASM
									  	 					<INPUT TYPE="radio" NAME="loai" value="4" onChange="submitform();">RSM
									  	 					<INPUT TYPE="radio" NAME="loai" value="5" onChange="submitform();">NPP
									  	 				
									  	 				<%}else if(objbean.getLoai().equals("4")){ %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="1"  onChange="submitform();">NVBH
									  	 					<INPUT TYPE="radio" NAME="loai" value="2" onChange="submitform();">GSBH
									  	 					<INPUT TYPE="radio" NAME="loai" value="3" onChange="submitform();">ASM
									  	 					<INPUT TYPE="radio" NAME="loai" value="4" checked  onChange="submitform();">RSM
									  	 					<INPUT TYPE="radio" NAME="loai" value="5" onChange="submitform();">NPP
									  	 				
										  	 	<%} else if(objbean.getLoai().equals("5")){ %>
								  	 					<INPUT TYPE="radio" NAME="loai" value="1"  onChange="submitform();">NVBH
								  	 					<INPUT TYPE="radio" NAME="loai" value="2" onChange="submitform();">GSBH
								  	 					<INPUT TYPE="radio" NAME="loai" value="3" onChange="submitform();">ASM
								  	 					<INPUT TYPE="radio" NAME="loai" value="4"   onChange="submitform();">RSM
								  	 					<INPUT TYPE="radio" NAME="loai" value="5" checked onChange="submitform();">NPP
								  	 				
									  	 	<%}
									  	 				 
									  	 			  }}%>
										  	 </TD>
										</TR>
			<TR >
						
						
							<TR>
								<TD width="20%" class="plainlabel" >Th??ng <FONT class="erroralert"> </FONT></TD>
								<TD class="plainlabel">
									<select name="thang" style="width :100px ">
									<option value=0> </option>  
									<%
  										int k=0;
									String[] thang = { "","1","2","3","4","5","6","7","8","9","10","11","12" };
  									for(k=0;k<=12;k++){
  									  if(k==objbean.getThang()){
  									%>
									<option value=<%=k%> selected="selected" > <%=thang[k]%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=thang[k]%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									
								</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">N??m</TD>
						  	  	<TD class="plainlabel">
									<select name="nam"  style="width :100px ">
										<option value=0> </option>  
										<%
										  Calendar c2 = Calendar.getInstance();
	  										int t=c2.get(Calendar.YEAR) +6;
	  										int n;
	  										for(n=2008;n<t;n++){
	  										if(n==objbean.getNam()){
	  									%>
										<option value=<%=n%> selected="selected" > <%=n%></option> 
										<%
	 										}else{
	 									%>
										<option value=<%=n%> ><%=n%></option> 
										<%
	 										}
	 									 }
	 									%>
									</select>
						  	  	</TD>
						  </TR>
						  
				  		  
						  	<TR>
						  	  	<TD class="plainlabel">Di???n gi???i</TD>
						  	  <TD class="plainlabel" >
						  
						  	  <input  type="text"  name="diengiai" value="<%=objbean.getDienGiai()%>" > 
						  	  		
						  	  	</TD>
						  	</TR>		
						  	
						  	
						  	<TR style="display: none" >
						  	  	  <TD class="plainlabel">Lo???i Upload</TD>
							  	  <TD class="plainlabel" >
							  	  		<select name="loaiUpload"  style="width :200px">
							  	  		<%if(objbean.getLoaiUpload().equals("2")){ %>
										<option value="1" > Ch??? ti??u b??nh th?????ng </option>  
										<%} else{ %>
										<option value="1" selected> Ch??? ti??u b??nh th?????ng </option>  
										<%} %>
									</select> 
							  	  </TD>
						  	</TR>	
						  	
								
								  	
						  	<tr class="plainlabel">
						  
						  	  <td >
						  	  <INPUT type="file" name="filename" size="40" value=''> 
						  	  </td>
						  	  <td>
						  	  <a class="button2" href="javascript:xuatexcel()">
											<img style="top: -4px;" src="../images/button.png" alt="">Xu???t Excel </a>
						  	  </td> 
						  	</tr>	
						  	
						  	
						 
						  	
						  	<tr class ="planlable">
							<td colspan="2">
							<%if( objbean.getID() != null && objbean.getID().trim().length() >0 && chitieuRs != null){ 
							
								ResultSetMetaData rsmd = chitieuRs.getMetaData();
								int socottrongSql = rsmd.getColumnCount();
							
							%>
							  <TABLE width="100%" >
                                <TR class="tbheader">
                                	<%for(int i =1 ; i <= socottrongSql;i++ ){ %>
                                    <TH width="10%"><%=rsmd.getColumnName(i) %></TH> 
                                    <%} %>
                                </TR>
                               <%
                               	while(chitieuRs.next())
                               	{ %> 
	                                <TR>
	                                <%for(int i =1 ; i <= socottrongSql;i++ ){
	                                	if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL )
	                                	{
	                                %>
	                                    <TD width="10%" align="right"><input  style="width: 100%" readonly value="<%=formatter.format(chitieuRs.getDouble(i)) %>"/></TD> 
										<%}else
										{ %>
										<TD width="10%"><input style="width: 100%" readonly value="<%=chitieuRs.getString(i) %>"/></TD>
										<%} %>
									<%} %>
	                                </TR>
                                	
                                
                                <%} %>
                                
                             </TABLE>
							<%} %>
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

//lamtrontien_phandu();
</script>
</BODY>
</HTML>