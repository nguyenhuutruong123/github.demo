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
	ResultSet nhomskus = obj.getNhomskus();
	ResultSet ddkd = obj.getddkd();
	String view = obj.getView();

	int[] quyen = new  int[6];
	if (view != null && view.length() > 0) {
		quyen = util.Getquyen("BCDonHangBanTrongKy","&view="+view, userId);
	}
	else {
		quyen = util.Getquyen("BCDonHangBanTrongKy","", userId);
	}
%>
<% String nnId = (String)session.getAttribute("nnId");
if(nnId == null) {
	nnId = "vi"; 
}
String url = "";
if (view != null && view.length() > 0) {
	url = util.getChuyenNguUrl("BCDonHangBanTrongKy","&view="+view,nnId);	
}
else {
	url = util.getChuyenNguUrl("BCDonHangBanTrongKy","",nnId);	
}
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>SalesUp - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="../css/bower_components/bootstrap/dist/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="../css/bower_components/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="../css/bower_components/Ionicons/css/ionicons.min.css">
  <!-- jvectormap -->
  <link rel="stylesheet" href="../css/bower_components/jvectormap/jquery-jvectormap.css">
  <!-- datepicker -->
  <link rel="stylesheet" href="../css/bower_components/bootstrap-datepicker/dist/css/bootstrap-datepicker.css">
  <!-- DataTables -->
  <link rel="stylesheet" href="../css/bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="../css/dist/css/AdminLTE.min.css">
  <link rel="stylesheet" href="../css/dist/css/skins/_all-skins.min.css">
   <!-- Select2 -->
 <link href="../css/bower_components/select2/css/select2.min.css" rel="stylesheet" />
  <!-- Google Font -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
  
  <!-- <link rel="stylesheet" href="../css/ladda/dist/ladda-themeless.min.css"> -->
   
   <style>
   body
   {
   	 font-family: Arial, Helvetica, sans-serif;
   	 font-size: 9pt;
   }
   
 	ul.chaomung{
    list-style-type: none;
    margin: 1px;
    padding: 9px;
    overflow: hidden;
    
    font-family: Arial, Helvetica, sans-serif;
	font-size: 9pt;
	font-weight: bold;
	font-style : normal;
	background-color: #80CB9B;
}

ul.chaomung li {
    float: left;
}

ul.chaomung li a {
    display: block;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}

/* Change the link color to #111 (black) on hover */
ul.chaomung li a:hover {
    background-color: #111;
}	
</style>
  
<script type="text/javascript">


function getBaoCao()
{
	alert('a');
	document.forms['frm'].action.value= 'CoBaoCao';
	document.forms["frm"].submit();
}

	function taoKoPivot() 
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
		//document.getElementById("btnSaveKoPivot").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		document.forms['frm'].action.value= 'taoKoPivot';
		document.forms["frm"].submit();
	}
	
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
	
	function submitform3() 
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
		document.forms['frm'].action.value= 'taonew';
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
	
	function DoDuLieu()
	{
		document.frm.action = "../../ChatSvl";
		document.forms['frm'].action.value= 'DoDuLieu';
		document.forms['frm'].submit();
		document.frm.action = "../../BCDonHangBanTrongKy";
	}
	
	
	function load() {
		tontai = $("#checktontai").val();
		$('#form-submit').on("click", function (e) {
			if (document.getElementById("Sdays").value != "" && document.getElementById("Edays").value != "") 
			{
				document.getElementById("form-submit").classList.add("disabled"); 
			}	
		});
	 
		$.ajax({
		  type: "POST",
		  url: "../../ChatSvl?check="+tontai+"",
		  data: "{''}",
		  contentType: "application/json", async: false,
	      success: function (data) {		        
	        $.each(data, function (i, item) {
	        	//console.log('oalal '+data[i].KETQUA);
        		if(data[i].KETQUA == "TRUE" )
	        	{
	        		//console.log("vao rui hahah");
	        		document.getElementById("checktontai").value = "_CHECK";
	        		DoDuLieu();
	        		document.getElementById("form-submit").classList.remove("disabled");
	        	}
			});
          }
		});
		setTimeout(load, 5000);
	}
