<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhomchitieu.INhomchitieu" %>
<%@ page  import = "geso.dms.center.beans.nhomchitieu.INhomchitieuList" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Bibica/index.jsp");
	}else{ %>

<% INhomchitieuList obj = (INhomchitieuList)session.getAttribute("obj"); %>
<% ResultSet Dsnkm = (ResultSet)obj.getDsnkm();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("NhomchitieuSvl","", userId);
	System.out.println(quyen[5]);
%>
<% obj.setNextSplittings(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">

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

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	 document.nkmForm.action.value = "new";
   	 document.forms["nkmForm"].submit();
}

function searchform()
{
	 document.nkmForm.action.value = "search";
   	 document.forms["nkmForm"].submit();
}

function clearform()
{       document.nkmForm.tungay.value="";
		document.nkmForm.denngay.value="";
	    document.nkmForm.tennhom.value="";
	    document.nkmForm.trangthai.value="";
	    document.nkmForm.action.value = "search";
	    document.forms["nkmForm"].submit();
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

<form name="nkmForm" method="post" action="../../NhomchitieuSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="1">
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
				   		<table width="100%" border="0" cellpadding="0" cellspacing="0">
					  		<tr height="22">
						  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý chỉ tiêu &gt; Chỉ tiêu NPP &gt; Nhóm chỉ tiêu </TD> 
						  		<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  	</tr>
						</table></TD>
			  	</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
									<TD width="19%" class="plainlabel">Tên nhóm </TD>
								    <TD width="81%" class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<INPUT name="tennhom" type="text" size="40" value='<%=obj.getDiengiai()%>' onChange ="searchform();">
											</TD></TR>
										</TABLE>								
								</TR>
								<TR>
									<TD class="plainlabel">Trạng thái </TD>
								  	<TD  class="plainlabel"><SELECT name = "trangthai"  onChange = "searchform();">
								  	
								  	<% if (obj.getTrangthai().equals("0")){ %>
								      	<option value=""> </option>
								    	<option value="1">Đã chốt</option>
								    	<option value="0" selected>Chưa chốt</option>
									<%}else if (obj.getTrangthai().equals("1")){%>		
									  	<option value=""> </option>			
								  		<option value="1" selected>Đã chốt</option>
								    	<option value="0" >Chưa chốt</option>
									<%} else {%>
									   <option value="" selected> </option>
								    	<option value="1">Đã chốt</option>
								    	<option value="0" >Chưa chốt</option>
                                        <%} %>
										</SELECT></TD>
									
								</TR>
								
										<TR>
											<TD class="plainlabel" >Từ ngày </TD>
											<TD class="plainlabel" colspan="3">
												<TABLE cellpadding="0" cellspacing="0">
													<TR><TD>
														<input  class="days" type="text" name="tungay" value='<%=obj.getTungay() %>'  size="20" onchange = "searchform();">
													</TD>
													
													</TR>
												</TABLE>																					
		  									</TD>
										</TR>
										<TR>
                                          <TD class="plainlabel" >Đến ngày </TD>
										  <TD class="plainlabel" colspan="3">
										  		<TABLE cellpadding="0" cellspacing="0"><TR><TD>
										 <input  class="days" type="text" name="denngay" value='<%=obj.getDenngay() %>' size="20" onchange = "searchform();">
										  		</TD>
										

											  </TR>
											 </TABLE>
									  </TR>
								<TR>
									<TD class="plainlabel">
									
                                 <a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	

                                    </TD>								
									<TD class="plainlabel">&nbsp;</TD>										
								</TR>
								
							</TABLE>
					  </FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			
			<TABLE width="100%" border = "0" cellpading = "0" cellspacing = "0">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Nhóm chỉ tiêu &nbsp;&nbsp;&nbsp;
					<%if(quyen[0]!= 0){ %>
						<a class="button3" href="javascript:submitform()">
	    				<img style="top: -4px;" src="../images/New.png" >Tạo mới </a>                            
					<%} %>
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="10%">Mã nhóm</TH>
									<TH width="20%">Tên nhóm</TH>
									
									<TH width="8%">Từ Ngày</TH>
									<TH width="13%">Đến Ngày </TH>
									<TH width="13%">Tính thưởng </TH>
									<TH width="9%">Ngày sửa</TH>
									<TH width="12%">Người sửa</TH>
									<TH width="9%">Trạng thái </TH>
									<TH width="9%">Tác vụ</TH>
								</TR>
						<% 
							INhomchitieu nkm = null;
							int m = 0;
							String star = "";
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							while (Dsnkm!=null&&Dsnkm.next()){
								//nkm = nkmlist.get(m);
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
								<%} else {%>
									<TR class= <%= darkrow%> >
								<%} %>										
															
									<TD align="center"><%=Dsnkm.getString("pk_seq") %></TD>
									<TD align="left"><%=Dsnkm.getString("ten") %></TD>
									<TD align="center"><%=Dsnkm.getString("TuNgay") %></TD>
									<TD align="center"><%=Dsnkm.getString("DenNgay") %></TD>
									<TD align="center"><%=Dsnkm.getString("TinhThuong") %></TD>
									<TD align="center"><%=Dsnkm.getString("ngaysua") %></TD>
									<TD align="center"><%=Dsnkm.getString("nguoisua") %> </TD>
									<% if(Dsnkm.getString("trangthai").equals("1")) {%>
										<TD align="center">Đã chốt</TD>
									<%}else {%>
										<TD align="center">Chưa chốt</TD>
									<%} %>
									<TD align="center">
									<% if(Dsnkm.getString("trangthai").equals("0")) { %>	
										<%if(quyen[2]!= 0){ %>
										<A href = "../../NhomchitieuUpdateSvl?userId=<%=userId%>&update=<%=Dsnkm.getString("pk_seq")%>">
											<img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;								
										<%} %>
										
										<%if(quyen[1]!= 0){ %>
										<A href = "../../NhomchitieuSvl?userId=<%=userId%>&delete=<%= Dsnkm.getString("pk_seq") %>">
											<img src="../images/Delete20.png" alt="Delete" title="Xóa" width="20" height="20" longdesc="Delete" border = 0></A>&nbsp;		
										<%} %>
										
										<%if(quyen[4]!= 0){ %>
										<A href = "../../NhomchitieuSvl?userId=<%=userId%>&chot=<%= Dsnkm.getString("pk_seq") %>">
											<img src="../images/Chot.png" alt="Chot" title="Chốt" width="20" height="20" longdesc="Chot" border = 0></A>
										<%} %>
										
									<%} else { %> 
										<%if(quyen[3]!= 0){ %>
										<A href = "../../NhomchitieuUpdateSvl?userId=<%=userId%>&display=<%=Dsnkm.getString("pk_seq")%>">
												<img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>&nbsp;
										<%} %>
										<%if(quyen[5]!= 0){ %>
										<A href = "../../NhomchitieuSvl?userId=<%=userId%>&bochot=<%= Dsnkm.getString("pk_seq") %>">
											<img src="../images/unChot.png" alt="Bo chot" title="Bỏ chốt" width="20" height="20" longdesc="Bo chot" border = 0></A>
										<%} %>
									<% } %>
									</TD>
								</TR>
							<%m++; }%>
							<tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('ddhForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="View('ddhForm', eval(ddhForm.nxtApprSplitting.value) -1, 'view')">
															&nbsp; <%}else{ %> <img alt="Trang Truoc"
															src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('ddhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View('ddhForm', eval(ddhForm.nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('ddhForm', -1, 'view')"> &nbsp; <%} %>
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
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<%
	if(Dsnkm!=null){ Dsnkm.close(); Dsnkm = null;}
	obj = null; session.setAttribute("obj", null);

}%>