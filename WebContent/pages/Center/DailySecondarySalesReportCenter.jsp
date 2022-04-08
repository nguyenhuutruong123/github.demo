<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
	
	String groupCustomer = (String)session.getAttribute("groupCustomer");
	String gorupSKU = (String)session.getAttribute("gorupSKU");
	
	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet kenh = obj.getkenh();
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet npp = obj.getnpp();
	ResultSet dvkd = obj.getdvkd();
	ResultSet nhanhang = obj.getnhanhang();
	ResultSet chungloai = obj.getchungloai();
	ResultSet loainpp = obj.getloainpp();
	ResultSet dvdl = obj.getdvdl();
	ResultSet sanpham = obj.getsanpham();
	ResultSet gsbh = obj.getgsbh();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("DailySecondarySalesTTSvl","", userId);
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("DailySecondarySalesTTSvl","",nnId);	
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

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">
	function seach() {
		document.forms['frm'].action.value = 'seach';
		document.forms["frm"].submit();
	}
	function submitform() 
	{
		if(document.forms["frm"]["level"].value=="2")
		{
			if(document.forms["frm"]["vungId"].value==""){
				setErrors("Vui lòng chọn vùng cần lấy báo cáo.");
				return;
			}
		}		
	
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}
		//document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		document.forms['frm'].action.value = 'Taomoi';
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
	};
	function AnThang()
	{
		document.getElementById("XemNgay").style.display = "";
		document.getElementById("XemThang").style.display = "none";
	}
	function HienThang()
	{
		document.getElementById("XemThang").style.display = "";
		document.getElementById("XemNgay").style.display = "none";
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	 function Dinhdang(element)
		{
			element.value=DinhDangTien(element.value);
			if(element.value== '' ||element.value=='0' )
			{
				element.value= '';
			}
		}
</script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select").select2(); 
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

	<form name="frm" method="post" action="../../DailySecondarySalesTTSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=obj.getuserId()%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=" "+url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng bạn",nnId) %>
					<%=obj.getuserTen()%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> <%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %></legend>
					<textarea id="errors" name="errors" rows="1"  style="width: 100% ; color:#F00 ; font-weight:bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Báo cáo bán hàng",nnId) %> </legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Xem theo",nnId) %></TD>
									<TD class="plainlabel" colspan="3">
									<% if(obj.gettype().trim().equals("1")){ %>
										<input type="radio" name="xemtheo" onchange="AnThang()" value="0" /><%=ChuyenNgu.get("Ngày",nnId) %> &nbsp;&nbsp;&nbsp;
										<!-- <input type="radio" name="xemtheo" onchange="HienThang()" value="1" checked="checked"/>Tháng -->
									<%}else{ %>
										<input type="radio" name="xemtheo" onchange="AnThang()" value="0" checked="checked" /><%=ChuyenNgu.get("Ngày",nnId) %> &nbsp;&nbsp;&nbsp;
										<!-- <input type="radio" name="xemtheo" onchange="HienThang()" value="1" />Tháng -->
									<%} %>
									</TD>
								</TR>
								<% if(obj.gettype().trim().equals("0")){ %>
									<TR id="XemNgay"  >
								<%} else { %>
									<TR id="XemNgay" style="display: none" >
								<%} %>
									<TD class="plainlabel"><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
									<TD class="plainlabel">
										<input type="text" name="Sdays"	class="days" value='<%=obj.gettungay()%>' /></TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Đến ngày",nnId) %></TD>
									<td>
										<input type="text" name="Edays" class="days" value='<%=obj.getdenngay()%>' /></td>
								</TR>
								<% if(obj.gettype().trim().equals("1")){ %>
									<TR id="XemThang">
								<%}else{ %>
									<TR id="XemThang" style="display: none">
								<%} %>
									<TD class="plainlabel"><%=ChuyenNgu.get("Từ tháng",nnId) %></TD>
									<TD class="plainlabel">
										<select name="tuthang">
										<option value=""> </option>
											<% for(int i = 0; i < 12; i++){ %>
											<% if(obj.getFromMonth().equals(Integer.toString(i+1))){ %>
												<option value="<%= i + 1 %>" selected="selected"><%=ChuyenNgu.get("Tháng",nnId) %>  <%= i+1 %> - <%=ChuyenNgu.get("Năm",nnId) %> <%= obj.getYear() %></option>
											<%}else{ %>
												<option value="<%= i + 1 %>"> <%=ChuyenNgu.get("Tháng",nnId) %> <%= i+1 %> - <%=ChuyenNgu.get("Năm",nnId) %> <%= obj.getYear() %></option>
											<%}} %>
										</select>
									</TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Đến tháng",nnId) %></TD>
									<TD class="plainlabel">
										<select name="denthang">
										<option value=""> </option>
											<% for(int i = 0; i < 12; i++){ %>
											<% if(obj.getToMonth().equals(Integer.toString(i+1))){ %>
												<option value="<%= i + 1 %>" selected="selected"> <%=ChuyenNgu.get("Tháng",nnId) %> <%= i+1 %> - <%=ChuyenNgu.get("Năm",nnId) %> <%= obj.getYear() %></option>
											<%}else{ %>
												<option value="<%= i + 1 %>"><%=ChuyenNgu.get("Tháng",nnId) %>  <%= i+1 %> - <%=ChuyenNgu.get("Năm",nnId) %> <%= obj.getYear() %></option>
											<%}} %>
										</select>
									</TD>
								</TR>
								
								<TR>
								<TD class="plainlabel"><%=ChuyenNgu.get("Từ mức",nnId) %></TD>
									<TD class="plainlabel">
										<input type="text" name="tumuc" onkeyup="Dinhdang(this)" value='<%=obj.gettumuc()%>' /> VND</TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Đến mức",nnId) %></TD>
									<td>
										<input type="text" name="denmuc" onkeyup="Dinhdang(this)" value='<%=obj.getdenmuc()%>' /> VND</td>
								</TR>
								
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId) %></TD>
									<TD class="plainlabel">
										<select name="kenhId" id="kenhId" onchange="seach();" style ="width:200px" >
											<option value="" selected>All</option>
											<%if (kenh != null)
													while (kenh.next()) {
														if (kenh.getString("pk_seq").equals(obj.getkenhId())) {%>
														<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("diengiai")%></option>
											<%} else { %>
												<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("diengiai")%></option>
											<%}}%>
										</select>
									</TD>
								<%-- 	<TD class="plainlabel">Giám sát bán hàng</TD>
									<TD class="plainlabel">
										<select name="gsbhs" id="gsbhsId"
											onchange="seach();" style ="width:200px" >
												<option value="" selected>All</option>
												<%if (gsbh != null)
														while (gsbh.next()) {
															if (gsbh.getString("pk_seq").equals(obj.getgsbhId())) {%>
														<option value="<%=gsbh.getString("pk_seq")%>" selected>
															<%=gsbh.getString("ten")%></option>
												<%} else {%>
														<option value="<%=gsbh.getString("pk_seq")%>"><%=gsbh.getString("ten")%></option>
												<%}}%>
										</select>
									</td> --%>
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Vùng/Miền",nnId) %></TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();" style ="width:200px">
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
										<select name="khuvucId" id="khuvucId" onchange="seach();" >
											<option value="" selected>All</option>
											<%if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
															<option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
													<%} else {%>
														<option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhà Phân Phối",nnId) %> </TD>
									<TD class="plainlabel">
										<select name="nppId" onchange="seach();" id="nppId" style ="width:200px">
											<option value="" selected>All</option>
											<%if (npp != null)
													while (npp.next()) {
														if (npp.getString("pk_seq").equals(obj.getnppId())) {%>
															<option value="<%=npp.getString("pk_seq")%>" selected><%=npp.getString("ten")%></option>
													<%} else {%>
														<option value="<%=npp.getString("pk_seq")%>"><%=npp.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
									
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhãn hàng",nnId) %></TD>
									<TD class="plainlabel">
										<select name="nhanhangId" id="nhanhangId" onchange="seach();">
												<option value="" selected>All</option>
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
								</TR>
								
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %></TD>
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
									<TD class="plainlabel"><%=ChuyenNgu.get("Chủng loại",nnId) %></TD>
									<TD class="plainlabel">
										<select name="chungloaiId" id="chungloaiId" onchange="seach();">
											<option value="" selected>All</option>
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
								<TR>									
									<%-- <TD class="plainlabel">Bao gồm chiết khấu</TD>
									<TD class="plainlabel">
										<%
											if(obj.getdiscount().equals("1")){%>
												<input type="radio" name="discount"	value="1"  checked="checked" />Có &nbsp;&nbsp;&nbsp; 
												<input type="radio" name="discount" value="0" />Không
											<%}else{%>
												<input type="radio" name="discount"	value="1"  />Có &nbsp;&nbsp;&nbsp; 
												<input type="radio" name="discount" value="0"  checked="checked"/>Không
											<%}%>										
									</TD>		 --%>
									<TD class="plainlabel"><%=ChuyenNgu.get("Loại nhà phân phối",nnId) %></TD>
									<TD class="plainlabel">
										<select name="loainppId" id="loainppId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (loainpp != null)
													while (loainpp.next()) {
														if (loainpp.getString("pk_seq").equals(obj.getloainppId())) {%>
														<option value="<%=loainpp.getString("pk_seq")%>" selected><%=loainpp.getString("ten")%></option>
													<%} else {%>
														<option value="<%=loainpp.getString("pk_seq")%>"><%=loainpp.getString("ten")%></option>
												<% }}%>
										</select>
									</TD>							
								</TR>
								
								<%-- <tr>
									<TD class="plainlabel">Bao gồm VAT</TD>
									<TD class="plainlabel">
										<%if(obj.getvat().equals("1")){%>
													<input type="radio" value="1"	name="vats" checked="checked" />Có &nbsp;&nbsp;&nbsp; 
													<input type="radio" value="0" name="vats" />Không
										<%}else{%> 
													<input type="radio" value="1"	name="vats" />Có &nbsp;&nbsp;&nbsp; 
													<input type="radio" value="0" name="vats" checked="checked"  />Không
										<%}%>
									</TD>
									
								</tr> --%>
									
								<TR>									
									<TD class="plainlabel"><%=ChuyenNgu.get("Mức lấy doanh số",nnId) %></TD>
									<TD class="plainlabel">
										<select name="level" >
										<%if(obj.getlessday().equals("0")){ %>
											<option value="0" selected="selected"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></option>
											<option value="1"><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></option>
											<option value="2"><%=ChuyenNgu.get("Khách hàng",nnId) %></option>
											<%}else if(obj.getlessday().equals("1")){ %>
											<option value="0"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></option>
											<option selected="selected" value="1"><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></option>
											<option value="2"><%=ChuyenNgu.get("Khách hàng",nnId) %></option>
											<%}else{
												%>
												<option value="0"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></option>
											<option value="1"><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></option>
											<option  selected="selected"  value="2"><%=ChuyenNgu.get("Khách hàng",nnId) %></option>
												
												<% }%>
											
										</select>
									</TD>									
								</TR>
								
								<TR>
								
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""><%=ChuyenNgu.get("Tạo báo cáo",nnId) %>  </a>
											</td>
								</TR>
							</TABLE>
						</div> 
			</div>
		</div>
		<br /> <br /> <br /> <br />
	</form>
	
	<script type="text/javascript">
		$("select").css({
			"width": "200px", 
			//"height": "200px"
		});
	</script>
	<%
		if(kenh !=null){ kenh.close(); kenh = null; }
		if(vung!=null){ vung.close(); vung = null; }
		if(khuvuc!=null){ khuvuc.close(); khuvuc = null; }
		if(npp!=null){ npp.close(); npp = null; }
		if(dvkd!=null){ dvkd.close(); dvkd = null; }
		if(nhanhang!=null){ nhanhang.close(); nhanhang = null; }
		if(chungloai!=null){ chungloai.close(); chungloai = null; }
		if(dvdl!=null){ dvdl.close(); dvdl = null; }
		if(sanpham!=null){ sanpham.close(); sanpham = null; }
		if(gsbh!=null){ gsbh.close(); gsbh = null; }
		
		if(obj!=null){
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	%>
</body> 
</html>