<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.beans.giamsatbanhang.IGiamsatbanhang" %>
<%@ page  import = "geso.dms.center.beans.giamsatbanhang.IGiamsatbanhangList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{int[] quyen = new  int[5];
	quyen = util.Getquyen("GiamsatbanhangSvl","",userId);
		
	%>
	
<% IGiamsatbanhangList obj = (IGiamsatbanhangList)session.getAttribute("obj"); %>
<% List<IGiamsatbanhang> gsbhlist = (List<IGiamsatbanhang>)obj.getGsbhList(); %>
<% ResultSet kbh = (ResultSet)obj.getKenhbanhang();  %>
<% obj.setNextSplittings();
ResultSet asmRs = (ResultSet) obj.getAsmRs();

%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 
 } 
String url = util.getChuyenNguUrl("GiamsatbanhangSvl","",nnId); 
 %>
<input type="hidden" name="ngonnguId" value='<%=nnId%>'>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
	document.gsbhForm.MaGSBH.value = "";   
	document.gsbhForm.asmId.value = "";   
    document.gsbhForm.TenGSBH.value = "";    
    document.gsbhForm.SoDienThoai.value = "";
    document.gsbhForm.KenhBanHang.selectedIndex = 0;
	document.gsbhForm.TrangThai.selectedIndex = 0;
	document.gsbhForm.loaigiamsat.selectedIndex = 0;
	
	submitform();
}

function submitform()
{
	document.forms['gsbhForm'].action.value= 'search';
	document.forms['gsbhForm'].submit();
}

