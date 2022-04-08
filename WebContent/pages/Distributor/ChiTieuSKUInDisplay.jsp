<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.dms.distributor.beans.chitieunpp.*"%>
<%@page import="geso.dms.distributor.beans.chitieunpp.imp.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="javax.xml.crypto.Data"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.util.List" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%
	IChitieuSKUInTT obj =(IChitieuSKUInTT)session.getAttribute("obj");
	List<IChitieusku> listddkd= (List<IChitieusku>) obj.getSpList();
	ResultSet dvkd = (ResultSet)obj.getDvkdRs();
	 ResultSet kenh = (ResultSet)obj.getKbhRs();
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
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
	function save()
	{	
			document.forms['ChitieuSKU'].removeAttribute('enctype', "multipart/form-data", 0);
		  var thang = document.forms["ChitieuSKU"].thang.value;
		  var diengiai = document.forms["ChitieuSKU"].diengiai.value;
		  var nam = document.forms["ChitieuSKU"].namxx.value;
		 
		  if( thang == 0 )
		  {
			  document.forms["ChitieuSKU"].dataerror.value="Chọn tháng cần đặt chỉ tiêu ";
			  return;
		  }
		  if( nam == 0 )
		  {
			  document.forms["ChitieuSKU"].dataerror.value="Chọn năm cần đặt chỉ tiêu ";
			  return;
		  }
		  if( diengiai== 0 )
		  {
			  document.forms["ChitieuSKU"].dataerror.value="Nhập vào diễn giải";
			  return;
		  }
		  if(document.getElementById("sku").rows.length==1)
		  {
			  document.forms["ChitieuSKU"].dataerror.value="Không có sản phẩm nào";
			  return;
		  }
		document.forms["ChitieuSKU"].action.value="save";
		document.forms["ChitieuSKU"].submit();
	}
	
	function upload()
	{
		document.forms['ChitieuSKU'].setAttribute('enctype', "multipart/form-data", 0);
	    document.forms['ChitieuSKU'].submit();
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
</SCRIPT>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>

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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="nkmId" value="">
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%= obj.getId()%>">
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
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= userTen %>&nbsp;  </TD></tr>
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
						    <div id="btnSave">
						    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
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
									<select name="thang" style="width: 80px">
									<option value=0> </option>  
									<%
									int k=1;
									for(k=1; k <= 12; k++ )
									{
										System.out.println(obj.getThang());
									  if(obj.getThang().equals(String.valueOf(k)) ) {
									%>
										<option value=<%= k %> selected="selected" > <%= k %></option> 
									<%  }else{  %>
									<% } }%>
									</select>
								</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Năm <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel">
									<select name="namxx" style="width :80px">
									<option value= ""> </option>  
									<%
									Calendar cal=Calendar.getInstance();
									int year_=cal.get(Calendar.YEAR);
									System.out.println(obj.getNam());
									for(int n=2008; n<year_+3; n++) {
									  if(obj.getNam().equals(String.valueOf(n)) ){									  
									%>
										<option value=<%=n %> selected="selected" > <%=n %></option> 
									<%
									  }else{
									 %>

									<% } }
									%>
									</select>
						  	  	</TD>
						  </TR>
						   <TR>								  
								    <TD class="plainlabel">Kênh bán hàng </TD>
									<TD colspan="5" class="plainlabel"> 
						 			<SELECT name="kenhbanhang" id="kenhbanhang" >
							 			 <option value=""></option>
										  <% if(kenh != null){
											  try{ while(kenh.next()){ 													 
								    			if(kenh.getString("pk_seq").equals(obj.getKbhId())){ %>
								      				<option value='<%=kenh.getString("pk_seq")%>' selected><%=kenh.getString("ten") %></option>
								      			<%}else{ %>
								     				
								     			<%}}}catch(java.sql.SQLException e){}}  kenh.close();%>	 
                                 			</SELECT></TD>														
								</TR>
						  	<TR>								  
								    <TD class="plainlabel">Đơn vi7 kinh doanh </TD>
									<TD colspan="5" class="plainlabel"> 
						 			<SELECT name="donvikinhdoanh" id="donvikinhdoanh" >
							 			 <option value=""></option>
										  <% if(dvkd != null){
											  try{ while(dvkd.next()){ 													 
								    			if(dvkd.getString("pk_seq").equals(obj.getDvkdId())){ %>
								      				<option value='<%=dvkd.getString("pk_seq")%>' selected><%=dvkd.getString("ten") %></option>
								      			<%}else{ %>
								     				
								     			<%}}}catch(java.sql.SQLException e){}} dvkd.close();%>	 
                                 			</SELECT></TD>														
								</TR>
						    <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel">
						  	  		<input type="text" style="width :250px" name="diengiai" value="<%= obj.getDiengiai() %>"> 
						  	  	</TD>
						  	</TR>
						  <!-- 	<TR>
						  	  	<TD class="plainlabel">Chọn tập tin</TD>
						  	  	<TD class="plainlabel">
						  	  		<INPUT type="file" name="filename" size="40" value=''>
						  	  	</TD>
						  	</TR>
						  	 <TR>							  	
						  	  	<TD class="plainlabel" colspan="2">
						  	  	<a class="button2"  href="javascript:upload()">
									<img style="top: -4px;" src="../images/button.png" alt="">Cập nhật</a>&nbsp;&nbsp;&nbsp;&nbsp;	
						  	  	</TD>
						  	</TR> -->
						</TABLE>
						<TABLE ID="sku" width="100%" border="0" cellspacing="1" cellpadding="0" >							
								<TR class="tbheader">
									<TH align="center" width="15%">Mã sản phẩm</TH>
									<TH align="center" width="40%">Tên sản phẩm</TH>
									<TH  align="center" width="15%">Số lượng</TH>
								</TR>
								<%
										int m=0;
										if(listddkd != null)
										{
											while (m < listddkd.size()){
												   IChitieusku ddkd = listddkd.get(m);
															
											%>
											<tr>
											<td align="center"><input type =text name="masp" readonly="readonly" style="width :100%;text-align: center;" value=<%=ddkd.getMasanpham() %>> </td>
											<td align="center"><input type =text name="tensp" readonly="readonly" style="width :100%;text-align: left;" readonly="readonly" value="<%=ddkd.getTensanpham() %>"> </td>
											<td align="right"> <input type =text name="soluong" readonly="readonly" style="width :100%;text-align: right;" value="<%= ddkd.getSoluong()%>" onkeypress="return keypress(event);"> </td>
										    </tr>
											<%
											m++;
								}
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
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%}%>