<%@page import="geso.dms.center.util.Utility"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
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
	ResultSet rsKhuVuc = obj.getkhuvuc();
	ResultSet rsVung = obj.getvung();
	
	ResultSet rsNpp = obj.getnpp();
	ResultSet rsGsbh = obj.getgsbh();
	ResultSet rsDdkd = obj.getRsddkd();
	ResultSet rsNhans = obj.getnhanhang();
	ResultSet rsChungLoai = obj.getchungloai();
	ResultSet rsDVKD = obj.getdvkd();
	ResultSet rsProgram = obj.getRsPrograms();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("UsingPromotionTTSvl","", userId);
%>
<% String nnId = (String)session.getAttribute("nnId"); %>
<% if(nnId == null) {
 nnId = "vi";  
 }
 String url = util.getChuyenNguUrl("UsingPromotionTTSvl","" ,nnId);
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
		if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return ;
		}			
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}		
		document.forms["frm"]["action"].value = "Taomoi";
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

	<form name="frm" method="post" action="../../UsingPromotionTTSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" value="1" name="action"  >
		<input type="hidden" value="<%=obj.getView() %>" name="view"  >
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng",nnId) %> <%= obj.getuserTen() %> </div>
			</div>
			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="#"> <img src="../images/Back30.png" alt="Quay ve"
					title="Quay ve" border="1px" longdesc="Quay ve"
					style="border-style: outset"></A> <A href="javascript:saveform()">
					<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai"
					border="1px" style="border-style: outset">
				</A>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> <%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %></legend>
					<textarea id="errors" 	name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
					<%= obj.getMsg() %>
					</textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"> <%=ChuyenNgu.get("Sử dụng và phân bổ khuyến mãi",nnId) %> </legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="100%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
									<TD class="plainlabel"><input type="text" name="Sdays"
										class="days" value="<%=obj.gettungay() %>" /></TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Đến ngày",nnId) %></TD>
									<TD class="plainlabel"><input type="text" name="Edays"
										class="days" value="<%=obj.getdenngay() %>" /></TD>
								</TR>
								
								<%if(obj.getView().equals("TT")){ %>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Vùng/Miền",nnId) %></TD>
									<TD class="plainlabel">
										<select onchange="search();"  name="vungId">
											<option value=""><%=ChuyenNgu.get("All",nnId) %></option>
											<% if(rsVung!=null){
													while(rsVung.next()){
														String vungId = rsVung.getString(1);
														if(vungId.equals(obj.getvungId())){%>
															<option selected="selected"  value="<%=rsVung.getString(1) %>"><%=rsVung.getString(2)%></option>
														<%}else{%>
														<option  value="<%=rsVung.getString(1) %>"><%=rsVung.getString(2)%></option>
															
											<%}}}%>										
										</select>
									</TD>
									
										
									<TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %></TD>
									<TD class="plainlabel">
										<select onchange="search();"  name="khuvucId">
											<option value="">All</option>
											<% if(rsKhuVuc!=null){
													while(rsKhuVuc.next()){
														String khuvucId = rsKhuVuc.getString(1);
														if(khuvucId.equals(obj.getkhuvucId())){%>
															<option selected="selected" value="<%=rsKhuVuc.getString(1)%>"><%=rsKhuVuc.getString(2)%></option>
														<%}else{%>
														<option value="<%=rsKhuVuc.getString(1)%>"><%=rsKhuVuc.getString(2)%></option>
											<%}}}%>
										</select>
									</TD>
								</TR>
								<%} %>
								
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId) %></TD>
									<TD class="plainlabel"><select onchange="search();"  name="kenhId" id="loaiCt">
											<option value=""><%=ChuyenNgu.get("All",nnId) %></option>
											<% if(rsKenh!=null){
													while(rsKenh.next()){
														String kenhId = rsKenh.getString(1);
														if(kenhId.equals(obj.getkenhId())){%>
															<option selected="selected" value="<%=rsKenh.getString(1) %>">
																	<%=rsKenh.getString(2)%></option>
														<%}else{%>
															<option value="<%=rsKenh.getString(1) %>"><%=rsKenh.getString(2)%></option>
											<%}}}%>
									</select></TD>
									
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
									<TD class="plainlabel">
										<select  onchange="search();"  name="nppId">
											<option value=""><%=ChuyenNgu.get("All",nnId) %></option>
											<% if(rsNpp!=null){
													while(rsNpp.next()){
														String nppId = rsNpp.getString(1);
														if(nppId.equals(obj.getnppId())){%>
															<option selected="selected" value="<%=rsNpp.getString(1)%>"><%=rsNpp.getString(2)%></option>
														<%}else{%>
														<option value="<%=rsNpp.getString(1)%>"><%=rsNpp.getString(2)%></option>
											<%}}}%>
										</select>
									</TD>									
								</TR>
								
								<TR>	
								
								<TD class="plainlabel"><%=ChuyenNgu.get("Chương trình khuyến mãi",nnId) %></TD>
									<TD class="plainlabel" colspan="3">
										<select  name="programs" style="width: 800px" >
											<option value=""><%=ChuyenNgu.get("All",nnId) %></option>
											<% if(rsProgram!=null){
										 		while(rsProgram.next()){
													String programId = rsProgram.getString("SCHEME");
													if(programId.equals(obj.getPrograms())){%>
														<option selected="selected" value="<%=rsProgram.getString("SCHEME")%>">
															<%=rsProgram.getString("SCHEME") + "<-->" + rsProgram.getString("DIENGIAI")%></option>
													<%}else{%>
														<option value="<%=rsProgram.getString("SCHEME")%>">
															<%=rsProgram.getString("SCHEME") + "<-->" + rsProgram.getString("DIENGIAI")%></option>
										 	<% }}}%>
										 	
										</select>
									</TD>								
									
									

								</TR>
								<TR>																		
									<%-- <TD class="plainlabel">Nhãn hàng</TD>
									<TD class="plainlabel">
										<select  name="nhanhangId">
											<option value="">All</option>
										 	<% if(rsNhans!=null){
										 		while(rsNhans.next()){
													String nhanId = rsNhans.getString(1);
													if(nhanId.equals(obj.getnhanhangId())){%>
														<option selected="selected" value="<%=rsNhans.getString(1)%>"><%=rsNhans.getString(2)%></option>
													<%}else{%>
														<option value="<%=rsNhans.getString(1)%>"><%=rsNhans.getString(2)%></option>
										 	<% }}}%>
										</select>
									</TD> --%>
									<%-- <TD class="plainlabel">Chủng loại</TD>
									<TD class="plainlabel">
										<select  name="chungloaiId">
											<option value="">All</option>
										 	<% if(rsChungLoai!=null){
										 		while(rsChungLoai.next()){
													String chungLoaiId = rsChungLoai.getString(1);
													if(chungLoaiId.equals(obj.getchungloaiId())){%>
														<option selected="selected" value="<%=rsChungLoai.getString(1)%>"><%=rsChungLoai.getString(2)%></option>
													<%}else{%>
														<option value="<%=rsChungLoai.getString(1)%>"><%=rsChungLoai.getString(2)%></option>
										 	<% }}}%>
										</select>
									</TD> --%>
								</TR>
								
								<TR>						
																		
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
						<div style="width: 100%; float: none; display: none">
							<div style="width: 30%; float: left;">
								<fieldset style="min-height: 200px;">
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
												<td><%=ChuyenNgu.get("Tên chương trình",nnId) %></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="TenChuongTrinh"></td>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Ngân sách phân bổ",nnId) %></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="NganSachPhanBo"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Đã sử dụng",nnId) %> </td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="DaSuDung"></td>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Ngân sách còn lại",nnId) %></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="NganSachConLai"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Số suất còn lại",nnId) %></td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="SoSuatConLai"></td>
											</tr>
											
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Phần trăm sử dụng",nnId) %> </td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="%SuDung"></td>
											</tr>
										</tbody>
									</TABLE>
								</fieldset>
							</div>
							<div
								style="width: 35px; float: left; min-height: 200px; vertical-align: middle"
								align="center">
								<br> <br> <br> <img src="../images/Back30.png"
									border="0" class="imageClick" onClick= "ChuyenSangPhai();"><br />

								<br> <br> <img src="../images/Next30.png" border="0"
									class="imageClick" onClick="ChuyenSangTrai();">
							</div>
						</div>
						<div style="width: 30%; float: left; display: none">
								<fieldset style="min-height: 200px">
									<legend class="legendtitle"> <%=ChuyenNgu.get("Dữ liệu ẩn",nnId) %> </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbAllFields">
										<tbody id="AllFields">
											<tr class="tbheader">
												<th></th>
												<th align="center"><%=ChuyenNgu.get("Chọn hiện",nnId) %></th>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Mã nhà phân phối",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="MaNhaPhanPhoi"></td>
											</tr>
											<tr class="tbdarkrow">
												<td><%=ChuyenNgu.get("Từ ngày",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="TuNgay"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Đến ngày",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="DenNgay"></td>
											</tr>
											<tr class="tblightrow">
												<td><%=ChuyenNgu.get("Hình thức",nnId) %></td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="HinhThuc">
												</td>
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
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<%
	try
	{
		if(rsKenh != null){ rsKenh.close();	rsKenh = null;}	
		if(rsKhuVuc != null){ rsKhuVuc.close();	rsKhuVuc = null;}
		if(rsVung != null){ rsVung.close();	rsVung = null;}
		if(rsNpp != null){ rsNpp.close();	rsNpp = null;}
		if(rsGsbh != null){ rsGsbh.close();	rsGsbh = null;}
		if(rsDdkd != null){ rsDdkd.close();	rsDdkd = null;}
		if(rsNhans != null){ rsNhans.close();	rsNhans = null;}
		if(!(rsChungLoai == null)){ rsChungLoai.close();	rsChungLoai = null;}
		if(rsDVKD != null){ rsDVKD.close();		rsDVKD = null;}
		if(rsProgram != null){ rsProgram.close();	rsProgram = null;}
		
		if(obj != null) {
			obj.DBclose();		
			obj = null;
		}
		session.setAttribute("obj",null);
	}catch(java.sql.SQLException e){}
}
%>
