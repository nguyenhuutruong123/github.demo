<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.inpxk.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>


<% IInPXKList obj = (IInPXKList)session.getAttribute("obj"); %>
<% ResultSet rs = obj.getRsPXK(); %>
<% ResultSet rsin = obj.getRsPXKIN(); %>
<% ResultSet nvgn = (ResultSet)obj.getNhanvienGN(); %>
<% ResultSet nvbh = (ResultSet)obj.getNhanvienBH(); %>

<% obj.setNextSplittings(); 

	int[] quyen = new  int[6];
	quyen = util.Getquyen("InPXKSvl","", userId);
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 
 } 
String url = util.getChuyenNguUrl("InPXKSvl","",nnId); 
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$("select:not(.notuseselect2)").select2({ width: 'resolve' }); 
		//$(".select2").select2();
		
	});
</script>
<script>
//perform JavaScript after the document is scriptable.
$(document).ready(function() {

    //When page loads...
    $(".tab_content").hide(); //Hide all content
    var index = $("ul.tabs li.current").show().index(); 
    $(".tab_content").eq(index).show();
    //On Click Event
    $("ul.tabs li").click(function() {
  
        $("ul.tabs li").removeClass("current"); //Remove any "active" class
        $(this).addClass("current"); //Add "active" class to selected tab
        $(".tab_content").hide(); //Hide all tab content  
        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
        $(activeTab).show(); //Fade in the active ID content
        return false;
    });

});
</script>
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
	function processing(id,chuoi){
		 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";
		 document.getElementById(id).href = chuoi;
		  
	}
	
	function searchphieu()
	{   
		document.forms["pxkForm"].tab.value="0";
		document.forms["pxkForm"].action.value="search";
	    document.forms["pxkForm"].submit();
	}
	
	function searchphieuxk()
	{   
		document.forms["pxkForm"].tab.value="1";
		document.forms["pxkForm"].action.value="searchxk";
	    document.forms["pxkForm"].submit();
	}
	function clearform()
	{
		 document.forms["pxkForm"].trangthai.value="";
		 document.forms["pxkForm"].nvbhTen.value="";
		
		 document.forms["pxkForm"].tungay.value = "";
		 document.forms["pxkForm"].denngay.value = "";
		 document.forms["pxkForm"].action.value = "clear";
		 document.forms["pxkForm"].submit();
	}
	function InALL()
	 {
		 var pxkIds = document.getElementsByName("InpxkIds");
		 var _pxkIds = '';
		 var kt = 0;
		 
		 
			 for(var i=0; i < pxkIds.length; i++){
				 if(pxkIds[i].checked == true)
				{
					 kt = 1;
				}
			 }
		 if(kt == 0 )
		 {
			 alert('Vui lòng chọn phiếu xuất kho');
			 return;
		 }		
		 
		 
		 var r = confirm("Bạn chắc chắn muốn in tất cả phiếu xuất kho? ");
		 if (r == false) {		 
		     return;
		 }
		 else
			{
			 	document.forms["pxkForm"].action.value = "InALL";
			 	document.forms["pxkForm"].submit(); 
			
			}
		 
		 
	 }
	
	function InPXK(Id)
	 {
		document.forms["pxkForm"].action.value = "InPXK";
		document.forms['pxkForm'].pxkId.value = Id;
		document.forms["pxkForm"].submit();  
	 }
	
	function XoaPXK(Id)
	 {
		document.forms["pxkForm"].action.value = "XoaPXK";
		document.forms['pxkForm'].pxkId.value = Id;
		document.forms["pxkForm"].submit();  
	 }
	
	function LuuPXK()
	 {
		 var pxkIds = document.getElementsByName("InpxkIds");
		 var _pxkIds = '';
		 var kt = 0;
		 
		 
			 for(var i=0; i < pxkIds.length; i++){
				 if(pxkIds[i].checked == true)
				{
					 kt = 1;
				}
			 }
		 if(kt == 0 )
		 {
			 alert('Vui lòng chọn đơn hàng');
			 return;
		 }		
		 
		 
		 var r = confirm("Bạn chắc chắn muốn lưu đơn hàng vào phiếu xuất kho? ");
		 if (r == false) {		 
		     return;
		 }
		 else
			{
			 	document.forms["pxkForm"].action.value = "LuuPXK";
			 	document.forms["pxkForm"].submit(); 
			
			}
		 
		 
	 }
	function newform(){
		
		 document.forms["pxkForm"].action.value="Tao moi";
		 document.forms["pxkForm"].submit();
	}
	function thongbao()
	{
		if(document.getElementById("msg").value != '')
			alert(document.getElementById("msg").value);
	}
	function chekALL()
	 {
		 var chkALL = document.getElementById("InALL");
		 var InpxkIds = document.getElementsByName("InpxkIds");
		 
		 if(chkALL.checked == true )
		 {
			 for(var i=0; i < InpxkIds.length; i++){
				 InpxkIds[i].checked = true;
			 }
		 }
		 else
		 {
			 for(var i=0; i < InpxkIds.length; i++){
				 InpxkIds[i].checked = false;
			 }
		 }
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

	<form name="pxkForm" method="post" action="../../InPXKSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value="<%= userId %>">
		<input type="hidden" name="pxkId" id="pxkId"  > 
		<input type="hidden" name="nppId" value="<%= obj.getNppId() %>"> 
		<input type="hidden" name="action" value="1"> 
		<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>"> 
		<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
		<input type = "hidden" name = "tab" id = "tab" value="<%= obj.getTab() %>">
		<script type="text/javascript">
	thongbao();
</script>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<!--begin body Dossier-->
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %></TD>

										<TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId)%> <%= obj.getNppTen() %></TD>
									</tr>
								</table></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
						<TR>
							<TD>
								<FIELDSET>
									<LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId)%> &nbsp;</LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="3">
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Từ ngày",nnId)%></TD>
											<TD class="plainlabel">
												<TABLE cellpadding="0" cellspacing="0">
													<TR>
														<TD><input class="days" type="text" name="tungay"
															value="<%= obj.getTungay() %>" size="11"></TD>
													</TR>
												</TABLE>
												
											<TD class="plainlabel"><%=ChuyenNgu.get("Đến ngày",nnId)%></TD>
											<TD class="plainlabel">
												<TABLE cellpadding="0" cellspacing="0">
													<TR>
														<TD><input class="days" type="text" name="denngay"
															value="<%= obj.getDenngay() %>" size="11"></TD>
													</TR>
												</TABLE>
										</TR>
										<TR class="tblightrow">
											
											<TD class="plainlabel"><%=ChuyenNgu.get("Nhân viên bán hàng",nnId)%></TD>
											<TD colspan="7" class="plainlabel"><SELECT
												name="nvbhTen">
													<option value="">&nbsp;&nbsp;</option>
													<% if(nvbh != null){
									  try{ while(nvbh.next()){ 
						    			if(nvbh.getString("nvbhId").equals(obj.getNvbhId())){ %>
													<option value='<%=nvbh.getString("nvbhId")%>' selected><%=nvbh.getString("nvbhTen") %></option>
													<%}else{ %>
													<option value='<%=nvbh.getString("nvbhId")%>'><%=nvbh.getString("nvbhTen") %></option>
													<%}}}catch(java.sql.SQLException e){} }%>
											</SELECT>
											</TD>
										</TR>
										<%-- <TR>
											<TD class="plainlabel">Đơn hàng</TD>
											<TD class="plainlabel">
												<TABLE cellpadding="0" cellspacing="0">
													<TR>
														<TD><input  type="text" name="donhangid"
															value="<%= obj.getDonhangId() %>" size="11"></TD>
													</TR>
												</TABLE>
										</TR> --%>
										
										<TR>
											<TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId)%></TD>
											<TD width="81%" colspan="3" class="plainlabel"><SELECT
												name="trangthai" >
													<% if (obj.getTrangthai().equals("1")){%>
													<option value="1" selected><%=ChuyenNgu.get("Đã chốt",nnId)%></option>
													<option value="0"><%=ChuyenNgu.get("Chưa chốt",nnId)%></option>
													<option value=""></option>
													<%}else if(obj.getTrangthai().equals("0")) {%>
													<option value="0" selected><%=ChuyenNgu.get("Chưa chốt",nnId)%></option>
													<option value="1"><%=ChuyenNgu.get("Đã chốt",nnId)%></option>
													<option value=""></option>
													<%}else{%>
													<option value="" selected></option>
													<option value="1"><%=ChuyenNgu.get("Đã chốt",nnId)%></option>
													<option value="0"><%=ChuyenNgu.get("Chưa chốt",nnId)%></option>
													<%}%>
											</SELECT></TD>
										</TR>
										
										<%-- <TR>
											<TD width="19%" class="plainlabel">Trạng thái IN </TD>
											<TD width="81%" colspan="3" class="plainlabel"><SELECT
												name="trangthaiin" >
													<% if (obj.getTrangthai().equals("1")){%>
													<option value="1" selected>Đã tạo</option>
													<option value="0">Chưa tạo</option>
													<option value=""></option>
													<%}else if(obj.getTrangthai().equals("0")) {%>
													<option value="0" selected>Chưa tạo</option>
													<option value="1">Đã tạo</option>
													<option value=""></option>
													<%}else{%>
													<option value="" selected></option>
													<option value="1">Đã tạo</option>
													<option value="0">Chưa tạo</option>
													<%}%>
											</SELECT></TD>
										</TR> --%>
									
										<TR>
											<TD class="plainlabel" >
										<TD class="plainlabel" colspan="3"><a class="button2"
												href="javascript:searchphieu()"> <img style="top: -4px;"
													src="../images/Search30.png" alt=""><%=ChuyenNgu.get("Tìm kiếm ĐH",nnId)%></a>&nbsp;&nbsp;&nbsp;&nbsp;
													
													<a class="button2"
												href="javascript:searchphieuxk()"> <img style="top: -4px;"
													src="../images/Search30.png" alt=""><%=ChuyenNgu.get("Tìm kiếm PXK",nnId)%></a>&nbsp;&nbsp;&nbsp;&nbsp;

												<a class="button2" href="javascript:clearform()"> <img
													style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId)%></a>&nbsp;&nbsp;&nbsp;&nbsp; 
													
													<a class="button2" href="javascript:LuuPXK()"> <img
													style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Lưu vào PXK",nnId)%></a>&nbsp;&nbsp;&nbsp;&nbsp; 
													<!-- 
							  <INPUT name="action" type="submit" value="Tim kiem">&nbsp;
                              <INPUT name="reinitialiser" type="reset" value="Nhap lai"></TD>
                               -->
										</TR>
									</TABLE>

								</FIELDSET></TD>
						</TR>
					</TABLE>
					<%  String style="";
	String current="";
 		style="style=\"display:none\"";
 	
 	%>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%">
							<ul class="tabs">
										<li
											<%=obj.getTab().equals("0") ? "class='current'" : "" %>>
											<a href="#tabDH"><%=ChuyenNgu.get("Đơn Hàng",nnId)%></a>
										</li>										
										<li
											<%=obj.getTab().equals("1") ? "class='current'" : "" %>><a
											href="#tabPXK"><%=ChuyenNgu.get("Phiếu Xuất Kho",nnId)%></a></li>
									</ul>
						<div class="panes">
							<div id="tabDH" class="tab_content">
								<FIELDSET>
									<LEGEND class="legendtitle">
										<%=ChuyenNgu.get("Đơn Hàng",nnId)%> &nbsp;&nbsp;&nbsp;&nbsp; 
										
										<!--  <INPUT name="action" type="submit" value="Tao moi">-->
									</LEGEND>
									<TABLE class="" width="100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD>
												<TABLE width="100%" border="0" cellspacing="1"
													cellpadding="2">
													<TR class="tbheader">
														<TH align="center"><%=ChuyenNgu.get("Mã đơn hàng",nnId)%></TH>
														<TH align="center"><%=ChuyenNgu.get("Nhập ngày",nnId)%></TH>
														<!-- <TH align="center">Trạng thái In</TH> -->
														<TH align="center"><%=ChuyenNgu.get("Mã khách hàng",nnId)%></TH> 
														<TH align="center"><%=ChuyenNgu.get("Tên khách hàng",nnId)%></TH>
														<TH align="center" style="width: 5%" class="nosort" ><%=ChuyenNgu.get("In",nnId)%> </BR><%=ChuyenNgu.get("tất cả",nnId)%> <br /> 
	                    								<input type="checkbox" id="InALL" onchange="chekALL();" >  
	                    								<A href = "javascript:InALL();"><IMG width="30px" height="30px" src="../images/Printer30.png" title="In tất cả đơn hàng" border=0></A>
	                    				
	               				   						</TH>
													</TR>
													<% 
									
									int m = 0;
									if(rs!=null)					
									while (rs.next()){
										
										if (m % 2 != 0) {%>
													<TR class="tblightrow">
														<%} else {%>
													
													<TR class="tbdarkrow">
														<%}%>
														<TD align="center"><%=rs.getString("dhId") %></TD>
														<TD align="left"><%= rs.getString("ngaydonhang") %></TD>
														<%-- <TD align="left">
														<%
														String trangthaiin = rs.getString("trangthaiin");
														if (trangthaiin == null)
														{ %>
															<span> Chưa tạo</span>
														<% }
														else if(trangthaiin.equals("1")) 
														{%>
														<span><b> Đã tạo </b></span><%
														}else { %>
														<span><b> Chưa tạo</b></span> 
														<%} %>
														</TD>  --%>
														<TD align="center"><%= rs.getString("MaKH") %></TD>
														<TD align="center"><%=  rs.getString("TenKH")%></TD>															
										           		<Td align="center" >										                    
										                    		<input type="checkbox" name="InpxkIds" value="<%= rs.getString("dhId") %>" >
										                </Td>    
													</TR>
													<%m++; }%>

													<tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('pxkForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="Prev('pxkForm', 'prev')"> &nbsp; <%}else{ %>
															<img alt="Trang Truoc" src="../images/prev_d.gif">
															&nbsp; <%} %> <%
													int[] listPages = obj.getNextSplittings();
													for(int i = 0; i < listPages.length; i++){
												%> <% 							
											
												if(listPages[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPages[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('pxkForm', <%= listPages[i] %>, 'view')"><%= listPages[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPages[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="Next('pxkForm', 'next')"> &nbsp; <%}else{ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif">
															&nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('pxkForm', -1, 'view')"> &nbsp; <%} %>
														</TD>
													</tr>
												</TABLE></TD>
										</TR>
									</TABLE>
								</FIELDSET>
							</div>
							<div id="tabPXK" class="tab_content">
							<FIELDSET>
									<LEGEND class="legendtitle">
										<%=ChuyenNgu.get("Phiếu xuất kho",nnId)%> &nbsp;&nbsp;&nbsp;&nbsp; 
										
										<!--  <INPUT name="action" type="submit" value="Tao moi">-->
									</LEGEND>
									<TABLE class="" width="100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD>
												<TABLE width="100%" border="0" cellspacing="1"
													cellpadding="2">
													<TR class="tbheader">
														<TH align="center"><%=ChuyenNgu.get("Mã phiếu",nnId)%></TH>
														<TH align="center"><%=ChuyenNgu.get("Ngày tạo",nnId)%></TH>
														<TH align="center"><%=ChuyenNgu.get("Người tạo",nnId)%></TH>
														<TH align="center" style="width: 5%"><%=ChuyenNgu.get("Tác vụ",nnId)%></TH> 
														
													</TR>
													<% 
									
									 m = 0;
									if(rsin!=null)					
									while (rsin.next()){
										
										if (m % 2 != 0) {%>
													<TR class="tblightrow">
														<%} else {%>
													
													<TR class="tbdarkrow">
														<%}%>
														<TD align="center"><%=rsin.getString("pk_seq") %></TD>
														<TD align="left"><%= rsin.getString("ngaytao") %></TD>
														<TD align="center"><%= rsin.getString("nguoitao") %></TD>
														<TD align="center" valign="middle">																												
															<A href = "javascript:InPXK(<%= rsin.getString("pk_seq") %>);"><IMG width="20px" height="20px" src="../images/pdf.jpg" title="In tất cả đơn hàng trong PXK?" border=0></A>
	                    									<A href = "javascript:XoaPXK(<%= rsin.getString("pk_seq") %>);"><IMG width="20px" height="20px" src="../images/Delete20.png" title="Bạn muốn xóa PXK?" border=0></A>
	                    				
														</TD>
										           		
													</TR>
										<%m++; }%>

													<tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('pxkForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="Prev('pxkForm', 'prev')"> &nbsp; <%}else{ %>
															<img alt="Trang Truoc" src="../images/prev_d.gif">
															&nbsp; <%} %> <%
															int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 							
											
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('pxkForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="Next('pxkForm', 'next')"> &nbsp; <%}else{ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif">
															&nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('pxkForm', -1, 'view')"> &nbsp; <%} %>
														</TD>
													</tr>
												</TABLE></TD>
										</TR>
									</TABLE>
								</FIELDSET>
								</div>
							</div>
							</TD>
						</TR>
					</TABLE> <!--end body Dossier-->
				</TD>
			</TR>
		</TABLE>
	</form>
</BODY>
</html>

<%
	try
	{
		if(!(nvgn == null)){ nvgn.close(); nvgn = null;}
		if(rs!=null){ rs.close(); rs = null; }
		
		if(obj != null){
			obj.DBclose(); 
			obj = null
		;}
		util=null;

		session.setAttribute("obj",null);
		
	}catch(Exception  e){}
	}
%>