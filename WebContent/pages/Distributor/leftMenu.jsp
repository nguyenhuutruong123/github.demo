<%@page import="geso.dms.distributor.db.sql.dbutils"%>
<%@page import="java.sql.ResultSet"%>

<%@page import="geso.dms.center.beans.menu.IMenu"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.util.*"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/opv/index.jsp");
	}else{  
		
	 String chuoi = (String)session.getAttribute("chuoi");
	%>

<html>
<head>
<LINK id="sitetheme" rel="stylesheet" href="<%=chuoi %>" type="text/css">
<style type="text/css">
body {
	font-family: verdana, arial, sans-serif;
	font-size: 1em;
	margin: 1px;
	background-color: #ffffff;
}
</style>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>jQuery Accordion Example</title>

<style type="text/css">
/* A few IE bug fixes */
* {
	margin: 0;
	padding: 0;
}

* html ul ul li a {
	height: 80%;
}

* html ul li a {
	height: 80%;
}

* html ul ul li {
	margin-bottom: -1px;
}

body {
	padding-left: 0em;
	font-family: Arial, Helvetica, sans-serif;
}

#theMenu {
	width: 0px;
	height: 0px;
	margin: 0px;
	padding: 0;
}

/* Some list and link styling */
ul li {
	width: 210px;
	line-height: 20pt;
	margin-bottom: 0;
	border-left-width: 0px;
	border-left-style: none;
	border-left-color: #FFFFFF;
	background-color: #80CB9B;
	border-bottom-style: solid;
	border-bottom-color: #FFFFFF;
	border-bottom-width: thin;
	margin-left: 0px;
	padding-top: 0;
	padding-right: 0;
	padding-bottom: 0;
	padding-left: 5px;
	text-indent: 5px;
}

ul li a {
	color: #000000;
	font-size: 9pt;
	background-color: #80CB9B;
	width: 210px;
}

ul li a:hover {
	display: block;
	color: #fff;
	background-color: #80CB9B;
	font-size: small;
	width: 210px;
}

ul ul li {
	width: 210px;
	margin-left: 0px;
	background-position: left center;
	line-height: 18pt;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	border-top-color: #FFFFFF;
	border-right-color: #FFFFFF;
	border-bottom-color: #FFFFFF;
	border-left-color: #FFFFFF;
	background-color: #C5E8CD;
	border-bottom-width: thin;
	padding-left: 0;
	font-family: Arial, Helvetica, sans-serif;
}

ul ul li a {
	display: marker;
	color: #fff;
	padding: 0px;
	font-size: small;
	width: 210px;
}

ul ul li a:hover {
	display: block;
	color: #369;
	padding: 0px;
	font-size: small;
	width: 210px;
}

/* For the xtra menu */
ul ul li a.selected {
	display: marker;
	color: #369;
	padding: 0px;
	font-size: small;
}

ul ul ul li {
	padding: 0;
	width: 210px;
	margin-left: 0px;
	background-position: left center;
	line-height: 20pt;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	border-top-color: #FFFFFF;
	border-right-color: #FFFFFF;
	border-bottom-color: #FFFFFF;
	border-left-color: #FFFFFF;
	background-color: #FFFFFF;
	border-bottom-width: thin;
}

ul ul ul li a {
	display: block;
	color: #fff;
	padding: 0px;
	font-size: small;
	background-color: #FFFFFF;
}

ul ul ul li a:hover {
	display: block;
	color: #369;
	background-color: #CEF6F5;
	padding: 0px;
	font-size: small;
}

ul ul ul li a.selected {
	display: block;
	color: #369;
	background-color: #CEF6F5;
	padding: 0px;
	font-size: small;
}

/* For the sub menu */
ul ul ul ul li {
	border-left: none;
	border-bottom: none;
	padding: 0;
	width: 210px;
	margin-bottom: 0;
	margin-left: 5px;
	background-color: #F4F4F0;
}

ul ul ul ul li a {
	display: block;
	color: #fff;
	padding: 0px;
	font-size: small;
	background-color: #CCCCCC;
}

ul ul ul ul li a:hover {
	display: block;
	color: #369;
	background-color: #CEF6F5;
	padding: 3px 8px;
	font-size: small;
}

ul ul ul ul li a.selected {
	display: block;
	color: #369;
	background-color: #CEF6F5;
	padding: 3px 8px;
	font-size: small;
}

