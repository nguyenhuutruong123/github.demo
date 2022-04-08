<%@page import="java.util.Calendar"%>
<%@page import="geso.dms.center.beans.chitieuttchovung.IChiTieuTTChoVung"%>
<%@page import="geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTChoVung"%>
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
	String sum = (String) session.getAttribute("sum");
	ChiTieuTTChoVung obj=(ChiTieuTTChoVung)session.getAttribute("obj");
	List<ChiTieuTTChoVung > chitietlist= obj.getChiTieu();
	Integer thang=(Integer)session.getAttribute("thang");
	Integer nam=(Integer)session.getAttribute("nam");
	String tumuc= (String)session.getAttribute("tumuc");//dung de tim kiem tu muc toi muc chi tieu xac dinh
	String toimuc= (String)session.getAttribute("toimuc");
	String loaichitieu=(String)session.getAttribute("loaichitieu");
	if( loaichitieu==null && loaichitieu.equals("") ){
		loaichitieu="1";//xet mac đinh cho loai chỉ tieu là 1(chỉ tiêu loại secondary)
	}
	String dvkdid=(String)session.getAttribute("dvkdid");
	ResultSet rs_dvkd=(ResultSet)session.getAttribute("rs_dvkd");
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
		document.forms['bgstForm'].loaict.value=value;	
		document.forms['bgstForm'].action.value='search';
		document.forms['bgstForm'].submit();
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

