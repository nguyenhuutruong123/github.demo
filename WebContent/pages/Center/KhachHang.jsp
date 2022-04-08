<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.beans.khachhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	System.out.println(userTen);
	System.out.println(sum);

	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IKhachhangList obj = (IKhachhangList)session.getAttribute("obj");  %>
<%ResultSet khlist = obj.getKhList(); %>
<% ResultSet hch = (ResultSet)obj.getHangcuahang(); %>
<% ResultSet kbh = (ResultSet)obj.getKenhbanhang();  %>
<% ResultSet vtch = (ResultSet)obj.getVitricuahang();  %>
<% ResultSet lch = (ResultSet)obj.getLoaicuahang(); %>
<% ResultSet npp =  (ResultSet) obj.getNhaphanphoi(); %>
<% ResultSet tp = (ResultSet)obj.getTp() ;  %>
<% ResultSet qh = (ResultSet)obj.getQh() ;
ResultSet Nvbh = (ResultSet)obj.getNvbh() ;  
ResultSet vung = (ResultSet)obj.getvung() ; 
obj.setNextSplittings(); 
int[] quyen = new  int[6];
quyen = util.Getquyen("KhachhangTTSvl","", userId);
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("KhachhangTTSvl","&isMT=0",nnId);	
 %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
 	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
 	   		
 	   		<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">


<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"type="text/javascript"></script>
 	   		
 	   		
 	   		<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
    </script>
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
	document.khForm.tungay.value="";
	document.khForm.denngay.value="";
	document.khForm.khTen.value = "";
	document.khForm.hchTen.selectedIndex = 0;
	document.khForm.kbhTen.selectedIndex = 0;
	document.khForm.vtchTen.selectedIndex = 0;
	document.khForm.lchTen.selectedIndex = 0;
	document.khForm.NhaPhanPhoi.selectedIndex = 0;
	document.khForm.nvbhid.selectedIndex=0;
	document.khForm.vungId.selectedIndex=0;
	document.khForm.tpId.value = "";
	document.khForm.qhId.value = "";
	submitform();
}

function submitform()
{
	document.forms['khForm'].action.value= 'search';
	document.forms['khForm'].submit();
}

function newform()
{
	document.forms['khForm'].action.value= 'new';
	document.forms['khForm'].submit();
}

