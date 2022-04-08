<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.dms.center.beans.banggia.IBangGiaList"%>
<%@ page import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%	
	IBangGiaList obj =(IBangGiaList)session.getAttribute("obj");
	ResultSet bglist = obj.getBanggiaRs();
	ResultSet dvkd = (ResultSet)obj.getDvkd(); 
	ResultSet vung = (ResultSet)obj.getVungRs();
	ResultSet diaban = (ResultSet)obj.getDiabanRs();
	ResultSet kenh = (ResultSet)obj.getKenh(); 

	int[] quyen = new  int[6];
	
	String view = obj.getView().trim().length() > 0 ? "&view="+obj.getView() : "";
	quyen = util.Getquyen("BangGiaSvl",view,userId);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	<script type="text/javascript">
		var msg = "<%=obj.getMsg()%>".trim();
		$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
			
			if(msg.length > 0 && msg !== "null") {
				alert(msg);
			}
        });	
			
	</script>
	
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	    document.forms['khtt'].bgTen.value= "";
	    document.forms['khtt'].dvkdId.value= "";
	    document.forms['khtt'].kenhId.value = "";
	    document.forms['khtt'].trangthai.value = "";
	    document.forms['khtt'].diengiai.value = "";
	    document.forms['khtt'].vungid.value = "";
		document.forms['khtt'].submit();
	}

	function submitform()
	{
		document.forms['khtt'].action.value= 'search';
		document.forms['khtt'].submit();
	}

	function newform()
	{
		document.forms['khtt'].action.value= 'new';
		document.forms['khtt'].submit();
	}
	</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="khtt" method="post" action="../../BangGiaSvl">  
<% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="view" value="<%= obj.getView() %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="loai" value="<%= obj.getLoai()%>" >


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Sản phẩm > <%= obj.getLoai().equals("0")?"Bảng giá B2B":"Bảng giá bán NPP" %>  </TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
                        </tr>
                    </TABLE>
                </TD>
            </TR>
        </TABLE>
        <TABLE width="100%" cellpadding="0" cellspacing="1">
            <TR>
                <TD>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD width="100%" align="center" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                            	 <TR>
									<TD class="plainlabel">Tên bảng giá</TD>
									<TD class="plainlabel"><INPUT name="bgTen" type="text" size="40" value='<%=obj.getMa() %>' onChange = "submitform();"/></TD>
									<TD class="plainlabel">Chi nhánh / NPP </TD>
								  	<TD class="plainlabel"><INPUT name="diengiai" type="text" size="40" value='<%=obj.getDiengiai() %>' onChange = "submitform();"/> </TD>
								</TR>
								<TR>
								    <TD class="plainlabel">Đơn vị kinh doanh </TD>
								    <TD class="plainlabel"><SELECT name="dvkdId" onChange = "submitform();">								      
								  	 		<option value =""></option>
								  	 <% try{ while(dvkd.next()){ 
								    	if(dvkd.getString("dvkdId").equals(obj.getDvkdId())){ %>
								      		<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
								      	<%}else{ %>
								     		<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
									</SELECT></TD>
									<TD class="plainlabel">Trạng thái</TD>
									<TD class="plainlabel"><select name="trangthai" onChange = "submitform();">
								  	<% if (obj.getTrangthai().equals("0")){ %>
								    	<option value=""> </option>
								    	<option value="1">Hoạt động</option>
								    	<option value="0" selected>Ngưng hoạt động</option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>
								    	<option value=""> </option>
								    	<option value="1" selected>Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
									<%}else{ %>
								    	<option value="" selected> </option>
								    	<option value="1" >Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
									<%}} %>
								    	</select></TD>	
								</TR>
								<TR>
								    <TD class="plainlabel">Vùng </TD>
								    <TD class="plainlabel"><SELECT name="vungid" onChange = "submitform();">								      
								  	 		<option value =""></option>
								  	 <% try{
								  		 if(vung!=null)
								  		 while(vung.next()){ 
								    	if(vung.getString("pk_seq").equals(obj.getVungId())){ %>
								      		<option value='<%=vung.getString("pk_seq") %>' selected><%=vung.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=vung.getString("pk_seq") %>'><%=vung.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
									</SELECT></TD>
									<TD class="plainlabel">Địa bàn </TD>
								    <TD class="plainlabel"><SELECT name="diabanid" onChange = "submitform();">								      
								  	 		<option value =""></option>
								  	 <% try{
								  		 if(diaban!=null)
								  		 while(diaban.next()){ 
								    	if(diaban.getString("pk_seq").equals(obj.getDiabanId())){ %>
								      		<option value='<%=diaban.getString("pk_seq") %>' selected><%=diaban.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=diaban.getString("pk_seq") %>'><%=diaban.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
									</SELECT></TD>
								</TR>
								<TR>
								  <TD class="plainlabel">Kênh bán hàng </TD>
								  <TD class="plainlabel">
								      	<SELECT name="kenhId" onChange = "submitform();">								      
								  	 		<option value =""></option>
								  	 <% try{ while(kenh.next()){ 
								    	if(kenh.getString("kenhId").equals(obj.getKenhId())){ %>
								      		<option value='<%=kenh.getString("kenhId") %>' selected><%=kenh.getString("kenh") %></option>
								      	<%}else{ %>
								     		<option value='<%=kenh.getString("kenhId") %>'><%=kenh.getString("kenh") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
                                  </SELECT></TD>
                                  <td colspan="2" class="plainlabel" > 
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                             	</td> 
							  </TR>
							
                            </TABLE>

                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE>
                    </TD>
                </TR>

                <TR>
                    <TD width="100%">
                        <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;Bảng giá&nbsp;&nbsp;	
                            	<%if(quyen[Utility.THEM]!=0 &&   !obj.getView().equals("NPP") ) {%>
                            	<a class="button3" href="javascript:newform()">
                           		<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>      
                            	<%} %>      
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
									<TH align="center" width="3%">STT</TH>
									<TH align="center" width="20%">Tên bảng giá </TH>
									<TH align="center"  width="10%">ĐVKD </TH>
									<TH align="center" width="8%">Kênh </TH>
									<!-- <TH width="8%">Từ ngày</TH> -->
									<TH align="center" width="8%">Trạng thái  </TH>
									<TH align="center" width="8%">Ngày tạo</TH>
									<TH align="center" width="12%">Người tạo </TH>
									<TH align="center" width="8%">Ngày sửa </TH>
									<TH align="center" width="12%">Người sửa </TH>
									<TH width="10%" align="center">&nbsp;Tác vụ</TH>
								</TR>
                                
                            <% 
							int m = 0;
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							try{
							while(bglist.next()){
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
							  <%} else {%>
									<TR class= <%= darkrow%> >
							  <%}%>
							  			<TD align="center"><%=m+1%></TD>
										<TD align="left"><%= bglist.getString("ten") %></TD>
										<TD align="left"><%= bglist.getString("dvkd") %></TD>
										<TD align="left"><%= bglist.getString("kenh") %></TD>	
										<%-- <TD align="left"><%= bglist.getString("TuNgay") %></TD> --%>
											
									<% if (bglist.getString("trangthai").equals("1")){ %>
										<TD align="left">Hoạt động</TD>							
									<%}else{ %>
										<TD align="left">Ngưng hoạt động</TD>
									<%} %>
									
										<TD align="center"><%= bglist.getDate("ngaytao").toString() %></TD>	
										<TD align="left"><%= bglist.getString("nguoitao") %></TD>
										<TD align="center"><%= bglist.getDate("ngaysua").toString() %></TD>
										<TD align="left"><%= bglist.getString("nguoisua") %></TD>
										<TD align="center">
											<TABLE border = 0 cellpadding="0" cellspacing="0">
																						
											<TR>											
											<TD>
											<% 	if(quyen[Utility.CHOT]!=0 &&  bglist.getString("trangthai").equals("0")  &&  !obj.getView().equals("NPP") ) {%>
												
														<A href = "../../BangGiaSvl?userId=<%=userId%>&chot=<%= bglist.getString("pk_seq") %>&loai=<%=obj.getLoai()%>&view=<%=obj.getView()%> "><img src="../images/Chot.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
												<%} %>
												
												<%if(quyen[Utility.SUA]!=0 &&  bglist.getString("trangthai").equals("0")  &&  !obj.getView().equals("NPP") ) {%>
													<%-- <A href = "../../BangGiaSvl?userId=<%=userId%>&copy=<%= bglist.getString("pk_seq") %>"><img src="../images/copy20.png" alt="Cap nhat" title="Copy bảng giá" width="20" height="20" longdesc="Copy bảng giá" border = 0></A> --%>
													<A href = "../../BangGiaUpdateSvl?userId=<%=userId%>&edit=<%= bglist.getString("pk_seq") %>&loai=<%=obj.getLoai()%>&view=<%=obj.getView()%> "><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
												<%}%>
												<%if(quyen[Utility.SUA]!=0 &&  bglist.getString("trangthai").equals("1")  &&  !obj.getView().equals("NPP") ) {%>
													<%-- <A href = "../../BangGiaSvl?userId=<%=userId%>&copy=<%= bglist.getString("pk_seq") %>"><img src="../images/copy20.png" alt="Cap nhat" title="Copy bảng giá" width="20" height="20" longdesc="Copy bảng giá" border = 0></A> --%>
													<A href = "../../BangGiaUpdateSvl?userId=<%=userId%>&display=<%= bglist.getString("pk_seq") %>&loai=<%=obj.getLoai()%>&view=<%=obj.getView()%> "><img src="../images/Display.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
												<%}%>
												<%-- Nút Copy --%>
												<%if(quyen[Utility.SUA]!=0 && !obj.getView().equals("NPP")) {%>
													<A href = "../../BangGiaSvl?userId=<%=userId%>&copy=<%= bglist.getString("pk_seq")%>&view=<%=obj.getView()%>">
														<img src="../images/copy20.png" alt="Copy" title="Copy bảng giá" width="20" height="20" longdesc="Copy bảng giá" border = 0>
													</A>
												<%}%>
												
												<%-- <%if(quyen[Utility.SUA]!=0 &&  bglist.getString("trangthai").equals("1")   ) {%>
													<A href = "../../BangGiaSvl?userId=<%=userId%>&copy=<%= bglist.getString("pk_seq") %>"><img src="../images/copy20.png" alt="Cap nhat" title="Copy bảng giá" width="20" height="20" longdesc="Copy bảng giá" border = 0></A>
													<A href = "../../BangGiaUpdateSvl?userId=<%=userId%>&edit=<%= bglist.getString("pk_seq") %>&loai=<%=obj.getLoai()%>&view=<%=obj.getView()%> "><img src="../images/Display.png" alt="Cap nhat" title="Hiển thị" width="20" height="20" longdesc="Cap nhat" border = 0></A>
												<%}%> --%>
												
												<% 	if(quyen[Utility.XOA]!=0 &&  bglist.getString("trangthai").equals("0") &&  !obj.getView().equals("NPP") ) {%>
													<%-- <A href = "../../BangGiaSvl?userId=<%=userId%>&delete=<%= bglist.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa bảng giá này ?')) return false;"></A>   --%>
														<A href = "../../BangGiaSvl?userId=<%=userId%>&delete=<%= bglist.getString("pk_seq") %>&loai=<%=obj.getLoai()%>&view=<%=obj.getView()%> "><img src="../images/Delete.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border = 0 onclick="if(!confirm('Bạn muốn xóa bảng giá này?')) return false;"></A>
												<%} %>
												
											</TD>
											</TR>
											</TABLE>
										</TD>
									</TR>
								<% m++; }
								
								} catch( Exception e){ System.out.println("Exception: " + e.getMessage()); }%>
                                
                                <TR class="tbfooter" >
									<TD align="center" colspan="15"> &nbsp; </TD>
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
</BODY>
</html>
<% 	
	try
    {
		if(bglist != null)
			bglist.close();
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {e.printStackTrace();} %>
<%}%>