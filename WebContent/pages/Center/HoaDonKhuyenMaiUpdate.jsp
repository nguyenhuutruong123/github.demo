<%@page import="geso.dms.center.beans.hoadon.IHoaDon_CTKM"%>
<%@page import="java.util.Formatter"%>
<%@page import="java.util.Formattable"%>
<%@page import="geso.dms.center.beans.nhaphanphoi.INhaphanphoiList"%>
<%@page import="geso.dms.center.beans.hoadon.IHoaDon_SanPham"%>
<%@page import="geso.dms.center.beans.hoadon.IHoaDon"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.donhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<% IHoaDon hdBean = (IHoaDon)session.getAttribute("obj"); %>
<% List<IHoaDon_SanPham> splist = (List<IHoaDon_SanPham>)hdBean.getListSanPham(); %>
<% String userId = (String)session.getAttribute("userId"); %>
<% Hashtable<String, Integer> spThieuList = hdBean.getSpThieuList(); %>
<%	String userTen = (String)session.getAttribute("userTen"); %>
<%	ResultSet rs_nhacc=(ResultSet)session.getAttribute("rs_nhacc");
	ResultSet rs_dondathang=(ResultSet)session.getAttribute("rs_dondathang");
	ResultSet rs_khott=(ResultSet)session.getAttribute("rs_khott");
	ResultSet rs_nhapp=(ResultSet)session.getAttribute("rs_nhapp");
	ResultSet rs_kenhbanhang=(ResultSet)session.getAttribute("rs_kenhbanhang");
	ResultSet rs_dvkd=(ResultSet)session.getAttribute("rs_dvkd");
	String diachinhanhang=(String)session.getAttribute("diachinhanhang");
	INhaphanphoiList infonpp=hdBean.getInfoNhaPhoiPhoi();
	NumberFormat formatter=new DecimalFormat("#,###,###"); 
	List<IHoaDon_CTKM> listctkm =hdBean.getListCTKM();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
	#dhtmltooltip
	{
		position: absolute;
		left: -300px;
		width: 150px;
		border: 1px solid black;
		padding: 2px;
		background-color: lightyellow;
		visibility: hidden;
		z-index: 100;
		/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
		filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
	}	
	#dhtmlpointer
	{
		position:absolute;
		left: -300px;
		z-index: 101;
		visibility: hidden;
	}
	
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();})
</script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_bangianpp-mua.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/DocTienTiengViet.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/formattien.js"></script>
<script language="javascript" type="text/javascript">

function checkall(value){

	//day là cột tiền khuyễn mại(hidden) theo mỗi đơn hàng
	var tienkm=document.getElementsByName("ctkmsudung");
	//Cột check 
	var checkone=document.getElementsByName("checkvalue");
	//côt lưu lại giá trị check,nếu check là true thì value=1; false :0;
	var giatricheck=document.getElementsByName("valuecheck");
	var chuoi;
	if(value==true){
		chuoi="1";
	}else{
		chuoi="0";
	}
	var tongtienkm=0;
	for(var i=0;i<checkone.length;i++ ){
		checkone.item(i).checked=value;
		giatricheck.item(i).value=chuoi;
		tongtienkm=parseFloat(tongtienkm) + parseFloat(tienkm.item(i).value);
	}
	if(value==true){
	document.forms["dhForm"].sumtienkm.value= formattien(tongtienkm);
	}else{
		document.forms["dhForm"].sumtienkm.value=0;
	}
}

function recheck(){
	var tienkm=document.getElementsByName("ctkmsudung");
	var tong=0;
	var checkone=document.getElementsByName("checkvalue");
	var giatricheck=document.getElementsByName("valuecheck");
	for(var i=0;i<checkone.length;i++ ){
		if(checkone.item(i).checked==true){
			giatricheck.item(i).value="1";
			tong=parseFloat(tong)+parseFloat(tienkm.item(i).value);
		}else {
			giatricheck.item(i).value="0";
		}
	}
	document.forms["dhForm"].sumtienkm.value= formattien(tong);
}

