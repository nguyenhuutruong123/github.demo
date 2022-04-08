<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.distributor.beans.report.Ireport"%>
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
	
	Ireport obj = (Ireport)session.getAttribute("obj");
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet npp = obj.getNppRs();
	String nppId = obj.getnppId();
	ResultSet loainpp = obj.getLoaiNppRs();
	ResultSet kenh = obj.getkenh();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("BCDonHangBanSampling","&view="+obj.getView() +"", userId);
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("BCDonHangBanSampling","&view=TT",nnId);	
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
	function submitform() 
	{
		if (document.getElementById("Sdays").value == "") 
		{
			alert("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.getElementById("Edays").value == "")
		{
			alert("Vui lòng chọn ngày kết thúc");
			return ;
		}		
		document.getElementById("btnSave1").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
	}
	function submitform2() 
	{
		if (document.getElementById("Sdays").value == "") 
		{
			alert("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.getElementById("Edays").value == "")
		{
			alert("Vui lòng chọn ngày kết thúc");
			return ;
		}		
		//document.getElementById("btnSave2").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		document.forms['frm'].action.value= 'tao2';
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
	
	
	function load() {
		tontai = $("#checktontai").val();
		
	 $.ajax({
			  type: "POST",
			  url: "../../ChatSvl?check="+tontai+"",
			  data: "{''}",
		    contentType: "application/json",
		    async: false,
			     success: function (data)  {		        
			        $.each(data, function (i, item) {
			        
			        	console.log('oalal '+data[i].KETQUA);
			        	if(data[i].KETQUA == "TRUE" )
			        		{
			        		console.log("vao rui hahah");
			        		document.getElementById("checktontai").value = "_CHECK"
			        			newtab();
			        		document.getElementById("btnSave1").innerHTML = "<a class='button' href='javascript:submitform();'> <img style='top: -4px;' src='../images/button.png'> Tạo báo cáo</a>";
			        		
			        		}
			        	if(document.forms['frm'].linkUrl.value!="" )
			        		{
			        		
			        		document.getElementById("btnSave1").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
			        		
			        		}
			        
			        });
	        		
	        	
			        }

});
	 
	setTimeout(load, 5000);
	
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
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="frm" method="post" action="../../BCDonHangBanSampling">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
	<input type="hidden" name="linkUrl" value='<%= obj.getUrl()%>'>
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="view" value='TT'>
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

					<legend class="legendtitle"> <%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %></legend>
					<textarea id="errors" value="<%= session.getAttribute("errors") %>" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Báo cáo đơn hàng bán trong kỳ",nnId) %></legend>
					<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
										<TD class="plainlabel"><%=ChuyenNgu.get("Từ ngày",nnId) %> </TD>
											<TD class="plainlabel">	<input type="text" autocomplete="off" name="Sdays" id="Sdays" class="days" value='<%= obj.gettungay() %>' style="width:248px;" />
											</TD>
											<TD class="plainlabel"><%=ChuyenNgu.get("Đến ngày",nnId) %> </TD>
											<TD class="plainlabel">
												<input type="text" autocomplete="off" name="Edays" id="Edays" class="days" value='<%= obj.getdenngay() %>' style="width:248px;"/>
											</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Vùng/Miền",nnId) %></TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();" style ="width:200px">
											<option value="" selected>All</option>
											<%if (vung != null)
													while (vung.next()) {
														if (vung.getString("pk_seq").equals(obj.getvungId())) {%>
													<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
												<%} else {%>
													<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %></TD>
									<TD class="plainlabel">
										<select name="khuvucId" id="khuvucId" onchange="seach();" >
											<option value="" selected>All</option>
											<%if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
															<option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
													<%} else {%>
														<option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Loại nhà phân phối",nnId) %></TD>
									<TD class="plainlabel">
										<select name="loainppId" id="loainppId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (loainpp != null)
													while (loainpp.next()) {
														if (loainpp.getString("pk_seq").equals(obj.getLoaiNppId())) {%>
														<option value="<%=loainpp.getString("pk_seq")%>" selected><%=loainpp.getString("ten")%></option>
													<%} else {%>
														<option value="<%=loainpp.getString("pk_seq")%>"><%=loainpp.getString("ten")%></option>
												<% }}%>
										</select>
									</TD>
									
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
									<TD class="plainlabel">
									<SELECT name="nppId" id="nppId">
										<option value=""> </option>
										<% if(npp != null){
											  try
											  { 
												  String optionGroup = "";
												  String optionName = "";
												  int i = 0;
												  
												  while(npp.next())
												  { 
													 if(i == 0)
													 {
														 optionGroup = npp.getString("kvTen");
														 optionName = npp.getString("kvTen");
														 
														 %>
														 
														 <optgroup label="<%= optionName %>" >
													 <% }
													 
													 optionGroup = npp.getString("kvTen");
													 if(optionGroup.trim().equals(optionName.trim()))
													 { %>
														 <% if(npp.getString("nppId").equals(nppId)) {%>
														 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
														 <%} else { %>
														 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
														 <%} %>
													 <% }
													 else
													 {
														 %>
														</optgroup>
														<% optionName = optionGroup; %>
														<optgroup label="<%= optionName %>" >
														<% if(npp.getString("nppId").equals(nppId)) {%>
														 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
														 <%} else { %>
														 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
														 <%} %>
													 <% }
													 i++;
									     	 	  } 
												  %>
												  	</optgroup>
												  <% npp.close(); 
									     	 }catch(java.sql.SQLException e){} } %>	  
                                		</SELECT>
									</TD>									
									
								</TR>
								<TR>
								<TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId) %></TD>
									<TD class="plainlabel">
										<select name="kenhId" id="kenhId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (kenh != null)
													while (kenh.next()) {
														if (kenh.getString("pk_seq").equals(obj.getkenhId())) {%>
														<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("ten")%></option>
													<%} else {%>
														<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("ten")%></option>
												<% }}%>
										</select>
									</TD>
								</TR>
								<TR>
									<TD  class="plainlabel" colspan = "4" ><label>
										<%  if (obj.getnhomspct().equals("1")){%>
										  	<input name="nhomspct" type="checkbox" value ="1" checked>
										<%} else {%>
											<input name="nhomspct" type="checkbox" value ="0">
										<%} %>
									    <%=ChuyenNgu.get("Lấy nhóm sản phẩm chỉ tiêu",nnId) %></label></TD>
										<TD  class="plainlabel" >&nbsp;</TD>

								
								</TR>
								<TR>
									<td > <div id="btnSave1"><a class="button" href="javascript:submitform();"> 
										<img style="top: -4px;" src="../images/button.png" alt=""> <%=ChuyenNgu.get("Tạo báo cáo",nnId) %>
									</a></div></td>
									<td colspan="4" hidden> <div id="btnSave2"><a class="button" href="javascript:submitform2();" > 
										<img style="top: -4px;" src="../images/button.png" alt=""> <%=ChuyenNgu.get("Đơn hàng IP",nnId) %>
									</a></div></td>
									<td></td>
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
	
	if( vung != null){ vung.close(); vung = null; }
	if( khuvuc != null)  { khuvuc.close(); khuvuc = null; }
	if( npp != null) { npp.close(); npp = null; }
	if( loainpp != null) { loainpp.close(); loainpp = null; }
	
	if(obj != null){
		obj = null;		
	}
	
	session.setAttribute("obj",null);

	}%>
	
	