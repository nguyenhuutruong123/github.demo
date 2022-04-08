<%@page import="geso.dms.center.beans.capnhatnhanvien.ICapnhatnhanvien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ page  import = "geso.dms.center.beans.nhacungcap.INhacungcap" %>
<%@ page  import = "geso.dms.center.beans.nhacungcap.INhacungcapList" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/AHF/index.jsp");
	} else {
%>
<%
	ICapnhatnhanvien obj = (ICapnhatnhanvien) session.getAttribute("obj");
%>
<%
	ResultSet quyen = obj.getquyen();
%>
<%
	ResultSet quyenchon = obj.getquyenchon();
%>
<%
	ResultSet kenh = obj.getkenh();
%>

<%
	ResultSet kenhRs = obj.getKenhRs();
%>

<%
	ResultSet kenhchon = obj.getkenhchon();
%>
<%
	ResultSet npp = obj.getnpp();
%>
<%
	ResultSet nppchon = obj.getnppchon();
%>
<%
	ResultSet sanpham = obj.getsanpham();
%>
<%
	ResultSet sanphamchon = obj.getsanphamchon();
%>
<%
	ResultSet kho = obj.getKhoRs();
%>
<%
	ResultSet khochon = obj.getKhochonrs();
%>

<%
	ResultSet nhomskus = obj.getNhomskus();
%>
<%
	ResultSet nhomskuschon = obj.getNhomskuschonrs();
%>

<%
	ResultSet vung = obj.getvung();
%>
<%
	ResultSet khuvuc = obj.getkhuvuc();
%>
<%
	ResultSet nhanhang = obj.getnhanhang();
%>
<%
	ResultSet chungloai = obj.getchungloai();
%>
<%
	ResultSet nhaphanphoi = obj.getnhaphanphoi();

	ResultSet dmquyen = obj.getDanhmucquyenRs();
	
	ResultSet ttRs = obj.getTtRs();
	ResultSet qhRs = obj.getQhRs();
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("CapnhatnhanvienSvl","",nnId);	
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
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
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$("select:not(.notuseselect2)").select2({ width: 'resolve' }); 
		//$(".select2").select2();
		
	});
</script>

