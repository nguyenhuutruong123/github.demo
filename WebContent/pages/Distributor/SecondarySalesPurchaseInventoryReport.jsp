<%@page import="geso.dms.center.util.Utility"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
	
	<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet kenh = obj.getkenh();
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet npp = obj.getnpp();
	ResultSet dvkd = obj.getdvkd();
	ResultSet nhanhang = obj.getnhanhang();
	ResultSet chungloai = obj.getchungloai();
	ResultSet dvdl = obj.getdvdl();
	ResultSet sanpham = obj.getsanpham();
	ResultSet gsbh = obj.getgsbh();
	ResultSet rsKho = obj.getkho();
	  
    int[] quyen = new  int[6];
	quyen = util.Getquyen("SecondarySalesPIR","", userId);
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
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
function seach()
{
	document.forms['frm'].action.value= 'seach';
	document.forms["frm"].submit();
}
	function submitform() {
		if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui l??ng ch???n ng??y b???t ?????u");
			return ;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui l??ng ch???n ng??y k???t th??c");
			return ;
		}
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}
		//document.getElementById("btnTaoBC").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
		reset();
	//	for ( var i = 0; i < fieldShow.length; ++i) {
	//		fieldShow.item(i).checked = false;
	//	}
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

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->




<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>	

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

	<form name="frm" method="post"
		action="../../SecondarySalesPIR"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=userId%>'>
			<input type="hidden" name="view" value='<%=obj.getView()%>'>
			
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Qu???n l?? t???n kho &#62; B??o c??o &#62; Xu???t-nh???p-t???n</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Ch??o m???ng b???n
					<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> B??o l???i nh???p li???u</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Xu???t nh???p t???n</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">

							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">T??? ng??y</TD>
									<TD class="plainlabel"><input  AUTOCOMPLETE="off" type="text" name="Sdays"
										class="days" value='<%=obj.gettungay()%>' /></TD>
									<TD class="plainlabel">?????n ng??y</TD>
									<td><input  AUTOCOMPLETE="off" type="text" name="Edays" class="days"
										value='<%=obj.getdenngay()%>' /></td>
								</TR>
								<TR>
									<TD class="plainlabel">K??nh b??n h??ng</TD>
									<TD class="plainlabel"><select name="kenhId" id="kenhId"
										onchange="seach();">
											<option value="" selected>All</option>
											<%
												if (kenh != null)
													while (kenh.next()) {
														if (kenh.getString("pk_seq").equals(obj.getkenhId())) {
											%>
											<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("diengiai")%></option>
											<%
												} else {
											%>
											<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("diengiai")%></option>
											<%
												}
													}
											%>
									</select></TD>
								</TR>
								<TR>									
									<TD class="plainlabel">Nh??n h??ng</TD>
									<TD class="plainlabel"><select name="nhanhangId"
										id="nhanhangId" onchange="seach();">
											<option value="" selected>All</option>
											<%
												if (nhanhang != null)
													while (nhanhang.next()) {
														if (nhanhang.getString("pk_seq")
																.equals(obj.getnhanhangId())) {
											%>
											<option value="<%=nhanhang.getString("pk_seq")%>" selected><%=nhanhang.getString("ten")%></option>
											<%
												} else {
											%>
											<option value="<%=nhanhang.getString("pk_seq")%>"><%=nhanhang.getString("ten")%></option>
											<%
												}
													}
											%>
									</select></TD>
								</TR>
								<TR>									
									<TD class="plainlabel">Ch???ng Lo???i</TD>
									<TD class="plainlabel"><select name="chungloaiId"
										id="chungloaiId" onchange="seach();">
											<option value="" selected>All</option>
											<%
												if (chungloai != null)
													while (chungloai.next()) {
														if (chungloai.getString("pk_seq").equals(
																obj.getchungloaiId())) {
											%>
											<option value="<%=chungloai.getString("pk_seq")%>" selected><%=chungloai.getString("ten")%></option>
											<%
												} else {
											%>
											<option value="<%=chungloai.getString("pk_seq")%>"><%=chungloai.getString("ten")%></option>
											<%
												}
													}
											%>
										</select>
									</TD>
									<TD class="plainlabel"></TD>
									<TD class="plainlabel">	</TD>
								</TR>
								 <TR>
											<TD class="plainlabel" ><%=ChuyenNgu.get("Tr???ng th??i",nnId) %> </TD>
											<TD class="plainlabel" colspan = "3">
											  <select name="TrangThai" onChange="search">
											    <% if (obj.getTrangthaispId().equals("1")){%>
											  	<option value="1" selected><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
											  	<option value="0"><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
											  	<option value=" "> </option>
											  
											<%}else if(obj.getTrangthaispId().equals("0")) {%>
											 	 <option value="0" selected><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
											  	<option value="1" ><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
											 	 <option value=" " > </option>
											  
											<%}else{%>											
											  	<option value="1" ><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
											  	<option value="0" ><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
											 	<option value=" " selected> </option>											  	
											<%}%>
									          </select></TD>
										</TR>
								<TR>
									<TD class="plainlabel">????n v??? kinh doanh</TD>
									<TD class="plainlabel"><select name="dvkdId" id="dvkdId"
										onchange="seach();">
											<option value="" selected>All</option>
											<%
												if (dvkd != null)
													while (dvkd.next()) {
														if (dvkd.getString("pk_seq").equals(obj.getdvkdId())) {
											%>
											<option value="<%=dvkd.getString("pk_seq")%>" selected><%=dvkd.getString("diengiai")%></option>
											<%
												} else {
											%>
											<option value="<%=dvkd.getString("pk_seq")%>"><%=dvkd.getString("diengiai")%></option>
											<%
												}
													}
											%>
									</select></TD>	
										<TD class="plainlabel">Kho</TD>
									<TD class="plainlabel"><select name="khoId" id="khoId"
										onchange="seach();">
											<option value="" selected>All</option>
											<%
												if (rsKho != null)
													while (rsKho.next()) {
														if (rsKho.getString("pk_seq").equals(obj.getkhoId())) {
														%>
													<option value="<%=rsKho.getString("pk_seq")%>" selected><%=rsKho.getString("TEN")%></option>
													<%
													} else {
													%>
													<option value="<%=rsKho.getString("pk_seq")%>"><%=rsKho.getString("TEN")%></option>
													<%
													}
													}
											%>
									</select></TD>								

								</TR>
								
								<TR>
									<TD class="plainlabel">T??nh theo gi??</TD>
									<TD class="plainlabel" colspan="3">
									<select name="giatinh" id="giatinh">
										<option value="1" selected>Gi?? mua NPP</option>
										<option value="2">Gi?? b??n NPP</option>
										<!-- <option value="3">Gi?? t???n</option> -->
									</select>
									</TD>
								</TR>
								
								<TR>
                             <TD class="plainlabel">Nh?? ph??n ph???i</TD>
									<TD class="plainlabel"><select name="nppId" id="nppId" >
											<option value="" selected>All</option>
											<% if(npp != null)
											   while(npp.next()){
											   if(npp.getString("pk_seq").equals(obj.getnppId()))
											   { %>
											   <option value="<%= npp.getString("pk_seq") %>" selected><%=npp.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= npp.getString("pk_seq") %>"><%=npp.getString("ten") %></option>
											<%} }%>
									</select></TD>
								
	                           </TR>
	                           
	                           
								  	<TD class="plainlabel" colspan="2">
							  			<TABLE cellpadding="0" cellspacing="0">
						  				<TR>
					  					<TD style="font-size: 12px; font-weight: bold;" >
				  						<input type="radio" value="0" name="loai"  />L???y t???ng
				  						<input type="radio" value="1" name="loai" checked="checked" />L???y theo ng??y nh???p kho
										</TD>
                                   		</TR>
										</TABLE>
									</TD>
								</TR>
								
								<TR>
								  	<TD class="plainlabel" colspan="2">
							  			<TABLE cellpadding="0" cellspacing="0">
						  				<TR>
					  					<TD style="font-size: 12px; font-weight: bold;" >
				  						<input type="radio" value="0" name="avail"  />L???y t???t c???
				  						<input type="radio" value="1" name="avail" checked="checked" />Ch??? l???y s??? l?????ng l???n h??n 0
										</TD>
                                   		</TR>
										</TABLE>
									</TD>
								</TR>
								
								<%-- <TR>
									
									<TD class="plainlabel">H??ng ??ang ??i tr??n ???????ng</TD>
									<TD class="plainlabel" colspan="3">
										<%
											if (obj.getdiscount().equals("1")) {
										%> <input type="radio" name="instransit" value="1"
										checked="checked" /> Yes &nbsp; &nbsp; <input type="radio"
										name="instransit" value="0" /> No 
										<% 	} else { %> <input type="radio" name="instransit" value="1" /> Yes
										&nbsp; &nbsp; <input type="radio" name="instransit" value="0"
										checked="checked" /> No <%
 										}
 										%>
									</TD>
								</TR> --%>
								<TR>
									<td colspan="4">
									<div id="btnTaoBC">
									<a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> <%=ChuyenNgu.get("T???o b??o c??o",nnId) %>  </a>
											</div>
									</td>
								</TR>
							</TABLE>
						</div>
						<hr>
					
					</div>
				</fieldset>
			</div>
		</div>
		<br /> <br /> <br /> <br />
	</form>
</body>
</html>
	
<%  if(chungloai != null){  chungloai.close(); chungloai = null; }
	if (dvkd != null){  dvkd.close();  dvkd = null; }
	if (nhanhang != null){  nhanhang.close();  nhanhang = null; }
	if (dvdl != null){  dvdl.close();   dvdl = null; }
	if(gsbh != null){  gsbh.close(); gsbh = null; }
	if(kenh != null){  kenh.close(); kenh = null; }
	if(khuvuc != null){  khuvuc.close(); khuvuc = null; }
	if(npp != null){  npp.close(); npp = null; }
	if(vung != null){  vung.close(); vung = null; }
	if(sanpham!=null){ sanpham.close(); sanpham = null; }
	if(rsKho!=null){ rsKho.close(); rsKho = null; }
	
	 if(obj != null){  
		 obj.DBclose(); 
		 obj = null;
	 }
	session.setAttribute("obj",null);
}%>