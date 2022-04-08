<%@page import="java.sql.SQLException"%>
<%@page
	import="geso.dms.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluy"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.dms.distributor.beans.dangkytrungbay.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="geso.dms.center.util.*"%>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<% IDangkykhuyenmaitichluy obj = (IDangkykhuyenmaitichluy)session.getAttribute("dkkmtlBean"); %>
<% ResultSet ctkmIds = (ResultSet)obj.getctkmIds();
	ResultSet sanphamRs = obj.getSanphamRs();
	
	ResultSet khachangRs = obj.getKhDangKyRs();
	
	ResultSet nhomSpRs = obj.getNhomsanphamRs();
	ResultSet vungRs = obj.getVungRs();
	ResultSet kvRs = obj.getKhuvucRs();
	ResultSet kenhRs = obj.getKenhRs();
	ResultSet nppRs = obj.getNppRs();
	ResultSet khoRs = obj.getKhoRs();
	String[] Mucphanbo = (String[])obj.gethttt1();
	String[] diengiaiMuc = (String[])obj.getDiengiaiMuc();
	String[] tumuc = (String[])obj.getTumuc();
	String[] denmuc = (String[])obj.getDenmuc();
	String[] thuongSR = (String[])obj.getThuongSR();
	String[] thuongTDSR = (String[])obj.getThuongTDSR();
	
	
	String[] diengiaiMuc3 = (String[])obj.getDiengiaiMuc3();
	String[] thuongSR3 = (String[])obj.getThuongSR3();
	String[] thuongTDSR3 = (String[])obj.getThuongTDSR3();
	String[] thuongSS3 = (String[])obj.getThuongSS3();
	String[] thuongTDSS3 = (String[])obj.getThuongTDSS3();
	String[] thuongASM3 = (String[])obj.getThuongASM3();
	String[] thuongTDASM3 = (String[])obj.getThuongTDASM3();
	
	String[] maspTra = (String[])obj.getMaspTra();
	String[] tenspTra = (String[])obj.getTenspTra();
	String[] soluongspTra = (String[])obj.getSoluongspTra();
	String[] idspTra = (String[])obj.getIdspTra();
	String[] dongiaspTra = (String[])obj.getDongiaspTra();
	
	Hashtable<String, String> muc_tiendo = obj.getMuc_Tiendo();
	Hashtable<String, String> muc_spTRA = obj.getMuc_SpTra();
	Hashtable<String, String> phanbo = obj.getPhanbo();
	
	Hashtable<String, String> dieukien = obj.getDieukien();
	Hashtable<String, String> quydoi = obj.getQuydoi();
	Hashtable<String, String> phanbo1 = obj.getPhanboTheoMucNPP1();
	Hashtable<String, String> phanbo2 = obj.getPhanboTheoMucNPP2();
	Hashtable<String, String> phanbo3 = obj.getPhanboTheoMucNPP3();
	Hashtable<String, String> phanbo4 = obj.getPhanboTheoMucNPP4();
	Hashtable<String, String> phanbo5 = obj.getPhanboTheoMucNPP5();
	
	
	
%>
<% Hashtable<String, String> khachhang_dangky = (Hashtable<String, String>)obj.getKhachHang_DangKy(); %>
<% Hashtable<String, String> khachhang_duyet = (Hashtable<String, String>)obj.getKhachHang_Duyet(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>DDT - Project</TITLE>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
 <script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>

<!-- <LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css"> -->
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
 <script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>

<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>


<link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>
<script>
        $(document).ready(function()
        {
        	<% for(int i = 0; i < 7; i++)
        	{ %>
        	
        		$(".muc" + <%= i %> ).colorbox({width:"500px", inline:true, href:"#muc" + <%= i %>});
                //Example of preserving a JavaScript event for inline calls.
                $("#click").click(function(){ 
                    $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                    return false;
                });
                
                $(".sanphamTRA" + <%= i %> ).colorbox({width:"600px", inline:true, href:"#sanphamTRA" + <%= i %> });
                //Example of preserving a JavaScript event for inline calls.
                $("#click").click(function(){ 
                    $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
                    return false;
                });
        	<% } %>
        });
    </script>

<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">
#mainContainer {
	width: 600px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100000000000;
}

#ajax_listOfOptions div {
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5000000000000;
}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
</style>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/dkkhuyenmai_sanpham.js"></script>
<script type="text/javascript"
	src="../scripts/KhachHang_DangKyTichLuy.js"></script>

<script type="text/javascript">
		$(document).ready(function() {
			$(".days").datepicker({
				changeMonth : true,
				changeYear : true
			});
			
			//$("ul.tabs").tabs("div.panes > div");
		});
	</script>

<script>
	$(function() {
	 	$("ul.tabs").tabs("div.panes > div");
	});
	</script>
<script>
	$(document).ready(function() {

	    //When page loads...
	    $(".tab_content").hide(); //Hide all content
	    var index = $("ul.tabs li.current").show().index(); 
	    $(".tab_content").eq(index).show();
	    //On Click Event
	    $("ul.tabs li").click(function() {
	  
	        $("ul.tabs li").removeClass("current"); //Remove any "active" class
	        $(this).addClass("current"); //Add "active" class to selected tab
	        $(".tab_content").hide(); //Hide all tab content  
	        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
	        $(activeTab).show(); //Fade in the active ID content
	        return false;
	    });

	});
	</script>

<SCRIPT language="JavaScript" type="text/javascript">
	function loadSPTheoNhom()
	{
		var active =$(".tabs li.current").index();
		document.forms["tctsku"].activeTab.value = active;
		document.forms["tctsku"].action.value = "loadSP_NHOM";
		document.forms["tctsku"].submit(); 
	}
		
		function replaces()
		{
			var masp = document.getElementsByName("maspTra");
			var tensp = document.getElementsByName("tenspTra");
	
			var i;
			for(i=0; i < masp.length; i++)
			{
				if(masp.item(i).value != "")
				{
					var sp = masp.item(i).value;
					var pos = parseInt(sp.indexOf(" - "));
					if(pos > 0)
					{
						masp.item(i).value = sp.substring(0, pos);
						tensp.item(i).value = sp.substr(parseInt(sp.indexOf(" - ")) + 3);					
					}
				}
				else
					tensp.item(i).value = "";
			}
			
			for(var k = 0; k < 7; k++)
			{
				var masp = document.getElementsByName('sanphamTRA' + k + '.masanpham');
				var tensp = document.getElementsByName('sanphamTRA' + k + '.tensanpham');
				
				for(var p=0; p < masp.length; p++)
				{
					if(masp.item(p).value != "")
					{
						var sp = masp.item(p).value;

						var pos = parseInt(sp.indexOf(" - "));
						if(pos > 0)
						{
							masp.item(p).value = sp.substring(0, pos);
							tensp.item(p).value = sp.substr(parseInt(sp.indexOf(" - ")) + 3);					
						}
					}
					else
					{
						tensp.item(p).value = "";
					}			
				}
			}

			setTimeout(replaces, 200);
		}	
		
		function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
		{    
			var keypressed = null;
			if (window.event)
				keypressed = window.event.keyCode; //IE
			else
				keypressed = e.which; //NON-IE, Standard
			
			if (keypressed < 48 || keypressed > 57)
			{ 
				if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
				{//Phím Delete và Phím Back
					return;
				}
				return false;
			}
		}
		
	function save()
	{
	  /* var active =$(".tabs li.current").index(); */
		/* document.forms["tctsku"].activeTab.value = active; */
	  document.forms["tctsku"].action.value = "save";
	  document.forms["tctsku"].submit(); 
    }
	
	function submitform()
	{
		/* var active =$(".tabs li.current").index(); */
		/* document.forms["tctsku"].activeTab.value = active; */
		document.forms["tctsku"].action.value = "submit";
		document.forms["tctsku"].submit(); 
	}
		
	function FormatNumber(e)
	{
		e.value = DinhDangTien(e.value.replace(/,/g,""));
	}
	
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
	
	function SelectALL()
	{
		var checkAll = document.getElementById("checkAll");
		var spIds = document.getElementsByName("spIds");
		
		if(checkAll.checked == true)
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = false;
		}
		
	}
	
	function SelectALL2()
	{
		var checkAll = document.getElementById("checkAll2");
		var spIds = document.getElementsByName("nppIds");
		
		if(checkAll.checked == true)
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = false;
		}
		
	}
	
	function DongLai()
	{
		$("#cboxClose").click();
	}

