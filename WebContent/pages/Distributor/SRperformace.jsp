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
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
		
	ResultSet kenh = obj.getkenh();

	ResultSet dvkd = obj.getdvkd();

	ResultSet ddkd =obj.getRsddkd();
	ResultSet dvdl = obj.getdvdl();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("Srperfomance","", userId);

%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("Srperfomance","",nnId);	
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
//		if (document.forms["frm"]["Sdays"].value.length == 0) {
//			setErrors("Vui lòng chọn ngày bắt đầu");
//			return ;
//		}
//		if (document.forms["frm"]["Edays"].value.length == 0) {
//			setErrors("Vui lòng chọn ngày kết thúc");
//			return ;
//		}

		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}
		//document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
		reset();
	//	for ( var i = 0; i < fieldShow.length; ++i) {
	//		fieldShow.item(i).checked = false;
	//	}
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
	};
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
		action="../../Srperfomance"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> <input
			type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%= " " + url %> </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng bạn",nnId) %>	<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> <%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %></legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Thực hiện chỉ tiêu Nhân viên bán hàng",nnId) %>
					</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">

							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Chọn năm",nnId) %></TD>
									
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
									
									<TD class="plainlabel"><%=ChuyenNgu.get("Chọn tháng",nnId) %></TD>
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
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId) %></TD>
									<TD class="plainlabel"><select name="kenhId" id="kenhId"
										onchange="seach();">
											<option value="" selected>All</option>
											<%
												if (kenh != null)
													while (kenh.next()) {
														if (kenh.getString("pk_seq").equals(obj.getkenhId())) {
											%>
											<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("diengiai")%></option>
											<%
												} else {
											%>
											<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("diengiai")%></option>
											<%
												}
													}
											%>
									</select></TD>
								</TR>
								
								
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %></TD>
									<TD class="plainlabel"><select name="dvkdId" id="dvkdId"
										onchange="seach();">
											<option value="" selected>All</option>
											<%
												if (dvkd != null)
													while (dvkd.next()) {
														if (dvkd.getString("pk_seq").equals(obj.getdvkdId())) {
											%>
											<option value="<%=dvkd.getString("pk_seq")%>" selected><%=dvkd.getString("diengiai")%></option>
											<%
												} else {
											%>
											<option value="<%=dvkd.getString("pk_seq")%>"><%=dvkd.getString("diengiai")%></option>
											<%
												}
													}
											%>
									</select></TD>	
									
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></TD>
									<TD class="plainlabel"><select name="ddkdId" id="ddkdId"
										onchange="seach();">
											<option value="" selected>All</option>
											<%
												if (ddkd != null)
													while (ddkd.next()) {
														if (ddkd.getString("pk_seq").equals(obj.getvat())) {
											%>
											<option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
											<%
												} else {
											%>
											<option value="<%=ddkd.getString("pk_seq")%>"><%=ddkd.getString("ten")%></option>
											<%
												}
													}
											%>
									</select></TD>	
									
								</TR>
								
								
								<TR>
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> <%=ChuyenNgu.get("Tạo báo cáo",nnId) %> </a>
									</td>
								</TR>
							</TABLE>
						</div>
						
							


						</div>
					</div>
				
			</div>
		
		<br /> <br /> <br /> <br />
	</form>
</body>
</html>

<%

	if(kenh != null){ kenh.close(); kenh = null; }
	if(dvkd != null){ dvkd.close(); dvkd = null; }
	if(ddkd != null){ ddkd.close(); ddkd = null; }
	if(dvdl != null){ dvdl.close(); dvdl= null; }
	obj.DBclose(); obj = null;
	session.setAttribute("obj",null);
}
%>