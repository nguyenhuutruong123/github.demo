<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.sql.ResultSet"%>

<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");
	IStockintransit obj = (IStockintransit)session.getAttribute("obj");
	ResultSet npp = obj.getnpp();
	String nppId = obj.getnppId();
	ResultSet vung = obj.getvung();
	ResultSet tinhthanh = obj.GetTinhThanh();
	ResultSet ddkd = obj.getRsddkd();
	
	

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>SalesUp - Project</TITLE>
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

<!-- Khai bao su dung colorbox ajax -->
<link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>

    
    
<script type="text/javascript">

function seach() {
	document.forms['frm'].action.value = 'seach';
	document.forms["frm"].submit();
}
	function submitform() 
	{
		
		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
	}
	function xemtrenweb() 
	{

		document.forms['frm'].action.value= 'av';
		document.forms["frm"].submit();
	}
	
</script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>	
    
<style>
.Text{
        font-family: Verdana, Arial, Sans-serif, 'Times New Roman';
        font-size: 8pt;
        font-weight: normal;
        font-style: normal;
        color: #333333;
        text-decoration: none;
}
.toolTip {
        font-family: Verdana, Arial, Sans-serif, 'Times New Roman';
        font-size: 8pt;
        filter:alpha(opacity=80);
        -moz-opacity: 0.8;
        opacity: 0.8;
        /* comment the above 3 line if you don't want transparency*/
}

</style>
    

   

<BODY  leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="frm" method="post" action="../../BCThucDatDoanhSoNhanVien">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="userId" value='<%=userId%>'>
	<input type="hidden" name="view" value='<%=obj.getLoaiMenu()%>'>
	
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý chỉ tiêu &#62; Báo cáo &#62; Báo cáo thực đạt doanh số nhân viên</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %></div>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>

					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" value="<%= session.getAttribute("errors") %>" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"></textarea>
				</fieldset>
			</div>
			
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Báo cao thuc dat doanh so nhan vien</legend>
					<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
										
										<TD class="plainlabel">Đến ngày </TD>
										<TD class="plainlabel">
												<input type="text" name="denngay" id="denngay" class="days" value='<%= obj.getdenngay() %>' onchange="xemtrenweb();"/>
											</TD>
									</TR>
									<%if(1==1){} else if(obj.getLoaiMenu().equals("TT")){%>
										<TR>
										<TD class="plainlabel">Miền</TD>
										<TD class="plainlabel">
											<select name="vungId" id="vungId" onchange="xemtrenweb();" style="width: 300px">
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
										<TD class="plainlabel">Tỉnh thành</TD>
										<TD class="plainlabel">
											<select name="tinhthanhId" id="tinhthanhId" onchange="seach();" style="width: 300px">
												<option value="" selected>All</option>
												<%if (tinhthanh != null)
														while (tinhthanh.next()) {
															if (tinhthanh.getString("pk_seq").equals(obj.getTinhthanhid())) {%>
																<option value="<%=tinhthanh.getString("pk_seq")%>" selected><%=tinhthanh.getString("ten")%></option>
														<%} else {%>
															<option value="<%=tinhthanh.getString("pk_seq")%>"><%=tinhthanh.getString("ten")%></option>
														<%}}%>
											</select>
										</TD>
									</TR>
								
									<TR>
										<TD class="plainlabel">Chi nhánh/Đối tác </TD>
										<TD class="plainlabel" colspan="3" > 
											<select name="nppId" onchange="seach();" id="nppId" style="width: 300px">
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
										
										
								</TR>
								<%}else{ %>
								<input type="hidden" name="nppId" value='<%=obj.getnppId()%>'>
								<%} %>
								<TR  style="display: none">
									<TD class="plainlabel">Nhân viên bán hàng</TD>
									<TD class="plainlabel">
										<select name="ddkdId" id="ddkdId"	onchange="seach();" style="width: 300px">
											<option value="" selected>All</option>
											<%if (ddkd != null)
													while (ddkd.next()) {
														if (ddkd.getString("pk_seq").equals(obj.getDdkd())) {%>
														<option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
													<%} else {%>
														<option value="<%=ddkd.getString("pk_seq")%>"><%=ddkd.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
									
								</TR>
							
								<TR>
									<td colspan="4"><a class="button" href="javascript:submitform();"> 
										<img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo
									</a></td>
								</TR>
							</TABLE>
						</div>
				
				</fieldset>
			</div>
		</div>
		<br />
	</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>