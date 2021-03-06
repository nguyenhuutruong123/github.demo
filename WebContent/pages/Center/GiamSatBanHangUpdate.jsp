<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.beans.giamsatbanhang.IGiamsatbanhang"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="geso.dms.center.util.*"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	int count = 0;
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/AHF/index.jsp");
	} else {
%>
<%
	IGiamsatbanhang gsbhBean = (IGiamsatbanhang) session.getAttribute("gsbhBean");
%>
<%
	//ResultSet ttpp = (ResultSet) gsbhBean.getTrungtamphanphoiList();
%>
<%
	ResultSet ncc = (ResultSet) gsbhBean.getNhacungcapList();
%>
<%
	ResultSet dvkd = (ResultSet) gsbhBean.getDvkdList();
%>
<%
	ResultSet kbh = (ResultSet) gsbhBean.getKenhbanhangList();
%>
<%
	ResultSet vung = (ResultSet) gsbhBean.createVungRS();
%>
<%
	ResultSet khuvuc = (ResultSet) gsbhBean.createKhuvucRS();
%>
<%
	ResultSet nppQlRs = (ResultSet) gsbhBean.getNppQuanLyRs();
		ResultSet nppRs = (ResultSet) gsbhBean.getNppRs();
		ResultSet gsbhTnRs = (ResultSet) gsbhBean.getGsbhTnRs();
		ResultSet diaban = (ResultSet) gsbhBean.getDiabanRs();
		ResultSet nppTnQl = (ResultSet) gsbhBean.getNppGsTnQuanLy();
%>
<%
	String nnId = (String) session.getAttribute("nnId");
%>
<%
	if (nnId == null) {
			nnId = "vi";
		}
		String url = util.getChuyenNguUrl("GiamsatbanhangSvl", "", nnId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs-panes.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
	});
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function() {
		$(".select2").select2({
			width : 'resolve'
		});
	});
</script>

<SCRIPT language="javascript" type="text/javascript">
	function upload() {// nhan nut cap nhat

		if (document.forms["gsbhForm"].filename.value == "") {

			document.forms["gsbhForm"].dataerror.value = "Ch??a t??m file upload l??n. Vui l??ng ch???n file c???n upload.";
		} else {
			document.forms["gsbhForm"].setAttribute('enctype',
					"multipart/form-data", 0);
			document.forms["gsbhForm"].submit();
		}

	}
	function submitform() {
		document.forms["gsbhForm"].submit();
	}

	function saveform() {
		document.forms['gsbhForm'].action.value = 'save';
		document.forms["gsbhForm"].submit();
	}

	function check_all(e, doituong) {
		var spIds = document.getElementsByName(doituong);
		for (var i = 0; i < spIds.length; i++) {
			spIds.item(i).checked = e.checked;
		}
	} 
	
	function check_each(doituong){
		var spIds = document.getElementsByName(doituong);
		var count = 0;
		for (var i = 0 ; i< spIds.length; i++){
			if (spIds.item(i).checked == true)
				count++;
		}
		$("#chkAll"+doituong).show();
		if (count == spIds.length) 
			$("#chkAll"+doituong).attr("checked", "checked");
		else $("#chkAll"+doituong).removeAttr("checked");
	}
</SCRIPT>



<!-- BEGIN RENDER AUTO -->

<%
	if (!geso.dms.center.util.GlobalValue.getDevmode()) {
%>
<script type="text/javascript" src="../scripts/disableF12.js"></script>
<%
	}
