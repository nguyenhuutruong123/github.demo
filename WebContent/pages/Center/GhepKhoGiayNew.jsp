<%@ page import="java.util.List"%>
<%@page import="java.util.Hashtable"%>
<%@ page import="geso.dms.center.beans.ghepkhogiay.*"%>
<%@page import="geso.dms.center.util.Utility"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
	<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	String action = (String) session.getAttribute("action");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TheGioNuocHoa/index.jsp");
	}else{ %>
<%
	KeHoachSanXuatN obj = (KeHoachSanXuatN) session.getAttribute("obj");
	KeHoachN keHoach = obj.getKeHoach();
	Hashtable<Float, KichBanN> toHopMay = keHoach.getToHopMay(); 
	List<MaySanXuat> maySanXuatList = obj.getMaySanXuat();
	session.setAttribute("toHopMay", toHopMay);
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
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		//$("select:not(.notuseselect2)").select2({ width: 'resolve' }); 
		$(".select2").select2();
		
		$("ul.tabs").tabs("div.panes > div");
		
	});
</script>

<script type="text/javascript" src="../scripts/ajax_erp_KH_SP_CK.js"></script>

<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100;
}

#ajax_listOfOptions div {
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5;
}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
}
</style>
<script language="javascript" type="text/javascript">	
	function exportExcell()
	{
		document.forms['frm'].action.value= 'exportExcell';
		document.forms["frm"].submit();
	}

	function importExcell()
	{
		document.forms['frm'].action.value= 'importExcell';
		document.forms["frm"].submit();
	}

	function validate() {
		if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return false;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return false;
		}
	}

	function submitform() {
		document.forms['frm'].action.value= 'create';
		document.forms["frm"].submit();
		reset();
	}

	function upload()
	{
		document.forms['frm'].action.value='upload';
		document.forms['frm'].setAttribute('enctype', "multipart/form-data", 0);
	   	document.forms['frm'].submit();
	}
	
	function saveform()
	{	  
		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		document.forms['frm'].action.value= 'save';
		document.forms["frm"].submit();
		reset();
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

	<form name="frm" method="post" action="../../GhepKhoGiayNewSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=userId%>'>
		<%System.out.println("id: " + obj.getId()); %>
		<input type="hidden" name="id" id="maKichBan" value='<%= obj.getId() %>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left" class="tbnavigation">
				<%if (action.trim().equals("save")){ %>
					&nbsp;Quản lý bán hàng &nbsp; &gt; Lập kế hoạch sản xuất &nbsp; &gt; Tạo mới
					<%}else if (action.trim().equals("view")){ %>
					&nbsp;Quản lý bán hàng &nbsp; &gt; Lập kế hoạch sản xuất &nbsp; &gt; Xem
					<%}else {%>
					&nbsp;Quản lý bán hàng &nbsp; &gt; Lập kế hoạch sản xuất &nbsp; &gt; Cập nhật
					<%} %>
				</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%></div>
			</div>
			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="javascript:history.back()"> <img
					src="../images/Back30.png" alt="Quay ve" title="Quay ve" width="30"
					height="30" border="1" longdesc="Quay ve"
					style="border-style: outset">
				</A> 
				<%if (!action.equals("view")){ %>
				<label id="btnSave"> <A href="javascript:saveform()"> <IMG
						src="../images/Save30.png" title="Lưu lại" alt="Lưu lại"
						border="1" style="border-style: outset">
				</A> 
				</label>
				<%} %>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Xuất nhập tồn</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">

							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="planame="id"inlabel">Từ ngày</TD>
									<TD class="plainlabel"><input type="text" name="Sdays"
										class="days" value='<%=obj.getTuNgay()%>' /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<td><input type="text" name="Edays" class="days"
										value='<%=obj.getDenNgay()%>' /></td>
								</TR>
<!-- 								<TR> -->
<!--                                     <TD class="plainlabel">Máy sản xuất </TD> -->
<!--                                     <TD  class="plainlabel"> -->
<!--                                     <SELECT name ="maySanXuat" onChange = "validate();"> -->
<!--                                     	<option value="" selected>All</option> -->
<%--                                         <%if (maySanXuatList != null){ --%>
<!--                                          	for (MaySanXuat maySanXuat : maySanXuatList) -->
<!--                                          	{ -->
<!--                                          		if (maySanXuat.getId().equals(obj.getMaMaySanXuat()) == true){%> -->
<%--                                          <option selected value="<%=obj.getMaMaySanXuat() %>" selected><%= maySanXuat.getMa() %></option> --%>
<%--                                         		<%}else{%> --%>
<%--                                          <option value="<%=obj.getMaMaySanXuat() %>"><%= maySanXuat.getMa() %></option> --%>
<%--                                         		<%} --%>
<!--                                          	} -->
<!-- 										}%> -->
<!--                                         </SELECT> -->
<!--                                        </TD> -->
                                        
<!--                                 </TR> -->
								<%if (!action.equals("view")) {%>
                               	<TR>
									<TD class="plainlabel">Chọn tập tin</TD>
									<TD class="plainlabel"><INPUT type="file" name="filename" size="40" value='upload'></TD>
								</TR>
								
								<TR>
									<TD class="plainlabel" colspan="4">
										<label id="btUpload">
											<a class="button" href="javascript:upload();"> <img style="top: -4px;" src="../images/button.png" alt=""> Upload 
											</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</label>
											<a class="button" href="javascript:submitform();"> 
												<img style="top: -4px;" src="../images/button.png" alt=""> Tạo kế hoạch 
											</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</td>
								</TR>
								<%} %>
								
								<%System.out.println("action is: " + action); if (!action.equals("save")){ %>
								<TR>
									<TD>
										<a class="button" href="javascript:exportExcell();"> 
											<img style="top: -4px;" src="../images/button.png" alt=""> Xuất Excel 
										</a>
									</TD>
								</TR>
								<%} %>
							</TABLE>
							<div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
								<hr> 
								<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
									<TR>
										<TD colspan="6" >
											<ul class="tabs">
												<%if (maySanXuatList != null){ 
												for (MaySanXuat maySanXuat : maySanXuatList){%>
												<li><a href="#" style="color: red; font-weight: bold; " ><%=maySanXuat.getMa() + "_" + maySanXuat.getKhoGiayNguyenLieu()%></a></li>
												<%}} %>
				                  			</ul>
				                  			<div class="panes">
				                  			<%for (MaySanXuat maySanXuat : maySanXuatList){%>
				                  				<div>
				                  				<%Set<Float> toHopmayKeys1 = toHopMay.keySet();
							 					 for (float khoGiayNguyenLieu1 : toHopmayKeys1)
							 					 {
							 						 if (khoGiayNguyenLieu1 == maySanXuat.getKhoGiayNguyenLieu()){
							 					 %>
													<TABLE class="tabledetail" width="100%" border="1" cellpadding="1" cellspacing="1" >
														<TR class="tbheader"> 
															<TH align="center" width="5%">STT</TH>
															<TH align="center" width="7%" rowspan="2">Mã tổ hợp</TH>
										                	<TH align="center" width="10%">Tên sản phẩm</TH>
										                	<TH align="center" width="10%" rowspan="2">Item</TH>
										                	<TH align="center" width="10%">Định lượng</TH>
										                	<TH align="center" width="20%" colspan="2">Kế hoạch máy xeo</TH>
										                	<TH align="center" width="25%" colspan="3">KH sang cuộn(Cut size)</TH>
										                	<TH align="center" width="10%" rowspan="2">PO Number</TH>
										                	<TH align="center" width="15%" rowspan="2">Tên khách hàng (Customer name)</TH>
										                	<TH align="center" width="5%" rowspan="2">MSX</TH>
									                	</TR>		
									                	<TR class="tbheader"> 
															<TH align="center" width="5%">No.</TH>
										                	<TH align="center" width="10%">(Product Name)</TH>
										                	<TH align="center" width="10%">(± 4g)</TH>
										                	<TH align="center" width="8%">Khổ(Full size)(cm)</TH>
										                	<TH align="center" width="5%">Roll(cuộn)</TH>
										                	<TH align="center" width="5%">khổ (width)</TH>
										                	<TH align="center" width="10%">cuộn(Roll)</TH>
										                	<TH align="center" width="10%">Trọng lượng(Weight)Kg (±10%)</TH>
									                	</TR>
					<!-- 				                	Start in to hop -->
									 					 <%
									 					 Set<Float> toHopmayKeys = toHopMay.keySet();
									 					 System.out.println("toHopmayKeys.toString: " +  toHopmayKeys.toString());
									 					 System.out.println("So loai kho giay nguyen lieu: " + toHopMay.size());
									 					 for (float khoGiayNguyenLieu : toHopmayKeys)
									 					 {
									 						 System.out.println("khoGiayNguyenLieu: " + khoGiayNguyenLieu);
									 						 KichBanN kichBan = toHopMay.get(khoGiayNguyenLieu);
									 						 List<ToHopN> toHopList = kichBan.getToHopList();
									 						 int stt = 0;
									 						 System.out.println("toHopHash size: " + toHopList.size());
									 						 for (ToHopN toHop : toHopList)
									 						 {
// 									 							 stt++;
									 							 if (toHop.getMaySanXuatId().trim().equals(maySanXuat.getId()) == true)
									 							 {
									 								stt++;
									 							 List<SanPham> sanPhamList = toHop.getSanPhamList();
									 							 int span = sanPhamList.size();
									 							 boolean isFirst = true;
									 							 
								 								 %>
					<!-- 		 							<tr> -->
					<!-- 		 								<td align="center"></td> -->
							 								
								 								 <%				 							 
									 							 for (SanPham sanPham : sanPhamList)
									 							 {
									 								 %>
									 								 <%if (isFirst == true){
								 									 %>
					  									 <tr>
							 								 <td align="center" rowspan="<%=span%>"><%=stt %></td>
							 								 <td align="center" rowspan="<%=span%>"><%=toHop.getId() %></td>
								 									 <%
									 								 }
									 								 %>
							 								 <td align="center"><%=sanPham.getTenNhanHang()%></td>
															 <td align="center"><%=sanPham.getMa()%></td>
							 								 <td align="center"><%=sanPham.getDinhLuong() %></td>
					<!-- 		 								 Xeo -->
							 								 <%if (isFirst == true){isFirst = false; System.out.println("toHop.getKhoGiayNguyenLieu(): " + toHop.getKhoGiayNguyenLieu() + "_ toHop.getHaoHut(): " + toHop.getHaoHut());%>
							 								 <td align="center" rowspan="<%=span%>"><%=toHop.getKhoGiayNguyenLieu() - toHop.getHaoHut() %></td>
							 								 <td align="center" rowspan="<%=span%>"><%=toHop.getSoLuong() %></td>
							 								 <%} %>
					<!-- 		 								 Sang cuon -->
							 								 <td align="center"><%=sanPham.getKhoGiay() %></td>
							 								 <td align="center"><%=sanPham.getTiLe() * toHop.getSoLuong() %></td>
							 								 <%double trongLuong = sanPham.getSoLuong() * (sanPham.getKhoGiay() * 7.5 + sanPham.getDinhLuong() - 115); %>
							 								 <td align="center"><%=trongLuong%></td>
							 								 <td align="center"><%=sanPham.getIdDonHang() %></td>
							 								 <td align="center"><%=sanPham.getTenKhachHang() %></td>
							 								 <td align="center"><%=sanPham.getTenMaySanXuat() %></td>
						 								 </tr>
									 								 <%
									 							 }
									 							 %>
					<!-- 		 							</tr> -->
								 								 <%
									 						 }
									 						 }
					 				 					 }
									 					 %>              	 
					<!-- 				                	End in to hop -->
													</TABLE>
													<%}} %>
												</div>
											<%} %>
												<div>
												</div>		
											</div>
										</TD>
									</TR>
								</TABLE>
							</div>
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
// 	if(kenhRs != null)
// 		kenhRs.close();

// 	if(nhanVienGiaoNhanRs != null)
// 		nhanVienGiaoNhanRs.close();

// 	if(donViKinhDoanhRs != null)
// 		donViKinhDoanhRs.close();

// 	if(kenhRs != null)
// 		kenhRs.close();

// 	if(nhaPhanPhoiRs != null) 
// 		nhaPhanPhoiRs.close();
	 if(obj != null)  
		 obj.DBClose(); 
	session.setAttribute("obj",null);
}%>