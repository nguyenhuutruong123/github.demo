<%@page import="geso.dms.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.dms.distributor.beans.xuatkho.*" %>
<%@ page  import = "geso.dms.distributor.beans.xuatkho.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<% IErpYeucauxuatkhoNpp lsxBean = (IErpYeucauxuatkhoNpp)session.getAttribute("lsxBean"); %>
<% ResultSet nppRs = lsxBean.getKhRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet ddhRs = lsxBean.getDondathangRs(); %>
<% Hashtable<String, String> sanpham_soluong = lsxBean.getSanpham_Soluong(); %>

<% 
	String[] spId = lsxBean.getSpId();
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spSoluongdat = lsxBean.getSpSoluongDat();
	String[] spTonkho = lsxBean.getSpTonKho();
	String[] spDaxuat = lsxBean.getSpDaXuat();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spLoai = lsxBean.getSpLoai();
	String[] spSCheme = lsxBean.getSpScheme();
	NumberFormat formater = new DecimalFormat("##,###,###");
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
					
					dongia.item(i).value = DinhDangTien(sp.substring(0, parseInt(sp.indexOf("]")))); 
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
		var thanhtien = document.getElementsByName("thanhtien");
		
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
	 
	 function changePOform()
	 { 
		 document.forms['hctmhForm'].action.value='changePO';
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
	
	function Update_SoLuong()
	{
		var spTen = document.getElementsByName("spTen");
		var soluong = document.getElementsByName("soluong");
		var soluong2 = document.getElementsByName("soluong2");
		
		var soluongDAT = document.getElementsByName("soluongDAT");
		var soluongDANHAN = document.getElementsByName("daxuat");
		
		for(var i = 0; i < soluong.length; i++ )
		{
			var toida = parseFloat(soluongDAT.item(i).value.replace(/,/g,"")) - parseFloat(soluongDANHAN.item(i).value.replace(/,/g,""));
			
			if( parseFloat(soluong.item(i).value.replace(/,/g,"")) > toida )
			{
				soluong.item(i).value = DinhDangTien(toida);
				soluong2.item(i).value = soluong.item(i).value;
				
				alert('Số lượng xuất của sản phẩm (' +  spTen.item(i).value + ') không được phép vượt quá tổng đặt và đã xuất ( ' + toida + ' )');
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

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>


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

<form name="hctmhForm" method="post" action="../../ErpYeucauxuatkhoNppUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="nppId" value='<%= lsxBean.getNppId() %>'>
<input type="hidden" name="id" value='<%= lsxBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý bán hàng > Bán hàng cho NPP / Đại lý > Xuất kho > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpYeucauxuatkhoNppSvl?userId=<%= userId %>" >
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
    	<legend class="legendtitle">Yêu cầu xuất kho </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="130px" class="plainlabel" >Ngày yêu cầu </TD>
                    <TD class="plainlabel" width="250px" >
                    	<input type="text" class="days" readonly="readonly"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/>
                    </TD>
                    
                    <TD width="100px" class="plainlabel" >Xuất cho </TD>
                    <TD class="plainlabel"  >
                    	<select name="xuatchoId" class="select2" style="width: 200px" onchange="submitform();" >
                    		<% if(lsxBean.getXuatcho().equals("0")) { %>
                    			<option value="0" selected="selected" >Đối tác</option>
                    		<% } else { %>
                    			<option value="0" >Đối tác</option>
                    		<% } %>
                    		<% if(lsxBean.getXuatcho().equals("1")) { %>
                    			<option value="1" selected="selected" >Khách hàng ETC</option>
                    		<% } else { %>
                    			<option value="1" >Khách hàng ETC</option>
                    		<% } %>
                    	</select>
                    </TD>
                </TR>

                <TR>
                	<TD class="plainlabel" >Đối tác / ETC </TD>
                    <TD class="plainlabel" >
                    	<select name = "khId" class="select2" style="width: 200px" onchange="submitform();" >
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
                    <TD class="plainlabel" >Kho xuất </TD>
                    <TD class="plainlabel" >
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
                    	</select>
                    </TD>         	
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Đơn đặt hàng </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<select name = "ddhIds" class="select2" style="width: 200px"  onchange="changePOform();" >
                    		<option value=""> </option>
                        	<%
                        		if(ddhRs != null)
                        		{
                        			try
                        			{
                        			while(ddhRs.next())
                        			{  
                        			if( lsxBean.getDondathangId().contains(ddhRs.getString("pk_seq")) ){ %>
                        				<option value="<%= ddhRs.getString("pk_seq") %>" selected="selected" ><%= ddhRs.getString("TEN") %></option>
                        			<%}else { %>
                        				<option value="<%= ddhRs.getString("pk_seq") %>" ><%= ddhRs.getString("TEN") %></option>
                        		 <% } } ddhRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>   
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Ghi chú </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 700px;" />
                    </TD>
                </TR>
               
            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%">
				<tr class="tbheader">
					<th align="center" width="10%" >Mã sản phẩm</th>
					<th align="center" width="17%" >Tên sản phẩm</th>
					<th align="center" width="10%" >Đơn vị</th>
					<th align="center" width="17%" >Scheme</th>
					<th align="center" width="10%" >Số lượng đặt</th>
					<th align="center" width="10%" >Tồn kho</th>
					<th align="center" width="10%" >Đã xuất</th>
					<th align="center" width="10%" >Số lượng xuất</th>
					<th align="center" width="6%" >Số lô</th>
				</tr>
				
				<%
					int count = 0;
					if(spMa != null)
					{
						for(int i = 0; i < spMa.length; i++)
						{
							String style = "";
							if(spLoai[i].equals("1"))
								style = " class='mySCHME' ";
						%>
					
						<tr <%= style %> >
							<td >
								<input type="hidden" name="spId" value="<%= spId[i] %>" style="width: 100%"   > 
								<input type="hidden" name="spLoai" value="<%= spLoai[i] %>" style="width: 100%"   > 
								<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%"  readonly="readonly"  > 
							</td>
							<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%" readonly="readonly"> </td>
							<td>
								<input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%; text-align: center;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="scheme" value="<%= spSCheme[i] %>" style="width: 100%; " readonly="readonly" >							
							</td>
							<td>
								<input type="text" name="soluongDAT" value="<%= spSoluongdat[i] %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="tonkho" value="<%= spTonkho[i] %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="daxuat" value="<%= spDaxuat[i] %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event); " onkeyup="Dinhdang(this);" >							
							</td>
							<td align="center" >
								<a href="" id="scheme_<%= spId[i] + "_" + spLoai[i] + "_" + spSCheme[i] %>" rel="subcontent_<%= spId[i] + "_" + spLoai[i] + "_" + spSCheme[i] %>">
		           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
		           	 		
	           	 		 		<DIV id="subcontent_<%= spId[i] + "_" + spLoai[i] + "_" + spSCheme[i] %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 450px; max-height:300px; overflow:auto; padding: 4px;">
				                    <table width="95%" align="center">
				                    	<tr>
				                    		<td ><b>Tổng xuất</b></td>
				                    		<td colspan="3" > <input type="text" name="soluong2" value="<%= spSoluong[i] %>" style="width: 100px; text-align: right;" readonly="readonly" >	</td>
				                    	</tr>
				                        <tr>
				                            <th width="100px" style="font-size: 11px">Số lượng</th>
				                            <th width="100px" style="font-size: 11px">Số lô</th>
				                            <th width="100px" style="font-size: 11px">Ngày hết hạn</th>
				                            <th width="100px" style="font-size: 11px">Tồn kho</th>
				                        </tr>
		                            	<%
		                            		ResultSet spRs = lsxBean.getSoloTheoSp(spId[i], spSoluong[i]);
			                        		if(spRs != null)
			                        		{
			                        			while(spRs.next())
			                        			{
			                        				String tudeXUAT = "";
			                        				if(spRs.getString("tuDEXUAT").trim().length() > 0)
			                        					tudeXUAT = formater.format(spRs.getDouble("tuDEXUAT"));
			                        				
			                        				String temID = spId[i] + "__" + spLoai[i] + "__" + spSCheme[i].trim();
			                        			%>
			                        			
			                        			<tr>
			                        				<td>
			                        				<% if(sanpham_soluong.get(spId[i] + "__" + spLoai[i] + "__" + spSCheme[i].trim() + "__" + spRs.getString("SOLO") ) != null ) { %>
			                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= formater.format(Double.parseDouble( sanpham_soluong.get( spId[i] + "__" + spLoai[i] + "__" + spSCheme[i].trim() + "__" + spRs.getString("SOLO") ))) %>" onkeyup="Dinhdang(this);" >
			                        				<% } else { %>
			                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= tudeXUAT  %>" onkeyup="Dinhdang(this);" >
			                        				<% } %>
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spSOLO" value="<%= spRs.getString("SOLO") %>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spNGAYHETHAN" value="<%= spRs.getString("NGAYHETHAN") %>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spTONKHO" value="<%= formater.format(spRs.getDouble("AVAILABLE")) %>" readonly="readonly">
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% } } %>
			                        		 
				                     </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontent_<%= spId[i] + "_" + spLoai[i] + "_" + spSCheme[i] %>')" > Đóng lại </a>
				                     </div>
					            </DIV> 
					            
					            <script type="text/javascript">
					            	dropdowncontent.init('scheme_<%= spId[i] %>_<%= spLoai[i] %>_<%= spSCheme[i] %>', "left-top", 300, "click");
					            </script>
					            
							</td>
						</tr>	
							
					<% count ++; } } %>
				
			</table>
				
            </div>
     </fieldset>	
    </div>
</div>

<script type="text/javascript">
	//replaces();
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