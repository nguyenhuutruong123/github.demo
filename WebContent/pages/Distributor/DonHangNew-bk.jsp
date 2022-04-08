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
<% IDonhang dhBean = (IDonhang)session.getAttribute("dhBean"); %>
<% List<ISanpham> splist = (List<ISanpham>)dhBean.getSpList(); %>
<% ResultSet ddkd = (ResultSet)dhBean.getDdkdList(); %>
<% ResultSet tbh = (ResultSet)dhBean.getTbhList(); %>
<% ResultSet kh = (ResultSet)dhBean.getKhList(); %>
<% ResultSet kho = (ResultSet)dhBean.getKhoList(); %>
<% Utility Ulti = new Utility();%>
<% String ngaykhoaso = Ulti.ngaykhoaso(dhBean.getNppId());%>
<% String userId = (String) session.getAttribute("userId"); %>
<% Hashtable<String, Integer> spThieuList = dhBean.getSpThieuList(); %>
<% Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); %>
<% Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); %>
<% List<ISanpham> scheme_sanpham = (List<ISanpham>)dhBean.getScheme_SpList(); %>
<% ResultSet gsbhs = (ResultSet)dhBean.getgsbhs(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="geso.dms.distributor.util.Utility"%><HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
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
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();})
</script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax-dynamic-list.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>

