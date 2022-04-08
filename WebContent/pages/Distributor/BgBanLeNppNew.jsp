<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.banggiablnpp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IBanggiablnpp bgblBean = (IBanggiablnpp)session.getAttribute("bgblBean"); %>

<% ResultSet dvkd = (ResultSet)bgblBean.getDvkdRs(); %>
<% List<ISanpham> splist = (List<ISanpham>)bgblBean.getSpList(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<SCRIPT language="javascript" type="text/javascript">
	 function confirmLogout()
	 {
	    if(confirm("Ban co muon dang xuat?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	  }
	 function submitform()
	 {   
	    document.forms["bgblForm"].submit();
	 }
	 function saveform()
	 {	  
		 var bgblTen = document.getElementById("bgblTen");
		  var dvkdTen = document.getElementById("dvkdTen");
		 
		 if(bgblTen.value == "")
		 {
			alert("Tên bảng giá không được rỗng..");
			return;
		 }

		 if(dvkdTen.value == "")
		 {
			alert("Vui lòng chọn đơn vị kinh doanh..");
			return;
		 }
			
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

	 	 document.forms['bgblForm'].action.value='save';
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

<form name="bgblForm" method="post" action="../../BanggiablUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="nppId" value='<%= bgblBean.getNppId() %>'>
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
            <TABLE width="100%" cellpadding="0" cellspacing="2">
                <TR>
                    <TD align="left" class="tbnavigation">
                       <table width="100%" border="0" cellpadding="0" cellspacing="0">
                           <tr height="22">
                               <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền > Bảng giá bán lẻ > Tạo mới
                               <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn   <%=userTen %>&nbsp;  </TD>
                         </tr>
                      </table>
                  </TD>
              </TR> 
            </TABLE>
            <TABLE width="100%" cellpadding="0" cellspacing="2">
                <TR ><TD >
                <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                    <TR class = "tbdarkrow">
                        <TD width="30" align="left"><A href= "javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
                        <TD width="2" align="left" ></TD>
                        <TD width="30" align="left" >
                        <div id="btnSave">
                        <A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
                        </div>
                        </TD>

                        <TD align="left" >&nbsp;</TD>
                    </TR>
                </TABLE>
                </TD></TR>
            </TABLE>   
            <TABLE width="100%" height="98%" border ="0" cellspacing="0" cellpadding="0">
                <tr>
                    <TD align="left" colspan="4" class="legendtitle">
                        <FIELDSET>
                        	<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>              
                        	<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" cols="100%" rows="1"  style="width: 100% " readonly="readonly"  ><%= bgblBean.getMessage() %></textarea>
                        	<% bgblBean.setMessage(""); %>
                        </FIELDSET>
                   </TD>
                </tr>
                <TR>
                    <TD>
                        <FIELDSET><LEGEND class="legendtitle">&nbsp;Bảng giá bán lẻ chuẩn &nbsp;</LEGEND>
                        <TABLE width="100%" border = "0" cellspacing="0" cellpadding="0">
                            <TR>
                                <TD>
                                    <TABLE border="0" width="100%" cellspacing="0" cellpadding="0">
                                        
                                        <TR>
                                            <TD width="100%" align="center">
                                                <TABLE width="99%" border = "0" cellpadding="6" cellspacing="0">
													<TR>
                                    <TD width="19%" class="plainlabel">Tên bảng giá</TD>
                                    <TD width="81%" class="plainlabel">
                                    <INPUT name="bgblTen" id="bgblTen" value="<%= bgblBean.getTenbanggia() %>" type="text" size="40"/></TD></TR>
                               
                                <TD class="plainlabel">Đơn vị kinh doanh</TD>
                                <TD class="plainlabel">
                                <SELECT name="dvkdTen" id="dvkdTen" onChange = "submitform();">
										  <option value=""> </option>
										  <% if(dvkd != null){
										  try{ while(dvkd.next()){ 
								    			if(dvkd.getString("dvkdId").equals(bgblBean.getDvkdId())){ %>
								      				<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkdTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=dvkd.getString("dvkdId")%>'><%=dvkd.getString("dvkdTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} }%>	 
                                </SELECT>
                                </TD> </TR>                             
                               <TR>
                                   </TABLE>                          
                                            </TD>
                                        </TR>
                                    </TABLE>                    
                                </TD>
                            </TR>
                        </TABLE> <br>
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
                                <TR class="tbheader">
                                    <TH width="15%" style="padding:4px">Mã sản phẩm </TH>
                                    <TH width="65%" style="padding:4px">Tên sản phẩm</TH>
									<TH width="10%" style="padding:4px">Giá chuẩn</TH>
                                    <TH width="10%" style="padding:4px">Giá bán</TH>
                                </TR>
                                
								<%
									if(splist != null)
									{
										ISanpham sanpham = null;
										int size = splist.size();
										int m = 0;
										while (m < size){
											sanpham = splist.get(m);
											  if(m % 2 == 0) {%>
	                                        		<TR class='tblightrow'>
	                                        <%} else {%>
	                                        		<TR class='tbdarkrow'> 
	                                        <%} %>                        	                                          
                                              <TD style="padding:4px"><%= sanpham.getMasanpham() %>
                                              	<INPUT name="spId" type="hidden" value="<%= sanpham.getId() %>" />
                                              	<INPUT name="spMa" type="hidden" value="<%= sanpham.getMasanpham() %>" /></TD>
                                              <TD style="padding:4px"><%= sanpham.getTensanpham() %>
                                              	<INPUT name="spTen" type="hidden" value="<%= sanpham.getTensanpham() %>" /></TD>
                                              <TD style="padding:4px"><%= sanpham.getGiabanlechuan() %>
                                              	<INPUT name="spGiablc" type="hidden" value="<%= sanpham.getGiabanlechuan() %>" /></TD>
                                              <TD style="padding:4px">
                                              	<INPUT type="text" name="spGiabanleNpp" value ="<%= sanpham.getGiaban() %>" size="17"></TD>
                                          </TR>
                                  	<% m++; }} %>
                               
                        </TABLE> </FIELDSET>

        <!--end body Dossier-->       
        </td>                               
        </tr>
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
		if(!(dvkd == null))
			dvkd.close();
		if(splist!=null){
			splist.clear();
		}
		if(bgblBean != null){
			bgblBean.DBclose();
			bgblBean = null;
		}
		
	}catch(Exception e){}

}%>