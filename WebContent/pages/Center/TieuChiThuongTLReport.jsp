<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
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
		response.sendRedirect("/VIETDUC/index.jsp");
	}else{ %>
<%	
	IStockintransit obj = (IStockintransit)session.getAttribute("obj");
	ResultSet vung = obj.vungRs_create();
	ResultSet khuvuc = obj.khuvucRs_create();
	ResultSet npp = obj.nppRs_create();
	ResultSet scheme = obj.getTieuchithuongTlRs();
	
	String url= util.getUrl("TieuchithuongTLReportSvl","");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	
	<TITLE><%= getServletContext().getInitParameter("TITLENAME") %></TITLE>
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

	function seach()
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
$(document).ready(function()
{
	$("#nppId").select2();
	$("#vungId").select2();
	$("#khuvucId").select2();
	$("#nhanhangId").select2();
	$("#chungloaiId").select2();
	$("#schemeId").select2();
	$("#dvkdId").select2();
	$("#gsbhId").select2();
	$("#kenhId").select2();
});
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tcsku" method="post" action="../../TieuchithuongTLReportSvl">
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
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp; Quản lý khuyến mại > Báo cáo > Thưởng KM tích lũy theo mức </TD>  
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
                            <LEGEND class="legendtitle">&nbsp;Theo dõi tích lũy &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                          
							
							 <TR id="TheoNgay" >
								<TD class="plainlabel" width="10%">Từ ngày</TD>
								<TD class="plainlabel">
									<input type="text" name="Sdays"	class="days" value='<%= obj.gettungay() %>' onchange="seach();" />
									&nbsp;&nbsp;&nbsp;&nbsp;
									Đến ngày
									&nbsp;&nbsp;
									<input type="text" name="Edays" class="days" value='<%= obj.getdenngay() %>' onchange="seach();" />
								</TD>
							</TR>
                             
                             <TR>
	                             <TD class="plainlabel" >Mã CTKM </TD>
								 <TD class="plainlabel" >
									<select name="schemeId" id="schemeId" style="width:800px" multiple="multiple" >
										<option value=""></option>
										<%if (scheme != null){
												while (scheme.next()) {
													if (obj.getcttbId().contains( scheme.getString("pk_seq")) ) {%>
														<option value="<%= scheme.getString("pk_seq")%>" selected><%= scheme.getString("scheme") +"-" + scheme.getString("diengiai")%></option>
												<%} else {%>
													<option value="<%= scheme.getString("pk_seq")%>"><%= scheme.getString("scheme") +"-" + scheme.getString("diengiai")%></option>
												<%} } } %>
									</select>
								</TD>
							</TR>
                             
                             <TR>
                             <TD class="plainlabel" >Vùng miền </TD>
							 <TD class="plainlabel">
								<select name="vungId" id="vungId" onchange="seach();" style="width:202px" >
										<option value="" selected>All</option>
										<%if (vung != null) 
										{
												while (vung.next()) {
													if (vung.getString("pk_seq").equals(obj.getvungId())) {%>
												<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
											<%} else {%>
												<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
										<% } } } %>
									</select>
							 </TD>
                             </TR >
                             <TR>
                             <TD class="plainlabel" >Khu vực </TD>
							 <TD class="plainlabel" >
									<select name="khuvucId" id="khuvucId" onchange="seach();"  style="width:202px" >
										<option value="" selected>All</option>
										<%if (khuvuc != null)
										{
											while (khuvuc.next()) {
											if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
												<option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
										<% } else {%>
											<option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
										<% } } }%>
									</select>
							</TD>
                             </TR >
                             <TR>
                             <TD class="plainlabel" >Chi nhánh / NPP </TD>
							 <TD class="plainlabel" >
								<select name="nppId" id="nppId" style="width:202px" >
									<option value="" selected>All</option>
									<%if (npp != null){
											while (npp.next()) {
												if (npp.getString("pk_seq").equals(obj.getnppId())) {%>
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
</BODY>
</html>

<%}%>