<script>
//perform JavaScript after the document is scriptable.
$(document).ready(function() {

    //When page loads...
    $(".tab_content").hide(); //Hide all content
    var index = $("ul.tabs li.current").show().index(); 
    $(".tab_content").eq(index).show();
    //On Click Event
    $("ul.tabs li").click(function() {
  
        $("ul.tabs li").removeClass("current"); //Remove any "active" class
        $(this).addClass("current"); //Add "active" class to selected tab
        $(".tab_content").hide(); //Hide all tab content  
        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
        $(activeTab).show(); //Fade in the active ID content
        return false;
    });

});


	function clearform() 
	{
		document.nccForm.tenviettat.value = "";
		document.nccForm.ncc.value = "";
		document.nccForm.tungay.value = "";
		document.nccForm.denngay.value = "";
		document.nccForm.trangthai.selectedIndex = 2;
		submitform();
	}

	function submitform() 
	{
		
		var active =$(".tabs li.current").index();
		document.forms["nccForm"].activeTab.value =active;
		
		document.forms['nccForm'].action.value = 'search';
		
		document.forms['nccForm'].submit();
	}

	function newform() {

		var active =$(".tabs li.current").index();
		document.forms["nccForm"].activeTab.value =active;
		
		var loai = $("#loai").val();
		if (loai === "1" || loai === "2" || loai === "3") {
			var title = loai === "1" ? "RSM" : loai === "2" ? "ASM"
					: loai === "3" ? "GSBH" : "";
			if ($("#loaiId").val().trim().length <= 0) {
				alert("Bạn chưa chọn tên " + title);
				return;
			}
		}

		document.forms['nccForm'].action.value = 'save';
		document.forms['nccForm'].submit();
	}
	function setThutu(h) {
		document.forms['nccForm'].thutu.value = h;
		document.forms['nccForm'].submit();

	}
	function checkedAll(chk, allquyen) 
	{
		for (var i = 0; i < chk.length; i++) 
		{
			if (allquyen.checked == true) 
			{
				chk[i].checked = true;
			} else 
			{
				chk[i].checked = false;
			}
		}
	}
	
	function check_all(checkid, doituong)
	{   
			 var spIds = document.getElementsByName(doituong);
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = checkid;
			 }
	}

	
	
	function setNppIdSelected() 
	{
		var nppId = document.getElementsByName('npp');
		var nppIds = "";
		var nppSelected = document.getElementById("nppSelected");
		for ( var ii = 0; ii < nppId.length; ii++) 
		{
			if (nppId.item(ii).checked == true) 
			{
				nppIds = nppIds + nppId.item(ii).value + ",";
			}
		}
		if (nppIds.length > 0) 
		{
			nppIds = nppIds.substring(0,nppIds.length-1);
		}
	nppSelected.value=nppIds;
}
	
	
	
	
	function AjaxNpp()
	{
		 	var kenhId = document.getElementById("kenhId");
		 	var kenhIds="";
				for(var i = 0; i < kenhId.options.length ; i++)
				{
					if(kenhId.options[i].selected)
						kenhIds += kenhId.options[i].value + ',';
				}
				if(kenhIds.length>0)
				{
					kenhIds=kenhIds.substring(0,kenhIds.length-1);
				}
				
				var vungId = document.getElementById("vungId");
				var vungIds="";
				for(var i = 0; i < vungId.options.length ; i++)
				{
					if(vungId.options[i].selected)
						vungIds += vungId.options[i].value + ',';
				}
				if(vungIds.length>0)
				{
					vungIds=vungIds.substring(0,vungIds.length-1);
				}
				
				
				var khuvucId = document.getElementById("khuvucId");
				var khuvucIds="";
				for(var i = 0; i < khuvucId.options.length ; i++)
				{
					if(khuvucId.options[i].selected)
						khuvucIds += khuvucId.options[i].value + ',';
				}
				if(khuvucIds.length>0)
				{
					khuvucIds=khuvucIds.substring(0,khuvucIds.length-1);
				}
				
				
				
				//Lay tat ca nppIdSelecd nhung nhapp da tick chon 
				var all_NppId_Checked="";
				var nppId= document.getElementsByName('npp');
				for(var ii=0;ii<nppId.length;ii++)
				{
					if(nppId.item(ii).checked==true)
					{
						all_NppId_Checked=all_NppId_Checked+nppId.item(ii).value+",";
					}		
				}
				if(all_NppId_Checked.length>0)
				{
					all_NppId_Checked=all_NppId_Checked.substring(0,all_NppId_Checked.length-1);
				}
				
				 $.get("../../CapNhatNhanVienAjax?action=ajaxNpp&kenhId="+kenhIds+"&vungId="+vungIds+"&kvId="+khuvucIds+"&nppSelected="+all_NppId_Checked+"", function(list,status) {
						var table = $('#nppTable');
							table.html(
								'<TABLE id="nppTable">'+
				                    '<TR class="tbheader">'+
			                        '<TH align="center" width="10%">Mã</TH>'+
			                        '<TH align="left" width="40%"> Tên </TH>'+
			                        '<TH align="center" width="20%"> Chọn tất cả <input type ="checkbox" name="checknpp_all"  onchange="check_all(this.checked ,\'npp\');"   "></TH>'+
			                    '</TR>'
		 					);
							$.each(list, function(index, data) {
								var checked='';
								if(all_NppId_Checked.indexOf(data.nppId)>=0)
									 checked='checked="checked"';
								var vclass= document.createElement("tr");
									vclass.setAttribute("class", "tblightrow");
								if(index % 2 !=0)
									vclass.setAttribute("class", "tblightrow");
								$(vclass).appendTo(table)
									.append($('<td><input type=text   value='+data.nppMa +' style="width: 100%;"  readonly="readonly" ></td>' ))
									.append($("<td><input type='text'  value= '"+data.nppTen+"' style='width: 100%;'  readonly='readonly'> </td>" ))
									.append($('<td><input type=checkbox value='+data.nppId+' name="npp" '+checked+'  ></td>' ));
								
							});
						
					});
			 
	}
	
	function ShowPopup() {
	    document.getElementById('light').style.display = 'block';
	    document.getElementById('fade').style.display = 'block'
	}
	function HidePopup() {
	    document.getElementById('light').style.display = 'none';
	    document.getElementById('fade').style.display = 'none'
	}
	
	function ajaxOption(id)
	{
		var str = '';
		var str2 = '';
		var str3='';
		var str4='';
		var tinhIds =document.getElementById("ttId");
		for(var j = 0; j < tinhIds.options.length ; j++)
		{
			if(tinhIds.options[j].selected)
				str4 += tinhIds.options[j].value + ',';
		}
		
		var str5='';
		var qhIds =  document.getElementById("qhId");
		for(var j = 0; j < qhIds.options.length ; j++)
		{
			if(qhIds.options[j].selected)
				str5 += qhIds.options[j].value + ',';
		}
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
		xmlhttp.open("POST","../../AjaxNhomNpp?type=" + id + "&kenhId=" + str + "&vungId=" + str2 + "&kvId=" + str3 + "&tpId=" + str4 + "&qhId=" + str5 + "&nhomId=<%=obj.getId()%>",true);
		xmlhttp.send();
	}
	
	
