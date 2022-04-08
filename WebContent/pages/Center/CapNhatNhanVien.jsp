<%@page
	import="geso.dms.center.beans.capnhatnhanvien.ICapnhatnhanvienList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="geso.dms.center.beans.nhacungcap.INhacungcap"%>
<%@ page import="geso.dms.center.beans.nhacungcap.INhacungcapList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page import="geso.dms.center.util.ChuyenNgu"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/AHF/index.jsp");
	} else {
		int[] quyen = new int[6];
		quyen = util.Getquyen("CapnhatnhanvienSvl", "", userId);
%>

<%
	ICapnhatnhanvienList obj = (ICapnhatnhanvienList) session.getAttribute("obj");	
	ResultSet DSNV = (ResultSet) obj.getDSNV();
	ResultSet loainv = (ResultSet) obj.getLoainv();
	String nnId = (String) session.getAttribute("nnId");
	if (nnId == null) {
		nnId = "vi";
	}
	String url = util.getChuyenNguUrl("CapnhatnhanvienSvl", "",nnId);
	ResultSet loainhanvienRs = obj.getLoainhanvienRs();
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
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">


<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { 
      $("select:not(.notuseselect2)").select2({ width: 'resolve' });     
     });
</script>

<SCRIPT language="javascript" type="text/javascript">

function clearform()
{  document.forms['nccForm'].action.value= 'xoa';
     document.forms['nccForm'].submit();
}

function submitform()
{
	document.forms['nccForm'].action.value= 'search';
	document.forms['nccForm'].submit();
}

function newform()
{
	document.forms['nccForm'].action.value= 'new';
	document.forms['nccForm'].submit();
	document.forms['nccForm'].submit();
}

function xuatExcel()
{
	document.forms['nccForm'].action.value= 'toExcel';
	document.forms['nccForm'].submit();
	document.forms['nccForm'].action.value= '';
}


