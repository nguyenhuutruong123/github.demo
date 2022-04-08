<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.dms.distributor.beans.chitieunpp.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="javax.xml.crypto.Data"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%
 	IChitieuSKUInTT obj =(IChitieuSKUInTT)session.getAttribute("ctskuBean");
	ResultSet nppRs = (ResultSet)obj.getNppRs();
	ResultSet nhomRs = (ResultSet)obj.getNhomspRs();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">

<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script> 
<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["ChitieuSKU"].submit();
	}
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function save()
	{
	  var thang = document.forms["ChitieuSKU"].thang.value;
	  var nam = document.forms["ChitieuSKU"].nam.value;
	
	  if( nam == '' )
	  {
		  document.forms["ChitieuSKU"].dataerror.value = "Chọn năm cần đặt chỉ tiêu";
		  return;
	  }
	  
	  if( thang == '' )
	  {
		  document.forms["ChitieuSKU"].dataerror.value = "Chọn tháng cần đặt chỉ tiêu";
		  return;
	  }
	 
	  document.forms["ChitieuSKU"].action.value = "save";
	  document.forms["ChitieuSKU"].submit();
  	}
	
	function print()
	{
	  var thang = document.forms["ChitieuSKU"].thang.value;
	  var nam = document.forms["ChitieuSKU"].nam.value;
	
	  if( nam == '' )
	  {
		  document.forms["ChitieuSKU"].dataerror.value = "Chọn năm cần đặt chỉ tiêu";
		  return;
	  }
	  
	  if( thang == '' )
	  {
		  document.forms["ChitieuSKU"].dataerror.value = "Chọn tháng cần đặt chỉ tiêu";
		  return;
	  }
	 
	  document.forms["ChitieuSKU"].action.value = "print";
	  document.forms["ChitieuSKU"].submit();
  	}
	
	function checkAll()
	{
		var check = document.getElementById("chkAll");
		var nppIds = document.getElementsByName("nppIds");
		
		if(check.checked)
		{
			for(i = 0; i < nppIds.length; i++)
				nppIds.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < nppIds.length; i++)
				nppIds.item(i).checked = false;
		}
	}

</SCRIPT>

<script type="text/javascript">
$( function() {
	//Created By: Brij Mohan
	//Website: http://techbrij.com
	function groupTable($rows, startIndex, total)
	{
		if (total === 0)
		{
			return;
		}
		var i , currentIndex = startIndex, count=1, lst=[];
		var tds = $rows.find('td:eq('+ currentIndex +')');
		var ctrl = $(tds[0]);
		lst.push($rows[0]);
		for (i=1;i<=tds.length;i++){
		if (ctrl.text() ==  $(tds[i]).text()){
		count++;
		$(tds[i]).addClass('deleted');
		lst.push($rows[i]);
		}
		else{
			if (count>1){
			ctrl.attr('rowspan',count);
			groupTable($(lst),startIndex+1,total-1)
			}
			count=1;
			lst = [];
			ctrl=$(tds[i]);
			lst.push($rows[i]);
		}
		}
	}
	var rowCount = $('#sku tr').length;

	groupTable($('#sku tr:has(td)'),0,rowCount);
	$('#sku .deleted').remove();
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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="task" value="TT">
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%= obj.getMsg() %>">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý chỉ tiêu > Chỉ tiêu SKU In > Hiển thị</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
							    <A href="javascript: print()" ><IMG src="../images/excel.gif" title="In Chi tieu" alt="In Chi tieu" border = "1"  style="border-style:outset; width: 30px; height: 30px"></A>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin chỉ tiêu </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="20%" class="plainlabel" >Tháng <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<select name="thang" style="width: 50px">
									<option value=0> </option>  
									<%
									int k = 1;
									for(k=1; k <= 12; k++ ){
										
									  if(obj.getThang().equals(Integer.toString(k))) {
									%>
										<option value=<%= k %> selected="selected" > <%= k %></option> 
									<%  }else{  %>
										<option value=<%= k %> > <%= k %></option> 
									<% } }%>
									</select>
								</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Năm <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel">
									<select name="nam" style="width :50px">
									<option value= ""> </option>  
									<%
									Calendar cal=Calendar.getInstance();
									int year_=cal.get(Calendar.YEAR);
									for(int n=2008; n<year_+3; n++) {
									  if(obj.getNam().equals( Integer.toString(n)) ){									  
									%>
										<option value=<%=n %> selected="selected" > <%=n %></option> 
									<%
									  }else{
									 %>
										<option value=<%=n %> ><%=n %></option> 
									<% } }
									%>
									</select>
						  	  	</TD>
						  </TR>
						   
						    <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel">
						  	  		<input type="text" name="diengiai" value="<%= obj.getDiengiai() %>"> 
						  	  	</TD>
						  	</TR>
						  	 <TR>
						  	  	<TD class="plainlabel">Nhóm sản phẩm</TD>
						  	  	<TD class="plainlabel">
								<select name="nspId" >
									<option value="" selected>All</option>
									<%if (nhomRs != null){
											while (nhomRs.next()) {
												if (nhomRs.getString("pk_seq").equals(obj.getNspId())) {%>
													<option value="<%= nhomRs.getString("pk_seq")%>" selected><%=nhomRs.getString("diengiai")%></option>
											<%} else {%>
												<option value="<%= nhomRs.getString("pk_seq")%>"><%=nhomRs.getString("diengiai")%></option>
											<%} } } %>
								</select>
							</TD>
						  	</TR>
						</TABLE>
						<hr />
						<TABLE id="sku" border="0" width="100%" cellpadding="0" cellspacing="1">
							<TR  class="tbheader" >
								<TH width="5%">STT</TH>
								<TH width="15%">&nbsp;Mã nhà phân phối</TH>
								<TH width="50%">&nbsp;Tên nhà phân phối</TH>
								<TH width="20%">&nbsp;Trạng thái</TH>
								<TH width="10%">Chọn hết <input type="checkbox" id="chkAll" onchange="checkAll()"></TH>
							</TR>
							<% if(nppRs != null)
							{ 
								int m = 1;
								while(nppRs.next()){ %> 
								
								<TR >
									<TD width="5%">
										<input type="text" name="STT" value="<%= m %>" style="text-align: center; width: 100%" readonly="readonly"></TD>
									<TD><input type="text" name="manpp" value="<%= nppRs.getString("ma") %>" style="width: 100%" readonly="readonly" ></TD>
									<TD><input type="text" name="manpp" value="<%= nppRs.getString("ten") %>" style="width: 100%" readonly="readonly" ></TD>
									<TD><input type="text" name="manpp" value="<%= nppRs.getString("trangthai") %>" style="width: 100%" readonly="readonly" ></TD>
									<TD align="center">
									<input type="checkbox" name="nppIds" checked="checked" value="<%= nppRs.getString("nppId") %>" >
									</TD>
								</TR>
								
							<% m++; } nppRs.close(); } %>
						</TABLE>
										
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<% 	
	try
    {
		if(nppRs != null)
			nppRs.close();
		if(nhomRs != null)
			nhomRs.close();
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("ctskuBean", null);
	}
	catch (Exception e) {} %>
<%}%>