</script>
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

	<form name="frm" method="post" action="../../BCDonHangBanTrongKy">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
	<input type="hidden" name="linkUrl" value='<%= obj.getUrl()%>'>
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="view" value='<%=view%>'>
	<input type="hidden" name="userId" value='<%=userId%>'>
	<input type="hidden" id="checktontai" name="checktontai" value='<%=obj.getKey()%>'>
	<input type="hidden" name="report_name" value='BCDonHangBanTrongKy'> 
	
<!-- Navigator chao mung -->
	<ul class="chaomung">
	<li><%=url %></li>
	<li style="float:right">Chào mừng bạn <%=userTen %></li>
	</ul>
	
	<div class="box" style="margin-left: 2px; border: 1px solid #dddbdb; background-color: #C5E8CD;">
		<%
		String errors = obj.getMsg()==null?"":obj.getMsg();
		if(errors.length() > 0){ %>
		 <div class="alert alert-warning" id="errors" name="errors" role="alert"><%= errors %></div>
		 <%} %>
		 
		 <div class="box-header">
			<div class="form-group col-md-6">
			  <h3 class="box-title"><%=ChuyenNgu.get("Báo cáo đơn hàng bán trong kỳ",nnId) %></h3>
			</div> 
		</div>
		
		<div class="box-body">
		
			<div class="col-md-6">
			<div class="form-group">
			  <div class="col-md-4"><label><%=ChuyenNgu.get("Từ ngày",nnId) %></label></div> 
	          <input type="text" autocomplete="off" class="days" name="Sdays" id="Sdays" size="20" value = "<%=obj.gettungay()%>" data-date-format="yyyy-mm-dd">
	        </div>
	        </div>
	        
	        <div class="col-md-6">
			<div class="form-group">
			   <div class="col-md-4"><label><%=ChuyenNgu.get("Đến ngày",nnId) %></label></div> 
	           <input type="text" autocomplete="off" class="days" name="Edays" id="Edays" size="20" value = "<%=obj.getdenngay()%>" data-date-format="yyyy-mm-dd">
	        </div>
	        </div>
	        
	        <div class="col-md-6">
			<div class="form-group">
			   <div class="col-md-4"><label><%=ChuyenNgu.get("Vùng/Miền",nnId) %></label></div> 
	           <select class="select2" name="vungId" id="vungId" onchange="seach();" style ="width:200px">
					<option value="" selected>All</option>
					<%if (vung != null)
							while (vung.next()) {
								if (vung.getString("pk_seq").equals(obj.getvungId())) {%>
							<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
						<%} else {%>
							<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
					<%}}%>
				</select>
	        </div>
	        </div>
	        
	        <div class="col-md-6">
			<div class="form-group">
			   <div class="col-md-4"><label><%=ChuyenNgu.get("Khu vực",nnId) %></label></div> 
				<select class="select2" name="khuvucId" id="khuvucId" onchange="seach();" style ="width:200px">
					<option value="" selected>All</option>
					<%if (khuvuc != null)
							while (khuvuc.next()) {
								if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
									<option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
							<%} else {%>
								<option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
							<%}}%>
				</select>
	        </div>
	        </div>
	        
	        <div class="col-md-6">
			<div class="form-group">
			   <div class="col-md-4"><label><%=ChuyenNgu.get("Loại nhà phân phối",nnId) %></label></div> 
				 <select class="select2" name="loainppId" id="loainppId" style ="width:200px">
					<option value="" selected>All</option>
					<%if (loainpp != null)
							while (loainpp.next()) {
								if (loainpp.getString("pk_seq").equals(obj.getLoaiNppId())) {%>
								<option value="<%=loainpp.getString("pk_seq")%>" selected><%=loainpp.getString("ten")%></option>
							<%} else {%>
								<option value="<%=loainpp.getString("pk_seq")%>"><%=loainpp.getString("ten")%></option>
						<% }}%>
				</select>
	        </div>
	        </div>
	        
	        <div class="col-md-6">
			<div class="form-group">
			   <div class="col-md-4"><label><%=ChuyenNgu.get("Nhà phân phối",nnId) %></label></div> 
				<select class="select2" name="nppId" id="nppId" style ="width:200px">
				<%if (view != null && view.equals("TT")) { %>
							<option value=""> </option>
						<% } %>
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
	        </div>
	        </div>
	        
	        <div class="col-md-6">
			<div class="form-group">
			   <div class="col-md-4"><label><%=ChuyenNgu.get("Kênh bán hàng",nnId) %></label></div> 
				<select class="select2" name="kenhId" id="kenhId" onchange="seach();" style="width: 200px">
					<option value="" selected>All</option>
					<%if (kenh != null)
							while (kenh.next()) {
								if (kenh.getString("pk_seq").equals(obj.getkenhId())) {%>
								<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("ten")%></option>
							<%} else {%>
								<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("ten")%></option>
						<% }}%>
				</select>
	        </div>
	        </div>
	        
	        <div class="col-md-6">
			<div class="form-group">
			   <div class="col-md-4"><label><%=ChuyenNgu.get("Nhóm SKU's",nnId) %></label></div> 
				<select class="select2" name="nhomskusId" id="nhomskusId" onchange="seach();" style="width: 200px">
					<option value="" selected>All</option>
					<%if (nhomskus != null)
							while (nhomskus.next()) {
								if (nhomskus.getString("pk_seq").equals(obj.getNhomskusId())) {%>
								<option value="<%=nhomskus.getString("pk_seq")%>" selected><%=nhomskus.getString("ten")%></option>
								<%} else {%>
								<option value="<%=nhomskus.getString("pk_seq")%>"><%=nhomskus.getString("ten")%></option>
								<% }
							}%>
				</select>
	        </div>
	        </div>
	        
	        <%if (view != null && !view.equals("TT")) { %>
	        <div class="col-md-6">
			<div class="form-group">
			   <div class="col-md-4"><label><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></label></div> 
				<select class="select2" name="ddkdId" id="ddkdId" onchange="seach();" style="width: 200px">
					<option value="" selected>All</option>
					<%if (ddkd != null)
							while (ddkd.next()) {
								if (ddkd.getString("pk_seq").equals(obj.getddkdId())) {%>
								<option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
							<%} else {%>
								<option value="<%=ddkd.getString("pk_seq")%>"><%=ddkd.getString("ten")%></option>
						<% }}%>
				</select>
	        </div>
	        </div>
	        <%} %>
	        
	        <div class="col-md-12">
		        <div class="form-group">
		        <div class="col-md-4"><label>
		        <%  if (obj.getnhomspct().equals("1")){%>
				  	<input name="nhomspct" type="checkbox" value ="1" checked>
				<%} else {%>
					<input name="nhomspct" type="checkbox" value ="0">
				<%} %>
				<%=ChuyenNgu.get("Lấy NSP chỉ tiêu",nnId) %></label>
				</div>
		        </div>
	        </div>
	        
	        <div class="col-md-12">
		        <div class="form-group">
		        <div class="col-md-4"> 
		        	<!-- <a href="javascript:taoKoPivot();" id="form-submit" class="btn btn-primary ladda-button" data-style="expand-left" data-size="l"> -->
		        	<!-- <span class="ladda-label">Tạo báo cáo</span></a> -->
		        	
		        	<a href="javascript:taoKoPivot();" id="form-submit" class="btn btn-primary" data-style="expand-left" data-size="l">
		        	Tạo báo cáo</a>
		        	
				</div>
		        </div>
	        </div>
	       	 
		</div>
	</div>
	
		  
		<!-- <script type="text/javascript">
		$("select").css({
			"width": "250px", 
		});
	</script> -->
	</form>
	
	<!-- jQuery 3 -->
	<script src="../css/bower_components/jquery/dist/jquery.min.js"></script>
	<script src="../css/bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
	<script src="../css/bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
	<script src="../css/bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.js"></script>
	<script src="../css/bower_components/select2/js/select2.min.js"></script>
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
	
	<script>
	$(document).ready(function() {
		$('#Sdays').datepicker({
		  autoclose: true
		});

		//Date picker
		$('#Edays').datepicker({
		  autoclose: true
		});
		
		$('.select2').select2(); 
	});
	</script>
	
	<!-- <script src="../css/ladda/dist/spin.min.js"></script>
	<script src="../css/ladda/dist/ladda.min.js"></script> -->
</body> 
<script language="javascript" type="text/javascript"> load(); </script>
</html>
<%
	if( vung != null){ vung.close(); vung = null; }
	if( khuvuc != null)  { khuvuc.close(); khuvuc = null; }
	if( npp != null) { npp.close(); npp = null; }
	if( loainpp != null) { loainpp.close(); loainpp = null; }
	if( nhomskus != null) { nhomskus.close(); nhomskus = null; }
	
	if(obj != null){
		obj = null;		
	}
	session.setAttribute("obj",null);
}%>
