<%@page import="java.util.Calendar"%>
<%@page import="geso.dms.center.beans.chitieunhanvien.imp.ChiTieuNhanvien"%>
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
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	ChiTieuNhanvien obj=(ChiTieuNhanvien)session.getAttribute("obj");
	List<ChiTieuNhanvien> chitietlist= obj.getChiTieu();
	
	Integer thang=(Integer)session.getAttribute("thang");
	Integer nam=(Integer)session.getAttribute("nam");


	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/HTP/index.jsp");
	}else{
	int[] quyen = new  int[5];
	quyen = util.Getquyen("ChiTieuNhanvienSvl", "",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);
	System.out.println(quyen[4]);
	%>
	
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("ChiTieuNhanvienSvl","",nnId);	
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>HTP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
		document.forms['bgstForm'].toimuc.value="";
		document.forms['bgstForm'].tumuc.value="";
	    document.forms['bgstForm'].nam.value=0;
	    document.forms['bgstForm'].thang.value=0;
	    document.forms['bgstForm'].selectdvkd.value="";
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
	function moform(value){
		document.forms['bgstForm'].loaichitieu.value=value;	
		document.forms['bgstForm'].action.value='search';
		document.forms['bgstForm'].submit();
	}
	
	function upload()
	{// nhan nut cap nhat

	 	   if(document.forms["bgstForm"].filename.value==""){
	 		   
	 		   document.forms["bgstForm"].dataerror.value="Chưa tìm file upload lên. Vui lòng chọn file cần upload.";
	 	   }else{
	 		 document.forms["bgstForm"].setAttribute('enctype', "multipart/form-data", 0);
	 		 document.forms["bgstForm"].submit();	
	 	   }

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

<form name="bgstForm" method="post" action="../../ChiTieuNhanvienSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
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
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp; 
                            	<%=" "+url %>
							</TD>  
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
                             <TD width="15%" class="plainlabel" ><%=ChuyenNgu.get("Tháng",nnId) %> &nbsp;&nbsp;  <FONT class="erroralert"> </FONT></TD>
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
                             <TD width="15%" class="plainlabel" ><%=ChuyenNgu.get("Năm",nnId) %> &nbsp;&nbsp;  <FONT class="erroralert"> </FONT></TD>
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
                           	<img style="top: -4px;" src="../images/Search30.png" alt=""><%=ChuyenNgu.get("Tìm kiếm",nnId) %> </a>   &nbsp;&nbsp;&nbsp;
                           		<a class="button2" href="javascript:clearform()">
							<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	
                           	
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
                            <LEGEND class="legendtitle">&nbsp;Quản lý chỉ tiêu &nbsp;&nbsp;	   
                            <%if(quyen[0]!=0) {%>                        
							<a class="button3" href="javascript:newform()">
                           	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> </a>                            
	                           <%} %>  
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width=""><%=ChuyenNgu.get("Tháng",nnId) %> </TH>
                                    <TH width=""><%=ChuyenNgu.get("Năm",nnId) %>  </TH>
                                    <TH width=""><%=ChuyenNgu.get("Diễn giải",nnId) %> </TH>
                                    <TH width=""><%=ChuyenNgu.get("Trạng thái",nnId) %></TH> 
                                    <TH width=""><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
                                    <TH width=""><%=ChuyenNgu.get("Người tạo",nnId) %></TH>
                                    <TH width=""><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
                                    <TH width=""><%=ChuyenNgu.get("Người sửa",nnId) %> </TH>
                                    <TH width="" align="center">&nbsp;<%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
                                </TR>
                                <% 
                               		ChiTieuNhanvien ct= new ChiTieuNhanvien() ;
                                    ct=null;
                                    int size = chitietlist.size();
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    System.out.println("sizw =" + size);
                                    while (m < size){
                                        ct = chitietlist.get(m);
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><div align="left"><%=ct.getThang()%></div></TD>                                   
                                                <TD><div align="center"><%=ct.getNam()%></div></TD>
                                                <TD><div align="center"><%=ct.getDienGiai()%></div></TD>
                                                
                                                <% if(ct.getTrangThai().trim().equals("0")){%>
                                                 <TD align="center"><%=ChuyenNgu.get("Chưa xử lý",nnId) %></TD>
                                                 <%}else if(ct.getTrangThai().trim().equals("1")){ %>
                                                  <TD align="center"><%=ChuyenNgu.get("Đã chốt",nnId) %></TD>
                                                 <%}else { %>
                                                 	<TD align="center"><%=ChuyenNgu.get("Đã Hủy",nnId) %></TD>
                                                 <%} %>
                                                <TD align="center"><%= ct.getNgayTao()%></TD>
                                                 <TD align="center"><%= ct.getNguoiTao()%></TD>
                                                  <TD align="center"><%= ct.getNgaySua()%></TD>
                                                   <TD align="center"><%= ct.getNguoiSua()%></TD>
                                                <TD align="center">
                                               		 <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR>
                                              		   <TD>
                                              		   <%if(quyen[3]!=0){ %>
                                                        <A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ChiTieuNhanvienNewSvl?userId="+userId+"&display="+ct.getID()+"")%>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" title="Hiển thị" border = 0></A>
                                                    	<%} %>
                                                     </TD>
                                                    <%
             										System.out.println(ct.getTrangThai());
                                                    if(ct.getTrangThai().trim().equals("0")){ %>
                                                    <TD>
                                                    	<%if(quyen[2]!=0) {%>
                                                        <A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ChiTieuNhanvienNewSvl?userId="+userId+"&update="+ct.getID()+"")%>"><img src="../images/Edit20.png" alt="Cập nhật" width="20" height="20" title="Cập nhật" border = 0></A>
                                                    	<%} %>
                                                    </TD>
                                                    <TD>
                                                   		<%if(quyen[1]!=0) {%>
                                                        <A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ChiTieuNhanvienSvl?userId="+userId+"&delete="+ct.getID()+"")%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" title="Xóa" border=0 onclick="if(!confirm('Bạn chắc chắn muốn xóa chỉ tiêu này?')) return false;"></A></TD>
                                                     	<%} %>
                                                     <TD>
                                                     	<%if(quyen[4]!=0) {%>
                                                        <A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ChiTieuNhanvienSvl?userId="+userId+"&chot="+ct.getID()+"")%>"><img src="../images/Chot.png" alt="Chốt" width="20" height="20" title="Chốt chỉ tiêu" border = 0></A>
                                                     	<%} %>
                                                     </TD>
                                                     <%}else{ %>
                                                     
                                                     <TD>
                                                     	<%if(ct.getTrangThai().trim().equals("1") && quyen[4]!=0) {%>
                                                        <A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ChiTieuNhanvienSvl?userId="+userId+"&unchot="+ct.getID()+"")%>"><img src="../images/unChot.png" alt="Hien thi" width="20" height="20" title="Hiển thị" border = 0></A>
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

