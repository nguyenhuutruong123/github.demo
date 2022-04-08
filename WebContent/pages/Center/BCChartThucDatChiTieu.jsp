<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "geso.dms.center.beans.bcchart.*" %>
<%@ page import = "geso.dms.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Trafaco/index.jsp");
	}else
	{ 
		IBcchart obj =(IBcchart)session.getAttribute("obj");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE><%= getServletContext().getInitParameter("TITLENAME") %></TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

	<SCRIPT language="javascript" type="text/javascript">
	
		function submitform()
		{
			document.forms["khtt"].submit();
		}
		function Xem()
		{
			document.forms['khtt'].action.value= 'xem';
			document.forms["khtt"].submit();
		}
	
	</SCRIPT>
	
	<!-- Styles -->
	<style>
	#chartdiv {
	  width: 100%;
	  height: 500px;
	}						
	</style>
	
	<!-- Resources -->
	<script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
	<script src="https://www.amcharts.com/lib/3/serial.js"></script>
	<script src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
	<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css" type="text/css" media="all" />
	<script src="https://www.amcharts.com/lib/3/themes/light.js"></script>
	
	<!-- Chart code -->
	<script>
	var chart = AmCharts.makeChart("chartdiv", {
	  "type": "serial",
	  "theme": "light",
	  "dataDateFormat": "YYYY-MM-DD",
	  "precision": 2,
	  "valueAxes": [{
	    "id": "v1",
	    "title": "Sales",
	    "position": "left",
	    "autoGridCount": false,
	    "labelFunction": function(value) {
	      //return "$" + Math.round(value) + "M";
	      return "" + Math.round(value) + " <%=obj.getDonvitinh()%>";
	    }
	  }
	  /* , 
	  {
	    "id": "v2",
	    "title": "Market Days",
	    "gridAlpha": 0,
	    "position": "right",
	    "autoGridCount": false
	  } */
	  ],
	  
	  "graphs": [ {
	    "id": "g4",
	    "valueAxis": "v1",
	    "lineColor": "#e1ede9",
	    "fillColors": "#e1ede9",
	    "fillAlphas": 1,
	    "type": "column",
	    "title": "Chỉ tiêu",
	    "valueField": "sales1",
	    "clustered": false,
	    "columnWidth": 0.5,
	    "legendValueText": "[[value]]M",
	    //"balloonText": "[[title]]<br /><b style='font-size: 130%'>$[[value]]M</b>"
	    "balloonText": "[[title]]<br /><b style='font-size: 130%'>[[value]] <%=obj.getDonvitinh()%></b>"
	  },
	  {
		    "id": "g3",
		    "valueAxis": "v1",
		    "lineColor": "#62cf73",
		    "fillColors": "#62cf73",
		    "fillAlphas": 1,
		    "type": "column",
		    "title": "Thực đạt",
		    "valueField": "sales2",
		    "clustered": false,
		    "columnWidth": 0.3,
		    "legendValueText": "[[value]]M",
		    //"balloonText": "[[title]]<br /><b style='font-size: 130%'>$[[value]]M</b>"
		    "balloonText": "[[title]]<br /><b style='font-size: 130%'>[[value]] <%=obj.getDonvitinh()%></b>"
		  }
	  /* , {
	    "id": "g1",
	    "valueAxis": "v2",
	    "bullet": "round",
	    "bulletBorderAlpha": 1,
	    "bulletColor": "#FFFFFF",
	    "bulletSize": 5,
	    "hideBulletsCount": 50,
	    "lineThickness": 2,
	    "lineColor": "#20acd4",
	    "type": "smoothedLine",
	    "title": "Market Days",
	    "useLineColorForBulletBorder": true,
	    "valueField": "market1",
	    "balloonText": "[[title]]<br /><b style='font-size: 130%'>[[value]]</b>"
	  }, {
	    "id": "g2",
	    "valueAxis": "v2",
	    "bullet": "round",
	    "bulletBorderAlpha": 1,
	    "bulletColor": "#FFFFFF",
	    "bulletSize": 5,
	    "hideBulletsCount": 50,
	    "lineThickness": 2,
	    "lineColor": "#e1ede9",
	    "type": "smoothedLine",
	    "dashLength": 5,
	    "title": "Market Days ALL",
	    "useLineColorForBulletBorder": true,
	    "valueField": "market2",
	    "balloonText": "[[title]]<br /><b style='font-size: 130%'>[[value]]</b>"
	  } */
	  
	  ],
	  
	  "chartScrollbar": {
	    "graph": "g1",
	    "oppositeAxis": false,
	    "offset": 30,
	    "scrollbarHeight": 50,
	    "backgroundAlpha": 0,
	    "selectedBackgroundAlpha": 0.1,
	    "selectedBackgroundColor": "#888888",
	    "graphFillAlpha": 0,
	    "graphLineAlpha": 0.5,
	    "selectedGraphFillAlpha": 0,
	    "selectedGraphLineAlpha": 1,
	    "autoGridCount": true,
	    "color": "#AAAAAA"
	  },
	  "chartCursor": {
	    "pan": true,
	    "valueLineEnabled": true,
	    "valueLineBalloonEnabled": true,
	    "cursorAlpha": 0,
	    "valueLineAlpha": 0.2
	  },
	  
	  "categoryField": "date",
	  "categoryAxis": {
	    "parseDates": false,
	    "dashLength": 1,
	    "minorGridEnabled": true
	  },
	  "legend": {
	    "useGraphSettings": true,
	    "position": "top"
	  },
	  "balloon": {
	    "borderThickness": 1,
	    "shadowAlpha": 0
	  },
	  "export": {
	   "enabled": true
	  },
	  
	  "dataProvider": [
	  
	     <%= obj.getData_ChartDoanhthu() %>              
	  /* {
	    "date": "MB1",
	    "market1": 71,
	    "market2": 75,
	    "sales1": 5,
	    "sales2": 8
	  }, {
	    "date": "MB2",
	    "market1": 74,
	    "market2": 78,
	    "sales1": 4,
	    "sales2": 6
	  }, {
	    "date": "MB3",
	    "market1": 78,
	    "market2": 88,
	    "sales1": 5,
	    "sales2": 2
	  }, {
	    "date": "MB4",
	    "market1": 85,
	    "market2": 89,
	    "sales1": 8,
	    "sales2": 9
	  }, {
	    "date": "MB5",
	    "market1": 82,
	    "market2": 89,
	    "sales1": 9,
	    "sales2": 6
	  }, {
	    "date": "MB6",
	    "market1": 83,
	    "market2": 85,
	    "sales1": 3,
	    "sales2": 5
	  }, {
	    "date": "MN7",
	    "market1": 88,
	    "market2": 92,
	    "sales1": 5,
	    "sales2": 7
	  }, {
	    "date": "MN8",
	    "market1": 85,
	    "market2": 90,
	    "sales1": 7,
	    "sales2": 6
	  }, {
	    "date": "MT1",
	    "market1": 85,
	    "market2": 91,
	    "sales1": 9,
	    "sales2": 5
	  }, {
	    "date": "MT2",
	    "market1": 80,
	    "market2": 84,
	    "sales1": 5,
	    "sales2": 8
	  }, {
	    "date": "MT3",
	    "market1": 87,
	    "market2": 92,
	    "sales1": 4,
	    "sales2": 8
	  }, {
	    "date": "MT4",
	    "market1": 84,
	    "market2": 87,
	    "sales1": 3,
	    "sales2": 4
	  }, {
	    "date": "HN1",
	    "market1": 83,
	    "market2": 88,
	    "sales1": 5,
	    "sales2": 7
	  }, {
	    "date": "HN2",
	    "market1": 84,
	    "market2": 87,
	    "sales1": 5,
	    "sales2": 8
	  }, {
	    "date": "HN3",
	    "market1": 81,
	    "market2": 85,
	    "sales1": 4,
	    "sales2": 7
	  } */
	  
	  ]
	});
	</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khtt" method="post" action="../../BCChartAllSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="view" value="<%= obj.getView() %>" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align="left" valign="top" bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR height="22">
								<TD align="left" colspan="2" class="tbnavigation">&nbsp;Báo cáo quản trị > Báo cáo chart > Thực đạt chỉ tiêu </TD>
								<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100%" align="left" >
									<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Phạm vi báo cáo &nbsp;</LEGEND>

										<TABLE width="100%" cellpadding="6" cellspacing="0">
								<TR>
								  	<TD class="plainlabel" style="width :100px"  >Năm </TD>
							  	  	<TD class="plainlabel" style="width :200px" >
										<select name="nam" id = "nam" style="width :150px"   onchange="submitform()">
										<%
										Calendar cal = Calendar.getInstance();
										int year_ = cal.get(Calendar.YEAR);
										for(int n = 2016; n < year_ + 5; n++) {
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
							  	
									<TD class="plainlabel" style="width :100px" >Tháng  </TD>
									<TD class="plainlabel" style="width :200px" >
										<select name="thang" id = "thang" style="width: 150px" onchange="submitform()">
										<option value="" ></option> 
										<%
										String[] arr = {"01","02","03","04","05","06","07","08","09","10","11","12"};
										int k=0;
										for(k=0; k < arr.length; k++ ){
											
										  if(obj.getThang().equals(arr[k] ) ) {
											  
											 
											  
										%>
											<option value=<%= arr[k] %> selected="selected" > <%=arr[k] %></option> 
										<%  }else{  %>
											<option value=<%= arr[k] %> > <%= arr[k] %></option> 
										<% } }%> 
										</select>
									</TD>
									
									
									<%
										ResultSet rsT = obj.getTieuChiList();
									if(rsT != null)
									{
									
									%>
										<TD class="plainlabel" style="width :100px" >Tiêu chí</TD>
										<TD class="plainlabel"  >
											<select name="theomuc" id = "theomuc" style="width: 150px"  onchange="submitform()">
											<%while(rsT.next()) { %>				
												<option value="<%= rsT.getString("pk_seq") %>" <%= rsT.getString("pk_seq").equals(obj.getTheomuc()) ?  "selected":""   %> >
														<%= rsT.getString("diengiai") %></option>
											<%} %>
											
											
											</select>
										</TD>
									<%} %>
								</TR>
							
								<TR>
									<TD class="plainlabel" colspan="6">																			
									<a class="button"
										href="javascript:Xem();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Xem Báo cáo
									</a>
									</td>
								</TR>
							</TABLE>
									</FIELDSET>
								</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
				
				<TR>
					<TD width="100%">
						
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Thục đạt chỉ tiêu &nbsp;&nbsp;
								
							</LEGEND>

							<div id="chartdiv"></div>				
							
						</FIELDSET>
					</TD>
				</TR>
				
				
			</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
</BODY>
</html>

<%
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}	
%>
