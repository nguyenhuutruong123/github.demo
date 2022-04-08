<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.Danhmucdoitra.IDanhmucdoitra" %>
<%@ page  import = "geso.dms.center.beans.Danhmucdoitra.IDanhmucdoitraList" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{int[] quyen = new  int[6];
	quyen = util.Getquyen("DanhmucdoitraSvl","",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);	
	System.out.println(quyen[4]); %>

<% IDanhmucdoitraList obj = (IDanhmucdoitraList)session.getAttribute("obj"); %>
<% List<IDanhmucdoitra> lchlist = (List<IDanhmucdoitra>)obj.getLchList(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">

 function clearform()
 {
     document.lchForm.LoaiCuaHang.value = "";
     document.lchForm.DienGiai.value = "";      
     document.lchForm.TrangThai.selectedIndex = 2;
     submitform();
 }

 function submitform()
 {
 	document.forms['lchForm'].action.value= 'search';
 	document.forms['lchForm'].submit();
 }

 function newform()
 {
 	document.forms['lchForm'].action.value= 'new';
 	document.forms['lchForm'].submit();
 }
 
 function thongbao()
 {var tt = document.forms['lchForm'].msg.value;
 	if(tt.length>0)
     alert(document.forms['lchForm'].msg.value);
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

<form name="lchForm" method="post" action="../../DanhmucdoitraSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

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
							   		Dữ liệu nền > Kinh doanh > Danh mục đổi trả </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn<%=userTen %>&nbsp;  </TD> 
						    </tr>
   						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm   </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
										  <TD class="plainlabel" >Loại khách hàng </TD>
										  <TD class="plainlabel" ><INPUT name="LoaiCuaHang" size="20" type="text" 
                                          					value="<%= obj.getLoaicuahang() %>" onChange="submitform();"></TD>
									  </TR>
										<TR>
											<TD width="20%" class="plainlabel" >Diễn giải </TD>
										    <TD width="80%" class="plainlabel" ><INPUT name="DienGiai" size="40" type="text" 
                                            				value="<%= obj.getDiengiai()%>" onChange="submitform();"></TD>
										</TR>
										<TR>
											<TD class="plainlabel" > Trạng thái </TD>
											<TD class="plainlabel" >
											  <select name="TrangThai" onChange="submitform();">
											  	<option value="1" <% if(obj.getLoaicuahang().equals("1")) { %> selected="selected" <%} %>> Trả hàng </option>
											  	<option value="0" <% if(obj.getLoaicuahang().equals("0")) { %> selected="selected" <%} %>>Đổi hàng</option>
											  	<option value="2"> </option>
											  
											
									          </select></TD>
										</TR>
										<TR>
                                              <TD class="plainlabel" colspan=2>
                                              <a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                              
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
			<TABLE width="100%" cellpadding="0" cellspacing="1">
			    <TR>
					<TD align="left" >
						 <FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Loại của hàng &nbsp;&nbsp;&nbsp; 
							<%if(quyen[0]!=0){ %>
							<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
							<%} %>
						</LEGEND> 
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												
											    <TH width="22%">Diễn giải </TH>
											    <TH width="11%">Loại </TH>
											    <TH width="9%">Ngày tạo</TH>
											  <TH width="13%">Người tạo </TH>										
											  <TH width="9%">Ngày sửa </TH>
											  <TH width="14%">Người sửa</TH>
											  <TH width="10%">Tác vụ</TH>
										  </TR>
								<% 
									IDanhmucdoitra lch = null;
									int size = lchlist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										lch = lchlist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="left"><div align="center"><%= lch.getDiengiai()  %></div></TD>                                   
												<TD><div align="center"><% if(lch.getLoaicuahang().equals("0")){ %>
													Đổi hàng
												<%} else {%>
													Trả hàng
												<%} %></div></TD>
											
												<TD align="center"><%=lch.getNgaytao()%></TD>
												<TD align="center"><%=lch.getNguoitao()%></TD>
												<TD align="center"><%=lch.getNgaysua()%></TD>
												<TD align="center"><%=lch.getNguoisua()%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													<%if(quyen[2]!=0){ %>
													  <A href = "../../DanhmucdoitraUpdateSvl?userId=<%=userId%>&update=<%= lch.getId()%>">
                                               <img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
													<%if(quyen[1]!=0){ %>
													  <A href = "../../DanhmucdoitraSvl?userId=<%=userId%>&delete=<%=lch.getId()%>">
                                                <img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa loại Danh mục này không ?')) return false;"></A>
                                                <%} %>
                                                </TD>
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }%>
								
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
	if(lchlist != null){ lchlist.clear(); lchlist = null ; }
	obj.closeDB(); obj = null;
	session.setAttribute("obj","");
	
}%>