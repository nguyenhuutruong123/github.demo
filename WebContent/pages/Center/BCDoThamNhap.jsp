<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String groupCustomer = (String)session.getAttribute("groupCustomer");
	String gorupSKU = (String)session.getAttribute("gorupSKU");
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet DlnRs = obj.GetDlnRs();
	 ResultSet vung = (ResultSet) obj.getvung();
	ResultSet khuvuc = (ResultSet)obj.getkhuvuc();
	ResultSet npp = (ResultSet)obj.getnpp();
	
	
	ResultSet nhanhang=obj.getnhanhang();
	ResultSet chungloai=obj.getchungloai();
	ResultSet nhomsp=obj.GetRsNhomSP();
	ResultSet nhomskus=obj.getNhomskus();
	Utility util = (Utility) session.getAttribute("util");
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("BCDoThamNhap","",nnId);	
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>OPV - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

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
		$(".button").hover(function() {
			$(".button img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
	$(document).ready(function() {
		$(".button1").hover(function() {
			$(".button1 img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
</script>

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">
	function seach() {
		document.forms['frm'].action.value = 'seach';
		document.forms["frm"].submit();
	}
	function submitform() 
	{
	/* 	if(document.forms["frm"]["level"].value=="2")
		{
// 			if(document.forms["frm"]["vungId"].value==""){
// 				//setErrors("Vui lòng chọn vùng cần lấy báo cáo.");
// 				return;
// 			}
		}	 */	
		/* var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		} */
		
		document.forms['frm'].action.value = 'Taomoi';
		document.forms["frm"].submit();
	/* 	reset(); */
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	function submitCBO(){
		document.forms['frm'].action.value="";
		document.forms['frm'].submit();
	}
	function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");		
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}		
	};
	function toPdf() 
	{
		document.forms['frm'].action.value = 'toPdf';
		document.forms["frm"].submit();
	}
	function AnThang()
	{
		document.getElementById("XemNgay").style.display = "";
		document.getElementById("XemThang").style.display = "none";
	}
	function HienThang()
	{
		document.getElementById("XemThang").style.display = "";
		document.getElementById("XemNgay").style.display = "none";
	}
</script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
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

	<form name="frm" method="post" action="../../BCDoThamNhap">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="view" value='<%=obj.getLoaiMenu() %>'> 
		<input type="hidden" name="userId" value='<%=obj.getuserId()%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=" "+url %> </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng bạn",nnId) %>
					<%=obj.getuserTen()%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> <%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %></legend>
					<textarea id="errors" name="errors" rows="1"  style="width: 100% ; color:#F00 ; font-weight:bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				
				
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
									<TD class="plainlabel">
										<input type="text" name="Sdays"	class="days" value='<%=obj.gettungay()%>' style="width:250px"/></TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Đến ngày",nnId) %></TD>
									<td>
										<input type="text" name="Edays" class="days" value='<%=obj.getdenngay()%>'  style="width:250px"/></td>
								</TR>
							<%if(!obj.getLoaiMenu().equals("NPP"))  {%>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Vùng/Miền",nnId) %></TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();" style ="width:200px" multiple="multiple">
										
											<%if (vung != null)
													while (vung.next()) {
														if (obj.getvungId().contains(vung.getString("pk_seq"))) {%>
													<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
												<%} else {%>
													<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									</TR>
								<tr>
								
								<TD width="19%" class="plainlabel" ><%=ChuyenNgu.get("Khu Vực",nnId) %> </TD>
								<TD class="plainlabel" >
								<TABLE cellpadding="0" cellspacing="0">
								<TR><TD>
								<select name="khuvucId" id = "khuvucId" onchange="submitCBO();" multiple="multiple" >
                                 <option value ="" > </option>  
                               <%
                               if(khuvuc != null)
                               while(khuvuc.next())
                               {
                               if(obj.getkhuvucId().contains(khuvuc.getString("pk_seq"))) {
                            	%><option value ="<%=khuvuc.getString("pk_seq") %>" selected> <%=khuvuc.getString("ten") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=khuvuc.getString("pk_seq") %>"> <%=khuvuc.getString("ten") %></option>
                              <%}} %>                             </select>		   										</TD>
                                    		</TR>
								</TABLE>
								</TD>
								
								</tr>
								<%} %>
								<tr>
								  	<TD width="19%" class="plainlabel" ><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
								<select id = "nppId" name="nppId"  style = "width: 250px" multiple="multiple" >
                                 <option value =""> </option>  
                               <%
                               if(npp != null)
                               while(npp.next())
                               {
                               if(obj.getnppId().contains(npp.getString("pk_seq")) ) {
                            	%><option value ="<%=npp.getString("pk_seq") %>" selected> <%=npp.getString("ten") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=npp.getString("pk_seq") %>"> <%=npp.getString("ten") %></option>
                              <%}} %>                             </select>		   										</TD>
                                    		</TR>
								</TABLE>									
								</TD>
								
								</tr>
								<tr>
								<TD width="19%" class="plainlabel" ><%=ChuyenNgu.get("Trạng Thái",nnId) %> </TD>
								 <TD class="plainlabel" valign="middle" colspan = '3'>
	                           <select name="trangthai">
								  	<option value="" selected ></option>
									 	<option value="0" ><%=ChuyenNgu.get("Chưa chốt",nnId) %></option>
									  	<option value="1"><%=ChuyenNgu.get("Đã chốt",nnId) %></option>
									
	                           </select>
                        	</TD>  
								</tr>
								
								<TR>
								<TD class="plainlabel"><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %></TD>
									<TD class="plainlabel" >
										<select name="nhomspid" onchange="seach();"  id="nhomspid" >
											<option value="" selected><%=ChuyenNgu.get("Tất cả",nnId) %></option>
											<%if (nhomsp != null)
													while (nhomsp.next()) {
														if (nhomsp.getString("pk_seq").equals(obj.GetNhoSPId())) {%>
														<option value="<%=nhomsp.getString("pk_seq")%>" selected><%=nhomsp.getString("diengiai")%></option>
													<%} else {%>
														<option value="<%=nhomsp.getString("pk_seq")%>"><%=nhomsp.getString("diengiai")%></option>
												<% }}%>
										</select>
									</TD>
									</TR>
									<TR>
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
									
								</TR>
								<TR>
								</TR>
								<TR>
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
								<TR>
										<TD class="plainlabel"></TD>
										<TD class="plainlabel" colspan="3">
										<fieldset>
										<legend><%=ChuyenNgu.get("Lấy theo",nnId) %></legend> 
										 <% if(obj.getchon().equals("0")){ %>
											<input type="radio" name="chonid" value="0"  checked="checked" /><%=ChuyenNgu.get("Khách hàng",nnId) %> &nbsp;&nbsp;&nbsp;
											<!-- <input type="radio" name="chonid" value="1"  />Sản phẩm &nbsp;&nbsp;&nbsp;	 -->
											<input type="radio" name="chonid" value="2"  /><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="3"  /><%=ChuyenNgu.get("Nhãn Hàng",nnId) %> &nbsp;&nbsp;&nbsp;										
											<input type="radio" name="chonid" value="4"  /><%=ChuyenNgu.get("Chủng loại",nnId) %> &nbsp;&nbsp;&nbsp;	
											<input type="radio" name="chonid" value="5"  /><%=ChuyenNgu.get("Nhóm SKU's",nnId) %> &nbsp;&nbsp;&nbsp;		
										<%-- <%}else if(obj.getchon().equals("1")){ %>
											<input type="radio" name="chonid" value="0" /> Khách hàng&nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="1" checked="checked"  />Sản phẩm &nbsp;&nbsp;&nbsp;	
											<input type="radio" name="chonid" value="2"  />Nhóm sản phẩm &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="3"  />Nhãn Hàng &nbsp;&nbsp;&nbsp;										
											<input type="radio" name="chonid" value="4"  />Chủng loại &nbsp;&nbsp;&nbsp;	
											<input type="radio" name="chonid" value="5"  />Nhóm SKU's &nbsp;&nbsp;&nbsp;	 --%>
										<%}else if(obj.getchon().equals("2")){ %>
											<input type="radio" name="chonid" value="0" /><%=ChuyenNgu.get("Khách hàng",nnId) %> &nbsp;&nbsp;&nbsp;
											<!-- <input type="radio" name="chonid" value="1"  />Sản phẩm &nbsp;&nbsp;&nbsp;	 -->
											<input type="radio" name="chonid" value="2" checked="checked"  /><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="3"  /><%=ChuyenNgu.get("Nhãn Hàng",nnId) %> &nbsp;&nbsp;&nbsp;										
											<input type="radio" name="chonid" value="4"  /><%=ChuyenNgu.get("Chủng loại",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="5"  /><%=ChuyenNgu.get("Nhóm SKU's",nnId) %> &nbsp;&nbsp;&nbsp;		
										<%}else if(obj.getchon().equals("3")){ %>
											<input type="radio" name="chonid" value="0" /><%=ChuyenNgu.get("Khách hàng",nnId) %> &nbsp;&nbsp;&nbsp;
											<!-- <input type="radio" name="chonid" value="1"  />Sản phẩm &nbsp;&nbsp;&nbsp;	 -->
											<input type="radio" name="chonid" value="2"  /><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="3" checked="checked"  /><%=ChuyenNgu.get("Nhãn Hàng",nnId) %> &nbsp;&nbsp;&nbsp;										
											<input type="radio" name="chonid" value="4"  /><%=ChuyenNgu.get("Chủng loại",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="5"  /><%=ChuyenNgu.get("Nhóm SKU's",nnId) %> &nbsp;&nbsp;&nbsp;		
											<%} else if(obj.getchon().equals("4")){ %>	
											<input type="radio" name="chonid" value="0" /><%=ChuyenNgu.get("Khách hàng",nnId) %> &nbsp;&nbsp;&nbsp;
											<!-- <input type="radio" name="chonid" value="1"  />Sản phẩm &nbsp;&nbsp;&nbsp;	 -->
											<input type="radio" name="chonid" value="2"  /><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="3"  /><%=ChuyenNgu.get("Nhãn Hàng",nnId) %> &nbsp;&nbsp;&nbsp;										
											<input type="radio" name="chonid" value="4" checked="checked"  /><%=ChuyenNgu.get("Chủng loại",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="5"  /><%=ChuyenNgu.get("Nhóm SKU's",nnId) %> &nbsp;&nbsp;&nbsp;		
											<%} else if(obj.getchon().equals("5")){ %>	
											<input type="radio" name="chonid" value="0" /><%=ChuyenNgu.get("Khách hàng",nnId) %> &nbsp;&nbsp;&nbsp;
											<!-- <input type="radio" name="chonid" value="1"  />Sản phẩm &nbsp;&nbsp;&nbsp;	 -->
											<input type="radio" name="chonid" value="2"  /><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="3"  /><%=ChuyenNgu.get("Nhãn Hàng",nnId) %> &nbsp;&nbsp;&nbsp;										
											<input type="radio" name="chonid" value="4"   /><%=ChuyenNgu.get("Chủng loại",nnId) %> &nbsp;&nbsp;&nbsp;
											<input type="radio" name="chonid" value="5" checked="checked" /><%=ChuyenNgu.get("Nhóm SKU's",nnId) %> &nbsp;&nbsp;&nbsp;		
											<%} %>
										</fieldset>
										</TD>
									</TR>
								<TR>
									<td  class="plainlabel"><a class="button"
										href="javascript:submitform();"> <img style="top: -5px;"
											src="../images/button.png" alt=""><%=ChuyenNgu.get("Tạo báo cáo",nnId) %> </a></td>
									
											
								</TR>
							</TABLE>
						</div> 
			</div>
		</div>
		<br /> <br /> <br /> <br />
	</form>
	<%
	
		if(obj!=null){obj.DBclose();
		obj = null;}
		session.setAttribute("obj", null);
	%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>