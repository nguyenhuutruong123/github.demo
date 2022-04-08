<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.dongboctkm.IDongboctkm" %>
<%@ page  import = "geso.dms.center.beans.dongboctkm.imp.Dongboctkm" %>
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
		response.sendRedirect("/DTL/index.jsp");
	}else{ %>

<% IDongboctkm nkmBean = (IDongboctkm)session.getAttribute("nkmBean"); 

	  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>OPV - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">


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


<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	var tungay = document.getElementById('tungay').value;
	var denngay = document.getElementById('denngay').value;
	if(tungay == '')
	{
		 alert('Vui lòng chon từ ngày');
		 return;
	}
	
	if(denngay == '')
	{
		 alert('Vui lòng chon đến ngày');
		 return;
	}
	if(tungay > denngay)
	{
		 alert('Kiểm tra thời gian nhóm sản phẩm');
		 return;
	}
	
    document.forms["nkmForm"].submit();
}
function xuatexel()
{
	document.nkmForm.action.value='xuatexel';
    document.forms["nkmForm"].submit();
}

function saveform()
{
	document.nkmForm.action.value='saveform';
    document.forms["nkmForm"].submit();
}

function filterDvkd()
{
    document.nkmForm.action.value='filter';
    document.nkmForm.nhId.value='0';
    document.nkmForm.clId.value='0';
    document.forms["nkmForm"].submit();       
}

function filterNh()
{
    document.nkmForm.action.value='filter';
    document.nkmForm.clId.value='0';
    document.forms["nkmForm"].submit();   
    
}

function filterCl()
{
    document.nkmForm.action.value='filter';
    document.forms["nkmForm"].submit();       
}

function checkedAll(chk) {
	for(i=0; i<chk.length; i++){
	 	if(document.nkmForm.chon.checked==true){
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

	<form name="nkmForm" method="post"
		action="../../DongboctkmSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%=userId%>'>  <input
			type="hidden" name="action" value="0">

		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Chương trình khuyến mãi &gt; Khai báo &gt;Đồng bộ > Cập nhật</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%=userTen %>&nbsp;</TD>
									</tr>
								</table></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										
										<TD width="2" align="left"></TD>
										<TD width="30" align="left"><A
											href="javascript: saveform()"><IMG
												src="../images/Save30.png" title="Luu lai" alt="Luu lai"
												border="1" style="border-style: outset">
										</A>
										</TD>
										<TD>                                            </TD>
										
										<TD width="2" align="left"></TD>
										<TD width="30" align="left"><A
											href="javascript: xuatexel()"><IMG
												src="../images/excel.gif" width="30" title="Xuất exel"
												alt="Luu lai" border="1" style="border-style: outset">
										</A>
										</TD>
										<TD>&nbsp;</TD>
										<TD>&nbsp;</TD>

									</TR>
								</TABLE></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu</LEGEND>

									<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%"
										readonly="readonly" rows="1"><%= nkmBean.getMessage() %></textarea>
									<% nkmBean.setMessage(""); %>
								</FIELDSET></TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông
										tin đồng bộ</LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										
										<TR>
											<TD width="30%" class="plainlabel">Từ ngày
												<FONT class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel">
												<input readonly  class="days" type="text"  id="tungay"  name="tungay" value='<%=nkmBean.getTungay() %>'  size="10" >
											</TD>
										</TR>
										<TR>
											<TD width="30%" class="plainlabel">Đến ngày
												<FONT class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel">
												<input readonly  class="days" type="text" id="denngay" name="denngay" value='<%=nkmBean.getDenngay() %>'  size="10" >
											</TD>
										</TR>
									</TABLE>
									
								</FIELDSET></TD>
						</TR>
					</TABLE></TD>
			</TR>
		</TABLE>
	</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%  
	%>
<%}%>