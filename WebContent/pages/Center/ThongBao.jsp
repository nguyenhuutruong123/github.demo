<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="javax.naming.spi.DirStateFactory.Result"%>
<%@page import="geso.dms.center.db.sql.dbutils"%>
<%@page import="geso.dms.center.beans.thongbao.*"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% IThongbaoList thongbao = (IThongbaoList)session.getAttribute("obj"); %>
<% ResultSet tbList = thongbao.getThongbaoList();%>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
String url="";
url = util.getUrl("ThongbaoSvl","&viewMode=" + thongbao.getViewMode() + "&loaivanban=" + thongbao.getLoaithongbao() + "&task=" + thongbao.getTask());
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/OPV/index.jsp");
}else{
	
	int[] quyen = new  int[6];
	quyen = util.Getquyen("ThongbaoSvl","&viewMode=" + thongbao.getViewMode() + "&loaivanban=" + thongbao.getLoaithongbao() + "&task=" + thongbao.getTask(), userId);
	
%>
<% thongbao.setNextSplittings(); %>
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
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
	    document.forms["ctForm"].ngaybatdau.value =  "";
	    document.forms["ctForm"].ngayketthuc.value =  "";
		
	    document.forms["ctForm"].tieude.value = "";
	    document.forms["ctForm"].noidung.value = "";
	    document.forms["ctForm"].loaivanban.value = "5";
	    document.forms["ctForm"].maso.value = "";
	    document.forms["ctForm"].submit();
	}
	function Submitform()
	{
		document.forms["ctForm"].action.value = "search";
		document.forms["ctForm"].submit();
	}
	function TaoMoi()
	{
		document.forms["ctForm"].action.value = "new";
		document.forms["ctForm"].submit();
	}
	function thongbao()
	 {
		 var tt = document.forms["ctForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ctForm"].msg.value);
	 }
</script>
<script type="text/javascript">
function goiham()
{
	parent.menu.ajaxOption('<%=userId%>',  'xxx');

}

</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body onload="goiham()">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<%-- <p>User ID: <%=userId %></p>
<p>View Mod: <%=thongbao.getViewMode() %></p>
<p>Loai Thong Bao: <%= thongbao.getLoaithongbao() %></p>
<p>Task: <%= thongbao.getTask() %></p>

<p>Ten: <%=userTen %></p>
<p>Sua: <%=Utility.SUA %></p>
<p>Xoa: <%=Utility.XOA %></p>
<p>Chot: <%=Utility.CHOT %></p>
<p>Quyen sua la: <%=quyen[Utility.SUA] %></p>
<p>Quyen xoa la: <%=quyen[Utility.XOA] %></p>
<p>Quyen chot la: <%=quyen[Utility.CHOT] %></p>
 --%>



