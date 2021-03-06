<%@page import="geso.dms.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.dms.distributor.beans.hoadontaichinhNPP.*" %>
<%@ page  import = "geso.dms.distributor.beans.hoadontaichinhNPP.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<% IErpHoadontaichinhNPP lsxBean = (IErpHoadontaichinhNPP)session.getAttribute("lsxBean"); %>
<% ResultSet khRs = lsxBean.getKhRs(); %>
<% ResultSet nppRs = lsxBean.getNppRs(); %>
<% ResultSet ddhRs = lsxBean.getDondathangRs(); %>
<% ResultSet kbhRs = lsxBean.getKbhRs(); %>

<% 
	String[] spId = lsxBean.getSpId();
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spLoai = lsxBean.getSpLoai();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spDongia = lsxBean.getSpDongia();
	String[] spChietkhau = lsxBean.getSpChietkhau();
	String[] spSCheme = lsxBean.getSpScheme();
	String[] spVat = lsxBean.getSpVat();
	String[] spTienThue = lsxBean.getSpTienthue();
	NumberFormat formater = new DecimalFormat("##,###,###");
	
	String[] dhCk_diengiai = lsxBean.getDhck_diengiai();
	String[] dhCk_giatri = lsxBean.getDhck_giatri();
	String[] dhCk_loai = lsxBean.getDhck_loai();
	
	Hashtable<String, String> sanpham_soluong = lsxBean.getSanpham_Soluong();
	
%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
    NumberFormat formatter = new DecimalFormat("#,###,###"); 
    NumberFormat formatter2 = new DecimalFormat("#,###,###.####"); 
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SalesUpERP/index.jsp");
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

.mySCHME tr,td input
{
	color: red;
}

.mySCHME input
{
	color: red;
}

</style>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.scrollTableBody-1.0.0.js" type="text/javascript">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->
</script>

<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>
<script type="text/javascript">
            $(function() {
                $('#tableDDH').scrollTableBody({rowsToDisplay:4});
            });
        </script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-SpListDonDatHang.js"></script>

