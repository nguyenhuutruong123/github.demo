<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "geso.dms.center.beans.themnppctkm.*" %>
<%@ page import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
		IThemNppCtkmList obj =(IThemNppCtkmList)session.getAttribute("obj");
		ResultSet ThemNppCtkmRs = obj.getDataRs();

		int[] quyen = new  int[6];
		quyen = util.Getquyen("ThemNppCtkmSvl","", userId);
%>
<% String nnId = (String)session.getAttribute("nnId"); %>
<% if(nnId == null) {
 nnId = "vi";  
 }
 String url = util.getChuyenNguUrl("ThemNppCtkmSvl","",nnId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$( ".days" ).datepicker({
				changeMonth: true,
				changeYear: true
			});
		});
	</script>

	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{
	document.forms["khtt"].Tungay.value= "";
	document.forms["khtt"].Denngay.value= "";
	document.forms["khtt"].nguoitao.value= "";
	document.forms["khtt"].nguoisua.value= "";
	document.forms["khtt"].trangthai.value= "";
	document.forms["khtt"].submit();
	}

	function submitform()
	{
		document.forms["khtt"].action.value= "search";
		document.forms["khtt"].submit();
	}

	function newform()
	{
		document.forms["khtt"].action.value= "new";
		document.forms["khtt"].submit();
	}
	
	function processing(id,chuoi)
	{
		 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
		 document.getElementById(id).href=chuoi;
	}
	 function thongbao()
	 {
		 var tt = document.forms["khtt"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["khtt"].msg.value);
 	 }		 
	
	</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="khtt" method="post" action="../../ThemNppCtkmSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >

<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align="left" valign="top" bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR height="22">
								<TD align="left" colspan="2" class="tbnavigation"><%=url %> </TD>
								<TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId) %> <%= userTen %></TD>
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
								<TD width="100%" align="center" >
									<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId) %> &nbsp;</LEGEND>

										<TABLE  width="100%" cellpadding="6" cellspacing="0">

										<%-- 	<TR>
												<TD width="130px" class="plainlabel"> Từ ngày </TD>
												<TD width="300px" class="plainlabel">
													<input  type="text" name="Tungay" value="<%=obj.getTungay() %>" size="20" onchange=submitform();>
												</TD>
											</TR >
											<TR>
												<TD width="130px" class="plainlabel">  Đến ngày </TD>
												<TD width="300px" class="plainlabel">
													<input type="text" name="Denngay" value="<%=obj.getDenngay() %>" size="20" onchange=submitform();>
												</TD>
											</TR> --%>

<%-- <TR>
									<TD class="plainlabel">Trạng thái </TD>
									<TD class="plainlabel">
										<select name="trangthai" onChange="submitform();">
											<% if (obj.getTrangthai().equals("1")){%>
											  	<option value="1" selected>Đã chốt</option>
											  	<option value="0">Chưa chốt</option>
											  	<option value=""></option>
											<%}else if(obj.getTrangthai().equals("0")) {%>
											 	<option value="0" selected>Chưa chốt</option>
											  	<option value="1" >Đã chốt</option>
											  	<option value="" ></option>
											<%}else{%>
												<option value="" selected> </option>
												<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đã chốt</option>											
											<%} %>
									          </select>
									</TD>
							    </TR>	 --%>
										<!-- 	<tr class="plainlabel"> 
												<td colspan="2" >
													<a class="button2" href="javascript:clearform()">
														<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
												</td>
											</tr> -->
										</TABLE>
									</FIELDSET>
								</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>

				<TR>
					<TD width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Thêm nhà phân phối chương trình khuyến mãi",nnId) %> &nbsp;&nbsp;
								<%if(quyen[0]!= 0){ %>
								<a class="button3" href="javascript:newform()">
									<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> </a>
								<%} %>
							</LEGEND>


