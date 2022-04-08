<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "geso.dms.center.util.*" %>
<%@page import="geso.dms.center.util.ChatSvl"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen"); 	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
	
	IStockintransit obj = (IStockintransit)session.getAttribute("obj");
	ResultSet rsm = obj.bmRs_Create();
	ResultSet asm = obj.asmRs_Create();
	ResultSet gsbhRs = obj.gsbhRs_Create();
	ResultSet kenh = obj.kbhRs_create();
	ResultSet nhanhang = obj.getnhanhang();
	ResultSet chungloai = obj.getchungloai();
	ResultSet npp = obj.nppRs_create();
	String view = obj.getView();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("BCDoanhSoTheoNhanSu","", userId);
%>
<% String nnId = (String)session.getAttribute("nnId");
if(nnId == null) {
	nnId = "vi"; 
}
String url = "";
url = util.getChuyenNguUrl("BCDoanhSoTheoNhanSu","",nnId);	
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>SalesUp - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
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
<script type="text/javascript">
	
	
	
	function submitformNgay() 
	{
		if (document.getElementById("Sdays").value == "")
		{
			alert("Vui lòng chọn ngày từ ngày");
			return ;
		}	
		if (document.getElementById("Edays").value == "")
		{
			alert("Vui lòng chọn ngày kết thúc");
			return ;
		}		
		document.forms['frm'].action.value= 'taoNgay';
		document.forms["frm"].submit();
	}

	function seach()
	{
		document.forms['frm'].action.value= 'seach';
		document.forms["frm"].submit();
	}
	
	function newtab()
	{    
		var tt = document.forms['frm'].linkUrl.value;
		
		if(tt.length>0)
	    {
			 var win = window.open(tt, '_blank');
			  win.focus(); 
	    }
		
		document.forms['frm'].linkUrl.value= ''; 
		
		
	}
	
	
	
	
