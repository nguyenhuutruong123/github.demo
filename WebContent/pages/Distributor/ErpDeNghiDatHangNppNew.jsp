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

<% IErpDenghidathangNpp lsxBean = (IErpDenghidathangNpp)session.getAttribute("lsxBean"); %>
<% ResultSet dvkdRs = lsxBean.getDvkdRs(); %>
<% ResultSet kbhRs = lsxBean.getKbhRs(); %>
<% ResultSet nppRs = lsxBean.getNppRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet dvtRs = lsxBean.getDvtRs(); %>
<% ResultSet spRs = lsxBean.getSanphamRs(); %>
<% ResultSet congnoRs = lsxBean.getCongnoRs(); %>
<% Hashtable<String, String> sp_soluong = lsxBean.getSanpham_soluong(); %>

<% NumberFormat formater = new DecimalFormat("##,###,###");%>

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
		TinhTien();
		setTimeout(replaces, 300);
	}
	
	
	 function TinhTien()
	 {
		var spMa = document.getElementsByName("spMA");
		var soluong = document.getElementsByName("spSOLUONG");
		var dongia = document.getElementsByName("spDONGIA");
		var thanhtien = document.getElementsByName("spTHANHTIEN");
		
		var tongtien = 0;
		for(var i = 0; i < spMa.length; i++)
		{
			if(spMa.item(i).value != "" && dongia.item(i).value != "" && soluong.item(i).value != "" )
			{
				var tt = parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,""));
				thanhtien.item(i).value = DinhDangTien(tt);
				
				tongtien += tt;
			}
		}
		
		var chietkhau = document.getElementById("txtPTChietKhau").value;
		if(chietkhau == '')
			chietkhau = '0';
		
		var vat = document.getElementById("txtVAT").value;
		if(vat == '')
			vat = '0';
		
		var tongtienCK = parseFloat(chietkhau) * parseFloat(tongtien) / 100;
		var tongtienSAUCK = parseFloat(tongtien) - parseFloat(tongtienCK);
		
		document.getElementById("txtTongCK").value = DinhDangTien(tongtienCK);
		document.getElementById("txtBVAT").value = DinhDangTien(tongtienSAUCK);
		document.getElementById("txtTongSauCK").value = DinhDangTien(tongtienSAUCK);
		
		var tongtienSAUVAT = parseFloat(tongtienSAUCK) + ( parseFloat(vat) * parseFloat(tongtienSAUCK) / 100 );
		document.getElementById("txtSauVAT").value = DinhDangTien(tongtienSAUVAT);
		
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
			xmlhttp.open("GET","../../ErpDenghidathangNppSvl?spMa=" + spMA + "&dvt=" + dvtMA + "&locgiaQUYDOI=1",true);
			xmlhttp.send();
		 
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

