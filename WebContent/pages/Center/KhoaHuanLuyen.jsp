<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.dms.center.beans.khoahuanluyen.IKhoahuanluyenList"%>
<%@ page import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%	
	IKhoahuanluyenList obj =(IKhoahuanluyenList)session.getAttribute("obj");
	ResultSet khlRs = obj.getKhoahuanluyenRs();
	
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
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
	
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	    document.forms['khl'].tungay.value= "";
	    document.forms['khl'].denngay.value= "";
	    document.forms['khl'].trangthai.value = "";
		document.forms['khl'].submit();
	}

	function submitform()
	{
		document.forms['khl'].action.value= 'search';
		document.forms['khl'].submit();
	}

	function newform()
	{
		document.forms['khl'].action.value= 'new';
		document.forms['khl'].submit();
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

<form name="khl" method="post" action="../../KhoahuanluyenSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý nhân sự > Khai báo > Khóa huấn luyện / Kỹ năng </TD>  
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
                             	<TD width="15%" class="plainlabel" >Từ ngày </TD>
								<TD class="plainlabel">
									<input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="20" onchange=submitform();>
								</TD>
                             </TR>
                             <TR>
                             	<TD width="20%" class="plainlabel" >Đến ngày </TD>
								<TD class="plainlabel">
									<input class="days" type="text" name="denngay" value="<%=obj.getDenngay() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                             <TR>
                             	<TD width="20%" class="plainlabel" >Trạng thái </TD>
								<TD class="plainlabel">
									<select name="trangthai" onChange="submitform();">
									<option value=""></option>
									<%
									String[]maTrangThai = new String[]{"0","1","2"};
									String[]tenTrangThai = new String[]{"Chưa chốt","Đã chốt","Đã hủy"}; 
									for(int i = 0 ; i<maTrangThai.length; i++)
									{ 
										if(maTrangThai[i].equals(obj.getTrangthai()))
										{
											%>
											<option value="<%= maTrangThai[i]%>" selected><%= tenTrangThai[i]%></option>
											<% 
										}else
										{
											%>
											<option value="<%= maTrangThai[i]%>"><%= tenTrangThai[i]%></option>
											<% 
										}
									}%>	
<%-- 									<% if(obj.getTrangthai().equals("0")){ %> --%>
<!-- 										<option value="0" selected>Chưa chốt</option> -->
<!-- 										<option value="1">Đã chốt</option> -->
<!-- 										<option value=""> </option> -->
<%-- 									<%} else { --%>
<!-- 										if (obj.getTrangthai().equals("1")){%>										 -->
<!-- 										<option value="0" >Chưa chốt</option> -->
<!-- 										<option value="1" selected>Đã chốt</option>										 -->
<!-- 										<option value=""> </option> -->
<%-- 									<%}else{ %> --%>
<!-- 										<option value="0" >Chưa chốt</option> -->
<!-- 										<option value="1" >Đã chốt</option>								 -->
<!-- 										<option value="" selected> </option> -->
<%-- 									<%	}  --%>
<!-- 								}%> -->
										
									    </select>
								</TD>
                             </TR >
                              
                             <tr class="plainlabel"> <td colspan="2" > 
                           		<a class="button2" href="javascript:clearform()">
									<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                             </td> </tr>
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
                            <LEGEND class="legendtitle">&nbsp;Khóa huấn luyện &nbsp;&nbsp;	   
                                                    
							<a class="button3" href="javascript:newform()">
                           	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
	                              
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="9%"> Mã khóa</TH>
                                    <TH width="20%"> Tiêu đề</TH>
                                    <TH width="9%"> Từ ngày</TH>
                                    <TH width="9%"> Đến ngày</TH>
                                    <TH width="9%"> Trạng thái</TH>
                                    <TH width="7%"> Ngày tạo</TH>
                                    <TH width="9%"> Người tạo</TH>
                                    <TH width="9%"> Ngày sửa</TH>
                                    <TH width="9%"> Người sửa</TH>
                                    <TH width="8%" align="center">&nbsp;Tác vụ</TH>
                                </TR>
                                <% 
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(khlRs != null)
                                    while ( khlRs.next()){
                                       
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><%= khlRs.getString("pk_seq") %></TD>                                   
                                                <TD><%= khlRs.getString("tieude")%></TD>
												<TD><%= khlRs.getString("tungay")%></TD>
												<TD><%= khlRs.getString("denngay")%></TD>
                                               		<%
												for(int i = 0;i<maTrangThai.length;i++)
												{
													if(maTrangThai[i].equals(khlRs.getString("trangthai").trim()))
													{
														%><TD align="center"><%=tenTrangThai[i] %></TD><%
													}
												}
													%>
                                                <TD align="center"><%= khlRs.getString("NGAYTAO")%> </TD>
                                                <TD align="center"><%= khlRs.getString("NGUOITAO")%></TD>
                                                <TD align="center"><%= khlRs.getString("NGAYSUA")%> </TD>
                                                <TD align="center"><%= khlRs.getString("NGUOISUA")%> </TD>
                                                <TD align="center"> 
                                                <%if( khlRs.getString("trangthai").trim().equals("0") ) {%>
					                                <A href = "../../KhoahuanluyenUpdateSvl?userId=<%=userId%>&update=<%= khlRs.getString("pk_seq") %>"><IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border=0></A>&nbsp;
					                                <A href = "../../KhoahuanluyenSvl?userId=<%=userId%>&delete=<%= khlRs.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa khóa huấn luyện này?')) return false;"></A>		
					                                 <A href = "../../KhoahuanluyenSvl?userId=<%=userId%>&chot=<%= khlRs.getString("pk_seq") %>"><img src="../images/Chot.png" alt="Chot" title="Chot" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn chốt khóa huấn luyện này?')) return false;"></A>								
							                   <%} else { %>
							                   		<A href = "../../KhoahuanluyenUpdateSvl?userId=<%=userId%>&display=<%= khlRs.getString("pk_seq") %>"><IMG src="../images/Display20.png" alt="Hien thi" title="Hiển thị" border=0></A>&nbsp;
							                   <%} %>
							                    </TD>
                                            </TR>
                                        <% m++; } %>  
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
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<% 	
	try
    {
		if(khlRs != null)
			khlRs.close();
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {} %>
<%}%>