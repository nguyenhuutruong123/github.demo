<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.kehoachnhanvien.IKeHoachNhanVien" %>
<%@ page  import = "geso.dms.center.beans.kehoachnhanvien.IKeHoachNhanVienList" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/dms/index.jsp");
	} else { %>

<% IKeHoachNhanVienList obj = (IKeHoachNhanVienList)session.getAttribute("obj"); %>
<% ResultSet vungRs = obj.getVungRs(); %>
<% ResultSet khuvucRs = obj.getKhuvucRs(); %>
<% ResultSet khnvRs = (ResultSet)obj.getKhnvRs();  %>
<% ResultSet kbhRs = (ResultSet)obj.getKbhRs();  
int[] quyen = new  int[6];
quyen = util.Getquyen("KeHoachNhanVienSvl", "",userId);
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	}	
String url = util.getChuyenNguUrl("KeHoachNhanVienSvl","",nnId);	
	%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">

 function clearform()
 {
     document.khnvForm.vungId.selectedIndex = 0;
     document.khnvForm.khuvucId.selectedIndex = 0;
     document.khnvForm.kbhId.selectedIndex = 0;
     document.khnvForm.Thang.value = "";
     document.khnvForm.tennv.value = "";
     document.khnvForm.Nam.value = "";
     submitform();
 }

 function submitform()
 {
 	document.forms['khnvForm'].action.value= 'search';
 	document.forms['khnvForm'].submit();
 }

 function newform()
 {
 	document.forms['khnvForm'].action.value= 'new';
 	document.forms['khnvForm'].submit();
 }
 
 function upload()
 {
 	document.forms['khnvForm'].action.value= 'upload';
 	document.forms['khnvForm'].setAttribute('enctype', "multipart/form-data", 0);
 	document.forms['khnvForm'].submit();
 }
 
 function thongbao()
 {var tt = document.forms['khnvForm'].msg.value;
 	if(tt.length>0)
     alert(document.forms['khnvForm'].msg.value);
 	} 
 
</SCRIPT>



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