function replaces()
{
	var masp = document.getElementsByName("masp");
	var tensp = document.getElementsByName("tensp");
	var donvitinh = document.getElementsByName("donvitinh");
	var dongia = document.getElementsByName("dongia");
	var chietkhau = document.getElementsByName("chietkhau");
	var soluong = document.getElementsByName("soluong");
	var thanhtien = document.getElementsByName("thanhtien");

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
				sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
				tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
				sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
				donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				var valuegia= sp.substring(0, parseInt(sp.indexOf("]")));
				dongia.item(i).value =formattien(valuegia);
			}
			if(parseInt(soluong.item(i).value) > 0)
			{				
				var don_gia = dongia.item(i).value;
				while(don_gia.match(",")){
					don_gia=don_gia.replace(",","");
					}
				
			    
				//tinh chiet khau theo san pham
				var valueck=document.forms['dhForm'].ChietKhau.value;
				var tck = (parseFloat(valueck) * parseInt(soluong.item(i).value) * parseFloat(don_gia)) / 100;
				chietkhau.item(i).value = formattien(tck); 
				//var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia) - parseFloat(chietkhau.item(i).value);
				var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia);
				thanhtien.item(i).value = formattien(tt);
			}
			else			
			{
				chietkhau.item(i).value = "";
				thanhtien.item(i).value = "";
			}
			if(checkMasp(masp.item(i).value) == true)
			{
				masp.item(i).parentNode.parentNode.bgColor = "#9FC";
			}
		}
		else
		{
			tensp.item(i).value = "";
			donvitinh.item(i).value = "";
			dongia.item(i).value = "";
			chietkhau.item(i).value = "";
			soluong.item(i).value = "";
			thanhtien.item(i).value = "";
			//ctkm.item(i).value = "";
			
		}
		TinhTien();
	}	
	setTimeout(replaces, 20);
	}
	

	