</SCRIPT>
<style type="text/css">
	/* Define the hover highlight color for the table row */
    .hoverTable tr:hover {
          background-color: #50ff99;
    }
</style>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="tctsku" method="post"
		action="../../DangkykhuyenmaitichluyUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%= userId %>'> <input
			type="hidden" name="phanloai" value='<%= obj.getPhanloai() %>'>
		<input type="hidden" id="activeTab" name="activeTab"
			value='<%=obj.getActiveTab()%>'> <input type="hidden"
			name="action" value="0"> 
			<input type="hidden" name="nppId" value="<%=obj.getNppId()%>">
			<input type="hidden" name="id" value="<%=obj.getId()%>">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<% if(obj.getPhanloai().equals("0")) { %>
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản
											lý khuyến mại > Khuyến mại tích lũy > Cập nhật</TD>
										<% } else { %>
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản
											lý khuyến mại > Khuyến mại tích điểm > Cập nhật</TD>
										<% } %>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%= userTen %></TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="../../DangkykhuyenmaitichluySvlSvl?userId=<%=userId%>&phanloai=<%= obj.getPhanloai() %>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript: save()"><IMG
													src="../images/Save30.png" title="Luu lai" alt="Luu lai"
													border="1" style="border-style: outset"></A>
											</div>
										</TD>
										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>

									<textarea name="dataerror"
										style="width: 100%; color: #F00; font-weight: bold"
										style="width: 100% ; color:#F00 ; font-weight:bold"
										readonly="readonly" rows="1"><%= obj.getMessage() %></textarea>
								</FIELDSET>
							</TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông
										tin khuyến mại</LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">



										<TR>
											<TD class="plainlabel" width="23%">Chương trình</TD>
											<TD class="plainlabel" colspan="3"><select name="ctkmId"
												id="ctkmId" onchange="submitform()">
													<option value='All'></option>
													<% if(ctkmIds != null){
													try{ while(ctkmIds.next()){ 
													if(ctkmIds.getString("pk_seq").equals(obj.getctkmId())){ %>
													<option value='<%=ctkmIds.getString("pk_seq")%>' selected><%=ctkmIds.getString("scheme") %></option>
													<%}else{ %>
													<option value='<%=ctkmIds.getString("pk_seq")%>'><%=ctkmIds.getString("scheme") %></option>
													<%}} ctkmIds.close(); }catch(java.sql.SQLException e){} }%>
											</select></TD>
										</TR>


										<TR>
											<TD width="120px" class="plainlabel">Ngày đăng ký <br />&nbsp;&nbsp;&nbsp;&nbsp;
												Từ ngày<FONT class="erroralert"> *</FONT></TD>
											<TD width="250px" class="plainlabel"><input type="text"
												name="thang" id="thang" class="days"
												value="<%= obj.getThang() %>" readonly="readonly">
											</TD>
											<TD width="120px" class="plainlabel">Đến ngày<FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><input type="text" name="nam"
												id="nam" class="days" value="<%= obj.getNam() %>"
												readonly="readonly"></TD>
										</TR>
										<TR>
											<TD width="120px" class="plainlabel">Ngày tính DS <br />&nbsp;&nbsp;&nbsp;&nbsp;
												Từ ngày<FONT class="erroralert"> *</FONT></TD>
											<TD width="250px" class="plainlabel"><input type="text"
												name="tungay" class="days"
												value="<%= obj.getNgayDS_Tungay() %>" readonly="readonly">
											</TD>
											<TD width="120px" class="plainlabel">Đến ngày<FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><input type="text"
												name="denngay" class="days"
												value="<%= obj.getNgayDS_Denngay() %>" readonly="readonly">
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel">Scheme <FONT class="erroralert">
													*</FONT></TD>
											<TD class="plainlabel"><input type="text" name="scheme"
												id="scheme" value="<%= obj.getScheme() %>"></TD>
											<TD class="plainlabel">Hình thức phân bổ</TD>
											<TD class="plainlabel"><select name="httt">
													<% if(obj.getHTTT().equals("0")) { %>
													<option value="0" selected="selected">Theo từng
														mức</option>
													<% } else { %>
													<option value="0">Theo từng mức</option>
													<% } %>
													<% if(obj.getHTTT().equals("1")) { %>
													<option value="1" selected="selected">Theo tổng
														số xuất</option>
													<% } else { %>
													<option value="1">Theo tổng số xuất</option>
													<% } %>
											</select></TD>
										</TR>
										<TR>
											<TD class="plainlabel">Diễn giải</TD>
											<TD class="plainlabel" colspan=3><input type="text"
												name="diengiai" id="diengiai"
												value="<%= obj.getDiengiai() %>"></TD>
											<%-- <TD class="plainlabel">Phần trăm trả CK</TD>
											<TD class="plainlabel"><input type="text"
												name="ptTRACK" id="ptTRACK" value="<%= obj.getPT_TRACK() %>">

											</TD> --%>
										</TR>

										<tr>
											<td class="plainlabel">Kho áp dụng</td>
											<TD class="plainlabel" colspan="1"><select name="khoId"
												style="width: 200px;">
													<option value=""></option>
													<%
											if(khoRs != null)
											{
												while(khoRs.next())
												{
													if(obj.getKhoIds().indexOf(khoRs.getString("pk_seq")) >= 0 )
													{
														%>
													<option value="<%= khoRs.getString("pk_seq") %>"
														selected="selected"><%= khoRs.getString("ten") %></option>
													<% } else { %>
													<option value="<%= khoRs.getString("pk_seq") %>"><%= khoRs.getString("ten") %></option>
													<%}
												}
												khoRs.close();
											}
										%>
											</select></td>

											<TD class="plainlabel">Ghi chú</TD>
											<TD class="plainlabel" colspan="2"><input type="text"
												name="ghichu" id="ghichu" value="<%= obj.getGhichu() %>">
											</TD>
										</tr>

									</TABLE>


									<ul class="tabs">
										<% if(obj.getPhanloai().equals("0")) { %>
										<li
											<%=obj.getActiveTab().equals("0") ? "class='current'" : "" %>><a
											href="#tab1">Tiêu chí tích lũy</a></li>
										<% } else { %>
										<li
											<%=obj.getActiveTab().equals("0") ? "class='current'" : "" %>><a
											href="#tab1">Tiêu chí tích điểm</a></li>
										<% } %>

										<% if(obj.getPhanloai().equals("0")) { %>
										<li
											<%=obj.getActiveTab().equals("1") ? "class='current'" : "" %>><a
											href="#tab2">Sản phẩm mua</a></li>
										<% } else { %>
										<li
											<%=obj.getActiveTab().equals("1") ? "class='current'" : "" %>><a
											href="#tab2">Công thức tích điểm</a></li>
										<% } %>

										<%-- 	<li <%=obj.getActiveTab().equals("2") ? "class='current'" : "" %>><a href="#tab3">Phân bổ</a></li> --%>
										<li
											<%=obj.getActiveTab().equals("3") ? "class='current'" : "" %>><a
											href="#tab4">KH đăng ký</a></li>
									</ul>

									<div class="panes">

										<div id="tab1" class="tab_content">

											<TABLE class="tabledetail" width="100%" border="0"
												cellspacing="1px" cellpadding="0px">

												<TR class="tbheader">
													<TH align="center" width="25%" rowspan="2">Mức tích
														lũy</TH>
													<TH align="center" width="5%" rowspan="2">Hình thức
														phân bổ</TH>
													<% if(obj.getPhanloai().equals("0")) { %>
													<TH align="center" width="10%" rowspan="2">Tiến độ</TH>
													<% } %>

													<TH align="center" width="20%" colspan="2">Doanh số
														phát sinh</TH>
													<td align="center" width="15%" rowspan="2">Trả tích
														lũy</td>
													<td align="center" width="15%" rowspan="2">Đơn vị</td>
												</TR>

												<TR class="tbheader">
													<TH align="center" width="15%">Từ mức</TH>
													<TH align="center" width="15%">Đến mức</TH>
												</TR>

												<%
				                    	int count = 0;
				                        String []mucpb = obj.gethttt1();
				                        int length = (mucpb==null?0: mucpb.length);
				                    	if( diengiaiMuc != null ) 
				                    	{
				                    		for(int i = 0; i < diengiaiMuc.length; i++)
				                    		{
				                    			String infoDETAIL = "";
				                    			if( muc_tiendo.get( Integer.toString(i) ) != null )
				                    				infoDETAIL = muc_tiendo.get( Integer.toString(i) );
				                    			//System.out.println("INFO DETAIL::: " + infoDETAIL);
				                    			
				                    			String infoDETAIL_SPTRA = "";
				                    			String infoDETAIL_SPTRA_HT = "";
				                    			if( muc_spTRA.get( Integer.toString(i) ) != null )
				                    			{
				                    				infoDETAIL_SPTRA = muc_spTRA.get( Integer.toString(i) );
				                    				infoDETAIL_SPTRA_HT = infoDETAIL_SPTRA.split("__")[0];
				                    			}
				                    			
												%>

												<tr>
													<td><input type="text" name="diengiaiMuc"
														value="<%= diengiaiMuc[i] %>" readonly="readonly">
													</td>
													<TD><select name="httt1">
															<% 
																if(i < length)
												  	  			{
														  	  			if(Mucphanbo[i].equals("0")) 
														  	  			{ %>
															<option value="0" selected="selected">Theo từng
																mức</option>
															<% } 
														  	  		else { %>
															<option value="0">Theo từng mức</option>
															<% } 
														  	  	} 
												  	  			else { %>
															<option value="0">Theo từng mức</option>
															<% } %>
															<%if(i < length)
												  	  				{
														  	  			if(Mucphanbo[i].equals("1")) 
														  	  			{ %>
															<option value="1" selected="selected">Theo tổng
																số xuất</option>
															<% }
														  	  		else 
													  	  			{
													  	  				
													  	  				%>
															<option value="1">Theo tổng số xuất</option>
															<% } 
												  	  				}
												  	  			else 
												  	  			{
												  	  				
												  	  				%>
															<option value="1">Theo tổng số xuất</option>
															<% } %>
													</select></TD>
													<% if(obj.getPhanloai().equals("0")) { %>
													<td align="center"><a class="muc<%= i %>" href="#">
															<img style="top: -4px;" src="../images/vitriluu.png"
															title="Tiến độ cần thực hiện">
													</a>
														<div style='display: none'>
															<div id='muc<%= i %>'
																style='padding: 0px 5px; background: #fff;'>
																<h4 align="left">Tiến độ cần phải đạt</h4>
																<table cellpadding="4px" cellspacing="2px" width="100%"
																	align="center">
																	<tr>
																		<td colspan="2">
																			<table align="left" cellpadding="0" cellspacing="1">
																				<tr class="tbheader">
																					<td width="150px" align="center"><span
																						style="font-size: 0.9em">Diễn giải</span></td>
																					<td width="100px" align="center"><span
																						style="font-size: 0.9em">Từ ngày</span></td>
																					<td width="100px" align="center"><span
																						style="font-size: 0.9em">Đến ngày</span></td>
																					<td width="100px" align="center"><span
																						style="font-size: 0.9em">Phải đạt ( % )</span></td>
																				</tr>
																				<%
										                                			int count2 = 0;
										                                			if(infoDETAIL.trim().length() > 0)
										                                			{ 
										                                				String[] info = infoDETAIL.split("__");
										                                				for(int k = 0; k < info.length; k++ )
										                                				{
										                                					//System.out.println("INFO ::: " + info[k]);
										                                				%>

																				<tr>
																					<td><input type="text"
																						value="Tiến độ <%= count2 + 1 %>"
																						name="muc<%= i %>.tiendo" style="width: 100%;"
																						readonly="readonly"></td>
																					<td><input type="text"
																						value="<%= info[k].split("_")[0] %>"
																						name="muc<%= i %>.tiendoTUNGAY"
																						style="width: 100%;" class="days"
																						readonly="readonly"></td>
																					<td><input type="text"
																						value="<%= info[k].split("_")[1] %>"
																						name="muc<%= i %>.tiendoDENNGAY"
																						style="width: 100%;" class="days"
																						readonly="readonly"></td>
																					<td><input type="text"
																						value="<%= info[k].split("_")[2] %>"
																						name="muc<%= i %>.tiendoPHAIDAT"
																						style="width: 100%; text-align: right;"
																						onkeypress="return keypress(event);"></td>
																				</tr>

																				<% 	count2++; } } %>

																				<% for(int j = count2; j < 5; j++ ) { %>
																				<tr>
																					<td><input type="text"
																						value="Tiến độ <%= j + 1 %>"
																						name="muc<%= i %>.tiendo" style="width: 100%;"
																						readonly="readonly"></td>
																					<td><input type="text" value=""
																						name="muc<%= i %>.tiendoTUNGAY"
																						style="width: 100%;" class="days"
																						readonly="readonly"></td>
																					<td><input type="text" value=""
																						name="muc<%= i %>.tiendoDENNGAY"
																						style="width: 100%;" class="days"
																						readonly="readonly"></td>
																					<td><input type="text" value=""
																						name="muc<%= i %>.tiendoPHAIDAT"
																						style="width: 100%; text-align: right;"
																						onkeypress="return keypress(event);"></td>
																				</tr>
																				<% } %>
																			</table>

																		</td>
																	</tr>
																	<tr>
																		<td valign="top" align="left" colspan="2"><a
																			class="button" href="javascript:DongLai();"> <img
																				style="top: -4px;" src="../images/button.png" alt="">
																				Đóng lại
																		</a></td>
																	</tr>
																</table>
															</div>
														</div></td>
													<% } %>
													<td><input type="text" name="tumuc"
														value="<%= tumuc[i] %>" style="text-align: right;"
														onkeyup="FormatNumber(this);"
														onkeypress="return keypress(event);"></td>
													<td><input type="text" name="denmuc"
														value="<%= denmuc[i] %>" style="text-align: right;"
														onkeyup="FormatNumber(this);"
														onkeypress="return keypress(event);"></td>
													<td><input type="text" name="chietkhau"
														value="<%= thuongSR[i] %>" style="text-align: right;"
														onkeypress="return keypress(event);"></td>
													<td><select name="donvi" style="width: 80%;">

															<% if( thuongTDSR[i].trim().equals("0") ) { %>
															<option value="0" selected="selected">%</option>
															<% } else { %>
															<option value="0">%</option>
															<% } %>

															<% if( thuongTDSR[i].trim().equals("1") ) { %>
															<option value="1" selected="selected">VNĐ</option>
															<% } else { %>
															<option value="1">VNĐ</option>
															<% } %>

															<% if( thuongTDSR[i].trim().equals("2") ) { %>
															<option value="2" selected="selected">Sản phẩm
															</option>
															<% } else { %>
															<option value="2">Sản phẩm</option>
															<% } %>

													</select> <a class="sanphamTRA<%= i %>" href="#"> <img
															style="top: -4px;" src="../images/vitriluu.png"
															title="Sản phẩm trả tích lũy"></a>
														<div style='display: none;'>
															<div id='sanphamTRA<%= i %>'
																style='padding: 0px 5px; background: #fff;'>
																<h4 align="left">
																	Sản phẩm trả mức (
																	<%= i + 1 %>
																	)
																</h4>
																<table cellpadding="4px" cellspacing="2px" width="100%"
																	align="center">
																	<tr>
																		<td width="150px" valign="top" align="left"><span
																			style="font-size: 12px">Hình thức</span></td>
																		<td valign="top" align="left"><select
																			name="sanphamTRA<%= i %>.hinhthuc" id="hinhthuctra"
																			style="width: 200px;">
																				<% if(infoDETAIL_SPTRA_HT.equals("0")) { %>
																				<option value="0" selected="selected">Bất
																					kỳ trong</option>
																				<% } else { %>
																				<option value="0">Bất kỳ trong</option>
																				<% } %>

																				<% if(infoDETAIL_SPTRA_HT.equals("1")) { %>
																				<option value="1" selected="selected">Nhập
																					số lượng sản phẩm</option>
																				<% } else { %>
																				<option value="1">Nhập số lượng sản phẩm</option>
																				<% } %>
																		</select></td>
																	</tr>
																	<tr>
																		<td colspan="2">
																			<table align="left" cellpadding="0px"
																				cellspacing="1px">
																				<tr class="tbheader">
																					<td width="100px" align="center"><span
																						style="font-size: 0.9em">Mã sản phẩm</span></td>
																					<td width="300px" align="center"><span
																						style="font-size: 0.9em">Tên sản phẩm</span></td>
																					<td width="100px" align="center"><span
																						style="font-size: 0.9em">Số lượng</span></td>
																				</tr>
																			</table>
																			<div id="sanphamTRA<%= i %>.tbsanpham"
																				style="width: 100%; max-height: 250px; overflow: auto">
																				<table align="left" cellpadding="0px"
																					cellspacing="1px">

																					<%
										                                			int count2 = 0;
										                                			if(infoDETAIL_SPTRA.trim().length() > 0)
										                                			{ 
										                                				String[] info = infoDETAIL_SPTRA.split("__")[1].split(";;");
										                                				for(int k = 0; k < info.length; k++ )
										                                				{
										                                					String[] info2 = info[k].split("_");
										                                					System.out.println("INFO - SP TRA ::: " + info[k]);
										                                				%>

																					<tr>
																						<td width="100px" align="center"><input
																							type="text" value="<%= info2[0] %>"
																							style="width: 100px"
																							name="sanphamTRA<%= i %>.masanpham"
																							onkeyup="ajax_showOptions(this,'abc',event)"
																							AUTOCOMPLETE="off"></td>
																						<td width="300px" align="left"><input
																							type="text" value="<%= info2[1] %>"
																							name="sanphamTRA<%= i %>.tensanpham"
																							style="width: 300px" readonly="readonly">
																						</td>
																						<td width="100px" align="left"><input
																							type="text" value="<%= info2[2].trim() %>"
																							name="sanphamTRA<%= i %>.soluong"
																							style="width: 100px; text-align: right;"
																							onkeypress="return keypress(event);"></td>
																					</tr>

																					<% 	count2++; } } %>

																					<% for(int pos = count2; pos < 10; pos++){ %>
																					<tr>
																						<td width="100px" align="center"><input
																							type="text" value="" style="width: 100px"
																							name="sanphamTRA<%= i %>.masanpham"
																							onkeyup="ajax_showOptions(this,'abc',event)"
																							AUTOCOMPLETE="off"></td>
																						<td width="300px" align="left"><input
																							type="text" value=""
																							name="sanphamTRA<%= i %>.tensanpham"
																							style="width: 300px" readonly="readonly">
																						</td>
																						<td width="100px" align="left"><input
																							type="text" value=""
																							name="sanphamTRA<%= i %>.soluong"
																							style="width: 100px; text-align: right;"
																							onkeypress="return keypress(event);"></td>
																					</tr>
																					<%} %>
																				</table>
																			</div>
																		</td>
																	</tr>
																	<tr>
																		<td valign="top" align="left" colspan="2"><a
																			class="button" href="javascript:DongLai();"> <img
																				style="top: -4px;" src="../images/button.png" alt="">
																				Đóng lại
																		</a></td>
																	</tr>
																</table>
															</div>
														</div></td>


													<td>
												</tr>

												<% count++; }
				                    		
				                    	}
				                    	
				                    	for(int i = count; i < 7; i++)
			                    		{
			                    			%>

												<tr>
													<td><input type="text" name="diengiaiMuc"
														value="Mức <%= ( i + 1 ) %>" readonly="readonly">
													</td>
													<TD><select name="httt1">

															<option value="0" selected="selected">Theo từng
																mức</option>

															<option value="1">Theo tổng số xuất</option>

													</select></TD>
													<% if(obj.getPhanloai().equals("0")) { %>
													<td align="center"><a class="muc<%= i %>" href="#">
															<img style="top: -4px;" src="../images/vitriluu.png"
															title="Tiến độ cần thực hiện">
													</a>
														<div style='display: none'>
															<div id='muc<%= i %>'
																style='padding: 0px 5px; background: #fff;'>
																<h4 align="left">Tiến độ cần phải đạt</h4>
																<table cellpadding="4px" cellspacing="2px" width="100%"
																	align="center">
																	<tr>
																		<td colspan="2">
																			<table align="left" cellpadding="0" cellspacing="1">
																				<tr class="tbheader">
																					<td width="150px" align="center"><span
																						style="font-size: 0.9em">Diễn giải</span></td>
																					<td width="100px" align="center"><span
																						style="font-size: 0.9em">Từ ngày</span></td>
																					<td width="100px" align="center"><span
																						style="font-size: 0.9em">Đến ngày</span></td>
																					<td width="100px" align="center"><span
																						style="font-size: 0.9em">Phải đạt ( % )</span></td>
																				</tr>
																				<% for(int j = 0; j < 5; j++ ) { %>
																				<tr>
																					<td><input type="text"
																						value="Tiến độ <%= j + 1 %>"
																						name="muc<%= i %>.tiendo" style="width: 100%;"
																						readonly="readonly"></td>
																					<td><input type="text" value=""
																						name="muc<%= i %>.tiendoTUNGAY"
																						style="width: 100%;" class="days"
																						readonly="readonly"></td>
																					<td><input type="text" value=""
																						name="muc<%= i %>.tiendoDENNGAY"
																						style="width: 100%;" class="days"
																						readonly="readonly"></td>
																					<td><input type="text" value=""
																						name="muc<%= i %>.tiendoPHAIDAT"
																						style="width: 100%; text-align: right;"
																						onkeypress="return keypress(event);"></td>
																				</tr>
																				<% } %>
																			</table>

																		</td>
																	</tr>
																	<tr>
																		<td valign="top" align="left" colspan="2"><a
																			class="button" href="javascript:DongLai();"> <img
																				style="top: -4px;" src="../images/button.png" alt="">
																				Đóng lại
																		</a></td>
																	</tr>
																</table>
															</div>
														</div></td>
													<% } %>
													<td><input type="text" name="tumuc" value=""
														style="text-align: right;" onkeyup="FormatNumber(this);"
														onkeypress="return keypress(event);"></td>
													<td><input type="text" name="denmuc" value=""
														style="text-align: right;" onkeyup="FormatNumber(this);"
														onkeypress="return keypress(event);"></td>
													<td><input type="text" name="chietkhau" value=""
														style="text-align: right;"
														onkeypress="return keypress(event);"></td>
													<td><select name="donvi" style="width: 80%;">
															<option value="0">%</option>
															<option value="1">VNĐ</option>
															<option value="2">Sản phẩm</option>
													</select> <a class="sanphamTRA<%= i %>" href="#"> <img
															style="top: -4px;" src="../images/vitriluu.png"
															title="Sản phẩm trả tích lũy"></a>
														<div style='display: none;'>
															<div id='sanphamTRA<%= i %>'
																style='padding: 0px 5px; background: #fff;'>
																<h4 align="left">
																	Sản phẩm trả mức (
																	<%= i + 1 %>
																	)
																</h4>
																<table cellpadding="4px" cellspacing="2px" width="100%"
																	align="center">
																	<tr>
																		<td width="150px" valign="top" align="left"><span
																			style="font-size: 12px">Hình thức</span></td>
																		<td valign="top" align="left"><select
																			name="sanphamTRA<%= i %>.hinhthuc" id="hinhthuctra"
																			style="width: 200px;">
																				<% if(obj.getHinhthuctra().equals("0")) { %>
																				<option value="0" selected="selected">Bất
																					kỳ trong</option>
																				<% } else { %>
																				<option value="0">Bất kỳ trong</option>
																				<% } %>

																				<% if(obj.getHinhthuctra().equals("1")) { %>
																				<option value="1" selected="selected">Nhập
																					số lượng sản phẩm</option>
																				<% } else { %>
																				<option value="1">Nhập số lượng sản phẩm</option>
																				<% } %>
																		</select></td>
																	</tr>
																	<tr>
																		<td colspan="2">
																			<table align="left" cellpadding="0px"
																				cellspacing="1px">
																				<tr class="tbheader">
																					<td width="100px" align="center"><span
																						style="font-size: 0.9em">Mã sản phẩm</span></td>
																					<td width="300px" align="center"><span
																						style="font-size: 0.9em">Tên sản phẩm</span></td>
																					<td width="100px" align="center"><span
																						style="font-size: 0.9em">Số lượng</span></td>
																				</tr>
																			</table>
																			<div id="sanphamTRA<%= i %>.tbsanpham"
																				style="width: 100%; max-height: 250px; overflow: auto">
																				<table align="left" cellpadding="0px"
																					cellspacing="1px">
																					<% for(int pos=0; pos < 10; pos++){ %>
																					<tr>
																						<td width="100px" align="center"><input
																							type="text" value="" style="width: 100px"
																							name="sanphamTRA<%= i %>.masanpham"
																							onkeyup="ajax_showOptions(this,'abc',event)"
																							AUTOCOMPLETE="off"></td>
																						<td width="300px" align="left"><input
																							type="text" value=""
																							name="sanphamTRA<%= i %>.tensanpham"
																							style="width: 300px" readonly="readonly">
																						</td>
																						<td width="100px" align="left"><input
																							type="text" value=""
																							name="sanphamTRA<%= i %>.soluong"
																							style="width: 100px; text-align: right;"
																							onkeypress="return keypress(event);"></td>
																					</tr>
																					<%} %>
																				</table>
																			</div>
																		</td>
																	</tr>
																	<tr>
																		<td valign="top" align="left" colspan="2"><a
																			class="button" href="javascript:DongLai();"> <img
																				style="top: -4px;" src="../images/button.png" alt="">
																				Đóng lại
																		</a></td>
																	</tr>
																</table>
															</div>
														</div></td>






												</tr>

												<%  }	
				                    	
				                    %>


												<TR>
													<TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
												</TR>
											</TABLE>

										</div>

										<% if(obj.getPhanloai().equals("1")) { %>

										<div id="tab2" class="tab_content">
											<TABLE class="tabledetail" width="100%" border="0"
												cellspacing="1px" cellpadding="0px">
												<tr>
													<td style="font-size: 12px; padding: 8px;" colspan="6">Nhóm
														sản phẩm &nbsp;&nbsp; <select name="nspId"
														style="width: 200px;" onchange="loadSPTheoNhom();">
															<option value=""></option>
															<%
													if(nhomSpRs != null)
													{
														while(nhomSpRs.next())
														{
															if(obj.getNhomsanphamIds().indexOf(nhomSpRs.getString("pk_seq")) >= 0 )
															{
																%>
															<option value="<%= nhomSpRs.getString("pk_seq") %>"
																selected="selected"><%= nhomSpRs.getString("ten") %></option>
															<% } else { %>
															<option value="<%= nhomSpRs.getString("pk_seq") %>"><%= nhomSpRs.getString("ten") %></option>
															<%}
														}
														nhomSpRs.close();
													}
												%>
													</select>
													</td>
												</tr>

												<TR class="tbheader">
													<td align="center" width="15%">Mã nhóm / sản phẩm</td>
													<td align="center" width="35%">Tên nhóm / sản phẩm</td>

													<td align="center" width="10%">Điều kiện</td>
													<td align="center" width="10%">Đơn vị</td>

													<td align="center" width="10%">Quy đổi</td>
													<td align="center" width="10%">Đơn vị</td>
												</TR>

												<% 
				                    count = 0;
				                    if(maspTra != null)
				                    {
				                    for(int i = 0; i < maspTra.length; i++) { 
				                    	
				                    	String _dieukien = "";
		                    			String _dvDieukien = "";
		                    			if( dieukien.get(maspTra[i]) != null )
		                    			{
		                    				_dieukien = dieukien.get(maspTra[i]).split("__")[0];
		                    				_dvDieukien = dieukien.get(maspTra[i]).split("__")[1];
		                    			}
		                    			
		                    			String _quydoi = "";
		                    			String _dvQuydoi = "";
		                    			if( quydoi.get(maspTra[i]) != null )
		                    			{
		                    				_quydoi = quydoi.get(maspTra[i]).split("__")[0];
		                    				_dvQuydoi = quydoi.get(maspTra[i]).split("__")[1];
		                    			}
		                    			
				                    	%>
												<tr>
													<td><input type="text" name="maspTra"
														style="width: 100%" value="<%= maspTra[i] %>"
														onkeyup="ajax_showOptions(this,'abc',event)"
														AUTOCOMPLETE="off"></td>
													<td><input type="text" name="tenspTra"
														style="width: 100%" readonly="readonly"
														value="<%= tenspTra[i] %>"></td>
													<td><input type="text" name="dieukien"
														value="<%= _dieukien %>" style="text-align: right;"
														onkeypress="return keypress(event);"></td>
													<td><select name="donviDIEUKIEN" style="width: 100%;">
															<% if(_dvDieukien.equals("0")) { %>
															<option value="0" selected="selected">Số lượng
															</option>
															<% } else { %>
															<option value="0">Số lượng</option>
															<% } %>
															<% if(_dvDieukien.equals("1")) { %>
															<option value="1" selected="selected">Số tiền</option>
															<% } else { %>
															<option value="1">Số tiền</option>
															<% } %>
													</select></td>

													<td><input type="text" name="quydoi"
														value="<%= _quydoi %>" style="text-align: right;"
														onkeypress="return keypress(event);"></td>
													<td><select name="donviQUYDOI" style="width: 100%;">
															<% if(_dvQuydoi.equals("0")) { %>
															<option value="0" selected="selected">Điểm</option>
															<% } else { %>
															<option value="0">Điểm</option>
															<% } %>
															<% if(_dvQuydoi.equals("1")) { %>
															<option value="1" selected="selected">VNĐ</option>
															<% } else { %>
															<option value="1">VNĐ</option>
															<% } %>
													</select></td>
												</tr>
												<% count++; } } %>

												<%
				                    	while(count < 50)
				                    	{
				                    		String _dieukien = "";
			                    			String _dvDieukien = "";
			                    			String _quydoi = "";
			                    			String _dvQuydoi = "";
			                    			
				                    		%>

												<tr>
													<td><input type="text" name="maspTra"
														style="width: 100%" value=""
														onkeyup="ajax_showOptions(this,'abc',event)"
														AUTOCOMPLETE="off"></td>
													<td><input type="text" name="tenspTra"
														style="width: 100%" readonly="readonly" value="">
													</td>
													<td><input type="text" name="dieukien"
														value="<%= _dieukien %>" style="text-align: right;"
														onkeypress="return keypress(event);"></td>
													<td><select name="donviDIEUKIEN" style="width: 100%;">
															<% if(_dvDieukien.equals("0")) { %>
															<option value="0" selected="selected">Số lượng
															</option>
															<% } else { %>
															<option value="0">Số lượng</option>
															<% } %>
															<% if(_dvDieukien.equals("1")) { %>
															<option value="1" selected="selected">Số tiền</option>
															<% } else { %>
															<option value="1">Số tiền</option>
															<% } %>
													</select></td>

													<td><input type="text" name="quydoi"
														value="<%= _quydoi %>" style="text-align: right;"
														onkeypress="return keypress(event);"></td>
													<td><select name="donviQUYDOI" style="width: 100%;">
															<% if(_dvQuydoi.equals("0")) { %>
															<option value="0" selected="selected">Điểm</option>
															<% } else { %>
															<option value="0">Điểm</option>
															<% } %>
															<% if(_dvQuydoi.equals("1")) { %>
															<option value="1" selected="selected">VNĐ</option>
															<% } else { %>
															<option value="1">VNĐ</option>
															<% } %>
													</select></td>
												</tr>

												<% count++; } %>


												<TR>
													<TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
												</TR>
											</TABLE>

										</div>

										<% } else { %>

										<div id="tab2" class="tab_content">

											<TABLE class="tabledetail" width="100%" border="0"
												cellspacing="1px" cellpadding="0px">

												<tr>
													<td style="font-size: 12px; padding: 8px;" colspan="2">Nhóm
														sản phẩm &nbsp;&nbsp; <select name="nspId"
														style="width: 200px;" onchange="loadSPTheoNhom();">
															<option value=""></option>
															<%
													if(nhomSpRs != null)
													{
														while(nhomSpRs.next())
														{
															if(obj.getNhomsanphamIds().indexOf(nhomSpRs.getString("pk_seq")) >= 0 )
															{
																%>
															<option value="<%= nhomSpRs.getString("pk_seq") %>"
																selected="selected"><%= nhomSpRs.getString("ten") %></option>
															<% } else { %>
															<option value="<%= nhomSpRs.getString("pk_seq") %>"><%= nhomSpRs.getString("ten") %></option>
															<%}
														}
														nhomSpRs.close();
													}
												%>
													</select>
													</td>
												</tr>

												<TR class="tbheader">
													<td align="center" width="20%">Mã sản phẩm</td>
													<td align="center" width="80%">Tên sản phẩm</td>
													<!-- <td align="center" width="10%">Chọn <input type="checkbox" name="checkAll" id="checkAll" onchange="SelectALL();" > </td> -->
												</TR>

												<%-- <%
				                    	if(sanphamRs != null)
				                    	{
				                    		while(sanphamRs.next())
				                    		{
				                    			%>
				                    			<tr>
				                    				<td><input type="text" value="<%= sanphamRs.getString("ma") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td><input type="text" value="<%= sanphamRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td align="center">
				                    					<% if(obj.getSpIds().contains(sanphamRs.getString("pk_seq"))) { %>
				                    						<input type="checkbox" name="spIds" value="<%= sanphamRs.getString("pk_seq") %>" checked="checked" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="spIds" value="<%= sanphamRs.getString("pk_seq") %>"  >
				                    					<% } %>
				                    				</td>
				                    				
				                    			</tr>
				                    			
				                    		<% }
				                    		sanphamRs.close();
				                    	}
				                    %> --%>

												<% 
				                    count = 0;
				                    if(maspTra != null)
				                    {
				                    for(int i = 0; i < maspTra.length; i++) { %>
												<tr>
													<td><input type="text" name="maspTra"
														style="width: 100%" value="<%= maspTra[i] %>"
														onkeyup="ajax_showOptions(this,'abc',event)"
														AUTOCOMPLETE="off"></td>
													<td><input type="text" name="tenspTra"
														style="width: 100%" readonly="readonly"
														value="<%= tenspTra[i] %>"></td>
												</tr>
												<% count++; } } %>

												<%
				                    	while(count < 50)
				                    	{
				                    		%>

												<tr>
													<td><input type="text" name="maspTra"
														style="width: 100%" value=""
														onkeyup="ajax_showOptions(this,'abc',event)"
														AUTOCOMPLETE="off"></td>
													<td><input type="text" name="tenspTra"
														style="width: 100%" readonly="readonly" value="">
													</td>
												</tr>

												<% count++; } %>

												<TR>
													<TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
												</TR>
											</TABLE>

										</div>

										<% } %>

										<%-- <div id="tab3" class="tab_content">
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
				                    
				                    <tr>
				                		<td style="font-size: 12px; padding: 8px; " colspan="2" valign="middle" >
				                	<b>Kênh bán hàng</b> &nbsp;&nbsp;   
				                		
				                			<select name="kenhId" style="width: 200px;" multiple="multiple"  >
												<option value="1" >Tất cả</option>
												<%
													if(kenhRs != null)
													{
														while(kenhRs.next())
														{
															if(obj.getKenhIds().indexOf(kenhRs.getString("pk_seq")) >= 0 )
															{
																%>
																<option value="<%= kenhRs.getString("pk_seq") %>" selected="selected"  ><%= kenhRs.getString("ten") %></option>
															<% } else { %>
																<option value="<%= kenhRs.getString("pk_seq") %>"  ><%= kenhRs.getString("ten") %></option>
															<%}
														}
														kenhRs.close();
													}
												%>							
											</select>
				                		&nbsp;&nbsp; 
				                	<b>Vùng </b>&nbsp;&nbsp;   
				                		
				                			<select name="vungId" style="width: 200px;" multiple="multiple"  >
												<option value="" >Tất cả</option>
												<%
													if(vungRs != null)
													{
														while(vungRs.next())
														{
															if(obj.getVungIds().indexOf(vungRs.getString("pk_seq")) >= 0 )
															{
																%>
																<option value="<%= vungRs.getString("pk_seq") %>" selected="selected"  ><%= vungRs.getString("ten") %></option>
															<% } else { %>
																<option value="<%= vungRs.getString("pk_seq") %>"  ><%= vungRs.getString("ten") %></option>
															<%}
														}
														vungRs.close();
													}
												%>							
											</select>
				                		&nbsp;&nbsp; 
				                	<b>Khu vực </b>
				                		&nbsp;&nbsp; 
				                			<select name="khuvucId" style="width: 200px;" multiple="multiple" >
												<option value="" >Tất cả</option>
												<%
													if(kvRs != null)
													{
														while(kvRs.next())
														{
															if(obj.getKhuvucIds().indexOf(kvRs.getString("pk_seq")) >= 0 )
															{
																%>
																<option value="<%= kvRs.getString("pk_seq") %>" selected="selected"  ><%= kvRs.getString("ten") %></option>
															<% } else { %>
																<option value="<%= kvRs.getString("pk_seq") %>"  ><%= kvRs.getString("ten") %></option>
															<%}
														}
														kvRs.close();
													}
												%>							
											</select>
				                		</td>	
				                		<td colspan="2" valign="middle" >
				                			<a class="button" href="javascript:submitform();">
        										<img style="top: -4px;" src="../images/button.png" alt=""> Hiển thị Npp theo điều kiện</a>
				                		</td>
				                		
				                    </tr>
				                    	<tr align="center">
				                		<td style="font-size: 12px; padding: 8px; " colspan="2" ><b >Mức</b>
				                			<select name="mucNPP" style="width: 150px;" onchange ="submitform();">
				                			<%
												int i = 0;
				                				
				                				while(i < 5)
				                				{
				                					String muc = Integer.toString(i);
				                			%>
				                			<%
												if(obj.getMucNPP().equals(muc))
													{
													%>
													<option value="<%= i %>" selected="selected"  >Mức<%=i+1%></option>
													<% } else { %>
													<option value="<%=i %>" > Mức<%=i+1%></option>
													
													<%}%>
												
													<% i++;
				                				}
												%>							
											</select>
				                		</td>
				                		</tr>
				                    <TR class="tbheader">
				                        <td align="center" width="20%">Mã nhà phân phối</td>
				                    	<td align="center" width="60%">Tên nhà phân phối</td>
				                    	<td align="center" width="10%">Số lượng</td>
				                    	<td align="center" width="10%">Chọn <input type="checkbox" name="checkAll2" id="checkAll2" onchange="SelectALL2();" > </td>
				                    </TR>
				                    <tr style="display: none">
				                    <td>
				                    		<input type="hidden" name="nppIds1" value="<%=obj.getNppIds1()==null?"":obj.getNppIds1()%>"  >
					                    				<input type="hidden" name="nppIds2" value="<%=obj.getNppIds2()==null?"":obj.getNppIds2()%>"  >
					                    				<input type="hidden" name="nppIds3" value="<%=obj.getNppIds3()==null?"":obj.getNppIds3()%>"  >
					                    				<input type="hidden" name="nppIds4" value="<%=obj.getNppIds4()==null?"":obj.getNppIds4()%>"  >
					                    				<input type="hidden" name="nppIds5" value="<%=obj.getNppIds5()==null?"":obj.getNppIds5()%>"  >
					                    </td>
				                    </tr>
				                    <%
				                    try{
				                    	if(nppRs != null)
				                    	{
				                    		System.out.println("Get KT "+obj.getKT() );
				                    		while(nppRs.next())
				                    		{
				                    			String soluongPHANBO = "";
				                    			if(phanbo.get(nppRs.getString("pk_seq")) != null )
				                    				soluongPHANBO = phanbo.get(nppRs.getString("pk_seq"));
				                    			if(obj.getMucNPP().equals("0"))
				                    				if(phanbo1.get(nppRs.getString("pk_seq")) != null )
				                    				soluongPHANBO = phanbo1.get(nppRs.getString("pk_seq"));
				                    			if(obj.getMucNPP().equals("1"))
				                    				if(phanbo2.get(nppRs.getString("pk_seq")) != null )
				                    				soluongPHANBO = phanbo2.get(nppRs.getString("pk_seq"));
				                    			if(obj.getMucNPP().equals("2"))
				                    				if(phanbo3.get(nppRs.getString("pk_seq")) != null )
				                    				soluongPHANBO = phanbo3.get(nppRs.getString("pk_seq"));
				                    			if(obj.getMucNPP().equals("3"))
				                    				if(phanbo4.get(nppRs.getString("pk_seq")) != null )
				                    				soluongPHANBO = phanbo4.get(nppRs.getString("pk_seq"));
				                    			if(obj.getMucNPP().equals("4"))
				                    				if(phanbo5.get(nppRs.getString("pk_seq")) != null )
				                    				soluongPHANBO = phanbo5.get(nppRs.getString("pk_seq"));
				                    			%>
				                    			
				                    			<tr>
				                    				
				                    				<td>
				                    				  <input type="hidden" name="mucnpp1" value="<%= obj.getMucNPP()== null ?"":obj.getMucNPP() %>"  >
				                    					<input type="hidden" name="soluongpb1" value="<%= phanbo1.get(nppRs.getString("pk_seq"))==null?"":phanbo1.get(nppRs.getString("pk_seq")) %>"  >
				                    					<input type="hidden" name="mapb1" value="<%= phanbo1.get(nppRs.getString("pk_seq"))==null?"":nppRs.getString("pk_seq")%>"  >
				                    					
				                    					<input type="hidden" name="soluongpb2" value="<%=phanbo2.get(nppRs.getString("pk_seq"))==null?"":phanbo2.get(nppRs.getString("pk_seq")) %>"  >
				                    					<input type="hidden" name="mapb2" value="<%=phanbo2.get(nppRs.getString("pk_seq"))==null?"":nppRs.getString("pk_seq")%>"  >
				                    					
				                    					<input type="hidden" name="soluongpb3" value="<%= phanbo3.get(nppRs.getString("pk_seq"))==null?"":phanbo3.get(nppRs.getString("pk_seq")) %>"  >
				                    					<input type="hidden" name="mapb3" value="<%= phanbo3.get(nppRs.getString("pk_seq"))==null?"":nppRs.getString("pk_seq")%>"  >
				                    					
				                    					<input type="hidden" name="soluongpb4" value="<%= phanbo4.get(nppRs.getString("pk_seq"))==null?"":phanbo4.get(nppRs.getString("pk_seq")) %>"  >
				                    					<input type="hidden" name="mapb4" value="<%= phanbo4.get(nppRs.getString("pk_seq"))==null?"":nppRs.getString("pk_seq")%>"  >
				                    					
				                    					<input type="hidden" name="soluongpb5" value="<%= phanbo5.get(nppRs.getString("pk_seq"))==null?"":phanbo5.get(nppRs.getString("pk_seq")) %>"  >
				                    					<input type="hidden" name="mapb5" value="<%= phanbo5.get(nppRs.getString("pk_seq"))==null?"":nppRs.getString("pk_seq")%>"  >	
				                    					
					                    			
				                    					
				                    				<%
				                    						String ids1 = obj.getNppIds1()==null?"":obj.getNppIds1();
					                    				    String ids2 = obj.getNppIds2()==null?"":obj.getNppIds2();
					                    				    String ids3 = obj.getNppIds3()==null?"":obj.getNppIds3();
					                    				    String ids4 = obj.getNppIds4()==null?"":obj.getNppIds4();
					                    				    String ids5 = obj.getNppIds5()==null?"":obj.getNppIds5();
				                    				
				                    				if(obj.getKT().equals("0")&& ids1.contains(nppRs.getString("pk_seq")) && obj.getMucNPP().equals("0")  || obj.getKT().equals("0")&& ids2.contains(nppRs.getString("pk_seq")) && obj.getMucNPP().equals("1") || obj.getKT().equals("0")&& ids3.contains(nppRs.getString("pk_seq")) && obj.getMucNPP().equals("2") 
				                    						|| obj.getKT().equals("0")&& ids4.contains(nppRs.getString("pk_seq")) && obj.getMucNPP().equals("3") || obj.getKT().equals("0")&& ids5.contains(nppRs.getString("pk_seq")) && obj.getMucNPP().equals("4")) 
				                    				{
				                    					///System.out.println("vaooooooooooooooooooooooooo");
				                    					%>
				                    					
				         								<input type="hidden" name="nppIds_ma" value="<%= nppRs.getString("pk_seq") %>"  >
				                    					
				                    					<input type="text" value="<%= nppRs.getString("ma") %>" style="width: 100%;" readonly="readonly" ></TD>
				                    				</td>
				                    				<td><input type="text" value="<%= nppRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td><input type="text" name="nppIds_phanbo" value="<%= soluongPHANBO %>" style="width: 100%; text-align: right;"  ></td>
				                    					<td align="center">
				                    					<% if(obj.getMucNPP().equals("0"))
				                    					if(ids1.contains(nppRs.getString("pk_seq"))) { %>
				
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>" checked="checked" >
				                    					<% }%>
				                    					<% if(obj.getMucNPP().equals("1"))
				                    					if(ids2.contains(nppRs.getString("pk_seq"))) { %>
				                    						
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>" checked="checked" >
				                    					<% }  %>
				                    					<% if(obj.getMucNPP().equals("2"))
				                    					if(ids3.contains(nppRs.getString("pk_seq"))) { %>
				                    						
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>" checked="checked" >
				                    					<% }%>
				                    					<% if(obj.getMucNPP().equals("3"))
				                    					if(ids4.contains(nppRs.getString("pk_seq"))) { %>
				                    						
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>" checked="checked" >
				                    					<% }  %>
				                    					<% if(obj.getMucNPP().equals("4"))
				                    					if(ids5.contains(nppRs.getString("pk_seq"))) { %>
				                    						
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>" checked="checked" >
				                    					<% }  %>
				                    				</td>
				                    					<% } else if(!obj.getKT().equals("1"))
				                    					{
				                    						%>	<input type="hidden" name="nppIds" value=""  > <% 
				                    							//	System.out.println("co vaoooo");
				                    					}
				                    				
				                    					else if(obj.getKT().equals("1"))
				                    					{
				                    					%>
				                    					
				                    					<input type="hidden" name="nppIds_ma" value="<%= nppRs.getString("pk_seq") %>"  >
				         
				                    					
				                    					<input type="text" value="<%= nppRs.getString("ma") %>" style="width: 100%;" readonly="readonly" >
				                    				</td>
				                    				<td><input type="text" value="<%= nppRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td><input type="text" name="nppIds_phanbo" value="<%= soluongPHANBO %>" style="width: 100%; text-align: right;"  ></td>
				                    				<td align="center">
				                    				
				                    					<% if(obj.getMucNPP().equals("0"))
				                    					if(obj.getNppIds1().contains(nppRs.getString("pk_seq"))) { %>
				
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>" checked="checked" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>"  >
				                    					<% } %>
				                    					<% if(obj.getMucNPP().equals("1"))
				                    					if(obj.getNppIds2().contains(nppRs.getString("pk_seq"))) { %>
				                    						
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>" checked="checked" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>"  >
				                    					<% } %>
				                    					<% if(obj.getMucNPP().equals("2"))
				                    					if(obj.getNppIds3().contains(nppRs.getString("pk_seq"))) { %>
				                    						
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>" checked="checked" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>"  >
				                    					<% } %>
				                    					<% if(obj.getMucNPP().equals("3"))
				                    					if(obj.getNppIds3().contains(nppRs.getString("pk_seq"))) { %>
				                    						
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>" checked="checked" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>"  >
				                    					<% } %>
				                    					<% if(obj.getMucNPP().equals("4"))
				                    					if(obj.getNppIds5().contains(nppRs.getString("pk_seq"))) { %>
				                    						
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>" checked="checked" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>"  >
				                    					<% } %>
				                    				
				                    						<%} else {%>
				                    							<input type="hidden" name="nppIds" value=""  >
				                    							<%} %>
				                    							
				                    					
				                    					
				                    				</td>
				                    				
				                    			</tr>
				                    			
				                    		<% }
				                    		nppRs.close();
				                    	}
				                    }
				                    catch(Exception ex)
				                    {
				                    	ex.printStackTrace();
				                    }
				                    %>
									
				                    <TR>
				                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
				                    </TR>
								</TABLE>
							</div> --%>




										<div id="tab4" class="tab_content">


											<table class="hoverTable" cellpadding="4px" cellspacing="2px" width="100%"
												align="center">
												<tr class="tbheader">
												<td width="3px" align="center"><span
														style="font-size: 0.9em">STT</span></td>
													<td width="22px" align="center"><span
														style="font-size: 0.9em">Mã</span></td>
													<td width="160px" align="center"><span
														style="font-size: 0.9em">Tên</span></td>
													<td width="310px" align="center"><span
														style="font-size: 0.9em">Địa chỉ</span></td>
													<td width="50px" align="center"><span
														style="font-size: 0.9em">Đăng ký</span></td>
												</tr>

												<%  	
								                               int i=0;
								                                	if(khachangRs != null)
				                    	{
				                    		while(khachangRs.next())
				                    		{

				                    			%>
												<tr>
												
												<td style="text-align: center;" > <%= i + 1 %> </td>
													<td><input name="khMa" type="text"
														value="<%= khachangRs.getString("ma") %>"
														style="width: 100%;" readonly="readonly"> <input
														name="khId" type="hidden"
														value="<%= khachangRs.getString("pk_Seq") %>"
														style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" name="khTen"
														value="<%= khachangRs.getString("ten") %>"
														style="width: 100%;" readonly="readonly"></td>
													<td><input type="text" name="khDiachi"
														value="<%= khachangRs.getString("diachi") %>"
														style="width: 100%;" readonly="readonly"></td>


									<%-- 				
								<a class="spGHICHU<%= i %>" href="#">
			                        <img style="top: -4px;" src="../images/vitriluu.png" title="Ghi chú"></a>
					            <div style='display:none'>
				                	<div id='spGHICHU<%= i %>' style='padding:0px 5px; background:#fff;'> --%>
				                	
				                	
				                	<td style="text-align: center;"> 
				                	<a href="" id="scheme_<%= i%>" rel="subcontent_<%= i %>">
			           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
			           	 		
		           	 		 		<DIV id="subcontent_<%= i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					                             background-color: white; width: 450px; max-height:300px; overflow:auto; padding: 4px;">
					                    <table width="95%" align="center">
																<tr>
																	<td colspan="2">
																		
																			<tr class="tbheader">
																				<td width="50px" align="center"><span
																					style="font-size: 0.9em">Mức</span></td>
																				<td width="150px" align="center"><span
																					style="font-size: 0.9em">Số xuất</span></td>
																				<td width="50px" align="center"><span
																					style="font-size: 0.9em">Duyệt</span></td>
																			</tr>
																			<%
																				int somuc = 7;
																				if(obj.getHTTT().equals("1"))
																					somuc = 1;
																				String khId=khachangRs.getString("pk_Seq");
																				for(int pos=0; pos < somuc; pos++){
																					String soxuat = khachhang_dangky.get(khachangRs.getString("pk_Seq")+"__"+pos) != null ? khachhang_dangky.get(khachangRs.getString("pk_Seq")+"__"+pos) : "";	
																					String duyet = khachhang_duyet.get(khachangRs.getString("pk_Seq")+"__"+pos) != null ? khachhang_duyet.get(khachangRs.getString("pk_Seq")+"__"+pos) : "";
																					
																					%>
																				<tr>
																					<td width="50px" align="center">
																						<input type="hidden"  style="width: 50px" name="khachhangDANGKY.<%= khachangRs.getString("pk_Seq") %>.muc"  value="<%= khachangRs.getString("pk_Seq") %>__<%=pos%>">
																									Mức	<%=pos+1 %>
																					</td>
																					<td width="150px" align="left">
																						<input type="text" value="<%=soxuat %>" name="khachhangDANGKY.<%= khachangRs.getString("pk_Seq") %>.soxuat"style="width: 150px" >
																					</td>
																					<td width="50px" align="left">
																					
																						<input type="checkbox"    <%=(duyet.length()>0&&duyet.equals(khachangRs.getString("pk_Seq")+"__"+pos))?"checked=\"checked\"":"" %>  value="<%=khachangRs.getString("pk_Seq")+"__"+pos  %>" name="khachhangDANGKY.<%= khachangRs.getString("pk_Seq") %>.duyet" style="width: 50px; text-align: left;" onkeypress="return keypress(event);">
																					</td>
																				</tr>
																				<%} %>
																			</table>
																  <div align="right">
					                     	<label style="color: red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('subcontent_<%= i %>')" > Đóng lại </a>
					                     </div>
					                     </DIV>
					                       <script type="text/javascript">
						            	dropdowncontent.init('scheme_<%= i  %>', "left-top", 300, "click");
						            </script>
					                     
					                     </td>
					                     </tr>
					                    
																
																
																
												<%  i++;  }
				                    		khachangRs.close();
				                    	}
								                              %>


											</table>
										</div>
									</div>
								</FIELDSET>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</form>
	<script type="text/javascript">
	replaces();	
</script>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%}%>