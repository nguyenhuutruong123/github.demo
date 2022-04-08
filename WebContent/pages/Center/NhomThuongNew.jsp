<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhomthuong.INhomthuong" %>
<%@ page  import = "geso.dms.center.beans.nhomthuong.imp.Nhomthuong" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
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

<% INhomthuong nkmBean = (INhomthuong)session.getAttribute("nkmBean"); 
	ResultSet spList = (ResultSet)nkmBean.getSpList(); 
	ResultSet spSelected = (ResultSet)nkmBean.getSpSelected();
	ResultSet dvkdList = (ResultSet)nkmBean.getDvkdList();
	ResultSet nhList = (ResultSet)nkmBean.getNhList();
	ResultSet clList = (ResultSet)nkmBean.getCLList();
	String dvkdId = (String) nkmBean.getDvkdId();
	String nhId = (String)nkmBean.getNhId();
	String clId = (String)nkmBean.getClId();
	ResultSet kenh = (ResultSet)nkmBean.getKenh();

	  %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("NhomthuongSvl","",nnId);	
 %>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>OPV - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">


<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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
        }); 		
		
</script>


<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	var tungay = document.getElementById('tungay').value;
	var denngay = document.getElementById('denngay').value;
	if(tungay == '')
	{
		 alert('Vui lòng chon từ ngày');
		 return;
	}
	
	if(denngay == '')
	{
		 alert('Vui lòng chon đến ngày');
		 return;
	}
	if(tungay > denngay)
	{
		 alert('Kiểm tra thời gian nhóm sản phẩm');
		 return;
	}
	
    document.forms["nkmForm"].submit();
}
function xuatexel()
{
	document.nkmForm.action.value='xuatexel';
    document.forms["nkmForm"].submit();
}

function filterDvkd()
{
    document.nkmForm.action.value='filter';
    document.nkmForm.nhId.value='0';
    document.nkmForm.clId.value='0';
    document.forms["nkmForm"].submit();       
}

function filterNh()
{
    document.nkmForm.action.value='filter';
    document.nkmForm.clId.value='0';
    document.forms["nkmForm"].submit();   
    
}

function filterCl()
{
    document.nkmForm.action.value='filter';
    document.forms["nkmForm"].submit();       
}

