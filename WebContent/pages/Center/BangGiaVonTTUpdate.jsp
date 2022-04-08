<%@page import="geso.dms.center.beans.banggiavontt.IBangGiaVonTT_SanPham"%>
<%@page import="java.util.List"%>
<%@page import="geso.dms.center.beans.banggiavontt.IBangGiaVonTT"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.banggiamuanpp.IBanggiamuanpp" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	String check1=(String)session.getAttribute("check");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IBangGiaVonTT bgvon = (IBangGiaVonTT)session.getAttribute("obj"); %>
<% List<IBangGiaVonTT_SanPham> listsanpham=bgvon.getListBangGia_SanPham(); %>
<% ResultSet dvkd = (ResultSet)session.getAttribute("rs_dvkd"); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
function submitform()
{   
   document.forms["bgmuanppForm"].submit();
}
function loadhang(){
	
	var value=	document.forms["bgmuanppForm"].check.value;
	if(value==0){
	document.forms['bgmuanppForm'].action.value='loadhang';
    document.forms["bgmuanppForm"].submit();
	}
}
 function saveform()
{
	if( document.forms['bgmuanppForm'].bgTen.value==""){
		document.forms["bgmuanppForm"].dataerror.value="Vui lòng nhập tên bảng giá";
      return;	
	}
	if( document.forms['bgmuanppForm'].dvkdId.value==""){
		document.forms["bgmuanppForm"].dataerror.value="Chọn đơn vị kinh doanh";
      return;	
	}
		
	document.forms['bgmuanppForm'].action.value='update';
    document.forms["bgmuanppForm"].submit();
}
 function upload(){
		var value=	document.forms["bgmuanppForm"].check.value;
		if(value==1){
			if(document.forms["bgmuanppForm"].filename.value==""){
				document.forms["bgmuanppForm"].dataerror.value="Chưa chọn file upload, vui lòng chọn file Excel theo đúng định dạng phiếu nhập kho để upload";
			}
			else{
				 document.forms['bgmuanppForm'].setAttribute('enctype', "multipart/form-data", 0);
				document.forms['bgmuanppForm'].submit();	
			}
		}else{
			document.forms["bgmuanppForm"].dataerror.value="Bạn đang chọn nhập dữ liệu, vui lòng chọn Import file để upload";
		}
	}
 	function checkradio(value){
	 document.forms["bgmuanppForm"].check.value=value;
 	}
