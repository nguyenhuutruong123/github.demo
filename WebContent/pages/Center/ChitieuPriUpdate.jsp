<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="geso.dms.center.beans.chitieu.imp.ChiTieuNPP"%>
<%@page import="java.util.Calendar"%>
<%@page import="geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTKhuVuc"%>
<%@page import="geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTChoVung"%>
<%@page import="java.util.Date"%>
<%@page import="javax.xml.crypto.Data"%>
<%@page import="geso.dms.center.beans.chitieu.imp.ChiTieu"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhomkhuyenmai.INhomkhuyenmai" %>
<%@ page  import = "geso.dms.center.beans.nhomkhuyenmai.imp.Nhomkhuyenmai" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	//Lay doi tuong obj
 	ChiTieu obj=(ChiTieu)session.getAttribute("obj");
	//truyen qua doi tuong list vung
	List<ChiTieuNPP> listnhapp= (List<ChiTieuNPP> )obj.getListChiTieuNPP();
	String check1=(String)session.getAttribute("check");
	//lay resultset vung de do vao combobox
	
	ResultSet rschitieunpp=obj.rs_chitieuprinpp();
	ResultSet rschitieuss=obj.rs_chitieupriss();
	ResultSet rschitieuasm=obj.rs_chitieupriasm();
	ResultSet rschitieursm=obj.rs_chitieuprirsm();
	
	ResultSet rs_dvkd=obj.getRsDvdk();
	ResultSet	rs_kenh= obj.getRsKenh();

	String []nhomsp=obj.getNhomSp();
	String []nhomspid=obj.getNhomSpId();
	
	ResultSet	kyRs= obj.getKyRs();
	ResultSet	quyRs= obj.getQuyRs();
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
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

<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>