li {
	list-style-type: none;
}

h2 {
	margin-top: 0em;
}
ul li ul li ul li ul
{
 overflow-y:auto;
 overflow-x:hidden;
}

ul li ul li ul li ul
{
 overflow-y:auto;
 overflow-x:hidden;
}
</style>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>

<script type="text/javascript" src="../scripts/jquery.js"></script>
<script type="text/javascript" src="../scripts/accordion.js"></script>
<script type="text/javascript">
	jQuery().ready(function() {
		jQuery("a").click(function(e) {
			jQuery("a").css("color", "black");
			jQuery(this).css("color", "red");
		});

		// applying the settings
		jQuery('#theMenu').Accordion({
			active : 'h3.selected',
			header : 'h3.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		jQuery('#xtraMenu1').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu2').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu3').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu4').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu5').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu6').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		
		jQuery('#xtraMenu7').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		
		jQuery('#xtraMenu8').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		
		jQuery('#xtraMenu9').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		jQuery('#xtraMenu10').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		jQuery('#xtraMenu11').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		jQuery('#xtraMenu12').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		
		jQuery('#subMenu').Accordion({
			active : 'h6.selected',
			header : 'h6.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

	});
</script>

<body >
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->


<ul id="theMenu">
	<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}%>
	<%
		dbutils db = new dbutils();
	 	ResultSet rs = util.getMenuRs(userId,"1", db);
	
		String myMENU = "";
		
		int coSUBMENU = 0;
		int stt = 1;
		int maxHEIGHT = 300;
		   if(stt >= 5 && stt <= 10 ) //Càng xuống dưới thì chiều cao giảm dần
		    maxHEIGHT = 200;
		   else if(stt >= 10 )
		    maxHEIGHT = 150;
	if(rs != null)
		while(rs.next() )
		{
			String level = rs.getString("LEVEL");
			
			String ten = ChuyenNgu.get(rs.getString("TEN"),nnId);
			String servlet = rs.getString("SERVLET");
			String par = rs.getString("PARAMETERS");
			
			if(rs.getString("moLEVEL1").equals("1")) //MENU CHINH
			{
				coSUBMENU = rs.getInt("coSubMenu");
				if(rs.getInt("coSubMenu") > 0 )
				{
					myMENU +=   "<li style='position: static;'> " +
								"	<h3 class='head'><a href='' class='head'>" + ten + " </a></h3> " +
								"	<ul style='display: block;'> " +
								"		<li> " +
								"			<ul id='xtraMenu" + stt + "'> ";	
								
								stt++;
					
					
				}
				else
				{
					myMENU += "<li style='position: static;'> " +
							  "	<h3 class='head'><a href='' class='head'>" + ten + " </a></h3> " +
							  "		<ul style='display: block;'> ";
				}
			}
			else if(rs.getString("moLEVEL2").equals("1"))  //MO SUBMENU
			{
				myMENU += 	"				<li> " +
							"					<h4 class='head' ><a href=''>" + ten + "</a></h4> " +
							"					<ul style='display: none;max-height: " + maxHEIGHT + "px; '> " ;
			}
			else  //UNG DUNG
			{
				String h = Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript")   + servlet + "?userId=" + userId + par );
				myMENU += " <li> " +
						  "		<h5 class='head' > " +
								  "			<a href='../../RouterSvl?task="+ h +"' target='content' >" + ten + "</a> " +
						  "		</h5> " +
						  "	</li>";
						  
				if( rs.getString("maxSTT").equals(rs.getString("soTT")) )  //DONG THE SUB MENU
				{
					if(coSUBMENU > 0)
					{
						
						myMENU +=   "	</ul> " +
									"</li> ";
					}
				}	
				
				

				if( rs.getString("totalSTT").equals(rs.getString("max_total_STT")) )  //DONG THE MENU LEVEL 1
				{
					if(coSUBMENU > 0)
					{
						myMENU +=   "			</ul> " +
									"		</li> " +
									"	</ul> " +
									"</li> ";
				    }
					else
					{
						myMENU +=   "	</ul> " +
									"</li> ";
					}
				}	
			}
		}

		db.shutDown();
		
	%>
	<%= myMENU %>
</ul>

</body>
</html>
<% } %>

