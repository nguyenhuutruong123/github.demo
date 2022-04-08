<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.center.beans.banggiamuanpp.IBanggiamuanpp" %>
<%@ page  import = "geso.dms.center.beans.banggiamuanpp.IBanggiamuanppList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{
		int[] quyen = new  int[5];
		quyen = util.Getquyen("11",userId);
		
		System.out.println(quyen[0]);
		System.out.println(quyen[1]);
		System.out.println(quyen[2]);
		System.out.println(quyen[3]);	
		System.out.println(quyen[4]);
		%>

<% IBanggiamuanppList obj = (IBanggiamuanppList)session.getAttribute("obj"); %>
<% List<IBanggiamuanpp> bgmuanpplist = (List<IBanggiamuanpp>)obj.getBgmuanppList(); %>
<% ResultSet dvkd = (ResultSet)obj.getDvkd();  %>
<% ResultSet kenh = (ResultSet)obj.getKenh();  %>
<% ResultSet bglist = (ResultSet)obj.getBglist();  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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
     document.bgmuanppForm.dvkdId.selectedIndex = 0;
     document.bgmuanppForm.kenhId.selectedIndex = 0;
     document.bgmuanppForm.trangthai.selectedIndex = 0;
     submitform();   
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

<form name="bgmuanppForm" method="post" action="../../BanggiamuanppSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=obj.getUserId() %>'>
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR>
				<TD align="left" >
				  <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR height="22">
					  	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Sản phẩm &gt; Bảng giá bán cho NPP</TD>
   						<TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=obj.getUserTen() %>  &nbsp;</TD>
					</TR>
				  </TABLE>
				  </TD>
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
									<TD width="21%" class="plainlabel">Tên bảng giá</TD>
									<TD width="79%" class="plainlabel">
									<INPUT name="bgTen" type="text" size="40" value='<%=obj.getTen() %>' onChange = "submitform();"/></TD>
								</TR>
								<TR>
								    <TD class="plainlabel">Đơn vị kinh doanh </TD>
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
								    	<option value="0" >Ngưng hoạt động</option>
									<%}} %>
								    	</select></TD>
								</TR>
								<TR>
									<TD class="plainlabel">
									<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="" onClick="clearform()">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                  </TD>								
									<TD class="plainlabel">&nbsp;</TD>										
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
					<LEGEND class="legendtitle">&nbsp;Bảng giá bán cho NPP &nbsp;&nbsp;&nbsp;
					<%if(quyen[0]!=0){ %>
					<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="" onClick="newform();">Tạo mới </a>  
    	<%} %>  
							</LEGEND>
	
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="18%">Tên bảng giá </TH>
									<TH width="14%">Đơn vị kinh doanh </TH>
									<TH width="10%">Kênh  </TH>
									<TH width="10%">Trạng thái  </TH>
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
							while(bglist.next()){
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
							  <%} else {%>
									<TR class= <%= darkrow%> >
							  <%}%>
										<TD align="left"><%= bglist.getString("ten") %></TD>
										<TD align="center"><%= bglist.getString("dvkd") %></TD>
										<TD align="center"><%= bglist.getString("kenh") %></TD>		
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
											<TABLE border = 0 cellpadding="0" cellspacing="0">
												<TR><TD>
												<%if(quyen[2]!=0){ %>
												<A href = "../../BanggiamuanppUpdateSvl?userId=<%=userId%>&update=<%= bglist.getString("id") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
												<%} %>
											</TD>
											<TD>&nbsp;</TD>
											<TD>
												<%if(quyen[2]!=0){ %>
												<A href = "../../BanggiamuanppSvl?userId=<%=userId%>&assign=<%= bglist.getString("id") %>"><img src="../images/Next20.png" alt="Chon Nha Phan Phoi" title="Chọn nhà phân phối" width="20" height="20" longdesc="Chon Nha Phan Phoi" border = 0></A>
												<%} %>
											</TD>
											<TD>&nbsp;</TD>
																						
											<TD>
												<%if(quyen[1]!=0){ %>
												<A href = "../../BanggiamuanppSvl?userId=<%=userId%>&delete=<%= bglist.getString("id") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa bảng giá này ?')) return false;"></A>
												<%} %>
												</TD>
												<TD>
												<%if(quyen[3]!=0){%>
														<A href = "../../BanggiamuanppSvl?userId=<%=userId%>&display=<%=  bglist.getString("id") %>"><img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
													<%} %>
												</TD>
											</TR>
											</TABLE>
										</TD>
									</TR>
								<%m++; }
								
								}catch(java.sql.SQLException e){}%>
								
								<TR>
									<TD align="center" colspan="11" class="tbfooter">&nbsp;	</TD>
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
<% dvkd.close();  %>
<% kenh.close();  %>
<% bglist.close();  %>
<% obj.closeDB(); %>
<%}%>