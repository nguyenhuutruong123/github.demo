<!--desigs : khoana, chi tieu trung tam cho tung vung -->
<%@page import="geso.dms.center.beans.chitieu.IChiTieu"%>
<%@page import="geso.dms.center.beans.chitieu.imp.ChiTieuNPP"%>
<%@page import="java.util.Calendar"%>
<%@page import="geso.dms.center.beans.chitieu.imp.ChiTieu"%>
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
	IChiTieu obj=(IChiTieu)session.getAttribute("obj");
	ResultSet rschitieu=obj.rsChitieuPri();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ChitieuPriSvl","&view="+obj.getView()+"",userId);
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
		submitform();
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

<form name="bgstForm" method="post" action="../../ChitieuPriSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
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
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp; Qu???n l?? ch??? ti??u &gt; Ch??? ti??u NPP &gt; Ch??? ti??u Mua V??o</TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n &nbsp;<%=userTen%>  </TD>
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
                              <LEGEND class="legendtitle">&nbsp;Ti??u ch?? t??m ki???m &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                             <TR>
                             <TR>
                             <TD width="15%" class="plainlabel" >Th??ng </TD>
								<TD class="plainlabel">
									<select name="thang" style="width: 100px" onchange="submitform();">
									<option value= "0"> </option>  
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
                             <TD width="20%" class="plainlabel" >N??m </TD>
								<TD class="plainlabel">
									<select name="nam"  style="width :100px" onchange="submitform();">
									<option value= "0"> </option>  
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
                              
                             <tr class="plainlabel"> 
	                             <td colspan="2" > 
	                           		<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nh???p l???i</a>&nbsp;&nbsp;&nbsp;&nbsp;	
	                             </td> 
                             </tr>
                            </TABLE>
                           </FIELDSET>
                    </TABLE>
                    </TD>
                </TR>
                            
                           
                             <tr class="plainlabel"> <td colspan="2" > 
                             <a class="button3" href="javascript:submitform()">
                           	<img style="top: -4px;" src="../images/Search30.png" alt="">T??m ki???m </a>   &nbsp;&nbsp;&nbsp;
                           		<a class="button2" href="javascript:clearform()">
							<img style="top: -4px;" src="../images/button.png" alt="">Nh???p l???i</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                           	
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
                            <LEGEND class="legendtitle">&nbsp;Qu???n l?? ch??? ti??u &nbsp;&nbsp;	   
                                                    <%if(quyen[0]!=0){ %>
							<a class="button3" href="javascript:newform()">
                           	<img style="top: -4px;" src="../images/New.png" alt="">T???o m???i </a>                            
	                              <%} %>
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="">Th??ng </TH>
                                    <TH width="">N??m  </TH>
                                    <TH >????n v??? KD</TH>
                                    <TH >K??nh </TH>
                                    <TH width="">Ch??? ti??u</TH>
                                    <TH width="">Tr???ng th??i</TH>
                                     <TH width="">S??? ng??y l??m vi???c</TH>
                                    <TH width="">Ng??y k???t th??c</TH>  
                                    <TH width="">Ng??y t???o</TH>
                                    <TH width="">Ng?????i t???o</TH>
                                    <TH width="">Ng??y s???a</TH>
                                    <TH width="">Ng?????i s???a </TH>
                                    <TH width="" align="center">&nbsp;T??c v???</TH>
                                </TR>
                                <% 
                                  
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    int m=0;
                                    if(rschitieu!=null)
                                    while (rschitieu.next()){
                                        
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><div align="left"><%=rschitieu.getString("thang")%></div></TD>                                   
                                                <TD><div align="center"><%=rschitieu.getString("nam")%></div></TD>
                                               <TD align="center"><%=rschitieu.getString("donvikinhdoanh")%></TD>
                                                <TD align="center"><%=rschitieu.getString("kenh")%></TD>
                                                <TD align="center"><%=formatter.format(rschitieu.getDouble("chitieu"))%></TD>
                                                <% if(rschitieu.getString("trangthai").trim().equals("0")){%>
                                                 <TD align="center">Ch??a x??? l??</TD>
                                                 <%}else{ %>
                                                  <TD align="center">???? ch???t</TD>
                                                 <%} %>
                                                  <TD align="center"><%=rschitieu.getString("songaylamviec")%></TD>
                                                <TD align="center"><%=rschitieu.getString("ngayketthuc")%></TD>
                                                <TD align="center"><%= rschitieu.getString("ngaytao")%></TD>
                                                 <TD align="center"><%= rschitieu.getString("nguoitao")%></TD>
                                                  <TD align="center"><%= rschitieu.getString("ngaysua")%></TD>
                                                   <TD align="center"><%= rschitieu.getString("nguoisua")%></TD>
                                                <TD align="center">
                                               		 <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR>
                                              		   <TD>
                                              		   <%if(quyen[3]!=0){ %>
                                                        <A href = "../../ChitieuPriNewSvl?userId=<%=userId%>&display=<%=rschitieu.getString("PK_SEQ")%>&loaict=<%="0"%>"><img src="../images/Display20.png" alt="Hien thi" title="Hi???n th???" width="20" height="20" title="Hi???n th???" border = 0></A>
                                                     <%} %>
                                                     </TD>
                                                    <%
             									
                                                    if(rschitieu.getString("trangthai").trim().equals("0")){ %>
                                                    	<TD>
                                                    <%if(quyen[2]!=0){ %>
                                                        <A href = "../../ChitieuPriNewSvl?userId=<%=userId%>&update=<%=rschitieu.getString("pk_seq")%>&loaict=<%="0"%>"><img src="../images/Edit20.png" alt="C???p nh???t" title="C???p nh???t" width="20" height="20" title="C???p nh???t" border = 0></A>
                                                    <%} %>
                                                   		 </TD>
                                                    	<TD>
                                                    <%if(quyen[1]!=0){ %>
                                                        <A href = "../../ChitieuPriSvl?userId=<%=userId%>&delete=<%=rschitieu.getString("pk_seq")%>&loaict=<%="0"%>"><img src="../images/Delete20.png" alt="Xoa" title="X??a" width="20" height="20" title="X??a" border=0 onclick="if(!confirm('B???n ch???c ch???n mu???n x??a ch??? ti??u n??y?')) return false;"></A>
                                                       <%} %> 
                                                        </TD>
                                                     <TD>
                                                     <%if(quyen[4]!=0){ %>
                                                        <A href = "../../ChitieuPriSvl?userId=<%=userId%>&chot=<%=rschitieu.getString("pk_seq")%>&loaict=<%="0"%>"><img src="../images/Chot.png" alt="Ch???t" width="20" height="20" title="Ch???t ch??? ti??u" border = 0></A>
                                                     <%} %>
                                                     </TD>
                                                     <%}else{ %>
                                                     <TD>
                                                     <%if(quyen[5]!=0){ %>
                                                        <A href = "../../ChitieuPriSvl?userId=<%=userId%>&unchot=<%=rschitieu.getString("pk_seq")%>&loaict=<%="0"%>"><img src="../images/unChot.png" alt="Hien thi" width="20" height="20" title="Th??o ch???t" border = 0></A>
                                                     <%} %>
                                                     </TD>
                                                    <%}%>
                                                     <TD>
                                                    <%if(quyen[2]!=0){ %>
                                                        <A href = "../../ChitieuPriNewSvl?userId=<%=userId%>&excel=<%=rschitieu.getString("pk_seq")%>"><img src="../images/excel.gif" alt="Xu???t File Excel" width="20" height="20" title="Xu???t File Excel" border = 0></A>
                                                    <%} %>
                                                    </TD>
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
</html>

<%
try{
	
	if(rschitieu!= null){ rschitieu.close(); rschitieu= null;}
	obj.DbClose(); obj= null;
	session.setAttribute("obj",null);
	
}catch(Exception er){
}
	}
%>