<% Csrf csdr = new Csrf(request,response,true,false,true);%>
<form name="ctForm" method="post" action = "../../ThongbaoSvl">
<input type="hidden" name="action" value="1">
<input type="hidden" name="viewMode" value="<%= thongbao.getViewMode() %>" >
<input type="hidden" name="currentPage" value="<%= thongbao.getCurrentPage() %>" >

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
							   		<%= " " + urlTB %> <%=  ( thongbao.getLoaithongbao().equals("5") || thongbao.getViewMode().equals("0") ) ? "Thông báo" : "Văn bản" %> </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn<%=ChuyenNgu.get("Tên",nnId) %> &nbsp;<%=userTen%>  </TD> 
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
										  <TD class="plainlabel" width="230px" ><input type="text" class="days" id="ngaybatdau" name="ngaybatdau" value="<%= thongbao.getNgaybatdau()%>" onchange="Submitform()" ></TD>
										  <TD class="plainlabel" width="120px" ><%=ChuyenNgu.get("Ngày kết thúc",nnId) %></TD>
										  <TD class="plainlabel" ><input type="text" class="days" id="ngayketthuc" name="ngayketthuc" value="<%= thongbao.getNgayketthuc()%>" onchange="Submitform()" ></TD>
										  
									   </TR>
									   <TR>
										  <TD class="plainlabel" ><%=ChuyenNgu.get("Tiêu đề",nnId) %></TD>
										  <TD class="plainlabel" ><input type="text" id="tieude"  name="tieude" value="<%= thongbao.getTieude()%>" onchange="Submitform()" ></TD>
										  <TD class="plainlabel" ><%=ChuyenNgu.get("Nội dung",nnId) %></TD>
										  <TD class="plainlabel" ><input type="text" id="noidung"  name="noidung" value="<%= thongbao.getNoidung() %>" onchange="Submitform()" ></TD>
									   </TR>
									   <TR>
										  <TD class="plainlabel"  ><%=ChuyenNgu.get("Mã số",nnId) %></TD>
										  <TD class="plainlabel"  ><input type="text" id="maso"  name="maso"  value="<%= thongbao.getId()%>" onchange="Submitform()" ></TD>
										  
										  <TD class="plainlabel" ><%=ChuyenNgu.get("Loại văn bản",nnId) %></TD>
										  <TD  class="plainlabel"  >
										  	 <select name="loaivanban" onchange="Submitform();" >
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
										  	 	<% if(thongbao.getLoaithongbao().equals("5")) { %>
										  	 		<option value="5" selected="selected" ><%=ChuyenNgu.get("Thông báo",nnId) %></option>
										  	 	<% } else { %> 
										  	 		<option value="5" ><%=ChuyenNgu.get("Thông báo",nnId) %></option>
										  	 	<% } %>
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
							<LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Văn bản",nnId) %>&nbsp;&nbsp;&nbsp;
							<%if(quyen[Utility.THEM]!=0 || thongbao.getViewMode().equals("0") ){ %>
							
							<%if(quyen[Utility.SUA]!=0){ %>
							<a class="button3" href="javascript:TaoMoi()">
    							<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> </a> 
    						<%} %>                           
							<%} %>
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH align="center" width="10%"><%=ChuyenNgu.get("Mã",nnId) %></TH>
											  	<TH align="center" width="16%"><%=ChuyenNgu.get("Tiêu đề",nnId) %></TH>
											  	<TH align="center" width="10%"><%=ChuyenNgu.get("Ngày bắt đầu",nnId) %></TH>
											  	<TH align="center" width="10%"><%=ChuyenNgu.get("Ngày kết thúc",nnId) %></TH>
											  	<TH align="center" width="10%"><%=ChuyenNgu.get("File đính kèm",nnId) %></TH>
							                    <TH align="center" width="7%"><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
							                    <TH align="center" width="10%"><%=ChuyenNgu.get("Người tạo",nnId) %></TH>
							                    <TH align="center" width="7%"><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
							                    <TH align="center" width="10%"><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
							                    <TH align="center" width="10%" ><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
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
															<TD align="left"><%= tbList.getString("TIEUDE") %></TD> 
															<TD align="center"><%= tbList.getString("NGAYBATDAU") %></TD> 
															<TD align="center"><%= tbList.getString("NGAYKETTHUC") %></TD>
															<%
																String filename = tbList.getString("filename");
																if(filename.trim().length() <= 0)
																{
																	filename="Không có kèm file";
																}
															%>
															<TD align="left"><%= filename %></TD>     									                                    
													     	<TD align="center"><%= tbList.getString("NGAYTAO") %></TD>	
										                    <TD align="center"><%= tbList.getString("TENNV") %></TD>
								         					<TD align="center"><%= tbList.getString("NGAYSUA") %></TD>
															<TD align="center"><%= tbList.getString("TENNVS") %></TD>
															<TD align="center" >
															<%-- <TABLE border = "0" cellpadding="0" cellspacing="0">
																<TR>
																	<TD>
																  		<A href="../../ThongbaoUpdateSvl?task=capnhat&id=<%= tbList.getString("PK_SEQ") %>">
			                                               				<img src="../images/Display20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
																	</TD> 
																	<TD>&nbsp;</TD>
																	<TD>
																  		<A href="../../ThongbaoSvl?task=xoa&id=<%= tbList.getString("PK_SEQ") %>">
			                                                			<img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="return confirm('Bạn có muốn xóa công ty này ?')"></A>
			                                                		</TD>
																</TR>
																</TABLE> --%>
																<TABLE border = 0 cellpadding="0" cellspacing="0">
                                	                    <TR><TD>
                                	                    	<%-- <p> la so nay: <%=tbList.getString("trangthai")%></p> --%>
                                	                    	<%
                                	                    		if ( tbList.getString("trangthai").equals("0")  ) {%>
                                	                    		
                                	                    		<%if(quyen[Utility.SUA]!=0 || tbList.getString("nguoitaoTB").equals(userId.trim()) ){ %>
	                                    	                    <A href="../../ThongbaoUpdateSvl?task=capnhat&id=<%= tbList.getString("PK_SEQ") %>&viewMode=<%= thongbao.getViewMode()  %>&loaivanban=<%= thongbao.getLoaithongbao() %>"><img src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0 "></A>
    	                                	                   	<%} %>
    	                                	                   	<%if(quyen[Utility.XOA]!=0 || tbList.getString("nguoitaoTB").equals(userId.trim())){ %>
    	                                	                   <A href="../../ThongbaoSvl?task=xoa&id=<%= tbList.getString("PK_SEQ") %>&loaivanban=<%= thongbao.getLoaithongbao() %>&viewMode=<%= thongbao.getViewMode()  %>" onclick="return confirm('Bạn muốn xóa thông báo')"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" longdesc="Xóa" border = 0 "></A> 
    	                                	                   <%} %>
    	                                	                   <%if( ( quyen[Utility.CHOT]!=0 || tbList.getString("nguoitaoTB").equals(userId.trim()) ) && tbList.getString("tinhtrang").equals("0") ){ %>
    	                                	                    <A href = "../../ThongbaoUpdateSvl?task=chot&id=<%=tbList.getString("PK_SEQ")%>&viewMode=<%= thongbao.getViewMode()  %>&loaivanban=<%= thongbao.getLoaithongbao() %>" onclick="return confirm('Bạn muốn gửi thông báo?')"><img src="../images/wmove2doc.png" alt="Chot"  width="20" height="20" title="Gửi thông báo" border = 0></A>
																<%} %>
															<%} else { %>
															
																<%if( quyen[Utility.SUA]!=0 || tbList.getString("nguoitaoTB").equals(userId.trim()) ){ %>
                                	                    		<A href="../../ThongbaoUpdateSvl?task=view&id=<%= tbList.getString("PK_SEQ") %>&viewMode=<%= thongbao.getViewMode()  %>&loaivanban=<%= thongbao.getLoaithongbao() %>">
			                                               				<img src="../images/Display20.png" alt="Cap nhat" title="Hiển thị" width="20" height="20" longdesc="Cap nhat" border = 0></A>
			                                               		<%} %>
			                                               		<%if(quyen[Utility.XOA]!=0 || tbList.getString("nguoitaoTB").equals(userId.trim()) ){ %>
			                                               				  <A href="../../ThongbaoSvl?task=xoa&id=<%= tbList.getString("PK_SEQ") %>&viewMode=<%= thongbao.getViewMode()  %>&loaivanban=<%= thongbao.getLoaithongbao() %>" onclick="return confirm('Bạn muốn xóa thông báo đã gửi')"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" longdesc="Xóa" border = 0 "></A> 
															<%} }%>
															<%if( ( quyen[Utility.CHOT]!=0 || tbList.getString("nguoitaoTB").equals(userId.trim()) ) ){ %>
   	                                	                    <A href = "../../ThongbaoUpdateSvl?task=copy&id=<%=tbList.getString("PK_SEQ")%>&viewMode=<%= thongbao.getViewMode()  %>&loaivanban=<%= thongbao.getLoaithongbao() %>" onclick="return confirm('Copy thông báo này?')"><img src="../images/copy20.png" alt="Copy"  width="20" height="20" title="Copy thông báo" border = 0></A>
															<%} %>                                    	                   
                                        	            </TD>
                                                   
                                            		 </TABLE>
															</TD>
														</TR>
														<% m++; 
			                                                }
														tbList.close();
														}
														catch(SQLException ex){ System.out.print("Loi roi...."); }
														}
														%>
												
									<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	 <% thongbao.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= thongbao.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= thongbao.getNxtApprSplitting() %>" >
											 	<%if(thongbao.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(thongbao.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
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
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=thongbao.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(thongbao.getNxtApprSplitting() < thongbao.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(thongbao.getNxtApprSplitting() == thongbao.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
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