<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script>
$(function() {
 
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<SCRIPT language="JavaScript" type="text/javascript">

function submitform()
{
    document.forms["ChiTieuTTForm"].submit();
}

function lamtrontien_phandu(){
	
	var tongtien = document.forms["ChiTieuTTForm"].tongchitieu.value;

	var chitieu = document.getElementsByName("chitieu");
	
	var index=-1;
	
	for(var i=chitieu.length-1;i >=0; i--)
	{
		if(chitieu.item(i).value >0)
		{
			index=i;
			break;
		}
	}
	
	var tongtientruoc=0;
	
	for(var i=0; i < index; i++)
	{
		
		if(chitieu.item(i).value != "")
		{
			var thanh_tien=0;
			try{
			 thanh_tien=parseFloat(chitieu.item(i).value);
			}catch(err){}
			 tongtientruoc=parseFloat(tongtientruoc) +parseFloat(thanh_tien)
		}
	}
	
	if(index!=-1 && tongtien!=0){
		chitieu.item(index).value=parseFloat(tongtien)-parseFloat(tongtientruoc);
	}
}
 function moform(value){
	 try{
	 document.forms["ChiTieuTTForm"].ngayketthuc.value="0";
	 }catch(err){
		 
	 }
	 document.forms["ChiTieuTTForm"].songaylamviec.value="";
	 document.forms["ChiTieuTTForm"].tongchitieu.value="0";
	document.forms['ChiTieuTTForm'].loaichitieu.value=value;	
	document.forms['ChiTieuTTForm'].submit();
 } 
function checkall(value){
	var checkone=document.getElementsByName("checkkhuvuc1");
	var giatricheck=document.getElementsByName("checkkhuvuc");
	var chuoi;
	if(value==true){
		chuoi="1";
	}else{
		chuoi="0";
	}
	for(var i=0;i<checkone.length;i++ ){
		checkone.item(i).checked=value;
		giatricheck.item(i).value=chuoi;
	}
}
function recheck(){
	var checkone=document.getElementsByName("checkkhuvuc1");
	var giatricheck=document.getElementsByName("checkkhuvuc");
	for(var i=0;i<checkone.length;i++ ){
		if(checkone.item(i).checked==true){
			giatricheck.item(i).value="1";
		}else {
			giatricheck.item(i).value="0";
		}
		
			
	}
}
function keypress(e) 
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
function loctien(){
	var checkimport=document.forms["ChiTieuTTForm"].luutam.value;
	if(checkimport=="0"){
		//ch? th?c hi?n submit khi trong tinh trang la tu dong load chi tieu
	document.forms["ChiTieuTTForm"].tongchitieu.value="0";
	document.forms["ChiTieuTTForm"].action.value="loctien";
	document.forms["ChiTieuTTForm"].submit();
	}else{
		document.forms["ChiTieuTTForm"].submit();
	}
	
}
function lockhuvuc(){
	var checkimport=document.forms["ChiTieuTTForm"].luutam.value;
	if(checkimport=="0"){
	document.forms["ChiTieuTTForm"].tongchitieu.value="0";
	document.forms["ChiTieuTTForm"].action.value="lockhuvuc";
	document.forms["ChiTieuTTForm"].submit();
	}
}
function upload(){// nhan nut cap nhat

	   if(document.forms["ChiTieuTTForm"].filename.value==""){
		   
		   document.forms["ChiTieuTTForm"].dataerror.value="Chưa tìm file upload lên. Vui lòng chọn file cần upload.";
	   }else{
		 document.forms["ChiTieuTTForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["ChiTieuTTForm"].submit();	
	   }

}
function save(){
	  document.forms["ChiTieuTTForm"].dataerror.value=" ";
 	 var thang=document.forms["ChiTieuTTForm"].thang.value;
  	var nam=document.forms["ChiTieuTTForm"].nam.value;
  if(nam==0){
	  document.forms["ChiTieuTTForm"].dataerror.value="Chọn năm cần đạt chỉ tiêu ";
	  return;
  }
  if(thang==0){
	  document.forms["ChiTieuTTForm"].dataerror.value="Chọn tháng cần đạt chỉ tiêu ";
	  return;
	  }
 
	  
  //kiem tra xem thang nam dat chi tieu co hop le hay khong
  var d=new Date();
	 var year_= d.getFullYear();
	 var month_=d.getMonth()+1;
	
		 if(parseInt(nam)<parseInt(year_)){
			 
			  document.forms["ChiTieuTTForm"].dataerror.value="Thời gian đặt chỉ tiêu không hợp lý. Phải đặt thời gian chỉ tiêu lớn hơn thời gian hiện thời ";
				return; 
		 }else if(parseInt(nam)==parseInt(year_) && parseInt(thang)<parseInt(month_)){
			  document.forms["ChiTieuTTForm"].dataerror.value="Thời gian đặt chỉ tiêu không hợp lý. Phải đặt thời gian chỉ tiêu lớn hơn thời gian hiện thời";
				return; 
		 }
	 
	
	 var ngayketthuc=document.forms["ChiTieuTTForm"].ngayketthuc.value;
		if(ngayketthuc==""){
	  document.forms["ChiTieuTTForm"].dataerror.value="Chọn ngày kết thúc ";
		return;
		}else{
		 //kiem tra ngay thang co hop le khong
		//Run some code here
		 var today = new Date(ngayketthuc);//d?i ra ki?u ngày tháng và b? l?i, khi dó nó có giá tr? Invalid Date
		if(today=="Invalid Date"){
			 document.forms["ChiTieuTTForm"].dataerror.value="Nhập thời gian không đúng. Định dạng ngày tháng là: yyyy-MM-dd ";
	     	return; 
		}
		
	}

	document.forms["ChiTieuTTForm"].action.value="save";
	document.forms["ChiTieuTTForm"].submit();


}
function kiemtra(){
	if(document.forms['ChiTieuTTForm'].luutam.value=='0')
	{return false;}
	else{
		return true;
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
		element.value=DinhDangTien(element.value);
		if(element.value== '' ||element.value=='0' )
		{
			element.value= '';
		}
	}

</SCRIPT>

<script type="text/javascript">
$( function() {
	//Created By: Brij Mohan
	//Website: http://techbrij.com
	function groupTable($rows, startIndex, total)
	{
		if (total === 0)
		{
			return;
		}
		var i , currentIndex = startIndex, count=1, lst=[];
		var tds = $rows.find('td:eq('+ currentIndex +')');
		var ctrl = $(tds[0]);
		lst.push($rows[0]);
		for (i=1;i<=tds.length;i++){
		if (ctrl.text() ==  $(tds[i]).text()){
		count++;
		$(tds[i]).addClass('deleted');
		lst.push($rows[i]);
		}
		else{
			if (count>1){
			ctrl.attr('rowspan',count);
			groupTable($(lst),startIndex+1,total-1)
			}
			count=1;
			lst = [];
			ctrl=$(tds[i]);
			lst.push($rows[i]);
		}
		}
	}
	var rowCount = $('#sku tr').length;

	groupTable($('#sku tr:has(td)'),0,rowCount);
	$('#sku .deleted').remove();
	});
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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden"  name="userId" value='<%=userId%>'>
<input type="hidden" name="userTen" value='<%=userTen%>'>
<input type="hidden" name="nkmId" value="">
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%=obj.getID()%>">
<input type="hidden" name="tenform" value="0">
<input type="hidden" name="luutam" value="<%=check1%>"><!--  de luu gia tri cua radio khi cho -->
<input type="hidden" name="loaichitieu" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý chỉ tiêu &gt; Chỉ tiêu NPP &gt; Chỉ tiêu cho Mua Vào &gt; Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<TD >	<A href = "../../ChitieuPriNewSvl?userId=<%=userId%>&excel=<%=obj.getID()%>"><img src="../images/excel.gif" alt="Xuất File Excel" width="20" height="20" title="Xuất File Excel" border = 0></A> </TD>				
							
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1"><%=obj.getMessage()%></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông
										tin chỉ tiêu</LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

											<TR>
								<TD width="130px" class="plainlabel" valign="top">Tháng <FONT class="erroralert"> </FONT></TD>
								<TD width="300px" class="plainlabel" valign="top">
									<select name="thang" style="width :50px">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  									  if(obj.getThang().equals(k+"")){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=k%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									
								</TD>
								<TD width="120px" class="plainlabel" valign="top"> Năm </TD>
								<TD class="plainlabel" valign="top">
									<select name="nam"  style="width :50px">
									<option value=0> </option>  
									<%
									  Calendar c2 = Calendar.getInstance();
  										int t=c2.get(Calendar.YEAR) +6;
  										int n;
  										for(n=2015;n<t;n++){
  										if(obj.getNam().equals(n+"")){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
									</select>
								</TD>
							</TR>
						

										<tr class="plainlabel">
											<td>Đơn vị kinh doanh</td>
											<td><select name=dvkdid>
													<%
                             if (rs_dvkd!=null){
                            	 while (rs_dvkd.next()){                      				                       				
                       				 if(rs_dvkd.getString("pk_seq").equals(obj.getDVKDId())){ %>
													<option value="<%=rs_dvkd.getString("pk_seq") %>"
														selected="selected">
														<%=rs_dvkd.getString("donvikinhdoanh") %></option>
													<%}else{ %>
													<option value="<%=rs_dvkd.getString("pk_seq") %>">
														<%=rs_dvkd.getString("donvikinhdoanh") %></option>
													<%}     		
                            	 }
                             }
                             %>
											</select></td>

											<td>Kênh bán hàng</td>
											<td><select name=kbhid>
													<%
                             if (rs_kenh!=null){
                            	 while (rs_kenh.next()){
                       				%>
													<% if(rs_kenh.getString("pk_seq").equals(obj.getKenhId())){ %>
													<option value="<%=rs_kenh.getString("pk_seq") %>"
														selected="selected">
														<%=rs_kenh.getString("ten") %></option>
													<%}else{ %>
													<option value="<%=rs_kenh.getString("pk_seq") %>">
														<%=rs_kenh.getString("ten") %></option>
													<%} %>
													<%     		
                            	 }
                             }
                             %>
											</select></td>

										</tr>
										<TR>
											<TD class="plainlabel">Số ngày làm việc</TD>
											<TD class="plainlabel"><input
												onkeypress="return keypress(event);" type="text"
												name="songaylamviec" value="<%=obj.getSoNgayLamViec()%>">
											</TD>
											<TD class="plainlabel"></TD>
											<TD class="plainlabel"></TD>
										</TR>
										<TR>
											<TD class="plainlabel">Diễn giải</TD>
											<TD class="plainlabel">
													<textarea rows="" cols="" name="diengiai" > <%=obj.getDienGiai()%></textarea>
											</TD>
											<TD class="plainlabel">Ngày kết thúc</TD>
											<TD class="plainlabel"><input type="text" class="days"
												name="ngayketthuc" value="<%=obj.getNgayKetThuc()%>">
											</TD>
										</TR>
										<tr class="plainlabel">
											<TD class="plainlabel"> File Upload   </td>
											<TD class="plainlabel">		<INPUT type="file" name="filename" size="40" value=''></TD>
											<TD class="plainlabel" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
											<a class="button2" href="javascript:upload()"> <img style="top: -4px;" src="../images/button.png" alt=""> Cập nhật</a>
											</td>
										</tr>
									</TABLE>

								</FIELDSET>
							</TD>
						</TR>
						  	
						  	
						</TABLE>
						<ul class="tabs">
							<li><a href="#tabnpp">Nhà phân phối</a></li>
							<li><a href="#tabss">SS</a></li>
							<li><a href="#tabasm">ASM</a></li>
							<li><a href="#tabrsm">RSM</a></li> 
						</ul>
						<div class="panes">
							<div id="tabnpp" class="tab_content">
								<TABLE class="tabledetail" width="100%" cellspacing="1px" cellpadding="3px" border="1">							
									<TR class="tbheader">
										<%
										int col=0;
										if(nhomsp!=null)
										{
											for(int i=0;i<nhomsp.length;i++)
											{
												col++;
												if(nhomsp[i]!=null){
												%>
												<TH ><%=nhomsp[i] %></TH>
												<%
												}
											}
										}
										%>
									</TR>
										
										<%
										NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
										int m=0;
										if(rschitieunpp != null)
										{
											String nppIdPrev="";
											String kvIdPrev ="";
											String vungIdPrev="";
											while (rschitieunpp.next()){
												
												if(!vungIdPrev.trim().equals(rschitieunpp.getString("vungTen").trim())){ 
													vungIdPrev= rschitieunpp.getString("vungTen");
												%>
											 	<tr style="color:red ;font-weight: bold;font-size:12" >
												
												 	<TD colspan="<%=col%>"  >
													 	<%="Vùng : "+rschitieunpp.getString("vungTen")%>   
												 	</TD>  
												</tr>
												<%} 
												if(!kvIdPrev.trim().equals(rschitieunpp.getString("kvTen").trim())) {
													
													kvIdPrev=rschitieunpp.getString("kvTen").trim();
												%>
												<tr style="color:blue ;font-weight: bold;font-size:14" >
												 	<TD colspan="<%=col%>" style="text-align:center;"   >
													 	<%="Khu vực  :  "+rschitieunpp.getString("kvTen")%>   
												 	</TD>  
												</tr>
												<%} %>
												<%if(!nppIdPrev.trim().equals(rschitieunpp.getString("nppId").trim())){ 
													nppIdPrev= rschitieunpp.getString("nppId");	
												%>
												<tr style="color:black ;font-weight: bold;font-size:12" >
												 	<TD colspan="<%=col+2%>"  >
													 	<%=""+rschitieunpp.getString("nppId") +"-"+rschitieunpp.getString("nppTen")%>   
												 	</TD>  
											 	</tr>
												<%} %>
												<tr>
													<TD style="display:none">
														<input  type="hidden"  name="nppId"  value="<%=rschitieunpp.getString("nppId") %>"   >
													</TD>   
												<%
												if(nhomspid!=null){
												for(int i=0;i<nhomspid.length;i++)
												{
													if(nhomspid[i]!=null)
													{
													%>
													<TD width="10%">   
														<input onkeyup="Dinhdang(this)" style="text-align: right;width: 100%;border:0" name="ctNhom<%=nhomspid[i] %>"  value=" <%=formatter.format(rschitieunpp.getDouble("CT"+nhomspid[i]))%>"  onchange=" Dinhdang(this)"    >
													</TD>
													<%
													}
											 	}	
											}
											%>
										    	</tr>
											<%
											m++;
										}
									}
									%>
								</TABLE>
							</div>
							<div id="tabss" class="tab_content">
								<TABLE class="tabledetail" width="100%" cellspacing="1px" cellpadding="3px" border="1">		
									<TR class="tbheader">
										<%
										col=0;
										if(nhomsp!=null)
										{
											for(int i=0;i<nhomsp.length;i++)
											{
												col++;
												if(nhomsp[i]!=null){
												if(i==0){
												%>
													<TH colspan = 2><%=nhomsp[i] %></TH>
												<%
													} else { %>
													<TH ><%=nhomsp[i] %></TH>
												<% }
												}
											}
										}
										%>
									</TR>
										
										<%
										formatter = new DecimalFormat("#,###,###.##"); 
										m=0;
										if(rschitieuss != null)
										{
											String ssIdPrev="";
											while (rschitieuss.next()){ %>
												<%if(!ssIdPrev.trim().equals(rschitieuss.getString("ssId").trim())){ 
													ssIdPrev= rschitieuss.getString("ssId");	
												%>
												<tr style="color:black ;font-weight: bold;font-size:12" >
												 	<TD >
													 	<%=""+rschitieuss.getString("ssId") +"-"+rschitieuss.getString("ssTen")%>
													 	<input  type="hidden"  name="ssId"  value="<%=rschitieuss.getString("ssId") %>"   >   
												 	</TD>  
												<%} %>
														   
												<%
												if(nhomspid!=null){
												for(int i=0;i<nhomspid.length;i++)
												{
													if(nhomspid[i]!=null)
													{
													%>
													<TD width="10%">   
														<input onkeyup="Dinhdang(this)" style="text-align: right;width: 100%;border:0" name="ctNhom<%=nhomspid[i] %>"  
														value=" <%=formatter.format(rschitieuss.getDouble("CT"+nhomspid[i]))%>"  onchange=" Dinhdang(this)"    >
													</TD>
													<%
													}
											 	}	
											}
											%>
										    	</tr>
											<%
											m++;
										}
									}
									%>
								</TABLE>
							</div>
							<div id="tabasm" class="tab_content">
								<TABLE class="tabledetail" width="100%" cellspacing="1px" cellpadding="3px" border="1">
									<TR class="tbheader">
										<%
										col=0;
										if(nhomsp!=null)
										{
											for(int i=0;i<nhomsp.length;i++)
											{
												col++;
												if(nhomsp[i]!=null){
												if(i==0){
												%>
													<TH colspan = 2><%=nhomsp[i] %></TH>
												<%
													} else { %>
													<TH ><%=nhomsp[i] %></TH>
												<% }
												}
											}
										}
										%>
									</TR>
										
										<%
										formatter = new DecimalFormat("#,###,###.##"); 
										m=0;
										if(rschitieuasm != null)
										{
											String asmIdPrev="";
											while (rschitieuasm.next()){ %>
												<%if(!asmIdPrev.trim().equals(rschitieuasm.getString("asmId").trim())){ 
													asmIdPrev= rschitieuasm.getString("asmId");	
												%>
												<tr style="color:black ;font-weight: bold;font-size:12" >
												 	<TD >
													 	<%=""+rschitieuasm.getString("asmId") +"-"+rschitieuasm.getString("asmTen")%>
													 	<input  type="hidden"  name="asmId"  value="<%=rschitieuasm.getString("asmId") %>"   >   
												 	</TD>  
												<%} %>
														   
												<%
												if(nhomspid!=null){
												for(int i=0;i<nhomspid.length;i++)
												{
													if(nhomspid[i]!=null)
													{
													%>
													<TD width="10%">   
														<input onkeyup="Dinhdang(this)" style="text-align: right;width: 100%;border:0" name="ctNhom<%=nhomspid[i] %>"  
														value=" <%=formatter.format(rschitieuasm.getDouble("CT"+nhomspid[i]))%>"  onchange=" Dinhdang(this)"    >
													</TD>
													<%
													}
											 	}	
											}
											%>
										    	</tr>
											<%
											m++;
										}
									}
									%>		
								</TABLE>
							</div>
							<div id="tabrsm" class="tab_content">
								<TABLE class="tabledetail" width="100%" cellspacing="1px" cellpadding="3px" border="1">
									<TR class="tbheader">
										<%
										col=0;
										if(nhomsp!=null)
										{
											for(int i=0;i<nhomsp.length;i++)
											{
												col++;
												if(nhomsp[i]!=null){
												if(i==0){
												%>
													<TH colspan = 2><%=nhomsp[i] %></TH>
												<%
													} else { %>
													<TH ><%=nhomsp[i] %></TH>
												<% }
												}
											}
										}
										%>
									</TR>
										
										<%
										formatter = new DecimalFormat("#,###,###.##"); 
										m=0;
										if(rschitieursm != null)
										{
											String rsmIdPrev="";
											while (rschitieursm.next()){ %>
												<%if(!rsmIdPrev.trim().equals(rschitieursm.getString("rsmId").trim())){ 
													rsmIdPrev= rschitieursm.getString("rsmId");	
												%>
												<tr style="color:black ;font-weight: bold;font-size:12" >
												 	<TD >
													 	<%=""+rschitieursm.getString("rsmId") +"-"+rschitieursm.getString("rsmTen")%>
													 	<input  type="hidden"  name="rsmId"  value="<%=rschitieursm.getString("rsmId") %>"   >   
												 	</TD>  
												<%} %>
														   
												<%
												if(nhomspid!=null){
												for(int i=0;i<nhomspid.length;i++)
												{
													if(nhomspid[i]!=null)
													{
													%>
													<TD width="10%">   
														<input onkeyup="Dinhdang(this)" style="text-align: right;width: 100%;border:0" name="ctNhom<%=nhomspid[i] %>"  
														value=" <%=formatter.format(rschitieursm.getDouble("CT"+nhomspid[i]))%>"  onchange=" Dinhdang(this)"    >
													</TD>
													<%
													}
											 	}	
											}
											%>
										    	</tr>
											<%
											m++;
										}
									}
									%>		
								</TABLE>
							</div>	
						</div>
					</TD>
				</TR>
			</TABLE>
		</form>
	</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
					
					