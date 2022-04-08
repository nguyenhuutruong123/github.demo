<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"

	pageEncoding="UTF-8"%>
	<%@page import="geso.dms.center.util.Utility"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TTC/index.jsp");
	}else{ %>
	<%
		IStockintransit obj = (IStockintransit) session.getAttribute("obj");
		ResultSet kenh = obj.getkenh();
		ResultSet vung = obj.getvung();
		ResultSet khuvuc = obj.getkhuvuc();
		ResultSet npp = obj.getnpp();
		ResultSet dvkd = obj.getdvkd();
		ResultSet dvdl = obj.getdvdl();
		ResultSet gsbh = obj.getgsbh();
		ResultSet ddkd = obj.getRsddkd();
	%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("ThucDatChiTieuSR","",nnId);	
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
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
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
	function submitform() {
		document.forms['frm'].action.value= 'Taomoi';
		document.forms["frm"].submit();
	}
	function xuatExcelKHMoi() {
		document.forms['frm'].action.value= 'xuatkhmoi';
		document.forms["frm"].submit();
	}
	function seach()
	{
		document.forms['frm'].action.value= 'seach';
		document.forms["frm"].submit();
	}
	
	function chotform()
	{
		document.forms['frm'].action.value= 'chot';
		document.forms["frm"].submit();
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");
		var fieldHidden = document.getElementsByName("fieldsAn");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}
		for ( var i = 0; i < fieldHidden.length; ++i) {
			fieldHidden.item(i).checked = false;
		}
	};
	
