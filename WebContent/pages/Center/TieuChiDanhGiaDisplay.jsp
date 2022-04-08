<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.beans.tieuchidanhgia.imp.*" %>
<%@ page import="geso.dms.center.beans.tieuchidanhgia.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%
 	ITieuchidanhgia obj =(ITieuchidanhgia)session.getAttribute("tcdgBean");
	ResultSet gsbhRs = obj.getGsbhRs();
	List<ITieuchiDetail> tcDetailList = obj.getTieuchiDetail();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["tctsku"].submit();
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
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function save()
	{
	  var thang = document.getElementById("thang").value;
	  if( thang == '' )
	  {
		  alert("Vui lòng chọn tháng");
		  return;
	  }
	  
	  var nam = document.getElementById("nam").value;
	  if( nam == '' )
	  {
		  alert("Vui lòng chọn năm");
		  return;
	  }
	  
	  document.forms["tctsku"].action.value = "save";
	  document.forms["tctsku"].submit(); 
  }
	
	
	function sellectAll_GSBH()
	 {
		 var chkAll = document.getElementById("chkAll_GSBH");
		 var gsbhIds = document.getElementsByName("gsbhIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < gsbhIds.length; i++)
			 {
				 gsbhIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < gsbhIds.length; i++)
			 {
				 gsbhIds.item(i).checked = false;
			 }
		 }
	 }
	function toExcel()
	{
		document.forms['tctsku'].action.value= 'ToExcel';
		document.forms['tctsku'].submit();
	}
	
</SCRIPT>

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
<input type="hidden" name="id" value='<%= obj.getId() %>' >
<input type="hidden" name="trangthai" value='<%= obj.getTrangthai() %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý nhân sự > Khai báo > Qui trình làm việc > Hiển thị</TD> 
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
							 <TD>
								<A href="javascript:toExcel();"  ><img src="../images/excel.gif" alt="Excel"  title="Excel" border="1" longdesc="Excel" style="border-style:outset" width='30' length='30'></A>
							</TD>								
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin tiêu chí đánh giá </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR valign="top">
						  	  	<TD width="20%"  class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>" >
						  	  	</TD>
						  </TR>
						  <TR>
								<TD class="plainlabel" >Tháng <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<select name="thang" id = "thang" style="width: 50px">
									<option value= ""> </option>  
									<%
									int k=1;
									for(k=1; k <= 12; k++ ){
										
									  if(obj.getThang().equals(Integer.toString(k)) ) {
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
									<select name="nam" id = "nam" style="width :50px">
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
										<option value=<%= n %> ><%=n %></option> 
									<% } }
									%>
									</select>
						  	  	</TD>
						  </TR>
                          <TR>
                             	<TD class="plainlabel" >Giám sát bán hàng </TD>
								<TD class="plainlabel">
									<select name="gsbhIds" id="gsbhIds" onchange="submitform()">
										<option value=""> </option>
										<%
											if(gsbhRs != null)
											{
												while(gsbhRs.next())
												{
													if(obj.getGsbhIds().equals(gsbhRs.getString("pk_seq"))) {
													%>
													<option value="<%= gsbhRs.getString("pk_seq") %>" selected="selected"><%= gsbhRs.getString("ten") %></option>	
												<%} else { %>
													<option value="<%= gsbhRs.getString("pk_seq") %>" ><%= gsbhRs.getString("ten") %></option>
										<% } } } %>
									</select>
								</TD>
                          </TR >
						</TABLE>
					<hr />
						
					<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
					
					<% int count = 0;
					
					if(obj.getGsbhIds().trim().length() <= 0) { %>
					
                    <TR class="tbheader">
                        <TH align="center" width="10%">Tiêu chí</TH>
                        <TH align="left" width="80%"> Diễn giải </TH>
                        <TH align="center" width="10%"> Trọng số</TH>
                    </TR>
                    
                    <% 
                        for(int i = 0; i < tcDetailList.size(); i++)
                        {
                        	ITieuchiDetail detail = tcDetailList.get(i);
                        	%>
                        	<tr>
                        		<td>
                        			<input type="text" style="width: 100%" name = 'matieuchi' value="<%= detail.getMa() %>" readonly="readonly" >
                        			<input type="hidden" style="width: 100%" name = 'idtieuchi' value="<%= detail.getId() %>" >
                        		</td>
                        		<td><input type="text" style="width: 100%" name = 'diengiaitc' value="<%= detail.getDiengiai() %>" ></td>
                        		<td>
                        			<input type="text" style="width: 100%; text-align: right;" name = 'trongso' value="<%= detail.getTrongso() %>" onkeypress="return keypress(event);" >
                        			<input type="hidden" style="width: 100%; text-align: right;" name = 'chamlan1' value="" >
                        		    <input type="hidden" style="width: 100%; text-align: right;" name = 'chamlan2' value="" >
                        		    <input type="hidden" style="width: 100%; text-align: right;" name = 'chamlan3' value="" >
                        		</td>
                        	</tr>
                        <% count++; }
                    %>
                    
                    <% } else { %> 
                    	
                   <TR class="tbheader">
                        <TH align="center" width="10%">Tiêu chí</TH>
                        <TH align="left" width="70%"> Diễn giải </TH>
                        <TH align="center" width="10%"> Trọng số</TH>
                        <TH align="center" width="10%">Điểm chấm</TH>
                        <TH align="left" width="10%" style="display: none">Chấm lần 2</TH>
                        <TH align="center" width="10%" style="display: none">Chấm lần 3</TH>
                    </TR>	
                    
                    <% 
                        for(int i = 0; i < tcDetailList.size(); i++)
                        {
                        	ITieuchiDetail detail = tcDetailList.get(i);
                        	%>
                        	<tr>
                        		<td>
                        			<input type="text" style="width: 100%" name = 'matieuchi' value="<%= detail.getMa() %>" readonly="readonly" >
                        			<input type="hidden" style="width: 100%" name = 'idtieuchi' value="<%= detail.getId() %>" >
                        		</td>
                        		<td><input type="text" style="width: 100%" name = 'diengiaitc' value="<%= detail.getDiengiai() %>" ></td>
                        		<td><input type="text" style="width: 100%; text-align: right;" name = 'trongso' value="<%= detail.getTrongso() %>" onkeypress="return keypress(event);" ></td>
                        		<td><input type="text" style="width: 100%; text-align: right;" name = 'chamlan1' value="<%= detail.getChamlan1() %>" onkeypress="return keypress(event);" ></td>
                        		<td style="display: none"><input type="text" style="width: 100%; text-align: right;" name = 'chamlan2' value="<%= detail.getChamlan2() %>" onkeypress="return keypress(event);" ></td>
                        		<td style="display: none"><input type="text" style="width: 100%; text-align: right;" name = 'chamlan3' value="<%= detail.getChamlan3() %>" onkeypress="return keypress(event);" ></td>
                        	</tr>
                        <% }
                    %>
                    
                     	
                    <% } %>
					
	
                    <TR>
                        <TD align="center" colspan="10" class="tbfooter">&nbsp;</TD>
                    </TR>
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