function Xoa(id){
	
	document.forms['nccForm'].IdXoa.value = id;
	document.forms['nccForm'].action.value= 'delete';
	document.forms['nccForm'].submit();
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

	<form name="nccForm" method="post" action="../../CapnhatnhanvienSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="action" value='1'>
			<input type="hidden" name="IdXoa" value=''>			
			

		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation"><%=" " + url%>
										</TD>
										<TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng bạn", nnId)%>
											<%=userTen%> &nbsp;</td>
									</tr>
								</table>
							</TD>
						</TR>
						<TR>
							<TD>
								<TABLE border="0" width="100%">
									<TR>
										<TD width="100%" align="left"><FIELDSET>
												<LEGEND class="legendtitle"><%=ChuyenNgu.get("Tiêu chí tìm kiếm", nnId)%>
												</LEGEND>

												<TABLE class="tblight" width="100%" cellpadding="6"
													cellspacing="0">
													<TR>
														<TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Tên nhân viên", nnId)%>
														</TD>
														<TD width="81%" class="plainlabel"><INPUT name="ten"
															type="text" size="40" value="<%=obj.getTen()%>" onChange="submitform();"></TD>
													</TR>
													<TR>
														<TD class="plainlabel"><%=ChuyenNgu.get("Phân loại", nnId)%></TD>

														<TD class="plainlabel"><select name="phanloai"
															onChange="submitform();">
																<option value=""></option>
																<%
																	if (obj.getPhanloai().equals("1")) {
																%>
																<option value="1" selected><%=ChuyenNgu.get("Nhà phân phối", nnId)%></option>
																<option value="2"><%=ChuyenNgu.get("Trung tâm", nnId)%></option>
																<%
																	} else if (obj.getPhanloai().equals("2")) {
																%>
																<option value="2" selected><%=ChuyenNgu.get("Trung tâm", nnId)%></option>
																<option value="1"><%=ChuyenNgu.get("Nhà phân phối", nnId)%></option>
																<%
																	} else {
																%>
																<option value="2"><%=ChuyenNgu.get("Trung tâm", nnId)%></option>
																<option value="1"><%=ChuyenNgu.get("Nhà phân phối", nnId)%></option>
																<%
																	}
																%>

														</select></TD>
													</TR>
													<TR>
														<TD class="plainlabel"><%=ChuyenNgu.get("Từ ngày", nnId)%>
														</TD>
														<TD class="plainlabel" colspan="3">
															<TABLE cellpadding="0" cellspacing="0">
																<TR>
																	<TD><input class="days" type="text" name="tungay"
																		value='<%=obj.getTungay()%>' size="20"
																		onchange=submitform();></TD>

																</TR>
															</TABLE>

														</TD>
													</TR>
													<TR>
														<TD class="plainlabel"><%=ChuyenNgu.get("Đến ngày", nnId)%>
														</TD>
														<TD class="plainlabel" colspan="3">
															<TABLE cellpadding="0" cellspacing="0">
																<TR>
																	<TD><input class="days" type="text" name="denngay"
																		value='<%=obj.getDenngay()%>' size="20"
																		onchange=submitform();></TD>


																</TR>
															</TABLE>
													</TR>
													<TR>
														<TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái", nnId)%></TD>

														<TD class="plainlabel"><select name="trangthai"
															onChange="submitform();">
																<option value=""></option>
																<%
																	if (obj.getTrangthai().equals("1")) {
																%>
																<option value="1" selected><%=ChuyenNgu.get("Hoạt động", nnId)%></option>
																<option value="0"><%=ChuyenNgu.get("Kết thúc", nnId)%></option>
																<%
																	} else if (obj.getTrangthai().equals("0")) {
																%>
																<option value="0" selected><%=ChuyenNgu.get("Kết thúc", nnId)%></option>
																<option value="1"><%=ChuyenNgu.get("Hoạt động", nnId)%></option>
																<%
																	} else {
																%>
																<option value="1"><%=ChuyenNgu.get("Hoạt động", nnId)%></option>
																<option value="0"><%=ChuyenNgu.get("Kết thúc", nnId)%></option>

																<%
																	}
																%>

														</select></TD>
													</TR>


													<TR>
														<TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Username/Email", nnId)%>
														</TD>
														<TD width="81%" class="plainlabel"><INPUT
															name="username_email" type="text" value="<%=obj.getUsername_email()%>"
															onChange="submitform();">
														</TD>
													</TR>


													<TR>
														<TD class="plainlabel"><%=ChuyenNgu.get("Loại nhân viên", nnId)%></TD>

														<TD class="plainlabel"><select name="loainhanvien_fk"
															onChange="submitform();">
																<option value=""></option>
															<%
																if (loainhanvienRs != null) {
																		String _temp = obj.getLoainhanvien_fk();
																		try {
																			while (loainhanvienRs.next()) {
																				String _rs1 = loainhanvienRs.getString("loai");
																				String _rs2 = loainhanvienRs.getString("ten");
																				if (_temp != null && _rs1.equals(_temp)) {
															%>
																				<option value="<%=_rs1%>" selected><%=_rs2%></option>
																					<%
																						} else {
																					%>
																				<option value="<%=_rs1%>"><%=_rs2%></option>
																				<%
																					}
																								}
																							} catch (Exception e) {
																								e.printStackTrace();
																							}
																						}
																				%>
														</select></TD>
													</TR>

													<TR>
														<TD class="plainlabel" colspan=4><a class="button2"
															href="javascript:clearform()"> <img
																style="top: -4px;" src="../images/button.png" alt=""
																onClick="clearform();"><%=ChuyenNgu.get("Nhập lại", nnId)%></a>&nbsp;&nbsp;&nbsp;&nbsp;
															<a class="button2" href="javascript:xuatExcel()"> <img
																style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất Excel", nnId)%>
														</a></TD>
													</TR>

												</TABLE>
											</FIELDSET></TD>
									</TR>
								</TABLE>
							</TD>
						</TR>

						<TR>
							<TD align="left">
								<FIELDSET>
									<LEGEND class="legendtitle">
										&nbsp;<%=ChuyenNgu.get("Nhân viên", nnId)%>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<%
											if (quyen[Utility.THEM] != 0) {
										%>
										<a class="button3" onClick="newform();"> <img
											style="top: -4px;" src="../images/New.png"><%=ChuyenNgu.get("Tạo mới", nnId)%>
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
														<TH width="20%"><%=ChuyenNgu.get("Họ tên", nnId)%></TH>
														<TH width="5%"><%=ChuyenNgu.get("Đăng nhập", nnId)%></TH>
														<TH width="10%"><%=ChuyenNgu.get("Email", nnId)%></TH>
														<TH width="10%"><%=ChuyenNgu.get("Điện thoại", nnId)%></TH>
														<TH width="10%"><%=ChuyenNgu.get("Trạng thái", nnId)%></TH>
														<TH width="10%"><%=ChuyenNgu.get("Phân loại", nnId)%></TH>
														<TH width="8%"><%=ChuyenNgu.get("Người tạo", nnId)%></TH>
														<TH width="8%"><%=ChuyenNgu.get("Người sửa", nnId)%></TH>
														<TH width="8%"><%=ChuyenNgu.get("Ngày tạo", nnId)%></TH>
														<TH width="8%"><%=ChuyenNgu.get("Ngày sửa", nnId)%></TH>
														<TH width="8%"><%=ChuyenNgu.get("Tác vụ", nnId)%></TH>
													</TR>
													<%
														String lightrow = "tblightrow";
															String darkrow = "tbdarkrow";
															int i = 0;
															if (DSNV != null) {
																while (DSNV.next()) {
																	if (i % 2 != 0) {
													%>
													<TR align="center" align="left" class=<%=lightrow%>>
														<%
															} else {
														%>													
													<TR align="center" align="left" class=<%=darkrow%>>
														<%
															}
														%>
														<TD width="20%" align="left"><%=DSNV.getString("Ten")%></TD>
														<TD width="5%"><%=DSNV.getString("dangnhap")%></TD>
														<TD width="10%"><%=DSNV.getString("email")%></TD>
														<TD width="10%"><%=DSNV.getString("dienthoai")%></TD>
														<%
															if (DSNV.getString("trangthai").equals("1")) {
														%>
														<TD width="10%"><%=ChuyenNgu.get("Hoạt động", nnId)%></TD>
														<%
															} else {
														%>
														<TD width="10%"><%=ChuyenNgu.get("Ngưng hoạt động", nnId)%></TD>
														<%
															}
														%>
														<%
															if (DSNV.getString("phanloai").equals("1")) {
														%>
														<TD width="10%"><%=ChuyenNgu.get("Nhà phân phối", nnId)%></TD>
														<%
															} else {
														%>
														<TD width="10%"><%=ChuyenNgu.get("Trung tâm", nnId)%></TD>
														<%
															}
														%>
														<TD width="6%"><%=DSNV.getString("nguoitao1")%></TD>
														<TD width="6%"><%=DSNV.getString("nguoisua1")%></TD>
														<TD width="6%"><%=DSNV.getString("ngaytao")%></TD>
														<TD width="6%"><%=DSNV.getString("ngaysua")%></TD>
														<TD align="center">
															<TABLE border=0 cellpadding="0" cellspacing="0">
																<TR>
																	<TD>
																		<%
																			if (quyen[Utility.SUA] != 0) {
																		%> 
																		<A href="../../CapnhatnhanviennewSvl?userId=<%=userId%>&update=<%=DSNV.getString("pk_seq")%>">
																			<img
																			src="../images/Edit20.png" alt="Cap nhat"
																			title="Cập nhật" width="20" height="20"
																			longdesc="Cap nhat" border=0>
																		</A> 
																		<%
 																			}
 																		%>
																	</TD>
																	<TD>
																		<%
																			if (quyen[Utility.XEM] != 0) {
																		%> 
																		<A href="../../CapnhatnhanviennewSvl?userId=<%=userId%>&display=<%=DSNV.getString("pk_seq")%>">
																			<img
																			src="../images/Display20.png" alt="Cap nhat"
																			title="Cập nhật" width="20" height="20"
																			longdesc="Cap nhat" border=0>
																		</A> 
																		<%
 																			}
 																		%>
																	</TD>
																	<TD>&nbsp;</TD>
																	  <TD>
		                                                 			   <%
		                                                 			   	if (quyen[Utility.XOA] != 0) {
		                                                 			   %>
		                                                        		<A href="javascript:Xoa('<%=DSNV.getString("pk_seq")%>');">
		                                                        		<img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 
		                                                        		onclick="if(!confirm('Bạn có muốn xoá nhân viên này?')) return false;"></A>
	                                                    				<%
	                                                    					}
	                                                    				%>
                                                  					  </TD>
																</TR>
															</TABLE>
														</TD>
													</TR>
													<%
														i++;
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
</body>
<script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<%
	try {
			if (DSNV != null) {
				DSNV.close();
				DSNV = null;
			}
			obj.DbClose();
			session.setAttribute("obj", null);

		} catch (Exception er) {
			obj.DbClose();
			er.printStackTrace();
		}

	}
%>