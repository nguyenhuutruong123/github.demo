<%@page import="geso.dms.center.util.Utility"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
	IStockintransit obj = (IStockintransit)session.getAttribute("obj");
	ResultSet rsKenh = obj.getkenh();
	ResultSet rsKhuVuc = obj.getkhuvuc();
	ResultSet rsVung = obj.getvung();	
	ResultSet rsNpp = obj.getnpp();		
	ResultSet rsNhans = obj.getnhanhang();
	ResultSet rsChungLoai = obj.getchungloai();
	ResultSet rsDVKD = obj.getdvkd();
	ResultSet rsProgram = obj.getRsPrograms();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("UsingPromoTT","", userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>DDT - Project</TITLE>
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
	function search(){
		document.forms["frm"]["action"].value = "search";
		document.forms["frm"].submit();
	}	
	
	function submitform() {
		if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui l??ng ch???n ng??y b???t ?????u");
			return;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui l??ng ch???n ng??y k???t th??c");
			return;
		}			
		var fieldShow = document.getElementsByName("fieldsHien");		
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}		
		document.forms["frm"]["action"].value = "create";
		document.forms["frm"].submit();
		reset();
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");		
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
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

	<form name="frm" method="post" action="../../UsingPromoTT">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" value="1" name="action"  >
		<%-- <input type="hidden" name="nppId" value="<%= obj.getnppId() %>" > --%>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Qu???n l?? khuy???n m??i &#62; B??o c??o &#62; S??? d???ng v?? chi tr??? KM</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Ch??o m???ng b???n&nbsp;<%=userTen %></div>
			</div>
			
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> B??o l???i nh???p li???u</legend>
					<textarea id="errors" readonly="readonly" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
					<%= obj.getMsg() %>
					</textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">S??? d???ng v?? chi tr??? khuy???n m??i</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">T??? ng??y</TD>
									<TD class="plainlabel"><input type="text" name="Sdays"
										class="days" value="<%=obj.gettungay() %>" /></TD>
									<TD class="plainlabel">?????n ng??y</TD>
									<TD class="plainlabel"><input type="text" name="Edays"
										class="days" value="<%=obj.getdenngay() %>" /></TD>
								</TR>
									<TR>
									<TD class="plainlabel">V??ng</TD>
									<TD class="plainlabel">
										<select  name="vung" onchange="search();">
											<option value="">All</option>
											<% if(rsVung!=null){
										 		while(rsVung.next()){
													String vungId = rsVung.getString("pk_seq");
													if(vungId.equals(obj.getvungId())){%>
														<option selected="selected" value="<%=rsVung.getString("pk_seq")%>">
															<%=rsVung.getString("ten")%></option>
													<%}else{%>
														<option value="<%=rsVung.getString("pk_seq")%>">
															<%=rsVung.getString("ten")%></option>
										 	<% }}}%>										 	
										</select>
									</TD>
									
									<TD class="plainlabel">Khu v???c</TD>
									<TD class="plainlabel">
										<select  name="khuvuc" onchange="search();">
											<option value="">All</option>
											<% if(rsKhuVuc!=null){
										 		while(rsKhuVuc.next()){
													String kvId = rsKhuVuc.getString("pk_seq");
													if(kvId.equals(obj.getkhuvucId())){%>
														<option selected="selected" value="<%=rsKhuVuc.getString("pk_seq")%>">
															<%=rsKhuVuc.getString("ten")%></option>
													<%}else{%>
														<option value="<%=rsKhuVuc.getString("pk_seq")%>">
															<%=rsKhuVuc.getString("ten")%></option>
										 	<% }}}%>										 	
										</select>
									</TD>
									</TR>
								
									<TR>
									
									<TD class="plainlabel">K??nh b??n h??ng</TD>
									<TD class="plainlabel"><select onchange="search();"  name="kenhId" id="loaiCt">
											<option value="">All</option>
											<% if(rsKenh!=null){
													while(rsKenh.next()){
														String kenhId = rsKenh.getString(1);
														if(kenhId.equals(obj.getkenhId())){
															%>
															<option selected="selected" value="<%=rsKenh.getString(1) %>"><%=rsKenh.getString(2)%></option>
														<%}else{%>	
															<option value="<%=rsKenh.getString(1) %>"><%=rsKenh.getString(2)%></option>
											<%}}}%>
									</select></TD>
									
									<TD class="plainlabel">????n v??? kinh doanh</TD>
									<TD class="plainlabel">
										<select  name="dvkdId" onchange="search();" >
											<option value="">All</option>
										 	<% if(rsDVKD != null){
										 		while(rsDVKD.next()){
										 			String dvkdId = rsDVKD.getString(1);
										 			if(dvkdId.equals(obj.getdvkdId())){%>
										 				<option selected="selected" value="<%=rsDVKD.getString(1)%>"><%=rsDVKD.getString(2)%></option>
										 			<%}else{%>
										 				<option  value="<%=rsDVKD.getString(1)%>"><%=rsDVKD.getString(2)%></option>
										 			
										 	<%}}}%>
										</select>
									</TD>																																				
									</TR>															
								
								<TR>
									
									<TD class="plainlabel">Nh??n h??ng</TD>
									<TD class="plainlabel">
										<select  name="nhanhangId" onchange="search();">
											<option value="">All</option>
										 	<% if(rsNhans!=null){
										 		while(rsNhans.next()){
													String nhanId = rsNhans.getString(1);
													if(nhanId.equals(obj.getnhanhangId())){%>
														<option selected="selected" value="<%=rsNhans.getString(1)%>"><%=rsNhans.getString(2)%></option>
													<%}else{%>
														<option value="<%=rsNhans.getString(1)%>"><%=rsNhans.getString(2)%></option>
										 	<% }}}%>
										</select>
									</TD>
									
									<TD class="plainlabel">Nh?? ph??n ph???i</TD>
									<TD class="plainlabel">
										<select  name="npp">
											<option value="">All</option>
											<% if(rsNpp!=null){
										 		while(rsNpp.next()){
													String nppId = rsNpp.getString("pk_seq");
													if(nppId.equals(obj.getnppId())){%>
														<option selected="selected" value="<%=rsNpp.getString("pk_seq")%>">
															<%=rsNpp.getString("ten")%></option>
													<%}else{%>
														<option value="<%=rsNpp.getString("pk_seq")%>">
															<%=rsNpp.getString("ten")%></option>
										 	<% }}}%>										 	
										</select>
									</TD>												
																		
									
																		
								</TR>								
								<TR>									
									<TD class="plainlabel">Ch????ng tr??nh khuy???n m??i</TD>
									<TD class="plainlabel">
										<select  name="programs">
											<option value="">All</option>
											<% if(rsProgram!=null){
										 		while(rsProgram.next()){
													String programId = rsProgram.getString("SCHEME");
													if(programId.equals(obj.getPrograms())){%>
														<option selected="selected" value="<%=rsProgram.getString("SCHEME")%>">
															<%=rsProgram.getString("DIENGIAI")%></option>
													<%}else{%>
														<option value="<%=rsProgram.getString("SCHEME")%>">
															<%=rsProgram.getString("DIENGIAI")%></option>
										 	<% }}}%>										 	
										</select>
									</TD>
									
									<TD class="plainlabel">Ch???ng lo???i</TD>
									<TD class="plainlabel">
										<select  name="chungloaiId">
											<option value="">All</option>
										 	<% if(rsChungLoai!=null){
										 		while(rsChungLoai.next()){
													String chungLoaiId = rsChungLoai.getString(1);
													if(chungLoaiId.equals(obj.getchungloaiId())){%>
														<option selected="selected" value="<%=rsChungLoai.getString(1)%>"><%=rsChungLoai.getString(2)%></option>
													<%}else{%>
														<option value="<%=rsChungLoai.getString(1)%>"><%=rsChungLoai.getString(2)%></option>
										 	<% }}}%>
										</select>
									</TD>
								</TR>
								<TR>
									<TD colspan="2">																			
									<a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> T???o b??o c??o
									</a>
									</td>
								</TR>
							</TABLE>
						</div>												
						
					</div>
				</fieldset>
			</div>
		</div>
		<br /> <br /> <br /> <br />		
	</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%
	try
	{
		if(!(rsChungLoai == null)){ rsChungLoai.close(); rsChungLoai = null;}	
		if(rsDVKD != null){ rsDVKD.close();	 rsDVKD = null;}	
		if(rsKenh != null){ rsKenh.close();	 rsKenh = null;}			
		if(rsNhans != null){ rsNhans.close(); rsNhans = null;}
		if(rsNpp != null){ rsNpp.close(); rsNpp = null;}
		if(rsProgram != null){ rsProgram.close(); rsProgram = null;}
		
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj",null);
	}catch(java.sql.SQLException e){}
	}
%>