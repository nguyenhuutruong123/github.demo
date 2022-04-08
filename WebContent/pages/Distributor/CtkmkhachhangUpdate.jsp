<%@page import="java.sql.SQLException"%>
<%@page import="geso.dms.distributor.beans.ctkmkhachhang.ICtkmkhachhang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.ArrayList"%>
<%@ page  import = "java.util.List"%>
<%@ page  import = "java.sql.ResultSet"%>
<%@ page  import = "java.util.Hashtable" %>

<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

	 		
<% ICtkmkhachhang obj = (ICtkmkhachhang)session.getAttribute("obj"); %>
<% ResultSet Dskh = obj.getDskh(); %> 
<% ResultSet Schemes = (ResultSet)obj.getSchemes(); %>
<% ResultSet ddkd = (ResultSet)obj.getDdkdList(); %>
<% Hashtable<Integer, String> ddkdIds = (Hashtable<Integer, String>)obj.getDdkdIds(); %>
      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<script type="text/javascript" src="../scripts/jquery.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-1.js"></script>
		<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	    <script type="text/javascript">
	        $(document).ready(function(){
	            $(".button").hover(function(){
	                $(".button img")
	                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
	                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
	                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
	            });
	        }); 
	</script>
	
<SCRIPT language="javascript" type="text/javascript">
  
</SCRIPT>
<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["khForm"].submit();
	    document.forms["khForm"].action.value ='submit';
	}
	
	function reseach()
	{   
		document.forms["khForm"].action.value ='seach';
	    submitform();
	}
	
	function save()
	{ 
		document.forms["khForm"].action.value ='save';
	    submitform();
	}
	
	function checkall()
	 { 
		var khachhang = document.getElementsByName("khachhang");
		for(i=0; i<khachhang.length; i++)
		{
		 	if(document.khForm.chon.checked ==true)
		 	{
		 	  	khachhang[i].checked = true;
			}
		 	else
		 	{
				khachhang[i].checked =false;
			}
		 }
	 }
	
	function UnCheckedAll()
	{
		var selectAll = document.getElementById("khachhang");
		selectAll.checked = false;
	}
	
