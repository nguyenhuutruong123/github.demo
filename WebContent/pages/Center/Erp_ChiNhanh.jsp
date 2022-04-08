<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.beans.erp_chinhanh.IErp_chinhanhList" %>
<%@ page  import = "geso.dms.center.beans.erp_chinhanh.imp.Erp_chinhanhList" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{  
		int[] quyen = new  int[5];
		quyen = util.Getquyen("115",userId);
		
		
		%>


<% 	IErp_chinhanhList cnList = (IErp_chinhanhList)session.getAttribute("cnList");
	ResultSet Rscn = cnList.getRscn();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>Best - Chi Nhánh</title>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
      $(document).ready(function() {            
            $( ".days" ).datepicker({                     
                        changeMonth: true,
                        changeYear: true                    
            });            
      });   
</script>


<script language="javascript" type="text/javascript">

function xoaform()
{
    document.cnForm.ma.value = "";
    document.cnForm.ten.value = "";
    document.cnForm.tt.value = "";
    submitform();
}

function submitform()
{
	document.forms['cnForm'].action.value= 'search';
	document.forms['cnForm'].submit();
}

function newform()
{
	document.forms['cnForm'].action.value= 'new';
	document.forms['cnForm'].submit();
}


function thongbao()
{
	 var tt = document.forms["cnForm"].msg.value;
	 if(tt.length>0)
    	alert(document.forms["cnForm"].msg.value);
	}

</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="cnForm" method="post" action="../../Erp_ChiNhanhSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%= cnList.getMsg() %>'>
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
						   <td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; &nbsp;Kinh doanh &gt; Chi nhánh					   
						   </td>
   
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
								  	<td class="plainlabel" style="width:15%">Mã chi nhánh</td>
								    <TD class="plainlabel"><input type="text" name="ma" value="<%=cnList.getMA() %>" onchange="submitform()"></td>
						      	</tr>
						      	
						      	<tr>
								  	<td class="plainlabel" style="width:15%">Tên chi nhánh</td>
								    <TD class="plainlabel"><input type="text" name="ten" value="<%= cnList.getTEN() %>" onchange="submitform()"></td>
						      	</tr>
								
								<tr>
						      		<td class = "plainlabel" style="width:15%">Trạng thái</td>
									<td class = "plainlabel">
										<select name = "tt" onchange = "submitform()">
										
										<%if(cnList.getTRANGTHAI().equals("1")  ) {%>
										<option value = "1" selected="selected">Hoạt động</option>
										<option value = "0">Ngưng hoạt động</option>
										<option value = "" ></option>
										<%} else if(cnList.getTRANGTHAI().equals("0")){ %>
											<option value = "0"  selected="selected">Ngưng hoạt động</option>
										  	<option value = "1" >Hoạt động</option>
											<option  value="" > </option>
										  <%} else  {%>
										  	<option  value="" > </option>
										  <option value = "0"  >Ngưng hoạt động</option>
										  	<option value ="1" >Hoạt động</option>
										
										  <%} %>
										</select>
									</td>
								</tr>

							    <tr>
									<td colspan="2" class="plainlabel">
									<a class="button2" href="javascript:xoaform()">
									<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
						<legend class="legendtitle">&nbsp;Chi nhánh &nbsp;&nbsp;&nbsp;
						<%if(quyen[0]!=0){ %>
						<a class="button3" href="javascript:newform()">
    					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a> 
    					<%} %>                           
					 	</legend>
	
		   				<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<tr>
			  	   				<td align="left" colspan="4" class="legendtitle">
									<table width="100%" border="0" cellspacing="1" cellpadding="4">
										<tr class="tbheader">
											<th width="5%">Mã chi nhánh</th>
											<th width="7%">Tên chi nhánh</th>
											<th width="6%">Trạng thái</th>	
											<th width="5%">Ngày tạo</th>
											<th width="5%">Người tạo</th>
											<th width ="5%">Ngày sửa</th>											
											<th width ="5%">Người sửa</th>
														
											<th width="1%" align="center">&nbsp;Tác vụ</th>					
										</tr>
				
									<%
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(Rscn!=null)
									while (Rscn.next())
									{
										if(m % 2 != 0) {%>
										<tr class = <%=lightrow%> >
									<%} else {%>
										<tr class = <%=darkrow%> >
									<%}%>
										<td align="center"><%= Rscn.getString("ma")%> </td>
										<td align="center"><%= Rscn.getString("ten")%> </td>
										
											<% 
										if(Rscn.getString("tt").equals("1")){
										%>	
											<td align="center">Hoạt Động </td>
											<%
										}else{
											%>
											<td align="center">Ngưng Hoạt Động </td>
											<% 
										}
										%>
										
										<td align="center"><%= Rscn.getString("ngaytao") %> </td>
										<td align="center"><%= Rscn.getString("nguoitao")%> </td>
										<td align="center"><%= Rscn.getString("ngaysua")%> </td>
										<td align="center"><%= Rscn.getString("nguoisua")%> </td>										
									
										<td align = "center">
											<table border = 0 cellpadding="0" cellspacing="0">
												<tr>
												<td>
												<%if(quyen[2]!=0){ %>
													<a href = "../../Erp_ChiNhanhUpdateSvl?userid=<%=userId%>&update=<%=Rscn.getString("id_cn")%>"><img src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>
												<%} %>
												</td>
												<td>&nbsp;&nbsp;</td>
												<td>
												<%if(quyen[1]!=0){ %>
												<a href = ../../Erp_ChiNhanhSvl?userid=<%=userId%>&delete=<%=Rscn.getString("id_cn")%>><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa Chi nhánh này ?')) return false;"></a></td>
												<%} %>
												</tr>
											</table>				
										</tr>
									
									<% 	m++;
									}%>									
									
										<tr>
											<td height="26" colspan="11" align="center" class="tbfooter">&nbsp;	</td>
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
	if (Rscn != null) Rscn.close();
	cnList.DBClose(); 

	}%>
