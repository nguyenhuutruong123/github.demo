<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.beans.nhomnhanvien.INhomNhanVien"%>
<%@ page import="geso.dms.center.beans.nhomnhanvien.INhomNhanVienList"%>
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
		response.sendRedirect("/OPV/index.jsp");
	}else{ %>

<% INhomNhanVien nhBean = (INhomNhanVien)session.getAttribute("nhBean"); %>
<% 	
	ResultSet spRs =(ResultSet) nhBean.getSpRs();
/* ResultSet nhanhangRs =(ResultSet) nhBean.getNganhHangRs();
ResultSet nganhhangRs =(ResultSet) nhBean.getNganhHangRs(); */
ResultSet loaiNVRs =(ResultSet) nhBean.getLoaiNV();
ResultSet kbhRs =(ResultSet) nhBean.getKenhbanhang();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>OPV - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<style type="text/css" >
    .black_overlay{
        display: none;
        position: absolute;
        top: 0%;
        left: 0%;
        width: 100%;
        height: 900%;
        background-color: black;
        z-index:1001;
        -moz-opacity: 0.8;
        opacity:.80;
        filter: alpha(opacity=80);
    }
    .white_content {
        display: none;
        position: absolute;
        top: 25%;
        left: 25%;
        width: 50%;
        height: 50%;
        padding: 16px;
        /*border: 10px solid #CCC;
        background-color: white;*/
        z-index:1002;
        overflow: auto;
    }
 
 </style>
<script>
$(document).ready(function()
{
	$("#kenhId").select2();
	$("#vungId").select2();
	$("#kvId").select2();	
	$("#tinhId").select2();
	$("#quanhuyenId").select2();
});
</script>



<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["nspForm"].submit();
}

function save()
{
	document.nspForm.action.value="save";
    document.forms["nspForm"].submit();       
}


