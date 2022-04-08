<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.report.IBcHoaDon"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page import="java.sql.ResultSetMetaData"%>
<%@ page import="java.sql.Types"%>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>

<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%	
	IBcHoaDon obj = (IBcHoaDon) session.getAttribute("obj");
	ResultSet ddkd = obj.getDdkdRs();
	ResultSet nhaphangRs = obj.getNhaphangRs();
	ResultSet spRs = obj.getSpRs();
	ResultSet hdRs = obj.getHoadonRs();
	ResultSet kbhRs = obj.getKbhRs();
	ResultSet totalRs = obj.getTotalRs();

	String url="";
	/* url = util.getUrl("BcHoaDonSvl",""); */

	ResultSet vungRs = obj.getVungRs();
	ResultSet ttRs = obj.getTtRs();
	ResultSet nppRs = obj.getNppRs();
	ResultSet nhomRs = obj.getNhomRs();
	

	int[] quyen = new  int[6];
	quyen = util.Getquyen("SAP_Invoice","", userId);
	
	
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
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
</script>

<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs-panes.css">
<script type="text/javascript">
	 
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
</script>


<script language="javascript" type="text/javascript">

function clearform()
{   
	document.forms["ckParkForm"].Sdays.value="";
	document.forms["ckParkForm"].Edays.value="";
	document.forms["ckParkForm"].khId.value="";
	document.forms["ckParkForm"].spId.value="";
	document.forms["ckParkForm"].ddkdId.value="";
	document.forms["ckParkForm"].kbhId.value="";
   document.forms["ckParkForm"].submit();
}

function submitform()
{   
	document.forms['ckParkForm'].action.value='';
   document.forms["ckParkForm"].submit();
}

function xuatExcel()
{
	document.forms['ckParkForm'].action.value= 'excel';
	document.forms['ckParkForm'].submit();
	
}

function search()
{
	document.forms['ckParkForm'].action.value= 'search';
	document.forms['ckParkForm'].submit();
}

function saveform()
{
	document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
	document.forms['ckParkForm'].action.value= 'save';
	document.forms['ckParkForm'].submit();
}

function thongbao()
{
	if(document.getElementById("msg").value != '')
		alert(document.getElementById("msg").value);
}