<script language="javascript" type="text/javascript">

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
	
	function SuaDonGia(stt)
	{
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("spDongia");
		var chietkhau = document.getElementsByName("spChietkhau");
		var spVat = document.getElementsByName("spVat");
		var tienTHUE = document.getElementsByName("spTienthue");

		if( soluong.item(stt).value != '' && dongia.item(stt).value != '' )
		{
			var _soluong = parseFloat(soluong.item(stt).value.replace(/,/g,""));
			var _dongia = parseFloat(dongia.item(stt).value.replace(/,/g,""));
			
			var _ck = chietkhau.item(stt).value.replace(/,/g,"");
			if(_ck == '')
				_ck = 0;
			
			var _vat = spVat.item(stt).value.replace(/,/g,"");
			if(_vat == '')
				_vat = 0;
			
			tienTHUE.item(stt).value = ( Math.round( _soluong * _dongia ) - _ck ) * _vat / 100 ;
		} 
		
		TinhTien();
	}
	
	function SuaThanhTien(stt)
	{
		//alert('STT: ' + stt );
		/* var nppDNId = document.getElementById("nppDNId").value;

		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("spDongia");
		var chietkhau = document.getElementsByName("spChietkhau");
		var tienTHUE = document.getElementsByName("spTienthue");
		var thanhtien = document.getElementsByName("thanhtien");
		
		if( soluong.item(stt).value != '' && thanhtien.item(stt).value != '' )
		{
			var _soluong = parseFloat(soluong.item(stt).value.replace(/,/g,""));
			var _thanhtien = parseFloat(thanhtien.item(stt).value.replace(/,/g,""));
			
			var _ck = chietkhau.item(stt).value.replace(/,/g,"");
			if(_ck == '')
				_ck = '0';
			
			var _vat = tienTHUE.item(stt).value.replace(/,/g,"");
			if(_vat == '')
				_vat = '0';
			
			//alert('CK: ' + _ck + ' - VAT: ' + _vat + ' - TT: ' + _thanhtien + ' - SO LUONG: ' + _soluong );
			dongia.item(stt).value = ( parseFloat( _thanhtien ) - parseFloat( _vat ) + parseFloat( _ck ) ) / parseFloat( _soluong );
			//alert('DG: ' + dongia.item(stt).value);
		}
		
		TinhTien(); */
	}
	
	function SuaTienThue(stt)
	{
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("spDongia");
		var chietkhau = document.getElementsByName("spChietkhau");
		var tienTHUE = document.getElementsByName("spTienthue");
		var thanhtien = document.getElementsByName("thanhtien");
		
		if( soluong.item(stt).value != '' && thanhtien.item(stt).value != '' )
		{
			var _soluong = parseFloat(soluong.item(stt).value.replace(/,/g,""));
			var _dongia = parseFloat(dongia.item(stt).value.replace(/,/g,""));
			
			var _ck = chietkhau.item(stt).value.replace(/,/g,"");
			if(_ck == '')
				_ck = '0';
			
			var _vat = tienTHUE.item(stt).value.replace(/,/g,"");
			if(_vat == '')
				_vat = '0';
			
			thanhtien.item(stt).value = ( Math.round( _soluong * _dongia ) - _ck ) + _vat;
		}
		
		TinhTien();
	 }
	
	 function TinhTien()
	 {	
			var nppDNId = document.getElementById("nppDNId").value;
			var spMa = document.getElementsByName("spMa");
			var soluong = document.getElementsByName("soluong");
			var dongia = document.getElementsByName("spDongia");
			var chietkhau = document.getElementsByName("spChietkhau");
			var spVat = document.getElementsByName("spVat");
			var thanhtien = document.getElementsByName("thanhtien");
			var spTienthue = document.getElementsByName("spTienthue");
			
			var totalCK = 0;  // totalCK= chiet khau sp + ck don hang
			var tongtien = 0;
			var ck= 0;
			var totalvat=0;
			
			if(nppDNId == '106179') // N???u Nh?? H???i Ph??ng: c??c c???t ????? 4 s??? l???
			{
				for(var i = 0; i < spMa.length; i++)
				{
					if(spMa.item(i).value != "" && dongia.item(i).value != "" && soluong.item(i).value != "" )
					{
						/* var ck_sp = chietkhau.item(i).value.replace(/,/g,"");
						if(ck_sp == '')
							ck_sp = '0';
						
						var tt = (parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")));
						var tt_coCK = (parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,""))) - parseFloat(ck_sp);
						
						tt = Math.round( tt + parseFloat(vat_sp) );
						
						var vat_sp = spTienthue.item(i).value.replace(/,/g,"");
						if(vat_sp == '')
							vat_sp = parseFloat(tt_coCK) * spVat.item(i).value.replace(/,/g,"")/100;
						
						//alert(spTienthue.item(i).value.replace(/,/g,""));
						thanhtien.item(i).value = DinhDangTien( tt + vat_sp );
						
						tongtien += tt;
						ck =  parseFloat(ck) + parseFloat(ck_sp);
						totalvat += vat_sp; */
						
						var ck_sp = chietkhau.item(i).value.replace(/,/g,"");
						if(ck_sp == '')
							ck_sp = '0';
						
						var tt = Math.round(parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")));
						var tt_coCK = Math.round(parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,""))) - parseFloat(ck_sp);
						
						var vat_sp = spTienthue.item(i).value.replace(/,/g,"");
						if(vat_sp == '')
							vat_sp = parseFloat(tt_coCK) *  parseFloat ( spVat.item(i).value.replace(/,/g,"") ) / 100;
						
						thanhtien.item(i).value = DinhDangTien( tt );
						
						tongtien += parseFloat( tt);
						ck =  parseFloat(ck) + parseFloat(ck_sp);
						totalvat += parseFloat(vat_sp);
						
					}
				}
							 
				totalCK = parseFloat(ck);
				
				document.getElementById("tongtien").value = DinhDangTien(tongtien);
				document.getElementById("tongchietkhau").value = DinhDangTien(totalCK);
				//document.getElementById("tiensauCK").value = DinhDangTien(parseFloat(tongtien)- parseFloat(totalCK));
				
				document.getElementById("tienvat").value = DinhDangTien(parseFloat(totalvat));
				document.getElementById("tiensauvat").value = DinhDangTien( (parseFloat(tongtien)- parseFloat(totalCK)) + parseFloat(totalvat));
		
			}
			else
			{
				for(var i = 0; i < spMa.length; i++)
				{
					if(spMa.item(i).value != "" && dongia.item(i).value != "" && soluong.item(i).value != "" )
					{
						var ck_sp = chietkhau.item(i).value.replace(/,/g,"");
						if(ck_sp == '')
							ck_sp = '0';
						
						var tt = Math.round(parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")));
						var tt_coCK = Math.round(parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,""))) - parseFloat(ck_sp);
						//thanhtien.item(i).value = DinhDangTien(tt);
						//alert(thanhtien.item(i).value);
						
						//var vat_sp = parseFloat(tt_coCK) * spVat.item(i).value.replace(/,/g,"")/100;
						var vat_sp = spTienthue.item(i).value.replace(/,/g,"");
						if(vat_sp == '')
							vat_sp = parseFloat(tt_coCK) *  parseFloat ( spVat.item(i).value.replace(/,/g,"") ) / 100;
						
						//alert('TT: ' + tt);
						thanhtien.item(i).value = DinhDangTien( tt );
						
						//alert('TT truoc: ' + parseFloat(tt) + ' -- VAT: ' + parseFloat(vat_sp) + '  -- CK SP: ' + parseFloat(ck_sp) );
						//tt = Math.round( parseFloat(tt) + parseFloat(vat_sp) - parseFloat(ck_sp) );
						tongtien += parseFloat( tt);
						
						ck =  parseFloat(ck) + parseFloat(ck_sp);
						
						totalvat += parseFloat(vat_sp);
					}
				}
				
				//alert('TONG TIEN: ' + tongtien + "  -- TONG VAT: " +  totalvat);
				totalCK = parseFloat(ck);
				
				document.getElementById("tongtien").value = DinhDangTien(Math.round(tongtien));
				document.getElementById("tongchietkhau").value = DinhDangTien(Math.round(totalCK));
				//document.getElementById("tiensauCK").value = DinhDangTien(Math.round(parseFloat(tongtien)- parseFloat(totalCK)));
				
				document.getElementById("tienvat").value = DinhDangTien(Math.round(parseFloat(totalvat)));
				document.getElementById("tiensauvat").value = DinhDangTien(Math.round((parseFloat(tongtien)- parseFloat(totalCK)) + parseFloat(totalvat)));
			}
	
	 }
	
	 function saveform()
	 {	
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function chotform()
	 {	
		 document.getElementById("btnSave2").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'chot';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['hctmhForm'].action.value='submit';
	     document.forms["hctmhForm"].submit();
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
		
		//UPDATE
		Update_SoLuong();
		
	}	
	
	 function loadcontent()
	 {
		document.forms['hctmhForm'].action.value='reload';
	    document.forms["hctmhForm"].submit();
	 }
	 function goBack()
	 {
		 window.history.go(-1);
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

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpHoadontaichinhNPPUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="nppDNId"  id="nppDNId" value='<%= lsxBean.getnppDangnhap() %>'>
<input type="hidden" name="id" value='<%= lsxBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Qu???n l?? b??n h??ng > B??n h??ng cho NPP/ ?????i l?? > Xu???t H??TC > C???p nh???t
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Ch??o m???ng b???n <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
  	
    	<%-- <A href= "../../ErpHoadontaichinhNPPSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A> --%>
        	
            <A>
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset" onclick="goBack()">
        	</A>
        	
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Ch???nh s???a th??ng tin YC" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
        <span id="btnSave2">
	        <A href="javascript:chotform()" >
	        	<IMG src="../images/Chot.png" title="Duy???t y??u c???u" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
    	
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Th??ng b??o</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= lsxBean.getMsg() %></textarea>
		         <% lsxBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
     <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Xu???t h??a ????n t??i ch??nh </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="130px" class="plainlabel" valign="top">Ng??y xu???t h??a ????n </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" class="days" readonly="readonly"  name="ngayxuat" value="<%= lsxBean.getNgayxuatHD() %>"/>
                    </TD>
                    
                    <TD width="150px" class="plainlabel" valign="top">Ng??y ghi nh???n c??ng n??? </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" class="days" readonly="readonly"  name="ngayghinhan" value="<%= lsxBean.getNgayghinhanCN() %>"/>
                    </TD>
                </TR>
         
                
                <TR>
                	<TD class="plainlabel">K?? hi???u h??a ????n</TD>
                	<TD class="plainlabel" valign="top">
                    	<input type="text"  name="kyhieuhoadon" value="<%= lsxBean.getKyhieuhoadon() %>"/>
                    </TD>
                    
                    <TD class="plainlabel">S??? h??a ????n</TD>
                	<TD class="plainlabel" valign="top">
                    	<input type="text"   name="sohoadon" value="<%= lsxBean.getSohoadon().trim() %>"/>
                    </TD>
                </TR>
                
                 <TR>
                     <TD class="plainlabel" >Lo???i xu???t HD </TD>
                   <TD class="plainlabel">
                   			<select name = "loaiXHD" class="select2" style="width: 200px" onchange="submitform();" >
                   		  <%if(lsxBean.getLoaiXHD().equals("0")){ %>
                    		<option value="" ></option>
                    		<option value="1" >Xu???t cho kh??ch h??ng ETC</option>
                    		<option value="0" selected="selected">Xu???t cho ?????i t??c</option>
                    		<%}else if (lsxBean.getLoaiXHD().equals("1")){%>
                    		<option value=""  ></option>
                    		<option value="1" selected="selected">Xu???t cho kh??ch h??ng ETC</option>
                    		<option value="0" >Xu???t cho ?????i t??c</option>
                    		<%} else if (lsxBean.getLoaiXHD().equals("")){%>
                    		<option value=""  selected="selected"></option>
                    		<option value="1" >Xu???t cho kh??ch h??ng ETC</option>
                    		<option value="0" >Xu???t cho ?????i t??c</option>
                    		<%} %>
                    	</select>
                   </TD>
                   
                  <%if(lsxBean.getLoaiXHD().equals("1")){ %>
                	<TD class="plainlabel" >Kh??ch h??ng ETC </TD>
                    <TD class="plainlabel" >
                    	<select name = "khId" class="select2" style="width: 200px" onchange="submitform();" >
                    		<option value=""> </option>
                        	<%
                        		if(khRs != null)
                        		{
                        			try
                        			{
                        			while(khRs.next())
                        			{  
                        			if( khRs.getString("pk_seq").equals(lsxBean.getKhId())){ %>
                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("ten") %></option>
                        		 <% } } khRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD> 
                    <%}else if (lsxBean.getLoaiXHD().equals("0")){ %>
                    <TD class="plainlabel" >?????i t??c </TD>
                    <TD class="plainlabel" >
                    	<select name = "nppId" class="select2" style="width: 200px" onchange="submitform();" >
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
                    <%}else if (lsxBean.getLoaiXHD().equals("")){ %>
                     <TD class="plainlabel" colspan="3"></TD>
                    <%} %>
                                 	                   
                </TR>
                <TR>
                	<TD class="plainlabel" >H??nh th???c thanh to??n </TD>
                    <TD class="plainlabel">
                    	<select name="hinhthuctt" id="hinhthuctt" style="width: 200px;" >
							    <% 
							   String[] mangchuoi=new String[]{"TM/CK","Chuy???n kho???n","Ti???n m???t"};
							   for(int k=0;k < mangchuoi.length;k ++ ){
								   
								   if(lsxBean.getHinhthucTT().equals(mangchuoi[k])) {
									   %>
									    	<option value="<%=mangchuoi[k] %>" selected="selected"><%=mangchuoi[k] %> </option>
									   <%
								   }else{
									   %>
								    	<option value="<%=mangchuoi[k] %>" ><%=mangchuoi[k] %> </option>
								  		 <%  
								   }
							   }
							  %>
												   
						</select>
                    </TD> 
                  	<TD class="plainlabel" > K??nh b??n h??ng </TD>
                    <TD class="plainlabel"  >
                    	<select name = "khId" class="select2" style="width: 200px" disabled="disabled" >
                    		<option value=""> </option>
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
                    <TD class="plainlabel" >Ng?????i mua h??ng </TD> 
                    <TD class="plainlabel" >
                     	<input type="text" name="nguoimuahang"  id= "nguoimuahang" value="<%=lsxBean.getNguoimua() %>" />
				    </TD>
                    
                     <%if(lsxBean.getnppDangnhap().equals("106210")){  // N???U CN HCM: khi c?? check In Ng?????i mua h??ng --> hi???n trong phi???u in  %>
                    <TD class="plainlabel" >In Ng?????i mua h??ng</TD>
                    <TD class="plainlabel" >
                    	<%if(lsxBean.getInNguoimua().equals("1")){%>                   	
                    		 <input type="checkbox"  name="innguoimuahang" value="1" checked="checked" />
                    	 <%}else{ %>
                    		 <input type="checkbox"  name="innguoimuahang" value="0"  />
                         <%}%>
                    </TD>     
                    <%}else{ %>
                    <TD class="plainlabel" ></TD>
                    <TD class="plainlabel" >
                    	<input type="hidden"  name="innguoimuahang" value="1"  />
                    </TD>
                    <%} %>
                    
                </TR>
                 <TR>
                	<TD class="plainlabel" >Ghi ch?? </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 500px" />
                    </TD>
                </TR>
                      <TR>
                	<TD class="plainlabel" >M?? v??? vi???c </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="mavv" value="<%= lsxBean.getMavuviec() %>" style="width: 500px" />
                    </TD>
                </TR>
                <TR>
                	<TD class="plainlabel" >????n ?????t h??ng </TD>
                    <TD class="plainlabel" colspan="2" >
                    	<fieldset  >
							<legend> Ch???n ????n ?????t h??ng</legend>
							 <table id="tableDDH" width="100%">
								<TR class="tbheader">
						             <TH align="center" width="30%">S??? ????n h??ng</TH>
						             <TH align="left" width="50%"> Ng??y </TH>
						             <TH align="center" width="20%"> Ch???n </TH>
               					</TR>
               				
               					<tbody>  
               					    							
             					<%
         							if(ddhRs != null)
         							{
         								while(ddhRs.next()){ %>
           								<tr class= 'tblightrow'>
           									<td>
           										 <%=ddhRs.getString("pk_seq") %> 		
           									</td>
           									<td>                   										
           										 <%=ddhRs.getString("NgayDonHang") %> 	
           									</td>
  										<% 
   										 if(lsxBean.getDondathangId().contains(ddhRs.getString("pk_seq").trim())){%>
       										<TD align="center"><input type ="radio" checked="checked" name ="ddhid" value ="<%= ddhRs.getString("pk_seq") %>" onchange="loadcontent();" ></TD>
       									<%}else{ %>
       										<TD align="center"><input type ="radio" id="ddhid" name ="ddhid" value ="<%= ddhRs.getString("pk_seq") %>" onchange="loadcontent();" ></TD>
       									<%}%>
   										</tr>
   									<% } ddhRs.close(); } %>
   									</tbody>                     								                  							
                   			</table>
                   		</fieldset>
                    </TD> 
                    
                    <TD class="plainlabel" colspan="2" valign="bottom" >
                		<table class="plainlabel" width="100%"  style="padding-top:0 ; margin-top:0">                 								                   								                   								
               				<tr>
               					<td>T???ng ti???n </td>
               					<td>
               						<input type="text" style="text-align:right" id="tongtien" name="tongtien" value="<%= lsxBean.getTongtienBVAT() %>" >
               					</td>
               				</tr>
               				<tr>
               					<td>Ti???n chi???t kh???u </td>
               					<td>
               						<input type="text" style="text-align:right" id="tongchietkhau" name="tongchietkhau" value="<%= lsxBean.getTongCK() %>">
               					</td>
               				</tr>                 				  
               				
               				<tr>
               					<td>Ti???n vat </td>
               					<td>
               						<input type="text" style="text-align:right" id="tienvat" name="tienvat" value="<%= lsxBean.getTongVAT() %>">
               					</td>
               				</tr>
               				
               				<tr>
               					<td>Ti???n th???c thu </td>
               					<td>
               						<input type="text" style="text-align:right" id="tiensauvat" name="tiensauvat" value="<%= lsxBean.getTongtienAVAT() %>">
               					</td>
               				</tr>
                  		</table>
                  	</TD>  
                </TR>
                
               
             <!--    <TR class="plainlabel">
					<TD colspan="4">
						<a class="button2" href="javascript:loadcontent()">
                        <img style="top: -4px;" src="../images/button.png" alt="">Xem chi ti???t</a>&nbsp;&nbsp;&nbsp;&nbsp;
					</TD>
				</TR> -->
            </TABLE>
			<hr />
			
			 <table cellpadding="0px" cellspacing="1px" width="100%">
				<tr class="tbheader">
					<th align="center" width="15%" >M?? s???n ph???m</th>
					<th align="center" width="25%" >T??n s???n ph???m</th>
					<th align="center" width="10%" >????n v???</th>
					<th align="center" width="10%" >S??? l?????ng </th>
					<th align="center" width="10%" >????n gi??</th>
					<th align="center" width="8%" >VAT</th>
					<th align="center" width="8%" >Chi???t kh???u</th>
					<th align="center" width="8%" >Ti???n thu???</th>
					<th align="center" width="10%" >Th??nh ti???n</th>
					<th align="center" width="1%" style="display: none;" >Scheme</th>
					
				</tr>
				
				<%
					int count = 0;
					if(spMa != null)
					{
						for(int i = 0; i < spMa.length; i++)
						{%>
					
						<tr>
							<td>
								<input type="hidden" name="spId" value="<%= spId[i] %>" style="width: 100%"   > 
								<%-- <input type="hidden" name="spLoai" value="<%= spLoai[i] %>" style="width: 100%"   >  --%>
								<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%"  readonly="readonly"  > 
							</td>
							<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%" readonly="readonly"> </td>
							<td>
								 <input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%; text-align: right;" readonly="readonly">	 						
							</td>
							<td>
								<input type="text" name="soluong" value="<%= formatter.format(Double.parseDouble(spSoluong[i])) %>" style="width: 70%; text-align: right;" readonly="readonly">
								
								
									<a href="" id="scheme_<%= spMa[i] %>" rel="subcontent_<%= spMa[i] %>">
			           	 				&nbsp; <img alt="Ch???n s??? l??" src="../images/vitriluu.png"></a>
			           	 		
		           	 		 		<DIV id="subcontent_<%= spMa[i] %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					                             background-color: white; width: 450px; max-height:300px; overflow:auto; padding: 4px;">
					                    <table width="95%" align="center">
					                    	<tr>
					                    		<td ><b>T???ng xu???t</b></td>
					                    		<td colspan="3" > <input type="text" name="soluong2" value="<%= spSoluong[i] %>" style="width: 100px; text-align: right;" readonly="readonly" >	</td>
					                    	</tr>
					                        <tr>
					                            <th width="100px" style="font-size: 11px">S??? l?????ng</th>
					                            <th width="100px" style="font-size: 11px">S??? l??</th>
					                            <th width="100px" style="font-size: 11px">Ng??y h???t h???n</th>
					                            <th width="100px" style="font-size: 11px">T???n kho</th>
					                        </tr>
			                            	<%
			                            		ResultSet spRs = lsxBean.getSoloTheoSp(spId[i], spDonvi[i], spSoluong[i]);  
				                        		if(spRs != null)
				                        		{
				                        			while(spRs.next())
				                        			{
				                        				String tudeXUAT = "";
				                        				//if(spRs.getString("tuDEXUAT").trim().length() > 0)
				                        					//tudeXUAT = formater.format(spRs.getDouble("tuDEXUAT"));
				                        				String temID = spId[i] + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYHETHAN");
				                        			%>
				                        			
				                        			<tr>
				                        				<td>
				                        				<% if(sanpham_soluong.get( temID ) != null ) { %>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= spMa[i] %>_spSOLUONG" value="<%= formater.format(Double.parseDouble( sanpham_soluong.get( temID ))) %>" onkeyup="CheckSoLuong_DeXuat(this);" >
				                        				<% } else { %>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= spMa[i] %>_spSOLUONG" value="<%= tudeXUAT  %>" onkeyup="CheckSoLuong_DeXuat(this);" >
				                        				<% } %>
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= spMa[i] %>_spSOLO" value="<%= spRs.getString("SOLO") %>" readonly="readonly">
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= spMa[i] %>_spNGAYHETHAN" value="<%= spRs.getString("NGAYHETHAN") %>" readonly="readonly">
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= spMa[i] %>_spTONKHO" value="<%= formater.format(spRs.getDouble("AVAILABLE")) %>" readonly="readonly">
				                        				</td>
				                        			</tr>
				                        			
				                        		 <% } } %>
				                        		 
					                     </table>
					                     <div align="right">
					                     	<label style="color: red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('subcontent_<%= spMa[i] %>')" > ????ng l???i </a>
					                     </div>
						            </DIV> 
						            
						            <script type="text/javascript">
						            	dropdowncontent.init('scheme_<%= spMa[i]  %>', "left-top", 300, "click");
						            </script>
								
															
							</td>
							<td>
								<input type="text" name="spDongia" value="<%= formatter2.format(Double.parseDouble(spDongia[i])) %>" style="width: 100%; text-align: right;"  onkeyup="SuaDonGia(<%= i %>);" >							
							</td>
							<td>
								<input type="text" name="spVat" value="<%= formatter.format(Double.parseDouble(spVat[i]))%>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<%if(lsxBean.getnppDangnhap().equals("106179")){ %>
								<input type="text" name="spChietkhau" value="<%= formatter2.format(Double.parseDouble(spChietkhau[i])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
								<%}else{ %>
								<input type="text" name="spChietkhau" value="<%= formatter.format(Double.parseDouble(spChietkhau[i])) %>" style="width: 100%; text-align: right;" readonly="readonly">
								<%} %>
							</td>
							<%-- <td>
								<%if(lsxBean.getnppDangnhap().equals("106179")){ %>
								<input type="text" name="spTienthue" value="<%= formatter2.format(Double.parseDouble(spTienThue[i])) %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event); " onkeyup="SuaTienThue(<%= i %>);" >							
								<%}else{ %>
								<input type="text" name="spTienthue" value="<%= formatter.format(Double.parseDouble(spTienThue[i])) %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event); " onkeyup="SuaTienThue(<%= i %>);" >
								<%} %>
							</td> --%>
							<td>
								<%if(lsxBean.getnppDangnhap().equals("106179")){ %>
								<input type="text" name="spTienthue" value="<%= formatter2.format(Double.parseDouble(spTienThue[i])) %>" style="width: 100%; text-align: right;" readonly="readonly" >							
								<%}else{ %>
								<input type="text" name="spTienthue" value="<%= formatter.format(Double.parseDouble(spTienThue[i])) %>" style="width: 100%; text-align: right;" onkeyup="SuaTienThue(<%= i %>);"  >
								<%} %>
							</td>
							<td>
								<%if(lsxBean.getnppDangnhap().equals("106179")){ %>
								<input type="text" name="thanhtien" value="<%= formatter.format(Double.parseDouble(spSoluong[i])*Double.parseDouble(spDongia[i])) %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event); " readonly="readonly" >							
								<%}else{ %>
								<input type="text" name="thanhtien" value="<%= formatter2.format(Double.parseDouble(spSoluong[i])*Double.parseDouble(spDongia[i])) %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event); " readonly="readonly" >
								<%} %>
							</td>
							<td style="display: none;" >
								<input type="text" name="scheme"  value="<%= spSCheme[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event); " >							
							</td>
						</tr>	
							
					<% count ++; } } %>
			
			</table> 
				
            </div>
     </fieldset>	
    </div>
</div>

<script type="text/javascript">
	TinhTien();
</script>

<%
	try
	{
		lsxBean.DBclose(); 
	}
	catch(Exception er){ }

	} %>
</form>
</BODY>
</html>