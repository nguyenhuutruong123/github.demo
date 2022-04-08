<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.banggiasieuthi.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IBanggiasieuthiList obj = (IBanggiasieuthiList)session.getAttribute("obj"); %>
<% List<IBanggiasieuthi> bgstlist = (List<IBanggiasieuthi>)obj.getBgstList(); %>
<% ResultSet dvkd = (ResultSet)obj.getDvkd();

int[] quyen = new  int[6];
quyen = util.Getquyen("BanggiasieuthiSvl","", userId);
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 
 } 
String url = util.getChuyenNguUrl("BanggiasieuthiSvl","",nnId); 
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
	function clearform()
	{
	    document.bgstForm.bgstTen.value = "";      
	    document.bgstForm.dvkdTen.selectedIndex = 0;
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

<form name="bgstForm" method="post" action="../../BanggiasieuthiSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
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
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %></TD>  
                            <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId)%><%= obj.getNppTen() %>&nbsp;  </TD>
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
                              <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId)%> &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                                <TR>
                                    <TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Tên bảng giá",nnId)%></TD>
                                    <TD width="81%" class="plainlabel">
                                    <INPUT name="bgstTen" value="<%= obj.getTenbanggia() %>" type="text" size="40" onChange = "submitform();" /></TD></TR>
                               

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
                                		<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
									  
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
                            <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Bảng giá siêu thị",nnId)%> &nbsp;&nbsp;	                         
							<%if(quyen[0]!= 0){ %>
							<a class="button3" href="javascript:newform()">
                           	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId)%> </a>                            
	                         <%} %>     
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width=""><%=ChuyenNgu.get("Bảng giá",nnId)%> </TH>
                                    <TH width=""><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId)%> </TH>
                                    <TH width=""><%=ChuyenNgu.get("Ngày tạo",nnId)%></TH>
                                    <TH width=""><%=ChuyenNgu.get("Người tạo",nnId)%></TH>
                                    <TH width=""><%=ChuyenNgu.get("Ngày sửa",nnId)%></TH>
                                    <TH width=""><%=ChuyenNgu.get("Người sửa",nnId)%></TH>
                                    <TH width="" align="center">&nbsp;Tác vụ</TH>

                                </TR>

                                <% 
                                    IBanggiasieuthi bgst = null;
                                    int size = bgstlist.size();
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    while (m < size){
                                        bgst = bgstlist.get(m);
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><div align="left"><%=bgst.getTenbanggia()%></div></TD>                                   
                                                <TD><div align="center"><%=bgst.getDonvikinhdoanh()%></div></TD>
                                                <TD align="center"><%=bgst.getNgaytao()%></TD>
                                                <TD align="center"><%=bgst.getNguoitao()%></TD>
                                                <TD align="center"><%=bgst.getNgaysua()%></TD>
                                                <TD align="center"><%= bgst.getNguoisua()%></TD>
                                                <TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR>
	                                                    <TD>
	                                                        <%if(quyen[2]!= 0){ %>
	                                                        <A href = "../../BanggiastUpdateSvl?userId=<%=userId%>&update=<%=bgst.getId()%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
	                                                   		<%} %>
	                                                    </TD>
	                                                    <TD>&nbsp;</TD>
	                                                    <TD>
	                                                    	<%if(quyen[2]!= 0){ %>
															<A href = "../../BanggiasieuthiSvl?userId=<%=userId%>&assign=<%= bgst.getId() %>"><img src="../images/Next20.png" alt="Chon Nha Phan Phoi" title="Chọn khách hàng nhà phân phối" width="20" height="20" longdesc="Chọn khách hàng nhà phân phối" border = 0></A>
															<%} %>
														</TD>
														<TD>&nbsp;</TD>
	                                                    <TD>
	                                                    	<%if(quyen[1]!= 0){ %>
	                                                        <A href = "../../BanggiasieuthiSvl?userId=<%=userId%>&delete=<%=bgst.getId()%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn chắc chắn muốn xóa bảng giá này?')) return false;"></A>
	                                                    	<%} %>
	                                                    </TD>
                                                    </TR></TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } %>  
                                         <tr class="tbfooter" > <td colspan="8" >&nbsp;</td> </tr>                                                   
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
<%
	try
	{
		if(!(dvkd == null)){ dvkd.close(); dvkd = null; }
		if(bgstlist!=null){ bgstlist.clear(); bgstlist = null; }
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj",null);
		
	}catch(java.sql.SQLException e){}
%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%}%>