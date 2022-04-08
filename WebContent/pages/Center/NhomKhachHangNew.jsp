<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhomkhachhang.INhomkhachhang" %>
<%@ page  import = "geso.dms.center.beans.nhomkhachhang.imp.Nhomkhachhang" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%  INhomkhachhang nkhBean = (INhomkhachhang)session.getAttribute("nkhBean"); 
	ResultSet khList = (ResultSet)nkhBean.getKhList();
	ResultSet khSelected = (ResultSet)nkhBean.getKhSelected();
	ResultSet vungList = (ResultSet)nkhBean.getVungList();
	ResultSet kvList = (ResultSet)nkhBean.getKvList();
	ResultSet nppList = (ResultSet)nkhBean.getNppList();
	String vungId = (String) nkhBean.getVungId();
	String kvId = (String)nkhBean.getKvId();
	String nppId = (String)nkhBean.getNppId();
	ResultSet rsvitri=nkhBean.getRsViTriCuaHang();
	ResultSet rshangcuahang=nkhBean.getRsHangCuaHang();
	
	ResultSet rsloaicuahang=nkhBean.getRsLoaiCuaHang();
	  %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("NhomkhachhangSvl","",nnId);	
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
function submitform()
{
	document.nkhForm.action.value='new';
    document.forms['nkhForm'].submit();
}

function filterVung()
{
    document.nkhForm.action.value='filter';
    document.nkhForm.kvId.value='0';
    document.nkhForm.nppId.value='0';
    document.forms['nkhForm'].submit();       
}

function filterKv()
{
    document.nkhForm.action.value='filter';
    document.nkhForm.nppId.value='0';
    document.forms['nkhForm'].submit();   
    
}

function filterNpp()
{
    document.nkhForm.action.value='filter';
    document.forms['nkhForm'].submit();       
}

