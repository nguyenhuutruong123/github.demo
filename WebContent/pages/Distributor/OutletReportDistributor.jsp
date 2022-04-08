
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="geso.dms.center.util.Utility"%>
<%@page import="java.sql.ResultSet"%>
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

	IStockintransit obj = (IStockintransit)session.getAttribute("obj");
	ResultSet rsKenh = obj.getkenh();	
	ResultSet rsDdkd = obj.getRsddkd();
	  
	int[] quyen = new  int[6];
	quyen = util.Getquyen("OutletReportDistributorSvl","", userId);
	
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
	function search(){
		document.forms["frm"]["action"].value = "search";
		document.forms["frm"].submit();
	}
	function submitform() {
		if (document.forms["frm"]["Sdays"].value.length == 0 || document.forms["frm"]["Sdays"].value==null) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return false;
		}
		var fieldShow = document.getElementsByName("fieldsHien");
		var fieldHidden = document.getElementsByName("fieldsAn");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}
		for ( var i = 0; i < fieldHidden.length; ++i) {
			fieldHidden.item(i).checked = true;
		}
		document.forms["frm"]["action"].value = "create";
		document.forms["frm"].submit();
		reset();
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");
		var fieldHidden = document.getElementsByName("fieldsAn");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}
		for ( var i = 0; i < fieldHidden.length; ++i) {
			fieldHidden.item(i).checked = false;
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

	<form name="frm" method="post" action="../../OutletReportDistributorSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" value="1" name="action"  >
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo quản trị &#62;Theo dõi doanh số &#62; Doanh số theo cửa hiệu</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= session.getAttribute("userTen") %></div>
			</div>
			
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" 	name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
					<%= obj.getMsg() %>
					</textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Doanh số theo cửa hiệu</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel"><input type="text" name="Sdays"
										class="days" value='<%=obj.gettungay()%>' /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<TD class="plainlabel"><input type="text" name="Edays"
										class="days" value='<%=obj.getdenngay()%>' /></TD>
								</TR>
								<TR>
									<TD class="plainlabel">Kênh bán hàng</TD>
									<TD class="plainlabel"><select onchange="search();"  name="kenhs" id="loaiCt">
											<option value="">All</option>
											<% if(rsKenh!=null){
													while(rsKenh.next()){
														String kenhId = rsKenh.getString(1);
														if(kenhId.equals(obj.getkenhId())){
															%>
															<option selected="selected" value="<%=rsKenh.getString(1) %>"><%=rsKenh.getString(2)%></option>
														<%}else{%>
															<option value="<%=rsKenh.getString(1) %>"><%=rsKenh.getString(2)%></option>
											<%}}}%>
									</select></TD>									
									<TD class="plainlabel">Nhân viên bán hàng</TD>
									<TD class="plainlabel">
										<select value="" name="ddkds">
											<option value="">All</option>
											<% if(rsDdkd!=null){
													while(rsDdkd.next()){
														String ddkdId = rsDdkd.getString(1);
														if(ddkdId.equals(obj.getDdkd())){%>
															<option selected="selected" value="<%=rsDdkd.getString(1)%>"><%=rsDdkd.getString(2)%></option>
														<%}else{%>
														<option value="<%=rsDdkd.getString(1)%>"><%=rsDdkd.getString(2)%></option>
											<%}}}%>
										</select>
									</TD>
								</TR>
								
								<tr>
									<td class="plainlabel">Gồm những KH<br/>không có doanh số</td>
									<td class="plainlabel"><input type="checkbox" name="check" id="check"></td>
								</tr>								
								
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt="">Tạo báo cáo
									</a></td>
								</TR>
							</TABLE>
						</div>
						<hr>
						<div style="width: 100%; float: none; display: none ">
							<div style="width: 30%; float: left;">
								<fieldset style="min-height: 200px;">
									<legend class="legendtitle"> Fields ẩn </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbShowFields">
										<tbody id="ShowFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chon an</th>
											</tr>
											<tr class="tbdarkrow">
												<td>Kênh bán hàng</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Channel"></td>
											</tr>
											<tr class="tblightrow">
												<td>Nhân viên bán hàng</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="SalesRep"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Khách hàng</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Customer"></td>
											</tr>
											<tr class="tblightrow">
												<td>Doanh số cao nhất trên tháng</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="HighestEverVolume"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Ngày mua đầu tiên khách hàng</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="FirstBuyDate"></td>
											</tr>
											<tr class="tblightrow">
												<td>Ngày mua lần cuối khách hàng</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="LastBuyDate"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Doanh số trung bình trên tháng</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Monthly_Avg_second_sales"></td>
											</tr>
											<tr class="tblightrow">
												<td>Doanh số bán ra từ nhà phân phối</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="MonthlyArchiveSecondSales"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Tổng đơn hàng</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Order"></td>
											</tr>
										</tbody>
									</TABLE>
								</fieldset>
							</div>
							<div
								style="width: 35px; float: left; min-height: 200px; vertical-align: middle;display: none"
								align="center">
								<br> <br> <br> <img src="../images/Back30.png"
									border="0" class="imageClick" onClick= "ChuyenSangPhai();"><br />

								<br> <br> <img src="../images/Next30.png" border="0"
									class="imageClick" onClick="ChuyenSangTrai();">
							</div>
						</div>
						<div style="width: 30%; float: left; display: none">
								<fieldset style="min-height: 200px">
									<legend class="legendtitle"> Tất cả các Fields </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbAllFields">
										<tbody id="AllFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chon hien</th>
											</tr>
											<tr class="tblightrow">
												<td>Tỉnh thành</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Province"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Mã nhà phân phối</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="DistributorCode"></td>
											</tr>
																																							
										</tbody>
									</TABLE>
								</fieldset>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<br /> <br /> <br /> <br />		
	</form>
</body>
</html>
<%
	try{
		
		if( rsKenh !=null){
			rsKenh.close(); rsKenh = null;
		}
		if( rsDdkd !=null){
			rsDdkd.close(); rsDdkd = null;
		}
		if(obj!=null){
			obj.DBclose();
			obj=null;
		}
		session.setAttribute("obj",null);
		
	}catch(Exception er){}
}
%>