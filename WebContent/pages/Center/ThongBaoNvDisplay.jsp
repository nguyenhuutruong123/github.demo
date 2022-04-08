<%@page import="geso.dms.center.util.Csrf"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="javax.naming.spi.DirStateFactory.Result"%>
<%@page import="geso.dms.center.db.sql.dbutils"%>
<%@page import="geso.dms.center.beans.thongbao.*"%>
<% IThongbao thongbao = (IThongbao)session.getAttribute("tbBean"); %>
<% ResultSet nvList = thongbao.getThongbaonhanvienList();%>
<% ResultSet kvList = thongbao.getKhuvucList();%>
<% ResultSet vList = thongbao.getVungList();%>
<% ResultSet vbhdList = thongbao.getVbhdRs(); %>
<% ResultSet vbccList = thongbao.getVbccRs(); %>
<% ResultSet vbttList = thongbao.getVbttRs(); %>
<% ResultSet vbsdbsList = thongbao.getVbsdbsRs(); %>
<% ResultSet thaoluanList = thongbao.getThaoluan(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Thông báo</title>
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
		
		 function GuiTraLoi()
		 {
			 document.forms["congtyForm"].action.value="guitraloi";
		     document.forms['congtyForm'].submit();
		 }
		 
		 function LuuFile(name)
		 {
			 document.forms["congtyForm"].action.value = "download";
			 document.forms["congtyForm"].tenfile.value = name;
			 document.forms["congtyForm"].submit();
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

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>

<body>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="action" value="">
<input type="hidden" name="id" value="<%= thongbao.getId() %>">
<input type="hidden" name="userId" value="<%= userId %>">
<input type = "hidden" name = "tenfile" value = "">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   	Quản lý thông báo > Chức năng > Hộp thư đến > Hiển thị </TD>
							   <TD colspan="2" align="right" class="tbnavigation"> Chào mừng bạn &nbsp;<%=userTen%>   </TD> 
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
									<TD width="30" align="left"><A href="../../ThongbaoSvl?task=1&viewMode=<%= thongbao.getViewMode() %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>				
		    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= thongbao.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>				
		  		<TR>
				   <TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông tin thông báo </LEGEND>
						<TABLE width="100%" cellspacing="0" cellpadding="6">
						<TR>
							  <TD width="120px" class="plainlabel" >Ngày bắt đầu</TD>
							  <TD width="250px" class="plainlabel" align="left">
							  	<input type="text" id="ngaybatdau" name="ngaybatdau" value="<%= thongbao.getNgaybatdau() %>" disabled="disabled" >
							  </TD>
							  <TD width="120px" class="plainlabel" >Ngày kết thúc</TD>
							  <TD  class="plainlabel" >
							  	<input type="text" id="ngayketthuc" name="ngayketthuc" value="<%= thongbao.getNgayketthuc() %>" disabled="disabled" >
							  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							  	<% if(thongbao.getHethieuluc().equals("1")) { %>
							  		<input type="checkbox" name="hethieuluc" checked="checked" disabled="disabled" ><i>Hết hiệu lực</i>
							  	<% } else { %>
							  		<input type="checkbox" name="hethieuluc" disabled="disabled" ><i>Hết hiệu lực</i>
							  	<% } %>
							  </TD>
						</TR>
						<TR>
							  <TD class="plainlabel" >Loại thông báo</TD>
							  <TD  class="plainlabel" >
							  	 <select name="loaithongbao" disabled="disabled" >
							  	 	<option value="" ></option>
							  	 	<% if(thongbao.getLoaithongbao().equals("0")) { %>
							  	 		<option value="0" selected="selected" >Văn bản</option>
							  	 	<% } else { %> 
							  	 		<option value="0" >Văn bản</option>
							  	 	<% } %>
							  	 	<% if(thongbao.getLoaithongbao().equals("1")) { %>
							  	 		<option value="1" selected="selected" >Hướng dẫn</option>
							  	 	<% } else { %> 
							  	 		<option value="1" >Hướng dẫn</option>
							  	 	<% } %>
							  	 	<% if(thongbao.getLoaithongbao().equals("2")) { %>
							  	 		<option value="2" selected="selected" >Căn cứ</option>
							  	 	<% } else { %> 
							  	 		<option value="2" >Căn cứ</option>
							  	 	<% } %>
							  	 	<% if(thongbao.getLoaithongbao().equals("3")) { %>
							  	 		<option value="3" selected="selected" >Thay thế</option>
							  	 	<% } else { %> 
							  	 		<option value="3" >Thay thế</option>
							  	 	<% } %>
							  	 	<% if(thongbao.getLoaithongbao().equals("4")) { %>
							  	 		<option value="4" selected="selected" >Sửa đổi, bổ sung</option>
							  	 	<% } else { %> 
							  	 		<option value="4" >Sửa đổi, bổ sung</option>
							  	 	<% } %>
							  	 	
							  	 	<% if(thongbao.getLoaithongbao().equals("5")) { %>
							  	 		<option value="5" selected="selected" >Thông báo</option>
							  	 	<% } else { %> 
							  	 		<option value="5" >Thông báo</option>
							  	 	<% } %>

							  	 </select>
							  </TD>
							  <TD class="plainlabel">File đính kèm</TD>
					  	  		<TD class="plainlabel" colspan="3" >
					  	  		<div><div id="input"  style="float: left;"><INPUT type="file" name="fileup" value=""></div>
					  	  		&nbsp;&nbsp;
					  	  		<img src="../images/xpcollapse1.gif" onclick="LocNhanVien()" style="cursor: pointer;" alt="Thêm file" width="20" height="20" longdesc="Thêm file" border = 0>
					  	  		</div>
					  	  		<% if(thongbao.getFile().length > 0 ) {	
					  	  			for(int i=0;i <thongbao.getFile().length; i++){%>
					  	  			<input type="hidden" name = "filename" value = "<%=thongbao.getFile()[i]%>">
					  	  			<div id="tenfilea"><p><%= thongbao.getFile()[i] %>&nbsp;<img src="../images/Save20.png" onclick="LuuFile('<%= thongbao.getFile()[i]%>')" style="cursor: pointer;" alt="Lưu File" width="20" height="20" longdesc="Lưu File" border = 0></p></div>
					  	  			<%}
				  	  			}%>
					  	  	</TD>
						 </TR>
						 <TR>
							  <TD class="plainlabel" >Tiêu đề</TD>
							  <TD  class="plainlabel" colspan="3" >
							  	<input style="width: 600px" type="text" class="txtsearch" maxlength="100"  size="30" id="tieude" name="tieude" value="<%= thongbao.getTieude() %>" disabled="disabled"></TD>
						 </TR>
						 <TR>
							  <TD class="plainlabel" valign="top" >Nội dung thông báo</TD>
							  <TD class="plainlabel" colspan="3">			
				    				<textarea  id="noidung" name="noidung" style="width: 100%" rows="3" cols="20" disabled="disabled" ><%= thongbao.getNoidung() %></textarea></TD>
						 </TR>
				  		
				  		<TR>
			  				<TD colspan="4" >
			  					<ul class="tabs">
			  					
			  						<li><a href="#">Thảo luận</a></li>
			  						
			  						<% if(!thongbao.getLoaithongbao().equals("5")) { %>
			  						
				  						<% if(thongbao.getHienHuongDan().equals("1")) { %>
			                  				<li><a href="#">Văn bản hướng dẫn</a></li>
			                  			<% } %>
			                  			
			                  			<% if(thongbao.getHienCanCu().equals("1")) { %>
			                  				<li><a href="#">Văn bản căn cứ</a></li>
			                  			<% } %>
			                  			
			                  			<% if(thongbao.getSuaDoiBoSung().equals("1")) { %>
			                  				<li><a href="#">Văn bản sửa đổi BS</a></li>
			                  			<% } %>
			                  			
			                  			<% if(thongbao.getThayThe().equals("1")) { %>
			                  				<li><a href="#">Văn bản thay thế</a></li>
			                  			<% } %>
			                  			
			                  			
			                  			<% if(thongbao.getDuocHuongDanTu().trim().length() > 0 ) { %>
			                  				<li><a href="#">VB Hướng dẫn cho</a></li>
			                  			<% } %>
			                  			
			                  			<% if(thongbao.getDuocCanCuTu().trim().length() > 0 ) { %>
			                  				<li><a href="#">VB Căn cứ cho</a></li>
			                  			<% } %>
			                  			
			                  			<% if(thongbao.getDuocSuaDoiTu().trim().length() > 0 ) { %>
			                  				<li><a href="#">VB Sửa đổi cho</a></li>
			                  			<% } %>
			                  			
			                  			<% if(thongbao.getDuocThayTheTu().trim().length() > 0 ) { %>
			                  				<li><a href="#">VB thay thế cho</a></li>
			                  			<% } %>
			                  			
		                  			<% } %> 	
		                  		</ul>
		                  		
		                  		<div class="panes">
		                  			
		                  			<div style="font-size: 1.0em; font-weight: 500">

		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<tr>
		                  						<td width="120px" valign="middle" >
		                  							Nội dung trả lời: &nbsp;&nbsp; 
		                  						</td>
		                  						<td style="width: 700px;" valign="middle" >
		                  							<textarea rows="1" cols="60" style="width: 100%;" name="noidungtraloi" ><%= thongbao.getNoidungtraloi()  %></textarea>
		                  						</td>
		                  						<td valign="middle" >
		                  							<input type="button" value=" Gửi trả lời " onclick="GuiTraLoi();" >
		                  						</td>
		                  					</tr>
		                  					<tr>
		                  						<td colspan="2" >&nbsp;</td>
		                  					</tr>
		                  				</TABLE>
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  				<% if(thaoluanList != null) { 
		                  					while(thaoluanList.next())
		                  					{
		                  						%>
		                  						<tr>
			                  						<td width="85%" >
					                  					<b <%= thaoluanList.getString("phanloai").equals("0") ? "style='color: red;'" : "" %>   ><%= thaoluanList.getString("nguoithuchien") %>: </b><%= thaoluanList.getString("noidung") %>
			                  						</td>
			                  						<td width="15%" align="right" >
			                  							<i><%= thaoluanList.getString("thoidiem") %> </i>
			                  						</td>
			                  					</tr>
		                  					<% }
		                  					thaoluanList.close();
		                  				} %>
		                  					
		                  				</TABLE>
		                  				
		                  			</div>
		                  			
		                  			<% if(thongbao.getHienHuongDan().equals("1")) { %>
		                  			<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" >Mã văn bản</TH>
		                  						<TH width="80%" align="center" class="plainlabel" >Nội dung</TH>
		                  						<TH width="10%" align="center" class="plainlabel" >Chọn hết <input type="checkbox" id='chkAll2'  > </TH>
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
		                  			<% } %>
		                  			
		                  			<% if(thongbao.getHienCanCu().equals("1")) { %>
		                  			<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" >Mã văn bản</TH>
		                  						<TH width="80%" align="center" class="plainlabel" >Nội dung</TH>
		                  						<TH width="10%" align="center" class="plainlabel" >Chọn hết <input type="checkbox" id='chkAll3'  > </TH>
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
		                  			<% } %>
		                  			
		                  			<% if(thongbao.getSuaDoiBoSung().equals("1")) { %>
		                  			<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" >Mã văn bản</TH>
		                  						<TH width="80%" align="center" class="plainlabel" >Nội dung</TH>
		                  						<TH width="10%" align="center" class="plainlabel" >Chọn hết <input type="checkbox" id='chkAll4' onchange="sellectAll('chkAll4', 'vbsdbsIds');"  > </TH>
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
		                  			<% } %>
		                  			
		                  			<% if(thongbao.getThayThe().equals("1")) { %>
		                  			<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" >Mã văn bản</TH>
		                  						<TH width="80%" align="center" class="plainlabel" >Nội dung</TH>
		                  						<TH width="10%" align="center" class="plainlabel" >Chọn hết <input type="checkbox" id='chkAll3'  > </TH>
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
		                  			
		                  			
		                  			<% if(thongbao.getDuocHuongDanTu().trim().length() > 0 ) 
		                  			{ 
		                  				%>
		                  				<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" >Mã văn bản</TH>
		                  						<TH width="90%" align="center" class="plainlabel" >Nội dung</TH>
		                  					</TR>
		                  			<% 	
		                  				String[] vbList = thongbao.getDuocHuongDanTu().split("___");
		                  				int m = 0;
		                  				for(int i = 0; i < vbList.length; i++)
		                  				{
		                  					String[] vb = vbList[i].split("__"); %>
		                  					
		                  					<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
                  								<TD style="font-size: 1.0em" ><%= vb[0] %></TD>
                  								<TD class="thongbao" ><a href="../../ThongbaoUpdateSvl?task=view&id=<%= vb[0] %>&viewMode=<%= thongbao.getViewMode()  %>" target="target" ><%= vb[1] %></a></TD>			
                  							</TR>
		                  					
		                  				<% m++; } %>
		                  				
		                  				</TABLE>
		                  				</div>
		                  				
		                  			<% } %>
		                  			
		                  			
		                  			<% if(thongbao.getDuocCanCuTu().trim().length() > 0 ) 
		                  			{ 
		                  				%>
		                  				<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" >Mã văn bản</TH>
		                  						<TH width="90%" align="center" class="plainlabel" >Nội dung</TH>
		                  					</TR>
		                  			<% 	
		                  				String[] vbList = thongbao.getDuocCanCuTu().split("___");
		                  				int m = 0;
		                  				for(int i = 0; i < vbList.length; i++)
		                  				{
		                  					String[] vb = vbList[i].split("__"); %>
		                  					
		                  					<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
                  								<TD style="font-size: 1.0em" ><%= vb[0] %></TD>
                  								<TD class="thongbao" ><a href="../../ThongbaoUpdateSvl?task=view&id=<%= vb[0] %>&viewMode=<%= thongbao.getViewMode()  %>" target="target" ><%= vb[1] %></a></TD>			
                  							</TR>
		                  					
		                  				<% m++; } %>
		                  				
		                  				</TABLE>
		                  				</div>
		                  			<% } %>
		                  			
		                  			
		                  			<% if(thongbao.getDuocSuaDoiTu().trim().length() > 0 ) 
		                  			{ 
		                  				%>
		                  				<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" >Mã văn bản</TH>
		                  						<TH width="90%" align="center" class="plainlabel" >Nội dung</TH>
		                  					</TR>
		                  			<% 	
		                  				String[] vbList = thongbao.getDuocSuaDoiTu().split("___");
		                  				int m = 0;
		                  				for(int i = 0; i < vbList.length; i++)
		                  				{
		                  					String[] vb = vbList[i].split("__"); %>
		                  					
		                  					<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
                  								<TD style="font-size: 1.0em" ><%= vb[0] %></TD>
                  								<TD class="thongbao" ><a href="../../ThongbaoUpdateSvl?task=view&id=<%= vb[0] %>&viewMode=<%= thongbao.getViewMode()  %>" target="target" ><%= vb[1] %></a></TD>			
                  							</TR>
		                  					
		                  				<% m++; } %>
		                  				
		                  				</TABLE>
		                  				</div>
		                  			<% } %>
		                  			
		                  			
		                  			<% if(thongbao.getDuocThayTheTu().trim().length() > 0 ) 
		                  			{ 
		                  				%>
		                  				<div style="font-size: 1.0em; font-weight: 500">
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" >Mã văn bản</TH>
		                  						<TH width="90%" align="center" class="plainlabel" >Nội dung</TH>
		                  					</TR>
		                  			<% 	
		                  				String[] vbList = thongbao.getDuocThayTheTu().split("___");
		                  				int m = 0;
		                  				for(int i = 0; i < vbList.length; i++)
		                  				{
		                  					String[] vb = vbList[i].split("__"); %>
		                  					
		                  					<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
                  								<TD style="font-size: 1.0em" ><%= vb[0] %></TD>
                  								<TD class="thongbao" ><a href="../../ThongbaoUpdateSvl?task=view&id=<%= vb[0] %>&viewMode=<%= thongbao.getViewMode()  %>" target="target" ><%= vb[1] %></a></TD>			
                  							</TR>
		                  					
		                  				<% m++; } %>
		                  				
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