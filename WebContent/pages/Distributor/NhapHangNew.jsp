<%@page import="geso.dms.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.dms.distributor.beans.nhaphang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<% INhaphang lsxBean = (INhaphang)session.getAttribute("nhaphang"); %>
<% ResultSet dvkdRs = lsxBean.getDvkdRs(); %>
<% ResultSet kbhRs = lsxBean.getKbhRs(); %>
<% ResultSet nppRs = lsxBean.getNppRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<%-- <% ResultSet dvtRs = lsxBean.getDvtRs(); %>
<% ResultSet congnoRs = lsxBean.getCongnoRs(); %> --%>

<% 
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spTonkho = lsxBean.getSpTonkho();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spGianhap = lsxBean.getSpGianhap();
	//String[] spSCheme = lsxBean.getSpScheme();
	String[] spVat = lsxBean.getSpVat();
	String[] spChietkhau = lsxBean.getSpChietkhau();
	String[] spGiagoc = lsxBean.getSpGiagoc();
	String[] spBgId = lsxBean.getSpBgId();
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

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-SpListNhapHang.js"></script>

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
		};
	}
	
	function replaces()
	{
		var spHansd = document.getElementsByName("spHansd");
		var spMa = document.getElementsByName("spMa");
		var spTen = document.getElementsByName("spTen");  
		var donvi = document.getElementsByName("donvi");
		var soluong = document.getElementsByName("soluong");
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
					
					/* spTonkho.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);*/
					
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
				dongia.item(i).value = "";
				thanhtien.item(i).value = "";	
				spvat.item(i).value = "";
				spTonkho.item(i).value = "";
				spCK.item(i).value = "";
			}
		}	
		
		TinhTien();
		setTimeout(replaces, 300);
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
			//var spScheme = document.getElementsByName("scheme");
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
				/* if(spScheme.item(i).value != "" )
				{
					var _ck = thanhtien.item(i).value.replace(/,/g,"");
					if(_ck == '')
						_ck = '0';
					
					tongtienCKKM += -1*parseFloat(_ck);
				} */
			}
			tongtien = tongtien;
			
			var chietkhau = '0';
			document.getElementById("txtBVAT").value = DinhDangTien(tongtien);
			var tongtienSAUCKKM = parseFloat(tongtien) - parseFloat (tongtienCKKM);	
			var ckNguyenDon = 0;
			
			/* var ckNguyenDon = 0;
			if(chietkhau !='0')
			{
				ckNguyenDon =  tongtienSAUCKKM * parseFloat (chietkhau)/100.0;
			}
			document.getElementById("txtTongCK").value = DinhDangTien(ckNguyenDon); */
			

			var TongTienTruocVat = tongtienSAUCKKM -ckNguyenDon;
			
			document.getElementById("txtTongSauCK").value = DinhDangTien(TongTienTruocVat);
			
			tt_vat=TongTienTruocVat* ( vatTong/100.0 ) ;
			document.getElementById("txtVAT").value = DinhDangTien(tt_vat);
			
			
			var tongtienSAUVAT = TongTienTruocVat + tt_vat;
	
			document.getElementById("txtSauVAT").value = DinhDangTien(tongtienSAUVAT);
			
			
			var tiengiam = '0'; //document.getElementById("txtTiengiam").value.replace(/,/g,"");
			
			//document.getElementById("txtThanhToan").value = DinhDangTien(parseFloat(tongtienSAUVAT) - parseFloat(tiengiam));
			
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
	 function CapNhatGia(e, stt)
	 { 
		 	//alert(e.value);
		 	
		 	/* var spMa = document.getElementsByName("spMa");
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
	 
	 function saveform()
	 {	
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
			   document.forms["hctmhForm"].dataerror.value="Ch??a t??m file upload l??n. Vui l??ng ch???n file c???n upload.";
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

<form name="hctmhForm" method="post" action="../../NhaphangUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="loaidonhang" value='<%= lsxBean.getLoaidonhang() %>'>
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Qu???n l?? t???n kho > Mua h??ng t??? nh?? cung c???p > Nh???p h??ng > T???o m???i
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Ch??o m???ng b???n  <%= lsxBean.getNppTen() %> &nbsp;
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
        </span>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Th??ng b??o</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= lsxBean.getMessage() %></textarea>
		         <% lsxBean.setMessage(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">????n ?????t h??ng </legend>
        	<div style="float:none; width:100%" align="left">
            
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                 <TR>
                    <TD width="120px" class="plainlabel" valign="top">Ng??y xu???t kho </TD>
                    <TD class="plainlabel" valign="top" style="width: 250px;"  >
                    	<input type="text" readonly="readonly" class="days"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/>
                    </TD>
                
                    <TD width="120px" class="plainlabel" valign="top">Ng??y nh???n h??ng </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<input type="text" class="days" readonly="readonly"  name="ngaynhan" value="<%= lsxBean.getNgaynhap() %>"/>
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" valign="top">????n v??? kinh doanh </TD>
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
                    <TD class="plainlabel" valign="top">K??nh b??n h??ng </TD>
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
                	<TD class="plainlabel" valign="top">Nh?? ph??n ph???i </TD>
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
                    <TD class="plainlabel" valign="top">Kho ?????t </TD>
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
                        	<option value = ""> </option>
                    	</select>
                    </TD>         	
                </TR>
                
                <TR style = "display:none">
                    <TD class="plainlabel" >T???ng gi?? tr??? </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" readonly="readonly"  id="txtBVAT" value="" style="text-align: right;"  />
                    	<input type="hidden"  value="0" id="txtPTChietKhau" style="text-align: right;" name="ptChietkhau" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" />
                    </TD>
                    
                    <TD class="plainlabel"  > </td>
                    <TD class="plainlabel"  >
                      </TD>
                    
                </TR>
                
                <TR  >
                    
                    <!-- <TD class="plainlabel" valign="top">% Chi???t kh???u DLN</TD>
                    <TD class="plainlabel" valign="top"  >
                    	<input type="hidden" readonly="readonly"  value="0" name="ptChietKhau" id="ptChietKhau" style="text-align: right;" />
                    </TD> -->
                    	
                    <TD class="plainlabel" valign="top">T???ng ti???n tr?????c VAT </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text" readonly="readonly"  value="" id="txtTongSauCK" style="text-align: right;" />
                    </TD>
                    <TD class="plainlabel" valign="top">Ghi ch?? </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text"  value="" id="ghichu"  name="ghichu" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" valign="top">Ti???n VAT </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text" value="" id="txtVAT" style="text-align: right;" name="ptVat" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" readonly='readonly' />
                    </TD>
                    	
                    <TD class="plainlabel" valign="top">T???ng ti???n sau VAT </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly"  value="" id="txtSauVAT" style="text-align: right;" />
                    </TD>
                </TR>
                
               <!--  <TR>
                	
                	<TD class="plainlabel" valign="top" >T???ng ti???n chi???t kh???u </TD>
                    <TD class="plainlabel" valign="top" >
                    <input type="text" readonly="readonly"  value="" id="txtTongCK" style="text-align: right;" /></TD>
                    
                
                	<TD class="plainlabel" >Ti???n gi???m </TD>
                    <TD class="plainlabel" >
                    	<input type="hidden"  name="txtTiengiam" id="txtTiengiam" value='0' onkeyup="Dinhdang(this);" style="text-align: right;" />
                    </TD>
                </TR> -->
                
                <%-- <TR>
                    <TD class="plainlabel" valign="top">C??ng n??? </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" value="<%= lsxBean.getCongno() %>" id="txtCongno" style="text-align: right;" name="ptVat" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" readonly='readonly' />
                    </TD>
                    
                    <TD class="plainlabel" valign="top" style="color: red;" >S??? ti???n thanh to??n</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly"  value="" id="txtThanhToan" style="text-align: right;" />
                    </TD>
                </TR> --%>
                
                <%-- <TR>
                	<TD class="plainlabel" >Ghi ch?? </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 700px;" />
                    </TD>
                </TR> --%>
                
               <!-- 	<tr class="plainlabel" style="display: none;">
			  	  	<td >
			  	  		<INPUT type="file" name="filename" size="40" value=''> 
			  	  	</td> 
			  	  	<td colspan="3">
			  			&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:upload()">
						<img style="top: -4px;" src="../images/button.png" alt="">UpLoad File</a>							
			  	 	</td>
			  	</tr> -->
                
                <!-- <TR  >
				   <TD  class="plainlabel" colspan = '4'>
				   <div id="btnApKhuyenMai">
				  		<a class="button2" href="javascript:Apkhuyenmai()">
							<img style="top: -4px;" src="../images/New.png" alt="">L??u & ??p khuy???n m???i </a>
					</div>									
				  </TD>
				</TR> -->
            </TABLE>
			<ul class="tabs">
         		<li><a href="#">Th??ng tin ????n h??ng</a></li>
          	</ul>
             			
            <div class="panes">
			<div>
			
				<table cellpadding="0px" cellspacing="1px" width="100%">
					<tr class="tbheader">
						<th align="center" width="3%" >STT</th>
						<th align="center" width="12%" >M?? s???n ph???m</th>
						<th align="center" width="25%" >T??n s???n ph???m</th>
						<th align="center" width="10%" >????n v???</th>
						<!-- <th align="center" width="10%" >T???n kho</th> -->
						<th align="center" width="10%" >S??? l?????ng</th>
						<th align="center" width="10%" style="display: none;" >????n gi?? g???c</th>
						<th align="center" width="10%" >????n gi??</th>
						<th align="center" width="10%" >% VAT</th>
						<th align="center" width="10%" >Th??nh ti???n</th>
						<!-- <th align="center" width="10%" >CTKM</th> -->
					</tr>
					<%
						int count = 0;
						if(spMa != null)
						{
							for(int i = 0; i < spMa.length; i++)
							{%>
						
							<tr>
							<td style="text-align: center;" > <%= i + 1 %> </td>
							
								<td> <input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > </td>
								<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%" readonly="readonly"> </td>
								<td>
									<%-- <% if(spSCheme[i].trim().length() <= 0) { %>
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
									 <% } else { %> --%>
									 	<input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%" readonly="readonly">
									 <%-- <% } %> --%>
								</td>
								
								<%-- <td> <input type="hidden" name="spTonkho" value="<%= spTonkho[i] %>" style="width: 100%" readonly="readonly"> </td> --%>
								
								<td> 
								<input type="hidden" name="spTonkho" value="<%= spTonkho[i] %>" style="width: 100%" readonly="readonly">
								<input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
								
								<%-- <td style="display: none;"> 
									<% if(spSCheme[i].trim().length() <= 0) { %> 
										<input type="text" name="spGiagoc" value="<%= spGiagoc[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > 
									<% } else {  %>
										<input type="text" name="spGiagoc" value="0" style="width: 100%; text-align: right;" readonly="readonly" > 
									<% } %>
								</td> --%>
								<td> <input type="text" name="dongiaBVAT" value="" style="width: 97%; text-align: right;" readonly="readonly" > </td>
								<td style = "display:none">
								<input type="text" name="dongia" value="<%= spGianhap[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > 
								</td>
								<%-- <td style="display:none"> <input type="text" name="chietkhau" value="<%= spChietkhau[i] %>" style="width: 96%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td> --%>
								<td> <input type="text" name="spvat" value="<%= spVat[i] %>" style="width: 100%; text-align: right;" readonly="readonly"> 
								<input type="hidden" name="spBgId" value="<%=spBgId[i] %>" style="width: 100%; text-align: right;" readonly="readonly" >
								</td>
								<td> 
								<input type="text" name="thanhtien" value="" style="width: 100%; text-align: right;" readonly="readonly" >
								</td>
								<%-- <td> <input type="text" name="scheme" value="<%= spSCheme[i] %>" style="width: 100%;" readonly="readonly"  >  
								</td>--%>
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
						 	</td>
						 	<!-- <td> <input type="text" name="spTonkho" value="" style="width: 100%" readonly="readonly"> </td> -->
							<td> 
							<input type="hidden" name="spTonkho" value="" style="width: 100%" readonly="readonly">
							<input type="text" name="soluong" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
							<td style="display: none;"> <input type="text" name="spGiagoc" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							<td> <input type="text" name="dongiaBVAT" value="" style="width: 97%; text-align: right;" readonly="readonly" > </td>
							<td style = "display:none"> <input type="text" name="dongia" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							<td style="display:none"> <input type="text" name="chietkhau" value="" style="width: 96%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
							<td> <input type="text" name="spvat" value="" style="width: 100%; text-align: right;" readonly="readonly"  > </td>
							<td> <input type="text" name="thanhtien" value="" style="width: 100%; text-align: right;" readonly="readonly" > 
							<input type="hidden" name="spBgId" value="" style="width: 100%; text-align: right;" readonly="readonly" >
							</td>
							<!-- <td> <input type="text" name="scheme" value="" style="width: 100%; text-align: right;" readonly="readonly" >
							</td> --> 
						</tr>	
					<% } %>	
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
		//dvtRs.close();
		lsxBean.DBclose(); 
	}
	catch(Exception er){ }

	} %>
</form>
</BODY>
</html>