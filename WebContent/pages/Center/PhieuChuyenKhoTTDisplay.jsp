<%@page import="geso.dms.center.beans.phieuchuyenkhott.imp.PhieuChuyenKhoTT_SanPham"%>
<%@page import="geso.dms.center.beans.phieuchuyenkhott.IPhieuChuyenKhoTT_SanPham"%>
<%@page import="geso.dms.center.beans.phieuchuyenkhott.IPhieuChuyenKhoTT"%>
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
<% IPhieuChuyenKhoTT dhBean = (IPhieuChuyenKhoTT)session.getAttribute("obj");
ResultSet rs_khott=(ResultSet)session.getAttribute("rs_khott");
ResultSet rs_phieunhap=(ResultSet)session.getAttribute("rs_phieunhap");
%>
<% List<IPhieuChuyenKhoTT_SanPham> splist = dhBean.getListSanPham(); %>
<% String userId = (String) session.getAttribute("userId"); 
   String userTen=(String)session.getAttribute("userTen");
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


function submitform(){

	document.forms['dhForm'].action.value='submit';
    document.forms["dhForm"].submit();
	}
function layhang(){
	document.forms['dhForm'].action.value='layhang';
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

<form name="dhForm" method="post" action="../../PhieuChuyenKhoTTNewSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen%>'>
<input type="hidden" name="action" value='new'>
<input type="hidden" name="tenform" value="newform">
<INPUT type="hidden" name="id" value='<%= dhBean.getId() %>'>
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
			 								   <TD align="left"  class="tbnavigation">&nbsp;Quản lý tồn kho > Kho trung tâm > Phiếu Chuyển kho  > Xem </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng bạn &nbsp; <%=userTen %> &nbsp; </TD>
					    				 </TR>
									  </TABLE>
								  </TD>
							  </TR>	
						  	</TABLE>
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
								<TR ><TD >
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class = "tbdarkrow">
											<TD width="30" align="left"><A href = "javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay về" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
							    			<TD width="30" align="left"><A href="Print.jsp" ><img src="../images/Printer30.png" alt="In" title="In chứng từ" longdesc="In chung tu" border=1 style="border-style:outset"></A></TD>
								    		<TD align="left" >&nbsp;</TD>
										</TR>
									</TABLE>
								</TD>
								</TR>
							    </TABLE>												
							        <TABLE border="0" width="100%" cellpadding = "0" cellspacing = "0" >
									
									<TR>
									<TD  align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Kho nhận </LEGEND>
										<TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>																											
										<TR>
									  	<TD class="plainlabel" width="19%">Kho nhận</TD>
									  	<TD class="plainlabel" >
									  	<input type="text" readonly="readonly" value="<%=dhBean.getTenKhoNhan()%>">
									  
									  	</TD>
							        	</TR>
							        	<TR >
											  <TD class="plainlabel">Kho hàng chuyển </TD>
											  <TD colspan="3" class="plainlabel">
											  	<input type="text" name="khochuyenten" readonly="readonly" size="20" value='<%=dhBean.getTenKhoChuyen() %>'>
											    <input type="hidden" name="khochuyenid" size="20" value='<%=dhBean.getKhoID_Chuyen() %>'>
												</TD>
											</TR>
							       		

										<TR >
											  <TD class="plainlabel">Ngày chuyển kho </TD>
											  <TD colspan="3" class="plainlabel">
											  <TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
											    <input type="text" name="ngaychuyenkho" size="20" readonly="readonly" value='<%=dhBean.getNgayChuyen() %>'>
											</TD><TD>
											</TD>
											</TR>
							       			</TABLE>
							              </TR>	
							           
									</TABLE>
										</FIELDSET>
							  			</TD>
							  		 </TR>	
							  		 <TR>
							   		 <TD>
							    		<fieldset>
										<TABLE width = "100%" cellpadding="0" cellspacing="1" >
										<tbody id="san_pham">
												<TR class="tbheader" >
													<TH width="8%">Số thứ tự </TH>
													<TH width="22">Mã sản phẩm </TH>
													<TH width="40%">Tên sản phẩm </TH>
													<TH width="10%">Số lượng chuyển</TH>
												</TR>
										<% 
											int size=0;
												if(splist != null){
											IPhieuChuyenKhoTT_SanPham sanpham =new PhieuChuyenKhoTT_SanPham();
											size = splist.size();
											int m = 0;
											while (m < size){
											sanpham = (IPhieuChuyenKhoTT_SanPham)splist.get(m); 
								
								%>
									<TR class= 'tblightrow' >
								    	<TD>
								    	<input name="sott" type="text" readonly="readonly" value="<%=m+1%>"  style="width:100%;">
								    	 </TD>
										 <TD align="left" >
										<input name="masp" type="text" readonly="readonly"  value="<%=sanpham.getMaSanpham()%>"    style="width:100%;">
										</TD>
										<TD align="left" >
										<input name="tensp" type="text" readonly value="<%=sanpham.getTenSanPham()%>" style="width:100%">
										</TD>
										<TD align="left" >
										<input name="soluongchuyen" type="text" readonly="readonly" value="<%=sanpham.getSoLuongChuyen()%>" onblur="rechecksoluong();" style="width:100%">
										</TD>
							    	</TR>
								<% m++; 
							    }
							}%>																																																																																																													
								
						      <TR>
						       <TD colspan="8" class="tbfooter">&nbsp;</TD>
					          </TR>	
					         </tbody>   
					      </TABLE> 
						</fieldset> 							
			      	</TD>
			
									</TR>
									</TABLE>
				</TBODY>
				</TABLE>							
				
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>


