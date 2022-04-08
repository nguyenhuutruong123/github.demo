<%@page import="geso.dms.distributor.db.sql.dbutils"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="geso.dms.distributor.beans.chuyentuyen.IChuyenTuyen"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="geso.dms.distributor.util.*"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = new Utility();
	util.getUserInfo(userId, new dbutils());
	geso.dms.center.util.Utility t = new geso.dms.center.util.Utility();
	if (!t.check(userId, userTen, sum)) {
		response.sendRedirect("/OPV/index.jsp");
	} else {
%>

<%IChuyenTuyen bean = (IChuyenTuyen) session.getAttribute("ctuyenBean");%>
<%ResultSet ddkdFrom = (ResultSet) bean.getDkdFromRs();%>
<%ResultSet ddkdTo = (ResultSet) bean.getDdkdToRs();%>
<%ResultSet tuyenFrom = (ResultSet) bean.getTuyenFromRs();%>
<%ResultSet tuyenTo = (ResultSet) bean.getTuyenToRs();%>
<%ResultSet kh_tbh_dpt = (ResultSet) bean.getKh_Tbh_DptList();%>
<%ResultSet nppRs = (ResultSet) bean.getNppRs();%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>OPV - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<style type="text/css">
.plainlabelNew {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 10pt;
	color: #000000;
	line-height: 15pt;
}

.plainlabelNew2 {
	display: none;
}
</style>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$(".select2").select2();
        }); 		
		
