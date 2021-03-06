<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.beans.kho.IKho"%>
<%@ page import="geso.dms.center.beans.kho.IKhoList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ 
		int[] quyen = new  int[6];
		quyen = util.Getquyen("KhoSvl", "",userId);
		
		System.out.println(quyen[0]);
		System.out.println(quyen[1]);
		System.out.println(quyen[2]);
		System.out.println(quyen[3]);	
		System.out.println(quyen[4]);
%>

<%
	IKhoList obj = (IKhoList)session.getAttribute("obj");
%>
<%
	List<IKho> kholist = (List<IKho>)obj.getKhoList();
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	}	
String url = util.getChuyenNguUrl("KhoSvl","",nnId);	
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
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" language="JavaScript"
	src="../scripts/simplecalendar.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">


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


<SCRIPT language="javascript" type="text/javascript">
	function clearform() {
		document.khoForm.ten.value = "";
		document.khoForm.tungay.value = "";
		document.khoForm.denngay.value = "";
		document.khoForm.trangthai.selectedIndex = 0;
		submitform();
	}

	function submitform() {
		document.forms['khoForm'].action.value = 'search';
		document.forms['khoForm'].submit();
	}

	function newform() {
		document.forms['khoForm'].action.value = 'new';
		document.forms['khoForm'].submit();
	}
	function thongbao() {
		var tt = document.forms['khoForm'].msg.value;
		if (tt.length > 0)
			alert(document.forms['khoForm'].msg.value);
	}
