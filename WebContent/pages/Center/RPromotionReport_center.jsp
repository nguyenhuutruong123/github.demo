<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page import = "geso.dms.center.util.*" %>
<%@page import="geso.dms.center.util.ChatSvl"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
	ResultSet programs = obj.getRsPrograms();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("PromotionReportTTSvl","", userId);
%>
<% String nnId = (String)session.getAttribute("nnId"); %>
<% if(nnId == null) {
 nnId = "vi";  
 }
 String url = util.getChuyenNguUrl("PromotionReportTTSvl","" ,nnId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
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
		document.forms['frm'].action.value = "search";
		document.forms["frm"].submit();
	}
	function submitform() {
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}
		document.getElementById("btnSave1").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		
		document.forms['frm'].action.value = "Taomoi";
		document.forms["frm"].submit();
		reset();
	}
	
	function xuatexel() {
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}

		document.forms['frm'].action.value = "xuatexel";
		document.forms["frm"].submit();
		reset();
	}
	
	
	function xuatexelcu() {
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}

		document.forms['frm'].action.value = "xuatexelcu";
		document.forms["frm"].submit();
		reset();
	}
	
	
	function xuatexelkt() {
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}

		document.forms['frm'].action.value = "Taobcmoi";
		document.forms["frm"].submit();
		reset();
	}
	
	function newtab()
	{    
		var tt = document.forms['frm'].linkUrl.value;
		
		if(tt.length>0)
	    {
			 var win = window.open(tt, '_blank');
			  win.focus(); 
	    }
		
		document.forms['frm'].linkUrl.value= ''; 
		
		
	}
	
	function DoDuLieu()
	{
		document.frm.action = "../../ChatSvl";
		document.forms['frm'].action.value= 'DoDuLieu';
		document.forms['frm'].submit();
		document.frm.action = "../../PromotionReportTTSvl";
	}
	function load() {
		tontai = $("#checktontai").val(); 
		
	 $.ajax({
			  type: "POST",
			  url: "../../ChatSvl?check="+tontai+"",
			  data: "{''}",
		    contentType: "application/json",
		    async: false,
			     success: function (data)  {		        
			        $.each(data, function (i, item) {
			        
			        	console.log('oalal '+data[i].KETQUA);
			        	if(data[i].KETQUA == "TRUE" )
			        		{
			        		console.log("vao rui hahah");
			        		document.getElementById("checktontai").value = "_CHECK"
			        			DoDuLieu();
			        		document.getElementById("btnSave1").innerHTML = "<a class='button' href='javascript:submitform();'> <img style='top: -4px;' src='../images/button.png'> Tạo báo cáo</a>";
			        		
			        		}
			        	if(document.forms['frm'].linkUrl.value!="" )
			        		{
			        		
			        		document.getElementById("btnSave1").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
			        		
			        		}
			        
			        });
	        		
	        	
			        }

	});
		 
		setTimeout(load, 5000);
		
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
</script>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
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

	<form name="frm" method="post"
		action="../../PromotionReportTTSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=obj.getuserId()%>'>
		<input type="hidden" name="view" value='<%=obj.getView()%>'>
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden" name="linkUrl" value='<%= obj.getUrl()%>'>
		<input type="hidden" id="checktontai" name="checktontai" value='<%=obj.getKey()%>'>
		<input type="hidden" name="report_name" value='BCXuatKhuyenMaiTT'> 
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=url %> </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng",nnId) %>
					<%=obj.getuserTen()%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %></legend>
					<textarea id="errors" name="errors" rows="2"  style="width: 100%;text-align: left; color:#F00 ; font-weight:bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left;font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Báo cáo xuất khuyến mãi",nnId) %></legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<!-- 2 tag table -->
							<TABLE width="70%" cellpadding="6" cellspacing="0">
							<TABLE width="85%" cellpadding="6" cellspacing="0">
								<%-- <TR>
									<TD class="plainlabel">Chọn năm</TD>
									
									<TD class="plainlabel">
										<SELECT name = "year">
										<%
									  
  										int n;
  										for(n=2008;n<2025;n++){
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

										</SELECT>									
									</TD>
									
									<TD class="plainlabel">Chọn tháng</TD>
									<TD>
										<SELECT name = "month">
										<%if(obj.getMonth().equals("01")){ %>
											<option value = "01" selected>T1</option>
										<%}else{ %>
											<option value = "01">T1</option>
										<%} %>

										<%if(obj.getMonth().equals("02")){ %>
											<option value = "02" selected>T2</option>
										<%}else{ %>
											<option value = "02">T2</option>
										<%} %>

										<%if(obj.getMonth().equals("03")){ %>
											<option value = "03" selected>T3</option>
										<%}else{ %>
											<option value = "03">T3</option>
										<%} %>

										<%if(obj.getMonth().equals("04")){ %>
											<option value = "04" selected>T4</option>
										<%}else{ %>
											<option value = "04">T4</option>
										<%} %>

										<%if(obj.getMonth().equals("05")){ %>
											<option value = "05" selected>T5</option>
										<%}else{ %>
											<option value = "05">T5</option>
										<%} %>

										<%if(obj.getMonth().equals("06")){ %>
											<option value = "06" selected>T6</option>
										<%}else{ %>
											<option value = "06">T6</option>
										<%} %>

										<%if(obj.getMonth().equals("07")){ %>
											<option value = "07" selected>T7</option>
										<%}else{ %>
											<option value = "07">T7</option>
										<%} %>

										<%if(obj.getMonth().equals("08")){ %>
											<option value = "08" selected>T8</option>
										<%}else{ %>
											<option value = "08">T8</option>
										<%} %>
										
										<%if(obj.getMonth().equals("09")){ %>
											<option value = "09" selected>T9</option>
										<%}else{ %>
											<option value = "09">T9</option>
										<%} %>
										
										<%if(obj.getMonth().equals("10")){ %>
											<option value = "10" selected>T10</option>
										<%}else{ %>
											<option value = "10">T10</option>
										<%} %>
										
										<%if(obj.getMonth().equals("11")){ %>
											<option value = "11" selected>T11</option>
										<%}else{ %>
											<option value = "11">T11</option>
										<%} %>
										
										<%if(obj.getMonth().equals("12")){ %>
											<option value = "12" selected>T12</option>
										<%}else{ %>
											<option value = "12">T12</option>
										<%} %>
										
										

										</SELECT>
									
									</TD>
								</TR> --%>
								<TR>
									<TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
									<td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="tungay" name="tungay" value="<%= obj.gettungay() %>" maxlength="10" readonly />
			                    	</td>
			                    	<TD class="plainlabel" ><%=ChuyenNgu.get("Đến ngày",nnId) %></TD>
							      <td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="denngay" name="denngay" value="<%= obj.getdenngay() %>" maxlength="10" readonly />
			                    	</td>
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Chương trình khuyến mãi",nnId) %></TD>
									<TD class="plainlabel" colspan="3" >
										<select name="programs" style="width: 500px" >
											<option value=""><%=ChuyenNgu.get("All",nnId) %></option>
											<%
												if(programs!=null){
													while(programs.next()){
														if(obj.getPrograms().equals(programs.getString("SCHEME"))){%>
															<option value="<%=programs.getString("SCHEME")%>" selected="selected" >
																<%= programs.getString("SCHEME") + "<-->" + programs.getString("DIENGIAI") %></option>															
														<%}else{%>
															<option value="<%=programs.getString("SCHEME")%>" >
																<%=programs.getString("SCHEME") + "<-->" + programs.getString("DIENGIAI") %></option>	
														<%}
													}
												}
											%>
										</select>
									</TD>											
								</TR>
								
								<%if(!obj.getView().equals("NPP")){ %>
									<TR>									
									<TD class="plainlabel"><%=ChuyenNgu.get("Vùng/Miền",nnId) %></TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();">
											<option value="" selected><%=ChuyenNgu.get("All",nnId) %></option>
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
									<TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %></TD>
									<TD class="plainlabel">
									<select name="khuvucId" id="khuvucId" onchange="seach();">
											<option value="" selected><%=ChuyenNgu.get("All",nnId) %></option>
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
								<%} %>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId) %></TD>
									<TD class="plainlabel">
										<select name="kenhId" id="kenhId" onchange="seach();">
											<option value="" selected><%=ChuyenNgu.get("All",nnId) %></option>
											<%if (kenh != null)
													while (kenh.next()) {
														if (kenh.getString("pk_seq").equals(obj.getkenhId())) {%>
														<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("diengiai")%></option>
											<%} else { %>
												<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("diengiai")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
									<TD class="plainlabel"><select name="nppId" id="nppId" >
											<option value="" selected><%=ChuyenNgu.get("All",nnId) %></option>
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
									<TD  class="plainlabel" colspan = "4" ><label>
										<%  if (obj.getDhChot().equals("1")){%>
										  	<input name="dhchot" type="checkbox" value ="1" checked>
										<%} else {%>
											<input name="dhchot" type="checkbox" value ="0">
										<%} %>
									    <%=ChuyenNgu.get("Chỉ lấy những đơn hàng đã chốt",nnId) %></label></TD>
										<TD  class="plainlabel" >&nbsp;</TD>

								
								</TR>
															
								<TR>
									<TD  class="plainlabel" colspan = "4" ><label>
										<%  if (obj.getUnghang().equals("1")){%>
										  	<input name="unghang" type="checkbox" value ="1" checked>
										<%} else {%>
											<input name="unghang" type="checkbox" value ="0">
										<%} %>
									    <%=ChuyenNgu.get("Bao gồm chương trình ứng hàng trước",nnId) %></label></TD>
										<TD  class="plainlabel" >&nbsp;</TD>

								
								</TR>
									
								<TR >
									<td colspan="0" style="width:250px"><div id="btnSave1"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất Excel",nnId) %></a></div></td>
											
								<%-- 	<td ><a class="button"
										href="javascript:xuatexel();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> <%=ChuyenNgu.get("Xuất excel",nnId) %> </a></td>
											 --%>
									<%-- <td colspan="4"><a class="button"
									href="javascript:xuatexelkt();"> <img style="top: -4px;"
									src="../images/button.png" alt=""> <%=ChuyenNgu.get("Tiền khuyến mãi đúng",nnId) %> </a></td>
									
									<td colspan="4" ><a class="button"
									href="javascript:xuatexelcu();"> <img style="top: -4px;"
									src="../images/button.png" alt=""> <%=ChuyenNgu.get("Doanh số đúng",nnId) %> </a></td> --%>
								</TR>
							</TABLE>
						</div>
						<hr>
						<div style="width: 100%; float: none;display: none" >
							<div style="width: 30%; float: left">
								<fieldset style="min-height: 200px">
									<legend class="legendtitle"> <%=ChuyenNgu.get("Dữ liệu hiển thị",nnId) %> </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbShowFields">
										<tbody id="ShowFields">
											<tr class="tbheader">
												<th></th>
												<th align="center"><%=ChuyenNgu.get("Chọn ẩn",nnId) %></th>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Kênh bán hàng",nnId) %></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="KenhBanHang"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Chi nhánh",nnId) %></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="ChiNhanh"></td>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Khu vực",nnId) %></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="KhuVuc"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Nhà phân phối",nnId) %></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="NhaPhanPhoi"></td>
											</tr>	
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Mã chương trình",nnId) %></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="MaChuongTrinh"></td>
											</tr>

											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Diễn giải",nnId) %></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="ChuongTrinhKhuyenMai"></td>
											</tr>

											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Mã sản phẩm",nnId) %></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="MaSanPham"></td>
											</tr>
											
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Tên sản phẩm",nnId) %> </td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="TenSanPham"></td>
											</tr>

											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Số lượng lẻ",nnId) %></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="SoLuong(Le)"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Số tiền",nnId) %></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="SoTien"></td>
											</tr>		
																					
										</tbody>
									</TABLE>
								</fieldset>
							</div>
							<div
								style="width: 35px; float: left; min-height: 200px; vertical-align: middle; display: none"
								align="center">
								<br> <br> <br> <img src="../images/Back30.png"
									border="0" class="imageClick" onClick="ChuyenSangPhai();"><br />
								<br> <br> <img src="../images/Next30.png" border="0"
									class="imageClick" onClick="ChuyenSangTrai();">
							</div>
							<div style="width: 30%; float: left; display: none">
								<fieldset style="min-height: 200px;">
									<legend class="legendtitle"> <%=ChuyenNgu.get("Dữ liệu ẩn",nnId) %> </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbAllFields">
										<tbody id="AllFields">
											<tr class="tbheader">
												<th></th>
												<th align="center"><%=ChuyenNgu.get("Chọn hiển thị",nnId) %></th>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Nhãn hàng",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="NhanHang"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Chủng loại",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="ChungLoai"></td>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Mã nhà phân phối",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="MaNhaPhanPhoi"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="DaiDienKinhDoanh"></td>
											</tr>

											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Tỉnh/Thành",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Tinh/Thanh"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Quận/Huyện",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Quan/Huyen"></td>
											</tr>

											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Mã khách hàng",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="MaKhachHang"></td>
											</tr>
											
											
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Tên khách hàng",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="TenKhachHang"></td>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Ngày",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Ngay"></td>
											</tr>		

											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Loại chương trình",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="LoaiChuongTrinh"></td>
											</tr>
																						
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Loại chương trình",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="SoLuong(Thung)"></td>
											</tr>											
										</tbody>
									</TABLE>
								</fieldset>
							</div>
		

						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<br /> <br /> <br /> <br />
	</form>
<%
	if(kenh !=null){ kenh.close(); kenh = null; }
	if(vung !=null){ vung.close(); vung = null;	}
	if(khuvuc !=null){ khuvuc.close(); khuvuc = null;}
	if(npp !=null){ npp.close(); npp = null;}
	if(programs!=null){ programs.close(); programs = null;}
	
	if(obj!=null) {	
		obj.DBclose();
		obj = null;
	}
	session.setAttribute("obj", null);
}%>

</body> 
<script language="javascript" type="text/javascript">
	load();
</script>	
</html>