<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="20%"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TH>
									<TH width="40%"><%=ChuyenNgu.get("Scheme",nnId) %></TH>
									<TH width="10%"><%=ChuyenNgu.get("Trạng thái",nnId) %></TH>
									<TH width="10%"><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
									<TH width="10%"><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
									<TH width="10%"><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
								</TR>
								
						<%  														
	                        int m = 0;
	                        String lightrow = "tblightrow";
	                        String darkrow = "tbdarkrow";
							if(ThemNppCtkmRs!=null)
							{%>
							<% try{								
                                while (ThemNppCtkmRs.next()){
                                    	
                                   	if (m % 2 != 0) {%>                     
                                    	<TR class= <%=lightrow%> >
                                    <%} else {%>
                                       	<TR class= <%= darkrow%> >
                                    	<%}%>
                                    		<TD><div align="left"><%= ThemNppCtkmRs.getString("nppTEN")==null?"": ThemNppCtkmRs.getString("nppTEN")%></div></TD>
											<TD><div align="left"><%= ThemNppCtkmRs.getString("hoadon")==null?"": ThemNppCtkmRs.getString("hoadon")%></div></TD>
											<%
										
											if (ThemNppCtkmRs.getString("trangthai").equals("1")){ %>
												<TD align="center"><%=ChuyenNgu.get("Đã chốt",nnId) %></TD>
											<%}else{%>
												<TD align="center"><%=ChuyenNgu.get("Chưa chốt",nnId) %></TD>
											<%}%>
											<TD align="left"><%=ThemNppCtkmRs.getString("ngaysua")==null?" ":ThemNppCtkmRs.getString("ngaysua")%></TD>
											<TD align="left"><%=ThemNppCtkmRs.getString("nguoisua")==null?" ":ThemNppCtkmRs.getString("nguoisua")%></TD>
											
											
											<TD align="center">
											<TABLE border = 0 cellpadding="0" cellspacing="0">
												<TR>
												<%if(ThemNppCtkmRs.getString("trangthai").equals("0")  ){ %>
												<TD>
													<%if(quyen[2]!= 0){ %>
													<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ThemNppCtkmUpdateSvl?userId="+userId+"&update="+ThemNppCtkmRs.getString("pk_Seq") +"")%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
												</TD>
												<TD>
													<%if(quyen[4]!= 0){ %>
													<A id='chotphieu<%=ThemNppCtkmRs.getString("pk_Seq")%>' href=""><img src="../images/Chot.png" alt="Chốt phiếu Chặn hóa đơn" width="20" height="20" title="Chốt phiếu chặn hóa đơn" border="0" onclick="if(!confirm('Chốt phiếu')) {return false ;}else{ processing('<%="chotphieu"+ThemNppCtkmRs.getString("pk_Seq")%>' , '../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ThemNppCtkmSvl?userId="+userId+"&chot="+ThemNppCtkmRs.getString("pk_seq") +"")%>');}"  ></A>
													<%} %>
												</TD>
												
												<TD>
													<%if(quyen[1]!= 0){ %>
													<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ThemNppCtkmSvl?userId="+userId+"&delete="+ThemNppCtkmRs.getString("pk_Seq") +"")%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa  ?')) return false;"></A>
													<%} %>
												</TD>
														
														
												<%} %>
												<TD>&nbsp;</TD>
												<%if(ThemNppCtkmRs.getString("trangthai").equals("1")  && quyen[3]!= 0 ){ %>
													<TD>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ThemNppCtkmUpdateSvl?userId="+userId+"&display="+ThemNppCtkmRs.getString("pk_Seq") +"")%>"><img src="../images/Display20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													</TD>	
												<%} %>
												</TR>
												</TABLE>
											</TD>
										</TR>
								<%m++; }}catch(java.sql.SQLException e){e.printStackTrace();}
							}%>
								
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
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<%
	try
	{
		if(ThemNppCtkmRs != null){ ThemNppCtkmRs.close(); ThemNppCtkmRs = null; }

		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {} %>
<%}%>
