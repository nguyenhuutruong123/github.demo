<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.util.Utility"%>
<%@page import="geso.dms.distributor.beans.report.Ireport"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page  import = "geso.dms.center.util.*" %>	
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
	<%	
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
	<%
	
	Ireport obj = (Ireport)session.getAttribute("obj");
	ResultSet khachhang = obj.getkhachhang();
	ResultSet ddkd = obj.getddkd();
	  
	int[] quyen = new  int[6];
	quyen = util.Getquyen("BCDonHangHuyTrongKy","", userId);
	
	%>
	
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 
 } 
String url = util.getChuyenNguUrl("BCDonHangHuyTrongKy","",nnId); 
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
<script type="text/javascript">
	function submitform() {
		if (document.forms["frm"]["Sdays"].value.length==0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return false;
		}
		if (document.forms["frm"]["Edays"].value.length==0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return false;
		}		
	/*	if (document.forms["frm"]["ddkds"].value == "0") {
			setErrors("Chọn Nhân viên bán hàng");
			return false;
		}
		if (document.forms["frm"]["khachs"].value == "0") {
			setErrors("Chọn khách hàng");
			return false;
		}
		*/
		var fieldShow = document.getElementsByName("fieldsHien");
		var fieldHidden = document.getElementsByName("fieldsAn");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}
		/*
		for ( var i = 0; i < fieldHidden.length; ++i) {
			fieldHidden.item(i).checked = true;
		}
		*/
		//document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
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
	function seach()
	{
		document.forms['frm'].action.value= 'seach';
		document.forms["frm"].submit();
	}
</script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select").select2(); 
    	});
     
 	</script>	

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="frm" method="post" action="../../BCDonHangHuyTrongKy">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%= " " + url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng",nnId)%> <%= userTen %></div>
			</div>

			
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>

					<legend class="legendtitle"><%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId)%></legend>
					<textarea id="errors" value="<%= session.getAttribute("errors") %>" 
						name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Báo cáo đơn hàng hủy trong kỳ",nnId)%></legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
										<TD class="plainlabel"><%=ChuyenNgu.get("Từ ngày",nnId)%></TD>
											<TD class="plainlabel">	<input type="text" name="Sdays" class="days" value='<%= obj.gettungay() %>' />
											</TD>
											<TD class="plainlabel"><%=ChuyenNgu.get("Đến ngày",nnId)%></TD>
											<TD class="plainlabel">
												<input type="text" name="Edays" class="days" value='<%= obj.getdenngay() %>'/>
											</TD>
									</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Đại diện KD",nnId)%></TD>
									<TD class="plainlabel"><select name="ddkdId" id="ddkdId" onchange="seach();">
											<option value="" >All</option>
										<% if(ddkd !=null){ 
											while(ddkd.next())
											{ 
											if(ddkd.getString("pk_seq").equals(obj.getddkdId())) {
											%>
											<option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
											<%} else { %>
											<option value="<%=ddkd.getString("pk_seq")%>" ><%=ddkd.getString("ten")%></option>
											<%}}} %>
											</select></TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Khách Hàng",nnId)%></TD>
									<TD class="plainlabel">
									<select name="khachhangId" id="khachhangId" onchange="seach();">
									<option value="" >All</option>
											<% if(khachhang !=null){ 
											while(khachhang.next())
											{ 
											if(khachhang.getString("pk_seq").equals(obj.getkhachhangId())) {
											%>
												<option value="<%=khachhang.getString("pk_seq")%>" selected><%=khachhang.getString("ten")%></option>
											<%} else { %>
												<option value="<%=khachhang.getString("pk_seq")%>" ><%=khachhang.getString("ten")%></option>
											<%}}} %>
									</select></TD>
								</TR>
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""><%=ChuyenNgu.get("Tạo báo cáo",nnId)%>
									</a></td>
								</TR>
							</TABLE>
						</div>
						<div style="width: 100%; float: none; display:none;">
							<div style="width: 30%; float: left">
								<fieldset style="min-height: 200px">
									<legend class="legendtitle"><%=ChuyenNgu.get("Fields hiện ",nnId)%></legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbShowFields">
										<tbody id="ShowFields">
											<tr class="tbheader">
												<th></th>
												<th align="center"><%=ChuyenNgu.get("Chọn ẩn",nnId)%></th>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Người xóa",nnId)%></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Nguoi_xoa"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Ngày xóa",nnId)%></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Ngay_xoa"></td>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Tên khách hàng",nnId)%></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Ten_khach_hang"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Số hóa đơn",nnId)%></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="So_hoa_don"></td>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Ngày hóa đơn",nnId)%></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Ngay_hoa_don"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Tên hàng",nnId)%></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Ten_hang"></td>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Tổng tiền",nnId)%></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Tong_tien"></td>
											</tr>
										</tbody>
									</TABLE>
								</fieldset>
							</div>
							<div
								style="width: 35px; float: left; min-height: 200px; vertical-align: middle"
								align="center">
								<br> <br> <br> <img src="../images/Back30.png"
									border="0" class="imageClick" onClick="ChuyenSangPhai();"><br />
								<br> <br> <img src="../images/Next30.png" border="0"
									class="imageClick" onClick="ChuyenSangTrai();">
							</div>
							<div style="width: 30%; float: left;">
								<fieldset style="min-height: 200px;">
									<legend class="legendtitle"><%=ChuyenNgu.get("Tất cả các Fields",nnId)%> </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbAllFields">
										<tbody id="AllFields">
											<tr class="tbheader">
												<th></th>
												<th align="center"><%=ChuyenNgu.get("Chọn hiện",nnId)%></th>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Chiết khấu",nnId)%></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Chiet_khau"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Mã hàng",nnId)%></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Ma_hang"></td>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Chương trình khuyến mãi",nnId)%></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="CTKM"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Địa chỉ",nnId)%></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Dia_chi"></td>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Mã khách hàng",nnId)%></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Ma_khach_hang"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Số lượng",nnId)%></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="So_luong"></td>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Đơn giá",nnId)%></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Don_gia"></td>
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
	<script type="text/javascript">
		$("select").css({
			"width": "200px", 
			//"height": "200px"
		});
	</script>
	
</body> 
</html>
	
<%
	try
	{
		if(!(ddkd == null)){ ddkd.close(); ddkd = null ;}
		if(khachhang!=null){ khachhang.close();  khachhang = null ;}
		
		if(obj != null){
	    	obj = null ;
	    } 
		session.setAttribute("obj",null);
		
	}catch(java.sql.SQLException e){}
}
	
%>