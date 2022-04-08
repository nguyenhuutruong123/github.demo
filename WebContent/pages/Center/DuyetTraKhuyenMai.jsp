<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.dms.center.beans.duyettrakhuyenmai.*"%>
<%@page import="geso.dms.center.beans.duyettrakhuyenmai.imp.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SOHACO/index.jsp");
	}else{ %>
<%	
	IDuyettrakhuyenmaiList obj=(IDuyettrakhuyenmaiList)session.getAttribute("obj");
	ResultSet tieuchiSKU = obj.getTieuchiSKUInRs();
	int[] quyen = new  int[6];
	quyen = util.Getquyen("DuyettrakhuyenmaiSvl","",userId);
	
%>
<% String nnId = (String)session.getAttribute("nnId"); %>
<% if(nnId == null) {
 nnId = "vi";  
 }
 String url = util.getChuyenNguUrl("DuyettrakhuyenmaiSvl","" ,nnId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>SOHACO - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	    document.forms['tcsku'].nam.value= "";
	    document.forms['tcsku'].thang.value= "";
	    document.forms['tcsku'].diengiai.value= "";
	    document.forms['tcsku'].trangthai.value= "";
		document.forms['tcsku'].submit();
	}

	function submitform()
	{
		document.forms['tcsku'].action.value= 'search';
		document.forms['tcsku'].submit();
	}

	function newform()
	{
		document.forms['tcsku'].action.value= 'new';
		document.forms['tcsku'].submit();
	}
	function thongbao()
 	{   
		var tt = document.forms['tcsku'].msg.value;
	 	if(tt.length>0)
	     	alert(document.forms['tcsku'].msg.value);
	 	
	 	document.forms['tcsku'].msg.value= '';
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

<form name="tcsku" method="post" action="../../DuyettrakhuyenmaiSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="task" value="TT" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value="<%=obj.getMsg()%>" >

<script language="javascript" type="text/javascript">
    thongbao();
</script>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
        <!--begin common dossier info---> <!--End common dossier info--->
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation"><%=url %> </TD>  
                            <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId) %> <%= userTen %></TD>
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
                             <TD width="15%" class="plainlabel" ><%=ChuyenNgu.get("Tháng",nnId) %> </TD>
								<TD class="plainlabel">
									<select name="thang" style="width: 100px" onchange="submitform();">
									<option value= ""> </option>  
									<%
									int k=1;
									for(k=1; k <= 12; k++ ){
										
									  if(obj.getThang().equals(Integer.toString(k)) ) {
									%>
										<option value=<%= k %> selected="selected" > <%= k %></option> 
									<%  }else{  %>
										<option value=<%= k %> > <%= k %></option> 
									<% } }%>
									</select>
								</TD>
                             </TR>
                              <TR>
                             <TD width="20%" class="plainlabel" ><%=ChuyenNgu.get("Năm",nnId) %> </TD>
								<TD class="plainlabel">
									<select name="nam"  style="width :100px" onchange="submitform();">
									<option value= ""> </option>  
									<%
									Calendar cal=Calendar.getInstance();
									int year_=cal.get(Calendar.YEAR);
									for(int n=2008; n<year_+3; n++) {
									  if(obj.getNam().equals( Integer.toString(n)) ){									  
									%>
										<option value=<%=n %> selected="selected" > <%=n %></option> 
									<%
									  }else{
									 %>
										<option value=<%=n %> ><%=n %></option> 
									<% } }
									%>
									</select>
								</TD>
                             </TR >
                              
                               <TR class="plainlabel">
	                              <TD class="plainlabel"><%=ChuyenNgu.get("Diễn giải/Scheme",nnId) %></TD>
	                              <TD class="plainlabel">
	                              	<input name= "diengiai" type="text" value='<%= obj.getDiengiai()%>' onchange="submitform();">
	                              </TD>
                              </TR>
                              
                              <TR class="plainlabel">
	                              <TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId) %></TD>
	                              <TD class="plainlabel">
	                              	<select name = "trangthai" onchange="submitform();">
	                              	<%if(obj.getTrangthai().equals("0")) {%>
	                              		<option value="" ></option>
	                              		<option value="0" selected="selected"><%=ChuyenNgu.get("Chưa chốt",nnId) %></option>
	                              		<option value="1" ><%=ChuyenNgu.get("Đã duyệt",nnId) %></option>
	                              	<%} else if(obj.getTrangthai().equals("1")) {%>
	                              		<option value="" ></option>
	                              		<option value="0" ><%=ChuyenNgu.get("Chưa chốt",nnId) %></option>
	                              		<option value="1" selected="selected"><%=ChuyenNgu.get("Đã duyệt",nnId) %></option>
	                              	<%}else {%>
	                              		<option value="" selected="selected"></option>
	                              		<option value="0" ><%=ChuyenNgu.get("Chưa chốt",nnId) %></option>
	                              		<option value="1" ><%=ChuyenNgu.get("Đã duyệt",nnId) %></option>
	                              	<%} %>
	                              	</select>
							
	                              </TD>
                              </TR>
                              
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
                            <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Duyệt trả khuyến mại tích lũy",nnId) %> &nbsp;&nbsp;	   
                              <%if(quyen[Utility.THEM]!=0){ %>                    
							<a class="button3" href="javascript:newform()">
                           	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> </a>                            
	                            <%} %>  
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width=""><%=ChuyenNgu.get("Scheme",nnId) %> </TH>
                                    <TH width=""><%=ChuyenNgu.get("Diễn giải",nnId) %> </TH>
                                    <TH width=""><%=ChuyenNgu.get("Trạng thái",nnId) %> </TH>
                                    <TH width=""><%=ChuyenNgu.get("Ngày tạo",nnId) %> </TH>
                                    <TH width=""><%=ChuyenNgu.get("Người tạo",nnId) %> </TH>
                                    <TH width=""><%=ChuyenNgu.get("Ngày sửa",nnId) %> </TH>
                                    <TH width=""><%=ChuyenNgu.get("Người sửa",nnId) %> </TH>
                                    <TH width="" align="center">&nbsp;<%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
                                </TR>
                                <% 
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(tieuchiSKU != null)
                                    while ( tieuchiSKU.next()){
                                       
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD><%= tieuchiSKU.getString("SCHEME")%></TD>
												<TD><%= tieuchiSKU.getString("DIENGIAI")%></TD>
                                                <% if( tieuchiSKU.getString("trangthai").trim().equals("0") ) {%>
                                                <TD align="center"><%=ChuyenNgu.get("Chưa chốt",nnId) %></TD>
                                                <%}else{ %>
                                                <TD align="center"><%=ChuyenNgu.get("Đã duyệt",nnId) %></TD>
                                                <%} %> 
                                                <TD align="center"><%= tieuchiSKU.getString("NGAYTAO")%> </TD>
                                                <TD align="center"><%= tieuchiSKU.getString("NGUOITAO")%></TD>
                                                <TD align="center"><%= tieuchiSKU.getString("NGAYSUA")%> </TD>
                                                <TD align="center"><%= tieuchiSKU.getString("NGUOISUA")%> </TD>
                                                <TD align="center"> 
                                                <% if( tieuchiSKU.getString("trangthai").trim().equals("0") ) { %>
                                                	<%if(quyen[Utility.SUA]!=0){ %>
					                                <A href = "../../DuyettrakhuyenmaiUpdateSvl?userId=<%=userId%>&update=<%= tieuchiSKU.getString("pk_seq") %>"><IMG src="../images/Edit20.png" alt="Cap nhat" title="Cap nhat" border=0></A>&nbsp;
					                               <%} %>
					                               	<%if(quyen[Utility.CHOT]!=0){ %>
					                                <A href = "../../DuyettrakhuyenmaiSvl?userId=<%=userId%>&duyet=<%= tieuchiSKU.getString("pk_seq") %>"><img src="../images/Chot.png" alt="Duyet" width="20" height="20" longdesc="Duyet" border=0 onclick="if(!confirm('Bạn có muốn duyệt ?')) return false;"></A>&nbsp;
					                                <%} %>
					                                <%if(quyen[Utility.XOA]!=0){ %>
					                                <A href = "../../DuyettrakhuyenmaiSvl?userId=<%=userId%>&delete=<%= tieuchiSKU.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa ?')) return false;"></A>									
							                    	<%} %>
							                    <%} else { %>
							                    	
							                    	<%if(quyen[Utility.XEM]!=0){ %>
							                    	<A href = "../../DuyettrakhuyenmaiUpdateSvl?userId=<%=userId%>&display=<%= tieuchiSKU.getString("pk_seq") %>"><IMG src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0></A>&nbsp;
							                    	<%} %>
							                    <%} %>
							                    </TD>
                                            </TR>
                                        <% m++; } %>  
                                          <TR class="tbfooter" >
									<TD align="center" colspan="15"> | < < 1 to 20 of 100 > >| </TD>
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
</body>  <!-- <script type='text/javascript' src='../scripts/loadingv2.js'></script> -->
</html>
<% 	
	try
{
		if(tieuchiSKU != null)
			tieuchiSKU.close();
	}
	catch (SQLException e) {} %>
<%}%>