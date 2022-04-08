<%@page import="java.sql.SQLException"%>
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
String tennhapp=dhBean.getTenNPPMua();
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
		 $('.addspeech').speechbubble();
	})
</script>

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax-dynamic-list.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>

<script language="javascript" type="text/javascript">

function upload(){// nhan nut cap nhat

	   if(document.forms["dhForm"].filename.value==""){
		   
		   document.forms["dhForm"].dataerror.value="Chưa tìm file upload lên. Vui lòng chọn file cần upload.";
	   }else{
		 document.forms["dhForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["dhForm"].submit();	
	   }

}
function printform()
{   
	document.getElementById("btnPrint").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

	document.forms['dhForm'].action.value='print';
   	document.forms["dhForm"].submit();
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

<form name="dhForm" method="post" action="../../MuaHangNhaPPKhacSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="nppId" value='<%= dhBean.getNppId_Ban() %>'>
<input type="hidden" name="action" value='1'>
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
			 								   <TD align="left"  class="tbnavigation">&nbsp;Quản lý tồn kho > Mua hàng từ NPP khác > Hiển thị </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng bạn <%= tennhapp %> &nbsp; </TD>
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
							    			<TD width="30" align="left">
							    			   <div id="btnPrint">
                            <A href="../../MuaHangNhaPPKhacSvl?userId=<%=userId %>&print=<%= dhBean.getId() %>" ><img src="../images/Printer30.png" alt="In" title="In chung tu" width="30" height="30" longdesc="In chung tu" border=1 style="border-style:outset"></A>
                            </div>
							    			</TD>
							    			<TD width="2" align="left" >&nbsp;</TD>
							    			<TD width="30" align="left">
							    			   <div id="btnPrint">
                            <A href="../../MuaHangNhaPPKhacSvl?userId=<%=userId %>&dis=<%= dhBean.getId() %>" ><img src="../images/Display20.png" alt="In" title="Xem chung tu" width="30" height="30" longdesc="In chung tu" border=1 style="border-style:outset"></A>
                            </div>
							    			</TD>
							    		<TD >&nbsp;</TD>
								    		<!-- <TD align="right" >
								    		 <a class="button2" href="javascript:upload()">
												<img style="top: -4px;" src="../images/button.png" alt="">Upload</a>							
										
								    		 <INPUT type="file" name="filename" size="40" value=''> </TD> -->
										</TR>
										
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="0" width="100%" cellpadding = "0" cellspacing = "0" >
								<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>			
				    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width:100%"  rows="1" readonly="readonly" > </textarea>
									</FIELDSET>
							   </TD>
								</tr>
								<TR>
									<TD  align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Đơn hàng bán </LEGEND>
										<TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>
												<TR >
												<TD width="22%" class="plainlabel">Số đơn hàng </TD>
												<TD colspan="3" class="plainlabel"><%= dhBean.getId() %> </TD>
												</TR>							
											
											<TR >
												<TD width="22%" class="plainlabel">Nhà phân phối bán hàng</TD>
												<TD colspan="3" class="plainlabel"><%=  dhBean.getTenNPPBan() %> </TD>
												</TR>							
											
											<TR class="tblightrow">
												<TD  class="plainlabel">Nhà phân phối mua hàng</TD>
												<TD colspan="3" class="plainlabel"><%= dhBean.getTenNPPMua() %> </TD>
												
											</TR>
												<TR >
											  <TD class="plainlabel">Ngày giao dịch </TD>
											  <TD colspan="3" class="plainlabel">
											  <table border=0 cellpadding="0" cellspacing="0">
                                                <TR><TD>
											    <input type="text" name="tungay" size="20" value="<%= dhBean.getNgayGiaoDich() %>" readonly >
											</TD>
											<TD>
											</TD></TR>
                                          </table></TR>
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
											    <TD colspan="3"  class="plainlabel"><input name="TIENCOTHUE" id="TIENCOTHUE" type="text" value="<%= tiencothue %>" readonly>%</TD>
										  	</TR>
												
										</TABLE>
									</FIELDSET>
								  </TD>
							   </TR>	
							   <TR>
							   		<TD>
										<TABLE width = "100%" border="1" cellpadding="2px" cellspacing="1" >
										<tbody id="san_pham">
												<TR class="tbheader" >
													<TH width="15%">Mã sản phẩm </TH>
													<TH width="28%">Tên sản phẩm </TH>
													<TH width="7%">DVT</TH>
													<TH width="10%">Số lượng </TH>
													<TH width="10%">Số lượng nhận </TH>
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
									<input name="tensp" type="text" readonly value="<%=sanpham.getMaSanPham()%>" style="width:100%">
									</TD>
									<TD align="left" >
										<input name="tensp" type="text" readonly value="<%=sanpham.getTenSanPham()%>"  style="width:100%"></TD>
									 <TD align = "center" ><input name="donvitinh" type="text" value="<%= sanpham.getDVT() %>" readonly  style="text-align:center ;width:100%"></TD>
						        	<TD align="left" >
										<input name="tensp" type="text" readonly value="<%=sanpham.getSoLuong()%>" style="width:100%"></TD>
										<TD align="left" >
										<input name="tensp" type="text" readonly value="<%=sanpham.getSoLuongNhan() %>" style="width:100%"></TD>
									 <TD align = "center" >
								    	<input type="text" name="dongia" value="<%= sanpham.getGiaMua() %>" readonly style="text-align:right;width:100%">
								   <TD align = "center" >
								    	<input type="text" name="thantien" value="<%= sanpham.getThanhTien() %>" readonly  style="text-align:right;width:100%">
								     </TD>
								</TR>
								<% m++; }}%>
								<TR><TD colspan="8" class="tbfooter">&nbsp;</TD></TR>																																																																																																															
								</tbody></TABLE>							
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
	session.setAttribute("obj",null);

}catch(Exception er){
	
}

%>
<%}%>


