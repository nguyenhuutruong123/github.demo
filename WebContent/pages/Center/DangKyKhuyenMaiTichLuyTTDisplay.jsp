<%@page import="java.sql.SQLException"%>
<%@page
	import="geso.dms.center.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyTT"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.dms.distributor.beans.dangkytrungbay.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SOHACO/index.jsp");
	} else {
%>
<%
	IDangkykhuyenmaitichluyTT obj = (IDangkykhuyenmaitichluyTT)session.getAttribute("obj");
%>
<%
	ResultSet ctkmIds = (ResultSet)obj.getCtkmRs();
%>
<%
	ResultSet khRs = (ResultSet)obj.getKhList();
%>
<%
	ResultSet NppRs = (ResultSet)obj.getNppRs();
%>
<%
	ResultSet nvbhRs = (ResultSet)obj.getNvBanhang();
%>
<%
	ResultSet vungRs = (ResultSet)obj.getVungRs();
%>
<%
	ResultSet khuvucRs = (ResultSet)obj.getKhuvucRs();
%>
<%
	Hashtable<String, Integer> kh_muc = obj.getMucDangky();
%>
<%
	Hashtable<String, Integer> kh_stt = obj.getSTT();
%>
<% String nnId = (String)session.getAttribute("nnId"); %>
<% if(nnId == null) {
 nnId = "vi";  
 }
 String url = util.getChuyenNguUrl("DangkykhuyenmaitichluyTTSvl","" ,nnId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>SOHACO - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript"
	src="../scripts/Timepicker/jquery-ui.min.js"></script>

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
        });	
		
    </script>	
	
<script type="text/javascript" src="..scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>
<script>
	$(document).ready(function() {
		$("#accordion").accordion({
			autoHeight : false
		}); //autoHeight content set false
		$("#accordion").accordion("option", "icons", {
			'header' : 'ui-icon-plus',
			'headerSelected' : 'ui-icon-minus'
		});
		$("#accordion").accordion({
			active : 1
		});

		var ac = document.getElementById("action").value;
		if (ac == "upload") {
			document.forms['dktbForm'].submit();
		}
	});
</script>
<script type='text/javascript' src='../scripts/loadingManual.js'></script>
<script type="text/javascript">

	
	function xuatexcel()
	{
		  
		  document.forms["dktbForm"].action.value = "excel";
		  document.forms["dktbForm"].submit(); 
	}

