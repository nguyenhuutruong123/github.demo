<%@page import="geso.dms.center.util.Utility"%>
<%@page import="geso.dms.center.beans.report.ITheodoiTichluy"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
	
<%
	
	ITheodoiTichluy obj = (ITheodoiTichluy)session.getAttribute("obj");
	ResultSet rsKhuVuc = obj.getkhuvucRs();
	ResultSet rsVung = obj.getvungRs();
	ResultSet rsNpp = obj.getNppRs();
	ResultSet ddkdRs = obj.getDdkdRs();
	ResultSet ctkmRs = obj.getPromotionRs();
	int[] quyen = new  int[6];
	quyen = util.Getquyen("BCTheodoiTichluySvl","", userId);
%>
<% String nnId = (String)session.getAttribute("nnId"); %>
<% if(nnId == null) {
 nnId = "vi";  
 }
 String url = util.getChuyenNguUrl("BCTheodoiTichluySvl","" ,nnId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>SGP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
		$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
	function search(){
		document.forms["frm"]["action"].value = "search";
		document.forms["frm"].submit();
	}
	function submitform() {
		/* if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return ;
		} */			
				
		document.forms["frm"]["action"].value = "Taomoi";
		document.forms["frm"].submit();
		reset();
	}
	
	function submitform1() {
		/* if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return ;
		} */			
				
		document.forms["frm"]["action"].value = "Taomoi1";
		document.forms["frm"].submit();
		reset();
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");		
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}		
	};
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

	<form name="frm" method="post" action="../../BCTheodoiTichluySvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" value="1" name="action"  >
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng",nnId) %> <%= obj.getuserTen() %> </div>
			</div>
			
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> <%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %></legend>
					<textarea id="errors" 	name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
					<%= obj.getMsg() %>
					</textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"></legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<%-- <TR>
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel"><input type="text" name="Sdays"
										class="days" value="<%=obj.gettungay() %>" /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<TD class="plainlabel"><input type="text" name="Edays"
										class="days" value="<%=obj.getdenngay() %>" /></TD>
								</TR> --%>
								
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Vùng/Miền",nnId) %></TD>
									<TD class="plainlabel">
										<select onchange="search();" name="vungId" style="width: 300px">
											<option value=""><%=ChuyenNgu.get("All",nnId) %></option>
											<% if(rsVung!=null){
													while(rsVung.next()){
														String vungId = rsVung.getString(1);
														if(vungId.equals(obj.getvungId())){%>
															<option selected="selected"  value="<%=rsVung.getString(1) %>"><%=rsVung.getString(2)%></option>
														<%}else{%>
														<option  value="<%=rsVung.getString(1) %>"><%=rsVung.getString(2)%></option>
															
											<%}}}%>										
										</select>
									</TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %></TD>
									<TD class="plainlabel">
										<select onchange="search();"  name="khuvucId">
											<option value=""><%=ChuyenNgu.get("All",nnId) %></option>
											<% if(rsKhuVuc!=null){
													while(rsKhuVuc.next()){
														String khuvucId = rsKhuVuc.getString(1);
														if(khuvucId.equals(obj.getkhuvucId())){%>
															<option selected="selected" value="<%=rsKhuVuc.getString(1)%>"><%=rsKhuVuc.getString(2)%></option>
														<%}else{%>
														<option value="<%=rsKhuVuc.getString(1)%>"><%=rsKhuVuc.getString(2)%></option>
											<%}}}%>
										</select>
									</TD>
								</TR>
								<TR>
									
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
									<TD class="plainlabel">
										<select onchange="search();"  name="nppId" style="width: 300px">
											<option value=""><%=ChuyenNgu.get("All",nnId) %></option>
											<% if(rsNpp!=null){
													while(rsNpp.next()){
														if(rsNpp.getString("PK_SEQ").equals(obj.getnppId())){%>
															<option selected="selected" value="<%=rsNpp.getString("PK_SEQ")%>"><%=rsNpp.getString("TEN")%></option>
														<%}else{%>
														<option value="<%=rsNpp.getString("PK_SEQ")%>"><%=rsNpp.getString("TEN")%></option>
											<%}}}%>
										</select>
									</TD>	
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></TD>
									<TD class="plainlabel">
										<select name="ddkdId">
											<option value=""><%=ChuyenNgu.get("All",nnId) %></option>
											<% if(ddkdRs != null){
												while(ddkdRs.next()){
													if(ddkdRs.getString("PK_SEQ").equals(obj.getDdkdId())){%>
														<option selected="selected" value="<%=ddkdRs.getString("PK_SEQ")%>"><%=ddkdRs.getString("TEN")%></option>
													<%}else{%>
														<option value="<%=ddkdRs.getString("PK_SEQ")%>"><%=ddkdRs.getString("TEN")%></option>
											<%}}}%>
										</select>
									</TD>									
								</TR>
								<tr>
									<TD class="plainlabel"><%=ChuyenNgu.get("Chương trình khuyến mãi",nnId) %></TD>
									<TD class="plainlabel">
										<select  name="programs" multiple="multiple">
											<% if(ctkmRs!=null){
										 		while(ctkmRs.next()){
													if(obj.getpromotion().indexOf(ctkmRs.getString("PK_SEQ")) >= 0){%>
														<option selected="selected" value="<%=ctkmRs.getString("PK_SEQ")%>"><%=ctkmRs.getString("SCHEME")%></option>
													<%}else{%>
														<option value="<%=ctkmRs.getString("PK_SEQ")%>"><%=ctkmRs.getString("SCHEME")%></option>
										 	<% }}}%>
										 	
										</select>
									</TD>
								</TR>

								
								<TR>
									<td colspan="1"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> <%=ChuyenNgu.get("Tạo báo cáo",nnId) %>
									</a></td>
								
									<td colspan="2"><a class="button"
										href="javascript:submitform1();"> <img style="top: -4px;"
											src="../images/button.png" alt=""><%=ChuyenNgu.get("Báo cáo theo dõi tích lũy giai đoạn",nnId) %> 
									</a></td>
								</TR>
							</TABLE>
						</div>
						<hr>
						
						
					</div>
				</fieldset>
			</div>
		</div>
		<br /> <br /> <br /> <br />		
	</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<%
	try
	{
		if(rsKhuVuc != null){ rsKhuVuc.close();	rsKhuVuc = null;}
		if(rsVung != null){ rsVung.close();	rsVung = null;}
		if(rsNpp != null){ rsNpp.close();	rsNpp = null;}
		if(ddkdRs != null){ ddkdRs.close();	ddkdRs = null;}
		if(ctkmRs != null){ ctkmRs.close();	ctkmRs = null;}
		
		if(obj != null) {
			obj.DBclose();		
			obj = null;
		}
		session.setAttribute("obj",null);
	}catch(java.sql.SQLException e){}
}
%>
