<%@page import="geso.dms.center.beans.sitecode_conv.Isitecode_conv"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.center.beans.nhaphanphoi.INhaphanphoi" %>
<%@ page  import = "geso.dms.center.beans.nhaphanphoi.INhaphanphoiList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ int[] quyen = new  int[5];
		quyen = util.Getquyen("18",userId);
		
		System.out.println(quyen[0]);
		System.out.println(quyen[1]);
		System.out.println(quyen[2]);
		System.out.println(quyen[3]);	
		System.out.println(quyen[4]);%>
	

<% Isitecode_conv obj = (Isitecode_conv)session.getAttribute("obj"); 
	ResultSet rs_sitecode_conv=obj.getsistecode_conv();
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
	document.forms['nppForm'].nppTen.value="";
	document.forms['nppForm'].TrangThai.value="";
    
    submitform();    
}

function submitform()
{
	document.forms['nppForm'].action.value= 'search';
	document.forms['nppForm'].submit();
}


function thongbao()
{var tt = document.forms['nppForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['nppForm'].msg.value);
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

<form name="nppForm" method="post" action="../../sitecode_convSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
		<!--begin common dossier info---> <!--End common dossier info--->
		<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kinh doanh &gt; Nhà phân phối ERP </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>
		</TABLE>
		<TABLE width="100%" cellpadding="0" cellspacing="0">
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>				
							  <LEGEND class="legendtitle">Tiêu chí tìm kiếm &nbsp;</LEGEND>
						    
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								    <tr>  <TD width="22%" class="plainlabel">Tên của nhà phân phối (NPP) </TD>
								        <TD colspan="3" class="plainlabel">
								        	<INPUT name="nppTen" type="text" size="40" value="<%=obj.getten()%>" onChange="submitform();"></TD>
								  </TR>
								  <TR>
								    <TD class="plainlabel">Trạng thái </TD>
								    <TD colspan="3" class="plainlabel"><select name="TrangThai" onChange = "submitform();">
											
											<% if (obj.gettrangthai().equals("0")){%>
											  <option value="0">Chờ xử lý </option>
											  <option value="1" selected>Hoạt động</option>
											  <option value="2">Ngưng hoạt động</option>
											   <option value=""></option>
											  
											<%}else if(obj.gettrangthai().equals("1")) {%>
											  <option value="0"> Chờ xử lý </option>
											  <option value="1" selected >Hoạt động</option>
											  <option value="2" >  Ngưng hoạt động</option>
											   <option value=""></option>
											<%}else if(obj.gettrangthai().equals("2")){%>																						  
											  <option value="0" >Chờ xử lý</option>
											  <option value="1" >Hoạt động</option>
											  <option value="2" selected>Ngưng hoạt động </option>
											    <option value=""></option>
											<%}else{%>
												 <option value="0" >Chờ xử lý</option>
											  <option value="1" >Hoạt động</option>
											  <option value="2" >Ngưng hoạt động </option>
											    <option value="" selected></option>
											<%} %>
										    </select></TD>
						      </TR>
							    <TR>
									<TD colspan="4" class="plainlabel">
									<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
									<!-- 
                                      <INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();"> -->
                                       </TD>
								</TR>
							</TABLE>

							</FIELDSET>
							</TD>
						</TR>
					</TABLE>
					</TD>
				</TR>
		</TABLE>
		<TABLE width="100%" cellpadding="0" cellspacing="0">		
				<TR>
					<TD width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Nhà phân phối ERP
							
						</LEGEND>
	
						    <TABLE class="" width="100%">
						<TR>
							<TD width="100%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="13%">Tên NPP ERP </TH>
									<TH width="10%">Sitecode ERP</TH>
									<TH width="10%">NPP DMS </TH>
									<TH width="10%">Sitecode DMS</TH>
									<TH width="9%">Ngày tạo</TH>
									<TH width="12%">Người tạo </TH>
									<TH width="9%">Ngày sửa</TH>
									<TH width="11%">Người sửa </TH>
									<TH width="9%">Trạng thái </TH>
									<TH width="8%" align="center">&nbsp;Tác vụ</TH>
								</TR>
								
								<% 
									
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									try{
									while (rs_sitecode_conv.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="left"><div align="left"><%=rs_sitecode_conv.getString("ten")%></div></TD>                                   
												<TD><div align="center"><%=rs_sitecode_conv.getString("sitecode")%></div></TD>    
												<%if(!rs_sitecode_conv.getString("trangthai").trim().equals("0")){ %>                               
												<TD><div align="center"><%= rs_sitecode_conv.getString("npptiennhiem")%></div></TD>
												<%}else{ %>
												<TD><div align="center"><%= rs_sitecode_conv.getString("tenupdate")%></div></TD>
												<%} %>
												<TD align="center"><%=rs_sitecode_conv.getString("convsitecode")%></TD>
												<TD align="center"><%=rs_sitecode_conv.getString("ngaytao")%></TD>
												<TD align="center"><%=rs_sitecode_conv.getString("nguoitao")%></TD>
												<TD align="center"><%=rs_sitecode_conv.getString("ngaysua")%></TD>
												<TD align="center"><%=rs_sitecode_conv.getString("nguoisua")%></TD>
												<% if (rs_sitecode_conv.getString("trangthai").equals("0")){ %>
													<TD align="center">Chờ xử lý</TD>
												<%}else if(rs_sitecode_conv.getString("trangthai").equals("1")){%>
													<TD align="center">Hoạt động</TD>
												<%}else{%>
													<TD align="center">Ngưng hoạt động</TD>
												<%} %>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="2">
													<TR>
												<% if (rs_sitecode_conv.getString("trangthai").equals("0")){ 
													if(!rs_sitecode_conv.getString("convsitecode").toString().equals("")){
														%>
														<TD>
														<%if(quyen[4]!=0){ %>
														<A href = "../../sitecode_convUpdateSvl?userId=<%=userId%>&chot=<%=rs_sitecode_conv.getString("sitecode")%>"><img src="../images/Chot.png" alt="Cap nhat" title="Chốt" width="20" height="20" longdesc="Cap nhat" border = 0></A>
														<%} %>
														</TD>
														<TD>
														<%if(quyen[2]!=0){ %>
														<A href = "../../sitecode_convUpdateSvl?userId=<%=userId%>&update=<%=rs_sitecode_conv.getString("sitecode")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
														<%} %>
														</TD>
														<% 
													}else{
												%>
												
													<TD>
														<A href = "../../sitecode_convUpdateSvl?userId=<%=userId%>&update=<%=rs_sitecode_conv.getString("sitecode")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													</TD>
													<%}}else{ %>
													<TD>&nbsp;</TD>
													<TD>
														<A href = "#"><img src="../images/Display.png" alt="Xem" width="20" height="20" longdesc="Xem" border=0 ></A></TD>
													<%} %>
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }}
									catch(Exception er){
									}
								
								%>
																			


							</TABLE>
							</TD>
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

</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%}%>