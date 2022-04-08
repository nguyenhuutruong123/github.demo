<%@page import="geso.dms.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.dms.center.beans.dondathang.*" %>
<%@ page  import = "geso.dms.center.beans.dondathang.imp.*" %>
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

<% IErpDuyetddh lsxBean = (IErpDuyetddh)s.getAttribute("lsxBean");
   IErpDuyetddhList lsxBeanList = (IErpDuyetddhList)s.getAttribute("duyetddhList");
%>

<% ResultSet dvkdRs = lsxBean.getDvkdRs(); %>
<% ResultSet kbhRs = lsxBean.getKbhRs(); %>
<% ResultSet nppRs = lsxBean.getNppRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet dvtRs = lsxBean.getDvtRs(); %>
<% ResultSet congnoRs = lsxBean.getCongnoRs(); %>

<% 
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spGianhap = lsxBean.getSpGianhap();
	String[] spChietkhau = lsxBean.getSpChietkhau();
	String[] spSCheme = lsxBean.getSpScheme();
	String[] spTrongluong = lsxBean.getSpTrongluong();
	String[] spThetich = lsxBean.getSpThetich();
	
	String[] dhCk_diengiai = lsxBean.getDhck_diengiai();
	String[] dhCk_giatri = lsxBean.getDhck_giatri();
	String[] dhCk_loai = lsxBean.getDhck_loai();
	
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
					
					trongluong.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					thetich.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
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
				trongluong.item(i).value = "";	
				thetich.item(i).value = "";		
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
		var chietkhau = document.getElementsByName("chietkhau");
		var thanhtien = document.getElementsByName("thanhtien");
		
		var trongluong = document.getElementsByName("spTrongLuong");
		var thetich = document.getElementsByName("spTheTich");
		
		var tongtien = 0;
		var tongtienCK = 0;
		
		var totalTL = 0;
		var totalTT = 0;
		
		for(var i = 0; i < spMa.length; i++)
		{
			if(spMa.item(i).value != "" && dongia.item(i).value != "" && soluong.item(i).value != "" )
			{
				var _ck = chietkhau.item(i).value.replace(/,/g,"");
				if(_ck == '')
					_ck = '0';
				
				var tt = parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")) - parseFloat(_ck);
				thanhtien.item(i).value = DinhDangTien(tt);
				
				tongtien += tt;
				tongtienCK += parseFloat(_ck);
			}
			
			if(trongluong.item(i).value != '' && soluong.item(i).value != '' )
				totalTL += parseFloat(trongluong.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,""));
			
			if(thetich.item(i).value != '' && soluong.item(i).value != '' )
				totalTT += parseFloat(thetich.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,""));
			
		}
		
		document.getElementById("txtTongTL").value = DinhDangTien(totalTL);
		document.getElementById("txtTongTT").value = DinhDangTien(totalTT);
		
		
		var dhCK_diengiai = document.getElementsByName("dhCK_diengiai");
		var dhCK_giatri = document.getElementsByName("dhCK_giatri");
		var dhCK_loai = document.getElementsByName("dhCK_loai");
		
		var tongDhCK = 0;
		var tongtien_sau_hoahong = 0;
		
		for(var j = 0; j < dhCK_giatri.length; j++ )
		{
			if(dhCK_giatri.item(j).value != '' )
			{
				var loai = dhCK_loai.item(j).value;
				if(loai == '0') //tien
					tongDhCK += parseFloat(dhCK_giatri.item(j).value.replace(/,/g,""));
				else  //CHIET KHAU
				{
					if( j == 0)
					{
						var tt_hoahong = parseFloat(dhCK_giatri.item(j).value.replace(/,/g,"")) * parseFloat(tongtien) / 100;
						tongtien_sau_hoahong = tongtien - tt_hoahong;
						tongDhCK += parseFloat(tt_hoahong);
					}
					else
					{
						tongDhCK += parseFloat(dhCK_giatri.item(j).value.replace(/,/g,"")) * parseFloat(tongtien_sau_hoahong) / 100;
					}
					
				}
			}
		}
		
		tongtienCK += parseFloat(tongDhCK);
		
		var vat = document.getElementById("txtVAT").value;
		if(vat == '')
			vat = '0';
		
		//var tongtienCK = parseFloat(chietkhau) * parseFloat(tongtien) / 100;

		var tongtienSAUCK = parseFloat(tongtien) - parseFloat(tongtienCK);
		
		document.getElementById("txtTongCK").value = DinhDangTien(tongtienCK);
		document.getElementById("txtBVAT").value = DinhDangTien(tongtien);
		document.getElementById("txtTongSauCK").value = DinhDangTien(tongtienSAUCK);
		
		var tongtienSAUVAT = parseFloat(tongtienSAUCK) + ( parseFloat(vat) * parseFloat(tongtienSAUCK) / 100 );
		document.getElementById("txtSauVAT").value = DinhDangTien(tongtienSAUVAT);
		
	 }
	
	 function saveform()
	 {	
		 /* if(parseFloat(document.getElementById("sotienthu").value.replace(/,/g,"")) <= 0 )
		 {
			alert('Bạn phải nhập số tiền thu của NPP');
			return;
		 } */
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function duyetform()
	 {	
		 /* if(parseFloat(document.getElementById("sotienthu").value.replace(/,/g,"")) <= 0 )
		 {
			alert('Bạn phải nhập số tiền thu của NPP');
			return;
		 } */
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'duyet';
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

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="hctmhForm" method="post" action="../../ErpDuyetddhUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= lsxBean.getId() %>'>
<input type="hidden" name="loaidonhang" value='<%= lsxBean.getLoaidonhang() %>'>
<%-- 
<input type="hidden" name="tungaytk" value='<%= lsxBeanList.getTungay() %>'>
<input type="hidden" name="denngaytk" value='<%= lsxBeanList.getDenngay() %>'>
<input type="hidden" name="vungtk" value='<%= lsxBeanList.getVungId() %>'>
<input type="hidden" name="khuvuctk" value='<%= lsxBeanList.getKhuvucId() %>'>
<input type="hidden" name="kbhtk" value='<%= lsxBeanList.getKbhId() %>'>
<input type="hidden" name="npptk" value='<%= lsxBeanList.getNppTen() %>'>
<input type="hidden" name="trangthaitk" value='<%= lsxBeanList.getTrangthai()%>'> --%>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý kho trung tâm > Đơn đặt hàng > Duyệt đơn đặt hàng
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<%-- <A href= "../../ErpDuyetddhSvl?userId=<%= userId %>&loaidonhang=<%= lsxBean.getLoaidonhang() %>" > --%>
    	<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        	
        <span id="btnSave">
	        <A href="javascript:saveform();" >
	        	<IMG src="../images/Save.png" title="Lưu đơn đặt hàng" alt="Lưu đơn đặt hàng" border ="1px" style="border-style:outset"></A>
        </span>
        
        <span id="btnChot">
	        <A href="javascript:duyetform();" >
	        	<IMG src="../images/Chot.png" title="Duyệt đơn đặt hàng" alt="Duyệt đơn đặt hàng" border ="1px" style="border-style:outset"></A>
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
    	<legend class="legendtitle">Đơn đặt hàng </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="130px" class="plainlabel" valign="top">Ngày đơn hàng </TD>
                    <TD width="250px"  class="plainlabel" valign="top" >
                    	<input type="text" readonly="readonly"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/>
                    </TD>
                    
                    <TD width="120px" class="plainlabel" valign="top">Ngày đề nghị giao </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly"  name="ngaydenghi" value="<%= lsxBean.getNgaydenghi() %>"/>
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" valign="top">Đơn vị kinh doanh </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<input type="hidden" name="dvkdId" value="<%= lsxBean.getDvkdId() %>" >
                    	<select class="select2" style="width: 200px" disabled="disabled" >
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
                    <TD class="plainlabel" valign="top">Kênh bán hàng </TD>
                    <TD class="plainlabel" valign="top"   >
                    	<input type="hidden" name="kbhId" value="<%= lsxBean.getKbhId() %>" >
                    	<select class="select2" style="width: 200px" disabled="disabled" >
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
                	<TD class="plainlabel" valign="top">Nhà phân phối </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<input type="hidden" name="nppId" value="<%= lsxBean.getNppId() %>" >
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
                    <TD class="plainlabel" valign="top">Kho đặt </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<input type="hidden" name="khonhapId" value="<%= lsxBean.getKhoNhapId() %>" >
                    	<select class="select2" style="width: 200px" disabled="disabled" >
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
                    <TD class="plainlabel" valign="top">Tổng thành tiền </TD>
                    <TD class="plainlabel" valign="top" colspan="3" >
                    	<input type="text" readonly="readonly"  id="txtBVAT" value="" style="text-align: right;"  />
                    	
                    
                    	<input type="hidden"  value="<%= lsxBean.getChietkhau() %>" id="txtPTChietKhau" style="text-align: right;" name="ptChietkhau" disabled="disabled" />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" valign="top">Tổng tiền chiết khấu </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text" readonly="readonly"  value="" id="txtTongCK" style="text-align: right; width: 170px; " />
                    	
                    	<a href="" id="popupCHIETKHAU" rel="subcontentCK">
		           	 				&nbsp; <img alt="Khai báo chiết khấu" src="../images/vitriluu.png" title="Khai báo chiết khấu" ></a>
		           	 	
			           	 	<DIV id="subcontentCK" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					                             background-color: white; width: 500px; max-height:300px; overflow:auto; padding: 4px;">
				                    <table width="95%" align="center">
				                        <tr>
				                            <th width="250px" style="font-size: 12px; text-align: center;">Diễn giải</th>
				                            <th width="80px" style="font-size: 12px; text-align: center;">Giá trị</th>
				                            <th width="80px" style="font-size: 12px; text-align: center;">Đơn vị</th>
				                        </tr>
				                        <%
				                        	int sodong = 0;
				                        	if(dhCk_diengiai != null)
				                        	{
				                        		for(int i = 0; i < dhCk_diengiai.length; i++)
				                        		{ 
				                        		 	sodong ++;	%>
				                        	<tr>
				                        		<td><input name="dhCK_diengiai" type="text" style="width: 100%;" value="<%= dhCk_diengiai[i]  %>" readonly="readonly" > </td>
				                        		<td><input name="dhCK_giatri" type="text" style="width: 100%;" value="<%= dhCk_giatri[i]  %>" readonly="readonly" > </td>
				                        		<td>
				                        			<select name="dhCK_loai" style="width: 100%" >
					                        			<% if( dhCk_loai[i].equals("0")  ) { %>
					                        				<option value="0" selected="selected" >Tiền</option>
					                        				<option value="1" >%</option>
					                        			<% } else { %>
					                        				<option value="0" >Tiền</option>
					                        				<option value="1" selected="selected" >%</option>
					                        			<% } %>
				                        			</select>
				                        		</td>
				                        	</tr>
				                      <% } } %>
				                      
				                      <% for(int i = sodong; i < 4; i++ ) { %>
				                      		
				                      		<tr>
				                        		<td><input name="dhCK_diengiai" type="text" style="width: 100%;" value="" > </td>
				                        		<td><input name="dhCK_giatri" type="text" style="width: 100%;" value="" readonly="readonly" > </td>
				                        		<td>
				                        			<select name="dhCK_loai" style="width: 100%" >
				                        				<option value="0" >Tiền</option>
				                        				<option value="1" >%</option>
				                        			</select>
				                        		</td>
				                        	</tr>
				                      		
				                      <% } %>
				                      
				                   </table>
				                   
				                   <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentCK')" ><b> Đóng lại </b></a>
				                   </div>
				                   
					       </DIV>
                    	
	                    	<script type="text/javascript">
				            	dropdowncontent.init('popupCHIETKHAU', "right-top", 300, "click");
				            </script>
                    	
                    </TD>
                    	
                    <TD class="plainlabel" valign="top">Tổng tiền sau CK </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly"  value="" id="txtTongSauCK" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" valign="top">VAT </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text" value="<%= lsxBean.getVat() %>" id="txtVAT" style="text-align: right;" name="ptVat" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" /> %
                    </TD>
                    	
                    <TD class="plainlabel" valign="top" style="color: red;" >Tổng tiền sau VAT </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly"  value="" id="txtSauVAT" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" valign="top">Tổng trọng lượng </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text" value="" id="txtTongTL" style="text-align: right;" readonly="readonly" /> gram
                    </TD>
                    	
                    <TD class="plainlabel" valign="top" >Tổng thể tích </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" value="" id="txtTongTT" style="text-align: right;" readonly="readonly"  /> cm3
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" style="color: red;" >Số tiền thu của NPP </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="sotienthu" id="sotienthu" value='<%= lsxBean.getChietkhau() %>' onkeyup="Dinhdang(this);" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Ghi chú </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 700px;" />
                    </TD>
                </TR>
                
                
               
            </TABLE>
			
			<ul class="tabs">
		                  			
         		<li><a href="#">Thông tin đơn hàng</a></li>
          		<li><a href="#">Công nợ</a></li>

          	</ul>
             			
            <div class="panes">
              										
			<div>
			
				<table cellpadding="0px" cellspacing="1px" width="100%">
					<tr class="tbheader">
						<th align="center" width="10%" >Mã sản phẩm</th>
						<th align="center" width="25%" >Tên sản phẩm</th>
						<th align="center" width="10%" >Đơn vị</th>
						<th align="center" width="10%" >Số lượng</th>
						<th align="center" width="10%" >Đơn giá</th>
						<th align="center" width="10%" >Chiết khấu</th>
						<th align="center" width="10%" >Thành tiền</th>
						<th align="center" width="15%" >Scheme</th>
					</tr>
					<!-- <tr class="tbheader">
						<th align="center" width="7.5%">Thùng</th>
						<th align="center" width="7.5%">Lẻ</th>
						<th align="center" width="7.5%">Thùng</th>
						<th align="center" width="7.5%">Lẻ</th>
					</tr> -->
					
					<%
						int count = 0;
						if(spMa != null)
						{
							for(int i = 0; i < spMa.length; i++)
							{%>
						
							<tr>
								<% if(spSCheme[i].trim().length() <= 0 ) { %>
									<td>
										<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%"  readonly="readonly"  > 
										<input type="hidden" name="spTrongLuong" value="<%= spTrongluong[i] %>" > 
										<input type="hidden" name="spTheTich" value="<%= spThetich[i] %>" > 
									</td>
									<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%" readonly="readonly"> </td>
									<td>
										<%-- <input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%" readonly="readonly"> --%>
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
									</td>
									
									<td> <input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > </td>
									<td> <input type="text" name="dongia" value="<%= spGianhap[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > </td>
									<td> <input type="text" name="chietkhau" value="<%= spChietkhau[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > </td>
									
									<td> <input type="text" name="thanhtien" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
									<td> <input type="text" name="scheme" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
								<% } else { %>
									<td>
										<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%; color: red;"  readonly="readonly"  > 
										<input type="hidden" name="spTrongLuong" value="<%= spTrongluong[i] %>" > 
										<input type="hidden" name="spTheTich" value="<%= spThetich[i] %>" > 
									</td>
									<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%; color: red;" readonly="readonly"> </td>
									<td>
										<input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%; color: red;" readonly="readonly">
									</td>
									
									<td> <input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width: 100%; color: red; text-align: right;" readonly="readonly" > </td>
									<td> <input type="text" name="dongia" value="" style="width: 100%; text-align: right; color: red;" readonly="readonly" > </td>
									<td> <input type="text" name="chietkhau" value="" style="width: 100%; text-align: right; color: red;" readonly="readonly" > </td>
									
									<td> 
										<% if(spSoluong[i].trim().length() > 0) { %>
											<input type="text" name="thanhtien" value="0" style="width: 100%; color: red; text-align: right;" readonly="readonly" > 
										<% } else { %>
											<input type="text" name="thanhtien" value="-<%= spGianhap[i] %>" style="width: 100%; color: red; text-align: right;" readonly="readonly" > 
										<% } %>
									</td>
									<td> 
										<input type="text" name="scheme" value="<%= spSCheme[i] %>" style="width: 100%; color: red; " readonly="readonly" > 
									</td>
								<% } %>
							</tr>	
								
						<% count ++; } } %>
					
				</table>
			</div>
			
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
		lsxBean.DBclose(); 
	}
	catch(Exception er){ }

	} %>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>