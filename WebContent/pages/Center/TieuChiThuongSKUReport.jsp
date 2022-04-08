<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.dms.center.beans.tieuchithuong.*"%>
<%@page import="geso.dms.center.beans.tieuchithuong.imp.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%	
	ITieuchithuongSKUList obj=(ITieuchithuongSKUList)session.getAttribute("obj");
	ResultSet vung = obj.getVungRs();
	ResultSet khuvuc = obj.getKhuvucRs();
	ResultSet npp = obj.getNppRs();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("TieuchithuongSKUReportSvl","", userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	
	<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
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
	 
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	    document.forms['tcsku'].nam.value= "";
	    document.forms['tcsku'].thang.value= "";
		document.forms['tcsku'].submit();
	}

	function submitform()
	{
		document.forms['tcsku'].action.value= 'search';
		document.forms['tcsku'].submit();
	}

	function Taobaocao()
	{
		document.forms['tcsku'].action.value= 'taobc';
		document.forms['tcsku'].submit();
	}
	
	function LayTheoNgay()
	{
		document.getElementById("TheoThang").style.display = "none";
		document.getElementById("TheoNgay").style.display = "";
	}
	
	function LayTheoThang()
	{
		document.getElementById("TheoNgay").style.display = "none";
		document.getElementById("TheoThang").style.display = "";
	}
	
	</SCRIPT>
	
	
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

