<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.beans.banggia.imp.*" %>
<%@ page import="geso.dms.center.beans.banggia.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%
 	IBangGia obj =(IBangGia)session.getAttribute("csxBean");
	ResultSet dvkdList = obj.getDvkdRs();
	ResultSet kbhList = obj.getKbhRs();
	ResultSet nppRs = obj.getNppRs();
	List<IBangGia_Sp> spList = (List<IBangGia_Sp>)obj.getSpList();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script>

$(function() {
 
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<SCRIPT language="JavaScript" type="text/javascript">

function thaydoichietkhau(e)
{
	var val = parseFloat(e.value); //NaN = not a number
	if(isNaN(val)) { val = 0; }
	else if(val < 0) val = 0;
	else if(val > 100) val = 100;
	
	e.value = val;
	if(confirm('Bạn có chắc muốn thay doi toan bo chiet khau  = '+val+'?')) {
		$(".chietkhauNPP").val(val);
		e.value = val == 0 ? "" : val;
	} else {
		e.value = "";
	}

}
	function submitform()
	{
	    document.forms["khtt"].submit();
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
	 function Dinhdang(element)
		{
			element.value=DinhDangTien(element.value);
			if(element.value== '' ||element.value=='0' )
			{
				element.value= '';
			}
		}
	
	function FormatNumber(e)
	{
		e.value = DinhDangTien(e.value);
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
	
	function save()
	{
	  document.forms["khtt"].action.value = "save";
	  document.forms["khtt"].submit(); 
    }
	
	function submitFrom()
	{
	  document.forms["khtt"].action.value = "submit";
	  document.forms["khtt"].submit(); 
    }
	
	function checkedAll()
	{
		 var chkAll = document.getElementById("chekAllSp");
		 var spIds = document.getElementsByName("chonban");
		 
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
	
	function checkedAll_KhachHang()
	{
		 var chkAll = document.getElementById("checkAllKh");
		 var spIds = document.getElementsByName("nppId");
		 
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
	
	function checkedAll_HopDong(hdId)
	{
		 var chkAll = document.getElementById(hdId + ".checkAll");
		 var spIds = document.getElementsByName(hdId + ".chonban");
		 
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

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="khtt" method="post" action="../../BangGiaUpdateSvl">
<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<input type="hidden" name="view" value='<%= obj.getView() %>' >
<input type="hidden" name="id" value="<%=obj.getId()   %>">
<input type="hidden" name="loai" value="<%= obj.getLoai()%>" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Sản phẩm > <%= obj.getLoai().equals("0")?"Bảng giá B2B":"Bảng giá bán NPP" %>  > Hiển thị</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../BangGiaSvl?userId=<%= userId%>&view=<%=obj.getView() %> " ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						   <%if(obj.getTrangthai().equals("0")){ %>
							    <div id="btnSave">
							    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
							    </div>
						    <%} %>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin bảng giá </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Tên bảng giá </TD>   
		                        <TD class="plainlabel" valign="middle"  ><input type="text" name="ten" value="<%= obj.getTen() %>"  >  </TD>          
		                 		<TD class="plainlabel" valign="middle" width="120px" >Đơn vị kinh doanh </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="hidden" name="dvkdId" value="<%= obj.getDvkdId() %>" >
		                        	<select   >
										<option value=""></option>
										<% if (dvkdList != null)
										{
											while (dvkdList.next())
											{
												if (dvkdList.getString("dvkdId").equals(obj.getDvkdId()))
												{
												%>
													<option value="<%=dvkdList.getString("dvkdId")%>" selected="selected"><%=dvkdList.getString("dvkd")%></option>
												<% } else { %>
													<option value="<%=dvkdList.getString("dvkdId")%>"><%=dvkdList.getString("dvkd")%></option>
										<% } } dvkdList.close(); } %>
									</select>
		                        </TD>          
		                  </TR> 
		                  
		                    <TR>
                          		<TD class="plainlabel" >Từ ngày </TD>   
		                        <TD class="plainlabel"><input type="text" class="days" name="tungay" value="<%= obj.getTuNgay() %>"  >  </TD>          
		                 		<TD class="plainlabel" >Kênh bán hàng </TD>   
		                        <TD class="plainlabel"  >
		                       <%-- 	<input type="hidden" name="kbhId"  value="<%= obj.getKbhId() %>" > --%>
		                        	<select  name="kbhId"  >
										<option value=""></option>
										<% if (kbhList != null)
										{
											while (kbhList.next())
											{
												if (kbhList.getString("kenhId").equals(obj.getKbhId()))
												{
												%>
													<option value="<%=kbhList.getString("kenhId")%>" selected="selected"><%=kbhList.getString("kenh")%></option>
												<% } else { %>
													<option value="<%=kbhList.getString("kenhId")%>"><%=kbhList.getString("kenh")%></option>
										<% } } kbhList.close(); } %>
									</select>
		                        </TD>     
		                  </TR> 
		                  
		                    <TR style="display: none;" >
                          		<TD class="plainlabel" width="120px" >Chiết khấu </TD>   
		                        <TD class="plainlabel" >
		                        	<input type="text" name="chietkhau" value="<%= obj.getChietKhau() %>"  maxlength="10" onchange="thaydoichietkhau(this);" > 
		                        </TD>          
		                  </TR> 
		                  
                          
                          <TR>
                          		     
		                  </TR> 
		                 
		                  <TR>
                          		<TD class="plainlabel" >Trạng thái </TD>   
		                        <TD class="plainlabel" colspan="3" >
		                            <% if(obj.getTrangthai().equals("1")) { %>
		                            	<input type="checkbox" name = "trangthai" value="1" checked="checked" ><i> Hoạt động</i>
		                            <% } else { %>
		                            	<input type="checkbox" name = "trangthai" value="1" ><i> Hoạt động</i>
		                            <% } %>
		                        </TD>                
		                  </TR>
		                  
		                  
		                  <TR>
		                  		<td colspan="4">
		                  		
		                  			<ul class="tabs">
		                  				<% if( !obj.getView().equals("NPP") ) { %>
		                  					<li><a href="#" <%= obj.getLoai().equals("0")?"style=' display: none;' ":"" %>>Chi nhánh / NPP</a></li>
		                  				<% } %>
			                  			<li><a href="#">Chọn sản phẩm</a></li>
			                  			
			                  			
			                  		</ul>
		                  			
		                  			<div class="panes">
		                  			
			                  			<% if(  !obj.getView().equals("NPP")  || obj.getLoai().equals("0") ) { %>							
										<div  > 
											
											<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"  <%= obj.getLoai().equals("0")?"style=' display: none;' ":"" %>>
												<TR class="tbheader">
													<TH width="15%">Mã Chi nhánh / NPP </TH>
													<TH width="25%">Họ tên</TH>								
													<TH width="10%">Điện thoại</TH>
													<TH width="30%">Địa chỉ </TH>
													<TH width="10%" style="display: none">Chiết khấu</TH>
													<TH width="10%">Chọn
														<input type="checkbox" id="checkAllKh" onclick="checkedAll_KhachHang();">
													</TH>
												</TR>
												
												<% if (nppRs != null)
												{
													int rowCount = 0;
													while (nppRs.next())
													{ %>
											
											<% if(rowCount % 2 == 0) { %>			
													<TR class="tbdarkrow">
											<% } else { %>
													<TR class="tblightrow">
											<% } %>
													
														<TD><%= nppRs.getString("ma") %></TD>
														<TD><%= nppRs.getString("ten") %></TD>
														<TD><%= nppRs.getString("dienthoai") %></TD>
														<TD><%= nppRs.getString("diachi") %></TD>
														 <TD style="display: none"  >
														 		<input type="hidden" name="nppIdck" value="<%= nppRs.getString("pk_seq") %>">
		                    							    	<input style=" text-align: right;" type="text" name="chietkhauNPP" class="chietkhauNPP" value="<%=nppRs.getDouble("chietkhaunpp")%>"  > 
		                    								</TD>       
														<% if( obj.getNppId().indexOf(nppRs.getString("pk_seq")) >= 0 ) { %> 
															<TD align="center">
																<input type="checkbox" name="nppId" value="<%= nppRs.getString("pk_seq") %>"  checked="checked" >
															</TD>
														<%  } else { %> 
															<TD align="center" > 
																<input type="checkbox" name="nppId" value="<%= nppRs.getString("pk_seq") %>"   >
															</TD>	
														<% } %>
														
													</TR>	
														
												<% rowCount++;  } } %>
												
												
											</TABLE>
											
										</div>
										<%} %>
										
										<div>
			                  			
				                  			<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
												<TR class="tbheader">
													<TH width="10%">Mã sản phẩm </TH>
													<TH width="22%">Tên sản phẩm</TH>								
													<TH width="8%">Giá bán</TH>
													<TH width="8%">Chiết khấu</TH>
													<TH width="8%">Đơn vị</TH>
													<TH width="10%">Chọn bán 
														<input type="checkbox" id="chekAllSp" onclick="checkedAll();">
													</TH>
												</TR>
												
												<% for(int i = 0; i < spList.size(); i++ ) { 
													IBangGia_Sp sanpham = spList.get(i);	%>
													
													<TR>
														<TD>
															<input type="hidden" style="width: 100%" name="spId" value="<%= sanpham.getIdsp() %>" >
															<input type="text" style="width: 100%" name="spMa" value="<%= sanpham.getMasp() %>" readonly="readonly" >
														</TD>
														<TD>
															<input type="text" style="width: 100%" name="spTen" value="<%= sanpham.getTensp() %>" readonly="readonly" >
														</TD>
														<TD>
															<input type="text" style="width: 100%; text-align: right;" name="giaban" value="<%= sanpham.getGiaban() %>"   >
														</TD>
														
														 <TD  >
														 		
		                    							    	<input style=" width: 100%; text-align: right;" type="text" name="spchietkhauNPP" class="spchietkhauNPP" value="<%=sanpham.getSpchietkhau() %>"  > 
		                    								</TD>
														
														<TD>
															<input type="text" style="width: 100%; text-align: center;" name="donvi" value="<%= sanpham.getDonvi() %>" readonly="readonly" >
														</TD>
														<TD align="center">
															<% if(sanpham.getChonban().equals("1")) { %>
																<input type="checkbox" name="chonban" value="<%= sanpham.getIdsp() %>"  checked="checked" >
															<% } else { %>
																<input type="checkbox" name="chonban" value="<%= sanpham.getIdsp() %>"  >
															<% } %>
														</TD>
													</TR>
													
												<% } %>
												
											</TABLE>
										</div>
										
									</div>
		                  		</td>
		                  </TR> 
		                  
						</TABLE>
							
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>

</BODY>
</HTML>

<%
	if(nppRs != null)
		nppRs.close();
	spList.clear();
	
	obj.DbClose();
%>

<%}%>
