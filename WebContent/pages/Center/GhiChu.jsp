<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.center.beans.ghichu.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ int[] quyen = new  int[6];
		quyen = util.Getquyen("GhichuSvl", "",userId);
		
		System.out.println(quyen[0]);
		System.out.println(quyen[1]);
		System.out.println(quyen[2]);
		System.out.println(quyen[3]);	
		System.out.println(quyen[4]);%>

<% IGhichuList obj = (IGhichuList)session.getAttribute("obj"); %>
<% ResultSet ttRs = (ResultSet)obj.getTinhthanhRs(); %>
<% ResultSet qhRs = (ResultSet)obj.getQuanhuyenRs(); %>
<% ResultSet pxRs = (ResultSet)obj.getPhuongxaRs(); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("GhichuSvl","",nnId);	
	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TTC - Project</TITLE>

<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { 
      $("select:not(.notuseselect2)").select2({ width: 'resolve' });     
     });
</script>  
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{	
	//document.kvForm.VungMien.selectedIndex = 0;
    document.kvForm.tinhthanh.value = "";  
    document.kvForm.quanhuyen.value = ""; 
    document.kvForm.TrangThai.selectedIndex = 2;
    submitform();
}

function submitform()
{
	//alert("hello");
	document.forms['kvForm'].action.value = 'search';
	document.forms['kvForm'].submit();	
}

function newform()
{
	document.forms['kvForm'].action.value= 'new';
	document.forms['kvForm'].submit();
}

