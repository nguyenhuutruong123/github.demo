<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.khoahuanluyen.IKhoahuanluyenList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>


<%	
	IKhoahuanluyenList obj = (IKhoahuanluyenList) session.getAttribute("obj");	
	ResultSet npps = obj.getNppRs();
	ResultSet vungs = obj.getVungRs();
	ResultSet khuvucs = obj.getKhuvucRs();
	ResultSet kenhs = obj.getKbhRs();
	ResultSet khoahvRs = obj.getKhoahuanluyenRs();
	
	int[] quyen = new  int[6];
	quyen = util.Getquyen("BCKhoaHuanLuyenNV","&view="+obj.getView()+"", userId);
	
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
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
	 <style type="text/css">

	#table tbody tr input:HOVER{
		background: #C5E8CD;
	}
	#table tbody tr:hover{
		background: #C5E8CD;
	}
	</style>
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


<script language="javascript" type="text/javascript">
	
	function Chontatca(element)
	{
		if(element.checked==true)
		{
			var ngay=document.getElementsByName('ngayquydinh');
			for(var i=1;i<ngay.length;i++)
				{
					ngay[i].value=ngay[0].value;
				}
		}
	}

	function seach() 
	{
		document.forms['frm'].action.value = 'seach';
		document.forms["frm"].submit();
	}
	function submitform()
	{
		if(document.getElementById("tungay").value == "")
		{
			alert('Vui lòng nhập ngày tồn kho cần lấy');
			return;
		}
		document.forms['frm'].errors.value= '';
		document.forms['frm'].action.value = 'Taomoi';
		document.forms["frm"].submit();
	}
	
	function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var khlIds = document.getElementsByName("khlIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < khlIds.length; i++)
			 {
				 khlIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < khlIds.length; i++)
			 {
				 khlIds.item(i).checked = false;
			 }
		 }
	 }

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

	<form name="frm" method="post" action="../../BCKhoaHuanLuyenNV">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="view" value='TKQD'> 
		<input type="hidden" name="userId" value='<%= obj.getUserId() %>'>
			
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý chỉ tiêu &gt; Chỉ tiêu NPP &gt; Tồn theo quy định </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= obj.getUserTen() %></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea id="errors" name="errors" rows="2"  style="width: 100%;text-align: left; color:#F00 ; font-weight:bold">
						<%= obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left;font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Tồn kho theo quy định</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel">
										<input class="days" type="text" name="tungay" id="tungay" value="<%= obj.getTungay() %>" size="20" >
									</TD>
									
									<TD class="plainlabel">Đến ngày</TD>
									<TD class="plainlabel">
										<input class="days" type="text" name="denngay" id="denngay" value="<%= obj.getDenngay() %>" size="20" >
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel">Vùng/Miền</TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (vungs != null)
													while (vungs.next()) {
														if (vungs.getString("pk_seq").equals(obj.getVungId())) {%>
													<option value="<%=vungs.getString("pk_seq")%>" selected><%=vungs.getString("ten")%></option>
												<%} else {%>
													<option value="<%=vungs.getString("pk_seq")%>"><%=vungs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Khu vực</TD>
									<TD class="plainlabel">
										<select name="khuvucId" id="khuvucId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (khuvucs != null)
													while (khuvucs.next()) {
														if (khuvucs.getString("pk_seq").equals(obj.getKvId())) {%>
															<option value="<%=khuvucs.getString("pk_seq")%>" selected><%=khuvucs.getString("ten")%></option>
													<%} else {%>
														<option value="<%=khuvucs.getString("pk_seq")%>"><%=khuvucs.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Kênh bán hàng</TD>
									<TD class="plainlabel" >
										<select name="kenhId" id="kenhId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (kenhs != null)
													while (kenhs.next()) {
														if (kenhs.getString("pk_seq").equals(obj.getKbhId())) {%>
														<option value="<%= kenhs.getString("pk_seq")%>" selected><%= kenhs.getString("diengiai") %></option>
											<%} else { %>
												<option value="<%= kenhs.getString("pk_seq")%>"><%= kenhs.getString("diengiai") %></option>
											<%}}%>
										</select>
									</TD>
									
									<TD class="plainlabel">
								Tính theo giá
								</TD>
								<TD class="plainlabel">
								<% String[]	 trangthai = new  String[] {"Gía mua","Gía bán"  } ;
									String[] idTrangThai = new  String[] {"0","1"} ;
								%> 
								<SELECT name="gia" >
								<option> </option>
				      		 <% for( int i=0;i<trangthai.length;i++) { 
				    			if(idTrangThai[i].equals(obj.getGia()) ){ %>
				      				<option value='<%=idTrangThai[i]%>' selected><%=trangthai[i] %></option>
				      		 	<%}else{ %>
				     				<option value='<%=idTrangThai[i]%>'><%=trangthai[i] %></option>
				     			<%} 
				      		 }
				      		 	%>
				      		 	</SELECT>
		      		 	
								</TD>
									
								</TR>
								
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo </a></td>
								</TR>
							</TABLE>
							</div>
							<hr />
							
							<div style="width: 100%">
		                    <table id="table" width="100%" align="center" cellpadding="0" cellspacing="1">
		                        <tr class="plainlabel" style="padding: 3px">
		                            <th width="15%" align="center" >Mã NPP</th>
		                            <th width="37%" align="center" >Tên NPP</th>
		                            <th width="40%" align="center">Địa chỉ</th>
		                            <th width="8%" align="center">Gía trị tồn kho<input type="checkbox" id="chonhet" onchange="Chontatca(this)"></input></th>
		                        </tr>
                            	<%
	                        		if(npps != null)
	                        		{
	                        			String lightrow = "tblightrow";
	    								String darkrow = "tbdarkrow";
	    								int m=0;
	                        			while(npps.next())
	                        			{  if (m % 2 != 0) {%>						
										<TR  class= <%=lightrow%> >
									<%} else {%>
										<TR   class= <%= darkrow%> >
									<%}%>
	                        			
	                        			
	                        				<td>
	                        					<input style="width: 100%; display: none" name="nppIds" value="<%= npps.getString("pk_seq") %>">
	                        					<input  style="width: 100%;;background-color: transparent;border: none;" value="<%= npps.getString("ma") %>" readonly="readonly">
	                        				</td>
	                        				<td><input style="width: 100%;;background-color: transparent;border: none;" value="<%= npps.getString("ten") %>" readonly="readonly"></td>
	                        				<td><input style="width: 100%;;background-color: transparent;border: none;" value="<%= npps.getString("diachi") %>" readonly="readonly"></td>
	                        				<td><input style="width: 98%; text-align: right;" name="ngayquydinh" ></td>
	                        			</TR>
	                        			
	                        		 <% m++; } npps.close(); } %>
		                    </table>
		                    </div>
					</div>
				</fieldset>
			</div>
		</div>
	</form>
<%
	if(kenhs !=null){ kenhs.close(); kenhs= null;}
	if(vungs !=null){ vungs.close(); vungs= null;}
	if(khuvucs !=null){ khuvucs.close(); khuvucs= null;}
	if(npps !=null){ npps.close(); npps= null;}
	if(khoahvRs !=null){ khoahvRs.close(); khoahvRs= null;}
	
	if(obj!=null)
	{
		obj.DbClose();
		obj = null;
	}
	session.setAttribute("obj", null);
}
%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>