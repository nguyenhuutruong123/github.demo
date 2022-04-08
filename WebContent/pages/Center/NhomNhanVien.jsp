<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="geso.dms.center.beans.nhomnhanvien.INhomNhanVien"%>
<%@ page import="geso.dms.center.beans.nhomnhanvien.INhomNhanVienList"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum))
	{
		response.sendRedirect("/OPV/index.jsp");
	} else
	{
%>


<%
	INhomNhanVienList obj = (INhomNhanVienList) session.getAttribute("obj");
%>
<%
	ResultSet npplist = (ResultSet) obj.getNhomRs();
%>

<%
	obj.setNextSplittings();
	int[] quyen = new  int[6];
	quyen = util.Getquyen("NhomNhanVienSvl","",userId);
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("NhomNhanVienSvl","",nnId);	
	%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>OPV - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<style type="text/css">
#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}

th {
	cursor: pointer;
}
</style>

<SCRIPT language="javascript" type="text/javascript">
	function clearform() 
	{
		document.nppForm.nppTen.value = "";
		document.nppForm.DienThoai.value = "";
		document.nppForm.kvId.selectedIndex = 0;
		document.nppForm.TrangThai.selectedIndex = 2;
		submitform();
	}

	function submitform() {
		document.forms['nppForm'].action.value = 'search';
		document.forms['nppForm'].submit();
	}
	function xuatexcel() {
		document.forms['nppForm'].action.value = 'excel';
		document.forms['nppForm'].submit();
	}
	function newform() {
		document.forms['nppForm'].action.value = 'new';
		document.forms['nppForm'].submit();
	}
	function thongbao() {
		var tt = document.forms['nppForm'].msg.value;
		if (tt.length > 0)
			alert(document.forms['nppForm'].msg.value);
	}


	function xuatExcel()
	{
		document.forms['nppForm'].action.value= 'excel';
	 	document.forms['nppForm'].submit();
	 	document.forms['nppForm'].action.value= '';
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

	<form name="nppForm" method="post" action="../../NhomNhanVienSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="userTen" value='<%=userTen%>'> 
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="crrApprSplitting" value="<%=obj.getCrrApprSplitting()%>"> 
		<input type="hidden" name="nxtApprSplitting" value="<%=obj.getNxtApprSplitting()%>">
		<input type="hidden" name="msg" value='<%=obj.getMsg()%>'>
<script language="javascript" type="text/javascript">
		thongbao();
</script>

		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">

			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<!--begin body Dossier--> <!--begin common dossier info---> <!--End common dossier info--->
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation"><%= " " + url %>  </TD>

										<TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng bạn",nnId) %> <%=userTen%>&nbsp;
										</TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD>
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="center">
											<FIELDSET>
												<!-- <LEGEND class="legendtitle">Tiêu chí tìm kiếm &nbsp;</LEGEND> -->

												<TABLE width="100%" cellpadding="6" cellspacing="0">
													<%-- <tr>
														<TD width="22%" class="plainlabel">Mã nhóm</TD>
														<TD colspan="3" class="plainlabel">
														<INPUT name="ma" type="text" value="<%=obj.getTen()%>" onChange="submitform();"></TD>
													</TR>--%>

													<tr>
														<TD width="22%" class="plainlabel"><%=ChuyenNgu.get("Tên nhóm",nnId) %></TD>
														<TD colspan="3" class="plainlabel">
															<INPUT name="ten" type="text" value="<%=obj.getTen()%>" onChange="submitform();">
														</TD>
													</TR> 
													<TR>
														<TD colspan="4" class="plainlabel">
														<!-- 	<a class="button2" href="javascript:clearform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
															<!-- <a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a> -->
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
					<TABLE width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle">
										&nbsp;<%=ChuyenNgu.get("Nhóm nhân viên",nnId) %>
										<%if(quyen[Utility.THEM]!=0) {%>
												{
										<a class="button3" href="javascript:newform()"> <img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %>
										</a>
										<%
											}
										%>
									</LEGEND>
									<TABLE class="" width="100%">
										<TR>
											<TD width="100%">
												<TABLE width="100%" id="table" class="tabledetail sortable" border="0" cellspacing="1" cellpadding="4">
													<thead>
														<TR class="tbheader">
															<TH width="4%"><%=ChuyenNgu.get("STT",nnId) %></TH>
												
															<TH width="13%"><%=ChuyenNgu.get("Tên nhóm",nnId) %></TH>
															<TH width="9%"><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
															<TH width="12%"><%=ChuyenNgu.get("Người tạo",nnId) %></TH>
															<TH width="9%"><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
															<TH width="11%"><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
															<TH class="nosort" width="8%" align="center">&nbsp;<%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
														</TR>
													</thead>
													<tbody>

														<%
														int m = 0;
														String lightrow = "tblightrow";
														String darkrow = "tbdarkrow";
														if (npplist != null)
															while (npplist.next())
															{
																if (m % 2 != 0)
																{
														%>
														<TR class=<%=lightrow%>>
															<%
																} else
																			{
															%>
														
														<TR class=<%=darkrow%>>
															<%
																}
															%>
															<TD align="center"><%=m+1 %></TD>
										
															<TD align="left"><div align="left"><%=npplist.getString("ten")%></div></TD>
															<TD align="center"><%=npplist.getString("ngaytao")%></TD>
															<TD align="center"><%=npplist.getString("nguoitao")%></TD>
															<TD align="center"><%=npplist.getString("ngaysua")%></TD>
															<TD align="center"><%=npplist.getString("nguoisua")%></TD>
															<TD align="center">
																<TABLE border=0 cellpadding="0" cellspacing="2">
																	<TR>
																	   <TD>
																			<%if(quyen[Utility.XEM]!=0) {%>
																			 <A href="../../NhomNhanVienUpdateSvl?userId=<%=userId%>&display=<%=npplist.getString("id")%>"><img src="../images/Display20.png" alt="Xem" width="20" height="20" longdesc="Xem" border=0></A> <%
 	}
 %>
																		</TD>
																		<TD>
																			<%if(quyen[Utility.SUA]!=0) {%>
																			 <A href="../../NhomNhanVienUpdateSvl?userId=<%=userId%>&update=<%=npplist.getString("id")%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border=0></A> <%
 	}
 %>
																		</TD>
																		<TD>
																			<%
																			if(quyen[Utility.XOA]!=0)
																							{
																			%> <A href="../../NhomNhanVienSvl?userId=<%=userId%>&delete=<%=npplist.getString("id")%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
																				onclick="if(!confirm('Bạn muốn xóa nhóm hàng này?')) return false;"></A>
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
													</tbody>
													<tfoot>
														
													</tfoot>

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
	<script type="text/javascript" src="../scripts/script-table-sorter.js"></script>
	<script type="text/javascript">
		var sorter = new TINY.table.sorter("sorter");
		sorter.head = "HEAD";
		//sorter.asc = "asc";
		sorter.desc = "desc";
		sorter.even = "tblightrow";
		sorter.odd = "tbdarkrow";
		sorter.evensel = "evenselected";
		sorter.oddsel = "oddselected";
		sorter.paginate = true;
		sorter.currentid = "currentpage";
		sorter.limitid = "pagelimit";
		sorter.init("table",3);
	</script>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
	}
	
%>