<form name="hctmhForm" method="post" action="../../ErpDenghidathangNppUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý tồn kho > Mua hàng từ nhà cung cấp > Đề nghị đặt hàng > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn  <%= lsxBean.getNppTen() %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<%-- <A href= "../../ErpDenghidathangNppSvl?userId=<%= userId %>&loaidonhang=<%= lsxBean.getLoaidonhang() %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A> --%>
        	<A href= "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDenghidathangNppSvl?userId=" + userId+ "&loaidonhang="+ lsxBean.getLoaidonhang()) %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
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
    	<legend class="legendtitle">Đề nghị đặt hàng </legend>
        	<div style="float:none; width:100%" align="left">
            
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD class="plainlabel" >Ngày đề nghị </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" class="days" readonly="readonly"  name="ngaydenghi" value="<%= lsxBean.getNgaydenghi() %>"/>
                    </TD>
                    <TD class="plainlabel" style="color: red;" >Tần suất đặt hàng </TD>
                    <TD class="plainlabel"  >
                    	<select name='tansuatdathang'  onchange="submitform();" >
                    		<% if(lsxBean.getTansuatdathang().equals("6")) { %>
                    			<option value="6" selected="selected" >1 tuần / 1 lần</option>
                    			<option value="3" >2 tuần / 1 lần</option>
                    		<% } else { %>
                    			<option value="6" >1 tuần / 1 lần</option>
                    			<option value="3" selected="selected"  >2 tuần / 1 lần</option>
                    		<% } %>
                    	</select>
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" style="width: 120px" >Đơn vị kinh doanh </TD>
                    <TD class="plainlabel" style="width: 250px" >
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
                    <TD class="plainlabel" style="width: 120px" >Kênh bán hàng </TD>
                    <TD class="plainlabel"  >
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
                	<TD class="plainlabel" >Nhà phân phối </TD>
                    <TD class="plainlabel" >
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
                    <TD class="plainlabel" >Kho đặt </TD>
                    <TD class="plainlabel"  >
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
                
                <TR style="display: none;" >
                    <TD class="plainlabel" >Tổng tiền chiết khấu </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly"  value="" id="txtTongCK" style="text-align: right;" /></TD>
                    	
                    <TD class="plainlabel" >Tổng tiền sau CK </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly"  value="" id="txtTongSauCK" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" >VAT </TD>
                    <TD class="plainlabel" >
                    	<input type="text" value="<%= lsxBean.getVat() %>" id="txtVAT" style="text-align: right;" name="ptVat" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" /> %
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
                
            </TABLE>
			
			<!-- <ul class="tabs">
		                  			
         		<li><a href="#">Thông tin đơn hàng</a></li>
          		<li><a href="#">Công nợ</a></li>

          	</ul>
             			
            <div class="panes">
              										
			<div> -->
			<hr />
				<table cellpadding="0px" cellspacing="1px" width="100%">
					<tr class="tbheader">
						<th align="center" width="8%" >Mã sản phẩm</th>
						<th align="center" width="21%" >Tên sản phẩm</th>
						<th align="center" width="7%" >Đơn vị</th>
						<th align="center" width="10%" >TB Bán / Ngày</th>
						<th align="center" width="10%" >Tồn tối thiểu</th>
						<th align="center" width="10%" >Dự kiến bán</th>
						<th align="center" width="7%" >Tồn kho</th>
						<th align="center" width="7%" >Đề nghị</th>
						<th align="center" width="10%" >Đơn giá</th>
						<th align="center" width="10%" >Thành tiền</th>
						
					</tr>
					
					<%
						if(spRs != null)
						{
							while(spRs.next())
							{
								double quyDOI = spRs.getDouble("quydoi");
								if(quyDOI == 0)
									quyDOI = 1;
								
								double songay = 30;
								double tontoithieu = spRs.getDouble("tontoithieu") * spRs.getDouble("tbBAN") / quyDOI;
								double tonhientai = spRs.getDouble("tonKHO") / quyDOI;
								
								double dukienban = Double.parseDouble(lsxBean.getTansuatdathang()) * spRs.getDouble("tbBAN") / quyDOI;
								dukienban += spRs.getDouble("tbBAN") * songay;
								dukienban += spRs.getDouble("ptTangtruong");
								dukienban -= tonhientai;
								
								double denghi = tontoithieu + dukienban - tonhientai;
								if(denghi < 0)
									denghi = 0;
								
								if(sp_soluong.get(spRs.getString("MA")) != null )
									denghi = Double.parseDouble(sp_soluong.get(spRs.getString("MA")));
								
								double thanhtien = denghi * spRs.getDouble("donGIA");
								if(thanhtien < 0)
									thanhtien = 0;
								String color = "color:red;";
								%>
							<tr>
								<td><input type="text" name="spMA"  value="<%= spRs.getString("MA") %>" readonly="readonly" style="width: 100%; <%= denghi > 0?color:"" %>" > </td>
								<td><input type="text" value="<%= spRs.getString("TEN") %>" readonly="readonly" style="width: 100%; <%= denghi > 0?color:"" %>" > </td>
								<td><input type="text" value="Thùng" readonly="readonly" style="width: 100%; <%= denghi > 0?color:"" %>" > </td>
								<td><input type="text" value="<%= formater.format(spRs.getDouble("tbBAN")) %>" readonly="readonly" style="text-align: right;width: 100%; <%= denghi > 0?color:"" %>" > </td>
								<td><input type="text" value="<%= formater.format(tontoithieu) %>" readonly="readonly" style="text-align: right;width: 100%; <%= denghi > 0?color:"" %>" > </td>
								<td><input type="text" value="<%= formater.format(dukienban) %>" readonly="readonly" style="text-align: right;width: 100%; <%= denghi > 0?color:"" %>" > </td>
								<td><input type="text" value="<%= formater.format(tonhientai) %>" readonly="readonly" style="text-align: right;width: 100%; <%= denghi > 0?color:"" %>" > </td>
								<td>
								<input type="text" name="spSOLUONG" value="<%= formater.format(denghi) %>" style="text-align: right;width: 100%; <%= denghi > 0?color:"" %>" onkeypress="return keypress(this);" >
								</td>
								<td><input type="text" name="spDONGIA" value="<%= formater.format(spRs.getDouble("donGIA")) %>" readonly="readonly" style="text-align: right;width: 100%; <%= denghi > 0?color:"" %>" > </td>
								<td><input type="text" name="spTHANHTIEN" value="<%= formater.format(thanhtien) %>" readonly="readonly" style="text-align: right;width: 100%; <%= denghi > 0?color:"" %>" > </td>
							</tr>	
								
						<% } spRs.close(); }
					%>
					
				</table>
			<%-- </div>
			
			<div >
				
				<table cellpadding="0px" cellspacing="1px" width="100%" align="center" >
					<tr class="tbheader">
						<th align="center" width="10%" >Mã sản phẩm</th>
						<th align="center" width="28%" >Tên sản phẩm</th>
						<th align="center" width="7%" >Đơn vị</th>
						<th align="center" width="11%" >Nợ hàng mua</th>
						<th align="center" width="11%" >Nợ khuyến mại</th>
						<th align="center" width="11%" >Nợ tích lũy</th>
						<th align="center" width="11%" >Nợ trưng bày</th>
						<th align="center" width="11%" >Nợ đổi hàng</th>
					</tr>
					
					<% if(congnoRs != null) 
					{ 
						while(congnoRs.next())
						{ %>
						
						<tr>
							<td><input type="text" value="<%= congnoRs.getString("MA") %>" readonly="readonly" style="width: 100%;" > </td>
							<td><input type="text" value="<%= congnoRs.getString("TEN") %>" readonly="readonly" style="width: 100%;" > </td>
							<td><input type="text" value="<%= congnoRs.getString("DONVI") %>" readonly="readonly" style="width: 100%;" > </td>
							<td><input type="text" value="<%= formater.format(congnoRs.getDouble("noHANGMUA")) %>" readonly="readonly" style="text-align: right;width: 100%;" > </td>
							<td><input type="text" value="<%= formater.format(congnoRs.getDouble("noKHUYENMAI")) %>" readonly="readonly" style="text-align: right;width: 100%;" > </td>
							<td><input type="text" value="<%= formater.format(congnoRs.getDouble("noTICHLUY")) %>" readonly="readonly" style="text-align: right;width: 100%;" > </td>
							<td><input type="text" value="<%= formater.format(congnoRs.getDouble("noTRUNGBAY")) %>" readonly="readonly" style="text-align: right;width: 100%;" > </td>
							<td><input type="text" value="<%= formater.format(congnoRs.getDouble("noDOIHANG")) %>" readonly="readonly" style="text-align: right;width: 100%;" > </td>
						</tr>
							
						<% } congnoRs.close();  %>
					<% } %>
					
				</table>
				
			</div>
				
            </div> --%>
     
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