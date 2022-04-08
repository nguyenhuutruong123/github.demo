<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.phieuthuhoi.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>


<% IPhieuthuhoiList obj = (IPhieuthuhoiList)session.getAttribute("obj"); %>
<% List<IPhieuthuhoi> pthList = (List<IPhieuthuhoi>)obj.getPthList(); %>
<% ResultSet nvgn = (ResultSet)obj.getNhanvienGN(); %>
<% obj.setNextSplittings(); 
	  
	int[] quyen = new  int[6];
	quyen = util.Getquyen("PhieuthuhoiSvl","", userId);
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 
 } 
String url = util.getChuyenNguUrl("PhieuthuhoiSvl","",nnId); 
 %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>

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
	   document.forms["pthForm"].submit();
	}
	
	function processing(id,chuoi){
		
		 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";	
		 document.getElementById(id).href = chuoi; 
	}

	function clearform(){
		document.forms["pthForm"].nvgnTen.value="";
		document.forms["pthForm"].trangthai.value="";  
		document.forms["pthForm"].tungay.value="";  
		document.forms["pthForm"].action.value="clear";  
		submitform();
	}
	function thongbao()
	{
		if(document.getElementById("msg").value != '')
			alert(document.getElementById("msg").value);
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

<form name="pthForm" method="post" action="../../PhieuthuhoiSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>


<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >


<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="nppTen" value='<%= obj.getNppTen() %>'>
<input type="hidden" name="nvgn_Ten" value=''>
<input type="hidden" name="action" value=''>
<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
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
   							<TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %></TD>
   							<TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId)%> <%= obj.getNppTen() %> </TD>
						</tr>
					</table>
				</TD>
		  	</TR>
		</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
			<TR>
				<TD >
					<FIELDSET><LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId)%> &nbsp;</LEGEND>
					<TABLE  width="100%" cellspacing="0" cellpadding="3">
						<TR class="tblightrow">
							<TD  class="plainlabel"><%=ChuyenNgu.get("Nhân viên giao nhận",nnId)%> </TD>
							<TD colspan="3" class="plainlabel"> 
				 			<SELECT name="nvgnTen" id="nvgnList" onChange = "submitform();">
					 			 <option value="">&nbsp;</option>
								  <% if(nvgn != null){
									  try{ while(nvgn.next()){ 
						    			if(nvgn.getString("nvgnId").equals(obj.getNvgnId())){ %>
						      				<option value='<%=nvgn.getString("nvgnId")%>' selected><%=nvgn.getString("nvgnTen") %></option>
						      			<%}else{ %>
						     				<option value='<%=nvgn.getString("nvgnId")%>'><%=nvgn.getString("nvgnTen") %></option>
						     			<%}} nvgn.close(); }catch(java.sql.SQLException e){} }%>	 
                               			</SELECT></TD>
						</TR>
						<TR>
							<TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId)%> </TD>
							<TD width="81%" colspan="3" class="plainlabel">
								<SELECT name ="trangthai" onChange = "submitform();">                                  
                                   <% if (obj.getTrangthai().equals("1")){%>
                                         <option value="1" selected><%=ChuyenNgu.get("Đã chốt",nnId)%></option>
                                         <option value="0"><%=ChuyenNgu.get("Chưa chốt",nnId)%></option>
                                         <option value=""> </option>                                        
                                   <%}else if(obj.getTrangthai().equals("0")) {%>
                                         <option value="0" selected><%=ChuyenNgu.get("Chưa chốt",nnId)%></option>
                                         <option value="1" ><%=ChuyenNgu.get("Đã chốt",nnId)%></option>
                                         <option value="" > </option>                                         
                                   <%}else{%>                                          
                                         <option value="" selected> </option>
                                         <option value="1" ><%=ChuyenNgu.get("Đã chốt",nnId)%></option>
                                         <option value="0" ><%=ChuyenNgu.get("Chưa chốt",nnId)%></option>
                                   <%}%>
                                </SELECT>
							</TD>
						</TR>
						<TR>
							<TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId)%></TD>
							<TD class="plainlabel">
								<TABLE cellpadding="0" cellspacing="0">
									<TR><TD>
									    <input class="days" type="text" name="tungay" value="<%= obj.getTungay() %>" size="11">
									</TD></TR>
								</TABLE>	
						</TR>
						<TR>
							<TD class="plainlabel" colspan="3">
							<a class="button2" href="javascript:submitform()">
								<img style="top: -4px;" src="../images/Search30.png" alt=""><%=ChuyenNgu.get("Tìm kiếm",nnId)%> </a>&nbsp;&nbsp;&nbsp;&nbsp;	
							<a class="button2" href="javascript:clearform()">
								<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId)%></a>&nbsp;&nbsp;&nbsp;&nbsp;	
						</TR>
					</TABLE>

				  </FIELDSET>
				</TD>	
			</TR>
		</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle"><%=ChuyenNgu.get("Phiếu thu hồi",nnId)%>  </LEGEND>
					<TABLE class="" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="2">
									<TR class="tbheader">
											<TH ><%=ChuyenNgu.get("Mã phiếu thu",nnId)%></TH>
											<TH ><%=ChuyenNgu.get("Mã phiếu xuất",nnId)%></TH>
											<TH ><%=ChuyenNgu.get("Trạng thái",nnId)%></TH>
											<TH ><%=ChuyenNgu.get("Ngày tạo",nnId)%></TH>
											<TH align="left"><%=ChuyenNgu.get("Người tạo",nnId)%> </TH>
											<TH ><%=ChuyenNgu.get("Ngày sửa",nnId)%></TH>
											<TH align="left"><%=ChuyenNgu.get("Người sửa",nnId)%> </TH>
											<TH ><%=ChuyenNgu.get("Tac vu",nnId)%></TH>
									</TR>
									<% 
									IPhieuthuhoi pth = null;
									int size = pthList.size();
									int m = 0;
									while (m < size){
										pth = pthList.get(m);
										if (m % 2 != 0) {%>						
											<TR class= "tblightrow">
										<%} else {%>
											<TR class= "tbdarkrow">
										<%}%>
												<TD align="center"><%= pth.getId() %></TD>                                   
												<TD align="center"><%= pth.getPhieuxuatkho() %></TD> 
												 <% if (pth.getTrangthai().equals("1")){ %>
                                                    <TD align="center"><TH ><%=ChuyenNgu.get("Da chot",nnId)%></TH></TD>
                                                <%}else{%>
                                                    <TD align="center"><TH ><%=ChuyenNgu.get("Chua chot",nnId)%></TH></TD>
                                                <%}%>                 
												<TD align="center"><%= pth.getNgaytao()%></TD>
												<TD align="left"><%= pth.getNguoitao()%></TD>
												<TD align="center"><%= pth.getNgaysua()%></TD>
												<TD align="left"><%= pth.getNguoisua()%></TD>
												<% if(pth.getTrangthai().equals("0")){ %>
												<TD align="center" valign="middle">
													<%if(quyen[3]!= 0){ %>
													<A href = "../../PhieuthuhoiPdfSvl?userId=<%=userId%>&display=<%= pth.getId() %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
													<%} %>
													
													<%if(quyen[4]!= 0){ %>
													<A id=<%="chotpthid"+pth.getId() %>  href = "">
													<img src="../images/Chot.png" alt="Chot phieu xuat"  width="20" height="20" longdesc="Chot phieu xuat" 
													   border = 0 onclick="if(!confirm('Bạn Muốn Chốt Phiếu Thu Hồi Này ?')) { return false; }else{ processing('<%="chotpthid"+pth.getId() %>' , '../../PhieuthuhoiSvl?userId=<%=userId%>&chotphieu=<%= pth.getId() %>&nppId=<%= obj.getNppId()%>'); } " >
													 </A>	
													 <%} %>												
												</TD>
												<%}else{ %>
													<TD align="center" valign="middle">
													<%if(quyen[3]!= 0){ %>
													<A href = "../../PhieuthuhoiPdfSvl?userId=<%=userId %>&display=<%= pth.getId() %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>											
													<%} %>
												</TD>
												<%} %>
											</TR>
								<% m++; }%>
								
					<tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
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
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
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
		<!--end body Dossier--></TD>
</TR>
</TABLE>
</form>
</BODY>
</html>

<%

	
	try{
		if(nvgn != null){ nvgn.close();  nvgn = null;}
		if(pthList != null){ pthList.clear(); pthList = null;}
		
		if(obj != null){
			obj.DBclose(); 
			obj =  null;
		}
				
		util=null;
		session.setAttribute("obj",null);
	}
	catch (Exception e) {}

	}
%>