<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.hangcuahang.IHangcuahang" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IHangcuahang hchBean = (IHangcuahang)session.getAttribute("hchBean"); 
   NumberFormat formatter = new DecimalFormat("#,###,###");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout(){
    if(confirm("Ban co muon dang xuat?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 
 
 
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	 function Dinhdang(element)
		{
			element.value=DinhDangTien(element.value);
			if(element.value== '' ||element.value=='0' )
			{
				element.value= '';
			}
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

<form name='hchForm' method="post" action="../../HangcuahangUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= hchBean.getId() %>'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		D??? li???u n???n > Kinh doanh > H???ng kh??ch h??ng > Hi????n thi?? </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%=userTen %>&nbsp;  </TD> 
						    </tr>
   
						   	<tr>
						   		<TD align="left" height="5" colspan="4" class=""></td>
   
  							</tr>
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    					
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = "100%" cellpadding = "3" cellspacing = "0" border = "0">
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">B??o l???i nh???p li???u </LEGEND>
				
		    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= hchBean.getMessage() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>
				
				  	<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Th??ng tin H???ng kh??ch h??ng
								</LEGEND>
								<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
									<TR>
									  <TD width="15%" class="plainlabel required" >H???ng kh??ch h??ng <FONT class="erroralert"></FONT></TD>
									  <TD  class="plainlabel" ><INPUT name="hangcuahang"
										type="text" value='<%= hchBean.getHangcuahang() %>' size="20"></TD>
								  </TR>
									<TR>
									  <TD class="plainlabel required" >Di???n gi???i <FONT class="erroralert"></FONT></TD>
									  <TD class="plainlabel" ><INPUT name="diengiai"
										type="text" value='<%= hchBean.getDiengiai() %>' size="80"></TD>
								  </TR>
								  
								<TR>
									<TD class="plainlabel">T??? m???c</TD>
									<TD class="plainlabel">
										<input type="text" name="tumuc" onkeyup="Dinhdang(this)" value='<%=formatter.format(Double.parseDouble(hchBean.gettumuc()))%>' /> VND</TD>
								</TR>
								<TR>
									<TD class="plainlabel">?????n m???c</TD>
									<td class="plainlabel">
										<input type="text" name="denmuc" onkeyup="Dinhdang(this)" value='<%=formatter.format(Double.parseDouble(hchBean.getdenmuc()))%>' /> VND</td>
								</TR>
								  <TR>
									<TD class="plainlabel">Th??ng trung b??nh</TD>
									<td class="plainlabel">
										<select name = "thangtb" >
										
										<option value = "0" <%if(hchBean.getThangtb().equals("")) {%> selected="selected" <%} %>></option>
										<option value = "1" <%if(hchBean.getThangtb().equals("1")) {%> selected="selected" <%} %>>1 th??ng</option>
										<option value = "3"  <%if(hchBean.getThangtb().equals("3")) {%> selected="selected" <%} %>>3 th??ng</option>
										<option value = "6"  <%if(hchBean.getThangtb().equals("6")) {%> selected="selected" <%} %> >6 th??ng</option>
									
										</select>
										
										</td>
								</TR>
								
									<TR>
									  <TD  class="plainlabel" ><label>
										<%  if (hchBean.getTrangthai().equals("1")){%>
										  	<input name="trangthai" type="checkbox" value ="1" checked>
										<%} else {%>
											<input name="trangthai" type="checkbox" value ="0">
										<%} %>
									    Ho???t ?????ng</label></TD>
										<TD  class="plainlabel" >&nbsp;</TD>
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
<% hchBean.closeDB(); %>
<%}%>