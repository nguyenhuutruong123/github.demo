<%@page import="java.util.Map"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.routesumaryreport.IRouteSumaryReport"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	IRouteSumaryReport obj = (IRouteSumaryReport) session.getAttribute("obj");
	
	Hashtable<String, String> HashStatus = obj.getHashStatus();
	ResultSet rsKhuVuc = obj.getArea();
	ResultSet rsNPP = obj.getDistributor();
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
<script type="text/javascript">
		$(document).ready(function() {	
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


<script language="javascript" type="text/javascript">	
	function change(){
		document.forms['frm'].action.value="search";
		document.forms["frm"].submit();
	}
	function submitform() {
		if (document.forms["frm"]["khuvucs"].value.length == 0) {
			setErrors("Vui lòng chọn khu vực");
			return;
		}
		if (document.forms["frm"]["hoatdong"].value.length == 0) {
			setErrors("Vui lòng chọn trạng thái ");
			return;
		}

		if (document.forms["frm"]["npps"].value.length == 0) {
			setErrors("Vui lòng chọn nhà phân phối");
			return;
		}				
		document.forms['frm'].action.value="create";
		document.forms["frm"].submit();
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}	
</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="frm" method="post" action="../../RouteSumaryReport">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value="" />
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">&nbsp;Báo cáo quản trị &gt; Báo cáo khác &gt; Thống kê tuyến</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn : <%=userTen%></div>
			</div>
			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="#"> <img
					src="../images/Back30.png" alt="Quay ve" title="Quay ve"
					border="1px" longdesc="Quay ve" style="border-style: outset"></A>
				<A href="javascript:saveform()"> <IMG src="../images/Save30.png"
					title="Luu lai" alt="Luu lai" border="1px"
					style="border-style: outset"></A>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" value="<%=session.getAttribute("errors")%>"
						name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Thống kê tuyến</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">	
								<TR>
									<TD class="plainlabel">Khu vực</TD>
									<TD class="plainlabel">
									<select name="khuvucs" id="khuvucs" onchange="change()" style="width: 200px;" >
											<option value="" selected="selected" disabled="disabled" >Chọn</option>
											<%
												if (rsKhuVuc != null) {
													while (rsKhuVuc.next()) {
														if (rsKhuVuc.getString("PK_SEQ").equals(obj.getKhuVuc())) {
											%>
														<option selected="selected" value="<%=rsKhuVuc.getString("PK_SEQ")%>">	<%=rsKhuVuc.getString("TEN")%></option> 
													<%
 														} else {
 													%>												
													<option value="<%=rsKhuVuc.getString("PK_SEQ")%>"><%=rsKhuVuc.getString("TEN")%></option>												
											 <%
																							 	}
																							 		}
																							 	}
																							 %>
									</select></TD>									
								</TR>
								<TR>									
									<TD class="plainlabel">Trạng thái</TD>
									<TD class="plainlabel">
									<select name="hoatdong" onchange="change()" style="width: 200px;" id="loaiCt">
											<%
												for (Map.Entry<String, String> map : HashStatus.entrySet()) {
													if (obj.getStatus().equals(map.getKey())) {	%>
													<option value="<%=map.getKey()%>" selected="selected"><%=map.getValue()%></option>
													<%} else {%>
														<option value="<%=map.getKey()%>"><%=map.getValue()%></option>
													<%}}%>
									</select>
									</TD>									
								</TR>								
								<TR>
									<TD class="plainlabel">Nhà phân phối</TD>
									<TD class="plainlabel">
									<select name="npps" style="width: 200px;" id="loaiCt">
											<option value="" disabled="disabled" selected>Chọn</option>
											<%
												if (rsNPP != null) {
													while (rsNPP.next()) {
														if (rsNPP.getString("PK_SEQ").equals(obj.getDistributor())) {
											%>
														<option selected="selected" value="<%=rsNPP.getString("PK_SEQ")%>"><%=rsNPP.getString("TEN")%></option> 
													<%
 														} else {
 													%>												
													<option value="<%=rsNPP.getString("PK_SEQ")%>"><%=rsNPP.getString("TEN")%></option>												
											<%
																								}
																									}
																								}
																							%>
										</select>
									</TD>								
								</TR>			
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo
									</a></td>
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
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%
	try{
		if(rsKhuVuc!=null){
		rsKhuVuc.close();}
		if(rsNPP!=null){
		rsNPP.close();}
		if(obj!=null){obj.DBClose();
		obj = null;}
		session.setAttribute("obj",null);
	}catch(Exception er){
	
}
%>