<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
	
	String groupCustomer = (String)session.getAttribute("groupCustomer");
	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet kenh = obj.getkenh();
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet npp = obj.getnpp();
	ResultSet dvkd = obj.getdvkd();
	ResultSet nsp = obj.GetRsNhomSP();
	ResultSet dvdl = obj.getdvdl();
	ResultSet sanpham = obj.getsanpham();
	ResultSet gsbh = obj.getgsbh();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("DailySecondarySalesTT_focusedSKUSvl","", userId);
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("DailySecondarySalesTT_focusedSKUSvl","",nnId);	
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
	function search() {
		document.forms['frm'].action.value = 'search';
		document.forms["frm"].submit();
	}

	function submitform() 
	{
		
		document.forms['frm'].errors.value = '';
		document.forms['frm'].action.value = 'Taomoi';
		document.forms["frm"].submit();
		reset();
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
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


</script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2(); 
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

	<form name="frm" method="post" action="../../DailySecondarySalesTT_focusedSKUSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=obj.getuserId()%>'>

			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=" "+url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng bạn",nnId) %> 
					<%=obj.getuserTen()%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %> </legend>
					<textarea id="errors" name="errors" rows="1"  style="width: 100% ; color:#F00 ; font-weight:bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset >
					<legend class="legendtitle"><%=ChuyenNgu.get("Doanh số SKU tập trung",nnId) %> </legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Từ ngày",nnId) %> </TD>
									<TD class="plainlabel">
										<input type="text" name="Sdays"	class="days" value='<%=obj.gettungay()%>' /></TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Đến ngày",nnId) %> </TD>
									<td>
										<input type="text" name="Edays" class="days" value='<%=obj.getdenngay()%>' /></td>
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId) %> </TD>
									<TD class="plainlabel">
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
									<TD class="plainlabel"><%=ChuyenNgu.get("Giám sát bán hàng",nnId) %> </TD>
									<TD class="plainlabel">
										<select name="gsbhs" id="gsbhsId"
											onchange="seach();">
												<option value="" selected>All</option>
												<%if (gsbh != null)
														while (gsbh.next()) {
															if (gsbh.getString("pk_seq").equals(obj.getgsbhId())) {%>
														<option value="<%=gsbh.getString("pk_seq")%>" selected>
															<%=gsbh.getString("ten")%></option>
												<%} else {%>
														<option value="<%=gsbh.getString("pk_seq")%>"><%=gsbh.getString("ten")%></option>
												<%}}%>
										</select>
									</td>
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Vùng/Miền",nnId) %> </TD>
									<TD class="plainlabel">
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
									<TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %> </TD>
									<TD class="plainlabel" >
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
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhà Phân Phối",nnId) %>  </TD>
									<TD class="plainlabel">
										<select name="nppId" onchange="seach();" id="nppId">
											<option value="" selected>All</option>
											<%if (npp != null)
													while (npp.next()) {
														if (npp.getString("pk_seq").equals(obj.getnppId())) {%>
															<option value="<%=npp.getString("pk_seq")%>" selected><%=npp.getString("ten")%></option>
													<%} else {%>
														<option value="<%=npp.getString("pk_seq")%>"><%=npp.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
									
									<TD class="plainlabel"></TD>
									<TD class="plainlabel">
										
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %> </TD>
									<TD class="plainlabel" colspan="3">
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

								</TR>							
								<TR>									
									<TD class="plainlabel"><%=ChuyenNgu.get("Bao gồm chiết khấu",nnId) %> </TD>
									<TD class="plainlabel" colspan="3">
										<%
											if(obj.getdiscount().equals("1")){%>
												<input type="radio" name="discount"	value="1"  checked="checked" /><%=ChuyenNgu.get("Có",nnId) %> &nbsp;&nbsp;&nbsp; 
												<input type="radio" name="discount" value="0" /><%=ChuyenNgu.get("Không",nnId) %> 
											<%}else{%>
												<input type="radio" name="discount"	value="1"  /><%=ChuyenNgu.get("Có",nnId) %>  &nbsp;&nbsp;&nbsp; 
												<input type="radio" name="discount" value="0"  checked="checked"/><%=ChuyenNgu.get("Không",nnId) %> 
											<%}%>										
									</TD>									
								</TR>
								<tr style="display: none">
									<TD class="plainlabel"><%=ChuyenNgu.get("Bao gồm VAT",nnId) %> </TD>
									<TD class="plainlabel" colspan="3">
										<%if(obj.getvat().equals("1")){%>
												<input type="radio" value="0"	name="vats" checked="checked" /><%=ChuyenNgu.get("Có",nnId) %>  &nbsp;&nbsp;&nbsp; 
												<input type="radio" value="0" name="vats" /><%=ChuyenNgu.get("Không",nnId) %> 
										<%}else{%> 
												<input type="radio" value="0"	name="vats" /><%=ChuyenNgu.get("Có",nnId) %>  &nbsp;&nbsp;&nbsp; 
												<input type="radio" value="0" name="vats" checked="checked"  /><%=ChuyenNgu.get("Không",nnId) %> 
										<%}%>
									</TD>
								</TR>
								
								<TR>
								
									
											 	
											 <%
											 String wheresku = (String)session.getAttribute("checkedSKU");
											 String[] checkedSKU=wheresku.split(",");
											 boolean co=true;
				                           	 if(checkedSKU==null)
				                           	 {
												 while(nsp.next())
	
					                               { 
													 if(co)
													 { co=false;%>
														 
														 <TD width="19%" class="plainlabel" valign="top"><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %> </TD>
														  	<TD class="plainlabel" colspan="3" >
																<TABLE cellpadding="0" cellspacing="0">
																	<TR>
																	<TD>
																	
																	 <ul >
														 
														 
													<% }%>
													 
					                             
				                            	  	<li><label  for="check" > <input type ="hidden" value="" name="valuechecked"><input type="checkbox" name="check"  onchange="recheck();" value ="<%=nsp.getString("pk_seq") %>"> <%=nsp.getString("pk_seq")+ " -" + nsp.getString("diengiai")%> </label>  <input name ="skutest"  type="hidden" value="<%=nsp.getString("pk_seq") %>"></li>
					                              <%}
				                           	 }
				                           	 else
				                           	 {
				                            	while (nsp.next())
				                            	{
				                            		if(co)
													 { co=false;
													 %>
															<TD width="19%" class="plainlabel" valign="top"><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %> </TD>
														  	<TD class="plainlabel" colspan="3" >
																<TABLE cellpadding="0" cellspacing="0">
																	<TR>
																	<TD>
																	
																	 <ul >
														 
													<% }
				                            		
				                            		System.out.println("Asssss: "+nsp.getString("pk_seq"));
				                            		int i=0;
				                            		for (i=0;i<checkedSKU.length;i++)
				                            		{
				                            			if(checkedSKU[i].trim().equals(nsp.getString("pk_seq").trim()))
				                            				break;
				                            		}
				                            		if(i< checkedSKU.length)
				                            		{
				                            			 {%>
						                            	  	<li><label  for="check" > <input type ="hidden" value="" name="valuechecked"><input type="checkbox" checked="checked" name="check"  onchange="recheck();" value ="<%=nsp.getString("pk_seq") %>"> <%=nsp.getString("pk_seq")+ " -" + nsp.getString("diengiai")%> </label>  <input name ="skutest"  type="hidden" value="<%=nsp.getString("pk_seq") %>"></li>
							                              <%}
				                            		}
				                            		else
				                            		{
				                            			 {%>
						                            	  	<li><label  for="check" > <input type ="hidden" value="" name="valuechecked"><input type="checkbox" name="check"  onchange="recheck();" value ="<%=nsp.getString("pk_seq") %>"> <%=nsp.getString("pk_seq")+ " -" + nsp.getString("diengiai")%> </label>  <input name ="skutest"  type="hidden" value="<%=nsp.getString("pk_seq") %>"></li>
							                              <%}
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
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""><%=ChuyenNgu.get("Tạo báo cáo",nnId) %> </a></td>
								</TR>
								
							</TABLE>
							</TD>
							</TR>
							</TABLE>
							</div>
							</div>
							</fieldset>
							</div>
							</form>
							<script type="text/javascript">
		$("select:not(.notuseselect2)").css({
			"width": "200px", 
			//"height": "200px"
		});
	</script>
							
		
	<%
		if(kenh !=null){ kenh.close(); kenh = null; }
		if(vung!=null){ vung.close(); vung = null; }
		if(khuvuc!=null){ khuvuc.close(); khuvuc = null; }
		if(npp!=null){ npp.close(); npp = null; }
		if(dvkd!=null){ dvkd.close(); dvkd = null; }
		if(nsp!=null){ nsp.close(); nsp = null; }
		if(dvdl!=null){ dvdl.close(); dvdl = null; }
		if(sanpham!=null){ sanpham.close(); sanpham = null; }
		if(gsbh!=null){ gsbh.close(); gsbh = null; }
		
		if(obj!=null){
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj",null);
	}
	%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>