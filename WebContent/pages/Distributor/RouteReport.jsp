<%@page import="geso.dms.distributor.beans.Router.IDRouter"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.Router.imp.Router"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>


<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	String loi=(String)session.getAttribute("loi");
	String tungay=(String)session.getAttribute("tungay");
	String denngay=(String)session.getAttribute("denngay");
	Utility util = (Utility) session.getAttribute("util");
	IDRouter obj = (IDRouter)session.getAttribute("obj");
	//ResultSet khuvuc = (ResultSet)obj.getkhuvuc();
	ResultSet npp = (ResultSet)obj.getnpp();
	ResultSet ddkd = (ResultSet)obj.getddkd();
	ResultSet tuyen = (ResultSet)obj.getTuyen();
	ResultSet danhsach = (ResultSet)obj.getdanhsach();
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ 
		  
	int[] quyen = new  int[6];
	quyen = util.Getquyen("RoutereportSvl","", userId);
	%>
   

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="javascript" type="text/javascript">

function khuvucChanging(){
	
	document.forms['rpForm'].action.value="khuvucChanged";
}

function submitform()
{
	document.forms['rpForm'].action.value="thuchien";
	document.forms['rpForm'].submit();
}
function exportToExcel()
{
	document.forms['rpForm'].action.value="export";
	document.forms['rpForm'].submit();
}
function exportToMCP()
{
	document.forms['rpForm'].action.value="exportmcp";
	document.forms['rpForm'].submit();
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

<form name="rpForm" method="post" action="../../RoutereportSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Báo cáo quản tri &gt; Báo cáo khác &gt; Tuyến bán hàng</TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=loi%></textarea>
						</FIELDSET>
				   </TD>
				</tr>	
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle">Tiêu chí xuất báo cáo</LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">



								
							  <TR>
										<TD width="19%" class="plainlabel" >Nhân viên bán hàng</TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
								<select name="ddkdId" >
                                 <option value =""> </option>  
                               <%
                               while(ddkd.next())
                               {
                               if(ddkd.getString("pk_seq").equals(obj.getddkdId())) {
                            	%><option value ="<%=ddkd.getString("pk_seq") %>" selected> <%=ddkd.getString("ten") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=ddkd.getString("pk_seq") %>"> <%=ddkd.getString("ten") %></option>
                              <%}} %>                             </select>		   										</TD>
                                    		</TR>
								</TABLE>									
								</TD>							
								</TR>
							    <TR>
							    <TR>
										<TD width="19%" class="plainlabel" >Tuyến</TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
								<TR><TD>
								<select name="tuyenId"  >
                                 <option value =""> </option>  
                               <%
                               while(tuyen.next())
                               {
                               if(tuyen.getString("ngaylamviec").equals(obj.gettuyenId())) {
                            	%><option value ="<%=tuyen.getString("ngaylamviec") %>" selected> <%=tuyen.getString("ngaylamviec") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=tuyen.getString("ngaylamviec") %>"> <%=tuyen.getString("ngaylamviec") %></option>
                              <%}} %>             
                               </select>		   										
                               </TD>
                               </TR>
                               </TABLE>
                               </TD>
                               </TR>
                               <TR>
                               </TR>
                               </TABLE>
                               </FIELDSET>
                               </TD>
                               </TR>
                               </TABLE>
                                
								<a class="button3" href="javascript:exportToExcel()">
    							Xuất ra excel </a>                            
								<a class="button3" href="javascript:exportToMCP()">
    							Xuất MCP </a> 
			
					</TD>
				</TR>
				
			</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
</BODY>
</HTML>

<%
	try
	{
		if(!(danhsach == null)){ danhsach.close(); danhsach = null; }
		if(ddkd != null){ ddkd.close(); ddkd = null; }
		if(npp != null){ npp.close();  npp = null; }
		if(tuyen != null){ tuyen.close(); tuyen = null; }
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		util=null;
		session.setAttribute("obj",null);
	}catch(java.sql.SQLException e){}
	}
%>