%>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<!-- BEGIN RENDER AUTO -->
	<%
		if (!geso.dms.center.util.GlobalValue.getDevmode()) {
	%>
	<noscript>
		<meta http-equiv="refresh"
			content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")%>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
	<%
		}
	%>
	<!-- END RENDER AUTO -->

	<form name="gsbhForm" method="post"
		action="../../GiamsatbanhangUpdateSvl">
		<%
			geso.dms.center.util.Csrf csdr = new geso.dms.center.util.Csrf(request, response, true, false, true);
		%>
		<input type="hidden" name="<%=csdr.get_tokenName()%>"
			value='<%=csdr.get_tokenValue()%>'> <input type="hidden"
			name="userId" value="<%=userId%>"> <input type="hidden"
			name="action" value='1'> <input type="hidden"
			name="cothechonTN" value='<%=gsbhBean.getCothechonTN()%>'>
		<input type="hidden" name="id" value='<%=gsbhBean.getId()%>'>
		<input type="hidden" name="ngonnguId" value='<%=nnId%>'>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;
											<%=url%> > <%=ChuyenNgu.get("C???p nh???t", nnId)%>
										<TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Ch??o m???ng ", nnId)%>
											<%=userTen%>&nbsp;</TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR class="tbdarkrow">
							<TD width="30" align="center"><A
								href="javascript:history.back()"><img
									src="../images/Back30.png" alt="Back" title="Back" width="30"
									height="30" border="1" longdesc="Back"
									style="border-style: outset"></A></TD>
							<TD width="2" align="left"></TD>
							<TD width="30" align="left"><A href="javascript: saveform()"><IMG
									src="../images/Save30.png" title="Luu lai" alt="Luu lai"
									border="1" style="border-style: outset"></A></TD>
							<TD align="left">&nbsp;</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle"><%=ChuyenNgu.get("B??o l???i nh???p li???u", nnId)%></LEGEND>

									<textarea name="dataerror"
										style="width: 100%; color: #F00; font-weight: bold"
										style="width: 100% ; color:#F00 ; font-weight:bold"
										readonly="readonly" rows="1"><%=ChuyenNgu.get(gsbhBean.getMessage(), nnId)%></textarea>
									<%
										gsbhBean.setMessage("");
									%>
								</FIELDSET>
							</TD>
						</tr>
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black"><%=ChuyenNgu.get("Th??ng tin", nnId)%></LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("H??nh", nnId)%></TD>
											<TD class="plainlabel">
												<div id="hinhdaidien">
													<img src="../Templates/images/<%=gsbhBean.getHinhanh()%>"
														style="max-width: 300px; max-height: 300px" />
												</div> <input type="hidden" id="fileName" name="fileName"
												value="<%=gsbhBean.getHinhanh()%>">
											</TD>
											<TD class="plainlabel"></TD>
											<TD class="plainlabel"></TD>
										</TR>
										<TR class="plainlabel">
											<TD class="plainlabel"><%=ChuyenNgu.get("Ch???n file", nnId)%></TD>
											<td><INPUT type="file" name="filename" size="40"
												value=''></td>

											<TD class="plainlabel">&nbsp;&nbsp;&nbsp;&nbsp; <a
												class="button2" href="javascript:upload()"> <img
													style="top: -4px;" src="../images/button.png" alt="">
													C???p nh???t
											</a>

											</TD>
											<TD class="plainlabel"></TD>
										</TR>
										<TR>
											<TD width="20%" class="plainlabel"><%=ChuyenNgu.get("T??n", nnId)%>
												<FONT class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><INPUT type="text" name="TenGSBH"
												style="width: 300px" value="<%=gsbhBean.getTen()%>"></TD>
											<TD class="plainlabel"><%=ChuyenNgu.get("Ch??? t??i kho???n", nnId)%></TD>
											<TD class="plainlabel"><INPUT type="text"
												name="chutaikhoan" value="<%=gsbhBean.getChuTaiKhoan()%>"></TD>
										</TR>
										<TR class="plainlabel">
											<TD width="20%" class="plainlabel"><%=ChuyenNgu.get("M??", nnId)%>
												<FONT class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><INPUT type="text" name="MaGSBH"
												style="width: 200px" value="<%=gsbhBean.getSmartId()%>"></TD>
											<TD class="plainlabel"><%=ChuyenNgu.get("S??? t??i kho???n", nnId)%></TD>
											<TD class="plainlabel"><INPUT type="text"
												name="sotaikhoan" value="<%=gsbhBean.getSoTaiKhoan()%>"></TD>
										</TR>
										<TR class="plainlabel">
											<TD class="plainlabel"><%=ChuyenNgu.get("Ng??y sinh", nnId)%><FONT
												class="erroralert"> </FONT></TD>
											<TD class="plainlabel"><input name="ngaysinh"
												id="ngaysinh" type="text"
												value="<%=gsbhBean.getNgaysinh()%>"></TD>
											<TD class="plainlabel"><%=ChuyenNgu.get("Ng??n h??ng", nnId)%></TD>
											<td class="plainlabel"><input type="text"
												name="NganHangId" value="<%=gsbhBean.getNganHangId()%>"></td>

										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("S??? CMND", nnId)%><FONT
												class="erroralert"></FONT></TD>
											<TD class="plainlabel"><input name="cmnd" maxlength="50"
												id="cmnd" type="text" value="<%=gsbhBean.getCmnd()%>"></TD>
											<TD class="plainlabel"><%=ChuyenNgu.get("Chi nh??nh", nnId)%></TD>
											<TD class="plainlabel"><INPUT TYPE="text"
												name="ChiNhanhId" value="<%=gsbhBean.getChiNhanhId()%>" /></TD>
										</TR>

										<TR>
											<TD width="15%" class="plainlabel"><%=ChuyenNgu.get("?????a ch???", nnId)%>
												<FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel"><INPUT name="DiaChi" id="DiaChi"
												type="text" value="<%=gsbhBean.getDiachi()%>"
												maxlength="300" style="width: 400px;"></TD>
												
												<TD class="plainlabel">Email</TD>
											<TD class="plainlabel"><INPUT type="text" name="Email"
												value="<%=gsbhBean.getEmail()%>"></TD>
												
												
												
											<%-- <TD class="plainlabel"><%=ChuyenNgu.get("M???t kh???u", nnId)%></TD>
																						<TD class="plainlabel"><INPUT type="password" name="matkhau"
												value="<%=gsbhBean.getMatKhau()%>">
											</TD>
											<TD class="plainlabel"><INPUT type="password"
												name="matkhau"
												value="<%=gsbhBean.getMatKhau().length() > 0 ? "matkhau" : ""%>">
											</TD> --%>

										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Qu?? qu??n", nnId)%><FONT
												class="erroralert"> </FONT></TD>
											<TD  class="plainlabel" colspan="3"><input name="quequan"
												id="quequan" type="text" value="<%=gsbhBean.getQuequan()%>"
												maxlength="300" style="width: 400Px"></TD>
												
												

										</TR>
										<TR>
											<%-- <TD class="plainlabel"><%=ChuyenNgu.get("T??n ????ng nh???p", nnId)%>
												<FONT class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><INPUT type="text"
												name="tendangnhap" value="<%=gsbhBean.getTenDangNhap()%>">
											</TD> --%>
										

										</TR>

										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("??i???n tho???i", nnId)%>
												<FONT class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><INPUT type="text"
												name="DienThoai" value="<%=gsbhBean.getSodienthoai()%>">
											</TD>


											<TD class="plainlabel"><%=ChuyenNgu.get("????n v??? kinh doanh", nnId)%>
												<FONT class="erroralert">*</FONT></TD>

											<TD class="plainlabel"><select name="dvkdId">
													<option value=""></option>
													<%
														try {
																while (dvkd.next()) {
																	if (dvkd.getString("dvkdId").equals(gsbhBean.getDvkdId())) {
													%>
													<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkdTen")%></option>
													<%
														} else {
													%>
													<option value='<%=dvkd.getString("dvkdId")%>'><%=dvkd.getString("dvkdTen")%></option>
													<%
														}
																}
															} catch (java.sql.SQLException e) {
																e.printStackTrace();
															}
													%>
											</select></TD>
										</TR>

										<%-- <TR>
											<TD class="plainlabel">Trung t??m ph??n ph???i<FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel">
												<select name="TTPP"> 
													<option value=""></option>
													<%
													if(ttpp!=null)
													{
														while(ttpp.next())
														{
															if(gsbhBean.getTtppId()!=null)
															{
																if(ttpp.getString("PK_SEQ").equals(gsbhBean.getTtppId()))
																{
																	%>
																	<option value='<%=ttpp.getString("PK_SEQ")%>' selected><%=ttpp.getString("TEN")%></option>
																	<%
																}
																else
																{
																	%>
																	<option value='<%=ttpp.getString("PK_SEQ")%>' ><%=ttpp.getString("TEN")%></option>
																	<%
																}
															}
															else
															{
															%>
															<option value='<%=ttpp.getString("PK_SEQ")%>' ><%=ttpp.getString("TEN")%></option>
														<%	}
														}
													}
														
													%>
												</select>
											</TD>
											<TD  class="plainlabel"></TD>
											<TD  class="plainlabel"></TD>
										</TR> --%>

										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Nh?? cung c???p", nnId)%><FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><select name="NCC"
												onChange="submitform();">
													<option value=""></option>
													<%
														try {
																while (ncc.next()) {
																	if (ncc.getString("nccId").equals(gsbhBean.getNccId())) {
													%>
													<option value='<%=ncc.getString("nccId")%>' selected><%=ncc.getString("nccTen")%></option>
													<%
														} else {
													%>
													<option value='<%=ncc.getString("nccId")%>'><%=ncc.getString("nccTen")%></option>
													<%
														}
																}
															} catch (java.sql.SQLException e) {
																e.printStackTrace();
															}
													%>
											</select></TD>

											<TD class="plainlabel"><%=ChuyenNgu.get("Gi??m s??t ti???n nhi???m", nnId)%>
											<TD class="plainlabel"><SELECT name="gsbhTnId"
												onchange="submitform()"
												<%=gsbhBean.getCothechonTN().equals("1") ? "" : " disabled='disabled' "%>>
													<option value=""></option>
													<%
														if (gsbhTnRs != null)
																while (gsbhTnRs.next()) {
																	if (gsbhTnRs.getString("pk_seq").equals(gsbhBean.getGsbhTnId())) {
													%>
													<option value="<%=gsbhTnRs.getString("pk_seq")%>" selected><%=gsbhTnRs.getString("ten")%></option>
													<%
														} else {
													%>
													<option value="<%=gsbhTnRs.getString("pk_seq")%>"><%=gsbhTnRs.getString("ten")%></option>
													<%
														}
																}
													%>

											</SELECT></TD>



										</TR>

										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("K??nh b??n h??ng", nnId)%><FONT
												class="erroralert"> *</FONT></TD>
												
											
											<TD class="plainlabel"><SELECT name="KenhBH"  onchange="submitform();" >

													<option value=""></option>
													<%
														try {
																while (kbh.next()) {
																	if (kbh.getString("kbhId").equals(gsbhBean.getKbhId())) {
													%>
													<option value='<%=kbh.getString("kbhId")%>' selected><%=kbh.getString("kbhTen")%></option>
													<%
														} else {
													%>
													<option value='<%=kbh.getString("kbhId")%>'><%=kbh.getString("kbhTen")%></option>
													<%
														}
																}
															} catch (java.sql.SQLException e) {
																e.printStackTrace();
															}
													%>

											</SELECT></TD>
											
											<TD class="plainlabel"><%=ChuyenNgu.get("ASM", nnId)%>
												<FONT class="erroralert">*</FONT></TD>

											<TD class="plainlabel">
												<select name="asmId">
													<option value=""></option>
													<%
														try {
																ResultSet asmRs = gsbhBean.getAsmRs();
																if(asmRs != null)
																while (asmRs.next()) {
																	if (asmRs.getString("pk_seq").equals(gsbhBean.getAsmId())) {
													%>
													<option value='<%=asmRs.getString("pk_seq")%>' selected><%=asmRs.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=asmRs.getString("pk_seq")%>'><%=asmRs.getString("ten")%></option>
													<%
														}
																}
															} catch (java.sql.SQLException e) {
																e.printStackTrace();
															}
													%>
													</select>
											</TD>
											
											



										</TR>
										

										<TR>
											<TD class="plainlabel" style="display: none"><%=ChuyenNgu.get("?????a b??n", nnId)%>
												<FONT class="erroralert"> *</FONT></TD>
											<TD class="plainlabel" colspan="3" style="display: none"><SELECT
												name="diaban_fk" class="select2" onchange="submitform()">

													<option value=""></option>
													<%
														try {
																while (diaban.next()) {
																	if (diaban.getString("pk_seq").equals(gsbhBean.getDiaban_fk())) {
													%>
													<option value='<%=diaban.getString("pk_seq")%>' selected><%=diaban.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=diaban.getString("pk_seq")%>'><%=diaban.getString("ten")%></option>
													<%
														}
																}
															} catch (java.sql.SQLException e) {
																e.printStackTrace();
															}
													%>

											</SELECT></TD>

										</TR>

										<tr>




											<TD class="plainlabel"><%=ChuyenNgu.get("Khu v???c", nnId)%><FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><a href="" id="khuvuc"
												rel="subcontentSp"> &nbsp; <img
													alt="<%=ChuyenNgu.get("Khu v???c", nnId)%>"
													src="../images/vitriluu.png" onclick="check_each('khuvuc')"></a>

												<DIV id="subcontentSp"
													style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 250px; max-height: 300px; overflow-y: scroll; padding: 4px;">
													<table width="90%" align="center">
														<tr>
															<th width="200px"><%=ChuyenNgu.get("T??n", nnId)%></th>
															<th width="80px" align="center"><%=ChuyenNgu.get("Ch???n h???t", nnId)%><input
																type="checkbox"
																onchange="check_all(this, 'khuvuc');" id="chkAllkhuvuc"></th>
														</tr>
														<%
															if (khuvuc != null) {
																int i = 0;
																	while (khuvuc.next()) {
														%>

														<tr>
															<td><input style="width: 100%"
																value="<%=khuvuc.getString("kvTen")%>"></td>
															<td align="center">
																<%
																	if (gsbhBean.getKvId().indexOf(khuvuc.getString("kvId")) >= 0) {
																%>
																<input type="checkbox" name="khuvuc" id="khuvucId_0<%=i%>" checked="checked" onchange="check_each('khuvuc');"
																value="<%=khuvuc.getString("kvId")%>"> <%
																	 	} else {
																	 %>
																<input type="checkbox" name="khuvuc" id="khuvucId_0<%=i%>" onchange="check_each('khuvuc');"
																value="<%=khuvuc.getString("kvId")%>"> <%
																	 	}
																	 %>
															</td>
														</tr>

														<%
															i++;
															}
																	khuvuc.close();
																}
														%>
													</table>
													<div align="right">
														<label style="color: red"></label>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
															onclick="submitform()"
															href="javascript:dropdowncontent.hidediv('subcontentSp')">Ho??n
															t???t</a>
													</div>
												</DIV></TD>
												<TD class="plainlabel"><%=ChuyenNgu.get("Ho???t ?????ng", nnId)%></TD>
												<TD class="plainlabel"><input name="trangthai"
												type="checkbox" value="1"
												<%=gsbhBean.getTrangthai().equals("1") ? "checked" : ""%>></TD>
												

										</tr>

										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("V??ng", nnId)%> <FONT class="erroralert">
													*</FONT>
											</TD>
											<TD class="plainlabel"><a href="" id="vung"
												rel="subcontentCl"> &nbsp; <img
													alt="<%=ChuyenNgu.get("V??ng", nnId)%>" src="../images/vitriluu.png" onclick="check_each('vung')"></a>
												<DIV id="subcontentCl"
													style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 590px; max-height: 300px; overflow-y: scroll; padding: 4px;">
													<table width="90%" align="center">
														<tr>
															<th width="350px"><%=ChuyenNgu.get("V??ng", nnId)%></th>
															<th width="100px" align="center"><%=ChuyenNgu.get("Ch???n h???t", nnId)%> <input
																type="checkbox" id="chkAllvung" onchange="check_all(this, 'vung');" ></th>
														</tr>
														<%
															if (vung != null) {
																int i = 0;
																	while (vung.next()) {
														%>

														<tr>
															<td><input style="width: 100%"
																value="<%=vung.getString("vung")%>"></td>
															<td align="center">
																<%
																	if (gsbhBean.getVungId().indexOf(
																						vung.getString("vungId")) >= 0) {
																%> <input type="checkbox" name="vung"
																checked="checked" onchange="check_each('vung');" value="<%=vung.getString("vungId")%>">
																<%
																	} else {
																%> <input type="checkbox" name="vung" onchange="check_each('vung');"
																value="<%=vung.getString("vungId")%>"> <%
 	}
 %>
															</td>
														</tr>

														<%
															i++;
																	}
																	vung.close();
																}
														%>
													</table>
													<div align="right">
														<label style="color: red"></label>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
															href="javascript:dropdowncontent.hidediv('subcontentCl')"
															onclick="submitform()"><%=ChuyenNgu.get("Ho??n t???t", nnId)%></a>
													</div>
												</DIV></TD>
											<TD class="plainlabel"></TD>
											<TD class="plainlabel"></TD>
										</TR>


									</TABLE>
								</FIELDSET> <%
											 	if (!gsbhBean.getDvkdId().equals("100069")) {
											 %>
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black"><%=ChuyenNgu.get("Nh?? ph??n ph???i gi??m s??t ti???n nhi???m qu???n l??", nnId)%></LEGEND>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="6">
										<tbody id="tb_npp">
											<TR class="tbheader">
												<TH align="center" width="10%"><%=ChuyenNgu.get("M??", nnId)%></TH>
												<TH align="center" width="50%"><%=ChuyenNgu.get("T??n", nnId)%></TH>
												<TH align="center" width="20%"><%=ChuyenNgu.get("Ph???n tr??m ch??? ti??u", nnId)%></TH>
											</TR>

											<%
												int k = 0;

														if (nppTnQl != null) {
															try {
																while (nppTnQl.next()) {
																	if (k % 2 == 0) {
											%>

											<TR class='tbdarkrow'>
												<%
													} else {
												%>
											
											<TR class='tblightrow'>
												<%
													}
												%>
												<TD align="center"><%=nppTnQl.getString("nppMa")%></TD>
												<TD align="left"><%=nppTnQl.getString("nppTen")%></TD>
												<TD align="center"><input type="hidden"
													name="nppGsTnQl" value='<%=nppTnQl.getString("nppId")%>'>
													<input type="text" name="phantramchitieu"
													value='<%=nppTnQl.getFloat("PhanTram")%>'></TD>

											</TR>
											<%
												k++;
																} //while
																nppTnQl.close();
															} catch (Exception ex) {
																ex.printStackTrace();
															}
														}
											%>
											<TR>
												<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
											</TR>
										</tbody>
									</TABLE>
								</FIELDSET> <%
											 	}
											 %> <%
											 	if (!gsbhBean.getDvkdId().equals("100069")) {
											 %>
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Nh??
										ph??n ph???i qu???n l??</LEGEND>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="6">
										<tbody id="tb_npp">
											<TR class="tbheader">
												<TH align="center" width="10%">M?? NPP</TH>
												<TH align="center" width="50%">T??n NPP</TH>
												<TH align="center" width="20%">Ng??y b???t ?????u</TH>
												<TH align="center" width="20%">Ng??y k???t th??c</TH>
											</TR>

											<%
												int k = 0;
														int i = 0;

														if (nppRs != null) {
															try {
																while (nppRs.next()) {
																	if (k % 2 == 0) {
											%>

											<TR class='tbdarkrow'>
												<%
													} else {
												%>
											
											<TR class='tblightrow'>
												<%
													}
												%>
												<TD align="center"><input
													 name="nppMa" value='<%=nppRs.getString("nppMa")%>'> <input
													type="hidden" name="nppId"
													value='<%=nppRs.getString("nppId")%>'></TD>

												<TD align="left"><input name="nppTen" size="60"
													value='<%=nppRs.getString("nppTen")%>'></TD>
												<%-- <TD align="center">
															<input  value='<%=nppQlRs.getString("ngaybatdau")%>'>
														</TD>
														<TD align="center">
															<input  value='<%=nppQlRs.getString("ngayketthuc")%>'>
														</TD> --%>

												<%
													if (gsbhBean.getNgayketthuc().trim().length() == 0 && gsbhBean.getQuerycu().length()==0) {
												%>
												<TD align="center"><input class="days" type="text"
													name="ngaybatdau<%=i%>"
													value='<%=gsbhBean.getNgaybatdau()%>'></TD>
												<TD align="center"><input class="days" type="text"
													name="ngayketthuc<%=i%>"
													value='<%=gsbhBean.getNgayketthuc()%>'></TD>
												<%
													} else {
												%>

												<TD align="center"><input class="days" type="text"
													name="ngaybatdau<%=i%>"
													value='<%=nppRs.getString("ngaybatdau")%>'></TD>
												<TD align="center"><input class="days" type="text"
													name="ngayketthuc<%=i%>"
													value='<%=nppRs.getString("ngayketthuc")%>'></TD>
												<%
													}
												%>
											</TR>
											<%
												k++;
																	i++;
																} //while
																nppRs.close();
															} catch (Exception ex) {
																ex.printStackTrace();
															}
														}
											%>
											<TR>
												<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
											</TR>
										</tbody>
									</TABLE>
								</FIELDSET> <%
 	}
 %>

								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD colspan="4">
											<FIELDSET>
												<LEGEND class="legendtitle" style="color: black"><%=ChuyenNgu.get("L???ch s???", nnId)%></LEGEND>

												<TABLE border="0" width="100%" cellpadding="3"
													cellspacing="1">
													<TR class="tbheader">
														<TH width="13%"><%=ChuyenNgu.get("M?? NPP", nnId)%></TH>
														<TH width="13%"><%=ChuyenNgu.get("T??n NPP", nnId)%></TH>

														<TH width="13%"><%=ChuyenNgu.get("Tr???ng th??i", nnId)%></TH>
														<TH width="13%"><%=ChuyenNgu.get("Th???i ??i???m", nnId)%></TH>
													</TR>

													<%
														int i = 0;
															String lightrow = "tblightrow";
															String darkrow = "tbdarkrow";
															try {
																if (gsbhBean.getLogRs() != null)
																	while (gsbhBean.getLogRs().next()) {

																		String trangthai = "";
																		if (gsbhBean.getLogRs().getInt("trangthai") == 1)
																			trangthai = ChuyenNgu.get("Ho???t ?????ng", nnId);
																		else if (gsbhBean.getLogRs().getInt("trangthai") == 0)
																			trangthai = ChuyenNgu.get("Ng??ng ho???t ?????ng", nnId);
																		if (i % 2 != 0) {
													%>
													<TR class=<%=lightrow%>>
														<%
															} else {
														%>
													
													<TR class=<%=darkrow%>>
														<%
															}
														%>
														<TD align="center">
															<div align="left"><%=gsbhBean.getLogRs().getString("MANPP")%></div>
														</TD>
														<TD align="center">
															<div align="left"><%=gsbhBean.getLogRs().getString("NPP")%></div>
														</TD>

														<TD align="center">
															<div align="left"><%=trangthai%></div>
														</TD>
														<TD align="center">
															<div align="left"><%=gsbhBean.getLogRs().getString("ThoiDiem")%></div>
														</TD>
													</TR>

													<%
														i++;
																	}
																else {
																	System.out.println("GSBH la null");
																}
															} catch (java.sql.SQLException e) {
															}
													%>
												</TABLE>
											</FIELDSET>
										</TD>
									</TR>
								</TABLE>


							</TD>
						</TR>
					</TABLE>

				</TD>

			</TR>
		</TABLE>
	</form>
	<script type="text/javascript">
		dropdowncontent.init('vung', "right-top", 300, "click");
		dropdowncontent.init('khuvuc', "left-top", 300, "click");
		dropdowncontent.init('nppQl', "right-top", 300, "click");
		dropdowncontent.init('nppGsTnQl', "left-top", 300, "click");
		dropdowncontent.init('lsQlNpp', "right-top", 300, "click");
	</script>
</body>
<script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
	}
%>