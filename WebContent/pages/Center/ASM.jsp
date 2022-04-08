<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.asm.IAsm" %>
<%@ page  import = "geso.dms.center.beans.asm.IAsmList" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{int[] quyen = new  int[6];
	quyen = util.Getquyen("ASMSvl","",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);	
	System.out.println(quyen[4]); %>


<% IAsmList obj =(IAsmList)session.getAttribute("obj"); %>
<% ResultSet kbh =  (ResultSet) obj.getKbh(); %>
<% ResultSet dvkd = (ResultSet) obj.getDvkd();  %>
<% ResultSet kv = (ResultSet) obj.getKv(); %>
<% ResultSet asmList = (ResultSet) obj.getAsmlist();%>
<% obj.setNextSplittings(); %>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("ASMSvl","",nnId);	
	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<SCRIPT language="javascript" type="text/javascript">

function clearform()
{
	document.asmForm.asmTen.value = "";
	document.asmForm.asmMa.value = "";
    document.asmForm.DienThoai.value = "";  
    document.asmForm.kbhId.selectedIndex = 0;
    document.asmForm.dvkdId.selectedIndex = 0;
    document.asmForm.kvId.selectedIndex = 0;
    document.asmForm.loaigiamsat.selectedIndex = 0;
    document.asmForm.TrangThai.selectedIndex = 2;
    submitform();    
}

function submitform()
{
	document.forms['asmForm'].action.value= 'search';
	document.forms['asmForm'].submit();
}
function xuatexcel()
{
	document.forms['asmForm'].action.value= 'excel';
	document.forms['asmForm'].submit();
}
function newform()
{
	document.forms['asmForm'].action.value= 'new';
	document.forms['asmForm'].submit();
}
function thongbao(){
	var tt = document.forms['asmForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['asmForm'].msg.value);
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

<form name="asmForm" method="post" action="../../ASMSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>

<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
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
				  <TD align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">

						  <tr height="20">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp; <%= " " + url %> </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId) %> <%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>

		</TABLE>
		<TABLE width="100%" cellpadding="0" cellspacing="0">		
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle"><%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId) %> &nbsp;</LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
							      <TR>  
							      		<TD width="24%" class="plainlabel"><%=ChuyenNgu.get("Tên trưởng khu vực (ASM)",nnId) %> </TD>
								        <TD class="plainlabel"><input type="text" name="asmTen" id="asmTen" value="<%= obj.getTen() %>" onChange="submitform();"></TD>
								    	
								    	<TD width="24%" class="plainlabel"><%=ChuyenNgu.get("Mã ASM",nnId) %> </TD>
									    <TD class="plainlabel">
									    	<input type="text" name="asmMa" id="asmMa" value="<%= obj.getMa() %>" onChange="submitform();">
									    </TD>
								  </TR>

								  <TR>
								    <TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng (KBH)",nnId) %> </TD>
								    <TD  class="plainlabel">
								    	<SELECT name="kbhId" onChange = "submitform();">
									    <option value=""></option> 
									      <% try{ while(kbh.next()){ 
									    	if(kbh.getString("kbhId").equals(obj.getKbhId())){ %>
									      		<option value='<%=kbh.getString("kbhId") %>' selected='selected'><%=kbh.getString("kbh") %></option>
									      	<%}else{ %>
									     		<option value='<%=kbh.getString("kbhId") %>' ><%=kbh.getString("kbh") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									    </SELECT>
									</TD>
								    <TD class="plainlabel"><%=ChuyenNgu.get("Đơn vị Kinh doanh(ĐVKD)",nnId) %> </TD>
								    <TD width="20%" class="plainlabel">
								    <SELECT name="dvkdId" onChange = "submitform();">
									    <option value=""></option> 
									      <% try{ while(dvkd.next()){ 
									    	if(dvkd.getString("dvkdId").equals(obj.getDvkdId())){ %>
									      		<option value='<%=dvkd.getString("dvkdId") %>' selected='selected'><%=dvkd.getString("dvkd") %></option>
									      	<%}else{ %>
									     		<option value='<%=dvkd.getString("dvkdId") %>' ><%=dvkd.getString("dvkd") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									    </SELECT> 
									</TD>
								<TR>
						            <TD width="18%" class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %></TD>
									<TD width="33%" class="plainlabel">
									<SELECT name="kvId"  onChange = "submitform()">
									<option value=""> </option>
									      <% try{ while(kv.next()){ 
									    	if(kv.getString("kvId").equals(obj.getKvId())){ %>
									      		<option value='<%= kv.getString("kvId") %>' selected='selected'><%= kv.getString("kv") %></option>
									      	<%}else{ %>
									     		<option value='<%= kv.getString("kvId") %>' ><%= kv.getString("kv") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
                                	</SELECT>
									</TD>
						            
								    <TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId) %> </TD>
								    <TD  class="plainlabel"><SELECT name="TrangThai" onChange = "submitform();">
                                      
                                      <% if (obj.getTrangthai().equals("1")){%>
											  <option value="1" selected><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
											  <option value="0"><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
											  <option value="2"></option>
											  
										<%}else if(obj.getTrangthai().equals("0")) {%>
											  <option value="0" selected><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
											  <option value="1" ><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
											  <option value="2" ></option>
											  
										<%}else{%>																						  
											  <option value="1" ><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
											  <option value="0" ><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
											  <option value="2" selected></option>
										<%}%>
                                    </SELECT></TD>
						      </TR>
						      <TR>								   								    	
								    <TD class="plainlabel"><%=ChuyenNgu.get("Loại nhân viên",nnId) %> </TD>
								    <TD class="plainlabel"><SELECT name="loaigiamsat" onChange = "submitform();" >
                                      <% 
                                      if (obj.getThuviec().equals("0")){ %>
								    	<option > </option>
								    	<option value="1"><%=ChuyenNgu.get("Thử việc",nnId) %></option>
								    	<option value="0" selected><%=ChuyenNgu.get("Nhân viên",nnId) %></option>
									<%}else{ 							
								  		if (obj.getThuviec().equals("1")){ %>
								    	<option > </option>
								    	<option value="1" selected><%=ChuyenNgu.get("Thử việc",nnId) %></option>
								    	<option value="0" ><%=ChuyenNgu.get("Nhân viên",nnId) %></option>
									<%}else{ %>
								    	<option  selected> </option>
								    	<option value="1" ><%=ChuyenNgu.get("Thử việc",nnId) %></option>
								    	<option value="0" ><%=ChuyenNgu.get("Nhân viên",nnId) %></option>
									<%}} %>
                                    </SELECT></TD>
                                    
                                    <TD class="plainlabel"><%=ChuyenNgu.get("Số điện thoại (ĐT)",nnId) %> </TD>
								    <TD class="plainlabel"><input type="text" name="DienThoai" id="DienThoai" value="<%= obj.getDienthoai() %>" onChange="submitform();"></TD>
							    
						      </TR>
							    <TR>
									<TD colspan="4" class="plainlabel">
									<a class="button2" href="javascript:clearform()">
											<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
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
							<LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Trưởng khu vực",nnId) %> &nbsp;&nbsp;&nbsp;
							<%if(quyen[0]!=0){ %>
							<a class="button3" href="javascript:newform()">
    								<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> </a>    
							<%} %>
							
						</LEGEND>
	
						    <TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">

								<TR class="tbheader">
									<TH width="8%"><%=ChuyenNgu.get("Mã ASM",nnId) %></TH>
									<TH width="12%"><%=ChuyenNgu.get("Tên ASM",nnId) %> </TH>
									<TH width="8%"><%=ChuyenNgu.get("Số ĐT",nnId) %> </TH>
									<TH width="7%"><%=ChuyenNgu.get("Kênh BH",nnId) %> </TH>
									<TH width="8%"><%=ChuyenNgu.get("ĐVKD",nnId) %></TH>
									<TH width="8%"><%=ChuyenNgu.get("Khu vực",nnId) %></TH>
									<TH width="7%"><%=ChuyenNgu.get("Loại",nnId) %></TH>
									<TH width="9%"><%=ChuyenNgu.get("Trạng thái",nnId) %> </TH>
									<TH width="6%"><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
									<TH width="9%"><%=ChuyenNgu.get("Người tạo",nnId) %></TH>
									<TH width="6%"><%=ChuyenNgu.get("Ngày sửa",nnId) %> </TH>
									<TH width="9%"><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
									<TH width="15%" align="center">&nbsp;<%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
								</TR>

                                <%      
                                   
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    
                                    if(asmList!=null)
                                    	try{
                                    		while (asmList.next()){
                                       
                                        	if (m % 2 != 0) {%>                     
                                            	<TR class= <%=lightrow%> >
                                        	<%} else { %>
                                            	<TR class= <%= darkrow%> >
                                        	<%}%>
                                            	    <TD align="left"><div align="left"><%= asmList.getString("asmma") %></div></TD>
                                            	    <TD align="left"><div align="left"><%= asmList.getString("asmten") %></div></TD>                                   
                                                	<TD><div align="center"><%= asmList.getString("dienthoai")  %></div></TD>
                                                	<TD align="center"><%= asmList.getString("kbh") %></TD>
                                                	<TD align="center"><%= asmList.getString("dvkd") %></TD>
                                                	<TD align="center"><%= asmList.getString("kv") %></TD>
                                                	<% if(asmList.getString("tinhtrang").equals("0")){ %>                             
												<TD align="center"> <%=ChuyenNgu.get("Nhân viên",nnId) %> </TD>
												<%} else {%>
												<TD align="center"> <%=ChuyenNgu.get("Thử việc",nnId) %></TD>
												<%} %> 
                                                	<% if (asmList.getString("trangthai").equals("1")){ %>
                                                    	<TD align="center"><%=ChuyenNgu.get("Hoạt động",nnId) %></TD>
                                                	<%}else{%>
                                                    	<TD align="center"><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></TD>
                                                	<%}%>
                                                	<TD align="center"><%= asmList.getString("ngaytao") %></TD>
                                                	<TD align="center"><%= asmList.getString("nguoitao") %></TD>
                                                	<TD align="center"><%= asmList.getString("ngaysua")%></TD>
                                                	<TD align="center"><%= asmList.getString("nguoisua")%></TD>
                                                	<TD align="center">
                                                		<TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    	<TR><TD>
                                                    	<%if(quyen[2]!=0){ %>
                                                    		<A href = "../../ASMUpdateSvl?userId=<%=userId%>&update=<%= asmList.getString("asmid") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
                                                    	<%} %>
                                                    	</TD>
                                                    	<TD>&nbsp;</TD>
                                                    	<TD>
                                                    	<%if(quyen[1]!=0){ %>
                                                        	<A href = "../../ASMSvl?userId=<%=userId%>&delete=<%= asmList.getString("asmid") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa Trưởng khu vực này?')) return false;"></A>
                                                    	<%} %>
                                                    	</TD>
                                                    	
                                                    	<TD>&nbsp;</TD>
                                                    	<TD>	
														<%if(quyen[3]!=0){ %>
														<A href = "../../ASMUpdateSvl?userId=<%=userId%>&display=<%=asmList.getString("asmid") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>&nbsp;
														<%} %>
                                                    	
                                                    	</TR></TABLE>
                                                	</TD>
                                            	</TR>
                                		<%m++; }}catch(java.sql.SQLException e){}
                                		
                                		%>
                                		  <tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Đầu" src="../images/first.gif" style="cursor: pointer;" onclick="View('ddkdForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Đầu" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Trước" src="../images/prev.gif" style="cursor: pointer;" onclick="View('ddkdForm', eval(ddkdForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Trước" src="../images/prev_d.gif" > &nbsp;
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
													<a href="javascript:View('ddkdForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiếp" src="../images/next.gif" style="cursor: pointer;" onclick="View('ddkdForm', eval(ddkdForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiếp" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuối" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuối" src="../images/last.gif" style="cursor: pointer;" onclick="View('ddkdForm', -1, 'view')"> &nbsp;
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
</HTML>

<%
	if(kbh != null){ kbh.close();  kbh = null;}
 	if(dvkd != null){ dvkd.close();  dvkd = null;} 
 	if(kv != null){ kv.close();  kv = null;}
 	if(asmList != null){ asmList.close(); asmList = null;}
 	
	obj.DBClose(); obj = null;
	session.setAttribute("obj",null);
	}%>