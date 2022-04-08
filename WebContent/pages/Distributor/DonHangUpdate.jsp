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
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
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
<% IDonhang dhBean = (IDonhang)s.getAttribute("dhBean");
%>
<% List<ISanpham> splist = (List<ISanpham>)dhBean.getSpList(); %>
<% ResultSet ddkd = (ResultSet)dhBean.getDdkdList(); %>
<% ResultSet tbh = (ResultSet)dhBean.getTbhList(); %>
<% ResultSet kh = (ResultSet)dhBean.getKhList();%>
<% ResultSet kho = (ResultSet)dhBean.getKhoList(); %>
<% ResultSet ghichuo = (ResultSet)dhBean.getGhichuOList(); %>
<% String ngaykhoaso = (String)dhBean.getNgayKs(); %>
<% String userId = (String) s.getAttribute("userId"); %>
<% Hashtable<String, Integer> spThieuList = dhBean.getSpThieuList(); %>
<% Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); %>
<% Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); %>
<% List<ISanpham> scheme_sanpham = (List<ISanpham>)dhBean.getScheme_SpList(); %>
<% ResultSet gsbhs = (ResultSet)dhBean.getgsbhs(); %>
<% ResultSet nvgn = (ResultSet)dhBean.getnvgnRs(); 


 String msg_vuot_ngan_sach = "";
 for(int xy = 0; xy < dhBean.getCtkm_bi_giam_so_suat().size(); xy++)
 {
	msg_vuot_ngan_sach +="\n " +  dhBean.getCtkm_bi_giam_so_suat().get(xy);
 }