<form name="tcsku" method="post" action="../../TieuchithuongSKUReportSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="task" value="TT" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp; Quản lý chỉ tiêu > Báo cáo > Thưởng SKU tập trung </TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
                        </tr>
                    </TABLE>
                </TD>
            </TR>
        </TABLE>
        <TABLE width="100%" cellpadding="0" cellspacing="1">
            <TR>
                <TD>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
	                    	<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>			
				    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" readonly="readonly" rows="2"><%= obj.getMsg()  %></textarea>
										<% obj.setMsg(""); %>
									</FIELDSET>
							   </TD>
							</tr>
	                        <TR>
                            <TD width="100%" align="center" >
                            <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;Thưởng SKU tập trung &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                            <TR>
	                            <TD class="plainlabel" style="width: 120px">Xem theo</TD>
	                            <TD class="plainlabel" colspan="3">
								<% if(obj.getType().equals("1")){  %>
										<input type="radio" name="xemtheo" onchange="LayTheoNgay();" value="0" />Ngày &nbsp;&nbsp;&nbsp;
										<input type="radio" name="xemtheo" onchange="LayTheoThang();" value="1" checked="checked"/>Tháng
									<%}else{ %>
										<input type="radio" name="xemtheo" onchange="LayTheoNgay();" value="0" checked="checked" />Ngày &nbsp;&nbsp;&nbsp;
										<input type="radio" name="xemtheo" onchange="LayTheoThang();" value="1" />Tháng
									<%} %> 
								</TD>
							</TR>
							
							<% if(obj.getType().equals("1")) { %>
                             <TR id="TheoNgay" style="display: none">
								<TD class="plainlabel">Từ ngày</TD>
								<TD class="plainlabel">
									<input type="text" name="Sdays"	class="days" value='<%= obj.getTungay() %>' />
									&nbsp;&nbsp;&nbsp;&nbsp;
									Đến ngày
									&nbsp;&nbsp;
									<input type="text" name="Edays" class="days" value='<%= obj.getDenngay() %>' />
								</TD>
							</TR>
							
							<TR id="TheoThang">
                            	<TD class="plainlabel" >Tháng </TD>
								<TD class="plainlabel" >
									<select name="thang" style="width: 50px">
									<option value=0> </option>  
									<%
									int k=1;
									for(k=1; k <= 12; k++ )
									{	
									  	if(obj.getThang().equals(Integer.toString(k)) ) {
										%>
											<option value=<%= k %> selected="selected" > <%= k %></option> 
										<% } else{  %>
											<option value=<%= k %> > <%= k %></option> 
										<% } }%>
									</select>
									
									&nbsp;&nbsp;&nbsp;&nbsp;
									Năm
									&nbsp;&nbsp;
									
									<select name="nam"  style="width :50px">
									<option value=0> </option>  
									<%
									Calendar cal = Calendar.getInstance();
									int year_= cal.get(Calendar.YEAR);
									for(int n= 2009; n < year_+3; n++) {
									  if(obj.getNam().equals( Integer.toString(n)) ){									  
									%>
										<option value=<%= n %> selected="selected" > <%= n %></option> 
									<%
									  }else{
									 %>
										<option value=<%= n %> ><%= n %></option> 
									<% } }
									%>
									</select>
								 </TD>
                             </TR>
                             <%} else { %> 
                             	 <TR id="TheoNgay" >
								<TD class="plainlabel">Từ ngày</TD>
								<TD class="plainlabel">
									<input type="text" name="Sdays"	class="days" value='<%= obj.getTungay() %>' />
									&nbsp;&nbsp;&nbsp;&nbsp;
									Đến ngày
									&nbsp;&nbsp;
									<input type="text" name="Edays" class="days" value='<%= obj.getDenngay() %>' />
								</TD>
							</TR>
							
							<TR id="TheoThang" style="display: none">
                            	<TD class="plainlabel" >Tháng </TD>
								<TD class="plainlabel" >
									<select name="thang" style="width: 50px">
									<option value=0> </option>  
									<%
									int k=1;
									for(k=1; k <= 12; k++ )
									{	
									  	if(obj.getThang().equals(Integer.toString(k)) ) {
										%>
											<option value=<%= k %> selected="selected" > <%= k %></option> 
										<% } else{  %>
											<option value=<%= k %> > <%= k %></option> 
										<% } }%>
									</select>
									
									&nbsp;&nbsp;&nbsp;&nbsp;
									Năm
									&nbsp;&nbsp;
									
									<select name="nam"  style="width :50px">
									<option value=0> </option>  
									<%
									Calendar cal = Calendar.getInstance();
									int year_= cal.get(Calendar.YEAR);
									for(int n= 2009; n < year_+3; n++) {
									  if(obj.getNam().equals( Integer.toString(n)) ){									  
									%>
										<option value=<%= n %> selected="selected" > <%= n %></option> 
									<%
									  }else{
									 %>
										<option value=<%= n %> ><%= n %></option> 
									<% } }
									%>
									</select>
								 </TD>
                             </TR>
                             <%} %>
                             <TR>
                             <TD class="plainlabel" >Miền </TD>
							 <TD class="plainlabel">
								<select name="vungId" id="vungId" onchange="seach();">
										<option value="" selected>All</option>
										<%if (vung != null) 
										{
												while (vung.next()) {
													if (vung.getString("pk_seq").equals(obj.getVungId())) {%>
												<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
											<%} else {%>
												<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
										<% } } } %>
									</select>
							 </TD>
                             </TR >
                             <TR>
                             <TD class="plainlabel" >Vùng </TD>
							 <TD class="plainlabel" >
									<select name="khuvucId" id="khuvucId" onchange="seach();">
										<option value="" selected>All</option>
										<%if (khuvuc != null)
										{
											while (khuvuc.next()) {
											if (khuvuc.getString("pk_seq").equals(obj.getKvId())) {%>
												<option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
										<% } else {%>
											<option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
										<% } } }%>
									</select>
							</TD>
                             </TR >
                             <TR>
                             <TD class="plainlabel" >Nhà phân phối </TD>
							 <TD class="plainlabel" >
								<select name="nppId" >
									<option value="" selected>All</option>
									<%if (npp != null){
											while (npp.next()) {
												if (npp.getString("pk_seq").equals(obj.getNppIds())) {%>
													<option value="<%= npp.getString("pk_seq")%>" selected><%= npp.getString("ten")%></option>
											<%} else {%>
												<option value="<%= npp.getString("pk_seq")%>"><%= npp.getString("ten")%></option>
											<%} } } %>
								</select>
							</TD>
                            
                             <tr class="plainlabel"> <td colspan="4" > 
                             <a class="button3" href="javascript:Taobaocao()">
                           	 <img style="top: -4px;" src="../images/button.png" alt="">Tạo báo cáo </a> &nbsp;&nbsp;&nbsp;
                             </td> </tr>

                            </TABLE>

                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE>
                    </TD>
                </TR>
        </TABLE>
        </TD>
    </TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
	
<%
	if(vung != null) { vung.close(); vung = null ;}
	if(khuvuc != null) { khuvuc.close(); khuvuc = null ;}
	if(npp != null) { npp.close(); npp = null ;}
	
	if(obj != null){
		obj = null;		
	}
	session.setAttribute("obj",null);

}
%>