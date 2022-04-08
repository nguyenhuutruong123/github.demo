<%@page import="geso.dms.center.beans.donmuahang.ISanPhamTraKM"%>
<%@page import="geso.dms.center.beans.duyetdhkm.IDuyetDhKm"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/AHF/index.jsp");
	} else {
%>
<%
	IDuyetDhKm dhkm = (IDuyetDhKm) session.getAttribute("dhkm");
	String thang = dhkm.getThang();
	String nam = dhkm.getNam();
	String msg = dhkm.getMessage();
	ResultSet rsctkm = dhkm.getRsCTKM();
	ResultSet rslistsp = dhkm.getlistsp();
	String ctkmchon = dhkm.getCTKMChon();
	NumberFormat formatter = new DecimalFormat("#,###,###");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
	function thuchien()
	{
		    if(!confirm("Bạn có chắc muốn thực hiện không ?"))
		    {
			  return;	
		    }
			document.forms["mkbctBean"].action.value="createdh";   	
	   	    document.forms['mkbctBean'].submit();
	}	
	function submit1()
	{
			document.forms["mkbctBean"].action.value="chance";   	
    		document.forms['mkbctBean'].submit();
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
			if (keypressed == 8 ||  keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	 function sellectAll(cbId1,cbId2 )
	  {
	  	 var chkAll_Lct = document.getElementById(cbId1);
	  	 var loaiCtIds = document.getElementsByName(cbId2);
	  	 

	  	 
	  	 if(chkAll_Lct.checked)
	  	 {
	  		 for(var i = 0; i < loaiCtIds.length; i++)
	  		 {
	  			 loaiCtIds.item(i).checked = true;
	  		 }
	  	 }
	  	 else
	  	 {
	  		 for(var i = 0; i < loaiCtIds.length; i++)
	  		 {
	  			 loaiCtIds.item(i).checked = false;
	  		 }
	  	 }
	  }
	

</SCRIPT>

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

	<form name="mkbctBean" method="post" action="../../DuyetdhkmUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value="1"> <input
			type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="userTen" value='<%=userTen%>'>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="1"
			height="100%">
			<TR>
				<TD align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR height="22">
										<TD align="left" class="tbnavigation">&nbsp;Quản lý bán
											hàng &gt; Duyệt đơn khuyến mãi &gt; Tạo mới</TD>
										<TD align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
										</TD>
									</TR>

								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="javascript:history.back()"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>

										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo</LEGEND>

									<textarea name="dataerror"
										style="width: 100%; color: #F00; font-weight: bold"
										style="width: 100%" readonly="readonly" rows="1"><%=msg%></textarea>
								</FIELDSET>
							</TD>
						</tr>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black"> Tạo
										đơn hàng khuyến mãi tháng </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD width="20%" class="plainlabel">Chọn tháng</TD>
											<TD width="80%" class="plainlabel"><select
												onchange="submit1();" name="thang" style="width: 50px"">
													<option value=0></option>
													<%
														int k = 1;
															for (k = 1; k <= 12; k++) {
																String chuoi = "" + k;
																if (thang.equals(chuoi)) {
													%>
													<option value=<%=k%> selected="selected">
														<%=k%></option>
													<%
														} else {
													%>
													<option value=<%=k%>><%=k%></option>
													<%
														}
															}
													%>
											</select></TD>
										</TR>

										<TR>
											<TD class="plainlabel">Chọn năm</TD>
											<TD class="plainlabel"><select onchange="submit1();"
												name="nam" style="width: 50px"">
													<option value=0></option>
													<%
														int n;
															for (n = 2008; n < 2020; n++) {
																if (nam.equals("" + n)) {
													%>
													<option value=<%=n%> selected="selected">
														<%=n%></option>
													<%
														} else {
													%>
													<option value=<%=n%>><%=n%></option>
													<%
														}
															}
													%>
											</select></TD>
										</TR>

										<TR>
											<TD class="plainlabel">Chọn chương trình KM</TD>
											<TD class="plainlabel">
												<table width="100%" id="tabc" class="tabledetail"
													width="100%" border="0" cellpadding="0" cellspacing="1">
													<tr class="tbheader">
														<th>Scheme</th>
														<th>Diễn giải</th>
														<TH align="center" width="10%">Chọn tất cả 
															<input  type="checkbox" id="cbCtkm"    onClick="sellectAll('cbCtkm','ctkmchon')">
														</TH>
													</tr>

													<%
														if (rsctkm != null)
																while (rsctkm.next()) {
													%>
													<tr>
														<td><input type="text" name="scheme"
															value="<%=rsctkm.getString("scheme")%>"></td>
														<td><input type="text" name="diengiai"
															value="<%=rsctkm.getString("diengiai")%>"></td>
														<td>
															<%
																if (ctkmchon.contains(rsctkm.getString("pk_seq"))) {
															%> <input
															type="checkbox" name="ctkmchon" checked="checked"
															value="<%=rsctkm.getString("pk_seq")%>"> <%
 	} else {
 %>
															<input type="checkbox" name="ctkmchon"
															 onchange="submit1();" value="<%=rsctkm.getString("pk_seq")%>">
															
															
															 <%
 	}
 %>
														</td>
													</tr>
													<%
														}
													%>

												</table>
											</TD>
										</TR>
										<TR>
											<%
												if (!dhkm.getTrangthai().equals("1")) {
											%>
											<TD colspan="2"><a class="button2"
												href="javascript:thuchien();"> <img style="top: -4px;"
													src="../images/button.png" alt="">Tạo đơn hàng khuyến
													mãi
											</a>&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2"
												href="javascript:submit1();"> <img style="top: -4px;"
													src="../images/button.png" alt="">Xem sản phẩm
											</a>&nbsp;&nbsp;&nbsp;&nbsp; <%
 	}
 %></TD>
										</TR>
										<tr>

											<td colspan="2">
												<table name="tabledetail" width="100%">
													<tr class="tbheader">
														<th width="20%">Chương trình khuyến mãi</th>
														<th width="20%">Sản phẩm</th>
														<th width="10%">Số lượng</th>

														<TH align="center" width="10%">Chọn tất cả 
															<input  type="checkbox" id="cbNpp"  onClick="sellectAll('cbNpp','npp')">
														</TH>
													</tr>
													<%
													  String nppIdPrev="";
														String kvIdPrev ="";
														int i = 0;
															if (rslistsp != null)
																while (rslistsp.next()) 
																{
																	if(!nppIdPrev.trim().equals(rslistsp.getString("tennpp").trim()))
									 						       	{ 
																			nppIdPrev= rslistsp.getString("tennpp");
																			%>
																		  <tr style="color:black ;font-weight: bold;font-size:12" >
																		
																		 <TD colspan="<%=4%>"  >
																			 <%=" "+rslistsp.getString("tennpp")%>   
																		 </TD>  
																		 </tr>
																	<%} %>
																	
													<tr class='tblightrow'>
													<td>
															<input style="width: 100%" readonly="readonly" type="text" name="scheme" value="<%=rslistsp.getString("scheme")%>">
													</td>
														<td>
															<input style="width: 100%"  readonly="readonly" type="text" name="spma" value="<%=rslistsp.getString("ten")%>">
														</td>
														<td>
															<input style="width: 100%"  readonly="readonly" type="text" name="soluong" readonly="readonly"  value="<%=  formatter.format(rslistsp.getDouble("soluong")) %>"  style="width:100%"></td>
														<%
															if (dhkm.getTable().indexOf( rslistsp.getString("nppScheme")) >= 0) {
														%>
														<TD align="center">
															<input type="checkbox" name="npp"  id="npp" value="<%=rslistsp.getString("nppScheme")%>"  checked="checked">
														</TD>
														<%
															} else {
														%>
														<TD align="center">
															<input type="checkbox" name="npp"  id="npp" value="<%=rslistsp.getString("nppScheme")%>">
														</TD>
														<%
															}
														%>
													</tr>
													<%
														i++;
																}
													%>
												</table>
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
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
	}
%>