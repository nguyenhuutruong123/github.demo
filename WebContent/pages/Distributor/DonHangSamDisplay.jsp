<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.donhangsam.*" %>
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
<% IDonhangsam dhBean = (IDonhangsam)s.getAttribute("dhBean");
%>
<% List<ISanpham> splist = (List<ISanpham>)dhBean.getSpList(); %>
<% ResultSet ddkd = (ResultSet)dhBean.getDdkdList(); %>
<% ResultSet tbh = (ResultSet)dhBean.getTbhList(); %>
<% ResultSet kh = (ResultSet)dhBean.getKhList();%>
<% ResultSet kho = (ResultSet)dhBean.getKhoList(); %>
<% String ngaykhoaso = (String)dhBean.getNgayKs(); %>
<% String userId = (String) s.getAttribute("userId"); %>
<% Hashtable<String, Integer> spThieuList = dhBean.getSpThieuList(); %>
<% Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); %>
<% Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); %>
<% List<ISanpham> scheme_sanpham = (List<ISanpham>)dhBean.getScheme_SpList(); %>
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
<script type="text/javascript" src="../scripts/ajax-dynamic-list-dhsam.js"></script>
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
				//var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia) - parseFloat(chietkhau.item(i).value);
				var ptChietkhau = parseFloat(chietkhauDLN.item(i).value) + parseFloat(chietkhauTT.item(i).value); 
				if(chietkhauDH.item(i).value != "")
				{
					ptChietkhau += parseFloat(chietkhauDH.item(i).value);
				}
				don_gia = parseFloat(gia_goc) * (1-parseFloat(ptChietkhau)/100);
				dongia.item(i).value = DinhDangDonGia(don_gia);
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
			dongia.item(i).value = "";
			chietkhau.item(i).value = "";
			soluong.item(i).value = "";
			thanhtien.item(i).value = "";
			tonkho.item(i).value = "";
			//ctkm.item(i).value = "";
			TinhTien();
		}
	}	
	
	setTimeout(replaces, 100);
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
	function TinhTien()
	{
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("spGiagoc1");
		var thanhtien = document.getElementsByName("thanhtien1");
		//var chietkhau = document.getElementsByName("ChietKhau");
		
		var tongtien = 0;
		var tienbck = 0;
		for(var i=0; i < thanhtien.length; i++)
		{
			var thanh_tien = thanhtien.item(i).value;
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
			}
		}
		var tienchuaVAT = tongtien;
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
			vat = "10";
		var sotiengiam = document.getElementById("sotiengiam").value;
		var tongtiencoVAT = (parseFloat(vat) * tiensauCK) / 100 + tiensauCK;
		document.getElementById("SoTienCoVAT").value = DinhDangTien(Math.round(tongtiencoVAT));
		document.getElementById("SoTienCKKM").value = DinhDangTien(parseFloat(TongchietkhauKM()) + parseFloat(Tongtienkhuyenmai()));
		var SoTienSauCKKM = tongtiencoVAT + parseFloat(TongchietkhauKM()) + parseFloat(Tongtienkhuyenmai()); //tong tien khuyen mai co dau (-) dang truoc
		if(sotiengiam.lenght > 0)
			SoTienSauCKKM = SoTienSauCKKM - sotiengiam.replace(",","");
		document.getElementById("SoTienSauCKKM").value = DinhDangTien(Math.round(SoTienSauCKKM));
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
	
	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
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
				    alert("Đơn hàng đã xuất kho, Bạn không thể nhập số lượng lớn hơn, Vui lòng nhập lại..");
					return;
				}
			 }			
		 }
	}
	
	function saveform()
	{	
		 if(document.getElementById("aplaikm").value == "true")
		 {
			 alert('Đơn hàng này đã áp khuyến mại, bạn phải áp lại khuyến mại trước khi lưu');
			 return;
		 }
		
		 if(checkSanPham() == false)
		 {
			alert("Lỗi, bạn phải nhập sản phẩm cho đơn hàng...");
			return;
		 } 
		 congDonSPCungMa();
		
		 var masp = document.getElementsByName("masp");
		 var tensp = document.getElementsByName("tensp");
		 var soluong = document.getElementsByName("soluong");
		 var ddkdId = document.getElementById("ddkdTen");
		 var khId = document.getElementById("khTen");
		 var khoId = document.getElementById("khoTen");
		 var gsbhId = document.getElementById("gsbhId");
		 
		 if(gsbhId.value == "")
		 {
			alert("Vui lòng kiểm tra giám sát bán hàng...");
			return;
		 }
		 
		 if(ddkdId.value == "")
			{
				alert("vui lòng chọn Nhân viên bán hàng...");
				return;
			}
			if(khId.value == "")
			{
				alert("Vui lòng chọn khách hàng...");
				return;
			}
			if(khoId.value == "")
			{
				alert("vui lòng chọn kho nhập hàng...");
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
						alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
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
		///		alert('Lỗi...Bạn đã khóa sổ đến ngày ' + ngaykhoaso  + ' rồi...');
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
					alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
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
		//		alert('Lỗi...Bạn đã khóa sổ đến ngày '+ngaykhoaso  +' rồi...');
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
					alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho SP được chọn");
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
			var dongiaAdd = document.createElement("TD");
			var chietkhauAdd = document.createElement("TD");
			var thanhtienAdd = document.createElement("TD");
			var ctkmAdd = document.createElement("TD");
			
			var masp = document.createElement("input");
			masp.setAttribute("type", "textbox");
			masp.setAttribute("onkeyup", "ajax_showOptions(this,'abc',event)");
			masp.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			masp.name = 'masp';
			maspAdd.appendChild(masp);
			
			var tensp = document.createElement("input");
			tensp.setAttribute("type", "textbox");
			tensp.setAttribute("readonly", "readonly");
			tensp.setAttribute("style"," width:100%;border:1px;	border-style:solid;border-color: #808080;");
			
			tensp.name = 'tensp';
			tenspAdd.appendChild(tensp);
			
			var soluong = document.createElement("input");
			soluong.setAttribute("type", "textbox");
			soluong.setAttribute("onkeypress","return keypress(event)");
			soluong.value = "";
			soluong.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;");

			soluong.name = "soluong";
			soluongAdd.appendChild(soluong);
			
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
			dongia.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			dongia.value = "";
			dongia.name = 'dongia';
			dongiaAdd.appendChild(dongia);
			
			var chietkhau = document.createElement("input");
			chietkhau.setAttribute("type", "textbox");
			chietkhau.setAttribute("readonly", "readonly");
			chietkhau.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			chietkhau.value = "";
			chietkhau.name = 'spchietkhau';
			chietkhauAdd.appendChild(chietkhau);
			
			var thanhtien = document.createElement("input");
			thanhtien.setAttribute("type", "textbox");
			thanhtien.setAttribute("readonly", "readonly");
			thanhtien.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			thanhtien.value = "";
			thanhtien.name = "thanhtien";
			thanhtienAdd.appendChild(thanhtien);
			
			var ctkm = document.createElement("input");
			ctkm.setAttribute("type", "textbox");
			ctkm.setAttribute("readonly", "readonly");
			ctkm.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			ctkm.value = "";
			ctkm.name = 'ctkm';
			ctkmAdd.appendChild(ctkm);
			
			tr.appendChild(maspAdd);
			tr.appendChild(tenspAdd);
			tr.appendChild(soluongAdd);
			tr.appendChild(dvtinhAdd);
			tr.appendChild(dongiaAdd);
			tr.appendChild(chietkhauAdd);
			tr.appendChild(thanhtienAdd);
			tr.appendChild(ctkmAdd);
			
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
			element.value=DinhDangTien(element.value);
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

<form name="dhForm" method="post" action="../../DonhangSamUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="nppId" value='<%= dhBean.getNppId() %>'>
<input type="hidden" name="ngaykhoaso" id = "ngaykhoaso" value='<%= ngaykhoaso %>'>
<input type="hidden" name="action" value='1'>
<INPUT type="hidden" name="id" value='<%= dhBean.getId() %>'>
<INPUT type="hidden" name="trangthai" id="trangthaiDh" value='<%= dhBean.getTrangthai() %>'>
<INPUT type="hidden" name="khoTen" value='<%= dhBean.getKhoId() %>'>
<input type="hidden" name="muctindung" id ="muctindung" value='<%= dhBean.getMuctindung() %>'>
<INPUT type="hidden" name="aplaikm" id="aplaikm" value='<%=dhBean.isAplaikhuyenmai() %>'>
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
			 								   <TD align="left"  class="tbnavigation">&nbsp;Quản lý bán hàng > Bán hàng cho khách hàng > Hiển thị </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng bạn  <%= dhBean.getNppTen() %> &nbsp; </TD>
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
										   <!--  <TD width="30" align="left" >
										    
										    <div id="btnSave">
										    	<A href="javascript:saveform()" ><img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A>
										    
										    	</div>
										   
										    </TD> -->
										    <TD width="2" align="left" >&nbsp;</TD>
								    		<TD width="30" align="left"><A href="../../InBangKepdfSvl?userId=<%=userId%>&display=<%=dhBean.getId()%>" ><img src="../images/Printer30.png" alt="In" title="In chung tu" longdesc="In chung tu" border=1 style="border-style:outset"></A></TD>
								    		<TD align="left" >&nbsp;</TD>
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="0" width="100%" cellpadding = "0" cellspacing = "0">
								<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>			
				    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" readonly="readonly" rows="1"><%= dhBean.getMessage()  %></textarea>
										<% dhBean.setMessage(""); %>
									</FIELDSET>
							   </TD>
								</tr>
								<TR>
									<TD align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Đơn hàng bán </LEGEND>
										<TABLE cellpadding = "3" cellspacing = "0" width = "100%" border = 0>
											<TR>
											  <TD width="170px" class="plainlabel">Ngày giao dịch </TD>
											  <TD class="plainlabel">
											 	  <input type="text"  class="days" size="11"
                                    					id="tungay" name="tungay" value="<%= dhBean.getNgaygiaodich() %>" maxlength="10" readonly />
											  </TD>
											  											  
											    <TD class="plainlabel">Giám sát bán hàng </TD>
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
												<TD class="plainlabel">Kho hàng </TD>
												<TD class="plainlabel"> 
									 			<SELECT name="khoTen" id="khoTen" onchange="submitform()">
													  <% if(kho != null){
														  try{while(kho.next()){
											    			if(kho.getString("khoId").equals(dhBean.getKhoId())){ session.setAttribute("khoId", kho.getString("khoId")); %>
											      				<option value='<%= kho.getString("khoId") %>' selected onMouseover="ddrivetip('<%= kho.getString("diengiai") %>', 300)"; onMouseout="hideddrivetip()"><%= kho.getString("Ten") + " " %></option>
											      			<%}else{ if(kho.getString("Ten").indexOf("PR") < 0){ %>
											     				<option value='<%= kho.getString("khoId") %>' onMouseover="ddrivetip('<%= kho.getString("diengiai") %>', 300)"; onMouseout="hideddrivetip()"><%= kho.getString("Ten") + " " %></option>
											     			<%}}}}catch(java.sql.SQLException e){} }%>
                                    			</SELECT></TD>
                                    			                                    			
												<TD  class="plainlabel">Nhân viên bán hàng </TD>
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
                                    			</SELECT></TD>											
											</TR>
											
											<TR >
												<TD class="plainlabel">Mã khách hàng</TD>
												<TD class="plainlabel" width="250px">
                                                 	<input type="text" id="smartId" name="smartId" value="<%= dhBean.getSmartId() %>" onkeypress="keypress2(event);" />
                                                <TD class="plainlabel" width="190px">Tên khách hàng - Địa chỉ</TD>                                                   
                                                <TD class="plainlabel" colspan="5">
                                                	<input type="text" id="khTen" name="khTen" style="width: 100%" value="<%= dhBean.getKhTen() %>" readonly/>  
                                                </TD>                                                           
                                            </TR>   
                                            
											<TR class="tblightrow" >
											    <TD class="plainlabel" ><!-- % Chiết khấu (khách hàng) --> </TD>
											    <TD class="plainlabel" >
											    	<input name="ChietKhau" type = "hidden" id="ChietKhau" type="text" value="<%=dhBean.getChietkhau()  %>" onkeypress="return keypress(event);"></TD>
									            <TD class="plainlabel">Tổng chiết khấu</TD>
									            <TD class="plainlabel" colspan=3>
									            	<input name="TienChietKhau" id="TienChietKhau" type="text" disabled="disabled" value="<%= dhBean.getTongChietKhau() %>"> VND </TD>
										   	</TR>
											
											<TR class="tblightrow" style="display: none" >
											  <TD class="plainlabel">VAT (%) </TD>
											  <TD class="plainlabel" >
											  	<input name="VAT" id="VAT" type="text" value="<%= dhBean.getVAT() %>" onkeypress="return keypress(event);"> %</TD>
												
											  <TD class="plainlabel">Tổng số tiền (sau VAT)</TD>
											  <TD class="plainlabel" colspan="3">
											  	<input name="SoTienCoVAT" id="SoTienCoVAT" type="text" readonly value="<%= dhBean.getTongtiensauVAT()%>"> VND </TD>
											   
											  <!-- 											  
											  <TD  class="plainlabel">Tổng số tiền (trước VAT) </TD>	     
										      <TD colspan="3"  class="plainlabel"><input name="SoTienChuaVAT" id="SoTienChuaVAT" type="text" value="<%=dhBean.getTongtientruocVAT()%>" readonly > VND </TD>
									       	   -->	
									       	</TR>											  											

										    <TR class="tblightrow">

											  <TD class="plainlabel">Tổng số tiền (trước chiết khấu)</TD>	     
										      <TD class="plainlabel"><input name="SoTienChuaVAT" id="SoTienChuaVAT" type="text" value="<%=dhBean.getTongtientruocVAT()%>" readonly > VND </TD>
											  
											  <TD  class="plainlabel">Tổng số tiền (sau chiết khấu)</TD>
											  <TD colspan="3" class="plainlabel"><input name="SoTienSauCK" id="SoTienSauCK" type="text" readonly 
											  	  value="<%=dhBean.getTongtiensauCK()%>" > VND </TD>										  	
										  	</TR>

											<TR class="tblightrow">
											  <TD class="plainlabel">Tổng số tiền KM</TD>
											  <TD class="plainlabel">
											  	<input name="SoTienCKKM" id="SoTienCKKM" type="text" readonly value="<%= dhBean.getTongtienCKKM() %>"> VND </TD>
											  <TD class="plainlabel"><Font color="red">Tổng số tiền (sau KM)</Font></TD>
											  <TD class="plainlabel">
											  	<input name="SoTienSauCKKM" id="SoTienSauCKKM" type="text" readonly value="<%= dhBean.getTongtiensauCKKM() %>"> VND </TD>
										  	</TR>
										  	<TR class="tblightrow">
											  <TD class="plainlabel">Địa chỉ giao hàng</TD>
											  <TD class="plainlabel" >
											  	<input name="diachigiao" id="diachigiao" type="text" value="<%= dhBean.getDiachigiaohang() %>"> </TD>
										  	  <TD class="plainlabel">Nợ cũ</TD>
										  	  <TD class="plainlabel">
											  	<input name="NoCu" id="NoCu" type="text" readonly value="<%= formatter.format(dhBean.getNoCu()) %>" onkeyup="DDTien();"> VND </TD>
										  	
										  	</TR>
												<TR>
										  	<TD width="120px" class="plainlabel" valign="top">Nhân viên giao nhận</TD>
															<TD class="plainlabel" valign="top" colspan="1">
																  	<SELECT name="nvgnId" id="nvgnId" >
																  		<option value='' ></option>
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
															<TD class="plainlabel" >Số tiền giảm </TD>
							                    <TD class="plainlabel" colspan="3" >
							                    	<input type="text"  name="sotiengiam" id="sotiengiam" onkeypress="return keypress(event);" maxlength="15" onkeyup="Dinhdang(this)" value="<%= formatter.format(Float.parseFloat(dhBean.getSotiengiam()==null?"0":dhBean.getSotiengiam()))  %>" style="width: 200px;" /> VNĐ
							                    </TD>
															</TR>
											<TR>
							                	<TD class="plainlabel" >Ghi chú </TD>
							                    <TD class="plainlabel" colspan="3" >
							                    	<input type="text"  name="ghichu" value="<%= dhBean.getGhiChu() %>" style="width: 700px;" />
							                    </TD>
										    </TR>	
												
												
										  <TR class="tblightrow">
										   <TD  class="plainlabel" colspan = '4'>
										   <div id="btnApKhuyenMai">
										  	<!-- 	<a class="button2" href="javascript:Apkhuyenmai()">
													<img style="top: -4px;" src="../images/New.png" alt="">Lưu & Áp khuyến mại</a> -->
											</div>											
										  </TD></TR>
										</TABLE>
									</FIELDSET>
								  </TD>
							   </TR>	
							   <TR>
							   		<TD>
										<TABLE width = "100%" border="0" cellpadding="0" cellspacing="1">
										<tbody id="san_pham">
												<TR class="tbheader" >
													<TH width="10%" height="20">Mã sản phẩm </TH>
													<TH width="28%">Tên sản phẩm</TH>
													<TH width="7%">Tồn hiện tại</TH>
													<TH width="7%">Số lượng</TH>
													<TH width="7%">DVT</TH>
													<TH width="7%">Đơn giá gốc</TH>
													<TH width="7%" style="display: none">Chiết khấu dữ liệu nền</TH>
													<TH width="7%" style="display: none">Chiết khấu trung tâm</TH>
													<TH width="7%" >Chiết khấu </TH> <!-- chiet khau don hang -->
													<TH width="10%">Đơn giá sau CK </TH>
													<TH width="7%" style="display: none">Chiết khấu </TH>
													<TH width="9%">Thành tiền </TH>
													<TH width="9%" >CTKM</TH>
												</TR>
									<% 
							if(splist != null){
							ISanpham sanpham = null;
							int size = splist.size();
							int m = 0;
							while (m < size){
								sanpham = splist.get(m); 
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

									    <TD align = "center" ><div class="addspeech" title="San pham nay con toi da <%= spThieuList.get(sanpham.getMasanpham()) %> san pham, vui long chon lai so luong">
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
								 
								    	<input type="text" name="spGiagoc1" value="<%=formatter2.format(Double.parseDouble( sanpham.getGiagoc())) %>" readonly style="width:100% ;text-align:right">
								    	<input type="hidden" name="spGiagoc" value="<%=formatter2.format(Double.parseDouble( sanpham.getGiagoc())) %>" readonly style="width:100% ;text-align:right">
								    	<input type="hidden"  name="spBgId" value="<%= sanpham.getBgId() %>" readonly style="width:100%">
								    </TD>
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhauDLN" type="hidden" value="<%= sanpham.getChietkhauDLN() %>" readonly style="width:100%;text-align:right">
								    </TD>
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhauTT" type="hidden" value="<%= sanpham.getChietkhauTT() %>" readonly style="width:100%;text-align:right">
								    </TD>
								    <TD align="center" >
								    	<input type="text" name="spchietkhauDH" value="<%= sanpham.getChietkhauDH() %>" style="width:100%;text-align:right" onkeypress="return keypress(event);">
								    </TD>
								    <TD align = "center" >
								    	<input type="text" name="dongia1"   <%= dhBean.getIsChiNhanh().equals("0")?"disabled=\"disabled\"":"" %>  onkeyup="Dinhdang(this)"  value="<%=formatter2.format(Math.round(Double.parseDouble( sanpham.getDongia())))%>" style="width:100% ;text-align:right; color: black;">
								    	<input type="hidden" name="dongia" value="<%= sanpham.getDongia() %>" readonly style="width:100% ;text-align:right">
								    </TD>
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhau1" type="text" value="<%= sanpham.getChietkhau()%>" disabled="disabled"  style="width:100%;text-align:right; color: black;">
								    	<input name="spchietkhau" type="hidden" value="<%= sanpham.getChietkhau() %>" readonly style="width:100%;text-align:right">
								    </TD>
								    <TD align = "center" >
								    	<input name="thanhtien1" type="text" value="<%= formatter.format(Math.round(Double.parseDouble(sanpham.getChietkhau()))) %>" disabled="disabled"  style="width:100%;text-align:right; color: black;">
								    	<input name="thanhtien" type="hidden" value="<%= sanpham.getChietkhau() %>" readonly  style="width:100% ;text-align:right">
								    </TD>
								    <TD align = "center" >
								    	<input name="ctkm1" type="text"  value="<%= sanpham.getCTKM() %>" disabled="disabled" style="width:100%; color: black;">
								    	<input name="ctkm" type="hidden"  value="<%= sanpham.getCTKM() %>" readonly style="width:100%">
								    </TD>
								</TR>
								<% m++; }}%>
								
								<%if(scheme_tongtien.size() > 0)
								{
									Enumeration<String> keys = scheme_tongtien.keys();
									while(keys.hasMoreElements())
									{
										String key = keys.nextElement(); %>
										<TR class= 'tblightrow'>
											<TD align="center" ><input type="text" size="18" readonly></TD>
											<TD align="left" ><input type="text" readonly style="width:100%"></TD>
											<TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input name="ttTrakm" type="text" value="<%= "-" + formatter.format(scheme_tongtien.get(key)) %>" readonly style="color:red; width:100%;text-align:right"></TD>
										    <TD align = "center" ><input name="trakmSpScheme" type="text" value="<%= key %>" style="color:red;width:100%" readonly></TD>
										</TR>
								<%}}%>						
								<%if(scheme_chietkhau.size() > 0)
								{
									Float tonggiatri = Float.parseFloat(dhBean.getTongtiensauVAT());
									Enumeration<String> keyss = scheme_chietkhau.keys();
									while(keyss.hasMoreElements())
									{
										String key = keyss.nextElement(); 
										long chietkhau = Math.round(scheme_chietkhau.get(key)); 
										%>
										<TR class= 'tblightrow'>
											<TD align="center" ><input type="text" style="width:100%;text-align:right" readonly></TD>
											<TD align="left" ><input type="text" readonly style="width:100%"></TD>
											<TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input name="ckTrakm"  type="text" value="<%="-" +formatter.format(Math.round(chietkhau)) %>" readonly style="color:red;width:100%;text-align:right"></TD>
										    <TD align = "center"  colspan="3"><input name="trakmSpScheme" type="text"  value="<%= key %>" style="color:red;width:100%" readonly></TD>
										</TR>
								<%}}%>								
								<%  if(scheme_sanpham.size() > 0)
								{
									ISanpham tkmSp = null;
									int count = 0;
									while(count < scheme_sanpham.size())
									{
										tkmSp = scheme_sanpham.get(count); %>
										<TR class= 'tblightrow'>
											<TD align="center" >
												<input name="maspTrakm" type="text" value="<%= tkmSp.getMasanpham() %>" style="color:red; width:100%;text-align:right" readonly>
											</TD>
											<TD align="left" >
												<input name="tenspTraKm" type="text" readonly value="<%= tkmSp.getTensanpham() %>" style="color:red;width:100%"></TD>
									        <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
									        <TD align = "center" ><input name="slgTraKm" type="text" value="<%= formatter.format(Math.round(Double.parseDouble(tkmSp.getSoluong()))) %>" style="color:red;width:100%;text-align:right" readonly></TD>
									        <TD align = "center" ><input name="dvtTrakm" type="text" value="<%= tkmSp.getDonvitinh() %>" readonly style="color:red;width:100%;text-align:center"></TD>
										    <TD align = "center" colspan="5" ><input name="trakmSpScheme" type="text" style="color:red;width:100%" value="<%= tkmSp.getCTKM() %>"  readonly></TD>
										</TR>
								<% count++; }}%>
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
		$("#smartId").autocomplete("KhachHangList.jsp");		
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
