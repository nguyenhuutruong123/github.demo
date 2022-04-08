<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.thongtinsanpham.IThongtinsanphamList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	NumberFormat formatter = new DecimalFormat("#,###,###");
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ThongtinsanphamSvl", "",userId);
		
		//system.out.println(quyen[0]);
		//system.out.println(quyen[1]);
		//system.out.println(quyen[2]);
		//system.out.println(quyen[3]);	
		//system.out.println(quyen[4]);
		%>

<% IThongtinsanphamList obj = (IThongtinsanphamList)session.getAttribute("obj"); %>
<% ResultSet splist = (ResultSet)obj.getThongtinsanphamList(); %>
<% ResultSet dvkd = obj.getDvkd(); %>
<% ResultSet nh = obj.getNh(); %>
<% ResultSet cl = obj.getCl(); %>
<% ResultSet Nghang = obj.getNghang(); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi";
	} 
	String url = util.getChuyenNguUrl("ThongtinsanphamSvl","",nnId);
	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Bibica - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">

function submitform()
{
	 document.spForm.action.value = 'search';    
     document.forms["spForm"].submit();
}

function clearform()
{
	document.spForm.masp.value = "";
	document.spForm.tensp.value = "";
	document.spForm.dvkdId.selectedIndex = 0;
	document.spForm.ngid.selectedIndex = 0;
	/* document.spForm.clId.selectedIndex = 0; */
    document.spForm.trangthai.selectedIndex = 0;
    document.spForm.nhId.selectedIndex = 0;
    submitform();
}

function xuatExcel()
{
	document.forms['spForm'].action.value= 'excel';
	document.forms['spForm'].submit();
}

function xuatExcelnew()
{
	document.forms['spForm'].action.value= 'excelnew';
	document.forms['spForm'].submit();
}

function newform()
{
	document.forms['spForm'].action.value= 'new';
	document.forms['spForm'].submit();
}
function thongbao()
{   var tt = document.forms['spForm'].msg.value;
	if(tt.length>0)
    	alert(document.forms['spForm'].msg.value);
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

<form name="spForm" method="post" action="../../ThongtinsanphamSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=obj.getUserId()%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >

<script language="javascript" type="text/javascript">
    thongbao();
</script>
 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR>
				<TD align="left">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR height="22">
							<TD align="left" colspan="2" class="tbnavigation"><%=url %></TD>
					   		<TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId) %><%=userTen %>&nbsp;  </TD>
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
							<LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId) %> &nbsp;</LEGEND>

							<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
								<TR>
									<TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Mã sản phẩm",nnId) %></TD>
							        <TD class="plainlabel"  colspan = 1><INPUT name="masp" type="text" size="30" value = '<%=obj.getMasp() %>' onChange = "submitform();"></TD>
								  	<TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Nhãn hàng",nnId) %></TD>
								  	<TD class="plainlabel" colspan = 2 ><SELECT name="nhId"  onChange = "submitform();">
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
									<TD class="plainlabel"><%=ChuyenNgu.get("Ngành hàng",nnId) %></TD>
									<TD width="30%" class="plainlabel"><SELECT name="ngid" onChange = "submitform();">
								    <option value="" > </option>
									<%  try{
								  		while(Nghang.next()){								  			
								  			if (obj.getNganhhangId().equals(Nghang.getString("pk_seq"))){ %>								  			
								  				<option value="<%= Nghang.getString("pk_seq")%>" selected><%= Nghang.getString("ten")%></option>
								  		  <%}else{ %>		
								  		  		<option value="<%= Nghang.getString("pk_seq")%>" ><%= Nghang.getString("ten")%></option>
								  	<%		}
								  		}	
								  	}catch (java.sql.SQLException e){} %>
                        	        </SELECT></TD>
                      
								  <TD class="plainlabel" width="19%"><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %></TD>
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
									<TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId) %></TD>
									<TD class="plainlabel"><select name="trangthai" onChange = "submitform();">
								  	<% if (obj.getTrangthai().equals("0")){ %>
								    	<option value="2"> </option>
								    	<option value="1"><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
								    	<option value="0" selected><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>
								    	<option value="2"> </option>
								    	<option value="1" selected><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
								    	<option value="0" ><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
									<%}else{ %>
								    	<option value="2" selected> </option>
								    	<option value="1" ><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
								    	<option value="0" ><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
									<%}} %>
								    	</select></TD>
								   
									<TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Tên sản phẩm",nnId) %></TD>
							        <TD class="plainlabel" colspan = 5><INPUT name="tensp" type="text" size="30" value = '<%=obj.getTensp() %>' onChange = "submitform();"></TD>
								</TR>
