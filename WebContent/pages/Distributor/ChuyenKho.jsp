<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.dms.distributor.beans.chuyenkhonew.IChuyenKhoList"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	String nppId = (String) session.getAttribute("nhappId");
	
	Utility util = (Utility) session.getAttribute("util");
	
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/AHF/index.jsp");
	} else {
%>

<%
	IChuyenKhoList obj = (IChuyenKhoList) session.getAttribute("obj");
%>
<%
	ResultSet dhlist = (ResultSet) obj.getDhList();
ResultSet nvRs = (ResultSet) obj.getNvRs();

%>
<%
	obj.setNextSplittings();
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url ="";
if(obj.getView().trim().length() >0)
	url =  util.getChuyenNguUrl("ChuyenKhoSvl","&view="+obj.getView(),nnId);	
else
	url =  util.getChuyenNguUrl("ChuyenKhoSvl","",nnId);	


	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>AHF - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
	});
</script>
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.js"></script>
<link rel="stylesheet" href="../css/jqueryautocomplete.css"
	type="text/css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/jqueryautocomplete.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>
<SCRIPT language="javascript" type="text/javascript">
	function submitform() {
		document.forms["ddhForm"].submit();
	}
	
	function submitForm(arg) {

		document.forms["ddhForm"].action.value = arg;
		document.forms["ddhForm"].submit();

	}

	function newform() {
		document.forms['ddhForm'].action.value = 'new';
		document.forms['ddhForm'].submit();
	}

	function clearform() {
		//document.forms["ddhForm"].sku.value = "";
		document.forms["ddhForm"].tungay.value = "";
		document.forms["ddhForm"].denngay.value = "";
		document.forms["ddhForm"].trangthai.value = "";
		document.forms["ddhForm"].sochungtu.value = "";
		document.forms["ddhForm"].nguoitao.value = "";
		document.forms["ddhForm"].submit();
	}
	
	function thongbao() {
		var tt = document.forms['ddhForm'].msg.value;
		if (tt.length > 0)
			alert(document.forms['ddhForm'].msg.value);

		document.forms['ddhForm'].msg.value = '';
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

	<form id="ddhForm" name="ddhForm" method="post"
		action="../../ChuyenKhoSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="1"> 
		<input type="hidden" name="view" value="<%=obj.getView()%>"> 
		<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
		<%-- <input type="hidden" name="crrApprSplitting" value="<%=obj.getCrrApprSplitting()%>">
		<input type="hidden" name="nxtApprSplitting" value="<%=obj.getNxtApprSplitting()%>"> --%>
		<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<script language="javascript" type="text/javascript">
	<%if(obj.getMsg().trim().length() > 0){%>
		thongbao(); 
	<%}%>
</script>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%" bgcolor="#FFFFFF">
			<TR>
				<TD colspan="4" align='left' valign='top'>

					<TABLE width="100%" cellspacing="0" cellpadding="0">
						<TR>
							<TD>
								<TABLE width="100%" cellspacing="1" cellpadding="0">
									<TR>
										<TD align="left" class="tbnavigation">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr height="22">
													<TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %></TD>
													
										<% if( obj.getNppId().length() != 0 ) { %>
										<TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng bạn",nnId) %>  <%= obj.getNppTen() %>&nbsp;
										<% } else { %>
										<TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng Bạn",nnId) %> <%= userTen %>&nbsp;
										<% } %>
										</TD>
											</table>
										</TD>
									</TR>
								</TABLE>

								<TABLE border="0" width="100%" cellspacing=0 cellpadding=0>

									<TR>
										<TD width="100%" align="left">
											<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Tiêu chí hiển thị",nnId) %>
													&nbsp;</LEGEND>

												<TABLE width="100%" cellpadding="4" cellspacing="0">
													<TR>
														<TD class="plainlabel"><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
														<td class="plainlabel"><input type="text"
															class="days" size="11" id="tungay" name="tungay"
															value="<%=obj.getTungay()%>" maxlength="10" readonly />
														</td>
													</TR>
													<TR>
														<TD class="plainlabel"><%=ChuyenNgu.get("Ðến ngày",nnId) %></TD>
														<td class="plainlabel"><input type="text"
															class="days" size="11" id="denngay" name="denngay"
															value="<%=obj.getDenngay()%>" maxlength="10" readonly />
														</td>
													</TR>
													<TR>
														<TD class="plainlabel"><%=ChuyenNgu.get("Số chứng từ",nnId) %></TD>
														<td class="plainlabel"><input type="text"
															 size="11" id="sochungtu" name="sochungtu"
															value="<%=obj.getSochungtu()%>"  />
														</td>
													</TR>
													<TR>
														<TD class="plainlabel"><%=ChuyenNgu.get("Người tạo",nnId) %></TD>
														<TD class="plainlabel"><select name="nguoitao" id="nguoitao"  class="select2" style="width:200px" >
											<option value="" ></option>
										<% if(nvRs !=null){ 
											while(nvRs.next())
											{ 
											if(nvRs.getString("pk_seq").equals(obj.getNguoitao())) {
											%>
											<option value="<%=nvRs.getString("pk_seq")%>" selected><%=nvRs.getString("ten")%></option>
											<%} else { %>
											<option value="<%=nvRs.getString("pk_seq")%>" ><%=nvRs.getString("ten")%></option>
											<%}}} %>
											</select></TD>
													</TR>
													<TR>
														<TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId) %></TD>
														<TD class="plainlabel"><select id="trangthai"
															name="trangthai" onChange="submitform();">
																<%
																	if (obj.getTrangthai().equals("0")) {
																%>
																<option value=""></option>
																<option value="0" selected><%=ChuyenNgu.get("Chưa chốt",nnId) %></option>
																<option value="1" ><%=ChuyenNgu.get("Chờ duyệt",nnId) %></option>
																<option value="3"><%=ChuyenNgu.get("Hoàn thành",nnId) %></option>
																<option value="2"><%=ChuyenNgu.get("Đã hủy",nnId) %></option>
																<%
																	} else if (obj.getTrangthai().equals("3")) {
																%>
																<option value=""></option>
																<option value="0"><%=ChuyenNgu.get("Chưa chốt",nnId) %></option>
																<option value="1" ><%=ChuyenNgu.get("Chờ duyệt",nnId) %></option>
																<option value="3" selected="selected"><%=ChuyenNgu.get("Hoàn thành",nnId) %></option>
																<option value="2"><%=ChuyenNgu.get("Đã hủy",nnId) %></option>
																<%
																	} else if (obj.getTrangthai().equals("1")) {
																%>
																<option value=""></option>
																<option value="0"><%=ChuyenNgu.get("Chưa chốt",nnId) %></option>
																<option value="1" selected="selected"><%=ChuyenNgu.get("Chờ duyệt",nnId) %></option>
																<option value="3"><%=ChuyenNgu.get("Hoàn thành",nnId) %></option>
																<option value="2" ><%=ChuyenNgu.get("Đã hủy",nnId) %></option>
																<%
																	} else if (obj.getTrangthai().equals("2")) {
																%>
																<option value=""></option>
																<option value="0"><%=ChuyenNgu.get("Chưa chốt",nnId) %></option>
																<option value="1" ><%=ChuyenNgu.get("Chờ duyệt",nnId) %></option>
																<option value="3"><%=ChuyenNgu.get("Hoàn thành",nnId) %></option>
																<option value="2" selected="selected"><%=ChuyenNgu.get("Đã hủy",nnId) %></option>
																<%
																	}  else {
																%>
																<option value=""></option>
																
																<option value="0"><%=ChuyenNgu.get("Chưa chốt",nnId) %></option>
																<option value="1" ><%=ChuyenNgu.get("Chờ duyệt",nnId) %></option>
																<option value="3"><%=ChuyenNgu.get("Hoàn thành",nnId) %></option>
																<option value="2"><%=ChuyenNgu.get("Đã hủy",nnId) %></option>
																<%
																	}
																%>
														</select></TD>
													</TR>
													<TR>

														<TD class="plainlabel" align="left" colspan=2><a
															class="button1" href="javascript:submitForm('Hien thi')">
																<img style="top: -4px;" src="../images/Search30.png"
																alt=""><%=ChuyenNgu.get("Tìm kiếm",nnId) %>
														</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2"
															href="javascript:clearform()"> <img
																style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %>
														</a>&nbsp;&nbsp;&nbsp;&nbsp; <!--  <INPUT name="reinitialiser" type="button" value="Nhap  lai gia tri" onclick="clearform()" >-->

														</TD>

													</TR>


												</TABLE>
											</FIELDSET>
										</TD>
									</TR>
								</TABLE>
								<TABLE width="100%" cellspacing=0 cellpadding=0>
									<TR>
										<TD>
											<FIELDSET>
											<% if( obj.getNppId().length() != 0 ) {%>
												<LEGEND class="legendtitle">
													&nbsp;<%=ChuyenNgu.get("Chuyển kho",nnId) %>&nbsp;&nbsp; <a class="button3"
														onclick="newform()"> <img style="top: -4px;"
														src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %>
													</a>
												</LEGEND>
											<% } %>

												<TABLE width="100%">
													<TR>
														<TD>
															<TABLE width="100%" border="0" cellspacing="1"
																cellpadding="4">
																<TR class="tbheader">
																	<TH><%=ChuyenNgu.get("Ngày điều chỉnh",nnId) %></TH>
																	<TH><%=ChuyenNgu.get("Số chứng từ",nnId) %></TH>
																	<TH><%=ChuyenNgu.get("Người tạo",nnId) %></TH>
																	<TH><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
																	<TH><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
																	<% if (obj.getNppId().length() == 0) { %>
																	<TH><%=ChuyenNgu.get("Người duyệt",nnId) %></TH>
																	<TH><%=ChuyenNgu.get("Ngày duyệt",nnId) %></TH>
																	<% } %>
																	<TH><%=ChuyenNgu.get("Trạng thái",nnId) %></TH>
																	<TH align="center">&nbsp;<%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
																</TR>

																<%
																	NumberFormat formatter = new DecimalFormat("#,###,###");
																		int m = 0;
																		String lightrow = "tblightrow";
																		String darkrow = "tbdarkrow";
																		String style = "style  =\"background-color:#000000\"";
																		if (dhlist != null) {
																%>
																<%
																	try {
																				while (dhlist.next()) {
																					String trangthai = dhlist.getString("trangthai");
																					if (trangthai.equals("0")) {
																						trangthai = "Chưa chốt ";
																						style = "style  =\"color:#000000\"";
																					} else if (trangthai.equals("1")) {
																						/* if (obj.getNppId().length() > 0) {
																							trangthai = "Hoàn thành ";
																							style = "style  =\"color:#000000\"";
																						} else { */
																							trangthai = " Chờ duyệt ";
																							style = "style  =\"color:#000000\"";
																						/* } */
																					} else if (trangthai.equals("2")) {
																						trangthai = " Đã hủy ";
																						style = "style  =\"color:#000000\"";
																					} else {
																						trangthai = "Hoàn thành";
																						style = "style  =\"color:#000000\"";
																					}

																					if (m % 2 != 0) {
																%>
																<TR class=<%=lightrow%> <%=style%>>
																	<%
																		} else {
																	%>
																
																<TR class=<%=darkrow%> <%=style%>>
																	<%
																		}
																	%>
																	<TD align="center"><%=dhlist.getString("ngaydieuchinh")%></TD>
																	<TD align="center"><%=dhlist.getString("nhaphangId")%></TD>
																	<TD align="center"><%=dhlist.getString("nguoitao")%></TD>
																	<TD align="center"><%=dhlist.getString("ngaytao")%></TD>
																	<TD align="center"><%=dhlist.getString("nguoisua")%></TD>
																	<% if (obj.getNppId().length() == 0) { %>
																	<TD align="center"><%=dhlist.getString("nguoiduyet")==null?"":dhlist.getString("nguoiduyet")%></TD>
																	<TD align="center"><%=dhlist.getString("ngaychot")==null?"":dhlist.getString("ngaychot")%></TD>
																	<% } %>
																	<TD align="center"><%=trangthai%></TD>
																	<TD align="center">
																		<TABLE border=0 cellpadding="0" cellspacing="0">
																			<TR>
																				<TD>
																					<%
																						switch (Integer.parseInt(dhlist.getString("trangthai"))) {
																							case 0: %> 
<%-- 																								<A href="../../ChuyenKhoUpdateSvl?userId=<%=userId%>&display=<%=dhlist.getString("nhaphangId")%>&nppId=<%=dhlist.getString("nppId")%>&view=<%="NPP"%>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" title="Hiển thị" border=0"></A> --%>
																								<A href = "../../ChuyenKhoUpdateSvl?userId=<%=userId%>&update=<%=dhlist.getString("nhaphangId") %>"><img src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0 "></A>
																								<A href = "../../ChuyenKhoSvl?userId=<%=userId%>&GoiLenTrungTam=<%= dhlist.getString("nhaphangId") %>"><img  onclick="if(!confirm('Bạn muốn gởi đơn chuyển kho này lên TT không ?')) return false;"  src="../images/Chot.png" alt=C title="Gởi" width="25" height="25" longdesc=" Gởi " border = 0 ></A> 
																								<A href="../../ChuyenKhoSvl?userId=<%=userId%>&delete=<%=dhlist.getString("nhaphangId")%>"><img onclick="if(!confirm('Bạn muốn hủy đơn chuyển kho này?')) return false;" src="../images/Delete20.png" alt="Xóa" title=" Hủy " width="20" height="20" longdesc=" Hủy " border=0"></A> 																								
																								<% break;
 																							case 1: %> 
 																							<% if (obj.getNppId().length() == 0) { %>
																								<A href="../../ChuyenKhoUpdateSvl?userId=<%=userId%>&display=<%=dhlist.getString("nhaphangId")%>&nppId=<%=dhlist.getString("nppId")%> &view=<%="TT"%>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" title="Hiển thị" border=0"></A> 
																								<A href="../../ChuyenKhoSvl?userId=<%=userId%>&delete=<%=dhlist.getString("nhaphangId")%>"><img onclick="if(!confirm('Bạn muốn hủy đơn chuyển kho này?')) return false;" src="../images/Delete20.png" alt="Xóa" title=" Hủy " width="20" height="20" longdesc=" Hủy " border=0"></A> <%
																							} else { %> 
																								<A href="../../ChuyenKhoUpdateSvl?userId=<%=userId%>&display=<%=dhlist.getString("nhaphangId")%>&nppId=<%=dhlist.getString("nppId")%> &view=<%="NPP"%>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" title="Hiển thị" border=0"></A> 
																							<% } %> 																								<% break;
 																							default:
 %> <A
																					href="../../ChuyenKhoUpdateSvl?userId=<%=userId%>&display=<%=dhlist.getString("nhaphangId")%>&nppId=<%=dhlist.getString("nppId")%>&view=<%=""%>"><img
																						src="../images/Display20.png" alt="Hiển thị"
																						width="20" height="20" longdesc="Hiển thị"
																						title="Hiển thị" border=0"></A> <%
 	}
 %> <%--                                 	                    	<% if (dhlist.getString("trangthai").equals("0")) {%> --%>
																					<%--                                 	                    	<% 	System.out.println("@@@@@@" + obj.getNppId()); %> --%>
																					<%-- 															<% 	System.out.println("Phân phối========"); %> --%>
																					<%-- 	                                    	                    <A href = "../../ChuyenKhoUpdateSvl?userId=<%=userId%>&update=<%=dhlist.getString("nhaphangId") %>"><img src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0 "></A> --%>
																					<%-- 	                                    	                    <A href = "../../ChuyenKhoSvl?userId=<%=userId%>&GoiLenTrungTam=<%=dhlist.getString("nhaphangId") %>"><img  onclick="if(!confirm('Bạn muốn gởi đơn chuyển kho này không ?')) return false;"  src="../images/Chot.png" alt=C title="Gởi lên trung tâm" width="20" height="20" longdesc="Gởi lên trung tâm" border = 0 "></A> --%>
																					<%--     	                                	                   	<A href = "../../ChuyenKhoSvl?userId=<%=userId%>&deleteNPP=<%=dhlist.getString("nhaphangId") %>"><img  onclick="if(!confirm('Bạn muốn xóa đơn chuyển kho này?')) return false;"  src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" longdesc="Xóa" border = 0 "></A>  --%>
																					<%-- 															<%} else if ( dhlist.getString("trangthai").equals("1") && obj.getNppId().length() > 0 ) { %> --%>
																					<%--                                 	                    		<A href = "../../ChuyenKhoUpdateSvl?userId=<%=userId%>&display=<%=dhlist.getString("nhaphangId") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" title="Hiển thị" border = 0 "></A> --%>
																					<%-- 															<%} else if ( dhlist.getString("trangthai").equals("1") && obj.getNppId().length() == 0 ) { %> --%>
																					<%-- 															<% 	System.out.println("@@@@@@" + obj.getNppId()); %> --%>
																					<%-- 															<% 	System.out.println("Trung tâm========"); %> --%>
																					<%-- 																<A href = "../../ChuyenKhoUpdateSvl?userId=<%=userId%>&update=<%=dhlist.getString("nhaphangId") %>"><img src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0 "></A> --%>
																					<%-- 	                                    	                    <A href = "../../ChuyenKhoSvl?userId=<%=userId%>&XacNhan=<%=dhlist.getString("nhaphangId") %>"><img  onclick="if(!confirm('Bạn muốn xác nhận đơn chuyển kho này?')) return false;"  src="../images/Chot.png" alt=C title="Xác nhận" width="20" height="20" longdesc="Xác nhận" border = 0 "></A> --%>
																					<%--     	                                	                   	<A href = "../../ChuyenKhoSvl?userId=<%=userId%>&delete=<%=dhlist.getString("nhaphangId") %>"><img  onclick="if(!confirm('Bạn muốn xóa đơn chuyển kho này?')) return false;"  src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" longdesc="Xóa" border = 0 "></A> --%>
																					<%-- 															<% } else if ( dhlist.getString("trangthai").equals("2") && obj.getNppId().length() > 0 ) {%> --%>
																					<%-- 																<A href = "../../ChuyenKhoUpdateSvl?userId=<%=userId%>&display=<%=dhlist.getString("nhaphangId") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" title="Hiển thị" border = 0 "></A>	                                    	                        	                                	                   	 --%>
																					<%-- 															<% } else if ( dhlist.getString("trangthai").equals("2") && obj.getNppId().length() == 0 ) {%>   --%>
																					<%-- 																<A href = "../../ChuyenKhoUpdateSvl?userId=<%=userId%>&display=<%=dhlist.getString("nhaphangId") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" title="Hiển thị" border = 0 "></A> --%>
																					<%-- 																<A href = "../../ChuyenKhoSvl?userId=<%=userId%>&delete=<%=dhlist.getString("nhaphangId") %>"><img  onclick="if(!confirm('Bạn muốn xóa đơn chuyển kho này?')) return false;"  src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" longdesc="Xóa" border = 0 "></A>																 --%>
																					<%-- 															<% } else if ( dhlist.getString("trangthai").equals("3") ) { %>    --%>
																					<%-- 																<A href = "../../ChuyenKhoUpdateSvl?userId=<%=userId%>&display=<%=dhlist.getString("nhaphangId") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" title="Hiển thị" border = 0 "></A>  --%>
																					<%-- 															<% } %>                              	                    --%>
																				</TD>
																		</TABLE>
																	</TD>
																</TR>
																<%
																	m++;
																				}
																			} catch (java.sql.SQLException e) {
																				e.printStackTrace();
																			}
																		}
																%>


											<tr class="tbfooter">
												<TD align="center" valign="middle" colspan="13" class="tbfooter">
											 <% obj.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
																</tr>


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
				</TD>
			</TR>
		</TABLE>
	</form>


</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<%
	if (!(dhlist == null))
			dhlist.close();
%>
<%
	if (obj != null) {
			obj.DBclose();
			obj = null;
		}
%>
<%
	}
%>
