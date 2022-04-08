<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.chuyenkhonv.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>


<% IChuyenkhoList obj = (IChuyenkhoList)session.getAttribute("obj"); %>
<% ResultSet ckRs = (ResultSet)obj.getRsChuyenKho(); %>
<% ResultSet nvbh = (ResultSet)obj.getNhanvienBH(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>SalesUp - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">

<script type="text/javascript" src="../scripts/phanTrang.js"></script>

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
	   return;
	 }
	function searchphieu()
	{   
		document.forms["ckForm"].action.value="search";
	    document.forms["ckForm"].submit();
	}
	function clearform()
	{
		 document.forms["ckForm"].trangthai.value="";
		 document.forms["ckForm"].nvbhTen.selectedIndex = -1;
		 document.forms["ckForm"].trangthai.selectedIndex = -1;
		 document.forms["ckForm"].tungay.value = "";
		 document.forms["ckForm"].denngay.value = "";
		 document.forms["ckForm"].action.value = "clear";
		 document.forms["ckForm"].submit();
	}
	function newform(){
		
		 document.forms["ckForm"].action.value = "Tao moi";
		 document.forms["ckForm"].submit();
	}
	function thongbao()
	{
		if(document.getElementById("msg").value != '')
			alert(document.getElementById("msg").value);
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

<form name="ckForm" method="post" action="../../ChuyenkhoNVSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="action" value='1'>

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
   							<TD align="left" width="50%" class="tbnavigation">&nbsp;Quản lý tồn kho > Chuyển kho</TD>
   							
   							<TD align="right" width="50%" class="tbnavigation">Chào mừng nhà phân phối <%= obj.getNppTen() %></TD>
						</tr>
					</table>
				</TD>
		  	</TR>
		</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
			<TR>
				<TD >
					<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</LEGEND>
					
					<TABLE  width="100%" cellspacing="0" cellpadding="3">
						<TR>
							<TD class="plainlabel" >Từ ngày</TD>
							<TD class="plainlabel">
								<input class="days" type="text" name="tungay" value="<%= obj.getTungay() %>" size="11">
							</TD>		
						</TR>
						<TR>
							<TD class="plainlabel" >Đến ngày</TD>
							<TD class="plainlabel">
								<input class="days" type="text" name="denngay" value="<%= obj.getDenngay() %>" size="11">
							</TD>		
						</TR>
						<TR>
							<TD width="19%" class="plainlabel">Trạng thái </TD>
							<TD width="81%" colspan="3" class="plainlabel">
								<SELECT name ="trangthai" onChange = "searchphieu();">                                  
                                   <% if (obj.getTrangthai().equals("1")){%>
                                         <option value="1" selected>Đã chốt</option>
                                         <option value="0">Chưa chốt</option>
                                         <option value=""> </option>                                        
                                   <%}else if(obj.getTrangthai().equals("0")) {%>
                                         <option value="0" selected>Chưa chốt</option>
                                         <option value="1" >Đã chốt</option>
                                         <option value="" > </option>                                         
                                   <%}else{%>                                          
                                         <option value="" selected> </option>
                                         <option value="1" >Đã chốt</option>
                                         <option value="0" >Chưa chốt</option>
                                   <%}%>
                                </SELECT>
							</TD>
						</TR>
						<TR class="tblightrow">
							<TD  class="plainlabel">Nhân viên bán hàng </TD>
							<TD colspan="3" class="plainlabel"> 
				 			<SELECT name="nvbhTen" onChange = "searchphieu();">
					 			 <option value="">&nbsp;&nbsp;</option>
								  <% if(nvbh != null){
									  try{ while(nvbh.next()){ 
						    			if(nvbh.getString("nvbhId").equals(obj.getNvbhId())){ %>
						      				<option value='<%=nvbh.getString("nvbhId")%>' selected><%=nvbh.getString("nvbhTen") %></option>
						      			<%}else{ %>
						     				<option value='<%=nvbh.getString("nvbhId")%>'><%=nvbh.getString("nvbhTen") %></option>
						     			<%}}}catch(java.sql.SQLException e){} }%>	 
                               			</SELECT></TD>
						</TR>
						<TR>
							<TD class="plainlabel" colspan="3">
							<a class="button2" href="javascript:searchphieu()">
								<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;
	
							<a class="button2" href="javascript:clearform()">
								<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
	
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
					<LEGEND class="legendtitle">Phiếu chuyển kho &nbsp;&nbsp;&nbsp;&nbsp;
						<a class="button3" href="javascript:newform()">
    						<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
					</LEGEND>
					<TABLE class="" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="2">
									<TR class="tbheader">
											<TH align="center" width="10%">Mã phiếu</TH>
											<TH align="center" width="8%">Ngày chuyển</TH>
											<TH align="center" width="16%">Nhân viên bán hàng</TH>
											<TH align="center" width="8%">Trạng thái</TH>
											<TH align="center" width="8%">Ngày tạo</TH>
											<TH align="center" width="16%">Người tạo </TH>
											<TH align="center" width="10%">Ngày sửa</TH>
											<TH align="center" width="16%"> Người sửa </TH>
											<TH align="center" width="8%">Tác vụ</TH>
									</TR>
									
									<% if(ckRs != null)
									{ 
										int m = 0;
										while(ckRs.next()) 
										{ %>
											
										<% if (m % 2 != 0) {%>						
											<TR class= "tblightrow">
										<%} else {%>
											<TR class= "tbdarkrow">
										<%}%>
										
										<TD align="center"><%= ckRs.getString("pk_seq") %></TD>
										<TD align="center"><%= ckRs.getString("ngaychuyen") %></TD>
										<TD align="left"><%= ckRs.getString("ddkdTen") %></TD>
										<% if (ckRs.getString("trangthai").equals("1")){ %>
                                            <TD align="center"><b>Đã chốt</b></TD>
                                        <%}else{ if(ckRs.getString("trangthai").equals("0")){ %>
                                            <TD align="center">Chưa chốt</TD>
                                        <%}else{ %>
                                        	<TD align="center"><i>Đã hủy</i></TD>
                                        <% }}%>  
										<TD align="center"><%= ckRs.getString("ngaytao") %></TD>
										<TD align="left"><%= ckRs.getString("nguoitao") %></TD>
										<TD align="center"><%= ckRs.getString("ngaysua") %></TD>
										<TD align="left"><%= ckRs.getString("nguoisua") %></TD>
										
										<TD align="center" valign="middle">
											<% if(ckRs.getString("trangthai").equals("0")) {%>
												<A href = "../../ChuyenkhoNVUpdateSvl?userId=<%=userId%>&update=<%= ckRs.getString("pk_seq") %>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
												<A href = "../../ChuyenkhoNVSvl?userId=<%=userId%>&delete=<%= ckRs.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa phiếu chuyển kho này?')) return false;"></A>&nbsp;
												<A href = "../../ChuyenkhoNVSvl?userId=<%=userId%>&chotphieu=<%= ckRs.getString("pk_seq") %>" > <img src="../images/Chot.png" alt="Chot phieu chuyen" width="20" height="20" longdesc="Chot phieu chuyen" border = 0 onclick="if(!confirm('Bạn có muốn chốt phiếu chuyển kho này?')) return false; "></A>
											<%}else{ %>
												<A href = "../../ChuyenkhoNVUpdateSvl?userId=<%=userId%>&display=<%= ckRs.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
												
											<% } %>
												
										</TD>
										
										</TR>
									<%  m++; } } %>
								
									<tr class="tbfooter" > 
									 	<TD align="center" valign="middle" colspan="13" class="tbfooter">
									 		&nbsp;
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
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%
	try
	{
		if(!(nvbh == null))
			nvbh.close();
		if(obj != null){
		obj.DBclose(); obj = null
		;}
	}catch(java.sql.SQLException e){}
	}
%>