</script>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select").select2(); 
    	});
     
 	</script>	

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="frm" method="post" action="../../BCDoanhSoTheoNhanSu">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
	<input type="hidden" name="linkUrl" value='<%= obj.getUrl()%>'>
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="view" value='<%=view%>'>
	<input type="hidden" name="userId" value='<%=userId%>'>
	<input type="hidden" id="checktontai" name="checktontai" value='<%=obj.getKey()%>'>
	

	
	
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=" "+url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng bạn",nnId) %> <%= userTen %></div>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>

					<legend class="legendtitle"><%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %> </legend>
					<textarea id="errors" value="<%= session.getAttribute("errors") %>" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("DOANH SỐ THEO NHÂN SỰ",nnId) %></legend>
					<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
							
									<TD class="plainlabel"><%=ChuyenNgu.get("Từ ngày",nnId) %> </TD>
									<TD class="plainlabel">
										<input AUTOCOMPLETE="off" type="text" name="Sdays" id="Sdays" class="days" value='<%= obj.gettungay() %>' style="width:248px;"/>
									</TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Đến ngày",nnId) %> </TD>
									<TD class="plainlabel">
										<input AUTOCOMPLETE="off" type="text" name="Edays" id="Edays" class="days" value='<%= obj.getdenngay() %>' style="width:248px;"/>
									</TD>
								</TR>
								
								<TR>
								
									<TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId) %></TD>
									<TD class="plainlabel">
										<select name="kenhId" id="kenhId" onchange="seach();" style ="width:200px">
											<option value="" selected>All</option>
											<%if (kenh != null)
													while (kenh.next()) {
														if (kenh.getString("pk_seq").equals(obj.getkenhId())) {%>
													<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("ten")%></option>
												<%} else {%>
													<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
								
									<TD class="plainlabel"><%=ChuyenNgu.get("RSM",nnId) %></TD>
									<TD class="plainlabel">
										<select name="bmId" id="bmId" onchange="seach();" style ="width:200px">
											<option value="" selected>All</option>
											<%if (rsm != null)
													while (rsm.next()) {
														if (rsm.getString("pk_seq").equals(obj.getBMId())) {%>
													<option value="<%=rsm.getString("pk_seq")%>" selected><%=rsm.getString("ten")%></option>
												<%} else {%>
													<option value="<%=rsm.getString("pk_seq")%>"><%=rsm.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("ASM",nnId) %></TD>
									<TD class="plainlabel">
										<select name="asmId" id="asmId" onchange="seach();" >
											<option value="" selected>All</option>
											<%if (asm != null)
													while (asm.next()) {
														if (asm.getString("pk_seq").equals(obj.getASMId())) {%>
															<option value="<%=asm.getString("pk_seq")%>" selected><%=asm.getString("ten")%></option>
													<%} else {%>
														<option value="<%=asm.getString("pk_seq")%>"><%=asm.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Giám sát bán hàng",nnId) %></TD>
									<TD class="plainlabel">
										<select name="ssId" id="ssId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (gsbhRs != null)
													while (gsbhRs.next()) {
														if (gsbhRs.getString("pk_seq").equals(obj.getgsbhId())) {%>
														<option value="<%=gsbhRs.getString("pk_seq")%>" selected><%=gsbhRs.getString("ten")%></option>
													<%} else {%>
														<option value="<%=gsbhRs.getString("pk_seq")%>"><%=gsbhRs.getString("ten")%></option>
												<% }}%>
										</select>
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
									<TD class="plainlabel">
										<select  onchange="seach();"  name="nppId">
											<option value="">All</option>
											<% if(npp!=null){
													while(npp.next()){
														String nppId = npp.getString(1);
														if(nppId.equals(obj.getnppId())){%>
															<option selected="selected" value="<%=npp.getString(1)%>"><%=npp.getString(2)%></option>
														<%}else{%>
														<option value="<%=npp.getString(1)%>"><%=npp.getString(2)%></option>
											<%}}}%>
										</select>
									</TD>	
								</TR>
								
									
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhãn hàng",nnId) %></TD>
									<TD class="plainlabel">
										
										<select name="nhanhangId" id="nhanhangId" onchange="seach();" style ="width:200px">
											<option value="">All</option>
											<% if(nhanhang!=null){
													while(nhanhang.next()){
														
														if(nhanhang.getString("pk_seq").equals(obj.getnhanhangId())){%>
															<option selected="selected" value="<%=nhanhang.getString("pk_seq")%>"><%=nhanhang.getString("ten")%></option>
														<%}else{%>
														<option value="<%=nhanhang.getString("pk_seq")%>"><%=nhanhang.getString("ten")%></option>
											<%}}}%>
										</select>
									</TD>	
									<TD class="plainlabel"><%=ChuyenNgu.get("Chủng loại",nnId) %></TD>
									<TD class="plainlabel">
										<select  onchange="seach();"  name="chungloaiId">
											<option value="">All</option>
											<% if(chungloai!=null){
													while(chungloai.next()){
														
														if(chungloai.getString("pk_seq").equals(obj.getchungloaiId())){%>
															<option selected="selected" value="<%=chungloai.getString("pk_seq")%>"><%=chungloai.getString("ten")%></option>
														<%}else{%>
														<option value="<%=chungloai.getString("pk_seq")%>"><%=chungloai.getString("ten")%></option>
											<%}}}%>
										</select>
									</TD>	
								</TR>
								
							
								
								<TR>
								
									
								
								
									
									<td > <div id="btnSave1"><a class="button" href="javascript:submitformNgay();"> 
										<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Tạo báo cáo",nnId) %> 
									</a></div></td>
									
									
									
								</TR>
							</TABLE>
						</div>
						
				</fieldset>
			</div>
		</div>
		<br />
		<script type="text/javascript">
		$("select").css({
			"width": "250px", 
			//"height": "200px"
		});
	</script>
		
	</form>
</body> 

<script language="javascript" type="text/javascript">
	load();
</script>	
 
</html>
<%
	
	
	obj.DBclose();
	
	if(obj != null){
		obj = null;		
	}
	
	session.setAttribute("obj",null);

	}%>
	
	