<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
	
	String groupCustomer = (String)session.getAttribute("groupCustomer");
	String gorupSKU = (String)session.getAttribute("gorupSKU");
	
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
	ResultSet nvgn = obj.getnvgn();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("SanluonggiaonhanTTSvl","", userId);
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

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
		$(".button").hover(function() {
			$(".button img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
	$(document).ready(function() {
		$(".button1").hover(function() {
			$(".button1 img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
</script>

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">
	function seach() {
		document.forms['frm'].action.value = 'seach';
		document.forms["frm"].submit();
	}
	function submitform() 
	{	
		document.forms['frm'].action.value = 'Taomoi';
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
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	 function Dinhdang(element)
		{
			element.value=DinhDangTien(element.value);
			if(element.value== '' ||element.value=='0' )
			{
				element.value= '';
			}
		}
</script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2(); 
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

<form name="frm" method="post" action="../../SanluonggiaonhanTTSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="action" value='1'> 
<input type="hidden" name="userId" value='<%=obj.getuserId()%>'>
	<div id="main" style="width: 99%; padding-left: 2px">
		<div align="left" id="header" style="width: 100%; float: none">
			<div style="width: 70%; padding: 5px; float: left" class="tbnavigation">B??o c??o qu???n tr??? &#62; Doanh s??? &#62; S???n l?????ng NVGN</div>
			<div align="right" style="padding: 5px" class="tbnavigation">
			Ch??o m???ng b???n, <%=obj.getuserTen()%></div>
		</div>
		<div align="left" style="width: 100%; float: none; clear: left">
			<fieldset>
			<legend class="legendtitle"> B??o l???i nh???p li???u</legend>
			<textarea id="errors" name="errors" rows="1"  style="width: 100% ; color:#F00 ; font-weight:bold">
			<%=obj.getMsg()%></textarea>
			</fieldset>
		</div>
		
		<div align="left" style="width: 100%; float: none; clear: left; font-size: 0.7em">
			<fieldset>
			<legend class="legendtitle">B??o c??o s???n l?????ng nh??n vi??n giao nh???n </legend>
			<div style="width: 100%; float: none" align="left">
				<div style="width: 100%; float: none" align="left" class="plainlabel">
					<TABLE width="70%" cellpadding="6" cellspacing="0">								
						<tr>
							<td class="plainlabel">T??? ng??y</td>
							<td class="plainlabel">
							<input type="text" name="Sdays"	class="days" value='<%=obj.gettungay()%>' />
							</td>
							
							<td class="plainlabel">?????n ng??y</td>
							<td class="plainlabel">
							<input type="text" name="Edays" class="days" value='<%=obj.getdenngay()%>' />
							</td>
						</tr>																						
								
						<tr>
							<td class="plainlabel">K??nh b??n h??ng</td>
							<td class="plainlabel">
							<select name="kenhId" id="kenhId" onchange="seach();">
							<option value="" selected>All</option>
							<%if (kenh != null)
								while (kenh.next()) {
								if (kenh.getString("pk_seq").equals(obj.getkenhId())) {%>
									<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("diengiai")%></option>
								<%} else { %>
									<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("diengiai")%></option>
								<%}}%>
							</select>
							</td>
							
							<td class="plainlabel">Nh??n h??ng</td>
							<td class="plainlabel">
							<select name="nhanhangId" id="nhanhangId" onchange="seach();">
							<option value="" selected>All</option>
							<%if (nhanhang != null)
								while (nhanhang.next()) {
									if (nhanhang.getString("pk_seq").equals(obj.getnhanhangId())) {	%>
										<option value="<%=nhanhang.getString("pk_seq")%>" selected><%=nhanhang.getString("ten")%></option>
									<%} else {%>
										<option value="<%=nhanhang.getString("pk_seq")%>"><%=nhanhang.getString("ten")%></option>
									<%}}%>
							</select>
							</td>													
						</tr>
						
						<tr>
							<td class="plainlabel">????n v??? kinh doanh</td>
							<td class="plainlabel">
							<select name="dvkdId" id="dvkdId"	onchange="seach();">
							<option value="" selected>All</option>
								<%if (dvkd != null)
									while (dvkd.next()) {
										if (dvkd.getString("pk_seq").equals(obj.getdvkdId())) {%>
											<option value="<%=dvkd.getString("pk_seq")%>" selected><%=dvkd.getString("diengiai")%></option>
										<%} else {%>
											<option value="<%=dvkd.getString("pk_seq")%>"><%=dvkd.getString("diengiai")%></option>
										<%}}%>
							</select>
							</td>
							
							<td class="plainlabel">Ch???ng lo???i</td>
							<td class="plainlabel">
							<select name="chungloaiId" id="chungloaiId" onchange="seach();">
							<option value="" selected>All</option>
								<%if (chungloai != null)
									while (chungloai.next()) {
										if (chungloai.getString("pk_seq").equals(obj.getchungloaiId())) {%>
											<option value="<%=chungloai.getString("pk_seq")%>" selected><%=chungloai.getString("ten")%></option>
										<%} else {%>
											<option value="<%=chungloai.getString("pk_seq")%>"><%=chungloai.getString("ten")%></option>
										<% }}%>
							</select>
							</td>
						</tr>		
						
						<tr>
							<td class="plainlabel">V??ng/Mi???n</td>
							<td class="plainlabel">
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
							</td>
							
							<td class="plainlabel">Khu v???c</td>
							<td class="plainlabel">
							<select name="khuvucId" id="khuvucId" onchange="seach();">
							<option value="" selected>All</option>
							<%if (khuvuc != null)
								while (khuvuc.next()) {
									if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) { %>
										<option value="<%=khuvuc.getString("pk_seq")%>" selected > <%=khuvuc.getString("ten")%></option>
									<%} else {%>
										<option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
									<%}}%>
							</select>
							</td>													
						</tr>
						
						<tr>
							<td class="plainlabel">Nh?? Ph??n Ph???i </td>
							<td class="plainlabel">
							<select name="nppId" onchange="seach();" id="nppId">
							<option value="" selected>All</option>
							<%if (npp != null)
								while (npp.next()) {
									if (npp.getString("pk_seq").equals(obj.getnppId())) {%>
										<option value="<%=npp.getString("pk_seq")%>" selected><%=npp.getString("ten")%></option>
									<%} else {%>
										<option value="<%=npp.getString("pk_seq")%>"><%=npp.getString("ten")%></option>
									<%}}%>
							</select>
							</td>
							
							<td class="plainlabel">Nh??n vi??n giao nh???n</td>
							<td class="plainlabel">
							<select name="nvgnId" id="nvgnId" onchange="seach();">
							<option value="" selected>All</option>
							<%if (nvgn != null)
								while (nvgn.next()) {
								if (nvgn.getString("pk_seq").equals(obj.getnvgnId())) {%>
									<option value="<%=nvgn.getString("pk_seq")%>" selected><%=nvgn.getString("ten")%></option>
								<%} else {%>
									<option value="<%=nvgn.getString("pk_seq")%>"><%=nvgn.getString("ten")%></option>
								<%}}%>
							</select>
							</td>							
						</tr>																																							
								
						<tr>
							<td colspan="4">
							<a class="button" href="javascript:submitform();"> <img style="top: -4px;"
							   src="../images/button.png" alt=""> T???o b??o c??o </a>
							</td>
						</tr>
					</table>
				</div> 
			</div>
			</fieldset>
		</div>
		<br /> <br /> <br /> <br />
		</div>
	</form>
	
	<script type="text/javascript">
		$("select:not(.notuseselect2)").css({
			"width": "200px", 
			//"height": "200px"
		});
	</script>
	
	<%
		if(kenh !=null){ kenh.close(); kenh = null ; }
		if(vung!=null){ vung.close(); vung = null ; }
		if(khuvuc!=null){ khuvuc.close(); khuvuc = null ; }
		if(npp!=null){ npp.close(); npp = null ; }
		if(dvkd!=null){ dvkd.close(); dvkd = null ; }
		if(nhanhang!=null){ nhanhang.close(); nhanhang = null ; }
		if(chungloai!=null){ chungloai.close(); chungloai = null ; }
		if(dvdl!=null){ dvdl.close(); dvdl = null ; }
		if(sanpham!=null){ sanpham.close(); sanpham = null ; }
		if(nvgn!=null){ nvgn.close(); nvgn = null ; }
		if(obj!=null){
			obj.DBclose();
			obj = null;
		}		
		session.setAttribute("obj", null);
		
	}
	%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>