</script>

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
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="frm" method="post" action="../../ThucDatChiTieuSR">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"> <%=" "+ url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen%></div>
			</div>
		
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle">Thông báo</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
					<%= obj.getMsg() %></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"> Thực hiện chỉ tiêu</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
							<!-- <TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										
										<TD width="2" align="left"></TD>
										<TD width="10" align="left"><A
											href="javascript:chotform()"><IMG
												src="../images/Save30.png" title="Luu lai" alt="Luu lai"
												border="1" style="border-style: outset">
										</A>
										</TD>
										<TD>&nbsp;</TD>

									</TR>
								</TABLE></TD> -->
								
								
							<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Chọn nhân viên",nnId) %></TD>
									
									<TD class="plainlabel">
										<SELECT name = "loainv" onchange = "seach();">
										
										<%-- <%
  										int n;
  										for(n=1;n<=3;n++){
  										if(obj.getLoaiNv().equals(""+n)){ %>
										<option value=<%=n%> selected="selected" > <%if(n == 1)%> SR <%else if(n == 2) %>SS  <%else %> ASM</option> 
										<% } else { %>
										<option value=<%=n%> ><%if(n == 1)%> SR <%else if(n == 2) %>SS  <%else %>ASM </option> 
										<% } } %> --%>
										
										<%
  										int n;
  										for(n=1;n<=1;n++){
  										if(obj.getLoaiNv().equals(""+n)){ %>
										<option value=<%=n%> selected="selected" > <%if(n == 1)%> SR</option> 
										<% } else { %>
										<option value=<%=n%> ><%if(n == 1)%> SR</option> 
										<% } } %>

										</SELECT>									
									</TD>
									</TR>
									<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Vùng/Miền",nnId) %></TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();" style ="width:200px" multiple="multiple">
										
											<%if (vung != null)
													while (vung.next()) {
														if (obj.getvungId().contains(vung.getString("pk_seq"))) {%>
													<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
												<%} else {%>
													<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									</TR>
								<tr>
								
								<TD width="19%" class="plainlabel" ><%=ChuyenNgu.get("Khu Vực",nnId) %> </TD>
								<TD class="plainlabel" >
								<TABLE cellpadding="0" cellspacing="0">
								<TR><TD>
								<select name="khuvucId" id = "khuvucId" onchange="submitCBO();" multiple="multiple" >
                                 <option value ="" > </option>  
                               <%
                               if(khuvuc != null)
                               while(khuvuc.next())
                               {
                               if(obj.getkhuvucId().contains(khuvuc.getString("pk_seq"))) {
                            	%><option value ="<%=khuvuc.getString("pk_seq") %>" selected> <%=khuvuc.getString("ten") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=khuvuc.getString("pk_seq") %>"> <%=khuvuc.getString("ten") %></option>
                              <%}} %>                             </select>		   										</TD>
                                    		</TR>
								</TABLE>
								</TD>
								
								</tr>
								
								<tr>
								  	<TD width="19%" class="plainlabel" ><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
								<select id = "nppId" name="nppId"  style = "width: 250px" multiple="multiple" >
                                 <option value =""> </option>  
                               <%
                               if(npp != null)
                               while(npp.next())
                               {
                               if(obj.getnppId().contains(npp.getString("pk_seq")) ) {
                            	%><option value ="<%=npp.getString("pk_seq") %>" selected> <%=npp.getString("ten") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=npp.getString("pk_seq") %>"> <%=npp.getString("ten") %></option>
                              <%}} %>                             </select>		   										</TD>
                                    		</TR>
								</TABLE>									
								</TD>
								
								</tr>
								
								
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Chọn năm",nnId) %></TD>
									
									<TD class="plainlabel">
										<SELECT name = "year">
										<%
									  
  										
  										for(n=2008;n<2025;n++){
  										if(obj.getYear().equals(""+n)){
	  									%>
										<option value=<%=n%> selected="selected" > <%=n%></option> 
										<%
	 										}else{
	 									%>
										<option value=<%=n%> ><%=n%></option> 
										<%
	 										}
	 									 }
 										%>

										</SELECT>									
									</TD>
									
									<TD class="plainlabel"><%=ChuyenNgu.get("Chọn tháng",nnId) %></TD>
									<TD>
										<SELECT name = "month">
										<%if(obj.getMonth().equals("01")){ %>
											<option value = "01" selected><%=ChuyenNgu.get("T1",nnId) %></option>
										<%}else{ %>
											<option value = "01"><%=ChuyenNgu.get("T1",nnId) %></option>
										<%} %>

										<%if(obj.getMonth().equals("02")){ %>
											<option value = "02" selected><%=ChuyenNgu.get("T2",nnId) %></option>
										<%}else{ %>
											<option value = "02"><%=ChuyenNgu.get("T2",nnId) %></option>
										<%} %>

										<%if(obj.getMonth().equals("03")){ %>
											<option value = "03" selected><%=ChuyenNgu.get("T3",nnId) %></option>
										<%}else{ %>
											<option value = "03"><%=ChuyenNgu.get("T3",nnId) %></option>
										<%} %>

										<%if(obj.getMonth().equals("04")){ %>
											<option value = "04" selected><%=ChuyenNgu.get("T4",nnId) %></option>
										<%}else{ %>
											<option value = "04"><%=ChuyenNgu.get("T4",nnId) %></option>
										<%} %>

										<%if(obj.getMonth().equals("05")){ %>
											<option value = "05" selected><%=ChuyenNgu.get("T5",nnId) %></option>
										<%}else{ %>
											<option value = "05"><%=ChuyenNgu.get("T5",nnId) %></option>
										<%} %>

										<%if(obj.getMonth().equals("06")){ %>
											<option value = "06" selected><%=ChuyenNgu.get("T6",nnId) %></option>
										<%}else{ %>
											<option value = "06"><%=ChuyenNgu.get("T6",nnId) %></option>
										<%} %>

										<%if(obj.getMonth().equals("07")){ %>
											<option value = "07" selected><%=ChuyenNgu.get("T7",nnId) %></option>
										<%}else{ %>
											<option value = "07"><%=ChuyenNgu.get("T7",nnId) %></option>
										<%} %>

										<%if(obj.getMonth().equals("08")){ %>
											<option value = "08" selected><%=ChuyenNgu.get("T8",nnId) %></option>
										<%}else{ %>
											<option value = "08"><%=ChuyenNgu.get("T8",nnId) %></option>
										<%} %>
										
										<%if(obj.getMonth().equals("09")){ %>
											<option value = "09" selected><%=ChuyenNgu.get("T9",nnId) %></option>
										<%}else{ %>
											<option value = "09"><%=ChuyenNgu.get("T9",nnId) %></option>
										<%} %>
										
										<%if(obj.getMonth().equals("10")){ %>
											<option value = "10" selected><%=ChuyenNgu.get("T10",nnId) %></option>
										<%}else{ %>
											<option value = "10"><%=ChuyenNgu.get("T10",nnId) %></option>
										<%} %>
										
										<%if(obj.getMonth().equals("11")){ %>
											<option value = "11" selected><%=ChuyenNgu.get("T11",nnId) %></option>
										<%}else{ %>
											<option value = "11"><%=ChuyenNgu.get("T11",nnId) %></option>
										<%} %>
										
										<%if(obj.getMonth().equals("12")){ %>
											<option value = "12" selected><%=ChuyenNgu.get("T12",nnId) %></option>
										<%}else{ %>
											<option value = "12"><%=ChuyenNgu.get("T12",nnId) %></option>
										<%} %>
										
										

										</SELECT>
									
									</TD>
								</TR>
								
								 <TR>
									<TD  class="plainlabel" colspan = "4" ><label>
										<%  if (obj.getcovat().equals("0")){%>
										  	<input name="covat" type="checkbox" value ="0" checked>
										<%} else {%>
											<input name="covat" type="checkbox" value ="1" >
										<%} %>
									     <%=ChuyenNgu.get("Không bao gồm VAT",nnId) %></label></TD>
										<TD  class="plainlabel" >&nbsp;</TD>

								
								</TR>
<%-- 									<TR>
									<TD class="plainlabel">Kênh bán hàng</TD>									
									<TD class="plainlabel">
										<select name="kenhId" id="kenhId" onchange="seach();">
												<option value="" selected>All</option>
												<%if (kenh != null)
														while (kenh.next()) {
															if (kenh.getString("pk_seq").equals(obj.getkenhId())) {%>
												   		<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("diengiai")%></option>
												   <%} else {%>
												   	<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("diengiai")%></option>
													<%}}%>
										</select>
									</TD>
									
									
									<TD class="plainlabel">Đơn vị kinh doanh</TD>
									<TD class="plainlabel">
										<select name="dvkdId" id="dvkdId" onchange="seach();" >
											<option value="" selected>All</option>
											<%if (dvkd != null)
													while (dvkd.next()) {
														if (dvkd.getString("pk_seq").equals(obj.getdvkdId())) {%>
										   				<option value="<%=dvkd.getString("pk_seq")%>" selected><%=dvkd.getString("diengiai")%></option>
										   		  <%} else {%>
										   				<option value="<%=dvkd.getString("pk_seq")%>"><%=dvkd.getString("diengiai")%></option>
													<%}}%>
											</select>
										</TD>
									
									
									
									
									
									
								</TR>
								<TR>
								<TD class="plainlabel">Vùng/Miền</TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();">
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
									
										
										<TD class="plainlabel">Nhà phân phối</TD>
									<TD class="plainlabel">
										<select name="nppId" onchange="seach();" id="nppId" >
											<option value="" selected>All</option>
											<%if (npp != null)
													while (npp.next()) {
														if (npp.getString("pk_seq").equals(obj.getnppId())) {
												%>
											   	<option value="<%=npp.getString("pk_seq")%>" selected><%=npp.getString("ten")%></option>
											   <%} else {%>
											   	<option value="<%=npp.getString("pk_seq")%>"><%=npp.getString("ten")%></option>
												<%}}%>
										</select>
									</TD>
									</TR>
								<TR>									
									<TD class="plainlabel">Khu vực</TD>
									<TD class="plainlabel">
									<select name="khuvucId" id="khuvucId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
											   <option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
											<%}}%>
									</select>
									</TD>	
									<TD class="plainlabel">Nhân viên bán hàng</TD>
									<TD class="plainlabel">
										<select name="ddkdId">
											<option value="" >All</option>
											<%if (ddkd != null)
													while (ddkd.next()) {
														if (ddkd.getString("pk_seq").equals(obj.getDdkd())) {%>
											   <option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=ddkd.getString("pk_seq")%>"><%=ddkd.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>								
								</TR>	---%>
														
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> <%=ChuyenNgu.get("Tạo báo cáo",nnId) %>
									</a> &nbsp;&nbsp;&nbsp;&nbsp; 
										<a class="button2" href="javascript:xuatExcelKHMoi()"> 
											<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất KH Mới", nnId)%>
										</a>
									</td> 
								</TR>
							</TABLE>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
	</form>
</body> <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%
	try
	{
		if(!(ddkd == null))
			ddkd.close();
		if(dvdl != null)
			dvdl.close();
		if(dvkd != null)
			dvkd.close();
		if(gsbh != null)
			gsbh.close();
		if(kenh != null)
			kenh.close();
		if(khuvuc != null)
			khuvuc.close();
		if(npp != null)
			npp.close();

		if(obj != null){
			obj.DBclose();
			obj = null;
		}
	}catch(java.sql.SQLException e){}
	}
	
%>