<!-- 								</TR> -->
								<%-- <TR class="plainlabel">
									<TD width="19%" class="plainlabel">Mã sản phẩm Đại Đồng Tiến</TD>
							        <TD class="plainlabel" colspan = 5><INPUT name="maddt" type="text" size="30" value = '<%=obj.getMaddt() %>' onChange = "submitform();"></TD>
								</TR> --%>
								
								
							
								
								
								<TR>
									<TD class="plainlabel" colspan="2">
										<a class="button2" href="javascript:clearform()">
												<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                        <a class="button2" href="javascript:xuatExcel()">
												<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất Excel",nnId) %></a>	
												 <a class="button2" href="javascript:xuatExcelnew()">
												<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất Excel New",nnId) %></a>                          
                                    </TD>								
									<TD width="19%" class="plainlabel"></TD>
									<TD  class="plainlabel"></TD>											
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
					<%if(quyen[0]!=0){ %>
					<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %></a>                            
					<%} %>
					</LEGEND>
	
					<TABLE  width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="10%"><%=ChuyenNgu.get("Mã sản phẩm",nnId) %></TH>
									<TH width="33%"><%=ChuyenNgu.get("Tên sản phẩm",nnId) %></TH>
									<TH width="9%"><%=ChuyenNgu.get("Đơn vị ĐL",nnId) %></TH>
									<TH width="9%"><%=ChuyenNgu.get("Đơn vị KD",nnId) %></TH>
									<TH width="9%"><%=ChuyenNgu.get("Nhãn hàng",nnId) %></TH>
									<TH width="10%"><%=ChuyenNgu.get("Ngành hàng",nnId) %></TH>
									<TH width="8%"><%=ChuyenNgu.get("Trạng thái",nnId) %></TH>
									<TH width="10%"><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
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
										<TD align="center"><%=ChuyenNgu.get("Hoạt động",nnId) %></TD>
									<%}else {%>
										<TD align="center"><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></TD>
									<%} %>

									<TD align="center">
										<TABLE>
											<TR><TD>
												<%if(quyen[2]!=0){ %>
												<A href = "../../RouterSvl?task=<%=Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ThongtinsanphamUpdateSvl?userId=" + userId + "&update=" + splist.getString("pk_seq") ) %>" ><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
												<%} %>
												<%if(quyen[3]!=0){ %>
												<A href ="../../RouterSvl?task=<%=Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") +"ThongtinsanphamUpdateSvl?userId=" + userId + "&display=" + splist.getString("pk_seq") )%>"  ><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>&nbsp;
												<%} %>
												<%if(quyen[1]!=0){ %>
												<A href = "../../RouterSvl?task=<%=Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") +"ThongtinsanphamSvl?userId=" + userId + "&delete=" + splist.getString("pk_seq")) %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa sản phẩm này ?')) return false;"></A>
												<%} %>
											</TD></TR>												
										</TABLE>									
								</TR>
							<%m++; }}catch(Exception e){}}%>
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
												
												//system.out.println("Current page:" + obj.getNxtApprSplitting());
												//system.out.println("List page:" + listPage[i]);
												
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
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<% if(splist != null){ splist.close(); splist = null ;} %>
<% if (dvkd != null){ dvkd.close();  dvkd = null ;}%>
<% if (nh != null){ nh.close();  nh = null ;} %>
<% if (cl != null){ cl.close();  cl = null ;}%>
<%  obj.DBClose(); obj = null ;
	session.getAttribute("obj");
 %>

<%}%>
