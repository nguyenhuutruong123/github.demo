<%@page import="geso.dms.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.dms.distributor.beans.dondathang.*" %>
<%@ page  import = "geso.dms.distributor.beans.dondathang.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<% IErpDondathangNpp lsxBean = (IErpDondathangNpp)session.getAttribute("lsxBean"); %>
<% ResultSet dvkdRs = lsxBean.getDvkdRs(); %>
<% ResultSet kbhRs = lsxBean.getKbhRs(); %>
<% ResultSet nppRs = lsxBean.getNppRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet dvtRs = lsxBean.getDvtRs(); %>
<% ResultSet congnoRs = lsxBean.getCongnoRs(); %>

<% 
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spTonkho = lsxBean.getSpTonkho();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spGianhap = lsxBean.getSpGianhap();
	String[] spSCheme = lsxBean.getSpScheme();
	String[] spVat = lsxBean.getSpVat();
	String[] spGiagoc = lsxBean.getSpGiagoc();
	String[] spBgId = lsxBean.getSpBgId();
	String[] spSoluongtt = lsxBean.getSoluongtt();
	String[] spGhichu = lsxBean.getSpGhichu();
	String[] spNgaygiaohang = lsxBean.getSpNgaygiaohang();
	String[] spChietkhau = lsxBean.getSpChietkhau();
	String[] spTrakmId = lsxBean.getSpTrakmId();
	NumberFormat formater = new DecimalFormat("##,###,###");
%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	} else { %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>SalesUp - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>


<style type="text/css">
#mainContainer {
	width: 660px;
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
	z-index: 100;
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
	z-index: 5;
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
}
</style>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>
<link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>
<script>
	var j = 0;
    $(document).ready(function()
    {
    	while(j < 50)
    	{
     	$(".spGHICHU"+j).colorbox({width:"46%", inline:true, href:"#spGHICHU"+j});
         //Example of preserving a JavaScript event for inline calls.
         $("#click").click(function(){ 
             $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DDT - Project.");
             return false;
         });
        j++;
    	}
    });
</script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-SpListDonDatHang.js"></script>

