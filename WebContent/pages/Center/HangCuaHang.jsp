<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.hangcuahang.IHangcuahang" %>
<%@ page  import = "geso.dms.center.beans.hangcuahang.IHangcuahangList" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page import = "java.text.DecimalFormat" %>
<%@ page import = "java.text.NumberFormat" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{int[] quyen = new  int[6];
	quyen = util.Getquyen("HangcuahangSvl", "",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);	
	System.out.println(quyen[4]); %>
	

<% IHangcuahangList obj = (IHangcuahangList)session.getAttribute("obj");
System.out.println("thong bao :"+obj.getMsg());
%>
<% ResultSet hchlist = (ResultSet) obj.getHchList(); %>
<% NumberFormat formatter = new DecimalFormat("##,###,###"); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("HangcuahangSvl","",nnId);	
	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<style type="text/css">
.phanbo {
	-moz-box-shadow:inset 0px 1px 0px 0px #ffffff;
	-webkit-box-shadow:inset 0px 1px 0px 0px #ffffff;
	box-shadow:inset 0px 1px 0px 0px #ffffff;
	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #ededed), color-stop(1, #dfdfdf) );
	background:-moz-linear-gradient( center top, #ededed 5%, #dfdfdf 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ededed', endColorstr='#dfdfdf');
	background-color:#ededed;
	-moz-border-radius:6px;
	-webkit-border-radius:6px;
	border-radius:6px;
	border:1px solid #dcdcdc;
	display:inline-block;
	color:#777777;
	font-family:arial;
	font-size:15px;
	font-weight:bold;
	padding:6px 24px;
	text-decoration:none;
	text-shadow:1px 1px 0px #ffffff;
}.phanbo:hover {
	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #dfdfdf), color-stop(1, #ededed) );
	background:-moz-linear-gradient( center top, #dfdfdf 5%, #ededed 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#dfdfdf', endColorstr='#ededed');
	background-color:#dfdfdf;
}.phanbo:active {
	position:relative;
	top:1px;
}
</style>

<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
    document.hchForm.HangCuaHang.value = "";
    document.hchForm.DienGiai.value = "";      
    document.hchForm.TrangThai.selectedIndex = 2;
    submitform();
}

function submitform()
{
	document.forms['hchForm'].action.value= 'search';
	document.forms['hchForm'].submit();
}

function newform()
{
	document.forms['hchForm'].action.value= 'new';
	document.forms['hchForm'].submit();
}

function updateHCH()
{
	document.forms['hchForm'].action.value = 'phanbo';
	document.forms['hchForm'].submit();
}

function thongbao()
{var tt = document.forms['hchForm'].msg.value;	
	if(tt.length>0)
    alert(document.forms['hchForm'].msg.value);
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

<form name="hchForm" method="post" action="../../HangcuahangSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg()%>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   	 	&nbsp;<%= " " + url %> </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD> 
						    </tr>
   						</table>
					</TD>
				</TR>
					<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
		    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
										  <TD class="plainlabel" ><%=ChuyenNgu.get("Hạng khách hàng",nnId) %> </TD>
										  <TD class="plainlabel" ><INPUT name="HangCuaHang" size="20" type="text" 
                                          					value="<%= obj.getHangcuahang()%>" onChange="submitform();"></TD>
									  </TR>
										<TR>
											<TD width="20%" class="plainlabel" ><%=ChuyenNgu.get("Diễn giải",nnId) %> </TD>
										    <TD width="80%" class="plainlabel" ><INPUT name="DienGiai" size="40" type="text" 
                                            				value="<%= obj.getDiengiai()%>" onChange="submitform();"></TD>
										</TR>
										<TR>
											<TD class="plainlabel" ><%=ChuyenNgu.get("Trạng thái",nnId) %> </TD>
											<TD class="plainlabel" >
											  <select name="TrangThai" onChange="submitform();">
											    <% if (obj.getTrangthai().equals("1")){%>
											  	<option value="1" selected><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
											  	<option value="0"><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
											  	<option value="2"> </option>
											  
											<%}else if(obj.getTrangthai().equals("0")) {%>
											 	 <option value="0" selected><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
											  	<option value="1" ><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
											 	 <option value="2" > </option>
											  
											<%}else{%>											
											  	<option value="1" ><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
											  	<option value="0" ><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
											 	<option value="2" selected> </option>											  	
											<%}%>
									          </select></TD>
										</TR>
										<TR>
										    <TD class="plainlabel" colspan=4>
										    <a class="button2" href="javascript:clearform()">
											<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	
											
											<!-- <a class="button2" href="javascript:updateHCH()">
											<img style="top: -4px;" src="../images/button.png" alt="">Update HCH</a> -->
	
										    
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
			<TABLE width="100%" cellpadding="0" cellspacing="1">
			    <TR>
					<TD align="left" >
						 <FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Hạng khách hàng &nbsp;&nbsp;&nbsp;
							<%if(quyen[0]!=0){ %>
							<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> </a>    
    	<%} %>                        
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="10%"><%=ChuyenNgu.get("Hạng khách hàng",nnId) %> </TH>
											    <TH width="13%"><%=ChuyenNgu.get("Diễn giải",nnId) %> </TH>
											    <TH width="11%"><%=ChuyenNgu.get("Từ mức",nnId) %> </TH>
											    <TH width="11%"><%=ChuyenNgu.get("Đến mức",nnId) %> </TH> 
											    <TH width="11%"><%=ChuyenNgu.get("Tính TB Tháng",nnId) %> </TH>
											    <TH width="11%"><%=ChuyenNgu.get("Trạng thái",nnId) %> </TH>											    
											    <TH width="9%"><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
											  <TH width="12%"><%=ChuyenNgu.get("Người tạo",nnId) %> </TH>										
											  <TH width="7%"><%=ChuyenNgu.get("Ngày sửa",nnId) %> </TH>
											  <TH width="12%"><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
											  <TH width="16%"><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
										  </TR>
								<% 
									IHangcuahang hch = null;
									//int size = hchlist.size();
									//System.out.println("kich thuoc "+size);
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (hchlist.next()){
// 										hch = hchlist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
										<%
// 										System.out.println("hang cua hang"+hch.getHangcuahang());
										%>
											<TR class= <%= darkrow%> >
										<%}%>
<%-- 												<TD align="left"><div align="left"><%= hch.getHangcuahang() %></div></TD>                                    --%>
<%-- 												<TD><div align="left"><%= hch.getDiengiai() %></div></TD> --%>
												
<%-- 												<TD><div align="center"><%= formatter.format(Double.parseDouble(hch.gettumuc())) %></div></TD> --%>
<%-- 												<TD><div align="center"><%= formatter.format(Double.parseDouble(hch.getdenmuc())) %></div></TD> --%>
<%-- 												<TD><div align="center"><%= hch.getThangtb() %></div></TD> --%>
												
<%-- 											  <% if (hch.getTrangthai().equals("1")){ %> --%>
<!-- 													<TD align="center">Hoạt động</TD> -->
<%-- 												<%}else{%> --%>
<!-- 													<TD align="center">Ngưng hoạt động</TD> -->
<%-- 												<%}%> --%>
<%-- 												<TD align="center"><%=hch.getNgaytao()%></TD> --%>
<%-- 												<TD align="center"><%=hch.getNguoitao()%></TD> --%>
<%-- 												<TD align="center"><%=hch.getNgaysua()%></TD> --%>
<%-- 												<TD align="center"><%=hch.getNguoisua()%></TD> --%>
<!-- 												<TD align="center"> -->
<!-- 												<TABLE border = 0 cellpadding="0" cellspacing="0"> -->
<!-- 													<TR><TD> -->
<%-- 													<%if(quyen[2]!=0){ %> --%>
<%-- 													  <A href = "../../HangcuahangUpdateSvl?userId=<%=userId%>&update=<%= hch.getId()%>"> --%>
<!--                                                <img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A> -->
<%-- 													<%} %> --%>
<!-- 													</TD> -->
<!-- 													<TD>&nbsp;</TD> -->
<!-- 													<TD> -->
<%-- 													<%if(quyen[1]!=0){ %> --%>
<%-- 													  <A href = "../../HangcuahangSvl?userId=<%=userId%>&delete=<%=hch.getId()%>"> --%>
<!--                                                 <img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa hạng khách hàng này?')) return false;"></A> -->
<%--                                                 <%} %></TD> --%>
												<TD align="left"><div align="left"><%= hchlist.getString("hang") %></div></TD>                                   
												<TD><div align="left"><%= hchlist.getString("diengiai") %></div></TD>
												
												<TD><div align="center"><%= formatter.format(Double.parseDouble(hchlist.getString("tumuc"))) %></div></TD>
												<TD><div align="center"><%= formatter.format(Double.parseDouble(hchlist.getString("denmuc"))) %></div></TD>
												<TD><div align="center"><%= hchlist.getString("thangtb") %></div></TD>
												
											  <% if (hchlist.getString("trangthai").equals("1")){ %>
													<TD align="center"><%=ChuyenNgu.get("Hoạt động",nnId) %></TD>
												<%}else{%>
													<TD align="center"><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></TD>
												<%}%>
												<TD align="center"><%=hchlist.getString("ngaytao")%></TD>
												<TD align="center"><%=hchlist.getString("nguoitao")%></TD>
												<TD align="center"><%=hchlist.getString("ngaysua")%></TD>
												<TD align="center"><%=hchlist.getString("nguoisua")%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													<%if(quyen[2]!=0){ %>
													<A href = "../../RouterSvl?task=<%=util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "HangcuahangUpdateSvl?userId="+userId+"&update="+hchlist.getString("id")) %> ">
													  <img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
													<%if(quyen[1]!=0){ %>
													<A href = "../../RouterSvl?task=<%=util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "HangcuahangSvl?userId="+userId+"&delete="+hchlist.getString("id")) %> ">
													  <img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa hạng khách hàng này?')) return false;"></A>
                                                <%} %></TD>
                                                <TD>&nbsp;</TD>
                                                <TD>
													<%if(quyen[3]!=0){ %>
													<A href = "../../RouterSvl?task=<%=util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "HangcuahangUpdateSvl?userId="+userId+"&display="+hchlist.getString("id")) %> ">
													  <img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
													<%} %>
													</TD>
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }%>
								
									<TR>	
									<TD height="" colspan="11" align="center" class="tbfooter">
									&nbsp;</TD>
								</TR>
							</TABLE>
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

</form>

</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<% 
  if(hchlist != null){ hchlist.close(); hchlist = null; }
  obj.closeDB(); obj = null;
  session.setAttribute("obj", null);
  
%>
<%}%>