%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<% String nnId = (String)session.getAttribute("nnId"); %> 


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
<script type="text/javascript" src="../scripts/ajax-dynamic-list.js"></script>
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
	var coderoute = document.getElementById("coderoute");
	
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
			coderoute.value = tem.substring(0, parseInt(tem.indexOf("][")));
			
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
	var tiengiamtru = document.getElementsByName("tiengiamtru");
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
				
				<% if(dhBean.getDonhangkhac().equals("1")){ %>
				dongia.item(i).value = "0";
				<%} else {%>
				dongia.item(i).value = DinhDangDonGia(sp.substring(0, parseInt(sp.indexOf("] ["))));
				<%}%>	
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				tonkho.item(i).value = DinhDangTien(sp.substring(0, parseInt(sp.indexOf("] ["))));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				spGiagoc.item(i).value =DinhDangDonGia( sp.substring(0, parseInt(sp.indexOf("] ["))) ); 
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);

				spBgId.item(i).value = sp.substring(0, parseInt(sp.indexOf("] ["))); 
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				chietkhauDH.item(i).value = sp.substring(0, parseInt(sp.indexOf("] ["))); 
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
				var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia) - parseFloat(chietkhau.item(i).value);
				var ptChietkhau = parseFloat(chietkhauDLN.item(i).value) + parseFloat(chietkhauTT.item(i).value); 
				if(chietkhauDH.item(i).value != "")
				{
					ptChietkhau += parseFloat(chietkhauDH.item(i).value);
				}
				
				var tien_giam_tru = tiengiamtru.item(i).value;
				
				<% if(dhBean.getDonhangkhac().equals("2")){ %>
					if(tien_giam_tru != "")
					{
						if(tien_giam_tru=='-')
							tien_giam_tru = '0';
						tien_giam_tru =	tien_giam_tru.replace(",","");
						don_gia =  parseFloat(gia_goc) + parseFloat(tien_giam_tru);
					}
				<%}%>
				
				
				//don_gia =   Math.round( parseFloat(gia_goc) * (1-parseFloat(ptChietkhau)/100)  ); 
				//don_gia =   parseFloat(gia_goc) * (1-parseFloat(ptChietkhau)/100);
				//dongia.item(i).value =DinhDangDonGia(don_gia);
				
				var tt = parseInt(so_luong) * parseFloat(don_gia);
				dongia.item(i).value = DinhDangDonGia(roundNumber(don_gia,6));
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
			tiengiamtru.item(i).value = "";
			//ctkm.item(i).value = "";
			TinhTien();
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
		var sotiengiam = document.getElementById("sotiengiam").value;
		
		var tongtiencoVAT = (parseFloat(vat) * tiensauCK) / 100 + tiensauCK;
		document.getElementById("SoTienCoVAT").value = DinhDangTien(Math.round(tongtiencoVAT));
		document.getElementById("SoTienCKKM").value = DinhDangTien(parseFloat(TongchietkhauKM()) + parseFloat(Tongtienkhuyenmai()));
		
		var SoTienSauCKKM = tongtiencoVAT + parseFloat(TongchietkhauKM()) + parseFloat(Tongtienkhuyenmai()); //tong tien khuyen mai co dau (-) dang truoc
		
		if(sotiengiam.length > 0)
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
	
	function checkHangMau(dong)
	{
		var masp = document.getElementsByName("masp");
		var hangmau = document.getElementsByName("hangmau");
		var hangmauVal = document.getElementsByName("hangmauVal");
		
		
		
		if(masp.item(dong).value ==''  )
		{
			alert('Vui lòng chọn sản phẩm!');
			hangmau.item(dong).checked = false;
			hangmauVal.item(dong).value = "0";
			return;
		}
		if(hangmau.item(dong).checked == true)
		{
			hangmauVal.item(dong).value = "1";
			var dongia1 = document.getElementsByName("dongia1");
			var dongia = document.getElementsByName("dongia");
			dongia1.item(dong).value = "0";
			dongia.item(dong).value = "0";
		}
		else
		{
			hangmauVal.item(dong).value = "0";
			masp.item(dong).value = '';
			alert('Vui lòng chọn lại sản phẩm để cập nhật lại giá!');
		}
		
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
	
	function DinhDangChenhlech(element)
	{
		if(element.value =='-'|| element.value =='-0')
		{
			element.value ='-';
			if(element.value== '' )
			{
				element.value= '';
			}
			return;
		}
		 Dinhdang(element);
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
				var coderoute = document.getElementById("coderoute");
				
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
						coderoute.value = tem.substring(0, parseInt(tem.indexOf("][")));
						
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
		var donhangkhac = document.getElementById("donhangkhac").value;
		 if(document.getElementById("aplaikm").value == "true" && donhangkhac== "0")
		 {
			 alert('Đơn hàng này đã áp khuyến mại, bạn phải áp lại khuyến mại trước khi lưu');
			 return;
		 }
		 
		 var kq = document.getElementById("chuaapkm").value;
		 if(kq == "false" && donhangkhac== "0")
		 {
			 alert('Bạn chưa áp khuyến mại cho đơn hàng này, bạn phải áp khuyến mại');
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
		 var nvgnId = document.getElementById("nvgnId");
		 
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
		 if(nvgnId.value == "")
		 {
			alert("Vui lòng kiểm tra nhân viên giao nhận...");
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
		
		document.forms['dhForm'].action.value = 'submit';
	    document.forms["dhForm"].submit();
	 }
	 
	 function chot_inside()
	 { 		
		var cf = confirm('Bạn có muốn chốt đơn hàng này?');
		
		if (cf) {
			document.forms['dhForm'].action.value = 'chot_inside';
		    document.forms["dhForm"].submit();
	 	}
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
				if(soluong.item(k).value == ""  || soluong.item(k).value  == "0" ||  tensp.item(k).value == "")
				{
					alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho SP được chọn");
					return;
				}
		 }
		 
		 document.getElementById("btnApKhuyenMai").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		 
		 document.forms['dhForm'].action.value='apkhuyenmai';
		 document.forms['dhForm'].submit();
	 }
	 function TinhTienComBo()
	{
		var giaSpComBo = document.getElementById("giaSpComBo").value;
		var soluongSpComBo = document.getElementById("soluongSpComBo").value;
		if(giaSpComBo != "" && soluongSpComBo != "")
		{
			giaSpComBo = giaSpComBo.replace(",","");
			soluongSpComBo = soluongSpComBo.replace(",","");
			document.getElementById("ttSpComBo").value = DinhDangTien( Math.round(parseFloat(soluongSpComBo)*parseFloat(giaSpComBo)));
		}
		
		
	}
	 function congDonSPCungMa()
	 {
		var masp = document.getElementsByName("masp");
		var soluong = document.getElementsByName("soluong");
		var hangmau = document.getElementsByName("hangmau");
		var ii;
		for(ii = 0; ii < (masp.length - 1) ; ii++)
		{
			for( j = ii + 1; j < masp.length; j++)
			{
				if(masp.item(ii).value != "" && masp.item(ii).value == masp.item(j).value && hangmau.item(ii).checked == hangmau.item(j).checked )
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
			var chietkhaudhAdd = document.createElement("TD");
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
			
			var chietkhaudh = document.createElement("input");
			chietkhaudh.setAttribute("type", "textbox");
			chietkhaudh.setAttribute("readonly", "readonly");
			chietkhaudh.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			chietkhaudh.value = "";
			chietkhaudh.name = 'spchietkhauDH';
			chietkhauAdd.appendChild(chietkhaudh);
			
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
			tr.appendChild(chietkhaudhAdd);
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
	function thongbao_vuot_ngan_sach() {
		var tt = document.forms['dhForm'].msg_vuot_ngan_sach.value;
		if (tt.length > 0)
		{
			if(!confirm('Các CTKM bị hết ngân sách bạn có muốn tiếp tục lưu khuyến mãi?' + tt )) 
			{
				return false ;
			}else
			{
				 document.forms['dhForm'].chap_nhan_giam_so_suat_km.value='true';
				 Apkhuyenmai();
			}
		}

		document.forms['dhForm'].msg_vuot_ngan_sach.value = '';
	}
	
	
</script>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" onload="Focus()" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="dhForm" method="post" action="../../DonhangUpdateSvl">
<% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>

<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="nppId" value='<%= dhBean.getNppId() %>'>
<input type="hidden" name="ngaykhoaso" id = "ngaykhoaso" value='<%= ngaykhoaso %>'>
<input type="hidden" name="action" value='1'>
<INPUT type="hidden" name="id" value='<%= dhBean.getId() %>'>
<INPUT type="hidden" name="chap_nhan_giam_so_suat_km" value='<%= dhBean.getChap_nhan_giam_so_suat_km() %>'>
<INPUT type="hidden" id ="msg_vuot_ngan_sach" name="msg_vuot_ngan_sach" value='<%= msg_vuot_ngan_sach %>'>


<INPUT type="hidden" name="donhangkhac"  id="donhangkhac" value='<%= dhBean.getDonhangkhac() %>'>
<INPUT type="hidden" name="trangthai" id="trangthaiDh" value='<%= dhBean.getTrangthai() %>'>
<INPUT type="hidden" name="khoTen" value='<%= dhBean.getKhoId() %>'>
<input type="hidden" name="muctindung" id ="muctindung" value='<%= dhBean.getMuctindung() %>'>
<INPUT type="hidden" name="aplaikm" id="aplaikm" value='<%=dhBean.isAplaikhuyenmai() %>'>
<INPUT type="hidden" name="chuaapkm" id="chuaapkm" value='<%=dhBean.isChuaApkhuyenmai() %>'>
<INPUT type="hidden" name="cokm" id="cokm" value='<%=dhBean.isCokhuyenmai() %>'>
<input type="hidden" name="tck" id ="tck" value='<%=dhBean.getChietkhau() %>'>
<INPUT type="hidden" name="bgstId" id="bgstId" value='<%= dhBean.getBgstId() %>'>
<input type="hidden" id = "khId" name="khId" value='<%= dhBean.getKhId() %>'>
 
<input type="hidden" id = "tn" name="tn" value='<%= session.getAttribute("tn") %>'>
<input type="hidden" id = "dn" name="dn" value='<%= session.getAttribute("dn") %>'>
<input type="hidden" id = "tumuc" name="tumuc" value='<%= session.getAttribute("tumuc") %>'>
<input type="hidden" id = "denmuc" name="denmuc" value='<%= session.getAttribute("denmuc") %>'>
<input type="hidden" id = "nvbhid" name="nvbhid" value='<%= session.getAttribute("nvbhid") %>'>
<input type="hidden" id = "sodh" name="sodh" value='<%= session.getAttribute("sodh") %>'>
<input type="hidden" id = "makh" name="makh" value='<%= session.getAttribute("makh") %>'>
<input type="hidden" id = "tthai" name="tthai" value='<%= session.getAttribute("tthai") %>'>
<input type="hidden" id = "tthaiin" name="tthaiin" value='<%= session.getAttribute("tthaiin") %>'>



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
			 								   <TD align="left"  class="tbnavigation">&nbsp;Quản lý bán hàng > Bán hàng cho khách hàng > Cập nhật </TD>								   
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
											
											<TD align="left">
												<A href = "javascript:history.back()" >
													<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset">
												</A>

<!-- 												<div id="btnSave">
											    	<A href="javascript:saveform()" >
											    		<img src="../images/Save30.png" alt="Luu lai" title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset">
											    	</A>
											    </div> -->

								    			<A href="../../InBangKepdfSvl?userId=<%=userId%>&display=<%=dhBean.getId()%>">
								    				<img src="../images/Printer30.png" alt="In" title="In chung tu" longdesc="In chung tu" border=1 style="border-style:outset">
								    			</A>
								  				
								  				<A href="../../InBangKepdfSvl?userId=<%=userId%>&excel=<%=dhBean.getId()%>">
								  					<img src="../images/excel.gif" alt="Thông tin đơn hàng" title="Thông tin đơn hàng" longdesc="Thông tin đơn hàng"  border="1px" height=30 width=30 >
								  				</A>
								    		
								    		<% 
								    			String trangthaidh = dhBean.getTrangthai(); 
								    			
								    			if (trangthaidh != null && (trangthaidh.equals("0"))) { %>
							  					<A href="javascript:chot_inside()" >
							  						<img src="../images/Chot.png" alt="Chốt" title="Chốt" border="1" longdesc="Chốt" style="border-style:outset">
							  					</A>
								    		<% 	
								    			}
								    		%>
								    		</TD>
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
										<LEGEND class="legendtitle">&nbsp;Đơn hàng bán</LEGEND>
										<TABLE cellpadding = "3" cellspacing = "0" width = "100%" border = 0>
											<TR>
											  <TD width="200px" class="plainlabel">Ngày giao dịch </TD>
											  <TD class="plainlabel">
											 	  <input type="text"  <%= dhBean.getIsPDA().equals("1") ? "": "class=\"days\""  %>   size="11"
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
												<%System.out.println("MAKH: "+dhBean.getSmartId()); %>
                                                 	<input type="text" id="smartId" name="smartId" value="<%= dhBean.getSmartId() %>" onkeypress="keypress2(event);" />
                                                <TD class="plainlabel" width="190px">Tên khách hàng - Địa chỉ</TD>                                                   
                                                <TD class="plainlabel" colspan="5">
                                                	<input type="text" id="khTen" name="khTen" style="width: 100%" value="<%= dhBean.getKhTen() %>" readonly/>  
                                                </TD>                                                           
                                            </TR>   
                                            
											<TR class="tblightrow">
												<TD class="plainlabel" >Code Route<!-- % Chiết khấu (khách hàng) --> </TD>
											    <TD class="plainlabel" >
											   	 	<input type="text" id="coderoute" name="coderoute" value="<%=dhBean.getCoderoute()%>">
											    	<input name="ChietKhau" type = "hidden" id="ChietKhau" type="text" value="<%=dhBean.getChietkhau()  %>" onkeypress="return keypress(event);">
											    </TD>
									            <TD class="plainlabel">Tổng chiết khấu</TD>
									            <TD class="plainlabel" colspan="1">
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
											  <%System.out.println("Tien sau KM:"+dhBean.getTongtiensauCKKM()); %>
											  	<input name="SoTienSauCKKM" id="SoTienSauCKKM" type="text" readonly value="<%= dhBean.getTongtiensauCKKM() %>"> VND </TD>
										  	</TR>
										  	<TR class="tblightrow">
											  <TD class="plainlabel">Địa chỉ giao hàng</TD>
											  <TD class="plainlabel" >
											  	<input name="diachigiao" id="diachigiao" type="text" value="<%= dhBean.getDiachigiaohang() %>"> </TD>
										  	  <TD class="plainlabel">Nợ cũ</TD>
										  	  <TD class="plainlabel">
											  	<input name="NoCu" id="NoCu" type="text"  value="<%= formatter.format(dhBean.getNoCu()) %>" onkeyup="DDTien();"> VND </TD>
										  	
										  	</TR>
										  	<TR>
										  	<TD width="120px" class="plainlabel" valign="top">Nhân viên giao nhận</TD>
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
															<TD class="plainlabel" >Số tiền giảm </TD>
							                    <TD class="plainlabel" colspan="3" >
							                    	<input type="text"  name="sotiengiam" maxlength="15" id="sotiengiam" onkeypress="return keypress(event);" onkeyup="Dinhdang(this)" value="<%= formatter.format(Float.parseFloat(dhBean.getSotiengiam()==null?"0":dhBean.getSotiengiam()))  %>" style="width: 200px;" /> VNĐ
							                    </TD>
											</TR>
											
											<TR>
							                	<TD class="plainlabel" >Ghi chú </TD>
												<TD class="plainlabel" valign="top" colspan="3" >
											  	<SELECT name="ghichuO" id="ghichuO" >
											  			<option value=''></option>
												    <% try{ while(ghichuo.next()){ 
											    			if(ghichuo.getString("PK_SEQ").equals(dhBean.getGhiChuOption())){ %>
											      				<option value='<%=ghichuo.getString("PK_SEQ")%>' selected> <%=ghichuo.getString("diengiai") %></option>
											      			<%}else{ %>
											     				<option value='<%=ghichuo.getString("PK_SEQ")%>'> <%=ghichuo.getString("diengiai") %></option>
											     			<%}}}catch(Exception e){} %>
												 </SELECT>
												</TD>
										    </TR>
											
										  	<TR>
							                	<TD class="plainlabel" >Ghi chú khác </TD>
							                    <TD class="plainlabel" colspan="3" >
							                    	<input type="text"  name="ghichu" value="<%= dhBean.getGhiChu() %>" style="width: 700px;" />
							                    </TD>
										     </TR>
										  	
										  	
										  	<TR>
							                	<TD class="plainlabel" >Ngày giao hàng </TD>
							                    <TD class="plainlabel" >
											 	  <input type="text"  class="days" size="11"
                                    					id="ngaygh" name="ngaygh" value="<%= dhBean.getNgaygiaohang() %>" maxlength="10" readonly />
											  	</TD>
											  	
											  	<TD class="plainlabel" >Trạng thái</TD>
											  	<TD class="plainlabel" colspan="3" >
											  		<input style="color: red" type="text"  value="<%= ChuyenNgu.get( dhBean.getTrangThaiText(),nnId  ) %>" readonly />
											  	</TD>
											  	
										     </TR>
										     
										     <TR> 
										     <%
										     	String donhangkhac = "";
											  	if(dhBean.getDonhangkhac().equals("1"))
											  		donhangkhac = "( Đơn hàng khác ) ";
											  	else if(dhBean.getDonhangkhac().equals("2"))
											  		donhangkhac = "( Đơn hàng combo ) ";
											 
										     %>
										     <TD class="plainlabel" >Loại đơn hàng</TD>
							                    <TD class="plainlabel" colspan="5" >
											 	 <select style="width: 400px">
											 	 <option value = ""></option>
											 	 <%if (dhBean.getIsPDA() != null && dhBean.getIsPDA().equals("1")) {%>
											 	 <option value = "" selected>Đơn hàng từ PDA  <%=donhangkhac %></option>
											 	 <option value = "">Đơn hàng từ Web  <%=donhangkhac %></option>
											 	 <%} else { %>
											 	 <option value = "" >Đơn hàng từ PDA  <%=donhangkhac %></option>
											 	 <option value = "" selected>Đơn hàng từ Web  <%=donhangkhac %></option>
											 	 <% } %>
											 	 </select>
											  </TD>
										     </TR>
												<%if(dhBean.getDonhangkhac().equals("2")){ %>
										     <TR>
							                	<TD class="plainlabel" ><Font color="blue">Tên combo</Font> </TD>
							                    <TD class="plainlabel" >
											 	  <input type="text"   
                                    					id="tenSpComBo" name="tenSpComBo" value="<%= dhBean.getTenSpComBo() %>"   />
											  	</TD>
											  	
											  	
											  	<TD class="plainlabel" ><Font color="blue">Đơn giá combo</Font>  </TD>
							                    <TD class="plainlabel" >
											 	  <input type="text"   onchange="TinhTienComBo()"
                                    					id="giaSpComBo" name="giaSpComBo" value="<%= dhBean.getGiaSpComBo() %>" onkeyup="Dinhdang(this)"  />
											  	</TD>
											  	
										     </TR>
										     
										     <TR>
							                	<TD class="plainlabel" ><Font color="blue">Số lượng combo</Font> </TD>
							                    <TD class="plainlabel" >
											 	  <input type="text"    onchange="TinhTienComBo()"
                                    					id="soluongSpComBo" name="soluongSpComBo" value="<%= dhBean.getSoluongSpComBo() %>" onkeyup="Dinhdang(this)"  />
											  	</TD>
											  	
											  	
											  	<TD class="plainlabel" ><Font color="blue">Thành tiền combo</Font></TD>
							                    <TD class="plainlabel" >
											 	  <input type="text" 
                                    					id="ttSpComBo" name="ttSpComBo" value="" readonly />
											  	</TD>
											  	
										     </TR>
										     
										    	 <script language="javascript" type="text/javascript">
										    		 TinhTienComBo();
												</script>
												
												<%} %>
												
										<%if(dhBean.getDonhangkhac().equals("0")){
											ResultSet tichluyRs = dhBean.createRsTichluyList();
											%>			
											<TR class="tblightrow" >
												<TD class="plainlabel"  colspan="4" >
                    	
												                    	<a class="button2" href="" id="doanhsoDENGHI" rel="subcontentDOANHSODENGHI">
													           	 							<img style="top: -4px;" src="../images/Edit.png" alt="">Danh sách tích lũy</a>
													           	 							
													           	 						
													           	 				<DIV id="subcontentDOANHSODENGHI"
																					style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: #FFF; width: 700px; padding: 20px; z-index: 100000009;">
																											
																              	
												                    						 
												                    						 <table width="100%" align="center" cellpadding="0" cellspacing="1"  id="tableGhiChu">
													                    						<tr class="tbheader">	
													                    							<th  width="40%"  >Chương trình</th>
													                    							<th  width="50%">Thưởng</th>
													                    							<th  width="10%">Chọn</th>	
													                    						</tr>
													                    						<%if(tichluyRs != null)
													                    							while(tichluyRs.next())
												                    								{
												                    									String DuyetKm_FK = tichluyRs.getString("DuyetKm_FK");
												                    									String diengiai =  tichluyRs.getString("diengiai") ;
												                    									String kq =   tichluyRs.getString("kq");
												                    									
												                    									
												                    									
												                    									
												                    									boolean check =dhBean.getTichluyIdList().contains(DuyetKm_FK);
												                    									
												                    							%>
												                    							<tr >	
												                    								<td   style="text-align: left;" ><input type="text" value="<%=diengiai %>" style="text-align: left;width: 100%; height: 100%" readonly> </td>
												                    								<td   style="text-align: center;"><input type="text" value="<%=kq %>" style="text-align: center;width: 100%" readonly> </td>
													                    							
													                    							<TD align="center"  >
													                    								<%if(check) { %>
																										<input type="checkbox" name="chonTichluyDacBiet" checked value="<%=DuyetKm_FK%>">
																									<%} else { %>
																										<input type="checkbox" name="chonTichluyDacBiet" value="<%=DuyetKm_FK%>">
																									<%} %>
																									</TD>
												                    							</tr>
												                    						
													
												                    							
												                    						
												                    							<%} %>
													                    					 </table>   
													                    		
													           	 				
													                    		<div align="right">
																														<a
																															href="javascript:dropdowncontent.hidediv('subcontentDOANHSODENGHI')"
																															style="font-weight: bold;">Đóng lại</a>
																													</div>            
												                    			</DIV>
												                    			
												                    			
												                    	 <script type="text/javascript">
																										dropdowncontent.init('doanhsoDENGHI', "right-top", 300, "click");									
																										</script>
												                    	
												                    		                   
												                    													
												                    </TD>
											</TR>	
												
										<%} %>				
										  <TR class="tblightrow">
										   <TD  class="plainlabel" colspan = '4'>
										     <%if(!dhBean.getDonhangkhac().equals("0")){ %>
										   		<div id="btnSave">
										  		<a class="button2" href="javascript:saveform()">
													<img style="top: -4px;" src="../images/New.png" alt="">Lưu</a>
											</div>	
										   <%} else { %>
										   <div id="btnApKhuyenMai">
										  		<a class="button2" href="javascript:Apkhuyenmai()">
													<img style="top: -4px;" src="../images/New.png" alt="">Lưu & Áp khuyến mại</a>
											</div>	
											<%} %>												
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
													<TH width="20%">Tên sản phẩm</TH>
													<TH width="7%">Tồn hiện tại</TH>
													<TH width="7%">Số lượng</TH>
													<TH width="7%">DVT</TH>
													<TH width="7%">Đơn giá gốc</TH>
													<TH width="7%" style="display: none">Chiết khấu dữ liệu nền</TH>
													<TH width="7%" style="display: none">Chiết khấu trung tâm</TH>
													<%if( dhBean.getDonhangkhac().equals("2") ){ %>
														<TH width="7%" >Chênh lệch </TH>
													<%} %>
													<!-- <TH width="7%" >Chiết khấu </TH> chiet khau don hang -->
													<TH width="10%">Đơn giá sau CK </TH>
													<TH width="7%" style="display: none">Chiết khấu </TH>
													<%if( dhBean.getDonhangkhac().equals("0") ){ %>
													<TH width="7%">Hàng mẫu</TH>
													<%} %>
													<TH width="9%">Thành tiền </TH>
													<TH width="9%" >CTKM</TH>
												</TR>
									<% 
									int m = 0;
							if(splist != null){
							ISanpham sanpham = null;
							int size = splist.size();
							
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
								    	<input type="text" name="spGiagoc1" value="<%=formatter.format(Double.parseDouble( sanpham.getGiagoc()) ) %>" readonly style="width:100% ;text-align:right">
								    	<input type="hidden" name="spGiagoc" value="<%=formatter.format(Double.parseDouble( sanpham.getGiagoc()) ) %>" readonly style="width:100% ;text-align:right">
								    	<input type="hidden"  name="spBgId" value="<%= sanpham.getBgId() %>" readonly style="width:100%">
								    </TD>
								    
								    <TD align="center" style="display:<%= dhBean.getDonhangkhac().equals("2")? "":"none" %>"  >
								    	<input name="tiengiamtru" type="text" value="<%= formatter.format(Math.round(geso.dms.center.util.Utility.parseDouble(sanpham.getTiengiamtru())))%>"  style="width:100%;text-align:right" onkeyup="DinhDangChenhlech(this)" >
								    </TD>
								    
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhauDLN" type="hidden" value="<%= sanpham.getChietkhauDLN() %>" readonly style="width:100%;text-align:right">
								    </TD>
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhauTT" type="hidden" value="<%= sanpham.getChietkhauTT() %>" readonly style="width:100%;text-align:right">
								    </TD>
								    
								    
								  <%--  <TD align="center" >
								    	<input type="text" name="spchietkhauDH" disabled="disabled" value="<%= dhBean.getChietkhauDH() %>" style="width:100%;text-align:right" >
								    	<input name="spchietkhauDH1"  type="text" value="<%= dhBean.getChietkhauDH() %>"  style="width:100%;text-align:center; color: black;" onkeypress="return keypress(event);">
								    	<input name="spchietkhauDH" type="hidden" value="<%= dhBean.getChietkhauDH() %>"  style="width:100%;text-align:center">
								    </TD> --%>
								    
								    <TD align="center" style="display: none" >
								    	<input type="text" name="spchietkhauDH" value="<%= sanpham.getChietkhauDH() %>" style="width:100%;text-align:right" onkeypress="return keypress(event);">
								    </TD>
								    
								    
								    
								    
								    <TD align = "center" >
								    	<input type="text" name="dongia1"  <%= dhBean.getIsChiNhanh().equals("0")?"disabled=\"disabled\"":"" %>  value="<%=sanpham.getDongia().equals("")?"0":formatter2.format(Double.parseDouble( sanpham.getDongia()))%>" style="width:100% ;text-align:right; color: black;">
										<input type="hidden" name="dongia" readonly style="width:100% ;text-align:right">
								    </TD>
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhau1" type="text" value="<%= sanpham.getChietkhau()%>" disabled="disabled"  style="width:100%;text-align:right; color: black;">
								    	<input name="spchietkhau" type="hidden" value="<%= sanpham.getChietkhau() %>" readonly style="width:100%;text-align:right">
								    </TD>
								    
								     <TD align = "center" style="display:<%= dhBean.getDonhangkhac().equals("0")? "":"none" %>"  >
								    	<input onchange="checkHangMau('<%=m %>')" type="checkbox" id="hangmau"  name="hangmau" value="1" <%= sanpham.getHangmau().equals("1") ? "checked" : ""  %> >
								   		<input id="hangmauVal"  name="hangmauVal" type="hidden"  value="<%= sanpham.getHangmau() %>" readonly style="width:100%">
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
										System.out.println("ChietKhauPage: "+chietkhau);
										%>
										<TR class= 'tblightrow'>
											<TD align="center" ><input type="text" style="width:100%;text-align:right" readonly></TD>
											<TD align="left" ><input type="text" readonly style="width:100%"></TD>
											<TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input name="ckTrakm"  type="text" value="<%= "-"+formatter.format(Math.round(chietkhau)) %>" readonly style="color:red;width:100%;text-align:right"></TD>
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
									        <TD align = "center" ><input type="text" value="<%= tkmSp.getSoluongton() %>" style="color:red; width:100%;text-align:center" readonly></TD>
									        <TD align = "center" ><input name="slgTraKm" type="text" value="<%= formatter.format(Math.round(Double.parseDouble(tkmSp.getSoluong()))) %>" style="color:red;width:100%;text-align:right" readonly></TD>
									        <TD align = "center" ><input name="dvtTrakm" type="text" value="<%= tkmSp.getDonvitinh() %>" readonly style="color:red;width:100%;text-align:center"></TD>
										    <TD align = "center" colspan="5" ><input name="trakmSpScheme" type="text" style="color:red;width:100%" value="<%= tkmSp.getCTKM() %>"  readonly></TD>
										</TR>
								<% count++; }}%>
								
								
								
							<% if(!dhBean.getTrangthai().equals("3")){
									int i=0;
									while(i <= 40){ 
								%>
								<TR class= 'tblightrow'>
									<TD align="center" >
										<input name="masp" type="text" value="" onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" AUTOCOMPLETE="off">
									</TD>
									<TD align="left" >
										<input name="tensp1" type="text" disabled="disabled" value="" style="width:100%; color:black; ">
										<input name="tensp" type="hidden" value="" style="width:100%">
									</TD>
									<TD align = "center" >
								    	<input name="tonkho1" disabled="disabled" type="text"  style="width:100%;text-align:center; color:black; ">
								    	<input name="tonkho" type="hidden"  style="width:100%;text-align:center">
								    </TD>
								    <TD align = "center" >
							        <input name="soluong" type="text" value="" style="width:100%; text-align:right" onkeypress="return keypress(event);" AUTOCOMPLETE="off"></TD>
								     <TD align = "center" >
								    	<input name="donvitinh1" disabled="disabled" type="text"  style="width:100%;text-align:center; color:black; ">
								    	<input name="donvitinh" type="hidden"  style="width:100%;text-align:center">
								    </TD>
								    
								      <TD align = "center" >
								    	<input type="text" name="spGiagoc1" value="" readonly style="width:100% ;text-align:right">
								    	<input type="hidden" name="spGiagoc" value="" readonly style="width:100% ;text-align:right">
								    	<input type="hidden"  name="spBgId" value="" readonly style="width:100%">
								   	 	</TD>
								   	 	
								   	 	 <TD align="center"  style="display:<%= dhBean.getDonhangkhac().equals("2")? "":"none" %>" >
								    	<input name="tiengiamtru" type="text" value=""  style="width:100%;text-align:right" onkeyup="DinhDangChenhlech(this)" >
								    	</TD>
								    
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhauDLN" type="hidden" value="" readonly style="width:100%;text-align:right">
								    </TD>
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhauTT" type="hidden" value="" readonly style="width:100%;text-align:right">
								    </TD>
								    <TD align="center" style="display: none" >
								    	<input type="text" name="spchietkhauDH"  value="" style="width:100%;text-align:right" >
								    </TD>
								    <TD align = "center" >
								    	<input type="text" name="dongia1" <%= dhBean.getIsChiNhanh().equals("0")?"disabled=\"disabled\"":"" %>  onkeyup="Dinhdang(this)"    style="width:100% ;text-align:right; color: black;">
								    	<input type="hidden" name="dongia" readonly style="width:100% ;text-align:right">
								    </TD>
								    <TD align="center" style="display: none" >
								    	<input name="spchietkhau1" type="text" style="width:100%;text-align:right; color: black;">
								    	<input name="spchietkhau" type="hidden" readonly style="width:100%;text-align:right">
								    </TD>
								    
								     <TD align = "center" style="display:<%= dhBean.getDonhangkhac().equals("0")? "":"none" %>" >
								    	<input onchange="checkHangMau('<%=(m+i) %>')" type="checkbox" id="hangmau"  name="hangmau" value="1"  >
								    	<input id="hangmauVal"  name="hangmauVal" type="hidden"  value="0" readonly style="width:100%">
								    </TD>
								    
								    <TD align = "center" >
								    	<input name="thanhtien1" type="text" disabled="disabled"  style="width:100%;text-align:right; color: black;">
								    	<input name="thanhtien" type="hidden" readonly  style="width:100% ;text-align:right">
								    </TD>
								    <TD align = "center" >
								    	<input name="ctkm1" type="text" disabled="disabled" style="width:100%; color: black;">
								    	<input name="ctkm" type="hidden" readonly style="width:100%">
								    </TD>
								</TR>
								<% i++;}} %>
																
								
								</tbody></TABLE>
								<%if(!dhBean.getTrangthai().equals("3")){ %>
							&nbsp;&nbsp;&nbsp;&nbsp;<a class="button2" href="javascript:ThemSanPham()">
	                         <img style="top: -4px;" src="../images/add.png" alt="">Thêm sản phẩm</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<%} %>																																																																																																								
			
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
		$("#smartId").autocomplete("KhachHangList.jsp");		
	});	
</script>
</form>
</BODY>

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
<script language="javascript" type="text/javascript">
thongbao_vuot_ngan_sach();
</script>
