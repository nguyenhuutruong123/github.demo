<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.phieuxuatkho.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%
	IPhieuxuatkho pxkBean = (IPhieuxuatkho) session
			.getAttribute("pxkBean");
%>
<%
	ResultSet nvgn = (ResultSet) pxkBean.getNhanvienGN();
%>
<%
	ResultSet donhangList = (ResultSet) pxkBean.getDonhangList();
%>
<%
	ResultSet dhIdsList = (ResultSet) pxkBean.getDhIdsList();
%>

<%
	Hashtable<Integer, String> donhangIds = (Hashtable<Integer, String>) pxkBean
			.getDonhangIds();
%>

<%
	String userId = (String) session.getAttribute("userId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
   
    <script language="javascript" type="text/javascript">
		function submitform()
		{ 
		    document.forms['pxkForm'].action.value='submit';
		    document.forms['pxkForm'].submit();
		}	
	</script>
    

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

<form name="pxkForm" method="post" action="../../PhieuxuatkhoPdfSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId%>' size="30">
<input type="hidden" name="id" value='<%=pxkBean.getId()%>'>
<input type="hidden" name="nppId" value='<%=pxkBean.getNppId()%>'>
<input type="hidden" name="action" value='1'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý bán hàng > Phiếu xuất kho > hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
Chào mừng bạn  <%=pxkBean.getNppTen()%> 
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="javascript:submitform()" >
        	<IMG src="../images/Printer30.png" title="Print" alt="Print" border ="1px" style="border-style:outset"></A>
    </div>
   	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Phiếu xuất kho </legend>
        	
        	<div style="float:none; width:100%" align="left">
            	<table width="100%" cellspacing="0" cellpadding="6px">
                    <tr>
                    	<td class="plainlabel" width="30%" valign="middle">Ngày lập phiếu</td>
                    	<td class="plainlabel">
                            <input type="text" size="11" value="<%=pxkBean.getNgaylap()%>" maxlength="10" readonly />
                    	</td> 
                    	<td class="plainlabel">&nbsp;</td>                   
                    </tr>       
                    <tr>
           				<td class="plainlabel">Nhân viên giao nhận<FONT class="erroralert"> *</FONT></td> 
                        <td class="plainlabel">
                            <SELECT name="nvgnTen" id="nvgnList" disabled="disabled" >
					 			 <option value="">&nbsp;</option>
								  <%
								  	if (nvgn != null) {
								  		try {
								  			while (nvgn.next()) {
								  				if (nvgn.getString("nvgnId")
								  						.equals(pxkBean.getNvgnId())) {
								  %>
						      				<option value='<%=nvgn.getString("nvgnId")%>' selected><%=nvgn.getString("nvgnTen")%></option>
						      			<%
						      				} else {
						      			%>
						     				<option value='<%=nvgn.getString("nvgnId")%>'><%=nvgn.getString("nvgnTen")%></option>
						     	<%
						     		}
						     				}
						     			} catch (java.sql.SQLException e) {
						     			}
						     		}
						     	%>	 
                              </SELECT>
                        </td>
                        <td class="plainlabel">&nbsp;</td>
                    </tr>
                 </table>
                 <hr>                 
                 <table width="100%" cellpadding="4px" cellspacing="1px">
                 	<tr>
                    	<th class="tbheader" align="center">Mã đơn hàng</th>
                        <th class="tbheader" align="center">Nhập ngày</th>
                        <th class="tbheader" align="center">Mã khách hàng</th>
                        <th class="tbheader" align="left">Tên khách hàng</th>
                        <th class="tbheader" valign="middle" width="10%" align="center">Chọn 
                        	<input type="checkbox" name="selectAll" id="selectAll" ></th>
                    </tr>
                    <%
                    	if (dhIdsList != null) {
                    		try {
                    			while (dhIdsList.next()) {
                    %>
		    			<tr class="tbdarkrow">
	                    	<td align="center"><%=dhIdsList.getString("dhId")%></td>
	                        <td align="center"><%=dhIdsList.getDate("ngaynhap")
												.toString()%></td>
	                        <td align="center"><%=dhIdsList.getString("khId")%></td>
	                        <td><%=dhIdsList.getString("khTen")%></td>
	                        <%
	                        	if (donhangIds.contains(dhIdsList.getString("dhId"))) {
	                        %>
	                        	<td align="center"><input type="checkbox" name="donhangList" value="<%=dhIdsList.getString("dhId")%>" onChange="UnCheckedAll()" checked disabled="disabled"></td>
	                        <%
	                        	} else {
	                        %>
	                        	<td align="center"><input type="checkbox" name="donhangList" value="<%=dhIdsList.getString("dhId")%>" onChange="UnCheckedAll()" disabled="disabled"></td>
	                        <%
	                        	}
	                        %>
                    	</tr>
	     			<%
	     				}
	     						dhIdsList.close();
	     					} catch (java.sql.SQLException e) {
	     					}
	     				}
	     			%>
	     			
                    <%
	     			                    	if (donhangList != null) {
	     			                    		try {
	     			                    			while (donhangList.next()) {
	     			                    %>
		    			<tr class="tbdarkrow">
	                    	<td align="center"><%=donhangList.getString("dhId")%></td>
	                        <td align="center"><%=donhangList.getDate("ngaynhap").toString()%></td>
	                        <td align="center"><%=donhangList.getString("khId")%></td>
	                        <td><%=donhangList.getString("khTen")%></td>
	                        <%
	                        	if (donhangIds.contains(donhangList.getString("dhId"))) {
	                        %>
	                        	<td align="center"><input type="checkbox" name="donhangList" value="<%=donhangList.getString("dhId")%>" onChange="UnCheckedAll()" checked disabled="disabled"></td>
	                        <%
	                        	} else {
	                        %>
	                        	<td align="center"><input type="checkbox" name="donhangList" value="<%=donhangList.getString("dhId")%>" onChange="UnCheckedAll()" disabled="disabled"></td>
	                        <%
	                        	}
	                        %>
                    	</tr>
	     			<%
	     				}
	     						donhangList.close();
	     					} catch (java.sql.SQLException e) {
	     					}
	     				}
	     			%>
	     			
                    <tr class="tbfooter"><td colspan="5">&nbsp;</td></tr>
                 </table>
            </div>
            <div align="left" style="width:100%; float:none; clear:none; display:none" id="sanphamList">                   
        </div>      
    </fieldset>	
    </div>
</div>
</form>
</BODY>
</html>