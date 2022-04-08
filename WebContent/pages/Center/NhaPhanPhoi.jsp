<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.center.beans.nhaphanphoi.INhaphanphoi" %>
<%@ page  import = "geso.dms.center.beans.nhaphanphoi.INhaphanphoiList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{int[] quyen = new  int[6];
	quyen = util.Getquyen("NhaphanphoiSvl","",userId);
	
	//system.out.println(quyen[0]);
	//system.out.println(quyen[1]);
	//system.out.println(quyen[2]);
	//system.out.println(quyen[3]);	
	//system.out.println(quyen[4]); %>
	

<% INhaphanphoiList obj = (INhaphanphoiList)session.getAttribute("obj"); %>
<% ResultSet npplist = (ResultSet) obj.getNppList(); %>
<% ResultSet kv = (ResultSet)obj.getKhuvuc();  %>
<% ResultSet loainpp = (ResultSet)obj.getLoaiNPP();  %>
<% obj.setNextSplittings(); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("NhaphanphoiSvl","",nnId);	
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

function showMap(lat,lng){
	var url = "https://maps.google.com/?q=" + lat + "," + lng;
	window.open(url);
}
	
function clearform()
{
	document.nppForm.nppTen.value = "";	
	document.nppForm.DienThoai.value = ""; 
	document.nppForm.kvId.selectedIndex = 0;    
	document.nppForm.loainppId.selectedIndex =0
    document.nppForm.TrangThai.selectedIndex = 2;
    submitform();    
}

