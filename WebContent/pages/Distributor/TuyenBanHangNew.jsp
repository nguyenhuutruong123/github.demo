<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.tuyenbanhang.ITuyenbanhang" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% ITuyenbanhang tbhBean = (ITuyenbanhang)session.getAttribute("tbhBean"); %>
<% ResultSet ddkd = (ResultSet)tbhBean.getDaidienkd();  
String view = tbhBean.getView();
%>

<% ResultSet kh_tbh_dpt = (ResultSet)tbhBean.getKh_Tbh_DptList(); %>
<% ResultSet kh_tbh_cdpt = (ResultSet)tbhBean.getKh_Tbh_CdptList();  %>

<% Hashtable<Integer, String> kh_tbh_dptIds = (Hashtable<Integer, String>)tbhBean.getKh_Tbh_DptIds(); %>
<% Hashtable<Integer, String> kh_tbh_cdptIds = (Hashtable<Integer, String>)tbhBean.getKh_Tbh_CdptIds(); %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

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
<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout()
 {
    if(confirm("Bạn có muốn đăng xuất?"))
    {
		top.location.href = "home.jsp";
    }
    return
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
			alert("Vui lòng chọn Nhân viên bán hàng của Kế hoạch bán hàng này...");
			return;
		}
		if(tbhTen.value == "")
		{
			alert("Vui lòng nhập diễn giải cho Kế hoạch bán hàng này ...");
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
	var sott = document.getElementsByName("thutu");
	var i,j;
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
	if(ddkdTen.value == "")
	{
		alert("Vui lòng chọn Nhân viên bán hàng của Kế hoạch bán hàng này...");
		return;
	}
	if(tbhTen.value == "")
	{
		alert("Vui lòng nhập diễn giải cho Kế hoạch bán hàng này ...");
		return;
	}
	if(nlv.value == "")
	{
		alert("Vui lòng chọn ngày làm việc của tuyến...");
		return;
	}
	
	for(i=0; i < tbh_khdpt.length - 1; i++)
	{
		for(j=i+1;j < tbh_khdpt.length; j++)
		{				
			if(sott.item(j).value == sott.item(i).value)
				{alert("Thứ tự đã bị trùng, vui lòng nhập lại");
				return;}
		}
	} 	
	
	
	document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

	
 	document.forms['tbhForm'].action.value='save';
    document.forms['tbhForm'].submit();
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
		option.value = 'F2-1';
		option.appendChild(document.createTextNode('F2-1'));
		selectBox.appendChild(option);
		
		option = document.createElement('option');
		option.value = 'F2-2';
		option.appendChild(document.createTextNode('F2-2'));
		selectBox.appendChild(option);

	/* 	option = document.createElement('option');
		option.value = 'F6';
		option.appendChild(document.createTextNode('F6'));
		selectBox.appendChild(option); */
		
		option = document.createElement('option');
		option.value = 'F4';
		option.appendChild(document.createTextNode('F4'));
		selectBox.appendChild(option);
		
		
		
		option = document.createElement('option');
		option.value = 'F8';
		option.appendChild(document.createTextNode('F8'));
		selectBox.appendChild(option);

		
		option = document.createElement('option');
		option.value = 'F24';
		option.appendChild(document.createTextNode('F24'));
		selectBox.appendChild(option);
		
		
		
		option = document.createElement('option');
		option.value = 'F1';
		option.appendChild(document.createTextNode('F1'));
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
	
	function ChuyenSangTrai()
	{	
		var tbh_khdpt = document.getElementsByName("kh_tbh_dptList");
		var tbh_khcdpt = document.getElementsByName("kh_tbh_cdptList");
		//alert(tbh_khdpt.length);
		//var tbh_khName = document.getElementsByName("tbh_khName");
		var i;
		for(i=0; i < tbh_khdpt.length; i++)
		{
			var str = new String;
			if(tbh_khdpt.item(i).checked)
			{
				str = tbh_khdpt.item(i).value;
				var makh = str.substring(0, str.indexOf(";"));
				//alert("Makh"+makh);
				str = str.substr(str.indexOf(";") + 1);
				var smartid = str.substring(0,str.indexOf(";"));
				
				str = str.substr(str.indexOf(";") + 1);
				var tenkh = str.substring(0, str.indexOf(";"));
				
				str = str.substr(str.indexOf(";") + 1);
				var diachi = str.substring(0, str.length);		
				addRow2('kh_tbh_cdpt', tbh_khcdpt.length, makh, smartid, tenkh, diachi);
				//alert(diachi);
				//var id="dpt" + i;
				//document.getElementById(id).style.display = "none";
				//tbh_khdpt.item(i).checked = false;		
				document.getElementById("tb_kh_tbh_dpt").deleteRow(tbh_khdpt.item(i).parentNode.parentNode.rowIndex);
				//removeRow('khdpt', id);
			}
		}
		//setTimeout(ChuyenSangTrai(), 10);
	}
	
	function AutoChuyenTrai()
	{
		var tbh_khdpt = document.getElementsByName("kh_tbh_dptList");
		var tbh_khcdpt = document.getElementsByName("kh_tbh_cdptList");
		
		var i;
		for(i=0; i < tbh_khdpt.length; i++)
		{
			var str = new String;
			if(tbh_khdpt.item(i).checked)
			{
				ChuyenSangTrai();
			}
		}
		for(j=0; j < 20; j++)
			ChuyenSangTrai();
	}
	
	function ChuyenSangPhai()
	{	
		var tbh_khcdpt = document.getElementsByName("kh_tbh_cdptList");
		var tbh_khdpt = document.getElementsByName("kh_tbh_dptList");
		//alert(tbh_khcdpt.length);
		//var tbh_khName = document.getElementsByName("tbh_khName");
		var i;
		for(i=0; i < tbh_khcdpt.length; i++)
		{
			var str = new String;
			if(tbh_khcdpt.item(i).checked)
			{
				str = tbh_khcdpt.item(i).value;
				var makh = str.substring(0, str.indexOf(";"));
				
				str = str.substr(str.indexOf(";") + 1);
				var smartid = str.substring(0, str.indexOf(";"));
				
				str = str.substr(str.indexOf(";") + 1);
				var tenkh = str.substring(0, str.indexOf(";"));
				
				str = str.substr(str.indexOf(";") + 1);
				var diachi = str.substring(0, str.length);
				//alert(tbh_khdpt.length);
				addRow('kh_tbh_dpt', tbh_khdpt.length, makh, smartid, tenkh, diachi);
				//alert(i);
				//var id = "cpt" + i;
				//document.getElementById(id).style.display = "none";
				//tbh_khcdpt.item(i).checked = false;			
				document.getElementById("tb_kh_tbh_cdpt").deleteRow(tbh_khcdpt.item(i).parentNode.parentNode.rowIndex);
			}
		}
		//setTimeout(ChuyenSangPhai(), 10);
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
				ChuyenSangPhai();
			}
		}
		for(j=0; j < 20; j++)
			ChuyenSangPhai();
	}
	
	function LocDuLieu()
	{
		document.forms['tbhForm'].action.value='tim';
    	document.forms['tbhForm'].submit();
		
    	/*
    	var key = document.getElementById("khTen").value;
		var key2 = document.getElementById("diachi").value;
		var tbh_khcdpt = document.getElementsByName("kh_tbh_cdptList");
		var i;
		for(i=0; i < tbh_khcdpt.length; i++)
		{
			var str = new String;
			str = tbh_khcdpt.item(i).value;
			var makh = str.substring(0, str.indexOf(";"));
			str = str.substr(str.indexOf(";") + 1);
			var tenkh = str.substring(0, str.indexOf(";"));
			str = str.substr(str.indexOf(";") + 1);
			var diachi = str.substring(0, str.length);
			
			if(key != "" && key2 != "")
			{
				if(tenkh.indexOf(key) != -1 && diachi.indexOf(key2) != 1)
					tbh_khcdpt.item(i).parentNode.parentNode.style.display = "";
				else
					tbh_khcdpt.item(i).parentNode.parentNode.style.display = "none";
			}
			
			if(key2 == "") //chi quan tam den key 1(Ten Khach hang)
			{
				if(tenkh.indexOf(key) != -1)
					tbh_khcdpt.item(i).parentNode.parentNode.style.display = "";
				else
					tbh_khcdpt.item(i).parentNode.parentNode.style.display = "none";
			}
			
			if(key == "") //chi quan tam den key 2(dia Chi)
			{
				if(diachi.indexOf(key2) != -1)
					tbh_khcdpt.item(i).parentNode.parentNode.style.display = "";
				else
					tbh_khcdpt.item(i).parentNode.parentNode.style.display = "none";
			}
			
		}
		*/
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
	
	function changeDDKD()
	{
		document.forms['tbhForm'].action.value='changeDDKD';
    	document.forms['tbhForm'].submit();
	}
	
</script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="tbhForm" method="post" action="../../TuyenbanhangUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="nppId" id="nppId" value='<%= tbhBean.getNppId() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="view" id="view" value='<%= view %>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
	  <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <TR height="22">
						  	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kinh doanh &gt; Kế hoạch bán hàng &gt; Tạo mới
						  	<%  String chaomung = "";
						  		if(view.equals("TT")) { chaomung = userTen; }
						  		else 				  { chaomung = tbhBean.getNppTen(); }
						  	%>
						  	<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=chaomung%></TD>
						 </TR>
					  </table>
					</TD>
			  </TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="2">
				<TR >
					<TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR class = "tbdarkrow">
								<TD width="30" align="left"><A href="javascript:history.back()" >			 		
									<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								<TD width="2" align="left" ></TD>
				    			<TD width="30" align="left" >
				    			
				    			<div id="btnSave">
				    			<A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
				    			</div>
				    			</TD>
								<TD width="2" align="left" ></TD>								
								<TD width="30" align="left" ><A href="javascript:printform()" ><img src="../images/Printer30.png" alt="In chung tu" width="30" height="30" border="1" longdesc="In chung tu" title="In chung tu" style="border-bottom-style:outset"></A></TD>
								<TD align="left" >&nbsp;</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
					
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="0">
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>			
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  cols="139%" rows="1" ><%= tbhBean.getMessage() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>
				<TR>
				  <TD height="100%" width="100%">
				<FIELDSET>
				<LEGEND class="legendtitle">&nbsp;Thông tin Kế hoạch bán hàng &nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
								  <TD class="plainlabel">Nhân viên bán hàng</TD>
								  <TD class="plainlabel">
								    <SELECT name="ddkdTen" id="ddkdTen" onchange="changeDDKD();">
								  	<option value=""></option>
								 	<% 
									  try{ while(ddkd.next()){ 
									    	if(ddkd.getString("ddkdId").equals(tbhBean.getDdkdId())){ %>
									      		<option value='<%=ddkd.getString("ddkdId") %>' selected><%=ddkd.getString("ddkdTen") %></option>
									      	<%}else{ %>
									     		<option value='<%=ddkd.getString("ddkdId") %>'><%=ddkd.getString("ddkdTen") %></option>
									     	<%}}}catch(java.sql.SQLException e){}
									   %>
							    	</SELECT></TD>
							  </TR>
								<TR>
									<TD width="24%" class="plainlabel">Diễn giải Kế hoạch bán hàng </TD>
									<TD width="76%" class="plainlabel"><INPUT name="tbhTen" id="tbhTen"
										type="text" value="<%= tbhBean.getDiengiai() %>" size="60"></TD>
								</TR>
								
								<TR>
								  <TD class="plainlabel">Ngày làm việc </TD>
							      <TD class="plainlabel" valign="middle">
							        <select name="ngaylamviec" id="nlv"><option value=""></option>
							         <%
							         	String[] thu =  new String[]{"Thu hai", "Thu ba", "Thu tu", "Thu nam", "Thu sau", "Thu bay"};
							 			for(int h = 0; h < thu.length; h++)
							 			{
							 				if(tbhBean.getNgaylamviec().equals(thu[h])){ %>
							 					<option value="<%= thu[h] %>" selected><%= thu[h] %></option>
							 			<%}else{%>
							 					<option value="<%= thu[h] %>"><%= thu[h] %></option>
							 		<%}} %>  						          
						             </select>
									 </TD>
							  </TR>
							</TABLE>
	
				    </FIELDSET>
				  </TD>	
				</TR>
			</TABLE>
			<TABLE width = 100% border="0" cellpadding="0" cellspacing ="0">
			<TR>
			 	<TD width = "50%" valign="top">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%">
								<FIELDSET>
								<LEGEND class="legendtitle">Khách hàng được phân vào tuyến này	</LEGEND>
								  	<TABLE  width="100%" align="left" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%">

							<TABLE  class ="tabledetail"  width="100%" border="0" cellspacing="1" cellpadding="4" id="tb_kh_tbh_dpt">
							<tbody id="kh_tbh_dpt">
								<TR class="tbheader">
								  	<TH width="5%">Thứ tự </TH>
								  	<TH width="12%">Mã KH </TH>
								  	<!-- <TH width="12%">Smartid </TH> -->
									<TH width="30%">Tên KH</TH>
									<TH width="40%">Địa chỉ </TH>
									<TH width="7%">Tần số </TH>
									<TH width="6%">Chọn </TH>
								</TR>
								<%
								int i = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								if (kh_tbh_dpt != null){
								try{while(kh_tbh_dpt.next()){ 
									if (i % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
								<%}%>
								<TD align="center">
							    	<input name='thutu' type="text" value='<%= i %>' size="5"></TD>

								<%-- <TD align="left"> <input type="hidden" value = '<%= kh_tbh_dpt.getString("khId") %>'> </TD> --%>
								<%-- <TD align="left" > <%= kh_tbh_dpt.getString("khId") %> </TD> --%>
								<%-- <TD align="left" class="textfont"><input type="hidden" value='<%= kh_tbh_cdpt.getString("khId")%>'> </TD> --%>								
								<TD align="left" > <%= kh_tbh_dpt.getString("smartid") %> </TD>
								<TD align="left" > <%= kh_tbh_dpt.getString("ten") %> </TD>
								<TD align="center"><p align="left"> <%= kh_tbh_dpt.getString("diachi") %> </TD>
								<TD align="center">
								
								
                                  
                                     <% 
									String[]	tt = new String[]
														{"F2-1" ,"F2-2", "F4", "F8","F24","F1" };
											String[]			idTrangThai = new String[]
														{ "F2-1" ,"F2-2", "F4", "F8","F24","F1" };
												%>
										<select name="tansoList">
												 <% for( int ii=0;ii<tt.length;ii++) { 
								    			if(idTrangThai[ii].equals(kh_tbh_dpt.getString("tanso")  )   ){ %>
								      				<option value='<%=idTrangThai[ii]%>' selected><%=tt[ii] %></option>
								      		 	<%}else{ %>
								     				<option value='<%=idTrangThai[ii]%>'><%=tt[ii] %></option>
								     			<%} 
								      		 }
								       	%>
											
									          </select>
                                    
                                  </TD>
                                   <TD align="left" class="textfont"><input type="hidden" value='<%= kh_tbh_cdpt.getString("khId")%>'> </TD>
                                   <%if (kh_tbh_dptIds.contains(kh_tbh_dpt.getString("khId"))){ %>
										   <TD align="center"><input name="kh_tbh_dptList" type="checkbox" value ="<%=kh_tbh_dpt.getString("khId")+";"+kh_tbh_dpt.getString("smartid")+";"+kh_tbh_dpt.getString("ten")+";"+kh_tbh_dpt.getString("diachi") %>" checked></TD>
									<%}else{%>
										   <TD align="center"><input name="kh_tbh_dptList" type="checkbox" value ="<%=kh_tbh_dpt.getString("khId")+";"+kh_tbh_dpt.getString("smartid")+";"+kh_tbh_dpt.getString("ten")+";"+kh_tbh_dpt.getString("diachi") %>"></TD>
									<%}%></TR>
						     	<% i++;}}catch(java.sql.SQLException e){} 
						     	}%>
							</tbody></TABLE>
							</TD>
						</TR>
			</TABLE>
				</FIELDSET>	</TD>
						</TR>
				    </TABLE>
				</TD>
				<TD width ="1%" valign="top"> 
					<TABLE border = "0" cellpadding="0" cellspacing="0">
						<TR>
							<TD>&nbsp;
								
						  </TD>
						</TR>					
						<TR>
							<TD>
								<img src="../images/Back30.png" border="0" style="cursor: pointer;" onClick="AutoChuyenPhai()">
							</TD>
						</TR>
						<TR>
							<TD>&nbsp;
								
						  </TD>
						</TR>
						<TR>
						<TD>&nbsp;				
						  </TD>
						</TR>

						<TR>
							<TD>
								<img src="../images/Next30.png" border="0" style="cursor: pointer;" onClick="AutoChuyenTrai()">
							</TD>
						</TR>
					</TABLE>
			  </TD>			
				<TD width = "40%" valign="top">
					<TABLE width="100%" border="0" cellpadding="0">
						<TR>
							<TD >
								<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>
								<TABLE width="100%" cellspacing="0" cellpadding = "6">
									<TR>
										<TD width="30%" class="plainlabel">Mã/Tên KH </TD>
										<TD width="70%" valign="middle" class="plainlabel">
											<TABLE>
												<TR>
													<TD>
														<input name="khTen" id="khTen" type="text" value="<%= tbhBean.getTenkhachhang() %>" onchange="ajaxOption('bangcpt')" size="25" >
													</TD>
													<TD>
														<img src="../images/Search20.png" width="20" height="20" onClick="ajaxOption('bangcpt')" style="cursor: pointer;" >							  
													</TD>
												</TR>
											</TABLE>	  
									  </TD>
								
									</TR>
									<TR>
										<TD class="plainlabel">Địa chỉ </TD>
										<TD class="plainlabel">
											<TABLE>
												<TR>
													<TD>
														<input name="diachi" id="diachi" type="text" value="<%= tbhBean.getDiachi() %>" onchange="ajaxOption('bangcpt')" size="25" >
													</TD>
													<TD>
														<img src="../images/Search20.png" width="20" height="20" onClick="ajaxOption('bangcpt')" style="cursor: pointer;" >							  
													</TD>
												</TR>
											</TABLE>	  
								
										</TD>
									</TR>
								</TABLE>
								</FIELDSET>
							</TD>	
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD colspan = "6" width="100%" >
								<FIELDSET>
								<LEGEND class="legendtitle">Khách hàng chưa được phân vào tuyến này</LEGEND>
							<div id = "bangcpt">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="6" id="tb_kh_tbh_cdpt">
							<tbody id="kh_tbh_cdpt">
								<TR class="tbheader">
								<TH width="5%">Mã KH</TH>
								<!-- <TH width="5%">Smart ID</TH> -->
								  <TH width="45%">Tên KH</TH>
									<TH width="50%">Địa chỉ </TH>
									<TH width="5%">Chọn
									<input type="checkbox" name="chontatca" value='1'
									onclick="checkedAll(document.tbhForm.kh_tbh_cdptList,document.tbhForm.chontatca);">
									</TH>
								</TR>
							<% 
							int j = 0; 
							if(kh_tbh_cdpt != null){
								try{while(kh_tbh_cdpt.next()){ 
									if (j % 2 != 0) { %>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
								<%}%>
								<%-- <TD align="left" class="textfont"><%= kh_tbh_cdpt.getString("khId") %> </TD> --%>
								
									<%-- <TD align="left" class="textfont"><input type="hidden" value='<%= kh_tbh_cdpt.getString("khId")%>'> </TD> --%>
									<TD align="left" class="textfont"><%= kh_tbh_cdpt.getString("smartid") %> </TD>
									<TD align="left" class="textfont"><%= kh_tbh_cdpt.getString("ten") %> </TD>
									<TD align="center"><div align="left"><%= kh_tbh_cdpt.getString("diachi") %> </div></TD>
									<%-- <TD><input type="hidden" value='<%= kh_tbh_cdpt.getString("khId")%>'> </TD> --%>
                                    <%if (kh_tbh_cdptIds.contains(kh_tbh_cdpt.getString("khId"))){ %>
										   <TD align="center"><input name="kh_tbh_cdptList" type="checkbox" value ="<%=kh_tbh_cdpt.getString("khId")+";"+kh_tbh_cdpt.getString("smartid")+";"+kh_tbh_cdpt.getString("ten")+";"+kh_tbh_cdpt.getString("diachi") %>" checked></TD>
									<%}else{%>
										   <TD align="center"><input name="kh_tbh_cdptList" type="checkbox" value ="<%=kh_tbh_cdpt.getString("khId")+";"+kh_tbh_cdpt.getString("smartid")+";"+kh_tbh_cdpt.getString("ten")+";"+kh_tbh_cdpt.getString("diachi") %>"></TD>
									<%}%>
									</TR>
							     	<%j++;}}catch(java.sql.SQLException e){} 
							     	
							     }%>					
							</tbody></TABLE>
							</div>
							</FIELDSET>	</TD>
						</TR>
				    </TABLE>
				</TD>
				
			</TR>
		</TABLE>
		

	    <!--end body Dossier--></TD>
	</TR>
	
</TABLE>
</form>
</BODY>


<%
if(ddkd!=null){
	ddkd.close();
}
if(kh_tbh_dpt!=null){
	kh_tbh_dpt.close();
}
if(kh_tbh_cdpt!=null){
	kh_tbh_cdpt.close();
}
if(kh_tbh_dptIds!=null){
	kh_tbh_dptIds.clear();
}
if(kh_tbh_cdptIds!=null){
	kh_tbh_cdptIds.clear();
}
if(tbhBean!=null){
	tbhBean.DBclose();
	tbhBean=null;
}



session.setAttribute("tbhBean",null);
}%>
</HTML>
