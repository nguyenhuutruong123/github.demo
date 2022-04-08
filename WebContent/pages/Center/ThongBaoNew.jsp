<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.db.sql.dbutils"%>
<%@page import="geso.dms.center.beans.thongbao.*"%>
<%@ page import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<% IThongbao thongbao = (IThongbao)session.getAttribute("tbBean"); System.out.println("----View Mode ben trang JSP: " + thongbao.getViewMode() ); %>
<% ResultSet nvList = thongbao.getThongbaonhanvienList();%>
<% ResultSet nppList = thongbao.getNppdangnhapList();%>
<% ResultSet kvList = thongbao.getKhuvucList();%>
<% ResultSet vList = thongbao.getVungList();%>
<% ResultSet vbhdList = thongbao.getVbhdRs(); %>
<% ResultSet vbccList = thongbao.getVbccRs(); %>
<% ResultSet vbttList = thongbao.getVbttRs(); %>
<% ResultSet vbsdbsList = thongbao.getVbsdbsRs(); %>
<% ResultSet NhomNv = thongbao.getNhomNvRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% Utility util = (Utility) session.getAttribute("util"); %>

<% 
	String userTen = (String) session.getAttribute("userTen"); 
	ResultSet ddkdList = thongbao.getDdkdRs();
	ResultSet ttRs = thongbao.getTtRs();
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String urlTB = util.getChuyenNguUrl("ThongbaoSvl","&viewMode=1&loaivanban=5&task=",nnId).substring(0,32);		
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Insert title here</title>
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
	<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>

	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
	
	<script>
		//perform JavaScript after the document is scriptable.
		$(function() {
		 // setup ul.tabs to work as tabs for each div directly under div.panes
		 	$("ul.tabs").tabs("div.panes > div");
		});
	</script>
	
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
	
	<SCRIPT language="javascript" type="text/javascript">
		function LocNhanVien()
		{
			document.forms["congtyForm"].action.value="locnhanvien";
			document.forms["congtyForm"].submit();
		}
		
		 function saveform()
		 {
			 var tieude = document.getElementById('tieude');
			 var ngaybatdau= document.getElementById('ngaybatdau');
			 var ngayketthuc= document.getElementById('ngayketthuc');
			 var noidung= document.getElementById('noidung');
			 if(ngaybatdau.value=="")
			 {
				 alert("Vui lòng nhập ngày bắt đầu");
				 ctMa.focus();
				 return;
			 }
			 /* if(ngayketthuc.value=="")
			 {
				 alert("Vui lòng nhập ngày kết thúc");
				 ctMa.focus();
				 return;
			 } */
			 if(tieude.value=="")
			 {
				 alert("Vui lòng nhập tiêu đề thông báo");
				 ctMa.focus();
				 return;
			 }
			 if(noidung.value == "")
			 {
				 alert("Vui lòng nhập nội dung thông báo");
				 ctTen.focus();
				 return;
			 }
			 
			 document.forms["congtyForm"].action.value="save";
		     document.forms['congtyForm'].submit();
		 }
		 
		 function Xoafile(name)
		 {
		 	/* var xmlhttp;
		 	if (window.XMLHttpRequest)
		 	{// code for IE7+, Firefox, Chrome, Opera, Safari
		 	   xmlhttp = new XMLHttpRequest();
		 	}
		 	else
		 	{
		 	   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		 	}
		 	xmlhttp.onreadystatechange=function()
		 	{
		 	   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
		 	   {
		 		   if(xmlhttp.responseText=='')
		 			   {
		 	       			document.getElementById('tenfilea').innerHTML = xmlhttp.responseText;
		 	       			document.getElementById("valueten").value='0';
		 	       			alert('Đã xóa thành công');
		 			   }
		 		   else
		 			   {
		 			   alert(xmlhttp.responseText);
		 			   }
		 	   }
		 	}
		 	if(confirm("Bạn muốn xóa file đính kèm"))
		 	{
		 		xmlhttp.open("GET","../../ThongbaoUpdateSvl?task=" + "xoafilenew" + "&id=" + document.getElementById('valueten').value,true);
		 		xmlhttp.send();
		 	}
		 	else
		 		return false; */
			if(confirm("Bạn muốn xóa file đính kèm"))
		 	{
		 		document.forms["congtyForm"].removename.value = name;
		 		document.forms["congtyForm"].action.value = "remove";
			    document.forms['congtyForm'].submit();
		 	}
		 	else
		 		return false;
		 }
		 
		 function sellectAll(id1, id2)
		 {
			 var chkAll = document.getElementById(id1);
			 var spIds = document.getElementsByName(id2);
			
			 
			 if(id2 == 'nvIds')
			 {
				 var ddkdIds = document.getElementsByName('ddkdIds');
			 }
			 var nppid = document.getElementsByName('nppids'); 
			 if(chkAll.checked)
			 {
				 for(i = 0; i < spIds.length; i++)
				 {
					 spIds.item(i).checked = true;
				 }
				 for(i = 0; i < nppid.length; i++)
				 {
					 nppid.item(i).checked = true;
				 }
				 if(id2 == 'nvIds')
				 {
					 for(i = 0; i < ddkdIds.length; i++)
					 {
					 	ddkdIds.item(i).checked = true;
					 }
				 }
				 
			 }
			 else
			 {
				 for(i = 0; i < spIds.length; i++)
				 {
					 spIds.item(i).checked = false;
				 }
				 for(i = 0; i < nppid.length; i++)
				 {
					 nppid.item(i).checked = false;
				 }
				 if(id2 == 'nvIds')
				 {
					 for(i = 0; i < ddkdIds.length; i++)
					 {
					 	ddkdIds.item(i).checked = false;
					 }
				 }
				 
			 }
		 }
		 function sellectAll2(id1, id2)
		 {
			 var chkAll = document.getElementById(id1);
			 var spIds = document.getElementsByName(id2);
			
			 if(chkAll.checked)
			 {
				 for(i = 0; i < spIds.length; i++)
				 {
					 spIds.item(i).checked = true;
				
				 }
				 
			 }
			 else
			 {
				 for(i = 0; i < spIds.length; i++)
				 {
					 spIds.item(i).checked = false;
				 }
			 }
		 }
		 
 	</SCRIPT>
	
	<style type="text/css">
		.thongbao a { 
			color: blue;
		}
		.thongbao a:hover{
			color: red;
		}
	</style>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>