<script language="javascript" type="text/javascript">

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
	
	function replaces()
	{
		var spHansd = document.getElementsByName("spHansd");
		var spMa = document.getElementsByName("spMa");
		var spTen = document.getElementsByName("spTen");  
		var donvi = document.getElementsByName("donvi");
		var soluong = document.getElementsByName("soluong");
		var soluongtt = document.getElementsByName("soluongtt");
		var dongia = document.getElementsByName("dongia");
		var thanhtien = document.getElementsByName("thanhtien");
		var spvat = document.getElementsByName("spvat");
		var spGiagoc = document.getElementsByName("spGiagoc");
		var spBgId = document.getElementsByName("spBgId");
		var spTonkho = document.getElementsByName("spTonkho");
		var spCK = document.getElementsByName("chietkhau");
		
		var i;
		for(i = 0; i < spMa.length; i++)
		{
			if(spMa.item(i).value != "")
			{
				var sp = spMa.item(i).value;
				var pos = parseInt(sp.indexOf(" - "));

				if(pos > 0)
				{
					spMa.item(i).value = sp.substring(0, pos);
					sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
					
					spTen.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					
					donvi.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					dongia.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					spvat.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					spGiagoc.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					spCK.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					spTonkho.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					spBgId.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
					
					CapNhatGia(document.getElementsByName("donvi"), i);
				}
			}
			else
			{
				spMa.item(i).value = "";
				spTen.item(i).value = "";
				donvi.item(i).value = "";
				soluong.item(i).value = "";
				soluongtt.item(i).value = "";
				dongia.item(i).value = "";
				thanhtien.item(i).value = "";	
				spvat.item(i).value = "";
				spTonkho.item(i).value = "";
			}
		}	
		
		TinhTien();
		setTimeout(replaces, 300);
	}
	
	function TongchietkhauKM()
	{
		var ckTrakm = document.getElementsByName("thanhtien");
		var sum = 0;
		if(ckTrakm.length > 0)
		{
			
			for(h =0; h < ckTrakm.length; h++)
			{
				
				var thanh_tien = ckTrakm.item(h).value;
				thanh_tien = thanh_tien.replace(",","");
				
				if(thanh_tien != "")
					sum =  parseFloat(sum)  + parseFloat(thanh_tien) ;
			}
		}
		
		alert('chietkhau = ' + sum );
		
		return roundNumber(sum, 0);
		
	}
	 function TinhTien()
	 {
		 	var spMa = document.getElementsByName("spMa");
			var soluong = document.getElementsByName("soluong");
			var dongia = document.getElementsByName("dongia");
			var giagoc = document.getElementsByName("spGiagoc");
			var chietkhau = document.getElementsByName("chietkhau");
			var thueVAT = document.getElementsByName("spvat");
			var thanhtien = document.getElementsByName("thanhtien");
			var spScheme = document.getElementsByName("scheme");
			var dongiaBVAT = document.getElementsByName("dongiaBVAT");
			var spvat = document.getElementsByName("spvat");
			
			
			var tongtien = 0;
			var tongthanhtien = 0;
			var tongtienCKKM = 0;
			var tt_vat = 0;
			
			var vatTong = 0;
			
			for(var i = 0; i < spMa.length; i++)
			{
				dongiaBVAT.item(i).value = DinhDangTien(parseFloat(dongia.item(i).value.replace(/,/g,""))); ///1.1
				
				if(spMa.item(i).value != "" && dongia.item(i).value != "" && soluong.item(i).value != "" )
				{
					vatTong = parseFloat(spvat.item(i).value.replace(/,/g,"")) ;
					
					
					var _ck = chietkhau.item(i).value.replace(/,/g,"");
					if(_ck == '')
						_ck = '0';
					var _thueVAT = thueVAT.item(i).value.replace(/,/g,"");
					if(_thueVAT == '')
						_thueVAT = '0';	
					
					giagoc.item(i).value = parseFloat(dongia.item(i).value.replace(/,/g,"")) ; /*  Math.round ( / (1 - _ck/100)); */
					
					var tt = Math.round ( parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")) );
					
					var tienthue = Math.round ( parseFloat(tt / 1.1) * parseFloat(_thueVAT) / 100 );
					
					thanhtien.item(i).value = DinhDangDonGia( parseFloat(tt ).toFixed(0));
					tongthanhtien += parseFloat(thanhtien.item(i).value.replace(/,/g,""));
					
					tongtien += parseFloat (parseFloat(giagoc.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")));
					tt_vat += parseFloat (tienthue);
						}
				if(spScheme.item(i).value != "" )
				{
					var _ck = thanhtien.item(i).value.replace(/,/g,"");
					if(_ck == '')
						_ck = '0';
					
					tongtienCKKM += -1*parseFloat(_ck);
				}
			}
			tongtien = tongtien;
			
			var chietkhau = document.getElementById("ptChietKhau").value;
			if(chietkhau == '')
				chietkhau = '0';
			
			
			
			
			
			document.getElementById("txtBVAT").value = DinhDangTien(tongtien);
			
			var tongtienSAUCKKM = parseFloat(tongtien) - parseFloat (tongtienCKKM);	
			
			
			var ckNguyenDon = 0;
			if(chietkhau !='0')
			{
				ckNguyenDon =  tongtienSAUCKKM * parseFloat (chietkhau)/100.0;
			}
			document.getElementById("txtTongCK").value = DinhDangTien(ckNguyenDon);
			

			var TongTienTruocVat = tongtienSAUCKKM -ckNguyenDon;
			
			document.getElementById("txtTongSauCK").value = DinhDangTien(TongTienTruocVat);
			
			tt_vat=TongTienTruocVat* ( vatTong/100.0 ) ;
			document.getElementById("txtVAT").value = DinhDangTien(tt_vat);
			
			
			var tongtienSAUVAT = TongTienTruocVat + tt_vat;
	
			document.getElementById("txtSauVAT").value = DinhDangTien(tongtienSAUVAT);
			
			
			var tiengiam = document.getElementById("txtTiengiam").value.replace(/,/g,"");
			
			document.getElementById("txtThanhToan").value = DinhDangTien(parseFloat(tongtienSAUVAT) - parseFloat(tiengiam));
			
	 }
	
	 function CapNhatGia(e, stt)
	 { 
		 	/* //alert(e.value);
		 	
		 	var spMa = document.getElementsByName("spMa");
		 	var dongia = document.getElementsByName("dongia");
		 	//dongia.item(stt).value = DinhDangTien(e.value); 
		 
		    //DUNG AJAX LOAD LAI DON GIA NEU KO PHAI LA DON VI CHUAN
		 	var xmlhttp;
			if ( e.value == "" || spMa.item(stt).value == "" )
			{
				dongia.item(stt).value = "0";
			    return;
			}
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
				   dongia.item(stt).value = DinhDangTien(xmlhttp.responseText);
			   }
			}
			
			var dvtMA = encodeURIComponent(e.item(stt).value.replace(" ","+"));
			var spMA = encodeURIComponent(spMa.item(stt).value.replace(" ","+"));
			xmlhttp.open("GET","../../ErpDondathangNppSvl?spMa=" + spMA + "&dvt=" + dvtMA + "&locgiaQUYDOI=1",true);
			xmlhttp.send(); */
		 
	 }
	 
	 
	 function CapNhatSoLuong(e, stt)
	 { 
		 	//alert(e.value);
		 	var dhId = document.getElementById("dhId").value;
		 	var spMa = document.getElementsByName("spMa");
		 	var dongia = document.getElementsByName("dongia");
		 	var soluong = document.getElementsByName("soluong");
		 	var soluongtt = document.getElementsByName("soluongtt");
		 	
		 	//dongia.item(stt).value = DinhDangTien(e.value); 
		 
		    //DUNG AJAX LOAD LAI DON GIA NEU KO PHAI LA DON VI CHUAN
		 	var xmlhttp;
			if ( e.value == "" || spMa.item(stt).value == "" )
			{
				dongia.item(stt).value = "0";
			    return;
			}
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
				   console.log('[kq]'+xmlhttp.responseText);
				   var kq=xmlhttp.responseText;
				   if(kq=='0')
					{
					   soluongtt.item(stt).value =soluong.item(stt).value    ;
					}
			   }
			}
			
			var dvtMA = encodeURIComponent(e.value.replace(" ","+"));
			var spMA = encodeURIComponent(spMa.item(stt).value.replace(" ","+"));
			xmlhttp.open("GET","../../ErpDondathangNppSvl?spMa=" + spMA + "&dhId=" + dhId + "&locgiaQUYDOI=addSKU",true);
			xmlhttp.send();
	 }
	 
	 function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
		
		//num = (Math.round( num * 1000 ) / 1000).toString();
		
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
		
		if(isNaN(num))
			num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
			num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

		var kq;
		if(sole.length >= 0)
		{
			kq = (((sign)?'':'-') + num) + sole;
		}
		else
			kq = (((sign)?'':'-') + num);
		
		//alert(kq);
		return kq;
	}
	 function saveform()
	 {	
		 if(document.getElementById("aplaikm").value == "true")
		 {
			 alert('Đơn hàng này đã áp khuyến mại, bạn phải áp lại khuyến mại trước khi lưu');
			 return;
		 }
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['hctmhForm'].action.value='submit';
	     document.forms["hctmhForm"].submit();
	 }
	 
	 function Apkhuyenmai()
	 {
		 document.getElementById("btnApKhuyenMai").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		 document.forms['hctmhForm'].action.value='apkhuyenmai';
		 document.forms['hctmhForm'].submit();
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
	 
	function Dinhdang(element)
	{
		element.value = DinhDangTien(element.value);
		if(element.value== '' )
		{
			element.value= '';
		}
	}	
	
	function upload()
	{
		   if(document.forms["hctmhForm"].filename.value=="")
		   {
			   document.forms["hctmhForm"].dataerror.value="Chưa tìm file upload lên. Vui lòng chọn file cần upload.";
		   }else
		   {
			 document.forms["hctmhForm"].setAttribute('enctype', "multipart/form-data", 0);
			 document.forms["hctmhForm"].submit();	
		   }
	}
	 
	 
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
		
	});
