<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.tuyenbanhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% ITuyenbanhangList obj = (ITuyenbanhangList)session.getAttribute("obj"); %>
<% List<ITuyenbanhang> tbhlist = (List<ITuyenbanhang>)obj.getTbhList(); %>
<% ResultSet ddkd = (ResultSet)obj.getDdkd(); 
ResultSet nppRs = obj.getNppRs_ho();
String view = obj.getView();
	int[] quyen = new  int[6];
	if (view != null && view.equals("TT")) {
		quyen = util.Getquyen("TuyenbanhangSvl","&view=TT", userId);
	}
	else {System.out.println("vao day quyen");
		quyen = util.Getquyen("TuyenbanhangSvl","", userId);
	}
%>

<% obj.setNextSplittings(); %>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 
 } 
String url = util.getChuyenNguUrl("TuyenbanhangSvl","",nnId); 
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
    document.tbhForm.tbhTen.value = "";      
    document.tbhForm.ddkdTen.selectedIndex = 0;
    submitform();
}

function submitform()
{
	document.tbhForm.action.value= 'search';
	document.forms['tbhForm'].submit();
}

function newform()
{
	document.tbhForm.action.value= 'new';
	document.forms['tbhForm'].submit();
}
function thongbao()
{
	if(document.getElementById("msg").value != '')
		alert(document.getElementById("msg").value);
}
</SCRIPT>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>	

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

<form name="tbhForm" method="post" action="../../TuyenbanhangSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>

<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="view" id="view" value='<%= view %>'>
		<script type="text/javascript">
	thongbao();
