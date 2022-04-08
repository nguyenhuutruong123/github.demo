<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.banggiablnpp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{
		int[] quyen = new  int[6];
	%>

<% IBanggiablnppList obj = (IBanggiablnppList)session.getAttribute("obj"); %>
<% ResultSet bgbllist = (ResultSet)obj.getBgblList(); %>
<% ResultSet ncc = (ResultSet)obj.getNcc(); %>
<% ResultSet dvkd = (ResultSet)obj.getDvkd(); %>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
 } 
String url = util.getChuyenNguUrl("BanggiablSvl","",nnId); 
String view = obj.getView();
if (view != null && view.equals("TT")) {
	quyen = util.Getquyen("","&view=TT",userId);
}
else {
	quyen = util.Getquyen("BanggiablSvl","",userId);
}
 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<SCRIPT language="javascript" type="text/javascript">
	function submitform()
	{
		document.forms['bgblForm'].action.value= 'search';
		document.forms['bgblForm'].submit();
	}
	function clearform()
	{
		document.bgblForm.bgblTen.value = "";
		document.bgblForm.dvkdTen.selectedIndex = 0;
		submitform();	    
	}

	function Xoa(id){
		
		document.forms['bgblForm'].IdXoa.value = id;
		document.forms['bgblForm'].action.value= 'delete';
		document.forms['bgblForm'].submit();
	}

	function newform()
	{
		document.forms['bgblForm'].action.value= 'new';
		document.forms['bgblForm'].submit();
	}
	function chot()
	{
		document.forms['bgblForm'].action.value= 'chot';
		document.forms['bgblForm'].submit();
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

<form name="bgblForm" method="post" action="../../BanggiablSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="view" value='<%=view%>'>
<input type="hidden" name="IdXoa" value=''>
<input type="hidden" name="IdChot" value=''>



<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
    height="100%">
    
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">

                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation"><%= " " + url %></TD>  
                            <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId)%> <%= obj.getNppTen() %>&nbsp;</TD>
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

                              <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId)%>&nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                                <TR>
                                    <TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Tên bảng giá",nnId)%></TD>
                                    <TD width="81%" class="plainlabel">
                                    <INPUT name="bgblTen" value="<%= obj.getTenbanggia() %>" type="text" size="40" onChange = "submitform();"/></TD></TR>
                               
                               <TR>
                                <TD class="plainlabel"><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId)%></TD>
                                <TD class="plainlabel">
                                <SELECT name="dvkdTen" onChange = "submitform();">
										  <option value=""> </option>
										  <% if(dvkd != null){
										  try{ while(dvkd.next()){ 
								    			if(dvkd.getString("dvkdId").equals(obj.getDvkdId())){ %>
								      				<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkdTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=dvkd.getString("dvkdId")%>'><%=dvkd.getString("dvkdTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} }%>	 
                                </SELECT>
                                </TD> </TR>
                               
                               <TR>
									<TD class="plainlabel" colspan="3">
                           		 		<a class="button2" href="javascript:clearform()">
                                		<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId)%></a>&nbsp;&nbsp;&nbsp;&nbsp;
                                		                       									
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
                            <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Bảng giá bán lẻ",nnId)%>  &nbsp; &nbsp; &nbsp;
							      
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader" align=center>
                                    <TH width="15%"><%=ChuyenNgu.get("Bảng giá",nnId)%> </TH>
                                    <TH width="10%"><%=ChuyenNgu.get("ĐVKD",nnId)%> </TH>
                                    <TH width="10%"><%=ChuyenNgu.get("Ngày tạo",nnId)%></TH>
                                    <TH width="15%"><%=ChuyenNgu.get("Người tạo",nnId)%></TH>
                                    <TH width="10%"><%=ChuyenNgu.get("Ngày sửa",nnId)%> </TH>
                                    <TH width="15%"><%=ChuyenNgu.get("Người sửa",nnId)%></TH>
                                    <TH width="5%" align="center">&nbsp;<%=ChuyenNgu.get("Tác vụ",nnId)%></TH>

                                </TR>

                                <% 
                                int size;
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                   if(bgbllist!= null){
                                    while (bgbllist.next()){
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><div align="left"><%=bgbllist.getString("BGBLTEN")%></div></TD>                                   
                                                <TD><div align="center"><%=bgbllist.getString("DVKDTENVIETTAT")%></div></TD>
                                                <TD align="center"><%=bgbllist.getString("ngaytao")%></TD>
                                                <TD align="left"><%=bgbllist.getString("nguoitao")%></TD>
                                                <TD align="center"><%=bgbllist.getString("ngaysua")%></TD>
                                                <TD align="left"><%= bgbllist.getString("nguoisua")%></TD>
                                                <TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR>
                                                    <TD>
                                                    <%if(bgbllist.getString("trangthai").equals("0") &&   quyen[Utility.SUA]!=0){ %>
                                                      <A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "BanggiablUpdateSvl?userId=" + userId+ "&update="+ bgbllist.getString("pk_seq")) %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>                                                 
                                                   <%} %>
                                                    </TD>
                                                     <TD>
                                                    <%if( quyen[Utility.XEM]!=0){ %>
                                                    <A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "BanggiablUpdateSvl?userId=" + userId+ "&display="+ bgbllist.getString("pk_seq")) %>"><img src="../images/Display.png" alt="hien thi" title="Hiển thị" width="20" height="20" longdesc="hien thi" border = 0></A>                                                 
                                                    
                                                   <%} %>
                                                    </TD>
                                                    <TD>
                                                  
														<%if(bgbllist.getString("trangthai").equals("0") &&  quyen[Utility.CHOT]!=0){ %>
                                                      <A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "BanggiablSvl?userId=" + userId+ "&chot="+ bgbllist.getString("pk_seq")) %>"><img src="../images/Chot.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0></A>                                                 														
														<%} %>	
													</TD>
													
                                                    <TD>&nbsp;</TD>
                                                    <TD>
                                                        </TD>
                                                    </TR></TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; }} %>
                                        <tr class="tbfooter" > <td colspan="10" >&nbsp;</td> </tr>                                                   
                            </TABLE>
                            </TD>
                        </TR>
                    </TABLE>
                    </FIELDSET>
                    </TD>
                </TR>

            </TBODY>
        </TABLE>
        <!--end body Dossier--></TD>
    </TR>
</TABLE>
</form>
</BODY>
</html>
<%
	try
	{
		if(!(dvkd == null))
			dvkd.close();
		if(!(ncc == null))
			ncc.close();
		if(bgbllist!=null){
			bgbllist.close();
		}
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}

		session.setAttribute("obj",null);
	}catch(java.sql.SQLException e){}
	}
%>
