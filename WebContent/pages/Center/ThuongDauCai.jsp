j <!--desigs : khoana, chi tieu trung tam cho tung vung -->

<%@page import="java.util.Calendar"%>
<%@page import="geso.dms.center.beans.thuongdauthung.imp.Thuongdauthung"%>
<%@page import="geso.dms.center.beans.thuongdauthung.IThuongdauthung"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.banggiasieuthi.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	IThuongdauthung obj=(Thuongdauthung)session.getAttribute("obj");	
	Integer thang=(Integer)session.getAttribute("thang");
	Integer nam=(Integer)session.getAttribute("nam");
	ResultSet luongkhacRs = obj.getLuongkhacRs();

	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Bibica/index.jsp");
	}else{
	int[] quyen = new  int[5];
	quyen = util.Getquyen("24",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);
	System.out.println(quyen[4]);
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
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	    document.forms['bgstForm'].nam.value=0;
	    document.forms['bgstForm'].thang.value=0;
	    document.forms['bgstForm'].action.value= 'search';
		document.forms['bgstForm'].submit();
	}

	function submitform()
	{
	
		document.forms['bgstForm'].action.value= 'search';
		document.forms['bgstForm'].submit();
	}

	function newform()
	{
		document.forms['bgstForm'].action.value= 'new';
		document.forms['bgstForm'].submit();
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

<form name="bgstForm" method="post" action="../../ThuongdauthungSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="nppId" value="" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
        <!--begin common dossier info---> <!--End common dossier info--->
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">

                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý chỉ tiêu &gt; Chỉ tiêu nhân viên &gt; Thưởng đầu cái</TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn &nbsp;<%=userTen%>  </TD>
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
                             <TD width="15%" class="plainlabel" >Tháng &nbsp;&nbsp;  <FONT class="erroralert"> </FONT></TD>
								<TD class="plainlabel">
									<select name="thang" style="width :50px">
									<option value=0> </option>  
									<%
									int k=1;
									for(k=1;k<=12;k++){
									  if(k==thang){
									  
										%>
										<option value=<%=k %> selected="selected" > <%=k %></option> 
										<%
									  }else{
										 %>
										<option value=<%=k %> ><%=k %></option> 
										<%
									  }
									  }
									%>
									</select>
								</TD>
                             </TR>
                              <TR>
                             <TD width="15%" class="plainlabel" >Năm &nbsp;&nbsp;  <FONT class="erroralert"> </FONT></TD>
								<TD class="plainlabel">
									<select name="nam"  style="width :50px">
									<option value=0> </option>  
									<%
									Calendar cal=Calendar.getInstance();
									int year_=cal.get(Calendar.YEAR);
									for(int n=2008;n<year_+3;n++){
									  if(n==nam){									  
									%>
									<option value=<%=n %> selected="selected" > <%=n %></option> 
									<%
									  }else{
									 %>
									<option value=<%=n %> ><%=n %></option> 
									<%
									  }
									  }
									%>
									</select>
								</TD>
                             </TR >
                             
                             
                             <tr class="plainlabel"> <td colspan="2" > 
                             <a class="button3" href="javascript:submitform()">
                           	<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>   &nbsp;&nbsp;&nbsp;
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
                            <LEGEND class="legendtitle">&nbsp;Quản lý Lương Khác &nbsp;&nbsp;	   
                            <%if(quyen[0]!=0) {%>                        
							<a class="button3" href="javascript:newform()">
                           	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
	                           <%} %>  
                            </LEGEND>
    
                      <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="">Từ ngày </TH>
                                    <TH width="">Đến ngày  </TH>
                                    <TH width="">Diễn giải </TH>
                                    <TH width="">Trạng thái</TH> 
                                    <TH width="">Ngày tạo</TH>
                                    <TH width="">Người tạo</TH>
                                    <TH width="">Ngày sửa</TH>
                                    <TH width="">Người sửa </TH>
                                    <TH width="" align="center">&nbsp;Tác vụ</TH>
                                </TR>
                                <% 
                                    
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(luongkhacRs != null)
                                    while (luongkhacRs.next()){
                                        
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><div align="left"><%=luongkhacRs.getString("tungay")%></div></TD>                                   
                                                <TD><div align="center"><%=luongkhacRs.getString("denngay")%></div></TD>
                                                <TD><div align="center"><%=luongkhacRs.getString("DIENGIAI")%></div></TD>
                                                <% if(luongkhacRs.getString("TRANGTHAI").trim().equals("0")){%>
                                                 <TD align="center">Chưa xử lý</TD>
                                                 <%}else{ %>
                                                  <TD align="center">Đã chốt</TD>
                                                 <%} %>
                                                <TD align="center"><%= luongkhacRs.getString("NGAYTAO")%></TD>
                                                 <TD align="center"><%=luongkhacRs.getString("NGUOITAO")%></TD>
                                                  <TD align="center"><%= luongkhacRs.getString("NGAYSUA")%></TD>
                                                   <TD align="center"><%= luongkhacRs.getString("NGUOISUA")%></TD>
                                                <TD align="center">
                                               		 <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR>
                                              		   <TD>
                                              		   <%if(quyen[3]!=0){ %>
                                                        <A href = "../../ThuongdauthungNewSvl?userId=<%=userId%>&display=<%=luongkhacRs.getString("PK_SEQ")%>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" title="Hiển thị" border = 0></A>
                                                    	<%} %>
                                                     </TD>
                                                    <%
             										System.out.println(luongkhacRs.getString("TRANGTHAI"));
                                                    if(luongkhacRs.getString("TRANGTHAI").trim().equals("0")){ %>
                                                    <TD>
                                                    	<%if(quyen[2]!=0) {%>
                                                        <A href = "../../ThuongdauthungNewSvl?userId=<%=userId%>&update=<%=luongkhacRs.getString("PK_SEQ")%>"><img src="../images/Edit20.png" alt="Cập nhật" width="20" height="20" title="Cập nhật" border = 0></A>
                                                    	<%} %>
                                                    </TD>
                                                    <TD>
                                                   		<%if(quyen[1]!=0) {%>
                                                        <A href = "../../ThuongdauthungSvl?userId=<%=userId%>&delete=<%=luongkhacRs.getString("PK_SEQ")%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" title="Xóa" border=0 onclick="if(!confirm('Bạn chắc chắn muốn xóa thưởng  này?')) return false;"></A></TD>
                                                     	<%} %>
                                                     <TD>
                                                     	<%if(quyen[4]!=0) {%>
                                                        <A href = "../../ThuongdauthungSvl?userId=<%=userId%>&chot=<%=luongkhacRs.getString("PK_SEQ")%>"><img src="../images/Chot.png" alt="Chốt" width="20" height="20" title="Chốt chỉ tiêu" border = 0 onclick="if(!confirm('Bạn chắc chắn muốn chốt thưởng này?')) return false;"></A>
                                                     	<%} %>
                                                     </TD>
                                                     <%}else{ %>
                                                     
                                                     <TD>
                                                     	<%if(quyen[4]!=0) {%>
                                                        <A href = "../../ThuongdauthungSvl?userId=<%=userId%>&unchot=<%=luongkhacRs.getString("PK_SEQ")%>"><img src="../images/unChot.png" alt="Hien thi" width="20" height="20" title="Hiển thị" border = 0 onclick="if(!confirm('Bạn chắc chắn muốn  mở chốt thưởng này?')) return false;"></A>
                                                    	<%} %>
                                                     </TD>
                                                    <%}%>
                                                    </TR></TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } %>  
                                        <tr class="tbheader"> <td colspan="15"> &nbsp;</td></tr>                                                 
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
<%} %>
</html>
<%
	try
	{
		
		if(obj != null){
			obj.closeDB();
			obj = null;
		}
		session.setAttribute("obj", null);
	}catch(Exception e){}
	
%>