function submitform()
{
	document.forms['nppForm'].action.value= 'search';
	document.forms['nppForm'].submit();
}
function xuatexcel()
{
	document.forms['nppForm'].action.value= 'excel';
	document.forms['nppForm'].submit();
}
function newform()
{
	document.forms['nppForm'].action.value= 'new';
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

 
</style>
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

<form name="nppForm" method="post" action="../../NhaphanphoiSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
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
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %> </TD>
   
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
								    <tr>  <TD width="22%" class="plainlabel"><%=ChuyenNgu.get("Mã/Tên của nhà phân phối (NPP)",nnId) %> </TD>
								        <TD colspan="3" class="plainlabel">
								        	<INPUT name="nppTen" type="text" size="40" value="<%=obj.getTen()%>" onChange="submitform();"></TD>
								  </TR>
								  <TR>
								    <TD class="plainlabel"><%=ChuyenNgu.get("Số điện thoại (DT)",nnId) %> </TD>
								    <TD colspan="3" class="plainlabel">
								    		<INPUT name="DienThoai" type="text" size="40" value='<%=obj.getSodienthoai()%>' onChange="submitform();"> </TD>
						      </TR>
						  
								  <TR>
								    <TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %></TD>
								    <TD colspan="3" class="plainlabel"><SELECT name="kvId" onChange = "submitform();">
								    <option value=""></option>
								      <% try{while(kv.next()){ 
								    	if(kv.getString("kvId").equals(obj.getKvId())){ %>
								      		<option value='<%=kv.getString("kvId")%>' selected><%=kv.getString("kvTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=kv.getString("kvId")%>'><%=kv.getString("kvTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        			</TD>                        				
						     	 </TR>
						      
								  <TR>
								    <TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId) %> </TD>
								    <TD colspan="3" class="plainlabel"><select name="TrangThai" onChange = "submitform();">
											
											<% if (obj.getTrangthai().equals("1")){%>
											  <option value="1" selected><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
											  <option value="0"><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
											  <option value="2"> </option>
											  
											<%}else if(obj.getTrangthai().equals("0")) {%>
											  <option value="0" selected><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
											  <option value="1" ><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
											  <option value="2" > </option>
											  
											<%}else{%>																						  
											  <option value="1" ><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
											  <option value="0" ><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
											  <option value="2" selected> </option>
											<%}%>
										    </select></TD>
						      		</TR>
						      		
						    	<TR>
								    <TD class="plainlabel"><%=ChuyenNgu.get("Loại nhà phân phối",nnId) %></TD>
								    <TD colspan="3" class="plainlabel"><SELECT name="loainppId" onChange = "submitform();">
								    <option value=""></option>
								      <% try{while(loainpp.next()){ 
								    	if(loainpp.getString("pk_seq").equals(obj.getLoaiNppId())){ %>
								      		<option value='<%=loainpp.getString("pk_seq")%>' selected><%=loainpp.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=loainpp.getString("pk_seq")%>'><%=loainpp.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        			</TD>                        				
						     	 </TR>
						      
							    <TR>
									<TD colspan="4" class="plainlabel">
									<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	
			<a class="button2" href="javascript:xuatexcel()">
				<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất Excel",nnId) %> </a>&nbsp;&nbsp;&nbsp;&nbsp;	
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
							<LEGEND class="legendtitle">&nbsp;Nhà phân phối  
							<%if(quyen[0]!=0){ %>
							<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> </a> 
    	<%} %>                           
							<!-- 
							<INPUT name="new" type="button" value="Tao moi" onClick="newform();"> -->
						</LEGEND>
	
						    <TABLE class="" width="100%">
						<TR>
							<TD width="100%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="3%"><%=ChuyenNgu.get("STT",nnId) %> </TH>
									<TH width="5%"><%=ChuyenNgu.get("Mã",nnId) %> </TH>
									<TH width="13%"><%=ChuyenNgu.get("Tên NPP",nnId) %> </TH>
									<TH width="10%"><%=ChuyenNgu.get("Loại NPP",nnId) %> </TH>
									<!-- <TH width="10%">Số ĐT </TH> -->
									<TH width="7%"><%=ChuyenNgu.get("Khu vực",nnId) %> </TH>
									<TH width="7%"><%=ChuyenNgu.get("Trạng thái",nnId) %> </TH>
									<TH width="7%"><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
									<TH width="9%"><%=ChuyenNgu.get("Người tạo",nnId) %> </TH>
									<TH width="7%"><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
									<TH width="9%"><%=ChuyenNgu.get("Người sửa",nnId) %> </TH>
									<TH width="10%" align="center">&nbsp;<%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
								</TR>
								
								<% 
									INhaphanphoi npp = null;
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(npplist!=null)
									while (npplist.next()){
										String lat = npplist.getString("lat")==null?"":npplist.getString("lat");
										String lng = npplist.getString("long")==null?"":npplist.getString("long");
										String lat1 = npplist.getString("lat1")==null?"":npplist.getString("lat1");
										String lng1 = npplist.getString("long1")==null?"":npplist.getString("long1");
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="left"><div align="left"><%=npplist.getInt("_no")%></div></TD>
												<TD align="left"><div align="left"><%=npplist.getString("nppMa")%></div></TD>
												<TD align="left"><div align="left"><%=npplist.getString("nppTen")%></div></TD>
												<TD align="left"><div align="left"><%=npplist.getString("tenloainpp")%></div></TD>                                   
												<%-- <TD><div align="center"><%=npplist.getString("dienthoai")%></div></TD>      --%>                              
												<TD><div align="center"><%= npplist.getString("khuvuc")%></div></TD>
												<% if (npplist.getString("trangthai").equals("1")){ %>
													<TD align="center"><%=ChuyenNgu.get("Hoạt động",nnId) %></TD>
												<%}else{%>
													<TD align="center"><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></TD>
												<%}%>
												<TD align="center"><%=npplist.getString("ngaytao")%></TD>
												<TD align="center"><%=npplist.getString("nguoitao")%></TD>
												<TD align="center"><%=npplist.getString("ngaysua")%></TD>
												<TD align="center"><%=npplist.getString("nguoisua")%></TD>
												
												<TD align="center">
													<% if (lat.length() > 0 && lng.length() > 0) { %>
														<A href="#"><img src="../images/maps_Bmu.png" onClick="showMap('<%=lat%>','<%=lng%>');" alt="Show map CN1" title="Show map CN1"  width="20" height="20" longdesc="Show map CN1" border=0></A>
													<% } %>
														<% if (lat1.length() > 0 && lng1.length() > 0) { %>
														<A href="#"><img src="../images/maps_Bmu.png" onClick="showMap('<%=lat1%>','<%=lng1%>');" alt="Show map CN2" title="Show map CN2" width="20" height="20" longdesc="Show map CN2" border=0></A>
													<% } %>
													<%if(quyen[2]!=0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") +"NhaphanphoiUpdateSvl?userId="+userId+"&update="+npplist.getString("id")+"")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													
													<%if(quyen[1]!=0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") +"NhaphanphoiSvl?userId="+userId+"&delete="+npplist.getString("id")+"")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa NPP" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa nhà phân phối này?')) return false;"></A>
													<%} %>
													
													<%if(quyen[3]!=0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") +"NhaphanphoiUpdateSvl?userId="+userId+"&display="+npplist.getString("id")+"")%>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
													<%} %>
														
														<!-- Xóa tọa độ nhà phân phối  -->
													<%if(quyen[1]!=0 && npplist.getString("lat").length() > 0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") +"NhaphanphoiSvl?userId="+userId+"&xoatd1="+npplist.getString("id")+"")%>"><img src="../images/Delete_Icon.png" alt="Xoa" title="Xóa tọa độ NPP" width="20" height="20" longdesc="Xóa tọa độ NPP" border=0 onclick="if(!confirm('Bạn muốn xóa tọa độ nhà phân phối này?')) return false;"></A>
													<%} %>
														
													<%if(quyen[1]!=0 && npplist.getString("lat1").length() > 0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") +"NhaphanphoiSvl?userId="+userId+"&xoatd2="+npplist.getString("id")+"")%>"><img src="../images/delete2.png" alt="Xoa" title="Xóa tọa độ NPP CN2" width="20" height="20" longdesc="Xóa tạo độ CN2" border=0 onclick="if(!confirm('Bạn muốn xóa tọa độ CN2 nhà phân phối này?')) return false;"></A>
													<%} %>
												</TD>
													
												<%-- <TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="2">
													<TR>
													<% if (lat.length() > 0 && lng.length() > 0) { %>
													<TD align="center">
															<A href="#">
																<img src="../images/maps_Bmu.png" onClick="showMap('<%=lat%>','<%=lng%>');"
																	alt="Show map" title="Show map" width="20" height="20" longdesc="Show map" border=0>
															</A>
														</TD>
														<TD>&nbsp;</TD>
													<% } %>
													
													<TD>
													<%if(quyen[2]!=0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") +"NhaphanphoiUpdateSvl?userId="+userId+"&update="+npplist.getString("id")+"")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													
													<TD>
													<%if(quyen[1]!=0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") +"NhaphanphoiSvl?userId="+userId+"&delete="+npplist.getString("id")+"")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa NPP" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa nhà phân phối này?')) return false;"></A>
														<%} %>
														</TD>
														<TD>&nbsp;</TD>
														<TD>
													<%if(quyen[3]!=0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") +"NhaphanphoiUpdateSvl?userId="+userId+"&display="+npplist.getString("id")+"")%>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
													<%} %>
													</TD>
														<!-- Xóa tọa độ nhà phân phối  -->
															<TD>
													<%if(quyen[1]!=0 && npplist.getString("lat").length() > 0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") +"NhaphanphoiSvl?userId="+userId+"&xoatd1="+npplist.getString("id")+"")%>"><img src="../images/Delete_Icon.png" alt="Xoa" title="Xóa tọa độ NPP" width="20" height="20" longdesc="Xóa tọa độ NPP" border=0 onclick="if(!confirm('Bạn muốn xóa tọa độ nhà phân phối này?')) return false;"></A>
														<%} %>
														
														<%if(quyen[1]!=0 && npplist.getString("lat1").length() > 0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") +"NhaphanphoiSvl?userId="+userId+"&xoatd2="+npplist.getString("id")+"")%>"><img src="../images/delete2.png" alt="Xoa" title="Xóa tọa độ NPP CN2" width="20" height="20" longdesc="Xóa tạo độ CN2" border=0 onclick="if(!confirm('Bạn muốn xóa tọa độ CN2 nhà phân phối này?')) return false;"></A>
														<%} %>
														</TD>
														
													</TR></TABLE>
												</TD> --%>
											</TR>
										<%m++; }%>
																			
										<tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('nppForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('nppForm', eval(nppForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												//system.out.println("Current page:" + obj.getNxtApprSplitting());
												//system.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('nppForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('nppForm', eval(nppForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('nppForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr>  

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

<%
    if(npplist != null){ npplist.close(); npplist = null; }
	if(kv != null){ kv.close(); kv = null; }
	if(loainpp != null){ loainpp.close(); loainpp = null; }
	
	obj.DBclose(); obj = null;
	session.setAttribute("obj", null);
	
	
	}%>