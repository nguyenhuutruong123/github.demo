<%@page import="geso.dms.center.beans.phieuchuyenkhott.imp.PhieuChuyenKhoTT"%>
<%@page import="geso.dms.center.beans.phieuchuyenkhott.IPhieuChuyenKhoTT"%>
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
	  String khochuyen=(String)session.getAttribute("khochuyen");
	  String khonhan=(String)session.getAttribute("khonhan");
	  String dvkdid=(String)session.getAttribute("dvkdid");
	  String  sanphamid=(String)session.getAttribute("sanphamid");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
ResultSet rs_dvkd=(ResultSet)session.getAttribute("rs_dvkd");
ResultSet rs_sanpham=(ResultSet)session.getAttribute("rs_sanpham");
  ResultSet rs_khott=(ResultSet)session.getAttribute("rs_khott");
  ResultSet rs_khott_nhan=(ResultSet)session.getAttribute("rs_khott_nhan");
 IPhieuChuyenKhoTT obj = (IPhieuChuyenKhoTT)session.getAttribute("obj"); %>
<% List<IPhieuChuyenKhoTT> kholist = obj.getListChuyenKho(); %>
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
	document.khoForm.khonhan.value = "0"; 
    document.khoForm.khochuyen.value = "0"; 
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

<form name="khoForm" method="post" action="../../PhieuChuyenKhoTTSvl" charset="UTF-8">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
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
							   		&nbsp;Quản lý tồn kho> Kho trung tâm >Phiếu chuyển kho </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn &nbsp;<%=userTen %> &nbsp;</td> 
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
									  	<TD class="plainlabel" width="19%">Kho Chuyển</TD>
									  	<TD class="plainlabel" >
									  	<select name="khochuyen">
									  	<option value="0" > </option>
									  	<% if(rs_khott!=null){
									  		try{
									  			while(rs_khott.next()){
									  				if(rs_khott.getString("pk_seq").equals(khochuyen)){
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
									  			System.out.println("Error PhieuChuyenKhoTT  "+er.toString());
									  		}
									  	}
									  		%>	  
									  	</select>
									  
									  	</TD>
								</TR>
								<TR>
									  	<TD class="plainlabel" width="19%">Kho Nhận </TD>
									  	<TD class="plainlabel" >
									  	<select name="khonhan">
									  	<option value="0" > </option>
									  	<% if(rs_khott_nhan!=null){
									  		try{
									  			while(rs_khott_nhan.next()){
									  				if(rs_khott_nhan.getString("pk_seq").equals(khonhan)){
									  					%>
									  					<option value="<%= rs_khott_nhan.getString("pk_seq")%>" selected="selected"> <%= rs_khott_nhan.getString("ten") %> </option>
									  					<%
									  				}else{
									  					%>
									  					<option value="<%= rs_khott_nhan.getString("pk_seq")%>"> <%= rs_khott_nhan.getString("ten") %> </option>
									  					<%
									  				}
									  			
									  			}
									  		}catch(Exception er){
									  			System.out.println("Error PhieuChuyenKhoTT  "+er.toString());
;									  		}
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
								<TD class="plainlabel">
										Chọn đơn vị kinh doanh 
									</TD>
									<TD class="plainlabel">
									<select name="dvkdid">
									  	<option value="0" > </option>
									  	<% if(rs_dvkd!=null){
									  		try{
									  			while(rs_dvkd.next()){
									  				if(rs_dvkd.getString("pk_seq").trim().equals(dvkdid)){
									  					%>
									  					<option value="<%= rs_dvkd.getString("pk_seq")%>" selected="selected"> <%= rs_dvkd.getString("ten") %> </option>
									  					<%
									  					}else{
									  					%>
									  					<option value="<%= rs_dvkd.getString("pk_seq")%>"> <%= rs_dvkd.getString("ten") %> </option>
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
								<TR >
								<TD class="plainlabel" >
										Chọn sản phẩm 
									</TD>
									<TD class="plainlabel">
										<select name="sanphamid">
									  	<option value="0" > </option>
									  	<% if(rs_dvkd!=null){
									  		try{
									  			while(rs_sanpham.next()){
									  				if(rs_sanpham.getString("pk_seq").trim().equals(sanphamid)){
									  					%>
									  					<option value="<%= rs_sanpham.getString("pk_seq")%>" selected="selected"> <%=rs_sanpham.getString("ma") +" - "+  rs_sanpham.getString("ten") %> </option>
									  					<%
									  				}else{
									  					%>
									  					<option value="<%= rs_sanpham.getString("pk_seq")%>"> <%= rs_sanpham.getString("ma") +" -  "+  rs_sanpham.getString("ten") %> </option>
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
									<TD class="plainlabel">Trạng thái</TD>
											
									<TD class="plainlabel"><select name="trangthai" onChange="submitform();">
											<option value="" selected> </option>
										<% if (obj.getTrangthai().equals("1")){ %>
										  	<option value="1" selected >Đã chốt</option>
										  	<option value="0">Đang chờ xử lý </option>
										   <%}else if(obj.getTrangthai().equals("0")){ %>
										  		<option value="1"  >Đã chốt</option>
										  		<option value="0" selected>Đang chờ xử lý</option>
										 	<%}else{%>
										 	<option value="1"  >Đã chốt</option>
										  		<option value="0" >Đang chờ xử lý</option>
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
							<LEGEND class="legendtitle">&nbsp;Danh sách phiếu chuyển kho  &nbsp;&nbsp;
								<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>   	
						
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="10%">Số Phiếu chuyển</TH>
												<TH > Ngày chuyển </TH>
												<TH width="15%">Tên kho chuyển </TH>
												<TH width="15%">Tên kho nhận </TH>
												<TH width="10%">Trạng thái </TH>
												<TH width="10%">Ngày tạo</TH>
												<TH width="10%">Người tạo </TH>
												<TH width="10%">Ngày sửa</TH>
												<TH width="10%">Người sửa</TH>
												<TH width="10%">Tác vụ</TH>
											</TR>
								<% 
								IPhieuChuyenKhoTT pnkho = null;
									int size = kholist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										pnkho = (IPhieuChuyenKhoTT)kholist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
											<TD align="left"><%=pnkho.getId()%></TD> 
											<TD><%=pnkho.getNgayChuyen() %></TD>                                  
											<TD><%=pnkho.getTenKhoChuyen() %></TD>
											<TD><%=pnkho.getTenKhoNhan() %></TD>
											<%if (pnkho.getTrangthai().equals("0")) {%>
												<TD align="center">Đang chờ xử lý </TD>
											<%}else { %>
												<TD align="center" style="color:red;font-weight: bold;">Đã chốt </TD>
											<%} %>
												
												<TD align="center"><%=pnkho.getNgaytao() %></TD>
												<TD align="center"><%=pnkho.getNguoitao() %></TD>
												<TD align="center"><%=pnkho.getNgaysua() %></TD>												
												<TD align="center"><%=pnkho.getNguoisua() %></TD>
												<TD align="center">
													<TABLE border = 0 cellpadding="0" cellspacing="0">										
												<TR>
												<% if((pnkho.getTrangthai().equals("0"))) {%>
														<TD>
														<A href = "../../PhieuChuyenKhoTTNewSvl?userId=<%=userId%>&update=<%=pnkho.getId()%>" ><img src="../images/Edit20.png" title="Chỉnh sửa" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>
														</TD>
														<TD>
														<A href = "../../PhieuChuyenKhoTTSvl?userId=<%=userId%>&delete=<%=pnkho.getId()%>"><img src="../images/Delete20.png" title="Xóa" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa đơn hàng này?')) return false;"> </A>
														</TD>
														<TD >
                                                       	 <A href = "../../PhieuChuyenKhoTTNewSvl?userId=<%=userId%>&chot=<%=pnkho.getId()%>"><img src="../images/Chot.png" alt="Chốt " title="Chốt phiếu" width="30" height="25" longdesc="Chốt " border = 0></A>
                                                        </TD>
                                                       <%}else{ %>
                                                       <TD >
                                                       	 <A href = "../../PhieuChuyenKhoTTNewSvl?userId=<%=userId%>&display=<%=pnkho.getId()%>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
                                                        </TD>
                                                       <%} %> 
														</TR>
														
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