<form name="khnvForm" method="post" action="../../KeHoachNhanVienSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   	<%=url %>
							   </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn<%=userTen %>&nbsp;  </TD> 
						    </tr>
   						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm</LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
											<TD class="plainlabel" width="120px"><%=ChuyenNgu.get("Loại nhân viên",nnId) %></TD>
											<TD class="plainlabel"  width="120px">
												<select name="Loai" onChange="submitform();">
													<option value=""></option>
													<option value="1" <%=obj.getLoai().equals("1") ? "selected" : ""%>>RSM</option>
													<option value="2" <%=obj.getLoai().equals("2") ? "selected" : ""%>>ASM</option>
													<option value="3" <%=obj.getLoai().equals("3") ? "selected" : ""%>>GSBH</option>
												</select>
											</TD>
											<TD class="plainlabel" colspan="4"></TD>
										</TR>
										<TR>
											<TD class="plainlabel" width="120px"><%=ChuyenNgu.get("Tháng",nnId) %></TD>
										    <TD class="plainlabel" >
												<INPUT name="Thang" size="40" type="text" value="<%= obj.getThang()%>" onChange="submitform();">
											</TD>
											
											<TD class="plainlabel" width="120px"><%=ChuyenNgu.get("Năm",nnId) %></TD>
											<TD class="plainlabel" >
												<INPUT name="Nam" size="40" type="text" value="<%= obj.getNam()%>" onChange="submitform();">
											</TD>
											<TD class="plainlabel"></TD>
											<TD class="plainlabel"></TD>
										</TR>
										<TR>
					                        <TD class="plainlabel">Vùng</TD>
											<TD class="plainlabel" width="250px">
												<select  name="vungId" onchange="submitform();"  id="vungId" name="vungId">
													<option value="">All</option>
													<% if(vungRs!=null){
												 		while(vungRs.next()){
															String kvId = vungRs.getString("pk_seq");
															if(kvId.equals(obj.getVungId())){%>
																<option selected="selected" value="<%=vungRs.getString("pk_seq")%>">
																	<%=vungRs.getString("ten")%></option>
															<%}else{%>
																<option value="<%=vungRs.getString("pk_seq")%>">
																	<%=vungRs.getString("ten")%></option>
												 	<% }}}%>										 	
												</select>
											</TD>
														
					                        <TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %></TD>
											<TD class="plainlabel" width="250px" >
												<select  name="khuvucId" onchange="submitform();" id="khuvucId">
													<option value="">All</option>
													<% if(khuvucRs!=null){
												 		while(khuvucRs.next()){
															String kvId = khuvucRs.getString("pk_seq");
															if(kvId.equals(obj.getKhuvucId())){%>
																<option selected="selected" value="<%=khuvucRs.getString("pk_seq")%>">
																	<%=khuvucRs.getString("ten")%></option>
															<%}else{%>
																<option value="<%=khuvucRs.getString("pk_seq")%>">
																	<%=khuvucRs.getString("ten")%></option>
												 	<% }}}%>										 	
												</select>
											</TD>
											<TD class="plainlabel"></TD>
											<TD class="plainlabel"></TD>
					                     </TR> 
					                     <TR>
					                        <TD class="plainlabel">Kênh bán hàng</TD>
											<TD class="plainlabel"  >
												<select  name="kbhId" onchange="submitform();"  id="kbhId" name="kbhId">
													<option value="">All</option>
													<% if(kbhRs!=null){
												 		while(kbhRs.next()){
															String kbhId = kbhRs.getString("pk_seq");
															if(kbhId.equals(obj.getKbhId())){%>
																<option selected="selected" value="<%=kbhRs.getString("pk_seq")%>">
																	<%=kbhRs.getString("ten")%></option>
															<%}else{%>
																<option value="<%=kbhRs.getString("pk_seq")%>">
																	<%=kbhRs.getString("ten")%></option>
												 	<% }}}%>										 	
												</select>
											</TD>
													<TD class="plainlabel" width="120px"><%=ChuyenNgu.get("Tên nhân viên",nnId) %></TD>
										    <TD class="plainlabel" >
												<INPUT name="tennv" id="tennv" size="40" type="text" value="<%= obj.getTenNhanVien()%>" onChange="submitform();">
											</TD>	
					                       <TD class="plainlabel"></TD>
											<TD class="plainlabel"></TD>
					                     </TR>    
										<TR>
                                             <TD class="plainlabel" >
                                              <a class="button2" href="javascript:clearform()">
												<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                              
                                            </TD>
                                            <TD class="plainlabel" colspan="5"></TD>
										</TR>
									</TABLE>
									</FIELDSET>
								</TD>	
							</TR>
						</TABLE>
					</TD>
				</TR>	
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="1">
			    <TR>
					<TD align="left" >
						 <FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Kế hoạch nhân viên&nbsp;&nbsp;&nbsp; 
							<%if(quyen[Utility.THEM]!=0) {%>
							<a class="button3" href="javascript:newform()">
								<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %></a>                            
							<%} %>
							
							</LEGEND> 
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="10%"><%=ChuyenNgu.get("STT",nnId) %></TH>
												<TH width="10%"><%=ChuyenNgu.get("Tên nhân viên",nnId) %></TH>
											    <TH width="10%"><%=ChuyenNgu.get("Tháng",nnId) %></TH>
											    <TH width="10%"><%=ChuyenNgu.get("Năm",nnId) %></TH>
											    <TH width="10%"><%=ChuyenNgu.get("Trạng thái",nnId) %></TH>
											    <TH width="10%"><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
												<TH width="10%"><%=ChuyenNgu.get("Người tạo",nnId) %></TH>										
												<TH width="10%"><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
												<TH width="10%"><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
												<TH width="10%"><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
											</TR>
								<% 
									if(khnvRs != null) 
									{
										int m = 0;
										String lightrow = "tblightrow";
										String darkrow = "tbdarkrow";
										String trangthai = "";
										while (khnvRs.next()) {
											trangthai = khnvRs.getString("trangthai").equals("0") ? "Chưa chốt" : "Đã chốt";
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
											<%} else {%>
												<TR class= <%=darkrow%> >
											<%}%>
													<TD align="center"><%=m+1 %></TD>
													<TD align="center"><%= khnvRs.getString("nhanvien") %></TD>                                   
													<TD align="center"><%= khnvRs.getString("thang") %></TD>
													<TD align="center"><%= khnvRs.getString("nam") %></TD>
													<TD align="center"><%= trangthai %></TD>
													<TD align="center"><%= khnvRs.getString("ngaytao") %></TD>
													<TD align="center"><%= khnvRs.getString("nguoitao") %></TD>
													<TD align="center"><%= khnvRs.getString("ngaysua") %></TD>
													<TD align="center"><%= khnvRs.getString("nguoisua") %></TD>
													<TD align="center">
														<TABLE border = 0 cellpadding="0" cellspacing="0">
															<TR>
																<% if(quyen[Utility.XEM] != 0){ %>
																<TD>
																	<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "KeHoachNhanVienUpdateSvl?userId="+userId+"&display="+ khnvRs.getString("pk_seq")+"") %>">
																		<img src="../images/Display20.png" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
																</TD>
																<TD>&nbsp;</TD>
																
															<% }if(khnvRs.getString("trangthai").equals("0")) {
																if(khnvRs.getString("loaikehoach").equals("0")) { 
																	if(  khnvRs.getInt("allowChot") > 0 &&  quyen[Utility.CHOT] != 0){%>
																	<TD>
																		<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "KeHoachNhanVienSvl?userId="+userId+"&duyet="+ khnvRs.getString("pk_seq")+"") %>" onclick="if(!confirm('Bạn muốn chốt kế hoạch này?')) return false;">
																			<img src="../images/Chot.png" title="Duyệt" width="20" height="20" longdesc="Duyệt" border = 0></A>
																	</TD>
																	<TD>&nbsp;</TD>
																	<%} %>																	
																	
															<%  } else { %>
															
															<% if( khnvRs.getInt("allowChot") > 0 &&quyen[Utility.CHOT] != 0){%>
																	<TD>
																		<A href =  "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "KeHoachNhanVienSvl?userId="+userId+"&duyet="+ khnvRs.getString("pk_seq")+"") %>" onclick="if(!confirm('Bạn muốn chốt kế hoạch này?')) return false;">
																			<img src="../images/Chot.png" title="Duyệt" width="20" height="20" longdesc="Duyệt" border = 0></A>
																	</TD>
																	<TD>&nbsp;</TD>
																	<%} %>
															
																	<%if(quyen[Utility.SUA]!=0) {%>
																	<TD>
																		<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "KeHoachNhanVienUpdateSvl?userId="+userId+"&update="+ khnvRs.getString("pk_seq")+"") %>">
																			<img src="../images/Edit20.png" title="Chỉnh sửa" width="20" height="20" longdesc="Chỉnh sửa" border = 0></A>
																	</TD>
																	<TD>&nbsp;</TD>
																	<%} %>
																	<%if(quyen[Utility.XOA]!=0) {%>
																	<TD>
																		<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "KeHoachNhanVienSvl?userId="+userId+"&delete="+ khnvRs.getString("pk_seq")+"") %>">
																			<img src="../images/Delete20.png" title="Xóa" width="20" height="20" longdesc="Xóa" border=0 onclick="if(!confirm('Bạn muốn xóa kế hoạch này')) return false;"></A>
				                                                	</TD>
				                                                	<%} %>
				                                                <%}
															} else { 
																if( khnvRs.getInt("allowChot") > 0 && khnvRs.getString("loaikehoach").equals("0") && quyen[Utility.HUYCHOT] != 0) { %>
																	<TD>
																		<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "KeHoachNhanVienSvl?userId="+userId+"&moduyet="+ khnvRs.getString("pk_seq")+"") %>" onclick="if(!confirm('Bỏ chốt kế hoạch?')) return false;">
																			<img src="../images/unChot.png" title="Bỏ chốt" width="20" height="20" longdesc="Bỏ chốt" border = 0></A>
																	</TD>
																	<TD>&nbsp;</TD>
															<%	}
															}%>
															</TR>
														</TABLE>
													</TD>
												</TR>
									<%		m++; 
										}
									}
								%>
								<TR>
									<TD align="center" colspan="11" class="tbfooter">&nbsp;</TD>
								</TR>
								
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
</BODY>
</HTML>
<% 
if(vungRs != null)
	vungRs.close();
if(khuvucRs != null)
	khuvucRs.close();
if(khnvRs != null)
	khnvRs.close();
obj.closeDB(); %>
<%}
%>
