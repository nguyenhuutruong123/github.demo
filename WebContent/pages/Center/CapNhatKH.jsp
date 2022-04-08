<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.Router.imp.Router"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.beans.Router.IDRouter;" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	String loi=(String)session.getAttribute("loi");
	String tungay=(String)session.getAttribute("tungay");
	String denngay=(String)session.getAttribute("denngay");
	Utility util = (Utility) session.getAttribute("util");
	IDRouter obj = (IDRouter)session.getAttribute("obj");
	ResultSet vung = (ResultSet)obj.getvung();
	ResultSet khuvuc = (ResultSet)obj.getkhuvuc();
	ResultSet npp = (ResultSet)obj.getnpp();
	ResultSet ddkd = (ResultSet)obj.getddkd();
	ResultSet tuyen = (ResultSet)obj.getTuyen();
	ResultSet danhsach = (ResultSet)obj.getdanhsach();
	ResultSet capnhatkhList = (ResultSet)obj.getCapnhatKHRs();
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
	
	<%
	int[] quyen = new  int[6];
	quyen = util.Getquyen("CapnhatKH","", userId);
	
	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="javascript" type="text/javascript">

function khuvucChanging(){
	
	document.forms['rpForm'].action.value="khuvucChanged";
}

function submitform()
{
	document.forms['rpForm'].action.value="thuchien";
	document.forms['rpForm'].submit();
}
function exportToExcel()
{
	//alter('Mời bạn chọn nhà phân phối!');
	if(document.forms['rpForm'].nppId.value==""){
		document.forms['rpForm'].nppId.focus();
		alert('Vui lòng chọn nhà phân phối!');
		
		return;
	}
	
	document.forms['rpForm'].action.value="export";
	document.forms['rpForm'].submit();
}

function exportToExcel2()
{	
	
	document.forms['rpForm'].action.value='export2';	
	document.forms['rpForm'].submit();
}

function newform()
{
	document.forms['rpForm'].action.value= 'new';
	document.forms['rpForm'].submit();
}

function submitCBO(){
	document.forms['rpForm'].action.value="";
	document.forms['rpForm'].submit();
}
</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2(); 
    	});
    </script>	



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

