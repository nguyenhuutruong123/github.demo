<%-- <%@page import="com.sun.org.apache.regexp.internal.REUtil"%> --%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.sql.ResultSet"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	    
        String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		String sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		IStockintransit obj = (IStockintransit)session.getAttribute("obj");
		ResultSet npp = obj.getnpp();
		ResultSet sku = obj.getsanpham();
		ResultSet kenh = obj.getkenh();
		ResultSet vung = obj.getvung();
		ResultSet khuvuc = obj.getkhuvuc();
		ResultSet nhanhang=obj.getnhanhang();
		ResultSet chungloai=obj.getchungloai();
		ResultSet dvkd=obj.getdvkd();
		ResultSet nhomsp=obj.GetRsNhomSP();
		ResultSet nhomskus=obj.getNhomskus();
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%
	int[] quyen = new  int[6];
	quyen = util.Getquyen("DistributionTT_Svl","", userId);
	
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = "";

if(obj.getView().trim().length() > 0)
	url = util.getChuyenNguUrl("DistributionTT_Svl","&view="+obj.getView(),nnId);	
else
	url = util.getChuyenNguUrl("DistributionTT_Svl","",nnId);
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
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

  <style type="text/css">
   #ulsp { height: 100px; overflow: auto; width: 100px; border: 1px solid #000; }
   #ulsp { list-style-type: none; margin: 0; padding: 0; overflow-x: hidden; width: 500px }
   li { margin: 0; padding: 0; }
   label { display: block; color: WindowText; background-color: Window; margin: 0; padding: 0; width: 100%; }
   label:hover { background-color: Highlight; color: HighlightText; }
  </style>

	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<SCRIPT language="javascript" type="text/javascript">

	function seach() {
		document.forms['rpForm'].action.value = 'seach';
		document.forms["rpForm"].submit();
	}
	
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}

	function submitform()
	{
		if (document.forms["rpForm"]["tungay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return;
		}
		if (document.forms["rpForm"]["denngay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return;
		}
		
		document.forms['rpForm'].action.value = 'create';
		document.forms['rpForm'].dataerror.value="";
		document.forms['rpForm'].submit();
		
	}
	
	function submitformdp()
	{
		if (document.forms["rpForm"]["tungay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return;
		}
		if (document.forms["rpForm"]["denngay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return;
		}
		
		document.forms['rpForm'].action.value = 'createdp';
		document.forms['rpForm'].dataerror.value="";
		document.forms['rpForm'].submit();
		
	}
	
	function checkall(value){
		var checkone=document.getElementsByName("checkkhuvuc1");
		var giatricheck=document.getElementsByName("checkkhuvuc");
		var chuoi;
		if(value==true){
			chuoi="1";
		}else{
			chuoi="0";
		}
		for(var i=0;i<checkone.length;i++ ){
			checkone.item(i).checked=value;
			giatricheck.item(i).value=chuoi;
		}
	}
	function recheck(){
		var checkone=document.getElementsByName("check");
		var giatricheck=document.getElementsByName("valuechecked");
		for(var i=0;i<checkone.length;i++ ){
			if(checkone.item(i).checked==true){
				giatricheck.item(i).value="1";
			}else {
				giatricheck.item(i).value="0";
			}
			
				
		}
	}
</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2(); 
    	});
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

<form name="rpForm" method="post" action="../../DistributionTT_Svl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="view" value= <%= obj.getView() %> >
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation"><%=" "+url %> </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng Bạn",nnId) %> <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					
 					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle"><%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %> </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" id="errors" style="width: 100%" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>	
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle"><%=ChuyenNgu.get("Thời gian xuất báo cáo",nnId) %></LEGEND>
							<TABLE  width="100%" cellpadding="5"  cellspacing="0">
								<TR>
								  	<TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
								  	<TD class="plainlabel" >
										<input type="text" class="days" name="tungay" size="20" value = "<%=obj.gettungay()%>" >																		
									</TD>																		
								
									<TD class="plainlabel" ><%=ChuyenNgu.get("Đến ngày",nnId) %> </TD>
								  	<TD class="plainlabel" >
							  			<input type="text" name="denngay" class="days" size="20" value = "<%=obj.getdenngay()%>" >						
									</TD>
								</tr>
								<tr>
									<TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId) %></TD>
									<TD class="plainlabel" >
										<select name="kenhId" id="kenhId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (kenh != null)
													while (kenh.next()) {
														if (kenh.getString("pk_seq").equals(obj.getkenhId())) {%>
														<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("diengiai")%></option>
											<%} else { %>
												<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("diengiai")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %></TD>
									<TD class="plainlabel">
										<select name="dvkdId" id="dvkdId"	onchange="seach();">
											<option value="" selected>All</option>
											<%if (dvkd != null)
													while (dvkd.next()) {
														if (dvkd.getString("pk_seq").equals(obj.getdvkdId())) {%>
														<option value="<%=dvkd.getString("pk_seq")%>" selected><%=dvkd.getString("diengiai")%></option>
													<%} else {%>
														<option value="<%=dvkd.getString("pk_seq")%>"><%=dvkd.getString("diengiai")%></option>
													<%}}%>
										</select>
									</TD>
								</tr>
								<%if(obj.getView().equals("TT")){ %>
								<tr>
									<TD class="plainlabel"><%=ChuyenNgu.get("Vùng / Miền",nnId) %></TD>
									<TD class="plainlabel" >
										<select name="vungId" id="vungId" onchange="seach();">
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
									<TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %></TD>
									<TD class="plainlabel">
										<select name="khuvucId" id="khuvucId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
															<option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
													<%} else {%>
														<option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
									
									
								</tr>
								<%} %>
								<tr>
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %></TD>
									<TD class="plainlabel" >
										<select name="nhomspid" onchange="seach();"  id="nhomspid" >
											<option value="" selected><%=ChuyenNgu.get("Tất cả",nnId) %></option>
											<%if (chungloai != null)
													while (nhomsp.next()) {
														if (nhomsp.getString("pk_seq").equals(obj.GetNhoSPId())) {%>
														<option value="<%=nhomsp.getString("pk_seq")%>" selected><%=nhomsp.getString("diengiai")%></option>
													<%} else {%>
														<option value="<%=nhomsp.getString("pk_seq")%>"><%=nhomsp.getString("diengiai")%></option>
												<% }}%>
										</select>
									</TD>	
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhãn hàng",nnId) %></TD>
									<TD class="plainlabel">
										<select name="nhanhangId" id="nhanhangId" onchange="seach();">
												<option value="" selected>All</option>
												<%if (nhanhang != null)
														while (nhanhang.next()) {
															if (nhanhang.getString("pk_seq")
																	.equals(obj.getnhanhangId())) {	%>
															<option value="<%=nhanhang.getString("pk_seq")%>" selected><%=nhanhang.getString("ten")%></option>
														<%} else {%>
															<option value="<%=nhanhang.getString("pk_seq")%>"><%=nhanhang.getString("ten")%></option>
												<%}}%>
										</select>
									</TD>
								</tr>			
												
								<TR>
								    <TD class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %> </TD>
								      <TD class="plainlabel">
								      	<SELECT name="nppId">	
								      	<option value="">All</option>							      
								  	 	<%if(npp != null) try{ while(npp.next()){ 
								    	if(npp.getString("pk_seq").trim().equals(obj.getnppId())){ %>
								      		<option value='<%=npp.getString("pk_seq") %>' selected><%=npp.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=npp.getString("pk_seq") %>'><%=npp.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
                                  </select>
							  		</TD>
							  		<TD class="plainlabel"><%=ChuyenNgu.get("Chủng loại",nnId) %></TD>
									<TD class="plainlabel">
										<select name="chungloaiId" id="chungloaiId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (chungloai != null)
													while (chungloai.next()) {
														if (chungloai.getString("pk_seq").equals(obj.getchungloaiId())) {%>
														<option value="<%=chungloai.getString("pk_seq")%>" selected><%=chungloai.getString("ten")%></option>
													<%} else {%>
														<option value="<%=chungloai.getString("pk_seq")%>"><%=chungloai.getString("ten")%></option>
												<% }}%>
										</select>
									</TD>
								</TR>
								
								<TR>
								    <TD class="plainlabel"><%=ChuyenNgu.get("Nhóm SKU's",nnId) %> </TD>
								      <TD class="plainlabel">
								      	<SELECT name="nhomskusId">	
								      	<option value="">All</option>							      
								  	 	<%if(nhomskus != null) try{ while(nhomskus.next()){ 
								    	if(nhomskus.getString("pk_seq").trim().equals(obj.getNhomskusId())){ %>
								      		<option value='<%=nhomskus.getString("pk_seq") %>' selected><%=nhomskus.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=nhomskus.getString("pk_seq") %>'><%=nhomskus.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>
                                  		</select>
							  		</TD>
							  		<td class="plainlabel"></td>
							  		<td class="plainlabel"></td>
								</TR>
									
								<TR style="display: none;">
										<TD class="plainlabel"></TD>
										<TD class="plainlabel" colspan="3">
										<fieldset>
										<legend><%=ChuyenNgu.get("Lấy theo",nnId) %></legend> 
										 <% if(obj.getchon().equals("0")){ %>
											<input type="radio" name="chonid" value="0"  checked="checked" onchange="seach();"/><%=ChuyenNgu.get("Sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="1" onchange="seach();" /><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;	
											<input type="radio" name="chonid" value="2" onchange="seach();" /><%=ChuyenNgu.get("Nhãn Hàng",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="3" onchange="seach();" /><%=ChuyenNgu.get("Chủng loại",nnId) %> &nbsp;&nbsp;&nbsp;	
											<input type="radio" name="chonid" value="4" onchange="seach();" /><%=ChuyenNgu.get("Nhóm SKU's",nnId) %> &nbsp;&nbsp;&nbsp;										
										<%}else if(obj.getchon().equals("1")){ %>
											<input type="radio" name="chonid" value="0" onchange="seach();"/><%=ChuyenNgu.get("Sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="1"  checked="checked"  onchange="seach();"/><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="2" onchange="seach();"/><%=ChuyenNgu.get("Nhãn Hàng",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="3" onchange="seach();"/><%=ChuyenNgu.get("Chủng loại",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="4" onchange="seach();" /><%=ChuyenNgu.get("Nhóm SKU's",nnId) %> &nbsp;&nbsp;&nbsp;	
										<%}else if(obj.getchon().equals("2")){ %>
											<input type="radio" name="chonid" value="0" onchange="seach();"/><%=ChuyenNgu.get("Sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="1" onchange="seach();"/><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="2"  checked="checked" onchange="seach();"/><%=ChuyenNgu.get("Nhãn Hàng",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="3" onchange="seach();"/><%=ChuyenNgu.get("Chủng loại",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="4" onchange="seach();" /><%=ChuyenNgu.get("Nhóm SKU's",nnId) %> &nbsp;&nbsp;&nbsp;	
										<%}else if(obj.getchon().equals("3")){ %>
											<input type="radio" name="chonid" value="0" onchange="seach();"/><%=ChuyenNgu.get("Sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="1" onchange="seach();"/><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="2" onchange="seach();"/><%=ChuyenNgu.get("Nhãn Hàng",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="3"  checked="checked" onchange="seach();"/><%=ChuyenNgu.get("Chủng loại",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="4" onchange="seach();" /><%=ChuyenNgu.get("Nhóm SKU's",nnId) %> &nbsp;&nbsp;&nbsp;	
										<%}else if(obj.getchon().equals("4")){ %>
											<input type="radio" name="chonid" value="0" onchange="seach();"/><%=ChuyenNgu.get("Sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="1" onchange="seach();"/><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="2" onchange="seach();"/><%=ChuyenNgu.get("Nhãn Hàng",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="3" onchange="seach();"/><%=ChuyenNgu.get("Chủng loại",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="4"  checked="checked" onchange="seach();" /><%=ChuyenNgu.get("Nhóm SKU's",nnId) %> &nbsp;&nbsp;&nbsp;	
										<%}%>	
										</fieldset>
										</TD>
									</TR>
												
									
						
									<TD class="plainlabel"></TD>
									<TD class="plainlabel" colspan="3">
		
									<!-- <legend>Xem theo</legend>  -->
									<% if(obj.gettype().equals("0")){ %>
										<input type="hidden" name="typeid" onchange="Laytheokh();" value="2"  /> &nbsp;&nbsp;&nbsp;
										<input type="hidden" name="typeid" onchange="LayThethoigian();" value="1" />
										<input type="hidden" name="typeid"  value="0" checked="checked"  />
									<%} if(obj.gettype().equals("1")){ %>
										<input type="hidden" name="typeid" onchange="Laytheokh();" value="2" checked="checked" />&nbsp;&nbsp;&nbsp;
										<input type="hidden" name="typeid" onchange="LayThethoigian();" value="1" />
										<input type="hidden" name="typeid"  value="0" />
									<%}else{ %>										
										<input type="hidden" name="typeid"  onchange="Laytheokh();" value="2" /><!-- Lấy theo khách hàng  -->&nbsp;&nbsp;&nbsp;
										<input type="hidden" name="typeid" onchange="LayThethoigian();" value="1" checked="checked"/><!-- Theo thời gian -->
										<input type="hidden" name="typeid"  value="0" /><!-- Theo bất kỳ sản phẩm -->
									<%} %>
									</TD>
								
								 <TR>
									<TD colspan="5" class="plainlabel">
									<a class="button2" href="javascript:submitform()" >
										<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Tạo báo cáo",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;                                    </TD>
								
									<%-- <TD colspan="3" class="plainlabel">
									<a class="button2" href="javascript:submitformdp()" >
										<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("BC độ phủ",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;                                    </TD> --%>
								</TR>
								<TR>
									<TD width="19%" class="plainlabel" valign="top"><%=ChuyenNgu.get("Sản Phẩm",nnId) %></TD>
								  	<TD class="plainlabel" colspan="4" >
										<TABLE cellpadding="0" cellspacing="0">
											<TR>
											<TD>
											
											 <ul id="ulsp" style="height: 500px; font-size : 9pt;">
											 	
											 <%
											 String wheresku = (String)session.getAttribute("checkedSKU");
											 String[] checkedSKU=wheresku.split(",");
				                           	 if(checkedSKU==null){
											 while(sku.next())

				                               {%>
			                            	  	<li><label  for="check" > <input type ="hidden" value="" name="valuechecked"><input type="checkbox" name="check"  onchange="recheck();" value ="<%=sku.getString("pk_seq") %>"> <%=sku.getString("ma")+ " -" + sku.getString("ten")%> </label>  <input name ="skutest"  type="hidden" value="<%=sku.getString("pk_seq") %>"></li>
				                              <%}
				                           	 }else{
				                            	while (sku.next()){
				                            		int i=0;
				                            		for (i=0;i<checkedSKU.length;i++){
				                            			if(checkedSKU[i].trim().equals(sku.getString("pk_seq").trim())){
				                            				break;
				                            			}
				                            		}
				                            		if(obj.getchon().equals("1"))
				                            		{
				                            			if(i< checkedSKU.length){
				                            			 	{%>
						                            	  		<li><label  for="check" > <input type ="hidden" value="" name="valuechecked"> <%=sku.getString("ten")%> </label>  <input name ="skutest"  type="hidden" value="<%=sku.getString("pk_seq") %>"></li>
							                              	<%}
				                            			}else{
				                            				 {%>
						                            	  		<li><label  for="check" > <input type ="hidden" value="" name="valuechecked"> <%=sku.getString("ma")+ " -" + sku.getString("ten")%> </label>  <input name ="skutest"  type="hidden" value="<%=sku.getString("pk_seq") %>"></li>
							                              	<%}
				                            			}
				                            		}
				                            		else
				                            		{
				                            			if(i< checkedSKU.length){
				                            			 	{%>
						                            	  		<li><label  for="check" > <input type ="hidden" value="" name="valuechecked"><input type="checkbox" checked="checked" name="check"  onchange="recheck();" value ="<%=sku.getString("ma") %>"> <%=sku.getString("pk_seq")+ " -" + sku.getString("ten")%> </label>  <input name ="skutest"  type="hidden" value="<%=sku.getString("pk_seq") %>"></li>
							                              	<%}
				                            			}else{
				                            				 {%>
						                            	  		<li><label  for="check" > <input type ="hidden" value="" name="valuechecked"><input type="checkbox" name="check"  onchange="recheck();" value ="<%=sku.getString("pk_seq") %>"> <%=sku.getString("ma")+ " -" + sku.getString("ten")%> </label>  <input name ="skutest"  type="hidden" value="<%=sku.getString("pk_seq") %>"></li>
							                              	<%}
				                            			}
				                            		}
				                            	}
				                            }
											 %>  

											  </ul>
                              	   										</TD>
                                    		</TR>
								</TABLE>									
								</TD>							
								</TR>
							   
							</TABLE>
							</FIELDSET>						</TD>
						</TR>
					</TABLE>					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">
		$("select:not(.notuseselect2)").css({
			"width": "200px", 
			//"height": "200px"
		});
	</script>

</body> 
</HTML>
<%
	if(vung!=null){ vung.close(); vung = null ;	}	
	if(khuvuc!=null){ khuvuc.close(); khuvuc = null ;	}		
	if(kenh!=null){ kenh.close(); kenh = null ;	}		
	if(npp!=null){ npp.close(); npp = null ;	}		
	if(sku!=null){ sku.close(); sku = null ;	}		 
	if(nhanhang!=null){ nhanhang.close(); nhanhang = null ;	}		
	if(chungloai!=null){ chungloai.close();	 chungloai = null ;	}
	if(dvkd!=null){ dvkd.close();	 dvkd = null ;	} 
	if(nhomsp!=null){ nhomsp.close(); nhomsp = null ;	}
	
	if(obj!=null){ 
		obj.DBclose();
		obj = null;
	}
	session.setAttribute("obj", null);
		               	  			


	}%>