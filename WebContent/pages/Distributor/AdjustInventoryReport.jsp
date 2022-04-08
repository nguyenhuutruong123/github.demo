<%@page import="geso.dms.center.util.Utility"%>
<%@page import="java.sql.ResultSet"%>
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
   ResultSet kenh = obj.getkenh();
   ResultSet vung = obj.getvung();
   ResultSet khuvuc = obj.getkhuvuc();
   ResultSet npp = obj.getnpp();
   ResultSet dvkd = obj.getdvkd();
   ResultSet nhanhang = obj.getnhanhang();
   ResultSet chungloai = obj.getchungloai();
   ResultSet dvdl = obj.getdvdl();
   ResultSet sanpham = obj.getsanpham();
   ResultSet gsbh = obj.getgsbh();
   
  int[] quyen = new  int[6];
  quyen = util.Getquyen("AdjustInventoryReportnpp","", userId);
 
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

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
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
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
function seach()
{
	document.forms['frm'].action.value= 'seach';
	document.forms["frm"].submit();
}
	function submitform() {
		if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return ;
		}
		var fieldShow = document.getElementsByName("fieldsHien");
		var fieldHidden = document.getElementsByName("fieldsAn");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}
	/*	for ( var i = 0; i < fieldHidden.length; ++i) {
			fieldHidden.item(i).checked = true;
		}
	*/
		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}
	}
	
	function submitform2() {
		if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return ;
		}
		var fieldShow = document.getElementsByName("fieldsHien");
		var fieldHidden = document.getElementsByName("fieldsAn");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}
		document.forms['frm'].action.value= 'tao2';
		document.forms["frm"].submit();
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	window.onload = function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");
		var fieldHidden = document.getElementsByName("fieldsAn");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}
		for ( var i = 0; i < fieldHidden.length; ++i) {
			fieldHidden.item(i).checked = false;
		}
	};
</script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
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

	<form name="frm" method="post" action="../../AdjustInventoryReportnpp">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 50%; padding: 5px; float: left"
					class="tbnavigation">Quản lý tồn kho > Báo cáo > Điều chỉnh tồn kho</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn   <%=userTen %></div>
			</div>
				<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"><%= obj.getMsg() %></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Điều chỉnh tồn Kho</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel">
												<input type="text" name="Sdays" class="days" value ='<%=obj.gettungay() %>'/>
									</TD>
									<TD class="plainlabel">Đến ngày</TD>
									<td >
										<input type="text" name="Edays" class="days" value ='<%=obj.getdenngay() %>'/>
									</td>
								</TR>
								<TR>
								<TD class="plainlabel">Kênh bán hàng</TD>
									
									<TD class="plainlabel">
										<select name="kenhId" id="kenhId" onchange="seach();">
												<option value="" selected>All</option>
												<% if(kenh != null)
												   while(kenh.next()){
												   if(kenh.getString("pk_seq").equals(obj.getkenhId()))
												   { %>
												   <option value="<%= kenh.getString("pk_seq") %>" selected><%=kenh.getString("diengiai") %></option>
												   <%}else { %>
												   <option value="<%= kenh.getString("pk_seq") %>"><%=kenh.getString("diengiai") %></option>
												<%} }%>
										</select>
									</TD>
									 <TD class="plainlabel">Đơn vị kinh doanh</TD>
									<TD class="plainlabel"><select name="dvkdId" id="dvkdId" >
											<option value="" selected>All</option>
											<% if(dvkd != null)
											   while(dvkd.next()){
											   if(dvkd.getString("pk_seq").equals(obj.getdvkdId()))
											   { %>
											   <option value="<%= dvkd.getString("pk_seq") %>" selected><%=dvkd.getString("diengiai") %></option>
											   <%}else { %>
											   <option value="<%= dvkd.getString("pk_seq") %>"><%=dvkd.getString("diengiai") %></option>
											<%} }%>
									</select>
									</TD>
									</TR>
									<TR>
									
									<TD class="plainlabel">Nhãn hàng</TD>
									<TD class="plainlabel">
										<select name="nhanhangId" id="nhanhangId" onchange="seach();">
												<option value="" selected>All</option>
													<% if(nhanhang != null)
												   while(nhanhang.next()){
												   if(nhanhang.getString("pk_seq").equals(obj.getnhanhangId()))
												   { %>
												   <option value="<%= nhanhang.getString("pk_seq") %>" selected><%=nhanhang.getString("ten") %></option>
												   <%}else { %>
												   <option value="<%= nhanhang.getString("pk_seq") %>"><%=nhanhang.getString("ten") %></option>
												<%} }%>
										</select>
									</TD>
									
									<TD class="plainlabel">Tên sản phẩm</TD>
									<TD class="plainlabel"><select name="sanphamId" id="sanphamId" onchange="seach();">
											<option value="" selected>All</option>
												<% if(sanpham != null)
											   while(sanpham.next()){
											   if(sanpham.getString("pk_seq").equals(obj.getsanphamId()))
											   { %>
											   <option value="<%= sanpham.getString("pk_seq") %>" selected><%=sanpham.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= sanpham.getString("pk_seq") %>"><%=sanpham.getString("ten") %></option>
											<%} }%>
									</select>
									</TD>
								</TR>
								<TR>
								<TD class="plainlabel">Chủng Lọai</TD>
									<TD class="plainlabel">
									<select name="chungloaiId" id="chungloaiId" onchange="seach();">
											<option value="" selected>All</option>
											<% if(chungloai != null)
											   while(chungloai.next()){
											   if(chungloai.getString("pk_seq").equals(obj.getchungloaiId()))
											   { %>
											   <option value="<%= chungloai.getString("pk_seq") %>" selected><%=chungloai.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= chungloai.getString("pk_seq") %>"><%=chungloai.getString("ten") %></option>
											<%} }%>
									</select>
									</TD>
								</TR>
      							<TR>
									<td colspan="1"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo
									</a></td>
									<td colspan="4"><a class="button"
										href="javascript:submitform2();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> BC Chênh lệch tồn kho
									</a></td>
								</TR>
						</TABLE>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<br />
	</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<% 
	 if(chungloai != null){ chungloai.close(); chungloai = null; }
	 if (dvkd != null){  dvkd.close(); dvkd = null; }
	 if (nhanhang != null){  nhanhang.close(); nhanhang = null; }
	 if (dvdl != null){  dvdl.close() ; dvdl = null; }
	 if(gsbh != null){  gsbh.close(); gsbh = null; }
	 if(kenh != null){  kenh.close(); kenh = null; }
	 if(khuvuc != null){  khuvuc.close(); khuvuc = null; }
	 if(npp != null){  npp.close(); npp = null; }
	 if(vung != null){  vung.close(); vung = null; }
	 if(sanpham != null){  sanpham.close(); sanpham = null; }

	 if(obj != null){
		 obj.DBclose();
		 obj = null;
	 }
	session.setAttribute("obj",null);

}%>