</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dktbForm" method="post"
		action="../../DangkykhuyenmaitichluyTTUpdateSvl">
		<INPUT name="userId" type="hidden" value='<%=userId%>' size="30">
		<input type="hidden" name="id" value='<%=obj.getId()%>'> <input
			type="hidden" name="action" id="action" value="<%=obj.getAction()%>">

		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 40%; padding: 5px; float: left"
					class="tbnavigation">
					<%
						if (obj.getId().length() == 0) {
					%>
					<%=url %>
					<%
						} else {
					%>
					<%=url %>
					<%
						}
					%>
				</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng",nnId) %>
					<%=userTen%>
					&nbsp;
				</div>
			</div>

			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="../../DangkykhuyenmaitichluyTTSvl?userId=<%=userId%>">
					<img src="../images/Back30.png" alt="Quay ve" title="Quay ve"
					border="1px" longdesc="Quay ve" style="border-style: outset">
				</A>

			</div>
			<div align="left" style="width: 100%; float: none">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %> </legend>
					<textarea name="dataerror"
						style="width: 100%; color: #F00; font-weight: bold" cols="71"
						rows="1" style="width: 100% " readonly="readonly"><%=obj.getMessage()%></textarea>

				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none">
				<fieldset>
					<legend class="legendtitle"> <%=ChuyenNgu.get("Đăng ký khuyến mãi tích lũy",nnId) %> </legend>
					<div style="width: 100%; float: non; clear: left; font-size: 0.7em">
						<TABLE width="100%" cellpadding="5px" cellspacing="0px">
							<TR>
								<TD class="plainlabel" width="130px"><%=ChuyenNgu.get("Chương trình",nnId) %></TD>
								<TD class="plainlabel">
									<%
										if (obj.getId().length() == 0) {
									%> <select name="ctkmId"
									id="ctkmId" onchange="submitform()">
										<option value=''></option>
										<%
											if (ctkmIds != null) {
														try {
															while (ctkmIds.next()) {
																if (ctkmIds.getString("pk_seq").equals(
																		obj.getctkmId())) {
										%>
										<option value='<%=ctkmIds.getString("pk_seq")%>' selected><%=ctkmIds.getString("scheme")%></option>
										<%
											} else {
										%>
										<option value='<%=ctkmIds.getString("pk_seq")%>'><%=ctkmIds.getString("scheme")%></option>
										<%
											}
															}
															ctkmIds.close();
														} catch (java.sql.SQLException e) {
														}
													}
										%>
								</select> <%
 	} else {
 			try {
 				while (ctkmIds.next()) {
 					if (ctkmIds.getString("pk_seq").equals(
 							obj.getctkmId())) {
 %> <input
									type="hidden" name="ctkmId"
									value='<%=ctkmIds.getString("pk_seq")%>'> <input
									type="text" value="<%=ctkmIds.getString("scheme")%>">
									<%
										}
									%> <%
 	}
 			} catch (Exception ex) {
 			}
 		}
 %>
								</TD>
							</TR>

							<TR>
								<TD class="plainlabel"><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
								<TD class="plainlabel"><input type="text"
									value="<%=obj.getTungay()%>" readonly="readonly" />
									&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp; <%=ChuyenNgu.get("Đến ngày",nnId) %> &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="text" value="<%=obj.getDenngay()%>" readonly="readonly" />
								</TD>
							</TR>

							<TR>
				                <TD class="plainlabel">Diễn giải</TD>
				                <TD class="plainlabel" >
				                    <input type="text" value="<%= obj.getDiengiai() %>"  name = "diengiai"  />
				                   &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; Ngày đăng ký &nbsp;&nbsp;&nbsp;&nbsp; 
				                    <input  name = "ngaydangky" class="days" type="text" value="<%= obj.getNgaydangky() %>" readonly="readonly" />
				                </TD>
				            </TR> 
							
							
						</TABLE>

						
								<hr />
								
								<%
										if (obj.getId().length() > 0) {
									%> 
									<div id="accordion"
							style="width: 100%; height: auto; float: none; font-size: 1.0em"
							align="left">


							<h1 style="font-size: 1.8em">
								<a href="#"><%=ChuyenNgu.get("Khách hàng",nnId) %></a>
							</h1>
							<div style="height: auto">
								<!-- <a class="button" href="javascript:submitform();">
					<img style="top: -4px;" src="../images/button.png" alt=""> Hiển thị Khách hàng</a> -->
									
									
								<table style="width: 100%" cellpadding="0" cellspacing="1">
									<Tr class="tbheader">
										<td align="center" width="5%"><%=ChuyenNgu.get("STT",nnId) %></td>
										<td align="center" width="7%"><%=ChuyenNgu.get("MÃ KH",nnId) %></td>
										<td align="center"  width="20%"><%=ChuyenNgu.get("Tên khách hàng",nnId) %></td>
										<td align="center"  width="20%"><%=ChuyenNgu.get("Chi nhánh/ Nhà phân phối",nnId) %></td>
										<td width="20%" align="center"><%=ChuyenNgu.get("Người tạo",nnId) %></td>
										<%-- <td width="10%" align="center"><%=ChuyenNgu.get("Từ PDA",nnId) %></td> --%>
										<td width="10%" align="center"><%=ChuyenNgu.get("Mức đăng ký",nnId) %></td>
										
										
									</Tr>
									<%
										ResultSet khDK = obj.getKhDangkyRs();
										if (khDK != null) {
												int stt = 1;
												while (khDK.next()) {
									%>

									<TR class="tblightrow">
										<td><input type="text"
											name="stt_<%=stt %>" value="<%=stt%>"
											style="width: 100%; text-align: center;" readonly="readonly">
										</td>
										<td><input type="text"
											value="<%=khDK.getString("MÃ KH(Dữ liệu upload)")%>" style="width: 100%"
											readonly="readonly"></td>
										<td><input type="text"
											value="<%=khDK.getString("TÊN")%>" style="width: 100%"
											readonly="readonly"></td>

										<td><input type="text"
											value="<%=khDK.getString("NPP")%>" style="width: 100%"
											readonly="readonly"></td>
										<td><input type="text"
											value="<%=khDK.getString("Người tạo")%>" style="width: 100%"
											readonly="readonly"></td>
											
										<%-- <td  ><input type="text" 
											value="<%=khDK.getString("Từ PDA").equals("1") ? "x" : ""%>" style="width: 100%; text-align: center;"
											readonly="readonly"></td> --%>
												
										<td align="center"><input type="text"
											name="mdk_<%=stt%>"
											value="<%= khDK.getString("Mức(Dữ liệu upload)") %>"
											style="width: 100%; text-align: center;"></td>
										
									</TR>
									<%
										stt++;
												}
												khDK.close();
											}
									%>
								</table>
								</div>
						</div>
								<%} %>
							
					</div>
				</fieldset>
			</div>
		</div>


	</form>
</body>
<!-- <script type='text/javascript' src='../scripts/loadingv2.js'></script> -->
</html>
<%
	if (obj != null) {
			obj.DBclose();
			obj = null;
		}
		try {
			if (ctkmIds != null)
				ctkmIds.close();
		} catch (SQLException e) {
		}
%>
<%
	}
%>

