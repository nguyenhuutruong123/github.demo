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
	//Lay doi tuong objbean
 	ChiTieuTTChoVung objbean=(ChiTieuTTChoVung)session.getAttribute("obj");
	//truyen qua doi tuong list vung
	List<ChiTieuTTKhuVuc> listkhuvuc= (List<ChiTieuTTKhuVuc> )objbean.getListKhuVuc();
	
	String check1=(String)session.getAttribute("check");
	
	String loaichitieu=(String)session.getAttribute("loaichitieu");
	
	ResultSet rs_dvkd=(ResultSet)session.getAttribute("rs_dvkd");
	
	ResultSet rs_kenh=objbean.getRsKenh();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["ChiTieuTTForm"].submit();
}

function moform(value){
	 document.forms["ChiTieuTTForm"].loaict.value=value;	
	 document.forms["ChiTieuTTForm"].action.vaue="thaydoict";
	 document.forms["ChiTieuTTForm"].submit();
}
function tinhphantram(){
	//Thuc hien tinh tong tien xuong ph??a cuoi	
	//thuc hien lay tong tien truoc 
	var chitieu = document.getElementsByName("chitieu");
	var tongtien = document.forms["ChiTieuTTForm"].tongchitieu.value;
	//tinh phan tram
	var phantram=document.getElementsByName("phantram");
	
	try{
		tongtien=parseFloat(tongtien);
	}catch(err){
		tongtien=0;
	}
	if(tongtien==0){
		 document.forms["ChiTieuTTForm"].dataerror.value="Vui l??ng nh???p t???ng s??? ch??? ti??u ";
	}
	//sau khi tinh thanh tien thi tinh lai % chi tieu
	for(var i=0; i <chitieu.length; i++)
	{
		if(chitieu.item(i).value != "")
		{
			var thanh_tien = chitieu.item(i).value;
			if(parseFloat(tongtien)>0){
				var phan_tram= parseFloat(thanh_tien)/parseFloat(tongtien)* 100;
	
				phantram.item(i).value=phan_tram.toFixed(2);
				if(phantram.item(i).value=="NaN"){
					phantram.item(i).value=0;
				}
			}else{
				phantram.item(i).value=0;
			}
		}
	}
}
function keypress(e) //H??m d??ng d? ngan ngu?i d??ng nh?p c??c k?? t? kh??c k?? t? s? v??o TextBox
{    
	var keypressed = null;
	if (window.event)
		keypressed = window.event.keyCode; //IE
	else
		keypressed = e.which; //NON-IE, Standard
	
	if (keypressed < 48 || keypressed > 57)
	{ 
		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
		{//Ph??m Delete v?? Ph??m Back
			return;
		}
		return false;
	}
}
function lockhuvuc(){
	document.forms["ChiTieuTTForm"].action.value="lockhuvuc";
	document.forms["ChiTieuTTForm"].submit();
}
function upload(){// nhan nut cap nhat
	 var thang=document.forms["ChiTieuTTForm"].thang.value;
	  var nam=document.forms["ChiTieuTTForm"].nam.value;
	  var kenhid=document.forms["ChiTieuTTForm"].kenhid.value;
	  if(thang==0){
		  document.forms["ChiTieuTTForm"].dataerror.value="Nh???p th??ng ?????t ch??? ti??u";
			return; 
	  }
	  if(nam==0){
		  document.forms["ChiTieuTTForm"].dataerror.value="Nh???p n??m ?????t ch??? ti??u";
			return;
	  }
	  if(kenhid==""){
		  document.forms["ChiTieuTTForm"].dataerror.value="Ch???n k??nh b??n h??ng";
			return;
	  }
	if(document.forms["ChiTieuTTForm"].luutam.value=="1"){//truong hop upload
	   if(document.forms["ChiTieuTTForm"].filename.value==""){
		   document.forms["ChiTieuTTForm"].dataerror.value="Ch??a t??m th???y file upload l??n, vui l??ng ch???n ???????ng d???n file upload b???ng nut BROWSE b??n d?????i";
	   }else{
		 document.forms["ChiTieuTTForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["ChiTieuTTForm"].submit();	
	   }
	}else{//truong hop cap nhat tu dong
		//phai kiem tra so tien da co chua
		var tongchitieu=document.forms["ChiTieuTTForm"].tongchitieu.value;
		
	   if(tongchitieu==0|| tongchitieu==""){
		document.forms["ChiTieuTTForm"].dataerror.value="Nh???p t???ng chi ti??u l??n h??n 0";
		return;
	   }
		
		document.forms["ChiTieuTTForm"].action.value="capnhat";
		 document.forms["ChiTieuTTForm"].submit();
	}


}
function save(){
	var loaict=document.forms['ChiTieuTTForm'].thang.value;
  var thang=document.forms["ChiTieuTTForm"].thang.value;
  var nam=document.forms["ChiTieuTTForm"].nam.value;
  var tongchitieu=document.forms["ChiTieuTTForm"].tongchitieu.value;
  var ngayketthuc;
   ngayketthuc =document.forms["ChiTieuTTForm"].ngayketthuc.value;
   if(thang=="0")
	  {
	  document.forms["ChiTieuTTForm"].dataerror.value="Ch???n th??ng c???n ?????t ch??? ti??u ";
	  return;
	  }
   
  if(nam=="0"){
	  document.forms["ChiTieuTTForm"].dataerror.value="Ch???n n??m c???n ?????t ch??? ti??u ";
	  return;
  }
  //kiem tra xem thang nam dat chi tieu co hop le hay khong
  	 var d=new Date();
	 var year_= d.getFullYear();
	 var month_=d.getMonth()+1;
	 if(parseInt(nam)<parseInt(year_)){
		  document.forms["ChiTieuTTForm"].dataerror.value="Th???i gian ?????t ch??? ti??u kh??ng h???p l???,ph???i ?????t th???i gian ch??? ti??u l???n h??n ho???c b???ng th???i gian hi???n t???i ";
			return; 
	 }else if(parseInt(nam)==parseInt(year_) && parseInt(thang)<parseInt(month_)){
		  document.forms["ChiTieuTTForm"].dataerror.value="Th???i gian ?????t ch??? ti??u kh??ng h???p l???,ph???i ?????t th???i gian ch??? ti??u l???n h??n ho???c b???ng th???i gian hi???n t???i ";
			return; 
	 }
  
  if(tongchitieu=="" || tongchitieu=="0" ){
	  document.forms["ChiTieuTTForm"].dataerror.value="Nh???p ch??? ti??u l???n h??n 0 ";
	  return;
  }
  
  if(ngayketthuc==""){
	  document.forms["ChiTieuTTForm"].dataerror.value="Ch???n ng??y k???t th??c  ";
	 return;
  }
  else{
		 //kiem tra ngay thang co hop le khong
		//Run some code here
		 var today = new Date(ngayketthuc);//?????i ra ki???u ng??y th??ng v?? b??? l???i, khi ???? n?? c?? gi?? tr??? Invalid Date
		if(today=="Invalid Date"){
			 document.forms["ChiTieuTTForm"].dataerror.value="Nh???p ng??y k???t th??c kh??ng ????ng ?????nh d???ng ng??y th??ng,ki???u ?????nh d???ng yyyy-MM-dd ";
	     	return; 
		}
  }
  
 	var thanhtien = document.getElementsByName("chitieu");
	var tongtien = 0;
	for(var i=0; i < thanhtien.length; i++)
	{
		if(thanhtien.item(i).value != "")
		{
			var thanh_tien = thanhtien.item(i).value;
			tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
		}
	}  
	var tongchitieu= document.forms["ChiTieuTTForm"].tongchitieu.value;
  if(parseFloat(tongchitieu)!= tongtien)
	{
	  document.forms["ChiTieuTTForm"].dataerror.value="Nh???p t???ng ch??? ti??u c???a khu v???c ph???i b???ng t???ng ch??? ti??u. ";
	  return;
	  }
  document.forms["ChiTieuTTForm"].action.value="new";
  document.forms["ChiTieuTTForm"].submit();
  
  
}
function checkradio(value){
	document.forms["ChiTieuTTForm"].luutam.value=value;
}
</SCRIPT>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>

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
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="userTen" value='<%=userTen%>'>
<input type="hidden" name="nkmId" value="">
<input type="hidden" name="action" value="0">
<input type="hidden" name="tenform" value="newform">
<input type="hidden" name="id" value="0">
<input type="hidden" name="loaict" value=<%=loaichitieu%>>
<input type=hidden name="luutam" value="<%=check1%>"><!--  de luu gia tri cua radio khi cho -->
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Qu???n l?? ch??? ti??u &gt; Ch??? ti??u cho khu v???c &gt; T???o m???i</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%=userTen%>&nbsp;  </TD></tr>
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
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">B??o l???i nh???p li???u </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=objbean.getMessage()%></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Th??ng tin ch??? ti??u </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<tr class="plainlabel">
							<td colspan="2">
							 <FIELDSET style=" width:99%">
									<LEGEND class="legendtitle" >Lo???i ch??? ti??u</LEGEND>
									<%
						    	if(loaichitieu.equals("0")){
						    %>
						  	<input type="radio" value="0"  checked="checked" name="loaichitieu" id="auto" onclick="moform(this.value)"> <label for="auto">Primary  </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  	<input type="radio" value="1" name="loaichitieu" id="noauto" onclick="moform(this.value)"> <label for="noauto" >Secondary </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  	 <%
 						  	 	}else{
 						  	 	%>
							<input type="radio" value="0"   name="loaichitieu" id="auto" onclick="moform(this.value)"> <label for="auto">  Primary</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  	<input type="radio" value="1" checked="checked" name="loaichitieu" id="noauto" onclick="moform(this.value)"> <label for="noauto" > Secondary</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  		 <%
  						  		   }
  						  		 %>
									</FIELDSET>
									</td>
							</tr>
							<TR>
								<TD width="20%" class="plainlabel" >Th??ng <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<select name="thang" style="width :50px" onchange="submitform();">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  									  if(k==objbean.getThang()){
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
							</TR>
							<TR>
							  	<TD class="plainlabel">N??m</TD>
						  	  	<TD class="plainlabel">
										<select name="nam"  style="width :50px"  onchange="submitform();">
									<option value=0> </option>  
									<%
									  Calendar c2 = Calendar.getInstance();
  										int t=c2.get(Calendar.YEAR) +6;
  										int n;
  										for(n=2008;n<t;n++){
  										if(n==objbean.getNam()){
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
                             <td>????n v??? kinh doanh </td>
                             <td>
                             <select name=selectdvkd >
                             <option value =""> </option>             
                             <%
                          
                             if (rs_dvkd!=null){
                            	 while (rs_dvkd.next()){
                       				%>
                       				<% if(rs_dvkd.getString("pk_seq").equals(objbean.getDVKDID())){ %>
                       				<option value ="<%=rs_dvkd.getString("pk_seq") %>" selected="selected"> <%=rs_dvkd.getString("donvikinhdoanh") %></option>
                       				<%}else{ %>
                       				<option value ="<%=rs_dvkd.getString("pk_seq") %>"> <%=rs_dvkd.getString("donvikinhdoanh") %></option>
                       				<%} %>
                       				
                       				<%     		
                            	 }
                             }
                             %>
                             </select>
                             </td>                           
                             </tr>
                             <tr class="plainlabel">
                             <td>K??nh b??n h??ng </td>
                             <td>
								 <select name=kenhid   onchange="submitform();">
                             <%
                          
                             if (rs_kenh!=null){
                            	 while (rs_kenh.next()){
                       				%>
                       				<% if(rs_kenh.getString("pk_seq").equals(objbean.getKenhID())){ %>
                       				<option value ="<%=rs_kenh.getString("pk_seq") %>" selected="selected"> <%=rs_kenh.getString("ten") %></option>
                       				<%}else{ %>
                       				<option value ="<%=rs_kenh.getString("pk_seq") %>"> <%=rs_kenh.getString("ten") %></option>
                       				<%} %>
                       				<%     		
                            	 }
                             }
                             %>
                             </select>                            
                             </td>
                             </tr>
				  		   <TR>
						  	  	<TD class="plainlabel">T???ng s??? ch??? ti??u</TD>
						  	  <TD class="plainlabel">
						  	  <input onkeypress="return keypress(event);" onchange="tinhphantram();" type="text" name="tongchitieu" value="<%=Math.round(objbean.getChitieu())%>" > 
						  	  	</TD>
						  	</TR>
						  	 
						  	 <TR>
						  	  	<TD class="plainlabel">S??? ng??y l??m vi???c</TD>
						  	  <TD class="plainlabel">
						  	  <input  type="text" name="songaylamviec" value="<%=objbean.getSoNgayLamViec()%>" > 
						  	  	</TD>
						  	</TR>
						  
						  	<TR>
						  	  	<TD class="plainlabel">Ng??y k???t th??c</TD>
						  	  	<TD class="plainlabel">
						  		<input type="text" name="ngayketthuc" value="<%=objbean.getNgayKetThuc()%>">  
						  		&nbsp;&nbsp;&nbsp;<a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'ChiTieuTTForm.ngayketthuc',false, 'yyyy-mm-dd'); return false;">
		                        &nbsp;<img src="../images/Calendar20.png" name="imgCalendar" border="1" alt=""></a>	
						  	  	</TD>
						  	</TR>	
						
						 
						      <TR>
						  	  	<TD class="plainlabel">Di???n gi???i</TD>
						  	  	<TD class="plainlabel">
						  	  	<textarea name="diengiai"  style="width: 50%"  rows="2"><%=objbean.getDienGiai()%></textarea>	
						  	  	</TD>
						  	</TR>
						  	<tr class="plainlabel">
						  	<td >
						    <%
						    	if(check1.equals("0")){
						    %>
						  	<input type="radio" value="0"  checked="checked" name="chon" id="auto1" onclick="checkradio(this.value)"> <label for="auto1"> T??? ?????ng ph??n b??? ch??? ti??u </label> <br>
						  	 <input type="radio" value="1" name="chon" id="noauto1" onclick="checkradio(this.value)"> <label for="noauto1" >Import t??? file Excel  </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
						  	 <%
 						  	 	}else{
 						  	 %>
						     <input type="radio" value="0"   name="chon" id="auto1" onclick="checkradio(this.value)"> <label for="auto1"> T??? ?????ng ph??n b??? ch??? ti??u </label> <br>
						  	 <input type="radio" value="1" checked="checked" name="chon" id="noauto1" onclick="checkradio(this.value)"> <label for="noauto1" >Import t??? file Excel  </label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
								<img style="top: -4px;" src="../images/button.png" alt=""> C???p nh???t</a>							
						  	</td>
						  	</tr>
						</TABLE>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >							
								<TR class="tbheader">
									<TH width="8%">S??? th??? t??? </TH>
									<TH width="10%">M?? khu v???c</TH>
									<TH >T??n khu v???c </TH>
									<TH width="10%">Doanh s??? trung b??nh th??ng </TH>
									<TH width="10%"> (%)ch??? ti??u </TH>
									<TH width="10%">Ch??? ti??u </TH>
									<%if(loaichitieu.equals("1")) {%>
									<TH width="10%">S??? ????n h??ng </TH>
									<TH width="10%">SKU </TH>
									<%} %>
								</TR>
								
							<%
																int m=0;
																	double conlai=objbean.getChitieu();
																				if(listkhuvuc != null)
																				{
																					
																					while (m<listkhuvuc.size()){
																						
																						ChiTieuTTKhuVuc khuvuc=listkhuvuc.get(m);
																						
															%>
									<tr>
									<td > <input type =text name="sott" readonly="readonly" style="width :100%" value=<%=m+1 %> >  </TD>
									<td>  <input type ="text" name="mavung" readonly="readonly" style="width :100%" value=<%=khuvuc.getKhuVucId() %> ></td>
									<TD ><input type =text name="tenvung" style="width :100%" readonly="readonly" value="<%=khuvuc.getTenKhuVucId() %>"> </TD>
									<TD ><input type =text name="trungbinhthang" style="width :100%" readonly="readonly" value="<%=Math.round(khuvuc.getTrungBinhThang()) %>"> </TD>
									<td><input type =text name="phantram" style="width :100%" readonly="readonly" value="" > </td>
									<TD > <input autocomplete="off" type ="text" name="chitieu" style="width :100%" value="<%=Math.round(khuvuc.getChiTieu())%>" onkeypress="return keypress(event);" onchange="tinhphantram();" > </TD>
								    <%if(loaichitieu.equals("1")) { %>
								    <TD > <input type =text name="sodonhang"  style="width :100%" value="<%=khuvuc.getSoDonHang()%>" > </TD>
								    <TD > <input type =text name="sosku"  style="width :100%" value="<%=khuvuc.getSoSKU()%>"> </TD>
									<%} %>								   
								    </tr>
									<%
									m++;
								}
							}
							%>
							<tr class="tbfooter" style="height: 20px">
							<td colspan="10" align="right">			
							</td>	
							
							 </tr>
						</TABLE>							
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
	<script type="text/javascript">
	tinhphantram();
	</script>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>