function checkedAll(chk) {
	for(i=0; i<chk.length; i++){
	 	if(document.nkhForm.chon.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
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

<%-- <% Csrf csdr=new Csrf(request,response,true,false,true);%> --%>
<form name="nkhForm" method="post" action="../../NhomkhachhangUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="0">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %> &gt; Tạo mới</TD> 
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
						    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập dữ liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=nkhBean.getMessage()%></textarea>
						<% nkhBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin nhóm khách hàng </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						<TR>
							  	<TD class="plainlabel"><%=ChuyenNgu.get("Tên",nnId) %></TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="tennhom" style="width:400px" value='<%= nkhBean.getTenNhom() %>'></TD>
						  </TR>
							<TR>
							  	<TD class="plainlabel"><%=ChuyenNgu.get("Diễn giải",nnId) %></TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="diengiai" style="width:400px" value='<%= nkhBean.getDiengiai() %>'></TD>
						  </TR>
						  
				  		  <TR>
						    	<TD class="plainlabel"><%=ChuyenNgu.get("Vùng",nnId) %></TD>
						  	  	<TD class="plainlabel">
						  	  	<SELECT name="vungId" onChange="filterVung();">
						  	  					<OPTION value="0" >&nbsp;</OPTION>	
						  	  		<%
						  	  		try{   
						  	  			if(vungList!= null){						  	  			
						   					while (vungList.next()){
						  	  					if (vungList.getString("pk_seq").equals(vungId)){%>
						  	  						<OPTION value="<%= vungList.getString("pk_seq")%>" selected><%= vungList.getString("diengiai")%></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value="<%= vungList.getString("pk_seq")%>" ><%= vungList.getString("diengiai") %></OPTION>
						  	  	<%				  }
						  	  				
						  	  				}
						  	  			
						  	  		}%>						  	  			
						  	  	</SELECT>
						  	  	</TD>
						  	</TR>
						  		
						  	<TR>
						  	  	<TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %></TD>
						  	  	<TD class="plainlabel"><SELECT name="kvId" onchange="filterKv();">
						  	  		<OPTION value="0" ></OPTION>	
						  	  		<% if(kvList!= null){						  	  		
							   				while (kvList.next()){
						  	  					if (kvList.getString("pk_seq").equals(kvId)){%>
						  	  						<OPTION value='<%= kvList.getString("pk_seq")%>' selected ><%= kvList.getString("diengiai") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value='<%= kvList.getString("pk_seq")%>'  ><%= kvList.getString("diengiai") %></OPTION>
						  	  		<%			 }
						  	  				
						  	  				}
						  	  			
						  	  		}%>
						  	  		
						  	  	</SELECT>
						  	  	</TD>
						  	</TR>
						  	
						  	<TR>
						  	  	<TD class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
						  	  	<TD class="plainlabel"><SELECT name="nppId" onchange="filterNpp();">	
						  	  		<OPTION value="0" ></OPTION>
						  	  		<% if(nppList!= null){					  	  			
							   				while (nppList.next()){
							   					
						  	  					if (nppList.getString("pk_seq").equals(nppId)){%>
						  	  						<OPTION value="<%= nppList.getString("pk_seq")%>" selected><%= nppList.getString("ten") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value="<%= nppList.getString("pk_seq")%>" ><%= nppList.getString("ten") %></OPTION>
						  	  	<%				  }
						  	  			
						  	  				}
						  	  			
						  	  		}%>
						  	  	</SELECT>
						  	  	</TD>
						  	</TR>
						  		<TR>
						  	  	<TD class="plainlabel"><%=ChuyenNgu.get("Hạng cửa hàng",nnId) %></TD>
						  	  	<TD class="plainlabel"><SELECT name="HchId" onchange="filterNpp();">	
						  	  		<OPTION value="0" ></OPTION>
						  	  		<% if(rshangcuahang!= null){					  	  			
							   				while (rshangcuahang.next()){
							   					
						  	  					if (rshangcuahang.getString("pk_seq").equals(nkhBean.getHchID())){%>
						  	  						<OPTION value="<%= rshangcuahang.getString("pk_seq")%>" selected><%= rshangcuahang.getString("diengiai") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value="<%= rshangcuahang.getString("pk_seq")%>" ><%= rshangcuahang.getString("diengiai") %></OPTION>
						  	  	<%				  }
						  	  			
						  	  				}
						  	  			
						  	  		}%>
						  	  	</SELECT>
						  	  	</TD>
						  	</TR>
						  		<TR>
						  	  	<TD class="plainlabel"><%=ChuyenNgu.get("Vị trí cửa hàng",nnId) %></TD>
						  	  	<TD class="plainlabel"><SELECT name="VthId" onchange="filterNpp();">	
						  	  		<OPTION value="0" ></OPTION>
						  	  		<% if(rsvitri!= null){					  	  			
							   				while (rsvitri.next()){
							   					
						  	  					if (rsvitri.getString("pk_seq").equals(nkhBean.getVtchID())){%>
						  	  						<OPTION value="<%= rsvitri.getString("pk_seq")%>" selected><%= rsvitri.getString("diengiai") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value="<%= rsvitri.getString("pk_seq")%>" ><%= rsvitri.getString("diengiai") %></OPTION>
						  	  	<%				  }
						  	  			
						  	  				}
						  	  			
						  	  		}%>
						  	  	</SELECT>
						  	  	</TD>
						  	</TR>
						  		<TR>
						  	  	<TD class="plainlabel"><%=ChuyenNgu.get("Loại cửa hàng",nnId) %></TD>
						  	  	<TD class="plainlabel"><SELECT name="LchId" onchange="filterNpp();">	
						  	  		<OPTION value="0" ></OPTION>
						  	  		<% if(rsloaicuahang!= null){					  	  			
							   				while (rsloaicuahang.next()){
							   					
						  	  					if (rsloaicuahang.getString("pk_seq").equals(nkhBean.getLchID())){%>
						  	  						<OPTION value="<%= rsloaicuahang.getString("pk_seq")%>" selected><%= rsloaicuahang.getString("diengiai") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value="<%= rsloaicuahang.getString("pk_seq")%>" ><%= rsloaicuahang.getString("diengiai") %></OPTION>
						  	  	<%				  }
						  	  			
						  	  				}
						  	  			
						  	  		}%>
						  	  	</SELECT>
						  	  	</TD>
						  	</TR>
						  	<TR>
						  		
							  <TD colspan="2" class="plainlabel" ><label>
							  	<% if(nkhBean.getTrangthai().equals("1")){ %>
							    	<input name="trangthai" type="checkbox" value="1" checked >
							    <%}else{ %>
							    	<input name="trangthai" type="checkbox" value="0" >
							    <%} %>
							    <%=ChuyenNgu.get("Hoạt động",nnId) %></label></TD>
						  </TR>
						  
						</TABLE>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">							
								<TR class="tbheader">
									<TH width="28%"><%=ChuyenNgu.get("Mã khách hàng",nnId) %> </TH>
									<TH width="60%"><%=ChuyenNgu.get("Tên khách hàng",nnId) %> </TH>
									<TH width="12%">Chọn<%=ChuyenNgu.get("Chọn",nnId) %>
										<input type="checkbox" name="chon" onClick="checkedAll(document.nkhForm.khachhang)">								
									
									</TH>
								</TR>

								<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
						   	       rs = khSelected;
								   
								   if (!(rs == null)){			
								   		while (rs.next()){								   			
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
										<%  } else {%>
												<TR class= <%= darkrow%> >
										<%  } %>	
												<TD align="left" class="textfont"><%= rs.getString("smartid") %></TD>
												<TD align="center"><div align="left"><%= rs.getString("ten")%></div></TD>
												<TD align="center">
													<input type="checkbox" name="khachhang" value='<%= rs.getString("pk_seq") %>' checked>									
												</TD>

												</TR>
													
									<% 			m++;}
									}	
			
							   	    rs = khList;
									   
									if (!(rs == null)){													    
										while (rs.next()){								   			
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
										<%  } else {%>
												<TR class= <%= darkrow%> >
										<%  } %>	
												<TD align="left" class="textfont"><%= rs.getString("pk_seq") %></TD>
												<TD align="center"><div align="left"><%= rs.getString("ten")%></div></TD>
												<TD align="center">
													<input type="checkbox" name="khachhang" value='<%= rs.getString("pk_seq") %>'>									
												</TD>

												</TR>
													
								<% 			m++;}
										}	
								   }catch(java.sql.SQLException e){}   
									%>	
								   
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
	if(khList != null) khList.close(); 
	if(khSelected != null) khSelected.close();
	if(vungList != null) vungList.close();
	if(kvList != null) kvList.close();
	if(nppList != null) nppList.close() ; %>
<%}%>