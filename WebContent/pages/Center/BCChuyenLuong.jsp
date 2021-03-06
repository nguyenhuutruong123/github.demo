<%@page import="java.sql.ResultSet"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@page import="geso.dms.center.beans.khoahuanluyen.IKhoahuanluyenList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%	
	IKhoahuanluyenList obj = (IKhoahuanluyenList) session.getAttribute("obj");	
	ResultSet npps = obj.getNppRs();
	ResultSet vungs = obj.getVungRs();
	ResultSet khuvucs = obj.getKhuvucRs();
	ResultSet kenhs = obj.getKbhRs();
	ResultSet khoahvRs = obj.getKhoahuanluyenRs();
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
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

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


<script language="javascript" type="text/javascript">

	function seach() 
	{
		document.forms['frm'].action.value = 'seach';
		document.forms["frm"].submit();
	}
	function submitform()
	{
		//document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='0' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
		document.forms['frm'].action.value = 'Taomoi';
		document.forms["frm"].submit();
	}
	
	function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var khlIds = document.getElementsByName("khlIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < khlIds.length; i++)
			 {
				 khlIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < khlIds.length; i++)
			 {
				 khlIds.item(i).checked = false;
			 }
		 }
	 }


		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='0' longdesc='cho luu..' style='border-style:outset'> Processing...</a>"; 		  
		 
	
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

	<form name="frm" method="post" action="../../BCChuyenLuong">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%= obj.getUserId() %>'>
			
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Qu???n l?? nh??n s??? > B??o c??o > Chuy???n L????ng nh??n vi??n </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Ch??o m???ng b???n <%= obj.getUserTen() %></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Th??ng b??o</legend>
					<textarea id="errors" name="errors" rows="2"  style="width: 100%;text-align: left; color:#F00 ; font-weight:bold">
						<%= obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left;font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">L????ng nh??n vi??n</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								 <TR>
									<TD class="plainlabel" >Th??ng </TD>
									<TD class="plainlabel">
										<select name="thang" id = "thang" style="width: 50px" >
										<option value= ""> </option>  
										<%
										int k=1;
										for(k=1; k <= 12; k++ ){
											
										  if(obj.getTungay().equals(Integer.toString(k) ) ) {
										%>
											<option value=<%= k %> selected="selected" > <%= k %></option> 
										<%  }else{  %>
											<option value=<%= k %> > <%= k %></option> 
										<% } }%>
										</select>
									</TD>
								</TR>
								<TR>
								  	<TD class="plainlabel">N??m </TD>
							  	  	<TD class="plainlabel">
										<select name="nam" id = "nam" style="width :50px" >
										<option value= ""> </option>  
										<%
										Calendar cal = Calendar.getInstance();
										int year_ = cal.get(Calendar.YEAR);
										for(int n = 2011; n < year_ + 3; n++) {
										  if(obj.getDenngay().equals( Integer.toString(n)) ){									  
										%>
											<option value=<%= n %> selected="selected" > <%=n %></option> 
										<%
										  }else{
										 %>
											<option value=<%= n %> ><%=n %></option> 
										<% } }
										%>
										</select>
							  	  	</TD>
							  </TR>
								 <TR>
									<TD class="plainlabel">V??ng/Mi???n</TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (vungs != null)
													while (vungs.next()) {
														if (vungs.getString("pk_seq").equals(obj.getVungId())) {%>
													<option value="<%=vungs.getString("pk_seq")%>" selected><%=vungs.getString("ten")%></option>
												<%} else {%>
													<option value="<%=vungs.getString("pk_seq")%>"><%=vungs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Khu v???c</TD>
									<TD class="plainlabel">
										<select name="khuvucId" id="khuvucId">
											<option value="" selected>All</option>
											<%if (khuvucs != null)
													while (khuvucs.next()) {
														if (khuvucs.getString("pk_seq").equals(obj.getKvId())) {%>
															<option value="<%=khuvucs.getString("pk_seq")%>" selected><%=khuvucs.getString("ten")%></option>
													<%} else {%>
														<option value="<%=khuvucs.getString("pk_seq")%>"><%=khuvucs.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel">M???c l???y</TD>
									<TD class="plainlabel" colspan="3">
										<select name="muclay" id="muclay">
											<option value="0" selected>Nh??n vi??n b??n h??ng</option>
											<option value="1" >Gi??m s??t b??n h??ng</option>
										</select>
									</TD>									
								</TR>
								<TR>
									<td colspan="4"><a class="button" id ="btnSave"
										href="javascript:submitform();"> <img style="top: -4px;" src="../images/button.png" alt=""> T???o b??o c??o </a></td>
								</TR>
							</TABLE>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
	</form>
<script type="text/javascript">
	dropdowncontent.init('khlId', "right-bottom", 300, "click");
</script>
<%
	if(kenhs !=null) kenhs.close();
	if(vungs !=null) vungs.close();
	if(khuvucs !=null) khuvucs.close();
	if(npps !=null) npps.close();
	
	if(obj!=null)
	{
		obj.DbClose();
		obj = null;
	}
	session.setAttribute("obj", null);
%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>