</script>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kinh doanh &gt; Kế hoạch bán hàng
   							<TD colspan="2" align="right" style="padding-right : 5px; " class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId)%> <%if (view != null && view.equals("TT")) {%> <%= userTen %> <%} else { %> <%= obj.getNppTen() %> <%} %>  </TD>
						  </tr>
						</table>
		  		</TR>
			</TABLE>

			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId)%>&nbsp;</LEGEND>
						<TABLE  width="100%" cellspacing="0" cellpadding = "6">
							
							<% if (view != null && view.equals("TT")) {%>
							<TR>
								<TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId)%><FONT class="erroralert">*</FONT></TD>
								<TD width="81%" valign="middle" class="plainlabel">
								 <SELECT name="npp_fk" id="npp_fk" onChange="submitform();" class="select2" style="width: 200px;" >
								    	<option value=""></option>
								      <% try{while(nppRs.next()){ 
								    	if(obj.getNpp_fk_ho() != null && nppRs.getString("pk_seq").equals(obj.getNpp_fk_ho())) { %>
								      		<option value='<%=nppRs.getString("pk_seq")%>' selected><%=nppRs.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=nppRs.getString("pk_seq")%>'><%=nppRs.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){e.printStackTrace();} %>	  
                        				</SELECT>	
                        		</TD>
							</TR>
							<% } %>
							
							<TR>
								<TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Kế hoạch bán hàng",nnId)%> </TD>
								<TD width="81%" valign="middle" class="plainlabel">
									<TABLE cellpadding="0" cellspacing="0">
										<TR>
											<TD colspan="2">
												<input name="tbhTen" type="text" value="<%= obj.getTuyenbh() %>" size="40" onChange = "submitform();">
											</TD>											
										</TR>
									</TABLE>	  
							  </TD>
							</TR>
							<TR>
								<TD class="plainlabel"><%=ChuyenNgu.get("Nhân viên bán hàng",nnId)%> </TD>
								<TD class="plainlabel"><SELECT name="ddkdTen" onChange = "submitform();" >
								  <option value=""></option>
								  <% 
								  try{ if (ddkd!= null) {
									  	while(ddkd.next()){ 
								    	if(obj.getDdkdId() != null && ddkd.getString("ddkdId").equals(obj.getDdkdId())){ %>
								      		<option value='<%=ddkd.getString("ddkdId") %>' selected><%=ddkd.getString("ddkdTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=ddkd.getString("ddkdId") %>'><%=ddkd.getString("ddkdTen") %></option>
								     	<%}}}}catch(java.sql.SQLException e){}
								   %>
							    </SELECT></TD>
							</TR>
							<TR>
							    <TD class="plainlabel" colspan="4" >
							    
                               <a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId)%></a>&nbsp;&nbsp;&nbsp;&nbsp;	
                               

                                </TD>
							</TR>
						</TABLE>
						</FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			<TABLE width = "100%" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="100%">
						<FIELDSET>
						<LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Kế hoạch bán hàng",nnId)%> &nbsp;&nbsp;&nbsp;
							<%if(quyen[0]!= 0){ %>
							<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId)%> </a> 
    						<%} %>                           
						</LEGEND>
						<TABLE class="" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="98%">
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="2">
										<TR class="tbheader">
											<TH width=""><%=ChuyenNgu.get("Kế hoạch bán hàng",nnId)%> </TH>
											<TH width=""><%=ChuyenNgu.get("Nhân viên bán hàng",nnId)%></TH>
											<TH width=""><%=ChuyenNgu.get("Ngày tạo",nnId)%></TH>
											<TH width=""><%=ChuyenNgu.get("Người tạo",nnId)%></TH>
											<TH width=""><%=ChuyenNgu.get("Ngày sửa",nnId)%> </TH>
											<TH width=""><%=ChuyenNgu.get("Người sửa",nnId)%> </TH>
											<TH width=""><%=ChuyenNgu.get("Tác vụ",nnId)%></TH>
										</TR>
	
								<% 
									ITuyenbanhang tbh = null;
									int size = tbhlist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										tbh = tbhlist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="left"><div align="left"><%= tbh.getDiengiai() %></div></TD>                                   
												<TD><div align="center"><%= tbh.getDdkd() %></div></TD>
												<TD align="center"><%= tbh.getNgaytao() %></TD>
												<TD align="center"><%= tbh.getNguoitao() %></TD>
												<TD align="center"><%= tbh.getNgaysua() %></TD>
												<TD align="center"><%= tbh.getNguoisua() %></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
														<%if(quyen[2]!= 0){ 
														if (view != null && view.equals("TT")) { %>
														<%-- <A href = "../../TuyenbanhangUpdateSvl?userId=<%=userId%>&update=<%= tbh.getId()%>&view=<%=view%>&npp_fk=<%=obj.getNpp_fk_ho()%>">
														 --%>
														<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "TuyenbanhangUpdateSvl?userId=" + userId+ "&update="+ tbh.getId()+"&view="+view)%>">
														<img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
														<%} else {%>
														<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "TuyenbanhangUpdateSvl?userId=" + userId+ "&update="+ tbh.getId())%>">
														<img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<% } } %>
													</TD>
														<%-- <TD>&nbsp;
														<%if(quyen[0]!= 0){ %>
															<A href="../../TuyenbanhangMoveSvl?userId=<%=userId%>&move=<%= tbh.getId() %>" title="Chuyen Tuyen"><img src="../images/convert.gif" alt="Chuyen Tuyen" title="Chuyển tuyến" width="20" height="20" longdesc="Chuyen Tuyen" border = 0></A>
														<%} %>
														</TD> --%>
													<TD>&nbsp;
														<%if(quyen[1]!= 0){ %>
													    <%-- <A href = "../../TuyenbanhangSvl?userId=<%=userId%>&delete=<%= tbh.getId() %>"> --%>
													    <A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "TuyenbanhangSvl?userId=" + userId+ "&delete="+ tbh.getId()+ "&view="+view)%>">
													    <img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa Kế hoạch bán hàng này?')) return false;"></A>
														<%} %>
													</TD>
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }%>
							
<tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('tbhForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('tbhForm', eval(tbhForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
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
													<a href="javascript:View('tbhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('tbhForm', eval(tbhForm.nxtApprSplitting.value) + 1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('tbhForm', -1, 'view')"> &nbsp;
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
			</TBODY>
		</TABLE>

	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<% 

	try{
	
		if(ddkd != null){ ddkd.close(); ddkd = null;}
		if(tbhlist!=null){ tbhlist.clear(); tbhlist = null;}
		
		if(obj != null){
			obj.DBclose();
			obj = null;}	
		
		session.setAttribute("obj",null);
	}
	catch (Exception e) {}
	
%>
<%}%>