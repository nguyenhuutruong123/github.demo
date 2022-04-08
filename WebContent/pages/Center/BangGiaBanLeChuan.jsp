<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.center.beans.banggiablc.IBanggiablc" %>
<%@ page  import = "geso.dms.center.beans.banggiablc.IBanggiablcList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IBanggiablcList obj = (IBanggiablcList)session.getAttribute("obj"); %>
<% List<IBanggiablc> bgblclist = (List<IBanggiablc>)obj.getBgblcList(); %>
<% 
ResultSet kbh   = (ResultSet)obj.getKbh();
ResultSet loaikh   = (ResultSet)obj.getLoaiKhachHang();
ResultSet dvkd = (ResultSet)obj.getDvkd();  
	int[] quyen = new  int[6];
	quyen = util.Getquyen("BanggiabanlechuanSvl","",userId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
    document.bgblcForm.bgTen.value = "";
    document.bgblcForm.dvkdId.selectedIndex = 0;
    document.bgblcForm.kbhId.selectedIndex = 0;
    
    
    
    document.bgblcForm.trangthai.selectedIndex = 0;
    submitform();   
}

function submitform()
{
	document.bgblcForm.action.value= 'search';
	document.forms['bgblcForm'].submit();
}

function newform()
{
	document.forms['bgblcForm'].action.value= 'new';
	document.forms['bgblcForm'].submit();
}
function thongbao()
{
	if(document.getElementById("msg").value != '')
		alert(document.getElementById("msg").value);
}
</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="bgblcForm" method="post" action="../../BanggiabanlechuanSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=obj.getUserId() %>'>
<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="action" value='1'>
<script type="text/javascript">
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
					   <TD align="left" colspan="2" class="tbnavigation">
					   		&nbsp;Dữ liệu nền &gt;Sản phẩm &gt; Bảng giá bán lẻ</TD>
					   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;  </TD>
					</TR>
				  </TABLE>
				</TD>
			</TR>
		</TABLE>
		<TABLE width="100%" cellpadding="0" cellspacing="1">				
				<TR>
					<TD>
					<TABLE border="0" width="100%"  cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="left"><FIELDSET>
							<LEGEND class="legendtitle">Tiêu chí tìm kiếm &nbsp;</LEGEND>

							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Tên bảng giá</TD>
									<TD class="plainlabel"><INPUT name ="bgTen" type="text" value="<%= obj.getTen() %>" size="40" onChange = "submitform();"/></TD>
									<TD class="plainlabel">Đơn vị kinh doanh  </TD>
								    <TD class="plainlabel"><SELECT  name="dvkdId"  onChange = "submitform();">
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
								   	<TD class="plainlabel">Loại khách hàng</TD>
								    <TD class="plainlabel" colspan="1"><SELECT  name="lkhId"  onChange = "submitform();">
								  		<option value =""></option>
								  	 <% try{ while(loaikh.next()){ 
								    	if(loaikh.getString("pk_seq").equals(obj.getLoaikhachhangId())){ %>
								      		<option value='<%=loaikh.getString("pk_seq") %>' selected><%=loaikh.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=loaikh.getString("pk_seq") %>'><%=loaikh.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
									</SELECT></TD>		
								</TR>
								
								<TR>
									<TD class="plainlabel">kênh bán hàng</TD>
								    <TD class="plainlabel" colspan="1"><SELECT  name="kbhId"  onChange = "submitform();">
								  		<option value =""></option>
								  		
								  	 <% try{ while(kbh.next()){ 
								    	if(kbh.getString("pk_seq").equals(obj.getKbhId())){ %>
								      		<option value='<%=kbh.getString("pk_seq") %>' selected><%=kbh.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=kbh.getString("pk_seq") %>'><%=kbh.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
									</SELECT></TD>
									 <TD class="plainlabel" colspan="2">
										<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="" onClick="clearform()" >Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
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
					<LEGEND class="legendtitle">&nbsp; Bảng giá bán &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<%if(quyen[Utility.THEM]!=0) {%>
				
				    	<a class="button3" href="../../RouterSvl?task=<%=Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "BanggiabanlechuanUpdateSvl?userId=" + userId + "&copy=0" ) %>">
				    	<img style="top: -4px;" src="../images/New.png" alt="" onClick="newform();">Tạo mới </a>
				    	
    			
    	
    	
					<%} %>
					</LEGEND>
	
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="4%">STT</TH>
									<TH width="15%">Tên bảng giá </TH>
									<TH width="9%">Ngày hiệu lực</TH>
									<TH >Đơn vị kinh doanh </TH>
									<TH >Kênh bán hàng</TH>
									<TH >Trạng thái </TH>
									<TH width="9%">Ngày tạo</TH>
									<TH width="9%">Người tạo </TH>
									<TH width="9%">Ngày sửa</TH>
									<TH width="15%">Người sửa </TH>
									<TH width="8%" align="center">&nbsp;Tác vụ</TH>
								</TR>

									<% 
									IBanggiablc bgblc = null;
									int size = bgblclist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										bgblc = bgblclist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><%=m+1%></TD>
												<TD align="left"><div align="left"><%= bgblc.getTen()%></div></TD>       
												<TD align="left"><div align="center"><%= bgblc.getTungay()%></div></TD>                             
												<TD align="left"><div align="center"><%=bgblc.getDvkd() %></div></TD>
												<TD align="left"><div align="center"><%=bgblc.getKbhId() %></div></TD>                                   
												<%if (bgblc.getTrangthai().equals("1")){%>
													<TD align="center">Hoạt động</TD>
												<%}else{ %>
													<TD align="center">Ngưng hoạt động</TD>
												<%} %>
												<TD align="center"><%=bgblc.getNgaytao()%></TD>
												<TD align="center"><%=bgblc.getNguoitao()%></TD>
												<TD align="center"><%=bgblc.getNgaysua()%></TD>
												<TD align="center"><%=bgblc.getNguoisua()%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR>
													<TD>
													<%if(bgblc.getTrangthai().equals("0") && quyen[Utility.SUA]!=0){ %>
														<A href = "../../RouterSvl?task=<%=Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "BanggiabanlechuanUpdateSvl?userId=" + userId + "&update=" + bgblc.getId()) %>"><img src="../images/Edit.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													<TD>
														<%if(bgblc.getTrangthai().equals("0") &&  quyen[Utility.XOA]!=0){ %>
															<A href = "../../RouterSvl?task=<%=Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "BanggiabanlechuanSvl?userId=" + userId + "&delete=" + bgblc.getId()) %>">
															<img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa bảng giá bán lẻ này?')) return false;"></A>
														<%} %>	
													</TD>
													
													<TD>
														<%if(bgblc.getTrangthai().equals("0") &&  quyen[Utility.CHOT]!=0){ %>
															<A href = "../../RouterSvl?task=<%=Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "BanggiabanlechuanSvl?userId=" + userId + "&chot=" + bgblc.getId()) %>"><img src="../images/Chot.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Ban Muon Chốt Bảng giá Nay?')) return false;"></A>
														<%} %>	
													</TD>
													
													<TD>
													<%if(quyen[Utility.XEM]!=0){ %>
														<A href = "../../RouterSvl?task=<%=Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "BanggiabanlechuanUpdateSvl?userId=" + userId + "&display=" + bgblc.getId()) %>"><img src="../images/Display.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													
													<TD>
													<%if(quyen[Utility.THEM]!=0 && bgblc.getTrangthai().equals("1")){ %>
														<A href = "../../BanggiabanlechuanUpdateSvl?userId=<%=userId%>&copy=<%= bgblc.getId() %>">
															<img src="../images/copy20.png" alt="Cap nhat" title="copy" width="20" height="20" longdesc="Cap nhat" border = 0>
														</A>
													<%} %>
													</TD>
													
													<TD>&nbsp;</TD>

													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }%>
								
								<TR>
									<TD align="center" colspan="11" class="tbfooter">&nbsp;		</TD>
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
</body>
</html>
<% if (dvkd != null)
	dvkd.close(); 
	obj.DbClose();
	}
	
%>