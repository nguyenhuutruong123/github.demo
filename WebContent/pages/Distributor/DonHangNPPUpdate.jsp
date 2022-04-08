<%@page import="geso.dms.center.beans.nhaphanphoi.imp.Nhaphanphoi"%>
<%@page import="geso.dms.distributor.beans.donhangnhapp.imp.DonHangNPP"%>
<%@page import="geso.dms.distributor.beans.donhangnhapp.imp.SanPhamDhNpp"%>
<%@page import="geso.dms.center.beans.dieukienkhuyenmai.imp.Sanpham"%>
<%@page import="geso.dms.distributor.beans.donhangnhapp.ISanPhamDhNpp"%>
<%@page import="geso.dms.distributor.beans.donhangnhapp.IDonhangnpp"%>
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
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<% IDonhangnpp dhBean = (IDonhangnpp)session.getAttribute("obj"); %>
<% List<ISanPhamDhNpp> splist = (List<ISanPhamDhNpp>)dhBean.getSanPhamList(); %>
<% 
  	Hashtable<String,Integer> spthieu =dhBean.getSPThieuList(); 
	ResultSet rskho=dhBean.getrskho();
	ResultSet rskenh=dhBean.getrskenh();
  %>
<%ResultSet npp= dhBean.GetRs_NhappBan();  %> <!-- Truyen vao 1 list cac nha phan phoi,de load ra dropdownlist nha phan phoi -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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
		 $('.addspeech').speechbubble();
	})
</script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_bangianpp-mua.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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

<script language="javascript" type="text/javascript">
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
function submitform(){
	congDonSPCungMa();
	document.getElementById("btnKTraTK").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

	document.forms['dhForm'].action.value='submit';
    document.forms["dhForm"].submit();
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
function saveform()
{	 
	 congDonSPCungMa();
	 var masp = document.getElementsByName("masp");
	 var tensp = document.getElementsByName("tensp");
	 var soluong = document.getElementsByName("soluong");
	 var nhappmua = document.getElementById("nhappmua");
	
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
	 }
		if(nhappmua.value == "")
		{
			alert("vui lòng chọn nhà phân phối mua hàng...");
			return;
		}
		
		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

		
	 document.forms['dhForm'].action.value='update';//de serverlet hieu dc day la su kien sua don hang
     document.forms['dhForm'].submit();
}
function replaces()
{
	var masp = document.getElementsByName("masp");
	var tensp = document.getElementsByName("tensp");
	var donvitinh = document.getElementsByName("donvitinh");
	var dongia = document.getElementsByName("dongia");
	//var chietkhau = document.getElementsByName("spchietkhau");
	var soluong = document.getElementsByName("soluong");
	var thanhtien = document.getElementsByName("thanhtien");
	
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
				dongia.item(i).value = DinhDangTien( sp.substring(0, parseInt(sp.indexOf("]"))) );
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
				
			   var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia);
				thanhtien.item(i).value = DinhDangTien(tt);
				TinhTien();
			}
			else			
			{
				
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
			soluong.item(i).value = "";
			thanhtien.item(i).value = "";
			TinhTien();
		}
	}	
	setTimeout(replaces, 20);//goi lai sau 20ms
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
	function addRow(name)
	{
		tableName = document.getElementById(name);
		
		var tr = document.createElement("TR");
		var maspAdd = document.createElement("TD");
		var tenspAdd = document.createElement("TD");
		var dvtinhAdd = document.createElement("TD");
		var soluongAdd= document.createElement("TD");
		var dongiaAdd = document.createElement("TD");
		var thanhtienAdd = document.createElement("TD");
		
		var masp = document.createElement("input");
		masp.setAttribute("type", "textbox");
		masp.setAttribute("onkeyup", "ajax_showOptions(this,'abc',event)");
		masp.setAttribute("autocomplete", "off");
		masp.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");

		masp.name = 'masp';
		maspAdd.appendChild(masp);
		
		var tensp = document.createElement("input");
		tensp.setAttribute("type", "textbox");
		tensp.setAttribute("readonly", "readonly");
		tensp.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");

		tensp.name = 'tensp';
		tenspAdd.appendChild(tensp);
		
		var dvt = document.createElement("input");
		dvt.setAttribute("type", "textbox");
		dvt.setAttribute("readonly", "readonly");
		dvt.value = "";
		dvt.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
		dvt.name = 'donvitinh';
		dvtinhAdd.appendChild(dvt);
		
		var soluong = document.createElement("input");
		soluong.setAttribute("type", "textbox");
		soluong.setAttribute("onkeypress","return keypress(event)");
		soluong.value = "";
		soluong.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
		soluong.name = "soluong";
		soluongAdd.appendChild(soluong);
		

		
		var dongia = document.createElement("input");
		dongia.setAttribute("type", "textbox");
		dongia.setAttribute("readonly", "readonly");
		dongia.value = "";
		dongia.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
		dongia.name = 'dongia';
		dongiaAdd.appendChild(dongia);
		
		
		var thanhtien = document.createElement("input");
		thanhtien.setAttribute("type", "textbox");
		thanhtien.setAttribute("readonly", "readonly");
		thanhtien.value = "";
		thanhtien.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
		thanhtien.name = "thanhtien";
		thanhtienAdd.appendChild(thanhtien);

		tr.appendChild(maspAdd);
		tr.appendChild(tenspAdd);
		tr.appendChild(dvtinhAdd);
		tr.appendChild(soluongAdd);
		tr.appendChild(dongiaAdd);
		tr.appendChild(thanhtienAdd);
		
		tableName.appendChild(tr);
	}
	
	
	
	
