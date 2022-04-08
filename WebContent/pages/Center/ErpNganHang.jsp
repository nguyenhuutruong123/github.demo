<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.beans.nganhang.IErpNganHangList" %>
<%@ page  import = "geso.dms.center.beans.nganhang.imp.*" %>

<%
	IErpNganHangList nhList = (IErpNganHangList)session.getAttribute("nhList");
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen"); 
	ResultSet NhList = (ResultSet)nhList.getNhList();
	
	Utility util = (Utility) session.getAttribute("util");
	int[] quyen = new  int[5];
	quyen = util.Getquyen("114",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);	
	System.out.println(quyen[4]);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>Best - Ngan Hang</title>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">

<script language="javascript" type="text/javascript">

function xoaform()
{
    document.nhListForm.MA.value = "";
    document.nhListForm.TEN.value = "";
    submitform();
}

function submitform()
{
	document.forms['nhListForm'].action.value= 'search';
	document.forms['nhListForm'].submit();
}

function newform()
{
	document.forms['nhListForm'].action.value= 'new';
	document.forms['nhListForm'].submit();
}

function thongbao()
{
	 var tt = document.forms["nhListForm"].msg.value;
	 if(tt.length>0)
    	alert(document.forms["nhListForm"].msg.value);
	}

</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="nhListForm" method="post" action="../../ErpNganHangSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="userTen" value='<%=userTen%>'> 
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%= nhList.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<table width="100%" cellpadding="0" cellspacing="1">		
				<tr>
					<td align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kinh doanh &gt; Ngân hàng
						   </TD>
						   <td align = "right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td>
						  </tr>
 					  </table>
					</td>
				</tr>
				<tr>
					<td>
					<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td width="100%" align="center" >
							<fieldset>
							  <legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>
							<table width="100%" cellpadding="6" cellspacing="0">
								<tr>
								  	<td class="plainlabel">Mã</td>
								    <TD class="plainlabel"><input type="text" name="MA" value="<%=nhList.getMA() %>" onchange="submitform()"></td>
						      	</tr>
						      	
						      	<tr>
								  	<td class="plainlabel">Tên</td>
								    <TD class="plainlabel"><input type="text" name="TEN" value="<%=nhList.getTEN() %>" onchange="submitform()"></td>
						      	</tr>

							    <tr>
									<td colspan="2" class="plainlabel">
									<a class="button2" href="javascript:xoaform()">
									<img style="top: -4px;" src="../images/button.png" alt="">Nhập Lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
									</td>									
								</tr>
								
							</table>

							</fieldset>
						</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td width="100%">
						<fieldset>
						<legend class="legendtitle">&nbsp;Ngân hàng&nbsp;&nbsp;&nbsp;
						<%if(quyen[0]!=0){ %>
						<a class="button3" href="javascript:newform()">
    					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>   
    					<%} %>                         
					 	</legend>
	
		   				<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<tr>
			  	   				<td align="left" colspan="4" class="legendtitle">
									<table width="100%" border="0" cellspacing="1" cellpadding="4">
										<tr class="tbheader">
											<th width="6%">Mã</th>
											<th width="30%">Tên</th>	
											<th width="9%">Trạng thái</th>										
											<th width="5%">Ngày tạo</th>
											<th width="9%">Người tạo</th>
											<th width ="5%">Ngày sửa</th>											
											<th width ="9%">Người sửa</th>											
											<th width="4%" align="center">&nbsp;Tác vụ</th>					
										</tr>
				
									<%
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(NhList!=null)
									while (NhList.next())
									{
										if(m % 2 != 0) {%>
										<tr class = <%=lightrow%> >
									<%} else {%>
										<tr class = <%=darkrow%> >
									<%}%>
									
										<td align="center"><%=NhList.getString("MA")%> </td>
										<td align="center"><%=NhList.getString("TEN")%> </td>
										<% 
										if(NhList.getString("trangthai").equals("1")){
										%>	
											<td align="center">Hoạt Động </td>
											<%
										}else{
											%>
											<td align="center">Ngưng Hoạt Động </td>
											<% 
										}
										%>								
										<td align="center"><%=NhList.getString("NGAYTAO")%> </td>
										<td align="center"><%=NhList.getString("NGUOITAO")%> </td>
										<td align="center"><%=NhList.getString("NGAYSUA")%> </td>										
										<td align="center"><%=NhList.getString("NGUOISUA")%> </td>
										
										
										<td align = "center">
											<table border = 0 cellpadding="0" cellspacing="0">
												<tr>
												<td> 
												 	<%if(quyen[2]!=0){ %>
													<a href = "../../ErpNganHangUpdateSvl?userid=<%=userId%>&update=<%=NhList.getString("PK_SEQ")%>"><img src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>
													<%} %>
												</td>
												<td>&nbsp;&nbsp;</td>
												<td>
													<%if(quyen[1]!=0){ %>
													<a href = "../../ErpNganHangSvl?userid=<%=userId%>&delete=<%=NhList.getString("PK_SEQ")%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border="0" onclick="if(!confirm('Bạn có muốn xóa ngân hàng này ?')) return false; "></a></td>
													<%} %>
												</tr>
											</table>				
										</tr>
									
									<% 	m++;
									}%>									
									
										<tr>
											<td height="26" colspan = "11" align="center" class="tbfooter">&nbsp;	</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>

					</fieldset>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%
nhList.DBClose();%>