function xuatExcel()
{
	document.forms['khForm'].action.value= 'excel';
	document.forms['khForm'].submit();
	
}
function thongbao()
{   var tt = document.forms['khForm'].msg.value;
	if(tt.length>0)
    	alert(document.forms['khForm'].msg.value);
	
	document.forms['khForm'].msg.value= '';
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

<form name="khForm" method="post" action="../../KhachhangTTSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg()%>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
<INPUT name="isMT" type="hidden" value='<%= obj.getIsMT() %>' size="30">
<script language="javascript" type="text/javascript">
    thongbao();
</script>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" 	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  	<tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation"><%=" "+url %>
							 </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %> &nbsp;</TD>
							</tr>
						</table>
					</TD>
		  		</TR>	
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</LEGEND>
							<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
									<TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
									<td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10"  onChange="submitform();"/>
			                    	<TD class="plainlabel" ><%=ChuyenNgu.get("Đến ngày",nnId) %></TD>
							      <td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onChange="submitform();"/>
			                    	</td>
								</TR>
								
								<TR>
									<TD width="17%" class="plainlabel"><%=ChuyenNgu.get("Mã / Tên khách hàng",nnId) %></TD>
								    <TD width="29%" class="plainlabel">
											<INPUT name="khTen" type="text" value="<%= obj.getTen() %>" size="40" onChange = "submitform();">
								  </TD>
									<TD width="16%" class="plainlabel"><%=ChuyenNgu.get("Hạng khách hàng",nnId) %></TD>
 
									<TD width="38%" class="plainlabel">
										<SELECT name="hchTen" onChange = "submitform();">
										  <option value=""> </option>
										  <% try{ while(hch.next()){ 
								    			if(hch.getString("hchId").equals(obj.getHchId())){ %>
								      				<option value='<%=hch.getString("hchId")%>' selected><%= hch.getString("hchTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=hch.getString("hchId")%>'><%= hch.getString("hchTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>	 
                                        </SELECT>
								  </TD>
								</TR>
								<TR>
								  	<TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId) %></TD>
								  	<TD  class="plainlabel"><SELECT name="kbhTen" onChange = "submitform();">
								    	<option value=""> </option>
									    <% try{ while(kbh.next()){ 
								    			if(kbh.getString("kbhId").equals(obj.getKbhId())){ %>
								      				<option value='<%=kbh.getString("kbhId")%>' selected><%=kbh.getString("kbhTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=kbh.getString("kbhId")%>'><%=kbh.getString("kbhTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
									  	</SELECT>
									</TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Vị trí",nnId) %></TD>
	                                <TD  class="plainlabel"><SELECT name="vtchTen" onChange = "submitform();">
    		                            <option value=""> </option>
            		                    <% try{ while(vtch.next()){ 
								    			if(vtch.getString("vtchId").equals(obj.getVtchId())){ %>
								      				<option value='<%=vtch.getString("vtchId")%>' selected><%=vtch.getString("vtchTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=vtch.getString("vtchId")%>'><%=vtch.getString("vtchTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
                                        </SELECT></TD>
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Loại khách hàng",nnId) %></TD>
								  	<TD  class="plainlabel"><SELECT name="lchTen"  onChange = "submitform();">
								    	<option value=""> </option>
								    	<% try{ while(lch.next()){ 
								    			if(lch.getString("lchId").equals(obj.getLchId())){ %>
								      				<option value='<%=lch.getString("lchId")%>' selected><%=lch.getString("lchTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=lch.getString("lchId")%>'><%=lch.getString("lchTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
										</SELECT>
									</TD>
									
																										
								<TD width="18%" class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
									<TD width="33%" class="plainlabel">
									<SELECT name="NhaPhanPhoi" id="NhaPhanPhoi" onChange = "submitform()">
									<option value=""> </option>
									<% if(npp != null){
										  try
										  { 
											  String optionGroup = "";
											  String optionName = "";
											  int i = 0;
											  
											  while(npp.next())
											  { 
												 if(i == 0)
												 {
													 optionGroup = npp.getString("kvTen");
													 optionName = npp.getString("kvTen");
													 
													 %>
													 
													 <optgroup label="<%= optionName %>" >
												 <% }
												 
												 optionGroup = npp.getString("kvTen");
												 if(optionGroup.trim().equals(optionName.trim()))
												 { %>
													 <% if(npp.getString("nppId").equals(obj.getNppId())) {%>
													 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 else
												 {
													 %>
													</optgroup>
													<% optionName = optionGroup; %>
													<optgroup label="<%= optionName %>" >
													<% if(npp.getString("nppId").equals(obj.getNppId())) {%>
													 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 i++;
								     	 	  } 
											  %>
											  	</optgroup>
											  <%npp.close(); 
								     	 }catch(java.sql.SQLException e){}}%>	  
                                	</SELECT>
								</TD>
								</TR>
								
								<TR>
							   	 <TD class="plainlabel"><%=ChuyenNgu.get("Tỉnh /Thành phố",nnId) %></TD>
								 <TD class="plainlabel"><SELECT name="tpId" id="TP" onChange="submitform();">
								    	<option value=""></option>
								      <% try{while(tp.next()){ 
								    	if(tp.getString("tpId").equals(obj.getTpId())){ %>
								      		<option value='<%=tp.getString("tpId")%>' selected><%=tp.getString("tpTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=tp.getString("tpId")%>'><%=tp.getString("tpTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>	</TD>

							   	 <TD class="plainlabel"><%=ChuyenNgu.get("Quận/Huyện",nnId) %></TD>
								 <TD class="plainlabel"><SELECT name="qhId" id="QH" onChange="submitform();" >
								    <option value=""></option>
								      <%if(qh != null){ 
								      		try{while(qh.next()){ 
								    			if(qh.getString("qhId").equals(obj.getQhId())){ %>
								      				<option value='<%=qh.getString("qhId")%>' selected><%=qh.getString("qhTen") %></option>
								      		 <%}else{ %>
								     				<option value='<%=qh.getString("qhId")%>'><%=qh.getString("qhTen") %></option>
								     		<%}}}catch(java.sql.SQLException e){} 
								     		
								      }	%>
								     		  
                        				</SELECT>	</TD>
                        			
						  </TR>
								<TR>
								 <TD class="plainlabel"><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></TD>
								 <TD class="plainlabel" ><SELECT name="nvbhid" id="nvbhid" onChange="submitform();"  >
								    <option value=""></option>
								      <%if(Nvbh != null){ 
								      		try{while(Nvbh.next()){ 
								    			if(Nvbh.getString("Pk_seq").equals(obj.getNvbhId() ) ){ %>
								      				<option value='<%=Nvbh.getString("Pk_seq")%>' selected><%=Nvbh.getString("ten") %></option>
								      		 <%}else{ %>
								     				<option value='<%=Nvbh.getString("Pk_seq")%>'><%=Nvbh.getString("Ten") %></option>
								     		<%}}}catch(java.sql.SQLException e){} 
								     		
								      }	%>  
                        				</SELECT>	</TD>
                        			<TD class="plainlabel"><%=ChuyenNgu.get("Vùng/Miền",nnId) %></TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId"  style ="width:200px" onChange = "submitform();">
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
								    <TD class="plainlabel" colspan="4">
								    	<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
										<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a> &nbsp;&nbsp;&nbsp;&nbsp;	    
								    <!--  
                                      <INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();"> 
                                     --> </TD>
								</TR>
							</TABLE>
					  </FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellspacing="0" cellpadding="2">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Khách hàng &nbsp;&nbsp;&nbsp;
					<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> </a>                            
					
					<!-- <INPUT name="new" type="button" value="Tao moi" onClick="newform();"> -->
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="9%"><%=ChuyenNgu.get("Mã khách hàng",nnId) %></TH>
									<TH width="10%"><%=ChuyenNgu.get("Tên khách hàng",nnId) %></TH>
									<TH width="6%"><%=ChuyenNgu.get("Trạng thái",nnId) %></TH>
									<%if(!obj.getIsMT().equals("1")){ %>
									
									<TH width="13%"><%=ChuyenNgu.get("Nhà phân phố",nnId) %>i</TH>
									<%} %>
									<TH width="15%"><%=ChuyenNgu.get("Địa chỉ",nnId) %></TH>									
									<!-- <TH width="9%">Tỉnh thành</TH> 
									<TH width="9%">Quận huyện</TH> -->
									
									<TH width="9%"><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
									<TH width="9%"><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
									<TH width="9%"><%=ChuyenNgu.get("Người tạo",nnId) %></TH>
									<TH width="9%"><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
									<TH width="7%"><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
								</TR>
								
						<%  
							if(khlist != null)
							{
								int m = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								while (khlist.next()){
									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>
											<TD align="left"><div align="center"><%=khlist.getString("smartid")%></div></TD>                                   
											<TD><div align="left"><%=khlist.getString("khten")%></div></TD>
											<%
											if (khlist.getString("trangthai").equals("1")){ %>
												<TD align="center"><%=ChuyenNgu.get("Hoạt động",nnId) %></TD>
											<%}else{%>
												<TD align="center"><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></TD>
											<%}%>
												<%if(!obj.getIsMT().equals("1")){ %>
											<TD align="center"><%=khlist.getString("nppten")%></TD>
											<%} %>
											<TD align="center"><%=khlist.getString("diachi")%></TD>
											<%-- 	<TD align="center"><%=khlist.getString("tinhthanh")%></TD>
											<TD align="center"><%=khlist.getString("quanhuyen")%></TD>
											 --%>
											<TD align="center"><%=khlist.getString("ngaytao")%></TD>
											<TD align="center"><%=khlist.getString("ngaysua")%></TD>
										<% if(khlist.getString("ddkdtao").length() > 0)
											{
												
												%>
												<TD align="center"><%=khlist.getString("ddkdtao")%></TD>
												<%}else{ %>
												<TD align="center"><%=khlist.getString("nguoitao")%></TD>
											<% }%>
										<% if(khlist.getString("ddkdsua").length() > 0 && khlist.getString("ispdasua").equals("1"))
											{
												
												%>
												<TD align="center"><%=khlist.getString("ddkdsua")%></TD>
												<%}else{ %>
													<TD align="center"><%=khlist.getString("nguoisua")%></TD>
											<% }%>
										
											<TD align="center">
											<TABLE border = 0 cellpadding="0" cellspacing="0">
												<TR><TD>
													<A href = "../../KhachhangUpdateTTSvl?userId=<%=userId%>&update=<%=khlist.getString("khid")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
												</TD>
												<TD>&nbsp;</TD>
												<TD>
													<A href = "../../KhachhangTTSvl?userId=<%=userId%>&delete=<%=khlist.getString("khid")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa khách hàng" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Ban Muon Xoa Khach Hang Nay?')) return false;"></A></TD>

												<%if(khlist.getString("anhcuahang").length() > 0){ %>
												<TD>
													<A href = "../../KhachhangTTSvl?userId=<%=userId%>&deletehinh=<%=khlist.getString("khid")%>"><img src="../images/Delete_Icon.png" alt="Xoa" title="Xóa hình đại diện" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa hình đại diện khách hàng này ?')) return false;"></A></TD>
													<%} %>
												</TR>
												</TABLE>
											</TD>
										</TR>
								<%m++; }}%>
								
										 <tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('khForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('khForm', eval(khForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('khForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('khForm', eval(khForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('khForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr>  							</TABLE>
		
							</TABLE>
							</TD>
						</TR>
					</TABLE>
					</FIELDSET>
					</TD>
				</TR>
			</TBODY>
		</TABLE>

	</TR>
</TABLE>
</form>
</body>  
</HTML>

<% 	

	try{
		
		if(hch != null){ hch.close();  hch = null;}
		if(kbh != null){ kbh.close();  kbh = null; }
		if(lch != null){ lch.close();  lch = null;}
		if(vtch!=null){ vtch.close();  vtch = null;}
		if(npp!=null){ npp.close();  npp = null;}
		if(tp!=null){ tp.close();  tp = null;}
		if(qh!=null){ qh.close();  qh = null;}
		if(obj != null){
			obj.DBclose();
			obj = null;
		}		
		
		session.setAttribute("obj",null);
	
	}
	catch (SQLException e) {}
	

%>
<%}%>