function TinhTien()
{
	var thanhtien = document.getElementsByName("thanhtien");
	var  chietkhau=document.getElementsByName("chietkhau");
	var tongtien = 0;
	var tongtienck=0;
	for(var i=0; i < thanhtien.length; i++)
	{
		if(thanhtien.item(i).value != "")
		{
			var thanh_tien = thanhtien.item(i).value.replace(",", "");
			while(thanh_tien.match(",")){
				thanh_tien=thanh_tien.replace(",","");
				}
			var chiet_khau=chietkhau.item(i).value.replace(",","");
			while(chiet_khau.match(",")){
				chiet_khau=chiet_khau.replace(",","");
				}
			//var thanh_tien = thanhtien.item(i).value;
			tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
			tongtienck=parseFloat(tongtienck)+parseFloat(chiet_khau);
		}
	}
	document.getElementById("tongtienkm").value=formattien(tongtienck);
	
	//document.forms['dhForm'].SoTienChuaVAT.value=tongtien;
	document.getElementById("SoTienChuaVat").value=formattien(tongtien);
	var vat = document.getElementById("VAT").value;
	if(vat == "")
		vat = "10";
	var tiencothue=(parseFloat(vat) * tongtien) / 100 + tongtien;
	document.getElementById("SoTienCoVAT").value =formattien(tiencothue);
	
	
	
  	 var tienphaitra= tiencothue-parseFloat(tongtienck) ;
  		 document.getElementById("tienphaitra").value=formattien(tienphaitra);
   		document.getElementById("doctien").value=DocTienBangChu(tienphaitra);
   		//KiemTraGioiHangCongNo();
}


	function checkMasp(masanpham)
	{
		var masp = document.getElementsByName("masp");
		for(i = 0; i < masp.length ; i++)
		{
			if(masp.item(i).value == masanpham)
				return true;
		}
		return false;
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
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	
	function saveform()
	 {	 
		 congDonSPCungMa();
		 var masp = document.getElementsByName("masp");
		 var tensp = document.getElementsByName("tensp");
		 var soluong = document.getElementsByName("soluong");
		var dongia=document.getElementsByName("dongia");
		var nhapp=document.forms['dhForm'].nhappid.value;
		
		if(nhapp==""){
			document.forms['dhForm'].dataerror.value="Vui lòng chọn nhà phân phối để xuất hàng ";
			return;
		}
		var khott= document.forms['dhForm'].khottid.value;
		if(khott==""){
			document.forms['dhForm'].dataerror.value="Chọn nhà phân phối để lấy kho ";
			return;
			
		}
		var kenhbanhang=document.forms['dhForm'].kenhbanhang.value;
		if(kenhbanhang==""){
			document.forms['dhForm'].dataerror.value="Vui lòng chọn kênh bán hàng để xuất hàng ";
			return;
		}
		var ngaygiaodich=document.forms['dhForm'].ngaygiaodich.value;
		if(ngaygiaodich.value==""){
			document.forms['dhForm'].dataerror.value="Vui lòng chọn ngày giao dịch để xuất hàng ";
			return;
		}
		var nhaccid =document.forms['dhForm'].nhaccid.value;
		if(nhaccid==""){
			document.forms['dhForm'].dataerror.value="Vui lòng chọn nhà cung cấp  để xuất hàng ";
			return;
		}
		
		var tongtien=document.forms['dhForm'].tienphaitra.value;
		
		var sumtienkm=document.forms['dhForm'].sumtienkm.value;
		//thuc hien bo dau , trong so da format
		while(sumtienkm.match(',')){
			sumtienkm=sumtienkm.replace(',','');
		}
		while(tongtien.match(',')){
			tongtien=tongtien.replace(',','');
		}
		//if(parseFloat(tongtien)<100000){
		//	document.forms['dhForm'].dataerror.value="Tổng Thanh Toán Của Đơn Hàng Không Được Dưới Mức 100 000 VND";
		//	return ;
		//}
		//thuc hien kiem tra tong tien cua don hang,co vuot tien duoc huong km khong?
		if(parseFloat(tongtien)>parseFloat(sumtienkm) ){
			document.forms['dhForm'].dataerror.value="Tổng Thanh Toán Của Đơn Hàng Không Được Vượt Mức Tiền Khuyến Mãi";
			return ;
		}
		//kiem tra neu gia =0 thi khong the thuc hien luu
		var ctkm_=document.getElementsByName("ctkmid");
		 for(var k = 0; k < masp.length; k++)
		 {
			if(masp.item(k).value != "")
			{
				if(soluong.item(k).value == "" || soluong.item(k).value  == "0" || tensp.item(k).value == "")			
				{
					document.forms['dhForm'].dataerror.value="Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn";
					return;
				}
				if(dongia.item(k).value == "" || dongia.item(k).value  == "0" || dongia.item(k).value == ""){
					document.forms['dhForm'].dataerror.value="Đơn giá của mặt hàng "+ tensp.item(k).value + ",không xác định. ";
					return ;	
				}
				if(ctkm_.item(k).value==""){
					document.forms['dhForm'].dataerror.value="Bạn phải chọn chương trình khuyến mại cho sản phẩm : "+tensp.item(k).value;
				return;
				}
			}
		 }
	 	 document.forms['dhForm'].action.value='duyet';
	     document.forms['dhForm'].submit();
	 }
	
	 function submitform()
	 { 
		congDonSPCungMa();
		document.forms['dhForm'].action.value='submit';
	    document.forms["dhForm"].submit();
	 }
	 function loadcontent()
	 {             
		document.forms['dhForm'].action.value='loadnpp';
	    document.forms["dhForm"].submit();
	 }
	 function ChotForm()
	 {
		 congDonSPCungMa();
		 var masp = document.getElementsByName("masp");
		 var tensp = document.getElementsByName("tensp");
		 var soluong = document.getElementsByName("soluong");
		 for(var k = 0; k < masp.length; k++)
		 {
			 if(masp.item(k).value != "")
				if(soluong.item(k).value == "" || tensp.item(k).value == "")
				{
					alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
					return;
				}
		 }
		 document.forms['dhForm'].action.value='chotdonhang';
		 document.forms['dhForm'].submit();
	 }
	 function congDonSPCungMa()
	 {
		var masp = document.getElementsByName("masp");
		var soluong = document.getElementsByName("soluong");
		var ii;
		for(ii = 0; ii < (masp.length - 1) ; ii++)
		{
			for( j = ii + 1; j < masp.length; j++)
			{
				if(masp.item(ii).value != "" && masp.item(ii).value == masp.item(j).value)
				{
					//alert(masp.item(ii).value + "-" + masp.item(j).value);				
					if(soluong.item(j).value == "")
						soluong.item(j).value = "0";
					
					soluong.item(ii).value = parseInt(soluong.item(ii).value) + parseInt(soluong.item(j).value);
					masp.item(j).value = "";
				}
			}
		}
	 }
	function addRow(name)
		{
			tableName = document.getElementById(name);
			
			var tr = document.createElement("TR");
			var maspAdd = document.createElement("TD");
			var tenspAdd = document.createElement("TD");
			var soluongAdd = document.createElement("TD");
			var dvtinhAdd = document.createElement("TD");
			var dongiaAdd = document.createElement("TD");
			var chietkhauAdd = document.createElement("TD");
			var thanhtienAdd = document.createElement("TD");
			var ctkmAdd = document.createElement("TD");
			
			var idsp = document.createElement("input");
			idsp.setAttribute("type", "hidden");
			idsp.setAttribute("readonly", "readonly");
			idsp.style.width = '100%';
			idsp.name = 'idsp';
			maspAdd.appendChild(idsp);
			
			var masp = document.createElement("input");
			masp.setAttribute("type", "textbox");
			masp.setAttribute("autocomplete","off");
			masp.setAttribute("onkeyup", "ajax_showOptions(this,'abc',event)");
			masp.style.width = '100%';
			masp.name = 'masp';
			maspAdd.appendChild(masp);
			
			var tensp = document.createElement("input");
			tensp.setAttribute("type", "textbox");
			tensp.setAttribute("readonly", "readonly");
			tensp.style.width = '100%';
			tensp.name = 'tensp';
			tenspAdd.appendChild(tensp);
			
			var soluong = document.createElement("input");
			soluong.setAttribute("type", "textbox");
			soluong.setAttribute("onkeypress","return keypress(event)");
			soluong.value = "";
			soluong.style.width = '100%';	
			soluong.name = "soluong";
			soluongAdd.appendChild(soluong);
			
			var dvt = document.createElement("input");
			dvt.setAttribute("type", "textbox");
			dvt.setAttribute("readonly", "readonly");
			dvt.value = "";
			dvt.style.width = '100%';
			dvt.name = 'donvitinh';
			dvtinhAdd.appendChild(dvt);
			
			var dongia = document.createElement("input");
			dongia.setAttribute("type", "textbox");
			dongia.setAttribute("readonly", "readonly");
			dongia.value = "";
			dongia.style.width = '100%';
			dongia.name = 'dongia';
			dongiaAdd.appendChild(dongia);
			
			var chietkhau = document.createElement("input");
			chietkhau.setAttribute("type", "textbox");
			chietkhau.setAttribute("readonly", "readonly");
			chietkhau.value = "";
			chietkhau.style.width = '100%';
			chietkhau.name = 'chietkhau';
			chietkhauAdd.appendChild(chietkhau);
			
			var thanhtien = document.createElement("input");
			thanhtien.setAttribute("type", "textbox");
			thanhtien.setAttribute("readonly", "readonly");
			thanhtien.value = "";
			thanhtien.style.width = '100%';
			thanhtien.name = "thanhtien";
			thanhtienAdd.appendChild(thanhtien);
			
		
		
			tr.appendChild(maspAdd);
			tr.appendChild(tenspAdd);
			tr.appendChild(soluongAdd);
			tr.appendChild(dvtinhAdd);
			tr.appendChild(dongiaAdd);
			tr.appendChild(chietkhauAdd);
			tr.appendChild(thanhtienAdd);
		
			
			tableName.appendChild(tr);
		}
		function ThemSanPham()
		{
			 var sl = window.prompt("Nhấp số lượng sản phẩm muốn thêm", '');
			 if(isNaN(sl) == false && sl < 30)
			 {
				 for(var i=0; i < sl ; i++)
					 addRow("san_pham");
			 }
			 else
			 {
				 alert('Số lượng bạn nhập không hợp lệ. Mọi lần bạn chỉ được thêm tối đa 30 sản phẩm');
				 return;
			 }
		 }
		
		function getinfoddh(){
			document.forms['dhForm'].action.value='loadddh';
			 document.forms['dhForm'].submit();
		}
</script>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="dhForm" method="post" action="../../HoaDonKMNewSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type="hidden" name="action" value='1'>
<INPUT type="hidden" name="trangthai" value=''>   
<input type="hidden" name="id"  value="<%=hdBean.getId()%>" >
<input type="hidden" name='tenform' value="updateform">

   
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
				<TABLE border =0 width = "100%" cellpadding="2" cellspacing="0">
				<TBODY>
					<TR height="22">
						<TD align="left" >
							<TABLE width="100%" cellpadding="0" cellspacing="0" >
								<TR>
									<TD align="left">
									   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										   <TR height="22">
			 								   <TD align="left"  class="tbnavigation">&nbsp;Xử lý hóa đơn > Hóa đơn khuyến mại> Chỉnh sửa </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp; </TD>
					    				 </TR>
									  </TABLE>
								  </TD>
							  </TR>	
						  	</TABLE>
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
								<TR ><TD >
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class = "tbdarkrow">
											<TD width="30" align="left"><A href = "javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay về " style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
										    <TD width="30" align="left" ><A href="javascript:saveform()" ><img src="../images/Chot.png" alt="Luu lai"  title="Chốt Đơn Hàng" border="1" longdesc="Luu lai" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
							    			<TD width="30" align="left"><A href="Print.jsp" ><img src="../images/Printer30.png" alt="In" title="In chung tu" longdesc="In chung tu" border=1 style="border-style:outset"></A></TD>
								    		<TD align="left" >&nbsp;</TD>
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="1" width="100%" cellpadding = "1" cellspacing = "0" style="border-color:gray;" >
								<tr>
								
								 <TD align="left" class="legendtitle">
									<fieldset style ="padding: 0px" >
									<legend> Báo lỗi nhập liệu</legend>
				    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%;margin:0px " readonly="readonly" rows="1"><%=hdBean.getMessage() %></textarea>
							  	</fieldset>
							  	 </TD>
								</tr>
								
								<TR class="plainlabel">
									<TD align="left" class="khoatest">						
										
										<TABLE class="khoatest" border="1" bordercolor="white" width="100%" cellpadding = "1" cellspacing = "0" style="padding-left:2px ;" >
											<tr class="plainlabel">
											<th width='15%' ></th>
											<th width='35%' ></th>
											<th width='15%' ></th>
											<th width='35%' ></th>
											</tr>
											<tr class="plainlabel" >
											<td >Nhà cung cấp : </td>
											<td >
												<select name='nhaccid' style="width: 100% ;background-color:white" >
												 <option value="" ></option>
													  <% if(rs_nhacc != null){
														  try{ while(rs_nhacc.next()){ 													 
											    			if(rs_nhacc.getString("pk_seq").trim().equals(hdBean.getIdNhaCungCap())){ %>
											      				<option value='<%=rs_nhacc.getString("pk_seq")%>' selected><%=rs_nhacc.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=rs_nhacc.getString("pk_seq")%>'><%=rs_nhacc.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select>	
											</td>
											<td > Chọn DVKD :</td>
											<td>
											 <select name='dvkdid' style="width: 100%;background-color: white" readonly onchange="submitform();">
												 <option value="" ></option>
													  <% if(rs_dvkd != null){
														  try{ while(rs_dvkd.next()){ 													 
											    			if(rs_dvkd.getString("pk_seq").trim().equals(hdBean.getIdDVKD())){ %>
											      				<option value='<%=rs_dvkd.getString("pk_seq")%>' selected><%=rs_dvkd.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=rs_dvkd.getString("pk_seq")%>'><%=rs_dvkd.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select>	
											</td>
											</tr>
											<TR class="plainlabel">
											  <TD class="plainlabel">Ngày giao dịch </TD>
											  <TD class="plainlabel">
											  <table border=0 cellpadding="0" cellspacing="0">
                                                <TR>
                                                <TD>
											    <input type="text" name="ngaygiaodich" style="width:100%" value="<%= hdBean.getNgaygiaodich() %>" >
											    </TD>
											     <TD>
												<a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'dhForm.ngaygiaodich',false, 'yyyy-mm-dd'); return false;">
		                                          &nbsp;<img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>
											    </TD>
										     	</TR>
                                          	</table>
                                          	</TD>
                                          <!-- chen vao kenh ban hang -->
                                          <td>Kênh bán hàng</td>
									          <td>
									          <select name='kenhbanhang' style="width: 100%;background-color:white" readonly onchange="submitform();">
												 <option value=""></option>
													  <% if(rs_kenhbanhang != null){
														  try{ while(rs_kenhbanhang.next()){ 				
											    			if(rs_kenhbanhang.getString("pk_seq").trim().equals(hdBean.getIDKenhBanHang())){ %>
											      				<option value='<%=rs_kenhbanhang.getString("pk_seq")%>' selected><%=rs_kenhbanhang.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=rs_kenhbanhang.getString("pk_seq")%>'><%=rs_kenhbanhang.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select> 
									          </td>
                                          
                                          	</TR>
											<TR class="plainlabel" >
												<TD >Nhà phân phối </TD>
												<td >
													<select name='nhappid' style="width: 100%; background-color:white" readonly onchange="loadcontent();">
													 <option value=""> </option>
													  <% if(rs_nhapp != null){						  
														  try{ while(rs_nhapp.next()){ 
											    			if(rs_nhapp.getString("pk_seq").trim().equals(hdBean.getNppId())){ %>
											      				<option value='<%=rs_nhapp.getString("pk_seq")%>' selected><%=rs_nhapp.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=rs_nhapp.getString("pk_seq")%>'><%=rs_nhapp.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select> 
												</td>
												<td>
												Mức chiết khấu(%)
												</td>
												<td>
												<input type='text'  readonly="readonly"  name='ChietKhau' style="width:100%" value="<%=hdBean.getChietkhau() %> ">
												</td>
											</TR>
											<tr class="plainlabel">
											<td>Địa chỉ nhận hàng: </td>
											<td colspan="3" >
											<input type='text'  readonly="readonly" name="diachinhanhang" style="width:100%" value="<%=infonpp.getDiaChi() %>">
											 
											</td>
											</tr>
											<tr class="plainlabel">
											<td>Điện thoại</td>
											<td><input type='text' readonly="readonly"  name="dienthoai" style="width:100%" value="<%=infonpp.getSodienthoai()%>" ></td>
											<td>Mã số thuế</td>
											<td>
											<input type='text' readonly="readonly"  name="Mã số thuế" style="width:100%" value="<%=infonpp.getMaSoThue() %>">
											</td>
											</tr>
											  <TR class="plainlabel" >
											  <TD  >Tổng số tiền(Chưa VAT) </TD>
											  <TD  ><input name="SoTienChuaVat" id="SoTienChuaVat"  type="text" readonly="readonly" 
											  	  value="<%=Math.round(hdBean.getTongtientruocVAT())%>" >
											  	VND </TD>
											  	 <td>
                                          	Kho xuất hàng :
                                          	</td>
                                          	<td>
                                          	<select name='khottid' style="width: 100%" disabled="disabled">
												 <option value=""></option>
													  <% if(rs_khott != null){
														  try{ while(rs_khott.next()){ 													 
											    			if(rs_khott.getString("pk_seq").trim().equals(hdBean.getKhottId())){ %>
											      				<option value='<%=rs_khott.getString("pk_seq")%>' selected><%=rs_khott.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=rs_khott.getString("pk_seq")%>'><%=rs_khott.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select>
                                          	</td>
											  </TR>
											  <TR class="plainlabel">
											    <TD  class="plainlabel">Tổng tiền(sau VAT) </TD>	     
										        <TD   class="plainlabel"><input name="SoTienCoVAT" id="SoTienCoVAT" type="text" readonly="readonly" 
										        	value="<%=(long)hdBean.getTongtiensauVAT()%>"> 
										          VND </TD>
										           <TD  class="plainlabel">VAT (%) </TD>
											  <TD  class="plainlabel"><input name="VAT" id="VAT" type="text" value="<%= Math.round(hdBean.getVAT()) %>" onkeypress="return keypress(event);">%</TD>										          										        
									       </TR>
											<TR class="plainlabel">
											  <TD  class="plainlabel">Tổng tiền chiết khấu :</TD>
											  <TD   class="plainlabel">
											  	<input name="tongtienkm" id='tongtienkm' type="text" readonly value="<%= hdBean.getTongtiensauVAT()-hdBean.getSoTienTraKM()%>">
									          VND </TD>
									        <TD  class="plainlabel">Phải trả :</TD>
											  <TD   class="plainlabel">
											  	<input name="tienphaitra" id='tienphaitra' type="text" readonly value="<%= hdBean.getTongtiensauVAT()-hdBean.getSoTienTraKM()-hdBean.getSoTienTraTB()%>">
									          VND </TD>
										  </TR>
										  <tr class="plainlabel">
										  <td>
										  Số tiền bằng chữ
										  </td>
										  <td colspan="3"> 
										  <textarea rows="1" style="width:100% ;font-size:11pt "  readonly="readonly" id="doctien">  </textarea>
										  </td>
										  </tr>

										</TABLE>
								
								  </TD>

							   </TR>	
							     <tr>
							   <!-- Danh Sach Chuong trinh khuyến mại -->
							   <td>
							   <table style=" margin-left :3px ;width :100%" border="0" cellpadding="0" cellspacing="1">
							   <tr class="tbheader">
							   <th width="20%" >Chương trình khuyễn mãi </th>
							   <th width="15%">Scheme</th>
							   <th width="35%">Diễn giải </th>
							   <th width="15%">Tổng tiền đã duyệt</th>
							   <th width="15%">Chọn <input type="checkbox" name="chonhet" onchange="checkall(this.checked);" disabled="disabled"> </th>
							   </tr>
							    <%int k=0; 
							   while(k < listctkm.size()){
								   IHoaDon_CTKM ctkm=listctkm.get(k);
								   %>
							   <tr class="plainlabel">
							  
								   <td style="padding-left:3px"  ><input type="hidden" name="chuongtrinhkm" value="<%=ctkm.getCTKM()%>">
								   	<%=ctkm.getCTKM()%> 
								   </td >
								   <td style="padding-left:3px">
								   <input type="hidden" name="tenctkm" value="<%=ctkm.getTenChuongTrinh()%>">
								   <%=ctkm.getTenChuongTrinh()%>
								   </td>
								   <td style="padding-left:3px">
								   <%=ctkm.getDienGiai()%>
								   <input type="hidden" name="diengiaictkm" value="<%=ctkm.getDienGiai()%>">
								   </td>
								   <td style="padding-left:3px">
								   <%=ctkm.getDaSuDung()%><input type="hidden" value="<%=Math.round(ctkm.getDaSuDung())%>" name="ctkmsudung" id="ctkmsudung">
								  
								   </td>
								   <td style="padding-left:3px;" align="center">
								   <%
								   //System.out.println("ID CHUONG TRINH :"+ctkm.getId());
								   if(ctkm.getId().equals("1")){ %>
								    <input type="checkbox" checked="checked" name="checkvalue" disabled="disabled" onchange="recheck();"><input name="valuecheck" type="hidden" value="<%=ctkm.getId()%>" >
									<%}else{ %>
									<input type="checkbox" name="checkvalue"  disabled="disabled" onchange="recheck();"><input name="valuecheck" type="hidden" value="<%=ctkm.getId()%>" >
									<%} %>								  
								   </td>			
							   	<td>
							   </td>
							   </tr>
							  
							      <%
								   k++;
							   }
							   
							   %>
 								<tr class="tbfooter">
							   	<td colspan="3" align="right">
							   		Tổng Tiền Được Khuyến Mại
							   	</td>
							   	 <td>
							   	 <input type ="text" readonly="readonly" name="sumtienkm" id="sumtienkm" style="width:100%;background-color:white">
							   	 </td>
							   	 <td>
							   	 </td>
							    </tr>
							    	
							   </table>
							   </td>
							   </tr>	
							   
							   <tr class="plainlabel" >
							   <td style="text-align: center">
								   Chi Tiết Hóa Đơn Hàng (Vui lòng chọn nhà phân phối,đơn vị kinh doanh,kênh bán hàng  trước khi sản phẩm cho đơn đặt hàng)
							   </td>
							   </tr>
							   <TR>
							   		<TD>
										<TABLE  width="100%"  border="0" cellpadding="0" cellspacing="1">
										<tbody id="san_pham">
												<TR class="tbheader" >
												<TH width="15%" height="20">Chọn Chương trình KM </TH>
													<TH width="15%" height="20">Mã sản phẩm </TH>
													<TH width="28%">Tên sản phẩm</TH>
													<TH width="8%">Số lượng</TH>
													<TH width="5%">DVT</TH>
													<TH width="10%">Đơn giá </TH>
													<TH width="7%">Chiết khấu </TH>
													<TH width="9%">Thành tiền </TH>		
												</TR>
									<% 
							if(splist != null){
							IHoaDon_SanPham sanpham;
							int size = splist.size();
							int m = 0;
							//System.out.println("So M La :"+size);
							while (m < size){
								sanpham = splist.get(m); 
								%>
									<TR class= 'tblightrow' >
									<TD>
									<select name="ctkmid" style="width:100%" >
									<option value=""></option>
									
									<% int t=0;
										while(t< listctkm.size()){
											IHoaDon_CTKM ctkm=listctkm.get(t);
											System.out.println( " Chuong Trinh KM SP :"+sanpham.getCTKMId());
												if(sanpham.getCTKMId().trim().equals(ctkm.getCTKM().trim())){
											%>
													<option selected="selected" value="<%= ctkm.getCTKM()%> " > <%=ctkm.getTenChuongTrinh() %> </option>
											<%		
												}else{
													%>
													<option value="<%= ctkm.getCTKM()%> " > <%=ctkm.getTenChuongTrinh() %> </option>
													<%
												}
											t++;  
										}
									%>
									</select>
									</TD>
									<TD align="left" >
										<input name='idsp' type='hidden' value=<%=sanpham.getIdSanPham() %>>
										<input name="masp" type="text" value="<%=sanpham.getMaSanPham()%>" autocomplete='off' onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%">
									</TD>
									<TD align="left" >
										<input name="tensp" type="text" readonly value="<%=sanpham.getTenSanPham()%>" style="width:100%" ></TD>
																	
						        	<% if (spThieuList != null  &&  spThieuList.containsKey(sanpham.getMaSanPham())){ %>
									    <TD align = "left"  class="addspeech">
								        <input name="soluong" type="text" value="<%= sanpham.getSoLuong() %>"  onkeypress="return keypress(event);" style="cursor:pointer; background-color:99CCCC;text-align:left ;width:100%" title="San pham nay con toi da <%= spThieuList.get(sanpham.getMaSanPham()) %> san pham, vui long chon lai so luong">
								        </TD>
								    <%}else{ %>
							        	<TD align = "left" >
								        <input name="soluong" type="text" value="<%= sanpham.getSoLuong() %>" onkeypress="return keypress(event);" style="text-align:left;width:100%;">
								        </TD>
								    <%} %>
								    <TD align = "center" ><input name="donvitinh" type="text" value="<%= sanpham.getDonViTinh() %>" readonly style="width:100%"></TD>
								    <TD align = "center" >
								    	<input type="text" name="dongia" value="<%= formatter.format(sanpham.getDonGia()) %>" readonly style="width:100% ;text-align:right">
								    </TD>
								    <TD align = "center" ><input name="chietkhau" type="text" <%=formatter.format(sanpham.getChietKhau()) %> readonly  style="width:100% ;text-align:right"></TD>
								    <TD align = "center" ><input name="thanhtien" type="text" value="<%=formatter.format(sanpham.getThanhTien()) %>" readonly  style="width:100% ;text-align:right"></TD>
								</TR>
								<% m++; }}%>		
								<%
									int soSp=0;
									while(soSp < 10){ 
								%>
								<TR class= 'tblightrow'>
								
								<TD>
									<select name="ctkmid" style="width:100%"  >
									<option value=""></option>
									
									<% int t=0;
										while(t< listctkm.size()){
											IHoaDon_CTKM ctkm=listctkm.get(t);
										
											%>
													<option value="<%= ctkm.getCTKM()%> " > <%=ctkm.getTenChuongTrinh() %> </option>
											<%		
											t++;  
										}
									%>
									</select>
									</TD>
									<TD align="center" >
									<input name='idsp' type='hidden'>
										<input name="masp" type="text" value="" autocomplete='off' onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" >
									</TD>
									<TD align="left" >
										<input name="tensp" type="text" readonly value="" style="width:100%"></TD>
										
								    <TD align = "left" >
							        	<input name="soluong" type="text" value="" style="width:100%; text-align:left" onkeypress="return keypress(event);">
							        </TD>
								    <TD align = "center" ><input name="donvitinh" type="text" value="" readonly style="width:100%;text-align:center"></TD>
								    <TD align = "center" ><input name="dongia" type="text" value="" readonly style="width:100%;text-align:right"></TD>
								    <TD align = "center" ><input name="chietkhau" type="text" value="" readonly style="width:100%;text-align:right"></TD>
								    <TD align = "center" ><input name="thanhtien" type="text" value="" readonly style="width:100%;text-align:right"></TD>
								</TR>
								<% soSp++;} %>
								</tbody>
								</TABLE>
										&nbsp;&nbsp;&nbsp;&nbsp;	<a class="button2" href="javascript:ThemSanPham()">
	                         			<img style="top: -4px;" src="../images/add.png" alt="">Thêm sản phẩm</a>&nbsp;&nbsp;&nbsp;&nbsp;
									</TD>
							  </TR>						   
						  </TABLE>
						</TD>
					</TR>	
					
				</TBODY>
			</TABLE>
	</td>
  </tr>

</TABLE>
<script type="text/javascript">
	//Call dropdowncontent.init("anchorID", "positionString", glideduration, "revealBehavior") at the end of the page:
	dropdowncontent.init("searchlink", "right-bottom", 500, "click");
	replaces();
	recheck();
</script>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

