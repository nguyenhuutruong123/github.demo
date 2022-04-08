<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "geso.dms.center.beans.khaosat.*" %>
<%@ page import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SalesUpERP/index.jsp");
	}else{ 
		int[] quyen = new int[5];
		quyen = util.Getquyen("KhaosatSvl", "", userId);
	%>
	
<%	
	IKhaosatList obj =(IKhaosatList)session.getAttribute("obj");
	ResultSet csxRs = obj.getKhaosatRs();
	
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("KhaosatSvl","",nnId);	
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
	    //document.forms['khtt'].manguongoc.value= "";
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

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="khtt" method="post" action="../../KhaosatSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
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
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %> </TD>  
                            <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng bạn",nnId) %> <%= userTen %></TD>
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
                              <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId) %> &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                             <TR>
                             	<TD width="20%" class="plainlabel" ><%=ChuyenNgu.get("Diễn giải",nnId) %> </TD>
								<TD class="plainlabel">
									<input  type="text" name="diengiai" value="<%=obj.getDiengiai() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                             <TR>
                             	<TD width="20%" class="plainlabel" ><%=ChuyenNgu.get("Trạng thái",nnId) %> </TD>
								<TD class="plainlabel">
									<select name="trangthai" onChange="submitform();">
										<% if(obj.getTrangthai().equals("0")){ %>
											<option value="0" selected><%=ChuyenNgu.get("Chưa chốt",nnId) %></option>
											<option value="1"><%=ChuyenNgu.get("Đã chốt",nnId) %></option>
											<option value="2"><%=ChuyenNgu.get("Đã hủy",nnId) %></option>
											<option value=""> </option>
										<%} else { if( obj.getTrangthai().equals("1") ) { %>										
											<option value="0" ><%=ChuyenNgu.get("Chưa chốt",nnId) %></option>
											<option value="1" selected><%=ChuyenNgu.get("Đã chốt",nnId) %></option>	
											<option value="2"><%=ChuyenNgu.get("Đã hủy",nnId) %></option>									
											<option value=""> </option>
										<% } else { if( obj.getTrangthai().equals("2") ) { %>
											<option value="2" selected="selected" ><%=ChuyenNgu.get("Đã hủy",nnId) %></option>	
											<option value="0" ><%=ChuyenNgu.get("Chưa chốt",nnId) %></option>
											<option value="1"><%=ChuyenNgu.get("Đã chốt",nnId) %></option>										
											<option value=""> </option>
										 <% } else { %> 
										 	<option value="" ></option>	
											<option value="0" ><%=ChuyenNgu.get("Chưa chốt",nnId) %></option>
											<option value="1"><%=ChuyenNgu.get("Đã chốt",nnId) %></option>										
											<option value="2"><%=ChuyenNgu.get("Đã hủy",nnId) %></option>
										 <% } }  }  %>
										
									 </select>
								</TD>
                             </TR >
                              
                             <tr class="plainlabel"> 
                             	<td colspan="2" > 
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	
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
                            <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Khảo sát",nnId) %> &nbsp;&nbsp;	
                            	 <%if(quyen[0]!=0){ %> 
                            	<a class="button3" href="javascript:newform()">
                           		<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> </a>      
                            	 <%} %>     
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="10%" align="center"><%=ChuyenNgu.get("Mã",nnId) %></TH>
                                    <TH width="20%" align="center"><%=ChuyenNgu.get("Diễn giải",nnId) %></TH>
                                    <TH width="10%" align="center"><%=ChuyenNgu.get("Bộ phận",nnId) %></TH>
                                    <TH width="10%" align="center"><%=ChuyenNgu.get("Trạng thái",nnId) %></TH>
                                    <TH width="10%" align="center"><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
                                    <TH width="10%" align="center"><%=ChuyenNgu.get("Người tạo",nnId) %></TH>
                                    <TH width="10%" align="center"><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
                                    <TH width="10%" align="center"><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
                                    <TH width="10%" align="center"><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
                                </TR>
                                <% 
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(csxRs != null)
                                    while ( csxRs.next()){
                                       
                                    	String tt = csxRs.getString("trangthai").trim();
                                    	
                                        if (m % 2 != 0) { %>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="center"><%= csxRs.getString("pk_seq") %></TD>  
                                                <TD align="left"><%= csxRs.getString("diengiai")%></TD>                                 
                                                <TD align="left"><%= csxRs.getString("bophan")%></TD>
                                                <% if( tt.equals("0") ) { %>
                                                	<TD align="center"><%=ChuyenNgu.get("Chưa chốt",nnId) %></TD>
                                                <% } else { if(tt.equals("1")) { %>       
                                                	<TD align="center"><%=ChuyenNgu.get("Đã chốt",nnId) %></TD>
                                                <%} else { %>
                                                	<TD align="center"><%=ChuyenNgu.get("Đã hủy",nnId) %></TD>
                                                <% } }  %> 
                                                	
                                                <TD align="center"><%= csxRs.getString("NGAYTAO")%> </TD>
                                                <TD align="left"><%= csxRs.getString("NGUOITAO")%></TD>
                                                <TD align="center"><%= csxRs.getString("NGAYSUA")%> </TD>
                                                <TD align="left"><%= csxRs.getString("NGUOISUA")%> </TD>
                                                <TD align="center"> 
                                                <% if( tt.equals("0") ) { %>
                                                	<% if (quyen[2] != 0){%>
							                   			<A href = "../../KhaosatUpdateSvl?userId=<%=userId%>&update=<%= csxRs.getString("pk_seq") %>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
													<%} %>
													<% if (quyen[1] != 0){%>
														<A href = "../../KhaosatSvl?userId=<%=userId%>&delete=<%= csxRs.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Ban Muon Xoa Khao Sat Nay?')) return false;"></A>
													<%} %>
													<% if (quyen[4] != 0){%>
														<A href = "../../KhaosatSvl?userId=<%=userId%>&chot=<%= csxRs.getString("pk_seq") %>"><img src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border=0 onclick="if(!confirm('Ban Muon Chot Khao Sat Nay?')) return false;"></A>
													<%} %>
													<A href = "../../KhaosatUpdateSvl?userId=<%=userId%>&display=<%= csxRs.getString("pk_seq") %>"><IMG src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0></A>&nbsp;
												<% } else { %>
													<A href = "../../KhaosatUpdateSvl?userId=<%=userId%>&display=<%= csxRs.getString("pk_seq") %>"><IMG src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0></A>&nbsp;
													  <% if (quyen[0] != 0){%>         
                     								 	<A href = "../../KhaosatUpdateSvl?userId=<%=userId%>&copy=<%= csxRs.getString("pk_seq")%>"><IMG src="../images/copy20.png" alt="Copy" title="Copy" border="0"></A>
                     	 								<%} %>
												<% }  %>
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