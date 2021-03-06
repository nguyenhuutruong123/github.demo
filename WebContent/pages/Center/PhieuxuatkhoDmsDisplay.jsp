<%@page import="geso.dms.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.dms.center.beans.phieuxuatkhodms.*" %>
<%@ page  import = "geso.dms.center.beans.phieuxuatkhodms.imp.*" %>
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

<% IPhieuxuatkhoDms lsxBean = (IPhieuxuatkhoDms)s.getAttribute("lsxBean"); 
   IPhieuxuatkhoDmsList lsxBeanList = (IPhieuxuatkhoDmsList)s.getAttribute("lsxBeanList");
%>

<% ResultSet dvkdRs = lsxBean.getDvkdRs(); %>
<% ResultSet kbhRs = lsxBean.getKbhRs(); %>
<% ResultSet nppRs = lsxBean.getNppRs(); %>
<% ResultSet dvtRs = lsxBean.getDvtRs(); %>

<% 
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spGianhap = lsxBean.getSpGianhap();
	String[] spSCheme = lsxBean.getSpScheme();
	String[] spVat = lsxBean.getSpVat();
	
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
<script type="text/javascript" src="../scripts/erp-SpListPhieuxuatkho.js"></script>

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
		var spMa = document.getElementsByName("spMa");
		var spTen = document.getElementsByName("spTen");  
		var donvi = document.getElementsByName("donvi");
		var dongia = document.getElementsByName("dongia");
		var spvat = document.getElementsByName("spvat");
		var soluong = document.getElementsByName("soluong");
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
					
					dongia.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					spvat.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
										
					//spBgId.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
					
					//CapNhatGia(document.getElementsByName("donvi"), i);
					
				}
			}
			else
			{
				spMa.item(i).value = "";
				spTen.item(i).value = "";
				donvi.item(i).value = "";
				soluong.item(i).value = "";
				dongia.item(i).value = "";
				spvat.item(i).value = "";
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
		var spScheme = document.getElementsByName("scheme");
		
		var tongtien = 0;
		var tongthanhtien = 0;
		var tt_vat = 0;
		for(var i = 0; i < spMa.length; i++)
		{
			if(spMa.item(i).value != "" && dongia.item(i).value != "" && soluong.item(i).value != "" )
			{
				
				dongia.item(i).value = dongiabvat; 
				thanhtien.item(i).value = DinhDangTien( parseFloat(tt));
				tongthanhtien += parseFloat(parseFloat(tt));
			}
		}
		
		tt_vat=tongthanhtien*0.1;
		document.getElementById("txtBVAT").value = DinhDangDonGia((tongthanhtien).toFixed(0));
		document.getElementById("txtVAT").value = DinhDangDonGia((tt_vat).toFixed(0));
		document.getElementById("txtAVAT").value = DinhDangDonGia((tongthanhtien*1.1).toFixed(0));
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

<form name="hctmhForm" method="post" action="../../PhieuxuatkhoDmsUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
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
        	Qu???n l?? kho trung t??m > Phi???u xu???t kho DMS > T???o m???i
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Ch??o m???ng b???n <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<%-- <A href= "../../ErpDondathangSvl?userId=<%= userId %>&loaidonhang=<%= lsxBean.getLoaidonhang() %>" > --%>
   		<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        
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
    	<legend class="legendtitle">Phi???u xu???t kho </legend>
        	<div style="float:none; width:100%" align="left">
        	
            <TABLE width="100%" cellpadding="4" cellspacing="0">	
				<TR>
					<TD class="plainlabel"> S??? ch???ng t??? </TD>
					<TD class="plainlabel">
						<input type="text" id="sochungtu" name="sochungtu" value="<%= lsxBean.getSochungtu() %>"/>
					</TD>
					<TD width="130px" class="plainlabel" valign="top">Ng??y ch???ng t??? </TD>
                    <TD width="300px"  class="plainlabel" valign="top" >
                    	<input type="text" class="days" id="ngaychungtu" name="ngaychungtu" value="<%= lsxBean.getNgaychungtu() %>"/>
                    </TD>
				</TR>					
                <TR>
                    <TD width="120px" class="plainlabel" valign="top"> S??? PO </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" id="sopo"  name="sopo" value="<%= lsxBean.getSopo() %>"/>
                    </TD>
                    <TD width="120px" class="plainlabel" valign="top"> S??? SO </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" id="soso"  name="soso" value="<%= lsxBean.getSoso() %>"/>
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" valign="top">????n v??? kinh doanh </TD>
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
                    <TD class="plainlabel" valign="top">K??nh b??n h??ng </TD>
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
                	<TD class="plainlabel" valign="top">Nh?? ph??n ph???i </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<select name = "nppId"    id="nppId" class="select2" style="width: 200px" onchange="submitform();" >
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
                    <TD class="plainlabel" valign="top"></TD>
                    <TD class="plainlabel" valign="top"></TD>         	
                </TR>
                
                <TR >
                    <TD class="plainlabel" valign="top">Ti???n tr?????c thu??? </TD>
                    <TD class="plainlabel" valign="top" colspan="3" >
                    	<input type="text" readonly="readonly" id="txtBVAT" name="txtBVAT" style="text-align: right;"  />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" valign="top">Ti???n VAT (10%) </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text" readonly="readonly" id="txtVAT" name="txtVAT" style="text-align: right;"  onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" />
                    </TD>
                    	
                    <TD class="plainlabel" valign="top" >T???ng ti???n sau VAT </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly" id="txtAVAT" name="txtAVAT" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Ghi ch?? </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 700px;" />
                    </TD>
                </TR>
            </TABLE>
			<ul class="tabs">
         		<li><a href="#">Th??ng tin phi???u xu???t kho</a></li>
          	</ul>
             			
            <div class="panes">
              										
			<div>
			
					<table cellpadding="0px" cellspacing="1px" width="100%">
					<tr class="tbheader">
						<th align="center" width="3%" >STT</th>
						<th align="center" width="10%" >M?? s???n ph???m</th>
						<th align="center" width="20%" >T??n s???n ph???m</th>
						<th align="center" width="5%" >????n v???</th>
						<th align="center" width="10%" >S??? l?????ng</th>
						<th align="center" width="10%" >????n gi?? sau CK</th>
						<th align="center" width="10%" >Th??nh ti???n</th>
						<th align="center" width="10%" >CTKM</th>
					</tr>
					<%
						int count = 0;
						if(spMa != null)
						{
							for(int i = 0; i < spMa.length; i++)
							{ %>
							
							<tr >
								<td style="text-align: center;" > <%= i + 1 %> </td>
								<td>
									<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 96%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
								</td>
								<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 99%" readonly="readonly"> </td>
								<td> <input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 99%" readonly="readonly"></td>
								<td > 
									<input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width:96%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > 
								</td>
								
								<td><input type="text" name="dongia" value="<%= spGianhap[i] %>" style="width: 97%; text-align: right;" >
									<input type="hidden" name="spvat" value="<%= spVat[i] %>" style="width: 100%; text-align: right;" > </td>
								<td> <input type="text" name="thanhtien" value="" style="width: 96%; text-align: right;" readonly="readonly" > </td>
								<td> <input type="text" name="scheme" value="<%= spSCheme[i] %>" style="width: 97%; text-align: right;" ></td>
							</tr>	
					<% count ++; } } %>
					
					<% for(int i = count; i < 50; i++) { %>
						
						<tr>
							<td style="text-align: center;" > <%= i + 1 %> </td>
							<td>
								<input type="text" name="spMa" value="" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
							</td>
							<td><input type="text" name="spTen" value="" style="width: 100%" readonly="readonly" > </td>
							<td><input type="text" name="donvi" value="" style="width: 100%" readonly="readonly" ></td>
							<td><input type="text" name="soluong" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
							<td><input type="text" name="dongia" value="" style="width: 100%; text-align: right;" >
								<input type="hidden" name="spvat" value="" style="width: 100%; text-align: right;" > </td>
							<td><input type="text" name="thanhtien" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							<td><input type="text" name="scheme" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td> 
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
		dvtRs.close();
		kbhRs.close();
		nppRs.close();
		dvtRs.close();
		lsxBean.DBclose(); 
	}
	catch(Exception er){ }

	} %>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
