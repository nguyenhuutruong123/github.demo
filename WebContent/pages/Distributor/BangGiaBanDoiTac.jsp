<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.distributor.beans.banggiabandoitac.imp.*" %>
<%@ page  import = "geso.dms.distributor.beans.banggiabandoitac.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IBanggiabandoitacList obj = (IBanggiabandoitacList)session.getAttribute("obj"); %>
<% ResultSet dvkd = (ResultSet)obj.getDvkd();  %>
<% ResultSet kenh = (ResultSet)obj.getKenh();  %>
<% ResultSet bglist = (ResultSet)obj.getBglist();  
	int[] quyen = new  int[4];
	quyen = util.Getquyen("BanggiabandoitacSvl","",userId);
	obj.setNextSplittings();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE><%=getServletContext().getInitParameter("TITLENAME") %></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<SCRIPT language="javascript" type="text/javascript">
 function submitform()
{   
   document.forms['bgmuanppForm'].action.value='search';
   document.forms["bgmuanppForm"].submit();
}

 function newform()
{   
	document.forms['bgmuanppForm'].action.value='new';
   	document.forms['bgmuanppForm'].submit();
}
 
 function clearform()
 {
     document.bgmuanppForm.bgTen.value = "";
     document.bgmuanppForm.dvkdId.value="";
     document.bgmuanppForm.kenhId.value="";
     document.bgmuanppForm.trangthai.value="";
     submitform();   
 }
 function thongbao()
 {
	 var tt = document.forms["bgmuanppForm"].msg.value;
 	 if(tt.length>0)
     	alert(document.forms["bgmuanppForm"].msg.value);
	 }		 
 function xuatExcel()
 {
 	document.forms['bgmuanppForm'].action.value= 'toExcel';
 	document.forms['bgmuanppForm'].submit();
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

<form name="bgmuanppForm" method="post" action="../../BanggiabandoitacSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=obj.getUserId() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="nppId" value='<%=obj.getNppId() %>'>
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
<input type="hidden" name="nxtApprSplitting"value="<%= obj.getNxtApprSplitting() %>">
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR>
				<TD align="left" class="tbnavigation">
				  <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR height="22">
					  	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền > Dữ liệu nền sản phẩm > Bảng giá bán cho đối tác </TD>
   						<TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%= obj.getNppTen() %>  &nbsp;</TD>
					</TR>
				  </TABLE></TD>
			</TR>
		</TABLE>
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR>
					<TD>
					<TABLE border="0" width="100%" >
						<TR>
							<TD width="100%" align="left"><FIELDSET>
							<LEGEND class="legendtitle">Tiêu chí tìm kiếm &nbsp;</LEGEND>

							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<TR>
									<TD width="100px" class="plainlabel">Tên bảng giá</TD>
									<TD width="260px" class="plainlabel">
									<INPUT name="bgTen" type="text" size="40" value='<%=obj.getTen() %>' onChange = "submitform();"/></TD>
									<TD class="plainlabel" width="120px">Đơn vị kinh doanh </TD>
								      <TD class="plainlabel">
								      	<SELECT name="dvkdId" onChange = "submitform();">								      
								  	 		<option value =""></option>
								  	 <% try{ while(dvkd.next()){ 
								    	if(dvkd.getString("dvkdId").equals(obj.getDvkdId())){ %>
								      		<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
								      	<%}else{ %>
								     		<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
									</SELECT></TD>
								</TR>
						
								<TR>
								  <TD class="plainlabel">Kênh bán hàng </TD>
								  <TD class="plainlabel">
								      	<SELECT name="kenhId" onChange = "submitform();">								      
								  	 		<option value =""></option>
								  	 <% try{ while(kenh.next()){ 
								    	if(kenh.getString("kenhId").equals(obj.getKenhId())){ %>
								      		<option value='<%=kenh.getString("kenhId") %>' selected><%=kenh.getString("kenh") %></option>
								      	<%}else{ %>
								     		<option value='<%=kenh.getString("kenhId") %>'><%=kenh.getString("kenh") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
                                  </SELECT></TD>
                                  <TD class="plainlabel">Trạng thái</TD>
									<TD class="plainlabel"><select name="trangthai" onChange = "submitform();">
								  	<% if (obj.getTrangthai().equals("0")){ %>
								    	<option value=""></option>
								    	<option value="1">Hoạt động</option>
								    	<option value="0" selected>Ngưng hoạt động</option>
									<%}else if (obj.getTrangthai().equals("1")){ %>
								    	<option value=""></option>
								    	<option value="1" selected>Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
									<%}else{ %>
								    	<option value="" selected></option>
								    	<option value="1" >Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
									<%} %>
								    	</select></TD>
							  </TR>
						
								<TR>
									<TD class="plainlabel" colspan="4" >
									<a class="button2" href="javascript:clearform()"> <img style="top: -4px;" src="../images/button.png" alt="" onClick="clearform()">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
									                                &nbsp;&nbsp;&nbsp;&nbsp;
                                  </TD>																	
								</TR>							  
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
					<LEGEND class="legendtitle">&nbsp;Bảng giá bán cho đối tác &nbsp;&nbsp;&nbsp;
						<a class="button3" href="javascript:newform()"> 
										 <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
					</LEGEND>
	
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="3%">STT</TH>
									<TH width="15%">Tên bảng giá </TH>
									<TH width="10%">ĐVKD </TH>
									<TH width="8%">Kênh </TH>
									<TH width="8%">Từ ngày</TH>
									<TH width="8%">Trạng thái  </TH>
									<TH width="8%">Ngày tạo</TH>
									<TH width="12%">Người tạo </TH>
									<TH width="8%">Ngày sửa </TH>
									<TH width="12%">Người sửa </TH>
									<TH width="10%" align="center">&nbsp;Tác vụ</TH>
								</TR>
						<% 
							int m = 0;
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							try{
								if(bglist != null)
							while(bglist.next()){
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
							  <%} else {%>
									<TR class= <%= darkrow%> >
							  <%}%>
							  			<TD align="center"><%=m+1%></TD>
										<TD align="left"><%= bglist.getString("ten") %></TD>
										<TD align="center"><%= bglist.getString("dvkd") %></TD>
										<TD align="center"><%= bglist.getString("kenh") %></TD>
										<TD align="center"><%= bglist.getString("tungay") %></TD>					
									<% if (bglist.getString("trangthai").equals("1")){ %>
										<TD align="center">Hoạt động</TD>							
									<%}else{ %>
										<TD align="center">Ngưng hoạt động</TD>
									<%} %>
										<TD align="center"><%= bglist.getDate("ngaytao").toString() %></TD>	
										<TD align="center"><%= bglist.getString("nguoitao") %></TD>
										<TD align="center"><%= bglist.getDate("ngaysua").toString() %></TD>
										<TD align="center"><%= bglist.getString("nguoisua") %></TD>
										<TD align="center">
											
											<A href = "../../BanggiabandoitacUpdateSvl?userId=<%=userId%>&update=<%= bglist.getString("id") %>"><img src="../images/Display.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
												
										</TD>
									</TR>
								<%m++; }
								
								}catch(Exception e){}%>
								
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
</BODY>
</HTML>
<% if(dvkd!=null) dvkd.close();  %>
<% if(kenh!=null) kenh.close();  %>
<% if(bglist!=null)	bglist.close();  %>
<% if(obj!=null) obj.closeDB(); %>
<%}%>