<form name="rpForm" method="post" action="../../CapnhatKH">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="action" value='1'>

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">	
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<table width="100%" cellpadding="0" cellspacing="1">		
				<tr>
					<td width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						  <td align="left" colspan="2" class="tbnavigation">&nbsp;Báo cáo quản tri &gt; Báo cáo khác &gt; Cập nhật khách hàng</TD>
   
						   <td colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					
 					</td>
				</tr>
				<tr>
					<td align="left" colspan="4" class="legendtitle">
						<fieldset>
						<legend class="legendtitle">Báo lỗi nhập liệu </legend>
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=loi%></textarea>
						</fieldset>
				   </td>
				</tr>	
				<tr>
					<td>
					<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td width="100%" align="center" >
							<fieldset>
							<legend class="legendtitle">Tiêu chí xuất báo cáo</legend>
							<table width="100%" cellpadding="6" cellspacing="0">
								<!-- <TR> -->
								<TR>
									<TD class="plainlabel">Vùng/Miền</TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="submitCBO();" style ="width:200px">
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
								</TR>
								<tr>								
									<td width="19%" class="plainlabel" >Khu vực </td>
									<td class="plainlabel" >
										<table cellpadding="0" cellspacing="0">
										<tr>
											<td>
											<select name="khuvucId" onchange="submitCBO();" style ="width:200px">
			                                <option value ="" > </option>  
			                               <%
			                               while(khuvuc.next())
			                               {
			                               		if(khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) 
			                               		{%>
			                            			<option value ="<%=khuvuc.getString("pk_seq") %>" selected> <%=khuvuc.getString("ten") %></option>  
			                            	  <%} else { %>
			                            	  		<option value ="<%=khuvuc.getString("pk_seq") %>"> <%=khuvuc.getString("ten") %></option>
			                            <%}}%>                             
			                              	</select>		   										
			                              	</td>
		                                </tr>
										</table>
									</TD>								
								</tr>															
								
								<tr>
								  	<td width="19%" class="plainlabel" >Nhà phân phối</td>
								  	<td class="plainlabel" >
										<table cellpadding="0" cellspacing="0">
										<tr>
											<td>
											<select name="nppId" onchange="submitCBO();" style="width: 330px" >
                                 			<option value =""> </option>  
                               				<%
                               				if(npp != null)
                               				while(npp.next())
                               				{
                               					if(npp.getString("pk_seq").equals(obj.getnppId())) 
                               					{%>
                            						<option value ="<%=npp.getString("pk_seq") %>" selected> <%=npp.getString("ten") %></option>  
                            	  			<%} else { %>
                            	  					<option value ="<%=npp.getString("pk_seq") %>"> <%=npp.getString("ten") %></option>
                              				<%}} %>                             
                              				</select>		   										
                              				</td>
                                    	</tr>
										</table>									
									</td>								
								</tr>
								
							    <tr>
									<td width="19%" class="plainlabel" >Nhân viên bán hàng</td>
								  	<td class="plainlabel" >
										<table cellpadding="0" cellspacing="0">
										<tr>
											<td>
											<select name="ddkdId" style ="width:200px">
                                	 		<option value =""> </option>  
                               				<%
                               				while(ddkd.next())
                               				{
                               					if(ddkd.getString("pk_seq").equals(obj.getddkdId())) 
                               					{%>
                            						<option value ="<%=ddkd.getString("pk_seq") %>" selected> <%=ddkd.getString("ten") %></option>  
                            	  			<%} else { %>
                            	  					<option value ="<%=ddkd.getString("pk_seq") %>"> <%=ddkd.getString("ten") %></option>
                              				<%}} %>                             
                              				</select>		   										
                              				</td>
                                    	</tr>
										</table>									
									</td>							
								</tr>								
							    <!-- <TR> -->
							    <tr>
									<td width="19%" class="plainlabel" >Tuyến bán hàng</td>
								  	<td class="plainlabel" >
										<table cellpadding="0" cellspacing="0">
										<tr>
											<td>
											<select name="tuyenId"  style ="width:200px">
                                			<option value =""> </option>  
                               				<%
                               				while(tuyen.next())
                              			 	{
                               					if(tuyen.getString("ngaylamviec").equals(obj.gettuyenId())) 
                               					{%>
                            						<option value ="<%=tuyen.getString("ngaylamviec") %>" selected> <%=tuyen.getString("ngaylamviec") %></option>  
                            	  			<%} else { %>
                            	  					<option value ="<%=tuyen.getString("ngaylamviec") %>"> <%=tuyen.getString("ngaylamviec") %></option>
                              				<%}} %>             
                               				</select>		   										
                               				</td>
                               			</tr>
                               			</table>
                               	 	</td>
                               </tr>
                               
                               <tr>
							   		<td class="plainlabel" colspan="4"><a class="button"
									href="javascript:exportToExcel()"> <img style="top: -4px;"
									src="../images/button.png" alt=""> Xuất ra file excel
									</a>
									</td>
							   </tr>
                           </table>                               
                           </fieldset>
						   </td>
                       	</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td>
					
					<TABLE width="100%" cellpadding="0" cellspacing="0">			
					<TR>
						<TD width="100%">
						<FIELDSET>
						<LEGEND class="legendtitle">&nbsp;Cập nhật khách hàng &nbsp;&nbsp;
							<%-- <%if(quyen[0]!=0){ %> --%>
							<a class="button3" href="javascript:newform()"> <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
				<%-- <%} %> --%>
						</LEGEND>
	
						<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">									
									<TH width="13%">Diễn giải </TH>									
									<TH width="8%">Trạng thái </TH>
									<TH width="6%">Ngày tạo </TH>
									<TH width="10%">Người tạo </TH>
									<TH width="6%">Ngày sửa</TH>
									<TH width="10%">Người sửa </TH>
									<TH width="6%" align="center">&nbsp;Tác vụ</TH>
								</TR>
							
								<% 
								if(capnhatkhList != null)
								{
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (capnhatkhList.next())
									{
										if (m % 2 != 0) {%>														
											<TR class= <%=lightrow%>>
										<%} else {%>
											<TR class= <%= darkrow%>>
										<%}%>
																				
										<TD align="center"><%= capnhatkhList.getString("diengiai") %></TD>                                   
										<TD align="center">
											<%
											String trangthai = capnhatkhList.getString("trangthai");
											if (trangthai.equals("0")){ %>
											<span> Còn chỉnh sửa</span>
											<%}else{ %>
											<span><b> Đã chốt</b></span>
											<%}%>
										</TD>										
										<TD align="center"><%= capnhatkhList.getString("ngaytao") %></TD>
										<TD align="center"><%= capnhatkhList.getString("nguoitao") %></TD>
										<TD align="center"><%= capnhatkhList.getString("ngaysua") %></TD>
										<TD align="center"><%= capnhatkhList.getString("nguoisua") %></TD>
										<TD align="center">
											<TABLE border = 0 cellpadding="0" cellspacing="0">
											<TR>
												<TD>
												<%if(trangthai.equals("0")){ %>
													<A href = "../../CapnhatKH?userId=<%=userId%>&chot=<%=capnhatkhList.getString("pk_seq")%>"><img src="../images/Chot.png" alt="Chot" title="Chốt" width="20" height="20" longdesc="Chot" border = 0></A>
													<A href = "../../CapnhatKHNewSvl?userId=<%=userId%>&update=<%=capnhatkhList.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<A href = "../../CapnhatKH?userId=<%=userId%>&delete=<%=capnhatkhList.getString("pk_seq")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border = 0 onclick="if(!confirm('Bạn có muốn xóa cập nhật khách hàng này ?')) return false;"></A>
												<%}else{ %>												
													<A href="../../CapnhatKH?userId=<%=userId%>&display=<%=capnhatkhList.getString("pk_seq")%>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
													<A href = "../../CapnhatKH?userId=<%=userId%>&delete=<%=capnhatkhList.getString("pk_seq")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border = 0 onclick="if(!confirm('Bạn có muốn xóa cập nhật khách hàng này ?')) return false;"></A>
												<%} %>
												</TD>													
											</TR>
											</TABLE>
												</TD>
											</TR>
								<%m++; }}%>
							</TABLE>
							</TD>
						</TR>
						<TR class="tbfooter"> 
							<TD align="center" valign="middle" colspan="13" class="tbfooter" style="height:25px;"></TD>						
						</TR>
					</TABLE>

					</FIELDSET>
					</TD>
				</TR>			
		</TABLE>
					
					</td>
				</tr>			
			</table>
		</td>
	</tr>
</table>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<%
	try
	{
		if(!(danhsach == null)){ danhsach.close(); danhsach = null; }
		if(ddkd != null){ ddkd.close(); ddkd = null; }
		if(npp != null){ npp.close(); npp = null; }
		if(tuyen != null){ tuyen.close(); tuyen = null; }
		if(khuvuc!=null){ khuvuc.close(); khuvuc = null; }
		if(vung!=null){ vung.close(); vung = null; }
		if(capnhatkhList!=null){ capnhatkhList.close(); capnhatkhList = null; }
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}catch(java.sql.SQLException e){}
}
%>