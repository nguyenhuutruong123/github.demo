<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.kehoachnhanvien.*" %>
<%@ page  import = "geso.dms.center.beans.kehoachnhanvien.imp.*" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.Date" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/dms/index.jsp");
	}else{ %>

<% 
IKeHoachNhanVien khnvBean = (IKeHoachNhanVien)session.getAttribute("khnvBean"); 

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>

<SCRIPT type="text/javascript" src="../scripts/kehoachnhanvien.js"></SCRIPT>

<SCRIPT language="javascript" type="text/javascript">
	var num = 0;

	function submitform()
	{
	    document.forms['khnvForm'].submit();
	}
	
	function saveform()
	{
		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	document.forms['khnvForm'].action.value= 'save';
	    document.forms['khnvForm'].submit();
	}
	function inpdf()
	{
 	 	document.forms['khnvForm'].action.value= 'inpdf';
	    document.forms['khnvForm'].submit();
	}
	function excel()
	{
  	 	document.forms['khnvForm'].action.value= 'excel';
	    document.forms['khnvForm'].submit();
	}
</SCRIPT>

<style>
	.tblightrow li,
	.tbdarkrow li {
		margin-top: 5px;
		line-height: 16px;
	}
</style>


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

<form name='khnvForm' method="post" action="../../KeHoachNhanVienUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="action" value='1'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<INPUT name="id" type="hidden" value='<%= khnvBean.getId() %>' size="30">
<INPUT name="songay" type="hidden" value='<%= khnvBean != null && khnvBean.getNgayList() != null ? khnvBean.getNgayList().length : "" %>' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   			D??? li???u n???n > C?? b???n > K??? ho???ch nh??n vi??n > Hi???n th??? </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%=userTen %>&nbsp;  </TD> 
						    </tr>
						   	<tr>
						   		<TD align="left" height="5" colspan="4" class=""></td>
  							</tr>
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../KeHoachNhanVienSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A>
													    	<A id="btnSave2" href="javascript: inpdf()" ><IMG src="../images/Printer30.png" title="Form in" alt="Form in" border = "1"  style="border-style:outset"></A>
													    	<A id="btnSave1" href="javascript: excel()" ><IMG src="../images/excel.gif" title="Excel" alt="Excel" border = "1"  style="border-style:outset;width: 30px"></A>
							 
							</TD>
						    <TD width="2" align="left" ></TD>
						     
						    
						</TR>
					</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">B??o l???i nh???p li???u </LEGEND>
		    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= khnvBean.getMessage() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>
					<tr>
					   <TD align="left" colspan="4" class="legendtitle">
							<FIELDSET>
							<LEGEND class="legendtitle">K??? ho???ch nh??n vi??n
							</LEGEND>
							<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
								<TR>
								  <TD width="24%" class="plainlabel" >Nh??n vi??n</TD>
								  <TD width="70%" class="plainlabel" ><INPUT name="tennhanvien"
									type="text" value='<%= khnvBean.getTenNhanVien() %>' size="20" readonly></TD>
								</TR>
								<TR>
								  <TD width="24%" class="plainlabel" >Th??ng </TD>
								  <TD width="70%" class="plainlabel" >
								  	<select name="thang" style="width: 200px;" disabled="disabled">
								  		<option value ="<%=khnvBean.getThang() %>" selected><%=khnvBean.getThang() %></option>
									</select>
								  </TD>
							  </TR>
								<TR>
								  <TD class="plainlabel" >N??m </TD>
								  <TD width="70%" class="plainlabel" >
								  	<select name="nam" style="width: 200px;" disabled="disabled">
								  		<option value ="<%=khnvBean.getNam() %>" selected><%=khnvBean.getNam() %></option>
									</select>
								  </TD>
							  	</TR>
							</TABLE>
							</FIELDSET>
						</TD>
					</TR>
					<TR>
						<TD align="left" colspan="4" class="legendtitle">
							<FIELDSET>
							<LEGEND class="legendtitle">Chi ti???t
							</LEGEND>
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="5%">Ng??y </TH>
												<TH width="10%" style="display:none;">T??c v??? </TH>
											    <TH width="45%">N???i dung - Th???c hi???n </TH>
											</TR>
								<% 
									if(khnvBean.getNgayList() != null) 
									{
										%>
										<script>num = <%=khnvBean.getNgayList().length %>;</script>
										<%
										String lightrow = "tblightrow";
										String darkrow = "tbdarkrow";
										
										for(int m = 0; m < khnvBean.getNgayList().length; m++) {
											IKeHoachNhanVienNgay khNgay = khnvBean.getNgayList()[m];
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> ngay="<%=khNgay.getNgay() %>" >
											<%} else {%>
												<TR class= <%=darkrow%> ngay="<%=khNgay.getNgay() %>" >
											<%}%>
													<TD align="center">
														<input type="hidden" name="ngay" value='<%=khNgay.getNgay() %>'>
														<b><%=khNgay.getNgay() %></b>
													</TD>
													<TD align="left"  style="display:none;">
														<A href="javascript: addChiTiet('npp', <%=khNgay.getNgay() %>)"><IMG src="../images/plus32.png" width="16" height="16" title="NPP" alt="NPP"><b>??i NPP</b></A><br/> 
														<A href="javascript: addChiTiet('thitruong', <%=khNgay.getNgay() %>)"><IMG src="../images/plus32.png" width="16" height="16" title="Th??? Tr?????ng" alt="Th??? Tr?????ng"><b>??i th??? tr?????ng</b></A>
													</TD>
													<TD align="left" id="chitiet<%=khNgay.getNgay() %>">
														<ul>
														<%
														IKeHoachNhanVienChiTiet chitiet;
														List<IKeHoachNhanVienChiTiet> nppList = khNgay.getNppList();
														boolean daviengtham;
														for(int j = 0; j < nppList.size(); j++) {
															chitiet = nppList.get(j);
															daviengtham = chitiet.getLat() != null && chitiet.getLat().trim().length() > 0 && chitiet.getLon() != null && chitiet.getLon().trim().length() > 0;
															%>
															<li><span <%=daviengtham ? " style='color:blue;'" : "" %>>NPP: <%=chitiet.getNppId()%></span><%=daviengtham ? " (<span style='color:blue;'>???? vi???ng th??m </span>)" + " <span style='color:red;'>" + chitiet.getThoidiem() + "</span>" : "" %>
																<%=chitiet.getGhiChu() != null && chitiet.getGhiChu().trim().length() > 0 ? "<br/><span style='color:red;'>Ghi ch??: " + chitiet.getGhiChu() + "</span>" : "" %>
																<%=chitiet.getGhiChu2() != null && chitiet.getGhiChu2().trim().length() > 0 ? "<br/><span style='color:red;'>Ghi ch?? th???c hi???n: " + chitiet.getGhiChu2() + "</span>" : "" %>
															</li>
															<%
														}
														nppList = khNgay.getThiTruongList();
															
														for(int j = 0; j < nppList.size(); j++) {
															chitiet = nppList.get(j);
															daviengtham = chitiet.getLat() != null && chitiet.getLat().trim().length() > 0 && chitiet.getLon() != null && chitiet.getLon().trim().length() > 0;
															%>
															<li>
																<span <%=daviengtham ? " style='color:blue;'" : "" %>>Th??? tr?????ng: <%=chitiet.getTinhId()%> - <%=chitiet.getQuanHuyenId()%></span>
																<%=chitiet.getGhiChu() != null && chitiet.getGhiChu().trim().length() > 0 ? "<br/><span style='color:red;'>Ghi ch??: " + chitiet.getGhiChu() + "</span>" : "" %>
																<%=daviengtham && chitiet.getDiaChi() != null && chitiet.getDiaChi().trim().length() > 0 ? "<br/><span style='color:blue;'> - ???? vi???ng th??m t???i ?????a ch???: <b>" + chitiet.getDiaChi() + "</b></span>" : "" %>
																<%=chitiet.getGhiChu2() != null && chitiet.getGhiChu2().trim().length() > 0 ? "<br/><span style='color:red;'> - Ghi ch?? khi th???c hi???n: " + chitiet.getGhiChu2() + "</span>" : "" %>
															</li>
															<%
														}
														nppList = khNgay.getTBHList();
														
														for(int j = 0; j < nppList.size(); j++) {
															chitiet = nppList.get(j);
															
															%>
															<li>
																Tuy???n b??n h??ng: <%=chitiet.getNppTBHId()%> - <%=chitiet.getDDKDId()%> - <%=chitiet.getTBHId() %>
																 - <%=chitiet.getGhiChuTBH()%> <span style='color:red;'><%=chitiet.getTyle() %> </span>
																
															</li>
															<%
														}
														%>
														</ul>
													</TD>
												</TR>
										<%
											}
										}
									%>
											<TR>
												<TD align="center" colspan="11" class="tbfooter">&nbsp;</TD>
											</TR>
											
										</TABLE>
										</TD>
									</TR>
								</TABLE>
							</FIELDSET>
						</TD>
					</TR>
				</TABLE>
			</TD>
	</TR>
 
</TABLE>
</form>
</BODY>
<script>
	
	$("select").select2();
	
</script>
</HTML>

<% 

khnvBean.closeDB(); %>
<%}%>