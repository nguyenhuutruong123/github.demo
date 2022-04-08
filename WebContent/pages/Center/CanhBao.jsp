<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "geso.dms.center.beans.canhbao.*" %>
<%@ page import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	String url="";
	url = util.getUrl("CanhbaoSvl","");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/OPV/index.jsp");
	}else{ %>
<%	
	ICanhbaoList obj =(ICanhbaoList)session.getAttribute("obj");
	ResultSet csxRs = obj.getCanhbaoRs();
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>OPV - Project</TITLE>
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
	    document.forms['khtt'].manguongoc.value= "";
	    document.forms['khtt'].diengiai.value= "";
	    document.forms['khtt'].trangthai.value = "";
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

<form name="khtt" method="post" action="../../CanhbaoSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
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
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%=url %> </TD>  
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
                             	<TD width="20%" class="plainlabel" >Nội dung </TD>
								<TD class="plainlabel">
									<input  type="text" name="diengiai" value="<%=obj.getDiengiai() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                             <TR>
                             	<TD width="20%" class="plainlabel" >Loại cảnh báo </TD>
								<TD class="plainlabel">
									<select name="trangthai" onChange="submitform();">
										<option value="" > </option>
										<% if(obj.getTrangthai().equals("Hết hạn sản phẩm")) { %>
											<option value="Hết hạn sản phẩm" selected="selected" >Hết hạn sản phẩm</option>
										 <%  } else { %> 
										 	<option value="Hết hạn sản phẩm" >Hết hạn sản phẩm</option>
										 <% } %>
										 
										 <% if(obj.getTrangthai().equals("Hết hạn hợp đồng SR")) { %>
											<option value="Hết hạn hợp đồng SR" selected="selected" >Hết hạn hợp đồng SR</option>
										 <%  } else { %> 
										 	<option value="Hết hạn hợp đồng SR" >Hết hạn hợp đồng SR</option>
										 <% } %>
										 
										 <% if(obj.getTrangthai().equals("Hết hạn hợp đồng SS")) { %>
											<option value="Hết hạn hợp đồng SS" selected="selected" >Hết hạn hợp đồng SS</option>
										 <%  } else { %> 
										 	<option value="Hết hạn hợp đồng SS" >Hết hạn hợp đồng SS</option>
										 <% } %>
										 
										 <% if(obj.getTrangthai().equals("Hết hạn hợp đồng Quản lý khu vực")) { %>
											<option value="Hết hạn hợp đồng Quản lý khu vực" selected="selected" >Hết hạn hợp đồng Quản lý khu vực</option>
										 <%  } else { %> 
										 	<option value="Hết hạn hợp đồng Quản lý khu vực" >Hết hạn hợp đồng Quản lý khu vực</option>
										 <% } %>
										
									 </select>
								</TD>
                             </TR >
                              
                             <tr class="plainlabel"> 
                             	<td colspan="2" > 
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                             	</td> 
                             </tr>
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
                            <LEGEND class="legendtitle">&nbsp;Cảnh báo &nbsp;&nbsp;	</LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="10%" align="center">Mã</TH>
                                    <TH width="55%" align="center">Nội dung</TH>
                                    <TH width="15%" align="center">Loại</TH>
                                    <TH width="15%" align="center">Ngày tạo</TH>
                                    <TH width="5%" align="center">Tác vụ</TH>
                                </TR>
                                <% 
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(csxRs != null)
                                    while ( csxRs.next()){
                                       
                                        if (m % 2 != 0) { %>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="center"><%= csxRs.getString("pk_seq") %></TD>                                   
                                                <TD align="left"><%= csxRs.getString("noidung")%></TD>
                                                <TD align="left"><%= csxRs.getString("loaithongbao")%></TD>
                                                <TD align="center"><%= csxRs.getString("ngaytao")%> </TD>
                                                <TD align="center"> 

							                   		<%-- <A href = "../../CanhbaoUpdateSvl?userId=<%=userId%>&display=<%= csxRs.getString("pk_seq") %>"><IMG src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0></A>&nbsp; --%>
													<A href = "../../CanhbaoSvl?userId=<%=userId%>&delete=<%= csxRs.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Ban Muon Xoa Nha may Nay?')) return false;"></A>
												
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
		if(csxRs != null)
			csxRs.close();
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {} %>
<%}%>