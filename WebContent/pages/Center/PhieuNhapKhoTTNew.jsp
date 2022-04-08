<%@page import="geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT_SanPham"%>
<%@page import="geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT"%>
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
<% PhieuNhapKhoTT dhBean = (PhieuNhapKhoTT)session.getAttribute("obj");
ResultSet rs_khott=(ResultSet)session.getAttribute("rs_khott");
%>
<% List<PhieuNhapKhoTT_SanPham> splist = dhBean.getListSanPham(); %>
<% String userId = (String) session.getAttribute("userId"); 
String userTen=(String)session.getAttribute("userTen");
String check1 =(String )session.getAttribute("check");
  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK  rel="stylesheet" type="text/css" href="../css/style.css" />
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

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_listsanpham_pnktt.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>

<script language="javascript" type="text/javascript">
function Kiemtratonkho(){
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
			alert("vui lòng chọn Nhân viên bán hàng...");
			return;
		}
	document.forms['dhForm'].action.value='kiemtrahangton';
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
function submitform(){
	congDonSPCungMa();
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
	
	
	var khott=document.forms['dhForm'].khott.value;
	if(khott=="0"){
		document.forms['dhForm'].dataerror.value="Vui lòng chọn kho để nhập";
		return;
	}
	var ngaynhap=document.forms['dhForm'].ngaynhapkho.value;
	if(ngaynhap==""){
		document.forms['dhForm'].dataerror.value="Bạn chưa nhập ngày nhập kho, vui lòng chọn ngày nhập kho bên dưới";
		return;
	}
	///Kiem tra ngay xem co hop le khong?
	 var today = new Date(ngaynhap);//đổi ra kiểu ngày tháng và bị lỗi, khi đó nó có giá trị Invalid Date
		if(today=="Invalid Date"){
			 document.forms["dhForm"].dataerror.value="Nhập ngày nhập kho không đúng định dạng ngày tháng,kiểu định dạng yyyy-MM-dd ";
	     	return; 
		}
	 congDonSPCungMa();
	 var masp = document.getElementsByName("masp");
	 var tensp = document.getElementsByName("tensp");
	 var soluong = document.getElementsByName("soluong");

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
		
	
	 document.forms['dhForm'].action.value='new';//de serverlet hieu dc day la su kien them moi
    document.forms['dhForm'].submit();
}
function replaces()
{
	var masp = document.getElementsByName("masp");
	var tensp = document.getElementsByName("tensp");
	
	var soluong = document.getElementsByName("soluong");

	
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
			}
			
			if(checkMasp(masp.item(i).value) == true)
			{
				masp.item(i).parentNode.parentNode.bgColor = "#9FC";
			}
		}
		else
		{
			tensp.item(i).value = "";
			soluong.item(i).value = "";
		}
	}	
	setTimeout(replaces, 20);//goi lai sau 20ms
	}
	
	replaces();
	
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
function addRow(name,count)
{
	tableName = document.getElementById(name);
	
	var tr = document.createElement("TR");
	var sottadd = document.createElement("TD");
	var maspAdd = document.createElement("TD");
	var tenspAdd = document.createElement("TD");
	var soluongAdd= document.createElement("TD");

	var sott = document.createElement("input");
	sott.setAttribute("type", "textbox");
	sott.setAttribute("readonly", "readonly");
	sott.setAttribute("value",count+1);
	sott.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
	sott.name = 'sott';
	sottadd.appendChild(sott);
	
	var masp = document.createElement("input");
	masp.setAttribute("type", "textbox");
	masp.setAttribute("onkeyup", "ajax_showOptions(this,'abc',event)");
	masp.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
	masp.setAttribute("autocomplete","off");
	masp.name = 'masp';
	maspAdd.appendChild(masp);
	
	var tensp = document.createElement("input");
	tensp.setAttribute("type", "text");
	tensp.setAttribute("readonly", "readonly");
	tensp.name = 'tensp';
	tensp.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
	tenspAdd.appendChild(tensp);
	

	var soluong = document.createElement("input");
	soluong.setAttribute("type", "text");
	soluong.setAttribute("onkeypress","return keypress(event)");
	soluong.value = "";
	soluong.name = "soluong";
	soluong.setAttribute("style","width:100%;border:1px;text-align:center;border-style:solid;border-color: #808080;");
	soluongAdd.appendChild(soluong);
	
	tr.appendChild(sottadd);
	tr.appendChild(maspAdd);
	tr.appendChild(tenspAdd);
	tr.appendChild(soluongAdd);
	
	tableName.appendChild(tr);
}


