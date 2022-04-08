<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.sql.ResultSet"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.dms.center.beans.chart.IChart"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%	
	IChart obj = (IChart) session.getAttribute("obj");	
	Hashtable<String, Long> data = (Hashtable<String, Long>)obj.getData();
	Hashtable<String, Long> dataHD = (Hashtable<String, Long>)obj.getNVHoatDong();
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
	
</script>

<script type="text/javascript" src="../scripts/Chart/jsapi"></script>

<script src="../scripts/Chart/jquery.gvChart-0.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
	gvChartInit();
	
	<% if(obj.getThang().trim().length() > 0) { %>
	
		jQuery(document).ready(function(){
			jQuery('#myTable').gvChart({
				chartType: 'LineChart',
				gvSettings: {
					vAxis: {title: 'Thực đạt & chỉ tiêu'},
					hAxis: {title: 'Tháng'},
					width: 900,
					height: 350,
					}
			});
		});
	
	<% } else { %> 
		
		jQuery(document).ready(function(){
			jQuery('#myTable').gvChart({
				chartType: 'LineChart',
				gvSettings: {
					vAxis: {title: 'Thực đạt & chỉ tiêu'},
					hAxis: {title: 'Năm'},
					width: 1000,
					height: 350,
					}
			});
		});
	
	<% } %>
</script>
<style>
	body{
		text-align: center;
		font-family: Arial, sans-serif;
		font-size: 12px;
	}
	
	a{
		text-decoration: none;
		font-weight: bold;
		color: #555;
	}
	
	a:hover{
		color: #000;
	}
	
	div.main{
		margin: auto;
		text-align: left;
		width: 1000px;
	}
	
	.gvChart{
		/*border: 2px solid #850000;
		border-radius: 5px;
		-moz-border-radius: 10px;*/
		width: 1000px;
		
		margin: auto;
		margin-top: 0px;
	}

</style>


<script language="javascript" type="text/javascript">
	function submitform()
	{
		document.forms['frm'].action.value = 'Taomoi';
		document.forms["frm"].submit();
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

	<form name="frm" method="post" action="../../BCChartChiTieuThang">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%= obj.getUserId() %>'>
			
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo quản trị > Báo cáo đồ thị > Thực đạt & chỉ tiêu theo tháng </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= obj.getUserTen() %></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100%;text-align: left; color:#F00 ; font-weight:bold">
						<%= obj.getMsg() %></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left;font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Thực đạt & chỉ tiêu theo tháng</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
								  	<TD class="plainlabel" style="width: 100px">Năm </TD>
							  	  	<TD class="plainlabel">
										<select name="nam" id = "nam" style="width :50px" >
										<option value= ""> </option>  
										<%
										Calendar cal = Calendar.getInstance();
										int year_ = cal.get(Calendar.YEAR);
										for(int n = 2010; n < year_ + 3; n++) {
										  if(obj.getNam().equals( Integer.toString(n)) ){									  
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
								 <TR style="display: none">
									<TD class="plainlabel" >Tháng </TD>
									<TD class="plainlabel">
										<select name="thang" id = "thang" style="width: 50px" >
										<option value= ""> </option>  
										<%
										int k=1;
										for(k=1; k <= 12; k++ ){
											
										  if(obj.getThang().equals(Integer.toString(k) ) ) {
										%>
											<option value=<%= k %> selected="selected" > <%= k %></option> 
										<%  }else{  %>
											<option value=<%= k %> > <%= k %></option> 
										<% } }%>
										</select>
									</TD>
								</TR>
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo </a></td>
								</TR>
							</TABLE>
						</div>
						<hr />
						<% if( data.size() > 0 ) { %>
						<div class="main">
								<table id='myTable'>
								<% if(obj.getThang().trim().length() > 0) { %>
									<caption align="left" >Thực đạt & chỉ tiêu theo tháng</caption>
								<% } else { %> 
									<caption align="left" >Thực đạt & chỉ tiêu theo năm</caption>
								<%} %>
									<thead>
										<tr>
											<th></th>
											<%  
												Enumeration<String> keys = data.keys(); 
												while(keys.hasMoreElements())
												{
													String key = keys.nextElement(); %> 
													<th><%= key %></th>
											<% } %>
										</tr>
									</thead>
									<tbody>
										<tr>
											<th>Chỉ tiêu</th>
											<%  
												Enumeration<String> keyss = data.keys(); 
												while(keyss.hasMoreElements())
												{
													String key = keyss.nextElement(); %> 
													<td><%= data.get(key) %></td>
											<% } %>
										</tr>	
										<tr>
											<th>Thực đạt</th>
											<%  
												Enumeration<String> keysss = dataHD.keys(); 
												while(keysss.hasMoreElements())
												{
													String key = keysss.nextElement(); %> 
													<td><%= dataHD.get(key) %></td>
											<% } %>
										</tr>			
									</tbody>
								</table>
							</div>
							<% } %>
					</div>
				</fieldset>
			</div>
		</div>
	</form>
<%
	if( data != null ) 
	{
		data.clear();
		data = null;
	}
	
	if(obj != null)
	{
		obj.DbClose();
		obj = null;
	}
	session.setAttribute("obj", null);
%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>