<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.beans.donhangip.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.SimpleDateFormat" %>

<% 
	NumberFormat formatter = new DecimalFormat("#,###,###");
   NumberFormat formatter2 = new DecimalFormat("#,###,##0.000000"); 
	HttpSession s = request.getSession();
   if (s.isNew()){
	   s.invalidate();
	   System.out.println("New session");
   }else{
	   System.out.println("Old session");
   }   	  
%>
<% IDonhangIP dhBean = (IDonhangIP)s.getAttribute("dhBean");
%>
<% List<ISanphamIP> splist = (List<ISanphamIP>)dhBean.getSpList(); %>
<% ResultSet ddkd = (ResultSet)dhBean.getDdkdList(); %>
<% ResultSet tbh = (ResultSet)dhBean.getTbhList(); %>
<% ResultSet kh = (ResultSet)dhBean.getKhList();%>
<% ResultSet kho = (ResultSet)dhBean.getKhoList(); %>
<% String ngaykhoaso = (String)dhBean.getNgayKs(); %>
<% String userId = (String) s.getAttribute("userId"); %>
<% Hashtable<String, Integer> spThieuList = dhBean.getSpThieuList(); %>
<% Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); %>
<% Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); %>
<% List<ISanphamIP> scheme_sanpham = (List<ISanphamIP>)dhBean.getScheme_SpList(); %>
<% ResultSet gsbhs = (ResultSet)dhBean.getgsbhs(); %>
<% ResultSet nvgn = (ResultSet)dhBean.getnvgnRs(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="geso.dms.distributor.util.Utility"%><HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
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
	
}
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();})
</script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        });	
		
    </script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax-dynamic-listTT.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script language="javascript" type="text/javascript">

