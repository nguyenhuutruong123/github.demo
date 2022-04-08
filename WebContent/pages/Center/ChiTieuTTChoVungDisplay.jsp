<%@page import="geso.dms.center.beans.chitieu.imp.ChiTieuNPP"%>
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
	ChiTieu chitieu=(ChiTieu)session.getAttribute("chitieunpp");
	//truyen qua doi tuong list vung
	List<ChiTieuTTKhuVuc> listkhuvuc= (List<ChiTieuTTKhuVuc> )objbean.getListKhuVuc();
	List<ChiTieuNPP> listnpp= (List<ChiTieuNPP>)chitieu.getListChiTieuNPP();
	System.out.println("KICH CO CUA LIST :"+listnpp.size());
	String check1=(String)session.getAttribute("check");
	//lay resultset vung de do vao combobox
	ResultSet rsvung= (ResultSet)session.getAttribute("rsvung");
	String loaict= (String)session.getAttribute("loaichitieu");
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
function tinhphantram(){
	//Thuc hien tinh tong tien xuong phía cuoi	
	//thuc hien lay tong tien truoc 
	var chitieu = document.getElementsByName("chitieu");
	var tongtien = document.forms["ChiTieuTTForm"].tongchitieu.value;
	//tinh phan tram
	var phantram=document.getElementsByName("phantram");
	
	try{
		tongtien=parseFloat(tongtien);
	}catch(err){
		
	}
	//sau khi tinh thanh tien thi tinh lai % chi tieu
	for(var i=0; i < chitieu.length; i++)
	{
		if(chitieu.item(i).value != "")
		{
			var thanh_tien = chitieu.item(i).value;
			
			var phan_tram=parseFloat(thanh_tien)/parseFloat(tongtien)* 100;
	
			phantram.item(i).value=phan_tram.toFixed(2);
			if(phantram.item(i).value=="NaN"){
				phantram.item(i).value=0;
			}
		}
	}
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
<input type="hidden" name="id" value="0">
<input type= hidden name="loaict" value="<%=loaict%>">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý chỉ tiêu &gt; Chỉ tiêu cho khu vực &gt;Xem</TD> 
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
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin chỉ tiêu </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<tr class="plainlabel" >
							<td colspan="2">
							Loại chỉ tiêu  : <%if(loaict.equals("0")){ %>
							   PRIMARY
								<%}else{ %>
								 SECONDARY
								<%} %>
							</td>
							</tr>
							<TR>
								<TD width="20%" class="plainlabel" >Tháng <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
								<input type="text" readonly="readonly" size=20px value="<%=objbean.getThang() %>">
								</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Năm</TD>
						  	  	<TD class="plainlabel">
									<input type="text" readonly="readonly" size=20px value="<%=objbean.getNam() %>">
						  	  	</TD>
						  </TR>
						   <TR>
						  	  	<TD class="plainlabel">Đơn vị kinh doanh</TD>
						  	  <TD class="plainlabel">
						  	  <input  type="text" readonly="readonly" name="dvkd" value="<%=objbean.getTenDVKD()%>" > 
						  	  	</TD>
						  	</TR>
						  	<TR>
						  	  	<TD class="plainlabel">Kênh bán hàng:</TD>
						  	  <TD class="plainlabel">
						  	  <input  type="text" readonly="readonly" name="dvkd" value="<%=objbean.getTenKenh()%>" > 
						  	  	</TD>
						  	</TR>
				  		   <TR>
						  	  	<TD class="plainlabel">Số chỉ tiêu</TD>
						  	  <TD class="plainlabel">
						  	  <input readonly="readonly" onkeypress="return keypress(event);" type="text" name="tongchitieu" value="<%=Math.round(objbean.getChitieu())%>" > 
						  	  	</TD>
						  	</TR>
						  	 <TR>
						  	  	<TD class="plainlabel">Số ngày làm việc</TD>
						  	  <TD class="plainlabel">
						  	  <input  type="text" readonly="readonly" name="songaylamviec" value="<%=objbean.getSoNgayLamViec()%>" > 
						  	  	</TD>
						  	</TR>
						  	
						  	<TR>
						  	  	<TD class="plainlabel">Ngày kết thúc</TD>
						  	  	<TD class="plainlabel">
						  		<input readonly="readonly" type="text" name="ngayketthuc" value="<%=objbean.getNgayKetThuc()%>">  
						  	  	</TD>
						  	</TR>		 
				
						      <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel">
						  	  	<textarea readonly="readonly" name="diengiai"  style="width: 50%"  rows="2"><%=objbean.getDienGiai()%></textarea>	
						  	  	</TD>
						  	</TR>
						  	<tr class="plainlabel">
						  	<td colspan="2">
						  	  </td> 
						  	</tr>
						  	<tr class="plainlabel">
						  	<td colspan="2">
							  	</td>
						  	</tr>
						</TABLE>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >							
								<TR class="tbheader">
									<TH width="8%">Số thứ tự </TH>
									<TH width="10%">Mã khu vực</TH>
									<TH >Tên khu vực </TH>
									<TH width="10%"> (%)chỉ tiêu </TH>
									<TH width="10%">Chỉ tiêu </TH>
									<%if(loaict.equals("1")) {%>
									<TH width="10%">Số đơn hàng </TH>
									<TH width="10%">SKU </TH>
									<%} %>
								</TR >
							<%
																int m=0;
																				if(listkhuvuc != null)
																				{
																					while (m<listkhuvuc.size()){
																						ChiTieuTTKhuVuc khuvuc=listkhuvuc.get(m);
															%>
									<tr class="tbdarkrow">
									<td > <input type =text name="sott" readonly="readonly" style="width :100%;background-color:#E6E6E6" value=<%=m+1 %> >  </TD>
									<td>  <input   type ="text" name="mavung" readonly="readonly" style="width :100%;background-color:#E6E6E6" value=<%=khuvuc.getKhuVucId() %> ></td>
									<TD ><input type =text name="tenvung" style="width :100%;background-color:#E6E6E6" readonly="readonly" value="<%=khuvuc.getTenKhuVucId() %>"> </TD>
									<td><input type =text name="phantram" style="width :100%;background-color:#E6E6E6" readonly="readonly" value="" > </td>
									<TD > <input autocomplete="off" type ="text" readonly="readonly" name="chitieu" style="width :100%;background-color:#E6E6E6" value="<%= Math.round(khuvuc.getChiTieu())%>" onkeypress="return keypress(event);" onchange="tinhphantram();" > </TD>
								    <%if(loaict.equals("1")) { %>
								    <TD > <input type =text name="sodonhang" readonly="readonly" style="width :100%;background-color:#E6E6E6" value="<%=khuvuc.getSoDonHang()%>" > </TD>
								    <TD > <input type =text name="sosku"  readonly="readonly" style="width :100%;background-color:#E6E6E6" value="<%=khuvuc.getSoSKU()%>"> </TD>
									<%} %>								   
								    </tr>
								    <%
								   	int k=0;
								   	if(listnpp!=null){
								   
								   		while(k<listnpp.size()){
								   			ChiTieuNPP npp=listnpp.get(k);
								   			if(npp.getid()==Double.parseDouble(khuvuc.getKhuVucId())){//da truyen khuvuc_fk vao file id cua nhpp ben servlet
								   				
								   				double phantram =0;
								   				
								   				phantram=npp.getSoTien()/objbean.getChitieu() *100;
								   				String chuoiphantram="0.00";
								   	
								   				if(phantram>0){
								   					chuoiphantram=String.format("%.4g%n",phantram );
								   				}
								   				%>
								   				<tr>
								   					<td   align="right" > <input type =text  readonly="readonly" style="width :100%; background-color: white;" value=<%=npp.getNhaPPId() %> >   </td>   				
								   					<td  align="right" > <input type =text  readonly="readonly" style="width :100%; background-color: white;" value=<%=npp.getNhaPPId() %> >   </td>
													<td  align="right"><input type =text  style="width :100%; background-color: white;" readonly="readonly" value="<%=npp.getTenNhaPP() %> "> </td>
													
														<td  align="right"><input type =text  style="width :100%; background-color: white;" readonly="readonly" value=" <%=chuoiphantram%>"> </td>
													
													<td  align="right"> <input type =text readonly="readonly" style="width :100%; background-color: white;" value="<%= Math.round(npp.getSoTien())%>"> </td>
													<%if(loaict.equals("1")) { %>
								  				  	<td  > <input type =text  readonly="readonly" style="width :100%; background-color: white" value="<%=npp.getSodonhang()%>" > </td>
								    				<td > <input type =text  readonly="readonly" style="width :100%; background-color: white" value="<%=npp.getsoSku()%>"> </td>
														<%} %>	
								   				</tr>
								   				<%
								   			}
								   		k++;
								   		}
								   	}
								   	%>
									<%
									m++;
								}
							}
							%>
							<tr class="tbfooter" style="height: 20px">
							<td colspan="7" align="right">			
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
</form>

<script type="text/javascript">
tinhphantram();
</script>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>