function thongbao()
{
	var tt = document.forms['kvForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['kvForm'].msg.value);
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

<form name="kvForm" method="post" action="../../GhichuSvl">  
<% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
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
								<%-- <TR>
								    <TD width="20%" class="plainlabel"><%=ChuyenNgu.get("Tỉnh thành",nnId) %></TD>
								      <TD width="80%" class="plainlabel"><SELECT name="tinhthanh" onChange = "submitform();">
								      	<option value='' ></option>
								        <% try{ while(ttRs.next()){ 
								    	if(ttRs.getString("pk_seq").equals(obj.getTinhthanhId())){ %>
								      		<option value='<%= ttRs.getString("pk_seq") %>' selected> <%= ttRs.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%= ttRs.getString("pk_seq") %>'> <%= ttRs.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>
								      </SELECT></TD>
								      
							      
								</TR>
								
								<TR>
									<TD width="20%" class="plainlabel"><%=ChuyenNgu.get("Quận huyện",nnId) %></TD>
								      <TD width="80%" class="plainlabel"><SELECT name="quanhuyen" onChange = "submitform();">
								      	<option value='' ></option>
								        <% try{ while(qhRs.next()){ 
								    	if(qhRs.getString("pk_seq").equals(obj.getQuanhuyenId())){ %>
								      		<option value='<%= qhRs.getString("pk_seq") %>' selected> <%= qhRs.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%= qhRs.getString("pk_seq") %>'> <%= qhRs.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>
								      </SELECT></TD>
								</TR> --%>
								
								  <TR>
								    <TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId) %> </TD>
								    <TD class="plainlabel"><SELECT name="TrangThai" onChange = "submitform();" >
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
											
                                    </SELECT></TD>
						      </TR>
							    
							    <TR>
									
									<TD colspan="2" class="plainlabel">
									<a class="button2" href="javascript:clearform()"> 
									<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
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
							<LEGEND class="legendtitle">&nbsp;Ghi chú &nbsp;&nbsp;
							<%if(quyen[0]!=0){ %>
									<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> </a>                            
				<%} %>
						</LEGEND>
	
						    <TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
								 	<TH width="8%"><%=ChuyenNgu.get("Mã hệ thống",nnId) %> </TH>
									<TH width="13%"><%=ChuyenNgu.get("Diễn giải",nnId) %> </TH>
									<%-- <TH width="13%"><%=ChuyenNgu.get("Tỉnh thành",nnId) %> </TH>
									<TH width="13%"><%=ChuyenNgu.get("Quận huyện",nnId) %> </TH> --%>
									<TH width="8%"><%=ChuyenNgu.get("Trạng thái",nnId) %> </TH>
									<TH width="6%"><%=ChuyenNgu.get("Ngày tạo",nnId) %> </TH>
									<TH width="10%"><%=ChuyenNgu.get("Người tạo",nnId) %> </TH>
									<TH width="6%"><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
									<TH width="10%"><%=ChuyenNgu.get("Người sửa",nnId) %> </TH>
									<TH width="6%" align="center">&nbsp;<%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
								</TR>
							
								<% 
								if(pxRs != null)
								{
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (pxRs.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><div align="center"><%= pxRs.getString("PK_SEQ") %></div></TD>
												<TD align="center"><div align="center"><%= pxRs.getString("TENPX") %></div></TD>
											    <%-- <TD align="center"><div align="center"><%= pxRs.getString("TENTT") %></div></TD>
											    <TD align="center"><div align="center"><%= pxRs.getString("TENQH") %></div></TD> --%>
											                             
												<% if (pxRs.getString("TRANGTHAI").equals("1")){ %>
													<TD align="center"><%=ChuyenNgu.get("Hoạt động",nnId) %> </TD>
												<%}else{%>
													<TD align="center"><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></TD>
												<%}%>
												<TD align="center"><%= pxRs.getString("NGAYTAO") %></TD>
												<TD align="center"><%= pxRs.getString("NGUOITAO") %></TD>
												<TD align="center"><%= pxRs.getString("NGAYSUA") %></TD>
												<TD align="center"><%= pxRs.getString("NGUOISUA") %></TD>
										 	
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													<%if(quyen[2]!=0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") +
 														"GhichuUpdateSvl?userId="+userId+"&update="+pxRs.getString("PK_SEQ")+"")%>">
 															<img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0>
 														</A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
													<%if(quyen[1]!=0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") +
 														"GhichuSvl?userId="+userId+"&delete="+pxRs.getString("PK_SEQ")+"")%>">
 															<img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 
 																onclick="if(!confirm('Bạn có muốn xóa thông tin ghi chú này ?')) return false;">
 															</A>
														<%} %>
														</TD>
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }}%>
								
								<tr class="tbfooter">
									<TD align="center" valign="middle" colspan="15" class="tbfooter"> <% obj.setNextSplittings(); %> 
										<script type="text/javascript" src="../scripts/phanTrang.js"></script> 
										<input type="hidden" name="crrApprSplitting" value="<%=obj.getCrrApprSplitting()%>"> 
										<input type="hidden" name="nxtApprSplitting" value="<%=obj.getNxtApprSplitting()%>"> 
										<% if (obj.getNxtApprSplitting() > 1) { %> 
											<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" 
												onclick="View(document.forms[0].name, 1, 'view')"> &nbsp; 
										<%} else { %> 
											<img alt="Trang Dau" src="../images/first.gif"> &nbsp; 
										<% } 
										if (obj.getNxtApprSplitting() > 1) { %> 
											<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;"
												onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp; 
										<% } else { %> 
											<img alt="Trang Truoc" src="../images/prev_d.gif"> &nbsp; 
										<% }
										
										int[] listPage = obj.getNextSplittings();
										
										for (int i = 0; i < listPage.length; i++) {
											if (listPage[i] == obj.getNxtApprSplitting()) { %> 
											<a style="color: white;"><%=listPage[i]%>/ <%=obj.getTheLastSplitting()%></a>
										<% } else { %> 
											<a href="javascript:View(document.forms[0].name, <%=listPage[i]%>, 'view')"><%=listPage[i]%></a>
										<% } %> 
										<input type="hidden" name="list" value="<%=listPage[i]%>" /> &nbsp; 
										<% } 
										
										if (obj.getNxtApprSplitting() < obj.getTheLastSplitting()) { %> 
											&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;"
														onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp; 
										<% } else {  %> 
											&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif"> &nbsp; 
										<% }  
										
										if (obj.getNxtApprSplitting() == obj.getTheLastSplitting()) { %> 
												<img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; 
										<% } else { %> 
												<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" 
													onclick="View(document.forms[0].name, -1, 'view')"> &nbsp; 
										<% } %>
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
	if(ttRs != null ){ttRs.close(); ttRs = null; }
	if(qhRs != null ){qhRs.close(); qhRs = null; }
	if(pxRs != null ){pxRs.close(); pxRs = null; }
	
	obj = null; session.setAttribute("obj", null);

}%>