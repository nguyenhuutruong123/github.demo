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
	setTimeout(goi, 1000);
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

	<h3 class="head"><a href="Home.html" class="head">Dữ liệu nền
	</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu1">
			<li>
			<h4 class="head" id="dlncbId"><a href="Home.jsp">Cơ bản </a></h4>
			<ul style="display: none;">
				<li>
				<%if(mang[1]==1){ %>
				<h5 class="head" id="nccId"><a
					href="../../NhacungcapSvl?userId=<%=userId %>" target="content">Nhà
				Cung Cấp</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[2]==1){ %>
				<h5 class="head" id="dvkdId"><a
					href="../../DonvikinhdoanhSvl?userId=<%=userId %>" target="content">Đơn
				vị Kinh doanh</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[3]==1){ %>
				<h5 class="head" id="kbkId"><a
					href="../../KhoSvl?userId=<%=userId %>" target="content">Khai
				báo Kho Nhà Phân Phối</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[4]==1){ %>
				<h5 class="head" id="ddkdId"><a
					href="../../KhoasotudongSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Thiết lập khóa sổ</a></h5>
				<%} %>
				</li>
	
			</ul>
			</li>
			<li>
			<h4 class="head" id="dlnspId"><a href="Home.html">Sản phẩm</a></h4>
			<ul style="display: none;">
				<li>
				<%if(mang[5]==1){ %>
				<h5 class="head" id="nhId"><a
					href="../../NhanhangSvl?userId=<%=userId %>" target="content">Nhãn
				hàng</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[6]==1){ %>
				<h5 class="head" id="clId"><a
					href="../../ChungloaiSvl?userId=<%=userId %>" target="content">Chủng
				loại</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[7]==1){ %>
				<h5 class="head" id="dvdlspId"><a
					href="../../DonvidoluongSvl?userId=<%=userId %>" target="content">Đơn
				vị đo lường </a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[8]==1){ %>
				<h5 class="head" id="ttspId"><a
					href="../../ThongtinsanphamSvl?userId=<%=userId %>"
					target="content">Thông tin sản phẩm </a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[9]==1){ %>
				<h5 class="head" id="nspId"><a
					href="../../NhomsanphamSvl?userId=<%=userId %>" target="content">Nhóm
				sản phẩm</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[10]==1){ %>
				<h5 class="head" id="bgblcId"><a
					href="../../BanggiabanlechuanSvl?userId=<%=userId %>"
					target="content">Bảng giá bán lẻ chuẩn</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[11]==1){ %>
				<h5 class="head" id="bgmnppId"><a
					href="../../BanggiamuanppSvl?userId=<%=userId %>" target="content">Bảng
				giá bán cho NPP</a></h5>
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
					target="content">Kênh bán hàng</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[13]==1){ %>
				<h5 class="head" id="vmId"><a
					href="../../VungmienSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Vùng miền</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[14]==1){ %>
				<h5 class="head" id="kvId"><a
					href="../../KhuvucSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Khu vực</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[15]==1){ %>
				<h5 class="head" id="gsbhId"><a
					href="../../GiamsatbanhangSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Giám sát bán hàng</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[16]==1){ %>
				<h5 class="head" id="ddkdId"><a
					href="../../DaidienkinhdoanhSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Nhân viên bán hàng</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[17]==1){ %>
				<h5 class="head" id="nppId"><a
					href="../../NhaphanphoiSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Nhà phân phối</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[18]==1){ %>
				<h5 class="head" id="nppId"><a
					href="../../sitecode_convSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Nhà phân phối SAP </a></h5>
				<%} %>
				</li>
				     
				
				      <li>
					      <%if(mang[79]==1){ %>
	            <h5 class="head" ><a
	            href="../../BmSvl?userId=<%=userId %>;userTen=<%= userTen %>" target="content">Trưởng chi nhánh</a></h5>
	  				 <%} %>
     				 </li>
				 <li>
				       <%if(mang[80]==1){ %>
            		<h5 class="head" ><a
         		   href="../../ASMSvl?userId=<%=userId %>;userTen=<%= userTen %>" target="content">Trưởng khu vực</a></h5>
      			  <%} %>
   				   </li>
				<li>
				<%if(mang[19]==1){ %>
				<h5 class="head" id="lchId"><a
					href="../../LoaicuahangSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Loại cửa hàng</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[20]==1){ %>
				<h5 class="head" id="hchId"><a
					href="../../HangcuahangSvl??userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Hạng cửa hàng</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[21]==1){ %>
				<h5 class="head" id="vtchId"><a
					href="../../VitricuahangSvl?userId=<%=userId %>;userTen=<%= userTen %>"
					target="content">Vị trí cửa hàng</a></h5>
				<%} %>
				</li>
				
				<li>
				<%if(mang[22]==1){ %>
					<h5 class="head" id="nkhId"><a href="../../ChietkhauSvl?userId=<%=userId %>;userTen=<%= userTen %>" target="content" >Mức chiết khấu</a></h5>
					<%} %>
				</li>
				
				<li>
				<%if(mang[75]==1){ %>
				<h5 class="head" id="nkhId"><a href="../../TieuChiThuongSvl?userId=<%=userId %>;userTen=<%= userTen %>" target="content">Tiêu chí thưởng </a></h5>
				<%} %>
				</li>
				
				<li>
				<%if(mang[22]==1){ %>
				<h5 class="head" id="nkhId"><a href="../../TieuchithuongSKUSvl?userId=<%=userId %>;userTen=<%= userTen %>" target="content">Tiêu chí thưởng SKU</a></h5>
				<%} %>
				</li>

			</ul>
			</li>
		</ul>
		</li>

	</ul>
	</li>
	<li>
	<h3 class="head "><a href="">Quản lý chỉ tiêu</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu">
			<li>
			<%if(mang[23]==1){ %>
			<h5 class="head" id="ddkdId"><a
				href="../../chitieuttchovungSvl?userId=<%=userId %>&userTen=<%= userTen %>"
				target="content">Chỉ tiêu cho khu vực</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[24]==1){ %>
			<h5 class="head" id="ddkdId"><a
				href="../../ChiTieuSvl?userId=<%=userId %>&userTen=<%= userTen %>"
				target="content">Chỉ tiêu cho NPP</a></h5>
			<%} %>
			</li>
			
			<li>
			<h5 class="head"><a
				href="../../ChiTieuSKUInSvl?userId=<%=userId %>&view=TT" target="content">Chỉ tiêu SKU In</a></h5>
			</li>
			
			<li>
			<%if(mang[25]==1){ %>
			<h5 class="head"><a
				href="../../DailySalesSvl?userId=<%=userId %>" target="content">Thực
			đạt & chỉ tiêu NPP</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[26]==1){ %>
			<h5 class="head"><a
				href="../../Salesreperformance?userId=<%=userId %>" target="content">Thực hiện chỉ tiêu SR</a></h5>
			<%} %>
			</li>
			
			<%if(mang[78]==1){ %>
			<li>
            	<h5 class="head"><a href="../../MokhaibaochitieuSvl?userId=<%=userId %>" target="content">Cho phép khai báo</a></h5>   		
     		 </li>
     		 <%} %>
     		 
     		 <%if(mang[81]==1){ %>
            <li>
                <h5 class="head">
                	<a href="../../SSPerformance?userId=<%=userId %>" target="content">Thực hiện chỉ tiêu SS</a>
                </h5>
            </li>
            <%} %>	
            
             <%if(mang[82]==1){ %>
     		 <li>
                  <h5 class="head">
                  	<a href="../../ASMPerformance?userId=<%=userId %>" target="content">Thực hiện chỉ tiêu ASM</a>  
			 	</h5>
            </li>
			<%}%>
			
			 <%if(mang[83]==1){ %>
            <li>
               <h5 class="head">
               	<a href="../../BMPerformance?userId=<%=userId %>" target="content">Thực hiện chỉ tiêu BM</a>
               </h5>
            </li>
			 <%} %>
		</ul>
		</li>
	</ul>
	</li>
	<li style="position: static;">
	<h3 class="head "><a href="">Quản lý tồn kho</a></h3>
	<ul style="display: block;">

		<li>
		<%if(mang[29]==1){ %>
		<h5 class="head" id="ddnId"><a
			href="../../DuyetdctkSvl?userId=<%=userId %>" target="content">Duyệt
		điều chỉnh tồn kho </a></h5>
		<%} %>
		</li>

		<li>
		<ul id="xtraMenu2">

			<li>
			<%if(mang[27]==1){ %>
			<h5 class="head" id="htNppId"><a
				href="../../Duyetdontrahang?userId=<%=userId %>" target="content">Duyệt
			hàng trả về NCC</a></h5>
			<%} %>
			</li>
			<li>
			<%if(mang[28]==1){ %>
			<li>
			<h4 class="head" id="dlncbId"><a href="Home.jsp">Duyệt hàng
			trả về NPP </a></h4>
			<ul style="display: none;">
				<li>
				<h5 class="head" id="htNppId"><a
					href="../../DuyettradonhangSvl?userId=<%=userId %>"
					target="content">Duyệt trả đơn hàng</a></h5>
				</li>
				<li>
				<h5 class="head" id="htNppId"><a
					href="../../DuyettrasanphamSvl?userId=<%=userId %>"
					target="content">Duyệt trả sản phẩm</a></h5>
				</li>
			</ul>
			</li>
			<%} %>
			</li>


			<li>
			<h4 class="head" id="dlncbId"><a href="#">Báo cáo </a></h4>
			<ul style="display: none;">

				<li>
				<%if(mang[71]==1){ %>
				<h5 class="head" id="kbkId"><a
					href="../../StockAlarm?userId=<%=userId %>" target="content">Cảnh
				báo hàng tồn kho</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[30]==1){ %>
				<h5 class="head"><a
					href="../../IventoryTT_Svl?userId=<%=userId %>" target="content">Tồn
				kho hiện tại </a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[31]==1){ %>
				<h5 class="head"><a
					href="../../DailyStockTTSvl?userId=<%=userId %>" target="content">Tồn
				kho hàng ngày</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[32]==1){ %>
				<h5 class="head" id="kbkId"><a
					href="../../StockInTransit?userId=<%=userId %>" target="content">Hàng
				chưa nhập kho</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[33]==1){ %>
				<h5 class="head"><a
					href="../../Dailypurchase_TTSvl?userId=<%=userId %>"
					target="content"> Hàng nhập kho</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[73]==1){ %>
				<h5 class="head" id="kbkId"><a
					href="../../SecondarySalesPIRTT?userId=<%=userId %>"
					target="content">Báo cáo xuất nhập tồn</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[34]==1){ %>
				<h5 class="head" id="kbkId"><a
					href="../../AdjustInventoryReport?userId=<%=userId %>"
					target="content">Điều chỉnh tồn kho</a></h5>
				<%} %>
				</li>
			</ul>
			</li>

		</ul>
		</li>
	</ul>
	</li>
	<li style="position: static;">
	<h3 class="head "><a href="">Quản lý bán hàng</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu3">
			  <li>
                  <h5 class="head" id="qltkId4">
                  <%if(mang[77]==1){ %>
                  <a href="../../PhanbodathangSvl?userId=<%=userId %>"
                        target="content">Phân bổ đặt hàng </a> 
                        <%} %>
                  </h5>
                  </li>
			<li>
			<h5 class="head" id="qltkId4">
			<%if(mang[36]==1){ %> <a href="../../DonmuahangSvl?userId=<%=userId %>"
				target="content">Duyệt đơn hàng </a> <%} %>
			</h5>
			</li>
			<li>
			<%if(mang[37]==1){ %>
			<h5 class="head"><a
				href="../../TheodoithieuhangSvl?userId=<%=userId %>"
				target="content">Theo dõi thiếu hàng </a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[38]==1){ %>
			<h5 class="head"><a
				href="../../BaocaocungungSvl?userId=<%= userId %>" target="content">Mức
			độ cung ứng </a></h5>
			<%} %>
			</li>
	
		</ul>
		</li>
	</ul>
	</li>

	<li style="position: static;">
	<h3 class="head"><a href="">Quản lý khuyến mãi</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu4">
			<li>
			<%if(mang[39]==1){ %>
			<h5 class="head" id=""><a
				href="../../NhomkhuyenmaiSvl?userId=<%=userId %>" target="content">Nhóm sản phẩm khuyến mãi</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[40]==1){ %>
			<h5 class="head"><a
				href="../../nhomkhachhangkmSvl?userId=<%=userId %>" target="content">Nhóm khách hàng</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[41]==1){ %>
			<h5 class="head" id=""><a
				href="../../DieukienkhuyenmaiSvl?userId=<%=userId %>"
				target="content">Điều kiện khuyến mãi</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[42]==1){ %>
			<h5 class="head" id=""><a
				href="../../TrakhuyenmaiSvl?userId=<%=userId %>" target="content">Trả khuyến mãi</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[43]==1){ %>
			<h5 class="head" id=""><a
				href="../../CtkhuyenmaiSvl?userId=<%=userId %>" target="content">Chương trình khuyến mãi</a></h5>
			<%} %>
			</li>
			
			<li>
			<%if(mang[43]==1){ %>
			<h5 class="head" id=""><a
				href="../../NhomctkhuyenmaiSvl?userId=<%=userId %>" target="content">Nhóm CT khuyến mãi</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[44]==1){ %>
			<h5 class="head" id=""><a
				href="../../PhanbokhuyenmaiSvl?userId=<%=userId %>" target="content">Phân
			bổ khuyến mãi</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[45]==1){ %>
			<h5 class="head"><a href="../../DieuchuyenkhuyenmaiSvl?userId=<%=userId %>" target="content">Điều chuyển ngân sách</a></h5>
			<%} %>
			</li>

			<li>
			<h4 class="head" id=""><a href="home.html" target="content">Báo cáo</a></h4>
			<ul>
				<li>
				<%if(mang[46]==1){ %>
				<h5 class="head" id=""><a href="../../UsingPromotionTTSvl?userId=<%= userId %>" target="content">Sử dụng khuyến mãi</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[47]==1){ %>
				<h5 class="head" id=""><a href="../../PromotionReportTTSvl?userId=<%=userId %>" target="content">Xuất khuyến mãi</a></h5>
				<%} %>
				</li>
				
				<li>
				<%if(mang[47]==1){ %>
				<h5 class="head" id=""><a href="../../ThanhToanKhyenMaiSvl?userId=<%=userId %>" target="content">Thanh toán khuyến mãi</a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[48]==1){ %>
				<h5 class="head" id=""><a href="../../AccumulatedPromotion?userId=<%= userId %>" target="content">Trả khuyến mãi tích lũy </a></h5>
				<%} %>
				</li>

			</ul>

			</li>


		</ul>
		</li>
	</ul>
	</li>

	<li style="position: static;">
	<h3 class="head "><a href="Home.html">Quản lý trưng bày</a></h3>
	<ul style="display: block;">
		<li>
		<%if(mang[49]==1){ %>
		<h5 class="head" id=""><a
			href="../../NhomsptrungbaySvl?userId=<%=userId %>" target="content">Điều kiện trưng bày</a></h5>
		<%} %>
		</li>
		<li>
		<%if(mang[50]==1){ %>
		<h5 class="head" id=""><a
			href="../../TratrungbaySvl?userId=<%=userId %>" target="content">Trả trưng bày</a></h5>
		<%} %>
		</li>
		<li>
		<%if(mang[51]==1){ %>
		<h5 class="head" id=""><a
			href="../../CttrungbaySvl?userId=<%=userId %>" target="content">Chương trình trưng bày</a></h5>
		<%} %>
		</li>
		<li>
		<%if(mang[52]==1){ %>
		<h5 class="head" id=""><a
			href="../../PhanbotrungbaySvl?userId=<%=userId %>" target="content">Phân bổ suất trưng bày</a></h5>
		<%} %>
		</li>
		<li>
		<%if(mang[5]==1){ %>
		<h5 class="head" id=""><a
			href="../../DuyettratrungbaySvl?userId=<%=userId %>" target="content">Duyệt trả trưng bày</a></h5>
		<%} %>
		</li>
		<li>
		<%if(mang[76]==1){ %>
		<h5 class="head" id=""><a
			href="../../MokhaibaotrungbaySvl?userId=<%=userId %>" target="content">Cho phép khai báo</a></h5>
		<%} %>
		</li>
		<li>

		<h4 class="head" id=""><a href="home.html" target="content">Báo cáo</a></h4>
		<ul>
			<li>
			<%if(mang[54]==1){ %>
			<h5 class="head" id=""><a
				href="../../Disproforcustomer?userId=<%= userId %>" target="content">Báo cáo trưng bày</a></h5>
			<%} %>
			</li>
		</ul>

		</li>

	</ul>
	</li>

	<li style="position: static;">
	<h3 class="head"><a href="">Báo cáo quản trị</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu5">

			<li>
			<h4 class="head" id=""><a href="home.html" target="content">Đánh giá</a></h4>
			<ul>
				<li>
				<%if(mang[56]==1){ %>
				<h5 class="head" id=""><a href="../../KPITT_Svl?userId=<%=userId %>" target="content">Chỉ số thành tích</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[57]==1){ %>
				<h5 class="head" id=""><a href="../../DistributionTT_Svl?userId=<%=userId %>" target="content">Độ phủ sản phẩm</a></h5>
				<%} %>
				</li>
			
			</ul>
			</li>

			<li>
			
			<li>
			<h4 class="head" id=""><a href="home.html" target="content">Doanh số</a></h4>
			<ul>
				<li>
				<%if(mang[64]==1){ %>
				<h5 class="head" id="kbkId"><a href="../../DailyPrimarySales?userId=<%=userId %>" target="content">Mua hàng </a></h5>
				<%} %>
				</li>

				<li>
				<%if(mang[65]==1){ %>
				<h5 class="head" id=""><a href="../../DailySecondarySalesTTSvl?userId=<%=userId %>" target="content">Bán hàng </a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[65]==1){ %>
				<h5 class="head" id=""><a href="../../DailySecondarySalesTT_focusedSKUSvl?userId=<%=userId %>" target="content">SKU tập trung</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[66]==1){ %>
				<h5 class="head" id=""><a href="../../OutletReportCenter?userId=<%=userId %>;Outletreport.xls" target="content">Doanh số cửa hiệu</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[57]==1){ %> <h5 class="head" id=""><a href="../../TieuchithuongSKUReportSvl?userId=<%=userId %>"
					target="content">Thưởng SKU tập trung </a></h5>
				<%} %>
				</li>
			</ul>
			</li>

			<li>
			<h4 class="head" id=""><a href="home.html" target="content">Báo cáo khác</a></h4>
			<ul>
				<li>
				<%if(mang[68]==1){ %>
				<h5 class="head" id=""><a href="../../Routereport?userId=<%=userId %>" target="content">Tuyến bán hàng</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[72]==1){ %>
				<h5 class="head" id=""><a href="../../RouteSumaryReport?userId=<%=userId %>" target="content">Tóm tắt tuyến đường</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[72]==1){ %>
				<h5 class="head" id=""><a href="../../BCTheoDoiKSN?userId=<%=userId %>" target="content">Theo dõi khóa sổ ngày
				</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[72]==1){ %>
				<h5 class="head" id=""><a href="../../BCPhanbodathangSvl?userId=<%=userId %>" target="content">Báo cáo phân bổ đặt hàng
				</a></h5>
				<%} %>
				</li>
				<li>
				<%if(mang[57]==1){ %> <h5 class="head" id=""><a href="../../BaoCaoTongHopTTSvl?userId=<%=userId %>"
					target="content">Báo Cáo tổng hợp </a></h5>
				<%} %>
				</li>
				
			</ul>
			</li>

		</ul>
		</li>
	</ul>
	</li>

	<li style="position: static;">
	<h3 class="head "><a href="">Quản trị hệ thống</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu6">

			<li>
			<%if(mang[69]==1){ %>
			<h5 class="head"><a href="../../DanhmucquyenSvl?userId=<%=userId %>" target="content">Cập nhật quyền</a></h5>
			<%} %>
			</li>

			<li>
			<%if(mang[70]==1 || userId.equals("system")){ %>
			<h5 class="head" id="kbkId"><a href="../../CapnhatnhanvienSvl?userId=<%=userId %>" target="content">Cập nhật nhân viên</a></h5>
			<%} %>
			</li>
			
			<li>
			<%if(mang[75]==1 || userId.equals("system")){ %>
			<h5 class="head" id="kbkId"><a href="../../MokhoasoSvl?userId=<%=userId %>" target="content">Mở khóa sổ</a></h5>
			<%} %>
			</li>
			<li>
			<%if(mang[70]==1 || userId.equals("system")){ %>
			<h5 class="head" id="kbkId"><a
				href="../../ThongbaoSvl?userId=<%=userId %>" target="content">Thông báo</a></h5>
			<%} %>
			</li>
			<li>
			<%if(mang[70]==1 || userId.equals("system")){ %>
			<h5 class="head" id="kbkId"><a
				href="../../ThongbaoSvl?userId=<%=userId %>&task=1" target="content">Thông báo mới:<div id="chuadoc"></div></a></h5>
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

