<%@page import="geso.dms.center.beans.nganhhang.INganhHang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% INganhHang nhBean = (INganhHang)session.getAttribute("nhBean"); %>
<% ResultSet dvkdList = (ResultSet)nhBean.getDvkdList(); %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<SCRIPT language="javascript" type="text/javascript">
function confirmLogout(){
   if(confirm("Ban co muon dang xuat?"))
   {
	top.location.href = "home.jsp";
   }
   return
}
function submitform()
{
    document.forms['nhForm'].submit();
}
function saveform()
{
	var kbhTen = document.getElementById('kbh');
	var diengiai = document.getElementById('diengiai');
	var dvkd = $("#dvkdSlt").val().trim();
	if(dvkd === "0") {
		alert("Bạn chưa chọn đơn vị kinh doanh!");
		return;
	}
	if(diengiai.value === "")
	{
		alert('Bạn chưa nhập diễn giải');
		return;
	}
	if(kbhTen.value === ""){
		alert('Bạn chưa nhập tên ngành hàng');
		return;
	}	
	document.forms['nhForm'].action.value = 'save';
	document.forms['nhForm'].submit();
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

<form name='nhForm' method="post" action="../../NganhHangUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="action" value='1'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<input type="hidden" name="id" value='<%= nhBean.getId() %>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   	&nbsp; Dữ liệu nền > Sản phẩm > Ngành hàng> Cập nhật</TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn<%=userTen %>&nbsp;  </TD> 
						    </tr>
   
						   	<tr>
						   		<TD align="left" height="5" colspan="4" class=""></td>
   
  							</tr>
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../NganhHangSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
		    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= nhBean.getMessage() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>
				
				  	<tr>
					   <TD align="left" colspan="4" class="legendtitle">
							<FIELDSET>
							<LEGEND class="legendtitle">Thông tin ngành hàng
							</LEGEND>
							<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
								<TR>
								  <TD width="15%" class="plainlabel required" >Ngành hàng <FONT class="erroralert"></FONT></TD>
								  <TD  class="plainlabel" ><INPUT name="ten" id="kbh"
									type="text" value='<%= nhBean.getTen() %>' size="20">
								</TD>
							  	  <TD class="plainlabel" ></TD>
								  <TD class="plainlabel" >
<!-- 												  <select name="thuexuat" onChange="submitform();"> -->
												  
<%-- 												<% if (nhBean.getThuexuat()==10){%> --%>
<!-- 												  	<option value="10" selected>10%</option> -->
<!-- 												  	<option value="5">5%</option>							   -->
<%-- 												<%}else{%>																					 	  --%>
<!-- 												  	<option value="10" >10%</option> -->
<!-- 												  	<option value="5" selected>5%</option> -->
<%-- 												<%}%> --%>
<!-- 										          </select> -->
								  </TD>
							  	</TR>
								<TR>
								  <TD class="plainlabel required" >Diễn giải </TD>
								  <TD class="plainlabel" >
								  	<INPUT name="diengiai" id="diengiai" type="text" value='<%= nhBean.getDiengiai() %>' size="80">
								  </TD>
							 	  <TD class="plainlabel required">Đơn vị kinh doanh</TD>
				                  <TD class="plainlabel">
				                      <select name="dvkd" id="dvkdSlt">
				                        	<option value="0"  ></option>
											<% 
				                                try{											
				                                    while (dvkdList.next()){%>													
				                                        <%	if(dvkdList.getString("pk_seq").equals(nhBean.getDvkdId())){ %>											
				                                                <option value= <%=dvkdList.getString("pk_seq")%> selected="selected"><%= dvkdList.getString("donvikinhdoanh") %></option>															
				                                            <%}else{%>
				                                                <option value= <%=dvkdList.getString("pk_seq")%> ><%= dvkdList.getString("donvikinhdoanh") %></option>																																		
				                                        <%	}
				                                    }
				                                } catch(java.sql.SQLException e){
				                                    
				                                }
				                            %>
				                        </select>
				                  </TD>
							  	</TR>
							
								<TR>
								  <TD  class="plainlabel" colspan="4"><label>
										<%  if (nhBean.getTrangthai().equals("1")){%>
										  	<input name="trangthai" type="checkbox" value ="1" checked="checked">
										<%} else {%>
											<input name="trangthai" type="checkbox" value ="1">
										<%} %>
									    Hoạt động</label>
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
	nhBean.DBClose();
	}%>