function upload()
{
	document.forms['ckParkForm'].setAttribute('enctype', "multipart/form-data", 0);
    document.forms['ckParkForm'].submit();
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

	<form name="ckParkForm" method="post" action="../../SAP_Invoice">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value=''> <input
			type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
			
			<% obj.setNextSplittings(); %>
<input type="hidden"name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">
			
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo quản trị > Hóa đơn </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1"
						style="width: 100%; color: #F00; font-weight: bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
				
					
						<div id="btnSave">
							<A href="javascript:saveform()"><img
								src="../images/Save30.png" alt="Luu lai" title="Đồng bộ Dữ liệu" border="1" longdesc="Luu lai"
								style="border-style: outset">
							</A>
						</div> 
				
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"> Hóa đơn </legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="100%" cellpadding="6px" cellspacing="0px"
								style="margin-top: 5px">
								 <TR>
									<TD class="plainlabel" width="120px">Từ ngày</TD>
									<TD class="plainlabel" width="250px"><input type="text"
										name="Sdays" class="days" value='<%=obj.getTuNgay()%>' /></TD>
									<TD class="plainlabel" width="100px">Đến ngày</TD>
									<TD class="plainlabel" colspan="3"><input type="text"
										name="Edays" class="days" value='<%=obj.getDenNgay()%>' /></td>
								</TR>


								<TR>
								<%-- 	<TD class="plainlabel" width="120px">Charnnel</TD>
									<TD class="plainlabel" width="250px"><input type="text" name="CustID" value='<%=obj.getc%>' /></TD> --%>
									<TD class="plainlabel" width="100px">InvoiceId</TD>
									<TD class="plainlabel" colspan="3"> <input type="text" name="InvoiceId"  value='<%=obj.getInvoiceId()%>' /></td>									
								</TR>
								
									<TR>
									<TD class="plainlabel" width="120px">Invoice_RevertFor </TD>
									<TD class="plainlabel" width="250px"><input type="text" name="Invoice_RevertFor" value='<%=obj.getInvoice_RevertFor()%>' /></TD>
									<TD class="plainlabel" width="100px">LegalNumber</TD>
									<TD class="plainlabel" colspan="3"> <input type="text" name="LegalNumber"  value='<%=obj.getLegalNumber()%>' /></td>									
								</TR>
								
								
								<TR>
									<TD class="plainlabel" width="120px">CustomerId</TD>
									<TD class="plainlabel" width="250px"><input type="text" name="CustomerId" value='<%=obj.getCustomerId()%>' /></TD>
									<TD class="plainlabel" width="100px">CustomerName</TD>
									<TD class="plainlabel" colspan="3"> <input type="text" name="CustomerName"  value='<%=obj.getCustomerName()%>' /></td>									
								</TR>
								
								
						
								
								
									<TR>
									<TD class="plainlabel" width="120px">InvoiceType</TD>
									<TD class="plainlabel" width="250px"><input type="text" name="InvoiceType" value='<%=obj.getInvoiceType()%>' /></TD>
									<TD class="plainlabel" width="100px">Status</TD>
									<TD class="plainlabel" colspan="3"> <input type="text" name="Status"  value='<%=obj.getStatus()%>' /></td>									
								</TR>
								
								
								
									<TR>
									<TD class="plainlabel" width="120px">SO_Number</TD>
									<TD class="plainlabel" width="250px"><input type="text" name="SO_Number" value='<%=obj.getSO_Number()%>' /></TD>
									<TD class="plainlabel" width="100px">PO_Number</TD>
									<TD class="plainlabel" colspan="3"> <input type="text" name="PO_Number"  value='<%=obj.getPO_Number()%>' /></td>									
								</TR>
								
								
								
								<TR>
									
																		
								</TR> 
								

								<TR>
									<TD class="plainlabel" colspan="6"><a class="button2"
										href="javascript:search();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tìm kiếm
									</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<!--  <a class="button2"
										href="javascript:xuatExcel();"> <img style="top: -4px;"
											src="../images/button.png" alt="">Xuất dữ liệu 
									</a>&nbsp;&nbsp;&nbsp;&nbsp;
									-->
								</TR>


 <TR>
							  
						  	  	<!-- <TD class="plainlabel"><INPUT type="file" name="filename" size="40" value=''>&nbsp;&nbsp;&nbsp;&nbsp;
						  			<a class="button2"  href="javascript:upload()">
									<img style="top: -4px;" src="../images/button.png" alt="">Upload Nhận hàng</a>
						  	  	</TD> -->
						  </TR>
						


							</TABLE>
						</div>
					</div>
				</fieldset>
			</div>

			<% String current=""; %>
			<table width="100%">
				<tr>
					<td>
						<ul class="tabs">

							<li <%=obj.getActiveTab().equals("0") ? "class='current'" : "" %>><a
								href="#tabNhap">Hóa đơn</a></li>

						</ul>

						<div class="panes">

		<div id="tabHD" class="tab_content">
			<TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px">
				<TR class="plainlabel" valign="bottom">
						<%
												if(hdRs!=null){
													try{
														ResultSetMetaData rsmd = hdRs.getMetaData();
														int socottrongSql = rsmd.getColumnCount();
						for( int i =1 ; i <=socottrongSql ; i ++  )
			{ %>
				<th width="10%" align="left"> <%=rsmd.getColumnName(i) %>
			<%}
					%>
				</TR>
					
												<%
														int m = 1;
														String lightrow = "tblightrow";
														String darkrow = "tbdarkrow";
														while (hdRs.next())
														{
															if (m % 2 != 0) {%>						
															<TR class= <%=lightrow%> >
														<%} else {%>
															<TR class= <%= darkrow%> >
														<%}%>
					<%										
					for(int i =1;i <=socottrongSql ; i ++)
					{
						if(rsmd.getColumnType(i) == Types.DOUBLE )
						{
							%>
								<Td align="right"><%= formatter.format(hdRs.getDouble(i)) %></Td>
						<% }
						else
						{ %>
						
						<Td align="right"><%=hdRs.getString(i)==null?"":hdRs.getString(i)%></Td>
					<% 	}
					}
						%>								
												<% m++;}}catch(Exception e){}} %>
												</TR>
												
												
												<tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('ckParkForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="Prev('ckParkForm', 'prev')"> &nbsp; <%}else{ %>
															<img alt="Trang Truoc" src="../images/prev_d.gif">
															&nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 							
											
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('ckParkForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="Next('ckParkForm', 'next')"> &nbsp; <%}else{ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif">
															&nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('dhForm', -1, 'view')"> &nbsp; <%} %>
														</TD>
													</tr>
																								 											
											</TABLE>
										</div> 
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
	
<%
	if(ddkd != null){ ddkd.close(); ddkd = null ;} 	
	if(nhaphangRs != null){ nhaphangRs.close(); nhaphangRs = null ;} 	
	if(spRs != null){ spRs.close(); spRs = null ;} 	
	if(hdRs != null){ hdRs.close(); hdRs = null ;} 	
	if(kbhRs != null){ kbhRs.close(); kbhRs = null ;} 	
	if(totalRs != null){ totalRs.close(); totalRs = null ;} 	
	if(vungRs != null){ vungRs.close(); vungRs = null ;} 	
	if(ttRs != null){ ttRs.close(); ttRs = null ;} 	
	if(nppRs != null){ nppRs.close(); nppRs = null ;} 	
	if(nhomRs != null){ nhomRs.close(); nhomRs = null ;} 	

	if(obj!=null){
		obj.closeDB();
		obj = null;
	}
	session.setAttribute("obj", null);
}
%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>