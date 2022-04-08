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

<% IErpDondathangDoitac lsxBean = (IErpDondathangDoitac)session.getAttribute("lsxBean"); %>
<% ResultSet dvkdRs = lsxBean.getDvkdRs(); %>
<% ResultSet kbhRs = lsxBean.getKbhRs(); %>
<% ResultSet nppRs = lsxBean.getKhRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet dvtRs = lsxBean.getDvtRs(); %>

<% 
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spGianhap = lsxBean.getSpGianhap();
	String[] spChietkhau = lsxBean.getSpChietkhau();
	String[] spVat = lsxBean.getSpVat();
	String[] spscheme = lsxBean.getSpScheme();
	NumberFormat formater = new DecimalFormat("##,###,###");
	NumberFormat formater2 = new DecimalFormat("##,###,###.##");
	Hashtable<String, String> sanpham_soluong = lsxBean.getSanpham_Soluong();
%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
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
		var dongia = document.getElementsByName("dongia");
		var thanhtien = document.getElementsByName("thanhtien");
		
		var trongluong = document.getElementsByName("spTrongLuong");
		var thetich = document.getElementsByName("spTheTich");
		var spQuyDoi = document.getElementsByName("spQuyDoi");
		
		var spvat = document.getElementsByName("spvat");
		
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
					
					/* trongluong.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					thetich.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					spQuyDoi.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3); */
					
					dongia.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					spvat.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
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
				trongluong.item(i).value = "";	
				thetich.item(i).value = "";	
				spQuyDoi.item(i).value = "";
				spvat.item(i).value = "";
			}
		}	
		
		TinhTien();
		setTimeout(replaces, 300);
	}
	
	function Huy_roundNumber(num, dec) 
	{
		var result = Math.round(num);
		result =  Math.round(result/Math.pow(10,dec));
		result = result *Math.pow(10,dec);
		return result;
	}
	
	 function TinhTien()
	 {
		var spMa = document.getElementsByName("spMa");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		var chietkhau = document.getElementsByName("chietkhau");
		var thueVAT = document.getElementsByName("spvat");
		var thanhtien = document.getElementsByName("thanhtien");
		
		var trongluong = document.getElementsByName("spTrongLuong");
		var thetich = document.getElementsByName("spTheTich");
		var spQuyDoi = document.getElementsByName("spQuyDoi");
		var spDonVi = document.getElementsByName("donvi");
		
		var vat = document.getElementById("txtVAT").value;
		if(vat == '')
			vat = '0';
		
		var tongtien = 0;
		var tongtienCK = 0;
		var tongtienVAT = 0;
		
		var totalTL = 0;
		var totalTT = 0;
		
		
		var totalTHG = 0;
		var totalLe = 0 ;
		
		var thuevat_chung=0;	
		   for(var i = 0; i < spMa.length; i++)
			{
				if(spMa.item(i).value != "" && dongia.item(i).value != "" && soluong.item(i).value != "" )
				{
				
					var _thueVAT = thueVAT.item(i).value.replace(/,/g,"");
					thuevat_chung=_thueVAT;
					if(  parseFloat(vat) > 0 && _thueVAT == '' )
					{
						thueVAT.item(i).value = vat;
						_thueVAT = vat;
					}
					else
					{
						if(_thueVAT == '')
							_thueVAT = '0';	
					}
						
					var sum  = parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,""));
					var tt= sum;
					tt = parseFloat(tt) * ( 1 +  parseFloat(_thueVAT) / 100 );
					thanhtien.item(i).value = DinhDangTien(tt);
					
					tongtien += sum;
				
					
				}		
			}
			
			
			
			
			
			var tiensaucung = tongtien * (1+ thuevat_chung/100.0);
			tiensaucung = Huy_roundNumber(tiensaucung,1);
			var tienvat = Math.round(tiensaucung - Math.round(tongtien));
			
			document.getElementById("txtBVAT").value = DinhDangTien(Math.round(tongtien));
			document.getElementById("txtVAT").value = DinhDangTien(tienvat);
			document.getElementById("txtSauVAT").value = DinhDangTien(tiensaucung);	
		   
	 }
	
	 function CapNhatGia(e, stt)
	 { 
		 	//alert(e.value);
		 	
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
				   //alert(xmlhttp.responseText);
				   dongia.item(stt).value = DinhDangTien(xmlhttp.responseText);
			   }
			}
			
			var dvtMA = encodeURIComponent(e.value.replace(" ","+"));
			var spMA = encodeURIComponent(spMa.item(stt).value.replace(" ","+"));
			xmlhttp.open("GET","../../ErpDondathangDoitacSvl?spMa=" + spMA + "&dvt=" + dvtMA + "&locgiaQUYDOI=1",true);
			xmlhttp.send();
		 
	 }
	 
	 function dateDiff(date1,date2) {
		   var dtFrom =date1;
		   var dtTo = date2;
		 
		   console.log ("ngay 11 "+ dtFrom);
		   console.log ("ngay 21 "+ dtTo);
		   var dt1 = new Date(dtFrom);
		   var dt2 = new Date(dtTo);
		   console.log ("ngay 1"+ dt1);
		   console.log ("ngay 2"+ dt2);
		   var diff = dt2.getTime() - dt1.getTime();
		   var days = diff/(1000 * 60 * 60 * 24);
		   return days;
		}
	 
	 function chotform()
	 {	
		 var kq = document.getElementById("ktracongno").value;
		 
		 if(kq == '')
			 kq=0;
		 
		 if(kq>0){
			 if(!confirm('Đơn hàng này của đối tác đã vượt mức công nợ, bạn có muốn tiếp tục duyệt đơn hàng ?'))
			 {
				 return;
			 }
		 }
		 
		 var msg="";
		 var spma=document.getElementsByName("spMa");
		 var hopdong_tungay=document.getElementById("ngaychuyen");
		 for(var i=0;i<spma.length;i++)
		 {
		 	console.log (spma.item(i).value);
		 	var ngayhethan= document.getElementsByName(spma.item(i).value+"_spNGAYHETHAN");
		 	var soluong= document.getElementsByName(spma.item(i).value+"_spSOLUONG");
		 	 for(var j=0;j<ngayhethan.length;j++)
	 		 {
		 		 if(soluong.item(j).value!='0' && soluong.item(j).value!='' )
		 			{
		 				if(dateDiff(hopdong_tungay.value,ngayhethan.item(j).value)<= 12*31 )
	 					{
	 						msg+=spma.item(i).value+" tồn tại lô có ngày hết hạn <= 12 tháng \n";	
	 					}
		 				
		 			}
	 		 }
		 }
		if(msg!="")
		{
			 if(!confirm(msg + " bạn có muốn tiếp tục không "))
				{
					
				}
				 else
				{
					 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
				 	 document.forms['hctmhForm'].action.value = 'duyet';
				     document.forms['hctmhForm'].submit();
				 }
		}
		else
		{
			document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
		 	 document.forms['hctmhForm'].action.value = 'duyet';
		     document.forms['hctmhForm'].submit();
		}
		
		 
		 
		
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
	
	function CheckSoLuong_DeXuat(element)
	{
		element.value = DinhDangTien(element.value);
		if(element.value== '' )
		{
			element.value= '';
		}
		
		Update_SoLuong( element );
	}	
	 function load()
	 { 
		 document.forms['hctmhForm'].action.value='load';
	     document.forms["hctmhForm"].submit();
	 }
	function Update_SoLuong( element )
	{
		var spMa = document.getElementsByName("spMa");
		var soluong = document.getElementsByName("soluong");
		var soluong2 = document.getElementsByName("soluong2");
		
		for(var i = 0; i < spMa.length; i++ )
		{
			var soluongDEXUAT = document.getElementsByName(spMa.item(i).value + "_spSOLUONG");
			
			var totalXUAT = 0;
			for(var j = 0; j < soluongDEXUAT.length; j++ )
			{
				totalXUAT = parseFloat(totalXUAT) + parseFloat(soluongDEXUAT.item(j).value.replace(/,/g,""));
			}
			
			//alert(totalXUAT);
			
			if( totalXUAT > parseFloat(soluong.item(i).value.replace(/,/g,"")) )
			{
				soluong2.item(i).value = soluong.item(i).value;
				element.value = '0';
				
				alert('Số lượng xuất ( ' + totalXUAT + ' ) không được phép vượt quá số lượng đặt ( ' + soluong.item(i).value + ' )');
			}

			soluong2.item(i).value = soluong.item(i).value;
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

<form name="hctmhForm" method="post" action="../../ErpDondathangDoitacUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="loaidonhang" value='<%= lsxBean.getLoaidonhang() %>'>
<input type="hidden" name="nppId" value='<%= lsxBean.getNppId() %>'>
<input type="hidden" name="id" value='<%= lsxBean.getId() %>'>
<input type="hidden" name="dungchungkenh" value='<%= lsxBean.getDungchungKenh() %>'>
<input type="hidden" id = "ktracongno" name = "ktracongno" value ='<%= lsxBean.getCongNo()%>' >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý bán hàng > Duyệt đơn hàng > Duyệt đơn hàng đối tác
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= lsxBean.getNppTen() %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpDondathangDoitacSvl?userId=<%= userId %>&loaidonhang=<%= lsxBean.getLoaidonhang() %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
  
  <%if(!lsxBean.getTrangthai().equals("4")){ %>     
        <span id="btnSave">
	        <A href="javascript:chotform()" >
	        	<IMG src="../images/Chot.png" title="Duyệt đơn hàng" alt="Duyệt đơn hàng" border ="1px" style="border-style:outset"></A>
        </span>
   <%} %>
   
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
    	<legend class="legendtitle">Bán hàng cho đối tác </legend>
        	<div style="float:none; width:100%" align="left">
            
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="130px" class="plainlabel" >Ngày đơn hàng </TD>
                    <TD width="250px"  class="plainlabel"  >
                    	<input type="text" class="days" readonly="readonly"  name="ngaychuyen" id="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/>
                    </TD>
                    
                    <TD width="120px" class="plainlabel" >Ngày đề nghị giao </TD>
                    <TD class="plainlabel" >
                    	<input type="text" class="days" readonly="readonly"  name="ngaydenghi" value="<%= lsxBean.getNgaydenghi() %>"/>
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Đơn vị kinh doanh </TD>
                    <TD class="plainlabel"   >
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
                    <TD class="plainlabel" >Kênh bán hàng </TD>
                    <TD class="plainlabel"    >
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
                	<TD class="plainlabel" >Đối tác </TD>
                    <TD class="plainlabel"   >
                    	<select name='doitacId' class="select2" style="width: 200px" onchange="submitform();"  >
                    		<option value=""> </option>
                        	<%
                        		if(nppRs != null)
                        		{
                        			try
                        			{
                        			while(nppRs.next())
                        			{  
                        			if( nppRs.getString("pk_seq").equals(lsxBean.getKhId())){ %>
                        				<option value="<%= nppRs.getString("pk_seq") %>" selected="selected" ><%= nppRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nppRs.getString("pk_seq") %>" ><%= nppRs.getString("ten") %></option>
                        		 <% } } nppRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>   
                    <TD class="plainlabel" >Kho đặt </TD>
                    <TD class="plainlabel"   >
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
                
                <TR>
                    <TD class="plainlabel" >Tổng giá trị </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text" readonly="readonly"  id="txtBVAT" value="" style="text-align: right;"  />
                    	
                    	<input type="hidden"  value="<%= lsxBean.getChietkhau() %>" id="txtPTChietKhau" style="text-align: right;" name="ptChietkhau" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" />
                    </TD>
                </TR>
                
                <TR >
                    <TD class="plainlabel" >Tổng tiền chiết khấu </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" readonly="readonly"  value="" id="txtTongCK" style="text-align: right;" /></TD>
                    	
                    <TD class="plainlabel" >Tổng tiền sau CK </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly"  value="" id="txtTongSauCK" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Tổng tiền VAT </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" value="<%= lsxBean.getVat() %>" id="txtVAT" style="text-align: right;" name="ptVat" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" readonly='readonly' />
                    </TD>
                    	
                    <TD class="plainlabel" >Tổng tiền sau VAT </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly"  value="" id="txtSauVAT" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Ghi chú </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 700px;" />
                    </TD>
                </TR>
                 <TR>
                      <TD class="plainlabel" >Đơn hàng khác, KM </TD>
                    <TD class="plainlabel" colspan="3">
                    	    <input type="checkbox"  disabled  name="isdhk"  id="isdhk" onchange="submitform()" <%=lsxBean.getIsdhk().equals("1")?"checked=\"checked\"":"" %>   value="1" style="text-align: right;"  />
                     	 	<input  type="hidden"  name="isdhk"  id="isdhk"    value="<%= lsxBean.getIsdhk() %>"   />
                    </TD>
                     <%--  
                     <TD class="plainlabel"   > Có in giá </td>
                    <TD class="plainlabel" >
                           <input type="checkbox"   name="isgia"  id="isgia"  <%=lsxBean.getIsgia().equals("1")?"checked=\"checked\"":"" %>   value="1" style="text-align: right;"  />
                      </TD>
                     --%>
                	</TR>
                <TR style="display: none;" >
				   <TD  class="plainlabel" colspan = '4'>
				   <div id="btnApKhuyenMai">
				  		<a class="button2" href="javascript:Apkhuyenmai()">
							<img style="top: -4px;" src="../images/New.png" alt="">Lưu & Áp khuyến mại </a>
					</div>									
				  </TD>
				</TR>
               
            </TABLE>
			
			<hr />
           
				<table cellpadding="0px" cellspacing="1px" width="100%">
					<tr class="tbheader">
						<th align="center" width="3%" >STT</th>
						<th align="center" width="10%" >Mã sản phẩm</th>
						<th align="center" width="22%" >Tên sản phẩm</th>
						<th align="center" width="10%" >Đơn vị</th>
						<th align="center" width="8%" >Số lượng</th>
						<th align="center" width="8%" >Đơn giá</th>
						<th align="center" width="8%" >Chiết khấu</th>
						<th align="center" width="10%" >VAT</th>
						<th align="center" width="10%" >Thành tiền</th>
						<th align="center" width="10%" >Scheme</th>
					</tr>
					
					<%
						int count = 0;
						if(spMa != null)
						{
							for(int i = 0; i < spMa.length; i++)
							{
								String dvdl_fk = "";
							%>
						
							<tr>
								<td style="text-align: center;" > <%= i + 1 %> </td>
								<td>
									<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off" readonly="readonly" > 
									<input type="hidden" name="spTrongLuong" value="0" > 
									<input type="hidden" name="spTheTich" value="0" > 
									<input type="hidden" name="spQuyDoi" value="0"  >
								</td>
								<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%" readonly="readonly"> </td>
								<td>
									 <input type="text" name="donvi" value="<%=spDonvi[i] %>" style="width: 100%" readonly="readonly">
									<%-- <select name="donvi" style="width: 100%"  >
										<option value="" ></option>
										<% if(dvtRs != null) 
										{ 
												dvtRs.beforeFirst();
												while(dvtRs.next())
												{
													if(spDonvi[i].equals(dvtRs.getString("DONVI")))
													{ dvdl_fk = dvtRs.getString("PK_SEQ");  %>
														<option value="<%= dvtRs.getString("DONVI") %>" selected="selected" ><%= dvtRs.getString("DONVI") %></option>
													<% } else { %>
														<option value="<%= dvtRs.getString("DONVI") %>" ><%= dvtRs.getString("DONVI") %></option>
												   <% } }
										} %>
									 </select>  --%>
								</td>
								
								<td> 
									<input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width: 65%; text-align: right;" readonly="readonly" > 
									
									<% if(spSoluong[i].trim().length() > 0) { %>
									<a href="" id="scheme_<%= spMa[i] %>" rel="subcontent_<%= spMa[i] %>">
			           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
			           	 		
		           	 		 		<DIV id="subcontent_<%= spMa[i] %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					                             background-color: white; width: 450px; max-height:300px; overflow:auto; padding: 4px;">
					                    <table width="95%" align="center">
					                    	<tr>
					                    		<td ><b>Tổng xuất</b></td>
					                    		<td colspan="3" > <input type="text" name="soluong2" value="<%= spSoluong[i] %>" style="width: 100px; text-align: right;" readonly="readonly" >	</td>
					                    	</tr>
					                        <tr>
					                            
					                            
					                            <th width="100px" style="font-size: 11px">Ngày hết hạn</th>
					                            <th width="100px" style="font-size: 11px">Ngày nhập kho</th>
					                            <th width="100px" style="font-size: 11px">Tồn kho</th>
					                            <th width="100px" style="font-size: 11px">Số lượng</th>
					                            <th width="100px" style="font-size: 11px">Số lô</th>
					                        </tr>
			                            	<%
			                            		ResultSet spRs = lsxBean.getSoloTheoSp(spMa[i], spDonvi[i], spSoluong[i]);  
				                        		if(spRs != null)
				                        		{
				                        			while(spRs.next())
				                        			{
				                        				
				                        				String tudeXUAT = "";
				                        				if(spRs.getString("tuDEXUAT").trim().length() > 0)
				                        					tudeXUAT = formater2.format(spRs.getDouble("tuDEXUAT"));

				                        				String temID = spMa[i]+spscheme[i];
				                        				temID = temID.trim();
				                        				System.out.println(" a;lo1 =[" + temID +"]"  );
				                        				System.out.println(" a;lo =[" + spMa[i] +"]"  );
				                        			%>
				                        			
				                        			<tr>
				                        				
				                        				
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spNGAYHETHAN" value="<%= spRs.getString("NGAYHETHAN") %>" readonly="readonly">
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spNGAYNHAPKHO" value="<%= spRs.getString("NGAYNHAPKHO") %>" readonly="readonly">
				                        				</td>
				                        				
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spTONKHO" value="<%= formater2.format(spRs.getDouble("AVAILABLE")) %>" readonly="readonly">
				                        				</td>
				                        				
				                        				<td>
				                        				<%
				                        					System.out.println(" haha = "+ sanpham_soluong.get( spMa[i] + "__" + spRs.getString("SOLO") + "__" +  spRs.getString("NGAYHETHAN")  + "__" +  spRs.getString("NGAYNHAPKHO") ));
				                        				
				                        				 if( sanpham_soluong.get( spMa[i] + "_" + spscheme[i] +"__"+ spRs.getString("SOLO").trim() + "__" +  spRs.getString("NGAYHETHAN")  + "__" +  spRs.getString("NGAYNHAPKHO") ) != null ) { %>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= formater2.format(Double.parseDouble( sanpham_soluong.get( spMa[i] + "__" + spRs.getString("SOLO") + "__" +  spRs.getString("NGAYHETHAN")  + "__" +  spRs.getString("NGAYNHAPKHO") ) )) %>" onkeyup="CheckSoLuong_DeXuat(this);" >
				                        				<% } else { %>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= tudeXUAT  %>" onkeyup="CheckSoLuong_DeXuat(this);" >
				                        				<% } %>
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spSOLO" value="<%= spRs.getString("SOLO") %>" readonly="readonly">
				                        				</td>
				                        			</tr>
				                        			
				                        		 <% } } %>
				                        		 
					                     </table>
					                     <div align="right">
					                     	<label style="color: red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('subcontent_<%= spMa[i] %>')" > Đóng lại </a>
					                     </div>
						            </DIV> 
						            
						            <script type="text/javascript">
						            	dropdowncontent.init('scheme_<%= spMa[i]  %>', "left-top", 300, "click");
						            </script>
						         <% } else { %>
						         	<a href="javascript:void(0);" >&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
						         <% } %>
								</td>
								<td> <input type="text" name="dongia" value="<%= spGianhap[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > </td>
								<td> <input type="text" name="chietkhau" value="<%= spChietkhau[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" readonly="readonly" > </td>
								<td> <input type="text" name="spvat" value="<%= spVat[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" readonly="readonly" > </td>
								<td> <input type="text" name="thanhtien" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
								<td> <input type="text" name="scheme" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							</tr>		
								
						<% count ++; } } %>
					
				</table>
			
			
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