<script language="javascript" type="text/javascript">
function replaces()
{
	var masp = document.getElementsByName("masp");
	var tensp = document.getElementsByName("tensp");
	var donvitinh = document.getElementsByName("donvitinh");
	var dongia = document.getElementsByName("dongia");
	var chietkhau = document.getElementsByName("spchietkhau");
	var soluong = document.getElementsByName("soluong");
	var thanhtien = document.getElementsByName("thanhtien");
	var ctkm = document.getElementsByName("ctkm");
	var ckId = document.getElementById("ChietKhau");
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
				//donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				dongia.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
			}
			if(parseInt(soluong.item(i).value) > 0)
			{				
				var don_gia = dongia.item(i).value;
				//don_gia = don_gia.replace(".", "");
				//tinh chiet khau theo san pham
				var tck = (parseFloat(ckId.value) * parseInt(soluong.item(i).value) * parseFloat(don_gia)) / 100;
				chietkhau.item(i).value = tck; 
				//var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia) - parseFloat(chietkhau.item(i).value);
				var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia);
				thanhtien.item(i).value = tt;
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
			//ctkm.item(i).value = "";
			TinhTien();
		}
	}	
	setTimeout(replaces, 20);
	}
	
	replaces();
	
	function TinhTien()
	{
		var thanhtien = document.getElementsByName("thanhtien");
		//var chietkhau = document.getElementsByName("ChietKhau");
		var tongtien = 0;
		for(var i=0; i < thanhtien.length; i++)
		{
			if(thanhtien.item(i).value != "")
			{
				//var thanh_tien = thanhtien.item(i).value.replace(".", "");
				var thanh_tien = thanhtien.item(i).value;
				tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
			}
		}
		var tienchuaVAT = tongtien;
		document.getElementById("SoTienChuaVAT").value = tienchuaVAT;

	 
		var ck = document.getElementById("ChietKhau").value;
		if(ck == "")
			ck = "0";
		var tienchietkhau = (tienchuaVAT * parseFloat(ck)) / 100;

		document.getElementById("TienChietKhau").value = tienchietkhau;

		var tiensauCK = tienchuaVAT- tienchietkhau;
		document.getElementById("SoTienSauCK").value = parseFloat(tiensauCK);

		var vat = document.getElementById("VAT").value;
		if(vat == "")
			vat = "10";

		var tongtiencoVAT = (parseFloat(vat) * tiensauCK) / 100 + tiensauCK;
		document.getElementById("SoTienCoVAT").value = tongtiencoVAT;
		document.getElementById("SoTienCKKM").value = parseFloat(TongchietkhauKM());
		var SoTienSauCKKM =tongtiencoVAT - parseFloat(TongchietkhauKM());
		document.getElementById("SoTienSauCKKM").value = Math.round(SoTienSauCKKM);
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
	
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }

	function TongchietkhauKM()
	{
		var ckTrakm = document.getElementsByName("ckTrakm");
		var sum = 0;
		if(ckTrakm.length > 0)
		{
			for(h =0; h < ckTrakm.length; h++)
			{
				if(ckTrakm.item(h).value != "")
					sum = parseFloat(sum) + parseFloat(ckTrakm.item(h).value);
			}
		}
		return sum;
	}
	
	function saveform()
	 {	 
		 congDonSPCungMa();
		 var masp = document.getElementsByName("masp");
		 var tensp = document.getElementsByName("tensp");
		 var soluong = document.getElementsByName("soluong");
		 var ddkdId = document.getElementById("ddkdTen");
		 var khId = document.getElementById("khTen");
		 var khoId = document.getElementById("khoTen");
		 var gsbhId = document.getElementById("gsbhId");
		 if(ddkdId.value == "")
			{
				alert("Vui lòng kiểm tra giám sát bán hàng...");
				return;
			}
		 for(var k = 0; k < masp.length; k++)
		 {
			if(masp.item(k).value != "")
			{
				if(soluong.item(k).value == "" || soluong.item(k).value  == "0" || tensp.item(k).value == "")			
				{
					alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
					return;
				}
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
			
			
		 }

		 var ngaykhoaso = document.getElementById("ngaykhoaso").value;
		 var tungay = document.getElementById("tungay").value;
		// alert(ngaykhoaso +' '+ tungay);
		 if(ngaykhoaso.length >0 && tungay.length >0 ){
		 var ngay1 =Date.parse(ngaykhoaso);
		 var ngay2 = Date.parse(tungay);
		 	if(ngay1 >=  ngay2)
			{
				alert('Lỗi...Bạn đã khóa sổ đến ngày '+ngaykhoaso  +' rồi...');
				return;
			}
		 }
		 var SoTienCoVAT = document.getElementById("SoTienCoVAT").value;
		 var muctindung = document.getElementById("muctindung").value;
		// alert(SoTienCoVAT);
	//	alert(muctindung);
		 if(parseFloat(SoTienCoVAT) > parseFloat(muctindung) && parseFloat(muctindung) > 0 )
	     {
		  if(!confirm('Ban da vuot muc tin dung roi, ban co muon tiep tuc mua hang khong?'))
		  	return;
		 }
	 	 document.forms['dhForm'].action.value='save';
	     document.forms['dhForm'].submit();
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
		 document.forms['dhForm'].action.value='chotdonhang';
		 document.forms['dhForm'].submit();
	 }
	 
	 function Apkhuyenmai()
	 {
		 var SoTienCoVAT = document.getElementById("SoTienCoVAT").value;
	 	 var muctindung = document.getElementById("muctindung").value;
	 
		 if(parseInt(SoTienCoVAT) > parseInt(muctindung) &&  parseInt(muctindung) >0)
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
		 	if(ngay1 >=  ngay2)
			{
				alert('Lỗi...Bạn đã khóa sổ đến ngày '+ngaykhoaso  +' rồi...');
				return;
			}
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
			soluong.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");

			soluong.name = "soluong";
			soluongAdd.appendChild(soluong);
			
			var dvt = document.createElement("input");
			dvt.setAttribute("type", "textbox");
			dvt.setAttribute("readonly", "readonly");
			dvt.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
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
</script>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="dhForm" method="post" action="../../DonhangUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="nppId" value='<%= dhBean.getNppId() %>'>
<input type="hidden" name="ngaykhoaso" id = "ngaykhoaso" value='<%=ngaykhoaso %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="muctindung" id ="muctindung" value='<%=dhBean.getMuctindung()%>'>
<INPUT type="hidden" name="trangthai" value=''>   
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
			 								   <TD align="left"  class="tbnavigation">&nbsp;Xử lý đơn hàng > Đơn hàng bán > tạo mới </TD>								   
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
										    <TD width="30" align="left" ><A href="javascript:saveform()" ><img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
							    			<TD width="30" align="left"><A href="Print.jsp" ><img src="../images/Printer30.png" alt="In" title="In chung tu" longdesc="In chung tu" border=1 style="border-style:outset"></A></TD>
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
				    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" readonly="readonly" rows="1"><%= dhBean.getMessage() %></textarea>
										<% dhBean.setMessage(""); %>
									</FIELDSET>
							   </TD>
								</tr>
								<TR>
									<TD align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Đơn hàng bán </LEGEND>
										<TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>
											<TR>
											  <TD class="plainlabel">Ngày giao dịch </TD>
											  <TD colspan="3" class="plainlabel">
											  <table border=0 cellpadding="0" cellspacing="0">
                                                <TR><TD>
											    <input type="text" name="tungay" id ="tungay" style="width:100%" value="<%= dhBean.getNgaygiaodich() %>" >
											</TD><TD>
												<a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'dhForm.tungay',false, 'yyyy-mm-dd'); return false;">
		  &nbsp;<img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>
											</TD></TR>
                                          </table>	</TR>
											<TR >
												<TD width="22%" class="plainlabel">Nhà phân phối </TD>
												<TD colspan="3" class="plainlabel"><%= dhBean.getNppTen() %> </TD>
											</TR>
											<TR class="tblightrow">
												<TD  class="plainlabel">Giám sát bán hàng </TD>
												<TD colspan="3" class="plainlabel"> 
									 			<SELECT name="gsbhId" id="gsbhId" onChange = "submitform();">
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
												<TD  class="plainlabel">Nhân viên bán hàng </TD>
												<TD colspan="3" class="plainlabel"> 
									 			<SELECT name="ddkdTen" id="ddkdTen" onChange = "submitform();">
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
											<TR class="tblightrow">
												<TD  class="plainlabel">Tuyến bán hàng </TD>
												<TD colspan="3"  class="plainlabel"> 
									 			<SELECT name="tbhTen" onChange = "submitform();">
										 			 <option value="">&nbsp;</option>
													  <% if(tbh != null){
														  try{ while(tbh.next()){ 
											    			if(tbh.getString("tbhId").equals(dhBean.getTbhId())){ %>
											      				<option value='<%=tbh.getString("tbhId")%>' selected><%=tbh.getString("tbhTen") %></option>
											      			<%}else{ %>
											     				<option value='<%=tbh.getString("tbhId")%>'><%=tbh.getString("tbhTen") %></option>
											     			<%}}}catch(java.sql.SQLException e){} }%>	 
                                    			</SELECT></TD>
											</TR>
											<TR class="tblightrow">
												<TD  class="plainlabel">Khách hàng</TD>
												<TD colspan="3"  class="plainlabel"> 
									 			<SELECT name="khTen" id="khTen" onChange = "submitform();">
										 			 <option value="">&nbsp;</option>
													  <% if(kh != null){
														  try{ while(kh.next()){
											    			if(kh.getString("khId").equals(dhBean.getKhId())){ %>
											      				<option value='<%= kh.getString("khId") + " - " + kh.getString("chietkhau") %>' selected onMouseover="ddrivetip('<%= "Dia chi: " + kh.getString("diachi") %>', 300)"; onMouseout="hideddrivetip()"><%= kh.getString("khTen") + " " %></option>
											      			<%}else{ %>
											     				<option value='<%= kh.getString("khId") + " - " + kh.getString("chietkhau") %>' onMouseover="ddrivetip('<%= "Dia chi: " + kh.getString("diachi") %>', 300)"; onMouseout="hideddrivetip()"><%= kh.getString("khTen") + " " %></option>
											     			<%}}}catch(java.sql.SQLException e){} }%>
                                    			</SELECT></TD>
											</TR>
											<TR class="tblightrow">
												<TD  class="plainlabel">Kho hàng </TD>
												<TD colspan="3" class="plainlabel"> 
									 			<SELECT name="khoTen" id="khoTen">
										 			 <option value="">&nbsp;</option>
													  <% if(kho != null){
														  try{ while(kho.next()){ 
											    			if(kho.getString("khoId").equals(dhBean.getKhoId())){ %>
											      				<option value='<%= kho.getString("khoId") %>' selected onMouseover="ddrivetip('<%= kho.getString("diengiai") %>', 300)"; onMouseout="hideddrivetip()"><%= kho.getString("Ten") + " " %></option>
											      			<%}else{ %>
											     				<option value='<%= kho.getString("khoId") %>' onMouseover="ddrivetip('<%= kho.getString("diengiai") %>', 300)"; onMouseout="hideddrivetip()"><%= kho.getString("Ten") + " " %></option>
											     			<%}}}catch(java.sql.SQLException e){} }%>
                                    			</SELECT></TD>
											</TR>
											  <TR class="tblightrow">
											    <TD  class="plainlabel">Tổng số tiền (trước VAT) </TD>	     
										        <TD colspan="3"  class="plainlabel"><input name="SoTienChuaVAT" id="SoTienChuaVAT" type="text" value="<%=dhBean.getTongtientruocVAT()%>" readonly > VND </TD>
									       	</TR>

											  <TR class="tblightrow">
											    <TD  class="plainlabel">% Chiết khấu (khách hàng) </TD>
											    <TD width="9%"  class="plainlabel"><input name="ChietKhau" id="ChietKhau" type="text" value="<%= dhBean.getChietkhau() %>" onkeypress="return keypress(event);"></TD>
									            
									            <TD width="19%" class="plainlabel" align="right">Tổng chiết khấu</TD>
									            <TD width="72%" class="plainlabel"><input name="TienChietKhau" id="TienChietKhau" type="text" disabled="disabled" value="<%= dhBean.getTongChietKhau() %>"> VND </TD>
										   	</TR>

											<TR class="tblightrow">
											  <TD  class="plainlabel">Tổng số tiền (Sau chiết khấu)</TD>
											  <TD colspan="3" class="plainlabel"><input name="SoTienSauCK" id="SoTienSauCK" type="text" readonly 
											  	  value="<%=dhBean.getTongtiensauCK()%>" > VND </TD>
										  	</TR>

										    <TR class="tblightrow">
											  <TD  class="plainlabel">VAT (%) </TD>
											  <TD colspan="3"  class="plainlabel"><input name="VAT" id="VAT" type="text" value="<%= dhBean.getVAT() %>" onkeypress="return keypress(event);">%</TD>
										  	</TR>

											<TR class="tblightrow">
											  <TD  class="plainlabel">Tổng số tiền (sau VAT)</TD>
											  <TD colspan="3"  class="plainlabel">
											  	<input name="SoTienCoVAT" id="SoTienCoVAT" type="text" readonly value="<%= dhBean.getTongtiensauVAT()%>">
									          VND </TD>
										  	</TR>

											<TR class="tblightrow">
											  <TD  class="plainlabel">Tổng số tiền chiết khấu KM</TD>
											  <TD colspan="3"  class="plainlabel">
											  	<input name="SoTienCKKM" id="SoTienCKKM" type="text" readonly value="<%= dhBean.getTongtienCKKM() %>">
									          VND </TD>
										  	</TR>

												
											<TR class="tblightrow">
											  <TD  class="plainlabel">Tổng số tiền (sau chiết khấu KM)</TD>
											  <TD colspan="3"  class="plainlabel">
											  	<input name="SoTienSauCKKM" id="SoTienSauCKKM" type="text" readonly value="<%= dhBean.getTongtiensauCKKM() %>">
									          VND </TD>
										  	</TR>
												
										  <TR class="tblightrow">
										    <TD  class="plainlabel" colspan = 5>
										  <a class="button2" href="javascript:Apkhuyenmai()">
												<img style="top: -4px;" src="../images/New.png" alt="">Lưu & Áp khuyến mại</a>&nbsp;&nbsp;&nbsp;&nbsp;
											<a class="button2" href="javascript:ChotForm()">
												<img style="top: -4px;" src="../images/button.png" alt="">Chốt đơn hàng</a>&nbsp;&nbsp;&nbsp;&nbsp;	
		
										    <!-- 
											    <INPUT name="KiemTraTonsoluong" type="button" value="Kiem tra ton kho" onclick="submitform();" >
											    <INPUT name="ApKhuyenMai" type="button" value="Luu & Ap khuyen mai" onclick="Apkhuyenmai();">
											    <INPUT name="Chot" type="button" value="Chot don hang" onclick="ChotForm();"></TD>
											     -->
										  </TR>
										</TABLE>
									</FIELDSET>
								  </TD>
							   </TR>	
							   <TR>
							   		<TD>
										<TABLE width = "100%" border="0" cellpadding="0" cellspacing="1">
										<tbody id="san_pham">
												<TR class="tbheader" >
													<TH width="15%" height="20">Mã sản phẩm </TH>
													<TH width="28%">Tên sản phẩm</TH>
													<TH width="5%">Số lượng</TH>
													<TH width="7%">DVT</TH>
													<TH width="10%">Đơn giá </TH>
													<TH width="7%">Chiết khấu </TH>
													<TH width="9%">Thành tiền </TH>
													<TH width="10%" >CTKM</TH>
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
										<input name="tensp" type="text" readonly value="<%=sanpham.getTensanpham()%>" style="width:100%" ></TD>
																		
						        	<% if (spThieuList.containsKey(sanpham.getMasanpham())){ %>
									    <TD align = "center" ><div class="addspeech" title="San pham nay con toi da <%= spThieuList.get(sanpham.getMasanpham()) %> san pham, vui long chon lai so luong">
								        <input name="soluong" type="text" value="<%= sanpham.getSoluong() %>" onkeypress="return keypress(event);" style="width:100%; color:red ; cursor:pointer; background-color:#0FF; text-align:right">
								        </div></TD>
								    <%}else{ %>
							        	<TD align = "center" >
								        <input name="soluong" type="text" value="<%= sanpham.getSoluong() %>" style="width:100%" onkeypress="return keypress(event);" style="text-align:right">
								        </TD>
								    <%} %>
								    
								    <TD align = "center" ><input name="donvitinh" type="text" value="<%= sanpham.getDonvitinh() %>" readonly style="width:100%"></TD>
								    <TD align = "center" >
								    	<input type="text" name="dongia" value="<%= sanpham.getDongia() %>" readonly style="width:100% ;text-align:right">
								    </TD>
								    <TD align = "center" ><input name="spchietkhau" type="text" value="<%= sanpham.getChietkhau() %>" readonly style="width:100%;text-align:left"></TD>
								    <TD align = "center" ><input name="thanhtien" type="text" value="<%= sanpham.getChietkhau() %>" readonly  style="width:100% ;text-align:right"></TD>
								    <TD align = "center" ><input name="ctkm" type="text"  value="<%= sanpham.getCTKM() %>" readonly style="width:100%"></TD>
								</TR>
								<% m++; }}%>
								<%if(scheme_tongtien.size() > 0)
								{
									Enumeration<String> keys = scheme_tongtien.keys();
									while(keys.hasMoreElements())
									{
										String key = keys.nextElement(); %>
										<TR class= 'tblightrow'>
											<TD align="center" ><input type="text" style="width:100%" readonly></TD>
											<TD align="left" ><input type="text" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input name="ttTrakm" type="text" value="<%= "-" + Float.toString(scheme_tongtien.get(key)) %>" readonly style="width:100%;text-align:right"></TD>
										    <TD align = "center" ><input name="trakmScheme" type="text" style="width:100%" value="<%= key %>"  readonly></TD>
										</TR>
								<%}}%>						
								<%if(scheme_chietkhau.size() > 0)
								{
									//Float tonggiatri = dhBean.getTongtienchuaCK();
									Float tonggiatri = Float.parseFloat(dhBean.getTongtiensauVAT());
									
									//System.out.print("\n Tong gia tri dh la: " + dhBean.getTongtiensauVAT());
									
									Enumeration<String> keyss = scheme_chietkhau.keys();
									while(keyss.hasMoreElements())
									{
										String key = keyss.nextElement(); 
										long chietkhau = Math.round((scheme_chietkhau.get(key) / 100) * tonggiatri); %>
										<TR class= 'tblightrow'>
											<TD align="center" ><input type="text" style="width:100%" readonly></TD>
											<TD align="left" ><input type="text" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input name="ckTrakm"  type="text" value="<%= Float.toString(chietkhau) %>" readonly style="width:100%;text-align:right"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input name="trakmScheme" type="text" style="width:100%" value="<%= key %>"  readonly></TD>
										</TR>
								<%}}%>								
								<%if(scheme_sanpham.size() > 0)
								{
									ISanpham tkmSp = null;
									int count = 0;
									while(count < scheme_sanpham.size())
									{
										tkmSp = scheme_sanpham.get(count); %>
										<TR class = 'tblightrow'>
											<TD align="center" >
												<input name="maspTrakm" type="text" value="<%= tkmSp.getMasanpham() %>" style="width:100%" readonly>
											</TD>
											<TD align="left" >
												<input name="tenspTraKm" type="text" readonly value="<%= tkmSp.getTensanpham() %>" style="width:100%"></TD>
										    <TD align = "center" >
									        <input name="slgTraKm" type="text" value="<%= tkmSp.getSoluong() %>" style="width:100%;text-align:right" readonly></TD>
										    <TD align = "center" ><input name="dvtTrakm" type="text" value="<%= tkmSp.getDonvitinh() %>" readonly style="width:100%;text-align:center"></TD>
										    <TD align = "center" ><input name="dgTrakm" type="text" value="0" readonly style="width:100%;text-align:right"></TD>
										    <TD align = "center" ><input name="" type="text" value="0" readonly style="width:100%;text-align:right"></TD>
										    <TD align = "center" ><input name="" type="text" value="0" readonly style="width:100% ;text-align:right"></TD>
										    <TD align = "center" ><input name="trakmScheme" type="text" style="width:100%" value="<%= tkmSp.getCTKM() %>" style="width:100%" readonly></TD>
										</TR>
										
								<% count++; }}%>								
								<%
									int i=0;
									while(i < 20){ //Integer.parseInt(dhBean.getSize())){
								%>
								<TR class= 'tblightrow'>
									<TD align="center" >
										<input name="masp" type="text" value="" onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" AUTOCOMPLETE="off">
									</TD>
									<TD align="left" >
										<input name="tensp" type="text" readonly value="" style="width:100%"></TD>
								    <TD align = "center" >
							        <input name="soluong" type="text" value="" style="width:100%; text-align:right" onkeypress="return keypress(event);"></TD>
								    <TD align = "center" ><input name="donvitinh" type="text" value="" readonly style="width:100%"></TD>
								    <TD align = "center" ><input name="dongia" type="text" value="" readonly style="width:100%;text-align:right"></TD>
								    <TD align = "center" ><input name="spchietkhau" type="text" value="" readonly style="width:100%;text-align:right"></TD>
								    <TD align = "center" ><input name="thanhtien" type="text" value="" readonly style="width:100%;text-align:right"></TD>
								    <TD align = "center" ><input name="ctkm" type="text" style="width:100%" value="" readonly ></TD>
								</TR>
								<% i++;} %>	
								</tbody></TABLE>
						&nbsp;&nbsp;&nbsp;&nbsp;<a class="button2" href="javascript:ThemSanPham()">
	                         <img style="top: -4px;" src="../images/add.png" alt="">Thêm sản phẩm</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<!--<input type="button" value=" Them San Pham " onclick="ThemSanPham();" />  -->																																																																																																							
			
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
	//Call dropdowncontent.init("anchorID", "positionString", glideduration, "revealBehavior") at the end of the page:
	dropdowncontent.init("searchlink", "right-bottom", 500, "click");
</script>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
	if(!(ddkd == null))
		ddkd.close();
	if(!(tbh == null))
		tbh.close();
	if(dhBean != null){
		dhBean.DBclose();
		dhBean = null;
	}
	
%>
