<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.beans.duyettrasanpham.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<% IDuyettrasanphamList obj = (IDuyettrasanphamList)session.getAttribute("obj"); %>
<% ResultSet npp = (ResultSet)obj.getNppRs(); %>
<% ResultSet dtspList = (ResultSet)obj.getDspList(); %>

<% String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen"); 	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{
		int[] quyen = new  int[5];
		quyen = util.Getquyen("28",userId);
		
		System.out.println(quyen[0]);
		System.out.println(quyen[1]);
		System.out.println(quyen[2]);
		System.out.println(quyen[3]);	
		System.out.println(quyen[4]);
	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
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
    if(confirm("Ban co muon dang xuat?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function clearform()
 {
	 document.forms["dtsp"].tungay.value = "";
	 document.forms["dtsp"].denngay.value = "";
	 document.forms["dtsp"].nppTen.value = "";
	 document.forms["dtsp"].trangthai.value = "";
	// document.forms["dtsp"].action1.value="guilai";
	 document.forms["dtsp"].submit();
 }
 function newform()
 {
	 document.forms["dtsp"].action1.value="taomoi";
	 document.forms["dtsp"].submit();
 }
 function submitform()
 {   
    document.forms["dtsp"].submit();
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

<form name="dtsp" method="post" action="../../DuyettrasanphamSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="action1" value="1" >
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD align="left" class="tbnavigation">
					   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation"> &nbsp;Quản lý tồn kho > Duyệt hàng trả về NPP > Duyệt trả sản phẩm
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= userTen %>&nbsp;</tr>
						</TABLE>
					</TD>
		  		</TR>
				<TR>
				<TD >
				<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm</LEGEND>
							<TABLE width="100%" cellpadding="6" cellspacing="0">
								
								<TR>
									<TD width="21%" class="plainlabel">Nhà phân phối </TD>
									<TD class="plainlabel">
									<SELECT name="nppTen" onChange = "submitform();">
										  <option value=""> </option>
										  <% try{ while(npp.next()){ 
								    			if(npp.getString("nppId").equals(obj.getNppId())){ %>
								      				<option value='<%=npp.getString("nppId")%>' selected><%= npp.getString("nppTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=npp.getString("nppId")%>'><%= npp.getString("nppTen") %></option>
								     			<%}} npp.close(); }catch(java.sql.SQLException e){} %>	 
                                    </SELECT></TD>
							    </TR>
								
								<TR>
									<TD class="plainlabel">Trạng thái </TD>
									<TD class="plainlabel">
										<select name="trangthai" onChange="submitform();">
												<option value=""></option>
											    <% if (obj.getTrangthai().equals("1")){%>											    
											  	<option value="1" selected>Đang chờ duyệt</option>
											  	<option value="2">Đã hủy</option>
											  	<option value="3">Đã duyệt</option>
											  	
	
											<%}else if(obj.getTrangthai().equals("2")) { %>
																		
											  	<option value="1" >Đang chờ duyệt</option>
											  	<option value="2" selected>Đã hủy</option>											
											  	<option value="3" >Đã duyệt</option>
											  	
											<%} else if(obj.getTrangthai().equals("3")){ %>
						
												<option value="1" >Đang chờ duyệt</option>
												<option value="2" >Đã hủy</option>											  											
											  	<option value="3" selected>Đã duyệt</option>
											<% } else{ %>
								
												<option value="1" >Đang chờ duyệt</option>
												<option value="2" >Đã hủy</option>											  											
											  	<option value="3">Đã duyệt</option>
											<% } %>
									          </select>
									</TD>
							    </TR>
								
								<TR>
									<TD class="plainlabel" >Từ ngày</TD>
									<TD class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
											    <input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="11" onChange = "submitform();">
											</TD></TR>
										</TABLE>	
								</TR>
								<TR>
								  <TD class="plainlabel" >Đến ngày</TD>
							      <TD width="100%" class="plainlabel">
								  		<TABLE cellpadding="0" cellspacing="0">
										  	<TR><TD>
											  <input class="days" type="text" name="denngay" value="<%=obj.getDenngay() %>" size="11" onChange = "submitform();">
										  	</TD></TR>
										</TABLE>
							  	</TR>
								<TR>
								<TR>
									<TD class="plainlabel" colspan="3">
									<a class="button2" style="display:none;">
										<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;	
									<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
								</TR>
							</TABLE>
				  </FIELDSET>
							</TD>	
				</TR>

				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Duyệt trả đơn hàng &nbsp;&nbsp;&nbsp;
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="3">
								<TR class="tbheader">
									<TH width="10%" align="center">Mã đơn hàng</TH>
									<TH width="20%" align="center">Nhà phân phối</TH>
									<TH width="10%" align="center">Mã khách hàng</TH>
									<TH width="10%" align="center">Tên khách hàng</TH>
									<TH width="10%" align="center">Ngày nhập</TH>
									<TH width="10%" align="center">Trạng thái</TH>
									<TH width="10%" align="center">Ngày duyệt</TH>
									<TH width="10%" align="center">Người duyệt</TH>
									<TH width="10%" align="center">Tác vụ</TH>
								</TR>
								
								<% 
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(dtspList != null){
									try{
									while (dtspList.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												
										<TD align="center"><%= dtspList.getString("dhtvId") %></TD> 
										<TD align="left"><%= dtspList.getString("nppTen") %></TD>                                  
										<TD align="center"><%= dtspList.getString("khId") %></TD>
										<TD align="left"><%= dtspList.getString("khTen") %></TD>
										<TD align="center"><%= dtspList.getString("ngaynhap") %></TD>
	
										<%
										String trangthai = dtspList.getString("trangthai");
										if (trangthai.equals("1")){ %>
											<TD align="left"><i>Đang chờ duyệt</i></TD>
										<%}else if (trangthai.equals("3")){%> 
											<TD align="left"><i>Đã duyệt</i></TD>
										<%}else{%>
											<TD align="left"><i>Đã hủy</i></TD>
										<%}%>
										<TD align="center"><%= dtspList.getString("ngayduyet") %></TD>
										<TD align="left"><%= dtspList.getString("nguoiduyet")%></TD>
										<TD align="center">

										<% if(dtspList.getString("trangthai").equals("1")){ %>
								<%if(quyen[4]!=0){ %>
											<A href = "../../DuyettraspUpdateSvl?userId=<%=userId%>&duyet=<%= dtspList.getString("dhtvId") %>&nppId=<%= dtspList.getString("nppId") %>"><img src="../images/Chot.png" alt="Duyet" title="Chốt" width="20" height="20" longdesc="Duyet" border = 0></A>
											<A href = "../../DuyettrasanphamSvl?userId=<%=userId%>&huy=<%= dtspList.getString("dhtvId") %>&nppId=<%= dtspList.getString("nppId") %>"><img src="../images/Delete.png" alt="Huy" title="Hủy" width="20" height="20" longdesc="Huy" border = 0></A>
										<%} %>
										<%}else if(dtspList.getString("trangthai").equals("3") || dtspList.getString("trangthai").equals("2"))  { %>
										<%if(quyen[3]!=0){ %>
											<A href = "../../DuyettraspUpdateSvl?userId=<%=userId%>&display=<%= dtspList.getString("dhtvId") %>&nppId=<%= dtspList.getString("nppId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
										<%}} %>
										
								
										</TD>
									</TR>
								<% m++;} dtspList.close(); } catch(java.sql.SQLException e){} } %>
								
								<TR>
									<TD align="center" colspan="10" class="tbfooter">
									|<   <   1 to 20 of 100   >   >| </TD>
								</TR>
							</TABLE>
							</TD>
						</TR>
					</TABLE>
					</FIELDSET>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<!--end body Dossier--></TD>
	</TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%}%>