<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="geso.dms.center.beans.nhomcttrungbay.INhomCTTrungBayList"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.center.util.*"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum))
	{
		response.sendRedirect("/SOHACO/index.jsp");
	} else
	{
	INhomCTTrungBayList obj = (INhomCTTrungBayList) session.getAttribute("obj");
	ResultSet npplist = (ResultSet) obj.getNhomRs();
	obj.setNextSplittings();
		int[] quyen = new int[6];
		quyen = util.Getquyen("NhomCTTrungBaySvl","", userId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>SOHACO - Project</TITLE>
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
		document.nppForm.ma.value = "";
		document.nppForm.ten.value = "";
		//document.nppForm.kvId.selectedIndex = 0;
		//document.nppForm.TrangThai.selectedIndex = 2;
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

	<form name="nppForm" method="post" action="../../NhomCTTrungBaySvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
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
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý trưng bày &gt; Khai báo &gt; Nhóm Chương trình trưng bày</TD>

										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
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
												<LEGEND class="legendtitle">Tiêu chí tìm kiếm &nbsp;</LEGEND>

												<TABLE width="100%" cellpadding="6" cellspacing="0">
													<tr>
														<TD width="22%" class="plainlabel">Mã nhóm</TD>
														<TD colspan="3" class="plainlabel">
														<INPUT name="ma" type="text" value="<%=obj.getMa()%>" onChange="submitform();"></TD>
													</TR>

													<tr>
														<TD width="22%" class="plainlabel">Tên nhóm</TD>
														<TD colspan="3" class="plainlabel">
															<INPUT name="ten" type="text" value="<%=obj.getTen()%>" onChange="submitform();">
														</TD>
													</TR>
													<TR>
														<TD colspan="4" class="plainlabel"><a class="button2" href="javascript:clearform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a></TD>
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
										&nbsp;Nhóm CT Trưng bày
										<%
											if (quyen[Utility.THEM] != 0)
												{
										%>
										<a class="button3" href="javascript:newform()"> <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới
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
															<TH width="4%">STT</TH>
															<TH width="13%">Mã nhóm</TH>
															<TH width="13%">Tên nhóm</TH>
															<TH width="9%">Ngày tạo</TH>
															<TH width="12%">Người tạo</TH>
															<TH width="9%">Ngày sửa</TH>
															<TH width="11%">Người sửa</TH>
															<TH class="nosort" width="8%" align="center">&nbsp;Tác vụ</TH>
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
															<TD align="left"><div align="left"><%=npplist.getString("ma")%></div></TD>
															<TD align="left"><div align="left"><%=npplist.getString("ten")%></div></TD>
															<TD align="center"><%=npplist.getString("ngaytao")%></TD>
															<TD align="center"><%=npplist.getString("nguoitao")%></TD>
															<TD align="center"><%=npplist.getString("ngaysua")%></TD>
															<TD align="center"><%=npplist.getString("nguoisua")%></TD>
															<TD align="center">
																<TABLE border=0 cellpadding="0" cellspacing="2">
																	<TR>
																		<TD>
																			<%
																				if (quyen[Utility.SUA] != 0)
																							{
																			%> <A href="../../NhomCTTrungBayUpdateSvl?userId=<%=userId%>&update=<%=npplist.getString("id")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border=0></A> <%
 	}
 %>
																		</TD>
																		<TD>
																			<%
																				if (quyen[Utility.XOA] != 0)
																							{
																			%> <A href="../../NhomCTTrungBaySvl?userId=<%=userId%>&delete=<%=npplist.getString("id")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0
																				onclick="if(!confirm('Bạn muốn xóa nhóm trưng bày này ?')) return false;"></A>
																				<%
																			}
																		%>
																		</TD>
																		<TD>
																			<%
																				if (quyen[Utility.XEM] != 0)
																		{%>
																			 <A href="../../NhomCTTrungBayUpdateSvl?userId=<%=userId%>&display=<%=npplist.getString("id")%>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border=0></A>
																		<%}%>
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
													<tr class="tbfooter" > 
                               			
                               			<TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('nhForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('nhForm', eval(nhForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('nhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('nhForm', eval(nhForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('nhForm', -1, 'view')"> &nbsp;
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