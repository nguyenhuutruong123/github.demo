<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		IStockintransit obj = (IStockintransit) session.getAttribute("obj");
		Utility util = (Utility) session.getAttribute("util");
		ResultSet kenh = obj.getkenh();
		ResultSet vung = obj.getvung();
		ResultSet khuvuc = obj.getkhuvuc();
		ResultSet npp = obj.getnpp();
		ResultSet dvkd = obj.getdvkd();
		ResultSet dvdl = obj.getdvdl();
	%>
	
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("DailySalesSvl","&view=TT",nnId);	
 %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>OPV - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
	$("#sanphamId").select2();
});
</script>
<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
	function submitform() {
		
		document.forms['frm'].action.value= 'Taomoi';
		document.forms["frm"].submit();
		/* reset(); */
	}
	function seach()
	{
		document.forms['frm'].action.value= 'seach';
		document.forms["frm"].submit();
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	
	
</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="frm" method="post" action="../../DailySalesSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
	<input type="hidden" name="view" value='TT'>
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=" "+url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen%></div>
			</div>
			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="#"> <img
					src="../images/Back30.png" alt="Quay ve" title="Quay ve"
					border="1px" longdesc="Quay ve" style="border-style: outset"></A>
				<A href="javascript:saveform()"> <IMG src="../images/Save30.png"
					title="Luu lai" alt="Luu lai" border="1px"
					style="border-style: outset"></A>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
					<%= obj.getMsg() %></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"> Thực đạt so với chỉ tiêu</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								
								<TR>
						
                    	<TD   width="130px" class="plainlabel"  valign="top"><%=ChuyenNgu.get("Thời gian áp dụng",nnId) %> </TD>
                    <%-- <TD width="300px" class="plainlabel" valign="top"  >
                  
                    	
                    	<% 	String[] trangthai = new  String[] {"Tháng","Qúy","Năm"  } ;
							String[] idTrangThai = new  String[] {"0","1","2"} ;
						%> 
						<SELECT name="apdung" onchange="seach();" >
		      		 <% for( int i=0;i<trangthai.length;i++) { 
		    			if(idTrangThai[i].equals(obj.gettype()   ) ){ %>
		      				<option value='<%=idTrangThai[i]%>' selected><%=trangthai[i] %></option>
		      		 	<%}else{ %>
		     				<option value='<%=idTrangThai[i]%>'><%=trangthai[i] %></option>
		     			<%} 
		      		 }
		      		 	%>
					       	</SELECT>
                    	
                    </TD> --%>         
                    
                       <TD   width="120px" class="plainlabel" valign="top"> </TD>
                    <TD class="plainlabel" valign="top"   >
                    
                    	
								<%=ChuyenNgu.get("Tháng",nnId) %>
								<select name="thang"  style="width :50px" >
									<option > </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										String chuoi=k<10?"0"+k:""+k;
  									  if(obj.getMonth().equals(k+"")){
  									%>
									<option value=<%=k%> selected="selected" > <%=chuoi%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=chuoi%></option> 
									<%
 										}
 									  }
 									%>
									</select>
								
		      		 	
								<%=ChuyenNgu.get("Năm",nnId) %>	
									<select name="nam"  style="width :75px" >
									<option > </option>  
									<%
									  
  										int n;
  										for(n=2014;n<2025;n++){
  										if(obj.getYear().equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>									
                    </TD>   	
                    
				</TR>
							
								
								
								<TR>
								<TD class="plainlabel"><%=ChuyenNgu.get("Miền",nnId) %></TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();">
											<option value="" selected>All</option>
											<%
												if (vung != null)
													while (vung.next()) {
														if (vung.getString("pk_seq").equals(obj.getvungId())) {
											%>
											   <option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
											   <%
											   	} else {
											   %>
											   <option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
											<%
												}
													}
											%>
										</select>
										</TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Chi nhánh / Nhà phân phối",nnId) %></TD>
									<TD class="plainlabel"><select name="nppId" id="nppId" style="width:200px;" >
											<option value="" selected>All</option>
											<%
												if (npp != null)
													while (npp.next()) {
														if (npp.getString("pk_seq").equals(obj.getnppId())) {
											%>
											   <option value="<%=npp.getString("pk_seq")%>" selected><%=npp.getString("ten")%></option>
											   <%
											   	} else {
											   %>
											   <option value="<%=npp.getString("pk_seq")%>"><%=npp.getString("ten")%></option>
											<%
												}
													}
											%>
									</select></TD>
									</TR>
								<TR>									
									<TD class="plainlabel"><%=ChuyenNgu.get("Khu Vực",nnId) %></TD>
									<TD class="plainlabel">
									<select name="khuvucId" id="khuvucId" onchange="seach();">
											<option value="" selected>All</option>
											<%
												if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {
											%>
											   <option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
											   <%
											   	} else {
											   %>
											   <option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
											<%
												}
													}
											%>
									</select>
									</TD>
									
									</TR>
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> <%=ChuyenNgu.get("Tạo báo cáo",nnId) %>
									</a></td>
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
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%
try{
if(kenh!=null){
	kenh.close();
}
if(vung!=null){
	vung.close();
}
if(khuvuc!=null){
	khuvuc.close();
}
if(npp!=null){
	npp.close();
}
if(dvkd!=null){
	dvkd.close();
}
if(dvdl!=null){
	dvdl.close();
}
obj.DBclose();
}catch(Exception er){
	
}
%>