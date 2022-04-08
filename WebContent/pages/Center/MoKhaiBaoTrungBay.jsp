<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.mokhaibaotrungbay.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IMokhaibaotrungbay mkbtbBean = (IMokhaibaotrungbay)session.getAttribute("mkbtbBean"); %>
<% String schemeId = (String)mkbtbBean.getSchemeId(); %>
<% String nppId = (String)mkbtbBean.getNppId(); %>
<% ResultSet scheme = (ResultSet)mkbtbBean.getScheme() ; %>
<% ResultSet npp = (ResultSet)mkbtbBean.getNpp(); 

	int[] quyen = new  int[6];
	quyen = util.Getquyen("MokhaibaotrungbaySvl","", userId);
	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
    	document.forms['mkbtbForm'].submit();
	}	

</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>	

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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="1" height="100%">
	<TR>
		<TD align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>				
					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<TR height="22">
				   				<TD align="left" class="tbnavigation">
				   					&nbsp;Quản lý trưng bày &gt; Cho phép khai báo trưng bày</TD>
				   				<TD  align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp;  </TD> 
		     				</TR>
   
						</TABLE>
					</TD>								
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="1"  cellspacing="1" >
			  <tr>
				<TD align="left" colspan="4" class="legendtitle">
				<FIELDSET>
				<LEGEND class="legendtitle">Thông báo</LEGEND>
			
   				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= mkbtbBean.getMsg() %></textarea>
				</FIELDSET>
				</TD>
			 </tr> 
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Mở khai báo trưng bày</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
							  	<TD width="15%" class="plainlabel">Chọn chương trình</TD>
						  	  	<TD class="plainlabel">
									<SELECT name="schemeId" onChange=submitform()>
								    <option value=""></option>
								      <% try{
								    	  if(scheme != null){
								    	  	while(scheme.next()){ 
								    	  		if(scheme.getString("pk_seq").equals(schemeId)){%>
								    	  			<option value='<%=scheme.getString("pk_seq")%>' selected><%=scheme.getString("scheme") + "_" + scheme.getString("diengiai") %></option>
									 	<%	  	}else{  %> 								    	  	
								     			<option value='<%=scheme.getString("pk_seq")%>'><%=scheme.getString("scheme") + "_" + scheme.getString("diengiai") %></option>
								     		<%	}
								    	  	}
								    	  }
								      }catch(java.sql.SQLException e){} %>	  
                        				</SELECT>						  	  	
						  	  	
						  	  	</TD>
						  </TR>

						  <TR>
							  	<TD class="plainlabel">Chọn Nhà Phân Phối</TD>
						  	  	<TD class="plainlabel">
									<SELECT name="nppId" >
								    <option value=""></option>
								      <% try{
								    	  if(npp != null){
								    	  	while(npp.next()){ 
								    	  		if(npp.getString("pk_seq").equals(nppId)){ 	  %>
								      				<option value='<%=npp.getString("pk_seq")%>' selected><%= npp.getString("ten")%></option>
								      		<%	}else{ %>
								      				<option value='<%=npp.getString("pk_seq")%>' ><%= npp.getString("ten")%></option>
								       		<%	}
								    	  	}
								    	  }
								      }catch(java.sql.SQLException e){} %>	  
                        			</SELECT>						  	  	
						  	  	
						  	  	
						  	  	</TD>
						  </TR>

 						  <TR>							  	
 						   <TD><a class="button2" href="javascript:submitform();">
							<img style="top: -4px;" src="../images/button.png" alt="">Thực hiện</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
	if(scheme != null){ scheme.close(); scheme = null ;} 
	if(npp != null){ npp.close(); npp = null ;} 

	if(mkbtbBean != null)
	{
		mkbtbBean = null;
	}
	session.setAttribute("mkbtbBean", null);
	
	}%>