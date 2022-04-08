<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.distributor.beans.reports.imp.Reports"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	
	Reports rpt=(Reports)session.getAttribute("report");
	ResultSet rscttb=rpt.getRsCTTB();
	
	String loi=rpt.getLoi();
	
	String tungay=rpt.getTuNgay();
	String denngay=rpt.getDenNgay();
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ 
		  
	int[] quyen = new  int[6];
	quyen = util.Getquyen("Disproforcustomernpp","", userId);	
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
<LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>
<script type="text/javascript" src="..scripts/jquery-1.js"></script>
<SCRIPT language="javascript" type="text/javascript">

function submitform()
{
	document.forms['rpForm'].dataerror.value="";
	document.forms['rpForm'].submit();
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

<form name="rpForm" method="post" action="../../Disproforcustomernpp">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
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
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý trưng bày > Báo cáo trưng bày</TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
						<%
							if(loi==null)
								loi="";
						%>
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
							  <LEGEND class="legendtitle">Thời gian xuất báo cáo</LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<TR>
								  	<TD width="19%" class="plainlabel" >Từ ngày</TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<input class="days" type="text" name="tungay" size="20" value = "<%=tungay%>" >
												</TD>
                                    		</TR>
										</TABLE>									</TD>
								<TR>
									<TD class="plainlabel" >Đến ngày </TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  				<TR>
							  					<TD>
													<input class="days" type="text" name="denngay" size="20" value = "<%=denngay%>" >												</TD>
												                                     		</TR>
										</TABLE>									</TD>
								</TR>
								<tr class="plainlabel">
									<TD class="plainlabel" style="top:10px">Chương trình trưng bày  </TD>
								<td>
								<fieldset style="width: 410px; float: left;">
							       <legend>Chương trình trưng bày &nbsp;</legend> 
							       <select name="cttbid" multiple="multiple" style="width:400px;height:300px">
							       <option value="" selected style="padding: 2px" >Chọn hết</option>
			                        <% 	if(rscttb != null) {
				                         while(rscttb.next()) 
				                         {
				                           if(rpt.getcttbid().indexOf(rscttb.getString("pk_seq")) >= 0 ){ %>
				                             <option value="<%= rscttb.getString("pk_seq") %>" selected style="padding: 2px" ><%= rscttb.getString("scheme")+"--"+rscttb.getString("diengiai") %></option>
				                         <%}else { %>
				                         	<option value="<%= rscttb.getString("pk_seq") %>" style="padding: 2px"><%= rscttb.getString("scheme")+"--"+rscttb.getString("diengiai")  %></option>
				                         <%} }}%>       	
                   				 </select>
                   				 </fieldset>
								</td>
								</tr>
							    <TR>
									<TD colspan="2" class="plainlabel">
									<a class="button2" href="javascript:submitform()" >
	<img style="top: -4px;" src="../images/button.png" alt="">Tạo báo cáo</a>&nbsp;&nbsp;&nbsp;&nbsp;                                    </TD>
								</TR>
							</TABLE>
							</FIELDSET>						</TD>
						</TR>
					</TABLE>					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
		if(rscttb!=null){
			rscttb.close();
			rscttb = null;
		}

		rpt.DBclose();
		rpt=null;
		util=null;
		session.setAttribute("report",null);

	}%>