<%@page import="java.util.Hashtable"%>
<%@page import="geso.dms.distributor.beans.banggiasieuthi.imp.KhachHang_Bgst"%>
<%@page import="geso.dms.distributor.beans.banggiasieuthi.IKhachHang_Bgst"%>
<%@page import="geso.dms.distributor.beans.banggiasieuthi.IBanggiasieuthi"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.banggiamuanpp.IBanggiamuanpp_npp" %>
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

<% IBanggiasieuthi bgsieuthikhBean = (IBanggiasieuthi)session.getAttribute("assign"); %>
<% List<IKhachHang_Bgst> listkh =  bgsieuthikhBean.getListKh(); %>
<%ResultSet rsloaicuahang= bgsieuthikhBean.getrsloaicuahang();%>
<%ResultSet rshangcuahang= bgsieuthikhBean.getrshangcuahang();%>
<%ResultSet rsvtch= bgsieuthikhBean.getrsvitricuahang();%>
<%ResultSet rsquanhuyen= bgsieuthikhBean.getrsquanhuyen();%>
<%ResultSet rstinhthanh= bgsieuthikhBean.getrstinhthanh();%>
<%ResultSet rsddkd= bgsieuthikhBean.getrsddkd();%>
<%Hashtable htkhcobg= bgsieuthikhBean.getDSKH_CO_BGST();%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
function submitform()
{   
	document.forms['bgmuanppForm'].action.value='searchkh';
   document.forms["bgmuanppForm"].submit();
}

 function saveform()
{
	document.forms['bgmuanppForm'].action.value='savebgkh';
    document.forms["bgmuanppForm"].submit();
}
 function checkedAll() {
		var nppIds = new Array(<%= bgsieuthikhBean.getkhachhangString() %>);	
		for (var i =0; i < nppIds.length; i++) 
	 	{
		 	var cb = "chbox" + nppIds[i];
		 	var objCheckBoxes = document.forms["bgmuanppForm"].elements[cb];
			if (document.forms["bgmuanppForm"].elements["chon"].checked == false){
				objCheckBoxes.checked = false;
			}else{
				objCheckBoxes.checked = true;
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

<form name="bgmuanppForm" method="post" action="../../BanggiasieuthiSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=bgsieuthikhBean.getUserId() %>'>
<input type="hidden" name="nppId" value='<%=bgsieuthikhBean.getNppId() %>'>
<input type="hidden" name="action" value='assign'>
<input type="hidden" name="id" value='<%= bgsieuthikhBean.getId() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; Dữ liệu nền sản phẩm &gt; Bảng giá bán siêu thị &gt;Phân bảng giá cho khách hàng</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=bgsieuthikhBean.getNppTen() %>&nbsp;  </TD></tr>
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
						    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
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
				
	    			<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" rows="1"><%=bgsieuthikhBean.getMessage()%></textarea>
					<% bgsieuthikhBean.setMessage(""); %>
					</FIELDSET>
			   </TD>
			</tr>			

		 	<TR>
				<TD >
			        <FIELDSET>
			        <LEGEND class="legendtitle">&nbsp;Bảng giá bán cho NPP </LEGEND>
					<TABLE width="100%"cellpadding="0" cellspacing="0">
						<TR>
							<TD>				
								<TABLE border="0" width="100%" cellpadding="5" cellspacing="0">
									<TR>
													<TD width="15%" class="plainlabel">Tên bảng giá <FONT class="erroralert">*</FONT></TD>
													<TD  class="plainlabel" colspan="3"><INPUT name="bgTen" value="<%= bgsieuthikhBean.getTenbanggia() %>"  type="text" style="width:300px" readonly="readonly"/></TD>
							
									
									</TR>
									<TR>
									
									<TD width="16%" class="plainlabel">Hạng khách hàng</TD>
									<TD width="38%" class="plainlabel">
										<SELECT name="hchid" onChange = "submitform();">
										  <option value=""> </option>
										  <% try{ while(rshangcuahang.next()){ 
								    			if(rshangcuahang.getString("pk_seq").equals(bgsieuthikhBean.getHangCuaHang())){ %>
								      				<option value='<%=rshangcuahang.getString("pk_seq")%>' selected><%=rshangcuahang.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=rshangcuahang.getString("pk_seq")%>'><%=rshangcuahang.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>	 
                                        </SELECT>
                                        
								  </TD>
								  <TD width="16%" class="plainlabel">Tỉnh thành</TD>
									<TD width="38%" class="plainlabel">
										<SELECT name="tinhthanhid" onChange = "submitform();">
										  <option value=""> </option>
										  <% try{ while(rstinhthanh.next()){ 
								    			if(rstinhthanh.getString("pk_seq").equals(bgsieuthikhBean.getTinhthanh())){ %>
								      				<option value='<%=rstinhthanh.getString("pk_seq")%>' selected><%=rstinhthanh.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=rstinhthanh.getString("pk_seq")%>'><%=rstinhthanh.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>	 
                                        </SELECT>
                                        
								  </TD>
								</TR>
								<TR>
								  	<TD class="plainlabel">Loại khách hàng</TD>
								  	<TD  class="plainlabel" ><SELECT name="lchid"  onChange = "submitform();">
								    	<option value=""> </option>
								    	<% try{ while(rsloaicuahang.next()){ 
								    			if(rsloaicuahang.getString("pk_seq").equals(bgsieuthikhBean.getLoaiCuahang())){ %>
								      				<option value='<%=rsloaicuahang.getString("pk_seq")%>' selected><%=rsloaicuahang.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=rsloaicuahang.getString("pk_seq")%>'><%=rsloaicuahang.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
										</SELECT>
									</TD>
									<TD width="16%" class="plainlabel">Quận huyện</TD>
									<TD width="38%" class="plainlabel">
										<SELECT name="quanhuyenid" onChange = "submitform();">
										  <option value=""> </option>
										  <% try{ while(rsquanhuyen.next()){ 
								    			if(rsquanhuyen.getString("pk_seq").equals(bgsieuthikhBean.getQuanhuyen())){ %>
								      				<option value='<%=rsquanhuyen.getString("pk_seq")%>' selected><%=rsquanhuyen.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=rsquanhuyen.getString("pk_seq")%>'><%=rsquanhuyen.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>	 
                                        </SELECT>
                                        
								  </TD>
								</TR>
								<TR>
									
									<TD class="plainlabel">Vị trí</TD>
	                                <TD  class="plainlabel"><SELECT name="vtchid" onChange = "submitform();">
    		                            <option value=""> </option>
            		                    <% try{ while(rsvtch.next()){ 
								    			if(rsvtch.getString("pk_seq").equals(bgsieuthikhBean.getVitricuahang())){ %>
								      				<option value='<%=rsvtch.getString("pk_seq")%>' selected><%=rsvtch.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=rsvtch.getString("pk_seq")%>'><%=rsvtch.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
                                        </SELECT></TD>
                                        
                                        <TD class="plainlabel">Nhân viên bán hàng</TD>
	                                <TD  class="plainlabel"><SELECT name="ddkdid" onChange = "submitform();">
    		                            <option value=""> </option>
            		                    <% try{ while(rsddkd.next()){ 
								    			if(rsddkd.getString("pk_seq").equals(bgsieuthikhBean.getddkdid())){ %>
								      				<option value='<%=rsddkd.getString("pk_seq")%>' selected><%=rsddkd.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=rsddkd.getString("pk_seq")%>'><%=rsddkd.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
                                        </SELECT></TD>
								</TR>
									
								</TABLE>
							</TD>
						</TR>
				</TABLE>
				</FIELDSET>
						<TABLE width="100%" border="1" cellspacing="1" cellpadding="2">							
								<TR class="tbheader">
									<TH width="28%">Mã khách hàng</TH>
									<TH width="60%">Tên khách hàng </TH>
									<TH width="12%">Chọn						
										<input type="checkbox" name="chon" onClick="checkedAll()">																	
									</TH>
								</TR>

								<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
								   
								   int k= listkh.size();
										    int j=0;
									  	while (j < k){	
									  		 IKhachHang_Bgst kh=listkh.get(j);
											if(!htkhcobg.containsKey(kh.getIdKh())){
												%>			
												<TR class= <%=lightrow%> >
										<%  } else {%>
												<TR class= <%= darkrow%> >
										<%  } %>	
										
												<TD align="left" class="textfont"><%= kh.getIdKh() %></TD>
												<TD align="center"><div align="left"><%= kh.getTdKh()%></div></TD>
												<TD align="center">
													<%if(kh.getCheck().equals("1")){ %>
													<input type="checkbox" name='<%= "chbox" + kh.getIdKh() %>' value='<%= kh.getIdKh() %>' checked="checked" >
													<%}else{ %>
													<input type="checkbox" name='<%= "chbox" + kh.getIdKh() %>' value='<%= kh.getIdKh() %>' >
													<%} %>									
												</TD>

												</TR>
													
								<% 		j++;}
									%>	
								   
						</TABLE>							
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
	<script type="text/javascript">
 
 	
 </script>
	
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<% 	


	try{
		
		if(rs != null)
			rs.close();
		if(rsddkd != null)
			rsddkd.close();
		if(rshangcuahang != null)
			rshangcuahang.close();
		if(rsloaicuahang != null)
			rsloaicuahang.close();
		if(rsquanhuyen != null)
			rsquanhuyen.close();
		if(rstinhthanh != null)
			rstinhthanh.close();
		if(rsvtch != null)
			rsvtch.close();
		if(bgsieuthikhBean != null){
			bgsieuthikhBean.DBclose();
			bgsieuthikhBean = null;
		}
		if(listkh!=null){
			listkh.clear();
			listkh=null;
		}
		if(htkhcobg!=null){
			htkhcobg.clear();
			htkhcobg=null;
		}
		if(	bgsieuthikhBean!=null)
		{
			bgsieuthikhBean.DBclose();
		}
	}
	catch (SQLException e) {}

%>
<%}%>