<body>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<form name="congtyForm" method="post" enctype="multipart/form-data" action="../../ThongbaoUpdateSvl" >
<input type="hidden" name="action" value="">
<input type="hidden" name="removename" value="">
<input type="hidden" name="viewMode" value="<%= thongbao.getViewMode() %>" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   	<%= " " + urlTB %> <%= ( thongbao.getLoaithongbao().equals("5") ? "Thông báo" : "Văn bản" ) %> > Tạo mới </TD>
							   <TD colspan="2" align="right" class="tbnavigation"> <%=ChuyenNgu.get("Chào mừng bạn",nnId) %> &nbsp;<%=userTen%>   </TD> 
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
									<TD width="30" align="left"><A href="../../ThongbaoSvl?userId=1000&loaivanban=<%= thongbao.getLoaithongbao() %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle"><%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %> </LEGEND>				
		    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= thongbao.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>				
		  		<TR>
				   <TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle"><%=ChuyenNgu.get("Thông tin văn bản",nnId) %> </LEGEND>
						<TABLE width="100%" cellspacing="0" cellpadding="6">
						<TR>
							  <TD width="120px" class="plainlabel" ><%=ChuyenNgu.get("Ngày bắt đầu",nnId) %></TD>
							  <TD width="250px" class="plainlabel" align="left">
							  	<input type="text" class="days"id="ngaybatdau" name="ngaybatdau" value="<%= thongbao.getNgaybatdau() %>">
							  </TD>
							  <TD width="120px" class="plainlabel" ><%=ChuyenNgu.get("Ngày kết thúc",nnId) %></TD>
							  <TD  class="plainlabel" >
							  	<input type="text" class="days" id="ngayketthuc" name="ngayketthuc" value="<%= thongbao.getNgayketthuc() %>">
							  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							  	<% if(thongbao.getHethieuluc().equals("1")) { %>
							  		<input type="checkbox" name="hethieuluc" checked="checked" value="1"  ><i><%=ChuyenNgu.get("Hết hiệu lực",nnId) %></i>
							  	<% } else { %>
							  		<input type="checkbox" name="hethieuluc" value="1" ><i><%=ChuyenNgu.get("Hết hiệu lực",nnId) %></i>
							  	<% } %>
							  </TD>
						</TR>
						<% if(!thongbao.getLoaithongbao().equals("5")) { %>
							<TR>
								  <TD class="plainlabel" ><%=ChuyenNgu.get("Loại văn bản",nnId) %></TD>
								  <TD  class="plainlabel" colspan="3" >
								  	 <select name="loaithongbao" >
								  	 	<option value="" ></option>
								  	 	<% if(thongbao.getLoaithongbao().equals("0")) { %>
								  	 		<option value="0" selected="selected" ><%=ChuyenNgu.get("Văn bản",nnId) %></option>
								  	 	<% } else { %> 
								  	 		<option value="0" ><%=ChuyenNgu.get("Văn bản",nnId) %></option>
								  	 	<% } %>
								  	 	<% if(thongbao.getLoaithongbao().equals("1")) { %>
								  	 		<option value="1" selected="selected" ><%=ChuyenNgu.get("Hướng dẫn",nnId) %></option>
								  	 	<% } else { %> 
								  	 		<option value="1" ><%=ChuyenNgu.get("Hướng dẫn",nnId) %></option>
								  	 	<% } %>
								  	 	<% if(thongbao.getLoaithongbao().equals("2")) { %>
								  	 		<option value="2" selected="selected" ><%=ChuyenNgu.get("Căn cứ",nnId) %></option>
								  	 	<% } else { %> 
								  	 		<option value="2" ><%=ChuyenNgu.get("Căn cứ",nnId) %></option>
								  	 	<% } %>
								  	 	<% if(thongbao.getLoaithongbao().equals("3")) { %>
								  	 		<option value="3" selected="selected" ><%=ChuyenNgu.get("Thay thế",nnId) %></option>
								  	 	<% } else { %> 
								  	 		<option value="3" ><%=ChuyenNgu.get("Thay thế",nnId) %></option>
								  	 	<% } %>
								  	 	<% if(thongbao.getLoaithongbao().equals("4")) { %>
								  	 		<option value="4" selected="selected" ><%=ChuyenNgu.get("Sửa đổi",nnId) %>, <%=ChuyenNgu.get("bổ sung",nnId) %></option>
								  	 	<% } else { %> 
								  	 		<option value="4" ><%=ChuyenNgu.get("Sửa đổi",nnId) %>, <%=ChuyenNgu.get("bổ sung",nnId) %></option>
								  	 	<% } %>
								  	 </select>
								  </TD>
							 </TR>
							 <TR>
								  <TD class="plainlabel" ><%=ChuyenNgu.get("Tiêu đề",nnId) %></TD>
								  <TD  class="plainlabel" colspan="3" >
								  	<input style="width: 600px" type="text" class="txtsearch" maxlength="100"  size="30" id="tieude" name="tieude" value="<%= thongbao.getTieude() %>"></TD>
							 </TR>
						 <% } else { %>
							 <TR>
								  <TD class="plainlabel" ><%=ChuyenNgu.get("Tiêu đề",nnId) %></TD>
								  <TD  class="plainlabel" colspan="3" >
								  	<input type="hidden" name="loaithongbao" value="5" >
								  	<input style="width: 600px" type="text" class="txtsearch" maxlength="100"  size="30" id="tieude" name="tieude" value="<%= thongbao.getTieude() %>"></TD>
							 </TR>
						 <% } %>
						 
						 <TR>
							  <TD class="plainlabel" valign="top" ><%=ChuyenNgu.get("Nội dung thông báo",nnId) %></TD>
							  <TD class="plainlabel" colspan="3">			
				    				<textarea  id="noidung" name="noidung" style="width: 100%" rows="3" cols="20" ><%= thongbao.getNoidung() %></textarea></TD>
						 </TR>
						 <TR>
							 <TD class="plainlabel" style= "display:none"></TD>
							 <TD class="plainlabel" style= "display:none">   	
							 <% if(thongbao.getChiNhanh().equals("1")) { %>
							  		<input type="checkbox" name="chinhanh" checked="checked" value="1" onchange="LocNhanVien()" ><i><%=ChuyenNgu.get("Chi nhánh",nnId) %></i>
							  	<% } else { %>
							  		<input type="checkbox" name="chinhanh" value="1" onchange="LocNhanVien()" ><i><%=ChuyenNgu.get("Chi nhánh",nnId) %></i>
							  	<% } %>
							 </TD>
							 <TD style= "display:none" class="plainlabel" colspan="3">   	
							 <% if(thongbao.getDoiTac().equals("1")) { %>
								  		<input type="checkbox" name="doitac" checked="checked" value="1" onchange="LocNhanVien()" ><i><%=ChuyenNgu.get("Nhà phân phối",nnId) %></i>
								  	<% } else { %>
								  		<input type="checkbox" name="doitac" value="1" onchange="LocNhanVien()" ><i><%=ChuyenNgu.get("Nhà phân phối",nnId) %></i>
								  	<% } %>
							 </TD>
						 
						 
						 </TR>
						 <TR>
						  	  	<TD class="plainlabel"><%=ChuyenNgu.get("File đính kèm",nnId) %></TD>
						  	  	<TD class="plainlabel" colspan="3" >
						  	  		<div><div id="input"  style="float: left;"><INPUT type="file" name="fileup" value=""></div>
						  	  		&nbsp;&nbsp;
						  	  		<img src="../images/xpcollapse1.gif" onclick="LocNhanVien()" style="cursor: pointer;" alt="Thêm file" width="20" height="20" longdesc="Thêm file" border = 0>
						  	  		</div>
						  	  		<% if(thongbao.getFile().length > 0 ) {	
						  	  			for(int i=0;i <thongbao.getFile().length; i++){%>
						  	  			<input type="hidden" name = "filename" value = "<%=thongbao.getFile()[i]%>">
						  	  			<div id="tenfilea"><p><%= thongbao.getFile()[i] %><img src="../images/Delete20.png" onclick="Xoafile('<%= thongbao.getFile()[i]%>')" style="cursor: pointer;" alt="Xóa" width="20" height="20" longdesc="Cap nhat" border = 0></p></div>
						  	  			<%}
					  	  			}%>
						  	  	</TD>
				  		</TR>
				  		<TR>
			  				<TD colspan="4" >
			  					<ul class="tabs">
			  						<li><a href="#"><%=ChuyenNgu.get("Nhân viên nhận",nnId) %></a></li>
			  					<% if(!thongbao.getLoaithongbao().equals("5")) { %>
		                  			<li><a href="#"><%=ChuyenNgu.get("Văn bản hướng dẫn",nnId) %></a></li>
		                  			<li><a href="#"><%=ChuyenNgu.get("Văn bản căn cứ",nnId) %></a></li>
		                  			<li><a href="#"><%=ChuyenNgu.get("Văn bản sửa đổi BS",nnId) %></a></li>
		                  			<li><a href="#"><%=ChuyenNgu.get("Văn bản thay thế",nnId) %></a></li>
		                  		<% } %>
		                  		</ul>
		                  		
		                  		<div class="panes">
		                  			
		                  			<div style="font-size: 1.0em; font-weight: 500" >
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<td width="80px"><%=ChuyenNgu.get("Miền",nnId) %> </td>
		                  						<td  width="150px" >
		                  							<select name="vungId" onchange="LocNhanVien()">
														 <option value = "" > </option>
							                             <%
							                             if (vList != null)
							                             {
							                            	 while (vList.next())
							                            	 { %>
							                       				<% if(vList.getString("pk_seq").equals(thongbao.getVung())){ %>
							                       					<option value ="<%= vList.getString("pk_seq") %>" selected="selected"> <%= vList.getString("ten") %></option>
							                       				<%}else{ %>
							                       					<option value ="<%= vList.getString("pk_seq") %>"> <%= vList.getString("ten") %></option>
							                       				<%} %>
							                       			<% }
							                             }
							                             %>
						                             </select>  
						                             </td>
													 <TD  width="80px">
						
		                  						<%=ChuyenNgu.get("Khu vực",nnId) %> 
		                  						</TD>
		                  						<TD width="150px">
		                  						
		                  							<select name="kvId" onchange="LocNhanVien()">
														 <option value =""> </option>
							                             <%
							                             if (kvList!=null)
							                             {
							                            	 while (kvList.next())
							                            	 { %>
							                       				<% if(kvList.getString("pk_seq").equals(thongbao.getKhuvuc())){ %>
							                       					<option value ="<%=kvList.getString("pk_seq") %>" selected="selected"> <%=kvList.getString("ten") %></option>
							                       				<%}else{ %>
							                       					<option value ="<%=kvList.getString("pk_seq") %>"> <%=kvList.getString("ten") %></option>
							                       				<%} %>
							                       			<% }
							                             }
						                             	 %>
						                             </select>
						                             </TD>
						                            <%--  <TD width="80px">
						                         
						                             Địa bàn
		                  						</TD>
		                  						<TD width="150px">
		                  							<select name="ttId" onchange="LocNhanVien()">
														 <option value =""> </option>
							                             <%
							                             if (ttRs!=null)
							                             {
							                            	 while (ttRs.next())
							                            	 { %>
							                       				<% if(ttRs.getString("pk_seq").equals(thongbao.getTtId() )){ %>
							                       					<option value ="<%=ttRs.getString("pk_seq") %>" selected="selected"> <%=ttRs.getString("ten") %></option>
							                       				<%}else{ %>
							                       					<option value ="<%=ttRs.getString("pk_seq") %>"> <%=ttRs.getString("ten") %></option>
							                       				<%} %>
							                       			<% }
							                             }
						                             	 %>
						                             </select> 
						                        
		                  						</td> --%>
		                  						</TR>
		                  				
		                  						  <TR>   
		                  						<td width="80px"> <%=ChuyenNgu.get("Loại NV",nnId) %></TD>
												<td width="150px">
												<select name="loaiNv" id="loaiNv" onchange="LocNhanVien();">
														<option value=""
															<%=thongbao.getLoaiNv().equals("") ? " selected " : ""%>></option>
														<option value="4"
															<%=thongbao.getLoaiNv().equals("4") ? " selected " : ""%>><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></option>
														<option value="5"
															<%=thongbao.getLoaiNv().equals("5") ? " selected " : ""%>><%=ChuyenNgu.get("Ban Giám Đốc",nnId) %></option>
														<option value="1"
															<%=thongbao.getLoaiNv().equals("1") ? " selected " : ""%>><%=ChuyenNgu.get("Giám Đốc Miền",nnId) %></option>
														<option value="2"
															<%=thongbao.getLoaiNv().equals("2") ? " selected " : ""%>><%=ChuyenNgu.get("Phụ trách Vùng",nnId) %></option>
														<option value="3"
															<%=thongbao.getLoaiNv().equals("3") ? " selected " : ""%>><%=ChuyenNgu.get("Phụ Trách Tỉnh/ GĐCN Cấp",nnId) %> 2</option>																			
												</select></TD>
		                  						<td width="80px"> <%=ChuyenNgu.get("Nhóm NV",nnId) %></TD>
												<td  >
												<select name="nhomnv" id="nhomnv" onchange="LocNhanVien();">
														<option value="" > </option>
														<% if(NhomNv != null) 
		                  					{ 
		                  						int m = 0;
		                  						while(NhomNv.next()) 
		                  						{ 
		                  							%>
							                       				<% if(NhomNv.getString("pk_seq").equals(thongbao.getNhomNvId())){ %>
							                       					<option value ="<%=NhomNv.getString("pk_seq") %>" selected="selected"> <%=NhomNv.getString("ten") %></option>
							                       				<%}else{ %>
							                       					<option value ="<%=NhomNv.getString("pk_seq") %>"> <%=NhomNv.getString("ten") %></option>
							                       				<%} %>
							                       			<% }
		                  					
		                  							
		                  	
		                  					} %>																		
												</select></TD>
		                  						
		                  					</TR>
		                  					</Table>
		                  					<Table width = 100% cellpadding = "3" cellspacing = "0" border = "0">
		                  					<TR>
		                  						<TH width="15%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Mã nhân viên",nnId) %></TH>
		                  						<TH width="65%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Tên nhân viên",nnId) %></TH>
		                  						<TH width="10%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Điện thoại",nnId) %></TH>
		                  						<TH width="10%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Chọn tất cả",nnId) %> <input type="checkbox" id='chkAll' onchange="sellectAll('chkAll', 'nvIds');"   > </TH>
		                  					</TR>
		                  					
		                  					<TR >
		                  						<td class="plainlabel" colspan="3" > <%=ChuyenNgu.get("NHÂN VIÊN ĐĂNG NHẬP",nnId) %> </td>
		                  		
		                  						<TH width="10%" align="center" class="plainlabel" > <%=ChuyenNgu.get("Chọn",nnId) %><input type="checkbox" id='chk' onchange="sellectAll2('chk', 'nvIds');"   > </TH>
		                  					</TR>
		                  					
		                  					<% if(nvList != null) 
		                  					{ 
		                  						int m = 0;
		                  						while(nvList.next()) 
		                  						{ 
		                  							%>
		                  							
		                  							<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
		                  								<TD style="font-size: 1.0em" ><%= nvList.getString("dangnhap") %></TD>
		                  								<TD><%= nvList.getString("ten") %></TD>
		                  								<TD><%= nvList.getString("dienthoai") %></TD>
		                  								<TD align="center" >
		                  									<% if( thongbao.getNhanvienIds().indexOf(nvList.getString("pk_seq")) >= 0 ) { %>
		                  										<input type="checkbox"  name="nvIds" value="<%= nvList.getString("pk_seq") %>" checked="checked"  >
		                  									<% } else { %> 
		                  										<input type="checkbox"  name="nvIds" value="<%= nvList.getString("pk_seq") %>"   >
		                  									<% } %>
		                  								</TD>
		                  							
		                  							</TR>
		                  							
		                  						<% m++; } 
		                  						nvList.close(); 
		                  					} %>
		                  					
		                  					<TR >
		                  						<td class="plainlabel" colspan="3" > <%=ChuyenNgu.get("User nhà phân phối",nnId) %> </td>
		                  						<TH width="10%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Chọn",nnId) %> <input type="checkbox" id='chk1' onchange="sellectAll2('chk1', 'nppids');"   > </TH>
		                  					</TR>
		                  					
		                  					<% if(nppList != null) 
		                  					{ 
		                  						int m = 0;
		                  						while(nppList.next()) 
		                  						{ 
		                  							%>
		                  							
		                  							<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
		                  								<TD style="font-size: 1.0em" ><%= nppList.getString("dangnhap") %></TD>
		                  								<TD><%= nppList.getString("ten") %></TD>
		                  								<TD><%= nppList.getString("dienthoai") %></TD>
		                  								<TD align="center" >
		                  									<% if( thongbao.getnppIds().indexOf(nppList.getString("pk_seq")) >= 0 ) { %>
		                  										<input type="checkbox" name="nppids" value="<%= nppList.getString("pk_seq") %>" checked="checked"  >
		                  									<% } else { %> 
		                  										<input type="checkbox" name="nppids" value="<%= nppList.getString("pk_seq") %>"   >
		                  									<% } %>
		                  								</TD>
		                  							</TR>
		                  							
		                  						<% m++; } 
		                  						nppList.close(); 
		                  					} %>
		                  					<TR >
		                  						<td class="plainlabel" colspan="3"> <%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %> </td>
		                  							<TH width="10%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Chọn",nnId) %> <input type="checkbox" id='chk2' onchange="sellectAll2('chk2', 'ddkdIds');"   > </TH>
		                  					</TR>
		                  					
		                  					
		                  					<% if(ddkdList != null) 
		                  					{ 
		                  						int m = 0;
		                  						while(ddkdList.next()) 
		                  						{ 
		                  							%>
		                  							
		                  							<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
		                  								<TD style="font-size: 1.0em" ><%= ddkdList.getString("MANHANVIEN") %></TD>
		                  								<TD><%= ddkdList.getString("ten") %></TD>
		                  								<TD><%= ddkdList.getString("dienthoai") %></TD>
		                  								<TD align="center" >
		                  									<% if( thongbao.getDdkdId().indexOf(ddkdList.getString("pk_seq")) >= 0 ) { %>
		                  										<input type="checkbox" name="ddkdIds" value="<%= ddkdList.getString("pk_seq") %>" checked="checked"  >
		                  									<% } else { %> 
		                  										<input type="checkbox" name="ddkdIds" value="<%= ddkdList.getString("pk_seq") %>"   >
		                  									<% } %>
		                  								</TD>
		                  							</TR>
		                  							
		                  						<% m++; } 
		                  						ddkdList.close(); 
		                  					} %>
		                  					
		                  				</TABLE>
		                  			</div>
		                  			
		                  		<% if(!thongbao.getLoaithongbao().equals("5")) { %>	
		                  			
		                  			<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Mã văn bản",nnId) %></TH>
		                  						<TH width="80%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Nội dung",nnId) %></TH>
		                  						<TH width="10%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Chọn hết",nnId) %> <input type="checkbox" id='chkAll1' onchange="sellectAll('chkAll1', 'vbhdIds');"  > </TH>
		                  					</TR>
		                  					
		                  					<% if(vbhdList != null ) 
		                  					{ 
		                  						int m = 0;
		                  						while(vbhdList.next() ) 
		                  						{
		                  							%>
		                  							<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
		                  								<TD style="font-size: 1.0em" ><%= vbhdList.getString("pk_seq") %></TD>
		                  								<TD class="thongbao" ><a href="../../ThongbaoUpdateSvl?task=view&id=<%= vbhdList.getString("pk_seq") %>&viewMode=<%= thongbao.getViewMode()  %>" target="target" ><%= vbhdList.getString("noidung") %></a></TD>
		                  								<TD align="center" >
		                  									<% if( thongbao.getVbhdId().indexOf(vbhdList.getString("pk_seq")) >= 0 ) { %>
		                  										<input type="checkbox" name="vbhdIds" value="<%= vbhdList.getString("pk_seq") %>" checked="checked"  >
		                  									<% } else { %> 
		                  										<input type="checkbox" name="vbhdIds" value="<%= vbhdList.getString("pk_seq") %>"   >
		                  									<% } %>
		                  								</TD>
		                  							</TR>
		                  						<% m++; } 
		                  						vbhdList.close();  
		                  					} %>
		                  					
		                  				</TABLE>
		                  			</div>
		                  			
		                  			<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Mã văn bản",nnId) %></TH>
		                  						<TH width="80%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Nội dung",nnId) %></TH>
		                  						<TH width="10%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Chọn hết",nnId) %> <input type="checkbox" id='chkAll2' onchange="sellectAll('chkAll2', 'vbccIds');" > </TH>
		                  					</TR>
		                  					
		                  					<% if(vbccList != null ) 
		                  					{ 
		                  						int m = 0;
		                  						while(vbccList.next() ) 
		                  						{
		                  							%>
		                  							<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
		                  								<TD style="font-size: 1.0em" ><%= vbccList.getString("pk_seq") %></TD>
		                  								<TD class="thongbao" ><a href="../../ThongbaoUpdateSvl?task=view&id=<%= vbccList.getString("pk_seq") %>&viewMode=<%= thongbao.getViewMode()  %>" target="target" ><%= vbccList.getString("noidung") %></a></TD>
		                  								<TD align="center" >
		                  									<% if( thongbao.getVbcId().indexOf(vbccList.getString("pk_seq")) >= 0 ) { %>
		                  										<input type="checkbox" name="vbccIds" value="<%= vbccList.getString("pk_seq") %>" checked="checked"  >
		                  									<% } else { %> 
		                  										<input type="checkbox" name="vbccIds" value="<%= vbccList.getString("pk_seq") %>"   >
		                  									<% } %>
		                  								</TD>
		                  							</TR>
		                  						<% m++; } 
		                  						vbccList.close();  
		                  					} %>
		                  					
		                  				</TABLE>
		                  			</div>
		                  			
		                  			<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Mã văn bản",nnId) %></TH>
		                  						<TH width="80%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Nội dung",nnId) %></TH>
		                  						<TH width="10%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Chọn hết",nnId) %> <input type="checkbox" id='chkAll4' onchange="sellectAll('chkAll4', 'vbsdbsIds');"  > </TH>
		                  					</TR>
		                  					
		                  					<% if(vbsdbsList != null ) 
		                  					{ 
		                  						int m = 0;
		                  						while(vbsdbsList.next() ) 
		                  						{
		                  							%>
		                  							<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
		                  								<TD style="font-size: 1.0em" ><%= vbsdbsList.getString("pk_seq") %></TD>
		                  								<TD class="thongbao" ><a href="../../ThongbaoUpdateSvl?task=view&id=<%= vbsdbsList.getString("pk_seq") %>&viewMode=<%= thongbao.getViewMode()  %>" target="target" ><%= vbsdbsList.getString("noidung") %></a></TD>
		                  								<TD align="center" >
		                  									<% if( thongbao.getVbsdbsId().indexOf(vbsdbsList.getString("pk_seq")) >= 0 ) { %>
		                  										<input type="checkbox" name="vbsdbsIds" value="<%= vbsdbsList.getString("pk_seq") %>" checked="checked"  >
		                  									<% } else { %> 
		                  										<input type="checkbox" name="vbsdbsIds" value="<%= vbsdbsList.getString("pk_seq") %>"   >
		                  									<% } %>
		                  								</TD>
		                  							</TR>
		                  						<% m++; } 
		                  						vbsdbsList.close();  
		                  					} %>
		                  					
		                  				</TABLE>
		                  			</div>
		                  			
		                  			<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Mã văn bản",nnId) %></TH>
		                  						<TH width="80%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Nội dung",nnId) %></TH>
		                  						<TH width="10%" align="center" class="plainlabel" ><%=ChuyenNgu.get("Chọn hết",nnId) %> <input type="checkbox" id='chkAll3' onchange="sellectAll('chkAll3', 'vbttIds');" > </TH>
		                  					</TR>
		                  					
		                  					<% if(vbttList != null ) 
		                  					{ 
		                  						int m = 0;
		                  						while(vbttList.next() ) 
		                  						{
		                  							%>
		                  							<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
		                  								<TD style="font-size: 1.0em" ><%= vbttList.getString("pk_seq") %></TD>
		                  								<TD class="thongbao" ><a href="../../ThongbaoUpdateSvl?task=view&id=<%= vbttList.getString("pk_seq") %>&viewMode=<%= thongbao.getViewMode()  %>" target="target" ><%= vbttList.getString("noidung") %></a></TD>
		                  								<TD align="center" >
		                  									<% if( thongbao.getVbttId().indexOf(vbttList.getString("pk_seq")) >= 0 ) { %>
		                  										<input type="checkbox" name="vbttIds" value="<%= vbttList.getString("pk_seq") %>" checked="checked"  >
		                  									<% } else { %> 
		                  										<input type="checkbox" name="vbttIds" value="<%= vbttList.getString("pk_seq") %>"   >
		                  									<% } %>
		                  								</TD>
		                  							</TR>
		                  						<% m++; } 
		                  						vbttList.close();  
		                  					} %>
		                  					
		                  				</TABLE>
		                  			</div>
		                  		
		                  		<% } %>	
		                  			
		                  		</div>
			  				</TD>
				  		</TR>
						 	 
						</TABLE>
						</FIELDSET>
					</TD>	
				</TR>
						
			</TABLE>
			</TD>
	</TR>
 
</TABLE>
<%thongbao.DBClose(); %>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>