</SCRIPT>
	

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


	<form name="nccForm" method="post" action="../../CapnhatnhanviennewSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="action" value='1'> <input type="hidden"
			name="Id" value='<%=obj.getId()%>'> <input type="hidden"
			name="manv" value=''> <input type="hidden" name="chonquyen"
			value=''> <input type="hidden" id="nppSelected"
			name="nppSelected" value='<%=obj.getNppIds()%>'> <input
			type="hidden" id="activeTab" name="activeTab"
			value='<%=obj.getActiveTab()%>'>

		<div id="main" style="width: 100%">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=" "+url %> &gt;
					Cập nhật</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng ",nnId) %><%=userTen%>
				</div>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Cập nhật nhân viên",nnId) %> </legend>
					<div style="width: 100%; float: none" align="left">
						<TABLE border="0" width="100%">
							<TR>
							<TR>
								<TD>
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class="tbdarkrow">
											<TD width="30" align="left"><A
												href="../../CapnhatnhanvienSvl?userId=<%=userId%>"><img
													src="../images/Back30.png" alt="Quay ve" title="Quay ve"
													border="1" longdesc="Quay ve" style="border-style: outset">
											</A></TD>
											<TD width="2" align="left"></TD>
											<TD width="30" align="left"><A
												href="javascript: newform()"><IMG
													src="../images/Save30.png" title="Luu lai" alt="Luu lai"
													border="1" style="border-style: outset"> </A></TD>
											<TD>&nbsp;</TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
										<LEGEND class="legendtitle"><%=ChuyenNgu.get("Thông báo",nnId) %> </LEGEND>
										<textarea name="dataerror"
											style="width: 100%; color: #F00; font-weight: bold"
											cols="100%" rows="1"><%=obj.getmsg()%> </textarea>
									</FIELDSET>
								</TD>
							</tr>
						</TABLE>

						<%  String style="";
	String current="";
 	if(obj.getphanloai().equals("1")){
 		style="style=\"display:none\"";
 	}
 	%>


						<table width="100%">
							<tr>
								<td>

									<ul class="tabs">
										<li
											<%=obj.getActiveTab().equals("0") ? "class='current'" : "" %>>
											<a href="#tabNv"><%=ChuyenNgu.get("Cập nhật nhân viên",nnId) %></a>
										</li>
										<%if(obj.getphanloai().equals("2")){ %>
										<li
											<%=obj.getActiveTab().equals("1") ? "class='current'" : "" %>><a
											href="#tabMenu"><%=ChuyenNgu.get("Menu",nnId) %></a></li>
										<li
											<%=obj.getActiveTab().equals("2") ? "class='current'" : "" %>><a
											href="#tabKenh"><%=ChuyenNgu.get("Kênh",nnId) %></a></li>
										<li
											<%=obj.getActiveTab().equals("3") ? "class='current'" : "" %>><a
											href="#tabNpp"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></a></li>
										<li
											<%=obj.getActiveTab().equals("4") ? "class='current'" : "" %>><a
											href="#tabSp"><%=ChuyenNgu.get("Sản phẩm",nnId) %></a></li>
										<li
											<%=obj.getActiveTab().equals("5") ? "class='current'" : "" %>><a
											href="#tabKho"><%=ChuyenNgu.get("Kho",nnId) %></a></li>
									<%-- 	<li
											<%=obj.getActiveTab().equals("6") ? "class='current'" : "" %>><a
											href="#tabNhomskus">Nhóm SKU's</a></li> --%>
										<%} %>
									</ul>

									<div class="panes">
										<div id="tabNv" class="tab_content">

											<TABLE width="100%" border="0" cellspacing="1px"
												cellpadding="3px">
												<TR class="plainlabel" valign="bottom">

													<TD width="100%" align="left">
														<FIELDSET>
															<LEGEND class="legendtitle"><%=ChuyenNgu.get("Cập nhật nhân viên",nnId) %> </LEGEND>

															<TABLE class="tblight" width="100%" cellpadding="6"
																cellspacing="0">
																<TR>
																	<TD width="15%" class="plainlabel"><%=ChuyenNgu.get("Họ tên",nnId) %></TD>
																	<TD class="plainlabel"><INPUT name="ten"
																		type="text" size="40" value="<%=obj.getTen()%>">
																	</TD>
																	<TD class="plainlabel"></TD>
																</TR>
																<TR>
																	<TD class="plainlabel"><%=ChuyenNgu.get("Ngày sinh",nnId) %></TD>
																	<TD class="plainlabel"><INPUT name="ngaysinh"
																		type="text" size="20" value="<%=obj.getngaysinh()%>">
																	</TD>
																	<TD class="plainlabel"></TD>
																</TR>
																<TR>
																	<TD class="plainlabel"><%=ChuyenNgu.get("Email",nnId) %></TD>
																	<TD class="plainlabel"><INPUT name="email"
																		type="text" size="40" value="<%=obj.getemail()%>">
																	</TD>
																	<TD class="plainlabel"></TD>
																</TR>
																<TR>
																	<TD class="plainlabel"><%=ChuyenNgu.get("Điện thoại",nnId) %></TD>
																	<TD class="plainlabel"><INPUT name="dienthoai"
																		type="text" size="20" value="<%=obj.getdienthoai()%>">
																	</TD>
																	<TD class="plainlabel"></TD>
																</TR>
																<TR>
																	<TD class="plainlabel"><%=ChuyenNgu.get("Tên đăng nhập",nnId) %></TD>
																	<TD class="plainlabel"><INPUT name="tendangnhap"
																		type="text" size="20"
																		value="<%=obj.gettendangnhap()%>"></TD>
																	<TD class="plainlabel"></TD>
																</TR>

																<TR>
																	<TD class="plainlabel"><%=ChuyenNgu.get("Mật khẩu khởi tạo",nnId) %></TD>
																	<TD class="plainlabel"><INPUT name="matkhau"
																		type="text" size="20" value="<%=obj.getmatkhau()%>">
																	</TD>
																	<TD class="plainlabel"></TD>
																</TR>

																<tr>
																	<TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId) %>&nbsp;&nbsp;&nbsp;
																		<%
																		if (obj.gettrangthai().trim().equals("1")) {
																		%> <input name="trangthai" type="checkbox" value="1"
																		checked> <%
																		} else {
																		%> <input name="trangthai" type="checkbox" value="1">
																		<% } %>
																	</TD>
																	<% if (obj.getphanloai().equals("2")) { %>
																	<TD class="plainlabel"><%=ChuyenNgu.get("Nhận mail DMS",nnId) %>&nbsp;&nbsp;&nbsp;
																		<%
																		if (obj.getNhanmaildms().trim().equals("1")) {
																		%> <input name="nhanmaildms" type="checkbox" value="1"
																		checked> <%
																		} else {
																		%> <input name="nhanmaildms" type="checkbox" value="1">
																		<% } %>
																	</TD> 
																	<% } else { %>
																	<TD class="plainlabel"></TD>
																	<% } %>
																	<TD class="plainlabel"></TD>
																</TR>
																<TR>
																	<TD class="plainlabel"><%=ChuyenNgu.get("Phân loại",nnId) %></TD>
																	<TD class="plainlabel"><select name="phanloai" class='select2'
																		onchange="submitform();" style="width: 200px;" >
																			<option value=""></option>
																			<%
																				if (obj.getphanloai().equals("2")) {
																			%>
																			<option value="2" selected><%=ChuyenNgu.get("Trung tâm",nnId) %></option>
																			<option value="1"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></option>
																			<%
																				} else if (obj.getphanloai().equals("1")) {
																			%>
																			<option value="1" selected><%=ChuyenNgu.get("Nhà phân phối",nnId) %></option>
																			<option value="2"><%=ChuyenNgu.get("Trung tâm",nnId) %></option>
																			<%
																				} else {
																			%>
																			<option value="1"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></option>
																			<option value="2"><%=ChuyenNgu.get("Trung tâm",nnId) %></option>
																			<%
																				}
																			%>
																	</select></TD>
																	<TD class="plainlabel"></TD>
																</TR>

																<%
																	if (obj.getphanloai().equals("1")) {
																%>
																<TR>

																	<TD class="plainlabel"><%=ChuyenNgu.get("Vùng",nnId) %></TD>
																	<TD class="plainlabel"><select name="vungId"
																		onchange="submitform();">
																			<option value="" selected></option>
																			<%
																				if (vung != null)
																							while (vung.next()) {
																								if (vung.getString("pk_seq")
																										.equals(obj.getvungId())) {
																			%>
																			<option value="<%=vung.getString("pk_seq")%>"
																				selected><%=vung.getString("ten")%></option>
																			<%
																				} else {
																			%>
																			<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
																			<%
																				}
																							}
																			%>
																	</select></TD>
																	<TD class="plainlabel"></TD>
																</TR>

																<TR>
																	<TD class="plainlabel"><%=ChuyenNgu.get("Khu Vực",nnId) %></TD>
																	<TD class="plainlabel"><select name="khuvucId"
																		onchange="submitform();">
																			<option value="" selected></option>
																			<%
																				if (khuvuc != null)
																							while (khuvuc.next()) {
																								if (khuvuc.getString("pk_seq").equals(
																										obj.getkhuvucId())) {
																			%>
																			<option value="<%=khuvuc.getString("pk_seq")%>"
																				selected><%=khuvuc.getString("ten")%></option>
																			<%
																				} else {
																			%>
																			<option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
																			<%
																				}
																							}
																			%>
																	</select></TD>
																	<TD class="plainlabel"></TD>
																</TR>

																<TR>
																	<TD class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
																	<TD class="plainlabel"><select name="nppId">
																			<option value="" selected></option>
																			<%
																			String nppId = obj.getnppId();
																				if (nhaphanphoi != null) {
																					while (nhaphanphoi.next()) {
																						String temp = nhaphanphoi.getString("convsitecode");
																						if (nppId != null && temp != null && temp.equals(nppId)) {
																			%>
																			<option
																				value="<%=nhaphanphoi.getString("convsitecode")%>"
																				selected><%=nhaphanphoi.getString("ten")%></option>
																			<%
																				} else {
																			%>
																			<option
																				value="<%=nhaphanphoi.getString("convsitecode")%>"><%=nhaphanphoi.getString("ten")%></option>
																			<%
																				}
																			}}
																			%>
																	</select></TD>
																	<TD class="plainlabel"></TD>
																</TR>																
																
																<TR>
																	<TD class="plainlabel"><%=ChuyenNgu.get("Danh mục quyền",nnId) %> </TD>
																	<TD class="plainlabel"><select name="dmquyenId">
																			<option value="" selected></option>
																			<%
																				if (dmquyen != null)
																							while (dmquyen.next()) {
																								if (dmquyen.getString("PK_SEQ").trim()
																										.equals(obj.getdmquyenId().trim())) {
																			%>
																			<option
																				value="<%=dmquyen.getString("PK_SEQ")%>"
																				selected><%=dmquyen.getString("DIENGIAI")%></option>
																			<%
																				} else {
																			%>
																			<option
																				value="<%=dmquyen.getString("PK_SEQ")%>"><%=dmquyen.getString("DIENGIAI")%></option>
																			<%
																				}
																			}
																			%>
																	</select></TD>
																	<TD class="plainlabel"></TD>
																</TR>
																																
																<%
																	}
																%>

																<%
																	if (obj.getphanloai().equals("2")) {
																%>
																<TR>
																	<TD class="plainlabel"><%=ChuyenNgu.get("Loại NV",nnId) %></TD>
																	<TD class="plainlabel"><select name="loai"
																		id="loai" onchange="submitform();">
																			<option value="0"
																				<%=obj.getLoai().equals("0") ? " selected " : ""%> ><%=ChuyenNgu.get("Admin HO",nnId) %> </option>
																			
																			<option value="5"
																				<%=obj.getLoai().equals("5") ? " selected " : ""%>><%=ChuyenNgu.get("Giám đốc kênh",nnId) %></option>	
																				
																			<option value="6"
																				<%=obj.getLoai().equals("6") ? " selected " :  ""%>><%=ChuyenNgu.get("Sales Admin",nnId) %>  </option>	
																			<option value="7"
																				<%=obj.getLoai().equals("7") ? " selected " : ""%>><%=ChuyenNgu.get("Sales Data",nnId) %> </option>
																			<option value="8"
																				<%=obj.getLoai().equals("8") ? " selected " :  ""%>><%=ChuyenNgu.get("Trade Marketing",nnId) %></option>
																			<option value="9"
																				<%=obj.getLoai().equals("9") ? " selected " :  ""%>><%=ChuyenNgu.get("Kế toán công ty",nnId) %></option>		
																			<option value="10"
																				<%=obj.getLoai().equals("10") ? " selected " :  ""%>><%=ChuyenNgu.get("Admin NPP",nnId) %></option>		
																																								
																			
																			<option value="1"
																				<%=obj.getLoai().equals("1") ? " selected " : ""%>><%=ChuyenNgu.get("RSM",nnId) %></option>
																			<option value="2"
																				<%=obj.getLoai().equals("2") ? " selected " : ""%>><%=ChuyenNgu.get("ASM",nnId) %></option>
																			<option value="3"
																				<%=obj.getLoai().equals("3") ? " selected " : ""%>><%=ChuyenNgu.get("GSBH",nnId) %></option>
																			<option value="4"
																				<%=obj.getLoai().equals("4") ? " selected " : ""%>><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></option>
																			
																																							
																	</select></TD>
																	<TD class="plainlabel"></TD>
																</TR>
																<%
																	if (obj.getLoai().equals("1") || obj.getLoai().equals("2")|| obj.getLoai().equals("3")&& obj.getLoaiRs() != null) {
																				ResultSet loaiRs = obj.getLoaiRs();
																				String title = obj.getLoai().equals("1") ? "RSM" : obj.getLoai().equals("2") ? "ASM" : obj.getLoai().equals("3") ? "GSBH" : "NV";
																%>
																<TR>
																	<TD class="plainlabel"><%=title%></TD>
																	<TD class="plainlabel"><select name="loaiId"
																		id="loaiId" style="width: 200px;">
																			<option value=""></option>
																			<%
																				try {
																								while (loaiRs.next()) {
																									if (obj.getLoaiId().equals(
																											loaiRs.getString("PK_SEQ"))) {
																			%>
																			<option value="<%=loaiRs.getString("PK_SEQ")%>"
																				selected><%=loaiRs.getString("TEN")%></option>
																			<%
																				} else {
																			%>
																			<option value="<%=loaiRs.getString("PK_SEQ")%>"><%=loaiRs.getString("TEN")%></option>
																			<%
																				}
																								}
																							} catch (Exception e) 
																							{
																								e.printStackTrace();
																							}
																							}
																			%>
																	</select></TD>
																	<TD class="plainlabel"></TD>
																	
																</TR>
																															
																<%
																	}
																%>
															</TABLE>
														</FIELDSET>
													</TD>
												</TR>
											</TABLE>
										</div>
										
										<%-- <%if(obj.getphanloai().equals("1")){ %>										
											<table width="100%" cellspacing="0px" cellpadding="4px">
											 <TR class="plainlabel" valign="bottom">
											    <td width="100%">
											      <fieldset style="width: 40%; float: left;"> 
											<legend>Tỉnh thành &nbsp;</legend> 
											<select id="ttId" name="ttId" multiple="multiple" style="width: 100%; height: 200px" onChange = "ajaxOption('qhId')" >
											           <% if(ttRs != null) {
											            while(ttRs.next()) 
											            {
											              if(obj.getTtId().indexOf(ttRs.getString("pk_seq")) >= 0 ){ %>
											                <option value="<%= ttRs.getString("pk_seq") %>" selected style="padding: 2px" ><%= ttRs.getString("ten") %></option>
											            <%}else { %>
											            	<option value="<%= ttRs.getString("pk_seq") %>" style="padding: 2px" ><%= ttRs.getString("ten")  %></option>
											            <%} }}%>        	
											       </select>
											       </fieldset>
											   <fieldset style="width: 55%; float: right;">
											<legend>Quận Huyện &nbsp;</legend> 
											<select name="qhId" id="qhId" multiple="multiple" style="width: 100%; height: 200px" >
											           <% if(qhRs != null) {
											            while(qhRs.next()) 
											            {
											              if(obj.getQhId().indexOf(qhRs.getString("pk_seq")) >= 0 ){ %>
											                <option value="<%= qhRs.getString("pk_seq") %>" selected style="padding: 2px" ><%= qhRs.getString("ten")  %></option>
											            <%}else { %>
											            	<option value="<%= qhRs.getString("pk_seq") %>" style="padding: 2px"><%= qhRs.getString("ten") %></option>
											            <%} }}%>        	
											                 </select>
											                 </fieldset>
											      
											           </td>
										
											        </TR>
											       <TR class="plainlabel" valign="bottom">
											        	            <td width="100%">
												     	 <fieldset > 
														
														<legend>Kho &nbsp;</legend> 
												<TABLE width="100%" border="0" cellspacing="1px"
												cellpadding="3px">
												<TR class="tbheader">
													<!-- <TH width="20%">Mã kho</TH> -->
													<TH width="20%">Tên kho</TH>
													<TH width="20%">Diễn giải</TH>
													<TH width="10%">Chọn tất cả<input type="checkbox"
														name="allkho" value='1'
														onclick="checkedAll(document.nccForm.kho,document.nccForm.allkho);">
													</TH>							
												</TR>
										<%int		 k = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(khochon !=null)
									 while(khochon.next())
									 { if (k % 2 != 0) {%>
												
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="20%"><%=khochon.getString("Ten")%></TH>
													<TH width="20%"><%=khochon.getString("diengiai")%></TH>
													<TH width="10%"><input type="checkbox" name="kho"
														value="<%=khochon.getString("pk_seq")%>" checked>
													</TH>
												</TR>
												<% k++; 
			                                       }
			                                     if(kho !=null)
												 while(kho.next())
												 {
													 if (k % 2 != 0) {%>
															<TR class=<%=lightrow%> align="left">
																<%} else { %>
															
															<TR class=<%= darkrow%> align="left">
																<%} %>
																<TH width="20%"><%=kho.getString("Ten")%></TH>
																<TH width="20%"><%=kho.getString("diengiai")%></TH>
																<TH width="10%"><input type="checkbox" name="kho"
																	value="<%=kho.getString("pk_seq")%>"></TH>
															</TR>
															<% k++; 
			                                       }%>
			
															<TR>
																<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
															</TR>
												
											</TABLE>
												       </fieldset>
											       </td>
											        </TR>
											</table>		
										<%} %> --%>										

										<%if(obj.getphanloai().equals("2")){ %>
										<div id="tabMenu" class="tab_content">
											<TABLE width="100%" border="0" cellspacing="1px"
												cellpadding="3px">
												<TR class="tbheader">
													<TH width="20%"><%=ChuyenNgu.get("Tên quyền",nnId) %></TH>
													<TH width="10%">
													<!-- <input type="checkbox"
														name="allquyen" value='1'
														onclick="checkedAll(document.nccForm.quyen,document.nccForm.allquyen);"> -->
													</TH>
												</TR>
												<% 
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(quyenchon !=null)
									 while(quyenchon.next())
									 { if (m % 2 != 0) {%>
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="20%"><%=quyenchon.getString("Ten")%></TH>
													<TH width="10%"><input type="checkbox" name="quyen"
														value="<%=quyenchon.getString("pk_seq")%>" checked></TH>
												</TR>
												<% m++; 
                                       }
                                     if(quyen !=null)
									 while(quyen.next())
									 {
										 if (m % 2 != 0) {%>
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="20%"><%=quyen.getString("Ten")%></TH>
													<TH width="10%"><input type="checkbox" name="quyen"
														value="<%=quyen.getString("pk_seq")%>"></TH>
												</TR>
												<% m++; 
                                       }%>

												<TR>
													<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
												</TR>
											</TABLE>
										</div>


										<div id="tabKenh" class="tab_content" <%=style%>>

											<TABLE width="100%" border="0" cellspacing="1px"
												cellpadding="3px">

												<TR class="tbheader">
													<TH width="20%"><%=ChuyenNgu.get("Tên kênh",nnId) %></TH>
													<TH width="10%"><%=ChuyenNgu.get("Chọn tất cả",nnId) %><input type="checkbox"
														name="allkenh" value='1'
														onclick="checkedAll(document.nccForm.kenh,document.nccForm.allkenh);">
													</TH>
												</TR>
												<% 
									int n = 0;
									//String lightrow = "tblightrow";
								//	String darkrow = "tbdarkrow";
									if(kenhchon !=null)
									 while(kenhchon.next())
									 { if (n % 2 != 0) {%>
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="20%"><%=kenhchon.getString("Ten")%></TH>
													<TH width="10%"><input type="checkbox" name="kenh"
														value="<%=kenhchon.getString("pk_seq")%>" checked></TH>
												</TR>
												<% n++; 
                                       }
                                     if(kenh !=null)
									 while(kenh.next())
									 {
										 if (n % 2 != 0) {%>
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="20%"><%=kenh.getString("Ten")%></TH>
													<TH width="10%"><input type="checkbox" name="kenh"
														value="<%=kenh.getString("pk_seq")%>"></TH>
												</TR>
												<% n++; 
                                       }%>

												<TR>
													<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
												</TR>
											</TABLE>
										</div>


										<div id="tabNpp" class="tab_content" <%=style%>>
											<TABLE width="100%" border="0" cellspacing="1px"
												cellpadding="3px">

												<TR class="plainlabel" valign="bottom">
													<th colspan="3">



														<fieldset style="width: 25%; float: left;">
															<legend><%=ChuyenNgu.get("Kênh bán hàng",nnId) %>  </legend>
															<select name="kenhId" id="kenhId" multiple="multiple"
																onchange="AjaxNpp();">
																<option value=""></option>
																<% if(kenhRs != null) {
		                         while(kenhRs.next()) 
		                         {
		                           if(obj.getKenhId().indexOf(kenhRs.getString("pk_seq")) >= 0 ){ %>
																<option value="<%= kenhRs.getString("pk_seq") %>"
																	selected style="padding: 2px"><%= kenhRs.getString("ten") %></option>
																<%}else { %>
																<option value="<%= kenhRs.getString("pk_seq") %>"
																	style="padding: 2px"><%= kenhRs.getString("ten") %></option>
																<%} }}%>
															</select>
														</fieldset>

														<fieldset style="width: 25%; float: left;">
															<legend><%=ChuyenNgu.get("Vùng",nnId) %> &nbsp;</legend>
															<select name="vung" id="vung" multiple="multiple"
																onchange="AjaxNpp()">
																<option value=""><%=ChuyenNgu.get("Chọn hết",nnId) %></option>
																<% if(vung != null) {
                         while(vung.next()) 
                         {
                           if(obj.getVungId().indexOf(vung.getString("pk_seq")) >= 0 ){ %>
																<option value="<%= vung.getString("pk_seq") %>" selected
																	style="padding: 2px"><%= vung.getString("ten") %></option>
																<%}else { %>
																<option value="<%=vung.getString("pk_seq") %>"
																	style="padding: 2px"><%= vung.getString("ten") %></option>
																<%} }}%>
															</select>
														</fieldset>

														<fieldset style="width: 25%; float: left;">
															<legend><%=ChuyenNgu.get("Khu Vực",nnId) %>&nbsp;</legend>
															<select name="khuvuc" multiple="multiple" id="khuvuc"
																onchange="AjaxNpp()">
																<option value=""><%=ChuyenNgu.get("Chọn hết",nnId) %></option>
																<% if(khuvuc != null) 
			            {
			            	while(khuvuc.next())
	                          {
	                            if(obj.getKhuvucId().indexOf(khuvuc.getString("pk_seq")) >= 0 )
	                            { %>
																<option value="<%=khuvuc.getString("pk_seq") %>"
																	selected style="padding: 2px"><%=khuvuc.getString("ten") %></option>
																<%}else { %>
																<option value="<%=khuvuc.getString("pk_seq") %>"
																	style="padding: 2px"><%=khuvuc.getString("ten") %></option>
																<%}}}%>
															</select>
														</fieldset>
													</th>
												</TR>
												<TR class="plainlabel" valign="bottom">
													<th colspan="3" align="left">
														<a class="button" href="javascript:submitform();">
        												<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Hiển thị Npp theo điều kiện",nnId) %> </a>
													</th>
												</TR>
											</table>

											<TABLE id="nppTable" width="100%" border="0"
												cellspacing="1px" cellpadding="3px">
												<TR class="tbheader">
													<TH width="20%"><%=ChuyenNgu.get("Mã",nnId) %></TH>
													<TH width="20%"><%=ChuyenNgu.get("Tên",nnId) %></TH>
													<TH width="10%"><%=ChuyenNgu.get("Chọn tất cả",nnId) %><input type="checkbox"
														name="allnpp" value='1'
														onclick="check_all(this.checked ,'npp');">
													</TH>
												</TR>


												<% 
									int h = 0;
                                     if(npp !=null)
									 while(npp.next())
									 {
										 if (h % 2 != 0) {%>
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="20%"><%=npp.getString("nppMa")%></TH>
													<TH width="20%"><%=npp.getString("nppTen")%></TH>
													<TH width="10%">
														<%if(obj.getNppIds().indexOf(npp.getString("NppId"))>=0){ System.out.println(npp.getString("NppId")+"___"+obj.getNppIds().indexOf(npp.getString("NppId"))); %>
														<input type="checkbox" name="npp"
														value="<%=npp.getString("NppId")%>" checked="checked">
														<%}else { %> <input type="checkbox" name="npp"
														value="<%=npp.getString("NppId")%>"> <%} %>
													</TH>
												</TR>
												<% h++; 
                                       }%>

												<TR>
													<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
												</TR>
											</TABLE>
										</div>


										<div id="tabSp" class="tab_content" <%=style%>>
											<TABLE width="100%" border="0" cellspacing="1px"
												cellpadding="3px">


												<TR class="tbheader">
													<TH width="30%"><%=ChuyenNgu.get("Sản phẩm",nnId) %></TH>
													<TH width="10%"><%=ChuyenNgu.get("Trạng thái",nnId) %></TH>
													<TH width="10%"><%=ChuyenNgu.get("Chọn tất cả",nnId) %><input type="checkbox"
														name="allsanpham" value='1'
														onclick="checkedAll(document.nccForm.sanpham,document.nccForm.allsanpham);">
													</TH>
												</TR>
												<tr>
													<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span
														style="font-weight: bold; font-size: 0.9em"><%=ChuyenNgu.get("Nhãn hàng",nnId) %> </span>&nbsp;&nbsp; <select name="nhanhangId"
														onchange="submitform();">
															<option value="" selected></option>
															<% if(nhanhang!=null)
											while(nhanhang.next())
										   	{ if(nhanhang.getString("pk_seq").equals(obj.getnhanhangId()))
										   	{%>
															<option value="<%=nhanhang.getString("pk_seq")%>"
																selected><%=nhanhang.getString("ten") %></option>
															<%} else { %>
															<option value="<%=nhanhang.getString("pk_seq")%>"><%=nhanhang.getString("ten") %></option>
															<%} }%>
													</select> <span style="font-weight: bold; font-size: 0.9em"><%=ChuyenNgu.get("Chủng loại",nnId) %> </span>&nbsp;&nbsp;<select name="chungloaiId"
														onchange="submitform();">
															<option value="" selected></option>
															<% if(chungloai!=null)
											while(chungloai.next())
										   	{ if(chungloai.getString("pk_seq").equals(obj.getchungloaiId()))
												{%>
															<option value="<%=chungloai.getString("pk_seq")%>"
																selected><%=chungloai.getString("ten") %></option>
															<%} else { %>
															<option value="<%=chungloai.getString("pk_seq")%>"><%=chungloai.getString("ten") %></option>
															<%}} %>
													</select></td>
													<% 
									int k = 0;
									//String lightrow = "tblightrow";
								//	String darkrow = "tbdarkrow";
									if(sanphamchon !=null)
									 while(sanphamchon.next())
									 { if (k % 2 != 0) {%>
												
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="30%"><%= sanphamchon.getString("ma") + " - " + sanphamchon.getString("Ten")%></TH>
													<TH width="10%" align="center"><%= ( sanphamchon.getInt("trangthai") == 1 ? "Hoạt động": "Ngưng hoạt động"  ) %></TH>
													<TH width="10%"><input type="checkbox" name="sanpham"
														value="<%=sanphamchon.getString("pk_seq")%>" checked>
													</TH>
												</TR>
												<% k++; 
                                       }
                                     if(sanpham !=null)
									 while(sanpham.next())
									 {
										 if (k % 2 != 0) {%>
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="30%"><%=sanpham.getString("ma") + " - " + sanpham.getString("Ten")  %></TH>
													<TH width="10%" align="center"><%=sanpham.getInt("trangthai") == 1 ? "Hoạt động": "Không hoạt động" %></TH>
													<TH width="10%"><input type="checkbox" name="sanpham"
														value="<%=sanpham.getString("pk_seq")%>"></TH>
												</TR>
												<% k++; 
                                       }%>

												<TR>
													<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
												</TR>
											</TABLE>
										</div>
										<!-- ##################### TAB KHO ##################### -->
										<div id="tabKho" class="tab_content" <%=style%>>
											<TABLE width="100%" border="0" cellspacing="1px"
												cellpadding="3px">
												<TR class="tbheader">
													<!-- <TH width="20%">Mã kho</TH> -->
													<TH width="20%"><%=ChuyenNgu.get("Tên kho",nnId) %></TH>
													<TH width="20%"><%=ChuyenNgu.get("Diễn giải",nnId) %></TH>
													<TH width="10%"><%=ChuyenNgu.get("Chọn tất cả",nnId) %><input type="checkbox"
														name="allkho" value='1'
														onclick="checkedAll(document.nccForm.kho,document.nccForm.allkho);">
													</TH>							
												</TR>
										<%		 k = 0;
									//String lightrow = "tblightrow";
								//	String darkrow = "tbdarkrow";
									if(khochon !=null)
									 while(khochon.next())
									 { if (k % 2 != 0) {%>
												
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="20%"><%=khochon.getString("Ten")%></TH>
													<TH width="20%"><%=khochon.getString("diengiai")%></TH>
													<TH width="10%"><input type="checkbox" name="kho"
														value="<%=khochon.getString("pk_seq")%>" checked>
													</TH>
												</TR>
												<% k++; 
			                                       }
			                                     if(kho !=null)
												 while(kho.next())
												 {
													 if (k % 2 != 0) {%>
															<TR class=<%=lightrow%> align="left">
																<%} else { %>
															
															<TR class=<%= darkrow%> align="left">
																<%} %>
																<TH width="20%"><%=kho.getString("Ten")%></TH>
																<TH width="20%"><%=kho.getString("diengiai")%></TH>
																<TH width="10%"><input type="checkbox" name="kho"
																	value="<%=kho.getString("pk_seq")%>"></TH>
															</TR>
															<% k++; 
			                                       }%>
			
															<TR>
																<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
															</TR>
												
											</TABLE>
										</div>
										
										
										
										<!-- ##################### TAB Nhom SKUS ##################### -->
										<div id="tabNhomskus" class="tab_content" <%=style%>>
											<TABLE width="100%" border="0" cellspacing="1px"
												cellpadding="3px">
												<TR class="tbheader">
													<!-- <TH width="20%">Mã kho</TH> -->
													<TH width="20%"><%=ChuyenNgu.get("Mã nhóm",nnId) %></TH>
													<TH width="20%"><%=ChuyenNgu.get("Tên nhóm",nnId) %></TH>
													<TH width="10%"><%=ChuyenNgu.get("Chọn tất cả",nnId) %><input type="checkbox"
														name="allnhomskus" value='1'
														onclick="checkedAll(document.nccForm.nhomskus,document.nccForm.allnhomskus);">
													</TH>							
												</TR>
										<%		 k = 0;
									
										if(nhomskuschon !=null)
											while(nhomskuschon.next())
									 		{ if (k % 2 != 0) {%>
												
												<TR class=<%=lightrow%> align="left">
													<%} else { %>
												
												<TR class=<%= darkrow%> align="left">
													<%} %>
													<TH width="20%"><%=nhomskuschon.getString("ma")%></TH>
													<TH width="20%"><%=nhomskuschon.getString("ten")%></TH>
													<TH width="10%"><input type="checkbox" name="nhomskus"
														value="<%=nhomskuschon.getString("pk_seq")%>" checked>
													</TH>
												</TR>
												<% k++; 
			                                 }
			                             if(nhomskus !=null)
											 while(nhomskus.next())
											 {
												if (k % 2 != 0) {%>
													<TR class=<%=lightrow%> align="left">
												<%} else { %>
															
													<TR class=<%= darkrow%> align="left">
												<%} %>
														<TH width="20%"><%=nhomskus.getString("ma")%></TH>
														<TH width="20%"><%=nhomskus.getString("ten")%></TH>
														<TH width="10%"><input type="checkbox" name="nhomskus"
																	value="<%=nhomskus.getString("pk_seq")%>"></TH>
													</TR>
												<% k++; 
			                                 }%>
			
													<TR>
														<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
													</TR>
												
											</TABLE>
										</div>
										
										

										<%} %>


									</div>
								</td>
							</tr>
						</table>
					</div>
				</fieldset>
			</div>
		</div>
	</form>




</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%
if(qhRs!=null)qhRs.close();
if(ttRs!=null) ttRs.close();
if(kho!=null) kho.close();
if(khochon!=null) khochon.close();
obj.DBClose();
obj=null;
	}
%>