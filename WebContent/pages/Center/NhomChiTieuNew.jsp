<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhomchitieu.INhomchitieu" %>
<%@ page  import = "geso.dms.center.beans.nhomchitieu.imp.Nhomchitieu" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.util.Hashtable"%>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% INhomchitieu nkmBean = (INhomchitieu)session.getAttribute("nkmBean"); 
	ResultSet spList = (ResultSet)nkmBean.getSpList(); 
	ResultSet spSelected = (ResultSet)nkmBean.getSpSelected();
	ResultSet dvkdList = (ResultSet)nkmBean.getDvkdList();
	ResultSet nhList = (ResultSet)nkmBean.getNhList();
	ResultSet clList = (ResultSet)nkmBean.getCLList();
	String dvkdId = (String) nkmBean.getDvkdId();
	String nhId = (String)nkmBean.getNhId();
	String clId = (String)nkmBean.getClId();
	ResultSet kenh = (ResultSet)nkmBean.getKenh();
	
	Hashtable<String, String> hssp_sl = nkmBean.getSanpham_Soluong();
	  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();})
</script>
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


<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
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

function DinhDangTien(num) 
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

function roundNumber(num, dec) 
{
	var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
	return result;
}

function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
{    
	var keypressed = null;
	if (window.event)
		keypressed = window.event.keyCode; //IE
	else
		keypressed = e.which; //NON-IE, Standard
	
	if (keypressed < 48 || keypressed > 57)
	{ 
		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
		{//Phím Delete và Phím Back
			return;
		}
		return false;
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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="nkmId" value="">
<input type="hidden" name="action" value="0">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý chỉ tiêu &gt; Chỉ tiêu NPP &gt; Nhóm chỉ tiêu &gt; Tạo mới</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= nkmBean.getMessage() %></textarea>
						<% nkmBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin nhóm chỉ tiêu </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="30%" class="plainlabel">Tên nhóm chỉ tiêu
									<FONT class="erroralert"> *</FONT>
								</TD>
								<TD class="plainlabel"><INPUT type="text" name="ten"
									size="80" style="width: 250px" value='<%= nkmBean.getTen()%>'>
								</TD>
							</TR>
							
							
							<TR>
								<TD width="30%"  class="plainlabel">Diễn giải</TD>
								<TD class="plainlabel"><INPUT type="text"
									name="diengiai" style="width: 250px" size="80"
									value='<%= nkmBean.getDiengiai() %>'>
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel">Từ Ngày</TD>
									<TD class="plainlabel">
									 <input type="text" class="days" name="tungay" value='<%=nkmBean.getTuNgay()%>' />
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel">Đến Ngày</TD>
									<TD class="plainlabel">
								 			<input type="text" class="days" name="denngay"  value='<%=nkmBean.getDenNgay()%>' />	
 									</TD>
 							</TR>
 							
 							<TR>
								<TD class="plainlabel">Tính thưởng</TD>
								<TD class="plainlabel"  valign="middle">
                        		<% if(nkmBean.getTinhThuong().trim().equals("1")){ %>
                        			<input type="checkbox" checked="checked" name="tinhthuong" value="1" onchange="filterCl();"/>
                        		<%}else{ %>
                        			<input type="checkbox"   name="tinhthuong" value="1" onchange="submitform();"/>
                        		<%} %>
                       	 		</TD>	
 									
 							</TR>
 							
 							<% if(nkmBean.getTinhThuong().trim().equals("1")){ %>
                        	<TR>
 								<TD class="plainlabel">Loại diều kiện</TD>
						  	  	<TD class="plainlabel"><SELECT style="width: 250px"  name="loaidk" onchange="filterCl();">
						  	  	<% if (nkmBean.getLoaiDk().trim().equals("0")){%>
						  	  		<OPTION value="0" selected>Bắt buộc nhập số lượng từng sản phẩm</OPTION>
						  	  		<OPTION value="1" >Bất kỳ trong</OPTION>
						  	  	<% } else { %>
						  	  		<OPTION value="0" >Bắt buộc nhập số lượng từng sản phẩm</OPTION>
						  	  		<OPTION value="1" selected>Bất kỳ trong</OPTION>
						  	  	<% } %>					  	  			
						  	  	</SELECT>
						  	  	</TD>
 							</TR>
 							<% } %>
 							
 							<% if (nkmBean.getLoaiDk().trim().equals("1")){ %>
 							<TR>
 								<TD class="plainlabel">Tổng lượng</TD>
								<TD class="plainlabel">
									<input type="text" name="tongluong"  value='<%=nkmBean.getTongluong()%>' onkeypress="return keypress(event);"/>	
 								</TD>
 							</TR>
 							<% } %>
 							
				  		   <TR>
						  	  	<TD class="plainlabel">Đơn vị kinh doanh</TD>
						  	  	<TD class="plainlabel"><SELECT style="width: 250px"  name="dvkdId" onchange="filterDvkd();">
						  	  		<OPTION value="0" ></OPTION>	
						  	  		<% if(dvkdList!= null){						  	  			
						   					while (dvkdList.next()){
						  	  					if (dvkdList.getString("pk_seq").equals(dvkdId)){%>
						  	  						<OPTION value=<%= dvkdList.getString("pk_seq")%> selected><%= dvkdList.getString("diengiai")%></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value=<%= dvkdList.getString("pk_seq")%> ><%= dvkdList.getString("diengiai") %></OPTION>
						  	  	<%				  }
						  	  				
						  	  				}
						  	  			
						  	  		}%>						  	  			
						  	  	</SELECT>
						  	  	</TD>
						  	</TR>
						  		  <TR>
						  	  	<TD class="plainlabel">Kênh bán hàng</TD>
						  	  	<TD class="plainlabel"><SELECT style="width: 250px"  name="kenhId" onchange="filterDvkd();">
						  	  		<OPTION value="" ></OPTION>	
						  	  		<% if(kenh!= null){						  	  			
						   					while (kenh.next()){
						  	  					if (kenh.getString("pk_seq").equals(nkmBean.getKenhId())){%>
						  	  						<OPTION value=<%= kenh.getString("pk_seq")%> selected><%= kenh.getString("diengiai")%></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value=<%= kenh.getString("pk_seq")%> ><%= kenh.getString("diengiai") %></OPTION>
						  	  	     <%	 }
						  	  				
						  	  				}
						  	  			
						  	  		}%>						  	  			
						  	  	</SELECT>
						  	  	</TD>
						  	</TR>
						  	<TR>
						  	  	<TD class="plainlabel">Nhãn hàng</TD>
						  	  	<TD class="plainlabel"><SELECT style="width: 250px"  name="nhId" onchange="filterNh();">
						  	  		<OPTION value="0" ></OPTION>	
						  	  		<% if(nhList!= null){						  	  		
							   				while (nhList.next()){
						  	  					if (nhList.getString("pk_seq").equals(nhId)){%>
						  	  						<OPTION value='<%= nhList.getString("pk_seq")%>' selected ><%= nhList.getString("ten") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value='<%= nhList.getString("pk_seq")%>'  ><%= nhList.getString("ten") %></OPTION>
						  	  		<%			 }
						  	  				
						  	  				}
						  	  			
						  	  		}%>
						  	  		
						  	  	</SELECT>
						  	  	</TD>
						  	</TR>
						  	<TR>
						  	  	<TD class="plainlabel">Chủng loại</TD>
						  	  	<TD class="plainlabel"><SELECT style="width: 250px"  name="clId" onchange="filterCl();">	
						  	  		<OPTION value="0" ></OPTION>
						  	  		<% if(clList!= null){					  	  			
							   				while (clList.next()){
							   					
						  	  					if (clList.getString("pk_seq").equals(clId)){%>
						  	  						<OPTION value="<%= clList.getString("pk_seq")%>" selected><%= clList.getString("ten") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value="<%= clList.getString("pk_seq")%>" ><%= clList.getString("ten") %></OPTION>
						  	  	<%				  }
						  	  			
						  	  				}
						  	  			
						  	  		}%>
						  	  	</SELECT>
						  	  	</TD>
						  	</TR>
						  	<TR style="display: none" >
						  		
						  	
							  <TD colspan="2" class="plainlabel" ><label>
							  	<% if(nkmBean.getTrangthai().equals("1")){ %>
							    	<input name="trangthai" type="checkbox" value="1" checked >
							    <%}else{ %>
							    	<input name="trangthai" type="checkbox" value="0" >
							    <%} %>
							    Hoạt động </label></TD>
						  </TR>
						  
						</TABLE>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">							
								<TR class="tbheader">
									<TH width="28%">Mã sản phẩm </TH>
									<TH width="60%">Tên sản phẩm </TH>
									<TH width="60%">Số lượng </TH>
									<TH width="12%">Chọn
										<input type="checkbox" name="chon" onClick="checkedAll(document.nkmForm.sanpham)">								
									
									</TH>
								</TR>

								<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
								   String soluong = "";
						   	       rs = spSelected;
									   
									   if (!(rs == null)){			
										    
									   		while (rs.next()){
									   			soluong = "";
									   			if(hssp_sl != null && hssp_sl.get(rs.getString("pk_seq")) != null )
									   				soluong = hssp_sl.get(rs.getString("pk_seq")); 
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
											<%  } else {%>
													<TR class= <%= darkrow%> >
											<%  } %>	
													<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
													<TD align="center"><div align="left"><%= rs.getString("ten")%></div></TD>
													<TD align="center">
														<input type="text" name="soluong" value='<%= soluong %>' onkeypress="return keypress(event);">		
														<input type="hidden" name="spid" value='<%= rs.getString("pk_seq") %>' >								
													</TD>
													<TD align="center">
														<input type="checkbox" name="sanpham" value='<%= rs.getString("pk_seq") %>' checked>									
													</TD>

													</TR>
													
								<% 			m++;}
										}	
			
							   	       rs = spList;
									   
									   if (!(rs == null)){			
										    
									   		while (rs.next()){	
									   			soluong = "";
									   			if(hssp_sl != null && hssp_sl.get(rs.getString("pk_seq")) != null )
									   				soluong = hssp_sl.get(rs.getString("pk_seq")); 
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
											<%  } else {%>
													<TR class= <%= darkrow%> >
											<%  } %>	
													<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
													<TD align="center"><div align="left"><%= rs.getString("ten")%></div></TD>
													<TD align="center">
														<input type="text" name="soluong" value='<%= soluong %>' onkeypress="return keypress(event);">		
														<input type="hidden" name="spid" value='<%= rs.getString("pk_seq") %>' >								
													</TD>
													<TD align="center">
														<input type="checkbox" name="sanpham" value='<%= rs.getString("pk_seq") %>'>									
													</TD>

													</TR>
													
								<% 			m++;}
										}	
									   
									%>	
									<tr class="tbfooter" >
									<TD align="center" colspan="10"></TD>
									</tr>
								   
						</TABLE>							
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
<%  
	if(spList != null) spList.close(); 
	if(spSelected != null) spSelected.close();
	if(dvkdList != null) dvkdList.close();
	if(nhList != null) nhList.close();
	if(clList != null) clList.close() ; %>
<%}%>