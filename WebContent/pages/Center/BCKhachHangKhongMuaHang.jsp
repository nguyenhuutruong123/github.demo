<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.imp.Stockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
    <%@ page  import = "geso.dms.center.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<% String userId = (String) session.getAttribute("userId");
   String userTen = (String) session.getAttribute("userTen");
   Stockintransit obj = (Stockintransit) session.getAttribute("obj");
   ResultSet vung = (ResultSet) obj.getvung();
   ResultSet khuvuc = (ResultSet) obj.getkhuvuc();
   ResultSet rsNPP = (ResultSet) obj.getnpp();
   String sum = (String) session.getAttribute("sum");
   Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{int[] quyen = new  int[6];
	quyen = util.Getquyen("BCKhachHangKhongMuaHangSvl","",userId);
	
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("BCKhachHangKhongMuaHangSvl","",nnId);	
 %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Báo cáo khách hàng không mua</title>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
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
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript">
	function submitform()
	{
		var tungay = document.forms['frm']['Sdays'].value;
		if(tungay.length==0) 
			{
				document.forms['frm']['errors'].value="Chọn từ ngày";
				return;
			}
		var denngay = document.forms['frm']['Edays'].value;
		if(denngay.length==0)
			{
				document.forms['frm']['errors'].value="Chọn đến ngày";
				return;
			}
		document.forms['frm']['action'].value="excel";
		document.forms['frm'].submit();
	}
	
	function changeform()
	{
		document.forms['frm']['action'].value="change";
		document.forms['frm'].submit();
	}
	
</script>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2(); 
    	});
    </script>	



<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="frm" method="post"
		action="../../BCKhachHangKhongMuaHangSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden" name="userTen" value='<%=userTen%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=" "+url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng bạn",nnId) %>
					<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> <%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %></legend>
					<textarea id="errors" name="errors" rows="2"  style="width: 100% ; color:#F00 ; font-weight:bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Khách hàng không mua",nnId) %> </legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
									<TD class="plainlabel">
										<input type="text" name="Sdays"	class="days" value='<%=obj.gettungay()%>' /></TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Đến ngày",nnId) %></TD>
									<td>
										<input type="text" name="Edays" class="days" value='<%=obj.getdenngay()%>' /></td>
								</TR>
								
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Vùng/Miền",nnId) %></TD>
									<TD class="plainlabel" >
										<select name="vungId" id="vungId"  style ="width:200px" onchange="changeform();">
											<option value="" selected>All</option>
											<%if (vung != null)
													while (vung.next()) {
														if (vung.getString("pk_seq").equals(obj.getvungId())) {%>
													<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
												<%} else {%>
													<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									
									<TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %></TD>
									<TD class="plainlabel">
										<select name="khuvucId"  style ="width:200px" onchange="changeform();">
			                                 <option value ="" > </option>  
			                               <%			                               
			                               while(khuvuc.next())
			                               {
			                               if(khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {
			                            	%><option value ="<%=khuvuc.getString("pk_seq") %>" selected> <%=khuvuc.getString("ten") %></option>  
			                            	  <%} else { %>
			                            	  <option value ="<%=khuvuc.getString("pk_seq") %>"> <%=khuvuc.getString("ten") %></option>
			                              <%}} %>                             
                              			</select>		  
									</TD>
								</TR>

								
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
									<TD class="plainlabel">
										<select name="nhapp">
											<option value="">All</option>
											<%if(rsNPP!=null)
											{ 
												while(rsNPP.next())
												{
												if(obj.getnppId().equals(rsNPP.getString("pk_seq")))
												{%>
												<option value="<%=rsNPP.getString("pk_seq")%>" selected="selected"><%=rsNPP.getString("TEN") %></option>
												<%}else{ %>
												<option value="<%=rsNPP.getString("pk_seq")%>"><%=rsNPP.getString("TEN") %></option>
												<%}
												}
											}%>
										</select>
									</TD>
								</TR>								
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""><%=ChuyenNgu.get("Tạo báo cáo",nnId) %>  </a></td>
								</TR>
							</TABLE>
						</div>
						<hr>				
					</div>
				</fieldset>
			</div>
		</div>
		<br /> <br /> <br /> <br />
	</form>
	
	<script type="text/javascript">
		$("select:not(.notuseselect2)").css({
			"width": "200px", 
			//"height": "200px"
		});
	</script>
	
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>

<%
	if(vung != null ){ vung.close(); vung = null; }
	if(khuvuc != null ){ khuvuc.close(); khuvuc = null; }
	if(rsNPP != null ){ rsNPP.close(); rsNPP = null; }
	
	obj.DBclose(); obj = null;
	session.setAttribute("obj",null);
	
	} %>
</html>