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
	int[] quyen = new  int[6];
	for (int i = 0; i < 6; i++)
	{
		quyen[i] = 1;
	}
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TheGioNuocHoa/index.jsp");
	}else{ %>
<%
	LenhSanXuatList obj = (LenhSanXuatList) session.getAttribute("obj");
	List<LenhSanXuat> lenhSanXuatList = obj.getLenhSanXuatList();
	List<MaySanXuat> maySanXuatList = obj.getMaySanXuatList();
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
// 		if (document.forms["frm"]["Sdays"].value.length == 0) {
// 			setErrors("Vui lòng chọn ngày bắt đầu");
// 			return false;
// 		}
// 		if (document.forms["frm"]["Edays"].value.length == 0) {
// 			setErrors("Vui lòng chọn ngày kết thúc");
// 			return false;
// 		}
	}

	function submitform() {
		validate();
		document.forms['frm'].action.value= 'search';
		document.forms["frm"].submit();
		reset();
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

	<form name="frm" method="post" action="../../LenhSanXuatSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left" class="tbnavigation">
					&nbsp;Quản lý bán hàng &nbsp; &gt; Lệnh sản xuất
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
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"><%=""%></textarea>
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
								<TR>
									<TD class="planame="id">Mã lệnh sản xuất</TD>
									<TD class="plainlabel"><input type="text" name="lenhSanXuatId" value='<%=obj.getLenhSanXuatId()%>' onChange = "submitform();"/></TD>
									<TD class="plainlabel">Mã tổ hợp</TD>
									<td><input type="text" name="toHopId" value='<%=obj.getToHopId()%>' onChange = "submitform();"/></td>
								</TR>
								<TR>
									<TD class="plainlabel">Mã kịch bản</TD>
									<td class="plainlabel"><input type="text" name="kichBanId" value='<%=obj.getKichBanId()%>' onChange = "search();"/></td>
                                    <TD class="plainlabel">Máy sản xuất </TD>
                                    <TD  class="plainlabel">
                                    <SELECT name ="maySanXuatId" onChange = "submitform();">
                                    	<option value="" selected>All</option>
                                        <%if (maySanXuatList != null){
                                         	for (MaySanXuat maySanXuat : maySanXuatList)
                                         	{
                                         		if (maySanXuat.getId().equals(obj.getMaySanXuatId()) == true){%>
                                         <option selected value="<%=maySanXuat.getId() %>" selected><%= maySanXuat.getMa() %></option>
                                        		<%}else{%>
                                         <option value="<%=maySanXuat.getId() %>"><%= maySanXuat.getMa() %></option>
                                        		<%}
                                         	}
										}%>
                                        </SELECT>
                                       </TD>
                                        
							 
								<TR>
									<td colspan="4">
										<div id="btnTaoBC">
											
										</div>
									</td>
								</TR>
							</TABLE>
							<div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
								<hr> 
								<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
									<TR>
										<TD colspan="6" >
				                  				<div>
													<TABLE class="tabledetail" width="100%" border="1" cellpadding="1" cellspacing="1" >
														<TR class="tbheader"> 
															<TH align="center" width="5%">STT</TH>
										                	<TH align="center" width="10%">Mã lệnh sản xuất</TH>
										                	<TH align="center" width="10%">Mã tổ hợp</TH>
										                	<TH align="center" width="10%">Số lượng tổ hợp</TH>
										                	<TH align="center" width="20%">Thời gian hoàn tất (dự kiến)</TH>
										                	<TH align="center" width="20%">Thời gian hoàn tất</TH>
										                	<TH align="center" width="10%">Trạng thái</TH>
										                	<TH align="center" width="5%">MSX</TH>
										                	<TH align="center" width="10%">Tác vụ</TH>
									                	</TR>		
					<!-- 				                	Start in to hop -->
									 					 <%
									 						int stt = 0;
									 						 for (LenhSanXuat lenhSanXuat : lenhSanXuatList)
									 						 {
								 								 stt++;
									 							 boolean isFirst = true;
								 								 %>
					  									 <tr>
							 								 <td align="center" ><%=stt %></td>
							 								 <td align="center"><%=lenhSanXuat.getId()%></td>
															 <td align="center"><%=lenhSanXuat.getToHop().getId()%></td>
							 								 <td align="center"><%=lenhSanXuat.getSoLuongToHop()%></td>
							 								 <td align="center"><%=lenhSanXuat.getThoiGianHoanTatDuKien()%></td>
							 								 <td align="center"><%=lenhSanXuat.getThoiGianHoanTat()%></td>
							 								 <%
							 								 String trangThai = "Hoàn tất";
							 								 if ( lenhSanXuat.getTrangThai().trim().equals("0"))
							 									 trangThai = "Chưa hoàn tất";
							 									 %>
							 								 <td align="center"><%=trangThai%></td>
							 								 <td align="center"><%=lenhSanXuat.getTenMaySanXuat()%></td>
							 								 <td align="center">
							 									 <TABLE border = 0 cellpadding="0" cellspacing="0">
																	<TR>
<%-- 																	<%if(quyen[3] != 0) {%> --%>
<!-- 																	<TD> -->
<%-- 																		<A href = "../../LenhSanXuatSvl?userId=<%=userId%>&view=<%=lenhSanXuat.getId()%>"><img src="../images/Display20.png" alt="Xoa" title="View" width="20" height="20" longdesc="View" border=0></A> --%>
<!-- 																	</TD> -->
<%-- 																	<%} %> --%>
																	<%if ( lenhSanXuat.getTrangThai().trim().equals("0"))
																	{%>
																	<%} %>
																	<TD>&nbsp;</TD>
																	<%if ( lenhSanXuat.getTrangThai().trim().equals("0"))
																	{%>
																	<TD>&nbsp;</TD>
																	<TD>
																	<%
																	if(quyen[4] != 0) {%>
																		<A onclick="if(!confirm('Bạn có muốn hoàn tất lệnh sản xuất này?')) return false;"
																		 href = "../../LenhSanXuatSvl?userId=<%=userId%>&chot=<%=lenhSanXuat.getId()%>&maySanXuatId=<%=obj.getMaySanXuatId() %>"><img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" longdesc="Chot" border=0></A>
																	<%} %>
																	</TD>
																	 <%}%>
																	</TR>												
																</TABLE>
							 								 </td>
						 								 </tr>
						 								 <%	
									 						 }
									 					 %>              	 
					<!-- 				                	End in to hop -->
													</TABLE>
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
	 if(obj != null)  
		 obj.DBClose(); 
	session.setAttribute("obj",null);
}%>