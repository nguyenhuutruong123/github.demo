<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.khoahuanluyen.IKhoahuanluyenList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

	
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/IMV/index.jsp");
	}else{ %>

<%	
	IKhoahuanluyenList obj = (IKhoahuanluyenList) session.getAttribute("obj");	
	ResultSet npps = obj.getNppRs();
	ResultSet vungs = obj.getVungRs();
	ResultSet khuvucs = obj.getKhuvucRs();
	ResultSet kenhs = obj.getKbhRs();
	ResultSet khoahvRs = obj.getKhoahuanluyenRs();
	
	int[] quyen = new  int[6];
	quyen = util.Getquyen("BCTonKhoTheoQuyDinh","", userId);
 %>
 <% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("BCTonKhoTheoQuyDinh","",nnId);	
	%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
		$(".button").hover(function() {
			$(".button img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
	$(document).ready(function() {
		$(".button1").hover(function() {
			$(".button1 img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
</script>


<script language="javascript" type="text/javascript">

	function seach() 
	{
		document.forms['frm'].action.value = 'seach';
		document.forms["frm"].submit();
	}
	function submitform()
	{
		if(document.getElementById("tungay").value == "")
		{
			alert('Vui lòng nhập ngày tồn kho cần lấy');
			return;
		}
		
		document.forms['frm'].action.value = 'Taomoi';
		document.forms["frm"].submit();
	}
	
	function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var khlIds = document.getElementsByName("khlIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < khlIds.length; i++)
			 {
				 khlIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < khlIds.length; i++)
			 {
				 khlIds.item(i).checked = false;
			 }
		 }
	 }

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

	<form name="frm" method="post" action="../../BCTonKhoTheoQuyDinh">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="view" value='TKQD'> 
		<input type="hidden" name="userId" value='<%= obj.getUserId() %>'>
			
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%= " " + url %> </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng bạn",nnId) %> <%= obj.getUserTen() %></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> <%=ChuyenNgu.get("Thông báo",nnId) %></legend>
					<textarea id="errors" name="errors" rows="2"  style="width: 100%;text-align: left; color:#F00 ; font-weight:bold">
						<%= obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left;font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Tồn kho theo quy định",nnId) %></legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
									<TD class="plainlabel">
										<input class="days" type="text" name="tungay" id="tungay" value="<%= obj.getTungay() %>" size="20" >
									</TD>
									
									<TD class="plainlabel"><%=ChuyenNgu.get("Đến ngày",nnId) %></TD>
									<TD class="plainlabel">
										<input class="days" type="text" name="denngay" id="denngay" value="<%= obj.getDenngay() %>" size="20" >
									</TD>
								</TR>							
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId) %></TD>
									<TD style="width: 60px" colspan="3">
										<select name="kenhId" id="kenhId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (kenhs != null)
													while (kenhs.next()) {
														if (kenhs.getString("pk_seq").equals(obj.getKbhId())) {%>
														<option value="<%= kenhs.getString("pk_seq")%>" selected><%= kenhs.getString("diengiai") %></option>
											<%} else { %>
												<option value="<%= kenhs.getString("pk_seq")%>"><%= kenhs.getString("diengiai") %></option>
											<%}}%>
										</select>
									</TD>
								</TR>
								<TR>
                             	<TD class="plainlabel" ><%=ChuyenNgu.get("Ngày quy định",nnId) %></TD>
								<TD>
									<input style="width: 8%; text-align: left;" name="ngayquydinh" value="24">
		                         
								</TD>
                        		</TR>					
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo </a></td>
								</TR>
							</TABLE>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
	</form>
<script type="text/javascript">
	dropdowncontent.init('nppId', "right-bottom", 300, "click");
</script>

<%
	if(kenhs !=null){ kenhs.close(); kenhs = null; }
	if(vungs !=null){  vungs.close(); vungs = null; }
	if(khuvucs !=null){  khuvucs.close(); khuvucs = null; }
	if(npps !=null){  npps.close(); npps = null; }
	if(khoahvRs !=null){  khoahvRs.close(); khoahvRs = null; }
	
	if(obj!=null)
	{
		obj.DbClose();
		obj = null;
	}
	session.setAttribute("obj", null);
}
%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>