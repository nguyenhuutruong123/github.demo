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
<%@page import= "java.text.DecimalFormat" %>
<%@page import= "java.text.NumberFormat" %>

<% INhaphang lsxBean = (INhaphang)session.getAttribute("nhaphang"); %>
<% ResultSet ddhRs = lsxBean.getDondathangRs(); %>
<% ResultSet chungtuRs = lsxBean.getChungtuRs(); %>

<% 
	String[] spId = lsxBean.getSpId();
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spXuat = lsxBean.getSpXuat();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spDongia = lsxBean.getSpDongia();
	String[] spVat = lsxBean.getSpvat();
	String[] spChietkhau = lsxBean.getSpchietkhau();
	String[] spSCheme = lsxBean.getSpScheme();
	String[] spKhoId = lsxBean.getSpKhoId();
	String[] bvat = lsxBean.getBvat();
	String[] vat = lsxBean.getVat();
	String[] avat = lsxBean.getAvat();
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
		
	}	
	
	function Update_SoLuong()
	{
		
			 var spMa = document.getElementsByName("spMa");
			var soluong = document.getElementsByName("soluong");
			var dongia = document.getElementsByName("dongia");
			var spvat = document.getElementsByName("spvat");
			var thanhtien = document.getElementsByName("thanhtien");
			var spchietkhau = document.getElementsByName("spchietkhau");
			<%
			/* System.out.println("do dai sp"+spId.length);
			System.out.println("do dai ck"+spChietkhau.length);
			System.out.println("do dai vat"+spVat.length); */
			%>
			 var tongtien = 0;
			 var tongchietkhau = 0;
			for(var i = 0; i < spMa.length; i++)
			{
				var don_gia = dongia.item(i).value.replace(/,/g, "");
				var so_luong = soluong.item(i).value.replace(/,/g, "");
				var chiet_khau = spchietkhau.item(i).value.replace(/,/g, "");
				
				if(i==0)
					tongchietkhau = parseFloat(tongchietkhau) +  (parseFloat(chiet_khau));
				if(i>0 && chiet_khau != '' && spMa.item(i).value!= spMa.item(i-1).value )
					tongchietkhau = parseFloat(tongchietkhau) +  (parseFloat(chiet_khau));
				if( don_gia != '' && so_luong != '' )
					tongtien = parseFloat(tongtien) +    parseFloat(don_gia) * parseFloat(so_luong);  	  
			}
			
			var phantram=tongchietkhau/tongtien;
			console.log('tphn tram'+ phantram);
					var tongvat = 0;
					
				 for(var i = 0; i < spMa.length; i++)
					{
						var don_gia = dongia.item(i).value.replace(/,/g, "");
						var so_luong = soluong.item(i).value.replace(/,/g, "");
						var _vat = spvat.item(i).value;
						
						var _tienVAT = parseFloat(so_luong) * parseFloat(don_gia) * parseFloat( _vat ) / 100 ;
						_tienVAT=_tienVAT - ((parseFloat(so_luong) * parseFloat(don_gia) * parseFloat( _vat ) / 100 )*phantram);
						console.log('tien vat trc'+ _tienVAT);
							if(_vat != '')
							tongvat = parseFloat(tongvat) + parseFloat( _tienVAT);
						
						}
					
					var tienchuaVAT = Math.round(tongtien);
					var tienchuaVAT1 = Math.round(tongtien-tongchietkhau);
					document.getElementById("txtBVAT").value = DinhDangTien(tienchuaVAT1);
					var tienvat=Math.round(tongvat);
					document.getElementById("txtVAT").value = DinhDangTien(parseFloat(tongvat));
					var SoTienSauCKKM=tienchuaVAT-tongchietkhau+tienvat;
					document.getElementById("txtSauVAT").value = DinhDangTien(Math.round(SoTienSauCKKM));
		setTimeout(Update_SoLuong, 500);
	}
	 
	function Update_Giatri()
	{
		
		var spMa = document.getElementsByName("spMa");
		var soluong = document.getElementsByName("spxuat");
		var dongia = document.getElementsByName("dongia");
		var spvat = document.getElementsByName("spvat");
		var bvat = document.getElementsByName("bvat");
		var vat = document.getElementsByName("vat");
		var avat = document.getElementsByName("avat");
		var thuevat = document.getElementById("thuevat").value;
		if(thuevat == "0" || thuevat == "")
			thuevat = "10";
		var tongtien = 0;
		var tongvat = 0;
		var tongtienavat = 0;
		for(var i = 0; i < spMa.length; i++)
		{
			var dg = dongia.item(i).value.replace(/,/g, "");
			var sl = soluong.item(i).value.replace(/,/g, "");
			
			tongtien = parseFloat(tongtien) + (parseFloat(dg) * parseFloat(sl));
		} 
		 
		tongvat = parseFloat(tongtien) * parseFloat(thuevat) /100;
		tongtienavat = parseFloat(tongtien) + parseFloat(tongvat);
		document.getElementById("txtBVAT").value = DinhDangTien(tongtien);
		document.getElementById("txtVAT").value = DinhDangTien(tongvat);
		document.getElementById("txtSauVAT").value = DinhDangTien(tongtienavat);
		setTimeout(Update_Giatri, 500);
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

<form name="hctmhForm" method="post" action="../../NhaphangUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= lsxBean.getId() %>'>
<input type="hidden" name="thuevat"  id="thuevat" value='<%= lsxBean.getThuevat() %>'>
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Qu???n l?? t???n kho > Mua h??ng t??? nh?? cung c???p > Nh???n h??ng
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Ch??o m???ng b???n <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<%-- <A href= "../../NhaphangSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A> --%>
        	<A href= "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "NhaphangSvl?userId=" + userId) %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
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
    	<legend class="legendtitle">Xu???t kho </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="120px" class="plainlabel" valign="top">Ng??y xu???t kho </TD>
                    <TD class="plainlabel" valign="top" style="width: 250px;"  >
                    	<input type="text" readonly="readonly"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/>
                    </TD>
                
                    <TD width="120px" class="plainlabel" valign="top">Ng??y nh???n h??ng </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<input type="text" class="days" readonly="readonly"  name="ngaynhan" value="<%= lsxBean.getNgaynhap() %>"/>
                    </TD>
                </TR>
                
                <TR>
                    <%-- <TD class="plainlabel" style="color: red;" valign="top">S??? ch???ng t??? </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text"  name="sohoadon" value="<%= lsxBean.getSohoadon() %>"/>
                    </TD> --%>
                    <TD class="plainlabel" >S??? ch???ng t??? </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly" name="sohoadon" id="sohoadon" value="<%= lsxBean.getSohoadon() %>" style="text-align: right;"  />
                    	<%-- <select name="sohoadon" id="sohoadon" class="select2" style="width: 200px" >
                    		<option value=""> </option>
                        	<%
                        		if(chungtuRs != null)
                        		{
                        			try
                        			{
                        			while(chungtuRs.next())
                        			{  
                        			if( lsxBean.getSohoadon().equals(chungtuRs.getString("chungtu")) ){ %>
                        				<option value="<%= chungtuRs.getString("chungtu") %>" selected="selected" ><%= chungtuRs.getString("chungtu") %></option>
                        			<%}else { %>
                        				<option value="<%= chungtuRs.getString("chungtu") %>" ><%= chungtuRs.getString("chungtu") %></option>
                        		 <% } } chungtuRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select> --%>
                    </TD>
                    <TD class="plainlabel" > </TD>
                    <TD class="plainlabel"  >
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" >????n ?????t h??ng </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="hidden" name = "ddhIds"  value="<%= lsxBean.getDondathangId() %>" >
                    	<select  class="select2" style="width: 700px" multiple="multiple" disabled="disabled" >
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
                    <TD class="plainlabel" >Ghi ch?? </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text" readonly="readonly" name="ghichu" value="<%= lsxBean.getGhichu() %>" style="text-align: left;"  />

                    </TD>
                </TR>
                <TR>
                    <TD class="plainlabel" >Ghi ch?? TT</TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text" name="ghichuTT" value="<%= lsxBean.getGhichuTT() %>" style="text-align: left;" />

                    </TD>
                </TR>
                <TR>
                    <TD class="plainlabel" >T???ng gi?? tr??? </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text" readonly="readonly"  id="txtBVAT" value="" style="text-align: right;"  />

                    </TD>
                </TR>

                <TR>
                    <TD class="plainlabel" valign="top">Ti???n Vat </TD>
                    <TD class="plainlabel" valign="top" width="240px" >
                    	<input type="text" value="" id="txtVAT" style="text-align: right;" name="ptVat" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" /> 
                    </TD>
                    	
                    <TD class="plainlabel" valign="top" width="130px" >T???ng ti???n sau VAT </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly"  value="" id="txtSauVAT" style="text-align: right;" />
                    </TD>
                </TR>
               
            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%">
				<tr class="tbheader">
					<th align="center" width="7%" > M?? s???n ph???m</th>
					<th align="center" width="20%"> T??n s???n ph???m</th>
					<th align="center" width="7%"> ????n v???</th>
					<th align="center" width="7%" >S??? l?????ng</th>
					<th align="center" width="9%" >????n gi??</th>
					<th align="center" width="12%" >M?? ch????ng tr??nh</th>
					<th align="center" width="8%" >Kho</th>
				</tr>
				
				<%
					int count = 0;
					if(spMa != null)
					{
						for(int i = 0; i < spMa.length; i++)
						{
							String style = "";
							String kho = "";
							if(spKhoId[i].equals("100001"))
							{
								style = " class='mySCHME' ";
								kho = "H??ng KM";
							}
							else
							{
								kho = "H??ng b??n";
							}
						%>
					
						<tr <%= style %> >
							<td >
								<input type="hidden" name="spId" value="<%= spId[i] %>" style="width: 100%"   > 
								<input type="hidden" name="spKhoId" value="<%= spKhoId[i] %>" style="width: 100%"   > 
								<input type="hidden" name="spvat" value="<%= spVat[i] %>" style="width: 100%"   > 
								<input type="hidden" name="spchietkhau" value="<%= spChietkhau[i] %>" style="width: 100%"   > 
								<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%"  readonly="readonly"  > 
								<input type="hidden" name="spKhoId" value="<%= spKhoId[i] %>" style="width: 100%"  readonly="readonly"  >
								<input type="hidden" name="bvat" value="<%= bvat[i] %>" style="width: 100%"  readonly="readonly"  > 
								<input type="hidden" name="vat" value="<%= vat[i] %>" style="width: 100%"  readonly="readonly"  > 
								<input type="hidden" name="avat" value="<%= avat[i] %>" style="width: 100%"  readonly="readonly"  >  
							</td>
							<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%" readonly="readonly"> </td>
							<td>
								<input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%; text-align: center;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="spxuat" value="<%= spXuat[i] %>" style="width: 100%; text-align: right; background-color: silver; " onkeypress="return keypress(event); " onkeyup="Dinhdang(this);" >
								<input type="hidden" name="soluong" value="<%= spSoluong[i] %>" style="width: 100%; text-align: right; background-color: silver; " onkeypress="return keypress(event); " onkeyup="Dinhdang(this);" >							
							</td>
							<td>
								<input type="text" name="dongia" value="<%= spDongia[i] %>" style="width: 100%; text-align: right;" readonly="readonly" >							
							</td>
							<td>
								<input type="text" name="scheme" value="<%= spSCheme[i].trim() %>" style="width: 100%; " readonly="readonly" >							
							</td>
							<td>
								<input type="text" name="kho" value="<%= kho %>" style="width: 100%; " readonly="readonly" >							
							</td>
							
						</tr>	
							
					<% count ++; } } %>
				
			</table>
				
            </div>
     </fieldset>	
    </div>
</div>

<script type="text/javascript">
	//Update_SoLuong();
	Update_Giatri();
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