function checkedAll(chk) {
	for(i=0; i<chk.length; i++){
	 	if(document.nspForm.chon.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
}




</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->
<form name="nspForm" method="post" action="../../NhomNhanVienUpdateSvl">
<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%=nhBean.getId()%>">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">Quản lý thông báo > Chức năng  >Nhóm nhân viên > Hiển thị</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../NhomNhanVienSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu</LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=nhBean.getMsg()%></textarea>
						<% nhBean.getMsg(); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin nhóm hàng </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
							  	<TD class="plainlabel">Tên nhóm</TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="ten" style="width:300px" value='<%= nhBean.getTen() %>'>
                                </TD>
                                </TR>
                                  <%-- <TR>
                                	<td class="plainlabel" width="80px" > <%=ChuyenNgu.get("Loại NV",nnId) %></TD>
												<td  class="plainlabel" colspan = "4" width="220px" >
												<select name="loaiNv" id="loaiNv"  onchange = "submitform();">
														<option value="0"
															<%=nhBean.getLoaiNv().equals("0") ? " selected " : ""%>></option>
														<option value="4"
															<%=nhBean.getLoaiNv().equals("4") ? " selected " : ""%>><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></option>
														<option value="5"
															<%=nhBean.getLoaiNv().equals("5") ? " selected " : ""%>><%=ChuyenNgu.get("Ban Giám Đốc",nnId) %></option>
														<option value="1"
															<%=nhBean.getLoaiNv().equals("1") ? " selected " : ""%>><%=ChuyenNgu.get("Giám Đốc Miền",nnId) %></option>
														<option value="2"
															<%=nhBean.getLoaiNv().equals("2") ? " selected " : ""%>><%=ChuyenNgu.get("Phụ trách Vùng",nnId) %></option>
														<option value="3"
															<%=nhBean.getLoaiNv().equals("3") ? " selected " : ""%>><%=ChuyenNgu.get("Phụ Trách Tỉnh/ GĐCN Cấp",nnId) %> 2</option>																			
														<option value="6"
															<%=nhBean.getLoaiNv().equals("6") ? " selected " : ""%>><%=ChuyenNgu.get("Nhà phân phối",nnId) %></option>	
															<option value="7"
															<%=nhBean.getLoaiNv().equals("7") ? " selected " : ""%>><%=ChuyenNgu.get("Trung tâm",nnId) %></option>
												</select></TD>
						  </TR> --%>
						   <TR>
						  	<TD class="plainlabel">Loại Nv</TD>
						  	  	<TD class="plainlabel">
								  <SELECT name="loainvId" id="loainvId"  style="width:150px"   >
						  	  		<OPTION value="" ></OPTION>	
						  	  		<% if(loaiNVRs!= null) {						  	  			
						   					while (loaiNVRs.next()) {
						  	  					if (nhBean.getLoainvId().indexOf(loaiNVRs.getString("loai"))  >=0) {%>
						  	  						<OPTION value=<%= loaiNVRs.getString("loai")%> selected><%= loaiNVRs.getString("ten")%></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value=<%= loaiNVRs.getString("loai")%> ><%= loaiNVRs.getString("ten") %></OPTION>
						  	  	<%  } } }%>						  	  			
						  	  	</SELECT>
						  	  	</TD>
						  	 </TR> 
						  	 <TR>
						  	<TD class="plainlabel">Kênh bán hàng</TD>
						  	  	<TD class="plainlabel">
								  <SELECT name="kbhId" id="kbhId"  style="width:200px" onchange = "submitform();"  >
						  	  		<OPTION value="" ></OPTION>	
						  	  		<% if(kbhRs!= null) {						  	  			
						   					while (kbhRs.next()) {
						  	  					if (nhBean.getKbhId().indexOf(kbhRs.getString("pk_seq"))  >=0) {%>
						  	  						<OPTION value=<%= kbhRs.getString("pk_seq")%> selected><%= kbhRs.getString("diengiai")%></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value=<%= kbhRs.getString("pk_seq")%> ><%= kbhRs.getString("diengiai") %></OPTION>
						  	  	<%  } } }%>						  	  			
						  	  	</SELECT>
						  	  	</TD>
						  	 </TR> 
						</TABLE>
						<div id="tbNpp">
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4" >							
								<TR class="tbheader">
								<TH width="15%">Tên đăng nhập</TH>
								<TH width="55%">Tên nhân viên</TH>
								<TH width="10%">Điện thoại</TH>
								<TH width="10%">Chọn tất cả
									<input type="checkbox" name="chon" onClick="checkedAll(document.nspForm.spId)">
									</TH>
								</TR>

								<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
								   rs = spRs;
								   
								   if (!(rs == null)){			
									    
								   		while (rs.next()){								   			
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
										<%  } else {%>
												<TR class= <%= darkrow%> >
										<%  } %>	
												<TD align="left" class="textfont"><%= rs.getString("dangnhap") %></TD>
												<TD align="center"><div align="left"><%= rs.getString("ten") %></div></TD>
												<TD align="center"><div align="left"><%= rs.getString("dienthoai") %></div></TD>
												<TD align="center">
												<% if (nhBean.getLoainvId().trim().length()>0 && !nhBean.getLoainvId().equals("4") ) {
													if((nhBean.getSpId().indexOf(rs.getString("pk_seq")) >=0)){ %>
													<input type="checkbox" name="spId" value='<%= rs.getString("pk_seq") %>' checked>
												 <%}else if(nhBean.getNvselected().indexOf(rs.getString("pk_seq")) >=0){ %>
													 	<input type="checkbox" name="nvselectId" value='<%= rs.getString("pk_seq") %>' checked>
												<%  } else{ %>
												 	<input type="checkbox" name="spId" value='<%= rs.getString("pk_seq") %>'>
												 <%} %>
												<%}else if(nhBean.getLoainvId().trim().length()>0 && nhBean.getLoainvId().equals("4") ){
													if((nhBean.getNvselected().indexOf(rs.getString("pk_seq")) >=0)){ %>
													<input type="checkbox" name="nvselectId" value='<%= rs.getString("pk_seq") %>' checked>
												 <%}else if(nhBean.getSpId().indexOf(rs.getString("pk_seq")) >=0){ %>
													<input type="checkbox" name="spId" value='<%= rs.getString("pk_seq") %>' checked>
												 <% }else{ %>
												 	<input type="checkbox" name="nvselectId" value='<%= rs.getString("pk_seq") %>'>
												 <%} %>
												<%} else {%>
													<input type="checkbox" name="spId" value='<%= rs.getString("pk_seq") %>'>
												<%} %>
												</TD>
												</TR>
							<% 			m++;}
									}	
							   	      %>
						</TABLE>			
						</div>				
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%nhBean.DBclose(); %>
<%}%>