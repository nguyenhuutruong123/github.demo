<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.thongtinsanpham.IThongtinsanphamList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%	 NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{  int[] quyen = new  int[6];	%>

<% IThongtinsanphamList obj = (IThongtinsanphamList)session.getAttribute("obj"); %>
<% ResultSet splist = (ResultSet)obj.getThongtinsanphamList(); %>
<% ResultSet dvkd = obj.getDvkd(); %>
<% ResultSet nh = obj.getNh(); %>
<% ResultSet cl = obj.getCl(); 
 String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
 } 
String url = util.getChuyenNguUrl("DThongtinsanphamSvl","",nnId); 
String view = obj.getView();
if (view != null && view.equals("TT")) {
	quyen = util.Getquyen("","&view=TT",userId);
}
else {
	quyen = util.Getquyen("DThongtinsanphamSvl","",userId);
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
function clearform()
{
	document.spForm.masp.value = "";
	document.spForm.tensp.value = "";     
    document.spForm.dvkdId.selectedIndex = 0;   
    document.spForm.nhId.selectedIndex = 0;
    document.spForm.clId.selectedIndex = 0;
    document.spForm.trangthai.selectedIndex = 0;
    submitform();
}

function submitform()
{
	document.forms['spForm'].action.value= 'search';
	document.forms['spForm'].submit();
}

function xuatExcel()
{
	document.forms['spForm'].action.value= 'excel';
	document.forms['spForm'].submit();
}

function newform()
{
	document.forms['spForm'].action.value= 'new';
	document.forms['spForm'].submit();
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

<form name="spForm" method="post" action="../../DThongtinsanphamSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=obj.getUserId()%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="view" value='<%=view%>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR>
				<TD align="left" class="tbnavigation">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR height="22">
							<TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; Dữ liệu nền sản phẩm &gt; Thông tin sản phẩm</TD>
					   		<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  &nbsp;&nbsp;<%=userTen %> &nbsp;</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR>
				<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="left">
							<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

							<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
								<TR>
									<TD width="19%" class="plainlabel">Mã sản phẩm</TD>
							        <TD class="plainlabel" colspan = 5><INPUT name="masp" type="text" size="30" value = '<%=obj.getMasp() %>' onChange = "submitform();"></TD>
								</TR>
								
								<TR>
									<TD width="19%" class="plainlabel">Tên sản phẩm</TD>
							        <TD class="plainlabel" colspan = 5><INPUT name="tensp" type="text" size="30" value = '<%=obj.getTensp() %>' onChange = "submitform();"></TD>
								</TR>
							
								<TR>
								  <TD class="plainlabel">Đơn vị kinh doanh</TD>
								  <TD class="plainlabel"><SELECT name="dvkdId" onChange = "submitform();">
								  <option value="" > </option>
								<%  try{
								  		while(dvkd.next()){								  			
								  			if (obj.getDvkdId().equals(dvkd.getString("dvkdId"))){ %>								  			
								  				<option value="<%= dvkd.getString("dvkdId")%>" selected><%= dvkd.getString("dvkd")%></option>
								  		  <%}else{ %>		
								  		  		<option value="<%= dvkd.getString("dvkdId")%>" ><%= dvkd.getString("dvkd")%></option>
								  	<%		}
								  		}
								  	}catch (java.sql.SQLException e){} %>
                                  </SELECT></TD>
							  	</TR>
								<TR>
								  <TD class="plainlabel">Nhãn hàng</TD>
								  <TD class="plainlabel"><SELECT name="nhId" onChange = "submitform();">
								  <option value="" > </option>
								<%  try{
								  		while(nh.next()){								  			
								  			if (obj.getNhId().equals(nh.getString("pk_seq"))){ %>								  			
								  				<option value="<%= nh.getString("pk_seq")%>" selected><%= nh.getString("ten")%></option>
								  		  <%}else{ %>		
								  		  		<option value="<%= nh.getString("pk_seq")%>" ><%= nh.getString("ten")%></option>
								  	<%		}
								  		}	
								  	}catch (java.sql.SQLException e){} %>

                                  </SELECT></TD>
							  	</TR>
							  	<TR>
									<TD class="plainlabel">Chủng loại</TD>
									<TD width="81%" class="plainlabel"><SELECT name="clId" onChange = "submitform();">
								    <option value="" > </option>
									<%  try{
								  		while(cl.next()){								  			
								  			if (obj.getClId().equals(cl.getString("pk_seq"))){ %>								  			
								  				<option value="<%= cl.getString("pk_seq")%>" selected><%= cl.getString("ten")%></option>
								  		  <%}else{ %>		
								  		  		<option value="<%= cl.getString("pk_seq")%>" ><%= cl.getString("ten")%></option>
								  	<%		}
								  		}	
								  	}catch (java.sql.SQLException e){} %>
                        	        </SELECT></TD>
								</TR>
								<TR>
									<TD class="plainlabel">Trạng thái</TD>
									<TD class="plainlabel"><select name="trangthai" onChange = "submitform();">
								  	<% if (obj.getTrangthai().equals("0")){ %>
								    	<option value="2"> </option>
								    	<option value="1">Hoạt động</option>
								    	<option value="0" selected>Ngưng hoạt động</option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>
								    	<option value="2"> </option>
								    	<option value="1" selected>Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
									<%}else{ %>
								    	<option value="2" selected> </option>
								    	<option value="1" >Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động </option>
									<%}} %>
								    	</select></TD>
								</TR>
							    <TR>
									<TD colspan="2" class="plainlabel">
										<a class="button2" href="javascript:clearform()">
											<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
											
										<a class="button2" href="javascript:xuatExcel()">
											<img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel</a>
                                    </TD>
								</TR>
								
							</TABLE>

							</FIELDSET>
							</TD>
						</TR>
					</TABLE>

			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Thông tin sản phẩm &nbsp;&nbsp;
					
					</LEGEND>
	
					<TABLE  width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="10%">Mã sản phẩm  </TH>
									<TH width="25%">Tên sản phẩm </TH>
									<TH width="5%">Đơn vị DL </TH>
									<TH width="10%">Đơn vị KD </TH>
									<TH width="10%">Nhãn hàng </TH>
									<TH width="10%">Ngành hàng </TH>
									<TH width="10%">Trạng thái </TH>
									<TH width="10%">Giá bán lẻ chuẩn</TH>
									<TH width="5%">Tác vụ </TH>
								</TR>
						<% 
							if(splist != null)
							{
							try{
							int m = 0;
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							while (splist.next()){
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
								<%} else {%>
									<TR class= <%= darkrow%> >
								<%}%>							
			
									<TD align="center"><%=splist.getString("ma") %></TD>
									<TD align="right"><div align="left"><%=splist.getString("ten") %> </div></TD>
									<TD align="center"><%=splist.getString("donvi") %></TD>
									<TD align="center"><%=splist.getString("dvkd") %></TD>
									<TD align="center"><%=splist.getString("nhanhang") %></TD>
									<TD align="center"><%=splist.getString("nganhhang") %></TD>
									<% if(splist.getString("trangthai").equals("1")) {%>
										<TD align="center">Hoạt động </TD>
									<%}else {%>
										<TD align="center">Ngưng hoạt động</TD>
									<%} %>
									<%
									String bglc=splist.getString("giablc");
									if(bglc!=null)
									{%>
									<TD align="right"><%=formatter.format(Double.parseDouble(bglc)) %></TD>
										
									<% } else {%>
									<TD align="right"><%=bglc%></TD>
									<%} %>
									<TD align="center">
										<TABLE>
											<TR><TD>
											<%if(quyen[3]!= 0){ %>
											  <A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DThongtinsanphamDisplaySvl?userId=" + userId+ "&display="+ splist.getString("pk_seq")) %>"><img src="../images/Display.png" alt="Cap nhat" title="Hiển thị" width="20" height="20" longdesc="Cap nhat" border = 0></A>                                                 											
											<%} %>
											</TD>
											<TD>&nbsp;</TD>
											</TR>												
										</TABLE>									
								</TR>
							<%m++; }}catch(Exception e){e.printStackTrace();}}%>
										 <tr class="tbfooter" > 
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
</BODY>
</HTML>

<% 	

	try{
		if(cl != null){ cl.close(); cl = null ; }
		if(dvkd != null){ dvkd.close(); dvkd = null ; }
		if(nh != null){ nh.close(); nh = null ; }
		if(splist!=null){ splist.close(); splist = null ; }
		
		if(obj != null)
		{
			obj.DBclose();
			obj = null;
	
		}	
		session.setAttribute("obj",null); 
		
	}
	catch (Exception e) {}

%>
<%}%>