</SCRIPT>


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

	<form name="khoForm" method="post" action="../../KhoSvl"
		charset="UTF-8"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<INPUT name="userId" type="hidden" value='<%=userId%>' size="30">
		<input type="hidden" name="action" value='1'> <input
			type="hidden" name="msg" value='<%=obj.getMsg()%>'>

		<script language="javascript" type="text/javascript">
			thongbao();
		</script>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">
											<%=url %>
										</TD>
										<TD colspan="2" align="right" class="tbnavigation">Ch??o
											m???ng b???n <%=userTen%> &nbsp;
										</td>
									</tr>

								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Ti??u ch?? t??m ki???m </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6"
										cellspacing="0">
										<TR>
											<TD class="plainlabel" width="19%"><%=ChuyenNgu.get("T??n kho",nnId) %></TD>
											<TD class="plainlabel"><INPUT name="ten" size="20"
												type="text" value='<%=obj.getTen()%>'
												onChange="submitform();"></TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("T??? ng??y",nnId) %></TD>
											<TD class="plainlabel">
												<TABLE cellpadding="0" cellspacing="0">
													<TR>
														<TD><input class="days" type="text" name="tungay"
															size="20" value='<%=obj.getTungay()%>'
															onchange="submitform();"></TD>

													</TR>
												</TABLE>
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("?????n ng??y",nnId) %></TD>
											<TD class="plainlabel">
												<TABLE cellpadding="0" cellspacing="0">
													<TR>
														<TD><input class="days" type="text" name="denngay"
															size="20" value='<%=obj.getDenngay()%>'
															onchange="submitform();"></TD>

													</TR>
												</TABLE>
											</TD>

										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Tr???ng th??i",nnId) %></TD>

											<TD class="plainlabel"><select name="trangthai"
												onChange="submitform();">
													<option value="2" selected></option>
													<%
														if (obj.getTrangthai().equals("1")) {
													%>
													<option value="1" selected><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
													<option value="0"><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
													<%
														} else {
													%>
													<%
														if (obj.getTrangthai().equals("0")) {
													%>
													<option value="1"><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
													<option value="0" selected><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
													<%
														} else {
													%>
													<option value="1"><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
													<option value="0"><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
													<%
														}
															}
													%>
											</select></TD>
										</TR>
										<TR>
											<TD class="plainlabel" colspan="2"><a class="button2"
												href="javascript:clearform()"> <img style="top: -4px;"
													src="../images/button.png" alt=""><%=ChuyenNgu.get("Nh???p l???i",nnId) %>
											</a>&nbsp;&nbsp;&nbsp;&nbsp;</TD>
										</TR>
									</TABLE>
								</FIELDSET></TD>
						</TR>
					</TABLE>

					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD align="left">
								<FIELDSET>
									<LEGEND class="legendtitle">
										&nbsp;Danh s??ch kho &nbsp;&nbsp;
										<%
											if (quyen[0] != 0) {
										%>
										<a class="button3" href="javascript:newform()"> <img
											style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("T???o m???i",nnId) %>
										</a>
										<%
											}
										%>
									</LEGEND>

									<TABLE class="" width="100%" border="0" cellpadding="0"
										cellspacing="0">
										<TR>
											<TD width="98%">
												<TABLE width="100%" border="0" cellspacing="1"
													cellpadding="4">
													<TR class="tbheader">
														<TH width="10%"><%=ChuyenNgu.get("T??n kho",nnId) %></TH>
														<TH width="20%"><%=ChuyenNgu.get("Di???n gi???i",nnId) %></TH>
														<TH width="12%"><%=ChuyenNgu.get("Tr???ng th??i",nnId) %></TH>
														<TH width="9%"><%=ChuyenNgu.get("Ng??y t???o",nnId) %></TH>
														<TH width="15%"><%=ChuyenNgu.get("Ng?????i t???o",nnId) %></TH>
														<TH width="9%"><%=ChuyenNgu.get("Ng??y s???a",nnId) %></TH>
														<TH width="14%"><%=ChuyenNgu.get("Ng?????i s???a",nnId) %></TH>
														<TH width="11%"><%=ChuyenNgu.get("T??c v???",nnId) %></TH>
													</TR>
													<%
														IKho kho = null;
															int size = kholist.size();
															int m = 0;
															String lightrow = "tblightrow";
															String darkrow = "tbdarkrow";
															while (m < size) {
																kho = (IKho) kholist.get(m);
																if (m % 2 != 0) {
													%>
													<TR class=<%=lightrow%>>
														<%
															} else {
														%>
													
													<TR class=<%=darkrow%>>
														<%
															}
														%>
														<TD align="left"><%=kho.getTen()%></TD>
														<TD><%=kho.getDiengiai()%></TD>

														<%
															if (kho.getTrangthai().equals("1")) {
														%>
														<TD align="center"><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></TD>
														<%
															} else {
														%>
														<TD align="center"><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></TD>
														<%
															}
														%>

														<TD align="center"><%=kho.getNgaytao()%></TD>
														<TD align="center"><%=kho.getNguoitao()%></TD>
														<TD align="center"><%=kho.getNgaysua()%></TD>
														<TD align="center"><%=kho.getNguoisua()%></TD>
														<TD align="center">
															<TABLE border=0 cellpadding="0" cellspacing="0">
																<TR>
																<TD>
																	<%if(quyen[3]!=0){ %>
				                                                        <A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "KhoUpdateSvl?userId="+userId+"&display="+ kho.getId()+"") %>"><img src="../images/Display20.png" alt="Hi???n th???" title="Hi???n th???" width="20" height="20" longdesc="Hi???n th???" border = 0 "></A>
				                                                    <%} %>
				                                                </TD>
				                                                <TD>&nbsp;</TD>
																	<TD>
																		<%
																			if (quyen[2] != 0) {
																		%> <A
																		href="../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "KhoUpdateSvl?userId="+userId+"&update="+kho.getId()+"")%>"><img
																			src="../images/Edit20.png" alt="Chinh sua"
																			title="C???p nh???t" width="20" height="20"
																			longdesc="Chinh sua" border=0></A> <%
																	 	}
																	 %>
																	</TD>
																	<TD>&nbsp;</TD>
																	<TD>
																		<%
																			if (quyen[1] != 0) {
																		%> <A href="../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "KhoSvl?userId="+userId+"&delete="+kho.getId()+"")%>">
																		<img
																			src="../images/Delete20.png" alt="Xoa" title="X??a"
																			width="20" height="20" longdesc="Xoa" border=0
																			onclick="if(!confirm('B???n mu???n x??a kho n??y?')) return false;"></A>
																			<%
																				}
																			%>
																	</TD>
																</TR>
															</TABLE>
														</TD>
													</TR>
													<%
														m++;
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
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>


<%
	if(kholist != null){ kholist.clear(); kholist = null; }
		obj = null;
		session.setAttribute("obj", null);
	}
%>