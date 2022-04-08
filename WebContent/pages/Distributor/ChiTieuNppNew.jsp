<%@page import="geso.dms.distributor.beans.chitieunpp.imp.ChiTieuDDKD"%>
<%@page import="geso.dms.distributor.beans.chitieunpp.imp.ChiTieuNhaPP"%>
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

<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%
	
	//Lay doi tuong objbean
 	ChiTieuNhaPP objbean=(ChiTieuNhaPP)session.getAttribute("obj");
	//truyen qua doi tuong list vung
	List<ChiTieuDDKD> listddkd= (List<ChiTieuDDKD>)objbean.getListChiTieuDDKD();
	String check1=(String)session.getAttribute("check");
	String tenhapp=(String)session.getAttribute("tennhapp");
	ResultSet rs_dvkd=objbean.getrsdvkd();
	
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
	document.forms["ChiTieuTTForm"].tongchitieu.value="0";
	document.forms["ChiTieuTTForm"].action.value="loadchitieu";
    document.forms["ChiTieuTTForm"].submit();
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
		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
		{//Phím Delete và Phím Back
			return;
		}
		return false;
	}
}
function save(){
	  document.forms["ChiTieuTTForm"].dataerror.value=" ";
  var thang=document.forms["ChiTieuTTForm"].thang.value;
  var nam=document.forms["ChiTieuTTForm"].nam.value;
  var tongchitieu=document.forms["ChiTieuTTForm"].tongchitieu.value;
  var ngayketthuc=document.forms["ChiTieuTTForm"].ngayketthuc.value;
  if(nam==0){
	  document.forms["ChiTieuTTForm"].dataerror.value="Chọn năm cần đặt chỉ tiêu ";
	  return;
  }
  if(thang==0){
	  document.forms["ChiTieuTTForm"].dataerror.value="Chọn tháng cần đặt chỉ tiêu ";
	  return;
	  }
  var dvkd_fk=document.forms["ChiTieuTTForm"].selectdvkd.value;	
	 if(dvkd_fk==""){
		document.forms["ChiTieuTTForm"].dataerror.value="Chọn đơn vị kinh doanh ";
		  return;
	 }
  //kiem tra xem thang nam dat chi tieu co hop le hay khong
  var d=new Date();
	 var year_= d.getFullYear();
	 var month_=d.getMonth()+1;

	 if(thang==6 && dvkd_fk=="100001"){
	 }else{
		 if(parseInt(nam)<parseInt(year_)){
			  document.forms["ChiTieuTTForm"].dataerror.value="Thời gian đặt chỉ tiêu không hợp lệ,phải đặt thời gian chỉ tiêu lớn hơn thời gian hiện tại ";
				return; 
		 }else if(parseInt(nam)==parseInt(year_) && parseInt(thang)<parseInt(month_)){
			  document.forms["ChiTieuTTForm"].dataerror.value="Thời gian đặt chỉ tiêu không hợp lệ,phải đặt thời gian chỉ tiêu lớn hơn thời gian hiện tại ";
				return; 
		 }
	 }
if(tongchitieu=="" || tongchitieu=="0" ){
	  document.forms["ChiTieuTTForm"].dataerror.value="Chọn tháng năm để lấy chỉ tiêu được phân bổ từ trung tâm";
	  return;
}
if(ngayketthuc==""){
	  document.forms["ChiTieuTTForm"].dataerror.value="Chọn ngày kết thúc  ";
	return;
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
	  document.forms["ChiTieuTTForm"].dataerror.value="Nhập tổng chỉ tiêu của khu vực phải bằng tổng chỉ tiêu. ";
	  return;
	  }
document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

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
<input type="hidden" name="nkmId" value="">
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="0">
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý chỉ tiêu > Chỉ tiêu Sells Out > Tạo mới</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%=objbean.GetTenNpp() %>&nbsp;  </TD></tr>
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
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=objbean.getMessage()%></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin chỉ tiêu </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="20%" class="plainlabel" >Tháng <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<select name="thang" style="width :50px" onchange="submitform()">
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
							  	<TD class="plainlabel">Năm</TD>
						  	  	<TD class="plainlabel">
										<select name="nam"  style="width :50px" onchange="submitform()">
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
                             <td> Đơn vị kinh doanh </td>
                             <td>
                             <select name=selectdvkd onchange="submitform()">
                                 <option value =""> </option>  
                             <%
                             if (rs_dvkd!=null){
                            	 while (rs_dvkd.next()){                      				                       				
                       				 if(rs_dvkd.getString("pk_seq").equals(objbean.getDVKDId())){ %>
                       				<option value ="<%=rs_dvkd.getString("pk_seq") %>" selected="selected"> <%=rs_dvkd.getString("donvikinhdoanh") %></option>
                       				<%}else{ %>
                       				<option value ="<%=rs_dvkd.getString("pk_seq") %>"> <%=rs_dvkd.getString("donvikinhdoanh") %></option>
                       				<%}     		
                            	 }
                             }
                             %>
                             </select>
                             </td>                           
                             </tr>
                              <tr class="plainlabel">
                             <td>Kênh bán hàng </td>
                             <td>
								 <select name=kenhid  onchange="submitform()">
                             <option value =""> </option>             
                             <%
                          
                             if (rs_kenh!=null){
                            	 while (rs_kenh.next()){
                       				%>
                       				<% if(rs_kenh.getString("pk_seq").equals(objbean.getKenhId())){ %>
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
						  	  	<TD class="plainlabel">Số chỉ tiêu</TD>
						  	  <TD class="plainlabel">
						  	  <input onkeypress="return keypress(event);" type="text" readonly="readonly" name="tongchitieu" value="<%=Math.round(objbean.getChitieu())%>" > 
						  	  	</TD>
						  	</TR>
						  	<TR>
						  	  	<TD class="plainlabel">Số ngày làm việc </TD>
						  	  	<TD class="plainlabel">
						  		<input type="text" name="songaylamviec" readonly="readonly" value="<%=objbean.getSoNgayLamViec()%>">  
						  							  	  	</TD>
						  	</TR>	
						  	<TR>
						  	  	<TD class="plainlabel">Ngày kết thúc</TD>
						  	  	<TD class="plainlabel">
						  		<input type="text" name="ngayketthuc" readonly="readonly" value="<%=objbean.getNgayKetThuc()%>">  
						  							  	  	</TD>
						  	</TR>		 
						      <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel">
						  	  	<textarea name="diengiai"  style="width: 50%"  rows="2"><%=objbean.getDienGiai()%></textarea>	
						  	  	</TD>
						  	</TR>
						</TABLE>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >							
								<TR class="tbheader">
									<TH width="15%">Số thứ tự </TH>
									<TH width="40%">Tên Nhân viên bán hàng</TH>
									<TH width="15%">Chỉ tiêu </TH>
									<TH width="15%">Số đơn hàng </TH>
									<TH width="15%">Số SKU</TH>
								</TR>
							<%
																int m=0;
																				if(listddkd != null)
																				{
																					while (m<listddkd.size()){
																						ChiTieuDDKD ddkd=listddkd.get(m);
																						
															%>
									<tr>
									<TH > <input type =text name="sott" readonly="readonly" style="width :100%" value="<%=m+1 %>" >  <input type ="hidden" name="maddkd" readonly="readonly" style="width :100%" value=<%=ddkd.getDDKDId() %>>  </TH>
									<TH ><input type =text name="tenddkd" style="width :100%" readonly="readonly" value="<%=ddkd.getDDKDTen() %>"> </TH>
									<TH > <input type =text name="chitieu" style="width :100%" value="<%= Math.round(ddkd.getChiTieu())%>" onkeypress="return keypress(event);"> </TH>
								    <TH ><input type =text name="sodonhang" style="width :100%" value="<%=ddkd.getSoDonHang() %>" onkeypress="return keypress(event);"> </TH>
								    <TH ><input type =text name="sosku" style="width :100%"  value="<%=ddkd.getSoSku() %>" onkeypress="return keypress(event);"> </TH> 
								    </tr>
									<%
									m++;
								}
							}
							%>
						</TABLE>							
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<% 	

	
	try{
		if(rs_dvkd != null)
			rs_dvkd.close();
		if(rs_kenh != null)
			rs_kenh.close();
		if(objbean != null){
			objbean.DBclose();
			objbean = null;
		}
	
	}
	catch (SQLException e) {}

%>
<%}%>