function TinhTien()
{
	var thanhtien = document.getElementsByName("thanhtien");
	//var chietkhau = document.getElementsByName("ChietKhau");
	var tongtien = 0;
	for(var i=0; i < thanhtien.length; i++)
	{
		if(thanhtien.item(i).value != "")
		{
			var thanh_tien = thanhtien.item(i).value;
			while(thanh_tien.match(","))
			{
				thanh_tien = thanh_tien.replace(",","");
			}
			tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
		}
	}
		ck = "0";
	var tienchuaVAT = tongtien ;
	document.getElementById("SoTienChuaVAT").value =DinhDangTien( tienchuaVAT);
	var vat = document.getElementById("VAT").value;
	if(vat == "")
		vat = "10";
	document.getElementById("SoTienCoVAT").value =DinhDangTien( (parseFloat(vat) * tienchuaVAT) / 100 + tienchuaVAT );
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

function roundNumber(num, dec) 
{
	var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
	return result;
}
 
</script>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="dhForm" method="post" action="../../DonHangNppUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="nppId" value='<%= dhBean.getNppId_Ban() %>'>
<input type="hidden" name="action" value='update'>
<input type="hidden" name="formname" value='formupdate'>
<INPUT type="hidden" name="id" value='<%= dhBean.getId() %>'>
<INPUT type="hidden" name="trangthai" id="trangthaiDh" value='<%= dhBean.getTrangthai() %>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
				<TABLE border =0 width = "100%" cellpadding="2" cellspacing="0">
				<TBODY>
					<TR height="22">
						<TD align="left" >
							<TABLE width="100%" cellpadding="0" cellspacing="0" style="margin:5px " >
								<TR>
									<TD align="left">
									   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										   <TR height="22">
			 								   <TD align="left"  class="tbnavigation">&nbsp;Quản lý bán hàng > Đơn hàng bán>Bán cho nhà phân phối > Cập nhật </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng bạn <%= dhBean.getTenNPPBan() %> &nbsp; </TD>
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
										    <TD width="30" align="left" >
										    <div id="btnSave">
										    <A href="javascript:saveform()" ><img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A>
										    </div>
										    </TD>
										    <TD width="2" align="left" >&nbsp;</TD>
							    			<TD width="30" align="left"> 
								    			    <div id="btnprint">
								    			   <A href="../../XuatKhoPdfSvl?userId=<%=userId%>&pdf=<%=dhBean.getId()%>&type=DonBanNPP" ><img src="../images/Printer30.png" alt="In" title="In chung tu" longdesc="In chung tu" border=1 style="border-style:outset"></A> 
								    				</div>
							    			</TD>
								    		<TD align="left" >&nbsp;</TD>
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="0" width="100%" cellpadding = "0" cellspacing = "0" >
								<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>			
				    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width:100%"  rows="1" readonly="readonly" title="<%=  dhBean.getthongbao() %>" > </textarea>
									</FIELDSET>
							   </TD>
								</tr>
								<TR>
									<TD  align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Đơn hàng bán </LEGEND>
										<TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>
											
											<TR >
												<TD width="22%" class="plainlabel">Nhà phân phối bán hàng</TD>
												<TD colspan="3" class="plainlabel"><%= dhBean.getTenNPPBan() %> </TD>
												</TR>							
											
											<TR class="tblightrow">
												<TD  class="plainlabel">Nhà phân phối mua hàng</TD>
												<TD colspan="3" class="plainlabel"> 
												<SELECT name="nhappmua" id="nhappmua">
												<!-- Cach tao mot combobox -->
										 			 <option value=""> </option>
													  <% int i=0; 
													  if(npp!= null){
														  try{ while(npp.next()){ 
															
															  if(npp.getString("pk_Seq").trim().equals(dhBean.getNppId_Mua()))
															  {
																%>  <option  selected="selected" value='<%=npp.getString("pk_Seq").trim()%>'  ><%=npp.getString("ten").trim() %></option><%
															  }
															  else
															  {
											    			%>
											     				<option value='<%=npp.getString("pk_Seq").trim()%>'><%=npp.getString("ten").trim()%></option>
											     			<% 
															  }i++; } }catch(Exception e){
											     				//System.out.println("Error : 194 DonHangNhaPPNew ; "+ e.toString());
											     			}} %>	 
                                    			</SELECT> 
												 </TD>
												
											</TR>
											<TR class="plainlabel">
											<TD  class="plainlabel">Kênh bán hàng</TD>
												<TD colspan="3" class="plainlabel"> 
												<select name="kenhbh"  >
												<%
												if(rskenh!= null){
													  try{ while(rskenh.next()){ 
													
														  if(rskenh.getString("kbh_fk").trim().equals(dhBean.getKenhBanHang()))
														  {
															%>  <option  selected="selected" value='<%=rskenh.getString("kbh_fk").trim()%>'  ><%=rskenh.getString("ten").trim() %></option><%
														  }
														  else
														  {
														%>
											     				<option value="<%=rskenh.getString("kbh_fk").trim()%>"> <%=rskenh.getString("ten").trim() %></option>
											     			<% 
														  }} }catch(Exception e){
										     				System.out.println("Error : 194 DonHangNhaPPNew ; "+ e.toString());
										     			}} %>	
												</select>
												</TD>
											</TR>
											<TR class="plainlabel">
											<TD  class="plainlabel">Kho hàng</TD>
												<TD colspan="3" class="plainlabel"> 
												<SELECT name="khobh" id="khobanhang">
												<!-- Cach tao mot combobox -->
										 			 <option value=""> </option>
													  <%
													  if(rskho!= null){
														  try{ while(rskho.next()){ 
														
															  if(rskho.getString("pk_Seq").trim().equals(dhBean.getKho()))
															  {
																%>  <option  selected="selected" value='<%=rskho.getString("pk_Seq").trim()%>'  ><%=rskho.getString("ten").trim()+"-"+ rskho.getString("diengiai").trim()%></option><%
															  }
															  else
															  {
																	%>
												     				<option value="<%=rskho.getString("pk_Seq").trim()%>"> <%=rskho.getString("ten").trim()+"-"+rskho.getString("diengiai").trim() %></option>
												     			<% 
															  }i++; } }catch(Exception e){
											     				System.out.println("Error : 194 DonHangNhaPPNew ; "+ e.toString());
											     			}} %>	 
                                    			</SELECT> 
												</TD>
											</TR>
												<TR >
											  <TD class="plainlabel">Ngày giao dịch </TD>
											  <TD colspan="3" class="plainlabel">
											  <TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
											    <input class="days" type="text" name="tungay" size="11" value="<%= dhBean.getNgayGiaoDich() %>">
											</TD></TR>
										</TABLE>
											  </TR>
											  <TR class="tblightrow">
											    <TD  class="plainlabel">Tổng số tiền (trước VAT) </TD>	     
										        <TD colspan="3"  class="plainlabel"><input name="SoTienChuaVAT" id="SoTienChuaVAT" type="text" value="<%= dhBean.getTongGiaTri() %>" readonly > VND </TD>
									       	</TR>
										    <TR class="tblightrow">
											  <TD  class="plainlabel">VAT (%) </TD>
											  <TD colspan="3"  class="plainlabel"><input name="VAT" id="VAT" type="text" value="<%= dhBean.getVAT() %>" readonly>%</TD>
										  	</TR>
										  	<%
										  	
										  			 double tienchuathue=(dhBean.getTongGiaTri());
													 double tiencothue=tienchuathue+tienchuathue/100*Double.parseDouble(dhBean.getVAT());
											 
											  %>
											<TR class="tblightrow">
											  <TD  class="plainlabel">Tổng số tiền (sau VAT)</TD>
											    <TD colspan="3"  class="plainlabel"><input name="SoTienCoVAT" id="SoTienCoVAT" type="text" value="<%= tiencothue %>" readonly>%</TD>
										  	</TR>
											
										</TABLE>
									</FIELDSET>
								  <div style=" margin: 5px">	&nbsp;&nbsp;	&nbsp;&nbsp;   
								  <label id="btnKTraTK">
								  <a class="button2" href="javascript:submitform()">
	<img style="top: -4px;" src="../images/button.png" alt="" >Kiểm tra tồn kho</a>
									</label>
	&nbsp;&nbsp;	&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;</div>
								  </TD>
							   </TR>	
							   <TR>
							   		<TD>
										<TABLE width = "100%"  cellpadding="0" cellspacing="1px" >
										<tbody id="san_pham">
												<TR class="tbheader" >
													<TH width="15%">Mã sản phẩm </TH>
													<TH width="28%">Tên sản phẩm </TH>
													<TH width="7%">DVT</TH>
													<TH width="5%">Số lượng </TH>
													<TH width="10%">Đơn giá </TH>
													<TH width="10%">Thành tiền </TH>
												</TR>
									<% 
							if(splist != null){
							ISanPhamDhNpp sanpham =new SanPhamDhNpp();
							int size = splist.size();
							int m = 0;
							while (m < size){
								sanpham = splist.get(m); 
								%>
									<TR class= 'tblightrow' >
									
										 <TD align="left" >
									<input name="masp" type="text"  autocomplete='off' value="<%=sanpham.getMaSanPham()%>"  style="width:100%;">
									</TD>
									<TD align="left" >
										<input name="tensp" type="text" readonly value="<%=sanpham.getTenSanPham()%>" onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%"></TD>
									 <TD align = "center" ><input name="donvitinh" type="text" value="<%= sanpham.getDVT() %>" readonly  style="text-align:center ;width:100%"></TD>
						        <% 
						        if ( spthieu.containsKey(sanpham.getMaSanPham())){ %>
									<TD align="left" >
									<input name="soluong" type="text"  value="<%=sanpham.getSoLuong()%>" title="Số lượng tối đa có thể đặt hàng là : <%=spthieu.get(sanpham.getMaSanPham()) %>" style="width:100%; background-color: gray;" onkeypress="return keypress(event);">
									</TD>
									<% }else 
									{ 
										%>
						        	<TD align="left" >
										<input name="soluong" type="text"  value="<%=sanpham.getSoLuong()%>" style="width:100%" onkeypress="return keypress(event);"></TD>
								<%} %>
								   	  <TD align = "center" >
								    	<input type="text" name="dongia" value="<%= sanpham.getGiaMua() %>" readonly style="text-align:right;width:100%">
								   <TD align = "center" >
								    	<input type="text" name="thanhtien" value="<%= sanpham.getThanhTien() %>" readonly  style="text-align:right;width:100%">
								     </TD>
								</TR>
								<% m++; }}%>
								<% if(!dhBean.getTrangthai().equals("3")){
									int soSp=0;
									while(soSp < 10){ 
								%>
								<TR class= 'tblightrow'>
									<TD align="center" >
										<input name="masp" autocomplete='off' type="text" value="" onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" style="text-align:left">
									</TD>
									<TD align="left" >
										<input name="tensp" type="text" readonly value="" style="width:100%"></TD>
										 <TD align = "center" ><input name="donvitinh" type="text" value="" readonly style="width:100%;text-align:center"></TD>
								    <TD align = "center" >
							        	<input name="soluong" type="text" value="" style="width:100%" onkeypress="return keypress(event);" style="text-align:right">
							        	<input name="soluongOld" type="hidden" value="0">
							        </TD>
								   
								    <TD align = "center" ><input name="dongia" type="text" value="" readonly style="width:100%;text-align:right"></TD>
								    <TD align = "center" ><input name="thanhtien" type="text" value="" readonly style="width:100%;text-align:right"></TD>
								</TR>
								<% soSp++;}} %>	
																																																																																																																						
								</tbody></TABLE>	
								 &nbsp;&nbsp;&nbsp;&nbsp;	<a class="button2" href="javascript:ThemSanPham()">
								<img style="top: -4px;" src="../images/add.png" alt="">Thêm sản phẩm</a>&nbsp;&nbsp;&nbsp;&nbsp;
								</TD>
							  </TR> 
						  </TABLE>
						  <TR><TD colspan="8" class="tbfooter">&nbsp;</TD></TR>	
						</TD>
					</TR>				
				</TBODY>
			</TABLE>
	</td>
  </tr>
<script type="text/javascript">
<!--
replaces();
//-->
</script>
</TABLE>
</form>
</BODY>
</HTML>
<% 	

try{
	if(dhBean != null){
		dhBean.DBclose();
		dhBean = null;
	}

	if(splist!=null){
		splist.clear();
	}
	if(spthieu!=null){
		spthieu.clear();
	}
	if(rskho!=null){
		rskho.close();
	}
	if(rskenh!=null){
		rskenh.close();
	}
	if(npp!=null){
		npp.close();
	}

	session.setAttribute("obj",null);
	}catch(Exception er){
		
	}

%>
<%}%>

