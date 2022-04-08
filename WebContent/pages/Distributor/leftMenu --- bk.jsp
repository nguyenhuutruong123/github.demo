<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.util.*"%>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  
	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	//boolean hople = util.checkHopLe(userId);
	boolean hople = true;
	
	 String chuoi=(String)session.getValue("chuoi");
	 if(chuoi.equals("")){
		 chuoi="../css/style1024.css";
	 }
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<html>
<head>
<style type="text/css">
body {
	font-family: verdana, arial, sans-serif;
	font-size: 10pt;
	margin: 1px;
	background-color: #ffffff;
}
</style>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Best - Project</title>
<LINK rel="stylesheet" href="<%=chuoi%>" type="text/css">
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
	font-size: 1.0em;
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
	width: 215px;
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
	width: 215px;
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
	width: 175px;
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

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
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
	jQuery('#xtraMenu7').Accordion({
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
	
	jQuery('#DCKM').Accordion({
		active: 'h6.selected',
		header: 'h6.head',
		alwaysOpen: false,
		animated: true,
		showSpeed: 400,
		hideSpeed: 800
	});

});	
</script>
<body>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<ul id="theMenu">
	<li style="position: static;">
	<h3 class="head"><a href="#">Thiết lập dữ liệu nền </a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu1">
			<li>
			<h4 class="head"><a href="Home.html">Dữ liệu nền sản phẩm</a></h4>
			<ul style="display: none;">
				<li>
				<h5 class="head"><a
					href="../../DThongtinsanphamSvl?userId=<%=userId %>"
					target="content">Thông tin sản phẩm </a></h5>
				</li>

				<li>
				<h5 class="head"><a
					href="../../BanggiasieuthiSvl?userId=<%=userId %>" target="content">Bảng
				giá siêu thị</a></h5>
				</li>

				<li>
				<h5 class="head"><a
					href="../../BanggiablSvl?userId=<%=userId %>" target="content">Bảng
				giá bán lẻ chuẩn npp</a></h5>
				</li>
			</ul>
			</li>

			<li>
			<h4 class="head" id="dlkdId"><a href="Home.html">Dữ liệu kinh doanh</a></h4>
			<ul style="display: none;">
				<li>
				<h5 class="head"><a
					href="../../MucchietkhauSvl?userId=<%=userId %>" target="content">Mức
				chiết khấu </a></h5>
				</li>
				
				<li>
				<h5 class="head"><a
					href="../../KhachhangSvl?userId=<%=userId %>" target="content">Khách
				hàng </a></h5>
				</li>
				<li>
				<h5 class="head"><a
					href="../../KhachhangChuaPhanTuyenSvl?userId=<%=userId %>" target="content">Khách
				hàng chưa phân tuyến </a></h5>
				</li>
				<%if(userId.equals("100057") || userId.equals("100198") || userId.equals("100058")){ %>
					
				<%}else{ %>
					<li>
					<h5 class="head"><a
						href="../../TuyenbanhangSvl?userId=<%=userId %>" target="content">Tuyến
						bán hàng</a></h5>
					</li>
				<%} %>
				<li>
				<h5 class="head"><a
					href="../../NhanviengiaonhanSvl?userId=<%=userId %>"
					target="content">Nhân viên giao nhận</a></h5>
				</li>
			</ul>
			</li>
		</ul>
		</li>
	</ul>
	</li>
	<li style="position: static;">
	<h3 class="head "><a href="Home.html">Quản lý chỉ tiêu</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu7">
			<li>
			<h5 class="head"><a
				href="../../ChiTieuNppSvl?userId=<%=userId %>" target="content">Chỉ tiêu Secondary</a></h5>
			</li>
			<li>
			<h5 class="head"><a
				href="../../ChiTieuNppPriSvl?userId=<%=userId %>" target="content">Chỉ tiêu Primary</a></h5>
			</li>
			<li>
			<h5 class="head"><a
				href="../../ChiTieuSKUInSvl?userId=<%=userId %>" target="content">Chỉ tiêu SKU In</a></h5>
			</li>

			<li>
			<h4 class="head"><a href=""> Báo cáo</a></h4>
			<ul style="display: block;">

				<li>

				<h5 class="head"><a
					href="../../Srperfomance?userId=<%=userId %>" target="content">Thực
				hiện chỉ tiêu của ĐDKD</a></h5>

				</li>
				<li>
				<h5 class="head"><a
					href="../../DailySalesDistributorSvl?userId=<%=userId %>"
					target="content">Thực hiện chỉ tiêu của NPP</a></h5>
				</li>
				
			</ul>
			</li>
		</ul>
		</li>
	</ul>
	</li>
	<li style="position: static;">
	<h3 class="head"><a href="Home.html">Quản lý tồn kho </a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu2">
			<li>
			<h4 class="head"><a href="Home.html">Mua hàng từ nhà cung cấp </a></h4>
			<ul style="display: none;">

				<li>
				<h5 class="head"><a
					href="../../DenghidathangSvl?userId=<%=userId %>" target="content">Đề
				nghị đặt hàng</a></h5>
				</li>
				<li>
				<h5 class="head"><a
					href="../../DondathangSvl?userId=<%=userId %>" target="content">Đặt hàng</a></h5>
				</li>

				<li>
				<h5 class="head"><a href="../../NhaphangSvl?userId=<%=userId %>" target="content">Nhận hàng</a></h5>
				</li>

			</ul>
			</li>
			<li>
			<h5 class="head"><a
				href="../../DontrahangSvl?userId=<%=userId %>" target="content">Trả
			hàng về nhà cung cấp</a></h5>
			</li>
			<li>
			<h5 class="head"><a
				href="../../DieuchinhtonkhoSvl?userId=<%=userId %>" target="content">Điều
			chỉnh tồn kho</a></h5>
			</li>
			<li>
			<h5 class="head"><a
				href="../../MuaHangNhaPPKhacSvl?userId=<%=userId %>"
				target="content">Mua hàng từ NPP khác</a></h5>
			</li>


			<li>
			<h4 class="head" id="bcqlkId"><a href="Home.html">Báo cáo</a></h4>
			<ul style="display: none;">

				<li>
				<h5 class="head"><a
					href="../../StockAlarmDistributor?userId=<%=userId %>"
					target="content"> Cảnh báo hàng tồn kho</a></h5>
				</li>

				<li>
				<h5 class="head"><a
					href="../../SecondarySalesPIR?userId=<%=userId %>"
					target="content">Xuất-Nhập-Tồn</a></h5>
				</li>
				<li>

				<h5 class="head"><a
					href="../../Iventorynpp?userId=<%=userId %>" target="content">Tồn
				hiện tại</a></h5>
				</li>
				<li>
				<h5 class="head"><a
					href="../../DailyStocknpp?userId=<%=userId %>" target="content">Tồn
				kho theo ngày</a></h5>
				</li>
				<li>
				<h5 class="head"><a
					href="../../Dailypurchasenpp?userId=<%=userId %>" target="content">Hàng
				nhập kho</a></h5>
				</li>
				<li>
				<h5 class="head"><a
					href="../../AdjustInventoryReportnpp?userId=<%=userId %>"
					target="content"> Điều chỉnh tồn kho</a></h5>
				</li>

			</ul>
			</li>

		</ul>
		</li>
	</ul>
	</li>
	
	<li style="position: static;">
	<h3 class="head "><a href="Home.html">Quản lý bán hàng</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu3">
			<li>
			<h5 class="head"><a href="../../DonhangSvl?userId=<%=userId%>"
				target="content">Bán hàng cho khách hàng</a></h5>
			</li>
			<li>
			<h5 class="head"><a
				href="../../PhieuxuatkhoSvl?userId=<%=userId %>" target="content">Phiếu xuất kho</a></h5>
			</li>
			<li>
			<h5 class="head"><a
				href="../../PhieuthuhoiSvl?userId=<%=userId %>" target="content">Phiếu
			thu hồi</a></h5>
			</li>
			<li>
			<h5 class="head"><a
				href="../../DonhangtraveSvl?userId=<%=userId %>" target="content">Đơn
			hàng trả về</a></h5>
			</li>
			<li>
			<h5 class="head"><a
				href="../../ChotdonhangSvl?userId=<%=userId %>" target="content">Chốt
			đơn hàng</a></h5>
			</li>
			
			<li>
			<h5 class="head"><a
				href="../../DonhangnppSvl?userId=<%=userId %>" target="content">Bán
			hàng cho NPP</a></h5>
			</li>

			<li>
			<h5 class="head"><a
				href="../../KhoasongaySvl?userId=<%=userId %>" target="content">Khóa
			sổ ngày</a></h5>
			</li>
			<li>
			<h4 class="head"><a href=""> Báo cáo</a></h4>
			<ul style="display: null">

				<li>
				<h5 class="head"><a
					href="../../BCDonHangBanTrongKy?userId=<%=userId %>"
					target="content"> BC DH bán trong kỳ</a></h5>
				</li>

				<li>

				<h5 class="head"><a
					href="../../BCDonHangHuyTrongKy?userId=<%=userId %>"
					target="content"> BC DH hủy trong kỳ</a></h5>
				</li>

			</ul>
			</li>
		</ul>
		</li>
	</ul>
	</li>
	
	<li style="position: static;">
	<h3 class="head "><a href="Home.html">Quản lý khyến mãi</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu5">
			<li>
			<h5 class="head"><a
				href="../../ChuongtrinhkhuyenmaiSvl?userId=<%=userId %>"
				target="content">Chương trình khuyến mãi</a></h5>
			</li>
			<li>
				<h5 class="head" ><a href="../../TrakhuyenmaiNppSvl?userId=<%=userId %>" target="content" >Sản phẩm trả khuyến mãi</a></h5>
			</li> 
			<li>
			<h5 class="head" id="Home.html"><a
				href="../../CtkmkhachhangSvl?userId=<%=userId %>" target="content">Khách
			hàng hưởng khuyến mãi</a></h5>
			</li>
			<li>
			<h5 class="head"><a
				href="../../DangkykhuyenmaitichluySvl?userId=<%=userId %>"
				target="content">Đăng ký khuyến mãi tích lũy</a></h5>
			</li>

			<li>
			<h4 class="head"><a href="Home.html"> Báo cáo</a></h4>
			<ul style="display: block;">
				<li>
				<h5 class="head"><a
					href="../../UsingPromotionnpp?userId=<%=userId %>" target="content">Sử
				dụng phân bổ khuyến mãi</a></h5>
				</li>
				<li>
				<h5 class="head" id=""><a
					href="../../Ppromotionreportnpp?userId=<%=userId %>"
					target="content">Xuất khuyến mãi</a></h5>
				</li>
				<li>
                 <h5 class="head"><a
                             href="../../UsingPromotionnpp?userId=<%=userId %>" target="content">Sử
                 dụng phân bổ</a></h5>
                 </li>

                 <li>

                 <li>
                 <h5 class="head" id=""><a
                             href="../../DailyPromotionReportDist?userId=<%=userId %>"
                             target="content">Xuất khuyến mãi ngày</a></h5>
                 </li>

                 <li>
                 <h5 class="head" id=""><a
                             href="../../Ppromotionreportnpp?userId=<%=userId %>"
                             target="content">Xuất khuyến mãi tháng</a></h5>
                 </li>
				
				
			</ul>
			</li>
		</ul>
		</li>
	</ul>
	</li>

	<li style="position: static;">
	<h3 class="head"><a href="Home.html">Quản lý trưng bày</a></h3>
	<ul style="display: block;">
		<li>
			<h5 class="head" id=""><a
				href="../../DangkytrungbaySvl?userId=<%=userId %>" target="content">Đăng
			ký trưng bày</a></h5>
		</li>
		<li>
			<h5 class="head" id=""><a
				href="../../DenghitratbSvl?userId=<%=userId %>" target="content">Đề
			nghị trưng bày</a></h5>
		</li>
		<li>
		<h5 class="head" id="">
				<a
				href="../../Disproforcustomernpp?userId=<%=userId %>"
				target="content"> Báo cáo trưng bày</a></h5>
		</li>
	</ul>
	</li>
