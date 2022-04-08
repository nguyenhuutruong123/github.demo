<%@page import="geso.dms.center.beans.gioihancongnott.imp.GioiHangCongNo_Npp"%>
<%@page import="geso.dms.center.beans.gioihancongnott.IGioiHangCongNoTT_Npp"%>
<%@page import="java.util.List"%>
<%@page import="geso.dms.center.beans.gioihancongnott.IGioiHanCongNoTT"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.gioihancongno.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<% IGioiHanCongNoTT ghcnBean = (IGioiHanCongNoTT)session.getAttribute("ghcnBean"); %>
<% ResultSet khuvuc = (ResultSet)session.getAttribute("rs_khuvuc"); %>
<% List<IGioiHangCongNoTT_Npp> listnhapp=ghcnBean.getListNhaPP(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");
String khuvucid = (String) session.getAttribute("khuvucid");
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<SCRIPT language="javascript" type="text/javascript">
	function confirmLogout()
	{
	   if(confirm("Bạn có muốn đăng xuất?"))
	   {
			top.location.href = "home.jsp";
	   }
	   return
	 }
	function submitform()
	{   
	
	   document.forms["ghcnForm"].action.value="loc";
	   document.forms["ghcnForm"].submit();
	}
	function saveform()
	{	  
		 var diengiai = document.getElementById("diengiai");
		 var songayno = document.getElementById("songayno");
		 var sotienno = document.getElementById("sotienno");
		 
		 if(diengiai.value == "")
		 {
			alert("Vui lòng nhập diễn giải mức chiết khấu...");
			return;
		 }
		 if(songayno.value == "")
		 {
			alert("Vui lòng nhập số ngày nợ...");
			return;
		 }
		 if(sotienno.value == "")
		 {
			alert("Vui lòng nhập số tiền nợ...");
			return;
		 }
				 
		 document.forms['ghcnForm'].action.value='save';
	     document.forms['ghcnForm'].submit();
	}
	function checkall(value){
		var checkone=document.getElementsByName("checkdetail");
		var giatricheck=document.getElementsByName("valuecheck");
		var chuoi;
		if(value==true){
			chuoi="1";
		}else{
			chuoi="0";
		}

		for(var i=0;i<checkone.length;i++ ){
			checkone.item(i).checked=value;
			giatricheck.item(i).value=chuoi;
		}
	}
	function recheck(){
		var checkone=document.getElementsByName("checkdetail");
		var giatricheck=document.getElementsByName("valuecheck");
		for(var i=0;i<checkone.length;i++ ){
			if(checkone.item(i).checked==true){
				giatricheck.item(i).value="1";
			}else {
				giatricheck.item(i).value="0";			
			}
			
				
		}
	}
</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="ghcnForm" method="post" action="../../GioiHanCongNoTTNewSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="action" value='1'>
<input type="hidden" name="formname" value="newform">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
	  <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <TR height="22">
						  	<TD align="left" colspan="2" class="tbnavigation"> &nbsp;Thiết lập dữ liệu nền > Dữ liệu nền kinh doanh > Mức tín dụng &gt; Tạo mới
						  	<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp; </TD>
						 </TR>
					  </table>
					</TD>
			  </TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="2">
				<TR >
					<TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR class = "tbdarkrow">
								<TD width="30" align="left"><A href= "javascript:history.back()">		 		
									<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								<TD width="2" align="left" ></TD>
								<TD width="30" align="left" ><A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
				    			<TD align="left" >&nbsp;</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
					
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" >
				<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
		                        	<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>              
		                        	<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" cols="104%" rows="1" style="width: 100%" readonly="readonly"><%= ghcnBean.getMessage() %></textarea>
		                        	<% ghcnBean.setMessage(""); %>
                        		</FIELDSET>
						   </TD>
					</tr>
				<TR>
				  <TD >
						<FIELDSET>
						<LEGEND class="legendtitle">&nbsp;Thông tin mức tín dụng &nbsp;</LEGEND>
						<TABLE class="tblight" width='100%' cellspacing="0" cellpadding="6">
							<TR>
								<TD width="15%" class="plainlabel">Diễn giải mức tín dụng <FONT class="erroralert">*</FONT></TD>
								<TD  class="plainlabel"><INPUT name="diengiai" id="diengiai" style="width:400px"
										type="text" value="<%= ghcnBean.getDiengiai() %>" ></TD>
							</TR>
							<TR>
								<TD  class="plainlabel">Số ngày tín dụng  <FONT class="erroralert">*</FONT></TD>
								<TD  class="plainlabel"><INPUT name="songayno" id="songayno" size="10"
										type="text" value="<%= ghcnBean.getSongayno() %>" ></TD>
							</TR>
							<TR>
								<TD class="plainlabel">Số tiền tín dụng <FONT class="erroralert">*</FONT></TD>
								<TD class="plainlabel" valign="middle">
								    <INPUT name="sotienno" id="sotienno" size="20" type="text" value="<%= ghcnBean.getSotienno() %>" > 
								</TD>
							</TR>
						</TABLE>
	
						</FIELDSET>
				 	</TD>	
				</TR>
               
			</TABLE>
        <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD colspan = "6" width='100%'>
						<FIELDSET><LEGEND class="legendtitle">Nhà phân phối được hưởng tín dụng</LEGEND>
						  	<div class="title" align="left" style="height:30px;background-color:#C5E8CD;padding-top:5px " >  Nhà phân phối thuộc khu vực: &nbsp;&nbsp;&nbsp;&nbsp;
						  	<select name="khuvucid" onChange = "submitform();">
								<option value="" selected></option>
									<% try{while(khuvuc.next()){ 
								    	if(khuvuc.getString("pk_seq").equals(khuvucid)){ %>
								      		<option value='<%= khuvuc.getString("pk_seq") %>' selected><%= khuvuc.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%= khuvuc.getString("pk_Seq") %>'><%= khuvuc.getString("ten") %></option>
								     <%}}}catch(java.sql.SQLException e){} %>	   
								</select><br>
							</div>					
								
					<TABLE  border="0" cellspacing="1" cellpadding="6" width='100%'>
								<TR class="tbheader">
								<TH width="18%">Tên khu vực </TH>
								  <TH align="center" width="17%">Mã nhà phân phối</TH>
									<TH width="25%">Tên nhà phân phối </TH>
									<TH width="30%">Địa chỉ </TH>
									<TH align="center" width="7%">Chọn <input type="checkbox" name=checkgaga onchange="checkall(this.checked);" >  </TH>
								</TR>
								<%
								int j = 0;
									int size=listnhapp.size();
									
									while(j<size){
									IGioiHangCongNoTT_Npp NhaPP=listnhapp.get(j);	
										if (j % 2 != 0) {%>						
										<TR class= "tblightrow">
										<%} else {%>
										<TR class= "tbdarkrow" >
										<%}%>
										<TD align="left"><div align="left"><input name="tenkhuvuc" type="hidden" value="<%=NhaPP.getKhuVuc() %>" > <%= NhaPP.getKhuVuc() %> </div></TD>
										<TD align="left"><div align="left"><input name="idnhapp" type="hidden" value="<%=NhaPP.getIdNhaPp() %>" > <%= NhaPP.getIdNhaPp() %> </div></TD>
										<TD align="center"><div align="left"><input name="tennhapp" type="hidden" value="<%=NhaPP.getTenNhaPp() %>" > <%= NhaPP.getTenNhaPp() %></div></TD>
										<TD align="center"><div align="left"><input name="diachinhapp" type="hidden" value="<%=NhaPP.getDiaChi() %>" ><%= NhaPP.getDiaChi() %> </div></TD>
										<%if(NhaPP.getId().equals("1")){ %>
										<TD>
											<input type="hidden" name=valuecheck value="<%=NhaPP.getId()%>">
											<input type="checkbox" name="checkdetail"  checked="checked" onchange="recheck();">
										 </TD>
										<%}else{ %>
										<TD> 
											<input type="hidden" name=valuecheck value="<%=NhaPP.getId()%>">
											<input type="checkbox" name="checkdetail" onchange="recheck();">
										</TD>
										<%} %>
									    <%j++;%> 
							 			<%}%>							     
                                    </TR> 
						</TABLE>
						</FIELDSET>								
					</TD>
				</TR>
		    </TABLE>
	    <!--end body Dossier--></TD>   
	    
	</TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>