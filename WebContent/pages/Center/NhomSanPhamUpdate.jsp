<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhomsanpham.INhomsanpham" %>
<%@ page  import = "geso.dms.center.beans.nhomsanpham.imp.Nhomsanpham" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% INhomsanpham nspBean = (INhomsanpham)session.getAttribute("nspBean"); %>
<% 	ResultSet nspList = (ResultSet)nspBean.getNspList(); 
	ResultSet spList = (ResultSet)nspBean.getSpList(); 
	ResultSet spSelected = (ResultSet)nspBean.getSpSelected();
	ResultSet dvkdList = (ResultSet)nspBean.getDvkdList();
	ResultSet nhList = (ResultSet)nspBean.getNhList();
	ResultSet clList = (ResultSet)nspBean.getCLList();
	String dvkdId = (String) nspBean.getDvkdId();
	String nhId = (String)nspBean.getNhId();
	String clId = (String)nspBean.getClId();
	
	  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="JavaScript" type="text/javascript">
<%if(!nspBean.getView().equals("NPP")){ %>
function submitform()
{
    document.forms["nspForm"].submit();
}

function filterDvkd()
{
    document.nspForm.action.value='filter';
    document.nspForm.nhId.value='0';
    document.nspForm.clId.value='0';
    document.forms["nspForm"].submit();       
}

function filterNh()
{
    document.nspForm.action.value='filter';
    document.nspForm.clId.value='0';
    document.forms["nspForm"].submit();   
    
}

function filterCl()
{
    document.nspForm.action.value='filter';
    document.forms["nspForm"].submit();       
}

function checkedAll(chk) {
	for(i=0; i<chk.length; i++){
	 	if(document.nspForm.chon.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
}
<%}%>
</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="nspForm" method="post" action="../../NhomsanphamUpdateSvl" >
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="nspId" value='<%= nspBean.getId()  %>'>
<input type="hidden" name="action" value="0">
<input type="hidden" name="view" value='<%= nspBean.getView()  %>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Sản phẩm  &gt; Nhóm sản phẩm &gt; Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <%if(!nspBean.getView().equals("NPP")){ %>
						    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<%} %>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=nspBean.getMessage()%></textarea>
						<% nspBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin nhóm sản phẩm</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						<TR>
								<TD width="29%" class="plainlabel" >Mã đồng bộ<FONT class="erroralert"> </FONT></TD>
								<TD class="plainlabel"><INPUT readonly="readonly" type="text" name="ma" style="width:300px" value='<%= nspBean.getMa()%>'></TD>
							</TR>
							<TR>
								<TD width="29%" class="plainlabel" >Tên nhóm sản phẩm<FONT class="erroralert"> </FONT></TD>
								<TD class="plainlabel"><INPUT readonly="readonly" type="text" name="ten" style="width:300px" value='<%= nspBean.getTen()%>'></TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel"><INPUT readonly="readonly" type="text" name="diengiai" style="width:300px" value='<%= nspBean.getDiengiai() %>'></TD>
						  </TR>
							<TR>
							  <TD class="plainlabel">Loại thành viên  <FONT class="erroralert"> </FONT></TD>
							  <TD width="71%" class="plainlabel"><SELECT name="thanhvien" onchange="filterCl()">
                              <% if (nspBean.getThanhvien().equals("1")){ %>                                
                                	<OPTION value="1" selected >Nhóm sản phẩm</OPTION>
                                	<OPTION value="2" >Sản phẩm </OPTION>
                              <%}else{ %>
                                	<OPTION value="1" >Nhóm sản phẩm</OPTION>
                                	<OPTION value="2" selected>Sản phẩm</OPTION>
							  <%} %>                              
                              </SELECT></TD>
						  </TR>
						  
						  <% if (nspBean.getThanhvien().equals("2")){%>					  	  		
						  		<TR>
						  	  	<TD class="plainlabel">Đơn vị kinh doanh</TD>
						  	  	<TD class="plainlabel"><SELECT name="dvkdId" onchange="filterDvkd();">
						  	  		<OPTION value="0" ></OPTION>	
						  	  		<% if(dvkdList!= null){						  	  			
						   					while (dvkdList.next()){
						  	  					if (dvkdList.getString("pk_seq").equals(dvkdId)){%>
						  	  						<OPTION value=<%= dvkdList.getString("pk_seq")%> selected><%= dvkdList.getString("diengiai")%></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value=<%= dvkdList.getString("pk_seq")%> ><%= dvkdList.getString("diengiai") %></OPTION>
						  	  	<%				  }
						  	  				
						  	  				}
						  	  			
						  	  		}%>						  	  			
						  	  	</SELECT>
						  	  	</TD>
						  		<TR>
						  		
						  		<TR>
						  	  	<TD class="plainlabel">Nhãn hàng</TD>
						  	  	<TD class="plainlabel"><SELECT name="nhId" onchange="filterNh();">
						  	  		<OPTION value="0" ></OPTION>	
						  	  		<% if(nhList!= null){						  	  		
							   				while (nhList.next()){
						  	  					if (nhList.getString("pk_seq").equals(nhId)){%>
						  	  						<OPTION value='<%= nhList.getString("pk_seq")%>' selected ><%= nhList.getString("ten") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value='<%= nhList.getString("pk_seq")%>'  ><%= nhList.getString("ten") %></OPTION>
						  	  		<%			 }
						  	  				
						  	  				}
						  	  			
						  	  		}%>
						  	  		
						  	  	</SELECT>
						  	  	</TD>
						  		<TR>
						  		<TR>
						  	  	<TD class="plainlabel">Chủng loại</TD>
						  	  	<TD class="plainlabel"><SELECT name="clId" onchange="filterCl();">	
						  	  		<OPTION value="0" ></OPTION>
						  	  		<% if(clList!= null){					  	  			
							   				while (clList.next()){
							   					
						  	  					if (clList.getString("pk_seq").equals(clId)){%>
						  	  						<OPTION value="<%= clList.getString("pk_seq")%>" selected><%= clList.getString("ten") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value="<%= clList.getString("pk_seq")%>" ><%= clList.getString("ten") %></OPTION>
						  	  	<%				  }
						  	  			
						  	  				}
						  	  			
						  	  		}%>
						  	  	</SELECT>
						  	  	</TD>
						  	  	</TR>
						  	  	<%-- <TR>
						  	  		<TR style="display: none">
						  	  		
						  	  	<TD class="plainlabel">Loại nhóm</TD>
							  <TD width="71%" class="plainlabel"><SELECT name="lnhom">
                              <% if (nspBean.getLoainhom().equals("0")){ %>                                
                                	<OPTION value="0" selected>Nhóm bình thường</OPTION>
                                	<OPTION value="3" >Nhóm chỉ tiêu</OPTION>
                              <%}else if(nspBean.getLoainhom().equals("3")){ %>
                                	<OPTION value="0" >Nhóm bình thường</OPTION>
                                	<OPTION value="3" selected>Nhóm chỉ tiêu</OPTION>
							  <%} else{ %>
							        <OPTION value="" selected></OPTION>
							        <OPTION value="0" >Nhóm bình thường</OPTION>
                                	<OPTION value="3" >Nhóm chỉ tiêu</OPTION> 
                                <%} %>                       
                              	
                              </SELECT></TD>
						  	              
						  	  	</TR> --%>
						  	  	
						  		<TR>
						  		
						  	<%	}%>
							  <TD colspan="2" class="plainlabel" ><label>
							  	<% if(nspBean.getTrangthai().equals("1")){ %>
							    	<input name="trangthai" type="checkbox" value="1" checked >
							    <%}else{ %>
							    	<input name="trangthai" type="checkbox" value="0" >
							    <%} %>
							   Hoạt động</label></TD>
						  </TR>
						  
						</TABLE>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">							
								<TR class="tbheader">
								<% if (nspBean.getThanhvien().equals("1")){ %>
									<TH width="28%">Nhóm sản phẩm</TH>
									<TH width="60%">Diễn giải</TH>
								<%}else{ %>
									<TH width="28%">Mã sản phẩm </TH>
									<TH width="60%">Tên sản phẩm </TH>
								<%}%>								
									<TH width="12%">Chọn
								<%	if(nspBean.getThanhvien().equals("1")){%>			
										<input type="checkbox" name="chon" onClick="checkedAll(document.nspForm.nhom)">
								<%}else{ %>
										<input type="checkbox" name="chon" onClick="checkedAll(document.nspForm.sanpham)">								
								<%} %>
									
									</TH>
								</TR>

								<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
							   if (nspBean.getThanhvien().equals("1")){

								   rs = nspList;
								   
								   if (!(rs == null)){			
									    
								   		while (rs.next()){								   			
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
										<%  } else {%>
												<TR class= <%= darkrow%> >
										<%  } %>	
																														
												<TD align="left" class="textfont"><%= rs.getString("ten") %></TD>
												<TD align="center"><div align="left"><%= rs.getString("diengiai") %></div></TD>
												<TD align="center">
												<% if (rs.getString("nsp_parent_fk").equals(nspBean.getId())){%>
														<input type="checkbox" name="nhom" value='<%= rs.getString("pk_seq") %>' checked>
												<%}else{ %>
														<input type="checkbox" name="nhom" value='<%= rs.getString("pk_seq") %>'>
												<%} %>
												</TD>
											
												</TR>
												
							<% 			m++;}
									}	
									
								}else{
							   	       rs = spSelected;
									   
									   if (!(rs == null)){			
										    
									   		while (rs.next()){								   			
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
											<%  } else {%>
													<TR class= <%= darkrow%> >
											<%  } %>	
													<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
													<TD align="center"><div align="left"><%= rs.getString("ten")%></div></TD>
													<TD align="center">
														<input type="checkbox" name="sanpham" value='<%= rs.getString("pk_seq") %>' checked>									
													</TD>

													</TR>
													
								<% 			m++;}
										}	
			
							   	       rs = spList;
									   
									   if (!(rs == null)){			
										    
									   		while (rs.next()){								   			
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
											<%  } else {%>
													<TR class= <%= darkrow%> >
											<%  } %>	
													<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
													<TD align="center"><div align="left"><%= rs.getString("ten")%></div></TD>
													<TD align="center">
														<input type="checkbox" name="sanpham" value='<%= rs.getString("pk_seq") %>'>									
													</TD>

													</TR>
													
								<% 			m++;}
										}	
									   
								}	%>	
								   
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
<% 	if(nspList != null) nspList.close(); 
	if(spList != null) spList.close(); 
	if(spSelected != null) spSelected.close();
	if(dvkdList != null) dvkdList.close();
	if(nhList != null) nhList.close();
	if(clList != null) clList.close() ; %>
<%}%>