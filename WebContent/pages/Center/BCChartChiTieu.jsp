<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.sql.ResultSet"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.dms.center.beans.chart.IChart"%>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%	
	IChart obj = (IChart) session.getAttribute("obj");	
	Hashtable<String, Long> data = (Hashtable<String, Long>)obj.getData();
	Hashtable<String, Long> dataHD = (Hashtable<String, Long>)obj.getNVHoatDong();
	String[] chuoingay=obj.getmangthang();
	String[] chuoisku=obj.getmangsku();
	String[] chuoitensku=obj.getmangTensku();
	ResultSet rs=obj.GetRsChiTieu();
	ResultSet rsDSSecYear=obj.GetRsDSSecYeer();
	ResultSet rsDSPriYear=obj.GetRsDSPriYeer();
	ResultSet rsDSSKU=obj.GetRsDSSKU();
	ResultSet rsTonKhoKV=obj.getRsTonKhoKhuVuc();
	
	String[] chuoiidKhuvuc=obj.getmangIDKhuvuc();
	String[] chuoiTenKhuvuc=obj.getmangTenKhuvuc();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("BCChartChiTieu","", userId);
	
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
	

		
		jQuery(document).ready(function(){
			jQuery('#myTable').gvChart({
				chartType: 'ColumnChart',
				gvSettings: {
					vAxis: {title: 'Th???c ?????t & ch??? ti??u'},
					hAxis: {title: 'Th??ng'},
					width: 1200,
					height:500
					}
			});
		});


		jQuery(document).ready(function(){
			jQuery('#myTable1').gvChart({
				chartType: 'LineChart',
				gvSettings: {
					vAxis: {title: 'Th???c ?????t Sec'},
					hAxis: {title: 'N??m Th??ng'},
					width: 1200,
					height:500
					}
			});
		});

		jQuery(document).ready(function(){
			jQuery('#myTable2').gvChart({
				chartType: 'LineChart',
				gvSettings: {
					vAxis: {title: 'Th???c ?????t Pri'},
					hAxis: {title: 'N??m Th??ng'},
					width: 1200,
					height:500
					}
			});
		});
		
		jQuery(document).ready(function(){
			jQuery('#myTable3').gvChart({
				chartType: 'PieChart',
				gvSettings: {
					vAxis: {title: 'S???n L?????ng SKU'},
					hAxis: {title: 'Nam'},
					width: 1200,
					height:500
					}
			});
		});
		jQuery(document).ready(function(){
			jQuery('#myTable4').gvChart({
				chartType: 'PieChart',
				gvSettings: {
					vAxis: {title: 'T???n kho theo khu v???c'},
					hAxis: {title: 'Nam'},
					width: 1200,
					height:500
					}
			});
		});
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
		/* margin: auto;
		*/
		text-align: left;
		width: 1200px;
	}
	
	.gvChart{
		/*border: 2px solid #850000;
		border-radius: 5px;
		-moz-border-radius: 10px;
		
			margin: auto;*/
		width: 1200px;
		
	
		margin-left:0px;
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

	<form name="frm" method="post" action="../../BCChartChiTieu">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%= obj.getUserId() %>'>
			
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation">B??o c??o qu???n tr??? > B??o c??o kh??c > B??o c??o Chart </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Ch??o m???ng b???n <%= obj.getUserTen() %></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Th??ng b??o</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100%;text-align: left; color:#F00 ; font-weight:bold">
						<%= obj.getMsg() %></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left;font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Th???c ?????t & ch??? ti??u khu v???c</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
								  	<TD class="plainlabel">N??m </TD>
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
								 <TR>
									<TD class="plainlabel" >Th??ng </TD>
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
										href="javascript:submitform();"> <img style="top: -4px;" src="../images/button.png" alt=""> T???o b??o c??o </a></td>
								</TR>
							</TABLE>
						</div>
						<hr />
						
						<div class="main">
								<table align="left" id='myTable'>
								
									<caption align="left" >Th???c ?????t & ch??? ti??u (S???n l?????ng Kg) </caption>
								
									<thead>
										<tr>
											<th></th>
											<%  
												for(int j=11;j>=0;j--){ %>
													<th><%= chuoingay[j] %></th>
											<%  }%>
										</tr>
									</thead>
									<tbody>
										<%while (rs.next()){ %>
										<tr>
											<th><%=rs.getString("type") %></th>
											<%  
												for(int j=11;j>=0;j--){ %>
													<td><%= rs.getDouble(chuoingay[j]) %></td>
											<%  }%>
										</tr>	
											<%} %>
									</tbody>
								</table>
							</div>
						
						<div class="main">
								<table align="left" id='myTable1'>
								
									<caption align="left" >Th???c ?????t Sec So V???i N??m Tr?????c </caption>
								
									<thead>
										<tr>
											<th></th>
											<%  
												for(int j=11;j>=0;j--){ %>
													<th><%= chuoingay[j] %></th>
											<%  }%>
										</tr>
									</thead>
									<tbody>
										<%while (rsDSSecYear.next()){ %>
										<tr>
											<th><%=rsDSSecYear.getString("type") %></th>
											<%  
												for(int j=11;j>=0;j--){ %>
													<td><%= rsDSSecYear.getDouble(chuoingay[j]) %></td>
											<%  }%>
										</tr>	
											<%} %>
									</tbody>
								</table>
							</div>
						
						
						<div class="main">
								<table align="left" id='myTable2'>
								
									<caption align="left" >Th???c ?????t Pri So V???i N??m Tr?????c </caption>
								
									<thead>
										<tr>
											<th></th>
											<%  
												for(int j=11;j>=0;j--){ %>
													<th><%= chuoingay[j] %></th>
											<%  }%>
										</tr>
									</thead>
									<tbody>
										<%while (rsDSPriYear.next()){ %>
										<tr>
											<th><%=rsDSPriYear.getString("type") %></th>
											<%  
												for(int j=11;j>=0;j--){ %>
													<td><%= rsDSPriYear.getDouble(chuoingay[j]) %></td>
											<%  }%>
										</tr>	
											<%} %>
									</tbody>
								</table>
							</div>
						
						
							<div class="main">
								<table align="left" id='myTable3'>
								
									<caption align="left" >S???n l?????ng SKU </caption>
								
									<thead>
										<tr>
											<th></th>
											<%  
												for(int j=0;j<=chuoitensku.length-1;j++){
												if( chuoitensku[j]!=null){
													%>
													<th><%= chuoitensku[j] %></th>
											<% 
											}}
											%>
										</tr>
									</thead>
									<tbody>
										<%while (rsDSSKU.next()){ %>
										<tr>
											<th><%=rsDSSKU.getString("type") %></th>
											<%  
											for(int j=0;j<=chuoisku.length-1;j++){
												if( chuoisku[j]!=null){ %>
													<td><%= rsDSSKU.getDouble(chuoisku[j]) %></td>
											<%
												}
											}%>
										</tr>	
											<%} %>
									</tbody>
								</table>
							</div>
							
							<div class="main">
								<table align="left" id='myTable4'>
								
									<caption align="left" >T???n kho theo khu v???c</caption>
								
									<thead>
										<tr>
											<th></th>
											<%  
												for(int j=0;j<=chuoiTenKhuvuc.length-1;j++){
												if( chuoiTenKhuvuc[j]!=null){
													%>
													<th><%= chuoiTenKhuvuc[j] %></th>
											<% 
											}}
											%>
										</tr>
									</thead>
									<tbody>
										<%while (rsTonKhoKV.next()){ %>
										<tr>
											<th><%=rsTonKhoKV.getString("type") %></th>
											<%  
											for(int j=0;j<=chuoiidKhuvuc.length-1;j++){
												if( chuoiidKhuvuc[j]!=null){ %>
													<td><%= rsTonKhoKV.getDouble(chuoiidKhuvuc[j]) %></td>
											<%
												}
											}%>
										</tr>	
											<%} %>
									</tbody>
								</table>
							</div>
					</div>
				</fieldset>
			</div>
		</div>
	</form>
	
	
<%
	if( data != null ) { data.clear(); data = null; }
	if( rs != null ) { rs.close(); rs = null; }
	if( rsDSSecYear != null ) { rsDSSecYear.close(); rsDSSecYear = null; }
	if( rsDSPriYear != null ) { rsDSPriYear.close(); rsDSPriYear = null; }
	if( rsDSSKU != null ) { rsDSSKU.close(); rsDSSKU = null; }
	if( rsTonKhoKV != null ) { rsTonKhoKV.close(); rsTonKhoKV = null; }
	if( dataHD != null ) { dataHD.clear(); dataHD = null; }
	
	if(obj != null){
		obj.DbClose();
		obj = null;
	}
	session.setAttribute("obj", null);
	}
%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>