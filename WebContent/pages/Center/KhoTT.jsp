<%@page import="geso.dms.center.beans.khott.IKhoTT"%>
<%@page import="geso.dms.center.beans.khott.imp.KhoTT"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.kho.IKho" %>
<%@ page  import = "geso.dms.center.beans.kho.IKhoList" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	  String tungay=(String)session.getAttribute("tungay");
	  String denngay=(String)session.getAttribute("denngay");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
 
<% KhoTT obj = (KhoTT)session.getAttribute("obj"); %>
<% List<KhoTT> kholist = (List<KhoTT>)obj.getListKho(); %>
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
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
    document.khoForm.ten.value = "";   
	document.khoForm.tungay.value = "";
	document.khoForm.denngay.value = "";       
    document.khoForm.trangthai.selectedIndex = 0;
    submitform();
}

function submitform()
{
	document.forms['khoForm'].action.value= 'search';
	document.forms['khoForm'].submit();
}

function newform()
{
	document.forms['khoForm'].action.value= 'new';
	document.forms['khoForm'].submit();
}
function thongbao()
{var tt = document.forms['khoForm'].msg.value;
	if(tt.length>0)
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

<form name="khoForm" method="post" action="../../KhoTTSvl" charset="UTF-8">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMessage() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		&nbsp;Thiết lập dữ liệu nền > Dữ liệu nền kinh doanh > Khai báo kho </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td> 
						    </tr>
   
						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="100%" align="left"><FIELDSET>
						<LEGEND class="legendtitle">Tiêu chí tìm kiếm </LEGEND>

							<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
								<TR>
									  	<TD class="plainlabel" width="19%">Tên kho</TD>
									  	<TD class="plainlabel" ><INPUT name="ten" size="20" type="text" value='<%= obj.getTen() %>' onChange="submitform();"></TD>
								</TR>
								<TR>
										<TD class="plainlabel" >Từ ngày </TD>
									 	<TD class="plainlabel" >
											<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<input type="text" name="tungay" size="20" value='<%=tungay %>' onFocus="submitform();">
												</TD>
												<TD>
<a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'khoForm.tungay',false, 'yyyy-mm-dd'); return false;">
		 &nbsp; <img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>
		  
		   										</TD>
                                    		</TR>
											</TABLE>
										</TD>
								</TR>
								<TR>
                                    <TD class="plainlabel" >Đến ngày </TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  				<TR>
							  					<TD>
													<input type="text" name="denngay" size="20" value='<%=denngay %>' onFocus="submitform();">
												</TD>
												<TD>
											<a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'khoForm.denngay',false, 'yyyy-mm-dd'); return false;">
											 &nbsp; <img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>

        		                                </TD>
                	                     	</TR>
										</TABLE>
									</TD>

								</TR>
								<TR>
									<TD class="plainlabel">Trạng thái</TD>
											
									<TD class="plainlabel"><select name="trangthai" onChange="submitform();">
											<option value="2" selected> </option>
										<% if (obj.getTrangthai().equals("1")){ %>
										  	<option value="1" selected >Hoạt động</option>
										  	<option value="0">Ngưng hoạt động</option>
										 <%}else{ %>
											<% if (obj.getTrangthai().equals("0")){ %>
										  		<option value="1"  >Hoạt động</option>
										  		<option value="0" selected>Ngưng hoạt động</option>
										 	<%}else{ %>
												<option value="1"  >Hoạt động</option>
										  		<option value="0"  >Ngưng hoạt động</option>
										 	<%}}%>										 
										    </select>
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel" colspan="2">
									<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;		
                                  
									</TD>
								</TR>
							</TABLE>
							</FIELDSET>
						</TD>	
					</TR>
				</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">		
			    <TR>
					<TD align="left" >
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Danh sách kho &nbsp;&nbsp;
								<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>   	
						
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="10%">Tên kho </TH>
												<TH width="20%">Diễn giải </TH>
												<TH width="12%">Trạng thái </TH>
												<TH width="9%">Ngày tạo</TH>
												<TH width="15%">Người tạo </TH>
												<TH width="9%">Ngày sửa</TH>
												<TH width="14%">Người sửa</TH>
												<TH width="11%">Tác vụ</TH>
											</TR>
								<% 
									KhoTT kho = null;
									int size = kholist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										kho = (KhoTT) kholist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
											<TD align="left"><%=kho.getTen() %></TD>                                   
											<TD><%=kho.getDiengiai() %></TD>
											
											<%if (kho.getTrangthai().equals("1")) {%>
												<TD align="center">Hoạt động </TD>
											<%}else{ %>
												<TD align="center">Ngưng hoạt động </TD>
											<%} %>
												
												<TD align="center"><%=kho.getNgaytao() %></TD>
												<TD align="center"><%=kho.getNguoitao() %></TD>
												<TD align="center"><%=kho.getNgaysua() %></TD>												
												<TD align="center"><%=kho.getNguoisua() %></TD>
												<TD align="center">
													<TABLE border = 0 cellpadding="0" cellspacing="0">
														<TR><TD>
														<A href = "../../KhoTTSvl?userId=<%=userId%>&update=<%=kho.getId()%>" ><img src="../images/Edit20.png" alt="Chinh sua" title="Chỉnh sửa" width="20" height="20" longdesc="Chinh sua" border = 0></A>
														</TD>
														<TD>&nbsp;</TD>
														<TD>
														<A href = "../../KhoTTSvl?userId=<%=userId%>&delete=<%=kho.getId()%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Ban Co Muon Xoa Kho Nay?')) return false;"> </A></TD>
														</TR>
													</TABLE>												</TD>
												</TR>
										<% 	m++; }%>
								
										
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
<%}%>