function newform()
{
	document.forms['gsbhForm'].action.value= 'new';
	document.forms['gsbhForm'].submit();
}
function thongbao()
{var tt = document.forms['gsbhForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['gsbhForm'].msg.value);
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

<form name="gsbhForm" method="post" action="../../GiamsatbanhangSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value='<%=obj.getMsg()%>'>
<input type="hidden" name="ngonnguId" value='<%=nnId%>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
		<!--begin common dossier info---> <!--End common dossier info--->
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%=url %></TD>
   
						   <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng ",nnId)%> <%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" ><FIELDSET>
							<legend class="legendtitle" ><%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId)%> </legend>
							<div align="left">
						    </div>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<TR>
								      <TD  class="plainlabel"><%=ChuyenNgu.get("Mã",nnId)%> </TD>
								        <TD  class="plainlabel"><input type="text" name="MaGSBH" value="<%= obj.getSmartId() %>" onChange="submitform();"></TD>
								        <TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId)%> </TD>
								    <TD class="plainlabel" colspan = 2><SELECT name="KenhBanHang" onChange = "submitform();">
								    <option value=""></option> 
								      <% try{ while(kbh.next()){ 
								    	if(kbh.getString("kbhId").equals(obj.getKbhId())){ %>
								      		<option value='<%=kbh.getString("kbhId") %>' selected='selected'><%=kbh.getString("kbhTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=kbh.getString("kbhId") %>' ><%=kbh.getString("kbhTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>
								     	
                                       </SELECT></TD>
								</TR>
								<TR>
								      <TD  class="plainlabel"><%=ChuyenNgu.get("Tên",nnId)%> </TD>
								        <TD  class="plainlabel"><input type="text" name="TenGSBH" value="<%= obj.getTen() %>" onChange="submitform();"></TD>
								        <TD class="plainlabel"><%=ChuyenNgu.get("Loại",nnId)%> </TD>
								    <TD class="plainlabel"  colspan = 2><SELECT name="loaigiamsat" onChange = "submitform();" >
                                      <% 
                                      if (obj.getThuviec().equals("0")){ %>
								    	<option > </option>
								    	<option value="1"><%=ChuyenNgu.get("Thử việc",nnId)%></option>
								    	<option value="0" selected><%=ChuyenNgu.get("Nhân viên",nnId)%></option>
									<%}else{ 							
								  		if (obj.getThuviec().equals("1")){ %>
								    	<option > </option>
								    	<option value="1" selected><%=ChuyenNgu.get("Thử việc",nnId)%></option>
								    	<option value="0" ><%=ChuyenNgu.get("Nhân viên",nnId)%></option>
									<%}else{ %>
								    	<option  selected> </option>
								    	<option value="1" ><%=ChuyenNgu.get("Thử việc",nnId)%></option>
								    	<option value="0" ><%=ChuyenNgu.get("Nhân viên",nnId)%></option>
									<%}} %>
                                    </SELECT></TD>
								  </TR>
								  <TR>
								    <TD class="plainlabel"><%=ChuyenNgu.get("Số điện thoại",nnId)%> </TD>
								    <TD class="plainlabel"><input type="text" name="SoDienThoai" value="<%= obj.getSodienthoai() %>" onChange="submitform();"></TD>
								    <TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId)%></TD>
								    <TD class="plainlabel"  colspan = 2><SELECT name="TrangThai" onChange = "submitform();" >
                                      <% if (obj.getTrangthai().equals("0")){ %>
								    	<option > </option>
								    	<option value="1"><%=ChuyenNgu.get("Hoạt động",nnId)%></option>
								    	<option value="0" selected><%=ChuyenNgu.get("Ngưng hoạt động",nnId)%></option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>
								    	<option > </option>
								    	<option value="1" selected><%=ChuyenNgu.get("Hoạt động",nnId)%></option>
								    	<option value="0" ><%=ChuyenNgu.get("Ngưng hoạt động",nnId)%></option>
									<%}else{ %>
								    	<option selected> </option>
								    	<option value="1" ><%=ChuyenNgu.get("Hoạt động",nnId)%></option>
								    	<option value="0" ><%=ChuyenNgu.get("Ngưng hoạt động",nnId)%></option>
									<%}} %>
                                    </SELECT></TD>
						      </TR>
						      <tr>
						      <TD class="plainlabel"><%=ChuyenNgu.get("AMS",nnId)%> </TD>
								    <TD class="plainlabel" colspan = 4><SELECT name="asmId" onChange = "submitform();">
								    <option value=""></option> 
								      <% try{ while(asmRs.next()){ 
								    	if(asmRs.getString("pk_seq").equals(obj.getAsmId())){ %>
								      		<option value='<%=asmRs.getString("pk_seq") %>' selected='selected'><%=asmRs.getString("ten") %></option>
								      	<%}else{ %> 
								     		<option value='<%=asmRs.getString("pk_seq") %>' ><%=asmRs.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>
								     	
                                       </SELECT></TD>
						      </tr>
							   <TR>
									<TD class="plainlabel" colspan = 4>
									<a class="button2" href="javascript:clearform()">
									<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId)%> </a>&nbsp;&nbsp;&nbsp;&nbsp;	
									
                                     </TD>
									<TD class="plainlabel">&nbsp;</TD>										
								</TR>
							</TABLE>

							</FIELDSET>
							</TD>
						</TR>
					</TABLE>
					</TD>
				</TR>

				<TR>
					<TD width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Giám sát bán hàng",nnId)%> &nbsp;&nbsp;&nbsp;
							<%-- <%if(quyen[0]!=0){ %> --%>
							<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId)%> </a>    <%--   <%} %>  --%> 
						</LEGEND>
	
						    <TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="5%"><%=ChuyenNgu.get("Mã",nnId)%> </TH>
									<TH width="13%"><%=ChuyenNgu.get("Tên",nnId)%>  </TH>
									<TH width="7%"><%=ChuyenNgu.get("Số điện thoại",nnId)%> </TH>
									<TH width="9%"><%=ChuyenNgu.get("Nhà cung cấp",nnId)%></TH>
									<TH width="7%"><%=ChuyenNgu.get("Kênh bán hàng",nnId)%></TH>
									<TH width="9%"><%=ChuyenNgu.get("Loại",nnId)%></TH>
									<TH width="8%"><%=ChuyenNgu.get("Trạng thái",nnId)%></TH>
									<TH width="9%"><%=ChuyenNgu.get("Ngày tạo",nnId)%> </TH>
									<TH width="12%"><%=ChuyenNgu.get("Người tạo",nnId)%> </TH>
									<TH width="9%"><%=ChuyenNgu.get("Ngày sửa",nnId)%> </TH>
									<TH width="11%"><%=ChuyenNgu.get("Người sửa",nnId)%> </TH>
									<TH width="18%" >&nbsp;<%=ChuyenNgu.get("Tác vụ",nnId)%></TH>
								</TR>

								<% 
									//IGiamsatbanhang gsbh = null;
									//int size = gsbhlist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									ResultSet gsbhList = obj.getGsbhListRs();
									//while (m < size){
									//	gsbh = gsbhlist.get(m);
									if (gsbhList != null) {

										try {
											while (gsbhList.next()) {
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
												<%} else {%>
													<TR class= <%= darkrow%> >
												<%}%>
												<TD align="left"><div align="left"><%=gsbhList.getString("smartid") %></div></TD>
												<TD align="left"><div align="left"><%= gsbhList.getString("ten")%></div></TD> 
												<TD align="left"><div align="left"><%= gsbhList.getString("sodienthoai") %></div></TD> 
												<TD align="left"><div align="left"><%= gsbhList.getString("nccten") %></div></TD> 
												<TD align="left"><div align="left"><%= gsbhList.getString("kbhten") %></div></TD> 
												<% if(gsbhList.getString("tinhtrang") != null && gsbhList.getString("tinhtrang").equals("0")){ %>                             
												<TD align="center"><%=ChuyenNgu.get("Nhân viên",nnId)%></TD>
												<%} else {%>
												<TD align="center"><%=ChuyenNgu.get("Thử việc",nnId)%></TD>
												<%} %>     
												<% if(gsbhList.getString("trangthai") != null && gsbhList.getString("trangthai").equals("1")){ %>                             
												<TD align="center"><%=ChuyenNgu.get("Hoạt động",nnId)%></TD>
												<%} else {%>
												<TD align="center"><%=ChuyenNgu.get("Ngưng hoạt động",nnId)%></TD>
												<%} %>
												<TD align="center"><%= gsbhList.getString("ngaytao")%></TD>
												<TD align="center"><%= gsbhList.getString("nguoitao")%></TD>
												<TD align="center"><%= gsbhList.getString("ngaysua")%></TD>
												<TD align="center"><%= gsbhList.getString("nguoisua")%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR>
													
													<TD>
													<%if(quyen[2]!=0){ %>
														<A href = "
														../../RouterSvl?task=<%=Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "GiamsatbanhangUploadSvl?userId=" + userId + "&update=" + gsbhList.getString("pk_seq") ) %>
														"><img src="../images/plus32.png" alt="Thêm ảnh" title="Thêm ảnh" width="20" height="20" longdesc="Thêm ảnh" border = 0></A>
													<%} %>
													</TD>
													
													<TD>
													<%if(quyen[2]!=0){ %>
														<A href = "
														../../RouterSvl?task=<%=Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "GiamsatbanhangUpdateSvl?userId=" + userId + "&update=" + gsbhList.getString("pk_seq") ) %>
														"><img src="../images/Edit20.png" alt="Cap nhat" title="C?p nh?t" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													
													<TD><%if(quyen[1]!=0){ %>
														<A href = "
														
														../../RouterSvl?task=<%=Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "GiamsatbanhangSvl?userId=" + userId + "&delete=" + gsbhList.getString("pk_seq") ) %>
														
														"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa giám sát bán hàng này ?')) return false;"></A>
														<%} %>
														</TD>
														
													<TD>	
														<%if(quyen[3]!=0){ %>
														<A href = "
														
														../../RouterSvl?task=<%=Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "GiamsatbanhangUpdateSvl?userId=" + userId + "&display=" + gsbhList.getString("pk_seq") ) %>
														
														"><img src="../images/Display20.png" alt="Hien thi" title="Hi?n th?" width="20" height="20" longdesc="Hien thi" border = 0></A>&nbsp;
														<%} %>
													</TD>
																
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }} catch (Exception e) {e.printStackTrace();}}%>
								
									<tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('gsbhForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('gsbhForm', eval(gsbhForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
											
												 
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('gsbhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="<%=ChuyenNgu.get("Trang tiếp",nnId)%>" src="../images/next.gif" style="cursor: pointer;" onclick="View('gsbhForm', eval(gsbhForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="<%=ChuyenNgu.get("Trang tiếp",nnId)%>" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="<%=ChuyenNgu.get("Trang cuối",nnId)%>" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="<%=ChuyenNgu.get("Trang cuối",nnId)%>" src="../images/last.gif" style="cursor: pointer;" onclick="View('gsbhForm', -1, 'view')"> &nbsp;
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
	if(kbh != null){ kbh.close(); kbh = null ;} 
	if(gsbhlist != null){ gsbhlist.clear(); gsbhlist = null ;} 
	
	if(obj != null)
	{
		obj.DBClose();
		obj = null;
	}
	session.setAttribute("obj", null);
	
	}%>