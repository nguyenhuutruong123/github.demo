<%@page import="geso.dms.center.beans.menu.IMenu"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.util.*"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{  
	 //IMenu obj = (IMenu)session.getAttribute("obj");
	 //int[] mang = obj.getMang(); 
	 int mang[] = util.quyen_ungdung(userId);
	 System.out.println("mang :"+mang[1]);
	 String chuoi=(String)session.getValue("chuoi");
	 if(chuoi.equals("")){
		 chuoi="../css/style1024.css";
	 }
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
</style>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>

<script type="text/javascript" src="../scripts/jquery.js"></script>
<script type="text/javascript" src="../scripts/accordion.js"></script>
<script type="text/javascript">


jQuery().ready(function(){	
	// applying the settings
	jQuery('#theMenu').Accordion({
		active: 'h3.selected',
		header: 'h3.head',
		alwaysOpen: false,
		animated: true,
		showSpeed: 400,
		hideSpeed: 800
	});
	jQuery('#xtraMenu1').Accordion({
		active: 'h4.selected',
		header: 'h4.head',
		alwaysOpen: false,
		animated: true,
		showSpeed: 400,
		hideSpeed: 800
	});

	jQuery('#xtraMenu2').Accordion({
		active: 'h4.selected',
		header: 'h4.head',
		alwaysOpen: false,
		animated: true,
		showSpeed: 400,
		hideSpeed: 800
	});

	jQuery('#xtraMenu3').Accordion({
		active: 'h4.selected',
		header: 'h4.head',
		alwaysOpen: false,
		animated: true,
		showSpeed: 400,
		hideSpeed: 800
	});

	jQuery('#xtraMenu4').Accordion({
		active: 'h4.selected',
		header: 'h4.head',
		alwaysOpen: false,
		animated: true,
		showSpeed: 400,
		hideSpeed: 800
	});

	jQuery('#xtraMenu5').Accordion({
		active: 'h4.selected',
		header: 'h4.head',
		alwaysOpen: false,
		animated: true,
		showSpeed: 400,
		hideSpeed: 800
	});

	jQuery('#xtraMenu6').Accordion({
		active: 'h4.selected',
		header: 'h4.head',
		alwaysOpen: false,
		animated: true,
		showSpeed: 400,
		hideSpeed: 800
	});

	jQuery('#subMenu').Accordion({
		active: 'h6.selected',
		header: 'h6.head',
		alwaysOpen: false,
		animated: true,
		showSpeed: 400,
		hideSpeed: 800
	});
	

});	
</script>
<script type="text/javascript">
var ma,chuoi;
function goiham(id,st)
{
	ma = id;
	chuoi =st;
	goi();
}
function goi()
{
	ajaxOption(ma, chuoi);
}
	function ajaxOption(id, str)
		{
			var xmlhttp;
			if (window.XMLHttpRequest)
			{// code for IE7+, Firefox, Chrome, Opera, Safari
			   xmlhttp = new XMLHttpRequest();
			}
			else
			{// code for IE6, IE5
			   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange=function()
			{
			   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			   {
			       document.getElementById('chuadoc').innerHTML = xmlhttp.responseText;
			   }
			}
			xmlhttp.open("POST","../../ThongbaoAjax?q=" + str + "&id=" + id,true);
			xmlhttp.send();
		}
</script>
<body onload="goiham('<%=userId%>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->
', 'xxx')">

<ul id="theMenu">
	<li style="position: static;">

	<h3 class="head"><a href="" class="head">D??? li???u n???n
	</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu1">
			<li>
			<h4 class="head" id="dlncbId"><a href="">C?? b???n </a></h4>
			<ul style="display: none;">
				<li>
				<%if(mang[1]==1){ %>
				<h5 class="head" id="nccId"><a
					href="../../NhacungcapSvl?userId=<%=userId %>" target="content">Nh??
				Cung C???p</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[2]==1){ %>
				<h5 class="head" id="dvkdId"><a
					href="../../DonvikinhdoanhSvl?userId=<%=userId %>" target="content">????n
				v??? Kinh doanh</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[3]==1){ %>
				<h5 class="head" id="kbkId"><a
					href="../../KhoSvl?userId=<%=userId %>" target="content">Khai
				b??o kho Nh?? Ph??n Ph???i</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[4]==1){ %>
				<h5 class="head" id="ddkdId"><a
					href="../../KhoasotudongSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Thi???t l???p kh??a s???</a></h5>
				<%} %>
				</li>
				
				<li>
				<%if(mang[4]==1){ %>
				<h5 class="head" id="ddkdId"><a
					href="../../KhoahuanluyenSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Kh??a hu???n luy???n / K??? n??ng</a></h5>
				<%} %>
				</li>
	
			</ul>
			</li>
			<li>
			<h4 class="head" id="dlnspId"><a href="">S???n ph???m</a></h4>
			<ul style="display: none;">
				<li>
				<%if(mang[5]==1){ %>
				<h5 class="head" id="nhId"><a
					href="../../NhanhangSvl?userId=<%=userId %>" target="content">Nh??n
				h??ng</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[6]==1){ %>
				<h5 class="head" id="clId"><a
					href="../../ChungloaiSvl?userId=<%=userId %>" target="content">Ch???ng
				lo???i</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[7]==1){ %>
				<h5 class="head" id="dvdlspId"><a
					href="../../DonvidoluongSvl?userId=<%=userId %>" target="content">????n
				v??? ??o l?????ng </a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[8]==1){ %>
				<h5 class="head" id="ttspId"><a
					href="../../ThongtinsanphamSvl?userId=<%=userId %>"
					target="content">Th??ng tin s???n ph???m </a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[9]==1){ %>
				<h5 class="head" id="nspId"><a
					href="../../NhomsanphamSvl?userId=<%=userId %>" target="content">Nh??m
				s???n ph???m</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[10]==1){ %>
				<h5 class="head" id="bgblcId"><a
					href="../../BanggiabanlechuanSvl?userId=<%=userId %>"
					target="content">B???ng gi?? b??n l??? chu???n</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[11]==1){ %>
				<h5 class="head" id="bgmnppId"><a
					href="../../BanggiamuanppSvl?userId=<%=userId %>" target="content">B???ng
				gi?? b??n cho NPP</a></h5>
				<%} %>
				</li>

			</ul>
			</li>
			<li>
			<h4 class="head" id="dlnHtkdId"><a href="">Kinh doanh </a></h4>
			<ul style="display: none;">
				<li>
				<%if(mang[12]==1){ %>
				<h5 class="head" id="kbhId"><a
					href="../../KenhbanhangSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">K??nh b??n h??ng</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[13]==1){ %>
				<h5 class="head" id="vmId"><a
					href="../../VungmienSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Mi???n</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[14]==1){ %>
				<h5 class="head" id="kvId"><a
					href="../../KhuvucSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">V??ng</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[15]==1){ %>
				<h5 class="head" id="gsbhId"><a
					href="../../GiamsatbanhangSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Gi??m s??t b??n h??ng</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[16]==1){ %>
				<h5 class="head" id="ddkdId"><a
					href="../../DaidienkinhdoanhSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Nh??n vi??n b??n h??ng</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[17]==1){ %>
				<h5 class="head" id="nppId"><a
					href="../../NhaphanphoiSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Nh?? ph??n ph???i</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[18]==1){ %>
				<h5 class="head" id="nppId"><a
					href="../../sitecode_convSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Nh?? ph??n ph???i ERP </a></h5>
				<%} %>
				</li>
				     
				
				      <li>
					      <%if(mang[79]==1){ %>
	            <h5 class="head" ><a
	            href="../../BmSvl?userId=<%=userId %>;userTen=<%= userTen %>" target="content">Gi??m ?????c mi???n</a></h5>
	  				 <%} %>
     				 </li>
				 <li>
				       <%if(mang[80]==1){ %>
            		<h5 class="head" ><a
         		   href="../../ASMSvl?userId=<%=userId %>;userTen=<%= userTen %>" target="content">ASM</a></h5>
      			  <%} %>
   				   </li>
				<li>
				<%if(mang[19]==1){ %>
				<h5 class="head" id="lchId"><a
					href="../../LoaicuahangSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Lo???i c???a h??ng</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[20]==1){ %>
				<h5 class="head" id="hchId"><a
					href="../../HangcuahangSvl??userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">H???ng c???a h??ng</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[21]==1){ %>
				<h5 class="head" id="vtchId"><a
					href="../../VitricuahangSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">V??? tr?? c???a h??ng</a></h5>
				<%} %>
				</li>
				
				<li>
				<%if(mang[22]==1){ %>
				<h5 class="head" id="nkhId"><a
					href="../../NhomkhachhangSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Nh??m kh??ch h??ng</a></h5>
				<%} %>
				</li>
				
				<li>
				<%if(mang[87]==1){ %>
					<h5 class="head" id="nkhId"><a href="../../ChietkhauSvl?userId=<%=userId %>;userTen=<%= userTen %>" target="content" >M???c chi???t kh???u</a></h5>
					<%} %>
				</li>
				
				<li>
				<%if(mang[75]==1){ %>
				<h5 class="head" id="nkhId"><a href="../../TieuChiThuongSvl?userId=<%=userId %>;userTen=<%= userTen %>" target="content">KPI</a></h5>
				<%} %>
				</li>
				
				<li>
				<%if(mang[88]==1){ %>
				<h5 class="head" id="nkhId"><a href="../../TieuchithuongSKUSvl?userId=<%=userId %>;userTen=<%= userTen %>" target="content">Ti??u ch?? th?????ng SKU</a></h5>
				<%} %>
				</li>

			</ul>
			</li>
		</ul>
		</li>

	</ul>
	</li>
	<li>
	<h3 class="head "><a href="">Qu???n l?? ch??? ti??u</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu">
			<li>
			<%if(mang[23]==1){ %>
			<h5 class="head" id="ddkdId"><a
				href="../../chitieuttchovungSvl?userId=<%=userId %>&userTen=<%= userTen %>"
				target="content">Ch??? ti??u cho khu v???c</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[24]==1){ %>
			<h5 class="head" id="ddkdId"><a
				href="../../ChiTieuSvl?userId=<%=userId %>&userTen=<%= userTen %>"
				target="content">Ch??? ti??u cho NPP</a></h5>
			<%} %>
			</li>
			
			<li>
			<%if(mang[90]==1){ %>
			<h5 class="head"><a
				href="../../ChiTieuSKUInSvl?userId=<%=userId %>&view=TT" target="content">Ch??? ti??u SKU In</a></h5>
			<%} %>
			</li>
			
			<li>
			<%if(mang[74]==1){ %>
			<h5 class="head"><a
				href="../../ThucDatVaChiTieuSKUIn?userId=<%=userId %>" target="content">Th???c ?????t & ch??? ti??u SKU</a></h5>
			<%} %>
			</li>
			
			<li>
			<%if(mang[25]==1){ %>
			<h5 class="head"><a
				href="../../DailySalesSvl?userId=<%=userId %>" target="content">Th???c ?????t & ch??? ti??u NPP</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[26]==1){ %>
			<h5 class="head"><a
				href="../../Salesreperformance?userId=<%=userId %>" target="content">Th???c hi???n ch??? ti??u SR</a></h5>
			<%} %>
			</li>
		
			<%if(mang[78]==1){ %>
			<li>
            	<h5 class="head"><a href="../../MokhaibaochitieuSvl?userId=<%=userId %>" target="content">Cho ph??p khai b??o</a></h5>   		
     		 </li>
     		 <%} %>
     		 
     		 <%if(mang[81]==1){ %>
            <li>
                <h5 class="head">
                	<a href="../../SSPerformance?userId=<%=userId %>" target="content">Th???c hi???n ch??? ti??u SS</a>
                </h5>
            </li>
            <%} %>	
            
             <%if(mang[82]==1){ %>
     		 <li>
                  <h5 class="head">
                  	<a href="../../ASMPerformance?userId=<%=userId %>" target="content">Th???c hi???n ch??? ti??u ASM</a>  
			 	</h5>
            </li>
			<%}%>
			
			 <%if(mang[83]==1){ %>
            <li>
               <h5 class="head">
               	<a href="../../BMPerformance?userId=<%=userId %>" target="content">Th???c hi???n ch??? ti??u BM</a>
               </h5>
            </li>
			 <%} %>
			 <li>
				<%if(mang[57]==1){ %> <h5 class="head" id=""><a href="../../TieuchithuongSKUReportSvl?userId=<%=userId %>"
					target="content">Th?????ng SKU t???p trung </a></h5>
				<%} %>
			</li>
		</ul>
		</li>
	</ul>
	</li>
	<li style="position: static;">
	<h3 class="head "><a href="">Qu???n l?? t???n kho</a></h3>
	<ul style="display: block;">

		<li>
		<%if(mang[29]==1){ %>
		<h5 class="head" id="ddnId"><a
			href="../../DuyetdctkSvl?userId=<%=userId %>" target="content">Duy???t
		??i???u ch???nh t???n kho </a></h5>
		<%} %>
		</li>

		<li>
		<ul id="xtraMenu2">

			<li>
			<%if(mang[27]==1){ %>
			<h5 class="head" id="htNppId"><a
				href="../../Duyetdontrahang?userId=<%=userId %>" target="content">Duy???t
			h??ng tr??? v??? NCC</a></h5>
			<%} %>
			</li>
			<li>
			<%if(mang[28]==1){ %>
			<li>
			<h4 class="head" id="dlncbId"><a href="">Duy???t h??ng
			tr??? v??? NPP </a></h4>
			<ul style="display: none;">
				<li>
				<h5 class="head" id="htNppId"><a
					href="../../DuyettradonhangSvl?userId=<%=userId %>"
					target="content">Duy???t tr??? ????n h??ng</a></h5>
				</li>
				<li>
				<h5 class="head" id="htNppId"><a
					href="../../DuyettrasanphamSvl?userId=<%=userId %>"
					target="content">Duy???t tr??? s???n ph???m</a></h5>
				</li>
			</ul>
			</li>
			<%} %>
			</li>


			<li>
			<h4 class="head" id="dlncbId"><a href="">B??o c??o </a></h4>
			<ul style="display: none;">

				<li>
				<%if(mang[71]==1){ %>
				<h5 class="head" id="kbkId"><a
					href="../../StockAlarm?userId=<%=userId %>" target="content">C???nh
				b??o h??ng t???n kho</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[30]==1){ %>
				<h5 class="head"><a
					href="../../IventoryTT_Svl?userId=<%=userId %>" target="content">T???n
				kho hi???n t???i </a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[31]==1){ %>
				<h5 class="head"><a
					href="../../DailyStockTTSvl?userId=<%=userId %>" target="content">T???n
				kho h??ng ng??y</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[32]==1){ %>
				<h5 class="head" id="kbkId"><a
					href="../../StockInTransit?userId=<%=userId %>" target="content">H??ng
				ch??a nh???p kho</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[33]==1){ %>
				<h5 class="head"><a
					href="../../Dailypurchase_TTSvl?userId=<%=userId %>"
					target="content"> H??ng nh???p kho</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[73]==1){ %>
				<h5 class="head" id="kbkId"><a
					href="../../SecondarySalesPIRTT?userId=<%=userId %>"
					target="content">B??o c??o xu???t nh???p t???n</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[34]==1){ %>
				<h5 class="head" id="kbkId"><a
					href="../../AdjustInventoryReport?userId=<%=userId %>"
					target="content">??i???u ch???nh t???n kho</a></h5>
				<%} %>
				</li>
			</ul>
			</li>

		</ul>
		</li>
	</ul>
	</li>
	<li style="position: static;">
	<h3 class="head "><a href="">Qu???n l?? b??n h??ng</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu3">
			  <li>
                  <h5 class="head" id="qltkId4">
                  <%if(mang[77]==1){ %>
                  <a href="../../PhanbodathangSvl?userId=<%=userId %>"
                        target="content">Ph??n b??? ?????t h??ng </a> 
                        <%} %>
                  </h5>
                  </li>
			<li>
			<h5 class="head" id="qltkId4">
			<%if(mang[36]==1){ %> <a href="../../DonmuahangSvl?userId=<%=userId %>"
				target="content">Duy???t ????n h??ng </a> <%} %>
			</h5>
			</li>
			<li>
			<%if(mang[37]==1){ %>
			<h5 class="head"><a
				href="../../TheodoithieuhangSvl?userId=<%=userId %>"
				target="content">Theo d??i thi???u h??ng </a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[38]==1){ %>
			<h5 class="head"><a
				href="../../BaocaocungungSvl?userId=<%= userId %>" target="content">M???c
			????? cung ???ng </a></h5>
			<%} %>
			</li>
	
		</ul>
		</li>
	</ul>
	</li>

	<li style="position: static;">
	<h3 class="head"><a href="">Qu???n l?? khuy???n m??i</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu4">
			<li>
			<%if(mang[39]==1){ %>
			<h5 class="head" id=""><a
				href="../../NhomkhuyenmaiSvl?userId=<%=userId %>" target="content">Nh??m s???n ph???m khuy???n m??i</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[40]==1){ %>
			<h5 class="head"><a
				href="../../nhomkhachhangkmSvl?userId=<%=userId %>" target="content">Nh??m kh??ch h??ng KM</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[41]==1){ %>
			<h5 class="head" id=""><a
				href="../../DieukienkhuyenmaiSvl?userId=<%=userId %>"
				target="content">??i???u ki???n khuy???n m??i</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[42]==1){ %>
			<h5 class="head" id=""><a
				href="../../TrakhuyenmaiSvl?userId=<%=userId %>" target="content">Tr??? khuy???n m??i</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[43]==1){ %>
			<h5 class="head" id=""><a
				href="../../CtkhuyenmaiSvl?userId=<%=userId %>" target="content">Ch????ng tr??nh khuy???n m??i</a></h5>
			<%} %>
			</li>
			
			<%-- <li>
			<%if(mang[43]==1){ %>
			<h5 class="head" id=""><a
				href="../../NhomctkhuyenmaiSvl?userId=<%=userId %>" target="content">Nh??m CT khuy???n m??i</a></h5>
			<%} %>
			</li> --%>

			<li>
			<%if(mang[44]==1){ %>
			<h5 class="head" id=""><a
				href="../../PhanbokhuyenmaiSvl?userId=<%=userId %>" target="content">Ph??n
			b??? khuy???n m??i</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[45]==1){ %>
			<h5 class="head"><a href="../../DieuchuyenkhuyenmaiSvl?userId=<%=userId %>" target="content">??i???u chuy???n ng??n s??ch</a></h5>
			<%} %>
			</li>

			<li>
			<h4 class="head" id=""><a href="" target="">B??o c??o</a></h4>
			<ul>
				<li>
				<%if(mang[46]==1){ %>
				<h5 class="head" id=""><a href="../../UsingPromoTT?userId=<%= userId %>" target="content">S??? d???ng v?? chi tr??? KM</a></h5>
				<%} %>
				</li>
				
				
				<li>
				<%if(mang[63]==1){ %>
				<h5 class="head" id=""><a href="../../UsingPromotionTTSvl?userId=<%= userId %>" target="content">S??? d???ng khuy???n m??i</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[47]==1){ %>
				<h5 class="head" id=""><a href="../../PromotionReportTTSvl?userId=<%=userId %>" target="content">Xu???t khuy???n m??i</a></h5>
				<%} %>
				</li>
				
				<li>
				<%if(mang[61]==1){ %>
				<h5 class="head" id=""><a href="../../ThanhToanKhyenMaiSvl?userId=<%=userId %>" target="content">Thanh to??n khuy???n m??i</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[48]==1){ %>
				<h5 class="head" id=""><a href="../../AccumulatedPromotion?userId=<%= userId %>" target="content">Tr??? khuy???n m??i t??ch l??y </a></h5>
				<%} %>
				</li>

			</ul>

			</li>


		</ul>
		</li>
	</ul>
	</li>

	<li style="position: static;">
	<h3 class="head "><a href="">Qu???n l?? tr??ng b??y</a></h3>
	<ul style="display: block;">
		<li>
		<%if(mang[49]==1){ %>
		<h5 class="head" id=""><a
			href="../../NhomsptrungbaySvl?userId=<%=userId %>" target="content">??i???u ki???n tr??ng b??y</a></h5>
		<%} %>
		</li>
		<li>
		<%if(mang[50]==1){ %>
		<h5 class="head" id=""><a
			href="../../TratrungbaySvl?userId=<%=userId %>" target="content">Tr??? tr??ng b??y</a></h5>
		<%} %>
		</li>
		<li>
		<%if(mang[51]==1){ %>
		<h5 class="head" id=""><a
			href="../../CttrungbaySvl?userId=<%=userId %>" target="content">Ch????ng tr??nh tr??ng b??y</a></h5>
		<%} %>
		</li>
		<li>
		<%if(mang[52]==1){ %>
		<h5 class="head" id=""><a
			href="../../PhanbotrungbaySvl?userId=<%=userId %>" target="content">Ph??n b??? su???t tr??ng b??y</a></h5>
		<%} %>
		</li>
		<li>
		<%if(mang[53]==1){ %>
		<h5 class="head" id=""><a
			href="../../DuyettratrungbaySvl?userId=<%=userId %>" target="content">Duy???t tr??? tr??ng b??y</a></h5>
		<%} %>
		</li>
		<li>
		<%if(mang[76]==1){ %>
		<h5 class="head" id=""><a
			href="../../MokhaibaotrungbaySvl?userId=<%=userId %>" target="content">Cho ph??p khai b??o tr??ng b??y</a></h5>
		<%} %>
		</li>
		<li>

		<h4 class="head" id=""><a href="" >B??o c??o</a></h4>
		<ul>
			<li>
			<%if(mang[54]==1){ %>
			<h5 class="head" id=""><a
				href="../../Disproforcustomer?userId=<%= userId %>" target="content">B??o c??o tr??ng b??y</a></h5>
			<%} %>
			</li>
		</ul>

		</li>

	</ul>
	</li>

	<li style="position: static;">
	<h3 class="head"><a href="">B??o c??o qu???n tr???</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu5">

			<li>
			<h4 class="head" id=""><a href="" >????nh gi??</a></h4>
			<ul>
				<li>
				<%if(mang[56]==1){ %>
				<h5 class="head" id=""><a href="../../KPITT_Svl?userId=<%=userId %>" target="content">Ch??? s??? th??nh t??ch</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[62]==1){ %>
				<h5 class="head" id=""><a href="../../DistributionTT_Svl?userId=<%=userId %>" target="content">????? ph??? s???n ph???m</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[89]==1){ %>
				<h5 class="head" id=""><a href="../../BCThieuHangNppTT?userId=<%= userId %>&view=TT" target="content">M???c ????? cung ???ng NPP</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[67]==1){ %>
				<h5 class="head" id=""><a href="../../BCTreDonHang?userId=<%= userId %>&view=TT" target="content">H???u c???n NPP</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[67]==1){ %>
				<h5 class="head" id=""><a href="../../BCKhoaHuanLuyenNV?userId=<%= userId %>" target="content">Kh??a hu???n luy???n</a></h5>
				<%} %>
				</li>
			</ul>
			</li>

			<li>
			
			<li>
			<h4 class="head" id=""><a href="">Doanh s???</a></h4>
			<ul>
				<li>
				<%if(mang[64]==1){ %>
				<h5 class="head" id="kbkId"><a href="../../DailyPrimarySales?userId=<%=userId %>" target="content">Mua h??ng </a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[65]==1){ %>
				<h5 class="head" id=""><a href="../../DailySecondarySalesTTSvl?userId=<%=userId %>" target="content">B??n h??ng </a></h5>
				<%} %>
				</li>
				
				<li>
				<%if(mang[66]==1){ %>
				<h5 class="head" id=""><a href="../../BCDonHangBanTrongKy?userId=<%=userId %>&view=TT" target="content">??H b??n trong k??? NPP</a></h5>
				<%} %>
				</li>
				
				<li>
				<%if(mang[67]==1){ %>
				<h5 class="head" id=""><a href="../../BCPerformmanceSvl?userId=<%=userId %>&view=TT" target="content">B??o c??o performmance</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[55]==1){ %>
				<h5 class="head" id=""><a href="../../DailySecondarySalesTT_focusedSKUSvl?userId=<%=userId %>" target="content">SKU t???p trung</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[35]==1){ %>
				<h5 class="head" id=""><a href="../../OutletReportCenter?userId=<%=userId %>;Outletreport.xls" target="content">Doanh s??? c???a hi???u</a></h5>
				<%} %>
				</li>
			</ul>
			</li>

			<li>
			<h4 class="head" id=""><a href="" >B??o c??o kh??c</a></h4>
			<ul>
				<li>
				<%if(mang[68]==1){ %>
				<h5 class="head" id=""><a href="../../Routereport?userId=<%=userId %>" target="content">Tuy???n b??n h??ng</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[72]==1){ %>
				<h5 class="head" id=""><a href="../../RouteSumaryReport?userId=<%=userId %>" target="content">T??m t???t tuy???n ???????ng</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[84]==1){ %>
				<h5 class="head" id=""><a href="../../BCTheoDoiKSN?userId=<%=userId %>" target="content">Theo d??i kh??a s??? ng??y
				</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[85]==1){ %>
				<h5 class="head" id=""><a href="../../BCPhanbodathangSvl?userId=<%=userId %>" target="content">B??o c??o ph??n b??? ?????t h??ng
				</a></h5>
				<%} %>
				</li>
					<li>
				<%if(mang[59]==1){ %>
				<h5 class="head" id=""><a
					href="../../BaoCaoTongHopTTSvl?userId=<%=userId %>"
					target="content">B??o C??o t???ng h???p </a></h5>
				<%} %>
				</li>
			</ul>
			</li>

		</ul>
		</li>
	</ul>
	</li>

	<li style="position: static;">
	<h3 class="head "><a href="">Qu???n tr??? h??? th???ng</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu6">

			<li>
			<%if(mang[69]==1){ %>
			<h5 class="head"><a href="../../DanhmucquyenSvl?userId=<%=userId %>" target="content">C???p nh???t quy???n</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[70]==1 || userId.equals("system")){ %>
			<h5 class="head" id="kbkId"><a href="../../CapnhatnhanvienSvl?userId=<%=userId %>" target="content">C???p nh???t nh??n vi??n</a></h5>
			<%} %>
			</li>
			
			<li>
			<%if(mang[58]==1 || userId.equals("system")){ %>
			<h5 class="head" id="kbkId"><a href="../../MokhoasoSvl?userId=<%=userId %>" target="content">M??? kh??a s???</a></h5>
			<%} %>
			</li>
			<li>
			<%if(mang[86]==1 || userId.equals("system")){ %>
			<h5 class="head" id="kbkId"><a
				href="../../ThongbaoSvl?userId=<%=userId %>" target="content">Th??ng b??o</a></h5>
			<%} %>
			</li>
			<li>
			<%if(mang[86]==1 || userId.equals("system")){ %>
			<h5 class="head" id="kbkId"><a
				href="../../ThongbaoSvl?userId=<%=userId %>&task=1" target="content"><div id="chuadoc"></div></a></h5>
			<%} %>
			</li>
		
		</ul>
	</ul>
	</li>
</ul>
</li>
</ul>
<!-- thuchien tai lai trang -->

</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%}%>

