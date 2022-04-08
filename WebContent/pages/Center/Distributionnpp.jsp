<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.util.Utility"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet sku = obj.getsanpham();
	ResultSet ddkd = obj.getRsddkd();
	ResultSet nhanhang = obj.getnhanhang();
	ResultSet chungloai = obj.getchungloai();
	ResultSet dvkd=obj.getdvkd();
	ResultSet nhomsp=obj.GetRsNhomSP();
	
	int[] quyen = new  int[6];
	quyen = util.Getquyen("Distributionnpp","", userId);
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
	function seach() {
		document.forms['frm'].action.value = 'seach';
		document.forms["frm"].submit();
	}
	function submitform() {
		if (document.forms["frm"]["tungay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return;
		}
		if (document.forms["frm"]["denngay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return;
		}
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}

		document.forms['frm'].action.value = 'create';
		document.forms["frm"].submit();
		reset();
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");		
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}		
	}
	function checkall(value){
		var checkone=document.getElementsByName("checkkhuvuc1");
		var giatricheck=document.getElementsByName("checkkhuvuc");
		var chuoi;
		if(value==true){
			chuoi="1";
		}else{
			chuoi="0";
		}
		for(var i=0;i<checkone.length;i++ ){
			checkone.item(i).checked=value;
			giatricheck.item(i).value=chuoi;
		}
	}
	function recheck(){
		var checkone=document.getElementsByName("check");
		var giatricheck=document.getElementsByName("valuechecked");
		for(var i=0;i<checkone.length;i++ ){
			if(checkone.item(i).checked==true){
				giatricheck.item(i).value="1";
			}else {
				giatricheck.item(i).value="0";
			}
			
				
		}
	}