function replaces()
{
	var khTen = document.getElementsByName("khTen"); 
	var smartId = document.getElementsByName("smartId");
	var ckId = document.getElementsByName("ChietKhau");
	var bgstId = document.getElementById("bgstId");
	
	for(i=0; i < smartId.length; i++)
	{
		var tem = smartId.item(0).value;
		if(tem == "")
		{
			khTen.item(0).value = "";
			document.getElementById("khId").value = "";
			ckId.item(0).value = "0";
			bgstId.value = "0";
			break;
		}	
		if(parseInt(tem.indexOf("-->")) > 0)
		{
			var tmp = tem.substring(0, parseInt(tem.indexOf("-->[")));
			document.getElementById("khId").value = tmp.substring(parseInt(tem.indexOf("-")+1, tmp.length));
			smartId.item(0).value = tmp.substring(0, parseInt(tem.indexOf("-")));
			tem = tem.substr(parseInt(tem.indexOf("-->[")) + 4);
			khTen.item(0).value = tem.substring(0, parseInt(tem.indexOf("][")));
			tem = tem.substr(parseInt(tem.indexOf("][")) + 2);
			ckId.item(0).value = tem.substring(0, parseInt(tem.indexOf("][")));
			
			tem = tem.substr(parseInt(tem.indexOf("][")) + 2);
			bgstId.value = tem.substring(0, parseInt(tem.indexOf("]")));
			
			if(khTen.item(0).value != "")
			{				 
				if(document.getElementById("gsbhId").value == "")
				{
					document.forms['dhForm'].action.value = 'submitKh';
			    	document.forms["dhForm"].submit();
				}
				else
				{
					document.forms['dhForm'].action.value = 'submit';
			    	document.forms["dhForm"].submit();
				}
			}
			
			break;
		}	
	}
	
	var masp = document.getElementsByName("masp");
	var tensp = document.getElementsByName("tensp1");
	var donvitinh = document.getElementsByName("donvitinh1");
	var dongia = document.getElementsByName("dongia1");
	var chietkhau = document.getElementsByName("spchietkhau1");
	var chietkhauDLN = document.getElementsByName("spchietkhauDLN");
	var chietkhauTT = document.getElementsByName("spchietkhauTT");
	var chietkhauDH = document.getElementsByName("spchietkhauDH");
	var soluong = document.getElementsByName("soluong");
	var tonkho = document.getElementsByName("tonkho1");
	var thanhtien = document.getElementsByName("thanhtien1");
	var ctkm = document.getElementsByName("ctkm1");
	var ckId = document.getElementById("ChietKhau");
	
	var spGiagoc = document.getElementsByName("spGiagoc1");
	var spBgId = document.getElementsByName("spBgId");
	var quydoi = document.getElementsByName("quydoi");
	
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
				
				dongia.item(i).value = DinhDangDonGia(sp.substring(0, parseInt(sp.indexOf("] ["))));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				tonkho.item(i).value = DinhDangTien(sp.substring(0, parseInt(sp.indexOf("] ["))));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				spGiagoc.item(i).value =DinhDangDonGia( sp.substring(0, parseInt(sp.indexOf("] ["))) ); 
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);

				spBgId.item(i).value = sp.substring(0, parseInt(sp.indexOf("] ["))); 
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);

				chietkhauDLN.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				
				quydoi.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				
				chietkhauTT.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
			}
			var so_luong = soluong.item(i).value;
			while(so_luong.match(","))
			{
				so_luong = so_luong.replace(",","");
			}
			if(parseInt(so_luong) > 0)
			{		
				var don_gia = dongia.item(i).value;
				while(don_gia.match(","))
				{
					don_gia = don_gia.replace(",","");
				}
				var gia_goc = spGiagoc.item(i).value;
				while(gia_goc.match(","))
				{
					gia_goc = gia_goc.replace(",","");
				}
				//don_gia = don_gia.replace(".", "");
				//tinh chiet khau theo san pham
				var tck = (parseFloat(ckId.value) * parseInt(so_luong) * parseFloat(don_gia)) / 100;
				chietkhau.item(i).value = DinhDangTien(roundNumber(tck, 2)); 
				var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia) - parseFloat(chietkhau.item(i).value);
				var ptChietkhau = parseFloat(chietkhauDLN.item(i).value) + parseFloat(chietkhauTT.item(i).value); 
				if(chietkhauDH.item(i).value != "")
				{
					ptChietkhau += parseFloat(chietkhauDH.item(i).value);
				}
				
				//don_gia =   Math.round( parseFloat(gia_goc) * (1-parseFloat(ptChietkhau)/100)  ); 
				don_gia =   parseFloat(gia_goc) * (1-parseFloat(ptChietkhau)/100);
				//dongia.item(i).value =DinhDangDonGia(don_gia);
				dongia.item(i).value = DinhDangDonGia(roundNumber(don_gia,6));
				var tt = parseInt(so_luong) * parseFloat(don_gia);
				thanhtien.item(i).value = DinhDangTien(roundNumber(tt, 0));
				TinhTien();
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
			//dongia.item(i).value = "";
			//chietkhau.item(i).value = "";
			//soluong.item(i).value = "";
			//thanhtien.item(i).value = "";
			tonkho.item(i).value = "";
			//spGiagoc.item(i).value = "";
			//ctkm.item(i).value = "";
		//	TinhTien();
		}
	}	
	
	setTimeout(replaces, 100);
	}
	
	function TinhTien()
	{
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("spGiagoc1");
		var thanhtien = document.getElementsByName("thanhtien1");
		//var chietkhau = document.getElementsByName("ChietKhau");
		var quydoi = document.getElementsByName("quydoi");
		var quydoi1 = document.getElementsByName("quydoi1");
		var tongtien = 0;
		var tienbck = 0;
		for(var i=0; i < thanhtien.length; i++)
		{
			var thanh_tien = thanhtien.item(i).value;
			var qd = quydoi1.item(i).value.replace(",", "");
			while(thanh_tien.match(","))
			{
				thanh_tien = thanh_tien.replace(",","");
			}
			if(thanh_tien != "")
			{
				//var thanh_tien = thanhtien.item(i).value.replace(".", "");
				tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
			}
			var so_luong = soluong.item(i).value;
			var don_gia = dongia.item(i).value;
			while(so_luong.match(","))
			{
				so_luong = so_luong.replace(",","");
			}
			while(don_gia.match(","))
			{
				don_gia = don_gia.replace(",","");
			}
			if(so_luong != "" && don_gia != "")
			{
				//var thanh_tien = thanhtien.item(i).value.replace(".", "");
				tienbck = parseFloat(tienbck) +  Math.round(parseFloat(so_luong)*parseFloat(don_gia));
				 quydoi.item(i).value = so_luong*qd;
			}
		}
		var tienchuaVAT = tongtien;
	//	console.log(tongtien);
		document.getElementById("SoTienChuaVAT").value = DinhDangTien(tienbck);
	
	 
		var ck = document.getElementById("ChietKhau").value;
		if(ck == "")
			ck = "0";
		var tienchietkhau = tienbck - tienchuaVAT + (tienchuaVAT * parseFloat(ck)) / 100;
	
		document.getElementById("TienChietKhau").value = DinhDangTien(tienchietkhau);
	
		var tiensauCK = Math.round(tienbck - tienchietkhau);
		
		document.getElementById("SoTienSauCK").value = DinhDangTien(parseFloat(tiensauCK));
	
		var vat = document.getElementById("VAT").value;
		if(vat == "")
			vat = "0";
		
		
		var tongtiencoVAT = (parseFloat(vat) * tiensauCK) / 100 + tiensauCK;
		document.getElementById("SoTienCoVAT").value = DinhDangTien(Math.round(tongtiencoVAT));
	}
	
	function Tongtienkhuyenmai()
	{
		var ttTrakm = document.getElementsByName("ttTrakm");
		var sum = 0;
		if(ttTrakm.length > 0)
		{
			for(h =0; h < ttTrakm.length; h++)
			{
				var thanh_tien = ttTrakm.item(h).value;
				while(thanh_tien.match(","))
				{
					thanh_tien = thanh_tien.replace(",","");
				}
				if(thanh_tien!= "")
					sum = parseFloat(sum) + parseFloat(thanh_tien);
			}
		}
		return sum;
	}
	
	function TongchietkhauKM()
	{
		var ckTrakm = document.getElementsByName("ckTrakm");
		var sum = 0;
		if(ckTrakm.length > 0)
		{
			for(h =0; h < ckTrakm.length; h++)
			{
				var thanh_tien = ckTrakm.item(h).value;
				while(thanh_tien.match(","))
				{
					thanh_tien = thanh_tien.replace(",","");
				}
				if(thanh_tien != "")
					sum = parseFloat(sum) + parseFloat(thanh_tien);
			}
		}
		return sum;
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
	function DinhDangDonGia(num) 
	 {
	  num = num.toString().replace(/\$|\,/g,'');
	   
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
	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
	
	function keypress(e) //H??m d??ng d? ngan ngu?i d??ng nh?p c??c k?? t? kh??c k?? t? s? v??o TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Ph??m Delete v?? Ph??m Back
				return;
			}
			return false;
		}
	}
	
	function keypress2(e)
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
			if (keypressed == 13)
			{
				var khTen = document.getElementsByName("khTen"); 
				var smartId = document.getElementsByName("smartId");
				var ckId = document.getElementsByName("ChietKhau");
				var bgstId = document.getElementById("bgstId");
				
				for(i=0; i < smartId.length; i++)
				{
					var tem = smartId.item(0).value;
					if(parseInt(tem.indexOf("-->[")) > 0)
					{
						var tmp = tem.substring(0, parseInt(tem.indexOf("-->[")));
						document.getElementById("khId").value = tmp.substring(parseInt(tem.indexOf("-")+1, tmp.length));
						smartId.item(0).value = tmp.substring(0, parseInt(tem.indexOf("-")));
						tem = tem.substr(parseInt(tem.indexOf("-->[")) + 4);
						khTen.item(0).value = tem.substring(0, parseInt(tem.indexOf("][")));
						tem = tem.substr(parseInt(tem.indexOf("][")) + 2);
						ckId.item(0).value = tem.substring(0, parseInt(tem.indexOf("][")));
						
						tem = tem.substr(parseInt(tem.indexOf("][")) + 2);
						bgstId.value = tem.substring(0, parseInt(tem.indexOf("]")));
					}
				}
				
				document.forms['dhForm'].action.value='submitKh';
				document.forms["dhForm"].submit();
			}
	}

	
	function CheckSoluongOld()
	{
		 var trangthaidh = document.getElementById("trangthaiDh").value;
		 if(trangthaidh == 3)
		 {
			 var soluong = document.getElementsByName("soluong");
			 var soluongOld = document.getElementsByName("soluongOld");
			 
			 for(i = 0; i < soluong.length ; i++)
			 {
				 var thanh_tien = soluong.item(i).value;
					while(thanh_tien.match(","))
					{
						thanh_tien = thanh_tien.replace(",","");
					}
				if(parseInt(thanh_tien) > parseInt(soluongOld.item(i).value))
				{
				    soluong.item(i).value = soluongOld.item(i).value;
				    alert("????n h??ng ???? xu???t kho, B???n kh??ng th??? nh???p s??? l?????ng l???n h??n, Vui l??ng nh???p l???i..");
					return;
				}
			 }			
		 }
	}
	
	function saveform()
	{	
		 
		 if(checkSanPham() == false)
		 {
			alert("L???i, b???n ph???i nh???p s???n ph???m cho ????n h??ng...");
			return;
		 } 
		 congDonSPCungMa();
		
		 var masp = document.getElementsByName("masp");
		 var tensp = document.getElementsByName("tensp");
		 var soluong = document.getElementsByName("soluong");
		 var ddkdId = document.getElementById("ddkdTen");
		 var khId = document.getElementById("khTen");
		
		 var gsbhId = document.getElementById("gsbhId");
		
		 
		 if(gsbhId.value == "")
		 {
			alert("Vui l??ng ki???m tra gi??m s??t b??n h??ng...");
			return;
		 }
 
 		if(ddkdId.value == "")
		{
			alert("vui l??ng ch???n Nh??n vi??n b??n h??ng...");
			return;
		}
		if(khId.value == "")
		{
			alert("Vui l??ng ch???n kh??ch h??ng...");
			return;
		}
		
		 
		 for(var k = 0; k < masp.length; k++)
		 {
			if(masp.item(k).value != "")
			{
				if(document.getElementById("trangthaiDh").value != 3)
				{
					if(soluong.item(k).value == "" || soluong.item(k).value  == "0" || tensp.item(k).value == "")			
					{
						alert("Ki???m tra l???i t??n v?? s??? l?????ng s???n ph???m, Ph???i nh???p t??n v?? s??? l?????ng cho s???n ph???m ???????c ch???n");
						return;
					}
				}
			}
		 }

		 var ngaykhoaso = document.getElementById("ngaykhoaso").value;
		 var tungay = document.getElementById("tungay").value;
		 if(ngaykhoaso.length >0 && tungay.length >0 ){
		 var ngay1 =Date.parse(ngaykhoaso);
		 var ngay2 = Date.parse(tungay);
		 //	if(ngay1 >=  ngay2)
		//	{
		///		alert('L???i...B???n ???? kh??a s??? ?????n ng??y ' + ngaykhoaso  + ' r???i...');
		//		return;
		//	}
		 }
 
		 var SoTienCoVAT = document.getElementById("SoTienCoVAT").value;
		 var muctindung = document.getElementById("muctindung").value;
		 if(parseFloat(SoTienCoVAT) > parseFloat(muctindung) && parseFloat(muctindung) > 0 )
	     {
		    if(!confirm('Ban da vuot muc tin dung roi, ban co muon tiep tuc mua hang khong?'))
		  	return;
		 }
		 
		 if(CheckSoluongOld() == true)
		 {
			 return;
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

	 	 document.forms['dhForm'].action.value='save';
	     document.forms['dhForm'].submit();			 
	 }

	 function checkSanPham()
	 {
		 var masp2 = document.getElementsByName("masp");
		 for(var hh = 0; hh < masp2.length; hh++)
		 {
			if(masp2.item(hh).value != "")
			{
				return true;
			}
		 }
		 return false;
	 }
	 
	 function submitform()
	 { 
		 congDonSPCungMa();
		
		document.forms['dhForm'].action.value='submit';
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
					alert("Ki???m tra l???i t??n v?? s??? l?????ng s???n ph???m, Ph???i nh???p t??n v?? s??? l?????ng cho s???n ph???m ???????c ch???n");
					return;
				}
		 }
		 document.getElementById("btnChot").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

		 document.forms['dhForm'].action.value='chotdonhang';
		 document.forms['dhForm'].submit();
	 }
	 
	 function Apkhuyenmai()
	 {
		 var SoTienCoVAT = document.getElementById("SoTienCoVAT").value;
	 	 var muctindung = document.getElementById("muctindung").value;
	 
		 if(parseInt(SoTienCoVAT) > parseInt(muctindung) &&  parseInt(muctindung) > 0)
		 {
			 if(!confirm('Ban da vuot muc tin dung roi, ban co muon tiep tuc mua hang khong?'))
			 	return;
		 }
		 var ngaykhoaso = document.getElementById("ngaykhoaso").value;
		 var tungay = document.getElementById("tungay").value;
		// alert(ngaykhoaso +' '+ tungay);
		 if(ngaykhoaso.length >0 && tungay.length >0 ){
		 var ngay1 =Date.parse(ngaykhoaso);
		 var ngay2 = Date.parse(tungay);
		//	if(ngay1 >=  ngay2)
		//	{
		//		alert('L???i...B???n ???? kh??a s??? ?????n ng??y '+ngaykhoaso  +' r???i...');
		//		return;
		//	}
		 }
		 congDonSPCungMa();
		 var masp = document.getElementsByName("masp");
		 var tensp = document.getElementsByName("tensp");
		 var soluong = document.getElementsByName("soluong");
		 for(var k = 0; k < masp.length; k++)
		 {
			 if(masp.item(k).value != "")
				if(soluong.item(k).value == "" || tensp.item(k).value == "")
				{
					alert("Ki???m tra l???i t??n v?? s??? l?????ng s???n ph???m, Ph???i nh???p t??n v?? s??? l?????ng cho SP ???????c ch???n");
					return;
				}
		 }
		 
		 document.getElementById("btnApKhuyenMai").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		 
		 document.forms['dhForm'].action.value='apkhuyenmai';
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

		 var tensp1 = document.getElementsByName("tensp1");
		 var donvitinh1 = document.getElementsByName("donvitinh1");
		 var dongia1 = document.getElementsByName("dongia1");
		 var chietkhau1 = document.getElementsByName("spchietkhau1");
	 	 var thanhtien1 = document.getElementsByName("thanhtien1");
	 	 var tonkho1 = document.getElementsByName("tonkho1");
		 
		 var tensp = document.getElementsByName("tensp");
		 var donvitinh = document.getElementsByName("donvitinh");
		 var dongia = document.getElementsByName("dongia");
		 var chietkhau = document.getElementsByName("spchietkhau");
	 	 var thanhtien = document.getElementsByName("thanhtien");
	 	 var tonkho = document.getElementsByName("tonkho");
	 	 
	 	var spGiagoc = document.getElementsByName("spGiagoc");
	 	var spGiagoc1 = document.getElementsByName("spGiagoc1");
	 	 
	 	for(var pos = 0; pos < masp.length; pos++)
	 	{
	 		if(masp.item(pos).value != "")
	 		{
		 		tensp.item(pos).value = tensp1.item(pos).value;
		 		donvitinh.item(pos).value = donvitinh1.item(pos).value;
		 		dongia.item(pos).value = dongia1.item(pos).value;
		 		chietkhau.item(pos).value = chietkhau1.item(pos).value;
		 		thanhtien.item(pos).value = thanhtien1.item(pos).value;
		 		tonkho.item(pos).value = tonkho1.item(pos).value;
		 		
		 		spGiagoc.item(pos).value = spGiagoc1.item(pos).value;
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
			var tonkhoAdd = document.createElement("TD");
			var quydoiAdd = document.createElement("TD");
			var dongiaAdd = document.createElement("TD");
			var chietkhauAdd = document.createElement("TD");
			var thanhtienAdd = document.createElement("TD");
			var ctkmAdd = document.createElement("TD");
			var spGiagocAdd = document.createElement("TD");
			var masp = document.createElement("input");
			masp.setAttribute("type", "textbox");
			masp.setAttribute("onkeyup", "ajax_showOptions(this,'abc',event)");
			masp.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			masp.name = 'masp';
			maspAdd.appendChild(masp);
			
			var tensp = document.createElement("input");
			tensp.setAttribute("type", "hidden");
			tensp.setAttribute("readonly", "readonly");
			tensp.setAttribute("style"," width:100%;border:1px;	border-style:solid;border-color: #808080;");
			
			tensp.name = 'tensp';
			tenspAdd.appendChild(tensp);
			
			
			var tensp1 = document.createElement("input");
			tensp1.setAttribute("type", "textbox");
			tensp1.setAttribute("readonly", "readonly");
			tensp1.setAttribute("style"," width:100%;border:1px;	border-style:solid;border-color: #808080;");
			
			tensp1.name = 'tensp1';
			tenspAdd.appendChild(tensp1);
			
			var tonkho1 = document.createElement("input");
			tonkho1.setAttribute("type", "textbox");
			tonkho1.setAttribute("readonly", "readonly");
			tonkho1.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;");
			tonkho1.value = "";
			
			tonkho1.name = 'tonkho1';
			tonkhoAdd.appendChild(tonkho1);
			
			var tonkho = document.createElement("input");
			tonkho.setAttribute("type", "hidden");
			tonkho.setAttribute("readonly", "readonly");
			tonkho.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;");
			tonkho.value = "";
			
			tonkho.name = 'tonkho';
			tonkhoAdd.appendChild(tonkho);
			
			
		
			
			
			var soluong = document.createElement("input");
			soluong.setAttribute("type", "textbox");
			soluong.setAttribute("onkeypress","return keypress(event)");
			soluong.value = "";
			soluong.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;");

			soluong.name = "soluong";
			soluongAdd.appendChild(soluong);
			
			var soluongOld = document.createElement("input");
			soluongOld.setAttribute("type", "hidden");
			soluongOld.setAttribute("onkeypress","return keypress(event)");
			soluongOld.value = "";
			soluongOld.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;");

			soluongOld.name = "soluongOld";
			soluongAdd.appendChild(soluongOld);
			
			
			
			var dvt = document.createElement("input");
			dvt.setAttribute("type", "textbox");
			dvt.setAttribute("readonly", "readonly");
			dvt.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;");
			dvt.value = "";
			
			dvt.name = 'donvitinh';
			dvtinhAdd.appendChild(dvt);
			var dongia = document.createElement("input");
			dongia.setAttribute("type", "textbox");
			dongia.setAttribute("readonly", "readonly");
			
			var dvt1 = document.createElement("input");
			dvt1.setAttribute("type", "hidden");
			dvt1.setAttribute("readonly", "readonly");
			dvt1.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;");
			dvt1.value = "";
			
			dvt1.name = 'donvitinh1';
			dvtinhAdd.appendChild(dvt1);
			
			
			
			var dongia = document.createElement("input");
			dongia.setAttribute("type", "textbox");
			dongia.setAttribute("readonly", "readonly");
			
			
			var quydoi = document.createElement("input");
			quydoi.setAttribute("type", "textbox");
			quydoi.setAttribute("readonly", "readonly");
			quydoi.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;");
			quydoi.value = "";
			
			quydoi.name = 'quydoi';
			quydoiAdd.appendChild(quydoi);
			
			
			var quydoi1 = document.createElement("input");
			quydoi1.setAttribute("type", "hidden");
			quydoi1.setAttribute("readonly", "readonly");
			quydoi1.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;display:none;");
			quydoi1.value = "";
			
			quydoi.name = 'quydoi1';
			quydoiAdd.appendChild(quydoi1);
			
			
			
			var spGiagoc1 = document.createElement("input");
			spGiagoc1.setAttribute("type", "hidden");
			spGiagoc1.setAttribute("readonly", "readonly");
			spGiagoc1.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;display:none;");
			spGiagoc1.value = "";
			
			spGiagoc1.name = 'spGiagoc1';
			spGiagocAdd.appendChild(spGiagoc1);
			
			
			var dongia = document.createElement("input");
			dongia.setAttribute("type", "textbox");
			dongia.setAttribute("readonly", "readonly");
		
			dongia.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;display:none;");
			dongia.value = "";
			dongia.name = 'dongia';
			dongiaAdd.appendChild(dongia);
			
			var chietkhau = document.createElement("input");
			chietkhau.setAttribute("type", "textbox");
			chietkhau.setAttribute("readonly", "readonly");
			chietkhau.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;display:none;");
			chietkhau.value = "";
			chietkhau.name = 'spchietkhau';
			chietkhauAdd.appendChild(chietkhau);
			
			var thanhtien = document.createElement("input");
			thanhtien.setAttribute("type", "textbox");
			thanhtien.setAttribute("readonly", "readonly");
			thanhtien.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;display:none;");
			thanhtien.value = "";
			thanhtien.name = "thanhtien";
			thanhtienAdd.appendChild(thanhtien);
			
			var ctkm = document.createElement("input");
			ctkm.setAttribute("type", "textbox");
			ctkm.setAttribute("readonly", "readonly");
			ctkm.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;display:none;");
			ctkm.value = "";
			ctkm.name = 'ctkm';
			ctkmAdd.appendChild(ctkm);
			
			tr.appendChild(maspAdd);
			tr.appendChild(tenspAdd);
			tr.appendChild(tonkhoAdd);
			tr.appendChild(soluongAdd);
			tr.appendChild(dvtinhAdd);
			tr.appendChild(quydoiAdd);
			tr.appendChild(spGiagocAdd);
			tr.appendChild(dongiaAdd);
			tr.appendChild(chietkhauAdd);
			tr.appendChild(thanhtienAdd);
			tr.appendChild(ctkmAdd);
			
			tableName.appendChild(tr);
		}
		function ThemSanPham()
		{
			 var sl = window.prompt("Nh???p s??? l?????ng s???n ph???m mu???n th??m", '');
			 if(isNaN(sl) == false && sl < 30)
			 {
				 for(var i=0; i < sl ; i++)
					 addRow("san_pham");
			 }
			 else
			 {
				 alert('S??? l?????ng b???n nh???p kh??ng h???p l???. M???i l???n b???n ch??? ???????c th??m t???i ??a 30 s???n ph???m');
				 return;
			 }
		 }

		function ajaxOption(id, str)
		{
			var xmlhttp;
			if (str == "")
			{
			   document.getElementById(id).innerHTML = "";
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
			      document.getElementById(id).innerHTML = xmlhttp.responseText;
			   }
			}
			xmlhttp.open("POST","../../DonHangAjaxSvl?q=" + str + "&id=" + id,true);
			xmlhttp.send();
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
			element.value=DinhDangDonGia(element.value);
			if(element.value== '' )
			{
				element.value= '';
			}
		}
	function Focus()
	{
		var flag = document.getElementById('smartId');
		var masp=document.getElementsByName('masp');
		for(var i=0 ; i < masp.length;i++)
		{
			if(masp[i].value=='')
			{	
				masp[i].focus();
				return;
			}
		}
	}
	
	function DDTien()
	{
		var nocu = document.getElementById("NoCu").value.replace(/\$|\,/g,'');
		if(nocu == "") nocu = "0";
		
		if(parseFloat(nocu) > 0)
	    {
			document.getElementById("NoCu").value = DinhDangTien(Math.round(nocu));
	    }
		
	}
</script>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" onload="Focus()" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="dhForm" method="post" action="../../DonhangIPUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="nppId" value='<%= dhBean.getNppId() %>'>
<input type="hidden" name="ngaykhoaso" id = "ngaykhoaso" value='<%= ngaykhoaso %>'>
<input type="hidden" name="action" value='1'>
<INPUT type="hidden" name="id" value='<%= dhBean.getId() %>'>
<INPUT type="hidden" name="trangthai" id="trangthaiDh" value='<%= dhBean.getTrangthai() %>'>
<INPUT type="hidden" name="khoTen" value='<%= dhBean.getKhoId() %>'>
<input type="hidden" name="muctindung" id ="muctindung" value='<%= dhBean.getMuctindung() %>'>
<INPUT type="hidden" name="aplaikm" id="aplaikm" value='<%=dhBean.isAplaikhuyenmai() %>'>
<INPUT type="hidden" name="chuaapkm" id="chuaapkm" value='<%=dhBean.isChuaApkhuyenmai() %>'>
<INPUT type="hidden" name="cokm" id="cokm" value='<%=dhBean.isCokhuyenmai() %>'>
<input type="hidden" name="tck" id ="tck" value='<%=dhBean.getChietkhau() %>'>
<INPUT type="hidden" name="bgstId" id="bgstId" value='<%= dhBean.getBgstId() %>'>
<input type="hidden" id = "khId" name="khId" value='<%= dhBean.getKhId() %>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
				<TABLE border =0 width = "100%" cellpadding="2" cellspacing="0">
				<TBODY>
					<TR height="22">
						<TD align="left" >
							<TABLE width="100%" cellpadding="0" cellspacing="0">
								<TR>
									<TD align="left">
									   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										   <TR height="22">
			 								   <TD align="left"  class="tbnavigation">&nbsp;Qu???n l?? b??n h??ng > B??n h??ng cho kh??ch h??ng > C???p nh???t </TD>								   
			 								   <TD align="right" class="tbnavigation">Ch??o m???ng b???n  <%= dhBean.getNppTen() %> &nbsp; </TD>
					    				 </TR>
									  </TABLE>
								  </TD>
							  </TR>	
						  	</TABLE>
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
								<TR ><TD >
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class = "tbdarkrow">
											<TD width="30" align="left"><A href = "javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
										    <TD width="30" align="left" >
										    
										    <div id="btnSave">
										    	<A href="javascript:saveform()" ><img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A>
										    
										    	</div>
										   
										    </TD>
										    <TD width="2" align="left" >&nbsp;</TD>
								    		<TD width="30" align="left"><A href="../../InBangKepIPdfSvl?userId=<%=userId%>&display=<%=dhBean.getId()%>" ><img src="../images/Printer30.png" alt="In" title="In chung tu" longdesc="In chung tu" border=1 style="border-style:outset"></A></TD>
								    		<TD align="left" >
								  				<A href="../../InBangKepIPdfSvl?userId=<%=userId%>&excel=<%=dhBean.getId()%>" ><img src="../images/excel.gif" alt="Th??ng tin ????n h??ng" title="Th??ng tin ????n h??ng" longdesc="Th??ng tin ????n h??ng"  border="1px" height=30 width=30 ></A>
								    		</TD>
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="0" width="100%" cellpadding = "0" cellspacing = "0">
								<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
									<LEGEND class="legendtitle">B??o l???i nh???p li???u </LEGEND>			
				    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" readonly="readonly" rows="1"><%= dhBean.getMessage()  %></textarea>
										<% dhBean.setMessage(""); %>
									</FIELDSET>
							   </TD>
								</tr>
								<TR>
									<TD align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;????n h??ng b??n </LEGEND>
										<TABLE cellpadding = "3" cellspacing = "0" width = "100%" border = 0>
											<TR>
											  <TD width="170px" class="plainlabel">Ng??y giao d???ch </TD>
											  <TD class="plainlabel">
											 	  <input type="text"  class="days" size="11"
                                    					id="tungay" name="tungay" value="<%= dhBean.getNgaygiaodich() %>" maxlength="10" readonly />
											  </TD>
											  											  
											    <TD class="plainlabel">Gi??m s??t b??n h??ng </TD>
												<TD colspan="5" class="plainlabel"> 
									 			<SELECT name="gsbhId" id="gsbhId" onChange = "ajaxOption('ddkdTen', this.value)">
										 			 <option value=""></option>
													  <% if(gsbhs != null){
														  try{ while(gsbhs.next()){ 													 
											    			if(gsbhs.getString("pk_seq").equals(dhBean.getGsbhId())){ %>
											      				<option value='<%=gsbhs.getString("pk_seq")%>' selected><%=gsbhs.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=gsbhs.getString("pk_seq")%>'><%=gsbhs.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	 
                                    			</SELECT></TD>														
											</TR>
											                                    
											<TR class="tblightrow">
											<%-- 	<TD class="plainlabel">Kho h??ng </TD>
												<TD class="plainlabel"> 
									 			<SELECT name="khoTen" id="khoTen" onchange="submitform()">
													  <% if(kho != null){
														  try{while(kho.next()){
											    			if(kho.getString("khoId").equals(dhBean.getKhoId())){ session.setAttribute("khoId", kho.getString("khoId")); %>
											      				<option value='<%= kho.getString("khoId") %>' selected onMouseover="ddrivetip('<%= kho.getString("diengiai") %>', 300)"; onMouseout="hideddrivetip()"><%= kho.getString("Ten") + " " %></option>
											      			<%}else{ if(kho.getString("Ten").indexOf("PR") < 0){ %>
											     				<option value='<%= kho.getString("khoId") %>' onMouseover="ddrivetip('<%= kho.getString("diengiai") %>', 300)"; onMouseout="hideddrivetip()"><%= kho.getString("Ten") + " " %></option>
											     			<%}}}}catch(java.sql.SQLException e){} }%>
                                    			</SELECT></TD> --%>
                                    			                                    			
												<TD width="170px" class="plainlabel">Ng??y giao h??ng </TD>
												  <TD class="plainlabel">
												 	  <input type="text"  class="days" size="11"
	                                    					id="ngaygiao" name="ngaygiao" value="<%= dhBean.getNgaygiao() %>" maxlength="10" readonly />
												  </TD>
												<TD  class="plainlabel">Nh??n vi??n b??n h??ng </TD>
												<TD colspan="5" class="plainlabel"> 
									 			<SELECT name="ddkdTen" id="ddkdTen" onChange = "ajaxOption('smartId', this.value)">
										 			 <option value=""></option>
													  <% if(ddkd != null){
														  try{ while(ddkd.next()){ 													 
											    			if(ddkd.getString("ddkdId").equals(dhBean.getDdkdId())){ %>
											      				<option value='<%=ddkd.getString("ddkdId")%>' selected><%=ddkd.getString("ddkdTen") %></option>
											      			<%}else{ %>
											     				<option value='<%=ddkd.getString("ddkdId")%>'><%=ddkd.getString("ddkdTen") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	 
                                    			</SELECT>
                                    			</TD>	
                                    			</TR>
											<TR >
												<TD class="plainlabel">M?? kh??ch h??ng</TD>
												<TD class="plainlabel" width="250px">
                                                 	<input type="text" id="smartId" name="smartId" value="<%= dhBean.getSmartId() %>" onkeypress="keypress2(event);" />
                                                <TD class="plainlabel" width="190px">T??n kh??ch h??ng - ?????a ch???</TD>                                                   
                                                <TD class="plainlabel" colspan="5">
                                                	<input type="text" id="khTen" name="khTen" style="width: 100%" value="<%= dhBean.getKhTen() %>" readonly/>  
                                                </TD>                                                           
                                            </TR>   
                                            
											<TR class="tblightrow" style="display: none">
											    <TD class="plainlabel" >% Chi???t kh???u (kh??ch h??ng) </TD>
											    <TD class="plainlabel" >
											    	<input name="ChietKhau" id="ChietKhau" type="text" value="<%=dhBean.getChietkhau()  %>" onkeypress="return keypress(event);"></TD>
									            <TD class="plainlabel">T???ng chi???t kh???u</TD>
									            <TD class="plainlabel" colspan=3>
									            	<input name="TienChietKhau" id="TienChietKhau" type="text" disabled="disabled" value="<%= dhBean.getTongChietKhau() %>"> VND </TD>
										   	</TR>
											
											<TR class="tblightrow" style="display: none" >
											  <TD class="plainlabel">VAT (%) </TD>
											  <TD class="plainlabel" >
											  	<input name="VAT" id="VAT" type="text" value="<%= dhBean.getVAT() %>" onkeypress="return keypress(event);"> %</TD>
												
											  <TD class="plainlabel">T???ng s??? ti???n (sau VAT)</TD>
											  <TD class="plainlabel" colspan="3">
											  	<input name="SoTienCoVAT" id="SoTienCoVAT" type="text" readonly value="<%= dhBean.getTongtiensauVAT()%>"> VND </TD>
											   
											  <!-- 											  
											  <TD  class="plainlabel">T???ng s??? ti???n (tr?????c VAT) </TD>	     
										      <TD colspan="3"  class="plainlabel"><input name="SoTienChuaVAT" id="SoTienChuaVAT" type="text" value="<%=dhBean.getTongtientruocVAT()%>" readonly > VND </TD>
									       	   -->	
									       	</TR>											  											

										    <TR class="tblightrow" style="display: none">

											  <TD class="plainlabel">T???ng s??? ti???n (tr?????c chi???t kh???u)</TD>	     
										      <TD class="plainlabel"><input name="SoTienChuaVAT" id="SoTienChuaVAT" type="text" value="<%=dhBean.getTongtientruocVAT()%>" readonly > VND </TD>
											  
											  <TD  class="plainlabel">T???ng s??? ti???n (sau chi???t kh???u)</TD>
											  <TD colspan="3" class="plainlabel"><input name="SoTienSauCK" id="SoTienSauCK" type="text" readonly 
											  	  value="<%=dhBean.getTongtiensauCK()%>" > VND </TD>										  	
										  	</TR>

											<%-- <TR class="tblightrow">
											  <TD class="plainlabel">T???ng s??? ti???n KM</TD>
											  <TD class="plainlabel">
											  	<input name="SoTienCKKM" id="SoTienCKKM" type="text" readonly value="<%= dhBean.getTongtienCKKM() %>"> VND </TD>
											  <TD class="plainlabel"><Font color="red">T???ng s??? ti???n (sau KM)</Font></TD>
											  <TD class="plainlabel">
											  <%System.out.println("Tien sau KM:"+dhBean.getTongtiensauCKKM()); %>
											  	<input name="SoTienSauCKKM" id="SoTienSauCKKM" type="text" readonly value="<%= dhBean.getTongtiensauCKKM() %>"> VND </TD>
										  	</TR> --%>
										  	<TR class="tblightrow">
											  <TD class="plainlabel">?????a ch??? giao h??ng</TD>
											  <TD class="plainlabel" >
											  	<input name="diachigiao" id="diachigiao" type="text" value="<%= dhBean.getDiachigiaohang() %>"> </TD>
										  	  <TD class="plainlabel">C??ng n???</TD>
										  	  <TD class="plainlabel">
											  	<input name="NoCu" id="NoCu" type="text"  value="<%= formatter.format(dhBean.getNoCu()) %>" onkeyup="DDTien();"> VND </TD>
										  	
										  	</TR>
									<%-- 	  	<TR>
										  	<TD width="120px" class="plainlabel" valign="top">Nh??n vi??n giao nh???n</TD>
															<TD class="plainlabel" valign="top" colspan="1">
																  	<SELECT name="nvgnId" id="nvgnId" >
																  	<option value=''></option>
																	    <% try{ while(nvgn.next()){ 
																    			if(nvgn.getString("PK_SEQ").equals(dhBean.getnvgnId()   )){ %>
																      				<option value='<%=nvgn.getString("PK_SEQ")%>' selected>
																      				<%=nvgn.getString("Ten") %></option>
																      			<%}else{ %>
																     				<option value='<%=nvgn.getString("PK_SEQ")%>'>
																     				<%=nvgn.getString("Ten") %></option>
																     			<%}}}catch(Exception e){} %>
																	 </SELECT>
															</TD>
															<TD class="plainlabel" >S??? ti???n gi???m </TD>
							                    <TD class="plainlabel" colspan="3" >
							                    	<input type="text"  name="sotiengiam" maxlength="15" id="sotiengiam" onkeypress="return keypress(event);" onkeyup="Dinhdang(this)" value="<%= formatter.format(Float.parseFloat(dhBean.getSotiengiam()==null?"0":dhBean.getSotiengiam()))  %>" style="width: 200px;" /> VN??
							                    </TD>
															</TR> --%>
										  	<TR>
							                	<TD class="plainlabel" >Ghi ch?? </TD>
							                    <TD class="plainlabel" colspan="3" >
							                    	<input type="text"  name="ghichu" value="<%= dhBean.getGhiChu() %>" style="width: 700px;" />
							                    </TD>
										     </TR>
										  	
								
										</TABLE>
									</FIELDSET>
								  </TD>
							   </TR>	
							   <TR>
							   		<TD>
										<TABLE width = "100%" border="0" cellpadding="0" cellspacing="1">
										<tbody id="san_pham">
												<TR class="tbheader" >
													<TH width="10%" height="20">M?? s???n ph???m </TH>
													<TH width="38%">T??n s???n ph???m</TH>
													<TH width="15%">T???n hi???n t???i</TH>
													<TH width="15%">S??? l?????ng</TH>
													<TH width="7%">DVT</TH>
														<TH width="15%" >Quy ?????i (KG)</TH>
													<TH width="7%" style="display: none">????n gi?? g???c</TH>
													<TH width="7%" style="display: none">Chi???t kh???u d??? li???u n???n</TH>
													<TH width="7%" style="display: none">Chi???t kh???u trung t??m</TH>
													<TH width="7%" style="display: none" >Chi???t kh???u </TH> <!-- chiet khau don hang -->
													<TH width="10%" style="display: none" >????n gi?? sau CK </TH>
													<TH width="7%" style="display: none">Chi???t kh???u </TH>
													<TH width="9%" style="display: none">Th??nh ti???n </TH>
												<!-- 	<TH width="9%" >CTKM</TH> -->
												</TR>
									<% 
							if(splist != null){
							ISanphamIP sanpham = null;
							int size = splist.size();
							int m = 0;
							while (m < size){
								sanpham = splist.get(m);
								System.out.println("[sanpham]"+sanpham.getDongia());
								%>
									<TR class= 'tblightrow' >
									<TD align="left" >
										<input name="masp" type="text" value="<%=sanpham.getMasanpham()%>" onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" AUTOCOMPLETE="off">
									</TD>
									<TD align="left" >
										<input name="tensp1" type="text" disabled="disabled" value="<%=sanpham.getTensanpham()%>" style="width:100%; color: black; cursor:pointer;" >			        	
								        <input name="tensp" type="hidden" value="<%=sanpham.getTensanpham()%>" style="width:100%" >
									</TD>
									<TD align = "center" >
									<%
										System.out.println(" ton hien tai  "+formatter.format(Math.round(Double.parseDouble(sanpham.getTonhientai()))));
									%>
								    	<input name="tonkho1" disabled="disabled" type="text" value="<%= formatter.format(Math.round(Double.parseDouble(sanpham.getTonhientai()))) %>"  style="width:100%;text-align:center; color: black;">
								    	<input name="tonkho" type="hidden" value="<%= sanpham.getTonhientai() %>"  style="width:100%;text-align:center">
								    </TD>									
						        	<% if (spThieuList.containsKey(sanpham.getMasanpham())){ %>

									    <TD style="display: none" align = "center" ><div class="addspeech" title="San pham nay con toi da <%= spThieuList.get(sanpham.getMasanpham()) %> san pham, vui long chon lai so luong">
								        <input name="soluong" type="text" value="<%= formatter.format(Math.round(Double.parseDouble(sanpham.getSoluong()))) %>" onkeyup="Dinhdang(this)" style="width:100%; color:red ; cursor:pointer; background-color:#0FF; text-align:right">
								        </div>
								        
								        <input name="soluongOld" type="hidden" value="<%= sanpham.getSoluong() %>" >
								        </TD>
								         
								    <%}else{ %>
								    		<%
												System.out.println("SO LUONG  "+formatter.format(Math.round(Double.parseDouble(sanpham.getSoluong()))));
											%>
							        	<TD align = "center" >
								        <input name="soluong" type="text" value="<%=formatter.format(Math.round(Double.parseDouble(sanpham.getSoluong()))) %>" style="width:100%;text-align:right" onkeyup="Dinhdang(this)">
								        <input name="soluongOld" type="hidden" value="<%= sanpham.getSoluong() %>" >
								        </TD>
								    <%} %>
								    <TD align = "center" >
								    	<input name="donvitinh1" disabled="disabled" type="text" value="<%= sanpham.getDonvitinh() %>"  style="width:100%;text-align:center; color: black;">
								    	<input name="donvitinh" type="hidden" value="<%= sanpham.getDonvitinh() %>"  style="width:100%;text-align:center">
								    </TD>
								    
								      <TD align = "center" >
								    	<input name="quydoi" readonly="readonly" type="text" value="<%= sanpham.getQuydoi() %>"  style="width:100%;text-align:center; color: black;">
								    	<input name="quydoi1" readonly="readonly" type="hidden" value="<%= sanpham.getQuydoi() %>"  style="width:100%;text-align:center; color: black;">
								    
								    </TD>
								    
								    <TD align = "center"  style="display: none">
								    	<input type="text" name="spGiagoc1" value="<%=formatter2.format(Double.parseDouble( sanpham.getGiagoc()) ) %>" readonly style="width:100% ;text-align:right">
								    	<input type="hidden" name="spGiagoc" value="<%=formatter2.format(Double.parseDouble( sanpham.getGiagoc()) ) %>" readonly style="width:100% ;text-align:right">
								    	<input type="hidden"  name="spBgId" value="<%= sanpham.getBgId() %>" readonly style="width:100%">
								    </TD>
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhauDLN" type="hidden" value="<%= sanpham.getChietkhauDLN() %>" readonly style="width:100%;text-align:right">
								    </TD>
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhauTT" type="hidden" value="<%= sanpham.getChietkhauTT() %>" readonly style="width:100%;text-align:right">
								    </TD>
								    <TD align="center" style="display: none">
								    	<input type="text" name="spchietkhauDH" value="<%= sanpham.getChietkhauDH() %>" style="width:100%;text-align:right" onkeypress="return keypress(event);">
								    </TD>
								    <TD align = "center" style="display: none">
								    	<input type="text" name="dongia1"   <%= dhBean.getIsChiNhanh().equals("0")?"disabled=\"disabled\"":"" %>    value="<%=formatter2.format(Double.parseDouble( sanpham.getDongia()))%>" style="width:100% ;text-align:right; color: black;">
										<input type="hidden" name="dongia" readonly style="width:100% ;text-align:right">
								    </TD>
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhau1" type="text" value="<%= sanpham.getChietkhau()%>" disabled="disabled"  style="width:100%;text-align:right; color: black;">
								    	<input name="spchietkhau" type="hidden" value="<%= sanpham.getChietkhau() %>" readonly style="width:100%;text-align:right">
								    </TD>
								    <TD align = "center" style="display: none">
								    	<input name="thanhtien1" type="text" value="<%= formatter.format(Math.round(Double.parseDouble(sanpham.getChietkhau()))) %>" disabled="disabled"  style="width:100%;text-align:right; color: black;">
								    	<input name="thanhtien" type="hidden" value="<%= sanpham.getChietkhau() %>" readonly  style="width:100% ;text-align:right">
								    </TD>
								  <%--   <TD align = "center" >
								    	<input name="ctkm1" type="text"  value="<%= sanpham.getCTKM() %>" disabled="disabled" style="width:100%; color: black;">
								    	<input name="ctkm" type="hidden"  value="<%= sanpham.getCTKM() %>" readonly style="width:100%">
								    </TD> --%>
								</TR>
								<% m++; }}%>
								
								
								
									<% int m = 0;
									while (m < 20){
							
							
								%>
									<TR class= 'tblightrow' >
									<TD align="left" >
										<input name="masp" type="text" value="" onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" AUTOCOMPLETE="off">
									</TD>
									<TD align="left" >
										<input name="tensp1" type="text" disabled="disabled" value="" style="width:100%; color: black; cursor:pointer;" >			        	
								        <input name="tensp" type="hidden" value="" style="width:100%" >
									</TD>
									<TD align = "center" >
								
								    	<input name="tonkho1" disabled="disabled" type="text" value=""  style="width:100%;text-align:center; color: black;">
								    	<input name="tonkho" type="hidden" value=""  style="width:100%;text-align:center">
								    </TD>									
						        
							        	<TD align = "center" >
								        <input name="soluong" type="text" value="" style="width:100%;text-align:right" onkeyup="Dinhdang(this)">
								        <input name="soluongOld" type="hidden" value="" >
								        </TD>
								  
								    <TD align = "center" >
								    	<input name="donvitinh1" disabled="disabled" type="text" value=""  style="width:100%;text-align:center; color: black;">
								    	<input name="donvitinh" type="hidden" value=""  style="width:100%;text-align:center">
								    </TD>
								    
								      <TD align = "center" >
								    	<input name="quydoi" readonly="readonly" type="text" value=""  style="width:100%;text-align:center; color: black;">
								    	<input name="quydoi1" readonly="readonly" type="hidden" value=""  style="width:100%;text-align:center; color: black;">
								    
								    </TD>
								    
								    <TD align = "center"  style="display: none">
								    	<input type="text" name="spGiagoc1" value="" readonly style="width:100% ;text-align:right">
								    	<input type="hidden" name="spGiagoc" value="" readonly style="width:100% ;text-align:right">
								    	<input type="hidden"  name="spBgId" value="" readonly style="width:100%">
								    </TD>
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhauDLN" type="hidden" value="" readonly style="width:100%;text-align:right">
								    </TD>
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhauTT" type="hidden" value="" readonly style="width:100%;text-align:right">
								    </TD>
								    <TD align="center" style="display: none">
								    	<input type="text" name="spchietkhauDH" value="" style="width:100%;text-align:right" onkeypress="return keypress(event);">
								    </TD>
								    <TD align = "center" style="display: none">
								    	<input type="text" name="dongia1"       value="" style="width:100% ;text-align:right; color: black;">
										<input type="hidden" name="dongia" readonly style="width:100% ;text-align:right">
								    </TD>
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhau1" type="text" value="" disabled="disabled"  style="width:100%;text-align:right; color: black;">
								    	<input name="spchietkhau" type="hidden" value="" readonly style="width:100%;text-align:right">
								    </TD>
								    <TD align = "center" style="display: none">
								    	<input name="thanhtien1" type="text" value="" disabled="disabled"  style="width:100%;text-align:right; color: black;">
								    	<input name="thanhtien" type="hidden" value="" readonly  style="width:100% ;text-align:right">
								    </TD>
								  <%--   <TD align = "center" >
								    	<input name="ctkm1" type="text"  value="<%= sanpham.getCTKM() %>" disabled="disabled" style="width:100%; color: black;">
								    	<input name="ctkm" type="hidden"  value="<%= sanpham.getCTKM() %>" readonly style="width:100%">
								    </TD> --%>
								</TR>
								<% m++; }%>
								
								
																
					<!-- 			
								</tbody></TABLE>
							&nbsp;&nbsp;&nbsp;&nbsp;<a class="button2" href="javascript:ThemSanPham()">
	                         <img style="top: -4px;" src="../images/add.png" alt="">Th??m s???n ph???m</a>&nbsp;&nbsp;&nbsp;&nbsp;
 -->																																																																																																														
			
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
	replaces();
	dropdowncontent.init("searchlink", "right-bottom", 500, "click");
	jQuery(function()
	{		
		$("#smartId").autocomplete("KhachHangListTT.jsp");		
	});	
</script>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>

</HTML>
<%
try{
	if(!(ddkd == null ))
		ddkd.close();
	if(!(tbh == null))
		tbh.close();
	if(kh!=null){
		kh.close();
	}
	if(kho!=null){
		kho.close();
	}
	spThieuList=null;
	scheme_tongtien=null;
	scheme_chietkhau=null;
	if(gsbhs!=null){
		gsbhs.close();
	}
	if(dhBean != null)
	{
		dhBean.DBclose();
	}
	dhBean=null;
	 s.setAttribute("dhBean",null);
}catch(Exception er){
	System.out.println("Error DonHang1080:"+er.toString());
}
%>