</script>
<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout()
 {
    if(confirm("Bạn có muốn đăng xuất?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function submitform()
 { 
	var tbh_khdpt = document.getElementsByName("kh_tbh_dptList");
	var tbh_khcdpt = document.getElementsByName("kh_tbh_cdptList");
	var tsList = document.getElementsByName("tansoList");
	var i;
	for(i=0; i < tbh_khdpt.length; i++)
	{
		if(tbh_khdpt.item(i).checked == false)
			tbh_khdpt.item(i).checked = true; //de tao lai 1 Resual Set luu danh sach dang duoc chon
	}
	for(i=0; i < tbh_khcdpt.length; i++)
	{
		if(tbh_khcdpt.item(i).checked == false)
			tbh_khcdpt.item(i).checked = true;
	}
	
	document.forms["tbhForm"].submit();	
 }

	 function chuyentuyen()
	 {
		var ddkdTen = document.getElementById("ddkdTen");
		var tbhTen = document.getElementById("tbhTen");
		var nlv = document.getElementById("nlv");
		if(ddkdTen.value == "")
		{
			alert("Vui lòng chọn nhân viên bán hàng của tuyến bán hàng này...");
			return;
		}
		if(tbhTen.value == "")
		{
			alert("Vui lòng nhập diễn giải cho tuyến bán hàng này ...");
			return;
		}
		if(nlv.value == "")
		{
			alert("Vui lòng chọn ngày làm việc của tuyến...");
			return;
		}
		
	 	document.forms['tbhForm'].action.value='sangtrai';
	    document.forms['tbhForm'].submit();
	 
	 
 }
 function saveform()
 {
	var tbh_khdpt = document.getElementsByName("kh_tbh_dptList");
	var tbh_khcdpt = document.getElementsByName("kh_tbh_cdptList");
	var tsList = document.getElementsByName("tansoList");
	var ddkdTen = document.getElementById("ddkdTen");
	var tbhTen = document.getElementById("tbhTen");
	var nlv = document.getElementById("nlv");
	var tuyenFromId=document.getElementById("tuyenFromId").value;
	var tuyenToId=document.getElementById("tuyenToId").value;
	var i;
	
	$.ajax({
		url: "../../ChuyenTuyenSvl",
		type: "GET",
		data: "action=check&tuyenFromId="+tuyenFromId+"&tuyenToId="+tuyenToId+"",
		success: function(msg) 
		{
			if(msg === "true") 
			{
				if(!confirm('Có khách hàng thuộc 2 tuyến bán hàng?Bạn có muốn chuyển hay không'))
				  	return;
			}
		}
	});
	for(i=0; i < tbh_khdpt.length; i++)
	{
		if(tsList.item(i).value == "")
		{
			alert("Vui lòng thiết lập tần số ghé thăm cho các khách hàng được phân tuyến");
			return;
		}
		if(tbh_khdpt.item(i).checked == false)
			tbh_khdpt.item(i).checked = true;
	}
	for(i=0; i < tbh_khcdpt.length; i++)
	{
		if(tbh_khcdpt.item(i).checked == false)
			tbh_khcdpt.item(i).checked = true;
	}
	document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
 	document.forms['tbhForm'].action.value='save';
    document.forms['tbhForm'].submit();
 }
 
 function checkedAll(chk,chontatca) 
 {
	for(var i=0; i<chk.length; i++){
	 if(chontatca.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
 } 
 
 
 
 </SCRIPT>
<script language="javascript" type="text/javascript">
	function addRow(name, stt, makh,smartid, tenkh, diachi)
	{  
		tableName = document.getElementById(name);
		
		var tr = document.createElement("TR");
		var sttAdd = document.createElement("TD");
		var makhAdd = document.createElement("TD");
		var smartidAdd = document.createElement("TD");
		var tenkhAdd = document.createElement("TD");
		var diachiAdd = document.createElement("TD");
		var tansoAdd = document.createElement("TD");
		var checkAdd = document.createElement("TD");
		
		var txt = document.createElement("input");
		txt.setAttribute("type", "textbox");
		txt.setAttribute("name", "thutu");
		//txt.setAttribute("value", stt);
		txt.value = stt;
		txt.size = 5;//txt.size = 5;
		
		//sttAdd.innerHTML = stt;
		sttAdd.appendChild(txt);
		
		makhAdd.setAttribute("class","plainlabelNew2");
		makhAdd.innerHTML = makh;
		
		smartidAdd.setAttribute("class","plainlabelNew");
		smartidAdd.innerHTML = smartid;
		//alert(smartid);
		
		tenkhAdd.setAttribute("class","plainlabelNew");
		tenkhAdd.innerHTML = tenkh;
		//alert(tenkh);
		
		diachiAdd.setAttribute("class","plainlabelNew");
		diachiAdd.innerHTML = diachi;
		//alert('diachi la: ' + diachi);
		
		
		var selectBox = document.createElement("select");
		selectBox.name = "tansoList";
		selectBox.setAttribute("style", "width:130px");
		var option = document.createElement('option');
		option.value = 'F2';
		option.appendChild(document.createTextNode('F2'));
		selectBox.appendChild(option);

		option = document.createElement('option');
		option.value = 'F4';
		option.appendChild(document.createTextNode('F4'));
		selectBox.appendChild(option);
		
		option = document.createElement('option');
		option.value = 'F8';
		option.appendChild(document.createTextNode('F8'));
		selectBox.appendChild(option);
	
		tansoAdd.appendChild(selectBox);
		
		var checkbox = document.createElement("input");
		checkbox.setAttribute("type", "checkbox");
		checkbox.name = "kh_tbh_dptList";
		checkbox.value = makh + ";" + smartid + ";" + tenkh + ";" + diachi;
		
		checkAdd.align = "center";
		checkAdd.appendChild(checkbox);
		
		tr.appendChild(sttAdd);
		tr.appendChild(makhAdd);
		tr.appendChild(smartidAdd);
		tr.appendChild(tenkhAdd);
		tr.appendChild(diachiAdd);
		tr.appendChild(tansoAdd);
		tr.appendChild(checkAdd);
		
		if(stt % 2 != 0)
			tr.setAttribute("class","tblightrow");
		else
			tr.setAttribute("class","tbdarkrow");
		tr.setAttribute("id", stt );
		tableName.appendChild(tr);
	}
	
	function addRow2(name, stt, makh,smartid, tenkh, diachi) //them vao khach hang chua dc phan tuyen
	{
		tableName = document.getElementById(name);
		
		var tr = document.createElement("TR");
		var makhAdd = document.createElement("TD");
		var smartidAdd = document.createElement("TD");
		var tenkhAdd = document.createElement("TD");
		var diachiAdd = document.createElement("TD");
		var checkAdd = document.createElement("TD");
		
		makhAdd.setAttribute("class","plainlabelNew2");
		makhAdd.innerHTML = makh;	
		
		smartidAdd.setAttribute("class","plainlabelNew");
		smartidAdd.innerHTML = smartid;	
		
		tenkhAdd.setAttribute("class","plainlabelNew");
		tenkhAdd.innerHTML = tenkh;	
		
		diachiAdd.setAttribute("class","plainlabelNew");
		diachiAdd.innerHTML = diachi;
		
		var checkbox = document.createElement("input");
		checkbox.setAttribute("type", "checkbox");
		checkbox.name = "kh_tbh_cdptList";
		checkbox.value = makh + ";" + smartid + ";" + tenkh + ";" + diachi;
		
		checkAdd.align = "center";
		checkAdd.appendChild(checkbox);
		
		tr.appendChild(makhAdd);
		tr.appendChild(smartidAdd);
		tr.appendChild(tenkhAdd);
		tr.appendChild(diachiAdd);
		tr.appendChild(checkAdd);
		
		if(stt % 2 != 0)
			tr.setAttribute("class","tblightrow");
		else
			tr.setAttribute("class","tbdarkrow");
		tr.setAttribute("id", stt);
		tableName.appendChild(tr);
	}
	
	function removeRow(name, id)
	{
		var tableName = document.getElementById(name);
		tableName.removeChild(document.getElementById(id));
	}
	
	function ChuyenSangTrai(i)
	{	
		var tbh_khdpt = document.getElementsByName("kh_tbh_dptList");
		var tbh_khcdpt = document.getElementsByName("kh_tbh_cdptList");

				str = tbh_khdpt.item(i).value;
				var makh = str.substring(0, str.indexOf(";"));
				
				str = str.substr(str.indexOf(";") + 1);
				var smartid = str.substring(0,str.indexOf(";"));
				
				str = str.substr(str.indexOf(";") + 1);
				var tenkh = str.substring(0, str.indexOf(";"));
				
				str = str.substr(str.indexOf(";") + 1);
				var diachi = str.substring(0, str.length);		
				addRow2('kh_tbh_cdpt', tbh_khcdpt.length, makh, smartid, tenkh, diachi);
				
				

	}
	
	function AutoChuyenTrai()
	{
		var tbh_khdpt = document.getElementsByName("kh_tbh_dptList");
		var tbh_khcdpt = document.getElementsByName("kh_tbh_cdptList");
		
		var i;
		for(i=0; i < tbh_khdpt.length; i++)
		{
			
			if(tbh_khdpt.item(i).checked)
			{
				
				ChuyenSangTrai(i);
			}
		}
		i=tbh_khdpt.length;
		while(i>0){
				i=i-1;
				if(tbh_khdpt.item(i).checked)
				{
					
					document.getElementById("tb_kh_tbh_dpt").deleteRow(tbh_khdpt.item(i).parentNode.parentNode.rowIndex);

				}
			
		}
		
		
		 var tbh_thutu = document.getElementsByName("thutu");
		
		for(i=0; i < tbh_thutu.length; i++)
		{
		
			tbh_thutu.item(i).value=i;
		
		} 
	}
	
	function ChuyenSangPhai(i)
	{	
		var tbh_khcdpt = document.getElementsByName("kh_tbh_cdptList");
		var tbh_khdpt = document.getElementsByName("kh_tbh_dptList");
			    var str = new String;
			
				str = tbh_khcdpt.item(i).value;
				var makh = str.substring(0, str.indexOf(";"));
				
				str = str.substr(str.indexOf(";") + 1);
				var smartid = str.substring(0, str.indexOf(";"));
				
				str = str.substr(str.indexOf(";") + 1);
				var tenkh = str.substring(0, str.indexOf(";"));
				
				str = str.substr(str.indexOf(";") + 1);
				var diachi = str.substring(0, str.length);
				addRow('kh_tbh_dpt', tbh_khdpt.length, makh, smartid, tenkh, diachi);					
							
		
		
	}
	
	function AutoChuyenPhai()
	{
		var tbh_khcdpt = document.getElementsByName("kh_tbh_cdptList");
		var tbh_khdpt = document.getElementsByName("kh_tbh_dptList");
		
		var i;
		for(i=0; i < tbh_khcdpt.length; i++)
		{
			var str = new String;
			if(tbh_khcdpt.item(i).checked)
			{
				ChuyenSangPhai(i);
			}
		}
		i=tbh_khcdpt.length;
		while(i>0){
				i=i-1;
				if(tbh_khcdpt.item(i).checked)
				{
					
					document.getElementById("tb_kh_tbh_cdpt").deleteRow(tbh_khcdpt.item(i).parentNode.parentNode.rowIndex);

				}
			
		}
	
	}
	
	function LocDuLieu()
	{
		document.forms['tbhForm'].action.value='tim';
    	document.forms['tbhForm'].submit();
		
    	
	}
	function ajaxOption(id)
	{
		var nppId = document.getElementById("nppId").value;
		var khTen = document.getElementById("khTen").value;
		var diachi = document.getElementById("diachi").value;
		
		var xmlhttp;
		
		if (window.XMLHttpRequest)
		{// code for IE7+, Firefox, Chrome, Opera, Safari
		   xmlhttp = new XMLHttpRequest();
		}
		else
		{// code for IE6, IE5
		   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function()
		{
		   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
		   {
		      document.getElementById(id).innerHTML = xmlhttp.responseText;
		   }
		}
		xmlhttp.open("GET","../../ajaxTuyenBh?id=" + id + "&nppId=" + nppId + "&khTen=" + khTen + "&diachi=" + diachi,true);
		xmlhttp.send();
	}
</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="tbhForm" method="post" action="../../ChuyenTuyenSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="nppId" id="nppId" value='<%=bean.getNppId()%>'>
		<input type="hidden" name="action" value='1'>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<!--begin body Dossier-->
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết
											lập dữ liệu nền &gt; Dữ liệu nền kinh doanh &gt; Đảo tuyến
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng <%=bean.getNppTen()%></TD>
									</TR>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellpadding="0" cellspacing="2">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="../../TuyenbanhangSvl?userId=<%=userId%>"> <img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												width="30" height="30" border="1" longdesc="Quay ve"
												style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript:saveform()"><IMG
													src="../images/Save30.png" title="Luu lai" alt="Luu lai"
													border="1" style="border-style: outset"></A>
											</div>
										</TD>
										<TD align="left">&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>

					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
									<textarea name="dataerror"
										style="width: 100%; color: #F00; font-weight: bold"
										cols="139%" rows="1"><%=bean.getMessage()%></textarea>
								</FIELDSET>
							</TD>
						</tr>
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle">&nbsp;Thông tin tuyến bán
										hàng cũ &nbsp;</LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">

										<TR>
											<TD style="width: 15%" class="plainlabel">Nhân viên bán hàng</TD>
											<TD class="plainlabel"><SELECT name="ddkdFromId"
												id="ddkdFromId" onChange="submitform();" class="select2" style =" width:250px">
													<option value=""></option>
													<%
														if (ddkdFrom != null)
																while (ddkdFrom.next()) {
																	if (ddkdFrom.getString("pk_seq").equals(
																			bean.getDddkdFromId())) {
													%>
													<option value='<%=ddkdFrom.getString("pk_seq")%>' selected><%=ddkdFrom.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=ddkdFrom.getString("pk_seq")%>'><%=ddkdFrom.getString("ten")%></option>
													<%
														}
																}
													%>
											</SELECT></TD>
										</TR>
										<%if(util.getLoaiNv().equals("3")){ %>
										<TR style="display: none">
											<TD style="width: 15%" class="plainlabel">Nhà phân phối</TD>
											<TD class="plainlabel"><SELECT name="nppFromId"
												id="nppFromId" onChange="submitform();">
													<option value=""></option>
													<%
													if (nppRs != null)
														while (nppRs.next()) {
															if (nppRs.getString("pk_seq").equals(bean.getNppFromId())) {
													%>
													<option value='<%=nppRs.getString("pk_seq")%>' selected><%=nppRs.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=nppRs.getString("pk_seq")%>'><%=nppRs.getString("ten")%></option>
													<%
														}
													}
													%>
											</SELECT></TD>
										</TR>
										<%} %>
										<TR>
											<TD class="plainlabel">Diễn giải tuyến bán hàng</TD>
											<TD class="plainlabel"><select name="tuyenFromId"
												id="tuyenFromId" onChange="submitform();" class="select2" style =" width:250px">
													<option value=""></option>
													<%
														if (tuyenFrom != null)
																while (tuyenFrom.next()) {
																	if (tuyenFrom.getString("pk_seq").equals(
																			bean.getTuyenFromId())) {
													%>
													<option value='<%=tuyenFrom.getString("pk_seq")%>'
														selected><%=tuyenFrom.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=tuyenFrom.getString("pk_seq")%>'><%=tuyenFrom.getString("ten")%></option>
													<%
														}
																}
													%>
											</select></TD>
										</TR>
									</TABLE>

								</FIELDSET>
							</TD>
						</TR>
					</TABLE>


					<TABLE width="100%" border="0" cellpadding="0">
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle">&nbsp;Thông tin tuyến bán
										hàng mới &nbsp;</LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">

										<TR>
											<TD style="width: 15%" class="plainlabel">Nhân viên bán
												hàng</TD>
											<TD class="plainlabel"><SELECT name="ddkdToId"
												id="ddkdToId" onChange="submitform();" class="select2" style =" width:250px">
													<option value=""></option>
													<%
														if (ddkdTo != null)
																while (ddkdTo.next()) {
																	if (ddkdTo.getString("pk_seq").equals(
																			bean.getDdkdToId())) {
													%>
													<option value='<%=ddkdTo.getString("pk_seq")%>' selected><%=ddkdTo.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=ddkdTo.getString("pk_seq")%>'><%=ddkdTo.getString("ten")%></option>
													<%
														}
																}
													%>
											</SELECT></TD>
										</TR>
										<%if(util.getLoaiNv().equals("3")){ %>
										<TR  style="display: none">
											<TD style="width: 15%" class="plainlabel">Nhà phân phối</TD>
											<TD class="plainlabel"><SELECT name="nppToId"
												id="nppToId" onChange="submitform();" class="select2" style =" width:250px">
													<option value=""></option>
													<%
													nppRs.beforeFirst();
													if (nppRs != null)
														while (nppRs.next()) {
															if (nppRs.getString("pk_seq").equals(bean.getNppToId())) {
													%>
													<option value='<%=nppRs.getString("pk_seq")%>' selected><%=nppRs.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=nppRs.getString("pk_seq")%>'><%=nppRs.getString("ten")%></option>
													<%
														}
													}
													%>
											</SELECT></TD>
										</TR>
										<%} %>
										<TR>
											<TD class="plainlabel">Diễn giải tuyến bán hàng</TD>
											<TD class="plainlabel"><select name="tuyenToId"
												id="tuyenToId" onChange="submitform();" class="select2" style =" width:250px">
													<option value=""></option>
													<%
														if (tuyenTo != null)
																while (tuyenTo.next()) {
																	if (tuyenTo.getString("pk_seq").equals(
																			bean.getTuyenToId())) {
													%>
													<option value='<%=tuyenTo.getString("pk_seq")%>' selected><%=tuyenTo.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=tuyenTo.getString("pk_seq")%>'><%=tuyenTo.getString("ten")%></option>
													<%
														}
																}
													%>
											</select></TD>
										</TR>
									</TABLE>
								</FIELDSET>
							</TD>
						</TR>
					</TABLE>
					<TABLE width=100% border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" valign="top">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%">
											<FIELDSET>
												<LEGEND class="legendtitle">Khách hàng được phân
													vào tuyến cũ này</LEGEND>
												<TABLE width="100%" border="0" cellspacing="1"
													cellpadding="4" id="tb_kh_tbh_dpt">
													<tbody id="kh_tbh_dpt">
														<TR class="tbheader">
															<TH width="5%">Tuyến</TH>
															<TH width="5%">Thứ tự</TH>
															<TH width="13%">Mã KH</TH>
															<TH width="30%">Tên KH</TH>
															<TH width="40%">Địa chỉ</TH>
															<TH width="12%">Tần số</TH>
														</TR>
														<%
															int i = 0;
																String lightrow = "tblightrow";
																String darkrow = "tbdarkrow";
																if (kh_tbh_dpt != null)
																	while (kh_tbh_dpt.next()) {
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
															<TD align="center"><input name='diengiai'
																type="text"
																value='Thứ <%=kh_tbh_dpt.getString("Ngay")%>'></TD>
															<TD align="center"><input name='thutu' type="text"
																value='<%=kh_tbh_dpt.getString("sott")%>' size="5"></TD>
															<TD align="left"><input type="hidden" name="khIds"
																value='<%=kh_tbh_dpt.getString("khId")%>'> <%=kh_tbh_dpt.getString("smartid")%>
															</TD>
															<TD align="left"><%=kh_tbh_dpt.getString("ten")%>
															</TD>
															<TD align="center"><p align="left">
																	<%=kh_tbh_dpt.getString("diachi")%></TD>
															<TD align="center"><select name="tansoList">
																    
																	<%-- <%
																		if (kh_tbh_dpt.getString("tanso").equals(
																							"1 tuan / 1 lan")) {
																	%>
																	<option value="1 tuan / 1 lan" selected>1 tuan
																		/ 1 lan</option>
																	<option value="2 tuan / 1 lan-chan">2 tuan / 1
																		lan - chan</option>
																	<option value="2 tuan / 1 lan-le">2 tuan / 1
																		lan - le</option>
																	<%
																		} else if (kh_tbh_dpt.getString("tanso").equals(
																							"2 tuan / 1 lan-chan")) {
																	%>
																	<option value="1 tuan / 1 lan">1 tuan / 1 lan</option>
																	<option value="2 tuan / 1 lan-chan" selected>2
																		tuan / 1 lan - chan</option>
																	<option value="2 tuan / 1 lan-le">2 tuan / 1
																		lan - le</option>
																	<%
																		} else {
																	%>
																	<option value="1 tuan / 1 lan" selected>1 tuan
																		/ 1 lan</option>
																	<option value="2 tuan / 1 lan-chan">2 tuan / 1
																		lan - chan</option>
																	<option value="2 tuan / 1 lan-le" selected>2
																		tuan / 1 lan - le</option>
																	<%
																		}
																	%> --%>
																	<option value="<%=kh_tbh_dpt.getString("tanso") %>">
																	<%=kh_tbh_dpt.getString("tanso") %>
																	</option>
																	
																	
															</select></TD>
														</TR>
														<%
															i++;
																	}
														%>
														<tr class="tbfooter">
															<td colspan="6">&nbsp;</td>
														</tr>
													</tbody>
												</TABLE>

											</FIELDSET>
										</TD>
									</TR>
								</TABLE>
							</TD>

						</TR>
					</TABLE> <!--end body Dossier-->
				</TD>
			</TR>

		</TABLE>
	</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>


<%
	session.setAttribute("ctuyenBean", null);
	}
%>
</HTML>