</script>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>	
 <style type="text/css">
   
  
   label:hover { background-color: Highlight; color: HighlightText; }
  </style>

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

	<form name="frm" method="post" action="../../Distributionnpp">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=obj.getuserId()%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo quản trị &#62; Theo dõi doanh số  &#62; Độ phủ của sản phẩm</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="2"  style="width: 100% ; color:#F00 ; font-weight:bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Độ phủ của sản phẩm</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel">
										<input type="text" name="tungay" class="days" value='<%=obj.gettungay()%>' /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<TD class="plainlabel">
										<input type="text" name="denngay" class="days" value='<%=obj.getdenngay()%>' /></td>
								</TR>
								
								<TR>
									<TD class="plainlabel">Nhân viên bán hàng</TD>
									<TD class="plainlabel">
										<select name="ddkdId" id="ddkdId" >
												<option value="" selected>Tất cả</option>
												<%if (ddkd != null)
														while (ddkd.next()) {
															if (ddkd.getString("pk_seq")
																	.equals(obj.getDdkd())) {	%>
															<option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
														<%} else {%>
															<option value="<%=ddkd.getString("pk_seq")%>"><%=ddkd.getString("ten")%></option>
												<%}}%>
										 </select>
									</TD>
									<TD class="plainlabel">Đơn vị kinh doanh</TD>
									<TD class="plainlabel">
										<select name="dvkdId" id="dvkdId"	onchange="seach();">
											<option value="" selected>All</option>
											<%if (dvkd != null)
													while (dvkd.next()) {
														if (dvkd.getString("pk_seq").equals(obj.getdvkdId())) {%>
														<option value="<%=dvkd.getString("pk_seq")%>" selected><%=dvkd.getString("diengiai")%></option>
													<%} else {%>
														<option value="<%=dvkd.getString("pk_seq")%>"><%=dvkd.getString("diengiai")%></option>
													<%}}%>
										</select>
									</TD>		
								</TR>
								
								<TR>
									<TD class="plainlabel">Nhãn hàng</TD>
									<TD class="plainlabel">
										<select name="nhanhangId" onchange="seach();" id="nhanhangId" >
												<option value="" selected>Tất cả</option>
												<%if (nhanhang != null)
														while (nhanhang.next()) {
															if (nhanhang.getString("pk_seq")
																	.equals(obj.getnhanhangId())) {	%>
															<option value="<%=nhanhang.getString("pk_seq")%>" selected><%=nhanhang.getString("ten")%></option>
														<%} else {%>
															<option value="<%=nhanhang.getString("pk_seq")%>"><%=nhanhang.getString("ten")%></option>
												<%}}%>
										 </select>
									</TD>			
									<TD class="plainlabel">Chủng loại</TD>
									<TD class="plainlabel">
										<select name="chungloaiId" onchange="seach();"  id="chungloaiId" >
											<option value="" selected>Tất cả</option>
											<%if (chungloai != null)
													while (chungloai.next()) {
														if (chungloai.getString("pk_seq").equals(obj.getchungloaiId())) {%>
														<option value="<%=chungloai.getString("pk_seq")%>" selected><%=chungloai.getString("ten")%></option>
													<%} else {%>
														<option value="<%=chungloai.getString("pk_seq")%>"><%=chungloai.getString("ten")%></option>
												<% }}%>
										</select>
									</TD>					
								</TR>
								<tr>
								    <TD class="plainlabel">Nhóm sản phẩm</TD>
									<TD class="plainlabel" colspan="3">
										<select name="nhomspid" onchange="seach();"  id="nhomspid" >
											<option value="" selected>Tất cả</option>
											<%if (chungloai != null)
													while (nhomsp.next()) {
														if (nhomsp.getString("pk_seq").equals(obj.GetNhoSPId())) {%>
														<option value="<%=nhomsp.getString("pk_seq")%>" selected><%=nhomsp.getString("diengiai")%></option>
													<%} else {%>
														<option value="<%=nhomsp.getString("pk_seq")%>"><%=nhomsp.getString("diengiai")%></option>
												<% }}%>
										</select>
									</TD>				
								</tr>
								<TR>
									
									<TD class="plainlabel"></TD>
									<TD class="plainlabel" colspan="3">
									<fieldset>
									<legend>Xem theo</legend> 
									<% if(obj.gettype().equals("1")){ %>
										<input type="radio" name="typeid" onchange="Laytheokh();" value="0" />Lấy theo khách hàng &nbsp;&nbsp;&nbsp;
										<input type="radio" name="typeid" onchange="LayThethoigian();" value="1" checked="checked"/>Theo thời gian
									<%}else{ %>
										<input type="radio" name="typeid" onchange="Laytheokh();" value="0" checked="checked" />Lấy theo khách hàng &nbsp;&nbsp;&nbsp;
										<input type="radio" name="typeid" onchange="LayThethoigian();" value="1" />Theo thời gian
									<%} %>
									</fieldset>
									</TD>
								</TR>
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt="Tạo báo cáo">Tạo báo cáo</a></td>
								</TR>
								<tr >
									<td colspan="4" >
									<fieldset>
										<legend> Chọn sản phẩm </legend>
										<ul>
										 <%											
				                               while(sku.next())
				                               {%>
													<li><label  for="check" > 
															<input type ="hidden" value="" name="valuechecked">
															<input type="checkbox" name="check"  onchange="recheck();" value ="<%=sku.getString("pk_seq") %>" > 
															<%=sku.getString("ma")+ " -" + sku.getString("ten")%> 
														</label>  
														<input name ="skutest"  type="hidden" value="<%=sku.getString("pk_seq") %>">
													</li>
				                              <%}
										 %> 
										</ul>
										</fieldset>
									</td>									
								</tr>
							</TABLE>
						</div>
						<hr>
						
					</div>
				</fieldset>
			</div>
		</div>
		<br /> <br /> <br /> <br />
	</form>
	<%
		if(nhanhang!=null){ nhanhang.close(); nhanhang = null; }
		if(chungloai!=null){  chungloai.close(); chungloai = null; }
		if(ddkd!=null){  ddkd.close(); ddkd = null; }
		if(sku!=null){  sku.close(); sku = null; }
		if(nhomsp!=null){  nhomsp.close(); nhomsp = null; }
		if(dvkd!=null){ dvkd.close(); dvkd = null; }
		
		if(obj!=null){
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj",null);
	}
	%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