function ThemSanPham()
{
	var sottcount = document.getElementsByName("sott");
	 var sl = window.prompt("Nhấp số lượng sản phẩm muốn thêm", '');
	 if(isNaN(sl) == false && sl < 30)
	 {
		 for(var i=0; i < sl ; i++)
			 addRow("san_pham",sottcount.length);
	 }
	 else
	 {
		 alert('Số lượng bạn nhập không hợp lệ. Mọi lần bạn chỉ được thêm tối đa 30 sản phẩm');
		 return;
	 }
 }
function checkradio(value){
	document.forms["dhForm"].check.value=value;
	  var	maspnew=document.getElementsByName('masp');
		var soluongnew=document.getElementsByName('soluong');
	if(value=="1"){
		//thuc hien khoa cac o nhap lieu,dong thoi khoa luon nut them san pham
	  
		//for(var i=0;i<maspnew.length;i++){
		//	maspnew.item(i).setAttribute('readonly','readonly');
		//	soluongnew.item(i).setAttribute('readonly','readonly');
		//}
	}else{
		 
			//for(var i=0;i<maspnew.length;i++){
			//	maspnew.item(i).removeAttribute('readonly');
			//	soluongnew.item(i).removeAttribute('readonly');
			//}
	}
	
}
function upload(){
	var value=	document.forms["dhForm"].check.value;
	if(value==1){
		if(document.forms["dhForm"].filename.value==""){
			document.forms["dhForm"].dataerror.value="Chưa chọn file upload, vui lòng chọn file Excel theo đúng định dạng phiếu nhập kho để upload";
		}
		else{
			 document.forms['dhForm'].setAttribute('enctype', "multipart/form-data", 0);
			document.forms['dhForm'].submit();	
		}
	}else{
		document.forms["dhForm"].dataerror.value="Bạn đang chọn nhập dữ liệu, vui lòng chọn Import file để upload";
	}
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

<form name="dhForm" method="post" action="../../PhieuNhapKhoTTNewSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen%>'>
<input type="hidden" name="action" value='new'>
<input type="hidden" name="tenform" value="newform">
<INPUT type="hidden" name="id" value='<%= dhBean.getId() %>'>
<input type="hidden" name="check" value="<%=check1%>" > 
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
			 								   <TD align="left"  class="tbnavigation">&nbsp;Quản lý kho >Quản lý kho trung tâm>Nhập kho > Tạo mới </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp; </TD>
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
							<TABLE border="0" width="100%" cellpadding = "0" cellspacing = "0" >
								<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>			
				    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width:100%"  rows="2"  > <%=  dhBean.getMessage()%></textarea>
									</FIELDSET>
							   </TD>
								</tr>
								
								<TR>
									<TD  align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Phiếu nhập kho </LEGEND>
										<TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>																											
										<TR>
									  	<TD class="plainlabel" width="19%">Chọn kho trung tâm</TD>
									  	<TD class="plainlabel" >
									  	<select name="khott">
									  	<option value="0" > </option>
									  	<% if(rs_khott!=null){
									  		try{
									  			while(rs_khott.next()){
									  				if(rs_khott.getString("pk_seq").equals(dhBean.getKhoID())){
									  					%>
									  					<option value="<%= rs_khott.getString("pk_seq")%>" selected="selected"> <%= rs_khott.getString("ten") %> </option>
									  					<%
									  				}else{
									  					%>
									  					<option value="<%= rs_khott.getString("pk_seq")%>"> <%= rs_khott.getString("ten") %> </option>
									  					<%
									  				}
									  			
									  			}
									  		}catch(Exception er){
									  			
									  		}
									  	}
									  		%>
									  	</select>
									  	</TD>
							        	</TR>
												<TR >
											  <TD class="plainlabel">Ngày nhập kho </TD>
											  <TD colspan="3" class="plainlabel">
											  <TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
											    <input type="text" name="ngaynhapkho" size="20" value='<%=dhBean.getNgayNhap() %>'>
											</TD><TD>
												 <a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'dhForm.ngaynhapkho',false, 'yyyy-mm-dd'); return false;">
		  											&nbsp;<img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>
											</TD></TR>
						
							       </TABLE>
							  </TR>
							  	<tr class="plainlabel">
						  	<td >
						    <%
						    	if(check1.equals("0")){
						    %>
						  	<input type="radio" value="0"  checked="checked" name="chon" id="auto1" onclick="checkradio(this.value)"> <label for="auto1"> Nhập phiếu </label> <br>
						  	 <input type="radio" value="1" name="chon" id="noauto1" onclick="checkradio(this.value)"> <label for="noauto1" >Import từ file Excel  </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  	 <%
 						  	 	}else{
 						  	 %>
						     <input type="radio" value="0"   name="chon" id="auto1" onclick="checkradio(this.value)"> <label for="auto1"> Nhập phiếu</label> <br>
						  	 <input type="radio" value="1" checked="checked" name="chon" id="noauto1" onclick="checkradio(this.value)"> <label for="noauto1" >Import từ file Excel  </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  		 <%
  						  		 	}
  						  		 %>
						    
						  	  </td> 
						  	  <td>
						  	  <INPUT type="file" name="filename" size="40" value=''> 
						  	  </td>
						  	</tr>
						  	<tr class="plainlabel">
						  	<td colspan="2">
						  		&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:upload()">
								<img style="top: -4px;" src="../images/button.png" alt=""> Cập nhật</a>							
						  	</td>
						  	</tr>
							</TABLE>
							</FIELDSET>
							  </TD>
							   </TR>	
							   <TR>
							   		<TD>
							   		<fieldset>
										<TABLE width = "100%" border="0" cellpadding="0" cellspacing="1" >
										<tbody id="san_pham">
												<TR class="tbheader" >
													<TH width="10%">Số thứ tự </TH>
													<TH width="25">Mã sản phẩm </TH>
													<TH width="45%">Tên sản phẩm </TH>
													<TH width="10%">Số lượng </TH>
													
												</TR>
									<% 
									int size=0;
							if(splist != null){
							PhieuNhapKhoTT_SanPham sanpham =new PhieuNhapKhoTT_SanPham();
							 size = splist.size();
							int m = 0;
							while (m < size){
								sanpham = splist.get(m); 
								%>
									<TR class= 'tblightrow' >
								    	<TD>
								    	<input name="sott" type="text" readonly="readonly" value="<%=m+1%>"  style="width:100%;">
								    	 </TD>
										 <TD align="left" >  
										<input name="masp" type="text" autocomplete="off" value="<%=sanpham.getSanPhamId()%>" onkeyup="ajax_showOptions(this,'abc',event)"   style="width:100%;">
										</TD>
										<TD align="left" >
										<input name="tensp" type="text" readonly value="<%=sanpham.getTenSanPham()%>" style="width:100%">
										</TD>
										 <TD align = "center" >
										 <input name="soluong" type="text" value="<%= sanpham.getSoLuong() %>"   style="text-align:center ;width:100%">																				
										 </TD>
							    	</TR>
								<% m++; }}%>
								<% if(size==0){//neu truong hop khong co list san pham thi tu dong them vao 10 hang truoc
									int soSp=0;
									while(soSp < 10){ 
								%>
									<TR class= 'tblightrow' >
								    	<TD>
								    	<input name="sott" type="text"  readonly="readonly" value=<%=soSp+1 %>  style="width:100%;">
								    	 </TD>
										 <TD align="left" >
										
										<input name="masp" type="text" autocomplete="off"  value="" onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%;">
										
										</TD>
										<TD align="left" >
										<input name="tensp" type="text" readonly value=""  style="width:100%">
										</TD>
										 <TD align = "center" >
										 <input name="soluong" type="text" value=""   style="text-align:center ;width:100%">										
										 </TD>
							    	</TR>
								<% soSp++;}} %>	
																																																																																																																						
								</tbody>
								</TABLE>	
								 &nbsp;&nbsp;&nbsp;&nbsp;	<a class="button2" href="javascript:ThemSanPham()">
								<img style="top: -4px;" src="../images/add.png" alt="">Thêm sản phẩm</a>&nbsp;&nbsp;&nbsp;&nbsp;
								</fieldset>
								</TD>
							  </TR> 
						  </TABLE>
						  <TR><TD colspan="8" class="tbfooter">&nbsp;</TD></TR>	
										
				</TBODY>
			</TABLE>
	</td>
  </tr>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>


