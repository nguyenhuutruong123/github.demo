<%@page import="geso.dms.center.beans.hoadon.IHoaDon"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT"%>
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
	  String khottid=(String)session.getAttribute("khottid");
	  String nhappid=(String)session.getAttribute("nppid");

	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
 	 ResultSet rs_khott=(ResultSet)session.getAttribute("rs_khott");
		ResultSet rs_nhapp=(ResultSet)session.getAttribute("rs_nhapp");

 IHoaDon obj = (IHoaDon)session.getAttribute("obj"); %>
<% List<IHoaDon> listhoadon = obj.getListHoaDon(); %>
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
    document.khoForm.khott.value = "0";   
    document.khoForm.dvkdid.value = "0";
    document.khoForm.sanphamid.value = "0";
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

<form name="khoForm" method="post" action="../../HoaDonSvl" charset="UTF-8">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId%>' size="30">
<input type="hidden" name="action" value='1'>
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
							   		&nbsp;Quản lý kho> Quản lý kho trung tam >Phiếu nhập kho </TD>
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
								<TD class="plainlabel">
										Chọn nhà phân phối :
									</TD>
									<TD class="plainlabel">
									<select name="dvkdid">
									  	<option value="0" > </option>
									  	<% if(rs_nhapp!=null){
									  		try{
									  			while(rs_nhapp.next()){
									  				if(rs_nhapp.getString("pk_seq").trim().equals(nhappid)){
									  					%>
									  					<option value="<%= rs_nhapp.getString("pk_seq")%>" selected="selected"> <%= rs_nhapp.getString("ten") %> </option>
									  					<%
									  					}else{
									  					%>
									  					<option value="<%= rs_nhapp.getString("pk_seq")%>"> <%= rs_nhapp.getString("ten") %> </option>
									  					<%
									  				}
									  			
									  			}
									  		}catch(Exception er){
									  		}
									  	}
									  		%>	  
									  	</select>	
									</TD>
								</TR>
								<TR>
									  	<TD class="plainlabel" width="19%">Chọn kho trung tâm</TD>
									  	<TD class="plainlabel" >
									  	<select name="khott">
									  	<option value="0" > </option>
									  	<% if(rs_khott!=null){
									  		try{
									  			while(rs_khott.next()){
									  				if(rs_khott.getString("pk_seq").equals(khottid)){
									  					%>
									  					<option value="<%= rs_khott.getString("pk_seq")%>" selected="selected"> <%= rs_khott.getString("ten") %> </option>
									  					<%
									  				}else{
									  					%>
									  					<option value="<%= rs_khott.getString("pk_seq")%>"> <%= rs_khott.getString("ten") %> </option>
									  					<%
									  				}
									  			
									  			}
									  		}catch(Exception er){
									  		}
									  	}
									  		%>	  
									  	</select>
									  
									  	</TD>
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
											<option value="" selected> </option>
										<% if (obj.getTrangthai().trim().equals("1")){ %>
										  	<option value="1" selected >Đã chốt</option>
										  	<option value="0">Đang chờ xử lý </option>
										  	<option value="3">Đã chuyển kho </option>
										   <%}else if(obj.getTrangthai().trim().equals("0")){ %>
										  		<option value="1"  >Đã chốt</option>
										  		<option value="0" selected>Đang chờ xử lý</option>
										  		<option value="3">Đã chuyển kho </option>
										 	<%}else if(obj.getTrangthai().trim().equals("3")){%>
										 		<option value="1"  >Đã chốt</option>
										  		<option value="0" >Đang chờ xử lý</option>
										  		<option value="3" selected>Đã chuyển kho </option>
										 	<%} else{%>
										 	   <option value="1"  >Đã chốt</option>
										  		<option value="0" >Đang chờ xử lý</option>
										  		<option value="3">Đã chuyển kho </option>
										 	
										 	<%} %>										 
										    </select>
									</TD>		
								</TR>
								<TR>
									<TD class="plainlabel" colspan="2">
								   <a class="button3" href="javascript:submitform()">
                           	<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>   &nbsp;&nbsp;&nbsp;
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
							<LEGEND class="legendtitle">&nbsp;Danh sách phiếu nhập kho trung tâm &nbsp;&nbsp;
								<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>   	
						
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="10%">Số ĐH</TH>
												<TH > Ngày nhập </TH>
												<TH width="11%">ID Đơn đặt hàng</TH>
												<TH width="20%">Tên kho TT </TH>		
												<TH width="">Nhà phân phối</TH>
												<TH width="">Nhà cung cấp</TH>
												<TH width="11%">Tổng tiền sau VAT</TH>
												<TH width="11%">Tiền trả KM</TH>
												<TH width="12%">Trạng thái </TH>
												<TH width="9%">Ngày tạo</TH>
												<TH width="15%">Người tạo </TH>
												<TH width="9%">Ngày sửa</TH>
												<TH width="14%">Người sửa</TH>
												<TH width="14%">Tác vụ</TH>
											</TR>
								<% 
								IHoaDon pnkho = null;
									int size = listhoadon.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										pnkho = (IHoaDon)listhoadon.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
											<TD align="left"><%=pnkho.getId()%></TD> 
											<TD><%=pnkho.getNgaygiaodich() %></TD>    
												<TD><%=pnkho.getIdDonDatHang()%></TD>                              
											<TD><%=pnkho.getKhottTen() %></TD>
											<TD><%=pnkho.getNppTen() %></TD>
											<TD><%=pnkho.getTenNhaCungCap() %></TD>
											<TD><%=pnkho.getTongtiensauVAT() %></TD>
											<TD><%=pnkho.getSoTienTraKM() %></TD>
											
											<%if (pnkho.getTrangthai().equals("0")) {%>
												<TD align="center">Đang chờ xử lý </TD>
											<%}else if(pnkho.getTrangthai().equals("1")) { %>
												<TD align="center" style="color:blue;font-weight: bold;">Đã chốt </TD>
											<%}else if(pnkho.getTrangthai().equals("3")){ %>
											<TD align="center" style="color: red;font-weight: bold;" >Đã chuyển kho </TD>
											<%} %>
												
												<TD align="center"><%=pnkho.getNgaytao() %></TD>
												<TD align="center"><%=pnkho.getNguoitao() %></TD>
												<TD align="center"><%=pnkho.getNgaysua() %></TD>												
												<TD align="center"><%=pnkho.getNguoisua() %></TD>
												<TD align="center">
													<TABLE border = 0 cellpadding="0" cellspacing="0">
														<% if(pnkho.getTrangthai().equals("0")){ %>													
														<TR>
														<TD>
														<A href = "../../PhieuNhapKhoTTNewSvl?userId=<%=userId%>&update=<%=pnkho.getId()%>" ><img src="../images/Edit20.png" title="Chỉnh sửa" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>
														</TD>
														<TD>
														<A href = "../../PhieuNhapKhoTTSvl?userId=<%=userId%>&delete=<%=pnkho.getId()%>"><img src="../images/Delete20.png" title="Xóa" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa đơn hàng này?')) return false;"> </A>
														</TD>
														<TD>
                                                        <A href = "../../PhieuNhapKhoTTNewSvl?userId=<%=userId%>&chot=<%=pnkho.getId()%>"><img src="../images/Chot.png" alt="Chốt " title="Chốt đơn hàng" width="30" height="30" longdesc="Chốt" border = 0></A>
                                                        </TD>
														</TR>
														<%}else { %>
														<TR>
														<TD colspan="4">
                                                       	 <A href = "../../PhieuNhapKhoTTNewSvl?userId=<%=userId%>&display=<%=pnkho.getId()%>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
                                                        </TD>
														</TR>
														<%} %>
													</TABLE>												
													</TD>
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