</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="khForm" method="post" action="../../CtkmkhachhangUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="nppId" type="hidden" value='<%=obj.getnppId()%>' size="30">
<INPUT name="action" type="hidden" value='' size="30">
<INPUT name="id" type="hidden" value='<%= obj.getId() %>' size="30">
<INPUT name="ctkmid" type="hidden" value='<%=obj.getCtkmId()%>' size="30">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		&nbsp;Quản lý khuyến mãi &gt; Khách hàng hưởng khuyến mãi > cập nhật </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=obj.getnppTen() %> &nbsp;</td> 
					      </tr>
   
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript:save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD>&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
				  	<tr>
						<TD align="left" colspan="4" class="legendtitle">
							<FIELDSET>
								<LEGEND class="legendtitle">Thông báo </LEGEND>
		    					<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1" ><%=""%></textarea>
							</FIELDSET>
					   </TD>
					</tr>			
				  	<tr>
						   <TD align="left" colspan="4">
								<FIELDSET>
								<LEGEND class="legendtitle">Tiêu chí tìm kiếm</LEGEND>
								<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
								  <tr>
			                    	<td class="plainlabel" width="20%" valign="middle">Chương trình khuyến mại</td>
			                    	<td class="plainlabel">
			                            <i><%= obj.getTen() %></i>
			                    	</td>               
			                    </tr>       
			                    <tr>
			                    	<td class="plainlabel" width="20%" valign="middle">Từ ngày</td>
			                    	<td class="plainlabel">
			                            <i><%= obj.getTungay() %></i>
			                    	</td>                
			                    </tr>     
			                    <tr>
			                    	<td class="plainlabel" width="20%" valign="middle">Đến ngày</td>
			                    	<td class="plainlabel">
			                            <i><%= obj.getDenngay() %></i>
			                    	</td>               
			                    </tr> 
								  <TR>
									  <TD width="20%" class="plainlabel" >Diễn giải </TD>
									  <TD width="80%" class="plainlabel" align="left" ><%= obj.getDiengiai() %></TD>
								  </TR>
								  <TR>
									  <TD width="20%" class="plainlabel" >Nhân viên bán hàng </TD>
									  <TD width="80%" class="plainlabel"></TD>
								  </TR>
								   <TR>
									  <TD width="100%" colspan="2" >
									  		<table width="100%" cellspacing="1px" cellpadding="4px">
							             	<tr class="tblight">
							                	<th align="left" class="plainlabel">Tên Nhân viên bán hàng</th>
							                	<th align="left" class="plainlabel">Địa chỉ</th>
							                	<th align="left" class="plainlabel">Điện thoại</th>
							                    <th width="10%" align="center" class="plainlabel">Chọn</th>
							                </tr>
							                <% 
							                	if(ddkd != null)
							                	{
							                	int n = 0;
												try
												{ 
													while(ddkd.next())
													{ 
													if (n % 2 == 0){ %>
														<TR class= "tblightrow" >
													<%}else{ %>
														<TR class= "tbdarkrow" >
													<%} %>
														<TD align="left"><%= ddkd.getString("ddkdTen") %></TD>
														<TD align="left"><%= ddkd.getString("diachi") %></TD>
														<TD align="left"><%= ddkd.getString("dienthoai") %></TD>
														<% if(ddkdIds.contains(ddkd.getString("ddkdId"))){ %>
															   <TD align="center"><input name="ddkdIds" type="checkbox" value ="<%= ddkd.getString("ddkdId") %>" checked ></TD>
														<%}else{%>
															   <TD align="center"><input name="ddkdIds" type="checkbox" value ="<%= ddkd.getString("ddkdId") %>" ></TD>
														<%} %> 
							                    	</TR> 
							                    <% n++; } ddkd.close(); } catch(java.sql.SQLException e){}} %>
											   	
							             </table>  
									  </TD>
								  </TR>
								  <TR>
									  <TD width="100%" colspan="2" align="right">
									  <a class="button2" href="javascript:submitform();">
											<img style="top: -4px;" src="../images/button.png" alt=""> Hiển thị khách hàng</a>&nbsp;&nbsp;&nbsp;&nbsp;
									  </TD>
									  
								  </TR>
            				</TABLE>
								</FIELDSET>
						</TD>
					</TR>
					<TR>
						<TD align="left" colspan="4">
							<FIELDSET>
							<LEGEND class="legendtitle">
								Chọn khách hàng
							</LEGEND>
							<TABLE class="tblight" width="100%" cellspacing="1" cellpadding="6">
								<TR>
								<TH width="10%" class="plainlabel">Mã khách hàng </TH>
								<TH width="20%" class="plainlabel">Tên khách hàng </TH>
								<TH width="40%" class="plainlabel">Địa chỉ </TH>
								<TH width="20%" class="plainlabel">Điện thoại </TH>
								<TH width="10%" class="plainlabel">Chọn <input name="chon" type="checkbox" value ="1" onclick ="checkall()"></TH>
								</TR>
								
								<%
								if(Dskh != null)
								{
								int m = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								try
								{ 
									while (Dskh.next())
									{							
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
										<TD align="left"><%= Dskh.getString("smartId") %> </TD>
	                                    <TD align="left"><%= Dskh.getString("ten") %> </TD>
	                                    <TD align="left"><%= Dskh.getString("diachi") %> </TD>
	                                    <TD align="left"><%= Dskh.getString("dienthoai") %> </TD>
										<TD align="center">
										<% if (Dskh.getString("chon").equals("1")){ %>
										    <input name="khachhang" type="checkbox" value="<%=Dskh.getString("pk_seq")%>" checked onchange="UnCheckedAll()">
										<%}else{ %>
											<input name="khachhang" type="checkbox" value="<%=Dskh.getString("pk_seq")%>" onchange="UnCheckedAll()">
										<%}%>
										</TD></TR>
									<% m++; } } catch(java.sql.SQLException e){} } %> 
								
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

	try{
		if(Dskh != null)
			Dskh.close();
		if(Schemes != null)
			Schemes.close();
		if(ddkd != null)
			ddkd.close();
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
	
	}
	catch (SQLException e) {}

%>
<%}%>