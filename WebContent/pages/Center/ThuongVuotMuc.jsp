<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.dms.center.beans.thuongvuotmuc.IThuongvuotmucList"%>
<%@ page import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%	
	IThuongvuotmucList obj =(IThuongvuotmucList)session.getAttribute("obj");
	ResultSet khlRs = obj.getKhlRs();
	
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
	    document.forms['khl'].thang.value= "";
	    document.forms['khl'].nam.value= "";
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

<form name="khl" method="post" action="../../ThuongvuotmucSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
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
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý nhân sự > Khai báo > Thưởng vượt mức</TD>  
                            <TD colspan="2" align="right" class="tbnavigation"> Chào mừng bạn <%= userTen %></TD>
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
									<TD class="plainlabel" >Tháng </TD>
									<TD class="plainlabel">
										<select name="thang" id = "thang" style="width: 50px"  onChange="submitform();">
										<option value= ""> </option>  
										<%
										int k=1;
										for(k=1; k <= 12; k++ ){
											
										  if(obj.getTungay().equals(Integer.toString(k) ) ) {
										%>
											<option value=<%= k %> selected="selected" > <%= k %></option> 
										<%  }else{  %>
											<option value=<%= k %> > <%= k %></option> 
										<% } }%>
										</select>
									</TD>
								</TR>
								<TR>
								  	<TD class="plainlabel">Năm </TD>
							  	  	<TD class="plainlabel">
										<select name="nam" id = "nam" style="width :50px"  onChange="submitform();">
										<option value= ""> </option>  
										<%
										Calendar cal=Calendar.getInstance();
										int year_=cal.get(Calendar.YEAR);
										for(int n=2008; n<year_+3; n++) {
										  if(obj.getDenngay().equals( Integer.toString(n)) ){									  
										%>
											<option value=<%=n %> selected="selected" > <%=n %></option> 
										<%
										  }else{
										 %>
											<option value=<%= n %> ><%=n %></option> 
										<% } }
										%>
										</select>
							  	  	</TD>
							  </TR>
                             <TR>
                             	<TD width="20%" class="plainlabel" >Trạng thái </TD>
								<TD class="plainlabel">
									<select name="trangthai" onChange="submitform();">
									<% if(obj.getTrangthai().equals("0")){ %>
										<option value="0" selected>Chưa chốt</option>
										<option value="1">Đã chốt</option>
										<option value=""></option>
									<%} else {
										if (obj.getTrangthai().equals("1")){%>										
										<option value="0" >Chưa chốt</option>
										<option value="1" selected>Đã chốt</option>										
										<option value=""> </option>
									<%}else{ %>
										<option value="0" >Chưa chốt</option>
										<option value="1" >Đã chốt</option>								
										<option value="" selected> </option>
									<%	} 
								}%>
										
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
                            <LEGEND class="legendtitle">&nbsp;Thưởng vượt mức &nbsp;&nbsp;	   
                                                    
							<a class="button3" href="javascript:newform()">
                           	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
	                              
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="8%"> Mã số</TH>
                                    <TH width="15%"> Diễn giải</TH>
                                    <TH width="8%"> Tháng</TH>
                                    <TH width="8%"> Năm</TH>
                                    <TH width="8%"> Trạng thái</TH>
                                    <TH width="5%"> Ngày tạo</TH>
                                    <TH width="15%"> Người tạo</TH>
                                    <TH width="8%"> Ngày sửa</TH>
                                    <TH width="15%"> Người sửa</TH>
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
                                                <TD><%= khlRs.getString("diengiai")%></TD>
												<TD><%= khlRs.getString("thang")%></TD>
												<TD><%= khlRs.getString("nam")%></TD>
                                                <%if( khlRs.getString("trangthai").trim().equals("0") ) {%>
                                                <TD align="center">Chưa chốt</TD>
                                                <%}else{ %>
                                                <TD align="center">Đã chốt</TD>
                                                <%} %> 
                                                <TD align="center"><%= khlRs.getString("NGAYTAO")%> </TD>
                                                <TD align="center"><%= khlRs.getString("NGUOITAO")%></TD>
                                                <TD align="center"><%= khlRs.getString("NGAYSUA")%> </TD>
                                                <TD align="center"><%= khlRs.getString("NGUOISUA")%> </TD>
                                                <TD align="center"> 
                                                <%if( khlRs.getString("trangthai").trim().equals("0") ) {%>
					                                <A href = "../../ThuongvuotmucUpdateSvl?userId=<%=userId%>&update=<%= khlRs.getString("pk_seq") %>"><IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border=0></A>&nbsp;
					                                <A href = "../../ThuongvuotmucUpdateSvl?userId=<%=userId%>&copy=<%= khlRs.getString("pk_seq") %>"><IMG src="../images/copy20.png" width="20" height="20" alt="Copy" title="Copy" border=0></A>&nbsp;
					                                <A href = "../../ThuongvuotmucSvl?userId=<%=userId%>&chot=<%= khlRs.getString("pk_seq") %>"><img src="../images/Chot.png" alt="Chot" title="Chốt" width="20" height="20" longdesc="Chot" border=0 onclick="if(!confirm('Bạn có muốn chốt thưởng vượt mức này?')) return false;"></A>&nbsp;
					                                <A href = "../../ThuongvuotmucSvl?userId=<%=userId%>&delete=<%= khlRs.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa thưởng vượt mức này?')) return false;"></A>
							                   <%} else { %>
							                   		<A href = "../../ThuongvuotmucUpdateSvl?userId=<%=userId%>&copy=<%= khlRs.getString("pk_seq") %>"><IMG src="../images/copy20.png" width="20" height="20" alt="Copy" title="Copy" border=0></A>&nbsp;
							                   		<A href = "../../ThuongvuotmucUpdateSvl?userId=<%=userId%>&display=<%= khlRs.getString("pk_seq") %>"><IMG src="../images/Display20.png" alt="Hien thi" title="Hiển thị" border=0></A>&nbsp;
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