function checkedAll(chk) {
	for(i=0; i<chk.length; i++){
	 	if(document.nkmForm.chon.checked==true){
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

	<form name="nkmForm" method="post"
		action="../../NhomthuongUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="nkmId" value=''> <input
			type="hidden" name="action" value="0">

		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">
											<%=url %> &gt; Tạo mới</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%=userTen %>&nbsp;</TD>
									</tr>
								</table></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="../../NhomthuongSvl?userId=<%=userId %>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset">
										</A>
										</TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left"><A
											href="javascript: submitform()"><IMG
												src="../images/Save30.png" title="Luu lai" alt="Luu lai"
												border="1" style="border-style: outset">
										</A>
										</TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left"><A
											href="javascript: xuatexel()"><IMG
												src="../images/excel.gif" width="30" title="Luu lai"
												alt="Luu lai" border="1" style="border-style: outset">
										</A>
										</TD>
										<TD>&nbsp;</TD>

									</TR>
								</TABLE></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu</LEGEND>

									<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%"
										readonly="readonly" rows="1"><%= nkmBean.getMessage() %></textarea>
									<% nkmBean.setMessage(""); %>
								</FIELDSET></TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông
										tin nhóm thưởng </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										
										<TR>
											<TD width="30%" class="plainlabel"><%=ChuyenNgu.get("Từ ngày",nnId) %>
												<FONT class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel">
												<input readonly  class="days" type="text"  id="tungay"  name="tungay" value='<%=nkmBean.getTungay() %>'  size="10" >
											</TD>
										</TR>
										<TR>
											<TD width="30%" class="plainlabel"><%=ChuyenNgu.get("Đến ngày",nnId) %>
												<FONT class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel">
												<input readonly  class="days" type="text" id="denngay" name="denngay" value='<%=nkmBean.getDenngay() %>'  size="10" >
											</TD>
										</TR>
										
										<TR>
											<TD width="30%" class="plainlabel"><%=ChuyenNgu.get("Tên nhóm thưởng",nnId) %>
												<FONT class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel"><INPUT type="text" name="ten"
												size="80" style="width: 250px" value='<%= nkmBean.getTen()%>'>
											</TD>
										</TR>
										<TR>
											<TD width="30%"  class="plainlabel"><%=ChuyenNgu.get("Diễn giải",nnId) %></TD>
											<TD class="plainlabel"><INPUT type="text"
												name="diengiai" style="width: 250px" size="80"
												value='<%= nkmBean.getDiengiai() %>'>
											</TD>
										</TR>
											<TR>
											<TD  class="plainlabel"><%=ChuyenNgu.get("Nhóm SP tập trung",nnId) %></TD>
											<TD class="plainlabel">	
											<% if(nkmBean.getNspTT().equals("1")) { %>
														 <input name="nsptt" type="checkbox" value ="1" checked> 
													<% } else { %>
														 <input name="nsptt" type="checkbox" value ="1" > 
													<% } %>
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %></TD>
											<TD class="plainlabel"><SELECT name="dvkdId"
												onchange="filterDvkd();">
													<OPTION value="0"></OPTION>
													<% if(dvkdList!= null){						  	  			
						   					while (dvkdList.next()){
						  	  					if (dvkdList.getString("pk_seq").equals(dvkdId)){%>
													<OPTION value=<%= dvkdList.getString("pk_seq")%> selected><%= dvkdList.getString("diengiai")%></OPTION>
													<%}else{ %>
													<OPTION value=<%= dvkdList.getString("pk_seq")%>><%= dvkdList.getString("diengiai") %></OPTION>
													<%				  }
						  	  				
						  	  				}
						  	  			
						  	  			}%>
											</SELECT></TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId) %></TD>
											<TD class="plainlabel"><SELECT name="kenhId"
												onchange="filterDvkd();">
													<OPTION value=""></OPTION>
													<% if(kenh!= null){						  	  			
						   					while (kenh.next()){
						  	  					if (kenh.getString("pk_seq").equals(nkmBean.getKenhId())){%>
													<OPTION value=<%= kenh.getString("pk_seq")%> selected><%= kenh.getString("diengiai")%></OPTION>
													<%}else{ %>
													<OPTION value=<%= kenh.getString("pk_seq")%>><%= kenh.getString("diengiai") %></OPTION>
													<%	 }
						  	  				
						  	  				}
						  	  			
						  	  		}%>
											</SELECT></TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Nhãn hàng",nnId) %></TD>
											<TD class="plainlabel"><SELECT name="nhId"
												onchange="filterNh();">
													<OPTION value="0"></OPTION>
													<% if(nhList!= null){						  	  		
							   				while (nhList.next()){
						  	  					if (nhList.getString("pk_seq").equals(nhId)){%>
													<OPTION value='<%= nhList.getString("pk_seq")%>' selected><%= nhList.getString("ten") %></OPTION>
													<%}else{ %>
													<OPTION value='<%= nhList.getString("pk_seq")%>'><%= nhList.getString("ten") %></OPTION>
													<%			 }
						  	  				
						  	  				}
						  	  			
						  	  		}%>

											</SELECT></TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Chủng loại",nnId) %></TD>
											<TD class="plainlabel"><SELECT name="clId"
												onchange="filterCl();">
													<OPTION value="0"></OPTION>
													<% if(clList!= null){					  	  			
							   				while (clList.next()){
							   					
						  	  					if (clList.getString("pk_seq").equals(clId)){%>
													<OPTION value="<%= clList.getString("pk_seq")%>" selected><%= clList.getString("ten") %></OPTION>
													<%}else{ %>
													<OPTION value="<%= clList.getString("pk_seq")%>"><%= clList.getString("ten") %></OPTION>
													<%				  }
						  	  			
						  	  				}
						  	  			
						  	  		}%>
											</SELECT></TD>
										</TR>
										<TR style="display: none">


											<TD colspan="2" class="plainlabel"><label> <% if(nkmBean.getTrangthai().equals("1")){ %>
													<input name="trangthai" type="checkbox" value="1" checked>
													<%}else{ %> <input name="trangthai" type="checkbox" value="0">
													<%} %> <%=ChuyenNgu.get("Hoạt động",nnId) %></label>
											</TD>
										</TR>

									</TABLE>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
										<TR class="tbheader">
											<TH width="28%"><%=ChuyenNgu.get("Mã sản phẩm",nnId) %></TH>
											<TH width="60%"><%=ChuyenNgu.get("Tên sản phẩm",nnId) %></TH>
											<TH width="12%"><%=ChuyenNgu.get("Chọn",nnId) %> <input type="checkbox" name="chon"
												onClick="checkedAll(document.nkmForm.sanpham)"></TH>
										</TR>

										<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
						   	       rs = spSelected;
									   
									   if (!(rs == null)){			
										    
									   		while (rs.next()){								   			
												if (m % 2 != 0) {%>
										<TR class=<%=lightrow%>>
											<%  } else {%>
										
										<TR class=<%= darkrow%>>
											<%  } %>
											<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
											<TD align="center"><div align="left"><%= rs.getString("ten")%></div>
											</TD>
											<TD align="center"><input type="checkbox" name="sanpham"
												value='<%= rs.getString("pk_seq") %>' checked></TD>

										</TR>

										<% 			m++;}
										}	
			
							   	       rs = spList;
									   
									   if (!(rs == null)){			
										    
									   		while (rs.next()){								   			
												if (m % 2 != 0) {%>
										<TR class=<%=lightrow%>>
											<%  } else {%>
										
										<TR class=<%= darkrow%>>
											<%  } %>
											<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
											<TD align="center"><div align="left"><%= rs.getString("ten")%></div>
											</TD>
											<TD align="center"><input type="checkbox" name="sanpham"
												value='<%= rs.getString("pk_seq") %>'></TD>

										</TR>

										<% 			m++;}
										}	
									   
									%>
									<tr class="tbfooter" ><TD align="center" colspan="10"></TD></tr>
									</TABLE>
								</FIELDSET></TD>
						</TR>
					</TABLE></TD>
			</TR>
		</TABLE>
	</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%  
	if(spList != null) spList.close(); 
	if(spSelected != null) spSelected.close();
	if(dvkdList != null) dvkdList.close();
	if(nhList != null) nhList.close();
	if(clList != null) clList.close() ; %>
<%}%>