</script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<script>

$(function() {
 
 	$("ul.tabs").tabs("div.panes > div");
});
</script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<style type="text/css">
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
		th {
		cursor: pointer;
		}	
  	</style>

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

<form name="hctmhForm" method="post" action="../../ErpDondathangNppUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="loaidonhang" value='<%= lsxBean.getLoaidonhang() %>'>
<INPUT type="hidden" name="aplaikm" id="aplaikm" value='<%=lsxBean.isAplaikhuyenmai() %>'>
<input type="hidden"  id="dhId" name="id" value='<%= lsxBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý tồn kho > Mua hàng từ nhà cung cấp > Đơn đặt hàng > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn  <%= lsxBean.getNppTen() %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<%-- <A href= "../../ErpDondathangNppSvl?userId=<%= userId %>&loaidonhang=<%= lsxBean.getLoaidonhang() %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A> --%>
        	<A href= "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangNppSvl?userId=" + userId+ "&loaidonhang="+ lsxBean.getLoaidonhang()) %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
	        	
	        	 <%-- <A href="../../ErpDondathangExcelSvl?userId=<%=userId%>&excelDetails=<%=lsxBean.getId() %>">
																<img src="../images/excel.gif" alt="In đơn hàng chi tiết Pdf" longdesc="In file Pdf" border="1px" height=30 width=30  style="border-style:outset">
																</A> --%>
				<A href="../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangExcelSvl?userId=" + userId+ "&excelDetails="+ lsxBean.getId()) %>">
																<img src="../images/excel.gif" alt="In đơn hàng chi tiết Pdf" longdesc="In file Pdf" border="1px" height=30 width=30  style="border-style:outset">
																</A>
																
			<%-- <A href="../../ErpDondathangPdf?userId=<%=userId%>&pdf=<%=lsxBean.getId()%>" ><img src="../images/Printer30.png" alt="In" title="In chung tu" longdesc="In chung tu" border=1 style="border-style:outset"></A> --%>
			<A href="../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangPdf?userId=" + userId+ "&pdf="+ lsxBean.getId()) %>" ><img src="../images/Printer30.png" alt="In" title="In chung tu" longdesc="In chung tu" border=1 style="border-style:outset"></A>
        </span>
        
       
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= lsxBean.getMsg() %></textarea>
		         <% lsxBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Đơn đặt hàng </legend>
        	<div style="float:none; width:100%" align="left">
            
            <TABLE width="100%" cellpadding="4" cellspacing="0">
            	<tr>
            		<TD width="130px" class="plainlabel" valign="top">Số đơn hàng </TD>
                    <TD width="250px"  class="plainlabel" valign="top" colspan = 3>
                    	<input type="text" readonly="readonly" value="<%= lsxBean.getId() %>" />
                    </TD>
            	</tr>												
                <TR>
                    <TD width="130px" class="plainlabel" valign="top">Ngày đơn hàng </TD>
                    <TD width="250px"  class="plainlabel" valign="top" >
                    	<input type="text" class="days" readonly="readonly"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>" onchange="submitform();" />
                    </TD>
                    
                    <TD width="120px" class="plainlabel" valign="top">Ngày đề nghị giao </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" class="days" readonly="readonly"  name="ngaydenghi" value="<%= lsxBean.getNgaydenghi() %>"/>
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" valign="top">Đơn vị kinh doanh </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<select name = "dvkdId" onchange="submitform();" class="select2" style="width: 200px" >
                    		<option value=""> </option>
                        	<%
                        		if(dvkdRs != null)
                        		{
                        			try
                        			{
                        			while(dvkdRs.next())
                        			{  
                        			if( dvkdRs.getString("pk_seq").equals(lsxBean.getDvkdId())){ %>
                        				<option value="<%= dvkdRs.getString("pk_seq") %>" selected="selected" ><%= dvkdRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= dvkdRs.getString("pk_seq") %>" ><%= dvkdRs.getString("ten") %></option>
                        		 <% } } dvkdRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>   
                    <TD class="plainlabel" valign="top">Kênh bán hàng </TD>
                    <TD class="plainlabel" valign="top"   >
                    	<select name = "kbhId" class="select2" style="width: 200px" onchange="submitform();" >
                    		<option value=""  > </option>
                        	<%
                        		if(kbhRs != null)
                        		{
                        			try
                        			{
                        			while(kbhRs.next())
                        			{  
                        			if( kbhRs.getString("pk_seq").equals(lsxBean.getKbhId())){ %>
                        				<option value="<%= kbhRs.getString("pk_seq") %>" selected="selected" ><%= kbhRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= kbhRs.getString("pk_seq") %>" ><%= kbhRs.getString("ten") %></option>
                        		 <% } } kbhRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>         	
                </TR>
                
                <TR>
                	<TD class="plainlabel" valign="top">Nhà phân phối </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<input type="hidden" name = "nppId" value="<%= lsxBean.getNppId() %>" > 
                    	<select class="select2" style="width: 200px" disabled="disabled" >
                    		<option value=""> </option>
                        	<%
                        		if(nppRs != null)
                        		{
                        			try
                        			{
                        			while(nppRs.next())
                        			{  
                        			if( nppRs.getString("pk_seq").equals(lsxBean.getNppId())){ %>
                        				<option value="<%= nppRs.getString("pk_seq") %>" selected="selected" ><%= nppRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nppRs.getString("pk_seq") %>" ><%= nppRs.getString("ten") %></option>
                        		 <% } } nppRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>   
                    <TD class="plainlabel" valign="top">Kho đặt </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<select name = "khonhapId" class="select2" style="width: 200px" onchange="submitform();">
                    		
                        	<%
                        		if(khonhapRs != null)
                        		{
                        			try
                        			{
                        			while(khonhapRs.next())
                        			{  
                        			if( khonhapRs.getString("pk_seq").equals(lsxBean.getKhoNhapId())){ %>
                        				<option value="<%= khonhapRs.getString("pk_seq") %>" selected="selected" ><%= khonhapRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khonhapRs.getString("pk_seq") %>" ><%= khonhapRs.getString("ten") %></option>
                        		 <% } } khonhapRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                        	<option value = "" ></option>
                    	</select>
                    </TD>         	
                </TR>
                
                <TR style = "display:none">
                    <TD class="plainlabel" >Tổng giá trị </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" readonly="readonly"  id="txtBVAT" value="" style="text-align: right;"  />
                    	<input type="hidden"  value="<%= lsxBean.getChietkhau() %>" id="txtPTChietKhau" style="text-align: right;" name="ptChietkhau" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" />
                    </TD>
                    
                    <TD class="plainlabel"  > </td>
                    <TD class="plainlabel"  >
                      </TD>
                    
                </TR>
                
                <TR >
                    
                    	
                    	
                    <TD class="plainlabel" valign="top">% Chiết khấu DLN</TD>
                    <TD class="plainlabel" valign="top"  >
                    	<input type="text" readonly="readonly"  value="<%= lsxBean.getPtChietKhau() %>" name="ptChietKhau" id="ptChietKhau" style="text-align: right;" />
                    </TD>
                    	
                    <TD class="plainlabel" valign="top">Tổng tiền trước VAT </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text" readonly="readonly"  value="" id="txtTongSauCK" style="text-align: right;" />
                    </TD>
                </TR>
                <TR>
                	<TD class="plainlabel" valign="top"  >Tiền chiết khấu DLN </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<input type="text" readonly="readonly"  value="" id="txtTongCK" style="text-align: right;" />
                    </TD>
                	
                	<TD class="plainlabel" >Tiền giảm </TD>
                    <TD class="plainlabel" >
                    	<input type="text"  name="txtTiengiam" id="txtTiengiam" value='<%= lsxBean.getTiengiam() %>' onkeyup="Dinhdang(this);" style="text-align: right;" />
                    </TD>
                    
                </TR>
                <TR>
                    <TD class="plainlabel" valign="top">Tiền VAT </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text" value="<%= lsxBean.getVat() %>" id="txtVAT" style="text-align: right;" name="ptVat" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" readonly='readonly' />
                    </TD>
                    	
                    <TD class="plainlabel" valign="top">Tổng tiền sau VAT </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly"  value="" id="txtSauVAT" style="text-align: right;" />
                    </TD>
                </TR>
                
                
                
                <TR>
                    <TD class="plainlabel" valign="top">Công nợ </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" value="<%= lsxBean.getCongno() %>" id="txtCongno" style="text-align: right;" name="ptVat" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" readonly='readonly' />
                    </TD>
                    
                    <TD class="plainlabel" valign="top" style="color: red;" >Số tiền thanh toán</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly"  value="" id="txtThanhToan" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Ghi chú </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 700px;" />
                    </TD>
                </TR>
                
                <tr class="plainlabel" style="display: none;">
			  	  	<td >
			  	  		<INPUT type="file" name="filename" size="40" value=''> 
			  	  	</td> 
			  	  	<td colspan="3">
			  			&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:upload()">
						<img style="top: -4px;" src="../images/button.png" alt="">UpLoad File</a>							
			  	 	</td>
				</tr>
                
                <TR  >
				   <TD  class="plainlabel" colspan = '4'>
				   <div id="btnApKhuyenMai">
				  		<a class="button2" href="javascript:Apkhuyenmai()">
							<img style="top: -4px;" src="../images/New.png" alt="">Lưu & Áp khuyến mại </a>
					</div>									
				  </TD>
				</TR>
               
            </TABLE>
			
			<ul class="tabs">
		                  			
         		<li><a href="#">Thông tin đơn hàng</a></li>
          	</ul>
             			
            <div class="panes">
              										
			<div>
			
				<table cellpadding="0px" cellspacing="1px" width="100%">
					<tr class="tbheader">
						<th align="center" width="3%" >STT</th>
						<th align="center" width="8%" >Mã sản phẩm</th>
						<th align="center" width="18%" >Tên sản phẩm</th>
						<th align="center" width="5%" >Đơn vị</th>
						<th align="center" width="10%" >Tồn kho</th>
						<th align="center" width="7%" >Số lượng</th>
						<th align="center" width="7%" style = "display:none"><font  color="red">Số lượng TT duyệt</font></th>
						<th align="center" width="10%" style="display: none;">Đơn giá gốc</th>
						<th align="center" width="10%" >Đơn giá sau CK</th>
						<!-- <th align="center" width="8%" >Chiết khấu</th> -->
						<!-- <th align="center" width="10%" >% VAT</th> -->
						<th align="center" width="10%" >Thành tiền</th>
						<th align="center" width="7%" >CTKM</th>
						<th align="center" width="10%" >Ngày có hàng</th>
						<th align="center" width="5%" >Ghi chú</th>
					</tr>
					
				<%
						int count = 0;
						if(spMa != null)
						{
							for(int i = 0; i < spMa.length; i++)
							{%>
							
							<tr onMouseover="ddrivetip('<%=spGhichu[i] %>', 250)"; onMouseout='hideddrivetip()'>
								<%  
								if(spSCheme[i].trim().length() <= 0 ) { %>
								
									<td style="text-align: center;" > <%= i + 1 %> </td>
									<td>
										<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 96%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
									</td>
									<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 99%" readonly="readonly"> </td>
									<td>
										<%-- <input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%" readonly="readonly"> --%>
										<%-- <select name="donvi" style="width: 100%;" onchange="CapNhatGia(this, <%= count %>);" >
											<option value="" ></option>
											<% if(dvtRs != null) 
											{ 
													dvtRs.beforeFirst();
													while(dvtRs.next())
													{
														if(spDonvi[i].equals(dvtRs.getString("DONVI")))
														{ %>
															<option value="<%= dvtRs.getString("DONVI") %>" selected="selected" ><%= dvtRs.getString("DONVI") %></option>
														<% } else { %>
															<option value="<%= dvtRs.getString("DONVI") %>" ><%= dvtRs.getString("DONVI") %></option>
													   <% } }
											} %>
										 </select>  --%>
										 <input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%" readonly="readonly">
										 
										 <% if(spTrakmId != null){ %>
										 <input type="hidden" name="spTrakmId" value="<%= spTrakmId[i] %>" style="width: 96%; color: red;" readonly="readonly">
										 <% } else { %>
										 <input type="hidden" name="spTrakmId" value="" style="width: 96%; color: red;" readonly="readonly">
										 <% } %>
									</td>
									
									<td> <input type="text" name="spTonkho" value="<%= spTonkho[i] %>" style="width: 100%" readonly="readonly"> </td>
									
									<td> <input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width:96%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);"  > </td>
									<td style="display:none"> <input type="text" name="spvat" value="<%= spVat[i] %>" style="width: 100%; text-align: right;" readonly="readonly"  > </td>
									<td style = "display:none"> <input type="text" name="soluongtt" value="<%= spSoluongtt[i] %>"  readonly="readonly" style="width:96%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
									
									<td style="display: none;"> 
									<% if(spSCheme[i].trim().length() <= 0) { %> 
										<input type="text" name="spGiagoc" value="<%= spGiagoc[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > 
									<% } else {  %>
										<input type="text" name="spGiagoc" value="0" style="width: 100%; text-align: right;" readonly="readonly" > 
									<% } %>
									</td>
									<td> <input type="text" name="dongiaBVAT" value="" style="width: 97%; text-align: right;" readonly="readonly" > </td>
									<td style = "display:none"> <input type="text" name="dongia" value="<%= spGianhap[i] %>" style="width: 97%; text-align: right;" readonly="readonly" > </td>
									
									
									<td style="display:none">
									<% if(spChietkhau != null){ %> 
										<input type="text" name="chietkhau" value="<%= spChietkhau[i] %>" style="width: 96%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" >
									<% } else { %> 
										<input type="text" name="chietkhau" value="" style="width: 96%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" >
									<% } %>
									</td>
									<td> <input type="text" name="thanhtien" value="" style="width: 96%; text-align: right;" readonly="readonly" > </td>
									<td>
										 <input type="text" name="scheme" value="" style="width: 97%; text-align: right;" readonly="readonly" > 
										 <input type="hidden" name="spBgId" value="<%=spBgId[i] %>" style="width: 100%; text-align: right;" readonly="readonly" >
										 </td>
									<td> <input type="text" name="spNgaygiaohang" value="<%= spNgaygiaohang[i] %>" style="width: 97%; text-align: right;" readonly="readonly" class="days" > </td>
									<td style="text-align: center;" >
										<a class="spGHICHU<%= i %>" href="#">
				                        <img style="top: -4px;" src="../images/vitriluu.png" title="Ghi chú"></a>
							            <div style='display:none'>
						                	<div id='spGHICHU<%= i %>' style='padding:0px 5px; background:#fff;'>
												<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
													<tr>
														<td>Ghi chú</td>
													 	<td> <input type="text" name="spGhichu" value="<%=spGhichu[i] %>" style="width: 100%; "  >	 </td>
													</tr> 
												</table>
											</div>
							           	</div>
						           	</td>
								<% } else { %>
									<td style="text-align: center;" > <%= i + 1 %> </td>
									<td>
										<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 96%; color: red;"  readonly="readonly"  > 
									</td>
									<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 99%; color: red;" readonly="readonly"> </td>
									<td>
										<input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 96%; color: red;" readonly="readonly">
										<input type="hidden" name="spTrakmId" value="<%= spTrakmId[i] %>" style="width: 96%; color: red;" readonly="readonly">
									</td>
									
									<td> <input type="text" name="spTonkho" value="<%= spTonkho[i] %>" style="width: 100%" readonly="readonly"> </td>
									
									<td> <input type="text" name="soluong" value="<%=spSoluong[i] %>" style="width: 97%; color: red; text-align: right;" readonly="readonly" > </td>
									<td style = "display:none"> <input type="text" name="soluongtt" value="<%=spSoluongtt[i] %>" style="width: 97%; color: red; text-align: right;"  readonly="readonly" > </td>
									<td style="display:none"> <input type="text" name="spvat" value="<%= spVat[i] %>" style="width: 100%; text-align: right;" readonly="readonly"  > </td>
									<td style="display: none;"> <input type="text" name="spGiagoc" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
									
									<td style = "display:none"> <input type="text" name="dongiaBVAT" value="" style="width: 97%; text-align: right;" readonly="readonly" > </td>
									
									<td> <input type="text" name="dongia" value="" style="width: 96%; text-align: right; color: red;" readonly="readonly" > </td>
									<td style="display:none"> <input type="text" name="chietkhau" value="<%= spChietkhau[i] %>" style="width: 96%; text-align: right; color: red;" readonly="readonly" > </td>
									
									<td> 
										<% if(spSoluong[i].trim().length() > 0 && !spSoluong[i].trim().equals("0") ) { %>
											<input type="text" name="thanhtien" value="0" style="width: 96%; color: red; text-align: right;" readonly="readonly" > 
										<% } else { %>
											<input type="text" name="thanhtien" value="-<%= spGianhap[i] %>" style="width: 96%; color: red; text-align: right;" readonly="readonly" > 
										<% } %>
									</td>
									<td colspan="3"> 
										<input type="text" name="scheme" value="<%= spSCheme[i] %>" style="width: 97%; color: red; " readonly="readonly" >
										<input type="hidden" name="spBgId" value="" style="width: 100%; text-align: right;" readonly="readonly" > 
									</td>
									<td style="display:none"> <input type="text" name="spNgaygiaohang" value="" style="width: 97%; text-align: right;" readonly="readonly" class="days" > </td>
									<td style="display:none"> <input type="hidden" name="spGhichu" value=""  > </td>
								<% } %>
							</tr>	
						<% count ++; } } %>
					
					<% for(int i = count; i < 50; i++) { %>
						
						<tr>
							<td style="text-align: center;" > <%= i + 1 %> </td>
							<td>
								<input type="text" name="spMa" value="" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
							</td>
							<td> <input type="text" name="spTen" value="" style="width: 100%" readonly="readonly"> </td>
							<td>
								<%-- <select name="donvi" style="width: 100%" onchange="CapNhatGia(this, <%= i %>);" >
									<option value="" ></option>
										<% if(dvtRs != null) 
										{ 
											dvtRs.beforeFirst();
											while(dvtRs.next())
											{ %>
												<option value="<%= dvtRs.getString("DONVI") %>"  ><%= dvtRs.getString("DONVI") %></option>
										   <% } 
										} %>
								 </select>  --%>
								  <input type="text" name="donvi" value="" style="width: 100%" readonly="readonly">
								 <input type="hidden" name="spTrakmId" value="" style="width: 96%; color: red;" readonly="readonly">
							</td>
							<td> <input type="text" name="spTonkho" value="" style="width: 100%" readonly="readonly"> </td>
							<td> <input type="text" name="soluong" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
							<td style = "display:none"> <input type="text" name="soluongtt" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" readonly="readonly" > </td>
							<td style="display: none;"> <input type="text" name="spGiagoc" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							
							<td> <input type="text" name="dongiaBVAT" value="" style="width: 97%; text-align: right;" readonly="readonly" > </td>
							
							<td style = "display:none"> <input type="text" name="dongia" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							<td style="display:none"> <input type="text" name="spvat" value="" style="width: 100%; text-align: right;" readonly="readonly"  > </td>
							<td style="display:none"> <input type="text" name="chietkhau" value="" style="width: 96%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
							<td> <input type="text" name="thanhtien" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							<td> 
								<input type="text" name="scheme" value="" style="width: 100%; text-align: right;" readonly="readonly" >
								<input type="hidden" name="spBgId" value="" style="width: 100%; text-align: right;" readonly="readonly" > 
							</td>
							<td> <input type="text" name="spNgaygiaohang" value="" style="width: 97%; text-align: right;" readonly="readonly" class="days" > </td>
							<td style="text-align: center;"> 
								<a class="spGHICHU<%= i %>" href="#">
			                        <img style="top: -4px;" src="../images/vitriluu.png" title="Ghi chú"></a>
					            <div style='display:none'>
				                	<div id='spGHICHU<%= i %>' style='padding:0px 5px; background:#fff;'>
										<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
											<tr>
												<td>Ghi chú</td>
											 	<td><input type="text" name="spGhichu" value="" style="width: 100%; " ></td>
											</tr> 
										</table>
									</div>
					           	</div>
							</td>
						</tr>	
						
						
						
					<% } %>	
					
					
							  	
					
					
					
					
					<%-- <%
						int count = 0;
						if(spMa != null)
						{
							for(int i = 0; i < spMa.length; i++)
							{%>
						
							<tr>
							<td style="text-align: center;" > <%= i + 1 %> </td>
							
								<td>
									<% if(spSCheme[i].trim().length() <= 0) { %>
										<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
									<% } else { %>
										<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 97%; color: red; "  readonly="readonly"  > 
									<% } %>
								</td>
								<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%" readonly="readonly"> </td>
								<td>
									<% if(spSCheme[i].trim().length() <= 0) { %>
										<select name="donvi" style="width: 100%" onchange="CapNhatGia(this, <%= count %>);" >
											<option value="" ></option>
											<% if(dvtRs != null) 
											{ 
													dvtRs.beforeFirst();
													while(dvtRs.next())
													{
														if(spDonvi[i].equals(dvtRs.getString("DONVI")))
														{ %>
															<option value="<%= dvtRs.getString("DONVI") %>" selected="selected" ><%= dvtRs.getString("DONVI") %></option>
														<% } else { %>
															<option value="<%= dvtRs.getString("DONVI") %>" ><%= dvtRs.getString("DONVI") %></option>
													   <% } }
											} %>
										 </select> 
									 <% } else { %>
									 	<input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%" readonly="readonly"  style="width: 97%; color: red; " >
									 <% } %>
								</td>
								
								<td>
									<% if(spSCheme[i].trim().length() <= 0) { %> 
										<input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" >
									<% } else { %>
										<input type="text" name="soluong" value="<%= spSoluong[i] %>"  style="width: 97%; color: red; " readonly="readonly" >
									<% } %> 
								</td>
								
								<td>
									
										<input type="text" name="soluongtt" value="<%= spSoluongtt[i] %>" style="width: 100%; text-align: right;" readonly="readonly" >
								</td>
								
								<td> 
									<% if(spSCheme[i].trim().length() <= 0) { %> 
										<input type="text" name="spGiagoc" value="<%= spGiagoc[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > 
									<% } else {  %>
										<input type="text" name="spGiagoc" value="0" style="width: 97%; color: red; "  readonly="readonly"  > 
									<% } %>
								</td>
								
								
								<td> 
									<% if(spSCheme[i].trim().length() <= 0) { %> 
										<input type="text" name="dongia" value="<%= spGianhap[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > 
									<% } else {  %>
										<input type="text" name="dongia" value="0"  readonly="readonly"  style="width: 97%; color: red; " > 
									<% } %>
								</td>
								
									<td> <input type="text" name="spvat" value="<%= spVat[i] %>" style="width: 100%; text-align: right;" readonly="readonly"  > </td>
								
								<td> 
									<% if(spSCheme[i].trim().length() <= 0) { %> 
										<input type="text" name="thanhtien" value="" style="width: 100%; text-align: right;" readonly="readonly" > 
									<% } else { %>
										<input type="text" name="thanhtien" value="<%= spGianhap[i] %>" style="width: 100%; text-align: right;" readonly="readonly" style="width: 97%; color: red; " > 
									<% } %>
								</td>
								<td> <input type="text" name="scheme" value="<%= spSCheme[i] %>" style="width: 100%;" readonly="readonly"  > 
								
								<input type="hidden" name="spBgId" value="<%=spBgId[i] %>" style="width: 100%; text-align: right;" readonly="readonly" >
								</td>
								
								<td> <input type="text" name="spNgaygiaohang" value="<%= spNgaygiaohang[i] %>" style="width: 97%; text-align: right;" readonly="readonly" > </td>
								<td style="text-align: center;">
									<a class="spGHICHU<%= i %>" href="#">
			                        <img style="top: -4px;" src="../images/vitriluu.png" title="Ghi chú"></a>
						            <div style='display:none'>
					                	<div id='spGHICHU<%= i %>' style='padding:0px 5px; background:#fff;'>
											<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
												<tr>
													<td>Ghi chú</td>
												 	<td> <input type="text" name="spGhichu" value="<%=spGhichu[i] %>" style="width: 100%; " readonly="readonly"  >	 </td>
												</tr> 
											</table>
										</div>
						           	</div>
					           	</td>
							</tr>	
								
						<% count ++; } } %> --%>
					
					
						
			
					
				</table>
			</div>
			
		
				
            </div>
     
     		</div>
     </fieldset>	
    </div>
</div>

<script type="text/javascript">
	replaces();
</script>

<%
	try
	{
		dvtRs.close();
		lsxBean.DBclose(); 
	}
	catch(Exception er){ }

	} %>
</form>
</BODY>
</html>