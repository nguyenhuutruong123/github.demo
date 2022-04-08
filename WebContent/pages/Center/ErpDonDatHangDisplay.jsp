<%@page import="geso.dms.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.dms.center.beans.dondathang.*" %>
<%@ page  import = "geso.dms.center.beans.dondathang.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<% HttpSession s = request.getSession();
   if (s.isNew()){
	   s.invalidate();
	   System.out.println("New session");
   }else{
	   System.out.println("Old session");
   }
   
%>

<% IErpDondathang lsxBean = (IErpDondathang)s.getAttribute("lsxBean"); 
   IErpDondathangList lsxBeanList = (IErpDondathangList)s.getAttribute("lsxBeanList");
%>

<% ResultSet dvkdRs = lsxBean.getDvkdRs(); %>
<% ResultSet kbhRs = lsxBean.getKbhRs(); %>
<% ResultSet nppRs = lsxBean.getNppRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet dvtRs = lsxBean.getDvtRs(); %>
<% ResultSet congnoRs = lsxBean.getCongnoRs(); %>
<% ResultSet schemespecialRs = lsxBean.getSchemeSpecialRs(); %>

<% 
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spGianhap = lsxBean.getSpGianhap();
	String[] spChietkhau = lsxBean.getSpChietkhau();
	String[] spSCheme = lsxBean.getSpScheme();
	String[] spTrongluong = lsxBean.getSpTrongluong();
	String[] spThetich = lsxBean.getSpThetich();
	String[] spQuyDoi = lsxBean.getSpQuyDoi();
	String[] spVat = lsxBean.getSpVat();
	String[] spGhichu = lsxBean.getSpGhichu();
	String[] spSoluongtt = lsxBean.getSoluongtt();
	String[] spNgaygiaohang = lsxBean.getSpNgaygiaohang();
	String[] spGiagoc = lsxBean.getSpGiagoc();
	String[] spBgId = lsxBean.getSpBgId();
	String[] spTonkho = lsxBean.getSpTonkho();
	
	String[] dhCk_diengiai = lsxBean.getDhck_diengiai();
	String[] dhCk_giatri = lsxBean.getDhck_giatri();
	String[] dhCk_loai = lsxBean.getDhck_loai();
	
	NumberFormat formater = new DecimalFormat("##,###,###");
	if(lsxBean.getKbhId().equals("100052")) 
	{
		formater = new DecimalFormat("##,###,###.##");
	}	
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
<script type="text/javascript" src="../scripts/dropdowncontent2.js"></script>


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
		var spDongiaBVAT = document.getElementsByName("spDongiaBVAT");
		var pokhuyenmai = document.getElementById("pokhuyenmai");
		
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

					spDongiaBVAT.item(i).value = DinhDangTien(parseFloat(dongia.item(i).value.replace(/,/g,"")) / 1.1);
				}
				if(pokhuyenmai.checked == true)
				{
					dongia.item(i).value = "0";
					spGiagoc.item(i).value = "0";
				}
				if(soluong.item(i).value == "")
				{
					soluong.item(i).value = soluongtt.item(i).value;
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
	
	
	 function TinhTien()
	 {
		 var spMa = document.getElementsByName("spMa");
		var soluong = document.getElementsByName("soluongtt");
		var dongia = document.getElementsByName("dongia");
		var giagoc = document.getElementsByName("spGiagoc");
		var chietkhau = document.getElementsByName("chietkhau");
		var thanhtien = document.getElementsByName("thanhtien");			
		var thueVAT = document.getElementsByName("spvat");
		var spScheme = document.getElementsByName("scheme");
		var spDongiaBVAT = document.getElementsByName("spDongiaBVAT");
		
		var tongtien = 0;
		var tongthanhtien = 0;
		var tongtienCK = 0;
		var tongtienCKKM = 0;
		var tt_vat = 0;
		for(var i = 0; i < spMa.length; i++)
		{
			var dongiabvat = DinhDangTien(parseFloat(dongia.item(i).value.replace(/,/g,"")) / 1.1);
			spDongiaBVAT.item(i).value = dongiabvat; 
			if(spMa.item(i).value != "" && dongia.item(i).value != "" && soluong.item(i).value != "" )
			{
				var _ck = chietkhau.item(i).value.replace(/,/g,"");
				if(_ck == '')
					_ck = '0';
				
				var _thueVAT = thueVAT.item(i).value.replace(/,/g,"");
				if(_thueVAT == '')
					_thueVAT = '0';	
												
				var tt = parseFloat(dongiabvat.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")) ;
								
				thanhtien.item(i).value = DinhDangTien( parseFloat(tt));
				tongthanhtien += parseFloat(parseFloat(tt));
				
				//tongtienCK += tt * _ck / 100;
			}
			if(spScheme.item(i).value != "" )
			{
				var _ck = thanhtien.item(i).value.replace(/,/g,"");
				if(_ck == '')
					_ck = '0';
				
				tongtienCKKM += -1*parseFloat(_ck);
			}
		}
				
		var dhCK_diengiai = document.getElementsByName("dhCK_diengiai");
		var dhCK_giatri = document.getElementsByName("dhCK_giatri");
		var dhCK_loai = document.getElementsByName("dhCK_loai");
		var dhCK_chietkhau = document.getElementsByName("dhCK_chietkhau");
		
		var tongDhCK = 0;
		var tongtien_sau_hoahong = 0;
		var tienCK=0;
		 for(var j = 0; j < dhCK_giatri.length; j++ )
		{
			if(dhCK_giatri.item(j).value != '' )
			{
				var loai = dhCK_loai.item(j).value;
				if(loai == '0') //tien
				{
					tienCK=dhCK_giatri.item(j).value.replace(/,/g,"");
					tongDhCK += parseFloat(tienCK);
				}
				else  //CHIET KHAU
				{
					if( j == 0)
					{
						var tt_hoahong = parseFloat(dhCK_giatri.item(j).value.replace(/,/g,"")) * parseFloat(tongtien) / 100;
						tongtien_sau_hoahong = tongtien - tt_hoahong;
						tienCK=tt_hoahong;
						tongDhCK += parseFloat(tt_hoahong);
					}
					else
					{
						tienCK=parseFloat(dhCK_giatri.item(j).value.replace(/,/g,"")) * parseFloat(tongtien_sau_hoahong) / 100;
						tongDhCK += tienCK;						
					}
				}
				dhCK_chietkhau.item(j).value=tienCK;
			}
		}
		document.getElementById("txtTiengiam").value = DinhDangTien(tongDhCK);
		
		var tongtienSAUCK = tongthanhtien - tongtienCKKM;
		//Tiền VAT tính theo công thức Thuế VAT (10%): tính trên tổng tiền sau CK
		tt_vat=tongtienSAUCK*0.1;
		
		document.getElementById("txtTongSauCK").value = DinhDangDonGia(tongtienSAUCK.toFixed(0));
		document.getElementById("txtVAT").value = DinhDangTien(tt_vat);
		var tiengiam = document.getElementById("txtTiengiam").value.replace(/,/g,"");
		
		var tongtienSAUVAT = parseFloat(tongtienSAUCK) + tt_vat;
		document.getElementById("txtSauVAT").value = DinhDangDonGia(tongtienSAUVAT.toFixed(0));
		
		//var congno = document.getElementById("txtCongNo").value;
		var tongtienThanhToan = tongtienSAUVAT - parseFloat(tiengiam);
		document.getElementById("txtThanhToan").value = DinhDangDonGia(parseFloat(tongtienThanhToan.toFixed(0)));
	 }
	
	 function CapNhatGia(e, pos)
	 { 
		 var nppId= document.getElementById("nppId").value;
		var kbhId= document.getElementById("kbhId").value;
		var dvkdId = document.getElementById("dvkdId").value;
		var dvdlId = document.getElementsByName("donvi");
		var spId = document.getElementsByName("spMa");
		var spMa = document.getElementsByName("spMa");
		var dongia=document.getElementsByName("dongia");
		var giagoc=document.getElementsByName("spGiagoc");
		var ngaychuyen = document.getElementById("ngaychuyen").value;
		var spQuyDoi=document.getElementsByName("spQuyDoi");
		var spTrongLuong = document.getElementsByName("spTrongLuong") ;
		var spTheTich = document.getElementsByName("spTheTich") ;
		
		 $( dongia.item(pos) ).val( "" );
		 $( giagoc.item(pos) ).val( "" );
		 $( spQuyDoi.item(pos) ).val( "" );
		 $( spTrongLuong.item(pos)).val( "" );
		 $( spTheTich.item(pos)).val( "" );
		 
		$.ajax
		(
			{
		    url: "../../ErpDondathangSvl?type=GetDonGia&spMa=" + spMa.item(pos).value + "&dvdlId=" + dvdlId.item(pos).value + "&nppId=" + nppId+"&kbhId="+kbhId+"&dvkdId="+dvkdId+""  ,
		    type : 'GET',
		    dataType: 'json',
		    success: function( data )
		    {
		        var npp = data ;
		        console.log(data);
		       $( dongia.item(pos)        ).val( npp.dongia );
		       $( giagoc.item(pos)        ).val( npp.giagoc );
		       $( spQuyDoi.item(pos)         ).val( npp.quycach );
		       $( spTheTich.item(pos)         ).val( npp.thetich );
		       $( spTrongLuong.item(pos)         ).val( npp.trongluong );
		     }
		});
		 
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

<form name="hctmhForm" method="post" action="../../ErpDondathangUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="loaidonhang" value='<%= lsxBean.getLoaidonhang() %>'>
<input type="hidden" name="tungaytk" value='<%= lsxBeanList.getTungay() %>'>
<input type="hidden" name="denngaytk" value='<%= lsxBeanList.getDenngay() %>'>
<input type="hidden" name="vungtk" value='<%= lsxBeanList.getVungId() %>'>
<input type="hidden" name="khuvuctk" value='<%= lsxBeanList.getKhuvucId() %>'>
<input type="hidden" name="kbhtk" value='<%= lsxBeanList.getKbhId() %>'>
<input type="hidden" name="npptk" value='<%= lsxBeanList.getNppTen() %>'>
<input type="hidden" name="trangthaitk" value='<%= lsxBeanList.getTrangthai()%>'>
<input type="hidden" name="id" value='<%= lsxBean.getId()%>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý kho trung tâm > Đơn đặt hàng > Hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<%-- <A href= "../../ErpDondathangSvl?userId=<%= userId %>&loaidonhang=<%= lsxBean.getLoaidonhang() %>" > --%>
   		<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
        	
	        	<A href="../../ErpDondathangExcelSvl?userId=<%=userId%>&excelDetails=<%=lsxBean.getId() %>">
																<img src="../images/excel.gif" alt="In đơn hàng chi tiết Pdf" longdesc="In file Pdf" border="1px" height=30 width=30  style="border-style:outset">
																</A>    	        	
	        	<A href="../../ErpDondathangPdf?userId=<%=userId%>&pdf=<%=lsxBean.getId()%>" ><img src="../images/Printer30.png" alt="In" title="In chung tu" longdesc="In chung tu" border=1 style="border-style:outset"></A>
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
                    <TD width="250px"  class="plainlabel" valign="top" >
                    	<input type="text" readonly="readonly" value="<%= lsxBean.getId() %>" />
                    </TD>
                    <TD class="plainlabel">PO khuyến mãi </TD>
					<TD class="plainlabel">
						<%
						if (lsxBean.getPOKhuyenMai().trim().equals("1")) {
						%> <input name="pokhuyenmai" id="pokhuyenmai" type="checkbox" value="1" onchange="submitform();" checked> <%
						} else {
						%> <input name="pokhuyenmai" id="pokhuyenmai" type="checkbox" value="1" onchange="submitform();">
						<% } %>
					</TD>
            	</tr>											
                <TR>
                    <TD width="130px" class="plainlabel" valign="top">Ngày đơn hàng </TD>
                    <TD width="300px"  class="plainlabel" valign="top" >
                    	<input type="text" class="days" readonly="readonly"   id="ngaychuyen" name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/>
                    </TD>
                    
                    <TD width="120px" class="plainlabel" valign="top">Ngày đề nghị giao </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" class="days" readonly="readonly"   id="ngaydenghi"  name="ngaydenghi" value="<%= lsxBean.getNgaydenghi() %>"/>
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" valign="top">Đơn vị kinh doanh </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<select name = "dvkdId"   id="dvkdId"  onchange="submitform();" class="select2" style="width: 200px" >
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
                    	<select name = "kbhId"   id="kbhId"  class="select2" style="width: 200px" onchange="submitform();" >
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
                        		 <% } } kbhRs.close();} catch(SQLException ex){ex.printStackTrace();}
                        		}
                        	%>
                    	</select>
                    </TD>         	
                </TR>
                
                <TR>
                	<TD class="plainlabel" valign="top">Nhà phân phối </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<select name = "nppId"    id="nppId"  class="select2" style="width: 200px" onchange="submitform();" >
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
                    	<select name = "khonhapId" class="select2" style="width: 200px" >
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
                    	</select>
                    </TD>         	
                </TR>
                
                <TR style="display:none">
                    <TD class="plainlabel" valign="top">Tiền trước thuế </TD>
                    <TD class="plainlabel" valign="top" colspan="3" >
                    	<input type="text" readonly="readonly"  id="txtBVAT" value="" style="text-align: right;"  />
                    	<input type="hidden"  value="<%= lsxBean.getChietkhau() %>" id="txtPTChietKhau" style="text-align: right;" name="ptChietkhau" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" valign="top" style="display:none">Tổng tiền chiết khấu </TD>
                    <TD class="plainlabel" valign="top" style="display:none">
                    	<input type="text" readonly="readonly"  value="" id="txtTongCK" style="text-align: right; width: 170px; " />
                    	
                    </TD>
                    	
                    <TD class="plainlabel" valign="top">Tổng tiền trước VAT </TD>
                    <TD class="plainlabel" valign="top" colspan = 3>
                    	<input type="text" readonly="readonly"  value="" id="txtTongSauCK" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" valign="top">Tiền VAT (10%) </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text" value="<%= lsxBean.getVat() %>" id="txtVAT" style="text-align: right;" name="ptVat" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);"  <%= lsxBean.getKbhId().equals("100025") ? "readonly='readonly' " : ""  %>  />
                    </TD>
                    	
                    <TD class="plainlabel" valign="top" >Tổng tiền sau VAT </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly"  value="" id="txtSauVAT" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Tiền giảm </TD>
                    <TD class="plainlabel" colspan =3 >
                    	<input type="text"  name="txtTiengiam" id="txtTiengiam" value='<%= lsxBean.getTiengiam() %>' onkeyup="Dinhdang(this);" style="text-align: right;" />
                    	
                    	<a href="" id="popupCHIETKHAU" rel="subcontentCK">
		           	 				&nbsp; <img alt="Khai báo chiết khấu" src="../images/vitriluu.png" title="Khai báo chiết khấu" ></a>
		           	 	
			           	 	<DIV id="subcontentCK" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					                             background-color: white; width: 500px; max-height:300px; overflow:auto; padding: 4px;">
				                    <table width="95%" align="center">
				                        <tr>
				                            <th width="250px" style="font-size: 12px; text-align: center;">Diễn giải</th>
				                            <th width="80px" style="font-size: 12px; text-align: center;">Giá trị</th>
				                            <th width="80px" style="font-size: 12px; text-align: center;">Đơn vị</th>
				                        </tr>
				                        <%
				                        	int sodong = 0;
				                        	if(dhCk_diengiai != null)
				                        	{
				                        		for(int i = 0; i < dhCk_diengiai.length; i++)
				                        		{ 
				                        		 	sodong ++;	%>
				                        	<tr>
				                        		<td><input name="dhCK_diengiai" type="text" style="width: 100%;" value="<%= dhCk_diengiai[i]  %>" > </td>
				                        		<%if(lsxBean.getKbhId().equals("100052")) {%>
				                        		<td><input name="dhCK_giatri" type="text" style="width: 100%;" value="0" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
				                        		<%}else{ %>
				                        		<td><input name="dhCK_giatri" type="text" style="width: 100%;" value="<%= dhCk_giatri[i]  %>" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
				                        		<%} %>				                        		
				                        		<td>
				                        			<select name="dhCK_loai" style="width: 100%" >
					                        			<% if( dhCk_loai[i].equals("0")  ) { %>
					                        				<option value="0" selected="selected" >Tiền</option>
					                        				<option value="1" >%</option>
					                        			<% } else { %>
					                        				<option value="0" >Tiền</option>
					                        				<option value="1" selected="selected" >%</option>
					                        			<% } %>
				                        			</select>
				                        		</td>
				                        		<td style="display:none"><input name="dhCK_chietkhau" type="text" style="width: 100%;" value="<%= dhCk_giatri[i]  %>" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
				                        	</tr>
				                      <% } } %>
				                      
				                      <% for(int i = sodong; i < 4; i++ ) { %>
				                      		
				                      		<tr>
				                        		<td><input name="dhCK_diengiai" type="text" style="width: 100%;" value="" <%= ( i == 0 ? " readonly='readonly' " : "" ) %> > </td>
				                        		<td><input name="dhCK_giatri" type="text" style="width: 100%;" value="" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
				                        		<td>
				                        			<select name="dhCK_loai" style="width: 100%" >
				                        				<option value="0" >Tiền</option>
				                        				<option value="1" >%</option>
				                        			</select>
				                        		</td>
				                        		<td style="display:none"><input name="dhCK_chietkhau" type="text" style="width: 100%;" value="" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
				                        	</tr>
				                      		
				                      <% } %>
				                      
				                   </table>
				                   
				                   <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentCK')" ><b> Đóng lại </b></a>
				                   </div>
				                   
					       </DIV>
                    	
	                    	<script type="text/javascript">
				            	dropdowncontent.init('popupCHIETKHAU', "right-top", 300, "click");
				            </script>
                    	
                    </TD>
                </TR>
                  
                <TR>
                	<TD class="plainlabel" >Công nợ </TD>
                    <TD class="plainlabel"  >
                    	<input type="text"  name="txtCongNo" id="txtCongNo" value='<%= lsxBean.getCongNo() %>' onkeyup="Dinhdang(this);" style="text-align: right;" />
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
               
            </TABLE>
            <% if(!lsxBean.getPOKhuyenMai().equals("1")) { %>
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
						<th align="center" width="10%" style="display:none">Tồn kho</th>
						<th align="center" width="7%" >Số lượng</th>
						<th align="center" width="7%" ><font  color="red">Số lượng TT duyệt</font></th>
						<th align="center" width="10%"  style="display:none">Đơn giá gốc</th>
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
										<input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%" readonly="readonly">
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
										 
									</td>
									
									<td style = "display:none"> <input type="text" name="spTonkho" value="<%= spTonkho[i] %>" style="width: 100%" readonly="readonly"> </td>
									
									<td> <input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width:96%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" readonly="readonly" > </td>
									<td style="display:none"> <input type="text" name="spvat" value="<%= spVat[i] %>" style="width: 100%; text-align: right;" readonly="readonly"  > </td>
									<td> <input type="text" name="soluongtt" value="<%= spSoluongtt[i] %>" style="width:96%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
									
									<td> <input type="text" name="spDongiaBVAT" value="0" style="width: 100%; text-align: right;" readonly="readonly" > </td>
									<td style="display:none"> 
									<% if(spSCheme[i].trim().length() <= 0) { %> 
										<input type="text" name="spGiagoc" value="<%= spGiagoc[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > 
									<% } else {  %>
										<input type="text" name="spGiagoc" value="0" style="width: 100%; text-align: right;" readonly="readonly" > 
									<% } %>
									</td>
									<td style="display:none"> <input type="text" name="dongia" value="<%= spGianhap[i] %>" style="width: 97%; text-align: right;" readonly="readonly" > </td>
									
									
									<td style="display:none"> <input type="text" name="chietkhau" value="<%= spChietkhau[i] %>" style="width: 96%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
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
									</td>
									
									<td style = "display:none"> <input type="text" name="spTonkho" value="" style="width: 100%" readonly="readonly"> </td>
									
									<td> <input type="text" name="soluong" value="<%=spSoluong[i] %>" style="width: 97%; color: red; text-align: right;" readonly="readonly" > </td>
									<td> <input type="text" name="soluongtt" value="<%=spSoluongtt[i] %>" style="width: 97%; color: red; text-align: right;"  readonly="readonly" > </td>
									<td style="display:none"> <input type="text" name="spvat" value="<%= spVat[i] %>" style="width: 100%; text-align: right;" readonly="readonly"  > </td>
									<td style="display:none"> <input type="text" name="spGiagoc" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
									
									<td> <input type="text" name="spDongiaBVAT" value="0" style="width: 100%; text-align: right;" readonly="readonly" > </td>
									
									<td style="display:none"> <input type="text" name="dongia" value="" style="width: 96%; text-align: right; color: red;" readonly="readonly" > </td>
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
						 <input type="text" name="donvi" value="" style="width: 100%" readonly="readonly">
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
								 <input type="hidden" name="spTrakmId" value="" style="width: 96%; color: red;" readonly="readonly">
						 </td>
						 
						 <td style = "display:none"> <input type="text" name="spTonkho" value="" style="width: 100%" readonly="readonly"> </td>
						 
						<td> <input type="text" name="soluong" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);"  readonly="readonly" > </td>
						<td> <input type="text" name="soluongtt" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);"  > </td>
						<td style="display:none"> <input type="text" name="spGiagoc" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
						
						<td> <input type="text" name="spDongiaBVAT" value="0" style="width: 100%; text-align: right;" readonly="readonly" > </td>
						
						<td style="display:none"> <input type="text" name="dongia" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
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
				</table>
			</div>
            </div>
     		<% } else { %>
     		<ul class="tabs">
         		<li><a href="#">Thông tin đơn hàng</a></li>
          	</ul>
             			
            <div class="panes">
              										
			<div>
			
					<table cellpadding="0px" cellspacing="1px" width="100%">
					<tr class="tbheader">
						<th align="center" width="3%" >STT</th>
						<th align="center" width="10%" >Mã sản phẩm</th>
						<th align="center" width="20%" >Tên sản phẩm</th>
						<th align="center" width="5%" >Đơn vị</th>
						<th align="center" width="10%" style="display:none">Tồn kho</th>
						<th align="center" width="10%" style="display:none">Số lượng(npp)</th>
						<th align="center" width="10%" >Số lượng</th>
						<th align="center" width="10%"  style="display:none">Đơn giá gốc</th>
						<th align="center" width="10%" >Đơn giá sau CK</th>
						<!-- <th align="center" width="8%" >Chiết khấu</th> -->
						<!-- <th align="center" width="10%" >% VAT</th> -->
						<th align="center" width="10%" >Thành tiền</th>
						<th align="center" width="10%" >CTKM</th>
						<th align="center" width="10%" >Ngày có hàng</th>
						<th align="center" width="10%" >Ghi chú</th>
					</tr>
					<%
						int count = 0;
						if(spMa != null)
						{
							for(int i = 0; i < spMa.length; i++)
							{ %>
							
							<tr onMouseover="ddrivetip('<%=spGhichu[i] %>', 250)"; onMouseout='hideddrivetip()'>
								
								<td style="text-align: center;" > <%= i + 1 %> </td>
								<td>
									<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 96%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
								</td>
								<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 99%" readonly="readonly"> </td>
								<td>
									<%-- <input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%" readonly="readonly"> --%>
									<select name="donvi" style="width: 100%;" onchange="CapNhatGia(this, <%= count %>);" >
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
								</td>
								
								<td style = "display:none"> <input type="text" name="spTonkho" value="<%= spTonkho[i] %>" style="width: 100%" readonly="readonly"> </td>
								
								<td style="display:none"> <input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width:96%; text-align: right;" readonly="readonly" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
								<td style="display:none"> <input type="text" name="spvat" value="<%= spVat[i] %>" style="width: 100%; text-align: right;" readonly="readonly"  > </td>
								<td> <input type="text" name="soluongtt" value="<%= spSoluongtt[i] %>" style="width:96%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
								
								<td style="display:none">
									<input type="text" name="spGiagoc" value="0" style="width: 100%; text-align: right;" readonly="readonly" >
								</td>
								
								<td> <input type="text" name="spDongiaBVAT" value="0" style="width: 100%; text-align: right;" readonly="readonly" > </td>
								
								<td style="display:none"> <input type="text" name="dongia" value="<%= spGianhap[i] %>" style="width: 97%; text-align: right;" readonly="readonly" > </td>
								
								<td style="display:none"> <input type="text" name="chietkhau" value="<%= spChietkhau[i] %>" style="width: 96%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
								<td> <input type="text" name="thanhtien" value="" style="width: 96%; text-align: right;" readonly="readonly" > </td>
								<td > 
									<select name="scheme" style="width: 100%;"  >
										<option value="" ></option>
										<% if(schemespecialRs != null) 
										{ 
											schemespecialRs.beforeFirst();
											while(schemespecialRs.next())
											{
												if(spSCheme[i].equals(schemespecialRs.getString("PK_SEQ")))
												{ %>
													<option value="<%= schemespecialRs.getString("PK_SEQ") %>" selected="selected" ><%= schemespecialRs.getString("SCHEME") %></option>
												<% } else { %>
													<option value="<%= schemespecialRs.getString("PK_SEQ") %>" ><%= schemespecialRs.getString("SCHEME") %></option>
											   <% } }
										} %>
								 	</select>
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
								<select name="donvi" style="width: 100%" onchange="CapNhatGia(this, <%= i %>);" >
									<option value="" ></option>
										<% if(dvtRs != null) 
										{ 
											dvtRs.beforeFirst();
											while(dvtRs.next())
											{ %>
												<option value="<%= dvtRs.getString("DONVI") %>"  ><%= dvtRs.getString("DONVI") %></option>
										   <% } 
										} %>
									 </select> 
							 </td>
							 <td style = "display:none"> <input type="text" name="spTonkho" value="" style="width: 100%" readonly="readonly"> </td>
							<td style="display:none"> <input type="text" name="soluong" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" readonly="readonly"> </td>
							<td> <input type="text" name="soluongtt" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
							<td style="display:none"> <input type="text" name="spGiagoc" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							
							<td> <input type="text" name="spDongiaBVAT" value="0" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							
							<td style="display:none"> <input type="text" name="dongia" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							<td style="display:none"> <input type="text" name="spvat" value="" style="width: 100%; text-align: right;" readonly="readonly"  > </td>
							<td style="display:none"> <input type="text" name="chietkhau" value="" style="width: 96%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
							<td> <input type="text" name="thanhtien" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							<td > 
								<select name="scheme" style="width: 100%;"  >
									<option value="" ></option>
									<% if(schemespecialRs != null) 
									{ 
										schemespecialRs.beforeFirst();
										while(schemespecialRs.next())
										{ %>
											<option value="<%= schemespecialRs.getString("PK_SEQ") %>" ><%= schemespecialRs.getString("SCHEME") %></option>
								   		<% } 
									} %>
							 	</select>
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
				</table>
			</div>
            </div>
     		<% } %>
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
		kbhRs.close();
		nppRs.close();
		khonhapRs.close();
		dvtRs.close();
		congnoRs.close();
		lsxBean.DBclose(); 
	}
	catch(Exception er){ }

	} %>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