<%if(userId.equals("100046")) {
	%>
	<li style="position: static;">
	<h3 class="head "><a href="Home.html">Quản lý công nợ</a></h3>
	<ul style="display: block;">
		<li>
		<ul id="xtraMenu6">
			<li>
				<h5 class="head" ><a href="../../PhieuthutienSvl?userId=<%=userId %>" 
				target="content" >Phiếu thu tiền</a></h5>
			</li> 
			<li>
			<h5 class="head"><a
				href="../../PhieuthanhtoanSvl?userId=<%=userId %>"
				target="content"> Phiếu thanh toán </a></h5>
			</li>
			<li>
			<h4 class="head"><a href="Home.html"> Báo cáo</a></h4>
			<ul style="display: block;">
				<li>
				<h5 class="head" id=""><a
					href="../../BCCongNoTheoHDSvl?userId=<%=userId %>"
					target="content">Công nợ theo hóa đơn</a></h5>
				</li>
				<li>
				<h5 class="head" id=""><a
					href="../../BKTienThuTrongNgaySvl?userId=<%=userId %>"
					target="content">Bảng kê tiền thu </a></h5>
				</li>

				<li>
				<h5 class="head" id=""><a
					href="../../TomTatCongNoTrongKySvl?userId=<%=userId %>"
					target="content">Công nợ tóm tắt</a></h5>
				</li>
				<li>
				<h5 class="head" id=""><a
					href="../../BCCongNoChiTietSvl?userId=<%=userId %>"
					target="content">Công nợ chi tiết</a></h5>
				</li>
				
				
			</ul>
			</li>
		</ul>
		</li>
	</ul>
	</li>

<%	
}
%>
		<li style="position: static;">
	<h3 class="head"><a href="Home.html">Báo cáo quản trị</a></h3>
	<ul style="display: block;">
		<li>
			<ul id="xtraMenu4">
				<li>
					<h4 class="head"><a href="Home.html">Theo dõi thành tích</a></h4>			
					<ul style="display: block;">
					<li>
						<li>
								<h5 class="head"><a href="../../KPInpp?userId=<%=userId %>"	target="content">Yếu tố nền tảng của ĐDKD</a></h5>
						</li>
					</li>
					</ul>
				</li>
				<li>
					<h4 class="head"><a href="Home.html">Theo dõi Doanh số</a>	</h4>
					<ul style="display: block;">
						<li>
							<h5 class="head"><a
								href="../../Dailysalesnpp?userId=<%=userId %>" target="content">Doanh số bán hàng</a>
							</h5>
						</li>
						<li>
							<h5 class="head"><a
								href="../../DailySalesnpp_focusedSKU?userId=<%=userId %>" target="content">SKU tập trung</a>
							</h5>
						</li>
						<li>
							<h5 class="head">
							<a href="../../Distributionnpp?userId=<%=userId %>" target="content">&nbsp Độ phủ của sản phẩm</a>
							</h5>
							</li>
					 		<li>
							<h5 class="head"><a href="../../OutletReportDistributorSvl?userId=<%=userId %>"	target="content"> Doanh số theo cửa hiệu</a></h5>
							</li>
						
					</ul>
				</li>			
				<li>
					<h4 class="head"><a href="Home.html">Báo cáo khác</a></h4>
					<ul style="display: block;">
						<li>
							<h5 class="head"><a
								href="../../RoutereportSvl?userId=<%=userId %>" target="content">
							Tuyến đường của ĐDKD</a></h5>
						</li>
						<li>
							<h5 class="head"><a
								href="../../RouteSumaryReportDist?userId=<%=userId %>"
								target="content">Tóm tắt tuyến bán hàng</a></h5>
						</li>
					</ul>
				</li>	
						
		</ul>
		</li>		
			</ul>	

</body>
</html>
<%}%>

