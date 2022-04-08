<%@page import="com.cete.dynamicpdf.text.bd"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.bandott.IBandott" %>
<%@ page  import = "geso.dms.center.beans.bandott.imp.Bandott" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IBandott khBean = (IBandott)session.getAttribute("obj"); %>
<% ResultSet vungRs = khBean.getVungRs(); %>
<% ResultSet kvRs = khBean.getKvRs(); %>
<% ResultSet nppRs = khBean.getNppRs(); %>
<% ResultSet DvkdRs = khBean.getDvkdRs();%>
<% ResultSet ddkd = khBean.getDdkdRs(); %>
<% ResultSet ma_tenkhRs = khBean.getMa_tenkhRs(); %>
<% ResultSet tbh = khBean.getTbhRs(); %>
<% ResultSet khlist = khBean.getKhChuaViengThamRs(); 

	String isTT = khBean.getIsTT();
	int[] quyen = new  int[6];
	String param = "";
	if (isTT != null && isTT.equals("1")) {
		param = "&view="+khBean.getView()+"&isTT="+isTT;
	}
	else { //chưa dùng trường hợp này, tính sau
		param = "&view="+khBean.getView();
	}
	quyen = util.Getquyen("BandoTTSvl",param, userId);
	
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% 
if(nnId == null) {
	nnId = "vi"; 
}
//String url = util.getChuyenNguUrl("BandoTTSvl","&view=khachhangToado",nnId);
String url = util.getChuyenNguUrl("BandoTTSvl","&view=khachhangToado&isTT=1",nnId);

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>AHF</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 	<META http-equiv="Content-Style-Type" content="text/css"> -->

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
   
   <style>
 	ul.chaomung{
    list-style-type: none;
    margin: 1px;
    padding: 9px;
    overflow: hidden;
    
    font-family: Roboto, Arial, Helvetica, sans-serif;
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

.colorheader
{
	background-color: #80CB9B;
}
 	
 </style>
    
    <script type="text/javascript">

		function xtdnhieukh()
	  	{
	  		document.forms['bdForm'].action.value= 'xtdnhieukh';
	  		document.forms["bdForm"].submit();
	  	}
	    function anhdaidien()
	    {
	    	document.forms['bdForm'].action.value= 'xoaanhnhieu';
	  		document.forms["bdForm"].submit();
	    }
	    
	    function getdata(){
	    	readyFunc();
	    }
    
 	  	function xoatoado(khId) {
			if(confirm("Bạn có chắc chắn muốn xóa tọa độ khách hàng này không ?") && typeof(khId) !== "undefined") {
	  			$("#khId").val(khId);
	  			document.forms["bdForm"].submit();
	  		}
		} 
		function xoaanh(khId) {
			if(confirm("Bạn có chắc chắn muốn ảnh ảnh đại diện  ?") && typeof(khId) !== "undefined") {
	  			$("#khId").val(khId);
	  			document.forms['bdForm'].action.value= 'xoaanh';
	  			document.forms["bdForm"].submit();
	  		}
		}
	  	function submitform()
		{
		    document.forms["bdForm"].submit();
		}
	  	
	  	 function SelectALL()
	 	{
	 		var checkAll = document.getElementById("chonall");
	 		var spIds = document.getElementsByName("chon");
	 		
	 		if(checkAll.checked == true)
	 		{
	 			for(i = 0; i < spIds.length; i++)
	 				spIds.item(i).checked = true;
	 		}
	 		else
	 		{
	 			for(i = 0; i < spIds.length; i++)
	 				spIds.item(i).checked = false;
	 		}
	 		
	 	}
	     
  	</script>
  	
<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>
<!-- END RENDER AUTO -->
</head>

<body>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> <meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>"> </noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="bdForm" method="post" action="../../BandoTTSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
	<input type="hidden" name="userId" value='<%= userId %>'>
	<input type="hidden" name="view" value='khachhangToado'>
	<input type="hidden" name="action" id="action" value='1'>
	<input type="hidden" name="trungtam" id="trungtam" value=''>
	<input type="hidden" name="khId" id="khId" value="">
	<input type="hidden" name="isTT" id="isTT" value="<%=khBean.getIsTT()%>">
	<input type="hidden" name="isGSBH" id="isGSBH" value="<%=khBean.getIsGSBH()%>">
	
	<input type="hidden" name="quyen1" id="quyen1" value="<%=quyen[1]%>">
	
	
	<!-- Navigator chao mung -->
	<ul class="chaomung">
	<li><%=url %></li>
	<li style="float:right">Chào mừng bạn <%=userTen %></li>
	</ul>
	
	<div class="box" style="margin-left: 2px; border: 1px solid #dddbdb; background-color: #C5E8CD;">
		<%if(khBean.getMsg().length()>0){ %>
		 <div class="alert alert-warning" id="errors" name="errors" role="alert"><%=khBean.getMsg()%></div>
		 <%} %>
	
		<div class="box-header">
			<div class="form-group col-md-6">
			  <h3 class="box-title">Thông tin toạ độ khách hàng</h3>
			</div> 
		</div>
		
		<div class="box-body">
	
			<div class="col-md-6">
			<div class="form-group">
			  <div class="col-md-4"><label><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %></label></div> 
	          <select class="select2" name="dvkdId" id="dvkdId" onChange = "submitform();" style="width:300px"> 
               <option value="" selected></option>
               <% if(DvkdRs != null){
			  	try{ while(DvkdRs.next()){ 
    			if(DvkdRs.getString("pk_seq").equals(khBean.getDvkdId())){ %>
      				<option value='<%= DvkdRs.getString("pk_seq")%>' selected><%= DvkdRs.getString("donvikinhdoanh") %></option>
      			<%}else{ %>
     				<option value='<%= DvkdRs.getString("pk_seq")%>'><%= DvkdRs.getString("donvikinhdoanh") %></option>
     			<%}} DvkdRs.close(); }catch(java.sql.SQLException e){} }%>
                </select>
	        </div>
	        </div>
	        
	        <div class="col-md-6">
			<div class="form-group">
			  <div class="col-md-4"><label><%=ChuyenNgu.get("Vùng",nnId) %></label></div> 
	          <select class="select2" name="vung" id="vung" onChange = "submitform();" style="width:300px">
                  <option value="" selected></option>
                  <% if(vungRs != null){
				  try{ while(vungRs.next()){ 
	    			if(vungRs.getString("pk_seq").equals(khBean.getVungId())){ %>
	      				<option value='<%= vungRs.getString("pk_seq")%>' selected><%= vungRs.getString("ten") %></option>
	      			<%}else{ %>
	     				<option value='<%= vungRs.getString("pk_seq")%>'><%= vungRs.getString("ten") %></option>
	     		<%}} vungRs.close(); }catch(java.sql.SQLException e){} }%>
                </select>  
	        </div>
	        </div>
	        
	        <div class="col-md-6">
			<div class="form-group">
			  <div class="col-md-4"><label><%=ChuyenNgu.get("Khu vực",nnId) %></label></div> 
	         	<select class="select2" name="khuvuc" id="khuvuc" onChange = "submitform();" style="width:300px">
				<option value="" selected></option>
				<% if(kvRs != null){
				try{ while(kvRs.next()){ 
						if(kvRs.getString("pk_seq").equals(khBean.getkvId())){ %>
				<option value='<%= kvRs.getString("pk_seq")%>' selected><%= kvRs.getString("ten") %></option>
				<%}else{ %>
				<option value='<%= kvRs.getString("pk_seq")%>'><%= kvRs.getString("ten") %></option>
				<%}} kvRs.close(); }catch(java.sql.SQLException e){} }%>
				</select>     
	        </div>
	        </div>
	        
	        <div class="col-md-6">
			<div class="form-group">
			  <div class="col-md-4"><label><%=ChuyenNgu.get("Nhà phân phối",nnId) %></label></div> 
	         	<select class="select2" name="npp" id="npp" onChange = "submitform();" style="width:300px">
				<option value="" selected></option>
				<% if(nppRs != null){
				try{ while(nppRs.next()){ 
						if(nppRs.getString("pk_seq").equals(khBean.getNppId())){ %>
				<option value='<%= nppRs.getString("pk_seq")%>' selected><%= nppRs.getString("ten") %></option>
				<%}else{ %>
				<option value='<%= nppRs.getString("pk_seq")%>'><%= nppRs.getString("ten") %></option>
				<%}} nppRs.close(); }catch(java.sql.SQLException e){} }%>
				</select>    
	        </div>
	        </div>
	        
	        <div class="col-md-6">
			<div class="form-group">
			  <div class="col-md-4"><label><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></label></div> 
	         	<select class="select2" name="ddkd" id="ddkd" onChange = "submitform();" style="width:300px">
				<option value="" selected></option>
				<% if(ddkd != null){
				try{ while(ddkd.next()){ 
						if(ddkd.getString("ddkdId").equals(khBean.getDdkdId())){ %>
				<option value='<%= ddkd.getString("ddkdId")%>' selected><%= ddkd.getString("ddkdTen") %></option>
				<%}else{ %>
				<option value='<%= ddkd.getString("ddkdId")%>'><%= ddkd.getString("ddkdTen") %></option>
				<%}} ddkd.close(); }catch(java.sql.SQLException e){} }%>
				</select>       
	        </div>
	        </div>
	        
	        <div class="col-md-6">
			<div class="form-group">
			  <div class="col-md-4"><label><%=ChuyenNgu.get("Tuyến bán hàng",nnId) %></label></div> 
	         	<select class="select2" name="tbh" id="tbh" onChange = "submitform();" style="width:300px"> 
				<option value="" selected></option>
				<% if(tbh != null){
				try{ while(tbh.next()){ 
						if(tbh.getString("tbhId").equals(khBean.getTbhId())){ %>
				<option value='<%= tbh.getString("tbhId")%>' selected><%= tbh.getString("tbhTen") %></option>
				<%}else{ %>
				<option value='<%= tbh.getString("tbhId")%>'><%= tbh.getString("tbhTen") %></option>
				<%}} tbh.close(); }catch(java.sql.SQLException e){} }%>
				</select>       
	        </div>
	        </div>
	        
	        <div class="col-md-6">
			<div class="form-group">
			  <div class="col-md-4"><label><%=ChuyenNgu.get("Mã/tên khách hàng",nnId)%></label></div> 
	          <INPUT name="ma_tenkh" id="ma_tenkh" type="text" value="<%= khBean.getMa_tenkh() %>"  onChange = "submitform();">
	        </div>
	        </div>
	        
	         <div class="col-md-12" style="margin-top: 10px;">
			 <div class="form-group">
		       <div class="col-md-4">
		       <a class="button" id ="imgText1" style="display:none; border: 1px solid #80CB9B; padding: 6px; margin-right: 5px;  background: white;" href="javascript:xtdnhieukh()"><%=ChuyenNgu.get("Xóa toạ độ",nnId) %></a>
		       <a class="button" id ="imgText2" style="border: 1px solid #80CB9B; padding: 6px; margin-right: 5px;  background: white;" href="javascript:anhdaidien()"><%=ChuyenNgu.get("Xóa ảnh đại diện",nnId) %></a>
			 </div></div>
			 </div>
        
        </div>
     </div>
     
     <div class="box-body">
		<table id="example2" class="table table-bordered table-hover table-striped">
		  <thead class="colorheader text-center">
		     <tr>
		        <TH><%=ChuyenNgu.get("Mã khách hàng",nnId) %></TH>
				<TH><%=ChuyenNgu.get("Tên khách hàng",nnId) %></TH>
				<TH><%=ChuyenNgu.get("Địa chỉ",nnId) %></TH>
				<TH><%=ChuyenNgu.get("Điện thoại",nnId) %></TH>
				<TH><%=ChuyenNgu.get("Kinh độ",nnId) %></TH>
				<TH><%=ChuyenNgu.get("Vĩ độ",nnId) %></TH>
				<TH><%=ChuyenNgu.get("Xóa toạ độ",nnId) %></TH>
				<TH><%=ChuyenNgu.get("Xóa ảnh",nnId) %></TH>
				<TH>
					<input name="chonall" id="chonall" value="" type="checkbox" onchange="SelectALL();" />
				</TH>
		     </tr>
		     </thead>
		   <tfoot><tr></tr></tfoot>
		</table>
	 </div>
		
		<%-- <div id="main" style="width:99.5%; padding-left:2px">
		
		    <div style="width:100%; float:none" align="left">
		    <fieldset>
		    	<legend class="legendtitle">Thông tin khách hàng</legend>
		     	<hr>
		    
				<TABLE width="100%" border="0" cellspacing="0" cellpadding="2">
					<TR>
						<TD width="100%">
							<TABLE class="" width="100%">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="10%" align="center"><%=ChuyenNgu.get("Mã khách hàng",nnId) %></TH>
												<TH width="30%" align="center"><%=ChuyenNgu.get("Tên khách hàng",nnId) %></TH>
												<TH width="20%" align="center"><%=ChuyenNgu.get("Địa chỉ",nnId) %></TH>
												<TH width="10%" align="center"><%=ChuyenNgu.get("Điện thoại",nnId) %></TH>
												<TH width="10%" align="center"><%=ChuyenNgu.get("Kinh độ",nnId) %></TH>
												<TH width="10%" align="center"><%=ChuyenNgu.get("Vĩ độ",nnId) %></TH>
												<TH width="5%" align="center"><%=ChuyenNgu.get("Xóa toạ độ",nnId) %></TH>
												<TH width="5%" align="center"><%=ChuyenNgu.get("Xóa ảnh",nnId) %></TH>
												<TH width="4%">
													<input name="chonall" id="chonall" value="" type="checkbox" onchange="SelectALL();"  />
												</TH>
											</TR>
										
							<%  														
		                        int m = 0;
		                        String lightrow = "tblightrow";
		                        String darkrow = "tbdarkrow";
								if(khlist!=null)
								{ 
									try{								
		                               while (khlist.next()){
		                                   	
		                                  	if (m % 2 != 0) {%>                     
		                                   	<TR class= <%=lightrow%> >
		                                   <%} else {%>
		                                      	<TR class= <%= darkrow%> >
		                                   	<%}%>
												<TD align="left"><div align="center"><%=khlist.getString("khId") %></div></TD>                                   
												<TD align="center"><%= khlist.getString("khTen")%></TD>
													<TD align="center"><%= khlist.getString("diachi")%></TD>
												<TD align="center"><%= khlist.getString("dienthoai")%></TD>
												<TD align="center"><%=khlist.getString("lat")%></TD>
												<TD align="center"><%=khlist.getString("lon")%></TD>
												<TD align="center" >
													<TABLE border = 0 cellpadding="0" cellspacing="0">
														<TR >
															<TD>
																<%
																String isGSBH = khBean.getIsGSBH();
																System.out.println("quyen[1]: "+quyen[1]+"--isGSBH: "+isGSBH);
																if(quyen[1]!= 0 || (isGSBH != null && isGSBH.equals("1"))) {%>
																<A href = "javascript:xoatoado(<%=khlist.getString("pk_seq") %>);">
																<img src="../images/Delete20.png" alt="Xoá toạ độ (Delete coordinates)" title="Xoá toạ độ (Delete coordinates)" width="20" height="20" longdesc="Xoá toạ độ (Delete coordinates)" border = 0></A>
																<%} %>
															</TD>												
														</TR>
													</TABLE>
												</TD>
												<TD align="center">
													<TABLE border = 0 cellpadding="0" cellspacing="0">
														<TR>
															<TD>
																
																<%if(quyen[1]!= 0 ) {%>
																<A href = "javascript:xoaanh(<%=khlist.getString("pk_seq") %>);"><img src="../images/Delete_Icon.png" alt="Xoa toa do" title="Xóa ảnh" width="20" height="20" longdesc="Xoa toa do" border = 0></A>
																<%} %>
															</TD>												
														</TR>
													</TABLE>
												</TD>
												<TD align="center"> <input type="checkbox" name="chon" value="<%=khlist.getString("pk_seq") %>" /> </TD>
		                                   		
												
											</TR>
									<%m++; }}catch(java.sql.SQLException e){}
								}%>
									
											 							
										</TABLE>
			
								</TABLE>
							</TD>
						</TR>
					</TABLE>
		    
		    	</fieldset>
		    </div>
		 </div> --%>
	 </form>
	 
	 <!-- jQuery 3 -->
<script src="../css/bower_components/jquery/dist/jquery.min.js"></script>
<script src="../css/bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="../css/bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<script src="../css/bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.js"></script>
<script src="../css/bower_components/select2/js/select2.min.js"></script>
<!-- <script type="text/javascript" src="../scripts/sum().js"></script> -->

<!--  -->
<script>
$(document).ready(function() {
	$('.select2').select2(); 
	readyFunc();
	
	});
	
function formatMoney(num) 
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

function readyFunc()
{
	if ( $.fn.DataTable.isDataTable('#example2') ) 
	{
		$('#example2').DataTable().destroy();
	}
	
	 /* nhanvien = $("#userId").val();
	 tungay = $("#Sdays").val();
	 denngay = $("#Edays").val();
	 vungId = $("#vungId").val();
	 kenhId = $("#kbhId").val();
	 khId = $("#khId").val(); 
	 nppId = $("#nppId").val(); 
	 ddkdId = $("#ddkdId").val(); */ 
	 dvkd  = $("#dvkdId").val();
	 kv = $("#khuvuc").val();		 
	 vung = $("#vung").val();
	 npp = $("#npp").val();
	 ddkdId = $("#ddkd").val();		 
     tbh = $("#tbh").val();  		
	 makhId = $("#ma_tenkh").val();
	 isGSBH = $("#isGSBH").val();
	 quyen1 = $("#quyen1").val();
	 /* String mafast = util.antiSQLInspection(request.getParameter("mafast"));
	 String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
	 String ma_tenkh = util.antiSQLInspection(request.getParameter("ma_tenkh")); */
	 
	 
     $('#example2').DataTable({
    	/* "sAjaxSource": "../../AjaxBandoTT?userId="+nhanvien+"&tungay="+ tungay +"&denngay="+ denngay +"&vungId="+ vungId +"&kenhId="+ kenhId +"&khId="+ khId +"&nppId="+ nppId +"&ddkdId="+ ddkdId +"&action=ChisodoanhthuNV", */
    			
    	"sAjaxSource": "../../AjaxBandoTT?makhId="+ makhId +"&dvkd="+ dvkd +"&vung="+vung+"&kv="+kv+"&ddkd="+ddkdId+"&tbh="+tbh+"&npp="+npp+"&action=ToaDoKH",		
    	"dom": 'C<"clear">lfrtip',
    	"serverSide": true,
	    "responsive": true,
	    "searching": false,
	    "lengthChange": false,
	    "iDisplayLength": 50,
	    "ordering": false,
	    "iDisplayStart":0,
	    
	    "columnDefs": [ {
	        "targets": 6,
	        "data": function(data, type, row){ if(quyen1 != 0 || (isGSBH != null && isGSBH.equals("1"))) { return '<A href = "javascript:xoatoado('+ data[6] +');"><img src="../images/Delete20.png" alt="Xoá toạ độ (Delete coordinates)" title="Xoá toạ độ (Delete coordinates)" width="20" height="20" longdesc="Xoá toạ độ (Delete coordinates)" border = 0></A>'; } }
	      }, { 
	    	  "targets": 7,
		        "data": function(data, type, row){ if(quyen1 != 0) { return '<A href = "javascript:xoaanh('+ data[6] +');"><img src="../images/Delete_Icon.png" alt="Xoa toa do" title="Xóa ảnh" width="20" height="20" longdesc="Xoa toa do" border = 0></A>'; } }  
	      }, { 
	    	  "targets": 8,
		        "data": function(data, type, row){ if(quyen1 != 0) { return '<input type="checkbox" name="chon" value='+ data[6] +' />'; } }  
	      } ]
	    
	    /* ,
	    drawCallback: function () {
	         var api = this.api();
	        $( api.table().footer() ).html(
	       	'<tr><th colspan=6>Tổng</th><th> '+ formatMoney(api.column( 6, {page:'current'} ).data().sum()) +'</th><th> '+ formatMoney(api.column( 7, {page:'current'} ).data().sum()) +'</th><th> '+ formatMoney(api.column( 8, {page:'current'} ).data().sum()) +'</th></tr>'
	        ); 
	      } */ 
  });  
} 
  
</script>	

<script>
	var msg = "<%= khBean.getMsg() %>".trim();
	if(msg.length > 0) {
		alert(msg);
	}
</script>

<!-- <script type='text/javascript' src='../scripts/loadingv2.js'></script> -->
</BODY>
</HTML>

<% 
    if(vungRs != null) { vungRs.close(); vungRs = null; } 
	if(kvRs != null) { kvRs.close(); kvRs = null; } 
	if(nppRs != null) { nppRs.close(); nppRs = null; } 
	if(ddkd != null) { ddkd.close(); ddkd = null; } 
	if(tbh != null) { tbh.close(); tbh = null; } 
	if(khlist != null) { khlist.close(); khlist = null; } 

	khBean.DBclose(); khBean = null;
	session.setAttribute("obj",null);
   
}%>