<form name="bgstForm" method="post" action="../../chitieuttchovungSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="nppId" value="" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="loaict" value "<%=loaichitieu%>">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
        <!--begin common dossier info---> <!--End common dossier info--->
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">

                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp; Quản lý chỉ tiêu > Chỉ tiêu cho khu vực</TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn : &nbsp;<%=userTen%>  </TD>
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
                             <tr>
                             <td colspan="2" class="plainlabel">
                              <FIELDSET  >
									<LEGEND  >Loại chỉ tiêu</LEGEND>
									<%
						    	if(loaichitieu.equals("0")){
						    %>
						  	<input type="radio" value="0"  checked="checked" name="loaichitieu" id="auto" onclick="moform(this.value)"> <label for="auto">  Primary</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  	<input type="radio" value="1" name="loaichitieu" id="noauto" onclick="moform(this.value)"> <label for="noauto" > Secondary</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  	 <%
 						  	 	}else{
 						  	 %>
						<input type="radio" value="0"   name="loaichitieu" id="auto" onclick="moform(this.value)"> <label for="auto">  Primary</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  	<input type="radio" value="1" checked="checked" name="loaichitieu" id="noauto" onclick="moform(this.value)"> <label for="noauto" > Secondary</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  		 <%
  						  		 	}
  						  		 %>
									</FIELDSET>
                             </td>
                             </tr>
                             <TR>
                             <TD width="15%" class="plainlabel" >Tháng &nbsp;&nbsp; : <FONT class="erroralert"> *</FONT></TD>
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
                             <TD width="15%" class="plainlabel" >Năm &nbsp;&nbsp; : <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<select name="nam"  style="width :50px">
									<option value=0> </option>  
									<%
									Calendar cal=Calendar.getInstance();
									int year_=cal.get(Calendar.YEAR);
									int n=2008;
									for(n=2008;n<year_+4;n++){
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
                             <tr class="plainlabel">
                             <td>Đơn vị kinh doanh </td>
                             <td>
                             <select name=selectdvkd >
                                 <option value =""> </option>  
                             <%
                             if (rs_dvkd!=null){
                            	 while (rs_dvkd.next()){                      				                       				
                       				 if(rs_dvkd.getString("pk_seq").equals(dvkdid)){ %>
                       				<option value ="<%=rs_dvkd.getString("pk_seq") %>" selected="selected"> <%=rs_dvkd.getString("donvikinhdoanh") %></option>
                       				<%}else{ %>
                       				<option value ="<%=rs_dvkd.getString("pk_seq") %>"> <%=rs_dvkd.getString("donvikinhdoanh") %></option>
                       				<%}     		
                            	 }
                             }
                             %>
                             </select>
                             </td>                           
                             </tr>
                             <tr class="plainlabel" >
                             <td > Mức chỉ tiêu :&nbsp;&nbsp;&nbsp; Từ mức &nbsp;&nbsp;&nbsp;
                             </td>
                             <td>
                              <input name="tumuc" type=text value="<%= tumuc %>" >
                          	  &nbsp;&nbsp;&nbsp; Tới mức  &nbsp;&nbsp;&nbsp;  <input name="toimuc" type=text value="<%= toimuc %>" > </td> 
                             </tr >
                             <tr class="plainlabel"> <td colspan="2" > 
                             <a class="button3" href="javascript:submitform()">
                           	<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>   &nbsp;&nbsp;&nbsp;
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
                            <LEGEND class="legendtitle">&nbsp;Quản lý chỉ tiêu &nbsp;&nbsp;	   
                                                    
							<a class="button3" href="javascript:newform()">
                           	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
	                              
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="">Tháng </TH>
                                    <TH width="">Năm  </TH>
                                    <TH width="">Đơn vị KD  </TH>
                                    <TH width="">Kênh bán hàng  </TH>
                                    <TH width="">Chỉ tiêu</TH>
                                    <TH width="">Trạng thái</TH>            
                                    <TH width="">Ngày kết thúc</TH>                                 
                                    <TH width="">Số ngày làm việc</TH>
                                    <TH width="">Ngày tạo</TH>
                                    <TH width="">Người tạo</TH>
                                    <TH width="">Ngày sửa</TH>
                                    <TH width="">Người sửa </TH>
                                    <TH width="" align="center">&nbsp;Tác vụ</TH>
                                </TR>
                                <% 
                                    ChiTieuTTChoVung ct= new ChiTieuTTChoVung() ;
                                    ct=null;
                                    int size = chitietlist.size();
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    while (m < size){
                                        ct = chitietlist.get(m);
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><div align="left"><%=ct.getThang()%></div></TD>                                   
                                                <TD><div align="center"><%=ct.getNam()%></div></TD>
                                                 <TD><div align="left"><%=ct.getTenDVKD()%></div></TD>
                                                 <TD><div align="left"><%=ct.getTenKenh()%></div></TD>
                                                <TD align="center"><%=formatter.format(Math.round(ct.getChitieu()))%></TD>
                                                <%if(ct.getTrangThai().equals("0")){ %>
                                                 <TD align="center">Chờ xử lý</TD>
                                                 <%}else if(ct.getTrangThai().equals("2")){%>
                                                 <TD align="center">Đã xóa</TD>
                                                 <%}else{ %>
                                                   <TD align="center">Đã xử lý</TD>
                                                 <%} %>
                                                <TD align="center"><%=ct.getNgayKetThuc()%></TD>
                                            
                                                <TD align="center"><%=ct.getSoNgayLamViec()%></TD>
                                                <TD align="center"><%= ct.getNgayTao()%></TD>
                                                 <TD align="center"><%= ct.getNguoiTao()%></TD>
                                                  <TD align="center"><%= ct.getNgaySua()%></TD>
                                                   <TD align="center"><%= ct.getNguoiSua()%></TD>
                                                <TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR>
                                                  <%if(ct.getTrangThai().equals("0")){ %>
                                                    <TD>
                                                        <A href = "../../ChiTieuTTChoVungNewSvl?userId=<%=userId%>&update=<%=ct.getID()%>&loaict=<%=loaichitieu%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" title="Cập nhật" border = 0></A>
                                                    </TD>
                                                    <TD>
                                                        <A href = "../../chitieuttchovungSvl?userId=<%=userId%>&delete=<%=ct.getID()%>&loaict=<%=loaichitieu%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" title="Xóa" border=0 onclick="if(!confirm('Bạn chắc chắn muốn xóa chỉ tiêu này?')) return false;"></A></TD>
                                                     <TD>
                                                        <A href = "../../chitieuttchovungSvl?userId=<%=userId%>&chot=<%=ct.getID()%>&loaict=<%=loaichitieu%>"><img src="../images/Chot.png" alt="Chot" width="20" height="20" title="Chốt chỉ tiêu" border = 0></A>
                                                        </TD>
                                                     <%}else{ %>
                                                     <TD>
                                                        <A href = "../../ChiTieuTTChoVungNewSvl?userId=<%=userId%>&display=<%=ct.getID()%>&loaict=<%=loaichitieu%>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" title="Hiển thị" border = 0></A>
                                                        </TD>
                                                    <%} %>
                                                    </TR></TABLE>
                                                </TD>
                                                
                                            </TR>
                                           
                                        <% m++; } %>  
                                         <tr>
                                            <td class="tbfooter" colspan="14">&nbsp;
                                                </td>
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
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>