function checkall(value){
	 //value=document.forms["bgmuanppForm"].check.checked;
		var checkone=document.getElementsByName("chonban");
		var giatricheck=document.getElementsByName("valuechonban");
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
		
		var checkone=document.getElementsByName("chonban");
		var giatricheck=document.getElementsByName("valuechonban");
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

<form name="bgmuanppForm" method="post" action="../../BangGiaVonTTNewSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId%>'>
<input type="hidden" name="userTen" value='<%= userTen%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%=bgvon.getId() %>'>
<input type="hidden" name="tenform" value='updateform'>
<input type="hidden" name="check" value='<%=check1%>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	>
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						   <tr height="22">
 							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; Dữ liệu nền sản phẩm &gt; Bảng giá vốn trung tâm &gt; Chỉnh sửa</TD>
 							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen  %>&nbsp;  </TD>
					     </tr>
						</table>
					 </TD>
				  </TR>	
		  	</TABLE>


			<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR ><TD >
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR class = "tbdarkrow">
						<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
				    	<TD align="left" >&nbsp;</TD>
					</TR>
				</TABLE>
			</TD></TR>
			</TABLE>

		<TABLE width="100%"  border = "0" cellspacing="0" cellpadding="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1" ><%=bgvon.getMessage()%></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

		 	<TR>
				<TD >
			        <FIELDSET>
			        <LEGEND class="legendtitle">&nbsp;Bảng giá bán cho NPP </LEGEND>
					<TABLE width="100%"cellpadding="0" cellspacing="1">
						<TR>
							<TD>				
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="center">
											<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
												<TR>
													<TD width="15%" class="plainlabel">Tên bảng giá <FONT class="erroralert">*</FONT></TD>
													<TD  class="plainlabel"><INPUT name="bgTen" value="<%= bgvon.getTen() %>"
										type="text"  size="40"/></TD>
												</TR>
												<TR>
								    				<TD class="plainlabel">Đơn vị kinh doanh</TD>
								      				<TD class="plainlabel">
								      				<SELECT name="dvkdId" onChange = "loadhang();" >								      
								  	 					
								  	 					<% try{ while(dvkd.next()){ 
								  	 							if(dvkd.getString("pk_seq").equals(bgvon.getDvkdId())){ %>
								      								<option value='<%=dvkd.getString("pk_seq")%>' selected><%=dvkd.getString("ten") %></option>
								      						   <%}	%>				
								  	 							<%}}catch(java.sql.SQLException e){} %>	
								     	
													</SELECT></TD>
												</TR>
												<tr class="plainlabel">
						  	<td >
						    <%
						    	if(check1.equals("0")){
						    %>
						  	<input type="radio" value="0"  checked="checked" name="chon" id="auto1" onclick="checkradio(this.value)"> <label for="auto1"> Nhập phiếu </label> <br>
						  	 <input type="radio" value="1" name="chon" id="noauto1" onclick="checkradio(this.value)"> <label for="noauto1" >Import từ file Excel  </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
						  	 <%
 						  	 	}else{
 						  	 %>
						     <input type="radio" value="0"   name="chon" id="auto1" onclick="checkradio(this.value)"> <label for="auto1"> Nhập phiếu</label> <br>
						  	 <input type="radio" value="1" checked="checked" name="chon" id="noauto1" onclick="checkradio(this.value)"> <label for="noauto1" >Import từ file Excel  </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  		 <%
  						  		 	}
  						  		 %>
						    
						  	  </td> 
						  	  <td>
						  	  <INPUT type="file" name="filename" size="40" value=''> 
						  	  </td>
						  	</tr>
						  	<tr class="plainlabel">
						  	<td colspan="2">
						  		&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:upload()">
								<img style="top: -4px;" src="../images/button.png" alt=""> Cập nhật</a>							
						  	</td>
						  	</tr>
											</TABLE>								
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
				</TABLE>
				</FIELDSET>
				<TABLE cellpadding="0" cellspacing="1" width="100%">
					<TR>
						<TD >
												<TABLE class="tabledetail" width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR class="tbheader">
									<TH width="5%">Số TT </TH>
									<TH width="20%">Mã sản phẩm </TH>
									<TH width="40%">Tên sản phẩm</TH>	
									<TH width="5%">Đơn vị</TH>
									<TH width="10%">Giá vốn</TH>
									<TH width="10%">Giá vốn mới</TH>	
									<TH width="10%">Chọn bán 
									<input type="checkbox" name="chon" onclick="checkall(this.checked);">
									</TH>
								</TR>
								<%
								int j = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								while(j<listsanpham.size()){
									IBangGiaVonTT_SanPham sanpham=listsanpham.get(j);
										if (j % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
									    <%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
										<TD align="center"><%= (j+1) %> </TD>
											<TD align="center"><div align="left"><input type="hidden" name="idsp" value="<%=sanpham.getSanPhamID() %>" >  <input type="hidden" name="masp" value="<%=sanpham.getMaSanPham() %>" ><%=sanpham.getMaSanPham() %>  </div></TD>
											<TD align="center"><div align="left"><input type="hidden" name="tensp" value="<%=sanpham.getTenSanPham() %>" > <%=sanpham.getTenSanPham() %> </div></TD>
											<TD align="center"><input type="hidden" name="dvt" value="<%=sanpham.getDonViTinh() %> "> <%=sanpham.getDonViTinh() %> </TD>
											<TD align="left"><input type='hidden' name=giavon value="<%= Math.round(sanpham.getGiaVon()) %>"/><%= Math.round(sanpham.getGiaVon()) %></TD>
											<TD align="left"><input type='text' name=giamoi value="<%= Math.round(sanpham.getGiaMoi()) %>"/></TD>
											<TD align="center">	
											<% if(sanpham.getChonBan().equals("1")){ %>										
												<input type="checkbox" name="chonban" checked="checked" onchange="recheck(this.value)" >		
												<%}else{ %>
												<input type="checkbox" name="chonban" onchange="recheck(this.value)">
												<%} %>	
												<input type="hidden" name="valuechonban"  value='<%= sanpham.getChonBan() %>' >								
											</TD>
												
							     		<%j++;
										 
								}%>

							</TABLE>

						</TD>
					</TR>
				</TABLE>
			</TD>
			</TR>
		</TABLE>
	</TR>
</TABLE>
</FORM>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%}%>