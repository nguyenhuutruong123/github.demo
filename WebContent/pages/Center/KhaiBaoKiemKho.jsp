<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.dms.center.beans.khaibaokiemkho.IKhaiBaoKiemKhoList"%>
<%@ page import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Phanam/index.jsp");
	}else{ %>
<%	
	IKhaiBaoKiemKhoList obj =(IKhaiBaoKiemKhoList)session.getAttribute("obj");
	ResultSet bglist = (ResultSet)obj.getKiemkhoRs();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("KhaiBaoKiemKhoSvl","",userId);
	
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

<form name="khtt" method="post" action="../../KhaiBaoKiemKhoSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
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
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý khảo sát > Chức năng > Khai báo kiểm kho</TD>  
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
									<TD class="plainlabel" width="100px">Diễn giải</TD>
									<TD class="plainlabel" colspan="3">
										<INPUT name="diengiai" type="text" size="40" value='<%=obj.getDiengiai() %>' onChange = "submitform();"/></TD>
								</TR>
								<TR>
								    <TD class="plainlabel">Từ ngày</TD>
								    <TD class="plainlabel" width="250px">
								    	 <input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform();" />
								    </TD>
									<TD class="plainlabel" width="100px">Đến ngày</TD>
									<TD class="plainlabel">
										<input type="text" class="days" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform();" />
									</TD>	
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
                            <LEGEND class="legendtitle">&nbsp;Khai báo&nbsp;&nbsp;	
                            	<%if(quyen[Utility.THEM]!=0) {%>
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
									<TH align="center" width="20%">Diễn giải</TH>
									<!-- <TH align="center" width="8%">Trạng thái  </TH> -->
									<TH align="center" width="8%">Ngày tạo</TH>
									<TH align="center" width="12%">Người tạo </TH>
									<TH align="center" width="8%">Ngày sửa </TH>
									<TH align="center" width="12%">Người sửa </TH>
									<TH width="10%" align="center">Tác vụ</TH>
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
										<TD align="left"><%= bglist.getString("DIENGIAI") %></TD>									
										<TD align="center"><%= bglist.getDate("ngaytao").toString() %></TD>	
										<TD align="left"><%= bglist.getString("nguoitao") %></TD>
										<TD align="center"><%= bglist.getDate("ngaysua").toString() %></TD>
										<TD align="left"><%= bglist.getString("nguoisua") %></TD>
										<TD align="center">
											<%if(quyen[Utility.SUA]!=0 && bglist.getString("TRANGTHAI").equals("0")) {%>
												<A href = "../../KhaiBaoKiemKhoUpdateSvl?userId=<%=userId%>&edit=<%= bglist.getString("pk_seq") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
											<%}
												if(quyen[Utility.XOA]!=0 && bglist.getString("TRANGTHAI").equals("0")) {%>
												<A href = "../../KhaiBaoKiemKhoSvl?userId=<%=userId%>&delete=<%= bglist.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa khai báo này ?')) return false;"></A>  
											<%} 
												if(quyen[Utility.CHOT] != 0 && bglist.getString("TRANGTHAI").equals("0")){%>
												<A href = "../../KhaiBaoKiemKhoSvl?userId=<%=userId%>&duyet=<%= bglist.getString("pk_seq") %>"><img src="../images/Chot.png" alt="Cap nhat" title="Duyệt" width="20" height="20" longdesc="Cap nhat" border = 0 onclick="if(!confirm('Bạn có muốn duyệt khai báo này ?')) return false;"></A>
											<%}
												if(quyen[Utility.XEM] != 0 && bglist.getString("TRANGTHAI").equals("1")){%>
												<A href = "../../KhaiBaoKiemKhoUpdateSvl?userId=<%=userId%>&view=<%= bglist.getString("pk_seq") %>"><img src="../images/Display.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0 ></A>
												<%if(quyen[Utility.CHOT]!=0){ %>
									   		<A href = "../../KhaiBaoKiemKhoSvl?userId=<%=userId%>&unchot=<%=bglist.getString("pk_seq")%>"><img src="../images/unChot.png" alt="UnChot" width="20" height="20" longdesc="Chot" border = 0 onclick="if(!confirm('Bạn có muốn bỏ chốt kiểm kho này?')) return false;"></A>
									     	<%} %>
											<%} %>
													
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
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
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