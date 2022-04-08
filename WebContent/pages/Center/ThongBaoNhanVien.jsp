<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="javax.naming.spi.DirStateFactory.Result"%>
<%@page import="geso.dms.center.db.sql.dbutils"%>
<%@page import="geso.dms.center.beans.thongbao.*"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% IThongbaoList thongbao = (IThongbaoList)session.getAttribute("obj"); %>
<% ResultSet tbList = thongbao.getThongbaoList(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% thongbao.setNextSplittings();
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/OPV/index.jsp");
}else{
int[] quyen = new  int[6];
quyen = util.Getquyen("ThongbaoSvl","&viewMode=" + thongbao.getViewMode() + "&loaivanban=" + thongbao.getLoaithongbao() + "&task=" + thongbao.getTask(), userId);

%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("ThongbaoSvl","&viewMode=1&loaivanban=&task=1",nnId);	
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
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
<script type="text/javascript">
	function clearform()
	{
	    document.forms["ctForm"].maso.value = "";
	    document.forms["ctForm"].ngaybatdau.value = "";
	    document.forms["ctForm"].ngayketthuc.value = "";
	    document.forms["ctForm"].tieude.value = "";
	    document.forms["ctForm"].trangthai.value = "";
	    document.forms["ctForm"].noidung.value = "";
	    Submitform();
	}
	function Submitform()
	{
		document.forms["ctForm"].action.value = "nhanvien";
		document.forms["ctForm"].submit();
	}
	function TaoMoi()
	{
		document.forms["ctForm"].action.value = "new";
		document.forms["ctForm"].submit();
	}
	function thongbao()
	 {
		 var tt = document.forms["ckParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ckParkForm"].msg.value);
	 }
</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body >
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="currentPage" value="<%= thongbao.getCurrentPage() %>" >
<input type="hidden" name="action">
<input type="hidden" name="task" value="1">
<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top'>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		<%= " " + url %> </TD>
							   <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng bạn",nnId) %> &nbsp;<%=userTen%>  </TD> 
						    </tr>
   						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100%" align="left">
								<FIELDSET >
									<LEGEND class="legendtitle"><%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId) %>  </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
									   <TR>
										  <TD class="plainlabel" width="120px" ><%=ChuyenNgu.get("Ngày bắt đầu",nnId) %></TD>
										  <TD class="plainlabel" width="220px" ><input type="text" class="days" id="ngaybatdau" name="ngaybatdau" value="<%= thongbao.getNgaybatdau()%>" onchange="Submitform()" ></TD>
										  <TD class="plainlabel" width="120px" ><%=ChuyenNgu.get("Ngày kết thúc",nnId) %></TD>
										  <TD class="plainlabel"  ><input type="text" class="days" id="ngayketthuc" name="ngayketthuc" value="<%= thongbao.getNgayketthuc()%>" onchange="Submitform()" ></TD>
									   </TR>
									   <TR>
										  <TD class="plainlabel" ><%=ChuyenNgu.get("Tiêu đề",nnId) %></TD>
										  <TD class="plainlabel" ><input type="text" id="tieude"  name="tieude" size="20" value="<%= thongbao.getTieude()%>" onchange="Submitform()" ></TD>
										  <TD class="plainlabel" ><%=ChuyenNgu.get("Nội dung",nnId) %></TD>
										  <TD class="plainlabel" ><input type="text" id="noidung"  name="noidung" size="20" value="<%= thongbao.getNoidung() %>" onchange="Submitform()" ></TD>
									   </TR>

									   <TR>
										  <TD class="plainlabel" ><%=ChuyenNgu.get("Mã số",nnId) %></TD>
										  <TD class="plainlabel" ><input type="text" id="maso"  name="maso" size="20" value="<%= thongbao.getId()%>" onchange="Submitform()" ></TD>
										  
									   
										<TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId) %></TD>
										<TD class="plainlabel">
											<select name="trangthai" name="trangthai" onchange="Submitform()" >
											  	<% if (thongbao.getTrangthai().equals("1")){ %>
											  		<option value=""></option>
											    	<option value="1" selected><%=ChuyenNgu.get("Chưa xem",nnId) %></option>
											    	<option value="2" ><%=ChuyenNgu.get("Đã xem",nnId) %></option>
												
												<%	}else if(thongbao.getTrangthai().equals("2")){%>
												    <option value=""></option>
												    <option value="1" ><%=ChuyenNgu.get("Chưa xem",nnId) %></option>
											    	<option value="2" selected><%=ChuyenNgu.get("Đã xem",nnId) %> </option>	
												   
												<%}else{ %>
													<option value=""></option>
												    <option value="1" ><%=ChuyenNgu.get("Chưa xem",nnId) %></option>
											    	<option value="2"><%=ChuyenNgu.get("Đã xem",nnId) %> </option>	
												<%} %>
									    	</select>
									    </TD>
								</TR>
										<TR>
                                             <TD class="plainlabel" colspan='4'> 
                                             <a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	
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
							<LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Thông báo",nnId) %>&nbsp;&nbsp;&nbsp;
							
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH align="center" width="10%" ><%=ChuyenNgu.get("Mã",nnId) %></TH>
											  	<TH align="center" width="30%" ><%=ChuyenNgu.get("Tiêu đề",nnId) %></TH>
											  	<TH align="center" width="10%" ><%=ChuyenNgu.get("Loại",nnId) %></TH>
											  	<TH align="center" width="10%" ><%=ChuyenNgu.get("Người gửi",nnId) %></TH>
							                    <TH align="center" width="10%" ><%=ChuyenNgu.get("Trạng thái",nnId) %></TH>
							                    <TH align="center" width="10%" > <%=ChuyenNgu.get("Ngày bắt đầu",nnId) %> </TH>
							                    <TH align="center" width="10%" ><%=ChuyenNgu.get("Ngày kết thúc",nnId) %></TH>
							                    <TH align="center" width="10%"  ><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
										  	</TR>									
												<%
												if(tbList != null)
												{
													try
													{
														int m = 0;
														String lightrow = "tblightrow";
														String darkrow = "tbdarkrow";
														while (tbList.next())
														{
															if (m % 2 != 0) 
															{%>						
																<TR class= <%=lightrow%> >
															<%} 
															else 
															{%>
																<TR class= <%= darkrow%> >
															<%}%>
															<TD align="center"><%= tbList.getString("PK_SEQ") %></TD>
															<%
																String filename=tbList.getString("filename");
																if(filename.equals("0")){
																%>
																<TD><%= tbList.getString("TIEUDE") %></TD> 
															<%}else { %>
																<TD ><%= tbList.getString("TIEUDE") %><img src="../images/paperclip.png"></img></TD> 
															<%} %>
															<TD> <%= tbList.getString("loaithongbao").equals("5") ? "Thông báo" : "Văn bản" %> </TD>  									                                    
													     <TD><%= tbList.getString("nguoitao") %></TD> 
													     	<% if(tbList.getString("trangthai").trim().equals("1")){%>
				                                                 <TD align="center" style="color: red;" ><%=ChuyenNgu.get("Chưa xem",nnId) %></TD>
				                                                 <%}else{ %>
				                                                  <TD align="center"><%=ChuyenNgu.get("Đã xem",nnId) %></TD>
				                                                 <%} %>	
										                    <TD align="center"><%= tbList.getString("ngaybatdau") %></TD>
								         					<TD align="center"><%= tbList.getString("ngayketthuc") %></TD>
															<TD align="center" >
															<TABLE border = "0" cellpadding="0" cellspacing="0">
																<TR>
																	<TD>
																	<%if( quyen[3]!=0 || thongbao.getViewMode().equals("0") ){ %>
																		<%if(quyen[Utility.XEM]!=0){ %>
																		
																  		<A href="../../ThongbaoUpdateSvl?userId=<%= userId %>&id=<%= tbList.getString("PK_SEQ") %>&task=capnhatnv&viewMode=0">
			                                               				<img src="../images/Display20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
			                                               				<%} %>
																	<%} %>
																	<%if( ( quyen[Utility.CHOT]!=0 || tbList.getString("nguoitaoTB").equals(userId.trim()) ) ){ %>
   	                                	                    <A href = "../../ThongbaoUpdateSvl?task=copy&id=<%=tbList.getString("PK_SEQ")%>&viewMode=<%= thongbao.getViewMode()  %>&loaivanban=<%= thongbao.getLoaithongbao() %>" onclick="return confirm('Copy thông báo này?')"><img src="../images/copy20.png" alt="Copy"  width="20" height="20" title="Copy thông báo" border = 0></A>
															<%} %>
																	</TD> 
																	<TD>&nbsp;</TD>
																</TR>
																</TABLE>
															</TD>
														</TR>
														<% m++; 
			                                                }
														tbList.close();
														}
														catch(SQLException ex){
															ex.printStackTrace();
															System.out.print("Loi roi...."); }
														}
														%>
												
									<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	 <% thongbao.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= thongbao.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= thongbao.getNxtApprSplitting() %>" >
											 	<%if(thongbao.getNxtApprSplitting() >1) {%>
													<!-- <img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'viewnv')"> &nbsp; -->
												<%}else {%>
													<!-- <img alt="Trang Dau" src="../images/first.gif" > &nbsp; -->
													<%} %>
												<% if(thongbao.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'viewnv')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = thongbao.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + thongbao.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == thongbao.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"></a>
												<%}else{ %>
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'viewnv')"></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(thongbao.getNxtApprSplitting() < thongbao.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'viewnv')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(thongbao.getNxtApprSplitting() == thongbao.getTheLastSplitting()) {%>
											   		<!-- <img alt="Trang Cuoi" src="../images/last.gif" > &nbsp; -->
										   		<%}else{ %>
										   			<!-- <img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'viewnv')"> &nbsp; -->
										   		<%} %>
						</TD>
					 </tr>
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
<%
thongbao.DBClose();
%>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
<%} %>
</html>